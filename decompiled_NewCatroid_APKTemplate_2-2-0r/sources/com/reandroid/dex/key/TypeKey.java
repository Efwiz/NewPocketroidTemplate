package com.reandroid.dex.key;

import androidx.exifinterface.media.ExifInterface;
import com.reandroid.dex.common.DexUtils;
import com.reandroid.dex.smali.SmaliParseException;
import com.reandroid.dex.smali.SmaliReader;
import com.reandroid.dex.smali.SmaliWriter;
import com.reandroid.utils.CompareUtil;
import com.reandroid.utils.StringsUtil;
import com.reandroid.utils.collection.CombiningIterator;
import com.reandroid.utils.collection.EmptyIterator;
import com.reandroid.utils.collection.SingleIterator;
import java.io.IOException;
import java.util.Iterator;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import okhttp3.HttpUrl;
import org.apache.commons.compress.archivers.tar.TarConstants;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;

/* JADX INFO: loaded from: classes11.dex */
public class TypeKey implements Key {
    private String simpleName;
    private final String typeName;
    public static final TypeKey NULL = new TypeKey(TarConstants.VERSION_POSIX) { // from class: com.reandroid.dex.key.TypeKey.2
        @Override // com.reandroid.dex.key.TypeKey, com.reandroid.dex.key.Key, com.reandroid.dex.smali.SmaliFormat
        public void append(SmaliWriter smaliWriter) {
        }

        @Override // com.reandroid.dex.key.TypeKey, com.reandroid.dex.key.Key
        public TypeKey getDeclaring() {
            return this;
        }

        @Override // com.reandroid.dex.key.TypeKey
        public boolean isPackage(String str) {
            return false;
        }

        @Override // com.reandroid.dex.key.TypeKey
        public boolean isPackage(String str, boolean z) {
            return false;
        }

        @Override // com.reandroid.dex.key.Key
        public boolean isPlatform() {
            return false;
        }

        @Override // com.reandroid.dex.key.TypeKey
        public boolean isPrimitive() {
            return false;
        }

        @Override // com.reandroid.dex.key.TypeKey
        public boolean isTypeArray() {
            return false;
        }

        @Override // com.reandroid.dex.key.TypeKey
        public boolean isTypeObject() {
            return false;
        }

        @Override // com.reandroid.dex.key.TypeKey, com.reandroid.dex.key.Key
        public Key replaceKey(Key key, Key key2) {
            return this;
        }

        @Override // com.reandroid.dex.key.TypeKey
        public TypeKey setArrayDimension(int i) {
            return this;
        }

        @Override // com.reandroid.dex.key.TypeKey
        public TypeKey setPackage(String str) {
            return this;
        }

        @Override // com.reandroid.dex.key.TypeKey
        public String getTypeName() {
            return StringsUtil.EMPTY;
        }

        @Override // com.reandroid.dex.key.TypeKey, com.reandroid.dex.key.Key
        public Iterator<TypeKey> mentionedKeys() {
            return EmptyIterator.of();
        }
    };
    public static final TypeKey TYPE_B = new PrimitiveTypeKey("B", "byte");
    public static final TypeKey TYPE_C = new PrimitiveTypeKey("C", PsiKeyword.CHAR);
    public static final TypeKey TYPE_D = new PrimitiveTypeKey("D", "double") { // from class: com.reandroid.dex.key.TypeKey.3
        @Override // com.reandroid.dex.key.TypeKey.PrimitiveTypeKey, com.reandroid.dex.key.TypeKey
        public boolean isWide() {
            return true;
        }
    };
    public static final TypeKey TYPE_F = new PrimitiveTypeKey("F", "float");
    public static final TypeKey TYPE_I = new PrimitiveTypeKey("I", "int");
    public static final TypeKey TYPE_J = new PrimitiveTypeKey("J", "long") { // from class: com.reandroid.dex.key.TypeKey.4
        @Override // com.reandroid.dex.key.TypeKey.PrimitiveTypeKey, com.reandroid.dex.key.TypeKey
        public boolean isWide() {
            return true;
        }
    };
    public static final TypeKey TYPE_S = new PrimitiveTypeKey(ExifInterface.LATITUDE_SOUTH, "short");
    public static final TypeKey TYPE_V = new PrimitiveTypeKey(ExifInterface.GPS_MEASUREMENT_INTERRUPTED, PsiKeyword.VOID);
    public static final TypeKey TYPE_Z = new PrimitiveTypeKey("Z", "boolean");
    public static final TypeKey CLASS = new TypeKey("Ljava/lang/Class;");
    public static final TypeKey OBJECT = new TypeKey(TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR);
    public static final TypeKey STRING = new TypeKey("Ljava/lang/String;");
    public static final TypeKey DALVIK_EnclosingClass = new TypeKey("Ldalvik/annotation/EnclosingClass;");
    public static final TypeKey DALVIK_EnclosingMethod = new TypeKey("Ldalvik/annotation/EnclosingMethod;");
    public static final TypeKey DALVIK_InnerClass = new TypeKey("Ldalvik/annotation/InnerClass;");
    public static final TypeKey DALVIK_MemberClass = new TypeKey("Ldalvik/annotation/MemberClasses;");
    public static final TypeKey DALVIK_Signature = new TypeKey("Ldalvik/annotation/Signature;");

