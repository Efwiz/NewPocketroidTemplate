package com.android.tools.r8;

import com.android.tools.r8.dex.C0408c;
import com.android.tools.r8.dex.C0548k;
import com.android.tools.r8.graph.AbstractC0674i0;
import com.android.tools.r8.graph.C0612b4;
import com.android.tools.r8.graph.C0664h;
import com.android.tools.r8.graph.C0695l;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.Y0;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.internal.C0931Dn;
import com.android.tools.r8.internal.C0959Ek;
import com.android.tools.r8.internal.C1;
import com.android.tools.r8.internal.C1239Oj;
import com.android.tools.r8.internal.C1521Xz;
import com.android.tools.r8.internal.C1910eI;
import com.android.tools.r8.internal.C2854qW;
import com.android.tools.r8.internal.C3033t2;
import com.android.tools.r8.internal.C3334x6;
import com.android.tools.r8.internal.C3448ye;
import com.android.tools.r8.internal.G00;
import com.android.tools.r8.internal.PM;
import com.android.tools.r8.internal.W9;
import com.android.tools.r8.ir.optimize.C3553e;
import com.android.tools.r8.naming.AbstractC3649o0;
import com.android.tools.r8.naming.C3663t0;
import com.android.tools.r8.naming.O0;
import com.android.tools.r8.naming.V0;
import com.android.tools.r8.shaking.C3743k;
import com.android.tools.r8.shaking.g4;
import com.android.tools.r8.synthesis.C3861t;
import com.android.tools.r8.utils.A1;
import com.android.tools.r8.utils.C3879c3;
import com.android.tools.r8.utils.C3945p;
import com.android.tools.r8.utils.InterfaceC3885d3;
import com.android.tools.r8.utils.InterfaceC3949p3;
import com.android.tools.r8.utils.R0;
import com.android.tools.r8.utils.S0;
import com.android.tools.r8.utils.StringDiagnostic;
import com.android.tools.r8.utils.Y2;
import com.android.tools.r8.utils.y3;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;
import java.util.function.Supplier;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class D8 {
    static final /* synthetic */ boolean a = true;

    private static void a(C0702m c0702m) {
        C3743k c3743k = new C3743k();
        C1 c1B = c0702m.b();
        C0722p1 c0722p1A = c0702m.a();
        A1 a1Y = c0702m.Y();
        if (a1Y.Y0()) {
            Y0 y0 = c0722p1A.C4.d;
            long jD = a1Y.B0().d();
            c1B.getClass();
            c3743k.a(y0, new C1910eI(jD, 2147483647L)).a(c0722p1A.C4.d);
        }
        c0702m.a(c3743k.a());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void c(C3945p c3945p, A1 a1, ExecutorService executorService) {
        try {
            d(c3945p, a1, executorService);
        } finally {
            executorService.shutdown();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void d(C3945p c3945p, final A1 a1, ExecutorService executorService) throws IOException {
        if (a1.n) {
            System.gc();
            System.gc();
            Runtime runtime = Runtime.getRuntime();
            System.out.println("D8 is running with total memory:" + runtime.totalMemory());
            System.out.println("D8 is running with free memory:" + runtime.freeMemory());
            System.out.println("D8 is running with max memory:" + runtime.maxMemory());
        }
        y3 y3VarA = y3.a(a1, "D8 8.1.56");
        try {
            try {
                y3VarA.a("Pre conversion");
                boolean z = a;
                if (!z && !com.android.tools.r8.utils.E.a(a1, new Supplier() { // from class: com.android.tools.r8.D8$$ExternalSyntheticLambda17
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        return D8.a(a1);
                    }
                })) {
                    throw new AssertionError();
                }
                y3VarA.a("Read input app");
                final C0702m<C0664h> c0702mA = a(c3945p, a1, executorService, y3VarA);
                y3VarA.c();
                y3VarA.a("Initialize assume info collection");
                a(c0702mA);
                y3VarA.c();
                y3VarA.a("Desugared library amend");
                C3448ye.a(c0702mA);
                y3VarA.c();
                y3VarA.a("Collect input synthetics");
                com.android.tools.r8.synthesis.D.a(c0702mA);
                y3VarA.c();
                if (C3553e.a(a1)) {
                    final W9 w9 = new W9(c0702mA, com.android.tools.r8.ir.optimize.info.r.b());
                    C3879c3.a(c0702mA.f().d(), new Consumer() { // from class: com.android.tools.r8.D8$$ExternalSyntheticLambda18
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            D8.a(w9, (C0729q2) obj);
                        }
                    }, executorService);
                }
                if (a1.n1.v0) {
                    c0702mA.a(C0695l.a(c0702mA).a());
                }
                y3VarA.c();
                new PM(c0702mA, y3VarA).a(c0702mA, executorService);
                y3VarA.a("Post conversion");
                c3945p.c();
                if (a1.L0()) {
                    System.out.println("Finished compilation with method filter: ");
                    ((AbstractC1639am) a1.r0).forEach(new Consumer() { // from class: com.android.tools.r8.D8$$ExternalSyntheticLambda1
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            D8.a((String) obj);
                        }
                    });
                }
                boolean zF = c0702mA.f().b().f().f();
                boolean zG = c0702mA.f().b().f().g();
                com.android.tools.r8.dex.U uA0 = zF ? a1.A0() : null;
                y3VarA.a("Run inspections", new InterfaceC3885d3() { // from class: com.android.tools.r8.D8$$ExternalSyntheticLambda2
                    @Override // com.android.tools.r8.utils.InterfaceC3885d3
                    public final void a() {
                        D8.a(a1, c0702mA);
                    }
                });
                y3VarA.a("Create prefix rewriting lens", new InterfaceC3885d3() { // from class: com.android.tools.r8.D8$$ExternalSyntheticLambda3
                    @Override // com.android.tools.r8.utils.InterfaceC3885d3
                    public final void a() {
                        D8.b(c0702mA);
                    }
                });
                y3VarA.a("Create record rewriting lens", new InterfaceC3885d3() { // from class: com.android.tools.r8.D8$$ExternalSyntheticLambda4
                    @Override // com.android.tools.r8.utils.InterfaceC3885d3
                    public final void a() {
                        D8.c(c0702mA);
                    }
                });
                if (a1.Y0() && zG && zF && c0702mA.t.a()) {
                    y3VarA.a("Rewrite non-dex inputs");
                    C0612b4 c0612b4A = a(c0702mA, c3945p, executorService, uA0, y3VarA);
                    y3VarA.c();
                    c0702mA.d(new C0664h(c0702mA.f().g().a(c0612b4A), c0702mA.f().f()));
                    c0702mA.a(AbstractC3649o0.a());
                } else if (a1.Y0() && zG) {
                    c0702mA.a(AbstractC3649o0.a());
                }
                if (a1.Y0() && !a1.o1.isEmpty()) {
                    y3VarA.a("Generate main-dex list");
                    c0702mA.a().c();
                    c0702mA.d(c0702mA.f().a(new GenerateMainDexList(a1).traceMainDexForD8(c0702mA, executorService)));
                    y3VarA.c();
                }
                c0702mA.a(c0702mA.s().b(c0702mA));
                if (!z && !c0702mA.C().c()) {
                    throw new AssertionError();
                }
                a(c0702mA, y3VarA, executorService);
                y3VarA.a("Create MethodHandle.Lookup rewriting lens", new InterfaceC3885d3() { // from class: com.android.tools.r8.D8$$ExternalSyntheticLambda5
                    @Override // com.android.tools.r8.utils.InterfaceC3885d3
                    public final void a() {
                        D8.d(c0702mA);
                    }
                });
                y3VarA.c();
                SyntheticInfoConsumer syntheticInfoConsumerH0 = c0702mA.Y().H0();
                if (syntheticInfoConsumerH0 != null && c0702mA.Y().t0) {
                    c0702mA.D().a(syntheticInfoConsumerH0);
                    syntheticInfoConsumerH0.finished();
                }
                if (a1.X0()) {
                    new C3334x6(c0702mA, uA0).a(a1.s0(), c3945p);
                } else {
                    C0548k.a(c0702mA, uA0).a(c3945p, executorService);
                }
                a1.v1();
                c3945p.b(a1.e);
                a1.G1();
                if (a1.m) {
                    y3VarA.d();
                }
            } catch (ExecutionException e) {
                throw R0.a(e);
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

    public static void main(final String[] strArr) {
        if (strArr.length == 0) {
            throw new RuntimeException(Y2.a("Invalid invocation.", C3675p.b()));
        }
        R0.a(new S0() { // from class: com.android.tools.r8.D8$$ExternalSyntheticLambda8
            @Override // com.android.tools.r8.utils.S0
            public final void run() throws CompilationFailedException {
                D8.b(strArr);
            }
        });
    }

    public static void run(D8Command d8Command) throws CompilationFailedException {
        final C3945p c3945pA = d8Command.a();
        final A1 a1B = d8Command.b();
        final ExecutorService executorServiceA = C3879c3.a(a1B);
        R0.b(d8Command.g(), new R0.a() { // from class: com.android.tools.r8.D8$$ExternalSyntheticLambda16
            @Override // com.android.tools.r8.utils.R0.a
            public final void run() {
                D8.a(c3945pA, a1B, executorServiceA);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public static void b(String[] strArr) throws CompilationFailedException {
        D8Command d8Command = (D8Command) D8Command.parse(strArr, com.android.tools.r8.origin.a.e).build();
        if (d8Command.isPrintHelp()) {
            System.out.println(C3675p.b());
            return;
        }
        if (!d8Command.isPrintVersion()) {
            a(d8Command.a(), d8Command.b());
        } else {
            System.out.println("D8 " + Version.getVersionString());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void c(C0702m c0702m) {
        c0702m.a(O0.a(c0702m));
    }

    public static void run(D8Command d8Command, final ExecutorService executorService) throws CompilationFailedException {
        final C3945p c3945pA = d8Command.a();
        final A1 a1B = d8Command.b();
        R0.b(d8Command.g(), new R0.a() { // from class: com.android.tools.r8.D8$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.utils.R0.a
            public final void run() throws IOException {
                D8.d(c3945pA, a1B, executorService);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b(C0702m c0702m) {
        c0702m.a(C3663t0.a(c0702m));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b(C0702m c0702m, ExecutorService executorService) {
        new C1521Xz(c0702m).a(executorService);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C3945p c3945p, A1 a1, ExecutorService executorService) {
        try {
            d(c3945p, a1, executorService);
        } finally {
            executorService.shutdown();
        }
    }

    static void a(final C3945p c3945p, final A1 a1) throws CompilationFailedException {
        final ExecutorService executorServiceA = C3879c3.a(a1);
        R0.b(a1.e, new R0.a() { // from class: com.android.tools.r8.D8$$ExternalSyntheticLambda9
            @Override // com.android.tools.r8.utils.R0.a
            public final void run() {
                D8.c(c3945p, a1, executorServiceA);
            }
        });
    }

    private static C0702m a(C3945p c3945p, final A1 a1, ExecutorService executorService, final y3 y3Var) throws Throwable {
        y3Var.a("Application read");
        final C0408c c0408c = new C0408c(c3945p, a1, y3Var);
        final C0612b4 c0612b4A = c0408c.a(executorService);
        y3Var.c();
        y3Var.a("Load desugared lib");
        a1.a(y3Var, c0612b4A);
        y3Var.c();
        final G00 g00J0 = a1.J0();
        final C0664h c0664h = (C0664h) y3Var.a("Create app-info", new InterfaceC3949p3() { // from class: com.android.tools.r8.D8$$ExternalSyntheticLambda6
            @Override // com.android.tools.r8.utils.InterfaceC3949p3
            public final Object get() {
                return D8.a(c0612b4A, a1, c0408c);
            }
        });
        return (C0702m) y3Var.a("Create app-view", new InterfaceC3949p3() { // from class: com.android.tools.r8.D8$$ExternalSyntheticLambda7
            @Override // com.android.tools.r8.utils.InterfaceC3949p3
            public final Object get() {
                return C0702m.a(c0664h, g00J0, y3Var);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Boolean a(A1 a1) {
        return Boolean.valueOf(!a1.n1.s0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(W9 w9, C0729q2 c0729q2) {
        o5 o5VarX1 = c0729q2.X1();
        if (o5VarX1 != null) {
            w9.a(o5VarX1, c0729q2, (com.android.tools.r8.shaking.L) null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(String str) {
        System.out.println("  - " + str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(A1 a1, C0702m c0702m) {
        C0931Dn.a(a1.l, c0702m.f().d());
    }

    private static void a(final C0702m c0702m, final y3 y3Var, final ExecutorService executorService) {
        y3Var.a("Finalize synthetics", new InterfaceC3885d3() { // from class: com.android.tools.r8.D8$$ExternalSyntheticLambda10
            @Override // com.android.tools.r8.utils.InterfaceC3885d3
            public final void a() {
                C3861t.a(c0702m, y3Var, executorService);
            }
        });
        y3Var.a("Horizontal merger", new InterfaceC3885d3() { // from class: com.android.tools.r8.D8$$ExternalSyntheticLambda11
            @Override // com.android.tools.r8.utils.InterfaceC3885d3
            public final void a() {
                D8.a(c0702m, executorService, y3Var);
            }
        });
        y3Var.a("Signature rewriter", new InterfaceC3885d3() { // from class: com.android.tools.r8.D8$$ExternalSyntheticLambda12
            @Override // com.android.tools.r8.utils.InterfaceC3885d3
            public final void a() {
                D8.a(c0702m, executorService);
            }
        });
        y3Var.a("Kotlin metadata rewriter", new InterfaceC3885d3() { // from class: com.android.tools.r8.D8$$ExternalSyntheticLambda13
            @Override // com.android.tools.r8.utils.InterfaceC3885d3
            public final void a() {
                D8.b(c0702m, executorService);
            }
        });
        y3Var.a("Startup instrumentation", new InterfaceC3885d3() { // from class: com.android.tools.r8.D8$$ExternalSyntheticLambda14
            @Override // com.android.tools.r8.utils.InterfaceC3885d3
            public final void a() {
                C2854qW.a(c0702m, executorService);
            }
        });
        y3Var.a("Api reference stubber", new InterfaceC3885d3() { // from class: com.android.tools.r8.D8$$ExternalSyntheticLambda15
            @Override // com.android.tools.r8.utils.InterfaceC3885d3
            public final void a() {
                D8.d(c0702m, executorService);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(C0702m c0702m, ExecutorService executorService, y3 y3Var) {
        C0959Ek.a(c0702m).a(executorService, y3Var, (g4) null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(C0702m c0702m, ExecutorService executorService) {
        new C1239Oj(c0702m, null).b(c0702m.f().d(), executorService);
    }

    private static C0612b4 a(C0702m c0702m, C3945p c3945p, ExecutorService executorService, com.android.tools.r8.dex.U u, y3 y3Var) throws ExecutionException, IOException {
        c0702m.Y().e.warning(new StringDiagnostic("The compilation is slowed down due to a mix of class file and dex file inputs in the context of desugared library. This can be fixed by pre-compiling to dex the class file inputs and dex merging only dex files."));
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (C0729q2 c0729q2 : c0702m.f().d()) {
            if (c0729q2.g2()) {
                arrayList.add(c0729q2);
            } else {
                arrayList2.add(c0729q2);
            }
        }
        c0702m.d(new C0664h(c0702m.f().g().a(c0702m.e().c().a(arrayList2).b()), c0702m.f().f()));
        C3601m c3601m = new C3601m();
        new C1239Oj(c0702m, null).a(c0702m.f().d(), executorService);
        new C1521Xz(c0702m).a(executorService);
        C0548k.a(c0702m, u, c3601m).c(executorService);
        C3945p.a aVarA = C3945p.a(c3945p);
        aVarA.b().clear();
        aVarA.a(c3601m);
        AbstractC0674i0 abstractC0674i0C = new C0408c(aVarA.a(), c0702m.Y(), y3Var).a(executorService).c();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            abstractC0674i0C.a((C0729q2) it.next());
        }
        return ((C0612b4.a) abstractC0674i0C).b();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static C0664h a(C0612b4 c0612b4, A1 a1, C0408c c0408c) {
        com.android.tools.r8.synthesis.y yVarB;
        if (a1.f instanceof DexIndexedConsumer) {
            yVarB = com.android.tools.r8.synthesis.y.e();
        } else {
            yVarB = com.android.tools.r8.synthesis.y.b();
        }
        return C0664h.a(c0612b4, yVarB, c0408c.a(c0612b4));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void d(C0702m c0702m) {
        c0702m.a(V0.a(c0702m));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void d(C0702m c0702m, ExecutorService executorService) {
        new C3033t2(c0702m).a(executorService);
    }
}
