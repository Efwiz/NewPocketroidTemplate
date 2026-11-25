package com.reandroid.dex.key;

import com.reandroid.dex.common.DexUtils;
import com.reandroid.dex.id.MethodId;
import com.reandroid.dex.smali.SmaliParseException;
import com.reandroid.dex.smali.SmaliReader;
import com.reandroid.dex.smali.SmaliWriter;
import com.reandroid.utils.CompareUtil;
import com.reandroid.utils.StringsUtil;
import com.reandroid.utils.collection.ArrayIterator;
import com.reandroid.utils.collection.CombiningIterator;
import com.reandroid.utils.collection.ComputeIterator;
import com.reandroid.utils.collection.SingleIterator;
import java.io.IOException;
import java.util.Iterator;
import java.util.function.Function;
import net.bytebuddy.description.method.MethodDescription;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: loaded from: classes11.dex */
public class MethodKey implements Key {
    private final String declaring;
    private int mParamsHash;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private final String f243name;
    private final String[] parameters;
    private final String returnType;
    public static final MethodKey STATIC_CONSTRUCTOR = new MethodKey(TypeKey.OBJECT.getTypeName(), MethodDescription.TYPE_INITIALIZER_INTERNAL_NAME, (String[]) null, TypeKey.TYPE_V.getTypeName());
    public static final MethodKey EQUALS = new MethodKey(TypeKey.OBJECT.getTypeName(), Namer.EQUALS_METHOD_NAME, new String[]{TypeKey.OBJECT.getTypeName()}, TypeKey.TYPE_Z.getTypeName());
    public static final MethodKey HASHCODE = new MethodKey(TypeKey.OBJECT.getTypeName(), "hashCode", (String[]) null, TypeKey.TYPE_I.getTypeName());
    public static final MethodKey TO_STRING = new MethodKey(TypeKey.OBJECT.getTypeName(), "toString", (String[]) null, TypeKey.STRING.getTypeName());
    public static final MethodKey CONSTRUCTOR = new MethodKey(TypeKey.OBJECT.getTypeName(), "<init>", (String[]) null, TypeKey.TYPE_V.getTypeName());
    public static final MethodKey CONSTRUCTOR_STATIC = new MethodKey(TypeKey.OBJECT.getTypeName(), MethodDescription.TYPE_INITIALIZER_INTERNAL_NAME, (String[]) null, TypeKey.TYPE_V.getTypeName());

    public MethodKey(String str, String str2, String[] strArr, String str3) {
        this.declaring = str;
        this.f243name = str2;
        this.parameters = strArr;
        this.returnType = str3;
    }

    public MethodKey(TypeKey typeKey, String str, String[] strArr, String str2) {
        this(typeKey.getTypeName(), str, strArr, str2);
    }

    public MethodKey(TypeKey typeKey, String str, String[] strArr, TypeKey typeKey2) {
        this(typeKey.getTypeName(), str, strArr, typeKey2.getTypeName());
    }

    public int getRegister(int i) {
        return getProtoKey().getRegister(i);
    }

    public MethodKey changeDeclaring(TypeKey typeKey) {
        return changeDeclaring(typeKey.getTypeName());
    }

    public MethodKey changeDeclaring(String str) {
        return str.equals(getDeclaringName()) ? this : new MethodKey(str, getName(), getParameterNames(), getReturnTypeName());
    }

    public MethodKey changeName(String str) {
        return str.equals(getName()) ? this : new MethodKey(getDeclaringName(), str, getParameterNames(), getReturnTypeName());
    }

    public MethodKey changeParameters(String[] strArr) {
        return strArr == getParameterNames() ? this : new MethodKey(getDeclaringName(), getName(), strArr, getReturnTypeName());
    }

    public MethodKey changeParameter(int i, TypeKey typeKey) {
        return changeParameter(i, typeKey.getTypeName());
    }

    public MethodKey changeParameter(int i, String str) {
        if (str.equals(getParameter(i))) {
            return this;
        }
        String[] strArr = (String[]) this.parameters.clone();
        strArr[i] = str;
        return new MethodKey(getDeclaringName(), getName(), strArr, getReturnTypeName());
    }

