package com.android.tools.r8.ir.optimize;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0725p4;
import com.android.tools.r8.graph.C0737r2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.internal.AbstractC1154Lf;
import com.android.tools.r8.internal.AbstractC2356k4;
import com.android.tools.r8.internal.AbstractC2412ko;
import com.android.tools.r8.internal.AbstractC3472z00;
import com.android.tools.r8.internal.B4;
import com.android.tools.r8.internal.C1230Ob;
import com.android.tools.r8.internal.C1646at;
import com.android.tools.r8.internal.C1721bt;
import com.android.tools.r8.internal.C2409kl;
import com.android.tools.r8.internal.C2557mh;
import com.android.tools.r8.internal.C3030t00;
import com.android.tools.r8.internal.DG;
import com.android.tools.r8.internal.InterfaceC2641no;
import com.android.tools.r8.internal.OU;
import com.android.tools.r8.internal.S10;
import com.android.tools.r8.internal.TE;
import com.android.tools.r8.internal.WH;
import com.android.tools.r8.utils.A1;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.ir.optimize.a, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3541a {
    static final /* synthetic */ boolean e = true;
    private final C0702m a;
    private final C0722p1 b;
    private final A1 c;
    private final ArrayList d = new ArrayList();

    public C3541a(C0702m c0702m) {
        this.a = c0702m;
        this.c = c0702m.Y();
        this.b = c0702m.a();
    }

    public final void a(final C2409kl c2409kl, TE te, C1230Ob c1230Ob) {
        if (this.c.I()) {
            return;
        }
        Set setC = OU.c();
        B4 b4S = c2409kl.s();
        while (b4S.hasNext()) {
            InterfaceC2641no interfaceC2641noA = b4S.next().a(c2409kl);
            ArrayList arrayList = new ArrayList();
            while (interfaceC2641noA.hasNext()) {
                AbstractC2412ko next = interfaceC2641noA.next();
                if (next.Y1() && next.b0().S2() == this.b.o4.a) {
                    List listH1 = next.h1();
                    if (!e && ((ArrayList) listH1).size() != 3) {
                        throw new AssertionError();
                    }
                    S10 s10T0 = next.T0();
                    AbstractC2412ko abstractC2412koQ = s10T0.q();
                    if (abstractC2412koQ.p2()) {
                        int i = C1721bt.m;
                        C1721bt c1721btB = ((C1646at) ((C1646at) ((C1646at) ((C1646at) new C1646at().a(a(te, c1230Ob).getReference())).a(c2409kl, this.b.l3.c(this.a), null)).b(next)).a(((ArrayList) listH1).subList(1, 3))).b();
                        interfaceC2641noA.d(c1721btB);
                        s10T0.g(c1721btB.b());
                        setC.add(c1721btB.b());
                        arrayList.add(abstractC2412koQ.t0());
                    }
                }
            }
            arrayList.forEach(new Consumer() { // from class: com.android.tools.r8.ir.optimize.a$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    C3541a.a(c2409kl, (DG) obj);
                }
            });
        }
        if (!setC.isEmpty()) {
            new C3030t00(this.a, false).a(setC);
        }
        if (!e && !c2409kl.b(this.a)) {
            throw new AssertionError();
        }
    }

    public final ArrayList a() {
        return this.d;
    }

    private o5 a(TE te, C1230Ob c1230Ob) {
        final C0722p1 c0722p1A = this.a.a();
        final C0737r2 c0737r2B = c0722p1A.b(c0722p1A.l3, c0722p1A.X1, c0722p1A.m3);
        o5 o5VarB = this.a.D().b(new com.android.tools.r8.synthesis.C() { // from class: com.android.tools.r8.ir.optimize.a$$ExternalSyntheticLambda1
            @Override // com.android.tools.r8.synthesis.C
            public final com.android.tools.r8.synthesis.L.b a(com.android.tools.r8.synthesis.L l) {
                return l.B;
            }
        }, c1230Ob.a(), this.a, new Consumer() { // from class: com.android.tools.r8.ir.optimize.a$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(c0737r2B, c0722p1A, (com.android.tools.r8.synthesis.H) obj);
            }
        });
        synchronized (this.d) {
            this.d.add(o5VarB);
            com.android.tools.r8.ir.optimize.info.r rVarA = com.android.tools.r8.ir.optimize.info.n.a();
            C0702m c0702m = this.a;
            C0767v2 c0767v2 = this.b.l3;
            WH whB = WH.b();
            c0767v2.getClass();
            C2557mh c2557mhA = AbstractC1154Lf.a(AbstractC3472z00.a(c0767v2, whB, (C0702m<?>) c0702m).b());
            rVarA.getClass();
            rVarA.a(c0702m, o5VarB.e(), c2557mhA);
        }
        te.b().f(o5VarB, c1230Ob.b());
        return o5VarB;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(C0737r2 c0737r2, final C0722p1 c0722p1, com.android.tools.r8.synthesis.H h) {
        h.a(this.a.o()).a(c0737r2).a(C0725p4.Q()).a(new com.android.tools.r8.synthesis.G() { // from class: com.android.tools.r8.ir.optimize.a$$ExternalSyntheticLambda3
            @Override // com.android.tools.r8.synthesis.G
            public final com.android.tools.r8.graph.W a(C0684j2 c0684j2) {
                return AbstractC2356k4.a(c0722p1, c0684j2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(C2409kl c2409kl, DG dg) {
        dg.h().a(c2409kl, dg).s();
    }
}
