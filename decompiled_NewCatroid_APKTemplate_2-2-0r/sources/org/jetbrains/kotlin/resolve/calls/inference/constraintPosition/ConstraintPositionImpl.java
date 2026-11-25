package org.jetbrains.kotlin.resolve.calls.inference.constraintPosition;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: ConstraintPosition.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fHÖ\u0003J\t\u0010\r\u001a\u00020\u000eHÖ\u0001J\b\u0010\u000f\u001a\u00020\u0010H\u0016R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/constraintPosition/ConstraintPositionImpl;", "Lorg/jetbrains/kotlin/resolve/calls/inference/constraintPosition/ConstraintPosition;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/resolve/calls/inference/constraintPosition/ConstraintPositionKind;", "(Lorg/jetbrains/kotlin/resolve/calls/inference/constraintPosition/ConstraintPositionKind;)V", "getKind", "()Lorg/jetbrains/kotlin/resolve/calls/inference/constraintPosition/ConstraintPositionKind;", "component1", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
final /* data */ class ConstraintPositionImpl implements ConstraintPosition {
    private final ConstraintPositionKind kind;

    public static /* synthetic */ ConstraintPositionImpl copy$default(ConstraintPositionImpl constraintPositionImpl, ConstraintPositionKind constraintPositionKind, int i, Object obj) {
        if ((i & 1) != 0) {
            constraintPositionKind = constraintPositionImpl.kind;
        }
        return constraintPositionImpl.copy(constraintPositionKind);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final ConstraintPositionKind getKind() {
        return this.kind;
    }

    public final ConstraintPositionImpl copy(ConstraintPositionKind kind) {
        Intrinsics.checkNotNullParameter(kind, "kind");
        return new ConstraintPositionImpl(kind);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        return (other instanceof ConstraintPositionImpl) && this.kind == ((ConstraintPositionImpl) other).kind;
    }

    public int hashCode() {
        return this.kind.hashCode();
    }

    public ConstraintPositionImpl(ConstraintPositionKind kind) {
        Intrinsics.checkNotNullParameter(kind, "kind");
        this.kind = kind;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.constraintPosition.ConstraintPosition
    public ConstraintPositionKind getKind() {
        return this.kind;
    }

    public String toString() {
        return String.valueOf(getKind());
    }
}
