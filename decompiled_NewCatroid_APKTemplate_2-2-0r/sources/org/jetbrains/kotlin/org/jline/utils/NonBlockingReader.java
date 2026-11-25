package org.jetbrains.kotlin.org.jline.utils;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.Reader;
import org.apache.commons.lang3.time.DateUtils;

/* JADX INFO: loaded from: classes4.dex */
public class NonBlockingReader extends Reader implements Runnable {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private Reader in;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private String f941name;
    private Thread thread;
    private int ch = -2;
    private boolean threadIsReading = false;
    private IOException exception = null;
    private long threadDelay = DateUtils.MILLIS_PER_MINUTE;

    public NonBlockingReader(String str, Reader reader) {
        this.in = reader;
        this.f941name = str;
    }

    private synchronized void startReadingThreadIfNeeded() {
        if (this.thread == null) {
            Thread thread = new Thread(this);
            this.thread = thread;
            thread.setName(this.f941name + " non blocking reader thread");
            this.thread.setDaemon(true);
            this.thread.start();
        }
    }

    public synchronized void shutdown() {
        if (this.thread != null) {
            notify();
        }
    }

    @Override // java.io.Reader, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.in.close();
        shutdown();
    }

    @Override // java.io.Reader
    public int read() throws IOException {
        return read(0L, false);
    }

    public int peek(long j) throws IOException {
        return read(j, true);
    }

    public int read(long j) throws IOException {
        return read(j, false);
    }

    @Override // java.io.Reader
    public synchronized boolean ready() throws IOException {
        return this.ch >= 0 || this.in.ready();
    }

    private synchronized int read(long j, boolean z) throws IOException {
        int i;
        IOException iOException = this.exception;
        if (iOException != null) {
            if (!z) {
                this.exception = null;
                throw iOException;
            }
            throw iOException;
        }
        if (this.ch < -1) {
            if (!z && j <= 0 && !this.threadIsReading) {
                this.ch = this.in.read();
            } else {
                boolean z2 = true;
                if (!this.threadIsReading) {
                    this.threadIsReading = true;
                    startReadingThreadIfNeeded();
                    notifyAll();
                }
                if (j > 0) {
                    z2 = false;
                }
                while (true) {
                    if (!z2 && j <= 0) {
                        break;
                    }
                    long jCurrentTimeMillis = System.currentTimeMillis();
                    try {
                        wait(j);
                    } catch (InterruptedException e) {
                        this.exception = (IOException) new InterruptedIOException().initCause(e);
                    }
                    IOException iOException2 = this.exception;
                    if (iOException2 != null) {
                        if (!z) {
                            this.exception = null;
                        }
                        throw iOException2;
                    }
                    if (this.ch >= -1) {
                        break;
                    }
                    if (!z2) {
                        j -= System.currentTimeMillis() - jCurrentTimeMillis;
                    }
                }
            }
        }
        i = this.ch;
        if (!z) {
            this.ch = -2;
        }
        return i;
    }

    @Override // java.io.Reader
    public int read(char[] cArr, int i, int i2) throws IOException {
        cArr.getClass();
        if (i < 0 || i2 < 0 || i2 > cArr.length - i) {
            throw new IndexOutOfBoundsException();
        }
        if (i2 == 0) {
            return 0;
        }
        int i3 = read(0L);
        if (i3 == -1) {
            return -1;
        }
        cArr[i] = (char) i3;
        return 1;
    }

    /* JADX WARN: Code duplicated, block: B:54:0x005d A[DONT_GENERATE, EXC_TOP_SPLITTER, SYNTHETIC] */
    @Override // java.lang.Runnable
    public void run() throws IOException {
        int i;
        Log.debug("NonBlockingReader start");
        while (true) {
            try {
                synchronized (this) {
                    if (!this.threadIsReading) {
                        try {
                            wait(this.threadDelay);
                        } catch (InterruptedException unused) {
                        }
                    }
                    if (!this.threadIsReading) {
                        Log.debug("NonBlockingReader shutdown");
                        synchronized (this) {
                            this.thread = null;
                        }
                        return;
                    }
                    try {
                        Log.warn("Error in NonBlockingReader thread", th);
                        synchronized (this) {
                            return;
                        }
                    } finally {
                        Log.debug("NonBlockingReader shutdown");
                        synchronized (this) {
                            this.thread = null;
                        }
                    }
                }
                try {
                    i = this.in.read();
                    e = null;
                } catch (IOException e) {
                    e = e;
                    i = -2;
                }
                synchronized (this) {
                    this.exception = e;
                    this.ch = i;
                    this.threadIsReading = false;
                    notify();
                }
            } catch (Throwable th) {
                Log.warn("Error in NonBlockingReader thread", th);
                synchronized (this) {
                    return;
                }
            }
        }
    }
}
