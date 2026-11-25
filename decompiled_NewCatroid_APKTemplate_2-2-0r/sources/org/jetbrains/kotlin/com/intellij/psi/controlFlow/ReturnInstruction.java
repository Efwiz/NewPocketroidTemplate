package org.jetbrains.kotlin.com.intellij.psi.controlFlow;

import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;

/* JADX INFO: loaded from: classes3.dex */
public class ReturnInstruction extends GoToInstruction {
    private static final Logger LOG = Logger.getInstance((Class<?>) ReturnInstruction.class);
    private CallInstruction myCallInstruction;
    private boolean myRethrowFromFinally;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 1 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 1 ? 3 : 2];
        if (i == 1) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/controlFlow/ReturnInstruction";
        } else if (i != 3) {
            objArr[0] = "callInstruction";
        } else {
            objArr[0] = "visitor";
        }
        if (i != 1) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/controlFlow/ReturnInstruction";
        } else {
            objArr[1] = "getPossibleReturnOffsets";
        }
        if (i != 1) {
            if (i == 2) {
                objArr[2] = "setCallInstruction";
            } else if (i != 3) {
                objArr[2] = "<init>";
            } else {
                objArr[2] = "accept";
            }
        }
        String str2 = String.format(str, objArr);
        if (i == 1) {
            throw new IllegalStateException(str2);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ReturnInstruction(int i, CallInstruction callInstruction) {
        super(i, BranchingInstruction.Role.END, false);
        if (callInstruction == null) {
            $$$reportNull$$$0(0);
        }
        this.myCallInstruction = callInstruction;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.GoToInstruction, org.jetbrains.kotlin.com.intellij.psi.controlFlow.InstructionBase
    public String toString() {
        return "RETURN FROM " + getProcBegin() + (this.offset == 0 ? "" : " TO " + this.offset);
    }

    int[] getPossibleReturnOffsets() {
        if (this.offset == 0) {
            return new int[]{getProcBegin() - 5, getProcBegin() - 3, getProcBegin() - 1};
        }
        return new int[]{this.offset};
    }

    int getProcBegin() {
        return this.myCallInstruction.procBegin;
    }

    int getProcEnd() {
        return this.myCallInstruction.procEnd;
    }

    void setCallInstruction(CallInstruction callInstruction) {
        if (callInstruction == null) {
            $$$reportNull$$$0(2);
        }
        this.myCallInstruction = callInstruction;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.GoToInstruction, org.jetbrains.kotlin.com.intellij.psi.controlFlow.Instruction
    public int nNext() {
        return this.offset == 0 ? 3 : 1;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.GoToInstruction, org.jetbrains.kotlin.com.intellij.psi.controlFlow.Instruction
    public int getNext(int i, int i2) {
        if (this.offset != 0) {
            if (i2 == 0) {
                return this.offset;
            }
            LOG.assertTrue(false);
            return -1;
        }
        if (i2 == 0) {
            return getProcBegin() - 5;
        }
        if (i2 == 1) {
            return getProcBegin() - 3;
        }
        if (i2 == 2) {
            return getProcBegin() - 1;
        }
        LOG.assertTrue(false);
        return -1;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.GoToInstruction, org.jetbrains.kotlin.com.intellij.psi.controlFlow.BranchingInstruction, org.jetbrains.kotlin.com.intellij.psi.controlFlow.Instruction
    public void accept(ControlFlowInstructionVisitor controlFlowInstructionVisitor, int i, int i2) {
        if (controlFlowInstructionVisitor == null) {
            $$$reportNull$$$0(3);
        }
        controlFlowInstructionVisitor.visitReturnInstruction(this, i, i2);
    }

    void setRethrowFromFinally() {
        this.myRethrowFromFinally = true;
    }

    boolean isRethrowFromFinally() {
        return this.myRethrowFromFinally;
    }
}
