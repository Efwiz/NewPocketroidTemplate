package org.jetbrains.kotlin.frontend.java.di;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.builtins.jvm.JvmBuiltIns;
import org.jetbrains.kotlin.builtins.jvm.JvmBuiltInsPackageFragmentProvider;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.config.JvmAnalysisFlags;
import org.jetbrains.kotlin.config.JvmTarget;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.container.ComponentProvider;
import org.jetbrains.kotlin.container.ContainerKt;
import org.jetbrains.kotlin.container.DslKt;
import org.jetbrains.kotlin.container.StorageComponentContainer;
import org.jetbrains.kotlin.context.ModuleContext;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.incremental.components.EnumWhenTracker;
import org.jetbrains.kotlin.incremental.components.ExpectActualTracker;
import org.jetbrains.kotlin.incremental.components.InlineConstTracker;
import org.jetbrains.kotlin.incremental.components.LookupTracker;
import org.jetbrains.kotlin.load.java.AbstractJavaClassFinder;
import org.jetbrains.kotlin.load.java.InternalFlexibleTypeTransformer;
import org.jetbrains.kotlin.load.java.JavaClassFinderImpl;
import org.jetbrains.kotlin.load.java.JavaClassesTracker;
import org.jetbrains.kotlin.load.java.components.FilesByFacadeFqNameIndexer;
import org.jetbrains.kotlin.load.java.components.JavaDeprecationSettings;
import org.jetbrains.kotlin.load.java.components.JavaPropertyInitializerEvaluatorImpl;
import org.jetbrains.kotlin.load.java.components.JavaSourceElementFactoryImpl;
import org.jetbrains.kotlin.load.java.components.LazyResolveBasedCache;
import org.jetbrains.kotlin.load.java.components.SignaturePropagatorImpl;
import org.jetbrains.kotlin.load.java.components.TraceBasedErrorReporter;
import org.jetbrains.kotlin.load.java.lazy.JavaResolverSettings;
import org.jetbrains.kotlin.load.java.lazy.ModuleClassResolver;
import org.jetbrains.kotlin.load.kotlin.DeserializationComponentsForJava;
import org.jetbrains.kotlin.load.kotlin.PackagePartProvider;
import org.jetbrains.kotlin.load.kotlin.VirtualFileFinderFactory;
import org.jetbrains.kotlin.platform.TargetPlatform;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.PlatformConfiguratorBaseKt;
import org.jetbrains.kotlin.resolve.SealedClassInheritorsProvider;
import org.jetbrains.kotlin.resolve.TargetEnvironment;
import org.jetbrains.kotlin.resolve.calls.tower.ImplicitsExtensionsResolutionFilter;
import org.jetbrains.kotlin.resolve.jvm.JavaDescriptorResolver;
import org.jetbrains.kotlin.resolve.jvm.JvmDiagnosticComponents;
import org.jetbrains.kotlin.resolve.jvm.extensions.SyntheticJavaResolveExtension;
import org.jetbrains.kotlin.resolve.jvm.modules.JavaModuleResolver;
import org.jetbrains.kotlin.resolve.jvm.multiplatform.OptionalAnnotationPackageFragmentProvider;
import org.jetbrains.kotlin.resolve.jvm.platform.JvmPlatformAnalyzerServices;
import org.jetbrains.kotlin.resolve.lazy.AbsentDescriptorHandler;
import org.jetbrains.kotlin.resolve.lazy.KotlinCodeAnalyzer;
import org.jetbrains.kotlin.resolve.lazy.declarations.DeclarationProviderFactory;
import org.jetbrains.kotlin.resolve.scopes.optimization.OptimizingOptions;

