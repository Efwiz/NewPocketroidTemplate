package org.jetbrains.kotlin.codegen.range;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.ExpressionCodegen;
import org.jetbrains.kotlin.codegen.range.forLoop.ForLoopGenerator;
import org.jetbrains.kotlin.codegen.range.forLoop.IteratorForLoopGenerator;
import org.jetbrains.kotlin.codegen.range.inExpression.CallBasedInExpressionGenerator;
import org.jetbrains.kotlin.codegen.range.inExpression.InExpressionGenerator;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.psi.KtDestructuringDeclaration;
import org.jetbrains.kotlin.psi.KtForExpression;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;

/* JADX INFO: compiled from: AbstractDestructuredPairRangeValue.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B\u0015\u0012\u000e\u0010\u0002\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005J0\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u000e\u0010\u0002\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u0003H$J\u0018\u0010\u0010\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016J\u0018\u0010\u0011\u001a\u00020\u00122\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0013\u001a\u00020\u0014H\u0016R\u001c\u0010\u0002\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u0003X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/codegen/range/AbstractDestructuredPairRangeValue;", "Lorg/jetbrains/kotlin/codegen/range/RangeValue;", "rangeCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "(Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;)V", "getRangeCall", "()Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "createDestructuredPairForLoopGenerator", "Lorg/jetbrains/kotlin/codegen/range/forLoop/ForLoopGenerator;", "codegen", "Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;", "forExpression", "Lorg/jetbrains/kotlin/psi/KtForExpression;", "loopParameter", "Lorg/jetbrains/kotlin/psi/KtDestructuringDeclaration;", "createForLoopGenerator", "createInExpressionGenerator", "Lorg/jetbrains/kotlin/codegen/range/inExpression/InExpressionGenerator;", "operatorReference", "Lorg/jetbrains/kotlin/psi/KtSimpleNameExpression;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AbstractDestructuredPairRangeValue implements RangeValue {
    private final ResolvedCall<? extends CallableDescriptor> rangeCall;

    protected abstract ForLoopGenerator createDestructuredPairForLoopGenerator(ExpressionCodegen codegen, KtForExpression forExpression, KtDestructuringDeclaration loopParameter, ResolvedCall<? extends CallableDescriptor> rangeCall);

    public AbstractDestructuredPairRangeValue(ResolvedCall<? extends CallableDescriptor> rangeCall) {
        Intrinsics.checkNotNullParameter(rangeCall, "rangeCall");
        this.rangeCall = rangeCall;
    }

    protected final ResolvedCall<? extends CallableDescriptor> getRangeCall() {
        return this.rangeCall;
    }

    @Override // org.jetbrains.kotlin.codegen.range.RangeValue
    public ForLoopGenerator createForLoopGenerator(ExpressionCodegen codegen, KtForExpression forExpression) {
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        Intrinsics.checkNotNullParameter(forExpression, "forExpression");
        KtDestructuringDeclaration destructuringDeclaration = forExpression.getDestructuringDeclaration();
        if (destructuringDeclaration != null) {
            return createDestructuredPairForLoopGenerator(codegen, forExpression, destructuringDeclaration, this.rangeCall);
        }
        return new IteratorForLoopGenerator(codegen, forExpression);
    }

    @Override // org.jetbrains.kotlin.codegen.range.RangeValue
    public InExpressionGenerator createInExpressionGenerator(ExpressionCodegen codegen, KtSimpleNameExpression operatorReference) {
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        Intrinsics.checkNotNullParameter(operatorReference, "operatorReference");
        return new CallBasedInExpressionGenerator(codegen, operatorReference);
    }
}
