package org.jetbrains.kotlin.fir.scopes;

import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FirTypeScope.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001:\u0001\fB\u0005¢\u0006\u0002\u0010\u0002J*\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0018\u0010\u0007\u001a\u0014\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u00020\u00040\bH&J*\u0010\t\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u000b2\u0018\u0010\u0007\u001a\u0014\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u00020\u00040\bH&¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/fir/scopes/FirTypeScope;", "Lorg/jetbrains/kotlin/fir/scopes/FirContainingNamesAwareScope;", "()V", "processDirectOverriddenFunctionsWithBaseScope", "Lorg/jetbrains/kotlin/fir/scopes/ProcessorAction;", "functionSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;", "processor", "Lkotlin/Function2;", "processDirectOverriddenPropertiesWithBaseScope", "propertySymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertySymbol;", "Empty", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirTypeScope extends FirContainingNamesAwareScope {
    public abstract ProcessorAction processDirectOverriddenFunctionsWithBaseScope(FirNamedFunctionSymbol functionSymbol, Function2<? super FirNamedFunctionSymbol, ? super FirTypeScope, ? extends ProcessorAction> processor);

    public abstract ProcessorAction processDirectOverriddenPropertiesWithBaseScope(FirPropertySymbol propertySymbol, Function2<? super FirPropertySymbol, ? super FirTypeScope, ? extends ProcessorAction> processor);

    /* JADX INFO: compiled from: FirTypeScope.kt */
    @Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H\u0016J\u000e\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H\u0016J*\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0018\u0010\u000b\u001a\u0014\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\b0\fH\u0016J*\u0010\r\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\u000f2\u0018\u0010\u000b\u001a\u0014\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\b0\fH\u0016J\b\u0010\u0010\u001a\u00020\u0011H\u0016¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/fir/scopes/FirTypeScope$Empty;", "Lorg/jetbrains/kotlin/fir/scopes/FirTypeScope;", "()V", "getCallableNames", "", "Lorg/jetbrains/kotlin/name/Name;", "getClassifierNames", "processDirectOverriddenFunctionsWithBaseScope", "Lorg/jetbrains/kotlin/fir/scopes/ProcessorAction;", "functionSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;", "processor", "Lkotlin/Function2;", "processDirectOverriddenPropertiesWithBaseScope", "propertySymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertySymbol;", "toString", "", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Empty extends FirTypeScope {
        public static final Empty INSTANCE = new Empty();

        private Empty() {
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

        public String toString() {
            return "Empty scope";
        }
    }
}
