package com.android.tools.r8.graph;

import com.android.tools.r8.internal.AbstractC2734ot;
import com.android.tools.r8.internal.InterfaceC3205vM;
import com.android.tools.r8.internal.OU;
import com.android.tools.r8.utils.C3912j0;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class F4 {
    static final /* synthetic */ boolean d = true;
    private I4 a;
    private ArrayList b;
    private boolean c;

    private F4() {
        this.a = null;
        this.b = null;
        this.c = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(I4.a aVar, I4.a aVar2) {
        return aVar2 == aVar;
    }

    public final void a(I4 i4) {
        if (this.a == null) {
            this.a = i4;
            return;
        }
        if (this.b == null) {
            ArrayList arrayList = new ArrayList();
            this.b = arrayList;
            arrayList.add(this.a);
        }
        this.b.add(i4);
    }

    public final F4 a() {
        this.c = true;
        return this;
    }

    public final I4 a(I4.a aVar, Collection collection) {
        I4 i4 = this.a;
        if (i4 == null) {
            return aVar;
        }
        if (this.b == null) {
            return i4;
        }
        final ArrayList arrayList = new ArrayList();
        final ArrayList arrayList2 = new ArrayList();
        final ArrayList arrayList3 = new ArrayList();
        final Set setC = OU.c();
        this.b.forEach(new Consumer() { // from class: com.android.tools.r8.graph.F4$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(arrayList, arrayList2, setC, arrayList3, (I4) obj);
            }
        });
        if (!setC.isEmpty()) {
            if (arrayList2.isEmpty() && arrayList.isEmpty()) {
                arrayList3.add(I4.b.c);
            } else {
                arrayList3.add(a(setC, collection));
            }
        }
        if (arrayList.isEmpty()) {
            if (arrayList2.size() == 1 && arrayList3.isEmpty()) {
                return (I4) arrayList2.get(0);
            }
            if (arrayList2.isEmpty() && arrayList3.size() == 1) {
                return (I4) arrayList3.get(0);
            }
            return new N4(arrayList2, arrayList3);
        }
        if (arrayList2.isEmpty() && arrayList3.isEmpty() && arrayList.size() == 1) {
            return (I4) arrayList.get(0);
        }
        if (arrayList.size() == 1) {
            I4.c cVar = (I4.c) arrayList.get(0);
            cVar.getClass();
            if (cVar instanceof U4) {
                return new Q4(cVar.n(), arrayList2, arrayList3);
            }
            S4 s4M = cVar.m();
            if (d || s4M != null) {
                return new M4(s4M, arrayList2, arrayList3);
            }
            throw new AssertionError();
        }
        return new O4(arrayList, arrayList2, arrayList3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(final List list, final List list2, final Set set, final List list3, I4 i4) {
        i4.a(new Consumer() { // from class: com.android.tools.r8.graph.F4$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(list, (I4.c) obj);
            }
        }, new Consumer() { // from class: com.android.tools.r8.graph.F4$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                F4.a(list2, (T4) obj);
            }
        }, C3912j0.b(), new Consumer() { // from class: com.android.tools.r8.graph.F4$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                F4.a(set, list3, (I4.a) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(List list, I4.c cVar) {
        if (!list.isEmpty() && !this.c && !d) {
            throw new AssertionError("Unexpected multiple results between program and classpath");
        }
        list.add(cVar);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(List list, final T4 t4) {
        if (AbstractC2734ot.b(list, new InterfaceC3205vM() { // from class: com.android.tools.r8.graph.F4$$ExternalSyntheticLambda2
            @Override // com.android.tools.r8.internal.InterfaceC3205vM
            public final boolean apply(Object obj) {
                return F4.a(t4, (T4) obj);
            }
        })) {
            return;
        }
        list.add(t4);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(T4 t4, T4 t5) {
        return t5.d() == t4.d();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Set set, List list, final I4.a aVar) {
        if (aVar.u()) {
            set.add(aVar.l());
        }
        if (AbstractC2734ot.b(list, new InterfaceC3205vM() { // from class: com.android.tools.r8.graph.F4$$ExternalSyntheticLambda1
            @Override // com.android.tools.r8.internal.InterfaceC3205vM
            public final boolean apply(Object obj) {
                return F4.a(aVar, (I4.a) obj);
            }
        })) {
            return;
        }
        list.add(aVar);
    }

    private static I4.b a(Set set, Collection collection) {
        final Set setA = com.android.tools.r8.utils.T2.a((Iterable) collection);
        set.forEach(new Consumer() { // from class: com.android.tools.r8.graph.F4$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                F4.a(setA, (I4.b) obj);
            }
        });
        if (setA.isEmpty()) {
            return I4.b.c;
        }
        return new R4(setA);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(Set set, I4.b bVar) {
        if (!d && bVar != I4.b.c) {
            bVar.getClass();
            if (!(bVar instanceof R4)) {
                throw new AssertionError();
            }
        }
        Collection collection = bVar.b;
        if (collection != null) {
            set.addAll(collection);
        }
    }
}
