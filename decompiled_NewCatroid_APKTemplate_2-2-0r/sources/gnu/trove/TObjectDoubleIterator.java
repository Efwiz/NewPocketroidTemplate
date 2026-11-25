package gnu.trove;

import java.util.ConcurrentModificationException;

/* JADX INFO: loaded from: classes11.dex */
public class TObjectDoubleIterator<K> extends TIterator {
    private final TObjectDoubleHashMap<K> _map;

    @Override // gnu.trove.TIterator
    public /* bridge */ /* synthetic */ boolean hasNext() {
        return super.hasNext();
    }

    @Override // gnu.trove.TIterator
    public /* bridge */ /* synthetic */ void remove() {
        super.remove();
    }

    public TObjectDoubleIterator(TObjectDoubleHashMap<K> tObjectDoubleHashMap) {
        super(tObjectDoubleHashMap);
        this._map = tObjectDoubleHashMap;
    }

    @Override // gnu.trove.TIterator
    protected final int nextIndex() {
        int i;
        Object obj;
        if (this._expectedSize != this._hash.size()) {
            throw new ConcurrentModificationException();
        }
        Object[] objArr = this._map._set;
        int i2 = this._index;
        while (true) {
            i = i2 - 1;
            if (i2 <= 0 || !((obj = objArr[i]) == null || obj == TObjectHash.REMOVED)) {
                break;
            }
            i2 = i;
        }
        return i;
    }

    public void advance() {
        moveToNextIndex();
    }

    public K key() {
        return (K) this._map._set[this._index];
    }

    public double value() {
        return this._map._values[this._index];
    }

    public double setValue(double d) {
        double dValue = value();
        this._map._values[this._index] = d;
        return dValue;
    }
}
