package org.jetbrains.kotlin.resolve.calls.inference.constraintPosition;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: ConstraintPosition.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0005HÖ\u0001J\b\u0010\u0013\u001a\u00020\u0014H\u0016R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/constraintPosition/ConstraintPositionWithIndex;", "Lorg/jetbrains/kotlin/resolve/calls/inference/constraintPosition/ConstraintPosition;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/resolve/calls/inference/constraintPosition/ConstraintPositionKind;", "index", "", "(Lorg/jetbrains/kotlin/resolve/calls/inference/constraintPosition/ConstraintPositionKind;I)V", "getIndex", "()I", "getKind", "()Lorg/jetbrains/kotlin/resolve/calls/inference/constraintPosition/ConstraintPositionKind;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "toString", "", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
final /* data */ class ConstraintPositionWithIndex implements ConstraintPosition {
    private final int index;
    private final ConstraintPositionKind kind;

    public static /* synthetic */ ConstraintPositionWithIndex copy$default(ConstraintPositionWithIndex constraintPositionWithIndex, ConstraintPositionKind constraintPositionKind, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            constraintPositionKind = constraintPositionWithIndex.kind;
        }
        if ((i2 & 2) != 0) {
            i = constraintPositionWithIndex.index;
        }
        return constraintPositionWithIndex.copy(constraintPositionKind, i);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final ConstraintPositionKind getKind() {
        return this.kind;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final int getIndex() {
        return this.index;
    }

    public final ConstraintPositionWithIndex copy(ConstraintPositionKind kind, int index) {
        Intrinsics.checkNotNullParameter(kind, "kind");
        return new ConstraintPositionWithIndex(kind, index);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ConstraintPositionWithIndex)) {
            return false;
        }
        ConstraintPositionWithIndex constraintPositionWithIndex = (ConstraintPositionWithIndex) other;
        return this.kind == constraintPositionWithIndex.kind && this.index == constraintPositionWithIndex.index;
    }

    public int hashCode() {
        return (this.kind.hashCode() * 31) + Integer.hashCode(this.index);
    }

    public ConstraintPositionWithIndex(ConstraintPositionKind kind, int i) {
        Intrinsics.checkNotNullParameter(kind, "kind");
        this.kind = kind;
        this.index = i;
    }

    public final int getIndex() {
        return this.index;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.constraintPosition.ConstraintPosition
    public ConstraintPositionKind getKind() {
        return this.kind;
    }

    public String toString() {
        return new StringBuilder().append(getKind()).append('(').append(this.index).append(')').toString();
    }
}
