package com.android.tools.r8.synthesis;

import com.android.tools.r8.graph.AbstractC0681j0;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.internal.AbstractC1639am;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.synthesis.a, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3842a implements InterfaceC3856n {
    static final /* synthetic */ boolean f = true;
    final AbstractC0681j0 a;
    final int b;
    final C3844c c;
    final AbstractC1639am d;
    final y e;

    C3842a(int i, AbstractC0681j0 abstractC0681j0, C3844c c3844c, AbstractC1639am abstractC1639am, y yVar) {
        this.b = i;
        this.a = abstractC0681j0;
        this.c = c3844c;
        this.d = abstractC1639am;
        this.e = yVar;
        if (f) {
            return;
        }
        c3844c.a(abstractC0681j0);
    }

    public final AbstractC0681j0 a() {
        return this.a;
    }

    public final AbstractC1639am b() {
        return this.d;
    }

    public final D c() {
        return new D(this);
    }

    @Override // com.android.tools.r8.synthesis.InterfaceC3856n
    public final com.android.tools.r8.graph.O a(Function function, C0767v2 c0767v2) {
        return (com.android.tools.r8.graph.O) function.apply(c0767v2);
    }
}
