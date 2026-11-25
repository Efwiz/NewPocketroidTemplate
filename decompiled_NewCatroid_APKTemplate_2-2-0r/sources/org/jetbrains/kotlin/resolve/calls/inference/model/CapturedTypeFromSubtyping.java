package org.jetbrains.kotlin.resolve.calls.inference.model;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.resolve.calls.tower.CandidateApplicability;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.TypeVariableMarker;

/* JADX INFO: compiled from: ConstraintPositionAndErrors.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/model/CapturedTypeFromSubtyping;", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintSystemError;", "typeVariable", "Lorg/jetbrains/kotlin/types/model/TypeVariableMarker;", "constraintType", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "position", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintPosition;", "(Lorg/jetbrains/kotlin/types/model/TypeVariableMarker;Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintPosition;)V", "getConstraintType", "()Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "getPosition", "()Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintPosition;", "getTypeVariable", "()Lorg/jetbrains/kotlin/types/model/TypeVariableMarker;", "resolution.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CapturedTypeFromSubtyping extends ConstraintSystemError {
    private final KotlinTypeMarker constraintType;
    private final ConstraintPosition position;
    private final TypeVariableMarker typeVariable;

    public final TypeVariableMarker getTypeVariable() {
        return this.typeVariable;
    }

    public final KotlinTypeMarker getConstraintType() {
        return this.constraintType;
    }

    public final ConstraintPosition getPosition() {
        return this.position;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CapturedTypeFromSubtyping(TypeVariableMarker typeVariable, KotlinTypeMarker constraintType, ConstraintPosition position) {
        super(CandidateApplicability.INAPPLICABLE, null);
        Intrinsics.checkNotNullParameter(typeVariable, "typeVariable");
        Intrinsics.checkNotNullParameter(constraintType, "constraintType");
        Intrinsics.checkNotNullParameter(position, "position");
        this.typeVariable = typeVariable;
        this.constraintType = constraintType;
        this.position = position;
    }
}
