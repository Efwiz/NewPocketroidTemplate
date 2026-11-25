package org.jetbrains.kotlin.fir.resolve.inference.model;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.resolve.calls.inference.model.BuilderInferenceSubstitutionConstraintPosition;
import org.jetbrains.kotlin.resolve.calls.inference.model.InitialConstraint;

/* JADX INFO: compiled from: FirConstraintPositionAndErrors.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/inference/model/ConeBuilderInferenceSubstitutionConstraintPosition;", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/BuilderInferenceSubstitutionConstraintPosition;", "", "initialConstraint", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/InitialConstraint;", "(Lorg/jetbrains/kotlin/resolve/calls/inference/model/InitialConstraint;)V", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConeBuilderInferenceSubstitutionConstraintPosition extends BuilderInferenceSubstitutionConstraintPosition {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ConeBuilderInferenceSubstitutionConstraintPosition(InitialConstraint initialConstraint) {
        super(null, initialConstraint, false, 4, null);
        Intrinsics.checkNotNullParameter(initialConstraint, "initialConstraint");
    }
}
