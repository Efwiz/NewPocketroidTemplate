package org.jetbrains.kotlin.fir.resolve.providers.impl;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.koushikdutta.async.http.cache.ResponseCacheMiddleware;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.builtins.functions.FunctionTypeKind;
import org.jetbrains.kotlin.builtins.functions.FunctionTypeKindKt;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.EffectiveVisibility;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.Visibilities;
import org.jetbrains.kotlin.fir.FirModuleData;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.caches.FirCache;
import org.jetbrains.kotlin.fir.caches.FirCachesFactoryKt;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRef;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.declarations.builder.FirRegularClassBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirSimpleFunctionBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirTypeParameterBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirValueParameterBuilder;
import org.jetbrains.kotlin.fir.declarations.impl.FirResolvedDeclarationStatusImpl;
import org.jetbrains.kotlin.fir.declarations.utils.FirDeclarationBuildingUtilsKt;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.builder.FirAnnotationBuilder;
import org.jetbrains.kotlin.fir.expressions.impl.FirEmptyAnnotationArgumentMapping;
import org.jetbrains.kotlin.fir.resolve.ScopeUtilsKt;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolNamesProvider;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProviderInternals;
import org.jetbrains.kotlin.fir.scopes.FirKotlinScopeProvider;
import org.jetbrains.kotlin.fir.symbols.impl.ConeClassLikeLookupTagImpl;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeParameterSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirValueParameterSymbol;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.fir.types.FirFunctionTypeKindService;
import org.jetbrains.kotlin.fir.types.FirFunctionTypeKindServiceKt;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.TypeConstructionUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;
import org.jetbrains.kotlin.fir.types.impl.ConeTypeParameterTypeImpl;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.CallableId;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.StandardClassIds;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.util.OperatorNameConventions;

