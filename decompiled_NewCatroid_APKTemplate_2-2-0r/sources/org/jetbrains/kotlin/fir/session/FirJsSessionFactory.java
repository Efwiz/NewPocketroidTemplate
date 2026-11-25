package org.jetbrains.kotlin.fir.session;

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
import org.jetbrains.kotlin.config.CommonConfigurationKeysKt;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.fir.FirModuleData;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.FirVisibilityChecker;
import org.jetbrains.kotlin.fir.analysis.FirEmptyOverridesBackwardCompatibilityHelper;
import org.jetbrains.kotlin.fir.analysis.FirOverridesBackwardCompatibilityHelper;
import org.jetbrains.kotlin.fir.analysis.checkers.FirPlatformDiagnosticSuppressor;
import org.jetbrains.kotlin.fir.analysis.js.checkers.FirJsModuleKind;
import org.jetbrains.kotlin.fir.analysis.js.checkers.FirJsPlatformDiagnosticSuppressor;
import org.jetbrains.kotlin.fir.checkers.CheckersContainersKt;
import org.jetbrains.kotlin.fir.declarations.FirTypeSpecificityComparatorProvider;
import org.jetbrains.kotlin.fir.deserialization.ModuleDataProvider;
import org.jetbrains.kotlin.fir.deserialization.SingleModuleDataProvider;
import org.jetbrains.kotlin.fir.extensions.FirExtensionRegistrar;
import org.jetbrains.kotlin.fir.extensions.FirSwitchableExtensionDeclarationsSymbolProvider;
import org.jetbrains.kotlin.fir.java.FirProjectSessionProvider;
import org.jetbrains.kotlin.fir.resolve.calls.ConeCallConflictResolverFactory;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider;
import org.jetbrains.kotlin.fir.resolve.providers.impl.FirBuiltinSyntheticFunctionInterfaceProvider;
import org.jetbrains.kotlin.fir.resolve.providers.impl.FirExtensionSyntheticFunctionInterfaceProvider;
import org.jetbrains.kotlin.fir.scopes.FirKotlinScopeProvider;
import org.jetbrains.kotlin.fir.scopes.FirPlatformClassMapper;
import org.jetbrains.kotlin.fir.types.TypeComponentsKt;
import org.jetbrains.kotlin.incremental.components.LookupTracker;
import org.jetbrains.kotlin.js.config.JSConfigurationKeys;
import org.jetbrains.kotlin.js.resolve.JsTypeSpecificityComparatorWithoutDelegate;
import org.jetbrains.kotlin.library.KotlinLibrary;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.serialization.js.ModuleKind;

