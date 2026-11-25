package org.jetbrains.kotlin.incremental;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.build.GeneratedFile;
import org.jetbrains.kotlin.build.report.BuildReporter;
import org.jetbrains.kotlin.build.report.ICReporterKt;
import org.jetbrains.kotlin.build.report.metrics.BuildAttribute;
import org.jetbrains.kotlin.build.report.metrics.GradleBuildPerformanceMetric;
import org.jetbrains.kotlin.build.report.metrics.GradleBuildTime;
import org.jetbrains.kotlin.cli.common.CommonCompilerPerformanceManager;
import org.jetbrains.kotlin.cli.common.ExitCode;
import org.jetbrains.kotlin.cli.common.arguments.CommonCompilerArguments;
import org.jetbrains.kotlin.cli.common.arguments.K2JSCompilerArguments;
import org.jetbrains.kotlin.cli.common.arguments.K2JSCompilerArgumentsKt;
import org.jetbrains.kotlin.cli.common.messages.MessageCollector;
import org.jetbrains.kotlin.cli.js.K2JSCompiler;
import org.jetbrains.kotlin.config.Services;
import org.jetbrains.kotlin.incremental.components.ExpectActualTracker;
import org.jetbrains.kotlin.incremental.components.LookupTracker;
import org.jetbrains.kotlin.incremental.js.IncrementalDataProvider;
import org.jetbrains.kotlin.incremental.js.IncrementalDataProviderFromCache;
import org.jetbrains.kotlin.incremental.js.IncrementalNextRoundChecker;
import org.jetbrains.kotlin.incremental.js.IncrementalResultsConsumer;
import org.jetbrains.kotlin.incremental.js.IncrementalResultsConsumerImpl;
import org.jetbrains.kotlin.incremental.js.TranslationResultValue;
import org.jetbrains.kotlin.incremental.multiproject.ModulesApiHistory;
import org.jetbrains.kotlin.incremental.storage.LookupSymbolKey;
import org.jetbrains.kotlin.library.metadata.KlibMetadataSerializerProtocol;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.protobuf.InvalidProtocolBufferException;
import org.jetbrains.kotlin.serialization.js.JsSerializerProtocol;

