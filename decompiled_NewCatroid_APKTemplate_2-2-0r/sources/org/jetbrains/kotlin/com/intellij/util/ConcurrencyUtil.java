package org.jetbrains.kotlin.com.intellij.util;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.function.UnaryOperator;
import net.bytebuddy.implementation.MethodDelegation;
import org.antlr.stringtemplate.language.ASTExpr;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX INFO: loaded from: classes3.dex */
public final class ConcurrencyUtil {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 4 || i == 5 || i == 8 || i == 9 || i == 14 || i == 16 || i == 18 || i == 26 || i == 30) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 4 || i == 5 || i == 8 || i == 9 || i == 14 || i == 16 || i == 18 || i == 26 || i == 30) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = "map";
                break;
            case 2:
                objArr[0] = ASTExpr.DEFAULT_MAP_KEY_NAME;
                break;
            case 3:
            case 7:
                objArr[0] = "defaultValue";
                break;
            case 4:
            case 5:
            case 8:
            case 9:
            case 14:
            case 16:
            case 18:
            case 26:
            case 30:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/util/ConcurrencyUtil";
                break;
            case 6:
                objArr[0] = "ref";
                break;
            case 10:
            case 11:
            case 12:
            case 13:
            case 15:
            case 17:
            case 24:
            case 27:
                objArr[0] = "name";
                break;
            case 19:
                objArr[0] = "executor";
                break;
            case 20:
                objArr[0] = "unit";
                break;
            case 21:
            case 22:
                objArr[0] = ConfigConstants.CONFIG_KEY_THREADS;
                break;
            case 23:
                objArr[0] = "futures";
                break;
            case 25:
            case 28:
            case 32:
            case 34:
                objArr[0] = "runnable";
                break;
            case 29:
                objArr[0] = MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX;
                break;
            case 31:
            case 33:
                objArr[0] = "lock";
                break;
            case 35:
                objArr[0] = "task";
                break;
            default:
                objArr[0] = "tasks";
                break;
        }
        if (i == 4 || i == 5 || i == 8 || i == 9) {
            objArr[1] = "cacheOrGet";
        } else if (i == 14) {
            objArr[1] = "newSingleScheduledThreadExecutor";
        } else if (i == 16 || i == 18) {
            objArr[1] = "newNamedThreadFactory";
        } else if (i == 26) {
            objArr[1] = "underThreadNameRunnable";
        } else if (i != 30) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/ConcurrencyUtil";
        } else {
            objArr[1] = "once";
        }
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 6:
            case 7:
                objArr[2] = "cacheOrGet";
                break;
            case 4:
            case 5:
            case 8:
            case 9:
            case 14:
            case 16:
            case 18:
            case 26:
            case 30:
                break;
            case 10:
            case 11:
                objArr[2] = "newSingleThreadExecutor";
                break;
            case 12:
            case 13:
                objArr[2] = "newSingleScheduledThreadExecutor";
                break;
            case 15:
            case 17:
                objArr[2] = "newNamedThreadFactory";
                break;
            case 19:
            case 20:
                objArr[2] = "awaitQuiescence";
                break;
            case 21:
            case 22:
                objArr[2] = "joinAll";
                break;
            case 23:
                objArr[2] = "getAll";
                break;
            case 24:
            case 25:
                objArr[2] = "underThreadNameRunnable";
                break;
            case 27:
            case 28:
                objArr[2] = "runUnderThreadName";
                break;
            case 29:
                objArr[2] = "once";
                break;
            case 31:
            case 32:
            case 33:
            case 34:
                objArr[2] = "withLock";
                break;
            case 35:
                objArr[2] = "manifestExceptionsIn";
                break;
            default:
                objArr[2] = "invokeAll";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 4 && i != 5 && i != 8 && i != 9 && i != 14 && i != 16 && i != 18 && i != 26 && i != 30) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    static /* synthetic */ Object lambda$cacheOrGet$0(Object obj, Object obj2) {
        return obj2 == null ? obj : obj2;
    }

    public static <K, V> V cacheOrGet(ConcurrentMap<K, V> concurrentMap, K k, V v) {
        if (concurrentMap == null) {
            $$$reportNull$$$0(1);
        }
        if (k == null) {
            $$$reportNull$$$0(2);
        }
        if (v == null) {
            $$$reportNull$$$0(3);
        }
        V v2 = concurrentMap.get(k);
        if (v2 != null) {
            if (v2 == null) {
                $$$reportNull$$$0(4);
            }
            return v2;
        }
        V vPutIfAbsent = concurrentMap.putIfAbsent(k, v);
        if (vPutIfAbsent != null) {
            v = vPutIfAbsent;
        }
        if (v == null) {
            $$$reportNull$$$0(5);
        }
        return v;
    }

    public static <T> T cacheOrGet(AtomicReference<T> atomicReference, final T t) {
        if (atomicReference == null) {
            $$$reportNull$$$0(6);
        }
        if (t == null) {
            $$$reportNull$$$0(7);
        }
        T t2 = atomicReference.get();
        if (t2 != null) {
            if (t2 == null) {
                $$$reportNull$$$0(8);
            }
            return t2;
        }
        T tUpdateAndGet = atomicReference.updateAndGet(new UnaryOperator() { // from class: org.jetbrains.kotlin.com.intellij.util.ConcurrencyUtil$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ConcurrencyUtil.lambda$cacheOrGet$0(t, obj);
            }
        });
        if (tUpdateAndGet == null) {
            $$$reportNull$$$0(9);
        }
        return tUpdateAndGet;
    }

    public static ThreadPoolExecutor newSingleThreadExecutor(String str) {
        if (str == null) {
            $$$reportNull$$$0(10);
        }
        return newSingleThreadExecutor(str, 5);
    }

    public static ThreadPoolExecutor newSingleThreadExecutor(String str, int i) {
        if (str == null) {
            $$$reportNull$$$0(11);
        }
        return new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue(), newNamedThreadFactory(str, true, i));
    }

    public static ExecutorService newSameThreadExecutorService() {
        return new SameThreadExecutorService();
    }

    public static ThreadFactory newNamedThreadFactory(final String str, final boolean z, final int i) {
        if (str == null) {
            $$$reportNull$$$0(15);
        }
        return new ThreadFactory() { // from class: org.jetbrains.kotlin.com.intellij.util.ConcurrencyUtil$$ExternalSyntheticLambda1
            @Override // java.util.concurrent.ThreadFactory
            public final Thread newThread(Runnable runnable) {
                return ConcurrencyUtil.lambda$newNamedThreadFactory$1(str, z, i, runnable);
            }
        };
    }

    static /* synthetic */ Thread lambda$newNamedThreadFactory$1(String str, boolean z, int i, Runnable runnable) {
        Thread thread = new Thread(runnable, str);
        thread.setDaemon(z);
        thread.setPriority(i);
        return thread;
    }

    public static void runUnderThreadName(String str, Runnable runnable) {
        if (str == null) {
            $$$reportNull$$$0(27);
        }
        if (runnable == null) {
            $$$reportNull$$$0(28);
        }
        Thread threadCurrentThread = Thread.currentThread();
        String name2 = threadCurrentThread.getName();
        if (str.equals(name2)) {
            runnable.run();
            return;
        }
        threadCurrentThread.setName(str);
        try {
            runnable.run();
        } finally {
            threadCurrentThread.setName(name2);
        }
    }

    public static <E extends Throwable> void withLock(Lock lock, ThrowableRunnable<E> throwableRunnable) throws Throwable {
        if (lock == null) {
            $$$reportNull$$$0(33);
        }
        if (throwableRunnable == null) {
            $$$reportNull$$$0(34);
        }
        lock.lock();
        try {
            throwableRunnable.run();
        } finally {
            lock.unlock();
        }
    }
}
