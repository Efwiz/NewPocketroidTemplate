package com.android.tools.r8.internal;

/* JADX WARN: $VALUES field not found */
/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX INFO: renamed from: com.android.tools.r8.internal.Vu, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class EnumC1461Vu implements InterfaceC2880qs {
    public static final EnumC1461Vu b = new EnumC1461Vu(0, 0, "NONE");
    public static final EnumC1461Vu c = new EnumC1461Vu(1, 1, "INTERNAL_TO_CLASS_ID");
    public static final EnumC1461Vu d = new EnumC1461Vu(2, 2, "DESC_TO_CLASS_ID");
    private final int a;

    private EnumC1461Vu(int i, int i2, String str) {
        super(str, i);
        this.a = i2;
    }

    public static EnumC1461Vu c(int i) {
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
