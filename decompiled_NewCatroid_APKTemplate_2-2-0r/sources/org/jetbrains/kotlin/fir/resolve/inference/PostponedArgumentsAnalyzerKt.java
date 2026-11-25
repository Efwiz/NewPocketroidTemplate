package org.jetbrains.kotlin.fir.resolve.inference;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.resolve.calls.ResolutionContext;
import org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutor;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemBuilder;
import org.jetbrains.kotlin.types.model.TypeSubstitutorMarker;

/* JADX INFO: compiled from: PostponedArgumentsAnalyzer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000$\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a2\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b2\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n¨\u0006\u000b"}, d2 = {"transformToResolvedLambda", "Lorg/jetbrains/kotlin/fir/resolve/inference/ResolvedLambdaAtom;", "Lorg/jetbrains/kotlin/fir/resolve/inference/LambdaWithTypeVariableAsExpectedTypeAtom;", "csBuilder", "Lorg/jetbrains/kotlin/resolve/calls/inference/ConstraintSystemBuilder;", "context", "Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionContext;", "expectedType", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "returnTypeVariable", "Lorg/jetbrains/kotlin/fir/resolve/inference/ConeTypeVariableForLambdaReturnType;", "resolve"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class PostponedArgumentsAnalyzerKt {
    public static /* synthetic */ ResolvedLambdaAtom transformToResolvedLambda$default(LambdaWithTypeVariableAsExpectedTypeAtom lambdaWithTypeVariableAsExpectedTypeAtom, ConstraintSystemBuilder constraintSystemBuilder, ResolutionContext resolutionContext, ConeKotlinType coneKotlinType, ConeTypeVariableForLambdaReturnType coneTypeVariableForLambdaReturnType, int i, Object obj) {
        if ((i & 4) != 0) {
            coneKotlinType = null;
        }
        if ((i & 8) != 0) {
            coneTypeVariableForLambdaReturnType = null;
        }
        return transformToResolvedLambda(lambdaWithTypeVariableAsExpectedTypeAtom, constraintSystemBuilder, resolutionContext, coneKotlinType, coneTypeVariableForLambdaReturnType);
    }

    public static final ResolvedLambdaAtom transformToResolvedLambda(LambdaWithTypeVariableAsExpectedTypeAtom lambdaWithTypeVariableAsExpectedTypeAtom, ConstraintSystemBuilder csBuilder, ResolutionContext context, ConeKotlinType coneKotlinType, ConeTypeVariableForLambdaReturnType coneTypeVariableForLambdaReturnType) {
        Intrinsics.checkNotNullParameter(lambdaWithTypeVariableAsExpectedTypeAtom, "<this>");
        Intrinsics.checkNotNullParameter(csBuilder, "csBuilder");
        Intrinsics.checkNotNullParameter(context, "context");
        TypeSubstitutorMarker typeSubstitutorMarkerBuildCurrentSubstitutor = csBuilder.buildCurrentSubstitutor();
        Intrinsics.checkNotNull(typeSubstitutorMarkerBuildCurrentSubstitutor, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutor");
        ConeSubstitutor coneSubstitutor = (ConeSubstitutor) typeSubstitutorMarkerBuildCurrentSubstitutor;
        if (coneKotlinType == null) {
            coneKotlinType = lambdaWithTypeVariableAsExpectedTypeAtom.getExpectedType();
        }
        PostponedResolvedAtom postponedResolvedAtomPreprocessLambdaArgument = PostponedArgumentsKt.preprocessLambdaArgument(lambdaWithTypeVariableAsExpectedTypeAtom.getCandidateOfOuterCall(), csBuilder, lambdaWithTypeVariableAsExpectedTypeAtom.getReference(), coneSubstitutor.substituteOrSelf(coneKotlinType), context, null, true, coneTypeVariableForLambdaReturnType);
        Intrinsics.checkNotNull(postponedResolvedAtomPreprocessLambdaArgument, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.resolve.inference.ResolvedLambdaAtom");
        ResolvedLambdaAtom resolvedLambdaAtom = (ResolvedLambdaAtom) postponedResolvedAtomPreprocessLambdaArgument;
        lambdaWithTypeVariableAsExpectedTypeAtom.setAnalyzed(true);
        return resolvedLambdaAtom;
    }
}
