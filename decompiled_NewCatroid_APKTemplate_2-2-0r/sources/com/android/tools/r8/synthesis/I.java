package com.android.tools.r8.synthesis;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.o5;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class I extends AbstractC3855m implements T {
    static final /* synthetic */ boolean e = true;
    private final o5 d;

    I(L.b bVar, C3846e c3846e, o5 o5Var) {
        super(bVar, c3846e);
        if (!e && !bVar.i()) {
            throw new AssertionError();
        }
        this.d = o5Var;
    }

    @Override // com.android.tools.r8.synthesis.AbstractC3855m
    final int a(AbstractC3855m abstractC3855m, com.android.tools.r8.utils.structural.t tVar) {
        return this.d.e().a(((I) abstractC3855m).d.e(), tVar);
    }

    @Override // com.android.tools.r8.synthesis.AbstractC3855m
    public final T c() {
        return this;
    }

    @Override // com.android.tools.r8.synthesis.AbstractC3855m
    public final AbstractC0727q0 e() {
        return this.d.a();
    }

    @Override // com.android.tools.r8.synthesis.AbstractC3855m
    public final boolean i() {
        return H.a(this.d.e(), f());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.android.tools.r8.synthesis.AbstractC3855m
    /* JADX INFO: renamed from: k, reason: merged with bridge method [inline-methods] */
    public final J j() {
        return new J(f(), d(), this.d.getReference());
    }

    public final String toString() {
        return "SyntheticMethodDefinition{" + this.d + "}";
    }

    @Override // com.android.tools.r8.synthesis.T
    public final void a(Consumer consumer, Consumer consumer2) {
        consumer.accept(this);
    }

    @Override // com.android.tools.r8.synthesis.T
    public final C0729q2 a() {
        return this.d.a();
    }

    @Override // com.android.tools.r8.synthesis.AbstractC3855m
    final void a(com.android.tools.r8.utils.structural.m mVar, com.android.tools.r8.utils.structural.t tVar) {
        this.d.e().a(mVar, tVar);
    }
}
