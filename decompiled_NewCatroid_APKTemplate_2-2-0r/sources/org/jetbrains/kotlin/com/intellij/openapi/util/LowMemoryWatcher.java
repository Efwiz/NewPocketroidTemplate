package org.jetbrains.kotlin.com.intellij.openapi.util;

import java.util.concurrent.atomic.AtomicBoolean;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.openapi.Disposable;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.util.containers.WeakList;

/* JADX INFO: loaded from: classes3.dex */
public final class LowMemoryWatcher {
    private static final Logger LOG = Logger.getInstance((Class<?>) LowMemoryWatcher.class);
    private static final WeakList<LowMemoryWatcher> ourListeners = new WeakList<>();
    private static final AtomicBoolean ourNotificationsSuppressed = new AtomicBoolean();
    private final Runnable myRunnable;
    private final LowMemoryWatcherType myType;

    public enum LowMemoryWatcherType {
        ALWAYS,
        ONLY_AFTER_GC
    }

    /* JADX WARN: Code duplicated, block: B:13:0x001e  */
    /* JADX WARN: Code duplicated, block: B:14:0x0023  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = "notificationType";
        } else if (i == 7) {
            objArr[0] = "parentDisposable";
        } else if (i == 9) {
            objArr[0] = ModuleXmlParser.TYPE;
        } else if (i == 4) {
            objArr[0] = "notificationType";
        } else if (i != 5) {
            objArr[0] = "runnable";
        } else {
            objArr[0] = "parentDisposable";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/util/LowMemoryWatcher";
        if (i == 8 || i == 9) {
            objArr[2] = "<init>";
        } else {
            objArr[2] = "register";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public static void onLowMemorySignalReceived(boolean z) {
        LOG.info("Low memory signal received: afterGc=" + z);
        for (LowMemoryWatcher lowMemoryWatcher : ourListeners.toStrongList()) {
            try {
                if (lowMemoryWatcher.myType == LowMemoryWatcherType.ALWAYS || (lowMemoryWatcher.myType == LowMemoryWatcherType.ONLY_AFTER_GC && z)) {
                    lowMemoryWatcher.myRunnable.run();
                }
            } catch (Throwable th) {
                LOG.info(th);
            }
        }
    }

    static boolean notificationsSuppressed() {
        return ourNotificationsSuppressed.get();
    }

    public static LowMemoryWatcher register(Runnable runnable) {
        if (runnable == null) {
            $$$reportNull$$$0(2);
        }
        return new LowMemoryWatcher(runnable, LowMemoryWatcherType.ALWAYS);
    }

    public static void register(Runnable runnable, LowMemoryWatcherType lowMemoryWatcherType, Disposable disposable) {
        if (runnable == null) {
            $$$reportNull$$$0(3);
        }
        if (lowMemoryWatcherType == null) {
            $$$reportNull$$$0(4);
        }
        if (disposable == null) {
            $$$reportNull$$$0(5);
        }
        final LowMemoryWatcher lowMemoryWatcher = new LowMemoryWatcher(runnable, lowMemoryWatcherType);
        Disposer.register(disposable, new Disposable() { // from class: org.jetbrains.kotlin.com.intellij.openapi.util.LowMemoryWatcher$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.openapi.Disposable
            public final void dispose() {
                this.f$0.stop();
            }
        });
    }

    public static void register(Runnable runnable, Disposable disposable) {
        if (runnable == null) {
            $$$reportNull$$$0(6);
        }
        if (disposable == null) {
            $$$reportNull$$$0(7);
        }
        register(runnable, LowMemoryWatcherType.ALWAYS, disposable);
    }

    private LowMemoryWatcher(Runnable runnable, LowMemoryWatcherType lowMemoryWatcherType) {
        if (runnable == null) {
            $$$reportNull$$$0(8);
        }
        if (lowMemoryWatcherType == null) {
            $$$reportNull$$$0(9);
        }
        this.myRunnable = runnable;
        this.myType = lowMemoryWatcherType;
        ourListeners.add(this);
    }

    public void stop() {
        ourListeners.remove(this);
    }

    static void stopAll() {
        ourListeners.clear();
    }
}
