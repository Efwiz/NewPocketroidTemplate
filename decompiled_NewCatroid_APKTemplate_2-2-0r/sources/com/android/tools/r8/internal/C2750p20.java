package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.shaking.C3733i;
import java.util.Map;
import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.internal.p20, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C2750p20 implements InterfaceC2765pE {
    static final /* synthetic */ boolean c = true;
    private final L4 a;
    private final L4 b;

    public C2750p20(L4 l4, L4 l5) {
        this.a = l4;
        this.b = l5;
    }

    public static C2750p20 a() {
        C1371Tf c1371Tf = new C1371Tf();
        return new C2750p20(c1371Tf, c1371Tf);
    }

    public final Map b() {
        return this.a.e();
    }

    public final C0767v2 c(C0767v2 c0767v2) {
        if (c || this.a.containsKey(c0767v2)) {
            return (C0767v2) this.a.get(c0767v2);
        }
        throw new AssertionError();
    }

    public boolean d(C0767v2 c0767v2) {
        return this.a.containsKey(c0767v2);
    }

    public final boolean e(C0767v2 c0767v2) {
        return this.b.containsKey(c0767v2);
    }

    public final boolean f(C0767v2 c0767v2) {
        return !this.a.a(c0767v2).isEmpty();
    }

    public final Set b(C0767v2 c0767v2) {
        return this.a.a(c0767v2);
    }

    public boolean c() {
        return this.a.isEmpty();
    }

    public final C0767v2 a(C0767v2 c0767v2, C0767v2 c0767v3) {
        return (C0767v2) this.a.getOrDefault(c0767v2, c0767v3);
    }

    @Override // com.android.tools.r8.internal.InterfaceC2765pE
    public final boolean a(C0767v2 c0767v2) {
        return d(c0767v2);
    }

    @Override // com.android.tools.r8.internal.InterfaceC2765pE
    public final void a(C0702m c0702m) {
        for (C0767v2 c0767v2 : this.a.keySet()) {
            if (!c && !((C3733i) c0702m.f()).p(c0767v2)) {
                throw new AssertionError("Expected vertically merged class `" + c0767v2.r0() + "` to be absent");
            }
        }
    }
}
