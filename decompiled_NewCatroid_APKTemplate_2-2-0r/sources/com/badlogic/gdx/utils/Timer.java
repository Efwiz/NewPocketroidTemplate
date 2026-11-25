package com.badlogic.gdx.utils;

import androidx.lifecycle.CoroutineLiveDataKt;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.LifecycleListener;
import org.apache.commons.compress.harmony.pack200.PackingOptions;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/utils/Timer.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class Timer {
    static TimerThread thread;
    static final Object threadLock = new Object();
    long stopTimeMillis;
    final Array<Task> tasks = new Array<>(false, 8);

    public static Timer instance() {
        Timer timer;
        synchronized (threadLock) {
            TimerThread timerThreadThread = thread();
            if (timerThreadThread.instance == null) {
                timerThreadThread.instance = new Timer();
            }
            timer = timerThreadThread.instance;
        }
        return timer;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static TimerThread thread() {
        TimerThread timerThread;
        synchronized (threadLock) {
            TimerThread timerThread2 = thread;
            if (timerThread2 == null || timerThread2.files != Gdx.files) {
                TimerThread timerThread3 = thread;
                if (timerThread3 != null) {
                    timerThread3.dispose();
                }
                thread = new TimerThread();
            }
            timerThread = thread;
        }
        return timerThread;
    }

    public Timer() {
        start();
    }

    public Task postTask(Task task) {
        return scheduleTask(task, 0.0f, 0.0f, 0);
    }

    public Task scheduleTask(Task task, float f) {
        return scheduleTask(task, f, 0.0f, 0);
    }

    public Task scheduleTask(Task task, float f, float f2) {
        return scheduleTask(task, f, f2, -1);
    }

    public Task scheduleTask(Task task, float f, float f2, int i) {
        Object obj = threadLock;
        synchronized (obj) {
            synchronized (this) {
                synchronized (task) {
                    if (task.timer != null) {
                        throw new IllegalArgumentException("The same task may not be scheduled twice.");
                    }
                    task.timer = this;
                    long jNanoTime = System.nanoTime() / PackingOptions.SEGMENT_LIMIT;
                    long j = ((long) (f * 1000.0f)) + jNanoTime;
                    if (thread.pauseTimeMillis > 0) {
                        j -= jNanoTime - thread.pauseTimeMillis;
                    }
                    task.executeTimeMillis = j;
                    task.intervalMillis = (long) (f2 * 1000.0f);
                    task.repeatCount = i;
                    this.tasks.add(task);
                }
            }
            obj.notifyAll();
        }
        return task;
    }

    public void stop() {
        synchronized (threadLock) {
            if (thread().instances.removeValue(this, true)) {
                this.stopTimeMillis = System.nanoTime() / PackingOptions.SEGMENT_LIMIT;
            }
        }
    }

    public void start() {
        Object obj = threadLock;
        synchronized (obj) {
            Array<Timer> array = thread().instances;
            if (array.contains(this, true)) {
                return;
            }
            array.add(this);
            if (this.stopTimeMillis > 0) {
                delay((System.nanoTime() / PackingOptions.SEGMENT_LIMIT) - this.stopTimeMillis);
                this.stopTimeMillis = 0L;
            }
            obj.notifyAll();
        }
    }

    public void clear() {
        synchronized (threadLock) {
            TimerThread timerThreadThread = thread();
            synchronized (this) {
                synchronized (timerThreadThread.postedTasks) {
                    int i = this.tasks.size;
                    for (int i2 = 0; i2 < i; i2++) {
                        Task task = this.tasks.get(i2);
                        timerThreadThread.removePostedTask(task);
                        task.reset();
                    }
                }
                this.tasks.clear();
            }
        }
    }

    public synchronized boolean isEmpty() {
        return this.tasks.size == 0;
    }

    synchronized long update(TimerThread timerThread, long j, long j2) {
        int i = this.tasks.size;
        int i2 = 0;
        while (i2 < i) {
            Task task = this.tasks.get(i2);
            synchronized (task) {
                if (task.executeTimeMillis > j) {
                    j2 = Math.min(j2, task.executeTimeMillis - j);
                } else {
                    if (task.repeatCount == 0) {
                        task.timer = null;
                        this.tasks.removeIndex(i2);
                        i2--;
                        i--;
                    } else {
                        task.executeTimeMillis = task.intervalMillis + j;
                        j2 = Math.min(j2, task.intervalMillis);
                        if (task.repeatCount > 0) {
                            task.repeatCount--;
                        }
                    }
                    timerThread.addPostedTask(task);
                }
            }
            i2++;
        }
        return j2;
    }

    public synchronized void delay(long j) {
        int i = this.tasks.size;
        for (int i2 = 0; i2 < i; i2++) {
            Task task = this.tasks.get(i2);
            synchronized (task) {
                task.executeTimeMillis += j;
            }
        }
    }

    public static Task post(Task task) {
        return instance().postTask(task);
    }

    public static Task schedule(Task task, float f) {
        return instance().scheduleTask(task, f);
    }

    public static Task schedule(Task task, float f, float f2) {
        return instance().scheduleTask(task, f, f2);
    }

    public static Task schedule(Task task, float f, float f2, int i) {
        return instance().scheduleTask(task, f, f2, i);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/utils/Timer$Task.class
      classes8.dex
     */
    public static abstract class Task implements Runnable {

        /* JADX INFO: renamed from: app, reason: collision with root package name */
        final Application f127app;
        long executeTimeMillis;
        long intervalMillis;
        int repeatCount;
        volatile Timer timer;

        @Override // java.lang.Runnable
        public abstract void run();

        public Task() {
            Application application = Gdx.f82app;
            this.f127app = application;
            if (application == null) {
                throw new IllegalStateException("Gdx.app not available.");
            }
        }

        public void cancel() {
            synchronized (Timer.threadLock) {
                Timer.thread().removePostedTask(this);
                Timer timer = this.timer;
                if (timer != null) {
                    synchronized (timer) {
                        timer.tasks.removeValue(this, true);
                        reset();
                    }
                } else {
                    reset();
                }
            }
        }

        synchronized void reset() {
            this.executeTimeMillis = 0L;
            this.timer = null;
        }

        public boolean isScheduled() {
            return this.timer != null;
        }

        public synchronized long getExecuteTimeMillis() {
            return this.executeTimeMillis;
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/utils/Timer$TimerThread.class
      classes8.dex
     */
    static class TimerThread implements Runnable, LifecycleListener {

        /* JADX INFO: renamed from: app, reason: collision with root package name */
        final Application f128app;
        Timer instance;
        long pauseTimeMillis;
        final Array<Timer> instances = new Array<>(1);
        final Array<Task> postedTasks = new Array<>(2);
        final Array<Task> runTasks = new Array<>(2);
        private final Runnable runPostedTasks = new Runnable() { // from class: com.badlogic.gdx.utils.Timer.TimerThread.1
            @Override // java.lang.Runnable
            public void run() {
                TimerThread.this.runPostedTasks();
            }
        };
        final Files files = Gdx.files;

        public TimerThread() {
            Application application = Gdx.f82app;
            this.f128app = application;
            application.addLifecycleListener(this);
            resume();
            Thread thread = new Thread(this, "Timer");
            thread.setDaemon(true);
            thread.start();
        }

        @Override // java.lang.Runnable
        public void run() {
            while (true) {
                synchronized (Timer.threadLock) {
                    if (Timer.thread != this || this.files != Gdx.files) {
                        break;
                    }
                    long j = this.pauseTimeMillis;
                    long j2 = CoroutineLiveDataKt.DEFAULT_TIMEOUT;
                    if (j == 0) {
                        long jNanoTime = System.nanoTime() / PackingOptions.SEGMENT_LIMIT;
                        int i = this.instances.size;
                        long jUpdate = 5000;
                        for (int i2 = 0; i2 < i; i2++) {
                            try {
                                jUpdate = this.instances.get(i2).update(this, jNanoTime, jUpdate);
                            } catch (Throwable th) {
                                throw new GdxRuntimeException("Task failed: " + this.instances.get(i2).getClass().getName(), th);
                            }
                        }
                        j2 = jUpdate;
                    }
                    if (Timer.thread == this && this.files == Gdx.files) {
                        if (j2 > 0) {
                            try {
                                Timer.threadLock.wait(j2);
                            } catch (InterruptedException unused) {
                            }
                        }
                    }
                    dispose();
                }
            }
            dispose();
        }

        void runPostedTasks() {
            synchronized (this.postedTasks) {
                this.runTasks.addAll(this.postedTasks);
                this.postedTasks.clear();
            }
            Task[] taskArr = this.runTasks.items;
            int i = this.runTasks.size;
            for (int i2 = 0; i2 < i; i2++) {
                taskArr[i2].run();
            }
            this.runTasks.clear();
        }

        void addPostedTask(Task task) {
            synchronized (this.postedTasks) {
                if (this.postedTasks.isEmpty()) {
                    task.f127app.postRunnable(this.runPostedTasks);
                }
                this.postedTasks.add(task);
            }
        }

        void removePostedTask(Task task) {
            synchronized (this.postedTasks) {
                Task[] taskArr = this.postedTasks.items;
                for (int i = this.postedTasks.size - 1; i >= 0; i--) {
                    if (taskArr[i] == task) {
                        this.postedTasks.removeIndex(i);
                    }
                }
            }
        }

        @Override // com.badlogic.gdx.LifecycleListener
        public void resume() {
            synchronized (Timer.threadLock) {
                long jNanoTime = (System.nanoTime() / PackingOptions.SEGMENT_LIMIT) - this.pauseTimeMillis;
                int i = this.instances.size;
                for (int i2 = 0; i2 < i; i2++) {
                    this.instances.get(i2).delay(jNanoTime);
                }
                this.pauseTimeMillis = 0L;
                Timer.threadLock.notifyAll();
            }
        }

        @Override // com.badlogic.gdx.LifecycleListener
        public void pause() {
            synchronized (Timer.threadLock) {
                this.pauseTimeMillis = System.nanoTime() / PackingOptions.SEGMENT_LIMIT;
                Timer.threadLock.notifyAll();
            }
        }

        @Override // com.badlogic.gdx.LifecycleListener
        public void dispose() {
            synchronized (Timer.threadLock) {
                synchronized (this.postedTasks) {
                    this.postedTasks.clear();
                }
                if (Timer.thread == this) {
                    Timer.thread = null;
                }
                this.instances.clear();
                Timer.threadLock.notifyAll();
            }
            this.f128app.removeLifecycleListener(this);
        }
    }
}
