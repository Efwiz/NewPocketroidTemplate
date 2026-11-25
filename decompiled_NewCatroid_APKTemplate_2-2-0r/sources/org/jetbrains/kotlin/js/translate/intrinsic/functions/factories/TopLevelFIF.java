package org.jetbrains.kotlin.js.translate.intrinsic.functions.factories;

import java.util.List;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.incremental.IncrementalJvmCacheKt;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperation;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsInvocation;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.metadata.MetadataProperties;
import org.jetbrains.kotlin.js.patterns.DescriptorPredicate;
import org.jetbrains.kotlin.js.patterns.NamePredicate;
import org.jetbrains.kotlin.js.patterns.PatternBuilder;
import org.jetbrains.kotlin.js.translate.callTranslator.CallInfo;
import org.jetbrains.kotlin.js.translate.callTranslator.CallInfoExtensionsKt;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.js.translate.intrinsic.functions.basic.FunctionIntrinsic;
import org.jetbrains.kotlin.js.translate.intrinsic.functions.basic.FunctionIntrinsicWithReceiverComputed;
import org.jetbrains.kotlin.js.translate.utils.JsAstUtils;
import org.jetbrains.kotlin.js.translate.utils.JsDescriptorUtils;
import org.jetbrains.kotlin.js.translate.utils.TranslationUtils;
import org.jetbrains.kotlin.js.translate.utils.UtilsKt;
import org.jetbrains.kotlin.psi.KtCallableReferenceExpression;
import org.jetbrains.kotlin.psi.KtPsiUtil;
import org.jetbrains.kotlin.resolve.DescriptorFactory;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.resolve.scopes.receivers.ExpressionReceiver;

