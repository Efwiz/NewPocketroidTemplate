package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Nx, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1226Nx extends AbstractC1059Hx {
    private final long a;

    public /* synthetic */ C1226Nx(long j, int i) {
        this(j);
    }

    public final long a() {
        return this.a;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof C1226Nx) && this.a == ((C1226Nx) obj).a;
    }

    public final int hashCode() {
        return U00.a(this.a);
    }

    public final String toString() {
        String strConcat;
        StringBuilder sb = new StringBuilder("ULongValue(value=");
        long j = this.a;
        if (j >= 0) {
            AbstractC2443l9.a();
            strConcat = Long.toString(j, 10);
            AbstractC0936Ds.b(strConcat, "toString(this, checkRadix(radix))");
        } else {
            long j2 = 10;
            long j3 = ((j >>> 1) / j2) << 1;
            long j4 = j - (j3 * j2);
            if (j4 >= j2) {
                j4 -= j2;
                j3++;
            }
            AbstractC2443l9.a();
            String string = Long.toString(j3, 10);
            AbstractC0936Ds.b(string, "toString(this, checkRadix(radix))");
            AbstractC2443l9.a();
            String string2 = Long.toString(j4, 10);
            AbstractC0936Ds.b(string2, "toString(this, checkRadix(radix))");
            strConcat = string.concat(string2);
        }
        return sb.append((Object) strConcat).append(')').toString();
    }

    private C1226Nx(long j) {
        super(0);
        this.a = j;
    }
}
