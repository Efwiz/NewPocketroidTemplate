package org.jetbrains.kotlin.com.intellij.psi.controlFlow;

/* JADX INFO: loaded from: classes3.dex */
public class ConditionalThrowToInstruction extends ConditionalBranchingInstruction {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "visitor", "org/jetbrains/kotlin/com/intellij/psi/controlFlow/ConditionalThrowToInstruction", "accept"));
    }

    ConditionalThrowToInstruction(int i) {
        super(i, null, BranchingInstruction.Role.END);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.InstructionBase
    public String toString() {
        return "COND_THROW_TO " + this.offset;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.ConditionalBranchingInstruction, org.jetbrains.kotlin.com.intellij.psi.controlFlow.BranchingInstruction, org.jetbrains.kotlin.com.intellij.psi.controlFlow.Instruction
    public void accept(ControlFlowInstructionVisitor controlFlowInstructionVisitor, int i, int i2) {
        if (controlFlowInstructionVisitor == null) {
            $$$reportNull$$$0(0);
        }
        controlFlowInstructionVisitor.visitConditionalThrowToInstruction(this, i, i2);
    }
}
