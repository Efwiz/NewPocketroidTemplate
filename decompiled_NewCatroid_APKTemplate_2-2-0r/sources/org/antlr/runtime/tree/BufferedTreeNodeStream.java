package org.antlr.runtime.tree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.antlr.runtime.TokenStream;
import org.antlr.runtime.misc.IntArray;

/* JADX INFO: loaded from: classes12.dex */
public class BufferedTreeNodeStream implements TreeNodeStream {
    public static final int DEFAULT_INITIAL_BUFFER_SIZE = 100;
    public static final int INITIAL_CALL_STACK_SIZE = 10;
    TreeAdaptor adaptor;
    protected IntArray calls;
    protected Object down;
    protected Object eof;
    protected int lastMarker;
    protected List<Object> nodes;
    protected int p;
    protected Object root;
    protected TokenStream tokens;
    protected boolean uniqueNavigationNodes;
    protected Object up;

    @Override // org.antlr.runtime.IntStream
    public void release(int i) {
    }

    protected class StreamIterator implements Iterator<Object> {
        int i = 0;

        protected StreamIterator() {
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.i < BufferedTreeNodeStream.this.nodes.size();
        }

        @Override // java.util.Iterator
        public Object next() {
            int i = this.i;
            this.i = i + 1;
            if (i < BufferedTreeNodeStream.this.nodes.size()) {
                return BufferedTreeNodeStream.this.nodes.get(i);
            }
            return BufferedTreeNodeStream.this.eof;
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new RuntimeException("cannot remove nodes from stream");
        }
    }

    public BufferedTreeNodeStream(Object obj) {
        this(new CommonTreeAdaptor(), obj);
    }

    public BufferedTreeNodeStream(TreeAdaptor treeAdaptor, Object obj) {
        this(treeAdaptor, obj, 100);
    }

    public BufferedTreeNodeStream(TreeAdaptor treeAdaptor, Object obj, int i) {
        this.uniqueNavigationNodes = false;
        this.p = -1;
        this.root = obj;
        this.adaptor = treeAdaptor;
        this.nodes = new ArrayList(i);
        this.down = treeAdaptor.create(2, "DOWN");
        this.up = treeAdaptor.create(3, "UP");
        this.eof = treeAdaptor.create(-1, "EOF");
    }

    protected void fillBuffer() {
        fillBuffer(this.root);
        this.p = 0;
    }

    public void fillBuffer(Object obj) {
        boolean zIsNil = this.adaptor.isNil(obj);
        if (!zIsNil) {
            this.nodes.add(obj);
        }
        int childCount = this.adaptor.getChildCount(obj);
        if (!zIsNil && childCount > 0) {
            addNavigationNode(2);
        }
        for (int i = 0; i < childCount; i++) {
            fillBuffer(this.adaptor.getChild(obj, i));
        }
        if (zIsNil || childCount <= 0) {
            return;
        }
        addNavigationNode(3);
    }

    protected int getNodeIndex(Object obj) {
        if (this.p == -1) {
            fillBuffer();
        }
        for (int i = 0; i < this.nodes.size(); i++) {
            if (this.nodes.get(i) == obj) {
                return i;
            }
        }
        return -1;
    }

    protected void addNavigationNode(int i) {
        Object objCreate;
        if (i == 2) {
            if (hasUniqueNavigationNodes()) {
                objCreate = this.adaptor.create(2, "DOWN");
            } else {
                objCreate = this.down;
            }
        } else if (hasUniqueNavigationNodes()) {
            objCreate = this.adaptor.create(3, "UP");
        } else {
            objCreate = this.up;
        }
        this.nodes.add(objCreate);
    }

    @Override // org.antlr.runtime.tree.TreeNodeStream
    public Object get(int i) {
        if (this.p == -1) {
            fillBuffer();
        }
        return this.nodes.get(i);
    }

    @Override // org.antlr.runtime.tree.TreeNodeStream
    public Object LT(int i) {
        if (this.p == -1) {
            fillBuffer();
        }
        if (i == 0) {
            return null;
        }
        if (i < 0) {
            return LB(-i);
        }
        if ((this.p + i) - 1 >= this.nodes.size()) {
            return this.eof;
        }
        return this.nodes.get((this.p + i) - 1);
    }

    public Object getCurrentSymbol() {
        return LT(1);
    }

