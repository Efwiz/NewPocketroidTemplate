package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0737r2;
import com.android.tools.r8.graph.C0753u;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.ir.optimize.C3550d0;
import com.android.tools.r8.naming.AbstractC3649o0;
import com.android.tools.r8.utils.C3970u0;
import java.util.Arrays;
import java.util.ListIterator;
import java.util.function.Function;
import java.util.function.Predicate;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class I7 extends B7 {
    static final /* synthetic */ boolean e = true;
    private final C0684j2 b;
    private final int c;
    private final boolean d;

    public I7(int i, C0684j2 c0684j2, boolean z) {
        boolean z2 = e;
        if (!z2 && (182 > i || i > 185)) {
            throw new AssertionError();
        }
        if (!z2 && i == 182 && z) {
            throw new AssertionError("InvokeVirtual on interface type");
        }
        if (!z2 && i == 185 && !z) {
            throw new AssertionError("InvokeInterface on class type");
        }
        this.c = i;
        this.b = c0684j2;
        this.d = z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(com.android.tools.r8.utils.structural.z zVar) {
        zVar.b(new Predicate() { // from class: com.android.tools.r8.internal.I7$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((I7) obj).V();
            }
        }).c(new Function() { // from class: com.android.tools.r8.internal.I7$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((I7) obj).T();
            }
        });
    }

    @Override // com.android.tools.r8.internal.B7
    public final boolean H() {
        return true;
    }

    @Override // com.android.tools.r8.internal.B7
    public final boolean I() {
        return this.c == 185;
    }

    @Override // com.android.tools.r8.internal.B7
    public final boolean J() {
        return this.c == 183;
    }

    @Override // com.android.tools.r8.internal.B7
    public final boolean K() {
        return this.c == 184;
    }

    @Override // com.android.tools.r8.internal.B7
    public final boolean L() {
        return this.c == 182;
    }

    public C0684j2 T() {
        return this.b;
    }

    public int U() {
        return this.c;
    }

    public boolean V() {
        return this.d;
    }

    public final boolean b(C0767v2 c0767v2) {
        if (this.c == 183) {
            C0684j2 c0684j2 = this.b;
            if (c0684j2.e != c0767v2 && !c0684j2.f.toString().equals("<init>")) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.tools.r8.internal.B7
    public I7 l() {
        return this;
    }

    @Override // com.android.tools.r8.internal.B7
    public final boolean x() {
        return true;
    }

    @Override // com.android.tools.r8.internal.B7
    public final int z() {
        return U();
    }

    @Override // com.android.tools.r8.internal.B7
    public final int a(B7 b7, com.android.tools.r8.utils.structural.a aVar, com.android.tools.r8.graph.C c) {
        return aVar.a(this, b7.l(), new I7$$ExternalSyntheticLambda2());
    }

    @Override // com.android.tools.r8.internal.B7
    public final void a(com.android.tools.r8.utils.structural.o oVar) {
        oVar.a(this, new I7$$ExternalSyntheticLambda2());
    }

    @Override // com.android.tools.r8.internal.B7
    public final void a(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var, C0722p1 c0722p1, AbstractC1476Wj abstractC1476Wj, AbstractC2331jn abstractC2331jn, AbstractC3649o0 abstractC3649o0, C2363kB c2363kB, AbstractC1899eF abstractC1899eF) {
        EnumC1953et enumC1953etA = EnumC1953et.a(this.c, this.b, o5Var, c0702m, c0702m.n());
        if (enumC1953etA == EnumC1953et.j) {
            if (!e && !c0722p1.A4.b(this.b)) {
                throw new AssertionError();
            }
            abstractC1899eF.a(enumC1953etA.a(), C3970u0.a(this.b.e.b1()), this.b.C0().toString(), c2363kB.a(this.b.H0()).a(abstractC3649o0), this.d);
            return;
        }
        KE keA = abstractC1476Wj.a(this.b, o5Var.getReference(), enumC1953etA, (AbstractC1476Wj) null);
        EnumC1953et enumC1953etF = keA.f();
        C0684j2 c0684j2 = (C0684j2) keA.b();
        abstractC1899eF.a(enumC1953etF.a(), abstractC3649o0.d(c0684j2.e), abstractC3649o0.a(c0684j2).toString(), c0684j2.h.a(abstractC3649o0), enumC1953etF == EnumC1953et.d || this.d);
    }

    @Override // com.android.tools.r8.internal.B7
    public final void a(C2757p8 c2757p8) {
        c2757p8.a(this);
    }

    @Override // com.android.tools.r8.internal.B7
    final void a(com.android.tools.r8.graph.J5 j5, ListIterator listIterator) {
        switch (this.c) {
            case 182:
                j5.h(this.b);
                return;
            case 183:
                j5.c(this.b);
                return;
            case 184:
                j5.f(this.b);
                return;
            case 185:
                j5.b(this.b);
                return;
            default:
                throw new C3405y10("Unknown CfInvoke opcode " + this.c);
        }
    }

    /* JADX WARN: Code duplicated, block: B:19:0x0075  */
    /* JADX WARN: Code duplicated, block: B:22:0x007f A[LOOP:0: B:21:0x007d->B:22:0x007f, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:25:0x00ad  */
    /* JADX WARN: Code duplicated, block: B:28:0x00c0  */
    /* JADX WARN: Code duplicated, block: B:33:0x00db A[ADDED_TO_REGION, ORIG_RETURN, RETURN] */
    @Override // com.android.tools.r8.internal.B7
    public final void a(C2102gl c2102gl, N8 n8, A8 a8) {
        C0684j2 c0684j2A;
        EnumC1953et enumC1953etA;
        C0737r2 c0737r2;
        C0684j2 c0684j2;
        int size;
        EnumC1812d20[] enumC1812d20Arr;
        Integer[] numArr;
        int i;
        C0737r2 c0737r3 = null;
        switch (this.c) {
            case 182:
                c0684j2A = c2102gl.d().A4.a(this.b);
                if (c0684j2A == null || c2102gl.o.Y().D1()) {
                    enumC1953etA = EnumC1953et.g;
                    c0684j2A = this.b;
                } else {
                    enumC1953etA = EnumC1953et.j;
                    c0737r3 = this.b.h;
                }
                c0684j2 = c0684j2A;
                c0737r2 = c0737r3;
                size = this.b.G0().size();
                if (enumC1953etA != EnumC1953et.e) {
                    size++;
                }
                enumC1812d20Arr = new EnumC1812d20[size];
                numArr = new Integer[size];
                for (i = size - 1; i >= 0; i--) {
                    H8 h8E = n8.e();
                    enumC1812d20Arr[i] = h8E.b;
                    numArr[i] = Integer.valueOf(h8E.a);
                }
                c2102gl.a(enumC1953etA, c0684j2, c0737r2, Arrays.asList(enumC1812d20Arr), Arrays.asList(numArr), this.d);
                if (!this.b.I0().Y0()) {
                    c2102gl.d(n8.a(this.b.I0()).a);
                }
                if (e && enumC1953etA != EnumC1953et.a(this.c, this.b, c2102gl.i(), c2102gl.o, c2102gl.g())) {
                    throw new AssertionError();
                }
                return;
            case 183:
                C0702m c0702m = c2102gl.o;
                com.android.tools.r8.graph.o5 o5VarI = c2102gl.i();
                C0684j2 c0684j3 = this.b;
                enumC1953etA = EnumC1953et.a(c0684j3, o5VarI, c0702m, c2102gl.g());
                c0737r2 = null;
                c0684j2 = c0684j3;
                size = this.b.G0().size();
                if (enumC1953etA != EnumC1953et.e) {
                    size++;
                }
                enumC1812d20Arr = new EnumC1812d20[size];
                numArr = new Integer[size];
                while (i >= 0) {
                    H8 h8E2 = n8.e();
                    enumC1812d20Arr[i] = h8E2.b;
                    numArr[i] = Integer.valueOf(h8E2.a);
                }
                c2102gl.a(enumC1953etA, c0684j2, c0737r2, Arrays.asList(enumC1812d20Arr), Arrays.asList(numArr), this.d);
                if (!this.b.I0().Y0()) {
                    c2102gl.d(n8.a(this.b.I0()).a);
                }
                if (e) {
                    return;
                } else {
                    return;
                }
            case 184:
                c0684j2A = this.b;
                enumC1953etA = EnumC1953et.e;
                c0684j2 = c0684j2A;
                c0737r2 = c0737r3;
                size = this.b.G0().size();
                if (enumC1953etA != EnumC1953et.e) {
                    size++;
                }
                enumC1812d20Arr = new EnumC1812d20[size];
                numArr = new Integer[size];
                while (i >= 0) {
                    H8 h8E3 = n8.e();
                    enumC1812d20Arr[i] = h8E3.b;
                    numArr[i] = Integer.valueOf(h8E3.a);
                }
                c2102gl.a(enumC1953etA, c0684j2, c0737r2, Arrays.asList(enumC1812d20Arr), Arrays.asList(numArr), this.d);
                if (!this.b.I0().Y0()) {
                    c2102gl.d(n8.a(this.b.I0()).a);
                }
                if (e) {
                    return;
                } else {
                    return;
                }
            case 185:
                c0684j2A = this.b;
                enumC1953etA = EnumC1953et.d;
                c0684j2 = c0684j2A;
                c0737r2 = c0737r3;
                size = this.b.G0().size();
                if (enumC1953etA != EnumC1953et.e) {
                    size++;
                }
                enumC1812d20Arr = new EnumC1812d20[size];
                numArr = new Integer[size];
                while (i >= 0) {
                    H8 h8E4 = n8.e();
                    enumC1812d20Arr[i] = h8E4.b;
                    numArr[i] = Integer.valueOf(h8E4.a);
                }
                c2102gl.a(enumC1953etA, c0684j2, c0737r2, Arrays.asList(enumC1812d20Arr), Arrays.asList(numArr), this.d);
                if (!this.b.I0().Y0()) {
                    c2102gl.d(n8.a(this.b.I0()).a);
                }
                if (e) {
                    return;
                } else {
                    return;
                }
            default:
                throw new C3405y10("unknown CfInvoke opcode " + this.c);
        }
    }

    @Override // com.android.tools.r8.internal.B7
    public final com.android.tools.r8.ir.optimize.V a(C3550d0 c3550d0, C0753u c0753u, com.android.tools.r8.graph.o5 o5Var) {
        EnumC1953et enumC1953etF;
        AbstractC0727q0 abstractC0727q0C;
        EnumC1953et enumC1953et;
        AbstractC1476Wj abstractC1476WjC = c3550d0.c();
        C0702m c0702mB = c3550d0.b();
        C0684j2 c0684j2 = this.b;
        switch (this.c) {
            case 182:
                EnumC1953et enumC1953et2 = EnumC1953et.g;
                if (c0684j2.e == o5Var.t() && (abstractC0727q0C = c0702mB.c(c0684j2.e)) != null && abstractC0727q0C.b(c0684j2) != null) {
                    enumC1953et2 = EnumC1953et.c;
                }
                KE keA = abstractC1476WjC.a(c0684j2, o5Var.getReference(), enumC1953et2);
                c0684j2 = (C0684j2) keA.b();
                enumC1953etF = keA.f();
                break;
            case 183:
                C0767v2 c0767v2M0 = c0753u.M0();
                C0722p1 c0722p1A = c0702mB.a();
                c0722p1A.getClass();
                if (c0684j2.f == c0722p1A.Y0) {
                    enumC1953et = EnumC1953et.c;
                } else if (c0767v2M0 != c0684j2.B0()) {
                    enumC1953et = EnumC1953et.f;
                } else {
                    AbstractC1476Wj abstractC1476WjE = c0702mB.E();
                    C0684j2 c0684j3 = this.b;
                    C0684j2 reference = o5Var.getReference();
                    EnumC1953et enumC1953et3 = EnumC1953et.c;
                    KE keA2 = abstractC1476WjE.a(c0684j3, reference, enumC1953et3, (AbstractC1476Wj) null);
                    com.android.tools.r8.graph.W0 w0C = o5Var.a().c((C0684j2) keA2.b());
                    if (w0C == null) {
                        enumC1953et = EnumC1953et.f;
                    } else {
                        if (o5Var.a().isInterface()) {
                            if (w0C.R0()) {
                                enumC1953et = EnumC1953et.f;
                            }
                        } else if (!e && !w0C.O0() && !keA2.f().f()) {
                            throw new AssertionError();
                        }
                        enumC1953et = enumC1953et3;
                    }
                }
                enumC1953etF = abstractC1476WjC.a(c0684j2, o5Var.getReference(), enumC1953et).f();
                break;
            case 184:
                KE keA3 = abstractC1476WjC.a(c0684j2, o5Var.getReference(), EnumC1953et.e);
                c0684j2 = (C0684j2) keA3.b();
                enumC1953etF = keA3.f();
                break;
            case 185:
                C0684j2 reference2 = o5Var.getReference();
                EnumC1953et enumC1953et4 = EnumC1953et.d;
                enumC1953etF = abstractC1476WjC.a(c0684j2, reference2, enumC1953et4).f();
                if (!e && enumC1953etF != enumC1953et4 && enumC1953etF != EnumC1953et.g) {
                    throw new AssertionError();
                }
                break;
            default:
                throw new C3405y10("Unexpected opcode " + this.c);
        }
        return c3550d0.a(c0684j2, o5Var, enumC1953etF);
    }

    @Override // com.android.tools.r8.internal.B7
    public final AbstractC2359k7 a(AbstractC2359k7 abstractC2359k7, C0702m c0702m, InterfaceC3107u6 interfaceC3107u6) {
        AbstractC2359k7 abstractC2359k7A = abstractC2359k7.a(c0702m, interfaceC3107u6, this.b.G0().a);
        if (this.c != 184) {
            if (this.b.B0().M0()) {
                abstractC2359k7A = abstractC2359k7A.i();
            } else {
                C0722p1 c0722p1A = c0702m.a();
                if (this.c == 183 && this.b.b(c0722p1A)) {
                    abstractC2359k7A = abstractC2359k7A.a(this.b, interfaceC3107u6);
                } else {
                    abstractC2359k7A = abstractC2359k7A.a(c0702m, interfaceC3107u6, this.b.B0());
                }
            }
        }
        return this.b.I0().Y0() ? abstractC2359k7A : abstractC2359k7A.a(interfaceC3107u6, this.b.I0());
    }
}
