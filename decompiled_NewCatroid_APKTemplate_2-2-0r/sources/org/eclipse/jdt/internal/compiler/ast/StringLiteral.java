package org.eclipse.jdt.internal.compiler.ast;

import org.eclipse.jdt.internal.compiler.ASTVisitor;
import org.eclipse.jdt.internal.compiler.codegen.CodeStream;
import org.eclipse.jdt.internal.compiler.impl.StringConstant;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;
import org.eclipse.jdt.internal.compiler.util.Util;

/* JADX INFO: loaded from: classes14.dex */
public class StringLiteral extends Literal {
    int lineNumber;
    char[] source;

    public StringLiteral(char[] cArr, int i, int i2, int i3) {
        this(i, i2);
        this.source = cArr;
        this.lineNumber = i3 - 1;
    }

    public StringLiteral(int i, int i2) {
        super(i, i2);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Literal
    public void computeConstant() {
        this.constant = StringConstant.fromValue(String.valueOf(this.source));
    }

    public ExtendedStringLiteral extendWith(CharLiteral charLiteral) {
        return new ExtendedStringLiteral(this, charLiteral);
    }

    public ExtendedStringLiteral extendWith(StringLiteral stringLiteral) {
        return new ExtendedStringLiteral(this, stringLiteral);
    }

    public StringLiteralConcatenation extendsWith(StringLiteral stringLiteral) {
        return new StringLiteralConcatenation(this, stringLiteral);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public void generateCode(BlockScope blockScope, CodeStream codeStream, boolean z) {
        int i = codeStream.position;
        if (z) {
            codeStream.ldc(this.constant.stringValue());
        }
        codeStream.recordPositionsFrom(i, this.sourceStart);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Literal
    public TypeBinding literalType(BlockScope blockScope) {
        return blockScope.getJavaLangString();
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Literal, org.eclipse.jdt.internal.compiler.ast.Expression
    public StringBuffer printExpression(int i, StringBuffer stringBuffer) {
        stringBuffer.append('\"');
        int i2 = 0;
        while (true) {
            char[] cArr = this.source;
            if (i2 < cArr.length) {
                Util.appendEscapedChar(stringBuffer, cArr[i2], true);
                i2++;
            } else {
                stringBuffer.append('\"');
                return stringBuffer;
            }
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Literal
    public char[] source() {
        return this.source;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.ASTNode
    public void traverse(ASTVisitor aSTVisitor, BlockScope blockScope) {
        aSTVisitor.visit(this, blockScope);
        aSTVisitor.endVisit(this, blockScope);
    }
}
