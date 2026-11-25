package org.jetbrains.kotlin.org.jdom;

import com.gaurav.avnc.vnc.XKeySym;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.internal.CharCompanionObject;

/* JADX INFO: loaded from: classes4.dex */
public final class Verifier {
    private static final byte[] VALCONST = {0, 1, 0, 1, 0, 1, 65, 1, 65, 73, 65, 89, 65, 1, 65, 1, 65, 79, 1, 77, 1, 79, 1, 65, 1, 9, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 9, 1, 41, 1, 41, 1, 15, 9, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 41, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 41, 1, 41, 1, 41, 1, 41, 1, 41, 1, 41, 1, 15, 1, 15, 1, 15, 1, 9, 15, 41, 1, 25, 1, 41, 15, 1, 15, 1, 15, 1, 15, 1, 15, 41, 15, 41, 1, 41, 1, 25, 1, 41, 1, 15, 1, 41, 15, 41, 1, 41, 1, 15, 41, 1, 25, 1, 41, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 41, 1, 41, 1, 41, 1, 41, 1, 41, 1, 15, 1, 15, 41, 1, 25, 15, 1, 41, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 41, 1, 41, 1, 41, 1, 41, 1, 15, 1, 15, 1, 25, 41, 15, 1, 41, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 41, 15, 41, 1, 41, 1, 41, 1, 15, 1, 25, 1, 41, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 41, 15, 41, 1, 41, 1, 41, 1, 41, 1, 15, 1, 15, 1, 25, 1, 41, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 41, 1, 41, 1, 41, 1, 41, 1, 25, 1, 41, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 41, 1, 41, 1, 41, 1, 41, 1, 15, 1, 25, 1, 41, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 41, 1, 41, 1, 41, 1, 41, 1, 15, 1, 15, 1, 25, 1, 41, 1, 15, 1, 15, 1, 15, 1, 15, 1, 41, 1, 41, 1, 41, 1, 41, 1, 15, 1, 25, 1, 15, 1, 15, 41, 15, 41, 1, 15, 9, 41, 1, 25, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 41, 15, 41, 1, 41, 15, 1, 15, 1, 9, 1, 41, 1, 25, 1, 41, 1, 25, 1, 41, 1, 41, 1, 41, 1, 41, 15, 1, 15, 1, 41, 1, 41, 1, 41, 1, 41, 1, 41, 1, 41, 1, 41, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 15, 1, 41, 1, 41, 1, 15, 1, 15, 1, 15, 1, 15, 1, 9, 1, 15, 1, 15, 41, 1, 9, 1, 15, 1, 41, 1, 9, 1, 15, 1, 9, 1, 15, 1, 15, 1, 15, 1, 0, 1, 0};
    private static final int[] LENCONST = {9, 2, 2, 1, 18, 1, 1, 2, 9, 2, 1, 10, 1, 2, 1, 1, 2, 26, 4, 1, 1, 26, 3, 1, 56, 1, 8, 23, 1, 31, 1, 58, 2, 11, 2, 8, 1, 53, 1, 68, 9, 36, 3, 2, 4, 30, 56, 89, 18, 7, 14, 2, 46, 70, 26, 2, 36, 1, 1, 3, 1, 1, 1, 20, 1, 44, 1, 7, 3, 1, 1, 1, 1, 1, 1, 1, 1, 18, 13, 12, 1, 66, 1, 12, 1, 36, 1, 4, 9, 53, 2, 2, 2, 2, 3, 28, 2, 8, 2, 2, 55, 38, 2, 1, 7, 38, 10, 17, 1, 23, 1, 3, 1, 1, 1, 2, 1, 1, 11, 27, 5, 3, 46, 26, 5, 1, 10, 8, 13, 10, 6, 1, 71, 2, 5, 1, 15, 1, 4, 1, 1, 15, 2, 2, 1, 4, 2, 10, 519, 3, 1, 53, 2, 1, 1, 16, 3, 4, 3, 10, 2, 2, 10, 17, 3, 1, 8, 2, 2, 2, 22, 1, 7, 1, 1, 3, 4, 2, 1, 1, 7, 2, 2, 2, 3, 9, 1, 4, 2, 1, 3, 2, 2, 10, 2, 16, 1, 2, 6, 4, 2, 2, 22, 1, 7, 1, 2, 1, 2, 1, 2, 2, 1, 1, 5, 4, 2, 2, 3, 11, 4, 1, 1, 7, 10, 2, 3, 12, 3, 1, 7, 1, 1, 1, 3, 1, 22, 1, 7, 1, 2, 1, 5, 2, 1, 1, 8, 1, 3, 1, 3, 18, 1, 5, 10, 17, 3, 1, 8, 2, 2, 2, 22, 1, 7, 1, 2, 2, 4, 2, 1, 1, 6, 3, 2, 2, 3, 8, 2, 4, 2, 1, 3, 4, 10, 18, 2, 1, 6, 3, 3, 1, 4, 3, 2, 1, 1, 1, 2, 3, 2, 3, 3, 3, 8, 1, 3, 4, 5, 3, 3, 1, 4, 9, 1, 15, 9, 17, 3, 1, 8, 1, 3, 1, 23, 1, 10, 1, 5, 4, 7, 1, 3, 1, 4, 7, 2, 9, 2, 4, 10, 18, 2, 1, 8, 1, 3, 1, 23, 1, 10, 1, 5, 4, 7, 1, 3, 1, 4, 7, 2, 7, 1, 1, 2, 4, 10, 18, 2, 1, 8, 1, 3, 1, 23, 1, 16, 4, 6, 2, 3, 1, 4, 9, 1, 8, 2, 4, 10, 145, 46, 1, 1, 1, 2, 7, 5, 6, 1, 8, 1, 10, 39, 2, 1, 1, 2, 2, 1, 1, 2, 1, 6, 4, 1, 7, 1, 3, 1, 1, 1, 1, 2, 2, 1, 2, 1, 1, 1, 2, 6, 1, 2, 1, 2, 5, 1, 1, 1, 6, 2, 10, 62, 2, 6, 10, 11, 1, 1, 1, 1, 1, 4, 2, 8, 1, 33, 7, 20, 1, 6, 4, 6, 1, 1, 1, 21, 3, 7, 1, 1, XKeySym.XK_ae, 38, 10, 39, 9, 1, 1, 2, 1, 3, 1, 1, 1, 2, 1, 5, 41, 1, 1, 1, 1, 1, 11, 1, 1, 1, 1, 1, 3, 2, 3, 1, 5, 3, 1, 1, 1, 1, 1, 1, 1, 1, 3, 2, 3, 2, 1, 1, 40, 1, 9, 1, 2, 1, 2, 2, 7, 2, 1, 1, 1, 7, 40, 1, 4, 1, 8, 1, 3078, 156, 4, 90, 6, 22, 2, 6, 2, 38, 2, 6, 2, 8, 1, 1, 1, 1, 1, 1, 1, 31, 2, 53, 1, 7, 1, 1, 3, 3, 1, 7, 3, 4, 2, 6, 4, 13, 5, 3, 1, 7, 211, 13, 4, 1, 68, 1, 3, 2, 2, 1, 81, 3, 3714, 1, 1, 1, 25, 9, 6, 1, 5, 11, 84, 4, 2, 2, 2, 2, 90, 1, 3, 6, 40, 7379, 20902, 3162, 11172, 92, 2048, 8190, 2};
    private static final byte[] CHARFLAGS = buildBitFlags();

