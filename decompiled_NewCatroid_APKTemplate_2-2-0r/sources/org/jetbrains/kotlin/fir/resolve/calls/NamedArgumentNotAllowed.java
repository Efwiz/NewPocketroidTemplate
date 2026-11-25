package org.jetbrains.kotlin.fir.resolve.calls;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.resolve.ForbiddenNamedArgumentsTarget;
import org.jetbrains.kotlin.resolve.calls.tower.CandidateApplicability;

/* JADX INFO: compiled from: ResolutionDiagnostic.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/NamedArgumentNotAllowed;", "Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionDiagnostic;", "argument", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "function", "Lorg/jetbrains/kotlin/fir/declarations/FirFunction;", "forbiddenNamedArgumentsTarget", "Lorg/jetbrains/kotlin/resolve/ForbiddenNamedArgumentsTarget;", "(Lorg/jetbrains/kotlin/fir/expressions/FirExpression;Lorg/jetbrains/kotlin/fir/declarations/FirFunction;Lorg/jetbrains/kotlin/resolve/ForbiddenNamedArgumentsTarget;)V", "getArgument", "()Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "getForbiddenNamedArgumentsTarget", "()Lorg/jetbrains/kotlin/resolve/ForbiddenNamedArgumentsTarget;", "getFunction", "()Lorg/jetbrains/kotlin/fir/declarations/FirFunction;", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NamedArgumentNotAllowed extends ResolutionDiagnostic {
    private final FirExpression argument;
    private final ForbiddenNamedArgumentsTarget forbiddenNamedArgumentsTarget;
    private final FirFunction function;

    public final FirExpression getArgument() {
        return this.argument;
    }

    public final FirFunction getFunction() {
        return this.function;
    }

    public final ForbiddenNamedArgumentsTarget getForbiddenNamedArgumentsTarget() {
        return this.forbiddenNamedArgumentsTarget;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NamedArgumentNotAllowed(FirExpression argument, FirFunction function, ForbiddenNamedArgumentsTarget forbiddenNamedArgumentsTarget) {
        super(CandidateApplicability.INAPPLICABLE_ARGUMENTS_MAPPING_ERROR);
        Intrinsics.checkNotNullParameter(argument, "argument");
        Intrinsics.checkNotNullParameter(function, "function");
        Intrinsics.checkNotNullParameter(forbiddenNamedArgumentsTarget, "forbiddenNamedArgumentsTarget");
        this.argument = argument;
        this.function = function;
        this.forbiddenNamedArgumentsTarget = forbiddenNamedArgumentsTarget;
    }
}
