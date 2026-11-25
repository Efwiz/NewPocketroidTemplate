package org.jetbrains.kotlin.js.translate.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;
import org.jetbrains.kotlin.builtins.FunctionTypesKt;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.builtins.PrimitiveType;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.EffectiveVisibilityUtilsKt;
import org.jetbrains.kotlin.descriptors.FindClassInModuleKt;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ModalityUtilsKt;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyAccessorDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyGetterDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.descriptors.impl.AnonymousFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.impl.LocalVariableAccessorDescriptor;
import org.jetbrains.kotlin.descriptors.impl.LocalVariableDescriptor;
import org.jetbrains.kotlin.incremental.components.NoLookupLocation;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperation;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperator;
import org.jetbrains.kotlin.js.backend.ast.JsBlock;
import org.jetbrains.kotlin.js.backend.ast.JsConditional;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;
import org.jetbrains.kotlin.js.backend.ast.JsInvocation;
import org.jetbrains.kotlin.js.backend.ast.JsLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsNew;
import org.jetbrains.kotlin.js.backend.ast.JsNullLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsPropertyInitializer;
import org.jetbrains.kotlin.js.backend.ast.JsReturn;
import org.jetbrains.kotlin.js.backend.ast.JsScope;
import org.jetbrains.kotlin.js.backend.ast.JsStringLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsUnaryOperation;
import org.jetbrains.kotlin.js.backend.ast.JsUnaryOperator;
import org.jetbrains.kotlin.js.backend.ast.metadata.BoxingKind;
import org.jetbrains.kotlin.js.backend.ast.metadata.MetadataProperties;
import org.jetbrains.kotlin.js.backend.ast.metadata.SpecialFunction;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.js.translate.context.TemporaryConstVariable;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.js.translate.expression.InlineMetadata;
import org.jetbrains.kotlin.js.translate.general.Translation;
import org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.ArrayFIF;
import org.jetbrains.kotlin.js.translate.reference.ReferenceTranslator;
import org.jetbrains.kotlin.js.translate.utils.jsAstUtils.AstUtilsKt;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqNameUnsafe;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtBinaryExpression;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtOperationExpression;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.psi.KtUnaryExpression;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingContextUtils;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.inline.InlineUtil;
import org.jetbrains.kotlin.resolve.source.PsiSourceElementKt;
import org.jetbrains.kotlin.types.DynamicTypesKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.RangeUtilKt;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeUtils;

