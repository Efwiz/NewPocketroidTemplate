package org.jetbrains.kotlin.codegen.range;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.CodegenUtilKt;
import org.jetbrains.kotlin.codegen.ExpressionCodegen;
import org.jetbrains.kotlin.codegen.range.comparison.ComparisonGeneratorKt;
import org.jetbrains.kotlin.codegen.range.forLoop.ForInSimpleProgressionLoopGenerator;
import org.jetbrains.kotlin.codegen.range.forLoop.ForLoopGenerator;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtForExpression;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;

/* JADX INFO: compiled from: PrimitiveNumberUntilRangeValue.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\u0015\u0012\u000e\u0010\u0003\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00050\u0004¢\u0006\u0002\u0010\u0006J\u001a\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0002J\u0018\u0010\r\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016J\u0018\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\nH\u0014¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/codegen/range/PrimitiveNumberUntilRangeValue;", "Lorg/jetbrains/kotlin/codegen/range/PrimitiveNumberRangeIntrinsicRangeValue;", "Lorg/jetbrains/kotlin/codegen/range/ReversableRangeValue;", "rangeCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "(Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;)V", "createConstBoundedForInReversedUntilGenerator", "Lorg/jetbrains/kotlin/codegen/range/forLoop/ForLoopGenerator;", "codegen", "Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;", "forExpression", "Lorg/jetbrains/kotlin/psi/KtForExpression;", "createForInReversedLoopGenerator", "createForLoopGenerator", "Lorg/jetbrains/kotlin/codegen/range/forLoop/ForInSimpleProgressionLoopGenerator;", "getBoundedValue", "Lorg/jetbrains/kotlin/codegen/range/BoundedValue;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PrimitiveNumberUntilRangeValue extends PrimitiveNumberRangeIntrinsicRangeValue implements ReversableRangeValue {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PrimitiveNumberUntilRangeValue(ResolvedCall<? extends CallableDescriptor> rangeCall) {
        super(rangeCall);
        Intrinsics.checkNotNullParameter(rangeCall, "rangeCall");
    }

    @Override // org.jetbrains.kotlin.codegen.range.PrimitiveNumberRangeIntrinsicRangeValue
    protected BoundedValue getBoundedValue(ExpressionCodegen codegen) {
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        return new BoundedValue(coerceToRangeElementTypeIfRequired(CodegenUtilKt.generateCallReceiver(codegen, getRangeCall())), true, coerceToRangeElementTypeIfRequired(CodegenUtilKt.generateCallSingleArgument(codegen, getRangeCall())), false);
    }

    @Override // org.jetbrains.kotlin.codegen.range.RangeValue
    public ForInSimpleProgressionLoopGenerator createForLoopGenerator(ExpressionCodegen codegen, KtForExpression forExpression) {
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        Intrinsics.checkNotNullParameter(forExpression, "forExpression");
        return ForInSimpleProgressionLoopGenerator.Companion.fromBoundedValueWithStep1$default(ForInSimpleProgressionLoopGenerator.INSTANCE, codegen, forExpression, getBoundedValue(codegen), ComparisonGeneratorKt.getComparisonGeneratorForKotlinType(getElementKotlinType()), false, 16, null);
    }

    @Override // org.jetbrains.kotlin.codegen.range.ReversableRangeValue
    public ForLoopGenerator createForInReversedLoopGenerator(ExpressionCodegen codegen, KtForExpression forExpression) {
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        Intrinsics.checkNotNullParameter(forExpression, "forExpression");
        ForLoopGenerator forLoopGeneratorCreateConstBoundedForInReversedUntilGenerator = createConstBoundedForInReversedUntilGenerator(codegen, forExpression);
        return forLoopGeneratorCreateConstBoundedForInReversedUntilGenerator == null ? ForInSimpleProgressionLoopGenerator.INSTANCE.fromBoundedValueWithStepMinus1(codegen, forExpression, getBoundedValue(codegen), ComparisonGeneratorKt.getComparisonGeneratorForKotlinType(getElementKotlinType()), true) : forLoopGeneratorCreateConstBoundedForInReversedUntilGenerator;
    }

    private final ForLoopGenerator createConstBoundedForInReversedUntilGenerator(ExpressionCodegen codegen, KtForExpression forExpression) {
        KtExpression receiverExpression = CallUtilKt.getReceiverExpression(getRangeCall());
        if (receiverExpression == null) {
            return null;
        }
        return createConstBoundedForLoopGeneratorOrNull(codegen, forExpression, CodegenUtilKt.generateCallSingleArgument(codegen, getRangeCall()), receiverExpression, -1, false);
    }
}
