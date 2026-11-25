package org.jetbrains.kotlin.js.translate.utils;

import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.psi.KtArrayAccessExpression;
import org.jetbrains.kotlin.psi.KtDeclarationWithBody;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtOperationExpression;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtPureClassOrObject;
import org.jetbrains.kotlin.psi.KtReferenceExpression;
import org.jetbrains.kotlin.psi.KtSuperTypeCallEntry;
import org.jetbrains.kotlin.psi.KtSuperTypeListEntry;
import org.jetbrains.kotlin.psi.KtTypeReference;
import org.jetbrains.kotlin.psi.synthetics.SyntheticClassOrObjectDescriptorKt;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingContextUtils;
import org.jetbrains.kotlin.resolve.DescriptorToSourceUtils;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.resolve.calls.util.FakeCallableDescriptorForTypeAliasObject;
import org.jetbrains.kotlin.resolve.constants.CompileTimeConstant;
import org.jetbrains.kotlin.resolve.constants.evaluate.ConstantExpressionEvaluator;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeUtils;

/* JADX INFO: loaded from: classes5.dex */
public final class BindingUtils {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 2:
            case 5:
            case 11:
            case 16:
            case 24:
            case 25:
            case 26:
            case 33:
            case 40:
            case 43:
            case 46:
            case 49:
            case 52:
            case 55:
                str = "@NotNull method %s.%s must not return null";
                break;
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 2:
            case 5:
            case 11:
            case 16:
            case 24:
            case 25:
            case 26:
            case 33:
            case 40:
            case 43:
            case 46:
            case 49:
            case 52:
            case 55:
                i2 = 2;
                break;
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 28:
            case 30:
            case 35:
            case 37:
            case 51:
                objArr[0] = "expression";
                break;
            case 2:
            case 5:
            case 11:
            case 16:
            case 24:
            case 25:
            case 26:
            case 33:
            case 40:
            case 43:
            case 46:
            case 49:
            case 52:
            case 55:
                objArr[0] = "org/jetbrains/kotlin/js/translate/utils/BindingUtils";
                break;
            case 3:
            case 6:
            case 8:
            case 12:
            case 14:
            case 17:
            case 19:
            case 21:
            case 27:
            case 29:
            case 31:
            case 34:
            case 36:
            case 41:
            case 44:
            case 47:
            case 50:
            case 53:
            default:
                objArr[0] = "context";
                break;
            case 4:
            case 7:
            case 9:
                objArr[0] = "declaration";
                break;
            case 10:
            case 23:
                objArr[0] = "descriptor";
                break;
            case 13:
                objArr[0] = "classDeclaration";
                break;
            case 15:
                objArr[0] = "typeReference";
                break;
            case 18:
                objArr[0] = "parameter";
                break;
            case 20:
            case 22:
                objArr[0] = "reference";
                break;
            case 32:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 38:
                objArr[0] = "constant";
                break;
            case 39:
                objArr[0] = "parameterDescriptor";
                break;
            case 42:
            case 45:
            case 48:
                objArr[0] = "rangeExpression";
                break;
            case 54:
                objArr[0] = "arrayAccessExpression";
                break;
        }
        switch (i) {
            case 2:
                objArr[1] = "getDescriptorForExpression";
                break;
            case 5:
                objArr[1] = "getClassDescriptor";
                break;
            case 11:
                objArr[1] = "getParameterForDescriptor";
                break;
            case 16:
                objArr[1] = "getTypeByReference";
                break;
            case 24:
            case 25:
            case 26:
                objArr[1] = "resolveObjectViaTypeAlias";
                break;
            case 33:
                objArr[1] = "getDescriptorForElement";
                break;
            case 40:
                objArr[1] = "getDefaultArgument";
                break;
            case 43:
                objArr[1] = "getIteratorFunction";
                break;
            case 46:
                objArr[1] = "getNextFunction";
                break;
            case 49:
                objArr[1] = "getHasNextCallable";
                break;
            case 52:
                objArr[1] = "getTypeForExpression";
                break;
            case 55:
                objArr[1] = "getResolvedCallForArrayAccess";
                break;
            default:
                objArr[1] = "org/jetbrains/kotlin/js/translate/utils/BindingUtils";
                break;
        }
        switch (i) {
            case 2:
            case 5:
            case 11:
            case 16:
            case 24:
            case 25:
            case 26:
            case 33:
            case 40:
            case 43:
            case 46:
            case 49:
            case 52:
            case 55:
                break;
            case 3:
            case 4:
                objArr[2] = "getClassDescriptor";
                break;
            case 6:
            case 7:
                objArr[2] = "getFunctionDescriptor";
                break;
            case 8:
            case 9:
                objArr[2] = "getPropertyDescriptor";
                break;
            case 10:
                objArr[2] = "getParameterForDescriptor";
                break;
            case 12:
            case 13:
                objArr[2] = "hasAncestorClass";
                break;
            case 14:
            case 15:
                objArr[2] = "getTypeByReference";
                break;
            case 17:
            case 18:
                objArr[2] = "getPropertyDescriptorForConstructorParameter";
                break;
            case 19:
            case 20:
                objArr[2] = "getDescriptorForReferenceExpression";
                break;
            case 21:
            case 22:
                objArr[2] = "getNullableDescriptorForReferenceExpression";
                break;
            case 23:
                objArr[2] = "resolveObjectViaTypeAlias";
                break;
            case 27:
            case 28:
                objArr[2] = "isVariableReassignment";
                break;
            case 29:
            case 30:
                objArr[2] = "getCallableDescriptorForOperationExpression";
                break;
            case 31:
            case 32:
                objArr[2] = "getDescriptorForElement";
                break;
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
                objArr[2] = "getCompileTimeValue";
                break;
            case 39:
                objArr[2] = "getDefaultArgument";
                break;
            case 41:
            case 42:
                objArr[2] = "getIteratorFunction";
                break;
            case 44:
            case 45:
                objArr[2] = "getNextFunction";
                break;
            case 47:
            case 48:
                objArr[2] = "getHasNextCallable";
                break;
            case 50:
            case 51:
                objArr[2] = "getTypeForExpression";
                break;
            case 53:
            case 54:
                objArr[2] = "getResolvedCallForArrayAccess";
                break;
            case 56:
                objArr[2] = "getSuperCall";
                break;
            default:
                objArr[2] = "getDescriptorForExpression";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 2:
            case 5:
            case 11:
            case 16:
            case 24:
            case 25:
            case 26:
            case 33:
            case 40:
            case 43:
            case 46:
            case 49:
            case 52:
            case 55:
                throw new IllegalStateException(str2);
            default:
                throw new IllegalArgumentException(str2);
        }
    }

    private BindingUtils() {
    }

    private static <E extends PsiElement, D extends DeclarationDescriptor> D getDescriptorForExpression(BindingContext bindingContext, E e, Class<D> cls) {
        if (bindingContext == null) {
            $$$reportNull$$$0(0);
        }
        if (e == null) {
            $$$reportNull$$$0(1);
        }
        D d = (D) bindingContext.get(BindingContext.DECLARATION_TO_DESCRIPTOR, e);
        if (d == null) {
            $$$reportNull$$$0(2);
        }
        return d;
    }

    public static ClassDescriptor getClassDescriptor(BindingContext bindingContext, KtPureClassOrObject ktPureClassOrObject) {
        if (bindingContext == null) {
            $$$reportNull$$$0(3);
        }
        if (ktPureClassOrObject == null) {
            $$$reportNull$$$0(4);
        }
        ClassDescriptor classDescriptorFindClassDescriptor = SyntheticClassOrObjectDescriptorKt.findClassDescriptor(ktPureClassOrObject, bindingContext);
        if (classDescriptorFindClassDescriptor == null) {
            $$$reportNull$$$0(5);
        }
        return classDescriptorFindClassDescriptor;
    }

    public static FunctionDescriptor getFunctionDescriptor(BindingContext bindingContext, KtDeclarationWithBody ktDeclarationWithBody) {
        if (bindingContext == null) {
            $$$reportNull$$$0(6);
        }
        if (ktDeclarationWithBody == null) {
            $$$reportNull$$$0(7);
        }
        return (FunctionDescriptor) getDescriptorForExpression(bindingContext, ktDeclarationWithBody, FunctionDescriptor.class);
    }

    public static PropertyDescriptor getPropertyDescriptor(BindingContext bindingContext, KtProperty ktProperty) {
        if (bindingContext == null) {
            $$$reportNull$$$0(8);
        }
        if (ktProperty == null) {
            $$$reportNull$$$0(9);
        }
        return (PropertyDescriptor) getDescriptorForExpression(bindingContext, ktProperty, PropertyDescriptor.class);
    }

    private static KtParameter getParameterForDescriptor(ValueParameterDescriptor valueParameterDescriptor) {
        if (valueParameterDescriptor == null) {
            $$$reportNull$$$0(10);
        }
        KtParameter ktParameter = (KtParameter) DescriptorToSourceUtils.descriptorToDeclaration(valueParameterDescriptor);
        if (ktParameter == null) {
            $$$reportNull$$$0(11);
        }
        return ktParameter;
    }

    public static boolean hasAncestorClass(BindingContext bindingContext, KtPureClassOrObject ktPureClassOrObject) {
        if (bindingContext == null) {
            $$$reportNull$$$0(12);
        }
        if (ktPureClassOrObject == null) {
            $$$reportNull$$$0(13);
        }
        return JsDescriptorUtils.findAncestorClass(DescriptorUtils.getSuperclassDescriptors(getClassDescriptor(bindingContext, ktPureClassOrObject))) != null;
    }

    public static KotlinType getTypeByReference(BindingContext bindingContext, KtTypeReference ktTypeReference) {
        if (bindingContext == null) {
            $$$reportNull$$$0(14);
        }
        if (ktTypeReference == null) {
            $$$reportNull$$$0(15);
        }
        KotlinType kotlinType = (KotlinType) BindingContextUtils.getNotNull(bindingContext, BindingContext.TYPE, ktTypeReference);
        if (kotlinType == null) {
            $$$reportNull$$$0(16);
        }
        return kotlinType;
    }

    public static PropertyDescriptor getPropertyDescriptorForConstructorParameter(BindingContext bindingContext, KtParameter ktParameter) {
        if (bindingContext == null) {
            $$$reportNull$$$0(17);
        }
        if (ktParameter == null) {
            $$$reportNull$$$0(18);
        }
        return (PropertyDescriptor) bindingContext.get(BindingContext.PRIMARY_CONSTRUCTOR_PARAMETER, ktParameter);
    }

    public static DeclarationDescriptor getDescriptorForReferenceExpression(BindingContext bindingContext, KtReferenceExpression ktReferenceExpression) {
        if (bindingContext == null) {
            $$$reportNull$$$0(19);
        }
        if (ktReferenceExpression == null) {
            $$$reportNull$$$0(20);
        }
        if (BindingContextUtils.isExpressionWithValidReference(ktReferenceExpression, bindingContext)) {
            return resolveObjectViaTypeAlias((DeclarationDescriptor) BindingContextUtils.getNotNull(bindingContext, BindingContext.REFERENCE_TARGET, ktReferenceExpression));
        }
        return null;
    }

    private static DeclarationDescriptor getNullableDescriptorForReferenceExpression(BindingContext bindingContext, KtReferenceExpression ktReferenceExpression) {
        if (bindingContext == null) {
            $$$reportNull$$$0(21);
        }
        if (ktReferenceExpression == null) {
            $$$reportNull$$$0(22);
        }
        DeclarationDescriptor declarationDescriptor = (DeclarationDescriptor) bindingContext.get(BindingContext.REFERENCE_TARGET, ktReferenceExpression);
        if (declarationDescriptor != null) {
            return resolveObjectViaTypeAlias(declarationDescriptor);
        }
        return null;
    }

    private static DeclarationDescriptor resolveObjectViaTypeAlias(DeclarationDescriptor declarationDescriptor) {
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(23);
        }
        if (declarationDescriptor instanceof TypeAliasDescriptor) {
            ClassDescriptor classDescriptor = ((TypeAliasDescriptor) declarationDescriptor).getClassDescriptor();
            if (classDescriptor.getKind() != ClassKind.OBJECT && classDescriptor.getKind() != ClassKind.ENUM_CLASS) {
                classDescriptor = classDescriptor.mo6346getCompanionObjectDescriptor();
            }
            if (classDescriptor == null) {
                $$$reportNull$$$0(24);
            }
            return classDescriptor;
        }
        if (!(declarationDescriptor instanceof FakeCallableDescriptorForTypeAliasObject)) {
            if (declarationDescriptor == null) {
                $$$reportNull$$$0(26);
            }
            return declarationDescriptor;
        }
        ClassDescriptor referencedObject = ((FakeCallableDescriptorForTypeAliasObject) declarationDescriptor).getReferencedObject();
        if (referencedObject == null) {
            $$$reportNull$$$0(25);
        }
        return referencedObject;
    }

    public static boolean isVariableReassignment(BindingContext bindingContext, KtExpression ktExpression) {
        if (bindingContext == null) {
            $$$reportNull$$$0(27);
        }
        if (ktExpression == null) {
            $$$reportNull$$$0(28);
        }
        Boolean bool = (Boolean) bindingContext.get(BindingContext.VARIABLE_REASSIGNMENT, ktExpression);
        if (bool != null) {
            return bool.booleanValue();
        }
        return false;
    }

    public static CallableDescriptor getCallableDescriptorForOperationExpression(BindingContext bindingContext, KtOperationExpression ktOperationExpression) {
        if (bindingContext == null) {
            $$$reportNull$$$0(29);
        }
        if (ktOperationExpression == null) {
            $$$reportNull$$$0(30);
        }
        DeclarationDescriptor nullableDescriptorForReferenceExpression = getNullableDescriptorForReferenceExpression(bindingContext, ktOperationExpression.getOperationReference());
        if (nullableDescriptorForReferenceExpression == null) {
            return null;
        }
        return (CallableDescriptor) nullableDescriptorForReferenceExpression;
    }

    public static DeclarationDescriptor getDescriptorForElement(BindingContext bindingContext, PsiElement psiElement) {
        if (bindingContext == null) {
            $$$reportNull$$$0(31);
        }
        if (psiElement == null) {
            $$$reportNull$$$0(32);
        }
        DeclarationDescriptor declarationDescriptor = (DeclarationDescriptor) BindingContextUtils.getNotNull(bindingContext, BindingContext.DECLARATION_TO_DESCRIPTOR, psiElement);
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(33);
        }
        return declarationDescriptor;
    }

    public static Object getCompileTimeValue(BindingContext bindingContext, KtExpression ktExpression) {
        if (bindingContext == null) {
            $$$reportNull$$$0(34);
        }
        if (ktExpression == null) {
            $$$reportNull$$$0(35);
        }
        CompileTimeConstant<?> constant = ConstantExpressionEvaluator.getConstant(ktExpression, bindingContext);
        if (constant != null) {
            return getCompileTimeValue(bindingContext, ktExpression, constant);
        }
        return null;
    }

    public static Object getCompileTimeValue(BindingContext bindingContext, KtExpression ktExpression, CompileTimeConstant<?> compileTimeConstant) {
        if (bindingContext == null) {
            $$$reportNull$$$0(36);
        }
        if (ktExpression == null) {
            $$$reportNull$$$0(37);
        }
        if (compileTimeConstant == null) {
            $$$reportNull$$$0(38);
        }
        KotlinType type = bindingContext.getType(ktExpression);
        if (type == null) {
            type = TypeUtils.NO_EXPECTED_TYPE;
        }
        return compileTimeConstant.getValue(type);
    }

    public static KtExpression getDefaultArgument(ValueParameterDescriptor valueParameterDescriptor) {
        if (valueParameterDescriptor == null) {
            $$$reportNull$$$0(39);
        }
        KtExpression defaultValue = getParameterForDescriptor(valueParameterDescriptor).getDefaultValue();
        if (defaultValue == null) {
            $$$reportNull$$$0(40);
        }
        return defaultValue;
    }

    public static ResolvedCall<FunctionDescriptor> getIteratorFunction(BindingContext bindingContext, KtExpression ktExpression) {
        if (bindingContext == null) {
            $$$reportNull$$$0(41);
        }
        if (ktExpression == null) {
            $$$reportNull$$$0(42);
        }
        ResolvedCall<FunctionDescriptor> resolvedCall = (ResolvedCall) BindingContextUtils.getNotNull(bindingContext, BindingContext.LOOP_RANGE_ITERATOR_RESOLVED_CALL, ktExpression);
        if (resolvedCall == null) {
            $$$reportNull$$$0(43);
        }
        return resolvedCall;
    }

    public static ResolvedCall<FunctionDescriptor> getNextFunction(BindingContext bindingContext, KtExpression ktExpression) {
        if (bindingContext == null) {
            $$$reportNull$$$0(44);
        }
        if (ktExpression == null) {
            $$$reportNull$$$0(45);
        }
        ResolvedCall<FunctionDescriptor> resolvedCall = (ResolvedCall) BindingContextUtils.getNotNull(bindingContext, BindingContext.LOOP_RANGE_NEXT_RESOLVED_CALL, ktExpression);
        if (resolvedCall == null) {
            $$$reportNull$$$0(46);
        }
        return resolvedCall;
    }

    public static ResolvedCall<FunctionDescriptor> getHasNextCallable(BindingContext bindingContext, KtExpression ktExpression) {
        if (bindingContext == null) {
            $$$reportNull$$$0(47);
        }
        if (ktExpression == null) {
            $$$reportNull$$$0(48);
        }
        ResolvedCall<FunctionDescriptor> resolvedCall = (ResolvedCall) BindingContextUtils.getNotNull(bindingContext, BindingContext.LOOP_RANGE_HAS_NEXT_RESOLVED_CALL, ktExpression);
        if (resolvedCall == null) {
            $$$reportNull$$$0(49);
        }
        return resolvedCall;
    }

    public static KotlinType getTypeForExpression(BindingContext bindingContext, KtExpression ktExpression) {
        if (bindingContext == null) {
            $$$reportNull$$$0(50);
        }
        if (ktExpression == null) {
            $$$reportNull$$$0(51);
        }
        KotlinType typeNotNull = BindingContextUtils.getTypeNotNull(bindingContext, ktExpression);
        if (typeNotNull == null) {
            $$$reportNull$$$0(52);
        }
        return typeNotNull;
    }

    public static ResolvedCall<FunctionDescriptor> getResolvedCallForArrayAccess(BindingContext bindingContext, KtArrayAccessExpression ktArrayAccessExpression, boolean z) {
        if (bindingContext == null) {
            $$$reportNull$$$0(53);
        }
        if (ktArrayAccessExpression == null) {
            $$$reportNull$$$0(54);
        }
        ResolvedCall<FunctionDescriptor> resolvedCall = (ResolvedCall) BindingContextUtils.getNotNull(bindingContext, z ? BindingContext.INDEXED_LVALUE_GET : BindingContext.INDEXED_LVALUE_SET, ktArrayAccessExpression);
        if (resolvedCall == null) {
            $$$reportNull$$$0(55);
        }
        return resolvedCall;
    }

    public static ResolvedCall<FunctionDescriptor> getSuperCall(BindingContext bindingContext, KtPureClassOrObject ktPureClassOrObject) {
        if (bindingContext == null) {
            $$$reportNull$$$0(56);
        }
        for (KtSuperTypeListEntry ktSuperTypeListEntry : ktPureClassOrObject.getSuperTypeListEntries()) {
            if (ktSuperTypeListEntry instanceof KtSuperTypeCallEntry) {
                return CallUtilKt.getResolvedCallWithAssert((KtSuperTypeCallEntry) ktSuperTypeListEntry, bindingContext);
            }
        }
        return null;
    }
}
