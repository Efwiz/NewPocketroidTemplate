package org.jetbrains.kotlin.resolve.calls.components;

import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
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

/* JADX INFO: compiled from: InferenceSession.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000v\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u0000 *2\u00020\u0001:\u0001*J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH&J\u0010\u0010\t\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\nH&J\u0010\u0010\u000b\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\fH&J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H&J\u0012\u0010\u0011\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u0013\u001a\u00020\u0014H&J\b\u0010\u0015\u001a\u00020\u0016H&J6\u0010\u0017\u001a\u0010\u0012\u0004\u0012\u00020\u0019\u0012\u0004\u0012\u00020\u001a\u0018\u00010\u00182\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u00122\u0006\u0010 \u001a\u00020!H&J\u0010\u0010\"\u001a\u00020\u00062\u0006\u0010\u001b\u001a\u00020\u001cH&J\b\u0010#\u001a\u00020\u000eH&J\u0010\u0010$\u001a\u00020\u000e2\u0006\u0010%\u001a\u00020&H&J\u0010\u0010'\u001a\u00020\u000e2\u0006\u0010\u0013\u001a\u00020\u0014H&J\u0010\u0010(\u001a\u00020\u000e2\u0006\u0010\u0007\u001a\u00020)H&R\u0014\u0010\u0002\u001a\u0004\u0018\u00010\u0000X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006+À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/components/InferenceSession;", "", "parentSession", "getParentSession", "()Lorg/jetbrains/kotlin/resolve/calls/components/InferenceSession;", "addCompletedCallInfo", "", "callInfo", "Lorg/jetbrains/kotlin/resolve/calls/components/CompletedCallInfo;", "addErrorCallInfo", "Lorg/jetbrains/kotlin/resolve/calls/components/ErrorCallInfo;", "addPartialCallInfo", "Lorg/jetbrains/kotlin/resolve/calls/components/PartialCallInfo;", "callCompleted", "", "resolvedAtom", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedAtom;", "computeCompletionMode", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/ConstraintSystemCompletionMode;", "candidate", "Lorg/jetbrains/kotlin/resolve/calls/components/candidate/ResolutionCandidate;", "currentConstraintSystem", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintStorage;", "inferPostponedVariables", "", "Lorg/jetbrains/kotlin/types/TypeConstructor;", "Lorg/jetbrains/kotlin/types/UnwrappedType;", "lambda", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedLambdaAtom;", "constraintSystemBuilder", "Lorg/jetbrains/kotlin/resolve/calls/inference/ConstraintSystemBuilder;", "completionMode", "diagnosticsHolder", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinDiagnosticsHolder;", "initializeLambda", "resolveReceiverIndependently", "shouldCompleteResolvedSubAtomsOf", "resolvedCallAtom", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCallAtom;", "shouldRunCompletion", "writeOnlyStubs", "Lorg/jetbrains/kotlin/resolve/calls/model/SingleCallResolutionResult;", "Companion", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface InferenceSession {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = Companion.$$INSTANCE;

    void addCompletedCallInfo(CompletedCallInfo callInfo);

    void addErrorCallInfo(ErrorCallInfo callInfo);

    void addPartialCallInfo(PartialCallInfo callInfo);

    boolean callCompleted(ResolvedAtom resolvedAtom);

    ConstraintSystemCompletionMode computeCompletionMode(ResolutionCandidate candidate);

    ConstraintStorage currentConstraintSystem();

    InferenceSession getParentSession();

    Map<TypeConstructor, UnwrappedType> inferPostponedVariables(ResolvedLambdaAtom lambda, ConstraintSystemBuilder constraintSystemBuilder, ConstraintSystemCompletionMode completionMode, KotlinDiagnosticsHolder diagnosticsHolder);

    void initializeLambda(ResolvedLambdaAtom lambda);

    boolean resolveReceiverIndependently();

    boolean shouldCompleteResolvedSubAtomsOf(ResolvedCallAtom resolvedCallAtom);

    boolean shouldRunCompletion(ResolutionCandidate candidate);

    boolean writeOnlyStubs(SingleCallResolutionResult callInfo);

    /* JADX INFO: compiled from: InferenceSession.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/components/InferenceSession$Companion;", "", "()V", "default", "Lorg/jetbrains/kotlin/resolve/calls/components/InferenceSession;", "getDefault", "()Lorg/jetbrains/kotlin/resolve/calls/components/InferenceSession;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();
        private static final InferenceSession default = new InferenceSession() { // from class: org.jetbrains.kotlin.resolve.calls.components.InferenceSession$Companion$default$1
            private final InferenceSession parentSession;

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
            public boolean resolveReceiverIndependently() {
                return false;
            }

            @Override // org.jetbrains.kotlin.resolve.calls.components.InferenceSession
            public boolean shouldCompleteResolvedSubAtomsOf(ResolvedCallAtom resolvedCallAtom) {
                Intrinsics.checkNotNullParameter(resolvedCallAtom, "resolvedCallAtom");
                return true;
            }

            @Override // org.jetbrains.kotlin.resolve.calls.components.InferenceSession
            public boolean shouldRunCompletion(ResolutionCandidate candidate) {
                Intrinsics.checkNotNullParameter(candidate, "candidate");
                return true;
            }

            @Override // org.jetbrains.kotlin.resolve.calls.components.InferenceSession
            public boolean writeOnlyStubs(SingleCallResolutionResult callInfo) {
                Intrinsics.checkNotNullParameter(callInfo, "callInfo");
                return false;
            }

            @Override // org.jetbrains.kotlin.resolve.calls.components.InferenceSession
            public InferenceSession getParentSession() {
                return this.parentSession;
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
        };

        private Companion() {
        }

        public final InferenceSession getDefault() {
            return default;
        }
    }
}
