package org.jetbrains.kotlin.fir.symbols.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.name.CallableId;

/* JADX INFO: compiled from: FirFunctionSymbol.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b&\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\b\u0010\b\u001a\u00020\u0000H&R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/fir/symbols/impl/FirSyntheticPropertySymbol;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertySymbol;", "propertyId", "Lorg/jetbrains/kotlin/name/CallableId;", "getterId", "(Lorg/jetbrains/kotlin/name/CallableId;Lorg/jetbrains/kotlin/name/CallableId;)V", "getGetterId", "()Lorg/jetbrains/kotlin/name/CallableId;", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirSyntheticPropertySymbol extends FirPropertySymbol {
    private final CallableId getterId;

    public abstract FirSyntheticPropertySymbol copy();

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FirSyntheticPropertySymbol(CallableId propertyId, CallableId getterId) {
        super(propertyId);
        Intrinsics.checkNotNullParameter(propertyId, "propertyId");
        Intrinsics.checkNotNullParameter(getterId, "getterId");
        this.getterId = getterId;
    }

    public final CallableId getGetterId() {
        return this.getterId;
    }
}
