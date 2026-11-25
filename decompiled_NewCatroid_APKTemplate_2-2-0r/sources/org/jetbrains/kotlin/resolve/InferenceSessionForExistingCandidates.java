package org.jetbrains.kotlin.resolve;

import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.resolve.calls.components.CompletedCallInfo;
import org.jetbrains.kotlin.resolve.calls.components.ErrorCallInfo;
import org.jetbrains.kotlin.resolve.calls.components.InferenceSession;
import org.jetbrains.kotlin.resolve.calls.components.PartialCallInfo;
import org.jetbrains.kotlin.resolve.calls.components.candidate.ResolutionCandidate;
import org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemBuilder;
import org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintSystemCompletionMode;
import org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintStorage;
import org.jetbrains.kotlin.resolve.calls.model.KotlinDiagnosticsHolder;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedAtom;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCallAtom;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedLambdaAtom;
import org.jetbrains.kotlin.resolve.calls.model.SingleCallResolutionResult;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.types.error.ErrorUtils;

/* JADX INFO: compiled from: DelegateInferenceSession.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000v\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0001¢\u0006\u0002\u0010\u0005J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0010\u0010\f\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\rH\u0016J\u0010\u0010\u000e\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000fH\u0016J\u0010\u0010\u0010\u001a\u00020\u00032\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\u0012\u0010\u0013\u001a\u0004\u0018\u00010\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0016J\b\u0010\u0017\u001a\u00020\u0018H\u0016J4\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u00020\u001b\u0012\u0004\u0012\u00020\u001c0\u001a2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\u00142\u0006\u0010\"\u001a\u00020#H\u0016J\u0010\u0010$\u001a\u00020\t2\u0006\u0010\u001d\u001a\u00020\u001eH\u0016J\b\u0010\u0002\u001a\u00020\u0003H\u0016J\u0010\u0010%\u001a\u00020\u00032\u0006\u0010&\u001a\u00020'H\u0016J\u0010\u0010(\u001a\u00020\u00032\u0006\u0010\u0015\u001a\u00020\u0016H\u0016J\u0010\u0010)\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020*H\u0016R\u0016\u0010\u0004\u001a\u0004\u0018\u00010\u0001X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006+"}, d2 = {"Lorg/jetbrains/kotlin/resolve/InferenceSessionForExistingCandidates;", "Lorg/jetbrains/kotlin/resolve/calls/components/InferenceSession;", "resolveReceiverIndependently", "", "parentSession", "(ZLorg/jetbrains/kotlin/resolve/calls/components/InferenceSession;)V", "getParentSession", "()Lorg/jetbrains/kotlin/resolve/calls/components/InferenceSession;", "addCompletedCallInfo", "", "callInfo", "Lorg/jetbrains/kotlin/resolve/calls/components/CompletedCallInfo;", "addErrorCallInfo", "Lorg/jetbrains/kotlin/resolve/calls/components/ErrorCallInfo;", "addPartialCallInfo", "Lorg/jetbrains/kotlin/resolve/calls/components/PartialCallInfo;", "callCompleted", "resolvedAtom", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedAtom;", "computeCompletionMode", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/ConstraintSystemCompletionMode;", "candidate", "Lorg/jetbrains/kotlin/resolve/calls/components/candidate/ResolutionCandidate;", "currentConstraintSystem", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintStorage;", "inferPostponedVariables", "", "Lorg/jetbrains/kotlin/types/TypeConstructor;", "Lorg/jetbrains/kotlin/types/UnwrappedType;", "lambda", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedLambdaAtom;", "constraintSystemBuilder", "Lorg/jetbrains/kotlin/resolve/calls/inference/ConstraintSystemBuilder;", "completionMode", "diagnosticsHolder", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinDiagnosticsHolder;", "initializeLambda", "shouldCompleteResolvedSubAtomsOf", "resolvedCallAtom", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCallAtom;", "shouldRunCompletion", "writeOnlyStubs", "Lorg/jetbrains/kotlin/resolve/calls/model/SingleCallResolutionResult;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class InferenceSessionForExistingCandidates implements InferenceSession {
    private final InferenceSession parentSession;
    private final boolean resolveReceiverIndependently;

    @Override // org.jetbrains.kotlin.resolve.calls.components.InferenceSession
    public void addCompletedCallInfo(CompletedCallInfo callInfo) {
        Intrinsics.checkNotNullParameter(callInfo, "callInfo");
    }

    @Override // org.jetbrains.kotlin.resolve.calls.components.InferenceSession
    public void addErrorCallInfo(ErrorCallInfo callInfo) {
        Intrinsics.checkNotNullParameter(callInfo, "callInfo");
    }

    @Override // org.jetbrains.kotlin.resolve.calls.components.InferenceSession
    public void addPartialCallInfo(PartialCallInfo callInfo) {
        Intrinsics.checkNotNullParameter(callInfo, "callInfo");
    }

    @Override // org.jetbrains.kotlin.resolve.calls.components.InferenceSession
    public boolean callCompleted(ResolvedAtom resolvedAtom) {
        Intrinsics.checkNotNullParameter(resolvedAtom, "resolvedAtom");
        return false;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.components.InferenceSession
    public ConstraintSystemCompletionMode computeCompletionMode(ResolutionCandidate candidate) {
        Intrinsics.checkNotNullParameter(candidate, "candidate");
        return null;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.components.InferenceSession
    public void initializeLambda(ResolvedLambdaAtom lambda) {
        Intrinsics.checkNotNullParameter(lambda, "lambda");
    }

    @Override // org.jetbrains.kotlin.resolve.calls.components.InferenceSession
    public boolean writeOnlyStubs(SingleCallResolutionResult callInfo) {
        Intrinsics.checkNotNullParameter(callInfo, "callInfo");
        return false;
    }

    public InferenceSessionForExistingCandidates(boolean z, InferenceSession inferenceSession) {
        this.resolveReceiverIndependently = z;
        this.parentSession = inferenceSession;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.components.InferenceSession
    public InferenceSession getParentSession() {
        return this.parentSession;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.components.InferenceSession
    public boolean shouldRunCompletion(ResolutionCandidate candidate) {
        Intrinsics.checkNotNullParameter(candidate, "candidate");
        return !ErrorUtils.isError(candidate.getResolvedCall().get_candidateDescriptor());
    }

    @Override // org.jetbrains.kotlin.resolve.calls.components.InferenceSession
    public ConstraintStorage currentConstraintSystem() {
        return ConstraintStorage.Empty.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.components.InferenceSession
    public Map<TypeConstructor, UnwrappedType> inferPostponedVariables(ResolvedLambdaAtom lambda, ConstraintSystemBuilder constraintSystemBuilder, ConstraintSystemCompletionMode completionMode, KotlinDiagnosticsHolder diagnosticsHolder) {
        Intrinsics.checkNotNullParameter(lambda, "lambda");
        Intrinsics.checkNotNullParameter(constraintSystemBuilder, "constraintSystemBuilder");
        Intrinsics.checkNotNullParameter(completionMode, "completionMode");
        Intrinsics.checkNotNullParameter(diagnosticsHolder, "diagnosticsHolder");
        return MapsKt.emptyMap();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.components.InferenceSession
    public boolean shouldCompleteResolvedSubAtomsOf(ResolvedCallAtom resolvedCallAtom) {
        Intrinsics.checkNotNullParameter(resolvedCallAtom, "resolvedCallAtom");
        return !ErrorUtils.isError(resolvedCallAtom.get_candidateDescriptor());
    }

    @Override // org.jetbrains.kotlin.resolve.calls.components.InferenceSession
    /* JADX INFO: renamed from: resolveReceiverIndependently, reason: from getter */
    public boolean getResolveReceiverIndependently() {
        return this.resolveReceiverIndependently;
    }
}
