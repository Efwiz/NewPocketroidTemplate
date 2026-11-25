package org.jetbrains.kotlin.psi2ir.generators;

import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.synthetic.FunctionInterfaceAdapterDescriptor;
import org.jetbrains.kotlin.descriptors.synthetic.FunctionInterfaceAdapterExtensionFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.synthetic.FunctionInterfaceConstructorDescriptor;
import org.jetbrains.kotlin.resolve.sam.SamConversionResolverImplKt;
import org.jetbrains.kotlin.types.FlexibleType;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.KotlinTypeFactory;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeSubstitutor;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.types.Variance;

/* JADX INFO: compiled from: samConversions.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000(\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u000e\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u0002H\u0000\u001a\f\u0010\u0003\u001a\u00020\u0004*\u00020\u0004H\u0000\u001a\f\u0010\u0003\u001a\u00020\u0005*\u00020\u0005H\u0002\u001a\f\u0010\u0006\u001a\u00020\u0007*\u00020\bH\u0000\u001a\u0014\u0010\t\u001a\u00020\u0007*\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0004H\u0000¨\u0006\f"}, d2 = {"getOriginalForFunctionInterfaceAdapter", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "getSubstitutedFunctionTypeForSamType", "Lorg/jetbrains/kotlin/types/KotlinType;", "Lorg/jetbrains/kotlin/types/SimpleType;", "isSamConstructor", "", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "isSamType", "Lorg/jetbrains/kotlin/psi2ir/generators/GeneratorExtensions$SamConversion;", "kotlinType", "ir.psi2ir"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class SamConversionsKt {
    public static final boolean isSamType(GeneratorExtensions.SamConversion samConversion, KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(samConversion, "<this>");
        Intrinsics.checkNotNullParameter(kotlinType, "kotlinType");
        ClassifierDescriptor this$0 = kotlinType.getConstructor().getThis$0();
        return ((this$0 instanceof ClassDescriptor) && ((ClassDescriptor) this$0).isFun()) || samConversion.isPlatformSamType(kotlinType);
    }

    public static final boolean isSamConstructor(DeclarationDescriptor declarationDescriptor) {
        Intrinsics.checkNotNullParameter(declarationDescriptor, "<this>");
        return declarationDescriptor instanceof FunctionInterfaceConstructorDescriptor;
    }

    public static final FunctionDescriptor getOriginalForFunctionInterfaceAdapter(CallableDescriptor callableDescriptor) {
        Intrinsics.checkNotNullParameter(callableDescriptor, "<this>");
        if (callableDescriptor instanceof FunctionInterfaceAdapterDescriptor) {
            return (FunctionDescriptor) ((FunctionInterfaceAdapterDescriptor) callableDescriptor).getSamInterface();
        }
        if (callableDescriptor instanceof FunctionInterfaceAdapterExtensionFunctionDescriptor) {
            return ((FunctionInterfaceAdapterExtensionFunctionDescriptor) callableDescriptor).getSamInterface();
        }
        return null;
    }

    public static final KotlinType getSubstitutedFunctionTypeForSamType(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        UnwrappedType unwrappedTypeUnwrap = kotlinType.unwrap();
        if (unwrappedTypeUnwrap instanceof SimpleType) {
            return getSubstitutedFunctionTypeForSamType((SimpleType) unwrappedTypeUnwrap);
        }
        if (!(unwrappedTypeUnwrap instanceof FlexibleType)) {
            throw new NoWhenBranchMatchedException();
        }
        FlexibleType flexibleType = (FlexibleType) unwrappedTypeUnwrap;
        return KotlinTypeFactory.flexibleType(getSubstitutedFunctionTypeForSamType(flexibleType.getLowerBound()), getSubstitutedFunctionTypeForSamType(flexibleType.getUpperBound()));
    }

    private static final SimpleType getSubstitutedFunctionTypeForSamType(SimpleType simpleType) {
        ClassifierDescriptor this$0 = simpleType.getConstructor().getThis$0();
        ClassDescriptor classDescriptor = this$0 instanceof ClassDescriptor ? (ClassDescriptor) this$0 : null;
        if (classDescriptor == null) {
            throw new AssertionError("SAM should be represented by a class: " + simpleType);
        }
        FunctionDescriptor singleAbstractMethodOrNull = SamConversionResolverImplKt.getSingleAbstractMethodOrNull(classDescriptor);
        if (singleAbstractMethodOrNull == null) {
            throw new AssertionError(classDescriptor + " should have a single abstract method");
        }
        SimpleType simpleTypeMakeNullableAsSpecified = SamConversionResolverImplKt.getFunctionTypeForAbstractMethod(singleAbstractMethodOrNull, false).makeNullableAsSpecified(simpleType.getIsMarkedNullable());
        KotlinType kotlinTypeSubstitute = TypeSubstitutor.create(simpleType).substitute(simpleTypeMakeNullableAsSpecified, Variance.INVARIANT);
        if (kotlinTypeSubstitute == null) {
            throw new AssertionError("Failed to substitute function type " + simpleTypeMakeNullableAsSpecified + " corresponding to " + simpleType);
        }
        SimpleType simpleType2 = kotlinTypeSubstitute instanceof SimpleType ? (SimpleType) kotlinTypeSubstitute : null;
        if (simpleType2 != null) {
            return simpleType2;
        }
        throw new AssertionError("SAM type substitution result is not a simple type: " + simpleType + " -> " + kotlinTypeSubstitute);
    }
}
