package org.eclipse.jdt.internal.compiler.ast;

import io.netty.util.internal.StringUtil;
import org.eclipse.jdt.internal.compiler.ASTVisitor;
import org.eclipse.jdt.internal.compiler.codegen.CodeStream;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;

/* JADX INFO: loaded from: classes14.dex */
public class PostfixExpression extends CompoundAssignment {
    @Override // org.eclipse.jdt.internal.compiler.ast.CompoundAssignment
    public boolean checkCastCompatibility() {
        return false;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.CompoundAssignment
    public boolean restrainUsageToNumericTypes() {
        return true;
    }

    public PostfixExpression(Expression expression, Expression expression2, int i, int i2) {
        super(expression, expression2, i, i2);
        this.sourceStart = expression.sourceStart;
        this.sourceEnd = i2;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.CompoundAssignment, org.eclipse.jdt.internal.compiler.ast.Assignment, org.eclipse.jdt.internal.compiler.ast.Expression
    public void generateCode(BlockScope blockScope, CodeStream codeStream, boolean z) {
        int i = codeStream.position;
        ((Reference) this.lhs).generatePostIncrement(blockScope, codeStream, this, z);
        if (z) {
            codeStream.generateImplicitConversion(this.implicitConversion);
        }
        codeStream.recordPositionsFrom(i, this.sourceStart);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.CompoundAssignment
    public String operatorToString() {
        int i = this.operator;
        if (i == 13) {
            return "--";
        }
        if (i == 14) {
            return "++";
        }
        return "unknown operator";
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.CompoundAssignment, org.eclipse.jdt.internal.compiler.ast.Assignment
    public StringBuffer printExpressionNoParenthesis(int i, StringBuffer stringBuffer) {
        return this.lhs.printExpression(i, stringBuffer).append(StringUtil.SPACE).append(operatorToString());
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.CompoundAssignment, org.eclipse.jdt.internal.compiler.ast.Assignment, org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.ASTNode
    public void traverse(ASTVisitor aSTVisitor, BlockScope blockScope) {
        if (aSTVisitor.visit(this, blockScope)) {
            this.lhs.traverse(aSTVisitor, blockScope);
        }
        aSTVisitor.endVisit(this, blockScope);
    }
}
