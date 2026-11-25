package org.jetbrains.kotlin.fir.symbols.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousObject;
import org.jetbrains.kotlin.fir.declarations.FirEnumEntry;
import org.jetbrains.kotlin.fir.expressions.FirAnonymousObjectExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.name.CallableId;

/* JADX INFO: compiled from: FirVariableSymbol.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u00078F¢\u0006\u0006\u001a\u0004\b\b\u0010\t¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/fir/symbols/impl/FirEnumEntrySymbol;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirVariableSymbol;", "Lorg/jetbrains/kotlin/fir/declarations/FirEnumEntry;", "callableId", "Lorg/jetbrains/kotlin/name/CallableId;", "(Lorg/jetbrains/kotlin/name/CallableId;)V", "initializerObjectSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirAnonymousObjectSymbol;", "getInitializerObjectSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirAnonymousObjectSymbol;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirEnumEntrySymbol extends FirVariableSymbol<FirEnumEntry> {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FirEnumEntrySymbol(CallableId callableId) {
        super(callableId, null);
        Intrinsics.checkNotNullParameter(callableId, "callableId");
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final FirAnonymousObjectSymbol getInitializerObjectSymbol() {
        FirAnonymousObject anonymousObject;
        FirExpression initializer = ((FirEnumEntry) getFir()).getInitializer();
        FirAnonymousObjectExpression firAnonymousObjectExpression = initializer instanceof FirAnonymousObjectExpression ? (FirAnonymousObjectExpression) initializer : null;
        if (firAnonymousObjectExpression == null || (anonymousObject = firAnonymousObjectExpression.getAnonymousObject()) == null) {
            return null;
        }
        return anonymousObject.getSymbol();
    }
}
