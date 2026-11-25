package com.android.tools.r8.internal;

import com.google.android.gms.cast.MediaError;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;

/* JADX WARN: $VALUES field not found */
/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX INFO: renamed from: com.android.tools.r8.internal.mP, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class EnumC2535mP implements InterfaceC2880qs {
    public static final EnumC2535mP b = new EnumC2535mP(0, 0, "WARNING");
    public static final EnumC2535mP c = new EnumC2535mP(1, 1, MediaError.ERROR_TYPE_ERROR);
    public static final EnumC2535mP d = new EnumC2535mP(2, 2, VirtualFile.PROP_HIDDEN);
    private final int a;

    private EnumC2535mP(int i, int i2, String str) {
        super(str, i);
        this.a = i2;
    }

    public static EnumC2535mP c(int i) {
        if (i == 0) {
            return b;
        }
        if (i == 1) {
            return c;
        }
        if (i != 2) {
            return null;
        }
        return d;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2880qs
    public final int a() {
        return this.a;
    }
}
