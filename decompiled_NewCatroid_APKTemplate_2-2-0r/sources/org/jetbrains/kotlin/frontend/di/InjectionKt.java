package org.jetbrains.kotlin.frontend.di;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cfg.ControlFlowInformationProvider;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.container.ContainerKt;
import org.jetbrains.kotlin.container.DslKt;
import org.jetbrains.kotlin.container.StorageComponentContainer;
import org.jetbrains.kotlin.context.ModuleContext;
import org.jetbrains.kotlin.contracts.ContractDeserializerImpl;
import org.jetbrains.kotlin.extensions.StorageComponentContainerContributor;
import org.jetbrains.kotlin.extensions.TypeAttributeTranslatorExtension;
import org.jetbrains.kotlin.idea.MainFunctionDetector;
import org.jetbrains.kotlin.incremental.components.EnumWhenTracker;
import org.jetbrains.kotlin.incremental.components.ExpectActualTracker;
import org.jetbrains.kotlin.incremental.components.InlineConstTracker;
import org.jetbrains.kotlin.incremental.components.LookupTracker;
import org.jetbrains.kotlin.konan.library.NativeLibraryConstantsKt;
import org.jetbrains.kotlin.platform.SimplePlatform;
import org.jetbrains.kotlin.platform.TargetPlatform;
import org.jetbrains.kotlin.platform.TargetPlatformKt;
import org.jetbrains.kotlin.platform.TargetPlatformVersion;
import org.jetbrains.kotlin.resolve.AnnotationResolverImpl;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.BodyResolveCache;
import org.jetbrains.kotlin.resolve.BodyResolver;
import org.jetbrains.kotlin.resolve.CliSealedClassInheritorsProvider;
import org.jetbrains.kotlin.resolve.CompilerDeserializationConfiguration;
import org.jetbrains.kotlin.resolve.LazyTopDownAnalyzer;
import org.jetbrains.kotlin.resolve.ModuleStructureOracle;
import org.jetbrains.kotlin.resolve.PlatformConfiguratorBaseKt;
import org.jetbrains.kotlin.resolve.PlatformDependentAnalyzerServices;
import org.jetbrains.kotlin.resolve.SealedClassInheritorsProvider;
import org.jetbrains.kotlin.resolve.StatementFilter;
import org.jetbrains.kotlin.resolve.SupertypeLoopCheckerImpl;
import org.jetbrains.kotlin.resolve.TargetEnvironment;
import org.jetbrains.kotlin.resolve.calls.components.ClassicTypeSystemContextForCS;
import org.jetbrains.kotlin.resolve.calls.inference.components.ClassicConstraintSystemUtilContext;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowValueFactoryImpl;
import org.jetbrains.kotlin.resolve.calls.tower.KotlinResolutionStatelessCallbacksImpl;
import org.jetbrains.kotlin.resolve.checkers.ExpectedActualDeclarationChecker;
import org.jetbrains.kotlin.resolve.checkers.OptInUsageChecker;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.lazy.AbsentDescriptorHandler;
import org.jetbrains.kotlin.resolve.lazy.BasicAbsentDescriptorHandler;
import org.jetbrains.kotlin.resolve.lazy.FileScopeProvider;
import org.jetbrains.kotlin.resolve.lazy.KotlinCodeAnalyzer;
import org.jetbrains.kotlin.resolve.lazy.NoTopLevelDescriptorProvider;
import org.jetbrains.kotlin.resolve.lazy.ResolveSession;
import org.jetbrains.kotlin.resolve.lazy.declarations.DeclarationProviderFactory;
import org.jetbrains.kotlin.resolve.scopes.optimization.OptimizingOptions;
import org.jetbrains.kotlin.types.KotlinTypeRefinerImpl;
import org.jetbrains.kotlin.types.checker.KotlinTypePreparator;
import org.jetbrains.kotlin.types.checker.KotlinTypeRefiner;
import org.jetbrains.kotlin.types.checker.NewKotlinTypeCheckerImpl;
import org.jetbrains.kotlin.types.expressions.DeclarationScopeProviderForLocalClassifierAnalyzer;
import org.jetbrains.kotlin.types.expressions.LocalClassDescriptorHolder;
import org.jetbrains.kotlin.types.expressions.LocalLazyDeclarationResolver;
import org.jetbrains.kotlin.util.ProgressManagerBasedCancellationChecker;

