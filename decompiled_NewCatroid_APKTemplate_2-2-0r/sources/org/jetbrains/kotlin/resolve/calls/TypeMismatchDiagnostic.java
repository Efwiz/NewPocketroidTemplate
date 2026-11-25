package org.jetbrains.kotlin.resolve.calls;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.resolve.calls.model.DiagnosticReporter;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCallDiagnostic;
import org.jetbrains.kotlin.resolve.calls.tower.CandidateApplicability;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: DiagnosticReporterImpl.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005¢\u0006\u0002\u0010\u0007J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016R\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\t¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/TypeMismatchDiagnostic;", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallDiagnostic;", "callArgument", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallArgument;", "expectedType", "Lorg/jetbrains/kotlin/types/KotlinType;", "actualType", "(Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallArgument;Lorg/jetbrains/kotlin/types/KotlinType;Lorg/jetbrains/kotlin/types/KotlinType;)V", "getActualType", "()Lorg/jetbrains/kotlin/types/KotlinType;", "getCallArgument", "()Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallArgument;", "getExpectedType", "report", "", "reporter", "Lorg/jetbrains/kotlin/resolve/calls/model/DiagnosticReporter;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TypeMismatchDiagnostic extends KotlinCallDiagnostic {
    private final KotlinType actualType;
    private final KotlinCallArgument callArgument;
    private final KotlinType expectedType;

    public final KotlinCallArgument getCallArgument() {
        return this.callArgument;
    }

    public final KotlinType getExpectedType() {
        return this.expectedType;
    }

    public final KotlinType getActualType() {
        return this.actualType;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TypeMismatchDiagnostic(KotlinCallArgument callArgument, KotlinType expectedType, KotlinType actualType) {
        super(CandidateApplicability.INAPPLICABLE);
        Intrinsics.checkNotNullParameter(callArgument, "callArgument");
        Intrinsics.checkNotNullParameter(expectedType, "expectedType");
        Intrinsics.checkNotNullParameter(actualType, "actualType");
        this.callArgument = callArgument;
        this.expectedType = expectedType;
        this.actualType = actualType;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.KotlinCallDiagnostic
    public void report(DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        reporter.onCallArgument(this.callArgument, this);
    }
}
