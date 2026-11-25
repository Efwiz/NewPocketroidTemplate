package org.jetbrains.kotlin.com.google.gwt.dev.js.rhino;

import io.netty.util.internal.StringUtil;

/* JADX INFO: loaded from: classes3.dex */
public class Node implements Cloneable {
    private Comment commentAfter;
    private Comment commentBefore;
    private Node first;
    private Node last;
    Node next;
    private int operation;
    private CodePosition position;
    int type;

    private static class NumberNode extends Node {
        private double number;

        NumberNode(int i, double d, CodePosition codePosition) {
            super(i, codePosition);
            this.number = d;
        }

        @Override // org.jetbrains.kotlin.com.google.gwt.dev.js.rhino.Node
        public double getDouble() {
            return this.number;
        }
    }

    private static class StringNode extends Node {
        private String str;

        StringNode(int i, String str, CodePosition codePosition) {
            super(i, codePosition);
            if (str == null) {
                throw new IllegalArgumentException("StringNode: str is null");
            }
            this.str = str;
        }

        @Override // org.jetbrains.kotlin.com.google.gwt.dev.js.rhino.Node
        public String getString() {
            return this.str;
        }
    }

    public Node(int i) {
        this.type = i;
    }

    public Node(int i, Node node, CodePosition codePosition) {
        this.type = i;
        this.last = node;
        this.first = node;
        node.next = null;
        this.position = codePosition;
    }

    public Node(int i, Node node, int i2, CodePosition codePosition) {
        this.type = i;
        this.last = node;
        this.first = node;
        node.next = null;
        this.operation = i2;
        this.position = codePosition;
    }

    public Node(int i, Node node, Node node2, CodePosition codePosition) {
        this(i, node, node2, -1, codePosition);
    }

    public Node(int i, Node node, Node node2, int i2, CodePosition codePosition) {
        this.type = i;
        this.first = node;
        this.last = node2;
        node.next = node2;
        node2.next = null;
        this.operation = i2;
        this.position = codePosition;
    }

    public Node(int i, Node node, Node node2, Node node3, CodePosition codePosition) {
        this.type = i;
        this.first = node;
        this.last = node3;
        node.next = node2;
        node2.next = node3;
        node3.next = null;
        this.position = codePosition;
    }

    public Node(int i, Node node, Node node2, Node node3, Node node4, CodePosition codePosition) {
        this.type = i;
        this.first = node;
        this.last = node4;
        node.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = null;
        this.position = codePosition;
    }

    public Node(int i, CodePosition codePosition) {
        this.type = i;
        this.position = codePosition;
    }

    public Node(int i, int i2, CodePosition codePosition) {
        this.type = i;
        this.operation = i2;
        this.position = codePosition;
    }

    public static Node newIntNumber(double d, CodePosition codePosition) {
        return new NumberNode(148, d, codePosition);
    }

    public static Node newNumber(double d, CodePosition codePosition) {
        return new NumberNode(45, d, codePosition);
    }

    public static Node newString(String str, CodePosition codePosition) {
        return new StringNode(46, str, codePosition);
    }

    public static Node newString(int i, String str, CodePosition codePosition) {
        return new StringNode(i, str, codePosition);
    }

    public int getType() {
        return this.type;
    }

    public void setType(int i) {
        this.type = i;
    }

    public Node getFirstChild() {
        return this.first;
    }

    public Node getLastChild() {
        return this.last;
    }

    public Node getNext() {
        return this.next;
    }

    public Node getLastSibling() {
        Node node = this;
        while (true) {
            Node node2 = node.next;
            if (node2 == null) {
                return node;
            }
            node = node2;
        }
    }

    public void addChildToBack(Node node) {
        node.next = null;
        Node node2 = this.last;
        if (node2 == null) {
            this.last = node;
            this.first = node;
        } else {
            node2.next = node;
            this.last = node;
        }
    }

    public void addChildrenToBack(Node node) {
        Node node2 = this.last;
        if (node2 != null) {
            node2.next = node;
        }
        this.last = node.getLastSibling();
        if (this.first == null) {
            this.first = node;
        }
    }

    public Comment getCommentsBeforeNode() {
        return this.commentBefore;
    }

    public Comment getCommentsAfterNode() {
        return this.commentAfter;
    }

    public void setCommentsBeforeNode(Comment comment) {
        if (comment == null) {
            return;
        }
        this.commentBefore = comment;
    }

    public void setCommentsAfterNode(Comment comment) {
        if (comment == null) {
            return;
        }
        this.commentAfter = comment;
    }

    public int getOperation() {
        return this.operation;
    }

    public CodePosition getPosition() {
        return this.position;
    }

    public void setPosition(CodePosition codePosition) {
        this.position = codePosition;
    }

    public double getDouble() throws UnsupportedOperationException {
        throw new UnsupportedOperationException(this + " is not a number node");
    }

    public String getString() throws UnsupportedOperationException {
        throw new UnsupportedOperationException(this + " is not a string node");
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer(TokenStream.tokenToName(this.type));
        if (this instanceof StringNode) {
            stringBuffer.append(StringUtil.SPACE);
            stringBuffer.append(getString());
        } else {
            int i = this.type;
            if (i == 45) {
                stringBuffer.append(StringUtil.SPACE);
                stringBuffer.append(getDouble());
            } else if (i == 110) {
                stringBuffer.append(StringUtil.SPACE);
                stringBuffer.append(this.first.getString());
            } else if (i == 137) {
                stringBuffer.append(StringUtil.SPACE);
                stringBuffer.append(hashCode());
            } else if (i == 148) {
                stringBuffer.append(StringUtil.SPACE);
                stringBuffer.append((int) getDouble());
            }
        }
        if (this.operation != -1) {
            stringBuffer.append(StringUtil.SPACE);
            stringBuffer.append(this.operation);
        }
        return stringBuffer.toString();
    }
}
