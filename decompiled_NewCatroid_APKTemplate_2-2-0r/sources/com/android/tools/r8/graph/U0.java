package com.android.tools.r8.graph;

import com.android.tools.r8.graph.AbstractC0642e2;
import com.android.tools.r8.graph.U0;
import com.android.tools.r8.internal.InterfaceC1285Pz;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class U0<D extends U0<D, R>, R extends AbstractC0642e2<D, R>> extends O0 {
    private final boolean c;
    private com.android.tools.r8.androidapi.h d;
    private final AbstractC0642e2 e;

    public U0(AbstractC0642e2 abstractC0642e2, C0658g0 c0658g0, boolean z, com.android.tools.r8.androidapi.h hVar) {
        super(c0658g0);
        this.e = abstractC0642e2;
        this.c = z;
        this.d = hVar;
    }

    @Override // com.android.tools.r8.graph.O0
    public final boolean C0() {
        return true;
    }

    public abstract void E();

    @Override // com.android.tools.r8.graph.O0
    public boolean F0() {
        return E0();
    }

    public abstract void G0();

    public abstract com.android.tools.r8.androidapi.h H0();

    public final com.android.tools.r8.androidapi.h I0() {
        return this.d;
    }

    public C0767v2 J0() {
        return getReference().B0();
    }

    public C0759u2 K0() {
        return getReference().C0();
    }

    public abstract com.android.tools.r8.ir.optimize.info.g L0();

    @Override // com.android.tools.r8.graph.O0
    /* JADX INFO: renamed from: M0, reason: merged with bridge method [inline-methods] */
    public R getReference() {
        return (R) this.e;
    }

    public final boolean N0() {
        return this.c;
    }

    public final boolean O0() {
        return getAccessFlags().j();
    }

    public final boolean P0() {
        return ((W0) this).getAccessFlags().m();
    }

    public abstract InterfaceC1285Pz Z();

    public abstract Object a(Function function, Function function2);

    public final void a(final Consumer consumer, final Consumer consumer2) {
        a(new Function() { // from class: com.android.tools.r8.graph.U0$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return U0.a(consumer, (T0) obj);
            }
        }, new Function() { // from class: com.android.tools.r8.graph.U0$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return U0.a(consumer2, (W0) obj);
            }
        });
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        return obj.getClass() == getClass() && ((U0) obj).getReference().equals(getReference());
    }

    public final void f(com.android.tools.r8.androidapi.h hVar) {
        this.d = hVar;
    }

    public final int hashCode() {
        return getReference().hashCode();
    }

    @Override // com.android.tools.r8.graph.O0
    public final U0 v0() {
        return this;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Object a(Consumer consumer, T0 t0) {
        consumer.accept(t0);
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Object a(Consumer consumer, W0 w0) {
        consumer.accept(w0);
        return null;
    }
}
