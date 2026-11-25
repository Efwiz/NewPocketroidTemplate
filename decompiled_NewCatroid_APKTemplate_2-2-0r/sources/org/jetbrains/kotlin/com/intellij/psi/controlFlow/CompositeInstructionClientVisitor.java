package org.jetbrains.kotlin.com.intellij.psi.controlFlow;

/* JADX INFO: loaded from: classes3.dex */
class CompositeInstructionClientVisitor extends InstructionClientVisitor<Object[]> {
    private final InstructionClientVisitor[] myVisitors;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/com/intellij/psi/controlFlow/CompositeInstructionClientVisitor", "getResult"));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.InstructionClientVisitor
    public Object[] getResult() {
        Object[] objArr = new Object[this.myVisitors.length];
        int i = 0;
        while (true) {
            InstructionClientVisitor[] instructionClientVisitorArr = this.myVisitors;
            if (i >= instructionClientVisitorArr.length) {
                return objArr;
            }
            objArr[i] = instructionClientVisitorArr[i].getResult();
            i++;
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowInstructionVisitor
    public void visitInstruction(Instruction instruction, int i, int i2) {
        for (InstructionClientVisitor instructionClientVisitor : this.myVisitors) {
            instructionClientVisitor.visitInstruction(instruction, i, i2);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowInstructionVisitor
    public void visitEmptyInstruction(EmptyInstruction emptyInstruction, int i, int i2) {
        for (InstructionClientVisitor instructionClientVisitor : this.myVisitors) {
            instructionClientVisitor.visitEmptyInstruction(emptyInstruction, i, i2);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowInstructionVisitor
    public void visitCommentInstruction(CommentInstruction commentInstruction, int i, int i2) {
        for (InstructionClientVisitor instructionClientVisitor : this.myVisitors) {
            instructionClientVisitor.visitCommentInstruction(commentInstruction, i, i2);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowInstructionVisitor
    public void visitReadVariableInstruction(ReadVariableInstruction readVariableInstruction, int i, int i2) {
        for (InstructionClientVisitor instructionClientVisitor : this.myVisitors) {
            instructionClientVisitor.visitReadVariableInstruction(readVariableInstruction, i, i2);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowInstructionVisitor
    public void visitWriteVariableInstruction(WriteVariableInstruction writeVariableInstruction, int i, int i2) {
        for (InstructionClientVisitor instructionClientVisitor : this.myVisitors) {
            instructionClientVisitor.visitWriteVariableInstruction(writeVariableInstruction, i, i2);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowInstructionVisitor
    public void visitSimpleInstruction(SimpleInstruction simpleInstruction, int i, int i2) {
        for (InstructionClientVisitor instructionClientVisitor : this.myVisitors) {
            instructionClientVisitor.visitSimpleInstruction(simpleInstruction, i, i2);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowInstructionVisitor
    public void visitBranchingInstruction(BranchingInstruction branchingInstruction, int i, int i2) {
        for (InstructionClientVisitor instructionClientVisitor : this.myVisitors) {
            instructionClientVisitor.visitBranchingInstruction(branchingInstruction, i, i2);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowInstructionVisitor
    public void visitConditionalBranchingInstruction(ConditionalBranchingInstruction conditionalBranchingInstruction, int i, int i2) {
        for (InstructionClientVisitor instructionClientVisitor : this.myVisitors) {
            instructionClientVisitor.visitConditionalBranchingInstruction(conditionalBranchingInstruction, i, i2);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowInstructionVisitor
    public void visitConditionalGoToInstruction(ConditionalGoToInstruction conditionalGoToInstruction, int i, int i2) {
        for (InstructionClientVisitor instructionClientVisitor : this.myVisitors) {
            instructionClientVisitor.visitConditionalGoToInstruction(conditionalGoToInstruction, i, i2);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowInstructionVisitor
    public void visitConditionalThrowToInstruction(ConditionalThrowToInstruction conditionalThrowToInstruction, int i, int i2) {
        for (InstructionClientVisitor instructionClientVisitor : this.myVisitors) {
            instructionClientVisitor.visitConditionalThrowToInstruction(conditionalThrowToInstruction, i, i2);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowInstructionVisitor
    public void visitThrowToInstruction(ThrowToInstruction throwToInstruction, int i, int i2) {
        for (InstructionClientVisitor instructionClientVisitor : this.myVisitors) {
            instructionClientVisitor.visitThrowToInstruction(throwToInstruction, i, i2);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowInstructionVisitor
    public void visitGoToInstruction(GoToInstruction goToInstruction, int i, int i2) {
        for (InstructionClientVisitor instructionClientVisitor : this.myVisitors) {
            instructionClientVisitor.visitGoToInstruction(goToInstruction, i, i2);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowInstructionVisitor
    public void visitCallInstruction(CallInstruction callInstruction, int i, int i2) {
        for (InstructionClientVisitor instructionClientVisitor : this.myVisitors) {
            instructionClientVisitor.visitCallInstruction(callInstruction, i, i2);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowInstructionVisitor
    public void visitReturnInstruction(ReturnInstruction returnInstruction, int i, int i2) {
        for (InstructionClientVisitor instructionClientVisitor : this.myVisitors) {
            instructionClientVisitor.visitReturnInstruction(returnInstruction, i, i2);
        }
    }
}