    public static int decodeSurrogatePair(char c, char c2) {
        return ((c - 55296) * 1024) + 65536 + (c2 - CharCompanionObject.MIN_LOW_SURROGATE);
    }

    public static boolean isHighSurrogate(char c) {
        return 54 == (c >>> '\n');
    }

    public static boolean isLowSurrogate(char c) {
        return 55 == (c >>> '\n');
    }

    public static boolean isXMLWhitespace(char c) {
        return c == ' ' || c == '\n' || c == '\t' || c == '\r';
    }

    private static final byte[] buildBitFlags() {
        byte[] bArr = new byte[65536];
        int i = 0;
        int i2 = 0;
        while (true) {
            byte[] bArr2 = VALCONST;
            if (i >= bArr2.length) {
                return bArr;
            }
            byte b = bArr2[i];
            int i3 = LENCONST[i];
            while (true) {
                i3--;
                if (i3 >= 0) {
                    bArr[i2] = b;
                    i2++;
                }
            }
            i++;
        }
    }

    private static final String checkJDOMName(String str) {
        if (str == null) {
            return "XML names cannot be null";
        }
        if (str.length() == 0) {
            return "XML names cannot be empty";
        }
        if ((CHARFLAGS[str.charAt(0)] & 4) == 0) {
            return "XML name '" + str + "' cannot begin with the character \"" + str.charAt(0) + "\"";
        }
        for (int length = str.length() - 1; length >= 1; length--) {
            if (((byte) (CHARFLAGS[str.charAt(length)] & 8)) == 0) {
                return "XML name '" + str + "' cannot contain the character \"" + str.charAt(length) + "\"";
            }
        }
        return null;
    }

