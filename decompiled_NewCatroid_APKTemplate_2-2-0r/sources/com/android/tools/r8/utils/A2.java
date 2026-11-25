package com.android.tools.r8.utils;

import com.android.tools.r8.ClassConflictResolver;
import com.android.tools.r8.dex.InterfaceC0407b;
import com.android.tools.r8.errors.DuplicateTypesDiagnostic;
import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.references.Reference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;
import java.util.function.Supplier;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public final class A2 extends X {
    static final /* synthetic */ boolean e = true;
    private final InterfaceC0407b d;

    private A2(ConcurrentHashMap concurrentHashMap, InterfaceC0407b interfaceC0407b) {
        super(concurrentHashMap, null);
        this.d = interfaceC0407b;
    }

    @Override // com.android.tools.r8.utils.X
    final Supplier a(AbstractC0727q0 abstractC0727q0) {
        return (C0729q2) abstractC0727q0;
    }

    @Override // com.android.tools.r8.utils.X
    final com.android.tools.r8.graph.I e() {
        return com.android.tools.r8.graph.I.c;
    }

    @Override // com.android.tools.r8.utils.X
    public final String toString() {
        return "program classes: " + super.toString();
    }

    @Override // com.android.tools.r8.utils.X
    final AbstractC0727q0 a(AbstractC0727q0 abstractC0727q0, AbstractC0727q0 abstractC0727q1) {
        return this.d.a((C0729q2) abstractC0727q0, (C0729q2) abstractC0727q1);
    }

    public static A2 a(List list, final InterfaceC0407b interfaceC0407b) {
        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            C0729q2 c0729q2 = (C0729q2) it.next();
            concurrentHashMap.merge(c0729q2.d, c0729q2, new BiFunction() { // from class: com.android.tools.r8.utils.A2$$ExternalSyntheticLambda0
                @Override // java.util.function.BiFunction
                public final Object apply(Object obj, Object obj2) {
                    return A2.a(interfaceC0407b, (Supplier) obj, (Supplier) obj2);
                }
            });
        }
        return new A2(concurrentHashMap, interfaceC0407b);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Supplier a(InterfaceC0407b interfaceC0407b, Supplier supplier, Supplier supplier2) {
        return interfaceC0407b.a((C0729q2) supplier.get(), (C0729q2) supplier2.get());
    }

    public static InterfaceC0407b a(final ClassConflictResolver classConflictResolver, final O2 o2) {
        return new InterfaceC0407b() { // from class: com.android.tools.r8.utils.A2$$ExternalSyntheticLambda1
            @Override // com.android.tools.r8.dex.InterfaceC0407b
            public final C0729q2 a(C0729q2 c0729q2, C0729q2 c0729q3) {
                return A2.a(classConflictResolver, o2, c0729q2, c0729q3);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static C0729q2 a(ClassConflictResolver classConflictResolver, O2 o2, C0729q2 c0729q2, C0729q2 c0729q3) {
        C0729q2 c0729q4;
        boolean z = e;
        if (!z && c0729q2.d != c0729q3.d) {
            throw new AssertionError();
        }
        boolean zQ = c0729q2.e.q();
        boolean zQ2 = c0729q3.e.q();
        if (!zQ || !zQ2) {
            if (zQ) {
                if (!z && !c0729q2.e.q()) {
                    throw new AssertionError();
                }
                if (!z && c0729q3.e.q()) {
                    throw new AssertionError();
                }
                if (c0729q2.c instanceof C3987x1) {
                    c0729q4 = c0729q3;
                }
            } else if (zQ2) {
                if (!z && !c0729q3.e.q()) {
                    throw new AssertionError();
                }
                if (!z && c0729q2.e.q()) {
                    throw new AssertionError();
                }
                if (c0729q3.c instanceof C3987x1) {
                    c0729q4 = c0729q2;
                }
            }
            c0729q4 = null;
        } else if (c0729q2.d.S0()) {
            if (!z) {
                if (!z && c0729q2.Y().j() != c0729q3.Y().j()) {
                    throw new AssertionError();
                }
                if (!z && c0729q2.Y().l() != c0729q3.Y().l()) {
                    throw new AssertionError();
                }
            }
            c0729q4 = c0729q2;
        } else {
            c0729q4 = null;
        }
        if (c0729q4 != null) {
            return c0729q4;
        }
        if (classConflictResolver != null) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(c0729q2.c);
            arrayList.add(c0729q3.c);
            Origin originResolveDuplicateClass = classConflictResolver.resolveDuplicateClass(c0729q2.Z0(), arrayList, o2);
            if (originResolveDuplicateClass == c0729q2.c) {
                return c0729q2;
            }
            if (originResolveDuplicateClass == c0729q3.c) {
                return c0729q3;
            }
        }
        o2.a(new DuplicateTypesDiagnostic(Reference.classFromDescriptor(c0729q2.d.b1()), AbstractC1639am.a(c0729q2.c, c0729q3.c)));
        throw null;
    }
}
