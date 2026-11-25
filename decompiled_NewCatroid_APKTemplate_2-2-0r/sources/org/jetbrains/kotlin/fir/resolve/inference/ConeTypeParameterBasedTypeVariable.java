package org.jetbrains.kotlin.fir.resolve.inference;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeParameterSymbol;
import org.jetbrains.kotlin.fir.types.ConeTypeVariable;

/* JADX INFO: compiled from: ConeTypeVariables.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/inference/ConeTypeParameterBasedTypeVariable;", "Lorg/jetbrains/kotlin/fir/types/ConeTypeVariable;", "typeParameterSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirTypeParameterSymbol;", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirTypeParameterSymbol;)V", "getTypeParameterSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirTypeParameterSymbol;", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConeTypeParameterBasedTypeVariable extends ConeTypeVariable {
    private final FirTypeParameterSymbol typeParameterSymbol;

    public final FirTypeParameterSymbol getTypeParameterSymbol() {
        return this.typeParameterSymbol;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public ConeTypeParameterBasedTypeVariable(FirTypeParameterSymbol typeParameterSymbol) {
        Intrinsics.checkNotNullParameter(typeParameterSymbol, "typeParameterSymbol");
        String identifier = typeParameterSymbol.getName().getIdentifier();
        Intrinsics.checkNotNullExpressionValue(identifier, "getIdentifier(...)");
        super(identifier, typeParameterSymbol.getLookupTag());
        this.typeParameterSymbol = typeParameterSymbol;
    }
}
