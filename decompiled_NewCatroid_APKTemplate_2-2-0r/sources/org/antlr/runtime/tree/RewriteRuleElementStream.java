package org.antlr.runtime.tree;

import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes12.dex */
public abstract class RewriteRuleElementStream {
    protected TreeAdaptor adaptor;
    protected int cursor;
    protected boolean dirty;
    protected String elementDescription;
    protected List<Object> elements;
    protected Object singleElement;

    protected abstract Object dup(Object obj);

    protected Object toTree(Object obj) {
        return obj;
    }

    public RewriteRuleElementStream(TreeAdaptor treeAdaptor, String str) {
        this.cursor = 0;
        this.dirty = false;
        this.elementDescription = str;
        this.adaptor = treeAdaptor;
    }

    public RewriteRuleElementStream(TreeAdaptor treeAdaptor, String str, Object obj) {
        this(treeAdaptor, str);
        add(obj);
    }

    public RewriteRuleElementStream(TreeAdaptor treeAdaptor, String str, List<Object> list) {
        this(treeAdaptor, str);
        this.singleElement = null;
        this.elements = list;
    }

    public void reset() {
        this.cursor = 0;
        this.dirty = true;
    }

    public void add(Object obj) {
        if (obj == null) {
            return;
        }
        List<Object> list = this.elements;
        if (list != null) {
            list.add(obj);
            return;
        }
        if (this.singleElement == null) {
            this.singleElement = obj;
            return;
        }
        ArrayList arrayList = new ArrayList(5);
        this.elements = arrayList;
        arrayList.add(this.singleElement);
        this.singleElement = null;
        this.elements.add(obj);
    }

    public Object nextTree() {
        int size = size();
        if (this.dirty || (this.cursor >= size && size == 1)) {
            return dup(_next());
        }
        return _next();
    }

    protected Object _next() {
        int size = size();
        if (size == 0) {
            throw new RewriteEmptyStreamException(this.elementDescription);
        }
        int i = this.cursor;
        if (i >= size) {
            if (size == 1) {
                return toTree(this.singleElement);
            }
            throw new RewriteCardinalityException(this.elementDescription);
        }
        Object obj = this.singleElement;
        if (obj != null) {
            this.cursor = i + 1;
            return toTree(obj);
        }
        Object tree = toTree(this.elements.get(i));
        this.cursor++;
        return tree;
    }

    public boolean hasNext() {
        if (this.singleElement != null && this.cursor < 1) {
            return true;
        }
        List<Object> list = this.elements;
        return list != null && this.cursor < list.size();
    }

    public int size() {
        int i = this.singleElement != null ? 1 : 0;
        List<Object> list = this.elements;
        return list != null ? list.size() : i;
    }

    public String getDescription() {
        return this.elementDescription;
    }
}
