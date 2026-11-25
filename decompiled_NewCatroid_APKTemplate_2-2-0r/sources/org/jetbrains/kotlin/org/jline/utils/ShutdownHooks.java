package org.jetbrains.kotlin.org.jline.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* JADX INFO: loaded from: classes4.dex */
public final class ShutdownHooks {
    private static Thread hook;
    private static final List<Task> tasks = new ArrayList();

    public interface Task {
        void run() throws Exception;
    }

    public static synchronized <T extends Task> T add(T t) {
        Objects.requireNonNull(t);
        if (hook == null) {
            hook = addHook(new Thread("JLine Shutdown Hook") { // from class: org.jetbrains.kotlin.org.jline.utils.ShutdownHooks.1
                @Override // java.lang.Thread, java.lang.Runnable
                public void run() {
                    ShutdownHooks.runTasks();
                }
            });
        }
        Log.debug("Adding shutdown-hook task: ", t);
        tasks.add(t);
        return t;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static synchronized void runTasks() {
        Log.debug("Running all shutdown-hook tasks");
        List<Task> list = tasks;
        for (Task task : (Task[]) list.toArray(new Task[list.size()])) {
            Log.debug("Running task: ", task);
            try {
                task.run();
            } catch (Throwable th) {
                Log.warn("Task failed", th);
            }
        }
        tasks.clear();
    }

    private static Thread addHook(Thread thread) {
        Log.debug("Registering shutdown-hook: ", thread);
        try {
            Runtime.getRuntime().addShutdownHook(thread);
        } catch (AbstractMethodError e) {
            Log.debug("Failed to register shutdown-hook", e);
        }
        return thread;
    }

    public static synchronized void remove(Task task) {
        Objects.requireNonNull(task);
        if (hook == null) {
            return;
        }
        List<Task> list = tasks;
        list.remove(task);
        if (list.isEmpty()) {
            removeHook(hook);
            hook = null;
        }
    }

    private static void removeHook(Thread thread) {
        Log.debug("Removing shutdown-hook: ", thread);
        try {
            Runtime.getRuntime().removeShutdownHook(thread);
        } catch (AbstractMethodError e) {
            Log.debug("Failed to remove shutdown-hook", e);
        } catch (IllegalStateException unused) {
        }
    }
}
