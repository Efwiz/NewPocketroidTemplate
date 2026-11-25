package org.antlr.runtime.debug;

import org.antlr.runtime.TokenStream;
import org.antlr.runtime.tree.TreeAdaptor;
import org.antlr.runtime.tree.TreeNodeStream;

/* JADX INFO: loaded from: classes12.dex */
public class DebugTreeNodeStream implements TreeNodeStream {
    protected TreeAdaptor adaptor;
    protected DebugEventListener dbg;
    protected boolean initialStreamState = true;
    protected TreeNodeStream input;
    protected int lastMarker;

    @Override // org.antlr.runtime.IntStream
    public void release(int i) {
    }

    @Override // org.antlr.runtime.tree.TreeNodeStream
    public void reset() {
    }

    public DebugTreeNodeStream(TreeNodeStream treeNodeStream, DebugEventListener debugEventListener) {
        this.input = treeNodeStream;
        this.adaptor = treeNodeStream.getTreeAdaptor();
        this.input.setUniqueNavigationNodes(true);
        setDebugListener(debugEventListener);
    }

    public void setDebugListener(DebugEventListener debugEventListener) {
        this.dbg = debugEventListener;
    }

    @Override // org.antlr.runtime.tree.TreeNodeStream
    public TreeAdaptor getTreeAdaptor() {
        return this.adaptor;
    }

    @Override // org.antlr.runtime.IntStream
    public void consume() {
        Object objLT = this.input.LT(1);
        this.input.consume();
        this.dbg.consumeNode(objLT);
    }

    @Override // org.antlr.runtime.tree.TreeNodeStream
    public Object get(int i) {
        return this.input.get(i);
    }

    @Override // org.antlr.runtime.tree.TreeNodeStream
    public Object LT(int i) {
        Object objLT = this.input.LT(i);
        this.adaptor.getUniqueID(objLT);
        this.adaptor.getText(objLT);
        this.adaptor.getType(objLT);
        this.dbg.LT(i, objLT);
        return objLT;
    }

    @Override // org.antlr.runtime.IntStream
    public int LA(int i) {
        Object objLT = this.input.LT(i);
        this.adaptor.getUniqueID(objLT);
        this.adaptor.getText(objLT);
        int type = this.adaptor.getType(objLT);
        this.dbg.LT(i, objLT);
        return type;
    }

    @Override // org.antlr.runtime.IntStream
    public int mark() {
        int iMark = this.input.mark();
        this.lastMarker = iMark;
        this.dbg.mark(iMark);
        return this.lastMarker;
    }

    @Override // org.antlr.runtime.IntStream
    public int index() {
        return this.input.index();
    }

    @Override // org.antlr.runtime.IntStream
    public void rewind(int i) {
        this.dbg.rewind(i);
        this.input.rewind(i);
    }

    @Override // org.antlr.runtime.IntStream
    public void rewind() {
        this.dbg.rewind();
        this.input.rewind(this.lastMarker);
    }

    @Override // org.antlr.runtime.IntStream
    public void seek(int i) {
        this.input.seek(i);
    }

    @Override // org.antlr.runtime.IntStream
    public int size() {
        return this.input.size();
    }

    @Override // org.antlr.runtime.tree.TreeNodeStream
    public Object getTreeSource() {
        return this.input;
    }

    @Override // org.antlr.runtime.IntStream
    public String getSourceName() {
        return getTokenStream().getSourceName();
    }

    @Override // org.antlr.runtime.tree.TreeNodeStream
    public TokenStream getTokenStream() {
        return this.input.getTokenStream();
    }

    @Override // org.antlr.runtime.tree.TreeNodeStream
    public void setUniqueNavigationNodes(boolean z) {
        this.input.setUniqueNavigationNodes(z);
    }

    @Override // org.antlr.runtime.tree.TreeNodeStream
    public void replaceChildren(Object obj, int i, int i2, Object obj2) {
        this.input.replaceChildren(obj, i, i2, obj2);
    }

    @Override // org.antlr.runtime.tree.TreeNodeStream
    public String toString(Object obj, Object obj2) {
        return this.input.toString(obj, obj2);
    }
}
