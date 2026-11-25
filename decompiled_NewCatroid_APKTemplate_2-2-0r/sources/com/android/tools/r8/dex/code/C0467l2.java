package com.android.tools.r8.dex.code;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.internal.C2102gl;
import com.android.tools.r8.internal.C2363kB;
import com.android.tools.r8.internal.C3344xF;
import com.android.tools.r8.internal.EnumC1968f20;
import com.android.tools.r8.internal.EnumC3423yF;

/* JADX INFO: renamed from: com.android.tools.r8.dex.code.l2, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C0467l2 extends H0 {
    C0467l2(int i, InterfaceC0409a interfaceC0409a) {
        super(i, interfaceC0409a);
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final String A() {
        return "monitor-enter";
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final /* bridge */ /* synthetic */ void a(C0702m c0702m, com.android.tools.r8.dex.L l, C2363kB c2363kB, o5 o5Var) {
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final boolean l() {
        return true;
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final String t() {
        return "MonitorEnter";
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final int v() {
        return 29;
    }

    public C0467l2(int i) {
        super(i);
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final void a(C2102gl c2102gl) {
        c2102gl.a(new C3344xF(EnumC3423yF.ENTER, c2102gl.b(this.e, EnumC1968f20.OBJECT)));
    }
}
