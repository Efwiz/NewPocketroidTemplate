package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Kx, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1146Kx extends AbstractC1059Hx {
    private final String a;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C1146Kx(String str) {
        super(0);
        AbstractC0936Ds.c(str, "value");
        this.a = str;
    }

    public final String a() {
        return this.a;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof C1146Kx) && AbstractC0936Ds.a((Object) this.a, (Object) ((C1146Kx) obj).a);
    }

    public final int hashCode() {
        return this.a.hashCode();
    }

    public final String toString() {
        return "StringValue(value=" + this.a + ')';
    }
}
