package org.eclipse.jdt.internal.compiler.ast;

import org.eclipse.jdt.internal.compiler.ASTVisitor;
import org.eclipse.jdt.internal.compiler.flow.FlowContext;
import org.eclipse.jdt.internal.compiler.flow.FlowInfo;
import org.eclipse.jdt.internal.compiler.flow.InsideSubRoutineFlowContext;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;

/* JADX INFO: loaded from: classes14.dex */
public class ContinueStatement extends BranchStatement {
    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public boolean completesByContinue() {
        return true;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public boolean doesNotCompleteNormally() {
        return true;
    }

    public ContinueStatement(char[] cArr, int i, int i2) {
        super(cArr, i, i2);
    }

    /* JADX WARN: Code duplicated, block: B:28:0x007e  */
    /* JADX WARN: Code duplicated, block: B:30:0x0084  */
    /* JADX WARN: Code duplicated, block: B:31:0x008c A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:41:0x0098 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:43:0x008e A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:44:? A[LOOP:0: B:17:0x004f->B:44:?, LOOP_END, SYNTHETIC] */
    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public FlowInfo analyseCode(BlockScope blockScope, FlowContext flowContext, FlowInfo flowInfo) {
        FlowContext targetContextForContinueLabel;
        int i;
        ASTNode aSTNode;
        if (this.label == null) {
            targetContextForContinueLabel = flowContext.getTargetContextForDefaultContinue();
        } else {
            targetContextForContinueLabel = flowContext.getTargetContextForContinueLabel(this.label);
        }
        if (targetContextForContinueLabel == null) {
            if (this.label == null) {
                blockScope.problemReporter().invalidContinue(this);
            } else {
                blockScope.problemReporter().undefinedLabel(this);
            }
            return flowInfo;
        }
        targetContextForContinueLabel.recordAbruptExit();
        targetContextForContinueLabel.expireNullCheckedFieldInfo();
        if (targetContextForContinueLabel == FlowContext.NotContinuableContext) {
            blockScope.problemReporter().invalidContinue(this);
            return flowInfo;
        }
        this.initStateIndex = blockScope.methodScope().recordInitializationStates(flowInfo);
        this.targetLabel = targetContextForContinueLabel.continueLabel();
        this.subroutines = new SubRoutineStatement[5];
        int i2 = 0;
        FlowContext localParent = flowContext;
        while (true) {
            SubRoutineStatement subRoutineStatementSubroutine = localParent.subroutine();
            if (subRoutineStatementSubroutine != null) {
                if (i2 == this.subroutines.length) {
                    SubRoutineStatement[] subRoutineStatementArr = this.subroutines;
                    SubRoutineStatement[] subRoutineStatementArr2 = new SubRoutineStatement[i2 * 2];
                    this.subroutines = subRoutineStatementArr2;
                    System.arraycopy(subRoutineStatementArr, 0, subRoutineStatementArr2, 0, i2);
                }
                i = i2 + 1;
                this.subroutines[i2] = subRoutineStatementSubroutine;
                if (subRoutineStatementSubroutine.isSubRoutineEscaping()) {
                    break;
                }
                i2 = i;
                localParent.recordReturnFrom(flowInfo.unconditionalInits());
                if (localParent instanceof InsideSubRoutineFlowContext) {
                    if (localParent == targetContextForContinueLabel) {
                        targetContextForContinueLabel.recordContinueFrom(flowContext, flowInfo);
                    }
                    i = i2;
                    break;
                }
                aSTNode = localParent.associatedNode;
                if (aSTNode instanceof TryStatement) {
                    flowInfo.addInitializationsFrom(((TryStatement) aSTNode).subRoutineInits);
                }
                localParent = localParent.getLocalParent();
                if (localParent == null) {
                    i = i2;
                    break;
                }
            } else {
                localParent.recordReturnFrom(flowInfo.unconditionalInits());
                if (localParent instanceof InsideSubRoutineFlowContext) {
                    if (localParent == targetContextForContinueLabel) {
                        targetContextForContinueLabel.recordContinueFrom(flowContext, flowInfo);
                    }
                    i = i2;
                    break;
                }
                aSTNode = localParent.associatedNode;
                if (aSTNode instanceof TryStatement) {
                    flowInfo.addInitializationsFrom(((TryStatement) aSTNode).subRoutineInits);
                }
                localParent = localParent.getLocalParent();
                if (localParent == null) {
                    i = i2;
                    break;
                }
            }
        }
        if (i != this.subroutines.length) {
            SubRoutineStatement[] subRoutineStatementArr3 = this.subroutines;
            SubRoutineStatement[] subRoutineStatementArr4 = new SubRoutineStatement[i];
            this.subroutines = subRoutineStatementArr4;
            System.arraycopy(subRoutineStatementArr3, 0, subRoutineStatementArr4, 0, i);
        }
        return FlowInfo.DEAD_END;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public StringBuffer printStatement(int i, StringBuffer stringBuffer) {
        printIndent(i, stringBuffer).append("continue ");
        if (this.label != null) {
            stringBuffer.append(this.label);
        }
        return stringBuffer.append(';');
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.ASTNode
    public void traverse(ASTVisitor aSTVisitor, BlockScope blockScope) {
        aSTVisitor.visit(this, blockScope);
        aSTVisitor.endVisit(this, blockScope);
    }
}
