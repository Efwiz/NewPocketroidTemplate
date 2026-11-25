package com.reandroid.apkeditor.utils;

import io.netty.util.internal.StringUtil;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/* JADX INFO: loaded from: classes10.dex */
public class StringHelper {
    public static String trueOrNull(Boolean bool) {
        if (bool == null) {
            return null;
        }
        return trueOrNull(bool.booleanValue());
    }

    public static String trueOrNull(boolean z) {
        if (z) {
            return String.valueOf(true);
        }
        return null;
    }

    public static List<String> sortAscending(List<String> list) {
        list.sort(new Comparator<String>() { // from class: com.reandroid.apkeditor.utils.StringHelper.1
            @Override // java.util.Comparator
            public int compare(String str, String str2) {
                return str.compareTo(str2);
            }
        });
        return list;
    }

    public static void printNameAndValues(StringBuilder sb, String str, int i, Object[][] objArr) {
        printNameAndValues(sb, str, "", i, objArr);
    }

    public static void printNameAndValues(StringBuilder sb, String str, String str2, int i, Object[][] objArr) {
        String[][] strArrConvertNameAndValue = convertNameAndValue(objArr);
        if (strArrConvertNameAndValue == null) {
            return;
        }
        int i2 = 0;
        for (String[] strArr : strArrConvertNameAndValue) {
            int length = strArr[0].length();
            if (length > i2) {
                i2 = length;
            }
        }
        int i3 = i - i2;
        for (int i4 = 0; i4 < strArrConvertNameAndValue.length; i4++) {
            String[] strArr2 = strArrConvertNameAndValue[i4];
            if (i4 != 0) {
                sb.append("\n");
            }
            printRow(false, sb, str, i2, i3, strArr2[0], str2, strArr2[1]);
        }
    }

    private static String[][] convertNameAndValue(Object[][] objArr) {
        if (objArr == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (Object[] objArr2 : objArr) {
            String[] strArrConvertNameAndValueRow = convertNameAndValueRow(objArr2);
            if (strArrConvertNameAndValueRow != null) {
                arrayList.add(strArrConvertNameAndValueRow);
            }
        }
        if (arrayList.size() == 0) {
            return null;
        }
        return (String[][]) arrayList.toArray(new String[0][]);
    }

    private static String[] convertNameAndValueRow(Object[] objArr) {
        int length;
        Object obj;
        if (objArr != null && (length = objArr.length) == 2 && (obj = objArr[0]) != null && objArr[1] != null) {
            String[] strArr = new String[length];
            strArr[0] = obj.toString();
            String string = objArr[1].toString();
            strArr[1] = string;
            if (strArr[0] != null && string != null) {
                return strArr;
            }
        }
        return null;
    }

    public static void printTwoColumns(StringBuilder sb, String str, int i, String[][] strArr) {
        printTwoColumns(sb, str, "  ", i, strArr);
    }

    public static void printTwoColumns(StringBuilder sb, String str, String str2, int i, String[][] strArr) {
        int i2 = 0;
        for (String[] strArr2 : strArr) {
            int length = strArr2[0].length();
            if (length > i2) {
                i2 = length;
            }
        }
        int i3 = i - i2;
        for (int i4 = 0; i4 < strArr.length; i4++) {
            String[] strArr3 = strArr[i4];
            if (i4 != 0) {
                sb.append("\n");
            }
            printRow(true, sb, str, i2, i3, strArr3[0], str2, strArr3[1]);
        }
    }

    private static void printRow(boolean z, StringBuilder sb, String str, int i, int i2, String str2, String str3, String str4) {
        sb.append(str);
        if (z) {
            sb.append(str2);
        }
        fillSpace(sb, i - str2.length());
        if (!z) {
            sb.append(str2);
        }
        sb.append(str3);
        char[] charArray = str4.toCharArray();
        int i3 = 0;
        boolean z2 = false;
        for (int i4 = 0; i4 < charArray.length; i4++) {
            char c = charArray[i4];
            if (i4 == 0) {
                sb.append(c);
            } else {
                if (c == '\n' || (i3 > 0 && i3 % i2 == 0)) {
                    sb.append('\n');
                    sb.append(str);
                    fillSpace(sb, str3.length() + i);
                    i3 = 0;
                    z2 = false;
                }
                if (c != '\n') {
                    if (i3 == 0 && c == ' ' && !z2) {
                        z2 = true;
                    } else {
                        sb.append(c);
                    }
                }
            }
            i3++;
        }
    }

    private static void fillSpace(StringBuilder sb, int i) {
        for (int i2 = 0; i2 < i; i2++) {
            sb.append(StringUtil.SPACE);
        }
    }
}
