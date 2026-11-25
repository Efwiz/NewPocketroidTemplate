package javaslang;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;
import java.util.function.Supplier;
import javaslang.collection.Iterator;
import net.bytebuddy.description.type.TypeDescription;

/* JADX INFO: loaded from: classes11.dex */
public final class Lazy<T> implements Serializable, Supplier<T>, Value<T> {
    private static final long serialVersionUID = 1;
    private volatile transient Supplier<? extends T> supplier;
    private T value;

    @Override // javaslang.Value
    public boolean isEmpty() {
        return false;
    }

    @Override // javaslang.Value
    public boolean isSingleValued() {
        return true;
    }

    private Lazy(Supplier<? extends T> supplier) {
        this.supplier = supplier;
    }

    public static <T> Lazy<T> of(Supplier<? extends T> supplier) {
        Objects.requireNonNull(supplier, "supplier is null");
        if (supplier instanceof Lazy) {
            return (Lazy) supplier;
        }
        return new Lazy<>(supplier);
    }

    @Override // java.util.function.Supplier, javaslang.Value
    public T get() {
        return this.supplier == null ? this.value : computeValue();
    }

    private synchronized T computeValue() {
        Supplier<? extends T> supplier = this.supplier;
        if (supplier != null) {
            this.value = supplier.get();
            this.supplier = null;
        }
        return this.value;
    }

    public boolean isEvaluated() {
        return this.supplier == null;
    }

    @Override // javaslang.Value, java.lang.Iterable
    public Iterator<T> iterator() {
        return Iterator.of(get());
    }

    @Override // javaslang.Value
    public String stringPrefix() {
        return "Lazy";
    }

    public boolean equals(Object obj) {
        return obj == this || ((obj instanceof Lazy) && Objects.equals(((Lazy) obj).get(), get()));
    }

    public int hashCode() {
        return Objects.hash(get());
    }

    @Override // javaslang.Value
    public String toString() {
        return stringPrefix() + "(" + (!isEvaluated() ? TypeDescription.Generic.OfWildcardType.SYMBOL : this.value) + Constant.AFTER_QUTO;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        get();
        objectOutputStream.defaultWriteObject();
    }
}