/* JADX INFO: loaded from: classes5.dex */
public final class TranslationUtils {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final Set<FqNameUnsafe> CLASSES_WITH_NON_BOXED_CHARS = new HashSet(Arrays.asList(new FqNameUnsafe("kotlin.collections.CharIterator"), new FqNameUnsafe(RangeUtilKt.CHAR_PROGRESSION_FQN), new FqNameUnsafe("kotlin.js.internal.CharCompanionObject"), new FqNameUnsafe("kotlin.Char.Companion"), StandardNames.FqNames.charSequence, StandardNames.FqNames.number));

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 29:
            case 30:
            case 34:
            case 38:
            case 43:
            case 47:
            case 52:
            case 63:
            case 65:
            case 67:
            case 70:
            case 72:
            case 76:
            case 78:
            case 80:
            case 84:
            case 85:
            case 86:
            case 89:
            case 91:
            case 92:
            case 95:
            case 96:
            case 97:
            case 100:
            case 101:
            case 102:
            case 107:
            case 109:
                str = "@NotNull method %s.%s must not return null";
                break;
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 29:
            case 30:
            case 34:
            case 38:
            case 43:
            case 47:
            case 52:
            case 63:
            case 65:
            case 67:
            case 70:
            case 72:
            case 76:
            case 78:
            case 80:
            case 84:
            case 85:
            case 86:
            case 89:
            case 91:
            case 92:
            case 95:
            case 96:
            case 97:
            case 100:
            case 101:
            case 102:
            case 107:
            case 109:
                i2 = 2;
                break;
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 3:
            case 7:
            case 28:
            case 31:
            case 33:
            case 36:
            case 60:
            case 73:
            case 74:
            case 75:
            case 77:
            case 79:
                objArr[0] = "descriptor";
                break;
            case 2:
            case 8:
            case 17:
            case 20:
            case 23:
            case 26:
            case 27:
            case 32:
            case 35:
            case 40:
            case 41:
            case 44:
            case 48:
            case 56:
            case 57:
            case 59:
            case 62:
            case 64:
            case 66:
            case 68:
            case 71:
            case 81:
            case 87:
            case 93:
            case 98:
            case 103:
                objArr[0] = "context";
                break;
            case 4:
                objArr[0] = "functionScope";
                break;
            case 5:
                objArr[0] = "returnExpression";
                break;
            case 6:
                objArr[0] = "functionExpression";
                break;
            case 9:
                objArr[0] = "baseBinaryExpression";
                break;
            case 10:
            case 11:
                objArr[0] = "operator";
                break;
            case 12:
            case 13:
            case 14:
            case 19:
            case 22:
                objArr[0] = "expressionToCheck";
                break;
            case 15:
            case 18:
                objArr[0] = "ktSubject";
                break;
            case 16:
            case 24:
            case 42:
            case 45:
            case 49:
            case 53:
            case 55:
            case 88:
            case 90:
            case 94:
            case 99:
                objArr[0] = "expression";
                break;
            case 21:
                objArr[0] = "expressionType";
                break;
            case 25:
                objArr[0] = "elseExpression";
                break;
            case 29:
            case 30:
            case 34:
            case 38:
            case 43:
            case 47:
            case 52:
            case 63:
            case 65:
            case 67:
            case 70:
            case 72:
            case 76:
            case 78:
            case 80:
            case 84:
            case 85:
            case 86:
            case 89:
            case 91:
            case 92:
            case 95:
            case 96:
            case 97:
            case 100:
            case 101:
            case 102:
            case 107:
            case 109:
                objArr[0] = "org/jetbrains/kotlin/js/translate/utils/TranslationUtils";
                break;
            case 37:
                objArr[0] = "assignTo";
                break;
            case 39:
                objArr[0] = "declaration";
                break;
            case 46:
                objArr[0] = "block";
                break;
            case 50:
                objArr[0] = "receiver";
                break;
            case 51:
            case 105:
                objArr[0] = "arguments";
                break;
            case 54:
                objArr[0] = "ktExpression";
                break;
            case 58:
                objArr[0] = "p";
                break;
            case 61:
                objArr[0] = "property";
                break;
            case 69:
            case 108:
                objArr[0] = "name";
                break;
            case 82:
                objArr[0] = "value";
                break;
            case 83:
                objArr[0] = "to";
                break;
            case 104:
            default:
                objArr[0] = "function";
                break;
            case 106:
                objArr[0] = "specialFunction";
                break;
        }
        switch (i) {
            case 29:
            case 30:
                objArr[1] = "getNameForBackingField";
                break;
            case 34:
                objArr[1] = "backingFieldReference";
                break;
            case 38:
                objArr[1] = "assignmentToBackingField";
                break;
            case 43:
                objArr[1] = "translateBaseExpression";
                break;
            case 47:
                objArr[1] = "translateRightExpression";
                break;
            case 52:
                objArr[1] = "generateInvocationArguments";
                break;
            case 63:
                objArr[1] = "translateContinuationArgument";
                break;
            case 65:
                objArr[1] = "getEnclosingContinuationParameter";
                break;
            case 67:
                objArr[1] = "getCoroutineBaseClass";
                break;
            case 70:
                objArr[1] = "getCoroutineProperty";
                break;
            case 72:
                objArr[1] = "getCoroutineDoResumeFunction";
                break;
            case 76:
                objArr[1] = "getReturnTypeForCoercion";
                break;
            case 78:
                objArr[1] = "getAnyTypeFromSameModule";
                break;
            case 80:
                objArr[1] = "getDispatchReceiverTypeForCoercion";
                break;
            case 84:
            case 85:
            case 86:
                objArr[1] = "coerce";
                break;
            case 89:
                objArr[1] = "voidToUnit";
                break;
            case 91:
            case 92:
                objArr[1] = "unitToVoid";
                break;
            case 95:
            case 96:
            case 97:
                objArr[1] = "charToBoxedChar";
                break;
            case 100:
            case 101:
            case 102:
                objArr[1] = "boxedCharToChar";
                break;
            case 107:
                objArr[1] = "getTagForSpecialFunction";
                break;
            case 109:
                objArr[1] = "getIntrinsicFqn";
                break;
            default:
                objArr[1] = "org/jetbrains/kotlin/js/translate/utils/TranslationUtils";
                break;
        }
        switch (i) {
            case 3:
                objArr[2] = "getAccessorFunctionName";
                break;
            case 4:
            case 5:
                objArr[2] = "simpleReturnFunction";
                break;
            case 6:
            case 7:
            case 8:
                objArr[2] = "translateExtensionFunctionAsEcma5DataDescriptor";
                break;
            case 9:
                objArr[2] = "translateExclForBinaryEqualLikeExpr";
                break;
            case 10:
                objArr[2] = "isEqualLikeOperator";
                break;
            case 11:
                objArr[2] = "notOperator";
                break;
            case 12:
                objArr[2] = "isNullCheck";
                break;
            case 13:
                objArr[2] = "isNotNullCheck";
                break;
            case 14:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
                objArr[2] = "nullCheck";
                break;
            case 15:
            case 16:
            case 17:
                objArr[2] = "prepareForNullCheck";
                break;
            case 24:
            case 25:
            case 26:
                objArr[2] = "notNullConditional";
                break;
            case 27:
            case 28:
                objArr[2] = "getNameForBackingField";
                break;
            case 29:
            case 30:
            case 34:
            case 38:
            case 43:
            case 47:
            case 52:
            case 63:
            case 65:
            case 67:
            case 70:
            case 72:
            case 76:
            case 78:
            case 80:
            case 84:
            case 85:
            case 86:
            case 89:
            case 91:
            case 92:
            case 95:
            case 96:
            case 97:
            case 100:
            case 101:
            case 102:
            case 107:
            case 109:
                break;
            case 31:
                objArr[2] = "isReferenceToSyntheticBackingField";
                break;
            case 32:
            case 33:
                objArr[2] = "backingFieldReference";
                break;
            case 35:
            case 36:
            case 37:
                objArr[2] = "assignmentToBackingField";
                break;
            case 39:
            case 40:
                objArr[2] = "translateInitializerForProperty";
                break;
            case 41:
            case 42:
                objArr[2] = "translateBaseExpression";
                break;
            case 44:
            case 45:
            case 46:
                objArr[2] = "translateRightExpression";
                break;
            case 48:
            case 49:
                objArr[2] = "hasCorrespondingFunctionIntrinsic";
                break;
            case 50:
            case 51:
                objArr[2] = "generateInvocationArguments";
                break;
            case 53:
                objArr[2] = "isCacheNeeded";
                break;
            case 54:
            case 55:
            case 56:
                objArr[2] = "sure";
                break;
            case 57:
                objArr[2] = "isSimpleNameExpressionNotDelegatedLocalVar";
                break;
            case 58:
            case 59:
                objArr[2] = "propertyAccessedByFunctionsInternally";
                break;
            case 60:
            case 61:
                objArr[2] = "shouldAccessViaFunctions";
                break;
            case 62:
                objArr[2] = "translateContinuationArgument";
                break;
            case 64:
                objArr[2] = "getEnclosingContinuationParameter";
                break;
            case 66:
                objArr[2] = "getCoroutineBaseClass";
                break;
            case 68:
            case 69:
                objArr[2] = "getCoroutineProperty";
                break;
            case 71:
                objArr[2] = "getCoroutineDoResumeFunction";
                break;
            case 73:
                objArr[2] = "isOverridableFunctionWithDefaultParameters";
                break;
            case 74:
            case 75:
                objArr[2] = "getReturnTypeForCoercion";
                break;
            case 77:
                objArr[2] = "getAnyTypeFromSameModule";
                break;
            case 79:
                objArr[2] = "getDispatchReceiverTypeForCoercion";
                break;
            case 81:
            case 82:
            case 83:
                objArr[2] = "coerce";
                break;
            case 87:
            case 88:
                objArr[2] = "voidToUnit";
                break;
            case 90:
                objArr[2] = "unitToVoid";
                break;
            case 93:
            case 94:
                objArr[2] = "charToBoxedChar";
                break;
            case 98:
            case 99:
                objArr[2] = "boxedCharToChar";
                break;
            case 103:
            case 104:
            case 105:
                objArr[2] = "invokeSpecialFunction";
                break;
            case 106:
                objArr[2] = "getTagForSpecialFunction";
                break;
            case 108:
                objArr[2] = "getIntrinsicFqn";
                break;
            default:
                objArr[2] = "translateFunctionAsEcma5PropertyDescriptor";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 29:
            case 30:
            case 34:
            case 38:
            case 43:
            case 47:
            case 52:
            case 63:
            case 65:
            case 67:
            case 70:
            case 72:
            case 76:
            case 78:
            case 80:
            case 84:
            case 85:
            case 86:
            case 89:
            case 91:
            case 92:
            case 95:
            case 96:
            case 97:
            case 100:
            case 101:
            case 102:
            case 107:
            case 109:
                throw new IllegalStateException(str2);
            default:
                throw new IllegalArgumentException(str2);
        }
    }

    private TranslationUtils() {
    }

    public static JsPropertyInitializer translateFunctionAsEcma5PropertyDescriptor(JsFunction jsFunction, FunctionDescriptor functionDescriptor, TranslationContext translationContext) {
        if (jsFunction == null) {
            $$$reportNull$$$0(0);
        }
        if (functionDescriptor == null) {
            $$$reportNull$$$0(1);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(2);
        }
        JsExpression jsExpressionFunctionWithMetadata = jsFunction;
        if (InlineUtil.isInline(functionDescriptor)) {
            jsExpressionFunctionWithMetadata = InlineMetadata.compose(jsFunction, functionDescriptor, translationContext).functionWithMetadata(translationContext, PsiSourceElementKt.getPsi(functionDescriptor.getSource()));
        }
        if (DescriptorUtils.isExtension(functionDescriptor) || ((functionDescriptor instanceof PropertyAccessorDescriptor) && shouldAccessViaFunctions(((PropertyAccessorDescriptor) functionDescriptor).getCorrespondingProperty()))) {
            return translateExtensionFunctionAsEcma5DataDescriptor(jsExpressionFunctionWithMetadata, functionDescriptor, translationContext);
        }
        return new JsPropertyInitializer(new JsStringLiteral(getAccessorFunctionName(functionDescriptor)), jsExpressionFunctionWithMetadata);
    }

    private static String getAccessorFunctionName(FunctionDescriptor functionDescriptor) {
        if (functionDescriptor == null) {
            $$$reportNull$$$0(3);
        }
        return ((functionDescriptor instanceof PropertyGetterDescriptor) || (functionDescriptor instanceof LocalVariableAccessorDescriptor.Getter)) ? "get" : FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX;
    }

    public static JsFunction simpleReturnFunction(JsScope jsScope, JsExpression jsExpression) {
        if (jsScope == null) {
            $$$reportNull$$$0(4);
        }
        if (jsExpression == null) {
            $$$reportNull$$$0(5);
        }
        JsReturn jsReturn = new JsReturn(jsExpression);
        jsReturn.setSource(jsExpression.getSource());
        return new JsFunction(jsScope, new JsBlock(jsReturn), "<simpleReturnFunction>");
    }

    private static JsPropertyInitializer translateExtensionFunctionAsEcma5DataDescriptor(JsExpression jsExpression, FunctionDescriptor functionDescriptor, TranslationContext translationContext) {
        if (jsExpression == null) {
            $$$reportNull$$$0(6);
        }
        if (functionDescriptor == null) {
            $$$reportNull$$$0(7);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(8);
        }
        return new JsPropertyInitializer(translationContext.getNameForDescriptor(functionDescriptor).makeRef(), JsAstUtils.createDataDescriptor(jsExpression, ModalityUtilsKt.isOverridable(functionDescriptor), false));
    }

    public static JsExpression translateExclForBinaryEqualLikeExpr(JsBinaryOperation jsBinaryOperation) {
        if (jsBinaryOperation == null) {
            $$$reportNull$$$0(9);
        }
        return new JsBinaryOperation(notOperator(jsBinaryOperation.getOperator()), jsBinaryOperation.getArg1(), jsBinaryOperation.getArg2());
    }

    public static boolean isEqualLikeOperator(JsBinaryOperator jsBinaryOperator) {
        if (jsBinaryOperator == null) {
            $$$reportNull$$$0(10);
        }
        return notOperator(jsBinaryOperator) != null;
    }

    private static JsBinaryOperator notOperator(JsBinaryOperator jsBinaryOperator) {
        if (jsBinaryOperator == null) {
            $$$reportNull$$$0(11);
        }
        int i = AnonymousClass1.$SwitchMap$org$jetbrains$kotlin$js$backend$ast$JsBinaryOperator[jsBinaryOperator.ordinal()];
        if (i == 1) {
            return JsBinaryOperator.REF_NEQ;
        }
        if (i == 2) {
            return JsBinaryOperator.REF_EQ;
        }
        if (i == 3) {
            return JsBinaryOperator.NEQ;
        }
        if (i != 4) {
            return null;
        }
        return JsBinaryOperator.EQ;
    }

    public static JsBinaryOperation isNullCheck(JsExpression jsExpression) {
        if (jsExpression == null) {
            $$$reportNull$$$0(12);
        }
        return nullCheck(jsExpression, false);
    }

    private static JsBinaryOperation isNotNullCheck(JsExpression jsExpression) {
        if (jsExpression == null) {
            $$$reportNull$$$0(13);
        }
        return nullCheck(jsExpression, true);
    }

    public static JsBinaryOperation nullCheck(JsExpression jsExpression, boolean z) {
        if (jsExpression == null) {
            $$$reportNull$$$0(14);
        }
        return new JsBinaryOperation(z ? JsBinaryOperator.NEQ : JsBinaryOperator.EQ, jsExpression, new JsNullLiteral());
    }

    private static JsExpression prepareForNullCheck(KtExpression ktExpression, JsExpression jsExpression, TranslationContext translationContext) {
        if (ktExpression == null) {
            $$$reportNull$$$0(15);
        }
        if (jsExpression == null) {
            $$$reportNull$$$0(16);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(17);
        }
        KotlinType type = translationContext.bindingContext().getType(ktExpression);
        if (type == null) {
            type = translationContext.getCurrentModule().getBuiltIns().getAnyType();
        }
        return coerce(translationContext, jsExpression, TypeUtils.makeNullable(type));
    }

    public static JsBinaryOperation nullCheck(KtExpression ktExpression, JsExpression jsExpression, TranslationContext translationContext, boolean z) {
        if (ktExpression == null) {
            $$$reportNull$$$0(18);
        }
        if (jsExpression == null) {
            $$$reportNull$$$0(19);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(20);
        }
        return nullCheck(prepareForNullCheck(ktExpression, jsExpression, translationContext), z);
    }

    public static JsBinaryOperation nullCheck(KotlinType kotlinType, JsExpression jsExpression, TranslationContext translationContext, boolean z) {
        if (kotlinType == null) {
            $$$reportNull$$$0(21);
        }
        if (jsExpression == null) {
            $$$reportNull$$$0(22);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(23);
        }
        return nullCheck(coerce(translationContext, jsExpression, TypeUtils.makeNullable(kotlinType)), z);
    }

    public static JsConditional notNullConditional(JsExpression jsExpression, JsExpression jsExpression2, TranslationContext translationContext) {
        JsBinaryOperation jsBinaryOperationIsNotNullCheck;
        if (jsExpression == null) {
            $$$reportNull$$$0(24);
        }
        if (jsExpression2 == null) {
            $$$reportNull$$$0(25);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(26);
        }
        if (isCacheNeeded(jsExpression)) {
            TemporaryConstVariable orDeclareTemporaryConstVariable = translationContext.getOrDeclareTemporaryConstVariable(jsExpression);
            jsBinaryOperationIsNotNullCheck = isNotNullCheck(orDeclareTemporaryConstVariable.value());
            jsExpression = orDeclareTemporaryConstVariable.value();
        } else {
            jsBinaryOperationIsNotNullCheck = isNotNullCheck(jsExpression);
        }
        return new JsConditional(jsBinaryOperationIsNotNullCheck, jsExpression, jsExpression2);
    }

    public static JsName getNameForBackingField(TranslationContext translationContext, PropertyDescriptor propertyDescriptor) {
        JsName nameForDescriptor;
        if (translationContext == null) {
            $$$reportNull$$$0(27);
        }
        if (propertyDescriptor == null) {
            $$$reportNull$$$0(28);
        }
        if (isReferenceToSyntheticBackingField(propertyDescriptor)) {
            JsName nameForBackingField = translationContext.getNameForBackingField(propertyDescriptor);
            if (nameForBackingField == null) {
                $$$reportNull$$$0(29);
            }
            return nameForBackingField;
        }
        if (propertyDescriptor.getContainingDeclaration() instanceof PackageFragmentDescriptor) {
            nameForDescriptor = translationContext.getInnerNameForDescriptor(propertyDescriptor);
        } else {
            nameForDescriptor = translationContext.getNameForDescriptor(propertyDescriptor);
        }
        if (nameForDescriptor == null) {
            $$$reportNull$$$0(30);
        }
        return nameForDescriptor;
    }

    public static boolean isReferenceToSyntheticBackingField(PropertyDescriptor propertyDescriptor) {
        if (propertyDescriptor == null) {
            $$$reportNull$$$0(31);
        }
        return (JsDescriptorUtils.isSimpleFinalProperty(propertyDescriptor) || (propertyDescriptor.getContainingDeclaration() instanceof PackageFragmentDescriptor)) ? false : true;
    }

    public static JsNameRef backingFieldReference(TranslationContext translationContext, PropertyDescriptor propertyDescriptor) {
        if (translationContext == null) {
            $$$reportNull$$$0(32);
        }
        if (propertyDescriptor == null) {
            $$$reportNull$$$0(33);
        }
        DeclarationDescriptor containingDeclaration = propertyDescriptor.getContainingDeclaration();
        JsNameRef jsNameRef = new JsNameRef(getNameForBackingField(translationContext, propertyDescriptor), containingDeclaration instanceof PackageFragmentDescriptor ? null : translationContext.getDispatchReceiver(JsDescriptorUtils.getReceiverParameterForDeclaration(containingDeclaration)));
        MetadataProperties.setType(jsNameRef, getReturnTypeForCoercion(propertyDescriptor, true));
        return jsNameRef;
    }

    public static JsExpression assignmentToBackingField(TranslationContext translationContext, PropertyDescriptor propertyDescriptor, JsExpression jsExpression) {
        if (translationContext == null) {
            $$$reportNull$$$0(35);
        }
        if (propertyDescriptor == null) {
            $$$reportNull$$$0(36);
        }
        if (jsExpression == null) {
            $$$reportNull$$$0(37);
        }
        JsBinaryOperation jsBinaryOperationAssignment = JsAstUtils.assignment(backingFieldReference(translationContext, propertyDescriptor), jsExpression);
        if (jsBinaryOperationAssignment == null) {
            $$$reportNull$$$0(38);
        }
        return jsBinaryOperationAssignment;
    }

    public static JsExpression translateInitializerForProperty(KtProperty ktProperty, TranslationContext translationContext) {
        if (ktProperty == null) {
            $$$reportNull$$$0(39);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(40);
        }
        KtExpression initializer = ktProperty.getInitializer();
        if (initializer != null) {
            return coerce(translationContext, Translation.translateAsExpression(initializer, translationContext), ((VariableDescriptor) BindingContextUtils.getNotNull(translationContext.bindingContext(), BindingContext.VARIABLE, ktProperty)).getType());
        }
        return null;
    }

    public static JsExpression translateBaseExpression(TranslationContext translationContext, KtUnaryExpression ktUnaryExpression) {
        if (translationContext == null) {
            $$$reportNull$$$0(41);
        }
        if (ktUnaryExpression == null) {
            $$$reportNull$$$0(42);
        }
        JsExpression jsExpressionTranslateAsExpression = Translation.translateAsExpression(PsiUtils.getBaseExpression(ktUnaryExpression), translationContext);
        if (jsExpressionTranslateAsExpression == null) {
            $$$reportNull$$$0(43);
        }
        return jsExpressionTranslateAsExpression;
    }

    public static JsExpression translateRightExpression(TranslationContext translationContext, KtBinaryExpression ktBinaryExpression, JsBlock jsBlock) {
        if (translationContext == null) {
            $$$reportNull$$$0(44);
        }
        if (ktBinaryExpression == null) {
            $$$reportNull$$$0(45);
        }
        if (jsBlock == null) {
            $$$reportNull$$$0(46);
        }
        JsExpression jsExpressionTranslateAsExpression = Translation.translateAsExpression(ktBinaryExpression.getRight(), translationContext, jsBlock);
        if (jsExpressionTranslateAsExpression == null) {
            $$$reportNull$$$0(47);
        }
        return jsExpressionTranslateAsExpression;
    }

    public static boolean hasCorrespondingFunctionIntrinsic(TranslationContext translationContext, KtOperationExpression ktOperationExpression) {
        if (translationContext == null) {
            $$$reportNull$$$0(48);
        }
        if (ktOperationExpression == null) {
            $$$reportNull$$$0(49);
        }
        CallableDescriptor callableDescriptorForOperationExpression = BindingUtils.getCallableDescriptorForOperationExpression(translationContext.bindingContext(), ktOperationExpression);
        if (callableDescriptorForOperationExpression == null || !(callableDescriptorForOperationExpression instanceof FunctionDescriptor)) {
            return true;
        }
        KotlinType returnType = callableDescriptorForOperationExpression.getReturnType();
        return (returnType == null || !(KotlinBuiltIns.isChar(returnType) || KotlinBuiltIns.isLong(returnType) || KotlinBuiltIns.isInt(returnType))) && translationContext.intrinsics().getFunctionIntrinsic((FunctionDescriptor) callableDescriptorForOperationExpression, translationContext) != null;
    }

    public static List<JsExpression> generateInvocationArguments(JsExpression jsExpression, List<? extends JsExpression> list) {
        if (jsExpression == null) {
            $$$reportNull$$$0(50);
        }
        if (list == null) {
            $$$reportNull$$$0(51);
        }
        ArrayList arrayList = new ArrayList(list.size() + 1);
        arrayList.add(jsExpression);
        arrayList.addAll(list);
        return arrayList;
    }

    public static boolean isCacheNeeded(JsExpression jsExpression) {
        if (jsExpression == null) {
            $$$reportNull$$$0(53);
        }
        if (jsExpression instanceof JsLiteral.JsValueLiteral) {
            return false;
        }
        if ((jsExpression instanceof JsNameRef) && ((JsNameRef) jsExpression).getQualifier() == null) {
            return false;
        }
        if (jsExpression instanceof JsBinaryOperation) {
            JsBinaryOperation jsBinaryOperation = (JsBinaryOperation) jsExpression;
            JsBinaryOperator operator = jsBinaryOperation.getOperator();
            if (operator.isAssignment() || operator == JsBinaryOperator.COMMA) {
                return true;
            }
            return isCacheNeeded(jsBinaryOperation.getArg1()) || isCacheNeeded(jsBinaryOperation.getArg2());
        }
        if (!(jsExpression instanceof JsUnaryOperation)) {
            return true;
        }
        JsUnaryOperation jsUnaryOperation = (JsUnaryOperation) jsExpression;
        switch (AnonymousClass1.$SwitchMap$org$jetbrains$kotlin$js$backend$ast$JsUnaryOperator[jsUnaryOperation.getOperator().ordinal()]) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                return isCacheNeeded(jsUnaryOperation.getArg());
            default:
                return true;
        }
    }

    public static JsExpression sure(KtExpression ktExpression, JsExpression jsExpression, TranslationContext translationContext) {
        if (ktExpression == null) {
            $$$reportNull$$$0(54);
        }
        if (jsExpression == null) {
            $$$reportNull$$$0(55);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(56);
        }
        return new JsInvocation(translationContext.getReferenceToIntrinsic(Namer.NULL_CHECK_INTRINSIC_NAME), prepareForNullCheck(ktExpression, jsExpression, translationContext));
    }

    public static boolean isSimpleNameExpressionNotDelegatedLocalVar(KtExpression ktExpression, TranslationContext translationContext) {
        if (translationContext == null) {
            $$$reportNull$$$0(57);
        }
        if (!(ktExpression instanceof KtSimpleNameExpression)) {
            return false;
        }
        DeclarationDescriptor declarationDescriptor = (DeclarationDescriptor) translationContext.bindingContext().get(BindingContext.REFERENCE_TARGET, (KtSimpleNameExpression) ktExpression);
        if ((declarationDescriptor instanceof LocalVariableDescriptor) && ((LocalVariableDescriptor) declarationDescriptor).getIsDelegated()) {
            return false;
        }
        return ((declarationDescriptor instanceof PropertyDescriptor) && propertyAccessedByFunctionsInternally((PropertyDescriptor) declarationDescriptor, translationContext)) ? false : true;
    }

    private static boolean propertyAccessedByFunctionsInternally(PropertyDescriptor propertyDescriptor, TranslationContext translationContext) {
        if (propertyDescriptor == null) {
            $$$reportNull$$$0(58);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(59);
        }
        return (!JsDescriptorUtils.isSimpleFinalProperty(propertyDescriptor) && translationContext.isFromCurrentModule(propertyDescriptor)) || shouldAccessViaFunctions(propertyDescriptor);
    }

    public static boolean shouldAccessViaFunctions(CallableDescriptor callableDescriptor) {
        if (callableDescriptor == null) {
            $$$reportNull$$$0(60);
        }
        if (callableDescriptor instanceof PropertyDescriptor) {
            return shouldAccessViaFunctions((PropertyDescriptor) callableDescriptor);
        }
        if (callableDescriptor instanceof PropertyAccessorDescriptor) {
            return shouldAccessViaFunctions(((PropertyAccessorDescriptor) callableDescriptor).getCorrespondingProperty());
        }
        return false;
    }

    private static boolean shouldAccessViaFunctions(PropertyDescriptor propertyDescriptor) {
        if (propertyDescriptor == null) {
            $$$reportNull$$$0(61);
        }
        if (AnnotationsUtils.hasJsNameInAccessors(propertyDescriptor)) {
            return true;
        }
        Iterator<? extends PropertyDescriptor> it = propertyDescriptor.getOverriddenDescriptors().iterator();
        while (it.hasNext()) {
            if (shouldAccessViaFunctions(it.next())) {
                return true;
            }
        }
        return false;
    }

    public static JsExpression translateContinuationArgument(TranslationContext translationContext) {
        if (translationContext == null) {
            $$$reportNull$$$0(62);
        }
        JsExpression jsExpressionTranslateAsValueReference = ReferenceTranslator.translateAsValueReference(getEnclosingContinuationParameter(translationContext), translationContext);
        if (jsExpressionTranslateAsValueReference == null) {
            $$$reportNull$$$0(63);
        }
        return jsExpressionTranslateAsValueReference;
    }

    public static ValueParameterDescriptor getEnclosingContinuationParameter(TranslationContext translationContext) {
        if (translationContext == null) {
            $$$reportNull$$$0(64);
        }
        ValueParameterDescriptor continuationParameterDescriptor = translationContext.getContinuationParameterDescriptor();
        if (continuationParameterDescriptor == null) {
            continuationParameterDescriptor = getEnclosingContinuationParameter(translationContext.getParent());
        }
        if (continuationParameterDescriptor == null) {
            $$$reportNull$$$0(65);
        }
        return continuationParameterDescriptor;
    }

    public static ClassDescriptor getCoroutineBaseClass(TranslationContext translationContext) {
        if (translationContext == null) {
            $$$reportNull$$$0(66);
        }
        ClassDescriptor classDescriptorFindClassAcrossModuleDependencies = FindClassInModuleKt.findClassAcrossModuleDependencies(translationContext.getCurrentModule(), ClassId.topLevel(StandardNames.COROUTINES_PACKAGE_FQ_NAME.child(Name.identifier("CoroutineImpl"))));
        if (classDescriptorFindClassAcrossModuleDependencies == null) {
            $$$reportNull$$$0(67);
        }
        return classDescriptorFindClassAcrossModuleDependencies;
    }

    public static PropertyDescriptor getCoroutineProperty(TranslationContext translationContext, String str) {
        if (translationContext == null) {
            $$$reportNull$$$0(68);
        }
        if (str == null) {
            $$$reportNull$$$0(69);
        }
        PropertyDescriptor next = getCoroutineBaseClass(translationContext).getUnsubstitutedMemberScope().getContributedVariables(Name.identifier(str), NoLookupLocation.FROM_DESERIALIZATION).iterator().next();
        if (next == null) {
            $$$reportNull$$$0(70);
        }
        return next;
    }

    public static FunctionDescriptor getCoroutineDoResumeFunction(TranslationContext translationContext) {
        if (translationContext == null) {
            $$$reportNull$$$0(71);
        }
        SimpleFunctionDescriptor next = getCoroutineBaseClass(translationContext).getUnsubstitutedMemberScope().getContributedFunctions(Name.identifier("doResume"), NoLookupLocation.FROM_DESERIALIZATION).iterator().next();
        if (next == null) {
            $$$reportNull$$$0(72);
        }
        return next;
    }

    public static boolean isOverridableFunctionWithDefaultParameters(FunctionDescriptor functionDescriptor) {
        if (functionDescriptor == null) {
            $$$reportNull$$$0(73);
        }
        return UtilsKt.hasOrInheritsParametersWithDefaultValue(functionDescriptor) && !(functionDescriptor instanceof ConstructorDescriptor) && (functionDescriptor.getContainingDeclaration() instanceof ClassDescriptor) && ModalityUtilsKt.isOverridable(functionDescriptor);
    }

    public static KotlinType getReturnTypeForCoercion(CallableDescriptor callableDescriptor) {
        if (callableDescriptor == null) {
            $$$reportNull$$$0(74);
        }
        return getReturnTypeForCoercion(callableDescriptor, false);
    }

    public static KotlinType getReturnTypeForCoercion(CallableDescriptor callableDescriptor, final boolean z) {
        if (callableDescriptor == null) {
            $$$reportNull$$$0(75);
        }
        CallableDescriptor original = callableDescriptor.getOriginal();
        if (FunctionTypesKt.getFunctionTypeKind(original) != null || (original instanceof AnonymousFunctionDescriptor)) {
            return getAnyTypeFromSameModule(original);
        }
        Collection<? extends CallableDescriptor> overriddenDescriptors = original.getOverriddenDescriptors();
        if (overriddenDescriptors.isEmpty()) {
            KotlinType returnType = original.getReturnType();
            if (returnType == null) {
                return getAnyTypeFromSameModule(original);
            }
            DeclarationDescriptor containingDeclaration = original.getContainingDeclaration();
            boolean z2 = EffectiveVisibilityUtilsKt.effectiveVisibility(original.getVisibility(), (DeclarationDescriptor) original, true).getPublicApi() && !z;
            if (KotlinBuiltIns.isCharOrNullableChar(returnType) && (containingDeclaration instanceof ClassDescriptor) && z2) {
                ClassDescriptor classDescriptor = (ClassDescriptor) containingDeclaration;
                FqNameUnsafe fqNameUnsafe = DescriptorUtilsKt.getFqNameUnsafe(classDescriptor);
                if (!CLASSES_WITH_NON_BOXED_CHARS.contains(fqNameUnsafe) && !KotlinBuiltIns.isPrimitiveType(classDescriptor.getDefaultType()) && !StandardNames.isPrimitiveArray(fqNameUnsafe)) {
                    return getAnyTypeFromSameModule(original);
                }
            }
            if (returnType == null) {
                $$$reportNull$$$0(76);
            }
            return returnType;
        }
        Set set = (Set) overriddenDescriptors.stream().map(new Function() { // from class: org.jetbrains.kotlin.js.translate.utils.TranslationUtils$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return TranslationUtils.getReturnTypeForCoercion((CallableDescriptor) obj, z);
            }
        }).collect(Collectors.toSet());
        if (set.size() == 1) {
            return (KotlinType) set.iterator().next();
        }
        return getAnyTypeFromSameModule(original);
    }

    private static KotlinType getAnyTypeFromSameModule(DeclarationDescriptor declarationDescriptor) {
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(77);
        }
        SimpleType anyType = DescriptorUtils.getContainingModule(declarationDescriptor).getBuiltIns().getAnyType();
        if (anyType == null) {
            $$$reportNull$$$0(78);
        }
        return anyType;
    }

    public static KotlinType getDispatchReceiverTypeForCoercion(CallableDescriptor callableDescriptor) {
        if (callableDescriptor == null) {
            $$$reportNull$$$0(79);
        }
        CallableDescriptor original = callableDescriptor.getOriginal();
        if (original.mo6753getDispatchReceiverParameter() == null) {
            throw new IllegalArgumentException("This method can only be used for class members; given descriptor is not a member of a class " + original);
        }
        Collection<? extends CallableDescriptor> overriddenDescriptors = original.getOverriddenDescriptors();
        if (overriddenDescriptors.isEmpty()) {
            KotlinType type = original.mo6753getDispatchReceiverParameter().getType();
            if (type == null) {
                $$$reportNull$$$0(80);
            }
            return type;
        }
        Set set = (Set) overriddenDescriptors.stream().map(new Function() { // from class: org.jetbrains.kotlin.js.translate.utils.TranslationUtils$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return TranslationUtils.getDispatchReceiverTypeForCoercion((CallableDescriptor) obj);
            }
        }).collect(Collectors.toSet());
        if (set.size() == 1) {
            return (KotlinType) set.iterator().next();
        }
        return getAnyTypeFromSameModule(original);
    }

    public static JsExpression coerce(TranslationContext translationContext, JsExpression jsExpression, KotlinType kotlinType) {
        JsExpression jsExpressionVoidToUnit;
        JsExpression jsNew;
        JsExpression jsExpression2;
        if (translationContext == null) {
            $$$reportNull$$$0(81);
        }
        if (jsExpression == null) {
            $$$reportNull$$$0(82);
        }
        if (kotlinType == null) {
            $$$reportNull$$$0(83);
        }
        if (DynamicTypesKt.isDynamic(kotlinType)) {
            if (jsExpression == null) {
                $$$reportNull$$$0(84);
            }
            return jsExpression;
        }
        KotlinType type = MetadataProperties.getType(jsExpression);
        if (type == null) {
            type = translationContext.getCurrentModule().getBuiltIns().getAnyType();
        }
        if (type.equals(kotlinType)) {
            if (jsExpression == null) {
                $$$reportNull$$$0(85);
            }
            return jsExpression;
        }
        if (KotlinBuiltIns.isCharOrNullableChar(kotlinType)) {
            if (!KotlinBuiltIns.isCharOrNullableChar(type) && !(jsExpression instanceof JsNullLiteral)) {
                jsExpressionVoidToUnit = jsExpression;
                jsExpressionVoidToUnit = jsExpression;
                jsExpressionVoidToUnit = boxedCharToChar(translationContext, jsExpression);
            }
        } else if (KotlinBuiltIns.isUnit(kotlinType)) {
            if (!KotlinBuiltIns.isUnit(type)) {
                jsExpressionVoidToUnit = jsExpression;
                jsExpressionVoidToUnit = unitToVoid(jsExpression);
            }
        } else if (KotlinBuiltIns.isCharOrNullableChar(type)) {
            if (!KotlinBuiltIns.isCharOrNullableChar(kotlinType) && !(jsExpression instanceof JsNullLiteral)) {
                jsExpressionVoidToUnit = jsExpression;
                jsExpressionVoidToUnit = jsExpression;
                jsExpressionVoidToUnit = charToBoxedChar(translationContext, jsExpression);
            }
        } else if (KotlinBuiltIns.isUnit(type) && !KotlinBuiltIns.isUnit(kotlinType) && !MetadataProperties.isUnit(jsExpression)) {
            jsExpressionVoidToUnit = jsExpression;
            jsExpressionVoidToUnit = jsExpression;
            jsExpressionVoidToUnit = jsExpression;
            jsExpressionVoidToUnit = voidToUnit(translationContext, jsExpression);
        }
        jsExpressionVoidToUnit = jsExpression;
        jsExpressionVoidToUnit = jsExpression;
        jsExpressionVoidToUnit = jsExpression;
        jsExpressionVoidToUnit = jsExpression;
        jsExpressionVoidToUnit = jsExpression;
        jsExpressionVoidToUnit = jsExpression;
        jsExpressionVoidToUnit = jsExpression;
        jsExpressionVoidToUnit = jsExpression;
        jsExpressionVoidToUnit = jsExpression;
        jsExpressionVoidToUnit = jsExpression;
        jsExpressionVoidToUnit = jsExpression;
        jsExpressionVoidToUnit = jsExpression;
        jsExpressionVoidToUnit = jsExpression;
        PrimitiveType primitiveTypeUnsignedPrimitiveToSigned = ArrayFIF.INSTANCE.unsignedPrimitiveToSigned(kotlinType);
        JsExpression jsNew2 = jsExpressionVoidToUnit;
        if (primitiveTypeUnsignedPrimitiveToSigned != null && KotlinBuiltIns.isInt(type)) {
            int i = AnonymousClass1.$SwitchMap$org$jetbrains$kotlin$builtins$PrimitiveType[primitiveTypeUnsignedPrimitiveToSigned.ordinal()];
            if (i == 1) {
                jsNew2 = jsExpressionVoidToUnit;
                jsExpression2 = AstUtilsKt.toByte(translationContext, jsExpressionVoidToUnit);
            } else if (i == 2) {
                jsNew2 = jsExpressionVoidToUnit;
                jsExpression2 = jsExpressionVoidToUnit;
                jsExpression2 = AstUtilsKt.toShort(translationContext, jsExpressionVoidToUnit);
            }
            jsNew2 = jsExpressionVoidToUnit;
            jsExpression2 = jsExpressionVoidToUnit;
            ClassifierDescriptor this$0 = kotlinType.getConstructor().getThis$0();
            jsNew2 = jsExpression2;
            if (this$0 instanceof ClassDescriptor) {
                jsNew2 = new JsNew(ReferenceTranslator.translateAsTypeReference((ClassDescriptor) this$0, translationContext), Collections.singletonList(jsExpression2));
            }
        }
        jsNew2 = jsExpressionVoidToUnit;
        if (FunctionTypesKt.isFunctionTypeOrSubtype(type) || FunctionTypesKt.isSuspendFunctionTypeOrSubtype(type)) {
            jsNew = jsNew2;
            ClassifierDescriptor this$1 = kotlinType.getConstructor().getThis$0();
            jsNew = jsNew2;
            if ((this$1 instanceof ClassDescriptor) && ((ClassDescriptor) this$1).isFun()) {
                JsName inlineableInnerNameForDescriptor = translationContext.getInlineableInnerNameForDescriptor(this$1.getOriginal());
                if (kotlinType.getIsMarkedNullable()) {
                    jsNew = jsNew2;
                    JsConditional jsConditionalNotNullConditional = notNullConditional(jsNew2, new JsNullLiteral(), translationContext);
                    jsConditionalNotNullConditional.setThenExpression(new JsNew(new JsNameRef(inlineableInnerNameForDescriptor), Collections.singletonList(jsConditionalNotNullConditional.getThenExpression())));
                    jsNew = jsConditionalNotNullConditional;
                } else {
                    jsNew = jsNew2;
                    jsNew = new JsNew(new JsNameRef(inlineableInnerNameForDescriptor), Collections.singletonList(jsNew2));
                }
            }
        }
        jsNew = jsNew2;
        jsNew = jsNew2;
        MetadataProperties.setType(jsNew, kotlinType);
        if (jsNew == null) {
            $$$reportNull$$$0(86);
        }
        return jsNew;
    }

    private static JsExpression voidToUnit(TranslationContext translationContext, JsExpression jsExpression) {
        if (translationContext == null) {
            $$$reportNull$$$0(87);
        }
        if (jsExpression == null) {
            $$$reportNull$$$0(88);
        }
        JsExpression jsExpressionNewSequence = JsAstUtils.newSequence(Arrays.asList(jsExpression, ReferenceTranslator.translateAsValueReference(translationContext.getCurrentModule().getBuiltIns().getUnit(), translationContext)));
        if (jsExpressionNewSequence == null) {
            $$$reportNull$$$0(89);
        }
        return jsExpressionNewSequence;
    }

    private static JsExpression unitToVoid(JsExpression jsExpression) {
        if (jsExpression == null) {
            $$$reportNull$$$0(90);
        }
        if (jsExpression instanceof JsBinaryOperation) {
            JsBinaryOperation jsBinaryOperation = (JsBinaryOperation) jsExpression;
            if (jsBinaryOperation.getOperator() == JsBinaryOperator.COMMA && MetadataProperties.isUnit(jsBinaryOperation.getArg2())) {
                JsExpression arg1 = jsBinaryOperation.getArg1();
                if (arg1 == null) {
                    $$$reportNull$$$0(91);
                }
                return arg1;
            }
        }
        if (jsExpression == null) {
            $$$reportNull$$$0(92);
        }
        return jsExpression;
    }

    public static JsExpression charToBoxedChar(TranslationContext translationContext, JsExpression jsExpression) {
        if (translationContext == null) {
            $$$reportNull$$$0(93);
        }
        if (jsExpression == null) {
            $$$reportNull$$$0(94);
        }
        if (jsExpression instanceof JsInvocation) {
            JsInvocation jsInvocation = (JsInvocation) jsExpression;
            int i = AnonymousClass1.$SwitchMap$org$jetbrains$kotlin$js$backend$ast$metadata$BoxingKind[MetadataProperties.getBoxing(jsInvocation).ordinal()];
            if (i == 1) {
                JsExpression jsExpression2 = jsInvocation.getArguments().get(0);
                if (jsExpression2 == null) {
                    $$$reportNull$$$0(95);
                }
                return jsExpression2;
            }
            if (i == 2) {
                if (jsExpression == null) {
                    $$$reportNull$$$0(96);
                }
                return jsExpression;
            }
        }
        JsInvocation jsInvocationInvokeSpecialFunction = invokeSpecialFunction(translationContext, SpecialFunction.TO_BOXED_CHAR, jsExpression);
        jsInvocationInvokeSpecialFunction.setSource(jsExpression.getSource());
        MetadataProperties.setBoxing(jsInvocationInvokeSpecialFunction, BoxingKind.BOXING);
        if (jsInvocationInvokeSpecialFunction == null) {
            $$$reportNull$$$0(97);
        }
        return jsInvocationInvokeSpecialFunction;
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.js.translate.utils.TranslationUtils$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$jetbrains$kotlin$builtins$PrimitiveType;
        static final /* synthetic */ int[] $SwitchMap$org$jetbrains$kotlin$js$backend$ast$JsBinaryOperator;
        static final /* synthetic */ int[] $SwitchMap$org$jetbrains$kotlin$js$backend$ast$JsUnaryOperator;
        static final /* synthetic */ int[] $SwitchMap$org$jetbrains$kotlin$js$backend$ast$metadata$BoxingKind;

        static {
            int[] iArr = new int[BoxingKind.values().length];
            $SwitchMap$org$jetbrains$kotlin$js$backend$ast$metadata$BoxingKind = iArr;
            try {
                iArr[BoxingKind.UNBOXING.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$js$backend$ast$metadata$BoxingKind[BoxingKind.BOXING.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$js$backend$ast$metadata$BoxingKind[BoxingKind.NONE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            int[] iArr2 = new int[PrimitiveType.values().length];
            $SwitchMap$org$jetbrains$kotlin$builtins$PrimitiveType = iArr2;
            try {
                iArr2[PrimitiveType.BYTE.ordinal()] = 1;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$builtins$PrimitiveType[PrimitiveType.SHORT.ordinal()] = 2;
            } catch (NoSuchFieldError unused5) {
            }
            int[] iArr3 = new int[JsUnaryOperator.values().length];
            $SwitchMap$org$jetbrains$kotlin$js$backend$ast$JsUnaryOperator = iArr3;
            try {
                iArr3[JsUnaryOperator.BIT_NOT.ordinal()] = 1;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$js$backend$ast$JsUnaryOperator[JsUnaryOperator.NEG.ordinal()] = 2;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$js$backend$ast$JsUnaryOperator[JsUnaryOperator.POS.ordinal()] = 3;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$js$backend$ast$JsUnaryOperator[JsUnaryOperator.NOT.ordinal()] = 4;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$js$backend$ast$JsUnaryOperator[JsUnaryOperator.TYPEOF.ordinal()] = 5;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$js$backend$ast$JsUnaryOperator[JsUnaryOperator.VOID.ordinal()] = 6;
            } catch (NoSuchFieldError unused11) {
            }
            int[] iArr4 = new int[JsBinaryOperator.values().length];
            $SwitchMap$org$jetbrains$kotlin$js$backend$ast$JsBinaryOperator = iArr4;
            try {
                iArr4[JsBinaryOperator.REF_EQ.ordinal()] = 1;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$js$backend$ast$JsBinaryOperator[JsBinaryOperator.REF_NEQ.ordinal()] = 2;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$js$backend$ast$JsBinaryOperator[JsBinaryOperator.EQ.ordinal()] = 3;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$js$backend$ast$JsBinaryOperator[JsBinaryOperator.NEQ.ordinal()] = 4;
            } catch (NoSuchFieldError unused15) {
            }
        }
    }

    private static JsExpression boxedCharToChar(TranslationContext translationContext, JsExpression jsExpression) {
        if (translationContext == null) {
            $$$reportNull$$$0(98);
        }
        if (jsExpression == null) {
            $$$reportNull$$$0(99);
        }
        if (jsExpression instanceof JsInvocation) {
            JsInvocation jsInvocation = (JsInvocation) jsExpression;
            int i = AnonymousClass1.$SwitchMap$org$jetbrains$kotlin$js$backend$ast$metadata$BoxingKind[MetadataProperties.getBoxing(jsInvocation).ordinal()];
            if (i == 1) {
                if (jsExpression == null) {
                    $$$reportNull$$$0(101);
                }
                return jsExpression;
            }
            if (i == 2) {
                JsExpression jsExpression2 = jsInvocation.getArguments().get(0);
                if (jsExpression2 == null) {
                    $$$reportNull$$$0(100);
                }
                return jsExpression2;
            }
        }
        JsInvocation jsInvocationInvokeSpecialFunction = invokeSpecialFunction(translationContext, SpecialFunction.UNBOX_CHAR, jsExpression);
        jsInvocationInvokeSpecialFunction.setSource(jsExpression.getSource());
        MetadataProperties.setBoxing(jsInvocationInvokeSpecialFunction, BoxingKind.UNBOXING);
        if (jsInvocationInvokeSpecialFunction == null) {
            $$$reportNull$$$0(102);
        }
        return jsInvocationInvokeSpecialFunction;
    }

    public static JsInvocation invokeSpecialFunction(TranslationContext translationContext, SpecialFunction specialFunction, JsExpression... jsExpressionArr) {
        if (translationContext == null) {
            $$$reportNull$$$0(103);
        }
        if (specialFunction == null) {
            $$$reportNull$$$0(104);
        }
        if (jsExpressionArr == null) {
            $$$reportNull$$$0(105);
        }
        return new JsInvocation(JsAstUtils.pureFqn(translationContext.getNameForSpecialFunction(specialFunction), (JsExpression) null), jsExpressionArr);
    }

    public static String getTagForSpecialFunction(SpecialFunction specialFunction) {
        if (specialFunction == null) {
            $$$reportNull$$$0(106);
        }
        String str = "special:" + specialFunction.name();
        if (str == null) {
            $$$reportNull$$$0(107);
        }
        return str;
    }

    public static JsExpression getIntrinsicFqn(String str) {
        if (str == null) {
            $$$reportNull$$$0(108);
        }
        JsNameRef jsNameRefPureFqn = JsAstUtils.pureFqn("Kotlin", (JsExpression) null);
        Iterator<String> it = StringUtil.split(str, ".").iterator();
        while (it.hasNext()) {
            jsNameRefPureFqn = JsAstUtils.pureFqn(it.next(), jsNameRefPureFqn);
        }
        if (jsNameRefPureFqn == null) {
            $$$reportNull$$$0(109);
        }
        return jsNameRefPureFqn;
    }
}
