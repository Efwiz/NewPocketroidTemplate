package org.jetbrains.kotlin.fir.symbols.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.declarations.FirErrorFunction;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FirFunctionSymbol.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lorg/jetbrains/kotlin/fir/symbols/impl/FirErrorFunctionSymbol;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirFunctionWithoutNameSymbol;", "Lorg/jetbrains/kotlin/fir/declarations/FirErrorFunction;", "()V", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirErrorFunctionSymbol extends FirFunctionWithoutNameSymbol<FirErrorFunction> {
    /* JADX WARN: Illegal instructions before constructor call */
    public FirErrorFunctionSymbol() {
        Name nameIdentifier = Name.identifier("error");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        super(nameIdentifier, null);
    }
}
