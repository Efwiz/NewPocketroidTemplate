package com.badlogic.gdx.utils;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/utils/IdentityMap.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class IdentityMap<K, V> extends ObjectMap<K, V> {
    public IdentityMap() {
    }

    public IdentityMap(int i) {
        super(i);
    }

    public IdentityMap(int i, float f) {
        super(i, f);
    }

    public IdentityMap(IdentityMap<K, V> identityMap) {
        super(identityMap);
    }

    @Override // com.badlogic.gdx.utils.ObjectMap
    protected int place(K k) {
        return (int) ((((long) System.identityHashCode(k)) * (-7046029254386353131L)) >>> this.shift);
    }

    @Override // com.badlogic.gdx.utils.ObjectMap
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

    @Override // com.badlogic.gdx.utils.ObjectMap
    public int hashCode() {
        int iIdentityHashCode = this.size;
        K[] kArr = this.keyTable;
        V[] vArr = this.valueTable;
        int length = kArr.length;
        for (int i = 0; i < length; i++) {
            K k = kArr[i];
            if (k != null) {
                iIdentityHashCode += System.identityHashCode(k);
                V v = vArr[i];
                if (v != null) {
                    iIdentityHashCode += v.hashCode();
                }
            }
        }
        return iIdentityHashCode;
    }
}
