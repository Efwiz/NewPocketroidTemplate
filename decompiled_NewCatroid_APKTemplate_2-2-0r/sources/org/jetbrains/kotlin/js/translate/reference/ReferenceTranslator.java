package org.jetbrains.kotlin.js.translate.reference;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorPsiUtilsKt;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.PropertySetterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.descriptors.impl.AnonymousFunctionDescriptor;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsInvocation;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.metadata.MetadataProperties;
import org.jetbrains.kotlin.js.backend.ast.metadata.SideEffectKind;
import org.jetbrains.kotlin.js.descriptorUtils.DescriptorUtilsKt;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.js.translate.utils.AnnotationsUtils;
import org.jetbrains.kotlin.js.translate.utils.BindingUtils;
import org.jetbrains.kotlin.js.translate.utils.JsAstUtils;
import org.jetbrains.kotlin.js.translate.utils.PsiUtils;
import org.jetbrains.kotlin.js.translate.utils.TranslationUtils;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.name.FqNameUnsafe;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtQualifiedExpression;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.calls.util.FakeCallableDescriptorForTypeAliasObject;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: loaded from: classes5.dex */
public final class ReferenceTranslator {
    private static final Set<FqNameUnsafe> DECLARATIONS_WITHOUT_SIDE_EFFECTS = new HashSet(Arrays.asList(new FqNameUnsafe("kotlin.coroutines.experimental.intrinsics.COROUTINE_SUSPENDED"), new FqNameUnsafe("kotlin.coroutines.intrinsics.COROUTINE_SUSPENDED"), StandardNames.FqNames.unit));

