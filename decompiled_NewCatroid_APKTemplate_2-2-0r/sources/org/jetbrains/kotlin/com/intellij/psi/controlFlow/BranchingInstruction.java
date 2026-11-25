package org.jetbrains.kotlin.com.intellij.psi.controlFlow;

/* JADX INFO: loaded from: classes3.dex */
public abstract class BranchingInstruction extends InstructionBase {
    public int offset;
    public final Role role;

    public enum Role {
        THEN,
        ELSE,
        END
    }

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 1) {
            objArr[0] = "role";
        } else {
            objArr[0] = "visitor";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/controlFlow/BranchingInstruction";
        if (i != 1) {
            objArr[2] = "<init>";
        } else {
            objArr[2] = "accept";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public BranchingInstruction(int i, Role role) {
        if (role == null) {
            $$$reportNull$$$0(0);
        }
        this.offset = i;
        this.role = role;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.Instruction
    public void accept(ControlFlowInstructionVisitor controlFlowInstructionVisitor, int i, int i2) {
        if (controlFlowInstructionVisitor == null) {
            $$$reportNull$$$0(1);
        }
        controlFlowInstructionVisitor.visitBranchingInstruction(this, i, i2);
    }
}
