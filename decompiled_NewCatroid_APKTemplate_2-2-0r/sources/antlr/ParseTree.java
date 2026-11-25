package antlr;

import antlr.collections.AST;

/* JADX INFO: loaded from: classes.dex */
public abstract class ParseTree extends BaseAST {
    protected abstract int getLeftmostDerivation(StringBuffer stringBuffer, int i);

    @Override // antlr.BaseAST, antlr.collections.AST
    public void initialize(int i, String str) {
    }

    @Override // antlr.BaseAST, antlr.collections.AST
    public void initialize(Token token) {
    }

    @Override // antlr.BaseAST, antlr.collections.AST
    public void initialize(AST ast) {
    }

    public String getLeftmostDerivationStep(int i) {
        if (i <= 0) {
            return toString();
        }
        StringBuffer stringBuffer = new StringBuffer(2000);
        getLeftmostDerivation(stringBuffer, i);
        return stringBuffer.toString();
    }

    public String getLeftmostDerivation(int i) {
        StringBuffer stringBuffer = new StringBuffer(2000);
        stringBuffer.append(new StringBuffer("    ").append(toString()).toString());
        stringBuffer.append("\n");
        for (int i2 = 1; i2 < i; i2++) {
            stringBuffer.append(" =>");
            stringBuffer.append(getLeftmostDerivationStep(i2));
            stringBuffer.append("\n");
        }
        return stringBuffer.toString();
    }
}
