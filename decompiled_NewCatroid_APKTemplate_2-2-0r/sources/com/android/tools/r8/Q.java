package com.android.tools.r8;

import com.android.tools.r8.internal.AbstractC1048Hm;
import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.internal.AbstractC3408y3;
import com.android.tools.r8.internal.C1507Xl;
import com.android.tools.r8.internal.I3;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.utils.AbstractC3867a1;
import com.android.tools.r8.utils.StringDiagnostic;
import com.android.tools.r8.utils.Y2;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import name.antonsmirnov.firmata.FormatHelper;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class Q extends AbstractC3600l {
    private static final AbstractC1048Hm b = AbstractC1048Hm.a("--output", "--lib", "--min-api", "--desugared-lib", "--thread-count", "--pg-conf", "--pg-map-output", "--art-profile");
    private static final Set c = AbstractC1048Hm.c("--art-profile");

    static String a() {
        StringBuilder sb = new StringBuilder();
        Y2.a(sb, "Usage: l8 [options] <input-files>", " where <input-files> are any combination class, zip, or jar files", " where <input-files> are any combination of dex, class, zip, jar, or apk files", " and options are:");
        ParseFlagPrinter parseFlagPrinter = new ParseFlagPrinter();
        C1507Xl c1507XlA = AbstractC1639am.g().a(S.a(true)).a(S.b(false)).a(S.a("--output", "<file>", "Output result in <file>.", "<file> must be an existing directory or a zip file.")).a(S.a("--lib", "<file|jdk-home>", "Add <file|jdk-home> as a library resource.")).a(S.d()).a(S.a("--pg-conf", "<file>", "Proguard configuration <file>.")).a(S.a("--pg-map-output", "<file>", "Output the resulting name and line mapping to <file>.")).a(S.a("--desugared-lib", "<file>", "Specify desugared library configuration.", "<file> is a desugared library configuration (json).")).a((Iterable) S.a()).a(S.a("--thread-count", "<number>", "Use <number> of threads for compilation.", "If not specified the number will be based on", "heuristics taking the number of cores into account.")).a(S.c());
        AbstractC1639am abstractC1639amA = AbstractC1639am.a("<input>", "<output>");
        List listAsList = Arrays.asList("Rewrite human readable ART profile read from <input> and write to <output>.");
        StringBuilder sb2 = new StringBuilder("--art-profile");
        Iterator it = abstractC1639amA.iterator();
        while (it.hasNext()) {
            sb2.append(" ").append((String) it.next());
        }
        parseFlagPrinter.addFlags(c1507XlA.a(new S(sb2.toString(), Collections.emptyList(), listAsList)).a(S.a("l8")).a(S.a("--help", "Print this message.")).a()).appendLinesToBuilder(sb);
        return sb.toString();
    }

    public static L8Command.Builder a(String[] strArr, Origin origin) {
        return new Q().a(strArr, origin, L8Command.builder());
    }

    public static L8Command.Builder a(String[] strArr, Origin origin, DiagnosticsHandler diagnosticsHandler) {
        return new Q().a(strArr, origin, L8Command.builder(diagnosticsHandler));
    }

    private L8Command.Builder a(String[] strArr, Origin origin, final L8Command.Builder builder) {
        int i;
        String str;
        String str2;
        OutputMode outputMode = OutputMode.DexIndexed;
        C3674o.a aVarA = C3674o.a();
        Objects.requireNonNull(builder);
        String[] strArrA = AbstractC3867a1.a(strArr, new Consumer() { // from class: com.android.tools.r8.Q$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                builder.error((Diagnostic) obj);
            }
        });
        boolean z = false;
        CompilationMode compilationMode = null;
        Path path = null;
        for (int i2 = 0; i2 < strArrA.length; i2 = i + 1) {
            String strTrim = strArrA[i2].trim();
            if (b.contains(strTrim)) {
                i = i2 + 1;
                if (i < strArrA.length) {
                    str2 = strArrA[i];
                    if (c.contains(strTrim)) {
                        int i3 = i2 + 2;
                        if (i3 < strArrA.length) {
                            str = strArrA[i3];
                            i = i3;
                        } else {
                            builder.error(new StringDiagnostic("Missing parameter for " + strArrA[i2] + ".", origin));
                            break;
                        }
                    } else {
                        str = null;
                    }
                } else {
                    builder.error(new StringDiagnostic("Missing parameter for " + strArrA[i2] + ".", origin));
                    break;
                }
            } else {
                i = i2;
                str = null;
                str2 = null;
            }
            if (strTrim.length() != 0) {
                if (strTrim.equals("--help")) {
                    builder.setPrintHelp(true);
                } else if (strTrim.equals("--version")) {
                    builder.setPrintVersion(true);
                } else if (strTrim.equals("--debug")) {
                    if (compilationMode == CompilationMode.RELEASE) {
                        builder.error(new StringDiagnostic("Cannot compile in both --debug and --release mode.", origin));
                    } else {
                        compilationMode = CompilationMode.DEBUG;
                    }
                } else if (strTrim.equals("--release")) {
                    if (compilationMode == CompilationMode.DEBUG) {
                        builder.error(new StringDiagnostic("Cannot compile in both --debug and --release mode.", origin));
                    } else {
                        compilationMode = CompilationMode.RELEASE;
                    }
                } else if (strTrim.equals("--output")) {
                    if (path != null) {
                        builder.error(new StringDiagnostic("Cannot output both to '" + path.toString() + "' and '" + str2 + FormatHelper.QUOTE, origin));
                    } else {
                        path = Paths.get(str2, new String[0]);
                    }
                } else if (strTrim.equals("--min-api")) {
                    if (z) {
                        builder.error(new StringDiagnostic("Cannot set multiple --min-api options", origin));
                    } else {
                        AbstractC3600l.a(new Consumer() { // from class: com.android.tools.r8.Q$$ExternalSyntheticLambda0
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                builder.error((Diagnostic) obj);
                            }
                        }, "--min-api", str2, origin, new Consumer() { // from class: com.android.tools.r8.Q$$ExternalSyntheticLambda1
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                builder.setMinApiLevel(((Integer) obj).intValue());
                            }
                        });
                        z = true;
                    }
                } else if (strTrim.equals("--lib")) {
                    AbstractC3600l.a(builder, origin, str2);
                } else if (strTrim.equals("--pg-conf")) {
                    builder.addProguardConfigurationFiles(Paths.get(str2, new String[0]));
                } else if (strTrim.equals("--pg-map-output")) {
                    builder.setProguardMapOutputPath(Paths.get(str2, new String[0]));
                } else if (strTrim.equals("--desugared-lib")) {
                    builder.addDesugaredLibraryConfiguration(d0.a(Paths.get(str2, new String[0])));
                } else if (strTrim.equals("--classfile")) {
                    outputMode = OutputMode.ClassFile;
                } else if (strTrim.equals("--art-profile")) {
                    builder.addArtProfileForRewriting(I3.a(Paths.get(str2, new String[0])), AbstractC3408y3.a(Paths.get(str, new String[0])));
                } else if (strTrim.equals("--thread-count")) {
                    AbstractC3600l.a(new Consumer() { // from class: com.android.tools.r8.Q$$ExternalSyntheticLambda0
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            builder.error((Diagnostic) obj);
                        }
                    }, "--thread-count", str2, origin, new Consumer() { // from class: com.android.tools.r8.Q$$ExternalSyntheticLambda2
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            builder.a(((Integer) obj).intValue());
                        }
                    });
                } else if (strTrim.startsWith("--")) {
                    if (!a(builder, strTrim, origin)) {
                        int iB = AbstractC3600l.b(builder, strTrim, strArrA, i, origin);
                        if (iB < 0 && (iB = AbstractC3600l.a(builder, strTrim, strArrA, i, origin)) < 0) {
                            builder.error(new StringDiagnostic("Unknown option: " + strTrim, origin));
                        } else {
                            i += iB;
                        }
                    }
                } else {
                    builder.addProgramFiles(Paths.get(strTrim, new String[0]));
                }
            }
        }
        if (!aVarA.b()) {
            builder.addClasspathResourceProvider(aVarA.a());
        }
        if (compilationMode != null) {
            builder.setMode(compilationMode);
        }
        if (path == null) {
            path = Paths.get(".", new String[0]);
        }
        return builder.setOutput(path, outputMode);
    }
}
