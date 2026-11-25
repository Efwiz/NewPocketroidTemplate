package io.github.rosemoe.sora.lang.format;

import android.util.Log;
import io.github.rosemoe.sora.text.Content;
import io.github.rosemoe.sora.text.TextRange;
import java.lang.ref.WeakReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/* JADX INFO: loaded from: classes11.dex */
public abstract class AsyncFormatter implements Formatter {
    private static final String LOG_TAG = "AsyncFormatter";
    private static int sThreadId;
    private final Condition condition;
    private volatile TextRange cursorRange;
    private final ReentrantLock lock;
    private volatile TextRange range;
    private WeakReference<Formatter.FormatResultReceiver> receiver;
    private volatile Content text;
    private FormattingThread thread;

    public abstract TextRange formatAsync(Content content, TextRange textRange);

    public abstract TextRange formatRegionAsync(Content content, TextRange textRange, TextRange textRange2);

    public AsyncFormatter() {
        ReentrantLock reentrantLock = new ReentrantLock();
        this.lock = reentrantLock;
        this.condition = reentrantLock.newCondition();
    }

    private static synchronized int nextThreadId() {
        int i;
        i = sThreadId + 1;
        sThreadId = i;
        return i;
    }

    @Override // io.github.rosemoe.sora.lang.format.Formatter
    public void setReceiver(Formatter.FormatResultReceiver formatResultReceiver) {
        if (formatResultReceiver == null) {
            this.receiver = null;
        } else {
            this.receiver = new WeakReference<>(formatResultReceiver);
        }
    }

    private void run() {
        FormattingThread formattingThread = this.thread;
        if (formattingThread == null || !formattingThread.isAlive()) {
            Log.v(LOG_TAG, "Starting a new thread for formatting");
            FormattingThread formattingThread2 = new FormattingThread();
            this.thread = formattingThread2;
            formattingThread2.setDaemon(true);
            this.thread.setName("AsyncFormatter-" + nextThreadId());
            this.thread.start();
            return;
        }
        Log.v(LOG_TAG, "Waking up thread for formatting");
        this.lock.lock();
        this.condition.signal();
        this.lock.unlock();
    }

    @Override // io.github.rosemoe.sora.lang.format.Formatter
    public void format(Content content, TextRange textRange) {
        this.text = content;
        this.range = null;
        this.cursorRange = textRange;
        run();
    }

    @Override // io.github.rosemoe.sora.lang.format.Formatter
    public boolean isRunning() {
        FormattingThread formattingThread = this.thread;
        return formattingThread != null && formattingThread.isAlive() && this.lock.isLocked();
    }

    @Override // io.github.rosemoe.sora.lang.format.Formatter
    public void formatRegion(Content content, TextRange textRange, TextRange textRange2) {
        this.text = content;
        this.range = textRange;
        this.cursorRange = textRange2;
        run();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendUpdate(Content content, TextRange textRange) {
        WeakReference<Formatter.FormatResultReceiver> weakReference;
        Formatter.FormatResultReceiver formatResultReceiver;
        if (Thread.currentThread().isInterrupted() || (weakReference = this.receiver) == null || (formatResultReceiver = weakReference.get()) == null) {
            return;
        }
        formatResultReceiver.onFormatSucceed(content, textRange);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendFailure(Throwable th) {
        WeakReference<Formatter.FormatResultReceiver> weakReference;
        Formatter.FormatResultReceiver formatResultReceiver;
        if (Thread.currentThread().isInterrupted() || (weakReference = this.receiver) == null || (formatResultReceiver = weakReference.get()) == null) {
            return;
        }
        formatResultReceiver.onFormatFail(th);
    }

    @Override // io.github.rosemoe.sora.lang.format.Formatter
    public void cancel() {
        FormattingThread formattingThread = this.thread;
        if (formattingThread != null) {
            if (formattingThread.isAlive()) {
                formattingThread.interrupt();
            }
            this.thread = null;
        }
    }

    @Override // io.github.rosemoe.sora.lang.format.Formatter
    public void destroy() {
        FormattingThread formattingThread = this.thread;
        if (formattingThread != null && formattingThread.isAlive()) {
            this.thread.interrupt();
        }
        this.thread = null;
        this.receiver = null;
        this.text = null;
        this.range = null;
    }

    private class FormattingThread extends Thread {
        private FormattingThread() {
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            TextRange regionAsync;
            Log.v(AsyncFormatter.LOG_TAG, "AsyncFormatter thread started");
            while (!isInterrupted()) {
                try {
                    AsyncFormatter.this.lock.lock();
                    if (AsyncFormatter.this.text != null) {
                        if (AsyncFormatter.this.range == null) {
                            AsyncFormatter asyncFormatter = AsyncFormatter.this;
                            regionAsync = asyncFormatter.formatAsync(asyncFormatter.text, AsyncFormatter.this.cursorRange);
                        } else {
                            AsyncFormatter asyncFormatter2 = AsyncFormatter.this;
                            regionAsync = asyncFormatter2.formatRegionAsync(asyncFormatter2.text, AsyncFormatter.this.range, AsyncFormatter.this.cursorRange);
                        }
                        AsyncFormatter asyncFormatter3 = AsyncFormatter.this;
                        asyncFormatter3.sendUpdate(asyncFormatter3.text, regionAsync);
                        AsyncFormatter.this.text = null;
                        AsyncFormatter.this.range = null;
                        AsyncFormatter.this.condition.await();
                    }
                } catch (InterruptedException unused) {
                    Log.v(AsyncFormatter.LOG_TAG, "Thread is interrupted.");
                    return;
                } catch (Exception e) {
                    Log.e(AsyncFormatter.LOG_TAG, "Unexpected exception is thrown in the thread.", e);
                    AsyncFormatter.this.sendFailure(e);
                    return;
                }
            }
        }
    }
}
