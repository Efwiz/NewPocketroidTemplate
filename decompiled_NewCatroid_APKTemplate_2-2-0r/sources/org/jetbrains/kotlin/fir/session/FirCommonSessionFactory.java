package org.jetbrains.kotlin.fir.session;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.functions.Function5;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.jvm.internal.SpreadBuilder;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.fir.FirModuleData;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.FirVisibilityChecker;
import org.jetbrains.kotlin.fir.analysis.FirDefaultOverridesBackwardCompatibilityHelper;
import org.jetbrains.kotlin.fir.analysis.FirOverridesBackwardCompatibilityHelper;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.deserialization.ModuleDataProvider;
import org.jetbrains.kotlin.fir.deserialization.SingleModuleDataProvider;
import org.jetbrains.kotlin.fir.extensions.FirExtensionRegistrar;
import org.jetbrains.kotlin.fir.extensions.FirSwitchableExtensionDeclarationsSymbolProvider;
import org.jetbrains.kotlin.fir.java.FirProjectSessionProvider;
import org.jetbrains.kotlin.fir.resolve.calls.ConeCallConflictResolverFactory;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider;
import org.jetbrains.kotlin.fir.resolve.providers.impl.FirBuiltinSymbolProvider;
import org.jetbrains.kotlin.fir.resolve.providers.impl.FirCloneableSymbolProvider;
import org.jetbrains.kotlin.fir.resolve.providers.impl.FirExtensionSyntheticFunctionInterfaceProvider;
import org.jetbrains.kotlin.fir.scopes.FirKotlinScopeProvider;
import org.jetbrains.kotlin.fir.scopes.FirPlatformClassMapper;
import org.jetbrains.kotlin.fir.session.environment.AbstractProjectEnvironment;
import org.jetbrains.kotlin.fir.session.environment.AbstractProjectFileSearchScope;
import org.jetbrains.kotlin.incremental.components.EnumWhenTracker;
import org.jetbrains.kotlin.incremental.components.ImportTracker;
import org.jetbrains.kotlin.incremental.components.LookupTracker;
import org.jetbrains.kotlin.library.metadata.resolver.KotlinResolvedLibrary;
import org.jetbrains.kotlin.load.kotlin.KotlinClassFinder;
import org.jetbrains.kotlin.load.kotlin.PackageAndMetadataPartProvider;
import org.jetbrains.kotlin.load.kotlin.PackagePartProvider;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FirCommonSessionFactory.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0080\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002Jn\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e2\u0006\u0010\u0010\u001a\u00020\u00112\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\u000e2\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0012\u0010\u0018\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u001a0\u0019J\u0095\u0001\u0010\u001b\u001a\u00020\u00042\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\f2\b\u0010\u001e\u001a\u0004\u0018\u00010\u001f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e2\b\b\u0002\u0010\u0016\u001a\u00020\u00172\n\b\u0002\u0010 \u001a\u0004\u0018\u00010!2\n\b\u0002\u0010\"\u001a\u0004\u0018\u00010#2\n\b\u0002\u0010$\u001a\u0004\u0018\u00010%2\u0014\b\u0002\u0010\u0018\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u001a0\u00192\u0019\b\u0002\u0010&\u001a\u0013\u0012\u0004\u0012\u00020'\u0012\u0004\u0012\u00020\u001a0\u0019¢\u0006\u0002\b(¨\u0006)"}, d2 = {"Lorg/jetbrains/kotlin/fir/session/FirCommonSessionFactory;", "Lorg/jetbrains/kotlin/fir/session/FirAbstractSessionFactory;", "()V", "createLibrarySession", "Lorg/jetbrains/kotlin/fir/FirSession;", "mainModuleName", "Lorg/jetbrains/kotlin/name/Name;", "sessionProvider", "Lorg/jetbrains/kotlin/fir/java/FirProjectSessionProvider;", "moduleDataProvider", "Lorg/jetbrains/kotlin/fir/deserialization/ModuleDataProvider;", "projectEnvironment", "Lorg/jetbrains/kotlin/fir/session/environment/AbstractProjectEnvironment;", "extensionRegistrars", "", "Lorg/jetbrains/kotlin/fir/extensions/FirExtensionRegistrar;", "librariesScope", "Lorg/jetbrains/kotlin/fir/session/environment/AbstractProjectFileSearchScope;", "resolvedKLibs", "Lorg/jetbrains/kotlin/library/metadata/resolver/KotlinResolvedLibrary;", "packageAndMetadataPartProvider", "Lorg/jetbrains/kotlin/load/kotlin/PackageAndMetadataPartProvider;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "registerExtraComponents", "Lkotlin/Function1;", "", "createModuleBasedSession", "moduleData", "Lorg/jetbrains/kotlin/fir/FirModuleData;", "incrementalCompilationContext", "Lorg/jetbrains/kotlin/fir/session/IncrementalCompilationContext;", "lookupTracker", "Lorg/jetbrains/kotlin/incremental/components/LookupTracker;", "enumWhenTracker", "Lorg/jetbrains/kotlin/incremental/components/EnumWhenTracker;", "importTracker", "Lorg/jetbrains/kotlin/incremental/components/ImportTracker;", ConfigConstants.CONFIG_INIT_SECTION, "Lorg/jetbrains/kotlin/fir/session/FirSessionConfigurator;", "Lkotlin/ExtensionFunctionType;", "entrypoint"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirCommonSessionFactory extends FirAbstractSessionFactory {
    public static final FirCommonSessionFactory INSTANCE = new FirCommonSessionFactory();

    private FirCommonSessionFactory() {
    }

    public final FirSession createLibrarySession(Name mainModuleName, FirProjectSessionProvider sessionProvider, final ModuleDataProvider moduleDataProvider, final AbstractProjectEnvironment projectEnvironment, List<? extends FirExtensionRegistrar> extensionRegistrars, final AbstractProjectFileSearchScope librariesScope, final List<? extends KotlinResolvedLibrary> resolvedKLibs, final PackageAndMetadataPartProvider packageAndMetadataPartProvider, LanguageVersionSettings languageVersionSettings, final Function1<? super FirSession, Unit> registerExtraComponents) {
        Intrinsics.checkNotNullParameter(mainModuleName, "mainModuleName");
        Intrinsics.checkNotNullParameter(sessionProvider, "sessionProvider");
        Intrinsics.checkNotNullParameter(moduleDataProvider, "moduleDataProvider");
        Intrinsics.checkNotNullParameter(projectEnvironment, "projectEnvironment");
        Intrinsics.checkNotNullParameter(extensionRegistrars, "extensionRegistrars");
        Intrinsics.checkNotNullParameter(librariesScope, "librariesScope");
        Intrinsics.checkNotNullParameter(resolvedKLibs, "resolvedKLibs");
        Intrinsics.checkNotNullParameter(packageAndMetadataPartProvider, "packageAndMetadataPartProvider");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(registerExtraComponents, "registerExtraComponents");
        return createLibrarySession(mainModuleName, sessionProvider, moduleDataProvider, languageVersionSettings, extensionRegistrars, new Function1<FirSession, Unit>() { // from class: org.jetbrains.kotlin.fir.session.FirCommonSessionFactory.createLibrarySession.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(FirSession firSession) {
                invoke2(firSession);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FirSession it) {
                Intrinsics.checkNotNullParameter(it, "it");
                registerExtraComponents.invoke(it);
            }
        }, new Function0<FirKotlinScopeProvider>() { // from class: org.jetbrains.kotlin.fir.session.FirCommonSessionFactory.createLibrarySession.2
            @Override // kotlin.jvm.functions.Function0
            public final FirKotlinScopeProvider invoke() {
                return new FirKotlinScopeProvider(null, 1, null);
            }
        }, new Function4<FirSession, FirModuleData, FirKotlinScopeProvider, FirExtensionSyntheticFunctionInterfaceProvider, List<? extends FirSymbolProvider>>() { // from class: org.jetbrains.kotlin.fir.session.FirCommonSessionFactory.createLibrarySession.3
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(4);
            }

            @Override // kotlin.jvm.functions.Function4
            public final List<FirSymbolProvider> invoke(FirSession session, FirModuleData builtinsModuleData, FirKotlinScopeProvider kotlinScopeProvider, FirExtensionSyntheticFunctionInterfaceProvider firExtensionSyntheticFunctionInterfaceProvider) {
                KlibBasedSymbolProvider klibBasedSymbolProvider;
                Intrinsics.checkNotNullParameter(session, "session");
                Intrinsics.checkNotNullParameter(builtinsModuleData, "builtinsModuleData");
                Intrinsics.checkNotNullParameter(kotlinScopeProvider, "kotlinScopeProvider");
                FirSymbolProvider[] firSymbolProviderArr = new FirSymbolProvider[5];
                firSymbolProviderArr[0] = new MetadataSymbolProvider(session, moduleDataProvider, kotlinScopeProvider, packageAndMetadataPartProvider, projectEnvironment.getKotlinClassFinder(librariesScope), null, 32, null);
                boolean zIsEmpty = resolvedKLibs.isEmpty();
                ModuleDataProvider moduleDataProvider2 = moduleDataProvider;
                List<KotlinResolvedLibrary> list = resolvedKLibs;
                if (zIsEmpty) {
                    klibBasedSymbolProvider = null;
                } else {
                    List<KotlinResolvedLibrary> list2 = list;
                    ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
                    Iterator<T> it = list2.iterator();
                    while (it.hasNext()) {
                        arrayList.add(((KotlinResolvedLibrary) it.next()).getLibrary());
                    }
                    klibBasedSymbolProvider = new KlibBasedSymbolProvider(session, moduleDataProvider2, kotlinScopeProvider, arrayList, null, 16, null);
                }
                firSymbolProviderArr[1] = klibBasedSymbolProvider;
                firSymbolProviderArr[2] = firExtensionSyntheticFunctionInterfaceProvider;
                firSymbolProviderArr[3] = new FirBuiltinSymbolProvider(session, builtinsModuleData, kotlinScopeProvider);
                firSymbolProviderArr[4] = new FirCloneableSymbolProvider(session, builtinsModuleData, kotlinScopeProvider);
                return CollectionsKt.listOfNotNull((Object[]) firSymbolProviderArr);
            }
        });
    }

    public final FirSession createModuleBasedSession(final FirModuleData moduleData, FirProjectSessionProvider sessionProvider, final AbstractProjectEnvironment projectEnvironment, final IncrementalCompilationContext incrementalCompilationContext, List<? extends FirExtensionRegistrar> extensionRegistrars, LanguageVersionSettings languageVersionSettings, LookupTracker lookupTracker, EnumWhenTracker enumWhenTracker, ImportTracker importTracker, final Function1<? super FirSession, Unit> registerExtraComponents, Function1<? super FirSessionConfigurator, Unit> init) {
        Intrinsics.checkNotNullParameter(moduleData, "moduleData");
        Intrinsics.checkNotNullParameter(sessionProvider, "sessionProvider");
        Intrinsics.checkNotNullParameter(projectEnvironment, "projectEnvironment");
        Intrinsics.checkNotNullParameter(extensionRegistrars, "extensionRegistrars");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(registerExtraComponents, "registerExtraComponents");
        Intrinsics.checkNotNullParameter(init, "init");
        return createModuleBasedSession(moduleData, sessionProvider, extensionRegistrars, languageVersionSettings, lookupTracker, enumWhenTracker, importTracker, init, new Function1<FirSession, Unit>() { // from class: org.jetbrains.kotlin.fir.session.FirCommonSessionFactory.createModuleBasedSession.3
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(FirSession firSession) {
                invoke2(firSession);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FirSession it) {
                Intrinsics.checkNotNullParameter(it, "it");
                it.register(Reflection.getOrCreateKotlinClass(FirVisibilityChecker.class), FirVisibilityChecker.Default.INSTANCE);
                it.register(Reflection.getOrCreateKotlinClass(ConeCallConflictResolverFactory.class), DefaultCallConflictResolverFactory.INSTANCE);
                it.register(Reflection.getOrCreateKotlinClass(FirPlatformClassMapper.class), FirPlatformClassMapper.Default.INSTANCE);
                it.register(Reflection.getOrCreateKotlinClass(FirOverridesBackwardCompatibilityHelper.class), FirDefaultOverridesBackwardCompatibilityHelper.INSTANCE);
                registerExtraComponents.invoke(it);
            }
        }, new Function1<FirSessionConfigurator, Unit>() { // from class: org.jetbrains.kotlin.fir.session.FirCommonSessionFactory.createModuleBasedSession.4
            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FirSessionConfigurator it) {
                Intrinsics.checkNotNullParameter(it, "it");
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(FirSessionConfigurator firSessionConfigurator) {
                invoke2(firSessionConfigurator);
                return Unit.INSTANCE;
            }
        }, new Function0<FirKotlinScopeProvider>() { // from class: org.jetbrains.kotlin.fir.session.FirCommonSessionFactory.createModuleBasedSession.5
            @Override // kotlin.jvm.functions.Function0
            public final FirKotlinScopeProvider invoke() {
                return new FirKotlinScopeProvider(null, 1, null);
            }
        }, new Function5<FirSession, FirKotlinScopeProvider, FirSymbolProvider, FirSwitchableExtensionDeclarationsSymbolProvider, List<? extends FirSymbolProvider>, List<? extends FirSymbolProvider>>() { // from class: org.jetbrains.kotlin.fir.session.FirCommonSessionFactory.createModuleBasedSession.6
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(5);
            }

            @Override // kotlin.jvm.functions.Function5
            public final List<FirSymbolProvider> invoke(FirSession session, FirKotlinScopeProvider kotlinScopeProvider, FirSymbolProvider symbolProvider, FirSwitchableExtensionDeclarationsSymbolProvider firSwitchableExtensionDeclarationsSymbolProvider, List<? extends FirSymbolProvider> dependencies) {
                FirSymbolProvider[] firSymbolProviderArr;
                Collection<FirSymbolProvider> previousFirSessionsSymbolProviders;
                Intrinsics.checkNotNullParameter(session, "session");
                Intrinsics.checkNotNullParameter(kotlinScopeProvider, "kotlinScopeProvider");
                Intrinsics.checkNotNullParameter(symbolProvider, "symbolProvider");
                Intrinsics.checkNotNullParameter(dependencies, "dependencies");
                IncrementalCompilationContext incrementalCompilationContext2 = incrementalCompilationContext;
                MetadataSymbolProvider metadataSymbolProvider = null;
                if (incrementalCompilationContext2 != null) {
                    FirModuleData firModuleData = moduleData;
                    AbstractProjectEnvironment abstractProjectEnvironment = projectEnvironment;
                    PackagePartProvider precompiledBinariesPackagePartProvider = incrementalCompilationContext2.getPrecompiledBinariesPackagePartProvider();
                    if (precompiledBinariesPackagePartProvider != null && incrementalCompilationContext2.getPrecompiledBinariesFileScope() != null) {
                        KotlinClassFinder kotlinClassFinder = abstractProjectEnvironment.getKotlinClassFinder(incrementalCompilationContext2.getPrecompiledBinariesFileScope());
                        Intrinsics.checkNotNull(kotlinClassFinder, "null cannot be cast to non-null type org.jetbrains.kotlin.serialization.deserialization.KotlinMetadataFinder");
                        metadataSymbolProvider = new MetadataSymbolProvider(session, new SingleModuleDataProvider(firModuleData), kotlinScopeProvider, (PackageAndMetadataPartProvider) precompiledBinariesPackagePartProvider, kotlinClassFinder, FirDeclarationOrigin.Precompiled.INSTANCE);
                    }
                }
                SpreadBuilder spreadBuilder = new SpreadBuilder(5);
                spreadBuilder.add(symbolProvider);
                IncrementalCompilationContext incrementalCompilationContext3 = incrementalCompilationContext;
                if (incrementalCompilationContext3 == null || (previousFirSessionsSymbolProviders = incrementalCompilationContext3.getPreviousFirSessionsSymbolProviders()) == null || (firSymbolProviderArr = (FirSymbolProvider[]) previousFirSessionsSymbolProviders.toArray(new FirSymbolProvider[0])) == null) {
                    firSymbolProviderArr = new FirSymbolProvider[0];
                }
                spreadBuilder.addSpread(firSymbolProviderArr);
                spreadBuilder.add(metadataSymbolProvider);
                spreadBuilder.add(firSwitchableExtensionDeclarationsSymbolProvider);
                spreadBuilder.addSpread(dependencies.toArray(new FirSymbolProvider[0]));
                return CollectionsKt.listOfNotNull(spreadBuilder.toArray(new FirSymbolProvider[spreadBuilder.size()]));
            }
        });
    }
}
