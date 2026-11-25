package org.jetbrains.kotlin.codegen.range;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.CodegenUtilKt;
import org.jetbrains.kotlin.codegen.ExpressionCodegen;
import org.jetbrains.kotlin.codegen.StackValue;
import org.jetbrains.kotlin.codegen.range.comparison.IntComparisonGeneratorKt;
import org.jetbrains.kotlin.codegen.range.forLoop.ForInDefinitelySafeSimpleProgressionLoopGenerator;
import org.jetbrains.kotlin.codegen.range.forLoop.ForInSimpleProgressionLoopGenerator;
import org.jetbrains.kotlin.codegen.range.forLoop.ForLoopGenerator;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.psi.KtForExpression;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;

/* JADX INFO: compiled from: ArrayIndicesRangeValue.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\u0015\u0012\u000e\u0010\u0003\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00050\u0004¢\u0006\u0002\u0010\u0006J\u0018\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0018\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u000b\u001a\u00020\fH\u0014R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/codegen/range/ArrayIndicesRangeValue;", "Lorg/jetbrains/kotlin/codegen/range/PrimitiveNumberRangeIntrinsicRangeValue;", "Lorg/jetbrains/kotlin/codegen/range/ReversableRangeValue;", "rangeCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "(Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;)V", "expectedReceiverType", "Lorg/jetbrains/kotlin/types/KotlinType;", "createForInReversedLoopGenerator", "Lorg/jetbrains/kotlin/codegen/range/forLoop/ForLoopGenerator;", "codegen", "Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;", "forExpression", "Lorg/jetbrains/kotlin/psi/KtForExpression;", "createForLoopGenerator", "Lorg/jetbrains/kotlin/codegen/range/forLoop/ForInSimpleProgressionLoopGenerator;", "getBoundedValue", "Lorg/jetbrains/kotlin/codegen/range/BoundedValue;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ArrayIndicesRangeValue extends PrimitiveNumberRangeIntrinsicRangeValue implements ReversableRangeValue {
    private final KotlinType expectedReceiverType;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ArrayIndicesRangeValue(ResolvedCall<? extends CallableDescriptor> rangeCall) {
        super(rangeCall);
        Intrinsics.checkNotNullParameter(rangeCall, "rangeCall");
        KotlinType expectedReceiverType = ExpressionCodegen.getExpectedReceiverType(rangeCall);
        Intrinsics.checkNotNullExpressionValue(expectedReceiverType, "getExpectedReceiverType(...)");
        this.expectedReceiverType = expectedReceiverType;
    }

    @Override // org.jetbrains.kotlin.codegen.range.PrimitiveNumberRangeIntrinsicRangeValue
    protected BoundedValue getBoundedValue(final ExpressionCodegen codegen) {
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        StackValue stackValueConstant = StackValue.constant(0, codegen.asmType(getElementKotlinType()), getElementKotlinType());
        Intrinsics.checkNotNullExpressionValue(stackValueConstant, "constant(...)");
        StackValue stackValueOperation = StackValue.operation(Type.INT_TYPE, new Function1<InstructionAdapter, Unit>() { // from class: org.jetbrains.kotlin.codegen.range.ArrayIndicesRangeValue.getBoundedValue.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(InstructionAdapter instructionAdapter) {
                invoke2(instructionAdapter);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(InstructionAdapter instructionAdapter) {
                CodegenUtilKt.generateCallReceiver(codegen, this.getRangeCall()).put(codegen.asmType(this.expectedReceiverType), this.expectedReceiverType, instructionAdapter);
                instructionAdapter.arraylength();
            }
        });
        Intrinsics.checkNotNullExpressionValue(stackValueOperation, "operation(...)");
        return new BoundedValue(stackValueConstant, true, stackValueOperation, false);
    }

    @Override // org.jetbrains.kotlin.codegen.range.RangeValue
    public ForInSimpleProgressionLoopGenerator createForLoopGenerator(ExpressionCodegen codegen, KtForExpression forExpression) {
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        Intrinsics.checkNotNullParameter(forExpression, "forExpression");
        return ForInSimpleProgressionLoopGenerator.Companion.fromBoundedValueWithStep1$default(ForInSimpleProgressionLoopGenerator.INSTANCE, codegen, forExpression, getBoundedValue(codegen), IntComparisonGeneratorKt.getIntComparisonGenerator(), false, 16, null);
    }

    @Override // org.jetbrains.kotlin.codegen.range.ReversableRangeValue
    public ForLoopGenerator createForInReversedLoopGenerator(ExpressionCodegen codegen, KtForExpression forExpression) {
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        Intrinsics.checkNotNullParameter(forExpression, "forExpression");
        return ForInDefinitelySafeSimpleProgressionLoopGenerator.INSTANCE.fromBoundedValueWithStepMinus1(codegen, forExpression, getBoundedValue(codegen), IntComparisonGeneratorKt.getIntComparisonGenerator());
    }
}
