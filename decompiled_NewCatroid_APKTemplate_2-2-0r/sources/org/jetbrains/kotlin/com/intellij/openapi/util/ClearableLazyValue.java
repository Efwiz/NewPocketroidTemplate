package org.jetbrains.kotlin.com.intellij.openapi.util;

import java.util.function.Supplier;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;

/* JADX INFO: loaded from: classes3.dex */
public abstract class ClearableLazyValue<T> {
    private T myValue;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 2 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 2 ? 3 : 2];
        if (i != 2) {
            objArr[0] = "computable";
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/util/ClearableLazyValue";
        }
        if (i != 2) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/util/ClearableLazyValue";
        } else {
            objArr[1] = "getValue";
        }
        if (i == 1) {
            objArr[2] = "createAtomic";
        } else if (i != 2) {
            objArr[2] = CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME;
        }
        String str2 = String.format(str, objArr);
        if (i == 2) {
            throw new IllegalStateException(str2);
        }
    }

    protected abstract T compute();

    public static <T> ClearableLazyValue<T> create(final Supplier<? extends T> supplier) {
        if (supplier == null) {
            $$$reportNull$$$0(0);
        }
        return new ClearableLazyValue<T>() { // from class: org.jetbrains.kotlin.com.intellij.openapi.util.ClearableLazyValue.1
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/com/intellij/openapi/util/ClearableLazyValue$1", "compute"));
            }

            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.ClearableLazyValue
            protected T compute() {
                T t = (T) supplier.get();
                if (t == null) {
                    $$$reportNull$$$0(0);
                }
                return t;
            }
        };
    }

    public static <T> ClearableLazyValue<T> createAtomic(final Supplier<? extends T> supplier) {
        if (supplier == null) {
            $$$reportNull$$$0(1);
        }
        return new AtomicClearableLazyValue<T>() { // from class: org.jetbrains.kotlin.com.intellij.openapi.util.ClearableLazyValue.2
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/com/intellij/openapi/util/ClearableLazyValue$2", "compute"));
            }

            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.ClearableLazyValue
            protected T compute() {
                T t = (T) supplier.get();
                if (t == null) {
                    $$$reportNull$$$0(0);
                }
                return t;
            }
        };
    }

    public T getValue() {
        T t = this.myValue;
        if (t == null) {
            RecursionGuard.StackStamp stackStampMarkStack = RecursionManager.markStack();
            T tCompute = compute();
            if (stackStampMarkStack.mayCacheNow()) {
                this.myValue = tCompute;
            }
            t = tCompute;
        }
        if (t == null) {
            $$$reportNull$$$0(2);
        }
        return t;
    }

    public boolean isCached() {
        return this.myValue != null;
    }

    public void drop() {
        this.myValue = null;
    }
}
