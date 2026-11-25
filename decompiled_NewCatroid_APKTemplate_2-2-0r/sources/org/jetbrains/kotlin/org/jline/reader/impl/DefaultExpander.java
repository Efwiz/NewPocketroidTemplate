package org.jetbrains.kotlin.org.jline.reader.impl;

import java.util.ListIterator;
import org.jetbrains.kotlin.org.jline.reader.Expander;
import org.jetbrains.kotlin.org.jline.reader.History;

/* JADX INFO: loaded from: classes4.dex */
public class DefaultExpander implements Expander {
    @Override // org.jetbrains.kotlin.org.jline.reader.Expander
    public String expandVar(String str) {
        return str;
    }

    /* JADX WARN: Code duplicated, block: B:38:0x008b  */
    /* JADX WARN: Code duplicated, block: B:77:0x0143  */
    @Override // org.jetbrains.kotlin.org.jline.reader.Expander
    public String expandHistory(History history, String str) {
        String strTrim;
        boolean z;
        char cCharAt;
        String str2 = "!-";
        StringBuilder sb = new StringBuilder();
        int i = 0;
        int i2 = 0;
        boolean z2 = false;
        boolean z3 = false;
        while (i < str.length()) {
            char cCharAt2 = str.charAt(i);
            if (i2 > 0) {
                i2--;
                z2 = i2 >= 0;
                sb.append(cCharAt2);
            } else if (z2) {
                if (cCharAt2 == 'u') {
                    i2 = 4;
                } else {
                    z2 = false;
                }
                sb.append(cCharAt2);
            } else if (cCharAt2 == '\'') {
                z3 = !z3;
                sb.append(cCharAt2);
            } else if (z3) {
                sb.append(cCharAt2);
            } else {
                if (cCharAt2 == '!') {
                    int i3 = i + 1;
                    if (i3 < str.length()) {
                        char cCharAt3 = str.charAt(i3);
                        if (cCharAt3 == '\t') {
                            sb.append('!');
                            sb.append(cCharAt3);
                            strTrim = null;
                            i = i3;
                        } else {
                            if (cCharAt3 == '-') {
                                i3 = i + 2;
                                z = true;
                            } else if (cCharAt3 != '?') {
                                if (cCharAt3 != ' ') {
                                    if (cCharAt3 != '!') {
                                        if (cCharAt3 == '#') {
                                            sb.append(sb.toString());
                                        } else if (cCharAt3 == '$') {
                                            if (history.size() == 0) {
                                                throw new IllegalArgumentException("!$: event not found");
                                            }
                                            strTrim = history.get(history.index() - 1).trim();
                                            int iLastIndexOf = strTrim.lastIndexOf(32);
                                            if (iLastIndexOf != -1) {
                                                strTrim = strTrim.substring(iLastIndexOf + 1);
                                            }
                                        } else {
                                            switch (cCharAt3) {
                                                case '0':
                                                case '1':
                                                case '2':
                                                case '3':
                                                case '4':
                                                case '5':
                                                case '6':
                                                case '7':
                                                case '8':
                                                case '9':
                                                    z = false;
                                                    break;
                                                default:
                                                    String strSubstring = str.substring(i3);
                                                    int length = str.length();
                                                    int iSearchBackwards = searchBackwards(history, strSubstring, history.index(), true);
                                                    if (iSearchBackwards < 0) {
                                                        throw new IllegalArgumentException("!" + strSubstring + ": event not found");
                                                    }
                                                    strTrim = history.get(iSearchBackwards);
                                                    i = length;
                                                    break;
                                                    break;
                                            }
                                        }
                                    } else {
                                        if (history.size() == 0) {
                                            throw new IllegalArgumentException("!!: event not found");
                                        }
                                        strTrim = history.get(history.index() - 1);
                                    }
                                    i = i3;
                                } else {
                                    sb.append('!');
                                    sb.append(cCharAt3);
                                }
                                strTrim = null;
                                i = i3;
                            } else {
                                int i4 = i + 2;
                                int iIndexOf = str.indexOf(63, i4);
                                if (iIndexOf < 0) {
                                    iIndexOf = str.length();
                                }
                                String strSubstring2 = str.substring(i4, iIndexOf);
                                int iSearchBackwards2 = searchBackwards(history, strSubstring2, history.index(), false);
                                if (iSearchBackwards2 < 0) {
                                    throw new IllegalArgumentException("!?" + strSubstring2 + ": event not found");
                                }
                                strTrim = history.get(iSearchBackwards2);
                                i = iIndexOf;
                            }
                            i = i3;
                            while (i < str.length() && (cCharAt = str.charAt(i)) >= '0' && cCharAt <= '9') {
                                i++;
                            }
                            try {
                                int i5 = Integer.parseInt(str.substring(i3, i));
                                if (z && i5 > 0 && i5 <= history.size()) {
                                    strTrim = history.get(history.index() - i5);
                                } else if (!z && i5 > history.index() - history.size() && i5 <= history.index()) {
                                    strTrim = history.get(i5 - 1);
                                } else {
                                    StringBuilder sb2 = new StringBuilder();
                                    if (!z) {
                                        str2 = "!";
                                    }
                                    throw new IllegalArgumentException(sb2.append(str2).append(str.substring(i3, i)).append(": event not found").toString());
                                }
                            } catch (NumberFormatException unused) {
                                StringBuilder sb3 = new StringBuilder();
                                if (!z) {
                                    str2 = "!";
                                }
                                throw new IllegalArgumentException(sb3.append(str2).append(str.substring(i3, i)).append(": event not found").toString());
                            }
                        }
                        if (strTrim != null) {
                            sb.append(strTrim);
                        }
                    } else {
                        sb.append(cCharAt2);
                    }
                } else if (cCharAt2 == '\\') {
                    sb.append(cCharAt2);
                    z2 = true;
                } else if (cCharAt2 == '^' && i == 0) {
                    int i6 = i + 1;
                    int iIndexOf2 = str.indexOf(94, i6);
                    int i7 = iIndexOf2 + 1;
                    int iIndexOf3 = str.indexOf(94, i7);
                    if (iIndexOf3 < 0) {
                        iIndexOf3 = str.length();
                    }
                    if (iIndexOf2 > 0 && iIndexOf3 > 0) {
                        sb.append(history.get(history.index() - 1).replace(str.substring(i6, iIndexOf2), str.substring(i7, iIndexOf3)));
                        i = iIndexOf3 + 1;
                    } else {
                        sb.append(cCharAt2);
                    }
                } else {
                    sb.append(cCharAt2);
                }
                i++;
            }
            i++;
        }
        return sb.toString();
    }

    protected int searchBackwards(History history, String str, int i, boolean z) {
        ListIterator<History.Entry> it = history.iterator(i);
        while (it.hasPrevious()) {
            History.Entry entryPrevious = it.previous();
            if (z) {
                if (entryPrevious.line().startsWith(str)) {
                    return entryPrevious.index();
                }
            } else if (entryPrevious.line().contains(str)) {
                return entryPrevious.index();
            }
        }
        return -1;
    }
}
