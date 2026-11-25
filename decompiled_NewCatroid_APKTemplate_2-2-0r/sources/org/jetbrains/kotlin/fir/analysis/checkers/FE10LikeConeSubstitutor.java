package org.jetbrains.kotlin.fir.analysis.checkers;

import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.resolve.LookupTagUtilsKt;
import org.jetbrains.kotlin.fir.resolve.substitution.AbstractConeSubstitutor;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeParameterSymbol;
import org.jetbrains.kotlin.fir.types.ConeAttributes;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeStarProjection;
import org.jetbrains.kotlin.fir.types.ConeTypeParameterType;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.fir.types.ConeTypeProjectionKt;
import org.jetbrains.kotlin.fir.types.ProjectionKind;
import org.jetbrains.kotlin.fir.types.TypeComponentsKt;
import org.jetbrains.kotlin.fir.types.TypeConstructionUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;
import org.jetbrains.kotlin.name.StandardClassIds;

/* JADX INFO: compiled from: FirUpperBoundViolatedHelpers.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\b\u0004\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0000\u0018\u00002\u00020\u0001B+\b\u0016\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tB!\u0012\u0012\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00060\u000b\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\fJ\u001a\u0010\r\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u000e\u001a\u00020\u00062\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\u0012\u0010\u0011\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u0013\u001a\u00020\u0012H\u0016J\u0014\u0010\u0014\u001a\u00020\u0012*\u00020\u00122\u0006\u0010\u000e\u001a\u00020\u0006H\u0002R\u001a\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00060\u000bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/FE10LikeConeSubstitutor;", "Lorg/jetbrains/kotlin/fir/resolve/substitution/AbstractConeSubstitutor;", "typeParameters", "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirTypeParameterSymbol;", "typeArguments", "Lorg/jetbrains/kotlin/fir/types/ConeTypeProjection;", "useSiteSession", "Lorg/jetbrains/kotlin/fir/FirSession;", "(Ljava/util/List;Ljava/util/List;Lorg/jetbrains/kotlin/fir/FirSession;)V", SchemaSymbols.ATTVAL_SUBSTITUTION, "", "(Ljava/util/Map;Lorg/jetbrains/kotlin/fir/FirSession;)V", "substituteArgument", "projection", "index", "", "substituteType", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", ModuleXmlParser.TYPE, "withProjection", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FE10LikeConeSubstitutor extends AbstractConeSubstitutor {
    private final Map<FirTypeParameterSymbol, ConeTypeProjection> substitution;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public FE10LikeConeSubstitutor(Map<FirTypeParameterSymbol, ? extends ConeTypeProjection> substitution, FirSession useSiteSession) {
        super(TypeComponentsKt.getTypeContext(useSiteSession));
        Intrinsics.checkNotNullParameter(substitution, "substitution");
        Intrinsics.checkNotNullParameter(useSiteSession, "useSiteSession");
        this.substitution = substitution;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public FE10LikeConeSubstitutor(List<FirTypeParameterSymbol> typeParameters, List<? extends ConeTypeProjection> typeArguments, FirSession useSiteSession) {
        this(MapsKt.toMap(CollectionsKt.zip(typeParameters, typeArguments)), useSiteSession);
        Intrinsics.checkNotNullParameter(typeParameters, "typeParameters");
        Intrinsics.checkNotNullParameter(typeArguments, "typeArguments");
        Intrinsics.checkNotNullParameter(useSiteSession, "useSiteSession");
    }

    @Override // org.jetbrains.kotlin.fir.resolve.substitution.AbstractConeSubstitutor
    public ConeKotlinType substituteType(ConeKotlinType type) {
        ConeTypeProjection coneTypeProjection;
        ConeKotlinType coneKotlinTypeWithCombinedAttributesFrom;
        Intrinsics.checkNotNullParameter(type, "type");
        if (!(type instanceof ConeTypeParameterType) || (coneTypeProjection = this.substitution.get(((ConeTypeParameterType) type).getLookupTag().getSymbol())) == null) {
            return null;
        }
        if (ConeTypeProjectionKt.isStarProjection(coneTypeProjection)) {
            return withProjection(TypeConstructionUtilsKt.constructClassLikeType$default(StandardClassIds.INSTANCE.getAny(), new ConeTypeProjection[0], true, null, 4, null), coneTypeProjection);
        }
        ConeKotlinType type2 = ConeTypeProjectionKt.getType(coneTypeProjection);
        Intrinsics.checkNotNull(type2);
        ConeKotlinType coneKotlinTypeUpdateNullabilityIfNeeded = updateNullabilityIfNeeded(type2, type);
        if (coneKotlinTypeUpdateNullabilityIfNeeded == null || (coneKotlinTypeWithCombinedAttributesFrom = LookupTagUtilsKt.withCombinedAttributesFrom(coneKotlinTypeUpdateNullabilityIfNeeded, type)) == null) {
            return null;
        }
        return withProjection(coneKotlinTypeWithCombinedAttributesFrom, coneTypeProjection);
    }

    private final ConeKotlinType withProjection(ConeKotlinType coneKotlinType, ConeTypeProjection coneTypeProjection) {
        return coneTypeProjection.getKind() == ProjectionKind.INVARIANT ? coneKotlinType : TypeUtilsKt.withAttributes(coneKotlinType, ConeAttributes.INSTANCE.create(CollectionsKt.listOf(new OriginalProjectionTypeAttribute(coneTypeProjection))));
    }

    @Override // org.jetbrains.kotlin.fir.resolve.substitution.AbstractConeSubstitutor
    public ConeTypeProjection substituteArgument(ConeTypeProjection projection, int index) {
        ConeKotlinType data;
        Intrinsics.checkNotNullParameter(projection, "projection");
        ConeTypeProjection coneTypeProjectionSubstituteArgument = super.substituteArgument(projection, index);
        if (coneTypeProjectionSubstituteArgument == null || ConeTypeProjectionKt.isStarProjection(coneTypeProjectionSubstituteArgument)) {
            return null;
        }
        ConeKotlinType type = ConeTypeProjectionKt.getType(coneTypeProjectionSubstituteArgument);
        Intrinsics.checkNotNull(type);
        OriginalProjectionTypeAttribute originalProjection = FirUpperBoundViolatedHelpersKt.getOriginalProjection(type.getAttributes());
        if (originalProjection == null || (data = originalProjection.getData()) == null) {
            data = type;
        }
        ProjectionKind kind = data.getKind();
        if (kind == ProjectionKind.STAR) {
            return ConeStarProjection.INSTANCE;
        }
        if (kind == ProjectionKind.INVARIANT || kind == projection.getKind()) {
            return coneTypeProjectionSubstituteArgument;
        }
        if (projection.getKind() == ProjectionKind.INVARIANT) {
            return wrapProjection(data, type);
        }
        return ConeStarProjection.INSTANCE;
    }
}
