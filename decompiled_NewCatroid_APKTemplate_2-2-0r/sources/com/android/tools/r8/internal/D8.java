package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0753u;
import com.android.tools.r8.ir.optimize.C3550d0;
import com.android.tools.r8.naming.AbstractC3649o0;
import com.android.tools.r8.utils.C3888e1;
import java.util.function.BiFunction;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class D8 extends B7 {
    static final /* synthetic */ boolean c = true;
    private final a b;

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public enum a {
        Pop(87),
        Pop2(88),
        Dup(89),
        DupX1(90),
        DupX2(91),
        Dup2(92),
        Dup2X1(93),
        Dup2X2(94),
        Swap(95);

        private final int a;

        a(int i) {
            this.a = i;
        }
    }

    public D8(a aVar) {
        this.b = aVar;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static AbstractC2359k7 b(InterfaceC3107u6 interfaceC3107u6, InterfaceC3054tM interfaceC3054tM, AbstractC2359k7 abstractC2359k7, InterfaceC3054tM interfaceC3054tM2) {
        return abstractC2359k7.a(interfaceC3107u6, interfaceC3054tM).a(interfaceC3107u6, interfaceC3054tM2).a(interfaceC3107u6, interfaceC3054tM);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static AbstractC2359k7 c(InterfaceC3107u6 interfaceC3107u6, AbstractC2359k7 abstractC2359k7, InterfaceC3054tM interfaceC3054tM, InterfaceC3054tM interfaceC3054tM2) {
        return abstractC2359k7.a(interfaceC3107u6, interfaceC3054tM2).a(interfaceC3107u6, interfaceC3054tM);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static AbstractC2359k7 d(InterfaceC3107u6 interfaceC3107u6, AbstractC2359k7 abstractC2359k7, InterfaceC3054tM interfaceC3054tM, InterfaceC3054tM interfaceC3054tM2) {
        return abstractC2359k7.a(interfaceC3107u6, interfaceC3054tM).a(interfaceC3107u6, interfaceC3054tM2).a(interfaceC3107u6, interfaceC3054tM).a(interfaceC3107u6, interfaceC3054tM2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static AbstractC2359k7 e(InterfaceC3107u6 interfaceC3107u6, AbstractC2359k7 abstractC2359k7, InterfaceC3054tM interfaceC3054tM) {
        return abstractC2359k7.a(interfaceC3107u6, interfaceC3054tM).a(interfaceC3107u6, interfaceC3054tM);
    }

    public a T() {
        return this.b;
    }

    @Override // com.android.tools.r8.internal.B7
    public final void a(com.android.tools.r8.utils.structural.o oVar) {
    }

    @Override // com.android.tools.r8.internal.B7
    public final boolean y() {
        return false;
    }

    @Override // com.android.tools.r8.internal.B7
    public final int z() {
        return this.b.a;
    }

    public static D8 a(int i) {
        switch (i) {
            case 87:
                return new D8(a.Pop);
            case 88:
                return new D8(a.Pop2);
            case 89:
                return new D8(a.Dup);
            case 90:
                return new D8(a.DupX1);
            case 91:
                return new D8(a.DupX2);
            case 92:
                return new D8(a.Dup2);
            case 93:
                return new D8(a.Dup2X1);
            case 94:
                return new D8(a.Dup2X2);
            case 95:
                return new D8(a.Swap);
            default:
                throw new C3405y10("Invalid opcode for CfStackInstruction");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static AbstractC2359k7 b(InterfaceC3107u6 interfaceC3107u6, InterfaceC3054tM interfaceC3054tM, AbstractC2359k7 abstractC2359k7, InterfaceC3054tM interfaceC3054tM2, InterfaceC3054tM interfaceC3054tM3) {
        return abstractC2359k7.a(interfaceC3107u6, interfaceC3054tM).a(interfaceC3107u6, interfaceC3054tM2).a(interfaceC3107u6, interfaceC3054tM3).a(interfaceC3107u6, interfaceC3054tM);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static AbstractC2359k7 c(InterfaceC3107u6 interfaceC3107u6, InterfaceC3054tM interfaceC3054tM, AbstractC2359k7 abstractC2359k7, InterfaceC3054tM interfaceC3054tM2) {
        return abstractC2359k7.a(interfaceC3107u6, interfaceC3054tM).a(interfaceC3107u6, interfaceC3054tM2).a(interfaceC3107u6, interfaceC3054tM);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ AbstractC2359k7 e(final InterfaceC3107u6 interfaceC3107u6, AbstractC2359k7 abstractC2359k7, final InterfaceC3054tM interfaceC3054tM, final InterfaceC3054tM interfaceC3054tM2) {
        return abstractC2359k7.c(new BiFunction() { // from class: com.android.tools.r8.internal.D8$$ExternalSyntheticLambda0
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return D8.b(interfaceC3107u6, interfaceC3054tM, interfaceC3054tM2, (AbstractC2359k7) obj, (InterfaceC3054tM) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ AbstractC2359k7 c(final InterfaceC3107u6 interfaceC3107u6, AbstractC2359k7 abstractC2359k7, final InterfaceC3054tM interfaceC3054tM) {
        return abstractC2359k7.a(new com.android.tools.r8.utils.G3() { // from class: com.android.tools.r8.internal.D8$$ExternalSyntheticLambda9
            @Override // com.android.tools.r8.utils.G3
            public final Object a(Object obj, Object obj2, Object obj3) {
                return D8.a(interfaceC3107u6, interfaceC3054tM, (AbstractC2359k7) obj, (InterfaceC3054tM) obj2, (InterfaceC3054tM) obj3);
            }
        }, new BiFunction() { // from class: com.android.tools.r8.internal.D8$$ExternalSyntheticLambda10
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return D8.a(interfaceC3107u6, interfaceC3054tM, (AbstractC2359k7) obj, (InterfaceC3054tM) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ AbstractC2359k7 d(final InterfaceC3107u6 interfaceC3107u6, AbstractC2359k7 abstractC2359k7, final InterfaceC3054tM interfaceC3054tM) {
        return abstractC2359k7.a(new com.android.tools.r8.utils.G3() { // from class: com.android.tools.r8.internal.D8$$ExternalSyntheticLambda12
            @Override // com.android.tools.r8.utils.G3
            public final Object a(Object obj, Object obj2, Object obj3) {
                return D8.b(interfaceC3107u6, interfaceC3054tM, (AbstractC2359k7) obj, (InterfaceC3054tM) obj2, (InterfaceC3054tM) obj3);
            }
        }, new BiFunction() { // from class: com.android.tools.r8.internal.D8$$ExternalSyntheticLambda13
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return D8.b(interfaceC3107u6, interfaceC3054tM, (AbstractC2359k7) obj, (InterfaceC3054tM) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static AbstractC2359k7 b(InterfaceC3107u6 interfaceC3107u6, InterfaceC3054tM interfaceC3054tM, InterfaceC3054tM interfaceC3054tM2, AbstractC2359k7 abstractC2359k7, InterfaceC3054tM interfaceC3054tM3) {
        return abstractC2359k7.a(interfaceC3107u6, interfaceC3054tM).a(interfaceC3107u6, interfaceC3054tM2).a(interfaceC3107u6, interfaceC3054tM3).a(interfaceC3107u6, interfaceC3054tM).a(interfaceC3107u6, interfaceC3054tM2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ AbstractC2359k7 b(final InterfaceC3107u6 interfaceC3107u6, AbstractC2359k7 abstractC2359k7, final InterfaceC3054tM interfaceC3054tM) {
        return abstractC2359k7.c(new BiFunction() { // from class: com.android.tools.r8.internal.D8$$ExternalSyntheticLambda11
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return D8.c(interfaceC3107u6, interfaceC3054tM, (AbstractC2359k7) obj, (InterfaceC3054tM) obj2);
            }
        });
    }

    @Override // com.android.tools.r8.internal.B7
    public final int a(B7 b7, com.android.tools.r8.utils.structural.a aVar, com.android.tools.r8.graph.C c2) {
        com.android.tools.r8.graph.C.a(this, b7);
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ AbstractC2359k7 b(final InterfaceC3107u6 interfaceC3107u6, AbstractC2359k7 abstractC2359k7, final InterfaceC3054tM interfaceC3054tM, final InterfaceC3054tM interfaceC3054tM2) {
        return abstractC2359k7.a(new com.android.tools.r8.utils.G3() { // from class: com.android.tools.r8.internal.D8$$ExternalSyntheticLambda14
            @Override // com.android.tools.r8.utils.G3
            public final Object a(Object obj, Object obj2, Object obj3) {
                return D8.a(interfaceC3107u6, interfaceC3054tM, interfaceC3054tM2, (AbstractC2359k7) obj, (InterfaceC3054tM) obj2, (InterfaceC3054tM) obj3);
            }
        }, new BiFunction() { // from class: com.android.tools.r8.internal.D8$$ExternalSyntheticLambda15
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return D8.a(interfaceC3107u6, interfaceC3054tM, interfaceC3054tM2, (AbstractC2359k7) obj, (InterfaceC3054tM) obj2);
            }
        });
    }

    @Override // com.android.tools.r8.internal.B7
    public final void a(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var, C0722p1 c0722p1, AbstractC1476Wj abstractC1476Wj, AbstractC2331jn abstractC2331jn, AbstractC3649o0 abstractC3649o0, C2363kB c2363kB, AbstractC1899eF abstractC1899eF) {
        abstractC1899eF.a(this.b.a);
    }

    @Override // com.android.tools.r8.internal.B7
    public final void a(C2757p8 c2757p8) {
        c2757p8.a(this);
    }

    @Override // com.android.tools.r8.internal.B7
    public final void a(C2102gl c2102gl, N8 n8, A8 a8) {
        switch (C8.a[this.b.ordinal()]) {
            case 1:
                H8 h8E = n8.e();
                if (!c && h8E.b.b()) {
                    throw new AssertionError("Expected stack type to be single width");
                }
                return;
            case 2:
                if (n8.e().b.b()) {
                    return;
                }
                H8 h8E2 = n8.e();
                if (!c && h8E2.b.b()) {
                    throw new AssertionError();
                }
                return;
            case 3:
                H8 h8C = n8.c();
                if (!c && h8C.b.b()) {
                    throw new AssertionError();
                }
                EnumC1812d20 enumC1812d20 = h8C.b;
                int i = n8.a(h8C).a;
                int i2 = h8C.a;
                c2102gl.getClass();
                c2102gl.a(EnumC1968f20.a(enumC1812d20), i, i2);
                return;
            case 4:
                H8 h8E3 = n8.e();
                H8 h8E4 = n8.e();
                boolean z = c;
                if (!z && h8E3.b.b()) {
                    throw new AssertionError();
                }
                if (!z && h8E4.b.b()) {
                    throw new AssertionError();
                }
                a(c2102gl, n8, h8E3, h8E4);
                return;
            case 5:
                H8 h8E5 = n8.e();
                H8 h8E6 = n8.e();
                boolean z2 = c;
                if (!z2 && h8E5.b.b()) {
                    throw new AssertionError();
                }
                if (h8E6.b.b()) {
                    a(c2102gl, n8, h8E5, h8E6);
                    return;
                }
                H8 h8E7 = n8.e();
                if (!z2 && h8E7.b.b()) {
                    throw new AssertionError();
                }
                H8 h8A = n8.a(h8E5);
                H8 h8A2 = n8.a(h8E7);
                H8 h8A3 = n8.a(h8E6);
                H8 h8A4 = n8.a(h8E5);
                EnumC1812d20 enumC1812d21 = h8E5.b;
                int i3 = h8A4.a;
                int i4 = h8E5.a;
                c2102gl.getClass();
                c2102gl.a(EnumC1968f20.a(enumC1812d21), i3, i4);
                EnumC1812d20 enumC1812d22 = h8E6.b;
                c2102gl.a(EnumC1968f20.a(enumC1812d22), h8A3.a, h8E6.a);
                EnumC1812d20 enumC1812d23 = h8E7.b;
                c2102gl.a(EnumC1968f20.a(enumC1812d23), h8A2.a, h8E7.a);
                EnumC1812d20 enumC1812d24 = h8A4.b;
                c2102gl.a(EnumC1968f20.a(enumC1812d24), h8A.a, h8A4.a);
                return;
            case 6:
                H8 h8C2 = n8.c();
                if (h8C2.b.b()) {
                    EnumC1812d20 enumC1812d25 = h8C2.b;
                    int i5 = n8.a(h8C2).a;
                    int i6 = h8C2.a;
                    c2102gl.getClass();
                    c2102gl.a(EnumC1968f20.a(enumC1812d25), i5, i6);
                    return;
                }
                H8 h8D = n8.d();
                EnumC1812d20 enumC1812d26 = h8D.b;
                int i7 = n8.a(h8D).a;
                int i8 = h8D.a;
                c2102gl.getClass();
                c2102gl.a(EnumC1968f20.a(enumC1812d26), i7, i8);
                EnumC1812d20 enumC1812d27 = h8C2.b;
                c2102gl.a(EnumC1968f20.a(enumC1812d27), n8.a(h8C2).a, h8C2.a);
                return;
            case 7:
                H8 h8E8 = n8.e();
                H8 h8E9 = n8.e();
                boolean z3 = c;
                if (!z3 && h8E9.b.b()) {
                    throw new AssertionError();
                }
                if (h8E8.b.b()) {
                    a(c2102gl, n8, h8E8, h8E9);
                    return;
                }
                H8 h8E10 = n8.e();
                if (!z3 && h8E10.b.b()) {
                    throw new AssertionError();
                }
                a(c2102gl, n8, h8E8, h8E9, h8E10);
                return;
            case 8:
                H8 h8E11 = n8.e();
                H8 h8E12 = n8.e();
                if (h8E11.b.b() && h8E12.b.b()) {
                    a(c2102gl, n8, h8E11, h8E12);
                    return;
                }
                H8 h8E13 = n8.e();
                if (h8E11.b.b()) {
                    if (!h8E13.b.b()) {
                        H8 h8A5 = n8.a(h8E11);
                        H8 h8A6 = n8.a(h8E13);
                        H8 h8A7 = n8.a(h8E12);
                        H8 h8A8 = n8.a(h8E11);
                        EnumC1812d20 enumC1812d28 = h8E11.b;
                        int i9 = h8A8.a;
                        int i10 = h8E11.a;
                        c2102gl.getClass();
                        c2102gl.a(EnumC1968f20.a(enumC1812d28), i9, i10);
                        EnumC1812d20 enumC1812d29 = h8E12.b;
                        c2102gl.a(EnumC1968f20.a(enumC1812d29), h8A7.a, h8E12.a);
                        EnumC1812d20 enumC1812d210 = h8E13.b;
                        c2102gl.a(EnumC1968f20.a(enumC1812d210), h8A6.a, h8E13.a);
                        EnumC1812d20 enumC1812d211 = h8A8.b;
                        c2102gl.a(EnumC1968f20.a(enumC1812d211), h8A5.a, h8A8.a);
                        return;
                    }
                    throw new C1367Tb("Invalid dup2x2 with types: wide, single, wide");
                }
                if (!h8E12.b.b()) {
                    if (h8E13.b.b()) {
                        a(c2102gl, n8, h8E11, h8E12, h8E13);
                        return;
                    }
                    H8 h8E14 = n8.e();
                    if (!h8E14.b.b()) {
                        H8 h8A9 = n8.a(h8E12);
                        H8 h8A10 = n8.a(h8E11);
                        H8 h8A11 = n8.a(h8E14);
                        H8 h8A12 = n8.a(h8E13);
                        H8 h8A13 = n8.a(h8E12);
                        H8 h8A14 = n8.a(h8E11);
                        EnumC1812d20 enumC1812d212 = h8E11.b;
                        int i11 = h8A14.a;
                        int i12 = h8E11.a;
                        c2102gl.getClass();
                        c2102gl.a(EnumC1968f20.a(enumC1812d212), i11, i12);
                        EnumC1812d20 enumC1812d213 = h8E12.b;
                        c2102gl.a(EnumC1968f20.a(enumC1812d213), h8A13.a, h8E12.a);
                        EnumC1812d20 enumC1812d214 = h8E13.b;
                        c2102gl.a(EnumC1968f20.a(enumC1812d214), h8A12.a, h8E13.a);
                        EnumC1812d20 enumC1812d215 = h8E14.b;
                        c2102gl.a(EnumC1968f20.a(enumC1812d215), h8A11.a, h8E14.a);
                        EnumC1812d20 enumC1812d216 = h8A14.b;
                        c2102gl.a(EnumC1968f20.a(enumC1812d216), h8A10.a, h8A14.a);
                        EnumC1812d20 enumC1812d217 = h8A13.b;
                        c2102gl.a(EnumC1968f20.a(enumC1812d217), h8A9.a, h8A13.a);
                        return;
                    }
                    throw new C1367Tb("Invalid dup2x2 with types: wide, single, single, single");
                }
                throw new C1367Tb("Invalid dup2x2 with types: ..., wide, single");
            case 9:
                H8 h8E15 = n8.e();
                H8 h8E16 = n8.e();
                boolean z4 = c;
                if (!z4 && h8E15.b.b()) {
                    throw new AssertionError();
                }
                if (!z4 && h8E16.b.b()) {
                    throw new AssertionError();
                }
                a(c2102gl, n8, h8E15, h8E16);
                n8.e();
                return;
            default:
                return;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static AbstractC2359k7 a(InterfaceC3107u6 interfaceC3107u6, AbstractC2359k7 abstractC2359k7, InterfaceC3054tM interfaceC3054tM) {
        return abstractC2359k7.a(interfaceC3107u6, interfaceC3054tM).a(interfaceC3107u6, interfaceC3054tM);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static AbstractC2359k7 a(InterfaceC3107u6 interfaceC3107u6, AbstractC2359k7 abstractC2359k7, InterfaceC3054tM interfaceC3054tM, InterfaceC3054tM interfaceC3054tM2) {
        return abstractC2359k7.a(interfaceC3107u6, interfaceC3054tM2).a(interfaceC3107u6, interfaceC3054tM).a(interfaceC3107u6, interfaceC3054tM2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static AbstractC2359k7 a(InterfaceC3107u6 interfaceC3107u6, InterfaceC3054tM interfaceC3054tM, AbstractC2359k7 abstractC2359k7, InterfaceC3054tM interfaceC3054tM2) {
        return abstractC2359k7.a(interfaceC3107u6, interfaceC3054tM).a(interfaceC3107u6, interfaceC3054tM2).a(interfaceC3107u6, interfaceC3054tM);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static AbstractC2359k7 a(InterfaceC3107u6 interfaceC3107u6, InterfaceC3054tM interfaceC3054tM, AbstractC2359k7 abstractC2359k7, InterfaceC3054tM interfaceC3054tM2, InterfaceC3054tM interfaceC3054tM3) {
        return abstractC2359k7.a(interfaceC3107u6, interfaceC3054tM).a(interfaceC3107u6, interfaceC3054tM2).a(interfaceC3107u6, interfaceC3054tM3).a(interfaceC3107u6, interfaceC3054tM);
    }

    private static void a(C2102gl c2102gl, N8 n8, H8 h8, H8 h9) {
        H8 h8A = n8.a(h8);
        H8 h8A2 = n8.a(h9);
        H8 h8A3 = n8.a(h8);
        EnumC1812d20 enumC1812d20 = h8.b;
        int i = h8A3.a;
        int i2 = h8.a;
        c2102gl.getClass();
        c2102gl.a(EnumC1968f20.a(enumC1812d20), i, i2);
        EnumC1812d20 enumC1812d21 = h9.b;
        c2102gl.a(EnumC1968f20.a(enumC1812d21), h8A2.a, h9.a);
        EnumC1812d20 enumC1812d22 = h8A3.b;
        c2102gl.a(EnumC1968f20.a(enumC1812d22), h8A.a, h8A3.a);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static AbstractC2359k7 a(InterfaceC3107u6 interfaceC3107u6, InterfaceC3054tM interfaceC3054tM, InterfaceC3054tM interfaceC3054tM2, AbstractC2359k7 abstractC2359k7, InterfaceC3054tM interfaceC3054tM3) {
        return abstractC2359k7.a(interfaceC3107u6, interfaceC3054tM).a(interfaceC3107u6, interfaceC3054tM2).a(interfaceC3107u6, interfaceC3054tM3).a(interfaceC3107u6, interfaceC3054tM).a(interfaceC3107u6, interfaceC3054tM2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static AbstractC2359k7 a(InterfaceC3107u6 interfaceC3107u6, InterfaceC3054tM interfaceC3054tM, InterfaceC3054tM interfaceC3054tM2, AbstractC2359k7 abstractC2359k7, InterfaceC3054tM interfaceC3054tM3, InterfaceC3054tM interfaceC3054tM4) {
        return abstractC2359k7.a(interfaceC3107u6, interfaceC3054tM).a(interfaceC3107u6, interfaceC3054tM2).a(interfaceC3107u6, interfaceC3054tM3).a(interfaceC3107u6, interfaceC3054tM4).a(interfaceC3107u6, interfaceC3054tM).a(interfaceC3107u6, interfaceC3054tM2);
    }

    private static void a(C2102gl c2102gl, N8 n8, H8 h8, H8 h9, H8 h10) {
        H8 h8A = n8.a(h9);
        H8 h8A2 = n8.a(h8);
        H8 h8A3 = n8.a(h10);
        H8 h8A4 = n8.a(h9);
        H8 h8A5 = n8.a(h8);
        EnumC1812d20 enumC1812d20 = h8.b;
        int i = h8A5.a;
        int i2 = h8.a;
        c2102gl.getClass();
        c2102gl.a(EnumC1968f20.a(enumC1812d20), i, i2);
        EnumC1812d20 enumC1812d21 = h9.b;
        c2102gl.a(EnumC1968f20.a(enumC1812d21), h8A4.a, h9.a);
        EnumC1812d20 enumC1812d22 = h10.b;
        c2102gl.a(EnumC1968f20.a(enumC1812d22), h8A3.a, h10.a);
        EnumC1812d20 enumC1812d23 = h8.b;
        c2102gl.a(EnumC1968f20.a(enumC1812d23), h8A2.a, h8A5.a);
        EnumC1812d20 enumC1812d24 = h9.b;
        c2102gl.a(EnumC1968f20.a(enumC1812d24), h8A.a, h8A4.a);
    }

    @Override // com.android.tools.r8.internal.B7
    public final com.android.tools.r8.ir.optimize.V a(C3550d0 c3550d0, C0753u c0753u, com.android.tools.r8.graph.o5 o5Var) {
        return com.android.tools.r8.ir.optimize.V.d;
    }

    @Override // com.android.tools.r8.internal.B7
    public final AbstractC2359k7 a(AbstractC2359k7 abstractC2359k7, C0702m c0702m, final InterfaceC3107u6 interfaceC3107u6) {
        switch (C8.a[this.b.ordinal()]) {
            case 1:
                return abstractC2359k7.j();
            case 2:
                return abstractC2359k7.a(new com.android.tools.r8.utils.G3() { // from class: com.android.tools.r8.internal.D8$$ExternalSyntheticLambda17
                    @Override // com.android.tools.r8.utils.G3
                    public final Object a(Object obj, Object obj2, Object obj3) {
                        return (AbstractC2359k7) C3888e1.a((AbstractC2359k7) obj, (InterfaceC3054tM) obj2, (InterfaceC3054tM) obj3);
                    }
                }, new D8$$ExternalSyntheticLambda18());
            case 3:
                return abstractC2359k7.c(new BiFunction() { // from class: com.android.tools.r8.internal.D8$$ExternalSyntheticLambda8
                    @Override // java.util.function.BiFunction
                    public final Object apply(Object obj, Object obj2) {
                        return D8.a(interfaceC3107u6, (AbstractC2359k7) obj, (InterfaceC3054tM) obj2);
                    }
                });
            case 4:
                return abstractC2359k7.a(new com.android.tools.r8.utils.G3() { // from class: com.android.tools.r8.internal.D8$$ExternalSyntheticLambda7
                    @Override // com.android.tools.r8.utils.G3
                    public final Object a(Object obj, Object obj2, Object obj3) {
                        return D8.a(interfaceC3107u6, (AbstractC2359k7) obj, (InterfaceC3054tM) obj2, (InterfaceC3054tM) obj3);
                    }
                });
            case 5:
                return abstractC2359k7.c(new BiFunction() { // from class: com.android.tools.r8.internal.D8$$ExternalSyntheticLambda6
                    @Override // java.util.function.BiFunction
                    public final Object apply(Object obj, Object obj2) {
                        return D8.d(interfaceC3107u6, (AbstractC2359k7) obj, (InterfaceC3054tM) obj2);
                    }
                });
            case 6:
                return abstractC2359k7.a(new com.android.tools.r8.utils.G3() { // from class: com.android.tools.r8.internal.D8$$ExternalSyntheticLambda4
                    @Override // com.android.tools.r8.utils.G3
                    public final Object a(Object obj, Object obj2, Object obj3) {
                        return D8.d(interfaceC3107u6, (AbstractC2359k7) obj, (InterfaceC3054tM) obj2, (InterfaceC3054tM) obj3);
                    }
                }, new BiFunction() { // from class: com.android.tools.r8.internal.D8$$ExternalSyntheticLambda5
                    @Override // java.util.function.BiFunction
                    public final Object apply(Object obj, Object obj2) {
                        return D8.e(interfaceC3107u6, (AbstractC2359k7) obj, (InterfaceC3054tM) obj2);
                    }
                });
            case 7:
                return abstractC2359k7.a(new com.android.tools.r8.utils.G3() { // from class: com.android.tools.r8.internal.D8$$ExternalSyntheticLambda2
                    @Override // com.android.tools.r8.utils.G3
                    public final Object a(Object obj, Object obj2, Object obj3) {
                        return D8.e(interfaceC3107u6, (AbstractC2359k7) obj, (InterfaceC3054tM) obj2, (InterfaceC3054tM) obj3);
                    }
                }, new BiFunction() { // from class: com.android.tools.r8.internal.D8$$ExternalSyntheticLambda3
                    @Override // java.util.function.BiFunction
                    public final Object apply(Object obj, Object obj2) {
                        return D8.b(interfaceC3107u6, (AbstractC2359k7) obj, (InterfaceC3054tM) obj2);
                    }
                });
            case 8:
                return abstractC2359k7.a(new com.android.tools.r8.utils.G3() { // from class: com.android.tools.r8.internal.D8$$ExternalSyntheticLambda19
                    @Override // com.android.tools.r8.utils.G3
                    public final Object a(Object obj, Object obj2, Object obj3) {
                        return D8.b(interfaceC3107u6, (AbstractC2359k7) obj, (InterfaceC3054tM) obj2, (InterfaceC3054tM) obj3);
                    }
                }, new BiFunction() { // from class: com.android.tools.r8.internal.D8$$ExternalSyntheticLambda1
                    @Override // java.util.function.BiFunction
                    public final Object apply(Object obj, Object obj2) {
                        return D8.c(interfaceC3107u6, (AbstractC2359k7) obj, (InterfaceC3054tM) obj2);
                    }
                });
            case 9:
                return abstractC2359k7.a(new com.android.tools.r8.utils.G3() { // from class: com.android.tools.r8.internal.D8$$ExternalSyntheticLambda16
                    @Override // com.android.tools.r8.utils.G3
                    public final Object a(Object obj, Object obj2, Object obj3) {
                        return D8.c(interfaceC3107u6, (AbstractC2359k7) obj, (InterfaceC3054tM) obj2, (InterfaceC3054tM) obj3);
                    }
                });
            default:
                throw new C3405y10("Invalid opcode for CfStackInstruction");
        }
    }
}
