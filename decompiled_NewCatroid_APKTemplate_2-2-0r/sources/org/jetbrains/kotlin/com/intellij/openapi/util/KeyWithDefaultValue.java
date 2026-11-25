package org.jetbrains.kotlin.com.intellij.openapi.util;

import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;

/* JADX INFO: loaded from: classes3.dex */
public abstract class KeyWithDefaultValue<T> extends Key<T> {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 3) {
            objArr[0] = "name";
        } else {
            objArr[0] = "factory";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/util/KeyWithDefaultValue";
        if (i == 1 || i == 2 || i == 3) {
            objArr[2] = CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME;
        } else {
            objArr[2] = "<init>";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public abstract T getDefaultValue();

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyWithDefaultValue(String str) {
        super(str);
        if (str == null) {
            $$$reportNull$$$0(0);
        }
    }

    public static <T> KeyWithDefaultValue<T> create(String str, final T t) {
        if (str == null) {
            $$$reportNull$$$0(1);
        }
        return new KeyWithDefaultValue<T>(str) { // from class: org.jetbrains.kotlin.com.intellij.openapi.util.KeyWithDefaultValue.1
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.KeyWithDefaultValue
            public T getDefaultValue() {
                return (T) t;
            }
        };
    }
}
