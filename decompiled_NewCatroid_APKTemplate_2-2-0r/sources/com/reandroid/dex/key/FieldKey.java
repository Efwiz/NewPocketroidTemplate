package com.reandroid.dex.key;

import com.reandroid.dex.id.FieldId;
import com.reandroid.dex.smali.SmaliParseException;
import com.reandroid.dex.smali.SmaliReader;
import com.reandroid.dex.smali.SmaliWriter;
import com.reandroid.utils.CompareUtil;
import com.reandroid.utils.StringsUtil;
import com.reandroid.utils.collection.CombiningIterator;
import com.reandroid.utils.collection.SingleIterator;
import java.io.IOException;
import java.util.Iterator;
import java.util.function.Function;

/* JADX INFO: loaded from: classes11.dex */
public class FieldKey implements Key {
    private final String declaring;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private final String f241name;
    private final String type;

    public FieldKey(String str, String str2, String str3) {
        this.declaring = str;
        this.f241name = str2;
        this.type = str3;
    }

    @Deprecated
    public FieldKey changeDefining(TypeKey typeKey) {
        return changeDeclaring(typeKey);
    }

    @Deprecated
    public FieldKey changeDefining(String str) {
        return changeDeclaring(str);
    }

    public FieldKey changeDeclaring(TypeKey typeKey) {
        return changeDeclaring(typeKey.getTypeName());
    }

    public FieldKey changeDeclaring(String str) {
        return str.equals(getDeclaringName()) ? this : new FieldKey(str, getName(), getTypeName());
    }

    public FieldKey changeName(String str) {
        return str.equals(getName()) ? this : new FieldKey(getDeclaringName(), str, getTypeName());
    }

    public FieldKey changeType(TypeKey typeKey) {
        return changeType(typeKey.getTypeName());
    }

    public FieldKey changeType(String str) {
        return str.equals(getTypeName()) ? this : new FieldKey(getDeclaringName(), getName(), str);
    }

    @Override // com.reandroid.dex.key.Key
    public TypeKey getDeclaring() {
        return new TypeKey(getDeclaringName());
    }

    @Override // com.reandroid.dex.key.Key
    public Iterator<Key> mentionedKeys() {
        return CombiningIterator.singleThree(this, SingleIterator.of(getDeclaring()), SingleIterator.of(getNameKey()), SingleIterator.of(getType()));
    }

    public FieldKey replaceTypes(Function<TypeKey, TypeKey> function) {
        TypeKey declaring = getDeclaring();
        FieldKey fieldKeyChangeDeclaring = changeDeclaring(declaring.changeTypeName(function.apply(declaring)));
        TypeKey type = getType();
        return fieldKeyChangeDeclaring.changeType(type.changeTypeName(function.apply(type)));
    }

    @Override // com.reandroid.dex.key.Key
    public Key replaceKey(Key key, Key key2) {
        if (key.equals(this)) {
            return key2;
        }
        FieldKey fieldKeyChangeDeclaring = key.equals(getDeclaring()) ? changeDeclaring((TypeKey) key2) : this;
        return key.equals(fieldKeyChangeDeclaring.getType()) ? fieldKeyChangeDeclaring.changeType((TypeKey) key2) : fieldKeyChangeDeclaring;
    }

    public StringKey getNameKey() {
        return new StringKey(getName());
    }

    public TypeKey getType() {
        return new TypeKey(getTypeName());
    }

    public String getDeclaringName() {
        return this.declaring;
    }

    public String getName() {
        return this.f241name;
    }

    public String getTypeName() {
        return this.type;
    }

    @Override // com.reandroid.dex.key.Key, com.reandroid.dex.smali.SmaliFormat
    public void append(SmaliWriter smaliWriter) throws IOException {
        smaliWriter.appendOptional(getDeclaring());
        smaliWriter.append("->");
        smaliWriter.append((CharSequence) getName());
        smaliWriter.append(':');
        smaliWriter.appendOptional(getType());
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
        FieldKey fieldKey = (FieldKey) obj;
        if (z && (iCompare = CompareUtil.compare(getDeclaringName(), fieldKey.getDeclaringName())) != 0) {
            return iCompare;
        }
        int iCompare2 = CompareUtil.compare(getName(), fieldKey.getName());
        return iCompare2 != 0 ? iCompare2 : CompareUtil.compare(getTypeName(), fieldKey.getTypeName());
    }

    public int hashCode() {
        String declaringName = getDeclaringName();
        return ((declaringName != null ? 1 + declaringName.hashCode() : 1) * 31) + getName().hashCode();
    }

    public boolean equalsDeclaring(String str) {
        if (str == null) {
            return false;
        }
        return KeyUtil.matches(getDeclaringName(), str);
    }

    public boolean equalsDeclaring(TypeKey typeKey) {
        if (typeKey == null) {
            return false;
        }
        return KeyUtil.matches(getDeclaringName(), typeKey.getTypeName());
    }

