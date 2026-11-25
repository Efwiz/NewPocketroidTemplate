package gnu.trove;

/* JADX INFO: loaded from: classes11.dex */
public class TDoubleIterator extends TPrimitiveIterator {
    private final TDoubleHash _hash;

    @Override // gnu.trove.TIterator
    public /* bridge */ /* synthetic */ boolean hasNext() {
        return super.hasNext();
    }

    @Override // gnu.trove.TIterator
    public /* bridge */ /* synthetic */ void remove() {
        super.remove();
    }

    public TDoubleIterator(TDoubleHash tDoubleHash) {
        super(tDoubleHash);
        this._hash = tDoubleHash;
    }

    public double next() {
        moveToNextIndex();
        return this._hash._set[this._index];
    }
}
