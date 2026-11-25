package org.jetbrains.kotlin.fir.types;

import java.util.LinkedHashMap;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutor;
import org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutorByMap;
import org.jetbrains.kotlin.fir.resolve.substitution.SubstitutorsKt;
import org.jetbrains.kotlin.fir.symbols.ConeClassifierLookupTag;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeParameterSymbol;
import org.jetbrains.kotlin.types.AbstractTypeChecker;

/* JADX INFO: compiled from: ExpectActualUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\u001a\"\u0010\u0000\u001a\u00020\u00012\b\u0010\u0002\u001a\u0004\u0018\u00010\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0005\u001a\u00020\u0006\u001a6\u0010\u0007\u001a\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010\r\u001a\u00020\u00062\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\b¨\u0006\u000f"}, d2 = {"areCompatibleExpectActualTypes", "", "expectedType", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "actualType", "actualSession", "Lorg/jetbrains/kotlin/fir/FirSession;", "createExpectActualTypeParameterSubstitutor", "Lorg/jetbrains/kotlin/fir/resolve/substitution/ConeSubstitutor;", "expectedTypeParameters", "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirTypeParameterSymbol;", "actualTypeParameters", "useSiteSession", "parentSubstitutor", "providers"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ExpectActualUtilsKt {
    public static /* synthetic */ ConeSubstitutor createExpectActualTypeParameterSubstitutor$default(List list, List list2, FirSession firSession, ConeSubstitutor coneSubstitutor, int i, Object obj) {
        if ((i & 8) != 0) {
            coneSubstitutor = null;
        }
        return createExpectActualTypeParameterSubstitutor(list, list2, firSession, coneSubstitutor);
    }

    public static final ConeSubstitutor createExpectActualTypeParameterSubstitutor(List<FirTypeParameterSymbol> expectedTypeParameters, List<FirTypeParameterSymbol> actualTypeParameters, FirSession useSiteSession, ConeSubstitutor coneSubstitutor) {
        Intrinsics.checkNotNullParameter(expectedTypeParameters, "expectedTypeParameters");
        Intrinsics.checkNotNullParameter(actualTypeParameters, "actualTypeParameters");
        Intrinsics.checkNotNullParameter(useSiteSession, "useSiteSession");
        List<Pair> listZip = CollectionsKt.zip(expectedTypeParameters, actualTypeParameters);
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(listZip, 10)), 16));
        for (Pair pair : listZip) {
            Pair pair2 = TuplesKt.to((FirTypeParameterSymbol) pair.component1(), TypeConstructionUtilsKt.constructType$default((ConeClassifierLookupTag) ((FirTypeParameterSymbol) pair.component2()).getLookupTag(), new ConeTypeProjection[0], false, (ConeAttributes) null, 4, (Object) null));
            linkedHashMap.put(pair2.getFirst(), pair2.getSecond());
        }
        ConeSubstitutorByMap coneSubstitutorByMap = new ConeSubstitutorByMap(linkedHashMap, useSiteSession);
        if (coneSubstitutor == null) {
            return coneSubstitutorByMap;
        }
        return SubstitutorsKt.chain(coneSubstitutorByMap, coneSubstitutor);
    }

    public static final boolean areCompatibleExpectActualTypes(ConeKotlinType coneKotlinType, ConeKotlinType coneKotlinType2, FirSession actualSession) {
        Intrinsics.checkNotNullParameter(actualSession, "actualSession");
        if (coneKotlinType == null) {
            return coneKotlinType2 == null;
        }
        if (coneKotlinType2 == null) {
            return false;
        }
        return AbstractTypeChecker.equalTypes$default(AbstractTypeChecker.INSTANCE, TypeComponentsKt.getTypeContext(actualSession), coneKotlinType, coneKotlinType2, false, 8, null);
    }
}
