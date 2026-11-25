package gnu.trove;

/* JADX INFO: loaded from: classes11.dex */
public class TFloatIterator extends TPrimitiveIterator {
    private final TFloatHash _hash;

    @Override // gnu.trove.TIterator
    public /* bridge */ /* synthetic */ boolean hasNext() {
        return super.hasNext();
    }

    @Override // gnu.trove.TIterator
    public /* bridge */ /* synthetic */ void remove() {
        super.remove();
    }

    public TFloatIterator(TFloatHash tFloatHash) {
        super(tFloatHash);
        this._hash = tFloatHash;
    }

    public float next() {
        moveToNextIndex();
        return this._hash._set[this._index];
    }
}
