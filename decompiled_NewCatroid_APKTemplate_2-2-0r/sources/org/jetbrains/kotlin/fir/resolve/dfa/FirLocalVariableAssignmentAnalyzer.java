package org.jetbrains.kotlin.fir.resolve.dfa;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.contracts.description.EventOccurrencesRangeKt;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousObject;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression;
import org.jetbrains.kotlin.fir.references.FirReferenceUtilsKt;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: FirLocalVariableAssignmentAnalyzer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010%\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0000\u0018\u0000 (2\u00020\u0001:\u0001(B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014J\u000e\u0010\u0015\u001a\u00020\u00122\u0006\u0010\u0016\u001a\u00020\u0017J\u0014\u0010\u0018\u001a\u00020\u00122\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001aJ0\u0010\u001c\u001a\u0016\u0012\u0006\u0012\u0004\u0018\u00010\u0006\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u000f0\u000e2\n\u0010\u001d\u001a\u0006\u0012\u0002\b\u00030\u00052\u0006\u0010\u001e\u001a\u00020\nH\u0002J\u0006\u0010\u001f\u001a\u00020\u0012J\u0006\u0010 \u001a\u00020\u0012J\u000e\u0010!\u001a\u00020\u00122\u0006\u0010\"\u001a\u00020\nJ\u0016\u0010#\u001a\u0004\u0018\u00010\u00062\n\u0010\u001d\u001a\u0006\u0012\u0002\b\u00030\u0005H\u0002J\u000e\u0010$\u001a\u00020\n2\u0006\u0010%\u001a\u00020&J\u0006\u0010'\u001a\u00020\u0012R \u0010\u0003\u001a\u0014\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0005\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R \u0010\u0007\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\n0\t0\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\b\u0012\u0002\b\u0003\u0018\u00010\fX\u0082\u000e¢\u0006\u0002\n\u0000R(\u0010\r\u001a\u001c\u0012\u0018\u0012\u0016\u0012\u0006\u0012\u0004\u0018\u00010\u0006\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u000f0\u000e0\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006)"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/dfa/FirLocalVariableAssignmentAnalyzer;", "", "()V", "assignedLocalVariablesByDeclaration", "", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/FirLocalVariableAssignmentAnalyzer$Companion$Fork;", "postponedLambdas", "Lorg/jetbrains/kotlin/fir/resolve/dfa/Stack;", "", "", "rootFunction", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirFunctionSymbol;", "scopes", "Lkotlin/Pair;", "", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "enterClass", "", "klass", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "enterFunction", "function", "Lorg/jetbrains/kotlin/fir/declarations/FirFunction;", "enterFunctionCall", "lambdaArgs", "", "Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousFunction;", "enterScope", NativeSymbol.TYPE_NAME, "evaluatedInPlace", "exitClass", "exitFunction", "exitFunctionCall", "callCompleted", "getInfoForDeclaration", "isAccessToUnstableLocalVariable", "fir", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "reset", "Companion", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirLocalVariableAssignmentAnalyzer {
    private Map<FirBasedSymbol<?>, Companion.Fork> assignedLocalVariablesByDeclaration;
    private FirFunctionSymbol<?> rootFunction;
    private final Stack<Pair<Companion.Fork, Set<FirProperty>>> scopes = StackKt.stackOf(new Pair[0]);
    private final Stack<Map<Companion.Fork, Boolean>> postponedLambdas = StackKt.stackOf(new Map[0]);

    public final void reset() {
        this.rootFunction = null;
        this.assignedLocalVariablesByDeclaration = null;
        this.postponedLambdas.reset();
        this.scopes.reset();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final boolean isAccessToUnstableLocalVariable(FirExpression fir) {
        FirPropertySymbol resolvedPropertySymbol$default;
        FirProperty firProperty;
        Intrinsics.checkNotNullParameter(fir, "fir");
        if (this.assignedLocalVariablesByDeclaration == null) {
            return false;
        }
        FirElement firElementUnwrapElement = UtilKt.unwrapElement(fir);
        FirQualifiedAccessExpression firQualifiedAccessExpression = firElementUnwrapElement instanceof FirQualifiedAccessExpression ? (FirQualifiedAccessExpression) firElementUnwrapElement : null;
        if (firQualifiedAccessExpression == null || (resolvedPropertySymbol$default = FirReferenceUtilsKt.toResolvedPropertySymbol$default(firQualifiedAccessExpression.getCalleeReference(), false, 1, null)) == null || (firProperty = (FirProperty) resolvedPropertySymbol$default.getFir()) == null) {
            return false;
        }
        if (!this.scopes.top().getSecond().contains(firProperty)) {
            List<Map<Companion.Fork, Boolean>> listAll = this.postponedLambdas.all();
            if ((listAll instanceof Collection) && listAll.isEmpty()) {
                return false;
            }
            Iterator<T> it = listAll.iterator();
            while (it.hasNext()) {
                Map map = (Map) it.next();
                if (!map.isEmpty()) {
                    for (Map.Entry entry : map.entrySet()) {
                        Companion.Fork fork = (Companion.Fork) entry.getKey();
                        if (!((Boolean) entry.getValue()).booleanValue() || !fork.getAssignedInside().contains(firProperty)) {
                        }
                    }
                }
            }
            return false;
        }
        return true;
    }

    private final Companion.Fork getInfoForDeclaration(FirBasedSymbol<?> symbol) {
        FirFunctionSymbol<?> firFunctionSymbol = this.rootFunction;
        if (firFunctionSymbol == null || Intrinsics.areEqual(firFunctionSymbol, symbol)) {
            return null;
        }
        Map<FirBasedSymbol<?>, Companion.Fork> forks = this.assignedLocalVariablesByDeclaration;
        if (forks == null) {
            Companion.MiniCfgBuilder.MiniCfgData miniCfgData = new Companion.MiniCfgBuilder.MiniCfgData();
            new Companion.MiniCfgBuilder().visitElement2((FirElement) firFunctionSymbol.getFir(), miniCfgData);
            forks = miniCfgData.getForks();
            this.assignedLocalVariablesByDeclaration = forks;
        }
        return forks.get(symbol);
    }

    private final Pair<Companion.Fork, Set<FirProperty>> enterScope(FirBasedSymbol<?> symbol, boolean evaluatedInPlace) {
        Set<FirProperty> assignedLater;
        Set<FirProperty> assignedInside;
        Companion.Fork infoForDeclaration = getInfoForDeclaration(symbol);
        Set mutableSet = CollectionsKt.toMutableSet(this.scopes.top().getSecond());
        this.scopes.push(TuplesKt.to(infoForDeclaration, mutableSet));
        if (!evaluatedInPlace) {
            for (Pair<Companion.Fork, Set<FirProperty>> pair : this.scopes.all()) {
                Companion.Fork forkComponent1 = pair.component1();
                Set<FirProperty> setComponent2 = pair.component2();
                if (infoForDeclaration != null && (assignedInside = infoForDeclaration.getAssignedInside()) != null) {
                    setComponent2.addAll(assignedInside);
                }
                if (forkComponent1 != null && (assignedLater = forkComponent1.getAssignedLater()) != null) {
                    mutableSet.addAll(assignedLater);
                }
            }
        }
        return this.scopes.top();
    }

    public final void enterFunction(FirFunction function) {
        Intrinsics.checkNotNullParameter(function, "function");
        if (this.rootFunction == null) {
            this.rootFunction = function.getSymbol();
            this.scopes.push(TuplesKt.to(null, new LinkedHashSet()));
            return;
        }
        Pair<Companion.Fork, Set<FirProperty>> pairEnterScope = enterScope(function.getSymbol(), (function instanceof FirAnonymousFunction) && EventOccurrencesRangeKt.isInPlace(((FirAnonymousFunction) function).getInvocationKind()));
        Companion.Fork forkComponent1 = pairEnterScope.component1();
        Set<FirProperty> setComponent2 = pairEnterScope.component2();
        Iterator<Map<Companion.Fork, Boolean>> it = this.postponedLambdas.all().iterator();
        while (it.hasNext()) {
            for (Map.Entry<Companion.Fork, Boolean> entry : it.next().entrySet()) {
                Companion.Fork key = entry.getKey();
                if (!entry.getValue().booleanValue() && !Intrinsics.areEqual(key, forkComponent1)) {
                    CollectionsKt.addAll(setComponent2, key.getAssignedInside());
                }
            }
        }
    }

    public final void exitFunction() {
        this.scopes.pop();
        if (StackKt.isEmpty(this.scopes)) {
            this.rootFunction = null;
            this.assignedLocalVariablesByDeclaration = null;
        }
    }

    public final void enterClass(FirClass klass) {
        Intrinsics.checkNotNullParameter(klass, "klass");
        if (this.rootFunction == null) {
            return;
        }
        FirClassSymbol<? extends FirClass> symbol = klass.getSymbol();
        boolean z = klass instanceof FirAnonymousObject;
        Pair<Companion.Fork, Set<FirProperty>> pairEnterScope = enterScope(symbol, z);
        Companion.Fork forkComponent1 = pairEnterScope.component1();
        Set<FirProperty> setComponent2 = pairEnterScope.component2();
        if (!z || forkComponent1 == null) {
            return;
        }
        setComponent2.addAll(forkComponent1.getAssignedInside());
    }

    public final void exitClass() {
        if (this.rootFunction == null) {
            return;
        }
        this.scopes.pop();
    }

    public final void enterFunctionCall(Collection<? extends FirAnonymousFunction> lambdaArgs) {
        Intrinsics.checkNotNullParameter(lambdaArgs, "lambdaArgs");
        if (this.rootFunction == null) {
            return;
        }
        Stack<Map<Companion.Fork, Boolean>> stack = this.postponedLambdas;
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = lambdaArgs.iterator();
        while (it.hasNext()) {
            Companion.Fork infoForDeclaration = getInfoForDeclaration(((FirAnonymousFunction) it.next()).getSymbol());
            if (infoForDeclaration != null) {
                arrayList.add(infoForDeclaration);
            }
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Object obj : arrayList) {
            linkedHashMap.put(obj, false);
        }
        stack.push(linkedHashMap);
    }

    public final void exitFunctionCall(boolean callCompleted) {
        if (this.rootFunction == null) {
            return;
        }
        Map<Companion.Fork, Boolean> mapPop = this.postponedLambdas.pop();
        if (callCompleted) {
            return;
        }
        Set<Companion.Fork> setKeySet = mapPop.keySet();
        Map map = (Map) StackKt.topOrNull(this.postponedLambdas);
        if (map == null) {
            return;
        }
        for (Object obj : setKeySet) {
            map.put(obj, true);
        }
    }
}
