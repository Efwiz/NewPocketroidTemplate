package org.eclipse.jdt.internal.compiler.ast;

import org.eclipse.jdt.internal.compiler.ASTVisitor;
import org.eclipse.jdt.internal.compiler.codegen.BranchLabel;
import org.eclipse.jdt.internal.compiler.codegen.CodeStream;
import org.eclipse.jdt.internal.compiler.flow.FlowContext;
import org.eclipse.jdt.internal.compiler.flow.FlowInfo;
import org.eclipse.jdt.internal.compiler.flow.LabelFlowContext;
import org.eclipse.jdt.internal.compiler.flow.UnconditionalFlowInfo;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;

/* JADX INFO: loaded from: classes14.dex */
public class LabeledStatement extends Statement {
    public char[] label;
    public int labelEnd;
    int mergedInitStateIndex = -1;
    public Statement statement;
    public BranchLabel targetLabel;

    public LabeledStatement(char[] cArr, Statement statement, long j, int i) {
        this.statement = statement;
        if (statement instanceof EmptyStatement) {
            statement.bits |= 1;
        }
        this.label = cArr;
        this.sourceStart = (int) (j >>> 32);
        this.labelEnd = (int) j;
        this.sourceEnd = i;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public FlowInfo analyseCode(BlockScope blockScope, FlowContext flowContext, FlowInfo flowInfo) {
        Statement statement = this.statement;
        if (statement == null) {
            return flowInfo;
        }
        char[] cArr = this.label;
        BranchLabel branchLabel = new BranchLabel();
        this.targetLabel = branchLabel;
        LabelFlowContext labelFlowContext = new LabelFlowContext(flowContext, this, cArr, branchLabel, blockScope);
        FlowInfo flowInfoAnalyseCode = statement.analyseCode(blockScope, labelFlowContext, flowInfo);
        boolean z = (flowInfoAnalyseCode.tagBits & 3) != 0 && (labelFlowContext.initsOnBreak.tagBits & 3) == 0;
        UnconditionalFlowInfo unconditionalFlowInfoMergedWith = flowInfoAnalyseCode.mergedWith(labelFlowContext.initsOnBreak);
        if (z) {
            unconditionalFlowInfoMergedWith.addInitializationsFrom(flowInfo.unconditionalFieldLessCopy()).addInitializationsFrom(labelFlowContext.initsOnBreak.unconditionalFieldLessCopy());
        }
        this.mergedInitStateIndex = blockScope.methodScope().recordInitializationStates(unconditionalFlowInfoMergedWith);
        if ((this.bits & 64) == 0) {
            blockScope.problemReporter().unusedLabel(this);
        }
        return unconditionalFlowInfoMergedWith;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.ASTNode
    public ASTNode concreteStatement() {
        return this.statement;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public void generateCode(BlockScope blockScope, CodeStream codeStream) {
        if ((this.bits & Integer.MIN_VALUE) == 0) {
            return;
        }
        int i = codeStream.position;
        BranchLabel branchLabel = this.targetLabel;
        if (branchLabel != null) {
            branchLabel.initialize(codeStream);
            Statement statement = this.statement;
            if (statement != null) {
                statement.generateCode(blockScope, codeStream);
            }
            this.targetLabel.place();
        }
        int i2 = this.mergedInitStateIndex;
        if (i2 != -1) {
            codeStream.removeNotDefinitelyAssignedVariables(blockScope, i2);
            codeStream.addDefinitelyAssignedVariables(blockScope, this.mergedInitStateIndex);
        }
        codeStream.recordPositionsFrom(i, this.sourceStart);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public StringBuffer printStatement(int i, StringBuffer stringBuffer) {
        printIndent(i, stringBuffer).append(this.label).append(": ");
        Statement statement = this.statement;
        if (statement == null) {
            stringBuffer.append(';');
        } else {
            statement.printStatement(0, stringBuffer);
        }
        return stringBuffer;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public void resolve(BlockScope blockScope) {
        Statement statement = this.statement;
        if (statement != null) {
            statement.resolve(blockScope);
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.ASTNode
    public void traverse(ASTVisitor aSTVisitor, BlockScope blockScope) {
        Statement statement;
        if (aSTVisitor.visit(this, blockScope) && (statement = this.statement) != null) {
            statement.traverse(aSTVisitor, blockScope);
        }
        aSTVisitor.endVisit(this, blockScope);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public boolean doesNotCompleteNormally() {
        if (this.statement.breaksOut(this.label)) {
            return false;
        }
        return this.statement.doesNotCompleteNormally();
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public boolean completesByContinue() {
        return this.statement instanceof ContinueStatement;
    }
}
