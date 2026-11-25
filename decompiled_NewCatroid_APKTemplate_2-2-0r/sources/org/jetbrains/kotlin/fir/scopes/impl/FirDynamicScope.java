package org.jetbrains.kotlin.fir.scopes.impl;

import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.resolve.ScopeUtilsKt;
import org.jetbrains.kotlin.fir.scopes.FakeOverrideTypeCalculator;
import org.jetbrains.kotlin.fir.scopes.FirTypeScope;
import org.jetbrains.kotlin.fir.scopes.ProcessorAction;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirVariableSymbol;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FirDynamicScope.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u000e\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\rH\u0016J\u000e\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u000e0\rH\u0016J*\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0018\u0010\u0014\u001a\u0014\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00110\u0015H\u0016J*\u0010\u0016\u001a\u00020\u00112\u0006\u0010\u0017\u001a\u00020\u00182\u0018\u0010\u0014\u001a\u0014\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00110\u0015H\u0016J$\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u000e2\u0012\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u001a0\u001cH\u0016J(\u0010\u001d\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u000e2\u0016\u0010\u0014\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u001e\u0012\u0004\u0012\u00020\u001a0\u001cH\u0016R\u001d\u0010\u0007\u001a\u0004\u0018\u00010\u00018BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\b\u0010\tR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/fir/scopes/impl/FirDynamicScope;", "Lorg/jetbrains/kotlin/fir/scopes/FirTypeScope;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;)V", "anyTypeScope", "getAnyTypeScope", "()Lorg/jetbrains/kotlin/fir/scopes/FirTypeScope;", "anyTypeScope$delegate", "Lkotlin/Lazy;", "getCallableNames", "", "Lorg/jetbrains/kotlin/name/Name;", "getClassifierNames", "processDirectOverriddenFunctionsWithBaseScope", "Lorg/jetbrains/kotlin/fir/scopes/ProcessorAction;", "functionSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;", "processor", "Lkotlin/Function2;", "processDirectOverriddenPropertiesWithBaseScope", "propertySymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertySymbol;", "processFunctionsByName", "", "name", "Lkotlin/Function1;", "processPropertiesByName", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirVariableSymbol;", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirDynamicScope extends FirTypeScope {

    /* JADX INFO: renamed from: anyTypeScope$delegate, reason: from kotlin metadata */
    private final Lazy anyTypeScope;
    private final ScopeSession scopeSession;
    private final FirSession session;

    @FirDynamicScopeConstructor
    public FirDynamicScope(FirSession session, ScopeSession scopeSession) {
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
        this.session = session;
        this.scopeSession = scopeSession;
        this.anyTypeScope = LazyKt.lazy(new Function0<FirTypeScope>() { // from class: org.jetbrains.kotlin.fir.scopes.impl.FirDynamicScope$anyTypeScope$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final FirTypeScope invoke() {
                return ScopeUtilsKt.scope(this.this$0.session.getBuiltinTypes().getAnyType().getType(), this.this$0.session, this.this$0.scopeSession, FakeOverrideTypeCalculator.DoNothing.INSTANCE, null);
            }
        });
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirTypeScope
    public ProcessorAction processDirectOverriddenFunctionsWithBaseScope(FirNamedFunctionSymbol functionSymbol, Function2<? super FirNamedFunctionSymbol, ? super FirTypeScope, ? extends ProcessorAction> processor) {
        Intrinsics.checkNotNullParameter(functionSymbol, "functionSymbol");
        Intrinsics.checkNotNullParameter(processor, "processor");
        return ProcessorAction.NEXT;
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirTypeScope
    public ProcessorAction processDirectOverriddenPropertiesWithBaseScope(FirPropertySymbol propertySymbol, Function2<? super FirPropertySymbol, ? super FirTypeScope, ? extends ProcessorAction> processor) {
        Intrinsics.checkNotNullParameter(propertySymbol, "propertySymbol");
        Intrinsics.checkNotNullParameter(processor, "processor");
        return ProcessorAction.NEXT;
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirContainingNamesAwareScope
    public Set<Name> getCallableNames() {
        return SetsKt.emptySet();
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirContainingNamesAwareScope
    public Set<Name> getClassifierNames() {
        return SetsKt.emptySet();
    }

    private final FirTypeScope getAnyTypeScope() {
        return (FirTypeScope) this.anyTypeScope.getValue();
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public void processFunctionsByName(Name name2, final Function1<? super FirNamedFunctionSymbol, Unit> processor) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(processor, "processor");
        final Ref.BooleanRef booleanRef = new Ref.BooleanRef();
        FirTypeScope anyTypeScope = getAnyTypeScope();
        if (anyTypeScope != null) {
            anyTypeScope.processFunctionsByName(name2, new Function1<FirNamedFunctionSymbol, Unit>() { // from class: org.jetbrains.kotlin.fir.scopes.impl.FirDynamicScope.processFunctionsByName.1
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

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(FirNamedFunctionSymbol it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    booleanRef.element = true;
                    processor.invoke(it);
                }
            });
        }
        if (booleanRef.element) {
            return;
        }
        processor.invoke(((FirSimpleFunction) FirDynamicScopeKt.getDynamicMembersStorage(this.session).getFunctionsCacheByName().getValue(name2, null)).getSymbol());
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public void processPropertiesByName(Name name2, final Function1<? super FirVariableSymbol<?>, Unit> processor) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(processor, "processor");
        final Ref.BooleanRef booleanRef = new Ref.BooleanRef();
        FirTypeScope anyTypeScope = getAnyTypeScope();
        if (anyTypeScope != null) {
            anyTypeScope.processPropertiesByName(name2, new Function1<FirVariableSymbol<?>, Unit>() { // from class: org.jetbrains.kotlin.fir.scopes.impl.FirDynamicScope.processPropertiesByName.1
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
                    booleanRef.element = true;
                    processor.invoke(it);
                }
            });
        }
        if (booleanRef.element) {
            return;
        }
        processor.invoke(((FirProperty) FirDynamicScopeKt.getDynamicMembersStorage(this.session).getPropertiesCacheByName().getValue(name2, null)).getSymbol());
    }
}
