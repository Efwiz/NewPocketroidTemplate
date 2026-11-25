package com.android.tools.r8;

import com.android.tools.r8.internal.AbstractC1048Hm;
import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.internal.AbstractC3408y3;
import com.android.tools.r8.internal.BW;
import com.android.tools.r8.internal.C1507Xl;
import com.android.tools.r8.internal.I3;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.origin.PathOrigin;
import com.android.tools.r8.utils.AbstractC3867a1;
import com.android.tools.r8.utils.ExceptionDiagnostic;
import com.android.tools.r8.utils.StringDiagnostic;
import com.android.tools.r8.utils.Y2;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
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
import org.catrobat.catroid.common.Constants;
import org.jetbrains.kotlin.cli.common.arguments.PreprocessCommandLineArgumentsKt;

/* JADX INFO: renamed from: com.android.tools.r8.p, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3675p extends AbstractC3600l {
    private static final AbstractC1048Hm b = AbstractC1048Hm.a("--output", "--lib", "--classpath", "--pg-map", "--pg-map-output", "--min-api", "--main-dex-rules", "--main-dex-list", "--main-dex-list-output", "--desugared-lib", "--desugared-lib-pg-conf-output", "--thread-count", "--art-profile", "--startup-profile");
    private static final Set c = AbstractC1048Hm.c("--art-profile");

    public static AbstractC1639am a() {
        C1507Xl c1507XlA = AbstractC1639am.g().a(S.a(true)).a(S.b(false)).a(S.a("--output", "<file>", "Output result in <file>.", "<file> must be an existing directory or a zip file.")).a(S.a("--lib", "<file|jdk-home>", "Add <file|jdk-home> as a library resource.")).a(S.a("--classpath", "<file>", "Add <file> as a classpath resource.")).a(S.d()).a(S.a("--pg-map", "<file>", "Use <file> as a mapping file for distribution.")).a(S.a("--intermediate", "Compile an intermediate result intended for later", "merging.")).a(S.a("--file-per-class", "Produce a separate dex file per class.", "Synthetic classes are in their own file.")).a(S.a("--file-per-class-file", "Produce a separate dex file per input .class file.", "Synthetic classes are with their originating class.")).a(S.a("--no-desugaring", "Force disable desugaring.")).a(S.a("--desugared-lib", "<file>", "Specify desugared library configuration.", "<file> is a desugared library configuration (json).")).a(S.a("--desugared-lib-pg-conf-output", "<file>", "Output the Proguard configuration for L8 to <file>.")).a(S.a("--main-dex-rules", "<file>", "Proguard keep rules for classes to place in the", "primary dex file.")).a(S.a("--main-dex-list", "<file>", "List of classes to place in the primary dex file.")).a(S.a("--main-dex-list-output", "<file>", "Output resulting main dex list in <file>.")).a((Iterable) S.a()).a(S.a("--thread-count", "<number>", "Use <number> of threads for compilation.", "If not specified the number will be based on", "heuristics taking the number of cores into account.")).a(S.c()).a(S.a("--android-platform-build", "Compile as a platform build where the runtime/bootclasspath", "is assumed to be the version specified by --min-api."));
        AbstractC1639am abstractC1639amA = AbstractC1639am.a("<input>", "<output>");
        List listAsList = Arrays.asList("Rewrite human readable ART profile read from <input> and write to <output>.");
        StringBuilder sb = new StringBuilder("--art-profile");
        Iterator it = abstractC1639amA.iterator();
        while (it.hasNext()) {
            sb.append(" ").append((String) it.next());
        }
        return c1507XlA.a(new S(sb.toString(), Collections.emptyList(), listAsList)).a(S.a("--startup-profile", "<file>", "Startup profile <file> to use for dex layout.")).a(S.a("d8")).a(S.a("--help", "Print this message.")).a();
    }

    static String b() {
        StringBuilder sb = new StringBuilder();
        Y2.a(sb, "Usage: d8 [options] [@<argfile>] <input-files>", " where <input-files> are any combination of dex, class, zip, jar, or apk files", " and each <argfile> is a file containing additional arguments (one per line)", " and options are:");
        new ParseFlagPrinter().addFlags(AbstractC1639am.a(a())).appendLinesToBuilder(sb);
        return sb.toString();
    }

    public static D8Command.Builder a(String[] strArr, Origin origin) {
        return new C3675p().a(strArr, origin, D8Command.builder());
    }

    public static D8Command.Builder a(String[] strArr, Origin origin, DiagnosticsHandler diagnosticsHandler) {
        return new C3675p().a(strArr, origin, D8Command.builder(diagnosticsHandler));
    }

    private D8Command.Builder a(String[] strArr, Origin origin, final D8Command.Builder builder) {
        int i;
        String str;
        String str2;
        C3674o.a aVarA = C3674o.a();
        Objects.requireNonNull(builder);
        String[] strArrA = AbstractC3867a1.a(strArr, new Consumer() { // from class: com.android.tools.r8.p$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                builder.error((Diagnostic) obj);
            }
        });
        boolean z = false;
        CompilationMode compilationMode = null;
        Path path = null;
        OutputMode outputMode = null;
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
                } else if (strTrim.equals("--file-per-class")) {
                    outputMode = OutputMode.DexFilePerClass;
                } else if (strTrim.equals("--file-per-class-file")) {
                    outputMode = OutputMode.DexFilePerClassFile;
                } else if (strTrim.equals("--classfile")) {
                    outputMode = OutputMode.ClassFile;
                } else if (strTrim.equals("--pg-map")) {
                    builder.setProguardInputMapFile(Paths.get(str2, new String[0]));
                } else if (strTrim.equals("--pg-map-output")) {
                    builder.setProguardMapOutputPath(Paths.get(str2, new String[0]));
                } else if (strTrim.equals("--output")) {
                    if (path != null) {
                        builder.error(new StringDiagnostic("Cannot output both to '" + path.toString() + "' and '" + str2 + FormatHelper.QUOTE, origin));
                    } else {
                        path = Paths.get(str2, new String[0]);
                    }
                } else if (strTrim.equals("--lib")) {
                    AbstractC3600l.a(builder, origin, str2);
                } else if (strTrim.equals("--classpath")) {
                    Path path2 = Paths.get(str2, new String[0]);
                    try {
                        if (Files.exists(path2, new LinkOption[0])) {
                            String lowerCase = path2.getFileName().toString().toLowerCase();
                            if (lowerCase.endsWith(".apk") || lowerCase.endsWith(".jar") || lowerCase.endsWith(Constants.ZIP_EXTENSION)) {
                                aVarA.a(new ArchiveClassFileProvider(path2));
                            } else if (Files.isDirectory(path2, new LinkOption[0])) {
                                aVarA.a(DirectoryClassFileProvider.fromDirectory(path2));
                            } else {
                                builder.error(new StringDiagnostic("Unsupported classpath file type", new PathOrigin(path2)));
                            }
                        } else {
                            throw new NoSuchFileException(path2.toString());
                        }
                    } catch (IOException e) {
                        builder.error(new ExceptionDiagnostic(e, new PathOrigin(path2)));
                    }
                } else if (strTrim.equals("--main-dex-rules")) {
                    builder.addMainDexRulesFiles(Paths.get(str2, new String[0]));
                } else if (strTrim.equals("--main-dex-list")) {
                    builder.addMainDexListFiles(Paths.get(str2, new String[0]));
                } else if (strTrim.equals("--main-dex-list-output")) {
                    builder.setMainDexListOutputPath(Paths.get(str2, new String[0]));
                } else if (strTrim.equals("--optimize-multidex-for-linearalloc")) {
                    builder.setOptimizeMultidexForLinearAlloc(true);
                } else if (strTrim.equals("--min-api")) {
                    if (z) {
                        builder.error(new StringDiagnostic("Cannot set multiple --min-api options", origin));
                    } else {
                        AbstractC3600l.a(new Consumer() { // from class: com.android.tools.r8.p$$ExternalSyntheticLambda0
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                builder.error((Diagnostic) obj);
                            }
                        }, "--min-api", str2, origin, new Consumer() { // from class: com.android.tools.r8.p$$ExternalSyntheticLambda1
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                builder.setMinApiLevel(((Integer) obj).intValue());
                            }
                        });
                        z = true;
                    }
                } else if (strTrim.equals("--thread-count")) {
                    AbstractC3600l.a(new Consumer() { // from class: com.android.tools.r8.p$$ExternalSyntheticLambda0
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            builder.error((Diagnostic) obj);
                        }
                    }, "--thread-count", str2, origin, new Consumer() { // from class: com.android.tools.r8.p$$ExternalSyntheticLambda2
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            builder.a(((Integer) obj).intValue());
                        }
                    });
                } else if (strTrim.equals("--intermediate")) {
                    builder.setIntermediate(true);
                } else if (strTrim.equals("--no-desugaring")) {
                    builder.setDisableDesugaring(true);
                } else if (strTrim.equals("--desugared-lib")) {
                    builder.addDesugaredLibraryConfiguration(d0.a(Paths.get(str2, new String[0])));
                } else if (strTrim.equals("--desugared-lib-pg-conf-output")) {
                    builder.setDesugaredLibraryKeepRuleConsumer(new StringConsumer.FileConsumer(Paths.get(str2, new String[0])));
                } else if (strTrim.equals("--android-platform-build")) {
                    builder.setAndroidPlatformBuild(true);
                } else if (strTrim.equals("--art-profile")) {
                    builder.addArtProfileForRewriting(I3.a(Paths.get(str2, new String[0])), AbstractC3408y3.a(Paths.get(str, new String[0])));
                } else if (strTrim.equals("--startup-profile")) {
                    builder.addStartupProfileProviders(BW.a(Paths.get(str2, new String[0])));
                } else if (strTrim.startsWith("--")) {
                    if (!a(builder, strTrim, origin)) {
                        int iB = AbstractC3600l.b(builder, strTrim, strArrA, i, origin);
                        if (iB < 0 && (iB = AbstractC3600l.a(builder, strTrim, strArrA, i, origin)) < 0) {
                            builder.error(new StringDiagnostic("Unknown option: " + strTrim, origin));
                        } else {
                            i += iB;
                        }
                    }
                } else if (strTrim.startsWith(PreprocessCommandLineArgumentsKt.ARGFILE_ARGUMENT)) {
                    builder.error(new StringDiagnostic("Recursive @argfiles are not supported: ", origin));
                } else {
                    builder.addProgramFiles(Paths.get(strTrim, new String[0]));
                }
            }
        }
        if (!aVarA.b()) {
            builder.addClasspathResourceProvider((ClassFileResourceProvider) aVarA.a());
        }
        if (compilationMode != null) {
            builder.setMode(compilationMode);
        }
        if (outputMode == null) {
            outputMode = OutputMode.DexIndexed;
        }
        if (path == null) {
            path = Paths.get(".", new String[0]);
        }
        return builder.setOutput(path, outputMode);
    }
}
