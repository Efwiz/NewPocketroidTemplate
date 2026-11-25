package org.jetbrains.kotlin.fir.symbols.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.antlr.stringtemplate.StringTemplate;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.fir.FirLabel;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FirFunctionSymbol.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0003R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u00058F¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/fir/symbols/impl/FirAnonymousFunctionSymbol;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirFunctionWithoutNameSymbol;", "Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousFunction;", "()V", CoroutineCodegenUtilKt.COROUTINE_LABEL_FIELD_NAME, "Lorg/jetbrains/kotlin/fir/FirLabel;", "getLabel", "()Lorg/jetbrains/kotlin/fir/FirLabel;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirAnonymousFunctionSymbol extends FirFunctionWithoutNameSymbol<FirAnonymousFunction> {
    /* JADX WARN: Illegal instructions before constructor call */
    public FirAnonymousFunctionSymbol() {
        Name nameIdentifier = Name.identifier(StringTemplate.ANONYMOUS_ST_NAME);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        super(nameIdentifier, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final FirLabel getLabel() {
        return ((FirAnonymousFunction) getFir()).getLabel();
    }
}
