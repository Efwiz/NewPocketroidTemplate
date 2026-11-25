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
public abstract class DelegatingResolvedCall<D extends CallableDescriptor> implements ResolvedCall<D> {
    private final ResolvedCall<? extends D> resolvedCall;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 9:
            case 10:
            case 11:
                str = "@NotNull method %s.%s must not return null";
                break;
            case 8:
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 9:
            case 10:
            case 11:
                i2 = 2;
                break;
            case 8:
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 9:
            case 10:
            case 11:
                objArr[0] = "org/jetbrains/kotlin/resolve/calls/model/DelegatingResolvedCall";
                break;
            case 8:
                objArr[0] = "valueArgument";
                break;
            default:
                objArr[0] = "resolvedCall";
                break;
        }
        switch (i) {
            case 1:
                objArr[1] = "getStatus";
                break;
            case 2:
                objArr[1] = "getCall";
                break;
            case 3:
                objArr[1] = "getCandidateDescriptor";
                break;
            case 4:
                objArr[1] = "getResultingDescriptor";
                break;
            case 5:
                objArr[1] = "getContextReceivers";
                break;
            case 6:
                objArr[1] = "getExplicitReceiverKind";
                break;
            case 7:
                objArr[1] = "getValueArguments";
                break;
            case 8:
            default:
                objArr[1] = "org/jetbrains/kotlin/resolve/calls/model/DelegatingResolvedCall";
                break;
            case 9:
                objArr[1] = "getArgumentMapping";
                break;
            case 10:
                objArr[1] = "getTypeArguments";
                break;
            case 11:
                objArr[1] = "getDataFlowInfoForArguments";
                break;
        }
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 9:
            case 10:
            case 11:
                break;
            case 8:
                objArr[2] = "getArgumentMapping";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 9:
            case 10:
            case 11:
                throw new IllegalStateException(str2);
            case 8:
            default:
                throw new IllegalArgumentException(str2);
        }
    }

    public DelegatingResolvedCall(ResolvedCall<? extends D> resolvedCall) {
        if (resolvedCall == null) {
            $$$reportNull$$$0(0);
        }
        this.resolvedCall = resolvedCall;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
    public ResolutionStatus getStatus() {
        ResolutionStatus status = this.resolvedCall.getStatus();
        if (status == null) {
            $$$reportNull$$$0(1);
        }
        return status;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
    public Call getCall() {
        Call call = this.resolvedCall.getCall();
        if (call == null) {
            $$$reportNull$$$0(2);
        }
        return call;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
    public D getCandidateDescriptor() {
        D d = (D) this.resolvedCall.getCandidateDescriptor();
        if (d == null) {
            $$$reportNull$$$0(3);
        }
        return d;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
    public D getResultingDescriptor() {
        D d = (D) this.resolvedCall.getResultingDescriptor();
        if (d == null) {
            $$$reportNull$$$0(4);
        }
        return d;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
    /* JADX INFO: renamed from: getExtensionReceiver */
    public ReceiverValue mo7305getExtensionReceiver() {
        return this.resolvedCall.mo7305getExtensionReceiver();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
    /* JADX INFO: renamed from: getDispatchReceiver */
    public ReceiverValue mo7304getDispatchReceiver() {
        return this.resolvedCall.mo7304getDispatchReceiver();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
    public List<ReceiverValue> getContextReceivers() {
        List<ReceiverValue> contextReceivers = this.resolvedCall.getContextReceivers();
        if (contextReceivers == null) {
            $$$reportNull$$$0(5);
        }
        return contextReceivers;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
    public ExplicitReceiverKind getExplicitReceiverKind() {
        ExplicitReceiverKind explicitReceiverKind = this.resolvedCall.getExplicitReceiverKind();
        if (explicitReceiverKind == null) {
            $$$reportNull$$$0(6);
        }
        return explicitReceiverKind;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
    public Map<ValueParameterDescriptor, ResolvedValueArgument> getValueArguments() {
        Map<ValueParameterDescriptor, ResolvedValueArgument> valueArguments = this.resolvedCall.getValueArguments();
        if (valueArguments == null) {
            $$$reportNull$$$0(7);
        }
        return valueArguments;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
    public ArgumentMapping getArgumentMapping(ValueArgument valueArgument) {
        if (valueArgument == null) {
            $$$reportNull$$$0(8);
        }
        ArgumentMapping argumentMapping = this.resolvedCall.getArgumentMapping(valueArgument);
        if (argumentMapping == null) {
            $$$reportNull$$$0(9);
        }
        return argumentMapping;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
    public List<ResolvedValueArgument> getValueArgumentsByIndex() {
        return this.resolvedCall.getValueArgumentsByIndex();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
    public Map<TypeParameterDescriptor, KotlinType> getTypeArguments() {
        Map<TypeParameterDescriptor, KotlinType> typeArguments = this.resolvedCall.getTypeArguments();
        if (typeArguments == null) {
            $$$reportNull$$$0(10);
        }
        return typeArguments;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
    public DataFlowInfoForArguments getDataFlowInfoForArguments() {
        DataFlowInfoForArguments dataFlowInfoForArguments = this.resolvedCall.getDataFlowInfoForArguments();
        if (dataFlowInfoForArguments == null) {
            $$$reportNull$$$0(11);
        }
        return dataFlowInfoForArguments;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
    public KotlinType getSmartCastDispatchReceiverType() {
        return this.resolvedCall.getSmartCastDispatchReceiverType();
    }
}
