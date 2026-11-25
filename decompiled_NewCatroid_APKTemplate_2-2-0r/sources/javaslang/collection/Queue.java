package javaslang.collection;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.Serializable;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Function;
import java.util.function.Predicate;
import javaslang.Tuple;
import javaslang.Tuple2;

/* JADX INFO: loaded from: classes11.dex */
public class Queue<T> implements Serializable, LinearSeq<T> {
    private static final Queue<?> EMPTY = new Queue<>(List.empty(), List.empty());
    private static final long serialVersionUID = 1;
    private final List<T> front;
    private final List<T> rear;

    @Override // javaslang.collection.Traversable
    public boolean isTraversableAgain() {
        return true;
    }

    private Queue(List<T> list, List<T> list2) {
        boolean zIsEmpty = list.isEmpty();
        this.front = zIsEmpty ? list2.reverse() : list;
        this.rear = zIsEmpty ? list : list2;
    }

    public static <T> Queue<T> empty() {
        return (Queue<T>) EMPTY;
    }

    public static <T> Queue<T> of(T t) {
        return ofAll(List.of(t));
    }

    public static <T> Queue<T> ofAll(Iterable<? extends T> iterable) {
        Objects.requireNonNull(iterable, "elements is null");
        if (iterable instanceof Queue) {
            return (Queue) iterable;
        }
        if (!iterable.iterator().hasNext()) {
            return empty();
        }
        if (iterable instanceof List) {
            return new Queue<>((List) iterable, List.empty());
        }
        return new Queue<>(List.ofAll(iterable), List.empty());
    }

    public Tuple2<T, Queue<T>> dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("dequeue of empty Queue");
        }
        return Tuple.of(head(), tail());
    }

    public Queue<T> enqueue(T t) {
        return new Queue<>(this.front, this.rear.prepend(t));
    }

    public Queue<T> enqueueAll(Iterable<? extends T> iterable) {
        Objects.requireNonNull(iterable, "elements is null");
        List<T> listPrepend = this.rear;
        java.util.Iterator<? extends T> it = iterable.iterator();
        while (it.hasNext()) {
            listPrepend = listPrepend.prepend(it.next());
        }
        return new Queue<>(this.front, listPrepend);
    }

    public Queue<T> append(T t) {
        return enqueue(t);
    }

    public Queue<T> appendAll(Iterable<? extends T> iterable) {
        Objects.requireNonNull(iterable, "elements is null");
        return enqueueAll(iterable);
    }

    public Queue<T> filter(Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate, "predicate is null");
        List<T> listFilter = toList().filter(predicate);
        return listFilter.length() == length() ? this : listFilter.toQueue();
    }

    @Override // javaslang.collection.Seq
    public T get(int i) {
        if (isEmpty()) {
            throw new IndexOutOfBoundsException("get(" + i + ") on empty Queue");
        }
        if (i < 0) {
            throw new IndexOutOfBoundsException("get(" + i + Constant.AFTER_QUTO);
        }
        int length = this.front.length();
        if (i < length) {
            return this.front.get(i);
        }
        int i2 = i - length;
        int length2 = this.rear.length();
        if (i2 < length2) {
            return this.rear.get((length2 - i2) - 1);
        }
        throw new IndexOutOfBoundsException(String.format("get(%s) on Queue of length %s", Integer.valueOf(i), Integer.valueOf(length())));
    }

    @Override // javaslang.collection.Traversable
    public T head() {
        if (isEmpty()) {
            throw new NoSuchElementException("head of empty queue");
        }
        return this.front.head();
    }

    public Queue<T> init() {
        if (isEmpty()) {
            throw new UnsupportedOperationException("init of empty Queue");
        }
        if (this.rear.isEmpty()) {
            return new Queue<>(this.front.init(), this.rear);
        }
        return new Queue<>(this.front, this.rear.tail());
    }

    @Override // javaslang.collection.Traversable, javaslang.Value
    public boolean isEmpty() {
        return this.front.isEmpty();
    }

    @Override // javaslang.collection.Traversable
    public int length() {
        return this.front.length() + this.rear.length();
    }

    @Override // javaslang.collection.LinearSeq, javaslang.collection.Seq
    public <U> Queue<U> map(Function<? super T, ? extends U> function) {
        Objects.requireNonNull(function, "mapper is null");
        return new Queue<>(this.front.map((Function) function), this.rear.map((Function) function));
    }

    public Queue<T> remove(T t) {
        List<T> listRemove = toList().remove(t);
        return listRemove.length() == length() ? this : listRemove.toQueue();
    }

    public Queue<T> removeFirst(Predicate<T> predicate) {
        List<T> listRemoveFirst = toList().removeFirst(predicate);
        return listRemoveFirst.length() == length() ? this : listRemoveFirst.toQueue();
    }

    public Queue<T> replace(T t, T t2) {
        List<T> listReplace = this.front.replace(t, t2);
        List<T> listReplace2 = this.rear.replace(t, t2);
        if (listReplace.size() + listReplace2.size() == 0) {
            return empty();
        }
        return (listReplace == this.front && listReplace2 == this.rear) ? this : new Queue<>(listReplace, listReplace2);
    }

    @Override // java.lang.Iterable
    public Spliterator<T> spliterator() {
        return Spliterators.spliterator(iterator(), length(), 1040);
    }

    @Override // javaslang.collection.LinearSeq, javaslang.collection.Seq, javaslang.collection.Traversable
    public Queue<T> tail() {
        if (isEmpty()) {
            throw new UnsupportedOperationException("tail of empty Queue");
        }
        return new Queue<>(this.front.tail(), this.rear);
    }

    private Object readResolve() {
        return isEmpty() ? EMPTY : this;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof Queue) {
            return toList().equals(((Queue) obj).toList());
        }
        return false;
    }

    public int hashCode() {
        return toList().hashCode();
    }

    @Override // javaslang.Value
    public String stringPrefix() {
        return "Queue";
    }

    @Override // javaslang.Value
    public String toString() {
        return mkString(stringPrefix() + "(", ", ", Constant.AFTER_QUTO);
    }
}
