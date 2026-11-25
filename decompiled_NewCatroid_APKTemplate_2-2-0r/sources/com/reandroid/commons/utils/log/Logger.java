package com.reandroid.commons.utils.log;

import com.reandroid.commons.utils.ElapsedTime;
import io.netty.util.internal.StringUtil;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes10.dex */
public abstract class Logger {
    private static final long INTERVAL = 2000;
    private static final int MAX_TRACE = 10;
    private static final String TRACE_TAB1 = "    ";
    private static final String TRACE_TAB2 = "        ";
    private static final String TYPE_ERROR = "E";
    private static final String TYPE_INFO = "I";
    private static final String TYPE_WARN = "W";
    private static final long UPDATE_CONSOLE_INTERVAL = 4000;
    private static Logger sInstance;
    private boolean hideTime;
    private int mConsoleWidth;
    private boolean enable = true;
    private final ElapsedTime elapsedTime = new ElapsedTime();
    private final ElapsedTime logTime = new ElapsedTime();
    private final ElapsedTime widthCheckTime = new ElapsedTime();
    private final List<Logger> subLoggers = new ArrayList();

    public abstract void writeLine(String str);

    public abstract void writeSameLine(String str);

    Logger() {
    }

    public void every2Second(String str) {
        if (isEnable() && this.logTime.isExpired(INTERVAL)) {
            this.logTime.reset();
            writeLine(buildLine(null, str).toString());
            Iterator<Logger> it = this.subLoggers.iterator();
            while (it.hasNext()) {
                it.next().info(str);
            }
        }
    }

    public void every2SecondSameLine(String str) {
        if (isEnable() && this.logTime.isExpired(INTERVAL)) {
            this.logTime.reset();
            writeSameLine(buildLine(null, str).toString());
            Iterator<Logger> it = this.subLoggers.iterator();
            while (it.hasNext()) {
                it.next().writeSameLine(str);
            }
        }
    }

    public void onSameLine(String str) {
        if (isEnable()) {
            writeSameLine(buildLine(null, str).toString());
            Iterator<Logger> it = this.subLoggers.iterator();
            while (it.hasNext()) {
                it.next().onSameLine(str);
            }
        }
    }

    public void info(String str) {
        if (isEnable()) {
            writeLine(buildLine(TYPE_INFO, str).toString());
            Iterator<Logger> it = this.subLoggers.iterator();
            while (it.hasNext()) {
                it.next().info(str);
            }
        }
    }

    public void warn(String str) {
        if (isEnable()) {
            writeLine(buildLine("W", str).toString());
            Iterator<Logger> it = this.subLoggers.iterator();
            while (it.hasNext()) {
                it.next().warn(str);
            }
        }
    }

    public void error(String str) {
        if (isEnable()) {
            writeLine(buildLine("E", str).toString());
            Iterator<Logger> it = this.subLoggers.iterator();
            while (it.hasNext()) {
                it.next().error(str);
            }
        }
    }

    public void error(Throwable th) {
        if (isEnable()) {
            error(null, th);
        }
    }

    public void error(String str, Throwable th) {
        StringBuilder sb;
        if (isEnable()) {
            if (str != null) {
                sb = buildLine("E", str);
            } else {
                sb = new StringBuilder();
            }
            if (th != null) {
                if (str == null) {
                    sb = buildLine("E", th.getClass().getName() + " " + th.getMessage());
                } else {
                    sb.append(StringUtil.SPACE).append(th.getClass().getSimpleName()).append(StringUtil.SPACE).append(th.getMessage());
                }
                appendStackTraces(sb, th);
            }
            writeLine(sb.toString());
            Iterator<Logger> it = this.subLoggers.iterator();
            while (it.hasNext()) {
                it.next().error(str, th);
            }
        }
    }

    public void addSubLogger(Logger logger) {
        if (logger == null || logger == this || this.subLoggers.contains(logger)) {
            return;
        }
        this.subLoggers.add(logger);
    }

    public boolean isEnable() {
        return this.enable;
    }

    public void setEnable(boolean z) {
        setEnable(z, false);
    }

