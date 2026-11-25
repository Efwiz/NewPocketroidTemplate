package javaslang.collection;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.BiFunction;
import java.util.function.Function;

/* JADX INFO: loaded from: classes11.dex */
public interface Tree<T> extends Serializable, Traversable<T> {
    public static final long serialVersionUID = 1;

    public enum Order {
        PRE_ORDER,
        IN_ORDER,
        POST_ORDER,
        LEVEL_ORDER
    }

    List<Node<T>> getChildren();

    T getValue();

    boolean isLeaf();

    @Override // javaslang.collection.Traversable
    default boolean isTraversableAgain() {
        return true;
    }

    @Override // javaslang.collection.Traversable
    int size();

    @Override // javaslang.Value
    String toString();

    default Seq<Node<T>> traverse(Order order) {
        Objects.requireNonNull(order, "order is null");
        if (isEmpty()) {
            return Stream.empty();
        }
        Node node = (Node) this;
        int i = AnonymousClass1.$SwitchMap$javaslang$collection$Tree$Order[order.ordinal()];
        if (i == 1) {
            return TreeModule.Traversal.preOrder(node);
        }
        if (i == 2) {
            return TreeModule.Traversal.inOrder(node);
        }
        if (i == 3) {
            return TreeModule.Traversal.postOrder(node);
        }
        if (i == 4) {
            return TreeModule.Traversal.levelOrder(node);
        }
        throw new IllegalStateException("Unknown order: " + order.name());
    }

    /* JADX INFO: renamed from: javaslang.collection.Tree$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$javaslang$collection$Tree$Order;

        static {
            int[] iArr = new int[Order.values().length];
            $SwitchMap$javaslang$collection$Tree$Order = iArr;
            try {
                iArr[Order.PRE_ORDER.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$javaslang$collection$Tree$Order[Order.IN_ORDER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$javaslang$collection$Tree$Order[Order.POST_ORDER.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$javaslang$collection$Tree$Order[Order.LEVEL_ORDER.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    default Seq<T> values() {
        return (Seq<T>) traverse(Order.PRE_ORDER).map(new Function() { // from class: javaslang.collection.Tree$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((Tree.Node) obj).getValue();
            }
        });
    }

    @Override // javaslang.collection.Traversable
    default T head() {
        if (isEmpty()) {
            throw new NoSuchElementException("head of empty tree");
        }
        return iterator().next();
    }

    @Override // javaslang.collection.Traversable, javaslang.Value, java.lang.Iterable
    default Iterator<T> iterator() {
        return values().iterator();
    }

    @Override // javaslang.collection.Traversable
    default int length() {
        return size();
    }

    @Override // java.lang.Iterable
    default Spliterator<T> spliterator() {
        return Spliterators.spliterator(iterator(), length(), 1040);
    }

    @Override // javaslang.Value
    default String stringPrefix() {
        return "Tree";
    }

    @Override // javaslang.collection.Traversable
    default Seq<T> tail() {
        if (isEmpty()) {
            throw new UnsupportedOperationException("tail of empty tree");
        }
        return values().tail();
    }

    public static final class Node<T> implements Serializable, Tree<T> {
        private static final long serialVersionUID = 1;
        private final List<Node<T>> children;
        private final T value;

        @Override // javaslang.collection.Traversable, javaslang.Value
        public boolean isEmpty() {
            return false;
        }

        public Node(T t, List<Node<T>> list) {
            Objects.requireNonNull(list, "children is null");
            this.value = t;
            this.children = list;
        }

        @Override // javaslang.collection.Tree
        public List<Node<T>> getChildren() {
            return this.children;
        }

        @Override // javaslang.collection.Tree
        public T getValue() {
            return this.value;
        }

        @Override // javaslang.collection.Tree
        public boolean isLeaf() {
            return this.children.isEmpty();
        }

        @Override // javaslang.collection.Tree, javaslang.collection.Traversable
        public int size() {
            return ((Integer) this.children.foldLeft(0, new BiFunction() { // from class: javaslang.collection.Tree$Node$$ExternalSyntheticLambda1
                @Override // java.util.function.BiFunction
                public final Object apply(Object obj, Object obj2) {
                    return Integer.valueOf(((Integer) obj).intValue() + ((Tree.Node) obj2).length());
                }
            })).intValue() + 1;
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Node)) {
                return false;
            }
            Node node = (Node) obj;
            return Objects.equals(getValue(), node.getValue()) && Objects.equals(getChildren(), node.getChildren());
        }

        public int hashCode() {
            return Objects.hash(this.value, this.children);
        }

        @Override // javaslang.collection.Tree, javaslang.Value
        public String toString() {
            return stringPrefix() + (isLeaf() ? "(" + this.value + Constant.AFTER_QUTO : toLispString(this));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static String toLispString(Tree<?> tree) {
            String strValueOf = String.valueOf(tree.getValue());
            return tree.isLeaf() ? strValueOf : String.format("(%s %s)", strValueOf, tree.getChildren().map(new Function() { // from class: javaslang.collection.Tree$Node$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return Tree.Node.toLispString((Tree.Node) obj);
                }
            }).mkString(" "));
        }

        private Object writeReplace() {
            return new SerializationProxy(this);
        }

        private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
            throw new InvalidObjectException("Proxy required");
        }

        private static final class SerializationProxy<T> implements Serializable {
            private static final long serialVersionUID = 1;
            private transient Node<T> node;

            SerializationProxy(Node<T> node) {
                this.node = node;
            }

            private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
                objectOutputStream.defaultWriteObject();
                objectOutputStream.writeObject(((Node) this.node).value);
                objectOutputStream.writeObject(((Node) this.node).children);
            }

            private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
                objectInputStream.defaultReadObject();
                this.node = new Node<>(objectInputStream.readObject(), (List) objectInputStream.readObject());
            }

            private Object readResolve() {
                return this.node;
            }
        }
    }
}
