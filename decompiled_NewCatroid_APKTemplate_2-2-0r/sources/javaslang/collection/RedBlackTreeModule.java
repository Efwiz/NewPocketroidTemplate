package javaslang.collection;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.Serializable;
import java.util.Comparator;
import javaslang.Tuple;
import javaslang.Tuple2;
import javaslang.Tuple3;

/* JADX INFO: compiled from: RedBlackTree.java */
/* JADX INFO: loaded from: classes11.dex */
interface RedBlackTreeModule {

    /* JADX INFO: compiled from: RedBlackTree.java */
    public static final class Node<T> implements Serializable, RedBlackTree<T> {
        private static final long serialVersionUID = 1;
        final int blackHeight;
        final RedBlackTree.Color color;
        final Empty<T> empty;
        final RedBlackTree<T> left;
        final RedBlackTree<T> right;
        final int size;
        final T value;

        @Override // javaslang.collection.RedBlackTree
        public boolean isEmpty() {
            return false;
        }

        Node(RedBlackTree.Color color, int i, RedBlackTree<T> redBlackTree, T t, RedBlackTree<T> redBlackTree2, Empty<T> empty) {
            this.color = color;
            this.blackHeight = i;
            this.left = redBlackTree;
            this.value = t;
            this.right = redBlackTree2;
            this.empty = empty;
            this.size = redBlackTree.size() + redBlackTree2.size() + 1;
        }

        @Override // javaslang.collection.RedBlackTree
        public Comparator<T> comparator() {
            return this.empty.comparator;
        }

        @Override // javaslang.collection.RedBlackTree
        public boolean contains(T t) {
            int iCompare = this.empty.comparator.compare(t, this.value);
            if (iCompare < 0) {
                return this.left.contains(t);
            }
            if (iCompare > 0) {
                return this.right.contains(t);
            }
            return true;
        }

        @Override // javaslang.collection.RedBlackTree
        public int size() {
            return this.size;
        }

