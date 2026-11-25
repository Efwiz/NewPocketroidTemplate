package org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Comparator;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Arrays;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Hash;

/* JADX INFO: loaded from: classes5.dex */
public final class ObjectArrays {
    public static final Object[] EMPTY_ARRAY = new Object[0];
    public static final Object[] DEFAULT_EMPTY_ARRAY = new Object[0];
    public static final Hash.Strategy HASH_STRATEGY = new ArrayHashStrategy();

    private static <K> K[] newArray(K[] kArr, int i) {
        Class<?> cls = kArr.getClass();
        if (cls == Object[].class) {
            return i == 0 ? (K[]) EMPTY_ARRAY : (K[]) new Object[i];
        }
        return (K[]) ((Object[]) Array.newInstance(cls.getComponentType(), i));
    }

    public static <K> K[] forceCapacity(K[] kArr, int i, int i2) {
        K[] kArr2 = (K[]) newArray(kArr, i);
        System.arraycopy(kArr, 0, kArr2, 0, i2);
        return kArr2;
    }

    public static <K> void ensureOffsetLength(K[] kArr, int i, int i2) {
        Arrays.ensureOffsetLength(kArr.length, i, i2);
    }

    public static <K> void stableSort(K[] kArr, int i, int i2) {
        java.util.Arrays.sort(kArr, i, i2);
    }

    public static <K> void stableSort(K[] kArr) {
        stableSort(kArr, 0, kArr.length);
    }

    public static <K> void stableSort(K[] kArr, int i, int i2, Comparator<K> comparator) {
        java.util.Arrays.sort(kArr, i, i2, comparator);
    }

    public static <K> void stableSort(K[] kArr, Comparator<K> comparator) {
        stableSort(kArr, 0, kArr.length, comparator);
    }

    private static final class ArrayHashStrategy<K> implements Hash.Strategy<K[]>, Serializable {
        private static final long serialVersionUID = -7046029254386353129L;

        private ArrayHashStrategy() {
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Hash.Strategy
        public int hashCode(K[] kArr) {
            return java.util.Arrays.hashCode(kArr);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Hash.Strategy
        public boolean equals(K[] kArr, K[] kArr2) {
            return java.util.Arrays.equals(kArr, kArr2);
        }
    }
}
