package org.jetbrains.kotlin.fir.symbols.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.mpp.ValueParameterSymbolMarker;
import org.jetbrains.kotlin.name.CallableId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FirVariableSymbol.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0006\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0015\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\b8F¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0011\u0010\u000b\u001a\u00020\f8F¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u000f\u001a\u00020\f8F¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u000eR\u0011\u0010\u0010\u001a\u00020\f8F¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u000eR\u0011\u0010\u0011\u001a\u00020\f8F¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u000e¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/fir/symbols/impl/FirValueParameterSymbol;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirVariableSymbol;", "Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;", "Lorg/jetbrains/kotlin/mpp/ValueParameterSymbolMarker;", "name", "Lorg/jetbrains/kotlin/name/Name;", "(Lorg/jetbrains/kotlin/name/Name;)V", "containingFunctionSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirFunctionSymbol;", "getContainingFunctionSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirFunctionSymbol;", "hasDefaultValue", "", "getHasDefaultValue", "()Z", "isCrossinline", "isNoinline", "isVararg", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirValueParameterSymbol extends FirVariableSymbol<FirValueParameter> implements ValueParameterSymbolMarker {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FirValueParameterSymbol(Name name2) {
        super(new CallableId(name2, (FqName) null, 2, (DefaultConstructorMarker) null), null);
        Intrinsics.checkNotNullParameter(name2, "name");
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final boolean getHasDefaultValue() {
        return ((FirValueParameter) getFir()).getDefaultValue() != null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final boolean isCrossinline() {
        return ((FirValueParameter) getFir()).isCrossinline();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final boolean isNoinline() {
        return ((FirValueParameter) getFir()).isNoinline();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final boolean isVararg() {
        return ((FirValueParameter) getFir()).isVararg();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final FirFunctionSymbol<?> getContainingFunctionSymbol() {
        return ((FirValueParameter) getFir()).getContainingFunctionSymbol();
    }
}
