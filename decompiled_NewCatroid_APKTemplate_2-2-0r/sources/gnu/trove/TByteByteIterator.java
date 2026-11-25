package gnu.trove;

/* JADX INFO: loaded from: classes11.dex */
public class TByteByteIterator extends TPrimitiveIterator {
    private final TByteByteHashMap _map;

    @Override // gnu.trove.TIterator
    public /* bridge */ /* synthetic */ boolean hasNext() {
        return super.hasNext();
    }

    @Override // gnu.trove.TIterator
    public /* bridge */ /* synthetic */ void remove() {
        super.remove();
    }

    public TByteByteIterator(TByteByteHashMap tByteByteHashMap) {
        super(tByteByteHashMap);
        this._map = tByteByteHashMap;
    }

    public void advance() {
        moveToNextIndex();
    }

    public byte key() {
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
