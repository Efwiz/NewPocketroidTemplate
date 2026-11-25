package org.jetbrains.kotlin.fir.scopes.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRef;
import org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutor;
import org.jetbrains.kotlin.fir.resolve.substitution.SubstitutorsKt;
import org.jetbrains.kotlin.fir.types.impl.ConeTypeParameterTypeImpl;

/* JADX INFO: compiled from: FirAbstractOverrideChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001a \u0010\u0000\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"buildSubstitutorForOverridesCheck", "Lorg/jetbrains/kotlin/fir/resolve/substitution/ConeSubstitutor;", "overrideCandidate", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "baseDeclaration", "useSiteSession", "Lorg/jetbrains/kotlin/fir/FirSession;", "providers"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirAbstractOverrideCheckerKt {
    public static final ConeSubstitutor buildSubstitutorForOverridesCheck(FirCallableDeclaration overrideCandidate, FirCallableDeclaration baseDeclaration, FirSession useSiteSession) {
        Intrinsics.checkNotNullParameter(overrideCandidate, "overrideCandidate");
        Intrinsics.checkNotNullParameter(baseDeclaration, "baseDeclaration");
        Intrinsics.checkNotNullParameter(useSiteSession, "useSiteSession");
        if (overrideCandidate.getTypeParameters().size() != baseDeclaration.getTypeParameters().size()) {
            return null;
        }
        if (baseDeclaration.getTypeParameters().isEmpty()) {
            return ConeSubstitutor.Empty.INSTANCE;
        }
        List<FirTypeParameterRef> typeParameters = baseDeclaration.getTypeParameters();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(typeParameters, 10));
        Iterator<T> it = typeParameters.iterator();
        while (it.hasNext()) {
            arrayList.add(new ConeTypeParameterTypeImpl(((FirTypeParameterRef) it.next()).getSymbol().getLookupTag(), false, null, 4, null));
        }
        ArrayList arrayList2 = arrayList;
        List<FirTypeParameterRef> typeParameters2 = overrideCandidate.getTypeParameters();
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(typeParameters2, 10));
        Iterator<T> it2 = typeParameters2.iterator();
        while (it2.hasNext()) {
            arrayList3.add(((FirTypeParameterRef) it2.next()).getSymbol());
        }
        return SubstitutorsKt.substitutorByMap(MapsKt.toMap(CollectionsKt.zip(arrayList3, arrayList2)), useSiteSession);
    }
}
