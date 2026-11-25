package javaslang.collection;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;
import java.util.function.Supplier;

/* JADX INFO: compiled from: Stream.java */
/* JADX INFO: loaded from: classes11.dex */
interface StreamModule {

    /* JADX INFO: compiled from: Stream.java */
    public static final class ConsImpl<T> extends Stream.Cons<T> implements Serializable {
        private static final long serialVersionUID = 1;

        ConsImpl(T t, Supplier<Stream<T>> supplier) {
            super(t, supplier);
        }

        @Override // javaslang.collection.Stream, javaslang.collection.LinearSeq, javaslang.collection.Seq, javaslang.collection.Traversable
        public Stream<T> tail() {
            return this.tail.get();
        }

        private Object writeReplace() {
            return new SerializationProxy(this);
        }

        private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
            throw new InvalidObjectException("Proxy required");
        }
    }

    /* JADX INFO: compiled from: Stream.java */
    public static final class AppendElements<T> extends Stream.Cons<T> implements Serializable {
        private static final long serialVersionUID = 1;
        private final Queue<T> queue;

        AppendElements(T t, Queue<T> queue, Supplier<Stream<T>> supplier) {
            super(t, supplier);
            this.queue = queue;
        }

        @Override // javaslang.collection.Stream
        public Stream<T> append(T t) {
            return new AppendElements(this.head, this.queue.append(t), this.tail);
        }

        @Override // javaslang.collection.Stream
        public Stream<T> appendAll(Iterable<? extends T> iterable) {
            Objects.requireNonNull(this.queue, "elements is null");
            return isEmpty() ? Stream.ofAll(this.queue) : new AppendElements(this.head, this.queue.appendAll(iterable), this.tail);
        }

        @Override // javaslang.collection.Stream, javaslang.collection.LinearSeq, javaslang.collection.Seq, javaslang.collection.Traversable
        public Stream<T> tail() {
            Stream<T> stream = this.tail.get();
            if (stream.isEmpty()) {
                return Stream.ofAll(this.queue);
            }
            if (stream instanceof ConsImpl) {
                ConsImpl consImpl = (ConsImpl) stream;
                return new AppendElements(consImpl.head(), this.queue, consImpl.tail);
            }
            AppendElements appendElements = (AppendElements) stream;
            return new AppendElements(appendElements.head(), appendElements.queue.appendAll(this.queue), appendElements.tail);
        }

        private Object writeReplace() {
            return new SerializationProxy(this);
        }

        private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
            throw new InvalidObjectException("Proxy required");
        }
    }

    /* JADX INFO: compiled from: Stream.java */
    public static final class SerializationProxy<T> implements Serializable {
        private static final long serialVersionUID = 1;
        private transient Stream.Cons<T> stream;

        SerializationProxy(Stream.Cons<T> cons) {
            this.stream = cons;
        }

        private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
            objectOutputStream.defaultWriteObject();
            objectOutputStream.writeInt(this.stream.length());
            for (Stream streamTail = this.stream; !streamTail.isEmpty(); streamTail = streamTail.tail()) {
                objectOutputStream.writeObject(streamTail.head());
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r1v1, types: [javaslang.collection.Stream] */
        /* JADX WARN: Type inference failed for: r1v4 */
        /* JADX WARN: Type inference failed for: r1v5 */
        private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
            ?? Append;
            objectInputStream.defaultReadObject();
            int i = objectInputStream.readInt();
            if (i <= 0) {
                throw new InvalidObjectException("No elements");
            }
            Stream.Empty emptyInstance = Stream.Empty.instance();
            int i2 = 0;
            while (i2 < i) {
                Append = emptyInstance;
                i2++;
                Append = Append.append(objectInputStream.readObject());
            }
            Append = emptyInstance;
            this.stream = (Stream.Cons) Append;
        }

        private Object readResolve() {
            return this.stream;
        }
    }

    /* JADX INFO: compiled from: Stream.java */
    public interface StreamFactory {
        static <T> Stream<T> create(final java.util.Iterator<? extends T> it) {
            return it.hasNext() ? Stream.cons(it.next(), new Supplier() { // from class: javaslang.collection.StreamModule$StreamFactory$$ExternalSyntheticLambda0
                @Override // java.util.function.Supplier
                public final Object get() {
                    return StreamModule.StreamFactory.create(it);
                }
            }) : Stream.Empty.instance();
        }
    }

    /* JADX INFO: compiled from: Stream.java */
    public static final class StreamIterator<T> extends AbstractIterator<T> {
        private Supplier<Stream<T>> current;

        static /* synthetic */ Stream lambda$new$0(Stream.Cons cons) {
            return cons;
        }

        StreamIterator(final Stream.Cons<T> cons) {
            this.current = new Supplier() { // from class: javaslang.collection.StreamModule$StreamIterator$$ExternalSyntheticLambda0
                @Override // java.util.function.Supplier
                public final Object get() {
                    return StreamModule.StreamIterator.lambda$new$0(cons);
                }
            };
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return !this.current.get().isEmpty();
        }

        @Override // javaslang.collection.AbstractIterator
        public T getNext() {
            Stream<T> stream = this.current.get();
            stream.getClass();
            this.current = new Stream$$ExternalSyntheticLambda1(stream);
            return stream.head();
        }
    }
}
