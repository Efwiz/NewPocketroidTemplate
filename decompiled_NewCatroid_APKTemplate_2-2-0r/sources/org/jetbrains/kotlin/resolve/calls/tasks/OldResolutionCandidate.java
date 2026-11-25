package org.jetbrains.kotlin.resolve.calls.tasks;

import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.psi.Call;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.types.TypeSubstitutor;

/* JADX INFO: loaded from: classes6.dex */
public class OldResolutionCandidate<D extends CallableDescriptor> {
    private final Call call;
    private final D candidateDescriptor;
    private ReceiverValue dispatchReceiver;
    private ExplicitReceiverKind explicitReceiverKind;
    private final TypeSubstitutor knownTypeParametersResultingSubstitutor;

    /* JADX WARN: Code duplicated, block: B:19:0x002a  */
    /* JADX WARN: Code duplicated, block: B:20:0x002f  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 11:
            case 12:
            case 13:
                str = "@NotNull method %s.%s must not return null";
                break;
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 11:
            case 12:
            case 13:
                i2 = 2;
                break;
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        if (i != 1) {
            if (i != 2) {
                if (i != 4 && i != 6) {
                    switch (i) {
                        case 8:
                            objArr[0] = "descriptor";
                            break;
                        case 9:
                        case 10:
                            objArr[0] = "explicitReceiverKind";
                            break;
                        case 11:
                        case 12:
                        case 13:
                            objArr[0] = "org/jetbrains/kotlin/resolve/calls/tasks/OldResolutionCandidate";
                            break;
                        default:
                            objArr[0] = "call";
                            break;
                    }
                } else {
                    objArr[0] = "descriptor";
                }
            } else {
                objArr[0] = "explicitReceiverKind";
            }
        } else {
            objArr[0] = "descriptor";
        }
        switch (i) {
            case 11:
                objArr[1] = "getCall";
                break;
            case 12:
                objArr[1] = "getDescriptor";
                break;
            case 13:
                objArr[1] = "getExplicitReceiverKind";
                break;
            default:
                objArr[1] = "org/jetbrains/kotlin/resolve/calls/tasks/OldResolutionCandidate";
                break;
        }
        switch (i) {
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                objArr[2] = CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME;
                break;
            case 10:
                objArr[2] = "setExplicitReceiverKind";
                break;
            case 11:
            case 12:
            case 13:
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 11:
            case 12:
            case 13:
                throw new IllegalStateException(str2);
            default:
                throw new IllegalArgumentException(str2);
        }
    }

    private OldResolutionCandidate(Call call, D d, ReceiverValue receiverValue, ExplicitReceiverKind explicitReceiverKind, TypeSubstitutor typeSubstitutor) {
        if (call == null) {
            $$$reportNull$$$0(0);
        }
        if (d == null) {
            $$$reportNull$$$0(1);
        }
        if (explicitReceiverKind == null) {
            $$$reportNull$$$0(2);
        }
        this.call = call;
        this.candidateDescriptor = d;
        this.dispatchReceiver = receiverValue;
        this.explicitReceiverKind = explicitReceiverKind;
        this.knownTypeParametersResultingSubstitutor = typeSubstitutor;
    }

    public static <D extends CallableDescriptor> OldResolutionCandidate<D> create(Call call, D d) {
        if (call == null) {
            $$$reportNull$$$0(3);
        }
        if (d == null) {
            $$$reportNull$$$0(4);
        }
        return new OldResolutionCandidate<>(call, d, null, ExplicitReceiverKind.NO_EXPLICIT_RECEIVER, null);
    }

    public static <D extends CallableDescriptor> OldResolutionCandidate<D> create(Call call, D d, TypeSubstitutor typeSubstitutor) {
        if (call == null) {
            $$$reportNull$$$0(5);
        }
        if (d == null) {
            $$$reportNull$$$0(6);
        }
        return new OldResolutionCandidate<>(call, d, null, ExplicitReceiverKind.NO_EXPLICIT_RECEIVER, typeSubstitutor);
    }

    public static <D extends CallableDescriptor> OldResolutionCandidate<D> create(Call call, D d, ReceiverValue receiverValue, ExplicitReceiverKind explicitReceiverKind, TypeSubstitutor typeSubstitutor) {
        if (call == null) {
            $$$reportNull$$$0(7);
        }
        if (d == null) {
            $$$reportNull$$$0(8);
        }
        if (explicitReceiverKind == null) {
            $$$reportNull$$$0(9);
        }
        return new OldResolutionCandidate<>(call, d, receiverValue, explicitReceiverKind, typeSubstitutor);
    }

    public void setDispatchReceiver(ReceiverValue receiverValue) {
        this.dispatchReceiver = receiverValue;
    }

    public void setExplicitReceiverKind(ExplicitReceiverKind explicitReceiverKind) {
        if (explicitReceiverKind == null) {
            $$$reportNull$$$0(10);
        }
        this.explicitReceiverKind = explicitReceiverKind;
    }

    public Call getCall() {
        Call call = this.call;
        if (call == null) {
            $$$reportNull$$$0(11);
        }
        return call;
    }

    public D getDescriptor() {
        D d = this.candidateDescriptor;
        if (d == null) {
            $$$reportNull$$$0(12);
        }
        return d;
    }

    public ReceiverValue getDispatchReceiver() {
        return this.dispatchReceiver;
    }

    public ExplicitReceiverKind getExplicitReceiverKind() {
        ExplicitReceiverKind explicitReceiverKind = this.explicitReceiverKind;
        if (explicitReceiverKind == null) {
            $$$reportNull$$$0(13);
        }
        return explicitReceiverKind;
    }

    public TypeSubstitutor getKnownTypeParametersResultingSubstitutor() {
        return this.knownTypeParametersResultingSubstitutor;
    }

    public String toString() {
        return this.candidateDescriptor.toString();
    }
}
