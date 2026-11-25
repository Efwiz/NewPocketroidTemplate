package gnu.trove;

/* JADX INFO: loaded from: classes11.dex */
public class TLongIntIterator extends TPrimitiveIterator {
    private final TLongIntHashMap _map;

    @Override // gnu.trove.TIterator
    public /* bridge */ /* synthetic */ boolean hasNext() {
        return super.hasNext();
    }

    @Override // gnu.trove.TIterator
    public /* bridge */ /* synthetic */ void remove() {
        super.remove();
    }

    public TLongIntIterator(TLongIntHashMap tLongIntHashMap) {
        super(tLongIntHashMap);
        this._map = tLongIntHashMap;
    }

    public void advance() {
        moveToNextIndex();
    }

    public long key() {
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
