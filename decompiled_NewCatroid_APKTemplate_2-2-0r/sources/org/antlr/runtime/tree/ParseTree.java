package org.antlr.runtime.tree;

import java.util.List;
import org.antlr.runtime.Token;

/* JADX INFO: loaded from: classes12.dex */
public class ParseTree extends BaseTree {
    public List<Token> hiddenTokens;
    public Object payload;

    @Override // org.antlr.runtime.tree.Tree
    public Tree dupNode() {
        return null;
    }

    @Override // org.antlr.runtime.tree.Tree
    public int getTokenStartIndex() {
        return 0;
    }

    @Override // org.antlr.runtime.tree.Tree
    public int getTokenStopIndex() {
        return 0;
    }

    @Override // org.antlr.runtime.tree.Tree
    public int getType() {
        return 0;
    }

    @Override // org.antlr.runtime.tree.Tree
    public void setTokenStartIndex(int i) {
    }

    @Override // org.antlr.runtime.tree.Tree
    public void setTokenStopIndex(int i) {
    }

    public ParseTree(Object obj) {
        this.payload = obj;
    }

    @Override // org.antlr.runtime.tree.Tree
    public String getText() {
        return toString();
    }

    @Override // org.antlr.runtime.tree.BaseTree, org.antlr.runtime.tree.Tree
    public String toString() {
        Object obj = this.payload;
        if (obj instanceof Token) {
            Token token = (Token) obj;
            if (token.getType() == -1) {
                return "<EOF>";
            }
            return token.getText();
        }
        return obj.toString();
    }

    public String toStringWithHiddenTokens() {
        StringBuilder sb = new StringBuilder();
        if (this.hiddenTokens != null) {
            for (int i = 0; i < this.hiddenTokens.size(); i++) {
                sb.append(this.hiddenTokens.get(i).getText());
            }
        }
        String string = toString();
        if (!string.equals("<EOF>")) {
            sb.append(string);
        }
        return sb.toString();
    }

    public String toInputString() {
        StringBuffer stringBuffer = new StringBuffer();
        _toStringLeaves(stringBuffer);
        return stringBuffer.toString();
    }

    public void _toStringLeaves(StringBuffer stringBuffer) {
        if (this.payload instanceof Token) {
            stringBuffer.append(toStringWithHiddenTokens());
            return;
        }
        for (int i = 0; this.children != null && i < this.children.size(); i++) {
            ((ParseTree) this.children.get(i))._toStringLeaves(stringBuffer);
        }
    }
}
