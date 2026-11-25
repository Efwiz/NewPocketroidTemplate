package org.jetbrains.kotlin.fir.resolve.calls;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.expressions.FirExpression;

/* JADX INFO: compiled from: ResolutionDiagnostic.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/ArgumentPassedTwice;", "Lorg/jetbrains/kotlin/fir/resolve/calls/InapplicableArgumentDiagnostic;", "argument", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "valueParameter", "Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;", "(Lorg/jetbrains/kotlin/fir/expressions/FirExpression;Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;)V", "getArgument", "()Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "getValueParameter", "()Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ArgumentPassedTwice extends InapplicableArgumentDiagnostic {
    private final FirExpression argument;
    private final FirValueParameter valueParameter;

    @Override // org.jetbrains.kotlin.fir.resolve.calls.InapplicableArgumentDiagnostic
    public FirExpression getArgument() {
        return this.argument;
    }

    public final FirValueParameter getValueParameter() {
        return this.valueParameter;
    }

    public ArgumentPassedTwice(FirExpression argument, FirValueParameter valueParameter) {
        Intrinsics.checkNotNullParameter(argument, "argument");
        Intrinsics.checkNotNullParameter(valueParameter, "valueParameter");
        this.argument = argument;
        this.valueParameter = valueParameter;
    }
}
