package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0767v2;

/* JADX INFO: renamed from: com.android.tools.r8.internal.ge, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC2094ge extends DH {
    public AbstractC2094ge(C0702m c0702m) {
        this(c0702m.a(), c0702m.E());
    }

    @Override // com.android.tools.r8.internal.DH
    protected KE a(KE ke, C0684j2 c0684j2) {
        return ke;
    }

    @Override // com.android.tools.r8.internal.DH
    protected C1237Oh b(C1237Oh c1237Oh) {
        return c1237Oh;
    }

    @Override // com.android.tools.r8.internal.AbstractC1476Wj
    public com.android.tools.r8.graph.proto.k d(AbstractC1476Wj abstractC1476Wj, C0684j2 c0684j2) {
        if (this == abstractC1476Wj) {
            return com.android.tools.r8.graph.proto.k.h();
        }
        return k().d((AbstractC1476Wj) null, f(c0684j2));
    }

    @Override // com.android.tools.r8.internal.DH
    protected C0767v2 e(C0767v2 c0767v2) {
        return c0767v2;
    }

    @Override // com.android.tools.r8.internal.DH
    public C0684j2 f(C0684j2 c0684j2) {
        return c0684j2;
    }

    @Override // com.android.tools.r8.internal.AbstractC1476Wj
    public boolean g() {
        return k().g();
    }

    public C0684j2 h(C0684j2 c0684j2) {
        return c0684j2;
    }

    public AbstractC2094ge(C0722p1 c0722p1, AbstractC1476Wj abstractC1476Wj) {
        super(c0722p1, abstractC1476Wj);
    }

    @Override // com.android.tools.r8.internal.AbstractC1476Wj
    public C0767v2 a(C0767v2 c0767v2) {
        return k().a(c0767v2);
    }

    @Override // com.android.tools.r8.internal.AbstractC1476Wj
    public Iterable b(C0767v2 c0767v2) {
        return k().b(c0767v2);
    }

    @Override // com.android.tools.r8.internal.AbstractC1476Wj
    public com.android.tools.r8.graph.Y0 a(com.android.tools.r8.graph.Y0 y0) {
        return k().a(y0);
    }

    @Override // com.android.tools.r8.internal.AbstractC1476Wj
    /* JADX INFO: renamed from: b */
    public C0684j2 c(AbstractC1476Wj abstractC1476Wj, C0684j2 c0684j2) {
        return this == abstractC1476Wj ? c0684j2 : h(k().c((AbstractC1476Wj) null, c0684j2));
    }

    @Override // com.android.tools.r8.internal.AbstractC1476Wj
    /* JADX INFO: renamed from: a */
    public com.android.tools.r8.graph.Y0 b(AbstractC1476Wj abstractC1476Wj, com.android.tools.r8.graph.Y0 y0) {
        return this == abstractC1476Wj ? y0 : k().b((AbstractC1476Wj) null, y0);
    }
}
