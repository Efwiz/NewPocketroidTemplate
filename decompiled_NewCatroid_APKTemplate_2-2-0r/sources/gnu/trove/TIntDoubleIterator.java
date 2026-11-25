package gnu.trove;

/* JADX INFO: loaded from: classes11.dex */
public class TIntDoubleIterator extends TPrimitiveIterator {
    private final TIntDoubleHashMap _map;

    @Override // gnu.trove.TIterator
    public /* bridge */ /* synthetic */ boolean hasNext() {
        return super.hasNext();
    }

    @Override // gnu.trove.TIterator
    public /* bridge */ /* synthetic */ void remove() {
        super.remove();
    }

    public TIntDoubleIterator(TIntDoubleHashMap tIntDoubleHashMap) {
        super(tIntDoubleHashMap);
        this._map = tIntDoubleHashMap;
    }

    public void advance() {
        moveToNextIndex();
    }

    public int key() {
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
