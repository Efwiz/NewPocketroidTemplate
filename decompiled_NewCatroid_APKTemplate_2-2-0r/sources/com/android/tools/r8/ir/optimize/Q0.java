package com.android.tools.r8.ir.optimize;

import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.internal.AbstractC2412ko;
import com.android.tools.r8.internal.C1296Qj;
import com.android.tools.r8.internal.C1458Vr;
import com.android.tools.r8.internal.C1620aY;
import com.android.tools.r8.internal.C2732or;
import com.android.tools.r8.internal.C3332x4;
import com.android.tools.r8.internal.IY;
import com.android.tools.r8.internal.InterfaceC0841Ar;
import com.android.tools.r8.internal.InterfaceC2641no;
import com.android.tools.r8.internal.Lr;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;
import java.util.function.IntPredicate;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class Q0 {
    static final /* synthetic */ boolean j = true;
    private final C3332x4 a;
    private final C3332x4 b;
    private final InterfaceC2641no c;
    private final IY d;
    private C3332x4 f;
    private Lr i;
    private int e = -1;
    private boolean g = true;
    private boolean h = false;

    Q0(IY iy, InterfaceC2641no interfaceC2641no) {
        this.a = iy.h();
        this.b = iy.J2();
        this.c = interfaceC2641no;
        this.d = iy;
    }

    public final boolean a(int i) {
        if (b()) {
            return i == this.e;
        }
        Lr lr = this.i;
        if (lr != null) {
            return !lr.f(i);
        }
        if (j || !this.g) {
            return true;
        }
        throw new AssertionError();
    }

    public final boolean b() {
        return this.e >= 0;
    }

    public final boolean c() {
        return this.g;
    }

    final void d() {
        if (!j && b()) {
            throw new AssertionError();
        }
        this.g = false;
    }

    final boolean e() {
        return this.h;
    }

    final void f() {
        Lr lr;
        int iC;
        int i;
        AbstractC2412ko c1620aY;
        Lr lr2;
        if (!j && (lr2 = this.i) != null && lr2.isEmpty()) {
            throw new AssertionError();
        }
        if (this.i == null && !b() && this.g) {
            return;
        }
        int size = this.a.z().size();
        IntPredicate intPredicateA = a();
        C2732or c2732or = new C2732or(16);
        int i2 = 0;
        for (int i3 = 0; i3 < this.a.z().size(); i3++) {
            if (intPredicateA.test(i3)) {
                C3332x4 c3332x4 = this.a.z().get(i3);
                c3332x4.a(this.a, (Set) null);
                c2732or.a(i3);
                if (c3332x4.y().isEmpty()) {
                    this.h = true;
                }
            }
        }
        c2732or.sort(Comparator.naturalOrder());
        this.a.a(c2732or);
        if (b() || ((lr = this.i) != null && lr.size() == this.d.L2())) {
            if (!j && b() && this.f == null) {
                throw new AssertionError();
            }
            C3332x4 c3332x5 = b() ? this.f : this.b;
            InterfaceC2641no interfaceC2641no = this.c;
            C1296Qj c1296Qj = new C1296Qj();
            c1296Qj.a(c3332x5);
            interfaceC2641no.d(c1296Qj);
            return;
        }
        int[] iArr = new int[size];
        InterfaceC0841Ar interfaceC0841ArListIterator = c2732or.listIterator(0);
        while (interfaceC0841ArListIterator.hasNext()) {
            iArr[Integer.valueOf(((com.android.tools.r8.internal.K) interfaceC0841ArListIterator).t()).intValue()] = 1;
        }
        for (int i4 = 1; i4 < size; i4++) {
            iArr[i4] = iArr[i4] + iArr[i4 - 1];
        }
        int iL2 = this.d.L2();
        if (!this.g) {
            for (int iL3 = this.d.L2() - 1; iL3 >= 0; iL3--) {
                if (a(iL3)) {
                    iL2 = iL3;
                    break;
                }
            }
        }
        Lr lr3 = this.i;
        int iL4 = this.d.L2() - (com.android.tools.r8.utils.M.a(!this.g) + (lr3 != null ? lr3.size() : 0));
        int[] iArr2 = new int[iL4];
        int i5 = 0;
        for (int i6 = 0; i6 < iL2; i6++) {
            if (a(i6)) {
                int iC2 = this.d.c(i6) - iArr[this.d.c(i6)];
                iArr2[i5] = iC2;
                if (!j && iC2 >= this.a.z().size()) {
                    throw new AssertionError();
                }
                i5++;
            }
        }
        if (this.g) {
            iC = this.d.K2();
            i = iArr[this.d.K2()];
        } else {
            iC = this.d.c(iL2);
            i = iArr[this.d.c(iL2)];
        }
        int i7 = iC - i;
        if (this.d.T1()) {
            C1458Vr c1458VrW = this.d.W();
            int[] iArr3 = new int[iL4];
            int i8 = 0;
            while (i2 < iL2) {
                if (a(i2)) {
                    iArr3[i8] = c1458VrW.f(i2);
                    i8++;
                }
                i2++;
            }
            c1620aY = new C1458Vr(i7, this.d.value(), iArr3, iArr2);
        } else {
            if (!j && !this.d.y2()) {
                throw new AssertionError();
            }
            C1620aY c1620aYI0 = this.d.I0();
            C0759u2[] c0759u2Arr = new C0759u2[iL4];
            int i9 = 0;
            while (i2 < iL2) {
                if (a(i2)) {
                    c0759u2Arr[i9] = c1620aYI0.f(i2);
                    i9++;
                }
                i2++;
            }
            c1620aY = new C1620aY(this.d.value(), c0759u2Arr, iArr2, i7);
        }
        this.c.d(c1620aY);
    }

    final void b(int i) {
        if (!j && this.e >= 0) {
            throw new AssertionError();
        }
        this.e = i;
        this.f = this.d.e(i);
        this.g = false;
    }

    final void c(int i) {
        if (this.i == null) {
            this.i = new Lr(16);
        }
        this.i.a(i);
    }

    private IntPredicate a() {
        final int[] iArr = new int[this.a.z().size()];
        for (int i = 0; i < this.d.L2(); i++) {
            if (a(i)) {
                int iC = this.d.c(i);
                iArr[iC] = iArr[iC] + 1;
            }
        }
        if (this.g) {
            int iK2 = this.d.K2();
            iArr[iK2] = iArr[iK2] + 1;
        }
        Iterator it = this.a.o().c().iterator();
        while (it.hasNext()) {
            int iIntValue = ((Integer) it.next()).intValue();
            iArr[iIntValue] = iArr[iIntValue] + 1;
        }
        return new IntPredicate() { // from class: com.android.tools.r8.ir.optimize.Q0$$ExternalSyntheticLambda0
            @Override // java.util.function.IntPredicate
            public final boolean test(int i2) {
                return Q0.a(iArr, i2);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(int[] iArr, int i) {
        return iArr[i] == 0;
    }
}
