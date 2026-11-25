package org.jetbrains.kotlin.fir.session;

import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.functions.Function5;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.jvm.internal.SpreadBuilder;
import kotlin.reflect.KDeclarationContainer;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.fir.FirModuleData;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.checkers.CheckersContainersKt;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.deserialization.ModuleDataProvider;
import org.jetbrains.kotlin.fir.deserialization.SingleModuleDataProvider;
import org.jetbrains.kotlin.fir.extensions.FirExtensionRegistrar;
import org.jetbrains.kotlin.fir.extensions.FirSwitchableExtensionDeclarationsSymbolProvider;
import org.jetbrains.kotlin.fir.java.FirProjectSessionProvider;
import org.jetbrains.kotlin.fir.java.JavaSymbolProvider;
import org.jetbrains.kotlin.fir.java.deserialization.JvmClassFileBasedSymbolProvider;
import org.jetbrains.kotlin.fir.java.deserialization.OptionalAnnotationClassesProvider;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider;
import org.jetbrains.kotlin.fir.resolve.providers.impl.FirBuiltinSymbolProvider;
import org.jetbrains.kotlin.fir.resolve.providers.impl.FirCloneableSymbolProvider;
import org.jetbrains.kotlin.fir.resolve.providers.impl.FirExtensionSyntheticFunctionInterfaceProvider;
import org.jetbrains.kotlin.fir.resolve.scopes.JvmMappedScopesKt;
import org.jetbrains.kotlin.fir.scopes.FirContainingNamesAwareScope;
import org.jetbrains.kotlin.fir.scopes.FirKotlinScopeProvider;
import org.jetbrains.kotlin.fir.session.environment.AbstractProjectEnvironment;
import org.jetbrains.kotlin.fir.session.environment.AbstractProjectFileSearchScope;
import org.jetbrains.kotlin.incremental.components.EnumWhenTracker;
import org.jetbrains.kotlin.incremental.components.ImportTracker;
import org.jetbrains.kotlin.incremental.components.LookupTracker;
import org.jetbrains.kotlin.load.kotlin.PackagePartProvider;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FirJvmSessionFactory.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0082\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J`\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0012\u0010\u0016\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00180\u0017J¥\u0001\u0010\u0019\u001a\u00020\u00042\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u001c\u001a\u00020\u00112\u0006\u0010\u000b\u001a\u00020\f2\b\u0010\u001d\u001a\u0004\u0018\u00010\u001e2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e2\b\b\u0002\u0010\u0014\u001a\u00020\u00152\n\b\u0002\u0010\u001f\u001a\u0004\u0018\u00010 2\n\b\u0002\u0010!\u001a\u0004\u0018\u00010\"2\n\b\u0002\u0010#\u001a\u0004\u0018\u00010$2\u0006\u0010%\u001a\u00020&2\u0014\b\u0002\u0010\u0016\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00180\u00172\u0019\b\u0002\u0010'\u001a\u0013\u0012\u0004\u0012\u00020(\u0012\u0004\u0012\u00020\u00180\u0017¢\u0006\u0002\b)¨\u0006*"}, d2 = {"Lorg/jetbrains/kotlin/fir/session/FirJvmSessionFactory;", "Lorg/jetbrains/kotlin/fir/session/FirAbstractSessionFactory;", "()V", "createLibrarySession", "Lorg/jetbrains/kotlin/fir/FirSession;", "mainModuleName", "Lorg/jetbrains/kotlin/name/Name;", "sessionProvider", "Lorg/jetbrains/kotlin/fir/java/FirProjectSessionProvider;", "moduleDataProvider", "Lorg/jetbrains/kotlin/fir/deserialization/ModuleDataProvider;", "projectEnvironment", "Lorg/jetbrains/kotlin/fir/session/environment/AbstractProjectEnvironment;", "extensionRegistrars", "", "Lorg/jetbrains/kotlin/fir/extensions/FirExtensionRegistrar;", "scope", "Lorg/jetbrains/kotlin/fir/session/environment/AbstractProjectFileSearchScope;", "packagePartProvider", "Lorg/jetbrains/kotlin/load/kotlin/PackagePartProvider;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "registerExtraComponents", "Lkotlin/Function1;", "", "createModuleBasedSession", "moduleData", "Lorg/jetbrains/kotlin/fir/FirModuleData;", "javaSourcesScope", "incrementalCompilationContext", "Lorg/jetbrains/kotlin/fir/session/IncrementalCompilationContext;", "lookupTracker", "Lorg/jetbrains/kotlin/incremental/components/LookupTracker;", "enumWhenTracker", "Lorg/jetbrains/kotlin/incremental/components/EnumWhenTracker;", "importTracker", "Lorg/jetbrains/kotlin/incremental/components/ImportTracker;", "needRegisterJavaElementFinder", "", ConfigConstants.CONFIG_INIT_SECTION, "Lorg/jetbrains/kotlin/fir/session/FirSessionConfigurator;", "Lkotlin/ExtensionFunctionType;", "entrypoint"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirJvmSessionFactory extends FirAbstractSessionFactory {
    public static final FirJvmSessionFactory INSTANCE = new FirJvmSessionFactory();

    private FirJvmSessionFactory() {
    }

    public final FirSession createLibrarySession(Name mainModuleName, FirProjectSessionProvider sessionProvider, final ModuleDataProvider moduleDataProvider, final AbstractProjectEnvironment projectEnvironment, List<? extends FirExtensionRegistrar> extensionRegistrars, final AbstractProjectFileSearchScope scope, final PackagePartProvider packagePartProvider, LanguageVersionSettings languageVersionSettings, final Function1<? super FirSession, Unit> registerExtraComponents) {
        Intrinsics.checkNotNullParameter(mainModuleName, "mainModuleName");
        Intrinsics.checkNotNullParameter(sessionProvider, "sessionProvider");
        Intrinsics.checkNotNullParameter(moduleDataProvider, "moduleDataProvider");
        Intrinsics.checkNotNullParameter(projectEnvironment, "projectEnvironment");
        Intrinsics.checkNotNullParameter(extensionRegistrars, "extensionRegistrars");
        Intrinsics.checkNotNullParameter(scope, "scope");
        Intrinsics.checkNotNullParameter(packagePartProvider, "packagePartProvider");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(registerExtraComponents, "registerExtraComponents");
        return createLibrarySession(mainModuleName, sessionProvider, moduleDataProvider, languageVersionSettings, extensionRegistrars, new Function1<FirSession, Unit>() { // from class: org.jetbrains.kotlin.fir.session.FirJvmSessionFactory.createLibrarySession.1
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
                ComponentsContainersKt.registerCommonJavaComponents(it, projectEnvironment.getJavaModuleResolver());
                registerExtraComponents.invoke(it);
            }
        }, new Function0<FirKotlinScopeProvider>() { // from class: org.jetbrains.kotlin.fir.session.FirJvmSessionFactory.createLibrarySession.2

            /* JADX INFO: renamed from: org.jetbrains.kotlin.fir.session.FirJvmSessionFactory$createLibrarySession$2$1, reason: invalid class name */
            /* JADX INFO: compiled from: FirJvmSessionFactory.kt */
            @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
            /* synthetic */ class AnonymousClass1 extends FunctionReference implements Function5<FirClass, FirContainingNamesAwareScope, FirSession, ScopeSession, FirResolvePhase, FirContainingNamesAwareScope> {
                public static final AnonymousClass1 INSTANCE = new AnonymousClass1();

                AnonymousClass1() {
                    super(5);
                }

                @Override // kotlin.jvm.internal.CallableReference
                public final String getSignature() {
                    return "wrapScopeWithJvmMapped(Lorg/jetbrains/kotlin/fir/declarations/FirClass;Lorg/jetbrains/kotlin/fir/scopes/FirContainingNamesAwareScope;Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;)Lorg/jetbrains/kotlin/fir/scopes/FirContainingNamesAwareScope;";
                }

                @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
                public final String getName() {
                    return "wrapScopeWithJvmMapped";
                }

                @Override // kotlin.jvm.internal.CallableReference
                public final KDeclarationContainer getOwner() {
                    return Reflection.getOrCreateKotlinPackage(JvmMappedScopesKt.class, "entrypoint");
                }

                @Override // kotlin.jvm.functions.Function5
                public final FirContainingNamesAwareScope invoke(FirClass p0, FirContainingNamesAwareScope p1, FirSession p2, ScopeSession p3, FirResolvePhase firResolvePhase) {
                    Intrinsics.checkNotNullParameter(p0, "p0");
                    Intrinsics.checkNotNullParameter(p1, "p1");
                    Intrinsics.checkNotNullParameter(p2, "p2");
                    Intrinsics.checkNotNullParameter(p3, "p3");
                    return JvmMappedScopesKt.wrapScopeWithJvmMapped(p0, p1, p2, p3, firResolvePhase);
                }
            }

            @Override // kotlin.jvm.functions.Function0
            public final FirKotlinScopeProvider invoke() {
                return new FirKotlinScopeProvider(AnonymousClass1.INSTANCE);
            }
        }, new Function4<FirSession, FirModuleData, FirKotlinScopeProvider, FirExtensionSyntheticFunctionInterfaceProvider, List<? extends FirSymbolProvider>>() { // from class: org.jetbrains.kotlin.fir.session.FirJvmSessionFactory.createLibrarySession.3
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(4);
            }

            @Override // kotlin.jvm.functions.Function4
            public final List<FirSymbolProvider> invoke(FirSession session, FirModuleData builtinsModuleData, FirKotlinScopeProvider kotlinScopeProvider, FirExtensionSyntheticFunctionInterfaceProvider firExtensionSyntheticFunctionInterfaceProvider) {
                Intrinsics.checkNotNullParameter(session, "session");
                Intrinsics.checkNotNullParameter(builtinsModuleData, "builtinsModuleData");
                Intrinsics.checkNotNullParameter(kotlinScopeProvider, "kotlinScopeProvider");
                return CollectionsKt.listOfNotNull((Object[]) new FirSymbolProvider[]{new JvmClassFileBasedSymbolProvider(session, moduleDataProvider, kotlinScopeProvider, packagePartProvider, projectEnvironment.getKotlinClassFinder(scope), projectEnvironment.getFirJavaFacade(session, (FirModuleData) CollectionsKt.last(moduleDataProvider.getAllModuleData()), scope), null, 64, null), new FirBuiltinSymbolProvider(session, builtinsModuleData, kotlinScopeProvider), firExtensionSyntheticFunctionInterfaceProvider, new FirCloneableSymbolProvider(session, builtinsModuleData, kotlinScopeProvider), new OptionalAnnotationClassesProvider(session, moduleDataProvider, kotlinScopeProvider, packagePartProvider, null, 16, null)});
            }
        });
    }

    public final FirSession createModuleBasedSession(final FirModuleData moduleData, FirProjectSessionProvider sessionProvider, final AbstractProjectFileSearchScope javaSourcesScope, final AbstractProjectEnvironment projectEnvironment, final IncrementalCompilationContext incrementalCompilationContext, List<? extends FirExtensionRegistrar> extensionRegistrars, LanguageVersionSettings languageVersionSettings, LookupTracker lookupTracker, EnumWhenTracker enumWhenTracker, ImportTracker importTracker, boolean needRegisterJavaElementFinder, final Function1<? super FirSession, Unit> registerExtraComponents, Function1<? super FirSessionConfigurator, Unit> init) {
        Intrinsics.checkNotNullParameter(moduleData, "moduleData");
        Intrinsics.checkNotNullParameter(sessionProvider, "sessionProvider");
        Intrinsics.checkNotNullParameter(javaSourcesScope, "javaSourcesScope");
        Intrinsics.checkNotNullParameter(projectEnvironment, "projectEnvironment");
        Intrinsics.checkNotNullParameter(extensionRegistrars, "extensionRegistrars");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(registerExtraComponents, "registerExtraComponents");
        Intrinsics.checkNotNullParameter(init, "init");
        FirSession firSessionCreateModuleBasedSession = createModuleBasedSession(moduleData, sessionProvider, extensionRegistrars, languageVersionSettings, lookupTracker, enumWhenTracker, importTracker, init, new Function1<FirSession, Unit>() { // from class: org.jetbrains.kotlin.fir.session.FirJvmSessionFactory.createModuleBasedSession.3
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
                ComponentsContainersKt.registerCommonJavaComponents(it, projectEnvironment.getJavaModuleResolver());
                ComponentsContainersKt.registerJavaSpecificResolveComponents(it);
                registerExtraComponents.invoke(it);
            }
        }, new Function1<FirSessionConfigurator, Unit>() { // from class: org.jetbrains.kotlin.fir.session.FirJvmSessionFactory.createModuleBasedSession.4
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(FirSessionConfigurator firSessionConfigurator) {
                invoke2(firSessionConfigurator);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FirSessionConfigurator it) {
                Intrinsics.checkNotNullParameter(it, "it");
                CheckersContainersKt.registerJvmCheckers(it);
            }
        }, new Function0<FirKotlinScopeProvider>() { // from class: org.jetbrains.kotlin.fir.session.FirJvmSessionFactory.createModuleBasedSession.5

            /* JADX INFO: renamed from: org.jetbrains.kotlin.fir.session.FirJvmSessionFactory$createModuleBasedSession$5$1, reason: invalid class name */
            /* JADX INFO: compiled from: FirJvmSessionFactory.kt */
            @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
            /* synthetic */ class AnonymousClass1 extends FunctionReference implements Function5<FirClass, FirContainingNamesAwareScope, FirSession, ScopeSession, FirResolvePhase, FirContainingNamesAwareScope> {
                public static final AnonymousClass1 INSTANCE = new AnonymousClass1();

                AnonymousClass1() {
                    super(5);
                }

                @Override // kotlin.jvm.internal.CallableReference
                public final String getSignature() {
                    return "wrapScopeWithJvmMapped(Lorg/jetbrains/kotlin/fir/declarations/FirClass;Lorg/jetbrains/kotlin/fir/scopes/FirContainingNamesAwareScope;Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;)Lorg/jetbrains/kotlin/fir/scopes/FirContainingNamesAwareScope;";
                }

                @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
                public final String getName() {
                    return "wrapScopeWithJvmMapped";
                }

                @Override // kotlin.jvm.internal.CallableReference
                public final KDeclarationContainer getOwner() {
                    return Reflection.getOrCreateKotlinPackage(JvmMappedScopesKt.class, "entrypoint");
                }

                @Override // kotlin.jvm.functions.Function5
                public final FirContainingNamesAwareScope invoke(FirClass p0, FirContainingNamesAwareScope p1, FirSession p2, ScopeSession p3, FirResolvePhase firResolvePhase) {
                    Intrinsics.checkNotNullParameter(p0, "p0");
                    Intrinsics.checkNotNullParameter(p1, "p1");
                    Intrinsics.checkNotNullParameter(p2, "p2");
                    Intrinsics.checkNotNullParameter(p3, "p3");
                    return JvmMappedScopesKt.wrapScopeWithJvmMapped(p0, p1, p2, p3, firResolvePhase);
                }
            }

            @Override // kotlin.jvm.functions.Function0
            public final FirKotlinScopeProvider invoke() {
                return new FirKotlinScopeProvider(AnonymousClass1.INSTANCE);
            }
        }, new Function5<FirSession, FirKotlinScopeProvider, FirSymbolProvider, FirSwitchableExtensionDeclarationsSymbolProvider, List<? extends FirSymbolProvider>, List<? extends FirSymbolProvider>>() { // from class: org.jetbrains.kotlin.fir.session.FirJvmSessionFactory.createModuleBasedSession.6
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(5);
            }

            /* JADX WARN: Code duplicated, block: B:9:0x0070  */
            @Override // kotlin.jvm.functions.Function5
            public final List<FirSymbolProvider> invoke(FirSession session, FirKotlinScopeProvider kotlinScopeProvider, FirSymbolProvider symbolProvider, FirSwitchableExtensionDeclarationsSymbolProvider firSwitchableExtensionDeclarationsSymbolProvider, List<? extends FirSymbolProvider> dependencies) {
                OptionalAnnotationClassesProvider optionalAnnotationClassesProvider;
                FirSymbolProvider[] firSymbolProviderArr;
                Collection<FirSymbolProvider> previousFirSessionsSymbolProviders;
                Intrinsics.checkNotNullParameter(session, "session");
                Intrinsics.checkNotNullParameter(kotlinScopeProvider, "kotlinScopeProvider");
                Intrinsics.checkNotNullParameter(symbolProvider, "symbolProvider");
                Intrinsics.checkNotNullParameter(dependencies, "dependencies");
                IncrementalCompilationContext incrementalCompilationContext2 = incrementalCompilationContext;
                JvmClassFileBasedSymbolProvider jvmClassFileBasedSymbolProvider = null;
                if (incrementalCompilationContext2 != null) {
                    FirModuleData firModuleData = moduleData;
                    AbstractProjectEnvironment abstractProjectEnvironment = projectEnvironment;
                    if (incrementalCompilationContext2.getPrecompiledBinariesPackagePartProvider() == null || incrementalCompilationContext2.getPrecompiledBinariesFileScope() == null) {
                        optionalAnnotationClassesProvider = null;
                    } else {
                        SingleModuleDataProvider singleModuleDataProvider = new SingleModuleDataProvider(firModuleData);
                        JvmClassFileBasedSymbolProvider jvmClassFileBasedSymbolProvider2 = new JvmClassFileBasedSymbolProvider(session, singleModuleDataProvider, kotlinScopeProvider, incrementalCompilationContext2.getPrecompiledBinariesPackagePartProvider(), abstractProjectEnvironment.getKotlinClassFinder(incrementalCompilationContext2.getPrecompiledBinariesFileScope()), abstractProjectEnvironment.getFirJavaFacade(session, firModuleData, incrementalCompilationContext2.getPrecompiledBinariesFileScope()), FirDeclarationOrigin.Precompiled.INSTANCE);
                        optionalAnnotationClassesProvider = new OptionalAnnotationClassesProvider(session, singleModuleDataProvider, kotlinScopeProvider, incrementalCompilationContext2.getPrecompiledBinariesPackagePartProvider(), FirDeclarationOrigin.Precompiled.INSTANCE);
                        jvmClassFileBasedSymbolProvider = jvmClassFileBasedSymbolProvider2;
                    }
                } else {
                    optionalAnnotationClassesProvider = null;
                }
                JavaSymbolProvider javaSymbolProvider = new JavaSymbolProvider(session, projectEnvironment.getFirJavaFacade(session, moduleData, javaSourcesScope));
                session.register(Reflection.getOrCreateKotlinClass(JavaSymbolProvider.class), javaSymbolProvider);
                SpreadBuilder spreadBuilder = new SpreadBuilder(7);
                spreadBuilder.add(symbolProvider);
                IncrementalCompilationContext incrementalCompilationContext3 = incrementalCompilationContext;
                if (incrementalCompilationContext3 == null || (previousFirSessionsSymbolProviders = incrementalCompilationContext3.getPreviousFirSessionsSymbolProviders()) == null || (firSymbolProviderArr = (FirSymbolProvider[]) previousFirSessionsSymbolProviders.toArray(new FirSymbolProvider[0])) == null) {
                    firSymbolProviderArr = new FirSymbolProvider[0];
                }
                spreadBuilder.addSpread(firSymbolProviderArr);
                spreadBuilder.add(jvmClassFileBasedSymbolProvider);
                spreadBuilder.add(firSwitchableExtensionDeclarationsSymbolProvider);
                spreadBuilder.add(javaSymbolProvider);
                spreadBuilder.addSpread(dependencies.toArray(new FirSymbolProvider[0]));
                spreadBuilder.add(optionalAnnotationClassesProvider);
                return CollectionsKt.listOfNotNull(spreadBuilder.toArray(new FirSymbolProvider[spreadBuilder.size()]));
            }
        });
        if (needRegisterJavaElementFinder) {
            projectEnvironment.registerAsJavaElementFinder(firSessionCreateModuleBasedSession);
        }
        return firSessionCreateModuleBasedSession;
    }
}
