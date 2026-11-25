package com.android.tools.r8.dex.code;

import com.android.tools.r8.graph.C0645e5;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.internal.AbstractC1476Wj;
import com.android.tools.r8.internal.C2363kB;
import java.nio.ShortBuffer;
import java.util.Arrays;
import java.util.function.Function;
import java.util.function.ToIntFunction;
import org.slf4j.Marker;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class N3 extends d4 {
    static final /* synthetic */ boolean h = true;
    public final int e;
    public final int[] f;
    public final int[] g;

    public N3(int i, InterfaceC0409a interfaceC0409a) {
        int i2;
        super(i, interfaceC0409a);
        int iA = B1.a(interfaceC0409a);
        this.e = iA;
        this.f = new int[iA];
        int i3 = 0;
        while (true) {
            i2 = this.e;
            if (i3 >= i2) {
                break;
            }
            this.f[i3] = B1.f(interfaceC0409a);
            i3++;
        }
        this.g = new int[i2];
        for (int i4 = 0; i4 < this.e; i4++) {
            this.g[i4] = B1.f(interfaceC0409a);
        }
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final boolean K() {
        return true;
    }

    @Override // com.android.tools.r8.dex.code.d4
    public final int[] N() {
        return this.f;
    }

    @Override // com.android.tools.r8.dex.code.d4
    public final int O() {
        return this.e;
    }

    @Override // com.android.tools.r8.dex.code.d4
    public final int[] P() {
        return this.g;
    }

    @Override // com.android.tools.r8.dex.code.R2, com.android.tools.r8.dex.code.B1
    public final int hashCode() {
        return Arrays.hashCode(this.g) + ((Arrays.hashCode(this.f) + ((this.e + 17036887) * 31)) * 31);
    }

    @Override // com.android.tools.r8.dex.code.J, com.android.tools.r8.dex.code.B1
    public final int z() {
        return (this.g.length * 2) + (this.f.length * 2) + 2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(com.android.tools.r8.utils.structural.z zVar) {
        zVar.a(new ToIntFunction() { // from class: com.android.tools.r8.dex.code.N3$$ExternalSyntheticLambda0
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return ((N3) obj).e;
            }
        }).b(new Function() { // from class: com.android.tools.r8.dex.code.N3$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((N3) obj).f;
            }
        }).b(new Function() { // from class: com.android.tools.r8.dex.code.N3$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((N3) obj).g;
            }
        });
    }

    @Override // com.android.tools.r8.dex.code.R2, com.android.tools.r8.dex.code.B1
    final void b(com.android.tools.r8.utils.structural.o oVar) {
        oVar.a(this, new N3$$ExternalSyntheticLambda3());
    }

    @Override // com.android.tools.r8.dex.code.R2, com.android.tools.r8.dex.code.B1
    final int a(B1 b1, com.android.tools.r8.utils.structural.a aVar) {
        return aVar.a(this, (N3) b1, new N3$$ExternalSyntheticLambda3());
    }

    @Override // com.android.tools.r8.dex.code.R2, com.android.tools.r8.dex.code.B1
    public final String b(com.android.tools.r8.utils.P2 p2) {
        return a(p2, (B1) null);
    }

    @Override // com.android.tools.r8.dex.code.R2, com.android.tools.r8.dex.code.B1
    public final void a(C0645e5 c0645e5, o5 o5Var, AbstractC1476Wj abstractC1476Wj, AbstractC1476Wj abstractC1476Wj2, C2363kB c2363kB, ShortBuffer shortBuffer) {
        B1.a(2, shortBuffer, 0);
        shortBuffer.put((short) this.e);
        for (int i = 0; i < this.e; i++) {
            B1.a(this.f[i], shortBuffer);
        }
        for (int i2 = 0; i2 < this.e; i2++) {
            B1.a(this.g[i2], shortBuffer);
        }
    }

    public N3(int[] iArr, int[] iArr2) {
        if (!h && iArr2.length <= 0) {
            throw new AssertionError();
        }
        this.e = iArr2.length;
        this.f = iArr;
        this.g = iArr2;
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final String a(com.android.tools.r8.utils.P2 p2, B1 b1) {
        String string;
        StringBuilder sb = new StringBuilder("[SparseSwitchPayload");
        if (b1 == null) {
            sb.append(" offsets relative to associated SparseSwitch");
        }
        sb.append("]\n");
        for (int i = 0; i < this.e; i++) {
            if (b1 != null) {
                string = com.android.tools.r8.utils.Y2.a(b1.u() + this.g[i], 2);
            } else {
                int i2 = this.g[i];
                string = i2 >= 0 ? Marker.ANY_NON_NULL_MARKER + i2 : Integer.toString(i2);
            }
            com.android.tools.r8.utils.Y2.a(sb, this.f[i] + " -> " + string + "\n", 20);
        }
        return super.b(p2) + sb.toString();
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final String a(B1 b1) {
        StringBuilder sb = new StringBuilder("    .sparse-switch\n");
        for (int i = 0; i < this.f.length; i++) {
            sb.append("      ");
            sb.append(com.android.tools.r8.utils.Y2.a(this.f[i], 8));
            sb.append(" -> :label_");
            sb.append(b1.u() + this.g[i]);
            sb.append("  # ");
            sb.append(this.f[i]);
            sb.append("\n");
        }
        sb.append("    .end sparse-switch");
        return sb.toString();
    }
}
