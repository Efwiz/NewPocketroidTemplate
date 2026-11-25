package com.android.tools.r8.synthesis;

import com.android.tools.r8.graph.AbstractC0744s2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.internal.DH;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract class U {
    static final /* synthetic */ boolean c = true;
    private final L.b a;
    private final C3846e b;

    U(L.b bVar, C3846e c3846e) {
        boolean z = c;
        if (!z && bVar == null) {
            throw new AssertionError();
        }
        if (!z && c3846e == null) {
            throw new AssertionError();
        }
        this.a = bVar;
        this.b = c3846e;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract C0767v2 a();

    abstract U a(C3846e c3846e, DH dh);

    public /* bridge */ /* synthetic */ InterfaceC3845d a(DH dh) {
        return (InterfaceC3845d) b(dh);
    }

    abstract AbstractC3855m a(Function function);

    final C3846e b() {
        return this.b;
    }

    final L.b c() {
        return this.a;
    }

    abstract AbstractC0744s2 d();

    public final U b(DH dh) {
        return a(this.b.a(dh), dh);
    }
}
