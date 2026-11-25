package javaslang.collection;

import java.util.Comparator;
import java.util.Objects;
import javaslang.Tuple2;
import javaslang.control.Option;

/* JADX INFO: loaded from: classes11.dex */
interface RedBlackTree<T> extends Iterable<T> {
    Comparator<T> comparator();

    boolean contains(T t);

    boolean equals(Object obj);

    int hashCode();

    boolean isEmpty();

    int size();

    String toString();

    static <T extends Comparable<? super T>> RedBlackTree<T> empty() {
        return new RedBlackTreeModule.Empty(Comparators.naturalComparator());
    }

    static <T> RedBlackTree<T> empty(Comparator<? super T> comparator) {
        Objects.requireNonNull(comparator, "comparator is null");
        return new RedBlackTreeModule.Empty(comparator);
    }

    static <T> RedBlackTree<T> ofAll(Comparator<? super T> comparator, Iterable<? extends T> iterable) {
        Objects.requireNonNull(comparator, "comparator is null");
        Objects.requireNonNull(iterable, "values is null");
        if (iterable instanceof RedBlackTree) {
            RedBlackTree<T> redBlackTree = (RedBlackTree) iterable;
            if (redBlackTree.comparator() == comparator) {
                return redBlackTree;
            }
        }
        RedBlackTree<T> redBlackTreeEmpty = empty(comparator);
        java.util.Iterator<? extends T> it = iterable.iterator();
        while (it.hasNext()) {
            redBlackTreeEmpty = redBlackTreeEmpty.insert(it.next());
        }
        return redBlackTreeEmpty;
    }

    default RedBlackTree<T> insert(T t) {
        return RedBlackTreeModule.Node.insert(this, t).color(Color.BLACK);
    }

    default RedBlackTree<T> delete(T t) {
        return RedBlackTreeModule.Node.color((RedBlackTree) RedBlackTreeModule.Node.delete(this, t)._1, Color.BLACK);
    }

    default RedBlackTree<T> intersection(RedBlackTree<T> redBlackTree) {
        Objects.requireNonNull(redBlackTree, "tree is null");
        if (isEmpty()) {
            return this;
        }
        if (redBlackTree.isEmpty()) {
            return redBlackTree;
        }
        RedBlackTreeModule.Node node = (RedBlackTreeModule.Node) redBlackTree;
        Tuple2 tuple2Split = RedBlackTreeModule.Node.split(this, node.value);
        if (contains(node.value)) {
            return RedBlackTreeModule.Node.join(((RedBlackTree) tuple2Split._1).intersection(node.left), node.value, ((RedBlackTree) tuple2Split._2).intersection(node.right));
        }
        return RedBlackTreeModule.Node.merge(((RedBlackTree) tuple2Split._1).intersection(node.left), ((RedBlackTree) tuple2Split._2).intersection(node.right));
    }

    default Option<T> max() {
        return isEmpty() ? Option.none() : Option.some(RedBlackTreeModule.Node.maximum((RedBlackTreeModule.Node) this));
    }

    default Option<T> min() {
        return isEmpty() ? Option.none() : Option.some(RedBlackTreeModule.Node.minimum((RedBlackTreeModule.Node) this));
    }

    @Override // java.lang.Iterable
    default Iterator<T> iterator() {
        if (isEmpty()) {
            return Iterator.empty();
        }
        return new AbstractIterator<T>((RedBlackTreeModule.Node) this) { // from class: javaslang.collection.RedBlackTree.1
            Stack<RedBlackTreeModule.Node<T>> stack;
            final /* synthetic */ RedBlackTreeModule.Node val$that;

            {
                this.val$that = node;
                this.stack = pushLeftChildren(List.empty(), node);
            }

            @Override // java.util.Iterator
            public boolean hasNext() {
                return !this.stack.isEmpty();
            }

            @Override // javaslang.collection.AbstractIterator
            public T getNext() {
                Tuple2<RedBlackTreeModule.Node<T>, ? extends Stack<RedBlackTreeModule.Node<T>>> tuple2Pop2 = this.stack.pop2();
                RedBlackTreeModule.Node<T> node = tuple2Pop2._1;
                this.stack = node.right.isEmpty() ? (Stack) tuple2Pop2._2 : pushLeftChildren((Stack) tuple2Pop2._2, (RedBlackTreeModule.Node) node.right);
                return tuple2Pop2._1.value;
            }

            private Stack<RedBlackTreeModule.Node<T>> pushLeftChildren(Stack<RedBlackTreeModule.Node<T>> stack, RedBlackTreeModule.Node<T> node) {
                while (!node.isEmpty()) {
                    RedBlackTreeModule.Node<T> node2 = node;
                    stack = stack.push(node2);
                    node = node2.left;
                }
                return stack;
            }
        };
    }

    public enum Color {
        RED,
        BLACK;

        @Override // java.lang.Enum
        public String toString() {
            return this == RED ? "R" : "B";
        }
    }
}
