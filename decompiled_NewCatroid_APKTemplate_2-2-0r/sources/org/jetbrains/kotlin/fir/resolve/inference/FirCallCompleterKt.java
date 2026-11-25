package org.jetbrains.kotlin.fir.resolve.inference;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirReceiverParameter;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRef;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.resolve.calls.Candidate;
import org.jetbrains.kotlin.fir.resolve.calls.TypeArgumentMapping;
import org.jetbrains.kotlin.fir.types.ConeDefinitelyNotNullType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeParameterType;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;

/* JADX INFO: compiled from: FirCallCompleter.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\f\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u0000\u001a\f\u0010\u0000\u001a\u00020\u0001*\u00020\u0003H\u0002\u001a\f\u0010\u0004\u001a\u00020\u0005*\u00020\u0006H\u0002¨\u0006\u0007"}, d2 = {"isFunctionForExpectTypeFromCastFeature", "", "Lorg/jetbrains/kotlin/fir/declarations/FirFunction;", "Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;", "unwrap", "Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "resolve"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirCallCompleterKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean isFunctionForExpectTypeFromCastFeature(Candidate candidate) {
        if (!Intrinsics.areEqual(candidate.getTypeArgumentMapping(), TypeArgumentMapping.NoExplicitArguments.INSTANCE)) {
            return false;
        }
        FirDeclaration fir = candidate.getSymbol().getFir();
        FirFunction firFunction = fir instanceof FirFunction ? (FirFunction) fir : null;
        if (firFunction == null) {
            return false;
        }
        return isFunctionForExpectTypeFromCastFeature(firFunction);
    }

    public static final boolean isFunctionForExpectTypeFromCastFeature(FirFunction firFunction) {
        FirReceiverParameter receiverParameter;
        FirTypeRef typeRef;
        Intrinsics.checkNotNullParameter(firFunction, "<this>");
        FirTypeParameterRef firTypeParameterRef = (FirTypeParameterRef) CollectionsKt.singleOrNull((List) firFunction.getTypeParameters());
        if (firTypeParameterRef == null) {
            return false;
        }
        FirTypeRef returnTypeRef = firFunction.getReturnTypeRef();
        FirResolvedTypeRef firResolvedTypeRef = returnTypeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) returnTypeRef : null;
        ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
        if (!(type instanceof ConeKotlinType)) {
            type = null;
        }
        if (type == null) {
            return false;
        }
        ConeSimpleKotlinType coneSimpleKotlinTypeUnwrap = unwrap(type);
        ConeTypeParameterType coneTypeParameterType = coneSimpleKotlinTypeUnwrap instanceof ConeTypeParameterType ? (ConeTypeParameterType) coneSimpleKotlinTypeUnwrap : null;
        if (!Intrinsics.areEqual(coneTypeParameterType != null ? coneTypeParameterType.getLookupTag() : null, firTypeParameterRef.getSymbol().getLookupTag())) {
            return false;
        }
        List<FirValueParameter> valueParameters = firFunction.getValueParameters();
        if ((valueParameters instanceof Collection) && valueParameters.isEmpty()) {
            receiverParameter = firFunction.getReceiverParameter();
            if (receiverParameter != null) {
            }
            return true;
        }
        Iterator<T> it = valueParameters.iterator();
        while (it.hasNext()) {
            if (isFunctionForExpectTypeFromCastFeature$isBadType(((FirValueParameter) it.next()).getReturnTypeRef(), firTypeParameterRef)) {
            }
        }
        receiverParameter = firFunction.getReceiverParameter();
        if (receiverParameter != null || (typeRef = receiverParameter.getTypeRef()) == null || !isFunctionForExpectTypeFromCastFeature$isBadType(typeRef, firTypeParameterRef)) {
            return true;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ConeSimpleKotlinType unwrap(ConeKotlinType coneKotlinType) {
        ConeSimpleKotlinType coneSimpleKotlinTypeLowerBoundIfFlexible = ConeTypeUtilsKt.lowerBoundIfFlexible(coneKotlinType);
        return coneSimpleKotlinTypeLowerBoundIfFlexible instanceof ConeDefinitelyNotNullType ? unwrap(((ConeDefinitelyNotNullType) coneSimpleKotlinTypeLowerBoundIfFlexible).getOriginal()) : coneSimpleKotlinTypeLowerBoundIfFlexible;
    }

    private static final boolean isFunctionForExpectTypeFromCastFeature$isBadType(FirTypeRef firTypeRef, final FirTypeParameterRef firTypeParameterRef) {
        FirResolvedTypeRef firResolvedTypeRef = firTypeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) firTypeRef : null;
        ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
        ConeKotlinType coneKotlinType = type instanceof ConeKotlinType ? type : null;
        boolean z = false;
        if (coneKotlinType != null && !ConeTypeUtilsKt.contains(coneKotlinType, new Function1<ConeKotlinType, Boolean>() { // from class: org.jetbrains.kotlin.fir.resolve.inference.FirCallCompleterKt$isFunctionForExpectTypeFromCastFeature$isBadType$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(ConeKotlinType it) {
                Intrinsics.checkNotNullParameter(it, "it");
                ConeSimpleKotlinType coneSimpleKotlinTypeUnwrap = FirCallCompleterKt.unwrap(it);
                ConeTypeParameterType coneTypeParameterType = coneSimpleKotlinTypeUnwrap instanceof ConeTypeParameterType ? (ConeTypeParameterType) coneSimpleKotlinTypeUnwrap : null;
                return Boolean.valueOf(Intrinsics.areEqual(coneTypeParameterType != null ? coneTypeParameterType.getLookupTag() : null, firTypeParameterRef.getSymbol().getLookupTag()));
            }
        })) {
            z = true;
        }
        return true ^ z;
    }
}
