package org.jetbrains.kotlin.load.java;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyAccessorDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.load.java.descriptors.JavaClassDescriptor;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.checker.TypeCheckingProcedure;

/* JADX INFO: compiled from: specialBuiltinMembers.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000$\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0006\u001a\u00020\u0002\u001a\u0012\u0010\u0007\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u0006\u001a\u00020\u0002H\u0002\u001a\n\u0010\b\u001a\u00020\u0001*\u00020\u0002\u001a\u001b\u0010\t\u001a\u0004\u0018\u0001H\n\"\b\b\u0000\u0010\n*\u00020\u0002*\u0002H\n¢\u0006\u0002\u0010\u000b\u001a\u001b\u0010\f\u001a\u0004\u0018\u0001H\n\"\b\b\u0000\u0010\n*\u00020\u0002*\u0002H\n¢\u0006\u0002\u0010\u000b\u001a\u001b\u0010\r\u001a\u0004\u0018\u0001H\n\"\b\b\u0000\u0010\n*\u00020\u0002*\u0002H\n¢\u0006\u0002\u0010\u000b\u001a\u0012\u0010\u000e\u001a\u00020\u0001*\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011\u001a\n\u0010\u0012\u001a\u00020\u0001*\u00020\u0002\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0000\u0010\u0003¨\u0006\u0013"}, d2 = {"isFromJava", "", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "(Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;)Z", "getJvmMethodNameIfSpecial", "", "callableMemberDescriptor", "getOverriddenBuiltinThatAffectsJvmName", "doesOverrideBuiltinWithDifferentJvmName", "getOverriddenBuiltinReflectingJvmDescriptor", ExifInterface.GPS_DIRECTION_TRUE, "(Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;)Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "getOverriddenBuiltinWithDifferentJvmName", "getOverriddenSpecialBuiltin", "hasRealKotlinSuperClassWithOverrideOf", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "specialCallableDescriptor", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "isFromJavaOrBuiltins", "descriptors.jvm"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class SpecialBuiltinMembers {
    public static final <T extends CallableMemberDescriptor> T getOverriddenBuiltinWithDifferentJvmName(T t) {
        Intrinsics.checkNotNullParameter(t, "<this>");
        if (!SpecialGenericSignatures.INSTANCE.getORIGINAL_SHORT_NAMES().contains(t.getShortName()) && !BuiltinSpecialProperties.INSTANCE.getSPECIAL_SHORT_NAMES().contains(DescriptorUtilsKt.getPropertyIfAccessor(t).getShortName())) {
            return null;
        }
        if (t instanceof PropertyDescriptor ? true : t instanceof PropertyAccessorDescriptor) {
            return (T) DescriptorUtilsKt.firstOverridden$default(t, false, new Function1<CallableMemberDescriptor, Boolean>() { // from class: org.jetbrains.kotlin.load.java.SpecialBuiltinMembers.getOverriddenBuiltinWithDifferentJvmName.1
                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(CallableMemberDescriptor it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return Boolean.valueOf(ClassicBuiltinSpecialProperties.INSTANCE.hasBuiltinSpecialPropertyFqName(DescriptorUtilsKt.getPropertyIfAccessor(it)));
                }
            }, 1, null);
        }
        if (t instanceof SimpleFunctionDescriptor) {
            return (T) DescriptorUtilsKt.firstOverridden$default(t, false, new Function1<CallableMemberDescriptor, Boolean>() { // from class: org.jetbrains.kotlin.load.java.SpecialBuiltinMembers.getOverriddenBuiltinWithDifferentJvmName.2
                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(CallableMemberDescriptor it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return Boolean.valueOf(BuiltinMethodsWithDifferentJvmName.INSTANCE.isBuiltinFunctionWithDifferentNameInJvm((SimpleFunctionDescriptor) it));
                }
            }, 1, null);
        }
        return null;
    }

    public static final boolean doesOverrideBuiltinWithDifferentJvmName(CallableMemberDescriptor callableMemberDescriptor) {
        Intrinsics.checkNotNullParameter(callableMemberDescriptor, "<this>");
        return getOverriddenBuiltinWithDifferentJvmName(callableMemberDescriptor) != null;
    }

    public static final <T extends CallableMemberDescriptor> T getOverriddenSpecialBuiltin(T t) {
        Intrinsics.checkNotNullParameter(t, "<this>");
        T t2 = (T) getOverriddenBuiltinWithDifferentJvmName(t);
        if (t2 != null) {
            return t2;
        }
        BuiltinMethodsWithSpecialGenericSignature builtinMethodsWithSpecialGenericSignature = BuiltinMethodsWithSpecialGenericSignature.INSTANCE;
        Name name2 = t.getShortName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        if (builtinMethodsWithSpecialGenericSignature.getSameAsBuiltinMethodWithErasedValueParameters(name2)) {
            return (T) DescriptorUtilsKt.firstOverridden$default(t, false, new Function1<CallableMemberDescriptor, Boolean>() { // from class: org.jetbrains.kotlin.load.java.SpecialBuiltinMembers.getOverriddenSpecialBuiltin.2
                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(CallableMemberDescriptor it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return Boolean.valueOf(KotlinBuiltIns.isBuiltIn(it) && BuiltinMethodsWithSpecialGenericSignature.getSpecialSignatureInfo(it) != null);
                }
            }, 1, null);
        }
        return null;
    }

    public static final <T extends CallableMemberDescriptor> T getOverriddenBuiltinReflectingJvmDescriptor(T t) {
        CallableMemberDescriptor callableMemberDescriptorFirstOverridden$default;
        Intrinsics.checkNotNullParameter(t, "<this>");
        T t2 = (T) getOverriddenBuiltinWithDifferentJvmName(t);
        if (t2 != null) {
            return t2;
        }
        BuiltinMethodsWithSpecialGenericSignature builtinMethodsWithSpecialGenericSignature = BuiltinMethodsWithSpecialGenericSignature.INSTANCE;
        Name name2 = t.getShortName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        if (builtinMethodsWithSpecialGenericSignature.getSameAsBuiltinMethodWithErasedValueParameters(name2) && (callableMemberDescriptorFirstOverridden$default = DescriptorUtilsKt.firstOverridden$default(t, false, new Function1<CallableMemberDescriptor, Boolean>() { // from class: org.jetbrains.kotlin.load.java.SpecialBuiltinMembers.getOverriddenBuiltinReflectingJvmDescriptor.2
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(CallableMemberDescriptor it) {
                Intrinsics.checkNotNullParameter(it, "it");
                boolean z = false;
                if (KotlinBuiltIns.isBuiltIn(it)) {
                    SpecialGenericSignatures.SpecialSignatureInfo specialSignatureInfo = BuiltinMethodsWithSpecialGenericSignature.getSpecialSignatureInfo(it);
                    if (specialSignatureInfo != null ? specialSignatureInfo.getIsObjectReplacedWithTypeParameter() : false) {
                        z = true;
                    }
                }
                return Boolean.valueOf(z);
            }
        }, 1, null)) != null) {
            return (T) callableMemberDescriptorFirstOverridden$default.getOriginal();
        }
        return null;
    }

    public static final String getJvmMethodNameIfSpecial(CallableMemberDescriptor callableMemberDescriptor) {
        CallableMemberDescriptor propertyIfAccessor;
        Name jvmName;
        Intrinsics.checkNotNullParameter(callableMemberDescriptor, "callableMemberDescriptor");
        CallableMemberDescriptor overriddenBuiltinThatAffectsJvmName = getOverriddenBuiltinThatAffectsJvmName(callableMemberDescriptor);
        if (overriddenBuiltinThatAffectsJvmName == null || (propertyIfAccessor = DescriptorUtilsKt.getPropertyIfAccessor(overriddenBuiltinThatAffectsJvmName)) == null) {
            return null;
        }
        if (propertyIfAccessor instanceof PropertyDescriptor) {
            return ClassicBuiltinSpecialProperties.INSTANCE.getBuiltinSpecialPropertyGetterName(propertyIfAccessor);
        }
        if (!(propertyIfAccessor instanceof SimpleFunctionDescriptor) || (jvmName = BuiltinMethodsWithDifferentJvmName.INSTANCE.getJvmName((SimpleFunctionDescriptor) propertyIfAccessor)) == null) {
            return null;
        }
        return jvmName.asString();
    }

    private static final CallableMemberDescriptor getOverriddenBuiltinThatAffectsJvmName(CallableMemberDescriptor callableMemberDescriptor) {
        if (KotlinBuiltIns.isBuiltIn(callableMemberDescriptor)) {
            return getOverriddenBuiltinWithDifferentJvmName(callableMemberDescriptor);
        }
        return null;
    }

    public static final boolean hasRealKotlinSuperClassWithOverrideOf(ClassDescriptor classDescriptor, CallableDescriptor specialCallableDescriptor) {
        Intrinsics.checkNotNullParameter(classDescriptor, "<this>");
        Intrinsics.checkNotNullParameter(specialCallableDescriptor, "specialCallableDescriptor");
        DeclarationDescriptor containingDeclaration = specialCallableDescriptor.getContainingDeclaration();
        Intrinsics.checkNotNull(containingDeclaration, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
        SimpleType defaultType = ((ClassDescriptor) containingDeclaration).getDefaultType();
        Intrinsics.checkNotNullExpressionValue(defaultType, "getDefaultType(...)");
        for (ClassDescriptor superClassDescriptor = DescriptorUtils.getSuperClassDescriptor(classDescriptor); superClassDescriptor != null; superClassDescriptor = DescriptorUtils.getSuperClassDescriptor(superClassDescriptor)) {
            if (!(superClassDescriptor instanceof JavaClassDescriptor) && TypeCheckingProcedure.findCorrespondingSupertype(superClassDescriptor.getDefaultType(), defaultType) != null) {
                return !KotlinBuiltIns.isBuiltIn(superClassDescriptor);
            }
        }
        return false;
    }

    public static final boolean isFromJava(CallableMemberDescriptor callableMemberDescriptor) {
        Intrinsics.checkNotNullParameter(callableMemberDescriptor, "<this>");
        return DescriptorUtilsKt.getPropertyIfAccessor(callableMemberDescriptor).getContainingDeclaration() instanceof JavaClassDescriptor;
    }

    public static final boolean isFromJavaOrBuiltins(CallableMemberDescriptor callableMemberDescriptor) {
        Intrinsics.checkNotNullParameter(callableMemberDescriptor, "<this>");
        return isFromJava(callableMemberDescriptor) || KotlinBuiltIns.isBuiltIn(callableMemberDescriptor);
    }
}
