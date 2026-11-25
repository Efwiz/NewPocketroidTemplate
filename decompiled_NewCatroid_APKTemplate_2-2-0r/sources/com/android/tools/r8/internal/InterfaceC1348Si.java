package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.naming.AbstractC3649o0;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Si, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public interface InterfaceC1348Si {
    public static final /* synthetic */ int a = 0;

    static {
        boolean z = AbstractC1322Ri.a;
    }

    static FK B() {
        return FK.c;
    }

    static C0949Ef C() {
        return C0949Ef.d;
    }

    static WC L() {
        return WC.d;
    }

    static C3513zZ Q() {
        return C3513zZ.c;
    }

    static InterfaceC2728on a(C0767v2 c0767v2) {
        if (c0767v2.W0()) {
            if (AbstractC1322Ri.a || c0767v2.W0()) {
                return a(c0767v2.D0().w0());
            }
            throw new AssertionError();
        }
        if (AbstractC1322Ri.a || c0767v2.X0()) {
            return c0767v2.U0() ? TH.c : b(c0767v2);
        }
        throw new AssertionError();
    }

    static C0921Df b() {
        return C0921Df.c;
    }

    static TH c() {
        return TH.c;
    }

    static VC d() {
        return VC.c;
    }

    static C1883e10 g() {
        return C1883e10.c;
    }

    static C3461yr h() {
        return C3461yr.c;
    }

    static C3522zi i() {
        return C3522zi.c;
    }

    boolean A();

    C0767v2 D();

    boolean E();

    InterfaceC3054tM F();

    boolean G();

    AbstractC2926rV H();

    boolean I();

    boolean J();

    AbstractC1579a10 K();

    boolean M();

    boolean N();

    TH O();

    boolean P();

    M7 R();

    AbstractC2804pn S();

    C0767v2 a(C0722p1 c0722p1);

    C0767v2 a(C0722p1 c0722p1, C0767v2 c0767v2);

    Object a(AbstractC1476Wj abstractC1476Wj, AbstractC3649o0 abstractC3649o0);

    boolean a();

    VM asPrimitive();

    boolean e();

    C2875qn f();

    boolean isPrimitive();

    C2945rn j();

    boolean k();

    boolean l();

    boolean m();

    boolean n();

    C1883e10 o();

    D20 p();

    boolean q();

    boolean r();

    C20 s();

    int t();

    boolean u();

    InterfaceC2782pV v();

    boolean w();

    InterfaceC3014sn x();

    boolean y();

    C1733c10 z();

    static C2945rn b(C0767v2 c0767v2) {
        boolean z = AbstractC1322Ri.a;
        if (!z && !c0767v2.X0()) {
            throw new AssertionError();
        }
        if (z || !c0767v2.U0()) {
            return new C2945rn(c0767v2);
        }
        throw new AssertionError();
    }

    static InterfaceC2728on a(AbstractC3472z00 abstractC3472z00) {
        if (abstractC3472z00.H()) {
            return a(abstractC3472z00.c().P().charAt(0));
        }
        BS bsD = abstractC3472z00.d();
        if (bsD.E()) {
            return TH.c;
        }
        if (AbstractC1322Ri.a || !bsD.E()) {
            return new C2875qn(bsD);
        }
        throw new AssertionError();
    }

    static VM a(char c) {
        if (c == 'F') {
            return C3522zi.c;
        }
        if (c == 'S') {
            return UU.c;
        }
        if (c == 'Z') {
            return C2204i5.c;
        }
        if (c == 'I') {
            return C3461yr.c;
        }
        if (c == 'J') {
            return VC.c;
        }
        switch (c) {
            case 'B':
                return S5.c;
            case 'C':
                return C1592a9.c;
            case 'D':
                return C0921Df.c;
            default:
                throw new C3405y10("Unexpected primitive type: " + c);
        }
    }

    static C1733c10 a(M7 m7, C0767v2 c0767v2) {
        return new C1733c10(m7, c0767v2);
    }

    static InterfaceC2728on a(EnumC2523mE enumC2523mE, C0722p1 c0722p1) {
        if (!AbstractC1322Ri.a && !enumC2523mE.a()) {
            throw new AssertionError();
        }
        switch (enumC2523mE.ordinal()) {
            case 0:
                return b(c0722p1.Z1);
            case 1:
            case 2:
            case 3:
            case 4:
                return C3461yr.c;
            case 5:
                return C3522zi.c;
            case 6:
                return VC.c;
            case 7:
                return C0921Df.c;
            default:
                throw new C3405y10("Unexpected MemberType: " + enumC2523mE);
        }
    }

    default InterfaceC1348Si a(Function function) {
        if (AbstractC1322Ri.a || !J()) {
            return this;
        }
        throw new AssertionError();
    }
}
