package org.jetbrains.kotlin.js.translate.expression;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.jetbrains.kotlin.KtNodeTypes;
import org.jetbrains.kotlin.builtins.FunctionTypesKt;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.builtins.PrimitiveType;
import org.jetbrains.kotlin.builtins.ReflectionTypes;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperation;
import org.jetbrains.kotlin.js.backend.ast.JsBooleanLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsConditional;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsInvocation;
import org.jetbrains.kotlin.js.backend.ast.JsNullLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsPrefixOperation;
import org.jetbrains.kotlin.js.backend.ast.JsStringLiteral;
import org.jetbrains.kotlin.js.descriptorUtils.DescriptorUtilsKt;
import org.jetbrains.kotlin.js.patterns.NamePredicate;
import org.jetbrains.kotlin.js.patterns.typePredicates.TypePredicatesKt;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.js.translate.context.TemporaryVariable;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.js.translate.general.AbstractTranslator;
import org.jetbrains.kotlin.js.translate.general.Translation;
import org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.ArrayFIF;
import org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.TopLevelFIF;
import org.jetbrains.kotlin.js.translate.reference.ReferenceTranslator;
import org.jetbrains.kotlin.js.translate.utils.AnnotationsUtils;
import org.jetbrains.kotlin.js.translate.utils.BindingUtils;
import org.jetbrains.kotlin.js.translate.utils.JsAstUtils;
import org.jetbrains.kotlin.js.translate.utils.TranslationUtils;
import org.jetbrains.kotlin.js.translate.utils.UtilsKt;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtBinaryExpressionWithTypeRHS;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtIsExpression;
import org.jetbrains.kotlin.psi.KtPsiUtil;
import org.jetbrains.kotlin.psi.KtTypeReference;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.checkers.PrimitiveNumericComparisonInfo;
import org.jetbrains.kotlin.types.IntersectionTypeConstructor;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.TypeUtils;

/* JADX INFO: loaded from: classes5.dex */
public final class PatternTranslator extends AbstractTranslator {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    private enum EqualityType {
        PRIMITIVE,
        LONG,
        GENERAL
    }

