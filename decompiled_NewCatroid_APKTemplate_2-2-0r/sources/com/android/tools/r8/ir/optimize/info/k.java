package com.android.tools.r8.ir.optimize.info;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.internal.AbstractC1154Lf;
import com.android.tools.r8.internal.AbstractC1476Wj;
import com.android.tools.r8.internal.B1;
import com.android.tools.r8.internal.C2892r10;
import com.android.tools.r8.internal.DH;
import java.util.Set;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class k extends e {
    static final /* synthetic */ boolean e = true;
    private int b;
    private B1 a = C2892r10.F();
    private int c = 0;
    private AbstractC1154Lf d = AbstractC1154Lf.n();

    public final k a(C0702m c0702m, AbstractC1476Wj abstractC1476Wj, Set set) {
        this.d = this.d.a(c0702m, abstractC1476Wj, set);
        return this;
    }

    @Override // com.android.tools.r8.ir.optimize.info.g
    public final g b() {
        return this;
    }

    @Override // com.android.tools.r8.ir.optimize.info.g
    public final k c() {
        return this;
    }

    @Override // com.android.tools.r8.ir.optimize.info.e
    public final boolean e() {
        return (this.b & 1) != 0;
    }

    @Override // com.android.tools.r8.ir.optimize.info.e
    public final B1 f() {
        return this.a;
    }

    @Override // com.android.tools.r8.ir.optimize.info.e
    public final AbstractC1154Lf g() {
        return this.d;
    }

    @Override // com.android.tools.r8.ir.optimize.info.e
    public final int h() {
        return this.c;
    }

    @Override // com.android.tools.r8.ir.optimize.info.e
    public final boolean i() {
        return (this.b & 2) != 0;
    }

    @Override // com.android.tools.r8.ir.optimize.info.e
    public final boolean j() {
        return (this.b & 4) != 0;
    }

    final void k() {
        this.b |= 2;
    }

    final void l() {
        this.b |= 4;
    }

    final void m() {
        this.b |= 1;
    }

    public final k n() {
        k kVar = new k();
        kVar.a = this.a;
        kVar.b = this.b;
        kVar.c = this.c;
        kVar.d = this.d;
        return kVar;
    }

    final void a(B1 b1) {
        if (!e && !this.a.isUnknown() && !b1.y()) {
            throw new AssertionError();
        }
        this.a = b1;
    }

    public final k a(C0702m c0702m, DH dh, AbstractC1476Wj abstractC1476Wj) {
        a(this.a.b(c0702m, dh, abstractC1476Wj));
        return this;
    }

    final void a(int i) {
        this.c = i | this.c;
    }

    final void a(AbstractC1154Lf abstractC1154Lf) {
        this.d = abstractC1154Lf;
    }
}
