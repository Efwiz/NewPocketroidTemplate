package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import java.util.IdentityHashMap;
import java.util.Map;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class G2 {
    static final /* synthetic */ boolean e = true;
    private final C0702m a;
    private final Q4 b = new Q4();
    private final Q4 c = new Q4();
    private final IdentityHashMap d = new IdentityHashMap();

    G2(C0702m c0702m) {
        this.a = c0702m;
    }

    public final H2 a() {
        if (this.b.isEmpty() && this.c.isEmpty() && this.d.isEmpty()) {
            return null;
        }
        H2 h2 = new H2(this.a, this.b, this.c, this.d);
        for (Map.Entry entry : this.d.entrySet()) {
            com.android.tools.r8.graph.proto.k kVar = (com.android.tools.r8.graph.proto.k) entry.getValue();
            com.android.tools.r8.graph.proto.k kVarA = kVar.a(this.a, h2, h2.k());
            if (kVarA != kVar) {
                entry.setValue(kVarA);
            }
        }
        return h2;
    }

    public final G2 a(G2 g2) {
        this.b.a(g2.b);
        this.c.a(g2.c);
        this.d.putAll(g2.d);
        return this;
    }

    public final void a(com.android.tools.r8.graph.Y0 y0, com.android.tools.r8.graph.Y0 y1) {
        if (!e && y0 == y1) {
            throw new AssertionError();
        }
        this.b.put(y0, y1);
    }

    public final void a(C0684j2 c0684j2, C0684j2 c0684j3, com.android.tools.r8.graph.proto.k kVar) {
        boolean z = e;
        if (!z && c0684j2 == c0684j3) {
            throw new AssertionError();
        }
        this.c.put(c0684j2, c0684j3);
        if (!kVar.g()) {
            this.d.put(c0684j3, kVar);
        }
        if (!z && !c0684j2.I0().Y0() && c0684j3.I0().Y0() && !kVar.f()) {
            throw new AssertionError();
        }
    }

    public final void a(C0684j2 c0684j2, com.android.tools.r8.graph.proto.k kVar) {
        this.d.put(c0684j2, kVar);
    }
}
