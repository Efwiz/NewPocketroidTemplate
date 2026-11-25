package org.jetbrains.kotlin.com.intellij.openapi.util;

/* JADX INFO: loaded from: classes3.dex */
public abstract class VolatileNullableLazyValue<T> extends NullableLazyValue<T> {
    private volatile boolean myComputed;
    private volatile T myValue;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "value", "org/jetbrains/kotlin/com/intellij/openapi/util/VolatileNullableLazyValue", "createValue"));
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.util.NullableLazyValue
    protected abstract T compute();

    @Override // org.jetbrains.kotlin.com.intellij.openapi.util.NullableLazyValue
    public T getValue() {
        boolean z = this.myComputed;
        T tCompute = this.myValue;
        if (!z) {
            RecursionGuard.StackStamp stackStampMarkStack = RecursionManager.markStack();
            tCompute = compute();
            if (stackStampMarkStack.mayCacheNow()) {
                this.myValue = tCompute;
                this.myComputed = true;
            }
        }
        return tCompute;
    }

    public void drop() {
        this.myComputed = false;
        this.myValue = null;
    }

    public static <T> VolatileNullableLazyValue<T> createValue(final Factory<? extends T> factory) {
        if (factory == null) {
            $$$reportNull$$$0(0);
        }
        return new VolatileNullableLazyValue<T>() { // from class: org.jetbrains.kotlin.com.intellij.openapi.util.VolatileNullableLazyValue.1
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.VolatileNullableLazyValue, org.jetbrains.kotlin.com.intellij.openapi.util.NullableLazyValue
            protected T compute() {
                return (T) factory.create();
            }
        };
    }
}
