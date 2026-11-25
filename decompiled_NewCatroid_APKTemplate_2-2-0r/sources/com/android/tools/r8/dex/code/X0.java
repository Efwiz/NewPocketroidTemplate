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
public abstract class X0 extends L {
    static final /* synthetic */ boolean g = true;
    public final short e;
    protected int f;

    X0(int i, InterfaceC0409a interfaceC0409a) {
        super(interfaceC0409a);
        this.e = (short) i;
        this.f = B1.f(interfaceC0409a);
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final boolean D() {
        return true;
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final void a(C0702m c0702m, com.android.tools.r8.dex.L l, C2363kB c2363kB, o5 o5Var) {
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final int hashCode() {
        return ((this.f << 8) | this.e) ^ getClass().hashCode();
    }

    public final void j(int i) {
        this.f = i;
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final int x() {
        return this.f;
    }

    @Override // com.android.tools.r8.dex.code.B1
    final void b(com.android.tools.r8.utils.structural.o oVar) {
        oVar.a(this, new X0$$ExternalSyntheticLambda2());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(com.android.tools.r8.utils.structural.z zVar) {
        zVar.a(new ToIntFunction() { // from class: com.android.tools.r8.dex.code.X0$$ExternalSyntheticLambda0
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return ((X0) obj).e;
            }
        }).a(new ToIntFunction() { // from class: com.android.tools.r8.dex.code.X0$$ExternalSyntheticLambda1
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return ((X0) obj).f;
            }
        });
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final String b(com.android.tools.r8.utils.P2 p2) {
        return b(RegisterSpec.PREFIX + ((int) this.e) + ", " + f(this.f));
    }

    X0(int i) {
        if (!g && (i < 0 || i > 255)) {
            throw new AssertionError();
        }
        this.e = (short) i;
        this.f = -1;
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final void a(C0645e5 c0645e5, o5 o5Var, AbstractC1476Wj abstractC1476Wj, AbstractC1476Wj abstractC1476Wj2, C2363kB c2363kB, ShortBuffer shortBuffer) {
        B1.a(this.e, shortBuffer, v());
        if (!g && (u() + this.f) % 2 != 0) {
            throw new AssertionError();
        }
        B1.a(this.f, shortBuffer);
    }

    @Override // com.android.tools.r8.dex.code.B1
    final int a(B1 b1, com.android.tools.r8.utils.structural.a aVar) {
        return aVar.a(this, (X0) b1, new X0$$ExternalSyntheticLambda2());
    }
}
