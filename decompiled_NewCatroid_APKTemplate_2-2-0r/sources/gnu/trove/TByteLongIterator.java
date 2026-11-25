package gnu.trove;

/* JADX INFO: loaded from: classes11.dex */
public class TByteLongIterator extends TPrimitiveIterator {
    private final TByteLongHashMap _map;

    @Override // gnu.trove.TIterator
    public /* bridge */ /* synthetic */ boolean hasNext() {
        return super.hasNext();
    }

    @Override // gnu.trove.TIterator
    public /* bridge */ /* synthetic */ void remove() {
        super.remove();
    }

    public TByteLongIterator(TByteLongHashMap tByteLongHashMap) {
        super(tByteLongHashMap);
        this._map = tByteLongHashMap;
    }

    public void advance() {
        moveToNextIndex();
    }

    public byte key() {
        return this._map._set[this._index];
    }

    public long value() {
        return this._map._values[this._index];
    }

    public long setValue(long j) {
        long jValue = value();
        this._map._values[this._index] = j;
        return jValue;
    }
}
