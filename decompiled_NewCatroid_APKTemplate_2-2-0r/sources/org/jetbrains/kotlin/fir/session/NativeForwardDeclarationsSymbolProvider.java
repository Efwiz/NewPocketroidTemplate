package org.jetbrains.kotlin.fir.session;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.EffectiveVisibility;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.Visibilities;
import org.jetbrains.kotlin.fir.FirModuleData;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.caches.FirCache;
import org.jetbrains.kotlin.fir.caches.FirCachesFactoryKt;
import org.jetbrains.kotlin.fir.declarations.DeprecationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.builder.FirRegularClassBuilder;
import org.jetbrains.kotlin.fir.declarations.impl.FirResolvedDeclarationStatusImpl;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.builder.FirAnnotationBuilder;
import org.jetbrains.kotlin.fir.expressions.impl.FirEmptyAnnotationArgumentMapping;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolNamesProvider;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolNamesProviderWithoutCallables;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProviderInternals;
import org.jetbrains.kotlin.fir.scopes.FirKotlinScopeProvider;
import org.jetbrains.kotlin.fir.symbols.impl.ConeClassLikeLookupTagImpl;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.TypeConstructionUtilsKt;
import org.jetbrains.kotlin.fir.types.builder.FirResolvedTypeRefBuilder;
import org.jetbrains.kotlin.library.KotlinLibrary;
import org.jetbrains.kotlin.library.KotlinLibraryKt;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.NativeForwardDeclarationKind;
import org.jetbrains.kotlin.name.NativeStandardInteropNames;

