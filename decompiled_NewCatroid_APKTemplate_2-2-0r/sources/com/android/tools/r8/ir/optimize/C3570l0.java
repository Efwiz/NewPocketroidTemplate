package com.android.tools.r8.ir.optimize;

import com.android.tools.r8.graph.AbstractC0748t0;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.I4;
import com.android.tools.r8.graph.n5;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.internal.AN;
import com.android.tools.r8.internal.AbstractC1305Qs;
import com.android.tools.r8.internal.AbstractC1476Wj;
import com.android.tools.r8.internal.BN;
import com.android.tools.r8.internal.C1749cD;
import com.android.tools.r8.internal.C2011fa$$ExternalSyntheticLambda0;
import com.android.tools.r8.internal.C2409kl;
import com.android.tools.r8.internal.C2634ni;
import com.android.tools.r8.internal.C2844qM;
import com.android.tools.r8.internal.C3387xn;
import com.android.tools.r8.internal.JH;
import com.android.tools.r8.internal.OF;
import com.android.tools.r8.internal.RF;
import com.android.tools.r8.internal.TE;
import com.android.tools.r8.internal.V9;
import com.android.tools.r8.shaking.C3733i;
import com.android.tools.r8.utils.C3932m1;
import com.android.tools.r8.utils.InterfaceC3885d3;
import com.android.tools.r8.utils.InterfaceC3949p3;
import com.android.tools.r8.utils.P1;
import com.android.tools.r8.utils.Z1;
import com.android.tools.r8.utils.y3;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.function.BiConsumer;
import java.util.function.ObjIntConsumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

