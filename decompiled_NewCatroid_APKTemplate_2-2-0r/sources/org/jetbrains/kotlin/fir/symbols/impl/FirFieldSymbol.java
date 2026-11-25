package org.jetbrains.kotlin.fir.symbols.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.declarations.FirField;
import org.jetbrains.kotlin.name.CallableId;

/* JADX INFO: compiled from: FirVariableSymbol.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\b\u0016\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005R\u0011\u0010\u0006\u001a\u00020\u00078F¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0011\u0010\n\u001a\u00020\u00078F¢\u0006\u0006\u001a\u0004\b\n\u0010\tR\u0011\u0010\u000b\u001a\u00020\u00078F¢\u0006\u0006\u001a\u0004\b\u000b\u0010\t¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/symbols/impl/FirFieldSymbol;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirVariableSymbol;", "Lorg/jetbrains/kotlin/fir/declarations/FirField;", "callableId", "Lorg/jetbrains/kotlin/name/CallableId;", "(Lorg/jetbrains/kotlin/name/CallableId;)V", "hasInitializer", "", "getHasInitializer", "()Z", "isVal", "isVar", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public class FirFieldSymbol extends FirVariableSymbol<FirField> {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FirFieldSymbol(CallableId callableId) {
        super(callableId, null);
        Intrinsics.checkNotNullParameter(callableId, "callableId");
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final boolean getHasInitializer() {
        return ((FirField) getFir()).getInitializer() != null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final boolean isVal() {
        return ((FirField) getFir()).isVal();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final boolean isVar() {
        return ((FirField) getFir()).isVar();
    }
}
