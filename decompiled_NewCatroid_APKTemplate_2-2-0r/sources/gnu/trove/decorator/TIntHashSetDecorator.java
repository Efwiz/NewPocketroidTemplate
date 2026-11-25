package gnu.trove.decorator;

import gnu.trove.TIntHashSet;
import gnu.trove.TIntIterator;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Set;

/* JADX INFO: loaded from: classes11.dex */
public class TIntHashSetDecorator extends AbstractSet<Integer> implements Set<Integer> {
    protected final TIntHashSet _set;

    public TIntHashSetDecorator(TIntHashSet tIntHashSet) {
        this._set = tIntHashSet;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean add(Integer num) {
        return this._set.add(unwrap(num));
    }

    @Override // java.util.AbstractSet, java.util.Collection, java.util.Set
    public boolean equals(Object obj) {
        if (this._set.equals(obj)) {
            return true;
        }
        if (!(obj instanceof Set)) {
            return false;
        }
        Set set = (Set) obj;
        if (set.size() != this._set.size()) {
            return false;
        }
        Iterator it = set.iterator();
        int size = set.size();
        while (true) {
            int i = size - 1;
            if (size <= 0) {
                return true;
            }
            Object next = it.next();
            if (!(next instanceof Integer)) {
                break;
            }
            if (!this._set.contains(unwrap(next))) {
                break;
            }
            size = i;
        }
        return false;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public void clear() {
        this._set.clear();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean remove(Object obj) {
        return this._set.remove(unwrap(obj));
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
    public Iterator<Integer> iterator() {
        return new Iterator<Integer>() { // from class: gnu.trove.decorator.TIntHashSetDecorator.1
            private final TIntIterator it;

            {
                this.it = TIntHashSetDecorator.this._set.iterator();
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.util.Iterator
            public Integer next() {
                return TIntHashSetDecorator.this.wrap(this.it.next());
            }

            @Override // java.util.Iterator
            public boolean hasNext() {
                return this.it.hasNext();
            }

            @Override // java.util.Iterator
            public void remove() {
                this.it.remove();
            }
        };
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public int size() {
        return this._set.size();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean isEmpty() {
        return size() == 0;
    }

    protected Integer wrap(int i) {
        return new Integer(i);
    }

    protected int unwrap(Object obj) {
        return ((Integer) obj).intValue();
    }
}
