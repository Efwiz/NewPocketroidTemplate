package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0725p4;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0753u;
import com.android.tools.r8.graph.C0767v2;
import com.reandroid.arsc.value.CompoundEntry;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Consumer;
import org.jetbrains.kotlin.codegen.ImplementationBodyCodegen;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class RU {
    static final /* synthetic */ boolean g = true;
    private final C0702m a;
    private final C0722p1 b;
    private final C3226vg c;
    private final com.android.tools.r8.shaking.G0 d;
    private final C0729q2 e;
    private com.android.tools.r8.graph.W0 f;

    private RU(C0702m c0702m, C3226vg c3226vg, AbstractC1048Hm abstractC1048Hm, com.android.tools.r8.shaking.G0 g0) {
        C0729q2 c0729q2A = a(abstractC1048Hm);
        this.a = c0702m;
        this.b = c0702m.a();
        this.c = c3226vg;
        this.d = g0;
        this.e = c0729q2A;
    }

    private C0729q2 b() {
        C0729q2 c0729q2A = this.a.D().a(new com.android.tools.r8.synthesis.C() { // from class: com.android.tools.r8.internal.RU$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.synthesis.C
            public final com.android.tools.r8.synthesis.L.b a(com.android.tools.r8.synthesis.L l) {
                return l.j;
            }
        }, this.e, this.a, new Consumer() { // from class: com.android.tools.r8.internal.RU$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((com.android.tools.r8.synthesis.P) obj);
            }
        });
        if (g || c0729q2A.getAccessFlags().equals(com.android.tools.r8.graph.E.N())) {
            return c0729q2A;
        }
        throw new AssertionError();
    }

    final SU a() {
        C0729q2 c0729q2B = b();
        return new SU(c0729q2B, this.e, new com.android.tools.r8.graph.o5(c0729q2B, this.f));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(com.android.tools.r8.synthesis.P p) {
        C0767v2 c0767v2E = p.e();
        com.android.tools.r8.graph.T0.a aVarD1 = com.android.tools.r8.graph.T0.d1();
        C0722p1 c0722p1 = this.b;
        com.android.tools.r8.graph.T0 t0A = aVarD1.a(c0722p1.a(c0767v2E, c0722p1.J1, ImplementationBodyCodegen.ENUM_VALUES_FIELD_NAME)).a(com.android.tools.r8.graph.Q2.k(4121)).a(this.a.o()).a(!this.a.Y().a().d()).a();
        this.d.c(t0A.getReference()).d(t0A.getReference());
        com.android.tools.r8.graph.W0.a aVarA = com.android.tools.r8.graph.W0.Y1().a(this.b.a(c0767v2E)).a(C0725p4.b(4104, true));
        int iA = this.c.a();
        ArrayList arrayList = new ArrayList((iA * 4) + 4);
        arrayList.add(new C1818d7(iA, EnumC1812d20.INT));
        arrayList.add(new W7(this.b.J1));
        int i = 0;
        while (i < iA) {
            arrayList.add(new D8(D8.a.Dup));
            EnumC1812d20 enumC1812d20 = EnumC1812d20.INT;
            arrayList.add(new C1818d7(i, enumC1812d20));
            i++;
            arrayList.add(new C1818d7(i, enumC1812d20));
            arrayList.add(new D6(EnumC2523mE.INT));
        }
        arrayList.add(new P8(t0A.getReference()));
        arrayList.add(new C3186v8());
        com.android.tools.r8.graph.W0.a aVarA2 = aVarA.a(new C0753u(c0767v2E, 4, 0, arrayList));
        Z8 z8 = Z8.f;
        com.android.tools.r8.graph.W0 w0A = aVarA2.a(z8).b(this.a.o()).a(this.a.o()).a();
        com.android.tools.r8.graph.W0.a aVarY1 = com.android.tools.r8.graph.W0.Y1();
        C0722p1 c0722p2 = this.b;
        com.android.tools.r8.graph.W0.a aVarA3 = aVarY1.a(c0722p2.a(c0767v2E, c0722p2.b(c0722p2.J1, c0722p2.A1), CompoundEntry.NAME_values)).a(C0725p4.Q());
        EnumC1812d20 enumC1812d21 = EnumC1812d20.INT;
        O7 o7 = new O7(enumC1812d21, 0);
        W7 w7 = new W7(this.b.J1);
        EnumC1812d20 enumC1812d22 = EnumC1812d20.OBJECT;
        com.android.tools.r8.graph.W0 w0A2 = aVarA3.a(new C0753u(c0767v2E, 5, 2, AbstractC1639am.a(o7, w7, new R8(enumC1812d22, 1), new O8(t0A.getReference()), new C1818d7(0L, enumC1812d21), new O7(enumC1812d22, 1), new C1818d7(0L, enumC1812d21), new O7(enumC1812d21, 0), new I7(184, this.b.u4.a, false), new O7(enumC1812d22, 1), new C3109u8(enumC1812d22)))).a(z8).b(this.a.o()).a(this.a.o()).a();
        this.f = w0A2;
        ((com.android.tools.r8.synthesis.P) ((com.android.tools.r8.synthesis.P) p.a((AbstractCollection) AbstractC1639am.a(w0A, w0A2))).c(AbstractC1639am.c(t0A))).i();
    }

    private static C0729q2 a(AbstractC1048Hm abstractC1048Hm) {
        Iterator it = abstractC1048Hm.iterator();
        C0729q2 c0729q2 = null;
        while (it.hasNext()) {
            C0729q2 c0729q3 = (C0729q2) it.next();
            if (c0729q2 == null || c0729q3.d.compareTo(c0729q2.d) < 0) {
                c0729q2 = c0729q3;
            }
        }
        return c0729q2;
    }
}
