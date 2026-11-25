package com.android.tools.r8.graph;

import com.android.tools.r8.internal.AbstractC1476Wj;
import com.android.tools.r8.internal.AbstractC2964s2;
import java.util.Collection;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.graph.z3, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C0817z3 implements G3 {
    static final /* synthetic */ boolean g = true;
    private final C0738r3.a a;
    private final BiPredicate b;
    private final Predicate c;
    private final C0702m d;
    private final C0706m3.c e;
    private boolean f = false;

    private C0817z3(C0702m c0702m, C0738r3.a aVar, BiPredicate biPredicate, Predicate predicate) {
        this.d = c0702m;
        this.a = aVar;
        this.b = biPredicate;
        this.c = predicate;
        this.e = new C0706m3.c(c0702m.a().Z1).a(C0706m3.k.b);
    }

    @Override // com.android.tools.r8.graph.G3
    public final C0767v2 a(C0767v2 c0767v2) {
        return c0767v2;
    }

    public final C0706m3.c b(C0706m3.c cVar) {
        return cVar.a(this);
    }

    @Override // com.android.tools.r8.graph.G3
    public final List c(List list) {
        return list.isEmpty() ? list : com.android.tools.r8.utils.V1.a(list, new Function() { // from class: com.android.tools.r8.graph.z3$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.a((C0706m3.f) obj);
            }
        }, list);
    }

    @Override // com.android.tools.r8.graph.G3
    public final List d(List list) {
        return list.isEmpty() ? list : com.android.tools.r8.utils.V1.a(list, (Function) new C0818z3$$ExternalSyntheticLambda0(this), list);
    }

    @Override // com.android.tools.r8.graph.G3
    public final List e(List list) {
        return list.isEmpty() ? list : com.android.tools.r8.utils.V1.a(list, new Function() { // from class: com.android.tools.r8.graph.z3$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.b((C0706m3.c) obj);
            }
        }, list);
    }

    public final C0817z3 f(List list) {
        return list.isEmpty() ? this : new C0817z3(this.d, this.a.a(com.android.tools.r8.utils.V1.a((Collection) list, (Function) new C0761u3$$ExternalSyntheticLambda0())), this.b, this.c);
    }

    public static C0817z3 a(C0702m<?> c0702m, C0738r3.a aVar, BiPredicate<C0767v2, C0767v2> biPredicate, Predicate<C0767v2> predicate) {
        return new C0817z3(c0702m, aVar, biPredicate, predicate);
    }

    @Override // com.android.tools.r8.graph.G3
    public final List b(List list) {
        return list.isEmpty() ? list : com.android.tools.r8.utils.V1.a(list, (Function) new C0818z3$$ExternalSyntheticLambda0(this), list);
    }

    public C0706m3.g a(C0706m3.g gVar) {
        return !gVar.a() ? gVar.a(this) : gVar;
    }

    @Override // com.android.tools.r8.graph.G3
    public final C0706m3.i a(C0706m3.i iVar) {
        iVar.getClass();
        return iVar instanceof C0712n3 ? iVar : b(iVar.d());
    }

    public final C0706m3.e b(C0706m3.e eVar) {
        if (eVar.a() || eVar.n()) {
            return eVar;
        }
        if (eVar.m()) {
            return eVar.h().a(this);
        }
        if (eVar.l()) {
            return eVar.g().a(this);
        }
        if (!g && !eVar.o()) {
            throw new AssertionError();
        }
        if (this.f) {
            return this.e;
        }
        String str = eVar.i().b;
        if (this.a.b(str)) {
            return eVar;
        }
        C0706m3.e eVarA = this.a.a(str);
        if (eVarA == null) {
            return this.e;
        }
        this.f = true;
        C0706m3.e eVarB = b(eVarA);
        this.f = false;
        return eVarB.k() ? eVarB : eVarB.a(C0706m3.k.b);
    }

    public final C0706m3.f a(C0706m3.f fVar) {
        C0706m3.e eVar;
        C0706m3.f fVarA = fVar.a(this);
        if (g || (((eVar = fVarA.b) != null && eVar.b()) || !fVarA.c.isEmpty())) {
            return fVarA;
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.graph.G3
    public final List a(List list) {
        return list.isEmpty() ? list : com.android.tools.r8.utils.V1.a(list, (Function) new z3$$ExternalSyntheticLambda1(this), list);
    }

    @Override // com.android.tools.r8.graph.G3
    public final List a(C0767v2 c0767v2, C0767v2 c0767v3, List list) {
        if (!g && c0767v2 != c0767v3) {
            throw new AssertionError();
        }
        if (list.isEmpty()) {
            return list;
        }
        Predicate predicate = this.c;
        AbstractC1476Wj abstractC1476WjE = this.d.E();
        abstractC1476WjE.getClass();
        if (!predicate.test(abstractC1476WjE.a(AbstractC1476Wj.e(), c0767v2))) {
            return C0706m3.a();
        }
        return com.android.tools.r8.utils.V1.a(list, (Function) new z3$$ExternalSyntheticLambda1(this), list);
    }

    @Override // com.android.tools.r8.graph.G3
    public final C0706m3.e a(C0706m3.e eVar) {
        return eVar.a() ? eVar : b(eVar);
    }

    @Override // com.android.tools.r8.graph.G3
    public final C0706m3.c a(C0706m3.c cVar, C0706m3.c cVar2) {
        if (this.b.test(AbstractC2964s2.a(this.d.E(), cVar.b), AbstractC2964s2.a(this.d.E(), cVar2.b))) {
            return null;
        }
        return cVar.a(this);
    }

    @Override // com.android.tools.r8.graph.G3
    public final C0706m3.h a(C0706m3.h hVar) {
        C0706m3.i iVar;
        C0706m3.i iVarA;
        return (hVar.a() || iVar == (iVarA = a((iVar = hVar.a)))) ? hVar : new C0706m3.h(iVarA);
    }

    @Override // com.android.tools.r8.graph.G3
    public final C0706m3.c a(C0706m3.c cVar) {
        return cVar.a(this);
    }
}
