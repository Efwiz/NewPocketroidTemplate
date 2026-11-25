package org.jetbrains.kotlin.fir.declarations;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.InlineClassRepresentation;
import org.jetbrains.kotlin.descriptors.ValueClassRepresentation;
import org.jetbrains.kotlin.descriptors.ValueClassRepresentationKt;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.resolve.LookupTagUtilsKt;
import org.jetbrains.kotlin.fir.resolve.ScopeUtilsKt;
import org.jetbrains.kotlin.fir.resolve.TypeExpansionUtilsKt;
import org.jetbrains.kotlin.fir.resolve.substitution.SubstitutorsKt;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.ConeClassifierLookupTag;
import org.jetbrains.kotlin.fir.symbols.FirLazyDeclarationResolverKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassifierSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirConstructorSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirValueParameterSymbol;
import org.jetbrains.kotlin.fir.types.ConeBuiltinTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeLookupTagBasedType;
import org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeContext;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeComponentsKt;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.types.model.TypeSystemContextHelpersKt;
import org.jetbrains.kotlin.util.OperatorNameConventions;

/* JADX INFO: compiled from: ValueClassesUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000N\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010#\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u001e\u0010\u0000\u001a\n\u0012\u0004\u0012\u00020\u0002\u0018\u00010\u00012\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006\u001a&\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00062\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00020\u000bH\u0002\u001a\u001c\u0010\f\u001a\n\u0012\u0004\u0012\u00020\u000e\u0018\u00010\r*\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0002\u001a\u0012\u0010\u000f\u001a\u00020\b*\u00020\u00102\u0006\u0010\u0005\u001a\u00020\u0006\u001a\u001e\u0010\u0011\u001a\u0004\u0018\u00010\u0012*\u00020\u00122\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0013\u001a\u00020\u0014H\u0000\u001a\u0016\u0010\u0015\u001a\u0004\u0018\u00010\u0012*\u00020\u00122\u0006\u0010\u0005\u001a\u00020\u0006H\u0000\u001a(\u0010\u0016\u001a\u0016\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u00020\u0017\u0018\u00010\r*\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0006H\u0002¨\u0006\u0019"}, d2 = {"computeValueClassRepresentation", "Lorg/jetbrains/kotlin/descriptors/ValueClassRepresentation;", "Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;", "klass", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "isRecursiveSingleFieldValueClass", "", ModuleXmlParser.TYPE, "visited", "", "getValueClassUnderlyingParameters", "", "Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;", "isTypedEqualsInValueClass", "Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;", "substitutedUnderlyingTypeForInlineClass", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "context", "Lorg/jetbrains/kotlin/fir/types/ConeTypeContext;", "unsubstitutedUnderlyingTypeForInlineClass", "valueClassRepresentationTypeMarkersList", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/name/Name;", "providers"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ValueClassesUtilsKt {
    public static final ConeKotlinType substitutedUnderlyingTypeForInlineClass(ConeKotlinType coneKotlinType, FirSession session, ConeTypeContext context) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(context, "context");
        ConeKotlinType coneKotlinTypeUnsubstitutedUnderlyingTypeForInlineClass = unsubstitutedUnderlyingTypeForInlineClass(coneKotlinType, session);
        if (coneKotlinTypeUnsubstitutedUnderlyingTypeForInlineClass == null) {
            return null;
        }
        return SubstitutorsKt.createTypeSubstitutorByTypeConstructor(MapsKt.mapOf(TuplesKt.to(TypeSystemContextHelpersKt.typeConstructor(coneKotlinType, context), coneKotlinType)), context, true).substituteOrNull(coneKotlinTypeUnsubstitutedUnderlyingTypeForInlineClass);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final ConeKotlinType unsubstitutedUnderlyingTypeForInlineClass(ConeKotlinType coneKotlinType, FirSession session) {
        ConeClassifierLookupTag lookupTag;
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        ConeKotlinType coneKotlinTypeFullyExpandedType = TypeExpansionUtilsKt.fullyExpandedType(coneKotlinType, session);
        ConeLookupTagBasedType coneLookupTagBasedType = coneKotlinTypeFullyExpandedType instanceof ConeLookupTagBasedType ? (ConeLookupTagBasedType) coneKotlinTypeFullyExpandedType : null;
        FirClassifierSymbol<?> symbol = (coneLookupTagBasedType == null || (lookupTag = coneLookupTagBasedType.getLookupTag()) == null) ? null : LookupTagUtilsKt.toSymbol(lookupTag, session);
        FirRegularClassSymbol firRegularClassSymbol = symbol instanceof FirRegularClassSymbol ? (FirRegularClassSymbol) symbol : null;
        if (firRegularClassSymbol == null) {
            return null;
        }
        FirLazyDeclarationResolverKt.lazyResolveToPhase(firRegularClassSymbol, FirResolvePhase.STATUS);
        InlineClassRepresentation<ConeSimpleKotlinType> inlineClassRepresentation = FirValueClassRepresentationKt.getInlineClassRepresentation((FirRegularClass) firRegularClassSymbol.getFir());
        return inlineClassRepresentation != null ? (ConeSimpleKotlinType) inlineClassRepresentation.getUnderlyingType() : null;
    }

    public static final ValueClassRepresentation<ConeSimpleKotlinType> computeValueClassRepresentation(FirRegularClass klass, FirSession session) {
        Intrinsics.checkNotNullParameter(klass, "klass");
        Intrinsics.checkNotNullParameter(session, "session");
        List<FirValueParameter> valueClassUnderlyingParameters = getValueClassUnderlyingParameters(klass, session);
        if (valueClassUnderlyingParameters != null) {
            if (valueClassUnderlyingParameters.isEmpty()) {
                valueClassUnderlyingParameters = null;
            }
            if (valueClassUnderlyingParameters != null) {
                List<FirValueParameter> list = valueClassUnderlyingParameters;
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                for (FirValueParameter firValueParameter : list) {
                    Name name2 = firValueParameter.getName();
                    ConeKotlinType resolvedReturnType = firValueParameter.getSymbol().getResolvedReturnType();
                    Intrinsics.checkNotNull(resolvedReturnType, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType");
                    arrayList.add(TuplesKt.to(name2, (ConeSimpleKotlinType) resolvedReturnType));
                }
                ArrayList arrayList2 = arrayList;
                Pair pair = (Pair) CollectionsKt.singleOrNull((List) arrayList2);
                if (pair != null) {
                    Name name3 = (Name) pair.component1();
                    ConeSimpleKotlinType coneSimpleKotlinType = (ConeSimpleKotlinType) pair.component2();
                    if (isRecursiveSingleFieldValueClass(coneSimpleKotlinType, session, SetsKt.mutableSetOf(coneSimpleKotlinType))) {
                        return new InlineClassRepresentation(name3, coneSimpleKotlinType);
                    }
                }
                return ValueClassRepresentationKt.createValueClassRepresentation(TypeComponentsKt.getTypeContext(session), arrayList2);
            }
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static final List<FirValueParameter> getValueClassUnderlyingParameters(FirRegularClass firRegularClass, FirSession firSession) {
        FirConstructorSymbol firConstructorSymbolPrimaryConstructorIfAny;
        FirConstructor firConstructor;
        if (!firRegularClass.getStatus().isInline() || (firConstructorSymbolPrimaryConstructorIfAny = DeclarationUtilsKt.primaryConstructorIfAny(firRegularClass, firSession)) == null || (firConstructor = (FirConstructor) firConstructorSymbolPrimaryConstructorIfAny.getFir()) == null) {
            return null;
        }
        return firConstructor.getValueParameters();
    }

    private static final boolean isRecursiveSingleFieldValueClass(ConeSimpleKotlinType coneSimpleKotlinType, FirSession firSession, Set<ConeSimpleKotlinType> set) {
        Pair pair;
        ConeSimpleKotlinType coneSimpleKotlinType2;
        List<Pair<Name, ConeSimpleKotlinType>> listValueClassRepresentationTypeMarkersList = valueClassRepresentationTypeMarkersList(coneSimpleKotlinType, firSession);
        if (listValueClassRepresentationTypeMarkersList == null || (pair = (Pair) CollectionsKt.singleOrNull((List) listValueClassRepresentationTypeMarkersList)) == null || (coneSimpleKotlinType2 = (ConeSimpleKotlinType) pair.getSecond()) == null) {
            return false;
        }
        return !set.add(coneSimpleKotlinType2) || isRecursiveSingleFieldValueClass(coneSimpleKotlinType2, firSession, set);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static final List<Pair<Name, ConeSimpleKotlinType>> valueClassRepresentationTypeMarkersList(ConeSimpleKotlinType coneSimpleKotlinType, FirSession firSession) {
        FirClassifierSymbol<?> symbol = TypeUtilsKt.toSymbol(coneSimpleKotlinType, firSession);
        FirRegularClassSymbol firRegularClassSymbol = symbol instanceof FirRegularClassSymbol ? (FirRegularClassSymbol) symbol : null;
        if (firRegularClassSymbol == null || !((FirMemberDeclaration) firRegularClassSymbol.getFir()).getStatus().isInline()) {
            return null;
        }
        ValueClassRepresentation<ConeSimpleKotlinType> valueClassRepresentation = FirValueClassRepresentationKt.getValueClassRepresentation((FirRegularClass) firRegularClassSymbol.getFir());
        if (valueClassRepresentation != null) {
            return valueClassRepresentation.getUnderlyingPropertyNamesToTypes();
        }
        FirConstructorSymbol firConstructorSymbolPrimaryConstructorIfAny = DeclarationUtilsKt.primaryConstructorIfAny((FirClass) firRegularClassSymbol.getFir(), firSession);
        if (firConstructorSymbolPrimaryConstructorIfAny == null) {
            return null;
        }
        List<FirValueParameterSymbol> valueParameterSymbols = firConstructorSymbolPrimaryConstructorIfAny.getValueParameterSymbols();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(valueParameterSymbols, 10));
        for (FirValueParameterSymbol firValueParameterSymbol : valueParameterSymbols) {
            Name name2 = firValueParameterSymbol.getName();
            ConeKotlinType resolvedReturnType = firValueParameterSymbol.getResolvedReturnType();
            Intrinsics.checkNotNull(resolvedReturnType, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType");
            arrayList.add(TuplesKt.to(name2, (ConeSimpleKotlinType) resolvedReturnType));
        }
        return arrayList;
    }

    public static final boolean isTypedEqualsInValueClass(FirSimpleFunction firSimpleFunction, FirSession session) {
        FirRegularClassSymbol firRegularClassSymbol;
        Intrinsics.checkNotNullParameter(firSimpleFunction, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        ConeClassLikeLookupTag coneClassLikeLookupTagContainingClassLookupTag = ClassMembersKt.containingClassLookupTag(firSimpleFunction);
        if (coneClassLikeLookupTagContainingClassLookupTag == null || (firRegularClassSymbol = LookupTagUtilsKt.toFirRegularClassSymbol(coneClassLikeLookupTagContainingClassLookupTag, session)) == null) {
            return false;
        }
        ConeClassLikeType coneClassLikeTypeReplaceArgumentsWithStarProjections = ConeTypeUtilsKt.replaceArgumentsWithStarProjections(ScopeUtilsKt.defaultType(firRegularClassSymbol));
        if (!firSimpleFunction.getContextReceivers().isEmpty() || firSimpleFunction.getReceiverParameter() != null || !Intrinsics.areEqual(firSimpleFunction.getName(), OperatorNameConventions.EQUALS) || !firRegularClassSymbol.getRawStatus().isInline() || firSimpleFunction.getValueParameters().size() != 1) {
            return false;
        }
        ConeKotlinType coneKotlinTypeFullyExpandedType = TypeExpansionUtilsKt.fullyExpandedType(FirTypeUtilsKt.getConeType(firSimpleFunction.getReturnTypeRef()), session);
        if (!ConeBuiltinTypeUtilsKt.isBoolean(coneKotlinTypeFullyExpandedType) && !ConeBuiltinTypeUtilsKt.isNothing(coneKotlinTypeFullyExpandedType)) {
            return false;
        }
        ConeKotlinType coneType = FirTypeUtilsKt.getConeType(firSimpleFunction.getValueParameters().get(0).getReturnTypeRef());
        return (coneType instanceof ConeClassLikeType) && Intrinsics.areEqual(ConeTypeUtilsKt.replaceArgumentsWithStarProjections((ConeClassLikeType) coneType), coneClassLikeTypeReplaceArgumentsWithStarProjections);
    }
}
