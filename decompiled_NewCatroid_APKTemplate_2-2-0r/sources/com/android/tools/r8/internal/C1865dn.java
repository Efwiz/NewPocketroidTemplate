package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.dn, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1865dn {
    private final int a;
    private final Object b;

    public C1865dn(int i, Object obj) {
        this.a = i;
        this.b = obj;
    }

    public final int a() {
        return this.a;
    }

    public final Object b() {
        return this.b;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof C1865dn)) {
            return false;
        }
        C1865dn c1865dn = (C1865dn) obj;
        return this.a == c1865dn.a && AbstractC0936Ds.a(this.b, c1865dn.b);
    }

    public final int hashCode() {
        int iHashCode = Integer.hashCode(this.a) * 31;
        Object obj = this.b;
        return iHashCode + (obj == null ? 0 : obj.hashCode());
    }

    public final String toString() {
        return "IndexedValue(index=" + this.a + ", value=" + this.b + ')';
    }
}
