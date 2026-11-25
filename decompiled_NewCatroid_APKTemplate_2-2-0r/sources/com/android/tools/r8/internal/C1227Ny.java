package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Ny, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1227Ny {
    public static final C1227Ny c = new C1227Ny(null, null);
    private EnumC1364Sy a;
    private C0942Dy b;

    public C1227Ny(EnumC1364Sy enumC1364Sy, C0942Dy c0942Dy) {
        this.a = enumC1364Sy;
        this.b = c0942Dy;
    }

    public final EnumC1364Sy a() {
        return this.a;
    }

    public final C0942Dy b() {
        return this.b;
    }

    public final C0942Dy c() {
        return this.b;
    }

    public final EnumC1364Sy d() {
        return this.a;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof C1227Ny)) {
            return false;
        }
        C1227Ny c1227Ny = (C1227Ny) obj;
        return this.a == c1227Ny.a && AbstractC0936Ds.a(this.b, c1227Ny.b);
    }

    public final int hashCode() {
        EnumC1364Sy enumC1364Sy = this.a;
        int iHashCode = (enumC1364Sy == null ? 0 : enumC1364Sy.hashCode()) * 31;
        C0942Dy c0942Dy = this.b;
        return iHashCode + (c0942Dy != null ? c0942Dy.hashCode() : 0);
    }

    public final String toString() {
        return "KmTypeProjection(variance=" + this.a + ", type=" + this.b + ')';
    }
}
