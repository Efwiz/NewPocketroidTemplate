package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0594a1;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0725p4;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.origin.Origin;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class FF {
    static final /* synthetic */ boolean a = true;

    public static void a(EF ef, com.android.tools.r8.utils.O2 o2) {
        HashSet<C2822q2> hashSet = new HashSet();
        hashSet.addAll(ef.a().keySet());
        hashSet.addAll(ef.b().keySet());
        hashSet.addAll(ef.d().keySet());
        for (C2822q2 c2822q2 : hashSet) {
            Map<C2822q2, C1297Qk> mapA = ef.a();
            Map<C2822q2, C1297Qk> mapB = ef.b();
            Map<C2822q2, C1297Qk> mapD = ef.d();
            C1297Qk c1297Qk = mapB.get(c2822q2);
            C1297Qk c1297Qk2 = mapD.get(c2822q2);
            if (c1297Qk != null && c1297Qk2 != null) {
                Origin originC = ef.c();
                C1297Qk.a aVarA = mapA.get(c2822q2) == null ? C1297Qk.a(o2, originC) : mapA.get(c2822q2).b(o2, originC);
                C1297Qk.a aVarA2 = C1297Qk.a(o2, originC);
                C1297Qk.a aVarA3 = C1297Qk.a(o2, originC);
                b(c1297Qk, c1297Qk2, aVarA, aVarA2);
                b(c1297Qk2, c1297Qk, aVarA, aVarA3);
                C1297Qk c1297QkA = aVarA.a();
                if (c1297QkA.u()) {
                    mapA.remove(c2822q2);
                } else {
                    mapA.put(c2822q2, c1297QkA);
                }
                C1297Qk c1297QkA2 = aVarA2.a();
                if (c1297QkA2.u()) {
                    mapB.remove(c2822q2);
                } else {
                    mapB.put(c2822q2, c1297QkA2);
                }
                C1297Qk c1297QkA3 = aVarA3.a();
                if (c1297QkA3.u()) {
                    mapD.remove(c2822q2);
                } else {
                    mapD.put(c2822q2, c1297QkA3);
                }
            }
        }
    }

    private static void b(C1297Qk c1297Qk, C1297Qk c1297Qk2, final C1297Qk.a aVar, final C1297Qk.a aVar2) {
        d(c1297Qk, c1297Qk2, aVar, aVar2);
        c(c1297Qk, c1297Qk2, aVar, aVar2);
        Map<C0767v2, C0767v2> mapI = c1297Qk.i();
        Map<C0767v2, C0767v2> mapI2 = c1297Qk2.i();
        Objects.requireNonNull(aVar);
        FF$$ExternalSyntheticLambda8 fF$$ExternalSyntheticLambda8 = new FF$$ExternalSyntheticLambda8(aVar);
        Objects.requireNonNull(aVar2);
        a(mapI, mapI2, fF$$ExternalSyntheticLambda8, new FF$$ExternalSyntheticLambda8(aVar2));
        a(c1297Qk.p(), c1297Qk2.p(), new BiConsumer() { // from class: com.android.tools.r8.internal.FF$$ExternalSyntheticLambda11
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                aVar.c((C0684j2) obj, (C0767v2) obj2);
            }
        }, new BiConsumer() { // from class: com.android.tools.r8.internal.FF$$ExternalSyntheticLambda11
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                aVar2.c((C0684j2) obj, (C0767v2) obj2);
            }
        });
        a(c1297Qk.n(), c1297Qk2.n(), new BiConsumer() { // from class: com.android.tools.r8.internal.FF$$ExternalSyntheticLambda12
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                aVar.b((C0684j2) obj, (C0767v2) obj2);
            }
        }, new BiConsumer() { // from class: com.android.tools.r8.internal.FF$$ExternalSyntheticLambda12
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                aVar2.b((C0684j2) obj, (C0767v2) obj2);
            }
        });
        a(c1297Qk.j(), c1297Qk2.j(), new FF$$ExternalSyntheticLambda13(aVar), new FF$$ExternalSyntheticLambda13(aVar2));
        a(c1297Qk.e(), c1297Qk2.e(), new FF$$ExternalSyntheticLambda14(aVar), new FF$$ExternalSyntheticLambda14(aVar2));
        a(c1297Qk.g(), c1297Qk2.g(), new Consumer() { // from class: com.android.tools.r8.internal.FF$$ExternalSyntheticLambda9
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                aVar.a((C0684j2) obj);
            }
        }, new Consumer() { // from class: com.android.tools.r8.internal.FF$$ExternalSyntheticLambda9
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                aVar2.a((C0684j2) obj);
            }
        });
        a(c1297Qk.f(), c1297Qk2.f(), new FF$$ExternalSyntheticLambda10(aVar), new FF$$ExternalSyntheticLambda10(aVar2));
        e(c1297Qk, c1297Qk2, aVar, aVar2);
        a(c1297Qk, c1297Qk2, aVar, aVar2);
    }

    private static void c(C1297Qk c1297Qk, final C1297Qk c1297Qk2, final C1297Qk.a aVar, final C1297Qk.a aVar2) {
        c1297Qk.r().forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.FF$$ExternalSyntheticLambda5
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                FF.a(c1297Qk2, aVar2, aVar, (String) obj, (Map) obj2);
            }
        });
    }

    private static void d(C1297Qk c1297Qk, final C1297Qk c1297Qk2, final C1297Qk.a aVar, final C1297Qk.a aVar2) {
        c1297Qk.s().forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.FF$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                FF.a(c1297Qk2, aVar, aVar2, (String) obj, (String) obj2);
            }
        });
    }

    private static void e(C1297Qk c1297Qk, C1297Qk c1297Qk2, final C1297Qk.a aVar, final C1297Qk.a aVar2) {
        final Map<C0767v2, Set<C0684j2>> mapT = c1297Qk2.t();
        c1297Qk.t().forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.FF$$ExternalSyntheticLambda6
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                FF.a(mapT, aVar, aVar2, (C0767v2) obj, (Set) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Map map, C1297Qk.a aVar, C1297Qk.a aVar2, C0767v2 c0767v2, Set set) {
        if (map.containsKey(c0767v2)) {
            if (!a && !set.equals(map.get(c0767v2))) {
                throw new AssertionError();
            }
            aVar.a(c0767v2, set);
            return;
        }
        aVar2.a(c0767v2, set);
    }

    private static void a(C1297Qk c1297Qk, C1297Qk c1297Qk2, final C1297Qk.a aVar, final C1297Qk.a aVar2) {
        final Map<C0684j2, C0725p4> mapB = c1297Qk2.b();
        c1297Qk.b().forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.FF$$ExternalSyntheticLambda7
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                FF.a(mapB, aVar, aVar2, (C0684j2) obj, (C0725p4) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Map map, C1297Qk.a aVar, C1297Qk.a aVar2, C0684j2 c0684j2, C0725p4 c0725p4) {
        if (map.get(c0684j2) == c0725p4) {
            aVar.a(c0684j2, c0725p4);
        } else {
            aVar2.a(c0684j2, c0725p4);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C1297Qk c1297Qk, final C1297Qk.a aVar, final C1297Qk.a aVar2, final String str, Map map) {
        if (!c1297Qk.r().containsKey(str)) {
            map.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.FF$$ExternalSyntheticLambda3
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    aVar.a(str, (String) obj, (String) obj2);
                }
            });
        } else {
            final Map<String, String> map2 = c1297Qk.r().get(str);
            map.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.FF$$ExternalSyntheticLambda4
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    FF.a(map2, aVar2, str, aVar, (String) obj, (String) obj2);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Map map, C1297Qk.a aVar, String str, C1297Qk.a aVar2, String str2, String str3) {
        if (map.containsKey(str2) && ((String) map.get(str2)).equals(str3)) {
            aVar.a(str, str2, str3);
        } else {
            aVar2.a(str, str2, str3);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C1297Qk c1297Qk, C1297Qk.a aVar, C1297Qk.a aVar2, String str, String str2) {
        if (c1297Qk.s().containsKey(str) && c1297Qk.s().get(str).equals(str2)) {
            aVar.a(str, str2);
        } else {
            aVar2.a(str, str2);
        }
    }

    private static void a(Map map, final Map map2, final BiConsumer biConsumer, final BiConsumer biConsumer2) {
        map.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.FF$$ExternalSyntheticLambda2
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                FF.a(map2, biConsumer, biConsumer2, (AbstractC0594a1) obj, (C0767v2) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Map map, BiConsumer biConsumer, BiConsumer biConsumer2, AbstractC0594a1 abstractC0594a1, C0767v2 c0767v2) {
        if (map.get(abstractC0594a1) == c0767v2) {
            biConsumer.accept(abstractC0594a1, c0767v2);
        } else {
            biConsumer2.accept(abstractC0594a1, c0767v2);
        }
    }

    private static void a(Set set, final Set set2, final Consumer consumer, final Consumer consumer2) {
        set.forEach(new Consumer() { // from class: com.android.tools.r8.internal.FF$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                FF.a(set2, consumer, consumer2, (AbstractC0594a1) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Set set, Consumer consumer, Consumer consumer2, AbstractC0594a1 abstractC0594a1) {
        if (set.contains(abstractC0594a1)) {
            consumer.accept(abstractC0594a1);
        } else {
            consumer2.accept(abstractC0594a1);
        }
    }
}
