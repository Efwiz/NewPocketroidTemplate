package com.android.tools.r8.internal;

import com.google.android.gms.cast.MediaError;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;

/* JADX WARN: $VALUES field not found */
/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX INFO: renamed from: com.android.tools.r8.internal.Vy, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class EnumC1465Vy {
    public static final EnumC1465Vy a = new EnumC1465Vy("WARNING", 0);
    public static final EnumC1465Vy b = new EnumC1465Vy(MediaError.ERROR_TYPE_ERROR, 1);
    public static final EnumC1465Vy c = new EnumC1465Vy(VirtualFile.PROP_HIDDEN, 2);

    private EnumC1465Vy(String str, int i) {
        super(str, i);
    }
}
