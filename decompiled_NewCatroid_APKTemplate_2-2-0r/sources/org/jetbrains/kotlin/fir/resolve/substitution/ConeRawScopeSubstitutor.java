package org.jetbrains.kotlin.fir.resolve.substitution;

import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.resolve.LookupTagUtilsKt;
import org.jetbrains.kotlin.fir.resolve.TypeExpansionUtilsKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeFlexibleType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeRawType;
import org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType;
import org.jetbrains.kotlin.fir.types.ConeStarProjection;
import org.jetbrains.kotlin.fir.types.ConeTypeParameterType;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.fir.types.ConeTypeProjectionKt;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeComponentsKt;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.StandardClassIds;

/* JADX INFO: compiled from: Substitutors.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0013\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\bH\u0096\u0002J\b\u0010\t\u001a\u00020\nH\u0016J\u0012\u0010\u000b\u001a\u0004\u0018\u00010\f2\u0006\u0010\r\u001a\u00020\fH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/substitution/ConeRawScopeSubstitutor;", "Lorg/jetbrains/kotlin/fir/resolve/substitution/AbstractConeSubstitutor;", "useSiteSession", "Lorg/jetbrains/kotlin/fir/FirSession;", "(Lorg/jetbrains/kotlin/fir/FirSession;)V", Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "substituteType", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", ModuleXmlParser.TYPE, "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConeRawScopeSubstitutor extends AbstractConeSubstitutor {
    private final FirSession useSiteSession;

    public int hashCode() {
        return 0;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ConeRawScopeSubstitutor(FirSession useSiteSession) {
        super(TypeComponentsKt.getTypeContext(useSiteSession));
        Intrinsics.checkNotNullParameter(useSiteSession, "useSiteSession");
        this.useSiteSession = useSiteSession;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.substitution.AbstractConeSubstitutor
    public ConeKotlinType substituteType(ConeKotlinType type) {
        ConeSimpleKotlinType lowerBound;
        ConeSimpleKotlinType upperBound;
        ConeStarProjection typeProjection;
        Intrinsics.checkNotNullParameter(type, "type");
        if (type instanceof ConeTypeParameterType) {
            ConeTypeProjection coneTypeProjection = TypeUtilsKt.getProjectionsForRawType(CollectionsKt.listOf(((ConeTypeParameterType) type).getLookupTag().getSymbol()), this.useSiteSession)[0];
            Intrinsics.checkNotNull(coneTypeProjection, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.types.ConeKotlinType");
            return substituteOrSelf((ConeKotlinType) coneTypeProjection);
        }
        if (type instanceof ConeClassLikeType) {
            if (!(type.getTypeArguments().length == 0)) {
                ConeClassLikeType coneClassLikeType = (ConeClassLikeType) type;
                if (Intrinsics.areEqual(coneClassLikeType.getLookupTag().getClassId(), StandardClassIds.INSTANCE.getArray())) {
                    ConeTypeProjection coneTypeProjection2 = type.getTypeArguments()[0];
                    ConeKotlinType type2 = ConeTypeProjectionKt.getType(coneTypeProjection2);
                    ConeKotlinType coneKotlinTypeSubstituteOrSelf = type2 != null ? substituteOrSelf(type2) : null;
                    ConeTypeProjection[] coneTypeProjectionArr = new ConeTypeProjection[1];
                    if (coneKotlinTypeSubstituteOrSelf == null || (typeProjection = ConeTypeUtilsKt.toTypeProjection(coneKotlinTypeSubstituteOrSelf, coneTypeProjection2.getKind())) == null) {
                        typeProjection = ConeStarProjection.INSTANCE;
                    }
                    coneTypeProjectionArr[0] = typeProjection;
                    return ConeTypeUtilsKt.withArguments(coneClassLikeType, coneTypeProjectionArr);
                }
                FirRegularClassSymbol firRegularClassSymbol = LookupTagUtilsKt.toFirRegularClassSymbol(TypeExpansionUtilsKt.fullyExpandedType$default(coneClassLikeType, this.useSiteSession, null, 2, null).getLookupTag(), this.useSiteSession);
                if (firRegularClassSymbol == null) {
                    return null;
                }
                return ConeRawType.INSTANCE.create(ConeTypeUtilsKt.withArguments(coneClassLikeType, TypeUtilsKt.getProjectionsForRawType(firRegularClassSymbol.getTypeParameterSymbols(), this.useSiteSession)), ConeTypeUtilsKt.replaceArgumentsWithStarProjections(coneClassLikeType));
            }
        }
        if (!(type instanceof ConeFlexibleType)) {
            return null;
        }
        ConeFlexibleType coneFlexibleType = (ConeFlexibleType) type;
        ConeKotlinType coneKotlinTypeSubstituteOrNull = substituteOrNull(coneFlexibleType.getLowerBound());
        ConeKotlinType coneKotlinTypeSubstituteOrNull2 = substituteOrNull(coneFlexibleType.getUpperBound());
        if (coneKotlinTypeSubstituteOrNull == null && coneKotlinTypeSubstituteOrNull2 == null) {
            return null;
        }
        if (coneKotlinTypeSubstituteOrNull == null || (lowerBound = ConeTypeUtilsKt.lowerBoundIfFlexible(coneKotlinTypeSubstituteOrNull)) == null) {
            lowerBound = coneFlexibleType.getLowerBound();
        }
        if (coneKotlinTypeSubstituteOrNull2 == null || (upperBound = ConeTypeUtilsKt.upperBoundIfFlexible(coneKotlinTypeSubstituteOrNull2)) == null) {
            upperBound = coneFlexibleType.getUpperBound();
        }
        if ((coneKotlinTypeSubstituteOrNull instanceof ConeRawType) || (coneKotlinTypeSubstituteOrNull2 instanceof ConeRawType)) {
            return ConeRawType.INSTANCE.create(lowerBound, upperBound);
        }
        return new ConeFlexibleType(lowerBound, upperBound);
    }

    public boolean equals(Object other) {
        return other instanceof ConeRawScopeSubstitutor;
    }
}
