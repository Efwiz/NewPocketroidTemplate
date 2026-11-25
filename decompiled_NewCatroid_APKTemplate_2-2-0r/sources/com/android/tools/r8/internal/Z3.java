package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import java.util.Collection;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class Z3 extends AbstractC1739c4 {
    private final InterfaceC1665b4 b;

    Z3(C0684j2 c0684j2, InterfaceC1665b4 interfaceC1665b4) {
        super(c0684j2);
        this.b = interfaceC1665b4;
    }

    @Override // com.android.tools.r8.internal.AbstractC1739c4
    public final Collection a(I7 i7, C0702m c0702m, W3 w3, C1230Ob c1230Ob, AC ac) {
        return this.b.a(i7, c0702m.a(), ac);
    }
}
