package gnu.trove;

/* JADX INFO: loaded from: classes11.dex */
public class TLongFloatIterator extends TPrimitiveIterator {
    private final TLongFloatHashMap _map;

    @Override // gnu.trove.TIterator
    public /* bridge */ /* synthetic */ boolean hasNext() {
        return super.hasNext();
    }

    @Override // gnu.trove.TIterator
    public /* bridge */ /* synthetic */ void remove() {
        super.remove();
    }

    public TLongFloatIterator(TLongFloatHashMap tLongFloatHashMap) {
        super(tLongFloatHashMap);
        this._map = tLongFloatHashMap;
    }

    public void advance() {
        moveToNextIndex();
    }

    public long key() {
        return this._map._set[this._index];
    }

    public float value() {
        return this._map._values[this._index];
    }

    public float setValue(float f) {
        float fValue = value();
        this._map._values[this._index] = f;
        return fValue;
    }
}
