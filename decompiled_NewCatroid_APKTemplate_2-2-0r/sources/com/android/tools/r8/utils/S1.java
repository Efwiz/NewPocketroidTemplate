package com.android.tools.r8.utils;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0635d2;
import java.util.function.Supplier;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public final class S1 extends X {
    public S1(AbstractC3876c0 abstractC3876c0) {
        super(null, abstractC3876c0);
    }

    public static S1 g() {
        return new S1(null);
    }

    @Override // com.android.tools.r8.utils.X
    final Supplier a(AbstractC0727q0 abstractC0727q0) {
        return (C0635d2) abstractC0727q0;
    }

    @Override // com.android.tools.r8.utils.X
    final com.android.tools.r8.graph.I e() {
        return com.android.tools.r8.graph.I.e;
    }

    @Override // com.android.tools.r8.utils.X
    public final String toString() {
        return "library classes: " + super.toString();
    }

    @Override // com.android.tools.r8.utils.X
    final AbstractC0727q0 a(AbstractC0727q0 abstractC0727q0, AbstractC0727q0 abstractC0727q1) {
        return (C0635d2) abstractC0727q0;
    }
}