/* JADX INFO: loaded from: classes5.dex */
public final class TopLevelFIF extends CompositeFIF {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final FunctionIntrinsic CHAR_TO_STRING;
    private static final FunctionIntrinsic ENUM_VALUES_INTRINSIC;
    private static final FunctionIntrinsic ENUM_VALUE_OF_INTRINSIC;
    public static final DescriptorPredicate EQUALS_IN_ANY;
    private static final DescriptorPredicate HASH_CODE_IN_ANY;
    public static final FunctionIntrinsicFactory INSTANCE;
    private static final FunctionIntrinsic JS_CLASS_FUN_INTRINSIC;
    private static final KotlinAliasedFunctionIntrinsic KOTLIN_ANY_EQUALS;
    public static final KotlinAliasedFunctionIntrinsic KOTLIN_EQUALS;
    private static final KotlinAliasedFunctionIntrinsic KOTLIN_HASH_CODE;
    private static final KotlinFunctionIntrinsic KOTLIN_SUBSEQUENCE;
    private static final FunctionIntrinsic LATEINIT_KPROPERTY_ISINITIALIZED_INTRINSIC;
    private static final FunctionIntrinsic RETURN_RECEIVER_INTRINSIC;
    private static final FunctionIntrinsic STRING_SUBSTRING;
    public static final KotlinAliasedFunctionIntrinsic TO_STRING;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 1) {
            objArr[0] = "callInfo";
        } else {
            objArr[0] = "context";
        }
        objArr[1] = "org/jetbrains/kotlin/js/translate/intrinsic/functions/factories/TopLevelFIF";
        objArr[2] = "getReferenceToOnlyTypeParameter";
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    static {
        String str = Namer.EQUALS_METHOD_NAME;
        EQUALS_IN_ANY = PatternBuilder.pattern(IncrementalJvmCacheKt.KOTLIN_CACHE_DIRECTORY_NAME, "Any", Namer.EQUALS_METHOD_NAME);
        KOTLIN_ANY_EQUALS = new KotlinAliasedFunctionIntrinsic(str) { // from class: org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.TopLevelFIF.1
            static final /* synthetic */ boolean $assertionsDisabled = false;

            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                String str2 = (i == 3 || i == 4) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                Object[] objArr = new Object[(i == 3 || i == 4) ? 2 : 3];
                if (i == 1) {
                    objArr[0] = "arguments";
                } else if (i == 2) {
                    objArr[0] = "context";
                } else if (i == 3 || i == 4) {
                    objArr[0] = "org/jetbrains/kotlin/js/translate/intrinsic/functions/factories/TopLevelFIF$1";
                } else {
                    objArr[0] = "callInfo";
                }
                if (i == 3 || i == 4) {
                    objArr[1] = "apply";
                } else {
                    objArr[1] = "org/jetbrains/kotlin/js/translate/intrinsic/functions/factories/TopLevelFIF$1";
                }
                if (i != 3 && i != 4) {
                    objArr[2] = "apply";
                }
                String str3 = String.format(str2, objArr);
                if (i != 3 && i != 4) {
                    throw new IllegalArgumentException(str3);
                }
                throw new IllegalStateException(str3);
            }

            @Override // org.jetbrains.kotlin.js.translate.intrinsic.functions.basic.FunctionIntrinsicWithReceiverComputed, org.jetbrains.kotlin.js.translate.intrinsic.functions.basic.FunctionIntrinsic
            public JsExpression apply(CallInfo callInfo, List<? extends JsExpression> list, TranslationContext translationContext) {
                if (callInfo == null) {
                    $$$reportNull$$$0(0);
                }
                if (list == null) {
                    $$$reportNull$$$0(1);
                }
                if (translationContext == null) {
                    $$$reportNull$$$0(2);
                }
                if (CallInfoExtensionsKt.isSuperInvocation(callInfo)) {
                    JsBinaryOperation jsBinaryOperationEquality = JsAstUtils.equality(callInfo.getDispatchReceiver(), list.get(0));
                    if (jsBinaryOperationEquality == null) {
                        $$$reportNull$$$0(3);
                    }
                    return jsBinaryOperationEquality;
                }
                JsExpression jsExpressionApply = super.apply(callInfo, list, translationContext);
                if (jsExpressionApply == null) {
                    $$$reportNull$$$0(4);
                }
                return jsExpressionApply;
            }
        };
        KOTLIN_EQUALS = new KotlinAliasedFunctionIntrinsic(Namer.EQUALS_METHOD_NAME);
        KOTLIN_SUBSEQUENCE = new KotlinFunctionIntrinsic("subSequence", new JsExpression[0]);
        HASH_CODE_IN_ANY = PatternBuilder.pattern(IncrementalJvmCacheKt.KOTLIN_CACHE_DIRECTORY_NAME, "Any", "hashCode");
        KOTLIN_HASH_CODE = new KotlinAliasedFunctionIntrinsic("hashCode");
        RETURN_RECEIVER_INTRINSIC = new FunctionIntrinsicWithReceiverComputed() { // from class: org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.TopLevelFIF.2
            static final /* synthetic */ boolean $assertionsDisabled = false;

            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                String str2 = i != 2 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
                Object[] objArr = new Object[i != 2 ? 3 : 2];
                if (i == 1) {
                    objArr[0] = "context";
                } else if (i != 2) {
                    objArr[0] = "arguments";
                } else {
                    objArr[0] = "org/jetbrains/kotlin/js/translate/intrinsic/functions/factories/TopLevelFIF$2";
                }
                if (i != 2) {
                    objArr[1] = "org/jetbrains/kotlin/js/translate/intrinsic/functions/factories/TopLevelFIF$2";
                } else {
                    objArr[1] = "apply";
                }
                if (i != 2) {
                    objArr[2] = "apply";
                }
                String str3 = String.format(str2, objArr);
                if (i == 2) {
                    throw new IllegalStateException(str3);
                }
            }

            @Override // org.jetbrains.kotlin.js.translate.intrinsic.functions.basic.FunctionIntrinsicWithReceiverComputed
            public JsExpression apply(JsExpression jsExpression, List<? extends JsExpression> list, TranslationContext translationContext) {
                if (list == null) {
                    $$$reportNull$$$0(0);
                }
                if (translationContext == null) {
                    $$$reportNull$$$0(1);
                }
                if (jsExpression == null) {
                    $$$reportNull$$$0(2);
                }
                return jsExpression;
            }
        };
        JS_CLASS_FUN_INTRINSIC = new FunctionIntrinsic() { // from class: org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.TopLevelFIF.3
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                String str2 = i != 3 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
                Object[] objArr = new Object[i != 3 ? 3 : 2];
                if (i == 1) {
                    objArr[0] = "arguments";
                } else if (i == 2) {
                    objArr[0] = "context";
                } else if (i != 3) {
                    objArr[0] = "callInfo";
                } else {
                    objArr[0] = "org/jetbrains/kotlin/js/translate/intrinsic/functions/factories/TopLevelFIF$3";
                }
                if (i != 3) {
                    objArr[1] = "org/jetbrains/kotlin/js/translate/intrinsic/functions/factories/TopLevelFIF$3";
                } else {
                    objArr[1] = "apply";
                }
                if (i != 3) {
                    objArr[2] = "apply";
                }
                String str3 = String.format(str2, objArr);
                if (i == 3) {
                    throw new IllegalStateException(str3);
                }
            }

            @Override // org.jetbrains.kotlin.js.translate.intrinsic.functions.basic.FunctionIntrinsic
            public JsExpression apply(CallInfo callInfo, List<? extends JsExpression> list, TranslationContext translationContext) {
                if (callInfo == null) {
                    $$$reportNull$$$0(0);
                }
                if (list == null) {
                    $$$reportNull$$$0(1);
                }
                if (translationContext == null) {
                    $$$reportNull$$$0(2);
                }
                JsExpression referenceToOnlyTypeParameter = TopLevelFIF.getReferenceToOnlyTypeParameter(callInfo, translationContext);
                if (referenceToOnlyTypeParameter == null) {
                    $$$reportNull$$$0(3);
                }
                return referenceToOnlyTypeParameter;
            }
        };
        ENUM_VALUES_INTRINSIC = new FunctionIntrinsic() { // from class: org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.TopLevelFIF.4
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                Object[] objArr = new Object[3];
                if (i == 1) {
                    objArr[0] = "arguments";
                } else if (i != 2) {
                    objArr[0] = "callInfo";
                } else {
                    objArr[0] = "context";
                }
                objArr[1] = "org/jetbrains/kotlin/js/translate/intrinsic/functions/factories/TopLevelFIF$4";
                objArr[2] = "apply";
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
            }

            @Override // org.jetbrains.kotlin.js.translate.intrinsic.functions.basic.FunctionIntrinsic
            public JsExpression apply(CallInfo callInfo, List<? extends JsExpression> list, TranslationContext translationContext) {
                if (callInfo == null) {
                    $$$reportNull$$$0(0);
                }
                if (list == null) {
                    $$$reportNull$$$0(1);
                }
                if (translationContext == null) {
                    $$$reportNull$$$0(2);
                }
                return new JsInvocation(new JsNameRef(translationContext.getNameForDescriptor(DescriptorFactory.createEnumValuesMethod(translationContext.getCurrentModule().getBuiltIns().getEnum())), TopLevelFIF.getReferenceToOnlyTypeParameter(callInfo, translationContext)), new JsExpression[0]);
            }
        };
        ENUM_VALUE_OF_INTRINSIC = new FunctionIntrinsic() { // from class: org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.TopLevelFIF.5
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                Object[] objArr = new Object[3];
                if (i == 1) {
                    objArr[0] = "arguments";
                } else if (i != 2) {
                    objArr[0] = "callInfo";
                } else {
                    objArr[0] = "context";
                }
                objArr[1] = "org/jetbrains/kotlin/js/translate/intrinsic/functions/factories/TopLevelFIF$5";
                objArr[2] = "apply";
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
            }

            @Override // org.jetbrains.kotlin.js.translate.intrinsic.functions.basic.FunctionIntrinsic
            public JsExpression apply(CallInfo callInfo, List<? extends JsExpression> list, TranslationContext translationContext) {
                if (callInfo == null) {
                    $$$reportNull$$$0(0);
                }
                if (list == null) {
                    $$$reportNull$$$0(1);
                }
                if (translationContext == null) {
                    $$$reportNull$$$0(2);
                }
                return new JsInvocation(new JsNameRef(translationContext.getNameForDescriptor(DescriptorFactory.createEnumValueOfMethod(translationContext.getCurrentModule().getBuiltIns().getEnum())), TopLevelFIF.getReferenceToOnlyTypeParameter(callInfo, translationContext)), list.get(2));
            }
        };
        LATEINIT_KPROPERTY_ISINITIALIZED_INTRINSIC = new FunctionIntrinsic() { // from class: org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.TopLevelFIF.6
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                String str2 = i != 3 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
                Object[] objArr = new Object[i != 3 ? 3 : 2];
                if (i == 1) {
                    objArr[0] = "arguments";
                } else if (i == 2) {
                    objArr[0] = "context";
                } else if (i != 3) {
                    objArr[0] = "callInfo";
                } else {
                    objArr[0] = "org/jetbrains/kotlin/js/translate/intrinsic/functions/factories/TopLevelFIF$6";
                }
                if (i != 3) {
                    objArr[1] = "org/jetbrains/kotlin/js/translate/intrinsic/functions/factories/TopLevelFIF$6";
                } else {
                    objArr[1] = "apply";
                }
                if (i != 3) {
                    objArr[2] = "apply";
                }
                String str3 = String.format(str2, objArr);
                if (i == 3) {
                    throw new IllegalStateException(str3);
                }
            }

            @Override // org.jetbrains.kotlin.js.translate.intrinsic.functions.basic.FunctionIntrinsic
            public JsExpression apply(CallInfo callInfo, List<? extends JsExpression> list, TranslationContext translationContext) {
                if (callInfo == null) {
                    $$$reportNull$$$0(0);
                }
                if (list == null) {
                    $$$reportNull$$$0(1);
                }
                if (translationContext == null) {
                    $$$reportNull$$$0(2);
                }
                PropertyDescriptor propertyDescriptor = (PropertyDescriptor) CallUtilKt.getResolvedCall(((KtCallableReferenceExpression) KtPsiUtil.safeDeparenthesize(((ExpressionReceiver) callInfo.getResolvedCall().getExtensionReceiver()).getExpression())).getCallableReference(), translationContext.bindingContext()).getResultingDescriptor();
                JsBinaryOperation jsBinaryOperationNullCheck = TranslationUtils.nullCheck(new JsNameRef(TranslationUtils.getNameForBackingField(translationContext, (PropertyDescriptor) JsDescriptorUtils.findRealDeclaration(propertyDescriptor)), MetadataProperties.getCallableReferenceReceiver((JsInvocation) callInfo.getExtensionReceiver())), true);
                if (jsBinaryOperationNullCheck == null) {
                    $$$reportNull$$$0(3);
                }
                return jsBinaryOperationNullCheck;
            }
        };
        STRING_SUBSTRING = new FunctionIntrinsicWithReceiverComputed() { // from class: org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.TopLevelFIF.7
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                Object[] objArr = new Object[3];
                if (i != 1) {
                    objArr[0] = "arguments";
                } else {
                    objArr[0] = "context";
                }
                objArr[1] = "org/jetbrains/kotlin/js/translate/intrinsic/functions/factories/TopLevelFIF$7";
                objArr[2] = "apply";
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
            }

            @Override // org.jetbrains.kotlin.js.translate.intrinsic.functions.basic.FunctionIntrinsicWithReceiverComputed
            public JsExpression apply(JsExpression jsExpression, List<? extends JsExpression> list, TranslationContext translationContext) {
                if (list == null) {
                    $$$reportNull$$$0(0);
                }
                if (translationContext == null) {
                    $$$reportNull$$$0(1);
                }
                return new JsInvocation(new JsNameRef("substring", jsExpression), list);
            }
        };
        TO_STRING = new KotlinAliasedFunctionIntrinsic("toString");
        CHAR_TO_STRING = new FunctionIntrinsicWithReceiverComputed() { // from class: org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.TopLevelFIF.8
            static final /* synthetic */ boolean $assertionsDisabled = false;

            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                String str2 = i != 2 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
                Object[] objArr = new Object[i != 2 ? 3 : 2];
                if (i == 1) {
                    objArr[0] = "context";
                } else if (i != 2) {
                    objArr[0] = "arguments";
                } else {
                    objArr[0] = "org/jetbrains/kotlin/js/translate/intrinsic/functions/factories/TopLevelFIF$8";
                }
                if (i != 2) {
                    objArr[1] = "org/jetbrains/kotlin/js/translate/intrinsic/functions/factories/TopLevelFIF$8";
                } else {
                    objArr[1] = "apply";
                }
                if (i != 2) {
                    objArr[2] = "apply";
                }
                String str3 = String.format(str2, objArr);
                if (i == 2) {
                    throw new IllegalStateException(str3);
                }
            }

            @Override // org.jetbrains.kotlin.js.translate.intrinsic.functions.basic.FunctionIntrinsicWithReceiverComputed
            public JsExpression apply(JsExpression jsExpression, List<? extends JsExpression> list, TranslationContext translationContext) {
                if (list == null) {
                    $$$reportNull$$$0(0);
                }
                if (translationContext == null) {
                    $$$reportNull$$$0(1);
                }
                JsExpression jsExpressionCharToString = JsAstUtils.charToString(TranslationUtils.coerce(translationContext, jsExpression, translationContext.getCurrentModule().getBuiltIns().getCharType()));
                if (jsExpressionCharToString == null) {
                    $$$reportNull$$$0(2);
                }
                return jsExpressionCharToString;
            }
        };
        INSTANCE = new TopLevelFIF();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static JsExpression getReferenceToOnlyTypeParameter(CallInfo callInfo, TranslationContext translationContext) {
        if (callInfo == null) {
            $$$reportNull$$$0(0);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(1);
        }
        return UtilsKt.getReferenceToJsClass(callInfo.getResolvedCall().getTypeArguments().values().iterator().next(), translationContext);
    }

    private TopLevelFIF() {
        add(EQUALS_IN_ANY, KOTLIN_ANY_EQUALS);
        add(PatternBuilder.pattern("Char.toString"), CHAR_TO_STRING);
        add(PatternBuilder.pattern(IncrementalJvmCacheKt.KOTLIN_CACHE_DIRECTORY_NAME, "toString").isExtensionOf(StandardNames.FqNames.any.asString()), TO_STRING);
        PatternBuilder.DescriptorPredicateImpl descriptorPredicateImplIsExtensionOf = PatternBuilder.pattern(IncrementalJvmCacheKt.KOTLIN_CACHE_DIRECTORY_NAME, Namer.EQUALS_METHOD_NAME).isExtensionOf(StandardNames.FqNames.any.asString());
        KotlinAliasedFunctionIntrinsic kotlinAliasedFunctionIntrinsic = KOTLIN_EQUALS;
        add(descriptorPredicateImplIsExtensionOf, kotlinAliasedFunctionIntrinsic);
        add(HASH_CODE_IN_ANY, KOTLIN_HASH_CODE);
        add(PatternBuilder.pattern(NamePredicate.PRIMITIVE_NUMBERS, Namer.EQUALS_METHOD_NAME), kotlinAliasedFunctionIntrinsic);
        add(PatternBuilder.pattern("String|Boolean|Char|Number.equals"), kotlinAliasedFunctionIntrinsic);
        add(PatternBuilder.pattern("String.subSequence"), STRING_SUBSTRING);
        add(PatternBuilder.pattern("CharSequence.subSequence"), KOTLIN_SUBSEQUENCE);
        add(PatternBuilder.pattern(IncrementalJvmCacheKt.KOTLIN_CACHE_DIRECTORY_NAME, "iterator").isExtensionOf(StandardNames.FqNames.iterator.asString()), RETURN_RECEIVER_INTRINSIC);
        add(PatternBuilder.pattern("kotlin.js", "Json", "get"), ArrayFIF.GET_INTRINSIC);
        add(PatternBuilder.pattern("kotlin.js", "Json", FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX), ArrayFIF.SET_INTRINSIC);
        add(PatternBuilder.pattern("kotlin.js", "jsClass"), JS_CLASS_FUN_INTRINSIC);
        add(PatternBuilder.pattern(IncrementalJvmCacheKt.KOTLIN_CACHE_DIRECTORY_NAME, "enumValues"), ENUM_VALUES_INTRINSIC);
        add(PatternBuilder.pattern(IncrementalJvmCacheKt.KOTLIN_CACHE_DIRECTORY_NAME, "enumValueOf"), ENUM_VALUE_OF_INTRINSIC);
        add(PatternBuilder.pattern(IncrementalJvmCacheKt.KOTLIN_CACHE_DIRECTORY_NAME, "<get-isInitialized>").isExtensionOf("kotlin.reflect.KProperty0"), LATEINIT_KPROPERTY_ISINITIALIZED_INTRINSIC);
    }
}
