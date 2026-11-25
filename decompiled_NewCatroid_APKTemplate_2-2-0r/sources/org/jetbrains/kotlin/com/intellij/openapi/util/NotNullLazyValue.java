package org.jetbrains.kotlin.com.intellij.openapi.util;

import java.util.Objects;
import java.util.function.Supplier;

/* JADX INFO: loaded from: classes3.dex */
public abstract class NotNullLazyValue<T> {
    private T myValue;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 2 || i == 3 || i == 4 || i == 5) ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[(i == 1 || i == 2 || i == 3 || i == 4 || i == 5) ? 3 : 2];
        if (i == 1 || i == 2 || i == 3 || i == 4) {
            objArr[0] = "value";
        } else if (i != 5) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/util/NotNullLazyValue";
        } else {
            objArr[0] = "supplier";
        }
        if (i == 1 || i == 2 || i == 3 || i == 4 || i == 5) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/util/NotNullLazyValue";
        } else {
            objArr[1] = "getValue";
        }
        if (i == 1) {
            objArr[2] = "createConstantValue";
        } else if (i == 2) {
            objArr[2] = "createValue";
        } else if (i == 3) {
            objArr[2] = "lazy";
        } else if (i == 4) {
            objArr[2] = "atomicLazy";
        } else if (i == 5) {
            objArr[2] = "volatileLazy";
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 2 && i != 3 && i != 4 && i != 5) {
            throw new IllegalStateException(str2);
        }
        throw new IllegalArgumentException(str2);
    }

    static /* synthetic */ Object lambda$createConstantValue$0(Object obj) {
        return obj;
    }

    protected abstract T compute();

    @Deprecated
    protected NotNullLazyValue() {
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
            $$$reportNull$$$0(0);
        }
        return t;
    }

    public static <T> NotNullLazyValue<T> createConstantValue(final T t) {
        if (t == null) {
            $$$reportNull$$$0(1);
        }
        return lazy(new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.openapi.util.NotNullLazyValue$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return NotNullLazyValue.lambda$createConstantValue$0(t);
            }
        });
    }

    public static <T> NotNullLazyValue<T> createValue(final NotNullFactory<? extends T> notNullFactory) {
        if (notNullFactory == null) {
            $$$reportNull$$$0(2);
        }
        Objects.requireNonNull(notNullFactory);
        return lazy(new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.openapi.util.NotNullLazyValue$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
                return notNullFactory.create();
            }
        });
    }

    public static <T> NotNullLazyValue<T> lazy(final Supplier<? extends T> supplier) {
        if (supplier == null) {
            $$$reportNull$$$0(3);
        }
        return new NotNullLazyValue<T>() { // from class: org.jetbrains.kotlin.com.intellij.openapi.util.NotNullLazyValue.1
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/com/intellij/openapi/util/NotNullLazyValue$1", "compute"));
            }

            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.NotNullLazyValue
            protected T compute() {
                T t = (T) supplier.get();
                if (t == null) {
                    $$$reportNull$$$0(0);
                }
                return t;
            }
        };
    }

    public static <T> NotNullLazyValue<T> atomicLazy(final Supplier<? extends T> supplier) {
        if (supplier == null) {
            $$$reportNull$$$0(4);
        }
        return new AtomicNotNullLazyValue<T>() { // from class: org.jetbrains.kotlin.com.intellij.openapi.util.NotNullLazyValue.2
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/com/intellij/openapi/util/NotNullLazyValue$2", "compute"));
            }

            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.NotNullLazyValue
            protected T compute() {
                T t = (T) supplier.get();
                if (t == null) {
                    $$$reportNull$$$0(0);
                }
                return t;
            }
        };
    }

    public static <T> NotNullLazyValue<T> volatileLazy(final Supplier<? extends T> supplier) {
        if (supplier == null) {
            $$$reportNull$$$0(5);
        }
        return new NotNullLazyValue<T>() { // from class: org.jetbrains.kotlin.com.intellij.openapi.util.NotNullLazyValue.3
            private volatile T value;

            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                Object[] objArr = new Object[2];
                objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/util/NotNullLazyValue$3";
                if (i != 1) {
                    objArr[1] = "getValue";
                } else {
                    objArr[1] = "compute";
                }
                throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", objArr));
            }

            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.NotNullLazyValue
            public T getValue() {
                T t = this.value;
                if (t == null) {
                    RecursionGuard.StackStamp stackStampMarkStack = RecursionManager.markStack();
                    T t2 = (T) compute();
                    if (stackStampMarkStack.mayCacheNow()) {
                        this.value = t2;
                    }
                    t = t2;
                }
                if (t == null) {
                    $$$reportNull$$$0(0);
                }
                return t;
            }

            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.NotNullLazyValue
            protected T compute() {
                T t = (T) supplier.get();
                if (t == null) {
                    $$$reportNull$$$0(1);
                }
                return t;
            }
        };
    }
}
