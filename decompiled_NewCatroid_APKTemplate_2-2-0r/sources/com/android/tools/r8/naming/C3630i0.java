package com.android.tools.r8.naming;

import com.android.tools.r8.graph.B5;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.shaking.C3733i;
import com.android.tools.r8.utils.y3;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

/* JADX INFO: renamed from: com.android.tools.r8.naming.i0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C3630i0 {
    static final /* synthetic */ boolean b = true;
    private final C0702m a;

    public C3630i0(C0702m<C3733i> c0702m) {
        this.a = c0702m;
    }

    public final void a(ExecutorService executorService, y3 y3Var) {
        y3Var.a("ReplaceDexItemBasedConstString");
        new B(this.a, AbstractC3649o0.a()).b(executorService);
        y3Var.c();
    }

    public AbstractC3649o0 b(ExecutorService executorService, y3 y3Var) throws ExecutionException {
        boolean z = b;
        if (!z && !this.a.Y().k1()) {
            throw new AssertionError();
        }
        B5 b5A = AbstractC3633j0.a(this.a);
        y3Var.a("ComputeInterfaces");
        List listA = b5A.a();
        y3Var.c();
        y3Var.a("MinifyClasses");
        C0702m c0702m = this.a;
        C3614d c3614dA = new C3618f(c0702m, c0702m.Y().Z.isEmpty() ? new C3617e0(this.a) : new C3615d0(this.a), ((C3733i) this.a.f()).e()).a(y3Var);
        y3Var.c();
        if (!z) {
            new C3610b0(this.a, c3614dA, T.a(), C3666v.a()).a(((C3733i) this.a.f()).d(), this.a.a());
        }
        C3628h0 c3628h0 = new C3628h0(this.a);
        y3Var.a("MinifyMethods");
        T tA = new V(this.a, c3628h0).a(listA, b5A, executorService, y3Var);
        y3Var.c();
        if (!z) {
            new C3610b0(this.a, c3614dA, tA, C3666v.a()).a(((C3733i) this.a.f()).d(), this.a.a());
        }
        y3Var.a("MinifyFields");
        C3666v c3666vA = new C3669x(this.a, b5A, c3628h0).a(listA, y3Var);
        y3Var.c();
        C3610b0 c3610b0 = new C3610b0(this.a, c3614dA, tA, c3666vA);
        if (!z) {
            c3610b0.a(((C3733i) this.a.f()).d(), this.a.a());
        }
        y3Var.a("MinifyIdentifiers");
        new B(this.a, c3610b0).c(executorService);
        y3Var.c();
        return c3610b0;
    }
}
