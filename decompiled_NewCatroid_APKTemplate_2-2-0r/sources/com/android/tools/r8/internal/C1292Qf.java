package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0748t0;
import com.android.tools.r8.graph.C0702m;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Qf, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1292Qf {
    static final /* synthetic */ boolean c = true;
    private final C0702m a;
    private final HashMap b = new HashMap();

    private C1292Qf(C0702m c0702m) {
        this.a = c0702m;
    }

    public static C1292Qf a(C0702m c0702m, final ConcurrentHashMap concurrentHashMap) {
        final C1292Qf c1292Qf = new C1292Qf(c0702m);
        concurrentHashMap.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.Qf$$ExternalSyntheticLambda3
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C1292Qf.a(this.f$0, concurrentHashMap, (RE) obj, (Set) obj2);
            }
        });
        return c1292Qf;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ C1318Rf b(com.android.tools.r8.graph.o5 o5Var, RE re) {
        return new C1318Rf(o5Var, re.a());
    }

    final boolean c(C1318Rf c1318Rf) {
        return this.b.containsKey(new RE(c1318Rf.b(), c1318Rf.c().getReference()));
    }

    final void b(C1318Rf c1318Rf) {
        boolean z = c;
        if (!z && !c1318Rf.e().isEmpty()) {
            throw new AssertionError();
        }
        if (!z && !c1318Rf.d().isEmpty()) {
            throw new AssertionError();
        }
        C1318Rf c1318Rf2 = (C1318Rf) this.b.remove(new RE(c1318Rf.b(), c1318Rf.c().getReference()));
        if (!z && c1318Rf2 != c1318Rf) {
            throw new AssertionError();
        }
    }

    final Collection a() {
        return this.b.values();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(C1292Qf c1292Qf, Map map, RE re, Set set) {
        com.android.tools.r8.graph.o5 o5VarA = AbstractC0748t0.a(c1292Qf.a.b(re.b()));
        C1318Rf c1318RfA = o5VarA != null ? c1292Qf.a(o5VarA, re) : null;
        Iterator it = set.iterator();
        while (it.hasNext()) {
            RE re2 = (RE) it.next();
            com.android.tools.r8.graph.o5 o5VarA2 = AbstractC0748t0.a(c1292Qf.a.b(re2.b()));
            if (o5VarA2 == null) {
                if (c) {
                    c1318RfA.i();
                } else {
                    throw new AssertionError();
                }
            } else if (o5VarA2.e().B1() && re2.a() == 0 && c1318RfA.f()) {
                c1318RfA.i();
            } else if (!map.containsKey(re2)) {
                com.android.tools.r8.ir.optimize.info.h hVarB = o5VarA2.B();
                if (!hVarB.v() || !hVarB.t().get(re2.a())) {
                    c1318RfA.i();
                }
            } else {
                C1318Rf c1318RfA2 = c1292Qf.a(o5VarA2, re2);
                if (c1318RfA != c1318RfA2) {
                    c1318RfA.a(c1318RfA2);
                }
            }
        }
    }

    final C1318Rf a(final com.android.tools.r8.graph.o5 o5Var, RE re) {
        return (C1318Rf) this.b.computeIfAbsent(re, new Function() { // from class: com.android.tools.r8.internal.Qf$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C1292Qf.b(o5Var, (RE) obj);
            }
        });
    }

    final void a(final Consumer consumer) {
        Set setC = OU.c();
        for (C1318Rf c1318Rf : a()) {
            if (!setC.contains(c1318Rf)) {
                final C0947Ed c0947EdA = C0947Ed.a();
                final ArrayDeque arrayDeque = new ArrayDeque();
                arrayDeque.add(new C1005Gd(c1318Rf));
                while (!arrayDeque.isEmpty()) {
                    AbstractC1034Hd abstractC1034Hd = (AbstractC1034Hd) arrayDeque.removeLast();
                    c0947EdA.a(abstractC1034Hd);
                    if (!(abstractC1034Hd instanceof C0979Fd)) {
                        final C1318Rf c1318Rf2 = (C1318Rf) abstractC1034Hd.b();
                        setC.add(c1318Rf2);
                        arrayDeque.add(abstractC1034Hd.a().c());
                        c1318Rf2.e().removeIf(new Predicate() { // from class: com.android.tools.r8.internal.Qf$$ExternalSyntheticLambda2
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                return C1292Qf.a(c0947EdA, c1318Rf2, consumer, arrayDeque, (C1318Rf) obj);
                            }
                        });
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(C0947Ed c0947Ed, C1318Rf c1318Rf, Consumer consumer, Deque deque, C1318Rf c1318Rf2) {
        if (c0947Ed.a(c1318Rf2)) {
            if (!AbstractC2734ot.a((Iterable) c0947Ed.b(c1318Rf2), new InterfaceC3205vM() { // from class: com.android.tools.r8.internal.Qf$$ExternalSyntheticLambda0
                @Override // com.android.tools.r8.internal.InterfaceC3205vM
                public final boolean apply(Object obj) {
                    return C1292Qf.a((C1318Rf) obj);
                }
            })) {
                return false;
            }
            boolean zRemove = c1318Rf2.d().remove(c1318Rf);
            if (!c && !zRemove) {
                throw new AssertionError();
            }
            consumer.accept(c1318Rf);
            return true;
        }
        deque.add(new C1005Gd(c1318Rf2));
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(C1318Rf c1318Rf) {
        return c1318Rf.e().size() == 1;
    }
}
