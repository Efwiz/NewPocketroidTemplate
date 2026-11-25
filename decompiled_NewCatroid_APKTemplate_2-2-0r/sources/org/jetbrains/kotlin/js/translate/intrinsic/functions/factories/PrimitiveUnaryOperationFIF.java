package org.jetbrains.kotlin.js.translate.intrinsic.functions.factories;

import java.util.List;
import java.util.function.Predicate;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperation;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperator;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsIntLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsInvocation;
import org.jetbrains.kotlin.js.backend.ast.JsPrefixOperation;
import org.jetbrains.kotlin.js.backend.ast.JsUnaryOperator;
import org.jetbrains.kotlin.js.patterns.DescriptorPredicate;
import org.jetbrains.kotlin.js.patterns.NamePredicate;
import org.jetbrains.kotlin.js.patterns.PatternBuilder;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.js.translate.intrinsic.functions.basic.FunctionIntrinsic;
import org.jetbrains.kotlin.js.translate.intrinsic.functions.basic.FunctionIntrinsicWithReceiverComputed;
import org.jetbrains.kotlin.js.translate.operation.OperatorTable;
import org.jetbrains.kotlin.js.translate.utils.JsAstUtils;
import org.jetbrains.kotlin.js.translate.utils.JsDescriptorUtils;
import org.jetbrains.kotlin.js.translate.utils.jsAstUtils.AstUtilsKt;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.types.expressions.OperatorConventions;
import org.jetbrains.kotlin.util.OperatorNameConventions;

/* JADX INFO: loaded from: classes5.dex */
public enum PrimitiveUnaryOperationFIF implements FunctionIntrinsicFactory {
    INSTANCE;

