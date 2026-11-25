package org.jetbrains.kotlin.com.intellij.util.containers;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.lang.ref.ReferenceQueue;
import org.jetbrains.kotlin.com.intellij.reference.SoftReference;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;

/* JADX INFO: loaded from: classes3.dex */
final class SoftHashMap<K, V> extends RefHashMap<K, V> {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = JvmAnnotationNames.KIND_FIELD_NAME;
        } else if (i == 2) {
            objArr[0] = "strategy";
        } else if (i != 3) {
            objArr[0] = "hashingStrategy";
        } else {
            objArr[0] = "q";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/util/containers/SoftHashMap";
        if (i == 1 || i == 2 || i == 3) {
            objArr[2] = "createKey";
        } else {
            objArr[2] = "<init>";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    SoftHashMap(int i) {
        super(i);
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.containers.RefHashMap
    protected <T> RefHashMap.Key<T> createKey(T t, HashingStrategy<? super T> hashingStrategy, ReferenceQueue<? super T> referenceQueue) {
        if (t == null) {
            $$$reportNull$$$0(1);
        }
        if (hashingStrategy == null) {
            $$$reportNull$$$0(2);
        }
        if (referenceQueue == null) {
            $$$reportNull$$$0(3);
        }
        return new SoftKey(t, hashingStrategy, referenceQueue);
    }

    private static final class SoftKey<T> extends SoftReference<T> implements RefHashMap.Key<T> {
        private final int myHash;
        private final HashingStrategy<? super T> myStrategy;

        private SoftKey(T t, HashingStrategy<? super T> hashingStrategy, ReferenceQueue<? super T> referenceQueue) {
            super(t, referenceQueue);
            this.myStrategy = hashingStrategy;
            this.myHash = hashingStrategy.hashCode(t);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof RefHashMap.Key) || this.myHash != obj.hashCode()) {
                return false;
            }
            T t = get();
            Object obj2 = ((RefHashMap.Key) obj).get();
            if (t == null || obj2 == null) {
                return false;
            }
            return RefHashMap.keysEqual(t, obj2, this.myStrategy);
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.containers.RefHashMap.Key
        public int hashCode() {
            return this.myHash;
        }

        public String toString() {
            return "SoftHashMap.SoftKey(" + get() + Constant.AFTER_QUTO;
        }
    }
}
