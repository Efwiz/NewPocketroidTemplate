package com.android.tools.r8.relocator;

import com.android.tools.r8.CompilationFailedException;
import com.android.tools.r8.utils.A1;
import com.android.tools.r8.utils.C3879c3;
import com.android.tools.r8.utils.C3945p;
import com.android.tools.r8.utils.R0;
import java.io.IOException;
import java.util.concurrent.ExecutorService;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class Relocator {
    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(RelocatorCommand relocatorCommand, ExecutorService executorService, C3945p c3945p, A1 a1) {
        try {
            c(relocatorCommand, executorService, c3945p, a1);
        } finally {
            executorService.shutdown();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Bottom block not found for handler: all -> 0x0069 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static void c(com.android.tools.r8.relocator.RelocatorCommand r5, java.util.concurrent.ExecutorService r6, com.android.tools.r8.utils.C3945p r7, com.android.tools.r8.utils.A1 r8) throws java.io.IOException {
        /*
            java.lang.String r0 = "Relocator"
            com.android.tools.r8.utils.y3 r0 = com.android.tools.r8.utils.y3.a(r8, r0)
            com.android.tools.r8.dex.c r1 = new com.android.tools.r8.dex.c     // Catch: java.lang.Throwable -> L69 java.util.concurrent.ExecutionException -> L6b
            r1.<init>(r7, r8, r0)     // Catch: java.lang.Throwable -> L69 java.util.concurrent.ExecutionException -> L6b
            com.android.tools.r8.graph.b4 r1 = r1.a(r6)     // Catch: java.lang.Throwable -> L69 java.util.concurrent.ExecutionException -> L6b
            com.android.tools.r8.synthesis.y r2 = com.android.tools.r8.synthesis.y.d()     // Catch: java.lang.Throwable -> L69 java.util.concurrent.ExecutionException -> L6b
            com.android.tools.r8.graph.h r1 = com.android.tools.r8.graph.C0664h.a(r1, r2)     // Catch: java.lang.Throwable -> L69 java.util.concurrent.ExecutionException -> L6b
            com.android.tools.r8.graph.m r2 = com.android.tools.r8.graph.C0702m.b(r1)     // Catch: java.lang.Throwable -> L69 java.util.concurrent.ExecutionException -> L6b
            com.android.tools.r8.graph.l$a r3 = com.android.tools.r8.graph.C0695l.a(r2)     // Catch: java.lang.Throwable -> L69 java.util.concurrent.ExecutionException -> L6b
            com.android.tools.r8.graph.l r3 = r3.a()     // Catch: java.lang.Throwable -> L69 java.util.concurrent.ExecutionException -> L6b
            r2.a(r3)     // Catch: java.lang.Throwable -> L69 java.util.concurrent.ExecutionException -> L6b
            com.android.tools.r8.relocator.d r3 = new com.android.tools.r8.relocator.d     // Catch: java.lang.Throwable -> L69 java.util.concurrent.ExecutionException -> L6b
            r3.<init>(r2)     // Catch: java.lang.Throwable -> L69 java.util.concurrent.ExecutionException -> L6b
            java.util.Map r4 = r5.getMapping()     // Catch: java.lang.Throwable -> L69 java.util.concurrent.ExecutionException -> L6b
            com.android.tools.r8.naming.o0 r3 = r3.a(r4)     // Catch: java.lang.Throwable -> L69 java.util.concurrent.ExecutionException -> L6b
            r2.a(r3)     // Catch: java.lang.Throwable -> L69 java.util.concurrent.ExecutionException -> L6b
            com.android.tools.r8.internal.Oj r3 = new com.android.tools.r8.internal.Oj     // Catch: java.lang.Throwable -> L69 java.util.concurrent.ExecutionException -> L6b
            r4 = 0
            r3.<init>(r2, r4)     // Catch: java.lang.Throwable -> L69 java.util.concurrent.ExecutionException -> L6b
            java.util.Collection r1 = r1.d()     // Catch: java.lang.Throwable -> L69 java.util.concurrent.ExecutionException -> L6b
            r3.a(r1, r6)     // Catch: java.lang.Throwable -> L69 java.util.concurrent.ExecutionException -> L6b
            com.android.tools.r8.internal.x6 r6 = new com.android.tools.r8.internal.x6     // Catch: java.lang.Throwable -> L69 java.util.concurrent.ExecutionException -> L6b
            com.android.tools.r8.dex.U r1 = new com.android.tools.r8.dex.U     // Catch: java.lang.Throwable -> L69 java.util.concurrent.ExecutionException -> L6b
            com.android.tools.r8.dex.U$b r3 = com.android.tools.r8.dex.U.b.Relocator     // Catch: java.lang.Throwable -> L69 java.util.concurrent.ExecutionException -> L6b
            r1.<init>(r3)     // Catch: java.lang.Throwable -> L69 java.util.concurrent.ExecutionException -> L6b
            r6.<init>(r2, r1)     // Catch: java.lang.Throwable -> L69 java.util.concurrent.ExecutionException -> L6b
            com.android.tools.r8.ClassFileConsumer r5 = r5.getConsumer()     // Catch: java.lang.Throwable -> L69 java.util.concurrent.ExecutionException -> L6b
            r6.a(r5)     // Catch: java.lang.Throwable -> L69 java.util.concurrent.ExecutionException -> L6b
            r8.v1()     // Catch: java.lang.Throwable -> L69 java.util.concurrent.ExecutionException -> L6b
            com.android.tools.r8.utils.O2 r5 = r8.e
            r7.b(r5)
            r8.G1()
            boolean r5 = r8.m
            if (r5 == 0) goto L68
            r0.d()
        L68:
            return
        L69:
            r5 = move-exception
            goto L72
        L6b:
            r5 = move-exception
            java.lang.RuntimeException r6 = new java.lang.RuntimeException     // Catch: java.lang.Throwable -> L69
            r6.<init>(r5)     // Catch: java.lang.Throwable -> L69
            throw r6     // Catch: java.lang.Throwable -> L69
        L72:
            com.android.tools.r8.utils.O2 r6 = r8.e
            r7.b(r6)
            r8.G1()
            boolean r6 = r8.m
            if (r6 == 0) goto L81
            r0.d()
        L81:
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.tools.r8.relocator.Relocator.c(com.android.tools.r8.relocator.RelocatorCommand, java.util.concurrent.ExecutorService, com.android.tools.r8.utils.p, com.android.tools.r8.utils.A1):void");
    }

    public static void run(final RelocatorCommand relocatorCommand) throws CompilationFailedException {
        final C3945p app2 = relocatorCommand.getApp();
        final A1 internalOptions = relocatorCommand.getInternalOptions();
        final ExecutorService executorServiceA = C3879c3.a(internalOptions);
        R0.a(relocatorCommand.getReporter(), new R0.a() { // from class: com.android.tools.r8.relocator.Relocator$$ExternalSyntheticLambda1
            @Override // com.android.tools.r8.utils.R0.a
            public final void run() {
                Relocator.a(relocatorCommand, executorServiceA, app2, internalOptions);
            }
        });
    }

    public static void run(final RelocatorCommand relocatorCommand, final ExecutorService executorService) throws CompilationFailedException {
        final C3945p app2 = relocatorCommand.getApp();
        final A1 internalOptions = relocatorCommand.getInternalOptions();
        R0.a(relocatorCommand.getReporter(), new R0.a() { // from class: com.android.tools.r8.relocator.Relocator$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.utils.R0.a
            public final void run() throws IOException {
                Relocator.c(relocatorCommand, executorService, app2, internalOptions);
            }
        });
    }
}
