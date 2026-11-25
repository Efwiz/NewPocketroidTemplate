package com.reandroid.arsc.coder.xml;

import java.io.File;

/* JADX INFO: loaded from: classes10.dex */
public class XmlEncodeUtil {
    public static String getQualifiersFromValuesXml(File file) {
        String name2 = file.getParentFile().getName();
        int iIndexOf = name2.indexOf(45);
        if (iIndexOf > 0) {
            return name2.substring(iIndexOf);
        }
        return "";
    }

    public static String getTypeFromValuesXml(File file) {
        String name2 = file.getName();
        String strSubstring = name2.substring(0, name2.length() - 4);
        return (strSubstring.equals("plurals") || !strSubstring.endsWith("s")) ? strSubstring : strSubstring.substring(0, strSubstring.length() - 1);
    }
}
