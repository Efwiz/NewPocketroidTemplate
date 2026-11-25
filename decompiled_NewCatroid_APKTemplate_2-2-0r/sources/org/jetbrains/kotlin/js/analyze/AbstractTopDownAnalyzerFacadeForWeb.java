package org.jetbrains.kotlin.js.analyze;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.analyzer.AnalysisResult;
import org.jetbrains.kotlin.builtins.DefaultBuiltIns;
import org.jetbrains.kotlin.builtins.functions.FunctionInterfaceFactoryKt;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.config.CommonConfigurationKeys;
import org.jetbrains.kotlin.config.CommonConfigurationKeysKt;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.container.DslKt;
import org.jetbrains.kotlin.container.StorageComponentContainer;
import org.jetbrains.kotlin.context.ContextKt;
import org.jetbrains.kotlin.context.ModuleContext;
import org.jetbrains.kotlin.context.MutableModuleContext;
import org.jetbrains.kotlin.context.ProjectContext;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentProvider;
import org.jetbrains.kotlin.descriptors.impl.ModuleDescriptorImpl;
import org.jetbrains.kotlin.frontend.js.di.InjectionKt;
import org.jetbrains.kotlin.incremental.components.EnumWhenTracker;
import org.jetbrains.kotlin.incremental.components.ExpectActualTracker;
import org.jetbrains.kotlin.incremental.components.InlineConstTracker;
import org.jetbrains.kotlin.incremental.components.LookupTracker;
import org.jetbrains.kotlin.incremental.js.IncrementalDataProvider;
import org.jetbrains.kotlin.js.analyzer.JsAnalysisResult;
import org.jetbrains.kotlin.js.config.ErrorTolerancePolicy;
import org.jetbrains.kotlin.js.config.JSConfigurationKeys;
import org.jetbrains.kotlin.js.resolve.BindingContextSlicesJsKt;
import org.jetbrains.kotlin.js.resolve.JsPlatformAnalyzerServices;
import org.jetbrains.kotlin.konan.library.NativeLibraryConstantsKt;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.platform.TargetPlatform;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.resolve.AnalyzingUtils;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.BindingTraceContext;
import org.jetbrains.kotlin.resolve.DelegatingBindingTrace;
import org.jetbrains.kotlin.resolve.LazyTopDownAnalyzer;
import org.jetbrains.kotlin.resolve.PlatformDependentAnalyzerServices;
import org.jetbrains.kotlin.resolve.TargetEnvironment;
import org.jetbrains.kotlin.resolve.TopDownAnalysisMode;
import org.jetbrains.kotlin.resolve.extensions.AnalysisHandlerExtension;
import org.jetbrains.kotlin.resolve.lazy.declarations.FileBasedDeclarationProviderFactory;
import org.jetbrains.kotlin.serialization.js.ModuleKind;

