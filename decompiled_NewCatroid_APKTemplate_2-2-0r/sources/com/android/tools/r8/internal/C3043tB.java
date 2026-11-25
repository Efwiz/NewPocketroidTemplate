package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0798x1;
import java.util.BitSet;
import java.util.Iterator;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.tB, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3043tB {
    static final /* synthetic */ boolean e = true;
    private final C0702m a;
    private final C0722p1 b;
    private final com.android.tools.r8.ir.optimize.info.f c = com.android.tools.r8.ir.optimize.info.f.c();
    private final Set d = OU.c();

    C3043tB(C0702m c0702m) {
        c0702m.getClass();
        this.a = c0702m;
        this.b = c0702m.a();
    }

    private void b() {
        this.b.L5.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.tB$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a((C0684j2) obj, (int[]) obj2);
            }
        });
    }

    private void c() {
        this.a.x().a(new Consumer() { // from class: com.android.tools.r8.internal.tB$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((C0684j2) obj);
            }
        });
    }

    final Set a() {
        return this.d;
    }

    final void d() {
        com.android.tools.r8.graph.W0 w0B = b(this.b.g4.g);
        if (w0B != null) {
            this.c.a(w0B, AbstractC1428Un.a(new FH(C1554Zf.c()).a()));
        }
        C0798x1 c0798x1 = this.b.r4;
        com.android.tools.r8.graph.W0 w0B2 = b(c0798x1.k);
        if (w0B2 != null) {
            AbstractC1942ei.a(this.a);
            C1079In c1079InO = this.a.O();
            this.c.a(w0B2, AbstractC1428Un.a(new FH(new C1017Gn().a(c0798x1.a, c1079InO.a(1)).a(c0798x1.b, c1079InO.a(2)).a()).a(this.b.g4.g).a()));
        }
        b();
        Iterator it = this.b.N5.iterator();
        while (it.hasNext()) {
            com.android.tools.r8.graph.W0 w0B3 = b((C0684j2) it.next());
            if (w0B3 != null) {
                if (!e && !w0B3.i1().m().m() && !w0B3.i1().m().k()) {
                    throw new AssertionError();
                }
                com.android.tools.r8.ir.optimize.info.f fVar = this.c;
                C0702m c0702m = this.a;
                int i = AbstractC1154Lf.b;
                fVar.a(c0702m, w0B3, OH.o());
            }
        }
        Iterator<C0684j2> it2 = this.b.M5.iterator();
        while (it2.hasNext()) {
            com.android.tools.r8.graph.W0 w0B4 = b(it2.next());
            if (w0B4 != null) {
                this.c.a(w0B4, 0);
            }
        }
        c();
        com.android.tools.r8.graph.S1 s1 = this.b.f4;
        Iterator<E> it3 = AbstractC1639am.a(s1.e, s1.f, s1.g).iterator();
        while (it3.hasNext()) {
            com.android.tools.r8.graph.W0 w0B5 = b((C0684j2) it3.next());
            if (w0B5 != null) {
                this.c.a(w0B5, 0);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C0684j2 c0684j2, int[] iArr) {
        com.android.tools.r8.graph.W0 w0B = b(c0684j2);
        if (w0B != null) {
            if (!e && iArr.length <= 0) {
                throw new AssertionError();
            }
            int i = iArr[iArr.length - 1] + 1;
            BitSet bitSet = new BitSet(i);
            for (int i2 : iArr) {
                if (!e && i2 >= i) {
                    throw new AssertionError();
                }
                bitSet.set(i2);
            }
            this.c.a(w0B, bitSet);
            if (w0B.i1().p() != null) {
                w0B.i1().p().or(bitSet);
            } else {
                this.c.b(w0B, (BitSet) bitSet.clone());
            }
        }
    }

    private com.android.tools.r8.graph.W0 b(C0684j2 c0684j2) {
        C0702m c0702m = this.a;
        c0702m.getClass();
        com.android.tools.r8.graph.W0 w0C = c0684j2.c(c0702m.c(c0684j2.e));
        if (w0C == null) {
            return null;
        }
        this.d.add(c0684j2.e);
        return w0C;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(C0684j2 c0684j2) {
        com.android.tools.r8.graph.W0 w0B = b(c0684j2);
        if (w0B != null) {
            this.c.b(w0B);
        }
    }
}
