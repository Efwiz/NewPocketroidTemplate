package app.cash.quickjs;

import java.io.Closeable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.LinkedHashMap;
import java.util.logging.Logger;
import net.bytebuddy.description.type.TypeDescription;

/* JADX INFO: loaded from: classes.dex */
public final class QuickJs implements Closeable {
    private long context;

    /* JADX INFO: Access modifiers changed from: private */
    public native Object call(long context, long instance, Object method, Object[] args);

    private native byte[] compile(long context, String sourceCode, String fileName);

    private static native long createContext();

    private native void destroyContext(long context);

    private native Object evaluate(long context, String sourceCode, String fileName);

    private native Object execute(long context, byte[] bytecode);

    private native long get(long context, String name2, Object[] methods);

    private native void set(long context, String name2, Object object, Object[] methods);

    static {
        QuickJsNativeLoader.load();
    }

    public static QuickJs create() {
        long jCreateContext = createContext();
        if (jCreateContext == 0) {
            throw new OutOfMemoryError("Cannot create QuickJs instance");
        }
        return new QuickJs(jCreateContext);
    }

    private QuickJs(long context) {
        this.context = context;
    }

    public Object evaluate(String script, String fileName) {
        return evaluate(this.context, script, fileName);
    }

    public Object evaluate(String script) {
        return evaluate(this.context, script, TypeDescription.Generic.OfWildcardType.SYMBOL);
    }

    public <T> void set(String name2, Class<T> type, T object) {
        if (!type.isInterface()) {
            throw new UnsupportedOperationException("Only interfaces can be bound. Received: " + type);
        }
        if (type.getInterfaces().length > 0) {
            throw new UnsupportedOperationException(type + " must not extend other interfaces");
        }
        if (!type.isInstance(object)) {
            throw new IllegalArgumentException(object.getClass() + " is not an instance of " + type);
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Method method : type.getMethods()) {
            if (linkedHashMap.put(method.getName(), method) != null) {
                throw new UnsupportedOperationException(method.getName() + " is overloaded in " + type);
            }
        }
        set(this.context, name2, object, linkedHashMap.values().toArray());
    }

    public <T> T get(final String str, final Class<T> cls) {
        if (!cls.isInterface()) {
            throw new UnsupportedOperationException("Only interfaces can be proxied. Received: " + cls);
        }
        if (cls.getInterfaces().length > 0) {
            throw new UnsupportedOperationException(cls + " must not extend other interfaces");
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Method method : cls.getMethods()) {
            if (linkedHashMap.put(method.getName(), method) != null) {
                throw new UnsupportedOperationException(method.getName() + " is overloaded in " + cls);
            }
        }
        final long j = get(this.context, str, linkedHashMap.values().toArray());
        if (j != 0) {
            return (T) Proxy.newProxyInstance(cls.getClassLoader(), new Class[]{cls}, new InvocationHandler() { // from class: app.cash.quickjs.QuickJs.1
                @Override // java.lang.reflect.InvocationHandler
                public Object invoke(Object proxy, Method method2, Object[] args) throws Throwable {
                    if (method2.getDeclaringClass() == Object.class) {
                        return method2.invoke(this, args);
                    }
                    QuickJs quickJs = QuickJs.this;
                    return quickJs.call(quickJs.context, j, method2, args);
                }

                public String toString() {
                    return String.format("QuickJsProxy{name=%s, type=%s}", str, cls.getName());
                }
            });
        }
        throw new OutOfMemoryError("Cannot create QuickJs proxy to " + str);
    }

    public byte[] compile(String sourceCode, String fileName) {
        return compile(this.context, sourceCode, fileName);
    }

    public Object execute(byte[] bytecode) {
        return execute(this.context, bytecode);
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        long j = this.context;
        if (j != 0) {
            this.context = 0L;
            destroyContext(j);
        }
    }

    protected void finalize() {
        if (this.context != 0) {
            Logger.getLogger(getClass().getName()).warning("QuickJs instance leaked!");
        }
    }
}
