package org.apache.commons.beanutils.expression;

import name.antonsmirnov.firmata.FormatHelper;

/* JADX INFO: loaded from: classes12.dex */
public class DefaultResolver implements Resolver {
    private static final char INDEXED_END = ']';
    private static final char INDEXED_START = '[';
    private static final char MAPPED_END = ')';
    private static final char MAPPED_START = '(';
    private static final char NESTED = '.';

    @Override // org.apache.commons.beanutils.expression.Resolver
    public int getIndex(String str) {
        char cCharAt;
        if (str != null && str.length() != 0) {
            for (int i = 0; i < str.length() && (cCharAt = str.charAt(i)) != '.' && cCharAt != '('; i++) {
                if (cCharAt == '[') {
                    int iIndexOf = str.indexOf(93, i);
                    if (iIndexOf < 0) {
                        throw new IllegalArgumentException("Missing End Delimiter");
                    }
                    String strSubstring = str.substring(i + 1, iIndexOf);
                    if (strSubstring.length() == 0) {
                        throw new IllegalArgumentException("No Index Value");
                    }
                    try {
                        return Integer.parseInt(strSubstring, 10);
                    } catch (Exception unused) {
                        throw new IllegalArgumentException("Invalid index value '" + strSubstring + FormatHelper.QUOTE);
                    }
                }
            }
        }
        return -1;
    }

    @Override // org.apache.commons.beanutils.expression.Resolver
    public String getKey(String str) {
        char cCharAt;
        if (str != null && str.length() != 0) {
            for (int i = 0; i < str.length() && (cCharAt = str.charAt(i)) != '.' && cCharAt != '['; i++) {
                if (cCharAt == '(') {
                    int iIndexOf = str.indexOf(41, i);
                    if (iIndexOf < 0) {
                        throw new IllegalArgumentException("Missing End Delimiter");
                    }
                    return str.substring(i + 1, iIndexOf);
                }
            }
        }
        return null;
    }

    @Override // org.apache.commons.beanutils.expression.Resolver
    public String getProperty(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }
        for (int i = 0; i < str.length(); i++) {
            char cCharAt = str.charAt(i);
            if (cCharAt == '.') {
                return str.substring(0, i);
            }
            if (cCharAt == '(' || cCharAt == '[') {
                return str.substring(0, i);
            }
        }
        return str;
    }

    @Override // org.apache.commons.beanutils.expression.Resolver
    public boolean hasNested(String str) {
        return (str == null || str.length() == 0 || remove(str) == null) ? false : true;
    }

    @Override // org.apache.commons.beanutils.expression.Resolver
    public boolean isIndexed(String str) {
        char cCharAt;
        if (str != null && str.length() != 0) {
            for (int i = 0; i < str.length() && (cCharAt = str.charAt(i)) != '.' && cCharAt != '('; i++) {
                if (cCharAt == '[') {
                    return true;
                }
            }
        }
        return false;
    }

    @Override // org.apache.commons.beanutils.expression.Resolver
    public boolean isMapped(String str) {
        char cCharAt;
        if (str != null && str.length() != 0) {
            for (int i = 0; i < str.length() && (cCharAt = str.charAt(i)) != '.' && cCharAt != '['; i++) {
                if (cCharAt == '(') {
                    return true;
                }
            }
        }
        return false;
    }

    @Override // org.apache.commons.beanutils.expression.Resolver
    public String next(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        boolean z = false;
        boolean z2 = false;
        for (int i = 0; i < str.length(); i++) {
            char cCharAt = str.charAt(i);
            if (z) {
                if (cCharAt == ']') {
                    return str.substring(0, i + 1);
                }
            } else if (z2) {
                if (cCharAt == ')') {
                    return str.substring(0, i + 1);
                }
            } else {
                if (cCharAt == '.') {
                    return str.substring(0, i);
                }
                if (cCharAt == '(') {
                    z2 = true;
                } else if (cCharAt == '[') {
                    z = true;
                }
            }
        }
        return str;
    }

    @Override // org.apache.commons.beanutils.expression.Resolver
    public String remove(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        String next = next(str);
        if (str.length() == next.length()) {
            return null;
        }
        int length = next.length();
        if (str.charAt(length) == '.') {
            length++;
        }
        return str.substring(length);
    }
}
