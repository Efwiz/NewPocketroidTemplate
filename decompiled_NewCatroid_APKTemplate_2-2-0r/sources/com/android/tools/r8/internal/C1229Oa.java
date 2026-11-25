package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0680j;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.utils.AbstractC3938n2;
import com.android.tools.r8.utils.C3979v2;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.stream.Collectors;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Oa, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C1229Oa extends BS {
    static final /* synthetic */ boolean j = true;
    private C1513Xr f;
    private C0702m g;
    private final XH h;
    private final C0767v2 i;

    private C1229Oa(C0767v2 c0767v2, WH wh, C1513Xr c1513Xr, C0702m c0702m, XH xh) {
        super(wh);
        boolean z = j;
        if (!z && (c0702m == null ? c1513Xr == null || !c1513Xr.f() : !c0702m.q())) {
            throw new AssertionError();
        }
        if (!z && !c0767v2.O0()) {
            throw new AssertionError();
        }
        this.i = c0767v2;
        this.g = c0702m;
        this.f = c1513Xr;
        this.h = xh;
    }

    @Override // com.android.tools.r8.internal.BS
    public final AbstractC3472z00 P() {
        return a(this.c.b(WH.b()));
    }

    public C0767v2 R() {
        return this.i;
    }

    public C1513Xr S() {
        C1513Xr c1513Xr = this.f;
        if (c1513Xr != null) {
            return c1513Xr;
        }
        boolean z = j;
        if (!z && this.g == null) {
            throw new AssertionError();
        }
        if (!z && !this.g.q()) {
            throw new AssertionError();
        }
        return this.g.a().a(this.g, this.i);
    }

    @Override // com.android.tools.r8.internal.AbstractC3472z00
    public final C1229Oa b() {
        return this;
    }

    @Override // com.android.tools.r8.internal.BS
    /* JADX INFO: renamed from: c, reason: merged with bridge method [inline-methods] */
    public final C1229Oa a(WH wh) {
        return wh.equals(N()) ? this : (C1229Oa) this.h.a(wh, new BiFunction() { // from class: com.android.tools.r8.internal.Oa$$ExternalSyntheticLambda8
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return this.f$0.a((WH) obj, (XH) obj2);
            }
        });
    }

    @Override // com.android.tools.r8.internal.BS
    /* JADX INFO: renamed from: d, reason: merged with bridge method [inline-methods] */
    public final C1229Oa b(WH wh) {
        return a(N().a(wh));
    }

    @Override // com.android.tools.r8.internal.BS, com.android.tools.r8.internal.AbstractC3472z00
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof C1229Oa)) {
            return false;
        }
        C1229Oa c1229Oa = (C1229Oa) obj;
        if (N() == c1229Oa.N() && this.i.equals(c1229Oa.i)) {
            return S().equals(c1229Oa.S());
        }
        return false;
    }

    @Override // com.android.tools.r8.internal.BS, com.android.tools.r8.internal.AbstractC3472z00
    public final int hashCode() {
        return Objects.hash(this.c, this.i);
    }

    @Override // com.android.tools.r8.internal.AbstractC3472z00
    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.c);
        sb.append(" ");
        sb.append(this.i);
        sb.append(" {");
        ArrayList arrayListC = S().c();
        arrayListC.sort(Comparator.comparing(new Function() { // from class: com.android.tools.r8.internal.Oa$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return (C0767v2) ((C3979v2) obj).a();
            }
        }));
        sb.append((String) arrayListC.stream().map(new Function() { // from class: com.android.tools.r8.internal.Oa$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C1229Oa.a((C3979v2) obj);
            }
        }).collect(Collectors.joining(", ")));
        sb.append("}");
        return sb.toString();
    }

    @Override // com.android.tools.r8.internal.AbstractC3472z00
    public final boolean w() {
        return true;
    }

    public static C1229Oa a(final C0767v2 c0767v2, final WH wh, final C0702m<? extends C0680j> c0702m, final C1513Xr c1513Xr) {
        boolean z = j;
        if (!z && c0702m == null) {
            throw new AssertionError();
        }
        if (!z && !c0702m.q()) {
            throw new AssertionError();
        }
        if (z || c1513Xr != null) {
            return (C1229Oa) XH.a(wh, new Function() { // from class: com.android.tools.r8.internal.Oa$$ExternalSyntheticLambda6
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return C1229Oa.a(c0767v2, wh, c1513Xr, c0702m, (XH) obj);
                }
            });
        }
        throw new AssertionError();
    }

    public static C1229Oa b(final C0767v2 c0767v2, final WH wh, final C0702m<? extends C0680j> c0702m) {
        boolean z = j;
        if (!z && c0702m == null) {
            throw new AssertionError();
        }
        if (z || c0702m.q()) {
            return (C1229Oa) XH.a(wh, new Function() { // from class: com.android.tools.r8.internal.Oa$$ExternalSyntheticLambda3
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return C1229Oa.a(c0767v2, wh, c0702m, (XH) obj);
                }
            });
        }
        throw new AssertionError();
    }

    final C1229Oa c(C0767v2 c0767v2, WH wh, C0702m c0702m) {
        boolean z = j;
        if (!z && c0702m.q()) {
            throw new AssertionError();
        }
        if (!z && this.f == null) {
            throw new AssertionError();
        }
        if (z || this.f.f()) {
            return a(R() == c0767v2 ? R() : c0702m.a().Z1, N().a(wh));
        }
        throw new AssertionError();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ C1229Oa a(C0767v2 c0767v2, WH wh, C1513Xr c1513Xr, C0702m c0702m, XH xh) {
        return new C1229Oa(c0767v2, wh, c1513Xr, c0702m, xh);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static C1175Ma b(C0767v2 c0767v2) {
        AbstractC3938n2 abstractC3938n2 = AbstractC3938n2.b;
        return new C1175Ma(abstractC3938n2, abstractC3938n2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ C1229Oa a(C0767v2 c0767v2, WH wh, C0702m c0702m, XH xh) {
        return new C1229Oa(c0767v2, wh, null, c0702m, xh);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(Queue queue, C0767v2 c0767v2, Boolean bool) {
        queue.add(new C1202Na(c0767v2, bool.booleanValue() ? C1175Ma.e : C1175Ma.f));
    }

    public static C1229Oa a(final C0767v2 c0767v2, final WH wh) {
        return (C1229Oa) XH.a(wh, new Function() { // from class: com.android.tools.r8.internal.Oa$$ExternalSyntheticLambda7
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C1229Oa.a(c0767v2, wh, (XH) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ C1229Oa a(C0767v2 c0767v2, WH wh, XH xh) {
        return new C1229Oa(c0767v2, wh, C1513Xr.b(), null, xh);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public C1229Oa a(WH wh, XH xh) {
        if (j || this.c != wh) {
            return new C1229Oa(this.i, wh, this.f, this.g, xh);
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.internal.AbstractC3472z00
    public final boolean a(final C0702m c0702m) {
        C0680j c0680j = (C0680j) c0702m.f();
        AbstractC0727q0 abstractC0727q0C = c0680j.c(R());
        return abstractC0727q0C == null || abstractC0727q0C.a(c0680j) || S().a(new BiPredicate() { // from class: com.android.tools.r8.internal.Oa$$ExternalSyntheticLambda0
            @Override // java.util.function.BiPredicate
            public final boolean test(Object obj, Object obj2) {
                return C1229Oa.a(c0702m, (C0767v2) obj, (Boolean) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean a(C0702m c0702m, C0767v2 c0767v2, Boolean bool) {
        C0680j c0680j = (C0680j) c0702m.f();
        AbstractC0727q0 abstractC0727q0C = c0680j.c(c0767v2);
        return abstractC0727q0C == null || abstractC0727q0C.a(c0680j);
    }

    @Override // com.android.tools.r8.internal.BS
    public final C0767v2 a(C0722p1 c0722p1) {
        C0767v2 c0767v2D;
        return (this.i != c0722p1.Z1 || (c0767v2D = S().d()) == null) ? this.i : c0767v2D;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ String a(C3979v2 c3979v2) {
        if (((Boolean) c3979v2.b()).booleanValue()) {
            return ((C0767v2) c3979v2.a()).toString();
        }
        return "maybe(" + c3979v2.a() + Constant.AFTER_QUTO;
    }

    @Override // com.android.tools.r8.internal.AbstractC3472z00
    public final AbstractC3472z00 a(C0702m c0702m, final Function function, final Set set) {
        boolean z = j;
        if (!z && this.g == null) {
            throw new AssertionError();
        }
        if (!z && !this.g.q()) {
            throw new AssertionError();
        }
        C0767v2 c0767v2 = (C0767v2) function.apply(this.i);
        if (c0767v2.W0()) {
            return XM.a(c0767v2, false);
        }
        C1513Xr c1513Xr = this.f;
        if (c1513Xr == null || c1513Xr.f()) {
            return c0767v2 == this.i ? this : b(c0767v2, this.c, (C0702m<? extends C0680j>) this.g);
        }
        final com.android.tools.r8.utils.K k = new com.android.tools.r8.utils.K();
        final com.android.tools.r8.utils.N n = new com.android.tools.r8.utils.N();
        S().a(new BiConsumer() { // from class: com.android.tools.r8.internal.Oa$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a(set, function, k, n, (C0767v2) obj, (Boolean) obj2);
            }
        });
        if (!k.a()) {
            return c0767v2 == this.i ? this : a(c0767v2, this.c, (C0702m<? extends C0680j>) this.g, S());
        }
        if (n.c()) {
            if (!z && ((AbstractC0727q0) n.a()).isInterface()) {
                throw new AssertionError();
            }
            if (z || c0767v2 == this.g.a().Z1) {
                return b(((AbstractC0727q0) n.a()).d, this.c, (C0702m<? extends C0680j>) this.g);
            }
            throw new AssertionError();
        }
        final C1513Xr.a aVarA = C1513Xr.a();
        this.f.a(new BiConsumer() { // from class: com.android.tools.r8.internal.Oa$$ExternalSyntheticLambda2
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C1229Oa.a(function, aVarA, (C0767v2) obj, (Boolean) obj2);
            }
        });
        return a(c0767v2, this.c, (C0702m<? extends C0680j>) this.g, aVarA.a());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Set set, Function function, com.android.tools.r8.utils.K k, com.android.tools.r8.utils.N n, C0767v2 c0767v2, Boolean bool) {
        C0767v2 c0767v3;
        if (set.contains(c0767v2) || c0767v2 == (c0767v3 = (C0767v2) function.apply(c0767v2))) {
            return;
        }
        k.f();
        AbstractC0727q0 abstractC0727q0C = this.g.c(c0767v3);
        if (abstractC0727q0C.isInterface()) {
            return;
        }
        if (n.c() && abstractC0727q0C != n.a()) {
            throw new C1367Tb("More than one interface has changed to a class: " + n.a() + " and " + abstractC0727q0C);
        }
        n.a(abstractC0727q0C);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Function function, C1513Xr.a aVar, C0767v2 c0767v2, Boolean bool) {
        C0767v2 c0767v3 = (C0767v2) function.apply(c0767v2);
        if (!j && c0767v2 != c0767v3 && !bool.booleanValue()) {
            throw new AssertionError("Rewritten implies program types thus known.");
        }
        aVar.a(c0767v3, bool.booleanValue());
    }

    final BS a(C1969f3 c1969f3, C0702m c0702m) {
        C0722p1 c0722p1A = c0702m.a();
        if (c0702m.q()) {
            return a(c0702m.a().Z1, C1513Xr.a().a(c0722p1A.w5).a(c0722p1A.u5).a(), c1969f3.N());
        }
        return c(c0722p1A.Z1, c1969f3.N(), c0702m);
    }

    @Override // com.android.tools.r8.internal.BS
    public BS a(BS bs, C0702m<?> c0702m) {
        if (bs.r()) {
            return a(bs.a(), (C0702m) c0702m);
        }
        if (bs.w()) {
            C1229Oa c1229OaB = bs.b();
            if (c0702m.q()) {
                boolean z = j;
                if (!z && this.g == null) {
                    throw new AssertionError();
                }
                if (z || this.g.q()) {
                    return a(a((C0680j) this.g.f(), R(), c1229OaB.R()), c1229OaB.S(), c1229OaB.N());
                }
                throw new AssertionError();
            }
            return c(c1229OaB.R(), c1229OaB.N(), c0702m);
        }
        if (!j && !(bs instanceof AS)) {
            throw new AssertionError();
        }
        return b(bs.N());
    }

    private C1229Oa a(C0767v2 c0767v2, C1513Xr c1513Xr, WH wh) {
        boolean z = j;
        if (!z && this.g == null) {
            throw new AssertionError();
        }
        if (!z && !this.g.q()) {
            throw new AssertionError();
        }
        C1513Xr c1513XrS = S();
        if (!c1513XrS.equals(c1513Xr)) {
            c1513XrS = a((C0702m<? extends C0680j>) this.g, c1513XrS, c1513Xr);
        }
        C1513Xr c1513XrA = this.g.a().a(this.g, c0767v2);
        WH whA = N().a(wh);
        if (c1513XrS.equals(c1513XrA)) {
            return b(c0767v2, whA, (C0702m<? extends C0680j>) this.g);
        }
        return a(c0767v2, whA, (C0702m<? extends C0680j>) this.g, c1513XrS);
    }

    public static C0767v2 a(C0680j c0680j, C0767v2 c0767v2, C0767v2 c0767v3) {
        if (c0767v2 == c0767v3) {
            return c0767v2;
        }
        C0767v2 c0767v4 = c0680j.a().Z1;
        if (c0767v2 != c0767v4 && c0767v3 != c0767v4) {
            ArrayList arrayList = new ArrayList(10);
            while (c0767v2 != c0767v3) {
                arrayList.add(c0767v2);
                AbstractC0727q0 abstractC0727q0C = c0680j.c(c0767v2);
                if (abstractC0727q0C == null || (c0767v2 = abstractC0727q0C.f) == null || c0767v2 == c0767v4) {
                    int size = arrayList.size();
                    Collection collectionA = arrayList;
                    if (size > 20) {
                        collectionA = com.android.tools.r8.utils.T2.a((Iterable) arrayList);
                    }
                    while (!collectionA.contains(c0767v3)) {
                        AbstractC0727q0 abstractC0727q0C2 = c0680j.c(c0767v3);
                        if (abstractC0727q0C2 == null || (c0767v3 = abstractC0727q0C2.f) == null || c0767v3 == c0767v4) {
                        }
                    }
                    return c0767v3;
                }
            }
            return c0767v2;
        }
        return c0767v4;
    }

    /* JADX WARN: Code duplicated, block: B:121:0x014b A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:128:0x006a A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:83:0x0153 A[LOOP:1: B:82:0x0151->B:83:0x0153, LOOP_END] */
    public static C1513Xr a(C0702m<? extends C0680j> c0702m, C1513Xr c1513Xr, C1513Xr c1513Xr2) {
        AbstractC0727q0 abstractC0727q0C;
        int i;
        if (!c1513Xr.f() && !c1513Xr2.f()) {
            synchronized (c0702m.a().v) {
                com.android.tools.r8.utils.O1 o1 = c0702m.a().v;
                o1.getClass();
                C1513Xr c1513Xr3 = (C1513Xr) ((Map) o1.getOrDefault(c1513Xr, AbstractC2178hm.s())).get(c1513Xr2);
                if (c1513Xr3 != null) {
                    return c1513Xr3;
                }
                com.android.tools.r8.utils.O1 o2 = c0702m.a().v;
                o2.getClass();
                C1513Xr c1513Xr4 = (C1513Xr) ((Map) o2.getOrDefault(c1513Xr2, AbstractC2178hm.s())).get(c1513Xr);
                if (c1513Xr4 != null) {
                    return c1513Xr4;
                }
                IdentityHashMap identityHashMap = new IdentityHashMap();
                final ArrayDeque arrayDeque = new ArrayDeque();
                c1513Xr.a(new BiConsumer() { // from class: com.android.tools.r8.internal.Oa$$ExternalSyntheticLambda9
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        C1229Oa.a(arrayDeque, (C0767v2) obj, (Boolean) obj2);
                    }
                });
                c1513Xr2.a(new BiConsumer() { // from class: com.android.tools.r8.internal.Oa$$ExternalSyntheticLambda10
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        C1229Oa.b(arrayDeque, (C0767v2) obj, (Boolean) obj2);
                    }
                });
                while (!arrayDeque.isEmpty()) {
                    C1202Na c1202Na = (C1202Na) arrayDeque.poll();
                    C0767v2 c0767v2 = c1202Na.a;
                    C1175Ma c1175Ma = c1202Na.b;
                    C1175Ma c1175Ma2 = (C1175Ma) identityHashMap.computeIfAbsent(c0767v2, new Function() { // from class: com.android.tools.r8.internal.Oa$$ExternalSyntheticLambda11
                        @Override // java.util.function.Function
                        public final Object apply(Object obj) {
                            return C1229Oa.b((C0767v2) obj);
                        }
                    });
                    c1175Ma2.getClass();
                    boolean z = C1175Ma.g;
                    if (!z && !c1175Ma.a.c() && !c1175Ma.b.c()) {
                        throw new AssertionError();
                    }
                    if (!z && c1175Ma.a()) {
                        throw new AssertionError();
                    }
                    if (c1175Ma.a.c()) {
                        AbstractC3938n2 abstractC3938n2 = c1175Ma2.a;
                        AbstractC3938n2 abstractC3938n3 = c1175Ma.a;
                        if (!z && !abstractC3938n2.c() && !abstractC3938n3.c()) {
                            throw new AssertionError();
                        }
                        c1175Ma2.a = (abstractC3938n2.d() || abstractC3938n3.d()) ? AbstractC3938n2.a : AbstractC3938n2.c;
                        if (c1175Ma2.b.a() && c1175Ma2.a != abstractC3938n2) {
                            abstractC0727q0C = c0702m.c(c0767v2);
                            if (abstractC0727q0C != null) {
                                for (C0767v2 c0767v3 : abstractC0727q0C.g.a) {
                                    arrayDeque.add(new C1202Na(c0767v3, c1175Ma));
                                }
                            }
                        }
                    } else {
                        AbstractC3938n2 abstractC3938n4 = c1175Ma2.b;
                        AbstractC3938n2 abstractC3938n5 = c1175Ma.b;
                        if (!z && !abstractC3938n4.c() && !abstractC3938n5.c()) {
                            throw new AssertionError();
                        }
                        c1175Ma2.b = (abstractC3938n4.d() || abstractC3938n5.d()) ? AbstractC3938n2.a : AbstractC3938n2.c;
                        if (c1175Ma2.a.a() && c1175Ma2.b != abstractC3938n4) {
                            abstractC0727q0C = c0702m.c(c0767v2);
                            if (abstractC0727q0C != null) {
                                while (i < r4) {
                                    arrayDeque.add(new C1202Na(c0767v3, c1175Ma));
                                }
                            }
                        }
                    }
                }
                final ArrayList<C3979v2> arrayList = new ArrayList(identityHashMap.size());
                identityHashMap.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.Oa$$ExternalSyntheticLambda12
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        C1229Oa.a(arrayList, (C0767v2) obj, (C1175Ma) obj2);
                    }
                });
                C1513Xr.a aVarA = C1513Xr.a();
                for (C3979v2 c3979v2 : arrayList) {
                    Iterator it = arrayList.iterator();
                    do {
                        if (!it.hasNext()) {
                            aVarA.a((C0767v2) c3979v2.a(), ((Boolean) c3979v2.b()).booleanValue());
                            break;
                        }
                    } while (!((C0680j) c0702m.f()).b((C0767v2) ((C3979v2) it.next()).a(), (C0767v2) c3979v2.a()));
                }
                C1513Xr c1513XrA = aVarA.a();
                if (!c1513Xr.equals(c1513Xr2)) {
                    synchronized (c0702m.a().v) {
                        c0702m.a().v.a(c1513Xr, c1513Xr2, c1513XrA);
                    }
                }
                return c1513XrA;
            }
        }
        return C1513Xr.b();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(Queue queue, C0767v2 c0767v2, Boolean bool) {
        queue.add(new C1202Na(c0767v2, bool.booleanValue() ? C1175Ma.c : C1175Ma.d));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(List list, C0767v2 c0767v2, C1175Ma c1175Ma) {
        if (c1175Ma.a()) {
            if (!C1175Ma.g && !c1175Ma.a()) {
                throw new AssertionError();
            }
            list.add(new C3979v2(c0767v2, Boolean.valueOf(c1175Ma.a.d() && c1175Ma.b.d())));
        }
    }
}
