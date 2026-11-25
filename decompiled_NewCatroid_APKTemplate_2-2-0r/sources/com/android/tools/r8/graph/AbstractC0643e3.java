package com.android.tools.r8.graph;

import com.android.tools.r8.internal.Z00;
import com.android.tools.r8.utils.AbstractC3938n2;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.graph.e3, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC0643e3 extends AbstractC0677i3 {
    static final /* synthetic */ boolean e = true;
    protected final AbstractC0677i3.a b;
    protected final List c;
    protected final List d;

    public AbstractC0643e3(AbstractC0677i3.a aVar, ArrayList arrayList, ArrayList arrayList2) {
        boolean z = e;
        if (!z && aVar != null && aVar.d().f0()) {
            throw new AssertionError();
        }
        if (!z && !arrayList2.stream().allMatch(new Predicate() { // from class: com.android.tools.r8.graph.e3$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((AbstractC0629c3) obj).A();
            }
        })) {
            throw new AssertionError();
        }
        if (!z) {
            if (arrayList2.size() + arrayList.size() + com.android.tools.r8.utils.M.a(aVar != null) <= 1) {
                throw new AssertionError("Should have been a single or failed result");
            }
        }
        this.b = aVar;
        this.c = arrayList;
        this.d = arrayList2;
    }

    @Override // com.android.tools.r8.graph.AbstractC0677i3
    public final boolean A() {
        return !this.d.isEmpty();
    }

    @Override // com.android.tools.r8.graph.AbstractC0677i3
    public final AbstractC0727q0 a() {
        throw new Z00("Should not be called on MultipleFieldResolutionResult");
    }

    @Override // com.android.tools.r8.graph.AbstractC0677i3, com.android.tools.r8.graph.AbstractC0713n4
    public final /* bridge */ /* synthetic */ C5 g() {
        return null;
    }

    @Override // com.android.tools.r8.graph.AbstractC0677i3, com.android.tools.r8.graph.AbstractC0713n4
    public final boolean j() {
        return this.d.isEmpty();
    }

    @Override // com.android.tools.r8.graph.AbstractC0677i3
    public final boolean v() {
        AbstractC0677i3.a aVar = this.b;
        return aVar != null && aVar.v();
    }

    @Override // com.android.tools.r8.graph.AbstractC0677i3
    public final boolean w() {
        return this.b != null;
    }

    @Override // com.android.tools.r8.graph.AbstractC0677i3
    public final boolean x() {
        AbstractC0677i3.a aVar = this.b;
        return aVar != null && aVar.x();
    }

    @Override // com.android.tools.r8.graph.AbstractC0677i3
    public final boolean y() {
        return w() || !this.c.isEmpty();
    }

    @Override // com.android.tools.r8.graph.AbstractC0677i3
    public final boolean z() {
        return true;
    }

    @Override // com.android.tools.r8.graph.AbstractC0713n4
    public final AbstractC3938n2 a(InterfaceC0694k5 interfaceC0694k5, C0702m c0702m, C0680j c0680j) {
        throw new Z00("Should not be called on MultipleFieldResolutionResult");
    }

    @Override // com.android.tools.r8.graph.AbstractC0677i3
    public final void a(Consumer consumer, Consumer consumer2, Consumer consumer3) {
        AbstractC0677i3.a aVar = this.b;
        if (aVar != null) {
            consumer.accept(aVar);
        }
        this.c.forEach(consumer2);
        this.d.forEach(consumer3);
    }
}
