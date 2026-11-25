package org.jetbrains.kotlin.com.intellij.psi.controlFlow;

/* JADX INFO: loaded from: classes3.dex */
public class CallInstruction extends GoToInstruction {
    int procBegin;
    int procEnd;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "visitor", "org/jetbrains/kotlin/com/intellij/psi/controlFlow/CallInstruction", "accept"));
    }

    public CallInstruction(int i, int i2) {
        super(i);
        this.procBegin = i;
        this.procEnd = i2;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.GoToInstruction, org.jetbrains.kotlin.com.intellij.psi.controlFlow.InstructionBase
    public String toString() {
        return "CALL " + this.offset;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.GoToInstruction, org.jetbrains.kotlin.com.intellij.psi.controlFlow.BranchingInstruction, org.jetbrains.kotlin.com.intellij.psi.controlFlow.Instruction
    public void accept(ControlFlowInstructionVisitor controlFlowInstructionVisitor, int i, int i2) {
        if (controlFlowInstructionVisitor == null) {
            $$$reportNull$$$0(0);
        }
        controlFlowInstructionVisitor.visitCallInstruction(this, i, i2);
    }
}