/* JADX INFO: renamed from: com.android.tools.r8.ir.optimize.l0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3570l0 {
    static final /* synthetic */ boolean d = true;
    private final C0702m a;
    private final AN b = AN.d();
    private final int[] c;

    C3570l0(C0702m c0702m) {
        this.a = c0702m;
        this.c = c0702m.Y().P0().c;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public void a(final o5 o5Var, C2409kl c2409kl, final TE te) {
        I4.c<?> cVarO;
        o5 o5VarA;
        P1 p1 = new P1(new Supplier() { // from class: com.android.tools.r8.ir.optimize.l0$$ExternalSyntheticLambda5
            @Override // java.util.function.Supplier
            public final Object get() {
                return this.f$0.a(o5Var, te);
            }
        });
        for (AbstractC1305Qs abstractC1305Qs : c2409kl.b((Predicate) new C2011fa$$ExternalSyntheticLambda0())) {
            if (!abstractC1305Qs.a(this.a.a()) && (cVarO = ((C3733i) this.a.f()).b(abstractC1305Qs.S2(), abstractC1305Qs.R2()).o()) != null && !cVarO.a(o5Var, this.a).b() && (o5VarA = AbstractC0748t0.a(abstractC1305Qs.h(this.a, o5Var))) != null && te.a().b(o5VarA)) {
                X xA = ((N) p1.d()).a(c2409kl, abstractC1305Qs, cVarO, o5VarA, o5Var, V9.c(), C3387xn.b(), JH.w());
                if (xA == null || (xA instanceof C3544b0)) {
                    this.b.a(o5VarA, Optional.empty());
                } else {
                    W wA = xA.a();
                    if (!d && wA.c != EnumC3542a0.d) {
                        throw new AssertionError();
                    }
                    a(o5Var, o5VarA, te);
                }
            }
        }
    }

    final void a(final o5 o5Var, final C2409kl c2409kl, final TE te, y3 y3Var) {
        if (te.c()) {
            y3Var.a("Multi caller inliner: Record call edges", new InterfaceC3885d3() { // from class: com.android.tools.r8.ir.optimize.l0$$ExternalSyntheticLambda0
                @Override // com.android.tools.r8.utils.InterfaceC3885d3
                public final void a() {
                    this.f$0.a(o5Var, c2409kl, te);
                }
            });
        }
    }

    final void c(o5 o5Var) {
        if (!d && this.b.a(o5Var)) {
            throw new AssertionError();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ N a(o5 o5Var, TE te) {
        return new N(this.a, new C2634ni(EnumC3542a0.d), o5Var, te, Integer.MAX_VALUE);
    }

    final void a(o5 o5Var, o5 o5Var2, TE te) {
        Optional optional = (Optional) this.b.a((n5) o5Var2, Z1.a(new Supplier() { // from class: com.android.tools.r8.ir.optimize.l0$$ExternalSyntheticLambda2
            @Override // java.util.function.Supplier
            public final Object get() {
                return C3570l0.a();
            }
        }));
        if (optional.isPresent()) {
            BN bn = (BN) optional.get();
            bn.a(o5Var);
            if (bn.b() > this.c.length) {
                a(o5Var2, te, bn);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Optional a() {
        return Optional.of(BN.a());
    }

    private void a(o5 o5Var, final TE te, BN bn) {
        bn.a(new Predicate() { // from class: com.android.tools.r8.ir.optimize.l0$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return C3570l0.b((o5) obj);
            }
        });
        final C3932m1 c3932m1 = new C3932m1();
        bn.a(new ObjIntConsumer() { // from class: com.android.tools.r8.ir.optimize.l0$$ExternalSyntheticLambda7
            @Override // java.util.function.ObjIntConsumer
            public final void accept(Object obj, int i) {
                C3570l0.a(te, c3932m1, (o5) obj, i);
            }
        });
        if (c3932m1.a() > this.c.length) {
            this.b.a(o5Var, Optional.empty());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(TE te, C3932m1 c3932m1, o5 o5Var, int i) {
        if (te.a().a(o5Var)) {
            return;
        }
        c3932m1.d(i);
    }

    public final void a(C2844qM c2844qM, final ExecutorService executorService, y3 y3Var) {
        y3Var.a("Multi caller inliner");
        final OF of = (OF) y3Var.a("Call graph construction", new InterfaceC3949p3() { // from class: com.android.tools.r8.ir.optimize.l0$$ExternalSyntheticLambda9
            @Override // com.android.tools.r8.utils.InterfaceC3949p3
            public final Object get() {
                return this.f$0.a(executorService);
            }
        });
        c2844qM.a(this.a).a((C1749cD) y3Var.a("Needs inlining analysis", new InterfaceC3949p3() { // from class: com.android.tools.r8.ir.optimize.l0$$ExternalSyntheticLambda1
            @Override // com.android.tools.r8.utils.InterfaceC3949p3
            public final Object get() {
                return this.f$0.b(of);
            }
        }));
        y3Var.c();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean b(o5 o5Var) {
        return o5Var.B().u();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ OF a(ExecutorService executorService) {
        return OF.a(this.a).a(executorService);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public C1749cD b(final OF of) {
        final AbstractC1476Wj abstractC1476WjE = this.a.E();
        final C1749cD c1749cDB = C1749cD.b(abstractC1476WjE);
        this.b.a(new BiConsumer() { // from class: com.android.tools.r8.ir.optimize.l0$$ExternalSyntheticLambda8
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a(of, c1749cDB, abstractC1476WjE, (o5) obj, (Optional) obj2);
            }
        });
        this.b.a();
        return c1749cDB;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(OF of, final C1749cD c1749cD, final AbstractC1476Wj abstractC1476Wj, o5 o5Var, Optional optional) {
        int iC;
        if (!o5Var.e().D1().c() && optional.isPresent()) {
            if (!o5Var.e().B1() || ((C3733i) this.a.f()).b(o5Var.a())) {
                BN bn = (BN) optional.get();
                bn.a(new Predicate() { // from class: com.android.tools.r8.ir.optimize.l0$$ExternalSyntheticLambda3
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return C3570l0.a((o5) obj);
                    }
                });
                if (bn.b() == 0 || bn.b() > this.c.length || (iC = ((RF) of.a(o5Var)).c()) < bn.b() || bn.b() < iC) {
                    return;
                }
                if (o5Var.e().a1().k(this.c[bn.b() - 1])) {
                    bn.a(new ObjIntConsumer() { // from class: com.android.tools.r8.ir.optimize.l0$$ExternalSyntheticLambda4
                        @Override // java.util.function.ObjIntConsumer
                        public final void accept(Object obj, int i) {
                            C3570l0.a(c1749cD, abstractC1476Wj, (o5) obj, i);
                        }
                    });
                    com.android.tools.r8.ir.optimize.info.n.a().getClass();
                    com.android.tools.r8.ir.optimize.info.r.d(o5Var);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(o5 o5Var) {
        return o5Var.B().u();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C1749cD c1749cD, AbstractC1476Wj abstractC1476Wj, o5 o5Var, int i) {
        if (o5Var.B().u()) {
            return;
        }
        c1749cD.a(abstractC1476Wj, o5Var);
    }
}
