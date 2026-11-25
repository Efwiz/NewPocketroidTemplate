package org.eclipse.jdt.internal.compiler.ast;

import org.eclipse.jdt.internal.compiler.ASTVisitor;
import org.eclipse.jdt.internal.compiler.codegen.BranchLabel;
import org.eclipse.jdt.internal.compiler.codegen.CodeStream;
import org.eclipse.jdt.internal.compiler.flow.FlowContext;
import org.eclipse.jdt.internal.compiler.flow.FlowInfo;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.eclipse.jdt.internal.compiler.lookup.LocalVariableBinding;

/* JADX INFO: loaded from: classes14.dex */
public class Block extends Statement {
    public int explicitDeclarations;
    public BlockScope scope;
    public Statement[] statements;

    public Block(int i) {
        this.explicitDeclarations = i;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public FlowInfo analyseCode(BlockScope blockScope, FlowContext flowContext, FlowInfo flowInfo) {
        LocalVariableBinding[] localVariableBindingArr;
        if (this.statements == null) {
            return flowInfo;
        }
        int iComplainIfUnreachable = (flowInfo.reachMode() & 3) != 0 ? 1 : 0;
        boolean z = blockScope.compilerOptions().enableSyntacticNullAnalysisForFields;
        int length = this.statements.length;
        for (int i = 0; i < length; i++) {
            Statement statement = this.statements[i];
            iComplainIfUnreachable = statement.complainIfUnreachable(flowInfo, this.scope, iComplainIfUnreachable, true);
            if (iComplainIfUnreachable < 2) {
                flowInfo = statement.analyseCode(this.scope, flowContext, flowInfo);
            }
            flowContext.mergeFinallyNullInfo(flowInfo);
            if (z) {
                flowContext.expireNullCheckedFieldInfo();
            }
        }
        BlockScope blockScope2 = this.scope;
        if (blockScope2 != blockScope) {
            blockScope2.checkUnclosedCloseables(flowInfo, flowContext, null, null);
        }
        if (this.explicitDeclarations > 0 && (localVariableBindingArr = this.scope.locals) != null) {
            int i2 = this.scope.localIndex;
            for (int i3 = 0; i3 < i2; i3++) {
                flowInfo.resetAssignmentInfo(localVariableBindingArr[i3]);
            }
        }
        return flowInfo;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public void generateCode(BlockScope blockScope, CodeStream codeStream) {
        if ((this.bits & Integer.MIN_VALUE) == 0) {
            return;
        }
        int i = codeStream.position;
        Statement[] statementArr = this.statements;
        if (statementArr != null) {
            int length = statementArr.length;
            for (int i2 = 0; i2 < length; i2++) {
                this.statements[i2].generateCode(this.scope, codeStream);
            }
        }
        BlockScope blockScope2 = this.scope;
        if (blockScope2 != blockScope) {
            codeStream.exitUserScope(blockScope2);
        }
        codeStream.recordPositionsFrom(i, this.sourceStart);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public boolean isEmptyBlock() {
        return this.statements == null;
    }

    public StringBuffer printBody(int i, StringBuffer stringBuffer) {
        if (this.statements == null) {
            return stringBuffer;
        }
        int i2 = 0;
        while (true) {
            Statement[] statementArr = this.statements;
            if (i2 >= statementArr.length) {
                return stringBuffer;
            }
            statementArr[i2].printStatement(i + 1, stringBuffer);
            stringBuffer.append('\n');
            i2++;
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public StringBuffer printStatement(int i, StringBuffer stringBuffer) {
        printIndent(i, stringBuffer);
        stringBuffer.append("{\n");
        printBody(i, stringBuffer);
        return printIndent(i, stringBuffer).append('}');
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public void resolve(BlockScope blockScope) {
        if ((this.bits & 8) != 0) {
            blockScope.problemReporter().undocumentedEmptyBlock(this.sourceStart, this.sourceEnd);
        }
        if (this.statements != null) {
            if (this.explicitDeclarations != 0) {
                blockScope = new BlockScope(blockScope, this.explicitDeclarations);
            }
            this.scope = blockScope;
            int length = this.statements.length;
            for (int i = 0; i < length; i++) {
                this.statements[i].resolve(this.scope);
            }
        }
    }

    public void resolveUsing(BlockScope blockScope) {
        if ((this.bits & 8) != 0) {
            blockScope.problemReporter().undocumentedEmptyBlock(this.sourceStart, this.sourceEnd);
        }
        this.scope = blockScope;
        Statement[] statementArr = this.statements;
        if (statementArr != null) {
            int length = statementArr.length;
            for (int i = 0; i < length; i++) {
                this.statements[i].resolve(this.scope);
            }
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.ASTNode
    public void traverse(ASTVisitor aSTVisitor, BlockScope blockScope) {
        Statement[] statementArr;
        if (aSTVisitor.visit(this, blockScope) && (statementArr = this.statements) != null) {
            int length = statementArr.length;
            for (int i = 0; i < length; i++) {
                this.statements[i].traverse(aSTVisitor, this.scope);
            }
        }
        aSTVisitor.endVisit(this, blockScope);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public void branchChainTo(BranchLabel branchLabel) {
        Statement[] statementArr = this.statements;
        if (statementArr != null) {
            statementArr[statementArr.length - 1].branchChainTo(branchLabel);
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public boolean doesNotCompleteNormally() {
        Statement[] statementArr = this.statements;
        int length = statementArr == null ? 0 : statementArr.length;
        return length > 0 && statementArr[length - 1].doesNotCompleteNormally();
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public boolean completesByContinue() {
        Statement[] statementArr = this.statements;
        int length = statementArr == null ? 0 : statementArr.length;
        return length > 0 && statementArr[length - 1].completesByContinue();
    }
}
