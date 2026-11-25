package org.jetbrains.kotlin.com.intellij.openapi.util;

/* JADX INFO: loaded from: classes3.dex */
public abstract class AtomicNotNullLazyValue<T> extends NotNullLazyValue<T> {
    private volatile T myValue;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 1 ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[i != 1 ? 2 : 3];
        if (i != 1) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/util/AtomicNotNullLazyValue";
        } else {
            objArr[0] = "value";
        }
        if (i == 1) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/util/AtomicNotNullLazyValue";
        } else if (i != 2) {
            objArr[1] = "getValue";
        } else {
            objArr[1] = "createValue";
        }
        if (i == 1) {
            objArr[2] = "createValue";
        }
        String str2 = String.format(str, objArr);
        if (i == 1) {
            throw new IllegalArgumentException(str2);
        }
    }

    @Deprecated
    protected AtomicNotNullLazyValue() {
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.util.NotNullLazyValue
    public final T getValue() {
        T t = this.myValue;
        if (t == null) {
            synchronized (this) {
                t = this.myValue;
                if (t == null) {
                    RecursionGuard.StackStamp stackStampMarkStack = RecursionManager.markStack();
                    T tCompute = compute();
                    if (stackStampMarkStack.mayCacheNow()) {
                        this.myValue = tCompute;
                    }
                    t = tCompute;
                }
            }
        }
        if (t == null) {
            $$$reportNull$$$0(0);
        }
        return t;
    }
}
