package org.antlr.runtime.tree;

import java.util.List;

/* JADX INFO: loaded from: classes12.dex */
public class RewriteRuleNodeStream extends RewriteRuleElementStream {
    public RewriteRuleNodeStream(TreeAdaptor treeAdaptor, String str) {
        super(treeAdaptor, str);
    }

    public RewriteRuleNodeStream(TreeAdaptor treeAdaptor, String str, Object obj) {
        super(treeAdaptor, str, obj);
    }

    public RewriteRuleNodeStream(TreeAdaptor treeAdaptor, String str, List<Object> list) {
        super(treeAdaptor, str, list);
    }

    public Object nextNode() {
        return _next();
    }

    @Override // org.antlr.runtime.tree.RewriteRuleElementStream
    protected Object toTree(Object obj) {
        return this.adaptor.dupNode(obj);
    }

    @Override // org.antlr.runtime.tree.RewriteRuleElementStream
    protected Object dup(Object obj) {
        throw new UnsupportedOperationException("dup can't be called for a node stream.");
    }
}
