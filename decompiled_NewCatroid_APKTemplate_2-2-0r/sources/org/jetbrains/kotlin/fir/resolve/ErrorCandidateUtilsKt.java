package org.jetbrains.kotlin.fir.resolve;

import com.google.firebase.analytics.FirebaseAnalytics;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic;
import org.jetbrains.kotlin.fir.resolve.calls.CallInfo;
import org.jetbrains.kotlin.fir.resolve.calls.Candidate;
import org.jetbrains.kotlin.fir.resolve.calls.CandidateFactory;
import org.jetbrains.kotlin.fir.resolve.calls.CandidateKt;
import org.jetbrains.kotlin.fir.resolve.calls.FirErrorReferenceWithCandidate;
import org.jetbrains.kotlin.fir.resolve.calls.ResolutionContext;
import org.jetbrains.kotlin.fir.resolve.calls.ResolutionStageRunner;

/* JADX INFO: compiled from: errorCandidateUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00006\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\u001a0\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b\u001a0\u0010\f\u001a\u00020\u00012\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0004\u001a\u00020\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b\u001a\"\u0010\u000f\u001a\u00020\u000e*\u00020\u000b2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u0004\u001a\u00020\u0005\u001a\u001a\u0010\u0010\u001a\u00020\u0011*\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\b\u001a\u00020\t¨\u0006\u0012"}, d2 = {"createErrorReferenceWithErrorCandidate", "Lorg/jetbrains/kotlin/fir/resolve/calls/FirErrorReferenceWithCandidate;", "callInfo", "Lorg/jetbrains/kotlin/fir/resolve/calls/CallInfo;", "diagnostic", "Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "resolutionContext", "Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionContext;", "resolutionStageRunner", "Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionStageRunner;", "createErrorReferenceWithExistingCandidate", "candidate", "Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;", "createErrorCandidate", "fullyProcessCandidate", "", "resolve"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ErrorCandidateUtilsKt {
    public static final FirErrorReferenceWithCandidate createErrorReferenceWithErrorCandidate(CallInfo callInfo, ConeDiagnostic diagnostic, KtSourceElement ktSourceElement, ResolutionContext resolutionContext, ResolutionStageRunner resolutionStageRunner) {
        Intrinsics.checkNotNullParameter(callInfo, "callInfo");
        Intrinsics.checkNotNullParameter(diagnostic, "diagnostic");
        Intrinsics.checkNotNullParameter(resolutionContext, "resolutionContext");
        Intrinsics.checkNotNullParameter(resolutionStageRunner, "resolutionStageRunner");
        return new FirErrorReferenceWithCandidate(ktSourceElement, callInfo.getName(), createErrorCandidate(resolutionStageRunner, callInfo, resolutionContext, diagnostic), diagnostic);
    }

    public static final FirErrorReferenceWithCandidate createErrorReferenceWithExistingCandidate(Candidate candidate, ConeDiagnostic diagnostic, KtSourceElement ktSourceElement, ResolutionContext resolutionContext, ResolutionStageRunner resolutionStageRunner) {
        Intrinsics.checkNotNullParameter(candidate, "candidate");
        Intrinsics.checkNotNullParameter(diagnostic, "diagnostic");
        Intrinsics.checkNotNullParameter(resolutionContext, "resolutionContext");
        Intrinsics.checkNotNullParameter(resolutionStageRunner, "resolutionStageRunner");
        fullyProcessCandidate(resolutionStageRunner, candidate, resolutionContext);
        return new FirErrorReferenceWithCandidate(ktSourceElement, candidate.getCallInfo().getName(), candidate, diagnostic);
    }

    public static final Candidate createErrorCandidate(ResolutionStageRunner resolutionStageRunner, CallInfo callInfo, ResolutionContext resolutionContext, ConeDiagnostic diagnostic) {
        Intrinsics.checkNotNullParameter(resolutionStageRunner, "<this>");
        Intrinsics.checkNotNullParameter(callInfo, "callInfo");
        Intrinsics.checkNotNullParameter(resolutionContext, "resolutionContext");
        Intrinsics.checkNotNullParameter(diagnostic, "diagnostic");
        Candidate candidateCreateErrorCandidate = new CandidateFactory(resolutionContext, callInfo).createErrorCandidate(callInfo, diagnostic);
        resolutionStageRunner.processCandidate(candidateCreateErrorCandidate, resolutionContext, false);
        return candidateCreateErrorCandidate;
    }

    public static final void fullyProcessCandidate(ResolutionStageRunner resolutionStageRunner, Candidate candidate, ResolutionContext resolutionContext) {
        Intrinsics.checkNotNullParameter(resolutionStageRunner, "<this>");
        Intrinsics.checkNotNullParameter(candidate, "candidate");
        Intrinsics.checkNotNullParameter(resolutionContext, "resolutionContext");
        if (CandidateKt.getFullyAnalyzed(candidate)) {
            return;
        }
        resolutionStageRunner.processCandidate(candidate, resolutionContext, false);
    }
}
