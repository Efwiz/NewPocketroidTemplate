package org.jetbrains.kotlin.com.intellij.util.keyFMap;

import com.google.firebase.crashlytics.internal.metadata.UserMetadata;
import com.huawei.hms.framework.common.ContainerUtils;
import com.reandroid.arsc.value.CompoundEntry;
import java.util.Arrays;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtil;

/* JADX INFO: loaded from: classes3.dex */
final class ArrayBackedFMap implements KeyFMap {
    private final int[] keys;
    private final Object[] values;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 4 || i == 10 || i == 6 || i == 7) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 4 || i == 10 || i == 6 || i == 7) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = UserMetadata.KEYDATA_FILENAME;
                break;
            case 2:
            case 5:
            case 8:
                objArr[0] = ASTExpr.DEFAULT_MAP_KEY_NAME;
                break;
            case 3:
                objArr[0] = "value";
                break;
            case 4:
            case 6:
            case 7:
            case 10:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/util/keyFMap/ArrayBackedFMap";
                break;
            case 9:
                objArr[0] = "indexes";
                break;
            case 11:
                objArr[0] = "o";
                break;
            default:
                objArr[0] = CompoundEntry.NAME_values;
                break;
        }
        if (i == 4) {
            objArr[1] = "plus";
        } else if (i == 10) {
            objArr[1] = "getKeysByIndices";
        } else if (i == 6 || i == 7) {
            objArr[1] = "minus";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/keyFMap/ArrayBackedFMap";
        }
        switch (i) {
            case 2:
            case 3:
                objArr[2] = "plus";
                break;
            case 4:
            case 6:
            case 7:
            case 10:
                break;
            case 5:
                objArr[2] = "minus";
                break;
            case 8:
                objArr[2] = "get";
                break;
            case 9:
                objArr[2] = "getKeysByIndices";
                break;
            case 11:
                objArr[2] = "equalsByReference";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 4 && i != 10 && i != 6 && i != 7) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.keyFMap.KeyFMap
    public boolean isEmpty() {
        return false;
    }

    ArrayBackedFMap(int[] iArr, Object[] objArr) {
        if (objArr == null) {
            $$$reportNull$$$0(0);
        }
        if (iArr == null) {
            $$$reportNull$$$0(1);
        }
        this.keys = iArr;
        this.values = objArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.keyFMap.KeyFMap
    public <V> KeyFMap plus(Key<V> key, V v) {
        if (key == null) {
            $$$reportNull$$$0(2);
        }
        if (v == null) {
            $$$reportNull$$$0(3);
        }
        int iHashCode = key.hashCode();
        int iIndexOf = indexOf(iHashCode);
        if (iIndexOf >= 0) {
            Object[] objArr = this.values;
            if (objArr[iIndexOf] == v) {
                return this;
            }
            Object[] objArr2 = (Object[]) objArr.clone();
            objArr2[iIndexOf] = v;
            return new ArrayBackedFMap(this.keys, objArr2);
        }
        if (size() < 8) {
            int i = (-iIndexOf) - 1;
            return new ArrayBackedFMap(ArrayUtil.insert(this.keys, i, iHashCode), ArrayUtil.insert((V[]) this.values, i, v));
        }
        return new MapBackedFMap(this.keys, iHashCode, this.values, v);
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.keyFMap.KeyFMap
    public int size() {
        return this.keys.length;
    }

    private int indexOf(int i) {
        int i2;
        int i3 = 0;
        while (true) {
            int[] iArr = this.keys;
            if (i3 < iArr.length) {
                int i4 = iArr[i3];
                if (i4 == i) {
                    return i3;
                }
                if (i4 > i) {
                    i2 = -i3;
                    break;
                }
                i3++;
            } else {
                i2 = -iArr.length;
                break;
            }
        }
        return i2 - 1;
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.keyFMap.KeyFMap
    public KeyFMap minus(Key<?> key) {
        if (key == null) {
            $$$reportNull$$$0(5);
        }
        int iIndexOf = indexOf(key.hashCode());
        if (iIndexOf < 0) {
            return this;
        }
        if (size() == 3) {
            int i = (2 - iIndexOf) / 2;
            int i2 = 3 - ((iIndexOf + 2) / 2);
            Key keyByIndex = Key.getKeyByIndex(this.keys[i]);
            Key keyByIndex2 = Key.getKeyByIndex(this.keys[i2]);
            if (keyByIndex == null && keyByIndex2 == null) {
                KeyFMap keyFMap = EMPTY_MAP;
                if (keyFMap == null) {
                    $$$reportNull$$$0(6);
                }
                return keyFMap;
            }
            if (keyByIndex == null) {
                return new OneElementFMap(keyByIndex2, this.values[i2]);
            }
            if (keyByIndex2 == null) {
                return new OneElementFMap(keyByIndex, this.values[i]);
            }
            Object[] objArr = this.values;
            return new PairElementsFMap(keyByIndex, objArr[i], keyByIndex2, objArr[i2]);
        }
        return new ArrayBackedFMap(ArrayUtil.remove(this.keys, iIndexOf), ArrayUtil.remove(this.values, iIndexOf, ArrayUtil.OBJECT_ARRAY_FACTORY));
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.keyFMap.KeyFMap
    public <V> V get(Key<V> key) {
        if (key == null) {
            $$$reportNull$$$0(8);
        }
        int iIndexOf = indexOf(key.hashCode());
        if (iIndexOf < 0) {
            return null;
        }
        return (V) this.values[iIndexOf];
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        int i = 0;
        while (true) {
            int[] iArr = this.keys;
            if (i < iArr.length) {
                sb.append(sb.length() == 1 ? "" : ", ").append(Key.getKeyByIndex(iArr[i])).append(ContainerUtils.KEY_VALUE_DELIMITER).append(this.values[i]);
                i++;
            } else {
                return sb.append("}").toString();
            }
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.keyFMap.KeyFMap
    public Key[] getKeys() {
        return getKeysByIndices(this.keys);
    }

    static Key[] getKeysByIndices(int[] iArr) {
        if (iArr == null) {
            $$$reportNull$$$0(9);
        }
        int length = iArr.length;
        Key[] keyArr = new Key[length];
        int i = 0;
        for (int i2 : iArr) {
            Key keyByIndex = Key.getKeyByIndex(i2);
            if (keyByIndex != null) {
                keyArr[i] = keyByIndex;
                i++;
            }
        }
        if (i != length) {
            keyArr = (Key[]) Arrays.copyOf(keyArr, i);
        }
        if (keyArr == null) {
            $$$reportNull$$$0(10);
        }
        return keyArr;
    }

    public int hashCode() {
        int length = this.keys.length;
        int iHashCode = 0;
        for (int i = 0; i < length; i++) {
            iHashCode += this.keys[i] ^ this.values[i].hashCode();
        }
        return iHashCode;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ArrayBackedFMap)) {
            return false;
        }
        ArrayBackedFMap arrayBackedFMap = (ArrayBackedFMap) obj;
        if (arrayBackedFMap.size() != size()) {
            return false;
        }
        int length = this.keys.length;
        for (int i = 0; i < length; i++) {
            if (this.keys[i] != arrayBackedFMap.keys[i] || !this.values[i].equals(arrayBackedFMap.values[i])) {
                return false;
            }
        }
        return true;
    }
}