    public TypeKey(String str) {
        this.typeName = str;
    }

    public String getTypeName() {
        return this.typeName;
    }

    public String getSourceName() {
        TypeKey typeKeyPrimitiveType;
        int arrayDimension = getArrayDimension();
        if (arrayDimension == 0) {
            String typeName = getTypeName();
            if (typeName.length() == 1 && (typeKeyPrimitiveType = PrimitiveTypeKey.primitiveType(typeName.charAt(0))) != null) {
                return typeKeyPrimitiveType.getSourceName();
            }
            return DexUtils.toSourceName(typeName);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(getDeclaring().getSourceName());
        for (int i = 0; i < arrayDimension; i++) {
            sb.append(HttpUrl.PATH_SEGMENT_ENCODE_SET_URI);
        }
        return sb.toString();
    }

    @Override // com.reandroid.dex.key.Key
    public TypeKey getDeclaring() {
        String declaringName = getDeclaringName();
        return declaringName.equals(getTypeName()) ? this : create(declaringName);
    }

    @Override // com.reandroid.dex.key.Key
    public Iterator<TypeKey> mentionedKeys() {
        Iterator<TypeKey> itOf = SingleIterator.of(this);
        return isTypeArray() ? CombiningIterator.singleOne(getDeclaring(), itOf) : itOf;
    }

    @Override // com.reandroid.dex.key.Key
    public Key replaceKey(Key key, Key key2) {
        return key.equals(this) ? key2 : this;
    }

    public String getDeclaringName() {
        return DexUtils.toDeclaringType(getTypeName());
    }

    public String getSignatureTypeName() {
        return DexUtils.toSignatureType(getTypeName());
    }

    public TypeKey setArrayDimension(int i) {
        return i == getArrayDimension() ? this : new TypeKey(getArrayType(i));
    }

    public String getArrayType(int i) {
        return DexUtils.makeArrayType(getTypeName(), i);
    }

    public int getArrayDimension() {
        return DexUtils.countArrayPrefix(getTypeName());
    }

    public boolean isTypeSignature() {
        return DexUtils.isTypeSignature(getTypeName());
    }

    public boolean isTypeArray() {
        String typeName = getTypeName();
        return typeName.length() > 1 && typeName.charAt(0) == '[';
    }

    public boolean isTypeObject() {
        return DexUtils.isTypeObject(getTypeName());
    }

    public boolean isPrimitive() {
        return DexUtils.isPrimitive(getTypeName());
    }

    public boolean isWide() {
        String typeName = getTypeName();
        if (typeName.length() != 1) {
            return false;
        }
        return typeName.equals(TYPE_D.getTypeName()) || typeName.equals(TYPE_J.getTypeName());
    }

    public String getSimpleName() {
        if (this.simpleName == null) {
            this.simpleName = DexUtils.getSimpleName(getTypeName());
        }
        return this.simpleName;
    }

    public String getSimpleInnerName() {
        return DexUtils.getSimpleInnerName(getTypeName());
    }

    public boolean isInnerName() {
        return !getSimpleName().equals(getSimpleInnerName());
    }

    public String getPackageName() {
        return DexUtils.getPackageName(getTypeName());
    }

    public String getPackageSourceName() {
        String packageName = getPackageName();
        int length = packageName.length() - 1;
        if (length < 1) {
            return StringsUtil.EMPTY;
        }
        return packageName.substring(1, length).replace('/', '.');
    }

    public TypeKey changeTypeName(String str) {
        return changeTypeName(create(str));
    }

    public TypeKey changeTypeName(TypeKey typeKey) {
        return equals(typeKey) ? this : typeKey.setArrayDimension(getArrayDimension());
    }

    public TypeKey renamePackage(String str, String str2) {
        String packageName = getPackageName();
        if (packageName.equals(str)) {
            return setPackage(packageName, str2);
        }
        int length = str.length();
        return (length == 1 || packageName.length() < length || !packageName.startsWith(str)) ? this : setPackage(packageName, packageName.replace(str, str2));
    }

    public TypeKey setPackage(String str) {
        return setPackage(getPackageName(), str);
    }

    private TypeKey setPackage(String str, String str2) {
        if (str.equals(str2)) {
            return this;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str2);
        int length = str2.length() - 1;
        if (length > 0 && str2.charAt(length) != '/') {
            sb.append('/');
        }
        sb.append(getSimpleName());
        String typeName = getTypeName();
        char cCharAt = typeName.charAt(typeName.length() - 1);
        if (cCharAt == ';' || cCharAt == '<') {
            sb.append(cCharAt);
        }
        return new TypeKey(sb.toString()).setArrayDimension(getArrayDimension());
    }

    public boolean isPackage(String str) {
        return isPackage(str, !"L".equals(str));
    }

    public boolean isPackage(String str, boolean z) {
        if (isPrimitive()) {
            return false;
        }
        String packageName = getPackageName();
        if (z) {
            return packageName.startsWith(str);
        }
        return packageName.equals(str);
    }

    public TypeKey getEnclosingClass() {
        String typeName = getTypeName();
        String parentClassName = DexUtils.getParentClassName(typeName);
        return typeName.equals(parentClassName) ? this : new TypeKey(parentClassName);
    }

    public TypeKey createInnerClass(String str) {
        String typeName = getTypeName();
        String strCreateChildClass = DexUtils.createChildClass(typeName, str);
        return typeName.equals(strCreateChildClass) ? this : new TypeKey(strCreateChildClass);
    }

    public Iterator<String> iteratePackageNames() {
        if (getTypeName().indexOf(47) < 0) {
            return EmptyIterator.of();
        }
        return new Iterator<String>(getPackageName()) { // from class: com.reandroid.dex.key.TypeKey.1

            /* JADX INFO: renamed from: name, reason: collision with root package name */
            String f244name;
            final /* synthetic */ String val$packageName;

            {
                this.val$packageName = str;
                this.f244name = str;
            }

            @Override // java.util.Iterator
            public boolean hasNext() {
                String str = this.f244name;
                return str.charAt(str.length() - 1) == '/';
            }

            @Override // java.util.Iterator
            public String next() {
                String str = this.f244name;
                String strSubstring = str;
                while (strSubstring.charAt(strSubstring.length() - 1) == '/') {
                    strSubstring = strSubstring.substring(0, strSubstring.length() - 1);
                }
                int iLastIndexOf = strSubstring.lastIndexOf(47);
                if (iLastIndexOf > 0) {
                    strSubstring = strSubstring.substring(0, iLastIndexOf + 1);
                }
                this.f244name = strSubstring;
                return str;
            }
        };
    }

    @Override // com.reandroid.dex.key.Key, com.reandroid.dex.smali.SmaliFormat
    public void append(SmaliWriter smaliWriter) throws IOException {
        smaliWriter.append((CharSequence) getTypeName());
    }

    public int compareInnerFirst(TypeKey typeKey) {
        if (equals(typeKey)) {
            return 0;
        }
        String simpleName = getSimpleName();
        String simpleName2 = typeKey.getSimpleName();
        int iDiffStart = StringsUtil.diffStart(simpleName, simpleName2);
        if (iDiffStart > 0 && simpleName.charAt(iDiffStart) == '$' && iDiffStart > simpleName.lastIndexOf(47) + 1) {
            return CompareUtil.compare(simpleName2, simpleName);
        }
        return CompareUtil.compare(getTypeName(), typeKey.getTypeName());
    }

    public boolean equalsPackage(TypeKey typeKey) {
        if (typeKey == null) {
            return false;
        }
        String strTrimStart = StringsUtil.trimStart(getTypeName(), '[');
        String strTrimStart2 = StringsUtil.trimStart(typeKey.getTypeName(), '[');
        if (strTrimStart.charAt(0) != 'L' || strTrimStart2.charAt(0) != 'L') {
            return false;
        }
        if (typeKey == this || strTrimStart.equals(strTrimStart2)) {
            return true;
        }
        int iDiffStart = StringsUtil.diffStart(strTrimStart, strTrimStart2);
        return iDiffStart >= 0 && StringsUtil.indexOfFrom(strTrimStart, iDiffStart, '/') < 0 && StringsUtil.indexOfFrom(strTrimStart2, iDiffStart, '/') < 0;
    }

    @Override // java.lang.Comparable
    public int compareTo(Object obj) {
        if (obj == null) {
            return -1;
        }
        if (obj == this) {
            return 0;
        }
        return CompareUtil.compare(getTypeName(), ((TypeKey) obj).getTypeName());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof TypeKey) {
            return getTypeName().equals(((TypeKey) obj).getTypeName());
        }
        return false;
    }

