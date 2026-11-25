package com.android.tools.r8.dex;

import com.android.tools.r8.graph.C0633d0;
import com.android.tools.r8.graph.C0640e0;
import com.android.tools.r8.graph.C0658g0;
import com.android.tools.r8.graph.C0663g5;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0800x2;
import com.android.tools.r8.graph.M0;
import com.android.tools.r8.graph.S0;
import com.android.tools.r8.graph.W0;
import com.android.tools.r8.internal.C3505zR;
import com.android.tools.r8.internal.DI;
import com.android.tools.r8.internal.InterfaceC2842qK;
import com.android.tools.r8.internal.InterfaceC3506zS;
import com.android.tools.r8.utils.A1;
import java.util.IdentityHashMap;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class I extends V {
    static final /* synthetic */ boolean n = true;
    private final C3505zR a;
    private final DI b;
    private final DI c;
    private final C3505zR d;
    private final DI e;
    private final DI f;
    private final DI g;
    private final DI h;
    private final C3505zR i;
    private final DI j;
    private final IdentityHashMap k;
    private final IdentityHashMap l;
    private final A1 m;

    private I(A1 a1) {
        this.a = b();
        this.b = a();
        this.c = a();
        this.d = b();
        this.e = a();
        this.f = a();
        this.g = a();
        this.h = a();
        this.i = b();
        this.j = a();
        this.k = new IdentityHashMap();
        this.l = new IdentityHashMap();
        this.m = a1;
    }

    private static DI a() {
        DI di = new DI(0);
        di.e(-2);
        return di;
    }

    private static C3505zR b() {
        C3505zR c3505zR = new C3505zR(16);
        c3505zR.e(-2);
        return c3505zR;
    }

    public final InterfaceC2842qK c() {
        return this.h.keySet();
    }

    public final InterfaceC2842qK d() {
        return this.g.keySet();
    }

    public final InterfaceC2842qK e() {
        return this.f.keySet();
    }

    public final InterfaceC2842qK f() {
        return this.e.keySet();
    }

    public final InterfaceC3506zS g() {
        return this.i.keySet();
    }

    public final InterfaceC3506zS h() {
        return this.a.keySet();
    }

    public final InterfaceC2842qK i() {
        return this.j.keySet();
    }

    public final InterfaceC3506zS j() {
        return this.d.keySet();
    }

    public final InterfaceC2842qK k() {
        return this.c.keySet();
    }

    final S0 c(C0729q2 c0729q2) {
        return (S0) this.l.get(c0729q2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean a(DI di, Object obj) {
        if (di.containsKey(obj)) {
            return false;
        }
        di.a(-1, obj);
        return true;
    }

    @Override // com.android.tools.r8.dex.V
    public final void b(W0 w0) {
        w0.b(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int b(DI di, Object obj) {
        if (obj == null) {
            return 0;
        }
        int iA = di.a(obj);
        if (n || !(iA == -1 || iA == -2)) {
            return iA;
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.dex.V
    public final boolean a(C0729q2 c0729q2) {
        C3505zR c3505zR = this.i;
        if (c3505zR.containsKey(c0729q2)) {
            return false;
        }
        c3505zR.a(-1, c0729q2);
        return true;
    }

    final C0640e0 b(C0729q2 c0729q2) {
        return (C0640e0) this.k.get(c0729q2);
    }

    @Override // com.android.tools.r8.dex.V
    public final boolean a(S0 s0) {
        return a(this.j, s0);
    }

    @Override // com.android.tools.r8.dex.V
    public final boolean a(C0658g0 c0658g0) {
        if (this.m.l() || !c0658g0.isEmpty()) {
            return a(this.f, c0658g0);
        }
        return false;
    }

    @Override // com.android.tools.r8.dex.V
    public final boolean a(W0 w0) {
        C3505zR c3505zR = this.a;
        if (c3505zR.containsKey(w0)) {
            return false;
        }
        c3505zR.a(-1, w0);
        return true;
    }

    @Override // com.android.tools.r8.dex.V
    public final boolean a(M0 m0) {
        return a(this.b, m0);
    }

    @Override // com.android.tools.r8.dex.V
    public final boolean a(C0800x2 c0800x2) {
        if (c0800x2.isEmpty()) {
            return false;
        }
        return a(this.c, c0800x2);
    }

    @Override // com.android.tools.r8.dex.V
    public final boolean a(C0663g5 c0663g5) {
        if (c0663g5.isEmpty()) {
            return false;
        }
        return a(this.g, c0663g5);
    }

    @Override // com.android.tools.r8.dex.V
    public final boolean a(C0633d0 c0633d0) {
        return a(this.e, c0633d0);
    }

    @Override // com.android.tools.r8.dex.V
    public final void a(C0729q2 c0729q2, C0640e0 c0640e0) {
        C0640e0 c0640e1 = (C0640e0) this.k.put(c0729q2, c0640e0);
        if (!n && c0640e1 != null) {
            throw new AssertionError();
        }
        a(this.h, c0640e0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(Object obj, int i, DI di) {
        int iA = di.a(i, obj);
        if (!n && iA > -1) {
            throw new AssertionError();
        }
    }
}
