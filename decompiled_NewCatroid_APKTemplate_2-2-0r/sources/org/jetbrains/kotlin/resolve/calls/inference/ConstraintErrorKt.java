package org.jetbrains.kotlin.resolve.calls.inference;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.resolve.calls.inference.constraintPosition.ConstraintPosition;

/* JADX INFO: compiled from: ConstraintError.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u000e\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003¨\u0006\u0004"}, d2 = {"newTypeInferenceOrParameterConstraintError", "Lorg/jetbrains/kotlin/resolve/calls/inference/ConstraintError;", "constraintPosition", "Lorg/jetbrains/kotlin/resolve/calls/inference/constraintPosition/ConstraintPosition;", "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ConstraintErrorKt {
    public static final ConstraintError newTypeInferenceOrParameterConstraintError(ConstraintPosition constraintPosition) {
        Intrinsics.checkNotNullParameter(constraintPosition, "constraintPosition");
        return constraintPosition.isParameter() ? new ParameterConstraintError(constraintPosition) : new TypeInferenceError(constraintPosition);
    }
}
