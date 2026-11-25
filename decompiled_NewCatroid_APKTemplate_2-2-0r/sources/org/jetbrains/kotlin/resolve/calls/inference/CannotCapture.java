package org.jetbrains.kotlin.resolve.calls.inference;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.resolve.calls.inference.constraintPosition.ConstraintPosition;

/* JADX INFO: compiled from: ConstraintError.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/CannotCapture;", "Lorg/jetbrains/kotlin/resolve/calls/inference/ConstraintError;", "constraintPosition", "Lorg/jetbrains/kotlin/resolve/calls/inference/constraintPosition/ConstraintPosition;", "typeVariable", "Lorg/jetbrains/kotlin/resolve/calls/inference/TypeVariable;", "(Lorg/jetbrains/kotlin/resolve/calls/inference/constraintPosition/ConstraintPosition;Lorg/jetbrains/kotlin/resolve/calls/inference/TypeVariable;)V", "getTypeVariable", "()Lorg/jetbrains/kotlin/resolve/calls/inference/TypeVariable;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CannotCapture extends ConstraintError {
    private final TypeVariable typeVariable;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CannotCapture(ConstraintPosition constraintPosition, TypeVariable typeVariable) {
        super(constraintPosition);
        Intrinsics.checkNotNullParameter(constraintPosition, "constraintPosition");
        Intrinsics.checkNotNullParameter(typeVariable, "typeVariable");
        this.typeVariable = typeVariable;
    }

    public final TypeVariable getTypeVariable() {
        return this.typeVariable;
    }
}