    public void setEnable(boolean z, boolean z2) {
        this.enable = z;
        if (z2) {
            Iterator<Logger> it = this.subLoggers.iterator();
            while (it.hasNext()) {
                it.next().setEnable(z, true);
            }
        }
    }

    public boolean isHideTime() {
        return this.hideTime;
    }

    public void setHideTime(boolean z) {
        this.hideTime = z;
    }

    private void appendStackTraces(StringBuilder sb, Throwable th) {
        appendStackTraces(sb, th.getStackTrace());
        Throwable cause = th.getCause();
        if (cause == null) {
            return;
        }
        sb.append('\n').append(TRACE_TAB1).append("Caused by: ");
        sb.append(cause.getClass().getSimpleName());
        sb.append(StringUtil.SPACE).append(cause.getMessage());
        appendStackTraces(sb, cause.getStackTrace());
    }

    private void appendStackTraces(StringBuilder sb, StackTraceElement[] stackTraceElementArr) {
        if (stackTraceElementArr == null) {
            return;
        }
        int length = stackTraceElementArr.length;
        if (length > 10) {
            length = 10;
        }
        for (int i = 0; i < length; i++) {
            appendStackTrace(sb, stackTraceElementArr[i]);
        }
    }

    private void appendStackTrace(StringBuilder sb, StackTraceElement stackTraceElement) {
        if (stackTraceElement == null) {
            return;
        }
        sb.append('\n');
        sb.append(TRACE_TAB2);
        sb.append("at ").append(stackTraceElement.toString());
    }

    private StringBuilder buildLine(String str, String str2) {
        StringBuilder sb = new StringBuilder();
        if (!isHideTime()) {
            sb.append(this.elapsedTime.now());
            sb.append(StringUtil.SPACE);
        }
        if (str != null) {
            sb.append(str).append(": ");
        }
        sb.append(str2);
        return sb;
    }

    public static void i(String str) {
        getLogger().info(str);
    }

    public static void w(String str) {
        getLogger().warn(str);
    }

    public static void e(String str) {
        getLogger().error(str);
    }

    public static void e(Throwable th) {
        getLogger().error(th);
    }

    public static void e(String str, Throwable th) {
        getLogger().error(str, th);
    }

    public static void sameLine(String str) {
        getLogger().onSameLine(str);
    }

    public static void timely(String str) {
        getLogger().every2Second(str);
    }

    public static void timelySameLine(String str) {
        getLogger().every2SecondSameLine(str);
    }

    int checkConsoleWidth() {
        if (this.mConsoleWidth == 0) {
            updateConsoleWidth();
            return this.mConsoleWidth;
        }
        Boolean succeedOnce = ConsoleUtil.getSucceedOnce();
        if (succeedOnce != null && !succeedOnce.booleanValue()) {
            return this.mConsoleWidth;
        }
        if (this.widthCheckTime.isExpired(UPDATE_CONSOLE_INTERVAL)) {
            updateConsoleWidth();
        }
        return this.mConsoleWidth;
    }

    private void updateConsoleWidth() {
        this.widthCheckTime.reset();
        if (this.mConsoleWidth == 0) {
            this.mConsoleWidth = 80;
        }
        this.mConsoleWidth = ConsoleUtil.getConsoleWidth();
    }

    public static void clearFileLoggers() {
        Logger logger = getLogger();
        for (Logger logger2 : new ArrayList(logger.subLoggers)) {
            if (logger2 instanceof FileLogger) {
                FileLogger fileLogger = (FileLogger) logger2;
                fileLogger.onShutdown();
                logger.subLoggers.remove(fileLogger);
            }
        }
    }

    public static void addFileLogger(File file) {
        getLogger().addSubLogger(FileLogger.create(file));
    }

    public static void enableStdLogger(boolean z) {
        Logger logger = getLogger();
        logger.setEnable(z, false);
        for (Logger logger2 : logger.subLoggers) {
            if (!(logger2 instanceof StdLogger)) {
                logger2.setEnable(z, false);
            }
        }
    }

    public static Logger getLogger() {
        StdLogger stdLogger;
        Logger logger = sInstance;
        if (logger != null) {
            return logger;
        }
        synchronized (Logger.class) {
            stdLogger = StdLogger.getInstance();
            sInstance = stdLogger;
        }
        return stdLogger;
    }
}
