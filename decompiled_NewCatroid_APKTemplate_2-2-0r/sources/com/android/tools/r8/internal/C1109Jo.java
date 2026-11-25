package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Jo, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1109Jo extends AbstractC1244Oo implements InterfaceC1912eK {
    private C1190Mo g;
    final /* synthetic */ C1301Qo h;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    private C1109Jo(C1301Qo c1301Qo) {
        super(c1301Qo, 0);
        this.h = c1301Qo;
    }

    @Override // java.util.Iterator
    public final Object next() {
        C1190Mo c1190Mo = new C1190Mo(this.h, a());
        this.g = c1190Mo;
        return c1190Mo;
    }

    @Override // com.android.tools.r8.internal.AbstractC1244Oo, java.util.Iterator
    public final void remove() {
        super.remove();
        this.g.a = -1;
    }

    /* synthetic */ C1109Jo(C1301Qo c1301Qo, int i) {
        this(c1301Qo);
    }
}
