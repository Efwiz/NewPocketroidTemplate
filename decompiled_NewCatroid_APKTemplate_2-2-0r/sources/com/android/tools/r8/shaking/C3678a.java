package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.B5;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.InterfaceC0694k5;
import com.android.tools.r8.graph.o5;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.shaking.a, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3678a {
    static final /* synthetic */ boolean d = true;
    private final C0702m a;
    private final B5 b;
    private h4 c = new h4();

    public C3678a(C0702m c0702m, B5 b5) {
        this.a = c0702m;
        this.b = b5;
    }

    public final void a() {
        if (!d && this.c.a() != null) {
            throw new AssertionError();
        }
        a(this.a.a().Z1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C0767v2 c0767v2) {
        AbstractC0727q0 abstractC0727q0C = this.a.c(c0767v2);
        this.c = this.c.b();
        if (abstractC0727q0C != null && abstractC0727q0C.e0()) {
            C0729q2 c0729q2A0 = abstractC0727q0C.a0();
            HashSet hashSet = null;
            for (o5 o5Var : c0729q2A0.k2()) {
                if (o5Var.getAccessFlags().W() && this.c.a(o5Var.e()) == 1 && !((C3733i) this.a.f()).c((InterfaceC0694k5) o5Var)) {
                    if (hashSet == null) {
                        hashSet = new HashSet();
                    }
                    hashSet.add(o5Var.e());
                }
            }
            if (hashSet != null) {
                c0729q2A0.Y().a((Set) hashSet);
            }
        }
        this.b.a(c0767v2, new Consumer() { // from class: com.android.tools.r8.shaking.a$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((C0767v2) obj);
            }
        });
        this.c = this.c.a();
    }
}
