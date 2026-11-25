package org.jetbrains.kotlin.codegen.range.forLoop;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.ExpressionCodegen;
import org.jetbrains.kotlin.codegen.StackValue;
import org.jetbrains.kotlin.codegen.range.BoundedValue;
import org.jetbrains.kotlin.codegen.range.comparison.ComparisonGenerator;
import org.jetbrains.kotlin.psi.KtForExpression;
import org.jetbrains.org.objectweb.asm.Label;

/* JADX INFO: compiled from: ForInSimpleProgressionLoopGenerator.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 \u001c2\u00020\u0001:\u0001\u001cB7\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r¢\u0006\u0002\u0010\u000eBM\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u000f\u001a\u00020\u0010\u0012\u0006\u0010\u0011\u001a\u00020\t\u0012\u0006\u0010\u0012\u001a\u00020\u0010\u0012\u0006\u0010\u0013\u001a\u00020\t\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r¢\u0006\u0002\u0010\u0014J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0016J\u0010\u0010\u0019\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0014J\u0010\u0010\u001a\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0016J\b\u0010\u001b\u001a\u00020\u0016H\u0014R\u000e\u0010\u0012\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/codegen/range/forLoop/ForInSimpleProgressionLoopGenerator;", "Lorg/jetbrains/kotlin/codegen/range/forLoop/AbstractForInRangeLoopGenerator;", "codegen", "Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;", "forExpression", "Lorg/jetbrains/kotlin/psi/KtForExpression;", "boundedValue", "Lorg/jetbrains/kotlin/codegen/range/BoundedValue;", "inverseBoundsEvaluationOrder", "", "comparisonGenerator", "Lorg/jetbrains/kotlin/codegen/range/comparison/ComparisonGenerator;", "step", "", "(Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;Lorg/jetbrains/kotlin/psi/KtForExpression;Lorg/jetbrains/kotlin/codegen/range/BoundedValue;ZLorg/jetbrains/kotlin/codegen/range/comparison/ComparisonGenerator;I)V", "startValue", "Lorg/jetbrains/kotlin/codegen/StackValue;", "isStartInclusive", "endValue", "isEndInclusive", "(Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;Lorg/jetbrains/kotlin/psi/KtForExpression;Lorg/jetbrains/kotlin/codegen/StackValue;ZLorg/jetbrains/kotlin/codegen/StackValue;ZZLorg/jetbrains/kotlin/codegen/range/comparison/ComparisonGenerator;I)V", "checkEmptyLoop", "", "loopExit", "Lorg/jetbrains/org/objectweb/asm/Label;", "checkPostConditionAndIncrement", "checkPreCondition", "storeRangeStartAndEnd", "Companion", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ForInSimpleProgressionLoopGenerator extends AbstractForInRangeLoopGenerator {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final StackValue endValue;
    private final boolean inverseBoundsEvaluationOrder;
    private final boolean isEndInclusive;
    private final boolean isStartInclusive;
    private final StackValue startValue;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ForInSimpleProgressionLoopGenerator(ExpressionCodegen codegen, KtForExpression forExpression, StackValue startValue, boolean z, StackValue endValue, boolean z2, boolean z3, ComparisonGenerator comparisonGenerator, int i) {
        super(codegen, forExpression, i, comparisonGenerator);
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        Intrinsics.checkNotNullParameter(forExpression, "forExpression");
        Intrinsics.checkNotNullParameter(startValue, "startValue");
        Intrinsics.checkNotNullParameter(endValue, "endValue");
        Intrinsics.checkNotNullParameter(comparisonGenerator, "comparisonGenerator");
        this.startValue = startValue;
        this.isStartInclusive = z;
        this.endValue = endValue;
        this.isEndInclusive = z2;
        this.inverseBoundsEvaluationOrder = z3;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ForInSimpleProgressionLoopGenerator(ExpressionCodegen codegen, KtForExpression forExpression, BoundedValue boundedValue, boolean z, ComparisonGenerator comparisonGenerator, int i) {
        this(codegen, forExpression, i == 1 ? boundedValue.getLowBound() : boundedValue.getHighBound(), i == 1 ? boundedValue.getIsLowInclusive() : boundedValue.getIsHighInclusive(), i == 1 ? boundedValue.getHighBound() : boundedValue.getLowBound(), i == 1 ? boundedValue.getIsHighInclusive() : boundedValue.getIsLowInclusive(), z, comparisonGenerator, i);
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        Intrinsics.checkNotNullParameter(forExpression, "forExpression");
        Intrinsics.checkNotNullParameter(boundedValue, "boundedValue");
        Intrinsics.checkNotNullParameter(comparisonGenerator, "comparisonGenerator");
    }

    /* JADX INFO: compiled from: ForInSimpleProgressionLoopGenerator.kt */
    @Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J0\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\r\u001a\u00020\u000eJ0\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\r\u001a\u00020\u000e¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/codegen/range/forLoop/ForInSimpleProgressionLoopGenerator$Companion;", "", "()V", "fromBoundedValueWithStep1", "Lorg/jetbrains/kotlin/codegen/range/forLoop/ForInSimpleProgressionLoopGenerator;", "codegen", "Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;", "forExpression", "Lorg/jetbrains/kotlin/psi/KtForExpression;", "boundedValue", "Lorg/jetbrains/kotlin/codegen/range/BoundedValue;", "comparisonGenerator", "Lorg/jetbrains/kotlin/codegen/range/comparison/ComparisonGenerator;", "inverseBoundsEvaluationOrder", "", "fromBoundedValueWithStepMinus1", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public static /* synthetic */ ForInSimpleProgressionLoopGenerator fromBoundedValueWithStep1$default(Companion companion, ExpressionCodegen expressionCodegen, KtForExpression ktForExpression, BoundedValue boundedValue, ComparisonGenerator comparisonGenerator, boolean z, int i, Object obj) {
            if ((i & 16) != 0) {
                z = false;
            }
            return companion.fromBoundedValueWithStep1(expressionCodegen, ktForExpression, boundedValue, comparisonGenerator, z);
        }

        public final ForInSimpleProgressionLoopGenerator fromBoundedValueWithStep1(ExpressionCodegen codegen, KtForExpression forExpression, BoundedValue boundedValue, ComparisonGenerator comparisonGenerator, boolean inverseBoundsEvaluationOrder) {
            Intrinsics.checkNotNullParameter(codegen, "codegen");
            Intrinsics.checkNotNullParameter(forExpression, "forExpression");
            Intrinsics.checkNotNullParameter(boundedValue, "boundedValue");
            Intrinsics.checkNotNullParameter(comparisonGenerator, "comparisonGenerator");
            return new ForInSimpleProgressionLoopGenerator(codegen, forExpression, boundedValue, inverseBoundsEvaluationOrder, comparisonGenerator, 1);
        }

        public static /* synthetic */ ForInSimpleProgressionLoopGenerator fromBoundedValueWithStepMinus1$default(Companion companion, ExpressionCodegen expressionCodegen, KtForExpression ktForExpression, BoundedValue boundedValue, ComparisonGenerator comparisonGenerator, boolean z, int i, Object obj) {
            if ((i & 16) != 0) {
                z = false;
            }
            return companion.fromBoundedValueWithStepMinus1(expressionCodegen, ktForExpression, boundedValue, comparisonGenerator, z);
        }

        public final ForInSimpleProgressionLoopGenerator fromBoundedValueWithStepMinus1(ExpressionCodegen codegen, KtForExpression forExpression, BoundedValue boundedValue, ComparisonGenerator comparisonGenerator, boolean inverseBoundsEvaluationOrder) {
            Intrinsics.checkNotNullParameter(codegen, "codegen");
            Intrinsics.checkNotNullParameter(forExpression, "forExpression");
            Intrinsics.checkNotNullParameter(boundedValue, "boundedValue");
            Intrinsics.checkNotNullParameter(comparisonGenerator, "comparisonGenerator");
            return new ForInSimpleProgressionLoopGenerator(codegen, forExpression, boundedValue, inverseBoundsEvaluationOrder, comparisonGenerator, -1);
        }
    }

    @Override // org.jetbrains.kotlin.codegen.range.forLoop.AbstractForInRangeLoopGenerator
    protected void storeRangeStartAndEnd() {
        if (this.inverseBoundsEvaluationOrder) {
            StackValue.local(getEndVar(), getAsmElementType()).store(this.endValue, getV());
            loopParameter().store(this.startValue, getV());
        } else {
            loopParameter().store(this.startValue, getV());
            StackValue.local(getEndVar(), getAsmElementType()).store(this.endValue, getV());
        }
        if (this.isStartInclusive) {
            return;
        }
        incrementLoopVariable();
    }

    @Override // org.jetbrains.kotlin.codegen.range.forLoop.AbstractForInRangeLoopGenerator, org.jetbrains.kotlin.codegen.range.forLoop.ForLoopGenerator
    public void checkEmptyLoop(Label loopExit) {
        Intrinsics.checkNotNullParameter(loopExit, "loopExit");
        if (this.isEndInclusive) {
            super.checkEmptyLoop(loopExit);
        }
    }

    @Override // org.jetbrains.kotlin.codegen.range.forLoop.AbstractForInProgressionOrRangeLoopGenerator, org.jetbrains.kotlin.codegen.range.forLoop.ForLoopGenerator
    public void checkPreCondition(Label loopExit) {
        Intrinsics.checkNotNullParameter(loopExit, "loopExit");
        if (this.isEndInclusive) {
            return;
        }
        loopParameter().put(getAsmElementType(), getElementType(), getV());
        getV().load(getEndVar(), getAsmElementType());
        if (getStep() > 0) {
            getComparisonGenerator().jumpIfGreaterOrEqual(getV(), loopExit);
        } else {
            getComparisonGenerator().jumpIfLessOrEqual(getV(), loopExit);
        }
    }

    @Override // org.jetbrains.kotlin.codegen.range.forLoop.AbstractForInRangeLoopGenerator, org.jetbrains.kotlin.codegen.range.forLoop.AbstractForLoopGenerator
    protected void checkPostConditionAndIncrement(Label loopExit) {
        Intrinsics.checkNotNullParameter(loopExit, "loopExit");
        if (this.isEndInclusive) {
            super.checkPostConditionAndIncrement(loopExit);
        } else {
            incrementLoopVariable();
        }
    }
}
