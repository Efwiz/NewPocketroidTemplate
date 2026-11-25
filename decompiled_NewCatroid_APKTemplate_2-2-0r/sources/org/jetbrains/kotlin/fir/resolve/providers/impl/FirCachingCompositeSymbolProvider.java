package org.jetbrains.kotlin.fir.resolve.providers.impl;

import com.android.dx.rop.code.RegisterSpec;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.caches.FirCache;
import org.jetbrains.kotlin.fir.caches.FirCachesFactoryKt;
import org.jetbrains.kotlin.fir.resolve.providers.FirCompositeCachedSymbolNamesProvider;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolNamesProvider;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProviderInternals;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.CallableId;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FirCachingCompositeSymbolProvider.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0080\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\b\u0002\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0016\u0010\u001d\u001a\b\u0012\u0002\b\u0003\u0018\u00010\f2\u0006\u0010\u001e\u001a\u00020\u000bH\u0002J\u0012\u0010\u001f\u001a\u0004\u0018\u00010\u000f2\u0006\u0010 \u001a\u00020\u000fH\u0002J\u001a\u0010!\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00180\u00052\u0006\u0010\"\u001a\u00020\u0017H\u0002J\u0016\u0010#\u001a\b\u0012\u0004\u0012\u00020\u001a0\u00052\u0006\u0010\"\u001a\u00020\u0017H\u0002J\u0016\u0010$\u001a\b\u0012\u0004\u0012\u00020\u001c0\u00052\u0006\u0010\"\u001a\u00020\u0017H\u0002J\u0006\u0010%\u001a\u00020\u0000J!\u0010&\u001a\u00020'2\b\u0010(\u001a\u0004\u0018\u00010)2\f\u0010*\u001a\b\u0012\u0004\u0012\u00020,0+H\u0082\bJ\u0016\u0010-\u001a\b\u0012\u0002\b\u0003\u0018\u00010\f2\u0006\u0010\u001e\u001a\u00020\u000bH\u0016J\u0012\u0010.\u001a\u0004\u0018\u00010\u000f2\u0006\u0010/\u001a\u00020\u000fH\u0016J\"\u00100\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00180\u00052\u0006\u00101\u001a\u00020\u000f2\u0006\u00102\u001a\u000203H\u0016J*\u00104\u001a\u00020'2\u0010\u00105\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0018062\u0006\u00101\u001a\u00020\u000f2\u0006\u00102\u001a\u000203H\u0017J&\u00107\u001a\u00020'2\f\u00105\u001a\b\u0012\u0004\u0012\u00020\u001a062\u0006\u00101\u001a\u00020\u000f2\u0006\u00102\u001a\u000203H\u0017J&\u00108\u001a\u00020'2\f\u00105\u001a\b\u0012\u0004\u0012\u00020\u001c062\u0006\u00101\u001a\u00020\u000f2\u0006\u00102\u001a\u000203H\u0017R(\u0010\t\u001a\u001c\u0012\u0004\u0012\u00020\u000b\u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010\f\u0012\u0006\u0012\u0004\u0018\u00010\r0\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R$\u0010\u000e\u001a\u0018\u0012\u0004\u0012\u00020\u000f\u0012\u0006\u0012\u0004\u0018\u00010\u000f\u0012\u0006\u0012\u0004\u0018\u00010\r0\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0012\u001a\u00020\u0013X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R,\u0010\u0016\u001a \u0012\u0004\u0012\u00020\u0017\u0012\u000e\u0012\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00180\u0005\u0012\u0006\u0012\u0004\u0018\u00010\r0\nX\u0082\u0004¢\u0006\u0002\n\u0000R(\u0010\u0019\u001a\u001c\u0012\u0004\u0012\u00020\u0017\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001a0\u0005\u0012\u0006\u0012\u0004\u0018\u00010\r0\nX\u0082\u0004¢\u0006\u0002\n\u0000R(\u0010\u001b\u001a\u001c\u0012\u0004\u0012\u00020\u0017\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001c0\u0005\u0012\u0006\u0012\u0004\u0018\u00010\r0\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0004"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/providers/impl/FirCachingCompositeSymbolProvider;", "Lorg/jetbrains/kotlin/fir/resolve/providers/FirSymbolProvider;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "providers", "", "expectedCachesToBeCleanedOnce", "", "(Lorg/jetbrains/kotlin/fir/FirSession;Ljava/util/List;Z)V", "classLikeCache", "Lorg/jetbrains/kotlin/fir/caches/FirCache;", "Lorg/jetbrains/kotlin/name/ClassId;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;", "", "packageCache", "Lorg/jetbrains/kotlin/name/FqName;", "getProviders", "()Ljava/util/List;", "symbolNamesProvider", "Lorg/jetbrains/kotlin/fir/resolve/providers/FirSymbolNamesProvider;", "getSymbolNamesProvider", "()Lorg/jetbrains/kotlin/fir/resolve/providers/FirSymbolNamesProvider;", "topLevelCallableCache", "Lorg/jetbrains/kotlin/name/CallableId;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "topLevelFunctionCache", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;", "topLevelPropertyCache", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertySymbol;", "computeClass", "classId", "computePackage", "it", "computeTopLevelCallables", "callableId", "computeTopLevelFunctions", "computeTopLevelProperties", "createCopyWithCleanCaches", Namer.NULL_CHECK_INTRINSIC_NAME, "", RegisterSpec.PREFIX, "", "representation", "Lkotlin/Function0;", "", "getClassLikeSymbolByClassId", "getPackage", "fqName", "getTopLevelCallableSymbols", "packageFqName", "name", "Lorg/jetbrains/kotlin/name/Name;", "getTopLevelCallableSymbolsTo", FirebaseAnalytics.Param.DESTINATION, "", "getTopLevelFunctionSymbolsTo", "getTopLevelPropertySymbolsTo"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirCachingCompositeSymbolProvider extends FirSymbolProvider {
    private final FirCache classLikeCache;
    private final boolean expectedCachesToBeCleanedOnce;
    private final FirCache packageCache;
    private final List<FirSymbolProvider> providers;
    private final FirSymbolNamesProvider symbolNamesProvider;
    private final FirCache topLevelCallableCache;
    private final FirCache topLevelFunctionCache;
    private final FirCache topLevelPropertyCache;

    public /* synthetic */ FirCachingCompositeSymbolProvider(FirSession firSession, List list, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(firSession, list, (i & 4) != 0 ? false : z);
    }

    public final List<FirSymbolProvider> getProviders() {
        return this.providers;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public FirCachingCompositeSymbolProvider(final FirSession session, List<? extends FirSymbolProvider> providers, boolean z) {
        super(session);
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(providers, "providers");
        this.providers = providers;
        this.expectedCachesToBeCleanedOnce = z;
        this.classLikeCache = FirCachesFactoryKt.getFirCachesFactory(session).createCache(new Function2() { // from class: org.jetbrains.kotlin.fir.resolve.providers.impl.FirCachingCompositeSymbolProvider$special$$inlined$createCache$1
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
                return invoke((ClassId) obj, (Void) obj2);
            }

            public final FirClassLikeSymbol<?> invoke(ClassId key, Void r2) {
                Intrinsics.checkNotNullParameter(key, "key");
                return this.$receiver$inlined.computeClass(key);
            }
        });
        this.topLevelCallableCache = FirCachesFactoryKt.getFirCachesFactory(session).createCache(new Function2() { // from class: org.jetbrains.kotlin.fir.resolve.providers.impl.FirCachingCompositeSymbolProvider$special$$inlined$createCache$2
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
                return invoke((CallableId) obj, (Void) obj2);
            }

            public final List<? extends FirCallableSymbol<?>> invoke(CallableId key, Void r2) {
                Intrinsics.checkNotNullParameter(key, "key");
                return this.$receiver$inlined.computeTopLevelCallables(key);
            }
        });
        this.topLevelFunctionCache = FirCachesFactoryKt.getFirCachesFactory(session).createCache(new Function2() { // from class: org.jetbrains.kotlin.fir.resolve.providers.impl.FirCachingCompositeSymbolProvider$special$$inlined$createCache$3
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
                return invoke((CallableId) obj, (Void) obj2);
            }

            public final List<? extends FirNamedFunctionSymbol> invoke(CallableId key, Void r2) {
                Intrinsics.checkNotNullParameter(key, "key");
                return this.$receiver$inlined.computeTopLevelFunctions(key);
            }
        });
        this.topLevelPropertyCache = FirCachesFactoryKt.getFirCachesFactory(session).createCache(new Function2() { // from class: org.jetbrains.kotlin.fir.resolve.providers.impl.FirCachingCompositeSymbolProvider$special$$inlined$createCache$4
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
                return invoke((CallableId) obj, (Void) obj2);
            }

            public final List<? extends FirPropertySymbol> invoke(CallableId key, Void r2) {
                Intrinsics.checkNotNullParameter(key, "key");
                return this.$receiver$inlined.computeTopLevelProperties(key);
            }
        });
        this.packageCache = FirCachesFactoryKt.getFirCachesFactory(session).createCache(new Function2() { // from class: org.jetbrains.kotlin.fir.resolve.providers.impl.FirCachingCompositeSymbolProvider$special$$inlined$createCache$5
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
                return invoke((FqName) obj, (Void) obj2);
            }

            public final FqName invoke(FqName key, Void r2) {
                Intrinsics.checkNotNullParameter(key, "key");
                return this.$receiver$inlined.computePackage(key);
            }
        });
        List<? extends FirSymbolProvider> list = providers;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(((FirSymbolProvider) it.next()).getSymbolNamesProvider());
        }
        final ArrayList arrayList2 = arrayList;
        this.symbolNamesProvider = new FirCompositeCachedSymbolNamesProvider(this, arrayList2) { // from class: org.jetbrains.kotlin.fir.resolve.providers.impl.FirCachingCompositeSymbolProvider$symbolNamesProvider$1
            final /* synthetic */ FirCachingCompositeSymbolProvider this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(this.$session, arrayList2);
                this.this$0 = this;
            }

            @Override // org.jetbrains.kotlin.fir.resolve.providers.FirCompositeCachedSymbolNamesProvider, org.jetbrains.kotlin.fir.resolve.providers.FirCachedSymbolNamesProvider
            public Set<String> computeTopLevelClassifierNames(FqName packageFqName) {
                Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
                Set<String> setComputeTopLevelClassifierNames = super.computeTopLevelClassifierNames(packageFqName);
                FirCachingCompositeSymbolProvider firCachingCompositeSymbolProvider = this.this$0;
                if (setComputeTopLevelClassifierNames != null || firCachingCompositeSymbolProvider.expectedCachesToBeCleanedOnce) {
                    return setComputeTopLevelClassifierNames;
                }
                throw new IllegalArgumentException((("classifier names in package " + packageFqName) + " is expected to be not null in CLI").toString());
            }

            @Override // org.jetbrains.kotlin.fir.resolve.providers.FirCompositeCachedSymbolNamesProvider, org.jetbrains.kotlin.fir.resolve.providers.FirCachedSymbolNamesProvider
            public Set<String> computePackageNamesWithTopLevelCallables() {
                Set<String> setComputePackageNamesWithTopLevelCallables = super.computePackageNamesWithTopLevelCallables();
                FirCachingCompositeSymbolProvider firCachingCompositeSymbolProvider = this.this$0;
                if (setComputePackageNamesWithTopLevelCallables != null || firCachingCompositeSymbolProvider.expectedCachesToBeCleanedOnce) {
                    return setComputePackageNamesWithTopLevelCallables;
                }
                throw new IllegalArgumentException("package names with top-level callables is expected to be not null in CLI".toString());
            }

            @Override // org.jetbrains.kotlin.fir.resolve.providers.FirCompositeCachedSymbolNamesProvider, org.jetbrains.kotlin.fir.resolve.providers.FirCachedSymbolNamesProvider
            public Set<Name> computeTopLevelCallableNames(FqName packageFqName) {
                Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
                Set<Name> setComputeTopLevelCallableNames = super.computeTopLevelCallableNames(packageFqName);
                FirCachingCompositeSymbolProvider firCachingCompositeSymbolProvider = this.this$0;
                if (setComputeTopLevelCallableNames != null || firCachingCompositeSymbolProvider.expectedCachesToBeCleanedOnce) {
                    return setComputeTopLevelCallableNames;
                }
                throw new IllegalArgumentException((("callable names in package " + packageFqName) + " is expected to be not null in CLI").toString());
            }

            @Override // org.jetbrains.kotlin.fir.resolve.providers.FirCompositeCachedSymbolNamesProvider, org.jetbrains.kotlin.fir.resolve.providers.FirSymbolNamesProvider
            public boolean mayHaveSyntheticFunctionType(ClassId classId) {
                Intrinsics.checkNotNullParameter(classId, "classId");
                return FirSyntheticFunctionInterfaceProviderBase.INSTANCE.isNameForFunctionClass(classId, this.$session);
            }
        };
    }

    @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider
    public FirSymbolNamesProvider getSymbolNamesProvider() {
        return this.symbolNamesProvider;
    }

    public final FirCachingCompositeSymbolProvider createCopyWithCleanCaches() {
        if (!this.expectedCachesToBeCleanedOnce) {
            throw new IllegalArgumentException("Unexpected caches clearing".toString());
        }
        return new FirCachingCompositeSymbolProvider(getSession(), this.providers, false);
    }

    @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider
    public List<FirCallableSymbol<?>> getTopLevelCallableSymbols(FqName packageFqName, Name name2) {
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        Intrinsics.checkNotNullParameter(name2, "name");
        return !getSymbolNamesProvider().mayHaveTopLevelCallable(packageFqName, name2) ? CollectionsKt.emptyList() : (List) this.topLevelCallableCache.getValue(new CallableId(packageFqName, name2), null);
    }

    @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider
    @FirSymbolProviderInternals
    public void getTopLevelCallableSymbolsTo(List<FirCallableSymbol<?>> destination, FqName packageFqName, Name name2) {
        Intrinsics.checkNotNullParameter(destination, "destination");
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        Intrinsics.checkNotNullParameter(name2, "name");
        CollectionsKt.addAll(destination, getTopLevelCallableSymbols(packageFqName, name2));
    }

    @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider
    @FirSymbolProviderInternals
    public void getTopLevelFunctionSymbolsTo(List<FirNamedFunctionSymbol> destination, FqName packageFqName, Name name2) {
        Intrinsics.checkNotNullParameter(destination, "destination");
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        Intrinsics.checkNotNullParameter(name2, "name");
        if (getSymbolNamesProvider().mayHaveTopLevelCallable(packageFqName, name2)) {
            CollectionsKt.addAll(destination, (Iterable) this.topLevelFunctionCache.getValue(new CallableId(packageFqName, name2), null));
        }
    }

    @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider
    @FirSymbolProviderInternals
    public void getTopLevelPropertySymbolsTo(List<FirPropertySymbol> destination, FqName packageFqName, Name name2) {
        Intrinsics.checkNotNullParameter(destination, "destination");
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        Intrinsics.checkNotNullParameter(name2, "name");
        if (getSymbolNamesProvider().mayHaveTopLevelCallable(packageFqName, name2)) {
            CollectionsKt.addAll(destination, (Iterable) this.topLevelPropertyCache.getValue(new CallableId(packageFqName, name2), null));
        }
    }

    @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider
    public FqName getPackage(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        return (FqName) this.packageCache.getValue(fqName, null);
    }

    @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider
    public FirClassLikeSymbol<?> getClassLikeSymbolByClassId(ClassId classId) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        if (getSymbolNamesProvider().mayHaveTopLevelClassifier(classId)) {
            return (FirClassLikeSymbol) this.classLikeCache.getValue(classId, null);
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final List<FirCallableSymbol<?>> computeTopLevelCallables(CallableId callableId) {
        List<FirCallableSymbol<?>> listCreateListBuilder = CollectionsKt.createListBuilder();
        Iterator<T> it = this.providers.iterator();
        while (it.hasNext()) {
            ((FirSymbolProvider) it.next()).getTopLevelCallableSymbolsTo(listCreateListBuilder, callableId.getPackageName(), callableId.getCallableName());
        }
        return CollectionsKt.build(listCreateListBuilder);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final List<FirNamedFunctionSymbol> computeTopLevelFunctions(CallableId callableId) {
        List<FirNamedFunctionSymbol> listCreateListBuilder = CollectionsKt.createListBuilder();
        Iterator<T> it = this.providers.iterator();
        while (it.hasNext()) {
            ((FirSymbolProvider) it.next()).getTopLevelFunctionSymbolsTo(listCreateListBuilder, callableId.getPackageName(), callableId.getCallableName());
        }
        return CollectionsKt.build(listCreateListBuilder);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final List<FirPropertySymbol> computeTopLevelProperties(CallableId callableId) {
        List<FirPropertySymbol> listCreateListBuilder = CollectionsKt.createListBuilder();
        Iterator<T> it = this.providers.iterator();
        while (it.hasNext()) {
            ((FirSymbolProvider) it.next()).getTopLevelPropertySymbolsTo(listCreateListBuilder, callableId.getPackageName(), callableId.getCallableName());
        }
        return CollectionsKt.build(listCreateListBuilder);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final FqName computePackage(FqName it) {
        Iterator<T> it2 = this.providers.iterator();
        while (it2.hasNext()) {
            FqName fqName = ((FirSymbolProvider) it2.next()).getPackage(it);
            if (fqName != null) {
                return fqName;
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final FirClassLikeSymbol<?> computeClass(ClassId classId) {
        Iterator<T> it = this.providers.iterator();
        while (it.hasNext()) {
            FirClassLikeSymbol<?> classLikeSymbolByClassId = ((FirSymbolProvider) it.next()).getClassLikeSymbolByClassId(classId);
            if (classLikeSymbolByClassId != null) {
                return classLikeSymbolByClassId;
            }
        }
        return null;
    }
}
