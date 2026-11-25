package org.jetbrains.kotlin.com.intellij.openapi.util;

/* JADX INFO: loaded from: classes3.dex */
public abstract class AtomicNullableLazyValue<T> extends NullableLazyValue<T> {
    private volatile boolean myComputed;
    private volatile T myValue;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "value", "org/jetbrains/kotlin/com/intellij/openapi/util/AtomicNullableLazyValue", "createValue"));
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.util.NullableLazyValue
    public final T getValue() {
        T tCompute;
        boolean z = this.myComputed;
        T t = this.myValue;
        if (z) {
            return t;
        }
        synchronized (this) {
            boolean z2 = this.myComputed;
            tCompute = this.myValue;
            if (!z2) {
                RecursionGuard.StackStamp stackStampMarkStack = RecursionManager.markStack();
                tCompute = compute();
                if (stackStampMarkStack.mayCacheNow()) {
                    this.myValue = tCompute;
                    this.myComputed = true;
                }
            }
        }
        return tCompute;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.util.NullableLazyValue
    public boolean isComputed() {
        return this.myComputed;
    }

    public static <T> AtomicNullableLazyValue<T> createValue(final Factory<? extends T> factory) {
        if (factory == null) {
            $$$reportNull$$$0(0);
        }
        return new AtomicNullableLazyValue<T>() { // from class: org.jetbrains.kotlin.com.intellij.openapi.util.AtomicNullableLazyValue.1
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.NullableLazyValue
            protected T compute() {
                return (T) factory.create();
            }
        };
    }
}
