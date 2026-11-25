package org.jetbrains.kotlin.com.intellij.diagnostic;

import org.jetbrains.kotlin.com.intellij.openapi.application.Application;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;

/* JADX INFO: loaded from: classes3.dex */
public interface EventWatcher {
    public static final InstanceHolder ourInstance = new InstanceHolder();

    void runnableFinished(Runnable runnable, long j);

    void runnableStarted(Runnable runnable, long j);

    public static final class InstanceHolder {
        private EventWatcher myInstance;
        private final boolean myIsEnabled;

        private InstanceHolder() {
            this.myInstance = null;
            this.myIsEnabled = Boolean.getBoolean("idea.event.queue.dispatch.listen");
        }
    }

    static boolean isEnabled() {
        return ourInstance.myIsEnabled;
    }

    static EventWatcher getInstanceOrNull() {
        Application application;
        if (!isEnabled()) {
            return null;
        }
        InstanceHolder instanceHolder = ourInstance;
        EventWatcher eventWatcher = instanceHolder.myInstance;
        if (eventWatcher != null || (application = ApplicationManager.getApplication()) == null || application.isDisposed() || !LoadingState.CONFIGURATION_STORE_INITIALIZED.isOccurred()) {
            return eventWatcher;
        }
        EventWatcher eventWatcher2 = (EventWatcher) application.getService(EventWatcher.class);
        instanceHolder.myInstance = eventWatcher2;
        return eventWatcher2;
    }
}
