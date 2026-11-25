package org.antlr.runtime.tree;

import java.util.Iterator;
import org.antlr.runtime.misc.FastQueue;

/* JADX INFO: loaded from: classes12.dex */
public class TreeIterator implements Iterator<Object> {
    protected TreeAdaptor adaptor;
    public Object down;
    public Object eof;
    protected boolean firstTime;
    protected FastQueue<Object> nodes;
    protected Object root;
    protected Object tree;
    public Object up;

    public TreeIterator(Object obj) {
        this(new CommonTreeAdaptor(), obj);
    }

    public TreeIterator(TreeAdaptor treeAdaptor, Object obj) {
        this.firstTime = true;
        this.adaptor = treeAdaptor;
        this.tree = obj;
        this.root = obj;
        this.nodes = new FastQueue<>();
        this.down = treeAdaptor.create(2, "DOWN");
        this.up = treeAdaptor.create(3, "UP");
        this.eof = treeAdaptor.create(-1, "EOF");
    }

    public void reset() {
        this.firstTime = true;
        this.tree = this.root;
        this.nodes.clear();
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        if (this.firstTime) {
            return this.root != null;
        }
        FastQueue<Object> fastQueue = this.nodes;
        if (fastQueue != null && fastQueue.size() > 0) {
            return true;
        }
        Object obj = this.tree;
        if (obj == null) {
            return false;
        }
        return this.adaptor.getChildCount(obj) > 0 || this.adaptor.getParent(this.tree) != null;
    }

    @Override // java.util.Iterator
    public Object next() {
        if (this.firstTime) {
            this.firstTime = false;
            if (this.adaptor.getChildCount(this.tree) == 0) {
                this.nodes.add(this.eof);
                return this.tree;
            }
            return this.tree;
        }
        FastQueue<Object> fastQueue = this.nodes;
        if (fastQueue != null && fastQueue.size() > 0) {
            return this.nodes.remove();
        }
        Object obj = this.tree;
        if (obj == null) {
            return this.eof;
        }
        if (this.adaptor.getChildCount(obj) > 0) {
            Object child = this.adaptor.getChild(this.tree, 0);
            this.tree = child;
            this.nodes.add(child);
            return this.down;
        }
        Object parent = this.adaptor.getParent(this.tree);
        while (parent != null && this.adaptor.getChildIndex(this.tree) + 1 >= this.adaptor.getChildCount(parent)) {
            this.nodes.add(this.up);
            this.tree = parent;
            parent = this.adaptor.getParent(parent);
        }
        if (parent == null) {
            this.tree = null;
            this.nodes.add(this.eof);
            return this.nodes.remove();
        }
        Object child2 = this.adaptor.getChild(parent, this.adaptor.getChildIndex(this.tree) + 1);
        this.tree = child2;
        this.nodes.add(child2);
        return this.nodes.remove();
    }

    @Override // java.util.Iterator
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
