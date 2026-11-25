package org.eclipse.jdt.internal.compiler.ast;

import org.eclipse.jdt.internal.compiler.ASTVisitor;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;

/* JADX INFO: loaded from: classes14.dex */
public class StringLiteralConcatenation extends StringLiteral {
    private static final int INITIAL_SIZE = 5;
    public int counter;
    public Expression[] literals;

    public StringLiteralConcatenation(StringLiteral stringLiteral, StringLiteral stringLiteral2) {
        super(stringLiteral.sourceStart, stringLiteral.sourceEnd);
        this.source = stringLiteral.source;
        StringLiteral[] stringLiteralArr = new StringLiteral[5];
        this.literals = stringLiteralArr;
        this.counter = 1;
        stringLiteralArr[0] = stringLiteral;
        extendsWith(stringLiteral2);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.StringLiteral
    public StringLiteralConcatenation extendsWith(StringLiteral stringLiteral) {
        this.sourceEnd = stringLiteral.sourceEnd;
        Expression[] expressionArr = this.literals;
        int length = expressionArr.length;
        if (this.counter == length) {
            StringLiteral[] stringLiteralArr = new StringLiteral[length + 5];
            this.literals = stringLiteralArr;
            System.arraycopy(expressionArr, 0, stringLiteralArr, 0, length);
        }
        int length2 = this.source.length;
        char[] cArr = this.source;
        char[] cArr2 = new char[stringLiteral.source.length + length2];
        this.source = cArr2;
        System.arraycopy(cArr, 0, cArr2, 0, length2);
        System.arraycopy(stringLiteral.source, 0, this.source, length2, stringLiteral.source.length);
        Expression[] expressionArr2 = this.literals;
        int i = this.counter;
        this.counter = i + 1;
        expressionArr2[i] = stringLiteral;
        return this;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.StringLiteral, org.eclipse.jdt.internal.compiler.ast.Literal, org.eclipse.jdt.internal.compiler.ast.Expression
    public StringBuffer printExpression(int i, StringBuffer stringBuffer) {
        stringBuffer.append("StringLiteralConcatenation{");
        int i2 = this.counter;
        for (int i3 = 0; i3 < i2; i3++) {
            this.literals[i3].printExpression(i, stringBuffer);
            stringBuffer.append("+\n");
        }
        return stringBuffer.append('}');
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.StringLiteral, org.eclipse.jdt.internal.compiler.ast.Literal
    public char[] source() {
        return this.source;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.StringLiteral, org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.ASTNode
    public void traverse(ASTVisitor aSTVisitor, BlockScope blockScope) {
        if (aSTVisitor.visit(this, blockScope)) {
            int i = this.counter;
            for (int i2 = 0; i2 < i; i2++) {
                this.literals[i2].traverse(aSTVisitor, blockScope);
            }
        }
        aSTVisitor.endVisit(this, blockScope);
    }
}
