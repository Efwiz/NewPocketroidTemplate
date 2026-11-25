package com.android.tools.r8.naming;

import com.android.tools.r8.internal.AbstractC2004fU;
import com.android.tools.r8.internal.AbstractC3401y;
import com.android.tools.r8.internal.C0964Eo;
import com.android.tools.r8.internal.C2308jU;
import com.android.tools.r8.internal.InterfaceC3018sr;
import com.android.tools.r8.internal.OU;
import com.android.tools.r8.internal.PK;
import com.android.tools.r8.internal.RK;
import com.android.tools.r8.position.Position;
import com.android.tools.r8.references.MethodReference;
import com.android.tools.r8.utils.A1;
import com.android.tools.r8.utils.C3912j0;
import com.android.tools.r8.utils.C3979v2;
import com.android.tools.r8.utils.InterfaceC3895f3;
import com.android.tools.r8.utils.InterfaceC3905h3;
import com.android.tools.r8.utils.Q2;
import com.android.tools.r8.utils.V1;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.naming.p, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3650p {
    static final /* synthetic */ boolean l = true;
    private final String a;
    private final String b;
    private final HashMap c;
    private final HashMap d;
    private final HashMap e;
    private final ArrayList f;
    private final C3652q g;
    private final C3652q h;
    private final Map i;
    private final C3650p j;
    private final A1 k;

    private C3650p(String str, String str2, C3652q c3652q, C3652q c3652q2, A1 a1) {
        this.c = new HashMap();
        this.d = new HashMap();
        this.e = new HashMap();
        this.f = new ArrayList();
        this.a = str;
        this.b = str2;
        this.h = c3652q2;
        this.g = c3652q;
        this.k = a1;
        HashMap map = c3652q.a;
        this.i = map;
        this.j = (C3650p) map.get(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Q2 a(Q2 q2, Q2 q3) {
        return q3;
    }

    private void b(C3605b c3605b, C3634k c3634k) throws Throwable {
        final Map<String, String> map = c3605b.e().b;
        c3634k.a(new InterfaceC3905h3() { // from class: com.android.tools.r8.naming.p$$ExternalSyntheticLambda21
            @Override // com.android.tools.r8.utils.InterfaceC3905h3
            public final void accept(Object obj) {
                this.f$0.a(map, (Q) obj);
            }
        });
    }

    /* JADX WARN: Code duplicated, block: B:50:0x0130  */
    private void c(C3605b c3605b, C3634k c3634k) throws MappingComposeException {
        List listSingletonList;
        Q.b bVarK;
        C3634k c3634k2 = c3634k;
        final Map<String, String> map = c3605b.e().b;
        Iterator<Map.Entry<String, C3634k.c>> it = c3634k2.e.entrySet().iterator();
        while (it.hasNext()) {
            for (C3634k.c cVar : it.next().getValue().c()) {
                Q qA = cVar.a(c3634k2);
                List<C3634k.b> listB = cVar.b();
                C3648o c3648o = new C3648o();
                ArrayList arrayList = new ArrayList();
                C3646n c3646n = new C3646n();
                List<List> arrayList2 = new ArrayList();
                for (int i = 0; i < listB.size(); i++) {
                    C3634k.b bVar = listB.get(i);
                    c3648o.a(bVar.a);
                    a(bVar);
                    Q.b bVarB = bVar.b().b();
                    C3650p c3650p = bVarB.e() ? (C3650p) this.i.get(bVarB.g()) : this.j;
                    if (c3650p == null) {
                        listSingletonList = null;
                    } else {
                        if (bVarB.e()) {
                            bVarK = bVarB;
                            bVarK = bVarB.k();
                        }
                        bVarK = bVarB;
                        Q.b bVarB2 = bVarK.b();
                        this.h.a(c3650p, bVarB2);
                        Q2 q2 = (Q2) c3650p.d.get(bVarB2);
                        if (q2 != null) {
                            int iA = bVar.a(-1);
                            Map.Entry entryA = q2.a(Integer.valueOf(iA));
                            if (entryA == null && iA == 0 && !bVar.c.b()) {
                                entryA = q2.a(Integer.valueOf(bVar.f()));
                            }
                            if (entryA != null) {
                                listSingletonList = (List) entryA.getValue();
                            } else {
                                N0 n0 = bVar.c;
                                if (n0 != null && !n0.b() && !this.k.t1().c) {
                                    throw new MappingComposeException("Could not find original starting position of '" + bVar.a.a + "' which should be " + iA);
                                }
                                listSingletonList = null;
                            }
                            if (!l && !c3648o.c()) {
                                throw new AssertionError();
                            }
                        } else {
                            C3634k.b bVar2 = (C3634k.b) c3650p.e.get(bVarB2);
                            if (bVar2 == null) {
                                listSingletonList = null;
                            } else {
                                listSingletonList = Collections.singletonList(bVar2);
                            }
                        }
                    }
                    final ArrayList arrayList3 = new ArrayList();
                    if (arrayList2.isEmpty()) {
                        b(a(listSingletonList, bVar, c3646n), Collections.emptyList(), new Consumer() { // from class: com.android.tools.r8.naming.p$$ExternalSyntheticLambda12
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                arrayList3.add((List) obj);
                            }
                        });
                    } else {
                        for (List list : arrayList2) {
                            b(a(listSingletonList, bVar.a(((C3634k.b) list.get(0)).a), c3646n), list, new Consumer() { // from class: com.android.tools.r8.naming.p$$ExternalSyntheticLambda12
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    arrayList3.add((List) obj);
                                }
                            });
                            listSingletonList = listSingletonList;
                        }
                    }
                    if (N.a(i, listB)) {
                        arrayList2 = arrayList3;
                    } else {
                        Iterator it2 = arrayList3.iterator();
                        while (it2.hasNext()) {
                            arrayList.addAll((List) it2.next());
                        }
                        arrayList2 = Collections.emptyList();
                    }
                }
                C3634k.b bVar3 = (C3634k.b) V1.b(arrayList);
                if (c3646n.b != null) {
                    this.h.a(this.j.b, ((C3634k.b) V1.b(listB)).b.c(), bVar3.a()).a(listB);
                    bVar3.a(C3912j0.b(), c3646n.b);
                }
                if (!c3646n.a.isEmpty()) {
                    r rVarA = r.a(listB);
                    ArrayList<PK> arrayList4 = new ArrayList(c3646n.a);
                    arrayList4.sort(Comparator.comparing(new Function() { // from class: com.android.tools.r8.naming.p$$ExternalSyntheticLambda13
                        @Override // java.util.function.Function
                        public final Object apply(Object obj) {
                            return C3650p.a((PK) obj);
                        }
                    }));
                    for (PK pk : arrayList4) {
                        AbstractC3401y abstractC3401yR = pk.r();
                        InterfaceC3018sr it3 = abstractC3401yR.keySet().iterator();
                        while (it3.hasNext()) {
                            int iIntValue = ((Integer) it3.next()).intValue();
                            C0964Eo c0964Eo = (C0964Eo) abstractC3401yR;
                            c0964Eo.b(iIntValue, rVarA.a(c0964Eo.get(iIntValue)));
                        }
                        bVar3.a(C3912j0.b(), pk);
                    }
                }
                Q.b bVarB3 = qA.a(new Function() { // from class: com.android.tools.r8.naming.p$$ExternalSyntheticLambda14
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return C3650p.b(map, (String) obj);
                    }
                }).b();
                if (bVar3.a != null) {
                    ((Q2) this.d.computeIfAbsent(bVarB3, new Function() { // from class: com.android.tools.r8.naming.p$$ExternalSyntheticLambda15
                        @Override // java.util.function.Function
                        public final Object apply(Object obj) {
                            return C3650p.a((Q.b) obj);
                        }
                    })).a(c3648o.b(), c3648o.a(), arrayList);
                } else {
                    if (!l && arrayList.size() != 1) {
                        throw new AssertionError();
                    }
                    this.e.put(bVarB3, bVar3);
                }
                c3634k2 = c3634k;
            }
            c3634k2 = c3634k;
        }
    }

    public final String a() {
        return this.a;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ String b(Map map, String str) {
        return (String) map.getOrDefault(str, str);
    }

    public final void a(C3605b c3605b, C3634k c3634k) throws Throwable {
        List<com.android.tools.r8.naming.mappinginformation.e> listC = c3634k.c();
        if (listC != null) {
            this.f.addAll(listC);
        }
        b(c3605b, c3634k);
        c(c3605b, c3634k);
    }

    private static void b(List list, List list2, Consumer consumer) {
        if (!l && list.isEmpty()) {
            throw new AssertionError();
        }
        N0 n0 = ((C3634k.b) list.get(0)).a;
        if (n0 == null) {
            ArrayList arrayList = new ArrayList(list.size() + list2.size());
            arrayList.addAll(list2);
            arrayList.addAll(list);
            consumer.accept(arrayList);
            return;
        }
        final com.android.tools.r8.utils.N n = new com.android.tools.r8.utils.N(n0);
        int i = 0;
        for (int i2 = 0; i2 < list.size(); i2++) {
            C3634k.b bVar = (C3634k.b) list.get(i2);
            final N0 n1 = (N0) n.a();
            if (!bVar.a.equals(n1)) {
                List listA = V1.a((Collection) list2, new Function() { // from class: com.android.tools.r8.naming.p$$ExternalSyntheticLambda4
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return C3650p.b(n1, (C3634k.b) obj);
                    }
                });
                List listSubList = list.subList(i, i2);
                ArrayList arrayList2 = new ArrayList(listSubList.size() + listA.size());
                arrayList2.addAll(listA);
                arrayList2.addAll(listSubList);
                consumer.accept(arrayList2);
                n.a(bVar.a);
                i = i2;
            }
        }
        List listA2 = V1.a((Collection) list2, new Function() { // from class: com.android.tools.r8.naming.p$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C3650p.a(n, (C3634k.b) obj);
            }
        });
        List listSubList2 = list.subList(i, list.size());
        ArrayList arrayList3 = new ArrayList(listSubList2.size() + listA2.size());
        arrayList3.addAll(listA2);
        arrayList3.addAll(listSubList2);
        consumer.accept(arrayList3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(final Map map, Q q) {
        C3650p c3650p;
        Q q2;
        Q.a aVarA = q.b().a();
        Q.a aVarA2 = q.a(new Function() { // from class: com.android.tools.r8.naming.p$$ExternalSyntheticLambda22
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C3650p.a(map, (String) obj);
            }
        }).a();
        if (aVarA.e()) {
            c3650p = (C3650p) this.i.get(aVarA.g());
        } else {
            c3650p = this.j;
        }
        if (c3650p == null) {
            q2 = null;
        } else {
            Q.a aVar = aVarA.e() ? new Q.a(aVarA.h(), aVarA.c) : aVarA;
            this.h.a(c3650p, aVar);
            q2 = (Q) c3650p.c.get(aVar);
        }
        if (q2 != null) {
            Q.c cVarB = q2.b();
            if (!cVarB.e() && aVarA.e()) {
                C3650p c3650p2 = (C3650p) this.i.get(aVarA.g());
                String str = c3650p2 != null ? c3650p2.a : null;
                if (str != null) {
                    cVarB = cVarB.b(str);
                }
            }
            q = new Q(cVarB, aVarA2, Position.UNKNOWN);
        }
        Q q3 = (Q) this.c.put(aVarA2, q);
        if (!l && q3 != null) {
            throw new AssertionError();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ String a(Map map, String str) {
        return (String) map.getOrDefault(str, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ String a(PK pk) {
        return pk.q().toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Q2 a(Q.b bVar) {
        return new Q2(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ C3634k.b a(com.android.tools.r8.utils.N n, C3634k.b bVar) {
        return bVar.a((N0) n.a());
    }

    private void a(C3634k.b bVar) throws MappingComposeException {
        for (com.android.tools.r8.naming.mappinginformation.e eVar : bVar.e()) {
            if (eVar.o()) {
                final C2308jU c2308jUI = eVar.i();
                c2308jUI.r().forEach(new Consumer() { // from class: com.android.tools.r8.naming.p$$ExternalSyntheticLambda11
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        this.f$0.a(c2308jUI, (AbstractC2004fU) obj);
                    }
                });
            } else if (eVar.l()) {
                PK pkC = eVar.c();
                MethodReference methodReferenceQ = pkC.q();
                if (methodReferenceQ != null) {
                    this.h.a(methodReferenceQ, pkC);
                } else {
                    throw new MappingComposeException("Unable to compose outline call site information without outline key: " + pkC.p());
                }
            } else {
                continue;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ C3634k.b b(N0 n0, C3634k.b bVar) {
        return bVar.a(n0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b(C3634k.b bVar, com.android.tools.r8.naming.mappinginformation.e eVar) {
        bVar.a(C3912j0.b(), eVar);
    }

    private void b(final com.android.tools.r8.utils.V v) {
        ArrayList arrayList = new ArrayList(this.c.values());
        arrayList.sort(Comparator.comparing(new Q$$ExternalSyntheticLambda3()));
        arrayList.forEach(new Consumer() { // from class: com.android.tools.r8.naming.p$$ExternalSyntheticLambda10
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C3650p.a(v, (Q) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(C2308jU c2308jU, AbstractC2004fU abstractC2004fU) {
        abstractC2004fU.getClass();
        this.h.b.add(c2308jU);
    }

    private List a(List list, C3634k.b bVar, C3646n c3646n) throws MappingComposeException {
        N0 n0;
        if (list != null && !list.isEmpty()) {
            C3634k.b bVar2 = (C3634k.b) V1.b(list);
            if (bVar.c == null) {
                final C3634k.b bVar3 = new C3634k.b(bVar.a, bVar2.b, null, bVar.d);
                a(bVar3.e(), bVar2.e(), new Consumer() { // from class: com.android.tools.r8.naming.p$$ExternalSyntheticLambda9
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        C3650p.a(bVar3, (com.android.tools.r8.naming.mappinginformation.e) obj);
                    }
                });
                return Collections.singletonList(bVar3);
            }
            TreeMap treeMap = new TreeMap();
            ArrayList arrayList = new ArrayList();
            boolean z = false;
            int iA = -1;
            int iMax = -1;
            for (int i = 0; i < list.size(); i++) {
                C3634k.b bVar4 = (C3634k.b) list.get(i);
                arrayList.add(bVar4);
                if (!N.a(i, list)) {
                    if (iA == -1) {
                        iA = bVar4.a(-1);
                    }
                    iMax = Math.max(bVar4.f(), iMax);
                    N0 n1 = bVar4.a;
                    if (n1 == null) {
                        treeMap.put(-1, arrayList);
                    } else if (n1.a()) {
                        z = true;
                    } else {
                        for (int i2 = bVar4.a.a; i2 <= bVar4.a.b; i2++) {
                            treeMap.put(Integer.valueOf(i2), arrayList);
                        }
                    }
                    arrayList = new ArrayList();
                }
            }
            C3644m c3644m = new C3644m(z, treeMap, arrayList);
            ArrayList arrayList2 = new ArrayList();
            boolean z2 = l;
            if (!z2 && bVar.a == null) {
                throw new AssertionError();
            }
            List listB = c3644m.b(bVar.a(-1));
            C3634k.b bVarC = V1.c(listB);
            int iIntValue = bVar.a.a;
            if (listB == null) {
                if (!bVar.c.b() && (list.size() != 1 || bVar2.a != null)) {
                    if (bVar.c.a == 0) {
                        int iB = bVar.b(iIntValue);
                        Integer numA = c3644m.a(iB);
                        if (numA == null) {
                            numA = Integer.valueOf(bVar.f());
                        } else if (numA.intValue() > iB) {
                            numA = Integer.valueOf(numA.intValue() - 1);
                        }
                        N0 n2 = new N0(iB, numA.intValue());
                        C3634k.b bVar5 = new C3634k.b(n2, bVar2.b.b(), n2, bVar2.a());
                        bVar5.a(bVar2.d());
                        C3979v2 c3979v2A = C3979v2.a(Integer.valueOf(numA.intValue() - iB), bVar5);
                        bVarC = (C3634k.b) c3979v2A.b();
                        List listSingletonList = Collections.singletonList((C3634k.b) c3979v2A.b());
                        iIntValue += ((Integer) c3979v2A.a()).intValue() + 1;
                        listB = listSingletonList;
                    } else {
                        throw new MappingComposeException("Unexpected missing original position for '" + bVar + "'.");
                    }
                } else {
                    N0 n3 = bVar.a;
                    Q.b bVar6 = bVar2.b;
                    N0 n4 = bVar2.c;
                    if (n4 != null && n4.c() == 1) {
                        n0 = bVar2.c;
                    } else {
                        n0 = C3662t.e;
                    }
                    return Collections.singletonList(new C3634k.b(n3, bVar6, n0, bVar.d));
                }
            }
            if (!z2 && bVarC == null) {
                throw new AssertionError();
            }
            if (bVarC.a.equals(bVar.c)) {
                N0 n5 = bVar.a;
                a(arrayList2, bVar, listB, c3646n, n5.a, n5.b);
                return arrayList2;
            }
            List listSingletonList2 = listB;
            C3634k.b bVar7 = bVarC;
            int iIntValue2 = iIntValue;
            int i3 = bVar.a.a;
            while (true) {
                int i4 = bVar.a.b;
                if (iIntValue2 <= i4) {
                    List listB2 = c3644m.b(bVar.b(iIntValue2));
                    C3634k.b bVarC2 = V1.c(listB2);
                    if (bVarC2 == null || !bVar7.a.equals(bVarC2.a)) {
                        a(arrayList2, bVar, listSingletonList2, c3646n, i3, iIntValue2 - 1);
                        if (listB2 != null) {
                            i3 = iIntValue2;
                            listSingletonList2 = listB2;
                            bVar7 = bVarC2;
                        } else if (this.k.t1().c) {
                            int iB2 = bVar.b(bVar.b(iIntValue2));
                            Integer numA2 = c3644m.a(iB2);
                            if (numA2 == null) {
                                numA2 = Integer.valueOf(bVar.f());
                            } else if (numA2.intValue() > iB2) {
                                numA2 = Integer.valueOf(numA2.intValue() - 1);
                            }
                            N0 n6 = new N0(iB2, numA2.intValue());
                            C3634k.b bVar8 = new C3634k.b(n6, bVar7.b.b(), n6, bVar7.a());
                            bVar8.a(bVar7.d());
                            C3979v2 c3979v2A2 = C3979v2.a(Integer.valueOf(numA2.intValue() - iB2), bVar8);
                            C3634k.b bVar9 = (C3634k.b) c3979v2A2.b();
                            listSingletonList2 = Collections.singletonList((C3634k.b) c3979v2A2.b());
                            bVar7 = bVar9;
                            i3 = iIntValue2;
                            iIntValue2 = ((Integer) c3979v2A2.a()).intValue() + iIntValue2;
                        } else {
                            throw new MappingComposeException("Unexpected missing original position for '" + bVar + "'.");
                        }
                    }
                    iIntValue2++;
                } else {
                    a(arrayList2, bVar, listSingletonList2, c3646n, i3, i4);
                    return arrayList2;
                }
            }
        } else {
            return Collections.singletonList(bVar);
        }
    }

    private void c(final com.android.tools.r8.utils.V v) {
        this.e.forEach(new BiConsumer() { // from class: com.android.tools.r8.naming.p$$ExternalSyntheticLambda23
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C3650p.a(v, (Q.b) obj, (C3634k.b) obj2);
            }
        });
        ArrayList arrayList = new ArrayList(this.d.keySet());
        arrayList.sort(Comparator.comparing(new Function() { // from class: com.android.tools.r8.naming.p$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((Q.b) obj).c();
            }
        }));
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ((Q2) this.d.get((Q.b) it.next())).a(new Consumer() { // from class: com.android.tools.r8.naming.p$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    C3650p.a(v, (List) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C3634k.b bVar, com.android.tools.r8.naming.mappinginformation.e eVar) {
        bVar.a(C3912j0.b(), eVar);
    }

    private static void a(ArrayList arrayList, C3634k.b bVar, List list, final C3646n c3646n, int i, int i2) {
        final N0 n0 = ((C3634k.b) list.get(0)).a;
        if (!l && !list.stream().allMatch(new Predicate() { // from class: com.android.tools.r8.naming.p$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return C3650p.a(n0, (C3634k.b) obj);
            }
        })) {
            throw new AssertionError();
        }
        N0 n1 = new N0(i, i2);
        boolean zEquals = n0.equals(bVar.c);
        Iterator it = list.iterator();
        while (it.hasNext()) {
            C3634k.b bVar2 = (C3634k.b) it.next();
            N0 n2 = bVar2.c;
            if (!zEquals && n2 != null && n2.c() != 1) {
                int iB = bVar2.b(bVar.b(i));
                if (bVar.c.c() == 1) {
                    n2 = new N0(iB, iB);
                } else {
                    if (!l && n1.c() > n2.c()) {
                        throw new AssertionError();
                    }
                    n2 = new N0(iB, (n1.c() + iB) - 1);
                }
            }
            final C3634k.b bVar3 = new C3634k.b(n1, bVar2.b, n2, bVar.d);
            final ArrayList arrayList2 = new ArrayList();
            bVar2.e().forEach(new Consumer() { // from class: com.android.tools.r8.naming.p$$ExternalSyntheticLambda7
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    C3650p.a(c3646n, arrayList2, (com.android.tools.r8.naming.mappinginformation.e) obj);
                }
            });
            a(bVar3.e(), arrayList2, new Consumer() { // from class: com.android.tools.r8.naming.p$$ExternalSyntheticLambda8
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    C3650p.b(bVar3, (com.android.tools.r8.naming.mappinginformation.e) obj);
                }
            });
            arrayList.add(bVar3);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(N0 n0, C3634k.b bVar) {
        return bVar.a.equals(n0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(C3646n c3646n, List list, com.android.tools.r8.naming.mappinginformation.e eVar) {
        eVar.getClass();
        if (eVar instanceof RK) {
            c3646n.b = eVar.d();
        } else if (eVar instanceof PK) {
            c3646n.a.add(eVar.c());
        } else {
            list.add(eVar);
        }
    }

    private static void a(List list, List list2, final Consumer consumer) {
        final Set setC = OU.c();
        Iterator it = list2.iterator();
        while (it.hasNext()) {
            com.android.tools.r8.naming.mappinginformation.e eVar = (com.android.tools.r8.naming.mappinginformation.e) it.next();
            Iterator it2 = list.iterator();
            boolean z = false;
            while (it2.hasNext()) {
                com.android.tools.r8.naming.mappinginformation.e eVar2 = (com.android.tools.r8.naming.mappinginformation.e) it2.next();
                if (!eVar2.a(eVar)) {
                    setC.add(eVar2);
                    consumer.accept(eVar2.b(eVar));
                    z = true;
                }
            }
            if (!z) {
                consumer.accept(eVar);
            }
        }
        list.forEach(new Consumer() { // from class: com.android.tools.r8.naming.p$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C3650p.a(setC, consumer, (com.android.tools.r8.naming.mappinginformation.e) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Set set, Consumer consumer, com.android.tools.r8.naming.mappinginformation.e eVar) {
        if (set.contains(eVar)) {
            return;
        }
        consumer.accept(eVar);
    }

    public final void a(final com.android.tools.r8.utils.V v) {
        v.a(this.a).a(" -> ").a(this.b).a(":\n");
        this.f.forEach(new Consumer() { // from class: com.android.tools.r8.naming.p$$ExternalSyntheticLambda20
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C3650p.a(v, (com.android.tools.r8.naming.mappinginformation.e) obj);
            }
        });
        b(v);
        c(v);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(com.android.tools.r8.utils.V v, com.android.tools.r8.naming.mappinginformation.e eVar) {
        v.a("# " + eVar.p()).a("\n");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(com.android.tools.r8.utils.V v, Q q) {
        v.a("    ").a(q.toString()).a("\n");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(com.android.tools.r8.utils.V v, Q.b bVar, C3634k.b bVar2) {
        v.a("    ").a(bVar2.toString()).a("\n");
        Iterator it = bVar2.e().iterator();
        while (it.hasNext()) {
            v.a("    ").a("# ").a(((com.android.tools.r8.naming.mappinginformation.e) it.next()).p()).a("\n");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(final com.android.tools.r8.utils.V v, List list) {
        list.forEach(new Consumer() { // from class: com.android.tools.r8.naming.p$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C3650p.a(v, (C3634k.b) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(com.android.tools.r8.utils.V v, C3634k.b bVar) {
        v.a("    ").a(bVar.toString()).a("\n");
        Iterator it = bVar.e().iterator();
        while (it.hasNext()) {
            v.a("    ").a("# ").a(((com.android.tools.r8.naming.mappinginformation.e) it.next()).p()).a("\n");
        }
    }

    public final C3650p a(C3650p c3650p) {
        C3650p c3650p2 = new C3650p(this.a, c3650p.b, this.g, null, this.k);
        ArrayList arrayList = c3650p.f;
        ArrayList arrayList2 = this.f;
        final ArrayList arrayList3 = c3650p2.f;
        Objects.requireNonNull(arrayList3);
        a(arrayList, arrayList2, new Consumer() { // from class: com.android.tools.r8.naming.p$$ExternalSyntheticLambda16
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                arrayList3.add((com.android.tools.r8.naming.mappinginformation.e) obj);
            }
        });
        a(c3650p2.c, this.c, c3650p.c, new InterfaceC3895f3() { // from class: com.android.tools.r8.naming.p$$ExternalSyntheticLambda17
            @Override // com.android.tools.r8.utils.InterfaceC3895f3
            public final Object apply(Object obj, Object obj2) {
                return C3650p.a((Q) obj, (Q) obj2);
            }
        });
        a(c3650p2.e, this.e, c3650p.e, new InterfaceC3895f3() { // from class: com.android.tools.r8.naming.p$$ExternalSyntheticLambda18
            @Override // com.android.tools.r8.utils.InterfaceC3895f3
            public final Object apply(Object obj, Object obj2) {
                return this.f$0.a((C3634k.b) obj, (C3634k.b) obj2);
            }
        });
        a(c3650p2.d, this.d, c3650p.d, new InterfaceC3895f3() { // from class: com.android.tools.r8.naming.p$$ExternalSyntheticLambda19
            @Override // com.android.tools.r8.utils.InterfaceC3895f3
            public final Object apply(Object obj, Object obj2) {
                return C3650p.a((Q2) obj, (Q2) obj2);
            }
        });
        return c3650p2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Q a(Q q, Q q2) {
        if (l || q == null) {
            return q2;
        }
        throw new AssertionError();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ C3634k.b a(C3634k.b bVar, C3634k.b bVar2) throws MappingComposeException {
        if (bVar == null || bVar2 == null) {
            return bVar != null ? bVar : bVar2;
        }
        throw new MappingComposeException("Cannot compose duplicate methods without position in class '" + this.b + "': '" + bVar + "' and '" + bVar2);
    }

    private static void a(HashMap map, HashMap map2, HashMap map3, InterfaceC3895f3 interfaceC3895f3) {
        if (!l && !map.isEmpty()) {
            throw new AssertionError();
        }
        map.putAll(map2);
        for (Map.Entry entry : map3.entrySet()) {
            map.put((Q.c) entry.getKey(), interfaceC3895f3.apply(map.get(entry.getKey()), entry.getValue()));
        }
    }
}
