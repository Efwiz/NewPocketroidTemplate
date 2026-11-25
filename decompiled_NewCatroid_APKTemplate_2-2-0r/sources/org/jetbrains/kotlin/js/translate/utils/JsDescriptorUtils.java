package org.jetbrains.kotlin.js.translate.utils;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.com.intellij.openapi.util.Condition;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.ModalityUtilsKt;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyAccessorDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.PropertySetterDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.calls.tasks.DynamicCallsKt;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.scopes.receivers.ImplicitReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;
import org.jetbrains.kotlin.util.OperatorNameConventions;

/* JADX INFO: loaded from: classes5.dex */
public final class JsDescriptorUtils {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final Set<String> FAKE_CLASSES = ContainerUtil.immutableSet(StandardNames.FqNames.any.asString());

    /* JADX WARN: Code duplicated, block: B:10:0x0017 A[FALL_THROUGH] */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 5 && i != 7 && i != 17 && i != 18) {
            switch (i) {
                default:
                    switch (i) {
                        case 20:
                        case 21:
                        case 22:
                            break;
                        default:
                            str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                            break;
                    }
                case 10:
                case 11:
                case 12:
                    str = "@NotNull method %s.%s must not return null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 5 && i != 7 && i != 17 && i != 18) {
            switch (i) {
                case 10:
                case 11:
                case 12:
                    i2 = 2;
                    break;
                default:
                    switch (i) {
                        case 20:
                        case 21:
                        case 22:
                            i2 = 2;
                            break;
                        default:
                            i2 = 3;
                            break;
                    }
                    break;
            }
        } else {
            i2 = 2;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 2:
            case 6:
            case 15:
            case 19:
            case 23:
            case 24:
            case 25:
                objArr[0] = "descriptor";
                break;
            case 3:
                objArr[0] = "superclassDescriptors";
                break;
            case 4:
                objArr[0] = "classDescriptor";
                break;
            case 5:
            case 7:
            case 10:
            case 11:
            case 12:
            case 17:
            case 18:
            case 20:
            case 21:
            case 22:
                objArr[0] = "org/jetbrains/kotlin/js/translate/utils/JsDescriptorUtils";
                break;
            case 8:
            case 9:
                objArr[0] = "receiverParameter";
                break;
            case 13:
                objArr[0] = "property";
                break;
            case 14:
                objArr[0] = "propertyDescriptor";
                break;
            case 16:
                objArr[0] = "module";
                break;
            default:
                objArr[0] = "functionDescriptor";
                break;
        }
        if (i == 5) {
            objArr[1] = "getSupertypesWithoutFakes";
        } else if (i == 7) {
            objArr[1] = "getContainingDeclaration";
        } else if (i != 17 && i != 18) {
            switch (i) {
                case 10:
                    objArr[1] = "getDeclarationDescriptorForReceiver";
                    break;
                case 11:
                case 12:
                    objArr[1] = "getReceiverParameterForDeclaration";
                    break;
                default:
                    switch (i) {
                        case 20:
                        case 21:
                        case 22:
                            objArr[1] = "findRealInlineDeclaration";
                            break;
                        default:
                            objArr[1] = "org/jetbrains/kotlin/js/translate/utils/JsDescriptorUtils";
                            break;
                    }
                    break;
            }
        } else {
            objArr[1] = "getModuleName";
        }
        switch (i) {
            case 1:
                objArr[2] = "hasParameters";
                break;
            case 2:
                objArr[2] = "isCompareTo";
                break;
            case 3:
                objArr[2] = "findAncestorClass";
                break;
            case 4:
                objArr[2] = "getSuperclass";
                break;
            case 5:
            case 7:
            case 10:
            case 11:
            case 12:
            case 17:
            case 18:
            case 20:
            case 21:
            case 22:
                break;
            case 6:
                objArr[2] = "getContainingDeclaration";
                break;
            case 8:
                objArr[2] = "getReceiverParameterForReceiver";
                break;
            case 9:
                objArr[2] = "getDeclarationDescriptorForReceiver";
                break;
            case 13:
                objArr[2] = "sideEffectsPossibleOnRead";
                break;
            case 14:
                objArr[2] = "isSimpleFinalProperty";
                break;
            case 15:
            case 16:
                objArr[2] = "getModuleName";
                break;
            case 19:
                objArr[2] = "findRealInlineDeclaration";
                break;
            case 23:
                objArr[2] = "findRealDeclaration";
                break;
            case 24:
                objArr[2] = "isImmediateSubtypeOfError";
                break;
            case 25:
                objArr[2] = "isExceptionClass";
                break;
            default:
                objArr[2] = "valueParametersCount";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 5 && i != 7 && i != 17 && i != 18) {
            switch (i) {
                case 10:
                case 11:
                case 12:
                    break;
                default:
                    switch (i) {
                        case 20:
                        case 21:
                        case 22:
                            break;
                        default:
                            throw new IllegalArgumentException(str2);
                    }
                    break;
            }
        }
        throw new IllegalStateException(str2);
    }

    private JsDescriptorUtils() {
    }

    private static int valueParametersCount(FunctionDescriptor functionDescriptor) {
        if (functionDescriptor == null) {
            $$$reportNull$$$0(0);
        }
        return functionDescriptor.getValueParameters().size();
    }

    public static boolean hasParameters(FunctionDescriptor functionDescriptor) {
        if (functionDescriptor == null) {
            $$$reportNull$$$0(1);
        }
        return valueParametersCount(functionDescriptor) > 0;
    }

    public static boolean isCompareTo(CallableDescriptor callableDescriptor) {
        if (callableDescriptor == null) {
            $$$reportNull$$$0(2);
        }
        return callableDescriptor.getShortName().equals(OperatorNameConventions.COMPARE_TO);
    }

    public static ClassDescriptor findAncestorClass(List<ClassDescriptor> list) {
        if (list == null) {
            $$$reportNull$$$0(3);
        }
        for (ClassDescriptor classDescriptor : list) {
            if (classDescriptor.getKind() == ClassKind.CLASS || classDescriptor.getKind() == ClassKind.ENUM_CLASS) {
                return classDescriptor;
            }
        }
        return null;
    }

    public static ClassDescriptor getSuperclass(ClassDescriptor classDescriptor) {
        if (classDescriptor == null) {
            $$$reportNull$$$0(4);
        }
        return findAncestorClass(DescriptorUtils.getSuperclassDescriptors(classDescriptor));
    }

    public static List<KotlinType> getSupertypesWithoutFakes(ClassDescriptor classDescriptor) {
        List<KotlinType> listFilter = ContainerUtil.filter(classDescriptor.getTypeConstructor().mo7438getSupertypes(), new Condition() { // from class: org.jetbrains.kotlin.js.translate.utils.JsDescriptorUtils$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Condition
            public final boolean value(Object obj) {
                return JsDescriptorUtils.lambda$getSupertypesWithoutFakes$0((KotlinType) obj);
            }
        });
        if (listFilter == null) {
            $$$reportNull$$$0(5);
        }
        return listFilter;
    }

    static /* synthetic */ boolean lambda$getSupertypesWithoutFakes$0(KotlinType kotlinType) {
        ClassDescriptor classDescriptorForType = DescriptorUtils.getClassDescriptorForType(kotlinType);
        return (FAKE_CLASSES.contains(DescriptorUtils.getFqNameSafe(classDescriptorForType).asString()) || (classDescriptorForType.getKind() == ClassKind.INTERFACE && AnnotationsUtils.isNativeObject(classDescriptorForType))) ? false : true;
    }

    public static DeclarationDescriptor getContainingDeclaration(DeclarationDescriptor declarationDescriptor) {
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(6);
        }
        DeclarationDescriptor containingDeclaration = declarationDescriptor.getContainingDeclaration();
        if (containingDeclaration == null) {
            $$$reportNull$$$0(7);
        }
        return containingDeclaration;
    }

    public static ReceiverParameterDescriptor getReceiverParameterForReceiver(ReceiverValue receiverValue) {
        if (receiverValue == null) {
            $$$reportNull$$$0(8);
        }
        return getReceiverParameterForDeclaration(getDeclarationDescriptorForReceiver(receiverValue));
    }

    private static DeclarationDescriptor getDeclarationDescriptorForReceiver(ReceiverValue receiverValue) {
        if (receiverValue == null) {
            $$$reportNull$$$0(9);
        }
        if (receiverValue instanceof ImplicitReceiver) {
            DeclarationDescriptor original = ((ImplicitReceiver) receiverValue).getDeclarationDescriptor().getOriginal();
            if (original == null) {
                $$$reportNull$$$0(10);
            }
            return original;
        }
        throw new UnsupportedOperationException("Unsupported receiver type: " + receiverValue.getClass() + ", receiverParameter = " + receiverValue);
    }

    public static ReceiverParameterDescriptor getReceiverParameterForDeclaration(DeclarationDescriptor declarationDescriptor) {
        if (declarationDescriptor instanceof ClassDescriptor) {
            ReceiverParameterDescriptor thisAsReceiverParameter = ((ClassDescriptor) declarationDescriptor).getThisAsReceiverParameter();
            if (thisAsReceiverParameter == null) {
                $$$reportNull$$$0(11);
            }
            return thisAsReceiverParameter;
        }
        if (declarationDescriptor instanceof CallableMemberDescriptor) {
            ReceiverParameterDescriptor extensionReceiverParameter = ((CallableMemberDescriptor) declarationDescriptor).getExtensionReceiverParameter();
            if (extensionReceiverParameter == null) {
                $$$reportNull$$$0(12);
            }
            return extensionReceiverParameter;
        }
        throw new UnsupportedOperationException("Unsupported declaration type: " + declarationDescriptor.getClass() + ", declarationDescriptor = " + declarationDescriptor);
    }

    private static boolean isDefaultAccessor(PropertyAccessorDescriptor propertyAccessorDescriptor) {
        return propertyAccessorDescriptor == null || (propertyAccessorDescriptor.isDefault() && !((propertyAccessorDescriptor instanceof PropertySetterDescriptor) && propertyAccessorDescriptor.getCorrespondingProperty().isLateInit()));
    }

    public static boolean sideEffectsPossibleOnRead(PropertyDescriptor propertyDescriptor) {
        if (propertyDescriptor == null) {
            $$$reportNull$$$0(13);
        }
        return DynamicCallsKt.isDynamic(propertyDescriptor) || !isDefaultAccessor(propertyDescriptor.getGetter()) || ModalityUtilsKt.isOverridableOrOverrides(propertyDescriptor) || isStaticInitializationPossible(propertyDescriptor);
    }

    private static boolean isStaticInitializationPossible(PropertyDescriptor propertyDescriptor) {
        DeclarationDescriptor containingDeclaration = propertyDescriptor.getContainingDeclaration();
        return (containingDeclaration instanceof PackageFragmentDescriptor) || DescriptorUtils.isObject(containingDeclaration);
    }

    public static boolean isSimpleFinalProperty(PropertyDescriptor propertyDescriptor) {
        if (propertyDescriptor == null) {
            $$$reportNull$$$0(14);
        }
        return (DescriptorUtils.isExtension(propertyDescriptor) || !isDefaultAccessor(propertyDescriptor.getGetter()) || !isDefaultAccessor(propertyDescriptor.getSetter()) || TranslationUtils.shouldAccessViaFunctions((CallableDescriptor) propertyDescriptor) || ModalityUtilsKt.isOverridableOrOverrides(propertyDescriptor)) ? false : true;
    }

    public static String getModuleName(DeclarationDescriptor declarationDescriptor) {
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(15);
        }
        return getModuleName(DescriptorUtils.getContainingModule(findRealInlineDeclaration(declarationDescriptor)));
    }

    public static String getModuleName(ModuleDescriptor moduleDescriptor) {
        if (moduleDescriptor == null) {
            $$$reportNull$$$0(16);
        }
        if (moduleDescriptor.getShortName().equals(KotlinBuiltIns.BUILTINS_MODULE_NAME)) {
            String str = Namer.KOTLIN_LOWER_NAME;
            if (str == null) {
                $$$reportNull$$$0(17);
            }
            return str;
        }
        String strAsString = moduleDescriptor.getShortName().asString();
        String strSubstring = strAsString.substring(1, strAsString.length() - 1);
        if (strSubstring == null) {
            $$$reportNull$$$0(18);
        }
        return strSubstring;
    }

    public static DeclarationDescriptor findRealInlineDeclaration(DeclarationDescriptor declarationDescriptor) {
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(19);
        }
        if (!(declarationDescriptor instanceof FunctionDescriptor)) {
            if (declarationDescriptor == null) {
                $$$reportNull$$$0(22);
            }
            return declarationDescriptor;
        }
        FunctionDescriptor functionDescriptor = (FunctionDescriptor) declarationDescriptor;
        if (functionDescriptor.getKind().isReal() || !functionDescriptor.isInline()) {
            if (declarationDescriptor == null) {
                $$$reportNull$$$0(20);
            }
            return declarationDescriptor;
        }
        CallableMemberDescriptor callableMemberDescriptorFindRealDeclaration = findRealDeclaration(functionDescriptor);
        if (callableMemberDescriptorFindRealDeclaration == null) {
            $$$reportNull$$$0(21);
        }
        return callableMemberDescriptorFindRealDeclaration;
    }

    public static CallableMemberDescriptor findRealDeclaration(CallableMemberDescriptor callableMemberDescriptor) {
        if (callableMemberDescriptor == null) {
            $$$reportNull$$$0(23);
        }
        if (callableMemberDescriptor.getModality() == Modality.ABSTRACT) {
            return null;
        }
        if (callableMemberDescriptor.getKind().isReal()) {
            return callableMemberDescriptor;
        }
        Iterator<? extends CallableMemberDescriptor> it = callableMemberDescriptor.getOverriddenDescriptors().iterator();
        while (it.hasNext()) {
            CallableMemberDescriptor callableMemberDescriptorFindRealDeclaration = findRealDeclaration(it.next());
            if (callableMemberDescriptorFindRealDeclaration != null) {
                return callableMemberDescriptorFindRealDeclaration;
            }
        }
        return null;
    }

    public static boolean isImmediateSubtypeOfError(ClassDescriptor classDescriptor) {
        if (classDescriptor == null) {
            $$$reportNull$$$0(24);
        }
        if (!isExceptionClass(classDescriptor)) {
            return false;
        }
        ClassDescriptor superClassOrAny = DescriptorUtilsKt.getSuperClassOrAny(classDescriptor);
        return TypeUtilsKt.isNotNullThrowable(superClassOrAny.getDefaultType()) || AnnotationsUtils.isNativeObject(superClassOrAny);
    }

    public static boolean isExceptionClass(ClassDescriptor classDescriptor) {
        if (classDescriptor == null) {
            $$$reportNull$$$0(25);
        }
        return TypeUtilsKt.isSubtypeOf(classDescriptor.getDefaultType(), DescriptorUtils.getContainingModule(classDescriptor).getBuiltIns().getThrowable().getDefaultType());
    }
}
