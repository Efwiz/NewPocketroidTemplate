package org.jetbrains.kotlin.fir.session;

import java.util.List;
import kotlin.Metadata;
import kotlin.NotImplementedError;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Reflection;
import org.jetbrains.kotlin.config.AnalysisFlag;
import org.jetbrains.kotlin.config.ApiVersion;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersion;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.fir.BinaryModuleData;
import org.jetbrains.kotlin.fir.DependencyListForCliModule;
import org.jetbrains.kotlin.fir.FirLanguageSettingsComponent;
import org.jetbrains.kotlin.fir.FirModuleDataImpl;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.extensions.FirExtensionRegistrar;
import org.jetbrains.kotlin.fir.extensions.FirExtensionService;
import org.jetbrains.kotlin.fir.java.FirProjectSessionProvider;
import org.jetbrains.kotlin.fir.session.environment.AbstractProjectEnvironment;
import org.jetbrains.kotlin.fir.session.environment.AbstractProjectFileSearchScope;
import org.jetbrains.kotlin.incremental.components.EnumWhenTracker;
import org.jetbrains.kotlin.incremental.components.ImportTracker;
import org.jetbrains.kotlin.incremental.components.LookupTracker;
import org.jetbrains.kotlin.konan.library.NativeLibraryConstantsKt;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.platform.TargetPlatform;
import org.jetbrains.kotlin.platform.jvm.JvmPlatforms;
import org.jetbrains.kotlin.resolve.PlatformDependentAnalyzerServices;
import org.jetbrains.kotlin.resolve.jvm.platform.JvmPlatformAnalyzerServices;