    public int hashCode() {
        return getTypeName().hashCode();
    }

    public String toString() {
        return getTypeName();
    }

    public static TypeKey parse(String str) {
        if (str == null || str.length() == 0 || str.indexOf(62) > 0 || str.indexOf(40) > 0 || str.indexOf(64) > 0) {
            return null;
        }
        if (str.indexOf(47) <= 0 && str.indexOf(59) <= 0) {
            int i = 0;
            if (str.charAt(0) != '[') {
                if (str.indexOf(46) >= 0) {
                    return create(DexUtils.toBinaryName(str));
                }
                while (str.charAt(i) == '[' && i < str.length() - 1) {
                    i++;
                }
                if (primitiveType(str.charAt(i)) != null) {
                    return create(str);
                }
                TypeKey typeKey = TYPE_B;
                if (str.equals(typeKey.getSourceName())) {
                    return typeKey;
                }
                TypeKey typeKey2 = TYPE_D;
                if (str.equals(typeKey2.getSourceName())) {
                    return typeKey2;
                }
                TypeKey typeKey3 = TYPE_F;
                if (str.equals(typeKey3.getSourceName())) {
                    return typeKey3;
                }
                TypeKey typeKey4 = TYPE_I;
                if (str.equals(typeKey4.getSourceName())) {
                    return typeKey4;
                }
                TypeKey typeKey5 = TYPE_J;
                if (str.equals(typeKey5.getSourceName())) {
                    return typeKey5;
                }
                TypeKey typeKey6 = TYPE_S;
                if (str.equals(typeKey6.getSourceName())) {
                    return typeKey6;
                }
                TypeKey typeKey7 = TYPE_V;
                if (str.equals(typeKey7.getSourceName())) {
                    return typeKey7;
                }
                TypeKey typeKey8 = TYPE_Z;
                return str.equals(typeKey8.getSourceName()) ? typeKey8 : create(DexUtils.toBinaryName(str));
            }
        }
        return create(str);
    }

