package org.jetbrains.kotlin.org.jline.reader.impl;

import java.util.function.Consumer;

/* JADX INFO: loaded from: classes4.dex */
public class UndoTree<T> {
    private UndoTree<T>.Node current;
    private final UndoTree<T>.Node parent;
    private final Consumer<T> state;

    public UndoTree(Consumer<T> consumer) {
        this.state = consumer;
        UndoTree<T>.Node node = new Node(null);
        this.parent = node;
        ((Node) node).left = node;
        clear();
    }

    public void clear() {
        this.current = this.parent;
    }

    public void newState(T t) {
        UndoTree<T>.Node node = new Node(t);
        ((Node) this.current).right = node;
        ((Node) node).left = this.current;
        this.current = node;
    }

    public boolean canUndo() {
        return ((Node) this.current).left != this.parent;
    }

    public boolean canRedo() {
        return ((Node) this.current).right != null;
    }

    public void undo() {
        if (!canUndo()) {
            throw new IllegalStateException("Cannot undo.");
        }
        UndoTree<T>.Node node = ((Node) this.current).left;
        this.current = node;
        this.state.accept((T) ((Node) node).state);
    }

    public void redo() {
        if (!canRedo()) {
            throw new IllegalStateException("Cannot redo.");
        }
        UndoTree<T>.Node node = ((Node) this.current).right;
        this.current = node;
        this.state.accept((T) ((Node) node).state);
    }

    private class Node {
        private UndoTree<T>.Node left = null;
        private UndoTree<T>.Node right = null;
        private final T state;

        public Node(T t) {
            this.state = t;
        }
    }
}
