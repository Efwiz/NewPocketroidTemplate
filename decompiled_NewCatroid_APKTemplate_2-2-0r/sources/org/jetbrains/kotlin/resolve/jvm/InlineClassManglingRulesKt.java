package org.jetbrains.kotlin.resolve.jvm;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.InlineClassesUtilsKt;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: inlineClassManglingRules.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000(\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0010\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0002\u001a\u000e\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0006\u001a\u000e\u0010\u0007\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0006\u001a\u000e\u0010\b\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0006\u001a\u0014\u0010\t\u001a\u00020\u0001*\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0001H\u0002\u001a\n\u0010\f\u001a\u00020\u0001*\u00020\r\u001a\n\u0010\f\u001a\u00020\u0001*\u00020\n\u001a\f\u0010\u000e\u001a\u00020\u0001*\u00020\nH\u0002¨\u0006\u000f"}, d2 = {"isDontMangleClass", "", "classDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "requiresFunctionNameManglingForParameterTypes", "descriptor", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "requiresFunctionNameManglingForReturnType", "shouldHideConstructorDueToValueClassTypeValueParameters", "isTypeParameterWithUpperBoundThatRequiresMangling", "Lorg/jetbrains/kotlin/types/KotlinType;", "includeMfvc", "isValueClassThatRequiresMangling", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "requiresFunctionNameManglingInParameterTypes", "descriptors.jvm"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class InlineClassManglingRulesKt {
    public static final boolean shouldHideConstructorDueToValueClassTypeValueParameters(CallableMemberDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        ClassConstructorDescriptor classConstructorDescriptor = descriptor instanceof ClassConstructorDescriptor ? (ClassConstructorDescriptor) descriptor : null;
        if (classConstructorDescriptor == null || DescriptorVisibilities.isPrivate(classConstructorDescriptor.getVisibilityImpl())) {
            return false;
        }
        ClassDescriptor constructedClass = classConstructorDescriptor.getConstructedClass();
        Intrinsics.checkNotNullExpressionValue(constructedClass, "getConstructedClass(...)");
        if (InlineClassesUtilsKt.isValueClass(constructedClass) || DescriptorUtils.isSealedClass(classConstructorDescriptor.getConstructedClass())) {
            return false;
        }
        List<ValueParameterDescriptor> valueParameters = classConstructorDescriptor.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
        List<ValueParameterDescriptor> list = valueParameters;
        if ((list instanceof Collection) && list.isEmpty()) {
            return false;
        }
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            KotlinType type = ((ValueParameterDescriptor) it.next()).getType();
            Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
            if (requiresFunctionNameManglingInParameterTypes(type)) {
                return true;
            }
        }
        return false;
    }

    public static final boolean requiresFunctionNameManglingForParameterTypes(CallableMemberDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        ReceiverParameterDescriptor extensionReceiverParameter = descriptor.getExtensionReceiverParameter();
        KotlinType type = extensionReceiverParameter != null ? extensionReceiverParameter.getType() : null;
        if (type == null || !requiresFunctionNameManglingInParameterTypes(type)) {
            List<ValueParameterDescriptor> valueParameters = descriptor.getValueParameters();
            Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
            List<ValueParameterDescriptor> list = valueParameters;
            if (!(list instanceof Collection) || !list.isEmpty()) {
                Iterator<T> it = list.iterator();
                while (it.hasNext()) {
                    KotlinType type2 = ((ValueParameterDescriptor) it.next()).getType();
                    Intrinsics.checkNotNullExpressionValue(type2, "getType(...)");
                    if (requiresFunctionNameManglingInParameterTypes(type2)) {
                    }
                }
            }
            return false;
        }
        return true;
    }

    public static final boolean requiresFunctionNameManglingForReturnType(CallableMemberDescriptor descriptor) {
        KotlinType returnType;
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        if ((descriptor.getContainingDeclaration() instanceof ClassDescriptor) && (returnType = descriptor.getReturnType()) != null) {
            return InlineClassesUtilsKt.isInlineClassType(returnType) || isTypeParameterWithUpperBoundThatRequiresMangling(returnType, false);
        }
        return false;
    }

    public static final boolean isValueClassThatRequiresMangling(DeclarationDescriptor declarationDescriptor) {
        Intrinsics.checkNotNullParameter(declarationDescriptor, "<this>");
        return InlineClassesUtilsKt.isValueClass(declarationDescriptor) && !isDontMangleClass((ClassDescriptor) declarationDescriptor);
    }

    public static final boolean isValueClassThatRequiresMangling(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        ClassifierDescriptor this$0 = kotlinType.getConstructor().getThis$0();
        if (this$0 == null) {
            return false;
        }
        ClassifierDescriptor classifierDescriptor = this$0;
        return (InlineClassesUtilsKt.isInlineClass(classifierDescriptor) && isValueClassThatRequiresMangling(classifierDescriptor)) || InlineClassesUtilsKt.needsMfvcFlattening(kotlinType);
    }

    private static final boolean requiresFunctionNameManglingInParameterTypes(KotlinType kotlinType) {
        return isValueClassThatRequiresMangling(kotlinType) || isTypeParameterWithUpperBoundThatRequiresMangling(kotlinType, true);
    }

    private static final boolean isDontMangleClass(ClassDescriptor classDescriptor) {
        return Intrinsics.areEqual(DescriptorUtilsKt.getFqNameSafe(classDescriptor), StandardNames.RESULT_FQ_NAME);
    }

    private static final boolean isTypeParameterWithUpperBoundThatRequiresMangling(KotlinType kotlinType, boolean z) {
        ClassifierDescriptor this$0 = kotlinType.getConstructor().getThis$0();
        TypeParameterDescriptor typeParameterDescriptor = this$0 instanceof TypeParameterDescriptor ? (TypeParameterDescriptor) this$0 : null;
        if (typeParameterDescriptor == null) {
            return false;
        }
        return (z || !InlineClassesUtilsKt.isMultiFieldValueClass(typeParameterDescriptor)) && requiresFunctionNameManglingInParameterTypes(TypeUtilsKt.getRepresentativeUpperBound(typeParameterDescriptor));
    }
}
