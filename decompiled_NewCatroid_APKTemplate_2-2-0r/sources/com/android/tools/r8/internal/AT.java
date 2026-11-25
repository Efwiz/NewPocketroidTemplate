package com.android.tools.r8.internal;

import com.android.tools.r8.naming.C3634k;
import com.android.tools.r8.retrace.RetraceMethodResult;
import com.android.tools.r8.retrace.RetraceStackTraceContext;
import com.android.tools.r8.utils.AbstractC3938n2;
import com.android.tools.r8.utils.C3979v2;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.OptionalInt;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class AT implements RetraceMethodResult {
    static final /* synthetic */ boolean e = true;
    private final GE a;
    private final List b;
    private final YT c;
    private AbstractC3938n2 d = AbstractC3938n2.c;

    AT(C2992sT c2992sT, List list, GE ge, YT yt) {
        this.b = list;
        this.a = ge;
        this.c = yt;
        boolean z = e;
        if (!z && c2992sT == null) {
            throw new AssertionError();
        }
        if (!z && list.isEmpty()) {
            throw new AssertionError();
        }
    }

    private static Function b() {
        return new Function() { // from class: com.android.tools.r8.internal.AT$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((C1676bE) obj).b();
            }
        };
    }

    @Override // com.android.tools.r8.retrace.RetraceMethodResult
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public final C3437yT narrowByPosition(RetraceStackTraceContext retraceStackTraceContext, OptionalInt optionalInt) {
        ArrayList arrayList = new ArrayList();
        CT ct = retraceStackTraceContext instanceof CT ? (CT) retraceStackTraceContext : null;
        boolean z = true;
        boolean z2 = optionalInt.isPresent() && optionalInt.getAsInt() > 0;
        Function functionA = z2 ? a(optionalInt.getAsInt()) : b();
        Iterator it = this.b.iterator();
        while (it.hasNext()) {
            a((C3979v2) it.next(), functionA, optionalInt, ct, arrayList);
        }
        if (z2 && arrayList.isEmpty()) {
            Iterator it2 = this.b.iterator();
            while (it2.hasNext()) {
                a((C3979v2) it2.next(), a(), optionalInt, ct, arrayList);
            }
        }
        if (arrayList.isEmpty()) {
            if (!AT$$ExternalSyntheticBackport0.m(optionalInt) && optionalInt.getAsInt() > 0) {
                z = false;
            }
            for (C3979v2 c3979v2 : this.b) {
                List arrayList2 = new ArrayList();
                if (c3979v2.b() != null && z) {
                    arrayList2 = com.android.tools.r8.utils.V1.a((Collection) c3979v2.b(), new Function() { // from class: com.android.tools.r8.internal.AT$$ExternalSyntheticLambda4
                        @Override // java.util.function.Function
                        public final Object apply(Object obj) {
                            return AT.a((C1676bE) obj);
                        }
                    });
                }
                arrayList.add(new C3212vT((C2923rT) c3979v2.a(), arrayList2, optionalInt));
            }
        }
        return new C3437yT(arrayList, this.a, this.c, (CT) retraceStackTraceContext);
    }

    @Override // com.android.tools.r8.retrace.RetraceResult
    public final boolean isAmbiguous() {
        if (!this.d.e()) {
            return this.d.d();
        }
        if (this.b.size() > 1) {
            this.d = AbstractC3938n2.a;
            return true;
        }
        List list = (List) ((C3979v2) this.b.get(0)).b();
        if (list == null || list.size() < 2) {
            this.d = AbstractC3938n2.b;
            return false;
        }
        com.android.tools.r8.naming.Q.b bVarB = ((C3634k.b) com.android.tools.r8.utils.V1.b(((C1676bE) list.get(0)).a())).b.b();
        for (int i = 1; i < list.size(); i++) {
            if (!bVarB.equals(((C3634k.b) com.android.tools.r8.utils.V1.b(((C1676bE) list.get(i)).a())).b.b())) {
                this.d = AbstractC3938n2.a;
                return true;
            }
        }
        this.d = AbstractC3938n2.b;
        return false;
    }

    @Override // com.android.tools.r8.retrace.RetraceResult
    public final boolean isEmpty() {
        List list = (List) ((C3979v2) this.b.get(0)).b();
        return list == null || list.isEmpty();
    }

    @Override // com.android.tools.r8.retrace.RetraceResult
    public final Stream stream() {
        return this.b.stream().flatMap(new Function() { // from class: com.android.tools.r8.internal.AT$$ExternalSyntheticLambda6
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.a((C3979v2) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ C1676bE a(C1676bE c1676bE) {
        return c1676bE.d() ? c1676bE : new C1676bE(c1676bE.c(), C3634k.c.a());
    }

    private static void a(C3979v2 c3979v2, Function function, OptionalInt optionalInt, CT ct, ArrayList arrayList) {
        List<C1676bE> list = (List) c3979v2.b();
        if (list == null) {
            arrayList.add(new C3212vT((C2923rT) c3979v2.a(), null, optionalInt));
            return;
        }
        ArrayList arrayList2 = new ArrayList();
        for (C1676bE c1676bE : list) {
            List list2 = (List) function.apply(c1676bE);
            if (list2 != null && !list2.isEmpty()) {
                if (ct != null && ct.e()) {
                    AbstractC1639am abstractC1639amH = ((C3634k.b) com.android.tools.r8.utils.V1.b(list2)).h();
                    if (!abstractC1639amH.isEmpty()) {
                        if (!e && abstractC1639amH.size() != 1) {
                            throw new AssertionError("There can only be one outline entry for a line");
                        }
                        int iA = ((PK) abstractC1639amH.get(0)).a(ct.c());
                        a(c3979v2, a(iA), OptionalInt.of(iA), ct.a().b().a(), arrayList);
                        return;
                    }
                }
                arrayList2.add(new C1676bE(c1676bE.c(), new C3634k.c(list2)));
            }
        }
        if (arrayList2.isEmpty()) {
            return;
        }
        arrayList.add(new C3212vT((C2923rT) c3979v2.a(), arrayList2, optionalInt));
    }

    private static Function a(final int i) {
        return new Function() { // from class: com.android.tools.r8.internal.AT$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return AT.a(i, (C1676bE) obj);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ List a(int i, C1676bE c1676bE) {
        return c1676bE.a(i);
    }

    private static Function a() {
        return new Function() { // from class: com.android.tools.r8.internal.AT$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((C1676bE) obj).e();
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Stream a(C3979v2 c3979v2) {
        final C2923rT c2923rT = (C2923rT) c3979v2.a();
        List list = (List) c3979v2.b();
        if (list != null && !list.isEmpty()) {
            final HashSet hashSet = new HashSet();
            final ArrayList arrayList = new ArrayList(list.size());
            list.forEach(new Consumer() { // from class: com.android.tools.r8.internal.AT$$ExternalSyntheticLambda5
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.a(hashSet, c2923rT, arrayList, (C1676bE) obj);
                }
            });
            return arrayList.stream();
        }
        return Stream.of(new C3507zT(this, c2923rT, UT.a(this.a.a(c2923rT.a().getClassReference())), null));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Set set, C2923rT c2923rT, List list, C1676bE c1676bE) {
        com.android.tools.r8.naming.Q.b bVarB;
        com.android.tools.r8.naming.Q qC = c1676bE.c();
        if (qC != null && !isAmbiguous()) {
            bVarB = qC.b().b();
        } else {
            bVarB = ((C3634k.b) com.android.tools.r8.utils.V1.b(c1676bE.a())).b.b();
        }
        if (set.add(bVarB)) {
            list.add(new C3507zT(this, c2923rT, UT.a(NT.a(bVarB, c2923rT.a().getClassReference())), c1676bE));
        }
    }
}
