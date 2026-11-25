package org.jetbrains.kotlin.resolve.calls.tower;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.psi.Call;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCall;
import org.jetbrains.kotlin.resolve.calls.model.QualifierReceiverKotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.ReceiverKotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.model.SimpleKotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;

/* JADX INFO: compiled from: PSIKotlinCalls.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000,\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a$\u0010\n\u001a\n\u0012\u0004\u0012\u0002H\f\u0018\u00010\u000b\"\b\b\u0000\u0010\f*\u00020\r*\u00020\u00022\u0006\u0010\u000e\u001a\u00020\u000f\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u0017\u0010\u0005\u001a\u0004\u0018\u00010\u0006*\u00020\u00078F¢\u0006\u0006\u001a\u0004\b\b\u0010\t¨\u0006\u0010"}, d2 = {"psiKotlinCall", "Lorg/jetbrains/kotlin/resolve/calls/tower/PSIKotlinCall;", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCall;", "getPsiKotlinCall", "(Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCall;)Lorg/jetbrains/kotlin/resolve/calls/tower/PSIKotlinCall;", "receiverValue", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValue;", "Lorg/jetbrains/kotlin/resolve/calls/model/ReceiverKotlinCallArgument;", "getReceiverValue", "(Lorg/jetbrains/kotlin/resolve/calls/model/ReceiverKotlinCallArgument;)Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValue;", "getResolvedPsiKotlinCall", "Lorg/jetbrains/kotlin/resolve/calls/tower/NewResolvedCallImpl;", "D", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class PSIKotlinCallsKt {
    public static final PSIKotlinCall getPsiKotlinCall(KotlinCall kotlinCall) {
        Intrinsics.checkNotNullParameter(kotlinCall, "<this>");
        boolean z = kotlinCall instanceof PSIKotlinCall;
        return (PSIKotlinCall) kotlinCall;
    }

    public static final <D extends CallableDescriptor> NewResolvedCallImpl<D> getResolvedPsiKotlinCall(KotlinCall kotlinCall, BindingTrace trace) {
        Intrinsics.checkNotNullParameter(kotlinCall, "<this>");
        Intrinsics.checkNotNullParameter(trace, "trace");
        Call psiCall = getPsiKotlinCall(kotlinCall).getPsiCall();
        BindingContext context = trace.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getBindingContext(...)");
        ResolvedCall<? extends CallableDescriptor> resolvedCall = CallUtilKt.getResolvedCall(psiCall, context);
        if (resolvedCall instanceof NewResolvedCallImpl) {
            return (NewResolvedCallImpl) resolvedCall;
        }
        return null;
    }

    public static final ReceiverValue getReceiverValue(ReceiverKotlinCallArgument receiverKotlinCallArgument) {
        Intrinsics.checkNotNullParameter(receiverKotlinCallArgument, "<this>");
        if (receiverKotlinCallArgument instanceof SimpleKotlinCallArgument) {
            return ((SimpleKotlinCallArgument) receiverKotlinCallArgument).getReceiver().getReceiverValue();
        }
        if (receiverKotlinCallArgument instanceof QualifierReceiverKotlinCallArgument) {
            return ((QualifierReceiverKotlinCallArgument) receiverKotlinCallArgument).getReceiver().getClassValueReceiver();
        }
        return null;
    }
}
