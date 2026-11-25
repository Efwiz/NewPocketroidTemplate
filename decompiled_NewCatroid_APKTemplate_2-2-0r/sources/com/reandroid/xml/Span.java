package com.reandroid.xml;

import com.reandroid.utils.collection.ArrayCollection;
import com.reandroid.utils.collection.EmptyList;
import java.util.List;

/* JADX INFO: loaded from: classes11.dex */
public interface Span {
    int getFirstChar();

    int getLastChar();

    String getSpanAttributes();

    int getSpanOrder();

    String getTagName();

    default StyleElement toElement() {
        StyleElement styleElement = new StyleElement(getTagName());
        styleElement.addAttributes(parseAttributes(getSpanAttributes()));
        return styleElement;
    }

    default List<StyleAttribute> parseAttributes(String str) {
        if (str == null || str.length() == 0) {
            return EmptyList.of();
        }
        ArrayCollection arrayCollection = new ArrayCollection();
        StringBuilder sb = new StringBuilder();
        int length = str.length();
        boolean z = false;
        StyleAttribute styleAttribute = null;
        for (int i = 0; i < length; i++) {
            char cCharAt = str.charAt(i);
            if (cCharAt == '=' && styleAttribute == null) {
                if (sb != null) {
                    styleAttribute = new StyleAttribute(sb.toString(), "");
                    arrayCollection.add(styleAttribute);
                }
                z = false;
                sb = null;
            } else if (cCharAt != '\"' || styleAttribute == null) {
                if (cCharAt == ' ' || cCharAt == ';') {
                    if (styleAttribute == null || sb == null) {
                        z = false;
                    } else if (z) {
                        sb.append(cCharAt);
                    } else {
                        styleAttribute.setValue(sb.toString());
                    }
                    sb = null;
                    styleAttribute = null;
                } else {
                    if (sb == null) {
                        sb = new StringBuilder();
                    }
                    sb.append(cCharAt);
                }
            } else if (z) {
                styleAttribute.setValue(sb.toString());
                sb = null;
                styleAttribute = null;
            } else if (sb == null) {
                sb = new StringBuilder();
                z = true;
            } else {
                sb.append(cCharAt);
            }
        }
        if (styleAttribute != null && sb != null) {
            styleAttribute.setValue(sb.toString());
        }
        return arrayCollection;
    }

    static String splitTagName(String str) {
        if (str == null) {
            return null;
        }
        int iIndexOf = str.indexOf(59);
        if (iIndexOf < 0) {
            iIndexOf = str.indexOf(32);
        }
        return iIndexOf < 0 ? str : str.substring(0, iIndexOf);
    }

    static String splitAttribute(String str) {
        if (str == null || str.length() == 0) {
            return "";
        }
        if (str.charAt(0) == ' ') {
            str = str.trim();
        }
        int iIndexOf = str.indexOf(59);
        int iIndexOf2 = str.indexOf(32);
        if (iIndexOf < 0 || (iIndexOf2 >= 0 && iIndexOf2 < iIndexOf)) {
            iIndexOf = iIndexOf2;
        }
        if (iIndexOf < 0) {
            return "";
        }
        return str.substring(iIndexOf + 1);
    }
}
