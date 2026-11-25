package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.huawei.hms.ml.language.common.utils.Constant;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class GH extends AbstractC1380Tn {
    static final /* synthetic */ boolean e = true;
    private final int a;
    private final AbstractC1049Hn b;
    private final AbstractC2744p c;
    private final C0684j2 d;

    private GH(int i, AbstractC1049Hn abstractC1049Hn, AbstractC2744p abstractC2744p, C0684j2 c0684j2) {
        if (!e) {
            a(i);
        }
        this.a = i;
        this.b = abstractC1049Hn;
        this.c = abstractC2744p;
        this.d = c0684j2;
    }

    @Override // com.android.tools.r8.internal.AbstractC1380Tn
    public final GH a() {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC1380Tn
    public final AbstractC1049Hn b() {
        return this.b;
    }

    @Override // com.android.tools.r8.internal.AbstractC1380Tn
    public final C0684j2 c() {
        return this.d;
    }

    @Override // com.android.tools.r8.internal.AbstractC1380Tn
    public final boolean d() {
        return (this.a & 1) == 0;
    }

    @Override // com.android.tools.r8.internal.AbstractC1380Tn
    public final boolean e() {
        return (this.a & 2) == 0;
    }

    @Override // com.android.tools.r8.internal.AbstractC1380Tn
    public final AbstractC2744p f() {
        return this.c;
    }

    @Override // com.android.tools.r8.internal.AbstractC1380Tn
    public final boolean g() {
        return (this.a & 4) != 0;
    }

    public final String toString() {
        return "NonTrivialInstanceInitializerInfo(" + this.b + Constant.AFTER_QUTO;
    }

    private static void a(int i) {
        if (!e && (i & (-8)) != 0) {
            throw new AssertionError();
        }
    }

    public final GH a(C0702m c0702m, com.android.tools.r8.graph.proto.c cVar) {
        return new GH(this.a, this.b.a(cVar), this.c.a(c0702m, cVar), this.d);
    }

    public final GH a(C0702m c0702m, DH dh, AbstractC1476Wj abstractC1476Wj, com.android.tools.r8.graph.u5 u5Var) {
        return new GH(this.a, this.b.a(c0702m, dh, abstractC1476Wj), this.c.a(c0702m, dh, u5Var), dh.c(abstractC1476Wj, this.d));
    }
}
