package org.jetbrains.kotlin.incremental;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.build.GeneratedFile;
import org.jetbrains.kotlin.build.GeneratedJvmClass;
import org.jetbrains.kotlin.build.SourcesUtilsKt;
import org.jetbrains.kotlin.build.report.BuildReporter;
import org.jetbrains.kotlin.build.report.ICReporterKt;
import org.jetbrains.kotlin.build.report.metrics.BuildAttribute;
import org.jetbrains.kotlin.build.report.metrics.GradleBuildPerformanceMetric;
import org.jetbrains.kotlin.build.report.metrics.GradleBuildTime;
import org.jetbrains.kotlin.cli.common.ExitCode;
import org.jetbrains.kotlin.cli.common.arguments.CommonCompilerArguments;
import org.jetbrains.kotlin.cli.common.arguments.K2JVMCompilerArguments;
import org.jetbrains.kotlin.cli.common.messages.MessageCollector;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.cli.jvm.K2JVMCompiler;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaFile;
import org.jetbrains.kotlin.config.CommonConfigurationKeysKt;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.config.Services;
import org.jetbrains.kotlin.incremental.classpathDiff.AccessibleClassSnapshot;
import org.jetbrains.kotlin.incremental.classpathDiff.ClasspathChangesComputer;
import org.jetbrains.kotlin.incremental.classpathDiff.ClasspathSnapshotBuildReporter;
import org.jetbrains.kotlin.incremental.classpathDiff.ClasspathSnapshotShrinkerKt;
import org.jetbrains.kotlin.incremental.classpathDiff.ProgramSymbolSet;
import org.jetbrains.kotlin.incremental.components.ExpectActualTracker;
import org.jetbrains.kotlin.incremental.components.LookupTracker;
import org.jetbrains.kotlin.incremental.multiproject.ModulesApiHistory;
import org.jetbrains.kotlin.incremental.storage.LookupSymbolKey;
import org.jetbrains.kotlin.incremental.util.BufferingMessageCollector;
import org.jetbrains.kotlin.incremental.util.Either;
import org.jetbrains.kotlin.load.java.JavaClassesTracker;
import org.jetbrains.kotlin.load.kotlin.header.KotlinClassHeader;
import org.jetbrains.kotlin.load.kotlin.incremental.components.IncrementalCompilationComponents;
import org.jetbrains.kotlin.modules.TargetId;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: IncrementalJvmCompilerRunner.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u009b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\b\t\n\u0002\u0010\u001c\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000*\u0001,\b\u0016\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\u0081\u0001\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u0007\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\b\u0010\f\u001a\u0004\u0018\u00010\u0005\u0012\u000e\u0010\r\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u000e\u0012\u0006\u0010\u000f\u001a\u00020\u0010\u0012\u000e\b\u0002\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012\u0012\u0006\u0010\u0014\u001a\u00020\u0015\u0012\b\b\u0002\u0010\u0016\u001a\u00020\u000b\u0012\b\b\u0002\u0010\u0017\u001a\u00020\u000b\u0012\b\b\u0002\u0010\u0018\u001a\u00020\u000b¢\u0006\u0002\u0010\u0019J,\u00104\u001a\b\u0012\u0004\u0012\u00020\u0005052\u0006\u00106\u001a\u00020\u00032\f\u00107\u001a\b\u0012\u0004\u0012\u0002080\u00122\u0006\u00109\u001a\u00020:H\u0014J\u000e\u0010;\u001a\b\u0012\u0004\u0012\u00020<05H\u0014J<\u0010=\u001a\u00020>2\u0006\u00106\u001a\u00020\u00032\u0006\u0010?\u001a\u00020@2\u0006\u0010A\u001a\u00020\u00022\u0006\u0010)\u001a\u00020B2\u0012\u0010C\u001a\u000e\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020E0DH\u0014JD\u0010F\u001a\u00020>2\u0006\u00106\u001a\u00020\u00032\u0006\u0010?\u001a\u00020@2\u0006\u0010A\u001a\u00020\u00022\u0006\u0010)\u001a\u00020B2\u0012\u0010G\u001a\u000e\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020E0D2\u0006\u0010\u0016\u001a\u00020\u000bH\u0002J\u0018\u0010H\u001a\u00020\u00032\u0006\u0010I\u001a\u00020J2\u0006\u0010A\u001a\u00020\u0002H\u0014J\u0010\u0010K\u001a\u00020\u00052\u0006\u0010A\u001a\u00020\u0002H\u0014J>\u0010L\u001a\u00020M2\u0006\u0010A\u001a\u00020\u00022\u0006\u0010N\u001a\u00020O2\u0006\u0010P\u001a\u00020Q2\u0006\u00106\u001a\u00020\u00032\f\u0010R\u001a\b\u0012\u0004\u0012\u00020\u00050S2\u0006\u0010T\u001a\u00020\u000bH\u0014J \u0010U\u001a\u00020V2\u0006\u0010W\u001a\u00020>2\u0006\u0010X\u001a\u00020Y2\u0006\u00106\u001a\u00020\u0003H\u0014J\u0018\u0010Z\u001a\u00020V2\u0006\u0010W\u001a\u00020>2\u0006\u0010A\u001a\u00020\u0002H\u0014J\u001a\u0010[\u001a\u0004\u0018\u00010\\2\u0006\u0010?\u001a\u00020@2\u0006\u00106\u001a\u00020\u0003H\u0002J\u0018\u0010]\u001a\u00020V2\u0006\u0010^\u001a\u00020_2\u0006\u0010`\u001a\u00020\u001cH\u0002J\u0016\u0010a\u001a\b\u0012\u0004\u0012\u00020<0\u000e2\u0006\u0010?\u001a\u00020@H\u0002J^\u0010b\u001a\u0014\u0012\u0004\u0012\u00020Y\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u000e0c2\f\u0010d\u001a\b\u0012\u0004\u0012\u00020\u00050\u00122\u0006\u0010A\u001a\u00020\u00022\u0006\u00106\u001a\u00020\u00032\u0006\u00109\u001a\u00020:2\u0006\u0010)\u001a\u00020B2\f\u0010e\u001a\b\u0012\u0004\u0012\u00020\u00050\u00122\u0006\u0010T\u001a\u00020\u000bH\u0014J\u0010\u0010f\u001a\u00020\u000b2\u0006\u00106\u001a\u00020\u0003H\u0016J0\u0010g\u001a\u000e\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020E0D2\u0006\u0010A\u001a\u00020\u00022\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u0007H\u0014J.\u0010h\u001a\u00020V2\u0006\u00109\u001a\u00020:2\u0006\u00106\u001a\u00020\u00032\f\u00107\u001a\b\u0012\u0004\u0012\u0002080\u00122\u0006\u0010i\u001a\u00020jH\u0014J\f\u0010k\u001a\u00020l*\u00020lH\u0002J\u0012\u0010m\u001a\b\u0012\u0004\u0012\u00020n0\u0012*\u00020oH\u0002J\f\u0010p\u001a\u00020q*\u00020rH\u0002R\u0014\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001c0\u001bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010\u001d\u001a\u00020\u001e8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b!\u0010\"\u001a\u0004\b\u001f\u0010 R\u0016\u0010#\u001a\n\u0012\u0004\u0012\u00020$\u0018\u00010\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010%\u001a\u0004\u0018\u00010&X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012X\u0094\u0004¢\u0006\b\n\u0000\u001a\u0004\b'\u0010(R\u000e\u0010)\u001a\u00020*X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010+\u001a\u00020,X\u0082\u0004¢\u0006\u0004\n\u0002\u0010-R\u0014\u0010.\u001a\u00020\u000b8TX\u0094\u0004¢\u0006\u0006\u001a\u0004\b/\u00100R\u0014\u00101\u001a\u00020\u000b8TX\u0094\u0004¢\u0006\u0006\u001a\u0004\b2\u00100R\u0016\u00103\u001a\n\u0012\u0004\u0012\u00020$\u0018\u00010\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006s"}, d2 = {"Lorg/jetbrains/kotlin/incremental/IncrementalJvmCompilerRunner;", "Lorg/jetbrains/kotlin/incremental/IncrementalCompilerRunner;", "Lorg/jetbrains/kotlin/cli/common/arguments/K2JVMCompilerArguments;", "Lorg/jetbrains/kotlin/incremental/IncrementalJvmCachesManager;", "workingDir", "Ljava/io/File;", "reporter", "Lorg/jetbrains/kotlin/build/report/BuildReporter;", "Lorg/jetbrains/kotlin/build/report/metrics/GradleBuildTime;", "Lorg/jetbrains/kotlin/build/report/metrics/GradleBuildPerformanceMetric;", "usePreciseJavaTracking", "", "buildHistoryFile", "outputDirs", "", "modulesApiHistory", "Lorg/jetbrains/kotlin/incremental/multiproject/ModulesApiHistory;", "kotlinSourceFilesExtensions", "", "", "classpathChanges", "Lorg/jetbrains/kotlin/incremental/ClasspathChanges;", "withAbiSnapshot", "preciseCompilationResultsBackup", "keepIncrementalCompilationCachesInMemory", "(Ljava/io/File;Lorg/jetbrains/kotlin/build/report/BuildReporter;ZLjava/io/File;Ljava/util/Collection;Lorg/jetbrains/kotlin/incremental/multiproject/ModulesApiHistory;Ljava/util/List;Lorg/jetbrains/kotlin/incremental/ClasspathChanges;ZZZ)V", "changedUntrackedJavaClasses", "", "Lorg/jetbrains/kotlin/name/ClassId;", "compilerConfiguration", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "getCompilerConfiguration", "()Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "compilerConfiguration$delegate", "Lkotlin/Lazy;", "currentClasspathSnapshot", "Lorg/jetbrains/kotlin/incremental/classpathDiff/AccessibleClassSnapshot;", "javaFilesProcessor", "Lorg/jetbrains/kotlin/incremental/ChangedJavaFilesProcessor;", "getKotlinSourceFilesExtensions", "()Ljava/util/List;", "messageCollector", "Lorg/jetbrains/kotlin/incremental/util/BufferingMessageCollector;", "psiFileProvider", "org/jetbrains/kotlin/incremental/IncrementalJvmCompilerRunner$psiFileProvider$1", "Lorg/jetbrains/kotlin/incremental/IncrementalJvmCompilerRunner$psiFileProvider$1;", "shouldStoreFullFqNamesInLookupCache", "getShouldStoreFullFqNamesInLookupCache", "()Z", "shouldTrackChangesInLookupCache", "getShouldTrackChangesInLookupCache", "shrunkCurrentClasspathAgainstPreviousLookups", "additionalDirtyFiles", "", "caches", "generatedFiles", "Lorg/jetbrains/kotlin/build/GeneratedFile;", "services", "Lorg/jetbrains/kotlin/config/Services;", "additionalDirtyLookupSymbols", "Lorg/jetbrains/kotlin/incremental/LookupSymbol;", "calculateSourcesToCompile", "Lorg/jetbrains/kotlin/incremental/IncrementalCompilerRunner$CompilationMode;", "changedFiles", "Lorg/jetbrains/kotlin/incremental/ChangedFiles$Known;", "args", "Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;", "classpathAbiSnapshots", "", "Lorg/jetbrains/kotlin/incremental/AbiSnapshot;", "calculateSourcesToCompileImpl", "abiSnapshots", "createCacheManager", "icContext", "Lorg/jetbrains/kotlin/incremental/IncrementalCompilationContext;", "destinationDir", "makeServices", "Lorg/jetbrains/kotlin/config/Services$Builder;", "lookupTracker", "Lorg/jetbrains/kotlin/incremental/components/LookupTracker;", "expectActualTracker", "Lorg/jetbrains/kotlin/incremental/components/ExpectActualTracker;", "dirtySources", "", "isIncremental", "performWorkAfterCompilation", "", "compilationMode", "exitCode", "Lorg/jetbrains/kotlin/cli/common/ExitCode;", "performWorkBeforeCompilation", "processChangedJava", "Lorg/jetbrains/kotlin/build/report/metrics/BuildAttribute;", "processChangedUntrackedJavaClass", "psiClass", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiClass;", "classId", "processLookupSymbolsForAndroidLayouts", "runCompiler", "Lkotlin/Pair;", "sourcesToCompile", "allSources", "runWithNoDirtyKotlinSources", "setupJarDependencies", "updateCaches", "changesCollector", "Lorg/jetbrains/kotlin/incremental/ChangesCollector;", "forceExhaustiveWhen", "", "toChangeInfoList", "Lorg/jetbrains/kotlin/incremental/ChangeInfo;", "Lorg/jetbrains/kotlin/incremental/classpathDiff/ProgramSymbolSet;", "toChangesEither", "Lorg/jetbrains/kotlin/incremental/ChangesEither$Known;", "Lorg/jetbrains/kotlin/incremental/DirtyData;", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class IncrementalJvmCompilerRunner extends IncrementalCompilerRunner<K2JVMCompilerArguments, IncrementalJvmCachesManager> {
    private final Set<ClassId> changedUntrackedJavaClasses;
    private final ClasspathChanges classpathChanges;

    /* JADX INFO: renamed from: compilerConfiguration$delegate, reason: from kotlin metadata */
    private final Lazy compilerConfiguration;
    private List<? extends AccessibleClassSnapshot> currentClasspathSnapshot;
    private ChangedJavaFilesProcessor javaFilesProcessor;
    private final List<String> kotlinSourceFilesExtensions;
    private final BufferingMessageCollector messageCollector;
    private final ModulesApiHistory modulesApiHistory;
    private final IncrementalJvmCompilerRunner$psiFileProvider$1 psiFileProvider;
    private List<? extends AccessibleClassSnapshot> shrunkCurrentClasspathAgainstPreviousLookups;
    private final boolean usePreciseJavaTracking;

    /* JADX INFO: compiled from: IncrementalJvmCompilerRunner.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[KotlinClassHeader.Kind.values().length];
            try {
                iArr[KotlinClassHeader.Kind.CLASS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[KotlinClassHeader.Kind.MULTIFILE_CLASS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[KotlinClassHeader.Kind.MULTIFILE_CLASS_PART.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[KotlinClassHeader.Kind.FILE_FACADE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[KotlinClassHeader.Kind.SYNTHETIC_CLASS.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[KotlinClassHeader.Kind.UNKNOWN.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    private final Object forceExhaustiveWhen(Object obj) {
        return obj;
    }

    @Override // org.jetbrains.kotlin.incremental.IncrementalCompilerRunner
    public /* bridge */ /* synthetic */ Iterable additionalDirtyFiles(IncrementalCachesManager incrementalCachesManager, List list, Services services) {
        return additionalDirtyFiles((IncrementalJvmCachesManager) incrementalCachesManager, (List<? extends GeneratedFile>) list, services);
    }

    @Override // org.jetbrains.kotlin.incremental.IncrementalCompilerRunner
    public /* bridge */ /* synthetic */ IncrementalCompilerRunner.CompilationMode calculateSourcesToCompile(IncrementalCachesManager incrementalCachesManager, ChangedFiles.Known known, CommonCompilerArguments commonCompilerArguments, MessageCollector messageCollector, Map map) {
        return calculateSourcesToCompile((IncrementalJvmCachesManager) incrementalCachesManager, known, (K2JVMCompilerArguments) commonCompilerArguments, messageCollector, (Map<String, ? extends AbiSnapshot>) map);
    }

    @Override // org.jetbrains.kotlin.incremental.IncrementalCompilerRunner
    public /* bridge */ /* synthetic */ Services.Builder makeServices(CommonCompilerArguments commonCompilerArguments, LookupTracker lookupTracker, ExpectActualTracker expectActualTracker, IncrementalCachesManager incrementalCachesManager, Set set, boolean z) {
        return makeServices((K2JVMCompilerArguments) commonCompilerArguments, lookupTracker, expectActualTracker, (IncrementalJvmCachesManager) incrementalCachesManager, (Set<? extends File>) set, z);
    }

    @Override // org.jetbrains.kotlin.incremental.IncrementalCompilerRunner
    public /* bridge */ /* synthetic */ Pair runCompiler(List list, CommonCompilerArguments commonCompilerArguments, IncrementalCachesManager incrementalCachesManager, Services services, MessageCollector messageCollector, List list2, boolean z) {
        return runCompiler((List<? extends File>) list, (K2JVMCompilerArguments) commonCompilerArguments, (IncrementalJvmCachesManager) incrementalCachesManager, services, messageCollector, (List<? extends File>) list2, z);
    }

    @Override // org.jetbrains.kotlin.incremental.IncrementalCompilerRunner
    public /* bridge */ /* synthetic */ Map setupJarDependencies(CommonCompilerArguments commonCompilerArguments, BuildReporter buildReporter) {
        return setupJarDependencies((K2JVMCompilerArguments) commonCompilerArguments, (BuildReporter<GradleBuildTime, GradleBuildPerformanceMetric>) buildReporter);
    }

    @Override // org.jetbrains.kotlin.incremental.IncrementalCompilerRunner
    public /* bridge */ /* synthetic */ void updateCaches(Services services, IncrementalCachesManager incrementalCachesManager, List list, ChangesCollector changesCollector) {
        updateCaches(services, (IncrementalJvmCachesManager) incrementalCachesManager, (List<? extends GeneratedFile>) list, changesCollector);
    }

    public /* synthetic */ IncrementalJvmCompilerRunner(File file, BuildReporter buildReporter, boolean z, File file2, Collection collection, ModulesApiHistory modulesApiHistory, List list, ClasspathChanges classpathChanges, boolean z2, boolean z3, boolean z4, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(file, buildReporter, z, file2, collection, modulesApiHistory, (i & 64) != 0 ? SourcesUtilsKt.getDEFAULT_KOTLIN_SOURCE_FILES_EXTENSIONS() : list, classpathChanges, (i & 256) != 0 ? false : z2, (i & 512) != 0 ? false : z3, (i & 1024) != 0 ? false : z4);
    }

    @Override // org.jetbrains.kotlin.incremental.IncrementalCompilerRunner
    protected List<String> getKotlinSourceFilesExtensions() {
        return this.kotlinSourceFilesExtensions;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IncrementalJvmCompilerRunner(File workingDir, BuildReporter<GradleBuildTime, GradleBuildPerformanceMetric> reporter, boolean z, File file, Collection<? extends File> collection, ModulesApiHistory modulesApiHistory, List<String> kotlinSourceFilesExtensions, ClasspathChanges classpathChanges, boolean z2, boolean z3, boolean z4) {
        super(workingDir, "caches-jvm", reporter, file, collection, z2, z3, z4);
        Intrinsics.checkNotNullParameter(workingDir, "workingDir");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        Intrinsics.checkNotNullParameter(modulesApiHistory, "modulesApiHistory");
        Intrinsics.checkNotNullParameter(kotlinSourceFilesExtensions, "kotlinSourceFilesExtensions");
        Intrinsics.checkNotNullParameter(classpathChanges, "classpathChanges");
        this.usePreciseJavaTracking = z;
        this.modulesApiHistory = modulesApiHistory;
        this.kotlinSourceFilesExtensions = kotlinSourceFilesExtensions;
        this.classpathChanges = classpathChanges;
        this.messageCollector = new BufferingMessageCollector();
        this.compilerConfiguration = LazyKt.lazy(new IncrementalJvmCompilerRunner$compilerConfiguration$2(this));
        this.psiFileProvider = new IncrementalJvmCompilerRunner$psiFileProvider$1(this);
        this.changedUntrackedJavaClasses = new LinkedHashSet();
        this.javaFilesProcessor = !z ? new ChangedJavaFilesProcessor(reporter, new Function1<File, PsiFile>() { // from class: org.jetbrains.kotlin.incremental.IncrementalJvmCompilerRunner$javaFilesProcessor$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final PsiFile invoke(File it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return this.this$0.psiFileProvider.javaFile(it);
            }
        }) : null;
    }

    @Override // org.jetbrains.kotlin.incremental.IncrementalCompilerRunner
    protected boolean getShouldTrackChangesInLookupCache() {
        return this.classpathChanges instanceof ClasspathChanges.ClasspathSnapshotEnabled.IncrementalRun;
    }

    @Override // org.jetbrains.kotlin.incremental.IncrementalCompilerRunner
    protected boolean getShouldStoreFullFqNamesInLookupCache() {
        return getWithAbiSnapshot() || (this.classpathChanges instanceof ClasspathChanges.ClasspathSnapshotEnabled);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.incremental.IncrementalCompilerRunner
    public IncrementalJvmCachesManager createCacheManager(IncrementalCompilationContext icContext, K2JVMCompilerArguments args) {
        Intrinsics.checkNotNullParameter(icContext, "icContext");
        Intrinsics.checkNotNullParameter(args, "args");
        String destination = args.getDestination();
        return new IncrementalJvmCachesManager(icContext, destination != null ? new File(destination) : null, getCacheDirectory());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.incremental.IncrementalCompilerRunner
    public File destinationDir(K2JVMCompilerArguments args) {
        Intrinsics.checkNotNullParameter(args, "args");
        return CompilerRunnerUtils.getDestinationAsFile(args);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final CompilerConfiguration getCompilerConfiguration() {
        return (CompilerConfiguration) this.compilerConfiguration.getValue();
    }

    protected IncrementalCompilerRunner.CompilationMode calculateSourcesToCompile(IncrementalJvmCachesManager caches, ChangedFiles.Known changedFiles, K2JVMCompilerArguments args, MessageCollector messageCollector, Map<String, ? extends AbiSnapshot> classpathAbiSnapshots) {
        Intrinsics.checkNotNullParameter(caches, "caches");
        Intrinsics.checkNotNullParameter(changedFiles, "changedFiles");
        Intrinsics.checkNotNullParameter(args, "args");
        Intrinsics.checkNotNullParameter(messageCollector, "messageCollector");
        Intrinsics.checkNotNullParameter(classpathAbiSnapshots, "classpathAbiSnapshots");
        try {
            return calculateSourcesToCompileImpl(caches, changedFiles, args, messageCollector, classpathAbiSnapshots, getWithAbiSnapshot());
        } finally {
            this.messageCollector.flush(messageCollector);
            this.messageCollector.clear();
        }
    }

    protected Map<String, AbiSnapshot> setupJarDependencies(K2JVMCompilerArguments args, BuildReporter<GradleBuildTime, GradleBuildPerformanceMetric> reporter) {
        Intrinsics.checkNotNullParameter(args, "args");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        HashMap map = new HashMap();
        List<File> classpathAsList = CompilerRunnerUtils.getClasspathAsList(args);
        ArrayList<File> arrayList = new ArrayList();
        for (Object obj : classpathAsList) {
            if (StringsKt.equals(FilesKt.getExtension((File) obj), "jar", true)) {
                arrayList.add(obj);
            }
        }
        for (File file : arrayList) {
            Either<Set<File>> eitherAbiSnapshot = this.modulesApiHistory.abiSnapshot(file);
            if (eitherAbiSnapshot instanceof Either.Success) {
                for (final File file2 : (Iterable) ((Either.Success) eitherAbiSnapshot).getValue()) {
                    if (file2.exists()) {
                        String absolutePath = file.getAbsolutePath();
                        Intrinsics.checkNotNullExpressionValue(absolutePath, "getAbsolutePath(...)");
                        map.put(absolutePath, AbiSnapshotImpl.INSTANCE.read(file2));
                    } else {
                        ICReporterKt.warn(reporter, new Function0<String>() { // from class: org.jetbrains.kotlin.incremental.IncrementalJvmCompilerRunner$setupJarDependencies$2$1$1$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            public final String invoke() {
                                return "Snapshot file does not exist: " + file2.getPath() + ". Continue anyway.";
                            }
                        });
                    }
                }
            }
        }
        return map;
    }

    private final IncrementalCompilerRunner.CompilationMode calculateSourcesToCompileImpl(IncrementalJvmCachesManager caches, ChangedFiles.Known changedFiles, K2JVMCompilerArguments args, MessageCollector messageCollector, Map<String, ? extends AbiSnapshot> abiSnapshots, boolean withAbiSnapshot) {
        final ChangesEither.Unknown classpathChanges;
        DirtyFilesContainer dirtyFilesContainer = new DirtyFilesContainer(caches, getReporter(), getKotlinSourceFilesExtensions());
        initDirtyFiles(dirtyFilesContainer, changedFiles);
        ICReporterKt.debug(getReporter(), new Function0<String>() { // from class: org.jetbrains.kotlin.incremental.IncrementalJvmCompilerRunner.calculateSourcesToCompileImpl.1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final String invoke() {
                return "Classpath changes info passed from Gradle task: " + Reflection.getOrCreateKotlinClass(IncrementalJvmCompilerRunner.this.classpathChanges.getClass()).getSimpleName();
            }
        });
        ClasspathChanges classpathChanges2 = this.classpathChanges;
        if (classpathChanges2 instanceof ClasspathChanges.ClasspathSnapshotEnabled.IncrementalRun.NoChanges) {
            classpathChanges = new ChangesEither.Known(SetsKt.emptySet(), SetsKt.emptySet());
        } else if (classpathChanges2 instanceof ClasspathChanges.ClasspathSnapshotEnabled.IncrementalRun.ToBeComputedByIncrementalCompiler) {
            BuildReporter<GradleBuildTime, GradleBuildPerformanceMetric> reporter = getReporter();
            GradleBuildTime gradleBuildTime = GradleBuildTime.COMPUTE_CLASSPATH_CHANGES;
            reporter.startMeasure(gradleBuildTime);
            try {
                getReporter().addMetric(GradleBuildPerformanceMetric.COMPUTE_CLASSPATH_CHANGES_EXECUTION_COUNT, 1L);
                ChangesEither.Known changesEither = toChangesEither(BuildUtilKt.getChangedAndImpactedSymbols(toChangeInfoList(ClasspathChangesComputer.INSTANCE.computeClasspathChanges(((ClasspathChanges.ClasspathSnapshotEnabled.IncrementalRun.ToBeComputedByIncrementalCompiler) this.classpathChanges).getClasspathSnapshotFiles(), caches.getLookupCache(), new Function2<List<? extends AccessibleClassSnapshot>, List<? extends AccessibleClassSnapshot>, Unit>() { // from class: org.jetbrains.kotlin.incremental.IncrementalJvmCompilerRunner$calculateSourcesToCompileImpl$changedAndImpactedSymbols$1$storeCurrentClasspathSnapshotForReuse$1
                    {
                        super(2);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public /* bridge */ /* synthetic */ Unit invoke(List<? extends AccessibleClassSnapshot> list, List<? extends AccessibleClassSnapshot> list2) {
                        invoke2(list, list2);
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(List<? extends AccessibleClassSnapshot> currentClasspathSnapshotArg, List<? extends AccessibleClassSnapshot> shrunkCurrentClasspathAgainstPreviousLookupsArg) {
                        Intrinsics.checkNotNullParameter(currentClasspathSnapshotArg, "currentClasspathSnapshotArg");
                        Intrinsics.checkNotNullParameter(shrunkCurrentClasspathAgainstPreviousLookupsArg, "shrunkCurrentClasspathAgainstPreviousLookupsArg");
                        this.this$0.currentClasspathSnapshot = currentClasspathSnapshotArg;
                        this.this$0.shrunkCurrentClasspathAgainstPreviousLookups = shrunkCurrentClasspathAgainstPreviousLookupsArg;
                    }
                }, new ClasspathSnapshotBuildReporter(getReporter()))), CollectionsKt.listOf(caches.getPlatformCache()), getReporter()));
                reporter.endMeasure(gradleBuildTime);
                classpathChanges = changesEither;
            } catch (Throwable th) {
                reporter.endMeasure(gradleBuildTime);
                throw th;
            }
        } else if (classpathChanges2 instanceof ClasspathChanges.ClasspathSnapshotEnabled.NotAvailableDueToMissingClasspathSnapshot) {
            classpathChanges = new ChangesEither.Unknown(BuildAttribute.CLASSPATH_SNAPSHOT_NOT_FOUND);
        } else if (classpathChanges2 instanceof ClasspathChanges.ClasspathSnapshotEnabled.NotAvailableForNonIncrementalRun) {
            classpathChanges = new ChangesEither.Unknown(BuildAttribute.UNKNOWN_CHANGES_IN_GRADLE_INPUTS);
        } else {
            if (!(classpathChanges2 instanceof ClasspathChanges.ClasspathSnapshotDisabled)) {
                if (classpathChanges2 instanceof ClasspathChanges.NotAvailableForJSCompiler) {
                    throw new IllegalStateException(("Unexpected type for this code path: " + this.classpathChanges.getClass().getName() + '.').toString());
                }
                throw new NoWhenBranchMatchedException();
            }
            BuildReporter<GradleBuildTime, GradleBuildPerformanceMetric> reporter2 = getReporter();
            GradleBuildTime gradleBuildTime2 = GradleBuildTime.IC_ANALYZE_CHANGES_IN_DEPENDENCIES;
            reporter2.startMeasure(gradleBuildTime2);
            try {
                if (getBuildHistoryFile() == null) {
                    throw new IllegalStateException("The build is configured to use the build-history based IC approach, but doesn't specify the buildHistoryFile".toString());
                }
                if (!withAbiSnapshot && !getBuildHistoryFile().isFile()) {
                    IncrementalCompilerRunner.CompilationMode.Rebuild rebuild = new IncrementalCompilerRunner.CompilationMode.Rebuild(BuildAttribute.NO_BUILD_HISTORY);
                    reporter2.endMeasure(gradleBuildTime2);
                    return rebuild;
                }
                if (!getLastBuildInfoFile().exists()) {
                    IncrementalCompilerRunner.CompilationMode.Rebuild rebuild2 = new IncrementalCompilerRunner.CompilationMode.Rebuild(BuildAttribute.NO_LAST_BUILD_INFO);
                    reporter2.endMeasure(gradleBuildTime2);
                    return rebuild2;
                }
                final BuildInfo buildInfo = BuildInfo.INSTANCE.read(getLastBuildInfoFile(), messageCollector);
                if (buildInfo == null) {
                    IncrementalCompilerRunner.CompilationMode.Rebuild rebuild3 = new IncrementalCompilerRunner.CompilationMode.Rebuild(BuildAttribute.INVALID_LAST_BUILD_INFO);
                    reporter2.endMeasure(gradleBuildTime2);
                    return rebuild3;
                }
                ICReporterKt.debug(getReporter(), new Function0<String>() { // from class: org.jetbrains.kotlin.incremental.IncrementalJvmCompilerRunner$calculateSourcesToCompileImpl$changedAndImpactedSymbols$2$1
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final String invoke() {
                        return "Last Kotlin Build info -- " + buildInfo;
                    }
                });
                Collection<LookupSymbolKey> lookupSymbols = caches.getLookupCache().getLookupSymbols();
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(lookupSymbols, 10));
                for (LookupSymbolKey lookupSymbolKey : lookupSymbols) {
                    String scope = lookupSymbolKey.getScope();
                    if (StringsKt.isBlank(scope)) {
                        scope = lookupSymbolKey.getName();
                    }
                    arrayList.add(scope);
                }
                classpathChanges = ChangesDetectionUtilsKt.getClasspathChanges(CompilerRunnerUtils.getClasspathAsList(args), changedFiles, buildInfo, this.modulesApiHistory, getReporter(), abiSnapshots, withAbiSnapshot, caches.getPlatformCache(), CollectionsKt.distinct(arrayList));
                reporter2.endMeasure(gradleBuildTime2);
            } catch (Throwable th2) {
                reporter2.endMeasure(gradleBuildTime2);
                throw th2;
            }
        }
        if (classpathChanges instanceof ChangesEither.Unknown) {
            ICReporterKt.info(getReporter(), new Function0<String>() { // from class: org.jetbrains.kotlin.incremental.IncrementalJvmCompilerRunner.calculateSourcesToCompileImpl.2
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final String invoke() {
                    return "Could not get classpath changes: " + ((ChangesEither.Unknown) classpathChanges).getReason();
                }
            });
            return new IncrementalCompilerRunner.CompilationMode.Rebuild(((ChangesEither.Unknown) classpathChanges).getReason());
        }
        if (!(classpathChanges instanceof ChangesEither.Known)) {
            throw new NoWhenBranchMatchedException();
        }
        forceExhaustiveWhen(Unit.INSTANCE);
        ChangesEither.Known known = (ChangesEither.Known) classpathChanges;
        dirtyFilesContainer.addByDirtySymbols(known.getLookupSymbols());
        dirtyFilesContainer.addByDirtyClasses(known.getFqNames());
        BuildReporter<GradleBuildTime, GradleBuildPerformanceMetric> reporter3 = getReporter();
        GradleBuildTime gradleBuildTime3 = GradleBuildTime.IC_ANALYZE_CHANGES_IN_JAVA_SOURCES;
        reporter3.startMeasure(gradleBuildTime3);
        try {
            if (this.usePreciseJavaTracking) {
                BuildAttribute buildAttributeProcessChangedJava = processChangedJava(changedFiles, caches);
                if (buildAttributeProcessChangedJava != null) {
                    IncrementalCompilerRunner.CompilationMode.Rebuild rebuild4 = new IncrementalCompilerRunner.CompilationMode.Rebuild(buildAttributeProcessChangedJava);
                    reporter3.endMeasure(gradleBuildTime3);
                    return rebuild4;
                }
            } else {
                ChangedJavaFilesProcessor changedJavaFilesProcessor = this.javaFilesProcessor;
                Intrinsics.checkNotNull(changedJavaFilesProcessor);
                ChangesEither changesEitherProcess = changedJavaFilesProcessor.process(changedFiles);
                if (!(changesEitherProcess instanceof ChangesEither.Known)) {
                    if (!(changesEitherProcess instanceof ChangesEither.Unknown)) {
                        throw new NoWhenBranchMatchedException();
                    }
                    IncrementalCompilerRunner.CompilationMode.Rebuild rebuild5 = new IncrementalCompilerRunner.CompilationMode.Rebuild(((ChangesEither.Unknown) changesEitherProcess).getReason());
                    reporter3.endMeasure(gradleBuildTime3);
                    return rebuild5;
                }
                dirtyFilesContainer.addByDirtySymbols(((ChangesEither.Known) changesEitherProcess).getLookupSymbols());
            }
            Unit unit = Unit.INSTANCE;
            reporter3.endMeasure(gradleBuildTime3);
            BuildReporter<GradleBuildTime, GradleBuildPerformanceMetric> reporter4 = getReporter();
            GradleBuildTime gradleBuildTime4 = GradleBuildTime.IC_ANALYZE_CHANGES_IN_ANDROID_LAYOUTS;
            reporter4.startMeasure(gradleBuildTime4);
            try {
                Collection<LookupSymbol> collectionProcessLookupSymbolsForAndroidLayouts = processLookupSymbolsForAndroidLayouts(changedFiles);
                reporter4.endMeasure(gradleBuildTime4);
                BuildReporter<GradleBuildTime, GradleBuildPerformanceMetric> reporter5 = getReporter();
                GradleBuildTime gradleBuildTime5 = GradleBuildTime.IC_DETECT_REMOVED_CLASSES;
                reporter5.startMeasure(gradleBuildTime5);
                try {
                    DirtyData removedClassesChanges = getRemovedClassesChanges(caches, changedFiles);
                    reporter5.endMeasure(gradleBuildTime5);
                    dirtyFilesContainer.addByDirtySymbols(collectionProcessLookupSymbolsForAndroidLayouts);
                    dirtyFilesContainer.addByDirtySymbols(removedClassesChanges.getDirtyLookupSymbols());
                    dirtyFilesContainer.addByDirtyClasses(removedClassesChanges.getDirtyClassesFqNames());
                    dirtyFilesContainer.addByDirtyClasses(removedClassesChanges.getDirtyClassesFqNamesForceRecompile());
                    return new IncrementalCompilerRunner.CompilationMode.Incremental(dirtyFilesContainer);
                } catch (Throwable th3) {
                    reporter5.endMeasure(gradleBuildTime5);
                    throw th3;
                }
            } catch (Throwable th4) {
                reporter4.endMeasure(gradleBuildTime4);
                throw th4;
            }
        } catch (Throwable th5) {
            reporter3.endMeasure(gradleBuildTime3);
            throw th5;
        }
    }

    private final List<ChangeInfo> toChangeInfoList(ProgramSymbolSet programSymbolSet) {
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = programSymbolSet.getClasses().iterator();
        while (it.hasNext()) {
            FqName fqNameAsSingleFqName = ((ClassId) it.next()).asSingleFqName();
            Intrinsics.checkNotNullExpressionValue(fqNameAsSingleFqName, "asSingleFqName(...)");
            arrayList.add(new ChangeInfo.SignatureChanged(fqNameAsSingleFqName, true));
        }
        for (Map.Entry<ClassId, Set<String>> entry : programSymbolSet.getClassMembers().entrySet()) {
            ClassId key = entry.getKey();
            Set<String> value = entry.getValue();
            FqName fqNameAsSingleFqName2 = key.asSingleFqName();
            Intrinsics.checkNotNullExpressionValue(fqNameAsSingleFqName2, "asSingleFqName(...)");
            arrayList.add(new ChangeInfo.MembersChanged(fqNameAsSingleFqName2, value));
        }
        for (Map.Entry<FqName, Set<String>> entry2 : programSymbolSet.getPackageMembers().entrySet()) {
            arrayList.add(new ChangeInfo.MembersChanged(entry2.getKey(), entry2.getValue()));
        }
        return arrayList;
    }

    private final ChangesEither.Known toChangesEither(DirtyData dirtyData) {
        return new ChangesEither.Known(dirtyData.getDirtyLookupSymbols(), CollectionsKt.plus((Collection) dirtyData.getDirtyClassesFqNames(), (Iterable) dirtyData.getDirtyClassesFqNamesForceRecompile()));
    }

    private final BuildAttribute processChangedJava(ChangedFiles.Known changedFiles, IncrementalJvmCachesManager caches) {
        List listPlus = CollectionsKt.plus((Collection) changedFiles.getModified(), (Iterable) changedFiles.getRemoved());
        ArrayList arrayList = new ArrayList();
        for (Object obj : listPlus) {
            if (FileUtilsKt.isJavaFile((File) obj)) {
                arrayList.add(obj);
            }
        }
        ArrayList<File> arrayList2 = arrayList;
        for (final File file : arrayList2) {
            if (!caches.getPlatformCache().isTrackedFile(file)) {
                if (!file.exists()) {
                    ICReporterKt.info(getReporter(), new Function0<String>() { // from class: org.jetbrains.kotlin.incremental.IncrementalJvmCompilerRunner.processChangedJava.1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        public final String invoke() {
                            return "Could not get changed for untracked removed java file " + file;
                        }
                    });
                    return BuildAttribute.JAVA_CHANGE_UNTRACKED_FILE_IS_REMOVED;
                }
                final PsiFile psiFileJavaFile = this.psiFileProvider.javaFile(file);
                if (!(psiFileJavaFile instanceof PsiJavaFile)) {
                    ICReporterKt.info(getReporter(), new Function0<String>() { // from class: org.jetbrains.kotlin.incremental.IncrementalJvmCompilerRunner.processChangedJava.2
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        public final String invoke() {
                            StringBuilder sb = new StringBuilder("[Precise Java tracking] Expected PsiJavaFile, got ");
                            PsiFile psiFile = psiFileJavaFile;
                            return sb.append(psiFile != null ? psiFile.getClass() : null).toString();
                        }
                    });
                    return BuildAttribute.JAVA_CHANGE_UNEXPECTED_PSI;
                }
                PsiClass[] classes = ((PsiJavaFile) psiFileJavaFile).getClasses();
                Intrinsics.checkNotNullExpressionValue(classes, "getClasses(...)");
                for (PsiClass psiClass : classes) {
                    String qualifiedName = psiClass.getQualifiedName();
                    if (qualifiedName == null) {
                        ICReporterKt.info(getReporter(), new Function0<String>() { // from class: org.jetbrains.kotlin.incremental.IncrementalJvmCompilerRunner.processChangedJava.3
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            public final String invoke() {
                                return "[Precise Java tracking] Class with unknown qualified name in " + file;
                            }
                        });
                        return BuildAttribute.JAVA_CHANGE_UNKNOWN_QUALIFIER;
                    }
                    Intrinsics.checkNotNull(psiClass);
                    ClassId classId = ClassId.topLevel(new FqName(qualifiedName));
                    Intrinsics.checkNotNullExpressionValue(classId, "topLevel(...)");
                    processChangedUntrackedJavaClass(psiClass, classId);
                }
            }
        }
        caches.getPlatformCache().markDirty((Collection<? extends File>) arrayList2);
        return null;
    }

    private final void processChangedUntrackedJavaClass(PsiClass psiClass, ClassId classId) {
        this.changedUntrackedJavaClasses.add(classId);
        PsiClass[] innerClasses = psiClass.getInnerClasses();
        Intrinsics.checkNotNullExpressionValue(innerClasses, "getInnerClasses(...)");
        for (PsiClass psiClass2 : innerClasses) {
            String name2 = psiClass2.get_name();
            if (name2 != null) {
                Intrinsics.checkNotNull(psiClass2);
                ClassId classIdCreateNestedClassId = classId.createNestedClassId(Name.identifier(name2));
                Intrinsics.checkNotNullExpressionValue(classIdCreateNestedClassId, "createNestedClassId(...)");
                processChangedUntrackedJavaClass(psiClass2, classIdCreateNestedClassId);
            }
        }
    }

    private final Collection<LookupSymbol> processLookupSymbolsForAndroidLayouts(ChangedFiles.Known changedFiles) {
        ArrayList arrayList = new ArrayList();
        for (File file : CollectionsKt.plus((Collection) changedFiles.getModified(), (Iterable) changedFiles.getRemoved())) {
            String lowerCase = FilesKt.getExtension(file).toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
            if (Intrinsics.areEqual(lowerCase, "xml")) {
                String name2 = file.getName();
                Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                arrayList.add(new LookupSymbol(UtilsKt.ANDROID_LAYOUT_CONTENT_LOOKUP_NAME, StringsKt.substringBeforeLast$default(name2, '.', (String) null, 2, (Object) null)));
            }
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.incremental.IncrementalCompilerRunner
    public void performWorkBeforeCompilation(IncrementalCompilerRunner.CompilationMode compilationMode, K2JVMCompilerArguments args) {
        Intrinsics.checkNotNullParameter(compilationMode, "compilationMode");
        Intrinsics.checkNotNullParameter(args, "args");
        super.performWorkBeforeCompilation(compilationMode, args);
        if (compilationMode instanceof IncrementalCompilerRunner.CompilationMode.Incremental) {
            CompilerRunnerUtils.setClasspathAsList(args, CollectionsKt.plus((Collection) CollectionsKt.listOf(CompilerRunnerUtils.getDestinationAsFile(args)), (Iterable) CompilerRunnerUtils.getClasspathAsList(args)));
        }
    }

    protected void updateCaches(Services services, IncrementalJvmCachesManager caches, List<? extends GeneratedFile> generatedFiles, ChangesCollector changesCollector) {
        Intrinsics.checkNotNullParameter(services, "services");
        Intrinsics.checkNotNullParameter(caches, "caches");
        Intrinsics.checkNotNullParameter(generatedFiles, "generatedFiles");
        Intrinsics.checkNotNullParameter(changesCollector, "changesCollector");
        List<? extends GeneratedFile> list = generatedFiles;
        IncrementalJvmCache platformCache = caches.getPlatformCache();
        Object obj = services.get(JavaClassesTracker.class);
        BuildUtilKt.updateIncrementalCache(list, platformCache, changesCollector, obj instanceof JavaClassesTrackerImpl ? (JavaClassesTrackerImpl) obj : null);
    }

    @Override // org.jetbrains.kotlin.incremental.IncrementalCompilerRunner
    public boolean runWithNoDirtyKotlinSources(IncrementalJvmCachesManager caches) {
        Intrinsics.checkNotNullParameter(caches, "caches");
        return (caches.getPlatformCache().getObsoleteJavaClasses().isEmpty() && this.changedUntrackedJavaClasses.isEmpty()) ? false : true;
    }

    protected Iterable<File> additionalDirtyFiles(IncrementalJvmCachesManager caches, List<? extends GeneratedFile> generatedFiles, Services services) {
        Intrinsics.checkNotNullParameter(caches, "caches");
        Intrinsics.checkNotNullParameter(generatedFiles, "generatedFiles");
        Intrinsics.checkNotNullParameter(services, "services");
        IncrementalJvmCache platformCache = caches.getPlatformCache();
        HashSet hashSet = new HashSet();
        for (GeneratedFile generatedFile : generatedFiles) {
            if (generatedFile instanceof GeneratedJvmClass) {
                LocalFileKotlinClass outputClass = ((GeneratedJvmClass) generatedFile).getOutputClass();
                int i = WhenMappings.$EnumSwitchMapping$0[outputClass.getClassHeader().getKind().ordinal()];
                if (i == 1) {
                    FqName fqNameForClassNameWithoutDollars = outputClass.getClassName().getFqNameForClassNameWithoutDollars();
                    Intrinsics.checkNotNullExpressionValue(fqNameForClassNameWithoutDollars, "getFqNameForClassNameWithoutDollars(...)");
                    File sourceFileIfClass = platformCache.getSourceFileIfClass(fqNameForClassNameWithoutDollars);
                    if (sourceFileIfClass != null) {
                        hashSet.add(sourceFileIfClass);
                    }
                } else if (i == 2) {
                    String internalName = outputClass.getClassName().getInternalName();
                    Intrinsics.checkNotNullExpressionValue(internalName, "getInternalName(...)");
                    hashSet.addAll(additionalDirtyFiles$partsByFacadeName(platformCache, internalName));
                } else if (i == 3) {
                    String multifileClassName = outputClass.getClassHeader().getMultifileClassName();
                    Intrinsics.checkNotNull(multifileClassName);
                    hashSet.addAll(additionalDirtyFiles$partsByFacadeName(platformCache, multifileClassName));
                }
            }
        }
        return hashSet;
    }

    private static final List<File> additionalDirtyFiles$partsByFacadeName(IncrementalJvmCache incrementalJvmCache, String str) {
        List stableMultifileFacadeParts = incrementalJvmCache.getStableMultifileFacadeParts(str);
        if (stableMultifileFacadeParts == null) {
            stableMultifileFacadeParts = CollectionsKt.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = stableMultifileFacadeParts.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList, incrementalJvmCache.sourcesByInternalName((String) it.next()));
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.incremental.IncrementalCompilerRunner
    protected Iterable<LookupSymbol> additionalDirtyLookupSymbols() {
        Collection<LookupSymbol> collectionEmptyList;
        ChangedJavaFilesProcessor changedJavaFilesProcessor = this.javaFilesProcessor;
        if (changedJavaFilesProcessor == null || (collectionEmptyList = changedJavaFilesProcessor.getAllChangedSymbols()) == null) {
            collectionEmptyList = CollectionsKt.emptyList();
        }
        return collectionEmptyList;
    }

    protected Services.Builder makeServices(K2JVMCompilerArguments args, LookupTracker lookupTracker, ExpectActualTracker expectActualTracker, IncrementalJvmCachesManager caches, Set<? extends File> dirtySources, boolean isIncremental) {
        Intrinsics.checkNotNullParameter(args, "args");
        Intrinsics.checkNotNullParameter(lookupTracker, "lookupTracker");
        Intrinsics.checkNotNullParameter(expectActualTracker, "expectActualTracker");
        Intrinsics.checkNotNullParameter(caches, "caches");
        Intrinsics.checkNotNullParameter(dirtySources, "dirtySources");
        Services.Builder builderMakeServices = super.makeServices(args, lookupTracker, expectActualTracker, caches, dirtySources, isIncremental);
        String moduleName = args.getModuleName();
        if (moduleName == null) {
            throw new IllegalArgumentException("'moduleName' is null!".toString());
        }
        builderMakeServices.register(IncrementalCompilationComponents.class, new IncrementalCompilationComponentsImpl(MapsKt.mapOf(TuplesKt.to(new TargetId(moduleName, ModuleXmlParser.TYPE_PRODUCTION), caches.getPlatformCache()))));
        if (this.usePreciseJavaTracking) {
            JavaClassesTrackerImpl javaClassesTrackerImpl = new JavaClassesTrackerImpl(caches.getPlatformCache(), CollectionsKt.toSet(this.changedUntrackedJavaClasses), CommonConfigurationKeysKt.getLanguageVersionSettings(getCompilerConfiguration()));
            this.changedUntrackedJavaClasses.clear();
            builderMakeServices.register(JavaClassesTracker.class, javaClassesTrackerImpl);
        }
        return builderMakeServices;
    }

    protected Pair<ExitCode, Collection<File>> runCompiler(List<? extends File> sourcesToCompile, K2JVMCompilerArguments args, IncrementalJvmCachesManager caches, Services services, MessageCollector messageCollector, List<? extends File> allSources, boolean isIncremental) {
        Intrinsics.checkNotNullParameter(sourcesToCompile, "sourcesToCompile");
        Intrinsics.checkNotNullParameter(args, "args");
        Intrinsics.checkNotNullParameter(caches, "caches");
        Intrinsics.checkNotNullParameter(services, "services");
        Intrinsics.checkNotNullParameter(messageCollector, "messageCollector");
        Intrinsics.checkNotNullParameter(allSources, "allSources");
        K2JVMCompiler k2JVMCompiler = new K2JVMCompiler();
        List<String> list = CollectionsKt.toList(args.getFreeArgs());
        List<String> freeArgs = args.getFreeArgs();
        List<? extends File> list2 = sourcesToCompile;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
        Iterator<T> it = list2.iterator();
        while (it.hasNext()) {
            arrayList.add(((File) it.next()).getAbsolutePath());
        }
        args.setFreeArgs(CollectionsKt.plus((Collection) freeArgs, (Iterable) arrayList));
        args.setAllowNoSourceFiles(true);
        ExitCode exitCodeExec = k2JVMCompiler.exec(messageCollector, services, args);
        args.setFreeArgs(list);
        reportPerformanceData(k2JVMCompiler.getDefaultPerformanceManager());
        return TuplesKt.to(exitCodeExec, sourcesToCompile);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.incremental.IncrementalCompilerRunner
    public void performWorkAfterCompilation(IncrementalCompilerRunner.CompilationMode compilationMode, ExitCode exitCode, IncrementalJvmCachesManager caches) {
        Intrinsics.checkNotNullParameter(compilationMode, "compilationMode");
        Intrinsics.checkNotNullParameter(exitCode, "exitCode");
        Intrinsics.checkNotNullParameter(caches, "caches");
        super.performWorkAfterCompilation(compilationMode, exitCode, caches);
        if ((this.classpathChanges instanceof ClasspathChanges.ClasspathSnapshotEnabled) && exitCode == ExitCode.OK) {
            BuildReporter<GradleBuildTime, GradleBuildPerformanceMetric> reporter = getReporter();
            GradleBuildTime gradleBuildTime = GradleBuildTime.SHRINK_AND_SAVE_CURRENT_CLASSPATH_SNAPSHOT_AFTER_COMPILATION;
            reporter.startMeasure(gradleBuildTime);
            try {
                ClasspathSnapshotShrinkerKt.shrinkAndSaveClasspathSnapshot(compilationMode instanceof IncrementalCompilerRunner.CompilationMode.Incremental, (ClasspathChanges.ClasspathSnapshotEnabled) this.classpathChanges, caches.getLookupCache(), this.currentClasspathSnapshot, this.shrunkCurrentClasspathAgainstPreviousLookups, new ClasspathSnapshotBuildReporter(getReporter()));
                Unit unit = Unit.INSTANCE;
            } finally {
                reporter.endMeasure(gradleBuildTime);
            }
        }
    }
}
