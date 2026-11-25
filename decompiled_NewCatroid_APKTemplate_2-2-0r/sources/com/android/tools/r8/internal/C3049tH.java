package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.naming.AbstractC3649o0;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.internal.tH, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3049tH extends AbstractC3104u3 implements Iterable {
    static final /* synthetic */ boolean b = true;
    private final List a;

    public C3049tH(ArrayList arrayList) {
        this.a = arrayList;
    }

    @Override // com.android.tools.r8.internal.AbstractC3104u3
    public final C3049tH a() {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC3104u3
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public final C3049tH a(final C0702m c0702m, final AbstractC1476Wj abstractC1476Wj) {
        return a(new Function() { // from class: com.android.tools.r8.internal.tH$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C3049tH.a(c0702m, abstractC1476Wj, (C2434l3) obj);
            }
        });
    }

    @Override // com.android.tools.r8.internal.AbstractC3104u3
    public final boolean c() {
        return true;
    }

    public final C2434l3 d() {
        return (C2434l3) com.android.tools.r8.utils.V1.b(this.a);
    }

    @Override // com.android.tools.r8.internal.AbstractC3104u3
    public final boolean isEmpty() {
        return false;
    }

    @Override // java.lang.Iterable
    public final Iterator iterator() {
        return this.a.iterator();
    }

    @Override // com.android.tools.r8.internal.AbstractC3104u3
    public final AbstractC3104u3 b(final C0702m c0702m) {
        return a(new Function() { // from class: com.android.tools.r8.internal.tH$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C3049tH.a(c0702m, (C2434l3) obj);
            }
        });
    }

    @Override // com.android.tools.r8.internal.AbstractC3104u3
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public final C3049tH a(final com.android.tools.r8.graph.u5 u5Var) {
        return a(new Function() { // from class: com.android.tools.r8.internal.tH$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C3049tH.a(u5Var, (C2434l3) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ C2434l3 a(C0702m c0702m, AbstractC1476Wj abstractC1476Wj, C2434l3 c2434l3) {
        return c2434l3.a(c0702m, abstractC1476Wj);
    }

    public final C3049tH a(final AbstractC3649o0 abstractC3649o0, final C0722p1 c0722p1) {
        if (b || !abstractC3649o0.c()) {
            return a(new Function() { // from class: com.android.tools.r8.internal.tH$$ExternalSyntheticLambda2
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return C3049tH.a(abstractC3649o0, c0722p1, (C2434l3) obj);
                }
            });
        }
        throw new AssertionError();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ C2434l3 a(AbstractC3649o0 abstractC3649o0, C0722p1 c0722p1, C2434l3 c2434l3) {
        return c2434l3.a(abstractC3649o0, c0722p1);
    }

    @Override // com.android.tools.r8.internal.AbstractC3104u3
    public final void a(C0702m c0702m) throws Exception {
        if (c0702m.Y().q0().d()) {
            if (!b) {
                AbstractC3256w3.a(c0702m, new EnumC3181v3[0]);
            }
            List list = this.a;
            list.remove(list.size() - 1);
            if (this.a.isEmpty()) {
                c0702m.a(C1345Sf.d());
                return;
            }
        }
        C3049tH c3049tHA = c0702m.z().c() ? this : a(c0702m.z(), c0702m.a());
        com.android.tools.r8.utils.A1 a1Y = c0702m.Y();
        Collection collectionB = a1Y.q0().b();
        boolean z = b;
        if (!z && collectionB.isEmpty()) {
            throw new AssertionError();
        }
        if (!z && c3049tHA.a.size() != collectionB.size()) {
            throw new AssertionError();
        }
        Iterator it = collectionB.iterator();
        Iterator it2 = c3049tHA.a.iterator();
        while (it2.hasNext()) {
            ((C2434l3) it2.next()).a(((C3478z3) it.next()).b(), a1Y.e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ C2434l3 a(C0702m c0702m, C2434l3 c2434l3) {
        return c2434l3.a(c0702m);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ C2434l3 a(com.android.tools.r8.graph.u5 u5Var, C2434l3 c2434l3) {
        return c2434l3.a(u5Var);
    }

    private C3049tH a(Function function) {
        ArrayList arrayList = new ArrayList(this.a.size());
        Iterator it = this.a.iterator();
        while (it.hasNext()) {
            arrayList.add((C2434l3) function.apply((C2434l3) it.next()));
        }
        return new C3049tH(arrayList);
    }
}
