package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Gx, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1028Gx extends AbstractC3467yx {
    private final String a;
    private final int b;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C1028Gx(String str, int i) {
        super(0);
        AbstractC0936Ds.c(str, "className");
        this.a = str;
        this.b = i;
    }

    public final int a() {
        return this.b;
    }

    public final String b() {
        return this.a;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof C1028Gx)) {
            return false;
        }
        C1028Gx c1028Gx = (C1028Gx) obj;
        return AbstractC0936Ds.a((Object) this.a, (Object) c1028Gx.a) && this.b == c1028Gx.b;
    }

    public final int hashCode() {
        return Integer.hashCode(this.b) + (this.a.hashCode() * 31);
    }

    public final String toString() {
        return "KClassValue(className=" + this.a + ", arrayDimensionCount=" + this.b + ')';
    }
}