        @Override // javaslang.collection.RedBlackTree
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj instanceof Node) {
                return Collections.equals(this, (Node) obj);
            }
            return false;
        }

        @Override // javaslang.collection.RedBlackTree
        public int hashCode() {
            return Collections.hash(this);
        }

        @Override // javaslang.collection.RedBlackTree
        public String toString() {
            return isLeaf() ? "(" + this.color + ":" + this.value + Constant.AFTER_QUTO : toLispString(this);
        }

        private static String toLispString(RedBlackTree<?> redBlackTree) {
            if (redBlackTree.isEmpty()) {
                return "";
            }
            Node node = (Node) redBlackTree;
            String str = node.color + ":" + node.value;
            if (node.isLeaf()) {
                return str;
            }
            return "(" + str + (node.left.isEmpty() ? "" : " " + toLispString(node.left)) + (node.right.isEmpty() ? "" : " " + toLispString(node.right)) + Constant.AFTER_QUTO;
        }

        private boolean isLeaf() {
            return this.left.isEmpty() && this.right.isEmpty();
        }

        Node<T> color(RedBlackTree.Color color) {
            return this.color == color ? this : new Node<>(color, this.blackHeight, this.left, this.value, this.right, this.empty);
        }

        static <T> RedBlackTree<T> color(RedBlackTree<T> redBlackTree, RedBlackTree.Color color) {
            return redBlackTree.isEmpty() ? redBlackTree : ((Node) redBlackTree).color(color);
        }

        private static <T> Node<T> balanceLeft(RedBlackTree.Color color, int i, RedBlackTree<T> redBlackTree, T t, RedBlackTree<T> redBlackTree2, Empty<T> empty) {
            if (color == RedBlackTree.Color.BLACK && !redBlackTree.isEmpty()) {
                Node node = (Node) redBlackTree;
                if (node.color == RedBlackTree.Color.RED) {
                    if (!node.left.isEmpty()) {
                        Node node2 = (Node) node.left;
                        if (node2.color == RedBlackTree.Color.RED) {
                            return new Node<>(RedBlackTree.Color.RED, i + 1, new Node(RedBlackTree.Color.BLACK, i, node2.left, node2.value, node2.right, empty), node.value, new Node(RedBlackTree.Color.BLACK, i, node.right, t, redBlackTree2, empty), empty);
                        }
                    }
                    if (!node.right.isEmpty()) {
                        Node node3 = (Node) node.right;
                        if (node3.color == RedBlackTree.Color.RED) {
                            return new Node<>(RedBlackTree.Color.RED, i + 1, new Node(RedBlackTree.Color.BLACK, i, node.left, node.value, node3.left, empty), node3.value, new Node(RedBlackTree.Color.BLACK, i, node3.right, t, redBlackTree2, empty), empty);
                        }
                    }
                }
            }
            return new Node<>(color, i, redBlackTree, t, redBlackTree2, empty);
        }

        private static <T> Node<T> balanceRight(RedBlackTree.Color color, int i, RedBlackTree<T> redBlackTree, T t, RedBlackTree<T> redBlackTree2, Empty<T> empty) {
            if (color == RedBlackTree.Color.BLACK && !redBlackTree2.isEmpty()) {
                Node node = (Node) redBlackTree2;
                if (node.color == RedBlackTree.Color.RED) {
                    if (!node.right.isEmpty()) {
                        Node node2 = (Node) node.right;
                        if (node2.color == RedBlackTree.Color.RED) {
                            return new Node<>(RedBlackTree.Color.RED, i + 1, new Node(RedBlackTree.Color.BLACK, i, redBlackTree, t, node.left, empty), node.value, new Node(RedBlackTree.Color.BLACK, i, node2.left, node2.value, node2.right, empty), empty);
                        }
                    }
                    if (!node.left.isEmpty()) {
                        Node node3 = (Node) node.left;
                        if (node3.color == RedBlackTree.Color.RED) {
                            return new Node<>(RedBlackTree.Color.RED, i + 1, new Node(RedBlackTree.Color.BLACK, i, redBlackTree, t, node3.left, empty), node3.value, new Node(RedBlackTree.Color.BLACK, i, node3.right, node.value, node.right, empty), empty);
                        }
                    }
                }
            }
            return new Node<>(color, i, redBlackTree, t, redBlackTree2, empty);
        }

        private static <T> Tuple2<? extends RedBlackTree<T>, Boolean> blackify(RedBlackTree<T> redBlackTree) {
            if (redBlackTree instanceof Node) {
                Node node = (Node) redBlackTree;
                if (node.color == RedBlackTree.Color.RED) {
                    return Tuple.of(node.color(RedBlackTree.Color.BLACK), false);
                }
            }
            return Tuple.of(redBlackTree, true);
        }

        /* JADX WARN: Multi-variable type inference failed */
        static <T> Tuple2<? extends RedBlackTree<T>, Boolean> delete(RedBlackTree<T> redBlackTree, T t) {
            if (redBlackTree.isEmpty()) {
                return Tuple.of(redBlackTree, false);
            }
            Node node = (Node) redBlackTree;
            int iCompare = node.comparator().compare(t, node.value);
            if (iCompare < 0) {
                Tuple2 tuple2Delete = delete(node.left, t);
                RedBlackTree redBlackTree2 = (RedBlackTree) tuple2Delete._1;
                if (((Boolean) tuple2Delete._2).booleanValue()) {
                    return unbalancedRight(node.color, node.blackHeight - 1, redBlackTree2, node.value, node.right, node.empty);
                }
                return Tuple.of(new Node(node.color, node.blackHeight, redBlackTree2, node.value, node.right, node.empty), false);
            }
            if (iCompare > 0) {
                Tuple2 tuple2Delete2 = delete(node.right, t);
                RedBlackTree redBlackTree3 = (RedBlackTree) tuple2Delete2._1;
                if (((Boolean) tuple2Delete2._2).booleanValue()) {
                    return unbalancedLeft(node.color, node.blackHeight - 1, node.left, node.value, redBlackTree3, node.empty);
                }
                return Tuple.of(new Node(node.color, node.blackHeight, node.left, node.value, redBlackTree3, node.empty), false);
            }
            if (node.right.isEmpty()) {
                if (node.color != RedBlackTree.Color.BLACK) {
                    return Tuple.of(node.left, false);
                }
                return blackify(node.left);
            }
            Tuple3 tuple3DeleteMin = deleteMin((Node) node.right);
            RedBlackTree redBlackTree4 = (RedBlackTree) tuple3DeleteMin._1;
            boolean zBooleanValue = ((Boolean) tuple3DeleteMin._2).booleanValue();
            T3 t3 = tuple3DeleteMin._3;
            if (zBooleanValue) {
                return unbalancedLeft(node.color, node.blackHeight - 1, node.left, t3, redBlackTree4, node.empty);
            }
            return Tuple.of(new Node(node.color, node.blackHeight, node.left, t3, redBlackTree4, node.empty), false);
        }

        /* JADX WARN: Multi-variable type inference failed */
        private static <T> Tuple3<? extends RedBlackTree<T>, Boolean, T> deleteMin(Node<T> node) {
            if (node.left.isEmpty()) {
                if (node.color == RedBlackTree.Color.BLACK) {
                    if (node.right.isEmpty()) {
                        return Tuple.of(node.empty, true, node.value);
                    }
                    return Tuple.of(((Node) node.right).color(RedBlackTree.Color.BLACK), false, node.value);
                }
                return Tuple.of(node.right, false, node.value);
            }
            Tuple3 tuple3DeleteMin = deleteMin((Node) node.left);
            RedBlackTree redBlackTree = (RedBlackTree) tuple3DeleteMin._1;
            boolean zBooleanValue = ((Boolean) tuple3DeleteMin._2).booleanValue();
            T3 t3 = tuple3DeleteMin._3;
            if (zBooleanValue) {
                Tuple2 tuple2UnbalancedRight = unbalancedRight(node.color, node.blackHeight - 1, redBlackTree, node.value, node.right, node.empty);
                return Tuple.of(tuple2UnbalancedRight._1, tuple2UnbalancedRight._2, t3);
            }
            return Tuple.of(new Node(node.color, node.blackHeight, redBlackTree, node.value, node.right, node.empty), false, t3);
        }

        static <T> Node<T> insert(RedBlackTree<T> redBlackTree, T t) {
            if (redBlackTree.isEmpty()) {
                Empty empty = (Empty) redBlackTree;
                return new Node<>(RedBlackTree.Color.RED, 1, empty, t, empty, empty);
            }
            Node<T> node = (Node) redBlackTree;
            int iCompare = node.comparator().compare(t, node.value);
            if (iCompare < 0) {
                Node nodeInsert = insert(node.left, t);
                return nodeInsert == node.left ? node : balanceLeft(node.color, node.blackHeight, nodeInsert, node.value, node.right, node.empty);
            }
            if (iCompare > 0) {
                Node nodeInsert2 = insert(node.right, t);
                return nodeInsert2 == node.right ? node : balanceRight(node.color, node.blackHeight, node.left, node.value, nodeInsert2, node.empty);
            }
            return new Node<>(node.color, node.blackHeight, node.left, t, node.right, node.empty);
        }

        private static boolean isRed(RedBlackTree<?> redBlackTree) {
            return !redBlackTree.isEmpty() && ((Node) redBlackTree).color == RedBlackTree.Color.RED;
        }

        static <T> RedBlackTree<T> join(RedBlackTree<T> redBlackTree, T t, RedBlackTree<T> redBlackTree2) {
            if (redBlackTree.isEmpty()) {
                return redBlackTree2.insert(t);
            }
            if (redBlackTree2.isEmpty()) {
                return redBlackTree.insert(t);
            }
            Node node = (Node) redBlackTree;
            Node node2 = (Node) redBlackTree2;
            int i = node.blackHeight;
            int i2 = node2.blackHeight;
            int i3 = i - i2;
            if (i3 < 0) {
                return joinLT(node, t, node2, i).color(RedBlackTree.Color.BLACK);
            }
            if (i3 > 0) {
                return joinGT(node, t, node2, i2).color(RedBlackTree.Color.BLACK);
            }
            return new Node(RedBlackTree.Color.BLACK, node.blackHeight + 1, node, t, node2, node.empty);
        }

        private static <T> Node<T> joinGT(Node<T> node, T t, Node<T> node2, int i) {
            if (node.blackHeight == i) {
                return new Node<>(RedBlackTree.Color.RED, i + 1, node, t, node2, node.empty);
            }
            return balanceRight(node.color, node.blackHeight, node.left, node.value, joinGT((Node) node.right, t, node2, i), node2.empty);
        }

        private static <T> Node<T> joinLT(Node<T> node, T t, Node<T> node2, int i) {
            if (node2.blackHeight == i) {
                return new Node<>(RedBlackTree.Color.RED, i + 1, node, t, node2, node.empty);
            }
            return balanceLeft(node2.color, node2.blackHeight, joinLT(node, t, (Node) node2.left, i), node2.value, node2.right, node2.empty);
        }

        static <T> RedBlackTree<T> merge(RedBlackTree<T> redBlackTree, RedBlackTree<T> redBlackTree2) {
            if (redBlackTree.isEmpty()) {
                return redBlackTree2;
            }
            if (redBlackTree2.isEmpty()) {
                return redBlackTree;
            }
            Node node = (Node) redBlackTree;
            Node node2 = (Node) redBlackTree2;
            int i = node.blackHeight;
            int i2 = node2.blackHeight;
            int i3 = i - i2;
            if (i3 < 0) {
                return color(mergeLT(node, node2, i), RedBlackTree.Color.BLACK);
            }
            if (i3 > 0) {
                return color(mergeGT(node, node2, i2), RedBlackTree.Color.BLACK);
            }
            return color(mergeEQ(node, node2), RedBlackTree.Color.BLACK);
        }

        private static <T> Node<T> mergeEQ(Node<T> node, Node<T> node2) {
            Object objMinimum = minimum(node2);
            RedBlackTree redBlackTree = (RedBlackTree) deleteMin(node2)._1;
            if (node.blackHeight == (redBlackTree.isEmpty() ? 0 : ((Node) redBlackTree).blackHeight)) {
                return new Node<>(RedBlackTree.Color.RED, node.blackHeight + 1, node, objMinimum, redBlackTree, node.empty);
            }
            if (isRed(node.left)) {
                return new Node<>(RedBlackTree.Color.RED, node.blackHeight, color(node.left, RedBlackTree.Color.BLACK), node.value, new Node(RedBlackTree.Color.BLACK, node.blackHeight, node.right, objMinimum, redBlackTree, node.empty), node.empty);
            }
            if (isRed(node.right)) {
                RedBlackTree<T> redBlackTree2 = node.right;
                RedBlackTree<T> redBlackTree3 = ((Node) redBlackTree2).left;
                return new Node<>(RedBlackTree.Color.BLACK, node.blackHeight, new Node(RedBlackTree.Color.RED, node.blackHeight, node.left, node.value, redBlackTree3, node.empty), ((Node) redBlackTree2).value, new Node(RedBlackTree.Color.RED, node.blackHeight, ((Node) redBlackTree2).right, objMinimum, redBlackTree, node.empty), node.empty);
            }
            return new Node<>(RedBlackTree.Color.BLACK, node.blackHeight, node.color(RedBlackTree.Color.RED), objMinimum, redBlackTree, node.empty);
        }

        private static <T> Node<T> mergeGT(Node<T> node, Node<T> node2, int i) {
            if (node.blackHeight == i) {
                return mergeEQ(node, node2);
            }
            return balanceRight(node.color, node.blackHeight, node.left, node.value, mergeGT((Node) node.right, node2, i), node.empty);
        }

        private static <T> Node<T> mergeLT(Node<T> node, Node<T> node2, int i) {
            if (node2.blackHeight == i) {
                return mergeEQ(node, node2);
            }
            return balanceLeft(node2.color, node2.blackHeight, mergeLT(node, (Node) node2.left, i), node2.value, node2.right, node2.empty);
        }

        static <T> T maximum(Node<T> node) {
            while (!node.right.isEmpty()) {
                node = (Node) node.right;
            }
            return node.value;
        }

        static <T> T minimum(Node<T> node) {
            while (!node.left.isEmpty()) {
                node = (Node) node.left;
            }
            return node.value;
        }

        static <T> Tuple2<RedBlackTree<T>, RedBlackTree<T>> split(RedBlackTree<T> redBlackTree, T t) {
            if (redBlackTree.isEmpty()) {
                return Tuple.of(redBlackTree, redBlackTree);
            }
            Node node = (Node) redBlackTree;
            int iCompare = node.comparator().compare(t, node.value);
            if (iCompare < 0) {
                Tuple2 tuple2Split = split(node.left, t);
                return Tuple.of(tuple2Split._1, join((RedBlackTree) tuple2Split._2, node.value, color(node.right, RedBlackTree.Color.BLACK)));
            }
            if (iCompare > 0) {
                Tuple2 tuple2Split2 = split(node.right, t);
                return Tuple.of(join(color(node.left, RedBlackTree.Color.BLACK), node.value, (RedBlackTree) tuple2Split2._1), tuple2Split2._2);
            }
            return Tuple.of(color(node.left, RedBlackTree.Color.BLACK), color(node.right, RedBlackTree.Color.BLACK));
        }

        private static <T> Tuple2<Node<T>, Boolean> unbalancedLeft(RedBlackTree.Color color, int i, RedBlackTree<T> redBlackTree, T t, RedBlackTree<T> redBlackTree2, Empty<T> empty) {
            if (!redBlackTree.isEmpty()) {
                Node node = (Node) redBlackTree;
                if (node.color == RedBlackTree.Color.BLACK) {
                    return Tuple.of(balanceLeft(RedBlackTree.Color.BLACK, i, node.color(RedBlackTree.Color.RED), t, redBlackTree2, empty), Boolean.valueOf(color == RedBlackTree.Color.BLACK));
                }
                if (color == RedBlackTree.Color.BLACK && !node.right.isEmpty()) {
                    Node node2 = (Node) node.right;
                    if (node2.color == RedBlackTree.Color.BLACK) {
                        return Tuple.of(new Node(RedBlackTree.Color.BLACK, node.blackHeight, node.left, node.value, balanceLeft(RedBlackTree.Color.BLACK, i, node2.color(RedBlackTree.Color.RED), t, redBlackTree2, empty), empty), false);
                    }
                }
            }
            throw new IllegalStateException(String.format("unbalancedLeft(%s, %s, %s, %s, %s)", color, Integer.valueOf(i), redBlackTree, t, redBlackTree2));
        }

        private static <T> Tuple2<Node<T>, Boolean> unbalancedRight(RedBlackTree.Color color, int i, RedBlackTree<T> redBlackTree, T t, RedBlackTree<T> redBlackTree2, Empty<T> empty) {
            if (!redBlackTree2.isEmpty()) {
                Node node = (Node) redBlackTree2;
                if (node.color == RedBlackTree.Color.BLACK) {
                    return Tuple.of(balanceRight(RedBlackTree.Color.BLACK, i, redBlackTree, t, node.color(RedBlackTree.Color.RED), empty), Boolean.valueOf(color == RedBlackTree.Color.BLACK));
                }
                if (color == RedBlackTree.Color.BLACK && !node.left.isEmpty()) {
                    Node node2 = (Node) node.left;
                    if (node2.color == RedBlackTree.Color.BLACK) {
                        return Tuple.of(new Node(RedBlackTree.Color.BLACK, node.blackHeight, balanceRight(RedBlackTree.Color.BLACK, i, redBlackTree, t, node2.color(RedBlackTree.Color.RED), empty), node.value, node.right, empty), false);
                    }
                }
            }
            throw new IllegalStateException(String.format("unbalancedRight(%s, %s, %s, %s, %s)", color, Integer.valueOf(i), redBlackTree, t, redBlackTree2));
        }
    }

    /* JADX INFO: compiled from: RedBlackTree.java */
    public static final class Empty<T> implements Serializable, RedBlackTree<T> {
        private static final long serialVersionUID = 1;
        final Comparator<T> comparator;

        @Override // javaslang.collection.RedBlackTree
        public boolean contains(T t) {
            return false;
        }

        @Override // javaslang.collection.RedBlackTree
        public int hashCode() {
            return 1;
        }

        @Override // javaslang.collection.RedBlackTree
        public boolean isEmpty() {
            return true;
        }

        @Override // javaslang.collection.RedBlackTree
        public int size() {
            return 0;
        }

        /* JADX WARN: Multi-variable type inference failed */
        Empty(Comparator<? super T> comparator) {
            this.comparator = comparator;
        }

        @Override // javaslang.collection.RedBlackTree
        public Comparator<T> comparator() {
            return this.comparator;
        }

        @Override // javaslang.collection.RedBlackTree
        public boolean equals(Object obj) {
            return obj == this || (obj instanceof Empty);
        }

        @Override // javaslang.collection.RedBlackTree
        public String toString() {
            return "()";
        }
    }
}
