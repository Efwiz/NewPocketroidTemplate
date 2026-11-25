package com.android.tools.r8;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.internal.C2281j4;
import com.android.tools.r8.utils.A1;
import com.android.tools.r8.utils.C3879c3;
import com.android.tools.r8.utils.C3970u0;
import com.android.tools.r8.utils.R0;
import com.android.tools.r8.utils.S0;
import com.android.tools.r8.utils.Y2;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class BackportedMethodList {
    static final String a = Y2.a("Usage: BackportedMethodList [options]", " Options are:", "  --output <file>         # Output result in <file>.", "  --min-api <number>      # Minimum Android API level for the application", "  --desugared-lib <file>  # Desugared library configuration (JSON from the", "                          # configuration)", "  --lib <file>            # The compilation SDK library (android.jar)", "  --version               # Print the version of BackportedMethodList.", "  --help                  # Print this message.");

    /* JADX INFO: Access modifiers changed from: private */
    public static String a(C0684j2 c0684j2) {
        return C3970u0.i(c0684j2.e.e.toString()) + "#" + c0684j2.f + c0684j2.h.w0();
    }

    public static void main(final String[] strArr) {
        R0.a(new S0() { // from class: com.android.tools.r8.BackportedMethodList$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.utils.S0
            public final void run() throws CompilationFailedException {
                BackportedMethodList.run(strArr);
            }
        });
    }

    public static void run(final BackportedMethodListCommand backportedMethodListCommand) throws CompilationFailedException {
        if (backportedMethodListCommand.isPrintHelp()) {
            System.out.println(a);
            return;
        }
        if (backportedMethodListCommand.isPrintVersion()) {
            System.out.println("BackportedMethodList " + Version.getVersionString());
        } else {
            final A1 a1B = backportedMethodListCommand.b();
            final ExecutorService executorServiceA = C3879c3.a(a1B);
            try {
                R0.b(backportedMethodListCommand.c(), new R0.a() { // from class: com.android.tools.r8.BackportedMethodList$$ExternalSyntheticLambda1
                    @Override // com.android.tools.r8.utils.R0.a
                    public final void run() {
                        BackportedMethodList.a(backportedMethodListCommand, a1B, executorServiceA);
                    }
                });
            } finally {
                executorServiceA.shutdown();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(final BackportedMethodListCommand backportedMethodListCommand, A1 a1, ExecutorService executorService) {
        C2281j4.a(backportedMethodListCommand.a(), a1, executorService).stream().map(new Function() { // from class: com.android.tools.r8.BackportedMethodList$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return BackportedMethodList.a((C0684j2) obj);
            }
        }).sorted().forEach(new Consumer() { // from class: com.android.tools.r8.BackportedMethodList$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                BackportedMethodList.a(backportedMethodListCommand, (String) obj);
            }
        });
        backportedMethodListCommand.getBackportedMethodListConsumer().finished(backportedMethodListCommand.c());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(BackportedMethodListCommand backportedMethodListCommand, String str) {
        backportedMethodListCommand.getBackportedMethodListConsumer().accept(str, backportedMethodListCommand.c());
    }

    public static void run(String[] strArr) throws CompilationFailedException {
        run(BackportedMethodListCommand.parse(strArr).build());
    }
}