    public MethodKey changeReturnType(TypeKey typeKey) {
        return changeReturnType(typeKey.getTypeName());
    }

    public MethodKey changeReturnType(String str) {
        return str.equals(getReturnTypeName()) ? this : new MethodKey(getDeclaringName(), getName(), getParameterNames(), str);
    }

    public MethodKey removeParameter(int i) {
        return new MethodKey(getDeclaringName(), getName(), getProtoKey().removeParameter(i).getParameterNames(), getReturnTypeName());
    }

    @Override // com.reandroid.dex.key.Key
    public TypeKey getDeclaring() {
        return new TypeKey(getDeclaringName());
    }

    public StringKey getNameKey() {
        return new StringKey(getName());
    }

    public ProtoKey getProtoKey() {
        return new ProtoKey(getParameterNames(), getReturnTypeName());
    }

    public TypeKey getReturnType() {
        return new TypeKey(getReturnTypeName());
    }

    public String getDeclaringName() {
        return this.declaring;
    }

    public String getName() {
        return this.f243name;
    }

    public String[] getParameterNames() {
        return this.parameters;
    }

    public Iterator<TypeKey> getParameters() {
        return ComputeIterator.of(ArrayIterator.of(getParameterNames()), new MethodKey$$ExternalSyntheticLambda0());
    }

    public int getParametersCount() {
        String[] parameterNames = getParameterNames();
        if (parameterNames != null) {
            return parameterNames.length;
        }
        return 0;
    }

    public String getParameter(int i) {
        return getParameterNames()[i];
    }

    public TypeKey getParameterType(int i) {
        return TypeKey.create(getParameter(i));
    }

    public String getReturnTypeName() {
        return this.returnType;
    }

    public int getParameterRegistersCount() {
        return getProtoKey().getParameterRegistersCount();
    }

    @Override // com.reandroid.dex.key.Key
    public Iterator<Key> mentionedKeys() {
        return CombiningIterator.singleTwo(this, CombiningIterator.singleOne(getDeclaring(), SingleIterator.of(getNameKey())), getProtoKey().mentionedKeys());
    }

    public MethodKey replaceTypes(Function<TypeKey, TypeKey> function) {
        TypeKey declaring = getDeclaring();
        MethodKey methodKeyChangeDeclaring = changeDeclaring(declaring.changeTypeName(function.apply(declaring)));
        TypeKey returnType = getReturnType();
        MethodKey methodKeyChangeReturnType = methodKeyChangeDeclaring.changeReturnType(returnType.changeTypeName(function.apply(returnType)));
        int parametersCount = getParametersCount();
        for (int i = 0; i < parametersCount; i++) {
            TypeKey parameterType = getParameterType(i);
            methodKeyChangeReturnType = methodKeyChangeReturnType.changeParameter(i, parameterType.changeTypeName(function.apply(parameterType)));
        }
        return methodKeyChangeReturnType;
    }

