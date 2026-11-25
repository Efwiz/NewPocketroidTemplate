package com.android.tools.r8;

import com.android.tools.r8.internal.AbstractC1048Hm;
import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.internal.AbstractC3408y3;
import com.android.tools.r8.internal.BW;
import com.android.tools.r8.internal.C1507Xl;
import com.android.tools.r8.internal.I3;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.utils.AbstractC3867a1;
import com.android.tools.r8.utils.StringDiagnostic;
import com.android.tools.r8.utils.V2;
import com.android.tools.r8.utils.Y1;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import name.antonsmirnov.firmata.FormatHelper;
import org.jetbrains.kotlin.cli.common.arguments.PreprocessCommandLineArgumentsKt;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class a0 extends AbstractC3600l {
    private static final AbstractC1048Hm b = AbstractC1048Hm.a("--output", "--lib", "--classpath", "--min-api", "--main-dex-rules", "--main-dex-list", "--feature", "--main-dex-list-output", "--pg-conf", "--pg-conf-output", "--pg-map-output", "--desugared-lib", "--desugared-lib-pg-conf-output", "--map-id-template", "--source-file-template", "--art-profile", "--startup-profile", "--thread-count");
    private static final AbstractC1048Hm c = AbstractC1048Hm.a("--art-profile", "--feature");
    public static final /* synthetic */ int d = 0;

    static AbstractC1639am a() {
        C1507Xl c1507XlA = AbstractC1639am.g().a(S.b(true)).a(S.a(false)).a(S.b()).a(S.a("--classfile", "Compile program to Java classfile format.")).a(S.a("--output", "<file>", "Output result in <file>.", "<file> must be an existing directory or a zip file.")).a(S.a("--lib", "<file|jdk-home>", "Add <file|jdk-home> as a library resource.")).a(S.a("--classpath", "<file>", "Add <file> as a classpath resource.")).a(S.d()).a(S.a("--pg-compat", "Compile with R8 in Proguard compatibility mode.")).a(S.a("--pg-conf", "<file>", "Proguard configuration <file>.")).a(S.a("--pg-conf-output", "<file>", "Output the collective configuration to <file>.")).a(S.a("--pg-map-output", "<file>", "Output the resulting name and line mapping to <file>.")).a(S.a("--desugared-lib", "<file>", "Specify desugared library configuration.", "<file> is a desugared library configuration (json).")).a(S.a("--desugared-lib-pg-conf-output", "<file>", "Output the Proguard configuration for L8 to <file>.")).a(S.a("--no-tree-shaking", "Force disable tree shaking of unreachable classes.")).a(S.a("--no-minification", "Force disable minification of names.")).a(S.a("--no-data-resources", "Ignore all data resources.")).a(S.a("--no-desugaring", "Force disable desugaring.")).a(S.a("--main-dex-rules", "<file>", "Proguard keep rules for classes to place in the", "primary dex file.")).a(S.a("--main-dex-list", "<file>", "List of classes to place in the primary dex file."));
        AbstractC1639am abstractC1639amA = AbstractC1639am.a("<input>", "<output>");
        List listAsList = Arrays.asList("Add feature <input> file to <output> file. Several ", "occurrences can map to the same output.");
        StringBuilder sb = new StringBuilder("--feature");
        Iterator it = abstractC1639amA.iterator();
        while (it.hasNext()) {
            sb.append(" ").append((String) it.next());
        }
        C1507Xl c1507XlA2 = c1507XlA.a(new S(sb.toString(), Collections.emptyList(), listAsList)).a(S.a("--main-dex-list-output", "<file>", "Output the full main-dex list in <file>.")).a((Iterable) S.a()).a(S.a("--thread-count", "<number>", "Use <number> of threads for compilation.", "If not specified the number will be based on", "heuristics taking the number of cores into account.")).a(S.c()).a(S.a("--map-id-template", "<template>", "Set the map-id to <template>.", "The <template> can reference the variables:", "  %MAP_HASH: compiler generated mapping hash.")).a(S.a("--source-file-template", "<template>", "Set all source-file attributes to <template>", "The <template> can reference the variables:", "  %MAP_ID: map id (e.g., value of --map-id-template).", "  %MAP_HASH: compiler generated mapping hash.")).a(S.a("--android-platform-build", "Compile as a platform build where the runtime/bootclasspath", "is assumed to be the version specified by --min-api."));
        AbstractC1639am abstractC1639amA2 = AbstractC1639am.a("<input>", "<output>");
        List listAsList2 = Arrays.asList("Rewrite human readable ART profile read from <input> and write to <output>.");
        StringBuilder sb2 = new StringBuilder("--art-profile");
        Iterator it2 = abstractC1639amA2.iterator();
        while (it2.hasNext()) {
            sb2.append(" ").append((String) it2.next());
        }
        return c1507XlA2.a(new S(sb2.toString(), Collections.emptyList(), listAsList2)).a(S.a("r8")).a(S.a("--help", "Print this message.")).a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(R8Command.Builder builder, Path path, List list) {
        a(builder, path, list);
    }

    public static R8Command.Builder a(String[] strArr, Origin origin) {
        a0 a0Var = new a0();
        R8Command.Builder builder = R8Command.builder();
        Z z = new Z();
        a0Var.a(strArr, origin, builder, z);
        CompilationMode compilationMode = z.a;
        if (compilationMode != null) {
            builder.setMode(compilationMode);
        }
        Path path = z.c;
        if (path == null) {
            path = Paths.get(".", new String[0]);
        }
        OutputMode outputMode = z.b;
        if (outputMode == null) {
            outputMode = OutputMode.DexIndexed;
        }
        builder.setOutput(path, outputMode, z.e);
        builder.setEnableExperimentalMissingLibraryApiModeling(true);
        return builder;
    }

    public static R8Command.Builder a(String[] strArr, Origin origin, DiagnosticsHandler diagnosticsHandler) {
        a0 a0Var = new a0();
        R8Command.Builder builder = R8Command.builder(diagnosticsHandler);
        Z z = new Z();
        a0Var.a(strArr, origin, builder, z);
        CompilationMode compilationMode = z.a;
        if (compilationMode != null) {
            builder.setMode(compilationMode);
        }
        Path path = z.c;
        if (path == null) {
            path = Paths.get(".", new String[0]);
        }
        OutputMode outputMode = z.b;
        if (outputMode == null) {
            outputMode = OutputMode.DexIndexed;
        }
        builder.setOutput(path, outputMode, z.e);
        builder.setEnableExperimentalMissingLibraryApiModeling(true);
        return builder;
    }

    private void a(String[] strArr, Origin origin, final R8Command.Builder builder, Z z) {
        String str;
        Objects.requireNonNull(builder);
        String[] strArrA = AbstractC3867a1.a(strArr, new Consumer() { // from class: com.android.tools.r8.a0$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                builder.error((Diagnostic) obj);
            }
        });
        HashMap map = new HashMap();
        int i = 0;
        while (i < strArrA.length) {
            String strTrim = strArrA[i].trim();
            String str2 = null;
            if (b.contains(strTrim)) {
                int i2 = i + 1;
                if (i2 < strArrA.length) {
                    String str3 = strArrA[i2];
                    if (c.contains(strTrim)) {
                        i2 = i + 2;
                        if (i2 < strArrA.length) {
                            str2 = strArrA[i2];
                        } else {
                            builder.error(new StringDiagnostic("Missing parameter for " + strArrA[i] + ".", origin));
                            break;
                        }
                    }
                    i = i2;
                    str = str2;
                    str2 = str3;
                } else {
                    builder.error(new StringDiagnostic("Missing parameter for " + strArrA[i] + ".", origin));
                    break;
                }
            } else {
                str = null;
            }
            if (strTrim.length() != 0) {
                if (strTrim.equals("--help")) {
                    builder.setPrintHelp(true);
                } else if (strTrim.equals("--version")) {
                    builder.setPrintVersion(true);
                } else if (strTrim.equals("--debug")) {
                    if (z.a == CompilationMode.RELEASE) {
                        builder.error(new StringDiagnostic("Cannot compile in both --debug and --release mode.", origin));
                    }
                    z.a = CompilationMode.DEBUG;
                } else if (strTrim.equals("--release")) {
                    if (z.a == CompilationMode.DEBUG) {
                        builder.error(new StringDiagnostic("Cannot compile in both --debug and --release mode.", origin));
                    }
                    z.a = CompilationMode.RELEASE;
                } else if (strTrim.equals("--pg-compat")) {
                    builder.setProguardCompatibility(true);
                } else if (strTrim.equals("--dex")) {
                    if (z.b == OutputMode.ClassFile) {
                        builder.error(new StringDiagnostic("Cannot compile in both --dex and --classfile output mode.", origin));
                    }
                    z.b = OutputMode.DexIndexed;
                } else if (strTrim.equals("--classfile")) {
                    if (z.b == OutputMode.DexIndexed) {
                        builder.error(new StringDiagnostic("Cannot compile in both --dex and --classfile output mode.", origin));
                    }
                    z.b = OutputMode.ClassFile;
                } else if (strTrim.equals("--output")) {
                    if (z.c != null) {
                        builder.error(new StringDiagnostic("Cannot output both to '" + z.c.toString() + "' and '" + str2 + FormatHelper.QUOTE, origin));
                    }
                    z.c = Paths.get(str2, new String[0]);
                } else if (strTrim.equals("--lib")) {
                    AbstractC3600l.a(builder, origin, str2);
                } else if (strTrim.equals("--classpath")) {
                    builder.addClasspathFiles(Paths.get(str2, new String[0]));
                } else if (strTrim.equals("--min-api")) {
                    if (z.d) {
                        builder.error(new StringDiagnostic("Cannot set multiple --min-api options", origin));
                    } else {
                        AbstractC3600l.a(new Consumer() { // from class: com.android.tools.r8.a0$$ExternalSyntheticLambda0
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                builder.error((Diagnostic) obj);
                            }
                        }, "--min-api", str2, origin, new Consumer() { // from class: com.android.tools.r8.a0$$ExternalSyntheticLambda1
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                builder.setMinApiLevel(((Integer) obj).intValue());
                            }
                        });
                        z.d = true;
                    }
                } else if (strTrim.equals("--thread-count")) {
                    AbstractC3600l.a(new Consumer() { // from class: com.android.tools.r8.a0$$ExternalSyntheticLambda0
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            builder.error((Diagnostic) obj);
                        }
                    }, "--thread-count", str2, origin, new Consumer() { // from class: com.android.tools.r8.a0$$ExternalSyntheticLambda2
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            builder.a(((Integer) obj).intValue());
                        }
                    });
                } else if (strTrim.equals("--no-tree-shaking")) {
                    builder.setDisableTreeShaking(true);
                } else if (strTrim.equals("--no-minification")) {
                    builder.setDisableMinification(true);
                } else if (strTrim.equals("--no-desugaring")) {
                    builder.setDisableDesugaring(true);
                } else if (strTrim.equals("--main-dex-rules")) {
                    builder.addMainDexRulesFiles(Paths.get(str2, new String[0]));
                } else if (strTrim.equals("--feature")) {
                    ((List) map.computeIfAbsent(Paths.get(str, new String[0]), new Function() { // from class: com.android.tools.r8.a0$$ExternalSyntheticLambda3
                        @Override // java.util.function.Function
                        public final Object apply(Object obj) {
                            return a0.a((Path) obj);
                        }
                    })).add(Paths.get(str2, new String[0]));
                } else if (strTrim.equals("--main-dex-list")) {
                    builder.addMainDexListFiles(Paths.get(str2, new String[0]));
                } else if (strTrim.equals("--main-dex-list-output")) {
                    builder.setMainDexListOutputPath(Paths.get(str2, new String[0]));
                } else if (strTrim.equals("--optimize-multidex-for-linearalloc")) {
                    builder.setOptimizeMultidexForLinearAlloc(true);
                } else if (strTrim.equals("--pg-conf")) {
                    builder.addProguardConfigurationFiles(Paths.get(str2, new String[0]));
                } else if (strTrim.equals("--pg-conf-output")) {
                    builder.setProguardConfigurationConsumer(new StringConsumer.FileConsumer(Paths.get(str2, new String[0])));
                } else if (strTrim.equals("--pg-map-output")) {
                    builder.setProguardMapOutputPath(Paths.get(str2, new String[0]));
                } else if (strTrim.equals("--desugared-lib")) {
                    builder.addDesugaredLibraryConfiguration(d0.a(Paths.get(str2, new String[0])));
                } else if (strTrim.equals("--desugared-lib-pg-conf-output")) {
                    builder.setDesugaredLibraryKeepRuleConsumer(new StringConsumer.FileConsumer(Paths.get(str2, new String[0])));
                } else if (strTrim.equals("--no-data-resources")) {
                    z.e = false;
                } else if (strTrim.equals("--map-id-template")) {
                    builder.setMapIdProvider(Y1.a(str2, builder.b()));
                } else if (strTrim.equals("--source-file-template")) {
                    builder.setSourceFileProvider(V2.a(str2, builder.b()));
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
            i++;
        }
        map.forEach(new BiConsumer() { // from class: com.android.tools.r8.a0$$ExternalSyntheticLambda4
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.b(builder, (Path) obj, (List) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ List a(Path path) {
        return new ArrayList();
    }

    public static void a(final R8Command.Builder builder, final Path path, final List list) {
        builder.addFeatureSplit(new Function() { // from class: com.android.tools.r8.a0$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return a0.a(builder, path, list, (FeatureSplit.Builder) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ FeatureSplit a(R8Command.Builder builder, Path path, List list, FeatureSplit.Builder builder2) {
        builder2.setProgramConsumer(builder.a(path, OutputMode.DexIndexed, true));
        Iterator it = list.iterator();
        while (it.hasNext()) {
            builder2.addProgramResourceProvider(ArchiveProgramResourceProvider.fromArchive((Path) it.next()));
        }
        return builder2.build();
    }
}
