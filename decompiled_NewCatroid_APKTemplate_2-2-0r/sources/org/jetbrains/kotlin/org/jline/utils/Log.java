package org.jetbrains.kotlin.org.jline.utils;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/* JADX INFO: loaded from: classes4.dex */
public final class Log {
    public static void trace(Object... objArr) {
        log(Level.FINEST, objArr);
    }

    public static void trace(Supplier<String> supplier) {
        log(Level.FINEST, supplier);
    }

    public static void debug(Supplier<String> supplier) {
        log(Level.FINE, supplier);
    }

    public static void debug(Object... objArr) {
        log(Level.FINE, objArr);
    }

    public static void info(Object... objArr) {
        log(Level.INFO, objArr);
    }

    public static void warn(Object... objArr) {
        log(Level.WARNING, objArr);
    }

    public static boolean isDebugEnabled() {
        return isEnabled(Level.FINE);
    }

    static void render(PrintStream printStream, Object obj) {
        if (obj != null && obj.getClass().isArray()) {
            Object[] objArr = (Object[]) obj;
            printStream.print("[");
            int i = 0;
            while (i < objArr.length) {
                printStream.print(objArr[i]);
                i++;
                if (i < objArr.length) {
                    printStream.print(",");
                }
            }
            printStream.print("]");
            return;
        }
        printStream.print(obj);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Code duplicated, block: B:10:0x001e  */
    public static LogRecord createRecord(Level level, Object... objArr) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(byteArrayOutputStream);
        Throwable th = null;
        int i = 0;
        while (i < objArr.length) {
            int i2 = i + 1;
            if (i2 == objArr.length) {
                Object obj = objArr[i];
                if (obj instanceof Throwable) {
                    th = (Throwable) obj;
                } else {
                    render(printStream, objArr[i]);
                }
            } else {
                render(printStream, objArr[i]);
            }
            i = i2;
        }
        printStream.close();
        LogRecord logRecord = new LogRecord(level, byteArrayOutputStream.toString());
        logRecord.setThrown(th);
        return logRecord;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static LogRecord createRecord(Level level, Supplier<String> supplier) {
        return new LogRecord(level, supplier.get());
    }

    static void log(final Level level, final Supplier<String> supplier) {
        logr(level, new Supplier() { // from class: org.jetbrains.kotlin.org.jline.utils.Log$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return Log.createRecord(level, (Supplier<String>) supplier);
            }
        });
    }

    static void log(final Level level, final Object... objArr) {
        logr(level, new Supplier() { // from class: org.jetbrains.kotlin.org.jline.utils.Log$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
                return Log.createRecord(level, objArr);
            }
        });
    }

    static void logr(Level level, Supplier<LogRecord> supplier) {
        Logger logger = Logger.getLogger("org.jetbrains.kotlin.org.jline");
        if (logger.isLoggable(level)) {
            LogRecord logRecord = supplier.get();
            logRecord.setLoggerName(logger.getName());
            logger.log(logRecord);
        }
    }

    static boolean isEnabled(Level level) {
        return Logger.getLogger("org.jetbrains.kotlin.org.jline").isLoggable(level);
    }
}
