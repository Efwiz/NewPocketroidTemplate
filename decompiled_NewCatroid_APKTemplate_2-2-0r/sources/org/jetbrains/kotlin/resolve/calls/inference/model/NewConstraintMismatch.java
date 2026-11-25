package org.jetbrains.kotlin.resolve.calls.inference.model;

import kotlin.Metadata;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;

/* JADX INFO: compiled from: ConstraintPositionAndErrors.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bv\u0018\u00002\u00020\u0001R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0012\u0010\u0006\u001a\u00020\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0012\u0010\n\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\u0005\u0082\u0001\u0002\f\rø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u000eÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/model/NewConstraintMismatch;", "", "lowerType", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "getLowerType", "()Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "position", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/IncorporationConstraintPosition;", "getPosition", "()Lorg/jetbrains/kotlin/resolve/calls/inference/model/IncorporationConstraintPosition;", "upperType", "getUpperType", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/NewConstraintError;", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/NewConstraintWarning;", "resolution.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface NewConstraintMismatch {
    KotlinTypeMarker getLowerType();

    IncorporationConstraintPosition getPosition();

    KotlinTypeMarker getUpperType();
}
