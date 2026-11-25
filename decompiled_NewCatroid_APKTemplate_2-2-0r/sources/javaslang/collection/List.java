package javaslang.collection;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ListIterator;
import java.util.NavigableSet;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import javaslang.Tuple;
import javaslang.Tuple2;

/* JADX INFO: loaded from: classes11.dex */
public interface List<T> extends LinearSeq<T>, Stack<T> {
    public static final long serialVersionUID = 1;

    @Override // javaslang.collection.Traversable, javaslang.Value
    boolean isEmpty();

    @Override // javaslang.collection.Traversable
    default boolean isTraversableAgain() {
        return true;
    }

    @Override // javaslang.collection.Traversable
    int length();

    @Override // javaslang.collection.LinearSeq, javaslang.collection.Seq, javaslang.collection.Traversable
    List<T> tail();

    static <T> List<T> empty() {
        return Nil.instance();
    }

    static <T> List<T> of(T t) {
        return new Cons(t, Nil.instance());
    }

    /* JADX WARN: Multi-variable type inference failed */
    static <T> List<T> ofAll(Iterable<? extends T> iterable) {
        Objects.requireNonNull(iterable, "elements is null");
        if (iterable instanceof List) {
            return (List) iterable;
        }
        if (iterable instanceof java.util.List) {
            Nil nilInstance = Nil.instance();
            java.util.List list = (java.util.List) iterable;
            ListIterator listIterator = list.listIterator(list.size());
            while (listIterator.hasPrevious()) {
                nilInstance = (List<T>) ((List) nilInstance).prepend(listIterator.previous());
            }
            return (List<T>) nilInstance;
        }
        if (iterable instanceof NavigableSet) {
            Nil nilInstance2 = Nil.instance();
            java.util.Iterator itDescendingIterator = ((NavigableSet) iterable).descendingIterator();
            while (itDescendingIterator.hasNext()) {
                nilInstance2 = (List<T>) ((List) nilInstance2).prepend(itDescendingIterator.next());
            }
            return (List<T>) nilInstance2;
        }
        List listInstance = Nil.instance();
        java.util.Iterator<? extends T> it = iterable.iterator();
        while (it.hasNext()) {
            listInstance = listInstance.prepend(it.next());
        }
        return listInstance.reverse();
    }

    default List<T> dropRight(long j) {
        if (j <= 0) {
            return this;
        }
        if (j >= length()) {
            return empty();
        }
        return ofAll(iterator().dropRight(j));
    }

