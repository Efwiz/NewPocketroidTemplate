package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Dx, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C0941Dx extends AbstractC3467yx {
    private final String a;
    private final String b;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C0941Dx(String str, String str2) {
        super(0);
        AbstractC0936Ds.c(str, "enumClassName");
        AbstractC0936Ds.c(str2, "enumEntryName");
        this.a = str;
        this.b = str2;
    }

    public final String a() {
        return this.a;
    }

    public final String b() {
        return this.b;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof C0941Dx)) {
            return false;
        }
        C0941Dx c0941Dx = (C0941Dx) obj;
        return AbstractC0936Ds.a((Object) this.a, (Object) c0941Dx.a) && AbstractC0936Ds.a((Object) this.b, (Object) c0941Dx.b);
    }

    public final int hashCode() {
        return this.b.hashCode() + (this.a.hashCode() * 31);
    }

    public final String toString() {
        return "EnumValue(enumClassName=" + this.a + ", enumEntryName=" + this.b + ')';
    }
}
