package org.jetbrains.kotlin.analyzer.common;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SpreadBuilder;
import kotlin.ranges.RangesKt;
import org.jetbrains.kotlin.analyzer.AnalysisResult;
import org.jetbrains.kotlin.analyzer.ModuleContent;
import org.jetbrains.kotlin.analyzer.ModuleInfo;
import org.jetbrains.kotlin.analyzer.ResolverForModule;
import org.jetbrains.kotlin.analyzer.ResolverForModuleFactory;
import org.jetbrains.kotlin.analyzer.ResolverForProject;
import org.jetbrains.kotlin.analyzer.ResolverForSingleModuleProject;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.config.AnalysisFlag;
import org.jetbrains.kotlin.config.ApiVersion;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersion;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.container.ComponentProvider;
import org.jetbrains.kotlin.container.DslKt;
import org.jetbrains.kotlin.container.StorageComponentContainer;
import org.jetbrains.kotlin.context.ContextKt;
import org.jetbrains.kotlin.context.ModuleContext;
import org.jetbrains.kotlin.context.ProjectContext;
import org.jetbrains.kotlin.descriptors.ModuleCapability;
import org.jetbrains.kotlin.descriptors.PackageFragmentProvider;
import org.jetbrains.kotlin.descriptors.impl.CompositePackageFragmentProvider;
import org.jetbrains.kotlin.descriptors.impl.ModuleDescriptorImpl;
import org.jetbrains.kotlin.konan.library.NativeLibraryConstantsKt;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.platform.TargetPlatform;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.CodeAnalyzerInitializer;
import org.jetbrains.kotlin.resolve.LazyTopDownAnalyzer;
import org.jetbrains.kotlin.resolve.PlatformDependentAnalyzerServices;
import org.jetbrains.kotlin.resolve.SealedClassInheritorsProvider;
import org.jetbrains.kotlin.resolve.TargetEnvironment;
import org.jetbrains.kotlin.resolve.TopDownAnalysisMode;
import org.jetbrains.kotlin.resolve.extensions.AnalysisHandlerExtension;
import org.jetbrains.kotlin.resolve.lazy.AbsentDescriptorHandler;
import org.jetbrains.kotlin.resolve.lazy.ResolveSession;
import org.jetbrains.kotlin.resolve.lazy.declarations.DeclarationProviderFactoryService;
import org.jetbrains.kotlin.resolve.scopes.optimization.OptimizingOptions;
import org.jetbrains.kotlin.serialization.deserialization.MetadataPackageFragmentProvider;
import org.jetbrains.kotlin.serialization.deserialization.MetadataPartProvider;

