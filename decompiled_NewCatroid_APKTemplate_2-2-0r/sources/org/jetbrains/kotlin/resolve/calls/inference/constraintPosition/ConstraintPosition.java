package org.jetbrains.kotlin.resolve.calls.inference.constraintPosition;

import kotlin.Metadata;
import kotlin.collections.SetsKt;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: ConstraintPosition.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\b\u0010\u0006\u001a\u00020\u0007H\u0016J\b\u0010\b\u001a\u00020\u0007H\u0016R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\tÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/constraintPosition/ConstraintPosition;", "", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/resolve/calls/inference/constraintPosition/ConstraintPositionKind;", "getKind", "()Lorg/jetbrains/kotlin/resolve/calls/inference/constraintPosition/ConstraintPositionKind;", "isParameter", "", "isStrong", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface ConstraintPosition {
    ConstraintPositionKind getKind();

    default boolean isStrong() {
        return getKind() != ConstraintPositionKind.TYPE_BOUND_POSITION;
    }

    default boolean isParameter() {
        return SetsKt.setOf((Object[]) new ConstraintPositionKind[]{ConstraintPositionKind.VALUE_PARAMETER_POSITION, ConstraintPositionKind.RECEIVER_POSITION}).contains(getKind());
    }
}
