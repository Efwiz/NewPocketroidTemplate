package org.jetbrains.kotlin.frontend.js.di;

import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.container.DslKt;
import org.jetbrains.kotlin.container.StorageComponentContainer;
import org.jetbrains.kotlin.context.ModuleContext;
import org.jetbrains.kotlin.descriptors.PackageFragmentProvider;
import org.jetbrains.kotlin.descriptors.impl.CompositePackageFragmentProvider;
import org.jetbrains.kotlin.descriptors.impl.ModuleDescriptorImpl;
import org.jetbrains.kotlin.incremental.components.EnumWhenTracker;
import org.jetbrains.kotlin.incremental.components.ExpectActualTracker;
import org.jetbrains.kotlin.incremental.components.InlineConstTracker;
import org.jetbrains.kotlin.incremental.components.LookupTracker;
import org.jetbrains.kotlin.js.resolve.JsPlatformAnalyzerServices;
import org.jetbrains.kotlin.konan.library.NativeLibraryConstantsKt;
import org.jetbrains.kotlin.platform.TargetPlatform;
import org.jetbrains.kotlin.platform.js.JsPlatforms;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.LazyTopDownAnalyzer;
import org.jetbrains.kotlin.resolve.PlatformConfiguratorBaseKt;
import org.jetbrains.kotlin.resolve.PlatformDependentAnalyzerServices;
import org.jetbrains.kotlin.resolve.TargetEnvironment;
import org.jetbrains.kotlin.resolve.lazy.KotlinCodeAnalyzer;
import org.jetbrains.kotlin.resolve.lazy.declarations.DeclarationProviderFactory;

