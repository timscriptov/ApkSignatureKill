package com.signs.yowal;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.developer.filepicker.model.DialogConfigs;
import com.developer.filepicker.model.DialogProperties;
import com.developer.filepicker.view.FilePickerDialog;
import com.signs.yowal.ui.Dialogs;
import com.signs.yowal.utils.MyAppInfo;
import com.signs.yowal.utils.Preferences;
import com.tianyu.killer.R;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    public static AppCompatEditText apkPath;
    @SuppressLint("StaticFieldLeak")
    public static Context mContext;
    public static AlertDialog alertDialog;
    private AppCompatImageView apkIcon;
    private AppCompatTextView apkName;
    private AppCompatTextView apkPack;

    public static void toast(String str) {
        Toast.makeText(mContext, str, Toast.LENGTH_SHORT).show();
    }

    public static void dialogFinished() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Обработка завершена");
        builder.setMessage("Файл был сохранен в каталоге, подпишите его");
        builder.setCancelable(true);
        builder.setPositiveButton("Ок", null);
        AlertDialog show = builder.show();
        show.getButton(-1).setOnClickListener(view -> alertDialog.dismiss());
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);
        mContext = this;
        apkIcon = findViewById(R.id.apkIcon);
        apkName = findViewById(R.id.apkName);
        apkPack = findViewById(R.id.apkPackage);
        apkPath = findViewById(R.id.apkPath);

        apkPath.setText(Preferences.isApkPath());
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
                        apkIcon.setImageDrawable(new MyAppInfo(MainActivity.this, apk.getAbsolutePath()).getIcon());
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

        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Settings.ACTION_MANAGE_OVERLAY_PERMISSION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, Settings.ACTION_MANAGE_OVERLAY_PERMISSION}, 1);
            }
        }

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q && !Environment.isExternalStorageManager()) {
            Dialogs.showScopedStorageDialog(this);
        }
    }

    public void ChoiceFile(View view) {
        DialogProperties properties = new DialogProperties();
        properties.selection_mode = DialogConfigs.SINGLE_MODE;
        properties.selection_type = DialogConfigs.FILE_SELECT;
        properties.root = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        properties.error_dir = new File(DialogConfigs.DEFAULT_DIR);
        properties.offset = new File(DialogConfigs.DEFAULT_DIR);
        properties.extensions = new String[]{"apk", "APK"};
        FilePickerDialog dialog = new FilePickerDialog(this, properties);
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

    public void HookFile(View view) {
        final ProgressDialog myProgressDialog = ProgressDialog.show(this, "Обработка...", "Подождите...", true);
        final Handler mHandler = new Handler() {
            public void handleMessage(Message message) {
                myProgressDialog.dismiss();
            }
        };

        new Thread() {
            public void run() {
                Looper.prepare();
                String srcApk = apkPath.getText().toString();
                String outApk = apkPath.getText().toString().replace(".apk", "_kill.apk");

                new File(outApk);
                try {
                    SignatureTool signatureTool = new SignatureTool(mContext);
                    signatureTool.setPath(srcApk, outApk);
                    signatureTool.Kill();
                    toast("Обработка завершена, подпишите самостоятельно" + outApk);
                    dialogFinished();
                } catch (Exception e) {
                    toast("Обработка не удалась:" + e.toString());
                }
                mHandler.sendEmptyMessage(0);
                Looper.loop();
            }
        }.start();
    }
}
