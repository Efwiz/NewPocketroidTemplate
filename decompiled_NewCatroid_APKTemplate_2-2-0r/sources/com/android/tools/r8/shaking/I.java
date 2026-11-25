package com.android.tools.r8.shaking;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class I {
    static I c = new I(0);
    static I d = new I(2);
    static I e = new I(4);
    private final I a;
    private final int b;

    private I(int i) {
        this.b = i;
        this.a = a() ? this : new I(i | 1);
    }

    final boolean a() {
        return (this.b & 1) != 0;
    }

    final boolean b() {
        return (this.b & 2) != 0;
    }

    final boolean c() {
        return (this.b & 4) != 0;
    }

    public final I d() {
        return this.a;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && I.class == obj.getClass() && this.b == ((I) obj).b;
    }

    public final int hashCode() {
        return this.b;
    }
}
