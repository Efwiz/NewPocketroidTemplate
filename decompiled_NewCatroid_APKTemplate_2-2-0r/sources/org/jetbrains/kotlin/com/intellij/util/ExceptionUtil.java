package org.jetbrains.kotlin.com.intellij.util;

import com.huawei.hms.ml.language.common.utils.LanguageCodeUtil;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;

/* JADX INFO: loaded from: classes3.dex */
public final class ExceptionUtil extends ExceptionUtilRt {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 6 || i == 8 || i == 11 || i == 18 || i == 21 || i == 13 || i == 14) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 6 || i == 8 || i == 11 || i == 18 || i == 21 || i == 13 || i == 14) ? 2 : 3];
        switch (i) {
            case 1:
            case 6:
            case 8:
            case 11:
            case 13:
            case 14:
            case 18:
            case 21:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/util/ExceptionUtil";
                break;
            case 2:
                objArr[0] = "error";
                break;
            case 3:
                objArr[0] = "klass";
                break;
            case 4:
                objArr[0] = LanguageCodeUtil.TH;
                break;
            case 5:
                objArr[0] = "relativeTo";
                break;
            case 7:
            case 19:
                objArr[0] = "t";
                break;
            case 9:
            case 12:
                objArr[0] = "aThrowable";
                break;
            case 10:
                objArr[0] = "stackFrameSkipPattern";
                break;
            case 15:
            default:
                objArr[0] = "e";
                break;
            case 16:
                objArr[0] = "result";
                break;
            case 17:
                objArr[0] = "errorPattern";
                break;
            case 20:
                objArr[0] = "defaultMessage";
                break;
            case 22:
                objArr[0] = "runnable";
                break;
        }
        if (i == 1) {
            objArr[1] = "getRootCause";
        } else if (i == 6) {
            objArr[1] = "makeStackTraceRelative";
        } else if (i == 8 || i == 11) {
            objArr[1] = "getThrowableText";
        } else if (i == 18) {
            objArr[1] = "extractMessage";
        } else if (i == 21) {
            objArr[1] = "getNonEmptyMessage";
        } else if (i == 13 || i == 14) {
            objArr[1] = "getUserStackTrace";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/ExceptionUtil";
        }
        switch (i) {
            case 1:
            case 6:
            case 8:
            case 11:
            case 13:
            case 14:
            case 18:
            case 21:
                break;
            case 2:
            case 3:
                objArr[2] = "findCauseAndSuppressed";
                break;
            case 4:
            case 5:
                objArr[2] = "makeStackTraceRelative";
                break;
            case 7:
            case 9:
            case 10:
                objArr[2] = "getThrowableText";
                break;
            case 12:
                objArr[2] = "getUserStackTrace";
                break;
            case 15:
                objArr[2] = "getMessage";
                break;
            case 16:
            case 17:
                objArr[2] = "extractMessage";
                break;
            case 19:
            case 20:
                objArr[2] = "getNonEmptyMessage";
                break;
            case 22:
                objArr[2] = "runAndCatch";
                break;
            default:
                objArr[2] = "getRootCause";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 6 && i != 8 && i != 11 && i != 18 && i != 21 && i != 13 && i != 14) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public static Throwable getRootCause(Throwable th) {
        if (th == null) {
            $$$reportNull$$$0(0);
        }
        while (th.getCause() != null) {
            th = th.getCause();
        }
        if (th == null) {
            $$$reportNull$$$0(1);
        }
        return th;
    }

    public static <T> List<T> findCauseAndSuppressed(Throwable th, Class<T> cls) {
        if (th == null) {
            $$$reportNull$$$0(2);
        }
        if (cls == null) {
            $$$reportNull$$$0(3);
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        ArrayDeque arrayDeque = new ArrayDeque();
        arrayDeque.add(th);
        while (!arrayDeque.isEmpty()) {
            Throwable th2 = (Throwable) arrayDeque.removeFirst();
            if (linkedHashSet.add(th2)) {
                for (Throwable cause = th2.getCause(); cause != null; cause = cause.getCause()) {
                    arrayDeque.addLast(cause);
                }
                for (Throwable th3 : th2.getSuppressed()) {
                    arrayDeque.addLast(th3);
                }
            }
        }
        return ContainerUtil.filterIsInstance(linkedHashSet, cls);
    }

    public static Throwable makeStackTraceRelative(Throwable th, Throwable th2) {
        if (th == null) {
            $$$reportNull$$$0(4);
        }
        if (th2 == null) {
            $$$reportNull$$$0(5);
        }
        StackTraceElement[] stackTrace = th.getStackTrace();
        StackTraceElement[] stackTrace2 = th2.getStackTrace();
        int iMin = Math.min(stackTrace.length, stackTrace2.length);
        for (int i = 0; i < iMin; i++) {
            if (!stackTrace[(stackTrace.length - i) - 1].equals(stackTrace2[(stackTrace2.length - i) - 1])) {
                th.setStackTrace((StackTraceElement[]) Arrays.copyOf(stackTrace, stackTrace.length - i));
                break;
            }
        }
        if (th == null) {
            $$$reportNull$$$0(6);
        }
        return th;
    }

    public static String currentStackTrace() {
        return getThrowableText(new Throwable());
    }

    public static String getThrowableText(Throwable th) {
        if (th == null) {
            $$$reportNull$$$0(7);
        }
        StringWriter stringWriter = new StringWriter();
        th.printStackTrace(new PrintWriter(stringWriter));
        String string = stringWriter.getBuffer().toString();
        if (string == null) {
            $$$reportNull$$$0(8);
        }
        return string;
    }

    public static String getMessage(Throwable th) {
        if (th == null) {
            $$$reportNull$$$0(15);
        }
        String message = th.getMessage();
        while (th.getCause() != null && (message == null || message.contains("Exception: ") || message.contains("Error: "))) {
            th = th.getCause();
            message = th.getMessage();
        }
        return message != null ? extractMessage(extractMessage(message, "Exception: "), "Error: ") : message;
    }

    private static String extractMessage(String str, String str2) {
        if (str == null) {
            $$$reportNull$$$0(16);
        }
        if (str2 == null) {
            $$$reportNull$$$0(17);
        }
        if (str.lastIndexOf(str2) >= 0) {
            str = str.substring(str.lastIndexOf(str2) + str2.length());
        }
        if (str == null) {
            $$$reportNull$$$0(18);
        }
        return str;
    }

    public static void rethrowUnchecked(Throwable th) {
        ExceptionUtilRt.rethrowUnchecked(th);
    }

    public static void rethrow(Throwable th) {
        rethrowUnchecked(th);
        throw new RuntimeException(th);
    }

    public static void rethrowAllAsUnchecked(Throwable th) {
        if (th != null) {
            rethrow(th);
        }
    }

    public static Exception runAndCatch(ThrowableRunnable<? extends Exception> throwableRunnable) throws Throwable {
        if (throwableRunnable == null) {
            $$$reportNull$$$0(22);
        }
        try {
            throwableRunnable.run();
            return null;
        } catch (Exception e) {
            return e;
        }
    }
}
