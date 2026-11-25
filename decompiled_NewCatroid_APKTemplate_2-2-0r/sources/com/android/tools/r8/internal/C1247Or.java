package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Or, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1247Or extends AbstractC1193Mr {
    static {
        new C1247Or(1, 0);
    }

    public C1247Or(int i, int i2) {
        super(i, i2);
    }

    public final boolean equals(Object obj) {
        if (obj instanceof C1247Or) {
            if (a() > b()) {
                C1247Or c1247Or = (C1247Or) obj;
                if (c1247Or.a() <= c1247Or.b()) {
                }
                return true;
            }
            C1247Or c1247Or2 = (C1247Or) obj;
            if (a() == c1247Or2.a() && b() == c1247Or2.b()) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        if (a() > b()) {
            return -1;
        }
        return (a() * 31) + b();
    }

    public final String toString() {
        return a() + ".." + b();
    }
}
