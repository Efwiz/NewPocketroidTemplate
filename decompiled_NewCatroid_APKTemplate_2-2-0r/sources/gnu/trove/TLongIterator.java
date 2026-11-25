package gnu.trove;

/* JADX INFO: loaded from: classes11.dex */
public class TLongIterator extends TPrimitiveIterator {
    private final TLongHash _hash;

    @Override // gnu.trove.TIterator
    public /* bridge */ /* synthetic */ boolean hasNext() {
        return super.hasNext();
    }

    @Override // gnu.trove.TIterator
    public /* bridge */ /* synthetic */ void remove() {
        super.remove();
    }

    public TLongIterator(TLongHash tLongHash) {
        super(tLongHash);
        this._hash = tLongHash;
    }

    public long next() {
        moveToNextIndex();
        return this._hash._set[this._index];
    }
}
