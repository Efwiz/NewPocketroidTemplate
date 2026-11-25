package org.jetbrains.kotlin.resolve.calls.model;

import kotlin.Metadata;
import org.jetbrains.kotlin.resolve.calls.components.InferenceSession;
import org.jetbrains.kotlin.types.UnwrappedType;

/* JADX INFO: compiled from: KotlinCallArguments.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001R(\u0010\u0004\u001a\u0004\u0018\u00010\u00032\b\u0010\u0002\u001a\u0004\u0018\u00010\u00038V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR$\u0010\n\u001a\u00020\t2\u0006\u0010\u0002\u001a\u00020\t8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u0014\u0010\u000f\u001a\u00020\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\fR\u001c\u0010\u0010\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0012\u0018\u00010\u0011X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0015À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/model/LambdaKotlinCallArgument;", "Lorg/jetbrains/kotlin/resolve/calls/model/PostponableKotlinCallArgument;", "<anonymous parameter 0>", "Lorg/jetbrains/kotlin/resolve/calls/components/InferenceSession;", "builderInferenceSession", "getBuilderInferenceSession", "()Lorg/jetbrains/kotlin/resolve/calls/components/InferenceSession;", "setBuilderInferenceSession", "(Lorg/jetbrains/kotlin/resolve/calls/components/InferenceSession;)V", "", "hasBuilderInferenceAnnotation", "getHasBuilderInferenceAnnotation", "()Z", "setHasBuilderInferenceAnnotation", "(Z)V", "isSpread", "parametersTypes", "", "Lorg/jetbrains/kotlin/types/UnwrappedType;", "getParametersTypes", "()[Lorg/jetbrains/kotlin/types/UnwrappedType;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface LambdaKotlinCallArgument extends PostponableKotlinCallArgument {
    default InferenceSession getBuilderInferenceSession() {
        return null;
    }

    default boolean getHasBuilderInferenceAnnotation() {
        return false;
    }

    UnwrappedType[] getParametersTypes();

    @Override // org.jetbrains.kotlin.resolve.calls.model.KotlinCallArgument
    default boolean isSpread() {
        return false;
    }

    default void setBuilderInferenceSession(InferenceSession inferenceSession) {
    }

    default void setHasBuilderInferenceAnnotation(boolean z) {
    }
}
