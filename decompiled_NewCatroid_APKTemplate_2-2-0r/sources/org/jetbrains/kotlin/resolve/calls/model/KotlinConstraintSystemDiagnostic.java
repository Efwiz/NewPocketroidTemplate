package org.jetbrains.kotlin.resolve.calls.model;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintPositionAndErrorsKt;
import org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintSystemError;
import org.jetbrains.kotlin.resolve.calls.inference.model.NewConstraintError;

/* JADX INFO: compiled from: KotlinCallDiagnostics.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u00012\b\u0012\u0004\u0012\u00020\u00000\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\n\u0010\f\u001a\u0004\u0018\u00010\u0000H\u0016R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/model/KotlinConstraintSystemDiagnostic;", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallDiagnostic;", "Lorg/jetbrains/kotlin/resolve/calls/model/TransformableToWarning;", "error", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintSystemError;", "(Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintSystemError;)V", "getError", "()Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintSystemError;", "report", "", "reporter", "Lorg/jetbrains/kotlin/resolve/calls/model/DiagnosticReporter;", "transformToWarning", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinConstraintSystemDiagnostic extends KotlinCallDiagnostic implements TransformableToWarning<KotlinConstraintSystemDiagnostic> {
    private final ConstraintSystemError error;

    public final ConstraintSystemError getError() {
        return this.error;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KotlinConstraintSystemDiagnostic(ConstraintSystemError error) {
        super(error.getApplicability());
        Intrinsics.checkNotNullParameter(error, "error");
        this.error = error;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.KotlinCallDiagnostic
    public void report(DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        reporter.constraintError(this.error);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.TransformableToWarning
    public KotlinConstraintSystemDiagnostic transformToWarning() {
        ConstraintSystemError constraintSystemError = this.error;
        if (constraintSystemError instanceof NewConstraintError) {
            return new KotlinConstraintSystemDiagnostic(ConstraintPositionAndErrorsKt.transformToWarning((NewConstraintError) constraintSystemError));
        }
        return null;
    }
}
