package org.jetbrains.kotlin.com.intellij.util.ui;

import androidx.lifecycle.LifecycleKt$$ExternalSyntheticBackportWithForwarding0;
import java.awt.AWTEvent;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.InvocationEvent;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicReference;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.util.ExceptionUtilRt;
import org.jetbrains.kotlin.com.intellij.util.ReflectionUtil;

/* JADX INFO: loaded from: classes3.dex */
public abstract class EdtInvocationManager {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static MethodHandle dispatchEventMethod;
    private static final AtomicReference<EdtInvocationManager> ourInstance = new AtomicReference<>();

    /* JADX WARN: Code duplicated, block: B:24:0x0037  */
    /* JADX WARN: Code duplicated, block: B:32:0x004f  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 3 || i == 4 || i == 5) ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[(i == 1 || i == 3 || i == 4 || i == 5) ? 3 : 2];
        if (i == 1) {
            objArr[0] = "runnable";
        } else if (i == 3) {
            objArr[0] = "edtInvocationManager";
        } else if (i == 4) {
            objArr[0] = "current";
        } else if (i != 5) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/util/ui/EdtInvocationManager";
        } else {
            objArr[0] = "runnable";
        }
        if (i == 1) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/ui/EdtInvocationManager";
        } else if (i == 2) {
            objArr[1] = "getInstance";
        } else if (i == 3 || i == 4 || i == 5) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/ui/EdtInvocationManager";
        } else {
            objArr[1] = "getEventQueueThread";
        }
        if (i == 1) {
            objArr[2] = "invokeLaterIfNeeded";
        } else if (i == 3) {
            objArr[2] = "setEdtInvocationManager";
        } else if (i == 4) {
            objArr[2] = "restoreEdtInvocationManager";
        } else if (i == 5) {
            objArr[2] = "invokeAndWaitIfNeeded";
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 3 && i != 4 && i != 5) {
            throw new IllegalStateException(str2);
        }
        throw new IllegalArgumentException(str2);
    }

    public abstract void invokeAndWait(Runnable runnable) throws InterruptedException, InvocationTargetException;

    public abstract void invokeLater(Runnable runnable);

    public abstract boolean isEventDispatchThread();

    public static void dispatchAllInvocationEvents() {
        EventQueue systemEventQueue = Toolkit.getDefaultToolkit().getSystemEventQueue();
        MethodHandle methodHandleUnreflect = dispatchEventMethod;
        int i = 1;
        if (methodHandleUnreflect == null) {
            try {
                Method declaredMethod = EventQueue.class.getDeclaredMethod("dispatchEvent", AWTEvent.class);
                declaredMethod.setAccessible(true);
                methodHandleUnreflect = MethodHandles.lookup().unreflect(declaredMethod);
                dispatchEventMethod = methodHandleUnreflect;
            } catch (IllegalAccessException | NoSuchMethodException unused) {
                throw new RuntimeException();
            }
        }
        while (systemEventQueue.peekEvent() != null) {
            try {
                AWTEvent nextEvent = systemEventQueue.getNextEvent();
                if (nextEvent instanceof InvocationEvent) {
                    (void) methodHandleUnreflect.bindTo(systemEventQueue).invoke(nextEvent);
                }
                if (i % 10000 == 0) {
                    System.out.println("Suspiciously many (" + i + ") AWT events, last dispatched " + nextEvent);
                }
                i++;
            } catch (Throwable th) {
                ExceptionUtilRt.rethrowUnchecked(th);
                throw new RuntimeException(th);
            }
        }
    }

    private static Thread getEventQueueThread() {
        try {
            Thread thread = (Thread) ReflectionUtil.getDeclaredMethod(EventQueue.class, "getDispatchThread", new Class[0]).invoke(Toolkit.getDefaultToolkit().getSystemEventQueue(), new Object[0]);
            if (thread == null) {
                $$$reportNull$$$0(0);
            }
            return thread;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void invokeLaterIfNeeded(Runnable runnable) {
        if (runnable == null) {
            $$$reportNull$$$0(1);
        }
        EdtInvocationManager edtInvocationManager = getInstance();
        if (edtInvocationManager.isEventDispatchThread()) {
            runnable.run();
        } else {
            edtInvocationManager.invokeLater(runnable);
        }
    }

    public static EdtInvocationManager getInstance() {
        AtomicReference<EdtInvocationManager> atomicReference = ourInstance;
        EdtInvocationManager swingEdtInvocationManager = atomicReference.get();
        if (swingEdtInvocationManager == null) {
            swingEdtInvocationManager = new SwingEdtInvocationManager();
            if (!LifecycleKt$$ExternalSyntheticBackportWithForwarding0.m(atomicReference, null, swingEdtInvocationManager)) {
                swingEdtInvocationManager = atomicReference.get();
            }
        }
        if (swingEdtInvocationManager == null) {
            $$$reportNull$$$0(2);
        }
        return swingEdtInvocationManager;
    }

    public static void invokeAndWaitIfNeeded(Runnable runnable) {
        if (runnable == null) {
            $$$reportNull$$$0(5);
        }
        EdtInvocationManager edtInvocationManager = getInstance();
        if (edtInvocationManager.isEventDispatchThread()) {
            runnable.run();
            return;
        }
        try {
            edtInvocationManager.invokeAndWait(runnable);
        } catch (Exception e) {
            Logger.getInstance((Class<?>) EdtInvocationManager.class).error((Throwable) e);
        }
    }

    public static class SwingEdtInvocationManager extends EdtInvocationManager {
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            objArr[0] = "task";
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/ui/EdtInvocationManager$SwingEdtInvocationManager";
            if (i != 1) {
                objArr[2] = "invokeLater";
            } else {
                objArr[2] = "invokeAndWait";
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.ui.EdtInvocationManager
        public boolean isEventDispatchThread() {
            return EventQueue.isDispatchThread();
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.ui.EdtInvocationManager
        public void invokeLater(Runnable runnable) {
            if (runnable == null) {
                $$$reportNull$$$0(0);
            }
            EventQueue.invokeLater(runnable);
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.ui.EdtInvocationManager
        public void invokeAndWait(Runnable runnable) throws InterruptedException, InvocationTargetException {
            if (runnable == null) {
                $$$reportNull$$$0(1);
            }
            EventQueue.invokeAndWait(runnable);
        }
    }
}
