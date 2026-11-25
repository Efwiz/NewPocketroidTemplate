package org.jetbrains.kotlin.resolve.calls.inference.model;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.kotlin.resolve.calls.tower.CandidateApplicability;

/* JADX INFO: compiled from: ConstraintPositionAndErrors.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001B\u000f\b\u0004\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u0082\u0001\u000b\u0007\b\t\n\u000b\f\r\u000e\u000f\u0010\u0011¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintSystemError;", "", "applicability", "Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateApplicability;", "(Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateApplicability;)V", "getApplicability", "()Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateApplicability;", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/CapturedTypeFromSubtyping;", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstrainingTypeIsError;", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/InferredEmptyIntersectionError;", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/InferredEmptyIntersectionWarning;", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/InferredIntoDeclaredUpperBounds;", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/LowerPriorityToPreserveCompatibility;", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/NewConstraintError;", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/NewConstraintWarning;", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/NoSuccessfulFork;", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/NotEnoughInformationForTypeParameter;", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/OnlyInputTypesDiagnostic;", "resolution.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ConstraintSystemError {
    private final CandidateApplicability applicability;

    public /* synthetic */ ConstraintSystemError(CandidateApplicability candidateApplicability, DefaultConstructorMarker defaultConstructorMarker) {
        this(candidateApplicability);
    }

    private ConstraintSystemError(CandidateApplicability candidateApplicability) {
        this.applicability = candidateApplicability;
    }

    public final CandidateApplicability getApplicability() {
        return this.applicability;
    }
}
