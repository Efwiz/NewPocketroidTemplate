package org.antlr.runtime.tree;

import java.util.List;

/* JADX INFO: loaded from: classes12.dex */
public class RewriteRuleSubtreeStream extends RewriteRuleElementStream {
    public RewriteRuleSubtreeStream(TreeAdaptor treeAdaptor, String str) {
        super(treeAdaptor, str);
    }

    public RewriteRuleSubtreeStream(TreeAdaptor treeAdaptor, String str, Object obj) {
        super(treeAdaptor, str, obj);
    }

    public RewriteRuleSubtreeStream(TreeAdaptor treeAdaptor, String str, List<Object> list) {
        super(treeAdaptor, str, list);
    }

    public Object nextNode() {
        int size = size();
        if (this.dirty || (this.cursor >= size && size == 1)) {
            return this.adaptor.dupNode(_next());
        }
        Object obj_next = _next();
        while (this.adaptor.isNil(obj_next) && this.adaptor.getChildCount(obj_next) == 1) {
            obj_next = this.adaptor.getChild(obj_next, 0);
        }
        return this.adaptor.dupNode(obj_next);
    }

    @Override // org.antlr.runtime.tree.RewriteRuleElementStream
    protected Object dup(Object obj) {
        return this.adaptor.dupTree(obj);
    }
}
