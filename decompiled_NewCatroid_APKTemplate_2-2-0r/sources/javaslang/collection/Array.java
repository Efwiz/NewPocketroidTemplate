package javaslang.collection;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.Serializable;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Function;

/* JADX INFO: loaded from: classes11.dex */
public final class Array<T> implements Serializable, IndexedSeq<T> {
    private static final Array<?> EMPTY = new Array<>(new Object[0]);
    private static final long serialVersionUID = 1;
    private final Object[] back;

    @Override // javaslang.collection.Traversable
    public boolean isTraversableAgain() {
        return true;
    }

    private Array(Object[] objArr) {
        this.back = objArr;
    }

    static <T> Array<T> wrap(Object[] objArr) {
        if (objArr.length == 0) {
            return empty();
        }
        return new Array<>(objArr);
    }

    public static <T> Array<T> empty() {
        return (Array<T>) EMPTY;
    }

    @Override // javaslang.collection.Traversable, javaslang.Value, java.lang.Iterable
    public Iterator<T> iterator() {
        return new AbstractIterator<T>() { // from class: javaslang.collection.Array.1
            private int index = 0;

            @Override // java.util.Iterator
            public boolean hasNext() {
                return this.index < Array.this.back.length;
            }

            @Override // javaslang.collection.AbstractIterator
            public T getNext() {
                Object[] objArr = Array.this.back;
                int i = this.index;
                this.index = i + 1;
                return (T) objArr[i];
            }
        };
    }

    @Override // javaslang.collection.Seq
    public T get(int i) {
        if (i < 0 || i >= length()) {
            throw new IndexOutOfBoundsException("get(" + i + Constant.AFTER_QUTO);
        }
        return (T) this.back[i];
    }

    @Override // javaslang.collection.Traversable
    public T head() {
        if (isEmpty()) {
            throw new NoSuchElementException("head() on empty Array");
        }
        return (T) this.back[0];
    }

    @Override // javaslang.collection.Traversable, javaslang.Value
    public boolean isEmpty() {
        return this.back.length == 0;
    }

    private Object readResolve() {
        return isEmpty() ? EMPTY : this;
    }

    @Override // javaslang.collection.Traversable
    public int length() {
        return this.back.length;
    }

    @Override // javaslang.collection.IndexedSeq, javaslang.collection.Seq
    public <U> Array<U> map(Function<? super T, ? extends U> function) {
        Objects.requireNonNull(function, "mapper is null");
        Object[] objArr = new Object[length()];
        for (int i = 0; i < this.back.length; i++) {
            objArr[i] = function.apply(get(i));
        }
        return wrap(objArr);
    }

    @Override // java.lang.Iterable
    public Spliterator<T> spliterator() {
        return Spliterators.spliterator(iterator(), length(), 1040);
    }

    @Override // javaslang.collection.IndexedSeq, javaslang.collection.Seq, javaslang.collection.Traversable
    public Array<T> tail() {
        if (isEmpty()) {
            throw new UnsupportedOperationException("tail() on empty Array");
        }
        Object[] objArr = this.back;
        int length = objArr.length - 1;
        Object[] objArr2 = new Object[length];
        System.arraycopy(objArr, 1, objArr2, 0, length);
        return wrap(objArr2);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof Array) {
            return Objects.deepEquals(this.back, ((Array) obj).back);
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.back);
    }

    @Override // javaslang.Value
    public String stringPrefix() {
        return "Array";
    }

    @Override // javaslang.Value
    public String toString() {
        return mkString(stringPrefix() + "(", ", ", Constant.AFTER_QUTO);
    }
}
