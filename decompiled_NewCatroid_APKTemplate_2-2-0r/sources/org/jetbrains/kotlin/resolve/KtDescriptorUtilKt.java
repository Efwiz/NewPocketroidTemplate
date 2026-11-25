package org.jetbrains.kotlin.resolve;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.ParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.types.DeferredType;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: ktDescriptorUtil.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0000\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002\u001a\u0014\u0010\u0003\u001a\u00020\u0004*\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0004H\u0000\u001a\n\u0010\u0007\u001a\u00020\u0004*\u00020\b¨\u0006\t"}, d2 = {"indexOrMinusOne", "", "Lorg/jetbrains/kotlin/descriptors/ParameterDescriptor;", "isEffectivelyFinal", "", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "ignoreEnumClassFinality", "isFunctionForExpectTypeFromCastFeature", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class KtDescriptorUtilKt {
    public static final boolean isFunctionForExpectTypeFromCastFeature(FunctionDescriptor functionDescriptor) {
        KotlinType returnType;
        ReceiverParameterDescriptor extensionReceiverParameter;
        KotlinType type;
        Intrinsics.checkNotNullParameter(functionDescriptor, "<this>");
        List<TypeParameterDescriptor> typeParameters = functionDescriptor.getTypeParameters();
        Intrinsics.checkNotNullExpressionValue(typeParameters, "getTypeParameters(...)");
        TypeParameterDescriptor typeParameterDescriptor = (TypeParameterDescriptor) CollectionsKt.singleOrNull((List) typeParameters);
        if (typeParameterDescriptor == null || (returnType = functionDescriptor.getReturnType()) == null) {
            return false;
        }
        if (((returnType instanceof DeferredType) && ((DeferredType) returnType).isComputing()) || !Intrinsics.areEqual(returnType.getConstructor(), typeParameterDescriptor.getTypeConstructor())) {
            return false;
        }
        List<ValueParameterDescriptor> valueParameters = functionDescriptor.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
        List<ValueParameterDescriptor> list = valueParameters;
        if ((list instanceof Collection) && list.isEmpty()) {
            extensionReceiverParameter = functionDescriptor.getExtensionReceiverParameter();
            if (extensionReceiverParameter != null) {
            }
            return true;
        }
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            KotlinType type2 = ((ValueParameterDescriptor) it.next()).getType();
            Intrinsics.checkNotNullExpressionValue(type2, "getType(...)");
            if (isFunctionForExpectTypeFromCastFeature$isBadType(type2, typeParameterDescriptor)) {
            }
        }
        extensionReceiverParameter = functionDescriptor.getExtensionReceiverParameter();
        if (extensionReceiverParameter != null || (type = extensionReceiverParameter.getType()) == null || !isFunctionForExpectTypeFromCastFeature$isBadType(type, typeParameterDescriptor)) {
            return true;
        }
        return false;
    }

    private static final boolean isFunctionForExpectTypeFromCastFeature$isBadType(KotlinType kotlinType, final TypeParameterDescriptor typeParameterDescriptor) {
        return TypeUtilsKt.contains(kotlinType, new Function1<UnwrappedType, Boolean>() { // from class: org.jetbrains.kotlin.resolve.KtDescriptorUtilKt$isFunctionForExpectTypeFromCastFeature$isBadType$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(UnwrappedType it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(Intrinsics.areEqual(it.getConstructor(), typeParameterDescriptor.getTypeConstructor()));
            }
        });
    }

    public static final boolean isEffectivelyFinal(CallableMemberDescriptor callableMemberDescriptor, boolean z) {
        Intrinsics.checkNotNullParameter(callableMemberDescriptor, "<this>");
        if (callableMemberDescriptor.getModality() != Modality.FINAL) {
            DeclarationDescriptor containingDeclaration = callableMemberDescriptor.getContainingDeclaration();
            if ((!z && DescriptorUtils.isEnumClass(containingDeclaration)) || !(containingDeclaration instanceof ClassDescriptor) || ((ClassDescriptor) containingDeclaration).getModality() != Modality.FINAL) {
                return false;
            }
        }
        return true;
    }

    public static final int indexOrMinusOne(ParameterDescriptor parameterDescriptor) {
        Intrinsics.checkNotNullParameter(parameterDescriptor, "<this>");
        if (parameterDescriptor instanceof ReceiverParameterDescriptor) {
            return -1;
        }
        if (parameterDescriptor instanceof ValueParameterDescriptor) {
            return ((ValueParameterDescriptor) parameterDescriptor).getIndex();
        }
        throw new IllegalStateException(("expected either receiver or value parameter, but got: " + parameterDescriptor).toString());
    }
}
