package com.android.tools.r8.graph;

import java.util.Objects;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class Z0 implements com.android.tools.r8.utils.structural.x {
    private final C0759u2 a;
    private final C0767v2 b;

    private Z0(C0759u2 c0759u2, C0767v2 c0767v2) {
        this.a = c0759u2;
        this.b = c0767v2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(com.android.tools.r8.utils.structural.z zVar) {
        zVar.c(new Function() { // from class: com.android.tools.r8.graph.Z0$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((Z0) obj).a();
            }
        }).c(new Function() { // from class: com.android.tools.r8.graph.Z0$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((Z0) obj).b();
            }
        });
    }

    @Override // com.android.tools.r8.utils.structural.x
    public final com.android.tools.r8.utils.structural.x U() {
        return this;
    }

    public final boolean b(Y0 y0) {
        return this.a.equals(y0.C0()) && this.b.equals(y0.getType());
    }

    public final boolean equals(Object obj) {
        return com.android.tools.r8.utils.structural.k.a(this, obj);
    }

    public final int hashCode() {
        return Objects.hash(this.a, this.b);
    }

    @Override // com.android.tools.r8.utils.structural.x
    public final com.android.tools.r8.utils.structural.y p() {
        return new com.android.tools.r8.utils.structural.y() { // from class: com.android.tools.r8.graph.Z0$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.utils.structural.y
            public final void a(com.android.tools.r8.utils.structural.z zVar) {
                Z0.a(zVar);
            }
        };
    }

    public static Z0 a(Y0 y0) {
        return new Z0(y0.C0(), y0.getType());
    }

    public final C0759u2 a() {
        return this.a;
    }

    @Override // com.android.tools.r8.utils.structural.s, com.android.tools.r8.utils.structural.k
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public final boolean isEqualTo(Z0 z0) {
        return this.a == z0.a && this.b == z0.b;
    }

    public final C0767v2 b() {
        return this.b;
    }
}
