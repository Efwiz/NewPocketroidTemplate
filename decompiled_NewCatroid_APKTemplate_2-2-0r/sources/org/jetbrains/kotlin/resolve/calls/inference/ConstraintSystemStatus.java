package org.jetbrains.kotlin.resolve.calls.inference;

import java.util.List;
import kotlin.Metadata;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.resolve.calls.inference.constraintPosition.ConstraintPositionKind;

/* JADX INFO: compiled from: ConstraintSystemStatus.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\bf\u0018\u00002\u00020\u0001J\b\u0010\u0007\u001a\u00020\bH&J\b\u0010\t\u001a\u00020\bH&J\b\u0010\n\u001a\u00020\bH&J\b\u0010\u000b\u001a\u00020\bH&J\u0010\u0010\f\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\u000eH&J\b\u0010\u000f\u001a\u00020\bH&J\b\u0010\u0010\u001a\u00020\bH&J\b\u0010\u0011\u001a\u00020\bH&J\b\u0010\u0012\u001a\u00020\bH&J\b\u0010\u0013\u001a\u00020\bH&J\b\u0010\u0014\u001a\u00020\bH&R\u0018\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0015À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/ConstraintSystemStatus;", "", "constraintErrors", "", "Lorg/jetbrains/kotlin/resolve/calls/inference/ConstraintError;", "getConstraintErrors", "()Ljava/util/List;", "hasCannotCaptureTypesError", "", "hasConflictingConstraints", "hasContradiction", "hasErrorInConstrainingTypes", "hasOnlyErrorsDerivedFrom", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/resolve/calls/inference/constraintPosition/ConstraintPositionKind;", "hasParameterConstraintError", "hasTypeInferenceIncorporationError", "hasTypeParameterWithUnsatisfiedOnlyInputTypesError", "hasUnknownParameters", "hasViolatedUpperBound", "isSuccessful", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface ConstraintSystemStatus {
    List<ConstraintError> getConstraintErrors();

    boolean hasCannotCaptureTypesError();

    boolean hasConflictingConstraints();

    boolean hasContradiction();

    boolean hasErrorInConstrainingTypes();

    boolean hasOnlyErrorsDerivedFrom(ConstraintPositionKind kind);

    boolean hasParameterConstraintError();

    boolean hasTypeInferenceIncorporationError();

    boolean hasTypeParameterWithUnsatisfiedOnlyInputTypesError();

    boolean hasUnknownParameters();

    boolean hasViolatedUpperBound();

    boolean isSuccessful();
}
