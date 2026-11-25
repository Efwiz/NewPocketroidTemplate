package org.jetbrains.kotlin.com.intellij.openapi.util;

import java.util.function.Supplier;

/* JADX INFO: loaded from: classes3.dex */
public interface Computable<T> extends Supplier<T> {
    T compute();

    @Override // java.util.function.Supplier
    default T get() {
        return compute();
    }

    public static final class PredefinedValueComputable<T> implements Computable<T> {
        private final T myValue;

        public PredefinedValueComputable(T t) {
            this.myValue = t;
        }

        @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Computable
        public T compute() {
            return this.myValue;
        }

        public String toString() {
            return "PredefinedValueComputable{" + this.myValue + "}";
        }
    }
}
