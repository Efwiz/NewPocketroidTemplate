package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.H5;
import com.android.tools.r8.internal.C2750p20;
import com.android.tools.r8.internal.C3405y10;
import com.android.tools.r8.utils.AbstractC3938n2;
import com.android.tools.r8.utils.C3912j0;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class w4 extends H5 {
    static final /* synthetic */ boolean j = true;
    private final C0702m f;
    private final y4 g;
    private final C2750p20 h;
    private final List i;

    w4(C0702m c0702m, y4 y4Var, C2750p20 c2750p20, ArrayList arrayList) {
        super(c0702m);
        this.f = c0702m;
        this.g = y4.a(y4Var, c2750p20);
        this.h = c2750p20;
        this.i = arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public A4 b() {
        for (C0729q2 c0729q2 : ((C3733i) this.f.f()).d()) {
            c0729q2.Y().d(new Function() { // from class: com.android.tools.r8.shaking.w4$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return this.f$0.a((com.android.tools.r8.graph.W0) obj);
                }
            });
            c0729q2.b(a(C3912j0.b(), c0729q2.N1()));
            c0729q2.a(a(C3912j0.b(), c0729q2.w1()));
        }
        Iterator it = this.i.iterator();
        while (it.hasNext()) {
            ((v4) it.next()).a(new Function() { // from class: com.android.tools.r8.shaking.w4$$ExternalSyntheticLambda1
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return this.f$0.a((C0684j2) obj);
                }
            });
        }
        A4 a4A = this.g.a(this.f, this.h);
        if (a4A != null) {
            new C3689b(a4A, this.f.E()).a(((C3733i) this.f.f()).d());
        }
        return a4A;
    }

    @Override // com.android.tools.r8.graph.H5
    public final void a(C0767v2 c0767v2, C0767v2 c0767v3) {
        throw new C3405y10();
    }

    @Override // com.android.tools.r8.graph.H5
    public final void a(com.android.tools.r8.graph.Y0 y0, com.android.tools.r8.graph.Y0 y1) {
        if (this.g.b.containsValue(y1)) {
            return;
        }
        this.g.b.put(y0, y1);
    }

    @Override // com.android.tools.r8.graph.H5
    public final void a(C0684j2 c0684j2, C0684j2 c0684j3) {
        if (this.g.a(c0684j3)) {
            return;
        }
        this.g.b(c0684j2, c0684j3).a(c0684j2, c0684j3, false);
    }

    @Override // com.android.tools.r8.graph.H5
    public final com.android.tools.r8.graph.W0 a(com.android.tools.r8.graph.W0 w0, com.android.tools.r8.graph.W0 w1) {
        a(w0.getReference(), w1.getReference());
        if (w1.H1()) {
            if (!j && w0.D1().d()) {
                throw new AssertionError();
            }
            w1.a(AbstractC3938n2.b);
        }
        return w1;
    }

    @Override // com.android.tools.r8.graph.H5
    public final C0767v2 b(C0767v2 c0767v2) {
        while (this.h.d(c0767v2)) {
            c0767v2 = this.h.c(c0767v2);
        }
        return c0767v2;
    }
}
