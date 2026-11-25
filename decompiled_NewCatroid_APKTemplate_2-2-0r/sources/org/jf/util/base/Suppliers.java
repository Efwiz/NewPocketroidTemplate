package org.jf.util.base;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.Serializable;
import java.util.function.Supplier;

/* JADX INFO: loaded from: classes6.dex */
public class Suppliers {
    public static <T> Supplier<T> memoize(Supplier<T> supplier) {
        return supplier instanceof MemoizingSupplier ? supplier : new MemoizingSupplier(supplier);
    }

    static class MemoizingSupplier<T> implements Supplier<T>, Serializable {
        private static final long serialVersionUID = 0;
        final Supplier<T> delegate;
        volatile transient boolean initialized;
        transient T value;

        MemoizingSupplier(Supplier<T> supplier) {
            this.delegate = supplier;
        }

        @Override // java.util.function.Supplier
        public T get() {
            if (!this.initialized) {
                synchronized (this) {
                    if (!this.initialized) {
                        T t = this.delegate.get();
                        this.value = t;
                        this.initialized = true;
                        return t;
                    }
                }
            }
            return this.value;
        }

        public String toString() {
            return "Suppliers.memoize(" + (this.initialized ? "<supplier that returned " + this.value + ">" : this.delegate) + Constant.AFTER_QUTO;
        }
    }
}
