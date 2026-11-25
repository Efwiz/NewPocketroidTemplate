package org.jetbrains.kotlin.resolve.inline;

import java.util.Iterator;
import org.jetbrains.kotlin.builtins.FunctionTypesKt;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithVisibility;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ParameterDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyGetterDescriptor;
import org.jetbrains.kotlin.descriptors.PropertySetterDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.psi.KtCallableReferenceExpression;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtDeclarationWithBody;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtFunction;
import org.jetbrains.kotlin.psi.KtFunctionLiteral;
import org.jetbrains.kotlin.psi.KtLambdaExpression;
import org.jetbrains.kotlin.psi.KtNamedFunction;
import org.jetbrains.kotlin.psi.KtPsiUtil;
import org.jetbrains.kotlin.psi.ValueArgument;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.DescriptorToSourceUtils;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.calls.context.ResolutionContext;
import org.jetbrains.kotlin.resolve.calls.model.ArgumentMapping;
import org.jetbrains.kotlin.resolve.calls.model.ArgumentMatch;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;

/* JADX INFO: loaded from: classes6.dex */
public class InlineUtil {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 2:
                objArr[0] = "propertyDescriptor";
                break;
            case 3:
            case 13:
            case 14:
            case 16:
                objArr[0] = "descriptor";
                break;
            case 4:
            case 7:
                objArr[0] = "fromFunction";
                break;
            case 5:
                objArr[0] = "startExpression";
                break;
            case 6:
                objArr[0] = "context";
                break;
            case 8:
            case 10:
            case 12:
                objArr[0] = "bindingContext";
                break;
            case 9:
            case 11:
                objArr[0] = "argument";
                break;
            case 15:
                objArr[0] = "lambda";
                break;
            default:
                objArr[0] = "valueParameterOrReceiver";
                break;
        }
        objArr[1] = "org/jetbrains/kotlin/resolve/inline/InlineUtil";
        switch (i) {
            case 1:
                objArr[2] = "isInlineParameter";
                break;
            case 2:
                objArr[2] = "hasInlineAccessors";
                break;
            case 3:
                objArr[2] = "isPropertyWithAllAccessorsAreInline";
                break;
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                objArr[2] = "checkNonLocalReturnUsage";
                break;
            case 9:
            case 10:
                objArr[2] = "isInlinedArgument";
                break;
            case 11:
            case 12:
                objArr[2] = "getInlineArgumentDescriptor";
                break;
            case 13:
                objArr[2] = "isArrayConstructorWithLambda";
                break;
            case 14:
                objArr[2] = "getContainingClassOrFunctionDescriptor";
                break;
            case 15:
                objArr[2] = "allowsNonLocalReturns";
                break;
            case 16:
                objArr[2] = "containsReifiedTypeParameters";
                break;
            default:
                objArr[2] = "isInlineParameterExceptNullability";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public static boolean isInlineParameterExceptNullability(ParameterDescriptor parameterDescriptor) {
        if (parameterDescriptor == null) {
            $$$reportNull$$$0(0);
        }
        return !((parameterDescriptor instanceof ValueParameterDescriptor) && ((ValueParameterDescriptor) parameterDescriptor).getIsNoinline()) && FunctionTypesKt.isBuiltinFunctionalType(parameterDescriptor.getOriginal().getType());
    }

    public static boolean isInlineParameter(ParameterDescriptor parameterDescriptor) {
        if (parameterDescriptor == null) {
            $$$reportNull$$$0(1);
        }
        return isInlineParameterExceptNullability(parameterDescriptor) && !parameterDescriptor.getOriginal().getType().getIsMarkedNullable();
    }

    public static boolean isInline(DeclarationDescriptor declarationDescriptor) {
        return (declarationDescriptor instanceof FunctionDescriptor) && ((FunctionDescriptor) declarationDescriptor).isInline();
    }

    public static boolean hasInlineAccessors(PropertyDescriptor propertyDescriptor) {
        if (propertyDescriptor == null) {
            $$$reportNull$$$0(2);
        }
        PropertyGetterDescriptor getter = propertyDescriptor.getGetter();
        PropertySetterDescriptor setter = propertyDescriptor.getSetter();
        return (getter != null && getter.isInline()) || (setter != null && setter.isInline());
    }

    public static boolean isPropertyWithAllAccessorsAreInline(DeclarationDescriptor declarationDescriptor) {
        PropertyDescriptor propertyDescriptor;
        PropertyGetterDescriptor getter;
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(3);
        }
        if (!(declarationDescriptor instanceof PropertyDescriptor) || (getter = (propertyDescriptor = (PropertyDescriptor) declarationDescriptor).getGetter()) == null || !getter.isInline()) {
            return false;
        }
        if (!propertyDescriptor.isVar()) {
            return true;
        }
        PropertySetterDescriptor setter = propertyDescriptor.getSetter();
        return setter != null && setter.isInline();
    }

    public static boolean isInlineOrContainingInline(DeclarationDescriptor declarationDescriptor) {
        if (isInline(declarationDescriptor)) {
            return true;
        }
        if (declarationDescriptor == null) {
            return false;
        }
        return isInlineOrContainingInline(declarationDescriptor.getContainingDeclaration());
    }

    public static boolean isInPublicInlineScope(DeclarationDescriptor declarationDescriptor) {
        ClassDescriptor containingClass;
        if (declarationDescriptor == null) {
            return false;
        }
        if (!isInline(declarationDescriptor) || !(declarationDescriptor instanceof DeclarationDescriptorWithVisibility) || DescriptorVisibilities.isPrivate(((DeclarationDescriptorWithVisibility) declarationDescriptor).getVisibility()) || ((containingClass = DescriptorUtils.getContainingClass(declarationDescriptor)) != null && DescriptorVisibilities.isPrivate(containingClass.getVisibility()))) {
            return isInPublicInlineScope(declarationDescriptor.getContainingDeclaration());
        }
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static boolean checkNonLocalReturnUsage(DeclarationDescriptor declarationDescriptor, KtExpression ktExpression, ResolutionContext<?> resolutionContext) {
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(4);
        }
        if (ktExpression == null) {
            $$$reportNull$$$0(5);
        }
        if (resolutionContext == null) {
            $$$reportNull$$$0(6);
        }
        PsiElement contextParentOfType = resolutionContext.getContextParentOfType(ktExpression, KtClassOrObject.class, KtDeclarationWithBody.class);
        if (contextParentOfType == null) {
            return false;
        }
        return checkNonLocalReturnUsage(declarationDescriptor, (DeclarationDescriptor) resolutionContext.trace.get(BindingContext.DECLARATION_TO_DESCRIPTOR, contextParentOfType), contextParentOfType, resolutionContext.trace.getContext());
    }

    public static boolean checkNonLocalReturnUsage(DeclarationDescriptor declarationDescriptor, DeclarationDescriptor declarationDescriptor2, PsiElement psiElement, BindingContext bindingContext) {
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(7);
        }
        if (bindingContext == null) {
            $$$reportNull$$$0(8);
        }
        if (declarationDescriptor2 == null) {
            return false;
        }
        while (canBeInlineArgument(psiElement) && declarationDescriptor != declarationDescriptor2) {
            Boolean bool = (Boolean) bindingContext.get(BindingContext.NEW_INFERENCE_IS_LAMBDA_FOR_OVERLOAD_RESOLUTION_INLINE, psiElement);
            if (bool != null) {
                return bool.booleanValue();
            }
            if (!isInlinedArgument((KtFunction) psiElement, bindingContext, true)) {
                return false;
            }
            declarationDescriptor2 = getContainingClassOrFunctionDescriptor(declarationDescriptor2, true);
            psiElement = declarationDescriptor2 != null ? DescriptorToSourceUtils.descriptorToDeclaration(declarationDescriptor2) : null;
        }
        return declarationDescriptor == declarationDescriptor2;
    }

    public static boolean isInlinedArgument(KtFunction ktFunction, BindingContext bindingContext, boolean z) {
        if (ktFunction == null) {
            $$$reportNull$$$0(9);
        }
        if (bindingContext == null) {
            $$$reportNull$$$0(10);
        }
        ValueParameterDescriptor inlineArgumentDescriptor = getInlineArgumentDescriptor(ktFunction, bindingContext);
        if (inlineArgumentDescriptor != null) {
            return !z || allowsNonLocalReturns(inlineArgumentDescriptor);
        }
        return false;
    }

    public static ValueParameterDescriptor getInlineArgumentDescriptor(KtFunction ktFunction, BindingContext bindingContext) {
        KtExpression parentCallIfPresent;
        ResolvedCall<? extends CallableDescriptor> resolvedCall;
        ValueArgument valueArgumentForExpression;
        if (ktFunction == null) {
            $$$reportNull$$$0(11);
        }
        if (bindingContext == null) {
            $$$reportNull$$$0(12);
        }
        if (!canBeInlineArgument(ktFunction) || (parentCallIfPresent = KtPsiUtil.getParentCallIfPresent(ktFunction)) == null || (resolvedCall = CallUtilKt.getResolvedCall(parentCallIfPresent, bindingContext)) == null) {
            return null;
        }
        CallableDescriptor resultingDescriptor = resolvedCall.getResultingDescriptor();
        if ((!isInline(resultingDescriptor) && !isArrayConstructorWithLambda(resultingDescriptor)) || (valueArgumentForExpression = CallUtilKt.getValueArgumentForExpression(resolvedCall.getCall(), ktFunction)) == null) {
            return null;
        }
        ArgumentMapping argumentMapping = resolvedCall.getArgumentMapping(valueArgumentForExpression);
        if (!(argumentMapping instanceof ArgumentMatch)) {
            return null;
        }
        ValueParameterDescriptor valueParameter = ((ArgumentMatch) argumentMapping).getValueParameter();
        if (isInlineParameter(valueParameter)) {
            return valueParameter;
        }
        return null;
    }

    public static boolean canBeInlineArgument(PsiElement psiElement) {
        return (psiElement instanceof KtFunctionLiteral) || (psiElement instanceof KtNamedFunction);
    }

    public static boolean isArrayConstructorWithLambda(CallableDescriptor callableDescriptor) {
        if (callableDescriptor == null) {
            $$$reportNull$$$0(13);
        }
        return callableDescriptor.getValueParameters().size() == 2 && (callableDescriptor instanceof ConstructorDescriptor) && KotlinBuiltIns.isArrayOrPrimitiveArray(((ConstructorDescriptor) callableDescriptor).getConstructedClass());
    }

    public static DeclarationDescriptor getContainingClassOrFunctionDescriptor(DeclarationDescriptor declarationDescriptor, boolean z) {
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(14);
        }
        if (z) {
            declarationDescriptor = declarationDescriptor.getContainingDeclaration();
        }
        while (declarationDescriptor != null) {
            if ((declarationDescriptor instanceof FunctionDescriptor) || (declarationDescriptor instanceof ClassDescriptor)) {
                return declarationDescriptor;
            }
            declarationDescriptor = declarationDescriptor.getContainingDeclaration();
        }
        return null;
    }

    public static boolean allowsNonLocalReturns(CallableDescriptor callableDescriptor) {
        if (callableDescriptor == null) {
            $$$reportNull$$$0(15);
        }
        return ((callableDescriptor instanceof ValueParameterDescriptor) && ((ValueParameterDescriptor) callableDescriptor).getIsCrossinline()) ? false : true;
    }

    public static boolean containsReifiedTypeParameters(CallableDescriptor callableDescriptor) {
        if (callableDescriptor == null) {
            $$$reportNull$$$0(16);
        }
        Iterator<TypeParameterDescriptor> it = callableDescriptor.getTypeParameters().iterator();
        while (it.hasNext()) {
            if (it.next().isReified()) {
                return true;
            }
        }
        return false;
    }

    public static boolean isInlinableParameterExpression(KtExpression ktExpression) {
        return (ktExpression instanceof KtLambdaExpression) || (ktExpression instanceof KtNamedFunction) || (ktExpression instanceof KtCallableReferenceExpression);
    }
}
