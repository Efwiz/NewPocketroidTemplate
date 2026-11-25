package gnu.trove;

/* JADX INFO: loaded from: classes11.dex */
class CanonicalEquality<T> implements Equality<T> {
    CanonicalEquality() {
    }

    @Override // gnu.trove.Equality
    public boolean equals(T t, T t2) {
        if (t != null) {
            return t.equals(t2);
        }
        return t2 == null;
    }
}
