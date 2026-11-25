package gnu.trove;

/* JADX INFO: loaded from: classes11.dex */
public class TByteIterator extends TPrimitiveIterator {
    private final TByteHash _hash;

    @Override // gnu.trove.TIterator
    public /* bridge */ /* synthetic */ boolean hasNext() {
        return super.hasNext();
    }

    @Override // gnu.trove.TIterator
    public /* bridge */ /* synthetic */ void remove() {
        super.remove();
    }

    public TByteIterator(TByteHash tByteHash) {
        super(tByteHash);
        this._hash = tByteHash;
    }

    public byte next() {
        moveToNextIndex();
        return this._hash._set[this._index];
    }
}