    /* JADX WARN: Code duplicated, block: B:11:0x001b A[FALL_THROUGH] */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 2 && i != 5 && i != 22 && i != 18 && i != 19) {
            switch (i) {
                default:
                    switch (i) {
                        case 30:
                        case 31:
                        case 32:
                        case 33:
                            break;
                        default:
                            str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                            break;
                    }
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                    str = "@NotNull method %s.%s must not return null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 2 && i != 5 && i != 22 && i != 18 && i != 19) {
            switch (i) {
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                    i2 = 2;
                    break;
                default:
                    switch (i) {
                        case 30:
                        case 31:
                        case 32:
                        case 33:
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
            case 1:
            case 4:
            case 8:
            case 17:
            case 23:
            case 26:
            case 29:
            case 35:
                objArr[0] = "context";
                break;
            case 2:
            case 5:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 18:
            case 19:
            case 22:
            case 30:
            case 31:
            case 32:
            case 33:
                objArr[0] = "org/jetbrains/kotlin/js/translate/reference/ReferenceTranslator";
                break;
            case 3:
            case 6:
            case 7:
            case 15:
            case 16:
            case 20:
            case 24:
            case 25:
            case 27:
                objArr[0] = "descriptor";
                break;
            case 21:
                objArr[0] = "reference";
                break;
            case 28:
                objArr[0] = "referenceExpression";
                break;
            case 34:
            default:
                objArr[0] = "expression";
                break;
        }
        if (i == 2) {
            objArr[1] = "translateSimpleName";
        } else if (i == 5) {
            objArr[1] = "translateAsValueReference";
        } else if (i == 22) {
            objArr[1] = "getPrototypeIfNecessary";
        } else if (i != 18 && i != 19) {
            switch (i) {
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                    objArr[1] = "translateAsValueReferenceWithoutType";
                    break;
                default:
                    switch (i) {
                        case 30:
                        case 31:
                        case 32:
                        case 33:
                            objArr[1] = "getAccessTranslator";
                            break;
                        default:
                            objArr[1] = "org/jetbrains/kotlin/js/translate/reference/ReferenceTranslator";
                            break;
                    }
                    break;
            }
        } else {
            objArr[1] = "translateAsTypeReference";
        }
        switch (i) {
            case 2:
            case 5:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 18:
            case 19:
            case 22:
            case 30:
            case 31:
            case 32:
            case 33:
                break;
            case 3:
            case 4:
                objArr[2] = "translateAsValueReference";
                break;
            case 6:
                objArr[2] = "getType";
                break;
            case 7:
            case 8:
                objArr[2] = "translateAsValueReferenceWithoutType";
                break;
            case 15:
                objArr[2] = "isValueWithoutSideEffect";
                break;
            case 16:
            case 17:
                objArr[2] = "translateAsTypeReference";
                break;
            case 20:
            case 21:
                objArr[2] = "getPrototypeIfNecessary";
                break;
            case 23:
            case 24:
                objArr[2] = "isLocallyAvailableDeclaration";
                break;
            case 25:
            case 26:
                objArr[2] = "getLazyReferenceToObject";
                break;
            case 27:
                objArr[2] = "shouldTranslateAsFQN";
                break;
            case 28:
            case 29:
                objArr[2] = "getAccessTranslator";
                break;
            case 34:
            case 35:
                objArr[2] = "canBePropertyAccess";
                break;
            default:
                objArr[2] = "translateSimpleName";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 5 && i != 22 && i != 18 && i != 19) {
            switch (i) {
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                    break;
                default:
                    switch (i) {
                        case 30:
                        case 31:
                        case 32:
                        case 33:
                            break;
                        default:
                            throw new IllegalArgumentException(str2);
                    }
                    break;
            }
        }
        throw new IllegalStateException(str2);
    }

    private ReferenceTranslator() {
    }

    public static JsExpression translateSimpleName(KtSimpleNameExpression ktSimpleNameExpression, TranslationContext translationContext) {
        if (ktSimpleNameExpression == null) {
            $$$reportNull$$$0(0);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(1);
        }
        JsExpression jsExpressionTranslateAsGet = getAccessTranslator(ktSimpleNameExpression, translationContext).translateAsGet();
        if (jsExpressionTranslateAsGet == null) {
            $$$reportNull$$$0(2);
        }
        return jsExpressionTranslateAsGet;
    }

    public static JsExpression translateAsValueReference(DeclarationDescriptor declarationDescriptor, TranslationContext translationContext) {
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(3);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(4);
        }
        JsExpression jsExpressionTranslateAsValueReferenceWithoutType = translateAsValueReferenceWithoutType(declarationDescriptor, translationContext);
        MetadataProperties.setType(jsExpressionTranslateAsValueReferenceWithoutType, getType(declarationDescriptor));
        if (isValueWithoutSideEffect(declarationDescriptor)) {
            MetadataProperties.setUnit(jsExpressionTranslateAsValueReferenceWithoutType, true);
            MetadataProperties.setSideEffects(jsExpressionTranslateAsValueReferenceWithoutType, SideEffectKind.PURE);
            MetadataProperties.setSynthetic(jsExpressionTranslateAsValueReferenceWithoutType, true);
        }
        if (jsExpressionTranslateAsValueReferenceWithoutType == null) {
            $$$reportNull$$$0(5);
        }
        return jsExpressionTranslateAsValueReferenceWithoutType;
    }

    private static KotlinType getType(DeclarationDescriptor declarationDescriptor) {
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(6);
        }
        if (declarationDescriptor instanceof ClassDescriptor) {
            return ((ClassDescriptor) declarationDescriptor).getDefaultType();
        }
        if (!(declarationDescriptor instanceof CallableDescriptor)) {
            return null;
        }
        if (declarationDescriptor instanceof ValueParameterDescriptor) {
            ValueParameterDescriptor valueParameterDescriptor = (ValueParameterDescriptor) declarationDescriptor;
            if (valueParameterDescriptor.getContainingDeclaration() instanceof AnonymousFunctionDescriptor) {
                return DescriptorUtils.getContainingModule(declarationDescriptor).getBuiltIns().getAnyType();
            }
            if (valueParameterDescriptor.getContainingDeclaration() instanceof PropertySetterDescriptor) {
                return TranslationUtils.getReturnTypeForCoercion(((PropertySetterDescriptor) valueParameterDescriptor.getContainingDeclaration()).getCorrespondingProperty(), false);
            }
        }
        return ((CallableDescriptor) declarationDescriptor).getReturnType();
    }

    private static JsExpression translateAsValueReferenceWithoutType(DeclarationDescriptor declarationDescriptor, TranslationContext translationContext) {
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(7);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(8);
        }
        if (AnnotationsUtils.isNativeObject(declarationDescriptor) || AnnotationsUtils.isLibraryObject(declarationDescriptor)) {
            JsExpression innerReference = translationContext.getInnerReference(declarationDescriptor);
            if (innerReference == null) {
                $$$reportNull$$$0(9);
            }
            return innerReference;
        }
        JsExpression aliasForDescriptor = translationContext.getAliasForDescriptor(declarationDescriptor);
        if (aliasForDescriptor != null) {
            if (aliasForDescriptor == null) {
                $$$reportNull$$$0(10);
            }
            return aliasForDescriptor;
        }
        if (shouldTranslateAsFQN(declarationDescriptor)) {
            JsExpression qualifiedReference = translationContext.getQualifiedReference(declarationDescriptor);
            if (qualifiedReference == null) {
                $$$reportNull$$$0(11);
            }
            return qualifiedReference;
        }
        if (declarationDescriptor instanceof PropertyDescriptor) {
            PropertyDescriptor propertyDescriptor = (PropertyDescriptor) declarationDescriptor;
            if (isLocallyAvailableDeclaration(translationContext, propertyDescriptor) || isValueWithoutSideEffect(propertyDescriptor)) {
                JsExpression innerReference2 = translationContext.getInnerReference(propertyDescriptor);
                if (innerReference2 == null) {
                    $$$reportNull$$$0(12);
                }
                return innerReference2;
            }
            return new JsNameRef(translationContext.getNameForDescriptor(propertyDescriptor), translationContext.getInnerReference(propertyDescriptor.getContainingDeclaration()));
        }
        if (DescriptorUtils.isObject(declarationDescriptor) || DescriptorUtils.isEnumEntry(declarationDescriptor)) {
            ClassDescriptor classDescriptor = (ClassDescriptor) declarationDescriptor;
            if (!isLocallyAvailableDeclaration(translationContext, declarationDescriptor)) {
                if (isValueWithoutSideEffect(classDescriptor)) {
                    JsExpression innerReference3 = translationContext.getInnerReference(declarationDescriptor);
                    if (innerReference3 == null) {
                        $$$reportNull$$$0(13);
                    }
                    return innerReference3;
                }
                return getLazyReferenceToObject(classDescriptor, translationContext);
            }
            return new JsInvocation(JsAstUtils.pureFqn(translationContext.getNameForObjectInstance(classDescriptor), (JsExpression) null), new JsExpression[0]);
        }
        JsExpression innerReference4 = translationContext.getInnerReference(declarationDescriptor);
        if (innerReference4 == null) {
            $$$reportNull$$$0(14);
        }
        return innerReference4;
    }

    private static boolean isValueWithoutSideEffect(DeclarationDescriptor declarationDescriptor) {
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(15);
        }
        return DECLARATIONS_WITHOUT_SIDE_EFFECTS.contains(DescriptorUtils.getFqName(declarationDescriptor));
    }

    public static JsExpression translateAsTypeReference(ClassDescriptor classDescriptor, TranslationContext translationContext) {
        if (classDescriptor == null) {
            $$$reportNull$$$0(16);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(17);
        }
        if (AnnotationsUtils.isNativeObject(classDescriptor) || AnnotationsUtils.isLibraryObject(classDescriptor)) {
            JsExpression innerReference = translationContext.getInnerReference(classDescriptor);
            if (innerReference == null) {
                $$$reportNull$$$0(18);
            }
            return innerReference;
        }
        if ((DescriptorUtils.isObject(classDescriptor) || DescriptorUtils.isEnumEntry(classDescriptor)) && !isLocallyAvailableDeclaration(translationContext, classDescriptor)) {
            return getPrototypeIfNecessary(classDescriptor, getLazyReferenceToObject(classDescriptor, translationContext));
        }
        JsExpression innerReference2 = translationContext.getInnerReference(classDescriptor);
        if (innerReference2 == null) {
            $$$reportNull$$$0(19);
        }
        return innerReference2;
    }

    private static JsExpression getPrototypeIfNecessary(ClassDescriptor classDescriptor, JsExpression jsExpression) {
        if (classDescriptor == null) {
            $$$reportNull$$$0(20);
        }
        if (jsExpression == null) {
            $$$reportNull$$$0(21);
        }
        if (DescriptorUtils.isObject(classDescriptor) || DescriptorUtils.isEnumEntry(classDescriptor)) {
            JsInvocation jsInvocation = new JsInvocation(JsAstUtils.pureFqn("getPrototypeOf", JsAstUtils.pureFqn(CommonClassNames.JAVA_LANG_OBJECT_SHORT, (JsExpression) null)), jsExpression);
            MetadataProperties.setSideEffects(jsInvocation, SideEffectKind.PURE);
            jsExpression = JsAstUtils.pureFqn(JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, jsInvocation);
        }
        if (jsExpression == null) {
            $$$reportNull$$$0(22);
        }
        return jsExpression;
    }

    private static boolean isLocallyAvailableDeclaration(TranslationContext translationContext, DeclarationDescriptor declarationDescriptor) {
        if (translationContext == null) {
            $$$reportNull$$$0(23);
        }
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(24);
        }
        return translationContext.isFromCurrentModule(declarationDescriptor) && !(translationContext.isPublicInlineFunction() && DescriptorUtilsKt.shouldBeExported(declarationDescriptor, translationContext.getConfig()));
    }

    private static JsExpression getLazyReferenceToObject(ClassDescriptor classDescriptor, TranslationContext translationContext) {
        if (classDescriptor == null) {
            $$$reportNull$$$0(25);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(26);
        }
        return new JsNameRef(translationContext.getNameForDescriptor(classDescriptor), translationContext.getInnerReference(classDescriptor.getContainingDeclaration()));
    }

    private static boolean shouldTranslateAsFQN(DeclarationDescriptor declarationDescriptor) {
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(27);
        }
        return isLocalVarOrFunction(declarationDescriptor);
    }

    private static boolean isLocalVarOrFunction(DeclarationDescriptor declarationDescriptor) {
        return (declarationDescriptor.getContainingDeclaration() instanceof FunctionDescriptor) && !(declarationDescriptor instanceof ClassDescriptor);
    }

    public static AccessTranslator getAccessTranslator(KtSimpleNameExpression ktSimpleNameExpression, TranslationContext translationContext) {
        if (ktSimpleNameExpression == null) {
            $$$reportNull$$$0(28);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(29);
        }
        if (DescriptorPsiUtilsKt.isBackingFieldReference(BindingUtils.getDescriptorForReferenceExpression(translationContext.bindingContext(), ktSimpleNameExpression))) {
            BackingFieldAccessTranslator backingFieldAccessTranslatorNewInstance = BackingFieldAccessTranslator.newInstance(ktSimpleNameExpression, translationContext);
            if (backingFieldAccessTranslatorNewInstance == null) {
                $$$reportNull$$$0(30);
            }
            return backingFieldAccessTranslatorNewInstance;
        }
        if (canBePropertyAccess(ktSimpleNameExpression, translationContext)) {
            VariableAccessTranslator variableAccessTranslatorNewInstance = VariableAccessTranslator.newInstance(translationContext, ktSimpleNameExpression, null);
            if (variableAccessTranslatorNewInstance == null) {
                $$$reportNull$$$0(31);
            }
            return variableAccessTranslatorNewInstance;
        }
        if (CompanionObjectIntrinsicAccessTranslator.isCompanionObjectReference(ktSimpleNameExpression, translationContext)) {
            CompanionObjectIntrinsicAccessTranslator companionObjectIntrinsicAccessTranslatorNewInstance = CompanionObjectIntrinsicAccessTranslator.newInstance(ktSimpleNameExpression, translationContext);
            if (companionObjectIntrinsicAccessTranslatorNewInstance == null) {
                $$$reportNull$$$0(32);
            }
            return companionObjectIntrinsicAccessTranslatorNewInstance;
        }
        ReferenceAccessTranslator referenceAccessTranslatorNewInstance = ReferenceAccessTranslator.newInstance(ktSimpleNameExpression, translationContext);
        if (referenceAccessTranslatorNewInstance == null) {
            $$$reportNull$$$0(33);
        }
        return referenceAccessTranslatorNewInstance;
    }

    public static boolean canBePropertyAccess(KtExpression ktExpression, TranslationContext translationContext) {
        KtSimpleNameExpression selectorAsSimpleName;
        if (ktExpression == null) {
            $$$reportNull$$$0(34);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(35);
        }
        if (ktExpression instanceof KtQualifiedExpression) {
            selectorAsSimpleName = PsiUtils.getSelectorAsSimpleName((KtQualifiedExpression) ktExpression);
        } else {
            selectorAsSimpleName = ktExpression instanceof KtSimpleNameExpression ? (KtSimpleNameExpression) ktExpression : null;
        }
        if (selectorAsSimpleName == null) {
            return false;
        }
        DeclarationDescriptor descriptorForReferenceExpression = BindingUtils.getDescriptorForReferenceExpression(translationContext.bindingContext(), selectorAsSimpleName);
        return (!(descriptorForReferenceExpression instanceof VariableDescriptor) || (descriptorForReferenceExpression instanceof ValueParameterDescriptor) || (descriptorForReferenceExpression instanceof FakeCallableDescriptorForTypeAliasObject)) ? false : true;
    }
}