/* JADX INFO: compiled from: injection.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000¢\u0001\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001aÕ\u0001\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001d2\u001b\b\u0002\u0010\u001e\u001a\u0015\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020 \u0018\u00010\u001f¢\u0006\u0002\b!2\n\b\u0002\u0010\"\u001a\u0004\u0018\u00010#2\n\b\u0002\u0010$\u001a\u0004\u0018\u00010%2\b\b\u0002\u0010&\u001a\u00020'2\n\b\u0002\u0010(\u001a\u0004\u0018\u00010)2\u0012\b\u0002\u0010*\u001a\f\u0012\u0006\b\u0001\u0012\u00020,\u0018\u00010+\u001aO\u0010-\u001a\u00020 *\u00020\u00012\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u001a\u001a\u00020\u001b2\u0019\u0010\u001e\u001a\u0015\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020 \u0018\u00010\u001f¢\u0006\u0002\b!2\b\u0010\"\u001a\u0004\u0018\u00010#2\u0006\u0010\u001c\u001a\u00020\u001d\u001a\n\u0010.\u001a\u00020 *\u00020/\u001a\u001a\u00100\u001a\u00020 *\u0002012\u0006\u00102\u001a\u0002032\u0006\u0010\u001a\u001a\u00020\u001b\u001a\u0012\u00104\u001a\u00020 *\u00020\u00012\u0006\u0010\u0006\u001a\u00020\u0007¨\u00065"}, d2 = {"createContainerForLazyResolveWithJava", "Lorg/jetbrains/kotlin/container/StorageComponentContainer;", "jvmPlatform", "Lorg/jetbrains/kotlin/platform/TargetPlatform;", "moduleContext", "Lorg/jetbrains/kotlin/context/ModuleContext;", "bindingTrace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "declarationProviderFactory", "Lorg/jetbrains/kotlin/resolve/lazy/declarations/DeclarationProviderFactory;", "moduleContentScope", "Lorg/jetbrains/kotlin/com/intellij/psi/search/GlobalSearchScope;", "moduleClassResolver", "Lorg/jetbrains/kotlin/load/java/lazy/ModuleClassResolver;", "targetEnvironment", "Lorg/jetbrains/kotlin/resolve/TargetEnvironment;", "lookupTracker", "Lorg/jetbrains/kotlin/incremental/components/LookupTracker;", "expectActualTracker", "Lorg/jetbrains/kotlin/incremental/components/ExpectActualTracker;", "inlineConstTracker", "Lorg/jetbrains/kotlin/incremental/components/InlineConstTracker;", "enumWhenTracker", "Lorg/jetbrains/kotlin/incremental/components/EnumWhenTracker;", "packagePartProvider", "Lorg/jetbrains/kotlin/load/kotlin/PackagePartProvider;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "useBuiltInsProvider", "", "configureJavaClassFinder", "Lkotlin/Function1;", "", "Lkotlin/ExtensionFunctionType;", "javaClassTracker", "Lorg/jetbrains/kotlin/load/java/JavaClassesTracker;", "implicitsResolutionFilter", "Lorg/jetbrains/kotlin/resolve/calls/tower/ImplicitsExtensionsResolutionFilter;", "sealedInheritorsProvider", "Lorg/jetbrains/kotlin/resolve/SealedClassInheritorsProvider;", "optimizingOptions", "Lorg/jetbrains/kotlin/resolve/scopes/optimization/OptimizingOptions;", "absentDescriptorHandlerClass", "Ljava/lang/Class;", "Lorg/jetbrains/kotlin/resolve/lazy/AbsentDescriptorHandler;", "configureJavaSpecificComponents", "initJvmBuiltInsForTopDownAnalysis", "Lorg/jetbrains/kotlin/container/ComponentProvider;", "initialize", "Lorg/jetbrains/kotlin/builtins/jvm/JvmBuiltIns;", "module", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "initializeJavaSpecificComponents", "frontend.java"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class InjectionKt {
    public static final StorageComponentContainer createContainerForLazyResolveWithJava(final TargetPlatform jvmPlatform, final ModuleContext moduleContext, final BindingTrace bindingTrace, final DeclarationProviderFactory declarationProviderFactory, final GlobalSearchScope moduleContentScope, final ModuleClassResolver moduleClassResolver, final TargetEnvironment targetEnvironment, final LookupTracker lookupTracker, final ExpectActualTracker expectActualTracker, final InlineConstTracker inlineConstTracker, final EnumWhenTracker enumWhenTracker, final PackagePartProvider packagePartProvider, final LanguageVersionSettings languageVersionSettings, final boolean z, final Function1<? super StorageComponentContainer, Unit> function1, final JavaClassesTracker javaClassesTracker, final ImplicitsExtensionsResolutionFilter implicitsExtensionsResolutionFilter, final SealedClassInheritorsProvider sealedInheritorsProvider, final OptimizingOptions optimizingOptions, final Class<? extends AbsentDescriptorHandler> cls) {
        Intrinsics.checkNotNullParameter(jvmPlatform, "jvmPlatform");
        Intrinsics.checkNotNullParameter(moduleContext, "moduleContext");
        Intrinsics.checkNotNullParameter(bindingTrace, "bindingTrace");
        Intrinsics.checkNotNullParameter(declarationProviderFactory, "declarationProviderFactory");
        Intrinsics.checkNotNullParameter(moduleContentScope, "moduleContentScope");
        Intrinsics.checkNotNullParameter(moduleClassResolver, "moduleClassResolver");
        Intrinsics.checkNotNullParameter(targetEnvironment, "targetEnvironment");
        Intrinsics.checkNotNullParameter(lookupTracker, "lookupTracker");
        Intrinsics.checkNotNullParameter(expectActualTracker, "expectActualTracker");
        Intrinsics.checkNotNullParameter(inlineConstTracker, "inlineConstTracker");
        Intrinsics.checkNotNullParameter(enumWhenTracker, "enumWhenTracker");
        Intrinsics.checkNotNullParameter(packagePartProvider, "packagePartProvider");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(sealedInheritorsProvider, "sealedInheritorsProvider");
        StorageComponentContainer storageComponentContainerCreateContainer = PlatformConfiguratorBaseKt.createContainer("LazyResolveWithJava", JvmPlatformAnalyzerServices.INSTANCE, new Function1<StorageComponentContainer, Unit>() { // from class: org.jetbrains.kotlin.frontend.java.di.InjectionKt.createContainerForLazyResolveWithJava.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(StorageComponentContainer storageComponentContainer) {
                invoke2(storageComponentContainer);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(StorageComponentContainer createContainer) {
                Intrinsics.checkNotNullParameter(createContainer, "$this$createContainer");
                org.jetbrains.kotlin.frontend.di.InjectionKt.configureModule(createContainer, moduleContext, jvmPlatform, JvmPlatformAnalyzerServices.INSTANCE, bindingTrace, languageVersionSettings, sealedInheritorsProvider, optimizingOptions, cls);
                org.jetbrains.kotlin.frontend.di.InjectionKt.configureIncrementalCompilation(createContainer, lookupTracker, expectActualTracker, inlineConstTracker, enumWhenTracker);
                org.jetbrains.kotlin.frontend.di.InjectionKt.configureStandardResolveComponents(createContainer);
                DslKt.useInstance(createContainer, moduleContentScope);
                DslKt.useInstance(createContainer, packagePartProvider);
                DslKt.useInstance(createContainer, declarationProviderFactory);
                DslKt.useInstanceIfNotNull(createContainer, implicitsExtensionsResolutionFilter);
                DslKt.useInstance(createContainer, VirtualFileFinderFactory.INSTANCE.getInstance(moduleContext.getProject()).create(moduleContentScope));
                InjectionKt.configureJavaSpecificComponents(createContainer, moduleContext, moduleClassResolver, languageVersionSettings, function1, javaClassesTracker, z);
                targetEnvironment.configure(createContainer);
            }
        });
        initializeJavaSpecificComponents(storageComponentContainerCreateContainer, bindingTrace);
        return storageComponentContainerCreateContainer;
    }

    public static final void initializeJavaSpecificComponents(StorageComponentContainer storageComponentContainer, BindingTrace bindingTrace) {
        Intrinsics.checkNotNullParameter(storageComponentContainer, "<this>");
        Intrinsics.checkNotNullParameter(bindingTrace, "bindingTrace");
        StorageComponentContainer storageComponentContainer2 = storageComponentContainer;
        ((AbstractJavaClassFinder) DslKt.getService(storageComponentContainer2, AbstractJavaClassFinder.class)).initialize(bindingTrace, (KotlinCodeAnalyzer) DslKt.getService(storageComponentContainer2, KotlinCodeAnalyzer.class), (LanguageVersionSettings) DslKt.getService(storageComponentContainer2, LanguageVersionSettings.class), (JvmTarget) DslKt.getService(storageComponentContainer2, JvmTarget.class));
    }

    public static final void initialize(JvmBuiltIns jvmBuiltIns, ModuleDescriptor module, LanguageVersionSettings languageVersionSettings) {
        Intrinsics.checkNotNullParameter(jvmBuiltIns, "<this>");
        Intrinsics.checkNotNullParameter(module, "module");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        jvmBuiltIns.initialize(module, languageVersionSettings.supportsFeature(LanguageFeature.AdditionalBuiltInsMembers));
    }

    public static final void configureJavaSpecificComponents(StorageComponentContainer storageComponentContainer, ModuleContext moduleContext, ModuleClassResolver moduleClassResolver, LanguageVersionSettings languageVersionSettings, Function1<? super StorageComponentContainer, Unit> function1, JavaClassesTracker javaClassesTracker, boolean z) {
        Intrinsics.checkNotNullParameter(storageComponentContainer, "<this>");
        Intrinsics.checkNotNullParameter(moduleContext, "moduleContext");
        Intrinsics.checkNotNullParameter(moduleClassResolver, "moduleClassResolver");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        ContainerKt.registerSingleton(storageComponentContainer, JavaDescriptorResolver.class);
        ContainerKt.registerSingleton(storageComponentContainer, DeserializationComponentsForJava.class);
        DslKt.useInstance(storageComponentContainer, JavaPropertyInitializerEvaluatorImpl.INSTANCE);
        ContainerKt.registerSingleton(storageComponentContainer, SignaturePropagatorImpl.class);
        ContainerKt.registerSingleton(storageComponentContainer, TraceBasedErrorReporter.class);
        DslKt.useInstance(storageComponentContainer, InternalFlexibleTypeTransformer.INSTANCE);
        DslKt.useInstance(storageComponentContainer, JavaDeprecationSettings.INSTANCE);
        DslKt.useInstance(storageComponentContainer, moduleClassResolver);
        DslKt.useInstance(storageComponentContainer, SyntheticJavaResolveExtension.INSTANCE.getProvider(moduleContext.getProject()));
        if (function1 != null) {
            function1.invoke(storageComponentContainer);
        } else {
            ContainerKt.registerSingleton(storageComponentContainer, JavaClassFinderImpl.class);
            ContainerKt.registerSingleton(storageComponentContainer, LazyResolveBasedCache.class);
            ContainerKt.registerSingleton(storageComponentContainer, JavaSourceElementFactoryImpl.class);
        }
        DslKt.useInstance(storageComponentContainer, languageVersionSettings.getFlag(JvmAnalysisFlags.getJavaTypeEnhancementState()));
        KotlinBuiltIns builtIns = moduleContext.getModule().getBuiltIns();
        if (z && (builtIns instanceof JvmBuiltIns)) {
            DslKt.useInstance(storageComponentContainer, ((JvmBuiltIns) builtIns).getCustomizer());
            ContainerKt.registerSingleton(storageComponentContainer, JvmBuiltInsPackageFragmentProvider.class);
        }
        ContainerKt.registerSingleton(storageComponentContainer, OptionalAnnotationPackageFragmentProvider.class);
        if (javaClassesTracker == null) {
            javaClassesTracker = JavaClassesTracker.Default.INSTANCE;
        }
        DslKt.useInstance(storageComponentContainer, javaClassesTracker);
        DslKt.useInstance(storageComponentContainer, JavaResolverSettings.INSTANCE.create(languageVersionSettings.supportsFeature(LanguageFeature.ProhibitUsingNullableTypeParameterAgainstNotNullAnnotated), languageVersionSettings.supportsFeature(LanguageFeature.TypeEnhancementImprovementsInStrictMode), languageVersionSettings.supportsFeature(LanguageFeature.IgnoreNullabilityForErasedValueParameters), languageVersionSettings.supportsFeature(LanguageFeature.EnhanceNullabilityOfPrimitiveArrays)));
        DslKt.useInstance(storageComponentContainer, JavaModuleResolver.INSTANCE.getInstance(moduleContext.getProject()));
        ContainerKt.registerSingleton(storageComponentContainer, FilesByFacadeFqNameIndexer.class);
        ContainerKt.registerSingleton(storageComponentContainer, JvmDiagnosticComponents.class);
    }

    public static final void initJvmBuiltInsForTopDownAnalysis(ComponentProvider componentProvider) {
        Intrinsics.checkNotNullParameter(componentProvider, "<this>");
        initialize((JvmBuiltIns) DslKt.getService(componentProvider, JvmBuiltIns.class), (ModuleDescriptor) DslKt.getService(componentProvider, ModuleDescriptor.class), (LanguageVersionSettings) DslKt.getService(componentProvider, LanguageVersionSettings.class));
    }
}
