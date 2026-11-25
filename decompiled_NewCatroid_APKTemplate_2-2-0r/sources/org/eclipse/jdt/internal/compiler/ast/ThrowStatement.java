package org.eclipse.jdt.internal.compiler.ast;

import org.eclipse.jdt.internal.compiler.ASTVisitor;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants;
import org.eclipse.jdt.internal.compiler.codegen.CodeStream;
import org.eclipse.jdt.internal.compiler.flow.FlowContext;
import org.eclipse.jdt.internal.compiler.flow.FlowInfo;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;

/* JADX INFO: loaded from: classes14.dex */
public class ThrowStatement extends Statement {
    public Expression exception;
    public TypeBinding exceptionType;

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public boolean doesNotCompleteNormally() {
        return true;
    }

    public ThrowStatement(Expression expression, int i, int i2) {
        this.exception = expression;
        this.sourceStart = i;
        this.sourceEnd = i2;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public FlowInfo analyseCode(BlockScope blockScope, FlowContext flowContext, FlowInfo flowInfo) {
        this.exception.analyseCode(blockScope, flowContext, flowInfo);
        this.exception.checkNPE(blockScope, flowContext, flowInfo);
        flowContext.checkExceptionHandlers(this.exceptionType, this, flowInfo, blockScope);
        blockScope.checkUnclosedCloseables(flowInfo, flowContext, this, blockScope);
        flowContext.recordAbruptExit();
        return FlowInfo.DEAD_END;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public void generateCode(BlockScope blockScope, CodeStream codeStream) {
        if ((this.bits & Integer.MIN_VALUE) == 0) {
            return;
        }
        int i = codeStream.position;
        this.exception.generateCode(blockScope, codeStream, true);
        codeStream.athrow();
        codeStream.recordPositionsFrom(i, this.sourceStart);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public StringBuffer printStatement(int i, StringBuffer stringBuffer) {
        printIndent(i, stringBuffer).append("throw ");
        this.exception.printExpression(0, stringBuffer);
        return stringBuffer.append(';');
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public void resolve(BlockScope blockScope) {
        TypeBinding typeBindingResolveType = this.exception.resolveType(blockScope);
        this.exceptionType = typeBindingResolveType;
        if (typeBindingResolveType == null || !typeBindingResolveType.isValidBinding()) {
            return;
        }
        if (this.exceptionType == TypeBinding.NULL) {
            if (blockScope.compilerOptions().complianceLevel <= ClassFileConstants.JDK1_3) {
                blockScope.problemReporter().cannotThrowNull(this.exception);
            }
        } else if (this.exceptionType.findSuperTypeOriginatingFrom(21, true) == null) {
            blockScope.problemReporter().cannotThrowType(this.exception, this.exceptionType);
        }
        Expression expression = this.exception;
        TypeBinding typeBinding = this.exceptionType;
        expression.computeConversion(blockScope, typeBinding, typeBinding);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.ASTNode
    public void traverse(ASTVisitor aSTVisitor, BlockScope blockScope) {
        if (aSTVisitor.visit(this, blockScope)) {
            this.exception.traverse(aSTVisitor, blockScope);
        }
        aSTVisitor.endVisit(this, blockScope);
    }
}
