package org.jetbrains.kotlin.fir.backend;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.ir.symbols.IrValueParameterSymbol;
import org.jetbrains.kotlin.ir.symbols.impl.IrValueParameterSymbolImpl;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: CodeFragmentUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001B!\u0012\n\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bR\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\rR\u0015\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/fir/backend/InjectedValue;", "", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "typeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "isMutated", "", "(Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;Lorg/jetbrains/kotlin/fir/types/FirTypeRef;Z)V", "irParameterSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrValueParameterSymbol;", "getIrParameterSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrValueParameterSymbol;", "()Z", "getSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "getTypeRef", "()Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "fir2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class InjectedValue {
    private final IrValueParameterSymbol irParameterSymbol;
    private final boolean isMutated;
    private final FirBasedSymbol<?> symbol;
    private final FirTypeRef typeRef;

    /* JADX WARN: Multi-variable type inference failed */
    public InjectedValue(FirBasedSymbol<?> symbol, FirTypeRef typeRef, boolean z) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(typeRef, "typeRef");
        this.symbol = symbol;
        this.typeRef = typeRef;
        this.isMutated = z;
        this.irParameterSymbol = new IrValueParameterSymbolImpl(null, 1, 0 == true ? 1 : 0);
    }

    public final FirBasedSymbol<?> getSymbol() {
        return this.symbol;
    }

    public final FirTypeRef getTypeRef() {
        return this.typeRef;
    }

    /* JADX INFO: renamed from: isMutated, reason: from getter */
    public final boolean getIsMutated() {
        return this.isMutated;
    }

    public final IrValueParameterSymbol getIrParameterSymbol() {
        return this.irParameterSymbol;
    }
}
