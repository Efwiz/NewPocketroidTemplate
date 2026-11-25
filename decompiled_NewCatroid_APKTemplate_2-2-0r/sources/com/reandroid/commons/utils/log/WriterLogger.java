package com.reandroid.commons.utils.log;

import com.reandroid.commons.utils.ShutdownHook;
import com.reandroid.commons.utils.StringUtil;
import java.io.IOException;
import java.io.Writer;

/* JADX INFO: loaded from: classes10.dex */
public class WriterLogger extends Logger implements ShutdownHook.ShutdownListener {
    private static final int CHECK_LINE_INTERVAL = 100;
    private boolean ignoreSameLine;
    private int initialLinesCount;
    private int mLineCount;
    private final Object mLock;
    private Writer mWriter;
    private boolean mWrittenOnce;

    void onTotalCount(Writer writer, int i) {
    }

    public WriterLogger(Writer writer, int i) {
        this.mLock = new Object();
        this.mWriter = writer;
        this.initialLinesCount = i;
        ShutdownHook.INS.addListener(this);
    }

    public WriterLogger(Writer writer) {
        this(writer, 0);
    }

    @Override // com.reandroid.commons.utils.ShutdownHook.ShutdownListener
    public void onShutdown() {
        synchronized (this.mLock) {
            Writer writer = this.mWriter;
            if (writer == null) {
                return;
            }
            try {
                writer.flush();
                writer.close();
            } catch (IOException unused) {
            }
            this.mWriter = null;
        }
    }

    public int getTotalLineCount() {
        return getInitialLinesCount() + getLineCount();
    }

    public int getLineCount() {
        return this.mLineCount;
    }

    public int getInitialLinesCount() {
        return this.initialLinesCount;
    }

    public void resetCount() {
        this.initialLinesCount = 0;
        this.mLineCount = 0;
    }

    public void setIgnoreSameLine(boolean z) {
        this.ignoreSameLine = z;
    }

    public boolean isIgnoreSameLine() {
        return this.ignoreSameLine;
    }

    @Override // com.reandroid.commons.utils.log.Logger
    public void writeLine(String str) {
        write(str);
    }

    @Override // com.reandroid.commons.utils.log.Logger
    public void writeSameLine(String str) {
        if (isIgnoreSameLine()) {
            return;
        }
        write(str);
    }

    public Writer getWriter() {
        Writer writer;
        synchronized (this.mLock) {
            writer = this.mWriter;
        }
        return writer;
    }

    public void setWriter(Writer writer) {
        this.mWriter = writer;
    }

    private void write(String str) {
        synchronized (this.mLock) {
            Writer writer = getWriter();
            if (writer == null) {
                return;
            }
            if (str == null) {
                str = "null";
            }
            try {
                if (!this.mWrittenOnce) {
                    writer.write("\n");
                    writer.write(getStartLine());
                    this.mWrittenOnce = true;
                }
                writer.write("\n");
                writer.write(str);
                this.mLineCount++;
                writer.flush();
                checkLineCount(writer);
            } catch (IOException unused) {
            }
        }
    }

    private void checkLineCount(Writer writer) {
        int totalLineCount = getTotalLineCount();
        if (totalLineCount != 0 && totalLineCount % 100 == 0) {
            onTotalCount(writer, totalLineCount);
        }
    }

    String getStartLine() {
        return "\n  // Log start << " + StringUtil.toReadableDate(System.currentTimeMillis()) + " >>  //\n  ------------------------------------------";
    }
}
