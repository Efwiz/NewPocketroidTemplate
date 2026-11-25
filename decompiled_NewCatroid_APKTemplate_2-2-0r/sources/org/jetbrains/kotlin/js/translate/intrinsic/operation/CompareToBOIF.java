package org.jetbrains.kotlin.js.translate.intrinsic.operation;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.com.google.common.collect.ImmutableSet;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsIntLiteral;
import org.jetbrains.kotlin.js.patterns.DescriptorPredicate;
import org.jetbrains.kotlin.js.patterns.PatternBuilder;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.js.translate.utils.JsAstUtils;
import org.jetbrains.kotlin.lexer.KtSingleValueToken;
import org.jetbrains.kotlin.resolve.calls.tasks.DynamicCallsKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.expressions.OperatorConventions;

/* JADX INFO: compiled from: CompareToBOIF.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J&\u0010\b\u001a\u0004\u0018\u00010\u00072\u0006\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\f2\b\u0010\r\u001a\u0004\u0018\u00010\fH\u0016J\u000e\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fH\u0016R \u0010\u0003\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/js/translate/intrinsic/operation/CompareToBOIF;", "Lorg/jetbrains/kotlin/js/translate/intrinsic/operation/BinaryOperationIntrinsicFactory;", "()V", "patterns", "", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/js/patterns/DescriptorPredicate;", "Lorg/jetbrains/kotlin/js/translate/intrinsic/operation/BinaryOperationIntrinsic;", "getIntrinsic", "descriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "leftType", "Lorg/jetbrains/kotlin/types/KotlinType;", "rightType", "getSupportTokens", "", "Lorg/jetbrains/kotlin/lexer/KtSingleValueToken;", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CompareToBOIF implements BinaryOperationIntrinsicFactory {
    public static final CompareToBOIF INSTANCE = new CompareToBOIF();
    private static final List<Pair<DescriptorPredicate, BinaryOperationIntrinsic>> patterns = CollectionsKt.listOf((Object[]) new Pair[]{TuplesKt.to(PatternBuilder.pattern("Int|Short|Byte|Float|Double.compareTo(Long)"), BinaryOperationIntrinsicsKt.binaryIntrinsic$default(null, new Function2<JsExpression, TranslationContext, JsExpression>() { // from class: org.jetbrains.kotlin.js.translate.intrinsic.operation.CompareToBOIF$patterns$1
        @Override // kotlin.jvm.functions.Function2
        public final JsExpression invoke(JsExpression r, TranslationContext translationContext) {
            Intrinsics.checkNotNullParameter(r, "r");
            Intrinsics.checkNotNullParameter(translationContext, "<anonymous parameter 1>");
            JsExpression jsExpressionLongToNumber = JsAstUtils.longToNumber(r);
            Intrinsics.checkNotNullExpressionValue(jsExpressionLongToNumber, "longToNumber(...)");
            return jsExpressionLongToNumber;
        }
    }, null, 5, null)), TuplesKt.to(PatternBuilder.pattern("Long.compareTo(Int|Short|Byte|Float|Double)"), BinaryOperationIntrinsicsKt.binaryIntrinsic$default(new Function2<JsExpression, TranslationContext, JsExpression>() { // from class: org.jetbrains.kotlin.js.translate.intrinsic.operation.CompareToBOIF$patterns$2
        @Override // kotlin.jvm.functions.Function2
        public final JsExpression invoke(JsExpression l, TranslationContext translationContext) {
            Intrinsics.checkNotNullParameter(l, "l");
            Intrinsics.checkNotNullParameter(translationContext, "<anonymous parameter 1>");
            JsExpression jsExpressionLongToNumber = JsAstUtils.longToNumber(l);
            Intrinsics.checkNotNullExpressionValue(jsExpressionLongToNumber, "longToNumber(...)");
            return jsExpressionLongToNumber;
        }
    }, null, null, 6, null)), TuplesKt.to(PatternBuilder.pattern("Long.compareTo(Long)"), BinaryOperationIntrinsicsKt.complexBinaryIntrinsic$default(new Function3<JsExpression, JsExpression, TranslationContext, JsExpression>() { // from class: org.jetbrains.kotlin.js.translate.intrinsic.operation.CompareToBOIF$patterns$3
        @Override // kotlin.jvm.functions.Function3
        public final JsExpression invoke(JsExpression l, JsExpression r, TranslationContext translationContext) {
            Intrinsics.checkNotNullParameter(l, "l");
            Intrinsics.checkNotNullParameter(r, "r");
            Intrinsics.checkNotNullParameter(translationContext, "<anonymous parameter 2>");
            JsExpression jsExpressionCompareForObject = JsAstUtils.compareForObject(l, r);
            Intrinsics.checkNotNullExpressionValue(jsExpressionCompareForObject, "compareForObject(...)");
            return jsExpressionCompareForObject;
        }
    }, new Function3<JsExpression, JsExpression, TranslationContext, JsExpression>() { // from class: org.jetbrains.kotlin.js.translate.intrinsic.operation.CompareToBOIF$patterns$4
        @Override // kotlin.jvm.functions.Function3
        public final JsExpression invoke(JsExpression jsExpression, JsExpression jsExpression2, TranslationContext translationContext) {
            Intrinsics.checkNotNullParameter(jsExpression, "<anonymous parameter 0>");
            Intrinsics.checkNotNullParameter(jsExpression2, "<anonymous parameter 1>");
            Intrinsics.checkNotNullParameter(translationContext, "<anonymous parameter 2>");
            return new JsIntLiteral(0);
        }
    }, null, 4, null))});

    private CompareToBOIF() {
    }

    @Override // org.jetbrains.kotlin.js.translate.intrinsic.operation.BinaryOperationIntrinsicFactory
    public Set<KtSingleValueToken> getSupportTokens() {
        ImmutableSet<KtSingleValueToken> COMPARISON_OPERATIONS = OperatorConventions.COMPARISON_OPERATIONS;
        Intrinsics.checkNotNullExpressionValue(COMPARISON_OPERATIONS, "COMPARISON_OPERATIONS");
        return COMPARISON_OPERATIONS;
    }

    @Override // org.jetbrains.kotlin.js.translate.intrinsic.operation.BinaryOperationIntrinsicFactory
    public BinaryOperationIntrinsic getIntrinsic(FunctionDescriptor descriptor, KotlinType leftType, KotlinType rightType) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        FunctionDescriptor functionDescriptor = descriptor;
        if (DynamicCallsKt.isDynamic(functionDescriptor)) {
            return BinaryOperationIntrinsicsKt.binaryIntrinsic$default(null, null, null, 7, null);
        }
        if (leftType == null || rightType == null || !KotlinBuiltIns.isBuiltIn(functionDescriptor)) {
            return null;
        }
        Iterator<T> it = patterns.iterator();
        while (it.hasNext()) {
            Pair pair = (Pair) it.next();
            DescriptorPredicate descriptorPredicate = (DescriptorPredicate) pair.component1();
            BinaryOperationIntrinsic binaryOperationIntrinsic = (BinaryOperationIntrinsic) pair.component2();
            if (descriptorPredicate.test(descriptor)) {
                return binaryOperationIntrinsic;
            }
        }
        if (KotlinBuiltIns.isPrimitiveTypeOrNullablePrimitiveType(leftType) && KotlinBuiltIns.isPrimitiveTypeOrNullablePrimitiveType(rightType)) {
            return BinaryOperationIntrinsicsKt.binaryIntrinsic$default(BinaryOperationIntrinsicsKt.coerceTo(leftType), BinaryOperationIntrinsicsKt.coerceTo(rightType), null, 4, null);
        }
        return BinaryOperationIntrinsicsKt.complexBinaryIntrinsic$default(new Function3<JsExpression, JsExpression, TranslationContext, JsExpression>() { // from class: org.jetbrains.kotlin.js.translate.intrinsic.operation.CompareToBOIF.getIntrinsic.2
            @Override // kotlin.jvm.functions.Function3
            public final JsExpression invoke(JsExpression l, JsExpression r, TranslationContext translationContext) {
                Intrinsics.checkNotNullParameter(l, "l");
                Intrinsics.checkNotNullParameter(r, "r");
                Intrinsics.checkNotNullParameter(translationContext, "<anonymous parameter 2>");
                JsExpression jsExpressionCompareTo = JsAstUtils.compareTo(l, r);
                Intrinsics.checkNotNullExpressionValue(jsExpressionCompareTo, "compareTo(...)");
                return jsExpressionCompareTo;
            }
        }, new Function3<JsExpression, JsExpression, TranslationContext, JsExpression>() { // from class: org.jetbrains.kotlin.js.translate.intrinsic.operation.CompareToBOIF.getIntrinsic.3
            @Override // kotlin.jvm.functions.Function3
            public final JsExpression invoke(JsExpression jsExpression, JsExpression jsExpression2, TranslationContext translationContext) {
                Intrinsics.checkNotNullParameter(jsExpression, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(jsExpression2, "<anonymous parameter 1>");
                Intrinsics.checkNotNullParameter(translationContext, "<anonymous parameter 2>");
                return new JsIntLiteral(0);
            }
        }, null, 4, null);
    }
}
