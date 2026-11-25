package org.jetbrains.kotlin.com.intellij.psi.controlFlow;

import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;

/* JADX INFO: loaded from: classes3.dex */
public abstract class ConditionalBranchingInstruction extends BranchingInstruction {
    protected static final Logger LOG = Logger.getInstance((Class<?>) ConditionalGoToInstruction.class);
    public final PsiExpression expression;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 1) {
            objArr[0] = "role";
        } else {
            objArr[0] = "visitor";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/controlFlow/ConditionalBranchingInstruction";
        if (i != 1) {
            objArr[2] = "<init>";
        } else {
            objArr[2] = "accept";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.Instruction
    public int nNext() {
        return 2;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    ConditionalBranchingInstruction(int i, PsiExpression psiExpression, BranchingInstruction.Role role) {
        super(i, role);
        if (role == null) {
            $$$reportNull$$$0(0);
        }
        this.expression = psiExpression;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.Instruction
    public int getNext(int i, int i2) {
        if (i2 == 0) {
            return this.offset;
        }
        if (i2 == 1) {
            return i + 1;
        }
        LOG.assertTrue(false);
        return -1;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.BranchingInstruction, org.jetbrains.kotlin.com.intellij.psi.controlFlow.Instruction
    public void accept(ControlFlowInstructionVisitor controlFlowInstructionVisitor, int i, int i2) {
        if (controlFlowInstructionVisitor == null) {
            $$$reportNull$$$0(1);
        }
        controlFlowInstructionVisitor.visitConditionalBranchingInstruction(this, i, i2);
    }
}
