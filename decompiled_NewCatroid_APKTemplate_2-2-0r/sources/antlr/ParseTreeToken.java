package antlr;

import io.netty.util.internal.StringUtil;

/* JADX INFO: loaded from: classes.dex */
public class ParseTreeToken extends ParseTree {
    protected Token token;

    public ParseTreeToken(Token token) {
        this.token = token;
    }

    @Override // antlr.ParseTree
    protected int getLeftmostDerivation(StringBuffer stringBuffer, int i) {
        stringBuffer.append(StringUtil.SPACE);
        stringBuffer.append(toString());
        return i;
    }

    @Override // antlr.BaseAST, antlr.collections.AST
    public String toString() {
        Token token = this.token;
        if (token != null) {
            return token.getText();
        }
        return "<missing token>";
    }
}
