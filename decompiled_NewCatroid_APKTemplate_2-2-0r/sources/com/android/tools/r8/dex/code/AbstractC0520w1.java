package com.android.tools.r8.dex.code;

import com.android.tools.r8.graph.C0645e5;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.internal.AbstractC1476Wj;
import com.android.tools.r8.internal.C2363kB;
import java.nio.ShortBuffer;

/* JADX INFO: renamed from: com.android.tools.r8.dex.code.w1, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC0520w1 extends P0 {
    AbstractC0520w1(int i, InterfaceC0409a interfaceC0409a, com.android.tools.r8.graph.Y0[] y0Arr) {
        super(i, interfaceC0409a, y0Arr);
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final void a(C0702m c0702m, com.android.tools.r8.dex.L l, C2363kB c2363kB, o5 o5Var) {
        c0702m.E().c((AbstractC1476Wj) null, getField()).a(c0702m, l);
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final com.android.tools.r8.graph.Y0 getField() {
        return (com.android.tools.r8.graph.Y0) this.g;
    }

    AbstractC0520w1(int i, int i2, com.android.tools.r8.graph.Y0 y0) {
        super(i, i2, y0);
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final void a(C0645e5 c0645e5, o5 o5Var, AbstractC1476Wj abstractC1476Wj, AbstractC1476Wj abstractC1476Wj2, C2363kB c2363kB, ShortBuffer shortBuffer) {
        com.android.tools.r8.graph.Y0 y0C = abstractC1476Wj.c(abstractC1476Wj2, getField());
        B1.a(this.f, this.e, shortBuffer, v());
        B1.a(y0C, shortBuffer, c0645e5);
    }
}
