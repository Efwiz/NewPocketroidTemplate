package gnu.trove;

import java.util.ConcurrentModificationException;

/* JADX INFO: loaded from: classes11.dex */
public class TByteObjectIterator<V> extends TIterator {
    private final TByteObjectHashMap<V> _map;

    @Override // gnu.trove.TIterator
    public /* bridge */ /* synthetic */ boolean hasNext() {
        return super.hasNext();
    }

    @Override // gnu.trove.TIterator
    public /* bridge */ /* synthetic */ void remove() {
        super.remove();
    }

    public TByteObjectIterator(TByteObjectHashMap<V> tByteObjectHashMap) {
        super(tByteObjectHashMap);
        this._map = tByteObjectHashMap;
    }

    @Override // gnu.trove.TIterator
    protected final int nextIndex() {
        int i;
        if (this._expectedSize != this._map.size()) {
            throw new ConcurrentModificationException();
        }
        V[] vArr = this._map._values;
        int i2 = this._index;
        while (true) {
            i = i2 - 1;
            if (i2 <= 0 || TByteObjectHashMap.isFull(vArr, i)) {
                break;
            }
            i2 = i;
        }
        return i;
    }

    public void advance() {
        moveToNextIndex();
    }

    public byte key() {
        return this._map._set[this._index];
    }

    public V value() {
        return this._map._values[this._index];
    }

    public V setValue(V v) {
        V vValue = value();
        this._map._values[this._index] = v;
        return vValue;
    }
}
