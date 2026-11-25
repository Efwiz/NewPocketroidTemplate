package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.eV, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1928eV extends AbstractC1617aV {
    static final /* synthetic */ boolean c = true;
    private final List b;

    public C1928eV(List list) {
        boolean z = c;
        if (!z && list.size() <= 1) {
            throw new AssertionError();
        }
        if (!z && !list.stream().noneMatch(new dV$$ExternalSyntheticLambda2())) {
            throw new AssertionError();
        }
        if (!z && !list.stream().noneMatch(new Predicate() { // from class: com.android.tools.r8.internal.eV$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((AbstractC1617aV) obj).f();
            }
        })) {
            throw new AssertionError();
        }
        if (!z && !list.stream().noneMatch(new dV$$ExternalSyntheticLambda4())) {
            throw new AssertionError();
        }
        this.b = list;
    }

    final AbstractC1617aV a(AbstractC1617aV abstractC1617aV) {
        boolean z = c;
        if (!z && (abstractC1617aV instanceof P1)) {
            throw new AssertionError();
        }
        if (!z && (abstractC1617aV instanceof C3493zG)) {
            throw new AssertionError();
        }
        if (abstractC1617aV instanceof C1928eV) {
            return new C1928eV(AbstractC1639am.g().a((Iterable) this.b).a((Iterable) abstractC1617aV.b().b).a());
        }
        if (z || abstractC1617aV.d() || (abstractC1617aV instanceof C1846dV)) {
            return new C1928eV(AbstractC1639am.g().a((Iterable) this.b).a(abstractC1617aV).a());
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.internal.AbstractC1617aV
    public final C1928eV b() {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC1617aV
    public final boolean a(AbstractC1305Qs abstractC1305Qs) {
        Iterator it = this.b.iterator();
        while (it.hasNext()) {
            if (((AbstractC1617aV) it.next()).a(abstractC1305Qs)) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.tools.r8.internal.AbstractC1617aV
    public final AbstractC1617aV a(final C0702m c0702m, final com.android.tools.r8.graph.proto.c cVar, final C2005fV c2005fV) {
        List listA = com.android.tools.r8.utils.V1.a(this.b, new Function() { // from class: com.android.tools.r8.internal.eV$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C1928eV.a(c0702m, cVar, c2005fV, (AbstractC1617aV) obj);
            }
        }, (List) null);
        if (listA == null) {
            return this;
        }
        if (listA.isEmpty()) {
            return C3493zG.b;
        }
        if (listA.size() == 1) {
            return (AbstractC1617aV) com.android.tools.r8.utils.V1.a(listA);
        }
        if (AbstractC2734ot.b(listA, new InterfaceC3205vM() { // from class: com.android.tools.r8.internal.eV$$ExternalSyntheticLambda2
            @Override // com.android.tools.r8.internal.InterfaceC3205vM
            public final boolean apply(Object obj) {
                return ((AbstractC1617aV) obj).c();
            }
        })) {
            return P1.b;
        }
        return new C1928eV(listA);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static AbstractC1617aV a(C0702m c0702m, com.android.tools.r8.graph.proto.c cVar, C2005fV c2005fV, AbstractC1617aV abstractC1617aV) {
        AbstractC1617aV abstractC1617aVA = abstractC1617aV.a(c0702m, cVar, c2005fV);
        abstractC1617aVA.getClass();
        if (abstractC1617aVA instanceof C3493zG) {
            return null;
        }
        return abstractC1617aVA;
    }
}
