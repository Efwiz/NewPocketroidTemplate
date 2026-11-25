package com.android.tools.r8.internal;

import com.android.tools.r8.utils.AbstractC3951q0;
import com.android.tools.r8.utils.InterfaceC3936n0;
import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.internal.lX, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2469lX extends AbstractC3951q0 {
    static final /* synthetic */ boolean i = true;
    final /* synthetic */ C0868Bs f;
    final /* synthetic */ C2928rX g;
    final /* synthetic */ C2709oX h;

    C2469lX(C2709oX c2709oX, C0868Bs c0868Bs, C2928rX c2928rX) {
        this.h = c2709oX;
        this.f = c0868Bs;
        this.g = c2928rX;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(S10 s10) {
    }

    private void a(AbstractC2412ko abstractC2412ko, C2855qX c2855qX, final BiConsumer biConsumer) {
        if (abstractC2412ko instanceof N3) {
            return;
        }
        if (this.h.b.a(new lX$$ExternalSyntheticLambda0(c2855qX), abstractC2412ko)) {
            b(abstractC2412ko, c2855qX, biConsumer);
            return;
        }
        Iterator it = c2855qX.j().iterator();
        while (it.hasNext()) {
            b((S10) it.next(), c2855qX, new Consumer() { // from class: com.android.tools.r8.internal.lX$$ExternalSyntheticLambda11
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    C2469lX.a(biConsumer, (S10) obj);
                }
            }, new Consumer() { // from class: com.android.tools.r8.internal.lX$$ExternalSyntheticLambda16
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    C2469lX.b(biConsumer, (S10) obj);
                }
            });
        }
        if (abstractC2412ko.U1() || abstractC2412ko.K1() || abstractC2412ko.p2()) {
            Iterator it2 = c2855qX.h().iterator();
            while (it2.hasNext()) {
                b((S10) it2.next(), c2855qX, new Consumer() { // from class: com.android.tools.r8.internal.lX$$ExternalSyntheticLambda17
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        C2469lX.c(biConsumer, (S10) obj);
                    }
                }, new Consumer() { // from class: com.android.tools.r8.internal.lX$$ExternalSyntheticLambda18
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        C2469lX.d(biConsumer, (S10) obj);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(Map map, Map map2, S10 s10, IX ix) {
        a(map, map2, s10, ix);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void c(BiConsumer biConsumer, AbstractC2412ko abstractC2412ko, S10 s10) {
        biConsumer.accept(s10, IX.a(abstractC2412ko));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void d(BiConsumer biConsumer, AbstractC2412ko abstractC2412ko, S10 s10) {
        biConsumer.accept(s10, IX.a(abstractC2412ko));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void e(BiConsumer biConsumer, AbstractC2412ko abstractC2412ko, S10 s10) {
        biConsumer.accept(s10, IX.b(abstractC2412ko));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void f(BiConsumer biConsumer, AbstractC2412ko abstractC2412ko, S10 s10) {
        biConsumer.accept(s10, IX.b(abstractC2412ko));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void g(BiConsumer biConsumer, AbstractC2412ko abstractC2412ko, S10 s10) {
        biConsumer.accept(s10, IX.a(abstractC2412ko.b0()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void h(BiConsumer biConsumer, AbstractC2412ko abstractC2412ko, S10 s10) {
        biConsumer.accept(s10, IX.a(abstractC2412ko));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void i(BiConsumer biConsumer, AbstractC2412ko abstractC2412ko, S10 s10) {
        biConsumer.accept(s10, IX.a(abstractC2412ko));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void j(BiConsumer biConsumer, AbstractC2412ko abstractC2412ko, S10 s10) {
        biConsumer.accept(s10, IX.a(abstractC2412ko));
    }

    private void b(final AbstractC2412ko abstractC2412ko, C2855qX c2855qX, final BiConsumer biConsumer) {
        if (abstractC2412ko.p2()) {
            S10 s10B = abstractC2412ko.b();
            if (!i && s10B == null) {
                throw new AssertionError();
            }
            biConsumer.accept(s10B, IX.a(abstractC2412ko.t0()));
            return;
        }
        if (!abstractC2412ko.Z1()) {
            boolean z = i;
            if (!z && !(abstractC2412ko instanceof C1721bt)) {
                throw new AssertionError();
            }
            C1721bt c1721btG0 = abstractC2412ko.g0();
            if (!z && c1721btG0.S2() != this.h.a.a().f4.j) {
                throw new AssertionError();
            }
            b(c1721btG0.T0(), c2855qX, new Consumer() { // from class: com.android.tools.r8.internal.lX$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    C2469lX.g(biConsumer, abstractC2412ko, (S10) obj);
                }
            }, new Consumer() { // from class: com.android.tools.r8.internal.lX$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    C2469lX.h(biConsumer, abstractC2412ko, (S10) obj);
                }
            });
            return;
        }
        AbstractC1332Rs abstractC1332RsC0 = abstractC2412ko.c0();
        S10 s10U2 = abstractC1332RsC0.U2();
        if (this.h.b.d(abstractC2412ko)) {
            final C3441yX c3441yXA = IX.a(abstractC2412ko.Z());
            String strA = this.h.b.a(abstractC2412ko);
            c3441yXA.a(strA);
            if (strA == null && this.h.b.f(abstractC2412ko) && abstractC1332RsC0.Q2().P()) {
                c3441yXA.a(abstractC1332RsC0.Q2());
            }
            if (((ArrayList) abstractC1332RsC0.J2()).size() == 2) {
                S10 s10A = abstractC1332RsC0.a(1);
                QX qx = this.h.b;
                qx.getClass();
                if (s10A.w().w() && qx.a(s10A.w().b().R())) {
                    a(s10A, abstractC2412ko, c3441yXA, c2855qX, biConsumer);
                }
            }
            b(s10U2, c2855qX, new Consumer() { // from class: com.android.tools.r8.internal.lX$$ExternalSyntheticLambda23
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    biConsumer.accept((S10) obj, c3441yXA);
                }
            }, new Consumer() { // from class: com.android.tools.r8.internal.lX$$ExternalSyntheticLambda3
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    C2469lX.i(biConsumer, abstractC2412ko, (S10) obj);
                }
            });
            return;
        }
        if (!this.h.b.b(abstractC2412ko)) {
            if (this.h.b.a(abstractC2412ko, s10U2)) {
                b(s10U2, c2855qX, new Consumer() { // from class: com.android.tools.r8.internal.lX$$ExternalSyntheticLambda6
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        C2469lX.a(biConsumer, abstractC2412ko, (S10) obj);
                    }
                }, new Consumer() { // from class: com.android.tools.r8.internal.lX$$ExternalSyntheticLambda7
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        C2469lX.b(biConsumer, abstractC2412ko, (S10) obj);
                    }
                });
                return;
            } else if (this.h.b.e(abstractC2412ko)) {
                b(s10U2, c2855qX, new Consumer() { // from class: com.android.tools.r8.internal.lX$$ExternalSyntheticLambda8
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        C2469lX.c(biConsumer, abstractC2412ko, (S10) obj);
                    }
                }, new Consumer() { // from class: com.android.tools.r8.internal.lX$$ExternalSyntheticLambda9
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        C2469lX.d(biConsumer, abstractC2412ko, (S10) obj);
                    }
                });
                return;
            } else {
                b(s10U2, c2855qX, new Consumer() { // from class: com.android.tools.r8.internal.lX$$ExternalSyntheticLambda10
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        C2469lX.e(biConsumer, abstractC2412ko, (S10) obj);
                    }
                }, new Consumer() { // from class: com.android.tools.r8.internal.lX$$ExternalSyntheticLambda12
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        C2469lX.f(biConsumer, abstractC2412ko, (S10) obj);
                    }
                });
                return;
            }
        }
        final C3217vX c3217vXA = IX.a(abstractC2412ko.i0());
        String strA2 = this.h.b.a(abstractC2412ko);
        c3217vXA.a(strA2);
        S10 s10Q2 = abstractC1332RsC0.Q2();
        if (strA2 == null && this.h.b.c(abstractC2412ko) && s10Q2.P()) {
            c3217vXA.a(s10Q2);
        }
        QX qx2 = this.h.b;
        S10 s10M = s10Q2.m();
        qx2.getClass();
        if (s10M.w().w() && qx2.a(s10M.w().b().R())) {
            a(s10Q2, abstractC2412ko, c3217vXA, c2855qX, biConsumer);
        }
        b(s10U2, c2855qX, new Consumer() { // from class: com.android.tools.r8.internal.lX$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                biConsumer.accept((S10) obj, c3217vXA);
            }
        }, new Consumer() { // from class: com.android.tools.r8.internal.lX$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C2469lX.e(biConsumer, (S10) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void c(BiConsumer biConsumer, S10 s10) {
        biConsumer.accept(s10, new CX());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void d(BiConsumer biConsumer, S10 s10) {
        biConsumer.accept(s10, new CX());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void e(BiConsumer biConsumer, S10 s10) {
        biConsumer.accept(s10, new CX());
    }

    @Override // com.android.tools.r8.utils.AbstractC3951q0
    protected final com.android.tools.r8.utils.D3 a(InterfaceC3936n0 interfaceC3936n0, Function function) {
        final LinkedHashMap linkedHashMap = new LinkedHashMap();
        final IdentityHashMap identityHashMap = new IdentityHashMap();
        C3332x4 c3332x4 = (C3332x4) interfaceC3936n0.c();
        C2855qX c2855qXC = this.g.c(c3332x4, (C2855qX) this.f.e(c3332x4));
        c2855qXC.getClass();
        for (ML ml : c3332x4.x()) {
            if (c2855qXC.b(ml) && a(ml, c2855qXC, new Consumer() { // from class: com.android.tools.r8.internal.lX$$ExternalSyntheticLambda13
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    C2469lX.a((S10) obj);
                }
            }, new Consumer() { // from class: com.android.tools.r8.internal.lX$$ExternalSyntheticLambda14
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.a(linkedHashMap, identityHashMap, (S10) obj);
                }
            })) {
                a(linkedHashMap, identityHashMap, ml, new C3294wX());
            }
        }
        for (AbstractC2412ko abstractC2412ko : c3332x4.p()) {
            c2855qXC = this.g.a(abstractC2412ko, c2855qXC);
            c2855qXC.getClass();
            a(abstractC2412ko, c2855qXC, new BiConsumer() { // from class: com.android.tools.r8.internal.lX$$ExternalSyntheticLambda15
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    this.f$0.b(linkedHashMap, identityHashMap, (S10) obj, (IX) obj2);
                }
            });
        }
        boolean z = i;
        if (!z && !linkedHashMap.keySet().equals(identityHashMap.keySet())) {
            throw new AssertionError();
        }
        if (!z && !c2855qXC.i().containsAll(linkedHashMap.keySet())) {
            throw new AssertionError("Seen root that is not a live string builder");
        }
        interfaceC3936n0.a(new C2618nX(linkedHashMap, identityHashMap));
        Iterator<C3332x4> it = c3332x4.z().iterator();
        while (it.hasNext()) {
            function.apply(it.next());
        }
        return com.android.tools.r8.utils.D3.e();
    }

    private static void a(Map map, Map map2, S10 s10, IX ix) {
        boolean z;
        IX ix2 = (IX) map2.get(s10);
        if (ix2 == null) {
            map.put(s10, ix);
            map2.put(s10, ix);
            return;
        }
        boolean z2 = true;
        if (ix2 instanceof CX) {
            ix.getClass();
            if ((ix instanceof CX) || (ix instanceof AX)) {
                z2 = false;
            }
        } else {
            if (ix2 instanceof AX) {
                ix.getClass();
                z = ix instanceof AX;
            } else if (ix2 instanceof C3294wX) {
                ix.getClass();
                z = ix instanceof C3294wX;
            }
            z2 = true ^ z;
        }
        if (z2) {
            ix2.b(ix);
            map2.put(s10, ix);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(BiConsumer biConsumer, AbstractC2412ko abstractC2412ko, S10 s10) {
        biConsumer.accept(s10, IX.a((AbstractC1305Qs) abstractC2412ko.i0()));
    }

    private static void a(S10 s10, final AbstractC2412ko abstractC2412ko, InterfaceC3511zX interfaceC3511zX, C2855qX c2855qX, final BiConsumer biConsumer) {
        if (!i && !c2855qX.b(s10)) {
            throw new AssertionError();
        }
        final C3363xX c3363xX = new C3363xX(interfaceC3511zX);
        b(s10, c2855qX, new Consumer() { // from class: com.android.tools.r8.internal.lX$$ExternalSyntheticLambda21
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                biConsumer.accept((S10) obj, c3363xX);
            }
        }, new Consumer() { // from class: com.android.tools.r8.internal.lX$$ExternalSyntheticLambda22
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C2469lX.j(biConsumer, abstractC2412ko, (S10) obj);
            }
        });
        interfaceC3511zX.a(c3363xX);
    }

    private static boolean a(S10 s10, C2855qX c2855qX, Consumer consumer, Consumer consumer2) {
        com.android.tools.r8.utils.O3 o3H = com.android.tools.r8.utils.O3.h(s10);
        boolean zA = false;
        boolean z = false;
        while (o3H.c()) {
            S10 s11 = (S10) o3H.f();
            zA |= c2855qX.a(s11);
            Set set = (Set) c2855qX.f().getOrDefault(s11, Collections.emptySet());
            o3H.a((Iterable) set);
            if (set.size() != 1 || s11.i()) {
                if (!z) {
                    consumer.accept(s11);
                    z = true;
                } else {
                    consumer2.accept(s11);
                }
            }
        }
        return zA;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b(BiConsumer biConsumer, AbstractC2412ko abstractC2412ko, S10 s10) {
        biConsumer.accept(s10, IX.a(abstractC2412ko));
    }

    private static void b(S10 s10, C2855qX c2855qX, Consumer consumer, Consumer consumer2) {
        if (!i && !c2855qX.b(s10)) {
            throw new AssertionError();
        }
        if (a(s10, c2855qX, consumer2) || a(s10, c2855qX, consumer, consumer2)) {
            Iterator it = c2855qX.h().iterator();
            while (it.hasNext()) {
                consumer2.accept((S10) it.next());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(BiConsumer biConsumer, S10 s10) {
        biConsumer.accept(s10, new C3294wX());
    }

    private static boolean a(S10 s10, C2855qX c2855qX, Consumer consumer) {
        Map mapG = c2855qX.g();
        com.android.tools.r8.utils.O3 o3D = com.android.tools.r8.utils.O3.d((Iterable) mapG.getOrDefault(s10, Collections.emptySet()));
        boolean zA = false;
        while (o3D.c()) {
            S10 s11 = (S10) o3D.f();
            zA |= c2855qX.a(s11);
            if (s11.i()) {
                consumer.accept(s11);
            }
            o3D.a((Iterable) mapG.getOrDefault(s11, Collections.emptySet()));
        }
        return zA;
    }

    @Override // com.android.tools.r8.utils.AbstractC3951q0
    protected final com.android.tools.r8.utils.C3 a(InterfaceC3936n0 interfaceC3936n0, final List list) {
        final C2618nX c2618nX = (C2618nX) interfaceC3936n0.a();
        final C3505zR c3505zR = new C3505zR(16);
        Iterator it = list.iterator();
        while (it.hasNext()) {
            InterfaceC3936n0 interfaceC3936n1 = (InterfaceC3936n0) it.next();
            C2618nX c2618nX2 = (C2618nX) interfaceC3936n1.a();
            c2618nX2.a.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.lX$$ExternalSyntheticLambda19
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    C2469lX.a(c3505zR, c2618nX, list, (S10) obj, (IX) obj2);
                }
            });
            if (interfaceC3936n1.b()) {
                c2618nX2.c = true;
            }
        }
        InterfaceC1912eK it2 = c3505zR.f().iterator();
        while (it2.hasNext()) {
            AR ar2 = (AR) it2.next();
            boolean z = i;
            if (!z && ar2.getIntValue() > list.size()) {
                throw new AssertionError();
            }
            if (ar2.getIntValue() < list.size()) {
                IX fx = new FX();
                IX ix = (IX) c2618nX.b.get(ar2.getKey());
                if (!z && ix == null) {
                    throw new AssertionError();
                }
                fx.a(ix);
                ix.b(fx);
            }
        }
        if (c2618nX.c) {
            c2618nX.a.replaceAll(new BiFunction() { // from class: com.android.tools.r8.internal.lX$$ExternalSyntheticLambda20
                @Override // java.util.function.BiFunction
                public final Object apply(Object obj, Object obj2) {
                    return C2469lX.a((S10) obj, (IX) obj2);
                }
            });
        }
        return com.android.tools.r8.utils.D3.b(c2618nX);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(CR cr, C2618nX c2618nX, List list, S10 s10, IX ix) {
        cr.a(cr.a(s10) + 1, s10);
        IX ix2 = (IX) c2618nX.a.get(s10);
        IX fx = (IX) c2618nX.b.get(s10);
        if (ix2 == null) {
            if (!i && fx != null) {
                throw new AssertionError();
            }
            if (list.size() == 1) {
                c2618nX.a.put(s10, ix);
                c2618nX.b.put(s10, ix);
                return;
            } else {
                fx = new FX();
                c2618nX.a.put(s10, fx);
                c2618nX.b.put(s10, fx);
            }
        }
        if (!i && fx == null) {
            throw new AssertionError();
        }
        fx.b(ix);
        ix.a(fx);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(BiConsumer biConsumer, S10 s10) {
        biConsumer.accept(s10, new C3294wX());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Map map, Map map2, S10 s10) {
        a(map, map2, s10, new C3294wX());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static IX a(S10 s10, IX ix) {
        BX bx = new BX();
        bx.b(ix);
        return bx;
    }
}
