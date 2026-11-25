package com.android.tools.r8.internal;

/* JADX WARN: $VALUES field not found */
/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class KO implements InterfaceC2880qs {
    public static final KO b = new KO(0, 0, "CLASS");
    public static final KO c = new KO(1, 1, "PACKAGE");
    public static final KO d = new KO(2, 2, "LOCAL");
    private final int a;

    private KO(int i, int i2, String str) {
        super(str, i);
        this.a = i2;
    }

    public static KO c(int i) {
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
