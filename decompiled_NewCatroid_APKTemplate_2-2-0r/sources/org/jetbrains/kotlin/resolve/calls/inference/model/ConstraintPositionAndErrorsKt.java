package org.jetbrains.kotlin.resolve.calls.inference.model;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ConstraintPositionAndErrors.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\u0003\u001a\u00020\u0004*\u00020\u0005¨\u0006\u0006"}, d2 = {"isExpectedTypePosition", "", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/Constraint;", "transformToWarning", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/NewConstraintWarning;", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/NewConstraintError;", "resolution.common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ConstraintPositionAndErrorsKt {
    public static final boolean isExpectedTypePosition(Constraint constraint) {
        Intrinsics.checkNotNullParameter(constraint, "<this>");
        return (constraint.getPosition().getFrom() instanceof ExpectedTypeConstraintPosition) || (constraint.getPosition().getFrom() instanceof DelegatedPropertyConstraintPosition);
    }

    public static final NewConstraintWarning transformToWarning(NewConstraintError newConstraintError) {
        Intrinsics.checkNotNullParameter(newConstraintError, "<this>");
        return new NewConstraintWarning(newConstraintError.getLowerType(), newConstraintError.getUpperType(), newConstraintError.getPosition());
    }
}
