package com.android.tools.r8.dex.code;

import com.android.tools.r8.graph.C0645e5;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.internal.AbstractC1476Wj;
import com.android.tools.r8.internal.C2363kB;
import com.android.tools.r8.internal.EnumC1953et;
import com.android.tools.r8.internal.KE;
import java.nio.ShortBuffer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class R1 extends AbstractC0411a1 {
    R1(int i, InterfaceC0409a interfaceC0409a, C0684j2[] c0684j2Arr) {
        super(i, interfaceC0409a, c0684j2Arr);
    }

    public abstract EnumC1953et N();

    @Override // com.android.tools.r8.dex.code.B1
    public final void a(C0702m c0702m, com.android.tools.r8.dex.L l, C2363kB c2363kB, o5 o5Var) {
        ((C0684j2) c0702m.E().a(s(), o5Var.getReference(), N(), (AbstractC1476Wj) null).b()).a(c0702m, l);
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final C0684j2 s() {
        return (C0684j2) this.g;
    }

    R1(int i, int i2, C0684j2 c0684j2) {
        super(i, i2, c0684j2);
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final void a(C0645e5 c0645e5, o5 o5Var, AbstractC1476Wj abstractC1476Wj, AbstractC1476Wj abstractC1476Wj2, C2363kB c2363kB, ShortBuffer shortBuffer) {
        KE keA = abstractC1476Wj.a(s(), o5Var.getReference(), N(), (AbstractC1476Wj) null);
        B1.a(this.e, shortBuffer, keA.f().c());
        B1.a(keA.b(), shortBuffer, c0645e5);
        shortBuffer.put((short) this.f);
    }
}
