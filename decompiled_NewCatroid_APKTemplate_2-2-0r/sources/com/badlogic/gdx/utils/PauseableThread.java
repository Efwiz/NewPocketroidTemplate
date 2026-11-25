package com.badlogic.gdx.utils;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/utils/PauseableThread.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class PauseableThread extends Thread {
    final Runnable runnable;
    boolean paused = false;
    boolean exit = false;

    public PauseableThread(Runnable runnable) {
        this.runnable = runnable;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        while (true) {
            synchronized (this) {
                while (this.paused) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (this.exit) {
                return;
            } else {
                this.runnable.run();
            }
        }
    }

    public void onPause() {
        this.paused = true;
    }

    public void onResume() {
        synchronized (this) {
            this.paused = false;
            notifyAll();
        }
    }

    public boolean isPaused() {
        return this.paused;
    }

    public void stopThread() {
        this.exit = true;
        if (this.paused) {
            onResume();
        }
    }
}
