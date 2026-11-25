package com.android.tools.r8.graph;

import com.huawei.hms.framework.common.ContainerUtils;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.graph.f0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C0649f0 extends AbstractC0594a1 implements com.android.tools.r8.utils.structural.x<C0649f0> {
    public static final C0649f0[] c = new C0649f0[0];
    static final /* synthetic */ boolean d = true;
    public final C0759u2 a;
    public final B2 b;

    public C0649f0(C0759u2 c0759u2, B2 b2) {
        this.a = c0759u2;
        this.b = b2;
    }

    @Override // com.android.tools.r8.utils.structural.x
    public final com.android.tools.r8.utils.structural.x U() {
        return this;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof C0649f0)) {
            return false;
        }
        C0649f0 c0649f0 = (C0649f0) obj;
        return this.a.equals(c0649f0.a) && this.b.equals(c0649f0.b);
    }

    public final int hashCode() {
        return (this.b.hashCode() * 3) + this.a.hashCode();
    }

    @Override // com.android.tools.r8.utils.structural.x
    public final com.android.tools.r8.utils.structural.y p() {
        return new com.android.tools.r8.utils.structural.y() { // from class: com.android.tools.r8.graph.f0$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.utils.structural.y
            public final void a(com.android.tools.r8.utils.structural.z zVar) {
                C0649f0.a(zVar);
            }
        };
    }

    public final C0759u2 s0() {
        return this.a;
    }

    public B2 t0() {
        return this.b;
    }

    public final String toString() {
        return this.a + ContainerUtils.KEY_VALUE_DELIMITER + this.b;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(com.android.tools.r8.utils.structural.z zVar) {
        zVar.c(new Function() { // from class: com.android.tools.r8.graph.f0$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((C0649f0) obj).a;
            }
        }).c(new Function() { // from class: com.android.tools.r8.graph.f0$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((C0649f0) obj).b;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.android.tools.r8.graph.AbstractC0594a1
    public final void a(com.android.tools.r8.dex.V v) {
        if (!d) {
            throw new AssertionError();
        }
    }
}
