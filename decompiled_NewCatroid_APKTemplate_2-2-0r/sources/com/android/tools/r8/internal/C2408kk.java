package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.kk, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2408kk extends AbstractC3325x0 {
    C2328jk a;
    final /* synthetic */ C2484lk b;

    C2408kk(C2484lk c2484lk, C2328jk c2328jk) {
        this.b = c2484lk;
        this.a = c2328jk;
    }

    @Override // java.util.Map.Entry
    public final Object getKey() {
        return this.a.b;
    }

    @Override // java.util.Map.Entry
    public final Object getValue() {
        return this.a.a;
    }

    @Override // com.android.tools.r8.internal.AbstractC3325x0, java.util.Map.Entry
    public final Object setValue(Object obj) {
        Object obj2 = this.a.a;
        int iA = AbstractC0900Ck.a(obj);
        if (iA == this.a.c && AbstractC3428yK.a(obj, obj2)) {
            return obj;
        }
        AbstractC3123uM.a(this.b.f.a.a(iA, obj) == null, "value already present: %s", obj);
        this.b.f.a.a(this.a);
        C2328jk c2328jk = this.a;
        C2328jk c2328jk2 = new C2328jk(obj, iA, c2328jk.b, c2328jk.d);
        this.a = c2328jk2;
        this.b.f.a.a(c2328jk2, (C2328jk) null);
        C2484lk c2484lk = this.b;
        c2484lk.c = c2484lk.f.a.g;
        return obj2;
    }
}
