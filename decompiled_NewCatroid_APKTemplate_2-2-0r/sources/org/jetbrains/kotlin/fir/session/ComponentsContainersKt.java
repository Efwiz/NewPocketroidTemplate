package org.jetbrains.kotlin.fir.session;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.config.JvmAnalysisFlags;
import org.jetbrains.kotlin.config.JvmDefaultMode;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.fir.FirCliExceptionHandler;
import org.jetbrains.kotlin.fir.FirEnumWhenTrackerComponent;
import org.jetbrains.kotlin.fir.FirExceptionHandler;
import org.jetbrains.kotlin.fir.FirExpectActualMatchingContextFactory;
import org.jetbrains.kotlin.fir.FirImportTrackerComponent;
import org.jetbrains.kotlin.fir.FirLanguageSettingsComponent;
import org.jetbrains.kotlin.fir.FirLanguageSettingsComponentKt;
import org.jetbrains.kotlin.fir.FirLookupTrackerComponent;
import org.jetbrains.kotlin.fir.FirModuleData;
import org.jetbrains.kotlin.fir.FirModuleVisibilityChecker;
import org.jetbrains.kotlin.fir.FirNameConflictsTrackerComponent;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.FirVisibilityChecker;
import org.jetbrains.kotlin.fir.IncrementalPassThroughLookupTrackerComponent;
import org.jetbrains.kotlin.fir.analysis.CheckersComponent;
import org.jetbrains.kotlin.fir.analysis.FirOverridesBackwardCompatibilityHelper;
import org.jetbrains.kotlin.fir.analysis.checkers.FirInlineCheckerPlatformSpecificComponent;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirNameConflictsTracker;
import org.jetbrains.kotlin.fir.analysis.jvm.FirJvmOverridesBackwardCompatibilityHelper;
import org.jetbrains.kotlin.fir.analysis.jvm.checkers.FirJvmInlineCheckerComponent;
import org.jetbrains.kotlin.fir.caches.FirCachesFactory;
import org.jetbrains.kotlin.fir.caches.FirThreadUnsafeCachesFactory;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOverloadabilityHelper;
import org.jetbrains.kotlin.fir.declarations.FirTypeSpecificityComparatorProvider;
import org.jetbrains.kotlin.fir.declarations.SealedClassInheritorsProvider;
import org.jetbrains.kotlin.fir.declarations.SealedClassInheritorsProviderImpl;
import org.jetbrains.kotlin.fir.deserialization.FirDeserializationExtension;
import org.jetbrains.kotlin.fir.extensions.FirExtensionService;
import org.jetbrains.kotlin.fir.extensions.FirPredicateBasedProvider;
import org.jetbrains.kotlin.fir.extensions.FirPredicateBasedProviderImpl;
import org.jetbrains.kotlin.fir.extensions.FirRegisteredPluginAnnotations;
import org.jetbrains.kotlin.fir.extensions.FirRegisteredPluginAnnotationsImpl;
import org.jetbrains.kotlin.fir.java.FirJavaVisibilityChecker;
import org.jetbrains.kotlin.fir.java.FirJvmDefaultModeComponent;
import org.jetbrains.kotlin.fir.java.FirSyntheticPropertiesStorage;
import org.jetbrains.kotlin.fir.java.JvmSupertypeUpdater;
import org.jetbrains.kotlin.fir.java.deserialization.FirJvmDeserializationExtension;
import org.jetbrains.kotlin.fir.java.enhancement.FirAnnotationTypeQualifierResolver;
import org.jetbrains.kotlin.fir.java.enhancement.FirEnhancedSymbolsStorage;
import org.jetbrains.kotlin.fir.java.enhancement.JavaCompilerRequiredAnnotationEnhancementProvider;
import org.jetbrains.kotlin.fir.java.scopes.JavaOverridabilityRules;
import org.jetbrains.kotlin.fir.resolve.FirDefaultParametersResolver;
import org.jetbrains.kotlin.fir.resolve.FirJavaClassMapper;
import org.jetbrains.kotlin.fir.resolve.FirJavaSyntheticNamesProvider;
import org.jetbrains.kotlin.fir.resolve.FirQualifierResolver;
import org.jetbrains.kotlin.fir.resolve.FirSamConstructorStorage;
import org.jetbrains.kotlin.fir.resolve.FirTypeResolver;
import org.jetbrains.kotlin.fir.resolve.calls.ConeCallConflictResolverFactory;
import org.jetbrains.kotlin.fir.resolve.calls.FirDeclarationOverloadabilityHelperImpl;
import org.jetbrains.kotlin.fir.resolve.calls.FirSyntheticNamesProvider;
import org.jetbrains.kotlin.fir.resolve.calls.jvm.JvmCallConflictResolverFactory;
import org.jetbrains.kotlin.fir.resolve.inference.InferenceComponents;
import org.jetbrains.kotlin.fir.resolve.providers.impl.FirQualifierResolverImpl;
import org.jetbrains.kotlin.fir.resolve.providers.impl.FirTypeResolverImpl;
import org.jetbrains.kotlin.fir.resolve.transformers.FirDummyCompilerLazyDeclarationResolver;
import org.jetbrains.kotlin.fir.resolve.transformers.PlatformSupertypeUpdater;
import org.jetbrains.kotlin.fir.resolve.transformers.mpp.FirExpectActualMatchingContextImpl;
import org.jetbrains.kotlin.fir.resolve.transformers.plugin.CompilerRequiredAnnotationEnhancementProvider;
import org.jetbrains.kotlin.fir.scopes.FirOverrideChecker;
import org.jetbrains.kotlin.fir.scopes.FirOverrideService;
import org.jetbrains.kotlin.fir.scopes.FirPlatformClassMapper;
import org.jetbrains.kotlin.fir.scopes.PlatformSpecificOverridabilityRules;
import org.jetbrains.kotlin.fir.scopes.impl.FirDeclaredMemberScopeProvider;
import org.jetbrains.kotlin.fir.scopes.impl.FirDynamicMembersStorage;
import org.jetbrains.kotlin.fir.scopes.impl.FirEnumEntriesSupport;
import org.jetbrains.kotlin.fir.scopes.impl.FirGeneratedMemberDeclarationsStorage;
import org.jetbrains.kotlin.fir.scopes.impl.FirIntersectionOverrideStorage;
import org.jetbrains.kotlin.fir.scopes.impl.FirJvmEnumEntriesSupport;
import org.jetbrains.kotlin.fir.scopes.impl.FirStandardOverrideChecker;
import org.jetbrains.kotlin.fir.scopes.impl.FirSubstitutionOverrideStorage;
import org.jetbrains.kotlin.fir.scopes.impl.FirSynthesizedStorage;
import org.jetbrains.kotlin.fir.serialization.FirProvidedDeclarationsForMetadataService;
import org.jetbrains.kotlin.fir.symbols.FirLazyDeclarationResolver;
import org.jetbrains.kotlin.fir.types.FirCorrespondingSupertypesCache;
import org.jetbrains.kotlin.fir.types.FirFunctionTypeKindService;
import org.jetbrains.kotlin.fir.types.FirFunctionTypeKindServiceImpl;
import org.jetbrains.kotlin.fir.types.TypeComponents;
import org.jetbrains.kotlin.fir.types.TypeComponentsKt;
import org.jetbrains.kotlin.incremental.components.EnumWhenTracker;
import org.jetbrains.kotlin.incremental.components.ImportTracker;
import org.jetbrains.kotlin.incremental.components.LookupTracker;
import org.jetbrains.kotlin.load.java.JavaTypeEnhancementState;
import org.jetbrains.kotlin.resolve.jvm.JvmTypeSpecificityComparator;
import org.jetbrains.kotlin.resolve.jvm.modules.JavaModuleResolver;

