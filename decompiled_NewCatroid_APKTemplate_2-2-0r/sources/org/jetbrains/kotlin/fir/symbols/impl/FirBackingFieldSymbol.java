package org.jetbrains.kotlin.fir.symbols.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.declarations.FirBackingField;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor;
import org.jetbrains.kotlin.name.CallableId;

/* JADX INFO: compiled from: FirVariableSymbol.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u00078F¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0011\u0010\n\u001a\u00020\u000b8F¢\u0006\u0006\u001a\u0004\b\n\u0010\fR\u0011\u0010\r\u001a\u00020\u000b8F¢\u0006\u0006\u001a\u0004\b\r\u0010\fR\u0011\u0010\u000e\u001a\u00020\u000f8F¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/fir/symbols/impl/FirBackingFieldSymbol;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirVariableSymbol;", "Lorg/jetbrains/kotlin/fir/declarations/FirBackingField;", "callableId", "Lorg/jetbrains/kotlin/name/CallableId;", "(Lorg/jetbrains/kotlin/name/CallableId;)V", "getterSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertyAccessorSymbol;", "getGetterSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertyAccessorSymbol;", "isVal", "", "()Z", "isVar", "propertySymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertySymbol;", "getPropertySymbol", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertySymbol;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirBackingFieldSymbol extends FirVariableSymbol<FirBackingField> {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FirBackingFieldSymbol(CallableId callableId) {
        super(callableId, null);
        Intrinsics.checkNotNullParameter(callableId, "callableId");
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final boolean isVal() {
        return ((FirBackingField) getFir()).getIsVal();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final boolean isVar() {
        return ((FirBackingField) getFir()).getIsVar();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final FirPropertySymbol getPropertySymbol() {
        return ((FirBackingField) getFir()).getPropertySymbol();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final FirPropertyAccessorSymbol getGetterSymbol() {
        FirPropertyAccessor getter = ((FirProperty) ((FirBackingField) getFir()).getPropertySymbol().getFir()).getGetter();
        if (getter != null) {
            return getter.getSymbol();
        }
        return null;
    }
}
