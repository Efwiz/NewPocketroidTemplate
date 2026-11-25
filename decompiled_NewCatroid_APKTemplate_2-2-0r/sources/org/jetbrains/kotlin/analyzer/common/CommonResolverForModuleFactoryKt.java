package org.jetbrains.kotlin.analyzer.common;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.container.ContainerKt;
import org.jetbrains.kotlin.container.DslKt;
import org.jetbrains.kotlin.container.StorageComponentContainer;
import org.jetbrains.kotlin.context.ModuleContext;
import org.jetbrains.kotlin.frontend.di.InjectionKt;
import org.jetbrains.kotlin.incremental.components.InlineConstTracker;
import org.jetbrains.kotlin.konan.library.NativeLibraryConstantsKt;
import org.jetbrains.kotlin.load.kotlin.MetadataFinderFactory;
import org.jetbrains.kotlin.platform.TargetPlatform;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.PlatformConfiguratorBaseKt;
import org.jetbrains.kotlin.resolve.PlatformDependentAnalyzerServices;
import org.jetbrains.kotlin.resolve.TargetEnvironment;
import org.jetbrains.kotlin.resolve.checkers.ExpectedActualDeclarationChecker;
import org.jetbrains.kotlin.resolve.lazy.AbsentDescriptorHandler;
import org.jetbrains.kotlin.resolve.lazy.declarations.DeclarationProviderFactory;
import org.jetbrains.kotlin.serialization.deserialization.MetadataPackageFragmentProvider;
import org.jetbrains.kotlin.serialization.deserialization.MetadataPartProvider;

/* JADX INFO: compiled from: CommonResolverForModuleFactory.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000T\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\u001aj\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0010\u0010\u0016\u001a\f\u0012\u0006\b\u0001\u0012\u00020\u0018\u0018\u00010\u0017H\u0002\u001a\n\u0010\u0019\u001a\u00020\u001a*\u00020\u0001¨\u0006\u001b"}, d2 = {"createContainerToResolveCommonCode", "Lorg/jetbrains/kotlin/container/StorageComponentContainer;", "moduleContext", "Lorg/jetbrains/kotlin/context/ModuleContext;", "bindingTrace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "declarationProviderFactory", "Lorg/jetbrains/kotlin/resolve/lazy/declarations/DeclarationProviderFactory;", "moduleContentScope", "Lorg/jetbrains/kotlin/com/intellij/psi/search/GlobalSearchScope;", "targetEnvironment", "Lorg/jetbrains/kotlin/resolve/TargetEnvironment;", "metadataPartProvider", "Lorg/jetbrains/kotlin/serialization/deserialization/MetadataPartProvider;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", NativeLibraryConstantsKt.KONAN_DISTRIBUTION_PLATFORM_LIBS_DIR, "Lorg/jetbrains/kotlin/platform/TargetPlatform;", "analyzerServices", "Lorg/jetbrains/kotlin/resolve/PlatformDependentAnalyzerServices;", "shouldCheckExpectActual", "", "absentDescriptorHandlerClass", "Ljava/lang/Class;", "Lorg/jetbrains/kotlin/resolve/lazy/AbsentDescriptorHandler;", "configureCommonSpecificComponents", "", "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CommonResolverForModuleFactoryKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final StorageComponentContainer createContainerToResolveCommonCode(final ModuleContext moduleContext, final BindingTrace bindingTrace, final DeclarationProviderFactory declarationProviderFactory, final GlobalSearchScope globalSearchScope, final TargetEnvironment targetEnvironment, final MetadataPartProvider metadataPartProvider, final LanguageVersionSettings languageVersionSettings, final TargetPlatform targetPlatform, final PlatformDependentAnalyzerServices platformDependentAnalyzerServices, final boolean z, final Class<? extends AbsentDescriptorHandler> cls) {
        return PlatformConfiguratorBaseKt.createContainer("ResolveCommonCode", platformDependentAnalyzerServices, new Function1<StorageComponentContainer, Unit>() { // from class: org.jetbrains.kotlin.analyzer.common.CommonResolverForModuleFactoryKt.createContainerToResolveCommonCode.1
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
                InjectionKt.configureModule$default(createContainer, moduleContext, targetPlatform, platformDependentAnalyzerServices, bindingTrace, languageVersionSettings, null, null, cls, 32, null);
                DslKt.useInstance(createContainer, globalSearchScope);
                DslKt.useInstance(createContainer, declarationProviderFactory);
                InjectionKt.configureStandardResolveComponents(createContainer);
                CommonResolverForModuleFactoryKt.configureCommonSpecificComponents(createContainer);
                DslKt.useInstance(createContainer, metadataPartProvider);
                MetadataFinderFactory metadataFinderFactory = (MetadataFinderFactory) moduleContext.getProject().getService(MetadataFinderFactory.class);
                if (metadataFinderFactory == null) {
                    throw new IllegalStateException("No MetadataFinderFactory in project".toString());
                }
                DslKt.useInstance(createContainer, metadataFinderFactory.create(globalSearchScope));
                targetEnvironment.configure(createContainer);
                if (z) {
                    ContainerKt.registerSingleton(createContainer, ExpectedActualDeclarationChecker.class);
                }
                DslKt.useInstance(createContainer, InlineConstTracker.DoNothing.INSTANCE);
            }
        });
    }

    public static final void configureCommonSpecificComponents(StorageComponentContainer storageComponentContainer) {
        Intrinsics.checkNotNullParameter(storageComponentContainer, "<this>");
        ContainerKt.registerSingleton(storageComponentContainer, MetadataPackageFragmentProvider.class);
    }
}
