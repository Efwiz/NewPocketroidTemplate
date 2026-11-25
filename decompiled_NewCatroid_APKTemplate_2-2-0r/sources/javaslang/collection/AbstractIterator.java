package javaslang.collection;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.NoSuchElementException;
import net.bytebuddy.description.type.TypeDescription;

/* JADX INFO: loaded from: classes11.dex */
public abstract class AbstractIterator<T> implements Iterator<T> {
    protected abstract T getNext();

    @Override // javaslang.Value
    public String toString() {
        return stringPrefix() + "(" + (isEmpty() ? "" : TypeDescription.Generic.OfWildcardType.SYMBOL) + Constant.AFTER_QUTO;
    }

    @Override // java.util.Iterator
    public final T next() {
        if (!hasNext()) {
            throw new NoSuchElementException("next() on empty iterator");
        }
        return getNext();
    }
}
