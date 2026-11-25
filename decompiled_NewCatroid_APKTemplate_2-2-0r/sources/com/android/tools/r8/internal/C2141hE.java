package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.function.BiConsumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.hE, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2141hE extends AbstractC2094ge {
    static final /* synthetic */ boolean h = true;
    private final Map f;
    private final Map g;

    private C2141hE(IdentityHashMap identityHashMap, IdentityHashMap identityHashMap2, C0722p1 c0722p1, AbstractC1476Wj abstractC1476Wj) {
        super(c0722p1, abstractC1476Wj);
        if (!h && abstractC1476Wj.f()) {
            throw new AssertionError();
        }
        this.f = identityHashMap;
        this.g = identityHashMap2;
    }

    public static C2057gE a(C0702m c0702m) {
        return new C2057gE(c0702m, c0702m.E());
    }

    @Override // com.android.tools.r8.internal.AbstractC1476Wj
    public final C2141hE b() {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC1476Wj
    public final boolean f() {
        return false;
    }

    @Override // com.android.tools.r8.internal.AbstractC2094ge, com.android.tools.r8.internal.DH
    protected final C1237Oh b(C1237Oh c1237Oh) {
        boolean z = h;
        if (!z && c1237Oh.g()) {
            throw new AssertionError();
        }
        if (!z && c1237Oh.c()) {
            throw new AssertionError();
        }
        C1210Nh c1210Nh = (C1210Nh) C1237Oh.d().b((com.android.tools.r8.graph.Y0) c1237Oh.b());
        com.android.tools.r8.graph.Y0 y0 = (com.android.tools.r8.graph.Y0) c1237Oh.b();
        return ((C1210Nh) c1210Nh.a((com.android.tools.r8.graph.Y0) this.f.getOrDefault(y0, y0))).a();
    }

    public final void a(C0684j2 c0684j2, C0684j2 c0684j3) {
        this.g.put(c0684j2, c0684j3);
    }

    @Override // com.android.tools.r8.internal.AbstractC2094ge, com.android.tools.r8.internal.DH
    public final KE a(KE ke, C0684j2 c0684j2) {
        boolean z = h;
        if (!z && ke.a() != null) {
            throw new AssertionError();
        }
        JE je = (JE) KE.d().b((C0684j2) ke.b());
        C0684j2 c0684j3 = (C0684j2) ke.b();
        C0684j2 c0684j4 = (C0684j2) this.g.get(c0684j3);
        if (!z && c0684j3 == c0684j4) {
            throw new AssertionError();
        }
        C0684j2 c0684j5 = c0684j3;
        C0684j2 c0684j6 = c0684j4;
        while (c0684j6 != null) {
            C0684j2 c0684j7 = c0684j6;
            c0684j6 = (C0684j2) this.g.get(c0684j6);
            c0684j5 = c0684j7;
        }
        return ((JE) je.a(c0684j5)).a(ke.e()).a(ke.f()).a();
    }

    public final C2141hE a(C0702m c0702m, final AbstractC1476Wj abstractC1476Wj, final DH dh) {
        final C0722p1 c0722p1A = c0702m.a();
        final C2057gE c2057gE = new C2057gE(c0702m, AbstractC1476Wj.e());
        this.f.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.hE$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C2141hE.a(abstractC1476Wj, dh, c0722p1A, c2057gE, (com.android.tools.r8.graph.Y0) obj, (com.android.tools.r8.graph.Y0) obj2);
            }
        });
        this.g.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.hE$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C2141hE.a(abstractC1476Wj, dh, c0722p1A, c2057gE, (C0684j2) obj, (C0684j2) obj2);
            }
        });
        return c2057gE.a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(AbstractC1476Wj abstractC1476Wj, DH dh, C0722p1 c0722p1, C2057gE c2057gE, com.android.tools.r8.graph.Y0 y0, com.android.tools.r8.graph.Y0 y1) {
        com.android.tools.r8.graph.Y0 y0C = abstractC1476Wj.c(dh, y1);
        c2057gE.c.put(y0C.b(abstractC1476Wj.a(dh, y0.B0()), c0722p1), y0C);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(AbstractC1476Wj abstractC1476Wj, DH dh, C0722p1 c0722p1, C2057gE c2057gE, C0684j2 c0684j2, C0684j2 c0684j3) {
        C0684j2 c0684j2C = abstractC1476Wj.c(dh, c0684j3);
        c2057gE.d.put(c0684j2C.b(abstractC1476Wj.a(dh, c0684j2.B0()), c0722p1), c0684j2C);
    }
}
