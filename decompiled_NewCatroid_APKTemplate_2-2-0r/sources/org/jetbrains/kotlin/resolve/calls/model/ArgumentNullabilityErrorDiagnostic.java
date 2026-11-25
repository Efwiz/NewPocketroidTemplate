package org.jetbrains.kotlin.resolve.calls.model;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.resolve.calls.tower.CandidateApplicability;
import org.jetbrains.kotlin.types.UnwrappedType;

/* JADX INFO: compiled from: KotlinCallDiagnostics.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u00012\b\u0012\u0004\u0012\u00020\u00030\u00022\u00020\u0004B\u001d\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0006\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J\b\u0010\u0014\u001a\u00020\u0003H\u0016R\u0014\u0010\u0007\u001a\u00020\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\u0005\u001a\u00020\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\fR\u0014\u0010\b\u001a\u00020\tX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/model/ArgumentNullabilityErrorDiagnostic;", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallDiagnostic;", "Lorg/jetbrains/kotlin/resolve/calls/model/TransformableToWarning;", "Lorg/jetbrains/kotlin/resolve/calls/model/ArgumentNullabilityWarningDiagnostic;", "Lorg/jetbrains/kotlin/resolve/calls/model/ArgumentNullabilityMismatchDiagnostic;", "expectedType", "Lorg/jetbrains/kotlin/types/UnwrappedType;", "actualType", "expressionArgument", "Lorg/jetbrains/kotlin/resolve/calls/model/ExpressionKotlinCallArgument;", "(Lorg/jetbrains/kotlin/types/UnwrappedType;Lorg/jetbrains/kotlin/types/UnwrappedType;Lorg/jetbrains/kotlin/resolve/calls/model/ExpressionKotlinCallArgument;)V", "getActualType", "()Lorg/jetbrains/kotlin/types/UnwrappedType;", "getExpectedType", "getExpressionArgument", "()Lorg/jetbrains/kotlin/resolve/calls/model/ExpressionKotlinCallArgument;", "report", "", "reporter", "Lorg/jetbrains/kotlin/resolve/calls/model/DiagnosticReporter;", "transformToWarning", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ArgumentNullabilityErrorDiagnostic extends KotlinCallDiagnostic implements ArgumentNullabilityMismatchDiagnostic, TransformableToWarning<ArgumentNullabilityWarningDiagnostic> {
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
    public ArgumentNullabilityErrorDiagnostic(UnwrappedType expectedType, UnwrappedType actualType, ExpressionKotlinCallArgument expressionArgument) {
        super(CandidateApplicability.UNSAFE_CALL);
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

    @Override // org.jetbrains.kotlin.resolve.calls.model.TransformableToWarning
    public ArgumentNullabilityWarningDiagnostic transformToWarning() {
        return new ArgumentNullabilityWarningDiagnostic(getExpectedType(), getActualType(), getExpressionArgument());
    }
}
