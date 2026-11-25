package org.jetbrains.kotlin.incremental;

import java.io.File;
import java.util.List;
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
import org.jetbrains.kotlin.build.report.BuildReporter;
import org.jetbrains.kotlin.build.report.ICReporter;
import org.jetbrains.kotlin.build.report.metrics.DoNothingBuildMetricsReporter;
import org.jetbrains.kotlin.cli.common.arguments.CommonCompilerArguments;
import org.jetbrains.kotlin.cli.common.arguments.K2JSCompilerArguments;
import org.jetbrains.kotlin.cli.common.messages.MessageCollector;
import org.jetbrains.kotlin.config.IncrementalCompilation;
import org.jetbrains.kotlin.idea.KotlinFileType;
import org.jetbrains.kotlin.incremental.multiproject.ModulesApiHistory;

/* JADX INFO: compiled from: IncrementalJsCompilerRunner.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000P\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001c\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a`\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00032\b\b\u0002\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\r\u001a\u00020\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\u00102\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u0012\u001a7\u0010\u0013\u001a\u0002H\u0014\"\u0004\b\u0000\u0010\u00142\u0006\u0010\u0006\u001a\u00020\u00152\b\b\u0002\u0010\u0016\u001a\u00020\u00172\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u0002H\u00140\u0019H\u0086\bø\u0001\u0000¢\u0006\u0002\u0010\u001a\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u001b"}, d2 = {"makeJsIncrementally", "", "cachesDir", "Ljava/io/File;", "sourceRoots", "", "args", "Lorg/jetbrains/kotlin/cli/common/arguments/K2JSCompilerArguments;", "buildHistoryFile", "messageCollector", "Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;", "reporter", "Lorg/jetbrains/kotlin/build/report/ICReporter;", "scopeExpansion", "Lorg/jetbrains/kotlin/incremental/CompileScopeExpansionMode;", "modulesApiHistory", "Lorg/jetbrains/kotlin/incremental/multiproject/ModulesApiHistory;", "providedChangedFiles", "Lorg/jetbrains/kotlin/incremental/ChangedFiles;", "withJsIC", "R", "Lorg/jetbrains/kotlin/cli/common/arguments/CommonCompilerArguments;", CompilerOptions.ENABLED, "", "fn", "Lkotlin/Function0;", "(Lorg/jetbrains/kotlin/cli/common/arguments/CommonCompilerArguments;ZLkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "incremental-compilation-impl"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class IncrementalJsCompilerRunnerKt {
    public static final void makeJsIncrementally(File cachesDir, Iterable<? extends File> sourceRoots, K2JSCompilerArguments args, File buildHistoryFile, MessageCollector messageCollector, ICReporter reporter, CompileScopeExpansionMode scopeExpansion, ModulesApiHistory modulesApiHistory, ChangedFiles changedFiles) {
        Intrinsics.checkNotNullParameter(cachesDir, "cachesDir");
        Intrinsics.checkNotNullParameter(sourceRoots, "sourceRoots");
        Intrinsics.checkNotNullParameter(args, "args");
        Intrinsics.checkNotNullParameter(buildHistoryFile, "buildHistoryFile");
        Intrinsics.checkNotNullParameter(messageCollector, "messageCollector");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        Intrinsics.checkNotNullParameter(scopeExpansion, "scopeExpansion");
        Intrinsics.checkNotNullParameter(modulesApiHistory, "modulesApiHistory");
        List list = SequencesKt.toList(SequencesKt.filter(SequencesKt.flatMap(CollectionsKt.asSequence(sourceRoots), new Function1<File, FileTreeWalk>() { // from class: org.jetbrains.kotlin.incremental.IncrementalJsCompilerRunnerKt$makeJsIncrementally$allKotlinFiles$1
            @Override // kotlin.jvm.functions.Function1
            public final FileTreeWalk invoke(File it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return FilesKt.walk$default(it, null, 1, null);
            }
        }), new Function1<File, Boolean>() { // from class: org.jetbrains.kotlin.incremental.IncrementalJsCompilerRunnerKt$makeJsIncrementally$allKotlinFiles$2
            /* JADX WARN: Code duplicated, block: B:7:0x0019  */
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(File it) {
                boolean z;
                Intrinsics.checkNotNullParameter(it, "it");
                if (it.isFile()) {
                    z = StringsKt.equals(FilesKt.getExtension(it), KotlinFileType.EXTENSION, true);
                }
                return Boolean.valueOf(z);
            }
        }));
        BuildReporter buildReporter = new BuildReporter(reporter, DoNothingBuildMetricsReporter.INSTANCE);
        boolean zIsEnabledForJs = IncrementalCompilation.isEnabledForJs();
        IncrementalCompilation.setIsEnabledForJs(true);
        try {
            if (args.getIncrementalCompilation() == null) {
                args.setIncrementalCompilation(true);
            }
            IncrementalCompilerRunner.compile$default(new IncrementalJsCompilerRunner(cachesDir, buildReporter, buildHistoryFile, modulesApiHistory, scopeExpansion, false, false, false, 224, null), list, args, messageCollector, changedFiles, null, 16, null);
        } finally {
            IncrementalCompilation.setIsEnabledForJs(zIsEnabledForJs);
        }
    }

    public static /* synthetic */ Object withJsIC$default(CommonCompilerArguments args, boolean z, Function0 fn, int i, Object obj) {
        if ((i & 2) != 0) {
            z = true;
        }
        Intrinsics.checkNotNullParameter(args, "args");
        Intrinsics.checkNotNullParameter(fn, "fn");
        boolean zIsEnabledForJs = IncrementalCompilation.isEnabledForJs();
        IncrementalCompilation.setIsEnabledForJs(true);
        try {
            if (args.getIncrementalCompilation() == null) {
                args.setIncrementalCompilation(Boolean.valueOf(z));
            }
            return fn.invoke();
        } finally {
            InlineMarker.finallyStart(1);
            IncrementalCompilation.setIsEnabledForJs(zIsEnabledForJs);
            InlineMarker.finallyEnd(1);
        }
    }

    public static final <R> R withJsIC(CommonCompilerArguments args, boolean z, Function0<? extends R> fn) {
        Intrinsics.checkNotNullParameter(args, "args");
        Intrinsics.checkNotNullParameter(fn, "fn");
        boolean zIsEnabledForJs = IncrementalCompilation.isEnabledForJs();
        IncrementalCompilation.setIsEnabledForJs(true);
        try {
            if (args.getIncrementalCompilation() == null) {
                args.setIncrementalCompilation(Boolean.valueOf(z));
            }
            return fn.invoke();
        } finally {
            InlineMarker.finallyStart(1);
            IncrementalCompilation.setIsEnabledForJs(zIsEnabledForJs);
            InlineMarker.finallyEnd(1);
        }
    }
}
