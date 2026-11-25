package com.android.tools.r8.naming;

import com.android.tools.r8.DiagnosticsHandler;
import com.android.tools.r8.MapIdProvider;
import com.android.tools.r8.utils.A1;
import com.android.tools.r8.utils.O2;
import java.util.Objects;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class M0 {
    static final /* synthetic */ boolean f = true;
    private final C3605b a;
    private final A1 b;
    private final com.android.tools.r8.T c;
    private final O2 d;
    private final com.android.tools.r8.dex.U.b e;

    private M0(C3605b c3605b, com.android.tools.r8.dex.U.b bVar, A1 a1) {
        if (!f && c3605b == null) {
            throw new AssertionError();
        }
        this.a = c3605b.g();
        this.c = a1.x1;
        this.b = a1;
        this.d = a1.e;
        this.e = bVar;
    }

    public static M0 a(C3605b c3605b, A1 a1) {
        if (f || a1.m0 != null) {
            return new M0(c3605b, a1.m0, a1);
        }
        throw new AssertionError();
    }

    public final J0 a() {
        L0 l0 = new L0();
        this.a.a(l0);
        MapIdProvider mapIdProvider = this.b.J1;
        String string = l0.a.a().toString();
        J0 j0 = new J0(L0.a(mapIdProvider).get(new K0(string)), string);
        this.c.a(new x0().a(this.e.name()).a(j0).a(this.b.Y0()).a(this.b.B0()).a(this.b.z0()).a(), this.a);
        O2 o2 = this.d;
        final com.android.tools.r8.T t = this.c;
        Objects.requireNonNull(t);
        com.android.tools.r8.utils.R0.a(o2, new Consumer() { // from class: com.android.tools.r8.naming.M0$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                t.finished((DiagnosticsHandler) obj);
            }
        });
        return j0;
    }
}
