package org.eclipse.jdt.internal.compiler.ast;

import org.eclipse.jdt.internal.compiler.ASTVisitor;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;

/* JADX INFO: loaded from: classes14.dex */
public class ExtendedStringLiteral extends StringLiteral {
    public ExtendedStringLiteral(StringLiteral stringLiteral, CharLiteral charLiteral) {
        super(stringLiteral.source, stringLiteral.sourceStart, stringLiteral.sourceEnd, stringLiteral.lineNumber);
        extendWith(charLiteral);
    }

    public ExtendedStringLiteral(StringLiteral stringLiteral, StringLiteral stringLiteral2) {
        super(stringLiteral.source, stringLiteral.sourceStart, stringLiteral.sourceEnd, stringLiteral.lineNumber);
        extendWith(stringLiteral2);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.StringLiteral
    public ExtendedStringLiteral extendWith(CharLiteral charLiteral) {
        int length = this.source.length;
        char[] cArr = this.source;
        char[] cArr2 = new char[length + 1];
        this.source = cArr2;
        System.arraycopy(cArr, 0, cArr2, 0, length);
        this.source[length] = charLiteral.value;
        this.sourceEnd = charLiteral.sourceEnd;
        return this;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.StringLiteral
    public ExtendedStringLiteral extendWith(StringLiteral stringLiteral) {
        int length = this.source.length;
        char[] cArr = this.source;
        char[] cArr2 = new char[stringLiteral.source.length + length];
        this.source = cArr2;
        System.arraycopy(cArr, 0, cArr2, 0, length);
        System.arraycopy(stringLiteral.source, 0, this.source, length, stringLiteral.source.length);
        this.sourceEnd = stringLiteral.sourceEnd;
        return this;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.StringLiteral, org.eclipse.jdt.internal.compiler.ast.Literal, org.eclipse.jdt.internal.compiler.ast.Expression
    public StringBuffer printExpression(int i, StringBuffer stringBuffer) {
        return stringBuffer.append("ExtendedStringLiteral{").append(this.source).append('}');
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.StringLiteral, org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.ASTNode
    public void traverse(ASTVisitor aSTVisitor, BlockScope blockScope) {
        aSTVisitor.visit(this, blockScope);
        aSTVisitor.endVisit(this, blockScope);
    }
}
