package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0753u;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.InterfaceC0694k5;
import com.android.tools.r8.references.MethodReference;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class W9 extends AbstractC2797pg implements InterfaceC3007sg {
    private static AbstractC1639am e = AbstractC1639am.a(C3039t7.class, C1818d7.class, C2899r7.class, C1818d7.class, P8.class);
    private static AbstractC1639am f = AbstractC1639am.a(C1818d7.class, Q7.class, P8.class);
    private static AbstractC1639am g = AbstractC1639am.a(O7.class, C1818d7.class, C1818d7.class, D6.class);
    private final C0722p1 a;
    private final com.android.tools.r8.ir.optimize.info.n b;
    private final C0759u2 c;
    private final List d;

    public W9(C0702m c0702m, com.android.tools.r8.ir.optimize.info.r rVar) {
        final C0722p1 c0722p1A = c0702m.a();
        this.a = c0722p1A;
        this.b = rVar;
        this.c = c0722p1A.c("ENABLED");
        this.d = (List) c0702m.Y().T0.b().stream().map(new Function() { // from class: com.android.tools.r8.internal.W9$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return c0722p1A.a((MethodReference) obj);
            }
        }).collect(Collectors.toList());
    }

    @Override // com.android.tools.r8.internal.InterfaceC3007sg
    public final void a(com.android.tools.r8.graph.Y0 y0, final com.android.tools.r8.graph.o5 o5Var, final com.android.tools.r8.shaking.F0 f0) {
        if ((y0.C0() == this.a.B0 && y0.getType() == this.a.v1) || y0 == this.a.z4.e.b) {
            this.d.forEach(new Consumer() { // from class: com.android.tools.r8.internal.W9$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    f0.c((C0684j2) obj, o5Var);
                }
            });
        }
    }

    /* JADX WARN: Code duplicated, block: B:122:0x019b A[EDGE_INSN: B:122:0x019b->B:96:0x019b BREAK  A[LOOP:0: B:10:0x002a->B:95:0x0196], SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:92:0x018f  */
    /* JADX WARN: Code duplicated, block: B:95:0x0196 A[LOOP:0: B:10:0x002a->B:95:0x0196, LOOP_END] */
    @Override // com.android.tools.r8.internal.AbstractC2797pg
    public final void a(com.android.tools.r8.graph.o5 o5Var, InterfaceC0694k5 interfaceC0694k5, com.android.tools.r8.shaking.L l) {
        int i;
        AbstractC2206i7 abstractC2206i7H;
        int i2;
        com.android.tools.r8.graph.W0 w0E = o5Var.e();
        if (w0E.q1() && w0E.a1().z0()) {
            C0753u c0753uK = w0E.a1().K();
            if (w0E.u1()) {
                int i3 = 0;
                int i4 = 0;
                while (true) {
                    if (i4 >= c0753uK.I0().size()) {
                        break;
                    }
                    B7 b7 = c0753uK.I0().get(i4);
                    if (b7.H()) {
                        I7 i7L = b7.l();
                        if (i7L.U() == 182 && i7L.T() == this.a.p4.a) {
                            int i5 = i4 + 1;
                            AbstractC1639am abstractC1639am = e;
                            int i6 = i3;
                            int i7 = i5;
                            B7 b8 = null;
                            while (i7 < c0753uK.I0().size() && i6 < abstractC1639am.size()) {
                                b8 = c0753uK.I0().get(i7);
                                b8.getClass();
                                if (!(b8 instanceof M7) && !b8.G() && !(b8 instanceof C2051g8)) {
                                    if (b8.getClass() == abstractC1639am.get(i6)) {
                                        i6++;
                                    } else if (b8.getClass() == g.get(i3)) {
                                        AbstractC1639am abstractC1639am2 = g;
                                        int i8 = i7;
                                        while (true) {
                                            if (i8 < c0753uK.I0().size() && (i2 = i8 - i7) < abstractC1639am2.size()) {
                                                if (c0753uK.I0().get(i8).getClass() != abstractC1639am2.get(i2)) {
                                                    break;
                                                } else {
                                                    i8++;
                                                }
                                            } else {
                                                if (i8 - i7 == abstractC1639am2.size()) {
                                                    int size = g.size() + i7;
                                                    if (size < c0753uK.I0().size()) {
                                                        i7 = size;
                                                        b8 = c0753uK.I0().get(size);
                                                        break;
                                                    }
                                                    break;
                                                }
                                                break;
                                            }
                                        }
                                        b8.getClass();
                                        if (!(b8 instanceof M7) && !b8.G() && !(b8 instanceof C2051g8)) {
                                            if (b8.getClass() != abstractC1639am.get(i6)) {
                                                break;
                                            }
                                            i6++;
                                        }
                                    } else {
                                        b8.getClass();
                                        if (!(b8 instanceof M7)) {
                                            continue;
                                        }
                                    }
                                }
                                i7++;
                                i3 = 0;
                            }
                            AbstractC2206i7 abstractC2206i7H2 = i6 == abstractC1639am.size() ? b8.h() : null;
                            if (abstractC2206i7H2 == null) {
                                AbstractC1639am abstractC1639am3 = f;
                                int i9 = 0;
                                B7 b9 = null;
                                while (i5 < c0753uK.I0().size() && i9 < abstractC1639am3.size()) {
                                    b9 = c0753uK.I0().get(i5);
                                    b9.getClass();
                                    if (!(b9 instanceof R8) && !(b9 instanceof O7)) {
                                        if (b9.getClass() != abstractC1639am3.get(i9)) {
                                            break;
                                        } else {
                                            i9++;
                                        }
                                    }
                                    i5++;
                                }
                                abstractC2206i7H = i9 == abstractC1639am3.size() ? b9.h() : null;
                            } else {
                                abstractC2206i7H = abstractC2206i7H2;
                            }
                            if (abstractC2206i7H != null) {
                                if (abstractC2206i7H.U() != 179 || abstractC2206i7H.getField().f != this.a.B0) {
                                    break;
                                }
                            } else if (b7.M()) {
                                break;
                                break;
                            } else {
                                i4++;
                                i3 = 0;
                            }
                        } else {
                            if (b7.M()) {
                                break;
                            }
                            i4++;
                            i3 = 0;
                        }
                    } else if (b7.M()) {
                        break;
                        break;
                    } else {
                        i4++;
                        i3 = 0;
                    }
                    this.b.a(w0E);
                    return;
                }
                if (o5Var.t() == this.a.z4.e.a) {
                    List<B7> listI0 = o5Var.e().a1().K().I0();
                    for (i = 1; i < listI0.size(); i++) {
                        B7 b10 = listI0.get(i - 1);
                        if (b10.H()) {
                            I7 i7L2 = b10.l();
                            if (i7L2.U() == 182 && i7L2.T() == this.a.p4.a && listI0.get(i).E()) {
                                AbstractC2206i7 abstractC2206i7H3 = listI0.get(i).h();
                                if (abstractC2206i7H3.U() == 179 && abstractC2206i7H3.getField().f == this.c) {
                                    this.b.a(w0E);
                                    return;
                                }
                            }
                        }
                        if (b10.M()) {
                            return;
                        }
                    }
                }
            }
        }
    }
}
