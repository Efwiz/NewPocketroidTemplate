package gnu.trove;

/* JADX INFO: loaded from: classes11.dex */
public class TIntIntIterator extends TPrimitiveIterator {
    private final TIntIntHashMap _map;

    @Override // gnu.trove.TIterator
    public /* bridge */ /* synthetic */ boolean hasNext() {
        return super.hasNext();
    }

    @Override // gnu.trove.TIterator
    public /* bridge */ /* synthetic */ void remove() {
        super.remove();
    }

    public TIntIntIterator(TIntIntHashMap tIntIntHashMap) {
        super(tIntIntHashMap);
        this._map = tIntIntHashMap;
    }

    public void advance() {
        moveToNextIndex();
    }

    public int key() {
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
