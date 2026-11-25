package com.android.tools.r8.internal;

/* JADX WARN: $VALUES field not found */
/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class TO implements InterfaceC2880qs {
    public static final TO b = new TO(0, 0, "IN");
    public static final TO c = new TO(1, 1, "OUT");
    public static final TO d = new TO(2, 2, "INV");
    public static final TO e = new TO(3, 3, "STAR");
    private final int a;

    private TO(int i, int i2, String str) {
        super(str, i);
        this.a = i2;
    }

    public static TO c(int i) {
        if (i == 0) {
            return b;
        }
        if (i == 1) {
            return c;
        }
        if (i == 2) {
            return d;
        }
        if (i != 3) {
            return null;
        }
        return e;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2880qs
    public final int a() {
        return this.a;
    }
}