    public boolean equalsDeclaring(FieldKey fieldKey) {
        if (fieldKey == null) {
            return false;
        }
        if (fieldKey == this) {
            return true;
        }
        return KeyUtil.matches(getDeclaringName(), fieldKey.getDeclaringName());
    }

    public boolean equalsIgnoreName(FieldKey fieldKey) {
        if (fieldKey == null) {
            return false;
        }
        if (fieldKey == this) {
            return true;
        }
        if (KeyUtil.matches(getDeclaringName(), fieldKey.getDeclaringName())) {
            return KeyUtil.matches(getTypeName(), fieldKey.getTypeName());
        }
        return false;
    }

    public boolean equalsName(FieldKey fieldKey) {
        if (fieldKey == null) {
            return false;
        }
        if (fieldKey == this) {
            return true;
        }
        return KeyUtil.matches(getName(), fieldKey.getName());
    }

    public boolean equalsName(String str) {
        return KeyUtil.matches(getName(), str);
    }

    public boolean equalsType(String str) {
        return KeyUtil.matches(getTypeName(), str);
    }

    public boolean equalsType(TypeKey typeKey) {
        if (typeKey == null) {
            return false;
        }
        return KeyUtil.matches(getTypeName(), typeKey.getTypeName());
    }

    public boolean equalsType(FieldKey fieldKey) {
        if (fieldKey == null) {
            return false;
        }
        if (fieldKey == this) {
            return true;
        }
        return KeyUtil.matches(getTypeName(), fieldKey.getTypeName());
    }

    public boolean equals(Object obj) {
        return equals(obj, true, true);
    }

    public boolean equals(Object obj, boolean z, boolean z2) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof FieldKey)) {
            return false;
        }
        FieldKey fieldKey = (FieldKey) obj;
        if (!KeyUtil.matches(getName(), fieldKey.getName())) {
            return false;
        }
        if (z && !KeyUtil.matches(getDeclaringName(), fieldKey.getDeclaringName())) {
            return false;
        }
        if (z2) {
            return KeyUtil.matches(getTypeName(), fieldKey.getTypeName());
        }
        return true;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getDeclaringName());
        sb.append("->");
        sb.append(getName());
        if (getTypeName() != null) {
            sb.append(':');
            sb.append(getTypeName());
        }
        return sb.toString();
    }

    public static FieldKey parse(String str) {
        int iIndexOf;
        if (str == null) {
            return null;
        }
        String strTrim = str.trim();
        if (strTrim.length() < 6 || strTrim.charAt(0) != 'L' || (iIndexOf = strTrim.indexOf(";->")) < 0) {
            return null;
        }
        String strSubstring = strTrim.substring(0, iIndexOf + 1);
        String strSubstring2 = strTrim.substring(iIndexOf + 3);
        int iIndexOf2 = strSubstring2.indexOf(58);
        if (iIndexOf2 < 0) {
            return null;
        }
        String strSubstring3 = strSubstring2.substring(0, iIndexOf2);
        String strSubstring4 = strSubstring2.substring(iIndexOf2 + 1);
        return new FieldKey(strSubstring, strSubstring3, StringsUtil.isEmpty(strSubstring4) ? null : strSubstring4);
    }

    public static FieldKey create(FieldId fieldId) {
        String name2;
        TypeKey fieldType;
        TypeKey defining = fieldId.getDefining();
        if (defining == null || (name2 = fieldId.getName()) == null || (fieldType = fieldId.getFieldType()) == null) {
            return null;
        }
        return new FieldKey(defining.getTypeName(), name2, fieldType.getTypeName());
    }

    public static FieldKey read(SmaliReader smaliReader) throws IOException {
        TypeKey typeKey = TypeKey.read(smaliReader);
        smaliReader.skipWhitespacesOrComment();
        SmaliParseException.expect(smaliReader, '-');
        SmaliParseException.expect(smaliReader, '>');
        smaliReader.skipWhitespacesOrComment();
        int iIndexOfBeforeLineEnd = smaliReader.indexOfBeforeLineEnd(':');
        int iIndexOfWhiteSpaceOrComment = smaliReader.indexOfWhiteSpaceOrComment();
        if (iIndexOfBeforeLineEnd < 0 && iIndexOfWhiteSpaceOrComment < 0) {
            throw new SmaliParseException("Expecting ':'", smaliReader);
        }
        if (iIndexOfBeforeLineEnd < 0 || (iIndexOfWhiteSpaceOrComment >= 0 && iIndexOfWhiteSpaceOrComment < iIndexOfBeforeLineEnd)) {
            iIndexOfBeforeLineEnd = iIndexOfWhiteSpaceOrComment;
        }
        String escapedString = smaliReader.readEscapedString(smaliReader.getASCII(iIndexOfBeforeLineEnd));
        smaliReader.skipWhitespacesOrComment();
        SmaliParseException.expect(smaliReader, ':');
        return new FieldKey(typeKey.getTypeName(), escapedString, TypeKey.read(smaliReader).getTypeName());
    }
}
