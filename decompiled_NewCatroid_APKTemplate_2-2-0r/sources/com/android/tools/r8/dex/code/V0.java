package com.android.tools.r8.dex.code;

import com.android.dx.rop.code.RegisterSpec;
import com.android.tools.r8.graph.C0645e5;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.internal.AbstractC1476Wj;
import com.android.tools.r8.internal.C2363kB;
import java.nio.ShortBuffer;
import java.util.function.Function;
import java.util.function.ToIntFunction;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract class V0 extends L {
    static final /* synthetic */ boolean g = true;
    public final short e;
    public C0759u2 f;

    V0(int i, InterfaceC0409a interfaceC0409a, C0759u2[] c0759u2Arr) {
        super(interfaceC0409a);
        this.e = (short) i;
        this.f = c0759u2Arr[(int) B1.b(interfaceC0409a)];
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final int hashCode() {
        return ((this.f.hashCode() << 8) | this.e) ^ getClass().hashCode();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(com.android.tools.r8.utils.structural.z zVar) {
        zVar.a(new ToIntFunction() { // from class: com.android.tools.r8.dex.code.V0$$ExternalSyntheticLambda1
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return ((V0) obj).e;
            }
        }).c(new Function() { // from class: com.android.tools.r8.dex.code.V0$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((V0) obj).f;
            }
        });
    }

    @Override // com.android.tools.r8.dex.code.B1
    final void b(com.android.tools.r8.utils.structural.o oVar) {
        oVar.a(this, new V0$$ExternalSyntheticLambda0());
    }

    @Override // com.android.tools.r8.dex.code.B1
    public void a(C0645e5 c0645e5, o5 o5Var, AbstractC1476Wj abstractC1476Wj, AbstractC1476Wj abstractC1476Wj2, C2363kB c2363kB, ShortBuffer shortBuffer) {
        B1.a(this.e, shortBuffer, v());
        C0759u2 c0759u2 = this.f;
        c0759u2.getClass();
        B1.a(c0645e5.a(c0759u2), shortBuffer);
    }

    @Override // com.android.tools.r8.dex.code.B1
    public String b(com.android.tools.r8.utils.P2 p2) {
        return b(RegisterSpec.PREFIX + ((int) this.e) + ", " + p2.a(this.f));
    }

    V0(int i, C0759u2 c0759u2) {
        if (!g && (i < 0 || i > 255)) {
            throw new AssertionError();
        }
        this.e = (short) i;
        this.f = c0759u2;
    }

    @Override // com.android.tools.r8.dex.code.B1
    final int a(B1 b1, com.android.tools.r8.utils.structural.a aVar) {
        return aVar.a(this, (V0) b1, new V0$$ExternalSyntheticLambda0());
    }

    @Override // com.android.tools.r8.dex.code.B1
    public void a(C0702m c0702m, com.android.tools.r8.dex.L l, C2363kB c2363kB, o5 o5Var) {
        C0759u2 c0759u2 = this.f;
        c0759u2.getClass();
        l.a(c0759u2);
    }
}
