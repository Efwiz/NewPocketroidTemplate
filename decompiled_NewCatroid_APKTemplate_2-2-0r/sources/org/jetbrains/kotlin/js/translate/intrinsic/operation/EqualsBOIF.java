package org.jetbrains.kotlin.js.translate.intrinsic.operation;

import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.com.google.common.collect.ImmutableSet;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperator;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsNullLiteral;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.TopLevelFIF;
import org.jetbrains.kotlin.js.translate.utils.JsAstUtils;
import org.jetbrains.kotlin.js.translate.utils.PsiUtils;
import org.jetbrains.kotlin.js.translate.utils.TranslationUtils;
import org.jetbrains.kotlin.lexer.KtSingleValueToken;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.psi.KtBinaryExpression;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.types.DynamicTypesKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.types.expressions.OperatorConventions;

/* JADX INFO: compiled from: EqualsBOIF.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J&\u0010\u0013\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u00102\b\u0010\u0017\u001a\u0004\u0018\u00010\u0010H\u0016J\u0016\u0010\u0018\u001a\u0010\u0012\f\u0012\n \u001b*\u0004\u0018\u00010\u001a0\u001a0\u0019H\u0016J \u0010\u001c\u001a\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00102\u0006\u0010\u0017\u001a\u00020\u0010H\u0002J\u0018\u0010\u001d\u001a\u00020\u00042\u0006\u0010\u001e\u001a\u00020\u00102\u0006\u0010\u001f\u001a\u00020\u0010H\u0002J\u0014\u0010 \u001a\u00020\u000f*\u00020\u00072\u0006\u0010!\u001a\u00020\"H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u0005\u001a\u0012\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006j\u0002`\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\r\u001a\u0012\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006j\u0002`\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0018\u0010\u000e\u001a\u00020\u000f*\u00020\u00108BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012¨\u0006#"}, d2 = {"Lorg/jetbrains/kotlin/js/translate/intrinsic/operation/EqualsBOIF;", "Lorg/jetbrains/kotlin/js/translate/intrinsic/operation/BinaryOperationIntrinsicFactory;", "()V", "eqIntrinsic", "Lorg/jetbrains/kotlin/js/translate/intrinsic/operation/BinaryOperationIntrinsic;", "eqSelector", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/psi/KtBinaryExpression;", "Lorg/jetbrains/kotlin/js/backend/ast/JsBinaryOperator;", "Lorg/jetbrains/kotlin/js/translate/intrinsic/operation/OperatorSelector;", "equalsNullIntrinsic", "kotlinEqualsIntrinsic", "refEqIntrinsic", "refEqSelector", "primitive", "", "Lorg/jetbrains/kotlin/types/KotlinType;", "getPrimitive", "(Lorg/jetbrains/kotlin/types/KotlinType;)Z", "getIntrinsic", "descriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "leftType", "rightType", "getSupportTokens", "Lorg/jetbrains/kotlin/com/google/common/collect/ImmutableSet;", "Lorg/jetbrains/kotlin/lexer/KtSingleValueToken;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "isEnumEqualsIntrinsicApplicable", "primitiveTypes", "leftKotlinType", "rightKotlinType", "appliedToDynamic", "context", "Lorg/jetbrains/kotlin/js/translate/context/TranslationContext;", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class EqualsBOIF implements BinaryOperationIntrinsicFactory {
    private static final BinaryOperationIntrinsic eqIntrinsic;
    private static final Function1<KtBinaryExpression, JsBinaryOperator> eqSelector;
    private static final BinaryOperationIntrinsic refEqIntrinsic;
    private static final Function1<KtBinaryExpression, JsBinaryOperator> refEqSelector;
    public static final EqualsBOIF INSTANCE = new EqualsBOIF();
    private static final BinaryOperationIntrinsic equalsNullIntrinsic = new BinaryOperationIntrinsic() { // from class: org.jetbrains.kotlin.js.translate.intrinsic.operation.EqualsBOIF$$ExternalSyntheticLambda0
        @Override // org.jetbrains.kotlin.js.translate.intrinsic.operation.BinaryOperationIntrinsic
        public final JsExpression invoke(KtBinaryExpression ktBinaryExpression, JsExpression jsExpression, JsExpression jsExpression2, TranslationContext translationContext) {
            return EqualsBOIF.equalsNullIntrinsic$lambda$0(ktBinaryExpression, jsExpression, jsExpression2, translationContext);
        }
    };
    private static final BinaryOperationIntrinsic kotlinEqualsIntrinsic = new BinaryOperationIntrinsic() { // from class: org.jetbrains.kotlin.js.translate.intrinsic.operation.EqualsBOIF$$ExternalSyntheticLambda1
        @Override // org.jetbrains.kotlin.js.translate.intrinsic.operation.BinaryOperationIntrinsic
        public final JsExpression invoke(KtBinaryExpression ktBinaryExpression, JsExpression jsExpression, JsExpression jsExpression2, TranslationContext translationContext) {
            return EqualsBOIF.kotlinEqualsIntrinsic$lambda$1(ktBinaryExpression, jsExpression, jsExpression2, translationContext);
        }
    };

    private EqualsBOIF() {
    }

    @Override // org.jetbrains.kotlin.js.translate.intrinsic.operation.BinaryOperationIntrinsicFactory
    public ImmutableSet<KtSingleValueToken> getSupportTokens() {
        ImmutableSet<KtSingleValueToken> immutableSet = OperatorConventions.EQUALS_OPERATIONS;
        Intrinsics.checkNotNull(immutableSet);
        return immutableSet;
    }

    static {
        EqualsBOIF$refEqSelector$1 equalsBOIF$refEqSelector$1 = new Function1<KtBinaryExpression, JsBinaryOperator>() { // from class: org.jetbrains.kotlin.js.translate.intrinsic.operation.EqualsBOIF$refEqSelector$1
            @Override // kotlin.jvm.functions.Function1
            public final JsBinaryOperator invoke(KtBinaryExpression it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return PsiUtils.isNegatedOperation(it) ? JsBinaryOperator.REF_NEQ : JsBinaryOperator.REF_EQ;
            }
        };
        refEqSelector = equalsBOIF$refEqSelector$1;
        EqualsBOIF$eqSelector$1 equalsBOIF$eqSelector$1 = new Function1<KtBinaryExpression, JsBinaryOperator>() { // from class: org.jetbrains.kotlin.js.translate.intrinsic.operation.EqualsBOIF$eqSelector$1
            @Override // kotlin.jvm.functions.Function1
            public final JsBinaryOperator invoke(KtBinaryExpression it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return PsiUtils.isNegatedOperation(it) ? JsBinaryOperator.NEQ : JsBinaryOperator.EQ;
            }
        };
        eqSelector = equalsBOIF$eqSelector$1;
        refEqIntrinsic = BinaryOperationIntrinsicsKt.binaryIntrinsic$default(null, null, equalsBOIF$refEqSelector$1, 3, null);
        eqIntrinsic = BinaryOperationIntrinsicsKt.binaryIntrinsic$default(null, null, equalsBOIF$eqSelector$1, 3, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final JsExpression equalsNullIntrinsic$lambda$0(KtBinaryExpression expression, JsExpression left, JsExpression right, TranslationContext context) {
        Pair pair;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(left, "left");
        Intrinsics.checkNotNullParameter(right, "right");
        Intrinsics.checkNotNullParameter(context, "context");
        if (right instanceof JsNullLiteral) {
            KtExpression left2 = expression.getLeft();
            Intrinsics.checkNotNull(left2);
            pair = new Pair(left, left2);
        } else {
            KtExpression right2 = expression.getRight();
            Intrinsics.checkNotNull(right2);
            pair = new Pair(right, right2);
        }
        return TranslationUtils.nullCheck((KtExpression) pair.component2(), (JsExpression) pair.component1(), context, PsiUtils.isNegatedOperation(expression));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final JsExpression kotlinEqualsIntrinsic$lambda$1(KtBinaryExpression expression, JsExpression left, JsExpression right, TranslationContext context) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(left, "left");
        Intrinsics.checkNotNullParameter(right, "right");
        Intrinsics.checkNotNullParameter(context, "context");
        JsExpression jsExpressionCoerce = TranslationUtils.coerce(context, left, context.getCurrentModule().getBuiltIns().getAnyType());
        Intrinsics.checkNotNullExpressionValue(jsExpressionCoerce, "coerce(...)");
        JsExpression jsExpressionCoerce2 = TranslationUtils.coerce(context, right, context.getCurrentModule().getBuiltIns().getAnyType());
        Intrinsics.checkNotNullExpressionValue(jsExpressionCoerce2, "coerce(...)");
        JsExpression jsExpressionApply = TopLevelFIF.KOTLIN_EQUALS.apply(jsExpressionCoerce, CollectionsKt.listOf(jsExpressionCoerce2), context);
        return PsiUtils.isNegatedOperation(expression) ? JsAstUtils.not(jsExpressionApply) : jsExpressionApply;
    }

    private static final <T> Function1<KotlinType, T> primitiveTypes$select(final T t, final T t2, final T t3, final T t4) {
        return new Function1<KotlinType, T>() { // from class: org.jetbrains.kotlin.js.translate.intrinsic.operation.EqualsBOIF$primitiveTypes$select$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final T invoke(KotlinType it) {
                Intrinsics.checkNotNullParameter(it, "it");
                if (KotlinBuiltIns.isLongOrNullableLong(it)) {
                    return t2;
                }
                if (KotlinBuiltIns.isBooleanOrNullableBoolean(it)) {
                    return t3;
                }
                return KotlinBuiltIns.isCharOrNullableChar(it) ? t4 : t;
            }
        };
    }

    private final BinaryOperationIntrinsic primitiveTypes(KotlinType leftKotlinType, KotlinType rightKotlinType) {
        final BinaryOperationIntrinsic binaryOperationIntrinsicBinaryIntrinsic = BinaryOperationIntrinsicsKt.binaryIntrinsic(BinaryOperationIntrinsicsKt.coerceTo(leftKotlinType), BinaryOperationIntrinsicsKt.coerceTo(rightKotlinType), eqSelector);
        final BinaryOperationIntrinsic binaryOperationIntrinsicBinaryIntrinsic2 = BinaryOperationIntrinsicsKt.binaryIntrinsic(BinaryOperationIntrinsicsKt.coerceTo(leftKotlinType), BinaryOperationIntrinsicsKt.coerceTo(rightKotlinType), refEqSelector);
        Function2<Boolean, Boolean, BinaryOperationIntrinsic> function2 = new Function2<Boolean, Boolean, BinaryOperationIntrinsic>() { // from class: org.jetbrains.kotlin.js.translate.intrinsic.operation.EqualsBOIF$primitiveTypes$default$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ BinaryOperationIntrinsic invoke(Boolean bool, Boolean bool2) {
                return invoke(bool.booleanValue(), bool2.booleanValue());
            }

            public final BinaryOperationIntrinsic invoke(boolean z, boolean z2) {
                return (z && z2) ? binaryOperationIntrinsicBinaryIntrinsic : binaryOperationIntrinsicBinaryIntrinsic2;
            }
        };
        Function2<Boolean, Boolean, BinaryOperationIntrinsic> function3 = new Function2<Boolean, Boolean, BinaryOperationIntrinsic>() { // from class: org.jetbrains.kotlin.js.translate.intrinsic.operation.EqualsBOIF$primitiveTypes$bool$1
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ BinaryOperationIntrinsic invoke(Boolean bool, Boolean bool2) {
                return invoke(bool.booleanValue(), bool2.booleanValue());
            }

            public final BinaryOperationIntrinsic invoke(boolean z, boolean z2) {
                return (z && z2) ? EqualsBOIF.kotlinEqualsIntrinsic : binaryOperationIntrinsicBinaryIntrinsic2;
            }
        };
        Function2<Boolean, Boolean, BinaryOperationIntrinsic> function4 = new Function2<Boolean, Boolean, BinaryOperationIntrinsic>() { // from class: org.jetbrains.kotlin.js.translate.intrinsic.operation.EqualsBOIF$primitiveTypes$allEq$1
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ BinaryOperationIntrinsic invoke(Boolean bool, Boolean bool2) {
                return invoke(bool.booleanValue(), bool2.booleanValue());
            }

            public final BinaryOperationIntrinsic invoke(boolean z, boolean z2) {
                return binaryOperationIntrinsicBinaryIntrinsic;
            }
        };
        EqualsBOIF$primitiveTypes$allKEq$1 equalsBOIF$primitiveTypes$allKEq$1 = new Function2<Boolean, Boolean, BinaryOperationIntrinsic>() { // from class: org.jetbrains.kotlin.js.translate.intrinsic.operation.EqualsBOIF$primitiveTypes$allKEq$1
            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ BinaryOperationIntrinsic invoke(Boolean bool, Boolean bool2) {
                return invoke(bool.booleanValue(), bool2.booleanValue());
            }

            public final BinaryOperationIntrinsic invoke(boolean z, boolean z2) {
                return EqualsBOIF.kotlinEqualsIntrinsic;
            }
        };
        return (BinaryOperationIntrinsic) ((Function2) ((Function1) primitiveTypes$select(primitiveTypes$select(function2, function4, function3, equalsBOIF$primitiveTypes$allKEq$1), primitiveTypes$select(function4, equalsBOIF$primitiveTypes$allKEq$1, function3, equalsBOIF$primitiveTypes$allKEq$1), primitiveTypes$select(function3, function3, function2, equalsBOIF$primitiveTypes$allKEq$1), primitiveTypes$select(equalsBOIF$primitiveTypes$allKEq$1, equalsBOIF$primitiveTypes$allKEq$1, equalsBOIF$primitiveTypes$allKEq$1, function2)).invoke(leftKotlinType)).invoke(rightKotlinType)).invoke(Boolean.valueOf(TypeUtils.isNullableType(leftKotlinType)), Boolean.valueOf(TypeUtils.isNullableType(rightKotlinType)));
    }

    @Override // org.jetbrains.kotlin.js.translate.intrinsic.operation.BinaryOperationIntrinsicFactory
    public BinaryOperationIntrinsic getIntrinsic(FunctionDescriptor descriptor, final KotlinType leftType, final KotlinType rightType) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        if (leftType == null || rightType == null) {
            return null;
        }
        if (isEnumEqualsIntrinsicApplicable(descriptor, leftType, rightType)) {
            return refEqIntrinsic;
        }
        if (KotlinBuiltIns.isBuiltIn(descriptor) || TopLevelFIF.EQUALS_IN_ANY.test(descriptor)) {
            return new BinaryOperationIntrinsic() { // from class: org.jetbrains.kotlin.js.translate.intrinsic.operation.EqualsBOIF$$ExternalSyntheticLambda2
                @Override // org.jetbrains.kotlin.js.translate.intrinsic.operation.BinaryOperationIntrinsic
                public final JsExpression invoke(KtBinaryExpression ktBinaryExpression, JsExpression jsExpression, JsExpression jsExpression2, TranslationContext translationContext) {
                    return EqualsBOIF.getIntrinsic$lambda$2(leftType, rightType, ktBinaryExpression, jsExpression, jsExpression2, translationContext);
                }
            };
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final JsExpression getIntrinsic$lambda$2(KotlinType kotlinType, KotlinType kotlinType2, KtBinaryExpression expression, JsExpression left, JsExpression right, TranslationContext context) {
        BinaryOperationIntrinsic binaryOperationIntrinsicPrimitiveTypes;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(left, "left");
        Intrinsics.checkNotNullParameter(right, "right");
        Intrinsics.checkNotNullParameter(context, "context");
        if ((left instanceof JsNullLiteral) || (right instanceof JsNullLiteral)) {
            binaryOperationIntrinsicPrimitiveTypes = equalsNullIntrinsic;
        } else {
            EqualsBOIF equalsBOIF = INSTANCE;
            if (equalsBOIF.getPrimitive(kotlinType) && equalsBOIF.getPrimitive(kotlinType2)) {
                binaryOperationIntrinsicPrimitiveTypes = equalsBOIF.primitiveTypes(kotlinType, kotlinType2);
            } else {
                binaryOperationIntrinsicPrimitiveTypes = equalsBOIF.appliedToDynamic(expression, context) ? eqIntrinsic : kotlinEqualsIntrinsic;
            }
        }
        return binaryOperationIntrinsicPrimitiveTypes.invoke(expression, left, right, context);
    }

    private final boolean appliedToDynamic(KtBinaryExpression ktBinaryExpression, TranslationContext translationContext) {
        ReceiverValue dispatchReceiver;
        KotlinType type;
        BindingContext bindingContext = translationContext.bindingContext();
        Intrinsics.checkNotNullExpressionValue(bindingContext, "bindingContext(...)");
        ResolvedCall<? extends CallableDescriptor> resolvedCall = CallUtilKt.getResolvedCall(ktBinaryExpression, bindingContext);
        if (resolvedCall == null || (dispatchReceiver = resolvedCall.getDispatchReceiver()) == null || (type = dispatchReceiver.getType()) == null) {
            return false;
        }
        return DynamicTypesKt.isDynamic(type);
    }

    private final boolean getPrimitive(KotlinType kotlinType) {
        return KotlinBuiltIns.isPrimitiveTypeOrNullablePrimitiveType(kotlinType);
    }

    private final boolean isEnumEqualsIntrinsicApplicable(FunctionDescriptor descriptor, KotlinType leftType, KotlinType rightType) {
        return (!DescriptorUtils.isEnumClass(descriptor.getContainingModule()) || TypeUtils.isNullableType(leftType) || TypeUtils.isNullableType(rightType)) ? false : true;
    }
}
