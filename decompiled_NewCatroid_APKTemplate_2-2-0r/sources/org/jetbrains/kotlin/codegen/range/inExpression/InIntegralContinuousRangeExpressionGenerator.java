package org.jetbrains.kotlin.codegen.range.inExpression;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.codegen.AsmUtil;
import org.jetbrains.kotlin.codegen.BranchedValue;
import org.jetbrains.kotlin.codegen.FrameMap;
import org.jetbrains.kotlin.codegen.Invert;
import org.jetbrains.kotlin.codegen.StackValue;
import org.jetbrains.kotlin.codegen.range.BoundedValue;
import org.jetbrains.kotlin.codegen.range.UnsignedNumbersCoercionKt;
import org.jetbrains.kotlin.codegen.range.comparison.ComparisonGenerator;
import org.jetbrains.kotlin.codegen.range.comparison.RangeContainsTypeInfo;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.org.objectweb.asm.Label;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;

/* JADX INFO: compiled from: InIntegralContinuousRangeExpressionGenerator.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\fJ\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J\u0010\u0010\u0013\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0016R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/codegen/range/inExpression/InIntegralContinuousRangeExpressionGenerator;", "Lorg/jetbrains/kotlin/codegen/range/inExpression/InExpressionGenerator;", "operatorReference", "Lorg/jetbrains/kotlin/psi/KtSimpleNameExpression;", "rangeContainsTypeInfo", "Lorg/jetbrains/kotlin/codegen/range/comparison/RangeContainsTypeInfo;", "boundedValue", "Lorg/jetbrains/kotlin/codegen/range/BoundedValue;", "comparisonGenerator", "Lorg/jetbrains/kotlin/codegen/range/comparison/ComparisonGenerator;", "frameMap", "Lorg/jetbrains/kotlin/codegen/FrameMap;", "(Lorg/jetbrains/kotlin/psi/KtSimpleNameExpression;Lorg/jetbrains/kotlin/codegen/range/comparison/RangeContainsTypeInfo;Lorg/jetbrains/kotlin/codegen/range/BoundedValue;Lorg/jetbrains/kotlin/codegen/range/comparison/ComparisonGenerator;Lorg/jetbrains/kotlin/codegen/FrameMap;)V", "isNotIn", "", "gen", "Lorg/jetbrains/kotlin/codegen/BranchedValue;", "argument", "Lorg/jetbrains/kotlin/codegen/StackValue;", CompilerOptions.GENERATE, Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class InIntegralContinuousRangeExpressionGenerator implements InExpressionGenerator {
    private final BoundedValue boundedValue;
    private final ComparisonGenerator comparisonGenerator;
    private final FrameMap frameMap;
    private final boolean isNotIn;
    private final RangeContainsTypeInfo rangeContainsTypeInfo;

    public InIntegralContinuousRangeExpressionGenerator(KtSimpleNameExpression operatorReference, RangeContainsTypeInfo rangeContainsTypeInfo, BoundedValue boundedValue, ComparisonGenerator comparisonGenerator, FrameMap frameMap) {
        Intrinsics.checkNotNullParameter(operatorReference, "operatorReference");
        Intrinsics.checkNotNullParameter(rangeContainsTypeInfo, "rangeContainsTypeInfo");
        Intrinsics.checkNotNullParameter(boundedValue, "boundedValue");
        Intrinsics.checkNotNullParameter(comparisonGenerator, "comparisonGenerator");
        Intrinsics.checkNotNullParameter(frameMap, "frameMap");
        this.rangeContainsTypeInfo = rangeContainsTypeInfo;
        this.boundedValue = boundedValue;
        this.comparisonGenerator = comparisonGenerator;
        this.frameMap = frameMap;
        this.isNotIn = Intrinsics.areEqual(operatorReference.getReferencedNameElementType(), KtTokens.NOT_IN);
    }

    @Override // org.jetbrains.kotlin.codegen.range.inExpression.InExpressionGenerator
    public BranchedValue generate(StackValue argument) {
        Intrinsics.checkNotNullParameter(argument, "argument");
        BranchedValue branchedValueGen = gen(argument);
        return this.isNotIn ? new Invert(branchedValueGen) : branchedValueGen;
    }

    private final BranchedValue gen(StackValue argument) {
        return new BranchedValue(argument, this.comparisonGenerator.getComparedType()) { // from class: org.jetbrains.kotlin.codegen.range.inExpression.InIntegralContinuousRangeExpressionGenerator.gen.1
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
                InIntegralContinuousRangeExpressionGenerator inIntegralContinuousRangeExpressionGenerator = this;
                int iEnterTemp = frameMap.enterTemp(operandType);
                Label label = new Label();
                Label label2 = new Label();
                inIntegralContinuousRangeExpressionGenerator.boundedValue.putHighLow(v, getOperandType());
                putCoercedArgumentOnStack(v);
                v.store(iEnterTemp, getOperandType());
                v.load(iEnterTemp, getOperandType());
                if (inIntegralContinuousRangeExpressionGenerator.boundedValue.getIsLowInclusive()) {
                    inIntegralContinuousRangeExpressionGenerator.comparisonGenerator.jumpIfGreater(v, label);
                } else {
                    inIntegralContinuousRangeExpressionGenerator.comparisonGenerator.jumpIfGreaterOrEqual(v, label);
                }
                v.load(iEnterTemp, getOperandType());
                if (inIntegralContinuousRangeExpressionGenerator.boundedValue.getIsHighInclusive()) {
                    inIntegralContinuousRangeExpressionGenerator.comparisonGenerator.jumpIfGreaterOrEqual(v, jumpLabel);
                } else {
                    inIntegralContinuousRangeExpressionGenerator.comparisonGenerator.jumpIfGreater(v, jumpLabel);
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
                InIntegralContinuousRangeExpressionGenerator inIntegralContinuousRangeExpressionGenerator = this;
                int iEnterTemp = frameMap.enterTemp(operandType);
                Label label = new Label();
                inIntegralContinuousRangeExpressionGenerator.boundedValue.putHighLow(v, getOperandType());
                putCoercedArgumentOnStack(v);
                v.store(iEnterTemp, getOperandType());
                v.load(iEnterTemp, getOperandType());
                if (inIntegralContinuousRangeExpressionGenerator.boundedValue.getIsLowInclusive()) {
                    inIntegralContinuousRangeExpressionGenerator.comparisonGenerator.jumpIfLessOrEqual(v, label);
                } else {
                    inIntegralContinuousRangeExpressionGenerator.comparisonGenerator.jumpIfLess(v, label);
                }
                AsmUtil.pop(v, getOperandType());
                v.goTo(jumpLabel);
                v.mark(label);
                v.load(iEnterTemp, getOperandType());
                if (inIntegralContinuousRangeExpressionGenerator.boundedValue.getIsHighInclusive()) {
                    inIntegralContinuousRangeExpressionGenerator.comparisonGenerator.jumpIfLess(v, jumpLabel);
                } else {
                    inIntegralContinuousRangeExpressionGenerator.comparisonGenerator.jumpIfLessOrEqual(v, jumpLabel);
                }
                frameMap.leaveTemp(operandType);
            }

            private final void putCoercedArgumentOnStack(InstructionAdapter v) {
                StackValue arg1;
                KotlinType valueParameterType = this.rangeContainsTypeInfo.getValueParameterType();
                KotlinType rangeElementType = this.rangeContainsTypeInfo.getRangeElementType();
                if (KotlinBuiltIns.isUInt(rangeElementType)) {
                    arg1 = UnsignedNumbersCoercionKt.coerceUnsignedToUInt(getArg1(), valueParameterType, rangeElementType);
                } else if (KotlinBuiltIns.isULong(rangeElementType)) {
                    arg1 = UnsignedNumbersCoercionKt.coerceUnsignedToULong(getArg1(), valueParameterType, rangeElementType);
                } else {
                    arg1 = getArg1();
                }
                arg1.put(getOperandType(), v);
            }
        };
    }
}
