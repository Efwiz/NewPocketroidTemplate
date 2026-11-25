package org.jetbrains.kotlin.com.intellij.psi.controlFlow;

import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;

/* JADX INFO: loaded from: classes3.dex */
public class GoToInstruction extends BranchingInstruction {
    private static final Logger LOG = Logger.getInstance((Class<?>) GoToInstruction.class);
    public final boolean isReturn;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 2) {
            objArr[0] = "role";
        } else {
            objArr[0] = "visitor";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/controlFlow/GoToInstruction";
        if (i != 2) {
            objArr[2] = "<init>";
        } else {
            objArr[2] = "accept";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.Instruction
    public int nNext() {
        return 1;
    }

    GoToInstruction(int i) {
        this(i, BranchingInstruction.Role.END);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    GoToInstruction(int i, BranchingInstruction.Role role) {
        this(i, role, false);
        if (role == null) {
            $$$reportNull$$$0(0);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    GoToInstruction(int i, BranchingInstruction.Role role, boolean z) {
        super(i, role);
        if (role == null) {
            $$$reportNull$$$0(1);
        }
        this.isReturn = z;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.InstructionBase
    public String toString() {
        return "GOTO " + ("[" + this.role.toString() + "]") + " " + this.offset + (this.isReturn ? " RETURN" : "");
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.Instruction
    public int getNext(int i, int i2) {
        LOG.assertTrue(i2 == 0);
        return this.offset;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.BranchingInstruction, org.jetbrains.kotlin.com.intellij.psi.controlFlow.Instruction
    public void accept(ControlFlowInstructionVisitor controlFlowInstructionVisitor, int i, int i2) {
        if (controlFlowInstructionVisitor == null) {
            $$$reportNull$$$0(2);
        }
        controlFlowInstructionVisitor.visitGoToInstruction(this, i, i2);
    }
}
