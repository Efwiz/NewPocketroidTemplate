package org.jetbrains.kotlin.com.intellij.util.containers;

/* JADX INFO: loaded from: classes3.dex */
public final class IntObjectHashMap<T> {
    private final ArrayProducer<T[]> arrayFactory;
    private boolean hasZeroValue;
    private int[] keys;
    private int size;
    private T specialZeroValue;
    private T[] values;

    public interface ArrayProducer<T> {
        T produce(int i);
    }

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        objArr[0] = "value";
        objArr[1] = "org/jetbrains/kotlin/com/intellij/util/containers/IntObjectHashMap";
        if (i == 1) {
            objArr[2] = "addByIndex";
        } else if (i != 2) {
            objArr[2] = "replaceByIndex";
        } else {
            objArr[2] = "put";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public IntObjectHashMap(IntObjectHashMap<T> intObjectHashMap) {
        this.arrayFactory = intObjectHashMap.arrayFactory;
        this.keys = (int[]) intObjectHashMap.keys.clone();
        this.values = (T[]) ((Object[]) intObjectHashMap.values.clone());
        this.size = intObjectHashMap.size;
        this.specialZeroValue = intObjectHashMap.specialZeroValue;
        this.hasZeroValue = intObjectHashMap.hasZeroValue;
    }

    public void replaceByIndex(int i, int i2, T t) {
        if (t == null) {
            $$$reportNull$$$0(0);
        }
        if (i2 == 0) {
            this.specialZeroValue = t;
            this.hasZeroValue = true;
        } else {
            this.values[i] = t;
        }
    }

    public void addByIndex(int i, int i2, T t) {
        if (t == null) {
            $$$reportNull$$$0(1);
        }
        if (i2 == 0) {
            this.specialZeroValue = t;
            this.hasZeroValue = true;
            return;
        }
        if (this.size >= (this.values.length * 2) / 3) {
            rehash();
            i = hashIndex(this.keys, i2);
        }
        this.size++;
        this.values[i] = t;
        this.keys[i] = i2;
    }

    public int index(int i) {
        if (i == 0) {
            return Integer.MIN_VALUE;
        }
        return hashIndex(this.keys, i);
    }

    private static int hashIndex(int[] iArr, int i) {
        int length = ((int) ((((long) i) * 2654435769L) & 2147483647L)) & (iArr.length - 1);
        while (true) {
            int i2 = iArr[length];
            if (i2 == 0 || i2 == i) {
                return length;
            }
            if (length == 0) {
                length = iArr.length;
            }
            length--;
        }
    }

    private void rehash() {
        int length = this.keys.length << 1;
        int[] iArr = new int[length];
        T[] tArrProduce = this.arrayFactory.produce(length);
        for (int length2 = this.keys.length - 1; length2 >= 0; length2--) {
            int i = this.keys[length2];
            if (i != 0) {
                int iHashIndex = hashIndex(iArr, i);
                tArrProduce[iHashIndex] = this.values[length2];
                if (iArr[iHashIndex] == 0) {
                    iArr[iHashIndex] = i;
                }
            }
        }
        this.keys = iArr;
        this.values = tArrProduce;
    }

    public T get(int i) {
        return i == 0 ? this.specialZeroValue : this.values[hashIndex(this.keys, i)];
    }

    public T getByIndex(int i, int i2) {
        return i2 == 0 ? this.specialZeroValue : this.values[i];
    }
}
