package org.jetbrains.kotlin.com.intellij.openapi.util.io;

import java.io.File;
import java.util.Iterator;
import java.util.function.Supplier;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.com.intellij.openapi.util.NotNullLazyValue;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.Strings;
import org.jetbrains.kotlin.com.intellij.util.EnvironmentUtil;

/* JADX INFO: loaded from: classes3.dex */
public final class PathExecLazyValue {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 1 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 1 ? 3 : 2];
        if (i != 1) {
            objArr[0] = "name";
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/util/io/PathExecLazyValue";
        }
        if (i != 1) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/util/io/PathExecLazyValue";
        } else {
            objArr[1] = CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME;
        }
        if (i != 1) {
            objArr[2] = CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME;
        }
        String str2 = String.format(str, objArr);
        if (i == 1) {
            throw new IllegalStateException(str2);
        }
    }

    public static NotNullLazyValue<Boolean> create(final String str) {
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        if (Strings.containsAnyChar(str, "/\\")) {
            throw new IllegalArgumentException(str);
        }
        NotNullLazyValue<Boolean> notNullLazyValueAtomicLazy = NotNullLazyValue.atomicLazy(new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.openapi.util.io.PathExecLazyValue$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return PathExecLazyValue.lambda$create$0(str);
            }
        });
        if (notNullLazyValueAtomicLazy == null) {
            $$$reportNull$$$0(1);
        }
        return notNullLazyValueAtomicLazy;
    }

    static /* synthetic */ Boolean lambda$create$0(String str) {
        String value = EnvironmentUtil.getValue("PATH");
        if (value != null) {
            Iterator<String> it = StringUtil.tokenize(value, File.pathSeparator).iterator();
            while (it.hasNext()) {
                if (new File(it.next(), str).canExecute()) {
                    return true;
                }
            }
        }
        return false;
    }
}
