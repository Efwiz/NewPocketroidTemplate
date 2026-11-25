package com.android.tools.r8.dex.code;

import com.android.dx.rop.code.RegisterSpec;
import com.android.tools.r8.graph.AbstractC0744s2;
import java.util.function.Function;
import java.util.function.ToIntFunction;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class P0 extends K {
    static final /* synthetic */ boolean h = true;
    public final byte e;
    public final byte f;
    public AbstractC0744s2 g;

    P0(int i, InterfaceC0409a interfaceC0409a, AbstractC0744s2[] abstractC0744s2Arr) {
        super(interfaceC0409a);
        this.e = (byte) (i & 15);
        this.f = (byte) ((i >> 4) & 15);
        this.g = abstractC0744s2Arr[B1.a(interfaceC0409a)];
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final int hashCode() {
        return (((this.g.hashCode() << 8) | (this.e << 4)) | this.f) ^ getClass().hashCode();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(com.android.tools.r8.utils.structural.z zVar) {
        zVar.a(new ToIntFunction() { // from class: com.android.tools.r8.dex.code.P0$$ExternalSyntheticLambda1
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return ((P0) obj).e;
            }
        }).a(new ToIntFunction() { // from class: com.android.tools.r8.dex.code.P0$$ExternalSyntheticLambda2
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return ((P0) obj).f;
            }
        }).a(new Function() { // from class: com.android.tools.r8.dex.code.P0$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((P0) obj).g;
            }
        });
    }

    @Override // com.android.tools.r8.dex.code.B1
    final int a(B1 b1, com.android.tools.r8.utils.structural.a aVar) {
        return aVar.a(this, (P0) b1, (com.android.tools.r8.utils.structural.y<P0>) new com.android.tools.r8.utils.structural.y() { // from class: com.android.tools.r8.dex.code.P0$$ExternalSyntheticLambda4
            @Override // com.android.tools.r8.utils.structural.y
            public final void a(com.android.tools.r8.utils.structural.z zVar) {
                P0.b(zVar);
            }
        });
    }

    @Override // com.android.tools.r8.dex.code.B1
    final void b(com.android.tools.r8.utils.structural.o oVar) {
        oVar.a(this, new com.android.tools.r8.utils.structural.y() { // from class: com.android.tools.r8.dex.code.P0$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.utils.structural.y
            public final void a(com.android.tools.r8.utils.structural.z zVar) {
                P0.b(zVar);
            }
        });
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final String a(com.android.tools.r8.utils.P2 p2) {
        return a(RegisterSpec.PREFIX + ((int) this.e) + ", v" + ((int) this.f) + ", " + this.g.q0());
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final String b(com.android.tools.r8.utils.P2 p2) {
        return b(RegisterSpec.PREFIX + ((int) this.e) + ", v" + ((int) this.f) + ", " + p2.a(this.g));
    }

    P0(int i, int i2, AbstractC0744s2 abstractC0744s2) {
        boolean z = h;
        if (!z && (i < 0 || i > 15)) {
            throw new AssertionError();
        }
        if (!z && (i2 < 0 || i2 > 15)) {
            throw new AssertionError();
        }
        this.e = (byte) i;
        this.f = (byte) i2;
        this.g = abstractC0744s2;
    }
}
