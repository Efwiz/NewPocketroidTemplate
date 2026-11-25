package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.shaking.InterfaceC3680a0;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.hA, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2137hA implements InterfaceC3680a0 {
    private final String a;
    private final C0875Bz b;
    private final C1255Oz c;
    private final C1466Vz d;

    private C2137hA(String str, C0875Bz c0875Bz, C1255Oz c1255Oz, C1466Vz c1466Vz) {
        this.a = str;
        this.b = c0875Bz;
        this.c = c1255Oz;
        this.d = c1466Vz;
    }

    public static C2137hA a(C3247vy c3247vy, AbstractC0727q0 abstractC0727q0, C0702m c0702m, Consumer consumer, C1201Mz c1201Mz) {
        HashMap map = new HashMap();
        for (com.android.tools.r8.graph.T0 t0 : abstractC0727q0.W0()) {
            com.android.tools.r8.graph.Y0 reference = t0.getReference();
            map.put(new com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.e(reference.f.toString(), reference.h.b1()).a(), t0);
        }
        HashMap map2 = new HashMap();
        for (com.android.tools.r8.graph.W0 w0 : abstractC0727q0.M1()) {
            map2.put(AbstractC1745cA.a(w0.getReference()).a(), w0);
        }
        C1466Vz c1466Vz = new C1466Vz(c0702m);
        AbstractC0936Ds.c(c3247vy, "<this>");
        C2576my c2576my = com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.k.b;
        return new C2137hA(((C1025Gu) c3247vy.a(c2576my)).d(), C0875Bz.a(c3247vy, map2, map, c0702m.a(), c0702m.Y().e, consumer, c1201Mz, c1466Vz), C1255Oz.a(((C1025Gu) c3247vy.a(c2576my)).c(), c0702m.a(), c0702m.Y().e), c1466Vz);
    }

    public final String u() {
        return this.a;
    }

    final boolean a(C3247vy c3247vy, AbstractC0727q0 abstractC0727q0, C0702m c0702m) {
        C1466Vz c1466Vz = new C1466Vz(c0702m);
        C0875Bz c0875Bz = this.b;
        List listC = c3247vy.c();
        Objects.requireNonNull(listC);
        hA$$ExternalSyntheticLambda0 ha__externalsyntheticlambda0 = new hA$$ExternalSyntheticLambda0(listC);
        List listA = c3247vy.a();
        Objects.requireNonNull(listA);
        hA$$ExternalSyntheticLambda1 ha__externalsyntheticlambda1 = new hA$$ExternalSyntheticLambda1(listA);
        List listB = c3247vy.b();
        Objects.requireNonNull(listB);
        boolean zA = c0875Bz.a(ha__externalsyntheticlambda0, ha__externalsyntheticlambda1, new hA$$ExternalSyntheticLambda2(listB), abstractC0727q0, c0702m, c1466Vz);
        C1255Oz c1255Oz = this.c;
        C2576my c2576my = com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.k.b;
        ArrayList arrayListC = ((C1025Gu) c3247vy.a(c2576my)).c();
        Objects.requireNonNull(arrayListC);
        boolean zA2 = zA | c1255Oz.a(new hA$$ExternalSyntheticLambda1(arrayListC), c0702m);
        ((C1025Gu) c3247vy.a(c2576my)).b(this.a);
        return zA2 || !this.d.a(c1466Vz, c0702m);
    }

    @Override // com.android.tools.r8.shaking.InterfaceC3680a0
    public final void a(com.android.tools.r8.graph.Q0 q0) {
        this.b.a(q0);
        this.c.a(q0);
    }
}
