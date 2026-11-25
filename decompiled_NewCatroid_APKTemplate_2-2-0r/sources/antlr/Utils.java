package antlr;

/* JADX INFO: loaded from: classes.dex */
public class Utils {
    private static boolean useDirectClassLoading = false;
    private static boolean useSystemExit = true;

    static {
        if ("true".equalsIgnoreCase(System.getProperty("ANTLR_DO_NOT_EXIT", "false"))) {
            useSystemExit = false;
        }
        if ("true".equalsIgnoreCase(System.getProperty("ANTLR_USE_DIRECT_CLASS_LOADING", "false"))) {
            useDirectClassLoading = true;
        }
    }

    public static Class loadClass(String str) throws ClassNotFoundException {
        try {
            ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
            if (!useDirectClassLoading && contextClassLoader != null) {
                return contextClassLoader.loadClass(str);
            }
            return Class.forName(str);
        } catch (Exception unused) {
            return Class.forName(str);
        }
    }

    public static Object createInstanceOf(String str) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        return loadClass(str).newInstance();
    }

    public static void error(String str) {
        if (useSystemExit) {
            System.exit(1);
        }
        throw new RuntimeException(new StringBuffer("ANTLR Panic: ").append(str).toString());
    }

    public static void error(String str, Throwable th) {
        if (useSystemExit) {
            System.exit(1);
        }
        throw new RuntimeException("ANTLR Panic", th);
    }
}
