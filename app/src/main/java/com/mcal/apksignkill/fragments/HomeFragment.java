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
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import com.developer.filepicker.model.DialogConfigs;
import com.developer.filepicker.model.DialogProperties;
import com.developer.filepicker.view.FilePickerDialog;
import com.mcal.apksignkill.R;
import com.mcal.apksignkill.signer.SignatureTool;
import com.mcal.apksignkill.ui.CustomSignDialog;
import com.mcal.apksignkill.utils.BinSignatureTool;
import com.mcal.apksignkill.utils.DensityUtil;
import com.mcal.apksignkill.utils.MyAppInfo;
import com.mcal.apksignkill.utils.Preferences;
import com.mcal.apksignkill.utils.SuperSignatureTool;

import org.jetbrains.annotations.NotNull;

import java.io.File;

public class HomeFragment extends Fragment {
    public static AppCompatEditText apkPath;
    private AppCompatImageView apkIcon;
    private AppCompatTextView apkName;
    private AppCompatTextView apkPack;
    private AppCompatCheckBox binSignatureTool;
    private AppCompatCheckBox superSignatureTool;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.activity_main, container, false);

        apkIcon = mView.findViewById(R.id.apkIcon);
        apkName = mView.findViewById(R.id.apkName);
        apkPack = mView.findViewById(R.id.apkPackage);
        apkPath = mView.findViewById(R.id.apkPath);

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

        binSignatureTool = mView.findViewById(R.id.binMtSignatureKill);
        binSignatureTool.setChecked(Preferences.getBinMtSignatureKill());
        binSignatureTool.setOnCheckedChangeListener((p1, p2) -> {
            Preferences.setBinMtSignatureKill(p2);
        });

        superSignatureTool = mView.findViewById(R.id.superSignatureKill);
        superSignatureTool.setChecked(Preferences.getSuperSignatureKill());
        superSignatureTool.setOnCheckedChangeListener((p1, p2) -> {
            Preferences.setSuperSignatureKill(p2);
        });

        (mView.findViewById(R.id.browseApk)).setOnClickListener(p1 -> {
            browseApk();
        });

        (mView.findViewById(R.id.hookRun)).setOnClickListener(p1 -> {
            runProcess();
        });

        (mView.findViewById(R.id.menu)).setOnClickListener(p1 -> {
            show2();
        });

        return mView;
    }

    private void runProcess() {
        if (Preferences.isCustomSignature()) {
            new CustomSignDialog((p1, p2) -> hookRun()).show(getContext());
        } else {
            hookRun();
        }
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

    private void show2() {
        Dialog bottomDialog = new Dialog(getContext(), R.style.BottomDialog);
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_content_circle, null);

        (contentView.findViewById(R.id.donate)).setOnClickListener(p1 -> {
            donate();
        });

        (contentView.findViewById(R.id.git)).setOnClickListener(p1 -> {
            String url = "https://github.com/TimScriptov/ApkSignatureKill";
            Intent intent1 = new Intent(Intent.ACTION_VIEW);
            intent1.setData(Uri.parse(url));
            startActivity(intent1);
        });

        (contentView.findViewById(R.id.about)).setOnClickListener(p1 -> {
            about();
        });

        (contentView.findViewById(R.id.exit)).setOnClickListener(p1 -> {
            System.exit(0);
        });

        bottomDialog.setContentView(contentView);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) contentView.getLayoutParams();
        params.width = getResources().getDisplayMetrics().widthPixels - DensityUtil.dp2px(getContext(), 16f);
        params.bottomMargin = DensityUtil.dp2px(getContext(), 8f);
        contentView.setLayoutParams(params);
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.show();
    }

    private void about() {
        Dialog bottomDialog = new Dialog(getContext(), R.style.BottomDialog);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout contentView = new LinearLayout(getActivity());
        contentView.setBackgroundResource(R.drawable.shape_dialog);
        contentView.setOrientation(LinearLayout.VERTICAL);
        contentView.setPadding(40, 0, 40, 0);
        contentView.setLayoutParams(layoutParams);
        final AppCompatTextView msg = new AppCompatTextView(getContext());
        msg.setText(R.string.about);
        contentView.addView(msg);

        bottomDialog.setContentView(contentView);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) contentView.getLayoutParams();
        params.width = getResources().getDisplayMetrics().widthPixels - DensityUtil.dp2px(getContext(), 16f);
        params.bottomMargin = DensityUtil.dp2px(getContext(), 8f);
        contentView.setLayoutParams(params);
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.show();
    }

    private void donate() {
        Dialog bottomDialog = new Dialog(getContext(), R.style.BottomDialog);

        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_content_donate_circle, null);
        ClipboardManager cmb = (ClipboardManager)getContext().getSystemService ( Context.CLIPBOARD_SERVICE );

        (contentView.findViewById(R.id.qiwi)).setOnClickListener(p1 -> {
            cmb.setText("4693 9575 5605 5692");
            Toast.makeText(getContext(), "Скопирован в буфер обмена", Toast.LENGTH_LONG ).show ( );
        });

        (contentView.findViewById(R.id.visa)).setOnClickListener(p1 -> {
            cmb.setText("4276 3200 1538 3012");
            Toast.makeText(getContext(), "Скопирован в буфер обмена", Toast.LENGTH_LONG ).show ( );
        });

        (contentView.findViewById(R.id.paypal)).setOnClickListener(p1 -> {
            String url = "https://www.paypal.me/timscriptov";
            Intent intent1 = new Intent(Intent.ACTION_VIEW);
            intent1.setData(Uri.parse(url));
            startActivity(intent1);
        });

        bottomDialog.setContentView(contentView);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) contentView.getLayoutParams();
        params.width = getResources().getDisplayMetrics().widthPixels - DensityUtil.dp2px(getContext(), 16f);
        params.bottomMargin = DensityUtil.dp2px(getContext(), 8f);
        contentView.setLayoutParams(params);
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.show();
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
                    if (Preferences.getBinMtSignatureKill()) {
                        BinSignatureTool binSignatureTool = new BinSignatureTool(getContext());
                        binSignatureTool.setPath(srcApk, outApk);
                        binSignatureTool.Kill();
                    } else if (Preferences.getSuperSignatureKill()) {
                        SuperSignatureTool signatureTool = new SuperSignatureTool(getContext());
                        signatureTool.setPath(srcApk, outApk);
                        signatureTool.Kill();
                    }
                } catch (Exception e) {
                    toast("Обработка не удалась:" + e.toString());
                } finally {
                    if (SignatureTool.sign(getContext(), new File(outApk), new File(outApk.replace(".apk", "_sign.apk")))) {
                        dialogFinished();
                    }
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
