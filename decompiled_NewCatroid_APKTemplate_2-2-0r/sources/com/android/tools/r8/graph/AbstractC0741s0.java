package com.android.tools.r8.graph;

import com.android.tools.r8.graph.AbstractC0642e2;
import com.android.tools.r8.graph.U0;
import com.android.tools.r8.internal.C3405y10;
import com.android.tools.r8.origin.Origin;

/* JADX INFO: renamed from: com.android.tools.r8.graph.s0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC0741s0<D extends U0<D, R>, R extends AbstractC0642e2<D, R>> implements InterfaceC0593a0 {
    static final /* synthetic */ boolean c = true;
    private final AbstractC0727q0 a;
    private final U0 b;

    public AbstractC0741s0(AbstractC0727q0 abstractC0727q0, U0 u0) {
        boolean z = c;
        if (!z && abstractC0727q0 == null) {
            throw new AssertionError();
        }
        if (!z && u0 == null) {
            throw new AssertionError();
        }
        if (!z && abstractC0727q0.d != u0.J0()) {
            throw new AssertionError();
        }
        this.a = abstractC0727q0;
        this.b = u0;
    }

    @Override // com.android.tools.r8.graph.InterfaceC0593a0
    public final C0767v2 C() {
        return t();
    }

    public AbstractC0727q0 a() {
        return this.a;
    }

    @Override // com.android.tools.r8.graph.InterfaceC0593a0
    public AbstractC0727q0 b() {
        return a();
    }

    public final boolean equals(Object obj) {
        throw new C3405y10("Unsupported attempt at comparing Class and DexClassAndMember");
    }

    @Override // com.android.tools.r8.graph.InterfaceC0593a0
    public final Origin getOrigin() {
        return this.a.c;
    }

    public final int hashCode() {
        throw new C3405y10("Unsupported attempt at computing the hash code of DexClassAndMember");
    }

    public final C0658g0 s() {
        return this.b.s0();
    }

    public C0767v2 t() {
        return this.a.d;
    }

    public final String toString() {
        return w();
    }

    public C0759u2 u() {
        return getReference().C0();
    }

    @Override // com.android.tools.r8.graph.InterfaceC0593a0
    /* JADX INFO: renamed from: v, reason: merged with bridge method [inline-methods] */
    public R getReference() {
        return (R) this.b.getReference();
    }

    public String w() {
        return getReference().r0();
    }

    @Override // com.android.tools.r8.graph.InterfaceC0593a0
    public D e() {
        return (D) this.b;
    }
}
