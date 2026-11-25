package gnu.trove;

import java.io.Serializable;

/* JADX INFO: loaded from: classes11.dex */
public interface TObjectHashingStrategy<T> extends Serializable, Equality<T> {
    public static final TObjectHashingStrategy IDENTITY = new TObjectIdentityHashingStrategy();
    public static final TObjectHashingStrategy CANONICAL = new TObjectCanonicalHashingStrategy();

    int computeHashCode(T t);

    boolean equals(T t, T t2);
}
