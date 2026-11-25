package org.jetbrains.kotlin.fir.scopes;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.resolve.LookupTagUtilsKt;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.resolve.ScopeUtilsKt;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: Scopes.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000B\n\u0000\n\u0002\u0010$\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a.\u0010\u0000\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u00012\n\u0010\u0003\u001a\u0006\u0012\u0002\b\u00030\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b\u001a&\u0010\u0000\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u00012\n\u0010\u0003\u001a\u0006\u0012\u0002\b\u00030\u00042\u0006\u0010\t\u001a\u00020\n\u001a\u001c\u0010\u000b\u001a\u0004\u0018\u00010\f*\u00020\r2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b\u001a0\u0010\u000e\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00100\u000f*\u00020\u00112\n\u0010\u0012\u001a\u0006\u0012\u0002\b\u00030\u00132\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b¨\u0006\u0014"}, d2 = {"debugCollectOverrides", "", "", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "scope", "Lorg/jetbrains/kotlin/fir/scopes/FirTypeScope;", "getNestedClassifierScope", "Lorg/jetbrains/kotlin/fir/scopes/FirContainingNamesAwareScope;", "Lorg/jetbrains/kotlin/fir/symbols/ConeClassLikeLookupTag;", "overriddenFunctions", "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirFunctionSymbol;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;", "containingClass", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassSymbol;", "providers"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ScopesKt {
    /* JADX WARN: Multi-variable type inference failed */
    public static final FirContainingNamesAwareScope getNestedClassifierScope(ConeClassLikeLookupTag coneClassLikeLookupTag, FirSession session, ScopeSession scopeSession) {
        Intrinsics.checkNotNullParameter(coneClassLikeLookupTag, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
        FirClassLikeSymbol<?> symbol = LookupTagUtilsKt.toSymbol(coneClassLikeLookupTag, session);
        FirClassLikeDeclaration firClassLikeDeclaration = symbol != null ? (FirClassLikeDeclaration) symbol.getFir() : null;
        FirClass firClass = firClassLikeDeclaration instanceof FirRegularClass ? (FirRegularClass) firClassLikeDeclaration : null;
        if (firClass == null) {
            return null;
        }
        return firClass.getScopeProvider().getNestedClassifierScope(firClass, session, scopeSession);
    }

    public static final Map<Object, Object> debugCollectOverrides(FirCallableSymbol<?> symbol, FirSession session, ScopeSession scopeSession) {
        FirTypeScope firTypeScopeScope;
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
        ConeSimpleKotlinType dispatchReceiverType = symbol.getDispatchReceiverType();
        if (dispatchReceiverType == null || (firTypeScopeScope = ScopeUtilsKt.scope(dispatchReceiverType, session, scopeSession, FakeOverrideTypeCalculator.DoNothing.INSTANCE, FirResolvePhase.STATUS)) == null) {
            return MapsKt.emptyMap();
        }
        return debugCollectOverrides(symbol, firTypeScopeScope);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Map<Object, Object> debugCollectOverrides$process(FirTypeScope firTypeScope, FirCallableSymbol<?> firCallableSymbol) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        final ArrayList arrayList = new ArrayList();
        if (firCallableSymbol instanceof FirNamedFunctionSymbol) {
            firTypeScope.processDirectOverriddenFunctionsWithBaseScope((FirNamedFunctionSymbol) firCallableSymbol, new Function2<FirNamedFunctionSymbol, FirTypeScope, ProcessorAction>() { // from class: org.jetbrains.kotlin.fir.scopes.ScopesKt$debugCollectOverrides$process$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final ProcessorAction invoke(FirNamedFunctionSymbol baseSymbol, FirTypeScope baseScope) {
                    Intrinsics.checkNotNullParameter(baseSymbol, "baseSymbol");
                    Intrinsics.checkNotNullParameter(baseScope, "baseScope");
                    arrayList.add(ScopesKt.debugCollectOverrides$process(baseScope, baseSymbol));
                    return ProcessorAction.NEXT;
                }
            });
        } else if (firCallableSymbol instanceof FirPropertySymbol) {
            firTypeScope.processDirectOverriddenPropertiesWithBaseScope((FirPropertySymbol) firCallableSymbol, new Function2<FirPropertySymbol, FirTypeScope, ProcessorAction>() { // from class: org.jetbrains.kotlin.fir.scopes.ScopesKt$debugCollectOverrides$process$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final ProcessorAction invoke(FirPropertySymbol baseSymbol, FirTypeScope baseScope) {
                    Intrinsics.checkNotNullParameter(baseSymbol, "baseSymbol");
                    Intrinsics.checkNotNullParameter(baseScope, "baseScope");
                    arrayList.add(ScopesKt.debugCollectOverrides$process(baseScope, baseSymbol));
                    return ProcessorAction.NEXT;
                }
            });
        }
        linkedHashMap.put(firCallableSymbol, arrayList);
        return linkedHashMap;
    }

    public static final Map<Object, Object> debugCollectOverrides(FirCallableSymbol<?> symbol, FirTypeScope scope) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(scope, "scope");
        return debugCollectOverrides$process(scope, symbol);
    }

    public static final List<FirFunctionSymbol<?>> overriddenFunctions(FirNamedFunctionSymbol firNamedFunctionSymbol, FirClassSymbol<?> containingClass, FirSession session, ScopeSession scopeSession) {
        Intrinsics.checkNotNullParameter(firNamedFunctionSymbol, "<this>");
        Intrinsics.checkNotNullParameter(containingClass, "containingClass");
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
        FirTypeScope firTypeScopeUnsubstitutedScope = FirKotlinScopeProviderKt.unsubstitutedScope(containingClass, session, scopeSession, true, FirResolvePhase.STATUS);
        final ArrayList arrayList = new ArrayList();
        firTypeScopeUnsubstitutedScope.processFunctionsByName(firNamedFunctionSymbol.getCallableId().getCallableName(), new Function1<FirNamedFunctionSymbol, Unit>() { // from class: org.jetbrains.kotlin.fir.scopes.ScopesKt.overriddenFunctions.1
            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FirNamedFunctionSymbol it) {
                Intrinsics.checkNotNullParameter(it, "it");
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(FirNamedFunctionSymbol firNamedFunctionSymbol2) {
                invoke2(firNamedFunctionSymbol2);
                return Unit.INSTANCE;
            }
        });
        FirTypeScopeKt.processOverriddenFunctions(firTypeScopeUnsubstitutedScope, firNamedFunctionSymbol, new Function1<FirNamedFunctionSymbol, ProcessorAction>() { // from class: org.jetbrains.kotlin.fir.scopes.ScopesKt.overriddenFunctions.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final ProcessorAction invoke(FirNamedFunctionSymbol it) {
                Intrinsics.checkNotNullParameter(it, "it");
                arrayList.add(it);
                return ProcessorAction.NEXT;
            }
        });
        return arrayList;
    }
}
