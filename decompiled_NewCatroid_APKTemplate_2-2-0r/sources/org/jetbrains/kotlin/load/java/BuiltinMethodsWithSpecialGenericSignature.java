package org.jetbrains.kotlin.load.java;

import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.load.kotlin.MethodSignatureMappingKt;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;

/* JADX INFO: compiled from: specialBuiltinMembers.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0012\u0010\f\u001a\u0004\u0018\u00010\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0007J\u0012\u0010\u0010\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u000e\u001a\u00020\u000fH\u0007J\u000e\u0010\u0011\u001a\u0004\u0018\u00010\u0012*\u00020\u0005H\u0007J\n\u0010\u0013\u001a\u00020\u0004*\u00020\u0005R\u0018\u0010\u0003\u001a\u00020\u0004*\u00020\u00058BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u0015\u0010\b\u001a\u00020\u0004*\u00020\t8F¢\u0006\u0006\u001a\u0004\b\n\u0010\u000b¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/load/java/BuiltinMethodsWithSpecialGenericSignature;", "Lorg/jetbrains/kotlin/load/java/SpecialGenericSignatures;", "()V", "hasErasedValueParametersInJava", "", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "getHasErasedValueParametersInJava", "(Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;)Z", "sameAsBuiltinMethodWithErasedValueParameters", "Lorg/jetbrains/kotlin/name/Name;", "getSameAsBuiltinMethodWithErasedValueParameters", "(Lorg/jetbrains/kotlin/name/Name;)Z", "getDefaultValueForOverriddenBuiltinFunction", "Lorg/jetbrains/kotlin/load/java/SpecialGenericSignatures$TypeSafeBarrierDescription;", "functionDescriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "getOverriddenBuiltinFunctionWithErasedValueParametersInJava", "getSpecialSignatureInfo", "Lorg/jetbrains/kotlin/load/java/SpecialGenericSignatures$SpecialSignatureInfo;", "isBuiltinWithSpecialDescriptorInJvm", "descriptors.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BuiltinMethodsWithSpecialGenericSignature extends SpecialGenericSignatures {
    public static final BuiltinMethodsWithSpecialGenericSignature INSTANCE = new BuiltinMethodsWithSpecialGenericSignature();

    private BuiltinMethodsWithSpecialGenericSignature() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean getHasErasedValueParametersInJava(CallableMemberDescriptor callableMemberDescriptor) {
        return CollectionsKt.contains(SpecialGenericSignatures.INSTANCE.getERASED_VALUE_PARAMETERS_SIGNATURES(), MethodSignatureMappingKt.computeJvmSignature(callableMemberDescriptor));
    }

    @JvmStatic
    public static final FunctionDescriptor getOverriddenBuiltinFunctionWithErasedValueParametersInJava(FunctionDescriptor functionDescriptor) {
        Intrinsics.checkNotNullParameter(functionDescriptor, "functionDescriptor");
        BuiltinMethodsWithSpecialGenericSignature builtinMethodsWithSpecialGenericSignature = INSTANCE;
        Name name2 = functionDescriptor.getShortName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        if (builtinMethodsWithSpecialGenericSignature.getSameAsBuiltinMethodWithErasedValueParameters(name2)) {
            return (FunctionDescriptor) DescriptorUtilsKt.firstOverridden$default(functionDescriptor, false, new Function1<CallableMemberDescriptor, Boolean>() { // from class: org.jetbrains.kotlin.load.java.BuiltinMethodsWithSpecialGenericSignature.getOverriddenBuiltinFunctionWithErasedValueParametersInJava.1
                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(CallableMemberDescriptor it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return Boolean.valueOf(BuiltinMethodsWithSpecialGenericSignature.INSTANCE.getHasErasedValueParametersInJava(it));
                }
            }, 1, null);
        }
        return null;
    }

    @JvmStatic
    public static final SpecialGenericSignatures.TypeSafeBarrierDescription getDefaultValueForOverriddenBuiltinFunction(FunctionDescriptor functionDescriptor) {
        CallableMemberDescriptor callableMemberDescriptorFirstOverridden$default;
        Intrinsics.checkNotNullParameter(functionDescriptor, "functionDescriptor");
        if (SpecialGenericSignatures.INSTANCE.getERASED_VALUE_PARAMETERS_SHORT_NAMES().contains(functionDescriptor.getShortName()) && (callableMemberDescriptorFirstOverridden$default = DescriptorUtilsKt.firstOverridden$default(functionDescriptor, false, new Function1<CallableMemberDescriptor, Boolean>() { // from class: org.jetbrains.kotlin.load.java.BuiltinMethodsWithSpecialGenericSignature.getDefaultValueForOverriddenBuiltinFunction.1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(CallableMemberDescriptor it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(CollectionsKt.contains(SpecialGenericSignatures.INSTANCE.getSIGNATURE_TO_DEFAULT_VALUES_MAP().keySet(), MethodSignatureMappingKt.computeJvmSignature(it)));
            }
        }, 1, null)) != null) {
            return SpecialGenericSignatures.INSTANCE.getSIGNATURE_TO_DEFAULT_VALUES_MAP().get(MethodSignatureMappingKt.computeJvmSignature(callableMemberDescriptorFirstOverridden$default));
        }
        return null;
    }

    public final boolean getSameAsBuiltinMethodWithErasedValueParameters(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "<this>");
        return SpecialGenericSignatures.INSTANCE.getERASED_VALUE_PARAMETERS_SHORT_NAMES().contains(name2);
    }

    public final boolean isBuiltinWithSpecialDescriptorInJvm(CallableMemberDescriptor callableMemberDescriptor) {
        Intrinsics.checkNotNullParameter(callableMemberDescriptor, "<this>");
        if (!KotlinBuiltIns.isBuiltIn(callableMemberDescriptor)) {
            return false;
        }
        SpecialGenericSignatures.SpecialSignatureInfo specialSignatureInfo = getSpecialSignatureInfo(callableMemberDescriptor);
        return (specialSignatureInfo != null ? specialSignatureInfo.getIsObjectReplacedWithTypeParameter() : false) || SpecialBuiltinMembers.doesOverrideBuiltinWithDifferentJvmName(callableMemberDescriptor);
    }

    @JvmStatic
    public static final SpecialGenericSignatures.SpecialSignatureInfo getSpecialSignatureInfo(CallableMemberDescriptor callableMemberDescriptor) {
        CallableMemberDescriptor callableMemberDescriptorFirstOverridden$default;
        String strComputeJvmSignature;
        Intrinsics.checkNotNullParameter(callableMemberDescriptor, "<this>");
        if (!SpecialGenericSignatures.INSTANCE.getERASED_VALUE_PARAMETERS_SHORT_NAMES().contains(callableMemberDescriptor.getShortName()) || (callableMemberDescriptorFirstOverridden$default = DescriptorUtilsKt.firstOverridden$default(callableMemberDescriptor, false, new Function1<CallableMemberDescriptor, Boolean>() { // from class: org.jetbrains.kotlin.load.java.BuiltinMethodsWithSpecialGenericSignature$getSpecialSignatureInfo$builtinSignature$1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(CallableMemberDescriptor it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf((it instanceof FunctionDescriptor) && BuiltinMethodsWithSpecialGenericSignature.INSTANCE.getHasErasedValueParametersInJava(it));
            }
        }, 1, null)) == null || (strComputeJvmSignature = MethodSignatureMappingKt.computeJvmSignature(callableMemberDescriptorFirstOverridden$default)) == null) {
            return null;
        }
        return SpecialGenericSignatures.INSTANCE.getSpecialSignatureInfo(strComputeJvmSignature);
    }
}
