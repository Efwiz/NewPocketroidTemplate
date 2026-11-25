package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0753u;
import com.android.tools.r8.ir.optimize.C3550d0;
import com.android.tools.r8.naming.AbstractC3649o0;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class U8 extends L7 {
    static final /* synthetic */ boolean f = true;
    private final a b;
    private final M7 c;
    private final int[] d;
    private final List e;

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public enum a {
        LOOKUP,
        TABLE;
    }

    public U8(a aVar, M7 m7, int[] iArr, ArrayList arrayList) {
        this.b = aVar;
        this.c = m7;
        this.d = iArr;
        this.e = arrayList;
        boolean z = f;
        if (!z && aVar == a.LOOKUP && iArr.length != arrayList.size()) {
            throw new AssertionError();
        }
        if (!z && aVar == a.TABLE && iArr.length != 1) {
            throw new AssertionError();
        }
    }

    @Override // com.android.tools.r8.internal.B7
    public final boolean N() {
        return true;
    }

    public final M7 U() {
        return this.c;
    }

    public List<Integer> V() {
        return new C2732or(this.d);
    }

    public a W() {
        return this.b;
    }

    public final List X() {
        return this.e;
    }

    @Override // com.android.tools.r8.internal.B7
    public final com.android.tools.r8.utils.D3 a(final BiFunction biFunction, B7 b7, AbstractC2359k7 abstractC2359k7) {
        return com.android.tools.r8.utils.E3.a(this.e, abstractC2359k7, biFunction).a(new Function() { // from class: com.android.tools.r8.internal.U8$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.a(biFunction, (com.android.tools.r8.utils.C3) obj);
            }
        });
    }

    @Override // com.android.tools.r8.internal.B7
    public final int z() {
        return this.b == a.LOOKUP ? 171 : 170;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ com.android.tools.r8.utils.D3 a(BiFunction biFunction, com.android.tools.r8.utils.C3 c3) {
        return (com.android.tools.r8.utils.D3) biFunction.apply(this.c, c3.j());
    }

    @Override // com.android.tools.r8.internal.B7
    public final int a(B7 b7, com.android.tools.r8.utils.structural.a aVar, final com.android.tools.r8.graph.C c) {
        if (f || this.b == ((U8) b7).b) {
            return aVar.a(this, (U8) b7, (com.android.tools.r8.utils.structural.y<U8>) new com.android.tools.r8.utils.structural.y() { // from class: com.android.tools.r8.internal.U8$$ExternalSyntheticLambda4
                @Override // com.android.tools.r8.utils.structural.y
                public final void a(com.android.tools.r8.utils.structural.z zVar) {
                    U8.a(c, zVar);
                }
            });
        }
        throw new AssertionError();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(com.android.tools.r8.graph.C c, com.android.tools.r8.utils.structural.z zVar) {
        Function function = new Function() { // from class: com.android.tools.r8.internal.U8$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((U8) obj).U();
            }
        };
        com.android.tools.r8.utils.structural.u uVarA = c.a();
        zVar.a(function, uVarA, uVarA).b(new Function() { // from class: com.android.tools.r8.internal.U8$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((U8) obj).d;
            }
        }).a(new Function() { // from class: com.android.tools.r8.internal.U8$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((U8) obj).X();
            }
        }, c.a());
    }

    @Override // com.android.tools.r8.internal.B7
    public final void a(com.android.tools.r8.utils.structural.o oVar) {
        oVar.a(this.d.length);
        int i = 0;
        while (true) {
            int[] iArr = this.d;
            if (i >= iArr.length) {
                return;
            }
            oVar.a(iArr[i]);
            i++;
        }
    }

    @Override // com.android.tools.r8.internal.B7
    public final void a(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var, C0722p1 c0722p1, AbstractC1476Wj abstractC1476Wj, AbstractC2331jn abstractC2331jn, AbstractC3649o0 abstractC3649o0, C2363kB c2363kB, AbstractC1899eF abstractC1899eF) {
        C3263wA[] c3263wAArr = new C3263wA[this.e.size()];
        for (int i = 0; i < this.e.size(); i++) {
            c3263wAArr[i] = ((M7) this.e.get(i)).T();
        }
        int i2 = T8.a[this.b.ordinal()];
        if (i2 != 1) {
            if (i2 != 2) {
                return;
            }
            int i3 = this.d[0];
            abstractC1899eF.a(i3, (this.e.size() + i3) - 1, this.c.T(), c3263wAArr);
        } else {
            abstractC1899eF.a(this.c.T(), this.d, c3263wAArr);
        }
    }

    @Override // com.android.tools.r8.internal.B7
    public final void a(C2757p8 c2757p8) {
        c2757p8.a(this);
    }

    @Override // com.android.tools.r8.internal.B7
    public final void a(C2102gl c2102gl, N8 n8, A8 a8) {
        int[] iArr = new int[this.e.size()];
        for (int i = 0; i < this.e.size(); i++) {
            iArr[i] = a8.a((M7) this.e.get(i));
        }
        c2102gl.a(n8.e().a, this.d, a8.a(this.c), iArr);
    }

    @Override // com.android.tools.r8.internal.B7
    public final AbstractC2359k7 a(AbstractC2359k7 abstractC2359k7, C0702m c0702m, InterfaceC3107u6 interfaceC3107u6) {
        return abstractC2359k7.a(c0702m, interfaceC3107u6, c0702m.a().A1);
    }

    @Override // com.android.tools.r8.internal.B7
    public final com.android.tools.r8.ir.optimize.V a(C3550d0 c3550d0, C0753u c0753u, com.android.tools.r8.graph.o5 o5Var) {
        return com.android.tools.r8.ir.optimize.V.d;
    }
}