/* JADX INFO: compiled from: NativeForwardDeclarationsSymbolProvider.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0082\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 32\u00020\u0001:\u00013B+\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t¢\u0006\u0002\u0010\u000bJ\u0016\u0010\"\u001a\b\u0012\u0002\b\u0003\u0018\u00010 2\u0006\u0010#\u001a\u00020\u000eH\u0002J\u0016\u0010$\u001a\b\u0012\u0002\b\u0003\u0018\u00010 2\u0006\u0010#\u001a\u00020\u000eH\u0016J\u0012\u0010%\u001a\u0004\u0018\u00010\u00152\u0006\u0010&\u001a\u00020\u0015H\u0016J*\u0010'\u001a\u00020(2\u0010\u0010)\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030+0*2\u0006\u0010,\u001a\u00020\u00152\u0006\u0010-\u001a\u00020.H\u0017J&\u0010/\u001a\u00020(2\f\u0010)\u001a\b\u0012\u0004\u0012\u0002000*2\u0006\u0010,\u001a\u00020\u00152\u0006\u0010-\u001a\u00020.H\u0017J&\u00101\u001a\u00020(2\f\u0010)\u001a\b\u0012\u0004\u0012\u0002020*2\u0006\u0010,\u001a\u00020\u00152\u0006\u0010-\u001a\u00020.H\u0017R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R!\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u000f\u0010\u0010R-\u0010\u0013\u001a\u0014\u0012\u0004\u0012\u00020\u0015\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00160\r0\u00148BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0019\u0010\u0012\u001a\u0004\b\u0017\u0010\u0018R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u001a\u001a\u00020\u001bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR(\u0010\u001e\u001a\u001c\u0012\u0004\u0012\u00020\u000e\u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010 \u0012\u0006\u0012\u0004\u0018\u00010!0\u001fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u00064"}, d2 = {"Lorg/jetbrains/kotlin/fir/session/NativeForwardDeclarationsSymbolProvider;", "Lorg/jetbrains/kotlin/fir/resolve/providers/FirSymbolProvider;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "forwardDeclarationsModuleData", "Lorg/jetbrains/kotlin/fir/FirModuleData;", "kotlinScopeProvider", "Lorg/jetbrains/kotlin/fir/scopes/FirKotlinScopeProvider;", "kotlinLibraries", "", "Lorg/jetbrains/kotlin/library/KotlinLibrary;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/FirModuleData;Lorg/jetbrains/kotlin/fir/scopes/FirKotlinScopeProvider;Ljava/util/Collection;)V", KotlinLibraryKt.KLIB_PROPERTY_INCLUDED_FORWARD_DECLARATIONS, "", "Lorg/jetbrains/kotlin/name/ClassId;", "getIncludedForwardDeclarations", "()Ljava/util/Set;", "includedForwardDeclarations$delegate", "Lkotlin/Lazy;", "includedForwardDeclarationsByPackage", "", "Lorg/jetbrains/kotlin/name/FqName;", "", "getIncludedForwardDeclarationsByPackage", "()Ljava/util/Map;", "includedForwardDeclarationsByPackage$delegate", "symbolNamesProvider", "Lorg/jetbrains/kotlin/fir/resolve/providers/FirSymbolNamesProvider;", "getSymbolNamesProvider", "()Lorg/jetbrains/kotlin/fir/resolve/providers/FirSymbolNamesProvider;", "syntheticForwardDeclarationClassCache", "Lorg/jetbrains/kotlin/fir/caches/FirCache;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;", "", "createSyntheticForwardDeclarationClass", "classId", "getClassLikeSymbolByClassId", "getPackage", "fqName", "getTopLevelCallableSymbolsTo", "", FirebaseAnalytics.Param.DESTINATION, "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "packageFqName", "name", "Lorg/jetbrains/kotlin/name/Name;", "getTopLevelFunctionSymbolsTo", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;", "getTopLevelPropertySymbolsTo", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertySymbol;", "Companion", "entrypoint"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NativeForwardDeclarationsSymbolProvider extends FirSymbolProvider {
    private static final Companion Companion = new Companion(null);
    private static final Set<FqName> validPackages = NativeForwardDeclarationKind.INSTANCE.getPackageFqNameToKind().keySet();
    private final FirModuleData forwardDeclarationsModuleData;

    /* JADX INFO: renamed from: includedForwardDeclarations$delegate, reason: from kotlin metadata */
    private final Lazy includedForwardDeclarations;

    /* JADX INFO: renamed from: includedForwardDeclarationsByPackage$delegate, reason: from kotlin metadata */
    private final Lazy includedForwardDeclarationsByPackage;
    private final Collection<KotlinLibrary> kotlinLibraries;
    private final FirKotlinScopeProvider kotlinScopeProvider;
    private final FirSymbolNamesProvider symbolNamesProvider;
    private final FirCache syntheticForwardDeclarationClassCache;

    @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider
    @FirSymbolProviderInternals
    public void getTopLevelCallableSymbolsTo(List<FirCallableSymbol<?>> destination, FqName packageFqName, Name name2) {
        Intrinsics.checkNotNullParameter(destination, "destination");
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        Intrinsics.checkNotNullParameter(name2, "name");
    }

    @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider
    @FirSymbolProviderInternals
    public void getTopLevelFunctionSymbolsTo(List<FirNamedFunctionSymbol> destination, FqName packageFqName, Name name2) {
        Intrinsics.checkNotNullParameter(destination, "destination");
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        Intrinsics.checkNotNullParameter(name2, "name");
    }

    @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider
    @FirSymbolProviderInternals
    public void getTopLevelPropertySymbolsTo(List<FirPropertySymbol> destination, FqName packageFqName, Name name2) {
        Intrinsics.checkNotNullParameter(destination, "destination");
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        Intrinsics.checkNotNullParameter(name2, "name");
    }

    /* JADX INFO: compiled from: NativeForwardDeclarationsSymbolProvider.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\b\u0082\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/fir/session/NativeForwardDeclarationsSymbolProvider$Companion;", "", "()V", "validPackages", "", "Lorg/jetbrains/kotlin/name/FqName;", "entrypoint"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public NativeForwardDeclarationsSymbolProvider(FirSession session, FirModuleData forwardDeclarationsModuleData, FirKotlinScopeProvider kotlinScopeProvider, Collection<? extends KotlinLibrary> kotlinLibraries) {
        super(session);
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(forwardDeclarationsModuleData, "forwardDeclarationsModuleData");
        Intrinsics.checkNotNullParameter(kotlinScopeProvider, "kotlinScopeProvider");
        Intrinsics.checkNotNullParameter(kotlinLibraries, "kotlinLibraries");
        this.forwardDeclarationsModuleData = forwardDeclarationsModuleData;
        this.kotlinScopeProvider = kotlinScopeProvider;
        this.kotlinLibraries = kotlinLibraries;
        this.includedForwardDeclarations = LazyKt.lazy(new Function0<Set<? extends ClassId>>() { // from class: org.jetbrains.kotlin.fir.session.NativeForwardDeclarationsSymbolProvider$includedForwardDeclarations$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Set<? extends ClassId> invoke() {
                NativeForwardDeclarationsSymbolProvider nativeForwardDeclarationsSymbolProvider = this.this$0;
                Set setCreateSetBuilder = SetsKt.createSetBuilder();
                for (KotlinLibrary kotlinLibrary : nativeForwardDeclarationsSymbolProvider.kotlinLibraries) {
                    if (KotlinLibraryKt.isInterop(kotlinLibrary)) {
                        Iterator<String> it = KotlinLibraryKt.getIncludedForwardDeclarations(kotlinLibrary).iterator();
                        while (it.hasNext()) {
                            ClassId classId = ClassId.topLevel(new FqName(it.next()));
                            Intrinsics.checkNotNullExpressionValue(classId, "topLevel(...)");
                            if (NativeForwardDeclarationsSymbolProvider.validPackages.contains(classId.getPackageFqName())) {
                                setCreateSetBuilder.add(classId);
                            }
                        }
                    }
                }
                return SetsKt.build(setCreateSetBuilder);
            }
        });
        this.includedForwardDeclarationsByPackage = LazyKt.lazy(new Function0<Map<FqName, ? extends Set<String>>>() { // from class: org.jetbrains.kotlin.fir.session.NativeForwardDeclarationsSymbolProvider$includedForwardDeclarationsByPackage$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Map<FqName, ? extends Set<String>> invoke() {
                NativeForwardDeclarationsSymbolProvider nativeForwardDeclarationsSymbolProvider = this.this$0;
                Map mapCreateMapBuilder = MapsKt.createMapBuilder();
                for (ClassId classId : nativeForwardDeclarationsSymbolProvider.getIncludedForwardDeclarations()) {
                    FqName packageFqName = classId.getPackageFqName();
                    Intrinsics.checkNotNullExpressionValue(packageFqName, "getPackageFqName(...)");
                    Object obj = mapCreateMapBuilder.get(packageFqName);
                    if (obj == null) {
                        obj = (Set) new LinkedHashSet();
                        mapCreateMapBuilder.put(packageFqName, obj);
                    }
                    String strAsString = classId.getShortClassName().asString();
                    Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                    ((Set) obj).add(strAsString);
                }
                return MapsKt.build(mapCreateMapBuilder);
            }
        });
        this.syntheticForwardDeclarationClassCache = FirCachesFactoryKt.getFirCachesFactory(session).createCache(new Function2() { // from class: org.jetbrains.kotlin.fir.session.NativeForwardDeclarationsSymbolProvider$special$$inlined$createCache$1
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
                return invoke((ClassId) obj, (Void) obj2);
            }

            public final FirClassLikeSymbol<?> invoke(ClassId key, Void r2) {
                Intrinsics.checkNotNullParameter(key, "key");
                return this.$receiver$inlined.createSyntheticForwardDeclarationClass(key);
            }
        });
        this.symbolNamesProvider = new FirSymbolNamesProviderWithoutCallables() { // from class: org.jetbrains.kotlin.fir.session.NativeForwardDeclarationsSymbolProvider$symbolNamesProvider$1
            @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolNamesProvider
            public Set<String> getTopLevelClassifierNamesInPackage(FqName packageFqName) {
                Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
                Set<String> set = (Set) this.this$0.getIncludedForwardDeclarationsByPackage().get(packageFqName);
                return set == null ? SetsKt.emptySet() : set;
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Set<ClassId> getIncludedForwardDeclarations() {
        return (Set) this.includedForwardDeclarations.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Map<FqName, Set<String>> getIncludedForwardDeclarationsByPackage() {
        return (Map) this.includedForwardDeclarationsByPackage.getValue();
    }

    @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider
    public FirClassLikeSymbol<?> getClassLikeSymbolByClassId(ClassId classId) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        if (getIncludedForwardDeclarations().contains(classId) && !classId.isNestedClass()) {
            return (FirClassLikeSymbol) this.syntheticForwardDeclarationClassCache.getValue(classId, null);
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final FirClassLikeSymbol<?> createSyntheticForwardDeclarationClass(ClassId classId) {
        NativeForwardDeclarationKind nativeForwardDeclarationKind = NativeForwardDeclarationKind.INSTANCE.getPackageFqNameToKind().get(classId.getPackageFqName());
        if (nativeForwardDeclarationKind == null) {
            return null;
        }
        FirRegularClassSymbol firRegularClassSymbol = new FirRegularClassSymbol(classId);
        FirRegularClassBuilder firRegularClassBuilder = new FirRegularClassBuilder();
        firRegularClassBuilder.setModuleData(this.forwardDeclarationsModuleData);
        firRegularClassBuilder.setOrigin(FirDeclarationOrigin.Synthetic.ForwardDeclaration.INSTANCE);
        if (classId.isNestedClass()) {
            throw new IllegalStateException(("Expected top-level class when building forward declaration, got " + classId).toString());
        }
        Name shortClassName = classId.getShortClassName();
        Intrinsics.checkNotNullExpressionValue(shortClassName, "getShortClassName(...)");
        firRegularClassBuilder.setName(shortClassName);
        FirResolvedDeclarationStatusImpl firResolvedDeclarationStatusImpl = new FirResolvedDeclarationStatusImpl(Visibilities.Public.INSTANCE, Modality.FINAL, EffectiveVisibility.Public.INSTANCE);
        firResolvedDeclarationStatusImpl.setExpect(false);
        firResolvedDeclarationStatusImpl.setActual(false);
        firResolvedDeclarationStatusImpl.setCompanion(false);
        firResolvedDeclarationStatusImpl.setInner(false);
        firResolvedDeclarationStatusImpl.setData(false);
        firResolvedDeclarationStatusImpl.setInline(false);
        firResolvedDeclarationStatusImpl.setExternal(false);
        firResolvedDeclarationStatusImpl.setFun(false);
        firRegularClassBuilder.setStatus(firResolvedDeclarationStatusImpl);
        firRegularClassBuilder.setClassKind(nativeForwardDeclarationKind.getClassKind());
        firRegularClassBuilder.setScopeProvider(this.kotlinScopeProvider);
        firRegularClassBuilder.setSymbol(firRegularClassSymbol);
        firRegularClassBuilder.setResolvePhase(FirResolvePhase.INSTANCE.getANALYZED_DEPENDENCIES());
        List<FirTypeRef> superTypeRefs = firRegularClassBuilder.getSuperTypeRefs();
        FirResolvedTypeRefBuilder firResolvedTypeRefBuilder = new FirResolvedTypeRefBuilder();
        firResolvedTypeRefBuilder.setType(TypeConstructionUtilsKt.constructClassType$default(new ConeClassLikeLookupTagImpl(nativeForwardDeclarationKind.getSuperClassId()), new ConeTypeProjection[0], false, null, 4, null));
        superTypeRefs.add(firResolvedTypeRefBuilder.mo6809build());
        List<FirAnnotation> annotations = firRegularClassBuilder.getAnnotations();
        FirAnnotationBuilder firAnnotationBuilder = new FirAnnotationBuilder();
        FirResolvedTypeRefBuilder firResolvedTypeRefBuilder2 = new FirResolvedTypeRefBuilder();
        firResolvedTypeRefBuilder2.setType(TypeConstructionUtilsKt.constructClassType$default(TypeConstructionUtilsKt.toLookupTag(new ClassId(NativeStandardInteropNames.INSTANCE.getCInteropPackage(), NativeStandardInteropNames.INSTANCE.getExperimentalForeignApi())), new ConeTypeProjection[0], false, null, 4, null));
        firAnnotationBuilder.setAnnotationTypeRef(firResolvedTypeRefBuilder2.mo6809build());
        firAnnotationBuilder.setArgumentMapping(FirEmptyAnnotationArgumentMapping.INSTANCE);
        annotations.add(firAnnotationBuilder.mo6809build());
        FirRegularClass firRegularClassMo6809build = firRegularClassBuilder.mo6809build();
        firRegularClassMo6809build.replaceDeprecationsProvider(DeprecationUtilsKt.getDeprecationsProvider(firRegularClassMo6809build, getSession()));
        return firRegularClassSymbol;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider
    public FqName getPackage(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        if (getIncludedForwardDeclarationsByPackage().containsKey(fqName)) {
            return fqName;
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider
    public FirSymbolNamesProvider getSymbolNamesProvider() {
        return this.symbolNamesProvider;
    }
}
