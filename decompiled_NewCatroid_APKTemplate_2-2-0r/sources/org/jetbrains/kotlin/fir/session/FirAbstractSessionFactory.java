package org.jetbrains.kotlin.fir.session;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.functions.Function5;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.fir.BinaryModuleData;
import org.jetbrains.kotlin.fir.FirModuleData;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.FirSessionProvider;
import org.jetbrains.kotlin.fir.FirVisibilityCheckerKt;
import org.jetbrains.kotlin.fir.checkers.CheckersContainersKt;
import org.jetbrains.kotlin.fir.deserialization.ModuleDataProvider;
import org.jetbrains.kotlin.fir.extensions.FirExtensionRegistrar;
import org.jetbrains.kotlin.fir.extensions.FirSwitchableExtensionDeclarationsSymbolProvider;
import org.jetbrains.kotlin.fir.java.FirCliSession;
import org.jetbrains.kotlin.fir.java.FirProjectSessionProvider;
import org.jetbrains.kotlin.fir.resolve.providers.FirProvider;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProviderKt;
import org.jetbrains.kotlin.fir.resolve.providers.impl.FirCachingCompositeSymbolProvider;
import org.jetbrains.kotlin.fir.resolve.providers.impl.FirExtensionSyntheticFunctionInterfaceProvider;
import org.jetbrains.kotlin.fir.resolve.providers.impl.FirLibrarySessionProvider;
import org.jetbrains.kotlin.fir.resolve.providers.impl.FirProviderImpl;
import org.jetbrains.kotlin.fir.scopes.FirKotlinScopeProvider;
import org.jetbrains.kotlin.incremental.components.EnumWhenTracker;
import org.jetbrains.kotlin.incremental.components.ImportTracker;
import org.jetbrains.kotlin.incremental.components.LookupTracker;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FirAbstractSessionFactory.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0094\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010#\n\u0000\n\u0002\u0010\u000b\n\u0000\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0086\u0001\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e2\u0012\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00120\u00112\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u00142,\u0010\u0016\u001a(\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u0015\u0012\u0006\u0012\u0004\u0018\u00010\u0019\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001a0\u000e0\u0017H\u0004Jæ\u0001\u0010\u001b\u001a\u00020\u00042\u0006\u0010\u001c\u001a\u00020\u00182\u0006\u0010\u0007\u001a\u00020\b2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e2\u0006\u0010\u000b\u001a\u00020\f2\b\u0010\u001d\u001a\u0004\u0018\u00010\u001e2\b\u0010\u001f\u001a\u0004\u0018\u00010 2\b\u0010!\u001a\u0004\u0018\u00010\"2\u0017\u0010#\u001a\u0013\u0012\u0004\u0012\u00020$\u0012\u0004\u0012\u00020\u00120\u0011¢\u0006\u0002\b%2\u0012\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00120\u00112\u0014\u0010&\u001a\u0010\u0012\u0004\u0012\u00020$\u0012\u0004\u0012\u00020\u0012\u0018\u00010\u00112\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u00142G\u0010\u0016\u001aC\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u001a\u0012\u0006\u0012\u0004\u0018\u00010(\u0012\u0019\u0012\u0017\u0012\u0004\u0012\u00020\u001a0\u000e¢\u0006\f\b)\u0012\b\b*\u0012\u0004\b\b(+\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001a0\u000e0'H\u0004J\u001a\u0010,\u001a\b\u0012\u0004\u0012\u00020\u001a0\u000e*\u00020\u00042\u0006\u0010\u001c\u001a\u00020\u0018H\u0002J(\u0010-\u001a\b\u0012\u0004\u0012\u00020\u001a0\u000e*\u00020\u001a2\f\u0010.\u001a\b\u0012\u0004\u0012\u00020\u001a0/2\u0006\u00100\u001a\u000201H\u0002¨\u00062"}, d2 = {"Lorg/jetbrains/kotlin/fir/session/FirAbstractSessionFactory;", "", "()V", "createLibrarySession", "Lorg/jetbrains/kotlin/fir/FirSession;", "mainModuleName", "Lorg/jetbrains/kotlin/name/Name;", "sessionProvider", "Lorg/jetbrains/kotlin/fir/java/FirProjectSessionProvider;", "moduleDataProvider", "Lorg/jetbrains/kotlin/fir/deserialization/ModuleDataProvider;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "extensionRegistrars", "", "Lorg/jetbrains/kotlin/fir/extensions/FirExtensionRegistrar;", "registerExtraComponents", "Lkotlin/Function1;", "", "createKotlinScopeProvider", "Lkotlin/Function0;", "Lorg/jetbrains/kotlin/fir/scopes/FirKotlinScopeProvider;", "createProviders", "Lkotlin/Function4;", "Lorg/jetbrains/kotlin/fir/FirModuleData;", "Lorg/jetbrains/kotlin/fir/resolve/providers/impl/FirExtensionSyntheticFunctionInterfaceProvider;", "Lorg/jetbrains/kotlin/fir/resolve/providers/FirSymbolProvider;", "createModuleBasedSession", "moduleData", "lookupTracker", "Lorg/jetbrains/kotlin/incremental/components/LookupTracker;", "enumWhenTracker", "Lorg/jetbrains/kotlin/incremental/components/EnumWhenTracker;", "importTracker", "Lorg/jetbrains/kotlin/incremental/components/ImportTracker;", ConfigConstants.CONFIG_INIT_SECTION, "Lorg/jetbrains/kotlin/fir/session/FirSessionConfigurator;", "Lkotlin/ExtensionFunctionType;", "registerExtraCheckers", "Lkotlin/Function5;", "Lorg/jetbrains/kotlin/fir/extensions/FirSwitchableExtensionDeclarationsSymbolProvider;", "Lkotlin/ParameterName;", "name", "dependencies", "computeDependencyProviderList", "flatten", "visited", "", "collectSourceProviders", "", "entrypoint"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirAbstractSessionFactory {
    protected final FirSession createLibrarySession(Name mainModuleName, FirProjectSessionProvider sessionProvider, ModuleDataProvider moduleDataProvider, LanguageVersionSettings languageVersionSettings, List<? extends FirExtensionRegistrar> extensionRegistrars, Function1<? super FirSession, Unit> registerExtraComponents, Function0<FirKotlinScopeProvider> createKotlinScopeProvider, Function4<? super FirSession, ? super FirModuleData, ? super FirKotlinScopeProvider, ? super FirExtensionSyntheticFunctionInterfaceProvider, ? extends List<? extends FirSymbolProvider>> createProviders) {
        Intrinsics.checkNotNullParameter(mainModuleName, "mainModuleName");
        Intrinsics.checkNotNullParameter(sessionProvider, "sessionProvider");
        Intrinsics.checkNotNullParameter(moduleDataProvider, "moduleDataProvider");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(extensionRegistrars, "extensionRegistrars");
        Intrinsics.checkNotNullParameter(registerExtraComponents, "registerExtraComponents");
        Intrinsics.checkNotNullParameter(createKotlinScopeProvider, "createKotlinScopeProvider");
        Intrinsics.checkNotNullParameter(createProviders, "createProviders");
        FirCliSession firCliSession = new FirCliSession(sessionProvider, FirSession.Kind.Library);
        for (FirModuleData firModuleData : moduleDataProvider.getAllModuleData()) {
            FirCliSession firCliSession2 = firCliSession;
            sessionProvider.registerSession(firModuleData, firCliSession2);
            firModuleData.bindSession(firCliSession2);
        }
        FirCliSession firCliSession3 = firCliSession;
        ComponentsContainersKt.registerCliCompilerOnlyComponents(firCliSession3);
        ComponentsContainersKt.registerCommonComponents(firCliSession3, languageVersionSettings);
        registerExtraComponents.invoke(firCliSession);
        FirKotlinScopeProvider firKotlinScopeProviderInvoke = createKotlinScopeProvider.invoke();
        firCliSession.register(Reflection.getOrCreateKotlinClass(FirKotlinScopeProvider.class), firKotlinScopeProviderInvoke);
        BinaryModuleData.Companion companion = BinaryModuleData.INSTANCE;
        Name nameSpecial = Name.special("<builtins of " + mainModuleName.asString());
        Intrinsics.checkNotNullExpressionValue(nameSpecial, "special(...)");
        FirModuleData firModuleDataCreateDependencyModuleData$default = BinaryModuleData.Companion.createDependencyModuleData$default(companion, nameSpecial, moduleDataProvider.getPlatform(), moduleDataProvider.getAnalyzerServices(), null, 8, null);
        firModuleDataCreateDependencyModuleData$default.bindSession(firCliSession3);
        FirSessionConfigurator firSessionConfigurator = new FirSessionConfigurator(firCliSession3);
        Iterator<? extends FirExtensionRegistrar> it = extensionRegistrars.iterator();
        while (it.hasNext()) {
            firSessionConfigurator.registerExtensions(it.next().configure());
        }
        firSessionConfigurator.configure();
        ComponentsContainersKt.registerCommonComponentsAfterExtensionsAreConfigured(firCliSession3);
        FirCachingCompositeSymbolProvider firCachingCompositeSymbolProvider = new FirCachingCompositeSymbolProvider(firCliSession3, createProviders.invoke(firCliSession, firModuleDataCreateDependencyModuleData$default, firKotlinScopeProviderInvoke, FirExtensionSyntheticFunctionInterfaceProvider.INSTANCE.createIfNeeded(firCliSession3, firModuleDataCreateDependencyModuleData$default, firKotlinScopeProviderInvoke)), false, 4, null);
        firCliSession.register(Reflection.getOrCreateKotlinClass(FirSymbolProvider.class), firCachingCompositeSymbolProvider);
        firCliSession.register(Reflection.getOrCreateKotlinClass(FirProvider.class), new FirLibrarySessionProvider(firCachingCompositeSymbolProvider));
        return firCliSession3;
    }

    protected final FirSession createModuleBasedSession(FirModuleData moduleData, FirProjectSessionProvider sessionProvider, List<? extends FirExtensionRegistrar> extensionRegistrars, LanguageVersionSettings languageVersionSettings, LookupTracker lookupTracker, EnumWhenTracker enumWhenTracker, ImportTracker importTracker, Function1<? super FirSessionConfigurator, Unit> init, Function1<? super FirSession, Unit> registerExtraComponents, Function1<? super FirSessionConfigurator, Unit> registerExtraCheckers, Function0<FirKotlinScopeProvider> createKotlinScopeProvider, Function5<? super FirSession, ? super FirKotlinScopeProvider, ? super FirSymbolProvider, ? super FirSwitchableExtensionDeclarationsSymbolProvider, ? super List<? extends FirSymbolProvider>, ? extends List<? extends FirSymbolProvider>> createProviders) {
        Intrinsics.checkNotNullParameter(moduleData, "moduleData");
        Intrinsics.checkNotNullParameter(sessionProvider, "sessionProvider");
        Intrinsics.checkNotNullParameter(extensionRegistrars, "extensionRegistrars");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(init, "init");
        Intrinsics.checkNotNullParameter(registerExtraComponents, "registerExtraComponents");
        Intrinsics.checkNotNullParameter(createKotlinScopeProvider, "createKotlinScopeProvider");
        Intrinsics.checkNotNullParameter(createProviders, "createProviders");
        FirCliSession firCliSession = new FirCliSession(sessionProvider, FirSession.Kind.Source);
        FirCliSession firCliSession2 = firCliSession;
        moduleData.bindSession(firCliSession2);
        sessionProvider.registerSession(moduleData, firCliSession2);
        ComponentsContainersKt.registerModuleData(firCliSession2, moduleData);
        ComponentsContainersKt.registerCliCompilerOnlyComponents(firCliSession2);
        ComponentsContainersKt.registerCommonComponents(firCliSession2, languageVersionSettings);
        ComponentsContainersKt.registerResolveComponents(firCliSession2, lookupTracker, enumWhenTracker, importTracker);
        registerExtraComponents.invoke(firCliSession);
        FirKotlinScopeProvider firKotlinScopeProviderInvoke = createKotlinScopeProvider.invoke();
        firCliSession.register(Reflection.getOrCreateKotlinClass(FirKotlinScopeProvider.class), firKotlinScopeProviderInvoke);
        FirProviderImpl firProviderImpl = new FirProviderImpl(firCliSession2, firKotlinScopeProviderInvoke);
        firCliSession.register(Reflection.getOrCreateKotlinClass(FirProvider.class), firProviderImpl);
        FirSessionConfigurator firSessionConfigurator = new FirSessionConfigurator(firCliSession2);
        CheckersContainersKt.registerCommonCheckers(firSessionConfigurator);
        if (registerExtraCheckers != null) {
            registerExtraCheckers.invoke(firSessionConfigurator);
        }
        Iterator<? extends FirExtensionRegistrar> it = extensionRegistrars.iterator();
        while (it.hasNext()) {
            firSessionConfigurator.registerExtensions(it.next().configure());
        }
        init.invoke(firSessionConfigurator);
        firSessionConfigurator.configure();
        ComponentsContainersKt.registerCommonComponentsAfterExtensionsAreConfigured(firCliSession2);
        List<FirSymbolProvider> listComputeDependencyProviderList = computeDependencyProviderList(firCliSession2, moduleData);
        FirSwitchableExtensionDeclarationsSymbolProvider firSwitchableExtensionDeclarationsSymbolProviderCreateIfNeeded = FirSwitchableExtensionDeclarationsSymbolProvider.INSTANCE.createIfNeeded(firCliSession2);
        firCliSession.register(Reflection.getOrCreateKotlinClass(FirSymbolProvider.class), new FirCachingCompositeSymbolProvider(firCliSession2, createProviders.invoke(firCliSession, firKotlinScopeProviderInvoke, firProviderImpl.getSymbolProvider(), firSwitchableExtensionDeclarationsSymbolProviderCreateIfNeeded, listComputeDependencyProviderList), firSwitchableExtensionDeclarationsSymbolProviderCreateIfNeeded != null));
        if (firSwitchableExtensionDeclarationsSymbolProviderCreateIfNeeded != null) {
            firCliSession.register(Reflection.getOrCreateKotlinClass(FirSwitchableExtensionDeclarationsSymbolProvider.class), firSwitchableExtensionDeclarationsSymbolProviderCreateIfNeeded);
        }
        firCliSession.register(FirSymbolProviderKt.DEPENDENCIES_SYMBOL_PROVIDER_QUALIFIED_KEY, new FirCachingCompositeSymbolProvider(firCliSession2, listComputeDependencyProviderList, false, 4, null));
        return firCliSession2;
    }

    private final List<FirSymbolProvider> computeDependencyProviderList(FirSession firSession, FirModuleData firModuleData) {
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        List<FirModuleData> listPlus = CollectionsKt.plus((Collection) CollectionsKt.plus((Collection) firModuleData.getDependencies(), (Iterable) firModuleData.getFriendDependencies()), (Iterable) FirVisibilityCheckerKt.getAllDependsOnDependencies(firModuleData));
        ArrayList arrayList = new ArrayList();
        for (FirModuleData firModuleData2 : listPlus) {
            FirSessionProvider sessionProvider = firSession.getSessionProvider();
            FirSession session = sessionProvider != null ? sessionProvider.getSession(firModuleData2) : null;
            if (session != null) {
                arrayList.add(session);
            }
        }
        ArrayList arrayList2 = arrayList;
        ArrayList<FirSymbolProvider> arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            arrayList3.add(FirSymbolProviderKt.getSymbolProvider((FirSession) it.next()));
        }
        ArrayList arrayList4 = new ArrayList();
        for (FirSymbolProvider firSymbolProvider : arrayList3) {
            CollectionsKt.addAll(arrayList4, flatten(firSymbolProvider, linkedHashSet, firSymbolProvider.getSession().getKind() == FirSession.Kind.Source));
        }
        return CollectionsKt.sortedWith(arrayList4, new Comparator() { // from class: org.jetbrains.kotlin.fir.session.FirAbstractSessionFactory$computeDependencyProviderList$$inlined$sortedBy$1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.Comparator
            public final int compare(T t, T t2) {
                return ComparisonsKt.compareValues(((FirSymbolProvider) t).getSession().getKind(), ((FirSymbolProvider) t2).getSession().getKind());
            }
        });
    }

    private final List<FirSymbolProvider> flatten(FirSymbolProvider firSymbolProvider, Set<FirSymbolProvider> set, boolean z) {
        ArrayList arrayList = new ArrayList();
        flatten$collectProviders(firSymbolProvider, set, z, arrayList);
        return arrayList;
    }

    private static final void flatten$collectProviders(FirSymbolProvider firSymbolProvider, Set<FirSymbolProvider> set, boolean z, List<FirSymbolProvider> list) {
        if (set.add(firSymbolProvider)) {
            if (firSymbolProvider instanceof FirCachingCompositeSymbolProvider) {
                Iterator<FirSymbolProvider> it = ((FirCachingCompositeSymbolProvider) firSymbolProvider).getProviders().iterator();
                while (it.hasNext()) {
                    flatten$collectProviders(it.next(), set, z, list);
                }
            } else {
                if (!(z && firSymbolProvider.getSession().getKind() == FirSession.Kind.Source) && (z || firSymbolProvider.getSession().getKind() != FirSession.Kind.Library)) {
                    return;
                }
                list.add(firSymbolProvider);
            }
        }
    }
}
