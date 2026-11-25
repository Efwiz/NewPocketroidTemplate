package org.jetbrains.kotlin.ir.backend.js;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.jetbrains.kotlin.analyzer.AbstractAnalyzerWithCompilerReport;
import org.jetbrains.kotlin.analyzer.AnalysisResult;
import org.jetbrains.kotlin.analyzer.CompilationErrorException;
import org.jetbrains.kotlin.backend.common.CommonKLibResolver;
import org.jetbrains.kotlin.backend.common.KLibResolution;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.config.CommonConfigurationKeys;
import org.jetbrains.kotlin.config.CommonConfigurationKeysKt;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.impl.ModuleDescriptorImpl;
import org.jetbrains.kotlin.incremental.components.LookupTracker;
import org.jetbrains.kotlin.js.analyze.AbstractTopDownAnalyzerFacadeForWeb;
import org.jetbrains.kotlin.js.analyzer.JsAnalysisResult;
import org.jetbrains.kotlin.js.config.ErrorTolerancePolicy;
import org.jetbrains.kotlin.js.config.JSConfigurationKeys;
import org.jetbrains.kotlin.konan.file.ZipFileSystemAccessor;
import org.jetbrains.kotlin.library.KotlinLibrary;
import org.jetbrains.kotlin.library.KotlinLibraryKt;
import org.jetbrains.kotlin.library.metadata.KlibMetadataModuleDescriptorFactory;
import org.jetbrains.kotlin.library.metadata.resolver.KotlinLibraryResolveResult;
import org.jetbrains.kotlin.library.metadata.resolver.KotlinResolvedLibrary;
import org.jetbrains.kotlin.progress.ProgressIndicatorAndCompilationCanceledStatus;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.storage.LockBasedStorageManager;

