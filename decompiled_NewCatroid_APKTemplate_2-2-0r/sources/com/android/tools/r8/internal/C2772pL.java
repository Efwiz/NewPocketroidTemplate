package com.android.tools.r8.internal;

import java.io.Serializable;

/* JADX INFO: renamed from: com.android.tools.r8.internal.pL, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2772pL implements Serializable {
    private final Object a;
    private final Object b;

    public C2772pL(Object obj, Object obj2) {
        this.a = obj;
        this.b = obj2;
    }

    public final Object a() {
        return this.a;
    }

    public final Object b() {
        return this.b;
    }

    public final Object c() {
        return this.a;
    }

    public final Object d() {
        return this.b;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof C2772pL)) {
            return false;
        }
        C2772pL c2772pL = (C2772pL) obj;
        return AbstractC0936Ds.a(this.a, c2772pL.a) && AbstractC0936Ds.a(this.b, c2772pL.b);
    }

    public final int hashCode() {
        Object obj = this.a;
        int iHashCode = (obj == null ? 0 : obj.hashCode()) * 31;
        Object obj2 = this.b;
        return iHashCode + (obj2 != null ? obj2.hashCode() : 0);
    }

    public final String toString() {
        return "(" + this.a + ", " + this.b + ')';
    }
}
