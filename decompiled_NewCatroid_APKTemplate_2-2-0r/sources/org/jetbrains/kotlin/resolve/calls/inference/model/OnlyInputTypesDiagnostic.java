package org.jetbrains.kotlin.resolve.calls.inference.model;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.resolve.calls.tower.CandidateApplicability;
import org.jetbrains.kotlin.types.model.TypeVariableMarker;

/* JADX INFO: compiled from: ConstraintPositionAndErrors.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/model/OnlyInputTypesDiagnostic;", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintSystemError;", "typeVariable", "Lorg/jetbrains/kotlin/types/model/TypeVariableMarker;", "(Lorg/jetbrains/kotlin/types/model/TypeVariableMarker;)V", "getTypeVariable", "()Lorg/jetbrains/kotlin/types/model/TypeVariableMarker;", "resolution.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class OnlyInputTypesDiagnostic extends ConstraintSystemError {
    private final TypeVariableMarker typeVariable;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public OnlyInputTypesDiagnostic(TypeVariableMarker typeVariable) {
        super(CandidateApplicability.INAPPLICABLE, null);
        Intrinsics.checkNotNullParameter(typeVariable, "typeVariable");
        this.typeVariable = typeVariable;
    }

    public final TypeVariableMarker getTypeVariable() {
        return this.typeVariable;
    }
}
