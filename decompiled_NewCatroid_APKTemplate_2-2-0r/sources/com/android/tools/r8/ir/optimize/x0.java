package com.android.tools.r8.ir.optimize;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.internal.AbstractC2412ko;
import com.android.tools.r8.internal.AbstractC3472z00;
import com.android.tools.r8.internal.InterfaceC2641no;
import com.android.tools.r8.internal.S10;
import com.android.tools.r8.internal.uX$$ExternalSyntheticLambda0;
import com.huawei.hms.ml.language.common.utils.Constant;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class x0 implements z0 {
    private final S10 a;
    final /* synthetic */ B0 b;

    private x0(B0 b0, S10 s10) {
        this.b = b0;
        this.a = s10;
    }

    @Override // com.android.tools.r8.ir.optimize.z0
    public final x0 a() {
        return this;
    }

    public final String toString() {
        return "ExistingValue(v" + this.a.u() + Constant.AFTER_QUTO;
    }

    @Override // com.android.tools.r8.ir.optimize.z0
    public final void a(AbstractC2412ko abstractC2412ko, InterfaceC2641no interfaceC2641no) {
        this.b.f.addAll(abstractC2412ko.b().a());
        abstractC2412ko.b().g(this.a);
        interfaceC2641no.s();
        this.a.g0().forEach(new uX$$ExternalSyntheticLambda0());
    }

    @Override // com.android.tools.r8.ir.optimize.z0
    public final AbstractC3472z00 a(C0702m c0702m, AbstractC3472z00 abstractC3472z00) {
        return this.a.w();
    }
}
