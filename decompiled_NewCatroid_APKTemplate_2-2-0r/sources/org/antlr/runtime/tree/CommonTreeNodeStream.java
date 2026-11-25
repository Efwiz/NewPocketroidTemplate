package org.antlr.runtime.tree;

import org.antlr.runtime.Token;
import org.antlr.runtime.TokenStream;
import org.antlr.runtime.misc.IntArray;
import org.antlr.runtime.misc.LookaheadStream;

/* JADX INFO: loaded from: classes12.dex */
public class CommonTreeNodeStream extends LookaheadStream<Object> implements TreeNodeStream, PositionTrackingStream<Object> {
    public static final int DEFAULT_INITIAL_BUFFER_SIZE = 100;
    public static final int INITIAL_CALL_STACK_SIZE = 10;
    TreeAdaptor adaptor;
    protected IntArray calls;
    protected boolean hasNilRoot;
    protected TreeIterator it;
    protected int level;
    protected Object previousLocationElement;
    protected Object root;
    protected TokenStream tokens;

    @Override // org.antlr.runtime.tree.TreeNodeStream
    public void setUniqueNavigationNodes(boolean z) {
    }

    public CommonTreeNodeStream(Object obj) {
        this(new CommonTreeAdaptor(), obj);
    }

    public CommonTreeNodeStream(TreeAdaptor treeAdaptor, Object obj) {
        this.hasNilRoot = false;
        this.level = 0;
        this.root = obj;
        this.adaptor = treeAdaptor;
        this.it = new TreeIterator(treeAdaptor, this.root);
    }

    @Override // org.antlr.runtime.misc.LookaheadStream, org.antlr.runtime.misc.FastQueue
    public void reset() {
        super.reset();
        this.it.reset();
        this.hasNilRoot = false;
        this.level = 0;
        this.previousLocationElement = null;
        IntArray intArray = this.calls;
        if (intArray != null) {
            intArray.clear();
        }
    }

    @Override // org.antlr.runtime.misc.LookaheadStream
    public Object nextElement() {
        Object next = this.it.next();
        if (next == this.it.up) {
            int i = this.level - 1;
            this.level = i;
            if (i == 0 && this.hasNilRoot) {
                return this.it.next();
            }
        } else if (next == this.it.down) {
            this.level++;
        }
        if (this.level != 0 || !this.adaptor.isNil(next)) {
            return next;
        }
        this.hasNilRoot = true;
        this.it.next();
        this.level++;
        return this.it.next();
    }

    @Override // org.antlr.runtime.misc.LookaheadStream, org.antlr.runtime.misc.FastQueue
    public Object remove() {
        Object objRemove = super.remove();
        if (this.p == 0 && hasPositionInformation(this.prevElement)) {
            this.previousLocationElement = this.prevElement;
        }
        return objRemove;
    }

    @Override // org.antlr.runtime.misc.LookaheadStream
    public boolean isEOF(Object obj) {
        return this.adaptor.getType(obj) == -1;
    }

    @Override // org.antlr.runtime.tree.TreeNodeStream
    public Object getTreeSource() {
        return this.root;
    }

    @Override // org.antlr.runtime.IntStream
    public String getSourceName() {
        return getTokenStream().getSourceName();
    }

    @Override // org.antlr.runtime.tree.TreeNodeStream
    public TokenStream getTokenStream() {
        return this.tokens;
    }

    public void setTokenStream(TokenStream tokenStream) {
        this.tokens = tokenStream;
    }

    @Override // org.antlr.runtime.tree.TreeNodeStream
    public TreeAdaptor getTreeAdaptor() {
        return this.adaptor;
    }

    public void setTreeAdaptor(TreeAdaptor treeAdaptor) {
        this.adaptor = treeAdaptor;
    }

    @Override // org.antlr.runtime.tree.TreeNodeStream
    public Object get(int i) {
        throw new UnsupportedOperationException("Absolute node indexes are meaningless in an unbuffered stream");
    }

    @Override // org.antlr.runtime.IntStream
    public int LA(int i) {
        return this.adaptor.getType(LT(i));
    }

    public void push(int i) {
        if (this.calls == null) {
            this.calls = new IntArray();
        }
        this.calls.push(this.p);
        seek(i);
    }

    public int pop() {
        int iPop = this.calls.pop();
        seek(iPop);
        return iPop;
    }

    @Override // org.antlr.runtime.tree.PositionTrackingStream
    public Object getKnownPositionElement(boolean z) {
        Object obj = this.data.get(this.p);
        if (hasPositionInformation(obj)) {
            return obj;
        }
        if (!z) {
            return null;
        }
        for (int i = this.p - 1; i >= 0; i--) {
            Object obj2 = this.data.get(i);
            if (hasPositionInformation(obj2)) {
                return obj2;
            }
        }
        return this.previousLocationElement;
    }

    @Override // org.antlr.runtime.tree.PositionTrackingStream
    public boolean hasPositionInformation(Object obj) {
        Token token = this.adaptor.getToken(obj);
        return token != null && token.getLine() > 0;
    }

    @Override // org.antlr.runtime.tree.TreeNodeStream
    public void replaceChildren(Object obj, int i, int i2, Object obj2) {
        if (obj != null) {
            this.adaptor.replaceChildren(obj, i, i2, obj2);
        }
    }

    @Override // org.antlr.runtime.tree.TreeNodeStream
    public String toString(Object obj, Object obj2) {
        return "n/a";
    }

    public String toTokenTypeString() {
        reset();
        StringBuilder sb = new StringBuilder();
        int type = this.adaptor.getType(LT(1));
        while (type != -1) {
            sb.append(" ");
            sb.append(type);
            consume();
            type = this.adaptor.getType(LT(1));
        }
        return sb.toString();
    }
}
