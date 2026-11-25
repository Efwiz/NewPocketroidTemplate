package org.jetbrains.kotlin.resolve.calls.inference;

import java.util.List;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.psi.Call;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: loaded from: classes6.dex */
public class InferenceErrorData {
    public final Call call;
    public final ConstraintSystem constraintSystem;
    public final CallableDescriptor descriptor;
    public final KotlinType expectedType;
    public final KotlinType receiverArgumentType;
    public final List<KotlinType> valueArgumentsTypes;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 1:
            case 6:
                objArr[0] = "constraintSystem";
                break;
            case 2:
            case 7:
                objArr[0] = "valueArgumentsTypes";
                break;
            case 3:
            case 8:
                objArr[0] = "expectedType";
                break;
            case 4:
            case 9:
                objArr[0] = "call";
                break;
            case 5:
            default:
                objArr[0] = "descriptor";
                break;
        }
        objArr[1] = "org/jetbrains/kotlin/resolve/calls/inference/InferenceErrorData";
        switch (i) {
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                objArr[2] = CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME;
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    private InferenceErrorData(CallableDescriptor callableDescriptor, ConstraintSystem constraintSystem, List<KotlinType> list, KotlinType kotlinType, KotlinType kotlinType2, Call call) {
        if (callableDescriptor == null) {
            $$$reportNull$$$0(0);
        }
        if (constraintSystem == null) {
            $$$reportNull$$$0(1);
        }
        if (list == null) {
            $$$reportNull$$$0(2);
        }
        if (kotlinType2 == null) {
            $$$reportNull$$$0(3);
        }
        if (call == null) {
            $$$reportNull$$$0(4);
        }
        this.descriptor = callableDescriptor;
        this.constraintSystem = constraintSystem;
        this.receiverArgumentType = kotlinType;
        this.valueArgumentsTypes = list;
        this.expectedType = kotlinType2;
        this.call = call;
    }

    public static InferenceErrorData create(CallableDescriptor callableDescriptor, ConstraintSystem constraintSystem, List<KotlinType> list, KotlinType kotlinType, KotlinType kotlinType2, Call call) {
        if (callableDescriptor == null) {
            $$$reportNull$$$0(5);
        }
        if (constraintSystem == null) {
            $$$reportNull$$$0(6);
        }
        if (list == null) {
            $$$reportNull$$$0(7);
        }
        if (kotlinType2 == null) {
            $$$reportNull$$$0(8);
        }
        if (call == null) {
            $$$reportNull$$$0(9);
        }
        return new InferenceErrorData(callableDescriptor, constraintSystem, list, kotlinType, kotlinType2, call);
    }
}
