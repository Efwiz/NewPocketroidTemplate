package gnu.trove;

/* JADX INFO: loaded from: classes11.dex */
public class TFloatFloatIterator extends TPrimitiveIterator {
    private final TFloatFloatHashMap _map;

    @Override // gnu.trove.TIterator
    public /* bridge */ /* synthetic */ boolean hasNext() {
        return super.hasNext();
    }

    @Override // gnu.trove.TIterator
    public /* bridge */ /* synthetic */ void remove() {
        super.remove();
    }

    public TFloatFloatIterator(TFloatFloatHashMap tFloatFloatHashMap) {
        super(tFloatFloatHashMap);
        this._map = tFloatFloatHashMap;
    }

    public void advance() {
        moveToNextIndex();
    }

    public float key() {
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
