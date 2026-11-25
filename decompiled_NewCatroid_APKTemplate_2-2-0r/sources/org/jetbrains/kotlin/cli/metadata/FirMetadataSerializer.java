package org.jetbrains.kotlin.cli.metadata;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtSourceFile;
import org.jetbrains.kotlin.analyzer.common.CommonPlatformAnalyzerServices;
import org.jetbrains.kotlin.backend.common.CommonKLibResolver;
import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys;
import org.jetbrains.kotlin.cli.common.CommonCompilerPerformanceManager;
import org.jetbrains.kotlin.cli.common.FirSessionConstructionUtilsKt;
import org.jetbrains.kotlin.cli.common.GroupedKtSources;
import org.jetbrains.kotlin.cli.common.GroupedKtSourcesKt;
import org.jetbrains.kotlin.cli.common.SessionWithSources;
import org.jetbrains.kotlin.cli.common.fir.FirDiagnosticsCompilerResultsReporter;
import org.jetbrains.kotlin.cli.common.messages.MessageCollector;
import org.jetbrains.kotlin.cli.jvm.compiler.CliCompilerUtilsKt;
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment;
import org.jetbrains.kotlin.cli.jvm.compiler.VfsBasedProjectEnvironment;
import org.jetbrains.kotlin.cli.jvm.compiler.VfsBasedProjectEnvironmentKt;
import org.jetbrains.kotlin.cli.jvm.compiler.pipeline.CompilerPipelineKt;
import org.jetbrains.kotlin.cli.jvm.config.JvmClasspathRoot;
import org.jetbrains.kotlin.cli.jvm.config.JvmContentRootsKt;
import org.jetbrains.kotlin.cli.jvm.config.K2MetadataConfigurationKeys;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileManager;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileSystem;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.config.CommonConfigurationKeys;
import org.jetbrains.kotlin.config.CommonConfigurationKeysKt;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.config.JVMConfigurationKeys;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporterFactory;
import org.jetbrains.kotlin.diagnostics.impl.PendingDiagnosticsCollectorWithSuppress;
import org.jetbrains.kotlin.fir.BinaryModuleData;
import org.jetbrains.kotlin.fir.DependencyListForCliModule;
import org.jetbrains.kotlin.fir.FirModuleDataKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.UtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.extensions.FirExtensionRegistrar;
import org.jetbrains.kotlin.fir.pipeline.FirUtilsKt;
import org.jetbrains.kotlin.fir.pipeline.ModuleCompilerAnalyzedOutput;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.serialization.FirKLibSerializerExtension;
import org.jetbrains.kotlin.fir.serialization.FirKlibSerializationKt;
import org.jetbrains.kotlin.fir.session.IncrementalCompilationContext;
import org.jetbrains.kotlin.fir.session.environment.AbstractProjectEnvironment;
import org.jetbrains.kotlin.fir.session.environment.AbstractProjectFileSearchScope;
import org.jetbrains.kotlin.library.SerializedMetadata;
import org.jetbrains.kotlin.library.metadata.KlibMetadataProtoBuf;
import org.jetbrains.kotlin.library.metadata.resolver.KotlinLibraryResolveResult;
import org.jetbrains.kotlin.load.kotlin.PackagePartProvider;
import org.jetbrains.kotlin.load.kotlin.incremental.components.IncrementalCompilationComponents;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.modules.Module;
import org.jetbrains.kotlin.modules.TargetIdKt;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.platform.CommonPlatforms;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.util.DummyLogger;

