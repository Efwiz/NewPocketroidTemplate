package org.jetbrains.kotlin.fir.resolve;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.EffectiveVisibility;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.FirModuleDataKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.caches.FirCache;
import org.jetbrains.kotlin.fir.caches.NullableMap;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirContextReceiver;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirTypeAlias;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameter;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRef;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.declarations.builder.FirSimpleFunctionBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirTypeParameterBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirValueParameterBuilder;
import org.jetbrains.kotlin.fir.declarations.impl.FirResolvedDeclarationStatusImpl;
import org.jetbrains.kotlin.fir.extensions.FirExtensionServiceKt;
import org.jetbrains.kotlin.fir.resolve.calls.FirSyntheticFunctionSymbol;
import org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutor;
import org.jetbrains.kotlin.fir.resolve.substitution.SubstitutorsKt;
import org.jetbrains.kotlin.fir.scopes.impl.FirFakeOverrideGenerator;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeAliasSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeParameterSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirValueParameterSymbol;
import org.jetbrains.kotlin.fir.types.ConeCapturedType;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeDefinitelyNotNullType;
import org.jetbrains.kotlin.fir.types.ConeFlexibleType;
import org.jetbrains.kotlin.fir.types.ConeIntegerLiteralType;
import org.jetbrains.kotlin.fir.types.ConeIntersectionType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeLookupTagBasedType;
import org.jetbrains.kotlin.fir.types.ConeNullability;
import org.jetbrains.kotlin.fir.types.ConeStubType;
import org.jetbrains.kotlin.fir.types.ConeTypeParameterType;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeTypeVariableType;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeComponentsKt;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;
import org.jetbrains.kotlin.fir.types.builder.FirResolvedTypeRefBuilder;
import org.jetbrains.kotlin.fir.types.impl.ConeClassLikeTypeImpl;
import org.jetbrains.kotlin.fir.types.impl.ConeTypeParameterTypeImpl;
import org.jetbrains.kotlin.name.CallableId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.utils.addToStdlib.AddToStdlibKt;

