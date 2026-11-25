package org.jetbrains.kotlin.fir.resolve.substitution;

import java.util.Collection;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.symbols.ConeTypeParameterLookupTag;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeParameterSymbol;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeStubTypeConstructor;
import org.jetbrains.kotlin.fir.types.ConeTypeContext;
import org.jetbrains.kotlin.fir.types.ConeTypeParameterType;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.types.model.TypeConstructorMarker;

/* JADX INFO: compiled from: Substitutors.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000<\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\u001a*\u0010\u0000\u001a\u00020\u00012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u00032\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t\u001a\"\u0010\n\u001a\u00020\u00012\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u00050\u00032\u0006\u0010\r\u001a\u00020\u000e\u001a\u0012\u0010\u000f\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0010\u001a\u00020\u0001\u001a \u0010\u0011\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0012\u001a\u00020\u00072\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u0014¨\u0006\u0016"}, d2 = {"createTypeSubstitutorByTypeConstructor", "Lorg/jetbrains/kotlin/fir/resolve/substitution/ConeSubstitutor;", "map", "", "Lorg/jetbrains/kotlin/types/model/TypeConstructorMarker;", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "context", "Lorg/jetbrains/kotlin/fir/types/ConeTypeContext;", "approximateIntegerLiterals", "", "substitutorByMap", SchemaSymbols.ATTVAL_SUBSTITUTION, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirTypeParameterSymbol;", "useSiteSession", "Lorg/jetbrains/kotlin/fir/FirSession;", "chain", "other", "replaceStubsAndTypeVariablesToErrors", "typeContext", "stubTypesToReplace", "", "Lorg/jetbrains/kotlin/fir/types/ConeStubTypeConstructor;", "providers"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class SubstitutorsKt {
    public static final ConeSubstitutor chain(ConeSubstitutor coneSubstitutor, ConeSubstitutor other) {
        Intrinsics.checkNotNullParameter(coneSubstitutor, "<this>");
        Intrinsics.checkNotNullParameter(other, "other");
        if (Intrinsics.areEqual(coneSubstitutor, ConeSubstitutor.Empty.INSTANCE)) {
            return other;
        }
        return Intrinsics.areEqual(other, ConeSubstitutor.Empty.INSTANCE) ? coneSubstitutor : new ChainedSubstitutor(coneSubstitutor, other);
    }

    public static final ConeSubstitutor createTypeSubstitutorByTypeConstructor(Map<TypeConstructorMarker, ? extends ConeKotlinType> map, ConeTypeContext context, boolean z) {
        Intrinsics.checkNotNullParameter(map, "map");
        Intrinsics.checkNotNullParameter(context, "context");
        return map.isEmpty() ? ConeSubstitutor.Empty.INSTANCE : new ConeTypeSubstitutorByTypeConstructor(map, context, z);
    }

    public static final ConeSubstitutor replaceStubsAndTypeVariablesToErrors(ConeSubstitutor coneSubstitutor, ConeTypeContext typeContext, Collection<ConeStubTypeConstructor> stubTypesToReplace) {
        Intrinsics.checkNotNullParameter(coneSubstitutor, "<this>");
        Intrinsics.checkNotNullParameter(typeContext, "typeContext");
        Intrinsics.checkNotNullParameter(stubTypesToReplace, "stubTypesToReplace");
        return new ChainedSubstitutor(coneSubstitutor, new ConeStubAndTypeVariableToErrorTypeSubstitutor(typeContext, stubTypesToReplace));
    }

    public static final ConeSubstitutor substitutorByMap(Map<FirTypeParameterSymbol, ? extends ConeKotlinType> substitution, FirSession useSiteSession) {
        ConeTypeParameterLookupTag lookupTag;
        Intrinsics.checkNotNullParameter(substitution, "substitution");
        Intrinsics.checkNotNullParameter(useSiteSession, "useSiteSession");
        if (!substitution.isEmpty()) {
            for (Map.Entry<FirTypeParameterSymbol, ? extends ConeKotlinType> entry : substitution.entrySet()) {
                FirTypeParameterSymbol key = entry.getKey();
                ConeKotlinType value = entry.getValue();
                FirTypeParameterSymbol typeParameterSymbol = null;
                ConeTypeParameterType coneTypeParameterType = value instanceof ConeTypeParameterType ? (ConeTypeParameterType) value : null;
                if (coneTypeParameterType != null && (lookupTag = coneTypeParameterType.getLookupTag()) != null) {
                    typeParameterSymbol = lookupTag.getTypeParameterSymbol();
                }
                if (!Intrinsics.areEqual(typeParameterSymbol, key) || ConeTypeUtilsKt.isMarkedNullable(value)) {
                    return new ConeSubstitutorByMap(substitution, useSiteSession);
                }
            }
        }
        return ConeSubstitutor.Empty.INSTANCE;
    }
}
