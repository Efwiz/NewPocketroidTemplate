package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.i00, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2196i00 extends EZ {
    final /* synthetic */ Class a;
    final /* synthetic */ C2274j00 b;

    C2196i00(C2274j00 c2274j00, Class cls) {
        this.b = c2274j00;
        this.a = cls;
    }

    @Override // com.android.tools.r8.internal.EZ
    public final void a(C1799cu c1799cu, Object obj) {
        this.b.b.a(c1799cu, obj);
    }

    @Override // com.android.tools.r8.internal.EZ
    public final Object a(C1486Wt c1486Wt) {
        Object objA = this.b.b.a(c1486Wt);
        if (objA == null || this.a.isInstance(objA)) {
            return objA;
        }
        throw new C1542Yt("Expected a " + this.a.getName() + " but was " + objA.getClass().getName());
    }
}
