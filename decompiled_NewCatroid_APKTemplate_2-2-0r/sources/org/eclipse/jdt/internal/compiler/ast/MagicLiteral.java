package org.eclipse.jdt.internal.compiler.ast;

/* JADX INFO: loaded from: classes14.dex */
public abstract class MagicLiteral extends Literal {
    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public boolean isValidJavaStatement() {
        return false;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Literal
    public char[] source() {
        return null;
    }

    public MagicLiteral(int i, int i2) {
        super(i, i2);
    }
}
