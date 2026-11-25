package org.jetbrains.kotlin.fir.resolve.transformers.mpp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import org.jetbrains.kotlin.fir.FirExpectActualMatchingContext;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.DeclarationUtilsKt;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProviderKt;
import org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutor;
import org.jetbrains.kotlin.fir.scopes.impl.FirPackageMemberScope;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirConstructorSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeParameterSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirVariableSymbol;
import org.jetbrains.kotlin.fir.types.ExpectActualUtilsKt;
import org.jetbrains.kotlin.mpp.CallableSymbolMarker;
import org.jetbrains.kotlin.mpp.ClassLikeSymbolMarker;
import org.jetbrains.kotlin.mpp.RegularClassSymbolMarker;
import org.jetbrains.kotlin.name.CallableId;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.resolve.calls.mpp.AbstractExpectActualCompatibilityChecker;
import org.jetbrains.kotlin.resolve.multiplatform.ExpectActualCompatibility;

/* JADX INFO: compiled from: FirExpectActualResolver.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002JR\u0010\u0003\u001a*\u0012\u000e\u0012\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00060\u0005\u0012\u000e\u0012\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00060\u0007\u0018\u00010\u0004j\u0004\u0018\u0001`\b2\n\u0010\t\u001a\u0006\u0012\u0002\b\u00030\u00062\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/mpp/FirExpectActualResolver;", "", "()V", "findExpectForActual", "", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility;", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "", "Lorg/jetbrains/kotlin/fir/declarations/ExpectForActualData;", "actualSymbol", "useSiteSession", "Lorg/jetbrains/kotlin/fir/FirSession;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "context", "Lorg/jetbrains/kotlin/fir/FirExpectActualMatchingContext;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirExpectActualResolver {
    public static final FirExpectActualResolver INSTANCE = new FirExpectActualResolver();

    private FirExpectActualResolver() {
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final Map<ExpectActualCompatibility<FirBasedSymbol<?>>, List<FirBasedSymbol<?>>> findExpectForActual(FirBasedSymbol<?> actualSymbol, FirSession useSiteSession, ScopeSession scopeSession, FirExpectActualMatchingContext context) {
        FirRegularClassSymbol firRegularClassSymbolFullyExpandedClass;
        ConeSubstitutor coneSubstitutorCreateExpectActualTypeParameterSubstitutor$default;
        Intrinsics.checkNotNullParameter(actualSymbol, "actualSymbol");
        Intrinsics.checkNotNullParameter(useSiteSession, "useSiteSession");
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
        Intrinsics.checkNotNullParameter(context, "context");
        Collection membersForExpectClass = null;
        if (actualSymbol instanceof FirCallableSymbol) {
            FirCallableSymbol firCallableSymbol = (FirCallableSymbol) actualSymbol;
            CallableId callableId = firCallableSymbol.getCallableId();
            ClassId classId = callableId.getClassId();
            Ref.ObjectRef objectRef = new Ref.ObjectRef();
            if (classId != null) {
                FirClassLikeSymbol<?> classLikeSymbolByClassId = FirSymbolProviderKt.getDependenciesSymbolProvider(useSiteSession).getClassLikeSymbolByClassId(classId);
                objectRef.element = classLikeSymbolByClassId != null ? DeclarationUtilsKt.fullyExpandedClass(classLikeSymbolByClassId, classLikeSymbolByClassId.getModuleData().getSession()) : 0;
                FirClassLikeSymbol<?> classLikeSymbolByClassId2 = FirSymbolProviderKt.getSymbolProvider(useSiteSession).getClassLikeSymbolByClassId(classId);
                firRegularClassSymbolFullyExpandedClass = classLikeSymbolByClassId2 != null ? DeclarationUtilsKt.fullyExpandedClass(classLikeSymbolByClassId2, useSiteSession) : null;
                FirRegularClassSymbol firRegularClassSymbol = (FirRegularClassSymbol) objectRef.element;
                List<FirTypeParameterSymbol> typeParameterSymbols = firRegularClassSymbol != null ? firRegularClassSymbol.getTypeParameterSymbols() : null;
                if (typeParameterSymbols == null) {
                    typeParameterSymbols = CollectionsKt.emptyList();
                }
                List<FirTypeParameterSymbol> typeParameterSymbols2 = firRegularClassSymbolFullyExpandedClass != null ? firRegularClassSymbolFullyExpandedClass.getTypeParameterSymbols() : null;
                if (typeParameterSymbols2 == null) {
                    typeParameterSymbols2 = CollectionsKt.emptyList();
                }
                coneSubstitutorCreateExpectActualTypeParameterSubstitutor$default = ExpectActualUtilsKt.createExpectActualTypeParameterSubstitutor$default(typeParameterSymbols, typeParameterSymbols2, useSiteSession, null, 8, null);
                if (actualSymbol instanceof FirConstructorSymbol) {
                    FirRegularClassSymbol firRegularClassSymbol2 = (FirRegularClassSymbol) objectRef.element;
                    if (firRegularClassSymbol2 != null) {
                        membersForExpectClass = FirExpectActualMatchingContext.getConstructors$default(context, firRegularClassSymbol2, scopeSession, null, 2, null);
                    }
                } else {
                    FirRegularClassSymbol firRegularClassSymbol3 = (FirRegularClassSymbol) objectRef.element;
                    membersForExpectClass = firRegularClassSymbol3 != null ? context.getMembersForExpectClass(firRegularClassSymbol3, firCallableSymbol.getName()) : null;
                }
                if (membersForExpectClass == null) {
                    membersForExpectClass = CollectionsKt.emptyList();
                }
            } else {
                if (callableId.isLocal()) {
                    return null;
                }
                FirPackageMemberScope firPackageMemberScope = new FirPackageMemberScope(callableId.getPackageName(), useSiteSession, FirSymbolProviderKt.getDependenciesSymbolProvider(useSiteSession), null, 8, null);
                final ArrayList arrayList = new ArrayList();
                firPackageMemberScope.processFunctionsByName(callableId.getCallableName(), new Function1<FirNamedFunctionSymbol, Unit>() { // from class: org.jetbrains.kotlin.fir.resolve.transformers.mpp.FirExpectActualResolver$findExpectForActual$1$result$candidates$2$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(FirNamedFunctionSymbol firNamedFunctionSymbol) {
                        invoke2(firNamedFunctionSymbol);
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(FirNamedFunctionSymbol it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        arrayList.add(it);
                    }
                });
                firPackageMemberScope.processPropertiesByName(callableId.getCallableName(), new Function1<FirVariableSymbol<?>, Unit>() { // from class: org.jetbrains.kotlin.fir.resolve.transformers.mpp.FirExpectActualResolver$findExpectForActual$1$result$candidates$2$2
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
                    public final void invoke2(FirVariableSymbol<?> it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        arrayList.add(it);
                    }
                });
                firRegularClassSymbolFullyExpandedClass = null;
                coneSubstitutorCreateExpectActualTypeParameterSubstitutor$default = null;
                membersForExpectClass = arrayList;
            }
            ArrayList arrayList2 = new ArrayList();
            for (Object obj : membersForExpectClass) {
                FirCallableSymbol firCallableSymbol2 = (FirCallableSymbol) obj;
                if (!Intrinsics.areEqual(actualSymbol, firCallableSymbol2) && context.isExpect(firCallableSymbol2)) {
                    arrayList2.add(obj);
                }
            }
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            for (Object obj2 : arrayList2) {
                ExpectActualCompatibility callablesCompatibility = AbstractExpectActualCompatibilityChecker.INSTANCE.getCallablesCompatibility((FirCallableSymbol) obj2, (CallableSymbolMarker) actualSymbol, coneSubstitutorCreateExpectActualTypeParameterSubstitutor$default, (RegularClassSymbolMarker) objectRef.element, firRegularClassSymbolFullyExpandedClass, context);
                Object obj3 = linkedHashMap.get(callablesCompatibility);
                if (obj3 == null) {
                    obj3 = (List) new ArrayList();
                    linkedHashMap.put(callablesCompatibility, obj3);
                }
                ((List) obj3).add(obj2);
            }
            List list = (List) linkedHashMap.get(ExpectActualCompatibility.Compatible.INSTANCE);
            return list == null ? linkedHashMap : MapsKt.mapOf(TuplesKt.to(ExpectActualCompatibility.Compatible.INSTANCE, list));
        }
        if (!(actualSymbol instanceof FirClassLikeSymbol)) {
            return null;
        }
        FirClassLikeSymbol<?> classLikeSymbolByClassId3 = FirSymbolProviderKt.getDependenciesSymbolProvider(useSiteSession).getClassLikeSymbolByClassId(((FirClassLikeSymbol) actualSymbol).getClassId());
        FirRegularClassSymbol firRegularClassSymbol4 = classLikeSymbolByClassId3 instanceof FirRegularClassSymbol ? (FirRegularClassSymbol) classLikeSymbolByClassId3 : null;
        if (firRegularClassSymbol4 == null) {
            return null;
        }
        return MapsKt.mapOf(TuplesKt.to(AbstractExpectActualCompatibilityChecker.INSTANCE.getClassifiersCompatibility(firRegularClassSymbol4, (ClassLikeSymbolMarker) actualSymbol, context), CollectionsKt.listOf(firRegularClassSymbol4)));
    }
}
