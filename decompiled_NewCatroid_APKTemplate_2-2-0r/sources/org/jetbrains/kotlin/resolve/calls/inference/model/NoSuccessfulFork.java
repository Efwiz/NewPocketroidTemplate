package org.jetbrains.kotlin.resolve.calls.inference.model;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.resolve.calls.tower.CandidateApplicability;

/* JADX INFO: compiled from: ConstraintPositionAndErrors.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/model/NoSuccessfulFork;", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintSystemError;", "position", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/IncorporationConstraintPosition;", "(Lorg/jetbrains/kotlin/resolve/calls/inference/model/IncorporationConstraintPosition;)V", "getPosition", "()Lorg/jetbrains/kotlin/resolve/calls/inference/model/IncorporationConstraintPosition;", "resolution.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NoSuccessfulFork extends ConstraintSystemError {
    private final IncorporationConstraintPosition position;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NoSuccessfulFork(IncorporationConstraintPosition position) {
        super(CandidateApplicability.INAPPLICABLE, null);
        Intrinsics.checkNotNullParameter(position, "position");
        this.position = position;
    }

    public final IncorporationConstraintPosition getPosition() {
        return this.position;
    }
}
