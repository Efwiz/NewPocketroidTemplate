package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.internal.jX, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2311jX implements InterfaceC2390kX {
    static final /* synthetic */ boolean d = true;
    private final S10 a;
    private final S10 b;
    private final String c;

    private C2311jX(S10 s10, S10 s11, String str) {
        boolean z = d;
        if (!z && s10 == null && str == null) {
            throw new AssertionError();
        }
        if (!z && s11 == null && str == null) {
            throw new AssertionError();
        }
        this.a = s10;
        this.b = s11;
        this.c = str;
    }

    public static C2311jX a(S10 s10, S10 s11) {
        return new C2311jX(s10, s11, null);
    }

    public static C2311jX b(S10 s10, String str) {
        return new C2311jX(s10, null, str);
    }

    public static C2311jX a(S10 s10, String str) {
        return new C2311jX(null, s10, str);
    }

    @Override // com.android.tools.r8.internal.InterfaceC2390kX
    public final void a(C0702m c0702m, C2409kl c2409kl, C2561ml c2561ml, AbstractC2412ko abstractC2412ko, QX qx) {
        S10 s10A;
        if (this.c != null) {
            s10A = InterfaceC2390kX.a(c0702m, c2409kl, c2561ml, c2561ml.previous(), this.c);
            c2561ml.next();
        } else {
            s10A = null;
        }
        boolean z = d;
        if (!z && this.a == null && s10A == null) {
            throw new AssertionError();
        }
        if (!z && this.b == null && s10A == null) {
            throw new AssertionError();
        }
        int i = C2115gt.m;
        C2030ft c2030ft = new C2030ft();
        int i2 = AbstractC3472z00.b;
        C2030ft c2030ft2 = (C2030ft) ((C2030ft) c2030ft.a(c2409kl, AbstractC3472z00.a(c0702m.a().X1, WH.i(), (C0702m<?>) c0702m).b(), abstractC2412ko.j())).a(c0702m.a().k4.d);
        S10 s10 = this.a;
        if (s10 == null) {
            s10 = s10A;
        }
        S10 s11 = this.b;
        if (s11 != null) {
            s10A = s11;
        }
        c2561ml.a(((C2030ft) c2030ft2.a(AbstractC1639am.a(s10, s10A))).b(), (Set) null);
    }
}
