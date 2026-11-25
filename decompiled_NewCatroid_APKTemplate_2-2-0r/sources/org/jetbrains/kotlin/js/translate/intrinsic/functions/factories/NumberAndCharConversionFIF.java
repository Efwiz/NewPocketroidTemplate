package org.jetbrains.kotlin.js.translate.intrinsic.functions.factories;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsInvocation;
import org.jetbrains.kotlin.js.patterns.PatternBuilder;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.js.translate.intrinsic.functions.basic.FunctionIntrinsicWithReceiverComputed;
import org.jetbrains.kotlin.js.translate.utils.JsAstUtils;
import org.jetbrains.kotlin.js.translate.utils.jsAstUtils.AstUtilsKt;

/* JADX INFO: compiled from: NumberAndCharConversionFIF.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001:\u0001\fB\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u001a\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b0\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/js/translate/intrinsic/functions/factories/NumberAndCharConversionFIF;", "Lorg/jetbrains/kotlin/js/translate/intrinsic/functions/factories/CompositeFIF;", "()V", "USE_AS_IS", "Ljava/util/function/Predicate;", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "getUSE_AS_IS", "()Ljava/util/function/Predicate;", "convertOperations", "", "", "Lorg/jetbrains/kotlin/js/translate/intrinsic/functions/basic/FunctionIntrinsicWithReceiverComputed;", "ConversionUnaryIntrinsic", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NumberAndCharConversionFIF extends CompositeFIF {
    public static final NumberAndCharConversionFIF INSTANCE;
    private static final Predicate<FunctionDescriptor> USE_AS_IS;
    private static final Map<String, FunctionIntrinsicWithReceiverComputed> convertOperations;

    private NumberAndCharConversionFIF() {
    }

    static {
        NumberAndCharConversionFIF numberAndCharConversionFIF = new NumberAndCharConversionFIF();
        INSTANCE = numberAndCharConversionFIF;
        Predicate<FunctionDescriptor> predicateOr = PatternBuilder.pattern("Int.toInt|toFloat|toDouble").or(PatternBuilder.pattern("Short.toShort|toInt|toFloat|toDouble")).or(PatternBuilder.pattern("Byte.toByte|toShort|toInt|toFloat|toDouble")).or(PatternBuilder.pattern("Float|Double.toFloat|toDouble")).or(PatternBuilder.pattern("Long.toLong")).or(PatternBuilder.pattern("Char.toChar"));
        Intrinsics.checkNotNullExpressionValue(predicateOr, "or(...)");
        USE_AS_IS = predicateOr;
        Map<String, FunctionIntrinsicWithReceiverComputed> mapMapOf = MapsKt.mapOf(TuplesKt.to("Float|Double.toInt", new ConversionUnaryIntrinsic(new Function2<TranslationContext, JsExpression, JsExpression>() { // from class: org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.NumberAndCharConversionFIF$convertOperations$1
            @Override // kotlin.jvm.functions.Function2
            public final JsExpression invoke(TranslationContext $receiver, JsExpression it) {
                Intrinsics.checkNotNullParameter($receiver, "$this$$receiver");
                Intrinsics.checkNotNullParameter(it, "it");
                return AstUtilsKt.invokeKotlinFunction($receiver, "numberToInt", it);
            }
        })), TuplesKt.to("Float|Double.toShort", new ConversionUnaryIntrinsic(new Function2<TranslationContext, JsExpression, JsExpression>() { // from class: org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.NumberAndCharConversionFIF$convertOperations$2
            @Override // kotlin.jvm.functions.Function2
            public final JsExpression invoke(TranslationContext $receiver, JsExpression it) {
                Intrinsics.checkNotNullParameter($receiver, "$this$$receiver");
                Intrinsics.checkNotNullParameter(it, "it");
                return AstUtilsKt.toShort($receiver, AstUtilsKt.invokeKotlinFunction($receiver, "numberToInt", it));
            }
        })), TuplesKt.to("Int.toShort", new ConversionUnaryIntrinsic(new Function2<TranslationContext, JsExpression, JsExpression>() { // from class: org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.NumberAndCharConversionFIF$convertOperations$3
            @Override // kotlin.jvm.functions.Function2
            public final JsExpression invoke(TranslationContext $receiver, JsExpression it) {
                Intrinsics.checkNotNullParameter($receiver, "$this$$receiver");
                Intrinsics.checkNotNullParameter(it, "it");
                return AstUtilsKt.toShort($receiver, it);
            }
        })), TuplesKt.to("Float|Double.toByte", new ConversionUnaryIntrinsic(new Function2<TranslationContext, JsExpression, JsExpression>() { // from class: org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.NumberAndCharConversionFIF$convertOperations$4
            @Override // kotlin.jvm.functions.Function2
            public final JsExpression invoke(TranslationContext $receiver, JsExpression it) {
                Intrinsics.checkNotNullParameter($receiver, "$this$$receiver");
                Intrinsics.checkNotNullParameter(it, "it");
                return AstUtilsKt.toByte($receiver, AstUtilsKt.invokeKotlinFunction($receiver, "numberToInt", it));
            }
        })), TuplesKt.to("Short|Int.toByte", new ConversionUnaryIntrinsic(new Function2<TranslationContext, JsExpression, JsExpression>() { // from class: org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.NumberAndCharConversionFIF$convertOperations$5
            @Override // kotlin.jvm.functions.Function2
            public final JsExpression invoke(TranslationContext $receiver, JsExpression it) {
                Intrinsics.checkNotNullParameter($receiver, "$this$$receiver");
                Intrinsics.checkNotNullParameter(it, "it");
                return AstUtilsKt.toByte($receiver, it);
            }
        })), TuplesKt.to("Int|Short|Byte.toLong", new ConversionUnaryIntrinsic(new Function2<TranslationContext, JsExpression, JsExpression>() { // from class: org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.NumberAndCharConversionFIF$convertOperations$6
            @Override // kotlin.jvm.functions.Function2
            public final JsExpression invoke(TranslationContext $receiver, JsExpression it) {
                Intrinsics.checkNotNullParameter($receiver, "$this$$receiver");
                Intrinsics.checkNotNullParameter(it, "it");
                JsExpression jsExpressionLongFromInt = JsAstUtils.longFromInt(it);
                Intrinsics.checkNotNullExpressionValue(jsExpressionLongFromInt, "longFromInt(...)");
                return jsExpressionLongFromInt;
            }
        })), TuplesKt.to("Float|Double.toLong", new ConversionUnaryIntrinsic(new Function2<TranslationContext, JsExpression, JsExpression>() { // from class: org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.NumberAndCharConversionFIF$convertOperations$7
            @Override // kotlin.jvm.functions.Function2
            public final JsExpression invoke(TranslationContext $receiver, JsExpression it) {
                Intrinsics.checkNotNullParameter($receiver, "$this$$receiver");
                Intrinsics.checkNotNullParameter(it, "it");
                JsExpression jsExpressionLongFromNumber = JsAstUtils.longFromNumber(it);
                Intrinsics.checkNotNullExpressionValue(jsExpressionLongFromNumber, "longFromNumber(...)");
                return jsExpressionLongFromNumber;
            }
        })), TuplesKt.to("Char.toDouble|toFloat|toInt", new ConversionUnaryIntrinsic(new Function2<TranslationContext, JsExpression, JsExpression>() { // from class: org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.NumberAndCharConversionFIF$convertOperations$8
            @Override // kotlin.jvm.functions.Function2
            public final JsExpression invoke(TranslationContext $receiver, JsExpression it) {
                Intrinsics.checkNotNullParameter($receiver, "$this$$receiver");
                Intrinsics.checkNotNullParameter(it, "it");
                JsExpression jsExpressionCharToInt = JsAstUtils.charToInt(it);
                Intrinsics.checkNotNullExpressionValue(jsExpressionCharToInt, "charToInt(...)");
                return jsExpressionCharToInt;
            }
        })), TuplesKt.to("Char.toShort", new ConversionUnaryIntrinsic(new Function2<TranslationContext, JsExpression, JsExpression>() { // from class: org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.NumberAndCharConversionFIF$convertOperations$9
            @Override // kotlin.jvm.functions.Function2
            public final JsExpression invoke(TranslationContext $receiver, JsExpression it) {
                Intrinsics.checkNotNullParameter($receiver, "$this$$receiver");
                Intrinsics.checkNotNullParameter(it, "it");
                JsExpression jsExpressionCharToInt = JsAstUtils.charToInt(it);
                Intrinsics.checkNotNullExpressionValue(jsExpressionCharToInt, "charToInt(...)");
                return AstUtilsKt.toShort($receiver, jsExpressionCharToInt);
            }
        })), TuplesKt.to("Char.toByte", new ConversionUnaryIntrinsic(new Function2<TranslationContext, JsExpression, JsExpression>() { // from class: org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.NumberAndCharConversionFIF$convertOperations$10
            @Override // kotlin.jvm.functions.Function2
            public final JsExpression invoke(TranslationContext $receiver, JsExpression it) {
                Intrinsics.checkNotNullParameter($receiver, "$this$$receiver");
                Intrinsics.checkNotNullParameter(it, "it");
                JsExpression jsExpressionCharToInt = JsAstUtils.charToInt(it);
                Intrinsics.checkNotNullExpressionValue(jsExpressionCharToInt, "charToInt(...)");
                return AstUtilsKt.toByte($receiver, jsExpressionCharToInt);
            }
        })), TuplesKt.to("Char.toLong", new ConversionUnaryIntrinsic(new Function2<TranslationContext, JsExpression, JsExpression>() { // from class: org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.NumberAndCharConversionFIF$convertOperations$11
            @Override // kotlin.jvm.functions.Function2
            public final JsExpression invoke(TranslationContext $receiver, JsExpression it) {
                Intrinsics.checkNotNullParameter($receiver, "$this$$receiver");
                Intrinsics.checkNotNullParameter(it, "it");
                JsExpression jsExpressionLongFromInt = JsAstUtils.longFromInt(JsAstUtils.charToInt(it));
                Intrinsics.checkNotNullExpressionValue(jsExpressionLongFromInt, "longFromInt(...)");
                return jsExpressionLongFromInt;
            }
        })), TuplesKt.to("Number.toInt", new ConversionUnaryIntrinsic(new Function2<TranslationContext, JsExpression, JsExpression>() { // from class: org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.NumberAndCharConversionFIF$convertOperations$12
            @Override // kotlin.jvm.functions.Function2
            public final JsExpression invoke(TranslationContext $receiver, JsExpression it) {
                Intrinsics.checkNotNullParameter($receiver, "$this$$receiver");
                Intrinsics.checkNotNullParameter(it, "it");
                return AstUtilsKt.invokeKotlinFunction($receiver, "numberToInt", it);
            }
        })), TuplesKt.to("Number.toShort", new ConversionUnaryIntrinsic(new Function2<TranslationContext, JsExpression, JsExpression>() { // from class: org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.NumberAndCharConversionFIF$convertOperations$13
            @Override // kotlin.jvm.functions.Function2
            public final JsExpression invoke(TranslationContext $receiver, JsExpression it) {
                Intrinsics.checkNotNullParameter($receiver, "$this$$receiver");
                Intrinsics.checkNotNullParameter(it, "it");
                return AstUtilsKt.invokeKotlinFunction($receiver, "numberToShort", it);
            }
        })), TuplesKt.to("Number.toByte", new ConversionUnaryIntrinsic(new Function2<TranslationContext, JsExpression, JsExpression>() { // from class: org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.NumberAndCharConversionFIF$convertOperations$14
            @Override // kotlin.jvm.functions.Function2
            public final JsExpression invoke(TranslationContext $receiver, JsExpression it) {
                Intrinsics.checkNotNullParameter($receiver, "$this$$receiver");
                Intrinsics.checkNotNullParameter(it, "it");
                return AstUtilsKt.invokeKotlinFunction($receiver, "numberToByte", it);
            }
        })), TuplesKt.to("Number.toChar", new ConversionUnaryIntrinsic(new Function2<TranslationContext, JsExpression, JsExpression>() { // from class: org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.NumberAndCharConversionFIF$convertOperations$15
            @Override // kotlin.jvm.functions.Function2
            public final JsExpression invoke(TranslationContext $receiver, JsExpression it) {
                Intrinsics.checkNotNullParameter($receiver, "$this$$receiver");
                Intrinsics.checkNotNullParameter(it, "it");
                return AstUtilsKt.invokeKotlinFunction($receiver, "numberToChar", it);
            }
        })), TuplesKt.to("Number.toFloat|toDouble", new ConversionUnaryIntrinsic(new Function2<TranslationContext, JsExpression, JsExpression>() { // from class: org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.NumberAndCharConversionFIF$convertOperations$16
            @Override // kotlin.jvm.functions.Function2
            public final JsExpression invoke(TranslationContext $receiver, JsExpression it) {
                Intrinsics.checkNotNullParameter($receiver, "$this$$receiver");
                Intrinsics.checkNotNullParameter(it, "it");
                return AstUtilsKt.invokeKotlinFunction($receiver, "numberToDouble", it);
            }
        })), TuplesKt.to("Number.toLong", new ConversionUnaryIntrinsic(new Function2<TranslationContext, JsExpression, JsExpression>() { // from class: org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.NumberAndCharConversionFIF$convertOperations$17
            @Override // kotlin.jvm.functions.Function2
            public final JsExpression invoke(TranslationContext $receiver, JsExpression it) {
                Intrinsics.checkNotNullParameter($receiver, "$this$$receiver");
                Intrinsics.checkNotNullParameter(it, "it");
                return AstUtilsKt.invokeKotlinFunction($receiver, "numberToLong", it);
            }
        })), TuplesKt.to("Float|Double.toChar", new ConversionUnaryIntrinsic(new Function2<TranslationContext, JsExpression, JsExpression>() { // from class: org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.NumberAndCharConversionFIF$convertOperations$18
            @Override // kotlin.jvm.functions.Function2
            public final JsExpression invoke(TranslationContext $receiver, JsExpression it) {
                Intrinsics.checkNotNullParameter($receiver, "$this$$receiver");
                Intrinsics.checkNotNullParameter(it, "it");
                return AstUtilsKt.toChar($receiver, AstUtilsKt.invokeKotlinFunction($receiver, "numberToInt", it));
            }
        })), TuplesKt.to("Int|Short|Byte.toChar", new ConversionUnaryIntrinsic(new Function2<TranslationContext, JsExpression, JsExpression>() { // from class: org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.NumberAndCharConversionFIF$convertOperations$19
            @Override // kotlin.jvm.functions.Function2
            public final JsExpression invoke(TranslationContext $receiver, JsExpression it) {
                Intrinsics.checkNotNullParameter($receiver, "$this$$receiver");
                Intrinsics.checkNotNullParameter(it, "it");
                return AstUtilsKt.toChar($receiver, it);
            }
        })), TuplesKt.to("Long.toFloat|toDouble", new ConversionUnaryIntrinsic(new Function2<TranslationContext, JsExpression, JsExpression>() { // from class: org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.NumberAndCharConversionFIF$convertOperations$20
            @Override // kotlin.jvm.functions.Function2
            public final JsExpression invoke(TranslationContext $receiver, JsExpression it) {
                Intrinsics.checkNotNullParameter($receiver, "$this$$receiver");
                Intrinsics.checkNotNullParameter(it, "it");
                JsInvocation jsInvocationInvokeMethod = JsAstUtils.invokeMethod(it, Namer.LONG_TO_NUMBER, new JsExpression[0]);
                Intrinsics.checkNotNullExpressionValue(jsInvocationInvokeMethod, "invokeMethod(...)");
                return jsInvocationInvokeMethod;
            }
        })), TuplesKt.to("Long.toInt", new ConversionUnaryIntrinsic(new Function2<TranslationContext, JsExpression, JsExpression>() { // from class: org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.NumberAndCharConversionFIF$convertOperations$21
            @Override // kotlin.jvm.functions.Function2
            public final JsExpression invoke(TranslationContext $receiver, JsExpression it) {
                Intrinsics.checkNotNullParameter($receiver, "$this$$receiver");
                Intrinsics.checkNotNullParameter(it, "it");
                JsInvocation jsInvocationInvokeMethod = JsAstUtils.invokeMethod(it, "toInt", new JsExpression[0]);
                Intrinsics.checkNotNullExpressionValue(jsInvocationInvokeMethod, "invokeMethod(...)");
                return jsInvocationInvokeMethod;
            }
        })), TuplesKt.to("Long.toShort", new ConversionUnaryIntrinsic(new Function2<TranslationContext, JsExpression, JsExpression>() { // from class: org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.NumberAndCharConversionFIF$convertOperations$22
            @Override // kotlin.jvm.functions.Function2
            public final JsExpression invoke(TranslationContext $receiver, JsExpression it) {
                Intrinsics.checkNotNullParameter($receiver, "$this$$receiver");
                Intrinsics.checkNotNullParameter(it, "it");
                JsInvocation jsInvocationInvokeMethod = JsAstUtils.invokeMethod(it, "toInt", new JsExpression[0]);
                Intrinsics.checkNotNullExpressionValue(jsInvocationInvokeMethod, "invokeMethod(...)");
                return AstUtilsKt.toShort($receiver, jsInvocationInvokeMethod);
            }
        })), TuplesKt.to("Long.toByte", new ConversionUnaryIntrinsic(new Function2<TranslationContext, JsExpression, JsExpression>() { // from class: org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.NumberAndCharConversionFIF$convertOperations$23
            @Override // kotlin.jvm.functions.Function2
            public final JsExpression invoke(TranslationContext $receiver, JsExpression it) {
                Intrinsics.checkNotNullParameter($receiver, "$this$$receiver");
                Intrinsics.checkNotNullParameter(it, "it");
                JsInvocation jsInvocationInvokeMethod = JsAstUtils.invokeMethod(it, "toInt", new JsExpression[0]);
                Intrinsics.checkNotNullExpressionValue(jsInvocationInvokeMethod, "invokeMethod(...)");
                return AstUtilsKt.toByte($receiver, jsInvocationInvokeMethod);
            }
        })), TuplesKt.to("Long.toChar", new ConversionUnaryIntrinsic(new Function2<TranslationContext, JsExpression, JsExpression>() { // from class: org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.NumberAndCharConversionFIF$convertOperations$24
            @Override // kotlin.jvm.functions.Function2
            public final JsExpression invoke(TranslationContext $receiver, JsExpression it) {
                Intrinsics.checkNotNullParameter($receiver, "$this$$receiver");
                Intrinsics.checkNotNullParameter(it, "it");
                JsInvocation jsInvocationInvokeMethod = JsAstUtils.invokeMethod(it, "toInt", new JsExpression[0]);
                Intrinsics.checkNotNullExpressionValue(jsInvocationInvokeMethod, "invokeMethod(...)");
                return AstUtilsKt.toChar($receiver, jsInvocationInvokeMethod);
            }
        })));
        convertOperations = mapMapOf;
        numberAndCharConversionFIF.add(predicateOr, new ConversionUnaryIntrinsic(new Function2<TranslationContext, JsExpression, JsExpression>() { // from class: org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.NumberAndCharConversionFIF.1
            @Override // kotlin.jvm.functions.Function2
            public final JsExpression invoke(TranslationContext $receiver, JsExpression it) {
                Intrinsics.checkNotNullParameter($receiver, "$this$$receiver");
                Intrinsics.checkNotNullParameter(it, "it");
                return it;
            }
        }));
        for (Map.Entry<String, FunctionIntrinsicWithReceiverComputed> entry : mapMapOf.entrySet()) {
            INSTANCE.add(PatternBuilder.pattern(entry.getKey()), entry.getValue());
        }
    }

    public final Predicate<FunctionDescriptor> getUSE_AS_IS() {
        return USE_AS_IS;
    }

    /* JADX INFO: compiled from: NumberAndCharConversionFIF.kt */
    @Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\b\u0002\u0018\u00002\u00020\u0001B3\u0012,\u0010\u0002\u001a(\u0012\u0004\u0012\u00020\u0004\u0012\u0013\u0012\u00110\u0005¢\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\b\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\u0002\b\t¢\u0006\u0002\u0010\nJ(\u0010\r\u001a\u00020\u00052\b\u0010\b\u001a\u0004\u0018\u00010\u00052\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00050\u000f2\u0006\u0010\u0010\u001a\u00020\u0004H\u0016R7\u0010\u0002\u001a(\u0012\u0004\u0012\u00020\u0004\u0012\u0013\u0012\u00110\u0005¢\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\b\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\u0002\b\t¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/js/translate/intrinsic/functions/factories/NumberAndCharConversionFIF$ConversionUnaryIntrinsic;", "Lorg/jetbrains/kotlin/js/translate/intrinsic/functions/basic/FunctionIntrinsicWithReceiverComputed;", "applyFun", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/js/translate/context/TranslationContext;", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "Lkotlin/ParameterName;", "name", "receiver", "Lkotlin/ExtensionFunctionType;", "(Lkotlin/jvm/functions/Function2;)V", "getApplyFun", "()Lkotlin/jvm/functions/Function2;", "apply", "arguments", "", "context", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ConversionUnaryIntrinsic extends FunctionIntrinsicWithReceiverComputed {
        private final Function2<TranslationContext, JsExpression, JsExpression> applyFun;

        /* JADX WARN: Multi-variable type inference failed */
        public ConversionUnaryIntrinsic(Function2<? super TranslationContext, ? super JsExpression, ? extends JsExpression> applyFun) {
            Intrinsics.checkNotNullParameter(applyFun, "applyFun");
            this.applyFun = applyFun;
        }

        public final Function2<TranslationContext, JsExpression, JsExpression> getApplyFun() {
            return this.applyFun;
        }

        @Override // org.jetbrains.kotlin.js.translate.intrinsic.functions.basic.FunctionIntrinsicWithReceiverComputed
        public JsExpression apply(JsExpression receiver, List<? extends JsExpression> arguments, TranslationContext context) {
            Intrinsics.checkNotNullParameter(arguments, "arguments");
            Intrinsics.checkNotNullParameter(context, "context");
            arguments.isEmpty();
            Function2<TranslationContext, JsExpression, JsExpression> function2 = this.applyFun;
            Intrinsics.checkNotNull(receiver);
            return function2.invoke(context, receiver);
        }
    }
}
