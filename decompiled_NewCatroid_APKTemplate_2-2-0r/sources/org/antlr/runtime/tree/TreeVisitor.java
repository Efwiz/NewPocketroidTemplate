package org.antlr.runtime.tree;

/* JADX INFO: loaded from: classes12.dex */
public class TreeVisitor {
    protected TreeAdaptor adaptor;

    public TreeVisitor(TreeAdaptor treeAdaptor) {
        this.adaptor = treeAdaptor;
    }

    public TreeVisitor() {
        this(new CommonTreeAdaptor());
    }

    public Object visit(Object obj, TreeVisitorAction treeVisitorAction) {
        boolean zIsNil = this.adaptor.isNil(obj);
        if (treeVisitorAction != null && !zIsNil) {
            obj = treeVisitorAction.pre(obj);
        }
        for (int i = 0; i < this.adaptor.getChildCount(obj); i++) {
            Object objVisit = visit(this.adaptor.getChild(obj, i), treeVisitorAction);
            if (objVisit != this.adaptor.getChild(obj, i)) {
                this.adaptor.setChild(obj, i, objVisit);
            }
        }
        return (treeVisitorAction == null || zIsNil) ? obj : treeVisitorAction.post(obj);
    }
}
