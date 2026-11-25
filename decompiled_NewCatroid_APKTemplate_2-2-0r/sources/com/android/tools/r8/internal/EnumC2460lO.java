package com.android.tools.r8.internal;

/* JADX WARN: $VALUES field not found */
/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX INFO: renamed from: com.android.tools.r8.internal.lO, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class EnumC2460lO implements InterfaceC2880qs {
    public static final EnumC2460lO b = new EnumC2460lO(0, 0, "RETURNS_CONSTANT");
    public static final EnumC2460lO c = new EnumC2460lO(1, 1, "CALLS");
    public static final EnumC2460lO d = new EnumC2460lO(2, 2, "RETURNS_NOT_NULL");
    private final int a;

    private EnumC2460lO(int i, int i2, String str) {
        super(str, i);
        this.a = i2;
    }

    public static EnumC2460lO c(int i) {
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
