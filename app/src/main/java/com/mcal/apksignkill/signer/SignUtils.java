package com.mcal.apksignkill.signer;

import android.content.Context;
import android.content.res.AssetManager;
import android.sun.security.pkcs.PKCS8Key;
import android.util.Log;
import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.apksig.ApkSigner;
import com.android.apksig.ApkVerifier;
import com.mcal.apksignkill.utils.Preferences;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.List;

import kellinwood.security.zipsigner.optional.KeyStoreFileManager;

public class SignUtils {
    private static String msgId;
    @Nullable
    private static File idsigFile;
    @NonNull
    private final PrivateKey privateKey;
    @NonNull
    private final X509Certificate certificate;

    private SignUtils(@NonNull PrivateKey privateKey, @NonNull X509Certificate certificate) {
        this.privateKey = privateKey;
        this.certificate = certificate;
    }

    @NonNull
    public static SignUtils getInstance(Context context, File idSigFile)
            throws SignatureException {
        idsigFile = idSigFile;
        msgId = null;
        Pair<PrivateKey, X509Certificate> signingKey;
        boolean isCustom = Preferences.isCustomSignature();
        if (isCustom) {
            String keyPath = Preferences.isSignaturePath();
            String certAlias = Preferences.isSignatureAlias();
            String storePass = Preferences.isCertPassword();
            String keyPass = Preferences.isSignaturePassword();
            try {
                signingKey = loadKey(keyPath, keyPass.toCharArray(), certAlias, storePass.toCharArray());
            } catch (Exception e) {
                throw new SignatureException("Unable to sign apk.", e);
            }
        } else {
            try {
                signingKey = loadKey(context.getAssets());
            } catch (Exception e) {
                throw new SignatureException("Unable to sign apk.", e);
            }
        }
        return new SignUtils(signingKey.first, signingKey.second);
    }

    @NonNull
    private static Pair<PrivateKey, X509Certificate> loadKey(@NonNull AssetManager assets)
            throws IOException, InvalidKeyException, CertificateException {
        PrivateKey privateKey;
        X509Certificate certificate;
        try (InputStream key = assets.open("key/testkey.pk8")) {
            PKCS8Key pkcs8Key = new PKCS8Key();
            pkcs8Key.decode(key);
            privateKey = pkcs8Key;
        }
        try (InputStream cert = assets.open("key/testkey.x509.pem")) {
            certificate = (X509Certificate) CertificateFactory.getInstance("X.509")
                    .generateCertificate(cert);
        }
        return new Pair<>(privateKey, certificate);
    }

    public static @NotNull Pair<PrivateKey, X509Certificate> loadKey(String keystorePath, char[] keyPass, String certAlias, char[] storePass) throws Exception {
        if (!new File(keystorePath).exists())
            throw new FileNotFoundException(keystorePath + " not found.");
        KeyStore keystore = KeyStoreFileManager.loadKeyStore(keystorePath, keyPass);
        Certificate cert = keystore.getCertificate(certAlias);
        X509Certificate publicKey = (X509Certificate) cert;
        Key key = keystore.getKey(certAlias, storePass);
        PrivateKey privateKey = (PrivateKey) key;

        return new Pair<>(privateKey, publicKey);
    }

    public boolean verify(@NonNull File apk) {
        ApkVerifier.Builder builder = new ApkVerifier.Builder(apk);
        if (Preferences.getSignatureV4()) {
            if (idsigFile == null) {
                throw new RuntimeException("idsig file is mandatory for v4 signature scheme.");
            }
            builder.setV4SignatureFile(idsigFile);
        }
        ApkVerifier verifier = builder.build();
        try {
            ApkVerifier.Result result = verifier.verify();
            Log.i("SignUtils::verify", apk.toString());
            boolean isVerify = result.isVerified();
            if (isVerify) {
                if (Preferences.getSignatureV1() && result.isVerifiedUsingV1Scheme())
                    Log.i("SignUtils::verify", "V1 signature verification succeeded.");
                else Log.w("SignUtils::verify", "V1 signature verification failed/disabled.");
                if (Preferences.getSignatureV2() && result.isVerifiedUsingV2Scheme())
                    Log.i("SignUtils::verify", "V2 signature verification succeeded.");
                else Log.w("SignUtils::verify", "V2 signature verification failed/disabled.");
                if (Preferences.getSignatureV3() && result.isVerifiedUsingV3Scheme())
                    Log.i("SignUtils::verify", "V3 signature verification succeeded.");
                else Log.w("SignUtils::verify", "V3 signature verification failed/disabled.");
                if (Preferences.getSignatureV4() && result.isVerifiedUsingV4Scheme())
                    Log.i("SignUtils::verify", "V4 signature verification succeeded.");
                else Log.w("SignUtils::verify", "V4 signature verification failed/disabled.");
                List<X509Certificate> signerCertificates = result.getSignerCertificates();
                Log.i("SignUtils::verify", "Number of signatures: " + signerCertificates.size());
            }
            for (ApkVerifier.IssueWithParams warn : result.getWarnings()) {
                Log.w("SignUtils::verify", warn.toString());
            }
            for (ApkVerifier.IssueWithParams err : result.getErrors()) {
                Log.e("SignUtils::verify", err.toString());
            }
            if (Preferences.getSignatureV1()) {
                for (ApkVerifier.Result.V1SchemeSignerInfo signer : result.getV1SchemeIgnoredSigners()) {
                    String name = signer.getName();
                    for (ApkVerifier.IssueWithParams err : signer.getErrors()) {
                        Log.e("SignUtils::verify", name + ": " + err);
                    }
                    for (ApkVerifier.IssueWithParams err : signer.getWarnings()) {
                        Log.w("SignUtils::verify", name + ": " + err);
                    }
                }
            }
            return isVerify;
        } catch (Exception e) {
            Log.w("SignUtils::verify", "Verification failed.", e);
            return false;
        }
    }

    public boolean sign(File in, File out, int minSdk) {
        if (msgId != null) Log.w("SignUtils::sign", msgId);
        ApkSigner.SignerConfig signerConfig = new ApkSigner.SignerConfig.Builder("CERT",
                privateKey, Collections.singletonList(certificate)).build();
        ApkSigner.Builder builder = new ApkSigner.Builder(Collections.singletonList(signerConfig));
        builder.setInputApk(in);
        builder.setOutputApk(out);
        builder.setCreatedBy("ApkProtector");
        if (minSdk != -1) builder.setMinSdkVersion(minSdk);
        builder.setV1SigningEnabled(Preferences.getSignatureV1());
        builder.setV2SigningEnabled(Preferences.getSignatureV2());
        builder.setV3SigningEnabled(Preferences.getSignatureV3());
        if (Preferences.getSignatureV4()) {
            if (idsigFile == null) {
                throw new RuntimeException("idsig file is mandatory for v4 signature scheme.");
            }
            builder.setV4SigningEnabled(true);
            builder.setV4SignatureOutputFile(idsigFile);
        }
        ApkSigner signer = builder.build();
        Log.i("SignUtils::sign", String.format("SignApk: %s", in));
        try {
            signer.sign();
            Log.i("SignUtils::sign", "The signature is complete and the output file is " + out);
            return true;
        } catch (Exception e) {
            Log.w("SignUtils::sign", e);
            return false;
        }
    }
}