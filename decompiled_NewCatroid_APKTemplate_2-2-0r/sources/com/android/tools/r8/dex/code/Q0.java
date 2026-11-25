package com.android.tools.r8.dex.code;

import com.android.dx.rop.code.RegisterSpec;
import com.android.tools.r8.graph.C0645e5;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.internal.AbstractC1476Wj;
import com.android.tools.r8.internal.C2363kB;
import java.nio.ShortBuffer;
import java.util.function.ToIntFunction;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class Q0 extends K {
    static final /* synthetic */ boolean h = true;
    public final byte e;
    public final byte f;
    public final short g;

    Q0(int i, InterfaceC0409a interfaceC0409a) {
        super(interfaceC0409a);
        this.e = (byte) (i & 15);
        this.f = (byte) ((i >> 4) & 15);
        this.g = B1.e(interfaceC0409a);
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final void a(C0702m c0702m, com.android.tools.r8.dex.L l, C2363kB c2363kB, o5 o5Var) {
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final int hashCode() {
        return (((this.g << 8) | (this.e << 4)) | this.f) ^ getClass().hashCode();
    }

    @Override // com.android.tools.r8.dex.code.B1
    final void b(com.android.tools.r8.utils.structural.o oVar) {
        oVar.a(this, new Q0$$ExternalSyntheticLambda3());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(com.android.tools.r8.utils.structural.z zVar) {
        zVar.a(new ToIntFunction() { // from class: com.android.tools.r8.dex.code.Q0$$ExternalSyntheticLambda0
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return ((Q0) obj).e;
            }
        }).a(new ToIntFunction() { // from class: com.android.tools.r8.dex.code.Q0$$ExternalSyntheticLambda1
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return ((Q0) obj).f;
            }
        }).a(new ToIntFunction() { // from class: com.android.tools.r8.dex.code.Q0$$ExternalSyntheticLambda2
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return ((Q0) obj).g;
            }
        });
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final String b(com.android.tools.r8.utils.P2 p2) {
        return b(RegisterSpec.PREFIX + ((int) this.e) + ", v" + ((int) this.f) + ", #" + ((int) this.g));
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final void a(C0645e5 c0645e5, o5 o5Var, AbstractC1476Wj abstractC1476Wj, AbstractC1476Wj abstractC1476Wj2, C2363kB c2363kB, ShortBuffer shortBuffer) {
        B1.a(this.f, this.e, shortBuffer, v());
        shortBuffer.put(this.g);
    }

    Q0(int i, int i2, int i3) {
        boolean z = h;
        if (!z && (i < 0 || i > 15)) {
            throw new AssertionError();
        }
        if (!z && (i2 < 0 || i2 > 15)) {
            throw new AssertionError();
        }
        if (!z && (-32768 > i3 || i3 > 32767)) {
            throw new AssertionError();
        }
        this.e = (byte) i;
        this.f = (byte) i2;
        this.g = (short) i3;
    }

    @Override // com.android.tools.r8.dex.code.B1
    final int a(B1 b1, com.android.tools.r8.utils.structural.a aVar) {
        return aVar.a(this, (Q0) b1, new Q0$$ExternalSyntheticLambda3());
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final String a(com.android.tools.r8.utils.P2 p2) {
        byte b = this.e;
        byte b2 = this.f;
        return a(RegisterSpec.PREFIX + ((int) b) + ", v" + ((int) b2) + ", " + com.android.tools.r8.utils.Y2.a((int) this.g, 4) + "  # " + ((int) this.g));
    }
}
