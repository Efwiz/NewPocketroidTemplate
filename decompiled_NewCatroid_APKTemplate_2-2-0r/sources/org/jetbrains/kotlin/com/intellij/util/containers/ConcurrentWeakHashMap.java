package org.jetbrains.kotlin.com.intellij.util.containers;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;

/* JADX INFO: loaded from: classes3.dex */
final class ConcurrentWeakHashMap<K, V> extends ConcurrentRefHashMap<K, V> {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 2) {
            objArr[0] = "hashingStrategy";
        } else {
            objArr[0] = ASTExpr.DEFAULT_MAP_KEY_NAME;
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/util/containers/ConcurrentWeakHashMap";
        if (i == 2 || i == 3) {
            objArr[2] = "createKeyReference";
        } else {
            objArr[2] = "<init>";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    ConcurrentWeakHashMap(float f) {
        super(16, f, DEFAULT_CONCURRENCY_LEVEL, HashingStrategy.canonical());
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    ConcurrentWeakHashMap(HashingStrategy<? super K> hashingStrategy) {
        super(hashingStrategy);
        if (hashingStrategy == null) {
            $$$reportNull$$$0(1);
        }
    }

    private static final class WeakKey<K> extends WeakReference<K> implements ConcurrentRefHashMap.KeyReference<K> {
        private final int myHash;
        private final HashingStrategy<? super K> myStrategy;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i == 1) {
                objArr[0] = "strategy";
            } else if (i != 2) {
                objArr[0] = JvmAnnotationNames.KIND_FIELD_NAME;
            } else {
                objArr[0] = "q";
            }
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/containers/ConcurrentWeakHashMap$WeakKey";
            objArr[2] = "<init>";
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        private WeakKey(K k, int i, HashingStrategy<? super K> hashingStrategy, ReferenceQueue<K> referenceQueue) {
            super(k, referenceQueue);
            if (k == null) {
                $$$reportNull$$$0(0);
            }
            if (hashingStrategy == null) {
                $$$reportNull$$$0(1);
            }
            if (referenceQueue == null) {
                $$$reportNull$$$0(2);
            }
            this.myStrategy = hashingStrategy;
            this.myHash = i;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ConcurrentRefHashMap.KeyReference)) {
                return false;
            }
            K k = get();
            Object obj2 = ((ConcurrentRefHashMap.KeyReference) obj).get();
            if (k == null || obj2 == null) {
                return false;
            }
            return k == obj2 || this.myStrategy.equals(k, obj2);
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.containers.ConcurrentRefHashMap.KeyReference
        public int hashCode() {
            return this.myHash;
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.containers.ConcurrentRefHashMap
    protected ConcurrentRefHashMap.KeyReference<K> createKeyReference(K k, HashingStrategy<? super K> hashingStrategy) {
        if (k == null) {
            $$$reportNull$$$0(2);
        }
        if (hashingStrategy == null) {
            $$$reportNull$$$0(3);
        }
        return new WeakKey(k, hashingStrategy.hashCode(k), hashingStrategy, this.myReferenceQueue);
    }
}
