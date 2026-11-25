package org.jetbrains.kotlin.resolve.calls.components;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.resolve.calls.components.candidate.ResolutionCandidate;
import org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintStorage;
import org.jetbrains.kotlin.types.error.ErrorUtils;

/* JADX INFO: compiled from: KotlinCallCompleter.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\u001a\f\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u0000¨\u0006\u0003"}, d2 = {"isErrorCandidate", "", "Lorg/jetbrains/kotlin/resolve/calls/components/candidate/ResolutionCandidate;", "resolution"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class KotlinCallCompleterKt {
    public static final boolean isErrorCandidate(ResolutionCandidate resolutionCandidate) {
        Intrinsics.checkNotNullParameter(resolutionCandidate, "<this>");
        return ErrorUtils.isError(resolutionCandidate.getResolvedCall().get_candidateDescriptor()) || ConstraintStorage.Empty.INSTANCE.getHasContradiction();
    }
}