    protected Object LB(int i) {
        if (i == 0) {
            return null;
        }
        int i2 = this.p;
        if (i2 - i < 0) {
            return null;
        }
        return this.nodes.get(i2 - i);
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

    public boolean hasUniqueNavigationNodes() {
        return this.uniqueNavigationNodes;
    }

    @Override // org.antlr.runtime.tree.TreeNodeStream
    public void setUniqueNavigationNodes(boolean z) {
        this.uniqueNavigationNodes = z;
    }

    @Override // org.antlr.runtime.IntStream
    public void consume() {
        if (this.p == -1) {
            fillBuffer();
        }
        this.p++;
    }

    @Override // org.antlr.runtime.IntStream
    public int LA(int i) {
        return this.adaptor.getType(LT(i));
    }

    @Override // org.antlr.runtime.IntStream
    public int mark() {
        if (this.p == -1) {
            fillBuffer();
        }
        int iIndex = index();
        this.lastMarker = iIndex;
        return iIndex;
    }

    @Override // org.antlr.runtime.IntStream
    public int index() {
        return this.p;
    }

    @Override // org.antlr.runtime.IntStream
    public void rewind(int i) {
        seek(i);
    }

    @Override // org.antlr.runtime.IntStream
    public void rewind() {
        seek(this.lastMarker);
    }

    @Override // org.antlr.runtime.IntStream
    public void seek(int i) {
        if (this.p == -1) {
            fillBuffer();
        }
        this.p = i;
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

    @Override // org.antlr.runtime.tree.TreeNodeStream
    public void reset() {
        this.p = 0;
        this.lastMarker = 0;
        IntArray intArray = this.calls;
        if (intArray != null) {
            intArray.clear();
        }
    }

    @Override // org.antlr.runtime.IntStream
    public int size() {
        if (this.p == -1) {
            fillBuffer();
        }
        return this.nodes.size();
    }

    public Iterator<Object> iterator() {
        if (this.p == -1) {
            fillBuffer();
        }
        return new StreamIterator();
    }

    @Override // org.antlr.runtime.tree.TreeNodeStream
    public void replaceChildren(Object obj, int i, int i2, Object obj2) {
        if (obj != null) {
            this.adaptor.replaceChildren(obj, i, i2, obj2);
        }
    }

    public String toTokenTypeString() {
        if (this.p == -1) {
            fillBuffer();
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.nodes.size(); i++) {
            Object obj = this.nodes.get(i);
            sb.append(" ");
            sb.append(this.adaptor.getType(obj));
        }
        return sb.toString();
    }

    public String toTokenString(int i, int i2) {
        if (this.p == -1) {
            fillBuffer();
        }
        StringBuilder sb = new StringBuilder();
        while (i < this.nodes.size() && i <= i2) {
            Object obj = this.nodes.get(i);
            sb.append(" ");
            sb.append(this.adaptor.getToken(obj));
            i++;
        }
        return sb.toString();
    }

    @Override // org.antlr.runtime.tree.TreeNodeStream
    public String toString(Object obj, Object obj2) {
        System.out.println("toString");
        if (obj == null || obj2 == null) {
            return null;
        }
        if (this.p == -1) {
            fillBuffer();
        }
        if (obj instanceof CommonTree) {
            System.out.print("toString: " + ((CommonTree) obj).getToken() + ", ");
        } else {
            System.out.println(obj);
        }
        if (obj2 instanceof CommonTree) {
            System.out.println(((CommonTree) obj2).getToken());
        } else {
            System.out.println(obj2);
        }
        if (this.tokens != null) {
            int tokenStartIndex = this.adaptor.getTokenStartIndex(obj);
            int tokenStopIndex = this.adaptor.getTokenStopIndex(obj2);
            if (this.adaptor.getType(obj2) == 3) {
                tokenStopIndex = this.adaptor.getTokenStopIndex(obj);
            } else if (this.adaptor.getType(obj2) == -1) {
                tokenStopIndex = size() - 2;
            }
            return this.tokens.toString(tokenStartIndex, tokenStopIndex);
        }
        int i = 0;
        while (i < this.nodes.size() && this.nodes.get(i) != obj) {
            i++;
        }
        StringBuilder sb = new StringBuilder();
        Object obj3 = this.nodes.get(i);
        while (obj3 != obj2) {
            String text = this.adaptor.getText(obj3);
            if (text == null) {
                text = " " + String.valueOf(this.adaptor.getType(obj3));
            }
            sb.append(text);
            i++;
            obj3 = this.nodes.get(i);
        }
        String text2 = this.adaptor.getText(obj2);
        if (text2 == null) {
            text2 = " " + String.valueOf(this.adaptor.getType(obj2));
        }
        sb.append(text2);
        return sb.toString();
    }
}
