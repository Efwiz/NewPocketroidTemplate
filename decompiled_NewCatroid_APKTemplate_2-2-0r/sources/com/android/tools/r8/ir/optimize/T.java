package com.android.tools.r8.ir.optimize;

import com.android.tools.r8.graph.AbstractC0748t0;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.I4;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.internal.AbstractC1084Is;
import com.android.tools.r8.internal.AbstractC1305Qs;
import com.android.tools.r8.internal.AbstractC2412ko;
import com.android.tools.r8.internal.C2375kJ;
import com.android.tools.r8.internal.C2409kl;
import com.android.tools.r8.internal.C2974sB;
import com.android.tools.r8.internal.C3332x4;
import com.android.tools.r8.internal.InterfaceC2454lJ;
import com.android.tools.r8.internal.InterfaceC2641no;
import com.android.tools.r8.internal.S10;
import com.android.tools.r8.shaking.C3733i;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class T {
    static final /* synthetic */ boolean c = true;
    private final C0702m a;
    private final C0722p1 b;

    public T(C0702m c0702m) {
        this.a = c0702m;
        this.b = c0702m.a();
    }

    public final void a(final C2409kl c2409kl) {
        AbstractC0748t0 abstractC0748t0H;
        S10 s10;
        C2375kJ c2375kJ = new C2375kJ(new S(), 0);
        o5 o5VarH = c2409kl.h();
        Iterator<C3332x4> it = c2409kl.d.iterator();
        while (it.hasNext()) {
            for (AbstractC2412ko abstractC2412ko : it.next().p()) {
                if (abstractC2412ko.Y1()) {
                    AbstractC1305Qs abstractC1305QsB0 = abstractC2412ko.b0();
                    if (abstractC1305QsB0.b() != null && !abstractC2412ko.b().A()) {
                        C0684j2 c0684j2S2 = abstractC1305QsB0.S2();
                        C2974sB c2974sBX = this.a.x();
                        c2974sBX.getClass();
                        if (!c2974sBX.a(abstractC1305QsB0.S2(), abstractC1305QsB0.J2()) || !this.b.O5.contains(c0684j2S2)) {
                            if (!this.a.q()) {
                                continue;
                            } else {
                                if (!c && !this.a.f().i()) {
                                    throw new AssertionError();
                                }
                                C0702m<C3733i> c0702mI0 = this.a.i0();
                                I4.c<?> cVarO = ((C3733i) c0702mI0.f()).b(abstractC1305QsB0.S2(), abstractC1305QsB0.R2()).o();
                                if (cVarO != null && !cVarO.a(o5VarH, c0702mI0).b() && (abstractC0748t0H = abstractC1305QsB0.h(c0702mI0, o5VarH)) != null) {
                                    com.android.tools.r8.ir.optimize.info.h hVarI1 = abstractC0748t0H.e().i1();
                                    if (hVarI1.a(abstractC1305QsB0, c0702mI0.Y()) || !hVarI1.D() || (abstractC2412ko.Z1() && abstractC2412ko.c0().U2().m().w().F())) {
                                    }
                                }
                            }
                        }
                        Iterator it2 = ((ArrayList) abstractC2412ko.h1()).iterator();
                        do {
                            if (!it2.hasNext()) {
                                ((List) c2375kJ.computeIfAbsent(abstractC1305QsB0, new Function() { // from class: com.android.tools.r8.ir.optimize.T$$ExternalSyntheticLambda1
                                    @Override // java.util.function.Function
                                    public final Object apply(Object obj) {
                                        return T.a((AbstractC1305Qs) obj);
                                    }
                                })).add(abstractC2412ko.b());
                                break;
                            }
                            s10 = (S10) it2.next();
                            if (s10.i() || !s10.b.y1()) {
                                break;
                            }
                        } while (s10.b.h().v() == 0);
                    }
                }
            }
        }
        if (c2375kJ.isEmpty()) {
            return;
        }
        if (!c && c2409kl.j().D()) {
            throw new AssertionError();
        }
        final HashMap map = new HashMap();
        c2375kJ.k().stream().filter(new Predicate() { // from class: com.android.tools.r8.ir.optimize.T$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return T.a((InterfaceC2454lJ) obj);
            }
        }).sorted(new Comparator() { // from class: com.android.tools.r8.ir.optimize.T$$ExternalSyntheticLambda3
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return T.a((InterfaceC2454lJ) obj, (InterfaceC2454lJ) obj2);
            }
        }).limit(15L).forEach(new Consumer() { // from class: com.android.tools.r8.ir.optimize.T$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                T.a(c2409kl, map, (InterfaceC2454lJ) obj);
            }
        });
        if (!map.isEmpty()) {
            Iterator<C3332x4> it3 = c2409kl.d.iterator();
            while (it3.hasNext()) {
                InterfaceC2641no interfaceC2641noA = it3.next().a(c2409kl);
                while (interfaceC2641noA.hasNext()) {
                    AbstractC2412ko next = interfaceC2641noA.next();
                    if (next.Y1()) {
                        AbstractC1305Qs abstractC1305QsB1 = next.b0();
                        if (map.containsKey(abstractC1305QsB1)) {
                            S10 s11 = (S10) map.get(abstractC1305QsB1);
                            if (!c && s11 == null) {
                                throw new AssertionError();
                            }
                            abstractC1305QsB1.b().g(s11);
                            interfaceC2641noA.s();
                        } else {
                            continue;
                        }
                    }
                }
            }
        }
        c2409kl.y();
        if (!c && !c2409kl.b(this.a)) {
            throw new AssertionError();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ List a(AbstractC1305Qs abstractC1305Qs) {
        return new ArrayList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(InterfaceC2454lJ interfaceC2454lJ) {
        return ((List) interfaceC2454lJ.getValue()).size() > 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int a(InterfaceC2454lJ interfaceC2454lJ, InterfaceC2454lJ interfaceC2454lJ2) {
        return Integer.compare(((List) interfaceC2454lJ2.getValue()).size(), ((List) interfaceC2454lJ.getValue()).size());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(C2409kl c2409kl, Map map, InterfaceC2454lJ interfaceC2454lJ) {
        AbstractC1305Qs abstractC1305Qs = (AbstractC1305Qs) interfaceC2454lJ.getKey();
        S10 s10A = c2409kl.a(abstractC1305Qs.b().s(), abstractC1305Qs.a());
        AbstractC1084Is abstractC1084IsA = AbstractC1084Is.a(abstractC1305Qs.M2(), abstractC1305Qs.S2(), null, s10A, abstractC1305Qs.h1(), false);
        abstractC1084IsA.b(((S10) ((List) interfaceC2454lJ.getValue()).get(0)).b.Y0());
        if (abstractC1305Qs.h1().size() > 0) {
            a(c2409kl, abstractC1084IsA);
        } else {
            InterfaceC2641no interfaceC2641noA = c2409kl.j().a(c2409kl);
            while (interfaceC2641noA.hasNext()) {
                if (!interfaceC2641noA.next().m1()) {
                    interfaceC2641noA.previous();
                    break;
                }
            }
            interfaceC2641noA.add(abstractC1084IsA);
        }
        Iterator it = ((List) interfaceC2454lJ.getValue()).iterator();
        while (it.hasNext()) {
            map.put(((S10) it.next()).b.b0(), s10A);
        }
    }

    private static void a(C2409kl c2409kl, AbstractC1084Is abstractC1084Is) {
        InterfaceC2641no interfaceC2641noA = c2409kl.j().a(c2409kl);
        int i = 0;
        while (interfaceC2641noA.hasNext()) {
            AbstractC2412ko next = interfaceC2641noA.next();
            if (next.d1()) {
                Iterator it = ((ArrayList) abstractC1084Is.h1()).iterator();
                while (it.hasNext()) {
                    if (((S10) it.next()) == next.b()) {
                        i++;
                    }
                }
            }
            if (i == ((ArrayList) abstractC1084Is.h1()).size()) {
                if (!interfaceC2641noA.hasNext() || !interfaceC2641noA.o().m1()) {
                    break;
                    break;
                } else {
                    interfaceC2641noA.a(new Predicate() { // from class: com.android.tools.r8.ir.optimize.T$$ExternalSyntheticLambda0
                        @Override // java.util.function.Predicate
                        public final boolean test(Object obj) {
                            return T.a((AbstractC2412ko) obj);
                        }
                    });
                    break;
                }
            }
        }
        interfaceC2641noA.add(abstractC1084Is);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(AbstractC2412ko abstractC2412ko) {
        return !abstractC2412ko.m1();
    }
}
