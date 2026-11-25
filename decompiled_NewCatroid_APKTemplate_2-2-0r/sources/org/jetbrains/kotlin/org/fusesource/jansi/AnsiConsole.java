package org.jetbrains.kotlin.org.fusesource.jansi;

import com.huawei.hms.ml.language.common.utils.LanguageCodeUtil;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Locale;
import org.apache.commons.lang3.SystemProperties;
import org.jetbrains.kotlin.org.fusesource.jansi.internal.CLibrary;
import org.jetbrains.kotlin.org.fusesource.jansi.internal.Kernel32;

/* JADX INFO: loaded from: classes4.dex */
public class AnsiConsole {
    private static final boolean IS_CYGWIN;
    private static final boolean IS_MINGW;
    private static final boolean IS_WINDOWS;
    public static final PrintStream err;
    private static int installed;
    public static final PrintStream out;
    public static final PrintStream system_err;
    public static final PrintStream system_out;

    static {
        PrintStream printStream = System.out;
        system_out = printStream;
        PrintStream printStream2 = System.err;
        system_err = printStream2;
        boolean zContains = System.getProperty(SystemProperties.OS_NAME).toLowerCase(Locale.ENGLISH).contains("win");
        IS_WINDOWS = zContains;
        boolean z = zContains && System.getenv("PWD") != null && System.getenv("PWD").startsWith("/") && !"cygwin".equals(System.getenv("TERM"));
        IS_CYGWIN = z;
        boolean z2 = zContains && System.getenv("MSYSTEM") != null && System.getenv("MSYSTEM").startsWith("MINGW");
        IS_MINGW = z2;
        String strName = Charset.defaultCharset().name();
        if (zContains && !z && !z2) {
            int iGetConsoleOutputCP = Kernel32.GetConsoleOutputCP();
            if (Charset.isSupported(LanguageCodeUtil.MS + iGetConsoleOutputCP)) {
                strName = LanguageCodeUtil.MS + iGetConsoleOutputCP;
            } else if (Charset.isSupported("cp" + iGetConsoleOutputCP)) {
                strName = "cp" + iGetConsoleOutputCP;
            }
        }
        try {
            out = new PrintStream(wrapOutputStream(printStream), false, strName);
            err = new PrintStream(wrapErrorOutputStream(printStream2), false, strName);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    private AnsiConsole() {
    }

    public static OutputStream wrapOutputStream(OutputStream outputStream) {
        try {
            return wrapOutputStream(outputStream, CLibrary.STDOUT_FILENO);
        } catch (Throwable unused) {
            return wrapOutputStream(outputStream, 1);
        }
    }

    public static OutputStream wrapErrorOutputStream(OutputStream outputStream) {
        try {
            return wrapOutputStream(outputStream, CLibrary.STDERR_FILENO);
        } catch (Throwable unused) {
            return wrapOutputStream(outputStream, 2);
        }
    }

    public static OutputStream wrapOutputStream(OutputStream outputStream, int i) {
        if (Boolean.getBoolean("jansi.passthrough")) {
            return outputStream;
        }
        if (Boolean.getBoolean("jansi.strip")) {
            return new AnsiOutputStream(outputStream);
        }
        if (IS_WINDOWS && !IS_CYGWIN && !IS_MINGW) {
            try {
                return new WindowsAnsiOutputStream(outputStream);
            } catch (Throwable unused) {
                return new AnsiOutputStream(outputStream);
            }
        }
        try {
            if (!Boolean.getBoolean("jansi.force") && CLibrary.isatty(i) == 0) {
                return new AnsiOutputStream(outputStream);
            }
        } catch (Throwable unused2) {
        }
        return new FilterOutputStream(outputStream) { // from class: org.jetbrains.kotlin.org.fusesource.jansi.AnsiConsole.1
            @Override // java.io.FilterOutputStream, java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
            public void close() throws IOException {
                write(AnsiOutputStream.RESET_CODE);
                flush();
                super.close();
            }
        };
    }

    public static PrintStream out() {
        return out;
    }

    public static PrintStream err() {
        return err;
    }

    public static synchronized void systemInstall() {
        int i = installed + 1;
        installed = i;
        if (i == 1) {
            System.setOut(out);
            System.setErr(err);
        }
    }

    public static synchronized void systemUninstall() {
        int i = installed - 1;
        installed = i;
        if (i == 0) {
            System.setOut(system_out);
            System.setErr(system_err);
        }
    }
}
