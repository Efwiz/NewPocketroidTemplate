package org.jetbrains.kotlin.resolve.calls.inference.model;

import kotlin.Metadata;

/* JADX INFO: compiled from: ConstraintPositionAndErrors.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/model/BuilderInferencePosition;", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintPosition;", "()V", "toString", "", "resolution.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BuilderInferencePosition extends ConstraintPosition {
    public static final BuilderInferencePosition INSTANCE = new BuilderInferencePosition();

    private BuilderInferencePosition() {
        super(null);
    }

    public String toString() {
        return "For builder inference call";
    }
}