    default List<T> filter(final Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate, "predicate is null");
        List list = (List) foldLeft(empty(), new BiFunction() { // from class: javaslang.collection.List$$ExternalSyntheticLambda1
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return List.lambda$filter$4(predicate, (List) obj, obj2);
            }
        });
        return length() == list.length() ? this : list.reverse();
    }

    static /* synthetic */ List lambda$filter$4(Predicate predicate, List list, Object obj) {
        return predicate.test(obj) ? list.prepend(obj) : list;
    }

    @Override // javaslang.collection.Seq
    default T get(int i) {
        if (isEmpty()) {
            throw new IndexOutOfBoundsException("get(" + i + ") on Nil");
        }
        if (i < 0) {
            throw new IndexOutOfBoundsException("get(" + i + Constant.AFTER_QUTO);
        }
        List<T> listTail = this;
        for (int i2 = i - 1; i2 >= 0; i2--) {
            listTail = listTail.tail();
            if (listTail.isEmpty()) {
                throw new IndexOutOfBoundsException(String.format("get(%s) on List of length %s", Integer.valueOf(i), Integer.valueOf(i - i2)));
            }
        }
        return listTail.head();
    }

    default List<T> init() {
        if (isEmpty()) {
            throw new UnsupportedOperationException("init of empty list");
        }
        return dropRight(1L);
    }

    @Override // javaslang.collection.LinearSeq, javaslang.collection.Seq
    default <U> List<U> map(Function<? super T, ? extends U> function) {
        Objects.requireNonNull(function, "mapper is null");
        List listEmpty = empty();
        Iterator it = iterator();
        while (it.hasNext()) {
            listEmpty = listEmpty.prepend(function.apply(it.next()));
        }
        return listEmpty.reverse();
    }

    @Override // javaslang.collection.Stack
    default Tuple2<T, List<T>> pop2() {
        if (isEmpty()) {
            throw new NoSuchElementException("pop2 of empty list");
        }
        return Tuple.of(head(), tail());
    }

    default List<T> prepend(T t) {
        return new Cons(t, this);
    }

    @Override // javaslang.collection.Stack
    default List<T> push(T t) {
        return new Cons(t, this);
    }

    default List<T> remove(T t) {
        List listInstance = Nil.instance();
        boolean z = false;
        List<T> listPrepend = this;
        while (!z && !listPrepend.isEmpty()) {
            T tHead = listPrepend.head();
            if (Objects.equals(tHead, t)) {
                z = true;
            } else {
                listInstance = listInstance.prepend(tHead);
            }
            listPrepend = listPrepend.tail();
        }
        if (!z) {
            return this;
        }
        java.util.Iterator it = listInstance.iterator();
        while (it.hasNext()) {
            listPrepend = listPrepend.prepend(it.next());
        }
        return listPrepend;
    }

    default List<T> removeFirst(Predicate<T> predicate) {
        Objects.requireNonNull(predicate, "predicate is null");
        List listEmpty = empty();
        List<T> listTail = this;
        while (!listTail.isEmpty() && !predicate.test(listTail.head())) {
            listEmpty = listEmpty.prepend(listTail.head());
            listTail = listTail.tail();
        }
        return listTail.isEmpty() ? this : (List) listEmpty.foldLeft(listTail.tail(), new List$$ExternalSyntheticLambda0());
    }

    default List<T> replace(T t, T t2) {
        List listInstance = Nil.instance();
        List<T> listTail = this;
        while (!listTail.isEmpty() && !Objects.equals(listTail.head(), t)) {
            listInstance = listInstance.prepend(listTail.head());
            listTail = listTail.tail();
        }
        if (listTail.isEmpty()) {
            return this;
        }
        List<T> listPrepend = listTail.tail().prepend(t2);
        Iterator it = listInstance.iterator();
        while (it.hasNext()) {
            listPrepend = listPrepend.prepend(it.next());
        }
        return listPrepend;
    }

    default List<T> reverse() {
        return isEmpty() ? this : (List) foldLeft(empty(), new List$$ExternalSyntheticLambda0());
    }

    @Override // java.lang.Iterable
    default Spliterator<T> spliterator() {
        return Spliterators.spliterator(iterator(), length(), 1040);
    }

    @Override // javaslang.Value
    default String stringPrefix() {
        return "List";
    }

    public static final class Nil<T> implements Serializable, List<T> {
        private static final Nil<?> INSTANCE = new Nil<>();
        private static final long serialVersionUID = 1;

        public boolean equals(Object obj) {
            return obj == this;
        }

        public int hashCode() {
            return 1;
        }

        @Override // javaslang.collection.List, javaslang.collection.Traversable, javaslang.Value
        public boolean isEmpty() {
            return true;
        }

        @Override // javaslang.collection.List, javaslang.collection.Traversable
        public int length() {
            return 0;
        }

        private Nil() {
        }

        public static <T> Nil<T> instance() {
            return (Nil<T>) INSTANCE;
        }

        @Override // javaslang.collection.Traversable
        public T head() {
            throw new NoSuchElementException("head of empty list");
        }

        @Override // javaslang.collection.List, javaslang.collection.LinearSeq, javaslang.collection.Seq, javaslang.collection.Traversable
        public List<T> tail() {
            throw new UnsupportedOperationException("tail of empty list");
        }

        @Override // javaslang.Value
        public String toString() {
            return stringPrefix() + "()";
        }

        private Object readResolve() {
            return INSTANCE;
        }
    }

    public static final class Cons<T> implements Serializable, List<T> {
        private static final long serialVersionUID = 1;
        private final T head;
        private final int length;
        private final List<T> tail;

        @Override // javaslang.collection.List, javaslang.collection.Traversable, javaslang.Value
        public boolean isEmpty() {
            return false;
        }

        private Cons(T t, List<T> list) {
            this.head = t;
            this.tail = list;
            this.length = list.length() + 1;
        }

        @Override // javaslang.collection.Traversable
        public T head() {
            return this.head;
        }

        @Override // javaslang.collection.List, javaslang.collection.Traversable
        public int length() {
            return this.length;
        }

        @Override // javaslang.collection.List, javaslang.collection.LinearSeq, javaslang.collection.Seq, javaslang.collection.Traversable
        public List<T> tail() {
            return this.tail;
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof List)) {
                return false;
            }
            List listTail = (List) obj;
            List listTail2 = this;
            while (!listTail2.isEmpty() && !listTail.isEmpty()) {
                if (!Objects.equals(listTail2.head(), listTail.head())) {
                    return false;
                }
                listTail2 = listTail2.tail();
                listTail = listTail.tail();
            }
            return listTail2.isEmpty() && listTail.isEmpty();
        }

        public int hashCode() {
            return Collections.hash(this);
        }

        @Override // javaslang.Value
        public String toString() {
            return mkString(stringPrefix() + "(", ", ", Constant.AFTER_QUTO);
        }

        private Object writeReplace() {
            return new SerializationProxy(this);
        }

        private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
            throw new InvalidObjectException("Proxy required");
        }

        private static final class SerializationProxy<T> implements Serializable {
            private static final long serialVersionUID = 1;
            private transient Cons<T> list;

            SerializationProxy(Cons<T> cons) {
                this.list = cons;
            }

            private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
                objectOutputStream.defaultWriteObject();
                objectOutputStream.writeInt(this.list.length());
                for (List listTail = this.list; !listTail.isEmpty(); listTail = listTail.tail()) {
                    objectOutputStream.writeObject(listTail.head());
                }
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r1v1, types: [javaslang.collection.List] */
            /* JADX WARN: Type inference failed for: r1v3 */
            /* JADX WARN: Type inference failed for: r1v4 */
            private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
                ?? Prepend;
                objectInputStream.defaultReadObject();
                int i = objectInputStream.readInt();
                if (i <= 0) {
                    throw new InvalidObjectException("No elements");
                }
                Nil nilInstance = Nil.instance();
                int i2 = 0;
                while (i2 < i) {
                    Prepend = nilInstance;
                    i2++;
                    Prepend = Prepend.prepend(objectInputStream.readObject());
                }
                Prepend = nilInstance;
                this.list = (Cons) Prepend.reverse();
            }

            private Object readResolve() {
                return this.list;
            }
        }
    }
}
