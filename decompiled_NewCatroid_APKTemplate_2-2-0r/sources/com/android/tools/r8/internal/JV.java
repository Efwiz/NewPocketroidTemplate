package com.android.tools.r8.internal;

import com.android.tools.r8.utils.AbstractC3883d1;
import com.android.tools.r8.utils.AbstractC3897g0;
import com.android.tools.r8.utils.InterfaceC3877c1;
import java.util.Comparator;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.Consumer;
import java.util.function.Supplier;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class JV extends CN {
    static {
        new JV(new Supplier() { // from class: com.android.tools.r8.internal.JV$$ExternalSyntheticLambda4
            @Override // java.util.function.Supplier
            public final Object get() {
                return JV.i();
            }
        });
    }

    private JV(Supplier supplier) {
        super(supplier);
    }

    public static JV b(InterfaceC3877c1 interfaceC3877c1) {
        final JV jv = new JV(new Supplier() { // from class: com.android.tools.r8.internal.JV$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
                return JV.g();
            }
        });
        interfaceC3877c1.forEach(new Consumer() { // from class: com.android.tools.r8.internal.JV$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.add((com.android.tools.r8.graph.o5) obj);
            }
        });
        return jv;
    }

    public static JV f() {
        return b(AbstractC3883d1.a());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ SortedMap g() {
        return new TreeMap(new JV$$ExternalSyntheticLambda3());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ SortedMap i() {
        return new TreeMap(AbstractC3897g0.a());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Consumer consumer, AbstractC1476Wj abstractC1476Wj, com.android.tools.r8.graph.Q0 q0, com.android.tools.r8.graph.o5 o5Var) {
        consumer.accept(abstractC1476Wj.a(o5Var, q0));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(final AbstractC1476Wj abstractC1476Wj, final com.android.tools.r8.graph.Q0 q0, final Consumer consumer) {
        forEach(new Consumer() { // from class: com.android.tools.r8.internal.JV$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                JV.a(consumer, abstractC1476Wj, q0, (com.android.tools.r8.graph.o5) obj);
            }
        });
    }

    @Override // com.android.tools.r8.internal.AbstractC2401kf
    public final Set a() {
        final TreeSet treeSet = new TreeSet(new Comparator() { // from class: com.android.tools.r8.internal.JV$$ExternalSyntheticLambda5
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return JV.a((com.android.tools.r8.graph.W0) obj, (com.android.tools.r8.graph.W0) obj2);
            }
        });
        forEach(new Consumer() { // from class: com.android.tools.r8.internal.JV$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                JV.a(treeSet, (com.android.tools.r8.graph.o5) obj);
            }
        });
        return treeSet;
    }

    @Override // com.android.tools.r8.internal.CN
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public final JV a(final com.android.tools.r8.graph.Q0 q0, final AbstractC1476Wj abstractC1476Wj) {
        return b(new InterfaceC3877c1() { // from class: com.android.tools.r8.internal.JV$$ExternalSyntheticLambda7
            @Override // com.android.tools.r8.utils.InterfaceC3877c1
            public final void forEach(Consumer consumer) {
                this.f$0.a(abstractC1476Wj, q0, consumer);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int a(com.android.tools.r8.graph.W0 w0, com.android.tools.r8.graph.W0 w1) {
        return w0.getReference().compareTo(w1.getReference());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Set set, com.android.tools.r8.graph.o5 o5Var) {
        set.add(o5Var.e());
    }
}
