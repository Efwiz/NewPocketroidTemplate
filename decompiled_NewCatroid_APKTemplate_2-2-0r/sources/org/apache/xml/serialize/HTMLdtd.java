package org.apache.xml.serialize;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Hashtable;
import java.util.Locale;
import org.apache.xerces.dom.DOMMessageFormatter;
import org.bouncycastle.asn1.ASN1Encoding;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: loaded from: classes13.dex */
public final class HTMLdtd {
    private static final int ALLOWED_HEAD = 32;
    private static final int CLOSE_DD_DT = 128;
    private static final int CLOSE_P = 64;
    private static final int CLOSE_SELF = 256;
    private static final int CLOSE_TABLE = 512;
    private static final int CLOSE_TH_TD = 16384;
    private static final int ELEM_CONTENT = 2;
    private static final int EMPTY = 17;
    private static final String ENTITIES_RESOURCE = "HTMLEntities.res";
    public static final String HTMLPublicId = "-//W3C//DTD HTML 4.01//EN";
    public static final String HTMLSystemId = "http://www.w3.org/TR/html4/strict.dtd";
    private static final int ONLY_OPENING = 1;
    private static final int OPT_CLOSING = 8;
    private static final int PRESERVE = 4;
    public static final String XHTMLPublicId = "-//W3C//DTD XHTML 1.0 Strict//EN";
    public static final String XHTMLSystemId = "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd";
    private static Hashtable _boolAttrs;
    private static Hashtable _byChar;
    private static Hashtable _byName;
    private static Hashtable _elemDefs = new Hashtable();

    static {
        defineElement("ADDRESS", 64);
        defineElement("AREA", 17);
        defineElement("BASE", 49);
        defineElement("BASEFONT", 17);
        defineElement("BLOCKQUOTE", 64);
        defineElement("BODY", 8);
        defineElement("BR", 17);
        defineElement("COL", 17);
        defineElement("COLGROUP", 522);
        defineElement("DD", 137);
        defineElement("DIV", 64);
        defineElement(ASN1Encoding.DL, 66);
        defineElement("DT", 137);
        defineElement("FIELDSET", 64);
        defineElement("FORM", 64);
        defineElement("FRAME", 25);
        defineElement("H1", 64);
        defineElement("H2", 64);
        defineElement("H3", 64);
        defineElement("H4", 64);
        defineElement("H5", 64);
        defineElement("H6", 64);
        defineElement("HEAD", 10);
        defineElement("HR", 81);
        defineElement("HTML", 10);
        defineElement("IMG", 17);
        defineElement("INPUT", 17);
        defineElement("ISINDEX", 49);
        defineElement("LI", 265);
        defineElement("LINK", 49);
        defineElement("MAP", 32);
        defineElement("META", 49);
        defineElement("OL", 66);
        defineElement("OPTGROUP", 2);
        defineElement("OPTION", 265);
        defineElement("P", 328);
        defineElement("PARAM", 17);
        defineElement("PRE", 68);
        defineElement("SCRIPT", 36);
        defineElement("NOSCRIPT", 36);
        defineElement("SELECT", 2);
        defineElement("STYLE", 36);
        defineElement("TABLE", 66);
        defineElement("TBODY", 522);
        defineElement("TD", 16392);
        defineElement("TEXTAREA", 4);
        defineElement("TFOOT", 522);
        defineElement("TH", 16392);
        defineElement("THEAD", 522);
        defineElement("TITLE", 32);
        defineElement("TR", 522);
        defineElement("UL", 66);
        _boolAttrs = new Hashtable();
        defineBoolean("AREA", "href");
        defineBoolean("BUTTON", CompilerOptions.DISABLED);
        defineBoolean("DIR", "compact");
        defineBoolean(ASN1Encoding.DL, "compact");
        defineBoolean("FRAME", "noresize");
        defineBoolean("HR", "noshade");
        defineBoolean("IMAGE", "ismap");
        defineBoolean("INPUT", new String[]{"defaultchecked", "checked", "readonly", CompilerOptions.DISABLED});
        defineBoolean("LINK", "link");
        defineBoolean("MENU", "compact");
        defineBoolean(Namer.CLASS_KIND_OBJECT, "declare");
        defineBoolean("OL", "compact");
        defineBoolean("OPTGROUP", CompilerOptions.DISABLED);
        defineBoolean("OPTION", new String[]{"default-selected", "selected", CompilerOptions.DISABLED});
        defineBoolean("SCRIPT", "defer");
        defineBoolean("SELECT", new String[]{"multiple", CompilerOptions.DISABLED});
        defineBoolean("STYLE", CompilerOptions.DISABLED);
        defineBoolean("TD", "nowrap");
        defineBoolean("TH", "nowrap");
        defineBoolean("TEXTAREA", new String[]{CompilerOptions.DISABLED, "readonly"});
        defineBoolean("UL", "compact");
        initialize();
    }

