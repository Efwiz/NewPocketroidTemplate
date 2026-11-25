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

/* JADX INFO: compiled from: ForInDefinitelySafeSimpleProgressionLoopGenerator.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 \u001b2\u00020\u0001:\u0001\u001bB/\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\fBE\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\r\u001a\u00020\u000e\u0012\u0006\u0010\u000f\u001a\u00020\u0010\u0012\u0006\u0010\u0011\u001a\u00020\u000e\u0012\u0006\u0010\u0012\u001a\u00020\u0010\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\u0013J\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J\u0010\u0010\u0018\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0014J\u0010\u0010\u0019\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J\b\u0010\u001a\u001a\u00020\u0015H\u0014R\u000e\u0010\u0011\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/codegen/range/forLoop/ForInDefinitelySafeSimpleProgressionLoopGenerator;", "Lorg/jetbrains/kotlin/codegen/range/forLoop/AbstractForInRangeLoopGenerator;", "codegen", "Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;", "forExpression", "Lorg/jetbrains/kotlin/psi/KtForExpression;", "boundedValue", "Lorg/jetbrains/kotlin/codegen/range/BoundedValue;", "comparisonGenerator", "Lorg/jetbrains/kotlin/codegen/range/comparison/ComparisonGenerator;", "step", "", "(Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;Lorg/jetbrains/kotlin/psi/KtForExpression;Lorg/jetbrains/kotlin/codegen/range/BoundedValue;Lorg/jetbrains/kotlin/codegen/range/comparison/ComparisonGenerator;I)V", "startValue", "Lorg/jetbrains/kotlin/codegen/StackValue;", "isStartInclusive", "", "endValue", "isEndInclusive", "(Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;Lorg/jetbrains/kotlin/psi/KtForExpression;Lorg/jetbrains/kotlin/codegen/StackValue;ZLorg/jetbrains/kotlin/codegen/StackValue;ZLorg/jetbrains/kotlin/codegen/range/comparison/ComparisonGenerator;I)V", "checkEmptyLoop", "", "loopExit", "Lorg/jetbrains/org/objectweb/asm/Label;", "checkPostConditionAndIncrement", "checkPreCondition", "storeRangeStartAndEnd", "Companion", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ForInDefinitelySafeSimpleProgressionLoopGenerator extends AbstractForInRangeLoopGenerator {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final StackValue endValue;
    private final boolean isEndInclusive;
    private final boolean isStartInclusive;
    private final StackValue startValue;

    @Override // org.jetbrains.kotlin.codegen.range.forLoop.AbstractForInRangeLoopGenerator, org.jetbrains.kotlin.codegen.range.forLoop.ForLoopGenerator
    public void checkEmptyLoop(Label loopExit) {
        Intrinsics.checkNotNullParameter(loopExit, "loopExit");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ForInDefinitelySafeSimpleProgressionLoopGenerator(ExpressionCodegen codegen, KtForExpression forExpression, StackValue startValue, boolean z, StackValue endValue, boolean z2, ComparisonGenerator comparisonGenerator, int i) {
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
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ForInDefinitelySafeSimpleProgressionLoopGenerator(ExpressionCodegen codegen, KtForExpression forExpression, BoundedValue boundedValue, ComparisonGenerator comparisonGenerator, int i) {
        this(codegen, forExpression, i == 1 ? boundedValue.getLowBound() : boundedValue.getHighBound(), i == 1 ? boundedValue.getIsLowInclusive() : boundedValue.getIsHighInclusive(), i == 1 ? boundedValue.getHighBound() : boundedValue.getLowBound(), i == 1 ? boundedValue.getIsHighInclusive() : boundedValue.getIsLowInclusive(), comparisonGenerator, i);
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        Intrinsics.checkNotNullParameter(forExpression, "forExpression");
        Intrinsics.checkNotNullParameter(boundedValue, "boundedValue");
        Intrinsics.checkNotNullParameter(comparisonGenerator, "comparisonGenerator");
    }

    /* JADX INFO: compiled from: ForInDefinitelySafeSimpleProgressionLoopGenerator.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J&\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fJ&\u0010\r\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/codegen/range/forLoop/ForInDefinitelySafeSimpleProgressionLoopGenerator$Companion;", "", "()V", "fromBoundedValueWithStep1", "Lorg/jetbrains/kotlin/codegen/range/forLoop/ForInDefinitelySafeSimpleProgressionLoopGenerator;", "codegen", "Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;", "forExpression", "Lorg/jetbrains/kotlin/psi/KtForExpression;", "boundedValue", "Lorg/jetbrains/kotlin/codegen/range/BoundedValue;", "comparisonGenerator", "Lorg/jetbrains/kotlin/codegen/range/comparison/ComparisonGenerator;", "fromBoundedValueWithStepMinus1", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final ForInDefinitelySafeSimpleProgressionLoopGenerator fromBoundedValueWithStep1(ExpressionCodegen codegen, KtForExpression forExpression, BoundedValue boundedValue, ComparisonGenerator comparisonGenerator) {
            Intrinsics.checkNotNullParameter(codegen, "codegen");
            Intrinsics.checkNotNullParameter(forExpression, "forExpression");
            Intrinsics.checkNotNullParameter(boundedValue, "boundedValue");
            Intrinsics.checkNotNullParameter(comparisonGenerator, "comparisonGenerator");
            return new ForInDefinitelySafeSimpleProgressionLoopGenerator(codegen, forExpression, boundedValue, comparisonGenerator, 1);
        }

        public final ForInDefinitelySafeSimpleProgressionLoopGenerator fromBoundedValueWithStepMinus1(ExpressionCodegen codegen, KtForExpression forExpression, BoundedValue boundedValue, ComparisonGenerator comparisonGenerator) {
            Intrinsics.checkNotNullParameter(codegen, "codegen");
            Intrinsics.checkNotNullParameter(forExpression, "forExpression");
            Intrinsics.checkNotNullParameter(boundedValue, "boundedValue");
            Intrinsics.checkNotNullParameter(comparisonGenerator, "comparisonGenerator");
            return new ForInDefinitelySafeSimpleProgressionLoopGenerator(codegen, forExpression, boundedValue, comparisonGenerator, -1);
        }
    }

    @Override // org.jetbrains.kotlin.codegen.range.forLoop.AbstractForInRangeLoopGenerator
    protected void storeRangeStartAndEnd() {
        loopParameter().store(this.startValue, getV());
        if (!this.isStartInclusive) {
            incrementLoopVariable();
        }
        StackValue.local(getEndVar(), getAsmElementType()).store(this.endValue, getV());
    }

    @Override // org.jetbrains.kotlin.codegen.range.forLoop.AbstractForInProgressionOrRangeLoopGenerator, org.jetbrains.kotlin.codegen.range.forLoop.ForLoopGenerator
    public void checkPreCondition(Label loopExit) {
        Intrinsics.checkNotNullParameter(loopExit, "loopExit");
        loopParameter().put(getAsmElementType(), getElementType(), getV());
        getV().load(getEndVar(), getAsmElementType());
        if (getStep() > 0) {
            if (this.isEndInclusive) {
                getComparisonGenerator().jumpIfGreater(getV(), loopExit);
                return;
            } else {
                getComparisonGenerator().jumpIfGreaterOrEqual(getV(), loopExit);
                return;
            }
        }
        if (this.isEndInclusive) {
            getComparisonGenerator().jumpIfLess(getV(), loopExit);
        } else {
            getComparisonGenerator().jumpIfLessOrEqual(getV(), loopExit);
        }
    }

    @Override // org.jetbrains.kotlin.codegen.range.forLoop.AbstractForInRangeLoopGenerator, org.jetbrains.kotlin.codegen.range.forLoop.AbstractForLoopGenerator
    protected void checkPostConditionAndIncrement(Label loopExit) {
        Intrinsics.checkNotNullParameter(loopExit, "loopExit");
        incrementLoopVariable();
    }
}
