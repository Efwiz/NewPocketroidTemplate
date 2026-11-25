package org.jetbrains.kotlin.resolve.calls.model;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.resolve.calls.tower.CandidateApplicability;
import org.jetbrains.kotlin.types.UnwrappedType;

/* JADX INFO: compiled from: KotlinCallDiagnostics.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\u001d\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0004\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016R\u0014\u0010\u0005\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\nR\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/model/ArgumentNullabilityWarningDiagnostic;", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallDiagnostic;", "Lorg/jetbrains/kotlin/resolve/calls/model/ArgumentNullabilityMismatchDiagnostic;", "expectedType", "Lorg/jetbrains/kotlin/types/UnwrappedType;", "actualType", "expressionArgument", "Lorg/jetbrains/kotlin/resolve/calls/model/ExpressionKotlinCallArgument;", "(Lorg/jetbrains/kotlin/types/UnwrappedType;Lorg/jetbrains/kotlin/types/UnwrappedType;Lorg/jetbrains/kotlin/resolve/calls/model/ExpressionKotlinCallArgument;)V", "getActualType", "()Lorg/jetbrains/kotlin/types/UnwrappedType;", "getExpectedType", "getExpressionArgument", "()Lorg/jetbrains/kotlin/resolve/calls/model/ExpressionKotlinCallArgument;", "report", "", "reporter", "Lorg/jetbrains/kotlin/resolve/calls/model/DiagnosticReporter;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ArgumentNullabilityWarningDiagnostic extends KotlinCallDiagnostic implements ArgumentNullabilityMismatchDiagnostic {
    private final UnwrappedType actualType;
    private final UnwrappedType expectedType;
    private final ExpressionKotlinCallArgument expressionArgument;

    @Override // org.jetbrains.kotlin.resolve.calls.model.ArgumentNullabilityMismatchDiagnostic
    public UnwrappedType getExpectedType() {
        return this.expectedType;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ArgumentNullabilityMismatchDiagnostic
    public UnwrappedType getActualType() {
        return this.actualType;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ArgumentNullabilityMismatchDiagnostic
    public ExpressionKotlinCallArgument getExpressionArgument() {
        return this.expressionArgument;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ArgumentNullabilityWarningDiagnostic(UnwrappedType expectedType, UnwrappedType actualType, ExpressionKotlinCallArgument expressionArgument) {
        super(CandidateApplicability.RESOLVED);
        Intrinsics.checkNotNullParameter(expectedType, "expectedType");
        Intrinsics.checkNotNullParameter(actualType, "actualType");
        Intrinsics.checkNotNullParameter(expressionArgument, "expressionArgument");
        this.expectedType = expectedType;
        this.actualType = actualType;
        this.expressionArgument = expressionArgument;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.KotlinCallDiagnostic
    public void report(DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        reporter.onCallArgument(getExpressionArgument(), this);
    }
}
