package org.jetbrains.kotlin.fir.resolve.calls;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.symbols.SyntheticSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.name.CallableId;

/* JADX INFO: compiled from: Synthetics.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/FirSyntheticFunctionSymbol;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;", "Lorg/jetbrains/kotlin/fir/symbols/SyntheticSymbol;", "callableId", "Lorg/jetbrains/kotlin/name/CallableId;", "(Lorg/jetbrains/kotlin/name/CallableId;)V", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirSyntheticFunctionSymbol extends FirNamedFunctionSymbol implements SyntheticSymbol {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FirSyntheticFunctionSymbol(CallableId callableId) {
        super(callableId);
        Intrinsics.checkNotNullParameter(callableId, "callableId");
    }
}
