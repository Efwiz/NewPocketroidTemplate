package org.antlr.runtime.tree;

import java.util.List;
import org.antlr.runtime.Token;

/* JADX INFO: loaded from: classes12.dex */
public class RewriteRuleTokenStream extends RewriteRuleElementStream {
    @Override // org.antlr.runtime.tree.RewriteRuleElementStream
    protected Object toTree(Object obj) {
        return obj;
    }

    public RewriteRuleTokenStream(TreeAdaptor treeAdaptor, String str) {
        super(treeAdaptor, str);
    }

    public RewriteRuleTokenStream(TreeAdaptor treeAdaptor, String str, Object obj) {
        super(treeAdaptor, str, obj);
    }

    public RewriteRuleTokenStream(TreeAdaptor treeAdaptor, String str, List<Object> list) {
        super(treeAdaptor, str, list);
    }

    public Object nextNode() {
        return this.adaptor.create((Token) _next());
    }

    public Token nextToken() {
        return (Token) _next();
    }

    @Override // org.antlr.runtime.tree.RewriteRuleElementStream
    protected Object dup(Object obj) {
        throw new UnsupportedOperationException("dup can't be called for a token stream.");
    }
}
