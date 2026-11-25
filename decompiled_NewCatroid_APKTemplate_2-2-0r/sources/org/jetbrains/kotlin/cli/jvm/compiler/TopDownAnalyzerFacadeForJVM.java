package org.jetbrains.kotlin.cli.jvm.compiler;

import com.google.android.gms.cast.CastStatusCodes;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.jetbrains.kotlin.analyzer.AnalysisResult;
import org.jetbrains.kotlin.builtins.jvm.JvmBuiltIns;
import org.jetbrains.kotlin.builtins.jvm.JvmBuiltInsPackageFragmentProvider;
import org.jetbrains.kotlin.cli.jvm.config.ClassicFrontendSpecificJvmConfigurationKeys;
import org.jetbrains.kotlin.com.intellij.ide.highlighter.JavaFileType;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.search.DelegatingGlobalSearchScope;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.config.CommonConfigurationKeys;
import org.jetbrains.kotlin.config.CommonConfigurationKeysKt;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.config.JVMConfigurationKeys;
import org.jetbrains.kotlin.config.JvmTarget;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.container.ComponentProvider;
import org.jetbrains.kotlin.container.ContainerKt;
import org.jetbrains.kotlin.container.DslKt;
import org.jetbrains.kotlin.container.StorageComponentContainer;
import org.jetbrains.kotlin.context.ContextKt;
import org.jetbrains.kotlin.context.ModuleContext;
import org.jetbrains.kotlin.context.MutableModuleContext;
import org.jetbrains.kotlin.context.ProjectContext;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleCapability;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentProvider;
import org.jetbrains.kotlin.descriptors.PackageFragmentProviderOptimized;
import org.jetbrains.kotlin.descriptors.impl.CompositePackageFragmentProvider;
import org.jetbrains.kotlin.descriptors.impl.ModuleDescriptorImpl;
import org.jetbrains.kotlin.frontend.java.di.InjectionKt;
import org.jetbrains.kotlin.incremental.components.EnumWhenTracker;
import org.jetbrains.kotlin.incremental.components.ExpectActualTracker;
import org.jetbrains.kotlin.incremental.components.InlineConstTracker;
import org.jetbrains.kotlin.incremental.components.LookupTracker;
import org.jetbrains.kotlin.javac.components.JavacBasedClassFinder;
import org.jetbrains.kotlin.javac.components.JavacBasedSourceElementFactory;
import org.jetbrains.kotlin.javac.components.StubJavaResolverCache;
import org.jetbrains.kotlin.konan.library.NativeLibraryConstantsKt;
import org.jetbrains.kotlin.library.KotlinLibrary;
import org.jetbrains.kotlin.load.java.JavaClassesTracker;
import org.jetbrains.kotlin.load.java.lazy.ModuleClassResolver;
import org.jetbrains.kotlin.load.java.structure.JavaClass;
import org.jetbrains.kotlin.load.java.structure.impl.VirtualFileBoundJavaClass;
import org.jetbrains.kotlin.load.kotlin.DeserializationComponentsForJava;
import org.jetbrains.kotlin.load.kotlin.KotlinClassFinder;
import org.jetbrains.kotlin.load.kotlin.PackagePartProvider;
import org.jetbrains.kotlin.load.kotlin.incremental.IncrementalPackageFragmentProvider;
import org.jetbrains.kotlin.load.kotlin.incremental.IncrementalPackagePartProvider;
import org.jetbrains.kotlin.load.kotlin.incremental.components.IncrementalCompilationComponents;
import org.jetbrains.kotlin.modules.Module;
import org.jetbrains.kotlin.modules.TargetId;
import org.jetbrains.kotlin.modules.TargetIdKt;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.platform.TargetPlatform;
import org.jetbrains.kotlin.platform.jvm.JvmPlatforms;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.CliSealedClassInheritorsProvider;
import org.jetbrains.kotlin.resolve.CompilerEnvironment;
import org.jetbrains.kotlin.resolve.LazyTopDownAnalyzer;
import org.jetbrains.kotlin.resolve.TargetEnvironment;
import org.jetbrains.kotlin.resolve.TopDownAnalysisMode;
import org.jetbrains.kotlin.resolve.calls.tower.ImplicitsExtensionsResolutionFilter;
import org.jetbrains.kotlin.resolve.extensions.AnalysisHandlerExtension;
import org.jetbrains.kotlin.resolve.jvm.JavaDescriptorResolver;
import org.jetbrains.kotlin.resolve.jvm.extensions.PackageFragmentProviderExtension;
import org.jetbrains.kotlin.resolve.jvm.multiplatform.OptionalAnnotationPackageFragmentProvider;
import org.jetbrains.kotlin.resolve.lazy.KotlinCodeAnalyzer;
import org.jetbrains.kotlin.resolve.lazy.declarations.DeclarationProviderFactory;
import org.jetbrains.kotlin.resolve.lazy.declarations.FileBasedDeclarationProviderFactory;
import org.jetbrains.kotlin.serialization.deserialization.DeserializationConfiguration;
import org.jetbrains.kotlin.storage.StorageManager;

