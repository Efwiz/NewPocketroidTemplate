package com.reandroid.dex.key;

import com.reandroid.dex.common.DexUtils;
import com.reandroid.dex.smali.SmaliParseException;
import com.reandroid.dex.smali.SmaliReader;
import com.reandroid.dex.smali.SmaliWriter;
import com.reandroid.utils.CompareUtil;
import com.reandroid.utils.ObjectsUtil;
import com.reandroid.utils.StringsUtil;
import com.reandroid.utils.collection.CombiningIterator;
import com.reandroid.utils.collection.SingleIterator;
import java.io.IOException;
import java.util.Iterator;

/* JADX INFO: loaded from: classes11.dex */
public class StringKey implements Key {
    public static final StringKey EMPTY = new StringKey(StringsUtil.EMPTY);
    private boolean mSignature;
    private final String text;

    @Override // com.reandroid.dex.key.Key
    public boolean isPlatform() {
        return false;
    }

    public StringKey(String str) {
        this.text = str;
    }

    public String getString() {
        return this.text;
    }

    public String getEncodedString() {
        return DexUtils.encodeString(getString());
    }

    public String getQuoted() {
        return DexUtils.quoteString(getString());
    }

    public boolean isSignature() {
        return this.mSignature;
    }

    public void setSignature(boolean z) {
        this.mSignature = z;
    }

    @Override // com.reandroid.dex.key.Key
    public TypeKey getDeclaring() {
        if (isSignature()) {
            return TypeKey.parseSignature(getString());
        }
        return null;
    }

    @Override // com.reandroid.dex.key.Key
    public Iterator<Key> mentionedKeys() {
        return CombiningIterator.singleOne(getDeclaring(), SingleIterator.of(this));
    }

    @Override // com.reandroid.dex.key.Key
    public Key replaceKey(Key key, Key key2) {
        return key.equals(this) ? key2 : this;
    }

    @Override // com.reandroid.dex.key.Key, com.reandroid.dex.smali.SmaliFormat
    public void append(SmaliWriter smaliWriter) throws IOException {
        smaliWriter.append('\"');
        boolean zEncodeString = DexUtils.encodeString(smaliWriter, getString());
        smaliWriter.append('\"');
        if (zEncodeString && smaliWriter.isCommentUnicodeStrings()) {
            DexUtils.appendCommentString(250, smaliWriter.getCommentAppender(), getString());
        }
    }

    @Override // java.lang.Comparable
    public int compareTo(Object obj) {
        if (obj == null) {
            return -1;
        }
        return CompareUtil.compare(getString(), ((StringKey) obj).getString());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof StringKey) {
            return ObjectsUtil.equals(getString(), ((StringKey) obj).getString());
        }
        return false;
    }

    public int hashCode() {
        return getString().hashCode();
    }

    public String toString() {
        return getQuoted();
    }

    public static StringKey create(String str) {
        if (str == null) {
            return null;
        }
        if (str.length() == 0) {
            return EMPTY;
        }
        return new StringKey(str);
    }

    public static StringKey parseQuotedString(String str) {
        if (str != null && str.length() >= 2) {
            SmaliReader smaliReaderOf = SmaliReader.of(str);
            if (smaliReaderOf.get() != 34) {
                return null;
            }
            try {
                smaliReaderOf.skip(1);
                String escapedString = smaliReaderOf.readEscapedString('\"');
                if (smaliReaderOf.available() == 1 && smaliReaderOf.get() == 34) {
                    return create(escapedString);
                }
            } catch (IOException unused) {
            }
        }
        return null;
    }

    public static StringKey read(SmaliReader smaliReader) throws IOException {
        smaliReader.skipSpaces();
        SmaliParseException.expect(smaliReader, '\"');
        String escapedString = smaliReader.readEscapedString('\"');
        SmaliParseException.expect(smaliReader, '\"');
        return create(escapedString);
    }
}