/* JADX INFO: compiled from: FirExtensionSyntheticFunctionInterfaceProvider.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\b&\u0018\u0000 02\u00020\u0001:\u00010B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u001a\u0010\u0016\u001a\u0004\u0018\u00010\f2\u0006\u0010\u0017\u001a\u00020\u000b2\u0006\u0010\u0018\u001a\u00020\rH\u0002J\u0012\u0010\u0019\u001a\u0004\u0018\u00010\f2\u0006\u0010\u0017\u001a\u00020\u000bH\u0016J\u000e\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001c0\u001bH\u0007J\u0012\u0010\u001d\u001a\u0004\u0018\u00010\u001c2\u0006\u0010\u001e\u001a\u00020\u001cH\u0016J*\u0010\u001f\u001a\u00020 2\u0010\u0010!\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030#0\"2\u0006\u0010$\u001a\u00020\u001c2\u0006\u0010%\u001a\u00020&H\u0017J&\u0010'\u001a\u00020 2\f\u0010!\u001a\b\u0012\u0004\u0012\u00020(0\"2\u0006\u0010$\u001a\u00020\u001c2\u0006\u0010%\u001a\u00020&H\u0017J&\u0010)\u001a\u00020 2\f\u0010!\u001a\b\u0012\u0004\u0012\u00020*0\"2\u0006\u0010$\u001a\u00020\u001c2\u0006\u0010%\u001a\u00020&H\u0017J\u0014\u0010\u0017\u001a\u00020\u000b*\u00020\r2\u0006\u0010+\u001a\u00020,H\u0002J\u000e\u0010-\u001a\u0004\u0018\u00010\r*\u00020\u000bH\u0002J\f\u0010.\u001a\u00020/*\u00020\rH$R\"\u0010\t\u001a\u0016\u0012\u0004\u0012\u00020\u000b\u0012\u0006\u0012\u0004\u0018\u00010\f\u0012\u0004\u0012\u00020\r0\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0012\u001a\u00020\u0013X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015¨\u00061"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/providers/impl/FirSyntheticFunctionInterfaceProviderBase;", "Lorg/jetbrains/kotlin/fir/resolve/providers/FirSymbolProvider;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "moduleData", "Lorg/jetbrains/kotlin/fir/FirModuleData;", "kotlinScopeProvider", "Lorg/jetbrains/kotlin/fir/scopes/FirKotlinScopeProvider;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/FirModuleData;Lorg/jetbrains/kotlin/fir/scopes/FirKotlinScopeProvider;)V", ResponseCacheMiddleware.CACHE, "Lorg/jetbrains/kotlin/fir/caches/FirCache;", "Lorg/jetbrains/kotlin/name/ClassId;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;", "Lorg/jetbrains/kotlin/builtins/functions/FunctionTypeKind;", "getKotlinScopeProvider", "()Lorg/jetbrains/kotlin/fir/scopes/FirKotlinScopeProvider;", "getModuleData", "()Lorg/jetbrains/kotlin/fir/FirModuleData;", "symbolNamesProvider", "Lorg/jetbrains/kotlin/fir/resolve/providers/FirSymbolNamesProvider;", "getSymbolNamesProvider", "()Lorg/jetbrains/kotlin/fir/resolve/providers/FirSymbolNamesProvider;", "createSyntheticFunctionInterface", "classId", Namer.METADATA_CLASS_KIND, "getClassLikeSymbolByClassId", "getFunctionKindPackageNames", "", "Lorg/jetbrains/kotlin/name/FqName;", "getPackage", "fqName", "getTopLevelCallableSymbolsTo", "", FirebaseAnalytics.Param.DESTINATION, "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "packageFqName", "name", "Lorg/jetbrains/kotlin/name/Name;", "getTopLevelFunctionSymbolsTo", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;", "getTopLevelPropertySymbolsTo", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertySymbol;", "arity", "", "getAcceptableFunctionTypeKind", "isAcceptable", "", "Companion", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirSyntheticFunctionInterfaceProviderBase extends FirSymbolProvider {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final FirCache<ClassId, FirRegularClassSymbol, FunctionTypeKind> cache;
    private final FirKotlinScopeProvider kotlinScopeProvider;
    private final FirModuleData moduleData;
    private final FirSymbolNamesProvider symbolNamesProvider;

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

    protected abstract boolean isAcceptable(FunctionTypeKind functionTypeKind);

    public final FirModuleData getModuleData() {
        return this.moduleData;
    }

    public final FirKotlinScopeProvider getKotlinScopeProvider() {
        return this.kotlinScopeProvider;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FirSyntheticFunctionInterfaceProviderBase(FirSession session, FirModuleData moduleData, FirKotlinScopeProvider kotlinScopeProvider) {
        super(session);
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(moduleData, "moduleData");
        Intrinsics.checkNotNullParameter(kotlinScopeProvider, "kotlinScopeProvider");
        this.moduleData = moduleData;
        this.kotlinScopeProvider = kotlinScopeProvider;
        this.symbolNamesProvider = new FirSymbolNamesProvider() { // from class: org.jetbrains.kotlin.fir.resolve.providers.impl.FirSyntheticFunctionInterfaceProviderBase$symbolNamesProvider$1
            @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolNamesProvider
            public boolean getMayHaveSyntheticFunctionTypes() {
                return true;
            }

            @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolNamesProvider
            public boolean mayHaveTopLevelCallable(FqName packageFqName, Name name2) {
                Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
                Intrinsics.checkNotNullParameter(name2, "name");
                return false;
            }

            @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolNamesProvider
            public boolean mayHaveSyntheticFunctionType(ClassId classId) {
                Intrinsics.checkNotNullParameter(classId, "classId");
                return this.this$0.getAcceptableFunctionTypeKind(classId) != null;
            }

            @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolNamesProvider
            public Set<String> getTopLevelClassifierNamesInPackage(FqName packageFqName) {
                Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
                return SetsKt.emptySet();
            }

            @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolNamesProvider
            public Set<String> getPackageNamesWithTopLevelCallables() {
                return SetsKt.emptySet();
            }

            @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolNamesProvider
            public Set<Name> getTopLevelCallableNamesInPackage(FqName packageFqName) {
                Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
                return SetsKt.emptySet();
            }

            @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolNamesProvider
            public boolean mayHaveTopLevelClassifier(ClassId classId) {
                Intrinsics.checkNotNullParameter(classId, "classId");
                return mayHaveSyntheticFunctionType(classId);
            }
        };
        this.cache = FirCachesFactoryKt.getFirCachesFactory(moduleData.getSession()).createCache(new FirSyntheticFunctionInterfaceProviderBase$cache$1(this));
    }

    @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider
    public FirSymbolNamesProvider getSymbolNamesProvider() {
        return this.symbolNamesProvider;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider
    public FirRegularClassSymbol getClassLikeSymbolByClassId(ClassId classId) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        FunctionTypeKind acceptableFunctionTypeKind = getAcceptableFunctionTypeKind(classId);
        if (acceptableFunctionTypeKind == null) {
            return null;
        }
        return this.cache.getValue(classId, acceptableFunctionTypeKind);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final FunctionTypeKind getAcceptableFunctionTypeKind(ClassId classId) {
        FunctionTypeKind functionTypeKind = INSTANCE.getFunctionTypeKind(classId, getSession());
        if (functionTypeKind == null || !isAcceptable(functionTypeKind)) {
            return null;
        }
        return functionTypeKind;
    }

    @FirSymbolProviderInternals
    public final Set<FqName> getFunctionKindPackageNames() {
        return FirFunctionTypeKindServiceKt.getFunctionTypeService(getSession()).getFunctionKindPackageNames();
    }

    @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider
    public FqName getPackage(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        if (FirFunctionTypeKindServiceKt.getFunctionTypeService(getSession()).hasKindWithSpecificPackage(fqName)) {
            return fqName;
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final FirRegularClassSymbol createSyntheticFunctionInterface(ClassId classId, FunctionTypeKind kind) {
        String strAsString = classId.getRelativeClassName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        if (!isAcceptable(kind)) {
            return null;
        }
        String strSubstring = strAsString.substring(kind.getClassNamePrefix().length());
        Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
        Integer intOrNull = StringsKt.toIntOrNull(strSubstring);
        if (intOrNull == null) {
            return null;
        }
        int iIntValue = intOrNull.intValue();
        FirRegularClassSymbol firRegularClassSymbol = new FirRegularClassSymbol(classId);
        FirRegularClassBuilder firRegularClassBuilder = new FirRegularClassBuilder();
        firRegularClassBuilder.setModuleData(this.moduleData);
        firRegularClassBuilder.setOrigin(FirDeclarationOrigin.BuiltIns.INSTANCE);
        Name nameShortName = classId.getRelativeClassName().shortName();
        Intrinsics.checkNotNullExpressionValue(nameShortName, "shortName(...)");
        firRegularClassBuilder.setName(nameShortName);
        firRegularClassBuilder.setStatus(new FirResolvedDeclarationStatusImpl(Visibilities.Public.INSTANCE, Modality.ABSTRACT, EffectiveVisibility.Public.INSTANCE));
        firRegularClassBuilder.setClassKind(ClassKind.INTERFACE);
        firRegularClassBuilder.setScopeProvider(this.kotlinScopeProvider);
        firRegularClassBuilder.setSymbol(firRegularClassSymbol);
        firRegularClassBuilder.setResolvePhase(FirResolvePhase.INSTANCE.getANALYZED_DEPENDENCIES());
        List<FirTypeParameterRef> typeParameters = firRegularClassBuilder.getTypeParameters();
        IntRange intRange = new IntRange(1, iIntValue);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(intRange, 10));
        Iterator<Integer> it = intRange.iterator();
        while (it.hasNext()) {
            int iNextInt = ((IntIterator) it).nextInt();
            FirTypeParameterBuilder firTypeParameterBuilder = new FirTypeParameterBuilder();
            firTypeParameterBuilder.setModuleData(this.moduleData);
            firTypeParameterBuilder.setResolvePhase(FirResolvePhase.INSTANCE.getANALYZED_DEPENDENCIES());
            firTypeParameterBuilder.setOrigin(FirDeclarationOrigin.BuiltIns.INSTANCE);
            Name nameIdentifier = Name.identifier("P" + iNextInt);
            Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
            firTypeParameterBuilder.setName(nameIdentifier);
            firTypeParameterBuilder.setSymbol(new FirTypeParameterSymbol());
            firTypeParameterBuilder.setContainingDeclarationSymbol(firRegularClassSymbol);
            firTypeParameterBuilder.setVariance(Variance.IN_VARIANCE);
            firTypeParameterBuilder.setReified(false);
            firTypeParameterBuilder.getBounds().add(firTypeParameterBuilder.getModuleData().getSession().getBuiltinTypes().getNullableAnyType());
            arrayList.add(firTypeParameterBuilder.mo6809build());
        }
        typeParameters.addAll(arrayList);
        List<FirTypeParameterRef> typeParameters2 = firRegularClassBuilder.getTypeParameters();
        FirTypeParameterBuilder firTypeParameterBuilder2 = new FirTypeParameterBuilder();
        firTypeParameterBuilder2.setModuleData(this.moduleData);
        firTypeParameterBuilder2.setResolvePhase(FirResolvePhase.INSTANCE.getANALYZED_DEPENDENCIES());
        firTypeParameterBuilder2.setOrigin(FirDeclarationOrigin.BuiltIns.INSTANCE);
        Name nameIdentifier2 = Name.identifier("R");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier2, "identifier(...)");
        firTypeParameterBuilder2.setName(nameIdentifier2);
        firTypeParameterBuilder2.setSymbol(new FirTypeParameterSymbol());
        firTypeParameterBuilder2.setContainingDeclarationSymbol(firRegularClassSymbol);
        firTypeParameterBuilder2.setVariance(Variance.OUT_VARIANCE);
        firTypeParameterBuilder2.setReified(false);
        firTypeParameterBuilder2.getBounds().add(firTypeParameterBuilder2.getModuleData().getSession().getBuiltinTypes().getNullableAnyType());
        typeParameters2.add(firTypeParameterBuilder2.mo6809build());
        Name name2 = OperatorNameConventions.INVOKE;
        FirResolvedDeclarationStatusImpl firResolvedDeclarationStatusImpl = new FirResolvedDeclarationStatusImpl(Visibilities.Public.INSTANCE, Modality.ABSTRACT, EffectiveVisibility.Public.INSTANCE);
        firResolvedDeclarationStatusImpl.setOperator(true);
        firResolvedDeclarationStatusImpl.setSuspend(FunctionTypeKindKt.isSuspendOrKSuspendFunction(kind));
        firResolvedDeclarationStatusImpl.setHasStableParameterNames(false);
        List<FirTypeParameterRef> typeParameters3 = firRegularClassBuilder.getTypeParameters();
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(typeParameters3, 10));
        Iterator<T> it2 = typeParameters3.iterator();
        while (it2.hasNext()) {
            arrayList2.add(TypeUtilsKt.toFirResolvedTypeRef$default(new ConeTypeParameterTypeImpl(((FirTypeParameterRef) it2.next()).getSymbol().getLookupTag(), false, null, 4, null), null, null, 3, null));
        }
        ArrayList arrayList3 = arrayList2;
        if (kind.getIsReflectType()) {
            firRegularClassBuilder.getSuperTypeRefs().add(TypeUtilsKt.toFirResolvedTypeRef$default(TypeConstructionUtilsKt.constructClassType$default(TypeConstructionUtilsKt.toLookupTag(StandardClassIds.INSTANCE.getKFunction()), new ConeKotlinType[]{((FirResolvedTypeRef) CollectionsKt.last((List) arrayList3)).getType()}, false, null, 4, null), null, null, 3, null));
            firRegularClassBuilder.getSuperTypeRefs().add(createSyntheticFunctionInterface$lambda$16$lambda$15$lambda$14$createSuperType(this, iIntValue, arrayList3, kind.nonReflectKind()));
        } else {
            firRegularClassBuilder.getSuperTypeRefs().add(TypeUtilsKt.toFirResolvedTypeRef$default(TypeConstructionUtilsKt.constructClassType$default(TypeConstructionUtilsKt.toLookupTag(StandardClassIds.INSTANCE.getFunction()), new ConeKotlinType[]{((FirResolvedTypeRef) CollectionsKt.last((List) arrayList3)).getType()}, false, null, 4, null), null, null, 3, null));
        }
        FirSimpleFunctionBuilder firSimpleFunctionBuilder = new FirSimpleFunctionBuilder();
        firSimpleFunctionBuilder.setModuleData(this.moduleData);
        firSimpleFunctionBuilder.setResolvePhase(FirResolvePhase.INSTANCE.getANALYZED_DEPENDENCIES());
        firSimpleFunctionBuilder.setOrigin(FirDeclarationOrigin.BuiltIns.INSTANCE);
        firSimpleFunctionBuilder.setReturnTypeRef((FirTypeRef) CollectionsKt.last((List) arrayList3));
        firSimpleFunctionBuilder.setName(name2);
        firSimpleFunctionBuilder.setStatus(firResolvedDeclarationStatusImpl);
        FqName packageFqName = classId.getPackageFqName();
        Intrinsics.checkNotNullExpressionValue(packageFqName, "getPackageFqName(...)");
        firSimpleFunctionBuilder.setSymbol(new FirNamedFunctionSymbol(new CallableId(packageFqName, classId.getRelativeClassName(), name2, null, 8, null)));
        firSimpleFunctionBuilder.setResolvePhase(FirResolvePhase.INSTANCE.getANALYZED_DEPENDENCIES());
        List<FirValueParameter> valueParameters = firSimpleFunctionBuilder.getValueParameters();
        List listDropLast = CollectionsKt.dropLast(arrayList3, 1);
        ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(listDropLast, 10));
        int i = 0;
        for (Object obj : listDropLast) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            Name nameIdentifier3 = Name.identifier("p" + i2);
            Intrinsics.checkNotNullExpressionValue(nameIdentifier3, "identifier(...)");
            FirValueParameterBuilder firValueParameterBuilder = new FirValueParameterBuilder();
            firValueParameterBuilder.setModuleData(this.moduleData);
            firValueParameterBuilder.setContainingFunctionSymbol(firSimpleFunctionBuilder.getSymbol());
            firValueParameterBuilder.setOrigin(FirDeclarationOrigin.BuiltIns.INSTANCE);
            firValueParameterBuilder.setResolvePhase(FirResolvePhase.INSTANCE.getANALYZED_DEPENDENCIES());
            firValueParameterBuilder.setReturnTypeRef((FirResolvedTypeRef) obj);
            firValueParameterBuilder.setName(nameIdentifier3);
            firValueParameterBuilder.setSymbol(new FirValueParameterSymbol(nameIdentifier3));
            firValueParameterBuilder.setDefaultValue(null);
            firValueParameterBuilder.setCrossinline(false);
            firValueParameterBuilder.setNoinline(false);
            firValueParameterBuilder.setVararg(false);
            arrayList4.add(firValueParameterBuilder.mo6809build());
            i = i2;
        }
        CollectionsKt.addAll(valueParameters, arrayList4);
        List<FirTypeParameterRef> typeParameters4 = firRegularClassBuilder.getTypeParameters();
        ArrayList arrayList5 = new ArrayList(CollectionsKt.collectionSizeOrDefault(typeParameters4, 10));
        Iterator<T> it3 = typeParameters4.iterator();
        while (it3.hasNext()) {
            arrayList5.add(((FirTypeParameterRef) it3.next()).getSymbol());
        }
        firSimpleFunctionBuilder.setDispatchReceiverType(ScopeUtilsKt.defaultType(classId, arrayList5));
        ClassId annotationOnInvokeClassId = kind.getAnnotationOnInvokeClassId();
        if (annotationOnInvokeClassId != null) {
            List<FirAnnotation> annotations = firSimpleFunctionBuilder.getAnnotations();
            FirAnnotationBuilder firAnnotationBuilder = new FirAnnotationBuilder();
            firAnnotationBuilder.setAnnotationTypeRef(TypeUtilsKt.toFirResolvedTypeRef$default(TypeConstructionUtilsKt.constructClassLikeType$default(annotationOnInvokeClassId, new ConeTypeProjection[0], false, null, 4, null), null, null, 3, null));
            firAnnotationBuilder.setArgumentMapping(FirEmptyAnnotationArgumentMapping.INSTANCE);
            annotations.add(firAnnotationBuilder.mo6809build());
        }
        FirDeclarationBuildingUtilsKt.addDeclaration(firRegularClassBuilder, firSimpleFunctionBuilder.mo6809build());
        firRegularClassBuilder.mo6809build();
        return firRegularClassSymbol;
    }

    private static final FirResolvedTypeRef createSyntheticFunctionInterface$lambda$16$lambda$15$lambda$14$createSuperType(FirSyntheticFunctionInterfaceProviderBase firSyntheticFunctionInterfaceProviderBase, int i, List<? extends FirResolvedTypeRef> list, FunctionTypeKind functionTypeKind) {
        ConeClassLikeLookupTagImpl lookupTag = TypeConstructionUtilsKt.toLookupTag(firSyntheticFunctionInterfaceProviderBase.classId(functionTypeKind, i));
        List<? extends FirResolvedTypeRef> list2 = list;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
        Iterator<T> it = list2.iterator();
        while (it.hasNext()) {
            arrayList.add(((FirResolvedTypeRef) it.next()).getType());
        }
        return TypeUtilsKt.toFirResolvedTypeRef$default(TypeConstructionUtilsKt.constructClassType$default(lookupTag, (ConeTypeProjection[]) arrayList.toArray(new ConeKotlinType[0]), false, null, 4, null), null, null, 3, null);
    }

    private final ClassId classId(FunctionTypeKind functionTypeKind, int i) {
        return new ClassId(functionTypeKind.getPackageFqName(), functionTypeKind.numberedClassName(i));
    }

    /* JADX INFO: compiled from: FirExtensionSyntheticFunctionInterfaceProvider.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u0004\u0018\u00010\u0004*\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0003J\u0014\u0010\b\u001a\u00020\t*\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0007J\f\u0010\n\u001a\u00020\t*\u00020\u0005H\u0007¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/providers/impl/FirSyntheticFunctionInterfaceProviderBase$Companion;", "", "()V", "getFunctionTypeKind", "Lorg/jetbrains/kotlin/builtins/functions/FunctionTypeKind;", "Lorg/jetbrains/kotlin/name/ClassId;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "isNameForFunctionClass", "", "mayBeSyntheticFunctionClassName", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @FirSymbolProviderInternals
        public final boolean isNameForFunctionClass(ClassId classId, FirSession session) {
            Intrinsics.checkNotNullParameter(classId, "<this>");
            Intrinsics.checkNotNullParameter(session, "session");
            return getFunctionTypeKind(classId, session) != null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        @FirSymbolProviderInternals
        public final FunctionTypeKind getFunctionTypeKind(ClassId classId, FirSession firSession) {
            if (!mayBeSyntheticFunctionClassName(classId)) {
                return null;
            }
            FirFunctionTypeKindService functionTypeService = FirFunctionTypeKindServiceKt.getFunctionTypeService(firSession);
            FqName packageFqName = classId.getPackageFqName();
            Intrinsics.checkNotNullExpressionValue(packageFqName, "getPackageFqName(...)");
            String strAsString = classId.getShortClassName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            return functionTypeService.getKindByClassNamePrefix(packageFqName, strAsString);
        }

        @FirSymbolProviderInternals
        public final boolean mayBeSyntheticFunctionClassName(ClassId classId) {
            Intrinsics.checkNotNullParameter(classId, "<this>");
            String strAsString = classId.getRelativeClassName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            Character chLastOrNull = StringsKt.lastOrNull(strAsString);
            return chLastOrNull != null && Character.isDigit(chLastOrNull.charValue());
        }
    }
}
