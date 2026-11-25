package com.reandroid.commons.utils.log;

import io.netty.util.internal.StringUtil;
import java.io.PrintStream;

/* JADX INFO: loaded from: classes10.dex */
public class StdLogger extends Logger {
    private static StdLogger sInstance;
    private boolean mSameLine;
    private final Object mLock = new Object();
    private final PrintStream printStream = System.err;

    private StdLogger() {
    }

    @Override // com.reandroid.commons.utils.log.Logger
    public void writeLine(String str) {
        synchronized (this.mLock) {
            if (str == null) {
                str = "null";
            }
            if (this.mSameLine) {
                writeSameLine(" ");
                this.mSameLine = false;
            }
            this.printStream.println(str);
        }
    }

    @Override // com.reandroid.commons.utils.log.Logger
    public void writeSameLine(String str) {
        synchronized (this.mLock) {
            if (str == null) {
                str = "null";
            }
            this.mSameLine = true;
            int iCheckConsoleWidth = checkConsoleWidth();
            StringBuilder sb = new StringBuilder();
            sb.append('\r');
            for (int i = 0; i < iCheckConsoleWidth; i++) {
                sb.append(StringUtil.SPACE);
            }
            sb.append('\r');
            int i2 = iCheckConsoleWidth - 1;
            sb.append(StringUtil.SPACE);
            if (str.length() > i2) {
                str = str.substring(0, i2);
            }
            sb.append(str);
            sb.append('\r');
            this.printStream.print(sb.toString());
        }
    }

    static StdLogger getInstance() {
        StdLogger stdLogger;
        StdLogger stdLogger2 = sInstance;
        if (stdLogger2 != null) {
            return stdLogger2;
        }
        synchronized (StdLogger.class) {
            StdLogger stdLogger3 = new StdLogger();
            sInstance = stdLogger3;
            stdLogger3.setEnable(true);
            stdLogger = sInstance;
        }
        return stdLogger;
    }
}
