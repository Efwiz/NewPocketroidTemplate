package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import java.util.ArrayList;

/* JADX INFO: renamed from: com.android.tools.r8.internal.gX, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2087gX implements InterfaceC2390kX {
    static final /* synthetic */ boolean f = true;
    private final S10 a;
    private final S10 b;
    private final String c;
    private final S10 d;
    private boolean e;

    private C2087gX(S10 s10, S10 s11, String str, S10 s12) {
        boolean z = f;
        if (!z && s10 == null && str == null) {
            throw new AssertionError();
        }
        if (!z && s11 == null && str == null) {
            throw new AssertionError();
        }
        this.a = s10;
        this.b = s11;
        this.c = str;
        this.d = s12;
    }

    public static C2087gX b(S10 s10, S10 s11, String str) {
        return new C2087gX(s10, null, str, s11);
    }

    @Override // com.android.tools.r8.internal.InterfaceC2390kX
    public final boolean a() {
        return true;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2390kX
    public final C2087gX c() {
        return this;
    }

    public final void d() {
        this.e = true;
    }

    public static C2087gX a(S10 s10, S10 s11, S10 s12) {
        return new C2087gX(s10, s11, null, s12);
    }

    public static C2087gX a(S10 s10, S10 s11, String str) {
        return new C2087gX(null, s10, str, s11);
    }

    @Override // com.android.tools.r8.internal.InterfaceC2390kX
    public final void a(C0702m c0702m, C2409kl c2409kl, C2561ml c2561ml, AbstractC2412ko abstractC2412ko, QX qx) {
        boolean z = f;
        if (!z && !abstractC2412ko.Y1()) {
            throw new AssertionError();
        }
        if (!z && ((ArrayList) abstractC2412ko.h1()).size() != 2) {
            throw new AssertionError();
        }
        AbstractC2412ko abstractC2412koPrevious = c2561ml.previous();
        if (!z && abstractC2412koPrevious != abstractC2412ko) {
            throw new AssertionError();
        }
        String str = this.c;
        S10 s10A = str != null ? InterfaceC2390kX.a(c0702m, c2409kl, c2561ml, abstractC2412koPrevious, str) : null;
        if (!z && this.a == null && s10A == null) {
            throw new AssertionError();
        }
        if (!z && this.b == null && s10A == null) {
            throw new AssertionError();
        }
        int i = C2115gt.m;
        C2030ft c2030ft = (C2030ft) ((C2030ft) new C2030ft().a(c0702m.a().k4.d)).a(this.d);
        S10 s10 = this.a;
        if (s10 == null) {
            s10 = s10A;
        }
        S10 s11 = this.b;
        if (s11 != null) {
            s10A = s11;
        }
        c2561ml.add(((C2030ft) ((C2030ft) c2030ft.a(AbstractC1639am.a(s10, s10A))).a(abstractC2412ko.Y0())).b());
        AbstractC2412ko abstractC2412ko2 = (AbstractC2412ko) c2561ml.next();
        if (!z && abstractC2412ko2 != abstractC2412ko) {
            throw new AssertionError();
        }
        if (this.e) {
            InterfaceC2390kX.a(c2561ml, abstractC2412ko, qx);
        } else {
            abstractC2412ko.a(1, this.d);
        }
    }
}
