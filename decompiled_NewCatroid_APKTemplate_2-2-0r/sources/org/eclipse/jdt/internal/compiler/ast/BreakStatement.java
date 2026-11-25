package org.eclipse.jdt.internal.compiler.ast;

import io.netty.util.internal.StringUtil;
import org.eclipse.jdt.internal.compiler.ASTVisitor;
import org.eclipse.jdt.internal.compiler.flow.FlowContext;
import org.eclipse.jdt.internal.compiler.flow.FlowInfo;
import org.eclipse.jdt.internal.compiler.flow.InsideSubRoutineFlowContext;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;

/* JADX INFO: loaded from: classes14.dex */
public class BreakStatement extends BranchStatement {
    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public boolean doesNotCompleteNormally() {
        return true;
    }

    public BreakStatement(char[] cArr, int i, int i2) {
        super(cArr, i, i2);
    }

    /* JADX WARN: Code duplicated, block: B:24:0x0074  */
    /* JADX WARN: Code duplicated, block: B:26:0x007a  */
    /* JADX WARN: Code duplicated, block: B:27:0x0082 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:38:0x0084 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:39:0x008e A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:40:? A[LOOP:0: B:13:0x0042->B:40:?, LOOP_END, SYNTHETIC] */
    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public FlowInfo analyseCode(BlockScope blockScope, FlowContext flowContext, FlowInfo flowInfo) {
        FlowContext targetContextForBreakLabel;
        int i;
        ASTNode aSTNode;
        if (this.label == null) {
            targetContextForBreakLabel = flowContext.getTargetContextForDefaultBreak();
        } else {
            targetContextForBreakLabel = flowContext.getTargetContextForBreakLabel(this.label);
        }
        if (targetContextForBreakLabel == null) {
            if (this.label == null) {
                blockScope.problemReporter().invalidBreak(this);
            } else {
                blockScope.problemReporter().undefinedLabel(this);
            }
            return flowInfo;
        }
        targetContextForBreakLabel.recordAbruptExit();
        targetContextForBreakLabel.expireNullCheckedFieldInfo();
        this.initStateIndex = blockScope.methodScope().recordInitializationStates(flowInfo);
        this.targetLabel = targetContextForBreakLabel.breakLabel();
        this.subroutines = new SubRoutineStatement[5];
        int i2 = 0;
        while (true) {
            SubRoutineStatement subRoutineStatementSubroutine = flowContext.subroutine();
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
                flowContext.recordReturnFrom(flowInfo.unconditionalInits());
                flowContext.recordBreakTo(targetContextForBreakLabel);
                if (flowContext instanceof InsideSubRoutineFlowContext) {
                    if (flowContext == targetContextForBreakLabel) {
                        targetContextForBreakLabel.recordBreakFrom(flowInfo);
                    }
                    i = i2;
                    break;
                }
                aSTNode = flowContext.associatedNode;
                if (aSTNode instanceof TryStatement) {
                    flowInfo.addInitializationsFrom(((TryStatement) aSTNode).subRoutineInits);
                }
                flowContext = flowContext.getLocalParent();
                if (flowContext == null) {
                    i = i2;
                    break;
                }
            } else {
                flowContext.recordReturnFrom(flowInfo.unconditionalInits());
                flowContext.recordBreakTo(targetContextForBreakLabel);
                if (flowContext instanceof InsideSubRoutineFlowContext) {
                    if (flowContext == targetContextForBreakLabel) {
                        targetContextForBreakLabel.recordBreakFrom(flowInfo);
                    }
                    i = i2;
                    break;
                }
                aSTNode = flowContext.associatedNode;
                if (aSTNode instanceof TryStatement) {
                    flowInfo.addInitializationsFrom(((TryStatement) aSTNode).subRoutineInits);
                }
                flowContext = flowContext.getLocalParent();
                if (flowContext == null) {
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
        printIndent(i, stringBuffer).append(PsiKeyword.BREAK);
        if (this.label != null) {
            stringBuffer.append(StringUtil.SPACE).append(this.label);
        }
        return stringBuffer.append(';');
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.ASTNode
    public void traverse(ASTVisitor aSTVisitor, BlockScope blockScope) {
        aSTVisitor.visit(this, blockScope);
        aSTVisitor.endVisit(this, blockScope);
    }
}
