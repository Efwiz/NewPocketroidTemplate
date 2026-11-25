package com.android.tools.r8.dex.code;

import com.android.tools.r8.graph.C0645e5;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.internal.AbstractC1476Wj;
import com.android.tools.r8.internal.C2102gl;
import com.android.tools.r8.internal.C2363kB;
import java.nio.ShortBuffer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class R2 extends F0 {
    R2(int i, InterfaceC0409a interfaceC0409a) {
        super(interfaceC0409a);
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final String A() {
        return "nop";
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final /* bridge */ /* synthetic */ void a(C0702m c0702m, com.android.tools.r8.dex.L l, C2363kB c2363kB, o5 o5Var) {
    }

    @Override // com.android.tools.r8.dex.code.B1
    public void a(C2102gl c2102gl) {
    }

    @Override // com.android.tools.r8.dex.code.B1
    void b(com.android.tools.r8.utils.structural.o oVar) {
    }

    @Override // com.android.tools.r8.dex.code.B1
    public int hashCode() {
        return 549577;
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final String t() {
        return "Nop";
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final int v() {
        return 0;
    }

    public R2() {
    }

    @Override // com.android.tools.r8.dex.code.B1
    public String b(com.android.tools.r8.utils.P2 p2) {
        return b((String) null);
    }

    @Override // com.android.tools.r8.dex.code.B1
    int a(B1 b1, com.android.tools.r8.utils.structural.a aVar) {
        boolean z = W.a;
        if (!z && getClass() != b1.getClass()) {
            throw new AssertionError();
        }
        if (!z && r() != b1.r()) {
            throw new AssertionError();
        }
        if (z || toString().equals(b1.toString())) {
            return 0;
        }
        throw new AssertionError();
    }

    public static R2 a(int i, InterfaceC0409a interfaceC0409a) {
        if (i == 1) {
            return new C0418b3(i, interfaceC0409a);
        }
        if (i == 2) {
            return new N3(i, interfaceC0409a);
        }
        if (i != 3) {
            return new R2(i, interfaceC0409a);
        }
        return new C0529y0(i, interfaceC0409a);
    }

    @Override // com.android.tools.r8.dex.code.B1
    public String a(com.android.tools.r8.utils.P2 p2) {
        return a((String) null);
    }

    @Override // com.android.tools.r8.dex.code.B1
    public void a(C0645e5 c0645e5, o5 o5Var, AbstractC1476Wj abstractC1476Wj, AbstractC1476Wj abstractC1476Wj2, C2363kB c2363kB, ShortBuffer shortBuffer) {
        B1.a(0, shortBuffer, v());
    }
}
