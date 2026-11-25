package com.android.tools.r8.ir.optimize;

import com.android.tools.r8.AbstractC0403b;
import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.internal.AbstractC2412ko;
import com.android.tools.r8.internal.C3332x4;
import com.android.tools.r8.utils.AbstractC3951q0;
import com.android.tools.r8.utils.C3;
import com.android.tools.r8.utils.C3932m1;
import com.android.tools.r8.utils.D3;
import com.android.tools.r8.utils.InterfaceC3936n0;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class I0 extends AbstractC3951q0 {
    static final /* synthetic */ boolean i = true;
    final /* synthetic */ C3932m1 f;
    final /* synthetic */ J0 g;
    final /* synthetic */ O0 h;

    I0(C3932m1 c3932m1, J0 j0, O0 o0) {
        this.f = c3932m1;
        this.g = j0;
        this.h = o0;
    }

    @Override // com.android.tools.r8.utils.AbstractC3951q0
    protected final D3 a(InterfaceC3936n0 interfaceC3936n0, Function function) {
        int i2;
        Iterator<AbstractC2412ko> it = ((C3332x4) interfaceC3936n0.c()).p().iterator();
        int i3 = 1;
        while (true) {
            i2 = 2;
            if (!it.hasNext()) {
                break;
            }
            AbstractC2412ko next = it.next();
            if (this.f.b() <= this.g.a()) {
                int iA = this.g.a(next);
                if (iA != 1) {
                    if (iA == 2) {
                        this.h.a.add(next);
                    }
                    i3 = iA;
                    break;
                }
                i3 = iA;
            } else {
                return D3.d();
            }
        }
        if (i3 == 1) {
            Iterator it2 = this.g.a((C3332x4) interfaceC3936n0.c()).iterator();
            while (it2.hasNext()) {
                if (((InterfaceC3936n0) function.apply((C3332x4) it2.next())).d()) {
                    return D3.d();
                }
            }
        }
        int iB = AbstractC0403b.b(i3);
        if (iB == 0) {
            i2 = 4;
        } else if (iB != 1) {
            i2 = 3;
            if (!K0.a && i3 != 3) {
                throw new AssertionError();
            }
        }
        interfaceC3936n0.a(new M0(i2, AbstractC1639am.i()));
        return D3.e();
    }

    @Override // com.android.tools.r8.utils.AbstractC3951q0
    protected final C3 a(InterfaceC3936n0 interfaceC3936n0, List list) {
        M0 m0 = (M0) interfaceC3936n0.a();
        if (L0.a(m0.a)) {
            if (!M0.c && !L0.a(m0.a)) {
                throw new AssertionError();
            }
            int i2 = list.isEmpty() ? 3 : 4;
            Iterator it = list.iterator();
            while (it.hasNext()) {
                M0 m1 = (M0) ((InterfaceC3936n0) it.next()).a();
                if (!M0.c && L0.a(m1.a)) {
                    throw new AssertionError();
                }
                int i3 = m1.a;
                if (!L0.b(i2) && !L0.a(i3)) {
                    if (L0.a(i2) || L0.b(i3)) {
                        i2 = i3;
                    } else if (i2 != i3) {
                        i2 = 1;
                    }
                }
            }
            if (!M0.c && L0.a(i2)) {
                throw new AssertionError();
            }
            ArrayList arrayList = new ArrayList();
            if (L0.b(i2)) {
                Iterator it2 = list.iterator();
                while (it2.hasNext()) {
                    InterfaceC3936n0 interfaceC3936n1 = (InterfaceC3936n0) it2.next();
                    if (((M0) interfaceC3936n1.a()).a == 3) {
                        arrayList.add((C3332x4) interfaceC3936n1.c());
                    } else if (L0.b(((M0) interfaceC3936n1.a()).a)) {
                        arrayList.addAll(((M0) interfaceC3936n1.a()).b);
                    }
                }
            }
            m0 = new M0(i2, arrayList);
        } else {
            boolean z = i;
            if (!z) {
                int i4 = m0.a;
                AbstractC0403b.a(i4);
                if (i4 != 2 && m0.a != 3) {
                    throw new AssertionError();
                }
            }
            if (!z && !list.isEmpty()) {
                throw new AssertionError();
            }
        }
        interfaceC3936n0.a(m0);
        return D3.b(m0);
    }
}
