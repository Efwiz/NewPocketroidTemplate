package com.android.tools.r8.graph;

import com.android.tools.r8.DataResourceProvider;
import com.android.tools.r8.FeatureSplit;
import com.android.tools.r8.ProgramResourceProvider;
import com.android.tools.r8.ResourceException;
import com.android.tools.r8.internal.AbstractC1476Wj;
import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.internal.AbstractC2178hm;
import com.android.tools.r8.internal.AbstractC2963s10;
import com.android.tools.r8.internal.C1121Ka;
import com.android.tools.r8.internal.C1367Tb;
import com.android.tools.r8.internal.C1507Xl;
import com.android.tools.r8.internal.C1946em;
import com.android.tools.r8.internal.C3451yh;
import com.android.tools.r8.internal.OU;
import com.android.tools.r8.shaking.C3733i;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.graph.l, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C0695l {
    static final /* synthetic */ boolean d = true;
    private final C0702m a;
    private final AbstractC1476Wj b;
    private final Map c;

    /* JADX INFO: renamed from: com.android.tools.r8.graph.l$a */
    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class a {
        private final C0702m a;
        private final com.android.tools.r8.utils.A1 b;
        private final LinkedHashMap c;

        private a(C0702m c0702m) {
            this.c = new LinkedHashMap();
            this.a = c0702m;
            this.b = c0702m.Y();
        }

        public C0695l a() {
            AbstractC2963s10 it = this.a.f().b().a.iterator();
            while (it.hasNext()) {
                try {
                    ((DataResourceProvider) it.next()).accept(new C0688k(this, FeatureSplit.BASE));
                } catch (ResourceException e) {
                    throw new C1367Tb(e.getMessage(), e);
                }
            }
            C3451yh c3451yh = this.b.k;
            if (c3451yh != null) {
                for (FeatureSplit featureSplit : c3451yh.b()) {
                    Iterator<ProgramResourceProvider> it2 = featureSplit.getProgramResourceProviders().iterator();
                    while (it2.hasNext()) {
                        DataResourceProvider dataResourceProvider = it2.next().getDataResourceProvider();
                        if (dataResourceProvider != null) {
                            try {
                                dataResourceProvider.accept(new C0688k(this, featureSplit));
                            } catch (ResourceException e2) {
                                throw new C1367Tb(e2.getMessage(), e2);
                            }
                        }
                    }
                }
            }
            return new C0695l(this.a, this.c);
        }
    }

    private C0695l(C0702m c0702m, Map map) {
        this.a = c0702m;
        this.b = c0702m.E();
        this.c = map;
    }

    public final Set a() {
        if (!d) {
            d();
        }
        return this.c.keySet();
    }

    public final Set b() {
        if (!d) {
            d();
        }
        final Set setC = OU.c();
        this.c.forEach(new BiConsumer() { // from class: com.android.tools.r8.graph.l$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C0695l.a(setC, (C0767v2) obj, (Map) obj2);
            }
        });
        return setC;
    }

    public final boolean c() {
        return this.c.isEmpty();
    }

    public final void d() {
        for (Map.Entry entry : this.c.entrySet()) {
            if (!d) {
                if (entry.getKey() != this.a.E().b(this.b, (C0767v2) entry.getKey())) {
                    throw new AssertionError();
                }
            }
            Iterator it = ((Map) entry.getValue()).entrySet().iterator();
            while (it.hasNext()) {
                for (C0767v2 c0767v2 : (List) ((Map.Entry) it.next()).getValue()) {
                    if (!d && c0767v2 != this.a.E().b(this.b, c0767v2)) {
                        throw new AssertionError();
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(final Set set, C0767v2 c0767v2, Map map) {
        map.forEach(new BiConsumer() { // from class: com.android.tools.r8.graph.l$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                set.addAll((List) obj2);
            }
        });
    }

    public final AbstractC1639am a(C0767v2 c0767v2) {
        boolean z = d;
        if (!z) {
            d();
        }
        Map map = (Map) this.c.get(c0767v2);
        if (map == null) {
            if (z) {
                return AbstractC1639am.i();
            }
            throw new AssertionError("Unexpected attempt to get service implementations for non-service type `" + c0767v2.r0() + "`");
        }
        C1507Xl c1507XlG = AbstractC1639am.g();
        Iterator it = map.values().iterator();
        while (it.hasNext()) {
            c1507XlG.a((Iterable) it.next());
        }
        return c1507XlG.a();
    }

    public final boolean a(C0702m c0702m, C0767v2 c0767v2) {
        if (((C3733i) c0702m.f()).p().b()) {
            return false;
        }
        Map map = (Map) this.c.get(c0767v2);
        if (map != null && !map.isEmpty()) {
            if (map.keySet().stream().anyMatch(new Predicate() { // from class: com.android.tools.r8.graph.l$$ExternalSyntheticLambda4
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return C0695l.a((FeatureSplit) obj);
                }
            })) {
                return true;
            }
            if (!d && map.size() > 2) {
                throw new AssertionError();
            }
            C0729q2 c0729q2B = c0702m.b(c0767v2);
            if (c0729q2B != null && C1121Ka.a(c0702m, c0729q2B)) {
                return true;
            }
            for (Map.Entry entry : map.entrySet()) {
                FeatureSplit featureSplit = (FeatureSplit) entry.getKey();
                if (!d && !featureSplit.isBase()) {
                    throw new AssertionError();
                }
                Iterator it = ((List) entry.getValue()).iterator();
                while (it.hasNext()) {
                    C0729q2 c0729q2B2 = c0702m.b((C0767v2) it.next());
                    if (c0729q2B2 != null && C1121Ka.a(c0702m, c0729q2B2)) {
                        return true;
                    }
                }
            }
            return false;
        }
        if (d) {
            return true;
        }
        throw new AssertionError("Unexpected attempt to get service implementations for non-service type `" + c0767v2.r0() + "`");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(FeatureSplit featureSplit) {
        return !featureSplit.isBase();
    }

    public final C0695l a(AbstractC1476Wj abstractC1476Wj) {
        C1946em c1946emE = AbstractC2178hm.e();
        for (Map.Entry entry : this.c.entrySet()) {
            C0767v2 c0767v2B = abstractC1476Wj.b(this.b, (C0767v2) entry.getKey());
            C1946em c1946emE2 = AbstractC2178hm.e();
            for (Map.Entry entry2 : ((Map) entry.getValue()).entrySet()) {
                C1507Xl c1507XlG = AbstractC1639am.g();
                Iterator it = ((List) entry2.getValue()).iterator();
                while (it.hasNext()) {
                    c1507XlG.a(abstractC1476Wj.b(this.b, (C0767v2) it.next()));
                }
                c1946emE2.a((FeatureSplit) entry2.getKey(), c1507XlG.a());
            }
            c1946emE.a(c0767v2B, c1946emE2.a());
        }
        return new C0695l(this.a, c1946emE.a());
    }

    public final C0695l a(u5 u5Var) {
        C1946em c1946emE = AbstractC2178hm.e();
        for (Map.Entry entry : this.c.entrySet()) {
            if (!u5Var.e().contains(entry.getKey())) {
                C1946em c1946emE2 = AbstractC2178hm.e();
                for (Map.Entry entry2 : ((Map) entry.getValue()).entrySet()) {
                    C1507Xl c1507XlG = AbstractC1639am.g();
                    for (C0767v2 c0767v2 : (List) entry2.getValue()) {
                        if (!u5Var.e().contains(c0767v2)) {
                            c1507XlG.a(c0767v2);
                        }
                    }
                    if (c1507XlG.a().size() > 0) {
                        c1946emE2.a((FeatureSplit) entry2.getKey(), c1507XlG.a());
                    }
                }
                AbstractC2178hm abstractC2178hmA = c1946emE2.a();
                if (abstractC2178hmA.size() > 0) {
                    c1946emE.a((C0767v2) entry.getKey(), abstractC2178hmA);
                }
            }
        }
        return new C0695l(this.a, c1946emE.a());
    }

    public final void a(final BiConsumer biConsumer) {
        this.c.forEach(new BiConsumer() { // from class: com.android.tools.r8.graph.l$$ExternalSyntheticLambda2
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C0695l.a(biConsumer, (C0767v2) obj, (Map) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(BiConsumer biConsumer, C0767v2 c0767v2, Map map) {
        final C1507Xl c1507XlG = AbstractC1639am.g();
        map.values().forEach(new Consumer() { // from class: com.android.tools.r8.graph.l$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                c1507XlG.a((Iterable) obj);
            }
        });
        biConsumer.accept(c0767v2, c1507XlG.a());
    }

    public static a a(C0702m<?> c0702m) {
        return new a(c0702m);
    }
}
