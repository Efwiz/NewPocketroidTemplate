package antlr;

import antlr.collections.AST;
import io.netty.util.internal.StringUtil;

/* JADX INFO: loaded from: classes.dex */
public class ParseTreeRule extends ParseTree {
    public static final int INVALID_ALT = -1;
    protected int altNumber;
    protected String ruleName;

    public ParseTreeRule(String str) {
        this(str, -1);
    }

    public ParseTreeRule(String str, int i) {
        this.ruleName = str;
        this.altNumber = i;
    }

    public String getRuleName() {
        return this.ruleName;
    }

    @Override // antlr.ParseTree
    protected int getLeftmostDerivation(StringBuffer stringBuffer, int i) {
        if (i <= 0) {
            stringBuffer.append(StringUtil.SPACE);
            stringBuffer.append(toString());
            return 0;
        }
        int leftmostDerivation = 1;
        for (AST firstChild = getFirstChild(); firstChild != null; firstChild = firstChild.getNextSibling()) {
            if (leftmostDerivation >= i || (firstChild instanceof ParseTreeToken)) {
                stringBuffer.append(StringUtil.SPACE);
                stringBuffer.append(firstChild.toString());
            } else {
                leftmostDerivation += ((ParseTree) firstChild).getLeftmostDerivation(stringBuffer, i - leftmostDerivation);
            }
        }
        return leftmostDerivation;
    }

    @Override // antlr.BaseAST, antlr.collections.AST
    public String toString() {
        if (this.altNumber == -1) {
            return new StringBuffer("<").append(this.ruleName).append('>').toString();
        }
        return new StringBuffer("<").append(this.ruleName).append("[").append(this.altNumber).append("]>").toString();
    }
}
