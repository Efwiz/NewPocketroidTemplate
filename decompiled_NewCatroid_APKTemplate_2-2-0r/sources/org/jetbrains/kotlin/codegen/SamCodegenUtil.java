package org.jetbrains.kotlin.codegen;

import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.synthetic.SyntheticMemberDescriptor;
import org.jetbrains.kotlin.load.java.sam.SamAdapterDescriptor;
import org.jetbrains.kotlin.synthetic.SamAdapterExtensionFunctionDescriptor;

/* JADX INFO: loaded from: classes3.dex */
public class SamCodegenUtil {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 1) {
            objArr[0] = "fun";
        } else {
            objArr[0] = "descriptor";
        }
        objArr[1] = "org/jetbrains/kotlin/codegen/SamCodegenUtil";
        if (i != 1) {
            objArr[2] = "getOriginalIfSamAdapter";
        } else {
            objArr[2] = "resolveSamAdapter";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public static FunctionDescriptor getOriginalIfSamAdapter(FunctionDescriptor functionDescriptor) {
        if (functionDescriptor == null) {
            $$$reportNull$$$0(0);
        }
        if ((functionDescriptor instanceof SamAdapterDescriptor) || (functionDescriptor instanceof SamAdapterExtensionFunctionDescriptor)) {
            return (FunctionDescriptor) ((SyntheticMemberDescriptor) functionDescriptor).getBaseDescriptorForSynthetic();
        }
        return null;
    }

    public static <T extends FunctionDescriptor> T resolveSamAdapter(T t) {
        if (t == null) {
            $$$reportNull$$$0(1);
        }
        T t2 = (T) getOriginalIfSamAdapter(t);
        return t2 != null ? t2 : t;
    }

    private SamCodegenUtil() {
    }
}
