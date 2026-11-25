package org.jetbrains.kotlin.fir.resolve.calls;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.fir.symbols.SyntheticSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirSyntheticPropertySymbol;
import org.jetbrains.kotlin.name.CallableId;

/* JADX INFO: compiled from: Synthetics.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u00012\u00020\u0002B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0004¢\u0006\u0002\u0010\u0006J\b\u0010\u0007\u001a\u00020\u0001H\u0016¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/FirSimpleSyntheticPropertySymbol;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirSyntheticPropertySymbol;", "Lorg/jetbrains/kotlin/fir/symbols/SyntheticSymbol;", "propertyId", "Lorg/jetbrains/kotlin/name/CallableId;", "getterId", "(Lorg/jetbrains/kotlin/name/CallableId;Lorg/jetbrains/kotlin/name/CallableId;)V", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirSimpleSyntheticPropertySymbol extends FirSyntheticPropertySymbol implements SyntheticSymbol {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FirSimpleSyntheticPropertySymbol(CallableId propertyId, CallableId getterId) {
        super(propertyId, getterId);
        Intrinsics.checkNotNullParameter(propertyId, "propertyId");
        Intrinsics.checkNotNullParameter(getterId, "getterId");
    }

    @Override // org.jetbrains.kotlin.fir.symbols.impl.FirSyntheticPropertySymbol
    public FirSyntheticPropertySymbol copy() {
        return new FirSimpleSyntheticPropertySymbol(getCallableId(), getGetterId());
    }
}
