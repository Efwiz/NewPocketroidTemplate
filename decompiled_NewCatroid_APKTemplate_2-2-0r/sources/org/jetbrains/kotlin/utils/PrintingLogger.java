package org.jetbrains.kotlin.utils;

import java.io.PrintStream;
import org.bouncycastle.i18n.ErrorBundle;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.org.apache.log4j.Level;

/* JADX INFO: loaded from: classes6.dex */
public class PrintingLogger extends Logger {
    private final PrintStream out;
    public static final Logger SYSTEM_OUT = new PrintingLogger(System.out);
    public static final Logger SYSTEM_ERR = new PrintingLogger(System.err);

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 1) {
            objArr[0] = "out";
        } else {
            objArr[0] = ErrorBundle.DETAIL_ENTRY;
        }
        objArr[1] = "org/jetbrains/kotlin/utils/PrintingLogger";
        if (i != 1) {
            objArr[2] = "<init>";
        } else {
            objArr[2] = "error";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger
    public boolean isDebugEnabled() {
        return true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger
    public void setLevel(Level level) {
    }

    public PrintingLogger(PrintStream printStream) {
        if (printStream == null) {
            $$$reportNull$$$0(0);
        }
        this.out = printStream;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger
    public void debug(String str) {
        this.out.println(str);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger
    public void debug(Throwable th) {
        if (th != null) {
            th.printStackTrace(this.out);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger
    public void debug(String str, Throwable th) {
        debug(str);
        debug(th);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger
    public void info(String str) {
        debug(str);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger
    public void info(String str, Throwable th) {
        debug(str, th);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger
    public void warn(String str, Throwable th) {
        debug(str, th);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger
    public void error(String str, Throwable th, String... strArr) {
        if (strArr == null) {
            $$$reportNull$$$0(1);
        }
        debug(str, th);
        for (String str2 : strArr) {
            debug(str2);
        }
    }
}
