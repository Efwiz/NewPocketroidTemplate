package org.jetbrains.kotlin.codegen.range;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import org.jetbrains.kotlin.codegen.ExpressionCodegen;
import org.jetbrains.kotlin.codegen.range.forLoop.ForLoopGenerator;
import org.jetbrains.kotlin.codegen.range.forLoop.IteratorWithIndexForLoopGenerator;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.psi.KtDestructuringDeclaration;
import org.jetbrains.kotlin.psi.KtForExpression;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: SequenceWithIndexRangeValue.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u000e\u0010\u0002\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005J0\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u000e\u0010\u0002\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u0003H\u0014¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/codegen/range/SequenceWithIndexRangeValue;", "Lorg/jetbrains/kotlin/codegen/range/AbstractDestructuredPairRangeValue;", "rangeCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "(Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;)V", "createDestructuredPairForLoopGenerator", "Lorg/jetbrains/kotlin/codegen/range/forLoop/ForLoopGenerator;", "codegen", "Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;", "forExpression", "Lorg/jetbrains/kotlin/psi/KtForExpression;", "loopParameter", "Lorg/jetbrains/kotlin/psi/KtDestructuringDeclaration;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SequenceWithIndexRangeValue extends AbstractDestructuredPairRangeValue {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SequenceWithIndexRangeValue(ResolvedCall<? extends CallableDescriptor> rangeCall) {
        super(rangeCall);
        Intrinsics.checkNotNullParameter(rangeCall, "rangeCall");
    }

    @Override // org.jetbrains.kotlin.codegen.range.AbstractDestructuredPairRangeValue
    protected ForLoopGenerator createDestructuredPairForLoopGenerator(ExpressionCodegen codegen, KtForExpression forExpression, KtDestructuringDeclaration loopParameter, ResolvedCall<? extends CallableDescriptor> rangeCall) {
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        Intrinsics.checkNotNullParameter(forExpression, "forExpression");
        Intrinsics.checkNotNullParameter(loopParameter, "loopParameter");
        Intrinsics.checkNotNullParameter(rangeCall, "rangeCall");
        Type type = Type.getType((Class<?>) Sequence.class);
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        return new IteratorWithIndexForLoopGenerator(codegen, forExpression, loopParameter, rangeCall, type);
    }
}
