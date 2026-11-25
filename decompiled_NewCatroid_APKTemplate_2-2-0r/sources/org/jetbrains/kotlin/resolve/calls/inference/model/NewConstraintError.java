package org.jetbrains.kotlin.resolve.calls.inference.model;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.resolve.calls.tower.CandidateApplicability;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;

/* JADX INFO: compiled from: ConstraintPositionAndErrors.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\u001d\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0004\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\b\u0010\u000e\u001a\u00020\u000fH\u0016R\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\u0005\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\n¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/model/NewConstraintError;", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintSystemError;", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/NewConstraintMismatch;", "lowerType", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "upperType", "position", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/IncorporationConstraintPosition;", "(Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;Lorg/jetbrains/kotlin/resolve/calls/inference/model/IncorporationConstraintPosition;)V", "getLowerType", "()Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "getPosition", "()Lorg/jetbrains/kotlin/resolve/calls/inference/model/IncorporationConstraintPosition;", "getUpperType", "toString", "", "resolution.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NewConstraintError extends ConstraintSystemError implements NewConstraintMismatch {
    private final KotlinTypeMarker lowerType;
    private final IncorporationConstraintPosition position;
    private final KotlinTypeMarker upperType;

    @Override // org.jetbrains.kotlin.resolve.calls.inference.model.NewConstraintMismatch
    public KotlinTypeMarker getLowerType() {
        return this.lowerType;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.model.NewConstraintMismatch
    public KotlinTypeMarker getUpperType() {
        return this.upperType;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.model.NewConstraintMismatch
    public IncorporationConstraintPosition getPosition() {
        return this.position;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NewConstraintError(KotlinTypeMarker lowerType, KotlinTypeMarker upperType, IncorporationConstraintPosition position) {
        super(position.getFrom() instanceof ReceiverConstraintPosition ? CandidateApplicability.INAPPLICABLE_WRONG_RECEIVER : CandidateApplicability.INAPPLICABLE, null);
        Intrinsics.checkNotNullParameter(lowerType, "lowerType");
        Intrinsics.checkNotNullParameter(upperType, "upperType");
        Intrinsics.checkNotNullParameter(position, "position");
        this.lowerType = lowerType;
        this.upperType = upperType;
        this.position = position;
    }

    public String toString() {
        return getLowerType() + " <: " + getUpperType();
    }
}
