package org.jetbrains.kotlin.com.intellij.openapi.diagnostic;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.bouncycastle.i18n.ErrorBundle;
import org.jetbrains.kotlin.com.intellij.util.ExceptionUtil;
import org.jetbrains.kotlin.org.apache.log4j.Level;

/* JADX INFO: loaded from: classes3.dex */
public class DefaultLogger extends Logger {
    private static boolean ourMirrorToStderr = true;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 3 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 3 ? 3 : 2];
        if (i == 2) {
            objArr[0] = FirebaseAnalytics.Param.LEVEL;
        } else if (i == 3) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/diagnostic/DefaultLogger";
        } else if (i != 4) {
            objArr[0] = ErrorBundle.DETAIL_ENTRY;
        } else {
            objArr[0] = "parentDisposable";
        }
        if (i != 3) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/diagnostic/DefaultLogger";
        } else {
            objArr[1] = "attachmentsToString";
        }
        if (i == 1) {
            objArr[2] = "dumpExceptionsToStderr";
        } else if (i == 2) {
            objArr[2] = "setLevel";
        } else if (i != 3) {
            if (i != 4) {
                objArr[2] = "error";
            } else {
                objArr[2] = "disableStderrDumping";
            }
        }
        String str2 = String.format(str, objArr);
        if (i == 3) {
            throw new IllegalStateException(str2);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger
    public void debug(String str) {
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger
    public void debug(String str, Throwable th) {
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger
    public void debug(Throwable th) {
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger
    public void info(String str) {
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger
    public void info(String str, Throwable th) {
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger
    public boolean isDebugEnabled() {
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger
    public void setLevel(Level level) {
        if (level == null) {
            $$$reportNull$$$0(2);
        }
    }

    public DefaultLogger(String str) {
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger
    public void warn(String str, Throwable th) {
        Throwable thEnsureNotControlFlow = ensureNotControlFlow(th);
        System.err.println("WARN: " + str);
        if (thEnsureNotControlFlow != null) {
            thEnsureNotControlFlow.printStackTrace(System.err);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger
    public void error(String str, Throwable th, String... strArr) {
        if (strArr == null) {
            $$$reportNull$$$0(0);
        }
        Throwable thEnsureNotControlFlow = ensureNotControlFlow(th);
        String str2 = str + attachmentsToString(thEnsureNotControlFlow);
        dumpExceptionsToStderr(str2, thEnsureNotControlFlow, strArr);
        throw new AssertionError(str2, thEnsureNotControlFlow);
    }

    public static void dumpExceptionsToStderr(String str, Throwable th, String... strArr) {
        if (strArr == null) {
            $$$reportNull$$$0(1);
        }
        if (shouldDumpExceptionToStderr()) {
            System.err.println("ERROR: " + str);
            if (th != null) {
                th.printStackTrace(System.err);
            }
            if (strArr.length > 0) {
                System.err.println("details: ");
                for (String str2 : strArr) {
                    System.err.println(str2);
                }
            }
        }
    }

    public static String attachmentsToString(Throwable th) {
        if (th != null) {
            String str = (String) ExceptionUtil.findCauseAndSuppressed(th, ExceptionWithAttachments.class).stream().flatMap(new Function() { // from class: org.jetbrains.kotlin.com.intellij.openapi.diagnostic.DefaultLogger$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return Stream.of((Object[]) ((ExceptionWithAttachments) obj).getAttachments());
                }
            }).map(ATTACHMENT_TO_STRING).collect(Collectors.joining("\n----\n", "\n\nAttachments:\n", ""));
            if (!str.equals("\n\nAttachments:\n")) {
                if (str == null) {
                    $$$reportNull$$$0(3);
                }
                return str;
            }
        }
        return "";
    }

    public static boolean shouldDumpExceptionToStderr() {
        return ourMirrorToStderr;
    }
}
