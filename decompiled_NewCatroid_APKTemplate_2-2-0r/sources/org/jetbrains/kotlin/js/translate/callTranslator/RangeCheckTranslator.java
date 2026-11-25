package org.jetbrains.kotlin.js.translate.callTranslator;

import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperation;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsInvocation;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.metadata.MetadataProperties;
import org.jetbrains.kotlin.js.backend.ast.metadata.RangeKind;
import org.jetbrains.kotlin.js.backend.ast.metadata.RangeType;
import org.jetbrains.kotlin.js.backend.ast.metadata.SideEffectKind;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.js.translate.general.Translation;
import org.jetbrains.kotlin.js.translate.utils.JsAstUtils;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.ValueArgument;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedValueArgument;

/* JADX INFO: compiled from: RangeCheckTranslator.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J(\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0006H\u0002J(\u0010\f\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0006H\u0002J \u0010\r\u001a\u0004\u0018\u00010\u00062\u000e\u0010\u000e\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00100\u000f2\u0006\u0010\u0011\u001a\u00020\u0012R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/js/translate/callTranslator/RangeCheckTranslator;", "", "context", "Lorg/jetbrains/kotlin/js/translate/context/TranslationContext;", "(Lorg/jetbrains/kotlin/js/translate/context/TranslationContext;)V", "translateAsIntRangeCheck", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "lower", "upper", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/js/backend/ast/metadata/RangeKind;", "subject", "translateAsLongRangeCheck", "translateAsRangeCheck", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "receivers", "Lorg/jetbrains/kotlin/js/translate/callTranslator/ExplicitReceivers;", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class RangeCheckTranslator {
    private final TranslationContext context;

    /* JADX INFO: compiled from: RangeCheckTranslator.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] iArr = new int[RangeType.values().length];
            try {
                iArr[RangeType.INT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[RangeType.LONG.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[RangeKind.values().length];
            try {
                iArr2[RangeKind.RANGE_TO.ordinal()] = 1;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr2[RangeKind.UNTIL.ordinal()] = 2;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$1 = iArr2;
        }
    }

    public RangeCheckTranslator(TranslationContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final JsExpression translateAsRangeCheck(ResolvedCall<? extends FunctionDescriptor> resolvedCall, ExplicitReceivers receivers) {
        Pair<RangeType, RangeKind> range;
        List<JsExpression> arguments;
        List<ValueArgument> arguments2;
        ValueArgument valueArgument;
        KtExpression argumentExpression;
        JsExpression qualifier;
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        Intrinsics.checkNotNullParameter(receivers, "receivers");
        FunctionDescriptor functionDescriptor = (FunctionDescriptor) resolvedCall.getResultingDescriptor();
        if (Intrinsics.areEqual(functionDescriptor.getShortName().asString(), "contains") && (functionDescriptor.getDeclarationDescriptor() instanceof ClassDescriptor)) {
            JsExpression extensionOrDispatchReceiver = receivers.getExtensionOrDispatchReceiver();
            JsExpression.JsExpressionHasArguments jsExpressionHasArguments = extensionOrDispatchReceiver instanceof JsExpression.JsExpressionHasArguments ? (JsExpression.JsExpressionHasArguments) extensionOrDispatchReceiver : null;
            if (jsExpressionHasArguments == null || (range = MetadataProperties.getRange(jsExpressionHasArguments)) == null) {
                return null;
            }
            RangeType rangeTypeComponent1 = range.component1();
            RangeKind rangeKindComponent2 = range.component2();
            if (rangeKindComponent2 == RangeKind.UNTIL || rangeTypeComponent1 == RangeType.INT) {
                if (jsExpressionHasArguments.getArguments().size() != 2) {
                    return null;
                }
                arguments = jsExpressionHasArguments.getArguments();
            } else {
                if (!(jsExpressionHasArguments instanceof JsInvocation)) {
                    return null;
                }
                JsInvocation jsInvocation = (JsInvocation) jsExpressionHasArguments;
                JsExpression qualifier2 = jsInvocation.getQualifier();
                JsNameRef jsNameRef = qualifier2 instanceof JsNameRef ? (JsNameRef) qualifier2 : null;
                if (jsNameRef == null || (qualifier = jsNameRef.getQualifier()) == null || jsInvocation.getArguments().size() != 1) {
                    return null;
                }
                List<JsExpression> arguments3 = jsInvocation.getArguments();
                Intrinsics.checkNotNullExpressionValue(arguments3, "getArguments(...)");
                arguments = CollectionsKt.listOf((Object[]) new JsExpression[]{qualifier, CollectionsKt.single((List) arguments3)});
            }
            Intrinsics.checkNotNull(arguments);
            JsExpression jsExpression = arguments.get(0);
            JsExpression jsExpression2 = arguments.get(1);
            JsExpression jsExpressionCacheExpressionIfNeeded = this.context.cacheExpressionIfNeeded(jsExpression);
            JsExpression jsExpressionCacheExpressionIfNeeded2 = this.context.cacheExpressionIfNeeded(jsExpression2);
            ResolvedValueArgument resolvedValueArgument = (ResolvedValueArgument) CollectionsKt.singleOrNull(resolvedCall.getValueArguments().values());
            if (resolvedValueArgument != null && (arguments2 = resolvedValueArgument.getArguments()) != null && (valueArgument = (ValueArgument) CollectionsKt.singleOrNull((List) arguments2)) != null && (argumentExpression = valueArgument.getArgumentExpression()) != null) {
                TranslationContext translationContext = this.context;
                JsExpression jsExpressionCacheExpressionIfNeeded3 = translationContext.cacheExpressionIfNeeded(Translation.translateAsExpression(argumentExpression, translationContext));
                Intrinsics.checkNotNullExpressionValue(jsExpressionCacheExpressionIfNeeded3, "cacheExpressionIfNeeded(...)");
                int i = WhenMappings.$EnumSwitchMapping$0[rangeTypeComponent1.ordinal()];
                if (i == 1) {
                    Intrinsics.checkNotNull(jsExpressionCacheExpressionIfNeeded);
                    Intrinsics.checkNotNull(jsExpressionCacheExpressionIfNeeded2);
                    return translateAsIntRangeCheck(jsExpressionCacheExpressionIfNeeded, jsExpressionCacheExpressionIfNeeded2, rangeKindComponent2, jsExpressionCacheExpressionIfNeeded3);
                }
                if (i != 2) {
                    throw new NoWhenBranchMatchedException();
                }
                Intrinsics.checkNotNull(jsExpressionCacheExpressionIfNeeded);
                Intrinsics.checkNotNull(jsExpressionCacheExpressionIfNeeded2);
                return translateAsLongRangeCheck(jsExpressionCacheExpressionIfNeeded, jsExpressionCacheExpressionIfNeeded2, rangeKindComponent2, jsExpressionCacheExpressionIfNeeded3);
            }
        }
        return null;
    }

    private final JsExpression translateAsIntRangeCheck(JsExpression lower, JsExpression upper, RangeKind kind, JsExpression subject) {
        JsBinaryOperation jsBinaryOperationLessThanEq;
        JsBinaryOperation jsBinaryOperationLessThanEq2 = JsAstUtils.lessThanEq(lower, subject);
        Intrinsics.checkNotNullExpressionValue(jsBinaryOperationLessThanEq2, "lessThanEq(...)");
        int i = WhenMappings.$EnumSwitchMapping$1[kind.ordinal()];
        if (i == 1) {
            jsBinaryOperationLessThanEq = JsAstUtils.lessThanEq(subject, upper);
        } else {
            if (i != 2) {
                throw new NoWhenBranchMatchedException();
            }
            jsBinaryOperationLessThanEq = JsAstUtils.lessThan(subject, upper);
        }
        Intrinsics.checkNotNull(jsBinaryOperationLessThanEq);
        MetadataProperties.setSynthetic(jsBinaryOperationLessThanEq2, true);
        MetadataProperties.setSynthetic(jsBinaryOperationLessThanEq, true);
        JsBinaryOperation jsBinaryOperationAnd = JsAstUtils.and(jsBinaryOperationLessThanEq2, jsBinaryOperationLessThanEq);
        Intrinsics.checkNotNull(jsBinaryOperationAnd);
        MetadataProperties.setSynthetic(jsBinaryOperationAnd, true);
        Intrinsics.checkNotNullExpressionValue(jsBinaryOperationAnd, "apply(...)");
        return jsBinaryOperationAnd;
    }

    private final JsExpression translateAsLongRangeCheck(JsExpression lower, JsExpression upper, RangeKind kind, JsExpression subject) {
        JsInvocation jsInvocationInvokeMethod;
        JsInvocation jsInvocationInvokeMethod2 = JsAstUtils.invokeMethod(lower, "lessThanOrEqual", subject);
        Intrinsics.checkNotNull(jsInvocationInvokeMethod2);
        MetadataProperties.setSideEffects(jsInvocationInvokeMethod2, SideEffectKind.PURE);
        Intrinsics.checkNotNullExpressionValue(jsInvocationInvokeMethod2, "apply(...)");
        int i = WhenMappings.$EnumSwitchMapping$1[kind.ordinal()];
        if (i == 1) {
            jsInvocationInvokeMethod = JsAstUtils.invokeMethod(subject, "lessThanOrEqual", upper);
        } else {
            if (i != 2) {
                throw new NoWhenBranchMatchedException();
            }
            jsInvocationInvokeMethod = JsAstUtils.invokeMethod(subject, "lessThan", upper);
        }
        Intrinsics.checkNotNull(jsInvocationInvokeMethod);
        MetadataProperties.setSideEffects(jsInvocationInvokeMethod, SideEffectKind.PURE);
        JsBinaryOperation jsBinaryOperationAnd = JsAstUtils.and(jsInvocationInvokeMethod2, jsInvocationInvokeMethod);
        Intrinsics.checkNotNullExpressionValue(jsBinaryOperationAnd, "and(...)");
        return jsBinaryOperationAnd;
    }
}
