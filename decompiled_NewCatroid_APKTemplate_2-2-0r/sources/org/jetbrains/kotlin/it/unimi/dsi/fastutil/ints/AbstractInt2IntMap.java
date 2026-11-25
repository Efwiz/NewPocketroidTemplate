package org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints;

import com.google.android.gms.cast.MediaError;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Size64;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectIterator;

/* JADX INFO: loaded from: classes5.dex */
public abstract class AbstractInt2IntMap extends AbstractInt2IntFunction implements Int2IntMap, Serializable {
    private static final long serialVersionUID = -4940583368468432370L;

    protected AbstractInt2IntMap() {
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2IntFunction
    public boolean containsKey(int i) {
        ObjectIterator<Int2IntMap.Entry> it = int2IntEntrySet().iterator();
        while (it.hasNext()) {
            if (it.next().getIntKey() == i) {
                return true;
            }
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2IntMap
    public boolean containsValue(int i) {
        ObjectIterator<Int2IntMap.Entry> it = int2IntEntrySet().iterator();
        while (it.hasNext()) {
            if (it.next().getIntValue() == i) {
                return true;
            }
        }
        return false;
    }

    @Override // java.util.Map
    public boolean isEmpty() {
        return size() == 0;
    }

    public static class BasicEntry implements Int2IntMap.Entry {
        protected int key;
        protected int value;

        public BasicEntry() {
        }

        public BasicEntry(int i, int i2) {
            this.key = i;
            this.value = i2;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2IntMap.Entry
        public int getIntKey() {
            return this.key;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2IntMap.Entry
        public int getIntValue() {
            return this.value;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2IntMap.Entry
        public int setValue(int i) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Map.Entry
        public boolean equals(Object obj) {
            Object value;
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            if (obj instanceof Int2IntMap.Entry) {
                Int2IntMap.Entry entry = (Int2IntMap.Entry) obj;
                return this.key == entry.getIntKey() && this.value == entry.getIntValue();
            }
            Map.Entry entry2 = (Map.Entry) obj;
            Object key = entry2.getKey();
            return key != null && (key instanceof Integer) && (value = entry2.getValue()) != null && (value instanceof Integer) && this.key == ((Integer) key).intValue() && this.value == ((Integer) value).intValue();
        }

        @Override // java.util.Map.Entry
        public int hashCode() {
            return this.key ^ this.value;
        }

        public String toString() {
            return this.key + "->" + this.value;
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2IntMap, java.util.Map
    public Set<Integer> keySet() {
        return new AbstractIntSet() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractInt2IntMap.1
            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection
            public boolean contains(int i) {
                return AbstractInt2IntMap.this.containsKey(i);
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public int size() {
                return AbstractInt2IntMap.this.size();
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public void clear() {
                AbstractInt2IntMap.this.clear();
            }

            /* JADX INFO: renamed from: org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractInt2IntMap$1$1, reason: invalid class name and collision with other inner class name */
            class C03181 implements IntIterator {
                private final ObjectIterator<Int2IntMap.Entry> i;

                C03181() {
                    this.i = Int2IntMaps.fastIterator(AbstractInt2IntMap.this);
                }

                @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterator, java.util.PrimitiveIterator.OfInt
                public int nextInt() {
                    return this.i.next().getIntKey();
                }

                @Override // java.util.Iterator
                public boolean hasNext() {
                    return this.i.hasNext();
                }

                @Override // java.util.Iterator
                public void remove() {
                    this.i.remove();
                }

                @Override // java.util.PrimitiveIterator
                public void forEachRemaining(final java.util.function.IntConsumer intConsumer) {
                    this.i.forEachRemaining(new Consumer() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractInt2IntMap$1$1$$ExternalSyntheticLambda0
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            intConsumer.accept(((Int2IntMap.Entry) obj).getIntKey());
                        }
                    });
                }
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntSet, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSet, java.util.Set
            public IntIterator iterator() {
                return new C03181();
            }

            @Override // java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSet, java.util.Set
            public IntSpliterator spliterator() {
                return IntSpliterators.asSpliterator(iterator(), Size64.sizeOf(AbstractInt2IntMap.this), MediaError.DetailedErrorCode.DASH_NETWORK);
            }
        };
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2IntMap, java.util.Map
    public Collection<Integer> values() {
        return new AbstractIntCollection() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractInt2IntMap.2
            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection
            public boolean contains(int i) {
                return AbstractInt2IntMap.this.containsValue(i);
            }

            @Override // java.util.AbstractCollection, java.util.Collection
            public int size() {
                return AbstractInt2IntMap.this.size();
            }

            @Override // java.util.AbstractCollection, java.util.Collection
            public void clear() {
                AbstractInt2IntMap.this.clear();
            }

            /* JADX INFO: renamed from: org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractInt2IntMap$2$1, reason: invalid class name */
            class AnonymousClass1 implements IntIterator {
                private final ObjectIterator<Int2IntMap.Entry> i;

                AnonymousClass1() {
                    this.i = Int2IntMaps.fastIterator(AbstractInt2IntMap.this);
                }

                @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterator, java.util.PrimitiveIterator.OfInt
                public int nextInt() {
                    return this.i.next().getIntValue();
                }

                @Override // java.util.Iterator
                public boolean hasNext() {
                    return this.i.hasNext();
                }

                @Override // java.util.Iterator
                public void remove() {
                    this.i.remove();
                }

                @Override // java.util.PrimitiveIterator
                public void forEachRemaining(final java.util.function.IntConsumer intConsumer) {
                    this.i.forEachRemaining(new Consumer() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractInt2IntMap$2$1$$ExternalSyntheticLambda0
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            intConsumer.accept(((Int2IntMap.Entry) obj).getIntValue());
                        }
                    });
                }
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSet, java.util.Set
            public IntIterator iterator() {
                return new AnonymousClass1();
            }

            @Override // java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSet, java.util.Set
            public IntSpliterator spliterator() {
                return IntSpliterators.asSpliterator(iterator(), Size64.sizeOf(AbstractInt2IntMap.this), 320);
            }
        };
    }

    @Override // java.util.Map
    public void putAll(Map<? extends Integer, ? extends Integer> map) {
        if (map instanceof Int2IntMap) {
            ObjectIterator<Int2IntMap.Entry> objectIteratorFastIterator = Int2IntMaps.fastIterator((Int2IntMap) map);
            while (objectIteratorFastIterator.hasNext()) {
                Int2IntMap.Entry next = objectIteratorFastIterator.next();
                put(next.getIntKey(), next.getIntValue());
            }
            return;
        }
        int size = map.size();
        Iterator<Map.Entry<? extends Integer, ? extends Integer>> it = map.entrySet().iterator();
        while (true) {
            int i = size - 1;
            if (size == 0) {
                return;
            }
            Map.Entry<? extends Integer, ? extends Integer> next2 = it.next();
            put(next2.getKey(), next2.getValue());
            size = i;
        }
    }

    @Override // java.util.Map
    public int hashCode() {
        int size = size();
        ObjectIterator<Int2IntMap.Entry> objectIteratorFastIterator = Int2IntMaps.fastIterator(this);
        int iHashCode = 0;
        while (true) {
            int i = size - 1;
            if (size == 0) {
                return iHashCode;
            }
            iHashCode += objectIteratorFastIterator.next().hashCode();
            size = i;
        }
    }

    @Override // java.util.Map
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Map)) {
            return false;
        }
        Map map = (Map) obj;
        if (map.size() != size()) {
            return false;
        }
        return int2IntEntrySet().containsAll(map.entrySet());
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        ObjectIterator<Int2IntMap.Entry> objectIteratorFastIterator = Int2IntMaps.fastIterator(this);
        int size = size();
        boolean z = true;
        while (true) {
            int i = size - 1;
            if (size != 0) {
                if (z) {
                    z = false;
                } else {
                    sb.append(", ");
                }
                Int2IntMap.Entry next = objectIteratorFastIterator.next();
                sb.append(String.valueOf(next.getIntKey()));
                sb.append("=>");
                sb.append(String.valueOf(next.getIntValue()));
                size = i;
            } else {
                sb.append("}");
                return sb.toString();
            }
        }
    }
}
