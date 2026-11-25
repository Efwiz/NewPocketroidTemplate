package com.android.tools.r8.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class B3 implements G3 {
    static final /* synthetic */ boolean b = true;
    final /* synthetic */ C3 a;

    private B3(C3 c3) {
        this.a = c3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public C0706m3.i b(C0706m3.i iVar) {
        iVar.getClass();
        if (!(iVar instanceof C0712n3)) {
            iVar = c(iVar.d());
        }
        return iVar == null ? this.a.e : iVar;
    }

    @Override // com.android.tools.r8.graph.G3
    public final C0706m3.i a(C0706m3.i iVar) {
        iVar.getClass();
        return iVar instanceof C0712n3 ? iVar : c(iVar.d());
    }

    public final C0706m3.e c(C0706m3.e eVar) {
        if (eVar.n() || eVar.o()) {
            return eVar;
        }
        if (eVar.l()) {
            return eVar.g().a(this);
        }
        if (b || eVar.m()) {
            return eVar.h().a(this);
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.graph.G3
    public final List d(List list) {
        return list.isEmpty() ? list : com.android.tools.r8.utils.V1.a(list, new Function() { // from class: com.android.tools.r8.graph.B3$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.b((C0706m3.i) obj);
            }
        }, list);
    }

    @Override // com.android.tools.r8.graph.G3
    public final List e(List list) {
        if (list.isEmpty()) {
            return list;
        }
        final List listA = com.android.tools.r8.utils.V1.a(list, new Function() { // from class: com.android.tools.r8.graph.B3$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.b((C0706m3.c) obj);
            }
        }, list);
        final ArrayList arrayList = new ArrayList(listA.size());
        this.a.d.g.forEach(new Consumer() { // from class: com.android.tools.r8.graph.B3$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                B3.a(listA, arrayList, (C0767v2) obj);
            }
        });
        return arrayList;
    }

    public final C0706m3.f a(C0706m3.f fVar) {
        C0706m3.f fVarA = fVar.a(this);
        C0706m3.e eVar = fVarA.b;
        return ((eVar == null || eVar.a()) && fVarA.c.isEmpty()) ? new C0706m3.f(fVar.a, this.a.e, fVarA.c) : fVarA;
    }

    public final C0706m3.c b(C0706m3.c cVar) {
        C0706m3.c cVarA = cVar.a(this);
        if (cVarA == null || cVarA.b == this.a.d.d) {
            return null;
        }
        return cVarA;
    }

    @Override // com.android.tools.r8.graph.G3
    public final List b(List list) {
        return list.isEmpty() ? list : com.android.tools.r8.utils.V1.a(list, new Function() { // from class: com.android.tools.r8.graph.B3$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.a((C0706m3.i) obj);
            }
        }, list);
    }

    @Override // com.android.tools.r8.graph.G3
    public final List c(List list) {
        return list.isEmpty() ? list : com.android.tools.r8.utils.V1.a(list, new Function() { // from class: com.android.tools.r8.graph.B3$$ExternalSyntheticLambda6
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.a((C0706m3.f) obj);
            }
        }, list);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ C0706m3.e b(C0706m3.e eVar) {
        C0706m3.e eVarC = c(eVar);
        return eVarC == null ? this.a.e.a(C0706m3.k.b) : eVarC;
    }

    @Override // com.android.tools.r8.graph.G3
    public final C0706m3.c a(C0706m3.c cVar) {
        C3 c3 = this.a;
        C0767v2 c0767v2 = c3.d.f;
        C0767v2 c0767v3 = c3.a.Z1;
        if (c0767v2 == c0767v3) {
            return cVar.b == c0767v3 ? cVar : c3.e;
        }
        C0706m3.c cVarA = cVar.a(this);
        return cVarA == null ? this.a.e : cVarA;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(List list, List list2, final C0767v2 c0767v2) {
        int iA = com.android.tools.r8.utils.V1.a(list, new Predicate() { // from class: com.android.tools.r8.graph.B3$$ExternalSyntheticLambda7
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return B3.a(c0767v2, (C0706m3.c) obj);
            }
        });
        C0706m3.c cVar = (C0706m3.c) (iA >= 0 ? list.get(iA) : null);
        if (cVar == null) {
            cVar = new C0706m3.c(c0767v2);
        }
        list2.add(cVar);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(C0767v2 c0767v2, C0706m3.c cVar) {
        return cVar.b == c0767v2;
    }

    @Override // com.android.tools.r8.graph.G3
    public final C0706m3.h a(C0706m3.h hVar) {
        if (hVar.a()) {
            return C0706m3.h.b;
        }
        C0706m3.i iVarB = hVar.b();
        C0706m3.i iVarA = a(iVarB);
        if (iVarA == null) {
            return C0706m3.h.b;
        }
        return iVarA == iVarB ? hVar : new C0706m3.h(iVarA);
    }

    @Override // com.android.tools.r8.graph.G3
    public final C0706m3.e a(C0706m3.e eVar) {
        return eVar.a() ? eVar : c(eVar);
    }

    @Override // com.android.tools.r8.graph.G3
    public final List a(List list) {
        return list.isEmpty() ? list : com.android.tools.r8.utils.V1.a(list, new Function() { // from class: com.android.tools.r8.graph.B3$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.c((C0706m3.e) obj);
            }
        }, list);
    }

    @Override // com.android.tools.r8.graph.G3
    public final C0706m3.c a(C0706m3.c cVar, C0706m3.c cVar2) {
        return cVar.a(this);
    }

    @Override // com.android.tools.r8.graph.G3
    public final List a(C0767v2 c0767v2, C0767v2 c0767v3, List list) {
        if (list.isEmpty()) {
            return list;
        }
        if (!this.a.b.test(c0767v2) && this.a.f.test(c0767v3)) {
            return com.android.tools.r8.utils.V1.a(list, new Function() { // from class: com.android.tools.r8.graph.B3$$ExternalSyntheticLambda1
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return this.f$0.b((C0706m3.e) obj);
                }
            }, list);
        }
        return C0706m3.a();
    }

    @Override // com.android.tools.r8.graph.G3
    public final C0767v2 a(C0767v2 c0767v2) {
        C0767v2 c0767v3 = (C0767v2) this.a.c.apply(c0767v2);
        if (this.a.b.test(c0767v3)) {
            return null;
        }
        return c0767v3;
    }
}
