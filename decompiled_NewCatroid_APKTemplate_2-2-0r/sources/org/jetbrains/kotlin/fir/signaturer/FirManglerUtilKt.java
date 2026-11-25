package org.jetbrains.kotlin.fir.signaturer;

import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirVariable;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX INFO: compiled from: FirManglerUtil.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00058F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0006¨\u0006\u0007"}, d2 = {"irName", "Lorg/jetbrains/kotlin/name/Name;", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "getIrName", "(Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;)Lorg/jetbrains/kotlin/name/Name;", "Lorg/jetbrains/kotlin/fir/declarations/FirPropertyAccessor;", "(Lorg/jetbrains/kotlin/fir/declarations/FirPropertyAccessor;)Lorg/jetbrains/kotlin/name/Name;", "fir2ir"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirManglerUtilKt {
    public static final Name getIrName(FirCallableDeclaration firCallableDeclaration) {
        Intrinsics.checkNotNullParameter(firCallableDeclaration, "<this>");
        if (firCallableDeclaration instanceof FirConstructor) {
            return SpecialNames.INIT;
        }
        if (firCallableDeclaration instanceof FirSimpleFunction) {
            return ((FirSimpleFunction) firCallableDeclaration).getName();
        }
        if (firCallableDeclaration instanceof FirPropertyAccessor) {
            return getIrName((FirPropertyAccessor) firCallableDeclaration);
        }
        if (firCallableDeclaration instanceof FirVariable) {
            return ((FirVariable) firCallableDeclaration).getName();
        }
        if (firCallableDeclaration instanceof FirFunction) {
            return SpecialNames.ANONYMOUS;
        }
        throw new NoWhenBranchMatchedException();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final Name getIrName(FirPropertyAccessor firPropertyAccessor) {
        String str;
        Intrinsics.checkNotNullParameter(firPropertyAccessor, "<this>");
        if (firPropertyAccessor.getIsGetter()) {
            str = "<get-";
        } else {
            if (!firPropertyAccessor.isSetter()) {
                throw new IllegalStateException(("unknown property accessor kind " + firPropertyAccessor).toString());
            }
            str = "<set-";
        }
        Name nameSpecial = Name.special(str + ((FirProperty) firPropertyAccessor.getPropertySymbol().getFir()).getName() + '>');
        Intrinsics.checkNotNullExpressionValue(nameSpecial, "special(...)");
        return nameSpecial;
    }
}
