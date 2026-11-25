package com.android.tools.r8;

import com.android.tools.r8.dex.C0408c;
import com.android.tools.r8.experimental.graphinfo.GraphConsumer;
import com.android.tools.r8.graph.AbstractC0681j0;
import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.B5;
import com.android.tools.r8.graph.C0664h;
import com.android.tools.r8.graph.C0680j;
import com.android.tools.r8.graph.C0695l;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.internal.AbstractC2151hN;
import com.android.tools.r8.shaking.D4;
import com.android.tools.r8.shaking.N1;
import com.android.tools.r8.shaking.O1;
import com.android.tools.r8.shaking.Y3;
import com.android.tools.r8.utils.A1;
import com.android.tools.r8.utils.C3879c3;
import com.android.tools.r8.utils.C3945p;
import com.android.tools.r8.utils.R0;
import com.android.tools.r8.utils.U2;
import com.android.tools.r8.utils.y3;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;
import java.util.function.Supplier;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class GenerateMainDexList {
    static final /* synthetic */ boolean c = true;
    private final y3 a = new y3("maindex");
    private final A1 b;

    public GenerateMainDexList(A1 a1) {
        this.b = a1;
    }

    private void a(C3945p c3945p, ExecutorService executorService, final U2 u2) {
        try {
            traceMainDexForGenerateMainDexList(executorService, new C0408c(c3945p, this.b, this.a).a(executorService)).a(new Consumer() { // from class: com.android.tools.r8.GenerateMainDexList$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.a(u2, (C0767v2) obj);
                }
            });
            u2.finished(this.b.e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(C3945p c3945p, ExecutorService executorService, A1 a1, List list) {
        try {
            new GenerateMainDexList(a1).a(c3945p, executorService, new U2(new K(a1.w1, list)));
        } finally {
            executorService.shutdown();
        }
    }

    public static void main(String[] strArr) throws CompilationFailedException {
        GenerateMainDexListCommand generateMainDexListCommandBuild = GenerateMainDexListCommand.parse(strArr).build();
        if (generateMainDexListCommandBuild.isPrintHelp()) {
            System.out.println(GenerateMainDexListCommand.j);
            return;
        }
        if (generateMainDexListCommandBuild.isPrintVersion()) {
            System.out.println("MainDexListGenerator 8.1.56");
            return;
        }
        List<String> listRun = run(generateMainDexListCommandBuild);
        if (generateMainDexListCommandBuild.getMainDexListConsumer() == null) {
            final PrintStream printStream = System.out;
            Objects.requireNonNull(printStream);
            listRun.forEach(new Consumer() { // from class: com.android.tools.r8.GenerateMainDexList$$ExternalSyntheticLambda5
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    printStream.println((String) obj);
                }
            });
        }
    }

    public static List<String> run(GenerateMainDexListCommand generateMainDexListCommand) throws CompilationFailedException {
        return a(generateMainDexListCommand.a(), generateMainDexListCommand.b());
    }

    public N1 traceMainDexForD8(C0702m<C0664h> c0702m, ExecutorService executorService) throws ExecutionException {
        return a(C0702m.a(c0702m.e().i(), c0702m.f().f()), executorService);
    }

    public N1 traceMainDexForGenerateMainDexList(ExecutorService executorService, AbstractC0681j0 abstractC0681j0) throws ExecutionException {
        return a(C0702m.a(abstractC0681j0.i()), executorService);
    }

    public static List<String> run(GenerateMainDexListCommand generateMainDexListCommand, final ExecutorService executorService) throws CompilationFailedException {
        final C3945p c3945pA = generateMainDexListCommand.a();
        final A1 a1B = generateMainDexListCommand.b();
        final ArrayList arrayList = new ArrayList();
        R0.a(generateMainDexListCommand.c(), new R0.a() { // from class: com.android.tools.r8.GenerateMainDexList$$ExternalSyntheticLambda4
            @Override // com.android.tools.r8.utils.R0.a
            public final void run() {
                GenerateMainDexList.a(c3945pA, executorService, a1B, arrayList);
            }
        });
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(U2 u2, C0767v2 c0767v2) {
        u2.accept(c0767v2.a1() + ".class", this.b.e);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private N1 a(final C0702m c0702m, ExecutorService executorService) {
        D4 d4;
        c0702m.a(C0695l.a((C0702m<?>) c0702m).a());
        O1.a(c0702m.f());
        B5 b5A = B5.a((C0702m<? extends C0680j>) c0702m);
        Y3 y3B = Y3.a(c0702m, (AbstractC2151hN) AbstractC2151hN.c(), b5A, this.b.o1).a(executorService);
        c0702m.a(y3B);
        GraphConsumer d5 = this.b.G1;
        if (y3B.g.isEmpty()) {
            d4 = 0;
        } else {
            d5 = new D4(d5);
            d4 = d5;
        }
        com.android.tools.r8.shaking.L lB = com.android.tools.r8.shaking.Z.b(c0702m, executorService, b5A, d5);
        final N1 n1B = lB.b(executorService, this.a);
        R8.a(y3B, new Supplier() { // from class: com.android.tools.r8.GenerateMainDexList$$ExternalSyntheticLambda2
            @Override // java.util.function.Supplier
            public final Object get() {
                return GenerateMainDexList.a(n1B, c0702m);
            }
        }, d4, c0702m, lB, true, this.b, this.a, executorService);
        return n1B;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Collection a(N1 n1, final C0702m c0702m) {
        final ArrayList arrayList = new ArrayList();
        n1.a(new Consumer() { // from class: com.android.tools.r8.GenerateMainDexList$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                GenerateMainDexList.a(c0702m, arrayList, (C0767v2) obj);
            }
        });
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C0702m c0702m, ArrayList arrayList, C0767v2 c0767v2) {
        AbstractC0727q0 abstractC0727q0C = c0702m.c(c0767v2);
        if (!c && !abstractC0727q0C.e0()) {
            throw new AssertionError();
        }
        arrayList.add(abstractC0727q0C.a0());
    }

    static List<String> a(final C3945p c3945p, final A1 a1) throws CompilationFailedException {
        final ExecutorService executorServiceA = C3879c3.a(a1);
        final ArrayList arrayList = new ArrayList();
        R0.a(a1.e, new R0.a() { // from class: com.android.tools.r8.GenerateMainDexList$$ExternalSyntheticLambda3
            @Override // com.android.tools.r8.utils.R0.a
            public final void run() {
                GenerateMainDexList.b(c3945p, executorServiceA, a1, arrayList);
            }
        });
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(C3945p c3945p, ExecutorService executorService, A1 a1, List list) {
        new GenerateMainDexList(a1).a(c3945p, executorService, new U2(new K(a1.w1, list)));
    }
}
