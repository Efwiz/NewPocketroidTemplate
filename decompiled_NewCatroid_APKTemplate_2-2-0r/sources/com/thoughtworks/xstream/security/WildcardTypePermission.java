package com.thoughtworks.xstream.security;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/security/WildcardTypePermission.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class WildcardTypePermission extends RegExpTypePermission {
    public WildcardTypePermission(String[] strArr) {
        super(getRegExpPatterns(strArr));
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:28:0x0060  */
    private static String[] getRegExpPatterns(String[] strArr) {
        if (strArr == null) {
            return null;
        }
        String[] strArr2 = new String[strArr.length];
        for (int i = 0; i < strArr.length; i++) {
            String str = strArr[i];
            StringBuffer stringBuffer = new StringBuffer(str.length() * 2);
            stringBuffer.append("(?u)");
            int length = str.length();
            int i2 = 0;
            while (i2 < length) {
                char cCharAt = str.charAt(i2);
                if (cCharAt == '$' || cCharAt == '.') {
                    stringBuffer.append('\\').append(cCharAt);
                } else if (cCharAt == '?') {
                    stringBuffer.append('.');
                } else if (cCharAt != '|') {
                    switch (cCharAt) {
                        case '(':
                        case ')':
                        case '+':
                            stringBuffer.append('\\').append(cCharAt);
                            break;
                        case '*':
                            int i3 = i2 + 1;
                            if (i3 < length && str.charAt(i3) == '*') {
                                stringBuffer.append("[\\P{C}]*");
                                i2 = i3;
                            } else {
                                stringBuffer.append("[\\P{C}&&[^.]]*");
                            }
                            break;
                        default:
                            switch (cCharAt) {
                                case '[':
                                case '\\':
                                case ']':
                                case '^':
                                    stringBuffer.append('\\').append(cCharAt);
                                    break;
                                default:
                                    stringBuffer.append(cCharAt);
                                    break;
                            }
                            break;
                    }
                } else {
                    stringBuffer.append('\\').append(cCharAt);
                }
                i2++;
            }
            strArr2[i] = stringBuffer.toString();
        }
        return strArr2;
    }
}
