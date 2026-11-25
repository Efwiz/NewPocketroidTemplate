package org.jetbrains.kotlin.resolve.calls.inference;

import kotlin.Metadata;
import org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintStorage;
import org.jetbrains.kotlin.types.model.TypeSubstitutorMarker;

/* JADX INFO: compiled from: ConstraintSystemBuilder.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\b\u0010\u0004\u001a\u00020\u0005H&J\b\u0010\u0006\u001a\u00020\u0007H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\bÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/ConstraintSystemBuilder;", "Lorg/jetbrains/kotlin/resolve/calls/inference/ConstraintSystemOperation;", "buildCurrentSubstitutor", "Lorg/jetbrains/kotlin/types/model/TypeSubstitutorMarker;", "currentStorage", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintStorage;", "prepareTransaction", "Lorg/jetbrains/kotlin/resolve/calls/inference/ConstraintSystemTransaction;", "resolution.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface ConstraintSystemBuilder extends ConstraintSystemOperation {
    TypeSubstitutorMarker buildCurrentSubstitutor();

    ConstraintStorage currentStorage();

    ConstraintSystemTransaction prepareTransaction();
}