    public static TypeKey create(String str) {
        int length;
        if (str == null || (length = str.length()) == 0) {
            return null;
        }
        if (length != 1) {
            return new TypeKey(str);
        }
        return primitiveType(str.charAt(0));
    }

    public static TypeKey read(SmaliReader smaliReader) throws IOException {
        smaliReader.skipWhitespacesOrComment();
        int iPosition = smaliReader.position();
        StringBuilder sb = new StringBuilder();
        while (smaliReader.get() == 91) {
            sb.append(smaliReader.readASCII());
        }
        if (smaliReader.get() != 76) {
            sb.append(smaliReader.readASCII());
        } else {
            int iIndexOfBeforeLineEnd = smaliReader.indexOfBeforeLineEnd(';');
            if (iIndexOfBeforeLineEnd < 0) {
                smaliReader.position(iPosition);
                throw new SmaliParseException("Invalid type, missing ';'", smaliReader);
            }
            sb.append(smaliReader.readString((iIndexOfBeforeLineEnd + 1) - smaliReader.position()));
        }
        TypeKey typeKeyCreate = create(sb.toString());
        if (typeKeyCreate != null) {
            return typeKeyCreate;
        }
        smaliReader.position(iPosition);
        throw new SmaliParseException("Invalid type", smaliReader);
    }

