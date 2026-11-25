package gnu.trove;

/* JADX INFO: loaded from: classes11.dex */
public class TLongDoubleIterator extends TPrimitiveIterator {
    private final TLongDoubleHashMap _map;

    @Override // gnu.trove.TIterator
    public /* bridge */ /* synthetic */ boolean hasNext() {
        return super.hasNext();
    }

    @Override // gnu.trove.TIterator
    public /* bridge */ /* synthetic */ void remove() {
        super.remove();
    }

    public TLongDoubleIterator(TLongDoubleHashMap tLongDoubleHashMap) {
        super(tLongDoubleHashMap);
        this._map = tLongDoubleHashMap;
    }

    public void advance() {
        moveToNextIndex();
    }

    public long key() {
        return this._map._set[this._index];
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
