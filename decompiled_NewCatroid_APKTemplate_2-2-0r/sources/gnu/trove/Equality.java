package gnu.trove;

/* JADX INFO: loaded from: classes11.dex */
public interface Equality<T> {
    public static final Equality CANONICAL = new CanonicalEquality();
    public static final Equality IDENTITY = new IdentityEquality();

    boolean equals(T t, T t2);
}
