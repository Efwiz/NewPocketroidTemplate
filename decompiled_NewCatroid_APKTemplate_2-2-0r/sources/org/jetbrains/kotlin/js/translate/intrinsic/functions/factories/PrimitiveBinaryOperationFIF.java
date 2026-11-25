package org.jetbrains.kotlin.js.translate.intrinsic.functions.factories;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.com.google.common.collect.ImmutableMap;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperation;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperator;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsIntLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsInvocation;
import org.jetbrains.kotlin.js.patterns.DescriptorPredicate;
import org.jetbrains.kotlin.js.patterns.NamePredicate;
import org.jetbrains.kotlin.js.patterns.PatternBuilder;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.js.translate.intrinsic.functions.basic.FunctionIntrinsic;
import org.jetbrains.kotlin.js.translate.intrinsic.functions.basic.FunctionIntrinsicWithReceiverComputed;
import org.jetbrains.kotlin.js.translate.intrinsic.functions.basic.RangeToIntrinsic;
import org.jetbrains.kotlin.js.translate.intrinsic.functions.basic.RangeUntilIntrinsic;
import org.jetbrains.kotlin.js.translate.operation.OperatorTable;
import org.jetbrains.kotlin.js.translate.utils.JsAstUtils;
import org.jetbrains.kotlin.js.translate.utils.jsAstUtils.AstUtilsKt;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.types.expressions.OperatorConventions;
import org.jetbrains.kotlin.util.OperatorNameConventions;

/* JADX INFO: loaded from: classes5.dex */
public enum PrimitiveBinaryOperationFIF implements FunctionIntrinsicFactory {
    INSTANCE;

    private static final ImmutableMap<String, JsBinaryOperator> BINARY_BITWISE_OPERATIONS;
    private static final NamePredicate BINARY_OPERATIONS;
    private static final DescriptorPredicate BOOLEAN_OPERATIONS;
    private static final DescriptorPredicate CHAR_RANGE_TO;
    private static final DescriptorPredicate CHAR_RANGE_UNTIL;
    private static final DescriptorPredicate INT_BINARY_OPERATIONS;
    private static final DescriptorPredicate INT_DIVISION;
    private static final DescriptorPredicate INT_MULTIPLICATION;
    private static final Predicate<FunctionDescriptor> INT_WITH_BIT_OPERATIONS;
    private static final DescriptorPredicate NUMBER_RANGE_TO;
    private static final DescriptorPredicate NUMBER_RANGE_UNTIL;
    private static final DescriptorPredicate NUMBER_REM;
    private static final Predicate<FunctionDescriptor> PREDICATE;
    private static final DescriptorPredicate PRIMITIVE_INTEGRAL_NUMBERS_COMPARE_TO_INTEGRAL_OPERATIONS;
    private static final DescriptorPredicate PRIMITIVE_NUMBERS_BINARY_OPERATIONS;
    private static final DescriptorPredicate PRIMITIVE_NUMBERS_COMPARE_TO_OPERATIONS;
    private static final DescriptorPredicate SIMPLE_INT_MULTIPLICATION;
    private static final DescriptorPredicate STRING_PLUS;
    private static final BinaryOperationIntrinsicBase INT_MULTIPLICATION_INTRINSIC = new BinaryOperationIntrinsicBase() { // from class: org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.PrimitiveBinaryOperationFIF.1
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = i != 3 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
            Object[] objArr = new Object[i != 3 ? 3 : 2];
            if (i == 1) {
                objArr[0] = "right";
            } else if (i == 2) {
                objArr[0] = "context";
            } else if (i == 3) {
                objArr[0] = "org/jetbrains/kotlin/js/translate/intrinsic/functions/factories/PrimitiveBinaryOperationFIF$1";
            } else if (i != 4) {
                objArr[0] = "left";
            } else {
                objArr[0] = "expression";
            }
            if (i != 3) {
                objArr[1] = "org/jetbrains/kotlin/js/translate/intrinsic/functions/factories/PrimitiveBinaryOperationFIF$1";
            } else {
                objArr[1] = "doApply";
            }
            if (i != 3) {
                if (i != 4) {
                    objArr[2] = "doApply";
                } else {
                    objArr[2] = "isSafeConstant";
                }
            }
            String str2 = String.format(str, objArr);
            if (i == 3) {
                throw new IllegalStateException(str2);
            }
        }

        @Override // org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.PrimitiveBinaryOperationFIF.BinaryOperationIntrinsicBase
        public JsExpression doApply(JsExpression jsExpression, JsExpression jsExpression2, TranslationContext translationContext) {
            if (jsExpression == null) {
                $$$reportNull$$$0(0);
            }
            if (jsExpression2 == null) {
                $$$reportNull$$$0(1);
            }
            if (translationContext == null) {
                $$$reportNull$$$0(2);
            }
            if (isSafeConstant(jsExpression) || isSafeConstant(jsExpression2)) {
                JsExpression int32 = JsAstUtils.toInt32(JsAstUtils.mul(jsExpression, jsExpression2));
                if (int32 == null) {
                    $$$reportNull$$$0(3);
                }
                return int32;
            }
            return new JsInvocation(Namer.imul(), jsExpression, jsExpression2);
        }