/* JADX INFO: compiled from: injection.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000Z\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001al\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u00132\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001a\u001a\\\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u00132\u0006\u0010\u0015\u001a\u00020\u0016¨\u0006\u001d"}, d2 = {"createContainerForJS", "Lorg/jetbrains/kotlin/container/StorageComponentContainer;", "moduleContext", "Lorg/jetbrains/kotlin/context/ModuleContext;", "bindingTrace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "declarationProviderFactory", "Lorg/jetbrains/kotlin/resolve/lazy/declarations/DeclarationProviderFactory;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "lookupTracker", "Lorg/jetbrains/kotlin/incremental/components/LookupTracker;", "expectActualTracker", "Lorg/jetbrains/kotlin/incremental/components/ExpectActualTracker;", "inlineConstTracker", "Lorg/jetbrains/kotlin/incremental/components/InlineConstTracker;", "enumWhenTracker", "Lorg/jetbrains/kotlin/incremental/components/EnumWhenTracker;", "additionalPackages", "", "Lorg/jetbrains/kotlin/descriptors/PackageFragmentProvider;", "targetEnvironment", "Lorg/jetbrains/kotlin/resolve/TargetEnvironment;", "analyzerServices", "Lorg/jetbrains/kotlin/resolve/PlatformDependentAnalyzerServices;", NativeLibraryConstantsKt.KONAN_DISTRIBUTION_PLATFORM_LIBS_DIR, "Lorg/jetbrains/kotlin/platform/TargetPlatform;", "createTopDownAnalyzerForJs", "Lorg/jetbrains/kotlin/resolve/LazyTopDownAnalyzer;", "js.frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class InjectionKt {
    public static final StorageComponentContainer createContainerForJS(final ModuleContext moduleContext, final BindingTrace bindingTrace, final DeclarationProviderFactory declarationProviderFactory, final LanguageVersionSettings languageVersionSettings, final LookupTracker lookupTracker, final ExpectActualTracker expectActualTracker, final InlineConstTracker inlineConstTracker, final EnumWhenTracker enumWhenTracker, List<? extends PackageFragmentProvider> additionalPackages, final TargetEnvironment targetEnvironment, final PlatformDependentAnalyzerServices analyzerServices, final TargetPlatform platform) {
        Intrinsics.checkNotNullParameter(moduleContext, "moduleContext");
        Intrinsics.checkNotNullParameter(bindingTrace, "bindingTrace");
        Intrinsics.checkNotNullParameter(declarationProviderFactory, "declarationProviderFactory");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(lookupTracker, "lookupTracker");
        Intrinsics.checkNotNullParameter(expectActualTracker, "expectActualTracker");
        Intrinsics.checkNotNullParameter(inlineConstTracker, "inlineConstTracker");
        Intrinsics.checkNotNullParameter(enumWhenTracker, "enumWhenTracker");
        Intrinsics.checkNotNullParameter(additionalPackages, "additionalPackages");
        Intrinsics.checkNotNullParameter(targetEnvironment, "targetEnvironment");
        Intrinsics.checkNotNullParameter(analyzerServices, "analyzerServices");
        Intrinsics.checkNotNullParameter(platform, "platform");
        StorageComponentContainer storageComponentContainerCreateContainer = PlatformConfiguratorBaseKt.createContainer("TopDownAnalyzerForJs", analyzerServices, new Function1<StorageComponentContainer, Unit>() { // from class: org.jetbrains.kotlin.frontend.js.di.InjectionKt$createContainerForJS$storageComponentContainer$1
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
                org.jetbrains.kotlin.frontend.di.InjectionKt.configureModule$default(createContainer, moduleContext, platform, analyzerServices, bindingTrace, languageVersionSettings, null, null, null, 32, null);
                org.jetbrains.kotlin.frontend.di.InjectionKt.configureIncrementalCompilation(createContainer, lookupTracker, expectActualTracker, inlineConstTracker, enumWhenTracker);
                org.jetbrains.kotlin.frontend.di.InjectionKt.configureStandardResolveComponents(createContainer);
                DslKt.useInstance(createContainer, declarationProviderFactory);
                targetEnvironment.configure(createContainer);
            }
        });
        StorageComponentContainer storageComponentContainer = storageComponentContainerCreateContainer;
        List listMutableListOf = CollectionsKt.mutableListOf(((KotlinCodeAnalyzer) DslKt.getService(storageComponentContainer, KotlinCodeAnalyzer.class)).getPackageFragmentProvider());
        ModuleDescriptorImpl moduleDescriptorImpl = (ModuleDescriptorImpl) DslKt.getService(storageComponentContainer, ModuleDescriptorImpl.class);
        CollectionsKt.addAll(listMutableListOf, additionalPackages);
        moduleDescriptorImpl.initialize(new CompositePackageFragmentProvider(listMutableListOf, "CompositeProvider@createContainerForJS for " + moduleDescriptorImpl));
        return storageComponentContainerCreateContainer;
    }

    public static final LazyTopDownAnalyzer createTopDownAnalyzerForJs(ModuleContext moduleContext, BindingTrace bindingTrace, DeclarationProviderFactory declarationProviderFactory, LanguageVersionSettings languageVersionSettings, LookupTracker lookupTracker, ExpectActualTracker expectActualTracker, InlineConstTracker inlineConstTracker, EnumWhenTracker enumWhenTracker, List<? extends PackageFragmentProvider> additionalPackages, TargetEnvironment targetEnvironment) {
        Intrinsics.checkNotNullParameter(moduleContext, "moduleContext");
        Intrinsics.checkNotNullParameter(bindingTrace, "bindingTrace");
        Intrinsics.checkNotNullParameter(declarationProviderFactory, "declarationProviderFactory");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(lookupTracker, "lookupTracker");
        Intrinsics.checkNotNullParameter(expectActualTracker, "expectActualTracker");
        Intrinsics.checkNotNullParameter(inlineConstTracker, "inlineConstTracker");
        Intrinsics.checkNotNullParameter(enumWhenTracker, "enumWhenTracker");
        Intrinsics.checkNotNullParameter(additionalPackages, "additionalPackages");
        Intrinsics.checkNotNullParameter(targetEnvironment, "targetEnvironment");
        return (LazyTopDownAnalyzer) DslKt.getService(createContainerForJS(moduleContext, bindingTrace, declarationProviderFactory, languageVersionSettings, lookupTracker, expectActualTracker, inlineConstTracker, enumWhenTracker, additionalPackages, targetEnvironment, JsPlatformAnalyzerServices.INSTANCE, JsPlatforms.INSTANCE.getDefaultJsPlatform()), LazyTopDownAnalyzer.class);
    }
}
