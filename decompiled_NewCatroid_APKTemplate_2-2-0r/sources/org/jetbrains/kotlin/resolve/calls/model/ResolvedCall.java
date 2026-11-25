package org.jetbrains.kotlin.resolve.calls.model;

import java.util.List;
import java.util.Map;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.psi.Call;
import org.jetbrains.kotlin.psi.ValueArgument;
import org.jetbrains.kotlin.resolve.calls.results.ResolutionStatus;
import org.jetbrains.kotlin.resolve.calls.tasks.ExplicitReceiverKind;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: loaded from: classes6.dex */
public interface ResolvedCall<D extends CallableDescriptor> {
    ArgumentMapping getArgumentMapping(ValueArgument valueArgument);

    Call getCall();

    D getCandidateDescriptor();

    List<ReceiverValue> getContextReceivers();

    DataFlowInfoForArguments getDataFlowInfoForArguments();

    /* JADX INFO: renamed from: getDispatchReceiver */
    ReceiverValue mo7304getDispatchReceiver();

    ExplicitReceiverKind getExplicitReceiverKind();

    /* JADX INFO: renamed from: getExtensionReceiver */
    ReceiverValue mo7305getExtensionReceiver();

    D getResultingDescriptor();

    KotlinType getSmartCastDispatchReceiverType();

    ResolutionStatus getStatus();

    Map<TypeParameterDescriptor, KotlinType> getTypeArguments();

    Map<ValueParameterDescriptor, ResolvedValueArgument> getValueArguments();

    List<ResolvedValueArgument> getValueArgumentsByIndex();
}
