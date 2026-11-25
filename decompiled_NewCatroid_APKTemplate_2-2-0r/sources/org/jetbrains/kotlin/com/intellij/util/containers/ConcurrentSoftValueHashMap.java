package org.jetbrains.kotlin.com.intellij.util.containers;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import org.antlr.stringtemplate.language.ASTExpr;

/* JADX INFO: loaded from: classes3.dex */
final class ConcurrentSoftValueHashMap<K, V> extends ConcurrentRefValueHashMap<K, V> {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 1) {
            objArr[0] = ASTExpr.DEFAULT_MAP_KEY_NAME;
        } else {
            objArr[0] = "value";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/util/containers/ConcurrentSoftValueHashMap";
        objArr[2] = "createValueReference";
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    ConcurrentSoftValueHashMap() {
    }

    private static final class MySoftReference<K, V> extends SoftReference<V> implements ConcurrentRefValueHashMap.ValueReference<K, V> {
        private final K key;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = i != 3 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
            Object[] objArr = new Object[i != 3 ? 3 : 2];
            if (i == 1) {
                objArr[0] = "referent";
            } else if (i == 2) {
                objArr[0] = "q";
            } else if (i != 3) {
                objArr[0] = ASTExpr.DEFAULT_MAP_KEY_NAME;
            } else {
                objArr[0] = "org/jetbrains/kotlin/com/intellij/util/containers/ConcurrentSoftValueHashMap$MySoftReference";
            }
            if (i != 3) {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/util/containers/ConcurrentSoftValueHashMap$MySoftReference";
            } else {
                objArr[1] = "getKey";
            }
            if (i != 3) {
                objArr[2] = "<init>";
            }
            String str2 = String.format(str, objArr);
            if (i == 3) {
                throw new IllegalStateException(str2);
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        private MySoftReference(K k, V v, ReferenceQueue<V> referenceQueue) {
            super(v, referenceQueue);
            if (k == null) {
                $$$reportNull$$$0(0);
            }
            if (v == null) {
                $$$reportNull$$$0(1);
            }
            if (referenceQueue == null) {
                $$$reportNull$$$0(2);
            }
            this.key = k;
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.containers.ConcurrentRefValueHashMap.ValueReference
        public K getKey() {
            K k = this.key;
            if (k == null) {
                $$$reportNull$$$0(3);
            }
            return k;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            ConcurrentRefValueHashMap.ValueReference valueReference = (ConcurrentRefValueHashMap.ValueReference) obj;
            V v = get();
            return this.key.equals(valueReference.getKey()) && v != null && v.equals(valueReference.get());
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.containers.ConcurrentRefValueHashMap
    ConcurrentRefValueHashMap.ValueReference<K, V> createValueReference(K k, V v) {
        if (k == null) {
            $$$reportNull$$$0(0);
        }
        if (v == null) {
            $$$reportNull$$$0(1);
        }
        return new MySoftReference(k, v, this.myQueue);
    }
}
