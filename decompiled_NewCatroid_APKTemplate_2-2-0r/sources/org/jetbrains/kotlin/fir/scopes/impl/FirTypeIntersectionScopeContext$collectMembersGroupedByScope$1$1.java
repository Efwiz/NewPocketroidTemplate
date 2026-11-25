package org.jetbrains.kotlin.fir.scopes.impl;

import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirConstructorSymbol;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX INFO: Add missing generic type declarations: [D] */
/* JADX INFO: compiled from: FirTypeIntersectionScopeContext.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\u0000\u001a\u00020\u0001\"\f\b\u0000\u0010\u0002*\u0006\u0012\u0002\b\u00030\u00032\u0006\u0010\u0004\u001a\u0002H\u0002H\n¢\u0006\u0004\b\u0005\u0010\u0006"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "D", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "it", "invoke", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;)V"}, k = 3, mv = {1, 9, 0}, xi = 176)
final class FirTypeIntersectionScopeContext$collectMembersGroupedByScope$1$1<D> extends Lambda implements Function1<D, Unit> {
    final /* synthetic */ List<D> $resultForScope;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FirTypeIntersectionScopeContext$collectMembersGroupedByScope$1$1(List<D> list) {
        super(1);
        this.$resultForScope = list;
    }

    @Override // kotlin.jvm.functions.Function1
    public /* bridge */ /* synthetic */ Unit invoke(Object obj) {
        invoke((FirCallableSymbol) obj);
        return Unit.INSTANCE;
    }

    /* JADX WARN: Incorrect types in method signature: (TD;)V */
    public final void invoke(FirCallableSymbol it) {
        Intrinsics.checkNotNullParameter(it, "it");
        if (it instanceof FirConstructorSymbol) {
            return;
        }
        this.$resultForScope.add(it);
    }
}
