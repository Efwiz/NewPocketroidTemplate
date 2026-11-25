package org.eclipse.jdt.internal.compiler.ast;

import org.eclipse.jdt.internal.compiler.ASTVisitor;
import org.eclipse.jdt.internal.compiler.codegen.CodeStream;
import org.eclipse.jdt.internal.compiler.impl.CharConstant;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;
import org.eclipse.jdt.internal.compiler.parser.ScannerHelper;

/* JADX INFO: loaded from: classes14.dex */
public class CharLiteral extends NumberLiteral {
    char value;

    public CharLiteral(char[] cArr, int i, int i2) {
        super(cArr, i, i2);
        computeValue();
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Literal
    public void computeConstant() {
        this.constant = CharConstant.fromValue(this.value);
    }

    private void computeValue() {
        char c = this.source[1];
        this.value = c;
        if (c != '\\') {
            return;
        }
        char c2 = this.source[2];
        if (c2 == '\"') {
            this.value = '\"';
            return;
        }
        if (c2 == '\'') {
            this.value = '\'';
            return;
        }
        if (c2 == '\\') {
            this.value = '\\';
            return;
        }
        if (c2 == 'b') {
            this.value = '\b';
            return;
        }
        if (c2 == 'f') {
            this.value = '\f';
            return;
        }
        if (c2 == 'n') {
            this.value = '\n';
            return;
        }
        if (c2 == 'r') {
            this.value = '\r';
            return;
        }
        if (c2 == 't') {
            this.value = '\t';
            return;
        }
        int numericValue = ScannerHelper.getNumericValue(c2);
        char c3 = this.source[3];
        if (c3 != '\'') {
            int numericValue2 = (numericValue * 8) + ScannerHelper.getNumericValue(c3);
            char c4 = this.source[4];
            if (c4 != '\'') {
                numericValue2 = (numericValue2 * 8) + ScannerHelper.getNumericValue(c4);
            }
            this.value = (char) numericValue2;
            return;
        }
        char c5 = (char) numericValue;
        this.value = c5;
        this.constant = CharConstant.fromValue(c5);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public void generateCode(BlockScope blockScope, CodeStream codeStream, boolean z) {
        int i = codeStream.position;
        if (z) {
            codeStream.generateConstant(this.constant, this.implicitConversion);
        }
        codeStream.recordPositionsFrom(i, this.sourceStart);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Literal
    public TypeBinding literalType(BlockScope blockScope) {
        return TypeBinding.CHAR;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.ASTNode
    public void traverse(ASTVisitor aSTVisitor, BlockScope blockScope) {
        aSTVisitor.visit(this, blockScope);
        aSTVisitor.endVisit(this, blockScope);
    }
}
