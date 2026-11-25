package org.eclipse.jdt.internal.compiler.ast;

import org.eclipse.jdt.internal.compiler.ASTVisitor;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants;
import org.eclipse.jdt.internal.compiler.codegen.CodeStream;
import org.eclipse.jdt.internal.compiler.flow.FlowContext;
import org.eclipse.jdt.internal.compiler.flow.FlowInfo;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;

/* JADX INFO: loaded from: classes14.dex */
public class EmptyStatement extends Statement {
    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public FlowInfo analyseCode(BlockScope blockScope, FlowContext flowContext, FlowInfo flowInfo) {
        return flowInfo;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public void generateCode(BlockScope blockScope, CodeStream codeStream) {
    }

    public EmptyStatement(int i, int i2) {
        this.sourceStart = i;
        this.sourceEnd = i2;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public int complainIfUnreachable(FlowInfo flowInfo, BlockScope blockScope, int i, boolean z) {
        return blockScope.compilerOptions().complianceLevel < ClassFileConstants.JDK1_4 ? i : super.complainIfUnreachable(flowInfo, blockScope, i, z);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public StringBuffer printStatement(int i, StringBuffer stringBuffer) {
        return printIndent(i, stringBuffer).append(';');
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public void resolve(BlockScope blockScope) {
        if ((this.bits & 1) == 0) {
            blockScope.problemReporter().superfluousSemicolon(this.sourceStart, this.sourceEnd);
        } else {
            blockScope.problemReporter().emptyControlFlowStatement(this.sourceStart, this.sourceEnd);
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.ASTNode
    public void traverse(ASTVisitor aSTVisitor, BlockScope blockScope) {
        aSTVisitor.visit(this, blockScope);
        aSTVisitor.endVisit(this, blockScope);
    }
}