/* JADX INFO: compiled from: TopDownAnalyzerFacadeForJS.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0086\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J^\u0010\u000b\u001a\u00020\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00160\u00152\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00160\u00152\u0006\u0010\u0018\u001a\u00020\u00192\b\b\u0002\u0010\u001a\u001a\u00020\u001b2\n\b\u0002\u0010\u001c\u001a\u0004\u0018\u00010\u0016JL\u0010\u001d\u001a\u00020\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u0010\u001a\u00020\u00112\u000e\b\u0002\u0010\"\u001a\b\u0012\u0004\u0012\u00020#0\u0015J$\u0010$\u001a\u00020\u001b2\f\u0010%\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e2\u0006\u0010&\u001a\u00020'2\u0006\u0010(\u001a\u00020)J(\u0010*\u001a\u00020#2\u0006\u0010+\u001a\u00020,2\u0006\u0010 \u001a\u00020!2\u0006\u0010-\u001a\u00020.2\u0006\u0010/\u001a\u000200H$R\u0012\u0010\u0003\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0012\u0010\u0007\u001a\u00020\bX¦\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\n¨\u00061"}, d2 = {"Lorg/jetbrains/kotlin/js/analyze/AbstractTopDownAnalyzerFacadeForWeb;", "", "()V", "analyzerServices", "Lorg/jetbrains/kotlin/resolve/PlatformDependentAnalyzerServices;", "getAnalyzerServices", "()Lorg/jetbrains/kotlin/resolve/PlatformDependentAnalyzerServices;", NativeLibraryConstantsKt.KONAN_DISTRIBUTION_PLATFORM_LIBS_DIR, "Lorg/jetbrains/kotlin/platform/TargetPlatform;", "getPlatform", "()Lorg/jetbrains/kotlin/platform/TargetPlatform;", "analyzeFiles", "Lorg/jetbrains/kotlin/js/analyzer/JsAnalysisResult;", "files", "", "Lorg/jetbrains/kotlin/psi/KtFile;", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "configuration", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "moduleDescriptors", "", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "friendModuleDescriptors", "targetEnvironment", "Lorg/jetbrains/kotlin/resolve/TargetEnvironment;", "thisIsBuiltInsModule", "", "customBuiltInsModule", "analyzeFilesWithGivenTrace", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "moduleContext", "Lorg/jetbrains/kotlin/context/ModuleContext;", "additionalPackages", "Lorg/jetbrains/kotlin/descriptors/PackageFragmentProvider;", "checkForErrors", "allFiles", "bindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "errorPolicy", "Lorg/jetbrains/kotlin/js/config/ErrorTolerancePolicy;", "loadIncrementalCacheMetadata", "incrementalData", "Lorg/jetbrains/kotlin/incremental/js/IncrementalDataProvider;", "lookupTracker", "Lorg/jetbrains/kotlin/incremental/components/LookupTracker;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "js.frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AbstractTopDownAnalyzerFacadeForWeb {
    public abstract PlatformDependentAnalyzerServices getAnalyzerServices();

    public abstract TargetPlatform getPlatform();

    protected abstract PackageFragmentProvider loadIncrementalCacheMetadata(IncrementalDataProvider incrementalData, ModuleContext moduleContext, LookupTracker lookupTracker, LanguageVersionSettings languageVersionSettings);

    public static /* synthetic */ JsAnalysisResult analyzeFiles$default(AbstractTopDownAnalyzerFacadeForWeb abstractTopDownAnalyzerFacadeForWeb, Collection collection, Project project, CompilerConfiguration compilerConfiguration, List list, List list2, TargetEnvironment targetEnvironment, boolean z, ModuleDescriptor moduleDescriptor, int i, Object obj) {
        if (obj == null) {
            return abstractTopDownAnalyzerFacadeForWeb.analyzeFiles(collection, project, compilerConfiguration, list, list2, targetEnvironment, (i & 64) != 0 ? false : z, (i & 128) != 0 ? null : moduleDescriptor);
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: analyzeFiles");
    }

    public final JsAnalysisResult analyzeFiles(Collection<? extends KtFile> files, Project project, CompilerConfiguration configuration, List<? extends ModuleDescriptor> moduleDescriptors, List<? extends ModuleDescriptor> friendModuleDescriptors, TargetEnvironment targetEnvironment, boolean thisIsBuiltInsModule, ModuleDescriptor customBuiltInsModule) {
        DefaultBuiltIns builtIns;
        Intrinsics.checkNotNullParameter(files, "files");
        Intrinsics.checkNotNullParameter(project, "project");
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        Intrinsics.checkNotNullParameter(moduleDescriptors, "moduleDescriptors");
        Intrinsics.checkNotNullParameter(friendModuleDescriptors, "friendModuleDescriptors");
        Intrinsics.checkNotNullParameter(targetEnvironment, "targetEnvironment");
        if (thisIsBuiltInsModule && customBuiltInsModule != null) {
            throw new IllegalArgumentException("Can't simultaneously use custom built-ins module and set current module as built-ins".toString());
        }
        if (thisIsBuiltInsModule) {
            builtIns = new DefaultBuiltIns(false);
        } else if (customBuiltInsModule != null) {
            builtIns = customBuiltInsModule.getBuiltIns();
        } else {
            builtIns = JsPlatformAnalyzerServices.INSTANCE.getBuiltIns();
        }
        Object obj = configuration.get(CommonConfigurationKeys.MODULE_NAME);
        Intrinsics.checkNotNull(obj);
        ProjectContext ProjectContext = ContextKt.ProjectContext(project, "TopDownAnalyzer for JS");
        Name nameSpecial = Name.special("<" + ((String) obj) + '>');
        Intrinsics.checkNotNullExpressionValue(nameSpecial, "special(...)");
        MutableModuleContext mutableModuleContextContextForNewModule$default = ContextKt.ContextForNewModule$default(ProjectContext, nameSpecial, builtIns, getPlatform(), null, 16, null);
        ArrayList arrayList = new ArrayList();
        if (thisIsBuiltInsModule) {
            builtIns.setBuiltInsModule(mutableModuleContextContextForNewModule$default.getModule());
            arrayList.add(FunctionInterfaceFactoryKt.functionInterfacePackageFragmentProvider(mutableModuleContextContextForNewModule$default.getStorageManager(), mutableModuleContextContextForNewModule$default.getModule()));
        }
        Set setPlus = SetsKt.plus(SetsKt.mutableSetOf(mutableModuleContextContextForNewModule$default.getModule()), (Iterable) moduleDescriptors);
        ModuleDescriptorImpl builtInsModule = builtIns.getBuiltInsModule();
        Intrinsics.checkNotNullExpressionValue(builtInsModule, "getBuiltInsModule(...)");
        Set setPlus2 = SetsKt.plus((Set<? extends ModuleDescriptorImpl>) setPlus, builtInsModule);
        ModuleDescriptorImpl module = mutableModuleContextContextForNewModule$default.getModule();
        List<ModuleDescriptorImpl> list = CollectionsKt.toList(setPlus2);
        Intrinsics.checkNotNull(list, "null cannot be cast to non-null type kotlin.collections.List<org.jetbrains.kotlin.descriptors.impl.ModuleDescriptorImpl>");
        Set<ModuleDescriptorImpl> set = CollectionsKt.toSet(friendModuleDescriptors);
        Intrinsics.checkNotNull(set, "null cannot be cast to non-null type kotlin.collections.Set<org.jetbrains.kotlin.descriptors.impl.ModuleDescriptorImpl>");
        module.setDependencies(list, set);
        Object obj2 = configuration.get(JSConfigurationKeys.MODULE_KIND, ModuleKind.PLAIN);
        Intrinsics.checkNotNullExpressionValue(obj2, "get(...)");
        BindingTraceContext bindingTraceContext = new BindingTraceContext();
        bindingTraceContext.record(BindingContextSlicesJsKt.MODULE_KIND, mutableModuleContextContextForNewModule$default.getModule(), (ModuleKind) obj2);
        return analyzeFilesWithGivenTrace(files, bindingTraceContext, mutableModuleContextContextForNewModule$default, configuration, targetEnvironment, project, arrayList);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ JsAnalysisResult analyzeFilesWithGivenTrace$default(AbstractTopDownAnalyzerFacadeForWeb abstractTopDownAnalyzerFacadeForWeb, Collection collection, BindingTrace bindingTrace, ModuleContext moduleContext, CompilerConfiguration compilerConfiguration, TargetEnvironment targetEnvironment, Project project, List list, int i, Object obj) {
        if (obj == null) {
            return abstractTopDownAnalyzerFacadeForWeb.analyzeFilesWithGivenTrace(collection, bindingTrace, moduleContext, compilerConfiguration, targetEnvironment, project, (i & 64) != 0 ? CollectionsKt.emptyList() : list);
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: analyzeFilesWithGivenTrace");
    }

    public final JsAnalysisResult analyzeFilesWithGivenTrace(Collection<? extends KtFile> files, BindingTrace trace, ModuleContext moduleContext, CompilerConfiguration configuration, TargetEnvironment targetEnvironment, Project project, List<? extends PackageFragmentProvider> additionalPackages) {
        Project project2;
        BindingTrace bindingTrace;
        AnalysisResult analysisResultSuccess;
        BindingTrace trace2 = trace;
        ModuleContext moduleContext2 = moduleContext;
        Project project3 = project;
        Intrinsics.checkNotNullParameter(files, "files");
        Intrinsics.checkNotNullParameter(trace2, "trace");
        Intrinsics.checkNotNullParameter(moduleContext2, "moduleContext");
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        Intrinsics.checkNotNullParameter(targetEnvironment, "targetEnvironment");
        Intrinsics.checkNotNullParameter(project3, "project");
        Intrinsics.checkNotNullParameter(additionalPackages, "additionalPackages");
        LookupTracker.DO_NOTHING do_nothing = (LookupTracker) configuration.get(CommonConfigurationKeys.LOOKUP_TRACKER);
        if (do_nothing == null) {
            do_nothing = LookupTracker.DO_NOTHING.INSTANCE;
        }
        LookupTracker lookupTracker = do_nothing;
        Intrinsics.checkNotNull(lookupTracker);
        ExpectActualTracker.DoNothing doNothing = (ExpectActualTracker) configuration.get(CommonConfigurationKeys.EXPECT_ACTUAL_TRACKER);
        if (doNothing == null) {
            doNothing = ExpectActualTracker.DoNothing.INSTANCE;
        }
        ExpectActualTracker expectActualTracker = doNothing;
        Intrinsics.checkNotNull(expectActualTracker);
        InlineConstTracker.DoNothing doNothing2 = (InlineConstTracker) configuration.get(CommonConfigurationKeys.INLINE_CONST_TRACKER);
        if (doNothing2 == null) {
            doNothing2 = InlineConstTracker.DoNothing.INSTANCE;
        }
        InlineConstTracker inlineConstTracker = doNothing2;
        Intrinsics.checkNotNull(inlineConstTracker);
        EnumWhenTracker.DoNothing doNothing3 = (EnumWhenTracker) configuration.get(CommonConfigurationKeys.ENUM_WHEN_TRACKER);
        if (doNothing3 == null) {
            doNothing3 = EnumWhenTracker.DoNothing.INSTANCE;
        }
        EnumWhenTracker enumWhenTracker = doNothing3;
        Intrinsics.checkNotNull(enumWhenTracker);
        LanguageVersionSettings languageVersionSettings = CommonConfigurationKeysKt.getLanguageVersionSettings(configuration);
        IncrementalDataProvider incrementalDataProvider = (IncrementalDataProvider) configuration.get(JSConfigurationKeys.INCREMENTAL_DATA_PROVIDER);
        AnalysisResult analysisResult = null;
        StorageComponentContainer storageComponentContainerCreateContainerForJS = InjectionKt.createContainerForJS(moduleContext, trace, new FileBasedDeclarationProviderFactory(moduleContext.getStorageManager(), files), languageVersionSettings, lookupTracker, expectActualTracker, inlineConstTracker, enumWhenTracker, CollectionsKt.plus((Collection) additionalPackages, (Iterable) CollectionsKt.listOfNotNull(incrementalDataProvider != null ? loadIncrementalCacheMetadata(incrementalDataProvider, moduleContext2, lookupTracker, languageVersionSettings) : null)), targetEnvironment, getAnalyzerServices(), getPlatform());
        List<AnalysisHandlerExtension> instances = AnalysisHandlerExtension.INSTANCE.getInstances(project3);
        Iterator<T> it = instances.iterator();
        while (true) {
            if (!it.hasNext()) {
                project2 = project3;
                bindingTrace = trace2;
                analysisResultSuccess = null;
                break;
            }
            project2 = project3;
            bindingTrace = trace2;
            analysisResultSuccess = ((AnalysisHandlerExtension) it.next()).doAnalysis(project, moduleContext.getModule(), moduleContext2, files, trace, storageComponentContainerCreateContainerForJS);
            if (analysisResultSuccess != null) {
                break;
            }
            moduleContext2 = moduleContext;
            project3 = project2;
            trace2 = bindingTrace;
        }
        if (analysisResultSuccess == null) {
            LazyTopDownAnalyzer.analyzeDeclarations$default((LazyTopDownAnalyzer) DslKt.getService(storageComponentContainerCreateContainerForJS, LazyTopDownAnalyzer.class), TopDownAnalysisMode.TopLevelDeclarations, files, null, null, 12, null);
            AnalysisResult.Companion companion = AnalysisResult.INSTANCE;
            BindingContext bindingContext = trace.getContext();
            Intrinsics.checkNotNullExpressionValue(bindingContext, "getBindingContext(...)");
            analysisResultSuccess = companion.success(bindingContext, moduleContext.getModule());
        }
        Iterator<T> it2 = instances.iterator();
        while (it2.hasNext()) {
            AnalysisResult analysisResultAnalysisCompleted = ((AnalysisHandlerExtension) it2.next()).analysisCompleted(project2, moduleContext.getModule(), bindingTrace, files);
            if (analysisResultAnalysisCompleted != null) {
                analysisResult = analysisResultAnalysisCompleted;
                break;
            }
        }
        if (analysisResult != null) {
            analysisResultSuccess = analysisResult;
        }
        if (analysisResultSuccess instanceof JsAnalysisResult) {
            return (JsAnalysisResult) analysisResultSuccess;
        }
        DelegatingBindingTrace delegatingBindingTrace = new DelegatingBindingTrace(analysisResultSuccess.getBindingContext(), "DelegatingBindingTrace by AnalysisHandlerExtension", false, null, false, null, 60, null);
        if (analysisResultSuccess instanceof AnalysisResult.RetryWithAdditionalRoots) {
            return new JsAnalysisResult.RetryWithAdditionalRoots(delegatingBindingTrace, analysisResultSuccess.getModuleDescriptor(), ((AnalysisResult.RetryWithAdditionalRoots) analysisResultSuccess).getAdditionalKotlinRoots());
        }
        return JsAnalysisResult.INSTANCE.success(delegatingBindingTrace, analysisResultSuccess.getModuleDescriptor(), analysisResultSuccess.getShouldGenerateCode());
    }

    public final boolean checkForErrors(Collection<? extends KtFile> allFiles, BindingContext bindingContext, ErrorTolerancePolicy errorPolicy) throws Exception {
        boolean z;
        Intrinsics.checkNotNullParameter(allFiles, "allFiles");
        Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
        Intrinsics.checkNotNullParameter(errorPolicy, "errorPolicy");
        try {
            AnalyzingUtils.INSTANCE.throwExceptionOnErrors(bindingContext);
            z = false;
        } catch (Exception e) {
            if (!errorPolicy.getAllowSemanticErrors()) {
                throw e;
            }
            z = true;
        }
        try {
            Iterator<? extends KtFile> it = allFiles.iterator();
            while (it.hasNext()) {
                AnalyzingUtils.checkForSyntacticErrors(it.next());
            }
            return z;
        } catch (Exception e2) {
            if (errorPolicy.getAllowSyntaxErrors()) {
                return true;
            }
            throw e2;
        }
    }
}
