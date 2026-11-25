package org.jetbrains.kotlin.codegen.range.forLoop;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.DescriptorAsmUtil;
import org.jetbrains.kotlin.codegen.ExpressionCodegen;
import org.jetbrains.kotlin.codegen.StackValue;
import org.jetbrains.kotlin.codegen.range.comparison.ComparisonGenerator;
import org.jetbrains.kotlin.psi.KtForExpression;
import org.jetbrains.org.objectweb.asm.Label;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: AbstractForInRangeLoopGenerator.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\b&\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\b\u0010\u000f\u001a\u00020\u0010H\u0014J\b\u0010\u0011\u001a\u00020\u0010H\u0016J\u0010\u0010\u0012\u001a\u00020\u00102\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\u0010\u0010\u0015\u001a\u00020\u00102\u0006\u0010\u0013\u001a\u00020\u0014H\u0014J\b\u0010\u0016\u001a\u00020\u0010H\u0004J\b\u0010\u0017\u001a\u00020\u0010H$R\u0014\u0010\b\u001a\u00020\tX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\u0006\u001a\u00020\u0007X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/codegen/range/forLoop/AbstractForInRangeLoopGenerator;", "Lorg/jetbrains/kotlin/codegen/range/forLoop/AbstractForInProgressionOrRangeLoopGenerator;", "codegen", "Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;", "forExpression", "Lorg/jetbrains/kotlin/psi/KtForExpression;", "step", "", "comparisonGenerator", "Lorg/jetbrains/kotlin/codegen/range/comparison/ComparisonGenerator;", "(Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;Lorg/jetbrains/kotlin/psi/KtForExpression;ILorg/jetbrains/kotlin/codegen/range/comparison/ComparisonGenerator;)V", "getComparisonGenerator", "()Lorg/jetbrains/kotlin/codegen/range/comparison/ComparisonGenerator;", "getStep", "()I", "assignToLoopParameter", "", "beforeLoop", "checkEmptyLoop", "loopExit", "Lorg/jetbrains/org/objectweb/asm/Label;", "checkPostConditionAndIncrement", "incrementLoopVariable", "storeRangeStartAndEnd", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AbstractForInRangeLoopGenerator extends AbstractForInProgressionOrRangeLoopGenerator {
    private final ComparisonGenerator comparisonGenerator;
    private final int step;

    @Override // org.jetbrains.kotlin.codegen.range.forLoop.AbstractForLoopGenerator
    protected void assignToLoopParameter() {
    }

    protected abstract void storeRangeStartAndEnd();

    protected final int getStep() {
        return this.step;
    }

    protected final ComparisonGenerator getComparisonGenerator() {
        return this.comparisonGenerator;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AbstractForInRangeLoopGenerator(ExpressionCodegen codegen, KtForExpression forExpression, int i, ComparisonGenerator comparisonGenerator) {
        super(codegen, forExpression);
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        Intrinsics.checkNotNullParameter(forExpression, "forExpression");
        Intrinsics.checkNotNullParameter(comparisonGenerator, "comparisonGenerator");
        this.step = i;
        this.comparisonGenerator = comparisonGenerator;
    }

    @Override // org.jetbrains.kotlin.codegen.range.forLoop.AbstractForInProgressionOrRangeLoopGenerator, org.jetbrains.kotlin.codegen.range.forLoop.AbstractForLoopGenerator, org.jetbrains.kotlin.codegen.range.forLoop.ForLoopGenerator
    public void beforeLoop() {
        super.beforeLoop();
        storeRangeStartAndEnd();
    }

    @Override // org.jetbrains.kotlin.codegen.range.forLoop.ForLoopGenerator
    public void checkEmptyLoop(Label loopExit) {
        Intrinsics.checkNotNullParameter(loopExit, "loopExit");
        loopParameter().put(getAsmElementType(), getElementType(), getV());
        getV().load(getEndVar(), getAsmElementType());
        if (this.step > 0) {
            this.comparisonGenerator.jumpIfGreater(getV(), loopExit);
        } else {
            this.comparisonGenerator.jumpIfLess(getV(), loopExit);
        }
    }

    @Override // org.jetbrains.kotlin.codegen.range.forLoop.AbstractForLoopGenerator
    protected void checkPostConditionAndIncrement(Label loopExit) {
        Intrinsics.checkNotNullParameter(loopExit, "loopExit");
        checkPostCondition(loopExit);
        incrementLoopVariable();
    }

    protected final void incrementLoopVariable() {
        if (getLoopParameterType() == Type.INT_TYPE) {
            getV().iinc(getLoopParameterVar(), this.step);
            return;
        }
        StackValue stackValueLoopParameter = loopParameter();
        stackValueLoopParameter.put(getAsmElementType(), getElementType(), getV());
        DescriptorAsmUtil.genIncrement(getAsmElementType(), this.step, getV());
        stackValueLoopParameter.store(StackValue.onStack(getAsmElementType(), getElementType()), getV());
    }
}
