package gnu.trove;

/* JADX INFO: loaded from: classes11.dex */
public final class TObjectIdentityHashingStrategy<T> implements TObjectHashingStrategy<T> {
    @Override // gnu.trove.TObjectHashingStrategy, gnu.trove.Equality
    public final boolean equals(T t, T t2) {
        return t == t2;
    }

    @Override // gnu.trove.TObjectHashingStrategy
    public final int computeHashCode(T t) {
        return System.identityHashCode(t);
    }
}
