package com.badlogic.gdx.utils;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/utils/Pool.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public abstract class Pool<T> {
    private final Array<T> freeObjects;
    public final int max;
    public int peak;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/utils/Pool$Poolable.class
      classes8.dex
     */
    public interface Poolable {
        void reset();
    }

    protected abstract T newObject();

    public Pool() {
        this(16, Integer.MAX_VALUE);
    }

    public Pool(int i) {
        this(i, Integer.MAX_VALUE);
    }

    public Pool(int i, int i2) {
        this.freeObjects = new Array<>(false, i);
        this.max = i2;
    }

    public T obtain() {
        return this.freeObjects.size == 0 ? newObject() : this.freeObjects.pop();
    }

    public void free(T t) {
        if (t == null) {
            throw new IllegalArgumentException("object cannot be null.");
        }
        if (this.freeObjects.size < this.max) {
            this.freeObjects.add(t);
            this.peak = Math.max(this.peak, this.freeObjects.size);
            reset(t);
            return;
        }
        discard(t);
    }

    public void fill(int i) {
        for (int i2 = 0; i2 < i; i2++) {
            if (this.freeObjects.size < this.max) {
                this.freeObjects.add(newObject());
            }
        }
        this.peak = Math.max(this.peak, this.freeObjects.size);
    }

    protected void reset(T t) {
        if (t instanceof Poolable) {
            ((Poolable) t).reset();
        }
    }

    protected void discard(T t) {
        reset(t);
    }

    public void freeAll(Array<T> array) {
        if (array == null) {
            throw new IllegalArgumentException("objects cannot be null.");
        }
        Array<T> array2 = this.freeObjects;
        int i = this.max;
        int i2 = array.size;
        for (int i3 = 0; i3 < i2; i3++) {
            T t = array.get(i3);
            if (t != null) {
                if (array2.size < i) {
                    array2.add(t);
                    reset(t);
                } else {
                    discard(t);
                }
            }
        }
        this.peak = Math.max(this.peak, array2.size);
    }

    public void clear() {
        Array<T> array = this.freeObjects;
        int i = array.size;
        for (int i2 = 0; i2 < i; i2++) {
            discard(array.get(i2));
        }
        array.clear();
    }

    public int getFree() {
        return this.freeObjects.size;
    }
}