/* JADX INFO: compiled from: TopDownAnalyzerFacadeForJVM.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u008a\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\bÆ\u0002\u0018\u00002\u00020\u0001:\u0002-.B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J¨\u0001\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0012\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00110\u000f2 \b\u0002\u0010\u0012\u001a\u001a\u0012\u0004\u0012\u00020\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b\u0012\u0004\u0012\u00020\u00150\u00132\b\b\u0002\u0010\u0016\u001a\u00020\u00102\u000e\b\u0002\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00190\u00182\u000e\b\u0002\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001b0\u00182\u000e\b\u0002\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001b0\u00182\b\b\u0002\u0010\u001d\u001a\u00020\u001eH\u0007JÌ\u0001\u0010\u001f\u001a\u00020 2\u0006\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0012\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00110\u000f2\u001e\u0010\u0012\u001a\u001a\u0012\u0004\u0012\u00020\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b\u0012\u0004\u0012\u00020\u00150\u00132\b\b\u0002\u0010!\u001a\u00020\u001e2\b\b\u0002\u0010\u0016\u001a\u00020\u00102\u000e\b\u0002\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00190\u00182\n\b\u0002\u0010\"\u001a\u0004\u0018\u00010#2\u000e\b\u0002\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001b0\u00182\u000e\b\u0002\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001b0\u00182\u001a\b\u0002\u0010$\u001a\u0014\u0012\b\u0012\u0006\u0012\u0002\b\u00030&\u0012\u0006\u0012\u0004\u0018\u00010\u00010%J>\u0010'\u001a\u00020(2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\r2\b\u0010)\u001a\u0004\u0018\u00010*2\u001a\b\u0002\u0010+\u001a\u0014\u0012\b\u0012\u0006\u0012\u0002\b\u00030&\u0012\u0006\u0012\u0004\u0018\u00010\u00010%H\u0002J\u001c\u0010,\u001a\u00020\u00102\u0006\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b¨\u0006/"}, d2 = {"Lorg/jetbrains/kotlin/cli/jvm/compiler/TopDownAnalyzerFacadeForJVM;", "", "()V", "analyzeFilesWithJavaIntegration", "Lorg/jetbrains/kotlin/analyzer/AnalysisResult;", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "files", "", "Lorg/jetbrains/kotlin/psi/KtFile;", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "configuration", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "packagePartProvider", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/com/intellij/psi/search/GlobalSearchScope;", "Lorg/jetbrains/kotlin/load/kotlin/PackagePartProvider;", "declarationProviderFactory", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/storage/StorageManager;", "Lorg/jetbrains/kotlin/resolve/lazy/declarations/DeclarationProviderFactory;", "sourceModuleSearchScope", "klibList", "", "Lorg/jetbrains/kotlin/library/KotlinLibrary;", "explicitModuleDependencyList", "Lorg/jetbrains/kotlin/descriptors/impl/ModuleDescriptorImpl;", "explicitModuleFriendsList", "explicitCompilerEnvironment", "Lorg/jetbrains/kotlin/resolve/TargetEnvironment;", "createContainer", "Lorg/jetbrains/kotlin/container/ComponentProvider;", "targetEnvironment", "implicitsResolutionFilter", "Lorg/jetbrains/kotlin/resolve/calls/tower/ImplicitsExtensionsResolutionFilter;", "moduleCapabilities", "", "Lorg/jetbrains/kotlin/descriptors/ModuleCapability;", "createModuleContext", "Lorg/jetbrains/kotlin/context/MutableModuleContext;", NativeLibraryConstantsKt.KONAN_DISTRIBUTION_PLATFORM_LIBS_DIR, "Lorg/jetbrains/kotlin/platform/TargetPlatform;", "capabilities", "newModuleSearchScope", "AllJavaSourcesInProjectScope", "SourceOrBinaryModuleClassResolver", "cli-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TopDownAnalyzerFacadeForJVM {
    public static final TopDownAnalyzerFacadeForJVM INSTANCE = new TopDownAnalyzerFacadeForJVM();

    @JvmStatic
    public static final AnalysisResult analyzeFilesWithJavaIntegration(Project project, Collection<? extends KtFile> files, BindingTrace trace, CompilerConfiguration configuration, Function1<? super GlobalSearchScope, ? extends PackagePartProvider> packagePartProvider) {
        Intrinsics.checkNotNullParameter(project, "project");
        Intrinsics.checkNotNullParameter(files, "files");
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        Intrinsics.checkNotNullParameter(packagePartProvider, "packagePartProvider");
        return analyzeFilesWithJavaIntegration$default(project, files, trace, configuration, packagePartProvider, null, null, null, null, null, null, CastStatusCodes.DEVICE_CONNECTION_SUSPENDED, null);
    }

    @JvmStatic
    public static final AnalysisResult analyzeFilesWithJavaIntegration(Project project, Collection<? extends KtFile> files, BindingTrace trace, CompilerConfiguration configuration, Function1<? super GlobalSearchScope, ? extends PackagePartProvider> packagePartProvider, Function2<? super StorageManager, ? super Collection<? extends KtFile>, ? extends DeclarationProviderFactory> declarationProviderFactory) {
        Intrinsics.checkNotNullParameter(project, "project");
        Intrinsics.checkNotNullParameter(files, "files");
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        Intrinsics.checkNotNullParameter(packagePartProvider, "packagePartProvider");
        Intrinsics.checkNotNullParameter(declarationProviderFactory, "declarationProviderFactory");
        return analyzeFilesWithJavaIntegration$default(project, files, trace, configuration, packagePartProvider, declarationProviderFactory, null, null, null, null, null, 1984, null);
    }

    @JvmStatic
    public static final AnalysisResult analyzeFilesWithJavaIntegration(Project project, Collection<? extends KtFile> files, BindingTrace trace, CompilerConfiguration configuration, Function1<? super GlobalSearchScope, ? extends PackagePartProvider> packagePartProvider, Function2<? super StorageManager, ? super Collection<? extends KtFile>, ? extends DeclarationProviderFactory> declarationProviderFactory, GlobalSearchScope sourceModuleSearchScope) {
        Intrinsics.checkNotNullParameter(project, "project");
        Intrinsics.checkNotNullParameter(files, "files");
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        Intrinsics.checkNotNullParameter(packagePartProvider, "packagePartProvider");
        Intrinsics.checkNotNullParameter(declarationProviderFactory, "declarationProviderFactory");
        Intrinsics.checkNotNullParameter(sourceModuleSearchScope, "sourceModuleSearchScope");
        return analyzeFilesWithJavaIntegration$default(project, files, trace, configuration, packagePartProvider, declarationProviderFactory, sourceModuleSearchScope, null, null, null, null, 1920, null);
    }

    @JvmStatic
    public static final AnalysisResult analyzeFilesWithJavaIntegration(Project project, Collection<? extends KtFile> files, BindingTrace trace, CompilerConfiguration configuration, Function1<? super GlobalSearchScope, ? extends PackagePartProvider> packagePartProvider, Function2<? super StorageManager, ? super Collection<? extends KtFile>, ? extends DeclarationProviderFactory> declarationProviderFactory, GlobalSearchScope sourceModuleSearchScope, List<? extends KotlinLibrary> klibList) {
        Intrinsics.checkNotNullParameter(project, "project");
        Intrinsics.checkNotNullParameter(files, "files");
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        Intrinsics.checkNotNullParameter(packagePartProvider, "packagePartProvider");
        Intrinsics.checkNotNullParameter(declarationProviderFactory, "declarationProviderFactory");
        Intrinsics.checkNotNullParameter(sourceModuleSearchScope, "sourceModuleSearchScope");
        Intrinsics.checkNotNullParameter(klibList, "klibList");
        return analyzeFilesWithJavaIntegration$default(project, files, trace, configuration, packagePartProvider, declarationProviderFactory, sourceModuleSearchScope, klibList, null, null, null, 1792, null);
    }

    @JvmStatic
    public static final AnalysisResult analyzeFilesWithJavaIntegration(Project project, Collection<? extends KtFile> files, BindingTrace trace, CompilerConfiguration configuration, Function1<? super GlobalSearchScope, ? extends PackagePartProvider> packagePartProvider, Function2<? super StorageManager, ? super Collection<? extends KtFile>, ? extends DeclarationProviderFactory> declarationProviderFactory, GlobalSearchScope sourceModuleSearchScope, List<? extends KotlinLibrary> klibList, List<ModuleDescriptorImpl> explicitModuleDependencyList) {
        Intrinsics.checkNotNullParameter(project, "project");
        Intrinsics.checkNotNullParameter(files, "files");
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        Intrinsics.checkNotNullParameter(packagePartProvider, "packagePartProvider");
        Intrinsics.checkNotNullParameter(declarationProviderFactory, "declarationProviderFactory");
        Intrinsics.checkNotNullParameter(sourceModuleSearchScope, "sourceModuleSearchScope");
        Intrinsics.checkNotNullParameter(klibList, "klibList");
        Intrinsics.checkNotNullParameter(explicitModuleDependencyList, "explicitModuleDependencyList");
        return analyzeFilesWithJavaIntegration$default(project, files, trace, configuration, packagePartProvider, declarationProviderFactory, sourceModuleSearchScope, klibList, explicitModuleDependencyList, null, null, 1536, null);
    }

    @JvmStatic
    public static final AnalysisResult analyzeFilesWithJavaIntegration(Project project, Collection<? extends KtFile> files, BindingTrace trace, CompilerConfiguration configuration, Function1<? super GlobalSearchScope, ? extends PackagePartProvider> packagePartProvider, Function2<? super StorageManager, ? super Collection<? extends KtFile>, ? extends DeclarationProviderFactory> declarationProviderFactory, GlobalSearchScope sourceModuleSearchScope, List<? extends KotlinLibrary> klibList, List<ModuleDescriptorImpl> explicitModuleDependencyList, List<ModuleDescriptorImpl> explicitModuleFriendsList) {
        Intrinsics.checkNotNullParameter(project, "project");
        Intrinsics.checkNotNullParameter(files, "files");
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        Intrinsics.checkNotNullParameter(packagePartProvider, "packagePartProvider");
        Intrinsics.checkNotNullParameter(declarationProviderFactory, "declarationProviderFactory");
        Intrinsics.checkNotNullParameter(sourceModuleSearchScope, "sourceModuleSearchScope");
        Intrinsics.checkNotNullParameter(klibList, "klibList");
        Intrinsics.checkNotNullParameter(explicitModuleDependencyList, "explicitModuleDependencyList");
        Intrinsics.checkNotNullParameter(explicitModuleFriendsList, "explicitModuleFriendsList");
        return analyzeFilesWithJavaIntegration$default(project, files, trace, configuration, packagePartProvider, declarationProviderFactory, sourceModuleSearchScope, klibList, explicitModuleDependencyList, explicitModuleFriendsList, null, 1024, null);
    }

    private TopDownAnalyzerFacadeForJVM() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX INFO: renamed from: org.jetbrains.kotlin.cli.jvm.compiler.TopDownAnalyzerFacadeForJVM$analyzeFilesWithJavaIntegration$1, reason: invalid class name */
    /* JADX INFO: compiled from: TopDownAnalyzerFacadeForJVM.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class AnonymousClass1 extends FunctionReference implements Function2<StorageManager, Collection<KtFile>, FileBasedDeclarationProviderFactory> {
        public static final AnonymousClass1 INSTANCE = new AnonymousClass1();

        AnonymousClass1() {
            super(2);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "<init>(Lorg/jetbrains/kotlin/storage/StorageManager;Ljava/util/Collection;)V";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "<init>";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(FileBasedDeclarationProviderFactory.class);
        }

        @Override // kotlin.jvm.functions.Function2
        public final FileBasedDeclarationProviderFactory invoke(StorageManager p0, Collection<KtFile> p1) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            Intrinsics.checkNotNullParameter(p1, "p1");
            return new FileBasedDeclarationProviderFactory(p0, p1);
        }
    }

    public static /* synthetic */ AnalysisResult analyzeFilesWithJavaIntegration$default(Project project, Collection collection, BindingTrace bindingTrace, CompilerConfiguration compilerConfiguration, Function1 function1, Function2 function2, GlobalSearchScope globalSearchScope, List list, List list2, List list3, TargetEnvironment targetEnvironment, int i, Object obj) {
        return analyzeFilesWithJavaIntegration(project, collection, bindingTrace, compilerConfiguration, function1, (i & 32) != 0 ? AnonymousClass1.INSTANCE : function2, (i & 64) != 0 ? INSTANCE.newModuleSearchScope(project, collection) : globalSearchScope, (i & 128) != 0 ? CollectionsKt.emptyList() : list, (i & 256) != 0 ? CollectionsKt.emptyList() : list2, (i & 512) != 0 ? CollectionsKt.emptyList() : list3, (i & 1024) != 0 ? CompilerEnvironment.INSTANCE : targetEnvironment);
    }

    @JvmStatic
    public static final AnalysisResult analyzeFilesWithJavaIntegration(Project project, Collection<? extends KtFile> files, BindingTrace trace, CompilerConfiguration configuration, Function1<? super GlobalSearchScope, ? extends PackagePartProvider> packagePartProvider, Function2<? super StorageManager, ? super Collection<? extends KtFile>, ? extends DeclarationProviderFactory> declarationProviderFactory, GlobalSearchScope sourceModuleSearchScope, List<? extends KotlinLibrary> klibList, List<ModuleDescriptorImpl> explicitModuleDependencyList, List<ModuleDescriptorImpl> explicitModuleFriendsList, TargetEnvironment explicitCompilerEnvironment) {
        Intrinsics.checkNotNullParameter(project, "project");
        Intrinsics.checkNotNullParameter(files, "files");
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        Intrinsics.checkNotNullParameter(packagePartProvider, "packagePartProvider");
        Intrinsics.checkNotNullParameter(declarationProviderFactory, "declarationProviderFactory");
        Intrinsics.checkNotNullParameter(sourceModuleSearchScope, "sourceModuleSearchScope");
        Intrinsics.checkNotNullParameter(klibList, "klibList");
        Intrinsics.checkNotNullParameter(explicitModuleDependencyList, "explicitModuleDependencyList");
        Intrinsics.checkNotNullParameter(explicitModuleFriendsList, "explicitModuleFriendsList");
        Intrinsics.checkNotNullParameter(explicitCompilerEnvironment, "explicitCompilerEnvironment");
        ComponentProvider componentProviderCreateContainer$default = createContainer$default(INSTANCE, project, files, trace, configuration, packagePartProvider, declarationProviderFactory, explicitCompilerEnvironment, sourceModuleSearchScope, klibList, null, explicitModuleDependencyList, explicitModuleFriendsList, null, 4608, null);
        ModuleDescriptor moduleDescriptor = (ModuleDescriptor) DslKt.getService(componentProviderCreateContainer$default, ModuleDescriptor.class);
        ModuleContext moduleContext = (ModuleContext) DslKt.getService(componentProviderCreateContainer$default, ModuleContext.class);
        List<AnalysisHandlerExtension> instances = org.jetbrains.kotlin.resolve.jvm.extensions.AnalysisHandlerExtension.INSTANCE.getInstances(project);
        Iterator<AnalysisHandlerExtension> it = instances.iterator();
        while (it.hasNext()) {
            AnalysisResult analysisResultDoAnalysis = it.next().doAnalysis(project, moduleDescriptor, moduleContext, files, trace, componentProviderCreateContainer$default);
            if (analysisResultDoAnalysis != null) {
                AnalysisResult analysisResultAnalyzeFilesWithJavaIntegration$invokeExtensionsOnAnalysisComplete = analyzeFilesWithJavaIntegration$invokeExtensionsOnAnalysisComplete(componentProviderCreateContainer$default, moduleDescriptor, instances, project, trace, files);
                return analysisResultAnalyzeFilesWithJavaIntegration$invokeExtensionsOnAnalysisComplete != null ? analysisResultAnalyzeFilesWithJavaIntegration$invokeExtensionsOnAnalysisComplete : analysisResultDoAnalysis;
            }
        }
        LazyTopDownAnalyzer.analyzeDeclarations$default((LazyTopDownAnalyzer) DslKt.getService(componentProviderCreateContainer$default, LazyTopDownAnalyzer.class), TopDownAnalysisMode.TopLevelDeclarations, files, null, null, 12, null);
        AnalysisResult analysisResultAnalyzeFilesWithJavaIntegration$invokeExtensionsOnAnalysisComplete2 = analyzeFilesWithJavaIntegration$invokeExtensionsOnAnalysisComplete(componentProviderCreateContainer$default, moduleDescriptor, instances, project, trace, files);
        if (analysisResultAnalyzeFilesWithJavaIntegration$invokeExtensionsOnAnalysisComplete2 != null) {
            return analysisResultAnalyzeFilesWithJavaIntegration$invokeExtensionsOnAnalysisComplete2;
        }
        AnalysisResult.Companion companion = AnalysisResult.INSTANCE;
        BindingContext context = trace.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getBindingContext(...)");
        return companion.success(context, moduleDescriptor);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ ComponentProvider createContainer$default(TopDownAnalyzerFacadeForJVM topDownAnalyzerFacadeForJVM, Project project, Collection collection, BindingTrace bindingTrace, CompilerConfiguration compilerConfiguration, Function1 function1, Function2 function2, TargetEnvironment targetEnvironment, GlobalSearchScope globalSearchScope, List list, ImplicitsExtensionsResolutionFilter implicitsExtensionsResolutionFilter, List list2, List list3, Map map, int i, Object obj) {
        return topDownAnalyzerFacadeForJVM.createContainer(project, collection, bindingTrace, compilerConfiguration, function1, function2, (i & 64) != 0 ? CompilerEnvironment.INSTANCE : targetEnvironment, (i & 128) != 0 ? topDownAnalyzerFacadeForJVM.newModuleSearchScope(project, collection) : globalSearchScope, (i & 256) != 0 ? CollectionsKt.emptyList() : list, (i & 512) != 0 ? null : implicitsExtensionsResolutionFilter, (i & 1024) != 0 ? CollectionsKt.emptyList() : list2, (i & 2048) != 0 ? CollectionsKt.emptyList() : list3, (i & 4096) != 0 ? MapsKt.emptyMap() : map);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final ComponentProvider createContainer(Project project, Collection<? extends KtFile> files, BindingTrace trace, CompilerConfiguration configuration, Function1<? super GlobalSearchScope, ? extends PackagePartProvider> packagePartProvider, Function2<? super StorageManager, ? super Collection<? extends KtFile>, ? extends DeclarationProviderFactory> declarationProviderFactory, TargetEnvironment targetEnvironment, GlobalSearchScope sourceModuleSearchScope, List<? extends KotlinLibrary> klibList, ImplicitsExtensionsResolutionFilter implicitsResolutionFilter, List<ModuleDescriptorImpl> explicitModuleDependencyList, List<ModuleDescriptorImpl> explicitModuleFriendsList, Map<ModuleCapability<?>, ? extends Object> moduleCapabilities) {
        ArrayList<TargetId> arrayList;
        Intrinsics.checkNotNullParameter(project, "project");
        Intrinsics.checkNotNullParameter(files, "files");
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        Intrinsics.checkNotNullParameter(packagePartProvider, "packagePartProvider");
        Intrinsics.checkNotNullParameter(declarationProviderFactory, "declarationProviderFactory");
        Intrinsics.checkNotNullParameter(targetEnvironment, "targetEnvironment");
        Intrinsics.checkNotNullParameter(sourceModuleSearchScope, "sourceModuleSearchScope");
        Intrinsics.checkNotNullParameter(klibList, "klibList");
        Intrinsics.checkNotNullParameter(explicitModuleDependencyList, "explicitModuleDependencyList");
        Intrinsics.checkNotNullParameter(explicitModuleFriendsList, "explicitModuleFriendsList");
        Intrinsics.checkNotNullParameter(moduleCapabilities, "moduleCapabilities");
        Object obj = configuration.get(JVMConfigurationKeys.JVM_TARGET, JvmTarget.DEFAULT);
        Intrinsics.checkNotNullExpressionValue(obj, "get(...)");
        LanguageVersionSettings languageVersionSettings = CommonConfigurationKeysKt.getLanguageVersionSettings(configuration);
        TargetPlatform targetPlatformJvmPlatformByTargetVersion = JvmPlatforms.INSTANCE.jvmPlatformByTargetVersion((JvmTarget) obj);
        MutableModuleContext mutableModuleContextCreateModuleContext = createModuleContext(project, configuration, targetPlatformJvmPlatformByTargetVersion, moduleCapabilities);
        StorageManager storageManager = mutableModuleContextCreateModuleContext.getStorageManager();
        ModuleDescriptorImpl module = mutableModuleContextCreateModuleContext.getModule();
        IncrementalCompilationComponents incrementalCompilationComponents = (IncrementalCompilationComponents) configuration.get(JVMConfigurationKeys.INCREMENTAL_COMPILATION_COMPONENTS);
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
        List list = (List) configuration.get(JVMConfigurationKeys.MODULES);
        if (list != null) {
            List list2 = list;
            ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
            Iterator it = list2.iterator();
            while (it.hasNext()) {
                arrayList2.add(TargetIdKt.TargetId((Module) it.next()));
            }
            arrayList = arrayList2;
        } else {
            arrayList = null;
        }
        SourceOrBinaryModuleClassResolver sourceOrBinaryModuleClassResolver = new SourceOrBinaryModuleClassResolver(sourceModuleSearchScope);
        JvmBuiltIns jvmBuiltIns = new JvmBuiltIns(storageManager, JvmBuiltIns.Kind.FALLBACK);
        ModuleDescriptorImpl builtInsModule = jvmBuiltIns.getBuiltInsModule();
        Intrinsics.checkNotNullExpressionValue(builtInsModule, "getBuiltInsModule(...)");
        InjectionKt.initialize(jvmBuiltIns, builtInsModule, languageVersionSettings);
        ModuleDescriptorImpl builtInsModule2 = jvmBuiltIns.getBuiltInsModule();
        Intrinsics.checkNotNullExpressionValue(builtInsModule2, "getBuiltInsModule(...)");
        TopDownAnalyzerFacadeForJVM$createContainer$configureJavaClassFinder$1 topDownAnalyzerFacadeForJVM$createContainer$configureJavaClassFinder$1 = configuration.getBoolean(JVMConfigurationKeys.USE_JAVAC) ? TopDownAnalyzerFacadeForJVM$createContainer$configureJavaClassFinder$1.INSTANCE : null;
        Name nameSpecial = Name.special("<dependencies of " + ((String) configuration.getNotNull(CommonConfigurationKeys.MODULE_NAME)) + '>');
        Intrinsics.checkNotNullExpressionValue(nameSpecial, "special(...)");
        MutableModuleContext mutableModuleContextContextForNewModule$default = ContextKt.ContextForNewModule$default(mutableModuleContextCreateModuleContext, nameSpecial, module.getBuiltIns(), targetPlatformJvmPlatformByTargetVersion, null, 16, null);
        GlobalSearchScope globalSearchScopeNotScope = GlobalSearchScope.notScope(sourceModuleSearchScope);
        ModuleDescriptorImpl moduleDescriptorImpl = module;
        Intrinsics.checkNotNullExpressionValue(globalSearchScopeNotScope, "notScope(...)");
        DeclarationProviderFactory EMPTY = DeclarationProviderFactory.EMPTY;
        Intrinsics.checkNotNullExpressionValue(EMPTY, "EMPTY");
        SourceOrBinaryModuleClassResolver sourceOrBinaryModuleClassResolver2 = sourceOrBinaryModuleClassResolver;
        TopDownAnalyzerFacadeForJVM$createContainer$configureJavaClassFinder$1 topDownAnalyzerFacadeForJVM$createContainer$configureJavaClassFinder$2 = topDownAnalyzerFacadeForJVM$createContainer$configureJavaClassFinder$1;
        StorageComponentContainer storageComponentContainerCreateContainerForLazyResolveWithJava = InjectionKt.createContainerForLazyResolveWithJava(targetPlatformJvmPlatformByTargetVersion, mutableModuleContextContextForNewModule$default, trace, EMPTY, globalSearchScopeNotScope, sourceOrBinaryModuleClassResolver2, targetEnvironment, lookupTracker, expectActualTracker, inlineConstTracker, enumWhenTracker, packagePartProvider.invoke(globalSearchScopeNotScope), languageVersionSettings, true, (950272 & 16384) != 0 ? null : topDownAnalyzerFacadeForJVM$createContainer$configureJavaClassFinder$2, (32768 & 950272) != 0 ? null : null, (65536 & 950272) != 0 ? null : implicitsResolutionFilter, (131072 & 950272) != 0 ? CliSealedClassInheritorsProvider.INSTANCE : null, (262144 & 950272) != 0 ? null : null, (950272 & 524288) != 0 ? null : null);
        sourceOrBinaryModuleClassResolver.setCompiledCodeResolver((JavaDescriptorResolver) DslKt.getService(storageComponentContainerCreateContainerForLazyResolveWithJava, JavaDescriptorResolver.class));
        mutableModuleContextContextForNewModule$default.setDependencies(CollectionsKt.listOf((Object[]) new ModuleDescriptorImpl[]{mutableModuleContextContextForNewModule$default.getModule(), builtInsModule2}));
        mutableModuleContextContextForNewModule$default.initializeModuleContents(new CompositePackageFragmentProvider(CollectionsKt.listOf((Object[]) new PackageFragmentProviderOptimized[]{sourceOrBinaryModuleClassResolver.getCompiledCodeResolver().getPackageFragmentProvider(), DslKt.getService(storageComponentContainerCreateContainerForLazyResolveWithJava, JvmBuiltInsPackageFragmentProvider.class), DslKt.getService(storageComponentContainerCreateContainerForLazyResolveWithJava, OptionalAnnotationPackageFragmentProvider.class)}), "CompositeProvider@TopDownAnalyzerForJvm for dependencies " + mutableModuleContextContextForNewModule$default.getModule()));
        ModuleDescriptorImpl module2 = mutableModuleContextContextForNewModule$default.getModule();
        IncrementalPackagePartProvider incrementalPackagePartProviderInvoke = packagePartProvider.invoke(sourceModuleSearchScope);
        if (arrayList != null && incrementalCompilationComponents != null) {
            ArrayList arrayList3 = arrayList;
            ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList3, 10));
            Iterator it2 = arrayList3.iterator();
            while (it2.hasNext()) {
                arrayList4.add(incrementalCompilationComponents.getIncrementalCache((TargetId) it2.next()));
            }
            incrementalPackagePartProviderInvoke = new IncrementalPackagePartProvider(incrementalPackagePartProviderInvoke, arrayList4);
        }
        StorageComponentContainer storageComponentContainerCreateContainerForLazyResolveWithJava2 = InjectionKt.createContainerForLazyResolveWithJava(targetPlatformJvmPlatformByTargetVersion, mutableModuleContextCreateModuleContext, trace, declarationProviderFactory.invoke(storageManager, files), sourceModuleSearchScope, sourceOrBinaryModuleClassResolver2, targetEnvironment, lookupTracker, expectActualTracker, inlineConstTracker, enumWhenTracker, incrementalPackagePartProviderInvoke, languageVersionSettings, true, (950272 & 16384) != 0 ? null : topDownAnalyzerFacadeForJVM$createContainer$configureJavaClassFinder$2, (32768 & 950272) != 0 ? null : (JavaClassesTracker) configuration.get(ClassicFrontendSpecificJvmConfigurationKeys.JAVA_CLASSES_TRACKER), (65536 & 950272) != 0 ? null : implicitsResolutionFilter, (131072 & 950272) != 0 ? CliSealedClassInheritorsProvider.INSTANCE : null, (262144 & 950272) != 0 ? null : null, (950272 & 524288) != 0 ? null : null);
        InjectionKt.initJvmBuiltInsForTopDownAnalysis(storageComponentContainerCreateContainerForLazyResolveWithJava2);
        IncrementalPackagePartProvider incrementalPackagePartProvider = incrementalPackagePartProviderInvoke instanceof IncrementalPackagePartProvider ? (IncrementalPackagePartProvider) incrementalPackagePartProviderInvoke : null;
        if (incrementalPackagePartProvider != null) {
            incrementalPackagePartProvider.setDeserializationConfiguration((DeserializationConfiguration) DslKt.getService(storageComponentContainerCreateContainerForLazyResolveWithJava2, DeserializationConfiguration.class));
        }
        sourceOrBinaryModuleClassResolver.setSourceCodeResolver((JavaDescriptorResolver) DslKt.getService(storageComponentContainerCreateContainerForLazyResolveWithJava2, JavaDescriptorResolver.class));
        ArrayList arrayList5 = new ArrayList();
        if (incrementalCompilationComponents != null && arrayList != null) {
            for (TargetId targetId : arrayList) {
                arrayList5.add(new IncrementalPackageFragmentProvider(files, moduleDescriptorImpl, storageManager, ((DeserializationComponentsForJava) DslKt.getService(storageComponentContainerCreateContainerForLazyResolveWithJava2, DeserializationComponentsForJava.class)).getComponents(), incrementalCompilationComponents.getIncrementalCache(targetId), targetId, (KotlinClassFinder) DslKt.getService(storageComponentContainerCreateContainerForLazyResolveWithJava2, KotlinClassFinder.class)));
            }
        }
        arrayList5.add(((JavaDescriptorResolver) DslKt.getService(storageComponentContainerCreateContainerForLazyResolveWithJava2, JavaDescriptorResolver.class)).getPackageFragmentProvider());
        Iterator<T> it3 = PackageFragmentProviderExtension.INSTANCE.getInstances(project).iterator();
        while (it3.hasNext()) {
            ArrayList arrayList6 = arrayList5;
            StorageComponentContainer storageComponentContainer = storageComponentContainerCreateContainerForLazyResolveWithJava2;
            ModuleDescriptorImpl moduleDescriptorImpl2 = moduleDescriptorImpl;
            PackageFragmentProvider packageFragmentProvider = ((PackageFragmentProviderExtension) it3.next()).getPackageFragmentProvider(project, moduleDescriptorImpl, storageManager, trace, null, lookupTracker);
            if (packageFragmentProvider != null) {
                arrayList6.add(packageFragmentProvider);
            }
            arrayList5 = arrayList6;
            moduleDescriptorImpl = moduleDescriptorImpl2;
            storageComponentContainerCreateContainerForLazyResolveWithJava2 = storageComponentContainer;
        }
        StorageComponentContainer storageComponentContainer2 = storageComponentContainerCreateContainerForLazyResolveWithJava2;
        ModuleDescriptorImpl moduleDescriptorImpl3 = moduleDescriptorImpl;
        moduleDescriptorImpl3.setDependencies(CollectionsKt.plus((Collection) CollectionsKt.plus((Collection) CollectionsKt.listOf((Object[]) new ModuleDescriptorImpl[]{moduleDescriptorImpl3, module2, builtInsModule2}), (Iterable) TopDownAnalyzerFacadeForJVMKt.getKlibModules(klibList, module2)), (Iterable) explicitModuleDependencyList), SetsKt.plus(SetsKt.setOf(module2), (Iterable) explicitModuleFriendsList));
        moduleDescriptorImpl3.initialize(new CompositePackageFragmentProvider(CollectionsKt.plus((Collection) CollectionsKt.listOf((Object[]) new PackageFragmentProvider[]{((KotlinCodeAnalyzer) DslKt.getService(storageComponentContainer2, KotlinCodeAnalyzer.class)).getPackageFragmentProvider(), DslKt.getService(storageComponentContainer2, OptionalAnnotationPackageFragmentProvider.class)}), (Iterable) arrayList5), "CompositeProvider@TopDownAnalzyerForJvm for " + moduleDescriptorImpl3));
        return storageComponentContainer2;
    }

    public final GlobalSearchScope newModuleSearchScope(Project project, Collection<? extends KtFile> files) {
        Intrinsics.checkNotNullParameter(project, "project");
        Intrinsics.checkNotNullParameter(files, "files");
        Collection<? extends KtFile> collection = files;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection, 10));
        Iterator<T> it = collection.iterator();
        while (it.hasNext()) {
            arrayList.add(((KtFile) it.next()).getVirtualFile());
        }
        GlobalSearchScope globalSearchScopeUniteWith = GlobalSearchScope.filesScope(project, CollectionsKt.toSet(arrayList)).uniteWith(new AllJavaSourcesInProjectScope(project));
        Intrinsics.checkNotNullExpressionValue(globalSearchScopeUniteWith, "uniteWith(...)");
        return globalSearchScopeUniteWith;
    }

    /* JADX INFO: compiled from: TopDownAnalyzerFacadeForJVM.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0011\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0096\u0002J\b\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/cli/jvm/compiler/TopDownAnalyzerFacadeForJVM$AllJavaSourcesInProjectScope;", "Lorg/jetbrains/kotlin/com/intellij/psi/search/DelegatingGlobalSearchScope;", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "(Lcom/intellij/openapi/project/Project;)V", "contains", "", "file", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile;", "toString", "", "cli-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class AllJavaSourcesInProjectScope extends DelegatingGlobalSearchScope {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AllJavaSourcesInProjectScope(Project project) {
            super(GlobalSearchScope.allScope(project));
            Intrinsics.checkNotNullParameter(project, "project");
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.search.DelegatingGlobalSearchScope, org.jetbrains.kotlin.com.intellij.psi.search.SearchScope
        public boolean contains(VirtualFile file) {
            Intrinsics.checkNotNullParameter(file, "file");
            return (Intrinsics.areEqual(file.getExtension(), SuffixConstants.EXTENSION_java) || file.getFileType() == JavaFileType.INSTANCE) && !file.isDirectory();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.search.DelegatingGlobalSearchScope
        public String toString() {
            return "All Java sources in the project";
        }
    }

    /* JADX INFO: compiled from: TopDownAnalyzerFacadeForJVM.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0012\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016R\u001a\u0010\u0005\u001a\u00020\u0006X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001a\u0010\u000b\u001a\u00020\u0006X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\b\"\u0004\b\r\u0010\nR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/cli/jvm/compiler/TopDownAnalyzerFacadeForJVM$SourceOrBinaryModuleClassResolver;", "Lorg/jetbrains/kotlin/load/java/lazy/ModuleClassResolver;", "sourceScope", "Lorg/jetbrains/kotlin/com/intellij/psi/search/GlobalSearchScope;", "(Lcom/intellij/psi/search/GlobalSearchScope;)V", "compiledCodeResolver", "Lorg/jetbrains/kotlin/resolve/jvm/JavaDescriptorResolver;", "getCompiledCodeResolver", "()Lorg/jetbrains/kotlin/resolve/jvm/JavaDescriptorResolver;", "setCompiledCodeResolver", "(Lorg/jetbrains/kotlin/resolve/jvm/JavaDescriptorResolver;)V", "sourceCodeResolver", "getSourceCodeResolver", "setSourceCodeResolver", "resolveClass", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "javaClass", "Lorg/jetbrains/kotlin/load/java/structure/JavaClass;", "cli-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class SourceOrBinaryModuleClassResolver implements ModuleClassResolver {
        public JavaDescriptorResolver compiledCodeResolver;
        public JavaDescriptorResolver sourceCodeResolver;
        private final GlobalSearchScope sourceScope;

        public SourceOrBinaryModuleClassResolver(GlobalSearchScope sourceScope) {
            Intrinsics.checkNotNullParameter(sourceScope, "sourceScope");
            this.sourceScope = sourceScope;
        }

        public final JavaDescriptorResolver getCompiledCodeResolver() {
            JavaDescriptorResolver javaDescriptorResolver = this.compiledCodeResolver;
            if (javaDescriptorResolver != null) {
                return javaDescriptorResolver;
            }
            Intrinsics.throwUninitializedPropertyAccessException("compiledCodeResolver");
            return null;
        }

        public final void setCompiledCodeResolver(JavaDescriptorResolver javaDescriptorResolver) {
            Intrinsics.checkNotNullParameter(javaDescriptorResolver, "<set-?>");
            this.compiledCodeResolver = javaDescriptorResolver;
        }

        public final JavaDescriptorResolver getSourceCodeResolver() {
            JavaDescriptorResolver javaDescriptorResolver = this.sourceCodeResolver;
            if (javaDescriptorResolver != null) {
                return javaDescriptorResolver;
            }
            Intrinsics.throwUninitializedPropertyAccessException("sourceCodeResolver");
            return null;
        }

        public final void setSourceCodeResolver(JavaDescriptorResolver javaDescriptorResolver) {
            Intrinsics.checkNotNullParameter(javaDescriptorResolver, "<set-?>");
            this.sourceCodeResolver = javaDescriptorResolver;
        }

        @Override // org.jetbrains.kotlin.load.java.lazy.ModuleClassResolver
        public ClassDescriptor resolveClass(JavaClass javaClass) {
            JavaDescriptorResolver compiledCodeResolver;
            Intrinsics.checkNotNullParameter(javaClass, "javaClass");
            if ((javaClass instanceof VirtualFileBoundJavaClass) && ((VirtualFileBoundJavaClass) javaClass).isFromSourceCodeInScope(this.sourceScope)) {
                compiledCodeResolver = getSourceCodeResolver();
            } else {
                compiledCodeResolver = getCompiledCodeResolver();
            }
            return compiledCodeResolver.resolveClass(javaClass);
        }
    }

    private final MutableModuleContext createModuleContext(Project project, CompilerConfiguration configuration, TargetPlatform platform, Map<ModuleCapability<?>, ? extends Object> capabilities) {
        ProjectContext ProjectContext = ContextKt.ProjectContext(project, "TopDownAnalyzer for JVM");
        JvmBuiltIns jvmBuiltIns = new JvmBuiltIns(ProjectContext.getStorageManager(), JvmBuiltIns.Kind.FROM_DEPENDENCIES);
        Name nameSpecial = Name.special("<" + ((String) configuration.getNotNull(CommonConfigurationKeys.MODULE_NAME)) + '>');
        Intrinsics.checkNotNullExpressionValue(nameSpecial, "special(...)");
        MutableModuleContext mutableModuleContextContextForNewModule = ContextKt.ContextForNewModule(ProjectContext, nameSpecial, jvmBuiltIns, platform, capabilities);
        jvmBuiltIns.setBuiltInsModule(mutableModuleContextContextForNewModule.getModule());
        return mutableModuleContextContextForNewModule;
    }

    private static final AnalysisResult analyzeFilesWithJavaIntegration$invokeExtensionsOnAnalysisComplete(ComponentProvider componentProvider, ModuleDescriptor moduleDescriptor, List<? extends AnalysisHandlerExtension> list, Project project, BindingTrace bindingTrace, Collection<? extends KtFile> collection) {
        ((JavaClassesTracker) DslKt.getService(componentProvider, JavaClassesTracker.class)).onCompletedAnalysis(moduleDescriptor);
        Iterator<? extends AnalysisHandlerExtension> it = list.iterator();
        while (it.hasNext()) {
            AnalysisResult analysisResultAnalysisCompleted = it.next().analysisCompleted(project, moduleDescriptor, bindingTrace, collection);
            if (analysisResultAnalysisCompleted != null) {
                return analysisResultAnalysisCompleted;
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void createContainer$useJavac(StorageComponentContainer storageComponentContainer) {
        ContainerKt.registerSingleton(storageComponentContainer, JavacBasedClassFinder.class);
        ContainerKt.registerSingleton(storageComponentContainer, StubJavaResolverCache.class);
        ContainerKt.registerSingleton(storageComponentContainer, JavacBasedSourceElementFactory.class);
    }
}
