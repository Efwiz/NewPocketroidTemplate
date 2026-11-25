package org.jetbrains.kotlin.codegen.range.inExpression;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.codegen.AsmUtil;
import org.jetbrains.kotlin.codegen.BranchedValue;
import org.jetbrains.kotlin.codegen.FrameMap;
import org.jetbrains.kotlin.codegen.Invert;
import org.jetbrains.kotlin.codegen.StackValue;
import org.jetbrains.kotlin.codegen.range.BoundedValue;
import org.jetbrains.kotlin.codegen.range.comparison.ObjectComparisonGenerator;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.org.objectweb.asm.Label;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;

/* JADX INFO: compiled from: InContinuousRangeOfComparableExpressionGenerator.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J\u0010\u0010\u0011\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/codegen/range/inExpression/InContinuousRangeOfComparableExpressionGenerator;", "Lorg/jetbrains/kotlin/codegen/range/inExpression/InExpressionGenerator;", "operatorReference", "Lorg/jetbrains/kotlin/psi/KtSimpleNameExpression;", "boundedValue", "Lorg/jetbrains/kotlin/codegen/range/BoundedValue;", "frameMap", "Lorg/jetbrains/kotlin/codegen/FrameMap;", "(Lorg/jetbrains/kotlin/psi/KtSimpleNameExpression;Lorg/jetbrains/kotlin/codegen/range/BoundedValue;Lorg/jetbrains/kotlin/codegen/FrameMap;)V", "comparisonGenerator", "Lorg/jetbrains/kotlin/codegen/range/comparison/ObjectComparisonGenerator;", "isNotIn", "", "gen", "Lorg/jetbrains/kotlin/codegen/BranchedValue;", "argument", "Lorg/jetbrains/kotlin/codegen/StackValue;", CompilerOptions.GENERATE, Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class InContinuousRangeOfComparableExpressionGenerator implements InExpressionGenerator {
    private final BoundedValue boundedValue;
    private final ObjectComparisonGenerator comparisonGenerator;
    private final FrameMap frameMap;
    private final boolean isNotIn;

    public InContinuousRangeOfComparableExpressionGenerator(KtSimpleNameExpression operatorReference, BoundedValue boundedValue, FrameMap frameMap) {
        Intrinsics.checkNotNullParameter(operatorReference, "operatorReference");
        Intrinsics.checkNotNullParameter(boundedValue, "boundedValue");
        Intrinsics.checkNotNullParameter(frameMap, "frameMap");
        this.boundedValue = boundedValue;
        this.frameMap = frameMap;
        this.isNotIn = Intrinsics.areEqual(operatorReference.getReferencedNameElementType(), KtTokens.NOT_IN);
        this.comparisonGenerator = ObjectComparisonGenerator.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.codegen.range.inExpression.InExpressionGenerator
    public BranchedValue generate(StackValue argument) {
        Intrinsics.checkNotNullParameter(argument, "argument");
        BranchedValue branchedValueGen = gen(argument);
        return this.isNotIn ? new Invert(branchedValueGen) : branchedValueGen;
    }

    private final BranchedValue gen(StackValue argument) {
        return new BranchedValue(argument, this.comparisonGenerator.getComparedType()) { // from class: org.jetbrains.kotlin.codegen.range.inExpression.InContinuousRangeOfComparableExpressionGenerator.gen.1
            @Override // org.jetbrains.kotlin.codegen.BranchedValue
            public void condJump(Label jumpLabel, InstructionAdapter v, boolean jumpIfFalse) {
                Intrinsics.checkNotNullParameter(jumpLabel, "jumpLabel");
                Intrinsics.checkNotNullParameter(v, "v");
                if (jumpIfFalse) {
                    genJumpIfFalse(v, jumpLabel);
                } else {
                    genJumpIfTrue(v, jumpLabel);
                }
            }

            private final void genJumpIfTrue(InstructionAdapter v, Label jumpLabel) {
                FrameMap frameMap = this.frameMap;
                Type operandType = getOperandType();
                InContinuousRangeOfComparableExpressionGenerator inContinuousRangeOfComparableExpressionGenerator = this;
                int iEnterTemp = frameMap.enterTemp(operandType);
                Label label = new Label();
                Label label2 = new Label();
                inContinuousRangeOfComparableExpressionGenerator.boundedValue.putHighLow(v, getOperandType());
                getArg1().put(getOperandType(), v);
                v.store(iEnterTemp, getOperandType());
                v.load(iEnterTemp, getOperandType());
                if (inContinuousRangeOfComparableExpressionGenerator.boundedValue.getIsLowInclusive()) {
                    v.swap();
                    inContinuousRangeOfComparableExpressionGenerator.comparisonGenerator.jumpIfLess(v, label);
                } else {
                    v.swap();
                    inContinuousRangeOfComparableExpressionGenerator.comparisonGenerator.jumpIfLessOrEqual(v, label);
                }
                v.load(iEnterTemp, getOperandType());
                if (inContinuousRangeOfComparableExpressionGenerator.boundedValue.getIsHighInclusive()) {
                    v.swap();
                    inContinuousRangeOfComparableExpressionGenerator.comparisonGenerator.jumpIfLessOrEqual(v, jumpLabel);
                } else {
                    v.swap();
                    inContinuousRangeOfComparableExpressionGenerator.comparisonGenerator.jumpIfLess(v, jumpLabel);
                }
                v.goTo(label2);
                v.mark(label);
                AsmUtil.pop(v, getOperandType());
                v.mark(label2);
                frameMap.leaveTemp(operandType);
            }

            private final void genJumpIfFalse(InstructionAdapter v, Label jumpLabel) {
                FrameMap frameMap = this.frameMap;
                Type operandType = getOperandType();
                InContinuousRangeOfComparableExpressionGenerator inContinuousRangeOfComparableExpressionGenerator = this;
                int iEnterTemp = frameMap.enterTemp(operandType);
                Label label = new Label();
                inContinuousRangeOfComparableExpressionGenerator.boundedValue.putHighLow(v, getOperandType());
                getArg1().put(getOperandType(), v);
                v.store(iEnterTemp, getOperandType());
                v.load(iEnterTemp, getOperandType());
                if (inContinuousRangeOfComparableExpressionGenerator.boundedValue.getIsLowInclusive()) {
                    v.swap();
                    inContinuousRangeOfComparableExpressionGenerator.comparisonGenerator.jumpIfGreaterOrEqual(v, label);
                } else {
                    v.swap();
                    inContinuousRangeOfComparableExpressionGenerator.comparisonGenerator.jumpIfGreater(v, label);
                }
                AsmUtil.pop(v, getOperandType());
                v.goTo(jumpLabel);
                v.mark(label);
                v.load(iEnterTemp, getOperandType());
                if (inContinuousRangeOfComparableExpressionGenerator.boundedValue.getIsHighInclusive()) {
                    v.swap();
                    inContinuousRangeOfComparableExpressionGenerator.comparisonGenerator.jumpIfGreater(v, jumpLabel);
                } else {
                    v.swap();
                    inContinuousRangeOfComparableExpressionGenerator.comparisonGenerator.jumpIfGreaterOrEqual(v, jumpLabel);
                }
                frameMap.leaveTemp(operandType);
            }
        };
    }
}
