package com.android.apksigner.utils;

import androidx.annotation.NonNull;

import java.io.File;

public class FileUtils {
    public static boolean moveFile(@NonNull File sourcePath, File targetPath) {
        //Files.move(tmpOutputApk.toPath(), outputApk.toPath(), StandardCopyOption.REPLACE_EXISTING);
        return sourcePath.renameTo(targetPath);
    }
}
