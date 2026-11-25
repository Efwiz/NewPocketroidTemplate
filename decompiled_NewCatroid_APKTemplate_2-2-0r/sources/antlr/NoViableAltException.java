package antlr;

import antlr.collections.AST;

/* JADX INFO: loaded from: classes.dex */
public class NoViableAltException extends RecognitionException {
    public AST node;
    public Token token;

    public NoViableAltException(AST ast) {
        super("NoViableAlt", "<AST>", ast.getLine(), ast.getColumn());
        this.node = ast;
    }

    public NoViableAltException(Token token, String str) {
        super("NoViableAlt", str, token.getLine(), token.getColumn());
        this.token = token;
    }

    @Override // java.lang.Throwable
    public String getMessage() {
        if (this.token != null) {
            return new StringBuffer("unexpected token: ").append(this.token.getText()).toString();
        }
        if (this.node == TreeParser.ASTNULL) {
            return "unexpected end of subtree";
        }
        return new StringBuffer("unexpected AST node: ").append(this.node.toString()).toString();
    }
}
