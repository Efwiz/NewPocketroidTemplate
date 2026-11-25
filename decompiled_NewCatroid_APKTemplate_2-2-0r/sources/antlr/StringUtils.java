package antlr;

/* JADX INFO: loaded from: classes.dex */
public class StringUtils {
    public static String stripBack(String str, char c) {
        while (str.length() > 0 && str.charAt(str.length() - 1) == c) {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }

    public static String stripBack(String str, String str2) {
        boolean z;
        do {
            z = false;
            for (int i = 0; i < str2.length(); i++) {
                char cCharAt = str2.charAt(i);
                while (str.length() > 0 && str.charAt(str.length() - 1) == cCharAt) {
                    str = str.substring(0, str.length() - 1);
                    z = true;
                }
            }
        } while (z);
        return str;
    }

    public static String stripFront(String str, char c) {
        while (str.length() > 0 && str.charAt(0) == c) {
            str = str.substring(1);
        }
        return str;
    }

    public static String stripFront(String str, String str2) {
        boolean z;
        do {
            z = false;
            for (int i = 0; i < str2.length(); i++) {
                char cCharAt = str2.charAt(i);
                while (str.length() > 0 && str.charAt(0) == cCharAt) {
                    z = true;
                    str = str.substring(1);
                }
            }
        } while (z);
        return str;
    }

    public static String stripFrontBack(String str, String str2, String str3) {
        int iIndexOf = str.indexOf(str2);
        int iLastIndexOf = str.lastIndexOf(str3);
        return (iIndexOf == -1 || iLastIndexOf == -1) ? str : str.substring(iIndexOf + 1, iLastIndexOf);
    }
}
