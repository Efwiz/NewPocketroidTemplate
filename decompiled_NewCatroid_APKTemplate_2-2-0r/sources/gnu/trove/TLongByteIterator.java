package gnu.trove;

/* JADX INFO: loaded from: classes11.dex */
public class TLongByteIterator extends TPrimitiveIterator {
    private final TLongByteHashMap _map;

    @Override // gnu.trove.TIterator
    public /* bridge */ /* synthetic */ boolean hasNext() {
        return super.hasNext();
    }

    @Override // gnu.trove.TIterator
    public /* bridge */ /* synthetic */ void remove() {
        super.remove();
    }

    public TLongByteIterator(TLongByteHashMap tLongByteHashMap) {
        super(tLongByteHashMap);
        this._map = tLongByteHashMap;
    }

    public void advance() {
        moveToNextIndex();
    }

    public long key() {
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
