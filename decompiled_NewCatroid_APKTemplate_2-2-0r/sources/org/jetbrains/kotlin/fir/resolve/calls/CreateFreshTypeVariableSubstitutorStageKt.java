package org.jetbrains.kotlin.fir.resolve.calls;

import java.util.ArrayList;
import java.util.Iterator;
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
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirTypeAlias;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameter;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRef;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRefsOwner;
import org.jetbrains.kotlin.fir.resolve.inference.ConeTypeParameterBasedTypeVariable;
import org.jetbrains.kotlin.fir.resolve.inference.model.ConeDeclaredUpperBoundConstraintPosition;
import org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutor;
import org.jetbrains.kotlin.fir.resolve.substitution.SubstitutorsKt;
import org.jetbrains.kotlin.fir.scopes.impl.FirNestedClassifierScopeKt;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassifierSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeParameterSymbol;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.fir.types.ConeTypeProjectionKt;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeTypeVariable;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.StandardClassIds;
import org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemOperation;

/* JADX INFO: compiled from: CreateFreshTypeVariableSubstitutorStage.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\u001a2\u0010\u0000\u001a\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u00012\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002\u001a\u001c\u0010\u000b\u001a\u00020\f*\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\t\u001a\u00020\nH\u0002¨\u0006\u000f"}, d2 = {"createToFreshVariableSubstitutorAndAddInitialConstraints", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/fir/resolve/substitution/ConeSubstitutor;", "", "Lorg/jetbrains/kotlin/fir/types/ConeTypeVariable;", "declaration", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameterRefsOwner;", "csBuilder", "Lorg/jetbrains/kotlin/resolve/calls/inference/ConstraintSystemOperation;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "getTypeParameterFromExpandedClass", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameter;", "index", "", "resolve"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CreateFreshTypeVariableSubstitutorStageKt {
    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public static final Pair<ConeSubstitutor, List<ConeTypeVariable>> createToFreshVariableSubstitutorAndAddInitialConstraints(FirTypeParameterRefsOwner firTypeParameterRefsOwner, ConstraintSystemOperation constraintSystemOperation, FirSession firSession) {
        List<FirTypeParameterRef> typeParameters = firTypeParameterRefsOwner.getTypeParameters();
        List<FirTypeParameterRef> list = typeParameters;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(new ConeTypeParameterBasedTypeVariable(((FirTypeParameterRef) it.next()).getSymbol()));
        }
        ArrayList arrayList2 = arrayList;
        ArrayList<ConeTypeParameterBasedTypeVariable> arrayList3 = arrayList2;
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(arrayList3, 10)), 16));
        for (ConeTypeParameterBasedTypeVariable coneTypeParameterBasedTypeVariable : arrayList3) {
            Pair pair = TuplesKt.to(coneTypeParameterBasedTypeVariable.getTypeParameterSymbol(), coneTypeParameterBasedTypeVariable.getDefaultType());
            linkedHashMap.put(pair.getFirst(), pair.getSecond());
        }
        ConeSubstitutor coneSubstitutorSubstitutorByMap = SubstitutorsKt.substitutorByMap(linkedHashMap, firSession);
        Iterator it2 = arrayList2.iterator();
        while (it2.hasNext()) {
            constraintSystemOperation.registerVariable((ConeTypeParameterBasedTypeVariable) it2.next());
        }
        int size = typeParameters.size();
        for (int i = 0; i < size; i++) {
            FirTypeParameterRef firTypeParameterRef = typeParameters.get(i);
            ConeTypeParameterBasedTypeVariable coneTypeParameterBasedTypeVariable2 = (ConeTypeParameterBasedTypeVariable) arrayList2.get(i);
            Iterator<FirResolvedTypeRef> it3 = getTypeParameterFromExpandedClass((FirTypeParameter) firTypeParameterRef.getSymbol().getFir(), i, firSession).getSymbol().getResolvedBounds().iterator();
            while (it3.hasNext()) {
                createToFreshVariableSubstitutorAndAddInitialConstraints$addSubtypeConstraint(coneTypeParameterBasedTypeVariable2, constraintSystemOperation, coneSubstitutorSubstitutorByMap, FirTypeUtilsKt.getConeType(it3.next()));
            }
        }
        return TuplesKt.to(coneSubstitutorSubstitutorByMap, arrayList2);
    }

    private static final void createToFreshVariableSubstitutorAndAddInitialConstraints$addSubtypeConstraint(ConeTypeParameterBasedTypeVariable coneTypeParameterBasedTypeVariable, ConstraintSystemOperation constraintSystemOperation, ConeSubstitutor coneSubstitutor, ConeKotlinType coneKotlinType) {
        ConeClassLikeLookupTag lookupTag;
        ConeSimpleKotlinType coneSimpleKotlinTypeLowerBoundIfFlexible = ConeTypeUtilsKt.lowerBoundIfFlexible(coneKotlinType);
        ClassId classId = null;
        ConeClassLikeType coneClassLikeType = coneSimpleKotlinTypeLowerBoundIfFlexible instanceof ConeClassLikeType ? (ConeClassLikeType) coneSimpleKotlinTypeLowerBoundIfFlexible : null;
        if (coneClassLikeType != null && (lookupTag = coneClassLikeType.getLookupTag()) != null) {
            classId = lookupTag.getClassId();
        }
        if (Intrinsics.areEqual(classId, StandardClassIds.INSTANCE.getAny()) && ConeTypeUtilsKt.isMarkedNullable(ConeTypeUtilsKt.upperBoundIfFlexible(coneKotlinType))) {
            return;
        }
        constraintSystemOperation.addSubtypeConstraint(coneTypeParameterBasedTypeVariable.getDefaultType(), coneSubstitutor.substituteOrSelf(coneKotlinType), new ConeDeclaredUpperBoundConstraintPosition());
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static final FirTypeParameter getTypeParameterFromExpandedClass(FirTypeParameter firTypeParameter, int i, FirSession firSession) {
        FirTypeParameterRef firTypeParameterRef;
        FirTypeParameterSymbol symbol;
        FirTypeParameter firTypeParameter2;
        FirTypeParameterSymbol symbol2;
        FirTypeParameter firTypeParameter3;
        FirDeclaration fir = firTypeParameter.getContainingDeclarationSymbol().getFir();
        if (fir instanceof FirRegularClass) {
            FirTypeParameterRef firTypeParameterRef2 = (FirTypeParameterRef) CollectionsKt.getOrNull(((FirRegularClass) fir).getTypeParameters(), i);
            return (firTypeParameterRef2 == null || (symbol2 = firTypeParameterRef2.getSymbol()) == null || (firTypeParameter3 = (FirTypeParameter) symbol2.getFir()) == null) ? firTypeParameter : firTypeParameter3;
        }
        if (fir instanceof FirTypeAlias) {
            ConeKotlinType coneType = FirNestedClassifierScopeKt.toConeType(firTypeParameter);
            ConeKotlinType coneType2 = FirTypeUtilsKt.getConeType(((FirTypeAlias) fir).getExpandedTypeRef());
            ConeTypeProjection[] typeArguments = coneType2.getTypeArguments();
            int length = typeArguments.length;
            int i2 = 0;
            while (true) {
                if (i2 >= length) {
                    i2 = -1;
                    break;
                }
                if (Intrinsics.areEqual(ConeTypeProjectionKt.getType(typeArguments[i2]), coneType)) {
                    break;
                }
                i2++;
            }
            FirClassifierSymbol<?> symbol3 = TypeUtilsKt.toSymbol(coneType2, firSession);
            Object fir2 = symbol3 != null ? symbol3.getFir() : null;
            if ((fir2 instanceof FirTypeParameterRefsOwner) && (firTypeParameterRef = (FirTypeParameterRef) CollectionsKt.getOrNull(((FirTypeParameterRefsOwner) fir2).getTypeParameters(), i2)) != null && (symbol = firTypeParameterRef.getSymbol()) != null && (firTypeParameter2 = (FirTypeParameter) symbol.getFir()) != null) {
                return fir2 instanceof FirTypeAlias ? getTypeParameterFromExpandedClass(firTypeParameter2, i2, firSession) : firTypeParameter2;
            }
        }
        return firTypeParameter;
    }
}
