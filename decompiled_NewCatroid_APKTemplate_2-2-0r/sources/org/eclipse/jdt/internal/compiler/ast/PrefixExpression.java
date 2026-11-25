package org.eclipse.jdt.internal.compiler.ast;

import io.netty.util.internal.StringUtil;
import org.eclipse.jdt.internal.compiler.ASTVisitor;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;

/* JADX INFO: loaded from: classes14.dex */
public class PrefixExpression extends CompoundAssignment {
    @Override // org.eclipse.jdt.internal.compiler.ast.CompoundAssignment
    public boolean checkCastCompatibility() {
        return false;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.CompoundAssignment
    public boolean restrainUsageToNumericTypes() {
        return true;
    }

    public PrefixExpression(Expression expression, Expression expression2, int i, int i2) {
        super(expression, expression2, i, expression.sourceEnd);
        this.sourceStart = i2;
        this.sourceEnd = expression.sourceEnd;
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
        stringBuffer.append(operatorToString()).append(StringUtil.SPACE);
        return this.lhs.printExpression(0, stringBuffer);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.CompoundAssignment, org.eclipse.jdt.internal.compiler.ast.Assignment, org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.ASTNode
    public void traverse(ASTVisitor aSTVisitor, BlockScope blockScope) {
        if (aSTVisitor.visit(this, blockScope)) {
            this.lhs.traverse(aSTVisitor, blockScope);
        }
        aSTVisitor.endVisit(this, blockScope);
    }
}
