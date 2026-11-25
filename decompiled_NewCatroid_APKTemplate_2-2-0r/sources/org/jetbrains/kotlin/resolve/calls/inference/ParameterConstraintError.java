package org.jetbrains.kotlin.resolve.calls.inference;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.resolve.calls.inference.constraintPosition.ConstraintPosition;

/* JADX INFO: compiled from: ConstraintError.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/ParameterConstraintError;", "Lorg/jetbrains/kotlin/resolve/calls/inference/ConstraintError;", "constraintPosition", "Lorg/jetbrains/kotlin/resolve/calls/inference/constraintPosition/ConstraintPosition;", "(Lorg/jetbrains/kotlin/resolve/calls/inference/constraintPosition/ConstraintPosition;)V", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ParameterConstraintError extends ConstraintError {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ParameterConstraintError(ConstraintPosition constraintPosition) {
        super(constraintPosition);
        Intrinsics.checkNotNullParameter(constraintPosition, "constraintPosition");
    }
}