    public static int charFromName(String str) {
        initialize();
        Object obj = _byName.get(str);
        if (obj == null || !(obj instanceof Integer)) {
            return -1;
        }
        return ((Integer) obj).intValue();
    }

    private static void defineBoolean(String str, String str2) {
        defineBoolean(str, new String[]{str2});
    }

    private static void defineBoolean(String str, String[] strArr) {
        _boolAttrs.put(str, strArr);
    }

    private static void defineElement(String str, int i) {
        _elemDefs.put(str, new Integer(i));
    }

    private static void defineEntity(String str, char c) {
        if (_byName.get(str) == null) {
            _byName.put(str, new Integer(c));
            _byChar.put(new Integer(c), str);
        }
    }

    public static String fromChar(int i) {
        if (i > 65535) {
            return null;
        }
        initialize();
        return (String) _byChar.get(new Integer(i));
    }

    private static void initialize() {
        int iIndexOf;
        if (_byName != null) {
            return;
        }
        InputStream inputStream = null;
        try {
            try {
                _byName = new Hashtable();
                _byChar = new Hashtable();
                InputStream resourceAsStream = HTMLdtd.class.getResourceAsStream(ENTITIES_RESOURCE);
                if (resourceAsStream == null) {
                    throw new RuntimeException(DOMMessageFormatter.formatMessage(DOMMessageFormatter.SERIALIZER_DOMAIN, "ResourceNotFound", new Object[]{ENTITIES_RESOURCE}));
                }
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resourceAsStream, "ASCII"));
                while (true) {
                    String line = bufferedReader.readLine();
                    if (line == null) {
                        break;
                    }
                    if (line.length() != 0 && line.charAt(0) != '#' && (iIndexOf = line.indexOf(32)) > 1) {
                        String strSubstring = line.substring(0, iIndexOf);
                        int i = iIndexOf + 1;
                        if (i < line.length()) {
                            String strSubstring2 = line.substring(i);
                            int iIndexOf2 = strSubstring2.indexOf(32);
                            if (iIndexOf2 > 0) {
                                strSubstring2 = strSubstring2.substring(0, iIndexOf2);
                            }
                            defineEntity(strSubstring, (char) Integer.parseInt(strSubstring2));
                        }
                    }
                }
                resourceAsStream.close();
                if (resourceAsStream != null) {
                    try {
                        resourceAsStream.close();
                    } catch (Exception unused) {
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(DOMMessageFormatter.formatMessage(DOMMessageFormatter.SERIALIZER_DOMAIN, "ResourceNotLoaded", new Object[]{ENTITIES_RESOURCE, e.toString()}));
            }
        } catch (Throwable th) {
            if (0 != 0) {
                try {
                    inputStream.close();
                } catch (Exception unused2) {
                }
            }
            throw th;
        }
    }

    public static boolean isBoolean(String str, String str2) {
        String[] strArr = (String[]) _boolAttrs.get(str.toUpperCase(Locale.ENGLISH));
        if (strArr == null) {
            return false;
        }
        for (String str3 : strArr) {
            if (str3.equalsIgnoreCase(str2)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isClosing(String str, String str2) {
        if (str2.equalsIgnoreCase("HEAD")) {
            return !isElement(str, 32);
        }
        if (str2.equalsIgnoreCase("P")) {
            return isElement(str, 64);
        }
        if (str2.equalsIgnoreCase("DT") || str2.equalsIgnoreCase("DD")) {
            return isElement(str, 128);
        }
        if (str2.equalsIgnoreCase("LI") || str2.equalsIgnoreCase("OPTION")) {
            return isElement(str, 256);
        }
        if (str2.equalsIgnoreCase("THEAD") || str2.equalsIgnoreCase("TFOOT") || str2.equalsIgnoreCase("TBODY") || str2.equalsIgnoreCase("TR") || str2.equalsIgnoreCase("COLGROUP")) {
            return isElement(str, 512);
        }
        if (str2.equalsIgnoreCase("TH") || str2.equalsIgnoreCase("TD")) {
            return isElement(str, 16384);
        }
        return false;
    }

    private static boolean isElement(String str, int i) {
        Integer num = (Integer) _elemDefs.get(str.toUpperCase(Locale.ENGLISH));
        return num != null && (num.intValue() & i) == i;
    }

    public static boolean isElementContent(String str) {
        return isElement(str, 2);
    }

    public static boolean isEmptyTag(String str) {
        return isElement(str, 17);
    }

    public static boolean isOnlyOpening(String str) {
        return isElement(str, 1);
    }

    public static boolean isOptionalClosing(String str) {
        return isElement(str, 8);
    }

    public static boolean isPreserveSpace(String str) {
        return isElement(str, 4);
    }

    public static boolean isURI(String str, String str2) {
        return str2.equalsIgnoreCase("href") || str2.equalsIgnoreCase("src");
    }
}
