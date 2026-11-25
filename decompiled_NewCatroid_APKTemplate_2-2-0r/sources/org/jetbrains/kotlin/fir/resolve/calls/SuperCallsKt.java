package org.jetbrains.kotlin.fir.resolve.calls;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirVariable;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCall;
import org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression;
import org.jetbrains.kotlin.fir.references.FirNamedReference;
import org.jetbrains.kotlin.fir.references.FirReference;
import org.jetbrains.kotlin.fir.resolve.BodyResolveComponents;
import org.jetbrains.kotlin.fir.resolve.LookupTagUtilsKt;
import org.jetbrains.kotlin.fir.resolve.ScopeUtilsKt;
import org.jetbrains.kotlin.fir.scopes.FakeOverrideTypeCalculator;
import org.jetbrains.kotlin.fir.scopes.FirTypeScope;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirVariableSymbol;
import org.jetbrains.kotlin.fir.types.ConeBuiltinTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.StandardClassIds;
import org.jetbrains.kotlin.utils.SmartList;

/* JADX INFO: compiled from: SuperCalls.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000V\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0002\u001a&\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n*\u00020\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\n2\u0006\u0010\u000f\u001a\u00020\u0010\u001a\"\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012*\u00020\f2\u0006\u0010\u0014\u001a\u00020\u000b2\u0006\u0010\u0015\u001a\u00020\u0016H\u0002\u001a\"\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012*\u00020\f2\u0006\u0010\u0014\u001a\u00020\u000b2\u0006\u0010\u0015\u001a\u00020\u0016H\u0002\u001a\u001c\u0010\u0018\u001a\u00020\u0006*\u00020\f2\u0006\u0010\u0019\u001a\u00020\u000b2\u0006\u0010\u001a\u001a\u00020\u0013H\u0002\u001a(\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u000b0\n*\u00020\f2\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00122\u0006\u0010\u001d\u001a\u00020\u0016H\u0002\u001aC\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u000b0\n*\u00020\f2\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00122\u0006\u0010\u001f\u001a\u00020\u00062\u0018\u0010 \u001a\u0014\u0012\u0004\u0012\u00020\u000b\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00130\u00120!H\u0082\b\u001a(\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u000b0\n*\u00020\f2\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00122\u0006\u0010#\u001a\u00020\u0016H\u0002\u001a(\u0010$\u001a\b\u0012\u0004\u0012\u00020\u000b0\n*\u00020\f2\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00122\u0006\u0010\u001d\u001a\u00020\u0016H\u0002\"*\u0010\u0000\u001a\u001e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001j\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0003`\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006%"}, d2 = {"ARITY_OF_METHODS_OF_ANY", "Ljava/util/HashMap;", "", "", "Lkotlin/collections/HashMap;", "isCallingMethodOfAny", "", "callExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCall;", "findTypesForSuperCandidates", "", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "Lorg/jetbrains/kotlin/fir/resolve/BodyResolveComponents;", "superTypeRefs", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "containingCall", "Lorg/jetbrains/kotlin/fir/expressions/FirQualifiedAccessExpression;", "getFunctionMembers", "", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", ModuleXmlParser.TYPE, "name", "Lorg/jetbrains/kotlin/name/Name;", "getPropertyMembers", "isConcreteMember", "supertype", "member", "resolveSupertypesByCalleeName", "supertypes", "calleeName", "resolveSupertypesByMembers", "allowNonConcreteInterfaceMembers", "getMembers", "Lkotlin/Function1;", "resolveSupertypesByPropertyName", "propertyName", "resolveSupertypesForMethodOfAny", "resolve"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class SuperCallsKt {
    private static final HashMap<String, Integer> ARITY_OF_METHODS_OF_ANY = MapsKt.hashMapOf(TuplesKt.to("hashCode", 0), TuplesKt.to(Namer.EQUALS_METHOD_NAME, 1), TuplesKt.to("toString", 0));

    public static final List<ConeKotlinType> findTypesForSuperCandidates(BodyResolveComponents bodyResolveComponents, List<? extends FirTypeRef> superTypeRefs, FirQualifiedAccessExpression containingCall) {
        Name name2;
        Intrinsics.checkNotNullParameter(bodyResolveComponents, "<this>");
        Intrinsics.checkNotNullParameter(superTypeRefs, "superTypeRefs");
        Intrinsics.checkNotNullParameter(containingCall, "containingCall");
        List<? extends FirTypeRef> list = superTypeRefs;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        for (FirTypeRef firTypeRef : list) {
            Intrinsics.checkNotNull(firTypeRef, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.types.FirResolvedTypeRef");
            arrayList.add(((FirResolvedTypeRef) firTypeRef).getType());
        }
        ArrayList arrayList2 = arrayList;
        boolean z = containingCall instanceof FirFunctionCall;
        boolean z2 = z && isCallingMethodOfAny((FirFunctionCall) containingCall);
        if (arrayList2.size() <= 1 && !z2) {
            return arrayList2;
        }
        if (z) {
            Name name3 = ((FirFunctionCall) containingCall).getCalleeReference().getName();
            if (z2) {
                return resolveSupertypesForMethodOfAny(bodyResolveComponents, arrayList2, name3);
            }
            return resolveSupertypesByCalleeName(bodyResolveComponents, arrayList2, name3);
        }
        ArrayList arrayList3 = arrayList2;
        FirReference calleeReference = containingCall.getCalleeReference();
        FirNamedReference firNamedReference = calleeReference instanceof FirNamedReference ? (FirNamedReference) calleeReference : null;
        if (firNamedReference != null && (name2 = firNamedReference.getName()) != null) {
            return resolveSupertypesByPropertyName(bodyResolveComponents, arrayList3, name2);
        }
        return CollectionsKt.emptyList();
    }

    private static final boolean isCallingMethodOfAny(FirFunctionCall firFunctionCall) {
        HashMap<String, Integer> map = ARITY_OF_METHODS_OF_ANY;
        String strAsString = firFunctionCall.getCalleeReference().getName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        Integer num = map.get(strAsString);
        if (num == null) {
            num = -1;
        }
        return num.intValue() == firFunctionCall.getArgumentList().getArguments().size();
    }

    private static final Collection<FirCallableDeclaration> getFunctionMembers(BodyResolveComponents bodyResolveComponents, ConeKotlinType coneKotlinType, Name name2) {
        final List listCreateListBuilder = CollectionsKt.createListBuilder();
        FirTypeScope firTypeScopeScope = ScopeUtilsKt.scope(coneKotlinType, bodyResolveComponents.getSession(), bodyResolveComponents.getScopeSession(), FakeOverrideTypeCalculator.DoNothing.INSTANCE, FirResolvePhase.STATUS);
        if (firTypeScopeScope != null) {
            firTypeScopeScope.processFunctionsByName(name2, new Function1<FirNamedFunctionSymbol, Unit>() { // from class: org.jetbrains.kotlin.fir.resolve.calls.SuperCallsKt$getFunctionMembers$1$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(FirNamedFunctionSymbol firNamedFunctionSymbol) {
                    invoke2(firNamedFunctionSymbol);
                    return Unit.INSTANCE;
                }

                /* JADX WARN: Multi-variable type inference failed */
                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(FirNamedFunctionSymbol it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    listCreateListBuilder.add((FirCallableDeclaration) it.getFir());
                }
            });
        }
        return CollectionsKt.build(listCreateListBuilder);
    }

    private static final Collection<FirCallableDeclaration> getPropertyMembers(BodyResolveComponents bodyResolveComponents, ConeKotlinType coneKotlinType, Name name2) {
        final List listCreateListBuilder = CollectionsKt.createListBuilder();
        FirTypeScope firTypeScopeScope = ScopeUtilsKt.scope(coneKotlinType, bodyResolveComponents.getSession(), bodyResolveComponents.getScopeSession(), FakeOverrideTypeCalculator.DoNothing.INSTANCE, FirResolvePhase.STATUS);
        if (firTypeScopeScope != null) {
            firTypeScopeScope.processPropertiesByName(name2, new Function1<FirVariableSymbol<?>, Unit>() { // from class: org.jetbrains.kotlin.fir.resolve.calls.SuperCallsKt$getPropertyMembers$1$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(FirVariableSymbol<?> firVariableSymbol) {
                    invoke2(firVariableSymbol);
                    return Unit.INSTANCE;
                }

                /* JADX WARN: Multi-variable type inference failed */
                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(FirVariableSymbol<?> it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    List<FirCallableDeclaration> list = listCreateListBuilder;
                    D fir = it.getFir();
                    org.jetbrains.kotlin.utils.CollectionsKt.addIfNotNull(list, fir instanceof FirVariable ? (FirVariable) fir : null);
                }
            });
        }
        return CollectionsKt.build(listCreateListBuilder);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static final boolean isConcreteMember(BodyResolveComponents bodyResolveComponents, ConeKotlinType coneKotlinType, FirCallableDeclaration firCallableDeclaration) {
        ConeClassLikeLookupTag lookupTag;
        if (firCallableDeclaration.getStatus().getModality() == Modality.ABSTRACT) {
            return false;
        }
        ConeClassLikeType coneClassLikeType = coneKotlinType instanceof ConeClassLikeType ? (ConeClassLikeType) coneKotlinType : null;
        FirClassLikeSymbol<?> symbol = (coneClassLikeType == null || (lookupTag = coneClassLikeType.getLookupTag()) == null) ? null : LookupTagUtilsKt.toSymbol(lookupTag, bodyResolveComponents.getSession());
        FirRegularClassSymbol firRegularClassSymbol = symbol instanceof FirRegularClassSymbol ? (FirRegularClassSymbol) symbol : null;
        if (firRegularClassSymbol == null || ((FirRegularClass) firRegularClassSymbol.getFir()).getClassKind() != ClassKind.INTERFACE) {
            return true;
        }
        FirCallableDeclaration fir = firCallableDeclaration.getSymbol().getFir();
        while (true) {
            FirCallableDeclaration originalForSubstitutionOverrideAttr = (ClassMembersKt.isSubstitutionOverride(fir) || (fir.getOrigin() instanceof FirDeclarationOrigin.Synthetic)) ? ClassMembersKt.getOriginalForSubstitutionOverrideAttr(fir) : null;
            if (originalForSubstitutionOverrideAttr == null) {
                originalForSubstitutionOverrideAttr = ClassMembersKt.isIntersectionOverride(fir) ? ClassMembersKt.getOriginalForIntersectionOverrideAttr(fir) : null;
            }
            if (originalForSubstitutionOverrideAttr == null) {
                break;
            }
            fir = originalForSubstitutionOverrideAttr;
        }
        FirCallableSymbol<? extends FirCallableDeclaration> symbol2 = fir.getSymbol();
        if (symbol2 != null) {
            ConeClassLikeLookupTag coneClassLikeLookupTagDispatchReceiverClassLookupTagOrNull = ClassMembersKt.dispatchReceiverClassLookupTagOrNull(symbol2);
            return !Intrinsics.areEqual(coneClassLikeLookupTagDispatchReceiverClassLookupTagOrNull != null ? coneClassLikeLookupTagDispatchReceiverClassLookupTagOrNull.getClassId() : null, StandardClassIds.INSTANCE.getAny());
        }
        throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol<out org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration>");
    }

    private static final List<ConeKotlinType> resolveSupertypesForMethodOfAny(BodyResolveComponents bodyResolveComponents, Collection<? extends ConeKotlinType> collection, Name name2) {
        ClassKind classKind;
        SmartList smartList = new SmartList();
        SmartList smartList2 = new SmartList();
        for (ConeKotlinType coneKotlinType : collection) {
            Collection<FirCallableDeclaration> functionMembers = getFunctionMembers(bodyResolveComponents, coneKotlinType, name2);
            if (!functionMembers.isEmpty()) {
                Collection<FirCallableDeclaration> collection2 = functionMembers;
                boolean z = collection2 instanceof Collection;
                if (!z || !collection2.isEmpty()) {
                    Iterator<T> it = collection2.iterator();
                    while (true) {
                        if (it.hasNext()) {
                            if (isConcreteMember(bodyResolveComponents, coneKotlinType, (FirCallableDeclaration) it.next())) {
                                smartList.add(coneKotlinType);
                            }
                        }
                    }
                }
                if (!z || !collection2.isEmpty()) {
                    Iterator<T> it2 = collection2.iterator();
                    while (it2.hasNext()) {
                        ConeSimpleKotlinType dispatchReceiverType = ((FirCallableDeclaration) it2.next()).getDispatchReceiverType();
                        if (dispatchReceiverType != null && !ConeBuiltinTypeUtilsKt.isAny(dispatchReceiverType)) {
                            smartList2.add(coneKotlinType);
                            break;
                        }
                    }
                }
            }
        }
        List list = smartList;
        CollectionsKt.removeAll((List) list, (Function1) new SuperCallsKt$resolveSupertypesByMembers$3(smartList2, bodyResolveComponents));
        if (smartList.isEmpty()) {
            List arrayList = new ArrayList();
            for (Object obj : smartList2) {
                ConeKotlinType coneKotlinType2 = (ConeKotlinType) obj;
                if (coneKotlinType2 instanceof ConeClassLikeType) {
                    FirClassLikeSymbol<?> symbol = LookupTagUtilsKt.toSymbol(((ConeClassLikeType) coneKotlinType2).getLookupTag(), bodyResolveComponents.getSession());
                    FirRegularClassSymbol firRegularClassSymbol = symbol instanceof FirRegularClassSymbol ? (FirRegularClassSymbol) symbol : null;
                    if (firRegularClassSymbol != null && (classKind = firRegularClassSymbol.getClassKind()) != null && classKind == ClassKind.CLASS) {
                        arrayList.add(obj);
                    }
                }
            }
            list = arrayList;
        }
        List listListOf = list;
        if (listListOf.isEmpty()) {
            listListOf = CollectionsKt.listOf(bodyResolveComponents.getSession().getBuiltinTypes().getAnyType().getType());
        }
        return listListOf;
    }

    private static final List<ConeKotlinType> resolveSupertypesByCalleeName(BodyResolveComponents bodyResolveComponents, Collection<? extends ConeKotlinType> collection, Name name2) {
        SmartList smartList = new SmartList();
        SmartList smartList2 = new SmartList();
        for (ConeKotlinType coneKotlinType : collection) {
            List listPlus = CollectionsKt.plus((Collection) getFunctionMembers(bodyResolveComponents, coneKotlinType, name2), (Iterable) getPropertyMembers(bodyResolveComponents, coneKotlinType, name2));
            if (!listPlus.isEmpty()) {
                List list = listPlus;
                boolean z = list instanceof Collection;
                if (!z || !list.isEmpty()) {
                    Iterator it = list.iterator();
                    while (true) {
                        if (it.hasNext()) {
                            if (isConcreteMember(bodyResolveComponents, coneKotlinType, (FirCallableDeclaration) it.next())) {
                                smartList.add(coneKotlinType);
                            }
                        }
                    }
                }
                if (!z || !list.isEmpty()) {
                    Iterator it2 = list.iterator();
                    while (it2.hasNext()) {
                        ConeSimpleKotlinType dispatchReceiverType = ((FirCallableDeclaration) it2.next()).getDispatchReceiverType();
                        if (dispatchReceiverType != null && !ConeBuiltinTypeUtilsKt.isAny(dispatchReceiverType)) {
                            smartList2.add(coneKotlinType);
                            break;
                        }
                    }
                }
            }
        }
        SmartList smartList3 = smartList;
        CollectionsKt.removeAll((List) smartList3, (Function1) new SuperCallsKt$resolveSupertypesByMembers$3(smartList2, bodyResolveComponents));
        return !smartList.isEmpty() ? smartList3 : smartList2;
    }

    private static final List<ConeKotlinType> resolveSupertypesByPropertyName(BodyResolveComponents bodyResolveComponents, Collection<? extends ConeKotlinType> collection, Name name2) {
        SmartList smartList = new SmartList();
        SmartList smartList2 = new SmartList();
        for (ConeKotlinType coneKotlinType : collection) {
            Collection<FirCallableDeclaration> propertyMembers = getPropertyMembers(bodyResolveComponents, coneKotlinType, name2);
            if (!propertyMembers.isEmpty()) {
                Collection<FirCallableDeclaration> collection2 = propertyMembers;
                boolean z = collection2 instanceof Collection;
                if (!z || !collection2.isEmpty()) {
                    Iterator<T> it = collection2.iterator();
                    while (true) {
                        if (it.hasNext()) {
                            if (isConcreteMember(bodyResolveComponents, coneKotlinType, (FirCallableDeclaration) it.next())) {
                                smartList.add(coneKotlinType);
                            }
                        }
                    }
                }
                if (!z || !collection2.isEmpty()) {
                    Iterator<T> it2 = collection2.iterator();
                    while (it2.hasNext()) {
                        ConeSimpleKotlinType dispatchReceiverType = ((FirCallableDeclaration) it2.next()).getDispatchReceiverType();
                        if (dispatchReceiverType != null && !ConeBuiltinTypeUtilsKt.isAny(dispatchReceiverType)) {
                            smartList2.add(coneKotlinType);
                            break;
                        }
                    }
                }
            }
        }
        SmartList smartList3 = smartList;
        CollectionsKt.removeAll((List) smartList3, (Function1) new SuperCallsKt$resolveSupertypesByMembers$3(smartList2, bodyResolveComponents));
        return !smartList.isEmpty() ? smartList3 : smartList2;
    }
}
