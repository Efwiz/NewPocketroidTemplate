package org.jetbrains.kotlin.resolve.calls.inference.model;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.resolve.calls.model.LambdaKotlinCallArgument;

/* JADX INFO: compiled from: ConstraintPositionAndErrorsImpl.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u001f\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/model/BuilderInferenceSubstitutionConstraintPositionImpl;", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/BuilderInferenceSubstitutionConstraintPosition;", "Lorg/jetbrains/kotlin/resolve/calls/model/LambdaKotlinCallArgument;", "builderInferenceLambda", "initialConstraint", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/InitialConstraint;", "isFromNotSubstitutedDeclaredUpperBound", "", "(Lorg/jetbrains/kotlin/resolve/calls/model/LambdaKotlinCallArgument;Lorg/jetbrains/kotlin/resolve/calls/inference/model/InitialConstraint;Z)V", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BuilderInferenceSubstitutionConstraintPositionImpl extends BuilderInferenceSubstitutionConstraintPosition<LambdaKotlinCallArgument> {
    public /* synthetic */ BuilderInferenceSubstitutionConstraintPositionImpl(LambdaKotlinCallArgument lambdaKotlinCallArgument, InitialConstraint initialConstraint, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(lambdaKotlinCallArgument, initialConstraint, (i & 4) != 0 ? false : z);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BuilderInferenceSubstitutionConstraintPositionImpl(LambdaKotlinCallArgument builderInferenceLambda, InitialConstraint initialConstraint, boolean z) {
        super(builderInferenceLambda, initialConstraint, z);
        Intrinsics.checkNotNullParameter(builderInferenceLambda, "builderInferenceLambda");
        Intrinsics.checkNotNullParameter(initialConstraint, "initialConstraint");
    }
}
