package com.android.tools.r8.internal;

import com.android.tools.r8.ClassFileConsumer;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0753u;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.ir.optimize.AbstractC3583s;
import com.android.tools.r8.ir.optimize.C3541a;
import com.android.tools.r8.ir.optimize.C3546c0;
import com.android.tools.r8.ir.optimize.C3553e;
import com.android.tools.r8.ir.optimize.C3574n0;
import com.android.tools.r8.ir.optimize.C3577p;
import com.android.tools.r8.ir.optimize.C3585t;
import com.android.tools.r8.ir.optimize.C3590v;
import com.android.tools.r8.ir.optimize.InterfaceC3556e0;
import com.android.tools.r8.position.MethodPosition;
import com.android.tools.r8.shaking.C3733i;
import com.android.tools.r8.shaking.C3762m1;
import com.android.tools.r8.utils.C3879c3;
import com.android.tools.r8.utils.C3970u0;
import com.android.tools.r8.utils.EnumC3891f;
import com.android.tools.r8.utils.InterfaceC3877c1;
import com.android.tools.r8.utils.InterfaceC3880d;
import com.android.tools.r8.utils.InterfaceC3905h3;
import com.android.tools.r8.utils.StringDiagnostic;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/* JADX INFO: renamed from: com.android.tools.r8.internal.sl, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C3012sl {
    static final /* synthetic */ boolean N = true;
    public final C3577p A;
    private final com.android.tools.r8.ir.optimize.P B;
    final C3553e C;
    public final com.android.tools.r8.ir.optimize.M D;
    private final com.android.tools.r8.ir.optimize.info.j E;
    protected C0759u2 H;
    protected final com.android.tools.r8.utils.D1 K;
    public final C0702m a;
    public final VK b;
    private final C3590v c;
    protected final D7 d;
    protected final C3521zh e;
    protected final com.android.tools.r8.shaking.G1 f;
    protected final ZX g;
    protected final com.android.tools.r8.ir.optimize.T h;
    private final C1933ea i;
    protected final com.android.tools.r8.utils.A1 j;
    public final com.android.tools.r8.ir.optimize.F k;
    public final C3541a l;
    public final AbstractC2598nE n;
    private final C2289jB o;
    protected final C3546c0 p;
    protected final com.android.tools.r8.naming.C q;
    private final com.android.tools.r8.ir.optimize.O r;
    protected final C2627nd s;
    private final C2544mY t;
    private final C3326x00 u;
    protected final com.android.tools.r8.ir.optimize.G0 v;
    private final C1555Zg w;
    protected final AbstractC0922Dg x;
    protected final C1536Yn y;
    protected final com.android.tools.r8.ir.optimize.D0 z;
    private final C3574n0 m = new C3574n0();
    protected final com.android.tools.r8.ir.optimize.info.o F = new com.android.tools.r8.ir.optimize.info.o();
    protected final com.android.tools.r8.ir.optimize.info.r G = com.android.tools.r8.ir.optimize.info.r.b();
    protected List I = null;
    protected final Set J = OU.c();
    AtomicBoolean L = new AtomicBoolean();
    AtomicBoolean M = new AtomicBoolean();

    public C3012sl(C0702m<?> c0702m) {
        boolean z = N;
        if (!z && c0702m.Y() == null) {
            throw new AssertionError();
        }
        if (!z && c0702m.Y().f == null) {
            throw new AssertionError();
        }
        this.a = c0702m;
        com.android.tools.r8.utils.A1 a1Y = c0702m.Y();
        this.j = a1Y;
        com.android.tools.r8.ir.optimize.F f = new com.android.tools.r8.ir.optimize.F(c0702m);
        this.k = f;
        this.l = c0702m.Y().A0.b() ? new C3541a(c0702m) : null;
        this.c = new C3590v(c0702m, this);
        this.g = new ZX(c0702m);
        this.D = new com.android.tools.r8.ir.optimize.M(c0702m, f);
        this.C = new C3553e(c0702m);
        this.h = new com.android.tools.r8.ir.optimize.T(c0702m);
        this.K = a1Y.q.a(new Function() { // from class: com.android.tools.r8.internal.sl$$ExternalSyntheticLambda8
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.a((String) obj);
            }
        });
        if (a1Y.U0()) {
            if (!z && !a1Y.A0.b()) {
                throw new AssertionError();
            }
            this.d = D7.a(c0702m, c0702m.d());
            this.s = null;
            this.B = null;
            this.i = null;
            this.e = null;
            this.f = null;
            this.p = null;
            this.b = VK.a();
            this.n = null;
            this.o = null;
            this.q = null;
            this.r = null;
            this.u = null;
            this.t = null;
            this.v = null;
            this.E = null;
            this.w = null;
            this.x = AbstractC0922Dg.a();
            this.A = null;
            this.y = null;
            this.z = null;
            return;
        }
        this.d = c0702m.q() ? D7.a() : D7.a(c0702m, c0702m.d());
        this.s = a1Y.H0 ? new C2627nd(c0702m, this) : null;
        if (c0702m.Y().A0.b() && c0702m.Y().a().e && c0702m.Y().B0().a(EnumC3891f.L)) {
            this.y = new C1536Yn(c0702m);
        } else {
            this.y = null;
        }
        this.z = (c0702m.Y().a().a && c0702m.Y().E()) ? new com.android.tools.r8.ir.optimize.D0(c0702m) : null;
        if (!c0702m.q()) {
            C0702m c0702mJ0 = c0702m.j0();
            this.A = null;
            this.i = null;
            this.B = null;
            this.e = null;
            this.f = null;
            this.p = null;
            this.b = VK.a();
            this.n = a1Y.Y0() ? new C3517zd(c0702mJ0) : null;
            this.o = null;
            this.q = null;
            this.r = null;
            this.u = null;
            this.v = null;
            this.E = null;
            this.w = null;
            this.x = AbstractC0922Dg.a();
        } else {
            if (!z && !c0702m.f().i()) {
                throw new AssertionError();
            }
            if (!z && c0702m.b0() == null) {
                throw new AssertionError();
            }
            C0702m<C3733i> c0702mI0 = c0702m.i0();
            this.A = new C3577p(c0702mI0);
            this.i = (a1Y.y && a1Y.P0().a) ? new C1933ea() : null;
            this.B = new com.android.tools.r8.ir.optimize.P(c0702mI0);
            this.e = new C3521zh(c0702mI0);
            this.f = a1Y.G ? new com.android.tools.r8.shaking.G1(c0702mI0) : null;
            AbstractC0922Dg abstractC0922DgA = AbstractC0922Dg.a(c0702mI0);
            this.x = abstractC0922DgA;
            C2289jB c2289jB = new C2289jB(c0702mI0, abstractC0922DgA);
            this.o = c2289jB;
            this.p = new C3546c0(c0702mI0, this, c2289jB);
            this.b = VK.a(c0702mI0);
            this.n = new C1763cQ(c0702mI0);
            this.E = new com.android.tools.r8.ir.optimize.info.j(c0702mI0, this);
            if (a1Y.k1()) {
                this.q = new com.android.tools.r8.naming.C(c0702mI0);
            } else {
                this.q = null;
            }
            this.r = a1Y.v ? new com.android.tools.r8.ir.optimize.O(c0702mI0) : null;
            this.u = new C3326x00(c0702mI0, C2433l20.a(c0702m));
            this.v = a1Y.D ? new com.android.tools.r8.ir.optimize.G0(c0702mI0, c0702m.d()) : null;
            this.w = a1Y.R ? new C1555Zg(c0702mI0) : null;
        }
        this.t = a1Y.p1() ? new C2544mY(c0702m, this.q) : null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ C0759u2 a(String str) {
        return this.j.a.c("L" + C3970u0.o(str));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void d(com.android.tools.r8.graph.o5 o5Var) {
        C2409kl c2409klA = o5Var.a(this.a);
        if (!N && c2409klA == null) {
            throw new AssertionError();
        }
        this.k.j(c2409klA);
        a(c2409klA, com.android.tools.r8.ir.optimize.info.q.b(), com.android.tools.r8.utils.y3.b());
    }

    public final boolean b() {
        return this.I != null;
    }

    final com.android.tools.r8.utils.y3 c(final com.android.tools.r8.graph.o5 o5Var, final com.android.tools.r8.ir.optimize.info.n nVar, final TE te, final C1230Ob c1230Ob) {
        return (com.android.tools.r8.utils.y3) com.android.tools.r8.utils.R0.a(o5Var.getOrigin(), new MethodPosition(o5Var.getReference().E0()), new Supplier() { // from class: com.android.tools.r8.internal.sl$$ExternalSyntheticLambda11
            @Override // java.util.function.Supplier
            public final Object get() {
                return this.f$0.a(o5Var, nVar, te, c1230Ob);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(EK ek, com.android.tools.r8.graph.o5 o5Var, C1230Ob c1230Ob) {
        b(o5Var, this.F, ek, c1230Ob);
    }

    public final com.android.tools.r8.utils.y3 b(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.ir.optimize.info.n nVar, TE te, C1230Ob c1230Ob) {
        com.android.tools.r8.graph.W0 w0E = o5Var.e();
        com.android.tools.r8.graph.W wA1 = w0E.a1();
        boolean zA = this.j.a(w0E);
        if (wA1 != null && zA) {
            return c(o5Var, nVar, te, c1230Ob);
        }
        w0E.a(com.android.tools.r8.ir.optimize.V.c);
        return com.android.tools.r8.utils.y3.b();
    }

    public final C3546c0 a() {
        return this.p;
    }

    public final void c(final com.android.tools.r8.graph.o5 o5Var) {
        boolean z = N;
        if (!z && !this.a.q()) {
            throw new AssertionError();
        }
        if (!z && o5Var.a().c(o5Var.getReference()) != null) {
            throw new AssertionError();
        }
        this.a.a(new InterfaceC3905h3() { // from class: com.android.tools.r8.internal.sl$$ExternalSyntheticLambda9
            @Override // com.android.tools.r8.utils.InterfaceC3905h3
            public final void accept(Object obj) {
                ((C3476z2) obj).a(o5Var);
            }
        });
        this.x.b(o5Var);
        this.b.b(o5Var);
        C3546c0 c3546c0 = this.p;
        if (c3546c0 != null) {
            c3546c0.d(o5Var);
        }
        this.J.add(o5Var.getReference());
    }

    protected final void a(ExecutorService executorService) {
        if (this.j.j()) {
            if (!N) {
                this.F.b();
            }
            C3879c3.a(this.a.f().d(), new Consumer() { // from class: com.android.tools.r8.internal.sl$$ExternalSyntheticLambda10
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.a((C0729q2) obj);
                }
            }, executorService);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX INFO: renamed from: d, reason: merged with bridge method [inline-methods] */
    public final com.android.tools.r8.utils.y3 a(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.ir.optimize.info.n nVar, TE te, C1230Ob c1230Ob) {
        boolean z;
        this.j.getClass();
        Runnable runnable = this.j.n1.E0;
        if (runnable != null) {
            runnable.run();
        }
        com.android.tools.r8.graph.W wA1 = o5Var.e().a1();
        wA1.getClass();
        if (wA1 instanceof com.android.tools.r8.graph.F5) {
            z = false;
        } else if (this.a.q() || this.j.n1.j0) {
            z = true;
        } else {
            if (!N && !o5Var.e().a1().z0()) {
                throw new AssertionError();
            }
            z = !(this.j.f instanceof ClassFileConsumer);
        }
        if (z && !this.j.V0) {
            C2409kl c2409klA = o5Var.a(this.a);
            if (c2409klA == null) {
                nVar.a(o5Var.e(), com.android.tools.r8.ir.optimize.V.c);
                return com.android.tools.r8.utils.y3.b();
            }
            return a(c2409klA, nVar, te, c1230Ob);
        }
        nVar.a(o5Var.e(), com.android.tools.r8.ir.optimize.V.c);
        return com.android.tools.r8.utils.y3.b();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(C0729q2 c0729q2) {
        if (c0729q2.x1()) {
            return;
        }
        c0729q2.g(new Consumer() { // from class: com.android.tools.r8.internal.sl$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((com.android.tools.r8.graph.o5) obj);
            }
        }, new Kj$$ExternalSyntheticLambda3());
    }

    public final void b(String str) {
        if (this.j.p0.isEmpty()) {
            return;
        }
        System.out.println("Entering phase: " + str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(com.android.tools.r8.graph.o5 o5Var) {
        o5Var.e(this.a);
    }

    public final void a(InterfaceC3880d interfaceC3880d) {
        if (this.a.q()) {
            if (b()) {
                this.I.add(interfaceC3880d);
                return;
            }
            throw new C3405y10("Attempt to call addWaveDoneAction() outside of wave.");
        }
        throw new C3405y10("addWaveDoneAction() should never be used in D8.");
    }

    public final void b(final com.android.tools.r8.graph.o5 o5Var) {
        boolean z = N;
        if (!z && !this.a.q()) {
            throw new AssertionError();
        }
        if (!z && o5Var.a().c(o5Var.getReference()) == null) {
            throw new AssertionError();
        }
        this.a.a(new InterfaceC3905h3() { // from class: com.android.tools.r8.internal.sl$$ExternalSyntheticLambda13
            @Override // com.android.tools.r8.utils.InterfaceC3905h3
            public final void accept(Object obj) {
                com.android.tools.r8.graph.o5 o5Var2 = o5Var;
                ((C3476z2) obj).getClass();
            }
        });
        this.x.a(o5Var);
        this.b.a(o5Var);
        C3546c0 c3546c0 = this.p;
        if (c3546c0 != null) {
            c3546c0.c(o5Var);
        }
    }

    protected final void a(ArrayList arrayList, ExecutorService executorService) {
        C3879c3.a(arrayList, new Consumer() { // from class: com.android.tools.r8.internal.sl$$ExternalSyntheticLambda7
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.d((com.android.tools.r8.graph.o5) obj);
            }
        }, executorService);
    }

    public void a(C2409kl c2409kl) {
        com.android.tools.r8.graph.o5 o5VarH = c2409kl.h();
        o5VarH.e();
        if (!N && !c2409kl.b(this.a)) {
            throw new AssertionError();
        }
        com.android.tools.r8.utils.y3 y3VarB = com.android.tools.r8.utils.y3.b();
        this.D.a(c2409kl, y3VarB);
        o5VarH.a(new C3455yl(this.a, this.D).a(c2409kl, C2133h6.b(), y3VarB), this.a);
    }

    public final void a(final ArrayList arrayList, VE ve, ExecutorService executorService) {
        a(CN.a(new InterfaceC3877c1() { // from class: com.android.tools.r8.internal.sl$$ExternalSyntheticLambda12
            @Override // com.android.tools.r8.utils.InterfaceC3877c1
            public final void forEach(Consumer consumer) {
                arrayList.forEach(consumer);
            }
        }), ve, executorService);
    }

    public final void a(com.android.tools.r8.graph.o5 o5Var, VE ve) {
        if (o5Var.e().M1()) {
            return;
        }
        final EK ekA = EK.a(o5Var, ve, this.a);
        ekA.a(new DK() { // from class: com.android.tools.r8.internal.sl$$ExternalSyntheticLambda1
            @Override // com.android.tools.r8.internal.DK
            public final void a(com.android.tools.r8.graph.o5 o5Var2, C1230Ob c1230Ob) {
                this.f$0.a(ekA, o5Var2, c1230Ob);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(EK ek, com.android.tools.r8.graph.o5 o5Var, C1230Ob c1230Ob) {
        b(o5Var, this.F, ek, c1230Ob);
    }

    public final void a(AbstractC1639am abstractC1639am, VE ve, ExecutorService executorService) {
        CN cnB = CN.b();
        Iterator<E> it = abstractC1639am.iterator();
        while (it.hasNext()) {
            C0729q2 c0729q2 = (C0729q2) it.next();
            Objects.requireNonNull(cnB);
            c0729q2.l(new CN$$ExternalSyntheticLambda2(cnB));
        }
        a(cnB, ve, executorService);
    }

    public final void a(CN cn, VE ve, ExecutorService executorService) {
        if (cn.isEmpty()) {
            return;
        }
        final EK ekA = EK.a(cn, ve, this.a);
        ekA.a(new DK() { // from class: com.android.tools.r8.internal.sl$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.internal.DK
            public final void a(com.android.tools.r8.graph.o5 o5Var, C1230Ob c1230Ob) {
                this.f$0.b(ekA, o5Var, c1230Ob);
            }
        }, executorService);
    }

    final com.android.tools.r8.utils.y3 a(final C2409kl c2409kl, com.android.tools.r8.ir.optimize.info.n nVar, final TE te, C1230Ob c1230Ob) throws Throwable {
        String str;
        com.android.tools.r8.graph.W0 w0;
        String str2;
        com.android.tools.r8.utils.y3 y3Var;
        com.android.tools.r8.graph.o5 o5Var;
        com.android.tools.r8.ir.optimize.info.n nVar2;
        C2409kl c2409kl2;
        com.android.tools.r8.graph.W0 w1;
        com.android.tools.r8.graph.o5 o5VarH = c2409kl.h();
        final com.android.tools.r8.graph.W0 w0E = o5VarH.e();
        C0729q2 c0729q2A = o5VarH.a();
        boolean z = N;
        if (!z && c0729q2A == null) {
            throw new AssertionError();
        }
        com.android.tools.r8.utils.y3 y3VarA = com.android.tools.r8.utils.y3.a(this.j, o5VarH.w());
        String strA = a(c2409kl, "Initial IR (SSA)", (String) null);
        BiConsumer<C2409kl, C0702m<?>> biConsumer = this.j.n1.Y;
        if (biConsumer != null) {
            biConsumer.accept(c2409kl, this.a);
        }
        if (this.j.f()) {
            y3VarA.a("Check for new-init issue");
            com.android.tools.r8.ir.optimize.F.a(c2409kl, this.a.a());
            y3VarA.c();
        }
        if (this.j.s()) {
            y3VarA.a("JDK-8272564 fix rewrite");
            com.android.tools.r8.ir.optimize.F.a(this.a, o5VarH, c2409kl);
            y3VarA.c();
        }
        boolean z2 = true;
        boolean z3 = this.j.W0 || o5VarH.f(this.a);
        if (z3) {
            this.k.getClass();
            com.android.tools.r8.ir.optimize.F.o(c2409kl);
        }
        if (this.o != null) {
            y3VarA.a("Lens rewrite");
            this.o.a(o5VarH, c2409kl, te);
            y3VarA.c();
        }
        if (!z && w0E.M1() && z3) {
            throw new AssertionError("Method already processed: " + o5VarH.w() + System.lineSeparator() + com.android.tools.r8.utils.R0.a());
        }
        if (!z && w0E.M1() && this.a.q() && this.a.f().o().c(o5VarH)) {
            throw new AssertionError("Unexpected reprocessing of method: " + o5VarH.w());
        }
        C3326x00 c3326x00 = this.u;
        if (c3326x00 != null && !c3326x00.a(c2409kl)) {
            if (!z && !this.a.q()) {
                throw new AssertionError();
            }
            if (!z && !this.j.n1.C) {
                throw new AssertionError();
            }
            this.j.e.warning(new StringDiagnostic("The method `" + w0E.r0() + "` does not type check and will be assumed to be unreachable."));
            o5VarH.e(this.a);
            return y3VarA;
        }
        if (!z) {
            c2409kl.d(this.a);
        }
        if (!z && !c2409kl.b(this.a)) {
            throw new AssertionError();
        }
        if (!this.a.a(o5VarH.e())) {
            com.android.tools.r8.graph.W wA1 = o5VarH.e().a1();
            if (!z) {
                wA1.getClass();
                if (wA1 instanceof com.android.tools.r8.graph.F5) {
                    throw new AssertionError();
                }
            }
            wA1.getClass();
            z2 = wA1 instanceof com.android.tools.r8.graph.Z;
        }
        if (z2) {
            if (!z && !this.a.q()) {
                throw new AssertionError();
            }
            y3VarA.a("Collect optimization info");
            a(o5VarH, c2409kl, C3585t.a(), nVar, te, C2133h6.a(), y3VarA);
            y3VarA.c();
            a(c2409kl, nVar);
            return y3VarA;
        }
        this.C.a(w0E, c2409kl, this.D, y3VarA);
        AbstractC3583s.a(this.a, c2409kl);
        if (this.v != null) {
            if (!z && !this.a.f().i()) {
                throw new AssertionError();
            }
            y3VarA.a("Rewrite service loaders");
            this.v.a(c2409kl, te, c1230Ob);
            y3VarA.c();
        }
        if (this.q != null) {
            y3VarA.a("Decouple identifier-name strings");
            this.q.a(c2409kl);
            y3VarA.c();
            if (!z && !c2409kl.b(this.a)) {
                throw new AssertionError();
            }
        }
        if (this.n != null) {
            y3VarA.a("Propagate member values");
            this.n.a(c2409kl);
            y3VarA.c();
        }
        if (this.w != null) {
            if (!z && !this.a.q()) {
                throw new AssertionError();
            }
            y3VarA.a("Remove switch maps");
            this.w.a(c2409kl);
            y3VarA.c();
        }
        C1536Yn c1536Yn = this.y;
        if (c1536Yn != null) {
            c1536Yn.a(c2409kl, o5VarH, te, c1230Ob);
            if (!z) {
                c2409kl.d(this.a);
            }
        }
        String strA2 = a(c2409kl, "IR after disable assertions (SSA)", strA);
        com.android.tools.r8.ir.optimize.info.a aVarI = o5VarH.B().i();
        if (aVarI.b() && this.a.H()) {
            I2.a(this.a.i0(), c2409kl, aVarI.a());
        }
        C3577p c3577p = this.A;
        if (c3577p != null) {
            c3577p.a(c2409kl, y3VarA);
        }
        String strA3 = a(c2409kl, "IR after inserting assume instructions (SSA)", strA2);
        y3VarA.a("Run proto shrinking tasks");
        this.a.b(new InterfaceC3905h3() { // from class: com.android.tools.r8.internal.sl$$ExternalSyntheticLambda2
            @Override // com.android.tools.r8.utils.InterfaceC3905h3
            public final void accept(Object obj) {
                ((C0831Aj) obj).a(w0E, c2409kl);
            }
        });
        String strA4 = a(c2409kl, "IR after generated extension registry shrinking (SSA)", strA3);
        this.a.d(new InterfaceC3905h3() { // from class: com.android.tools.r8.internal.sl$$ExternalSyntheticLambda3
            @Override // com.android.tools.r8.utils.InterfaceC3905h3
            public final void accept(Object obj) {
                ((C1158Lj) obj).a(c2409kl);
            }
        });
        y3VarA.c();
        String strA5 = a(c2409kl, "IR after generated message lite shrinking (SSA)", strA4);
        if (z3 || !this.j.P0().a || this.p == null) {
            str = "Collect optimization info";
            w0 = w0E;
            str2 = strA5;
        } else {
            y3VarA.a("Inlining");
            w0 = w0E;
            str2 = strA5;
            str = "Collect optimization info";
            this.p.a(c2409kl.h(), c2409kl, nVar, te, y3VarA);
            y3VarA.c();
            if (!z) {
                c2409kl.d(this.a);
            }
        }
        String strA6 = a(c2409kl, "IR after inlining (SSA)", str2);
        if (this.a.f().i()) {
            y3VarA.a("Rewrite to const class");
            com.android.tools.r8.ir.optimize.C0.a(this.a.i0(), c2409kl);
            y3VarA.c();
        }
        if (!z3) {
            com.android.tools.r8.utils.A1 a1 = this.j;
            if (a1.E || a1.n1.W) {
                y3VarA.a("Rewrite Class.getName");
                this.g.a(this.a, c2409kl);
                y3VarA.c();
            }
            y3VarA.a("Optimize const strings");
            this.g.a(c2409kl);
            this.g.b(c2409kl);
            y3VarA.c();
            y3VarA.a("Optimize library methods");
            this.a.W().a(c2409kl, te, c1230Ob);
            y3VarA.c();
            strA6 = a(c2409kl, "IR after class library method optimizer (SSA)", strA6);
            if (!z && !c2409kl.b(this.a)) {
                throw new AssertionError();
            }
        }
        if (!z) {
            c2409kl.d(this.a);
        }
        if (this.r != null) {
            if (!z) {
                c2409kl.d(this.a);
            }
            y3VarA.a("Devirtualize invoke interface");
            this.r.a(c2409kl);
            y3VarA.c();
            strA6 = a(c2409kl, "IR after devirtualizer (SSA)", strA6);
        }
        if (!z) {
            c2409kl.d(this.a);
        }
        y3VarA.a("Remove trivial type checks/casts");
        this.k.a(c2409kl, o5VarH, te, c1230Ob);
        y3VarA.c();
        if (this.w != null) {
            if (!z && !this.a.q()) {
                throw new AssertionError();
            }
            y3VarA.a("Rewrite constant enum methods");
            this.w.b(c2409kl);
            y3VarA.c();
        }
        y3VarA.a("Rewrite array length");
        this.k.i(c2409kl);
        y3VarA.c();
        y3VarA.a("Natural Int Loop Remover");
        C3574n0 c3574n0 = this.m;
        C0702m c0702m = this.a;
        c3574n0.getClass();
        C3574n0.a(c0702m, c2409kl);
        y3VarA.c();
        if (this.l != null) {
            y3VarA.a("Rewrite AssertionError");
            this.l.a(c2409kl, te, c1230Ob);
            y3VarA.c();
        }
        y3VarA.a("Run CSE");
        this.k.a(c2409kl);
        y3VarA.c();
        y3VarA.a("Simplify arrays");
        this.k.m(c2409kl);
        y3VarA.c();
        y3VarA.a("Rewrite move result");
        this.k.j(c2409kl);
        y3VarA.c();
        if (this.j.F && !z3) {
            y3VarA.a("Rewrite string concat");
            C2709oX.a(this.a, c2409kl);
            y3VarA.c();
        }
        y3VarA.a("Split range invokes");
        this.k.q(c2409kl);
        y3VarA.c();
        y3VarA.a("Propagate sparse conditionals");
        new MV(this.a, c2409kl).b();
        y3VarA.c();
        y3VarA.a("Rewrite always throwing instructions");
        this.k.e(c2409kl);
        y3VarA.c();
        y3VarA.a("Simplify control flow");
        if (this.k.n(c2409kl)) {
            y3VarA.a("Remove trivial type checks/casts");
            this.k.a(c2409kl, o5VarH, te, c1230Ob);
            y3VarA.c();
        }
        y3VarA.c();
        if (this.j.W) {
            y3VarA.a("Remove const numbers");
            this.k.g(c2409kl);
            y3VarA.c();
        }
        if (com.android.tools.r8.ir.optimize.B0.a(this.a, c2409kl)) {
            y3VarA.a("Remove field loads");
            new com.android.tools.r8.ir.optimize.B0(this.a, c2409kl).c();
            y3VarA.c();
        }
        if (this.j.n1.P) {
            for (C3332x4 c3332x4 : c2409kl.d) {
                if (c3332x4.m().N1()) {
                    c3332x4.m().Q().M2();
                }
            }
        }
        if (!z3) {
            y3VarA.a("Rewrite throw NPE");
            this.k.l(c2409kl);
            y3VarA.c();
            strA6 = a(c2409kl, "IR after rewrite throw null (SSA)", strA6);
        }
        y3VarA.a("Optimize class initializers");
        C3585t c3585tA = this.c.a(c2409kl, nVar);
        y3VarA.c();
        String strA7 = a(c2409kl, "IR after class initializer optimisation (SSA)", strA6);
        this.D.a(c2409kl, y3VarA);
        boolean z4 = N;
        if (!z4 && !c2409kl.b(this.a)) {
            throw new AssertionError();
        }
        String strA8 = a(c2409kl, "IR after dead code removal (SSA)", strA7);
        if (!z4) {
            c2409kl.d(this.a);
        }
        String strA9 = a(c2409kl, "IR before class inlining (SSA)", strA8);
        if (this.i != null) {
            y3VarA.a("Inline classes");
            if (!z4 && (!this.j.P0().a || this.p == null)) {
                throw new AssertionError();
            }
            y3Var = y3VarA;
            w1 = w0;
            o5Var = o5VarH;
            nVar2 = nVar;
            c2409kl2 = c2409kl;
            this.i.a(this.a.i0(), this.k, this.g, this.w, c2409kl.h(), c2409kl, nVar, te, c1230Ob, this.p, new com.android.tools.r8.utils.P1(new Supplier() { // from class: com.android.tools.r8.internal.sl$$ExternalSyntheticLambda4
                @Override // java.util.function.Supplier
                public final Object get() {
                    return this.f$0.a(c2409kl, te);
                }
            }));
            y3Var.c();
            if (!z4 && !c2409kl2.b(this.a)) {
                throw new AssertionError();
            }
            if (!z4) {
                c2409kl2.d(this.a);
            }
        } else {
            y3Var = y3VarA;
            o5Var = o5VarH;
            nVar2 = nVar;
            c2409kl2 = c2409kl;
            w1 = w0;
        }
        String strA10 = a(c2409kl2, "IR after class inlining (SSA)", strA9);
        if (!z4) {
            c2409kl2.d(this.a);
        }
        String strA11 = a(c2409kl2, "IR after interface method rewriting (SSA)", strA10);
        com.android.tools.r8.utils.y3 y3Var2 = y3Var;
        this.b.a(c2409kl2, y3Var2);
        if (!z4) {
            c2409kl2.d(this.a);
        }
        String strA12 = a(c2409kl2, "IR after outline handler (SSA)", strA11);
        if (c2409kl.k().d()) {
            y3Var2.a("Remove string switch");
            this.t.b(c2409kl2);
            y3Var2.c();
        }
        if (!this.j.X0()) {
            y3Var2.a("Canonicalize constants");
            com.android.tools.r8.ir.optimize.H h = new com.android.tools.r8.ir.optimize.H(this.a, this.k, o5Var, c2409kl2);
            h.a();
            y3Var2.c();
            String strA13 = a(c2409kl2, "IR after constant canonicalization (SSA)", strA12);
            y3Var2.a("Create constants for literal instructions");
            this.k.r(c2409kl2);
            y3Var2.c();
            String strA14 = a(c2409kl2, "IR after constant literals (SSA)", strA13);
            y3Var2.a("Shorten live ranges");
            this.k.a(c2409kl2, h);
            y3Var2.c();
            strA12 = a(c2409kl2, "IR after shorten live ranges (SSA)", strA14);
        }
        com.android.tools.r8.ir.optimize.D0 d0 = this.z;
        if (d0 != null) {
            d0.a(o5Var, c2409kl2, y3Var2);
        }
        y3Var2.a("Canonicalize idempotent calls");
        this.h.a(c2409kl2);
        y3Var2.c();
        String strA15 = a(c2409kl2, "IR after idempotent function call canonicalization (SSA)", strA12);
        if (this.j.b(w1) && !w1.M1()) {
            this.k.a(w1, c2409kl2);
            if (!z4 && !c2409kl2.b(this.a)) {
                throw new AssertionError();
            }
        }
        String strA16 = a(c2409kl2, "IR after argument type logging (SSA)", strA15);
        if (!z4) {
            c2409kl2.d(this.a);
        }
        this.D.a(c2409kl2, y3Var2);
        C2133h6.a aVarA = C2133h6.a();
        if (this.a.q()) {
            y3Var2.a(str);
            a(o5Var, c2409kl, c3585tA, nVar, te, aVarA, y3Var2);
            y3Var2.c();
        }
        y3Var2.a("Redundant catch/rethrow elimination");
        this.k.getClass();
        com.android.tools.r8.ir.optimize.F.f(c2409kl);
        y3Var2.c();
        String strA17 = a(c2409kl2, "IR after redundant catch/rethrow elimination (SSA)", strA16);
        if (this.A != null) {
            y3Var2.a("Remove assume instructions");
            com.android.tools.r8.ir.optimize.F.b((C0702m<?>) this.a, c2409kl2);
            y3Var2.c();
            if (!z4 && !c2409kl2.b(this.a)) {
                throw new AssertionError();
            }
            this.k.j(c2409kl2);
        }
        if (!z4) {
            c2409kl.G();
        }
        if (!z4) {
            c2409kl2.d(this.a);
        }
        a(c2409kl2, "Optimized IR (SSA)", a(c2409kl2, "IR after computation of optimization info summary (SSA)", strA17));
        y3Var2.a("Finalize IR");
        a(c2409kl2, nVar2, aVarA.a(), y3Var2);
        y3Var2.c();
        return y3Var2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public InterfaceC3556e0 a(C2409kl c2409kl, TE te) {
        C3546c0 c3546c0 = this.p;
        return c3546c0.a(c2409kl.h(), te, -1, c3546c0.a(te));
    }

    public final void a(final com.android.tools.r8.graph.o5 o5Var, final C2409kl c2409kl, C3585t c3585t, com.android.tools.r8.ir.optimize.info.n nVar, final TE te, C2133h6.a aVar, final com.android.tools.r8.utils.y3 y3Var) throws Throwable {
        AbstractC1049Hn abstractC1049HnA;
        this.a.a(new InterfaceC3905h3() { // from class: com.android.tools.r8.internal.sl$$ExternalSyntheticLambda5
            @Override // com.android.tools.r8.utils.InterfaceC3905h3
            public final void accept(Object obj) {
                ((C3476z2) obj).a(o5Var, c2409kl, te, y3Var);
            }
        });
        if (te.c()) {
            this.x.a(c2409kl, te);
        }
        C3546c0 c3546c0 = this.p;
        if (c3546c0 != null) {
            c3546c0.a(o5Var, c2409kl, te, y3Var);
        }
        if (this.f != null) {
            y3Var.a("Analyze library method overrides");
            this.f.a(c2409kl);
            y3Var.c();
        }
        if (this.e != null) {
            y3Var.a("Analyze field accesses");
            this.e.a(c2409kl, aVar, nVar, te);
            this.e.a(c3585t);
            y3Var.c();
        }
        if (this.a.a(c2409kl.h()).c((com.android.tools.r8.shaking.K0) this.j)) {
            return;
        }
        OW owA = null;
        if (!o5Var.e().A1()) {
            abstractC1049HnA = null;
        } else if (o5Var.e().u1()) {
            abstractC1049HnA = null;
            owA = IW.a(this.a, c2409kl, c3585t, nVar, y3Var);
        } else {
            abstractC1049HnA = C1162Ln.a(this.a, c2409kl, c3585t, nVar, y3Var);
        }
        this.x.a(o5Var.a(), owA);
        if (this.a.Y().x1().c()) {
            this.a.Z().g.a(o5Var.a(), owA);
        }
        this.E.a(o5Var, c2409kl, nVar, this.B, abstractC1049HnA, te, y3Var);
    }

    public final void a(C2409kl c2409kl, com.android.tools.r8.ir.optimize.info.n nVar, com.android.tools.r8.utils.y3 y3Var) {
        C2544mY c2544mY = this.t;
        if (c2544mY != null) {
            c2544mY.b(c2409kl);
        }
        this.D.a(c2409kl, y3Var);
        a(c2409kl, nVar, C2133h6.b(), y3Var);
    }

    public final void a(C2409kl c2409kl, com.android.tools.r8.ir.optimize.info.n nVar, C2133h6 c2133h6, com.android.tools.r8.utils.y3 y3Var) {
        C0759u2 c0759u2;
        if (this.j.n1.c) {
            c2409kl = a(a(c2409kl, new C2139hC(), "indirect phis", y3Var), new RL(), "inline phis", y3Var);
        }
        com.android.tools.r8.utils.A1 a1 = this.j;
        if (a1.f instanceof ClassFileConsumer) {
            y3Var.a("IR->CF");
            com.android.tools.r8.graph.o5 o5VarH = c2409kl.h();
            C0702m c0702m = this.a;
            com.android.tools.r8.ir.optimize.M m = this.D;
            com.android.tools.r8.graph.o5 o5VarH2 = c2409kl.h();
            y3Var.a("Finalize CF code");
            C0753u c0753uA = new L6(c0702m, o5VarH2, c2409kl, c2133h6).a(m, y3Var);
            y3Var.c();
            o5VarH.a(c0753uA, this.a);
            a(c2409kl, nVar);
            y3Var.c();
        } else {
            boolean z = N;
            if (!z && !a1.Y0()) {
                throw new AssertionError();
            }
            y3Var.a("IR->DEX");
            com.android.tools.r8.graph.o5 o5VarH3 = c2409kl.h();
            com.android.tools.r8.graph.W0 w0E = o5VarH3.e();
            o5VarH3.a(new C3455yl(this.a, this.D).a(c2409kl, c2133h6, y3Var), this.a);
            a(c2409kl, nVar);
            synchronized (this) {
                com.android.tools.r8.graph.W wA1 = w0E.a1();
                if (!z && !wA1.C0()) {
                    throw new AssertionError();
                }
                C0759u2 c0759u2B = wA1.u0().B();
                if (c0759u2B != null && ((c0759u2 = this.H) == null || c0759u2B.a(c0759u2) > 0)) {
                    this.H = c0759u2B;
                }
            }
            y3Var.c();
        }
        com.android.tools.r8.graph.o5 o5VarH4 = c2409kl.h();
        if (this.j.p0.size() <= 0 || !this.j.p0.contains(o5VarH4.getReference().r0())) {
            return;
        }
        String strV0 = o5VarH4.e().V0();
        System.out.println();
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("After finalization");
        System.out.println("-----------------------------------------------------------------------");
        System.out.println(strV0);
        System.out.println("-----------------------------------------------------------------------");
    }

    private C2409kl a(C2409kl c2409kl, AbstractC2290jC abstractC2290jC, String str, com.android.tools.r8.utils.y3 y3Var) {
        y3Var.a("IR->LIR (" + str + Constant.AFTER_QUTO);
        RB rbA = C1633al.a(this.a.a(), c2409kl, abstractC2290jC.a());
        y3Var.c();
        y3Var.a("LIR->IR (" + str + Constant.AFTER_QUTO);
        C2409kl c2409klA = LB.a(c2409kl.h(), rbA, abstractC2290jC.a(rbA), this.a);
        y3Var.c();
        return c2409klA;
    }

    /* JADX WARN: Code duplicated, block: B:18:0x004a  */
    public final void a(C2409kl c2409kl, com.android.tools.r8.ir.optimize.info.n nVar) {
        com.android.tools.r8.ir.optimize.V vA;
        com.android.tools.r8.graph.o5 o5VarH = c2409kl.h();
        if (this.j.P0().a && this.p != null && !o5VarH.e().u1()) {
            if (!o5VarH.a().f(this.a)) {
                C3762m1 c3762m1A = this.a.a(o5VarH);
                if (c3762m1A.h(this.j) || c3762m1A.f(this.j)) {
                    vA = this.p.a(c2409kl);
                } else {
                    vA = com.android.tools.r8.ir.optimize.V.c;
                }
            } else {
                vA = com.android.tools.r8.ir.optimize.V.c;
            }
        } else {
            vA = com.android.tools.r8.ir.optimize.V.c;
        }
        nVar.a(o5VarH.e(), vA);
    }

    public final String a(C2409kl c2409kl, String str, String str2) {
        if (this.j.p0.size() <= 0 || !this.j.p0.contains(c2409kl.u().getReference().r0())) {
            return str2;
        }
        String string = c2409kl.toString();
        System.out.println();
        System.out.println("-----------------------------------------------------------------------");
        System.out.println(str);
        System.out.println("-----------------------------------------------------------------------");
        if (str2 != null && str2.equals(string)) {
            System.out.println("Unchanged");
        } else {
            System.out.println(string);
        }
        System.out.println("-----------------------------------------------------------------------");
        return string;
    }
}
