package com.android.tools.r8.internal;

/* JADX WARN: $VALUES field not found */
/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX INFO: renamed from: com.android.tools.r8.internal.nP, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class EnumC2609nP implements InterfaceC2880qs {
    public static final EnumC2609nP b = new EnumC2609nP(0, 0, "LANGUAGE_VERSION");
    public static final EnumC2609nP c = new EnumC2609nP(1, 1, "COMPILER_VERSION");
    public static final EnumC2609nP d = new EnumC2609nP(2, 2, "API_VERSION");
    private final int a;

    private EnumC2609nP(int i, int i2, String str) {
        super(str, i);
        this.a = i2;
    }

    public static EnumC2609nP c(int i) {
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
