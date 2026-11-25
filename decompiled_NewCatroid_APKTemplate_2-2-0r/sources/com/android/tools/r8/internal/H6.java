package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0767v2;
import java.util.Arrays;
import java.util.Deque;
import java.util.Iterator;
import name.antonsmirnov.firmata.FormatHelper;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class H6 {
    static final /* synthetic */ boolean c = true;
    final C0702m a;
    final C0722p1 b;

    public H6(C0702m c0702m) {
        this.a = c0702m;
        this.b = c0702m.a();
    }

    boolean a(C0767v2 c0767v2, C0767v2 c0767v3) {
        return true;
    }

    public final boolean b(C0767v2 c0767v2, C0767v2 c0767v3) {
        boolean z = c;
        if (!z && c0767v3.U0()) {
            throw new AssertionError();
        }
        C0722p1 c0722p1 = this.b;
        if (c0767v2.N0() || c0767v2.e.w0() == 'B' || c0767v2.e.w0() == 'C' || c0767v2.R0() || c0767v2.e.w0() == 'S') {
            c0767v2 = c0722p1.A1;
        }
        C0722p1 c0722p2 = this.b;
        if (c0767v3.N0() || c0767v3.e.w0() == 'B' || c0767v3.e.w0() == 'C' || c0767v3.R0() || c0767v3.e.w0() == 'S') {
            c0767v3 = c0722p2.A1;
        }
        if (c0767v2 == c0767v3) {
            return true;
        }
        if (c0767v2.W0() || c0767v3.W0()) {
            return false;
        }
        if (!z && !c0767v2.X0()) {
            throw new AssertionError();
        }
        if (!z && !c0767v3.X0()) {
            throw new AssertionError();
        }
        if (c0767v3 == this.b.Z1 || c0767v2.U0()) {
            return true;
        }
        if (c0767v3.M0()) {
            return c0767v2.M0() && b(c0767v2.a(this.b), c0767v3.a(this.b));
        }
        if (!z && !c0767v3.O0()) {
            throw new AssertionError();
        }
        if (c0767v2.M0()) {
            C0722p1 c0722p3 = this.b;
            return c0767v3 == c0722p3.w5 || c0767v3 == c0722p3.u5;
        }
        if (z || c0767v2.O0()) {
            return a(c0767v2, c0767v3);
        }
        throw new AssertionError();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final boolean a(InterfaceC1348Si interfaceC1348Si, InterfaceC1348Si interfaceC1348Si2) {
        if (interfaceC1348Si.e() != interfaceC1348Si2.e()) {
            return false;
        }
        if (!interfaceC1348Si.e()) {
            return a(interfaceC1348Si.s(), interfaceC1348Si2.s());
        }
        InterfaceC2782pV interfaceC2782pVV = interfaceC1348Si.v();
        InterfaceC1348Si interfaceC1348SiV = interfaceC1348Si2.v();
        if (!interfaceC2782pVV.equals(interfaceC1348SiV)) {
            AbstractC2826q4 abstractC2826q4 = (AbstractC2826q4) interfaceC1348SiV;
            abstractC2826q4.getClass();
            if (!(abstractC2826q4 instanceof FK)) {
                AbstractC2826q4 abstractC2826q5 = (AbstractC2826q4) interfaceC2782pVV;
                if (abstractC2826q5 instanceof FK) {
                    return false;
                }
                if ((abstractC2826q5 instanceof C1733c10) && (abstractC2826q4 instanceof C1733c10)) {
                    C0767v2 c0767v2D = interfaceC2782pVV.D();
                    C0767v2 c0767v2D2 = interfaceC1348SiV.D();
                    if (c0767v2D != null && c0767v2D2 != null && c0767v2D != c0767v2D2) {
                        return false;
                    }
                } else if (interfaceC1348SiV.isPrimitive()) {
                    if (!interfaceC2782pVV.isPrimitive() || !interfaceC2782pVV.H().T() || !interfaceC1348SiV.H().T()) {
                        return false;
                    }
                } else {
                    if (interfaceC2782pVV.isPrimitive() || !interfaceC1348SiV.a()) {
                        return false;
                    }
                    if (interfaceC2782pVV.a()) {
                        return b(interfaceC2782pVV.x().a(this.b), interfaceC1348SiV.x().a(this.b));
                    }
                    if (interfaceC1348SiV.x().a(this.b) != this.b.Z1) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static boolean a(C20 c20, C20 c21) {
        if (!c) {
            AbstractC2826q4 abstractC2826q4 = (AbstractC2826q4) c20;
            abstractC2826q4.getClass();
            if (abstractC2826q4 instanceof C3513zZ) {
                throw new AssertionError();
            }
        }
        return c20.a(c21) == c21;
    }

    public final boolean a(C0767v2 c0767v2, EnumC1812d20 enumC1812d20) {
        return b(c0767v2, enumC1812d20.a(this.b));
    }

    public final E6 a(InterfaceC1456Vp interfaceC1456Vp, InterfaceC1456Vp interfaceC1456Vp2) {
        int iMax = Math.max(interfaceC1456Vp.isEmpty() ? -1 : interfaceC1456Vp.d(), interfaceC1456Vp2.isEmpty() ? -1 : interfaceC1456Vp2.d());
        for (int i = 0; i <= iMax; i++) {
            InterfaceC1348Si interfaceC1348SiB = interfaceC1456Vp.a(i) ? (InterfaceC1348Si) interfaceC1456Vp.get(i) : InterfaceC1348Si.B();
            InterfaceC1348Si interfaceC1348SiB2 = interfaceC1456Vp2.a(i) ? (InterfaceC1348Si) interfaceC1456Vp2.get(i) : InterfaceC1348Si.B();
            if (interfaceC1348SiB.P() && interfaceC1348SiB2.q()) {
                interfaceC1348SiB2 = InterfaceC1348Si.Q();
            }
            if (!a(interfaceC1348SiB, interfaceC1348SiB2)) {
                return a(interfaceC1456Vp, interfaceC1456Vp2, interfaceC1348SiB, interfaceC1348SiB2, i);
            }
        }
        return new G6();
    }

    private static F6 a(InterfaceC1456Vp interfaceC1456Vp, InterfaceC1456Vp interfaceC1456Vp2, InterfaceC1348Si interfaceC1348Si, InterfaceC1348Si interfaceC1348Si2, int i) {
        return new F6("Could not assign '" + com.android.tools.r8.utils.Z1.b(interfaceC1456Vp) + "' to '" + com.android.tools.r8.utils.Z1.b(interfaceC1456Vp2) + "'. The local at index " + i + " with '" + interfaceC1348Si + "' not being assignable to '" + interfaceC1348Si2 + FormatHelper.QUOTE);
    }

    public final E6 a(Deque deque, Deque deque2) {
        if (deque.size() != deque2.size()) {
            return new F6("Source stack " + Arrays.toString(deque.toArray()) + " and destination stack " + Arrays.toString(deque2.toArray()) + " is not the same size");
        }
        Iterator it = deque2.iterator();
        Iterator it2 = deque.iterator();
        int i = 0;
        while (it2.hasNext()) {
            InterfaceC3054tM interfaceC3054tM = (InterfaceC3054tM) it2.next();
            InterfaceC3054tM interfaceC3054tM2 = (InterfaceC3054tM) it.next();
            if (!a(interfaceC3054tM, interfaceC3054tM2)) {
                return new F6("Could not assign '" + Arrays.toString(deque.toArray()) + "' to '" + Arrays.toString(deque2.toArray()) + "'. The stack value at index " + i + " (from top) with '" + interfaceC3054tM + "' not being assignable to '" + interfaceC3054tM2 + FormatHelper.QUOTE);
            }
            i++;
        }
        return new G6();
    }
}
