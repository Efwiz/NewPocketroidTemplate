package org.jetbrains.kotlin.fir.symbols.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.mpp.SimpleFunctionSymbolMarker;
import org.jetbrains.kotlin.name.CallableId;

/* JADX INFO: compiled from: FirFunctionSymbol.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0016\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirFunctionSymbol;", "Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;", "Lorg/jetbrains/kotlin/mpp/SimpleFunctionSymbolMarker;", "callableId", "Lorg/jetbrains/kotlin/name/CallableId;", "(Lorg/jetbrains/kotlin/name/CallableId;)V", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public class FirNamedFunctionSymbol extends FirFunctionSymbol<FirSimpleFunction> implements SimpleFunctionSymbolMarker {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FirNamedFunctionSymbol(CallableId callableId) {
        super(callableId, null);
        Intrinsics.checkNotNullParameter(callableId, "callableId");
    }
}
