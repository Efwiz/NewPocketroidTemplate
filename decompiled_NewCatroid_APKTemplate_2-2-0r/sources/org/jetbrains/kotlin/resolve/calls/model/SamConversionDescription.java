package org.jetbrains.kotlin.resolve.calls.model;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.types.UnwrappedType;

/* JADX INFO: compiled from: ResolutionAtoms.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\bR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\b¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/model/SamConversionDescription;", "", "convertedTypeByOriginParameter", "Lorg/jetbrains/kotlin/types/UnwrappedType;", "convertedTypeByCandidateParameter", "originalParameterType", "(Lorg/jetbrains/kotlin/types/UnwrappedType;Lorg/jetbrains/kotlin/types/UnwrappedType;Lorg/jetbrains/kotlin/types/UnwrappedType;)V", "getConvertedTypeByCandidateParameter", "()Lorg/jetbrains/kotlin/types/UnwrappedType;", "getConvertedTypeByOriginParameter", "getOriginalParameterType", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SamConversionDescription {
    private final UnwrappedType convertedTypeByCandidateParameter;
    private final UnwrappedType convertedTypeByOriginParameter;
    private final UnwrappedType originalParameterType;

    public SamConversionDescription(UnwrappedType convertedTypeByOriginParameter, UnwrappedType convertedTypeByCandidateParameter, UnwrappedType originalParameterType) {
        Intrinsics.checkNotNullParameter(convertedTypeByOriginParameter, "convertedTypeByOriginParameter");
        Intrinsics.checkNotNullParameter(convertedTypeByCandidateParameter, "convertedTypeByCandidateParameter");
        Intrinsics.checkNotNullParameter(originalParameterType, "originalParameterType");
        this.convertedTypeByOriginParameter = convertedTypeByOriginParameter;
        this.convertedTypeByCandidateParameter = convertedTypeByCandidateParameter;
        this.originalParameterType = originalParameterType;
    }

    public final UnwrappedType getConvertedTypeByOriginParameter() {
        return this.convertedTypeByOriginParameter;
    }

    public final UnwrappedType getConvertedTypeByCandidateParameter() {
        return this.convertedTypeByCandidateParameter;
    }

    public final UnwrappedType getOriginalParameterType() {
        return this.originalParameterType;
    }
}
