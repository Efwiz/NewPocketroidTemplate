package gnu.trove;

import com.huawei.hms.ml.language.common.utils.Constant;

/* JADX INFO: loaded from: classes11.dex */
public abstract class TObjectHash<T> extends THash implements TObjectHashingStrategy<T> {
    protected final TObjectHashingStrategy<T> _hashingStrategy;
    protected transient Object[] _set;
    public static final Object REMOVED = new Object();
    public static final NULL NULL = new NULL();

    /* JADX INFO: Access modifiers changed from: private */
    static class NULL {
        NULL() {
        }
    }

    public TObjectHash() {
        this._hashingStrategy = this;
    }

    public TObjectHash(TObjectHashingStrategy<T> tObjectHashingStrategy) {
        this._hashingStrategy = tObjectHashingStrategy;
    }

    public TObjectHash(int i) {
        super(i);
        this._hashingStrategy = this;
    }

    public TObjectHash(int i, TObjectHashingStrategy<T> tObjectHashingStrategy) {
        super(i);
        this._hashingStrategy = tObjectHashingStrategy;
    }

    public TObjectHash(int i, float f) {
        super(i, f);
        this._hashingStrategy = this;
    }

    public TObjectHash(int i, float f, TObjectHashingStrategy<T> tObjectHashingStrategy) {
        super(i, f);
        this._hashingStrategy = tObjectHashingStrategy;
    }

    @Override // gnu.trove.THash
    public TObjectHash<T> clone() {
        TObjectHash<T> tObjectHash = (TObjectHash) super.clone();
        tObjectHash._set = this._set == EMPTY_OBJECT_ARRAY ? EMPTY_OBJECT_ARRAY : (Object[]) this._set.clone();
        return tObjectHash;
    }

    @Override // gnu.trove.THash
    protected int capacity() {
        return this._set.length;
    }

    @Override // gnu.trove.THash
    protected void removeAt(int i) {
        this._set[i] = REMOVED;
        super.removeAt(i);
    }

    @Override // gnu.trove.THash
    protected int setUp(int i) {
        int up = super.setUp(i);
        this._set = i == -1 ? EMPTY_OBJECT_ARRAY : new Object[up];
        return up;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public boolean forEach(TObjectProcedure<T> tObjectProcedure) {
        Object[] objArr = this._set;
        int length = objArr.length;
        while (true) {
            int i = length - 1;
            if (length <= 0) {
                return true;
            }
            Object obj = objArr[i];
            if (obj != null && obj != REMOVED && !tObjectProcedure.execute(obj)) {
                return false;
            }
            length = i;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public boolean contains(Object obj) {
        return index(obj) >= 0;
    }

    protected int index(T t) {
        Object[] objArr = this._set;
        if (objArr == EMPTY_OBJECT_ARRAY) {
            return -1;
        }
        int length = objArr.length;
        int iComputeHashCode = this._hashingStrategy.computeHashCode(t) & Integer.MAX_VALUE;
        int i = iComputeHashCode % length;
        Object obj = objArr[i];
        if (obj != null && (obj == REMOVED || !this._hashingStrategy.equals((T) obj, t))) {
            int i2 = (iComputeHashCode % (length - 2)) + 1;
            while (true) {
                i -= i2;
                if (i < 0) {
                    i += length;
                }
                obj = objArr[i];
                if (obj == null || (obj != REMOVED && this._hashingStrategy.equals((T) obj, t))) {
                    break;
                }
            }
        }
        if (obj == null) {
            return -1;
        }
        return i;
    }

    protected int insertionIndex(T t) {
        Object obj;
        if (this._set == EMPTY_OBJECT_ARRAY) {
            setUp(6);
        }
        Object[] objArr = this._set;
        int length = objArr.length;
        int iComputeHashCode = this._hashingStrategy.computeHashCode(t) & Integer.MAX_VALUE;
        int i = iComputeHashCode % length;
        Object obj2 = objArr[i];
        if (obj2 == null) {
            return i;
        }
        Object obj3 = REMOVED;
        if (obj2 == obj3 || !this._hashingStrategy.equals((T) obj2, t)) {
            int i2 = (iComputeHashCode % (length - 2)) + 1;
            int i3 = obj2 == obj3 ? i : -1;
            do {
                i -= i2;
                if (i < 0) {
                    i += length;
                }
                obj = objArr[i];
                if (i3 == -1 && obj == REMOVED) {
                    i3 = i;
                }
                if (obj == null || obj == REMOVED) {
                    break;
                }
            } while (!this._hashingStrategy.equals((T) obj, t));
            if (obj == REMOVED) {
                while (obj != null && (obj == REMOVED || !this._hashingStrategy.equals((T) obj, t))) {
                    i -= i2;
                    if (i < 0) {
                        i += length;
                    }
                    obj = objArr[i];
                }
            }
            if (obj == null) {
                return i3 == -1 ? i : i3;
            }
        }
        return (-i) - 1;
    }

    @Override // gnu.trove.TObjectHashingStrategy
    public final int computeHashCode(T t) {
        if (t != null) {
            return t.hashCode();
        }
        return 0;
    }

    @Override // gnu.trove.TObjectHashingStrategy, gnu.trove.Equality
    public final boolean equals(T t, T t2) {
        if (t != null) {
            return t.equals(t2);
        }
        return t2 == null;
    }

    protected final void throwObjectContractViolation(Object obj, Object obj2) throws IllegalArgumentException {
        throw new IllegalArgumentException("Equal objects must have equal hashcodes. During rehashing, Trove discovered that the following two objects claim to be equal (as in java.lang.Object.equals() or TObjectHashingStrategy.equals()) but their hashCodes (or those calculated by your TObjectHashingStrategy) are not equal.This violates the general contract of java.lang.Object.hashCode().  See bullet point two in that method's documentation. object #1 =" + obj + (obj == null ? "" : " (" + obj.getClass() + Constant.AFTER_QUTO) + ", hashCode=" + this._hashingStrategy.computeHashCode(obj) + "; object #2 =" + obj2 + (obj2 != null ? " (" + obj2.getClass() + Constant.AFTER_QUTO : "") + ", hashCode=" + this._hashingStrategy.computeHashCode(obj2));
    }
}
