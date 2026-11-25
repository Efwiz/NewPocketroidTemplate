package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import java.util.ArrayList;

/* JADX INFO: renamed from: com.android.tools.r8.internal.jM, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2300jM implements InterfaceC2697oM {
    private final C2937rf a;
    private final C3076tf b;
    private final InterfaceC2606nM c;

    private C2300jM(C0702m c0702m) {
        InterfaceC2606nM c2378kM = c0702m.Y().U() ? new C2378kM() : new C2531mM();
        this.c = c2378kM;
        this.a = new C2937rf(c0702m);
        this.b = new C3076tf(c0702m, c2378kM);
    }

    @Override // com.android.tools.r8.internal.InterfaceC2697oM
    public final ArrayList a(com.android.tools.r8.graph.o5 o5Var, C2067gM c2067gM, boolean z, boolean z2, int i) {
        return z2 ? this.b.a(o5Var, c2067gM, i) : this.a.a(o5Var, c2067gM);
    }

    @Override // com.android.tools.r8.internal.InterfaceC2697oM
    public final void a() {
        this.c.a();
    }
}
