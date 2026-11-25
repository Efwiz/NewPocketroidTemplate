package org.jetbrains.kotlin.fir.resolve.transformers;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.scopes.FakeOverrideTypeCalculator;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: ReturnTypeCalculator.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nJ\u0012\u0010\u0007\u001a\u00020\b2\n\u0010\u000b\u001a\u0006\u0012\u0002\b\u00030\fJ\u0012\u0010\r\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\nH&R\u0012\u0010\u0003\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/ReturnTypeCalculator;", "", "()V", "fakeOverrideTypeCalculator", "Lorg/jetbrains/kotlin/fir/scopes/FakeOverrideTypeCalculator;", "getFakeOverrideTypeCalculator", "()Lorg/jetbrains/kotlin/fir/scopes/FakeOverrideTypeCalculator;", "tryCalculateReturnType", "Lorg/jetbrains/kotlin/fir/types/FirResolvedTypeRef;", "declaration", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "tryCalculateReturnTypeOrNull", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ReturnTypeCalculator {
    public abstract FakeOverrideTypeCalculator getFakeOverrideTypeCalculator();

    public abstract FirResolvedTypeRef tryCalculateReturnTypeOrNull(FirCallableDeclaration declaration);

    public final FirResolvedTypeRef tryCalculateReturnType(FirCallableDeclaration declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        FirResolvedTypeRef firResolvedTypeRefTryCalculateReturnTypeOrNull = tryCalculateReturnTypeOrNull(declaration);
        Intrinsics.checkNotNull(firResolvedTypeRefTryCalculateReturnTypeOrNull);
        return firResolvedTypeRefTryCalculateReturnTypeOrNull;
    }

    public final FirResolvedTypeRef tryCalculateReturnType(FirCallableSymbol<?> symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        return tryCalculateReturnType((FirCallableDeclaration) symbol.getFir());
    }
}
