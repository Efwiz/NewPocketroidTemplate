package org.jetbrains.kotlin.com.intellij.openapi.util;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryType;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.function.Consumer;
import javax.management.Notification;
import javax.management.NotificationFilter;
import javax.management.NotificationListener;
import org.jetbrains.kotlin.com.intellij.openapi.Disposable;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.util.ConcurrencyUtil;
import org.jetbrains.kotlin.com.intellij.util.SystemProperties;
import org.jetbrains.kotlin.com.intellij.util.concurrency.SequentialTaskExecutor;

/* JADX INFO: loaded from: classes3.dex */
public final class LowMemoryWatcherManager implements Disposable {
    private final ExecutorService myExecutorService;
    private final Consumer<Boolean> myJanitor;
    private final NotificationListener myLowMemoryListener;
    private final Future<?> myMemoryPoolMXBeansFuture;
    private Future<?> mySubmitted;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 2) ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[(i == 1 || i == 2) ? 3 : 2];
        if (i == 1 || i == 2) {
            objArr[0] = "backendExecutorService";
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/util/LowMemoryWatcherManager";
        }
        if (i == 1 || i == 2) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/util/LowMemoryWatcherManager";
        } else if (i != 3) {
            objArr[1] = "getLogger";
        } else {
            objArr[1] = "initializeMXBeanListenersLater";
        }
        if (i == 1) {
            objArr[2] = "<init>";
        } else if (i == 2) {
            objArr[2] = "initializeMXBeanListenersLater";
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 2) {
            throw new IllegalStateException(str2);
        }
        throw new IllegalArgumentException(str2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Logger getLogger() {
        Logger logger = Logger.getInstance((Class<?>) LowMemoryWatcherManager.class);
        if (logger == null) {
            $$$reportNull$$$0(0);
        }
        return logger;
    }

    public LowMemoryWatcherManager(ExecutorService executorService) {
        ExecutorService executorServiceCreateSequentialApplicationPoolExecutor;
        if (executorService == null) {
            $$$reportNull$$$0(1);
        }
        this.myJanitor = new Consumer<Boolean>() { // from class: org.jetbrains.kotlin.com.intellij.openapi.util.LowMemoryWatcherManager.1
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "afterGc", "org/jetbrains/kotlin/com/intellij/openapi/util/LowMemoryWatcherManager$1", "accept"));
            }

            @Override // java.util.function.Consumer
            public void accept(Boolean bool) {
                if (bool == null) {
                    $$$reportNull$$$0(0);
                }
                synchronized (LowMemoryWatcherManager.this.myJanitor) {
                    LowMemoryWatcherManager.this.mySubmitted = null;
                }
                LowMemoryWatcher.onLowMemorySignalReceived(bool.booleanValue());
            }
        };
        this.myLowMemoryListener = new AnonymousClass3();
        if (Boolean.getBoolean("low.memory.watcher.sync")) {
            executorServiceCreateSequentialApplicationPoolExecutor = ConcurrencyUtil.newSameThreadExecutorService();
        } else {
            executorServiceCreateSequentialApplicationPoolExecutor = SequentialTaskExecutor.createSequentialApplicationPoolExecutor("LowMemoryWatcherManager", executorService);
        }
        this.myExecutorService = executorServiceCreateSequentialApplicationPoolExecutor;
        this.myMemoryPoolMXBeansFuture = initializeMXBeanListenersLater(executorService);
    }

    private Future<?> initializeMXBeanListenersLater(ExecutorService executorService) {
        if (executorService == null) {
            $$$reportNull$$$0(2);
        }
        Future<?> futureSubmit = executorService.submit(new Runnable() { // from class: org.jetbrains.kotlin.com.intellij.openapi.util.LowMemoryWatcherManager.2
            @Override // java.lang.Runnable
            public void run() {
                try {
                    for (MemoryPoolMXBean memoryPoolMXBean : ManagementFactory.getMemoryPoolMXBeans()) {
                        if (memoryPoolMXBean.getType() == MemoryType.HEAP && memoryPoolMXBean.isCollectionUsageThresholdSupported() && memoryPoolMXBean.isUsageThresholdSupported()) {
                            long max = memoryPoolMXBean.getUsage().getMax();
                            long jMin = Math.min((long) (max * LowMemoryWatcherManager.getOccupiedMemoryThreshold()), max - 5242880);
                            if (jMin > 0) {
                                memoryPoolMXBean.setUsageThreshold(jMin);
                                memoryPoolMXBean.setCollectionUsageThreshold(jMin);
                            }
                        }
                    }
                    ManagementFactory.getMemoryMXBean().addNotificationListener(LowMemoryWatcherManager.this.myLowMemoryListener, (NotificationFilter) null, (Object) null);
                } catch (Throwable th) {
                    LowMemoryWatcherManager.getLogger().info("Errors initializing LowMemoryWatcher: ", th);
                }
            }

            public String toString() {
                return "initializeMXBeanListeners runnable";
            }
        });
        if (futureSubmit == null) {
            $$$reportNull$$$0(3);
        }
        return futureSubmit;
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.com.intellij.openapi.util.LowMemoryWatcherManager$3, reason: invalid class name */
    class AnonymousClass3 implements NotificationListener {
        AnonymousClass3() {
        }

        public void handleNotification(Notification notification, Object obj) {
            if (LowMemoryWatcher.notificationsSuppressed()) {
                return;
            }
            boolean zEquals = "java.management.memory.threshold.exceeded".equals(notification.getType());
            final boolean zEquals2 = "java.management.memory.collection.threshold.exceeded".equals(notification.getType());
            if (zEquals || zEquals2) {
                synchronized (LowMemoryWatcherManager.this.myJanitor) {
                    if (LowMemoryWatcherManager.this.mySubmitted == null) {
                        LowMemoryWatcherManager lowMemoryWatcherManager = LowMemoryWatcherManager.this;
                        lowMemoryWatcherManager.mySubmitted = lowMemoryWatcherManager.myExecutorService.submit(new Runnable() { // from class: org.jetbrains.kotlin.com.intellij.openapi.util.LowMemoryWatcherManager$3$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.m6502xa35fecad(zEquals2);
                            }
                        });
                        if (LowMemoryWatcherManager.this.mySubmitted.isDone()) {
                            LowMemoryWatcherManager.this.mySubmitted = null;
                        }
                    }
                }
            }
        }

        /* JADX INFO: renamed from: lambda$handleNotification$0$org-jetbrains-kotlin-com-intellij-openapi-util-LowMemoryWatcherManager$3, reason: not valid java name */
        /* synthetic */ void m6502xa35fecad(boolean z) {
            LowMemoryWatcherManager.this.myJanitor.accept(Boolean.valueOf(z));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static float getOccupiedMemoryThreshold() {
        return SystemProperties.getFloatProperty("low.memory.watcher.notification.threshold", 0.95f);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.Disposable
    public void dispose() {
        try {
            this.myMemoryPoolMXBeansFuture.get();
            ManagementFactory.getMemoryMXBean().removeNotificationListener(this.myLowMemoryListener);
        } catch (Exception e) {
            getLogger().error((Throwable) e);
        }
        synchronized (this.myJanitor) {
            Future<?> future = this.mySubmitted;
            if (future != null) {
                future.cancel(false);
                this.mySubmitted = null;
            }
        }
        LowMemoryWatcher.stopAll();
    }
}
