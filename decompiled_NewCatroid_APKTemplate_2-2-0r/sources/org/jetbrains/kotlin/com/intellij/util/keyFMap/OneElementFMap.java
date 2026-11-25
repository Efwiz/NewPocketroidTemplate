package org.jetbrains.kotlin.com.intellij.util.keyFMap;

import com.huawei.hms.framework.common.ContainerUtils;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;

/* JADX INFO: loaded from: classes3.dex */
final class OneElementFMap<V> implements KeyFMap {
    private final Key<V> myKey;
    private final V myValue;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 5 || i == 7) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 5 || i == 7) ? 2 : 3];
        if (i == 1 || i == 3) {
            objArr[0] = "value";
        } else if (i == 5 || i == 7) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/util/keyFMap/OneElementFMap";
        } else if (i != 8) {
            objArr[0] = ASTExpr.DEFAULT_MAP_KEY_NAME;
        } else {
            objArr[0] = "o";
        }
        if (i == 5) {
            objArr[1] = "minus";
        } else if (i != 7) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/keyFMap/OneElementFMap";
        } else {
            objArr[1] = "getKeys";
        }
        switch (i) {
            case 2:
            case 3:
                objArr[2] = "plus";
                break;
            case 4:
                objArr[2] = "minus";
                break;
            case 5:
            case 7:
                break;
            case 6:
                objArr[2] = "get";
                break;
            case 8:
                objArr[2] = "equalsByReference";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 5 && i != 7) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.keyFMap.KeyFMap
    public boolean isEmpty() {
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.keyFMap.KeyFMap
    public int size() {
        return 1;
    }

    OneElementFMap(Key<V> key, V v) {
        if (key == null) {
            $$$reportNull$$$0(0);
        }
        if (v == null) {
            $$$reportNull$$$0(1);
        }
        this.myKey = key;
        this.myValue = v;
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.keyFMap.KeyFMap
    public <T> KeyFMap plus(Key<T> key, T t) {
        if (key == null) {
            $$$reportNull$$$0(2);
        }
        if (t == null) {
            $$$reportNull$$$0(3);
        }
        if (this.myKey == key) {
            return t == this.myValue ? this : new OneElementFMap(key, t);
        }
        return new PairElementsFMap(this.myKey, this.myValue, key, t);
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.keyFMap.KeyFMap
    public KeyFMap minus(Key<?> key) {
        if (key == null) {
            $$$reportNull$$$0(4);
        }
        KeyFMap keyFMap = key == this.myKey ? KeyFMap.EMPTY_MAP : this;
        if (keyFMap == null) {
            $$$reportNull$$$0(5);
        }
        return keyFMap;
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.keyFMap.KeyFMap
    public <T> T get(Key<T> key) {
        if (key == null) {
            $$$reportNull$$$0(6);
        }
        if (this.myKey == key) {
            return this.myValue;
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.keyFMap.KeyFMap
    public Key[] getKeys() {
        return new Key[]{this.myKey};
    }

    public String toString() {
        return "{" + this.myKey + ContainerUtils.KEY_VALUE_DELIMITER + this.myValue + "}";
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof OneElementFMap)) {
            return false;
        }
        OneElementFMap oneElementFMap = (OneElementFMap) obj;
        return this.myKey == oneElementFMap.myKey && this.myValue.equals(oneElementFMap.myValue);
    }

    public int hashCode() {
        return this.myKey.hashCode() ^ this.myValue.hashCode();
    }
}
