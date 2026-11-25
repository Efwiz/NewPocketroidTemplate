package org.jetbrains.kotlin.com.intellij.psi.controlFlow;

/* JADX INFO: loaded from: classes3.dex */
public class ControlFlowInstructionVisitor {
    public void visitInstruction(Instruction instruction, int i, int i2) {
    }

    public void visitEmptyInstruction(EmptyInstruction emptyInstruction, int i, int i2) {
        visitSimpleInstruction(emptyInstruction, i, i2);
    }

    public void visitCommentInstruction(CommentInstruction commentInstruction, int i, int i2) {
        visitSimpleInstruction(commentInstruction, i, i2);
    }

    public void visitReadVariableInstruction(ReadVariableInstruction readVariableInstruction, int i, int i2) {
        visitSimpleInstruction(readVariableInstruction, i, i2);
    }

    public void visitWriteVariableInstruction(WriteVariableInstruction writeVariableInstruction, int i, int i2) {
        visitSimpleInstruction(writeVariableInstruction, i, i2);
    }

    public void visitSimpleInstruction(SimpleInstruction simpleInstruction, int i, int i2) {
        visitInstruction(simpleInstruction, i, i2);
    }

    public void visitBranchingInstruction(BranchingInstruction branchingInstruction, int i, int i2) {
        visitInstruction(branchingInstruction, i, i2);
    }

    public void visitConditionalBranchingInstruction(ConditionalBranchingInstruction conditionalBranchingInstruction, int i, int i2) {
        visitBranchingInstruction(conditionalBranchingInstruction, i, i2);
    }

    public void visitConditionalGoToInstruction(ConditionalGoToInstruction conditionalGoToInstruction, int i, int i2) {
        visitConditionalBranchingInstruction(conditionalGoToInstruction, i, i2);
    }

    public void visitConditionalThrowToInstruction(ConditionalThrowToInstruction conditionalThrowToInstruction, int i, int i2) {
        visitConditionalBranchingInstruction(conditionalThrowToInstruction, i, i2);
    }

    public void visitThrowToInstruction(ThrowToInstruction throwToInstruction, int i, int i2) {
        visitBranchingInstruction(throwToInstruction, i, i2);
    }

    public void visitGoToInstruction(GoToInstruction goToInstruction, int i, int i2) {
        visitBranchingInstruction(goToInstruction, i, i2);
    }

    public void visitCallInstruction(CallInstruction callInstruction, int i, int i2) {
        visitGoToInstruction(callInstruction, i, i2);
    }

    public void visitReturnInstruction(ReturnInstruction returnInstruction, int i, int i2) {
        visitGoToInstruction(returnInstruction, i, i2);
    }
}
