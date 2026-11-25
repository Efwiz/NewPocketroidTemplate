package com.android.tools.r8.naming;

import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.internal.AbstractC2178hm;
import com.android.tools.r8.internal.AbstractC2734ot;
import com.android.tools.r8.internal.AbstractC2963s10;
import com.android.tools.r8.internal.C1507Xl;
import com.android.tools.r8.internal.InterfaceC3205vM;
import com.android.tools.r8.internal.RD;
import com.android.tools.r8.internal.RK;
import com.android.tools.r8.utils.InterfaceC3905h3;
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
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;

/* JADX INFO: renamed from: com.android.tools.r8.naming.k, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C3634k implements InterfaceC3627h {
    private static final List h = Collections.emptyList();
    static final /* synthetic */ boolean i = true;
    public final String a;
    public final String b;
    private final AbstractC2178hm c;
    private final AbstractC2178hm d;
    public final Map<String, c> e;
    public final Map f;
    private final List g;

    /* JADX INFO: renamed from: com.android.tools.r8.naming.k$b */
    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class b implements P {
        public final N0 a;
        public final Q.b b;
        public final N0 c;
        public final String d;
        private Q.b e = null;
        private boolean f = false;
        private final int g = g();
        private List h = C3634k.h;
        static final /* synthetic */ boolean j = true;
        private static int i = 0;

        /* JADX INFO: Access modifiers changed from: package-private */
        /* JADX INFO: renamed from: -$$Nest$fgetg, reason: not valid java name */
        public static /* bridge */ /* synthetic */ int m2000$$Nest$fgetg(b bVar) {
            return bVar.g;
        }

        public b(N0 n0, Q.b bVar, N0 n1, String str) {
            this.a = n0;
            this.b = bVar;
            this.c = n1;
            this.d = str;
        }

        private synchronized int g() {
            int i2;
            i2 = i;
            i = i2 + 1;
            return i2;
        }

        @Override // com.android.tools.r8.naming.P
        public String a() {
            return this.d;
        }

        public int b(int i2) {
            N0 n0 = this.a;
            if (n0 == null) {
                return i2;
            }
            if (!j && (n0.a > i2 || i2 > n0.b)) {
                throw new AssertionError();
            }
            N0 n1 = this.c;
            if (n1 == null) {
                return i2;
            }
            int i3 = n1.b;
            int i4 = n1.a;
            return i3 == i4 ? i3 : (i4 + i2) - n0.a;
        }

        @Override // com.android.tools.r8.naming.P
        public final boolean c() {
            return AbstractC2734ot.b(this.h, new InterfaceC3205vM() { // from class: com.android.tools.r8.naming.k$b$$ExternalSyntheticLambda0
                @Override // com.android.tools.r8.internal.InterfaceC3205vM
                public final boolean apply(Object obj) {
                    return ((com.android.tools.r8.naming.mappinginformation.e) obj).n();
                }
            });
        }

        public final List e() {
            return Collections.unmodifiableList(this.h);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof b)) {
                return false;
            }
            b bVar = (b) obj;
            return Objects.equals(this.a, bVar.a) && Objects.equals(this.c, bVar.c) && this.b.equals(bVar.b) && this.d.equals(bVar.d) && Objects.equals(this.e, bVar.e) && Objects.equals(this.h, bVar.h);
        }

        public final int f() {
            N0 n0 = this.c;
            if (n0 != null) {
                return n0.b;
            }
            N0 n1 = this.a;
            if (n1 != null) {
                return n1.b;
            }
            return Integer.MAX_VALUE;
        }

        public final AbstractC1639am h() {
            Predicate predicate = new Predicate() { // from class: com.android.tools.r8.naming.k$b$$ExternalSyntheticLambda3
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return ((com.android.tools.r8.naming.mappinginformation.e) obj).l();
                }
            };
            Function function = new Function() { // from class: com.android.tools.r8.naming.k$b$$ExternalSyntheticLambda4
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return ((com.android.tools.r8.naming.mappinginformation.e) obj).c();
                }
            };
            C1507Xl c1507XlG = AbstractC1639am.g();
            for (com.android.tools.r8.naming.mappinginformation.e eVar : this.h) {
                if (predicate.test(eVar)) {
                    c1507XlG.a(function.apply(eVar));
                }
            }
            return c1507XlG.a();
        }

        public final int hashCode() {
            int iHashCode = Objects.hashCode(this.h) + ((Objects.hashCode(this.e) + ((this.d.hashCode() + ((this.b.hashCode() + ((Objects.hashCode(this.c) + (Objects.hashCode(this.a) * 31)) * 31)) * 31)) * 31)) * 31);
            this.f = true;
            return iHashCode;
        }

        @Override // com.android.tools.r8.naming.P
        /* JADX INFO: renamed from: i, reason: merged with bridge method [inline-methods] */
        public final Q.b d() {
            Q.b bVar = this.e;
            if (bVar != null) {
                return bVar;
            }
            Q.b bVar2 = this.b;
            return new Q.b(this.d, bVar2.c, bVar2.d).b();
        }

        public final AbstractC1639am j() {
            Predicate predicate = new Predicate() { // from class: com.android.tools.r8.naming.k$b$$ExternalSyntheticLambda1
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return ((com.android.tools.r8.naming.mappinginformation.e) obj).o();
                }
            };
            Function function = new Function() { // from class: com.android.tools.r8.naming.k$b$$ExternalSyntheticLambda2
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return ((com.android.tools.r8.naming.mappinginformation.e) obj).i();
                }
            };
            C1507Xl c1507XlG = AbstractC1639am.g();
            for (com.android.tools.r8.naming.mappinginformation.e eVar : this.h) {
                if (predicate.test(eVar)) {
                    c1507XlG.a(function.apply(eVar));
                }
            }
            return c1507XlG.a();
        }

        public final boolean k() {
            for (com.android.tools.r8.naming.mappinginformation.e eVar : this.h) {
                if (eVar.k() || (eVar instanceof RK)) {
                    return true;
                }
            }
            return false;
        }

        public final boolean l() {
            for (com.android.tools.r8.naming.mappinginformation.e eVar : this.h) {
                eVar.getClass();
                if (eVar instanceof RK) {
                    return true;
                }
            }
            return false;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder();
            N0 n0 = this.a;
            if (n0 != null) {
                sb.append(n0).append(':');
            }
            sb.append(this.b);
            N0 n1 = this.c;
            if (n1 != null && !n1.equals(this.a)) {
                sb.append(":").append(this.c);
            }
            sb.append(" -> ").append(this.d);
            return sb.toString();
        }

        public final void a(Consumer consumer, com.android.tools.r8.naming.mappinginformation.e eVar) {
            if (this.h == C3634k.h) {
                this.h = new ArrayList();
            }
            com.android.tools.r8.naming.mappinginformation.e.a(this.h, eVar, consumer);
        }

        public final int a(int i2) {
            N0 n0 = this.c;
            if (n0 == null) {
                N0 n1 = this.a;
                return n1 != null ? n1.a : i2;
            }
            return n0.a;
        }

        public final void a(Q.b bVar) {
            if (!j && this.f) {
                throw new AssertionError();
            }
            this.e = bVar;
        }

        public final b a(N0 n0) {
            if (n0.equals(this.a)) {
                return this;
            }
            b bVar = new b(n0, this.b, new N0(b(n0.a), b(n0.b)), this.d);
            if (n0.b >= this.a.b) {
                bVar.h = new ArrayList(this.h);
            }
            return bVar;
        }

        @Override // com.android.tools.r8.naming.P
        public final Q.c b() {
            return this.b;
        }
    }

    /* JADX INFO: renamed from: com.android.tools.r8.naming.k$c */
    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class c {
        private final List a;
        static final /* synthetic */ boolean c = true;
        private static final c b = new c(Collections.emptyList());

        public c(List list) {
            this.a = list;
        }

        public List<b> a(int i) {
            return a(i, true);
        }

        public b b(int i) {
            b bVar = null;
            for (b bVar2 : this.a) {
                N0 n0 = bVar2.a;
                if (n0 != null) {
                    if (n0.a <= i && i <= n0.b) {
                        return bVar2;
                    }
                } else if (bVar == null) {
                    bVar = bVar2;
                }
            }
            return bVar;
        }

        public final List c() {
            if (this.a.size() <= 1) {
                return Collections.singletonList(this);
            }
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            List list = this.a;
            if (!N.a && list.size() <= 0) {
                throw new AssertionError();
            }
            int i = 0;
            while (N.a(i, list)) {
                arrayList2.add((b) list.get(i));
                i++;
            }
            int i2 = i + 1;
            arrayList2.add((b) list.get(i));
            if (((b) V1.b(arrayList2)).b.equals(((b) V1.b(this.a)).b)) {
                return Collections.singletonList(this);
            }
            while (i2 < this.a.size()) {
                ArrayList arrayList3 = new ArrayList();
                List list2 = this.a;
                if (!N.a && i2 >= list2.size()) {
                    throw new AssertionError();
                }
                while (N.a(i2, list2)) {
                    arrayList3.add((b) list2.get(i2));
                    i2++;
                }
                int i3 = i2 + 1;
                arrayList3.add((b) list2.get(i2));
                if (!((b) V1.b(arrayList2)).b.equals(((b) V1.b(arrayList3)).b)) {
                    arrayList.add(new c(arrayList2));
                    arrayList2 = new ArrayList();
                }
                arrayList2.addAll(arrayList3);
                i2 = i3;
            }
            arrayList.add(new c(arrayList2));
            return arrayList;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            return this.a.equals(((c) obj).a);
        }

        public final int hashCode() {
            return this.a.hashCode();
        }

        public final List a(int i, boolean z) {
            b bVar = null;
            for (int i2 = 0; i2 < this.a.size(); i2++) {
                b bVar2 = (b) this.a.get(i2);
                N0 n0 = bVar2.a;
                if (n0 != null) {
                    if (n0.a <= i && i <= n0.b) {
                        int i3 = i2 + 1;
                        while (i3 < this.a.size() && Objects.equals(((b) this.a.get(i3)).a, bVar2.a)) {
                            i3++;
                        }
                        return this.a.subList(i2, i3);
                    }
                } else if (bVar == null && z) {
                    bVar = bVar2;
                }
            }
            return bVar == null ? Collections.emptyList() : Collections.singletonList(bVar);
        }

        public List<b> b() {
            return this.a;
        }

        public static c a() {
            return b;
        }

        public final Q a(C3634k c3634k) {
            Q q = (Q) c3634k.c.get(((b) V1.b(this.a)).d());
            if (c || q != null) {
                return q;
            }
            throw new AssertionError();
        }
    }

    private C3634k(String str, String str2, HashMap map, HashMap map2, Map map3, HashMap map4, List list) {
        this.b = str;
        this.a = str2;
        this.c = AbstractC2178hm.a(map);
        this.d = AbstractC2178hm.a(map2);
        this.e = map3;
        this.f = map4;
        this.g = list;
    }

    static a a(String str, String str2, BiConsumer biConsumer) {
        return new a(str, str2, biConsumer);
    }

    public Q b(Q.c cVar) {
        if (cVar.f() == 1) {
            AbstractC2963s10 it = this.c.values().iterator();
            while (it.hasNext()) {
                Q q = (Q) it.next();
                if (q.b().equals(cVar)) {
                    return q;
                }
            }
            return null;
        }
        if (!i && cVar.f() != 2) {
            throw new AssertionError();
        }
        AbstractC2963s10 it2 = this.d.values().iterator();
        while (it2.hasNext()) {
            Q q2 = (Q) it2.next();
            if (q2.b().equals(cVar)) {
                return q2;
            }
        }
        return null;
    }

    public List<com.android.tools.r8.naming.mappinginformation.e> c() {
        return Collections.unmodifiableList(this.g);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof C3634k)) {
            return false;
        }
        C3634k c3634k = (C3634k) obj;
        return this.a.equals(c3634k.a) && this.b.equals(c3634k.b) && this.c.equals(c3634k.c) && this.d.equals(c3634k.d) && this.e.equals(c3634k.e);
    }

    public int hashCode() {
        return this.e.hashCode() + ((this.d.hashCode() + ((this.c.hashCode() + ((this.b.hashCode() + (this.a.hashCode() * 31)) * 31)) * 31)) * 31);
    }

    public final String toString() {
        final StringBuilder sb = new StringBuilder();
        a(com.android.tools.r8.utils.V.a(new Consumer() { // from class: com.android.tools.r8.naming.k$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                sb.append((String) obj);
            }
        }));
        return sb.toString();
    }

    public c a(String str) {
        return this.e.get(str);
    }

    public final C3634k a(C3634k c3634k) {
        if (this.a.equals(c3634k.a)) {
            if (this.b.equals(c3634k.b)) {
                if (this.c.isEmpty() && this.d.isEmpty()) {
                    return c3634k;
                }
                if (c3634k.c.isEmpty() && c3634k.d.isEmpty()) {
                    return this;
                }
                throw new RuntimeException("R8 Retrace do not support merging of partial class mappings.");
            }
            String str = this.a;
            throw new RuntimeException("Cannot combine mapping for " + str + " because it maps forward to both " + str + " and " + c3634k.a + ".");
        }
        throw new RuntimeException("Cannot combine mapping for " + this.b + " because it maps back to both " + this.a + " and " + c3634k.a + ".");
    }

    /* JADX INFO: renamed from: com.android.tools.r8.naming.k$a */
    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class a extends AbstractC3625g {
        private final String a;
        private final String b;
        private final HashMap c;
        private final HashMap d;
        private final HashMap e;
        private final HashMap f;
        private List g;
        private final BiConsumer h;

        private a(String str, String str2, BiConsumer biConsumer) {
            this.c = RD.a();
            this.d = RD.a();
            this.e = RD.a();
            this.f = RD.a();
            this.g = C3634k.h;
            this.a = str2;
            this.b = str;
            this.h = biConsumer;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ List b(String str) {
            return new ArrayList();
        }

        @Override // com.android.tools.r8.naming.AbstractC3625g
        public final AbstractC3625g a(Q q) {
            if (q.i()) {
                this.c.put(q.d().b(), q);
            } else {
                this.d.put(q.d().a(), q);
                ((List) this.f.computeIfAbsent(q.a(), new Function() { // from class: com.android.tools.r8.naming.k$a$$ExternalSyntheticLambda1
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return C3634k.a.b((String) obj);
                    }
                })).add(q);
            }
            return this;
        }

        @Override // com.android.tools.r8.naming.AbstractC3625g
        public final Q a(Q.c cVar) {
            cVar.getClass();
            if (cVar instanceof Q.a) {
                return (Q) this.d.get(cVar.a());
            }
            return (Q) this.c.get(cVar.b());
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r0v2, types: [java.util.HashMap] */
        /* JADX WARN: Type inference failed for: r0v3 */
        /* JADX WARN: Type inference failed for: r0v5, types: [java.util.Map] */
        /* JADX WARN: Type inference failed for: r10v0, types: [java.util.Map] */
        public final C3634k a() {
            ?? map;
            if (this.e.isEmpty()) {
                map = Collections.emptyMap();
            } else {
                map = new HashMap(this.e.size());
                for (Map.Entry entry : this.e.entrySet()) {
                    map.put((String) entry.getKey(), new c((List) entry.getValue()));
                }
            }
            return new C3634k(this.b, this.a, this.c, this.d, map, this.f, this.g);
        }

        @Override // com.android.tools.r8.naming.AbstractC3625g
        public final b a(N0 n0, Q.b bVar, N0 n1, String str) {
            b bVar2 = new b(n0, bVar, n1, str);
            ((List) this.e.computeIfAbsent(str, new Function() { // from class: com.android.tools.r8.naming.k$a$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return C3634k.a.a((String) obj);
                }
            })).add(bVar2);
            return bVar2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ List a(String str) {
            return new ArrayList();
        }

        @Override // com.android.tools.r8.naming.AbstractC3625g
        public final void a(Consumer consumer, com.android.tools.r8.naming.mappinginformation.e eVar) {
            if (this.g == C3634k.h) {
                this.g = new ArrayList();
            }
            for (com.android.tools.r8.naming.mappinginformation.e eVar2 : this.g) {
                if (!eVar2.a(eVar)) {
                    consumer.accept(eVar2);
                    return;
                }
            }
            this.g.add(eVar);
            eVar.getClass();
            if (eVar instanceof com.android.tools.r8.naming.mappinginformation.a) {
                this.h.accept(this.a, eVar.a().q());
            }
        }
    }

    public List<Q> b(String str) {
        ArrayList arrayList = new ArrayList();
        AbstractC2963s10 it = this.c.values().iterator();
        while (it.hasNext()) {
            Q q = (Q) it.next();
            if (q.b().a.equals(str)) {
                arrayList.add(q);
            }
        }
        AbstractC2963s10 it2 = this.d.values().iterator();
        while (it2.hasNext()) {
            Q q2 = (Q) it2.next();
            if (q2.b().a.equals(str)) {
                arrayList.add(q2);
            }
        }
        return arrayList;
    }

    @Override // com.android.tools.r8.naming.InterfaceC3627h
    public Q a(Q.c cVar) {
        if (cVar.f() == 1) {
            if (!i && !(cVar instanceof Q.b)) {
                throw new AssertionError();
            }
            return (Q) this.c.get(cVar);
        }
        boolean z = i;
        if (!z && cVar.f() != 2) {
            throw new AssertionError();
        }
        if (!z && !(cVar instanceof Q.a)) {
            throw new AssertionError();
        }
        return (Q) this.d.get(cVar);
    }

    public Collection<Q> b() {
        return this.c.values();
    }

    public final void a(InterfaceC3905h3 interfaceC3905h3) throws Throwable {
        AbstractC2963s10 it = this.d.values().iterator();
        while (it.hasNext()) {
            interfaceC3905h3.accept((Q) it.next());
        }
    }

    public Collection<Q> a() {
        return this.d.values();
    }

    public final void a(final Consumer consumer) {
        this.f.values().forEach(new Consumer() { // from class: com.android.tools.r8.naming.k$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C3634k.a(consumer, (List) obj);
            }
        });
        this.e.values().forEach(new Consumer() { // from class: com.android.tools.r8.naming.k$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C3634k.a(consumer, (C3634k.c) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(final Consumer consumer, List list) {
        list.forEach(new Consumer() { // from class: com.android.tools.r8.naming.k$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C3634k.a(consumer, (Q) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Consumer consumer, Q q) {
        if (q.b().e()) {
            consumer.accept(q.b().g());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(final Consumer consumer, c cVar) {
        cVar.a.forEach(new Consumer() { // from class: com.android.tools.r8.naming.k$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C3634k.a(consumer, (C3634k.b) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Consumer consumer, b bVar) {
        if (bVar.b.e()) {
            consumer.accept(bVar.b.g());
        }
    }

    final void a(final com.android.tools.r8.utils.V v) {
        v.a(this.a).a(" -> ").a(this.b).a(":\n");
        this.g.forEach(new Consumer() { // from class: com.android.tools.r8.naming.k$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C3634k.a(v, (com.android.tools.r8.naming.mappinginformation.e) obj);
            }
        });
        InterfaceC3905h3 interfaceC3905h3 = new InterfaceC3905h3() { // from class: com.android.tools.r8.naming.k$$ExternalSyntheticLambda6
            @Override // com.android.tools.r8.utils.InterfaceC3905h3
            public final void accept(Object obj) {
                C3634k.a(v, (Q) obj);
            }
        };
        ArrayList arrayList = new ArrayList(this.d.values());
        Collections.sort(arrayList);
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            interfaceC3905h3.accept((Q) it.next());
        }
        ArrayList<b> arrayList2 = new ArrayList();
        Iterator<c> it2 = this.e.values().iterator();
        while (it2.hasNext()) {
            arrayList2.addAll(it2.next().a);
        }
        arrayList2.sort(Comparator.comparingInt(new ToIntFunction() { // from class: com.android.tools.r8.naming.k$$ExternalSyntheticLambda7
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return C3634k.b.m2000$$Nest$fgetg((C3634k.b) obj);
            }
        }));
        for (b bVar : arrayList2) {
            v.a("    ").a(bVar.toString()).a("\n");
            Iterator it3 = Collections.unmodifiableList(bVar.h).iterator();
            while (it3.hasNext()) {
                v.a("      # ").a(((com.android.tools.r8.naming.mappinginformation.e) it3.next()).p()).a("\n");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(com.android.tools.r8.utils.V v, com.android.tools.r8.naming.mappinginformation.e eVar) {
        v.a("# " + eVar.p()).a("\n");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(com.android.tools.r8.utils.V v, Q q) {
        v.a("    ").a(q.toString()).a("\n");
    }
}
