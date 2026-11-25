package org.jetbrains.kotlin.incremental;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.io.FileTreeWalk;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import kotlin.text.StringsKt;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.jetbrains.kotlin.build.SourcesUtilsKt;
import org.jetbrains.kotlin.build.report.BuildReporter;
import org.jetbrains.kotlin.build.report.DoNothingICReporter;
import org.jetbrains.kotlin.build.report.ICReporter;
import org.jetbrains.kotlin.build.report.metrics.DoNothingBuildMetricsReporter;
import org.jetbrains.kotlin.cli.common.arguments.CommonCompilerArguments;
import org.jetbrains.kotlin.cli.common.arguments.K2JVMCompilerArguments;
import org.jetbrains.kotlin.cli.common.messages.MessageCollector;
import org.jetbrains.kotlin.config.IncrementalCompilation;
import org.jetbrains.kotlin.config.LanguageVersion;
import org.jetbrains.kotlin.incremental.multiproject.EmptyModulesApiHistory;

/* JADX INFO: compiled from: CompilerRunnerUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000F\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u001c\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a8\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00022\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00042\b\b\u0002\u0010\u0014\u001a\u00020\u00152\b\b\u0002\u0010\u0016\u001a\u00020\u0017\u001a7\u0010\u0018\u001a\u0002H\u0019\"\u0004\b\u0000\u0010\u00192\u0006\u0010\u0013\u001a\u00020\u001a2\b\b\u0002\u0010\u001b\u001a\u00020\u001c2\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u0002H\u00190\u001eH\u0086\bø\u0001\u0000¢\u0006\u0002\u0010\u001f\"4\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u00042\f\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u00018F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b\"(\u0010\t\u001a\u00020\u0002*\u00020\u00042\u0006\u0010\u0000\u001a\u00020\u00028F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\r\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006 "}, d2 = {"value", "", "Ljava/io/File;", "classpathAsList", "Lorg/jetbrains/kotlin/cli/common/arguments/K2JVMCompilerArguments;", "getClasspathAsList", "(Lorg/jetbrains/kotlin/cli/common/arguments/K2JVMCompilerArguments;)Ljava/util/List;", "setClasspathAsList", "(Lorg/jetbrains/kotlin/cli/common/arguments/K2JVMCompilerArguments;Ljava/util/List;)V", "destinationAsFile", "getDestinationAsFile", "(Lorg/jetbrains/kotlin/cli/common/arguments/K2JVMCompilerArguments;)Ljava/io/File;", "setDestinationAsFile", "(Lorg/jetbrains/kotlin/cli/common/arguments/K2JVMCompilerArguments;Ljava/io/File;)V", "makeJvmIncrementally", "", "cachesDir", "sourceRoots", "", "args", "messageCollector", "Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;", "reporter", "Lorg/jetbrains/kotlin/build/report/ICReporter;", "withIncrementalCompilation", "R", "Lorg/jetbrains/kotlin/cli/common/arguments/CommonCompilerArguments;", CompilerOptions.ENABLED, "", "fn", "Lkotlin/Function0;", "(Lorg/jetbrains/kotlin/cli/common/arguments/CommonCompilerArguments;ZLkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "incremental-compilation-impl"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CompilerRunnerUtils {
    public static final File getDestinationAsFile(K2JVMCompilerArguments k2JVMCompilerArguments) {
        Intrinsics.checkNotNullParameter(k2JVMCompilerArguments, "<this>");
        return new File(k2JVMCompilerArguments.getDestination());
    }

    public static final void setDestinationAsFile(K2JVMCompilerArguments k2JVMCompilerArguments, File value) {
        Intrinsics.checkNotNullParameter(k2JVMCompilerArguments, "<this>");
        Intrinsics.checkNotNullParameter(value, "value");
        k2JVMCompilerArguments.setDestination(value.getAbsolutePath());
    }

    public static final List<File> getClasspathAsList(K2JVMCompilerArguments k2JVMCompilerArguments) {
        Intrinsics.checkNotNullParameter(k2JVMCompilerArguments, "<this>");
        String classpath = k2JVMCompilerArguments.getClasspath();
        if (classpath == null) {
            classpath = "";
        }
        String str = classpath;
        String pathSeparator = File.pathSeparator;
        Intrinsics.checkNotNullExpressionValue(pathSeparator, "pathSeparator");
        List listSplit$default = StringsKt.split$default((CharSequence) str, new String[]{pathSeparator}, false, 0, 6, (Object) null);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listSplit$default, 10));
        Iterator it = listSplit$default.iterator();
        while (it.hasNext()) {
            arrayList.add(new File((String) it.next()));
        }
        return arrayList;
    }

    public static final void setClasspathAsList(K2JVMCompilerArguments k2JVMCompilerArguments, List<? extends File> value) {
        Intrinsics.checkNotNullParameter(k2JVMCompilerArguments, "<this>");
        Intrinsics.checkNotNullParameter(value, "value");
        List<? extends File> list = value;
        String pathSeparator = File.pathSeparator;
        Intrinsics.checkNotNullExpressionValue(pathSeparator, "pathSeparator");
        k2JVMCompilerArguments.setClasspath(CollectionsKt.joinToString$default(list, pathSeparator, null, null, 0, null, new Function1<File, CharSequence>() { // from class: org.jetbrains.kotlin.incremental.CompilerRunnerUtils$classpathAsList$2
            @Override // kotlin.jvm.functions.Function1
            public final CharSequence invoke(File it) {
                Intrinsics.checkNotNullParameter(it, "it");
                String absolutePath = it.getAbsolutePath();
                Intrinsics.checkNotNullExpressionValue(absolutePath, "getAbsolutePath(...)");
                return absolutePath;
            }
        }, 30, null));
    }

    public static /* synthetic */ void makeJvmIncrementally$default(File file, Iterable iterable, K2JVMCompilerArguments k2JVMCompilerArguments, MessageCollector messageCollector, ICReporter iCReporter, int i, Object obj) {
        if ((i & 8) != 0) {
            messageCollector = MessageCollector.INSTANCE.getNONE();
        }
        if ((i & 16) != 0) {
            iCReporter = DoNothingICReporter.INSTANCE;
        }
        makeJvmIncrementally(file, iterable, k2JVMCompilerArguments, messageCollector, iCReporter);
    }

    public static final void makeJvmIncrementally(File cachesDir, Iterable<? extends File> sourceRoots, K2JVMCompilerArguments args, MessageCollector messageCollector, ICReporter reporter) {
        IncrementalFirJvmCompilerRunner incrementalJvmCompilerRunner;
        LanguageVersion languageVersionFromVersionString;
        Intrinsics.checkNotNullParameter(cachesDir, "cachesDir");
        Intrinsics.checkNotNullParameter(sourceRoots, "sourceRoots");
        Intrinsics.checkNotNullParameter(args, "args");
        Intrinsics.checkNotNullParameter(messageCollector, "messageCollector");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        List<String> default_kotlin_source_files_extensions = SourcesUtilsKt.getDEFAULT_KOTLIN_SOURCE_FILES_EXTENSIONS();
        final List listPlus = CollectionsKt.plus((Collection<? extends String>) default_kotlin_source_files_extensions, SuffixConstants.EXTENSION_java);
        List list = SequencesKt.toList(SequencesKt.filter(SequencesKt.filter(SequencesKt.flatMap(CollectionsKt.asSequence(sourceRoots), new Function1<File, FileTreeWalk>() { // from class: org.jetbrains.kotlin.incremental.CompilerRunnerUtils$makeJvmIncrementally$rootsWalk$1
            @Override // kotlin.jvm.functions.Function1
            public final FileTreeWalk invoke(File it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return FilesKt.walk$default(it, null, 1, null);
            }
        }), CompilerRunnerUtils$makeJvmIncrementally$files$1.INSTANCE), new Function1<File, Boolean>() { // from class: org.jetbrains.kotlin.incremental.CompilerRunnerUtils$makeJvmIncrementally$sourceFiles$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(File it) {
                Intrinsics.checkNotNullParameter(it, "it");
                List<String> list2 = listPlus;
                String lowerCase = FilesKt.getExtension(it).toLowerCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
                return Boolean.valueOf(list2.contains(lowerCase));
            }
        }));
        File file = new File(cachesDir, "build-history.bin");
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(sourceRoots, 10));
        Iterator<? extends File> it = sourceRoots.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().getAbsolutePath());
        }
        boolean z = false;
        args.setJavaSourceRoots((String[]) arrayList.toArray(new String[0]));
        BuildReporter buildReporter = new BuildReporter(reporter, DoNothingBuildMetricsReporter.INSTANCE);
        boolean zIsEnabledForJvm = IncrementalCompilation.isEnabledForJvm();
        IncrementalCompilation.setIsEnabledForJvm(true);
        try {
            if (args.getIncrementalCompilation() == null) {
                args.setIncrementalCompilation(true);
            }
            if (args.getUseK2() || ((languageVersionFromVersionString = LanguageVersion.INSTANCE.fromVersionString(args.getLanguageVersion())) != null && languageVersionFromVersionString.getUsesK2())) {
                z = true;
            }
            if (z && args.getUseFirIC() && args.getUseFirLT()) {
                incrementalJvmCompilerRunner = new IncrementalFirJvmCompilerRunner(cachesDir, buildReporter, file, null, EmptyModulesApiHistory.INSTANCE, default_kotlin_source_files_extensions, ClasspathChanges.ClasspathSnapshotDisabled.INSTANCE);
            } else {
                incrementalJvmCompilerRunner = new IncrementalJvmCompilerRunner(cachesDir, buildReporter, !z, file, null, EmptyModulesApiHistory.INSTANCE, default_kotlin_source_files_extensions, ClasspathChanges.ClasspathSnapshotDisabled.INSTANCE, false, false, false, 1792, null);
            }
            IncrementalCompilerRunner.compile$default(incrementalJvmCompilerRunner, list, args, messageCollector, null, null, 16, null);
        } finally {
            IncrementalCompilation.setIsEnabledForJvm(zIsEnabledForJvm);
        }
    }

    public static /* synthetic */ Object withIncrementalCompilation$default(CommonCompilerArguments args, boolean z, Function0 fn, int i, Object obj) {
        if ((i & 2) != 0) {
            z = true;
        }
        Intrinsics.checkNotNullParameter(args, "args");
        Intrinsics.checkNotNullParameter(fn, "fn");
        boolean zIsEnabledForJvm = IncrementalCompilation.isEnabledForJvm();
        IncrementalCompilation.setIsEnabledForJvm(z);
        try {
            if (args.getIncrementalCompilation() == null) {
                args.setIncrementalCompilation(Boolean.valueOf(z));
            }
            return fn.invoke();
        } finally {
            InlineMarker.finallyStart(1);
            IncrementalCompilation.setIsEnabledForJvm(zIsEnabledForJvm);
            InlineMarker.finallyEnd(1);
        }
    }

    public static final <R> R withIncrementalCompilation(CommonCompilerArguments args, boolean z, Function0<? extends R> fn) {
        Intrinsics.checkNotNullParameter(args, "args");
        Intrinsics.checkNotNullParameter(fn, "fn");
        boolean zIsEnabledForJvm = IncrementalCompilation.isEnabledForJvm();
        IncrementalCompilation.setIsEnabledForJvm(z);
        try {
            if (args.getIncrementalCompilation() == null) {
                args.setIncrementalCompilation(Boolean.valueOf(z));
            }
            return fn.invoke();
        } finally {
            InlineMarker.finallyStart(1);
            IncrementalCompilation.setIsEnabledForJvm(zIsEnabledForJvm);
            InlineMarker.finallyEnd(1);
        }
    }
}
