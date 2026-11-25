package org.jetbrains.kotlin.config;

import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;

/* JADX INFO: loaded from: classes3.dex */
public class CompilerConfigurationKey<T> {
    Key<T> ideaKey;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        objArr[0] = "name";
        objArr[1] = "org/jetbrains/kotlin/config/CompilerConfigurationKey";
        if (i != 1) {
            objArr[2] = "<init>";
        } else {
            objArr[2] = CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public CompilerConfigurationKey(String str) {
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        this.ideaKey = Key.create(str);
    }

    public static <T> CompilerConfigurationKey<T> create(String str) {
        if (str == null) {
            $$$reportNull$$$0(1);
        }
        return new CompilerConfigurationKey<>(str);
    }

    public String toString() {
        return this.ideaKey.toString();
    }
}
