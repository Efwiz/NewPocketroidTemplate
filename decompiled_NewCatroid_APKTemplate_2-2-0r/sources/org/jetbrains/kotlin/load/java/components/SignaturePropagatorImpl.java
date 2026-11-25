package org.jetbrains.kotlin.load.java.components;

import java.util.List;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.load.java.structure.JavaMethod;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.jvm.JvmBindingContextSlices;
import org.jetbrains.kotlin.resolve.jvm.kotlinSignature.SignaturesPropagationData;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: loaded from: classes5.dex */
public class SignaturePropagatorImpl implements SignaturePropagator {
    private final BindingTrace trace;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 1:
                objArr[0] = "method";
                break;
            case 2:
                objArr[0] = "owner";
                break;
            case 3:
                objArr[0] = "returnType";
                break;
            case 4:
                objArr[0] = "valueParameters";
                break;
            case 5:
                objArr[0] = "typeParameters";
                break;
            case 6:
                objArr[0] = "descriptor";
                break;
            case 7:
                objArr[0] = "signatureErrors";
                break;
            default:
                objArr[0] = "trace";
                break;
        }
        objArr[1] = "org/jetbrains/kotlin/load/java/components/SignaturePropagatorImpl";
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                objArr[2] = "resolvePropagatedSignature";
                break;
            case 6:
            case 7:
                objArr[2] = "reportSignatureErrors";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public SignaturePropagatorImpl(BindingTrace bindingTrace) {
        if (bindingTrace == null) {
            $$$reportNull$$$0(0);
        }
        this.trace = bindingTrace;
    }

    @Override // org.jetbrains.kotlin.load.java.components.SignaturePropagator
    public SignaturePropagator.PropagatedSignature resolvePropagatedSignature(JavaMethod javaMethod, ClassDescriptor classDescriptor, KotlinType kotlinType, KotlinType kotlinType2, List<ValueParameterDescriptor> list, List<TypeParameterDescriptor> list2) {
        if (javaMethod == null) {
            $$$reportNull$$$0(1);
        }
        if (classDescriptor == null) {
            $$$reportNull$$$0(2);
        }
        if (kotlinType == null) {
            $$$reportNull$$$0(3);
        }
        if (list == null) {
            $$$reportNull$$$0(4);
        }
        if (list2 == null) {
            $$$reportNull$$$0(5);
        }
        SignaturesPropagationData signaturesPropagationData = new SignaturesPropagationData(classDescriptor, kotlinType, kotlinType2, list, list2, javaMethod);
        return new SignaturePropagator.PropagatedSignature(kotlinType, signaturesPropagationData.getModifiedReceiverType(), signaturesPropagationData.getModifiedValueParameters(), list2, signaturesPropagationData.getSignatureErrors(), signaturesPropagationData.getModifiedHasStableParameterNames());
    }

    @Override // org.jetbrains.kotlin.load.java.components.SignaturePropagator
    public void reportSignatureErrors(CallableMemberDescriptor callableMemberDescriptor, List<String> list) {
        if (callableMemberDescriptor == null) {
            $$$reportNull$$$0(6);
        }
        if (list == null) {
            $$$reportNull$$$0(7);
        }
        this.trace.record(JvmBindingContextSlices.LOAD_FROM_JAVA_SIGNATURE_ERRORS, callableMemberDescriptor.getOriginal(), list);
    }
}
