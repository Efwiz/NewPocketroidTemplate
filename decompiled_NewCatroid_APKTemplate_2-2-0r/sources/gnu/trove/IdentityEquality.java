package gnu.trove;

/* JADX INFO: loaded from: classes11.dex */
class IdentityEquality<T> implements Equality<T> {
    @Override // gnu.trove.Equality
    public boolean equals(T t, T t2) {
        return t == t2;
    }

    IdentityEquality() {
    }
}
