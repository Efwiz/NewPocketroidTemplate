package org.jetbrains.kotlin.fir.session;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
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
import org.jetbrains.kotlin.fir.BinaryModuleData;
import org.jetbrains.kotlin.fir.FirModuleData;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.FirVisibilityChecker;
import org.jetbrains.kotlin.fir.analysis.FirEmptyOverridesBackwardCompatibilityHelper;
import org.jetbrains.kotlin.fir.analysis.FirOverridesBackwardCompatibilityHelper;
import org.jetbrains.kotlin.fir.checkers.CheckersContainersKt;
import org.jetbrains.kotlin.fir.deserialization.ModuleDataProvider;
import org.jetbrains.kotlin.fir.extensions.FirExtensionRegistrar;
import org.jetbrains.kotlin.fir.extensions.FirSwitchableExtensionDeclarationsSymbolProvider;
import org.jetbrains.kotlin.fir.java.FirProjectSessionProvider;
import org.jetbrains.kotlin.fir.resolve.calls.ConeCallConflictResolverFactory;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider;
import org.jetbrains.kotlin.fir.resolve.providers.impl.FirBuiltinSyntheticFunctionInterfaceProvider;
import org.jetbrains.kotlin.fir.resolve.providers.impl.FirExtensionSyntheticFunctionInterfaceProvider;
import org.jetbrains.kotlin.fir.scopes.FirKotlinScopeProvider;
import org.jetbrains.kotlin.fir.scopes.FirPlatformClassMapper;
import org.jetbrains.kotlin.library.KotlinLibrary;
import org.jetbrains.kotlin.library.KotlinLibraryKt;
import org.jetbrains.kotlin.library.metadata.resolver.KotlinResolvedLibrary;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FirNativeSessionFactory.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002JX\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\b2\u0006\u0010\u0010\u001a\u00020\u00112\u0014\b\u0002\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00140\u0013J[\u0010\u0015\u001a\u00020\u00042\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\n\u001a\u00020\u000b2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\b2\u0006\u0010\u0010\u001a\u00020\u00112\u0017\u0010\u0018\u001a\u0013\u0012\u0004\u0012\u00020\u0019\u0012\u0004\u0012\u00020\u00140\u0013¢\u0006\u0002\b\u001a2\u0014\b\u0002\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00140\u0013¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/fir/session/FirNativeSessionFactory;", "Lorg/jetbrains/kotlin/fir/session/FirAbstractSessionFactory;", "()V", "createLibrarySession", "Lorg/jetbrains/kotlin/fir/FirSession;", "mainModuleName", "Lorg/jetbrains/kotlin/name/Name;", "resolvedLibraries", "", "Lorg/jetbrains/kotlin/library/metadata/resolver/KotlinResolvedLibrary;", "sessionProvider", "Lorg/jetbrains/kotlin/fir/java/FirProjectSessionProvider;", "moduleDataProvider", "Lorg/jetbrains/kotlin/fir/deserialization/ModuleDataProvider;", "extensionRegistrars", "Lorg/jetbrains/kotlin/fir/extensions/FirExtensionRegistrar;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "registerExtraComponents", "Lkotlin/Function1;", "", "createModuleBasedSession", "moduleData", "Lorg/jetbrains/kotlin/fir/FirModuleData;", ConfigConstants.CONFIG_INIT_SECTION, "Lorg/jetbrains/kotlin/fir/session/FirSessionConfigurator;", "Lkotlin/ExtensionFunctionType;", "entrypoint"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirNativeSessionFactory extends FirAbstractSessionFactory {
    public static final FirNativeSessionFactory INSTANCE = new FirNativeSessionFactory();

    private FirNativeSessionFactory() {
    }

    public final FirSession createLibrarySession(Name mainModuleName, final List<? extends KotlinResolvedLibrary> resolvedLibraries, FirProjectSessionProvider sessionProvider, final ModuleDataProvider moduleDataProvider, List<? extends FirExtensionRegistrar> extensionRegistrars, LanguageVersionSettings languageVersionSettings, final Function1<? super FirSession, Unit> registerExtraComponents) {
        Intrinsics.checkNotNullParameter(mainModuleName, "mainModuleName");
        Intrinsics.checkNotNullParameter(resolvedLibraries, "resolvedLibraries");
        Intrinsics.checkNotNullParameter(sessionProvider, "sessionProvider");
        Intrinsics.checkNotNullParameter(moduleDataProvider, "moduleDataProvider");
        Intrinsics.checkNotNullParameter(extensionRegistrars, "extensionRegistrars");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(registerExtraComponents, "registerExtraComponents");
        return createLibrarySession(mainModuleName, sessionProvider, moduleDataProvider, languageVersionSettings, extensionRegistrars, new Function1<FirSession, Unit>() { // from class: org.jetbrains.kotlin.fir.session.FirNativeSessionFactory.createLibrarySession.2
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
                registerExtraComponents.invoke(session);
            }
        }, new Function0<FirKotlinScopeProvider>() { // from class: org.jetbrains.kotlin.fir.session.FirNativeSessionFactory.createLibrarySession.3
            @Override // kotlin.jvm.functions.Function0
            public final FirKotlinScopeProvider invoke() {
                return new FirKotlinScopeProvider(null, 1, null);
            }
        }, new Function4<FirSession, FirModuleData, FirKotlinScopeProvider, FirExtensionSyntheticFunctionInterfaceProvider, List<? extends FirSymbolProvider>>() { // from class: org.jetbrains.kotlin.fir.session.FirNativeSessionFactory.createLibrarySession.4
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
                BinaryModuleData.Companion companion = BinaryModuleData.INSTANCE;
                Name nameSpecial = Name.special("<forward declarations>");
                Intrinsics.checkNotNullExpressionValue(nameSpecial, "special(...)");
                FirModuleData firModuleDataCreateDependencyModuleData$default = BinaryModuleData.Companion.createDependencyModuleData$default(companion, nameSpecial, moduleDataProvider.getPlatform(), moduleDataProvider.getAnalyzerServices(), null, 8, null);
                firModuleDataCreateDependencyModuleData$default.bindSession(session);
                List<KotlinResolvedLibrary> list = resolvedLibraries;
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                Iterator<T> it = list.iterator();
                while (it.hasNext()) {
                    arrayList.add(((KotlinResolvedLibrary) it.next()).getLibrary());
                }
                ArrayList arrayList2 = new ArrayList();
                ArrayList arrayList3 = new ArrayList();
                for (Object obj : arrayList) {
                    if (KotlinLibraryKt.isNativeStdlib((KotlinLibrary) obj)) {
                        arrayList2.add(obj);
                    } else {
                        arrayList3.add(obj);
                    }
                }
                Pair pair = new Pair(arrayList2, arrayList3);
                List listPlus = CollectionsKt.plus((Collection) pair.component1(), (Iterable) pair.component2());
                return CollectionsKt.listOfNotNull((Object[]) new FirSymbolProvider[]{new KlibBasedSymbolProvider(session, moduleDataProvider, kotlinScopeProvider, listPlus, null, 16, null), new NativeForwardDeclarationsSymbolProvider(session, firModuleDataCreateDependencyModuleData$default, kotlinScopeProvider, listPlus), new FirBuiltinSyntheticFunctionInterfaceProvider(session, builtinsModuleData, kotlinScopeProvider), firExtensionSyntheticFunctionInterfaceProvider});
            }
        });
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ FirSession createModuleBasedSession$default(FirNativeSessionFactory firNativeSessionFactory, FirModuleData firModuleData, FirProjectSessionProvider firProjectSessionProvider, List list, LanguageVersionSettings languageVersionSettings, Function1 function1, Function1 function2, int i, Object obj) {
        if ((i & 32) != 0) {
            function2 = new Function1<FirSession, Unit>() { // from class: org.jetbrains.kotlin.fir.session.FirNativeSessionFactory.createModuleBasedSession.1
                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(FirSession it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(FirSession firSession) {
                    invoke2(firSession);
                    return Unit.INSTANCE;
                }
            };
        }
        return firNativeSessionFactory.createModuleBasedSession(firModuleData, firProjectSessionProvider, list, languageVersionSettings, function1, function2);
    }

    public final FirSession createModuleBasedSession(FirModuleData moduleData, FirProjectSessionProvider sessionProvider, List<? extends FirExtensionRegistrar> extensionRegistrars, LanguageVersionSettings languageVersionSettings, Function1<? super FirSessionConfigurator, Unit> init, final Function1<? super FirSession, Unit> registerExtraComponents) {
        Intrinsics.checkNotNullParameter(moduleData, "moduleData");
        Intrinsics.checkNotNullParameter(sessionProvider, "sessionProvider");
        Intrinsics.checkNotNullParameter(extensionRegistrars, "extensionRegistrars");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(init, "init");
        Intrinsics.checkNotNullParameter(registerExtraComponents, "registerExtraComponents");
        return createModuleBasedSession(moduleData, sessionProvider, extensionRegistrars, languageVersionSettings, null, null, null, init, new Function1<FirSession, Unit>() { // from class: org.jetbrains.kotlin.fir.session.FirNativeSessionFactory.createModuleBasedSession.2
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
                it.register(Reflection.getOrCreateKotlinClass(FirOverridesBackwardCompatibilityHelper.class), FirEmptyOverridesBackwardCompatibilityHelper.INSTANCE);
                registerExtraComponents.invoke(it);
            }
        }, new Function1<FirSessionConfigurator, Unit>() { // from class: org.jetbrains.kotlin.fir.session.FirNativeSessionFactory.createModuleBasedSession.3
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(FirSessionConfigurator firSessionConfigurator) {
                invoke2(firSessionConfigurator);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FirSessionConfigurator it) {
                Intrinsics.checkNotNullParameter(it, "it");
                CheckersContainersKt.registerNativeCheckers(it);
            }
        }, new Function0<FirKotlinScopeProvider>() { // from class: org.jetbrains.kotlin.fir.session.FirNativeSessionFactory.createModuleBasedSession.4
            @Override // kotlin.jvm.functions.Function0
            public final FirKotlinScopeProvider invoke() {
                return new FirKotlinScopeProvider(null, 1, null);
            }
        }, new Function5<FirSession, FirKotlinScopeProvider, FirSymbolProvider, FirSwitchableExtensionDeclarationsSymbolProvider, List<? extends FirSymbolProvider>, List<? extends FirSymbolProvider>>() { // from class: org.jetbrains.kotlin.fir.session.FirNativeSessionFactory.createModuleBasedSession.5
            @Override // kotlin.jvm.functions.Function5
            public final List<FirSymbolProvider> invoke(FirSession firSession, FirKotlinScopeProvider firKotlinScopeProvider, FirSymbolProvider symbolProvider, FirSwitchableExtensionDeclarationsSymbolProvider firSwitchableExtensionDeclarationsSymbolProvider, List<? extends FirSymbolProvider> dependencies) {
                Intrinsics.checkNotNullParameter(firSession, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(firKotlinScopeProvider, "<anonymous parameter 1>");
                Intrinsics.checkNotNullParameter(symbolProvider, "symbolProvider");
                Intrinsics.checkNotNullParameter(dependencies, "dependencies");
                SpreadBuilder spreadBuilder = new SpreadBuilder(3);
                spreadBuilder.add(symbolProvider);
                spreadBuilder.add(firSwitchableExtensionDeclarationsSymbolProvider);
                spreadBuilder.addSpread(dependencies.toArray(new FirSymbolProvider[0]));
                return CollectionsKt.listOfNotNull(spreadBuilder.toArray(new FirSymbolProvider[spreadBuilder.size()]));
            }
        });
    }
}
