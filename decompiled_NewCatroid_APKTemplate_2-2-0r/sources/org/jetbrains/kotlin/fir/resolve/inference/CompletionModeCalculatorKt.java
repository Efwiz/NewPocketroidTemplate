package org.jetbrains.kotlin.fir.resolve.inference;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.resolve.ResolutionMode;
import org.jetbrains.kotlin.fir.resolve.calls.Candidate;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintSystemCompletionMode;

/* JADX INFO: compiled from: CompletionModeCalculator.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000$\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a$\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\b*\f\b\u0002\u0010\t\"\u00020\n2\u00020\n¨\u0006\u000b"}, d2 = {"computeCompletionMode", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/ConstraintSystemCompletionMode;", "Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;", "components", "Lorg/jetbrains/kotlin/fir/resolve/inference/InferenceComponents;", "resolutionMode", "Lorg/jetbrains/kotlin/fir/resolve/ResolutionMode;", "currentReturnType", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "CsCompleterContext", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/ConstraintSystemCompletionContext;", "resolve"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CompletionModeCalculatorKt {
    public static final ConstraintSystemCompletionMode computeCompletionMode(Candidate candidate, InferenceComponents components, ResolutionMode resolutionMode, ConeKotlinType coneKotlinType) {
        Intrinsics.checkNotNullParameter(candidate, "<this>");
        Intrinsics.checkNotNullParameter(components, "components");
        Intrinsics.checkNotNullParameter(resolutionMode, "resolutionMode");
        if (resolutionMode.getForceFullCompletion()) {
            return ConstraintSystemCompletionMode.FULL;
        }
        if (coneKotlinType == null) {
            return ConstraintSystemCompletionMode.PARTIAL;
        }
        return ConstraintSystemCompleterKt.getCsBuilder(candidate).isProperType(coneKotlinType) ? ConstraintSystemCompletionMode.FULL : new CalculatorForNestedCall(candidate, coneKotlinType, ConstraintSystemCompleterKt.getCsBuilder(candidate), components.getTrivialConstraintTypeInferenceOracle()).computeCompletionMode();
    }
}
