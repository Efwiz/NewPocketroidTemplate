package com.android.tools.r8.internal;

import java.util.Iterator;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;

/* JADX INFO: renamed from: com.android.tools.r8.internal.lG, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2451lG {
    private final C3392xs a = new C3392xs(null);

    public final int a(C2461lP c2461lP) {
        return this.a.b(new XY(c2461lP));
    }

    public final AbstractC1045Hj a() {
        if (this.a.b()) {
            return null;
        }
        C2847qP c2847qPK = C2919rP.k();
        Iterator it = this.a.a().iterator();
        while (it.hasNext()) {
            C2461lP c2461lP = (C2461lP) ((XY) it.next()).a();
            AbstractC0936Ds.c(c2461lP, CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD);
            c2847qPK.a(c2461lP);
        }
        return (AbstractC1045Hj) c2847qPK.c();
    }
}
