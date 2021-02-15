package com.signs.yowal.activities;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.mcal.apkkiller.R;
import com.signs.yowal.adapters.ViewPagerAdapter;
import com.signs.yowal.fragments.HomeFragment;
import com.signs.yowal.ui.Dialogs;
import com.signs.yowal.utils.ExceptionHandler;
import com.signs.yowal.view.CenteredToolBar;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {
    private CenteredToolBar toolbar;
    private ViewPager viewPager;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
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
            Dialogs.showScopedStorageDialog(this);
        }
    }

    @SuppressWarnings("ConstantConditions")
    private void setupToolbar(String title) {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
    }

    private void setupViewPager(@NotNull ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new HomeFragment(), "MAIN");
        viewPager.setAdapter(adapter);
    }
}
