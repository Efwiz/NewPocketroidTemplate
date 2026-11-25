package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.function.BiConsumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.lE, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2449lE extends AbstractC2094ge {
    static final /* synthetic */ boolean i = true;
    private final C0702m f;
    private final Map g;
    private final Map h;

    public C2449lE(C0702m c0702m, IdentityHashMap identityHashMap, IdentityHashMap identityHashMap2) {
        super(c0702m.a(), c0702m.E());
        this.f = c0702m;
        this.g = identityHashMap;
        this.h = identityHashMap2;
    }

    public static C2368kE a(C0702m c0702m) {
        return new C2368kE(c0702m);
    }

    @Override // com.android.tools.r8.internal.AbstractC2094ge, com.android.tools.r8.internal.DH
    protected final C1237Oh b(C1237Oh c1237Oh) {
        boolean z = i;
        if (!z && c1237Oh.g()) {
            throw new AssertionError();
        }
        if (!z && c1237Oh.c()) {
            throw new AssertionError();
        }
        C1210Nh c1210Nh = (C1210Nh) C1237Oh.d().b((com.android.tools.r8.graph.Y0) c1237Oh.b());
        com.android.tools.r8.graph.Y0 y0 = (com.android.tools.r8.graph.Y0) c1237Oh.b();
        return ((C1210Nh) c1210Nh.a((com.android.tools.r8.graph.Y0) this.h.getOrDefault(y0, y0))).a();
    }

    @Override // com.android.tools.r8.internal.AbstractC1476Wj
    public final C2449lE c() {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC2094ge, com.android.tools.r8.internal.DH
    public final KE a(KE ke, C0684j2 c0684j2) {
        C0684j2 c0684j3 = (C0684j2) ((Map) this.g.getOrDefault(ke.f(), Collections.emptyMap())).get(ke.b());
        return c0684j3 == null ? ke : ((JE) KE.d().b(c0684j3)).a(ke.e()).a(AbstractC3424yG.a(this.f, c0684j3, (C0684j2) ke.b(), ke.f())).a();
    }

    public final C1630ai a(C0702m c0702m, final AbstractC1476Wj abstractC1476Wj, final DH dh) {
        final C0722p1 c0722p1A = c0702m.a();
        final C1556Zh c1556ZhL = C1630ai.l();
        this.h.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.lE$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C2449lE.a(abstractC1476Wj, dh, c0722p1A, c1556ZhL, (com.android.tools.r8.graph.Y0) obj, (com.android.tools.r8.graph.Y0) obj2);
            }
        });
        return c1556ZhL.a(c0722p1A);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(AbstractC1476Wj abstractC1476Wj, DH dh, C0722p1 c0722p1, C1556Zh c1556Zh, com.android.tools.r8.graph.Y0 y0, com.android.tools.r8.graph.Y0 y1) {
        com.android.tools.r8.graph.Y0 y0B = abstractC1476Wj.b(dh, y1);
        c1556Zh.a(y0B.b(abstractC1476Wj.a(dh, y0.B0()), c0722p1), y0B);
    }
}
