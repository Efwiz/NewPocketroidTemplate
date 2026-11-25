package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.AbstractC0748t0;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.shaking.C3733i;
import java.util.ArrayList;
import java.util.Set;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.mn, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2563mn extends AbstractC1855de {
    static final /* synthetic */ boolean d = true;
    private final C0702m a;
    private final com.android.tools.r8.graph.o5 b;
    private final Set c = OU.c();

    C2563mn(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var) {
        this.a = c0702m;
        this.b = o5Var;
    }

    private void a(Set set) {
        set.forEach(new Consumer() { // from class: com.android.tools.r8.internal.mn$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((C0767v2) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C0767v2 c0767v2) {
        AbstractC0727q0 abstractC0727q0C;
        if (c0767v2 != this.b.t() && (abstractC0727q0C = this.a.c(c0767v2)) != null && abstractC0727q0C.e0() && abstractC0727q0C.b(this.a)) {
            ArrayList arrayList = null;
            for (C0767v2 c0767v3 : this.c) {
                if (this.a.a(c0767v3, c0767v2).d()) {
                    return;
                }
                if (this.a.a(c0767v2, c0767v3).d()) {
                    if (arrayList == null) {
                        arrayList = new ArrayList();
                    }
                    arrayList.add(c0767v3);
                }
            }
            this.c.add(c0767v2);
            if (arrayList != null) {
                this.c.removeAll(arrayList);
            }
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC1855de
    public final void a(AbstractC1182Mh abstractC1182Mh) {
        com.android.tools.r8.graph.T0 t0S = ((C3733i) this.a.f()).c(abstractC1182Mh.getField()).s();
        if (t0S != null) {
            if (t0S.J0().O0()) {
                a(t0S.J0());
            } else if (!d) {
                throw new AssertionError("Expected holder of field type to be a class type");
            }
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC1855de
    public final void a(AbstractC1084Is abstractC1084Is) {
        if (abstractC1084Is.Y1()) {
            AbstractC1305Qs abstractC1305QsB0 = abstractC1084Is.b0();
            C0684j2 c0684j2S2 = abstractC1305QsB0.S2();
            if (c0684j2S2.e.O0()) {
                AbstractC0748t0 abstractC0748t0H = abstractC1305QsB0.h(this.a, this.b);
                if (abstractC0748t0H != null) {
                    a(abstractC0748t0H.t());
                    a(abstractC0748t0H.e().i1().o());
                } else {
                    a(c0684j2S2.B0());
                }
            }
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC1855de
    public final void a(DG dg) {
        a(dg.h);
    }

    @Override // com.android.tools.r8.internal.AbstractC1855de
    public final void a() {
        if (!d) {
            throw new AssertionError();
        }
    }
}
