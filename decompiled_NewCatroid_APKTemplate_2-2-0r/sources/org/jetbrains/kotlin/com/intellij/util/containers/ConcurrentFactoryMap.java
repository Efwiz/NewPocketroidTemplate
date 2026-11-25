package org.jetbrains.kotlin.com.intellij.util.containers;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Supplier;
import org.antlr.stringtemplate.language.ASTExpr;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.com.intellij.openapi.util.RecursionGuard;
import org.jetbrains.kotlin.com.intellij.openapi.util.RecursionManager;
import org.jetbrains.kotlin.com.intellij.util.ConcurrencyUtil;
import org.jetbrains.kotlin.com.intellij.util.Function;
import org.jetbrains.kotlin.com.intellij.util.ObjectUtils;

/* JADX INFO: loaded from: classes3.dex */
public abstract class ConcurrentFactoryMap<K, V> implements ConcurrentMap<K, V> {
    private final ConcurrentMap<K, V> myMap;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 6:
                objArr[0] = ASTExpr.DEFAULT_MAP_KEY_NAME;
                break;
            case 4:
                objArr[0] = "oldValue";
                break;
            case 5:
                objArr[0] = "newValue";
                break;
            case 7:
                objArr[0] = "value";
                break;
            case 8:
            case 9:
                objArr[0] = "computeValue";
                break;
            case 10:
                objArr[0] = "mapCreator";
                break;
            case 11:
                objArr[0] = "compute";
                break;
            default:
                objArr[0] = "m";
                break;
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/util/containers/ConcurrentFactoryMap";
        switch (i) {
            case 1:
                objArr[2] = "putIfAbsent";
                break;
            case 2:
                objArr[2] = ProductAction.ACTION_REMOVE;
                break;
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                objArr[2] = SchemaSymbols.ATTVAL_REPLACE;
                break;
            case 8:
                objArr[2] = "createMap";
                break;
            case 9:
            case 10:
                objArr[2] = CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME;
                break;
            case 11:
                objArr[2] = "createWeakMap";
                break;
            default:
                objArr[2] = "putAll";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    protected abstract V create(K k);

    private ConcurrentFactoryMap() {
        this.myMap = createMap();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.Map
    public V get(Object obj) {
        ConcurrentMap<K, V> concurrentMap = this.myMap;
        Object objNotNull = notNull(obj);
        Object objCacheOrGet = concurrentMap.get(objNotNull);
        if (objCacheOrGet == null) {
            RecursionGuard.StackStamp stackStampMarkStack = RecursionManager.markStack();
            V vCreate = create(obj);
            objCacheOrGet = stackStampMarkStack.mayCacheNow() ? ConcurrencyUtil.cacheOrGet(concurrentMap, objNotNull, notNull(vCreate)) : vCreate;
        }
        return (V) nullize(objCacheOrGet);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <T> T nullize(T t) {
        if (t == FAKE_NULL()) {
            return null;
        }
        return t;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <T> T FAKE_NULL() {
        return (T) ObjectUtils.NULL;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public static <T> T notNull(Object obj) {
        return obj == 0 ? (T) FAKE_NULL() : obj;
    }

    @Override // java.util.Map
    public final boolean containsKey(Object obj) {
        return this.myMap.containsKey(notNull(obj));
    }

    @Override // java.util.Map
    public V put(K k, V v) {
        return (V) nullize(this.myMap.put((K) notNull(k), (V) notNull(v)));
    }

    @Override // java.util.Map
    public V remove(Object obj) {
        return (V) nullize(this.myMap.remove(notNull(obj)));
    }

    @Override // java.util.Map
    public Set<K> keySet() {
        return new CollectionWrapper.Set(this.myMap.keySet());
    }

    @Override // java.util.Map
    public void clear() {
        this.myMap.clear();
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
    public boolean containsValue(Object obj) {
        return this.myMap.containsValue(notNull(obj));
    }

    @Override // java.util.Map
    public void putAll(Map<? extends K, ? extends V> map) {
        if (map == null) {
            $$$reportNull$$$0(0);
        }
        for (Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override // java.util.Map
    public Collection<V> values() {
        return new CollectionWrapper(this.myMap.values());
    }

    @Override // java.util.Map
    public Set<Map.Entry<K, V>> entrySet() {
        return new CollectionWrapper.Set<Map.Entry<K, V>>(this.myMap.entrySet()) { // from class: org.jetbrains.kotlin.com.intellij.util.containers.ConcurrentFactoryMap.1
            @Override // org.jetbrains.kotlin.com.intellij.util.containers.ConcurrentFactoryMap.CollectionWrapper
            public Object wrap(Object obj) {
                return obj instanceof CollectionWrapper.EntryWrapper ? ((CollectionWrapper.EntryWrapper) obj).myEntry : obj;
            }

            @Override // org.jetbrains.kotlin.com.intellij.util.containers.ConcurrentFactoryMap.CollectionWrapper
            public Map.Entry<K, V> unwrap(Map.Entry<K, V> entry) {
                return (entry.getKey() == ConcurrentFactoryMap.FAKE_NULL() || entry.getValue() == ConcurrentFactoryMap.FAKE_NULL()) ? new CollectionWrapper.EntryWrapper(entry) : entry;
            }
        };
    }

    protected ConcurrentMap<K, V> createMap() {
        return new ConcurrentHashMap();
    }

    @Override // java.util.concurrent.ConcurrentMap, java.util.Map
    public V putIfAbsent(K k, V v) {
        if (k == null) {
            $$$reportNull$$$0(1);
        }
        return (V) nullize(this.myMap.putIfAbsent((K) notNull(k), (V) notNull(v)));
    }

    @Override // java.util.concurrent.ConcurrentMap, java.util.Map
    public boolean remove(Object obj, Object obj2) {
        if (obj == null) {
            $$$reportNull$$$0(2);
        }
        return this.myMap.remove(notNull(obj), notNull(obj2));
    }

    @Override // java.util.concurrent.ConcurrentMap, java.util.Map
    public boolean replace(K k, V v, V v2) {
        if (k == null) {
            $$$reportNull$$$0(3);
        }
        if (v == null) {
            $$$reportNull$$$0(4);
        }
        if (v2 == null) {
            $$$reportNull$$$0(5);
        }
        return this.myMap.replace((K) notNull(k), (V) notNull(v), (V) notNull(v2));
    }

    @Override // java.util.concurrent.ConcurrentMap, java.util.Map
    public V replace(K k, V v) {
        if (k == null) {
            $$$reportNull$$$0(6);
        }
        if (v == null) {
            $$$reportNull$$$0(7);
        }
        return (V) nullize(this.myMap.replace((K) notNull(k), (V) notNull(v)));
    }

    public String toString() {
        return this.myMap.toString();
    }

    public static <T, V> ConcurrentMap<T, V> createMap(final Function<? super T, ? extends V> function) {
        if (function == null) {
            $$$reportNull$$$0(8);
        }
        return new ConcurrentFactoryMap<T, V>() { // from class: org.jetbrains.kotlin.com.intellij.util.containers.ConcurrentFactoryMap.2
            {
                super();
            }

            @Override // org.jetbrains.kotlin.com.intellij.util.containers.ConcurrentFactoryMap
            protected V create(T t) {
                return (V) function.fun(t);
            }
        };
    }

    public static <K, V> ConcurrentMap<K, V> create(final Function<? super K, ? extends V> function, final Supplier<? extends ConcurrentMap<K, V>> supplier) {
        if (function == null) {
            $$$reportNull$$$0(9);
        }
        if (supplier == null) {
            $$$reportNull$$$0(10);
        }
        return new ConcurrentFactoryMap<K, V>() { // from class: org.jetbrains.kotlin.com.intellij.util.containers.ConcurrentFactoryMap.3
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/com/intellij/util/containers/ConcurrentFactoryMap$3", "createMap"));
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            @Override // org.jetbrains.kotlin.com.intellij.util.containers.ConcurrentFactoryMap
            protected V create(K k) {
                return (V) function.fun(k);
            }

            @Override // org.jetbrains.kotlin.com.intellij.util.containers.ConcurrentFactoryMap
            protected ConcurrentMap<K, V> createMap() {
                ConcurrentMap<K, V> concurrentMap = (ConcurrentMap) supplier.get();
                if (concurrentMap == null) {
                    $$$reportNull$$$0(0);
                }
                return concurrentMap;
            }
        };
    }

    public static <T, V> ConcurrentMap<T, V> createWeakMap(Function<? super T, ? extends V> function) {
        if (function == null) {
            $$$reportNull$$$0(11);
        }
        return create(function, new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.util.containers.ConcurrentFactoryMap$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return CollectionFactory.createConcurrentWeakMap();
            }
        });
    }

    private static class CollectionWrapper<K> extends AbstractCollection<K> {
        private final Collection<K> myDelegate;

        CollectionWrapper(Collection<K> collection) {
            this.myDelegate = collection;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
        public Iterator<K> iterator() {
            return new Iterator<K>() { // from class: org.jetbrains.kotlin.com.intellij.util.containers.ConcurrentFactoryMap.CollectionWrapper.1
                final Iterator<K> it;

                {
                    this.it = CollectionWrapper.this.myDelegate.iterator();
                }

                @Override // java.util.Iterator
                public boolean hasNext() {
                    return this.it.hasNext();
                }

                @Override // java.util.Iterator
                public K next() {
                    return (K) CollectionWrapper.this.unwrap(this.it.next());
                }

                @Override // java.util.Iterator
                public void remove() {
                    this.it.remove();
                }
            };
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public int size() {
            return this.myDelegate.size();
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean contains(Object obj) {
            return this.myDelegate.contains(wrap(obj));
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean remove(Object obj) {
            return this.myDelegate.remove(wrap(obj));
        }

        protected Object wrap(Object obj) {
            return ConcurrentFactoryMap.notNull(obj);
        }

        protected K unwrap(K k) {
            return (K) ConcurrentFactoryMap.nullize(k);
        }

        private static class Set<K> extends CollectionWrapper<K> implements java.util.Set<K> {
            Set(Collection<K> collection) {
                super(collection);
            }
        }

        protected static final class EntryWrapper<K, V> implements Map.Entry<K, V> {
            final Map.Entry<? extends K, ? extends V> myEntry;

            private EntryWrapper(Map.Entry<? extends K, ? extends V> entry) {
                this.myEntry = entry;
            }

            @Override // java.util.Map.Entry
            public K getKey() {
                return (K) ConcurrentFactoryMap.nullize(this.myEntry.getKey());
            }

            @Override // java.util.Map.Entry
            public V getValue() {
                return (V) ConcurrentFactoryMap.nullize(this.myEntry.getValue());
            }

            @Override // java.util.Map.Entry
            public V setValue(V v) {
                return this.myEntry.setValue((Object) ConcurrentFactoryMap.notNull(v));
            }

            @Override // java.util.Map.Entry
            public int hashCode() {
                return this.myEntry.hashCode();
            }

            @Override // java.util.Map.Entry
            public boolean equals(Object obj) {
                Map.Entry<? extends K, ? extends V> entry = this.myEntry;
                if (obj instanceof EntryWrapper) {
                    obj = ((EntryWrapper) obj).myEntry;
                }
                return entry.equals(obj);
            }
        }
    }
}