/* JADX INFO: compiled from: IncrementalJsCompilerRunner.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000¸\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0010\u001c\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001:\u0001CB[\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u0007\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\b\b\u0002\u0010\r\u001a\u00020\u000e\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u0010\u0012\b\b\u0002\u0010\u0011\u001a\u00020\u0010\u0012\b\b\u0002\u0010\u0012\u001a\u00020\u0010¢\u0006\u0002\u0010\u0013J,\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00050\u001a2\u0006\u0010\u001b\u001a\u00020\u00032\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001e0\u001d2\u0006\u0010\u001f\u001a\u00020 H\u0014J<\u0010!\u001a\u00020\"2\u0006\u0010\u001b\u001a\u00020\u00032\u0006\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020\u00022\u0006\u0010&\u001a\u00020'2\u0012\u0010(\u001a\u000e\u0012\u0004\u0012\u00020*\u0012\u0004\u0012\u00020+0)H\u0014J\u0018\u0010,\u001a\u00020\u00032\u0006\u0010-\u001a\u00020.2\u0006\u0010%\u001a\u00020\u0002H\u0014J\u0010\u0010/\u001a\u00020\u00052\u0006\u0010%\u001a\u00020\u0002H\u0014J>\u00100\u001a\u0002012\u0006\u0010%\u001a\u00020\u00022\u0006\u00102\u001a\u0002032\u0006\u00104\u001a\u0002052\u0006\u0010\u001b\u001a\u00020\u00032\f\u00106\u001a\b\u0012\u0004\u0012\u00020\u0005072\u0006\u00108\u001a\u00020\u0010H\u0014J^\u00109\u001a\u0014\u0012\u0004\u0012\u00020;\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050<0:2\f\u0010=\u001a\b\u0012\u0004\u0012\u00020\u00050\u001d2\u0006\u0010%\u001a\u00020\u00022\u0006\u0010\u001b\u001a\u00020\u00032\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010&\u001a\u00020'2\f\u0010>\u001a\b\u0012\u0004\u0012\u00020\u00050\u001d2\u0006\u00108\u001a\u00020\u0010H\u0014J.\u0010?\u001a\u00020@2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010\u001b\u001a\u00020\u00032\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001e0\u001d2\u0006\u0010A\u001a\u00020BH\u0014R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0014\u001a\u00020\u00108TX\u0094\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016R\u0014\u0010\u0017\u001a\u00020\u00108TX\u0094\u0004¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0016¨\u0006D"}, d2 = {"Lorg/jetbrains/kotlin/incremental/IncrementalJsCompilerRunner;", "Lorg/jetbrains/kotlin/incremental/IncrementalCompilerRunner;", "Lorg/jetbrains/kotlin/cli/common/arguments/K2JSCompilerArguments;", "Lorg/jetbrains/kotlin/incremental/IncrementalJsCachesManager;", "workingDir", "Ljava/io/File;", "reporter", "Lorg/jetbrains/kotlin/build/report/BuildReporter;", "Lorg/jetbrains/kotlin/build/report/metrics/GradleBuildTime;", "Lorg/jetbrains/kotlin/build/report/metrics/GradleBuildPerformanceMetric;", "buildHistoryFile", "modulesApiHistory", "Lorg/jetbrains/kotlin/incremental/multiproject/ModulesApiHistory;", "scopeExpansion", "Lorg/jetbrains/kotlin/incremental/CompileScopeExpansionMode;", "withAbiSnapshot", "", "preciseCompilationResultsBackup", "keepIncrementalCompilationCachesInMemory", "(Ljava/io/File;Lorg/jetbrains/kotlin/build/report/BuildReporter;Ljava/io/File;Lorg/jetbrains/kotlin/incremental/multiproject/ModulesApiHistory;Lorg/jetbrains/kotlin/incremental/CompileScopeExpansionMode;ZZZ)V", "shouldStoreFullFqNamesInLookupCache", "getShouldStoreFullFqNamesInLookupCache", "()Z", "shouldTrackChangesInLookupCache", "getShouldTrackChangesInLookupCache", "additionalDirtyFiles", "", "caches", "generatedFiles", "", "Lorg/jetbrains/kotlin/build/GeneratedFile;", "services", "Lorg/jetbrains/kotlin/config/Services;", "calculateSourcesToCompile", "Lorg/jetbrains/kotlin/incremental/IncrementalCompilerRunner$CompilationMode;", "changedFiles", "Lorg/jetbrains/kotlin/incremental/ChangedFiles$Known;", "args", "messageCollector", "Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;", "classpathAbiSnapshots", "", "", "Lorg/jetbrains/kotlin/incremental/AbiSnapshot;", "createCacheManager", "icContext", "Lorg/jetbrains/kotlin/incremental/IncrementalCompilationContext;", "destinationDir", "makeServices", "Lorg/jetbrains/kotlin/config/Services$Builder;", "lookupTracker", "Lorg/jetbrains/kotlin/incremental/components/LookupTracker;", "expectActualTracker", "Lorg/jetbrains/kotlin/incremental/components/ExpectActualTracker;", "dirtySources", "", "isIncremental", "runCompiler", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/cli/common/ExitCode;", "", "sourcesToCompile", "allSources", "updateCaches", "", "changesCollector", "Lorg/jetbrains/kotlin/incremental/ChangesCollector;", "IncrementalNextRoundCheckerImpl", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IncrementalJsCompilerRunner extends IncrementalCompilerRunner<K2JSCompilerArguments, IncrementalJsCachesManager> {
    private final ModulesApiHistory modulesApiHistory;
    private final CompileScopeExpansionMode scopeExpansion;

    /* JADX INFO: compiled from: IncrementalJsCompilerRunner.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[CompileScopeExpansionMode.values().length];
            try {
                iArr[CompileScopeExpansionMode.ALWAYS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[CompileScopeExpansionMode.NEVER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    @Override // org.jetbrains.kotlin.incremental.IncrementalCompilerRunner
    protected boolean getShouldTrackChangesInLookupCache() {
        return false;
    }

    @Override // org.jetbrains.kotlin.incremental.IncrementalCompilerRunner
    public /* bridge */ /* synthetic */ Iterable additionalDirtyFiles(IncrementalCachesManager incrementalCachesManager, List list, Services services) {
        return additionalDirtyFiles((IncrementalJsCachesManager) incrementalCachesManager, (List<? extends GeneratedFile>) list, services);
    }

    @Override // org.jetbrains.kotlin.incremental.IncrementalCompilerRunner
    public /* bridge */ /* synthetic */ IncrementalCompilerRunner.CompilationMode calculateSourcesToCompile(IncrementalCachesManager incrementalCachesManager, ChangedFiles.Known known, CommonCompilerArguments commonCompilerArguments, MessageCollector messageCollector, Map map) {
        return calculateSourcesToCompile((IncrementalJsCachesManager) incrementalCachesManager, known, (K2JSCompilerArguments) commonCompilerArguments, messageCollector, (Map<String, ? extends AbiSnapshot>) map);
    }

    @Override // org.jetbrains.kotlin.incremental.IncrementalCompilerRunner
    public /* bridge */ /* synthetic */ Services.Builder makeServices(CommonCompilerArguments commonCompilerArguments, LookupTracker lookupTracker, ExpectActualTracker expectActualTracker, IncrementalCachesManager incrementalCachesManager, Set set, boolean z) {
        return makeServices((K2JSCompilerArguments) commonCompilerArguments, lookupTracker, expectActualTracker, (IncrementalJsCachesManager) incrementalCachesManager, (Set<? extends File>) set, z);
    }

    @Override // org.jetbrains.kotlin.incremental.IncrementalCompilerRunner
    public /* bridge */ /* synthetic */ Pair runCompiler(List list, CommonCompilerArguments commonCompilerArguments, IncrementalCachesManager incrementalCachesManager, Services services, MessageCollector messageCollector, List list2, boolean z) {
        return runCompiler((List<? extends File>) list, (K2JSCompilerArguments) commonCompilerArguments, (IncrementalJsCachesManager) incrementalCachesManager, services, messageCollector, (List<? extends File>) list2, z);
    }

    @Override // org.jetbrains.kotlin.incremental.IncrementalCompilerRunner
    public /* bridge */ /* synthetic */ void updateCaches(Services services, IncrementalCachesManager incrementalCachesManager, List list, ChangesCollector changesCollector) throws InvalidProtocolBufferException {
        updateCaches(services, (IncrementalJsCachesManager) incrementalCachesManager, (List<? extends GeneratedFile>) list, changesCollector);
    }

    public /* synthetic */ IncrementalJsCompilerRunner(File file, BuildReporter buildReporter, File file2, ModulesApiHistory modulesApiHistory, CompileScopeExpansionMode compileScopeExpansionMode, boolean z, boolean z2, boolean z3, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(file, buildReporter, file2, modulesApiHistory, (i & 16) != 0 ? CompileScopeExpansionMode.NEVER : compileScopeExpansionMode, (i & 32) != 0 ? false : z, (i & 64) != 0 ? false : z2, (i & 128) != 0 ? false : z3);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IncrementalJsCompilerRunner(File workingDir, BuildReporter<GradleBuildTime, GradleBuildPerformanceMetric> reporter, File file, ModulesApiHistory modulesApiHistory, CompileScopeExpansionMode scopeExpansion, boolean z, boolean z2, boolean z3) {
        super(workingDir, "caches-js", reporter, file, null, z, z2, z3);
        Intrinsics.checkNotNullParameter(workingDir, "workingDir");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        Intrinsics.checkNotNullParameter(modulesApiHistory, "modulesApiHistory");
        Intrinsics.checkNotNullParameter(scopeExpansion, "scopeExpansion");
        this.modulesApiHistory = modulesApiHistory;
        this.scopeExpansion = scopeExpansion;
    }

    @Override // org.jetbrains.kotlin.incremental.IncrementalCompilerRunner
    protected boolean getShouldStoreFullFqNamesInLookupCache() {
        return getWithAbiSnapshot();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.incremental.IncrementalCompilerRunner
    public IncrementalJsCachesManager createCacheManager(IncrementalCompilationContext icContext, K2JSCompilerArguments args) {
        Intrinsics.checkNotNullParameter(icContext, "icContext");
        Intrinsics.checkNotNullParameter(args, "args");
        return new IncrementalJsCachesManager(icContext, !K2JSCompilerArgumentsKt.isIrBackendEnabled(args) ? JsSerializerProtocol.INSTANCE : KlibMetadataSerializerProtocol.INSTANCE, getCacheDirectory());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.incremental.IncrementalCompilerRunner
    public File destinationDir(K2JSCompilerArguments args) {
        Intrinsics.checkNotNullParameter(args, "args");
        if (K2JSCompilerArgumentsKt.isIrBackendEnabled(args)) {
            String outputDir = args.getOutputDir();
            Intrinsics.checkNotNull(outputDir);
            return new File(outputDir);
        }
        String outputFile = args.getOutputFile();
        Intrinsics.checkNotNull(outputFile);
        File parentFile = new File(outputFile).getParentFile();
        Intrinsics.checkNotNullExpressionValue(parentFile, "getParentFile(...)");
        return parentFile;
    }

    protected IncrementalCompilerRunner.CompilationMode calculateSourcesToCompile(IncrementalJsCachesManager caches, ChangedFiles.Known changedFiles, K2JSCompilerArguments args, MessageCollector messageCollector, Map<String, ? extends AbiSnapshot> classpathAbiSnapshots) {
        Intrinsics.checkNotNullParameter(caches, "caches");
        Intrinsics.checkNotNullParameter(changedFiles, "changedFiles");
        Intrinsics.checkNotNullParameter(args, "args");
        Intrinsics.checkNotNullParameter(messageCollector, "messageCollector");
        Intrinsics.checkNotNullParameter(classpathAbiSnapshots, "classpathAbiSnapshots");
        if (getBuildHistoryFile() == null) {
            throw new IllegalStateException("The build is configured to use the build-history based IC approach, but doesn't specify the buildHistoryFile".toString());
        }
        if (!getWithAbiSnapshot() && !getBuildHistoryFile().isFile()) {
            return new IncrementalCompilerRunner.CompilationMode.Rebuild(BuildAttribute.NO_BUILD_HISTORY);
        }
        BuildInfo buildInfo = BuildInfo.INSTANCE.read(getLastBuildInfoFile(), messageCollector);
        if (buildInfo == null) {
            return new IncrementalCompilerRunner.CompilationMode.Rebuild(BuildAttribute.INVALID_LAST_BUILD_INFO);
        }
        IncrementalJsCachesManager incrementalJsCachesManager = caches;
        DirtyFilesContainer dirtyFilesContainer = new DirtyFilesContainer(incrementalJsCachesManager, getReporter(), getKotlinSourceFilesExtensions());
        initDirtyFiles(dirtyFilesContainer, changedFiles);
        String libraries = args.getLibraries();
        if (libraries == null) {
            libraries = "";
        }
        String str = libraries;
        String pathSeparator = File.pathSeparator;
        Intrinsics.checkNotNullExpressionValue(pathSeparator, "pathSeparator");
        List listSplit$default = StringsKt.split$default((CharSequence) str, new String[]{pathSeparator}, false, 0, 6, (Object) null);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listSplit$default, 10));
        Iterator it = listSplit$default.iterator();
        while (it.hasNext()) {
            arrayList.add(new File((String) it.next()));
        }
        ArrayList arrayList2 = arrayList;
        ModulesApiHistory modulesApiHistory = this.modulesApiHistory;
        BuildReporter<GradleBuildTime, GradleBuildPerformanceMetric> reporter = getReporter();
        Map mapEmptyMap = MapsKt.emptyMap();
        IncrementalJsCache platformCache = caches.getPlatformCache();
        Collection<LookupSymbolKey> lookupSymbols = caches.getLookupCache().getLookupSymbols();
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(lookupSymbols, 10));
        for (LookupSymbolKey lookupSymbolKey : lookupSymbols) {
            arrayList3.add(StringsKt.isBlank(lookupSymbolKey.getScope()) ? lookupSymbolKey.getName() : lookupSymbolKey.getScope());
        }
        final ChangesEither classpathChanges = ChangesDetectionUtilsKt.getClasspathChanges(arrayList2, changedFiles, buildInfo, modulesApiHistory, reporter, mapEmptyMap, false, platformCache, CollectionsKt.distinct(arrayList3));
        if (classpathChanges instanceof ChangesEither.Unknown) {
            ICReporterKt.info(getReporter(), new Function0<String>() { // from class: org.jetbrains.kotlin.incremental.IncrementalJsCompilerRunner.calculateSourcesToCompile.1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final String invoke() {
                    return "Could not get classpath's changes: " + ((ChangesEither.Unknown) classpathChanges).getReason();
                }
            });
            return new IncrementalCompilerRunner.CompilationMode.Rebuild(((ChangesEither.Unknown) classpathChanges).getReason());
        }
        if (classpathChanges instanceof ChangesEither.Known) {
            ChangesEither.Known known = (ChangesEither.Known) classpathChanges;
            dirtyFilesContainer.addByDirtySymbols(known.getLookupSymbols());
            dirtyFilesContainer.addByDirtyClasses(known.getFqNames());
        }
        DirtyData removedClassesChanges = getRemovedClassesChanges(incrementalJsCachesManager, changedFiles);
        dirtyFilesContainer.addByDirtySymbols(removedClassesChanges.getDirtyLookupSymbols());
        dirtyFilesContainer.addByDirtyClasses(removedClassesChanges.getDirtyClassesFqNames());
        dirtyFilesContainer.addByDirtyClasses(removedClassesChanges.getDirtyClassesFqNamesForceRecompile());
        if (dirtyFilesContainer.isEmpty() && !changedFiles.getRemoved().isEmpty()) {
            return new IncrementalCompilerRunner.CompilationMode.Rebuild(BuildAttribute.DEP_CHANGE_REMOVED_ENTRY);
        }
        return new IncrementalCompilerRunner.CompilationMode.Incremental(dirtyFilesContainer);
    }

    protected Services.Builder makeServices(K2JSCompilerArguments args, LookupTracker lookupTracker, ExpectActualTracker expectActualTracker, IncrementalJsCachesManager caches, Set<? extends File> dirtySources, boolean isIncremental) {
        Intrinsics.checkNotNullParameter(args, "args");
        Intrinsics.checkNotNullParameter(lookupTracker, "lookupTracker");
        Intrinsics.checkNotNullParameter(expectActualTracker, "expectActualTracker");
        Intrinsics.checkNotNullParameter(caches, "caches");
        Intrinsics.checkNotNullParameter(dirtySources, "dirtySources");
        Services.Builder builderMakeServices = super.makeServices(args, lookupTracker, expectActualTracker, caches, dirtySources, isIncremental);
        if (isIncremental) {
            if (this.scopeExpansion == CompileScopeExpansionMode.ALWAYS) {
                builderMakeServices.register(IncrementalNextRoundChecker.class, new IncrementalNextRoundCheckerImpl(this, caches, dirtySources));
            }
            builderMakeServices.register(IncrementalDataProvider.class, new IncrementalDataProviderFromCache(caches.getPlatformCache()));
        }
        builderMakeServices.register(IncrementalResultsConsumer.class, new IncrementalResultsConsumerImpl());
        return builderMakeServices;
    }

    protected void updateCaches(Services services, IncrementalJsCachesManager caches, List<? extends GeneratedFile> generatedFiles, ChangesCollector changesCollector) throws InvalidProtocolBufferException {
        Intrinsics.checkNotNullParameter(services, "services");
        Intrinsics.checkNotNullParameter(caches, "caches");
        Intrinsics.checkNotNullParameter(generatedFiles, "generatedFiles");
        Intrinsics.checkNotNullParameter(changesCollector, "changesCollector");
        Object obj = services.get(IncrementalResultsConsumer.class);
        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type org.jetbrains.kotlin.incremental.js.IncrementalResultsConsumerImpl");
        IncrementalResultsConsumerImpl incrementalResultsConsumerImpl = (IncrementalResultsConsumerImpl) obj;
        IncrementalJsCache platformCache = caches.getPlatformCache();
        platformCache.setHeader(incrementalResultsConsumerImpl.getHeaderMetadata());
        platformCache.compareAndUpdate(incrementalResultsConsumerImpl, changesCollector);
        platformCache.clearCacheForRemovedClasses(changesCollector);
    }

    protected Pair<ExitCode, Collection<File>> runCompiler(List<? extends File> sourcesToCompile, K2JSCompilerArguments args, IncrementalJsCachesManager caches, Services services, MessageCollector messageCollector, List<? extends File> allSources, boolean isIncremental) {
        Intrinsics.checkNotNullParameter(sourcesToCompile, "sourcesToCompile");
        Intrinsics.checkNotNullParameter(args, "args");
        Intrinsics.checkNotNullParameter(caches, "caches");
        Intrinsics.checkNotNullParameter(services, "services");
        Intrinsics.checkNotNullParameter(messageCollector, "messageCollector");
        Intrinsics.checkNotNullParameter(allSources, "allSources");
        List<String> freeArgs = args.getFreeArgs();
        K2JSCompiler k2JSCompiler = new K2JSCompiler();
        try {
            List<String> freeArgs2 = args.getFreeArgs();
            List<? extends File> list = sourcesToCompile;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(((File) it.next()).getAbsolutePath());
            }
            args.setFreeArgs(CollectionsKt.plus((Collection) freeArgs2, (Iterable) arrayList));
            return TuplesKt.to(k2JSCompiler.exec(messageCollector, services, args), sourcesToCompile);
        } finally {
            args.setFreeArgs(freeArgs);
            CommonCompilerPerformanceManager defaultPerformanceManager = k2JSCompiler.getDefaultPerformanceManager();
            Intrinsics.checkNotNullExpressionValue(defaultPerformanceManager, "<get-defaultPerformanceManager>(...)");
            reportPerformanceData(defaultPerformanceManager);
        }
    }

    protected Iterable<File> additionalDirtyFiles(IncrementalJsCachesManager caches, List<? extends GeneratedFile> generatedFiles, Services services) {
        HashSet<File> newDirtySources;
        Intrinsics.checkNotNullParameter(caches, "caches");
        Intrinsics.checkNotNullParameter(generatedFiles, "generatedFiles");
        Intrinsics.checkNotNullParameter(services, "services");
        int i = WhenMappings.$EnumSwitchMapping$0[this.scopeExpansion.ordinal()];
        if (i == 1) {
            Object obj = services.get(IncrementalNextRoundChecker.class);
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type org.jetbrains.kotlin.incremental.IncrementalJsCompilerRunner.IncrementalNextRoundCheckerImpl");
            newDirtySources = ((IncrementalNextRoundCheckerImpl) obj).getNewDirtySources();
        } else {
            if (i != 2) {
                throw new NoWhenBranchMatchedException();
            }
            newDirtySources = SetsKt.emptySet();
        }
        return SetsKt.plus(newDirtySources, (Iterable) super.additionalDirtyFiles(caches, generatedFiles, services));
    }

    /* JADX INFO: compiled from: IncrementalJsCompilerRunner.kt */
    @Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\b\u0086\u0004\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007J\u0018\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00062\u0006\u0010\u0016\u001a\u00020\tH\u0016J\b\u0010\u0017\u001a\u00020\u0018H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R!\u0010\n\u001a\u0012\u0012\u0004\u0012\u00020\u00060\u000bj\b\u0012\u0004\u0012\u00020\u0006`\f¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R*\u0010\u000f\u001a\u001e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00110\u0010j\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0011`\u0012X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/incremental/IncrementalJsCompilerRunner$IncrementalNextRoundCheckerImpl;", "Lorg/jetbrains/kotlin/incremental/js/IncrementalNextRoundChecker;", "caches", "Lorg/jetbrains/kotlin/incremental/IncrementalJsCachesManager;", "sourcesToCompile", "", "Ljava/io/File;", "(Lorg/jetbrains/kotlin/incremental/IncrementalJsCompilerRunner;Lorg/jetbrains/kotlin/incremental/IncrementalJsCachesManager;Ljava/util/Set;)V", "emptyByteArray", "", "newDirtySources", "Ljava/util/HashSet;", "Lkotlin/collections/HashSet;", "getNewDirtySources", "()Ljava/util/HashSet;", "translatedFiles", "Ljava/util/HashMap;", "Lorg/jetbrains/kotlin/incremental/js/TranslationResultValue;", "Lkotlin/collections/HashMap;", "checkProtoChanges", "", "sourceFile", "packagePartMetadata", "shouldGoToNextRound", "", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public final class IncrementalNextRoundCheckerImpl implements IncrementalNextRoundChecker {
        private final IncrementalJsCachesManager caches;
        private final byte[] emptyByteArray;
        private final HashSet<File> newDirtySources;
        private final Set<File> sourcesToCompile;
        final /* synthetic */ IncrementalJsCompilerRunner this$0;
        private final HashMap<File, TranslationResultValue> translatedFiles;

        /* JADX WARN: Multi-variable type inference failed */
        public IncrementalNextRoundCheckerImpl(IncrementalJsCompilerRunner incrementalJsCompilerRunner, IncrementalJsCachesManager caches, Set<? extends File> sourcesToCompile) {
            Intrinsics.checkNotNullParameter(caches, "caches");
            Intrinsics.checkNotNullParameter(sourcesToCompile, "sourcesToCompile");
            this.this$0 = incrementalJsCompilerRunner;
            this.caches = caches;
            this.sourcesToCompile = sourcesToCompile;
            this.newDirtySources = new HashSet<>();
            this.emptyByteArray = new byte[0];
            this.translatedFiles = new HashMap<>();
        }

        public final HashSet<File> getNewDirtySources() {
            return this.newDirtySources;
        }

        @Override // org.jetbrains.kotlin.incremental.js.IncrementalNextRoundChecker
        public void checkProtoChanges(File sourceFile, byte[] packagePartMetadata) {
            Intrinsics.checkNotNullParameter(sourceFile, "sourceFile");
            Intrinsics.checkNotNullParameter(packagePartMetadata, "packagePartMetadata");
            HashMap<File, TranslationResultValue> map = this.translatedFiles;
            byte[] bArr = this.emptyByteArray;
            map.put(sourceFile, new TranslationResultValue(packagePartMetadata, bArr, bArr));
        }

        @Override // org.jetbrains.kotlin.incremental.js.IncrementalNextRoundChecker
        public boolean shouldGoToNextRound() throws InvalidProtocolBufferException {
            ChangesCollector changesCollector = new ChangesCollector();
            this.caches.getPlatformCache().compare(this.translatedFiles, changesCollector);
            DirtyData changedAndImpactedSymbols = BuildUtilKt.getChangedAndImpactedSymbols(changesCollector, CollectionsKt.listOf(this.caches.getPlatformCache()), this.this$0.getReporter());
            Collection<LookupSymbol> collectionComponent1 = changedAndImpactedSymbols.component1();
            Collection<FqName> collectionComponent2 = changedAndImpactedSymbols.component2();
            this.newDirtySources.addAll(BuildUtilKt.mapLookupSymbolsToFiles(this.caches.getLookupCache(), collectionComponent1, this.this$0.getReporter(), this.sourcesToCompile));
            this.newDirtySources.addAll(BuildUtilKt.mapClassesFqNamesToFiles(CollectionsKt.listOf(this.caches.getPlatformCache()), collectionComponent2, this.this$0.getReporter(), this.sourcesToCompile));
            return !this.newDirtySources.isEmpty();
        }
    }
}
