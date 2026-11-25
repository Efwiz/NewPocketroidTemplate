package org.jetbrains.kotlin.com.intellij.util.ui;

import java.awt.EventQueue;

/* JADX INFO: loaded from: classes3.dex */
public final class EDT {
    private static Thread myEventDispatchThread;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 1 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 1 ? 3 : 2];
        if (i != 1) {
            objArr[0] = "thread";
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/util/ui/EDT";
        }
        if (i != 1) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/ui/EDT";
        } else {
            objArr[1] = "getEventDispatchThread";
        }
        if (i != 1) {
            objArr[2] = "isEdt";
        }
        String str2 = String.format(str, objArr);
        if (i == 1) {
            throw new IllegalStateException(str2);
        }
    }

    public static boolean isEdt(Thread thread) {
        if (thread == null) {
            $$$reportNull$$$0(0);
        }
        return thread == myEventDispatchThread;
    }

    public static boolean isCurrentThreadEdt() {
        if (myEventDispatchThread == null) {
            return EventQueue.isDispatchThread();
        }
        return isEdt(Thread.currentThread());
    }
}
