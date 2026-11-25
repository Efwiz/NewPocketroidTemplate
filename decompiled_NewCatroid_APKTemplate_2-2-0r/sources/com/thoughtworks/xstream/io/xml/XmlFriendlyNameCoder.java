package com.thoughtworks.xstream.io.xml;

import com.thoughtworks.xstream.converters.reflection.ObjectAccessException;
import com.thoughtworks.xstream.io.naming.NameCoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import kotlin.text.Typography;
import okio.Utf8;
import org.apache.commons.compress.archivers.tar.TarConstants;
import org.jetbrains.kotlin.name.JvmNames;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/io/xml/XmlFriendlyNameCoder.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class XmlFriendlyNameCoder implements NameCoder, Cloneable {
    private static final IntPair[] XML_NAME_CHAR_EXTRA_BOUNDS;
    private static final IntPair[] XML_NAME_START_CHAR_BOUNDS;
    private final String dollarReplacement;
    private transient Map escapeCache;
    private final String escapeCharReplacement;
    private final String hexPrefix;
    private transient Map unescapeCache;

    /* JADX WARN: Type inference failed for: r0v0, types: [com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder$1IntPairList] */
    static {
        ?? r0 = new ArrayList() { // from class: com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder.1IntPairList
            void add(int i, int i2) {
                super.add(new IntPair(i, i2));
            }

            void add(char c) {
                super.add(new IntPair(c, c));
            }
        };
        r0.add(':');
        r0.add(65, 90);
        r0.add(97, 122);
        r0.add('_');
        r0.add(192, 214);
        r0.add(216, 246);
        r0.add(248, 767);
        r0.add(880, 893);
        r0.add(895, 8191);
        r0.add(8204, 8205);
        r0.add(8304, 8591);
        r0.add(11264, 12271);
        r0.add(12289, 55295);
        r0.add(63744, 64975);
        r0.add(65008, Utf8.REPLACEMENT_CODE_POINT);
        r0.add(65536, 983039);
        XML_NAME_START_CHAR_BOUNDS = (IntPair[]) r0.toArray(new IntPair[r0.size()]);
        r0.clear();
        r0.add('-');
        r0.add('.');
        r0.add(48, 57);
        r0.add(Typography.middleDot);
        r0.add(768, 879);
        r0.add(8255, 8256);
        XML_NAME_CHAR_EXTRA_BOUNDS = (IntPair[]) r0.toArray(new IntPair[r0.size()]);
    }

    public XmlFriendlyNameCoder() {
        this("_-", JvmNames.MULTIFILE_PART_NAME_DELIMITER);
    }

    public XmlFriendlyNameCoder(String str, String str2) {
        this(str, str2, "_.");
    }

    public XmlFriendlyNameCoder(String str, String str2, String str3) {
        this.dollarReplacement = str;
        this.escapeCharReplacement = str2;
        this.hexPrefix = str3;
        readResolve();
    }

    @Override // com.thoughtworks.xstream.io.naming.NameCoder
    public String decodeAttribute(String str) {
        return decodeName(str);
    }

    @Override // com.thoughtworks.xstream.io.naming.NameCoder
    public String decodeNode(String str) {
        return decodeName(str);
    }

    @Override // com.thoughtworks.xstream.io.naming.NameCoder
    public String encodeAttribute(String str) {
        return encodeName(str);
    }

    @Override // com.thoughtworks.xstream.io.naming.NameCoder
    public String encodeNode(String str) {
        return encodeName(str);
    }

    private String encodeName(String str) {
        char cCharAt;
        String str2 = (String) this.escapeCache.get(str);
        if (str2 != null) {
            return str2;
        }
        int length = str.length();
        int i = 0;
        while (i < length && (cCharAt = str.charAt(i)) != '$' && cCharAt != '_' && cCharAt > 27 && cCharAt < 127) {
            i++;
        }
        if (i == length) {
            return str;
        }
        StringBuffer stringBuffer = new StringBuffer(length + 8);
        if (i > 0) {
            stringBuffer.append(str.substring(0, i));
        }
        while (i < length) {
            char cCharAt2 = str.charAt(i);
            if (cCharAt2 == '$') {
                stringBuffer.append(this.dollarReplacement);
            } else if (cCharAt2 == '_') {
                stringBuffer.append(this.escapeCharReplacement);
            } else if ((i == 0 && !isXmlNameStartChar(cCharAt2)) || (i > 0 && !isXmlNameChar(cCharAt2))) {
                stringBuffer.append(this.hexPrefix);
                if (cCharAt2 < 16) {
                    stringBuffer.append("000");
                } else if (cCharAt2 < 256) {
                    stringBuffer.append(TarConstants.VERSION_POSIX);
                } else if (cCharAt2 < 4096) {
                    stringBuffer.append("0");
                }
                stringBuffer.append(Integer.toHexString(cCharAt2));
            } else {
                stringBuffer.append(cCharAt2);
            }
            i++;
        }
        String string = stringBuffer.toString();
        this.escapeCache.put(str, string);
        return string;
    }

    private String decodeName(String str) {
        String str2 = (String) this.unescapeCache.get(str);
        if (str2 != null) {
            return str2;
        }
        char cCharAt = this.dollarReplacement.charAt(0);
        char cCharAt2 = this.escapeCharReplacement.charAt(0);
        char cCharAt3 = this.hexPrefix.charAt(0);
        int length = str.length();
        int length2 = 0;
        while (length2 < length) {
            char cCharAt4 = str.charAt(length2);
            if (cCharAt4 == cCharAt || cCharAt4 == cCharAt2 || cCharAt4 == cCharAt3) {
                break;
            }
            length2++;
        }
        if (length2 == length) {
            return str;
        }
        StringBuffer stringBuffer = new StringBuffer(length + 8);
        if (length2 > 0) {
            stringBuffer.append(str.substring(0, length2));
        }
        while (length2 < length) {
            char cCharAt5 = str.charAt(length2);
            if (cCharAt5 == cCharAt && str.startsWith(this.dollarReplacement, length2)) {
                length2 += this.dollarReplacement.length() - 1;
                stringBuffer.append('$');
            } else if (cCharAt5 == cCharAt3 && str.startsWith(this.hexPrefix, length2)) {
                int length3 = length2 + this.hexPrefix.length();
                char c = (char) Integer.parseInt(str.substring(length3, length3 + 4), 16);
                length2 = length3 + 3;
                stringBuffer.append(c);
            } else if (cCharAt5 == cCharAt2 && str.startsWith(this.escapeCharReplacement, length2)) {
                length2 += this.escapeCharReplacement.length() - 1;
                stringBuffer.append('_');
            } else {
                stringBuffer.append(cCharAt5);
            }
            length2++;
        }
        String string = stringBuffer.toString();
        this.unescapeCache.put(str, string);
        return string;
    }

    public Object clone() {
        try {
            XmlFriendlyNameCoder xmlFriendlyNameCoder = (XmlFriendlyNameCoder) super.clone();
            xmlFriendlyNameCoder.readResolve();
            return xmlFriendlyNameCoder;
        } catch (CloneNotSupportedException e) {
            throw new ObjectAccessException("Cannot clone XmlFriendlyNameCoder", e);
        }
    }

    private Object readResolve() {
        this.escapeCache = createCacheMap();
        this.unescapeCache = createCacheMap();
        return this;
    }

    protected Map createCacheMap() {
        return new HashMap();
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/io/xml/XmlFriendlyNameCoder$IntPair.class
      classes11.dex
     */
    private static class IntPair {
        int max;
        int min;

        public IntPair(int i, int i2) {
            this.min = i;
            this.max = i2;
        }
    }

    private static boolean isXmlNameStartChar(int i) {
        return isInNameCharBounds(i, XML_NAME_START_CHAR_BOUNDS);
    }

    private static boolean isXmlNameChar(int i) {
        if (isXmlNameStartChar(i)) {
            return true;
        }
        return isInNameCharBounds(i, XML_NAME_CHAR_EXTRA_BOUNDS);
    }

    private static boolean isInNameCharBounds(int i, IntPair[] intPairArr) {
        for (IntPair intPair : intPairArr) {
            if (i >= intPair.min && i <= intPair.max) {
                return true;
            }
        }
        return false;
    }
}
