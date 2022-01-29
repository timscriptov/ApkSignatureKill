package com.mcal.apksignkill.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import com.developer.filepicker.model.DialogConfigs;
import com.developer.filepicker.model.DialogProperties;
import com.developer.filepicker.view.FilePickerDialog;
import com.mcal.apksigner.ApkSigner;
import com.mcal.apksignkill.App;
import com.mcal.apksignkill.R;
import com.mcal.apksignkill.utils.BinSignatureTool;
import com.mcal.apksignkill.utils.MyAppInfo;
import com.mcal.apksignkill.utils.SuperSignatureTool;

import org.jetbrains.annotations.NotNull;

import java.io.File;

public class HomeFragment extends Fragment {
    public static AppCompatEditText apkPath;
    private AppCompatImageView apkIcon;
    private AppCompatTextView apkName;
    private AppCompatTextView apkPack;

    View.OnClickListener radioButtonClickListener = v -> {
        AppCompatRadioButton rb = (AppCompatRadioButton) v;
        switch (rb.getId()) {
            case R.id.binMtSignatureKill:
                App.getPreferences().edit().putBoolean("setBinMtSignatureKill", true);
                App.getPreferences().edit().putBoolean("setSuperSignatureKill", false);
                break;
            case R.id.superSignatureKill:
                App.getPreferences().edit().putBoolean("setSuperSignatureKill", true);
                App.getPreferences().edit().putBoolean("setBinMtSignatureKill", false);
                break;

            default:
                break;
        }
    };

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.activity_main, container, false);

        apkIcon = mView.findViewById(R.id.apkIcon);
        apkName = mView.findViewById(R.id.apkName);
        apkPack = mView.findViewById(R.id.apkPackage);
        apkPath = mView.findViewById(R.id.apkPath);

        apkPath.setText(App.getPreferences().getString("ApkPath", ""));
        apkPath.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence p1, int p2, int p3, int p4) {
            }

            @Override
            public void onTextChanged(CharSequence p1, int p2, int p3, int p4) {
            }

            @Override
            public void afterTextChanged(Editable p1) {
                if (!p1.toString().isEmpty()) {
                    File apk = new File(p1.toString());
                    if (apk.exists()) {
                        apkIcon.setImageDrawable(new MyAppInfo(getContext(), apk.getAbsolutePath()).getIcon());
                        apkName.setText(MyAppInfo.getAppName());
                        apkPack.setText(MyAppInfo.getPackage());
                    } else {
                        apkIcon.setImageResource(R.mipmap.ic_launcher);
                        apkName.setText("Select apk");
                        apkPack.setText("none");
                    }
                }
            }
        });

        (mView.findViewById(R.id.browseApk)).setOnClickListener(p1 -> {
            browseApk();
        });

        (mView.findViewById(R.id.hookRun)).setOnClickListener(p1 -> {
            hookRun();
        });

        return mView;
    }

    public void browseApk() {
        DialogProperties properties = new DialogProperties();
        properties.selection_mode = DialogConfigs.SINGLE_MODE;
        properties.selection_type = DialogConfigs.FILE_SELECT;
        properties.root = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        properties.error_dir = new File(DialogConfigs.DEFAULT_DIR);
        properties.offset = new File(DialogConfigs.DEFAULT_DIR);
        properties.extensions = new String[]{"apk", "APK"};
        FilePickerDialog dialog = new FilePickerDialog(getContext(), properties);
        dialog.setTitle("Выберите установочный пакет");
        dialog.setDialogSelectionListener(files -> {
            for (String path : files) {
                File file = new File(path);
                if (file.getName().endsWith(".apk") || file.getName().endsWith(".APK")) {
                    apkPath.setText(file.getAbsolutePath());
                } else {
                    toast("Error");
                }
            }
        });
        dialog.show();
    }

    public void hookRun() {
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Обработка...");
        progressDialog.setMessage("Подождите...");
        progressDialog.setIndeterminate(true);
        progressDialog.show();

        @SuppressLint("HandlerLeak")
        Handler mHandler = new Handler() {
            public void handleMessage(Message msg) {
                progressDialog.dismiss();
            }
        };

        new Thread() {
            public void run() {
                Looper.prepare();
                String srcApk = apkPath.getText().toString();
                String outApk = apkPath.getText().toString().replace(".apk", "_kill.apk");

                new File(outApk);
                try {
                    if (App.getPreferences().getBoolean("getBinMtSignatureKill", true)) {
                        BinSignatureTool binSignatureTool = new BinSignatureTool(getContext());
                        binSignatureTool.setPath(srcApk, outApk);
                        binSignatureTool.Kill();
                    } else if (App.getPreferences().getBoolean("getSuperSignatureKill", false)) {
                        SuperSignatureTool signatureTool = new SuperSignatureTool(getContext());
                        signatureTool.setPath(srcApk, outApk);
                        signatureTool.Kill();
                    }
                } catch (Exception e) {
                    toast("Обработка не удалась:" + e.toString());
                } finally {
                    new ApkSigner(getContext()).signApk(outApk, outApk.replace(".apk", "_sign.apk"));
                    dialogFinished();
                }
                mHandler.sendEmptyMessage(0);
                Looper.loop();
            }
        }.start();
    }

    public void toast(String str) {
        Toast.makeText(getContext(), str, Toast.LENGTH_SHORT).show();
    }

    public void dialogFinished() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setTitle("Обработка завершена");
        alertDialog.setMessage("Файл был сохранен в каталоге");
        alertDialog.setCancelable(true);
        alertDialog.setPositiveButton("Ок", null);
        alertDialog.show();
    }
}
