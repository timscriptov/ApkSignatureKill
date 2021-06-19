package com.mcal.apksignkill.utils;

import android.app.Activity;
import android.util.DisplayMetrics;

import org.jetbrains.annotations.NotNull;

public class ScreenUtil {

    public static @NotNull DisplayMetrics getDisplayMetrics(@NotNull Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics;
    }

    public static int getScreenWidth(@NotNull Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }

    public static int getScreenHeight(@NotNull Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.heightPixels;
    }

    /**
     * Получение высоты строки состояния системы
     *
     * @param activity activity
     * @return Высота строки состояния
     */
    public static int getStatusBarHeight(Activity activity) {
        int statusBarHeight = DensityUtil.dp2px(activity, 25);
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = activity.getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }
}