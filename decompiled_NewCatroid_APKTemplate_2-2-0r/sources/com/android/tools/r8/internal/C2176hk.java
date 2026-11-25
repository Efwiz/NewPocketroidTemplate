package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.hk, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2176hk extends AbstractC3325x0 {
    C2328jk a;
    final /* synthetic */ C2254ik b;

    C2176hk(C2254ik c2254ik, C2328jk c2328jk) {
        this.b = c2254ik;
        this.a = c2328jk;
    }

    @Override // java.util.Map.Entry
    public final Object getKey() {
        return this.a.a;
    }

    @Override // java.util.Map.Entry
    public final Object getValue() {
        return this.a.b;
    }

    @Override // com.android.tools.r8.internal.AbstractC3325x0, java.util.Map.Entry
    public final Object setValue(Object obj) {
        Object obj2 = this.a.b;
        int iA = AbstractC0900Ck.a(obj);
        if (iA == this.a.d && AbstractC3428yK.a(obj, obj2)) {
            return obj;
        }
        AbstractC3123uM.a(this.b.f.b(iA, obj) == null, "value already present: %s", obj);
        this.b.f.a(this.a);
        C2328jk c2328jk = this.a;
        C2328jk c2328jk2 = new C2328jk(c2328jk.a, c2328jk.c, obj, iA);
        this.b.f.a(c2328jk2, this.a);
        C2328jk c2328jk3 = this.a;
        c2328jk3.h = null;
        c2328jk3.g = null;
        C2254ik c2254ik = this.b;
        c2254ik.c = c2254ik.f.g;
        C2254ik c2254ik2 = this.b;
        if (c2254ik2.b == this.a) {
            c2254ik2.b = c2328jk2;
        }
        this.a = c2328jk2;
        return obj2;
    }
}
