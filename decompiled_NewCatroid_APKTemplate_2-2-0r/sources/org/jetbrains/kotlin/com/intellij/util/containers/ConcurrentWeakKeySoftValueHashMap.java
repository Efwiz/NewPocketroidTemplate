package org.jetbrains.kotlin.com.intellij.util.containers;

import com.android.dx.rop.code.RegisterSpec;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.reandroid.arsc.value.CompoundEntry;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Supplier;
import org.antlr.stringtemplate.language.ASTExpr;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.com.intellij.util.ObjectUtilsRt;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;

/* JADX INFO: loaded from: classes3.dex */
@Deprecated
public class ConcurrentWeakKeySoftValueHashMap<K, V> implements ConcurrentMap<K, V> {
    private static final ThreadLocal<HardKey<?, ?>> HARD_KEY = ThreadLocal.withInitial(new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.util.containers.ConcurrentWeakKeySoftValueHashMap$$ExternalSyntheticLambda0
        @Override // java.util.function.Supplier
        public final Object get() {
            return ConcurrentWeakKeySoftValueHashMap.lambda$static$0();
        }
    });
    final HashingStrategy<? super K> myHashingStrategy;
    final ReferenceQueue<K> myKeyQueue;
    private final ConcurrentMap<KeyReference<K, V>, ValueReference<K, V>> myMap;
    final ReferenceQueue<V> myValueQueue;

    public interface KeyReference<K, V> extends Supplier<K> {
        @Override // java.util.function.Supplier
        K get();

        ValueReference<K, V> getValueReference();
    }

    public interface ValueReference<K, V> extends Supplier<V> {
        @Override // java.util.function.Supplier
        V get();

        KeyReference<K, V> getKeyReference();
    }

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 3 || i == 6 || i == 9) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 3 || i == 6 || i == 9) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = JvmAnnotationNames.KIND_FIELD_NAME;
                break;
            case 2:
                objArr[0] = RegisterSpec.PREFIX;
                break;
            case 3:
            case 6:
            case 9:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/util/containers/ConcurrentWeakKeySoftValueHashMap";
                break;
            case 4:
            case 11:
            case 13:
            case 18:
                objArr[0] = "value";
                break;
            case 5:
                objArr[0] = "queue";
                break;
            case 7:
            case 10:
            case 12:
            case 14:
            case 17:
                objArr[0] = ASTExpr.DEFAULT_MAP_KEY_NAME;
                break;
            case 8:
                objArr[0] = "m";
                break;
            case 15:
                objArr[0] = "oldValue";
                break;
            case 16:
                objArr[0] = "newValue";
                break;
            default:
                objArr[0] = "hashingStrategy";
                break;
        }
        if (i == 3) {
            objArr[1] = "createKeyReference";
        } else if (i == 6) {
            objArr[1] = "createHardKey";
        } else if (i != 9) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/containers/ConcurrentWeakKeySoftValueHashMap";
        } else {
            objArr[1] = CompoundEntry.NAME_values;
        }
        switch (i) {
            case 1:
            case 2:
                objArr[2] = "createKeyReference";
                break;
            case 3:
            case 6:
            case 9:
                break;
            case 4:
            case 5:
                objArr[2] = "createValueReference";
                break;
            case 7:
                objArr[2] = "get";
                break;
            case 8:
                objArr[2] = "putAll";
                break;
            case 10:
            case 11:
                objArr[2] = ProductAction.ACTION_REMOVE;
                break;
            case 12:
            case 13:
                objArr[2] = "putIfAbsent";
                break;
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
                objArr[2] = SchemaSymbols.ATTVAL_REPLACE;
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 3 && i != 6 && i != 9) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    protected ConcurrentWeakKeySoftValueHashMap(int i, float f, int i2) {
        this(i, f, i2, HashingStrategy.canonical());
    }

    protected ConcurrentWeakKeySoftValueHashMap(int i, float f, int i2, HashingStrategy<? super K> hashingStrategy) {
        if (hashingStrategy == null) {
            $$$reportNull$$$0(0);
        }
        this.myKeyQueue = new ReferenceQueue<>();
        this.myValueQueue = new ReferenceQueue<>();
        this.myHashingStrategy = hashingStrategy;
        this.myMap = new ConcurrentHashMap(i, f, i2);
    }

    static final class WeakKey<K, V> extends WeakReference<K> implements KeyReference<K, V> {
        private final int myHash;
        private final HashingStrategy<? super K> myStrategy;
        private final ValueReference<K, V> myValueReference;

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
                objArr[0] = "org/jetbrains/kotlin/com/intellij/util/containers/ConcurrentWeakKeySoftValueHashMap$WeakKey";
            }
            if (i != 4) {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/util/containers/ConcurrentWeakKeySoftValueHashMap$WeakKey";
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
        WeakKey(K k, ValueReference<K, V> valueReference, HashingStrategy<? super K> hashingStrategy, ReferenceQueue<? super K> referenceQueue) {
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
            if (!(obj instanceof KeyReference)) {
                return false;
            }
            K k = get();
            Object obj2 = ((KeyReference) obj).get();
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
        public ValueReference<K, V> getValueReference() {
            ValueReference<K, V> valueReference = this.myValueReference;
            if (valueReference == null) {
                $$$reportNull$$$0(4);
            }
            return valueReference;
        }
    }

    static final class SoftValue<K, V> extends SoftReference<V> implements ValueReference<K, V> {
        volatile KeyReference<K, V> myKeyReference;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = i != 2 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
            Object[] objArr = new Object[i != 2 ? 3 : 2];
            if (i == 1) {
                objArr[0] = "queue";
            } else if (i != 2) {
                objArr[0] = "value";
            } else {
                objArr[0] = "org/jetbrains/kotlin/com/intellij/util/containers/ConcurrentWeakKeySoftValueHashMap$SoftValue";
            }
            if (i != 2) {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/util/containers/ConcurrentWeakKeySoftValueHashMap$SoftValue";
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
        private SoftValue(V v, ReferenceQueue<? super V> referenceQueue) {
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
            return v != null && v.equals(((ValueReference) obj).get());
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.containers.ConcurrentWeakKeySoftValueHashMap.ValueReference
        public KeyReference<K, V> getKeyReference() {
            KeyReference<K, V> keyReference = this.myKeyReference;
            if (keyReference == null) {
                $$$reportNull$$$0(2);
            }
            return keyReference;
        }
    }

    KeyReference<K, V> createKeyReference(K k, V v) {
        if (k == null) {
            $$$reportNull$$$0(1);
        }
        if (v == null) {
            $$$reportNull$$$0(2);
        }
        ValueReference<K, V> valueReferenceCreateValueReference = createValueReference(v, this.myValueQueue);
        WeakKey weakKey = new WeakKey(k, valueReferenceCreateValueReference, this.myHashingStrategy, this.myKeyQueue);
        if (valueReferenceCreateValueReference instanceof SoftValue) {
            ((SoftValue) valueReferenceCreateValueReference).myKeyReference = weakKey;
        }
        ObjectUtilsRt.reachabilityFence(k);
        return weakKey;
    }

    protected ValueReference<K, V> createValueReference(V v, ReferenceQueue<? super V> referenceQueue) {
        if (v == null) {
            $$$reportNull$$$0(4);
        }
        if (referenceQueue == null) {
            $$$reportNull$$$0(5);
        }
        return new SoftValue(v, referenceQueue);
    }

    @Override // java.util.Map
    public int size() {
        return this.myMap.size();
    }

    @Override // java.util.Map
    public boolean isEmpty() {
        return this.myMap.isEmpty();
    }

    @Override // java.util.Map
    public void clear() {
        this.myMap.clear();
        processQueues();
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class HardKey<K, V> implements KeyReference<K, V> {
        private int myHash;
        private K myKey;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", ASTExpr.DEFAULT_MAP_KEY_NAME, "org/jetbrains/kotlin/com/intellij/util/containers/ConcurrentWeakKeySoftValueHashMap$HardKey", FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX));
        }

        private HardKey() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void set(K k, int i) {
            if (k == null) {
                $$$reportNull$$$0(0);
            }
            this.myKey = k;
            this.myHash = i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clear() {
            this.myKey = null;
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.containers.ConcurrentWeakKeySoftValueHashMap.KeyReference, java.util.function.Supplier
        public K get() {
            return this.myKey;
        }

        public boolean equals(Object obj) {
            return obj.equals(this);
        }

        public int hashCode() {
            return this.myHash;
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.containers.ConcurrentWeakKeySoftValueHashMap.KeyReference
        public ValueReference<K, V> getValueReference() {
            throw new UnsupportedOperationException();
        }
    }

    static /* synthetic */ HardKey lambda$static$0() {
        return new HardKey();
    }

    private HardKey<K, V> createHardKey(Object obj) {
        HardKey<K, V> hardKey = (HardKey) HARD_KEY.get();
        hardKey.set(obj, this.myHashingStrategy.hashCode(obj));
        if (hardKey == null) {
            $$$reportNull$$$0(6);
        }
        return hardKey;
    }

    @Override // java.util.Map
    public V get(Object obj) {
        if (obj == null) {
            $$$reportNull$$$0(7);
        }
        HardKey<K, V> hardKeyCreateHardKey = createHardKey(obj);
        try {
            ValueReference<K, V> valueReference = this.myMap.get(hardKeyCreateHardKey);
            return valueReference == null ? null : valueReference.get();
        } finally {
            hardKeyCreateHardKey.clear();
        }
    }

    @Override // java.util.Map
    public boolean containsKey(Object obj) {
        throw RefValueHashMapUtil.pointlessContainsKey();
    }

    @Override // java.util.Map
    public boolean containsValue(Object obj) {
        throw RefValueHashMapUtil.pointlessContainsValue();
    }

    @Override // java.util.Map
    public V remove(Object obj) {
        HardKey<K, V> hardKeyCreateHardKey = createHardKey(obj);
        try {
            ValueReference<K, V> valueReferenceRemove = this.myMap.remove(hardKeyCreateHardKey);
            return valueReferenceRemove == null ? null : valueReferenceRemove.get();
        } finally {
            hardKeyCreateHardKey.clear();
            processQueues();
        }
    }

    @Override // java.util.Map
    public void putAll(Map<? extends K, ? extends V> map) {
        if (map == null) {
            $$$reportNull$$$0(8);
        }
        for (Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override // java.util.Map
    public V put(K k, V v) {
        KeyReference<K, V> keyReferenceCreateKeyReference = createKeyReference(k, v);
        ValueReference<K, V> valueReferencePut = this.myMap.put(keyReferenceCreateKeyReference, keyReferenceCreateKeyReference.getValueReference());
        processQueues();
        if (valueReferencePut == null) {
            return null;
        }
        return valueReferencePut.get();
    }

    private boolean processQueues() {
        boolean zRemove = false;
        while (true) {
            KeyReference keyReference = (KeyReference) this.myKeyQueue.poll();
            if (keyReference == null) {
                break;
            }
            zRemove |= this.myMap.remove(keyReference, keyReference.getValueReference());
        }
        while (true) {
            ValueReference valueReference = (ValueReference) this.myValueQueue.poll();
            if (valueReference == null) {
                return zRemove;
            }
            zRemove |= this.myMap.remove(valueReference.getKeyReference(), valueReference);
        }
    }

    @Override // java.util.Map
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Map
    public Collection<V> values() {
        ArrayList arrayList = new ArrayList();
        Iterator<ValueReference<K, V>> it = this.myMap.values().iterator();
        while (it.hasNext()) {
            ValueReference<K, V> next = it.next();
            V v = next == null ? null : next.get();
            if (v != null) {
                arrayList.add(v);
            }
        }
        return arrayList;
    }

    @Override // java.util.Map
    public Set<Map.Entry<K, V>> entrySet() {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.concurrent.ConcurrentMap, java.util.Map
    public boolean remove(Object obj, Object obj2) {
        if (obj == null) {
            $$$reportNull$$$0(10);
        }
        if (obj2 == null) {
            $$$reportNull$$$0(11);
        }
        HardKey<K, V> hardKeyCreateHardKey = createHardKey(obj);
        try {
            ValueReference<K, V> valueReference = this.myMap.get(hardKeyCreateHardKey);
            return obj2.equals(valueReference == null ? null : valueReference.get()) && this.myMap.remove(hardKeyCreateHardKey, valueReference);
        } finally {
            hardKeyCreateHardKey.clear();
            processQueues();
        }
    }

    @Override // java.util.concurrent.ConcurrentMap, java.util.Map
    public V putIfAbsent(K k, V v) {
        V v2;
        if (k == null) {
            $$$reportNull$$$0(12);
        }
        if (v == null) {
            $$$reportNull$$$0(13);
        }
        KeyReference<K, V> keyReferenceCreateKeyReference = createKeyReference(k, v);
        ValueReference<K, V> valueReference = keyReferenceCreateKeyReference.getValueReference();
        while (true) {
            ValueReference<K, V> valueReferencePutIfAbsent = this.myMap.putIfAbsent(keyReferenceCreateKeyReference, valueReference);
            v2 = null;
            if (valueReferencePutIfAbsent == null) {
                break;
            }
            V v3 = valueReferencePutIfAbsent.get();
            if (v3 != null) {
                v2 = v3;
                break;
            }
            if (this.myMap.replace(keyReferenceCreateKeyReference, valueReferencePutIfAbsent, valueReference)) {
                break;
            }
            processQueues();
        }
        processQueues();
        return v2;
    }

    @Override // java.util.concurrent.ConcurrentMap, java.util.Map
    public boolean replace(K k, V v, V v2) {
        if (k == null) {
            $$$reportNull$$$0(14);
        }
        if (v == null) {
            $$$reportNull$$$0(15);
        }
        if (v2 == null) {
            $$$reportNull$$$0(16);
        }
        KeyReference<K, V> keyReferenceCreateKeyReference = createKeyReference(k, v);
        boolean zReplace = this.myMap.replace(keyReferenceCreateKeyReference, keyReferenceCreateKeyReference.getValueReference(), createKeyReference(k, v2).getValueReference());
        processQueues();
        return zReplace;
    }

    @Override // java.util.concurrent.ConcurrentMap, java.util.Map
    public V replace(K k, V v) {
        if (k == null) {
            $$$reportNull$$$0(17);
        }
        if (v == null) {
            $$$reportNull$$$0(18);
        }
        KeyReference<K, V> keyReferenceCreateKeyReference = createKeyReference(k, v);
        ValueReference<K, V> valueReferenceReplace = this.myMap.replace(keyReferenceCreateKeyReference, keyReferenceCreateKeyReference.getValueReference());
        V v2 = valueReferenceReplace == null ? null : valueReferenceReplace.get();
        processQueues();
        return v2;
    }
}
