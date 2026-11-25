package org.jetbrains.kotlin.com.intellij.util.containers;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.reandroid.arsc.value.CompoundEntry;
import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/* JADX INFO: loaded from: classes3.dex */
public class MultiMap<K, V> implements Serializable {
    private static final MultiMap<?, ?> EMPTY = new EmptyMap(null);
    private static final long serialVersionUID = -2632269270151455493L;
    protected final Map<K, Collection<V>> myMap;
    private Collection<V> values;

    /* JADX WARN: Code duplicated, block: B:8:0x0010  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 2 && i != 4 && i != 16) {
            switch (i) {
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 2 && i != 4 && i != 16) {
            switch (i) {
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                    i2 = 2;
                    break;
                default:
                    i2 = 3;
                    break;
            }
        } else {
            i2 = 2;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
                objArr[0] = "toCopy";
                break;
            case 2:
            case 4:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 16:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/util/containers/MultiMap";
                break;
            case 3:
            case 5:
                objArr[0] = TypedValues.TransitionType.S_FROM;
                break;
            case 6:
                objArr[0] = CompoundEntry.NAME_values;
                break;
            case 15:
            default:
                objArr[0] = "map";
                break;
        }
        if (i == 2) {
            objArr[1] = "createEmptyCollection";
        } else if (i == 4) {
            objArr[1] = "toHashMap";
        } else if (i != 16) {
            switch (i) {
                case 7:
                    objArr[1] = "entrySet";
                    break;
                case 8:
                case 9:
                    objArr[1] = "freezeValues";
                    break;
                case 10:
                    objArr[1] = "get";
                    break;
                case 11:
                    objArr[1] = "getModifiable";
                    break;
                case 12:
                    objArr[1] = "keySet";
                    break;
                case 13:
                case 14:
                    objArr[1] = CompoundEntry.NAME_values;
                    break;
                default:
                    objArr[1] = "org/jetbrains/kotlin/com/intellij/util/containers/MultiMap";
                    break;
            }
        } else {
            objArr[1] = "empty";
        }
        switch (i) {
            case 2:
            case 4:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 16:
                break;
            case 3:
            case 5:
                objArr[2] = "putAllValues";
                break;
            case 6:
                objArr[2] = "putValues";
                break;
            case 15:
                objArr[2] = "createSet";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 4 && i != 16) {
            switch (i) {
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                    break;
                default:
                    throw new IllegalArgumentException(str2);
            }
        }
        throw new IllegalStateException(str2);
    }

    public MultiMap() {
        this.myMap = new HashMap();
    }

    public MultiMap(Map<K, Collection<V>> map) {
        if (map == null) {
            $$$reportNull$$$0(0);
        }
        this.myMap = map;
    }

    protected Collection<V> createCollection() {
        return new ArrayList();
    }

    protected Collection<V> createEmptyCollection() {
        List listEmptyList = Collections.emptyList();
        if (listEmptyList == null) {
            $$$reportNull$$$0(2);
        }
        return listEmptyList;
    }

    /* JADX INFO: renamed from: lambda$putValues$0$org-jetbrains-kotlin-com-intellij-util-containers-MultiMap, reason: not valid java name */
    /* synthetic */ Collection m6714x54c21d15(Object obj) {
        return createCollection();
    }

