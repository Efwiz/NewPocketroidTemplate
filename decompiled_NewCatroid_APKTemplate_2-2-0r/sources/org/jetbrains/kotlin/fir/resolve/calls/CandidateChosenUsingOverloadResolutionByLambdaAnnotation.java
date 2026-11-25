package org.jetbrains.kotlin.fir.resolve.calls;

import kotlin.Metadata;
import org.jetbrains.kotlin.resolve.calls.tower.CandidateApplicability;

/* JADX INFO: compiled from: ResolutionDiagnostic.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/CandidateChosenUsingOverloadResolutionByLambdaAnnotation;", "Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionDiagnostic;", "()V", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CandidateChosenUsingOverloadResolutionByLambdaAnnotation extends ResolutionDiagnostic {
    public static final CandidateChosenUsingOverloadResolutionByLambdaAnnotation INSTANCE = new CandidateChosenUsingOverloadResolutionByLambdaAnnotation();

    private CandidateChosenUsingOverloadResolutionByLambdaAnnotation() {
        super(CandidateApplicability.RESOLVED);
    }
}
