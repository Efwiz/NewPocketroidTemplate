package io.github.rosemoe.sora.lang.analysis;

import android.os.Bundle;
import android.util.Log;
import io.github.rosemoe.sora.lang.styling.Styles;
import io.github.rosemoe.sora.text.CharPosition;
import io.github.rosemoe.sora.text.ContentReference;

/* JADX INFO: loaded from: classes11.dex */
public abstract class SimpleAnalyzeManager<V> implements AnalyzeManager {
    private static final String LOG_TAG = "SimpleAnalyzeManager";
    private static int sThreadId;
    private V data;
    private Bundle extraArguments;
    private final Object lock = new Object();
    private volatile long newestRequestId;
    private StyleReceiver receiver;
    private volatile ContentReference ref;
    private SimpleAnalyzeManager<V>.AnalyzeThread thread;

    protected abstract Styles analyze(StringBuilder sb, SimpleAnalyzeManager<V>.Delegate<V> delegate);

    /* JADX WARN: In static synchronized method top region not synchronized by class const: (wrap java.lang.Class:0x0000: CONST_CLASS  A[WRAPPED] io.github.rosemoe.sora.lang.analysis.SimpleAnalyzeManager.class) */
    private static synchronized int nextThreadId() {
        int i;
        synchronized (SimpleAnalyzeManager.class) {
            i = sThreadId + 1;
            sThreadId = i;
        }
        return i;
    }

    @Override // io.github.rosemoe.sora.lang.analysis.AnalyzeManager
    public void setReceiver(StyleReceiver styleReceiver) {
        this.receiver = styleReceiver;
    }

    @Override // io.github.rosemoe.sora.lang.analysis.AnalyzeManager
    public void reset(ContentReference contentReference, Bundle bundle) {
        this.ref = contentReference;
        this.extraArguments = bundle;
        rerun();
    }

    @Override // io.github.rosemoe.sora.lang.analysis.AnalyzeManager
    public void insert(CharPosition charPosition, CharPosition charPosition2, CharSequence charSequence) {
        rerun();
    }

    @Override // io.github.rosemoe.sora.lang.analysis.AnalyzeManager
    public void delete(CharPosition charPosition, CharPosition charPosition2, CharSequence charSequence) {
        rerun();
    }

    @Override // io.github.rosemoe.sora.lang.analysis.AnalyzeManager
    public synchronized void rerun() {
        this.newestRequestId++;
        SimpleAnalyzeManager<V>.AnalyzeThread analyzeThread = this.thread;
        if (analyzeThread == null || !analyzeThread.isAlive()) {
            Log.v(LOG_TAG, "Starting a new thread for analysis");
            SimpleAnalyzeManager<V>.AnalyzeThread analyzeThread2 = new AnalyzeThread();
            this.thread = analyzeThread2;
            analyzeThread2.setDaemon(true);
            this.thread.setName("SplAnalyzer-" + nextThreadId());
            this.thread.start();
        }
        synchronized (this.lock) {
            this.lock.notify();
        }
    }

    @Override // io.github.rosemoe.sora.lang.analysis.AnalyzeManager
    public void destroy() {
        this.ref = null;
        this.extraArguments = null;
        this.newestRequestId = 0L;
        this.data = null;
        SimpleAnalyzeManager<V>.AnalyzeThread analyzeThread = this.thread;
        if (analyzeThread != null && analyzeThread.isAlive()) {
            this.thread.interrupt();
        }
        this.thread = null;
        this.receiver = null;
    }

    public Bundle getExtraArguments() {
        return this.extraArguments;
    }

    public V getData() {
        return this.data;
    }

    private class AnalyzeThread extends Thread {
        private final StringBuilder textContainer;

        private AnalyzeThread() {
            this.textContainer = new StringBuilder();
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            Log.v(SimpleAnalyzeManager.LOG_TAG, "Analyze thread started");
            while (!isInterrupted()) {
                try {
                    if (SimpleAnalyzeManager.this.ref != null) {
                        Styles styles = null;
                        Object obj = null;
                        while (true) {
                            ContentReference contentReference = SimpleAnalyzeManager.this.ref;
                            if (contentReference == null) {
                                break;
                            }
                            long j = SimpleAnalyzeManager.this.newestRequestId;
                            SimpleAnalyzeManager<V>.Delegate<V> delegate = new Delegate<>(j);
                            this.textContainer.setLength(0);
                            this.textContainer.ensureCapacity(contentReference.length());
                            for (int i = 0; i < contentReference.getLineCount() && j == SimpleAnalyzeManager.this.newestRequestId; i++) {
                                if (i != 0) {
                                    this.textContainer.append(contentReference.getLineSeparator(i - 1));
                                }
                                contentReference.appendLineTo(this.textContainer, i);
                            }
                            Styles stylesAnalyze = SimpleAnalyzeManager.this.analyze(this.textContainer, delegate);
                            Object obj2 = ((Delegate) delegate).data;
                            if (j == SimpleAnalyzeManager.this.newestRequestId) {
                                styles = stylesAnalyze;
                                obj = obj2;
                                break;
                            } else {
                                styles = stylesAnalyze;
                                obj = obj2;
                            }
                        }
                        StyleReceiver styleReceiver = SimpleAnalyzeManager.this.receiver;
                        if (styleReceiver != null && styles != null) {
                            styleReceiver.setStyles(SimpleAnalyzeManager.this, styles);
                        }
                        SimpleAnalyzeManager.this.data = obj;
                    }
                    synchronized (SimpleAnalyzeManager.this.lock) {
                        try {
                            SimpleAnalyzeManager.this.lock.wait();
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                } catch (InterruptedException unused) {
                    Log.v(SimpleAnalyzeManager.LOG_TAG, "Thread is interrupted.");
                    return;
                } catch (Exception e) {
                    Log.e(SimpleAnalyzeManager.LOG_TAG, "Unexpected exception is thrown in the thread.", e);
                    return;
                }
            }
        }
    }

    public final class Delegate<T> {
        private T data;
        private final long myRequestId;

        public Delegate(long j) {
            this.myRequestId = j;
        }

        public void setData(T t) {
            this.data = t;
        }

        public boolean isCancelled() {
            return this.myRequestId != SimpleAnalyzeManager.this.newestRequestId;
        }
    }
}
