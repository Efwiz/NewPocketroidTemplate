package org.jetbrains.kotlin.com.intellij.openapi.util.io.win32;

import org.jetbrains.kotlin.com.intellij.openapi.util.io.FileAttributes;
import org.jetbrains.kotlin.com.intellij.util.BitUtil;

/* JADX INFO: loaded from: classes3.dex */
public class FileInfo {
    private int attributes;
    private long length;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private String f716name;
    private long timestamp;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/com/intellij/openapi/util/io/win32/FileInfo", "toFileAttributes"));
    }

    public FileAttributes toFileAttributes() {
        int i = this.attributes;
        if (i == -1) {
            FileAttributes fileAttributes = FileAttributes.BROKEN_SYMLINK;
            if (fileAttributes == null) {
                $$$reportNull$$$0(0);
            }
            return fileAttributes;
        }
        boolean zIsSet = BitUtil.isSet(i, 16);
        boolean zIsSet2 = BitUtil.isSet(this.attributes, 64);
        boolean zIsSet3 = BitUtil.isSet(this.attributes, 1024);
        boolean zIsSet4 = BitUtil.isSet(this.attributes, 2);
        boolean zIsSet5 = BitUtil.isSet(this.attributes, 1);
        return new FileAttributes(zIsSet, zIsSet2, zIsSet3, zIsSet4, this.length, (this.timestamp / 10000) - 11644473600000L, zIsSet || !zIsSet5);
    }

    public String toString() {
        return this.f716name;
    }
}
