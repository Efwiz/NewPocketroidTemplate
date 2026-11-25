package org.jetbrains.kotlin.com.intellij.util.text;

import java.util.ArrayList;
import java.util.List;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtilRt;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtilRt;

/* JADX INFO: loaded from: classes3.dex */
public final class NameUtilCore {
    /* JADX WARN: Code duplicated, block: B:21:0x002e  */
    /* JADX WARN: Code duplicated, block: B:22:0x0033  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 6) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 6) ? 2 : 3];
        if (i == 1) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/util/text/NameUtilCore";
        } else if (i == 2) {
            objArr[0] = "text";
        } else if (i == 3) {
            objArr[0] = "result";
        } else if (i == 4) {
            objArr[0] = "text";
        } else if (i != 6) {
            objArr[0] = "name";
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/util/text/NameUtilCore";
        }
        if (i == 1) {
            objArr[1] = "splitNameIntoWords";
        } else if (i != 6) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/text/NameUtilCore";
        } else {
            objArr[1] = "nameToWords";
        }
        switch (i) {
            case 1:
            case 6:
                break;
            case 2:
            case 3:
                objArr[2] = "addAllWords";
                break;
            case 4:
                objArr[2] = "nextWord";
                break;
            case 5:
                objArr[2] = "nameToWords";
                break;
            default:
                objArr[2] = "splitNameIntoWords";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 6) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public static String[] splitNameIntoWords(String str) {
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        String[] strArrSplit = str.split("_");
        ArrayList arrayList = new ArrayList();
        for (String str2 : strArrSplit) {
            addAllWords(str2, arrayList);
        }
        String[] stringArray = ArrayUtilRt.toStringArray(arrayList);
        if (stringArray == null) {
            $$$reportNull$$$0(1);
        }
        return stringArray;
    }

    private static void addAllWords(String str, List<? super String> list) {
        if (str == null) {
            $$$reportNull$$$0(2);
        }
        if (list == null) {
            $$$reportNull$$$0(3);
        }
        int i = 0;
        while (i < str.length()) {
            int iNextWord = nextWord(str, i);
            list.add(str.substring(i, iNextWord));
            i = iNextWord;
        }
    }

    public static int nextWord(String str, int i) {
        if (str == null) {
            $$$reportNull$$$0(4);
        }
        if (!Character.isLetterOrDigit(str.charAt(i))) {
            return i + 1;
        }
        int i2 = i;
        while (i2 < str.length() && Character.isDigit(str.charAt(i2))) {
            i2++;
        }
        if (i2 > i) {
            return i2;
        }
        while (i2 < str.length() && Character.isUpperCase(str.charAt(i2))) {
            i2++;
        }
        if (i2 > i + 1) {
            return (i2 == str.length() || !Character.isLetter(str.charAt(i2))) ? i2 : i2 - 1;
        }
        if (i2 == i) {
            i2++;
        }
        while (i2 < str.length() && Character.isLetter(str.charAt(i2)) && !isWordStart(str, i2)) {
            i2++;
        }
        return i2;
    }

    public static boolean isWordStart(String str, int i) {
        char cCharAt = str.charAt(i);
        if (Character.isUpperCase(cCharAt)) {
            if (i <= 0 || !Character.isUpperCase(str.charAt(i - 1))) {
                return true;
            }
            int i2 = i + 1;
            return i2 < str.length() && Character.isLowerCase(str.charAt(i2));
        }
        if (Character.isDigit(cCharAt)) {
            return true;
        }
        if (Character.isLetter(cCharAt)) {
            return i == 0 || !Character.isLetterOrDigit(str.charAt(i + (-1))) || isHardCodedWordStart(str, i);
        }
        return false;
    }

    private static boolean isHardCodedWordStart(String str, int i) {
        int i2;
        return str.charAt(i) == 'l' && i < str.length() - 1 && str.charAt(i + 1) == 'n' && (str.length() == (i2 = i + 2) || isWordStart(str, i2));
    }

    public static String[] nameToWords(String str) {
        int i;
        if (str == null) {
            $$$reportNull$$$0(5);
        }
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < str.length(); i2 = i) {
            int i3 = 0;
            int i4 = 0;
            int i5 = 0;
            int i6 = 0;
            i = i2;
            while (i < str.length()) {
                char cCharAt = str.charAt(i);
                if (Character.isDigit(cCharAt)) {
                    if (i3 > 0 || i4 > 0 || i6 > 0) {
                        break;
                    }
                    i5++;
                    i++;
                } else if (Character.isUpperCase(cCharAt)) {
                    if (i4 > 0 || i5 > 0 || i6 > 0) {
                        break;
                    }
                    i3++;
                    i++;
                } else if (Character.isLowerCase(cCharAt)) {
                    if (i5 > 0 || i6 > 0) {
                        break;
                    }
                    if (i3 > 1) {
                        i--;
                        break;
                    }
                    i4++;
                    i++;
                } else {
                    if (i3 > 0 || i4 > 0 || i5 > 0) {
                        break;
                    }
                    i6++;
                    i++;
                }
            }
            String strSubstring = str.substring(i2, i);
            if (!StringUtilRt.isEmptyOrSpaces(strSubstring)) {
                arrayList.add(strSubstring);
            }
        }
        String[] stringArray = ArrayUtilRt.toStringArray(arrayList);
        if (stringArray == null) {
            $$$reportNull$$$0(6);
        }
        return stringArray;
    }
}
