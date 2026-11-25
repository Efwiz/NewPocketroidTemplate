package org.jetbrains.kotlin.codegen.range;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.ExpressionCodegen;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.impl.LocalVariableDescriptor;
import org.jetbrains.kotlin.descriptors.impl.SyntheticFieldDescriptor;
import org.jetbrains.kotlin.psi.KtBinaryExpression;
import org.jetbrains.kotlin.psi.KtCallExpression;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtPsiUtil;
import org.jetbrains.kotlin.psi.KtQualifiedExpression;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.resolve.jvm.AsmTypes;
import org.jetbrains.kotlin.resolve.scopes.receivers.ExpressionReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.RangeUtilKt;
import org.jetbrains.kotlin.types.checker.KotlinTypeChecker;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: RangeValues.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000:\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a\"\u0010\u0000\u001a\f\u0012\u0006\b\u0001\u0012\u00020\u0002\u0018\u00010\u00012\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0002\u001a\u0016\u0010\u0007\u001a\u00020\b2\f\u0010\t\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0001H\u0002\u001a\u0016\u0010\n\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u0004\u001a\u0018\u0010\u000b\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002\u001a\u0018\u0010\u0010\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002\u001a\u001e\u0010\u0011\u001a\u0004\u0018\u00010\u0012*\u00020\u00132\u000e\u0010\u0014\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020\u0001H\u0002\u001a\u0012\u0010\u0015\u001a\u00020\u0012*\u00020\u00132\u0006\u0010\u0005\u001a\u00020\u0006\u001a\u001e\u0010\u0016\u001a\u0004\u0018\u00010\u0012*\u00020\u00132\u000e\u0010\u0014\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020\u0001H\u0002¨\u0006\u0017"}, d2 = {"getResolvedCallForRangeExpression", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "bindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "rangeExpression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "isCharSequenceIteratorCall", "", "iteratorCall", "isLocalVarReference", "isSubtypeOfCharSequence", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/KotlinType;", "builtIns", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "isSubtypeOfString", "createIntrinsifiedRangeValueOrNull", "Lorg/jetbrains/kotlin/codegen/range/RangeValue;", "Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;", "rangeCall", "createRangeValueForExpression", "createReversedRangeValueOrNull", Marker.BACKEND}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class RangeValuesKt {
    /* JADX WARN: Code duplicated, block: B:13:0x0077  */
    public static final RangeValue createRangeValueForExpression(ExpressionCodegen expressionCodegen, KtExpression rangeExpression) {
        boolean z;
        RangeValue rangeValueCreateIntrinsifiedRangeValueOrNull;
        Intrinsics.checkNotNullParameter(expressionCodegen, "<this>");
        Intrinsics.checkNotNullParameter(rangeExpression, "rangeExpression");
        BindingContext bindingContext = expressionCodegen.getBindingContext();
        Intrinsics.checkNotNullExpressionValue(bindingContext, "getBindingContext(...)");
        ResolvedCall<? extends CallableDescriptor> resolvedCallForRangeExpression = getResolvedCallForRangeExpression(bindingContext, rangeExpression);
        if (resolvedCallForRangeExpression != null && (rangeValueCreateIntrinsifiedRangeValueOrNull = createIntrinsifiedRangeValueOrNull(expressionCodegen, resolvedCallForRangeExpression)) != null) {
            return rangeValueCreateIntrinsifiedRangeValueOrNull;
        }
        KotlinType type = expressionCodegen.getBindingContext().getType(rangeExpression);
        Intrinsics.checkNotNull(type);
        Type typeAsmType = expressionCodegen.asmType(type);
        Intrinsics.checkNotNullExpressionValue(typeAsmType, "asmType(...)");
        ResolvedCall resolvedCall = (ResolvedCall) expressionCodegen.getBindingContext().get(BindingContext.LOOP_RANGE_ITERATOR_RESOLVED_CALL, rangeExpression);
        KotlinBuiltIns builtIns = expressionCodegen.getState().getModule().getBuiltIns();
        if (typeAsmType.getSort() == 9) {
            boolean zSupportsFeature = expressionCodegen.getState().getLanguageVersionSettings().supportsFeature(LanguageFeature.ProperForInArrayLoopRangeVariableAssignmentSemantic);
            if (!zSupportsFeature) {
                BindingContext bindingContext2 = expressionCodegen.getBindingContext();
                Intrinsics.checkNotNullExpressionValue(bindingContext2, "getBindingContext(...)");
                z = isLocalVarReference(rangeExpression, bindingContext2) ? false : true;
            }
            return new ArrayRangeValue(z, zSupportsFeature);
        }
        if (RangeUtilKt.isPrimitiveRange(type) || RangeUtilKt.isUnsignedRange(type)) {
            return new PrimitiveRangeRangeValue(rangeExpression);
        }
        if (RangeUtilKt.isPrimitiveProgression(type) || RangeUtilKt.isUnsignedProgression(type)) {
            return new PrimitiveProgressionRangeValue(rangeExpression);
        }
        if (isSubtypeOfString(type, builtIns) && isCharSequenceIteratorCall(resolvedCall)) {
            return new CharSequenceRangeValue(true, AsmTypes.JAVA_STRING_TYPE);
        }
        if (isSubtypeOfCharSequence(type, builtIns) && isCharSequenceIteratorCall(resolvedCall)) {
            return new CharSequenceRangeValue(false, null);
        }
        return new IterableRangeValue();
    }

    public static final boolean isLocalVarReference(KtExpression rangeExpression, BindingContext bindingContext) {
        ResolvedCall<? extends CallableDescriptor> resolvedCall;
        CallableDescriptor resultingDescriptor;
        Intrinsics.checkNotNullParameter(rangeExpression, "rangeExpression");
        Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
        if (!(rangeExpression instanceof KtSimpleNameExpression) || (resolvedCall = CallUtilKt.getResolvedCall(rangeExpression, bindingContext)) == null || (resultingDescriptor = resolvedCall.getResultingDescriptor()) == null || !(resultingDescriptor instanceof LocalVariableDescriptor) || (resultingDescriptor instanceof SyntheticFieldDescriptor)) {
            return false;
        }
        LocalVariableDescriptor localVariableDescriptor = (LocalVariableDescriptor) resultingDescriptor;
        return !localVariableDescriptor.getIsDelegated() && localVariableDescriptor.isVar();
    }

    private static final boolean isSubtypeOfString(KotlinType kotlinType, KotlinBuiltIns kotlinBuiltIns) {
        return KotlinTypeChecker.DEFAULT.isSubtypeOf(kotlinType, kotlinBuiltIns.getStringType());
    }

    private static final boolean isSubtypeOfCharSequence(KotlinType kotlinType, KotlinBuiltIns kotlinBuiltIns) {
        return KotlinTypeChecker.DEFAULT.isSubtypeOf(kotlinType, kotlinBuiltIns.getCharSequence().getDefaultType());
    }

    private static final ResolvedCall<? extends CallableDescriptor> getResolvedCallForRangeExpression(BindingContext bindingContext, KtExpression ktExpression) {
        KtExpression ktExpressionDeparenthesize = KtPsiUtil.deparenthesize(ktExpression);
        if (ktExpressionDeparenthesize == null) {
            return null;
        }
        if (ktExpressionDeparenthesize instanceof KtQualifiedExpression) {
            KtExpression selectorExpression = ((KtQualifiedExpression) ktExpressionDeparenthesize).getSelectorExpression();
            if ((selectorExpression instanceof KtCallExpression) || (selectorExpression instanceof KtSimpleNameExpression)) {
                return CallUtilKt.getResolvedCall(selectorExpression, bindingContext);
            }
            return null;
        }
        if (ktExpressionDeparenthesize instanceof KtSimpleNameExpression ? true : ktExpressionDeparenthesize instanceof KtCallExpression) {
            return CallUtilKt.getResolvedCall(ktExpressionDeparenthesize, bindingContext);
        }
        if (ktExpressionDeparenthesize instanceof KtBinaryExpression) {
            return CallUtilKt.getResolvedCall(((KtBinaryExpression) ktExpressionDeparenthesize).getOperationReference(), bindingContext);
        }
        return null;
    }

    private static final RangeValue createIntrinsifiedRangeValueOrNull(ExpressionCodegen expressionCodegen, ResolvedCall<? extends CallableDescriptor> resolvedCall) {
        CallableDescriptor resultingDescriptor = resolvedCall.getResultingDescriptor();
        if (RangeUtilKt.isPrimitiveNumberRangeTo(resultingDescriptor) || RangeUtilKt.isUnsignedIntegerRangeTo(resultingDescriptor)) {
            return new PrimitiveNumberRangeLiteralRangeValue(resolvedCall);
        }
        if (RangeUtilKt.isPrimitiveNumberDownTo(resultingDescriptor) || RangeUtilKt.isUnsignedIntegerDownTo(resultingDescriptor)) {
            return new DownToProgressionRangeValue(resolvedCall);
        }
        if (RangeUtilKt.isPrimitiveNumberUntil(resultingDescriptor) || RangeUtilKt.isUnsignedIntegerUntil(resultingDescriptor)) {
            return new PrimitiveNumberUntilRangeValue(resolvedCall);
        }
        if (RangeUtilKt.isArrayOrPrimitiveArrayIndices(resultingDescriptor)) {
            return new ArrayIndicesRangeValue(resolvedCall);
        }
        if (RangeUtilKt.isCollectionIndices(resultingDescriptor)) {
            return new CollectionIndicesRangeValue(resolvedCall);
        }
        if (RangeUtilKt.isCharSequenceIndices(resultingDescriptor)) {
            return new CharSequenceIndicesRangeValue(resolvedCall);
        }
        if (RangeUtilKt.isArrayOrPrimitiveArrayWithIndex(resultingDescriptor)) {
            return new ArrayWithIndexRangeValue(resolvedCall);
        }
        if (RangeUtilKt.isIterableWithIndex(resultingDescriptor)) {
            return new IterableWithIndexRangeValue(resolvedCall);
        }
        if (RangeUtilKt.isSequenceWithIndex(resultingDescriptor)) {
            return new SequenceWithIndexRangeValue(resolvedCall);
        }
        if (RangeUtilKt.isCharSequenceWithIndex(resultingDescriptor)) {
            return new CharSequenceWithIndexRangeValue(resolvedCall);
        }
        if (RangeUtilKt.isComparableRangeTo(resultingDescriptor)) {
            return new ComparableRangeLiteralRangeValue(expressionCodegen, resolvedCall);
        }
        if (RangeUtilKt.isPrimitiveProgressionReverse(resultingDescriptor)) {
            return createReversedRangeValueOrNull(expressionCodegen, resolvedCall);
        }
        return null;
    }

    private static final RangeValue createReversedRangeValueOrNull(ExpressionCodegen expressionCodegen, ResolvedCall<? extends CallableDescriptor> resolvedCall) {
        ReceiverValue extensionReceiver = resolvedCall.getExtensionReceiver();
        ExpressionReceiver expressionReceiver = extensionReceiver instanceof ExpressionReceiver ? (ExpressionReceiver) extensionReceiver : null;
        if (expressionReceiver == null) {
            return null;
        }
        RangeValue rangeValueCreateRangeValueForExpression = createRangeValueForExpression(expressionCodegen, expressionReceiver.getExpression());
        ReversableRangeValue reversableRangeValue = rangeValueCreateRangeValueForExpression instanceof ReversableRangeValue ? (ReversableRangeValue) rangeValueCreateRangeValueForExpression : null;
        if (reversableRangeValue == null) {
            return null;
        }
        return new ReversedRangeValue(reversableRangeValue);
    }

    private static final boolean isCharSequenceIteratorCall(ResolvedCall<?> resolvedCall) {
        CallableDescriptor resultingDescriptor;
        if (resolvedCall == null || (resultingDescriptor = resolvedCall.getResultingDescriptor()) == null) {
            return false;
        }
        return RangeUtilKt.isCharSequenceIterator(resultingDescriptor);
    }
}