/* JADX INFO: compiled from: klib.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0088\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010$\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001FB9\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t\u0012\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\n0\t¢\u0006\u0002\u0010\fJ\u000e\u0010;\u001a\u00020\u001a2\u0006\u0010<\u001a\u00020\u000fJ\u0010\u0010=\u001a\u00020\u00102\u0006\u0010<\u001a\u00020\u000fH\u0002J\u001e\u0010>\u001a\u00020?2\u0006\u0010@\u001a\u00020A2\u0006\u0010B\u001a\u00020C2\u0006\u0010D\u001a\u00020ER\u001a\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00100\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u000f0\u00128F¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0015\u001a\u00020\u0016¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0013\u0010\u0019\u001a\u0004\u0018\u00010\u001a¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u0010\u0010\u001d\u001a\u0004\u0018\u00010\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t¢\u0006\b\n\u0000\u001a\u0004\b \u0010!R\u001d\u0010\"\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u001a0#8F¢\u0006\u0006\u001a\u0004\b$\u0010%R\u0017\u0010&\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0012¢\u0006\b\n\u0000\u001a\u0004\b'\u0010\u0014R\u001a\u0010(\u001a\u00020)X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b*\u0010+\"\u0004\b,\u0010-R\u000e\u0010.\u001a\u00020/X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b0\u00101R-\u00102\u001a\u0014\u0012\u0004\u0012\u00020\u000f\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u00120#8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b4\u00105\u001a\u0004\b3\u0010%R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b6\u00107R\u0010\u00108\u001a\u0004\u0018\u00010\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00109\u001a\u00020:X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006G"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/ModulesStructure;", "", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "mainModule", "Lorg/jetbrains/kotlin/ir/backend/js/MainModule;", "compilerConfiguration", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "dependencies", "", "", "friendDependenciesPaths", "(Lcom/intellij/openapi/project/Project;Lorg/jetbrains/kotlin/ir/backend/js/MainModule;Lorg/jetbrains/kotlin/config/CompilerConfiguration;Ljava/util/Collection;Ljava/util/Collection;)V", "_descriptors", "", "Lorg/jetbrains/kotlin/library/KotlinLibrary;", "Lorg/jetbrains/kotlin/descriptors/impl/ModuleDescriptorImpl;", "allDependencies", "", "getAllDependencies", "()Ljava/util/List;", "allDependenciesResolution", "Lorg/jetbrains/kotlin/backend/common/KLibResolution;", "getAllDependenciesResolution", "()Lorg/jetbrains/kotlin/backend/common/KLibResolution;", "builtInModuleDescriptor", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "getBuiltInModuleDescriptor", "()Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "builtInsDep", "getCompilerConfiguration", "()Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "getDependencies", "()Ljava/util/Collection;", "descriptors", "", "getDescriptors", "()Ljava/util/Map;", "friendDependencies", "getFriendDependencies", "jsFrontEndResult", "Lorg/jetbrains/kotlin/ir/backend/js/ModulesStructure$JsFrontEndResult;", "getJsFrontEndResult", "()Lorg/jetbrains/kotlin/ir/backend/js/ModulesStructure$JsFrontEndResult;", "setJsFrontEndResult", "(Lorg/jetbrains/kotlin/ir/backend/js/ModulesStructure$JsFrontEndResult;)V", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "getMainModule", "()Lorg/jetbrains/kotlin/ir/backend/js/MainModule;", "moduleDependencies", "getModuleDependencies", "moduleDependencies$delegate", "Lkotlin/Lazy;", "getProject", "()Lcom/intellij/openapi/project/Project;", "runtimeModule", "storageManager", "Lorg/jetbrains/kotlin/storage/LockBasedStorageManager;", "getModuleDescriptor", "current", "getModuleDescriptorImpl", "runAnalysis", "", "errorPolicy", "Lorg/jetbrains/kotlin/js/config/ErrorTolerancePolicy;", "analyzer", "Lorg/jetbrains/kotlin/analyzer/AbstractAnalyzerWithCompilerReport;", "analyzerFacade", "Lorg/jetbrains/kotlin/js/analyze/AbstractTopDownAnalyzerFacadeForWeb;", "JsFrontEndResult", "ir.serialization.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ModulesStructure {
    private final Map<KotlinLibrary, ModuleDescriptorImpl> _descriptors;
    private final KLibResolution allDependenciesResolution;
    private final ModuleDescriptor builtInModuleDescriptor;
    private final KotlinLibrary builtInsDep;
    private final CompilerConfiguration compilerConfiguration;
    private final Collection<String> dependencies;
    private final List<KotlinLibrary> friendDependencies;
    public JsFrontEndResult jsFrontEndResult;
    private final LanguageVersionSettings languageVersionSettings;
    private final MainModule mainModule;

    /* JADX INFO: renamed from: moduleDependencies$delegate, reason: from kotlin metadata */
    private final Lazy moduleDependencies;
    private final Project project;
    private ModuleDescriptorImpl runtimeModule;
    private final LockBasedStorageManager storageManager;

    public ModulesStructure(Project project, MainModule mainModule, CompilerConfiguration compilerConfiguration, Collection<String> dependencies, Collection<String> friendDependenciesPaths) {
        Object next;
        Intrinsics.checkNotNullParameter(project, "project");
        Intrinsics.checkNotNullParameter(mainModule, "mainModule");
        Intrinsics.checkNotNullParameter(compilerConfiguration, "compilerConfiguration");
        Intrinsics.checkNotNullParameter(dependencies, "dependencies");
        Intrinsics.checkNotNullParameter(friendDependenciesPaths, "friendDependenciesPaths");
        this.project = project;
        this.mainModule = mainModule;
        this.compilerConfiguration = compilerConfiguration;
        this.dependencies = dependencies;
        this.allDependenciesResolution = CommonKLibResolver.INSTANCE.resolveWithoutDependencies(dependencies, KlibKt.getResolverLogger(compilerConfiguration), (ZipFileSystemAccessor) compilerConfiguration.get(JSConfigurationKeys.ZIP_FILE_SYSTEM_ACCESSOR));
        List<KotlinLibrary> allDependencies = getAllDependencies();
        Collection<String> collection = friendDependenciesPaths;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection, 10));
        Iterator<T> it = collection.iterator();
        while (it.hasNext()) {
            arrayList.add(new File((String) it.next()).getCanonicalPath());
        }
        ArrayList arrayList2 = arrayList;
        ArrayList arrayList3 = new ArrayList();
        for (Object obj : allDependencies) {
            if (arrayList2.contains(((KotlinLibrary) obj).getLibraryFile().getAbsolutePath())) {
                arrayList3.add(obj);
            }
        }
        this.friendDependencies = org.jetbrains.kotlin.utils.CollectionsKt.compactIfPossible(arrayList3);
        this.moduleDependencies = LazyKt.lazy(new Function0<Map<KotlinLibrary, ? extends List<? extends KotlinLibrary>>>() { // from class: org.jetbrains.kotlin.ir.backend.js.ModulesStructure$moduleDependencies$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Map<KotlinLibrary, ? extends List<? extends KotlinLibrary>> invoke() {
                List<KotlinResolvedLibrary> fullResolvedList$default = KotlinLibraryResolveResult.getFullResolvedList$default(this.this$0.getAllDependenciesResolution().resolveWithDependencies(), null, 1, null);
                LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(fullResolvedList$default, 10)), 16));
                for (KotlinResolvedLibrary kotlinResolvedLibrary : fullResolvedList$default) {
                    KotlinLibrary library = kotlinResolvedLibrary.getLibrary();
                    List<KotlinResolvedLibrary> resolvedDependencies = kotlinResolvedLibrary.getResolvedDependencies();
                    ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(resolvedDependencies, 10));
                    Iterator<T> it2 = resolvedDependencies.iterator();
                    while (it2.hasNext()) {
                        arrayList4.add(((KotlinResolvedLibrary) it2.next()).getLibrary());
                    }
                    Pair pair = TuplesKt.to(library, arrayList4);
                    linkedHashMap.put(pair.getFirst(), pair.getSecond());
                }
                return MapsKt.toMap(linkedHashMap);
            }
        });
        Iterator<T> it2 = getAllDependencies().iterator();
        do {
            if (!it2.hasNext()) {
                next = null;
                break;
            }
            next = it2.next();
        } while (!KlibKt.isBuiltIns((KotlinLibrary) next));
        this.builtInsDep = (KotlinLibrary) next;
        this.languageVersionSettings = CommonConfigurationKeysKt.getLanguageVersionSettings(this.compilerConfiguration);
        this.storageManager = new LockBasedStorageManager("ModulesStructure");
        this._descriptors = new LinkedHashMap();
        List<KotlinLibrary> allDependencies2 = getAllDependencies();
        ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(allDependencies2, 10));
        Iterator<T> it3 = allDependencies2.iterator();
        while (it3.hasNext()) {
            arrayList4.add(getModuleDescriptorImpl((KotlinLibrary) it3.next()));
        }
        ArrayList arrayList5 = arrayList4;
        Iterator it4 = arrayList5.iterator();
        while (it4.hasNext()) {
            ((ModuleDescriptorImpl) it4.next()).setDependencies(arrayList5);
        }
        KotlinLibrary kotlinLibrary = this.builtInsDep;
        this.builtInModuleDescriptor = kotlinLibrary != null ? getModuleDescriptor(kotlinLibrary) : null;
    }

    public final Project getProject() {
        return this.project;
    }

    public final MainModule getMainModule() {
        return this.mainModule;
    }

    public final CompilerConfiguration getCompilerConfiguration() {
        return this.compilerConfiguration;
    }

    public final Collection<String> getDependencies() {
        return this.dependencies;
    }

    public final KLibResolution getAllDependenciesResolution() {
        return this.allDependenciesResolution;
    }

    public final List<KotlinLibrary> getAllDependencies() {
        return this.allDependenciesResolution.getLibraries();
    }

    public final List<KotlinLibrary> getFriendDependencies() {
        return this.friendDependencies;
    }

    public final Map<KotlinLibrary, List<KotlinLibrary>> getModuleDependencies() {
        return (Map) this.moduleDependencies.getValue();
    }

    /* JADX INFO: compiled from: klib.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u000f\u001a\u00020\u00108F¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/ModulesStructure$JsFrontEndResult;", "", "jsAnalysisResult", "Lorg/jetbrains/kotlin/analyzer/AnalysisResult;", "hasErrors", "", "(Lorg/jetbrains/kotlin/analyzer/AnalysisResult;Z)V", "bindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "getBindingContext", "()Lorg/jetbrains/kotlin/resolve/BindingContext;", "getHasErrors", "()Z", "getJsAnalysisResult", "()Lorg/jetbrains/kotlin/analyzer/AnalysisResult;", "moduleDescriptor", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "getModuleDescriptor", "()Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "ir.serialization.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class JsFrontEndResult {
        private final boolean hasErrors;
        private final AnalysisResult jsAnalysisResult;

        public JsFrontEndResult(AnalysisResult jsAnalysisResult, boolean z) {
            Intrinsics.checkNotNullParameter(jsAnalysisResult, "jsAnalysisResult");
            this.jsAnalysisResult = jsAnalysisResult;
            this.hasErrors = z;
        }

        public final boolean getHasErrors() {
            return this.hasErrors;
        }

        public final AnalysisResult getJsAnalysisResult() {
            return this.jsAnalysisResult;
        }

        public final ModuleDescriptor getModuleDescriptor() {
            return this.jsAnalysisResult.getModuleDescriptor();
        }

        public final BindingContext getBindingContext() {
            return this.jsAnalysisResult.getBindingContext();
        }
    }

    public final JsFrontEndResult getJsFrontEndResult() {
        JsFrontEndResult jsFrontEndResult = this.jsFrontEndResult;
        if (jsFrontEndResult != null) {
            return jsFrontEndResult;
        }
        Intrinsics.throwUninitializedPropertyAccessException("jsFrontEndResult");
        return null;
    }

    public final void setJsFrontEndResult(JsFrontEndResult jsFrontEndResult) {
        Intrinsics.checkNotNullParameter(jsFrontEndResult, "<set-?>");
        this.jsFrontEndResult = jsFrontEndResult;
    }

    public final void runAnalysis(ErrorTolerancePolicy errorPolicy, final AbstractAnalyzerWithCompilerReport analyzer, final AbstractTopDownAnalyzerFacadeForWeb analyzerFacade) {
        boolean z;
        Intrinsics.checkNotNullParameter(errorPolicy, "errorPolicy");
        Intrinsics.checkNotNullParameter(analyzer, "analyzer");
        Intrinsics.checkNotNullParameter(analyzerFacade, "analyzerFacade");
        MainModule mainModule = this.mainModule;
        if (!(mainModule instanceof MainModule.SourceFiles)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        final List<KtFile> files = ((MainModule.SourceFiles) mainModule).getFiles();
        List<KtFile> list = files;
        analyzer.analyzeAndReport(list, new Function0<AnalysisResult>() { // from class: org.jetbrains.kotlin.ir.backend.js.ModulesStructure.runAnalysis.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final AnalysisResult invoke() {
                AbstractTopDownAnalyzerFacadeForWeb abstractTopDownAnalyzerFacadeForWeb = analyzerFacade;
                List<KtFile> list2 = files;
                Project project = this.getProject();
                CompilerConfiguration compilerConfiguration = this.getCompilerConfiguration();
                List<? extends ModuleDescriptor> list3 = CollectionsKt.toList(this.getDescriptors().values());
                List<KotlinLibrary> friendDependencies = this.getFriendDependencies();
                ModulesStructure modulesStructure = this;
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(friendDependencies, 10));
                Iterator<T> it = friendDependencies.iterator();
                while (it.hasNext()) {
                    arrayList.add(modulesStructure.getModuleDescriptor((KotlinLibrary) it.next()));
                }
                return abstractTopDownAnalyzerFacadeForWeb.analyzeFiles(list2, project, compilerConfiguration, list3, arrayList, analyzer.getTargetEnvironment(), this.getBuiltInModuleDescriptor() == null, this.getBuiltInModuleDescriptor());
            }
        });
        ProgressIndicatorAndCompilationCanceledStatus.checkCanceled();
        AnalysisResult analysisResult = analyzer.getAnalysisResult();
        if (this.compilerConfiguration.getBoolean(CommonConfigurationKeys.INCREMENTAL_COMPILATION)) {
            KlibKt.compareMetadataAndGoToNextICRoundIfNeeded(analysisResult, this.compilerConfiguration, this.project, files, errorPolicy.getAllowErrors());
        }
        boolean z2 = true;
        if (!analyzer.hasErrors() && (analysisResult instanceof JsAnalysisResult)) {
            z = false;
        } else {
            if (!errorPolicy.getAllowErrors()) {
                throw new CompilationErrorException();
            }
            z = true;
        }
        if (!analyzerFacade.checkForErrors(list, analysisResult.getBindingContext(), errorPolicy) && !z) {
            z2 = false;
        }
        setJsFrontEndResult(new JsFrontEndResult(analysisResult, z2));
    }

    public final Map<KotlinLibrary, ModuleDescriptor> getDescriptors() {
        return this._descriptors;
    }

    private final ModuleDescriptorImpl getModuleDescriptorImpl(KotlinLibrary current) {
        if (this._descriptors.containsKey(current)) {
            return (ModuleDescriptorImpl) MapsKt.getValue(this._descriptors, current);
        }
        boolean zIsEmpty = KotlinLibraryKt.getUnresolvedDependencies(current).isEmpty();
        LookupTracker.DO_NOTHING do_nothing = (LookupTracker) this.compilerConfiguration.get(CommonConfigurationKeys.LOOKUP_TRACKER);
        if (do_nothing == null) {
            do_nothing = LookupTracker.DO_NOTHING.INSTANCE;
        }
        LookupTracker lookupTracker = do_nothing;
        Intrinsics.checkNotNull(lookupTracker);
        KlibMetadataModuleDescriptorFactory defaultDeserializedDescriptorFactory = KlibKt.getJsFactories().getDefaultDeserializedDescriptorFactory();
        LanguageVersionSettings languageVersionSettings = this.languageVersionSettings;
        LockBasedStorageManager lockBasedStorageManager = this.storageManager;
        ModuleDescriptorImpl moduleDescriptorImpl = this.runtimeModule;
        ModuleDescriptorImpl moduleDescriptorImplCreateDescriptorOptionalBuiltIns = defaultDeserializedDescriptorFactory.createDescriptorOptionalBuiltIns(current, languageVersionSettings, lockBasedStorageManager, moduleDescriptorImpl != null ? moduleDescriptorImpl.getBuiltIns() : null, null, lookupTracker);
        if (zIsEmpty) {
            this.runtimeModule = moduleDescriptorImplCreateDescriptorOptionalBuiltIns;
        }
        this._descriptors.put(current, moduleDescriptorImplCreateDescriptorOptionalBuiltIns);
        return moduleDescriptorImplCreateDescriptorOptionalBuiltIns;
    }

    public final ModuleDescriptor getModuleDescriptor(KotlinLibrary current) {
        Intrinsics.checkNotNullParameter(current, "current");
        return getModuleDescriptorImpl(current);
    }

    public final ModuleDescriptor getBuiltInModuleDescriptor() {
        return this.builtInModuleDescriptor;
    }
}
