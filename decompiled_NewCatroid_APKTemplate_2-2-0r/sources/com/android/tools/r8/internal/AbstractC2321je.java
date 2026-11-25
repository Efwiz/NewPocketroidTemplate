package com.android.tools.r8.internal;

import com.google.android.gms.cast.MediaError;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;

/* JADX INFO: renamed from: com.android.tools.r8.internal.je, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract /* synthetic */ class AbstractC2321je {
    public static /* synthetic */ String a(int i) {
        if (i == 1) {
            return "WARNING";
        }
        if (i == 2) {
            return MediaError.ERROR_TYPE_ERROR;
        }
        return i == 3 ? VirtualFile.PROP_HIDDEN : "null";
    }
}
