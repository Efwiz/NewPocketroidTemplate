package org.jetbrains.kotlin.com.intellij.psi.controlFlow;

/* JADX INFO: loaded from: classes3.dex */
public final class EmptyInstruction extends SimpleInstruction {
    public static final EmptyInstruction INSTANCE = new EmptyInstruction();

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "visitor", "org/jetbrains/kotlin/com/intellij/psi/controlFlow/EmptyInstruction", "accept"));
    }

    private EmptyInstruction() {
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.InstructionBase
    public String toString() {
        return "EMPTY";
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.SimpleInstruction, org.jetbrains.kotlin.com.intellij.psi.controlFlow.Instruction
    public void accept(ControlFlowInstructionVisitor controlFlowInstructionVisitor, int i, int i2) {
        if (controlFlowInstructionVisitor == null) {
            $$$reportNull$$$0(0);
        }
        controlFlowInstructionVisitor.visitEmptyInstruction(this, i, i2);
    }
}
