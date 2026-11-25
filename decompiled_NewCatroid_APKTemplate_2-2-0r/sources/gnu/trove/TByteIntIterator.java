package gnu.trove;

/* JADX INFO: loaded from: classes11.dex */
public class TByteIntIterator extends TPrimitiveIterator {
    private final TByteIntHashMap _map;

    @Override // gnu.trove.TIterator
    public /* bridge */ /* synthetic */ boolean hasNext() {
        return super.hasNext();
    }

    @Override // gnu.trove.TIterator
    public /* bridge */ /* synthetic */ void remove() {
        super.remove();
    }

    public TByteIntIterator(TByteIntHashMap tByteIntHashMap) {
        super(tByteIntHashMap);
        this._map = tByteIntHashMap;
    }

    public void advance() {
        moveToNextIndex();
    }

    public byte key() {
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
