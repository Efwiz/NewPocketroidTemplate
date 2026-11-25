package io.github.rosemoe.sora.util;

/* JADX INFO: loaded from: classes11.dex */
public abstract class ObjectPool<T> {
    private final Object[] pool;

    protected abstract T allocateNew();

    protected void onRecycleObject(T t) {
    }

    public ObjectPool() {
        this(16);
    }

    public ObjectPool(int i) {
        this.pool = new Object[i];
    }

    public void recycle(T t) {
        if (t == null) {
            return;
        }
        onRecycleObject(t);
        synchronized (this) {
            int i = 0;
            while (true) {
                Object[] objArr = this.pool;
                if (i >= objArr.length) {
                    break;
                }
                if (objArr[i] == null) {
                    objArr[i] = t;
                    break;
                }
                i++;
            }
        }
    }

    public T obtain() {
        T t;
        synchronized (this) {
            int length = this.pool.length - 1;
            while (true) {
                t = null;
                if (length < 0) {
                    break;
                }
                Object[] objArr = this.pool;
                Object obj = objArr[length];
                if (obj != null) {
                    objArr[length] = null;
                    t = (T) obj;
                    break;
                }
                length--;
            }
        }
        return t == null ? allocateNew() : t;
    }
}
