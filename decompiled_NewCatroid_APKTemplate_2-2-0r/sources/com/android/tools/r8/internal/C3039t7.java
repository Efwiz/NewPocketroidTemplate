package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.naming.AbstractC3649o0;
import java.util.function.BiFunction;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.internal.t7, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C3039t7 extends W6 {
    static final /* synthetic */ boolean e = true;

    public C3039t7(EnumC1076Il enumC1076Il, EnumC1812d20 enumC1812d20, M7 m7) {
        super(enumC1076Il, enumC1812d20, m7);
    }

    public int W() {
        switch (AbstractC2970s7.a[this.b.ordinal()]) {
            case 1:
                return this.c.a() ? 198 : 153;
            case 2:
                return 156;
            case 3:
                return 157;
            case 4:
                return 158;
            case 5:
                return 155;
            case 6:
                return this.c.a() ? 199 : 154;
            default:
                throw new C3405y10("Unexpected type " + this.c);
        }
    }

    @Override // com.android.tools.r8.internal.B7
    public final void a(com.android.tools.r8.utils.structural.o oVar) {
    }

    @Override // com.android.tools.r8.internal.B7
    public final int z() {
        return W();
    }

    @Override // com.android.tools.r8.internal.B7
    public final int a(B7 b7, com.android.tools.r8.utils.structural.a aVar, com.android.tools.r8.graph.C c) {
        C3039t7 c3039t7 = (C3039t7) b7;
        boolean z = e;
        if (!z && this.b != c3039t7.b) {
            throw new AssertionError();
        }
        if (!z && this.c != c3039t7.c) {
            throw new AssertionError();
        }
        return c.a().a(this.d, c3039t7.d, aVar);
    }

    @Override // com.android.tools.r8.internal.B7
    public final com.android.tools.r8.utils.D3 a(final BiFunction biFunction, final B7 b7, AbstractC2359k7 abstractC2359k7) {
        return ((com.android.tools.r8.utils.D3) biFunction.apply(this.d, abstractC2359k7)).a(new Function() { // from class: com.android.tools.r8.internal.t7$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C3039t7.a(biFunction, b7, (com.android.tools.r8.utils.C3) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ com.android.tools.r8.utils.D3 a(BiFunction biFunction, B7 b7, com.android.tools.r8.utils.C3 c3) {
        return (com.android.tools.r8.utils.D3) biFunction.apply(b7, c3.j());
    }

    @Override // com.android.tools.r8.internal.B7
    public final void a(C2757p8 c2757p8) {
        c2757p8.a(this);
    }

    @Override // com.android.tools.r8.internal.B7
    public final void a(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var, C0722p1 c0722p1, AbstractC1476Wj abstractC1476Wj, AbstractC2331jn abstractC2331jn, AbstractC3649o0 abstractC3649o0, C2363kB c2363kB, AbstractC1899eF abstractC1899eF) {
        abstractC1899eF.a(W(), this.d.T());
    }

    @Override // com.android.tools.r8.internal.B7
    public final void a(C2102gl c2102gl, N8 n8, A8 a8) {
        int i = n8.e().a;
        int iA = a8.a(this.d);
        int iG = a8.g() + 1;
        EnumC1076Il enumC1076Il = this.b;
        EnumC1812d20 enumC1812d20 = this.c;
        c2102gl.getClass();
        c2102gl.a(enumC1076Il, EnumC1968f20.a(enumC1812d20), i, iA, iG);
    }

    @Override // com.android.tools.r8.internal.B7
    public final AbstractC2359k7 a(AbstractC2359k7 abstractC2359k7, C0702m c0702m, InterfaceC3107u6 interfaceC3107u6) {
        return abstractC2359k7.a(c0702m, interfaceC3107u6, this.c);
    }
}
