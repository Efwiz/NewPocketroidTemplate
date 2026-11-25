package org.jetbrains.kotlin.codegen.range.inExpression;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.codegen.BranchedValue;
import org.jetbrains.kotlin.codegen.FrameMap;
import org.jetbrains.kotlin.codegen.Invert;
import org.jetbrains.kotlin.codegen.StackValue;
import org.jetbrains.kotlin.codegen.range.BoundedValue;
import org.jetbrains.kotlin.codegen.range.comparison.ComparisonGenerator;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.org.objectweb.asm.Label;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;

/* JADX INFO: compiled from: InFloatingPointRangeLiteralExpressionGenerator.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J\u0010\u0010\u0011\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/codegen/range/inExpression/InFloatingPointRangeLiteralExpressionGenerator;", "Lorg/jetbrains/kotlin/codegen/range/inExpression/InExpressionGenerator;", "operatorReference", "Lorg/jetbrains/kotlin/psi/KtSimpleNameExpression;", "rangeLiteral", "Lorg/jetbrains/kotlin/codegen/range/BoundedValue;", "comparisonGenerator", "Lorg/jetbrains/kotlin/codegen/range/comparison/ComparisonGenerator;", "frameMap", "Lorg/jetbrains/kotlin/codegen/FrameMap;", "(Lorg/jetbrains/kotlin/psi/KtSimpleNameExpression;Lorg/jetbrains/kotlin/codegen/range/BoundedValue;Lorg/jetbrains/kotlin/codegen/range/comparison/ComparisonGenerator;Lorg/jetbrains/kotlin/codegen/FrameMap;)V", "isNotIn", "", "gen", "Lorg/jetbrains/kotlin/codegen/BranchedValue;", "argument", "Lorg/jetbrains/kotlin/codegen/StackValue;", CompilerOptions.GENERATE, Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class InFloatingPointRangeLiteralExpressionGenerator implements InExpressionGenerator {
    private final ComparisonGenerator comparisonGenerator;
    private final FrameMap frameMap;
    private final boolean isNotIn;
    private final BoundedValue rangeLiteral;

    public InFloatingPointRangeLiteralExpressionGenerator(KtSimpleNameExpression operatorReference, BoundedValue rangeLiteral, ComparisonGenerator comparisonGenerator, FrameMap frameMap) {
        Intrinsics.checkNotNullParameter(operatorReference, "operatorReference");
        Intrinsics.checkNotNullParameter(rangeLiteral, "rangeLiteral");
        Intrinsics.checkNotNullParameter(comparisonGenerator, "comparisonGenerator");
        Intrinsics.checkNotNullParameter(frameMap, "frameMap");
        this.rangeLiteral = rangeLiteral;
        this.comparisonGenerator = comparisonGenerator;
        this.frameMap = frameMap;
        if (rangeLiteral.getIsLowInclusive()) {
            rangeLiteral.getIsHighInclusive();
        }
        this.isNotIn = Intrinsics.areEqual(operatorReference.getReferencedNameElementType(), KtTokens.NOT_IN);
    }

    @Override // org.jetbrains.kotlin.codegen.range.inExpression.InExpressionGenerator
    public BranchedValue generate(StackValue argument) {
        Intrinsics.checkNotNullParameter(argument, "argument");
        BranchedValue branchedValueGen = gen(argument);
        return this.isNotIn ? new Invert(branchedValueGen) : branchedValueGen;
    }

    private final BranchedValue gen(StackValue argument) {
        return new BranchedValue(argument, this.comparisonGenerator.getComparedType()) { // from class: org.jetbrains.kotlin.codegen.range.inExpression.InFloatingPointRangeLiteralExpressionGenerator.gen.1
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
                frameMap.enterTemp(operandType);
                Label label = new Label();
                genJumpIfFalse(v, label);
                v.goTo(jumpLabel);
                v.mark(label);
                frameMap.leaveTemp(operandType);
            }

            private final void genJumpIfFalse(InstructionAdapter v, Label jumpLabel) {
                FrameMap frameMap = this.frameMap;
                Type operandType = getOperandType();
                InFloatingPointRangeLiteralExpressionGenerator inFloatingPointRangeLiteralExpressionGenerator = this;
                int iEnterTemp = frameMap.enterTemp(operandType);
                Pair<StackValue, Type> pairIntroduceTemporaryIfRequired = introduceTemporaryIfRequired(v, inFloatingPointRangeLiteralExpressionGenerator.rangeLiteral.getLowBound(), getOperandType());
                StackValue stackValueComponent1 = pairIntroduceTemporaryIfRequired.component1();
                Type typeComponent2 = pairIntroduceTemporaryIfRequired.component2();
                Pair<StackValue, Type> pairIntroduceTemporaryIfRequired2 = introduceTemporaryIfRequired(v, inFloatingPointRangeLiteralExpressionGenerator.rangeLiteral.getHighBound(), getOperandType());
                StackValue stackValueComponent2 = pairIntroduceTemporaryIfRequired2.component1();
                Type typeComponent3 = pairIntroduceTemporaryIfRequired2.component2();
                StackValue.Local local = StackValue.local(iEnterTemp, getOperandType());
                Intrinsics.checkNotNullExpressionValue(local, "local(...)");
                local.store(getArg1(), v);
                local.put(getOperandType(), v);
                stackValueComponent1.put(getOperandType(), v);
                inFloatingPointRangeLiteralExpressionGenerator.comparisonGenerator.jumpIfLess(v, jumpLabel);
                local.put(getOperandType(), v);
                stackValueComponent2.put(getOperandType(), v);
                inFloatingPointRangeLiteralExpressionGenerator.comparisonGenerator.jumpIfGreater(v, jumpLabel);
                if (typeComponent3 != null) {
                    inFloatingPointRangeLiteralExpressionGenerator.frameMap.leaveTemp(typeComponent3);
                }
                if (typeComponent2 != null) {
                    inFloatingPointRangeLiteralExpressionGenerator.frameMap.leaveTemp(typeComponent2);
                }
                frameMap.leaveTemp(operandType);
            }

            private final Pair<StackValue, Type> introduceTemporaryIfRequired(InstructionAdapter v, StackValue value, Type type) {
                if (value.canHaveSideEffects()) {
                    StackValue.Local local = StackValue.local(this.frameMap.enterTemp(type), type);
                    Intrinsics.checkNotNullExpressionValue(local, "local(...)");
                    StackValue.Local local2 = local;
                    local2.store(value, v);
                    value = local2;
                } else {
                    type = null;
                }
                return new Pair<>(value, type);
            }
        };
    }
}
