package com.reandroid.apk.xmlencoder;

import java.io.File;
import java.util.Comparator;
import java.util.List;
import org.antlr.stringtemplate.language.ASTExpr;
import org.apache.xerces.impl.xs.SchemaSymbols;

/* JADX INFO: loaded from: classes10.dex */
public class EncodeUtil {
    public static void sortValuesXml(List<File> list) {
        list.sort(new Comparator() { // from class: com.reandroid.apk.xmlencoder.EncodeUtil$$ExternalSyntheticLambda0
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return EncodeUtil.getValuesXmlCompare((File) obj).compareTo(EncodeUtil.getValuesXmlCompare((File) obj2));
            }
        });
    }

    private static String getValuesXmlCompare(File file) {
        String lowerCase = file.getName().toLowerCase();
        if (lowerCase.equals("public.xml")) {
            return "0";
        }
        if (lowerCase.equals("ids.xml")) {
            return SchemaSymbols.ATTVAL_TRUE_1;
        }
        if (lowerCase.contains(ASTExpr.DEFAULT_ATTRIBUTE_NAME_DEPRECATED)) {
            return "2";
        }
        return "3 " + lowerCase;
    }

    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static String getEntryPathFromResFile(File file) {
        File parentFile = file.getParentFile();
        return parentFile.getParentFile().getName() + "/" + parentFile.getName() + "/" + file.getName();
    }

    public static String getEntryNameFromResFile(File file) {
        String name2 = file.getName();
        if (name2.endsWith(".9.png")) {
            return name2.substring(0, name2.length() - ".9.png".length());
        }
        int iLastIndexOf = name2.lastIndexOf(46);
        return iLastIndexOf > 0 ? name2.substring(0, iLastIndexOf) : name2;
    }

    public static String getQualifiersFromResFile(File file) {
        String name2 = file.getParentFile().getName();
        int iIndexOf = name2.indexOf(45);
        if (iIndexOf > 0) {
            return name2.substring(iIndexOf);
        }
        return "";
    }

    public static String getTypeNameFromResFile(File file) {
        String name2 = file.getParentFile().getName();
        int iIndexOf = name2.indexOf(45);
        if (iIndexOf > 0) {
            name2 = name2.substring(0, iIndexOf);
        }
        return (name2.equals("plurals") || !name2.endsWith("s")) ? name2 : name2.substring(0, name2.length() - 1);
    }

    public static String sanitizeType(String str) {
        if (str.length() < 2) {
            return str;
        }
        char cCharAt = str.charAt(0);
        if (cCharAt == '^' || cCharAt == '+' || cCharAt == '*') {
            str = str.substring(1);
        }
        char[] charArray = str.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (char c : charArray) {
            if (c > 'z' || c < 'a') {
                break;
            }
            sb.append(c);
        }
        String string = sb.toString();
        return ("plurals".equals(string) || !string.endsWith("s")) ? string : string.substring(0, string.length() - 1);
    }
}
