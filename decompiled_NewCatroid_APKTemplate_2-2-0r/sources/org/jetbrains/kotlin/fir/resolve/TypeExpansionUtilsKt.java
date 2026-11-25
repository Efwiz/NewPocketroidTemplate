package org.jetbrains.kotlin.fir.resolve;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirTypeAlias;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameter;
import org.jetbrains.kotlin.fir.declarations.utils.FirDeclarationUtilKt;
import org.jetbrains.kotlin.fir.resolve.substitution.AbstractConeSubstitutor;
import org.jetbrains.kotlin.fir.symbols.ConeTypeParameterLookupTag;
import org.jetbrains.kotlin.fir.symbols.FirLazyDeclarationResolverKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeAliasSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeParameterSymbol;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeDefinitelyNotNullType;
import org.jetbrains.kotlin.fir.types.ConeDynamicType;
import org.jetbrains.kotlin.fir.types.ConeErrorType;
import org.jetbrains.kotlin.fir.types.ConeFlexibleType;
import org.jetbrains.kotlin.fir.types.ConeInferenceContext;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeKotlinTypeConflictingProjection;
import org.jetbrains.kotlin.fir.types.ConeKotlinTypeProjection;
import org.jetbrains.kotlin.fir.types.ConeKotlinTypeProjectionIn;
import org.jetbrains.kotlin.fir.types.ConeKotlinTypeProjectionOut;
import org.jetbrains.kotlin.fir.types.ConeNullability;
import org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType;
import org.jetbrains.kotlin.fir.types.ConeStarProjection;
import org.jetbrains.kotlin.fir.types.ConeTypeParameterType;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.ProjectionKind;
import org.jetbrains.kotlin.fir.types.TypeComponentsKt;
import org.jetbrains.kotlin.fir.types.TypeConstructionUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;
import org.jetbrains.kotlin.fir.types.impl.ConeClassLikeTypeImpl;
import org.jetbrains.kotlin.fir.types.impl.ConeTypeParameterTypeImpl;
import org.jetbrains.kotlin.util.WeakPair;
import org.jetbrains.kotlin.util.WeakPairKt;

