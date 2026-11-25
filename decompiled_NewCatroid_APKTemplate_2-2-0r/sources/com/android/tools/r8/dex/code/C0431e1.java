package com.android.tools.r8.dex.code;

import com.android.tools.r8.graph.C0645e5;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.internal.AbstractC1476Wj;
import com.android.tools.r8.internal.C2102gl;
import com.android.tools.r8.internal.C2363kB;
import java.nio.ShortBuffer;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;

/* JADX INFO: renamed from: com.android.tools.r8.dex.code.e1, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C0431e1 extends E0 {
    C0431e1(int i, InterfaceC0409a interfaceC0409a) {
        super(i, interfaceC0409a);
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final String A() {
        return PsiKeyword.GOTO;
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final int[] B() {
        return new int[]{this.e};
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final /* bridge */ /* synthetic */ void a(C0702m c0702m, com.android.tools.r8.dex.L l, C2363kB c2363kB, o5 o5Var) {
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final String b(com.android.tools.r8.utils.P2 p2) {
        return b(f(this.e));
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final String t() {
        return "Goto";
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final int v() {
        return 40;
    }

    public C0431e1(int i) {
        super(i);
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final void a(C0645e5 c0645e5, o5 o5Var, AbstractC1476Wj abstractC1476Wj, AbstractC1476Wj abstractC1476Wj2, C2363kB c2363kB, ShortBuffer shortBuffer) {
        B1.a(this.e, shortBuffer, v());
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final void a(C2102gl c2102gl) {
        c2102gl.b(u() + this.e);
    }
}
