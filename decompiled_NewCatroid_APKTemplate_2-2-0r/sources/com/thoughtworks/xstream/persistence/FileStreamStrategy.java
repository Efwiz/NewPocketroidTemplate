package com.thoughtworks.xstream.persistence;

import com.android.dex.DexFormat;
import com.thoughtworks.xstream.XStream;
import java.io.File;
import org.jetbrains.kotlin.name.JvmNames;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/persistence/FileStreamStrategy.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class FileStreamStrategy extends AbstractFilePersistenceStrategy implements StreamStrategy {
    public FileStreamStrategy(File file) {
        this(file, new XStream());
    }

    public FileStreamStrategy(File file, XStream xStream) {
        super(file, xStream, null);
    }

    @Override // com.thoughtworks.xstream.persistence.AbstractFilePersistenceStrategy
    protected Object extractKey(String str) {
        String strUnescape = unescape(str.substring(0, str.length() - 4));
        if (strUnescape.equals(DexFormat.MAGIC_SUFFIX)) {
            return null;
        }
        return strUnescape;
    }

    protected String unescape(String str) {
        StringBuffer stringBuffer = new StringBuffer();
        char[] charArray = str.toCharArray();
        char c = 65535;
        int i = -1;
        int i2 = 0;
        while (i2 < charArray.length) {
            char c2 = charArray[i2];
            if (c2 == '_' && i != -1) {
                if (c == '_') {
                    stringBuffer.append('_');
                } else {
                    stringBuffer.append((char) i);
                }
                i = -1;
            } else if (c2 == '_') {
                i = 0;
            } else if (i != -1) {
                i = (i * 16) + Integer.parseInt(String.valueOf(c2), 16);
            } else {
                stringBuffer.append(c2);
            }
            i2++;
            c = c2;
        }
        return stringBuffer.toString();
    }

    @Override // com.thoughtworks.xstream.persistence.AbstractFilePersistenceStrategy
    protected String getName(Object obj) {
        return new StringBuffer().append(escape(obj == null ? DexFormat.MAGIC_SUFFIX : obj.toString())).append(".xml").toString();
    }

    protected String escape(String str) {
        StringBuffer stringBuffer = new StringBuffer();
        for (char c : str.toCharArray()) {
            if (Character.isDigit(c) || ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z'))) {
                stringBuffer.append(c);
            } else if (c == '_') {
                stringBuffer.append(JvmNames.MULTIFILE_PART_NAME_DELIMITER);
            } else {
                stringBuffer.append(new StringBuffer("_").append(Integer.toHexString(c)).append("_").toString());
            }
        }
        return stringBuffer.toString();
    }
}
