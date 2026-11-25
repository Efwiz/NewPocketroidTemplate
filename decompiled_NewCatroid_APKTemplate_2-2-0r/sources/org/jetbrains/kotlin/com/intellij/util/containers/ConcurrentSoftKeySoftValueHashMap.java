package org.jetbrains.kotlin.com.intellij.util.containers;

import com.android.dx.rop.code.RegisterSpec;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import org.jetbrains.kotlin.com.intellij.util.ObjectUtilsRt;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;

/* JADX INFO: loaded from: classes3.dex */
final class ConcurrentSoftKeySoftValueHashMap<K, V> extends ConcurrentWeakKeySoftValueHashMap<K, V> {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 3 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 3 ? 3 : 2];
        if (i == 1) {
            objArr[0] = JvmAnnotationNames.KIND_FIELD_NAME;
        } else if (i == 2) {
            objArr[0] = RegisterSpec.PREFIX;
        } else if (i != 3) {
            objArr[0] = "hashingStrategy";
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/util/containers/ConcurrentSoftKeySoftValueHashMap";
        }
        if (i != 3) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/containers/ConcurrentSoftKeySoftValueHashMap";
        } else {
            objArr[1] = "createKeyReference";
        }
        if (i == 1 || i == 2) {
            objArr[2] = "createKeyReference";
        } else if (i != 3) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i == 3) {
            throw new IllegalStateException(str2);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    ConcurrentSoftKeySoftValueHashMap(int i, float f, int i2, HashingStrategy<? super K> hashingStrategy) {
        super(i, f, i2, hashingStrategy);
        if (hashingStrategy == null) {
            $$$reportNull$$$0(0);
        }
    }

    private static class SoftKey<K, V> extends SoftReference<K> implements ConcurrentWeakKeySoftValueHashMap.KeyReference<K, V> {
        private final int myHash;
        private final HashingStrategy<? super K> myStrategy;
        private final ConcurrentWeakKeySoftValueHashMap.ValueReference<K, V> myValueReference;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = i != 4 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
            Object[] objArr = new Object[i != 4 ? 3 : 2];
            if (i == 1) {
                objArr[0] = "valueReference";
            } else if (i == 2) {
                objArr[0] = "strategy";
            } else if (i == 3) {
                objArr[0] = "queue";
            } else if (i != 4) {
                objArr[0] = JvmAnnotationNames.KIND_FIELD_NAME;
            } else {
                objArr[0] = "org/jetbrains/kotlin/com/intellij/util/containers/ConcurrentSoftKeySoftValueHashMap$SoftKey";
            }
            if (i != 4) {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/util/containers/ConcurrentSoftKeySoftValueHashMap$SoftKey";
            } else {
                objArr[1] = "getValueReference";
            }
            if (i != 4) {
                objArr[2] = "<init>";
            }
            String str2 = String.format(str, objArr);
            if (i == 4) {
                throw new IllegalStateException(str2);
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        SoftKey(K k, ConcurrentWeakKeySoftValueHashMap.ValueReference<K, V> valueReference, HashingStrategy<? super K> hashingStrategy, ReferenceQueue<? super K> referenceQueue) {
            super(k, referenceQueue);
            if (k == null) {
                $$$reportNull$$$0(0);
            }
            if (valueReference == null) {
                $$$reportNull$$$0(1);
            }
            if (hashingStrategy == null) {
                $$$reportNull$$$0(2);
            }
            if (referenceQueue == null) {
                $$$reportNull$$$0(3);
            }
            this.myValueReference = valueReference;
            this.myHash = hashingStrategy.hashCode(k);
            this.myStrategy = hashingStrategy;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ConcurrentWeakKeySoftValueHashMap.KeyReference)) {
                return false;
            }
            K k = get();
            Object obj2 = ((ConcurrentWeakKeySoftValueHashMap.KeyReference) obj).get();
            if (k == null || obj2 == null) {
                return false;
            }
            if (k == obj2) {
                return true;
            }
            return this.myHash == obj.hashCode() && this.myStrategy.equals(k, obj2);
        }

        public int hashCode() {
            return this.myHash;
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.containers.ConcurrentWeakKeySoftValueHashMap.KeyReference
        public ConcurrentWeakKeySoftValueHashMap.ValueReference<K, V> getValueReference() {
            ConcurrentWeakKeySoftValueHashMap.ValueReference<K, V> valueReference = this.myValueReference;
            if (valueReference == null) {
                $$$reportNull$$$0(4);
            }
            return valueReference;
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
        SoftKey softKey = new SoftKey(k, valueReferenceCreateValueReference, this.myHashingStrategy, this.myKeyQueue);
        if (valueReferenceCreateValueReference instanceof ConcurrentWeakKeySoftValueHashMap.SoftValue) {
            ((ConcurrentWeakKeySoftValueHashMap.SoftValue) valueReferenceCreateValueReference).myKeyReference = softKey;
        }
        ObjectUtilsRt.reachabilityFence(k);
        return softKey;
    }
}
