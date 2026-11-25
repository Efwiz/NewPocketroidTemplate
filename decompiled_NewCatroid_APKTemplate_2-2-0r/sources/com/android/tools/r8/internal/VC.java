package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.naming.AbstractC3649o0;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class VC extends D20 {
    static final VC c = new VC();

    VC() {
    }

    @Override // com.android.tools.r8.internal.D20
    public final /* bridge */ /* synthetic */ D20 T() {
        return WC.d;
    }

    @Override // com.android.tools.r8.internal.D20
    public final /* bridge */ /* synthetic */ D20 U() {
        return c;
    }

    @Override // com.android.tools.r8.internal.AbstractC2826q4, com.android.tools.r8.internal.InterfaceC1348Si
    public C0767v2 a(C0722p1 c0722p1) {
        return c0722p1.B1;
    }

    @Override // com.android.tools.r8.internal.VM
    public String getTypeName() {
        return "long";
    }

    @Override // com.android.tools.r8.internal.AbstractC2826q4, com.android.tools.r8.internal.InterfaceC1348Si
    public boolean n() {
        return this instanceof WC;
    }

    @Override // com.android.tools.r8.internal.AbstractC2826q4, com.android.tools.r8.internal.InterfaceC1348Si
    public boolean w() {
        return !(this instanceof WC);
    }

    @Override // com.android.tools.r8.internal.InterfaceC1348Si
    public Object a(AbstractC1476Wj abstractC1476Wj, AbstractC3649o0 abstractC3649o0) {
        return JK.e;
    }
}
