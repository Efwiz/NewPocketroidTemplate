package org.jetbrains.kotlin.fir.resolve.transformers.body.resolve;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirTypeAlias;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameter;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRef;
import org.jetbrains.kotlin.fir.declarations.utils.FirDeclarationUtilKt;
import org.jetbrains.kotlin.fir.resolve.BodyResolveComponents;
import org.jetbrains.kotlin.fir.resolve.LookupTagUtilsKt;
import org.jetbrains.kotlin.fir.resolve.ScopeUtilsKt;
import org.jetbrains.kotlin.fir.resolve.TypeExpansionUtilsKt;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.ConeTypeParameterLookupTag;
import org.jetbrains.kotlin.fir.symbols.FirLazyDeclarationResolverKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeParameterSymbol;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeInferenceContext;
import org.jetbrains.kotlin.fir.types.ConeIntersectionType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeParameterType;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.fir.types.ConeTypeProjectionKt;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.ProjectionKind;
import org.jetbrains.kotlin.fir.types.TypeComponentsKt;
import org.jetbrains.kotlin.fir.types.TypeUnificationKt;
import org.jetbrains.kotlin.types.AbstractTypeChecker;
import org.jetbrains.kotlin.types.model.SimpleTypeMarker;

/* JADX INFO: compiled from: bareTypes.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000 \n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0010\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0002\u001a\u001c\u0010\u0004\u001a\u0004\u0018\u00010\u0005*\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0005¨\u0006\n"}, d2 = {"canBeUsedAsBareType", "", "firTypeAlias", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeAlias;", "computeRepresentativeTypeForBareType", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "Lorg/jetbrains/kotlin/fir/resolve/BodyResolveComponents;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/fir/types/ConeClassLikeType;", "originalType", "resolve"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class BareTypesKt {
    /* JADX WARN: Multi-variable type inference failed */
    public static final ConeKotlinType computeRepresentativeTypeForBareType(BodyResolveComponents bodyResolveComponents, ConeClassLikeType type, ConeKotlinType originalType) {
        ConeClassLikeType coneClassLikeTypeFullyExpandedType$default;
        ConeClassLikeLookupTag lookupTag;
        Intrinsics.checkNotNullParameter(bodyResolveComponents, "<this>");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(originalType, "originalType");
        ConeSimpleKotlinType coneSimpleKotlinTypeFullyExpandedType = TypeExpansionUtilsKt.fullyExpandedType(ConeTypeUtilsKt.lowerBoundIfFlexible(originalType), bodyResolveComponents.getSession());
        if (coneSimpleKotlinTypeFullyExpandedType != originalType) {
            return computeRepresentativeTypeForBareType(bodyResolveComponents, type, coneSimpleKotlinTypeFullyExpandedType);
        }
        Object obj = null;
        if (originalType instanceof ConeIntersectionType) {
            Collection<ConeKotlinType> intersectedTypes = ((ConeIntersectionType) originalType).getIntersectedTypes();
            ArrayList arrayList = new ArrayList();
            Iterator<T> it = intersectedTypes.iterator();
            while (it.hasNext()) {
                ConeKotlinType coneKotlinTypeComputeRepresentativeTypeForBareType = computeRepresentativeTypeForBareType(bodyResolveComponents, type, (ConeKotlinType) it.next());
                if (coneKotlinTypeComputeRepresentativeTypeForBareType != null) {
                    arrayList.add(coneKotlinTypeComputeRepresentativeTypeForBareType);
                }
            }
            ArrayList arrayList2 = arrayList;
            for (Object obj2 : arrayList2) {
                if (!(((ConeKotlinType) obj2).getTypeArguments().length == 0)) {
                    obj = obj2;
                    break;
                }
            }
            ConeKotlinType coneKotlinType = (ConeKotlinType) obj;
            return coneKotlinType != null ? coneKotlinType : (ConeKotlinType) CollectionsKt.firstOrNull((List) arrayList2);
        }
        ConeClassLikeType coneClassLikeType = originalType instanceof ConeClassLikeType ? (ConeClassLikeType) originalType : null;
        if (coneClassLikeType == null || (coneClassLikeTypeFullyExpandedType$default = TypeExpansionUtilsKt.fullyExpandedType$default(coneClassLikeType, bodyResolveComponents.getSession(), null, 2, null)) == null || (lookupTag = coneClassLikeTypeFullyExpandedType$default.getLookupTag()) == null) {
            return null;
        }
        FirClassLikeSymbol<?> symbol = LookupTagUtilsKt.toSymbol(type.getLookupTag(), bodyResolveComponents.getSession());
        FirClassLikeDeclaration firClassLikeDeclaration = symbol != null ? (FirClassLikeDeclaration) symbol.getFir() : null;
        FirTypeAlias firTypeAlias = firClassLikeDeclaration instanceof FirTypeAlias ? (FirTypeAlias) firClassLikeDeclaration : null;
        if (firTypeAlias != null && !canBeUsedAsBareType(firTypeAlias)) {
            return null;
        }
        ConeClassLikeType coneClassLikeTypeFullyExpandedType$default2 = TypeExpansionUtilsKt.fullyExpandedType$default(type, bodyResolveComponents.getSession(), null, 2, null);
        FirClassLikeSymbol<?> symbol2 = LookupTagUtilsKt.toSymbol(coneClassLikeTypeFullyExpandedType$default2.getLookupTag(), bodyResolveComponents.getSession());
        FirClassLikeDeclaration firClassLikeDeclaration2 = symbol2 != null ? (FirClassLikeDeclaration) symbol2.getFir() : null;
        FirRegularClass firRegularClass = firClassLikeDeclaration2 instanceof FirRegularClass ? (FirRegularClass) firClassLikeDeclaration2 : null;
        if (firRegularClass == null) {
            return null;
        }
        ConeInferenceContext typeContext = TypeComponentsKt.getTypeContext(bodyResolveComponents.getSession());
        Object objFirstOrNull = CollectionsKt.firstOrNull((List<? extends Object>) AbstractTypeChecker.INSTANCE.findCorrespondingSupertypes(typeContext.newTypeCheckerState(false, false), ScopeUtilsKt.defaultType(firRegularClass), lookupTag));
        ConeClassLikeType coneClassLikeType2 = objFirstOrNull instanceof ConeClassLikeType ? (ConeClassLikeType) objFirstOrNull : null;
        if (coneClassLikeType2 == null) {
            return null;
        }
        if (originalType.getNullability().isNullable()) {
            SimpleTypeMarker simpleTypeMarkerWithNullability = typeContext.withNullability((SimpleTypeMarker) coneClassLikeType2, true);
            Intrinsics.checkNotNull(simpleTypeMarkerWithNullability, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.types.ConeClassLikeType");
            coneClassLikeType2 = (ConeClassLikeType) simpleTypeMarkerWithNullability;
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        List<FirTypeParameterRef> typeParameters = firRegularClass.getTypeParameters();
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        Iterator<T> it2 = typeParameters.iterator();
        while (it2.hasNext()) {
            linkedHashSet.add(((FirTypeParameterRef) it2.next()).getSymbol());
        }
        if (!TypeUnificationKt.doUnify(bodyResolveComponents.getSession(), originalType, coneClassLikeType2, linkedHashSet, linkedHashMap)) {
            return null;
        }
        List<FirTypeParameterRef> typeParameters2 = firRegularClass.getTypeParameters();
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(typeParameters2, 10));
        Iterator<T> it3 = typeParameters2.iterator();
        while (it3.hasNext()) {
            ConeTypeProjection coneTypeProjection = (ConeTypeProjection) linkedHashMap.get(((FirTypeParameterRef) it3.next()).getSymbol());
            if (coneTypeProjection == null) {
                return null;
            }
            arrayList3.add(coneTypeProjection);
        }
        return ConeTypeUtilsKt.withArguments(coneClassLikeTypeFullyExpandedType$default2, (ConeTypeProjection[]) arrayList3.toArray(new ConeTypeProjection[0]));
    }

    private static final boolean canBeUsedAsBareType(FirTypeAlias firTypeAlias) {
        ConeTypeParameterLookupTag lookupTag;
        FirTypeParameterSymbol typeParameterSymbol;
        FirTypeParameter firTypeParameter;
        FirLazyDeclarationResolverKt.lazyResolveToPhase(firTypeAlias, FirResolvePhase.TYPES);
        Set set = CollectionsKt.toSet(firTypeAlias.getTypeParameters());
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        ConeClassLikeType expandedConeType = FirDeclarationUtilKt.getExpandedConeType(firTypeAlias);
        if (expandedConeType == null) {
            return false;
        }
        for (ConeTypeProjection coneTypeProjection : expandedConeType.getTypeArguments()) {
            if (coneTypeProjection.getKind() != ProjectionKind.STAR) {
                if (coneTypeProjection.getKind() != ProjectionKind.INVARIANT) {
                    return false;
                }
                ConeKotlinType type = ConeTypeProjectionKt.getType(coneTypeProjection);
                Intrinsics.checkNotNull(type);
                ConeTypeParameterType coneTypeParameterType = type instanceof ConeTypeParameterType ? (ConeTypeParameterType) type : null;
                if (coneTypeParameterType == null || (lookupTag = coneTypeParameterType.getLookupTag()) == null || (typeParameterSymbol = lookupTag.getTypeParameterSymbol()) == null || (firTypeParameter = (FirTypeParameter) typeParameterSymbol.getFir()) == null || !set.contains(firTypeParameter) || linkedHashSet.contains(firTypeParameter)) {
                    return false;
                }
                linkedHashSet.add(firTypeParameter);
            }
        }
        return true;
    }
}
