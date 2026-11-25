package org.jetbrains.kotlin.resolve;

import java.util.List;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.builtins.UnsignedTypes;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.name.FqNameUnsafe;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.KtPsiUtil;
import org.jetbrains.kotlin.psi.KtTypeReference;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.constants.BooleanValue;
import org.jetbrains.kotlin.resolve.constants.CompileTimeConstant;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.kotlin.resolve.constants.TypedCompileTimeConstant;
import org.jetbrains.kotlin.resolve.constants.evaluate.ConstantExpressionEvaluator;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.KotlinTypeKt;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.TypeUtils;

/* JADX INFO: loaded from: classes6.dex */
public class CompileTimeConstantUtils {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = "trace";
        } else if (i == 2) {
            objArr[0] = "parameterType";
        } else if (i == 3) {
            objArr[0] = "resolvedCall";
        } else if (i != 4) {
            objArr[0] = "parameters";
        } else {
            objArr[0] = "context";
        }
        objArr[1] = "org/jetbrains/kotlin/resolve/CompileTimeConstantUtils";
        if (i == 2) {
            objArr[2] = "isAcceptableTypeForAnnotationParameter";
        } else if (i == 3) {
            objArr[2] = "isArrayFunctionCall";
        } else if (i != 4) {
            objArr[2] = "checkConstructorParametersType";
        } else {
            objArr[2] = "canBeReducedToBooleanConstant";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public static void checkConstructorParametersType(List<KtParameter> list, BindingTrace bindingTrace) {
        if (list == null) {
            $$$reportNull$$$0(0);
        }
        if (bindingTrace == null) {
            $$$reportNull$$$0(1);
        }
        for (KtParameter ktParameter : list) {
            VariableDescriptor variableDescriptor = (VariableDescriptor) bindingTrace.getContext().get(BindingContext.VALUE_PARAMETER, ktParameter);
            if (variableDescriptor != null) {
                KotlinType type = variableDescriptor.getType();
                KtTypeReference ktTypeReferenceMo7396getTypeReference = ktParameter.mo7396getTypeReference();
                if (ktTypeReferenceMo7396getTypeReference != null) {
                    if (type.getIsMarkedNullable()) {
                        bindingTrace.report(Errors.NULLABLE_TYPE_OF_ANNOTATION_MEMBER.on(ktTypeReferenceMo7396getTypeReference));
                    } else if (!isAcceptableTypeForAnnotationParameter(type)) {
                        bindingTrace.report(Errors.INVALID_TYPE_OF_ANNOTATION_MEMBER.on(ktTypeReferenceMo7396getTypeReference));
                    }
                }
            }
        }
    }

    private static boolean isAcceptableTypeForAnnotationParameter(KotlinType kotlinType) {
        ClassDescriptor classDescriptor;
        if (kotlinType == null) {
            $$$reportNull$$$0(2);
        }
        if (KotlinTypeKt.isError(kotlinType)) {
            return true;
        }
        ClassDescriptor classDescriptor2 = TypeUtils.getClassDescriptor(kotlinType);
        if (classDescriptor2 == null) {
            return false;
        }
        if (DescriptorUtils.isEnumClass(classDescriptor2) || DescriptorUtils.isAnnotationClass(classDescriptor2) || KotlinBuiltIns.isKClass(classDescriptor2) || KotlinBuiltIns.isPrimitiveArray(kotlinType) || KotlinBuiltIns.isPrimitiveType(kotlinType) || KotlinBuiltIns.isString(kotlinType) || UnsignedTypes.isUnsignedType(kotlinType) || UnsignedTypes.isUnsignedArrayType(kotlinType)) {
            return true;
        }
        if (KotlinBuiltIns.isArray(kotlinType)) {
            List<TypeProjection> arguments = kotlinType.getArguments();
            if (arguments.size() == 1) {
                KotlinType nullableAnyType = arguments.get(0).getNullableAnyType();
                if (!nullableAnyType.getIsMarkedNullable() && (classDescriptor = TypeUtils.getClassDescriptor(nullableAnyType)) != null) {
                    return DescriptorUtils.isEnumClass(classDescriptor) || DescriptorUtils.isAnnotationClass(classDescriptor) || KotlinBuiltIns.isKClass(classDescriptor) || KotlinBuiltIns.isString(nullableAnyType);
                }
            }
        }
        return false;
    }

    public static boolean isArrayFunctionCall(ResolvedCall<?> resolvedCall) {
        if (resolvedCall == null) {
            $$$reportNull$$$0(3);
        }
        FqNameUnsafe fqName = DescriptorUtils.getFqName(resolvedCall.get$suspendPropertyDescriptor());
        if (fqName.isSafe()) {
            return ArrayFqNames.ARRAY_CALL_FQ_NAMES.contains(fqName.toSafe());
        }
        return false;
    }

    public static boolean canBeReducedToBooleanConstant(KtExpression ktExpression, BindingContext bindingContext, Boolean bool) {
        if (bindingContext == null) {
            $$$reportNull$$$0(4);
        }
        KtExpression ktExpressionDeparenthesize = KtPsiUtil.deparenthesize(ktExpression);
        if (ktExpressionDeparenthesize == null) {
            return false;
        }
        CompileTimeConstant<?> constant = ConstantExpressionEvaluator.getConstant(ktExpressionDeparenthesize, bindingContext);
        if (!(constant instanceof TypedCompileTimeConstant) || constant.getUsesVariableAsConstant()) {
            return false;
        }
        ConstantValue constantValue = ((TypedCompileTimeConstant) constant).getConstantValue();
        if (constantValue instanceof BooleanValue) {
            return bool == null || bool.equals(((BooleanValue) constantValue).getValue());
        }
        return false;
    }

    private CompileTimeConstantUtils() {
    }
}
