package org.jetbrains.kotlin.codegen;

import com.reandroid.dex.sections.Marker;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.bridges.Bridge;
import org.jetbrains.kotlin.backend.common.bridges.BridgesKt;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.config.JvmDefaultMode;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyAccessorDescriptor;
import org.jetbrains.kotlin.load.java.descriptors.JavaForKotlinOverridePropertyDescriptor;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.jvm.annotations.JvmAnnotationUtilKt;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.util.DeclarationUtilKt;

/* JADX INFO: compiled from: JvmBridgesImpl.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000B\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\u001aB\u0010\u0004\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0007\u0012\u0004\u0012\u00020\b0\u00060\u0005\"\u0004\b\u0000\u0010\u00072\u0006\u0010\t\u001a\u00020\u00022\u0012\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u0002H\u00070\u000b2\u0006\u0010\f\u001a\u00020\r\u001a\u0016\u0010\u000e\u001a\u00020\u00012\u0006\u0010\t\u001a\u00020\u00022\u0006\u0010\u000f\u001a\u00020\u0010\u001a\u0018\u0010\u0011\u001a\u00020\u00012\u0006\u0010\t\u001a\u00020\u00022\u0006\u0010\u000f\u001a\u00020\u0010H\u0002\u001a\f\u0010\u0012\u001a\u00020\u0013*\u00020\u0002H\u0002\u001a\u0014\u0010\u0014\u001a\u00020\u0001*\u00020\u00152\u0006\u0010\u000f\u001a\u00020\u0010H\u0002\"\u0018\u0010\u0000\u001a\u00020\u0001*\u00020\u00028BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0000\u0010\u0003¨\u0006\u0016"}, d2 = {"isJavaForKotlinOverrideProperty", "", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "(Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;)Z", "generateBridgesForFunctionDescriptorForJvm", "", "Lorg/jetbrains/kotlin/backend/common/bridges/Bridge;", "Signature", "Lorg/jetbrains/kotlin/codegen/DescriptorBasedFunctionHandleForJvm;", "descriptor", "signature", "Lkotlin/Function1;", "state", "Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "isAbstractOnJvmIgnoringActualModality", "jvmDefaultMode", "Lorg/jetbrains/kotlin/config/JvmDefaultMode;", "needToGenerateDelegationToDefaultImpls", "containerEntityForEqualityAndHashCode", "", "isJvmDefaultOrPlatformDependent", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", Marker.BACKEND}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class JvmBridgesImplKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final Object containerEntityForEqualityAndHashCode(FunctionDescriptor functionDescriptor) {
        DeclarationDescriptor containingDeclaration = functionDescriptor.getContainingDeclaration();
        ClassDescriptor classDescriptor = containingDeclaration instanceof ClassDescriptor ? (ClassDescriptor) containingDeclaration : null;
        TypeConstructor typeConstructor = classDescriptor != null ? classDescriptor.getTypeConstructor() : null;
        if (typeConstructor != null) {
            return typeConstructor;
        }
        DeclarationDescriptor containingDeclaration2 = functionDescriptor.getContainingDeclaration();
        Intrinsics.checkNotNullExpressionValue(containingDeclaration2, "getContainingDeclaration(...)");
        return containingDeclaration2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean isJavaForKotlinOverrideProperty(FunctionDescriptor functionDescriptor) {
        return (functionDescriptor instanceof PropertyAccessorDescriptor) && (((PropertyAccessorDescriptor) functionDescriptor).getCorrespondingProperty() instanceof JavaForKotlinOverridePropertyDescriptor);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean isJvmDefaultOrPlatformDependent(CallableMemberDescriptor callableMemberDescriptor, JvmDefaultMode jvmDefaultMode) {
        return JvmAnnotationUtilKt.isCompiledToJvmDefault(callableMemberDescriptor, jvmDefaultMode) || JvmAnnotationUtilKt.hasPlatformDependentAnnotation(callableMemberDescriptor);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean needToGenerateDelegationToDefaultImpls(FunctionDescriptor functionDescriptor, JvmDefaultMode jvmDefaultMode) {
        CallableMemberDescriptor callableMemberDescriptorFindImplementationFromInterface;
        FunctionDescriptor functionDescriptor2 = functionDescriptor;
        if (DeclarationUtilKt.findInterfaceImplementation$default(functionDescriptor2, false, 2, null) == null || (callableMemberDescriptorFindImplementationFromInterface = DeclarationUtilKt.findImplementationFromInterface(functionDescriptor2)) == null) {
            return false;
        }
        return !isJvmDefaultOrPlatformDependent(callableMemberDescriptorFindImplementationFromInterface, jvmDefaultMode);
    }

    public static final boolean isAbstractOnJvmIgnoringActualModality(FunctionDescriptor descriptor, JvmDefaultMode jvmDefaultMode) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(jvmDefaultMode, "jvmDefaultMode");
        if (DescriptorUtils.isInterface(descriptor.getContainingDeclaration())) {
            return !isJvmDefaultOrPlatformDependent(descriptor, jvmDefaultMode);
        }
        return false;
    }

    public static final <Signature> Set<Bridge<Signature, DescriptorBasedFunctionHandleForJvm>> generateBridgesForFunctionDescriptorForJvm(FunctionDescriptor descriptor, final Function1<? super FunctionDescriptor, ? extends Signature> signature, GenerationState state) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(signature, "signature");
        Intrinsics.checkNotNullParameter(state, "state");
        return BridgesKt.generateBridges(new DescriptorBasedFunctionHandleForJvm(descriptor, state), new Function1<DescriptorBasedFunctionHandleForJvm, Signature>() { // from class: org.jetbrains.kotlin.codegen.JvmBridgesImplKt.generateBridgesForFunctionDescriptorForJvm.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Signature invoke(DescriptorBasedFunctionHandleForJvm it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return signature.invoke(it.getDescriptor());
            }
        });
    }
}