    public static TypeKey primitiveType(char c) {
        if (c == 'F') {
            return TYPE_F;
        }
        if (c == 'S') {
            return TYPE_S;
        }
        if (c == 'V') {
            return TYPE_V;
        }
        if (c == 'Z') {
            return TYPE_Z;
        }
        if (c == 'I') {
            return TYPE_I;
        }
        if (c != 'J') {
            switch (c) {
                case 'B':
                    return TYPE_B;
                case 'C':
                    return TYPE_C;
                case 'D':
                    return TYPE_D;
                default:
                    return null;
            }
        }
        return TYPE_J;
    }

    public static TypeKey parseSignature(String str) {
        if (DexUtils.isTypeOrSignature(str)) {
            return new TypeKey(str);
        }
        return null;
    }

    static class PrimitiveTypeKey extends TypeKey {
        private final String sourceName;

        @Override // com.reandroid.dex.key.TypeKey, com.reandroid.dex.key.Key
        public TypeKey getDeclaring() {
            return this;
        }

        @Override // com.reandroid.dex.key.TypeKey
        public boolean isInnerName() {
            return false;
        }

        @Override // com.reandroid.dex.key.TypeKey
        public boolean isPrimitive() {
            return true;
        }

        @Override // com.reandroid.dex.key.TypeKey
        public boolean isTypeArray() {
            return false;
        }

        @Override // com.reandroid.dex.key.TypeKey
        public boolean isTypeObject() {
            return false;
        }

        @Override // com.reandroid.dex.key.TypeKey
        public boolean isTypeSignature() {
            return false;
        }

        @Override // com.reandroid.dex.key.TypeKey
        public boolean isWide() {
            return false;
        }

        public PrimitiveTypeKey(String str, String str2) {
            super(str);
            this.sourceName = str2;
        }

        @Override // com.reandroid.dex.key.Key
        public boolean uses(Key key) {
            return equals(key);
        }

        @Override // com.reandroid.dex.key.TypeKey
        public String getSourceName() {
            return this.sourceName;
        }
    }
}