    @Override // com.reandroid.dex.key.Key
    public Key replaceKey(Key key, Key key2) {
        if (key.equals(this)) {
            return key2;
        }
        MethodKey methodKeyChangeDeclaring = key.equals(getDeclaring()) ? changeDeclaring((TypeKey) key2) : this;
        if (key.equals(methodKeyChangeDeclaring.getReturnType())) {
            methodKeyChangeDeclaring = methodKeyChangeDeclaring.changeReturnType((TypeKey) key2);
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
        return methodKeyChangeDeclaring;
    }

    @Override // com.reandroid.dex.key.Key, com.reandroid.dex.smali.SmaliFormat
    public void append(SmaliWriter smaliWriter) throws IOException {
        getDeclaring().append(smaliWriter);
        smaliWriter.append("->");
        smaliWriter.append((CharSequence) getName());
        getProtoKey().append(smaliWriter);
    }

    @Override // java.lang.Comparable
    public int compareTo(Object obj) {
        return compareTo(obj, true);
    }

    public int compareTo(Object obj, boolean z) {
        int iCompare;
        if (obj == null) {
            return -1;
        }
        MethodKey methodKey = (MethodKey) obj;
        if (z && (iCompare = CompareUtil.compare(getDeclaringName(), methodKey.getDeclaringName())) != 0) {
            return iCompare;
        }
        int iCompare2 = CompareUtil.compare(getName(), methodKey.getName());
        if (iCompare2 != 0) {
            return iCompare2;
        }
        int iCompare3 = CompareUtil.compare(getParameterNames(), methodKey.getParameterNames());
        return iCompare3 != 0 ? iCompare3 : CompareUtil.compare(getReturnTypeName(), methodKey.getReturnTypeName());
    }

    public boolean equalsIgnoreDeclaring(MethodKey methodKey) {
        if (methodKey == null) {
            return false;
        }
        if (methodKey == this) {
            return true;
        }
        if (KeyUtil.matches(getName(), methodKey.getName()) && getNameParamsHashCode() == methodKey.getNameParamsHashCode() && CompareUtil.compare(getParameterNames(), methodKey.getParameterNames()) == 0) {
            return KeyUtil.matches(getReturnTypeName(), methodKey.getReturnTypeName());
        }
        return false;
    }

    public boolean equalsIgnoreReturnType(MethodKey methodKey) {
        if (methodKey == null) {
            return false;
        }
        if (methodKey == this) {
            return true;
        }
        return KeyUtil.matches(getDeclaringName(), methodKey.getDeclaringName()) && KeyUtil.matches(getName(), methodKey.getName()) && getNameParamsHashCode() == methodKey.getNameParamsHashCode() && CompareUtil.compare(getParameterNames(), methodKey.getParameterNames()) == 0;
    }

    public boolean equalsNameAndParameters(MethodKey methodKey) {
        if (methodKey == null) {
            return false;
        }
        if (methodKey == this) {
            return true;
        }
        return KeyUtil.matches(getName(), methodKey.getName()) && getNameParamsHashCode() == methodKey.getNameParamsHashCode() && CompareUtil.compare(getParameterNames(), methodKey.getParameterNames()) == 0;
    }

    public boolean equalsIgnoreName(MethodKey methodKey) {
        if (methodKey == null) {
            return false;
        }
        if (methodKey == this) {
            return true;
        }
        if (KeyUtil.matches(getDeclaringName(), methodKey.getDeclaringName()) && CompareUtil.compare(getParameterNames(), methodKey.getParameterNames()) == 0) {
            return KeyUtil.matches(getReturnTypeName(), methodKey.getReturnTypeName());
        }
        return false;
    }

    public boolean equalsName(MethodKey methodKey) {
        if (methodKey == null) {
            return false;
        }
        if (methodKey == this) {
            return true;
        }
        return KeyUtil.matches(getName(), methodKey.getName());
    }

    public boolean equalsName(String str) {
        return KeyUtil.matches(getName(), str);
    }

    public boolean equalsProto(MethodKey methodKey) {
        if (methodKey == null) {
            return false;
        }
        if (methodKey == this) {
            return true;
        }
        if (CompareUtil.compare(getParameterNames(), methodKey.getParameterNames()) != 0) {
            return false;
        }
        return KeyUtil.matches(getReturnTypeName(), methodKey.getReturnTypeName());
    }

    public boolean equalsDeclaring(String str) {
        return KeyUtil.matches(getDeclaringName(), str);
    }

    public boolean equalsDeclaring(TypeKey typeKey) {
        if (typeKey == null) {
            return false;
        }
        return KeyUtil.matches(getDeclaringName(), typeKey.getTypeName());
    }

    public boolean equalsDeclaring(MethodKey methodKey) {
        if (methodKey == null) {
            return false;
        }
        if (methodKey == this) {
            return true;
        }
        return KeyUtil.matches(getDeclaringName(), methodKey.getDeclaringName());
    }

    public boolean equalsReturnType(String str) {
        return KeyUtil.matches(getReturnTypeName(), str);
    }

    public boolean equalsReturnType(TypeKey typeKey) {
        if (typeKey == null) {
            return false;
        }
        return KeyUtil.matches(getReturnTypeName(), typeKey.getTypeName());
    }

    public boolean equalsReturnType(MethodKey methodKey) {
        if (methodKey == null) {
            return false;
        }
        if (methodKey == this) {
            return true;
        }
        return KeyUtil.matches(getReturnTypeName(), methodKey.getReturnTypeName());
    }

    public boolean equals(Object obj) {
        return equals(obj, true, true);
    }

    public boolean equals(Object obj, boolean z, boolean z2) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MethodKey)) {
            return false;
        }
        MethodKey methodKey = (MethodKey) obj;
        if (getNameParamsHashCode() != methodKey.getNameParamsHashCode() || !KeyUtil.matches(getName(), methodKey.getName()) || CompareUtil.compare(getParameterNames(), methodKey.getParameterNames()) != 0) {
            return false;
        }
        if (z && !KeyUtil.matches(getDeclaringName(), methodKey.getDeclaringName())) {
            return false;
        }
        if (z2) {
            return KeyUtil.matches(getReturnTypeName(), methodKey.getReturnTypeName());
        }
        return true;
    }

    public int hashCode() {
        String declaringName = getDeclaringName();
        int iHashCode = ((declaringName != null ? 1 + declaringName.hashCode() : 1) * 31) + getNameParamsHashCode();
        String returnTypeName = getReturnTypeName();
        int i = iHashCode * 31;
        return returnTypeName != null ? i + returnTypeName.hashCode() : i;
    }

    private int getNameParamsHashCode() {
        int i = this.mParamsHash;
        if (i != 0) {
            return i;
        }
        int iHashCode = getName().hashCode() + 31;
        String[] parameterNames = getParameterNames();
        if (parameterNames != null) {
            for (String str : parameterNames) {
                iHashCode = (iHashCode * 31) + str.hashCode();
            }
        }
        this.mParamsHash = iHashCode;
        return iHashCode;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        String declaringName = getDeclaringName();
        if (declaringName != null) {
            sb.append(declaringName);
            sb.append("->");
        }
        sb.append(getName());
        sb.append('(');
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

    public static MethodKey parse(String str) {
        int iIndexOf;
        if (str == null) {
            return null;
        }
        String strTrim = str.trim();
        if (strTrim.length() < 6 || (!(strTrim.charAt(0) == 'L' || strTrim.charAt(0) == '[') || (iIndexOf = strTrim.indexOf(";->")) < 0)) {
            return null;
        }
        String strSubstring = strTrim.substring(0, iIndexOf + 1);
        String strSubstring2 = strTrim.substring(iIndexOf + 3);
        int iIndexOf2 = strSubstring2.indexOf(40);
        if (iIndexOf2 < 0) {
            return null;
        }
        String strSubstring3 = strSubstring2.substring(0, iIndexOf2);
        String strSubstring4 = strSubstring2.substring(iIndexOf2 + 1);
        int iIndexOf3 = strSubstring4.indexOf(41);
        if (iIndexOf3 < 0) {
            return null;
        }
        String[] strArrSplitParameters = DexUtils.splitParameters(strSubstring4.substring(0, iIndexOf3));
        String strSubstring5 = strSubstring4.substring(iIndexOf3 + 1);
        return new MethodKey(strSubstring, strSubstring3, strArrSplitParameters, StringsUtil.isEmpty(strSubstring5) ? null : strSubstring5);
    }

    public static MethodKey create(MethodId methodId) {
        String name2;
        TypeKey defining = methodId.getDefining();
        if (defining == null || (name2 = methodId.getName()) == null) {
            return null;
        }
        return new MethodKey(defining, name2, methodId.getParameterNames(), methodId.getReturnTypeName());
    }

    public static MethodKey read(SmaliReader smaliReader) throws IOException {
        TypeKey typeKey = TypeKey.read(smaliReader);
        smaliReader.skipWhitespacesOrComment();
        SmaliParseException.expect(smaliReader, '-');
        SmaliParseException.expect(smaliReader, '>');
        smaliReader.skipWhitespacesOrComment();
        String escapedString = smaliReader.readEscapedString('(');
        ProtoKey protoKey = ProtoKey.read(smaliReader);
        return new MethodKey(typeKey.getTypeName(), escapedString, protoKey.getParameterNames(), protoKey.getReturnTypeName());
    }
}