/* JADX INFO: compiled from: FirJsSessionFactory.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002JV\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\b2\u0006\u0010\u0010\u001a\u00020\u00112\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00140\u0013Jq\u0010\u0015\u001a\u00020\u00042\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\n\u001a\u00020\u000b2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\b2\u0006\u0010\u0010\u001a\u00020\u00112\b\u0010\u0018\u001a\u0004\u0018\u00010\u00192\n\b\u0002\u0010\u001a\u001a\u0004\u0018\u00010\u001b2\u0014\b\u0002\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00140\u00132\u0017\u0010\u001c\u001a\u0013\u0012\u0004\u0012\u00020\u001d\u0012\u0004\u0012\u00020\u00140\u0013¢\u0006\u0002\b\u001eJ\u0012\u0010\u001f\u001a\u00020\u0014*\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u0011¨\u0006 "}, d2 = {"Lorg/jetbrains/kotlin/fir/session/FirJsSessionFactory;", "Lorg/jetbrains/kotlin/fir/session/FirAbstractSessionFactory;", "()V", "createLibrarySession", "Lorg/jetbrains/kotlin/fir/FirSession;", "mainModuleName", "Lorg/jetbrains/kotlin/name/Name;", "resolvedLibraries", "", "Lorg/jetbrains/kotlin/library/KotlinLibrary;", "sessionProvider", "Lorg/jetbrains/kotlin/fir/java/FirProjectSessionProvider;", "moduleDataProvider", "Lorg/jetbrains/kotlin/fir/deserialization/ModuleDataProvider;", "extensionRegistrars", "Lorg/jetbrains/kotlin/fir/extensions/FirExtensionRegistrar;", "compilerConfiguration", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "registerExtraComponents", "Lkotlin/Function1;", "", "createModuleBasedSession", "moduleData", "Lorg/jetbrains/kotlin/fir/FirModuleData;", "lookupTracker", "Lorg/jetbrains/kotlin/incremental/components/LookupTracker;", "icData", "Lorg/jetbrains/kotlin/fir/session/KlibIcData;", ConfigConstants.CONFIG_INIT_SECTION, "Lorg/jetbrains/kotlin/fir/session/FirSessionConfigurator;", "Lkotlin/ExtensionFunctionType;", "registerJsSpecificComponents", "entrypoint"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirJsSessionFactory extends FirAbstractSessionFactory {
    public static final FirJsSessionFactory INSTANCE = new FirJsSessionFactory();

    private FirJsSessionFactory() {
    }

    public final FirSession createModuleBasedSession(final FirModuleData moduleData, FirProjectSessionProvider sessionProvider, List<? extends FirExtensionRegistrar> extensionRegistrars, final CompilerConfiguration compilerConfiguration, LookupTracker lookupTracker, final KlibIcData icData, final Function1<? super FirSession, Unit> registerExtraComponents, Function1<? super FirSessionConfigurator, Unit> init) {
        Intrinsics.checkNotNullParameter(moduleData, "moduleData");
        Intrinsics.checkNotNullParameter(sessionProvider, "sessionProvider");
        Intrinsics.checkNotNullParameter(extensionRegistrars, "extensionRegistrars");
        Intrinsics.checkNotNullParameter(compilerConfiguration, "compilerConfiguration");
        Intrinsics.checkNotNullParameter(registerExtraComponents, "registerExtraComponents");
        Intrinsics.checkNotNullParameter(init, "init");
        return createModuleBasedSession(moduleData, sessionProvider, extensionRegistrars, CommonConfigurationKeysKt.getLanguageVersionSettings(compilerConfiguration), lookupTracker, null, null, init, new Function1<FirSession, Unit>() { // from class: org.jetbrains.kotlin.fir.session.FirJsSessionFactory.createModuleBasedSession.2
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
            public final void invoke2(FirSession session) {
                Intrinsics.checkNotNullParameter(session, "session");
                FirJsSessionFactory.INSTANCE.registerJsSpecificComponents(session, compilerConfiguration);
                registerExtraComponents.invoke(session);
            }
        }, new Function1<FirSessionConfigurator, Unit>() { // from class: org.jetbrains.kotlin.fir.session.FirJsSessionFactory.createModuleBasedSession.3
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(FirSessionConfigurator firSessionConfigurator) {
                invoke2(firSessionConfigurator);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FirSessionConfigurator it) {
                Intrinsics.checkNotNullParameter(it, "it");
                CheckersContainersKt.registerJsCheckers(it);
            }
        }, new Function0<FirKotlinScopeProvider>() { // from class: org.jetbrains.kotlin.fir.session.FirJsSessionFactory.createModuleBasedSession.4
            @Override // kotlin.jvm.functions.Function0
            public final FirKotlinScopeProvider invoke() {
                return new FirKotlinScopeProvider(null, 1, null);
            }
        }, new Function5<FirSession, FirKotlinScopeProvider, FirSymbolProvider, FirSwitchableExtensionDeclarationsSymbolProvider, List<? extends FirSymbolProvider>, List<? extends FirSymbolProvider>>() { // from class: org.jetbrains.kotlin.fir.session.FirJsSessionFactory.createModuleBasedSession.5
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(5);
            }

            @Override // kotlin.jvm.functions.Function5
            public final List<FirSymbolProvider> invoke(FirSession session, FirKotlinScopeProvider kotlinScopeProvider, FirSymbolProvider symbolProvider, FirSwitchableExtensionDeclarationsSymbolProvider firSwitchableExtensionDeclarationsSymbolProvider, List<? extends FirSymbolProvider> dependencies) {
                Intrinsics.checkNotNullParameter(session, "session");
                Intrinsics.checkNotNullParameter(kotlinScopeProvider, "kotlinScopeProvider");
                Intrinsics.checkNotNullParameter(symbolProvider, "symbolProvider");
                Intrinsics.checkNotNullParameter(dependencies, "dependencies");
                SpreadBuilder spreadBuilder = new SpreadBuilder(4);
                spreadBuilder.add(symbolProvider);
                spreadBuilder.add(firSwitchableExtensionDeclarationsSymbolProvider);
                KlibIcData klibIcData = icData;
                spreadBuilder.add(klibIcData != null ? new KlibIcCacheBasedSymbolProvider(session, new SingleModuleDataProvider(moduleData), kotlinScopeProvider, klibIcData, null, 16, null) : null);
                spreadBuilder.addSpread(dependencies.toArray(new FirSymbolProvider[0]));
                return CollectionsKt.listOfNotNull(spreadBuilder.toArray(new FirSymbolProvider[spreadBuilder.size()]));
            }
        });
    }

    public final FirSession createLibrarySession(Name mainModuleName, final List<? extends KotlinLibrary> resolvedLibraries, FirProjectSessionProvider sessionProvider, final ModuleDataProvider moduleDataProvider, List<? extends FirExtensionRegistrar> extensionRegistrars, final CompilerConfiguration compilerConfiguration, final Function1<? super FirSession, Unit> registerExtraComponents) {
        Intrinsics.checkNotNullParameter(mainModuleName, "mainModuleName");
        Intrinsics.checkNotNullParameter(resolvedLibraries, "resolvedLibraries");
        Intrinsics.checkNotNullParameter(sessionProvider, "sessionProvider");
        Intrinsics.checkNotNullParameter(moduleDataProvider, "moduleDataProvider");
        Intrinsics.checkNotNullParameter(extensionRegistrars, "extensionRegistrars");
        Intrinsics.checkNotNullParameter(compilerConfiguration, "compilerConfiguration");
        Intrinsics.checkNotNullParameter(registerExtraComponents, "registerExtraComponents");
        return createLibrarySession(mainModuleName, sessionProvider, moduleDataProvider, CommonConfigurationKeysKt.getLanguageVersionSettings(compilerConfiguration), extensionRegistrars, new Function1<FirSession, Unit>() { // from class: org.jetbrains.kotlin.fir.session.FirJsSessionFactory.createLibrarySession.1
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
                FirJsSessionFactory.INSTANCE.registerJsSpecificComponents(it, compilerConfiguration);
                registerExtraComponents.invoke(it);
            }
        }, new Function0<FirKotlinScopeProvider>() { // from class: org.jetbrains.kotlin.fir.session.FirJsSessionFactory.createLibrarySession.2
            @Override // kotlin.jvm.functions.Function0
            public final FirKotlinScopeProvider invoke() {
                return new FirKotlinScopeProvider(null, 1, null);
            }
        }, new Function4<FirSession, FirModuleData, FirKotlinScopeProvider, FirExtensionSyntheticFunctionInterfaceProvider, List<? extends FirSymbolProvider>>() { // from class: org.jetbrains.kotlin.fir.session.FirJsSessionFactory.createLibrarySession.3
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(4);
            }

            @Override // kotlin.jvm.functions.Function4
            public final List<FirSymbolProvider> invoke(FirSession session, FirModuleData builtinsModuleData, FirKotlinScopeProvider kotlinScopeProvider, FirExtensionSyntheticFunctionInterfaceProvider firExtensionSyntheticFunctionInterfaceProvider) {
                Intrinsics.checkNotNullParameter(session, "session");
                Intrinsics.checkNotNullParameter(builtinsModuleData, "builtinsModuleData");
                Intrinsics.checkNotNullParameter(kotlinScopeProvider, "kotlinScopeProvider");
                return CollectionsKt.listOfNotNull((Object[]) new FirSymbolProvider[]{new KlibBasedSymbolProvider(session, moduleDataProvider, kotlinScopeProvider, resolvedLibraries, null, 16, null), new FirBuiltinSyntheticFunctionInterfaceProvider(session, builtinsModuleData, kotlinScopeProvider), firExtensionSyntheticFunctionInterfaceProvider});
            }
        });
    }

    public final void registerJsSpecificComponents(FirSession firSession, CompilerConfiguration compilerConfiguration) {
        Intrinsics.checkNotNullParameter(firSession, "<this>");
        Intrinsics.checkNotNullParameter(compilerConfiguration, "compilerConfiguration");
        firSession.register(Reflection.getOrCreateKotlinClass(FirVisibilityChecker.class), FirVisibilityChecker.Default.INSTANCE);
        firSession.register(Reflection.getOrCreateKotlinClass(ConeCallConflictResolverFactory.class), JsCallConflictResolverFactory.INSTANCE);
        firSession.register(Reflection.getOrCreateKotlinClass(FirTypeSpecificityComparatorProvider.class), new FirTypeSpecificityComparatorProvider(new JsTypeSpecificityComparatorWithoutDelegate(TypeComponentsKt.getTypeContext(firSession))));
        firSession.register(Reflection.getOrCreateKotlinClass(FirPlatformClassMapper.class), FirPlatformClassMapper.Default.INSTANCE);
        firSession.register(Reflection.getOrCreateKotlinClass(FirOverridesBackwardCompatibilityHelper.class), FirEmptyOverridesBackwardCompatibilityHelper.INSTANCE);
        firSession.register(Reflection.getOrCreateKotlinClass(FirPlatformDiagnosticSuppressor.class), new FirJsPlatformDiagnosticSuppressor());
        Object obj = compilerConfiguration.get(JSConfigurationKeys.MODULE_KIND, ModuleKind.PLAIN);
        Intrinsics.checkNotNullExpressionValue(obj, "get(...)");
        firSession.register(Reflection.getOrCreateKotlinClass(FirJsModuleKind.class), new FirJsModuleKind((ModuleKind) obj));
    }
}
