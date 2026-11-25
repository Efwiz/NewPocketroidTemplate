package org.jetbrains.kotlin.fir.scopes.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.utils.FirDeclarationUtilKt;
import org.jetbrains.kotlin.fir.resolve.ScopeUtilsKt;
import org.jetbrains.kotlin.fir.scopes.FirContainingNamesAwareScope;
import org.jetbrains.kotlin.fir.scopes.FirOverrideCheckerKt;
import org.jetbrains.kotlin.fir.scopes.FirTypeScope;
import org.jetbrains.kotlin.fir.symbols.impl.FirFieldSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirVariableSymbol;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FirClassUseSiteMemberScope.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B+\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bJ\u001a\u0010\f\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u000e0\r2\u0006\u0010\u000f\u001a\u00020\u0010H\u0014J\u001c\u0010\u0011\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00130\u00120\u00072\u0006\u0010\u0014\u001a\u00020\u0013H\u0002J.\u0010\u0015\u001a \u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00130\u00120\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00170\u00070\u00162\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J\b\u0010\u0018\u001a\u00020\u0019H\u0016J\f\u0010\u001a\u001a\u00020\u001b*\u00020\u001cH\u0014¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/fir/scopes/impl/FirClassUseSiteMemberScope;", "Lorg/jetbrains/kotlin/fir/scopes/impl/AbstractFirUseSiteMemberScope;", "klass", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "superTypeScopes", "", "Lorg/jetbrains/kotlin/fir/scopes/FirTypeScope;", "declaredMemberScope", "Lorg/jetbrains/kotlin/fir/scopes/FirContainingNamesAwareScope;", "(Lorg/jetbrains/kotlin/fir/declarations/FirClass;Lorg/jetbrains/kotlin/fir/FirSession;Ljava/util/List;Lorg/jetbrains/kotlin/fir/scopes/FirContainingNamesAwareScope;)V", "collectProperties", "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirVariableSymbol;", "name", "Lorg/jetbrains/kotlin/name/Name;", "computeDirectOverriddenForDeclaredProperty", "Lorg/jetbrains/kotlin/fir/scopes/impl/FirTypeIntersectionScopeContext$ResultOfIntersection;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertySymbol;", "declaredPropertySymbol", "getPropertiesAndFieldsFromSupertypesByName", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirFieldSymbol;", "toString", "", "isVisibleInCurrentClass", "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirClassUseSiteMemberScope extends AbstractFirUseSiteMemberScope {
    @Override // org.jetbrains.kotlin.fir.scopes.impl.AbstractFirUseSiteMemberScope
    protected boolean isVisibleInCurrentClass(FirNamedFunctionSymbol firNamedFunctionSymbol) {
        Intrinsics.checkNotNullParameter(firNamedFunctionSymbol, "<this>");
        return true;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FirClassUseSiteMemberScope(FirClass klass, FirSession session, List<? extends FirTypeScope> superTypeScopes, FirContainingNamesAwareScope declaredMemberScope) {
        super(FirDeclarationUtilKt.getClassId(klass), session, FirOverrideCheckerKt.getFirOverrideChecker(session), superTypeScopes, ScopeUtilsKt.defaultType(klass), declaredMemberScope);
        Intrinsics.checkNotNullParameter(klass, "klass");
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(superTypeScopes, "superTypeScopes");
        Intrinsics.checkNotNullParameter(declaredMemberScope, "declaredMemberScope");
    }

    @Override // org.jetbrains.kotlin.fir.scopes.impl.AbstractFirUseSiteMemberScope
    protected Collection<FirVariableSymbol<?>> collectProperties(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        final List listCreateListBuilder = CollectionsKt.createListBuilder();
        final LinkedHashSet linkedHashSet = new LinkedHashSet();
        getDeclaredMemberScope().processPropertiesByName(name2, new Function1<FirVariableSymbol<?>, Unit>() { // from class: org.jetbrains.kotlin.fir.scopes.impl.FirClassUseSiteMemberScope$collectProperties$1$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(FirVariableSymbol<?> firVariableSymbol) {
                invoke2(firVariableSymbol);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FirVariableSymbol<?> symbol) {
                Intrinsics.checkNotNullParameter(symbol, "symbol");
                if (symbol.getRawStatus().isStatic()) {
                    return;
                }
                if (symbol instanceof FirPropertySymbol) {
                    this.this$0.getDirectOverriddenProperties().put(symbol, this.this$0.computeDirectOverriddenForDeclaredProperty((FirPropertySymbol) symbol));
                }
                linkedHashSet.add(symbol);
                listCreateListBuilder.add(symbol);
            }
        });
        Pair<List<FirTypeIntersectionScopeContext.ResultOfIntersection<FirPropertySymbol>>, List<FirFieldSymbol>> propertiesAndFieldsFromSupertypesByName = getPropertiesAndFieldsFromSupertypesByName(name2);
        List<FirTypeIntersectionScopeContext.ResultOfIntersection<FirPropertySymbol>> listComponent1 = propertiesAndFieldsFromSupertypesByName.component1();
        List<FirFieldSymbol> listComponent2 = propertiesAndFieldsFromSupertypesByName.component2();
        for (FirTypeIntersectionScopeContext.ResultOfIntersection<FirPropertySymbol> resultOfIntersection : listComponent1) {
            if (getOverridden((FirPropertySymbol) extractSomeSymbolFromSuperType(resultOfIntersection), linkedHashSet) == null) {
                listCreateListBuilder.add(resultOfIntersection.getChosenSymbol());
            }
        }
        listCreateListBuilder.addAll(listComponent2);
        return CollectionsKt.build(listCreateListBuilder);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public final List<FirTypeIntersectionScopeContext.ResultOfIntersection<FirPropertySymbol>> computeDirectOverriddenForDeclaredProperty(FirPropertySymbol declaredPropertySymbol) {
        ArrayList arrayList = new ArrayList();
        FirProperty firProperty = (FirProperty) declaredPropertySymbol.getFir();
        for (FirTypeIntersectionScopeContext.ResultOfIntersection<FirPropertySymbol> resultOfIntersection : getPropertiesAndFieldsFromSupertypesByName(declaredPropertySymbol.getName()).getFirst()) {
            if (getOverrideChecker().isOverriddenProperty(firProperty, (FirProperty) ((FirPropertySymbol) extractSomeSymbolFromSuperType(resultOfIntersection)).getFir())) {
                arrayList.add(resultOfIntersection);
            }
        }
        return arrayList;
    }

    private final Pair<List<FirTypeIntersectionScopeContext.ResultOfIntersection<FirPropertySymbol>>, List<FirFieldSymbol>> getPropertiesAndFieldsFromSupertypesByName(Name name2) {
        List<FirTypeIntersectionScopeContext.ResultOfIntersection<FirPropertySymbol>> list = getPropertiesFromSupertypes().get(name2);
        if (list != null) {
            return TuplesKt.to(list, MapsKt.getValue(getFieldsFromSupertypes(), name2));
        }
        final ArrayList arrayList = new ArrayList();
        FirTypeIntersectionScopeContext supertypeScopeContext = getSupertypeScopeContext();
        List<FirTypeScope> scopes = supertypeScopeContext.getScopes();
        ArrayList arrayList2 = new ArrayList();
        for (FirTypeScope firTypeScope : scopes) {
            ArrayList arrayList3 = new ArrayList();
            final FirTypeIntersectionScopeContext$collectMembersGroupedByScope$1$1 firTypeIntersectionScopeContext$collectMembersGroupedByScope$1$1 = new FirTypeIntersectionScopeContext$collectMembersGroupedByScope$1$1(arrayList3);
            firTypeScope.processPropertiesByName(name2, new Function1<FirVariableSymbol<?>, Unit>() { // from class: org.jetbrains.kotlin.fir.scopes.impl.FirClassUseSiteMemberScope$getPropertiesAndFieldsFromSupertypesByName$properties$1$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(FirVariableSymbol<?> firVariableSymbol) {
                    invoke2(firVariableSymbol);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(FirVariableSymbol<?> it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    if (it instanceof FirPropertySymbol) {
                        firTypeIntersectionScopeContext$collectMembersGroupedByScope$1$1.invoke(it);
                    } else if (it instanceof FirFieldSymbol) {
                        arrayList.add(it);
                    }
                }
            });
            if (arrayList3.isEmpty()) {
                arrayList3 = null;
            }
            Pair pair = arrayList3 != null ? TuplesKt.to(firTypeScope, arrayList3) : null;
            if (pair != null) {
                arrayList2.add(pair);
            }
        }
        List<FirTypeIntersectionScopeContext.ResultOfIntersection<FirPropertySymbol>> listConvertGroupedCallablesToIntersectionResults = supertypeScopeContext.convertGroupedCallablesToIntersectionResults(arrayList2);
        getPropertiesFromSupertypes().put(name2, listConvertGroupedCallablesToIntersectionResults);
        getFieldsFromSupertypes().put(name2, arrayList);
        return TuplesKt.to(listConvertGroupedCallablesToIntersectionResults, arrayList);
    }

    public String toString() {
        return "Use site scope of " + getClassId();
    }
}
