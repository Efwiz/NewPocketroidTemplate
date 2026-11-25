package com.android.tools.r8.dex.code;

import com.android.dx.rop.code.RegisterSpec;
import com.android.tools.r8.graph.C0645e5;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.internal.AbstractC1476Wj;
import com.android.tools.r8.internal.C2363kB;
import java.nio.ShortBuffer;

/* JADX INFO: Access modifiers changed from: package-private */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class H0 extends J {
    static final /* synthetic */ boolean f = true;
    public final short e;

    H0(int i, InterfaceC0409a interfaceC0409a) {
        super(interfaceC0409a);
        this.e = (short) i;
    }

    @Override // com.android.tools.r8.dex.code.B1
    public void a(C0645e5 c0645e5, o5 o5Var, AbstractC1476Wj abstractC1476Wj, AbstractC1476Wj abstractC1476Wj2, C2363kB c2363kB, ShortBuffer shortBuffer) {
        B1.a(this.e, shortBuffer, v());
    }

    @Override // com.android.tools.r8.dex.code.B1
    final void b(com.android.tools.r8.utils.structural.o oVar) {
        oVar.a((int) this.e);
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final int hashCode() {
        return this.e ^ getClass().hashCode();
    }

    @Override // com.android.tools.r8.dex.code.B1
    public String b(com.android.tools.r8.utils.P2 p2) {
        return b(RegisterSpec.PREFIX + ((int) this.e));
    }

    protected H0(int i) {
        if (!f && (i < 0 || i > 255)) {
            throw new AssertionError();
        }
        this.e = (short) i;
    }

    @Override // com.android.tools.r8.dex.code.B1
    final int a(B1 b1, com.android.tools.r8.utils.structural.a aVar) {
        return aVar.a((int) this.e, (int) ((H0) b1).e);
    }

    @Override // com.android.tools.r8.dex.code.B1
    public String a(com.android.tools.r8.utils.P2 p2) {
        return a(RegisterSpec.PREFIX + ((int) this.e));
    }
}
