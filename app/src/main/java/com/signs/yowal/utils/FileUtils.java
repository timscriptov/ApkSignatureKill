package com.signs.yowal.utils;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class FileUtils {
    public static void writeInt(byte @NotNull [] data, int off, int value) {
        data[off++] = (byte) (value & 0xFF);
        data[off++] = (byte) ((value >>> 8) & 0xFF);
        data[off++] = (byte) ((value >>> 16) & 0xFF);
        data[off] = (byte) ((value >>> 24) & 0xFF);
    }

    @Contract(pure = true)
    public static int readInt(byte @NotNull [] data, int off) {
        return data[off + 3] << 24 | (data[off + 2] & 0xFF) << 16 | (data[off + 1] & 0xFF) << 8
                | data[off] & 0xFF;
    }
}
