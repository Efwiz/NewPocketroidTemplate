package javaslang.control;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.Serializable;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;
import javaslang.Value;
import javaslang.collection.Iterator;

/* JADX INFO: loaded from: classes11.dex */
public interface Option<T> extends Serializable, Value<T> {
    public static final long serialVersionUID = 1;

    @Override // javaslang.Value
    T get();

    @Override // javaslang.Value
    boolean isEmpty();

    @Override // javaslang.Value
    default boolean isSingleValued() {
        return true;
    }

    @Override // javaslang.Value
    String toString();

    static <T> Option<T> some(T t) {
        return new Some(t);
    }

    static <T> Option<T> none() {
        return None.INSTANCE;
    }

    default boolean isDefined() {
        return !isEmpty();
    }

    default T getOrElse(T t) {
        return isEmpty() ? t : get();
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: X extends java.lang.Throwable */
    default <X extends Throwable> T getOrElseThrow(Supplier<X> supplier) throws Throwable {
        Objects.requireNonNull(supplier, "exceptionSupplier is null");
        if (isEmpty()) {
            throw supplier.get();
        }
        return get();
    }

    default <U> Option<U> map(Function<? super T, ? extends U> function) {
        Objects.requireNonNull(function, "mapper is null");
        return isEmpty() ? none() : some(function.apply(get()));
    }

    @Override // javaslang.Value, java.lang.Iterable
    default Iterator<T> iterator() {
        return isEmpty() ? Iterator.empty() : Iterator.of(get());
    }

    public static final class Some<T> implements Serializable, Option<T> {
        private static final Some<Void> NOTHING = new Some<>(null);
        private static final long serialVersionUID = 1;
        private final T value;

        @Override // javaslang.control.Option, javaslang.Value
        public boolean isEmpty() {
            return false;
        }

        private Some(T t) {
            this.value = t;
        }

        @Override // javaslang.control.Option, javaslang.Value
        public T get() {
            return this.value;
        }

        public boolean equals(Object obj) {
            return obj == this || ((obj instanceof Some) && Objects.equals(this.value, ((Some) obj).value));
        }

        public int hashCode() {
            return Objects.hashCode(this.value);
        }

        @Override // javaslang.Value
        public String stringPrefix() {
            return "Some";
        }

        @Override // javaslang.control.Option, javaslang.Value
        public String toString() {
            return stringPrefix() + "(" + this.value + Constant.AFTER_QUTO;
        }
    }

    public static final class None<T> implements Serializable, Option<T> {
        private static final None<?> INSTANCE = new None<>();
        private static final long serialVersionUID = 1;

        public boolean equals(Object obj) {
            return obj == this;
        }

        public int hashCode() {
            return 1;
        }

        @Override // javaslang.control.Option, javaslang.Value
        public boolean isEmpty() {
            return true;
        }

        private None() {
        }

        @Override // javaslang.control.Option, javaslang.Value
        public T get() {
            throw new NoSuchElementException("No value present");
        }

        @Override // javaslang.Value
        public String stringPrefix() {
            return "None";
        }

        @Override // javaslang.control.Option, javaslang.Value
        public String toString() {
            return stringPrefix();
        }

        private Object readResolve() {
            return INSTANCE;
        }
    }
}