    public final void putValues(K k, Collection<? extends V> collection) {
        if (collection == null) {
            $$$reportNull$$$0(6);
        }
        this.myMap.computeIfAbsent(k, new Function() { // from class: org.jetbrains.kotlin.com.intellij.util.containers.MultiMap$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.m6714x54c21d15(obj);
            }
        }).addAll(collection);
    }

    /* JADX INFO: renamed from: lambda$putValue$1$org-jetbrains-kotlin-com-intellij-util-containers-MultiMap, reason: not valid java name */
    /* synthetic */ Collection m6713x34503ce9(Object obj) {
        return createCollection();
    }

    public final void putValue(K k, V v) {
        this.myMap.computeIfAbsent(k, new Function() { // from class: org.jetbrains.kotlin.com.intellij.util.containers.MultiMap$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.m6713x34503ce9(obj);
            }
        }).add(v);
    }

    public final Set<Map.Entry<K, Collection<V>>> entrySet() {
        Set<Map.Entry<K, Collection<V>>> setEntrySet = this.myMap.entrySet();
        if (setEntrySet == null) {
            $$$reportNull$$$0(7);
        }
        return setEntrySet;
    }

    public final Collection<V> get(K k) {
        Collection<V> collectionCreateEmptyCollection = this.myMap.get(k);
        if (collectionCreateEmptyCollection == null) {
            collectionCreateEmptyCollection = createEmptyCollection();
        }
        if (collectionCreateEmptyCollection == null) {
            $$$reportNull$$$0(10);
        }
        return collectionCreateEmptyCollection;
    }

    public final Set<K> keySet() {
        Set<K> setKeySet = this.myMap.keySet();
        if (setKeySet == null) {
            $$$reportNull$$$0(12);
        }
        return setKeySet;
    }

    public boolean remove(K k, V v) {
        Collection<V> collection = this.myMap.get(k);
        if (collection == null) {
            return false;
        }
        boolean zRemove = collection.remove(v);
        if (collection.isEmpty()) {
            this.myMap.remove(k);
        }
        return zRemove;
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.com.intellij.util.containers.MultiMap$1, reason: invalid class name */
    class AnonymousClass1 extends AbstractCollection<V> {
        final /* synthetic */ MultiMap this$0;

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
        public Iterator<V> iterator() {
            return new Iterator<V>() { // from class: org.jetbrains.kotlin.com.intellij.util.containers.MultiMap.1.1
                private Iterator<V> itr = Collections.emptyIterator();
                private final Iterator<Collection<V>> mapIterator;

                {
                    this.mapIterator = AnonymousClass1.this.this$0.myMap.values().iterator();
                }

                @Override // java.util.Iterator
                public boolean hasNext() {
                    while (!this.itr.hasNext()) {
                        if (!this.mapIterator.hasNext()) {
                            return false;
                        }
                        this.itr = this.mapIterator.next().iterator();
                    }
                    return true;
                }

                @Override // java.util.Iterator
                public V next() {
                    while (!this.itr.hasNext()) {
                        if (!this.mapIterator.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        this.itr = this.mapIterator.next().iterator();
                    }
                    return this.itr.next();
                }

                @Override // java.util.Iterator
                public void remove() {
                    this.itr.remove();
                }
            };
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public int size() {
            Iterator<Collection<V>> it = this.this$0.myMap.values().iterator();
            int size = 0;
            while (it.hasNext()) {
                size += it.next().size();
            }
            return size;
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean contains(Object obj) {
            Iterator<Collection<V>> it = this.this$0.myMap.values().iterator();
            while (it.hasNext()) {
                if (it.next().contains(obj)) {
                    return true;
                }
            }
            return false;
        }
    }

    public final void clear() {
        this.myMap.clear();
    }

    public static <K, V> MultiMap<K, V> create() {
        return new MultiMap<>();
    }

    public static <K, V> MultiMap<K, V> createLinked() {
        return new MultiMap<>(new LinkedHashMap());
    }

    public static <K, V> MultiMap<K, V> createLinkedSet() {
        return new MultiMap<K, V>(new LinkedHashMap()) { // from class: org.jetbrains.kotlin.com.intellij.util.containers.MultiMap.2
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/com/intellij/util/containers/MultiMap$2", "createEmptyCollection"));
            }

            @Override // org.jetbrains.kotlin.com.intellij.util.containers.MultiMap
            protected Collection<V> createCollection() {
                return new LinkedHashSet();
            }

            @Override // org.jetbrains.kotlin.com.intellij.util.containers.MultiMap
            protected Collection<V> createEmptyCollection() {
                Set setEmptySet = Collections.emptySet();
                if (setEmptySet == null) {
                    $$$reportNull$$$0(0);
                }
                return setEmptySet;
            }
        };
    }

    public static <K, V> MultiMap<K, V> createConcurrent() {
        return new MultiMap<K, V>(new ConcurrentHashMap()) { // from class: org.jetbrains.kotlin.com.intellij.util.containers.MultiMap.4
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/com/intellij/util/containers/MultiMap$4", "createCollection"));
            }

            @Override // org.jetbrains.kotlin.com.intellij.util.containers.MultiMap
            protected Collection<V> createCollection() {
                List listCreateLockFreeCopyOnWriteList = ContainerUtil.createLockFreeCopyOnWriteList();
                if (listCreateLockFreeCopyOnWriteList == null) {
                    $$$reportNull$$$0(0);
                }
                return listCreateLockFreeCopyOnWriteList;
            }
        };
    }

    public static <K, V> MultiMap<K, V> createSet() {
        return new MultiMap<K, V>() { // from class: org.jetbrains.kotlin.com.intellij.util.containers.MultiMap.6
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/com/intellij/util/containers/MultiMap$6", "createEmptyCollection"));
            }

            @Override // org.jetbrains.kotlin.com.intellij.util.containers.MultiMap
            protected Collection<V> createCollection() {
                return new HashSet();
            }

            @Override // org.jetbrains.kotlin.com.intellij.util.containers.MultiMap
            protected Collection<V> createEmptyCollection() {
                Set setEmptySet = Collections.emptySet();
                if (setEmptySet == null) {
                    $$$reportNull$$$0(0);
                }
                return setEmptySet;
            }
        };
    }

    public final boolean equals(Object obj) {
        return this == obj || ((obj instanceof MultiMap) && this.myMap.equals(((MultiMap) obj).myMap));
    }

    public final int hashCode() {
        return this.myMap.hashCode();
    }

    public final String toString() {
        return this.myMap.toString();
    }

    private static final class EmptyMap extends MultiMap<Object, Object> {
        /* synthetic */ EmptyMap(AnonymousClass1 anonymousClass1) {
            this();
        }

        private EmptyMap() {
            super(Collections.emptyMap());
        }
    }
}
