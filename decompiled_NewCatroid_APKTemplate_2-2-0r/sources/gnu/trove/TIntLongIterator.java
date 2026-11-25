package gnu.trove;

/* JADX INFO: loaded from: classes11.dex */
public class TIntLongIterator extends TPrimitiveIterator {
    private final TIntLongHashMap _map;

    @Override // gnu.trove.TIterator
    public /* bridge */ /* synthetic */ boolean hasNext() {
        return super.hasNext();
    }

    @Override // gnu.trove.TIterator
    public /* bridge */ /* synthetic */ void remove() {
        super.remove();
    }

    public TIntLongIterator(TIntLongHashMap tIntLongHashMap) {
        super(tIntLongHashMap);
        this._map = tIntLongHashMap;
    }

    public void advance() {
        moveToNextIndex();
    }

    public int key() {
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
