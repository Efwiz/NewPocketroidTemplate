package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.InterfaceC0694k5;
import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Sz, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1365Sz implements com.android.tools.r8.graph.Q0 {
    private final InterfaceC0694k5 a;
    private final com.android.tools.r8.shaking.G b;
    private final Set c;
    final /* synthetic */ C1393Tz d;

    private C1365Sz(C1393Tz c1393Tz, InterfaceC0694k5 interfaceC0694k5, com.android.tools.r8.shaking.G g, Set set) {
        this.d = c1393Tz;
        this.a = interfaceC0694k5;
        this.b = g;
        this.c = set;
    }

    @Override // com.android.tools.r8.graph.Q0
    public final C0722p1 a() {
        return this.d.a.a();
    }

    @Override // com.android.tools.r8.graph.Q0
    public final AbstractC0727q0 c(C0767v2 c0767v2) {
        Set set = this.c;
        if (set == null || !set.contains(c0767v2)) {
            return this.b.a(c0767v2, this.a);
        }
        return null;
    }

    @Override // com.android.tools.r8.graph.Q0
    public final com.android.tools.r8.graph.O f(C0767v2 c0767v2) {
        throw new C3405y10("Not yet used");
    }
}