    private static final FunctionIntrinsic CHAR_DEC;
    private static final FunctionIntrinsic CHAR_INC;
    private static final FunctionIntrinsic CHAR_MINUS;
    private static final FunctionIntrinsic CHAR_PLUS;
    private static final DescriptorPredicate DEC_OPERATION_FOR_BYTE;
    private static final DescriptorPredicate DEC_OPERATION_FOR_INT;
    private static final DescriptorPredicate DEC_OPERATION_FOR_PRIMITIVE_NUMBER;
    private static final DescriptorPredicate DEC_OPERATION_FOR_SHORT;
    private static final DescriptorPredicate INC_OPERATION_FOR_BYTE;
    private static final DescriptorPredicate INC_OPERATION_FOR_INT;
    private static final DescriptorPredicate INC_OPERATION_FOR_PRIMITIVE_NUMBER;
    private static final DescriptorPredicate INC_OPERATION_FOR_SHORT;
    private static final DescriptorPredicate NEG_OPERATION_FOR_INT;
    private static final DescriptorPredicate NO_PARAMETERS;
    private static final FunctionIntrinsicWithReceiverComputed NUMBER_DEC_INTRINSIC;
    private static final FunctionIntrinsicWithReceiverComputed NUMBER_INC_INTRINSIC;
    private static final FunctionIntrinsicWithReceiverComputed NUMBER_NEG_INTRINSIC;
    private static final Predicate<FunctionDescriptor> PATTERN;
    private static final Predicate<FunctionDescriptor> PRIMITIVE_UNARY_OPERATION_NAMES;
    private static final NamePredicate UNARY_OPERATIONS;
    private static final DescriptorPredicate UNARY_OPERATION_FOR_PRIMITIVE_NUMBER;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 1) {
            objArr[0] = "descriptor";
        } else {
            objArr[0] = "context";
        }
        objArr[1] = "org/jetbrains/kotlin/js/translate/intrinsic/functions/factories/PrimitiveUnaryOperationFIF";
        objArr[2] = "getIntrinsic";
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    static {
        NamePredicate namePredicate = new NamePredicate(OperatorNameConventions.UNARY_OPERATION_NAMES);
        UNARY_OPERATIONS = namePredicate;
        DescriptorPredicate descriptorPredicatePattern = PatternBuilder.pattern(NamePredicate.PRIMITIVE_NUMBERS_MAPPED_TO_PRIMITIVE_JS, namePredicate);
        UNARY_OPERATION_FOR_PRIMITIVE_NUMBER = descriptorPredicatePattern;
        Predicate<FunctionDescriptor> predicateOr = descriptorPredicatePattern.or(PatternBuilder.pattern("Boolean.not")).or(PatternBuilder.pattern("Int|Short|Byte.inv"));
        PRIMITIVE_UNARY_OPERATION_NAMES = predicateOr;
        DescriptorPredicate descriptorPredicate = new DescriptorPredicate() { // from class: org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.PrimitiveUnaryOperationFIF$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(FunctionDescriptor functionDescriptor) {
                return PrimitiveUnaryOperationFIF.lambda$static$0(functionDescriptor);
            }
        };
        NO_PARAMETERS = descriptorPredicate;
        PATTERN = predicateOr.and(descriptorPredicate);
        INC_OPERATION_FOR_INT = PatternBuilder.pattern("Int.inc");
        DEC_OPERATION_FOR_INT = PatternBuilder.pattern("Int.dec");
        INC_OPERATION_FOR_BYTE = PatternBuilder.pattern("Byte.inc");
        DEC_OPERATION_FOR_BYTE = PatternBuilder.pattern("Byte.dec");
        INC_OPERATION_FOR_SHORT = PatternBuilder.pattern("Short.inc");
        DEC_OPERATION_FOR_SHORT = PatternBuilder.pattern("Short.dec");
        NEG_OPERATION_FOR_INT = PatternBuilder.pattern("Int.unaryMinus");
        INC_OPERATION_FOR_PRIMITIVE_NUMBER = PatternBuilder.pattern("Float|Double.inc()");
        DEC_OPERATION_FOR_PRIMITIVE_NUMBER = PatternBuilder.pattern("Float|Double.dec()");
        NUMBER_INC_INTRINSIC = new FunctionIntrinsicWithReceiverComputed() { // from class: org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.PrimitiveUnaryOperationFIF.1
            static final /* synthetic */ boolean $assertionsDisabled = false;

            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                Object[] objArr = new Object[3];
                if (i != 1) {
                    objArr[0] = "arguments";
                } else {
                    objArr[0] = "context";
                }
                objArr[1] = "org/jetbrains/kotlin/js/translate/intrinsic/functions/factories/PrimitiveUnaryOperationFIF$1";
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
                return new JsBinaryOperation(JsBinaryOperator.ADD, jsExpression, new JsIntLiteral(1));
            }
        };
        NUMBER_DEC_INTRINSIC = new FunctionIntrinsicWithReceiverComputed() { // from class: org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.PrimitiveUnaryOperationFIF.2
            static final /* synthetic */ boolean $assertionsDisabled = false;

            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                Object[] objArr = new Object[3];
                if (i != 1) {
                    objArr[0] = "arguments";
                } else {
                    objArr[0] = "context";
                }
                objArr[1] = "org/jetbrains/kotlin/js/translate/intrinsic/functions/factories/PrimitiveUnaryOperationFIF$2";
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
                return new JsBinaryOperation(JsBinaryOperator.SUB, jsExpression, new JsIntLiteral(1));
            }
        };
        NUMBER_NEG_INTRINSIC = new FunctionIntrinsicWithReceiverComputed() { // from class: org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.PrimitiveUnaryOperationFIF.3
            static final /* synthetic */ boolean $assertionsDisabled = false;

            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                Object[] objArr = new Object[3];
                if (i != 1) {
                    objArr[0] = "arguments";
                } else {
                    objArr[0] = "context";
                }
                objArr[1] = "org/jetbrains/kotlin/js/translate/intrinsic/functions/factories/PrimitiveUnaryOperationFIF$3";
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
                return new JsPrefixOperation(JsUnaryOperator.NEG, jsExpression);
            }
        };
        CHAR_PLUS = new UnaryOperationInstrinsicBase() { // from class: org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.PrimitiveUnaryOperationFIF.4
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                String str = i != 2 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
                Object[] objArr = new Object[i != 2 ? 3 : 2];
                if (i == 1) {
                    objArr[0] = "context";
                } else if (i != 2) {
                    objArr[0] = "receiver";
                } else {
                    objArr[0] = "org/jetbrains/kotlin/js/translate/intrinsic/functions/factories/PrimitiveUnaryOperationFIF$4";
                }
                if (i != 2) {
                    objArr[1] = "org/jetbrains/kotlin/js/translate/intrinsic/functions/factories/PrimitiveUnaryOperationFIF$4";
                } else {
                    objArr[1] = "doApply";
                }
                if (i != 2) {
                    objArr[2] = "doApply";
                }
                String str2 = String.format(str, objArr);
                if (i == 2) {
                    throw new IllegalStateException(str2);
                }
            }

            @Override // org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.PrimitiveUnaryOperationFIF.UnaryOperationInstrinsicBase
            public JsExpression doApply(JsExpression jsExpression, TranslationContext translationContext) {
                if (jsExpression == null) {
                    $$$reportNull$$$0(0);
                }
                if (translationContext == null) {
                    $$$reportNull$$$0(1);
                }
                JsExpression jsExpressionCharToInt = JsAstUtils.charToInt(jsExpression);
                if (jsExpressionCharToInt == null) {
                    $$$reportNull$$$0(2);
                }
                return jsExpressionCharToInt;
            }
        };
        CHAR_MINUS = new UnaryOperationInstrinsicBase() { // from class: org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.PrimitiveUnaryOperationFIF.5
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                Object[] objArr = new Object[3];
                if (i != 1) {
                    objArr[0] = "receiver";
                } else {
                    objArr[0] = "context";
                }
                objArr[1] = "org/jetbrains/kotlin/js/translate/intrinsic/functions/factories/PrimitiveUnaryOperationFIF$5";
                objArr[2] = "doApply";
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
            }

            @Override // org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.PrimitiveUnaryOperationFIF.UnaryOperationInstrinsicBase
            public JsExpression doApply(JsExpression jsExpression, TranslationContext translationContext) {
                if (jsExpression == null) {
                    $$$reportNull$$$0(0);
                }
                if (translationContext == null) {
                    $$$reportNull$$$0(1);
                }
                return new JsPrefixOperation(JsUnaryOperator.NEG, JsAstUtils.charToInt(jsExpression));
            }
        };
        CHAR_INC = new UnaryOperationInstrinsicBase() { // from class: org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.PrimitiveUnaryOperationFIF.6
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                String str = i != 2 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
                Object[] objArr = new Object[i != 2 ? 3 : 2];
                if (i == 1) {
                    objArr[0] = "context";
                } else if (i != 2) {
                    objArr[0] = "receiver";
                } else {
                    objArr[0] = "org/jetbrains/kotlin/js/translate/intrinsic/functions/factories/PrimitiveUnaryOperationFIF$6";
                }
                if (i != 2) {
                    objArr[1] = "org/jetbrains/kotlin/js/translate/intrinsic/functions/factories/PrimitiveUnaryOperationFIF$6";
                } else {
                    objArr[1] = "doApply";
                }
                if (i != 2) {
                    objArr[2] = "doApply";
                }
                String str2 = String.format(str, objArr);
                if (i == 2) {
                    throw new IllegalStateException(str2);
                }
            }

            @Override // org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.PrimitiveUnaryOperationFIF.UnaryOperationInstrinsicBase
            public JsExpression doApply(JsExpression jsExpression, TranslationContext translationContext) {
                if (jsExpression == null) {
                    $$$reportNull$$$0(0);
                }
                if (translationContext == null) {
                    $$$reportNull$$$0(1);
                }
                JsInvocation jsInvocationInvokeKotlinFunction = JsAstUtils.invokeKotlinFunction("charInc", jsExpression);
                if (jsInvocationInvokeKotlinFunction == null) {
                    $$$reportNull$$$0(2);
                }
                return jsInvocationInvokeKotlinFunction;
            }
        };
        CHAR_DEC = new UnaryOperationInstrinsicBase() { // from class: org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.PrimitiveUnaryOperationFIF.7
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                String str = i != 2 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
                Object[] objArr = new Object[i != 2 ? 3 : 2];
                if (i == 1) {
                    objArr[0] = "context";
                } else if (i != 2) {
                    objArr[0] = "receiver";
                } else {
                    objArr[0] = "org/jetbrains/kotlin/js/translate/intrinsic/functions/factories/PrimitiveUnaryOperationFIF$7";
                }
                if (i != 2) {
                    objArr[1] = "org/jetbrains/kotlin/js/translate/intrinsic/functions/factories/PrimitiveUnaryOperationFIF$7";
                } else {
                    objArr[1] = "doApply";
                }
                if (i != 2) {
                    objArr[2] = "doApply";
                }
                String str2 = String.format(str, objArr);
                if (i == 2) {
                    throw new IllegalStateException(str2);
                }
            }

            @Override // org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.PrimitiveUnaryOperationFIF.UnaryOperationInstrinsicBase
            public JsExpression doApply(JsExpression jsExpression, TranslationContext translationContext) {
                if (jsExpression == null) {
                    $$$reportNull$$$0(0);
                }
                if (translationContext == null) {
                    $$$reportNull$$$0(1);
                }
                JsInvocation jsInvocationInvokeKotlinFunction = JsAstUtils.invokeKotlinFunction("charDec", jsExpression);
                if (jsInvocationInvokeKotlinFunction == null) {
                    $$$reportNull$$$0(2);
                }
                return jsInvocationInvokeKotlinFunction;
            }
        };
    }

    static /* synthetic */ boolean lambda$static$0(FunctionDescriptor functionDescriptor) {
        return !JsDescriptorUtils.hasParameters(functionDescriptor);
    }

    private static class IntOverflowIntrinsic extends FunctionIntrinsicWithReceiverComputed {
        private final FunctionIntrinsicWithReceiverComputed underlyingIntrinsic;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = i != 2 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
            Object[] objArr = new Object[i != 2 ? 3 : 2];
            if (i == 1) {
                objArr[0] = "context";
            } else if (i != 2) {
                objArr[0] = "arguments";
            } else {
                objArr[0] = "org/jetbrains/kotlin/js/translate/intrinsic/functions/factories/PrimitiveUnaryOperationFIF$IntOverflowIntrinsic";
            }
            if (i != 2) {
                objArr[1] = "org/jetbrains/kotlin/js/translate/intrinsic/functions/factories/PrimitiveUnaryOperationFIF$IntOverflowIntrinsic";
            } else {
                objArr[1] = "apply";
            }
            if (i != 2) {
                objArr[2] = "apply";
            }
            String str2 = String.format(str, objArr);
            if (i == 2) {
                throw new IllegalStateException(str2);
            }
        }

        public IntOverflowIntrinsic(FunctionIntrinsicWithReceiverComputed functionIntrinsicWithReceiverComputed) {
            this.underlyingIntrinsic = functionIntrinsicWithReceiverComputed;
        }

        @Override // org.jetbrains.kotlin.js.translate.intrinsic.functions.basic.FunctionIntrinsicWithReceiverComputed
        public JsExpression apply(JsExpression jsExpression, List<? extends JsExpression> list, TranslationContext translationContext) {
            if (list == null) {
                $$$reportNull$$$0(0);
            }
            if (translationContext == null) {
                $$$reportNull$$$0(1);
            }
            JsExpression int32 = JsAstUtils.toInt32(this.underlyingIntrinsic.apply(jsExpression, list, translationContext));
            if (int32 == null) {
                $$$reportNull$$$0(2);
            }
            return int32;
        }
    }

    private static class ShortOverflowIntrinsic extends FunctionIntrinsicWithReceiverComputed {
        private final FunctionIntrinsicWithReceiverComputed underlyingIntrinsic;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = i != 2 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
            Object[] objArr = new Object[i != 2 ? 3 : 2];
            if (i == 1) {
                objArr[0] = "context";
            } else if (i != 2) {
                objArr[0] = "arguments";
            } else {
                objArr[0] = "org/jetbrains/kotlin/js/translate/intrinsic/functions/factories/PrimitiveUnaryOperationFIF$ShortOverflowIntrinsic";
            }
            if (i != 2) {
                objArr[1] = "org/jetbrains/kotlin/js/translate/intrinsic/functions/factories/PrimitiveUnaryOperationFIF$ShortOverflowIntrinsic";
            } else {
                objArr[1] = "apply";
            }
            if (i != 2) {
                objArr[2] = "apply";
            }
            String str2 = String.format(str, objArr);
            if (i == 2) {
                throw new IllegalStateException(str2);
            }
        }

        public ShortOverflowIntrinsic(FunctionIntrinsicWithReceiverComputed functionIntrinsicWithReceiverComputed) {
            this.underlyingIntrinsic = functionIntrinsicWithReceiverComputed;
        }

        @Override // org.jetbrains.kotlin.js.translate.intrinsic.functions.basic.FunctionIntrinsicWithReceiverComputed
        public JsExpression apply(JsExpression jsExpression, List<? extends JsExpression> list, TranslationContext translationContext) {
            if (list == null) {
                $$$reportNull$$$0(0);
            }
            if (translationContext == null) {
                $$$reportNull$$$0(1);
            }
            JsInvocation jsInvocation = AstUtilsKt.toShort(translationContext, this.underlyingIntrinsic.apply(jsExpression, list, translationContext));
            if (jsInvocation == null) {
                $$$reportNull$$$0(2);
            }
            return jsInvocation;
        }
    }

    private static class ByteOverflowIntrinsic extends FunctionIntrinsicWithReceiverComputed {
        private final FunctionIntrinsicWithReceiverComputed underlyingIntrinsic;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = i != 2 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
            Object[] objArr = new Object[i != 2 ? 3 : 2];
            if (i == 1) {
                objArr[0] = "context";
            } else if (i != 2) {
                objArr[0] = "arguments";
            } else {
                objArr[0] = "org/jetbrains/kotlin/js/translate/intrinsic/functions/factories/PrimitiveUnaryOperationFIF$ByteOverflowIntrinsic";
            }
            if (i != 2) {
                objArr[1] = "org/jetbrains/kotlin/js/translate/intrinsic/functions/factories/PrimitiveUnaryOperationFIF$ByteOverflowIntrinsic";
            } else {
                objArr[1] = "apply";
            }
            if (i != 2) {
                objArr[2] = "apply";
            }
            String str2 = String.format(str, objArr);
            if (i == 2) {
                throw new IllegalStateException(str2);
            }
        }

        public ByteOverflowIntrinsic(FunctionIntrinsicWithReceiverComputed functionIntrinsicWithReceiverComputed) {
            this.underlyingIntrinsic = functionIntrinsicWithReceiverComputed;
        }

        @Override // org.jetbrains.kotlin.js.translate.intrinsic.functions.basic.FunctionIntrinsicWithReceiverComputed
        public JsExpression apply(JsExpression jsExpression, List<? extends JsExpression> list, TranslationContext translationContext) {
            if (list == null) {
                $$$reportNull$$$0(0);
            }
            if (translationContext == null) {
                $$$reportNull$$$0(1);
            }
            JsInvocation jsInvocation = AstUtilsKt.toByte(translationContext, this.underlyingIntrinsic.apply(jsExpression, list, translationContext));
            if (jsInvocation == null) {
                $$$reportNull$$$0(2);
            }
            return jsInvocation;
        }
    }

    private static abstract class UnaryOperationInstrinsicBase extends FunctionIntrinsicWithReceiverComputed {
        static final /* synthetic */ boolean $assertionsDisabled = false;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = i != 2 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
            Object[] objArr = new Object[i != 2 ? 3 : 2];
            if (i == 1) {
                objArr[0] = "context";
            } else if (i != 2) {
                objArr[0] = "arguments";
            } else {
                objArr[0] = "org/jetbrains/kotlin/js/translate/intrinsic/functions/factories/PrimitiveUnaryOperationFIF$UnaryOperationInstrinsicBase";
            }
            if (i != 2) {
                objArr[1] = "org/jetbrains/kotlin/js/translate/intrinsic/functions/factories/PrimitiveUnaryOperationFIF$UnaryOperationInstrinsicBase";
            } else {
                objArr[1] = "apply";
            }
            if (i != 2) {
                objArr[2] = "apply";
            }
            String str2 = String.format(str, objArr);
            if (i == 2) {
                throw new IllegalStateException(str2);
            }
        }

        public abstract JsExpression doApply(JsExpression jsExpression, TranslationContext translationContext);

        private UnaryOperationInstrinsicBase() {
        }

        @Override // org.jetbrains.kotlin.js.translate.intrinsic.functions.basic.FunctionIntrinsicWithReceiverComputed
        public JsExpression apply(JsExpression jsExpression, List<? extends JsExpression> list, TranslationContext translationContext) {
            if (list == null) {
                $$$reportNull$$$0(0);
            }
            if (translationContext == null) {
                $$$reportNull$$$0(1);
            }
            JsExpression jsExpressionDoApply = doApply(jsExpression, translationContext);
            if (jsExpressionDoApply == null) {
                $$$reportNull$$$0(2);
            }
            return jsExpressionDoApply;
        }
    }

    @Override // org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.FunctionIntrinsicFactory
    public FunctionIntrinsic getIntrinsic(FunctionDescriptor functionDescriptor, TranslationContext translationContext) {
        final JsUnaryOperator unaryOperator;
        if (functionDescriptor == null) {
            $$$reportNull$$$0(0);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(1);
        }
        if (!PATTERN.test(functionDescriptor)) {
            return null;
        }
        if (PatternBuilder.pattern("Char.unaryPlus()").test(functionDescriptor)) {
            return CHAR_PLUS;
        }
        if (PatternBuilder.pattern("Char.unaryMinus()").test(functionDescriptor)) {
            return CHAR_MINUS;
        }
        if (PatternBuilder.pattern("Char.plus()").test(functionDescriptor)) {
            return CHAR_PLUS;
        }
        if (PatternBuilder.pattern("Char.minus()").test(functionDescriptor)) {
            return CHAR_MINUS;
        }
        if (PatternBuilder.pattern("Char.inc()").test(functionDescriptor)) {
            return CHAR_INC;
        }
        if (PatternBuilder.pattern("Char.dec()").test(functionDescriptor)) {
            return CHAR_DEC;
        }
        if (INC_OPERATION_FOR_INT.test(functionDescriptor)) {
            return new IntOverflowIntrinsic(NUMBER_INC_INTRINSIC);
        }
        if (DEC_OPERATION_FOR_INT.test(functionDescriptor)) {
            return new IntOverflowIntrinsic(NUMBER_DEC_INTRINSIC);
        }
        if (INC_OPERATION_FOR_SHORT.test(functionDescriptor)) {
            return new ShortOverflowIntrinsic(NUMBER_INC_INTRINSIC);
        }
        if (DEC_OPERATION_FOR_SHORT.test(functionDescriptor)) {
            return new ShortOverflowIntrinsic(NUMBER_DEC_INTRINSIC);
        }
        if (INC_OPERATION_FOR_BYTE.test(functionDescriptor)) {
            return new ByteOverflowIntrinsic(NUMBER_INC_INTRINSIC);
        }
        if (DEC_OPERATION_FOR_BYTE.test(functionDescriptor)) {
            return new ByteOverflowIntrinsic(NUMBER_DEC_INTRINSIC);
        }
        if (INC_OPERATION_FOR_PRIMITIVE_NUMBER.test(functionDescriptor)) {
            return NUMBER_INC_INTRINSIC;
        }
        if (DEC_OPERATION_FOR_PRIMITIVE_NUMBER.test(functionDescriptor)) {
            return NUMBER_DEC_INTRINSIC;
        }
        if (NEG_OPERATION_FOR_INT.test(functionDescriptor)) {
            return new IntOverflowIntrinsic(NUMBER_NEG_INTRINSIC);
        }
        Name name2 = functionDescriptor.getShortName();
        if ("inv".equals(name2.asString())) {
            unaryOperator = JsUnaryOperator.BIT_NOT;
        } else {
            unaryOperator = OperatorTable.getUnaryOperator(OperatorConventions.UNARY_OPERATION_NAMES.inverse().get(name2));
        }
        return new FunctionIntrinsicWithReceiverComputed() { // from class: org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.PrimitiveUnaryOperationFIF.8
            static final /* synthetic */ boolean $assertionsDisabled = false;

            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                Object[] objArr = new Object[3];
                if (i != 1) {
                    objArr[0] = "arguments";
                } else {
                    objArr[0] = "context";
                }
                objArr[1] = "org/jetbrains/kotlin/js/translate/intrinsic/functions/factories/PrimitiveUnaryOperationFIF$8";
                objArr[2] = "apply";
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
            }

            @Override // org.jetbrains.kotlin.js.translate.intrinsic.functions.basic.FunctionIntrinsicWithReceiverComputed
            public JsExpression apply(JsExpression jsExpression, List<? extends JsExpression> list, TranslationContext translationContext2) {
                if (list == null) {
                    $$$reportNull$$$0(0);
                }
                if (translationContext2 == null) {
                    $$$reportNull$$$0(1);
                }
                return new JsPrefixOperation(unaryOperator, jsExpression);
            }
        };
    }
}
