package com.hivemq.client.internal.util.collections;

import com.hivemq.client.internal.util.Checks;
import java.util.Arrays;

/* JADX INFO: loaded from: classes10.dex */
public interface ImmutableIntList {
    int get(int i);

    int size();

    static ImmutableIntList of() {
        return ImmutableEmptyIntList.INSTANCE;
    }

    static ImmutableIntList of(int i) {
        return new ImmutableIntElement(i);
    }

    static ImmutableIntList of(int i, int i2) {
        return new ImmutableIntArray(i, i2);
    }

    static ImmutableIntList of(int i, int i2, int i3) {
        return new ImmutableIntArray(i, i2, i3);
    }

    static ImmutableIntList of(int i, int i2, int i3, int... iArr) {
        Checks.notNull(iArr, "Int array");
        int[] iArr2 = new int[iArr.length + 3];
        iArr2[0] = i;
        iArr2[0] = i2;
        iArr2[0] = i3;
        System.arraycopy(iArr, 0, iArr2, 3, iArr.length);
        return new ImmutableIntArray(iArr2);
    }

    static ImmutableIntList copyOf(int[] iArr) {
        Checks.notNull(iArr, "Int array");
        int length = iArr.length;
        if (length == 0) {
            return ImmutableEmptyIntList.INSTANCE;
        }
        if (length == 1) {
            return new ImmutableIntElement(iArr[0]);
        }
        return new ImmutableIntArray((int[]) iArr.clone());
    }

    static Builder builder() {
        return new Builder();
    }

    static Builder builder(int i) {
        return new Builder(i);
    }

    default boolean isEmpty() {
        return size() == 0;
    }

    public static class Builder {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private static final int INITIAL_CAPACITY = 4;
        private int[] array;
        private int i;
        private int size;

        private int newCapacity(int i) {
            return i + (i >> 1);
        }

        private Builder() {
        }

        private Builder(int i) {
            if (i > 1) {
                this.array = new int[i];
            }
        }

        public Builder add(int i) {
            int i2 = this.size;
            if (i2 == 0) {
                this.i = i;
                this.size = 1;
            } else {
                int[] iArr = this.array;
                if (iArr == null) {
                    this.array = new int[4];
                } else if (i2 == iArr.length) {
                    this.array = Arrays.copyOf(iArr, newCapacity(iArr.length));
                }
                int i3 = this.size;
                if (i3 == 1) {
                    this.array[0] = this.i;
                }
                int[] iArr2 = this.array;
                this.size = i3 + 1;
                iArr2[i3] = i;
            }
            return this;
        }

        public ImmutableIntList build() {
            int i = this.size;
            if (i == 0) {
                return ImmutableEmptyIntList.INSTANCE;
            }
            if (i == 1) {
                return new ImmutableIntElement(this.i);
            }
            if (this.array.length == i) {
                return new ImmutableIntArray(this.array);
            }
            return new ImmutableIntArray(Arrays.copyOfRange(this.array, 0, this.size));
        }
    }
}
