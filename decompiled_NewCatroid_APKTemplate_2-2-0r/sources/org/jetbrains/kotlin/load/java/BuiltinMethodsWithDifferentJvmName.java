package org.jetbrains.kotlin.load.java;

import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.load.kotlin.MethodSignatureMappingKt;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;

/* JADX INFO: compiled from: specialBuiltinMembers.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\u0005J\u000e\u0010\n\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\u0005R\u0015\u0010\u0003\u001a\u00020\u0004*\u00020\u00058F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0006¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/load/java/BuiltinMethodsWithDifferentJvmName;", "Lorg/jetbrains/kotlin/load/java/SpecialGenericSignatures;", "()V", "isRemoveAtByIndex", "", "Lorg/jetbrains/kotlin/descriptors/SimpleFunctionDescriptor;", "(Lorg/jetbrains/kotlin/descriptors/SimpleFunctionDescriptor;)Z", "getJvmName", "Lorg/jetbrains/kotlin/name/Name;", "functionDescriptor", "isBuiltinFunctionWithDifferentNameInJvm", "descriptors.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BuiltinMethodsWithDifferentJvmName extends SpecialGenericSignatures {
    public static final BuiltinMethodsWithDifferentJvmName INSTANCE = new BuiltinMethodsWithDifferentJvmName();

    private BuiltinMethodsWithDifferentJvmName() {
    }

    public final Name getJvmName(SimpleFunctionDescriptor functionDescriptor) {
        Intrinsics.checkNotNullParameter(functionDescriptor, "functionDescriptor");
        Map<String, Name> signature_to_jvm_representation_name = SpecialGenericSignatures.INSTANCE.getSIGNATURE_TO_JVM_REPRESENTATION_NAME();
        String strComputeJvmSignature = MethodSignatureMappingKt.computeJvmSignature(functionDescriptor);
        if (strComputeJvmSignature == null) {
            return null;
        }
        return signature_to_jvm_representation_name.get(strComputeJvmSignature);
    }

    public final boolean isBuiltinFunctionWithDifferentNameInJvm(final SimpleFunctionDescriptor functionDescriptor) {
        Intrinsics.checkNotNullParameter(functionDescriptor, "functionDescriptor");
        return KotlinBuiltIns.isBuiltIn(functionDescriptor) && DescriptorUtilsKt.firstOverridden$default(functionDescriptor, false, new Function1<CallableMemberDescriptor, Boolean>() { // from class: org.jetbrains.kotlin.load.java.BuiltinMethodsWithDifferentJvmName.isBuiltinFunctionWithDifferentNameInJvm.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(CallableMemberDescriptor it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(SpecialGenericSignatures.INSTANCE.getSIGNATURE_TO_JVM_REPRESENTATION_NAME().containsKey(MethodSignatureMappingKt.computeJvmSignature(functionDescriptor)));
            }
        }, 1, null) != null;
    }

    public final boolean isRemoveAtByIndex(SimpleFunctionDescriptor simpleFunctionDescriptor) {
        Intrinsics.checkNotNullParameter(simpleFunctionDescriptor, "<this>");
        return Intrinsics.areEqual(simpleFunctionDescriptor.getShortName().asString(), "removeAt") && Intrinsics.areEqual(MethodSignatureMappingKt.computeJvmSignature(simpleFunctionDescriptor), SpecialGenericSignatures.INSTANCE.getREMOVE_AT_NAME_AND_SIGNATURE().getSignature());
    }
}