/* JADX INFO: compiled from: CommonResolverForModuleFactory.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000l\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 \"2\u00020\u0001:\u0002\"#B1\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u000b¢\u0006\u0002\u0010\fJj\u0010\r\u001a\u00020\u000e\"\b\b\u0000\u0010\u000f*\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u0002H\u000f0\u00162\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u0002H\u000f0\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\b\u0010\u001d\u001a\u0004\u0018\u00010\u001e2\u0010\u0010\u001f\u001a\f\u0012\u0006\b\u0001\u0012\u00020!\u0018\u00010 H\u0016R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006$"}, d2 = {"Lorg/jetbrains/kotlin/analyzer/common/CommonResolverForModuleFactory;", "Lorg/jetbrains/kotlin/analyzer/ResolverForModuleFactory;", "platformParameters", "Lorg/jetbrains/kotlin/analyzer/common/CommonAnalysisParameters;", "targetEnvironment", "Lorg/jetbrains/kotlin/resolve/TargetEnvironment;", "targetPlatform", "Lorg/jetbrains/kotlin/platform/TargetPlatform;", "shouldCheckExpectActual", "", "commonDependenciesContainer", "Lorg/jetbrains/kotlin/analyzer/common/CommonDependenciesContainer;", "(Lorg/jetbrains/kotlin/analyzer/common/CommonAnalysisParameters;Lorg/jetbrains/kotlin/resolve/TargetEnvironment;Lorg/jetbrains/kotlin/platform/TargetPlatform;ZLorg/jetbrains/kotlin/analyzer/common/CommonDependenciesContainer;)V", "createResolverForModule", "Lorg/jetbrains/kotlin/analyzer/ResolverForModule;", "M", "Lorg/jetbrains/kotlin/analyzer/ModuleInfo;", "moduleDescriptor", "Lorg/jetbrains/kotlin/descriptors/impl/ModuleDescriptorImpl;", "moduleContext", "Lorg/jetbrains/kotlin/context/ModuleContext;", "moduleContent", "Lorg/jetbrains/kotlin/analyzer/ModuleContent;", "resolverForProject", "Lorg/jetbrains/kotlin/analyzer/ResolverForProject;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "sealedInheritorsProvider", "Lorg/jetbrains/kotlin/resolve/SealedClassInheritorsProvider;", "resolveOptimizingOptions", "Lorg/jetbrains/kotlin/resolve/scopes/optimization/OptimizingOptions;", "absentDescriptorHandlerClass", "Ljava/lang/Class;", "Lorg/jetbrains/kotlin/resolve/lazy/AbsentDescriptorHandler;", "Companion", "SourceModuleInfo", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CommonResolverForModuleFactory extends ResolverForModuleFactory {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final CommonDependenciesContainer commonDependenciesContainer;
    private final CommonAnalysisParameters platformParameters;
    private final boolean shouldCheckExpectActual;
    private final TargetEnvironment targetEnvironment;
    private final TargetPlatform targetPlatform;

    public /* synthetic */ CommonResolverForModuleFactory(CommonAnalysisParameters commonAnalysisParameters, TargetEnvironment targetEnvironment, TargetPlatform targetPlatform, boolean z, CommonDependenciesContainer commonDependenciesContainer, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(commonAnalysisParameters, targetEnvironment, targetPlatform, z, (i & 16) != 0 ? null : commonDependenciesContainer);
    }

    /* JADX INFO: compiled from: CommonResolverForModuleFactory.kt */
    @Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u001c\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001Ba\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0018\u0010\u0004\u001a\u0014\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0006\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0005\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00010\t\u0012\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00010\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00010\u000f\u0012\u0006\u0010\u0010\u001a\u00020\u0011¢\u0006\u0002\u0010\u0012J\u000e\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00010\u000bH\u0016J\b\u0010\u001f\u001a\u00020 H\u0016J\u000e\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00010\u000fH\u0016R\u0014\u0010\u0013\u001a\u00020\u00148VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016R&\u0010\u0004\u001a\u0014\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0006\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00010\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00010\u000bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00010\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u0014\u0010\f\u001a\u00020\rX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001e¨\u0006!"}, d2 = {"Lorg/jetbrains/kotlin/analyzer/common/CommonResolverForModuleFactory$SourceModuleInfo;", "Lorg/jetbrains/kotlin/analyzer/ModuleInfo;", "name", "Lorg/jetbrains/kotlin/name/Name;", "capabilities", "", "Lorg/jetbrains/kotlin/descriptors/ModuleCapability;", "", "dependencies", "", "expectedBy", "", NativeLibraryConstantsKt.KONAN_DISTRIBUTION_PLATFORM_LIBS_DIR, "Lorg/jetbrains/kotlin/platform/TargetPlatform;", "modulesWhoseInternalsAreVisible", "", "dependOnOldBuiltIns", "", "(Lorg/jetbrains/kotlin/name/Name;Ljava/util/Map;Ljava/lang/Iterable;Ljava/util/List;Lorg/jetbrains/kotlin/platform/TargetPlatform;Ljava/util/Collection;Z)V", "analyzerServices", "Lorg/jetbrains/kotlin/resolve/PlatformDependentAnalyzerServices;", "getAnalyzerServices", "()Lorg/jetbrains/kotlin/resolve/PlatformDependentAnalyzerServices;", "getCapabilities", "()Ljava/util/Map;", "getExpectedBy", "()Ljava/util/List;", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "getPlatform", "()Lorg/jetbrains/kotlin/platform/TargetPlatform;", "dependencyOnBuiltIns", "Lorg/jetbrains/kotlin/analyzer/ModuleInfo$DependencyOnBuiltIns;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class SourceModuleInfo implements ModuleInfo {
        private final Map<ModuleCapability<?>, Object> capabilities;
        private final boolean dependOnOldBuiltIns;
        private final Iterable<ModuleInfo> dependencies;
        private final List<ModuleInfo> expectedBy;
        private final Collection<ModuleInfo> modulesWhoseInternalsAreVisible;
        private final Name name;
        private final TargetPlatform platform;

        /* JADX WARN: Multi-variable type inference failed */
        public SourceModuleInfo(Name name2, Map<ModuleCapability<?>, ? extends Object> capabilities, Iterable<? extends ModuleInfo> dependencies, List<? extends ModuleInfo> expectedBy, TargetPlatform platform, Collection<? extends ModuleInfo> modulesWhoseInternalsAreVisible, boolean z) {
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(capabilities, "capabilities");
            Intrinsics.checkNotNullParameter(dependencies, "dependencies");
            Intrinsics.checkNotNullParameter(expectedBy, "expectedBy");
            Intrinsics.checkNotNullParameter(platform, "platform");
            Intrinsics.checkNotNullParameter(modulesWhoseInternalsAreVisible, "modulesWhoseInternalsAreVisible");
            this.name = name2;
            this.capabilities = capabilities;
            this.dependencies = dependencies;
            this.expectedBy = expectedBy;
            this.platform = platform;
            this.modulesWhoseInternalsAreVisible = modulesWhoseInternalsAreVisible;
            this.dependOnOldBuiltIns = z;
        }

        @Override // org.jetbrains.kotlin.analyzer.ModuleInfo
        public Name getName() {
            return this.name;
        }

        @Override // org.jetbrains.kotlin.analyzer.ModuleInfo
        public Map<ModuleCapability<?>, Object> getCapabilities() {
            return this.capabilities;
        }

        @Override // org.jetbrains.kotlin.analyzer.ModuleInfo
        public List<ModuleInfo> getExpectedBy() {
            return this.expectedBy;
        }

        @Override // org.jetbrains.kotlin.analyzer.ModuleInfo
        public TargetPlatform getPlatform() {
            return this.platform;
        }

        @Override // org.jetbrains.kotlin.analyzer.ModuleInfo
        public List<ModuleInfo> dependencies() {
            SpreadBuilder spreadBuilder = new SpreadBuilder(2);
            spreadBuilder.add(this);
            spreadBuilder.addSpread(CollectionsKt.toList(this.dependencies).toArray(new ModuleInfo[0]));
            return CollectionsKt.listOf(spreadBuilder.toArray(new ModuleInfo[spreadBuilder.size()]));
        }

        @Override // org.jetbrains.kotlin.analyzer.ModuleInfo
        public Collection<ModuleInfo> modulesWhoseInternalsAreVisible() {
            return this.modulesWhoseInternalsAreVisible;
        }

        @Override // org.jetbrains.kotlin.analyzer.ModuleInfo
        public ModuleInfo.DependencyOnBuiltIns dependencyOnBuiltIns() {
            return this.dependOnOldBuiltIns ? ModuleInfo.DependencyOnBuiltIns.LAST : ModuleInfo.DependencyOnBuiltIns.NONE;
        }

        @Override // org.jetbrains.kotlin.analyzer.ModuleInfo
        public PlatformDependentAnalyzerServices getAnalyzerServices() {
            return CommonPlatformAnalyzerServices.INSTANCE;
        }
    }

    public CommonResolverForModuleFactory(CommonAnalysisParameters platformParameters, TargetEnvironment targetEnvironment, TargetPlatform targetPlatform, boolean z, CommonDependenciesContainer commonDependenciesContainer) {
        Intrinsics.checkNotNullParameter(platformParameters, "platformParameters");
        Intrinsics.checkNotNullParameter(targetEnvironment, "targetEnvironment");
        Intrinsics.checkNotNullParameter(targetPlatform, "targetPlatform");
        this.platformParameters = platformParameters;
        this.targetEnvironment = targetEnvironment;
        this.targetPlatform = targetPlatform;
        this.shouldCheckExpectActual = z;
        this.commonDependenciesContainer = commonDependenciesContainer;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.analyzer.ResolverForModuleFactory
    public <M extends ModuleInfo> ResolverForModule createResolverForModule(ModuleDescriptorImpl moduleDescriptor, ModuleContext moduleContext, ModuleContent<? extends M> moduleContent, ResolverForProject<M> resolverForProject, LanguageVersionSettings languageVersionSettings, SealedClassInheritorsProvider sealedInheritorsProvider, OptimizingOptions resolveOptimizingOptions, Class<? extends AbsentDescriptorHandler> absentDescriptorHandlerClass) {
        List listListOfNotNull;
        PackageFragmentProvider packageFragmentProviderPackageFragmentProviderForModuleInfo;
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        Intrinsics.checkNotNullParameter(moduleContext, "moduleContext");
        Intrinsics.checkNotNullParameter(moduleContent, "moduleContent");
        Intrinsics.checkNotNullParameter(resolverForProject, "resolverForProject");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(sealedInheritorsProvider, "sealedInheritorsProvider");
        ModuleInfo moduleInfoComponent1 = moduleContent.component1();
        Collection<KtFile> collectionComponent2 = moduleContent.component2();
        GlobalSearchScope moduleContentScope = moduleContent.getModuleContentScope();
        Project project = moduleContext.getProject();
        StorageComponentContainer storageComponentContainerCreateContainerToResolveCommonCode = CommonResolverForModuleFactoryKt.createContainerToResolveCommonCode(moduleContext, CodeAnalyzerInitializer.INSTANCE.getInstance(project).createTrace(), DeclarationProviderFactoryService.INSTANCE.createDeclarationProviderFactory(project, moduleContext.getStorageManager(), collectionComponent2, moduleContentScope, moduleInfoComponent1), moduleContentScope, this.targetEnvironment, this.platformParameters.getMetadataPartProviderFactory().invoke(moduleContent), languageVersionSettings, this.targetPlatform, CommonPlatformAnalyzerServices.INSTANCE, this.shouldCheckExpectActual, absentDescriptorHandlerClass);
        KlibMetadataPackageFragmentProviderFactory klibMetadataPackageFragmentProviderFactory = this.platformParameters.getKlibMetadataPackageFragmentProviderFactory();
        PackageFragmentProvider packageFragmentProviderCreatePackageFragmentProvider = klibMetadataPackageFragmentProviderFactory != null ? klibMetadataPackageFragmentProviderFactory.createPackageFragmentProvider(new PackageFragmentProviderCreationContext(moduleInfoComponent1, moduleContext.getStorageManager(), languageVersionSettings, moduleDescriptor)) : null;
        CommonDependenciesContainer commonDependenciesContainer = this.commonDependenciesContainer;
        if (commonDependenciesContainer == null || (packageFragmentProviderPackageFragmentProviderForModuleInfo = commonDependenciesContainer.packageFragmentProviderForModuleInfo(moduleInfoComponent1)) == null || (listListOfNotNull = CollectionsKt.listOf(packageFragmentProviderPackageFragmentProviderForModuleInfo)) == null) {
            StorageComponentContainer storageComponentContainer = storageComponentContainerCreateContainerToResolveCommonCode;
            listListOfNotNull = CollectionsKt.listOfNotNull((Object[]) new PackageFragmentProvider[]{((ResolveSession) DslKt.getService(storageComponentContainer, ResolveSession.class)).getPackageFragmentProvider(), DslKt.getService(storageComponentContainer, MetadataPackageFragmentProvider.class), packageFragmentProviderCreatePackageFragmentProvider});
        }
        return new ResolverForModule(new CompositePackageFragmentProvider(listListOfNotNull, "CompositeProvider@CommonResolver for " + moduleDescriptor), storageComponentContainerCreateContainerToResolveCommonCode);
    }

    /* JADX INFO: compiled from: CommonResolverForModuleFactory.kt */
    @Metadata(d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J~\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u001a\b\u0002\u0010\u0012\u001a\u0014\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0014\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u00132\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u00162\u0018\u0010\u0017\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001a0\u0019\u0012\u0004\u0012\u00020\u001b0\u0018¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/analyzer/common/CommonResolverForModuleFactory$Companion;", "", "()V", "analyzeFiles", "Lorg/jetbrains/kotlin/analyzer/AnalysisResult;", "files", "", "Lorg/jetbrains/kotlin/psi/KtFile;", "moduleName", "Lorg/jetbrains/kotlin/name/Name;", "dependOnBuiltIns", "", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "targetPlatform", "Lorg/jetbrains/kotlin/platform/TargetPlatform;", "targetEnvironment", "Lorg/jetbrains/kotlin/resolve/TargetEnvironment;", "capabilities", "", "Lorg/jetbrains/kotlin/descriptors/ModuleCapability;", "dependenciesContainer", "Lorg/jetbrains/kotlin/analyzer/common/CommonDependenciesContainer;", "metadataPartProviderFactory", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/analyzer/ModuleContent;", "Lorg/jetbrains/kotlin/analyzer/ModuleInfo;", "Lorg/jetbrains/kotlin/serialization/deserialization/MetadataPartProvider;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX WARN: Code duplicated, block: B:60:0x01c9  */
        /* JADX WARN: Code duplicated, block: B:71:? A[RETURN, SYNTHETIC] */
        /* JADX WARN: Multi-variable type inference failed */
        public final AnalysisResult analyzeFiles(Collection<? extends KtFile> files, Name moduleName, boolean dependOnBuiltIns, final LanguageVersionSettings languageVersionSettings, TargetPlatform targetPlatform, TargetEnvironment targetEnvironment, Map<ModuleCapability<?>, ? extends Object> capabilities, CommonDependenciesContainer dependenciesContainer, Function1<? super ModuleContent<? extends ModuleInfo>, ? extends MetadataPartProvider> metadataPartProviderFactory) {
            Project project;
            LinkedHashMap linkedHashMap;
            AnalysisResult analysisResultSuccess;
            AnalysisResult analysisResultAnalysisCompleted;
            List<ModuleInfo> moduleInfos;
            List<ModuleInfo> moduleInfos2;
            Intrinsics.checkNotNullParameter(files, "files");
            Intrinsics.checkNotNullParameter(moduleName, "moduleName");
            Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
            Intrinsics.checkNotNullParameter(targetPlatform, "targetPlatform");
            Intrinsics.checkNotNullParameter(targetEnvironment, "targetEnvironment");
            Intrinsics.checkNotNullParameter(capabilities, "capabilities");
            Intrinsics.checkNotNullParameter(metadataPartProviderFactory, "metadataPartProviderFactory");
            List list = (dependenciesContainer == null || (moduleInfos2 = dependenciesContainer.getModuleInfos()) == null) ? null : CollectionsKt.toList(moduleInfos2);
            if (list == null) {
                list = CollectionsKt.emptyList();
            }
            List list2 = list;
            List<ModuleInfo> refinesModuleInfos = dependenciesContainer != null ? dependenciesContainer.getRefinesModuleInfos() : null;
            if (refinesModuleInfos == null) {
                refinesModuleInfos = CollectionsKt.emptyList();
            }
            List<ModuleInfo> list3 = refinesModuleInfos;
            List<ModuleInfo> friendModuleInfos = dependenciesContainer != null ? dependenciesContainer.getFriendModuleInfos() : null;
            if (friendModuleInfos == null) {
                friendModuleInfos = CollectionsKt.emptyList();
            }
            SourceModuleInfo sourceModuleInfo = new SourceModuleInfo(moduleName, capabilities, list2, list3, targetPlatform, friendModuleInfos, dependOnBuiltIns);
            KtFile ktFile = (KtFile) CollectionsKt.firstOrNull(files);
            if (ktFile == null || (project = ktFile.getProject()) == null) {
                throw new AssertionError("No files to analyze");
            }
            LanguageVersionSettings languageVersionSettings2 = new LanguageVersionSettings() { // from class: org.jetbrains.kotlin.analyzer.common.CommonResolverForModuleFactory$Companion$analyzeFiles$multiplatformLanguageSettings$1
                private final /* synthetic */ LanguageVersionSettings $$delegate_0;

                @Override // org.jetbrains.kotlin.config.LanguageVersionSettings
                public ApiVersion getApiVersion() {
                    return this.$$delegate_0.getApiVersion();
                }

                @Override // org.jetbrains.kotlin.config.LanguageVersionSettings
                public <T> T getFlag(AnalysisFlag<? extends T> flag) {
                    Intrinsics.checkNotNullParameter(flag, "flag");
                    return (T) this.$$delegate_0.getFlag(flag);
                }

                @Override // org.jetbrains.kotlin.config.LanguageVersionSettings
                public LanguageVersion getLanguageVersion() {
                    return this.$$delegate_0.getLanguageVersion();
                }

                @Override // org.jetbrains.kotlin.config.LanguageVersionSettings
                public boolean isPreRelease() {
                    return this.$$delegate_0.isPreRelease();
                }

                @Override // org.jetbrains.kotlin.config.LanguageVersionSettings
                public boolean supportsFeature(LanguageFeature feature) {
                    Intrinsics.checkNotNullParameter(feature, "feature");
                    return this.$$delegate_0.supportsFeature(feature);
                }

                {
                    this.$$delegate_0 = this.$languageVersionSettings;
                }

                @Override // org.jetbrains.kotlin.config.LanguageVersionSettings
                public LanguageFeature.State getFeatureSupport(LanguageFeature feature) {
                    Intrinsics.checkNotNullParameter(feature, "feature");
                    return feature == LanguageFeature.MultiPlatformProjects ? LanguageFeature.State.ENABLED : this.$languageVersionSettings.getFeatureSupport(feature);
                }
            };
            CommonResolverForModuleFactory commonResolverForModuleFactory = new CommonResolverForModuleFactory(new CommonAnalysisParameters(metadataPartProviderFactory, null, 2, null), targetEnvironment, targetPlatform, false, dependenciesContainer);
            ProjectContext ProjectContext = ContextKt.ProjectContext(project, "metadata serializer");
            SourceModuleInfo sourceModuleInfo2 = sourceModuleInfo;
            CommonResolverForModuleFactory commonResolverForModuleFactory2 = commonResolverForModuleFactory;
            GlobalSearchScope globalSearchScopeAllScope = GlobalSearchScope.allScope(project);
            Intrinsics.checkNotNullExpressionValue(globalSearchScopeAllScope, "allScope(...)");
            LanguageVersionSettings languageVersionSettings3 = languageVersionSettings2;
            if (dependenciesContainer == null || (moduleInfos = dependenciesContainer.getModuleInfos()) == null) {
                linkedHashMap = null;
            } else {
                List<ModuleInfo> list4 = moduleInfos;
                LinkedHashMap linkedHashMap2 = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(list4, 10)), 16));
                for (Object obj : list4) {
                    linkedHashMap2.put(obj, dependenciesContainer.moduleDescriptorForModuleInfo((ModuleInfo) obj));
                }
                linkedHashMap = linkedHashMap2;
            }
            ResolverForSingleModuleProject resolverForSingleModuleProject = new ResolverForSingleModuleProject("sources for metadata serializer", ProjectContext, sourceModuleInfo2, commonResolverForModuleFactory2, globalSearchScopeAllScope, null, languageVersionSettings3, files, null, linkedHashMap == null ? MapsKt.emptyMap() : linkedHashMap, 288, null);
            ModuleDescriptorImpl moduleDescriptorImplDescriptorForModule = resolverForSingleModuleProject.mo6073descriptorForModule((ModuleInfo) sourceModuleInfo2);
            if (dependenciesContainer != null) {
                dependenciesContainer.registerDependencyForAllModules(sourceModuleInfo2, moduleDescriptorImplDescriptorForModule);
            }
            ComponentProvider componentProvider = resolverForSingleModuleProject.resolverForModule(sourceModuleInfo2).getComponentProvider();
            List<AnalysisHandlerExtension> instances = AnalysisHandlerExtension.INSTANCE.getInstances(project);
            BindingTrace bindingTrace = (BindingTrace) DslKt.getService(componentProvider, BindingTrace.class);
            List<AnalysisHandlerExtension> list5 = instances;
            Iterator<T> it = list5.iterator();
            do {
                if (!it.hasNext()) {
                    analysisResultSuccess = null;
                    break;
                }
                analysisResultSuccess = ((AnalysisHandlerExtension) it.next()).doAnalysis(project, moduleDescriptorImplDescriptorForModule, ProjectContext, files, bindingTrace, componentProvider);
            } while (analysisResultSuccess == null);
            if (analysisResultSuccess == null) {
                LazyTopDownAnalyzer.analyzeDeclarations$default((LazyTopDownAnalyzer) DslKt.getService(componentProvider, LazyTopDownAnalyzer.class), TopDownAnalysisMode.TopLevelDeclarations, files, null, null, 12, null);
                AnalysisResult.Companion companion = AnalysisResult.INSTANCE;
                BindingContext context = bindingTrace.getContext();
                Intrinsics.checkNotNullExpressionValue(context, "getBindingContext(...)");
                analysisResultSuccess = companion.success(context, moduleDescriptorImplDescriptorForModule);
            }
            Iterator<T> it2 = list5.iterator();
            while (it2.hasNext()) {
                analysisResultAnalysisCompleted = ((AnalysisHandlerExtension) it2.next()).analysisCompleted(project, moduleDescriptorImplDescriptorForModule, bindingTrace, files);
                if (analysisResultAnalysisCompleted != null) {
                    if (analysisResultAnalysisCompleted == null) {
                        return analysisResultSuccess;
                    }
                    return analysisResultAnalysisCompleted;
                }
            }
            analysisResultAnalysisCompleted = null;
            if (analysisResultAnalysisCompleted == null) {
                return analysisResultSuccess;
            }
            return analysisResultAnalysisCompleted;
        }
    }
}
