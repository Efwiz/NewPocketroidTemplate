package org.jetbrains.kotlin.incremental;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.KtIoFileSourceFile;
import org.jetbrains.kotlin.KtSourceFile;
import org.jetbrains.kotlin.KtVirtualFileSourceFile;
import org.jetbrains.kotlin.backend.common.actualizer.IrActualizedResult;
import org.jetbrains.kotlin.backend.common.extensions.IrGenerationExtension;
import org.jetbrains.kotlin.backend.jvm.JvmIrDeserializerImpl;
import org.jetbrains.kotlin.build.SourcesUtilsKt;
import org.jetbrains.kotlin.build.report.BuildReporter;
import org.jetbrains.kotlin.build.report.metrics.GradleBuildPerformanceMetric;
import org.jetbrains.kotlin.build.report.metrics.GradleBuildTime;
import org.jetbrains.kotlin.cli.common.ArgumentsKt;
import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys;
import org.jetbrains.kotlin.cli.common.CommonCompilerPerformanceManager;
import org.jetbrains.kotlin.cli.common.ExitCode;
import org.jetbrains.kotlin.cli.common.GroupedKtSources;
import org.jetbrains.kotlin.cli.common.arguments.CommonCompilerArguments;
import org.jetbrains.kotlin.cli.common.arguments.K2JVMCompilerArguments;
import org.jetbrains.kotlin.cli.common.config.ContentRootsKt;
import org.jetbrains.kotlin.cli.common.config.KotlinSourceRoot;
import org.jetbrains.kotlin.cli.common.environment.UtilKt;
import org.jetbrains.kotlin.cli.common.fir.FirDiagnosticsCompilerResultsReporterKt;
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity;
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSourceLocation;
import org.jetbrains.kotlin.cli.common.messages.GroupingMessageCollector;
import org.jetbrains.kotlin.cli.common.messages.IrMessageCollector;
import org.jetbrains.kotlin.cli.common.messages.MessageCollector;
import org.jetbrains.kotlin.cli.common.modules.ModuleBuilder;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.cli.jvm.JvmArgumentsKt;
import org.jetbrains.kotlin.cli.jvm.compiler.CoreEnvironmentUtilsKt;
import org.jetbrains.kotlin.cli.jvm.compiler.EnvironmentConfigFiles;
import org.jetbrains.kotlin.cli.jvm.compiler.FirKotlinToJvmBytecodeCompilerKt;
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment;
import org.jetbrains.kotlin.cli.jvm.compiler.VfsBasedProjectEnvironment;
import org.jetbrains.kotlin.cli.jvm.compiler.pipeline.CompilerPipelineKt;
import org.jetbrains.kotlin.cli.jvm.compiler.pipeline.ModuleCompilerEnvironment;
import org.jetbrains.kotlin.cli.jvm.compiler.pipeline.ModuleCompilerInput;
import org.jetbrains.kotlin.cli.jvm.compiler.pipeline.ModuleCompilerIrBackendInput;
import org.jetbrains.kotlin.cli.jvm.compiler.pipeline.ModuleCompilerOutput;
import org.jetbrains.kotlin.cli.jvm.config.ClassicFrontendSpecificJvmConfigurationKeys;
import org.jetbrains.kotlin.cli.jvm.config.JvmContentRootsKt;
import org.jetbrains.kotlin.cli.jvm.plugins.PluginCliParser;
import org.jetbrains.kotlin.com.intellij.openapi.Disposable;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.Disposer;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileManager;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileSystem;
import org.jetbrains.kotlin.config.CommonConfigurationKeys;
import org.jetbrains.kotlin.config.CommonConfigurationKeysKt;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.config.CompilerConfigurationKey;
import org.jetbrains.kotlin.config.IncrementalCompilation;
import org.jetbrains.kotlin.config.JVMConfigurationKeys;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.config.Services;
import org.jetbrains.kotlin.constant.EvaluatedConstTracker;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporterFactory;
import org.jetbrains.kotlin.diagnostics.impl.BaseDiagnosticsCollector;
import org.jetbrains.kotlin.diagnostics.impl.PendingDiagnosticsCollectorWithSuppress;
import org.jetbrains.kotlin.extensions.PreprocessedFileCreator;
import org.jetbrains.kotlin.fir.backend.Fir2IrComponents;
import org.jetbrains.kotlin.fir.backend.Fir2IrConfiguration;
import org.jetbrains.kotlin.fir.backend.Fir2IrPluginContext;
import org.jetbrains.kotlin.fir.backend.jvm.JvmFir2IrExtensions;
import org.jetbrains.kotlin.fir.pipeline.ConvertToIrKt;
import org.jetbrains.kotlin.fir.pipeline.Fir2IrActualizedResult;
import org.jetbrains.kotlin.fir.pipeline.FirResult;
import org.jetbrains.kotlin.fir.pipeline.ModuleCompilerAnalyzedOutput;
import org.jetbrains.kotlin.fir.session.environment.AbstractProjectEnvironment;
import org.jetbrains.kotlin.fir.session.environment.AbstractProjectFileSearchScope;
import org.jetbrains.kotlin.idea.KotlinFileType;
import org.jetbrains.kotlin.incremental.components.ExpectActualTracker;
import org.jetbrains.kotlin.incremental.components.InlineConstTracker;
import org.jetbrains.kotlin.incremental.components.LookupTracker;
import org.jetbrains.kotlin.incremental.multiproject.ModulesApiHistory;
import org.jetbrains.kotlin.ir.backend.jvm.serialization.JvmIrMangler;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;
import org.jetbrains.kotlin.ir.util.IrMessageLogger;
import org.jetbrains.kotlin.load.java.JavaClassesTracker;
import org.jetbrains.kotlin.load.kotlin.incremental.components.IncrementalCompilationComponents;
import org.jetbrains.kotlin.metadata.deserialization.BinaryVersion;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmMetadataVersion;
import org.jetbrains.kotlin.modules.Module;
import org.jetbrains.kotlin.modules.TargetId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.platform.CommonPlatforms;
import org.jetbrains.kotlin.platform.jvm.JvmPlatforms;
import org.jetbrains.kotlin.progress.CompilationCanceledException;
import org.jetbrains.kotlin.utils.KotlinPaths;

