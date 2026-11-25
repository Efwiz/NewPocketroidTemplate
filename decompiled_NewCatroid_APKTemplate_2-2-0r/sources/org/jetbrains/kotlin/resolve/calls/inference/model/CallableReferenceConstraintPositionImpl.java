package org.jetbrains.kotlin.resolve.calls.inference.model;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.resolve.calls.model.CallableReferenceKotlinCall;
import org.jetbrains.kotlin.resolve.calls.model.CallableReferenceResolutionAtom;

/* JADX INFO: compiled from: ConstraintPositionAndErrorsImpl.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/model/CallableReferenceConstraintPositionImpl;", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/CallableReferenceConstraintPosition;", "Lorg/jetbrains/kotlin/resolve/calls/model/CallableReferenceResolutionAtom;", "callableReferenceCall", "Lorg/jetbrains/kotlin/resolve/calls/model/CallableReferenceKotlinCall;", "(Lorg/jetbrains/kotlin/resolve/calls/model/CallableReferenceKotlinCall;)V", "getCallableReferenceCall", "()Lorg/jetbrains/kotlin/resolve/calls/model/CallableReferenceKotlinCall;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CallableReferenceConstraintPositionImpl extends CallableReferenceConstraintPosition<CallableReferenceResolutionAtom> {
    private final CallableReferenceKotlinCall callableReferenceCall;

    public final CallableReferenceKotlinCall getCallableReferenceCall() {
        return this.callableReferenceCall;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CallableReferenceConstraintPositionImpl(CallableReferenceKotlinCall callableReferenceCall) {
        super(callableReferenceCall);
        Intrinsics.checkNotNullParameter(callableReferenceCall, "callableReferenceCall");
        this.callableReferenceCall = callableReferenceCall;
    }
}
