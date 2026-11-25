package javaslang.collection;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.Serializable;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;
import javaslang.control.Option;

/* JADX INFO: loaded from: classes11.dex */
public final class TreeSet<T> implements Serializable, SortedSet<T> {
    private static final long serialVersionUID = 1;
    private final RedBlackTree<T> tree;

    @Override // javaslang.collection.Traversable
    public boolean isTraversableAgain() {
        return true;
    }

    TreeSet(RedBlackTree<T> redBlackTree) {
        this.tree = redBlackTree;
    }

    public static <T extends Comparable<? super T>> TreeSet<T> empty() {
        return new TreeSet<>(RedBlackTree.empty());
    }

    public static <T> TreeSet<T> empty(Comparator<? super T> comparator) {
        Objects.requireNonNull(comparator, "comparator is null");
        return new TreeSet<>(RedBlackTree.empty(comparator));
    }

    public static <T> TreeSet<T> ofAll(Comparator<? super T> comparator, Iterable<? extends T> iterable) {
        Objects.requireNonNull(comparator, "comparator is null");
        Objects.requireNonNull(iterable, "values is null");
        if (iterable.iterator().hasNext()) {
            return new TreeSet<>(RedBlackTree.ofAll(comparator, iterable));
        }
        return empty();
    }

    @Override // javaslang.collection.SortedSet, javaslang.collection.Set
    public TreeSet<T> add(T t) {
        return contains(t) ? this : new TreeSet<>(this.tree.insert(t));
    }

    public Comparator<T> comparator() {
        return this.tree.comparator();
    }

    @Override // javaslang.collection.Set
    public boolean contains(T t) {
        return this.tree.contains(t);
    }

    @Override // javaslang.collection.Traversable
    public T head() {
        if (isEmpty()) {
            throw new NoSuchElementException("head of empty TreeSet");
        }
        return this.tree.min().get();
    }

    @Override // javaslang.collection.SortedSet, javaslang.collection.Set
    public TreeSet<T> intersect(Set<? extends T> set) {
        Objects.requireNonNull(set, "elements is null");
        if (set instanceof TreeSet) {
            return new TreeSet<>(this.tree.intersection(((TreeSet) set).tree));
        }
        return retainAll(set);
    }

    @Override // javaslang.collection.Traversable, javaslang.Value
    public boolean isEmpty() {
        return this.tree.isEmpty();
    }

    @Override // javaslang.collection.Set, javaslang.collection.Traversable, javaslang.Value, java.lang.Iterable
    public Iterator<T> iterator() {
        return this.tree.iterator();
    }

    @Override // javaslang.collection.Set, javaslang.collection.Traversable
    public int length() {
        return this.tree.size();
    }

    public <U> TreeSet<U> map(Comparator<? super U> comparator, Function<? super T, ? extends U> function) {
        Objects.requireNonNull(function, "mapper is null");
        return ofAll(comparator, iterator().map(function));
    }

    @Override // javaslang.collection.SortedSet, javaslang.collection.Set
    public <U> TreeSet<U> map(Function<? super T, ? extends U> function) {
        return map(Comparators.naturalComparator(), function);
    }

    @Override // javaslang.collection.Traversable
    public Option<T> max() {
        return this.tree.max();
    }

    public TreeSet<T> retainAll(Iterable<? extends T> iterable) {
        Objects.requireNonNull(iterable, "elements is null");
        if (isEmpty()) {
            return this;
        }
        RedBlackTree<T> redBlackTreeOfAll = RedBlackTree.ofAll(this.tree.comparator(), iterable);
        return this.tree.intersection(redBlackTreeOfAll).size() == this.tree.size() ? this : new TreeSet<>(this.tree.intersection(redBlackTreeOfAll));
    }

    @Override // javaslang.collection.SortedSet, javaslang.collection.Set, javaslang.collection.Traversable
    public TreeSet<T> tail() {
        if (isEmpty()) {
            throw new UnsupportedOperationException("tail of empty TreeSet");
        }
        RedBlackTree<T> redBlackTree = this.tree;
        return new TreeSet<>(redBlackTree.delete(redBlackTree.min().get()));
    }

    /* JADX INFO: renamed from: lambda$toJavaSet$12$javaslang-collection-TreeSet, reason: not valid java name */
    /* synthetic */ java.util.TreeSet m2890lambda$toJavaSet$12$javaslangcollectionTreeSet() {
        return new java.util.TreeSet(comparator());
    }

    @Override // javaslang.collection.SortedSet, javaslang.collection.Set
    public java.util.TreeSet<T> toJavaSet() {
        return (java.util.TreeSet) toJavaSet(new Supplier() { // from class: javaslang.collection.TreeSet$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return this.f$0.m2890lambda$toJavaSet$12$javaslangcollectionTreeSet();
            }
        });
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof TreeSet) {
            return this.tree.equals(((TreeSet) obj).tree);
        }
        return false;
    }

    public int hashCode() {
        return this.tree.hashCode();
    }

    @Override // javaslang.Value
    public String stringPrefix() {
        return "TreeSet";
    }

    @Override // javaslang.Value
    public String toString() {
        return mkString(stringPrefix() + "(", ", ", Constant.AFTER_QUTO);
    }
}
