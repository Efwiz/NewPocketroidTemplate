package org.jetbrains.kotlin.com.intellij.openapi.util;

import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.com.intellij.openapi.util.UserDataHolder;
import org.jetbrains.kotlin.com.intellij.util.NotNullFunction;

/* JADX INFO: loaded from: classes3.dex */
public final class NotNullLazyKey<T, H extends UserDataHolder> extends Key<T> {
    private final NotNullFunction<? super H, ? extends T> myFunction;

    /* JADX WARN: Code duplicated, block: B:18:0x002a  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 3 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 3 ? 3 : 2];
        if (i == 1) {
            objArr[0] = "function";
        } else if (i == 2) {
            objArr[0] = "h";
        } else if (i == 3) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/util/NotNullLazyKey";
        } else if (i != 5) {
            objArr[0] = "name";
        } else {
            objArr[0] = "function";
        }
        if (i != 3) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/util/NotNullLazyKey";
        } else {
            objArr[1] = "getValue";
        }
        if (i == 2) {
            objArr[2] = "getValue";
        } else if (i != 3) {
            if (i == 4 || i == 5) {
                objArr[2] = CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME;
            } else {
                objArr[2] = "<init>";
            }
        }
        String str2 = String.format(str, objArr);
        if (i == 3) {
            throw new IllegalStateException(str2);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    private NotNullLazyKey(String str, NotNullFunction<? super H, ? extends T> notNullFunction) {
        super(str);
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        if (notNullFunction == null) {
            $$$reportNull$$$0(1);
        }
        this.myFunction = notNullFunction;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public T getValue(H h) {
        if (h == 0) {
            $$$reportNull$$$0(2);
        }
        T t = (T) h.getUserData(this);
        if (t == null) {
            RecursionGuard.StackStamp stackStampMarkStack = RecursionManager.markStack();
            T tFun = this.myFunction.fun(h);
            if (!stackStampMarkStack.mayCacheNow()) {
                t = tFun;
            } else if (h instanceof UserDataHolderEx) {
                t = (T) ((UserDataHolderEx) h).putUserDataIfAbsent(this, tFun);
            } else {
                h.putUserData(this, tFun);
                t = tFun;
            }
        }
        if (t == null) {
            $$$reportNull$$$0(3);
        }
        return t;
    }

    public static <T, H extends UserDataHolder> NotNullLazyKey<T, H> create(String str, NotNullFunction<? super H, ? extends T> notNullFunction) {
        if (str == null) {
            $$$reportNull$$$0(4);
        }
        if (notNullFunction == null) {
            $$$reportNull$$$0(5);
        }
        return new NotNullLazyKey<>(str, notNullFunction);
    }
}
