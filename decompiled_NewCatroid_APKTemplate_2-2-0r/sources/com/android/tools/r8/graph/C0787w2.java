package com.android.tools.r8.graph;

import com.android.tools.r8.internal.C00;
import com.android.tools.r8.internal.C3405y10;
import java.util.function.Function;
import java.util.function.ToIntFunction;

/* JADX INFO: renamed from: com.android.tools.r8.graph.w2, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C0787w2 extends C0633d0 {
    private final int e;
    private final C00 f;

    public C0787w2(int i, R0 r0, int i2, C00 c00) {
        super(i, r0);
        this.e = i2;
        this.f = c00;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(com.android.tools.r8.utils.structural.z zVar) {
        zVar.a(new ToIntFunction() { // from class: com.android.tools.r8.graph.w2$$ExternalSyntheticLambda1
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return this.f$0.d((C0633d0) obj);
            }
        }).b(new Function() { // from class: com.android.tools.r8.graph.w2$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.e((C0633d0) obj);
            }
        }).a((com.android.tools.r8.utils.structural.y) new d0$$ExternalSyntheticLambda2());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int d(C0633d0 c0633d0) {
        return this.e;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int[] e(C0633d0 c0633d0) {
        int iA = this.f.a() * 2;
        int[] iArr = new int[iA];
        for (int i = 0; i < iA; i++) {
            int i2 = i * 2;
            iArr[i2] = this.f.a(i);
            iArr[i2 + 1] = this.f.b(i);
        }
        return iArr;
    }

    @Override // com.android.tools.r8.graph.C0633d0
    public final void a(C0702m c0702m, com.android.tools.r8.dex.L l) {
        throw new C3405y10("Should not collect type annotation in DEX");
    }

    @Override // com.android.tools.r8.graph.C0633d0, com.android.tools.r8.utils.structural.x
    public final com.android.tools.r8.utils.structural.y p() {
        return new com.android.tools.r8.utils.structural.y() { // from class: com.android.tools.r8.graph.w2$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.utils.structural.y
            public final void a(com.android.tools.r8.utils.structural.z zVar) {
                this.f$0.b(zVar);
            }
        };
    }

    @Override // com.android.tools.r8.graph.C0633d0
    public final C0787w2 s0() {
        return this;
    }

    @Override // com.android.tools.r8.graph.C0633d0
    public final boolean v0() {
        return true;
    }

    public final C00 w0() {
        return this.f;
    }

    public final int x0() {
        return this.e;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.android.tools.r8.graph.C0633d0, com.android.tools.r8.graph.AbstractC0594a1
    public final void a(com.android.tools.r8.dex.V v) {
        throw new C3405y10("Should not collect type annotation in DEX");
    }
}
