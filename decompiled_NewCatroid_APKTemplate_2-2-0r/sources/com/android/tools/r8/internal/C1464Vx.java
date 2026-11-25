package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Vx, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1464Vx {
    private final Object a;

    public C1464Vx(Object obj) {
        this.a = obj;
    }

    public final Object a() {
        return this.a;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof C1464Vx) && AbstractC0936Ds.a(this.a, ((C1464Vx) obj).a);
    }

    public final int hashCode() {
        Object obj = this.a;
        if (obj == null) {
            return 0;
        }
        return obj.hashCode();
    }

    public final String toString() {
        return "KmConstantValue(value=" + this.a + ')';
    }
}
