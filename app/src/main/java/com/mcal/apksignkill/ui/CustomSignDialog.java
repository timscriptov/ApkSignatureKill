package com.mcal.apksignkill.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AlertDialog;

import com.developer.filepicker.model.DialogConfigs;
import com.developer.filepicker.model.DialogProperties;
import com.developer.filepicker.view.FilePickerDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.mcal.apksignkill.App;
import com.mcal.apksignkill.R;
import com.mcal.apksignkill.utils.Preferences;
import com.mcal.apksignkill.utils.ScopedStorage;

import org.jetbrains.annotations.Contract;

import java.io.File;

public class CustomSignDialog implements DialogInterface.OnClickListener {
    private TextInputEditText keyStorePath;
    private FilePickerDialog pickerDialog;
    private DialogInterface.OnClickListener listener;

    @Contract(pure = true)
    public CustomSignDialog(DialogInterface.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        if (i == DialogInterface.BUTTON_NEUTRAL) {
            pickerDialog.show();
        }
    }

    public void show(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        @SuppressLint("InflateParams") View view = LayoutInflater.from(context).inflate(R.layout.dialog_select_keystore, null);

        keyStorePath = view.findViewById(R.id.keystore_path);
        TextInputEditText keyStoreAlias = view.findViewById(R.id.keystore_alias);
        TextInputEditText keyStorePass = view.findViewById(R.id.keystore_pass);
        TextInputEditText certPassword = view.findViewById(R.id.cert_password);

        keyStorePath.setText(Preferences.isSignaturePath());
        keyStorePath.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence p1, int p2, int p3, int p4) {
            }

            @Override
            public void onTextChanged(CharSequence p1, int p2, int p3, int p4) {
            }

            @Override
            public void afterTextChanged(Editable p1) {
                App.getPreferences().edit().putString("signaturePath", p1.toString()).apply();
            }
        });

        keyStoreAlias.setText(Preferences.isSignatureAlias());
        keyStoreAlias.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence p1, int p2, int p3, int p4) {
            }

            @Override
            public void onTextChanged(CharSequence p1, int p2, int p3, int p4) {
            }

            @Override
            public void afterTextChanged(Editable p1) {
                App.getPreferences().edit().putString("signatureAlias", p1.toString()).apply();
            }
        });

        keyStorePass.setText(Preferences.isSignaturePassword());
        keyStorePass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence p1, int p2, int p3, int p4) {
            }

            @Override
            public void onTextChanged(CharSequence p1, int p2, int p3, int p4) {
            }

            @Override
            public void afterTextChanged(Editable p1) {
                App.getPreferences().edit().putString("signaturePassword", p1.toString()).apply();
            }
        });

        certPassword.setText(Preferences.isCertPassword());
        certPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence p1, int p2, int p3, int p4) {
            }

            @Override
            public void onTextChanged(CharSequence p1, int p2, int p3, int p4) {
            }

            @Override
            public void afterTextChanged(Editable p1) {
                App.getPreferences().edit().putString("certPassword", p1.toString()).apply();
            }
        });

        DialogProperties properties = new DialogProperties();
        properties.selection_mode = DialogConfigs.SINGLE_MODE;
        properties.selection_type = DialogConfigs.FILE_SELECT;
        properties.root = new File(ScopedStorage.getStorageDirectory().getAbsolutePath());
        properties.extensions = new String[]{".keystore", ".KEYSTORE", ".jks", ".JKS"};

        pickerDialog = new FilePickerDialog(context, properties, R.style.AlertDialogTheme);
        pickerDialog.setTitle("Select keystore");
        pickerDialog.setPositiveBtnName("Select");
        pickerDialog.setNegativeBtnName("Cancel");

        final AlertDialog dialog = builder.setTitle("Custom keystore")
                .setNeutralButton("Select", this)
                .setPositiveButton("Save", listener)
                .setNegativeButton("Cancel", null)
                .setView(view)
                .create();

        pickerDialog.setDialogSelectionListener(files -> {
            for (String path : files) {
                File file = new File(path);
                keyStorePath.setText(file.getAbsolutePath());
                pickerDialog.dismiss();
                dialog.show();
            }
        });
        dialog.show();
    }
}