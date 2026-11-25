package org.jetbrains.kotlin.ir.backend.js.ic;

import androidx.core.app.NotificationCompat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.ir.backend.js.SourceMapsInfo;
import org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.CompilationOutputs;
import org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.CompilationOutputsBuilt;
import org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.CompilationOutputsCached;
import org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.CrossModuleDependenciesResolver;
import org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.CrossModuleReferences;
import org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.IrModuleToJsTransformerKt;
import org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.JsGenerationGranularity;
import org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.JsIrModule;
import org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.JsIrModuleHeader;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.serialization.js.ModuleKind;

/* JADX INFO: compiled from: JsExecutableProducer.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0010\t\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001\u001eB5\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t\u0012\u0006\u0010\u000b\u001a\u00020\f¢\u0006\u0002\u0010\rJ\u0016\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\fJ(\u0010\u0015\u001a\u00020\u0011\"\b\b\u0000\u0010\u0016*\u00020\u00172\u0006\u0010\u0014\u001a\u00020\f2\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u0002H\u00160\u0019H\u0002J\u0010\u0010\u001a\u001a\u00020\u00112\u0006\u0010\u0014\u001a\u00020\fH\u0002J\u0012\u0010\u001b\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u001d0\u001cR\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/ic/JsExecutableProducer;", "", "mainModuleName", "", "moduleKind", "Lorg/jetbrains/kotlin/serialization/js/ModuleKind;", "sourceMapsInfo", "Lorg/jetbrains/kotlin/ir/backend/js/SourceMapsInfo;", "caches", "", "Lorg/jetbrains/kotlin/ir/backend/js/ic/ModuleArtifact;", "relativeRequirePath", "", "(Ljava/lang/String;Lorg/jetbrains/kotlin/serialization/js/ModuleKind;Lorg/jetbrains/kotlin/ir/backend/js/SourceMapsInfo;Ljava/util/List;Z)V", NotificationCompat.CATEGORY_STOPWATCH, "Lorg/jetbrains/kotlin/ir/backend/js/ic/StopwatchIC;", "buildExecutable", "Lorg/jetbrains/kotlin/ir/backend/js/ic/JsExecutableProducer$BuildResult;", "granularity", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsGenerationGranularity;", "outJsProgram", "buildMultiArtifactExecutable", "CacheInfo", "Lorg/jetbrains/kotlin/ir/backend/js/ic/JsMultiArtifactCache$CacheInfo;", "jsMultiArtifactCache", "Lorg/jetbrains/kotlin/ir/backend/js/ic/JsMultiArtifactCache;", "buildSingleModuleExecutable", "getStopwatchLaps", "", "", "BuildResult", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsExecutableProducer {
    private final List<ModuleArtifact> caches;
    private final String mainModuleName;
    private final ModuleKind moduleKind;
    private final boolean relativeRequirePath;
    private final SourceMapsInfo sourceMapsInfo;
    private final StopwatchIC stopwatch;

    /* JADX INFO: compiled from: JsExecutableProducer.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[JsGenerationGranularity.values().length];
            try {
                iArr[JsGenerationGranularity.WHOLE_PROGRAM.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[JsGenerationGranularity.PER_MODULE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[JsGenerationGranularity.PER_FILE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public JsExecutableProducer(String mainModuleName, ModuleKind moduleKind, SourceMapsInfo sourceMapsInfo, List<ModuleArtifact> caches, boolean z) {
        Intrinsics.checkNotNullParameter(mainModuleName, "mainModuleName");
        Intrinsics.checkNotNullParameter(moduleKind, "moduleKind");
        Intrinsics.checkNotNullParameter(caches, "caches");
        this.mainModuleName = mainModuleName;
        this.moduleKind = moduleKind;
        this.sourceMapsInfo = sourceMapsInfo;
        this.caches = caches;
        this.relativeRequirePath = z;
        this.stopwatch = new StopwatchIC();
    }

    /* JADX INFO: compiled from: JsExecutableProducer.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007J\t\u0010\f\u001a\u00020\u0003HÆ\u0003J\u000f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0003J#\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001J\t\u0010\u0014\u001a\u00020\u0006HÖ\u0001R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/ic/JsExecutableProducer$BuildResult;", "", "compilationOut", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/CompilationOutputs;", "buildModules", "", "", "(Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/CompilationOutputs;Ljava/util/List;)V", "getBuildModules", "()Ljava/util/List;", "getCompilationOut", "()Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/CompilationOutputs;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class BuildResult {
        private final List<String> buildModules;
        private final CompilationOutputs compilationOut;

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ BuildResult copy$default(BuildResult buildResult, CompilationOutputs compilationOutputs, List list, int i, Object obj) {
            if ((i & 1) != 0) {
                compilationOutputs = buildResult.compilationOut;
            }
            if ((i & 2) != 0) {
                list = buildResult.buildModules;
            }
            return buildResult.copy(compilationOutputs, list);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final CompilationOutputs getCompilationOut() {
            return this.compilationOut;
        }

        public final List<String> component2() {
            return this.buildModules;
        }

        public final BuildResult copy(CompilationOutputs compilationOut, List<String> buildModules) {
            Intrinsics.checkNotNullParameter(compilationOut, "compilationOut");
            Intrinsics.checkNotNullParameter(buildModules, "buildModules");
            return new BuildResult(compilationOut, buildModules);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof BuildResult)) {
                return false;
            }
            BuildResult buildResult = (BuildResult) other;
            return Intrinsics.areEqual(this.compilationOut, buildResult.compilationOut) && Intrinsics.areEqual(this.buildModules, buildResult.buildModules);
        }

        public int hashCode() {
            return (this.compilationOut.hashCode() * 31) + this.buildModules.hashCode();
        }

        public String toString() {
            return "BuildResult(compilationOut=" + this.compilationOut + ", buildModules=" + this.buildModules + ')';
        }

        public BuildResult(CompilationOutputs compilationOut, List<String> buildModules) {
            Intrinsics.checkNotNullParameter(compilationOut, "compilationOut");
            Intrinsics.checkNotNullParameter(buildModules, "buildModules");
            this.compilationOut = compilationOut;
            this.buildModules = buildModules;
        }

        public final List<String> getBuildModules() {
            return this.buildModules;
        }

        public final CompilationOutputs getCompilationOut() {
            return this.compilationOut;
        }
    }

    public final Map<String, Long> getStopwatchLaps() {
        Map mapCreateMapBuilder = MapsKt.createMapBuilder();
        Iterator<T> it = this.stopwatch.getLaps().iterator();
        while (it.hasNext()) {
            Pair pair = (Pair) it.next();
            Object first = pair.getFirst();
            long jLongValue = ((Number) pair.getSecond()).longValue();
            Long l = (Long) mapCreateMapBuilder.get(pair.getFirst());
            mapCreateMapBuilder.put(first, Long.valueOf(jLongValue + (l != null ? l.longValue() : 0L)));
        }
        return MapsKt.build(mapCreateMapBuilder);
    }

    public final BuildResult buildExecutable(JsGenerationGranularity granularity, boolean outJsProgram) {
        Intrinsics.checkNotNullParameter(granularity, "granularity");
        int i = WhenMappings.$EnumSwitchMapping$0[granularity.ordinal()];
        if (i == 1) {
            return buildSingleModuleExecutable(outJsProgram);
        }
        if (i == 2) {
            return buildMultiArtifactExecutable(outJsProgram, new JsPerModuleCache(this.moduleKind, this.caches));
        }
        if (i == 3) {
            return buildMultiArtifactExecutable(outJsProgram, new JsPerFileCache(this.caches));
        }
        throw new NoWhenBranchMatchedException();
    }

    private final BuildResult buildSingleModuleExecutable(boolean outJsProgram) {
        List<ModuleArtifact> list = this.caches;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(ModuleArtifact.loadJsIrModule$default((ModuleArtifact) it.next(), null, 1, null));
        }
        String str = this.mainModuleName;
        ModuleKind moduleKind = this.moduleKind;
        ArrayList arrayList2 = new ArrayList();
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            CollectionsKt.addAll(arrayList2, ((JsIrModule) it2.next()).getFragments());
        }
        return new BuildResult(IrModuleToJsTransformerKt.generateSingleWrappedModuleBody$default(str, moduleKind, arrayList2, this.sourceMapsInfo, true, null, outJsProgram, 32, null), CollectionsKt.listOf(this.mainModuleName));
    }

    private final <CacheInfo extends JsMultiArtifactCache.CacheInfo> BuildResult buildMultiArtifactExecutable(boolean outJsProgram, JsMultiArtifactCache<CacheInfo> jsMultiArtifactCache) {
        ArrayList arrayList = new ArrayList();
        this.stopwatch.startNext("JS code cache loading");
        List listLoadProgramHeadersFromCache = jsMultiArtifactCache.loadProgramHeadersFromCache();
        this.stopwatch.startNext("Cross module references resolving");
        ModuleKind moduleKind = this.moduleKind;
        List list = listLoadProgramHeadersFromCache;
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator it = list.iterator();
        while (it.hasNext()) {
            arrayList2.add(((JsMultiArtifactCache.CacheInfo) it.next()).getJsIrHeader());
        }
        Map<JsIrModuleHeader, CrossModuleReferences> mapResolveCrossModuleDependencies = new CrossModuleDependenciesResolver(moduleKind, arrayList2).resolveCrossModuleDependencies(this.relativeRequirePath);
        this.stopwatch.startNext("Loading JS IR modules with updated cross module references");
        jsMultiArtifactCache.loadRequiredJsIrModules(mapResolveCrossModuleDependencies);
        Pair<T, List<T>> mainModuleAndDependencies = jsMultiArtifactCache.getMainModuleAndDependencies(listLoadProgramHeadersFromCache);
        JsMultiArtifactCache.CacheInfo cacheInfo = (JsMultiArtifactCache.CacheInfo) mainModuleAndDependencies.component1();
        List list2 = (List) mainModuleAndDependencies.component2();
        CompilationOutputs compilationOutputsBuildMultiArtifactExecutable$compileModule = buildMultiArtifactExecutable$compileModule(cacheInfo, jsMultiArtifactCache, this, mapResolveCrossModuleDependencies, outJsProgram, arrayList, this.mainModuleName, true);
        List<JsMultiArtifactCache.CacheInfo> list3 = list2;
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list3, 10));
        for (JsMultiArtifactCache.CacheInfo cacheInfo2 : list3) {
            arrayList3.add(TuplesKt.to(cacheInfo2.getJsIrHeader().getExternalModuleName(), buildMultiArtifactExecutable$compileModule(cacheInfo2, jsMultiArtifactCache, this, mapResolveCrossModuleDependencies, outJsProgram, arrayList, cacheInfo2.getJsIrHeader().getExternalModuleName(), false)));
        }
        compilationOutputsBuildMultiArtifactExecutable$compileModule.setDependencies(arrayList3);
        StopwatchIC.stop$default(this.stopwatch, null, 1, null);
        return new BuildResult(compilationOutputsBuildMultiArtifactExecutable$compileModule, arrayList);
    }

    private static final <CacheInfo extends JsMultiArtifactCache.CacheInfo> CompilationOutputs buildMultiArtifactExecutable$compileModule(CacheInfo cacheinfo, JsMultiArtifactCache<CacheInfo> jsMultiArtifactCache, JsExecutableProducer jsExecutableProducer, Map<JsIrModuleHeader, CrossModuleReferences> map, boolean z, List<String> list, String str, boolean z2) {
        if (cacheinfo == null) {
            return jsMultiArtifactCache.mo7146fetchCompiledJsCodeForNullCacheInfo();
        }
        if (cacheinfo.getJsIrHeader().getAssociatedModule() == null) {
            jsExecutableProducer.stopwatch.startNext("Fetching cached JS code");
            CompilationOutputsCached compilationOutputsCachedFetchCompiledJsCode = jsMultiArtifactCache.fetchCompiledJsCode(cacheinfo);
            if (compilationOutputsCachedFetchCompiledJsCode != null) {
                return compilationOutputsCachedFetchCompiledJsCode;
            }
            jsExecutableProducer.stopwatch.startNext("Loading JS IR modules");
            cacheinfo.getJsIrHeader().setAssociatedModule(jsMultiArtifactCache.loadJsIrModule(cacheinfo));
        }
        jsExecutableProducer.stopwatch.startNext("Initializing JS imports");
        JsIrModule associatedModule = cacheinfo.getJsIrHeader().getAssociatedModule();
        if (associatedModule == null) {
            ICUtilsKt.m7122icError5T69zGA$default("can not load module " + str, null, null, 6, null);
            throw null;
        }
        CrossModuleReferences crossModuleReferences = map.get(cacheinfo.getJsIrHeader());
        if (crossModuleReferences == null) {
            ICUtilsKt.m7122icError5T69zGA$default("can not find cross references for module " + str, null, null, 6, null);
            throw null;
        }
        crossModuleReferences.initJsImportsForModule(associatedModule);
        jsExecutableProducer.stopwatch.startNext("Generating JS code");
        CompilationOutputsBuilt compilationOutputsBuiltGenerateSingleWrappedModuleBody = IrModuleToJsTransformerKt.generateSingleWrappedModuleBody(str, jsExecutableProducer.moduleKind, associatedModule.getFragments(), jsExecutableProducer.sourceMapsInfo, z2, crossModuleReferences, z);
        jsExecutableProducer.stopwatch.startNext("Committing compiled JS code");
        list.add(str);
        return jsMultiArtifactCache.commitCompiledJsCode(cacheinfo, compilationOutputsBuiltGenerateSingleWrappedModuleBody);
    }
}
