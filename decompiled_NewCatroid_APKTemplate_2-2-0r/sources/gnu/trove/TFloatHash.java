package gnu.trove;

/* JADX INFO: loaded from: classes11.dex */
public abstract class TFloatHash extends TPrimitiveHash implements TFloatHashingStrategy {
    protected final TFloatHashingStrategy _hashingStrategy;
    protected transient float[] _set;

    public TFloatHash() {
        this._hashingStrategy = this;
    }

    public TFloatHash(int i) {
        super(i);
        this._hashingStrategy = this;
    }

    public TFloatHash(int i, float f) {
        super(i, f);
        this._hashingStrategy = this;
    }

    public TFloatHash(TFloatHashingStrategy tFloatHashingStrategy) {
        this._hashingStrategy = tFloatHashingStrategy;
    }

    public TFloatHash(int i, TFloatHashingStrategy tFloatHashingStrategy) {
        super(i);
        this._hashingStrategy = tFloatHashingStrategy;
    }

    public TFloatHash(int i, float f, TFloatHashingStrategy tFloatHashingStrategy) {
        super(i, f);
        this._hashingStrategy = tFloatHashingStrategy;
    }

    @Override // gnu.trove.TPrimitiveHash, gnu.trove.THash
    public Object clone() {
        TFloatHash tFloatHash = (TFloatHash) super.clone();
        float[] fArr = this._set;
        tFloatHash._set = fArr == null ? null : (float[]) fArr.clone();
        return tFloatHash;
    }

    @Override // gnu.trove.TPrimitiveHash, gnu.trove.THash
    protected int setUp(int i) {
        int up = super.setUp(i);
        this._set = i == -1 ? null : new float[up];
        return up;
    }

    public boolean contains(float f) {
        return index(f) >= 0;
    }

    public boolean forEach(TFloatProcedure tFloatProcedure) {
        byte[] bArr = this._states;
        float[] fArr = this._set;
        if (bArr != null) {
            int length = bArr.length;
            while (true) {
                int i = length - 1;
                if (length <= 0) {
                    break;
                }
                if (bArr[i] == 1 && !tFloatProcedure.execute(fArr[i])) {
                    return false;
                }
                length = i;
            }
        }
        return true;
    }

    @Override // gnu.trove.TPrimitiveHash, gnu.trove.THash
    protected void removeAt(int i) {
        this._set[i] = 0.0f;
        super.removeAt(i);
    }

    protected int index(float f) {
        byte[] bArr = this._states;
        if (bArr == null) {
            return -1;
        }
        float[] fArr = this._set;
        int length = bArr.length;
        int iComputeHashCode = this._hashingStrategy.computeHashCode(f) & Integer.MAX_VALUE;
        int i = iComputeHashCode % length;
        byte b = bArr[i];
        if (b != 0 && (b == 2 || fArr[i] != f)) {
            int i2 = (iComputeHashCode % (length - 2)) + 1;
            while (true) {
                i -= i2;
                if (i < 0) {
                    i += length;
                }
                byte b2 = bArr[i];
                if (b2 == 0 || (b2 != 2 && fArr[i] == f)) {
                    break;
                }
            }
        }
        if (bArr[i] == 0) {
            return -1;
        }
        return i;
    }

    protected int insertionIndex(float f) {
        byte b;
        byte b2;
        if (this._set == null) {
            setUp(6);
        }
        byte[] bArr = this._states;
        float[] fArr = this._set;
        int length = bArr.length;
        int iComputeHashCode = this._hashingStrategy.computeHashCode(f) & Integer.MAX_VALUE;
        int i = iComputeHashCode % length;
        byte b3 = bArr[i];
        if (b3 == 0) {
            return i;
        }
        if (b3 == 1 && fArr[i] == f) {
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
        } while (fArr[i] != f);
        if (b != 2) {
            return b == 1 ? (-i) - 1 : i;
        }
        int i3 = i;
        while (true) {
            b2 = bArr[i3];
            if (b2 == 0 || (b2 != 2 && fArr[i3] == f)) {
                break;
            }
            i3 -= i2;
            if (i3 < 0) {
                i3 += length;
            }
        }
        return b2 == 1 ? (-i3) - 1 : i;
    }

    @Override // gnu.trove.TFloatHashingStrategy
    public final int computeHashCode(float f) {
        return HashFunctions.hash(f);
    }
}