/* JADX INFO: compiled from: injection.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000¨\u0001\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u001aP\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0007\u001aX\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015\u001a`\u0010\u0016\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0007\u001aj\u0010\u0016\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\b\u0010\u001d\u001a\u0004\u0018\u00010\u001eH\u0007\u001ar\u0010\u0016\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\b\u0010\u001d\u001a\u0004\u0018\u00010\u001e2\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015\u001a|\u0010\u001f\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\b\u0010\u001d\u001a\u0004\u0018\u00010\u001e2\u0006\u0010 \u001a\u00020!2\b\u0010\"\u001a\u0004\u0018\u00010#H\u0007\u001aX\u0010$\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010%\u001a\u00020&2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010'\u001a\u00020(2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015\u001a@\u0010)\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010*\u001a\u00020+2\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010,\u001a\u00020-2\u0006\u0010\f\u001a\u00020\rH\u0007\u001aP\u0010)\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010*\u001a\u00020+2\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010,\u001a\u00020-2\u0006\u0010\f\u001a\u00020\r2\u0010\u0010.\u001a\f\u0012\u0006\b\u0001\u0012\u00020\u0015\u0018\u00010/\u001a*\u00100\u001a\u000201*\u00020\u00012\u0006\u0010%\u001a\u00020&2\u0006\u00102\u001a\u0002032\u0006\u00104\u001a\u0002052\u0006\u00106\u001a\u000207\u001a>\u00108\u001a\u000201*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\u000b2\u0006\u00109\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\r2\b\b\u0002\u0010\u0010\u001a\u00020\u0011H\u0007\u001aX\u00108\u001a\u000201*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\u000b2\u0006\u00109\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\r2\b\b\u0002\u0010\u0010\u001a\u00020\u00112\b\u0010\u001d\u001a\u0004\u0018\u00010\u001e2\u0010\u0010.\u001a\f\u0012\u0006\b\u0001\u0012\u00020\u0015\u0018\u00010/\u001aZ\u0010:\u001a\u000201*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\u000b2\u0006\u00109\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\r2\b\u0010\u0010\u001a\u0004\u0018\u00010\u00112\b\u0010\u001d\u001a\u0004\u0018\u00010\u001e2\u0006\u0010 \u001a\u00020!2\b\u0010\"\u001a\u0004\u0018\u00010#H\u0007\u001a\f\u0010;\u001a\u000201*\u00020\u0001H\u0002\u001a\n\u0010<\u001a\u000201*\u00020\u0001¨\u0006="}, d2 = {"createContainerForBodyResolve", "Lorg/jetbrains/kotlin/container/StorageComponentContainer;", "moduleContext", "Lorg/jetbrains/kotlin/context/ModuleContext;", "bindingTrace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", NativeLibraryConstantsKt.KONAN_DISTRIBUTION_PLATFORM_LIBS_DIR, "Lorg/jetbrains/kotlin/platform/TargetPlatform;", "statementFilter", "Lorg/jetbrains/kotlin/resolve/StatementFilter;", "analyzerServices", "Lorg/jetbrains/kotlin/resolve/PlatformDependentAnalyzerServices;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "moduleStructureOracle", "Lorg/jetbrains/kotlin/resolve/ModuleStructureOracle;", "sealedProvider", "Lorg/jetbrains/kotlin/resolve/SealedClassInheritorsProvider;", "controlFlowInformationProviderFactory", "Lorg/jetbrains/kotlin/cfg/ControlFlowInformationProvider$Factory;", "absentDescriptorHandler", "Lorg/jetbrains/kotlin/resolve/lazy/AbsentDescriptorHandler;", "createContainerForLazyBodyResolve", "kotlinCodeAnalyzer", "Lorg/jetbrains/kotlin/resolve/lazy/KotlinCodeAnalyzer;", "bodyResolveCache", "Lorg/jetbrains/kotlin/resolve/BodyResolveCache;", "mainFunctionDetectorFactory", "Lorg/jetbrains/kotlin/idea/MainFunctionDetector$Factory;", "optimizingOptions", "Lorg/jetbrains/kotlin/resolve/scopes/optimization/OptimizingOptions;", "createContainerForLazyBodyResolve$default", "params", "", "any", "", "createContainerForLazyLocalClassifierAnalyzer", "lookupTracker", "Lorg/jetbrains/kotlin/incremental/components/LookupTracker;", "localClassDescriptorHolder", "Lorg/jetbrains/kotlin/types/expressions/LocalClassDescriptorHolder;", "createContainerForLazyResolve", "declarationProviderFactory", "Lorg/jetbrains/kotlin/resolve/lazy/declarations/DeclarationProviderFactory;", "targetEnvironment", "Lorg/jetbrains/kotlin/resolve/TargetEnvironment;", "absentDescriptorHandlerClass", "Ljava/lang/Class;", "configureIncrementalCompilation", "", "expectActualTracker", "Lorg/jetbrains/kotlin/incremental/components/ExpectActualTracker;", "inlineConstTracker", "Lorg/jetbrains/kotlin/incremental/components/InlineConstTracker;", "enumWhenTracker", "Lorg/jetbrains/kotlin/incremental/components/EnumWhenTracker;", "configureModule", "trace", "configureModule$default", "configurePlatformIndependentComponents", "configureStandardResolveComponents", "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class InjectionKt {
    @Deprecated(level = DeprecationLevel.HIDDEN, message = "configureModule$default provided for binary backward compatibility")
    public static final /* synthetic */ void configureModule$default(StorageComponentContainer storageComponentContainer, ModuleContext moduleContext, TargetPlatform platform, PlatformDependentAnalyzerServices analyzerServices, BindingTrace trace, LanguageVersionSettings languageVersionSettings, SealedClassInheritorsProvider sealedClassInheritorsProvider, OptimizingOptions optimizingOptions, int i, Object obj) {
        Intrinsics.checkNotNullParameter(storageComponentContainer, "<this>");
        Intrinsics.checkNotNullParameter(moduleContext, "moduleContext");
        Intrinsics.checkNotNullParameter(platform, "platform");
        Intrinsics.checkNotNullParameter(analyzerServices, "analyzerServices");
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        configureModule(storageComponentContainer, moduleContext, platform, analyzerServices, trace, languageVersionSettings, sealedClassInheritorsProvider == null ? CliSealedClassInheritorsProvider.INSTANCE : sealedClassInheritorsProvider, optimizingOptions, null);
    }

    public static /* synthetic */ void configureModule$default(StorageComponentContainer storageComponentContainer, ModuleContext moduleContext, TargetPlatform targetPlatform, PlatformDependentAnalyzerServices platformDependentAnalyzerServices, BindingTrace bindingTrace, LanguageVersionSettings languageVersionSettings, SealedClassInheritorsProvider sealedClassInheritorsProvider, int i, Object obj) {
        if ((i & 32) != 0) {
            sealedClassInheritorsProvider = CliSealedClassInheritorsProvider.INSTANCE;
        }
        configureModule(storageComponentContainer, moduleContext, targetPlatform, platformDependentAnalyzerServices, bindingTrace, languageVersionSettings, sealedClassInheritorsProvider);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "consider to use configureModule with OptimizingOptions and Class<out AbsentDescriptorHandler>", replaceWith = @ReplaceWith(expression = "\n            configureModule(\n            moduleContext,\n            platform,\n            analyzerServices,\n            trace,\n            languageVersionSettings,\n            sealedProvider,\n            null,\n            null\n            )", imports = {}))
    public static final void configureModule(StorageComponentContainer storageComponentContainer, ModuleContext moduleContext, TargetPlatform platform, PlatformDependentAnalyzerServices analyzerServices, BindingTrace trace, LanguageVersionSettings languageVersionSettings, SealedClassInheritorsProvider sealedProvider) {
        Intrinsics.checkNotNullParameter(storageComponentContainer, "<this>");
        Intrinsics.checkNotNullParameter(moduleContext, "moduleContext");
        Intrinsics.checkNotNullParameter(platform, "platform");
        Intrinsics.checkNotNullParameter(analyzerServices, "analyzerServices");
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(sealedProvider, "sealedProvider");
        configureModule(storageComponentContainer, moduleContext, platform, analyzerServices, trace, languageVersionSettings, sealedProvider, null, null);
    }

    public static /* synthetic */ void configureModule$default(StorageComponentContainer storageComponentContainer, ModuleContext moduleContext, TargetPlatform targetPlatform, PlatformDependentAnalyzerServices platformDependentAnalyzerServices, BindingTrace bindingTrace, LanguageVersionSettings languageVersionSettings, SealedClassInheritorsProvider sealedClassInheritorsProvider, OptimizingOptions optimizingOptions, Class cls, int i, Object obj) {
        configureModule(storageComponentContainer, moduleContext, targetPlatform, platformDependentAnalyzerServices, bindingTrace, languageVersionSettings, (i & 32) != 0 ? CliSealedClassInheritorsProvider.INSTANCE : sealedClassInheritorsProvider, optimizingOptions, cls);
    }

    public static final void configureModule(StorageComponentContainer storageComponentContainer, ModuleContext moduleContext, TargetPlatform platform, PlatformDependentAnalyzerServices analyzerServices, BindingTrace trace, LanguageVersionSettings languageVersionSettings, SealedClassInheritorsProvider sealedProvider, OptimizingOptions optimizingOptions, Class<? extends AbsentDescriptorHandler> cls) {
        Intrinsics.checkNotNullParameter(storageComponentContainer, "<this>");
        Intrinsics.checkNotNullParameter(moduleContext, "moduleContext");
        Intrinsics.checkNotNullParameter(platform, "platform");
        Intrinsics.checkNotNullParameter(analyzerServices, "analyzerServices");
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(sealedProvider, "sealedProvider");
        DslKt.useInstance(storageComponentContainer, sealedProvider);
        DslKt.useInstance(storageComponentContainer, moduleContext);
        DslKt.useInstance(storageComponentContainer, moduleContext.getModule());
        DslKt.useInstance(storageComponentContainer, moduleContext.getProject());
        DslKt.useInstance(storageComponentContainer, moduleContext.getStorageManager());
        DslKt.useInstance(storageComponentContainer, moduleContext.getModule().getBuiltIns());
        DslKt.useInstance(storageComponentContainer, trace);
        DslKt.useInstance(storageComponentContainer, languageVersionSettings);
        DslKt.useInstanceIfNotNull(storageComponentContainer, optimizingOptions);
        if (cls != null) {
            ContainerKt.registerSingleton(storageComponentContainer, cls);
        }
        DslKt.useInstance(storageComponentContainer, platform);
        DslKt.useInstance(storageComponentContainer, analyzerServices);
        ArrayList arrayList = new ArrayList();
        Iterator<SimplePlatform> it = platform.iterator();
        while (it.hasNext()) {
            TargetPlatformVersion targetPlatformVersion = it.next().getTargetPlatformVersion();
            if (Intrinsics.areEqual(targetPlatformVersion, TargetPlatformVersion.NoVersion.INSTANCE)) {
                targetPlatformVersion = null;
            }
            if (targetPlatformVersion != null) {
                arrayList.add(targetPlatformVersion);
            }
        }
        TargetPlatformVersion.NoVersion noVersion = (TargetPlatformVersion) CollectionsKt.singleOrNull((List) arrayList);
        if (noVersion == null) {
            noVersion = TargetPlatformVersion.NoVersion.INSTANCE;
        }
        DslKt.useInstance(storageComponentContainer, noVersion);
        analyzerServices.getPlatformConfigurator().configureModuleComponents(storageComponentContainer);
        analyzerServices.getPlatformConfigurator().configureModuleDependentCheckers(storageComponentContainer);
        DslKt.useInstance(storageComponentContainer, TypeAttributeTranslatorExtension.INSTANCE.createTranslators(moduleContext.getProject()));
        Iterator<StorageComponentContainerContributor> it2 = StorageComponentContainerContributor.INSTANCE.getInstances(moduleContext.getProject()).iterator();
        while (it2.hasNext()) {
            it2.next().registerModuleComponents(storageComponentContainer, platform, moduleContext.getModule());
        }
        ContainerKt.registerSingleton(storageComponentContainer, NewKotlinTypeCheckerImpl.class);
        if (!DescriptorUtilsKt.isTypeRefinementEnabled(moduleContext.getModule())) {
            DslKt.useInstance(storageComponentContainer, KotlinTypeRefiner.Default.INSTANCE);
        } else {
            ContainerKt.registerSingleton(storageComponentContainer, KotlinTypeRefinerImpl.class);
        }
        DslKt.useInstance(storageComponentContainer, KotlinTypePreparator.Default.INSTANCE);
        configurePlatformIndependentComponents(storageComponentContainer);
    }

    public static final void configureIncrementalCompilation(StorageComponentContainer storageComponentContainer, LookupTracker lookupTracker, ExpectActualTracker expectActualTracker, InlineConstTracker inlineConstTracker, EnumWhenTracker enumWhenTracker) {
        Intrinsics.checkNotNullParameter(storageComponentContainer, "<this>");
        Intrinsics.checkNotNullParameter(lookupTracker, "lookupTracker");
        Intrinsics.checkNotNullParameter(expectActualTracker, "expectActualTracker");
        Intrinsics.checkNotNullParameter(inlineConstTracker, "inlineConstTracker");
        Intrinsics.checkNotNullParameter(enumWhenTracker, "enumWhenTracker");
        DslKt.useInstance(storageComponentContainer, lookupTracker);
        DslKt.useInstance(storageComponentContainer, expectActualTracker);
        DslKt.useInstance(storageComponentContainer, inlineConstTracker);
        DslKt.useInstance(storageComponentContainer, enumWhenTracker);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "consider to use createContainerForBodyResolve with AbsentDescriptorHandler", replaceWith = @ReplaceWith(expression = "\n            createContainerForBodyResolve(\n            moduleContext,\n            bindingTrace,\n            platform,\n            statementFilter,\n            analyzerServices,\n            languageVersionSettings,\n            moduleStructureOracle,\n            sealedProvider,\n            controlFlowInformationProviderFactory,\n            null\n            )", imports = {}))
    public static final StorageComponentContainer createContainerForBodyResolve(ModuleContext moduleContext, BindingTrace bindingTrace, TargetPlatform platform, StatementFilter statementFilter, PlatformDependentAnalyzerServices analyzerServices, LanguageVersionSettings languageVersionSettings, ModuleStructureOracle moduleStructureOracle, SealedClassInheritorsProvider sealedProvider, ControlFlowInformationProvider.Factory controlFlowInformationProviderFactory) {
        Intrinsics.checkNotNullParameter(moduleContext, "moduleContext");
        Intrinsics.checkNotNullParameter(bindingTrace, "bindingTrace");
        Intrinsics.checkNotNullParameter(platform, "platform");
        Intrinsics.checkNotNullParameter(statementFilter, "statementFilter");
        Intrinsics.checkNotNullParameter(analyzerServices, "analyzerServices");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(moduleStructureOracle, "moduleStructureOracle");
        Intrinsics.checkNotNullParameter(sealedProvider, "sealedProvider");
        Intrinsics.checkNotNullParameter(controlFlowInformationProviderFactory, "controlFlowInformationProviderFactory");
        return createContainerForBodyResolve(moduleContext, bindingTrace, platform, statementFilter, analyzerServices, languageVersionSettings, moduleStructureOracle, sealedProvider, controlFlowInformationProviderFactory, null);
    }

    public static final StorageComponentContainer createContainerForBodyResolve(final ModuleContext moduleContext, final BindingTrace bindingTrace, final TargetPlatform platform, final StatementFilter statementFilter, final PlatformDependentAnalyzerServices analyzerServices, final LanguageVersionSettings languageVersionSettings, final ModuleStructureOracle moduleStructureOracle, final SealedClassInheritorsProvider sealedProvider, final ControlFlowInformationProvider.Factory controlFlowInformationProviderFactory, final AbsentDescriptorHandler absentDescriptorHandler) {
        Intrinsics.checkNotNullParameter(moduleContext, "moduleContext");
        Intrinsics.checkNotNullParameter(bindingTrace, "bindingTrace");
        Intrinsics.checkNotNullParameter(platform, "platform");
        Intrinsics.checkNotNullParameter(statementFilter, "statementFilter");
        Intrinsics.checkNotNullParameter(analyzerServices, "analyzerServices");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(moduleStructureOracle, "moduleStructureOracle");
        Intrinsics.checkNotNullParameter(sealedProvider, "sealedProvider");
        Intrinsics.checkNotNullParameter(controlFlowInformationProviderFactory, "controlFlowInformationProviderFactory");
        return PlatformConfiguratorBaseKt.createContainer("BodyResolve", analyzerServices, new Function1<StorageComponentContainer, Unit>() { // from class: org.jetbrains.kotlin.frontend.di.InjectionKt.createContainerForBodyResolve.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
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
                InjectionKt.configureModule(createContainer, moduleContext, platform, analyzerServices, bindingTrace, languageVersionSettings, sealedProvider, null, absentDescriptorHandler == null ? BasicAbsentDescriptorHandler.class : null);
                DslKt.useInstanceIfNotNull(createContainer, absentDescriptorHandler);
                DslKt.useInstance(createContainer, statementFilter);
                DslKt.useInstance(createContainer, BodyResolveCache.ThrowException.INSTANCE);
                ContainerKt.registerSingleton(createContainer, AnnotationResolverImpl.class);
                ContainerKt.registerSingleton(createContainer, BodyResolver.class);
                DslKt.useInstance(createContainer, moduleStructureOracle);
                DslKt.useInstance(createContainer, controlFlowInformationProviderFactory);
                DslKt.useInstance(createContainer, InlineConstTracker.DoNothing.INSTANCE);
            }
        });
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "createContainerForLazyBodyResolve$default provided for binary backward compatibility")
    public static final /* synthetic */ StorageComponentContainer createContainerForLazyBodyResolve$default(ModuleContext moduleContext, KotlinCodeAnalyzer kotlinCodeAnalyzer, BindingTrace bindingTrace, TargetPlatform platform, BodyResolveCache bodyResolveCache, PlatformDependentAnalyzerServices analyzerServices, LanguageVersionSettings languageVersionSettings, ModuleStructureOracle moduleStructureOracle, MainFunctionDetector.Factory mainFunctionDetectorFactory, SealedClassInheritorsProvider sealedProvider, ControlFlowInformationProvider.Factory controlFlowInformationProviderFactory, OptimizingOptions optimizingOptions, int i, Object obj) {
        Intrinsics.checkNotNullParameter(moduleContext, "moduleContext");
        Intrinsics.checkNotNullParameter(kotlinCodeAnalyzer, "kotlinCodeAnalyzer");
        Intrinsics.checkNotNullParameter(bindingTrace, "bindingTrace");
        Intrinsics.checkNotNullParameter(platform, "platform");
        Intrinsics.checkNotNullParameter(bodyResolveCache, "bodyResolveCache");
        Intrinsics.checkNotNullParameter(analyzerServices, "analyzerServices");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(moduleStructureOracle, "moduleStructureOracle");
        Intrinsics.checkNotNullParameter(mainFunctionDetectorFactory, "mainFunctionDetectorFactory");
        Intrinsics.checkNotNullParameter(sealedProvider, "sealedProvider");
        Intrinsics.checkNotNullParameter(controlFlowInformationProviderFactory, "controlFlowInformationProviderFactory");
        return createContainerForLazyBodyResolve(moduleContext, kotlinCodeAnalyzer, bindingTrace, platform, bodyResolveCache, analyzerServices, languageVersionSettings, moduleStructureOracle, mainFunctionDetectorFactory, sealedProvider, controlFlowInformationProviderFactory, optimizingOptions, null);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "consider to use createContainerForLazyBodyResolve with AbsentDescriptorHandler", replaceWith = @ReplaceWith(expression = "\n                createContainerForLazyBodyResolve(\n                moduleContext,\n                kotlinCodeAnalyzer,\n                bindingTrace,\n                platform,\n                bodyResolveCache,\n                analyzerServices,\n                languageVersionSettings,\n                moduleStructureOracle,\n                mainFunctionDetectorFactory,\n                sealedProvider,\n                controlFlowInformationProviderFactory,\n                null,\n                null\n            ) ", imports = {}))
    public static final StorageComponentContainer createContainerForLazyBodyResolve(ModuleContext moduleContext, KotlinCodeAnalyzer kotlinCodeAnalyzer, BindingTrace bindingTrace, TargetPlatform platform, BodyResolveCache bodyResolveCache, PlatformDependentAnalyzerServices analyzerServices, LanguageVersionSettings languageVersionSettings, ModuleStructureOracle moduleStructureOracle, MainFunctionDetector.Factory mainFunctionDetectorFactory, SealedClassInheritorsProvider sealedProvider, ControlFlowInformationProvider.Factory controlFlowInformationProviderFactory) {
        Intrinsics.checkNotNullParameter(moduleContext, "moduleContext");
        Intrinsics.checkNotNullParameter(kotlinCodeAnalyzer, "kotlinCodeAnalyzer");
        Intrinsics.checkNotNullParameter(bindingTrace, "bindingTrace");
        Intrinsics.checkNotNullParameter(platform, "platform");
        Intrinsics.checkNotNullParameter(bodyResolveCache, "bodyResolveCache");
        Intrinsics.checkNotNullParameter(analyzerServices, "analyzerServices");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(moduleStructureOracle, "moduleStructureOracle");
        Intrinsics.checkNotNullParameter(mainFunctionDetectorFactory, "mainFunctionDetectorFactory");
        Intrinsics.checkNotNullParameter(sealedProvider, "sealedProvider");
        Intrinsics.checkNotNullParameter(controlFlowInformationProviderFactory, "controlFlowInformationProviderFactory");
        return createContainerForLazyBodyResolve(moduleContext, kotlinCodeAnalyzer, bindingTrace, platform, bodyResolveCache, analyzerServices, languageVersionSettings, moduleStructureOracle, mainFunctionDetectorFactory, sealedProvider, controlFlowInformationProviderFactory, null, null);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "consider to use createContainerForLazyBodyResolve with AbsentDescriptorHandler", replaceWith = @ReplaceWith(expression = "\n                createContainerForLazyBodyResolve(\n                moduleContext,\n                kotlinCodeAnalyzer,\n                bindingTrace,\n                platform,\n                bodyResolveCache,\n                analyzerServices,\n                languageVersionSettings,\n                moduleStructureOracle,\n                mainFunctionDetectorFactory,\n                sealedProvider,\n                controlFlowInformationProviderFactory,\n                optimizingOptions,\n                null\n            ) ", imports = {}))
    public static final StorageComponentContainer createContainerForLazyBodyResolve(ModuleContext moduleContext, KotlinCodeAnalyzer kotlinCodeAnalyzer, BindingTrace bindingTrace, TargetPlatform platform, BodyResolveCache bodyResolveCache, PlatformDependentAnalyzerServices analyzerServices, LanguageVersionSettings languageVersionSettings, ModuleStructureOracle moduleStructureOracle, MainFunctionDetector.Factory mainFunctionDetectorFactory, SealedClassInheritorsProvider sealedProvider, ControlFlowInformationProvider.Factory controlFlowInformationProviderFactory, OptimizingOptions optimizingOptions) {
        Intrinsics.checkNotNullParameter(moduleContext, "moduleContext");
        Intrinsics.checkNotNullParameter(kotlinCodeAnalyzer, "kotlinCodeAnalyzer");
        Intrinsics.checkNotNullParameter(bindingTrace, "bindingTrace");
        Intrinsics.checkNotNullParameter(platform, "platform");
        Intrinsics.checkNotNullParameter(bodyResolveCache, "bodyResolveCache");
        Intrinsics.checkNotNullParameter(analyzerServices, "analyzerServices");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(moduleStructureOracle, "moduleStructureOracle");
        Intrinsics.checkNotNullParameter(mainFunctionDetectorFactory, "mainFunctionDetectorFactory");
        Intrinsics.checkNotNullParameter(sealedProvider, "sealedProvider");
        Intrinsics.checkNotNullParameter(controlFlowInformationProviderFactory, "controlFlowInformationProviderFactory");
        return createContainerForLazyBodyResolve(moduleContext, kotlinCodeAnalyzer, bindingTrace, platform, bodyResolveCache, analyzerServices, languageVersionSettings, moduleStructureOracle, mainFunctionDetectorFactory, sealedProvider, controlFlowInformationProviderFactory, optimizingOptions, null);
    }

    public static final StorageComponentContainer createContainerForLazyBodyResolve(final ModuleContext moduleContext, final KotlinCodeAnalyzer kotlinCodeAnalyzer, final BindingTrace bindingTrace, final TargetPlatform platform, final BodyResolveCache bodyResolveCache, final PlatformDependentAnalyzerServices analyzerServices, final LanguageVersionSettings languageVersionSettings, final ModuleStructureOracle moduleStructureOracle, final MainFunctionDetector.Factory mainFunctionDetectorFactory, final SealedClassInheritorsProvider sealedProvider, final ControlFlowInformationProvider.Factory controlFlowInformationProviderFactory, final OptimizingOptions optimizingOptions, final AbsentDescriptorHandler absentDescriptorHandler) {
        Intrinsics.checkNotNullParameter(moduleContext, "moduleContext");
        Intrinsics.checkNotNullParameter(kotlinCodeAnalyzer, "kotlinCodeAnalyzer");
        Intrinsics.checkNotNullParameter(bindingTrace, "bindingTrace");
        Intrinsics.checkNotNullParameter(platform, "platform");
        Intrinsics.checkNotNullParameter(bodyResolveCache, "bodyResolveCache");
        Intrinsics.checkNotNullParameter(analyzerServices, "analyzerServices");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(moduleStructureOracle, "moduleStructureOracle");
        Intrinsics.checkNotNullParameter(mainFunctionDetectorFactory, "mainFunctionDetectorFactory");
        Intrinsics.checkNotNullParameter(sealedProvider, "sealedProvider");
        Intrinsics.checkNotNullParameter(controlFlowInformationProviderFactory, "controlFlowInformationProviderFactory");
        return PlatformConfiguratorBaseKt.createContainer("LazyBodyResolve", analyzerServices, new Function1<StorageComponentContainer, Unit>() { // from class: org.jetbrains.kotlin.frontend.di.InjectionKt.createContainerForLazyBodyResolve.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
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
                InjectionKt.configureModule(createContainer, moduleContext, platform, analyzerServices, bindingTrace, languageVersionSettings, sealedProvider, optimizingOptions, absentDescriptorHandler != null ? null : BasicAbsentDescriptorHandler.class);
                DslKt.useInstanceIfNotNull(createContainer, absentDescriptorHandler);
                DslKt.useInstance(createContainer, mainFunctionDetectorFactory);
                DslKt.useInstance(createContainer, kotlinCodeAnalyzer);
                FileScopeProvider fileScopeProvider = kotlinCodeAnalyzer.getFileScopeProvider();
                Intrinsics.checkNotNullExpressionValue(fileScopeProvider, "getFileScopeProvider(...)");
                DslKt.useInstance(createContainer, fileScopeProvider);
                DslKt.useInstance(createContainer, bodyResolveCache);
                ContainerKt.registerSingleton(createContainer, AnnotationResolverImpl.class);
                ContainerKt.registerSingleton(createContainer, LazyTopDownAnalyzer.class);
                DslKt.useInstance(createContainer, moduleStructureOracle);
                DslKt.useInstance(createContainer, controlFlowInformationProviderFactory);
                DslKt.useInstance(createContainer, InlineConstTracker.DoNothing.INSTANCE);
                if (TargetPlatformKt.isCommon(platform)) {
                    ContainerKt.registerSingleton(createContainer, ExpectedActualDeclarationChecker.class);
                }
            }
        });
    }

    public static final StorageComponentContainer createContainerForLazyLocalClassifierAnalyzer(final ModuleContext moduleContext, final BindingTrace bindingTrace, final TargetPlatform platform, final LookupTracker lookupTracker, final LanguageVersionSettings languageVersionSettings, final StatementFilter statementFilter, final LocalClassDescriptorHolder localClassDescriptorHolder, final PlatformDependentAnalyzerServices analyzerServices, final ControlFlowInformationProvider.Factory controlFlowInformationProviderFactory, final AbsentDescriptorHandler absentDescriptorHandler) {
        Intrinsics.checkNotNullParameter(moduleContext, "moduleContext");
        Intrinsics.checkNotNullParameter(bindingTrace, "bindingTrace");
        Intrinsics.checkNotNullParameter(platform, "platform");
        Intrinsics.checkNotNullParameter(lookupTracker, "lookupTracker");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(statementFilter, "statementFilter");
        Intrinsics.checkNotNullParameter(localClassDescriptorHolder, "localClassDescriptorHolder");
        Intrinsics.checkNotNullParameter(analyzerServices, "analyzerServices");
        Intrinsics.checkNotNullParameter(controlFlowInformationProviderFactory, "controlFlowInformationProviderFactory");
        return PlatformConfiguratorBaseKt.createContainer("LocalClassifierAnalyzer", analyzerServices, new Function1<StorageComponentContainer, Unit>() { // from class: org.jetbrains.kotlin.frontend.di.InjectionKt.createContainerForLazyLocalClassifierAnalyzer.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
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
                InjectionKt.configureModule$default(createContainer, moduleContext, platform, analyzerServices, bindingTrace, languageVersionSettings, null, null, null, 32, null);
                AbsentDescriptorHandler absentDescriptorHandler2 = absentDescriptorHandler;
                if (absentDescriptorHandler2 != null) {
                    DslKt.useInstance(createContainer, absentDescriptorHandler2);
                }
                DslKt.useInstance(createContainer, localClassDescriptorHolder);
                DslKt.useInstance(createContainer, lookupTracker);
                DslKt.useInstance(createContainer, ExpectActualTracker.DoNothing.INSTANCE);
                DslKt.useInstance(createContainer, InlineConstTracker.DoNothing.INSTANCE);
                DslKt.useInstance(createContainer, EnumWhenTracker.DoNothing.INSTANCE);
                ContainerKt.registerSingleton(createContainer, LazyTopDownAnalyzer.class);
                DslKt.useInstance(createContainer, NoTopLevelDescriptorProvider.INSTANCE);
                TargetEnvironment.INSTANCE.configureCompilerEnvironment(createContainer);
                DslKt.useInstance(createContainer, controlFlowInformationProviderFactory);
                DslKt.useInstance(createContainer, FileScopeProvider.ThrowException.INSTANCE);
                ContainerKt.registerSingleton(createContainer, AnnotationResolverImpl.class);
                ContainerKt.registerSingleton(createContainer, DeclarationScopeProviderForLocalClassifierAnalyzer.class);
                ContainerKt.registerSingleton(createContainer, LocalLazyDeclarationResolver.class);
                DslKt.useInstance(createContainer, statementFilter);
            }
        });
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "consider to use createContainerForLazyResolve with absentDescriptorHandlerClass", replaceWith = @ReplaceWith(expression = "\n        createContainerForLazyResolve(\n            moduleContext,\n            declarationProviderFactory,\n            bindingTrace,\n            platform,\n            analyzerServices,\n            targetEnvironment,\n            languageVersionSettings,\n            null)\n        ", imports = {}))
    public static final StorageComponentContainer createContainerForLazyResolve(ModuleContext moduleContext, DeclarationProviderFactory declarationProviderFactory, BindingTrace bindingTrace, TargetPlatform platform, PlatformDependentAnalyzerServices analyzerServices, TargetEnvironment targetEnvironment, LanguageVersionSettings languageVersionSettings) {
        Intrinsics.checkNotNullParameter(moduleContext, "moduleContext");
        Intrinsics.checkNotNullParameter(declarationProviderFactory, "declarationProviderFactory");
        Intrinsics.checkNotNullParameter(bindingTrace, "bindingTrace");
        Intrinsics.checkNotNullParameter(platform, "platform");
        Intrinsics.checkNotNullParameter(analyzerServices, "analyzerServices");
        Intrinsics.checkNotNullParameter(targetEnvironment, "targetEnvironment");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        return createContainerForLazyResolve(moduleContext, declarationProviderFactory, bindingTrace, platform, analyzerServices, targetEnvironment, languageVersionSettings, null);
    }

    public static final StorageComponentContainer createContainerForLazyResolve(final ModuleContext moduleContext, final DeclarationProviderFactory declarationProviderFactory, final BindingTrace bindingTrace, final TargetPlatform platform, final PlatformDependentAnalyzerServices analyzerServices, final TargetEnvironment targetEnvironment, final LanguageVersionSettings languageVersionSettings, final Class<? extends AbsentDescriptorHandler> cls) {
        Intrinsics.checkNotNullParameter(moduleContext, "moduleContext");
        Intrinsics.checkNotNullParameter(declarationProviderFactory, "declarationProviderFactory");
        Intrinsics.checkNotNullParameter(bindingTrace, "bindingTrace");
        Intrinsics.checkNotNullParameter(platform, "platform");
        Intrinsics.checkNotNullParameter(analyzerServices, "analyzerServices");
        Intrinsics.checkNotNullParameter(targetEnvironment, "targetEnvironment");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        return PlatformConfiguratorBaseKt.createContainer("LazyResolve", analyzerServices, new Function1<StorageComponentContainer, Unit>() { // from class: org.jetbrains.kotlin.frontend.di.InjectionKt.createContainerForLazyResolve.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
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
                InjectionKt.configureModule$default(createContainer, moduleContext, platform, analyzerServices, bindingTrace, languageVersionSettings, null, null, cls, 32, null);
                InjectionKt.configureStandardResolveComponents(createContainer);
                DslKt.useInstance(createContainer, declarationProviderFactory);
                DslKt.useInstance(createContainer, InlineConstTracker.DoNothing.INSTANCE);
                targetEnvironment.configure(createContainer);
            }
        });
    }

    private static final void configurePlatformIndependentComponents(StorageComponentContainer storageComponentContainer) {
        ContainerKt.registerSingleton(storageComponentContainer, SupertypeLoopCheckerImpl.class);
        ContainerKt.registerSingleton(storageComponentContainer, KotlinResolutionStatelessCallbacksImpl.class);
        ContainerKt.registerSingleton(storageComponentContainer, DataFlowValueFactoryImpl.class);
        ContainerKt.registerSingleton(storageComponentContainer, OptInUsageChecker.class);
        ContainerKt.registerSingleton(storageComponentContainer, OptInUsageChecker.Overrides.class);
        ContainerKt.registerSingleton(storageComponentContainer, OptInUsageChecker.ClassifierUsage.class);
        ContainerKt.registerSingleton(storageComponentContainer, ContractDeserializerImpl.class);
        ContainerKt.registerSingleton(storageComponentContainer, CompilerDeserializationConfiguration.class);
        ContainerKt.registerSingleton(storageComponentContainer, ClassicTypeSystemContextForCS.class);
        ContainerKt.registerSingleton(storageComponentContainer, ClassicConstraintSystemUtilContext.class);
        DslKt.useInstance(storageComponentContainer, ProgressManagerBasedCancellationChecker.INSTANCE);
    }

    public static final void configureStandardResolveComponents(StorageComponentContainer storageComponentContainer) {
        Intrinsics.checkNotNullParameter(storageComponentContainer, "<this>");
        ContainerKt.registerSingleton(storageComponentContainer, ResolveSession.class);
        ContainerKt.registerSingleton(storageComponentContainer, LazyTopDownAnalyzer.class);
        ContainerKt.registerSingleton(storageComponentContainer, AnnotationResolverImpl.class);
    }
}
