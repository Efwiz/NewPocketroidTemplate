package org.jetbrains.kotlin.codegen.range;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.CodegenUtilKt;
import org.jetbrains.kotlin.codegen.ExpressionCodegen;
import org.jetbrains.kotlin.codegen.FrameMap;
import org.jetbrains.kotlin.codegen.range.forLoop.IteratorForLoopGenerator;
import org.jetbrains.kotlin.codegen.range.inExpression.InContinuousRangeOfComparableExpressionGenerator;
import org.jetbrains.kotlin.codegen.range.inExpression.InExpressionGenerator;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.psi.KtForExpression;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.types.RangeUtilKt;

/* JADX INFO: compiled from: ComparableRangeLiteralRangeValue.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u000e\u0010\u0004\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007J\u0018\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\rH\u0016J(\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0010\u001a\u00020\u00112\u000e\u0010\u0012\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00060\u0005H\u0014J\u0018\u0010\u0013\u001a\u00020\u00142\u000e\u0010\u0015\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00060\u0005H\u0014R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/codegen/range/ComparableRangeLiteralRangeValue;", "Lorg/jetbrains/kotlin/codegen/range/CallIntrinsicRangeValue;", "codegen", "Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;", "rangeCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "(Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;)V", "boundedValue", "Lorg/jetbrains/kotlin/codegen/range/BoundedValue;", "createForLoopGenerator", "Lorg/jetbrains/kotlin/codegen/range/forLoop/IteratorForLoopGenerator;", "forExpression", "Lorg/jetbrains/kotlin/psi/KtForExpression;", "createIntrinsicInExpressionGenerator", "Lorg/jetbrains/kotlin/codegen/range/inExpression/InContinuousRangeOfComparableExpressionGenerator;", "operatorReference", "Lorg/jetbrains/kotlin/psi/KtSimpleNameExpression;", "resolvedCall", "isIntrinsicInCall", "", "resolvedCallForIn", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ComparableRangeLiteralRangeValue extends CallIntrinsicRangeValue {
    private final BoundedValue boundedValue;

    @Override // org.jetbrains.kotlin.codegen.range.CallIntrinsicRangeValue
    public /* bridge */ /* synthetic */ InExpressionGenerator createIntrinsicInExpressionGenerator(ExpressionCodegen expressionCodegen, KtSimpleNameExpression ktSimpleNameExpression, ResolvedCall resolvedCall) {
        return createIntrinsicInExpressionGenerator(expressionCodegen, ktSimpleNameExpression, (ResolvedCall<? extends CallableDescriptor>) resolvedCall);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ComparableRangeLiteralRangeValue(ExpressionCodegen codegen, ResolvedCall<? extends CallableDescriptor> rangeCall) {
        super(rangeCall);
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        Intrinsics.checkNotNullParameter(rangeCall, "rangeCall");
        this.boundedValue = new BoundedValue(CodegenUtilKt.generateCallReceiver(codegen, rangeCall), false, CodegenUtilKt.generateCallSingleArgument(codegen, rangeCall), false, 10, null);
    }

    @Override // org.jetbrains.kotlin.codegen.range.RangeValue
    public IteratorForLoopGenerator createForLoopGenerator(ExpressionCodegen codegen, KtForExpression forExpression) {
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        Intrinsics.checkNotNullParameter(forExpression, "forExpression");
        return new IteratorForLoopGenerator(codegen, forExpression);
    }

    @Override // org.jetbrains.kotlin.codegen.range.CallIntrinsicRangeValue
    protected boolean isIntrinsicInCall(ResolvedCall<? extends CallableDescriptor> resolvedCallForIn) {
        Intrinsics.checkNotNullParameter(resolvedCallForIn, "resolvedCallForIn");
        return RangeUtilKt.isClosedRangeContains(resolvedCallForIn.getResultingDescriptor());
    }

    @Override // org.jetbrains.kotlin.codegen.range.CallIntrinsicRangeValue
    protected InContinuousRangeOfComparableExpressionGenerator createIntrinsicInExpressionGenerator(ExpressionCodegen codegen, KtSimpleNameExpression operatorReference, ResolvedCall<? extends CallableDescriptor> resolvedCall) {
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        Intrinsics.checkNotNullParameter(operatorReference, "operatorReference");
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        BoundedValue boundedValue = this.boundedValue;
        FrameMap frameMap = codegen.getFrameMap();
        Intrinsics.checkNotNullExpressionValue(frameMap, "<get-frameMap>(...)");
        return new InContinuousRangeOfComparableExpressionGenerator(operatorReference, boundedValue, frameMap);
    }
}
