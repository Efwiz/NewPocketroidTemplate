package gnu.trove;

/* JADX INFO: loaded from: classes11.dex */
public class TDoubleIntIterator extends TPrimitiveIterator {
    private final TDoubleIntHashMap _map;

    @Override // gnu.trove.TIterator
    public /* bridge */ /* synthetic */ boolean hasNext() {
        return super.hasNext();
    }

    @Override // gnu.trove.TIterator
    public /* bridge */ /* synthetic */ void remove() {
        super.remove();
    }

    public TDoubleIntIterator(TDoubleIntHashMap tDoubleIntHashMap) {
        super(tDoubleIntHashMap);
        this._map = tDoubleIntHashMap;
    }

    public void advance() {
        moveToNextIndex();
    }

    public double key() {
        return this._map._set[this._index];
    }

    public int value() {
        return this._map._values[this._index];
    }

    public int setValue(int i) {
        int iValue = value();
        this._map._values[this._index] = i;
        return iValue;
    }
}
