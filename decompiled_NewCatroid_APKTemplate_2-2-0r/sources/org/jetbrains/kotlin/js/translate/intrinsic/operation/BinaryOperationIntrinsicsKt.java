package org.jetbrains.kotlin.js.translate.intrinsic.operation;

import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperation;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperator;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.js.translate.operation.OperatorTable;
import org.jetbrains.kotlin.js.translate.utils.PsiUtils;
import org.jetbrains.kotlin.js.translate.utils.TranslationUtils;
import org.jetbrains.kotlin.js.translate.utils.UtilsKt;
import org.jetbrains.kotlin.psi.KtBinaryExpression;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.resolve.checkers.PrimitiveNumericComparisonInfo;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: binaryOperationIntrinsics.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000@\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\u001aX\u0010\u0007\u001a\u00020\b2\u001a\b\u0002\u0010\t\u001a\u0014\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u000b0\n2\u001a\b\u0002\u0010\r\u001a\u0014\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u000b0\n2\u0018\b\u0002\u0010\u000e\u001a\u0012\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001j\u0002`\u0004\u001a&\u0010\u000f\u001a\u0012\u0012\u0006\u0012\u0004\u0018\u00010\u0011\u0012\u0006\u0012\u0004\u0018\u00010\u00110\u00102\u0006\u0010\u0012\u001a\u00020\u00022\u0006\u0010\u0013\u001a\u00020\f\u001a \u0010\u0014\u001a\u0014\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010\u0015\u001a\u00020\u0011\u001a\\\u0010\u0016\u001a\u00020\b2\u001e\u0010\t\u001a\u001a\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u000b0\u00172\u001e\u0010\r\u001a\u001a\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u000b0\u00172\u0014\b\u0002\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001\"!\u0010\u0000\u001a\u0012\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001j\u0002`\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006*\"\u0010\u0018\"\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u00012\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001¨\u0006\u0019"}, d2 = {"defaultOperatorSelector", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/psi/KtBinaryExpression;", "Lorg/jetbrains/kotlin/js/backend/ast/JsBinaryOperator;", "Lorg/jetbrains/kotlin/js/translate/intrinsic/operation/OperatorSelector;", "getDefaultOperatorSelector", "()Lkotlin/jvm/functions/Function1;", "binaryIntrinsic", "Lorg/jetbrains/kotlin/js/translate/intrinsic/operation/BinaryOperationIntrinsic;", "toLeft", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "Lorg/jetbrains/kotlin/js/translate/context/TranslationContext;", "toRight", "operator", "binaryOperationTypes", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/types/KotlinType;", "expression", "context", "coerceTo", ModuleXmlParser.TYPE, "complexBinaryIntrinsic", "Lkotlin/Function3;", "OperatorSelector", "js.translator"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class BinaryOperationIntrinsicsKt {
    private static final Function1<KtBinaryExpression, JsBinaryOperator> defaultOperatorSelector = new Function1<KtBinaryExpression, JsBinaryOperator>() { // from class: org.jetbrains.kotlin.js.translate.intrinsic.operation.BinaryOperationIntrinsicsKt$defaultOperatorSelector$1
        @Override // kotlin.jvm.functions.Function1
        public final JsBinaryOperator invoke(KtBinaryExpression it) {
            Intrinsics.checkNotNullParameter(it, "it");
            JsBinaryOperator binaryOperator = OperatorTable.getBinaryOperator(PsiUtils.getOperationToken(it));
            Intrinsics.checkNotNullExpressionValue(binaryOperator, "getBinaryOperator(...)");
            return binaryOperator;
        }
    };

    public static final Pair<KotlinType, KotlinType> binaryOperationTypes(KtBinaryExpression expression, TranslationContext context) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        PrimitiveNumericComparisonInfo primitiveNumericComparisonInfo = UtilsKt.getPrimitiveNumericComparisonInfo(context, expression);
        if (primitiveNumericComparisonInfo != null) {
            return TuplesKt.to(primitiveNumericComparisonInfo.getLeftType(), primitiveNumericComparisonInfo.getRightType());
        }
        KtExpression left = expression.getLeft();
        KotlinType precisePrimitiveType = left != null ? UtilsKt.getPrecisePrimitiveType(context, left) : null;
        KtExpression right = expression.getRight();
        return TuplesKt.to(precisePrimitiveType, right != null ? UtilsKt.getPrecisePrimitiveType(context, right) : null);
    }

    public static final Function1<KtBinaryExpression, JsBinaryOperator> getDefaultOperatorSelector() {
        return defaultOperatorSelector;
    }

    public static /* synthetic */ BinaryOperationIntrinsic complexBinaryIntrinsic$default(Function3 function3, Function3 function4, Function1 function1, int i, Object obj) {
        if ((i & 4) != 0) {
            function1 = defaultOperatorSelector;
        }
        return complexBinaryIntrinsic(function3, function4, function1);
    }

    public static final BinaryOperationIntrinsic complexBinaryIntrinsic(final Function3<? super JsExpression, ? super JsExpression, ? super TranslationContext, ? extends JsExpression> toLeft, final Function3<? super JsExpression, ? super JsExpression, ? super TranslationContext, ? extends JsExpression> toRight, final Function1<? super KtBinaryExpression, ? extends JsBinaryOperator> operator) {
        Intrinsics.checkNotNullParameter(toLeft, "toLeft");
        Intrinsics.checkNotNullParameter(toRight, "toRight");
        Intrinsics.checkNotNullParameter(operator, "operator");
        return new BinaryOperationIntrinsic() { // from class: org.jetbrains.kotlin.js.translate.intrinsic.operation.BinaryOperationIntrinsicsKt$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.js.translate.intrinsic.operation.BinaryOperationIntrinsic
            public final JsExpression invoke(KtBinaryExpression ktBinaryExpression, JsExpression jsExpression, JsExpression jsExpression2, TranslationContext translationContext) {
                return BinaryOperationIntrinsicsKt.complexBinaryIntrinsic$lambda$2(operator, toLeft, toRight, ktBinaryExpression, jsExpression, jsExpression2, translationContext);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final JsExpression complexBinaryIntrinsic$lambda$2(Function1 operator, Function3 toLeft, Function3 toRight, KtBinaryExpression expression, JsExpression left, JsExpression right, TranslationContext context) {
        Intrinsics.checkNotNullParameter(operator, "$operator");
        Intrinsics.checkNotNullParameter(toLeft, "$toLeft");
        Intrinsics.checkNotNullParameter(toRight, "$toRight");
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(left, "left");
        Intrinsics.checkNotNullParameter(right, "right");
        Intrinsics.checkNotNullParameter(context, "context");
        return new JsBinaryOperation((JsBinaryOperator) operator.invoke(expression), (JsExpression) toLeft.invoke(left, right, context), (JsExpression) toRight.invoke(left, right, context));
    }

    public static /* synthetic */ BinaryOperationIntrinsic binaryIntrinsic$default(Function2 function2, Function2 function3, Function1 function1, int i, Object obj) {
        if ((i & 1) != 0) {
            function2 = new Function2<JsExpression, TranslationContext, JsExpression>() { // from class: org.jetbrains.kotlin.js.translate.intrinsic.operation.BinaryOperationIntrinsicsKt.binaryIntrinsic.1
                @Override // kotlin.jvm.functions.Function2
                public final JsExpression invoke(JsExpression l, TranslationContext translationContext) {
                    Intrinsics.checkNotNullParameter(l, "l");
                    Intrinsics.checkNotNullParameter(translationContext, "<anonymous parameter 1>");
                    return l;
                }
            };
        }
        if ((i & 2) != 0) {
            function3 = new Function2<JsExpression, TranslationContext, JsExpression>() { // from class: org.jetbrains.kotlin.js.translate.intrinsic.operation.BinaryOperationIntrinsicsKt.binaryIntrinsic.2
                @Override // kotlin.jvm.functions.Function2
                public final JsExpression invoke(JsExpression r, TranslationContext translationContext) {
                    Intrinsics.checkNotNullParameter(r, "r");
                    Intrinsics.checkNotNullParameter(translationContext, "<anonymous parameter 1>");
                    return r;
                }
            };
        }
        if ((i & 4) != 0) {
            function1 = defaultOperatorSelector;
        }
        return binaryIntrinsic(function2, function3, function1);
    }

    public static final BinaryOperationIntrinsic binaryIntrinsic(final Function2<? super JsExpression, ? super TranslationContext, ? extends JsExpression> toLeft, final Function2<? super JsExpression, ? super TranslationContext, ? extends JsExpression> toRight, Function1<? super KtBinaryExpression, ? extends JsBinaryOperator> operator) {
        Intrinsics.checkNotNullParameter(toLeft, "toLeft");
        Intrinsics.checkNotNullParameter(toRight, "toRight");
        Intrinsics.checkNotNullParameter(operator, "operator");
        return complexBinaryIntrinsic(new Function3<JsExpression, JsExpression, TranslationContext, JsExpression>() { // from class: org.jetbrains.kotlin.js.translate.intrinsic.operation.BinaryOperationIntrinsicsKt.binaryIntrinsic.3
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(3);
            }

            @Override // kotlin.jvm.functions.Function3
            public final JsExpression invoke(JsExpression l, JsExpression jsExpression, TranslationContext c) {
                Intrinsics.checkNotNullParameter(l, "l");
                Intrinsics.checkNotNullParameter(jsExpression, "<anonymous parameter 1>");
                Intrinsics.checkNotNullParameter(c, "c");
                return toLeft.invoke(l, c);
            }
        }, new Function3<JsExpression, JsExpression, TranslationContext, JsExpression>() { // from class: org.jetbrains.kotlin.js.translate.intrinsic.operation.BinaryOperationIntrinsicsKt.binaryIntrinsic.4
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(3);
            }

            @Override // kotlin.jvm.functions.Function3
            public final JsExpression invoke(JsExpression jsExpression, JsExpression r, TranslationContext c) {
                Intrinsics.checkNotNullParameter(jsExpression, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(r, "r");
                Intrinsics.checkNotNullParameter(c, "c");
                return toRight.invoke(r, c);
            }
        }, operator);
    }

    public static final Function2<JsExpression, TranslationContext, JsExpression> coerceTo(final KotlinType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        return new Function2<JsExpression, TranslationContext, JsExpression>() { // from class: org.jetbrains.kotlin.js.translate.intrinsic.operation.BinaryOperationIntrinsicsKt.coerceTo.1
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final JsExpression invoke(JsExpression e, TranslationContext c) {
                Intrinsics.checkNotNullParameter(e, "e");
                Intrinsics.checkNotNullParameter(c, "c");
                JsExpression jsExpressionCoerce = TranslationUtils.coerce(c, e, type);
                Intrinsics.checkNotNullExpressionValue(jsExpressionCoerce, "coerce(...)");
                return jsExpressionCoerce;
            }
        };
    }
}
