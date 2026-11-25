package org.jetbrains.kotlin.fir.symbols.impl;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.declarations.FirVariable;
import org.jetbrains.kotlin.name.CallableId;

/* JADX INFO: compiled from: FirVariableSymbol.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B\u000f\b\u0004\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b\u0082\u0001\u0007\t\n\u000b\f\r\u000e\u000f¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/fir/symbols/impl/FirVariableSymbol;", ExifInterface.LONGITUDE_EAST, "Lorg/jetbrains/kotlin/fir/declarations/FirVariable;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "callableId", "Lorg/jetbrains/kotlin/name/CallableId;", "(Lorg/jetbrains/kotlin/name/CallableId;)V", "getCallableId", "()Lorg/jetbrains/kotlin/name/CallableId;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirBackingFieldSymbol;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirDelegateFieldSymbol;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirEnumEntrySymbol;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirErrorPropertySymbol;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirFieldSymbol;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertySymbol;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirValueParameterSymbol;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirVariableSymbol<E extends FirVariable> extends FirCallableSymbol<E> {
    private final CallableId callableId;

    public /* synthetic */ FirVariableSymbol(CallableId callableId, DefaultConstructorMarker defaultConstructorMarker) {
        this(callableId);
    }

    private FirVariableSymbol(CallableId callableId) {
        this.callableId = callableId;
    }

    @Override // org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol
    public CallableId getCallableId() {
        return this.callableId;
    }
}
