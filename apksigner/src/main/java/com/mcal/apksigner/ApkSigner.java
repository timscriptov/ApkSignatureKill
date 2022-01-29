package com.mcal.apksigner;

import android.content.Context;
import android.util.Log;

import com.android.apksigner.ApkSignerTool;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ApkSigner {

    private Context context;

    public ApkSigner(Context context) {
        this.context = context;
    }

    public void signApk(String inputPath, String outputPath) {
        signWithTestKey(inputPath, outputPath, null);
    }

    /**
     * Sign an APK with testkey.
     *
     * @param inputPath  The APK file to sign
     * @param outputPath File to output the signed APK to
     * @param callback   Callback for System.out during signing. May be null
     */
    public void signWithTestKey(String inputPath, String outputPath, LogCallback callback) {
        try (LogWriter logger = new LogWriter(callback)) {
            long savedTimeMillis = System.currentTimeMillis();
            PrintStream oldOut = System.out;

            List<String> args = Arrays.asList(
                    "sign",
                    "--in",
                    inputPath,
                    "--out",
                    outputPath,
                    "--key",
                    context.getFilesDir() + "/bin/testkey.pk8",
                    "--cert",
                    context.getFilesDir() + "/bin/testkey.x509.pem"
            );

            logger.write("Signing an APK file with these arguments: " + args);

            /* If the signing has a callback, we need to change System.out to our logger */
            if (callback != null) {
                try (PrintStream stream = new PrintStream(logger)) {
                    System.setOut(stream);
                }
            }

            try {
                ApkSignerTool.main(args.toArray(new String[0]));
            } catch (Exception e) {
                callback.errorCount.incrementAndGet();
                logger.write("An error occurred while trying to sign the APK file " + inputPath +
                        " and outputting it to " + outputPath + ": " + e.getMessage() + "\n" +
                        "Stack trace: " + Log.getStackTraceString(e));
            }

            logger.write("Signing an APK file took " + (System.currentTimeMillis() - savedTimeMillis) + " ms");

            if (callback != null) {
                System.setOut(oldOut);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public interface LogCallback {
        AtomicInteger errorCount = new AtomicInteger(0);

        void onNewLineLogged(String line);
    }

    private static class LogWriter extends OutputStream {

        private final LogCallback mCallback;
        private String mCache = "";

        private LogWriter(LogCallback callback) {
            mCallback = callback;
        }

        @Override
        public void write(int b) {
            if (isLoggingDisabled()) return;

            mCache += (char) b;

            if (((char) b) == '\n') {
                mCallback.onNewLineLogged(mCache);
                mCache = "";
            }
        }

        private void write(String s) {
            if (isLoggingDisabled()) return;

            for (byte b : s.getBytes()) {
                write(b);
            }
        }

        private boolean isLoggingDisabled() {
            return mCallback == null;
        }
    }
}
