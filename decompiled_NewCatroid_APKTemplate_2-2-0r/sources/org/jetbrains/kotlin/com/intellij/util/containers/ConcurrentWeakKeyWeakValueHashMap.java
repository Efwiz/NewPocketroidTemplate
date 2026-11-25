package org.jetbrains.kotlin.com.intellij.util.containers;

import com.android.dx.rop.code.RegisterSpec;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import org.jetbrains.kotlin.com.intellij.util.ObjectUtilsRt;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;

/* JADX INFO: loaded from: classes3.dex */
final class ConcurrentWeakKeyWeakValueHashMap<K, V> extends ConcurrentWeakKeySoftValueHashMap<K, V> {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 3 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 3 ? 3 : 2];
        if (i == 1) {
            objArr[0] = JvmAnnotationNames.KIND_FIELD_NAME;
        } else if (i == 2) {
            objArr[0] = RegisterSpec.PREFIX;
        } else if (i == 3) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/util/containers/ConcurrentWeakKeyWeakValueHashMap";
        } else if (i == 4) {
            objArr[0] = "value";
        } else if (i != 5) {
            objArr[0] = "hashingStrategy";
        } else {
            objArr[0] = "queue";
        }
        if (i != 3) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/containers/ConcurrentWeakKeyWeakValueHashMap";
        } else {
            objArr[1] = "createKeyReference";
        }
        if (i == 1 || i == 2) {
            objArr[2] = "createKeyReference";
        } else if (i != 3) {
            if (i == 4 || i == 5) {
                objArr[2] = "createValueReference";
            } else {
                objArr[2] = "<init>";
            }
        }
        String str2 = String.format(str, objArr);
        if (i == 3) {
            throw new IllegalStateException(str2);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    ConcurrentWeakKeyWeakValueHashMap(int i, float f, int i2, HashingStrategy<? super K> hashingStrategy) {
        super(i, f, i2, hashingStrategy);
        if (hashingStrategy == null) {
            $$$reportNull$$$0(0);
        }
    }

    private static final class WeakValue<K, V> extends WeakReference<V> implements ConcurrentWeakKeySoftValueHashMap.ValueReference<K, V> {
        private volatile ConcurrentWeakKeySoftValueHashMap.KeyReference<K, V> myKeyReference;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = i != 2 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
            Object[] objArr = new Object[i != 2 ? 3 : 2];
            if (i == 1) {
                objArr[0] = "queue";
            } else if (i != 2) {
                objArr[0] = "value";
            } else {
                objArr[0] = "org/jetbrains/kotlin/com/intellij/util/containers/ConcurrentWeakKeyWeakValueHashMap$WeakValue";
            }
            if (i != 2) {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/util/containers/ConcurrentWeakKeyWeakValueHashMap$WeakValue";
            } else {
                objArr[1] = "getKeyReference";
            }
            if (i != 2) {
                objArr[2] = "<init>";
            }
            String str2 = String.format(str, objArr);
            if (i == 2) {
                throw new IllegalStateException(str2);
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        private WeakValue(V v, ReferenceQueue<? super V> referenceQueue) {
            super(v, referenceQueue);
            if (v == null) {
                $$$reportNull$$$0(0);
            }
            if (referenceQueue == null) {
                $$$reportNull$$$0(1);
            }
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            V v = get();
            return v != null && v.equals(((ConcurrentWeakKeySoftValueHashMap.ValueReference) obj).get());
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.containers.ConcurrentWeakKeySoftValueHashMap.ValueReference
        public ConcurrentWeakKeySoftValueHashMap.KeyReference<K, V> getKeyReference() {
            ConcurrentWeakKeySoftValueHashMap.KeyReference<K, V> keyReference = this.myKeyReference;
            if (keyReference == null) {
                $$$reportNull$$$0(2);
            }
            return keyReference;
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.containers.ConcurrentWeakKeySoftValueHashMap
    ConcurrentWeakKeySoftValueHashMap.KeyReference<K, V> createKeyReference(K k, V v) {
        if (k == null) {
            $$$reportNull$$$0(1);
        }
        if (v == null) {
            $$$reportNull$$$0(2);
        }
        ConcurrentWeakKeySoftValueHashMap.ValueReference<K, V> valueReferenceCreateValueReference = createValueReference(v, this.myValueQueue);
        ConcurrentWeakKeySoftValueHashMap.WeakKey weakKey = new ConcurrentWeakKeySoftValueHashMap.WeakKey(k, valueReferenceCreateValueReference, this.myHashingStrategy, this.myKeyQueue);
        if (valueReferenceCreateValueReference instanceof WeakValue) {
            ((WeakValue) valueReferenceCreateValueReference).myKeyReference = weakKey;
        }
        ObjectUtilsRt.reachabilityFence(k);
        return weakKey;
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.containers.ConcurrentWeakKeySoftValueHashMap
    protected ConcurrentWeakKeySoftValueHashMap.ValueReference<K, V> createValueReference(V v, ReferenceQueue<? super V> referenceQueue) {
        if (v == null) {
            $$$reportNull$$$0(4);
        }
        if (referenceQueue == null) {
            $$$reportNull$$$0(5);
        }
        return new WeakValue(v, referenceQueue);
    }
}
