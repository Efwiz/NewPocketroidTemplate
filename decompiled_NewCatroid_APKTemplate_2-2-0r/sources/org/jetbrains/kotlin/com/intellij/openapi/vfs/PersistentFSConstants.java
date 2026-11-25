package org.jetbrains.kotlin.com.intellij.openapi.vfs;

import org.jetbrains.kotlin.com.intellij.openapi.util.io.FileUtilRt;

/* JADX INFO: loaded from: classes3.dex */
public final class PersistentFSConstants {
    public static final long FILE_LENGTH_TO_CACHE_THRESHOLD;
    private static int ourMaxIntellisenseFileSize;

    static {
        long j = FileUtilRt.LARGE_FOR_CONTENT_LOADING;
        FILE_LENGTH_TO_CACHE_THRESHOLD = j;
        ourMaxIntellisenseFileSize = Math.min(FileUtilRt.getUserFileSizeLimit(), (int) j);
    }

    public static int getMaxIntellisenseFileSize() {
        return ourMaxIntellisenseFileSize;
    }

    private PersistentFSConstants() {
    }
}
