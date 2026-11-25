package com.android.tools.r8.tracereferences;

import com.android.tools.r8.AbstractC0403b;
import com.android.tools.r8.AbstractC3600l;
import com.android.tools.r8.Diagnostic;
import com.android.tools.r8.DiagnosticsHandler;
import com.android.tools.r8.JdkClassFileProvider;
import com.android.tools.r8.ParseFlagPrinter;
import com.android.tools.r8.S;
import com.android.tools.r8.StringConsumer;
import com.android.tools.r8.c0;
import com.android.tools.r8.internal.AbstractC1048Hm;
import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.internal.C3405y10;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.utils.AbstractC3867a1;
import com.android.tools.r8.utils.ExceptionDiagnostic;
import com.android.tools.r8.utils.StringDiagnostic;
import com.android.tools.r8.utils.Y2;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.function.Consumer;
import name.antonsmirnov.firmata.FormatHelper;
import org.jetbrains.kotlin.cli.common.arguments.PreprocessCommandLineArgumentsKt;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract class h {
    private static final AbstractC1048Hm a = AbstractC1048Hm.a("--lib", "--target", "--source", "--output");

    static String a() {
        StringBuilder sb = new StringBuilder();
        Y2.a(sb, "Usage: tracereferences <command> [<options>] [@<argfile>]", " Where <command> is one of:");
        new ParseFlagPrinter().addFlags(AbstractC1639am.a(S.a("--check", "Run emitting only diagnostics messages."), S.a("--keep-rules", "[<keep-rules-options>]", "Traced references will be output in the keep-rules", "format."))).appendLinesToBuilder(sb);
        Y2.a(sb, " and each <argfile> is a file containing additional options (one per line)", " and options are:");
        new ParseFlagPrinter().addFlags(AbstractC1639am.g().a(S.a("--lib", "<file|jdk-home>", "Add <file|jdk-home> runtime library.")).a(S.a("--source", "<file>", "Add <file> as a source for tracing references.")).a(S.a("--target", "<file>", "Add <file> as a target for tracing references. When", "target is not specified all references from source", "outside of library are treated as a missing", "references.")).a(S.a("--output", "<file>", "Output result in <outfile>. If not passed the", "result will go to standard out.")).a(S.c()).a(S.a("tracereferences")).a(S.a("--help", "Print this message.")).a()).appendLinesToBuilder(sb);
        Y2.a(sb, " and <keep-rule-options> are:");
        new ParseFlagPrinter().addFlags(AbstractC1639am.c(S.a("--allowobfuscation", "Output keep rules with the allowobfuscation", "modifier (defaults to rules without the modifier)"))).appendLinesToBuilder(sb);
        return sb.toString();
    }

    static TraceReferencesCommand.Builder a(String[] strArr, Origin origin) {
        return a(strArr, origin, TraceReferencesCommand.builder());
    }

    static TraceReferencesCommand.Builder a(String[] strArr, Origin origin, DiagnosticsHandler diagnosticsHandler) {
        return a(strArr, origin, TraceReferencesCommand.builder(diagnosticsHandler));
    }

    private static TraceReferencesCommand.Builder a(String[] strArr, Origin origin, final TraceReferencesCommand.Builder builder) {
        StringConsumer c0Var;
        int i;
        String str;
        Objects.requireNonNull(builder);
        String[] strArrA = AbstractC3867a1.a(strArr, new Consumer() { // from class: com.android.tools.r8.tracereferences.h$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                builder.a((Diagnostic) obj);
            }
        });
        if (strArrA.length == 0) {
            builder.a(new StringDiagnostic("Missing command"));
            return builder;
        }
        int i2 = 0;
        boolean z = false;
        Path path = null;
        for (int i3 = 0; i3 < strArrA.length; i3 = i + 1) {
            String strTrim = strArrA[i3].trim();
            if (a.contains(strTrim)) {
                int i4 = i3 + 1;
                if (i4 < strArrA.length) {
                    str = strArrA[i4];
                    i = i4;
                } else {
                    builder.a(new StringDiagnostic("Missing parameter for " + strArrA[i3] + ".", origin));
                    break;
                }
            } else {
                i = i3;
                str = null;
            }
            if (strTrim.length() != 0) {
                if (strTrim.equals("--help")) {
                    builder.setPrintHelp(true);
                    return builder;
                }
                if (strTrim.equals("--version")) {
                    builder.setPrintVersion(true);
                    return builder;
                }
                if (strTrim.equals("--check")) {
                    if (i2 != 0) {
                        builder.a(new StringDiagnostic("Multiple commands specified", origin));
                    }
                    i2 = 1;
                } else if (strTrim.equals("--keep-rules")) {
                    if (i2 != 0) {
                        builder.a(new StringDiagnostic("Multiple commands specified", origin));
                    }
                    i2 = 2;
                } else if (strTrim.equals("--allowobfuscation")) {
                    z = true;
                } else if (strTrim.equals("--lib")) {
                    Path path2 = Paths.get(str, new String[0]);
                    if (Files.exists(path2.resolve("lib").resolve("jrt-fs.jar"), new LinkOption[0]) || Files.exists(path2.resolve("jre").resolve("lib").resolve("rt.jar"), new LinkOption[0]) || Files.exists(path2.resolve("lib").resolve("rt.jar"), new LinkOption[0])) {
                        try {
                            builder.addLibraryResourceProvider(JdkClassFileProvider.fromJdkHome(path2));
                        } catch (IOException e) {
                            builder.a(new ExceptionDiagnostic(e, origin));
                        }
                    } else {
                        builder.addLibraryFiles(path2);
                    }
                } else if (strTrim.equals("--target")) {
                    builder.addTargetFiles(Paths.get(str, new String[0]));
                } else if (strTrim.equals("--source")) {
                    builder.addSourceFiles(Paths.get(str, new String[0]));
                } else if (strTrim.equals("--output")) {
                    if (path != null) {
                        builder.a(new StringDiagnostic("Option '--output' passed multiple times.", origin));
                    } else {
                        path = Paths.get(str, new String[0]);
                    }
                } else if (strTrim.startsWith(PreprocessCommandLineArgumentsKt.ARGFILE_ARGUMENT)) {
                    builder.a(new StringDiagnostic("Recursive @argfiles are not supported: ", origin));
                } else {
                    int iA = AbstractC3600l.a(new Consumer() { // from class: com.android.tools.r8.tracereferences.h$$ExternalSyntheticLambda0
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            builder.a((Diagnostic) obj);
                        }
                    }, builder.a(), strTrim, strArrA, i, origin);
                    if (iA >= 0) {
                        i += iA;
                    } else {
                        builder.a(new StringDiagnostic("Unsupported option '" + strTrim + FormatHelper.QUOTE, origin));
                    }
                }
            }
        }
        if (i2 == 0) {
            builder.a(new StringDiagnostic("Missing command, specify one of 'check' or '--keep-rules'", origin));
            return builder;
        }
        if (i2 == 1 && path != null) {
            builder.a(new StringDiagnostic("Using '--output' requires command '--keep-rules'", origin));
            return builder;
        }
        if (i2 != 2 && z) {
            builder.a(new StringDiagnostic("Using '--allowobfuscation' requires command '--keep-rules'", origin));
            return builder;
        }
        int iB = AbstractC0403b.b(i2);
        if (iB == 0) {
            builder.setConsumer(new TraceReferencesCheckConsumer(TraceReferencesConsumer.emptyConsumer()));
        } else if (iB == 1) {
            TraceReferencesKeepRules.Builder allowObfuscation = TraceReferencesKeepRules.builder().setAllowObfuscation(z);
            if (path != null) {
                c0Var = new StringConsumer.FileConsumer(path);
            } else {
                c0Var = new c0(new PrintWriter(System.out));
            }
            builder.setConsumer(new TraceReferencesCheckConsumer(allowObfuscation.setOutputConsumer(c0Var).build()));
        } else {
            throw new C3405y10();
        }
        return builder;
    }
}
