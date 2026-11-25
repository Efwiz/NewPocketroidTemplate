package com.android.tools.r8.graph;

import com.android.tools.r8.internal.AbstractC1915eM;
import java.util.Objects;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class F0 extends B0 {
    private final AbstractC1915eM c;

    F0(AbstractC1915eM abstractC1915eM) {
        this.c = abstractC1915eM;
    }

    @Override // com.android.tools.r8.graph.B0, com.android.tools.r8.utils.structural.x
    public final com.android.tools.r8.utils.structural.x U() {
        return this;
    }

    @Override // com.android.tools.r8.graph.B0, com.android.tools.r8.utils.structural.x
    public final /* bridge */ /* synthetic */ int a(com.android.tools.r8.utils.structural.x xVar, com.android.tools.r8.utils.structural.a aVar) {
        return a((B0) xVar, aVar);
    }

    @Override // com.android.tools.r8.graph.B0
    final int b(B0 b0, com.android.tools.r8.utils.structural.a aVar) {
        return aVar.a(this, (F0) b0, new F0$$ExternalSyntheticLambda0());
    }

    public final int hashCode() {
        return Objects.hashCode(this.c) * 31;
    }

    public final String toString() {
        return String.format("SET_POSITION_FRAME %s", this.c);
    }

    @Override // com.android.tools.r8.graph.B0
    public final F0 u0() {
        return this;
    }

    @Override // com.android.tools.r8.graph.B0
    final int v0() {
        return 256;
    }

    public final AbstractC1915eM x0() {
        return this.c;
    }

    @Override // com.android.tools.r8.graph.B0
    final void b(com.android.tools.r8.utils.structural.o oVar) {
        oVar.a(this, new F0$$ExternalSyntheticLambda0());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(com.android.tools.r8.utils.structural.z zVar) {
        zVar.h(new Function() { // from class: com.android.tools.r8.graph.F0$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((F0) obj).c;
            }
        });
    }

    @Override // com.android.tools.r8.graph.B0
    public final void a(I0 i0) {
        i0.a(this);
    }
}
