package com.android.tools.r8.internal;

import com.android.tools.r8.naming.C3634k;
import com.android.tools.r8.references.ClassReference;
import com.android.tools.r8.references.MethodReference;
import com.android.tools.r8.retrace.RetraceFrameResult;
import com.android.tools.r8.utils.AbstractC3938n2;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

/* JADX INFO: renamed from: com.android.tools.r8.internal.yT, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3437yT implements RetraceFrameResult {
    static final /* synthetic */ boolean f = true;
    private final GE a;
    private final List b;
    private final YT c;
    private final CT d;
    private AbstractC3938n2 e = AbstractC3938n2.c;

    public C3437yT(ArrayList arrayList, GE ge, YT yt, CT ct) {
        this.a = ge;
        this.b = arrayList;
        this.c = yt;
        this.d = ct;
        if (!f && arrayList.isEmpty()) {
            throw new AssertionError();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Stream a(C3212vT c3212vT) {
        C2923rT c2923rTC = c3212vT.c();
        List<C1676bE> listA = c3212vT.a();
        OptionalInt optionalIntB = c3212vT.b();
        if (listA == null || listA.isEmpty()) {
            return Stream.of(new C3290wT(this, c2923rTC, UT.a(this.a.a(c2923rTC.a().getClassReference())), AbstractC1639am.i(), Optional.empty(), optionalIntB, this.c));
        }
        ArrayList arrayList = new ArrayList();
        for (C1676bE c1676bE : listA) {
            List listA2 = c1676bE.a();
            com.android.tools.r8.naming.Q qC = c1676bE.c();
            if (!listA2.isEmpty()) {
                C3634k.b bVar = (C3634k.b) listA2.get(0);
                com.android.tools.r8.naming.N0 n0 = bVar.a;
                ArrayList arrayListA = AbstractC2763pC.a(bVar);
                for (int i = 1; i < listA2.size(); i++) {
                    C3634k.b bVar2 = (C3634k.b) listA2.get(i);
                    if (n0 == null || !n0.equals(bVar2.a)) {
                        a(arrayListA, Optional.ofNullable(qC), c2923rTC, optionalIntB, arrayList);
                        arrayListA = new ArrayList();
                        n0 = bVar2.a;
                    }
                    arrayListA.add(bVar2);
                }
                a(arrayListA, Optional.ofNullable(qC), c2923rTC, optionalIntB, arrayList);
            } else {
                if (!f && qC == null) {
                    throw new AssertionError();
                }
                List listSingletonList = Collections.singletonList(C3359xT.a(new C3634k.b(null, qC.b().b(), null, qC.a())));
                Optional optionalOf = Optional.of(qC);
                C3359xT c3359xT = (C3359xT) listSingletonList.get(0);
                C3634k.b bVar3 = c3359xT.a;
                ClassReference classReference = c2923rTC.a().getClassReference();
                boolean z = NT.b;
                arrayList.add(new C3290wT(this, c2923rTC, a(NT.a(bVar3.b, classReference), c3359xT, optionalIntB), listSingletonList, optionalOf, optionalIntB, this.c));
            }
        }
        return arrayList.stream();
    }

    @Override // com.android.tools.r8.retrace.RetraceResult
    public final boolean isAmbiguous() {
        if (this.e.e()) {
            AbstractC3938n2 abstractC3938n2A = AbstractC3938n2.a(this.b.size() <= 1 ? ((C3212vT) this.b.get(0)).d() : true);
            this.e = abstractC3938n2A;
            if (!f && abstractC3938n2A.e()) {
                throw new AssertionError();
            }
        }
        return this.e.d();
    }

    @Override // com.android.tools.r8.retrace.RetraceResult
    public final boolean isEmpty() {
        List listA = ((C3212vT) this.b.get(0)).a();
        return listA == null || listA.isEmpty();
    }

    @Override // com.android.tools.r8.retrace.RetraceResult
    public final Stream stream() {
        return this.b.stream().flatMap(new Function() { // from class: com.android.tools.r8.internal.yT$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.a((C3212vT) obj);
            }
        });
    }

    private void a(ArrayList arrayList, final Optional optional, final C2923rT c2923rT, final OptionalInt optionalInt, final ArrayList arrayList2) {
        C3634k.b bVar;
        com.android.tools.r8.naming.N0 n0;
        com.android.tools.r8.naming.N0 n1;
        if (isAmbiguous() && (n0 = (bVar = (C3634k.b) arrayList.get(0)).c) != null && n0.c() != 1 && ((n1 = bVar.a) == null || n1.c() != bVar.c.c())) {
            boolean z = f;
            if (!z && arrayList.size() <= 0) {
                throw new AssertionError();
            }
            if (!z && (((C3634k.b) arrayList.get(0)).c == null || ((C3634k.b) arrayList.get(0)).c.b <= ((C3634k.b) arrayList.get(0)).c.a)) {
                throw new AssertionError();
            }
            final ArrayList arrayList3 = new ArrayList();
            com.android.tools.r8.utils.V1.a(arrayList, new com.android.tools.r8.utils.U1() { // from class: com.android.tools.r8.internal.yT$$ExternalSyntheticLambda3
                @Override // com.android.tools.r8.utils.U1
                public final void accept(Object obj, int i) {
                    C3437yT.a(arrayList3, (C3634k.b) obj, i);
                }
            });
            arrayList3.forEach(new Consumer() { // from class: com.android.tools.r8.internal.yT$$ExternalSyntheticLambda4
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.a(arrayList2, optional, c2923rT, optionalInt, (List) obj);
                }
            });
            return;
        }
        List listA = com.android.tools.r8.utils.V1.a((Collection) arrayList, new Function() { // from class: com.android.tools.r8.internal.yT$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C3359xT.m1781$$Nest$sma((C3634k.b) obj);
            }
        });
        C3359xT c3359xT = (C3359xT) listA.get(0);
        C3634k.b bVar2 = c3359xT.a;
        ClassReference classReference = c2923rT.a().getClassReference();
        boolean z2 = NT.b;
        arrayList2.add(new C3290wT(this, c2923rT, a(NT.a(bVar2.b, classReference), c3359xT, optionalInt), listA, optional, optionalInt, this.c));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(List list, final C3634k.b bVar, int i) {
        if (i == 0) {
            for (int i2 = bVar.c.a; i2 <= bVar.c.b; i2++) {
                ArrayList arrayList = new ArrayList();
                arrayList.add(new C3359xT(bVar, OptionalInt.of(i2)));
                list.add(arrayList);
            }
            return;
        }
        list.forEach(new Consumer() { // from class: com.android.tools.r8.internal.yT$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C3437yT.a(bVar, (List) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C3634k.b bVar, List list) {
        list.add(C3359xT.a(bVar));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(List list, Optional optional, C2923rT c2923rT, OptionalInt optionalInt, List list2) {
        C3359xT c3359xT = (C3359xT) list2.get(0);
        C3634k.b bVar = c3359xT.a;
        ClassReference classReference = c2923rT.a().getClassReference();
        boolean z = NT.b;
        list.add(new C3290wT(this, c2923rT, a(NT.a(bVar.b, classReference), c3359xT, optionalInt), list2, optional, optionalInt, this.c));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public ST a(MethodReference methodReference, C3359xT c3359xT, final OptionalInt optionalInt) {
        com.android.tools.r8.naming.N0 n0;
        int asInt;
        final C3634k.b bVar = c3359xT.a;
        OptionalInt optionalInt2 = c3359xT.b;
        if (!isAmbiguous() && (bVar.a == null || optionalInt.orElse(-1) == -1)) {
            Supplier supplier = new Supplier() { // from class: com.android.tools.r8.internal.yT$$ExternalSyntheticLambda1
                @Override // java.util.function.Supplier
                public final Object get() {
                    return C3437yT.a(bVar);
                }
            };
            if (!optionalInt2.isPresent()) {
                optionalInt2 = (OptionalInt) supplier.get();
            }
            return UT.a(methodReference, optionalInt2);
        }
        if (!AT$$ExternalSyntheticBackport0.m(optionalInt) && (n0 = bVar.a) != null && n0.a <= (asInt = optionalInt.getAsInt()) && asInt <= n0.b) {
            Supplier supplier2 = new Supplier() { // from class: com.android.tools.r8.internal.yT$$ExternalSyntheticLambda2
                @Override // java.util.function.Supplier
                public final Object get() {
                    return C3437yT.a(bVar, optionalInt);
                }
            };
            if (!optionalInt2.isPresent()) {
                optionalInt2 = (OptionalInt) supplier2.get();
            }
            return UT.a(methodReference, optionalInt2);
        }
        return UT.a(methodReference, optionalInt2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ OptionalInt a(C3634k.b bVar) {
        int iA = bVar.a(0);
        if (iA > 0) {
            return OptionalInt.of(iA);
        }
        return OptionalInt.empty();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ OptionalInt a(C3634k.b bVar, OptionalInt optionalInt) {
        return OptionalInt.of(bVar.b(optionalInt.getAsInt()));
    }
}
