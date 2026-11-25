package org.jetbrains.kotlin.fir.resolve.calls;

import kotlin.Metadata;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.resolve.calls.tower.CandidateApplicability;

/* JADX INFO: compiled from: ResolutionDiagnostic.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R\u0012\u0010\u0003\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/InapplicableArgumentDiagnostic;", "Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionDiagnostic;", "()V", "argument", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "getArgument", "()Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class InapplicableArgumentDiagnostic extends ResolutionDiagnostic {
    public abstract FirExpression getArgument();

    public InapplicableArgumentDiagnostic() {
        super(CandidateApplicability.INAPPLICABLE);
    }
}
