package com.android.tools.r8.internal;

/* JADX WARN: $VALUES field not found */
/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX INFO: renamed from: com.android.tools.r8.internal.Dk, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class EnumC0928Dk {
    public static final EnumC0928Dk a = new EnumC0928Dk("INITIAL", 0);
    public static final EnumC0928Dk b = new EnumC0928Dk("FINAL", 1);

    private EnumC0928Dk(String str, int i) {
        super(str, i);
    }

    public final boolean a() {
        return this == b;
    }

    public final boolean b() {
        return this == a;
    }
}
