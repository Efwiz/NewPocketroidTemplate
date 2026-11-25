package org.jetbrains.kotlin.fir.scopes.impl;

import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.MethodDelegation;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutor;
import org.jetbrains.kotlin.fir.scopes.FakeOverrideTypeCalculator;
import org.jetbrains.kotlin.fir.scopes.FirTypeScope;
import org.jetbrains.kotlin.fir.scopes.ProcessorAction;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassifierSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirConstructorSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirVariableSymbol;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FirScopeWithFakeOverrideTypeCalculator.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000v\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u000e\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fH\u0016J\u000e\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\r0\fH\u0016J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\rH\u0016J.\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0011\u001a\u00020\r2\u001c\u0010\u0014\u001a\u0018\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0016\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\u00130\u0015H\u0016J\u001c\u0010\u0018\u001a\u00020\u00132\u0012\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u001a\u0012\u0004\u0012\u00020\u00130\u0019H\u0016J*\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001e2\u0018\u0010\u0014\u001a\u0014\u0012\u0004\u0012\u00020\u001e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u001c0\u0015H\u0016J*\u0010\u001f\u001a\u00020\u001c2\u0006\u0010 \u001a\u00020!2\u0018\u0010\u0014\u001a\u0014\u0012\u0004\u0012\u00020!\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u001c0\u0015H\u0016J$\u0010\"\u001a\u00020\u00132\u0006\u0010\u0011\u001a\u00020\r2\u0012\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u001e\u0012\u0004\u0012\u00020\u00130\u0019H\u0016J(\u0010#\u001a\u00020\u00132\u0006\u0010\u0011\u001a\u00020\r2\u0016\u0010\u0014\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030$\u0012\u0004\u0012\u00020\u00130\u0019H\u0016J\b\u0010%\u001a\u00020\bH\u0016J\u0010\u0010&\u001a\u00020\u00132\u0006\u0010'\u001a\u00020(H\u0002R\u000e\u0010\u0002\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\n¨\u0006)"}, d2 = {"Lorg/jetbrains/kotlin/fir/scopes/impl/FirScopeWithFakeOverrideTypeCalculator;", "Lorg/jetbrains/kotlin/fir/scopes/FirTypeScope;", MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX, "fakeOverrideTypeCalculator", "Lorg/jetbrains/kotlin/fir/scopes/FakeOverrideTypeCalculator;", "(Lorg/jetbrains/kotlin/fir/scopes/FirTypeScope;Lorg/jetbrains/kotlin/fir/scopes/FakeOverrideTypeCalculator;)V", "scopeOwnerLookupNames", "", "", "getScopeOwnerLookupNames", "()Ljava/util/List;", "getCallableNames", "", "Lorg/jetbrains/kotlin/name/Name;", "getClassifierNames", "mayContainName", "", "name", "processClassifiersByNameWithSubstitution", "", "processor", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassifierSymbol;", "Lorg/jetbrains/kotlin/fir/resolve/substitution/ConeSubstitutor;", "processDeclaredConstructors", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirConstructorSymbol;", "processDirectOverriddenFunctionsWithBaseScope", "Lorg/jetbrains/kotlin/fir/scopes/ProcessorAction;", "functionSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;", "processDirectOverriddenPropertiesWithBaseScope", "propertySymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertySymbol;", "processFunctionsByName", "processPropertiesByName", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirVariableSymbol;", "toString", "updateReturnType", "declaration", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirScopeWithFakeOverrideTypeCalculator extends FirTypeScope {
    private final FirTypeScope delegate;
    private final FakeOverrideTypeCalculator fakeOverrideTypeCalculator;

    public FirScopeWithFakeOverrideTypeCalculator(FirTypeScope delegate, FakeOverrideTypeCalculator fakeOverrideTypeCalculator) {
        Intrinsics.checkNotNullParameter(delegate, "delegate");
        Intrinsics.checkNotNullParameter(fakeOverrideTypeCalculator, "fakeOverrideTypeCalculator");
        this.delegate = delegate;
        this.fakeOverrideTypeCalculator = fakeOverrideTypeCalculator;
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public void processClassifiersByNameWithSubstitution(Name name2, Function2<? super FirClassifierSymbol<?>, ? super ConeSubstitutor, Unit> processor) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(processor, "processor");
        this.delegate.processClassifiersByNameWithSubstitution(name2, processor);
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public void processFunctionsByName(Name name2, final Function1<? super FirNamedFunctionSymbol, Unit> processor) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(processor, "processor");
        this.delegate.processFunctionsByName(name2, new Function1<FirNamedFunctionSymbol, Unit>() { // from class: org.jetbrains.kotlin.fir.scopes.impl.FirScopeWithFakeOverrideTypeCalculator.processFunctionsByName.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
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
                FirScopeWithFakeOverrideTypeCalculator.this.updateReturnType((FirCallableDeclaration) it.getFir());
                processor.invoke(it);
            }
        });
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public void processPropertiesByName(Name name2, final Function1<? super FirVariableSymbol<?>, Unit> processor) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(processor, "processor");
        this.delegate.processPropertiesByName(name2, new Function1<FirVariableSymbol<?>, Unit>() { // from class: org.jetbrains.kotlin.fir.scopes.impl.FirScopeWithFakeOverrideTypeCalculator.processPropertiesByName.1
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

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FirVariableSymbol<?> it) {
                Intrinsics.checkNotNullParameter(it, "it");
                FirScopeWithFakeOverrideTypeCalculator.this.updateReturnType((FirCallableDeclaration) it.getFir());
                processor.invoke(it);
            }
        });
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public void processDeclaredConstructors(Function1<? super FirConstructorSymbol, Unit> processor) {
        Intrinsics.checkNotNullParameter(processor, "processor");
        this.delegate.processDeclaredConstructors(processor);
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public boolean mayContainName(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return this.delegate.mayContainName(name2);
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirTypeScope
    public ProcessorAction processDirectOverriddenFunctionsWithBaseScope(FirNamedFunctionSymbol functionSymbol, final Function2<? super FirNamedFunctionSymbol, ? super FirTypeScope, ? extends ProcessorAction> processor) {
        Intrinsics.checkNotNullParameter(functionSymbol, "functionSymbol");
        Intrinsics.checkNotNullParameter(processor, "processor");
        return this.delegate.processDirectOverriddenFunctionsWithBaseScope(functionSymbol, new Function2<FirNamedFunctionSymbol, FirTypeScope, ProcessorAction>() { // from class: org.jetbrains.kotlin.fir.scopes.impl.FirScopeWithFakeOverrideTypeCalculator.processDirectOverriddenFunctionsWithBaseScope.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(2);
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // kotlin.jvm.functions.Function2
            public final ProcessorAction invoke(FirNamedFunctionSymbol symbol, FirTypeScope scope) {
                Intrinsics.checkNotNullParameter(symbol, "symbol");
                Intrinsics.checkNotNullParameter(scope, "scope");
                FirScopeWithFakeOverrideTypeCalculator.this.updateReturnType((FirCallableDeclaration) symbol.getFir());
                return processor.invoke(symbol, scope);
            }
        });
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirTypeScope
    public ProcessorAction processDirectOverriddenPropertiesWithBaseScope(FirPropertySymbol propertySymbol, final Function2<? super FirPropertySymbol, ? super FirTypeScope, ? extends ProcessorAction> processor) {
        Intrinsics.checkNotNullParameter(propertySymbol, "propertySymbol");
        Intrinsics.checkNotNullParameter(processor, "processor");
        return this.delegate.processDirectOverriddenPropertiesWithBaseScope(propertySymbol, new Function2<FirPropertySymbol, FirTypeScope, ProcessorAction>() { // from class: org.jetbrains.kotlin.fir.scopes.impl.FirScopeWithFakeOverrideTypeCalculator.processDirectOverriddenPropertiesWithBaseScope.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(2);
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // kotlin.jvm.functions.Function2
            public final ProcessorAction invoke(FirPropertySymbol symbol, FirTypeScope scope) {
                Intrinsics.checkNotNullParameter(symbol, "symbol");
                Intrinsics.checkNotNullParameter(scope, "scope");
                FirScopeWithFakeOverrideTypeCalculator.this.updateReturnType((FirCallableDeclaration) symbol.getFir());
                return processor.invoke(symbol, scope);
            }
        });
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirContainingNamesAwareScope
    public Set<Name> getCallableNames() {
        return this.delegate.getCallableNames();
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirContainingNamesAwareScope
    public Set<Name> getClassifierNames() {
        return this.delegate.getClassifierNames();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateReturnType(FirCallableDeclaration declaration) {
        if (ClassMembersKt.isSubstitutionOverride(declaration) || ClassMembersKt.isIntersectionOverride(declaration)) {
            this.fakeOverrideTypeCalculator.computeReturnType(declaration);
        }
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public List<String> getScopeOwnerLookupNames() {
        return this.delegate.getScopeOwnerLookupNames();
    }

    public String toString() {
        return this.delegate.toString();
    }
}
