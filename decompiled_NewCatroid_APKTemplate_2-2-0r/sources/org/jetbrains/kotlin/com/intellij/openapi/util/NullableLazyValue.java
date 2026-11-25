package org.jetbrains.kotlin.com.intellij.openapi.util;

/* JADX INFO: loaded from: classes3.dex */
public abstract class NullableLazyValue<T> {
    private boolean myComputed;
    private T myValue;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "value", "org/jetbrains/kotlin/com/intellij/openapi/util/NullableLazyValue", "createValue"));
    }

    protected abstract T compute();

    public T getValue() {
        T t = this.myValue;
        if (this.myComputed) {
            return t;
        }
        RecursionGuard.StackStamp stackStampMarkStack = RecursionManager.markStack();
        T tCompute = compute();
        if (stackStampMarkStack.mayCacheNow()) {
            this.myValue = tCompute;
            this.myComputed = true;
        }
        return tCompute;
    }

    public static <T> NullableLazyValue<T> createValue(final Factory<? extends T> factory) {
        if (factory == null) {
            $$$reportNull$$$0(0);
        }
        return new NullableLazyValue<T>() { // from class: org.jetbrains.kotlin.com.intellij.openapi.util.NullableLazyValue.1
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.NullableLazyValue
            protected T compute() {
                return (T) factory.create();
            }
        };
    }

    public boolean isComputed() {
        return this.myComputed;
    }
}
