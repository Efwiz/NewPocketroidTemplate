package com.esotericsoftware.kryo.util;

/* JADX INFO: loaded from: classes8.dex */
public class IdentityObjectIntMap<K> extends ObjectIntMap<K> {
    public IdentityObjectIntMap() {
    }

    public IdentityObjectIntMap(int i) {
        super(i);
    }

    public IdentityObjectIntMap(int i, float f) {
        super(i, f);
    }

    public IdentityObjectIntMap(IdentityObjectIntMap<K> identityObjectIntMap) {
        super(identityObjectIntMap);
    }

    @Override // com.esotericsoftware.kryo.util.ObjectIntMap
    protected int place(K k) {
        return (int) ((((long) System.identityHashCode(k)) * (-7046029254386353131L)) >>> this.shift);
    }

    @Override // com.esotericsoftware.kryo.util.ObjectIntMap
    int locateKey(K k) {
        if (k == null) {
            throw new IllegalArgumentException("key cannot be null.");
        }
        K[] kArr = this.keyTable;
        int iPlace = place(k);
        while (true) {
            K k2 = kArr[iPlace];
            if (k2 == null) {
                return -(iPlace + 1);
            }
            if (k2 == k) {
                return iPlace;
            }
            iPlace = (iPlace + 1) & this.mask;
        }
    }

    @Override // com.esotericsoftware.kryo.util.ObjectIntMap
    public int hashCode() {
        int iIdentityHashCode = this.size;
        K[] kArr = this.keyTable;
        int[] iArr = this.valueTable;
        int length = kArr.length;
        for (int i = 0; i < length; i++) {
            K k = kArr[i];
            if (k != null) {
                iIdentityHashCode += System.identityHashCode(k) + iArr[i];
            }
        }
        return iIdentityHashCode;
    }
}
