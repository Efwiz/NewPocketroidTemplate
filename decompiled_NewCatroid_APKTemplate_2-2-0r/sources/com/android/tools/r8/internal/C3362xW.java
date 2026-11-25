package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0744s2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.utils.InterfaceC3905h3;
import java.io.IOException;

/* JADX INFO: renamed from: com.android.tools.r8.internal.xW, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C3362xW extends CW implements InterfaceC2041g1 {
    private final C0767v2 a;

    C3362xW(C0767v2 c0767v2) {
        this.a = c0767v2;
    }

    public static C3293wW d() {
        return new C3293wW(null);
    }

    @Override // com.android.tools.r8.internal.CW
    public final void a(InterfaceC3905h3 interfaceC3905h3, InterfaceC3905h3 interfaceC3905h4) throws Throwable {
        interfaceC3905h3.accept(this);
    }

    @Override // com.android.tools.r8.internal.CW
    public final AbstractC0744s2 c() {
        return this.a;
    }

    public final C0767v2 e() {
        return this.a;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && getClass() == obj.getClass() && this.a == ((C3362xW) obj).a;
    }

    public final int hashCode() {
        return this.a.hashCode();
    }

    @Override // com.android.tools.r8.internal.CW
    public final void a(Appendable appendable) throws IOException {
        appendable.append(this.a.b1());
    }
}
