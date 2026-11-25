package com.android.tools.r8.graph;

import com.android.tools.r8.internal.C3405y10;
import com.android.tools.r8.utils.AbstractC3938n2;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class P4 extends I4 {
    protected final I4.c b;
    protected final List c;
    protected final List d;
    protected final List e;

    protected P4(I4.c cVar, ArrayList arrayList, ArrayList arrayList2, ArrayList arrayList3) {
        this.b = cVar;
        this.c = arrayList;
        this.d = arrayList2;
        this.e = arrayList3;
    }

    @Override // com.android.tools.r8.graph.AbstractC0713n4
    public final AbstractC3938n2 a(InterfaceC0694k5 interfaceC0694k5, C0702m c0702m, C0680j c0680j) {
        throw new C3405y10("Should not be called on MultipleFieldResolutionResult");
    }

    @Override // com.android.tools.r8.graph.I4
    public final AbstractC3938n2 b(InterfaceC0694k5 interfaceC0694k5, C0702m c0702m) {
        throw new C3405y10("Should not be called on MultipleFieldResolutionResult");
    }

    @Override // com.android.tools.r8.graph.I4
    public final AbstractC0748t0 c(C0729q2 c0729q2, C0702m c0702m, C0680j c0680j) {
        throw new C3405y10("Should not be called on MultipleFieldResolutionResult");
    }

    @Override // com.android.tools.r8.graph.I4
    public final boolean w() {
        return true;
    }

    @Override // com.android.tools.r8.graph.I4
    public final boolean y() {
        throw new C3405y10("Should not be called on MultipleFieldResolutionResult");
    }

    @Override // com.android.tools.r8.graph.I4
    public final W0 a(C0729q2 c0729q2, C0702m c0702m, C0680j c0680j) {
        throw new C3405y10("Should not be called on MultipleFieldResolutionResult");
    }

    @Override // com.android.tools.r8.graph.I4
    public final AbstractC0748t0 b(C0729q2 c0729q2, C0702m c0702m) {
        throw new C3405y10("Should not be called on MultipleFieldResolutionResult");
    }

    @Override // com.android.tools.r8.graph.I4
    public final AbstractC0701l4 a(C0729q2 c0729q2, C0702m c0702m, K3 k3, InterfaceC0679i5 interfaceC0679i5) {
        throw new C3405y10("Should not be called on MultipleFieldResolutionResult");
    }

    @Override // com.android.tools.r8.graph.I4
    public final W0 b(C0729q2 c0729q2, C0702m c0702m, C0680j c0680j) {
        throw new C3405y10("Should not be called on MultipleFieldResolutionResult");
    }

    @Override // com.android.tools.r8.graph.I4
    public final AbstractC0701l4 a(C0729q2 c0729q2, C0702m c0702m, C0729q2 c0729q3, C0729q2 c0729q4) {
        throw new C3405y10("Should not be called on MultipleFieldResolutionResult");
    }

    @Override // com.android.tools.r8.graph.I4
    public final InterfaceC0707m4 a(com.android.tools.r8.shaking.U0 u0, C0680j c0680j) {
        throw new C3405y10("Should not be called on MultipleFieldResolutionResult");
    }

    @Override // com.android.tools.r8.graph.I4
    public final InterfaceC0669h4 a(AbstractC0727q0 abstractC0727q0, C0680j c0680j) {
        throw new C3405y10("Should not be called on MultipleFieldResolutionResult");
    }

    @Override // com.android.tools.r8.graph.I4
    public final void a(Consumer consumer, Consumer consumer2, Consumer consumer3, Consumer consumer4) {
        I4.c cVar = this.b;
        if (cVar != null) {
            consumer.accept(cVar);
        }
        List list = this.c;
        if (list != null) {
            list.forEach(consumer);
        }
        this.d.forEach(consumer2);
        this.e.forEach(consumer4);
    }
}
