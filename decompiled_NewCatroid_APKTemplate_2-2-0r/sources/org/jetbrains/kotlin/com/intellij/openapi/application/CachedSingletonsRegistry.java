package org.jetbrains.kotlin.com.intellij.openapi.application;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jetbrains.kotlin.com.intellij.openapi.util.ClearableLazyValue;
import org.jetbrains.kotlin.com.intellij.util.ReflectionUtil;

/* JADX INFO: loaded from: classes3.dex */
public final class CachedSingletonsRegistry {
    private static final Object LOCK = new CachedSingletonsRegistry();
    private static final List<Class<?>> ourRegisteredClasses = new ArrayList();
    private static final List<ClearableLazyValue<?>> ourRegisteredLazyValues = new ArrayList();

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 2 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 2 ? 3 : 2];
        if (i == 1) {
            objArr[0] = "lazyValue";
        } else if (i != 2) {
            objArr[0] = "klass";
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/application/CachedSingletonsRegistry";
        }
        if (i != 2) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/application/CachedSingletonsRegistry";
        } else {
            objArr[1] = "markLazyValue";
        }
        if (i == 1) {
            objArr[2] = "markLazyValue";
        } else if (i != 2) {
            objArr[2] = "markCachedField";
        }
        String str2 = String.format(str, objArr);
        if (i == 2) {
            throw new IllegalStateException(str2);
        }
    }

    private CachedSingletonsRegistry() {
    }

    public static <T> T markCachedField(Class<T> cls) {
        if (cls == null) {
            $$$reportNull$$$0(0);
        }
        synchronized (LOCK) {
            ourRegisteredClasses.add(cls);
        }
        return null;
    }

    public static <T> ClearableLazyValue<T> markLazyValue(ClearableLazyValue<T> clearableLazyValue) {
        if (clearableLazyValue == null) {
            $$$reportNull$$$0(1);
        }
        synchronized (LOCK) {
            ourRegisteredLazyValues.add(clearableLazyValue);
        }
        if (clearableLazyValue == null) {
            $$$reportNull$$$0(2);
        }
        return clearableLazyValue;
    }

    public static void cleanupCachedFields() {
        synchronized (LOCK) {
            Iterator<Class<?>> it = ourRegisteredClasses.iterator();
            while (it.hasNext()) {
                try {
                    cleanupClass(it.next());
                } catch (Exception unused) {
                }
            }
            Iterator<ClearableLazyValue<?>> it2 = ourRegisteredLazyValues.iterator();
            while (it2.hasNext()) {
                it2.next().drop();
            }
        }
    }

    private static void cleanupClass(Class<?> cls) {
        ReflectionUtil.resetField(cls, null, "ourInstance");
    }
}
