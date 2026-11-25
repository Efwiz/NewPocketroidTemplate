package com.android.tools.r8.internal;

import com.android.tools.r8.utils.InterfaceC3937n1;
import com.android.tools.r8.utils.InterfaceC3942o1;
import com.android.tools.r8.utils.InterfaceC3947p1;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.BiFunction;
import java.util.function.Predicate;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class BH extends AbstractC3350xL {
    private static final O3 b = O3.a();
    static final /* synthetic */ boolean c = true;
    private final InterfaceC2947rp a;

    private BH(C0905Cp c0905Cp) {
        if (!c && c0905Cp.isEmpty()) {
            throw new AssertionError("Should use bottom() instead");
        }
        this.a = c0905Cp;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static AbstractC3204vL b(U1 u1, AbstractC3204vL abstractC3204vL) {
        return C2660o10.l();
    }

    public final BH a(BH bh) {
        if (this instanceof D5) {
            return bh;
        }
        bh.getClass();
        if (bh instanceof D5) {
            return this;
        }
        final C0905Cp c0905Cp = new C0905Cp(this.a);
        bh.a(new InterfaceC3937n1() { // from class: com.android.tools.r8.internal.BH$$ExternalSyntheticLambda2
            @Override // com.android.tools.r8.utils.InterfaceC3937n1
            public final void a(int i, Object obj) {
                BH.a(c0905Cp, i, (AbstractC3274wL) obj);
            }
        });
        return new BH(c0905Cp);
    }

    @Override // com.android.tools.r8.internal.AbstractC3350xL
    public final BH e() {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC3327x1
    public final boolean equals(Object obj) {
        if (obj == null || BH.class != obj.getClass()) {
            return false;
        }
        return this.a.equals(((BH) obj).a);
    }

    @Override // com.android.tools.r8.internal.AbstractC3350xL
    final AbstractC3350xL f() {
        BH bhA = a(new InterfaceC3947p1() { // from class: com.android.tools.r8.internal.BH$$ExternalSyntheticLambda5
            @Override // com.android.tools.r8.utils.InterfaceC3947p1
            public final Object a(int i, Object obj) {
                return BH.b(i, (AbstractC3274wL) obj);
            }
        });
        InterfaceC1912eK it = bhA.a.values().iterator();
        boolean z = true;
        boolean z2 = true;
        while (it.hasNext()) {
            AbstractC3274wL abstractC3274wL = (AbstractC3274wL) it.next();
            abstractC3274wL.getClass();
            if (!(abstractC3274wL instanceof C5)) {
                z = false;
            }
            if (!(abstractC3274wL instanceof C2748p10)) {
                z2 = false;
            }
        }
        if (z) {
            return D5.h();
        }
        return z2 ? C2821q10.h() : bhA;
    }

    public final int hashCode() {
        return this.a.hashCode();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ AbstractC3274wL b(int i, AbstractC3274wL abstractC3274wL) {
        return abstractC3274wL.b();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(InterfaceC2947rp interfaceC2947rp, int i, AbstractC3274wL abstractC3274wL) {
        AbstractC3274wL abstractC3274wLC;
        Object objC = C5.c();
        Object obj = interfaceC2947rp.get(i);
        if (obj != null) {
            objC = obj;
        }
        AbstractC3274wL abstractC3274wL2 = (AbstractC3274wL) objC;
        abstractC3274wL.getClass();
        if (!(abstractC3274wL instanceof C2748p10) && !(abstractC3274wL2 instanceof C2748p10)) {
            abstractC3274wLC = abstractC3274wL.a().a(abstractC3274wL2.a());
        } else {
            abstractC3274wLC = C2748p10.c();
        }
        interfaceC2947rp.a(i, abstractC3274wLC);
    }

    public static AbstractC3350xL a(C0905Cp c0905Cp) {
        if (c0905Cp.isEmpty()) {
            return D5.h();
        }
        return new BH(c0905Cp);
    }

    public final boolean a(InterfaceC3942o1 interfaceC3942o1) {
        InterfaceC1912eK it = this.a.b().iterator();
        while (it.hasNext()) {
            InterfaceC2806pp interfaceC2806pp = (InterfaceC2806pp) it.next();
            if (!interfaceC3942o1.a(interfaceC2806pp.a(), (AbstractC3274wL) interfaceC2806pp.getValue())) {
                return false;
            }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static AbstractC3204vL a(U1 u1, AbstractC3204vL abstractC3204vL) {
        return C2660o10.l();
    }

    @Override // com.android.tools.r8.internal.AbstractC3350xL
    final AbstractC3350xL a(int i, AbstractC3274wL abstractC3274wL) {
        C0905Cp c0905Cp = new C0905Cp(this.a);
        c0905Cp.a(i, abstractC3274wL);
        if (c0905Cp.isEmpty()) {
            return D5.h();
        }
        return new BH(c0905Cp);
    }

    public final void a(InterfaceC3937n1 interfaceC3937n1) {
        InterfaceC1912eK it = this.a.b().iterator();
        while (it.hasNext()) {
            InterfaceC2806pp interfaceC2806pp = (InterfaceC2806pp) it.next();
            interfaceC3937n1.a(interfaceC2806pp.a(), interfaceC2806pp.getValue());
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC3350xL
    public final AbstractC3274wL a(int i) {
        return (AbstractC3274wL) this.a.getOrDefault(Integer.valueOf(i), C2748p10.c());
    }

    final BH a(S10 s10) {
        return a(s10, new BiFunction() { // from class: com.android.tools.r8.internal.BH$$ExternalSyntheticLambda4
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return BH.a((U1) obj, (AbstractC3204vL) obj2);
            }
        });
    }

    final BH a(ArrayList arrayList) {
        if (arrayList.isEmpty()) {
            return this;
        }
        final int[] iArr = new int[arrayList.size()];
        Iterator it = arrayList.iterator();
        for (int i = 0; i < arrayList.size(); i++) {
            iArr[i] = ((S10) it.next()).q().q().J2();
        }
        return a(new InterfaceC3947p1() { // from class: com.android.tools.r8.internal.BH$$ExternalSyntheticLambda3
            @Override // com.android.tools.r8.utils.InterfaceC3947p1
            public final Object a(int i2, Object obj) {
                return BH.a(iArr, i2, (AbstractC3274wL) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static AbstractC3274wL a(int[] iArr, int i, AbstractC3274wL abstractC3274wL) {
        int i2 = com.android.tools.r8.utils.B.b;
        for (int i3 : iArr) {
            if (i3 == i) {
                return abstractC3274wL.a(new BiFunction() { // from class: com.android.tools.r8.internal.BH$$ExternalSyntheticLambda1
                    @Override // java.util.function.BiFunction
                    public final Object apply(Object obj, Object obj2) {
                        return BH.b((U1) obj, (AbstractC3204vL) obj2);
                    }
                });
            }
        }
        return abstractC3274wL;
    }

    final BH a(Iterable iterable, Predicate predicate) {
        ArrayList arrayList = new ArrayList();
        Iterator it = iterable.iterator();
        while (it.hasNext()) {
            S10 s10 = (S10) it.next();
            O3 o3 = b;
            s10.getClass();
            S10 s10A = s10.a(o3, CM.a());
            if (predicate.test(s10A)) {
                arrayList.add(s10A);
            }
        }
        return a(arrayList);
    }

    final BH a(S10 s10, final BiFunction biFunction) {
        O3 o3 = b;
        s10.getClass();
        S10 s10A = s10.a(o3, CM.a());
        if (!c && !s10A.H()) {
            throw new AssertionError();
        }
        final int iJ2 = s10A.q().q().J2();
        return a(new InterfaceC3947p1() { // from class: com.android.tools.r8.internal.BH$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.utils.InterfaceC3947p1
            public final Object a(int i, Object obj) {
                return BH.a(iJ2, biFunction, i, (AbstractC3274wL) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ AbstractC3274wL a(int i, BiFunction biFunction, int i2, AbstractC3274wL abstractC3274wL) {
        return i2 == i ? abstractC3274wL.a(biFunction) : abstractC3274wL;
    }

    final BH a(InterfaceC3947p1 interfaceC3947p1) {
        InterfaceC2806pp interfaceC2806pp;
        int iA;
        InterfaceC1912eK it = this.a.b().iterator();
        C0905Cp c0905Cp = null;
        while (it.hasNext()) {
            InterfaceC2806pp interfaceC2806pp2 = (InterfaceC2806pp) it.next();
            int iA2 = interfaceC2806pp2.a();
            AbstractC3274wL abstractC3274wL = (AbstractC3274wL) interfaceC2806pp2.getValue();
            AbstractC3274wL abstractC3274wL2 = (AbstractC3274wL) interfaceC3947p1.a(iA2, (AbstractC3274wL) interfaceC2806pp2.getValue());
            if (abstractC3274wL2 != abstractC3274wL) {
                if (c0905Cp == null) {
                    c0905Cp = new C0905Cp(16);
                    InterfaceC1912eK it2 = this.a.b().iterator();
                    while (it2.hasNext() && (iA = (interfaceC2806pp = (InterfaceC2806pp) it2.next()).a()) != iA2) {
                        c0905Cp.a(iA, (AbstractC3274wL) interfaceC2806pp.getValue());
                    }
                }
                c0905Cp.a(iA2, abstractC3274wL2);
            } else if (c0905Cp != null) {
                c0905Cp.a(iA2, abstractC3274wL2);
            }
        }
        return c0905Cp != null ? new BH(c0905Cp) : this;
    }
}
