package org.jetbrains.kotlin.org.jline.utils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Objects;
import java.util.function.Supplier;
import org.apache.commons.lang3.SystemProperties;

/* JADX INFO: loaded from: classes4.dex */
public final class Signals {
    public static Object register(String str, Runnable runnable) {
        Objects.requireNonNull(runnable);
        return register(str, runnable, runnable.getClass().getClassLoader());
    }

    public static Object register(final String str, final Runnable runnable, ClassLoader classLoader) {
        try {
            final Class<?> cls = Class.forName("sun.misc.SignalHandler");
            return doRegister(str, Proxy.newProxyInstance(classLoader, new Class[]{cls}, new InvocationHandler() { // from class: org.jetbrains.kotlin.org.jline.utils.Signals$$ExternalSyntheticLambda0
                @Override // java.lang.reflect.InvocationHandler
                public final Object invoke(Object obj, Method method, Object[] objArr) {
                    return Signals.lambda$register$1(runnable, cls, str, obj, method, objArr);
                }
            }));
        } catch (Exception e) {
            Log.debug("Error registering handler for signal ", str, e);
            return null;
        }
    }

    static /* synthetic */ Object lambda$register$1(final Runnable runnable, Class cls, final String str, Object obj, Method method, Object[] objArr) throws Throwable {
        if (method.getDeclaringClass() == Object.class) {
            if ("toString".equals(method.getName())) {
                return runnable.toString();
            }
            return null;
        }
        if (method.getDeclaringClass() != cls) {
            return null;
        }
        Log.trace((Supplier<String>) new Supplier() { // from class: org.jetbrains.kotlin.org.jline.utils.Signals$$ExternalSyntheticLambda3
            @Override // java.util.function.Supplier
            public final Object get() {
                return Signals.lambda$null$0(runnable, str);
            }
        });
        runnable.run();
        return null;
    }

    static /* synthetic */ String lambda$null$0(Runnable runnable, String str) {
        return "Calling handler " + toString(runnable) + " for signal " + str;
    }

    public static Object registerDefault(String str) {
        try {
            return doRegister(str, Class.forName("sun.misc.SignalHandler").getField("SIG_DFL").get(null));
        } catch (Exception e) {
            Log.debug("Error registering default handler for signal ", str, e);
            return null;
        }
    }

    public static void unregister(String str, Object obj) {
        if (obj != null) {
            try {
                doRegister(str, obj);
            } catch (Exception e) {
                Log.debug("Error unregistering handler for signal ", str, e);
            }
        }
    }

    private static Object doRegister(final String str, final Object obj) throws Exception {
        Log.trace((Supplier<String>) new Supplier() { // from class: org.jetbrains.kotlin.org.jline.utils.Signals$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
                return Signals.lambda$doRegister$2(str, obj);
            }
        });
        if ("QUIT".equals(str) || ("INFO".equals(str) && "9".equals(System.getProperty(SystemProperties.JAVA_SPECIFICATION_VERSION)))) {
            Log.trace((Supplier<String>) new Supplier() { // from class: org.jetbrains.kotlin.org.jline.utils.Signals$$ExternalSyntheticLambda2
                @Override // java.util.function.Supplier
                public final Object get() {
                    return Signals.lambda$doRegister$3(str);
                }
            });
            return null;
        }
        Class<?> cls = Class.forName("sun.misc.Signal");
        return cls.getMethod("handle", cls, Class.forName("sun.misc.SignalHandler")).invoke(null, cls.getConstructor(String.class).newInstance(str), obj);
    }

    static /* synthetic */ String lambda$doRegister$2(String str, Object obj) {
        return "Registering signal " + str + " with handler " + toString(obj);
    }

    static /* synthetic */ String lambda$doRegister$3(String str) {
        return "Ignoring unsupported signal " + str;
    }

    private static String toString(Object obj) {
        try {
            Class<?> cls = Class.forName("sun.misc.SignalHandler");
            if (obj == cls.getField("SIG_DFL").get(null)) {
                return "SIG_DFL";
            }
            if (obj == cls.getField("SIG_IGN").get(null)) {
                return "SIG_IGN";
            }
        } catch (Throwable unused) {
        }
        return obj != null ? obj.toString() : "null";
    }
}