/* JADX INFO: compiled from: SamResolution.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0080\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\bJ\u0010\u0010\u0016\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u0017\u001a\u00020\u0018J\u0010\u0010\u0019\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u001a\u001a\u00020\u001bJ\u0012\u0010\u001c\u001a\u0004\u0018\u00010\r2\u0006\u0010\u001d\u001a\u00020\u001eH\u0002J\u0010\u0010\u001c\u001a\u0004\u0018\u00010\u001f2\u0006\u0010\u001d\u001a\u00020\u001fJ\u0010\u0010 \u001a\u0004\u0018\u00010!2\u0006\u0010\"\u001a\u00020#J\u000e\u0010$\u001a\u00020%2\u0006\u0010\u001d\u001a\u00020\u001fJ\u0018\u0010&\u001a\n\u0012\u0004\u0012\u00020\r\u0018\u00010\f2\u0006\u0010'\u001a\u00020\u000bH\u0002J\u0010\u0010(\u001a\u00020)*\u0006\u0012\u0002\b\u00030\u0011H\u0002R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R*\u0010\t\u001a\u0016\u0012\u0004\u0012\u00020\u000b\u0012\f\u0012\n\u0012\u0004\u0012\u00020\r\u0018\u00010\f0\nX\u0082\u0004ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\n\u0002\u0010\u000eR&\u0010\u000f\u001a\u001a\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0011\u0012\u0006\u0012\u0004\u0018\u00010\u0012\u0012\u0004\u0012\u00020\u00000\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0005\b¡\u001e0\u0001\n\u0002\b!¨\u0006*"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/FirSamResolver;", "", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "outerClassManager", "Lorg/jetbrains/kotlin/fir/resolve/FirOuterClassManager;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;Lorg/jetbrains/kotlin/fir/resolve/FirOuterClassManager;)V", "resolvedFunctionType", "Lorg/jetbrains/kotlin/fir/caches/NullableMap;", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "Lorg/jetbrains/kotlin/fir/resolve/SAMInfo;", "Lorg/jetbrains/kotlin/fir/types/ConeLookupTagBasedType;", "Ljava/util/Map;", "samConstructorsCache", "Lorg/jetbrains/kotlin/fir/caches/FirCache;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;", "samConversionTransformers", "", "Lorg/jetbrains/kotlin/fir/resolve/FirSamConversionTransformerExtension;", "buildSamConstructorForRegularClass", "classSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;", "buildSamConstructorForTypeAlias", "typeAliasSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirTypeAliasSymbol;", "getFunctionTypeForPossibleSamType", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/fir/types/ConeClassLikeType;", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "getSamConstructor", "Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;", "firClassOrTypeAlias", "Lorg/jetbrains/kotlin/fir/declarations/FirClassLikeDeclaration;", "isSamType", "", "resolveFunctionTypeIfSamInterface", "firRegularClass", "createSyntheticConstructorSymbol", "Lorg/jetbrains/kotlin/fir/resolve/calls/FirSyntheticFunctionSymbol;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirSamResolver {
    private final FirOuterClassManager outerClassManager;
    private final Map<K, Object> resolvedFunctionType;
    private final FirCache<FirClassLikeSymbol<?>, FirNamedFunctionSymbol, FirSamResolver> samConstructorsCache;
    private final List<FirSamConversionTransformerExtension> samConversionTransformers;
    private final ScopeSession scopeSession;
    private final FirSession session;

    public FirSamResolver(FirSession session, ScopeSession scopeSession, FirOuterClassManager firOuterClassManager) {
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
        this.session = session;
        this.scopeSession = scopeSession;
        this.outerClassManager = firOuterClassManager;
        this.resolvedFunctionType = NullableMap.m6790constructorimpl$default(null, 1, null);
        this.samConstructorsCache = SamResolutionKt.getSamConstructorStorage(session).getSamConstructors();
        this.samConversionTransformers = FirSamConversionTransformerExtensionKt.getSamConversionTransformers(FirExtensionServiceKt.getExtensionService(session));
    }

    public /* synthetic */ FirSamResolver(FirSession firSession, ScopeSession scopeSession, FirOuterClassManager firOuterClassManager, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(firSession, scopeSession, (i & 4) != 0 ? null : firOuterClassManager);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final boolean isSamType(ConeKotlinType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        if (type instanceof ConeClassLikeType) {
            FirClassLikeSymbol<?> symbol = LookupTagUtilsKt.toSymbol(TypeExpansionUtilsKt.fullyExpandedType$default((ConeClassLikeType) type, this.session, null, 2, null).getLookupTag(), this.session);
            if ((symbol instanceof FirRegularClassSymbol) && resolveFunctionTypeIfSamInterface((FirRegularClass) ((FirRegularClassSymbol) symbol).getFir()) != null) {
                return true;
            }
        } else if (type instanceof ConeFlexibleType) {
            ConeFlexibleType coneFlexibleType = (ConeFlexibleType) type;
            if (isSamType(coneFlexibleType.getLowerBound()) && isSamType(coneFlexibleType.getUpperBound())) {
                return true;
            }
        }
        return false;
    }

    public final ConeKotlinType getFunctionTypeForPossibleSamType(ConeKotlinType type) {
        ConeKotlinType functionTypeForPossibleSamType;
        Intrinsics.checkNotNullParameter(type, "type");
        if (type instanceof ConeClassLikeType) {
            return getFunctionTypeForPossibleSamType(TypeExpansionUtilsKt.fullyExpandedType$default((ConeClassLikeType) type, this.session, null, 2, null));
        }
        if (type instanceof ConeFlexibleType) {
            ConeFlexibleType coneFlexibleType = (ConeFlexibleType) type;
            ConeKotlinType functionTypeForPossibleSamType2 = getFunctionTypeForPossibleSamType(coneFlexibleType.getLowerBound());
            if (functionTypeForPossibleSamType2 == null || (functionTypeForPossibleSamType = getFunctionTypeForPossibleSamType(coneFlexibleType.getUpperBound())) == null) {
                return null;
            }
            return new ConeFlexibleType(ConeTypeUtilsKt.lowerBoundIfFlexible(functionTypeForPossibleSamType2), ConeTypeUtilsKt.upperBoundIfFlexible(functionTypeForPossibleSamType));
        }
        if (type instanceof ConeStubType ? true : type instanceof ConeTypeParameterType ? true : type instanceof ConeTypeVariableType ? true : type instanceof ConeCapturedType ? true : type instanceof ConeDefinitelyNotNullType ? true : type instanceof ConeIntersectionType ? true : type instanceof ConeIntegerLiteralType) {
            return null;
        }
        if (!(type instanceof ConeLookupTagBasedType)) {
            throw new NoWhenBranchMatchedException();
        }
        AddToStdlibKt.unreachableBranch(type);
        throw null;
    }

    private final ConeLookupTagBasedType getFunctionTypeForPossibleSamType(ConeClassLikeType type) {
        SAMInfo<ConeLookupTagBasedType> sAMInfoResolveFunctionTypeIfSamInterface;
        ConeLookupTagBasedType coneLookupTagBasedTypeSubstituteOrNull;
        FirRegularClass firRegularClass = LookupTagUtilsKt.toFirRegularClass(type.getLookupTag(), this.session);
        if (firRegularClass == null || (sAMInfoResolveFunctionTypeIfSamInterface = resolveFunctionTypeIfSamInterface(firRegularClass)) == null) {
            return null;
        }
        ConeLookupTagBasedType coneLookupTagBasedType = (ConeLookupTagBasedType) sAMInfoResolveFunctionTypeIfSamInterface.component2();
        ConeSubstitutor coneSubstitutorBuildSubstitutorForSamTypeAlias = SamResolutionKt.buildSubstitutorForSamTypeAlias(firRegularClass, this.session, type);
        if (coneSubstitutorBuildSubstitutorForSamTypeAlias == null || (coneLookupTagBasedTypeSubstituteOrNull = coneSubstitutorBuildSubstitutorForSamTypeAlias.substituteOrNull(coneLookupTagBasedType)) == null) {
            coneLookupTagBasedTypeSubstituteOrNull = coneLookupTagBasedType;
        }
        ConeKotlinType coneKotlinType = coneLookupTagBasedTypeSubstituteOrNull;
        if (!(coneKotlinType instanceof ConeLookupTagBasedType)) {
            throw new IllegalArgumentException(("Function type should always be ConeLookupTagBasedType, but " + Reflection.getOrCreateKotlinClass(coneKotlinType.getClass()) + " was found").toString());
        }
        return (ConeLookupTagBasedType) TypeUtilsKt.withNullability$default(coneKotlinType, ConeNullability.INSTANCE.create(ConeTypeUtilsKt.isMarkedNullable(type)), TypeComponentsKt.getTypeContext(this.session), null, false, 12, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final FirSimpleFunction getSamConstructor(FirClassLikeDeclaration firClassOrTypeAlias) {
        ConeClassLikeType coneClassLikeTypeFullyExpandedType$default;
        ConeClassLikeLookupTag lookupTag;
        FirClassLikeSymbol<?> symbol;
        Intrinsics.checkNotNullParameter(firClassOrTypeAlias, "firClassOrTypeAlias");
        if (firClassOrTypeAlias instanceof FirTypeAlias) {
            FirTypeRef expandedTypeRef = ((FirTypeAlias) firClassOrTypeAlias).getExpandedTypeRef();
            FirResolvedTypeRef firResolvedTypeRef = expandedTypeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) expandedTypeRef : null;
            ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
            if (!(type instanceof ConeClassLikeType)) {
                type = null;
            }
            ConeClassLikeType coneClassLikeType = (ConeClassLikeType) type;
            if (coneClassLikeType != null && (coneClassLikeTypeFullyExpandedType$default = TypeExpansionUtilsKt.fullyExpandedType$default(coneClassLikeType, this.session, null, 2, null)) != null && (lookupTag = coneClassLikeTypeFullyExpandedType$default.getLookupTag()) != null && (symbol = LookupTagUtilsKt.toSymbol(lookupTag, this.session)) != null) {
                this.samConstructorsCache.getValue(symbol, this);
            }
        }
        FirNamedFunctionSymbol value = this.samConstructorsCache.getValue(firClassOrTypeAlias.getSymbol(), this);
        if (value != null) {
            return (FirSimpleFunction) value.getFir();
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final FirNamedFunctionSymbol buildSamConstructorForRegularClass(FirRegularClassSymbol classSymbol) {
        FirClassLikeSymbol<?> firClassLikeSymbolOuterClass;
        Intrinsics.checkNotNullParameter(classSymbol, "classSymbol");
        FirRegularClass firRegularClass = (FirRegularClass) classSymbol.getFir();
        SAMInfo<ConeLookupTagBasedType> sAMInfoResolveFunctionTypeIfSamInterface = resolveFunctionTypeIfSamInterface(firRegularClass);
        if (sAMInfoResolveFunctionTypeIfSamInterface == null) {
            return null;
        }
        FirNamedFunctionSymbol symbol = sAMInfoResolveFunctionTypeIfSamInterface.getSymbol();
        ConeLookupTagBasedType coneLookupTagBasedType = (ConeLookupTagBasedType) sAMInfoResolveFunctionTypeIfSamInterface.component2();
        FirSyntheticFunctionSymbol firSyntheticFunctionSymbolCreateSyntheticConstructorSymbol = createSyntheticConstructorSymbol(classSymbol);
        List<FirTypeParameterRef> typeParameters = firRegularClass.getTypeParameters();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(typeParameters, 10));
        Iterator<T> it = typeParameters.iterator();
        while (it.hasNext()) {
            FirTypeParameter firTypeParameter = (FirTypeParameter) ((FirTypeParameterRef) it.next()).getSymbol().getFir();
            FirTypeParameterBuilder firTypeParameterBuilder = new FirTypeParameterBuilder();
            firTypeParameterBuilder.setSource(firTypeParameter.getSource());
            firTypeParameterBuilder.setModuleData(FirModuleDataKt.getModuleData(this.session));
            firTypeParameterBuilder.setOrigin(FirDeclarationOrigin.SamConstructor.INSTANCE);
            firTypeParameterBuilder.setResolvePhase(FirResolvePhase.INSTANCE.getDECLARATIONS());
            firTypeParameterBuilder.setName(firTypeParameter.getName());
            firTypeParameterBuilder.setSymbol(new FirTypeParameterSymbol());
            firTypeParameterBuilder.setVariance(Variance.INVARIANT);
            firTypeParameterBuilder.setReified(false);
            CollectionsKt.addAll(firTypeParameterBuilder.getAnnotations(), firTypeParameter.getAnnotations());
            firTypeParameterBuilder.setContainingDeclarationSymbol(firSyntheticFunctionSymbolCreateSyntheticConstructorSymbol);
            arrayList.add(firTypeParameterBuilder);
        }
        ArrayList arrayList2 = arrayList;
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
        Iterator it2 = arrayList2.iterator();
        while (it2.hasNext()) {
            arrayList3.add(new ConeTypeParameterTypeImpl(((FirTypeParameterBuilder) it2.next()).getSymbol().getLookupTag(), false, null, 4, null));
        }
        ArrayList arrayList4 = arrayList3;
        List<FirTypeParameterRef> typeParameters2 = firRegularClass.getTypeParameters();
        ArrayList arrayList5 = new ArrayList(CollectionsKt.collectionSizeOrDefault(typeParameters2, 10));
        Iterator<T> it3 = typeParameters2.iterator();
        while (it3.hasNext()) {
            arrayList5.add(((FirTypeParameterRef) it3.next()).getSymbol());
        }
        ConeSubstitutor coneSubstitutorSubstitutorByMap = SubstitutorsKt.substitutorByMap(MapsKt.toMap(CollectionsKt.zip(arrayList5, arrayList4)), this.session);
        for (Pair pair : CollectionsKt.zip(arrayList2, firRegularClass.getTypeParameters())) {
            FirTypeParameterBuilder firTypeParameterBuilder2 = (FirTypeParameterBuilder) pair.component1();
            FirTypeParameter firTypeParameter2 = (FirTypeParameter) ((FirTypeParameterRef) pair.component2()).getSymbol().getFir();
            List<FirTypeRef> bounds = firTypeParameterBuilder2.getBounds();
            List<FirResolvedTypeRef> resolvedBounds = firTypeParameter2.getSymbol().getResolvedBounds();
            ArrayList arrayList6 = new ArrayList(CollectionsKt.collectionSizeOrDefault(resolvedBounds, 10));
            for (FirResolvedTypeRef firResolvedTypeRef : resolvedBounds) {
                FirResolvedTypeRefBuilder firResolvedTypeRefBuilder = new FirResolvedTypeRefBuilder();
                firResolvedTypeRefBuilder.setSource(firResolvedTypeRef.getSource());
                firResolvedTypeRefBuilder.setType(coneSubstitutorSubstitutorByMap.substituteOrSelf(FirTypeUtilsKt.getConeType(firResolvedTypeRef)));
                arrayList6.add(firResolvedTypeRefBuilder.mo6809build());
            }
            CollectionsKt.addAll(bounds, arrayList6);
        }
        FirSimpleFunctionBuilder firSimpleFunctionBuilder = new FirSimpleFunctionBuilder();
        firSimpleFunctionBuilder.setModuleData(FirModuleDataKt.getModuleData(this.session));
        firSimpleFunctionBuilder.setSource(firRegularClass.getSource());
        firSimpleFunctionBuilder.setName(firSyntheticFunctionSymbolCreateSyntheticConstructorSymbol.getName());
        firSimpleFunctionBuilder.setOrigin(FirDeclarationOrigin.SamConstructor.INSTANCE);
        FirRegularClass firRegularClass2 = firRegularClass;
        FirResolvedDeclarationStatusImpl firResolvedDeclarationStatusImpl = new FirResolvedDeclarationStatusImpl(firRegularClass2.getStatus().getVisibility(), Modality.FINAL, EffectiveVisibility.Local.INSTANCE);
        firResolvedDeclarationStatusImpl.setExpect(firRegularClass2.getStatus().isExpect());
        firResolvedDeclarationStatusImpl.setActual(firRegularClass2.getStatus().isActual());
        firSimpleFunctionBuilder.setStatus(firResolvedDeclarationStatusImpl);
        firSimpleFunctionBuilder.setSymbol(firSyntheticFunctionSymbolCreateSyntheticConstructorSymbol);
        List<FirTypeParameter> typeParameters3 = firSimpleFunctionBuilder.getTypeParameters();
        ArrayList arrayList7 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
        Iterator it4 = arrayList2.iterator();
        while (it4.hasNext()) {
            arrayList7.add(((FirTypeParameterBuilder) it4.next()).mo6809build());
        }
        CollectionsKt.addAll(typeParameters3, arrayList7);
        ConeKotlinType coneKotlinTypeSubstituteOrSelf = coneSubstitutorSubstitutorByMap.substituteOrSelf(coneLookupTagBasedType);
        ConeClassLikeTypeImpl coneClassLikeTypeImpl = new ConeClassLikeTypeImpl(firRegularClass.getSymbol().getLookupTag(), (ConeTypeProjection[]) arrayList4.toArray(new ConeTypeParameterTypeImpl[0]), false, null, 8, null);
        FirResolvedTypeRefBuilder firResolvedTypeRefBuilder2 = new FirResolvedTypeRefBuilder();
        firResolvedTypeRefBuilder2.setSource(null);
        firResolvedTypeRefBuilder2.setType(coneClassLikeTypeImpl);
        firSimpleFunctionBuilder.setReturnTypeRef(firResolvedTypeRefBuilder2.mo6809build());
        List<FirValueParameter> valueParameters = firSimpleFunctionBuilder.getValueParameters();
        FirValueParameterBuilder firValueParameterBuilder = new FirValueParameterBuilder();
        firValueParameterBuilder.setModuleData(FirModuleDataKt.getModuleData(this.session));
        firValueParameterBuilder.setContainingFunctionSymbol(firSyntheticFunctionSymbolCreateSyntheticConstructorSymbol);
        firValueParameterBuilder.setOrigin(FirDeclarationOrigin.SamConstructor.INSTANCE);
        FirResolvedTypeRefBuilder firResolvedTypeRefBuilder3 = new FirResolvedTypeRefBuilder();
        firResolvedTypeRefBuilder3.setSource(firRegularClass.getSource());
        firResolvedTypeRefBuilder3.setType(coneKotlinTypeSubstituteOrSelf);
        firValueParameterBuilder.setReturnTypeRef(firResolvedTypeRefBuilder3.mo6809build());
        firValueParameterBuilder.setName(SamResolutionKt.SAM_PARAMETER_NAME);
        firValueParameterBuilder.setSymbol(new FirValueParameterSymbol(SamResolutionKt.SAM_PARAMETER_NAME));
        firValueParameterBuilder.setCrossinline(false);
        firValueParameterBuilder.setNoinline(false);
        firValueParameterBuilder.setVararg(false);
        firValueParameterBuilder.setResolvePhase(FirResolvePhase.BODY_RESOLVE);
        valueParameters.add(firValueParameterBuilder.mo6809build());
        CollectionsKt.addAll(firSimpleFunctionBuilder.getAnnotations(), symbol.getAnnotations());
        firSimpleFunctionBuilder.setResolvePhase(FirResolvePhase.BODY_RESOLVE);
        FirSimpleFunction firSimpleFunctionBuild = firSimpleFunctionBuilder.mo6809build();
        FirSimpleFunction firSimpleFunction = firSimpleFunctionBuild;
        FirOuterClassManager firOuterClassManager = this.outerClassManager;
        ClassMembersKt.setContainingClassForStaticMemberAttr(firSimpleFunction, (firOuterClassManager == null || (firClassLikeSymbolOuterClass = firOuterClassManager.outerClass(firRegularClass.getSymbol())) == null) ? null : firClassLikeSymbolOuterClass.getLookupTag());
        return firSimpleFunctionBuild.getSymbol();
    }

    /* JADX WARN: Code duplicated, block: B:47:0x0110  */
    /* JADX WARN: Code duplicated, block: B:50:0x011a  */
    /* JADX WARN: Code duplicated, block: B:62:0x0128 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:63:? A[LOOP:2: B:48:0x0114->B:63:?, LOOP_END, SYNTHETIC] */
    /* JADX WARN: Multi-variable type inference failed */
    public final FirNamedFunctionSymbol buildSamConstructorForTypeAlias(FirTypeAliasSymbol typeAliasSymbol) {
        FirSimpleFunction samConstructor;
        ArrayList arrayList;
        Iterator it;
        Intrinsics.checkNotNullParameter(typeAliasSymbol, "typeAliasSymbol");
        ConeKotlinType type = ((FirResolvedTypeRef) ((FirTypeAlias) typeAliasSymbol.getFir()).getExpandedTypeRef()).getType();
        if (type != null) {
            ConeClassLikeType coneClassLikeTypeFullyExpandedType$default = TypeExpansionUtilsKt.fullyExpandedType$default((ConeClassLikeType) type, this.session, null, 2, null);
            FirClassLikeSymbol<?> symbol = LookupTagUtilsKt.toSymbol(coneClassLikeTypeFullyExpandedType$default.getLookupTag(), this.session);
            FirClassLikeDeclaration firClassLikeDeclaration = symbol != null ? (FirClassLikeDeclaration) symbol.getFir() : null;
            FirRegularClass firRegularClass = firClassLikeDeclaration instanceof FirRegularClass ? (FirRegularClass) firClassLikeDeclaration : null;
            if (firRegularClass == null || (samConstructor = getSamConstructor(firRegularClass)) == null) {
                return null;
            }
            ConeSubstitutor coneSubstitutorBuildSubstitutorForSamTypeAlias = SamResolutionKt.buildSubstitutorForSamTypeAlias(samConstructor, this.session, coneClassLikeTypeFullyExpandedType$default);
            if (coneSubstitutorBuildSubstitutorForSamTypeAlias == null) {
                return samConstructor.getSymbol();
            }
            ConeKotlinType coneKotlinTypeSubstituteOrNull = coneSubstitutorBuildSubstitutorForSamTypeAlias.substituteOrNull(FirTypeUtilsKt.getConeType(samConstructor.getReturnTypeRef()));
            List<FirValueParameter> valueParameters = samConstructor.getValueParameters();
            ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(valueParameters, 10));
            Iterator<T> it2 = valueParameters.iterator();
            while (it2.hasNext()) {
                arrayList2.add(coneSubstitutorBuildSubstitutorForSamTypeAlias.substituteOrNull(FirTypeUtilsKt.getConeType(((FirValueParameter) it2.next()).getReturnTypeRef())));
            }
            ArrayList arrayList3 = arrayList2;
            List<FirContextReceiver> contextReceivers = samConstructor.getContextReceivers();
            ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(contextReceivers, 10));
            Iterator<T> it3 = contextReceivers.iterator();
            while (it3.hasNext()) {
                arrayList4.add(coneSubstitutorBuildSubstitutorForSamTypeAlias.substituteOrNull(FirTypeUtilsKt.getConeType(((FirContextReceiver) it3.next()).getTypeRef())));
            }
            ArrayList arrayList5 = arrayList4;
            if (coneKotlinTypeSubstituteOrNull == null) {
                ArrayList arrayList6 = arrayList3;
                if ((arrayList6 instanceof Collection) && arrayList6.isEmpty()) {
                    arrayList = arrayList5;
                    if (arrayList instanceof Collection) {
                        it = arrayList.iterator();
                        while (it.hasNext()) {
                            if (((ConeKotlinType) it.next()) == null) {
                            }
                        }
                    } else {
                        it = arrayList.iterator();
                        while (it.hasNext()) {
                            if (((ConeKotlinType) it.next()) == null) {
                            }
                        }
                    }
                    return samConstructor.getSymbol();
                }
                Iterator it4 = arrayList6.iterator();
                while (it4.hasNext()) {
                    if (((ConeKotlinType) it4.next()) == null) {
                    }
                }
                arrayList = arrayList5;
                if ((arrayList instanceof Collection) || !arrayList.isEmpty()) {
                    it = arrayList.iterator();
                    while (it.hasNext()) {
                        if (((ConeKotlinType) it.next()) == null) {
                        }
                    }
                }
                return samConstructor.getSymbol();
            }
            return FirFakeOverrideGenerator.createCopyForFirFunction$default(FirFakeOverrideGenerator.INSTANCE, createSyntheticConstructorSymbol(typeAliasSymbol), samConstructor, null, this.session, FirDeclarationOrigin.SamConstructor.INSTANCE, false, null, arrayList3, ((FirTypeAlias) typeAliasSymbol.getFir()).getTypeParameters(), null, arrayList5, coneKotlinTypeSubstituteOrNull, null, null, null, 28704, null).getSymbol();
        }
        throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.types.ConeClassLikeType");
    }

    private final FirSyntheticFunctionSymbol createSyntheticConstructorSymbol(FirClassLikeSymbol<?> firClassLikeSymbol) {
        FqName packageFqName = firClassLikeSymbol.getClassId().getPackageFqName();
        Intrinsics.checkNotNullExpressionValue(packageFqName, "getPackageFqName(...)");
        FqName fqNameParent = firClassLikeSymbol.getClassId().getRelativeClassName().parent();
        if (fqNameParent.isRoot()) {
            fqNameParent = null;
        }
        Name shortClassName = firClassLikeSymbol.getClassId().getShortClassName();
        Intrinsics.checkNotNullExpressionValue(shortClassName, "getShortClassName(...)");
        return new FirSyntheticFunctionSymbol(new CallableId(packageFqName, fqNameParent, shortClassName, null, 8, null));
    }

    /* JADX WARN: Type inference incomplete: some casts might be missing */
    private final SAMInfo<ConeLookupTagBasedType> resolveFunctionTypeIfSamInterface(FirRegularClass firRegularClass) {
        FirSimpleFunction singleAbstractMethodOrNull;
        Map<K, Object> map = this.resolvedFunctionType;
        Object sAMInfo = map.get(firRegularClass);
        ConeLookupTagBasedType functionTypeForAbstractMethod = null;
        if (sAMInfo == null) {
            if (firRegularClass.getStatus().isFun() && (singleAbstractMethodOrNull = SamResolutionKt.getSingleAbstractMethodOrNull(firRegularClass, this.session, this.scopeSession)) != null) {
                Iterator<T> it = this.samConversionTransformers.iterator();
                while (it.hasNext()) {
                    ConeLookupTagBasedType customFunctionTypeForSamConversion = ((FirSamConversionTransformerExtension) it.next()).getCustomFunctionTypeForSamConversion(singleAbstractMethodOrNull);
                    if (customFunctionTypeForSamConversion != null) {
                        functionTypeForAbstractMethod = customFunctionTypeForSamConversion;
                        break;
                    }
                }
                FirNamedFunctionSymbol symbol = singleAbstractMethodOrNull.getSymbol();
                if (functionTypeForAbstractMethod == null) {
                    functionTypeForAbstractMethod = SamResolutionKt.getFunctionTypeForAbstractMethod(singleAbstractMethodOrNull, this.session);
                }
                sAMInfo = new SAMInfo(symbol, functionTypeForAbstractMethod);
            } else {
                sAMInfo = null;
            }
            map.put((K) firRegularClass, sAMInfo == null ? NullableMap.NullValue.INSTANCE : sAMInfo);
        } else if (Intrinsics.areEqual(sAMInfo, NullableMap.NullValue.INSTANCE)) {
            sAMInfo = null;
        }
        return (SAMInfo) sAMInfo;
    }
}
