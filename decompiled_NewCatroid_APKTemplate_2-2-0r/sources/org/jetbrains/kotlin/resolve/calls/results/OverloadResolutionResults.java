package org.jetbrains.kotlin.resolve.calls.results;

import java.util.Collection;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;

/* JADX INFO: loaded from: classes6.dex */
public interface OverloadResolutionResults<D extends CallableDescriptor> {
    Collection<ResolvedCall<D>> getAllCandidates();

    Code getResultCode();

    /* JADX INFO: renamed from: getResultingCall */
    ResolvedCall<D> mo7436getResultingCall();

    Collection<? extends ResolvedCall<D>> getResultingCalls();

    /* JADX INFO: renamed from: getResultingDescriptor */
    D mo7437getResultingDescriptor();

    boolean isAmbiguity();

    boolean isIncomplete();

    boolean isNothing();

    boolean isSingleResult();

    boolean isSuccess();

    public enum Code {
        SUCCESS(true),
        NAME_NOT_FOUND(false),
        SINGLE_CANDIDATE_ARGUMENT_MISMATCH(false),
        AMBIGUITY(false),
        MANY_FAILED_CANDIDATES(false),
        CANDIDATES_WITH_WRONG_RECEIVER(false),
        INCOMPLETE_TYPE_INFERENCE(false);

        private final boolean success;

        Code(boolean z) {
            this.success = z;
        }

        boolean isSuccess() {
            return this.success;
        }
    }
}