/* JADX INFO: compiled from: FirSessionFactoryHelper.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0080\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0003\u001a\u00020\u0004JÂ\u0001\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00132\b\u0010\u0015\u001a\u0004\u0018\u00010\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u001c2\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001f0\u001e2\u0006\u0010 \u001a\u00020!2\u0019\b\u0002\u0010\"\u001a\u0013\u0012\u0004\u0012\u00020$\u0012\u0004\u0012\u00020%0#¢\u0006\u0002\b&2\u0019\b\n\u0010'\u001a\u0013\u0012\u0004\u0012\u00020(\u0012\u0004\u0012\u00020%0#¢\u0006\u0002\b&H\u0086\bø\u0001\u0000\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006)"}, d2 = {"Lorg/jetbrains/kotlin/fir/session/FirSessionFactoryHelper;", "", "()V", "createEmptySession", "Lorg/jetbrains/kotlin/fir/FirSession;", "createSessionWithDependencies", "moduleName", "Lorg/jetbrains/kotlin/name/Name;", NativeLibraryConstantsKt.KONAN_DISTRIBUTION_PLATFORM_LIBS_DIR, "Lorg/jetbrains/kotlin/platform/TargetPlatform;", "analyzerServices", "Lorg/jetbrains/kotlin/resolve/PlatformDependentAnalyzerServices;", "externalSessionProvider", "Lorg/jetbrains/kotlin/fir/java/FirProjectSessionProvider;", "projectEnvironment", "Lorg/jetbrains/kotlin/fir/session/environment/AbstractProjectEnvironment;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "javaSourcesScope", "Lorg/jetbrains/kotlin/fir/session/environment/AbstractProjectFileSearchScope;", "librariesScope", "lookupTracker", "Lorg/jetbrains/kotlin/incremental/components/LookupTracker;", "enumWhenTracker", "Lorg/jetbrains/kotlin/incremental/components/EnumWhenTracker;", "importTracker", "Lorg/jetbrains/kotlin/incremental/components/ImportTracker;", "incrementalCompilationContext", "Lorg/jetbrains/kotlin/fir/session/IncrementalCompilationContext;", "extensionRegistrars", "", "Lorg/jetbrains/kotlin/fir/extensions/FirExtensionRegistrar;", "needRegisterJavaElementFinder", "", "dependenciesConfigurator", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/fir/DependencyListForCliModule$Builder;", "", "Lkotlin/ExtensionFunctionType;", "sessionConfigurator", "Lorg/jetbrains/kotlin/fir/session/FirSessionConfigurator;", "entrypoint"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirSessionFactoryHelper {
    public static final FirSessionFactoryHelper INSTANCE = new FirSessionFactoryHelper();

    private FirSessionFactoryHelper() {
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$PrimitiveArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    public static /* synthetic */ FirSession createSessionWithDependencies$default(FirSessionFactoryHelper firSessionFactoryHelper, Name moduleName, TargetPlatform platform, PlatformDependentAnalyzerServices analyzerServices, FirProjectSessionProvider firProjectSessionProvider, AbstractProjectEnvironment projectEnvironment, LanguageVersionSettings languageVersionSettings, AbstractProjectFileSearchScope javaSourcesScope, AbstractProjectFileSearchScope librariesScope, LookupTracker lookupTracker, EnumWhenTracker enumWhenTracker, ImportTracker importTracker, IncrementalCompilationContext incrementalCompilationContext, List extensionRegistrars, boolean z, Function1 function1, Function1 function2, int i, Object obj) {
        C46361 dependenciesConfigurator = (i & 16384) != 0 ? new Function1<DependencyListForCliModule.Builder, Unit>() { // from class: org.jetbrains.kotlin.fir.session.FirSessionFactoryHelper.createSessionWithDependencies.1
            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(DependencyListForCliModule.Builder builder) {
                Intrinsics.checkNotNullParameter(builder, "$this$null");
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(DependencyListForCliModule.Builder builder) {
                invoke2(builder);
                return Unit.INSTANCE;
            }
        } : function1;
        Function1 sessionConfigurator = (i & 32768) != 0 ? new Function1<FirSessionConfigurator, Unit>() { // from class: org.jetbrains.kotlin.fir.session.FirSessionFactoryHelper.createSessionWithDependencies.2
            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FirSessionConfigurator firSessionConfigurator) {
                Intrinsics.checkNotNullParameter(firSessionConfigurator, "$this$null");
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(FirSessionConfigurator firSessionConfigurator) {
                invoke2(firSessionConfigurator);
                return Unit.INSTANCE;
            }
        } : function2;
        Intrinsics.checkNotNullParameter(moduleName, "moduleName");
        Intrinsics.checkNotNullParameter(platform, "platform");
        Intrinsics.checkNotNullParameter(analyzerServices, "analyzerServices");
        Intrinsics.checkNotNullParameter(projectEnvironment, "projectEnvironment");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(javaSourcesScope, "javaSourcesScope");
        Intrinsics.checkNotNullParameter(librariesScope, "librariesScope");
        Intrinsics.checkNotNullParameter(extensionRegistrars, "extensionRegistrars");
        Intrinsics.checkNotNullParameter(dependenciesConfigurator, "dependenciesConfigurator");
        Intrinsics.checkNotNullParameter(sessionConfigurator, "sessionConfigurator");
        BinaryModuleData binaryModuleDataInitialize = BinaryModuleData.INSTANCE.initialize(moduleName, platform, analyzerServices);
        DependencyListForCliModule.Companion companion = DependencyListForCliModule.INSTANCE;
        DependencyListForCliModule.Builder builder = new DependencyListForCliModule.Builder(binaryModuleDataInitialize);
        dependenciesConfigurator.invoke(builder);
        DependencyListForCliModule dependencyListForCliModuleBuild = builder.build();
        FirProjectSessionProvider firProjectSessionProvider2 = firProjectSessionProvider == null ? new FirProjectSessionProvider() : firProjectSessionProvider;
        FirJvmSessionFactory.INSTANCE.createLibrarySession(moduleName, firProjectSessionProvider2, dependencyListForCliModuleBuild.getModuleDataProvider(), projectEnvironment, extensionRegistrars, librariesScope, projectEnvironment.getPackagePartProvider(librariesScope), languageVersionSettings, AnonymousClass3.INSTANCE);
        return FirJvmSessionFactory.INSTANCE.createModuleBasedSession(new FirModuleDataImpl(moduleName, dependencyListForCliModuleBuild.getRegularDependencies(), dependencyListForCliModuleBuild.getDependsOnDependencies(), dependencyListForCliModuleBuild.getFriendsDependencies(), platform, analyzerServices, null, 64, null), firProjectSessionProvider2, javaSourcesScope, projectEnvironment, incrementalCompilationContext, extensionRegistrars, languageVersionSettings, lookupTracker, enumWhenTracker, importTracker, z, AnonymousClass4.INSTANCE, sessionConfigurator);
    }

    public final FirSession createSessionWithDependencies(Name moduleName, TargetPlatform platform, PlatformDependentAnalyzerServices analyzerServices, FirProjectSessionProvider externalSessionProvider, AbstractProjectEnvironment projectEnvironment, LanguageVersionSettings languageVersionSettings, AbstractProjectFileSearchScope javaSourcesScope, AbstractProjectFileSearchScope librariesScope, LookupTracker lookupTracker, EnumWhenTracker enumWhenTracker, ImportTracker importTracker, IncrementalCompilationContext incrementalCompilationContext, List<? extends FirExtensionRegistrar> extensionRegistrars, boolean needRegisterJavaElementFinder, Function1<? super DependencyListForCliModule.Builder, Unit> dependenciesConfigurator, Function1<? super FirSessionConfigurator, Unit> sessionConfigurator) {
        Intrinsics.checkNotNullParameter(moduleName, "moduleName");
        Intrinsics.checkNotNullParameter(platform, "platform");
        Intrinsics.checkNotNullParameter(analyzerServices, "analyzerServices");
        Intrinsics.checkNotNullParameter(projectEnvironment, "projectEnvironment");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(javaSourcesScope, "javaSourcesScope");
        Intrinsics.checkNotNullParameter(librariesScope, "librariesScope");
        Intrinsics.checkNotNullParameter(extensionRegistrars, "extensionRegistrars");
        Intrinsics.checkNotNullParameter(dependenciesConfigurator, "dependenciesConfigurator");
        Intrinsics.checkNotNullParameter(sessionConfigurator, "sessionConfigurator");
        BinaryModuleData binaryModuleDataInitialize = BinaryModuleData.INSTANCE.initialize(moduleName, platform, analyzerServices);
        DependencyListForCliModule.Companion companion = DependencyListForCliModule.INSTANCE;
        DependencyListForCliModule.Builder builder = new DependencyListForCliModule.Builder(binaryModuleDataInitialize);
        dependenciesConfigurator.invoke(builder);
        DependencyListForCliModule dependencyListForCliModuleBuild = builder.build();
        FirProjectSessionProvider firProjectSessionProvider = externalSessionProvider == null ? new FirProjectSessionProvider() : externalSessionProvider;
        FirJvmSessionFactory.INSTANCE.createLibrarySession(moduleName, firProjectSessionProvider, dependencyListForCliModuleBuild.getModuleDataProvider(), projectEnvironment, extensionRegistrars, librariesScope, projectEnvironment.getPackagePartProvider(librariesScope), languageVersionSettings, AnonymousClass3.INSTANCE);
        return FirJvmSessionFactory.INSTANCE.createModuleBasedSession(new FirModuleDataImpl(moduleName, dependencyListForCliModuleBuild.getRegularDependencies(), dependencyListForCliModuleBuild.getDependsOnDependencies(), dependencyListForCliModuleBuild.getFriendsDependencies(), platform, analyzerServices, null, 64, null), firProjectSessionProvider, javaSourcesScope, projectEnvironment, incrementalCompilationContext, extensionRegistrars, languageVersionSettings, lookupTracker, enumWhenTracker, importTracker, needRegisterJavaElementFinder, AnonymousClass4.INSTANCE, sessionConfigurator);
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.fir.session.FirSessionFactoryHelper$createSessionWithDependencies$3, reason: invalid class name */
    /* JADX INFO: compiled from: FirSessionFactoryHelper.kt */
    @Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\n¢\u0006\u0002\b\u0004"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "it", "Lorg/jetbrains/kotlin/fir/FirSession;", "invoke"}, k = 3, mv = {1, 9, 0}, xi = 176)
    public static final class AnonymousClass3 extends Lambda implements Function1<FirSession, Unit> {
        public static final AnonymousClass3 INSTANCE = new AnonymousClass3();

        public AnonymousClass3() {
            super(1);
        }

        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(FirSession it) {
            Intrinsics.checkNotNullParameter(it, "it");
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(FirSession firSession) {
            invoke2(firSession);
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.fir.session.FirSessionFactoryHelper$createSessionWithDependencies$4, reason: invalid class name */
    /* JADX INFO: compiled from: FirSessionFactoryHelper.kt */
    @Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\n¢\u0006\u0002\b\u0004"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "it", "Lorg/jetbrains/kotlin/fir/FirSession;", "invoke"}, k = 3, mv = {1, 9, 0}, xi = 176)
    public static final class AnonymousClass4 extends Lambda implements Function1<FirSession, Unit> {
        public static final AnonymousClass4 INSTANCE = new AnonymousClass4();

        public AnonymousClass4() {
            super(1);
        }

        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(FirSession it) {
            Intrinsics.checkNotNullParameter(it, "it");
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(FirSession firSession) {
            invoke2(firSession);
            return Unit.INSTANCE;
        }
    }

    public final FirSession createEmptySession() {
        FirSession firSession = new FirSession(FirSession.Kind.Source) { // from class: org.jetbrains.kotlin.fir.session.FirSessionFactoryHelper.createEmptySession.1
        };
        Name nameIdentifier = Name.identifier("<stub module>");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        FirModuleDataImpl firModuleDataImpl = new FirModuleDataImpl(nameIdentifier, CollectionsKt.emptyList(), CollectionsKt.emptyList(), CollectionsKt.emptyList(), JvmPlatforms.INSTANCE.getUnspecifiedJvmPlatform(), JvmPlatformAnalyzerServices.INSTANCE, null, 64, null);
        FirSession firSession2 = firSession;
        ComponentsContainersKt.registerModuleData(firSession2, firModuleDataImpl);
        firModuleDataImpl.bindSession(firSession2);
        firSession.register(Reflection.getOrCreateKotlinClass(FirLanguageSettingsComponent.class), new FirLanguageSettingsComponent(new LanguageVersionSettings() { // from class: org.jetbrains.kotlin.fir.session.FirSessionFactoryHelper$createEmptySession$2$1
            private final Void stub() {
                throw new NotImplementedError("An operation is not implemented: It does not yet have well-defined semantics for tests.If you're seeing this, implement it in a test-specific way");
            }

            @Override // org.jetbrains.kotlin.config.LanguageVersionSettings
            public LanguageFeature.State getFeatureSupport(LanguageFeature feature) {
                Intrinsics.checkNotNullParameter(feature, "feature");
                return LanguageFeature.State.DISABLED;
            }

            @Override // org.jetbrains.kotlin.config.LanguageVersionSettings
            public boolean isPreRelease() {
                stub();
                throw null;
            }

            @Override // org.jetbrains.kotlin.config.LanguageVersionSettings
            public <T> T getFlag(AnalysisFlag<? extends T> flag) {
                Intrinsics.checkNotNullParameter(flag, "flag");
                stub();
                throw null;
            }

            @Override // org.jetbrains.kotlin.config.LanguageVersionSettings
            public ApiVersion getApiVersion() {
                stub();
                throw null;
            }

            @Override // org.jetbrains.kotlin.config.LanguageVersionSettings
            public LanguageVersion getLanguageVersion() {
                stub();
                throw null;
            }
        }));
        firSession.register(Reflection.getOrCreateKotlinClass(FirExtensionService.class), new FirExtensionService(firSession2));
        return firSession2;
    }
}
