package gnu.trove;

/* JADX INFO: loaded from: classes11.dex */
public abstract class THash implements Cloneable {
    protected static final int DEFAULT_INITIAL_CAPACITY = 4;
    protected static final float DEFAULT_LOAD_FACTOR = 0.8f;
    protected static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];
    protected static final int JUST_CREATED_CAPACITY = -1;
    protected transient int _deadkeys;
    protected transient int _free;
    protected final float _loadFactor;
    protected int _maxSize;
    protected transient int _size;

    protected abstract int capacity();

    protected abstract void rehash(int i);

    public THash() {
        this(-1, 0.8f);
    }

    public THash(int i) {
        this(i, 0.8f);
    }

    public THash(int i, float f) {
        this._loadFactor = f;
        setUp(i != -1 ? ((int) (i / f)) + 1 : -1);
    }

    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException unused) {
            return null;
        }
    }

    public boolean isEmpty() {
        return this._size == 0;
    }

    public int size() {
        return this._size;
    }

    public void ensureCapacity(int i) {
        if (i > this._maxSize - size()) {
            rehash(PrimeFinder.nextPrime(((int) ((i + size()) / this._loadFactor)) + 2));
            computeMaxSize(capacity());
        }
    }

    public void compact() {
        rehash(PrimeFinder.nextPrime(((int) (size() / this._loadFactor)) + 2));
        computeMaxSize(capacity());
    }

    public final void trimToSize() {
        compact();
    }

    protected void removeAt(int i) {
        this._size--;
        this._deadkeys++;
        compactIfNecessary();
    }

    private void compactIfNecessary() {
        if (this._deadkeys <= this._size || capacity() <= 42) {
            return;
        }
        compact();
    }

    public final void stopCompactingOnRemove() {
        int i = this._deadkeys;
        if (i < 0) {
            throw new IllegalStateException("Unpaired stop/startCompactingOnRemove");
        }
        this._deadkeys = i - capacity();
    }

    public final void startCompactingOnRemove(boolean z) {
        int i = this._deadkeys;
        if (i > 0) {
            throw new IllegalStateException("Unpaired stop/startCompactingOnRemove");
        }
        this._deadkeys = i + capacity();
        if (z) {
            compactIfNecessary();
        }
    }

    public void clear() {
        this._size = 0;
        this._free = capacity();
        this._deadkeys = 0;
    }

    protected int setUp(int i) {
        int iNextPrime = i == -1 ? 0 : PrimeFinder.nextPrime(i);
        computeMaxSize(iNextPrime);
        return iNextPrime;
    }

    private void computeMaxSize(int i) {
        this._maxSize = Math.max(0, Math.min(i - 1, (int) (i * this._loadFactor)));
        this._free = i - this._size;
        this._deadkeys = 0;
    }

    protected final void postInsertHook(boolean z) {
        if (z) {
            this._free--;
        } else {
            this._deadkeys--;
        }
        int i = this._size + 1;
        this._size = i;
        if (i > this._maxSize || this._free == 0) {
            rehash(PrimeFinder.nextPrime(calculateGrownCapacity()));
            computeMaxSize(capacity());
        }
    }

    protected int calculateGrownCapacity() {
        return capacity() << 1;
    }
}
