package org.jetbrains.kotlin.fir.resolve.dfa;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: DfaVariables.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B%\u0012\n\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0007J\r\u0010\r\u001a\u0006\u0012\u0002\b\u00030\u0003HÆ\u0003J\u000b\u0010\u000e\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u000b\u0010\u000f\u001a\u0004\u0018\u00010\u0005HÆ\u0003J/\u0010\u0010\u001a\u00020\u00002\f\b\u0002\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0005HÆ\u0001J\u0013\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0014\u001a\u00020\u0015HÖ\u0001J\b\u0010\u0016\u001a\u00020\u0017H\u0016R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\tR\u0015\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/dfa/Identifier;", "", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "dispatchReceiver", "Lorg/jetbrains/kotlin/fir/resolve/dfa/DataFlowVariable;", "extensionReceiver", "(Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;Lorg/jetbrains/kotlin/fir/resolve/dfa/DataFlowVariable;Lorg/jetbrains/kotlin/fir/resolve/dfa/DataFlowVariable;)V", "getDispatchReceiver", "()Lorg/jetbrains/kotlin/fir/resolve/dfa/DataFlowVariable;", "getExtensionReceiver", "getSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class Identifier {
    private final DataFlowVariable dispatchReceiver;
    private final DataFlowVariable extensionReceiver;
    private final FirBasedSymbol<?> symbol;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ Identifier copy$default(Identifier identifier, FirBasedSymbol firBasedSymbol, DataFlowVariable dataFlowVariable, DataFlowVariable dataFlowVariable2, int i, Object obj) {
        if ((i & 1) != 0) {
            firBasedSymbol = identifier.symbol;
        }
        if ((i & 2) != 0) {
            dataFlowVariable = identifier.dispatchReceiver;
        }
        if ((i & 4) != 0) {
            dataFlowVariable2 = identifier.extensionReceiver;
        }
        return identifier.copy(firBasedSymbol, dataFlowVariable, dataFlowVariable2);
    }

    public final FirBasedSymbol<?> component1() {
        return this.symbol;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final DataFlowVariable getDispatchReceiver() {
        return this.dispatchReceiver;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final DataFlowVariable getExtensionReceiver() {
        return this.extensionReceiver;
    }

    public final Identifier copy(FirBasedSymbol<?> symbol, DataFlowVariable dispatchReceiver, DataFlowVariable extensionReceiver) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        return new Identifier(symbol, dispatchReceiver, extensionReceiver);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Identifier)) {
            return false;
        }
        Identifier identifier = (Identifier) other;
        return Intrinsics.areEqual(this.symbol, identifier.symbol) && Intrinsics.areEqual(this.dispatchReceiver, identifier.dispatchReceiver) && Intrinsics.areEqual(this.extensionReceiver, identifier.extensionReceiver);
    }

    public int hashCode() {
        int iHashCode = this.symbol.hashCode() * 31;
        DataFlowVariable dataFlowVariable = this.dispatchReceiver;
        int iHashCode2 = (iHashCode + (dataFlowVariable == null ? 0 : dataFlowVariable.hashCode())) * 31;
        DataFlowVariable dataFlowVariable2 = this.extensionReceiver;
        return iHashCode2 + (dataFlowVariable2 != null ? dataFlowVariable2.hashCode() : 0);
    }

    public Identifier(FirBasedSymbol<?> symbol, DataFlowVariable dataFlowVariable, DataFlowVariable dataFlowVariable2) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        this.symbol = symbol;
        this.dispatchReceiver = dataFlowVariable;
        this.extensionReceiver = dataFlowVariable2;
    }

    public final FirBasedSymbol<?> getSymbol() {
        return this.symbol;
    }

    public final DataFlowVariable getDispatchReceiver() {
        return this.dispatchReceiver;
    }

    public final DataFlowVariable getExtensionReceiver() {
        return this.extensionReceiver;
    }

    public String toString() {
        FirBasedSymbol<?> firBasedSymbol = this.symbol;
        FirCallableSymbol firCallableSymbol = firBasedSymbol instanceof FirCallableSymbol ? (FirCallableSymbol) firBasedSymbol : null;
        return "[" + (firCallableSymbol != null ? firCallableSymbol.getCallableId() : null) + ", dispatchReceiver = " + this.dispatchReceiver + ", extensionReceiver = " + this.extensionReceiver + ']';
    }
}