/* JADX INFO: compiled from: FirMetadataSerializer.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u00020\u0001B\u0015\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0010\u0010\t\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u0002H\u0014J\u001e\u0010\n\u001a\u00020\u000b2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00030\u00022\u0006\u0010\r\u001a\u00020\u000eH\u0014¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/cli/metadata/FirMetadataSerializer;", "Lorg/jetbrains/kotlin/cli/metadata/AbstractMetadataSerializer;", "", "Lorg/jetbrains/kotlin/fir/pipeline/ModuleCompilerAnalyzedOutput;", "configuration", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "environment", "Lorg/jetbrains/kotlin/cli/jvm/compiler/KotlinCoreEnvironment;", "(Lorg/jetbrains/kotlin/config/CompilerConfiguration;Lorg/jetbrains/kotlin/cli/jvm/compiler/KotlinCoreEnvironment;)V", "analyze", "serialize", "", "analysisResult", "destDir", "Ljava/io/File;", "cli"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirMetadataSerializer extends AbstractMetadataSerializer<List<? extends ModuleCompilerAnalyzedOutput>> {
    @Override // org.jetbrains.kotlin.cli.metadata.AbstractMetadataSerializer
    public /* bridge */ /* synthetic */ void serialize(List<? extends ModuleCompilerAnalyzedOutput> list, File file) throws Throwable {
        serialize2((List<ModuleCompilerAnalyzedOutput>) list, file);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FirMetadataSerializer(CompilerConfiguration configuration, KotlinCoreEnvironment environment) {
        super(configuration, environment, null, 4, null);
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        Intrinsics.checkNotNullParameter(environment, "environment");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.cli.metadata.AbstractMetadataSerializer
    public List<? extends ModuleCompilerAnalyzedOutput> analyze() {
        ArrayList arrayListEmptyList;
        ArrayList arrayList;
        ArrayList arrayList2;
        AbstractProjectFileSearchScope precompiledBinariesFileScope;
        ArrayList arrayList3;
        AbstractProjectFileSearchScope abstractProjectFileSearchScopeMinus;
        final AbstractProjectFileSearchScope abstractProjectFileSearchScope;
        Object notNull = getEnvironment().getConfiguration().getNotNull(CLIConfigurationKeys.PERF_MANAGER);
        Intrinsics.checkNotNullExpressionValue(notNull, "getNotNull(...)");
        CommonCompilerPerformanceManager commonCompilerPerformanceManager = (CommonCompilerPerformanceManager) notNull;
        commonCompilerPerformanceManager.notifyAnalysisStarted();
        final CompilerConfiguration configuration = getEnvironment().getConfiguration();
        Object notNull2 = configuration.getNotNull(CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY);
        Intrinsics.checkNotNullExpressionValue(notNull2, "getNotNull(...)");
        MessageCollector messageCollector = (MessageCollector) notNull2;
        Name nameSpecial = Name.special("<" + ((String) configuration.getNotNull(CommonConfigurationKeys.MODULE_NAME)) + '>');
        Intrinsics.checkNotNullExpressionValue(nameSpecial, "special(...)");
        boolean z = configuration.getBoolean(CommonConfigurationKeys.USE_LIGHT_TREE);
        BinaryModuleData binaryModuleDataInitialize = BinaryModuleData.INSTANCE.initialize(nameSpecial, CommonPlatforms.INSTANCE.getDefaultCommonPlatform(), CommonPlatformAnalyzerServices.INSTANCE);
        DependencyListForCliModule.Companion companion = DependencyListForCliModule.INSTANCE;
        DependencyListForCliModule.Builder builder = new DependencyListForCliModule.Builder(binaryModuleDataInitialize);
        List list = (List) configuration.get(K2MetadataConfigurationKeys.INSTANCE.getREFINES_PATHS());
        if (list != null) {
            Intrinsics.checkNotNull(list);
            List list2 = list;
            ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
            Iterator it = list2.iterator();
            while (it.hasNext()) {
                arrayList4.add(new File((String) it.next()));
            }
            arrayListEmptyList = arrayList4;
        } else {
            arrayListEmptyList = null;
        }
        if (arrayListEmptyList == null) {
            arrayListEmptyList = CollectionsKt.emptyList();
        }
        List<File> jvmClasspathRoots = JvmContentRootsKt.getJvmClasspathRoots(configuration);
        ArrayList arrayList5 = new ArrayList();
        for (Object obj : jvmClasspathRoots) {
            if (!arrayListEmptyList.contains((File) obj)) {
                arrayList5.add(obj);
            }
        }
        ArrayList arrayList6 = arrayList5;
        ArrayList arrayList7 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList6, 10));
        Iterator it2 = arrayList6.iterator();
        while (it2.hasNext()) {
            arrayList7.add(((File) it2.next()).toPath());
        }
        builder.dependencies(arrayList7);
        List<File> jvmModularRoots = JvmContentRootsKt.getJvmModularRoots(configuration);
        ArrayList arrayList8 = new ArrayList(CollectionsKt.collectionSizeOrDefault(jvmModularRoots, 10));
        Iterator<T> it3 = jvmModularRoots.iterator();
        while (it3.hasNext()) {
            arrayList8.add(((File) it3.next()).toPath());
        }
        builder.dependencies(arrayList8);
        List listEmptyList = (List) configuration.get(K2MetadataConfigurationKeys.INSTANCE.getFRIEND_PATHS());
        if (listEmptyList == null) {
            listEmptyList = CollectionsKt.emptyList();
        } else {
            Intrinsics.checkNotNull(listEmptyList);
        }
        builder.friendDependenciesString(listEmptyList);
        List list3 = arrayListEmptyList;
        ArrayList arrayList9 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list3, 10));
        Iterator it4 = list3.iterator();
        while (it4.hasNext()) {
            arrayList9.add(((File) it4.next()).toPath());
        }
        builder.dependsOnDependencies(arrayList9);
        DependencyListForCliModule dependencyListForCliModuleBuild = builder.build();
        PendingDiagnosticsCollectorWithSuppress pendingDiagnosticsCollectorWithSuppressCreatePendingReporter$default = DiagnosticReporterFactory.createPendingReporter$default(DiagnosticReporterFactory.INSTANCE, null, 1, null);
        List listEmptyList2 = (List) configuration.get(CLIConfigurationKeys.CONTENT_ROOTS);
        if (listEmptyList2 == null) {
            listEmptyList2 = CollectionsKt.emptyList();
        }
        ArrayList arrayList10 = new ArrayList();
        for (Object obj2 : listEmptyList2) {
            if (obj2 instanceof JvmClasspathRoot) {
                arrayList10.add(obj2);
            }
        }
        ArrayList arrayList11 = new ArrayList();
        for (Object obj3 : arrayList10) {
            JvmClasspathRoot jvmClasspathRoot = (JvmClasspathRoot) obj3;
            if (jvmClasspathRoot.getFile().isDirectory() || Intrinsics.areEqual(FilesKt.getExtension(jvmClasspathRoot.getFile()), "klib")) {
                arrayList11.add(obj3);
            }
        }
        ArrayList arrayList12 = arrayList11;
        ArrayList arrayList13 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList12, 10));
        Iterator it5 = arrayList12.iterator();
        while (it5.hasNext()) {
            arrayList13.add(((JvmClasspathRoot) it5.next()).getFile().getAbsolutePath());
        }
        List fullResolvedList$default = KotlinLibraryResolveResult.getFullResolvedList$default(CommonKLibResolver.resolve$default(CommonKLibResolver.INSTANCE, arrayList13, DummyLogger.INSTANCE, null, 4, null), null, 1, null);
        if (z) {
            AbstractProjectEnvironment abstractProjectEnvironment = VfsBasedProjectEnvironmentKt.toAbstractProjectEnvironment(getEnvironment());
            Intrinsics.checkNotNull(abstractProjectEnvironment, "null cannot be cast to non-null type org.jetbrains.kotlin.cli.jvm.compiler.VfsBasedProjectEnvironment");
            final VfsBasedProjectEnvironment vfsBasedProjectEnvironment = (VfsBasedProjectEnvironment) abstractProjectEnvironment;
            AbstractProjectFileSearchScope searchScopeForProjectLibraries = vfsBasedProjectEnvironment.getSearchScopeForProjectLibraries();
            GroupedKtSources groupedKtSourcesCollectSources = GroupedKtSourcesKt.collectSources(configuration, vfsBasedProjectEnvironment, messageCollector);
            List<FirExtensionRegistrar> instances = FirExtensionRegistrar.INSTANCE.getInstances(vfsBasedProjectEnvironment.getProject());
            List list4 = CollectionsKt.toList(CollectionsKt.plus((Collection) groupedKtSourcesCollectSources.getCommonSources(), (Iterable) groupedKtSourcesCollectSources.getPlatformSources()));
            VfsBasedProjectEnvironment vfsBasedProjectEnvironment2 = vfsBasedProjectEnvironment;
            AbstractProjectFileSearchScope abstractProjectFileSearchScopeCreateIncrementalCompilationScope = CompilerPipelineKt.createIncrementalCompilationScope(configuration, vfsBasedProjectEnvironment2, null);
            if (abstractProjectFileSearchScopeCreateIncrementalCompilationScope != null) {
                abstractProjectFileSearchScopeMinus = searchScopeForProjectLibraries.minus(abstractProjectFileSearchScopeCreateIncrementalCompilationScope);
                abstractProjectFileSearchScope = abstractProjectFileSearchScopeCreateIncrementalCompilationScope;
            } else {
                abstractProjectFileSearchScopeMinus = searchScopeForProjectLibraries;
                abstractProjectFileSearchScope = null;
            }
            List<SessionWithSources> listPrepareCommonSessions = FirSessionConstructionUtilsKt.prepareCommonSessions(list4, configuration, vfsBasedProjectEnvironment2, nameSpecial, instances, abstractProjectFileSearchScopeMinus, dependencyListForCliModuleBuild, fullResolvedList$default, FirSessionConstructionUtilsKt.isCommonSourceForLt(groupedKtSourcesCollectSources), FirSessionConstructionUtilsKt.getFileBelongsToModuleForLt(groupedKtSourcesCollectSources), new Function1<List<? extends KtSourceFile>, IncrementalCompilationContext>() { // from class: org.jetbrains.kotlin.cli.metadata.FirMetadataSerializer$analyze$outputs$sessionsWithSources$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final IncrementalCompilationContext invoke(List<? extends KtSourceFile> files) {
                    Intrinsics.checkNotNullParameter(files, "files");
                    CompilerConfiguration compilerConfiguration = configuration;
                    VfsBasedProjectEnvironment vfsBasedProjectEnvironment3 = vfsBasedProjectEnvironment;
                    return CompilerPipelineKt.createContextForIncrementalCompilation(compilerConfiguration, vfsBasedProjectEnvironment3, AbstractProjectEnvironment.getSearchScopeBySourceFiles$default(vfsBasedProjectEnvironment3, files, false, 2, null), CollectionsKt.emptyList(), abstractProjectFileSearchScope);
                }
            });
            ArrayList arrayList14 = new ArrayList(CollectionsKt.collectionSizeOrDefault(listPrepareCommonSessions, 10));
            for (SessionWithSources sessionWithSources : listPrepareCommonSessions) {
                FirSession session = sessionWithSources.getSession();
                arrayList14.add(FirUtilsKt.resolveAndCheckFir(session, FirUtilsKt.buildFirViaLightTree(session, sessionWithSources.component2(), pendingDiagnosticsCollectorWithSuppressCreatePendingReporter$default, new FirMetadataSerializer$analyze$outputs$1$firFiles$1(commonCompilerPerformanceManager)), pendingDiagnosticsCollectorWithSuppressCreatePendingReporter$default));
            }
            arrayList2 = arrayList14;
        } else {
            Project project = getEnvironment().getProject();
            VirtualFileSystem fileSystem = VirtualFileManager.getInstance().getFileSystem("file");
            Intrinsics.checkNotNullExpressionValue(fileSystem, "getFileSystem(...)");
            VfsBasedProjectEnvironment vfsBasedProjectEnvironment3 = new VfsBasedProjectEnvironment(project, fileSystem, new Function1<GlobalSearchScope, PackagePartProvider>() { // from class: org.jetbrains.kotlin.cli.metadata.FirMetadataSerializer$analyze$outputs$projectEnvironment$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final PackagePartProvider invoke(GlobalSearchScope it6) {
                    Intrinsics.checkNotNullParameter(it6, "it");
                    return this.this$0.getEnvironment().createPackagePartProvider(it6);
                }
            });
            AbstractProjectFileSearchScope searchScopeForProjectLibraries2 = vfsBasedProjectEnvironment3.getSearchScopeForProjectLibraries();
            List<FirExtensionRegistrar> instances2 = FirExtensionRegistrar.INSTANCE.getInstances(vfsBasedProjectEnvironment3.getProject());
            List<KtFile> sourceFiles = getEnvironment().getSourceFiles();
            AbstractProjectFileSearchScope abstractProjectFileSearchScopePlus = VfsBasedProjectEnvironment.getSearchScopeByPsiFiles$default(vfsBasedProjectEnvironment3, sourceFiles, false, 2, null).plus(vfsBasedProjectEnvironment3.getSearchScopeForProjectJavaSources());
            VfsBasedProjectEnvironment vfsBasedProjectEnvironment4 = vfsBasedProjectEnvironment3;
            IncrementalCompilationComponents incrementalCompilationComponents = (IncrementalCompilationComponents) configuration.get(JVMConfigurationKeys.INCREMENTAL_COMPILATION_COMPONENTS);
            List list5 = (List) configuration.get(JVMConfigurationKeys.MODULES);
            if (list5 != null) {
                List list6 = list5;
                ArrayList arrayList15 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list6, 10));
                Iterator it6 = list6.iterator();
                while (it6.hasNext()) {
                    arrayList15.add(TargetIdKt.TargetId((Module) it6.next()));
                }
                arrayList = arrayList15;
            } else {
                arrayList = null;
            }
            final IncrementalCompilationContext incrementalCompilationContextCreateContextForIncrementalCompilation = CliCompilerUtilsKt.createContextForIncrementalCompilation(vfsBasedProjectEnvironment4, incrementalCompilationComponents, configuration, arrayList, abstractProjectFileSearchScopePlus);
            if (incrementalCompilationContextCreateContextForIncrementalCompilation != null && (precompiledBinariesFileScope = incrementalCompilationContextCreateContextForIncrementalCompilation.getPrecompiledBinariesFileScope()) != null) {
                searchScopeForProjectLibraries2 = searchScopeForProjectLibraries2.minus(precompiledBinariesFileScope);
            }
            List<SessionWithSources> listPrepareCommonSessions2 = FirSessionConstructionUtilsKt.prepareCommonSessions(sourceFiles, configuration, vfsBasedProjectEnvironment4, nameSpecial, instances2, searchScopeForProjectLibraries2, dependencyListForCliModuleBuild, fullResolvedList$default, FirSessionConstructionUtilsKt.isCommonSourceForPsi(), FirSessionConstructionUtilsKt.getFileBelongsToModuleForPsi(), new Function1<List<? extends KtFile>, IncrementalCompilationContext>() { // from class: org.jetbrains.kotlin.cli.metadata.FirMetadataSerializer$analyze$outputs$sessionsWithSources$2
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final IncrementalCompilationContext invoke(List<? extends KtFile> it7) {
                    Intrinsics.checkNotNullParameter(it7, "it");
                    return incrementalCompilationContextCreateContextForIncrementalCompilation;
                }
            });
            ArrayList arrayList16 = new ArrayList(CollectionsKt.collectionSizeOrDefault(listPrepareCommonSessions2, 10));
            for (SessionWithSources sessionWithSources2 : listPrepareCommonSessions2) {
                FirSession session2 = sessionWithSources2.getSession();
                arrayList16.add(FirUtilsKt.resolveAndCheckFir(session2, FirUtilsKt.buildFirFromKtFiles(session2, sessionWithSources2.component2()), pendingDiagnosticsCollectorWithSuppressCreatePendingReporter$default));
            }
            arrayList2 = arrayList16;
        }
        if (pendingDiagnosticsCollectorWithSuppressCreatePendingReporter$default.getHasErrors()) {
            FirDiagnosticsCompilerResultsReporter.INSTANCE.reportToMessageCollector(pendingDiagnosticsCollectorWithSuppressCreatePendingReporter$default, messageCollector, configuration.getBoolean(CLIConfigurationKeys.RENDER_DIAGNOSTIC_INTERNAL_NAME));
            arrayList3 = null;
        } else {
            arrayList3 = arrayList2;
        }
        commonCompilerPerformanceManager.notifyAnalysisFinished();
        return arrayList3;
    }

    /* JADX INFO: renamed from: serialize, reason: avoid collision after fix types in other method */
    protected void serialize2(List<ModuleCompilerAnalyzedOutput> analysisResult, File destDir) throws Throwable {
        Intrinsics.checkNotNullParameter(analysisResult, "analysisResult");
        Intrinsics.checkNotNullParameter(destDir, "destDir");
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (ModuleCompilerAnalyzedOutput moduleCompilerAnalyzedOutput : analysisResult) {
            FirSession session = moduleCompilerAnalyzedOutput.getSession();
            ScopeSession scopeSession = moduleCompilerAnalyzedOutput.getScopeSession();
            List<FirFile> listComponent3 = moduleCompilerAnalyzedOutput.component3();
            LanguageVersionSettings languageVersionSettings = CommonConfigurationKeysKt.getLanguageVersionSettings(getEnvironment().getConfiguration());
            for (FirFile firFile : listComponent3) {
                ProtoBuf.PackageFragment packageFragmentSerializeSingleFirFile = FirKlibSerializationKt.serializeSingleFirFile(firFile, session, scopeSession, null, new FirKLibSerializerExtension(session, getMetadataVersion(), null, false, false, null), languageVersionSettings);
                String strAsString = UtilsKt.getPackageFqName(firFile).asString();
                Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                Object obj = linkedHashMap.get(strAsString);
                if (obj == null) {
                    obj = (List) new ArrayList();
                    linkedHashMap.put(strAsString, obj);
                }
                byte[] byteArray = packageFragmentSerializeSingleFirFile.toByteArray();
                Intrinsics.checkNotNullExpressionValue(byteArray, "toByteArray(...)");
                ((List) obj).add(byteArray);
            }
        }
        KlibMetadataProtoBuf.Header.Builder builderNewBuilder = KlibMetadataProtoBuf.Header.newBuilder();
        builderNewBuilder.setModuleName(FirModuleDataKt.getModuleData(((ModuleCompilerAnalyzedOutput) CollectionsKt.last((List) analysisResult)).getSession()).getName().asString());
        if (CommonConfigurationKeysKt.getLanguageVersionSettings(getConfiguration()).isPreRelease()) {
            builderNewBuilder.setFlags(2);
        }
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (Map.Entry entry : CollectionsKt.sortedWith(linkedHashMap.entrySet(), new Comparator() { // from class: org.jetbrains.kotlin.cli.metadata.FirMetadataSerializer$serialize$$inlined$sortedBy$1
            @Override // java.util.Comparator
            public final int compare(T t, T t2) {
                return ComparisonsKt.compareValues((String) ((Map.Entry) t).getKey(), (String) ((Map.Entry) t2).getKey());
            }
        })) {
            String str = (String) entry.getKey();
            List list = (List) entry.getValue();
            arrayList.add(str);
            arrayList2.add(list);
            builderNewBuilder.addPackageFragmentName(str);
        }
        byte[] byteArray2 = builderNewBuilder.build().toByteArray();
        Intrinsics.checkNotNull(byteArray2);
        MetadataUtilsKt.buildKotlinMetadataLibrary(getConfiguration(), new SerializedMetadata(byteArray2, arrayList2, arrayList), destDir);
    }
}
