package com.badlogic.gdx.utils;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/utils/SortedIntList.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class SortedIntList<E> implements Iterable<Node<E>> {
    Node<E> first;
    private transient SortedIntList<E>.Iterator iterator;
    private NodePool<E> nodePool = new NodePool<>();
    int size = 0;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/utils/SortedIntList$Node.class
      classes8.dex
     */
    public static class Node<E> {
        public int index;
        protected Node<E> n;
        protected Node<E> p;
        public E value;
    }

    public E insert(int i, E e) {
        Node<E> node = this.first;
        if (node != null) {
            while (node.n != null && node.n.index <= i) {
                node = node.n;
            }
            if (i > node.index) {
                node.n = this.nodePool.obtain(node, node.n, e, i);
                if (node.n.n != null) {
                    node.n.n.p = node.n;
                }
                this.size++;
            } else if (i < node.index) {
                Node<E> nodeObtain = this.nodePool.obtain(null, this.first, e, i);
                this.first.p = nodeObtain;
                this.first = nodeObtain;
                this.size++;
            } else {
                node.value = e;
            }
        } else {
            this.first = this.nodePool.obtain(null, null, e, i);
            this.size++;
        }
        return null;
    }

    public E get(int i) {
        Node<E> node = this.first;
        if (node != null) {
            while (node.n != null && node.index < i) {
                node = node.n;
            }
            if (node.index == i) {
                return node.value;
            }
        }
        return null;
    }

    public void clear() {
        while (true) {
            Node<E> node = this.first;
            if (node != null) {
                this.nodePool.free(node);
                this.first = this.first.n;
            } else {
                this.size = 0;
                return;
            }
        }
    }

    public int size() {
        return this.size;
    }

    public boolean notEmpty() {
        return this.size > 0;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override // java.lang.Iterable
    public java.util.Iterator<Node<E>> iterator() {
        if (Collections.allocateIterators) {
            return new Iterator();
        }
        SortedIntList<E>.Iterator iterator = this.iterator;
        if (iterator != null) {
            return iterator.reset();
        }
        SortedIntList<E>.Iterator iterator2 = new Iterator();
        this.iterator = iterator2;
        return iterator2;
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/utils/SortedIntList$Iterator.class
      classes8.dex
     */
    public class Iterator implements java.util.Iterator<Node<E>> {
        private Node<E> position;
        private Node<E> previousPosition;

        public Iterator() {
            reset();
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.position != null;
        }

        @Override // java.util.Iterator
        public Node<E> next() {
            Node<E> node = this.position;
            this.previousPosition = node;
            this.position = node.n;
            return this.previousPosition;
        }

        @Override // java.util.Iterator
        public void remove() {
            Node<E> node = this.previousPosition;
            if (node != null) {
                if (node == SortedIntList.this.first) {
                    SortedIntList.this.first = this.position;
                } else {
                    this.previousPosition.p.n = this.position;
                    Node<E> node2 = this.position;
                    if (node2 != null) {
                        node2.p = this.previousPosition.p;
                    }
                }
                SortedIntList.this.size--;
            }
        }

        public SortedIntList<E>.Iterator reset() {
            this.position = SortedIntList.this.first;
            this.previousPosition = null;
            return this;
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/utils/SortedIntList$NodePool.class
      classes8.dex
     */
    static class NodePool<E> extends Pool<Node<E>> {
        NodePool() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.badlogic.gdx.utils.Pool
        public Node<E> newObject() {
            return new Node<>();
        }

        public Node<E> obtain(Node<E> node, Node<E> node2, E e, int i) {
            Node<E> node3 = (Node) super.obtain();
            node3.p = node;
            node3.n = node2;
            node3.value = e;
            node3.index = i;
            return node3;
        }
    }
}
