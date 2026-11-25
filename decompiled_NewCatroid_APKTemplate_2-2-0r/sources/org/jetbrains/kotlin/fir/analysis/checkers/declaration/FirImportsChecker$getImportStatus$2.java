package org.jetbrains.kotlin.fir.analysis.checkers.declaration;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Ref;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirVariableSymbol;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX INFO: compiled from: FirImportsChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u00012\n\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003H\n¢\u0006\u0002\b\u0004"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "sym", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirVariableSymbol;", "invoke"}, k = 3, mv = {1, 9, 0}, xi = 48)
public final class FirImportsChecker$getImportStatus$2 extends Lambda implements Function1<FirVariableSymbol<?>, Unit> {
    final /* synthetic */ CheckerContext $context;
    final /* synthetic */ Ref.BooleanRef $found;
    final /* synthetic */ Function1<FirCallableSymbol<?>, Boolean> $isApplicable;
    final /* synthetic */ Ref.ObjectRef<FirCallableSymbol<?>> $symbol;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public FirImportsChecker$getImportStatus$2(CheckerContext checkerContext, Function1<? super FirCallableSymbol<?>, Boolean> function1, Ref.BooleanRef booleanRef, Ref.ObjectRef<FirCallableSymbol<?>> objectRef) {
        super(1);
        this.$context = checkerContext;
        this.$isApplicable = function1;
        this.$found = booleanRef;
        this.$symbol = objectRef;
    }

    @Override // kotlin.jvm.functions.Function1
    public /* bridge */ /* synthetic */ Unit invoke(FirVariableSymbol<?> firVariableSymbol) {
        invoke2(firVariableSymbol);
        return Unit.INSTANCE;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
    public final void invoke2(FirVariableSymbol<?> sym) {
        Intrinsics.checkNotNullParameter(sym, "sym");
        if (FirImportsChecker.INSTANCE.isVisible((FirMemberDeclaration) sym.getFir(), this.$context) && this.$isApplicable.invoke(sym).booleanValue()) {
            this.$found.element = true;
        }
        this.$symbol.element = sym;
    }
}
