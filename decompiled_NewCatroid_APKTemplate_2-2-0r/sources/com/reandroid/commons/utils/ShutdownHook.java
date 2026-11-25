package com.reandroid.commons.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes10.dex */
public class ShutdownHook implements Runnable {
    public static final ShutdownHook INS;
    private final List<ShutdownListener> mListeners = new ArrayList();

    public interface ShutdownListener {
        void onShutdown();
    }

    private ShutdownHook() {
    }

    public void addListener(ShutdownListener shutdownListener) {
        synchronized (ShutdownHook.class) {
            try {
                if (shutdownListener == null) {
                    return;
                }
                if (!this.mListeners.contains(shutdownListener)) {
                    this.mListeners.add(shutdownListener);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // java.lang.Runnable
    public void run() {
        synchronized (ShutdownHook.class) {
            Iterator<ShutdownListener> it = this.mListeners.iterator();
            while (it.hasNext()) {
                callListener(it.next());
            }
        }
    }

    private void callListener(ShutdownListener shutdownListener) {
        try {
            shutdownListener.onShutdown();
        } catch (Throwable unused) {
        }
    }

    static {
        ShutdownHook shutdownHook = new ShutdownHook();
        INS = shutdownHook;
        Runtime.getRuntime().addShutdownHook(new Thread(shutdownHook));
    }
}
