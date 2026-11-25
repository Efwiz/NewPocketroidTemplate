package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.shaking.InterfaceC3680a0;
import com.android.tools.r8.utils.C3888e1;
import com.android.tools.r8.utils.C3970u0;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Bz, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C0875Bz implements InterfaceC3680a0 {
    static final /* synthetic */ boolean d = true;
    private final List a;
    private final List b;
    private final List c;

    private C0875Bz(AbstractC1639am abstractC1639am, AbstractC1639am abstractC1639am2, AbstractC1639am abstractC1639am3) {
        this.a = abstractC1639am;
        this.b = abstractC1639am2;
        this.c = abstractC1639am3;
    }

    public static C0875Bz a(InterfaceC1877dy interfaceC1877dy, HashMap map, HashMap map2, C0722p1 c0722p1, com.android.tools.r8.utils.O2 o2, Consumer consumer, C1201Mz c1201Mz, C1466Vz c1466Vz) {
        boolean z;
        com.android.tools.r8.graph.W0 w0;
        com.android.tools.r8.graph.W0 w1;
        com.android.tools.r8.graph.T0 t0;
        C1507Xl c1507XlG = AbstractC1639am.g();
        Iterator<C2815py> it = interfaceC1877dy.c().iterator();
        int i = 0;
        int i2 = 0;
        while (true) {
            int i3 = 1;
            if (!it.hasNext()) {
                C1507Xl c1507XlG2 = AbstractC1639am.g();
                for (C3539zy c3539zy : interfaceC1877dy.a()) {
                    C2212iA c2212iAA = C2212iA.a(c3539zy, c0722p1, o2);
                    C1593aA c1593aA = new C1593aA(c3539zy);
                    if (c1593aA.a() == null || (t0 = (com.android.tools.r8.graph.T0) map2.get(c1593aA.a().a())) == null) {
                        z = false;
                    } else {
                        t0.a(c2212iAA);
                        c1466Vz.a(t0.getReference());
                        z = true;
                    }
                    if (c1593aA.b() != null && (w1 = (com.android.tools.r8.graph.W0) map.get(c1593aA.b().a())) != null) {
                        if (AbstractC3382xi.v.b(c3539zy.e()).booleanValue()) {
                            consumer.accept(w1);
                        }
                        w1.a(c2212iAA);
                        c1466Vz.a(w1.getReference());
                        z = true;
                    }
                    if (c1593aA.c() != null && (w0 = (com.android.tools.r8.graph.W0) map.get(c1593aA.c().a())) != null) {
                        if (AbstractC3382xi.v.b(c3539zy.e()).booleanValue()) {
                            consumer.accept(w0);
                        }
                        w0.a(c2212iAA);
                        c1466Vz.a(w0.getReference());
                        z = true;
                    }
                    if (!z) {
                        c1507XlG2.a(c2212iAA);
                    }
                }
                List<C0974Ey> listB = interfaceC1877dy.b();
                C1507Xl c1507XlG3 = AbstractC1639am.g();
                Iterator<C0974Ey> it2 = listB.iterator();
                while (it2.hasNext()) {
                    c1507XlG3.a(C2678oA.a(it2.next(), c0722p1, o2));
                }
                return new C0875Bz(c1507XlG3.a(), c1507XlG.a(), c1507XlG2.a());
            }
            C2815py next = it.next();
            com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.j jVarB = com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.d.b(next);
            if (jVarB != null) {
                int i4 = i2 + 1;
                C1091Iz c1091IzA = C1091Iz.a(next, c0722p1, o2, c1201Mz.b(i2));
                com.android.tools.r8.graph.W0 w2 = (com.android.tools.r8.graph.W0) map.get(jVarB.a());
                if (w2 == null) {
                    c1507XlG.a(c1091IzA);
                    try {
                        String[] strArrE = C3970u0.e(jVarB.b());
                        int length = strArrE.length;
                        for (int i5 = i; i5 < length; i5++) {
                            String str = strArrE[i5];
                            if (str.charAt(i) == 'L' && !C3970u0.z(str)) {
                                break;
                            }
                        }
                    } catch (C1023Gs unused) {
                    }
                } else {
                    if (AbstractC3382xi.g.b(next.f()).booleanValue()) {
                        while (true) {
                            if (i3 > AbstractC1112Jr.a(w2.l1().size(), 32, RoundingMode.CEILING)) {
                                consumer.accept(w2);
                                break;
                            }
                            com.android.tools.r8.graph.W0 w3 = (com.android.tools.r8.graph.W0) map.get(AbstractC1745cA.a(jVarB, i3).a());
                            if (w3 != null) {
                                consumer.accept(w3);
                                break;
                            }
                            i3++;
                        }
                    }
                    w2.a(c1091IzA);
                    c1466Vz.a(w2.getReference());
                }
                i2 = i4;
                i = 0;
            } else if (!d) {
                throw new AssertionError();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ C0849Az b(C2212iA c2212iA) {
        return new C0849Az();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Consumer c(C2212iA c2212iA) {
        Objects.requireNonNull(c2212iA);
        return new C0879Bz$$ExternalSyntheticLambda2(c2212iA);
    }

    final boolean a(Consumer consumer, Consumer consumer2, Consumer consumer3, AbstractC0727q0 abstractC0727q0, C0702m c0702m, C1466Vz c1466Vz) {
        boolean zA = AbstractC1745cA.a(c0702m, this.a, consumer3, new com.android.tools.r8.utils.G3() { // from class: com.android.tools.r8.internal.Bz$$ExternalSyntheticLambda6
            @Override // com.android.tools.r8.utils.G3
            public final Object a(Object obj, Object obj2, Object obj3) {
                return Boolean.valueOf(((C2678oA) obj).a((Consumer) obj2, (C0702m) obj3));
            }
        });
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (com.android.tools.r8.graph.T0 t0 : abstractC0727q0.W0()) {
            if (t0.V0().d()) {
                C0849Az c0849Az = (C0849Az) linkedHashMap.computeIfAbsent(t0.V0().o(), new Function() { // from class: com.android.tools.r8.internal.Bz$$ExternalSyntheticLambda7
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return C0875Bz.a((C2212iA) obj);
                    }
                });
                if (!C0849Az.d && c0849Az.a != null) {
                    throw new AssertionError();
                }
                c0849Az.a = t0;
                c1466Vz.a(t0.getReference());
            }
        }
        for (com.android.tools.r8.graph.W0 w0 : abstractC0727q0.M1()) {
            if (w0.f1().l()) {
                zA |= w0.f1().b().a(consumer, w0, c0702m);
                c1466Vz.a(w0.getReference());
            } else {
                C2212iA c2212iAO = w0.f1().o();
                if (c2212iAO == null) {
                    continue;
                } else {
                    c1466Vz.a(w0.getReference());
                    C0849Az c0849Az2 = (C0849Az) linkedHashMap.computeIfAbsent(c2212iAO, new Function() { // from class: com.android.tools.r8.internal.Bz$$ExternalSyntheticLambda8
                        @Override // java.util.function.Function
                        public final Object apply(Object obj) {
                            return C0875Bz.b((C2212iA) obj);
                        }
                    });
                    if (w0.getReference().h.e == c0702m.a().D1) {
                        if (!C0849Az.d && c0849Az2.b != null) {
                            throw new AssertionError();
                        }
                        c0849Az2.b = w0;
                    } else {
                        if (!C0849Az.d && c0849Az2.c != null) {
                            throw new AssertionError();
                        }
                        c0849Az2.c = w0;
                    }
                }
            }
        }
        for (C2212iA c2212iA : linkedHashMap.keySet()) {
            C0849Az c0849Az3 = (C0849Az) linkedHashMap.get(c2212iA);
            zA |= c2212iA.a(consumer2, c0849Az3.a, c0849Az3.c, c0849Az3.b, c0702m);
        }
        return AbstractC1745cA.a(c0702m, this.b, consumer, new com.android.tools.r8.utils.G3() { // from class: com.android.tools.r8.internal.Bz$$ExternalSyntheticLambda9
            @Override // com.android.tools.r8.utils.G3
            public final Object a(Object obj, Object obj2, Object obj3) {
                return Boolean.valueOf(((C1091Iz) obj).a((Consumer) obj2, (C0702m) obj3));
            }
        }) | zA | AbstractC1745cA.a(c0702m, this.c, consumer2, new C0878Bz$$ExternalSyntheticLambda10());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ C0849Az a(C2212iA c2212iA) {
        return new C0849Az();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Consumer a(final C2678oA c2678oA) {
        Objects.requireNonNull(c2678oA);
        return new Consumer() { // from class: com.android.tools.r8.internal.Bz$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                c2678oA.a((com.android.tools.r8.graph.Q0) obj);
            }
        };
    }

    @Override // com.android.tools.r8.shaking.InterfaceC3680a0
    public final void a(com.android.tools.r8.graph.Q0 q0) {
        C3888e1.a(this.a, new Function() { // from class: com.android.tools.r8.internal.Bz$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C0875Bz.a((C2678oA) obj);
            }
        }, (Object) q0);
        C3888e1.a(this.b, new Function() { // from class: com.android.tools.r8.internal.Bz$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C0875Bz.a((C1091Iz) obj);
            }
        }, (Object) q0);
        C3888e1.a(this.c, new Function() { // from class: com.android.tools.r8.internal.Bz$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C0875Bz.c((C2212iA) obj);
            }
        }, (Object) q0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Consumer a(final C1091Iz c1091Iz) {
        Objects.requireNonNull(c1091Iz);
        return new Consumer() { // from class: com.android.tools.r8.internal.Bz$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                c1091Iz.a((com.android.tools.r8.graph.Q0) obj);
            }
        };
    }
}
