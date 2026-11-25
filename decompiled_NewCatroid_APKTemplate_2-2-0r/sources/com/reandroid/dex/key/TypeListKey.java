package com.reandroid.dex.key;

import com.reandroid.dex.data.TypeList;
import com.reandroid.utils.CompareUtil;
import com.reandroid.utils.collection.ArrayIterator;
import com.reandroid.utils.collection.ComputeIterator;
import java.util.Iterator;

/* JADX INFO: loaded from: classes11.dex */
public class TypeListKey implements Key, Iterable<String> {
    private final String[] parameters;

    public TypeListKey(String[] strArr) {
        this.parameters = strArr;
    }

    public TypeListKey remove(int i) {
        String[] parameterNames = getParameterNames();
        if (parameterNames == null) {
            return this;
        }
        int length = parameterNames.length;
        if (i < 0 || i >= length) {
            return this;
        }
        String[] strArr = length == 1 ? null : new String[length - 1];
        int i2 = 0;
        for (int i3 = 0; i3 < length; i3++) {
            if (i3 != i) {
                strArr[i2] = parameterNames[i3];
                i2++;
            }
        }
        return new TypeListKey(strArr);
    }

    public TypeListKey add(String str) {
        String[] parameterNames = getParameterNames();
        int i = 0;
        int length = parameterNames != null ? parameterNames.length : 0;
        String[] strArr = new String[length + 1];
        if (parameterNames != null) {
            int i2 = 0;
            while (i < length) {
                strArr[i2] = parameterNames[i];
                i2++;
                i++;
            }
            i = i2;
        }
        strArr[i] = str;
        return new TypeListKey(strArr);
    }

    public String[] getParameterNames() {
        return this.parameters;
    }

    public int indexOf(String str) {
        String[] parameterNames;
        if (str != null && (parameterNames = getParameterNames()) != null) {
            int length = parameterNames.length;
            for (int i = 0; i < length; i++) {
                if (str.equals(parameterNames[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    public int indexOf(TypeKey typeKey) {
        if (typeKey == null) {
            return -1;
        }
        int size = size();
        for (int i = 0; i < size; i++) {
            if (typeKey.equals(getType(i))) {
                return i;
            }
        }
        return -1;
    }

    public int size() {
        String[] parameterNames = getParameterNames();
        if (parameterNames != null) {
            return parameterNames.length;
        }
        return 0;
    }

    public String get(int i) {
        return getParameterNames()[i];
    }

    public TypeKey getType(int i) {
        return TypeKey.create(get(i));
    }

    public Iterator<TypeKey> getTypes() {
        return ComputeIterator.of(iterator(), new MethodKey$$ExternalSyntheticLambda0());
    }

    @Override // java.lang.Iterable
    public Iterator<String> iterator() {
        return ArrayIterator.of(getParameterNames());
    }

    @Override // com.reandroid.dex.key.Key
    public Iterator<TypeKey> mentionedKeys() {
        return getTypes();
    }

    @Override // com.reandroid.dex.key.Key
    public Key replaceKey(Key key, Key key2) {
        if (key.equals(this)) {
            return key2;
        }
        String[] parameterNames = getParameterNames();
        if (parameterNames != null && (key instanceof TypeKey)) {
            TypeKey typeKey = (TypeKey) key;
            String typeName = ((TypeKey) key2).getTypeName();
            int length = parameterNames.length;
            for (int i = 0; i < length; i++) {
                if (typeKey.equals(new TypeKey(parameterNames[i]))) {
                    parameterNames[i] = typeName;
                }
            }
        }
        return this;
    }

    @Override // java.lang.Comparable
    public int compareTo(Object obj) {
        if (obj == null) {
            return -1;
        }
        return CompareUtil.compare(getParameterNames(), ((TypeListKey) obj).getParameterNames());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof TypeListKey) && CompareUtil.compare(getParameterNames(), ((TypeListKey) obj).getParameterNames()) == 0;
    }

    public int hashCode() {
        String[] parameterNames = getParameterNames();
        int iHashCode = 1;
        if (parameterNames != null) {
            for (String str : parameterNames) {
                iHashCode = (iHashCode * 31) + str.hashCode();
            }
        }
        return iHashCode;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("(");
        String[] parameterNames = getParameterNames();
        if (parameterNames != null) {
            for (String str : parameterNames) {
                sb.append(str);
            }
        }
        sb.append(')');
        return sb.toString();
    }

    public static TypeListKey create(TypeList typeList) {
        return create(typeList.getNames());
    }

    public static TypeListKey create(String[] strArr) {
        if (strArr == null || strArr.length == 0) {
            return null;
        }
        return new TypeListKey(strArr);
    }
}
