package org.jetbrains.kotlin.resolve.calls.model;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.resolve.calls.components.candidate.ResolutionCandidate;
import org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintSystemError;
import org.jetbrains.kotlin.resolve.calls.inference.model.LowerPriorityToPreserveCompatibility;
import org.jetbrains.kotlin.utils.SmartList;

/* JADX INFO: compiled from: ResolvedCallAtoms.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00000\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0014\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004\u001a\u0012\u0010\u0005\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u0007\u001a\u0014\u0010\b\u001a\u00020\u0001*\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u000b\u001a\"\u0010\b\u001a\u00020\u0001*\u00020\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00040\u000e2\b\b\u0002\u0010\n\u001a\u00020\u000b¨\u0006\u000f"}, d2 = {"addDiagnosticIfNotNull", "", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinDiagnosticsHolder;", "diagnostic", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallDiagnostic;", "addError", "error", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintSystemError;", "markCandidateForCompatibilityResolve", "Lorg/jetbrains/kotlin/resolve/calls/components/candidate/ResolutionCandidate;", "needToReportWarning", "", "Lorg/jetbrains/kotlin/resolve/calls/model/CallableReferencesCandidateFactory;", "diagnostics", "Lorg/jetbrains/kotlin/utils/SmartList;", "resolution"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ResolvedCallAtomsKt {
    public static final void addDiagnosticIfNotNull(KotlinDiagnosticsHolder kotlinDiagnosticsHolder, KotlinCallDiagnostic kotlinCallDiagnostic) {
        Intrinsics.checkNotNullParameter(kotlinDiagnosticsHolder, "<this>");
        if (kotlinCallDiagnostic != null) {
            kotlinDiagnosticsHolder.addDiagnostic(kotlinCallDiagnostic);
        }
    }

    public static final void addError(KotlinDiagnosticsHolder kotlinDiagnosticsHolder, ConstraintSystemError error) {
        Intrinsics.checkNotNullParameter(kotlinDiagnosticsHolder, "<this>");
        Intrinsics.checkNotNullParameter(error, "error");
        kotlinDiagnosticsHolder.addDiagnostic(KotlinCallDiagnosticsKt.asDiagnostic(error));
    }

    public static /* synthetic */ void markCandidateForCompatibilityResolve$default(ResolutionCandidate resolutionCandidate, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = true;
        }
        markCandidateForCompatibilityResolve(resolutionCandidate, z);
    }

    public static final void markCandidateForCompatibilityResolve(ResolutionCandidate resolutionCandidate, boolean z) {
        Intrinsics.checkNotNullParameter(resolutionCandidate, "<this>");
        if (resolutionCandidate.getCallComponents().getLanguageVersionSettings().supportsFeature(LanguageFeature.DisableCompatibilityModeForNewInference)) {
            return;
        }
        resolutionCandidate.addDiagnostic(KotlinCallDiagnosticsKt.asDiagnostic(new LowerPriorityToPreserveCompatibility(z)));
    }

    public static /* synthetic */ void markCandidateForCompatibilityResolve$default(CallableReferencesCandidateFactory callableReferencesCandidateFactory, SmartList smartList, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = true;
        }
        markCandidateForCompatibilityResolve(callableReferencesCandidateFactory, smartList, z);
    }

    public static final void markCandidateForCompatibilityResolve(CallableReferencesCandidateFactory callableReferencesCandidateFactory, SmartList<KotlinCallDiagnostic> diagnostics, boolean z) {
        Intrinsics.checkNotNullParameter(callableReferencesCandidateFactory, "<this>");
        Intrinsics.checkNotNullParameter(diagnostics, "diagnostics");
        if (callableReferencesCandidateFactory.getCallComponents().getLanguageVersionSettings().supportsFeature(LanguageFeature.DisableCompatibilityModeForNewInference)) {
            return;
        }
        diagnostics.add(KotlinCallDiagnosticsKt.asDiagnostic(new LowerPriorityToPreserveCompatibility(z)));
    }
}
