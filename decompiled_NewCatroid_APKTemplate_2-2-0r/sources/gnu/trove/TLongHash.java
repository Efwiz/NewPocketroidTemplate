package gnu.trove;

/* JADX INFO: loaded from: classes11.dex */
public abstract class TLongHash extends TPrimitiveHash implements TLongHashingStrategy {
    protected final TLongHashingStrategy _hashingStrategy;
    protected transient long[] _set;

    public TLongHash() {
        this._hashingStrategy = this;
    }

    public TLongHash(int i) {
        super(i);
        this._hashingStrategy = this;
    }

    public TLongHash(int i, float f) {
        super(i, f);
        this._hashingStrategy = this;
    }

    public TLongHash(TLongHashingStrategy tLongHashingStrategy) {
        this._hashingStrategy = tLongHashingStrategy;
    }

    public TLongHash(int i, TLongHashingStrategy tLongHashingStrategy) {
        super(i);
        this._hashingStrategy = tLongHashingStrategy;
    }

    public TLongHash(int i, float f, TLongHashingStrategy tLongHashingStrategy) {
        super(i, f);
        this._hashingStrategy = tLongHashingStrategy;
    }

    @Override // gnu.trove.TPrimitiveHash, gnu.trove.THash
    public Object clone() {
        TLongHash tLongHash = (TLongHash) super.clone();
        long[] jArr = this._set;
        tLongHash._set = jArr == null ? null : (long[]) jArr.clone();
        return tLongHash;
    }

    @Override // gnu.trove.TPrimitiveHash, gnu.trove.THash
    protected int setUp(int i) {
        int up = super.setUp(i);
        this._set = i == -1 ? null : new long[up];
        return up;
    }

    public boolean contains(long j) {
        return index(j) >= 0;
    }

    public boolean forEach(TLongProcedure tLongProcedure) {
        byte[] bArr = this._states;
        long[] jArr = this._set;
        if (bArr != null) {
            int length = bArr.length;
            while (true) {
                int i = length - 1;
                if (length <= 0) {
                    break;
                }
                if (bArr[i] == 1 && !tLongProcedure.execute(jArr[i])) {
                    return false;
                }
                length = i;
            }
        }
        return true;
    }

    @Override // gnu.trove.TPrimitiveHash, gnu.trove.THash
    protected void removeAt(int i) {
        this._set[i] = 0;
        super.removeAt(i);
    }

    protected int index(long j) {
        byte[] bArr = this._states;
        if (bArr == null) {
            return -1;
        }
        long[] jArr = this._set;
        int length = bArr.length;
        int iComputeHashCode = this._hashingStrategy.computeHashCode(j) & Integer.MAX_VALUE;
        int i = iComputeHashCode % length;
        byte b = bArr[i];
        if (b != 0 && (b == 2 || jArr[i] != j)) {
            int i2 = (iComputeHashCode % (length - 2)) + 1;
            while (true) {
                i -= i2;
                if (i < 0) {
                    i += length;
                }
                byte b2 = bArr[i];
                if (b2 == 0 || (b2 != 2 && jArr[i] == j)) {
                    break;
                }
            }
        }
        if (bArr[i] == 0) {
            return -1;
        }
        return i;
    }

    protected int insertionIndex(long j) {
        byte b;
        byte b2;
        if (this._set == null) {
            setUp(6);
        }
        byte[] bArr = this._states;
        long[] jArr = this._set;
        int length = bArr.length;
        int iComputeHashCode = this._hashingStrategy.computeHashCode(j) & Integer.MAX_VALUE;
        int i = iComputeHashCode % length;
        byte b3 = bArr[i];
        if (b3 == 0) {
            return i;
        }
        if (b3 == 1 && jArr[i] == j) {
            return (-i) - 1;
        }
        int i2 = (iComputeHashCode % (length - 2)) + 1;
        do {
            i -= i2;
            if (i < 0) {
                i += length;
            }
            b = bArr[i];
            if (b != 1) {
                break;
            }
        } while (jArr[i] != j);
        if (b != 2) {
            return b == 1 ? (-i) - 1 : i;
        }
        int i3 = i;
        while (true) {
            b2 = bArr[i3];
            if (b2 == 0 || (b2 != 2 && jArr[i3] == j)) {
                break;
            }
            i3 -= i2;
            if (i3 < 0) {
                i3 += length;
            }
        }
        return b2 == 1 ? (-i3) - 1 : i;
    }

    @Override // gnu.trove.TLongHashingStrategy
    public final int computeHashCode(long j) {
        return HashFunctions.hash(j);
    }
}
