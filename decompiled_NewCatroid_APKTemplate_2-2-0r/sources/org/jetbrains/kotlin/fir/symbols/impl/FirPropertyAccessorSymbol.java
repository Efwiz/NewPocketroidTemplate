package org.jetbrains.kotlin.fir.symbols.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.Implementation;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FirFunctionSymbol.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0003R\u0011\u0010\u0004\u001a\u00020\u00058F¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\u00058F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\u0006R\u0011\u0010\b\u001a\u00020\t8F¢\u0006\u0006\u001a\u0004\b\n\u0010\u000b¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertyAccessorSymbol;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirFunctionWithoutNameSymbol;", "Lorg/jetbrains/kotlin/fir/declarations/FirPropertyAccessor;", "()V", "isGetter", "", "()Z", "isSetter", "propertySymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertySymbol;", "getPropertySymbol", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertySymbol;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirPropertyAccessorSymbol extends FirFunctionWithoutNameSymbol<FirPropertyAccessor> {
    /* JADX WARN: Illegal instructions before constructor call */
    public FirPropertyAccessorSymbol() {
        Name nameIdentifier = Name.identifier(Implementation.Context.Default.ACCESSOR_METHOD_SUFFIX);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        super(nameIdentifier, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final boolean isGetter() {
        return ((FirPropertyAccessor) getFir()).getIsGetter();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final boolean isSetter() {
        return ((FirPropertyAccessor) getFir()).isSetter();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final FirPropertySymbol getPropertySymbol() {
        return ((FirPropertyAccessor) getFir()).getPropertySymbol();
    }
}
