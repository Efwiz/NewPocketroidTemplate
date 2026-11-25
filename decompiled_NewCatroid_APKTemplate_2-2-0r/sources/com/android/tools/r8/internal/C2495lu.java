package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.lu, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2495lu extends AbstractC2571mu {
    private final String a;
    private final String b;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C2495lu(String str, String str2) {
        super(0);
        AbstractC0936Ds.c(str, "name");
        AbstractC0936Ds.c(str2, "desc");
        this.a = str;
        this.b = str2;
    }

    @Override // com.android.tools.r8.internal.AbstractC2571mu
    public final String a() {
        return this.a + this.b;
    }

    public final String b() {
        return this.b;
    }

    public final String c() {
        return this.a;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof C2495lu)) {
            return false;
        }
        C2495lu c2495lu = (C2495lu) obj;
        return AbstractC0936Ds.a((Object) this.a, (Object) c2495lu.a) && AbstractC0936Ds.a((Object) this.b, (Object) c2495lu.b);
    }

    public final int hashCode() {
        return this.b.hashCode() + (this.a.hashCode() * 31);
    }
}
