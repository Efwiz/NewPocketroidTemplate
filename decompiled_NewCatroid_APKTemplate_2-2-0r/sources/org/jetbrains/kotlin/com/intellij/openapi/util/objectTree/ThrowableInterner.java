package org.jetbrains.kotlin.com.intellij.openapi.util.objectTree;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Objects;
import org.jetbrains.kotlin.com.intellij.openapi.util.Comparing;
import org.jetbrains.kotlin.com.intellij.util.ReflectionUtil;
import org.jetbrains.kotlin.com.intellij.util.containers.HashingStrategy;
import org.jetbrains.kotlin.com.intellij.util.containers.Interner;
import org.jetbrains.kotlin.com.intellij.util.containers.WeakInterner;

/* JADX INFO: loaded from: classes3.dex */
public final class ThrowableInterner {
    private static final Interner<Throwable> myTraceInterner = new WeakInterner(new HashingStrategy<Throwable>() { // from class: org.jetbrains.kotlin.com.intellij.openapi.util.objectTree.ThrowableInterner.1
        @Override // org.jetbrains.kotlin.com.intellij.util.containers.HashingStrategy
        public int hashCode(Throwable th) {
            return ThrowableInterner.computeHashCode(th);
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.containers.HashingStrategy
        public boolean equals(Throwable th, Throwable th2) {
            if (th == th2) {
                return true;
            }
            if (th == null || th2 == null || !Comparing.equal(th.getClass(), th2.getClass()) || !Objects.equals(th.getMessage(), th2.getMessage()) || !equals(th.getCause(), th2.getCause())) {
                return false;
            }
            Object[] backtrace = ThrowableInterner.getBacktrace(th);
            Object[] backtrace2 = ThrowableInterner.getBacktrace(th2);
            if (backtrace != null && backtrace2 != null) {
                return Arrays.deepEquals(backtrace, backtrace2);
            }
            return Arrays.equals(th.getStackTrace(), th2.getStackTrace());
        }
    });
    private static final Field BACKTRACE_FIELD = ReflectionUtil.getDeclaredField(Throwable.class, "backtrace");

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 6 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 6 ? 3 : 2];
        if (i != 6) {
            objArr[0] = "throwable";
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/util/objectTree/ThrowableInterner";
        }
        if (i != 6) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/util/objectTree/ThrowableInterner";
        } else {
            objArr[1] = "intern";
        }
        switch (i) {
            case 1:
                objArr[2] = "computeTraceHashCode";
                break;
            case 2:
                objArr[2] = "computeAccurateTraceHashCode";
                break;
            case 3:
                objArr[2] = "getBacktrace";
                break;
            case 4:
                objArr[2] = "clearBacktrace";
                break;
            case 5:
                objArr[2] = "intern";
                break;
            case 6:
                break;
            default:
                objArr[2] = "computeHashCode";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i == 6) {
            throw new IllegalStateException(str2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int computeHashCode(Throwable th) {
        if (th == null) {
            $$$reportNull$$$0(0);
        }
        String message = th.getMessage();
        if (message != null) {
            return message.hashCode();
        }
        return computeTraceHashCode(th);
    }

    public static int computeTraceHashCode(Throwable th) {
        if (th == null) {
            $$$reportNull$$$0(1);
        }
        Object[] backtrace = getBacktrace(th);
        if (backtrace == null) {
            return Arrays.hashCode(th.getStackTrace());
        }
        for (Object obj : backtrace) {
            if (obj instanceof Object[]) {
                return Arrays.hashCode((Object[]) obj);
            }
        }
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Object[] getBacktrace(Throwable th) {
        if (th == null) {
            $$$reportNull$$$0(3);
        }
        try {
            Field field = BACKTRACE_FIELD;
            Object obj = field != null ? field.get(th) : null;
            if (obj instanceof Object[]) {
                return (Object[]) obj;
            }
            return null;
        } catch (Throwable unused) {
            return null;
        }
    }

    public static Throwable intern(Throwable th) {
        if (th == null) {
            $$$reportNull$$$0(5);
        }
        if (getBacktrace(th) != null) {
            th = myTraceInterner.intern(th);
        }
        if (th == null) {
            $$$reportNull$$$0(6);
        }
        return th;
    }
}
