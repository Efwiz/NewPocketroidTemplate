package com.android.tools.r8.dex.code;

import com.android.tools.r8.graph.C0645e5;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.internal.AbstractC1476Wj;
import com.android.tools.r8.internal.C2363kB;
import java.nio.ShortBuffer;
import java.util.Arrays;
import java.util.function.Function;
import java.util.function.ToIntFunction;

/* JADX INFO: renamed from: com.android.tools.r8.dex.code.b3, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C0418b3 extends d4 {
    static final /* synthetic */ boolean h = true;
    public final int e;
    public final int f;
    public final int[] g;

    public C0418b3(int i, InterfaceC0409a interfaceC0409a) {
        super(i, interfaceC0409a);
        int iA = B1.a(interfaceC0409a);
        this.e = iA;
        this.f = B1.f(interfaceC0409a);
        this.g = new int[iA];
        for (int i2 = 0; i2 < this.e; i2++) {
            this.g[i2] = B1.f(interfaceC0409a);
        }
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final boolean K() {
        return true;
    }

    @Override // com.android.tools.r8.dex.code.d4
    public final int[] N() {
        return new int[]{this.f};
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
        return Arrays.hashCode(this.g) + ((((this.e + 17036887) * 31) + this.f) * 31);
    }

    @Override // com.android.tools.r8.dex.code.J, com.android.tools.r8.dex.code.B1
    public final int z() {
        return (this.g.length * 2) + 4;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(com.android.tools.r8.utils.structural.z zVar) {
        zVar.a(new ToIntFunction() { // from class: com.android.tools.r8.dex.code.b3$$ExternalSyntheticLambda0
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return ((C0418b3) obj).e;
            }
        }).a(new ToIntFunction() { // from class: com.android.tools.r8.dex.code.b3$$ExternalSyntheticLambda1
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return ((C0418b3) obj).f;
            }
        }).b(new Function() { // from class: com.android.tools.r8.dex.code.b3$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((C0418b3) obj).g;
            }
        });
    }

    @Override // com.android.tools.r8.dex.code.R2, com.android.tools.r8.dex.code.B1
    final void b(com.android.tools.r8.utils.structural.o oVar) {
        oVar.a(this, new b3$$ExternalSyntheticLambda3());
    }

    @Override // com.android.tools.r8.dex.code.R2, com.android.tools.r8.dex.code.B1
    final int a(B1 b1, com.android.tools.r8.utils.structural.a aVar) {
        return aVar.a(this, (C0418b3) b1, new b3$$ExternalSyntheticLambda3());
    }

    @Override // com.android.tools.r8.dex.code.R2, com.android.tools.r8.dex.code.B1
    public final String b(com.android.tools.r8.utils.P2 p2) {
        return a(p2, (B1) null);
    }

    @Override // com.android.tools.r8.dex.code.R2, com.android.tools.r8.dex.code.B1
    public final void a(C0645e5 c0645e5, o5 o5Var, AbstractC1476Wj abstractC1476Wj, AbstractC1476Wj abstractC1476Wj2, C2363kB c2363kB, ShortBuffer shortBuffer) {
        B1.a(1, shortBuffer, 0);
        shortBuffer.put((short) this.e);
        B1.a(this.f, shortBuffer);
        for (int i = 0; i < this.e; i++) {
            B1.a(this.g[i], shortBuffer);
        }
    }

    public C0418b3(int[] iArr, int i) {
        if (!h && iArr.length <= 0) {
            throw new AssertionError();
        }
        this.e = iArr.length;
        this.f = i;
        this.g = iArr;
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final String a(com.android.tools.r8.utils.P2 p2, B1 b1) {
        StringBuilder sb = new StringBuilder("[PackedSwitchPayload");
        if (b1 == null) {
            sb.append(" offsets relative to associated PackedSwitch");
        }
        sb.append("]\n");
        for (int i = 0; i < this.e; i++) {
            com.android.tools.r8.utils.Y2.a(sb, (this.f + i) + " -> " + (b1 != null ? B1.d(b1.u() + this.g[i]) : B1.c(this.g[i])) + "\n", 20);
        }
        return super.b(p2) + sb.toString();
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final String a(B1 b1) {
        StringBuilder sb = new StringBuilder("    .packed-switch ");
        sb.append(com.android.tools.r8.utils.Y2.a(this.f, 8));
        sb.append("  # ");
        sb.append(this.f);
        sb.append("\n");
        for (int i : this.g) {
            sb.append("      :label_");
            sb.append(b1.u() + i);
            sb.append("\n");
        }
        sb.append("    .end packed-switch");
        return sb.toString();
    }
}
