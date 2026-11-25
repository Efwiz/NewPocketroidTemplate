package org.jetbrains.kotlin.com.intellij.openapi.editor.impl;

import java.util.concurrent.atomic.AtomicInteger;
import org.jetbrains.kotlin.com.intellij.util.BitUtil;

/* JADX INFO: loaded from: classes3.dex */
public abstract class RedBlackTree<K> extends AtomicInteger {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static boolean VERIFY;
    private int nodeSize;
    protected Node<K> root = null;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 5 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 5 ? 3 : 2];
        if (i == 2) {
            objArr[0] = "oldn";
        } else if (i != 5) {
            objArr[0] = "n";
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/editor/impl/RedBlackTree";
        }
        if (i != 5) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/editor/impl/RedBlackTree";
        } else {
            objArr[1] = "maximumNode";
        }
        if (i == 1) {
            objArr[2] = "rotateRight";
        } else if (i == 2) {
            objArr[2] = "replaceNode";
        } else if (i == 3) {
            objArr[2] = "deleteNode";
        } else if (i == 4) {
            objArr[2] = "maximumNode";
        } else if (i != 5) {
            objArr[2] = "rotateLeft";
        }
        String str2 = String.format(str, objArr);
        if (i == 5) {
            throw new IllegalStateException(str2);
        }
    }

    private static <K> void assertParentChild(Node<K> node) {
    }

    private static void verifyProperty2(Node<?> node) {
    }

    protected abstract Node<K> swapWithMaxPred(Node<K> node, Node<K> node2);

    RedBlackTree() {
        verifyProperties();
    }

    void incModCount() {
        incrementAndGet();
    }

    int getModCount() {
        return get();
    }

    protected void rotateLeft(Node<K> node) {
        if (node == null) {
            $$$reportNull$$$0(0);
        }
        Node<K> right = node.getRight();
        replaceNode(node, right);
        node.setRight(right.getLeft());
        if (right.getLeft() != null) {
            right.getLeft().setParent(node);
        }
        right.setLeft(node);
        node.setParent(right);
    }

    protected void rotateRight(Node<K> node) {
        if (node == null) {
            $$$reportNull$$$0(1);
        }
        Node<K> left = node.getLeft();
        replaceNode(node, left);
        node.setLeft(left.getRight());
        if (left.getRight() != null) {
            left.getRight().setParent(node);
        }
        left.setRight(node);
        node.setParent(left);
    }

    protected void replaceNode(Node<K> node, Node<K> node2) {
        if (node == null) {
            $$$reportNull$$$0(2);
        }
        Node<K> parent = node.getParent();
        if (parent == null) {
            this.root = node2;
        } else if (node == parent.getLeft()) {
            parent.setLeft(node2);
        } else {
            parent.setRight(node2);
        }
        if (node2 != null) {
            node2.setParent(parent);
        }
    }

    void onInsertNode() {
        this.nodeSize++;
    }

    void insertCase1(Node<K> node) {
        if (node.getParent() != null) {
            insertCase2(node);
        } else {
            node.setBlack();
        }
    }

    private void insertCase2(Node<K> node) {
        if (isBlack(node.getParent())) {
            return;
        }
        insertCase3(node);
    }

    private void insertCase3(Node<K> node) {
        if (isBlack(node.uncle())) {
            insertCase4(node);
            return;
        }
        node.getParent().setBlack();
        node.uncle().setBlack();
        node.grandparent().setRed();
        insertCase1(node.grandparent());
    }

    private void insertCase4(Node<K> node) {
        if (node == node.getParent().getRight() && node.getParent() == node.grandparent().getLeft()) {
            rotateLeft(node.getParent());
            node = node.getLeft();
        } else if (node == node.getParent().getLeft() && node.getParent() == node.grandparent().getRight()) {
            rotateRight(node.getParent());
            node = node.getRight();
        }
        insertCase5(node);
    }

    private void insertCase5(Node<K> node) {
        node.getParent().setBlack();
        node.grandparent().setRed();
        if (node == node.getParent().getLeft() && node.getParent() == node.grandparent().getLeft()) {
            rotateRight(node.grandparent());
        } else {
            rotateLeft(node.grandparent());
        }
    }

    protected void deleteNode(Node<K> node) {
        if (node == null) {
            $$$reportNull$$$0(3);
        }
        incModCount();
        for (Node<K> parent = node; parent.getParent() != null; parent = parent.getParent()) {
        }
        if (node.getLeft() != null && node.getRight() != null) {
            node = swapWithMaxPred(node, maximumNode(node.getLeft()));
        }
        Node<K> left = node.getRight() == null ? node.getLeft() : node.getRight();
        if (isBlack(node)) {
            node.setColor(isBlack(left));
            deleteCase1(node);
        }
        replaceNode(node, left);
        if (!isBlack(this.root)) {
            this.root.setBlack();
        }
        this.nodeSize--;
        verifyProperties();
    }

    protected Node<K> maximumNode(Node<K> node) {
        if (node == null) {
            $$$reportNull$$$0(4);
        }
        while (node.getRight() != null) {
            node = node.getRight();
        }
        if (node == null) {
            $$$reportNull$$$0(5);
        }
        return node;
    }

    private void deleteCase1(Node<K> node) {
        if (node.getParent() != null) {
            deleteCase2(node);
        }
    }

    private void deleteCase2(Node<K> node) {
        if (!isBlack(node.sibling())) {
            node.getParent().setRed();
            node.sibling().setBlack();
            if (node == node.getParent().getLeft()) {
                rotateLeft(node.getParent());
            } else {
                rotateRight(node.getParent());
            }
        }
        deleteCase3(node);
    }

    private void deleteCase3(Node<K> node) {
        if (isBlack(node.getParent()) && isBlack(node.sibling()) && isBlack(node.sibling().getLeft()) && isBlack(node.sibling().getRight())) {
            node.sibling().setRed();
            deleteCase1(node.getParent());
        } else {
            deleteCase4(node);
        }
    }

    private void deleteCase4(Node<K> node) {
        if (!isBlack(node.getParent()) && isBlack(node.sibling()) && isBlack(node.sibling().getLeft()) && isBlack(node.sibling().getRight())) {
            node.sibling().setRed();
            node.getParent().setBlack();
        } else {
            deleteCase5(node);
        }
    }

    private void deleteCase5(Node<K> node) {
        if (node == node.getParent().getLeft() && isBlack(node.sibling()) && !isBlack(node.sibling().getLeft()) && isBlack(node.sibling().getRight())) {
            node.sibling().setRed();
            node.sibling().getLeft().setBlack();
            rotateRight(node.sibling());
        } else if (node == node.getParent().getRight() && isBlack(node.sibling()) && !isBlack(node.sibling().getRight()) && isBlack(node.sibling().getLeft())) {
            node.sibling().setRed();
            node.sibling().getRight().setBlack();
            rotateLeft(node.sibling());
        }
        deleteCase6(node);
    }

    private void deleteCase6(Node<K> node) {
        node.sibling().setColor(isBlack(node.getParent()));
        node.getParent().setBlack();
        if (node == node.getParent().getLeft()) {
            node.sibling().getRight().setBlack();
            rotateLeft(node.getParent());
        } else {
            node.sibling().getLeft().setBlack();
            rotateRight(node.getParent());
        }
    }

    public static abstract class Node<K> {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        protected Node<K> left;
        private volatile byte myFlags;
        protected Node<K> parent;
        protected Node<K> right;

        boolean isFlagSet(byte b) {
            return BitUtil.isSet(this.myFlags, b);
        }

        void setFlag(byte b, boolean z) {
            this.myFlags = BitUtil.set(this.myFlags, b, z);
        }

        public Node<K> grandparent() {
            return getParent().getParent();
        }

        public Node<K> sibling() {
            Node<K> parent = getParent();
            return this == parent.getLeft() ? parent.getRight() : parent.getLeft();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public Node<K> uncle() {
            return getParent().sibling();
        }

        public Node<K> getLeft() {
            return this.left;
        }

        public void setLeft(Node<K> node) {
            this.left = node;
        }

        public Node<K> getRight() {
            return this.right;
        }

        public void setRight(Node<K> node) {
            this.right = node;
        }

        public Node<K> getParent() {
            return this.parent;
        }

        public void setParent(Node<K> node) {
            this.parent = node;
        }

        public boolean isBlack() {
            return isFlagSet((byte) 1);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setBlack() {
            setFlag((byte) 1, true);
        }

        void setRed() {
            setFlag((byte) 1, false);
        }

        public void setColor(boolean z) {
            setFlag((byte) 1, z);
        }
    }

    public int size() {
        return this.nodeSize;
    }

    int nodeSize() {
        return this.nodeSize;
    }

    void verifyProperties() {
        if (VERIFY) {
            verifyProperty1(this.root);
            verifyProperty2(this.root);
            verifyProperty4(this.root);
            verifyProperty5(this.root);
        }
    }

    private static void verifyProperty1(Node<?> node) {
        if (node == null) {
            return;
        }
        assertParentChild(node);
        verifyProperty1(node.getLeft());
        verifyProperty1(node.getRight());
    }

    private static boolean isBlack(Node<?> node) {
        return node == null || node.isBlack();
    }

    private static void verifyProperty4(Node<?> node) {
        isBlack(node);
        if (node == null) {
            return;
        }
        verifyProperty4(node.getLeft());
        verifyProperty4(node.getRight());
    }

    private static void verifyProperty5(Node<?> node) {
        verifyProperty5Helper(node, 0, -1);
    }

    private static int verifyProperty5Helper(Node<?> node, int i, int i2) {
        if (isBlack(node)) {
            i++;
        }
        if (node == null) {
            return i2 == -1 ? i : i2;
        }
        return verifyProperty5Helper(node.getRight(), i, verifyProperty5Helper(node.getLeft(), i, i2));
    }
}
