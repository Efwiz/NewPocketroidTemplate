package org.jetbrains.kotlin.codegen.range;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.CodegenUtilKt;
import org.jetbrains.kotlin.codegen.ExpressionCodegen;
import org.jetbrains.kotlin.codegen.StackValue;
import org.jetbrains.kotlin.codegen.range.comparison.ComparisonGeneratorKt;
import org.jetbrains.kotlin.codegen.range.forLoop.ForInSimpleProgressionLoopGenerator;
import org.jetbrains.kotlin.codegen.range.forLoop.ForLoopGenerator;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.psi.KtBinaryExpression;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtForExpression;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;

/* JADX INFO: compiled from: PrimitiveNumberRangeLiteralRangeValue.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\u0015\u0012\u000e\u0010\u0003\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00050\u0004¢\u0006\u0002\u0010\u0006J\u001a\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0002J\u001a\u0010\r\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0002J\u0018\u0010\u000e\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016J\u0018\u0010\u000f\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\nH\u0014¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/codegen/range/PrimitiveNumberRangeLiteralRangeValue;", "Lorg/jetbrains/kotlin/codegen/range/PrimitiveNumberRangeIntrinsicRangeValue;", "Lorg/jetbrains/kotlin/codegen/range/ReversableRangeValue;", "rangeCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "(Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;)V", "createConstBoundedForInRangeLiteralGenerator", "Lorg/jetbrains/kotlin/codegen/range/forLoop/ForLoopGenerator;", "codegen", "Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;", "forExpression", "Lorg/jetbrains/kotlin/psi/KtForExpression;", "createConstBoundedRangeForInReversedRangeLiteralGenerator", "createForInReversedLoopGenerator", "createForLoopGenerator", "getBoundedValue", "Lorg/jetbrains/kotlin/codegen/range/BoundedValue;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PrimitiveNumberRangeLiteralRangeValue extends PrimitiveNumberRangeIntrinsicRangeValue implements ReversableRangeValue {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PrimitiveNumberRangeLiteralRangeValue(ResolvedCall<? extends CallableDescriptor> rangeCall) {
        super(rangeCall);
        Intrinsics.checkNotNullParameter(rangeCall, "rangeCall");
    }

    @Override // org.jetbrains.kotlin.codegen.range.PrimitiveNumberRangeIntrinsicRangeValue
    protected BoundedValue getBoundedValue(ExpressionCodegen codegen) {
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        StackValue stackValueGenerateCallReceiver = CodegenUtilKt.generateCallReceiver(codegen, getRangeCall());
        if (PrimitiveNumberRangeLiteralRangeValueKt.canBeSpecializedByExcludingHighBound(codegen, getRangeCall())) {
            KtExpression firstArgumentExpression = CallUtilKt.getFirstArgumentExpression(getRangeCall());
            Intrinsics.checkNotNull(firstArgumentExpression, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtBinaryExpression");
            StackValue stackValueGen = codegen.gen(((KtBinaryExpression) firstArgumentExpression).getLeft());
            Intrinsics.checkNotNullExpressionValue(stackValueGen, "gen(...)");
            return new BoundedValue(stackValueGenerateCallReceiver, true, stackValueGen, false);
        }
        return new BoundedValue(coerceToRangeElementTypeIfRequired(stackValueGenerateCallReceiver), false, coerceToRangeElementTypeIfRequired(CodegenUtilKt.generateCallSingleArgument(codegen, getRangeCall())), false, 10, null);
    }

    @Override // org.jetbrains.kotlin.codegen.range.RangeValue
    public ForLoopGenerator createForLoopGenerator(ExpressionCodegen codegen, KtForExpression forExpression) {
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        Intrinsics.checkNotNullParameter(forExpression, "forExpression");
        ForLoopGenerator forLoopGeneratorCreateConstBoundedForInRangeLiteralGenerator = createConstBoundedForInRangeLiteralGenerator(codegen, forExpression);
        return forLoopGeneratorCreateConstBoundedForInRangeLiteralGenerator == null ? ForInSimpleProgressionLoopGenerator.Companion.fromBoundedValueWithStep1$default(ForInSimpleProgressionLoopGenerator.INSTANCE, codegen, forExpression, getBoundedValue(codegen), ComparisonGeneratorKt.getComparisonGeneratorForKotlinType(getElementKotlinType()), false, 16, null) : forLoopGeneratorCreateConstBoundedForInRangeLiteralGenerator;
    }

    @Override // org.jetbrains.kotlin.codegen.range.ReversableRangeValue
    public ForLoopGenerator createForInReversedLoopGenerator(ExpressionCodegen codegen, KtForExpression forExpression) {
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        Intrinsics.checkNotNullParameter(forExpression, "forExpression");
        ForLoopGenerator forLoopGeneratorCreateConstBoundedRangeForInReversedRangeLiteralGenerator = createConstBoundedRangeForInReversedRangeLiteralGenerator(codegen, forExpression);
        return forLoopGeneratorCreateConstBoundedRangeForInReversedRangeLiteralGenerator == null ? ForInSimpleProgressionLoopGenerator.INSTANCE.fromBoundedValueWithStepMinus1(codegen, forExpression, getBoundedValue(codegen), ComparisonGeneratorKt.getComparisonGeneratorForKotlinType(getElementKotlinType()), true) : forLoopGeneratorCreateConstBoundedRangeForInReversedRangeLiteralGenerator;
    }

    private final ForLoopGenerator createConstBoundedForInRangeLiteralGenerator(ExpressionCodegen codegen, KtForExpression forExpression) {
        KtExpression firstArgumentExpression = CallUtilKt.getFirstArgumentExpression(getRangeCall());
        if (firstArgumentExpression == null) {
            return null;
        }
        return PrimitiveNumberRangeIntrinsicRangeValue.createConstBoundedForLoopGeneratorOrNull$default(this, codegen, forExpression, CodegenUtilKt.generateCallReceiver(codegen, getRangeCall()), firstArgumentExpression, 1, false, 32, null);
    }

    private final ForLoopGenerator createConstBoundedRangeForInReversedRangeLiteralGenerator(ExpressionCodegen codegen, KtForExpression forExpression) {
        KtExpression receiverExpression = CallUtilKt.getReceiverExpression(getRangeCall());
        if (receiverExpression == null) {
            return null;
        }
        return PrimitiveNumberRangeIntrinsicRangeValue.createConstBoundedForLoopGeneratorOrNull$default(this, codegen, forExpression, CodegenUtilKt.generateCallSingleArgument(codegen, getRangeCall()), receiverExpression, -1, false, 32, null);
    }
}
