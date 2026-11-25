package org.jetbrains.kotlin.js.backend.ast;

import java.util.List;
import java.util.Stack;
import org.jetbrains.kotlin.com.intellij.util.SmartList;

/* JADX INFO: loaded from: classes5.dex */
public class JsVisitorWithContextImpl extends JsVisitorWithContext {
    protected final Stack<JsContext<JsStatement>> statementContexts = new Stack<>();

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/js/backend/ast/JsVisitorWithContextImpl", "getLastStatementLevelContext"));
    }

    public class ListContext<T extends JsNode> extends JsContext<T> {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private int index;
        private List<T> nodes;
        private final List<T> previous = new SmartList();
        private final List<T> next = new SmartList();
        private boolean removed = false;

        public ListContext() {
        }

        /* JADX WARN: Incorrect types in method signature: <R:TT;>(TR;)V */
        @Override // org.jetbrains.kotlin.js.backend.ast.JsContext
        public void addPrevious(JsNode jsNode) {
            this.previous.add(jsNode);
        }

        /* JADX WARN: Incorrect types in method signature: <R:TT;>(TR;)V */
        @Override // org.jetbrains.kotlin.js.backend.ast.JsContext
        public void addNext(JsNode jsNode) {
            this.next.add(jsNode);
        }

        @Override // org.jetbrains.kotlin.js.backend.ast.JsContext
        public void removeMe() {
            this.removed = true;
        }

        /* JADX WARN: Incorrect types in method signature: <R:TT;>(TR;)V */
        @Override // org.jetbrains.kotlin.js.backend.ast.JsContext
        public void replaceMe(JsNode jsNode) {
            JsVisitorWithContextImpl.checkReplacement(this.nodes.get(this.index), jsNode);
            this.nodes.set(this.index, jsNode);
            this.removed = false;
        }

        @Override // org.jetbrains.kotlin.js.backend.ast.JsContext
        public T getCurrentNode() {
            if (this.removed || this.index >= this.nodes.size()) {
                return null;
            }
            return this.nodes.get(this.index);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public void traverse(List<T> list) {
            this.nodes = list;
            this.index = 0;
            while (this.index < list.size()) {
                this.removed = false;
                this.previous.clear();
                this.next.clear();
                JsVisitorWithContextImpl.this.doTraverse(getCurrentNode(), this);
                if (!this.previous.isEmpty()) {
                    list.addAll(this.index, this.previous);
                    this.index += this.previous.size();
                }
                if (this.removed) {
                    list.remove(this.index);
                    this.index--;
                }
                if (!this.next.isEmpty()) {
                    list.addAll(this.index + 1, this.next);
                    this.index += this.next.size();
                }
                this.index++;
            }
            this.previous.clear();
            this.next.clear();
        }
    }

    private class LvalueContext extends NodeContext<JsExpression> {
        private LvalueContext() {
            super();
        }
    }

    private class NodeContext<T extends JsNode> extends JsContext<T> {
        protected T node;

        private NodeContext() {
        }

        @Override // org.jetbrains.kotlin.js.backend.ast.JsContext
        public void removeMe() {
            throw new UnsupportedOperationException();
        }

        /* JADX WARN: Incorrect types in method signature: <R:TT;>(TR;)V */
        /* JADX WARN: Multi-variable type inference failed */
        @Override // org.jetbrains.kotlin.js.backend.ast.JsContext
        public void replaceMe(JsNode jsNode) {
            JsVisitorWithContextImpl.checkReplacement(this.node, jsNode);
            this.node = jsNode;
        }

        @Override // org.jetbrains.kotlin.js.backend.ast.JsContext
        public T getCurrentNode() {
            return this.node;
        }

        protected T traverse(T t) {
            this.node = t;
            JsVisitorWithContextImpl.this.doTraverse(t, this);
            return this.node;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void checkReplacement(JsNode jsNode, JsNode jsNode2) {
        if (jsNode2 == null) {
            throw new RuntimeException("Cannot replace with null");
        }
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
    protected <T extends JsNode> T doAccept(T t) {
        return (T) new NodeContext().traverse(t);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
    protected JsExpression doAcceptLvalue(JsExpression jsExpression) {
        return new LvalueContext().traverse(jsExpression);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
    protected <T extends JsStatement> JsStatement doAcceptStatement(T t) {
        SmartList smartList = new SmartList(t);
        doAcceptStatementList(smartList);
        if (smartList.size() == 1) {
            return smartList.get(0);
        }
        return new JsBlock(smartList);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
    protected void doAcceptStatementList(List<JsStatement> list) {
        ListContext listContext = new ListContext();
        this.statementContexts.push(listContext);
        listContext.traverse(list);
        this.statementContexts.pop();
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
    protected <T extends JsNode> void doAcceptList(List<T> list) {
        new ListContext().traverse(list);
    }

    protected JsContext<JsStatement> getLastStatementLevelContext() {
        JsContext<JsStatement> jsContextPeek = this.statementContexts.peek();
        if (jsContextPeek == null) {
            $$$reportNull$$$0(0);
        }
        return jsContextPeek;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
    protected <T extends JsNode> void doTraverse(T t, JsContext jsContext) {
        t.traverse(this, jsContext);
    }
}