/* JADX INFO: compiled from: IncrementalFirJvmCompilerRunner.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\b\u0016\u0018\u00002\u00020\u0001B[\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0003\u0012\u000e\u0010\t\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\n\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\u000e\b\u0002\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e\u0012\u0006\u0010\u0010\u001a\u00020\u0011¢\u0006\u0002\u0010\u0012J^\u0010\u0013\u001a\u0014\u0012\u0004\u0012\u00020\u0015\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\n0\u00142\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00030\u000e2\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001e2\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00030\u000e2\u0006\u0010 \u001a\u00020!H\u0014¨\u0006\""}, d2 = {"Lorg/jetbrains/kotlin/incremental/IncrementalFirJvmCompilerRunner;", "Lorg/jetbrains/kotlin/incremental/IncrementalJvmCompilerRunner;", "workingDir", "Ljava/io/File;", "reporter", "Lorg/jetbrains/kotlin/build/report/BuildReporter;", "Lorg/jetbrains/kotlin/build/report/metrics/GradleBuildTime;", "Lorg/jetbrains/kotlin/build/report/metrics/GradleBuildPerformanceMetric;", "buildHistoryFile", "outputDirs", "", "modulesApiHistory", "Lorg/jetbrains/kotlin/incremental/multiproject/ModulesApiHistory;", "kotlinSourceFilesExtensions", "", "", "classpathChanges", "Lorg/jetbrains/kotlin/incremental/ClasspathChanges;", "(Ljava/io/File;Lorg/jetbrains/kotlin/build/report/BuildReporter;Ljava/io/File;Ljava/util/Collection;Lorg/jetbrains/kotlin/incremental/multiproject/ModulesApiHistory;Ljava/util/List;Lorg/jetbrains/kotlin/incremental/ClasspathChanges;)V", "runCompiler", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/cli/common/ExitCode;", "sourcesToCompile", "args", "Lorg/jetbrains/kotlin/cli/common/arguments/K2JVMCompilerArguments;", "caches", "Lorg/jetbrains/kotlin/incremental/IncrementalJvmCachesManager;", "services", "Lorg/jetbrains/kotlin/config/Services;", "messageCollector", "Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;", "allSources", "isIncremental", "", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class IncrementalFirJvmCompilerRunner extends IncrementalJvmCompilerRunner {
    @Override // org.jetbrains.kotlin.incremental.IncrementalJvmCompilerRunner, org.jetbrains.kotlin.incremental.IncrementalCompilerRunner
    public /* bridge */ /* synthetic */ Pair runCompiler(List list, CommonCompilerArguments commonCompilerArguments, IncrementalCachesManager incrementalCachesManager, Services services, MessageCollector messageCollector, List list2, boolean z) {
        return runCompiler((List<? extends File>) list, (K2JVMCompilerArguments) commonCompilerArguments, (IncrementalJvmCachesManager) incrementalCachesManager, services, messageCollector, (List<? extends File>) list2, z);
    }

    public /* synthetic */ IncrementalFirJvmCompilerRunner(File file, BuildReporter buildReporter, File file2, Collection collection, ModulesApiHistory modulesApiHistory, List list, ClasspathChanges classpathChanges, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(file, buildReporter, file2, collection, modulesApiHistory, (i & 32) != 0 ? SourcesUtilsKt.getDEFAULT_KOTLIN_SOURCE_FILES_EXTENSIONS() : list, classpathChanges);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IncrementalFirJvmCompilerRunner(File workingDir, BuildReporter<GradleBuildTime, GradleBuildPerformanceMetric> reporter, File file, Collection<? extends File> collection, ModulesApiHistory modulesApiHistory, List<String> kotlinSourceFilesExtensions, ClasspathChanges classpathChanges) {
        super(workingDir, reporter, false, file, collection, modulesApiHistory, kotlinSourceFilesExtensions, classpathChanges, false, false, false, 1792, null);
        Intrinsics.checkNotNullParameter(workingDir, "workingDir");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        Intrinsics.checkNotNullParameter(modulesApiHistory, "modulesApiHistory");
        Intrinsics.checkNotNullParameter(kotlinSourceFilesExtensions, "kotlinSourceFilesExtensions");
        Intrinsics.checkNotNullParameter(classpathChanges, "classpathChanges");
    }

    /* JADX WARN: Code duplicated, block: B:106:0x033e A[Catch: RuntimeException -> 0x03b7, CompilationCanceledException -> 0x03b9, all -> 0x03c0, PHI: r12
      0x033e: PHI (r12v8 boolean) = (r12v3 boolean), (r12v3 boolean), (r12v13 boolean) binds: [B:95:0x0304, B:97:0x0310, B:101:0x0322] A[DONT_GENERATE, DONT_INLINE], TRY_ENTER, TryCatch #14 {all -> 0x03c0, blocks: (B:74:0x0269, B:76:0x027f, B:78:0x0292, B:80:0x02af, B:82:0x02b5, B:84:0x02da, B:85:0x02de, B:88:0x02f1, B:90:0x02f4, B:94:0x0300, B:96:0x0306, B:99:0x0314, B:100:0x0318, B:102:0x0324, B:104:0x033a, B:106:0x033e, B:107:0x0346, B:109:0x034c, B:111:0x0358, B:113:0x036f, B:116:0x0377, B:119:0x037f, B:120:0x0383, B:122:0x038a, B:125:0x0393, B:127:0x0399, B:128:0x03a3, B:123:0x038e, B:146:0x0405), top: B:244:0x0269 }] */
    /* JADX WARN: Code duplicated, block: B:109:0x034c A[Catch: RuntimeException -> 0x03b7, CompilationCanceledException -> 0x03b9, all -> 0x03c0, TryCatch #14 {all -> 0x03c0, blocks: (B:74:0x0269, B:76:0x027f, B:78:0x0292, B:80:0x02af, B:82:0x02b5, B:84:0x02da, B:85:0x02de, B:88:0x02f1, B:90:0x02f4, B:94:0x0300, B:96:0x0306, B:99:0x0314, B:100:0x0318, B:102:0x0324, B:104:0x033a, B:106:0x033e, B:107:0x0346, B:109:0x034c, B:111:0x0358, B:113:0x036f, B:116:0x0377, B:119:0x037f, B:120:0x0383, B:122:0x038a, B:125:0x0393, B:127:0x0399, B:128:0x03a3, B:123:0x038e, B:146:0x0405), top: B:244:0x0269 }] */
    /* JADX WARN: Code duplicated, block: B:113:0x036f A[Catch: RuntimeException -> 0x03b7, CompilationCanceledException -> 0x03b9, all -> 0x03c0, TryCatch #14 {all -> 0x03c0, blocks: (B:74:0x0269, B:76:0x027f, B:78:0x0292, B:80:0x02af, B:82:0x02b5, B:84:0x02da, B:85:0x02de, B:88:0x02f1, B:90:0x02f4, B:94:0x0300, B:96:0x0306, B:99:0x0314, B:100:0x0318, B:102:0x0324, B:104:0x033a, B:106:0x033e, B:107:0x0346, B:109:0x034c, B:111:0x0358, B:113:0x036f, B:116:0x0377, B:119:0x037f, B:120:0x0383, B:122:0x038a, B:125:0x0393, B:127:0x0399, B:128:0x03a3, B:123:0x038e, B:146:0x0405), top: B:244:0x0269 }] */
    /* JADX WARN: Code duplicated, block: B:114:0x0374  */
    /* JADX WARN: Code duplicated, block: B:154:0x047f A[Catch: RuntimeException -> 0x057e, CompilationCanceledException -> 0x0583, all -> 0x0616, TRY_LEAVE, TryCatch #27 {all -> 0x0616, blocks: (B:215:0x05f4, B:217:0x05fc, B:152:0x0479, B:154:0x047f, B:156:0x0483, B:161:0x0494, B:165:0x049f, B:166:0x04a4, B:171:0x04fa, B:173:0x0500, B:176:0x050a, B:177:0x050e, B:179:0x0529, B:180:0x052e, B:182:0x0544, B:184:0x054b, B:185:0x0550), top: B:237:0x00a5 }] */
    /* JADX WARN: Code duplicated, block: B:159:0x048e  */
    /* JADX WARN: Code duplicated, block: B:161:0x0494 A[Catch: RuntimeException -> 0x049a, CompilationCanceledException -> 0x0585, all -> 0x0616, TRY_ENTER, TryCatch #27 {all -> 0x0616, blocks: (B:215:0x05f4, B:217:0x05fc, B:152:0x0479, B:154:0x047f, B:156:0x0483, B:161:0x0494, B:165:0x049f, B:166:0x04a4, B:171:0x04fa, B:173:0x0500, B:176:0x050a, B:177:0x050e, B:179:0x0529, B:180:0x052e, B:182:0x0544, B:184:0x054b, B:185:0x0550), top: B:237:0x00a5 }] */
    /* JADX WARN: Code duplicated, block: B:165:0x049f A[Catch: RuntimeException -> 0x049a, CompilationCanceledException -> 0x0585, all -> 0x0616, TryCatch #27 {all -> 0x0616, blocks: (B:215:0x05f4, B:217:0x05fc, B:152:0x0479, B:154:0x047f, B:156:0x0483, B:161:0x0494, B:165:0x049f, B:166:0x04a4, B:171:0x04fa, B:173:0x0500, B:176:0x050a, B:177:0x050e, B:179:0x0529, B:180:0x052e, B:182:0x0544, B:184:0x054b, B:185:0x0550), top: B:237:0x00a5 }] */
    /* JADX WARN: Code duplicated, block: B:168:0x04f5  */
    /* JADX WARN: Code duplicated, block: B:169:0x04f7  */
    /* JADX WARN: Code duplicated, block: B:174:0x0507  */
    /* JADX WARN: Code duplicated, block: B:176:0x050a A[Catch: RuntimeException -> 0x049a, CompilationCanceledException -> 0x0585, all -> 0x0616, TryCatch #27 {all -> 0x0616, blocks: (B:215:0x05f4, B:217:0x05fc, B:152:0x0479, B:154:0x047f, B:156:0x0483, B:161:0x0494, B:165:0x049f, B:166:0x04a4, B:171:0x04fa, B:173:0x0500, B:176:0x050a, B:177:0x050e, B:179:0x0529, B:180:0x052e, B:182:0x0544, B:184:0x054b, B:185:0x0550), top: B:237:0x00a5 }] */
    /* JADX WARN: Code duplicated, block: B:179:0x0529 A[Catch: RuntimeException -> 0x049a, CompilationCanceledException -> 0x0585, all -> 0x0616, TryCatch #27 {all -> 0x0616, blocks: (B:215:0x05f4, B:217:0x05fc, B:152:0x0479, B:154:0x047f, B:156:0x0483, B:161:0x0494, B:165:0x049f, B:166:0x04a4, B:171:0x04fa, B:173:0x0500, B:176:0x050a, B:177:0x050e, B:179:0x0529, B:180:0x052e, B:182:0x0544, B:184:0x054b, B:185:0x0550), top: B:237:0x00a5 }] */
    /* JADX WARN: Code duplicated, block: B:182:0x0544 A[Catch: RuntimeException -> 0x049a, CompilationCanceledException -> 0x0585, all -> 0x0616, TryCatch #27 {all -> 0x0616, blocks: (B:215:0x05f4, B:217:0x05fc, B:152:0x0479, B:154:0x047f, B:156:0x0483, B:161:0x0494, B:165:0x049f, B:166:0x04a4, B:171:0x04fa, B:173:0x0500, B:176:0x050a, B:177:0x050e, B:179:0x0529, B:180:0x052e, B:182:0x0544, B:184:0x054b, B:185:0x0550), top: B:237:0x00a5 }] */
    /* JADX WARN: Code duplicated, block: B:184:0x054b A[Catch: RuntimeException -> 0x049a, CompilationCanceledException -> 0x0585, all -> 0x0616, TryCatch #27 {all -> 0x0616, blocks: (B:215:0x05f4, B:217:0x05fc, B:152:0x0479, B:154:0x047f, B:156:0x0483, B:161:0x0494, B:165:0x049f, B:166:0x04a4, B:171:0x04fa, B:173:0x0500, B:176:0x050a, B:177:0x050e, B:179:0x0529, B:180:0x052e, B:182:0x0544, B:184:0x054b, B:185:0x0550), top: B:237:0x00a5 }] */
    /* JADX WARN: Code duplicated, block: B:217:0x05fc A[Catch: all -> 0x0616, TRY_LEAVE, TryCatch #27 {all -> 0x0616, blocks: (B:215:0x05f4, B:217:0x05fc, B:152:0x0479, B:154:0x047f, B:156:0x0483, B:161:0x0494, B:165:0x049f, B:166:0x04a4, B:171:0x04fa, B:173:0x0500, B:176:0x050a, B:177:0x050e, B:179:0x0529, B:180:0x052e, B:182:0x0544, B:184:0x054b, B:185:0x0550), top: B:237:0x00a5 }] */
    /* JADX WARN: Code duplicated, block: B:222:0x0613  */
    /* JADX WARN: Code duplicated, block: B:244:0x0269 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:246:0x01f3 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:253:0x0405 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:264:0x01d2 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:268:0x03df A[EDGE_INSN: B:268:0x03df->B:141:0x03df BREAK  A[LOOP:2: B:71:0x0261->B:130:0x03a9], SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:272:0x0358 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:273:0x0377 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:277:0x0346 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:278:0x0346 A[ADDED_TO_REGION, REMOVE, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:32:0x0141 A[Catch: all -> 0x0113, RuntimeException -> 0x011a, CompilationCanceledException -> 0x0126, TRY_ENTER, TRY_LEAVE, TryCatch #25 {CompilationCanceledException -> 0x0126, RuntimeException -> 0x011a, all -> 0x0113, blocks: (B:23:0x00db, B:32:0x0141, B:37:0x0158, B:42:0x0168, B:47:0x017b, B:52:0x0193), top: B:261:0x00db }] */
    /* JADX WARN: Code duplicated, block: B:35:0x0152 A[Catch: RuntimeException -> 0x05d3, CompilationCanceledException -> 0x05dd, all -> 0x05e2, TRY_ENTER, TRY_LEAVE, TryCatch #0 {all -> 0x05e2, blocks: (B:19:0x00a5, B:21:0x00ac, B:30:0x012e, B:35:0x0152, B:40:0x0162, B:45:0x0175, B:50:0x0185, B:55:0x01a2, B:58:0x01bb, B:49:0x0181, B:44:0x016e, B:39:0x015e), top: B:237:0x00a5 }] */
    /* JADX WARN: Code duplicated, block: B:39:0x015e A[Catch: RuntimeException -> 0x05d3, CompilationCanceledException -> 0x05dd, all -> 0x05e2, TRY_ENTER, TryCatch #0 {all -> 0x05e2, blocks: (B:19:0x00a5, B:21:0x00ac, B:30:0x012e, B:35:0x0152, B:40:0x0162, B:45:0x0175, B:50:0x0185, B:55:0x01a2, B:58:0x01bb, B:49:0x0181, B:44:0x016e, B:39:0x015e), top: B:237:0x00a5 }] */
    /* JADX WARN: Code duplicated, block: B:44:0x016e A[Catch: RuntimeException -> 0x05d3, CompilationCanceledException -> 0x05dd, all -> 0x05e2, TRY_ENTER, TryCatch #0 {all -> 0x05e2, blocks: (B:19:0x00a5, B:21:0x00ac, B:30:0x012e, B:35:0x0152, B:40:0x0162, B:45:0x0175, B:50:0x0185, B:55:0x01a2, B:58:0x01bb, B:49:0x0181, B:44:0x016e, B:39:0x015e), top: B:237:0x00a5 }] */
    /* JADX WARN: Code duplicated, block: B:49:0x0181 A[Catch: RuntimeException -> 0x05d3, CompilationCanceledException -> 0x05dd, all -> 0x05e2, TRY_ENTER, TryCatch #0 {all -> 0x05e2, blocks: (B:19:0x00a5, B:21:0x00ac, B:30:0x012e, B:35:0x0152, B:40:0x0162, B:45:0x0175, B:50:0x0185, B:55:0x01a2, B:58:0x01bb, B:49:0x0181, B:44:0x016e, B:39:0x015e), top: B:237:0x00a5 }] */
    /* JADX WARN: Code duplicated, block: B:52:0x0193 A[Catch: all -> 0x0113, RuntimeException -> 0x011a, CompilationCanceledException -> 0x0126, TRY_ENTER, TRY_LEAVE, TryCatch #25 {CompilationCanceledException -> 0x0126, RuntimeException -> 0x011a, all -> 0x0113, blocks: (B:23:0x00db, B:32:0x0141, B:37:0x0158, B:42:0x0168, B:47:0x017b, B:52:0x0193), top: B:261:0x00db }] */
    /* JADX WARN: Code duplicated, block: B:55:0x01a2 A[Catch: RuntimeException -> 0x05d3, CompilationCanceledException -> 0x05dd, all -> 0x05e2, TRY_ENTER, TRY_LEAVE, TryCatch #0 {all -> 0x05e2, blocks: (B:19:0x00a5, B:21:0x00ac, B:30:0x012e, B:35:0x0152, B:40:0x0162, B:45:0x0175, B:50:0x0185, B:55:0x01a2, B:58:0x01bb, B:49:0x0181, B:44:0x016e, B:39:0x015e), top: B:237:0x00a5 }] */
    /* JADX WARN: Code duplicated, block: B:57:0x01b9  */
    /* JADX WARN: Code duplicated, block: B:78:0x0292 A[Catch: RuntimeException -> 0x03b7, CompilationCanceledException -> 0x03b9, all -> 0x03c0, TryCatch #14 {all -> 0x03c0, blocks: (B:74:0x0269, B:76:0x027f, B:78:0x0292, B:80:0x02af, B:82:0x02b5, B:84:0x02da, B:85:0x02de, B:88:0x02f1, B:90:0x02f4, B:94:0x0300, B:96:0x0306, B:99:0x0314, B:100:0x0318, B:102:0x0324, B:104:0x033a, B:106:0x033e, B:107:0x0346, B:109:0x034c, B:111:0x0358, B:113:0x036f, B:116:0x0377, B:119:0x037f, B:120:0x0383, B:122:0x038a, B:125:0x0393, B:127:0x0399, B:128:0x03a3, B:123:0x038e, B:146:0x0405), top: B:244:0x0269 }] */
    /* JADX WARN: Code duplicated, block: B:87:0x02ed  */
    /* JADX WARN: Code duplicated, block: B:93:0x02fc  */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0, types: [java.lang.Object, org.jetbrains.kotlin.config.Services] */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v11 */
    /* JADX WARN: Type inference failed for: r3v19 */
    /* JADX WARN: Type inference failed for: r3v2, types: [org.jetbrains.kotlin.cli.common.messages.GroupingMessageCollector] */
    /* JADX WARN: Type inference failed for: r3v4 */
    /* JADX WARN: Type inference failed for: r3v41 */
    /* JADX WARN: Type inference failed for: r3v43 */
    @Override // org.jetbrains.kotlin.incremental.IncrementalJvmCompilerRunner
    protected Pair<ExitCode, Collection<File>> runCompiler(List<? extends File> sourcesToCompile, K2JVMCompilerArguments args, IncrementalJvmCachesManager caches, Services services, MessageCollector messageCollector, List<? extends File> allSources, boolean isIncremental) throws Throwable {
        LinkedHashSet linkedHashSetEmptySet;
        Throwable th;
        Disposable disposable;
        ?? r3;
        LinkedHashSet linkedHashSet;
        GroupingMessageCollector groupingMessageCollector;
        String str;
        Object obj;
        GroupingMessageCollector groupingMessageCollector2;
        String str2;
        RuntimeException runtimeException;
        CompilerMessageSourceLocation compilerMessageSourceLocation;
        KotlinPaths kotlinPathsComputeKotlinPaths;
        String[] pluginClasspaths;
        List listEmptyList;
        String[] pluginOptions;
        ArrayList arrayList;
        String[] pluginConfigurations;
        List listEmptyList2;
        ExitCode exitCodeLoadPluginsSafe;
        File file;
        String destination;
        String path;
        VfsBasedProjectEnvironment vfsBasedProjectEnvironmentCreateProjectEnvironment;
        LinkedHashSet linkedHashSet2;
        LinkedHashMap linkedHashMap;
        VirtualFileSystem fileSystem;
        PreprocessedFileCreator preprocessedFileCreator;
        Iterator<KotlinSourceRoot> it;
        Disposable disposable2;
        TargetId targetId;
        VfsBasedProjectEnvironment vfsBasedProjectEnvironment;
        String str3;
        PendingDiagnosticsCollectorWithSuppress pendingDiagnosticsCollectorWithSuppressCreatePendingReporter$default;
        CommonCompilerPerformanceManager commonCompilerPerformanceManager;
        ModuleCompilerEnvironment moduleCompilerEnvironment;
        Ref.ObjectRef objectRef;
        boolean z;
        FirResult firResultRunCompiler$firIncrementalCycle;
        VfsBasedProjectEnvironment vfsBasedProjectEnvironment2;
        List<IrGenerationExtension> listEmptyList3;
        Project project;
        RuntimeException e;
        String path2;
        File file2;
        VirtualFile virtualFileFindFileByPath;
        VirtualFile virtualFileFindFileByPath2;
        VirtualFile virtualFileCreate;
        String str4;
        File file3;
        ?? services2 = services;
        String str5 = "Compilation was canceled";
        Intrinsics.checkNotNullParameter(sourcesToCompile, "sourcesToCompile");
        Intrinsics.checkNotNullParameter(args, "args");
        Intrinsics.checkNotNullParameter(caches, "caches");
        Intrinsics.checkNotNullParameter(services2, "services");
        Intrinsics.checkNotNullParameter(messageCollector, "messageCollector");
        Intrinsics.checkNotNullParameter(allSources, "allSources");
        GroupingMessageCollector groupingMessageCollector3 = new GroupingMessageCollector(messageCollector, args.getAllWarningsAsErrors());
        String moduleName = args.getModuleName();
        if (moduleName == null) {
            moduleName = "main";
        }
        TargetId targetId2 = new TargetId(moduleName, ModuleXmlParser.TYPE_PRODUCTION);
        LinkedHashSet linkedHashSet3 = new LinkedHashSet();
        Iterator it2 = sourcesToCompile.iterator();
        while (it2.hasNext()) {
            linkedHashSet3.add(new KtIoFileSourceFile((File) it2.next()));
        }
        String[] commonSources = args.getCommonSources();
        if (commonSources != null) {
            LinkedHashSet linkedHashSet4 = new LinkedHashSet();
            int length = commonSources.length;
            int i = 0;
            while (i < length) {
                linkedHashSet4.add(new File(commonSources[i]));
                i++;
                commonSources = commonSources;
            }
            linkedHashSetEmptySet = linkedHashSet4;
        } else {
            linkedHashSetEmptySet = null;
        }
        if (linkedHashSetEmptySet == null) {
            linkedHashSetEmptySet = SetsKt.emptySet();
        }
        ExitCode exitCode = ExitCode.OK;
        LinkedHashSet linkedHashSet5 = new LinkedHashSet();
        Disposable disposableNewDisposable = Disposer.newDisposable();
        Intrinsics.checkNotNullExpressionValue(disposableNewDisposable, "newDisposable(...)");
        try {
            try {
                try {
                    try {
                        CompilerConfiguration compilerConfiguration = new CompilerConfiguration();
                        linkedHashSet = linkedHashSet5;
                        try {
                            compilerConfiguration.put(CLIConfigurationKeys.ORIGINAL_MESSAGE_COLLECTOR_KEY, messageCollector);
                            compilerConfiguration.put(CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY, groupingMessageCollector3);
                            compilerConfiguration.put(IrMessageLogger.INSTANCE.getIR_MESSAGE_LOGGER(), new IrMessageCollector(groupingMessageCollector3));
                            ArgumentsKt.setupCommonArguments(compilerConfiguration, args, new Function1<int[], BinaryVersion>() { // from class: org.jetbrains.kotlin.incremental.IncrementalFirJvmCompilerRunner$runCompiler$configuration$1$1
                                @Override // kotlin.jvm.functions.Function1
                                public final BinaryVersion invoke(int[] it3) {
                                    Intrinsics.checkNotNullParameter(it3, "it");
                                    return new JvmMetadataVersion(Arrays.copyOf(it3, it3.length));
                                }
                            });
                            if (!IncrementalCompilation.isEnabledForJvm()) {
                                JvmArgumentsKt.setupJvmSpecificArguments(compilerConfiguration, args);
                                kotlinPathsComputeKotlinPaths = ArgumentsKt.computeKotlinPaths(groupingMessageCollector3, args);
                                if (groupingMessageCollector3.getHasErrors()) {
                                    Pair<ExitCode, Collection<File>> pair = TuplesKt.to(ExitCode.COMPILATION_ERROR, CollectionsKt.emptyList());
                                    groupingMessageCollector3.flush();
                                    Disposer.dispose(disposableNewDisposable);
                                    return pair;
                                }
                                pluginClasspaths = args.getPluginClasspaths();
                                if (pluginClasspaths != null) {
                                    listEmptyList = CollectionsKt.emptyList();
                                } else {
                                    listEmptyList = CollectionsKt.emptyList();
                                }
                                pluginOptions = args.getPluginOptions();
                                if (pluginOptions != null) {
                                    arrayList = new ArrayList();
                                } else {
                                    arrayList = new ArrayList();
                                }
                                pluginConfigurations = args.getPluginConfigurations();
                                if (pluginConfigurations != null) {
                                    listEmptyList2 = CollectionsKt.emptyList();
                                } else {
                                    listEmptyList2 = CollectionsKt.emptyList();
                                }
                                exitCodeLoadPluginsSafe = PluginCliParser.loadPluginsSafe(listEmptyList, (Collection<String>) arrayList, listEmptyList2, compilerConfiguration);
                                if (exitCodeLoadPluginsSafe != ExitCode.OK) {
                                    Pair<ExitCode, Collection<File>> pair2 = TuplesKt.to(exitCodeLoadPluginsSafe, CollectionsKt.emptyList());
                                    groupingMessageCollector3.flush();
                                    Disposer.dispose(disposableNewDisposable);
                                    return pair2;
                                }
                                JvmArgumentsKt.configureJavaModulesContentRoots(compilerConfiguration, args);
                                JvmArgumentsKt.configureStandardLibs(compilerConfiguration, kotlinPathsComputeKotlinPaths, args);
                                JvmArgumentsKt.configureAdvancedJvmOptions(compilerConfiguration, args);
                                JvmArgumentsKt.configureKlibPaths(compilerConfiguration, args);
                                JvmContentRootsKt.configureJdkClasspathRoots(compilerConfiguration);
                                destination = args.getDestination();
                                if (destination == null) {
                                    destination = ".";
                                }
                                file = new File(destination);
                                path = file.getPath();
                                Intrinsics.checkNotNullExpressionValue(path, "getPath(...)");
                                groupingMessageCollector2 = groupingMessageCollector3;
                                if (StringsKt.endsWith$default(path, ".jar", false, 2, (Object) null)) {
                                    compilerConfiguration.put(JVMConfigurationKeys.OUTPUT_JAR, file);
                                } else {
                                    compilerConfiguration.put(JVMConfigurationKeys.OUTPUT_DIRECTORY, file);
                                }
                                CompilerConfigurationKey<List<Module>> compilerConfigurationKey = JVMConfigurationKeys.MODULES;
                                String name2 = targetId2.getName();
                                String path3 = file.getPath();
                                Intrinsics.checkNotNullExpressionValue(path3, "getPath(...)");
                                compilerConfiguration.addAll(compilerConfigurationKey, CollectionsKt.listOf(new ModuleBuilder(name2, path3, targetId2.getType())));
                                IncrementalFirJvmCompilerRunnerKt.configureBaseRoots(compilerConfiguration, args);
                                IncrementalFirJvmCompilerRunnerKt.configureSourceRootsFromSources(compilerConfiguration, allSources, linkedHashSetEmptySet, args.getJavaPackagePrefix());
                                Unit unit = Unit.INSTANCE;
                                UtilKt.setIdeaIoUseFallback();
                                vfsBasedProjectEnvironmentCreateProjectEnvironment = CompilerPipelineKt.createProjectEnvironment(compilerConfiguration, disposableNewDisposable, EnvironmentConfigFiles.JVM_CONFIG_FILES, messageCollector);
                                linkedHashSet2 = new LinkedHashSet();
                                linkedHashSet = new LinkedHashSet();
                                linkedHashMap = new LinkedHashMap();
                                List<KotlinSourceRoot> kotlinSourceRoots = ContentRootsKt.getKotlinSourceRoots(compilerConfiguration);
                                Project project2 = vfsBasedProjectEnvironmentCreateProjectEnvironment.getProject();
                                fileSystem = VirtualFileManager.getInstance().getFileSystem("file");
                                HashSet hashSet = new HashSet();
                                preprocessedFileCreator = new PreprocessedFileCreator(project2);
                                it = kotlinSourceRoots.iterator();
                                boolean z2 = false;
                                while (true) {
                                    disposable2 = disposableNewDisposable;
                                    if (it.hasNext()) {
                                        break;
                                        break;
                                    }
                                    KotlinSourceRoot next = it.next();
                                    path2 = next.getPath();
                                    boolean isCommon = next.getIsCommon();
                                    Iterator<KotlinSourceRoot> it3 = it;
                                    String hmppModuleName = next.getHmppModuleName();
                                    str3 = str5;
                                    file2 = new File(path2);
                                    virtualFileFindFileByPath = fileSystem.findFileByPath(FilesKt.normalize(file2).getPath());
                                    if (virtualFileFindFileByPath == null) {
                                        str4 = "Source file or directory not found: " + path2;
                                        file3 = (File) compilerConfiguration.get(JVMConfigurationKeys.MODULE_XML_FILE);
                                        if (file3 == null) {
                                            CoreEnvironmentUtilsKt.report(compilerConfiguration, CompilerMessageSeverity.ERROR, str4, null);
                                        } else {
                                            CoreEnvironmentUtilsKt.report(compilerConfiguration, CompilerMessageSeverity.ERROR, str4, null);
                                        }
                                    } else {
                                        targetId2 = targetId2;
                                        vfsBasedProjectEnvironmentCreateProjectEnvironment = vfsBasedProjectEnvironmentCreateProjectEnvironment;
                                        if (virtualFileFindFileByPath.isDirectory()) {
                                            for (File file4 : FilesKt.walkTopDown(file2)) {
                                                if (file4.isFile()) {
                                                    File absoluteFile = file4.getAbsoluteFile();
                                                    Intrinsics.checkNotNullExpressionValue(absoluteFile, "getAbsoluteFile(...)");
                                                    virtualFileFindFileByPath2 = fileSystem.findFileByPath(FilesKt.normalize(absoluteFile).getPath());
                                                    if (virtualFileFindFileByPath2 != null) {
                                                        virtualFileCreate = preprocessedFileCreator.create(virtualFileFindFileByPath2);
                                                    } else {
                                                        virtualFileCreate = null;
                                                    }
                                                    if (virtualFileCreate == null) {
                                                    }
                                                }
                                            }
                                        } else {
                                            while (r7.hasNext()) {
                                                if (file4.isFile()) {
                                                    File absoluteFile2 = file4.getAbsoluteFile();
                                                    Intrinsics.checkNotNullExpressionValue(absoluteFile2, "getAbsoluteFile(...)");
                                                    virtualFileFindFileByPath2 = fileSystem.findFileByPath(FilesKt.normalize(absoluteFile2).getPath());
                                                    if (virtualFileFindFileByPath2 != null) {
                                                        virtualFileCreate = preprocessedFileCreator.create(virtualFileFindFileByPath2);
                                                    } else {
                                                        virtualFileCreate = null;
                                                    }
                                                    if (virtualFileCreate == null) {
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    it = it3;
                                    vfsBasedProjectEnvironmentCreateProjectEnvironment = vfsBasedProjectEnvironmentCreateProjectEnvironment;
                                    str5 = str3;
                                    disposableNewDisposable = disposable2;
                                    targetId2 = targetId2;
                                    groupingMessageCollector2.report(CompilerMessageSeverity.INFO, str2, compilerMessageSourceLocation);
                                    Pair<ExitCode, Collection<File>> pair3 = TuplesKt.to(ExitCode.OK, linkedHashSet);
                                    groupingMessageCollector2.flush();
                                    Disposer.dispose(disposableNewDisposable);
                                    return pair3;
                                }
                                targetId = targetId2;
                                vfsBasedProjectEnvironment = vfsBasedProjectEnvironmentCreateProjectEnvironment;
                                str3 = str5;
                                pendingDiagnosticsCollectorWithSuppressCreatePendingReporter$default = DiagnosticReporterFactory.createPendingReporter$default(DiagnosticReporterFactory.INSTANCE, null, 1, null);
                                commonCompilerPerformanceManager = (CommonCompilerPerformanceManager) compilerConfiguration.get(CLIConfigurationKeys.PERF_MANAGER);
                                moduleCompilerEnvironment = new ModuleCompilerEnvironment(vfsBasedProjectEnvironment, pendingDiagnosticsCollectorWithSuppressCreatePendingReporter$default);
                                if (commonCompilerPerformanceManager != null) {
                                    commonCompilerPerformanceManager.notifyCompilerInitialized(0, 0, targetId.getName() + '-' + targetId.getType());
                                    Unit unit2 = Unit.INSTANCE;
                                }
                                objectRef = new Ref.ObjectRef();
                                z = compilerConfiguration.getBoolean(CLIConfigurationKeys.RENDER_DIAGNOSTIC_INTERNAL_NAME);
                                disposableNewDisposable = disposable2;
                                groupingMessageCollector = groupingMessageCollector2;
                                str = str3;
                                firResultRunCompiler$firIncrementalCycle = runCompiler$firIncrementalCycle(linkedHashMap, linkedHashSet, linkedHashSet2, targetId, compilerConfiguration, commonCompilerPerformanceManager, moduleCompilerEnvironment, new Ref.ObjectRef(), pendingDiagnosticsCollectorWithSuppressCreatePendingReporter$default, objectRef, linkedHashSet3, messageCollector, z, caches, linkedHashSet, this, isIncremental, vfsBasedProjectEnvironment);
                                if (firResultRunCompiler$firIncrementalCycle == null) {
                                    Pair<ExitCode, Collection<File>> pair4 = TuplesKt.to(ExitCode.COMPILATION_ERROR, linkedHashSet);
                                    groupingMessageCollector.flush();
                                    Disposer.dispose(disposableNewDisposable);
                                    return pair4;
                                }
                                if (commonCompilerPerformanceManager != null) {
                                    commonCompilerPerformanceManager.notifyGenerationStarted();
                                    Unit unit3 = Unit.INSTANCE;
                                }
                                if (commonCompilerPerformanceManager != null) {
                                    commonCompilerPerformanceManager.notifyIRTranslationStarted();
                                    Unit unit4 = Unit.INSTANCE;
                                }
                                JvmFir2IrExtensions jvmFir2IrExtensions = new JvmFir2IrExtensions(compilerConfiguration, new JvmIrDeserializerImpl(), JvmIrMangler.INSTANCE);
                                LanguageVersionSettings languageVersionSettings = CommonConfigurationKeysKt.getLanguageVersionSettings(compilerConfiguration);
                                BaseDiagnosticsCollector diagnosticsReporter = moduleCompilerEnvironment.getDiagnosticsReporter();
                                Object objPutIfAbsent = compilerConfiguration.putIfAbsent(CommonConfigurationKeys.EVALUATED_CONST_TRACKER, EvaluatedConstTracker.INSTANCE.create());
                                Intrinsics.checkNotNullExpressionValue(objPutIfAbsent, "putIfAbsent(...)");
                                Fir2IrConfiguration fir2IrConfiguration = new Fir2IrConfiguration(languageVersionSettings, diagnosticsReporter, false, (EvaluatedConstTracker) objPutIfAbsent, (InlineConstTracker) compilerConfiguration.get(CommonConfigurationKeys.INLINE_CONST_TRACKER), (ExpectActualTracker) compilerConfiguration.get(CommonConfigurationKeys.EXPECT_ACTUAL_TRACKER), false);
                                if (vfsBasedProjectEnvironment instanceof VfsBasedProjectEnvironment) {
                                    vfsBasedProjectEnvironment2 = vfsBasedProjectEnvironment;
                                } else {
                                    vfsBasedProjectEnvironment2 = null;
                                }
                                if (vfsBasedProjectEnvironment2 != null) {
                                    listEmptyList3 = null;
                                } else {
                                    listEmptyList3 = null;
                                }
                                if (listEmptyList3 == null) {
                                    listEmptyList3 = CollectionsKt.emptyList();
                                }
                                Fir2IrActualizedResult fir2IrActualizedResultConvertToIrAndActualizeForJvm = ConvertToIrKt.convertToIrAndActualizeForJvm(firResultRunCompiler$firIncrementalCycle, jvmFir2IrExtensions, fir2IrConfiguration, listEmptyList3);
                                IrModuleFragment irModuleFragment = fir2IrActualizedResultConvertToIrAndActualizeForJvm.getIrModuleFragment();
                                Fir2IrComponents components = fir2IrActualizedResultConvertToIrAndActualizeForJvm.getComponents();
                                Fir2IrPluginContext pluginContext = fir2IrActualizedResultConvertToIrAndActualizeForJvm.getPluginContext();
                                IrActualizedResult irActualizedResult = fir2IrActualizedResultConvertToIrAndActualizeForJvm.getIrActualizedResult();
                                if (commonCompilerPerformanceManager != null) {
                                    commonCompilerPerformanceManager.notifyIRTranslationFinished();
                                    Unit unit5 = Unit.INSTANCE;
                                }
                                ModuleCompilerOutput moduleCompilerOutputGenerateCodeFromIr = CompilerPipelineKt.generateCodeFromIr(new ModuleCompilerIrBackendInput(targetId, compilerConfiguration, jvmFir2IrExtensions, irModuleFragment, components, pluginContext, irActualizedResult), moduleCompilerEnvironment, commonCompilerPerformanceManager);
                                if (commonCompilerPerformanceManager != null) {
                                    commonCompilerPerformanceManager.notifyIRGenerationFinished();
                                    Unit unit6 = Unit.INSTANCE;
                                }
                                if (commonCompilerPerformanceManager != null) {
                                    commonCompilerPerformanceManager.notifyGenerationFinished();
                                    Unit unit7 = Unit.INSTANCE;
                                }
                                FirDiagnosticsCompilerResultsReporterKt.reportToMessageCollector(pendingDiagnosticsCollectorWithSuppressCreatePendingReporter$default, messageCollector, z);
                                CompilerPipelineKt.writeOutputs(vfsBasedProjectEnvironment, compilerConfiguration, CollectionsKt.listOf(moduleCompilerOutputGenerateCodeFromIr.getGenerationState()), (FqName) objectRef.element);
                                groupingMessageCollector.flush();
                                Disposer.dispose(disposableNewDisposable);
                                return TuplesKt.to(exitCode, linkedHashSet);
                                groupingMessageCollector2 = groupingMessageCollector;
                                str2 = str;
                                compilerMessageSourceLocation = null;
                                groupingMessageCollector2.report(CompilerMessageSeverity.INFO, str2, compilerMessageSourceLocation);
                                Pair<ExitCode, Collection<File>> pair5 = TuplesKt.to(ExitCode.OK, linkedHashSet);
                                groupingMessageCollector2.flush();
                                Disposer.dispose(disposableNewDisposable);
                                return pair5;
                            }
                            try {
                                compilerConfiguration.putIfNotNull(CommonConfigurationKeys.LOOKUP_TRACKER, services2.get(LookupTracker.class));
                                compilerConfiguration.putIfNotNull(CommonConfigurationKeys.EXPECT_ACTUAL_TRACKER, services2.get(ExpectActualTracker.class));
                                compilerConfiguration.putIfNotNull(CommonConfigurationKeys.INLINE_CONST_TRACKER, services2.get(InlineConstTracker.class));
                                compilerConfiguration.putIfNotNull(JVMConfigurationKeys.INCREMENTAL_COMPILATION_COMPONENTS, services2.get(IncrementalCompilationComponents.class));
                                compilerConfiguration.putIfNotNull(ClassicFrontendSpecificJvmConfigurationKeys.JAVA_CLASSES_TRACKER, services2.get(JavaClassesTracker.class));
                                JvmArgumentsKt.setupJvmSpecificArguments(compilerConfiguration, args);
                                kotlinPathsComputeKotlinPaths = ArgumentsKt.computeKotlinPaths(groupingMessageCollector3, args);
                                if (groupingMessageCollector3.getHasErrors()) {
                                    Pair<ExitCode, Collection<File>> pair6 = TuplesKt.to(ExitCode.COMPILATION_ERROR, CollectionsKt.emptyList());
                                    groupingMessageCollector3.flush();
                                    Disposer.dispose(disposableNewDisposable);
                                    return pair6;
                                }
                                pluginClasspaths = args.getPluginClasspaths();
                                if (pluginClasspaths != null || (listEmptyList = ArraysKt.toList(pluginClasspaths)) == null) {
                                    listEmptyList = CollectionsKt.emptyList();
                                }
                                pluginOptions = args.getPluginOptions();
                                if (pluginOptions != null || (arrayList = ArraysKt.toMutableList(pluginOptions)) == null) {
                                    arrayList = new ArrayList();
                                }
                                pluginConfigurations = args.getPluginConfigurations();
                                if (pluginConfigurations != null || (listEmptyList2 = ArraysKt.toList(pluginConfigurations)) == null) {
                                    listEmptyList2 = CollectionsKt.emptyList();
                                }
                                exitCodeLoadPluginsSafe = PluginCliParser.loadPluginsSafe(listEmptyList, (Collection<String>) arrayList, listEmptyList2, compilerConfiguration);
                                if (exitCodeLoadPluginsSafe != ExitCode.OK) {
                                    Pair<ExitCode, Collection<File>> pair7 = TuplesKt.to(exitCodeLoadPluginsSafe, CollectionsKt.emptyList());
                                    groupingMessageCollector3.flush();
                                    Disposer.dispose(disposableNewDisposable);
                                    return pair7;
                                }
                                JvmArgumentsKt.configureJavaModulesContentRoots(compilerConfiguration, args);
                                JvmArgumentsKt.configureStandardLibs(compilerConfiguration, kotlinPathsComputeKotlinPaths, args);
                                JvmArgumentsKt.configureAdvancedJvmOptions(compilerConfiguration, args);
                                JvmArgumentsKt.configureKlibPaths(compilerConfiguration, args);
                                JvmContentRootsKt.configureJdkClasspathRoots(compilerConfiguration);
                                destination = args.getDestination();
                                if (destination == null) {
                                    destination = ".";
                                }
                                file = new File(destination);
                                path = file.getPath();
                                Intrinsics.checkNotNullExpressionValue(path, "getPath(...)");
                                groupingMessageCollector2 = groupingMessageCollector3;
                                try {
                                    try {
                                        if (StringsKt.endsWith$default(path, ".jar", false, 2, (Object) null)) {
                                            try {
                                                compilerConfiguration.put(JVMConfigurationKeys.OUTPUT_JAR, file);
                                            } catch (CompilationCanceledException unused) {
                                                disposableNewDisposable = disposableNewDisposable;
                                                str2 = "Compilation was canceled";
                                                groupingMessageCollector2 = groupingMessageCollector2;
                                            } catch (RuntimeException e2) {
                                                runtimeException = e2;
                                                disposableNewDisposable = disposableNewDisposable;
                                                str = "Compilation was canceled";
                                                linkedHashSet = linkedHashSet;
                                                groupingMessageCollector = groupingMessageCollector2;
                                                if (!(runtimeException.getCause() instanceof CompilationCanceledException)) {
                                                    throw runtimeException;
                                                }
                                                GroupingMessageCollector groupingMessageCollector4 = groupingMessageCollector;
                                                groupingMessageCollector4.report(CompilerMessageSeverity.INFO, str, null);
                                                Pair<ExitCode, Collection<File>> pair8 = TuplesKt.to(ExitCode.OK, linkedHashSet);
                                                groupingMessageCollector4.flush();
                                                Disposer.dispose(disposableNewDisposable);
                                                return pair8;
                                            } catch (Throwable th2) {
                                                th = th2;
                                                disposable = disposableNewDisposable;
                                                r3 = groupingMessageCollector2;
                                            }
                                        } else {
                                            try {
                                                compilerConfiguration.put(JVMConfigurationKeys.OUTPUT_DIRECTORY, file);
                                            } catch (CompilationCanceledException unused2) {
                                                disposableNewDisposable = disposableNewDisposable;
                                                str2 = str5;
                                                groupingMessageCollector2 = groupingMessageCollector2;
                                            }
                                        }
                                        CompilerConfigurationKey<List<Module>> compilerConfigurationKey2 = JVMConfigurationKeys.MODULES;
                                        String name3 = targetId2.getName();
                                        String path4 = file.getPath();
                                        Intrinsics.checkNotNullExpressionValue(path4, "getPath(...)");
                                        compilerConfiguration.addAll(compilerConfigurationKey2, CollectionsKt.listOf(new ModuleBuilder(name3, path4, targetId2.getType())));
                                        IncrementalFirJvmCompilerRunnerKt.configureBaseRoots(compilerConfiguration, args);
                                        IncrementalFirJvmCompilerRunnerKt.configureSourceRootsFromSources(compilerConfiguration, allSources, linkedHashSetEmptySet, args.getJavaPackagePrefix());
                                        Unit unit8 = Unit.INSTANCE;
                                        UtilKt.setIdeaIoUseFallback();
                                        vfsBasedProjectEnvironmentCreateProjectEnvironment = CompilerPipelineKt.createProjectEnvironment(compilerConfiguration, disposableNewDisposable, EnvironmentConfigFiles.JVM_CONFIG_FILES, messageCollector);
                                        linkedHashSet2 = new LinkedHashSet();
                                        linkedHashSet = new LinkedHashSet();
                                        linkedHashMap = new LinkedHashMap();
                                        List<KotlinSourceRoot> kotlinSourceRoots2 = ContentRootsKt.getKotlinSourceRoots(compilerConfiguration);
                                        Project project3 = vfsBasedProjectEnvironmentCreateProjectEnvironment.getProject();
                                        fileSystem = VirtualFileManager.getInstance().getFileSystem("file");
                                        HashSet hashSet2 = new HashSet();
                                        preprocessedFileCreator = new PreprocessedFileCreator(project3);
                                        it = kotlinSourceRoots2.iterator();
                                        boolean z3 = false;
                                        while (true) {
                                            disposable2 = disposableNewDisposable;
                                            if (it.hasNext()) {
                                                break;
                                            }
                                            try {
                                                try {
                                                    KotlinSourceRoot next2 = it.next();
                                                    path2 = next2.getPath();
                                                    boolean isCommon2 = next2.getIsCommon();
                                                    Iterator<KotlinSourceRoot> it4 = it;
                                                    String hmppModuleName2 = next2.getHmppModuleName();
                                                    str3 = str5;
                                                    try {
                                                        try {
                                                            file2 = new File(path2);
                                                            virtualFileFindFileByPath = fileSystem.findFileByPath(FilesKt.normalize(file2).getPath());
                                                            if (virtualFileFindFileByPath == null) {
                                                                str4 = "Source file or directory not found: " + path2;
                                                                file3 = (File) compilerConfiguration.get(JVMConfigurationKeys.MODULE_XML_FILE);
                                                                if (file3 == null && Logger.isInitialized()) {
                                                                    try {
                                                                        Logger.getInstance((Class<?>) KotlinCoreEnvironment.class).warn(str4 + "\n\nbuild file path: " + file3 + "\ncontent:\n" + FilesKt.readText$default(file3, null, 1, null));
                                                                    } catch (CompilationCanceledException unused3) {
                                                                        compilerMessageSourceLocation = null;
                                                                        linkedHashSet = linkedHashSet;
                                                                        groupingMessageCollector2 = groupingMessageCollector2;
                                                                        str2 = str3;
                                                                        disposableNewDisposable = disposable2;
                                                                        groupingMessageCollector2.report(CompilerMessageSeverity.INFO, str2, compilerMessageSourceLocation);
                                                                        Pair<ExitCode, Collection<File>> pair9 = TuplesKt.to(ExitCode.OK, linkedHashSet);
                                                                        groupingMessageCollector2.flush();
                                                                        Disposer.dispose(disposableNewDisposable);
                                                                        return pair9;
                                                                    }
                                                                }
                                                                try {
                                                                    CoreEnvironmentUtilsKt.report(compilerConfiguration, CompilerMessageSeverity.ERROR, str4, null);
                                                                } catch (CompilationCanceledException unused4) {
                                                                    compilerMessageSourceLocation = null;
                                                                    linkedHashSet = linkedHashSet;
                                                                    groupingMessageCollector2 = groupingMessageCollector2;
                                                                    str2 = str3;
                                                                    disposableNewDisposable = disposable2;
                                                                    groupingMessageCollector2.report(CompilerMessageSeverity.INFO, str2, compilerMessageSourceLocation);
                                                                    Pair<ExitCode, Collection<File>> pair10 = TuplesKt.to(ExitCode.OK, linkedHashSet);
                                                                    groupingMessageCollector2.flush();
                                                                    Disposer.dispose(disposableNewDisposable);
                                                                    return pair10;
                                                                }
                                                            } else {
                                                                targetId2 = targetId2;
                                                                vfsBasedProjectEnvironmentCreateProjectEnvironment = vfsBasedProjectEnvironmentCreateProjectEnvironment;
                                                                if (virtualFileFindFileByPath.isDirectory() || Intrinsics.areEqual(virtualFileFindFileByPath.getExtension(), KotlinFileType.EXTENSION)) {
                                                                    while (r7.hasNext()) {
                                                                        if (file4.isFile()) {
                                                                            File absoluteFile3 = file4.getAbsoluteFile();
                                                                            Intrinsics.checkNotNullExpressionValue(absoluteFile3, "getAbsoluteFile(...)");
                                                                            virtualFileFindFileByPath2 = fileSystem.findFileByPath(FilesKt.normalize(absoluteFile3).getPath());
                                                                            if (virtualFileFindFileByPath2 != null) {
                                                                                virtualFileCreate = preprocessedFileCreator.create(virtualFileFindFileByPath2);
                                                                            } else {
                                                                                virtualFileCreate = null;
                                                                            }
                                                                            if (virtualFileCreate == null && hashSet2.add(virtualFileCreate)) {
                                                                                if (!z3) {
                                                                                    CoreEnvironmentUtilsKt.registerPluginsSuppliedExtensionsIfNeeded(virtualFileCreate, project3);
                                                                                    z3 = true;
                                                                                }
                                                                                KtVirtualFileSourceFile ktVirtualFileSourceFile = new KtVirtualFileSourceFile(virtualFileCreate);
                                                                                if (isCommon2) {
                                                                                    linkedHashSet.add(ktVirtualFileSourceFile);
                                                                                } else {
                                                                                    linkedHashSet2.add(ktVirtualFileSourceFile);
                                                                                }
                                                                                if (hmppModuleName2 != null) {
                                                                                    Object obj2 = linkedHashMap.get(hmppModuleName2);
                                                                                    if (obj2 == null) {
                                                                                        obj2 = (Set) new LinkedHashSet();
                                                                                        linkedHashMap.put(hmppModuleName2, obj2);
                                                                                    }
                                                                                    ((Set) obj2).add(ktVirtualFileSourceFile);
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                } else {
                                                                    if (!z3) {
                                                                        CoreEnvironmentUtilsKt.registerPluginsSuppliedExtensionsIfNeeded(virtualFileFindFileByPath, project3);
                                                                        z3 = true;
                                                                    }
                                                                    if (Intrinsics.areEqual(virtualFileFindFileByPath.getFileType(), KotlinFileType.INSTANCE)) {
                                                                        while (r7.hasNext()) {
                                                                            if (file4.isFile()) {
                                                                                File absoluteFile4 = file4.getAbsoluteFile();
                                                                                Intrinsics.checkNotNullExpressionValue(absoluteFile4, "getAbsoluteFile(...)");
                                                                                virtualFileFindFileByPath2 = fileSystem.findFileByPath(FilesKt.normalize(absoluteFile4).getPath());
                                                                                if (virtualFileFindFileByPath2 != null) {
                                                                                    virtualFileCreate = preprocessedFileCreator.create(virtualFileFindFileByPath2);
                                                                                } else {
                                                                                    virtualFileCreate = null;
                                                                                }
                                                                                if (virtualFileCreate == null) {
                                                                                }
                                                                            }
                                                                        }
                                                                    } else {
                                                                        CoreEnvironmentUtilsKt.report(compilerConfiguration, CompilerMessageSeverity.ERROR, "Source entry is not a Kotlin file: " + path2, null);
                                                                    }
                                                                }
                                                            }
                                                            it = it4;
                                                            vfsBasedProjectEnvironmentCreateProjectEnvironment = vfsBasedProjectEnvironmentCreateProjectEnvironment;
                                                            str5 = str3;
                                                            disposableNewDisposable = disposable2;
                                                            targetId2 = targetId2;
                                                        } catch (RuntimeException e3) {
                                                            e = e3;
                                                            runtimeException = e;
                                                            linkedHashSet = linkedHashSet;
                                                            groupingMessageCollector = groupingMessageCollector2;
                                                            str = str3;
                                                            disposableNewDisposable = disposable2;
                                                            if (!(runtimeException.getCause() instanceof CompilationCanceledException)) {
                                                                throw runtimeException;
                                                            }
                                                            GroupingMessageCollector groupingMessageCollector5 = groupingMessageCollector;
                                                            groupingMessageCollector5.report(CompilerMessageSeverity.INFO, str, null);
                                                            Pair<ExitCode, Collection<File>> pair11 = TuplesKt.to(ExitCode.OK, linkedHashSet);
                                                            groupingMessageCollector5.flush();
                                                            Disposer.dispose(disposableNewDisposable);
                                                            return pair11;
                                                        }
                                                    } catch (CompilationCanceledException unused5) {
                                                        str2 = str3;
                                                        disposableNewDisposable = disposable2;
                                                        compilerMessageSourceLocation = null;
                                                        groupingMessageCollector2.report(CompilerMessageSeverity.INFO, str2, compilerMessageSourceLocation);
                                                        Pair<ExitCode, Collection<File>> pair12 = TuplesKt.to(ExitCode.OK, linkedHashSet);
                                                        groupingMessageCollector2.flush();
                                                        Disposer.dispose(disposableNewDisposable);
                                                        return pair12;
                                                    }
                                                } catch (Throwable th3) {
                                                    th = th3;
                                                    r3 = groupingMessageCollector2;
                                                    disposable = disposable2;
                                                }
                                            } catch (CompilationCanceledException unused6) {
                                                str2 = str5;
                                            } catch (RuntimeException e4) {
                                                e = e4;
                                                str3 = str5;
                                                runtimeException = e;
                                                linkedHashSet = linkedHashSet;
                                                groupingMessageCollector = groupingMessageCollector2;
                                                str = str3;
                                                disposableNewDisposable = disposable2;
                                                if (!(runtimeException.getCause() instanceof CompilationCanceledException)) {
                                                    throw runtimeException;
                                                }
                                                GroupingMessageCollector groupingMessageCollector6 = groupingMessageCollector;
                                                groupingMessageCollector6.report(CompilerMessageSeverity.INFO, str, null);
                                                Pair<ExitCode, Collection<File>> pair13 = TuplesKt.to(ExitCode.OK, linkedHashSet);
                                                groupingMessageCollector6.flush();
                                                Disposer.dispose(disposableNewDisposable);
                                                return pair13;
                                            }
                                            groupingMessageCollector2.report(CompilerMessageSeverity.INFO, str2, compilerMessageSourceLocation);
                                            Pair<ExitCode, Collection<File>> pair14 = TuplesKt.to(ExitCode.OK, linkedHashSet);
                                            groupingMessageCollector2.flush();
                                            Disposer.dispose(disposableNewDisposable);
                                            return pair14;
                                        }
                                        targetId = targetId2;
                                        vfsBasedProjectEnvironment = vfsBasedProjectEnvironmentCreateProjectEnvironment;
                                        str3 = str5;
                                        try {
                                            try {
                                                try {
                                                    pendingDiagnosticsCollectorWithSuppressCreatePendingReporter$default = DiagnosticReporterFactory.createPendingReporter$default(DiagnosticReporterFactory.INSTANCE, null, 1, null);
                                                    commonCompilerPerformanceManager = (CommonCompilerPerformanceManager) compilerConfiguration.get(CLIConfigurationKeys.PERF_MANAGER);
                                                    moduleCompilerEnvironment = new ModuleCompilerEnvironment(vfsBasedProjectEnvironment, pendingDiagnosticsCollectorWithSuppressCreatePendingReporter$default);
                                                    if (commonCompilerPerformanceManager != null) {
                                                        try {
                                                            commonCompilerPerformanceManager.notifyCompilerInitialized(0, 0, targetId.getName() + '-' + targetId.getType());
                                                            Unit unit9 = Unit.INSTANCE;
                                                        } catch (CompilationCanceledException unused7) {
                                                            compilerMessageSourceLocation = null;
                                                            linkedHashSet = linkedHashSet;
                                                            groupingMessageCollector2 = groupingMessageCollector2;
                                                            str2 = str3;
                                                            disposableNewDisposable = disposable2;
                                                            groupingMessageCollector2.report(CompilerMessageSeverity.INFO, str2, compilerMessageSourceLocation);
                                                            Pair<ExitCode, Collection<File>> pair15 = TuplesKt.to(ExitCode.OK, linkedHashSet);
                                                            groupingMessageCollector2.flush();
                                                            Disposer.dispose(disposableNewDisposable);
                                                            return pair15;
                                                        }
                                                    }
                                                    objectRef = new Ref.ObjectRef();
                                                    z = compilerConfiguration.getBoolean(CLIConfigurationKeys.RENDER_DIAGNOSTIC_INTERNAL_NAME);
                                                    disposableNewDisposable = disposable2;
                                                    groupingMessageCollector = groupingMessageCollector2;
                                                    str = str3;
                                                    try {
                                                        firResultRunCompiler$firIncrementalCycle = runCompiler$firIncrementalCycle(linkedHashMap, linkedHashSet, linkedHashSet2, targetId, compilerConfiguration, commonCompilerPerformanceManager, moduleCompilerEnvironment, new Ref.ObjectRef(), pendingDiagnosticsCollectorWithSuppressCreatePendingReporter$default, objectRef, linkedHashSet3, messageCollector, z, caches, linkedHashSet, this, isIncremental, vfsBasedProjectEnvironment);
                                                        try {
                                                            if (firResultRunCompiler$firIncrementalCycle == null) {
                                                                Pair<ExitCode, Collection<File>> pair16 = TuplesKt.to(ExitCode.COMPILATION_ERROR, linkedHashSet);
                                                                groupingMessageCollector.flush();
                                                                Disposer.dispose(disposableNewDisposable);
                                                                return pair16;
                                                            }
                                                            if (commonCompilerPerformanceManager != null) {
                                                                commonCompilerPerformanceManager.notifyGenerationStarted();
                                                                Unit unit10 = Unit.INSTANCE;
                                                            }
                                                            if (commonCompilerPerformanceManager != null) {
                                                                commonCompilerPerformanceManager.notifyIRTranslationStarted();
                                                                Unit unit11 = Unit.INSTANCE;
                                                            }
                                                            JvmFir2IrExtensions jvmFir2IrExtensions2 = new JvmFir2IrExtensions(compilerConfiguration, new JvmIrDeserializerImpl(), JvmIrMangler.INSTANCE);
                                                            LanguageVersionSettings languageVersionSettings2 = CommonConfigurationKeysKt.getLanguageVersionSettings(compilerConfiguration);
                                                            BaseDiagnosticsCollector diagnosticsReporter2 = moduleCompilerEnvironment.getDiagnosticsReporter();
                                                            Object objPutIfAbsent2 = compilerConfiguration.putIfAbsent(CommonConfigurationKeys.EVALUATED_CONST_TRACKER, EvaluatedConstTracker.INSTANCE.create());
                                                            Intrinsics.checkNotNullExpressionValue(objPutIfAbsent2, "putIfAbsent(...)");
                                                            Fir2IrConfiguration fir2IrConfiguration2 = new Fir2IrConfiguration(languageVersionSettings2, diagnosticsReporter2, false, (EvaluatedConstTracker) objPutIfAbsent2, (InlineConstTracker) compilerConfiguration.get(CommonConfigurationKeys.INLINE_CONST_TRACKER), (ExpectActualTracker) compilerConfiguration.get(CommonConfigurationKeys.EXPECT_ACTUAL_TRACKER), false);
                                                            if (vfsBasedProjectEnvironment instanceof VfsBasedProjectEnvironment) {
                                                                vfsBasedProjectEnvironment2 = vfsBasedProjectEnvironment;
                                                            } else {
                                                                vfsBasedProjectEnvironment2 = null;
                                                            }
                                                            if (vfsBasedProjectEnvironment2 != null || (project = vfsBasedProjectEnvironment2.getProject()) == null) {
                                                                listEmptyList3 = null;
                                                            } else {
                                                                listEmptyList3 = IrGenerationExtension.INSTANCE.getInstances(project);
                                                            }
                                                            if (listEmptyList3 == null) {
                                                                listEmptyList3 = CollectionsKt.emptyList();
                                                            }
                                                            Fir2IrActualizedResult fir2IrActualizedResultConvertToIrAndActualizeForJvm2 = ConvertToIrKt.convertToIrAndActualizeForJvm(firResultRunCompiler$firIncrementalCycle, jvmFir2IrExtensions2, fir2IrConfiguration2, listEmptyList3);
                                                            IrModuleFragment irModuleFragment2 = fir2IrActualizedResultConvertToIrAndActualizeForJvm2.getIrModuleFragment();
                                                            Fir2IrComponents components2 = fir2IrActualizedResultConvertToIrAndActualizeForJvm2.getComponents();
                                                            Fir2IrPluginContext pluginContext2 = fir2IrActualizedResultConvertToIrAndActualizeForJvm2.getPluginContext();
                                                            IrActualizedResult irActualizedResult2 = fir2IrActualizedResultConvertToIrAndActualizeForJvm2.getIrActualizedResult();
                                                            if (commonCompilerPerformanceManager != null) {
                                                                commonCompilerPerformanceManager.notifyIRTranslationFinished();
                                                                Unit unit12 = Unit.INSTANCE;
                                                            }
                                                            ModuleCompilerOutput moduleCompilerOutputGenerateCodeFromIr2 = CompilerPipelineKt.generateCodeFromIr(new ModuleCompilerIrBackendInput(targetId, compilerConfiguration, jvmFir2IrExtensions2, irModuleFragment2, components2, pluginContext2, irActualizedResult2), moduleCompilerEnvironment, commonCompilerPerformanceManager);
                                                            if (commonCompilerPerformanceManager != null) {
                                                                commonCompilerPerformanceManager.notifyIRGenerationFinished();
                                                                Unit unit13 = Unit.INSTANCE;
                                                            }
                                                            if (commonCompilerPerformanceManager != null) {
                                                                commonCompilerPerformanceManager.notifyGenerationFinished();
                                                                Unit unit14 = Unit.INSTANCE;
                                                            }
                                                            FirDiagnosticsCompilerResultsReporterKt.reportToMessageCollector(pendingDiagnosticsCollectorWithSuppressCreatePendingReporter$default, messageCollector, z);
                                                            CompilerPipelineKt.writeOutputs(vfsBasedProjectEnvironment, compilerConfiguration, CollectionsKt.listOf(moduleCompilerOutputGenerateCodeFromIr2.getGenerationState()), (FqName) objectRef.element);
                                                            groupingMessageCollector.flush();
                                                            Disposer.dispose(disposableNewDisposable);
                                                            return TuplesKt.to(exitCode, linkedHashSet);
                                                        } catch (CompilationCanceledException unused8) {
                                                        } catch (RuntimeException e5) {
                                                            e = e5;
                                                            runtimeException = e;
                                                            if (!(runtimeException.getCause() instanceof CompilationCanceledException)) {
                                                                throw runtimeException;
                                                            }
                                                            GroupingMessageCollector groupingMessageCollector7 = groupingMessageCollector;
                                                            groupingMessageCollector7.report(CompilerMessageSeverity.INFO, str, null);
                                                            Pair<ExitCode, Collection<File>> pair17 = TuplesKt.to(ExitCode.OK, linkedHashSet);
                                                            groupingMessageCollector7.flush();
                                                            Disposer.dispose(disposableNewDisposable);
                                                            return pair17;
                                                        }
                                                    } catch (CompilationCanceledException unused9) {
                                                        linkedHashSet = linkedHashSet;
                                                    } catch (RuntimeException e6) {
                                                        e = e6;
                                                        linkedHashSet = linkedHashSet;
                                                    }
                                                    groupingMessageCollector2 = groupingMessageCollector;
                                                    str2 = str;
                                                } catch (CompilationCanceledException unused10) {
                                                    linkedHashSet = linkedHashSet;
                                                    disposableNewDisposable = disposable2;
                                                    compilerMessageSourceLocation = null;
                                                    groupingMessageCollector2 = groupingMessageCollector2;
                                                    str2 = str3;
                                                }
                                            } catch (CompilationCanceledException unused11) {
                                                linkedHashSet = linkedHashSet;
                                                disposableNewDisposable = disposable2;
                                                groupingMessageCollector2 = groupingMessageCollector2;
                                                str2 = str3;
                                            }
                                        } catch (RuntimeException e7) {
                                            e = e7;
                                            linkedHashSet = linkedHashSet;
                                            groupingMessageCollector = groupingMessageCollector2;
                                            str = str3;
                                            disposableNewDisposable = disposable2;
                                        } catch (Throwable th4) {
                                            th = th4;
                                            obj = groupingMessageCollector2;
                                            disposable = disposable2;
                                            services2 = obj;
                                            th = th;
                                            r3 = services2;
                                            r3.flush();
                                            Disposer.dispose(disposable);
                                            throw th;
                                        }
                                    } catch (CompilationCanceledException unused12) {
                                        disposableNewDisposable = disposableNewDisposable;
                                        linkedHashSet = linkedHashSet;
                                        compilerMessageSourceLocation = null;
                                        str2 = "Compilation was canceled";
                                        groupingMessageCollector2 = groupingMessageCollector2;
                                    }
                                } catch (RuntimeException e8) {
                                    e = e8;
                                    disposableNewDisposable = disposableNewDisposable;
                                    str = str5;
                                    linkedHashSet = linkedHashSet;
                                    groupingMessageCollector = groupingMessageCollector2;
                                } catch (Throwable th5) {
                                    th = th5;
                                    disposable = disposableNewDisposable;
                                    obj = groupingMessageCollector2;
                                }
                            } catch (CompilationCanceledException unused13) {
                                disposableNewDisposable = disposableNewDisposable;
                                groupingMessageCollector2 = groupingMessageCollector3;
                                str2 = "Compilation was canceled";
                                linkedHashSet = linkedHashSet;
                            } catch (RuntimeException e9) {
                                runtimeException = e9;
                                disposableNewDisposable = disposableNewDisposable;
                                groupingMessageCollector = groupingMessageCollector3;
                                str = "Compilation was canceled";
                                linkedHashSet = linkedHashSet;
                                if (!(runtimeException.getCause() instanceof CompilationCanceledException)) {
                                    throw runtimeException;
                                }
                                GroupingMessageCollector groupingMessageCollector8 = groupingMessageCollector;
                                groupingMessageCollector8.report(CompilerMessageSeverity.INFO, str, null);
                                Pair<ExitCode, Collection<File>> pair18 = TuplesKt.to(ExitCode.OK, linkedHashSet);
                                groupingMessageCollector8.flush();
                                Disposer.dispose(disposableNewDisposable);
                                return pair18;
                            } catch (Throwable th6) {
                                th = th6;
                                disposable = disposableNewDisposable;
                                r3 = groupingMessageCollector3;
                            }
                            compilerMessageSourceLocation = null;
                            groupingMessageCollector2.report(CompilerMessageSeverity.INFO, str2, compilerMessageSourceLocation);
                            Pair<ExitCode, Collection<File>> pair19 = TuplesKt.to(ExitCode.OK, linkedHashSet);
                            groupingMessageCollector2.flush();
                            Disposer.dispose(disposableNewDisposable);
                            return pair19;
                        } catch (CompilationCanceledException unused14) {
                            linkedHashSet = linkedHashSet;
                            groupingMessageCollector2 = groupingMessageCollector3;
                            str2 = "Compilation was canceled";
                        } catch (RuntimeException e10) {
                            e = e10;
                            disposableNewDisposable = disposableNewDisposable;
                            groupingMessageCollector = groupingMessageCollector3;
                            str = "Compilation was canceled";
                            linkedHashSet = linkedHashSet;
                        }
                    } catch (Throwable th7) {
                        th = th7;
                        disposable = disposableNewDisposable;
                        obj = groupingMessageCollector3;
                    }
                } catch (Throwable th8) {
                    th = th8;
                    obj = args;
                }
            } catch (CompilationCanceledException unused15) {
                linkedHashSet = linkedHashSet5;
            } catch (RuntimeException e11) {
                e = e11;
                disposableNewDisposable = disposableNewDisposable;
                linkedHashSet = linkedHashSet5;
                groupingMessageCollector = groupingMessageCollector3;
                str = "Compilation was canceled";
            }
        } catch (Throwable th9) {
            th = th9;
        }
        r3.flush();
        Disposer.dispose(disposable);
        throw th;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v28, types: [T, org.jetbrains.kotlin.name.FqName] */
    private static final FirResult runCompiler$firIncrementalCycle(Map<String, Set<KtSourceFile>> map, LinkedHashSet<KtSourceFile> linkedHashSet, LinkedHashSet<KtSourceFile> linkedHashSet2, TargetId targetId, CompilerConfiguration compilerConfiguration, CommonCompilerPerformanceManager commonCompilerPerformanceManager, ModuleCompilerEnvironment moduleCompilerEnvironment, Ref.ObjectRef<AbstractProjectFileSearchScope> objectRef, PendingDiagnosticsCollectorWithSuppress pendingDiagnosticsCollectorWithSuppress, Ref.ObjectRef<FqName> objectRef2, LinkedHashSet<KtSourceFile> linkedHashSet3, MessageCollector messageCollector, boolean z, IncrementalJvmCachesManager incrementalJvmCachesManager, LinkedHashSet<File> linkedHashSet4, IncrementalFirJvmCompilerRunner incrementalFirJvmCompilerRunner, boolean z2, VfsBasedProjectEnvironment vfsBasedProjectEnvironment) {
        T tPlus;
        while (true) {
            LinkedHashMap linkedHashMap = new LinkedHashMap(MapsKt.mapCapacity(map.size()));
            Iterator<T> it = map.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                Object key = entry.getKey();
                Set set = (Set) entry.getValue();
                LinkedHashSet linkedHashSet5 = new LinkedHashSet();
                for (Object obj : set) {
                    KtSourceFile ktSourceFile = (KtSourceFile) obj;
                    LinkedHashSet<KtSourceFile> linkedHashSet6 = linkedHashSet3;
                    if (!(linkedHashSet6 instanceof Collection) || !linkedHashSet6.isEmpty()) {
                        Iterator<T> it2 = linkedHashSet6.iterator();
                        while (it2.hasNext()) {
                            if (Intrinsics.areEqual(((KtSourceFile) it2.next()).getPath(), ktSourceFile.getPath())) {
                                linkedHashSet5.add(obj);
                                break;
                            }
                        }
                    }
                }
                linkedHashMap.put(key, linkedHashSet5);
            }
            ArrayList arrayList = new ArrayList();
            for (Object obj2 : linkedHashSet) {
                KtSourceFile ktSourceFile2 = (KtSourceFile) obj2;
                LinkedHashSet<KtSourceFile> linkedHashSet7 = linkedHashSet3;
                if (!(linkedHashSet7 instanceof Collection) || !linkedHashSet7.isEmpty()) {
                    Iterator<T> it3 = linkedHashSet7.iterator();
                    while (it3.hasNext()) {
                        if (Intrinsics.areEqual(((KtSourceFile) it3.next()).getPath(), ktSourceFile2.getPath())) {
                            arrayList.add(obj2);
                            break;
                        }
                    }
                }
            }
            ArrayList arrayList2 = arrayList;
            ArrayList arrayList3 = new ArrayList();
            for (Object obj3 : linkedHashSet2) {
                KtSourceFile ktSourceFile3 = (KtSourceFile) obj3;
                LinkedHashSet<KtSourceFile> linkedHashSet8 = linkedHashSet3;
                if (!(linkedHashSet8 instanceof Collection) || !linkedHashSet8.isEmpty()) {
                    Iterator<T> it4 = linkedHashSet8.iterator();
                    while (it4.hasNext()) {
                        if (Intrinsics.areEqual(((KtSourceFile) it4.next()).getPath(), ktSourceFile3.getPath())) {
                            arrayList3.add(obj3);
                            break;
                        }
                    }
                }
            }
            ModuleCompilerInput moduleCompilerInput = new ModuleCompilerInput(targetId, new GroupedKtSources(arrayList3, arrayList2, linkedHashMap), CommonPlatforms.INSTANCE.getDefaultCommonPlatform(), JvmPlatforms.INSTANCE.getUnspecifiedJvmPlatform(), compilerConfiguration, null, 32, null);
            if (commonCompilerPerformanceManager != null) {
                commonCompilerPerformanceManager.notifyAnalysisStarted();
            }
            PendingDiagnosticsCollectorWithSuppress pendingDiagnosticsCollectorWithSuppress2 = pendingDiagnosticsCollectorWithSuppress;
            FirResult firResultCompileModuleToAnalyzedFir = CompilerPipelineKt.compileModuleToAnalyzedFir(moduleCompilerInput, moduleCompilerEnvironment, CollectionsKt.emptyList(), objectRef.element, pendingDiagnosticsCollectorWithSuppress2, commonCompilerPerformanceManager);
            if (commonCompilerPerformanceManager != null) {
                commonCompilerPerformanceManager.notifyAnalysisFinished();
            }
            if (objectRef2.element == null && compilerConfiguration.get(JVMConfigurationKeys.OUTPUT_JAR) != null) {
                objectRef2.element = FirKotlinToJvmBytecodeCompilerKt.findMainClass(((ModuleCompilerAnalyzedOutput) CollectionsKt.last((List) firResultCompileModuleToAnalyzedFir.getOutputs())).getFir());
            }
            Iterator<T> it5 = linkedHashSet3.iterator();
            while (it5.hasNext()) {
                String path = ((KtSourceFile) it5.next()).getPath();
                Intrinsics.checkNotNull(path);
                linkedHashSet4.add(new File(path));
            }
            if (pendingDiagnosticsCollectorWithSuppress.getHasErrors()) {
                FirDiagnosticsCompilerResultsReporterKt.reportToMessageCollector(pendingDiagnosticsCollectorWithSuppress2, messageCollector, z);
                return null;
            }
            LinkedHashSet<File> linkedHashSetCollectNewDirtySources = IncrementalFirCacheUtilsKt.collectNewDirtySources(firResultCompileModuleToAnalyzedFir, targetId, compilerConfiguration, incrementalJvmCachesManager, linkedHashSet4, incrementalFirJvmCompilerRunner.getReporter());
            if (!z2 || linkedHashSetCollectNewDirtySources.isEmpty()) {
                return firResultCompileModuleToAnalyzedFir;
            }
            incrementalJvmCachesManager.getPlatformCache().markDirty((Collection<? extends File>) linkedHashSetCollectNewDirtySources);
            LinkedHashSet<File> linkedHashSet9 = linkedHashSetCollectNewDirtySources;
            AbstractProjectFileSearchScope searchScopeByIoFiles$default = AbstractProjectEnvironment.getSearchScopeByIoFiles$default(vfsBasedProjectEnvironment, incrementalJvmCachesManager.getInputsCache().getOutputForSourceFiles(linkedHashSet9), false, 2, null);
            AbstractProjectFileSearchScope abstractProjectFileSearchScope = objectRef.element;
            if (searchScopeByIoFiles$default.isEmpty()) {
                tPlus = searchScopeByIoFiles$default;
                tPlus = abstractProjectFileSearchScope;
            } else if (abstractProjectFileSearchScope != null) {
                tPlus = searchScopeByIoFiles$default;
                if (!abstractProjectFileSearchScope.isEmpty()) {
                    tPlus = abstractProjectFileSearchScope.plus(searchScopeByIoFiles$default);
                }
            }
            objectRef.element = tPlus;
            incrementalJvmCachesManager.getInputsCache().removeOutputForSourceFiles(linkedHashSet9);
            Iterator<? extends File> it6 = linkedHashSet9.iterator();
            while (it6.hasNext()) {
                linkedHashSet3.add(new KtIoFileSourceFile(it6.next()));
            }
            vfsBasedProjectEnvironment.getLocalFileSystem().refresh(false);
        }
    }
}
