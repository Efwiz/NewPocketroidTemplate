package com.reandroid.dex.key;

import com.reandroid.dex.id.ProtoId;
import com.reandroid.dex.smali.SmaliParseException;
import com.reandroid.dex.smali.SmaliReader;
import com.reandroid.dex.smali.SmaliWriter;
import com.reandroid.utils.CompareUtil;
import com.reandroid.utils.collection.ArrayCollection;
import com.reandroid.utils.collection.ArrayIterator;
import com.reandroid.utils.collection.CombiningIterator;
import com.reandroid.utils.collection.ComputeIterator;
import com.reandroid.utils.collection.SingleIterator;
import java.io.IOException;
import java.util.Iterator;
import java.util.Objects;

/* JADX INFO: loaded from: classes11.dex */
public class ProtoKey implements Key {
    private int mHash;
    private final String[] parameters;
    private final String returnType;

    public ProtoKey(String[] strArr, String str) {
        this.parameters = strArr;
        this.returnType = str;
    }

    public int getRegister(int i) {
        int parametersCount = getParametersCount();
        if (i >= parametersCount) {
            i = parametersCount;
        }
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3++) {
            i2 = getParameter(i3).isWide() ? i2 + 2 : i2 + 1;
        }
        return i2;
    }

    public int getParameterIndex(int i) {
        int parametersCount = getParametersCount();
        int i2 = 0;
        for (int i3 = 0; i3 < parametersCount; i3++) {
            if (i2 == i) {
                return i3;
            }
            i2 = getParameter(i3).isWide() ? i2 + 2 : i2 + 1;
        }
        return -1;
    }

    public ProtoKey removeParameter(int i) {
        TypeListKey parameterListKey = getParameterListKey();
        if (parameterListKey != null) {
            parameterListKey = parameterListKey.remove(i);
        }
        return create(parameterListKey, getReturnTypeName());
    }

    public ProtoKey changeReturnType(TypeKey typeKey) {
        return changeReturnType(typeKey.getTypeName());
    }

    public ProtoKey changeReturnType(String str) {
        return str.equals(getReturnTypeName()) ? this : new ProtoKey(getParameterNames(), str);
    }

    public TypeListKey getParameterListKey() {
        return TypeListKey.create(getParameterNames());
    }

    public TypeKey getReturnType() {
        return new TypeKey(getReturnTypeName());
    }

    public String getReturnTypeName() {
        return this.returnType;
    }

    public int getParametersCount() {
        String[] parameterNames = getParameterNames();
        if (parameterNames != null) {
            return parameterNames.length;
        }
        return 0;
    }

    public String[] getParameterNames() {
        return this.parameters;
    }

    public String getParameterName(int i) {
        return getParameterNames()[i];
    }

    public TypeKey getParameter(int i) {
        return TypeKey.create(getParameterName(i));
    }

    public Iterator<TypeKey> getParameters() {
        return ComputeIterator.of(ArrayIterator.of(getParameterNames()), new MethodKey$$ExternalSyntheticLambda0());
    }

    public String getShorty() {
        StringBuilder sb = new StringBuilder();
        sb.append(toShorty(getReturnTypeName()));
        String[] parameterNames = getParameterNames();
        if (parameterNames != null) {
            for (String str : parameterNames) {
                sb.append(toShorty(str));
            }
        }
        return sb.toString();
    }

    public int getParameterRegistersCount() {
        Iterator<TypeKey> parameters = getParameters();
        int i = 0;
        while (parameters.hasNext()) {
            i = parameters.next().isWide() ? i + 2 : i + 1;
        }
        return i;
    }

    @Override // com.reandroid.dex.key.Key
    public Iterator<Key> mentionedKeys() {
        return CombiningIterator.singleThree(this, SingleIterator.of(StringKey.create(getShorty())), getParameters(), SingleIterator.of(getReturnType()));
    }

    @Override // com.reandroid.dex.key.Key
    public Key replaceKey(Key key, Key key2) {
        if (key.equals(this)) {
            return key2;
        }
        ProtoKey protoKeyChangeReturnType = key.equals(getReturnType()) ? changeReturnType((TypeKey) key2) : this;
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
        return protoKeyChangeReturnType;
    }

    @Override // com.reandroid.dex.key.Key, com.reandroid.dex.smali.SmaliFormat
    public void append(SmaliWriter smaliWriter) throws IOException {
        smaliWriter.append('(');
        smaliWriter.appendAll(getParameters(), false);
        smaliWriter.append(')');
        getReturnType().append(smaliWriter);
    }

    @Override // java.lang.Comparable
    public int compareTo(Object obj) {
        if (obj == null) {
            return -1;
        }
        ProtoKey protoKey = (ProtoKey) obj;
        int iCompare = CompareUtil.compare(getParameterNames(), protoKey.getParameterNames());
        return iCompare != 0 ? iCompare : CompareUtil.compare(getReturnTypeName(), protoKey.getReturnTypeName());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ProtoKey)) {
            return false;
        }
        ProtoKey protoKey = (ProtoKey) obj;
        return Objects.equals(getReturnTypeName(), protoKey.getReturnTypeName()) && CompareUtil.compare(getParameterNames(), protoKey.getParameterNames()) == 0;
    }

    public int hashCode() {
        int i = this.mHash;
        if (i != 0) {
            return i;
        }
        String returnTypeName = getReturnTypeName();
        int iHashCode = returnTypeName != null ? 1 + returnTypeName.hashCode() : 1;
        String[] parameterNames = getParameterNames();
        if (parameterNames != null) {
            for (String str : parameterNames) {
                iHashCode = (iHashCode * 31) + str.hashCode();
            }
        }
        this.mHash = iHashCode;
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
        String returnTypeName = getReturnTypeName();
        if (returnTypeName != null) {
            sb.append(returnTypeName);
        }
        return sb.toString();
    }

    public static ProtoKey create(ProtoId protoId) {
        TypeKey returnType = protoId.getReturnType();
        if (returnType == null) {
            return null;
        }
        return new ProtoKey(protoId.getParameterNames(), returnType.getTypeName());
    }

    public static ProtoKey create(TypeListKey typeListKey, String str) {
        if (str == null) {
            return null;
        }
        return new ProtoKey(typeListKey != null ? typeListKey.getParameterNames() : null, str);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static ProtoKey read(SmaliReader smaliReader) throws IOException {
        String[] strArr;
        smaliReader.skipWhitespacesOrComment();
        SmaliParseException.expect(smaliReader, '(');
        smaliReader.skipWhitespacesOrComment();
        ArrayCollection arrayCollection = new ArrayCollection();
        while (!smaliReader.finished() && smaliReader.get() != 41) {
            arrayCollection.add(TypeKey.read(smaliReader));
            smaliReader.skipWhitespacesOrComment();
        }
        SmaliParseException.expect(smaliReader, ')');
        TypeKey typeKey = TypeKey.read(smaliReader);
        int size = arrayCollection.size();
        if (size == 0) {
            strArr = null;
        } else {
            String[] strArr2 = new String[size];
            for (int i = 0; i < size; i++) {
                strArr2[i] = ((TypeKey) arrayCollection.get(i)).getTypeName();
            }
            strArr = strArr2;
        }
        return new ProtoKey(strArr, typeKey.getTypeName());
    }

    private static char toShorty(String str) {
        if (str.length() == 1) {
            return str.charAt(0);
        }
        if (str.length() != 0) {
            return 'L';
        }
        throw new RuntimeException();
    }
}
