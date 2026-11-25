package org.jetbrains.kotlin.com.intellij.psi.controlFlow;

import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;

/* JADX INFO: loaded from: classes3.dex */
public class ConditionalGoToInstruction extends ConditionalBranchingInstruction {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 1) {
            objArr[0] = "role";
        } else {
            objArr[0] = "visitor";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/controlFlow/ConditionalGoToInstruction";
        if (i != 1) {
            objArr[2] = "<init>";
        } else {
            objArr[2] = "accept";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    ConditionalGoToInstruction(int i, PsiExpression psiExpression) {
        this(i, BranchingInstruction.Role.END, psiExpression);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    ConditionalGoToInstruction(int i, BranchingInstruction.Role role, PsiExpression psiExpression) {
        super(i, psiExpression, role);
        if (role == null) {
            $$$reportNull$$$0(0);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.InstructionBase
    public String toString() {
        return "COND_GOTO " + ("[" + this.role + "]") + " " + this.offset;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.ConditionalBranchingInstruction, org.jetbrains.kotlin.com.intellij.psi.controlFlow.BranchingInstruction, org.jetbrains.kotlin.com.intellij.psi.controlFlow.Instruction
    public void accept(ControlFlowInstructionVisitor controlFlowInstructionVisitor, int i, int i2) {
        if (controlFlowInstructionVisitor == null) {
            $$$reportNull$$$0(1);
        }
        controlFlowInstructionVisitor.visitConditionalGoToInstruction(this, i, i2);
    }
}