        private boolean isSafeConstant(JsExpression jsExpression) {
            if (jsExpression == null) {
                $$$reportNull$$$0(4);
            }
            return (jsExpression instanceof JsIntLiteral) && Math.abs(((JsIntLiteral) jsExpression).value) < 1048576;
        }
    };
    private static final BinaryOperationIntrinsicBase NUMBER_REM_INTRINSIC = new BinaryOperationIntrinsicBase() { // from class: org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.PrimitiveBinaryOperationFIF.2
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = i != 3 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
            Object[] objArr = new Object[i != 3 ? 3 : 2];
            if (i == 1) {
                objArr[0] = "right";
            } else if (i == 2) {
                objArr[0] = "context";
            } else if (i != 3) {
                objArr[0] = "left";
            } else {
                objArr[0] = "org/jetbrains/kotlin/js/translate/intrinsic/functions/factories/PrimitiveBinaryOperationFIF$2";
            }
            if (i != 3) {
                objArr[1] = "org/jetbrains/kotlin/js/translate/intrinsic/functions/factories/PrimitiveBinaryOperationFIF$2";
            } else {
                objArr[1] = "doApply";
            }
            if (i != 3) {
                objArr[2] = "doApply";
            }
            String str2 = String.format(str, objArr);
            if (i == 3) {
                throw new IllegalStateException(str2);
            }
        }

        @Override // org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.PrimitiveBinaryOperationFIF.BinaryOperationIntrinsicBase
        public JsExpression doApply(JsExpression jsExpression, JsExpression jsExpression2, TranslationContext translationContext) {
            if (jsExpression == null) {
                $$$reportNull$$$0(0);
            }
            if (jsExpression2 == null) {
                $$$reportNull$$$0(1);
            }
            if (translationContext == null) {
                $$$reportNull$$$0(2);
            }
            JsExpression int32 = JsAstUtils.toInt32(JsAstUtils.mod(jsExpression, jsExpression2));
            if (int32 == null) {
                $$$reportNull$$$0(3);
            }
            return int32;
        }
    };
    private static final BinaryOperationIntrinsicBase BUILTINS_COMPARE_TO_INTRINSIC = new BinaryOperationIntrinsicBase() { // from class: org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.PrimitiveBinaryOperationFIF.3
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = i != 3 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
            Object[] objArr = new Object[i != 3 ? 3 : 2];
            if (i == 1) {
                objArr[0] = "right";
            } else if (i == 2) {
                objArr[0] = "context";
            } else if (i != 3) {
                objArr[0] = "left";
            } else {
                objArr[0] = "org/jetbrains/kotlin/js/translate/intrinsic/functions/factories/PrimitiveBinaryOperationFIF$3";
            }
            if (i != 3) {
                objArr[1] = "org/jetbrains/kotlin/js/translate/intrinsic/functions/factories/PrimitiveBinaryOperationFIF$3";
            } else {
                objArr[1] = "doApply";
            }
            if (i != 3) {
                objArr[2] = "doApply";
            }
            String str2 = String.format(str, objArr);
            if (i == 3) {
                throw new IllegalStateException(str2);
            }
        }

        @Override // org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.PrimitiveBinaryOperationFIF.BinaryOperationIntrinsicBase
        public JsExpression doApply(JsExpression jsExpression, JsExpression jsExpression2, TranslationContext translationContext) {
            if (jsExpression == null) {
                $$$reportNull$$$0(0);
            }
            if (jsExpression2 == null) {
                $$$reportNull$$$0(1);
            }
            if (translationContext == null) {
                $$$reportNull$$$0(2);
            }
            JsExpression jsExpressionCompareTo = JsAstUtils.compareTo(jsExpression, jsExpression2);
            if (jsExpressionCompareTo == null) {
                $$$reportNull$$$0(3);
            }
            return jsExpressionCompareTo;
        }
    };
    private static final BinaryOperationIntrinsicBase PRIMITIVE_NUMBER_COMPARE_TO_INTRINSIC = new BinaryOperationIntrinsicBase() { // from class: org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.PrimitiveBinaryOperationFIF.4
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = i != 3 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
            Object[] objArr = new Object[i != 3 ? 3 : 2];
            if (i == 1) {
                objArr[0] = "right";
            } else if (i == 2) {
                objArr[0] = "context";
            } else if (i != 3) {
                objArr[0] = "left";
            } else {
                objArr[0] = "org/jetbrains/kotlin/js/translate/intrinsic/functions/factories/PrimitiveBinaryOperationFIF$4";
            }
            if (i != 3) {
                objArr[1] = "org/jetbrains/kotlin/js/translate/intrinsic/functions/factories/PrimitiveBinaryOperationFIF$4";
            } else {
                objArr[1] = "doApply";
            }
            if (i != 3) {
                objArr[2] = "doApply";
            }
            String str2 = String.format(str, objArr);
            if (i == 3) {
                throw new IllegalStateException(str2);
            }
        }

        @Override // org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.PrimitiveBinaryOperationFIF.BinaryOperationIntrinsicBase
        public JsExpression doApply(JsExpression jsExpression, JsExpression jsExpression2, TranslationContext translationContext) {
            if (jsExpression == null) {
                $$$reportNull$$$0(0);
            }
            if (jsExpression2 == null) {
                $$$reportNull$$$0(1);
            }
            if (translationContext == null) {
                $$$reportNull$$$0(2);
            }
            JsExpression jsExpressionPrimitiveCompareTo = JsAstUtils.primitiveCompareTo(jsExpression, jsExpression2);
            if (jsExpressionPrimitiveCompareTo == null) {
                $$$reportNull$$$0(3);
            }
            return jsExpressionPrimitiveCompareTo;
        }
    };

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 3 || i == 4 || i == 5 || i == 6) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 3 || i == 4 || i == 5 || i == 6) ? 2 : 3];
        if (i == 1) {
            objArr[0] = "context";
        } else if (i == 3 || i == 4 || i == 5 || i == 6) {
            objArr[0] = "org/jetbrains/kotlin/js/translate/intrinsic/functions/factories/PrimitiveBinaryOperationFIF";
        } else {
            objArr[0] = "descriptor";
        }
        if (i == 3 || i == 4 || i == 5 || i == 6) {
            objArr[1] = "getOperator";
        } else {
            objArr[1] = "org/jetbrains/kotlin/js/translate/intrinsic/functions/factories/PrimitiveBinaryOperationFIF";
        }
        if (i == 2) {
            objArr[2] = "getOperator";
        } else if (i != 3 && i != 4 && i != 5 && i != 6) {
            objArr[2] = "getIntrinsic";
        }
        String str2 = String.format(str, objArr);
        if (i != 3 && i != 4 && i != 5 && i != 6) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    static {
        NamePredicate namePredicate = new NamePredicate(OperatorNameConventions.BINARY_OPERATION_NAMES);
        BINARY_OPERATIONS = namePredicate;
        INT_BINARY_OPERATIONS = PatternBuilder.pattern("Int.plus|minus(Int)");
        SIMPLE_INT_MULTIPLICATION = PatternBuilder.pattern("Byte|Short.times(Byte|Short)");
        INT_DIVISION = PatternBuilder.pattern("Byte|Short|Int.div(Byte|Short|Int)");
        DescriptorPredicate descriptorPredicatePattern = PatternBuilder.pattern(NamePredicate.PRIMITIVE_NUMBERS_MAPPED_TO_PRIMITIVE_JS, namePredicate);
        PRIMITIVE_NUMBERS_BINARY_OPERATIONS = descriptorPredicatePattern;
        PRIMITIVE_INTEGRAL_NUMBERS_COMPARE_TO_INTEGRAL_OPERATIONS = PatternBuilder.pattern("Byte|Short|Int.compareTo(Byte|Short|Int)");
        DescriptorPredicate descriptorPredicatePattern2 = PatternBuilder.pattern(NamePredicate.PRIMITIVE_NUMBERS_MAPPED_TO_PRIMITIVE_JS, "compareTo");
        PRIMITIVE_NUMBERS_COMPARE_TO_OPERATIONS = descriptorPredicatePattern2;
        Predicate<FunctionDescriptor> predicateOr = PatternBuilder.pattern("Int.or|and|xor|shl|shr|ushr").or(PatternBuilder.pattern("Short|Byte.or|and|xor"));
        INT_WITH_BIT_OPERATIONS = predicateOr;
        DescriptorPredicate descriptorPredicatePattern3 = PatternBuilder.pattern("Boolean.or|and|xor");
        BOOLEAN_OPERATIONS = descriptorPredicatePattern3;
        DescriptorPredicate descriptorPredicatePattern4 = PatternBuilder.pattern("String.plus");
        STRING_PLUS = descriptorPredicatePattern4;
        INT_MULTIPLICATION = PatternBuilder.pattern("Int.times(Int)");
        NUMBER_REM = PatternBuilder.pattern("Byte|Short|Int.rem(Byte|Short|Int)");
        CHAR_RANGE_TO = PatternBuilder.pattern("Char.rangeTo(Char)");
        CHAR_RANGE_UNTIL = PatternBuilder.pattern("Char.rangeUntil(Char)");
        NUMBER_RANGE_TO = PatternBuilder.pattern("Byte|Short|Int.rangeTo(Byte|Short|Int)");
        NUMBER_RANGE_UNTIL = PatternBuilder.pattern("Byte|Short|Int|Long.rangeUntil(Byte|Short|Int|Long)");
        BINARY_BITWISE_OPERATIONS = ImmutableMap.builder().put("or", JsBinaryOperator.BIT_OR).put("and", JsBinaryOperator.BIT_AND).put("xor", JsBinaryOperator.BIT_XOR).put("shl", JsBinaryOperator.SHL).put("shr", JsBinaryOperator.SHR).put("ushr", JsBinaryOperator.SHRU).build();
        PREDICATE = descriptorPredicatePattern.or(descriptorPredicatePattern3).or(descriptorPredicatePattern4).or(predicateOr).or(descriptorPredicatePattern2);
    }

    private static abstract class BinaryOperationIntrinsicBase extends FunctionIntrinsicWithReceiverComputed {
        static final /* synthetic */ boolean $assertionsDisabled = false;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = i != 2 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
            Object[] objArr = new Object[i != 2 ? 3 : 2];
            if (i == 1) {
                objArr[0] = "context";
            } else if (i != 2) {
                objArr[0] = "arguments";
            } else {
                objArr[0] = "org/jetbrains/kotlin/js/translate/intrinsic/functions/factories/PrimitiveBinaryOperationFIF$BinaryOperationIntrinsicBase";
            }
            if (i != 2) {
                objArr[1] = "org/jetbrains/kotlin/js/translate/intrinsic/functions/factories/PrimitiveBinaryOperationFIF$BinaryOperationIntrinsicBase";
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

        public abstract JsExpression doApply(JsExpression jsExpression, JsExpression jsExpression2, TranslationContext translationContext);

        private BinaryOperationIntrinsicBase() {
        }

        @Override // org.jetbrains.kotlin.js.translate.intrinsic.functions.basic.FunctionIntrinsicWithReceiverComputed
        public JsExpression apply(JsExpression jsExpression, List<? extends JsExpression> list, TranslationContext translationContext) {
            if (list == null) {
                $$$reportNull$$$0(0);
            }
            if (translationContext == null) {
                $$$reportNull$$$0(1);
            }
            JsExpression jsExpressionDoApply = doApply(jsExpression, list.get(0), translationContext);
            if (jsExpressionDoApply == null) {
                $$$reportNull$$$0(2);
            }
            return jsExpressionDoApply;
        }
    }

    @Override // org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.FunctionIntrinsicFactory
    public FunctionIntrinsic getIntrinsic(FunctionDescriptor functionDescriptor, TranslationContext translationContext) {
        JsBinaryOperator jsBinaryOperator;
        if (functionDescriptor == null) {
            $$$reportNull$$$0(0);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(1);
        }
        if (CHAR_RANGE_TO.test(functionDescriptor)) {
            return new RangeToIntrinsic(functionDescriptor);
        }
        if (CHAR_RANGE_UNTIL.test(functionDescriptor)) {
            return new RangeUntilIntrinsic(functionDescriptor);
        }
        if (PRIMITIVE_INTEGRAL_NUMBERS_COMPARE_TO_INTEGRAL_OPERATIONS.test(functionDescriptor)) {
            return PRIMITIVE_NUMBER_COMPARE_TO_INTRINSIC;
        }
        if (PRIMITIVE_NUMBERS_COMPARE_TO_OPERATIONS.test(functionDescriptor)) {
            return BUILTINS_COMPARE_TO_INTRINSIC;
        }
        if (KotlinBuiltIns.isBuiltIn(functionDescriptor) && functionDescriptor.getShortName().equals(OperatorNameConventions.COMPARE_TO)) {
            return BUILTINS_COMPARE_TO_INTRINSIC;
        }
        if (NUMBER_RANGE_UNTIL.test(functionDescriptor)) {
            return new RangeUntilIntrinsic(functionDescriptor);
        }
        if (!PREDICATE.test(functionDescriptor)) {
            return null;
        }
        if (INT_MULTIPLICATION.test(functionDescriptor)) {
            return INT_MULTIPLICATION_INTRINSIC;
        }
        if (NUMBER_REM.test(functionDescriptor)) {
            return NUMBER_REM_INTRINSIC;
        }
        if (NUMBER_RANGE_TO.test(functionDescriptor)) {
            return new RangeToIntrinsic(functionDescriptor);
        }
        if (INT_WITH_BIT_OPERATIONS.test(functionDescriptor) && (jsBinaryOperator = BINARY_BITWISE_OPERATIONS.get(functionDescriptor.getShortName().asString())) != null) {
            return new OptimizedIntBinaryOperationInstrinsic(jsBinaryOperator);
        }
        JsBinaryOperator operator = getOperator(functionDescriptor);
        if (INT_BINARY_OPERATIONS.test(functionDescriptor)) {
            return new AdditiveIntBinaryOperationInstrinsic(operator);
        }
        if (SIMPLE_INT_MULTIPLICATION.test(functionDescriptor) || INT_DIVISION.test(functionDescriptor)) {
            return new IntBinaryOperationFunctionIntrinsic(operator);
        }
        PrimitiveBinaryOperationFunctionIntrinsic primitiveBinaryOperationFunctionIntrinsic = new PrimitiveBinaryOperationFunctionIntrinsic(operator);
        if (PatternBuilder.pattern("Char.plus|minus(Int)").test(functionDescriptor)) {
            return new CharAndIntBinaryOperationFunctionIntrinsic(primitiveBinaryOperationFunctionIntrinsic);
        }
        if (PatternBuilder.pattern("Char.minus(Char)").test(functionDescriptor)) {
            return new CharAndCharBinaryOperationFunctionIntrinsic(primitiveBinaryOperationFunctionIntrinsic);
        }
        return PatternBuilder.pattern("String.plus(Any)").test(functionDescriptor) ? new StringAndCharBinaryOperationFunctionIntrinsic(primitiveBinaryOperationFunctionIntrinsic) : primitiveBinaryOperationFunctionIntrinsic;
    }

    private static JsBinaryOperator getOperator(FunctionDescriptor functionDescriptor) {
        if (functionDescriptor == null) {
            $$$reportNull$$$0(2);
        }
        Name name2 = functionDescriptor.getShortName().equals(OperatorNameConventions.MOD) ? OperatorNameConventions.REM : functionDescriptor.getShortName();
        String strAsString = name2.asString();
        strAsString.hashCode();
        switch (strAsString) {
            case "or":
                JsBinaryOperator jsBinaryOperator = JsBinaryOperator.BIT_OR;
                if (jsBinaryOperator == null) {
                    $$$reportNull$$$0(3);
                }
                return jsBinaryOperator;
            case "and":
                JsBinaryOperator jsBinaryOperator2 = JsBinaryOperator.BIT_AND;
                if (jsBinaryOperator2 == null) {
                    $$$reportNull$$$0(4);
                }
                return jsBinaryOperator2;
            case "xor":
                JsBinaryOperator jsBinaryOperator3 = JsBinaryOperator.BIT_XOR;
                if (jsBinaryOperator3 == null) {
                    $$$reportNull$$$0(5);
                }
                return jsBinaryOperator3;
            default:
                JsBinaryOperator binaryOperator = OperatorTable.getBinaryOperator(OperatorConventions.BINARY_OPERATION_NAMES.inverse().get(name2));
                if (binaryOperator == null) {
                    $$$reportNull$$$0(6);
                }
                return binaryOperator;
        }
    }

    private static class PrimitiveBinaryOperationFunctionIntrinsic extends BinaryOperationIntrinsicBase {
        private final JsBinaryOperator operator;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i == 1) {
                objArr[0] = "left";
            } else if (i == 2) {
                objArr[0] = "right";
            } else if (i != 3) {
                objArr[0] = "operator";
            } else {
                objArr[0] = "context";
            }
            objArr[1] = "org/jetbrains/kotlin/js/translate/intrinsic/functions/factories/PrimitiveBinaryOperationFIF$PrimitiveBinaryOperationFunctionIntrinsic";
            if (i == 1 || i == 2 || i == 3) {
                objArr[2] = "doApply";
            } else {
                objArr[2] = "<init>";
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        private PrimitiveBinaryOperationFunctionIntrinsic(JsBinaryOperator jsBinaryOperator) {
            super();
            if (jsBinaryOperator == null) {
                $$$reportNull$$$0(0);
            }
            this.operator = jsBinaryOperator;
        }

        @Override // org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.PrimitiveBinaryOperationFIF.BinaryOperationIntrinsicBase
        public JsExpression doApply(JsExpression jsExpression, JsExpression jsExpression2, TranslationContext translationContext) {
            if (jsExpression == null) {
                $$$reportNull$$$0(1);
            }
            if (jsExpression2 == null) {
                $$$reportNull$$$0(2);
            }
            if (translationContext == null) {
                $$$reportNull$$$0(3);
            }
            return new JsBinaryOperation(this.operator, jsExpression, jsExpression2);
        }
    }

    private static class IntBinaryOperationFunctionIntrinsic extends PrimitiveBinaryOperationFunctionIntrinsic {
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = i != 4 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
            Object[] objArr = new Object[i != 4 ? 3 : 2];
            if (i == 1) {
                objArr[0] = "left";
            } else if (i == 2) {
                objArr[0] = "right";
            } else if (i == 3) {
                objArr[0] = "context";
            } else if (i != 4) {
                objArr[0] = "operator";
            } else {
                objArr[0] = "org/jetbrains/kotlin/js/translate/intrinsic/functions/factories/PrimitiveBinaryOperationFIF$IntBinaryOperationFunctionIntrinsic";
            }
            if (i != 4) {
                objArr[1] = "org/jetbrains/kotlin/js/translate/intrinsic/functions/factories/PrimitiveBinaryOperationFIF$IntBinaryOperationFunctionIntrinsic";
            } else {
                objArr[1] = "doApply";
            }
            if (i == 1 || i == 2 || i == 3) {
                objArr[2] = "doApply";
            } else if (i != 4) {
                objArr[2] = "<init>";
            }
            String str2 = String.format(str, objArr);
            if (i == 4) {
                throw new IllegalStateException(str2);
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        private IntBinaryOperationFunctionIntrinsic(JsBinaryOperator jsBinaryOperator) {
            super(jsBinaryOperator);
            if (jsBinaryOperator == null) {
                $$$reportNull$$$0(0);
            }
        }

        @Override // org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.PrimitiveBinaryOperationFIF.PrimitiveBinaryOperationFunctionIntrinsic, org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.PrimitiveBinaryOperationFIF.BinaryOperationIntrinsicBase
        public JsExpression doApply(JsExpression jsExpression, JsExpression jsExpression2, TranslationContext translationContext) {
            if (jsExpression == null) {
                $$$reportNull$$$0(1);
            }
            if (jsExpression2 == null) {
                $$$reportNull$$$0(2);
            }
            if (translationContext == null) {
                $$$reportNull$$$0(3);
            }
            JsExpression int32 = JsAstUtils.toInt32(super.doApply(jsExpression, jsExpression2, translationContext));
            if (int32 == null) {
                $$$reportNull$$$0(4);
            }
            return int32;
        }
    }

    private static class OptimizedIntBinaryOperationInstrinsic extends PrimitiveBinaryOperationFunctionIntrinsic {
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str;
            int i2;
            switch (i) {
                case 4:
                case 6:
                case 7:
                case 8:
                case 9:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                case 5:
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
            switch (i) {
                case 4:
                case 6:
                case 7:
                case 8:
                case 9:
                    i2 = 2;
                    break;
                case 5:
                default:
                    i2 = 3;
                    break;
            }
            Object[] objArr = new Object[i2];
            switch (i) {
                case 1:
                    objArr[0] = "left";
                    break;
                case 2:
                    objArr[0] = "right";
                    break;
                case 3:
                    objArr[0] = "context";
                    break;
                case 4:
                case 6:
                case 7:
                case 8:
                case 9:
                    objArr[0] = "org/jetbrains/kotlin/js/translate/intrinsic/functions/factories/PrimitiveBinaryOperationFIF$OptimizedIntBinaryOperationInstrinsic";
                    break;
                case 5:
                    objArr[0] = "expression";
                    break;
                default:
                    objArr[0] = "operator";
                    break;
            }
            switch (i) {
                case 4:
                    objArr[1] = "doApply";
                    break;
                case 5:
                default:
                    objArr[1] = "org/jetbrains/kotlin/js/translate/intrinsic/functions/factories/PrimitiveBinaryOperationFIF$OptimizedIntBinaryOperationInstrinsic";
                    break;
                case 6:
                case 7:
                case 8:
                case 9:
                    objArr[1] = "unwrapAdditive";
                    break;
            }
            switch (i) {
                case 1:
                case 2:
                case 3:
                    objArr[2] = "doApply";
                    break;
                case 4:
                case 6:
                case 7:
                case 8:
                case 9:
                    break;
                case 5:
                    objArr[2] = "unwrapAdditive";
                    break;
                default:
                    objArr[2] = "<init>";
                    break;
            }
            String str2 = String.format(str, objArr);
            switch (i) {
                case 4:
                case 6:
                case 7:
                case 8:
                case 9:
                    throw new IllegalStateException(str2);
                case 5:
                default:
                    throw new IllegalArgumentException(str2);
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public OptimizedIntBinaryOperationInstrinsic(JsBinaryOperator jsBinaryOperator) {
            super(jsBinaryOperator);
            if (jsBinaryOperator == null) {
                $$$reportNull$$$0(0);
            }
        }

        @Override // org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.PrimitiveBinaryOperationFIF.PrimitiveBinaryOperationFunctionIntrinsic, org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.PrimitiveBinaryOperationFIF.BinaryOperationIntrinsicBase
        public JsExpression doApply(JsExpression jsExpression, JsExpression jsExpression2, TranslationContext translationContext) {
            if (jsExpression == null) {
                $$$reportNull$$$0(1);
            }
            if (jsExpression2 == null) {
                $$$reportNull$$$0(2);
            }
            if (translationContext == null) {
                $$$reportNull$$$0(3);
            }
            JsExpression jsExpressionDoApply = super.doApply(unwrapAdditive(jsExpression), unwrapAdditive(jsExpression2), translationContext);
            if (jsExpressionDoApply == null) {
                $$$reportNull$$$0(4);
            }
            return jsExpressionDoApply;
        }

        private static JsExpression unwrapAdditive(JsExpression jsExpression) {
            if (jsExpression == null) {
                $$$reportNull$$$0(5);
            }
            JsExpression jsExpressionExtractToInt32Argument = JsAstUtils.extractToInt32Argument(jsExpression);
            if (jsExpressionExtractToInt32Argument == null) {
                if (jsExpression == null) {
                    $$$reportNull$$$0(6);
                }
                return jsExpression;
            }
            if (!(jsExpressionExtractToInt32Argument instanceof JsBinaryOperation)) {
                if (jsExpression == null) {
                    $$$reportNull$$$0(7);
                }
                return jsExpression;
            }
            int i = AnonymousClass5.$SwitchMap$org$jetbrains$kotlin$js$backend$ast$JsBinaryOperator[((JsBinaryOperation) jsExpressionExtractToInt32Argument).getOperator().ordinal()];
            if (i == 1 || i == 2) {
                if (jsExpressionExtractToInt32Argument == null) {
                    $$$reportNull$$$0(8);
                }
                return jsExpressionExtractToInt32Argument;
            }
            if (jsExpression == null) {
                $$$reportNull$$$0(9);
            }
            return jsExpression;
        }
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.PrimitiveBinaryOperationFIF$5, reason: invalid class name */
    static /* synthetic */ class AnonymousClass5 {
        static final /* synthetic */ int[] $SwitchMap$org$jetbrains$kotlin$js$backend$ast$JsBinaryOperator;

        static {
            int[] iArr = new int[JsBinaryOperator.values().length];
            $SwitchMap$org$jetbrains$kotlin$js$backend$ast$JsBinaryOperator = iArr;
            try {
                iArr[JsBinaryOperator.ADD.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$js$backend$ast$JsBinaryOperator[JsBinaryOperator.SUB.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    private static class AdditiveIntBinaryOperationInstrinsic extends OptimizedIntBinaryOperationInstrinsic {
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = i != 4 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
            Object[] objArr = new Object[i != 4 ? 3 : 2];
            if (i == 1) {
                objArr[0] = "left";
            } else if (i == 2) {
                objArr[0] = "right";
            } else if (i == 3) {
                objArr[0] = "context";
            } else if (i != 4) {
                objArr[0] = "operator";
            } else {
                objArr[0] = "org/jetbrains/kotlin/js/translate/intrinsic/functions/factories/PrimitiveBinaryOperationFIF$AdditiveIntBinaryOperationInstrinsic";
            }
            if (i != 4) {
                objArr[1] = "org/jetbrains/kotlin/js/translate/intrinsic/functions/factories/PrimitiveBinaryOperationFIF$AdditiveIntBinaryOperationInstrinsic";
            } else {
                objArr[1] = "doApply";
            }
            if (i == 1 || i == 2 || i == 3) {
                objArr[2] = "doApply";
            } else if (i != 4) {
                objArr[2] = "<init>";
            }
            String str2 = String.format(str, objArr);
            if (i == 4) {
                throw new IllegalStateException(str2);
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AdditiveIntBinaryOperationInstrinsic(JsBinaryOperator jsBinaryOperator) {
            super(jsBinaryOperator);
            if (jsBinaryOperator == null) {
                $$$reportNull$$$0(0);
            }
        }

        @Override // org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.PrimitiveBinaryOperationFIF.OptimizedIntBinaryOperationInstrinsic, org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.PrimitiveBinaryOperationFIF.PrimitiveBinaryOperationFunctionIntrinsic, org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.PrimitiveBinaryOperationFIF.BinaryOperationIntrinsicBase
        public JsExpression doApply(JsExpression jsExpression, JsExpression jsExpression2, TranslationContext translationContext) {
            if (jsExpression == null) {
                $$$reportNull$$$0(1);
            }
            if (jsExpression2 == null) {
                $$$reportNull$$$0(2);
            }
            if (translationContext == null) {
                $$$reportNull$$$0(3);
            }
            JsExpression int32 = JsAstUtils.toInt32(super.doApply(jsExpression, jsExpression2, translationContext));
            if (int32 == null) {
                $$$reportNull$$$0(4);
            }
            return int32;
        }
    }

    private static class CharAndIntBinaryOperationFunctionIntrinsic extends BinaryOperationIntrinsicBase {
        private final BinaryOperationIntrinsicBase functionIntrinsic;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = i != 4 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
            Object[] objArr = new Object[i != 4 ? 3 : 2];
            if (i == 1) {
                objArr[0] = "left";
            } else if (i == 2) {
                objArr[0] = "right";
            } else if (i == 3) {
                objArr[0] = "context";
            } else if (i != 4) {
                objArr[0] = "functionIntrinsic";
            } else {
                objArr[0] = "org/jetbrains/kotlin/js/translate/intrinsic/functions/factories/PrimitiveBinaryOperationFIF$CharAndIntBinaryOperationFunctionIntrinsic";
            }
            if (i != 4) {
                objArr[1] = "org/jetbrains/kotlin/js/translate/intrinsic/functions/factories/PrimitiveBinaryOperationFIF$CharAndIntBinaryOperationFunctionIntrinsic";
            } else {
                objArr[1] = "doApply";
            }
            if (i == 1 || i == 2 || i == 3) {
                objArr[2] = "doApply";
            } else if (i != 4) {
                objArr[2] = "<init>";
            }
            String str2 = String.format(str, objArr);
            if (i == 4) {
                throw new IllegalStateException(str2);
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        private CharAndIntBinaryOperationFunctionIntrinsic(BinaryOperationIntrinsicBase binaryOperationIntrinsicBase) {
            super();
            if (binaryOperationIntrinsicBase == null) {
                $$$reportNull$$$0(0);
            }
            this.functionIntrinsic = binaryOperationIntrinsicBase;
        }

        @Override // org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.PrimitiveBinaryOperationFIF.BinaryOperationIntrinsicBase
        public JsExpression doApply(JsExpression jsExpression, JsExpression jsExpression2, TranslationContext translationContext) {
            if (jsExpression == null) {
                $$$reportNull$$$0(1);
            }
            if (jsExpression2 == null) {
                $$$reportNull$$$0(2);
            }
            if (translationContext == null) {
                $$$reportNull$$$0(3);
            }
            JsInvocation jsInvocation = AstUtilsKt.toChar(translationContext, this.functionIntrinsic.doApply(jsExpression, jsExpression2, translationContext));
            if (jsInvocation == null) {
                $$$reportNull$$$0(4);
            }
            return jsInvocation;
        }
    }

    private static class CharAndCharBinaryOperationFunctionIntrinsic extends BinaryOperationIntrinsicBase {
        private final BinaryOperationIntrinsicBase functionIntrinsic;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = i != 4 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
            Object[] objArr = new Object[i != 4 ? 3 : 2];
            if (i == 1) {
                objArr[0] = "left";
            } else if (i == 2) {
                objArr[0] = "right";
            } else if (i == 3) {
                objArr[0] = "context";
            } else if (i != 4) {
                objArr[0] = "functionIntrinsic";
            } else {
                objArr[0] = "org/jetbrains/kotlin/js/translate/intrinsic/functions/factories/PrimitiveBinaryOperationFIF$CharAndCharBinaryOperationFunctionIntrinsic";
            }
            if (i != 4) {
                objArr[1] = "org/jetbrains/kotlin/js/translate/intrinsic/functions/factories/PrimitiveBinaryOperationFIF$CharAndCharBinaryOperationFunctionIntrinsic";
            } else {
                objArr[1] = "doApply";
            }
            if (i == 1 || i == 2 || i == 3) {
                objArr[2] = "doApply";
            } else if (i != 4) {
                objArr[2] = "<init>";
            }
            String str2 = String.format(str, objArr);
            if (i == 4) {
                throw new IllegalStateException(str2);
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        private CharAndCharBinaryOperationFunctionIntrinsic(BinaryOperationIntrinsicBase binaryOperationIntrinsicBase) {
            super();
            if (binaryOperationIntrinsicBase == null) {
                $$$reportNull$$$0(0);
            }
            this.functionIntrinsic = binaryOperationIntrinsicBase;
        }

        @Override // org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.PrimitiveBinaryOperationFIF.BinaryOperationIntrinsicBase
        public JsExpression doApply(JsExpression jsExpression, JsExpression jsExpression2, TranslationContext translationContext) {
            if (jsExpression == null) {
                $$$reportNull$$$0(1);
            }
            if (jsExpression2 == null) {
                $$$reportNull$$$0(2);
            }
            if (translationContext == null) {
                $$$reportNull$$$0(3);
            }
            JsExpression jsExpressionDoApply = this.functionIntrinsic.doApply(jsExpression, jsExpression2, translationContext);
            if (jsExpressionDoApply == null) {
                $$$reportNull$$$0(4);
            }
            return jsExpressionDoApply;
        }
    }

    private static class StringAndCharBinaryOperationFunctionIntrinsic extends BinaryOperationIntrinsicBase {
        private final BinaryOperationIntrinsicBase functionIntrinsic;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = i != 4 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
            Object[] objArr = new Object[i != 4 ? 3 : 2];
            if (i == 1) {
                objArr[0] = "left";
            } else if (i == 2) {
                objArr[0] = "right";
            } else if (i == 3) {
                objArr[0] = "context";
            } else if (i != 4) {
                objArr[0] = "functionIntrinsic";
            } else {
                objArr[0] = "org/jetbrains/kotlin/js/translate/intrinsic/functions/factories/PrimitiveBinaryOperationFIF$StringAndCharBinaryOperationFunctionIntrinsic";
            }
            if (i != 4) {
                objArr[1] = "org/jetbrains/kotlin/js/translate/intrinsic/functions/factories/PrimitiveBinaryOperationFIF$StringAndCharBinaryOperationFunctionIntrinsic";
            } else {
                objArr[1] = "doApply";
            }
            if (i == 1 || i == 2 || i == 3) {
                objArr[2] = "doApply";
            } else if (i != 4) {
                objArr[2] = "<init>";
            }
            String str2 = String.format(str, objArr);
            if (i == 4) {
                throw new IllegalStateException(str2);
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        private StringAndCharBinaryOperationFunctionIntrinsic(BinaryOperationIntrinsicBase binaryOperationIntrinsicBase) {
            super();
            if (binaryOperationIntrinsicBase == null) {
                $$$reportNull$$$0(0);
            }
            this.functionIntrinsic = binaryOperationIntrinsicBase;
        }

        @Override // org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.PrimitiveBinaryOperationFIF.BinaryOperationIntrinsicBase
        public JsExpression doApply(JsExpression jsExpression, JsExpression jsExpression2, TranslationContext translationContext) {
            if (jsExpression == null) {
                $$$reportNull$$$0(1);
            }
            if (jsExpression2 == null) {
                $$$reportNull$$$0(2);
            }
            if (translationContext == null) {
                $$$reportNull$$$0(3);
            }
            JsExpression jsExpressionDoApply = this.functionIntrinsic.doApply(jsExpression, TopLevelFIF.TO_STRING.apply(jsExpression2, Collections.emptyList(), translationContext), translationContext);
            if (jsExpressionDoApply == null) {
                $$$reportNull$$$0(4);
            }
            return jsExpressionDoApply;
        }
    }
}