    public static String checkNamespacePrefix(String str) {
        if (str == null || str.equals("") || checkJDOMName(str) == null) {
            return null;
        }
        return checkJDOMName(str);
    }

    public static String checkNamespaceURI(String str) {
        if (str != null && !str.equals("")) {
            char cCharAt = str.charAt(0);
            if (Character.isDigit(cCharAt)) {
                return "Namespace URIs cannot begin with a number";
            }
            if (cCharAt == '$') {
                return "Namespace URIs cannot begin with a dollar sign ($)";
            }
            if (cCharAt == '-') {
                return "Namespace URIs cannot begin with a hyphen (-)";
            }
            if (isXMLWhitespace(cCharAt)) {
                return "Namespace URIs cannot begin with white-space";
            }
        }
        return null;
    }

    public static String checkNamespaceCollision(Namespace namespace, Namespace namespace2) {
        String prefix = namespace.getPrefix();
        String uri = namespace.getURI();
        String prefix2 = namespace2.getPrefix();
        String uri2 = namespace2.getURI();
        if (!prefix.equals(prefix2) || uri.equals(uri2)) {
            return null;
        }
        return "The namespace prefix \"" + prefix + "\" collides";
    }

    public static String checkNamespaceCollision(Attribute attribute, Element element) {
        return checkNamespaceCollision(attribute, element, -1);
    }

    public static String checkNamespaceCollision(Attribute attribute, Element element, int i) {
        Namespace namespace = attribute.getNamespace();
        if ("".equals(namespace.getPrefix())) {
            return null;
        }
        return checkNamespaceCollision(namespace, element, i);
    }

    public static String checkNamespaceCollision(Namespace namespace, Element element) {
        return checkNamespaceCollision(namespace, element, -1);
    }

    public static String checkNamespaceCollision(Namespace namespace, Element element, int i) {
        String strCheckNamespaceCollision;
        String strCheckNamespaceCollision2;
        String strCheckNamespaceCollision3 = checkNamespaceCollision(namespace, element.getNamespace());
        if (strCheckNamespaceCollision3 != null) {
            return strCheckNamespaceCollision3 + " with the element namespace prefix";
        }
        if (element.hasAdditionalNamespaces() && (strCheckNamespaceCollision2 = checkNamespaceCollision(namespace, element.getAdditionalNamespaces())) != null) {
            return strCheckNamespaceCollision2;
        }
        if (!element.hasAttributes() || (strCheckNamespaceCollision = checkNamespaceCollision(namespace, element.getAttributes(), i)) == null) {
            return null;
        }
        return strCheckNamespaceCollision;
    }

    public static String checkNamespaceCollision(Namespace namespace, Attribute attribute) {
        if (attribute.getNamespace().equals(Namespace.NO_NAMESPACE)) {
            return null;
        }
        String strCheckNamespaceCollision = checkNamespaceCollision(namespace, attribute.getNamespace());
        return strCheckNamespaceCollision != null ? strCheckNamespaceCollision + " with an attribute namespace prefix on the element" : strCheckNamespaceCollision;
    }

    public static String checkNamespaceCollision(Namespace namespace, List<?> list) {
        return checkNamespaceCollision(namespace, list, -1);
    }

    public static String checkNamespaceCollision(Namespace namespace, List<?> list, int i) {
        String strCheckNamespaceCollision = null;
        if (list == null) {
            return null;
        }
        Iterator<?> it = list.iterator();
        int i2 = -1;
        while (strCheckNamespaceCollision == null && it.hasNext()) {
            Object next = it.next();
            i2++;
            if (next instanceof Attribute) {
                if (i2 != i) {
                    strCheckNamespaceCollision = checkNamespaceCollision(namespace, (Attribute) next);
                }
            } else if (next instanceof Element) {
                strCheckNamespaceCollision = checkNamespaceCollision(namespace, (Element) next);
            } else if ((next instanceof Namespace) && (strCheckNamespaceCollision = checkNamespaceCollision(namespace, (Namespace) next)) != null) {
                strCheckNamespaceCollision = strCheckNamespaceCollision + " with an additional namespace declared by the element";
            }
        }
        return strCheckNamespaceCollision;
    }

    public static final boolean isAllXMLWhitespace(String str) {
        int length = str.length();
        do {
            length--;
            if (length < 0) {
                return true;
            }
        } while (isXMLWhitespace(str.charAt(length)));
        return false;
    }
}
