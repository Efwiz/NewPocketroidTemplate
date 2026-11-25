package com.crashinvaders.vfx.utils;

import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.Pool;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/* JADX INFO: loaded from: classes8.dex */
public class PrioritizedArray<T> implements Iterable<T> {
    private final WrapperComparator<T> comparator;
    private final ValueArrayMap<T, Wrapper<T>> items;
    private PrioritizedArrayIterable<T> iterable;

    public PrioritizedArray() {
        this.comparator = new WrapperComparator<>();
        this.items = new ValueArrayMap<>();
    }

    public PrioritizedArray(int i) {
        this.comparator = new WrapperComparator<>();
        this.items = new ValueArrayMap<>(i);
    }

    public T get(int i) {
        return this.items.getValueAt(i).item;
    }

    public void add(T t) {
        add(t, 0);
    }

    public void add(T t, int i) {
        this.items.put(t, ((Wrapper) Wrapper.pool.obtain()).initialize(t, i));
        this.items.sort(this.comparator);
    }

    public void remove(int i) {
        remove(this.items.getValueAt(i).item);
    }

    public void remove(T t) {
        Wrapper<T> wrapperRemove = this.items.remove(t);
        if (wrapperRemove != null) {
            Wrapper.pool.free(wrapperRemove);
        }
    }

    public boolean contains(T t) {
        return this.items.contains(t);
    }

    public void clear() {
        for (int i = 0; i < this.items.size(); i++) {
            Wrapper.pool.free(this.items.getValueAt(i));
        }
        this.items.clear();
    }

    public int size() {
        return this.items.size();
    }

    public void setPriority(T t, int i) {
        this.items.get(t).priority = i;
        this.items.sort(this.comparator);
    }

    @Override // java.lang.Iterable
    public Iterator<T> iterator() {
        if (this.iterable == null) {
            this.iterable = new PrioritizedArrayIterable<>(this);
        }
        return this.iterable.iterator();
    }

    public String toString() {
        return this.items.toString();
    }

    public String toString(String str) {
        return this.items.toString(str);
    }

    private static class Wrapper<T> implements Pool.Poolable {
        private static final Pool<Wrapper> pool = new Pool<Wrapper>() { // from class: com.crashinvaders.vfx.utils.PrioritizedArray.Wrapper.1
            /* JADX INFO: Access modifiers changed from: protected */
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.badlogic.gdx.utils.Pool
            public Wrapper newObject() {
                return new Wrapper();
            }
        };
        T item;
        int priority;

        private Wrapper() {
        }

        public Wrapper initialize(T t, int i) {
            this.item = t;
            this.priority = i;
            return this;
        }

        @Override // com.badlogic.gdx.utils.Pool.Poolable
        public void reset() {
            this.item = null;
            this.priority = 0;
        }

        public String toString() {
            return this.item + "[" + this.priority + "]";
        }
    }

    private static class WrapperComparator<T> implements Comparator<Wrapper<T>> {
        private WrapperComparator() {
        }

        @Override // java.util.Comparator
        public int compare(Wrapper wrapper, Wrapper wrapper2) {
            return CommonUtils.compare(wrapper.priority, wrapper2.priority);
        }
    }

    public static class PrioritizedArrayIterator<T> implements Iterator<T>, Iterable<T> {
        private final boolean allowRemove;
        private final PrioritizedArray<T> array;
        int index;
        boolean valid;

        @Override // java.lang.Iterable
        public Iterator<T> iterator() {
            return this;
        }

        public PrioritizedArrayIterator(PrioritizedArray<T> prioritizedArray) {
            this(prioritizedArray, true);
        }

        public PrioritizedArrayIterator(PrioritizedArray<T> prioritizedArray, boolean z) {
            this.valid = true;
            this.array = prioritizedArray;
            this.allowRemove = z;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            if (this.valid) {
                return this.index < this.array.size();
            }
            throw new GdxRuntimeException("#iterator() cannot be used nested.");
        }

        @Override // java.util.Iterator
        public T next() {
            if (this.index >= this.array.size()) {
                throw new NoSuchElementException(String.valueOf(this.index));
            }
            if (this.valid) {
                ValueArrayMap valueArrayMap = ((PrioritizedArray) this.array).items;
                int i = this.index;
                this.index = i + 1;
                return ((Wrapper) valueArrayMap.getValueAt(i)).item;
            }
            throw new GdxRuntimeException("#iterator() cannot be used nested.");
        }

        @Override // java.util.Iterator
        public void remove() {
            if (!this.allowRemove) {
                throw new GdxRuntimeException("Remove not allowed.");
            }
            int i = this.index - 1;
            this.index = i;
            this.array.remove(i);
        }

        public void reset() {
            this.index = 0;
        }
    }

    public static class PrioritizedArrayIterable<T> implements Iterable<T> {
        private final boolean allowRemove;
        private final PrioritizedArray<T> array;
        private PrioritizedArrayIterator<T> iterator1;
        private PrioritizedArrayIterator<T> iterator2;

        public PrioritizedArrayIterable(PrioritizedArray<T> prioritizedArray) {
            this(prioritizedArray, true);
        }

        public PrioritizedArrayIterable(PrioritizedArray<T> prioritizedArray, boolean z) {
            this.array = prioritizedArray;
            this.allowRemove = z;
        }

        @Override // java.lang.Iterable
        public Iterator<T> iterator() {
            if (this.iterator1 == null) {
                this.iterator1 = new PrioritizedArrayIterator<>(this.array, this.allowRemove);
                this.iterator2 = new PrioritizedArrayIterator<>(this.array, this.allowRemove);
            }
            if (!this.iterator1.valid) {
                this.iterator1.index = 0;
                this.iterator1.valid = true;
                this.iterator2.valid = false;
                return this.iterator1;
            }
            this.iterator2.index = 0;
            this.iterator2.valid = true;
            this.iterator1.valid = false;
            return this.iterator2;
        }
    }
}
