package org.jetbrains.kotlin.com.intellij.util.lang;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.concurrent.ConcurrentMap;

/* JADX INFO: loaded from: classes3.dex */
public final class ClassLoadingLocks<T> {
    private final ConcurrentMap<T, WeakLockReference<T>> myMap;
    private final ReferenceQueue<Object> myQueue;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 2 || i == 3 || i == 4) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 2 || i == 3 || i == 4) ? 2 : 3];
        if (i == 1 || i == 2 || i == 3 || i == 4) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/util/lang/ClassLoadingLocks";
        } else {
            objArr[0] = "className";
        }
        if (i == 1 || i == 2 || i == 3 || i == 4) {
            objArr[1] = "getOrCreateLock";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/lang/ClassLoadingLocks";
        }
        if (i != 1 && i != 2 && i != 3 && i != 4) {
            objArr[2] = "getOrCreateLock";
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 2 && i != 3 && i != 4) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public Object getOrCreateLock(T t) {
        WeakLockReference<T> weakLockReferencePutIfAbsent;
        Object obj;
        if (t == null) {
            $$$reportNull$$$0(0);
        }
        WeakLockReference<T> weakLockReference = this.myMap.get(t);
        if (weakLockReference != null && (obj = weakLockReference.get()) != null) {
            if (obj == null) {
                $$$reportNull$$$0(1);
            }
            return obj;
        }
        Object obj2 = new Object();
        WeakLockReference<T> weakLockReference2 = new WeakLockReference<>(t, obj2, this.myQueue);
        do {
            processQueue();
            weakLockReferencePutIfAbsent = this.myMap.putIfAbsent(t, weakLockReference2);
            if (weakLockReferencePutIfAbsent == null) {
                return obj2;
            }
            Object obj3 = weakLockReferencePutIfAbsent.get();
            if (obj3 != null) {
                if (obj3 == null) {
                    $$$reportNull$$$0(3);
                }
                return obj3;
            }
        } while (!this.myMap.replace(t, weakLockReferencePutIfAbsent, weakLockReference2));
        return obj2;
    }

    private void processQueue() {
        while (true) {
            WeakLockReference weakLockReference = (WeakLockReference) this.myQueue.poll();
            if (weakLockReference == null) {
                return;
            } else {
                this.myMap.remove(weakLockReference.myClassName, weakLockReference);
            }
        }
    }

    private static final class WeakLockReference<T> extends WeakReference<Object> {
        final T myClassName;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i == 1) {
                objArr[0] = "lock";
            } else if (i != 2) {
                objArr[0] = "className";
            } else {
                objArr[0] = "q";
            }
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/lang/ClassLoadingLocks$WeakLockReference";
            objArr[2] = "<init>";
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        private WeakLockReference(T t, Object obj, ReferenceQueue<Object> referenceQueue) {
            super(obj, referenceQueue);
            if (t == null) {
                $$$reportNull$$$0(0);
            }
            if (obj == null) {
                $$$reportNull$$$0(1);
            }
            if (referenceQueue == null) {
                $$$reportNull$$$0(2);
            }
            this.myClassName = t;
        }
    }
}
