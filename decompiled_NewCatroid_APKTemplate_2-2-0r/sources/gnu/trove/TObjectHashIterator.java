package gnu.trove;

/* JADX INFO: loaded from: classes11.dex */
class TObjectHashIterator<E> extends THashIterator<E> {
    protected final TObjectHash<E> _objectHash;

    public TObjectHashIterator(TObjectHash<E> tObjectHash) {
        super(tObjectHash);
        this._objectHash = tObjectHash;
    }

    @Override // gnu.trove.THashIterator
    protected E objectAtIndex(int i) {
        return (E) this._objectHash._set[i];
    }
}
