package org.jetbrains.kotlin.com.intellij.psi.controlFlow;

import org.jetbrains.kotlin.com.intellij.psi.PsiVariable;

/* JADX INFO: loaded from: classes3.dex */
public final class WriteVariableInstruction extends SimpleInstruction {
    public final PsiVariable variable;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 1) {
            objArr[0] = "variable";
        } else {
            objArr[0] = "visitor";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/controlFlow/WriteVariableInstruction";
        if (i != 1) {
            objArr[2] = "<init>";
        } else {
            objArr[2] = "accept";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    WriteVariableInstruction(PsiVariable psiVariable) {
        if (psiVariable == null) {
            $$$reportNull$$$0(0);
        }
        this.variable = psiVariable;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.InstructionBase
    public String toString() {
        return "WRITE " + this.variable.getName();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.SimpleInstruction, org.jetbrains.kotlin.com.intellij.psi.controlFlow.Instruction
    public void accept(ControlFlowInstructionVisitor controlFlowInstructionVisitor, int i, int i2) {
        if (controlFlowInstructionVisitor == null) {
            $$$reportNull$$$0(1);
        }
        controlFlowInstructionVisitor.visitWriteVariableInstruction(this, i, i2);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return this.variable.equals(((WriteVariableInstruction) obj).variable);
    }

    public int hashCode() {
        return this.variable.hashCode() + 753;
    }
}
