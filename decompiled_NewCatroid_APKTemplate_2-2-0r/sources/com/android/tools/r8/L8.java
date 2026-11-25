package com.android.tools.r8;

import com.android.tools.r8.dex.C0408c;
import com.android.tools.r8.graph.C0612b4;
import com.android.tools.r8.graph.C0664h;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.internal.C1239Oj;
import com.android.tools.r8.internal.C3334x6;
import com.android.tools.r8.internal.C3448ye;
import com.android.tools.r8.internal.G00;
import com.android.tools.r8.internal.PM;
import com.android.tools.r8.naming.C3663t0;
import com.android.tools.r8.naming.V0;
import com.android.tools.r8.shaking.C3728h;
import com.android.tools.r8.shaking.E1;
import com.android.tools.r8.synthesis.C3861t;
import com.android.tools.r8.utils.A1;
import com.android.tools.r8.utils.C3879c3;
import com.android.tools.r8.utils.C3945p;
import com.android.tools.r8.utils.R0;
import com.android.tools.r8.utils.R2;
import com.android.tools.r8.utils.S0;
import com.android.tools.r8.utils.Y2;
import com.android.tools.r8.utils.y3;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class L8 {
    static final /* synthetic */ boolean a = true;

    static void a(C3945p c3945p, A1 a1, boolean z, D8Command d8Command, R8Command r8Command) throws CompilationFailedException {
        a(c3945p, a1, z, d8Command, r8Command, C3879c3.a(a1));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public static void b(String[] strArr) throws CompilationFailedException {
        L8Command l8Command = (L8Command) L8Command.parse(strArr, com.android.tools.r8.origin.a.e).build();
        if (l8Command.isPrintHelp()) {
            R2.a();
            System.out.println(Q.a());
        } else {
            if (!l8Command.isPrintVersion()) {
                run(l8Command);
                return;
            }
            System.out.println("L8 " + Version.getVersionString());
        }
    }

    public static void main(final String[] strArr) {
        if (strArr.length == 0) {
            throw new RuntimeException(Y2.a("Invalid invocation.", Q.a()));
        }
        R0.a(new S0() { // from class: com.android.tools.r8.L8$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.utils.S0
            public final void run() throws CompilationFailedException {
                L8.b(strArr);
            }
        });
    }

    public static void run(L8Command l8Command) throws CompilationFailedException {
        a(l8Command.a(), l8Command.b(), l8Command.k(), l8Command.i(), l8Command.j());
    }

    private static void a(final C3945p c3945p, final A1 a1, boolean z, D8Command d8Command, R8Command r8Command, final ExecutorService executorService) {
        try {
            R0.b(a1.e, new R0.a() { // from class: com.android.tools.r8.L8$$ExternalSyntheticLambda1
                @Override // com.android.tools.r8.utils.R0.a
                public final void run() throws IOException {
                    L8.a(a1, c3945p, executorService);
                }
            });
            if (z) {
                R8.run(r8Command, executorService);
            } else if (d8Command != null) {
                D8.run(d8Command, executorService);
            }
        } finally {
            executorService.shutdown();
        }
    }

    public static void run(L8Command l8Command, ExecutorService executorService) throws CompilationFailedException {
        a(l8Command.a(), l8Command.b(), l8Command.k(), l8Command.i(), l8Command.j(), executorService);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(A1 a1, C3945p c3945p, ExecutorService executorService) throws IOException {
        boolean z = a;
        if (!z && !a1.T0()) {
            throw new AssertionError();
        }
        if (!z && a1.J) {
            throw new AssertionError();
        }
        a1.J = true;
        if (!z && !a1.O) {
            throw new AssertionError();
        }
        a1.O = false;
        if (!z && !a1.P) {
            throw new AssertionError();
        }
        a1.P = false;
        if (!z && a1.B0) {
            throw new AssertionError();
        }
        a1.B0 = true;
        a1.m0 = com.android.tools.r8.dex.U.b.L8;
        y3 y3VarA = y3.a(a1, "L8 desugaring");
        if (!z && !a1.T0()) {
            throw new AssertionError();
        }
        try {
            try {
                a1.z0 = false;
                C0702m c0702mA = a(c3945p, a1, executorService, y3VarA);
                C3448ye.a(c0702mA);
                if (!a1.L) {
                    C3728h.a(c0702mA);
                }
                new PM(c0702mA, y3VarA).a(c0702mA, executorService);
                C3861t.a(c0702mA, y3VarA, executorService);
                c0702mA.a(C3663t0.a(c0702mA));
                c0702mA.a(V0.a(c0702mA));
                new C1239Oj(c0702mA, null).a(c0702mA.f().d(), executorService);
                new C3334x6(c0702mA, a1.A0()).a((ClassFileConsumer) a1.f);
                a1.v1();
                c3945p.b(a1.e);
                a1.G1();
                if (a1.m) {
                    y3VarA.d();
                }
                a1.J = false;
                a1.O = true;
                a1.P = true;
                a1.B0 = false;
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
        } catch (Throwable th) {
            c3945p.b(a1.e);
            a1.G1();
            if (a1.m) {
                y3VarA.d();
            }
            throw th;
        }
    }

    private static C0702m a(C3945p c3945p, A1 a1, ExecutorService executorService, y3 y3Var) throws Throwable {
        C0612b4 c0612b4A = new C0408c(c3945p, a1, y3Var).a(executorService);
        a1.a(y3Var, c0612b4A);
        G00 g00J0 = a1.J0();
        return C0702m.a(C0664h.a(new E1(a1).a(c0612b4A, g00J0), com.android.tools.r8.synthesis.y.e()), g00J0);
    }
}
