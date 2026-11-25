package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0717o2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.utils.InterfaceC3880d;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.dc, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1853dc extends AbstractC1777cc implements InterfaceC1935ec, InterfaceC2013fc {
    static final /* synthetic */ boolean d = true;
    boolean b;
    List c;

    public C1853dc(List list, boolean z) {
        boolean z2 = d;
        if (!z2 && !AbstractC1699bX.a(AbstractC2734ot.d(list)).noneMatch(new Predicate() { // from class: com.android.tools.r8.internal.dc$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return C1853dc.a((AbstractC3122uL) obj);
            }
        })) {
            throw new AssertionError();
        }
        this.b = z;
        this.c = list;
        if (!z2 && m()) {
            throw new AssertionError("Must use UnknownMethodState instead");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean a(AbstractC3122uL abstractC3122uL) {
        if (abstractC3122uL.d()) {
            AbstractC2168hc abstractC2168hcA = abstractC3122uL.a();
            abstractC2168hcA.getClass();
            if (abstractC2168hcA instanceof C2716oc) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.tools.r8.internal.ZE, com.android.tools.r8.internal.YE
    public final C1853dc b() {
        return this;
    }

    @Override // com.android.tools.r8.internal.ZE, com.android.tools.r8.internal.YE
    public final InterfaceC1935ec g() {
        return this;
    }

    public final List k() {
        return this.c;
    }

    public final boolean l() {
        return AbstractC2734ot.b(this.c, new InterfaceC3205vM() { // from class: com.android.tools.r8.internal.dc$$ExternalSyntheticLambda2
            @Override // com.android.tools.r8.internal.InterfaceC3205vM
            public final boolean apply(Object obj) {
                return ((AbstractC3122uL) obj).c();
            }
        });
    }

    public final boolean m() {
        return a(this.c, this.b);
    }

    public final boolean n() {
        return this.b;
    }

    public final int o() {
        return this.c.size();
    }

    public static InterfaceC2013fc a(ArrayList arrayList, boolean z) {
        if (a((List) arrayList, z)) {
            return C2508m10.k();
        }
        return new C1853dc(arrayList, z);
    }

    public final AbstractC3122uL a(int i) {
        return (AbstractC3122uL) this.c.get(i);
    }

    private static boolean a(List list, boolean z) {
        return z && AbstractC2734ot.a((Iterable) list, new InterfaceC3205vM() { // from class: com.android.tools.r8.internal.dc$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.internal.InterfaceC3205vM
            public final boolean apply(Object obj) {
                return ((AbstractC3122uL) obj).e();
            }
        });
    }

    @Override // com.android.tools.r8.internal.YE
    public final InterfaceC2013fc a() {
        ArrayList arrayList = new ArrayList(this.c.size());
        Iterator it = this.c.iterator();
        while (it.hasNext()) {
            arrayList.add(((AbstractC3122uL) it.next()).f());
        }
        return new C1853dc(arrayList, this.b);
    }

    @Override // com.android.tools.r8.internal.YE
    public final YE a() {
        ArrayList arrayList = new ArrayList(this.c.size());
        Iterator it = this.c.iterator();
        while (it.hasNext()) {
            arrayList.add(((AbstractC3122uL) it.next()).f());
        }
        return new C1853dc(arrayList, this.b);
    }

    public final void a(int i, AbstractC3122uL abstractC3122uL) {
        if (!d && i != 0 && abstractC3122uL.d()) {
            AbstractC2168hc abstractC2168hcA = abstractC3122uL.a();
            abstractC2168hcA.getClass();
            if (abstractC2168hcA instanceof C2716oc) {
                throw new AssertionError();
            }
        }
        this.c.set(i, abstractC3122uL);
    }

    public final InterfaceC2013fc a(C0702m c0702m, AbstractC0717o2 abstractC0717o2, C1853dc c1853dc, FW fw) {
        if (this.c.size() != c1853dc.c.size()) {
            if (d) {
                return C2508m10.k();
            }
            throw new AssertionError();
        }
        int i = 1;
        if (c1853dc.b) {
            this.b = true;
        }
        int i2 = 0;
        if (this.c.size() <= abstractC0717o2.b().t0()) {
            i = 0;
        } else {
            if (!d && this.c.size() != abstractC0717o2.b().t0() + 1) {
                throw new AssertionError();
            }
            AbstractC3122uL abstractC3122uL = (AbstractC3122uL) this.c.get(0);
            AbstractC3122uL abstractC3122uL2 = (AbstractC3122uL) c1853dc.c.get(0);
            List list = this.c;
            abstractC3122uL.getClass();
            list.set(0, abstractC3122uL.a(c0702m, abstractC3122uL2, null, fw, InterfaceC3880d.a));
        }
        while (i < this.c.size()) {
            AbstractC3122uL abstractC3122uL3 = (AbstractC3122uL) this.c.get(i);
            AbstractC3122uL abstractC3122uL4 = (AbstractC3122uL) c1853dc.c.get(i);
            C0767v2 c0767v2K = abstractC0717o2.b().k(i2);
            List list2 = this.c;
            abstractC3122uL3.getClass();
            list2.set(i, abstractC3122uL3.a(c0702m, abstractC3122uL4, c0767v2K, fw, InterfaceC3880d.a));
            if (!d && ((AbstractC3122uL) this.c.get(i)).d()) {
                AbstractC2168hc abstractC2168hcA = ((AbstractC3122uL) this.c.get(i)).a();
                abstractC2168hcA.getClass();
                if (abstractC2168hcA instanceof C2716oc) {
                    throw new AssertionError();
                }
            }
            i++;
            i2++;
        }
        return m() ? C2508m10.k() : this;
    }
}