/* JADX INFO: compiled from: TypeExpansionUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000.\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a(\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\bH\u0002\u001a\u0014\u0010\t\u001a\u00020\u0005*\u00020\u00052\u0006\u0010\n\u001a\u00020\u0005H\u0002\u001a\u001c\u0010\u000b\u001a\u00020\u0005*\u00020\u00052\u0006\u0010\f\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u0005H\u0002\u001a,\u0010\r\u001a\u0004\u0018\u00010\u0005*\u00020\u00052\u0006\u0010\u0007\u001a\u00020\b2\u0016\b\u0002\u0010\u000e\u001a\u0010\u0012\u0004\u0012\u00020\u0003\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u000f\u001a\u0014\u0010\u0010\u001a\u0004\u0018\u00010\u0005*\u00020\u00032\u0006\u0010\u0007\u001a\u00020\b\u001a*\u0010\u0011\u001a\u00020\u0005*\u00020\u00052\u0006\u0010\u0007\u001a\u00020\b2\u0016\b\u0002\u0010\u000e\u001a\u0010\u0012\u0004\u0012\u00020\u0003\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u000f\u001a\u0012\u0010\u0011\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0007\u001a\u00020\b\u001a\u0012\u0010\u0011\u001a\u00020\u0012*\u00020\u00122\u0006\u0010\u0007\u001a\u00020\b\u001a*\u0010\u0013\u001a\u00020\u0005*\u00020\u00052\u0006\u0010\u0007\u001a\u00020\b2\u0014\u0010\u000e\u001a\u0010\u0012\u0004\u0012\u00020\u0003\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u000fH\u0002¨\u0006\u0014"}, d2 = {"mapTypeAliasArguments", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "typeAlias", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeAlias;", "abbreviatedType", "Lorg/jetbrains/kotlin/fir/types/ConeClassLikeType;", "resultingType", "useSiteSession", "Lorg/jetbrains/kotlin/fir/FirSession;", "applyAttributesFrom", "abbreviation", "applyNullabilityFrom", "session", "directExpansionType", "expandedConeType", "Lkotlin/Function1;", "fullyExpandedConeType", "fullyExpandedType", "Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;", "fullyExpandedTypeNoCache", "providers"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class TypeExpansionUtilsKt {
    public static /* synthetic */ ConeClassLikeType fullyExpandedType$default(ConeClassLikeType coneClassLikeType, FirSession firSession, Function1 function1, int i, Object obj) {
        if ((i & 2) != 0) {
            function1 = new Function1<FirTypeAlias, ConeClassLikeType>() { // from class: org.jetbrains.kotlin.fir.resolve.TypeExpansionUtilsKt.fullyExpandedType.1
                @Override // kotlin.jvm.functions.Function1
                public final ConeClassLikeType invoke(FirTypeAlias alias) {
                    Intrinsics.checkNotNullParameter(alias, "alias");
                    FirLazyDeclarationResolverKt.lazyResolveToPhase(alias, FirResolvePhase.SUPER_TYPES);
                    return FirDeclarationUtilKt.getExpandedConeType(alias);
                }
            };
        }
        return fullyExpandedType(coneClassLikeType, firSession, function1);
    }

    public static final ConeClassLikeType fullyExpandedType(ConeClassLikeType coneClassLikeType, FirSession useSiteSession, Function1<? super FirTypeAlias, ? extends ConeClassLikeType> expandedConeType) {
        Intrinsics.checkNotNullParameter(coneClassLikeType, "<this>");
        Intrinsics.checkNotNullParameter(useSiteSession, "useSiteSession");
        Intrinsics.checkNotNullParameter(expandedConeType, "expandedConeType");
        if (coneClassLikeType instanceof ConeClassLikeTypeImpl) {
            ConeClassLikeTypeImpl coneClassLikeTypeImpl = (ConeClassLikeTypeImpl) coneClassLikeType;
            WeakPair<?, ConeClassLikeType> cachedExpandedType = coneClassLikeTypeImpl.getCachedExpandedType();
            Object objComponent1 = WeakPairKt.component1(cachedExpandedType);
            ConeClassLikeType coneClassLikeType2 = (ConeClassLikeType) WeakPairKt.component2(cachedExpandedType);
            if (objComponent1 == useSiteSession && coneClassLikeType2 != null) {
                return coneClassLikeType2;
            }
            ConeClassLikeType coneClassLikeTypeFullyExpandedTypeNoCache = fullyExpandedTypeNoCache(coneClassLikeType, useSiteSession, expandedConeType);
            coneClassLikeTypeImpl.setCachedExpandedType(new WeakPair<>(useSiteSession, coneClassLikeTypeFullyExpandedTypeNoCache));
            return coneClassLikeTypeFullyExpandedTypeNoCache;
        }
        return fullyExpandedTypeNoCache(coneClassLikeType, useSiteSession, expandedConeType);
    }

    public static final ConeKotlinType fullyExpandedType(ConeKotlinType coneKotlinType, FirSession useSiteSession) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        Intrinsics.checkNotNullParameter(useSiteSession, "useSiteSession");
        if (coneKotlinType instanceof ConeDynamicType) {
            return coneKotlinType;
        }
        if (!(coneKotlinType instanceof ConeFlexibleType)) {
            return coneKotlinType instanceof ConeClassLikeType ? fullyExpandedType$default((ConeClassLikeType) coneKotlinType, useSiteSession, null, 2, null) : coneKotlinType;
        }
        ConeFlexibleType coneFlexibleType = (ConeFlexibleType) coneKotlinType;
        return new ConeFlexibleType(fullyExpandedType(coneFlexibleType.getLowerBound(), useSiteSession), fullyExpandedType(coneFlexibleType.getUpperBound(), useSiteSession));
    }

    public static final ConeSimpleKotlinType fullyExpandedType(ConeSimpleKotlinType coneSimpleKotlinType, FirSession useSiteSession) {
        Intrinsics.checkNotNullParameter(coneSimpleKotlinType, "<this>");
        Intrinsics.checkNotNullParameter(useSiteSession, "useSiteSession");
        return coneSimpleKotlinType instanceof ConeClassLikeType ? fullyExpandedType$default((ConeClassLikeType) coneSimpleKotlinType, useSiteSession, null, 2, null) : coneSimpleKotlinType;
    }

    private static final ConeClassLikeType fullyExpandedTypeNoCache(ConeClassLikeType coneClassLikeType, FirSession firSession, Function1<? super FirTypeAlias, ? extends ConeClassLikeType> function1) {
        ConeClassLikeType coneClassLikeTypeDirectExpansionType = directExpansionType(coneClassLikeType, firSession, function1);
        return coneClassLikeTypeDirectExpansionType == null ? coneClassLikeType : fullyExpandedType(coneClassLikeTypeDirectExpansionType, firSession, function1);
    }

    public static /* synthetic */ ConeClassLikeType directExpansionType$default(ConeClassLikeType coneClassLikeType, FirSession firSession, Function1 function1, int i, Object obj) {
        if ((i & 2) != 0) {
            function1 = new Function1<FirTypeAlias, ConeClassLikeType>() { // from class: org.jetbrains.kotlin.fir.resolve.TypeExpansionUtilsKt.directExpansionType.1
                @Override // kotlin.jvm.functions.Function1
                public final ConeClassLikeType invoke(FirTypeAlias alias) {
                    Intrinsics.checkNotNullParameter(alias, "alias");
                    FirLazyDeclarationResolverKt.lazyResolveToPhase(alias, FirResolvePhase.SUPER_TYPES);
                    return FirDeclarationUtilKt.getExpandedConeType(alias);
                }
            };
        }
        return directExpansionType(coneClassLikeType, firSession, function1);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final ConeClassLikeType directExpansionType(ConeClassLikeType coneClassLikeType, FirSession useSiteSession, Function1<? super FirTypeAlias, ? extends ConeClassLikeType> expandedConeType) {
        FirTypeAlias firTypeAlias;
        ConeClassLikeType coneClassLikeTypeInvoke;
        ConeClassLikeType coneClassLikeTypeApplyNullabilityFrom;
        ConeClassLikeType coneClassLikeTypeApplyAttributesFrom;
        Intrinsics.checkNotNullParameter(coneClassLikeType, "<this>");
        Intrinsics.checkNotNullParameter(useSiteSession, "useSiteSession");
        Intrinsics.checkNotNullParameter(expandedConeType, "expandedConeType");
        FirClassLikeSymbol<?> symbol = LookupTagUtilsKt.toSymbol(coneClassLikeType.getLookupTag(), useSiteSession);
        FirTypeAliasSymbol firTypeAliasSymbol = symbol instanceof FirTypeAliasSymbol ? (FirTypeAliasSymbol) symbol : null;
        if (firTypeAliasSymbol == null || (coneClassLikeTypeInvoke = expandedConeType.invoke((firTypeAlias = (FirTypeAlias) firTypeAliasSymbol.getFir()))) == null || (coneClassLikeTypeApplyNullabilityFrom = applyNullabilityFrom(coneClassLikeTypeInvoke, useSiteSession, coneClassLikeType)) == null || (coneClassLikeTypeApplyAttributesFrom = applyAttributesFrom(coneClassLikeTypeApplyNullabilityFrom, coneClassLikeType)) == null) {
            return null;
        }
        if (coneClassLikeTypeApplyAttributesFrom.getTypeArguments().length == 0) {
            return coneClassLikeTypeApplyAttributesFrom;
        }
        ConeKotlinType coneKotlinTypeMapTypeAliasArguments = mapTypeAliasArguments(firTypeAlias, coneClassLikeType, coneClassLikeTypeApplyAttributesFrom, useSiteSession);
        if (coneKotlinTypeMapTypeAliasArguments instanceof ConeClassLikeType) {
            return (ConeClassLikeType) coneKotlinTypeMapTypeAliasArguments;
        }
        return null;
    }

    private static final ConeClassLikeType applyNullabilityFrom(ConeClassLikeType coneClassLikeType, FirSession firSession, ConeClassLikeType coneClassLikeType2) {
        return ConeTypeUtilsKt.isMarkedNullable(coneClassLikeType2) ? (ConeClassLikeType) TypeUtilsKt.withNullability$default(coneClassLikeType, ConeNullability.NULLABLE, TypeComponentsKt.getTypeContext(firSession), null, false, 12, null) : coneClassLikeType;
    }

    private static final ConeClassLikeType applyAttributesFrom(ConeClassLikeType coneClassLikeType, ConeClassLikeType coneClassLikeType2) {
        return (ConeClassLikeType) TypeUtilsKt.withAttributes(coneClassLikeType, coneClassLikeType.getAttributes().add(coneClassLikeType2.getAttributes()));
    }

    private static final ConeKotlinType mapTypeAliasArguments(FirTypeAlias firTypeAlias, ConeClassLikeType coneClassLikeType, ConeClassLikeType coneClassLikeType2, FirSession firSession) {
        if (!firTypeAlias.getTypeParameters().isEmpty() && coneClassLikeType.getTypeArguments().length == 0) {
            return TypeConstructionUtilsKt.constructClassType$default(coneClassLikeType2.getLookupTag(), new ConeTypeProjection[0], ConeTypeUtilsKt.isNullable(coneClassLikeType2), null, 4, null);
        }
        List<FirTypeParameter> typeParameters = firTypeAlias.getTypeParameters();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(typeParameters, 10));
        Iterator<T> it = typeParameters.iterator();
        while (it.hasNext()) {
            arrayList.add(((FirTypeParameter) it.next()).getSymbol());
        }
        final Map map = MapsKt.toMap(CollectionsKt.zip(arrayList, coneClassLikeType.getTypeArguments()));
        final ConeInferenceContext typeContext = TypeComponentsKt.getTypeContext(firSession);
        return new AbstractConeSubstitutor(map, typeContext) { // from class: org.jetbrains.kotlin.fir.resolve.TypeExpansionUtilsKt$mapTypeAliasArguments$substitutor$1
            final /* synthetic */ Map<FirTypeParameterSymbol, ConeTypeProjection> $typeAliasMap;

            /* JADX INFO: compiled from: TypeExpansionUtils.kt */
            @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
            public /* synthetic */ class WhenMappings {
                public static final /* synthetic */ int[] $EnumSwitchMapping$0;

                static {
                    int[] iArr = new int[ProjectionKind.values().length];
                    try {
                        iArr[ProjectionKind.STAR.ordinal()] = 1;
                    } catch (NoSuchFieldError unused) {
                    }
                    try {
                        iArr[ProjectionKind.IN.ordinal()] = 2;
                    } catch (NoSuchFieldError unused2) {
                    }
                    try {
                        iArr[ProjectionKind.OUT.ordinal()] = 3;
                    } catch (NoSuchFieldError unused3) {
                    }
                    try {
                        iArr[ProjectionKind.INVARIANT.ordinal()] = 4;
                    } catch (NoSuchFieldError unused4) {
                    }
                    $EnumSwitchMapping$0 = iArr;
                }
            }

            @Override // org.jetbrains.kotlin.fir.resolve.substitution.AbstractConeSubstitutor
            public ConeKotlinType substituteType(ConeKotlinType type) {
                Intrinsics.checkNotNullParameter(type, "type");
                return null;
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(typeContext);
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r1v2, types: [T, org.jetbrains.kotlin.fir.types.ConeKotlinType] */
            @Override // org.jetbrains.kotlin.fir.resolve.substitution.AbstractConeSubstitutor
            public ConeTypeProjection substituteArgument(ConeTypeProjection projection, int index) {
                ConeKotlinType type;
                ConeTypeParameterLookupTag lookupTag;
                FirTypeParameterSymbol symbol;
                T tWithAttributes;
                Intrinsics.checkNotNullParameter(projection, "projection");
                ConeKotlinTypeProjection coneKotlinTypeProjection = projection instanceof ConeKotlinTypeProjection ? (ConeKotlinTypeProjection) projection : null;
                if (coneKotlinTypeProjection == null || (type = coneKotlinTypeProjection.getType()) == null) {
                    return null;
                }
                ConeTypeParameterType coneTypeParameterType = type instanceof ConeTypeParameterType ? (ConeTypeParameterType) type : null;
                if (coneTypeParameterType == null || (lookupTag = coneTypeParameterType.getLookupTag()) == null || (symbol = lookupTag.getSymbol()) == null) {
                    return super.substituteArgument(projection, index);
                }
                ConeTypeProjection coneTypeProjection = this.$typeAliasMap.get(symbol);
                if (coneTypeProjection == null) {
                    return super.substituteArgument(projection, index);
                }
                Ref.ObjectRef objectRef = new Ref.ObjectRef();
                ConeKotlinTypeProjection coneKotlinTypeProjection2 = coneTypeProjection instanceof ConeKotlinTypeProjection ? (ConeKotlinTypeProjection) coneTypeProjection : null;
                objectRef.element = updateNullabilityIfNeeded(coneKotlinTypeProjection2 != null ? coneKotlinTypeProjection2.getType() : null, type);
                ConeKotlinType coneKotlinType = (ConeKotlinType) objectRef.element;
                if (coneKotlinType instanceof ConeErrorType ? true : coneKotlinType instanceof ConeClassLikeTypeImpl ? true : coneKotlinType instanceof ConeDefinitelyNotNullType ? true : coneKotlinType instanceof ConeTypeParameterTypeImpl ? true : coneKotlinType instanceof ConeFlexibleType) {
                    tWithAttributes = TypeUtilsKt.withAttributes((ConeKotlinType) objectRef.element, type.getAttributes().add(((ConeKotlinType) objectRef.element).getAttributes()));
                } else {
                    if (coneKotlinType == null) {
                        return coneTypeProjection;
                    }
                    tWithAttributes = (ConeKotlinType) objectRef.element;
                }
                objectRef.element = tWithAttributes;
                if (coneTypeProjection.getKind() == projection.getKind()) {
                    return substituteArgument$convertProjectionKindToConeTypeProjection(objectRef, coneTypeProjection.getKind());
                }
                if (coneTypeProjection.getKind() == ProjectionKind.STAR || projection.getKind() == ProjectionKind.STAR) {
                    return ConeStarProjection.INSTANCE;
                }
                if (coneTypeProjection.getKind() == ProjectionKind.INVARIANT) {
                    return substituteArgument$convertProjectionKindToConeTypeProjection(objectRef, projection.getKind());
                }
                if (projection.getKind() == ProjectionKind.INVARIANT) {
                    return substituteArgument$convertProjectionKindToConeTypeProjection(objectRef, coneTypeProjection.getKind());
                }
                return new ConeKotlinTypeConflictingProjection((ConeKotlinType) objectRef.element);
            }

            private static final ConeTypeProjection substituteArgument$convertProjectionKindToConeTypeProjection(Ref.ObjectRef<ConeKotlinType> objectRef, ProjectionKind projectionKind) {
                int i = WhenMappings.$EnumSwitchMapping$0[projectionKind.ordinal()];
                if (i == 1) {
                    return ConeStarProjection.INSTANCE;
                }
                if (i == 2) {
                    return new ConeKotlinTypeProjectionIn(objectRef.element);
                }
                if (i == 3) {
                    return new ConeKotlinTypeProjectionOut(objectRef.element);
                }
                if (i == 4) {
                    return objectRef.element;
                }
                throw new NoWhenBranchMatchedException();
            }
        }.substituteOrSelf(coneClassLikeType2);
    }

    public static final ConeClassLikeType fullyExpandedConeType(FirTypeAlias firTypeAlias, FirSession useSiteSession) {
        Intrinsics.checkNotNullParameter(firTypeAlias, "<this>");
        Intrinsics.checkNotNullParameter(useSiteSession, "useSiteSession");
        ConeClassLikeType expandedConeType = FirDeclarationUtilKt.getExpandedConeType(firTypeAlias);
        if (expandedConeType != null) {
            return fullyExpandedType$default(expandedConeType, useSiteSession, null, 2, null);
        }
        return null;
    }
}
