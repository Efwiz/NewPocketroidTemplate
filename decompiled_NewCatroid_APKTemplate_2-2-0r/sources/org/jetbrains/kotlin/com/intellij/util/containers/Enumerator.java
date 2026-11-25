package org.jetbrains.kotlin.com.intellij.util.containers;

import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtil;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2IntMap;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectIterator;

/* JADX INFO: loaded from: classes3.dex */
public class Enumerator<T> {
    private static final Logger LOG = Logger.getInstance((Class<?>) Enumerator.class);
    private int myNextNumber = 1;
    private final Object2IntMap<T> myNumbers;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 3) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 3) ? 2 : 3];
        if (i == 1 || i == 3) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/util/containers/Enumerator";
        } else if (i != 4) {
            objArr[0] = Constants.OBJECTS;
        } else {
            objArr[0] = "object";
        }
        if (i == 1 || i == 3) {
            objArr[1] = "enumerate";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/containers/Enumerator";
        }
        if (i != 1 && i != 3) {
            if (i != 4) {
                objArr[2] = "enumerate";
            } else {
                objArr[2] = "contains";
            }
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 3) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public Enumerator(int i) {
        this.myNumbers = new Object2IntOpenHashMap(i);
    }

    public int[] enumerate(T[] tArr, int i, int i2) {
        if (tArr == null) {
            $$$reportNull$$$0(2);
        }
        int[] iArrNewIntArray = ArrayUtil.newIntArray((tArr.length - i) - i2);
        for (int i3 = i; i3 < tArr.length - i2; i3++) {
            iArrNewIntArray[i3 - i] = enumerate(tArr[i3]);
        }
        if (iArrNewIntArray == null) {
            $$$reportNull$$$0(3);
        }
        return iArrNewIntArray;
    }

    public int enumerate(T t) {
        int iEnumerateImpl = enumerateImpl(t);
        return Math.max(iEnumerateImpl, -iEnumerateImpl);
    }

    public int enumerateImpl(T t) {
        if (t == null) {
            return 0;
        }
        int i = this.myNumbers.getInt(t);
        if (i != 0) {
            return i;
        }
        int i2 = this.myNextNumber;
        this.myNextNumber = i2 + 1;
        this.myNumbers.put(t, i2);
        return -i2;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        ObjectIterator<Object2IntMap.Entry<T>> it = this.myNumbers.object2IntEntrySet().iterator();
        while (it.hasNext()) {
            Object2IntMap.Entry<T> next = it.next();
            sb.append(next.getIntValue()).append(": ").append(next.getKey()).append("\n");
        }
        return sb.toString();
    }
}
