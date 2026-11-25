package org.jetbrains.kotlin.js.translate.intrinsic.functions.factories;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsInvocation;
import org.jetbrains.kotlin.js.patterns.DescriptorPredicate;
import org.jetbrains.kotlin.js.patterns.PatternBuilder;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.js.translate.intrinsic.functions.basic.FunctionIntrinsic;
import org.jetbrains.kotlin.js.translate.intrinsic.functions.basic.FunctionIntrinsicWithReceiverComputed;
import org.jetbrains.kotlin.js.translate.utils.JsAstUtils;
import org.jetbrains.kotlin.utils.FunctionsKt;

/* JADX INFO: compiled from: LongOperationFIF.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001:\u0001&B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001a\u0010\u0018\u001a\u0004\u0018\u00010\u00192\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001dH\u0016J\u000e\u0010\u001e\u001a\u00020\u00162\u0006\u0010\u001f\u001a\u00020\u0015J:\u0010 \u001a\u0004\u0018\u00010\u00192\b\u0010!\u001a\u0004\u0018\u00010\u00162\u0012\u0010\"\u001a\u000e\u0012\u0004\u0012\u00020$\u0012\u0004\u0012\u00020$0#2\u0012\u0010%\u001a\u000e\u0012\u0004\u0012\u00020$\u0012\u0004\u0012\u00020$0#R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0006R\u0011\u0010\t\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0006R\u0011\u0010\u000b\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u0006R\u0011\u0010\r\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u0006R\u0011\u0010\u000f\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0006R\u0011\u0010\u0011\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0006R\u001a\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u00160\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u00160\u0014X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006'"}, d2 = {"Lorg/jetbrains/kotlin/js/translate/intrinsic/functions/factories/LongOperationFIF;", "Lorg/jetbrains/kotlin/js/translate/intrinsic/functions/factories/FunctionIntrinsicFactory;", "()V", "FLOATING_POINT_BINARY_OPERATION_LONG", "Lorg/jetbrains/kotlin/js/patterns/DescriptorPredicate;", "getFLOATING_POINT_BINARY_OPERATION_LONG", "()Lorg/jetbrains/kotlin/js/patterns/DescriptorPredicate;", "INTEGER_BINARY_OPERATION_LONG", "getINTEGER_BINARY_OPERATION_LONG", "LONG_BINARY_OPERATION_FLOATING_POINT", "getLONG_BINARY_OPERATION_FLOATING_POINT", "LONG_BINARY_OPERATION_INTEGER", "getLONG_BINARY_OPERATION_INTEGER", "LONG_BINARY_OPERATION_LONG", "getLONG_BINARY_OPERATION_LONG", "LONG_BIT_SHIFTS", "getLONG_BIT_SHIFTS", "LONG_EQUALS_ANY", "getLONG_EQUALS_ANY", "floatBinaryIntrinsics", "", "", "Lorg/jetbrains/kotlin/js/translate/intrinsic/functions/factories/LongOperationFIF$BaseBinaryIntrinsic;", "longBinaryIntrinsics", "getIntrinsic", "Lorg/jetbrains/kotlin/js/translate/intrinsic/functions/basic/FunctionIntrinsic;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "context", "Lorg/jetbrains/kotlin/js/translate/context/TranslationContext;", "methodIntrinsic", "methodName", "wrapIntrinsicIfPresent", "intrinsic", "toLeft", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "toRight", "BaseBinaryIntrinsic", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LongOperationFIF implements FunctionIntrinsicFactory {
    private static final DescriptorPredicate FLOATING_POINT_BINARY_OPERATION_LONG;
    public static final LongOperationFIF INSTANCE = new LongOperationFIF();
    private static final DescriptorPredicate INTEGER_BINARY_OPERATION_LONG;
    private static final DescriptorPredicate LONG_BINARY_OPERATION_FLOATING_POINT;
    private static final DescriptorPredicate LONG_BINARY_OPERATION_INTEGER;
    private static final DescriptorPredicate LONG_BINARY_OPERATION_LONG;
    private static final DescriptorPredicate LONG_BIT_SHIFTS;
    private static final DescriptorPredicate LONG_EQUALS_ANY;
    private static final Map<String, BaseBinaryIntrinsic> floatBinaryIntrinsics;
    private static final Map<String, BaseBinaryIntrinsic> longBinaryIntrinsics;

    private LongOperationFIF() {
    }

    static {
        DescriptorPredicate descriptorPredicatePattern = PatternBuilder.pattern("Long.equals");
        Intrinsics.checkNotNullExpressionValue(descriptorPredicatePattern, "pattern(...)");
        LONG_EQUALS_ANY = descriptorPredicatePattern;
        DescriptorPredicate descriptorPredicatePattern2 = PatternBuilder.pattern("Long.compareTo|rangeTo|plus|minus|times|div|mod|rem|and|or|xor(Long)");
        Intrinsics.checkNotNullExpressionValue(descriptorPredicatePattern2, "pattern(...)");
        LONG_BINARY_OPERATION_LONG = descriptorPredicatePattern2;
        DescriptorPredicate descriptorPredicatePattern3 = PatternBuilder.pattern("Long.shl|shr|ushr(Int)");
        Intrinsics.checkNotNullExpressionValue(descriptorPredicatePattern3, "pattern(...)");
        LONG_BIT_SHIFTS = descriptorPredicatePattern3;
        DescriptorPredicate descriptorPredicatePattern4 = PatternBuilder.pattern("Long.compareTo|rangeTo|plus|minus|times|div|mod|rem(Int|Short|Byte)");
        Intrinsics.checkNotNullExpressionValue(descriptorPredicatePattern4, "pattern(...)");
        LONG_BINARY_OPERATION_INTEGER = descriptorPredicatePattern4;
        DescriptorPredicate descriptorPredicatePattern5 = PatternBuilder.pattern("Long.compareTo|plus|minus|times|div|mod|rem(Double|Float)");
        Intrinsics.checkNotNullExpressionValue(descriptorPredicatePattern5, "pattern(...)");
        LONG_BINARY_OPERATION_FLOATING_POINT = descriptorPredicatePattern5;
        DescriptorPredicate descriptorPredicatePattern6 = PatternBuilder.pattern("Int|Short|Byte.compareTo|rangeTo|plus|minus|times|div|mod|rem(Long)");
        Intrinsics.checkNotNullExpressionValue(descriptorPredicatePattern6, "pattern(...)");
        INTEGER_BINARY_OPERATION_LONG = descriptorPredicatePattern6;
        DescriptorPredicate descriptorPredicatePattern7 = PatternBuilder.pattern("Double|Float.compareTo|plus|minus|times|div|mod|rem(Long)");
        Intrinsics.checkNotNullExpressionValue(descriptorPredicatePattern7, "pattern(...)");
        FLOATING_POINT_BINARY_OPERATION_LONG = descriptorPredicatePattern7;
        List<Pair> listListOf = CollectionsKt.listOf((Object[]) new Pair[]{TuplesKt.to(Namer.EQUALS_METHOD_NAME, Namer.EQUALS_METHOD_NAME), TuplesKt.to("compareTo", Namer.COMPARE_TO_METHOD_NAME), TuplesKt.to("rangeTo", "rangeTo"), TuplesKt.to("plus", ProductAction.ACTION_ADD), TuplesKt.to("minus", "subtract"), TuplesKt.to("times", "multiply"), TuplesKt.to("div", "div"), TuplesKt.to("mod", "modulo"), TuplesKt.to("rem", "modulo"), TuplesKt.to("shl", "shiftLeft"), TuplesKt.to("shr", "shiftRight"), TuplesKt.to("ushr", "shiftRightUnsigned"), TuplesKt.to("and", "and"), TuplesKt.to("or", "or"), TuplesKt.to("xor", "xor")});
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listListOf, 10));
        for (Pair pair : listListOf) {
            arrayList.add(TuplesKt.to(pair.getFirst(), INSTANCE.methodIntrinsic((String) pair.getSecond())));
        }
        longBinaryIntrinsics = MapsKt.toMap(arrayList);
        floatBinaryIntrinsics = MapsKt.mapOf(TuplesKt.to("compareTo", new BaseBinaryIntrinsic(LongOperationFIF$floatBinaryIntrinsics$1.INSTANCE)), TuplesKt.to("plus", new BaseBinaryIntrinsic(LongOperationFIF$floatBinaryIntrinsics$2.INSTANCE)), TuplesKt.to("minus", new BaseBinaryIntrinsic(LongOperationFIF$floatBinaryIntrinsics$3.INSTANCE)), TuplesKt.to("times", new BaseBinaryIntrinsic(LongOperationFIF$floatBinaryIntrinsics$4.INSTANCE)), TuplesKt.to("div", new BaseBinaryIntrinsic(LongOperationFIF$floatBinaryIntrinsics$5.INSTANCE)), TuplesKt.to("mod", new BaseBinaryIntrinsic(LongOperationFIF$floatBinaryIntrinsics$6.INSTANCE)), TuplesKt.to("rem", new BaseBinaryIntrinsic(LongOperationFIF$floatBinaryIntrinsics$7.INSTANCE)));
    }

    public final DescriptorPredicate getLONG_EQUALS_ANY() {
        return LONG_EQUALS_ANY;
    }

    public final DescriptorPredicate getLONG_BINARY_OPERATION_LONG() {
        return LONG_BINARY_OPERATION_LONG;
    }

    public final DescriptorPredicate getLONG_BIT_SHIFTS() {
        return LONG_BIT_SHIFTS;
    }

    public final DescriptorPredicate getLONG_BINARY_OPERATION_INTEGER() {
        return LONG_BINARY_OPERATION_INTEGER;
    }

    public final DescriptorPredicate getLONG_BINARY_OPERATION_FLOATING_POINT() {
        return LONG_BINARY_OPERATION_FLOATING_POINT;
    }

    public final DescriptorPredicate getINTEGER_BINARY_OPERATION_LONG() {
        return INTEGER_BINARY_OPERATION_LONG;
    }

    public final DescriptorPredicate getFLOATING_POINT_BINARY_OPERATION_LONG() {
        return FLOATING_POINT_BINARY_OPERATION_LONG;
    }

    /* JADX INFO: compiled from: LongOperationFIF.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B=\u00126\u0010\u0002\u001a2\u0012\u0013\u0012\u00110\u0004¢\u0006\f\b\u0005\u0012\b\b\u0006\u0012\u0004\b\b(\u0007\u0012\u0013\u0012\u00110\u0004¢\u0006\f\b\u0005\u0012\b\b\u0006\u0012\u0004\b\b(\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\tJ(\u0010\f\u001a\u00020\u00042\b\u0010\r\u001a\u0004\u0018\u00010\u00042\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00040\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016RA\u0010\u0002\u001a2\u0012\u0013\u0012\u00110\u0004¢\u0006\f\b\u0005\u0012\b\b\u0006\u0012\u0004\b\b(\u0007\u0012\u0013\u0012\u00110\u0004¢\u0006\f\b\u0005\u0012\b\b\u0006\u0012\u0004\b\b(\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/js/translate/intrinsic/functions/factories/LongOperationFIF$BaseBinaryIntrinsic;", "Lorg/jetbrains/kotlin/js/translate/intrinsic/functions/basic/FunctionIntrinsicWithReceiverComputed;", "applyFun", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "Lkotlin/ParameterName;", "name", "left", "right", "(Lkotlin/jvm/functions/Function2;)V", "getApplyFun", "()Lkotlin/jvm/functions/Function2;", "apply", "receiver", "arguments", "", "context", "Lorg/jetbrains/kotlin/js/translate/context/TranslationContext;", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class BaseBinaryIntrinsic extends FunctionIntrinsicWithReceiverComputed {
        private final Function2<JsExpression, JsExpression, JsExpression> applyFun;

        public final Function2<JsExpression, JsExpression, JsExpression> getApplyFun() {
            return this.applyFun;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public BaseBinaryIntrinsic(Function2<? super JsExpression, ? super JsExpression, ? extends JsExpression> applyFun) {
            Intrinsics.checkNotNullParameter(applyFun, "applyFun");
            this.applyFun = applyFun;
        }

        @Override // org.jetbrains.kotlin.js.translate.intrinsic.functions.basic.FunctionIntrinsicWithReceiverComputed
        public JsExpression apply(JsExpression receiver, List<? extends JsExpression> arguments, TranslationContext context) {
            Intrinsics.checkNotNullParameter(arguments, "arguments");
            Intrinsics.checkNotNullParameter(context, "context");
            arguments.size();
            Function2<JsExpression, JsExpression, JsExpression> function2 = this.applyFun;
            Intrinsics.checkNotNull(receiver);
            return function2.invoke(receiver, arguments.get(0));
        }
    }

    public final BaseBinaryIntrinsic methodIntrinsic(final String methodName) {
        Intrinsics.checkNotNullParameter(methodName, "methodName");
        return new BaseBinaryIntrinsic(new Function2<JsExpression, JsExpression, JsExpression>() { // from class: org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.LongOperationFIF.methodIntrinsic.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final JsExpression invoke(JsExpression left, JsExpression right) {
                Intrinsics.checkNotNullParameter(left, "left");
                Intrinsics.checkNotNullParameter(right, "right");
                JsInvocation jsInvocationInvokeMethod = JsAstUtils.invokeMethod(left, methodName, right);
                Intrinsics.checkNotNullExpressionValue(jsInvocationInvokeMethod, "invokeMethod(...)");
                return jsInvocationInvokeMethod;
            }
        });
    }

    public final FunctionIntrinsic wrapIntrinsicIfPresent(final BaseBinaryIntrinsic intrinsic, final Function1<? super JsExpression, ? extends JsExpression> toLeft, final Function1<? super JsExpression, ? extends JsExpression> toRight) {
        Intrinsics.checkNotNullParameter(toLeft, "toLeft");
        Intrinsics.checkNotNullParameter(toRight, "toRight");
        if (intrinsic != null) {
            return new BaseBinaryIntrinsic(new Function2<JsExpression, JsExpression, JsExpression>() { // from class: org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.LongOperationFIF.wrapIntrinsicIfPresent.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final JsExpression invoke(JsExpression left, JsExpression right) {
                    Intrinsics.checkNotNullParameter(left, "left");
                    Intrinsics.checkNotNullParameter(right, "right");
                    return intrinsic.getApplyFun().invoke(toLeft.invoke(left), toRight.invoke(right));
                }
            });
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.FunctionIntrinsicFactory
    public FunctionIntrinsic getIntrinsic(FunctionDescriptor descriptor, TranslationContext context) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(context, "context");
        String strAsString = descriptor.getShortName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        if (LONG_EQUALS_ANY.test(descriptor) || LONG_BINARY_OPERATION_LONG.test(descriptor) || LONG_BIT_SHIFTS.test(descriptor)) {
            return longBinaryIntrinsics.get(strAsString);
        }
        if (INTEGER_BINARY_OPERATION_LONG.test(descriptor)) {
            return wrapIntrinsicIfPresent(longBinaryIntrinsics.get(strAsString), AnonymousClass1.INSTANCE, FunctionsKt.identity());
        }
        if (LONG_BINARY_OPERATION_INTEGER.test(descriptor)) {
            return wrapIntrinsicIfPresent(longBinaryIntrinsics.get(strAsString), FunctionsKt.identity(), AnonymousClass2.INSTANCE);
        }
        if (FLOATING_POINT_BINARY_OPERATION_LONG.test(descriptor)) {
            return wrapIntrinsicIfPresent(floatBinaryIntrinsics.get(strAsString), FunctionsKt.identity(), new Function1<JsExpression, JsExpression>() { // from class: org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.LongOperationFIF.getIntrinsic.3
                @Override // kotlin.jvm.functions.Function1
                public final JsExpression invoke(JsExpression it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    JsInvocation jsInvocationInvokeMethod = JsAstUtils.invokeMethod(it, Namer.LONG_TO_NUMBER, new JsExpression[0]);
                    Intrinsics.checkNotNullExpressionValue(jsInvocationInvokeMethod, "invokeMethod(...)");
                    return jsInvocationInvokeMethod;
                }
            });
        }
        if (LONG_BINARY_OPERATION_FLOATING_POINT.test(descriptor)) {
            return wrapIntrinsicIfPresent(floatBinaryIntrinsics.get(strAsString), new Function1<JsExpression, JsExpression>() { // from class: org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.LongOperationFIF.getIntrinsic.4
                @Override // kotlin.jvm.functions.Function1
                public final JsExpression invoke(JsExpression it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    JsInvocation jsInvocationInvokeMethod = JsAstUtils.invokeMethod(it, Namer.LONG_TO_NUMBER, new JsExpression[0]);
                    Intrinsics.checkNotNullExpressionValue(jsInvocationInvokeMethod, "invokeMethod(...)");
                    return jsInvocationInvokeMethod;
                }
            }, FunctionsKt.identity());
        }
        return null;
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.LongOperationFIF$getIntrinsic$1, reason: invalid class name */
    /* JADX INFO: compiled from: LongOperationFIF.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class AnonymousClass1 extends FunctionReference implements Function1<JsExpression, JsExpression> {
        public static final AnonymousClass1 INSTANCE = new AnonymousClass1();

        AnonymousClass1() {
            super(1);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "longFromInt(Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;)Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "longFromInt";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(JsAstUtils.class);
        }

        @Override // kotlin.jvm.functions.Function1
        public final JsExpression invoke(JsExpression p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            return JsAstUtils.longFromInt(p0);
        }
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.LongOperationFIF$getIntrinsic$2, reason: invalid class name */
    /* JADX INFO: compiled from: LongOperationFIF.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class AnonymousClass2 extends FunctionReference implements Function1<JsExpression, JsExpression> {
        public static final AnonymousClass2 INSTANCE = new AnonymousClass2();

        AnonymousClass2() {
            super(1);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "longFromInt(Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;)Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "longFromInt";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(JsAstUtils.class);
        }

        @Override // kotlin.jvm.functions.Function1
        public final JsExpression invoke(JsExpression p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            return JsAstUtils.longFromInt(p0);
        }
    }
}
