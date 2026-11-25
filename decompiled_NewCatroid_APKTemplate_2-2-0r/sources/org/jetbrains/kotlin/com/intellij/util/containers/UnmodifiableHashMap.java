package org.jetbrains.kotlin.com.intellij.util.containers;

import com.reandroid.arsc.value.CompoundEntry;
import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtilRt;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Hash;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2ObjectOpenCustomHashMap;

/* JADX INFO: loaded from: classes3.dex */
public final class UnmodifiableHashMap<K, V> implements Map<K, V> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final Object[] data;
    private final K k1;
    private final K k2;
    private final K k3;
    private Set<K> keySet;
    private final int size;
    private final HashingStrategy<K> strategy;
    private final V v1;
    private final V v2;
    private final V v3;
    private Collection<V> values;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 3 || i == 7 || i == 9 || i == 11 || i == 12 || i == 14 || i == 15) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 3 || i == 7 || i == 9 || i == 11 || i == 12 || i == 14 || i == 15) ? 2 : 3];
        switch (i) {
            case 1:
            case 4:
                objArr[0] = "strategy";
                break;
            case 2:
            case 10:
            default:
                objArr[0] = "map";
                break;
            case 3:
            case 7:
            case 9:
            case 11:
            case 12:
            case 14:
            case 15:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/util/containers/UnmodifiableHashMap";
                break;
            case 5:
                objArr[0] = "data";
                break;
            case 6:
            case 8:
                objArr[0] = ASTExpr.DEFAULT_MAP_KEY_NAME;
                break;
            case 13:
                objArr[0] = "m";
                break;
        }
        if (i == 3) {
            objArr[1] = "fromMap";
        } else if (i == 7) {
            objArr[1] = "without";
        } else if (i == 9) {
            objArr[1] = "with";
        } else if (i == 11 || i == 12) {
            objArr[1] = "withAll";
        } else if (i == 14) {
            objArr[1] = "keySet";
        } else if (i != 15) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/containers/UnmodifiableHashMap";
        } else {
            objArr[1] = CompoundEntry.NAME_values;
        }
        switch (i) {
            case 3:
            case 7:
            case 9:
            case 11:
            case 12:
            case 14:
            case 15:
                break;
            case 4:
            case 5:
                objArr[2] = "<init>";
                break;
            case 6:
                objArr[2] = "without";
                break;
            case 8:
                objArr[2] = "with";
                break;
            case 10:
                objArr[2] = "withAll";
                break;
            case 13:
                objArr[2] = "putAll";
                break;
            default:
                objArr[2] = "fromMap";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 3 && i != 7 && i != 9 && i != 11 && i != 12 && i != 14 && i != 15) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public static <K, V> UnmodifiableHashMap<K, V> empty() {
        return empty(HashingStrategy.canonical());
    }

    public static <K, V> UnmodifiableHashMap<K, V> empty(HashingStrategy<K> hashingStrategy) {
        return new UnmodifiableHashMap<>(hashingStrategy, ArrayUtilRt.EMPTY_OBJECT_ARRAY, null, null, null, null, null, null);
    }

    public static <K, V> UnmodifiableHashMap<K, V> fromMap(final HashingStrategy<K> hashingStrategy, Map<? extends K, ? extends V> map) {
        K k;
        V value;
        K k2;
        V v;
        K k3;
        V value2;
        if (hashingStrategy == null) {
            $$$reportNull$$$0(1);
        }
        if (map == null) {
            $$$reportNull$$$0(2);
        }
        if (map instanceof UnmodifiableHashMap) {
            UnmodifiableHashMap<K, V> unmodifiableHashMap = (UnmodifiableHashMap) map;
            if (((UnmodifiableHashMap) unmodifiableHashMap).strategy == hashingStrategy) {
                if (unmodifiableHashMap == null) {
                    $$$reportNull$$$0(3);
                }
                return unmodifiableHashMap;
            }
        }
        if (map.size() <= 3) {
            Iterator<Map.Entry<? extends K, ? extends V>> it = map.entrySet().iterator();
            if (it.hasNext()) {
                Map.Entry<? extends K, ? extends V> next = it.next();
                K key = next.getKey();
                value = next.getValue();
                if (it.hasNext()) {
                    Map.Entry<? extends K, ? extends V> next2 = it.next();
                    K key2 = next2.getKey();
                    V value3 = next2.getValue();
                    if (it.hasNext()) {
                        Map.Entry<? extends K, ? extends V> next3 = it.next();
                        K key3 = next3.getKey();
                        value2 = next3.getValue();
                        k3 = key3;
                    } else {
                        k3 = null;
                        value2 = null;
                    }
                    k = key;
                    v = value3;
                    k2 = key2;
                } else {
                    value = value;
                    k2 = null;
                    v = null;
                    k3 = null;
                    value2 = null;
                    k = key;
                }
            } else {
                k = null;
                value = null;
                k2 = null;
                v = null;
                k3 = null;
                value2 = null;
            }
            return new UnmodifiableHashMap<>(hashingStrategy, ArrayUtilRt.EMPTY_OBJECT_ARRAY, k, value, k2, v, k3, value2);
        }
        final Object[] objArr = new Object[map.size() * 4];
        map.forEach(new BiConsumer() { // from class: org.jetbrains.kotlin.com.intellij.util.containers.UnmodifiableHashMap$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                UnmodifiableHashMap.insert(hashingStrategy, objArr, Objects.requireNonNull(obj), obj2);
            }
        });
        return new UnmodifiableHashMap<>(hashingStrategy, objArr, null, null, null, null, null, null);
    }

    private UnmodifiableHashMap(HashingStrategy<K> hashingStrategy, Object[] objArr, K k, V v, K k2, V v2, K k3, V v3) {
        if (hashingStrategy == null) {
            $$$reportNull$$$0(4);
        }
        if (objArr == null) {
            $$$reportNull$$$0(5);
        }
        this.strategy = hashingStrategy;
        this.data = objArr;
        this.k1 = k;
        this.k2 = k2;
        this.k3 = k3;
        this.v1 = v;
        this.v2 = v2;
        this.v3 = v3;
        this.size = (objArr.length / 4) + (k == null ? 0 : k2 == null ? 1 : k3 == null ? 2 : 3);
    }

    public UnmodifiableHashMap<K, V> without(K k) {
        Object obj;
        if (k == null) {
            $$$reportNull$$$0(6);
        }
        Object[] objArr = this.data;
        int iTablePos = objArr.length == 0 ? -1 : tablePos(this.strategy, objArr, k);
        if (iTablePos >= 0) {
            Object[] objArr2 = new Object[(this.size - 1) * 4];
            int i = 0;
            while (true) {
                Object[] objArr3 = this.data;
                if (i >= objArr3.length) {
                    break;
                }
                if (i != iTablePos && (obj = objArr3[i]) != null) {
                    insert(this.strategy, objArr2, obj, objArr3[i + 1]);
                }
                i += 2;
            }
            K k2 = this.k1;
            if (k2 != null) {
                insert(this.strategy, objArr2, k2, this.v1);
                K k3 = this.k2;
                if (k3 != null) {
                    insert(this.strategy, objArr2, k3, this.v2);
                    K k4 = this.k3;
                    if (k4 != null) {
                        insert(this.strategy, objArr2, k4, this.v3);
                    }
                }
            }
            return new UnmodifiableHashMap<>(this.strategy, objArr2, null, null, null, null, null, null);
        }
        K k5 = this.k1;
        if (k5 != null) {
            if (this.strategy.equals(k5, k)) {
                return new UnmodifiableHashMap<>(this.strategy, this.data, this.k2, this.v2, this.k3, this.v3, null, null);
            }
            K k6 = this.k2;
            if (k6 != null) {
                if (this.strategy.equals(k6, k)) {
                    return new UnmodifiableHashMap<>(this.strategy, this.data, this.k1, this.v1, this.k3, this.v3, null, null);
                }
                K k7 = this.k3;
                if (k7 != null && this.strategy.equals(k7, k)) {
                    return new UnmodifiableHashMap<>(this.strategy, this.data, this.k1, this.v1, this.k2, this.v2, null, null);
                }
            }
        }
        return this;
    }

    public UnmodifiableHashMap<K, V> with(K k, V v) {
        if (k == null) {
            $$$reportNull$$$0(8);
        }
        Object[] objArr = this.data;
        int iTablePos = objArr.length == 0 ? -1 : tablePos(this.strategy, objArr, k);
        if (iTablePos >= 0) {
            Object[] objArr2 = this.data;
            int i = iTablePos + 1;
            if (objArr2[i] == v) {
                return this;
            }
            Object[] objArr3 = (Object[]) objArr2.clone();
            objArr3[i] = v;
            return new UnmodifiableHashMap<>(this.strategy, objArr3, this.k1, this.v1, this.k2, this.v2, this.k3, this.v3);
        }
        K k2 = this.k1;
        if (k2 == null) {
            return new UnmodifiableHashMap<>(this.strategy, this.data, k, v, null, null, null, null);
        }
        if (this.strategy.equals(k2, k)) {
            return v == this.v1 ? this : new UnmodifiableHashMap<>(this.strategy, this.data, this.k1, v, this.k2, this.v2, this.k3, this.v3);
        }
        K k3 = this.k2;
        if (k3 == null) {
            return new UnmodifiableHashMap<>(this.strategy, this.data, this.k1, this.v1, k, v, null, null);
        }
        if (this.strategy.equals(k3, k)) {
            return v == this.v2 ? this : new UnmodifiableHashMap<>(this.strategy, this.data, this.k1, this.v1, this.k2, v, this.k3, this.v3);
        }
        K k4 = this.k3;
        if (k4 == null) {
            return new UnmodifiableHashMap<>(this.strategy, this.data, this.k1, this.v1, this.k2, this.v2, k, v);
        }
        if (this.strategy.equals(k4, k)) {
            return v == this.v3 ? this : new UnmodifiableHashMap<>(this.strategy, this.data, this.k1, this.v1, this.k2, this.v2, this.k3, v);
        }
        Object[] objArr4 = new Object[(this.size + 1) * 4];
        int i2 = 0;
        while (true) {
            Object[] objArr5 = this.data;
            if (i2 < objArr5.length) {
                Object obj = objArr5[i2];
                if (obj != null) {
                    insert(this.strategy, objArr4, obj, objArr5[i2 + 1]);
                }
                i2 += 2;
            } else {
                insert(this.strategy, objArr4, this.k1, this.v1);
                insert(this.strategy, objArr4, this.k2, this.v2);
                insert(this.strategy, objArr4, this.k3, this.v3);
                insert(this.strategy, objArr4, k, v);
                return new UnmodifiableHashMap<>(this.strategy, objArr4, null, null, null, null, null, null);
            }
        }
    }

    public UnmodifiableHashMap<K, V> withAll(Map<? extends K, ? extends V> map) {
        if (map == null) {
            $$$reportNull$$$0(10);
        }
        if (map.isEmpty()) {
            return this;
        }
        if (map.size() == 1) {
            Map.Entry<? extends K, ? extends V> next = map.entrySet().iterator().next();
            UnmodifiableHashMap<K, V> unmodifiableHashMapWith = with(next.getKey(), next.getValue());
            if (unmodifiableHashMapWith == null) {
                $$$reportNull$$$0(12);
            }
            return unmodifiableHashMapWith;
        }
        Object2ObjectOpenCustomHashMap object2ObjectOpenCustomHashMap = new Object2ObjectOpenCustomHashMap(this, new Hash.Strategy<K>() { // from class: org.jetbrains.kotlin.com.intellij.util.containers.UnmodifiableHashMap.1
            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Hash.Strategy
            public int hashCode(K k) {
                if (k == null) {
                    return 0;
                }
                return UnmodifiableHashMap.this.strategy.hashCode(k);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Hash.Strategy
            public boolean equals(K k, K k2) {
                return k == k2 || !(k == null || k2 == null || !UnmodifiableHashMap.this.strategy.equals(k, k2));
            }
        });
        object2ObjectOpenCustomHashMap.putAll(map);
        return fromMap(this.strategy, object2ObjectOpenCustomHashMap);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <K> void insert(HashingStrategy<K> hashingStrategy, Object[] objArr, K k, Object obj) {
        int i = ~tablePos(hashingStrategy, objArr, k);
        objArr[i] = k;
        objArr[i + 1] = obj;
    }

    @Override // java.util.Map
    public int size() {
        return this.size;
    }

    @Override // java.util.Map
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override // java.util.Map
    public boolean containsKey(Object obj) {
        if (obj == null) {
            return false;
        }
        Object[] objArr = this.data;
        if (objArr.length > 0 && tablePos(this.strategy, objArr, obj) >= 0) {
            return true;
        }
        K k = this.k1;
        if (k != null) {
            if (this.strategy.equals(k, obj)) {
                return true;
            }
            K k2 = this.k2;
            if (k2 != null) {
                if (this.strategy.equals(k2, obj)) {
                    return true;
                }
                K k3 = this.k3;
                if (k3 != null) {
                    return this.strategy.equals(k3, obj);
                }
            }
        }
        return false;
    }

    @Override // java.util.Map
    public boolean containsValue(Object obj) {
        if (this.k1 != null) {
            if (Objects.equals(this.v1, obj)) {
                return true;
            }
            if (this.k2 != null) {
                if (Objects.equals(this.v2, obj)) {
                    return true;
                }
                if (this.k3 != null && Objects.equals(this.v3, obj)) {
                    return true;
                }
            }
        }
        int i = 0;
        while (true) {
            Object[] objArr = this.data;
            if (i >= objArr.length) {
                return false;
            }
            if (objArr[i] != null && Objects.equals(objArr[i + 1], obj)) {
                return true;
            }
            i += 2;
        }
    }

    @Override // java.util.Map
    public V get(Object obj) {
        return getOrDefault(obj, null);
    }

    @Override // java.util.Map
    public V getOrDefault(Object obj, V v) {
        int iTablePos;
        if (obj == null) {
            return v;
        }
        K k = this.k1;
        if (k != null) {
            if (this.strategy.equals(k, obj)) {
                return this.v1;
            }
            K k2 = this.k2;
            if (k2 != null) {
                if (this.strategy.equals(k2, obj)) {
                    return this.v2;
                }
                K k3 = this.k3;
                if (k3 != null && this.strategy.equals(k3, obj)) {
                    return this.v3;
                }
            }
        }
        Object[] objArr = this.data;
        return (objArr.length != 0 && (iTablePos = tablePos(this.strategy, objArr, obj)) >= 0) ? (V) this.data[iTablePos + 1] : v;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static <K> int tablePos(HashingStrategy<K> hashingStrategy, Object[] objArr, K k) {
        int iFloorMod = Math.floorMod(hashingStrategy.hashCode(k), objArr.length / 2) * 2;
        while (true) {
            Object obj = objArr[iFloorMod];
            if (obj == null) {
                return ~iFloorMod;
            }
            if (hashingStrategy.equals(obj, k)) {
                return iFloorMod;
            }
            iFloorMod += 2;
            if (iFloorMod == objArr.length) {
                iFloorMod = 0;
            }
        }
    }

    @Override // java.util.Map
    public int hashCode() {
        int iHashCode;
        K k = this.k1;
        int i = 0;
        if (k != null) {
            iHashCode = this.strategy.hashCode(k) ^ Objects.hashCode(this.v1);
            K k2 = this.k2;
            if (k2 != null) {
                iHashCode += this.strategy.hashCode(k2) ^ Objects.hashCode(this.v2);
                K k3 = this.k3;
                if (k3 != null) {
                    iHashCode += this.strategy.hashCode(k3) ^ Objects.hashCode(this.v3);
                }
            }
        } else {
            iHashCode = 0;
        }
        while (true) {
            Object[] objArr = this.data;
            if (i >= objArr.length) {
                return iHashCode;
            }
            Object obj = objArr[i];
            if (obj != null) {
                iHashCode += this.strategy.hashCode((K) obj) ^ Objects.hashCode(this.data[i + 1]);
            }
            i += 2;
        }
    }

    @Override // java.util.Map
    public boolean equals(Object obj) {
        if (!(obj instanceof Map)) {
            return false;
        }
        Map map = (Map) obj;
        if (size() != map.size()) {
            return false;
        }
        K k = this.k1;
        if (k != null) {
            if (!Objects.equals(map.get(k), this.v1)) {
                return false;
            }
            K k2 = this.k2;
            if (k2 != null) {
                if (!Objects.equals(map.get(k2), this.v2)) {
                    return false;
                }
                K k3 = this.k3;
                if (k3 != null && !Objects.equals(map.get(k3), this.v3)) {
                    return false;
                }
            }
        }
        int i = 0;
        while (true) {
            Object[] objArr = this.data;
            if (i >= objArr.length) {
                return true;
            }
            Object obj2 = objArr[i];
            if (obj2 != null && !Objects.equals(map.get(obj2), this.data[i + 1])) {
                return false;
            }
            i += 2;
        }
    }

    @Override // java.util.Map
    @Deprecated
    public V put(K k, V v) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Map
    @Deprecated
    public V remove(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Map
    @Deprecated
    public void putAll(Map<? extends K, ? extends V> map) {
        if (map == null) {
            $$$reportNull$$$0(13);
        }
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Map
    @Deprecated
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Map
    public void forEach(BiConsumer<? super K, ? super V> biConsumer) {
        if (this.k1 != null) {
            if (this.k2 != null) {
                K k = this.k3;
                if (k != null) {
                    biConsumer.accept(k, this.v3);
                }
                biConsumer.accept(this.k2, this.v2);
            }
            biConsumer.accept(this.k1, this.v1);
        }
        int i = 0;
        while (true) {
            Object[] objArr = this.data;
            if (i >= objArr.length) {
                return;
            }
            Object obj = objArr[i];
            if (obj != null) {
                biConsumer.accept(obj, objArr[i + 1]);
            }
            i += 2;
        }
    }

    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append('{');
        forEach(new BiConsumer() { // from class: org.jetbrains.kotlin.com.intellij.util.containers.UnmodifiableHashMap$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                UnmodifiableHashMap.lambda$toString$1(sb, obj, obj2);
            }
        });
        return sb.append('}').toString();
    }

    static /* synthetic */ void lambda$toString$1(StringBuilder sb, Object obj, Object obj2) {
        if (sb.length() > 1) {
            sb.append(", ");
        }
        sb.append(obj).append('=').append(obj2);
    }

    private abstract class MyIterator<E> implements Iterator<E> {
        int pos;

        abstract E fieldElement(int i);

        abstract E tableElement(int i);

        MyIterator() {
            if (UnmodifiableHashMap.this.k1 != null) {
                this.pos = UnmodifiableHashMap.this.k2 != null ? UnmodifiableHashMap.this.k3 == null ? -2 : -3 : -1;
            } else {
                this.pos = -1;
                advance();
            }
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.pos < UnmodifiableHashMap.this.data.length;
        }

        private void advance() {
            int i = this.pos;
            if (i < 0) {
                this.pos = i + 1;
            } else {
                this.pos = i + 2;
            }
            if (this.pos >= 0) {
                while (this.pos < UnmodifiableHashMap.this.data.length) {
                    Object[] objArr = UnmodifiableHashMap.this.data;
                    int i2 = this.pos;
                    if (objArr[i2] != null) {
                        return;
                    } else {
                        this.pos = i2 + 1;
                    }
                }
            }
        }

        @Override // java.util.Iterator
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            int i = this.pos;
            if (i < 0) {
                int i2 = ~i;
                advance();
                return fieldElement(i2);
            }
            advance();
            return tableElement(i);
        }
    }

    @Override // java.util.Map
    public Set<K> keySet() {
        if (this.keySet == null) {
            this.keySet = new AbstractSet<K>() { // from class: org.jetbrains.kotlin.com.intellij.util.containers.UnmodifiableHashMap.2
                @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
                public Iterator<K> iterator() {
                    return new UnmodifiableHashMap<K, V>.MyIterator<K>() { // from class: org.jetbrains.kotlin.com.intellij.util.containers.UnmodifiableHashMap.2.1
                        {
                            UnmodifiableHashMap unmodifiableHashMap = UnmodifiableHashMap.this;
                        }

                        @Override // org.jetbrains.kotlin.com.intellij.util.containers.UnmodifiableHashMap.MyIterator
                        K fieldElement(int i) {
                            if (i == 0) {
                                return (K) UnmodifiableHashMap.this.k1;
                            }
                            return i == 1 ? (K) UnmodifiableHashMap.this.k2 : (K) UnmodifiableHashMap.this.k3;
                        }

                        @Override // org.jetbrains.kotlin.com.intellij.util.containers.UnmodifiableHashMap.MyIterator
                        K tableElement(int i) {
                            return (K) UnmodifiableHashMap.this.data[i];
                        }
                    };
                }

                @Override // java.lang.Iterable
                public void forEach(Consumer<? super K> consumer) {
                    if (UnmodifiableHashMap.this.k1 != null) {
                        if (UnmodifiableHashMap.this.k2 != null) {
                            if (UnmodifiableHashMap.this.k3 != null) {
                                consumer.accept((Object) UnmodifiableHashMap.this.k3);
                            }
                            consumer.accept((Object) UnmodifiableHashMap.this.k2);
                        }
                        consumer.accept((Object) UnmodifiableHashMap.this.k1);
                    }
                    for (int i = 0; i < UnmodifiableHashMap.this.data.length; i += 2) {
                        if (UnmodifiableHashMap.this.data[i] != null) {
                            consumer.accept(UnmodifiableHashMap.this.data[i]);
                        }
                    }
                }

                @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
                public boolean contains(Object obj) {
                    return UnmodifiableHashMap.this.containsKey(obj);
                }

                @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
                public int size() {
                    return UnmodifiableHashMap.this.size();
                }
            };
        }
        Set<K> set = this.keySet;
        if (set == null) {
            $$$reportNull$$$0(14);
        }
        return set;
    }

    @Override // java.util.Map
    public Collection<V> values() {
        if (this.values == null) {
            this.values = new AbstractCollection<V>() { // from class: org.jetbrains.kotlin.com.intellij.util.containers.UnmodifiableHashMap.3
                @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
                public Iterator<V> iterator() {
                    return new UnmodifiableHashMap<K, V>.MyIterator<V>() { // from class: org.jetbrains.kotlin.com.intellij.util.containers.UnmodifiableHashMap.3.1
                        {
                            UnmodifiableHashMap unmodifiableHashMap = UnmodifiableHashMap.this;
                        }

                        @Override // org.jetbrains.kotlin.com.intellij.util.containers.UnmodifiableHashMap.MyIterator
                        V fieldElement(int i) {
                            if (i == 0) {
                                return (V) UnmodifiableHashMap.this.v1;
                            }
                            return i == 1 ? (V) UnmodifiableHashMap.this.v2 : (V) UnmodifiableHashMap.this.v3;
                        }

                        @Override // org.jetbrains.kotlin.com.intellij.util.containers.UnmodifiableHashMap.MyIterator
                        V tableElement(int i) {
                            return (V) UnmodifiableHashMap.this.data[i + 1];
                        }
                    };
                }

                @Override // java.lang.Iterable
                public void forEach(Consumer<? super V> consumer) {
                    if (UnmodifiableHashMap.this.k1 != null) {
                        if (UnmodifiableHashMap.this.k2 != null) {
                            if (UnmodifiableHashMap.this.k3 != null) {
                                consumer.accept((Object) UnmodifiableHashMap.this.v3);
                            }
                            consumer.accept((Object) UnmodifiableHashMap.this.v2);
                        }
                        consumer.accept((Object) UnmodifiableHashMap.this.v1);
                    }
                    for (int i = 0; i < UnmodifiableHashMap.this.data.length; i += 2) {
                        if (UnmodifiableHashMap.this.data[i] != null) {
                            consumer.accept(UnmodifiableHashMap.this.data[i + 1]);
                        }
                    }
                }

                @Override // java.util.AbstractCollection, java.util.Collection
                public boolean contains(Object obj) {
                    return UnmodifiableHashMap.this.containsValue(obj);
                }

                @Override // java.util.AbstractCollection, java.util.Collection
                public int size() {
                    return UnmodifiableHashMap.this.size();
                }
            };
        }
        Collection<V> collection = this.values;
        if (collection == null) {
            $$$reportNull$$$0(15);
        }
        return collection;
    }

    @Override // java.util.Map
    public Set<Map.Entry<K, V>> entrySet() {
        return new AbstractSet<Map.Entry<K, V>>() { // from class: org.jetbrains.kotlin.com.intellij.util.containers.UnmodifiableHashMap.4
            @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
            public Iterator<Map.Entry<K, V>> iterator() {
                return new UnmodifiableHashMap<K, V>.MyIterator<Map.Entry<K, V>>() { // from class: org.jetbrains.kotlin.com.intellij.util.containers.UnmodifiableHashMap.4.1
                    {
                        UnmodifiableHashMap unmodifiableHashMap = UnmodifiableHashMap.this;
                    }

                    /* JADX INFO: Access modifiers changed from: package-private */
                    @Override // org.jetbrains.kotlin.com.intellij.util.containers.UnmodifiableHashMap.MyIterator
                    public Map.Entry<K, V> fieldElement(int i) {
                        if (i == 0) {
                            return new AbstractMap.SimpleImmutableEntry(UnmodifiableHashMap.this.k1, UnmodifiableHashMap.this.v1);
                        }
                        if (i == 1) {
                            return new AbstractMap.SimpleImmutableEntry(UnmodifiableHashMap.this.k2, UnmodifiableHashMap.this.v2);
                        }
                        return new AbstractMap.SimpleImmutableEntry(UnmodifiableHashMap.this.k3, UnmodifiableHashMap.this.v3);
                    }

                    /* JADX INFO: Access modifiers changed from: package-private */
                    @Override // org.jetbrains.kotlin.com.intellij.util.containers.UnmodifiableHashMap.MyIterator
                    public Map.Entry<K, V> tableElement(int i) {
                        return new AbstractMap.SimpleImmutableEntry(UnmodifiableHashMap.this.data[i], UnmodifiableHashMap.this.data[i + 1]);
                    }
                };
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public int size() {
                return UnmodifiableHashMap.this.size();
            }
        };
    }
}
