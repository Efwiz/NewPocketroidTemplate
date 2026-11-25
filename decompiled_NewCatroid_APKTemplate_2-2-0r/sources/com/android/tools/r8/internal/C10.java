package com.android.tools.r8.internal;

import com.android.tools.r8.errors.UnsupportedFeatureDiagnostic;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0737r2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.position.MethodPosition;
import com.android.tools.r8.utils.EnumC3891f;
import java.util.Set;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract class C10 {
    static final /* synthetic */ boolean e = true;
    final C0702m a;
    final String b;
    final EnumC3891f c;
    final Set d = OU.a();

    C10(C0702m c0702m, String str, EnumC3891f enumC3891f) {
        this.a = c0702m;
        this.b = str;
        this.c = enumC3891f;
    }

    static C1507Xl b(C1507Xl c1507Xl, C0767v2 c0767v2) {
        Object c1668b7;
        if (!c0767v2.Y0()) {
            boolean z = e;
            if (!z && c0767v2.Y0()) {
                throw new AssertionError();
            }
            if (c0767v2.W0()) {
                EnumC1812d20 enumC1812d20 = EnumC1812d20.OBJECT;
                c1668b7 = new C1818d7(0L, EnumC1812d20.a((char) c0767v2.e.e[0]));
            } else {
                if (!z && !c0767v2.X0()) {
                    throw new AssertionError();
                }
                c1668b7 = new C1668b7();
            }
            c1507Xl.a(c1668b7);
        }
        return c1507Xl;
    }

    abstract UnsupportedFeatureDiagnostic a(Origin origin, MethodPosition methodPosition);

    abstract C3224ve a(B7 b7);

    final void a(com.android.tools.r8.graph.o5 o5Var) {
        if (this.d.add(o5Var.getReference())) {
            UnsupportedFeatureDiagnostic unsupportedFeatureDiagnosticA = a(o5Var.getOrigin(), MethodPosition.create(o5Var));
            if (!e && ((unsupportedFeatureDiagnosticA.getSupportedApiLevel() != -1 || this.c != null) && unsupportedFeatureDiagnosticA.getSupportedApiLevel() != this.c.d())) {
                throw new AssertionError();
            }
            this.a.Y().e.warning(unsupportedFeatureDiagnosticA);
        }
    }

    final void a(C1230Ob c1230Ob, G7 g7, C1507Xl c1507Xl) {
        c1507Xl.a((Object[]) new B7[]{a(), new I7(184, com.android.tools.r8.ir.optimize.U0.d(this.a, g7, c1230Ob).b().getReference(), false), new D8(D8.a.Pop)});
    }

    final C1891e7 a() {
        return new C1891e7(this.a.a().c("Instruction is unrepresentable in DEX " + this.a.Y().B0().c() + ": " + this.b));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(C1507Xl c1507Xl, C0767v2 c0767v2) {
        if (!e && c0767v2.Y0()) {
            throw new AssertionError();
        }
        c1507Xl.a(new D8(c0767v2.Z0() ? D8.a.Pop2 : D8.a.Pop));
    }

    static void a(C0737r2 c0737r2, final C1507Xl c1507Xl) {
        c0737r2.u0().d(new Consumer() { // from class: com.android.tools.r8.internal.C10$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C10.a(c1507Xl, (C0767v2) obj);
            }
        });
    }
}
