package org.jetbrains.kotlin.com.google.gwt.dev.js.rhino;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.Hashtable;
import java.util.MissingResourceException;

/* JADX INFO: loaded from: classes3.dex */
public class Context {
    private static MessagesBundle messages = new MessagesBundle();
    private static Hashtable threadContexts = new Hashtable(11);
    private static Object threadLocalCx;
    private static Method threadLocalGet;
    private static Method threadLocalSet;
    private int enterCount;
    int errorCount;
    private ErrorReporter errorReporter;
    private boolean generatingSource = true;
    int version;

    /* JADX WARN: Code duplicated, block: B:8:0x0015  */
    /* JADX WARN: Code duplicated, block: B:9:0x001a  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = "startPosition";
        } else if (i == 2) {
            objArr[0] = "endPosition";
        } else if (i == 4) {
            objArr[0] = "startPosition";
        } else if (i != 5) {
            objArr[0] = CrashHianalyticsData.MESSAGE;
        } else {
            objArr[0] = "endPosition";
        }
        objArr[1] = "org/jetbrains/kotlin/com/google/gwt/dev/js/rhino/Context";
        if (i == 3 || i == 4 || i == 5) {
            objArr[2] = "reportError";
        } else {
            objArr[2] = "reportWarning";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public Context() {
        setLanguageVersion(0);
    }

    public static Context enter() {
        return enter(null);
    }

    /* JADX WARN: Code duplicated, block: B:14:0x0023  */
    public static Context enter(Context context) {
        Context currentContext = getCurrentContext();
        if (context == null) {
            if (currentContext != null) {
                context = currentContext;
            } else {
                context = new Context();
                setThreadContext(context);
            }
        } else if (context.enterCount != 0) {
            if (context != currentContext) {
                throw new RuntimeException("Cannot enter Context active on another thread");
            }
        } else if (currentContext != null) {
            context = currentContext;
        } else {
            setThreadContext(context);
        }
        context.enterCount++;
        return context;
    }

    public static void exit() {
        Context currentContext = getCurrentContext();
        if (currentContext == null) {
            throw new RuntimeException("Calling Context.exit without previous Context.enter");
        }
        if (currentContext.enterCount < 1) {
            codeBug();
        }
        int i = currentContext.enterCount - 1;
        currentContext.enterCount = i;
        if (i == 0) {
            setThreadContext(null);
        }
    }

    public static Context getCurrentContext() {
        Object obj = threadLocalCx;
        if (obj != null) {
            try {
                return (Context) threadLocalGet.invoke(obj, null);
            } catch (Exception unused) {
            }
        }
        return (Context) threadContexts.get(Thread.currentThread());
    }

    private static void setThreadContext(Context context) {
        Object obj = threadLocalCx;
        if (obj != null) {
            try {
                threadLocalSet.invoke(obj, context);
                return;
            } catch (Exception unused) {
            }
        }
        Thread threadCurrentThread = Thread.currentThread();
        if (context != null) {
            threadContexts.put(threadCurrentThread, context);
        } else {
            threadContexts.remove(threadCurrentThread);
        }
    }

    public int getLanguageVersion() {
        return this.version;
    }

    public void setLanguageVersion(int i) {
        this.version = i;
    }

    public ErrorReporter getErrorReporter() {
        return this.errorReporter;
    }

    public ErrorReporter setErrorReporter(ErrorReporter errorReporter) {
        this.errorReporter = errorReporter;
        return errorReporter;
    }

    public static void reportWarning(String str, CodePosition codePosition, CodePosition codePosition2) {
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        if (codePosition == null) {
            $$$reportNull$$$0(1);
        }
        if (codePosition2 == null) {
            $$$reportNull$$$0(2);
        }
        getContext().getErrorReporter().warning(str, codePosition, codePosition2);
    }

    public static void reportError(String str, CodePosition codePosition, CodePosition codePosition2) {
        if (str == null) {
            $$$reportNull$$$0(3);
        }
        if (codePosition == null) {
            $$$reportNull$$$0(4);
        }
        if (codePosition2 == null) {
            $$$reportNull$$$0(5);
        }
        Context currentContext = getCurrentContext();
        if (currentContext != null) {
            currentContext.errorCount++;
            currentContext.getErrorReporter().mo6456error(str, codePosition, codePosition2);
            return;
        }
        throw new EvaluatorException(str);
    }

    public boolean hasFeature(int i) {
        if (i == 1) {
            int i2 = this.version;
            return i2 == 100 || i2 == 110 || i2 == 120;
        }
        if (i == 2 || i == 3) {
            return false;
        }
        if (i == 4) {
            return this.version == 120;
        }
        throw new IllegalArgumentException();
    }

    static String getMessage2(String str, Object obj, Object obj2) {
        return getMessage(str, new Object[]{obj, obj2});
    }

    static Context getContext() {
        Context currentContext = getCurrentContext();
        if (currentContext != null) {
            return currentContext;
        }
        throw new RuntimeException("No Context associated with current Thread");
    }

    static String getMessage(String str, Object[] objArr) {
        try {
            return new MessageFormat(messages.getString(str)).format(objArr);
        } catch (MissingResourceException unused) {
            throw new RuntimeException("No message resource found for message property " + str);
        }
    }

    static void codeBug() {
        throw new RuntimeException("FAILED ASSERTION");
    }
}