/* JADX INFO: compiled from: ComponentsContainers.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00008\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002\u001a\u0012\u0010\u0003\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u0005\u001a\n\u0010\u0006\u001a\u00020\u0001*\u00020\u0002\u001a\u0012\u0010\u0007\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\b\u001a\u00020\t\u001a\n\u0010\n\u001a\u00020\u0001*\u00020\u0002\u001a\u0012\u0010\u000b\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\f\u001a\u00020\r\u001a.\u0010\u000e\u001a\u00020\u0001*\u00020\u00022\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u00102\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u00122\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u0014¨\u0006\u0015"}, d2 = {"registerCliCompilerOnlyComponents", "", "Lorg/jetbrains/kotlin/fir/FirSession;", "registerCommonComponents", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "registerCommonComponentsAfterExtensionsAreConfigured", "registerCommonJavaComponents", "javaModuleResolver", "Lorg/jetbrains/kotlin/resolve/jvm/modules/JavaModuleResolver;", "registerJavaSpecificResolveComponents", "registerModuleData", "moduleData", "Lorg/jetbrains/kotlin/fir/FirModuleData;", "registerResolveComponents", "lookupTracker", "Lorg/jetbrains/kotlin/incremental/components/LookupTracker;", "enumWhenTracker", "Lorg/jetbrains/kotlin/incremental/components/EnumWhenTracker;", "importTracker", "Lorg/jetbrains/kotlin/incremental/components/ImportTracker;", "entrypoint"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ComponentsContainersKt {
    public static final void registerCommonComponents(FirSession firSession, LanguageVersionSettings languageVersionSettings) {
        Intrinsics.checkNotNullParameter(firSession, "<this>");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        firSession.register(Reflection.getOrCreateKotlinClass(FirLanguageSettingsComponent.class), new FirLanguageSettingsComponent(languageVersionSettings));
        firSession.register(Reflection.getOrCreateKotlinClass(TypeComponents.class), new TypeComponents(firSession));
        firSession.register(Reflection.getOrCreateKotlinClass(InferenceComponents.class), new InferenceComponents(firSession));
        firSession.register(Reflection.getOrCreateKotlinClass(FirDeclaredMemberScopeProvider.class), new FirDeclaredMemberScopeProvider(firSession));
        firSession.register(Reflection.getOrCreateKotlinClass(FirCorrespondingSupertypesCache.class), new FirCorrespondingSupertypesCache(firSession));
        firSession.register(Reflection.getOrCreateKotlinClass(FirDefaultParametersResolver.class), new FirDefaultParametersResolver());
        firSession.register(Reflection.getOrCreateKotlinClass(FirExtensionService.class), new FirExtensionService(firSession));
        firSession.register(Reflection.getOrCreateKotlinClass(FirSubstitutionOverrideStorage.class), new FirSubstitutionOverrideStorage(firSession));
        firSession.register(Reflection.getOrCreateKotlinClass(FirIntersectionOverrideStorage.class), new FirIntersectionOverrideStorage(firSession));
        firSession.register(Reflection.getOrCreateKotlinClass(FirSynthesizedStorage.class), new FirSynthesizedStorage(firSession));
        firSession.register(Reflection.getOrCreateKotlinClass(FirGeneratedMemberDeclarationsStorage.class), new FirGeneratedMemberDeclarationsStorage(firSession));
        firSession.register(Reflection.getOrCreateKotlinClass(FirSamConstructorStorage.class), new FirSamConstructorStorage(firSession));
        firSession.register(Reflection.getOrCreateKotlinClass(FirOverrideService.class), new FirOverrideService(firSession));
        firSession.register(Reflection.getOrCreateKotlinClass(FirDynamicMembersStorage.class), new FirDynamicMembersStorage(firSession));
        firSession.register(Reflection.getOrCreateKotlinClass(FirEnumEntriesSupport.class), new FirEnumEntriesSupport(firSession));
        firSession.register(Reflection.getOrCreateKotlinClass(FirOverrideChecker.class), new FirStandardOverrideChecker(firSession));
        firSession.register(Reflection.getOrCreateKotlinClass(FirDeclarationOverloadabilityHelper.class), new FirDeclarationOverloadabilityHelperImpl(firSession));
    }

    public static final void registerCommonComponentsAfterExtensionsAreConfigured(FirSession firSession) {
        Intrinsics.checkNotNullParameter(firSession, "<this>");
        firSession.register(Reflection.getOrCreateKotlinClass(FirFunctionTypeKindService.class), new FirFunctionTypeKindServiceImpl(firSession));
        firSession.register(Reflection.getOrCreateKotlinClass(FirProvidedDeclarationsForMetadataService.class), FirProvidedDeclarationsForMetadataService.INSTANCE.create(firSession));
    }

    public static final void registerCliCompilerOnlyComponents(FirSession firSession) {
        Intrinsics.checkNotNullParameter(firSession, "<this>");
        firSession.register(Reflection.getOrCreateKotlinClass(FirCachesFactory.class), FirThreadUnsafeCachesFactory.INSTANCE);
        firSession.register(Reflection.getOrCreateKotlinClass(SealedClassInheritorsProvider.class), SealedClassInheritorsProviderImpl.INSTANCE);
        firSession.register(Reflection.getOrCreateKotlinClass(FirLazyDeclarationResolver.class), FirDummyCompilerLazyDeclarationResolver.INSTANCE);
        firSession.register(Reflection.getOrCreateKotlinClass(FirExceptionHandler.class), FirCliExceptionHandler.INSTANCE);
        firSession.register(Reflection.getOrCreateKotlinClass(FirRegisteredPluginAnnotations.class), new FirRegisteredPluginAnnotationsImpl(firSession));
        firSession.register(Reflection.getOrCreateKotlinClass(FirPredicateBasedProvider.class), new FirPredicateBasedProviderImpl(firSession));
    }

    public static final void registerCommonJavaComponents(FirSession firSession, JavaModuleResolver javaModuleResolver) {
        Intrinsics.checkNotNullParameter(firSession, "<this>");
        Intrinsics.checkNotNullParameter(javaModuleResolver, "javaModuleResolver");
        firSession.register(Reflection.getOrCreateKotlinClass(FirAnnotationTypeQualifierResolver.class), new FirAnnotationTypeQualifierResolver(firSession, (JavaTypeEnhancementState) FirLanguageSettingsComponentKt.getLanguageVersionSettings(firSession).getFlag(JvmAnalysisFlags.getJavaTypeEnhancementState()), javaModuleResolver));
        firSession.register(Reflection.getOrCreateKotlinClass(FirEnhancedSymbolsStorage.class), new FirEnhancedSymbolsStorage(firSession));
        firSession.register(Reflection.getOrCreateKotlinClass(FirSyntheticPropertiesStorage.class), new FirSyntheticPropertiesStorage(firSession));
        firSession.register(Reflection.getOrCreateKotlinClass(FirJvmDefaultModeComponent.class), new FirJvmDefaultModeComponent((JvmDefaultMode) FirLanguageSettingsComponentKt.getLanguageVersionSettings(firSession).getFlag(JvmAnalysisFlags.getJvmDefaultMode())));
        firSession.register(Reflection.getOrCreateKotlinClass(PlatformSupertypeUpdater.class), new JvmSupertypeUpdater(firSession));
        firSession.register(Reflection.getOrCreateKotlinClass(PlatformSpecificOverridabilityRules.class), new JavaOverridabilityRules(firSession));
        firSession.register(Reflection.getOrCreateKotlinClass(FirDeserializationExtension.class), new FirJvmDeserializationExtension(firSession));
        firSession.register(Reflection.getOrCreateKotlinClass(FirEnumEntriesSupport.class), new FirJvmEnumEntriesSupport(firSession));
        firSession.register(Reflection.getOrCreateKotlinClass(CompilerRequiredAnnotationEnhancementProvider.class), JavaCompilerRequiredAnnotationEnhancementProvider.INSTANCE);
    }

    public static /* synthetic */ void registerResolveComponents$default(FirSession firSession, LookupTracker lookupTracker, EnumWhenTracker enumWhenTracker, ImportTracker importTracker, int i, Object obj) {
        if ((i & 1) != 0) {
            lookupTracker = null;
        }
        if ((i & 2) != 0) {
            enumWhenTracker = null;
        }
        if ((i & 4) != 0) {
            importTracker = null;
        }
        registerResolveComponents(firSession, lookupTracker, enumWhenTracker, importTracker);
    }

    public static final void registerResolveComponents(final FirSession firSession, LookupTracker lookupTracker, EnumWhenTracker enumWhenTracker, ImportTracker importTracker) {
        Intrinsics.checkNotNullParameter(firSession, "<this>");
        firSession.register(Reflection.getOrCreateKotlinClass(FirQualifierResolver.class), new FirQualifierResolverImpl(firSession));
        firSession.register(Reflection.getOrCreateKotlinClass(FirTypeResolver.class), new FirTypeResolverImpl(firSession));
        firSession.register(Reflection.getOrCreateKotlinClass(CheckersComponent.class), new CheckersComponent());
        firSession.register(Reflection.getOrCreateKotlinClass(FirNameConflictsTrackerComponent.class), new FirNameConflictsTracker());
        firSession.register(Reflection.getOrCreateKotlinClass(FirModuleVisibilityChecker.class), new FirModuleVisibilityChecker.Standard(firSession));
        firSession.register(Reflection.getOrCreateKotlinClass(SourcesToPathsMapper.class), new SourcesToPathsMapper());
        if (lookupTracker != null) {
            firSession.register(Reflection.getOrCreateKotlinClass(FirLookupTrackerComponent.class), new IncrementalPassThroughLookupTrackerComponent(lookupTracker, new Function1<KtSourceElement, String>() { // from class: org.jetbrains.kotlin.fir.session.ComponentsContainersKt$registerResolveComponents$firFileToPath$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final String invoke(KtSourceElement it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return SourcesToPathsMapperKt.getSourcesToPathsMapper(firSession).getSourceFilePath(it);
                }
            }));
        }
        if (enumWhenTracker != null) {
            firSession.register(Reflection.getOrCreateKotlinClass(FirEnumWhenTrackerComponent.class), new IncrementalPassThroughEnumWhenTrackerComponent(enumWhenTracker));
        }
        if (importTracker != null) {
            firSession.register(Reflection.getOrCreateKotlinClass(FirImportTrackerComponent.class), new IncrementalPassThroughImportTrackerComponent(importTracker));
        }
        firSession.register(Reflection.getOrCreateKotlinClass(FirExpectActualMatchingContextFactory.class), FirExpectActualMatchingContextImpl.Factory.INSTANCE);
    }

    public static final void registerJavaSpecificResolveComponents(FirSession firSession) {
        Intrinsics.checkNotNullParameter(firSession, "<this>");
        firSession.register(Reflection.getOrCreateKotlinClass(FirVisibilityChecker.class), FirJavaVisibilityChecker.INSTANCE);
        firSession.register(Reflection.getOrCreateKotlinClass(ConeCallConflictResolverFactory.class), JvmCallConflictResolverFactory.INSTANCE);
        firSession.register(Reflection.getOrCreateKotlinClass(FirTypeSpecificityComparatorProvider.class), new FirTypeSpecificityComparatorProvider(new JvmTypeSpecificityComparator(TypeComponentsKt.getTypeContext(firSession))));
        firSession.register(Reflection.getOrCreateKotlinClass(FirPlatformClassMapper.class), new FirJavaClassMapper(firSession));
        firSession.register(Reflection.getOrCreateKotlinClass(FirSyntheticNamesProvider.class), FirJavaSyntheticNamesProvider.INSTANCE);
        firSession.register(Reflection.getOrCreateKotlinClass(FirOverridesBackwardCompatibilityHelper.class), FirJvmOverridesBackwardCompatibilityHelper.INSTANCE);
        firSession.register(Reflection.getOrCreateKotlinClass(FirInlineCheckerPlatformSpecificComponent.class), new FirJvmInlineCheckerComponent());
    }

    public static final void registerModuleData(FirSession firSession, FirModuleData moduleData) {
        Intrinsics.checkNotNullParameter(firSession, "<this>");
        Intrinsics.checkNotNullParameter(moduleData, "moduleData");
        firSession.register(Reflection.getOrCreateKotlinClass(FirModuleData.class), moduleData);
    }
}
