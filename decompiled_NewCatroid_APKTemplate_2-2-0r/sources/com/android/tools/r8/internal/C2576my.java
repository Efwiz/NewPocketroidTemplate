package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.my, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2576my {
    private final InterfaceC1955ev a;

    public C2576my(C1062Ia c1062Ia) {
        this.a = c1062Ia;
    }

    public final boolean equals(Object obj) {
        return (obj instanceof C2576my) && AbstractC0936Ds.a(this.a, ((C2576my) obj).a);
    }

    public final int hashCode() {
        C1062Ia c1062Ia = (C1062Ia) this.a;
        c1062Ia.getClass();
        return AbstractC2116gu.a(c1062Ia).hashCode();
    }

    public final String toString() {
        InterfaceC1955ev interfaceC1955ev = this.a;
        AbstractC0936Ds.c(interfaceC1955ev, "<this>");
        Class clsA = ((C1062Ia) ((J9) interfaceC1955ev)).a();
        AbstractC0936Ds.a((Object) clsA, "null cannot be cast to non-null type java.lang.Class<T of kotlin.jvm.JvmClassMappingKt.<get-java>>");
        return clsA.getName();
    }
}
