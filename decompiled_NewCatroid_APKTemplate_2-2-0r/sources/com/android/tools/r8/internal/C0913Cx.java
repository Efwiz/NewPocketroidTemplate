package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Cx, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C0913Cx extends AbstractC1059Hx {
    private final double a;

    public C0913Cx(double d) {
        super(0);
        this.a = d;
    }

    public final Double a() {
        return Double.valueOf(this.a);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof C0913Cx) && Double.compare(this.a, ((C0913Cx) obj).a) == 0;
    }

    public final int hashCode() {
        return Double.hashCode(this.a);
    }

    public final String toString() {
        return "DoubleValue(value=" + this.a + ')';
    }
}
