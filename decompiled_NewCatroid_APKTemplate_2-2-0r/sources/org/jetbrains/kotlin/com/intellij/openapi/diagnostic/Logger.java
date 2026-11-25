package org.jetbrains.kotlin.com.intellij.openapi.diagnostic;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import com.huawei.hms.ml.language.common.utils.Constant;
import com.reandroid.arsc.value.CompoundEntry;
import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Function;
import org.bouncycastle.i18n.ErrorBundle;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.cli.common.arguments.CommonCompilerArguments;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtilRt;
import org.jetbrains.kotlin.com.intellij.util.ExceptionUtil;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;
import org.jetbrains.kotlin.org.apache.log4j.Level;

/* JADX INFO: loaded from: classes3.dex */
public abstract class Logger {
    private static boolean isUnitTestMode;
    private static Factory ourFactory = new DefaultFactory();
    static final Function<Attachment, String> ATTACHMENT_TO_STRING = new Function() { // from class: org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger$$ExternalSyntheticLambda1
        @Override // java.util.function.Function
        public final Object apply(Object obj) {
            return Logger.lambda$static$0((Attachment) obj);
        }
    };

    public interface Factory {
        Logger getLoggerInstance(String str);
    }

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 3 || i == 5) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 3 || i == 5) ? 2 : 3];
        switch (i) {
            case 2:
                objArr[0] = "category";
                break;
            case 3:
            case 5:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/diagnostic/Logger";
                break;
            case 4:
                objArr[0] = "cl";
                break;
            case 6:
            case 11:
            case 14:
                objArr[0] = CrashHianalyticsData.MESSAGE;
                break;
            case 7:
            case 20:
                objArr[0] = ErrorBundle.DETAIL_ENTRY;
                break;
            case 8:
                objArr[0] = "header";
                break;
            case 9:
                objArr[0] = CompoundEntry.NAME_values;
                break;
            case 10:
            case 12:
            case 13:
            case 15:
            case 16:
            case 17:
            case 21:
            case 22:
                objArr[0] = "t";
                break;
            case 18:
            case 19:
                objArr[0] = "attachments";
                break;
            default:
                objArr[0] = "factory";
                break;
        }
        if (i == 3 || i == 5) {
            objArr[1] = "getInstance";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/diagnostic/Logger";
        }
        switch (i) {
            case 2:
            case 4:
                objArr[2] = "getInstance";
                break;
            case 3:
            case 5:
                break;
            case 6:
            case 7:
                objArr[2] = "debug";
                break;
            case 8:
            case 9:
                objArr[2] = "debugValues";
                break;
            case 10:
            case 11:
            case 12:
                objArr[2] = "infoWithDebug";
                break;
            case 13:
            case 14:
            case 15:
                objArr[2] = "warnWithDebug";
                break;
            case 16:
                objArr[2] = CompilerOptions.INFO;
                break;
            case 17:
                objArr[2] = CommonCompilerArguments.WARN;
                break;
            case 18:
            case 19:
            case 20:
            case 21:
                objArr[2] = "error";
                break;
            case 22:
                objArr[2] = "warnInProduction";
                break;
            default:
                objArr[2] = "setFactory";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 3 && i != 5) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public abstract void debug(String str);

    public abstract void debug(String str, Throwable th);

    public abstract void debug(Throwable th);

    public abstract void error(String str, Throwable th, String... strArr);

    public abstract void info(String str);

    public abstract void info(String str, Throwable th);

    public abstract boolean isDebugEnabled();

    public abstract void setLevel(Level level);

    public abstract void warn(String str, Throwable th);

    private static final class DefaultFactory implements Factory {
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "category", "org/jetbrains/kotlin/com/intellij/openapi/diagnostic/Logger$DefaultFactory", "getLoggerInstance"));
        }

        private DefaultFactory() {
        }

        @Override // org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger.Factory
        public Logger getLoggerInstance(String str) {
            if (str == null) {
                $$$reportNull$$$0(0);
            }
            return new DefaultLogger(str);
        }
    }

    public static void setFactory(Class<? extends Factory> cls) {
        if (cls == null) {
            $$$reportNull$$$0(0);
        }
        if (isInitialized()) {
            if (cls.isInstance(ourFactory)) {
                return;
            } else {
                logFactoryChanged(cls);
            }
        }
        try {
            Constructor<? extends Factory> declaredConstructor = cls.getDeclaredConstructor(new Class[0]);
            declaredConstructor.setAccessible(true);
            ourFactory = declaredConstructor.newInstance(new Object[0]);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static void setFactory(Factory factory) {
        if (factory == null) {
            $$$reportNull$$$0(1);
        }
        if (isInitialized()) {
            logFactoryChanged(factory.getClass());
        }
        ourFactory = factory;
    }

    private static void logFactoryChanged(Class<? extends Factory> cls) {
        System.out.println("Changing log factory from " + ourFactory.getClass().getCanonicalName() + " to " + cls.getCanonicalName() + '\n' + ExceptionUtil.getThrowableText(new Throwable()));
    }

    public static Factory getFactory() {
        return ourFactory;
    }

    public static boolean isInitialized() {
        return !(ourFactory instanceof DefaultFactory);
    }

    public static Logger getInstance(String str) {
        if (str == null) {
            $$$reportNull$$$0(2);
        }
        Logger loggerInstance = ourFactory.getLoggerInstance(str);
        if (loggerInstance == null) {
            $$$reportNull$$$0(3);
        }
        return loggerInstance;
    }

    public static Logger getInstance(Class<?> cls) {
        if (cls == null) {
            $$$reportNull$$$0(4);
        }
        Logger loggerInstance = ourFactory.getLoggerInstance("#" + cls.getName());
        if (loggerInstance == null) {
            $$$reportNull$$$0(5);
        }
        return loggerInstance;
    }

    public void debug(String str, Object... objArr) {
        if (str == null) {
            $$$reportNull$$$0(6);
        }
        if (objArr == null) {
            $$$reportNull$$$0(7);
        }
        if (isDebugEnabled()) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            for (Object obj : objArr) {
                sb.append(obj);
            }
            debug(sb.toString());
        }
    }

    public void debugValues(String str, Collection<?> collection) {
        if (str == null) {
            $$$reportNull$$$0(8);
        }
        if (collection == null) {
            $$$reportNull$$$0(9);
        }
        if (isDebugEnabled()) {
            StringBuilder sb = new StringBuilder();
            sb.append(str).append(" (").append(collection.size()).append(Constant.AFTER_QUTO);
            if (!collection.isEmpty()) {
                sb.append(":");
                for (Object obj : collection) {
                    sb.append("\n");
                    sb.append(obj);
                }
            }
            debug(sb.toString());
        }
    }

    public final void infoWithDebug(Throwable th) {
        if (th == null) {
            $$$reportNull$$$0(10);
        }
        infoWithDebug(th.toString(), th);
    }

    public final void infoWithDebug(String str, Throwable th) {
        if (str == null) {
            $$$reportNull$$$0(11);
        }
        if (th == null) {
            $$$reportNull$$$0(12);
        }
        info(str);
        debug(th);
    }

    public final void warnWithDebug(Throwable th) {
        if (th == null) {
            $$$reportNull$$$0(13);
        }
        warnWithDebug(th.toString(), th);
    }

    public final void warnWithDebug(String str, Throwable th) {
        if (str == null) {
            $$$reportNull$$$0(14);
        }
        if (th == null) {
            $$$reportNull$$$0(15);
        }
        warn(str);
        debug(th);
    }

    public boolean isTraceEnabled() {
        return isDebugEnabled();
    }

    public void trace(String str) {
        debug(str);
    }

    public void trace(Throwable th) {
        debug(th);
    }

    public void info(Throwable th) {
        if (th == null) {
            $$$reportNull$$$0(16);
        }
        info(th.getMessage(), th);
    }

    public void warn(String str) {
        warn(str, null);
    }

    public void warn(Throwable th) {
        if (th == null) {
            $$$reportNull$$$0(17);
        }
        warn(th.getMessage(), th);
    }

    public void error(String str) {
        error(str, new Throwable(str), ArrayUtilRt.EMPTY_STRING_ARRAY);
    }

    public void error(Object obj) {
        error(String.valueOf(obj));
    }

    static /* synthetic */ String lambda$static$0(Attachment attachment) {
        return attachment.getPath() + "\n" + attachment.getDisplayText();
    }

    public void error(String str, Attachment... attachmentArr) {
        if (attachmentArr == null) {
            $$$reportNull$$$0(18);
        }
        error(str, (Throwable) null, attachmentArr);
    }

    public void error(String str, Throwable th, Attachment... attachmentArr) {
        if (attachmentArr == null) {
            $$$reportNull$$$0(19);
        }
        final Function<Attachment, String> function = ATTACHMENT_TO_STRING;
        Objects.requireNonNull(function);
        error(str, th, (String[]) ContainerUtil.map2Array(attachmentArr, String.class, new org.jetbrains.kotlin.com.intellij.util.Function() { // from class: org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.util.Function
            public final Object fun(Object obj) {
                return (String) function.apply((Attachment) obj);
            }
        }));
    }

    public void error(String str, String... strArr) {
        if (strArr == null) {
            $$$reportNull$$$0(20);
        }
        error(str, new Throwable(str), strArr);
    }

    public void error(String str, Throwable th) {
        error(str, th, ArrayUtilRt.EMPTY_STRING_ARRAY);
    }

    public void error(Throwable th) {
        if (th == null) {
            $$$reportNull$$$0(21);
        }
        error(th.getMessage(), th, ArrayUtilRt.EMPTY_STRING_ARRAY);
    }

    public boolean assertTrue(boolean z, Object obj) {
        if (!z) {
            String str = obj != null ? "Assertion failed: " + obj : "Assertion failed";
            error(str, new Throwable(str));
        }
        return z;
    }

    public boolean assertTrue(boolean z) {
        return z || assertTrue(false, null);
    }

    protected static Throwable ensureNotControlFlow(Throwable th) {
        return th instanceof ControlFlowException ? new Throwable("Control-flow exceptions (like " + th.getClass().getSimpleName() + ") should never be logged: ignore for explicitly started processes or rethrow to handle on the outer process level", th) : th;
    }

    public static void setUnitTestMode() {
        isUnitTestMode = true;
    }

    public void warnInProduction(Throwable th) {
        if (th == null) {
            $$$reportNull$$$0(22);
        }
        if (isUnitTestMode) {
            error(th);
        } else {
            warn(th);
        }
    }
}
