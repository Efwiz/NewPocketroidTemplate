package gnu.trove;

/* JADX INFO: loaded from: classes11.dex */
public class TFloatByteIterator extends TPrimitiveIterator {
    private final TFloatByteHashMap _map;

    @Override // gnu.trove.TIterator
    public /* bridge */ /* synthetic */ boolean hasNext() {
        return super.hasNext();
    }

    @Override // gnu.trove.TIterator
    public /* bridge */ /* synthetic */ void remove() {
        super.remove();
    }

    public TFloatByteIterator(TFloatByteHashMap tFloatByteHashMap) {
        super(tFloatByteHashMap);
        this._map = tFloatByteHashMap;
    }

    public void advance() {
        moveToNextIndex();
    }

    public float key() {
        return this._map._set[this._index];
    }

    public byte value() {
        return this._map._values[this._index];
    }

    public byte setValue(byte b) {
        byte bValue = value();
        this._map._values[this._index] = b;
        return bValue;
    }
}