    /* JADX WARN: Code duplicated, block: B:14:0x0028 A[FALL_THROUGH] */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 4 && i != 5 && i != 7 && i != 8 && i != 12 && i != 13 && i != 18 && i != 30) {
            switch (i) {
                default:
                    switch (i) {
                        case 26:
                        case 27:
                        case 28:
                            break;
                        default:
                            str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                            break;
                    }
                case 22:
                case 23:
                case 24:
                    str = "@NotNull method %s.%s must not return null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 4 && i != 5 && i != 7 && i != 8 && i != 12 && i != 13 && i != 18 && i != 30) {
            switch (i) {
                case 22:
                case 23:
                case 24:
                    i2 = 2;
                    break;
                default:
                    switch (i) {
                        case 26:
                        case 27:
                        case 28:
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
            case 3:
            case 6:
                objArr[0] = "expression";
                break;
            case 4:
            case 5:
            case 7:
            case 8:
            case 12:
            case 13:
            case 18:
            case 22:
            case 23:
            case 24:
            case 26:
            case 27:
            case 28:
            case 30:
                objArr[0] = "org/jetbrains/kotlin/js/translate/expression/PatternTranslator";
                break;
            case 9:
                objArr[0] = "subject";
                break;
            case 10:
                objArr[0] = "targetTypeReference";
                break;
            case 11:
            case 14:
            case 15:
            case 16:
            case 25:
                objArr[0] = ModuleXmlParser.TYPE;
                break;
            case 17:
                objArr[0] = "typeParameter";
                break;
            case 19:
                objArr[0] = "subjectType";
                break;
            case 20:
                objArr[0] = "expressionToMatch";
                break;
            case 21:
            case 29:
                objArr[0] = "patternExpression";
                break;
            default:
                objArr[0] = "context";
                break;
        }
        if (i == 4 || i == 5) {
            objArr[1] = "translateCastExpression";
        } else if (i == 7 || i == 8) {
            objArr[1] = "translateIsExpression";
        } else if (i == 12 || i == 13) {
            objArr[1] = "getIsTypeCheckCallable";
        } else if (i == 18) {
            objArr[1] = "getIsTypeCheckCallableForReifiedType";
        } else if (i != 30) {
            switch (i) {
                case 22:
                case 23:
                case 24:
                    objArr[1] = "translateExpressionPattern";
                    break;
                default:
                    switch (i) {
                        case 26:
                        case 27:
                        case 28:
                            objArr[1] = "equalityType";
                            break;
                        default:
                            objArr[1] = "org/jetbrains/kotlin/js/translate/expression/PatternTranslator";
                            break;
                    }
                    break;
            }
        } else {
            objArr[1] = "translateExpressionForExpressionPattern";
        }
        switch (i) {
            case 1:
                objArr[2] = "<init>";
                break;
            case 2:
                objArr[2] = "isCastExpression";
                break;
            case 3:
                objArr[2] = "translateCastExpression";
                break;
            case 4:
            case 5:
            case 7:
            case 8:
            case 12:
            case 13:
            case 18:
            case 22:
            case 23:
            case 24:
            case 26:
            case 27:
            case 28:
            case 30:
                break;
            case 6:
                objArr[2] = "translateIsExpression";
                break;
            case 9:
            case 10:
                objArr[2] = "translateIsCheck";
                break;
            case 11:
                objArr[2] = "getIsTypeCheckCallable";
                break;
            case 14:
                objArr[2] = "doGetIsTypeCheckCallable";
                break;
            case 15:
                objArr[2] = "getIsTypeCheckCallableForBuiltin";
                break;
            case 16:
                objArr[2] = "getIsTypeCheckCallableForPrimitiveBuiltin";
                break;
            case 17:
                objArr[2] = "getIsTypeCheckCallableForReifiedType";
                break;
            case 19:
            case 20:
            case 21:
                objArr[2] = "translateExpressionPattern";
                break;
            case 25:
                objArr[2] = "equalityType";
                break;
            case 29:
                objArr[2] = "translateExpressionForExpressionPattern";
                break;
            default:
                objArr[2] = TypeProxy.SilentConstruction.Appender.NEW_INSTANCE_METHOD_NAME;
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 4 && i != 5 && i != 7 && i != 8 && i != 12 && i != 13 && i != 18 && i != 30) {
            switch (i) {
                case 22:
                case 23:
                case 24:
                    break;
                default:
                    switch (i) {
                        case 26:
                        case 27:
                        case 28:
                            break;
                        default:
                            throw new IllegalArgumentException(str2);
                    }
                    break;
            }
        }
        throw new IllegalStateException(str2);
    }

    public static PatternTranslator newInstance(TranslationContext translationContext) {
        if (translationContext == null) {
            $$$reportNull$$$0(0);
        }
        return new PatternTranslator(translationContext);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    private PatternTranslator(TranslationContext translationContext) {
        super(translationContext);
        if (translationContext == null) {
            $$$reportNull$$$0(1);
        }
    }

    public static boolean isCastExpression(KtBinaryExpressionWithTypeRHS ktBinaryExpressionWithTypeRHS) {
        if (ktBinaryExpressionWithTypeRHS == null) {
            $$$reportNull$$$0(2);
        }
        return KtPsiUtil.isSafeCast(ktBinaryExpressionWithTypeRHS) || KtPsiUtil.isUnsafeCast(ktBinaryExpressionWithTypeRHS);
    }

    public JsExpression translateCastExpression(KtBinaryExpressionWithTypeRHS ktBinaryExpressionWithTypeRHS) {
        JsExpression jsInvocation;
        if (ktBinaryExpressionWithTypeRHS == null) {
            $$$reportNull$$$0(3);
        }
        JsExpression jsExpressionTranslateAsExpression = Translation.translateAsExpression(ktBinaryExpressionWithTypeRHS.getLeft(), context());
        KtTypeReference right = ktBinaryExpressionWithTypeRHS.getRight();
        JsExpression jsExpressionCoerce = TranslationUtils.coerce(context(), jsExpressionTranslateAsExpression, context().getCurrentModule().getBuiltIns().getAnyType());
        TemporaryVariable temporaryVariableDeclareTemporary = context().declareTemporary(jsExpressionCoerce, ktBinaryExpressionWithTypeRHS);
        JsExpression jsExpressionTranslateIsCheck = translateIsCheck(temporaryVariableDeclareTemporary.assignmentExpression(), right);
        if (jsExpressionTranslateIsCheck == null) {
            if (jsExpressionCoerce == null) {
                $$$reportNull$$$0(4);
            }
            return jsExpressionCoerce;
        }
        if (KtPsiUtil.isSafeCast(ktBinaryExpressionWithTypeRHS)) {
            jsInvocation = new JsNullLiteral();
        } else {
            jsInvocation = new JsInvocation(context().getReferenceToIntrinsic(Namer.THROW_CLASS_CAST_EXCEPTION_FUN_NAME), new JsExpression[0]);
        }
        JsConditional jsConditional = new JsConditional(jsExpressionTranslateIsCheck, temporaryVariableDeclareTemporary.reference(), jsInvocation);
        KotlinType typeByReference = BindingUtils.getTypeByReference(bindingContext(), right);
        if (KtPsiUtil.isSafeCast(ktBinaryExpressionWithTypeRHS)) {
            typeByReference = typeByReference.unwrap().makeNullableAsSpecified(true);
        }
        JsExpression jsExpressionCoerce2 = TranslationUtils.coerce(context(), jsConditional, typeByReference);
        if (jsExpressionCoerce2 == null) {
            $$$reportNull$$$0(5);
        }
        return jsExpressionCoerce2;
    }

    public JsExpression translateIsExpression(KtIsExpression ktIsExpression) {
        if (ktIsExpression == null) {
            $$$reportNull$$$0(6);
        }
        JsExpression jsExpressionTranslateIsCheck = translateIsCheck(TranslationUtils.coerce(context(), Translation.translateAsExpression(ktIsExpression.getLeftHandSide(), context()), context().getCurrentModule().getBuiltIns().getAnyType()), ktIsExpression.getTypeReference());
        if (jsExpressionTranslateIsCheck == null) {
            return new JsBooleanLiteral(!ktIsExpression.isNegated());
        }
        if (!ktIsExpression.isNegated()) {
            if (jsExpressionTranslateIsCheck == null) {
                $$$reportNull$$$0(8);
            }
            return jsExpressionTranslateIsCheck;
        }
        JsPrefixOperation jsPrefixOperationNot = JsAstUtils.not(jsExpressionTranslateIsCheck);
        if (jsPrefixOperationNot == null) {
            $$$reportNull$$$0(7);
        }
        return jsPrefixOperationNot;
    }

    public JsExpression translateIsCheck(JsExpression jsExpression, KtTypeReference ktTypeReference) {
        if (jsExpression == null) {
            $$$reportNull$$$0(9);
        }
        if (ktTypeReference == null) {
            $$$reportNull$$$0(10);
        }
        KotlinType typeByReference = BindingUtils.getTypeByReference(bindingContext(), ktTypeReference);
        JsExpression jsExpressionDoGetIsTypeCheckCallable = doGetIsTypeCheckCallable(typeByReference);
        if (jsExpressionDoGetIsTypeCheckCallable == null) {
            return null;
        }
        boolean zIsReifiedTypeParameter = TypeUtils.isReifiedTypeParameter(typeByReference);
        if ((!zIsReifiedTypeParameter && TypeUtils.isNullableType(typeByReference)) || (zIsReifiedTypeParameter && KtPsiUtil.findChildByType(ktTypeReference, KtNodeTypes.NULLABLE_TYPE) != null)) {
            jsExpressionDoGetIsTypeCheckCallable = namer().orNull(jsExpressionDoGetIsTypeCheckCallable);
        }
        return new JsInvocation(jsExpressionDoGetIsTypeCheckCallable, jsExpression);
    }

    public JsExpression getIsTypeCheckCallable(KotlinType kotlinType) {
        if (kotlinType == null) {
            $$$reportNull$$$0(11);
        }
        JsExpression jsExpressionDoGetIsTypeCheckCallable = doGetIsTypeCheckCallable(kotlinType);
        if (TypeUtils.isReifiedTypeParameter(kotlinType) || !TypeUtils.isNullableType(kotlinType)) {
            if (jsExpressionDoGetIsTypeCheckCallable == null) {
                $$$reportNull$$$0(13);
            }
            return jsExpressionDoGetIsTypeCheckCallable;
        }
        JsExpression jsExpressionOrNull = namer().orNull(jsExpressionDoGetIsTypeCheckCallable);
        if (jsExpressionOrNull == null) {
            $$$reportNull$$$0(12);
        }
        return jsExpressionOrNull;
    }

    private JsExpression doGetIsTypeCheckCallable(KotlinType kotlinType) {
        if (kotlinType == null) {
            $$$reportNull$$$0(14);
        }
        ClassifierDescriptor this$0 = kotlinType.getConstructor().getThis$0();
        if (this$0 != null && AnnotationsUtils.isNativeInterface(this$0)) {
            if (kotlinType.getIsMarkedNullable()) {
                return null;
            }
            return namer().isInstanceOf(JsAstUtils.pureFqn(CommonClassNames.JAVA_LANG_OBJECT_SHORT, (JsExpression) null));
        }
        JsExpression isTypeCheckCallableForBuiltin = getIsTypeCheckCallableForBuiltin(kotlinType);
        if (isTypeCheckCallableForBuiltin != null) {
            return isTypeCheckCallableForBuiltin;
        }
        JsExpression isTypeCheckCallableForPrimitiveBuiltin = getIsTypeCheckCallableForPrimitiveBuiltin(kotlinType);
        if (isTypeCheckCallableForPrimitiveBuiltin != null) {
            return isTypeCheckCallableForPrimitiveBuiltin;
        }
        TypeParameterDescriptor typeParameterDescriptorOrNull = TypeUtils.getTypeParameterDescriptorOrNull(kotlinType);
        if (typeParameterDescriptorOrNull != null) {
            if (typeParameterDescriptorOrNull.isReified()) {
                return getIsTypeCheckCallableForReifiedType(typeParameterDescriptorOrNull);
            }
            return getIsTypeCheckForAll(typeParameterDescriptorOrNull.getUpperBounds());
        }
        TypeConstructor constructor = kotlinType.getConstructor();
        if (constructor instanceof IntersectionTypeConstructor) {
            return getIsTypeCheckForAll(constructor.mo7438getSupertypes());
        }
        return namer().isInstanceOf(ReferenceTranslator.translateAsTypeReference(DescriptorUtils.getClassDescriptorForType(kotlinType), context()));
    }

    private JsExpression getIsTypeCheckForAll(Collection<KotlinType> collection) {
        Iterator<KotlinType> it = collection.iterator();
        JsExpression jsExpression = null;
        while (it.hasNext()) {
            JsExpression jsExpressionDoGetIsTypeCheckCallable = doGetIsTypeCheckCallable(it.next());
            if (jsExpressionDoGetIsTypeCheckCallable != null) {
                if (jsExpression != null) {
                    jsExpressionDoGetIsTypeCheckCallable = namer().andPredicate(jsExpression, jsExpressionDoGetIsTypeCheckCallable);
                }
                jsExpression = jsExpressionDoGetIsTypeCheckCallable;
            }
        }
        return jsExpression;
    }

    private JsExpression getIsTypeCheckCallableForBuiltin(KotlinType kotlinType) {
        if (kotlinType == null) {
            $$$reportNull$$$0(15);
        }
        if ((KotlinBuiltIns.isNotNullOrNullableFunctionSupertype(kotlinType) || FunctionTypesKt.isFunctionTypeOrSubtype(kotlinType)) && !ReflectionTypes.isNumberedKPropertyOrKMutablePropertyType(kotlinType)) {
            return namer().isTypeOf(new JsStringLiteral("function"));
        }
        if (KotlinBuiltIns.isArray(kotlinType)) {
            if (ArrayFIF.typedArraysEnabled(context().getConfig())) {
                return namer().isArray();
            }
            return Namer.IS_ARRAY_FUN_REF;
        }
        if (TypePredicatesKt.getCHAR_SEQUENCE().test(kotlinType)) {
            return namer().isCharSequence();
        }
        if (TypePredicatesKt.getCOMPARABLE().test(kotlinType)) {
            return namer().isComparable();
        }
        return null;
    }

    private JsExpression getIsTypeCheckCallableForPrimitiveBuiltin(KotlinType kotlinType) {
        if (kotlinType == null) {
            $$$reportNull$$$0(16);
        }
        Name nameIfStandardType = DescriptorUtilsKt.getNameIfStandardType(kotlinType);
        if (NamePredicate.STRING.test(nameIfStandardType)) {
            return namer().isTypeOf(new JsStringLiteral("string"));
        }
        if (NamePredicate.BOOLEAN.test(nameIfStandardType)) {
            return namer().isTypeOf(new JsStringLiteral("boolean"));
        }
        if (NamePredicate.LONG.test(nameIfStandardType)) {
            return namer().isInstanceOf(Namer.kotlinLong());
        }
        if (NamePredicate.NUMBER.test(nameIfStandardType)) {
            return namer().kotlin(Namer.IS_NUMBER);
        }
        if (NamePredicate.CHAR.test(nameIfStandardType)) {
            return namer().kotlin(Namer.IS_CHAR);
        }
        if (NamePredicate.PRIMITIVE_NUMBERS_MAPPED_TO_PRIMITIVE_JS.test(nameIfStandardType)) {
            return namer().isTypeOf(new JsStringLiteral("number"));
        }
        if (!ArrayFIF.typedArraysEnabled(context().getConfig()) || !KotlinBuiltIns.isPrimitiveArray(kotlinType)) {
            return null;
        }
        return namer().isPrimitiveArray(KotlinBuiltIns.getPrimitiveArrayElementType(kotlinType));
    }

    private JsExpression getIsTypeCheckCallableForReifiedType(TypeParameterDescriptor typeParameterDescriptor) {
        if (typeParameterDescriptor == null) {
            $$$reportNull$$$0(17);
        }
        typeParameterDescriptor.getContainingDeclaration();
        JsExpression aliasForDescriptor = context().getAliasForDescriptor(typeParameterDescriptor);
        if (aliasForDescriptor == null) {
            $$$reportNull$$$0(18);
        }
        return aliasForDescriptor;
    }

    public JsExpression translateExpressionPattern(KotlinType kotlinType, JsExpression jsExpression, KtExpression ktExpression) {
        KotlinType kotlinTypeRefineType;
        KotlinType precisePrimitiveTypeNotNull;
        if (kotlinType == null) {
            $$$reportNull$$$0(19);
        }
        if (jsExpression == null) {
            $$$reportNull$$$0(20);
        }
        if (ktExpression == null) {
            $$$reportNull$$$0(21);
        }
        PrimitiveNumericComparisonInfo primitiveNumericComparisonInfo = UtilsKt.getPrimitiveNumericComparisonInfo(context(), ktExpression);
        if (primitiveNumericComparisonInfo != null) {
            kotlinTypeRefineType = primitiveNumericComparisonInfo.getLeftType();
            precisePrimitiveTypeNotNull = primitiveNumericComparisonInfo.getRightType();
        } else {
            kotlinTypeRefineType = UtilsKt.refineType(kotlinType);
            precisePrimitiveTypeNotNull = UtilsKt.getPrecisePrimitiveTypeNotNull(context(), ktExpression);
        }
        EqualityType equalityType = equalityType(kotlinTypeRefineType);
        EqualityType equalityType2 = equalityType(precisePrimitiveTypeNotNull);
        JsExpression jsExpressionCoerce = TranslationUtils.coerce(context(), translateExpressionForExpressionPattern(ktExpression), kotlinTypeRefineType);
        if (equalityType == EqualityType.PRIMITIVE && equalityType2 == EqualityType.PRIMITIVE) {
            JsBinaryOperation jsBinaryOperationEquality = JsAstUtils.equality(jsExpression, jsExpressionCoerce);
            if (jsBinaryOperationEquality == null) {
                $$$reportNull$$$0(22);
            }
            return jsBinaryOperationEquality;
        }
        if (jsExpressionCoerce instanceof JsNullLiteral) {
            JsBinaryOperation jsBinaryOperationNullCheck = TranslationUtils.nullCheck(kotlinTypeRefineType, jsExpression, context(), false);
            if (jsBinaryOperationNullCheck == null) {
                $$$reportNull$$$0(23);
            }
            return jsBinaryOperationNullCheck;
        }
        JsExpression jsExpressionApply = TopLevelFIF.KOTLIN_EQUALS.apply(jsExpression, Collections.singletonList(jsExpressionCoerce), context());
        if (jsExpressionApply == null) {
            $$$reportNull$$$0(24);
        }
        return jsExpressionApply;
    }

    private static EqualityType equalityType(KotlinType kotlinType) {
        if (kotlinType == null) {
            $$$reportNull$$$0(25);
        }
        ClassifierDescriptor this$0 = kotlinType.getConstructor().getThis$0();
        if (!(this$0 instanceof ClassDescriptor)) {
            EqualityType equalityType = EqualityType.GENERAL;
            if (equalityType == null) {
                $$$reportNull$$$0(26);
            }
            return equalityType;
        }
        PrimitiveType primitiveType = KotlinBuiltIns.getPrimitiveType(this$0);
        if (primitiveType == null) {
            EqualityType equalityType2 = EqualityType.GENERAL;
            if (equalityType2 == null) {
                $$$reportNull$$$0(27);
            }
            return equalityType2;
        }
        EqualityType equalityType3 = primitiveType == PrimitiveType.LONG ? EqualityType.LONG : EqualityType.PRIMITIVE;
        if (equalityType3 == null) {
            $$$reportNull$$$0(28);
        }
        return equalityType3;
    }

    public JsExpression translateExpressionForExpressionPattern(KtExpression ktExpression) {
        if (ktExpression == null) {
            $$$reportNull$$$0(29);
        }
        JsExpression jsExpressionTranslateAsExpression = Translation.translateAsExpression(ktExpression, context());
        if (jsExpressionTranslateAsExpression == null) {
            $$$reportNull$$$0(30);
        }
        return jsExpressionTranslateAsExpression;
    }
}
