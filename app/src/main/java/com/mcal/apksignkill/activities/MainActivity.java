package com.mcal.apksignkill.activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.appbar.MaterialToolbar;
import com.mcal.apksignkill.BuildConfig;
import com.mcal.apksignkill.R;
import com.mcal.apksignkill.adapters.ViewPagerAdapter;
import com.mcal.apksignkill.fragments.HomeFragment;
import com.mcal.apksignkill.utils.DensityUtil;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.main);

        setupToolbar(getString(R.string.app_name));
        viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Settings.ACTION_MANAGE_OVERLAY_PERMISSION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, Settings.ACTION_MANAGE_OVERLAY_PERMISSION}, 1);
            }
        }

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q && !Environment.isExternalStorageManager()) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle(R.string.scoped_storage_title);
            dialog.setMessage(R.string.scoped_storage_msg);
            dialog.setPositiveButton(R.string.settings_title, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION, Uri.parse("package:" + BuildConfig.APPLICATION_ID));
                    startActivity(intent);
                    dialog.cancel();
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("WrongConstant")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.donate) {
            donate();
        } else if (item.getItemId() == R.id.github) {
            String url = "https://github.com/TimScriptov/ApkSignatureKill";
            Intent intent1 = new Intent(Intent.ACTION_VIEW);
            intent1.setData(Uri.parse(url));
            startActivity(intent1);
        } else if (item.getItemId() == R.id.about) {
            about();
        } else if (item.getItemId() == R.id.exit) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void about() {
        Dialog bottomDialog = new Dialog(this, R.style.BottomDialog);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout contentView = new LinearLayout(this);
        contentView.setBackgroundResource(R.drawable.shape_dialog);
        contentView.setOrientation(LinearLayout.VERTICAL);
        contentView.setPadding(40, 0, 40, 0);
        contentView.setLayoutParams(layoutParams);
        final AppCompatTextView msg = new AppCompatTextView(this);
        msg.setText(R.string.about);
        contentView.addView(msg);

        bottomDialog.setContentView(contentView);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) contentView.getLayoutParams();
        params.width = getResources().getDisplayMetrics().widthPixels - DensityUtil.dp2px(this, 16f);
        params.bottomMargin = DensityUtil.dp2px(this, 8f);
        contentView.setLayoutParams(params);
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.show();
    }

    private void donate() {
        Dialog bottomDialog = new Dialog(this, R.style.BottomDialog);

        View contentView = LayoutInflater.from(this).inflate(R.layout.dialog_content_donate_circle, null);
        ClipboardManager cmb = (ClipboardManager)this.getSystemService ( Context.CLIPBOARD_SERVICE );

        (contentView.findViewById(R.id.qiwi)).setOnClickListener(p1 -> {
            cmb.setText("4693 9575 5605 5692");
            Toast.makeText(this, "Скопирован в буфер обмена", Toast.LENGTH_LONG ).show ( );
        });

        (contentView.findViewById(R.id.visa)).setOnClickListener(p1 -> {
            cmb.setText("4276 3200 1538 3012");
            Toast.makeText(this, "Скопирован в буфер обмена", Toast.LENGTH_LONG ).show ( );
        });

        (contentView.findViewById(R.id.paypal)).setOnClickListener(p1 -> {
            String url = "https://www.paypal.me/timscriptov";
            Intent intent1 = new Intent(Intent.ACTION_VIEW);
            intent1.setData(Uri.parse(url));
            startActivity(intent1);
        });

        bottomDialog.setContentView(contentView);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) contentView.getLayoutParams();
        params.width = getResources().getDisplayMetrics().widthPixels - DensityUtil.dp2px(this, 16f);
        params.bottomMargin = DensityUtil.dp2px(this, 8f);
        contentView.setLayoutParams(params);
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.show();
    }

    @SuppressWarnings("ConstantConditions")
    private void setupToolbar(String title) {
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
    }

    private void setupViewPager(@NotNull ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new HomeFragment(), "MAIN");
        viewPager.setAdapter(adapter);
    }
}
