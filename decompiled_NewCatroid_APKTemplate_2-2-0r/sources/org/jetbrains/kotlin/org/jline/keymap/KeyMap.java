package org.jetbrains.kotlin.org.jline.keymap;

import com.beust.jcommander.Parameters;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import org.jetbrains.kotlin.org.jline.terminal.Terminal;
import org.jetbrains.kotlin.org.jline.utils.Curses;
import org.jetbrains.kotlin.org.jline.utils.InfoCmp;

/* JADX INFO: loaded from: classes4.dex */
public class KeyMap<T> {
    public static final Comparator<String> KEYSEQ_COMPARATOR = new Comparator() { // from class: org.jetbrains.kotlin.org.jline.keymap.KeyMap$$ExternalSyntheticLambda0
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            return KeyMap.lambda$static$0((String) obj, (String) obj2);
        }
    };
    private T nomatch;
    private T unicode;
    private Object[] mapping = new Object[128];
    private T anotherKey = null;
    private long ambiguousTimeout = 1000;

    /* JADX WARN: Code duplicated, block: B:37:0x0079 A[PHI: r4
      0x0079: PHI (r4v17 int) = (r4v11 int), (r4v10 int) binds: [B:85:0x011c, B:36:0x0076] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:81:0x010d A[PHI: r4 r7
      0x010d: PHI (r4v14 int) = (r4v13 int), (r4v10 int) binds: [B:90:0x012e, B:23:0x0056] A[DONT_GENERATE, DONT_INLINE]
      0x010d: PHI (r7v6 char) = (r7v3 char), (r7v0 char) binds: [B:90:0x012e, B:23:0x0056] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:82:0x0110  */
    public static String translate(String str) {
        int upperCase;
        char c;
        int iDigit;
        int iDigit2;
        int iDigit3;
        char cCharAt;
        if (!str.isEmpty() && (((cCharAt = str.charAt(0)) == '\'' || cCharAt == '\"') && str.charAt(str.length() - 1) == cCharAt)) {
            str = str.substring(1, str.length() - 1);
        }
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (i < str.length()) {
            char cCharAt2 = str.charAt(i);
            char upperCase2 = 127;
            char cCharAt3 = '\\';
            if (cCharAt2 == '\\') {
                int i2 = i + 1;
                if (i2 < str.length()) {
                    char cCharAt4 = str.charAt(i2);
                    if (cCharAt4 == 'C') {
                        i2 = i + 2;
                        if (i2 >= str.length()) {
                            i = i2;
                            cCharAt2 = cCharAt4;
                            continue;
                        } else {
                            cCharAt3 = str.charAt(i2);
                            if (cCharAt3 == '-') {
                                i2 = i + 3;
                                if (i2 >= str.length()) {
                                    i = i2;
                                    cCharAt2 = cCharAt3;
                                } else {
                                    cCharAt3 = str.charAt(i2);
                                }
                            }
                            if (cCharAt3 != '?') {
                                upperCase2 = (char) (Character.toUpperCase(cCharAt3) & 31);
                            }
                            i = i2;
                            cCharAt2 = upperCase2;
                        }
                    } else if (cCharAt4 == 'E') {
                        c = 27;
                        int i3 = i2;
                        cCharAt2 = c;
                        i = i3;
                    } else if (cCharAt4 != '\\') {
                        if (cCharAt4 == 'n') {
                            c = '\n';
                        } else if (cCharAt4 == 'r') {
                            c = '\r';
                        } else if (cCharAt4 == 'x') {
                            int i4 = i + 2;
                            int i5 = 0;
                            char c2 = 0;
                            while (i5 < 2 && i4 < str.length() && (iDigit = Character.digit(str.charAt(i4), 16)) >= 0) {
                                c2 = (char) ((c2 * 16) + iDigit);
                                i5++;
                                i4++;
                            }
                            i = i4 - 1;
                            upperCase = c2 & 255;
                            cCharAt2 = (char) upperCase;
                        } else if (cCharAt4 == 'a') {
                            c = 7;
                        } else if (cCharAt4 != 'b') {
                            switch (cCharAt4) {
                                case '0':
                                case '1':
                                case '2':
                                case '3':
                                case '4':
                                case '5':
                                case '6':
                                case '7':
                                    int i6 = 0;
                                    char c3 = 0;
                                    while (i6 < 3 && i2 < str.length() && (iDigit2 = Character.digit(str.charAt(i2), 8)) >= 0) {
                                        c3 = (char) ((c3 * '\b') + iDigit2);
                                        i6++;
                                        i2++;
                                    }
                                    i2--;
                                    c = (char) (c3 & 255);
                                    break;
                                default:
                                    switch (cCharAt4) {
                                        case 'd':
                                            i = i2;
                                            cCharAt2 = upperCase2;
                                            break;
                                        case 'e':
                                            c = 27;
                                            break;
                                        case 'f':
                                            c = '\f';
                                            break;
                                        default:
                                            switch (cCharAt4) {
                                                case 't':
                                                    c = '\t';
                                                    break;
                                                case 'u':
                                                    i += 2;
                                                    int i7 = 0;
                                                    char c4 = 0;
                                                    while (i7 < 4 && i < str.length() && (iDigit3 = Character.digit(str.charAt(i), 16)) >= 0) {
                                                        c4 = (char) ((c4 * 16) + iDigit3);
                                                        i7++;
                                                        i++;
                                                    }
                                                    cCharAt2 = c4;
                                                    continue;
                                                case 'v':
                                                    c = 11;
                                                    break;
                                                default:
                                                    i = i2;
                                                    cCharAt2 = cCharAt4;
                                                    continue;
                                            }
                                            break;
                                    }
                                    break;
                            }
                        } else {
                            i = i2;
                            cCharAt2 = '\b';
                        }
                        int i8 = i2;
                        cCharAt2 = c;
                        i = i8;
                    } else {
                        i = i2;
                        cCharAt2 = cCharAt3;
                    }
                } else {
                    return sb.toString();
                }
            } else if (cCharAt2 != '^') {
                continue;
            } else {
                i++;
                if (i < str.length()) {
                    cCharAt2 = str.charAt(i);
                    if (cCharAt2 != '^') {
                        if (cCharAt2 == '?') {
                            cCharAt2 = upperCase2;
                        } else {
                            upperCase = Character.toUpperCase(cCharAt2) & 31;
                            cCharAt2 = (char) upperCase;
                        }
                    }
                } else {
                    return sb.toString();
                }
            }
            sb.append(cCharAt2);
            i++;
        }
        return sb.toString();
    }

    public static Collection<String> range(String str) {
        String strSubstring;
        String[] strArrSplit = str.split(Parameters.DEFAULT_OPTION_PREFIXES);
        if (strArrSplit.length != 2) {
            return null;
        }
        strArrSplit[0] = translate(strArrSplit[0]);
        strArrSplit[1] = translate(strArrSplit[1]);
        if (strArrSplit[0].length() != strArrSplit[1].length()) {
            return null;
        }
        if (strArrSplit[0].length() > 1) {
            String str2 = strArrSplit[0];
            strSubstring = str2.substring(0, str2.length() - 1);
            if (!strArrSplit[1].startsWith(strSubstring)) {
                return null;
            }
        } else {
            strSubstring = "";
        }
        String str3 = strArrSplit[0];
        char cCharAt = str3.charAt(str3.length() - 1);
        String str4 = strArrSplit[1];
        char cCharAt2 = str4.charAt(str4.length() - 1);
        if (cCharAt > cCharAt2) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        while (cCharAt <= cCharAt2) {
            arrayList.add(strSubstring + cCharAt);
            cCharAt = (char) (cCharAt + 1);
        }
        return arrayList;
    }

    public static String esc() {
        return "\u001b";
    }

    public static String alt(char c) {
        return "\u001b" + c;
    }

    public static String alt(String str) {
        return "\u001b" + str;
    }

    public static String del() {
        return "\u007f";
    }

    public static String ctrl(char c) {
        return c == '?' ? del() : Character.toString((char) (Character.toUpperCase(c) & 31));
    }

    public static String key(Terminal terminal, InfoCmp.Capability capability) {
        try {
            String stringCapability = terminal.getStringCapability(capability);
            if (stringCapability == null) {
                return null;
            }
            StringWriter stringWriter = new StringWriter();
            Curses.tputs(stringWriter, stringCapability, new Object[0]);
            return stringWriter.toString();
        } catch (IOException unused) {
            return null;
        }
    }

    static /* synthetic */ int lambda$static$0(String str, String str2) {
        int length = str.length();
        int length2 = str2.length();
        int iMin = Math.min(length, length2);
        for (int i = 0; i < iMin; i++) {
            char cCharAt = str.charAt(i);
            char cCharAt2 = str2.charAt(i);
            if (cCharAt != cCharAt2) {
                int i2 = length - length2;
                return i2 != 0 ? i2 : cCharAt - cCharAt2;
            }
        }
        return length - length2;
    }

    public T getUnicode() {
        return this.unicode;
    }

    public void setUnicode(T t) {
        this.unicode = t;
    }

    public T getNomatch() {
        return this.nomatch;
    }

    public long getAmbiguousTimeout() {
        return this.ambiguousTimeout;
    }

    public void setAmbiguousTimeout(long j) {
        this.ambiguousTimeout = j;
    }

    public T getBound(CharSequence charSequence, int[] iArr) {
        iArr[0] = -1;
        if (charSequence != null && charSequence.length() > 0) {
            char cCharAt = charSequence.charAt(0);
            Object[] objArr = this.mapping;
            if (cCharAt >= objArr.length) {
                iArr[0] = Character.codePointCount(charSequence, 0, charSequence.length());
                return null;
            }
            Object obj = objArr[cCharAt];
            if (obj instanceof KeyMap) {
                return (T) ((KeyMap) this.mapping[cCharAt]).getBound(charSequence.subSequence(1, charSequence.length()), iArr);
            }
            if (obj != null) {
                iArr[0] = charSequence.length() - 1;
                return (T) this.mapping[cCharAt];
            }
            iArr[0] = charSequence.length();
            return this.anotherKey;
        }
        return this.anotherKey;
    }

    public T getBound(CharSequence charSequence) {
        int[] iArr = new int[1];
        T bound = getBound(charSequence, iArr);
        if (iArr[0] <= 0) {
            return bound;
        }
        return null;
    }

    public void bind(T t, CharSequence... charSequenceArr) {
        for (CharSequence charSequence : charSequenceArr) {
            bind(t, charSequence);
        }
    }

    public void bind(T t, Iterable<? extends CharSequence> iterable) {
        Iterator<? extends CharSequence> it = iterable.iterator();
        while (it.hasNext()) {
            bind(t, it.next());
        }
    }

    public void bind(T t, CharSequence charSequence) {
        if (charSequence != null) {
            if (t == null) {
                unbind(charSequence);
            } else {
                bind(this, charSequence, t, false);
            }
        }
    }

    public void unbind(CharSequence charSequence) {
        if (charSequence != null) {
            unbind(this, charSequence);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static <T> T unbind(KeyMap<T> keyMap, CharSequence charSequence) {
        if (charSequence == null || charSequence.length() <= 0) {
            return null;
        }
        int i = 0;
        KeyMap<T> keyMap2 = null;
        int i2 = 0;
        while (i2 < charSequence.length() - 1) {
            char cCharAt = charSequence.charAt(i2);
            Object[] objArr = ((KeyMap) keyMap).mapping;
            if (cCharAt > objArr.length) {
                return null;
            }
            Object obj = objArr[cCharAt];
            if (!(obj instanceof KeyMap)) {
                return null;
            }
            KeyMap<T> keyMap3 = (KeyMap) obj;
            i2++;
            keyMap2 = keyMap;
            keyMap = keyMap3;
        }
        char cCharAt2 = charSequence.charAt(charSequence.length() - 1);
        Object[] objArr2 = ((KeyMap) keyMap).mapping;
        if (cCharAt2 > objArr2.length) {
            return null;
        }
        T t = (T) objArr2[cCharAt2];
        if (t instanceof KeyMap) {
            KeyMap keyMap4 = (KeyMap) t;
            T t2 = keyMap4.anotherKey;
            keyMap4.anotherKey = null;
            return t2;
        }
        objArr2[cCharAt2] = null;
        int i3 = 0;
        while (true) {
            Object[] objArr3 = ((KeyMap) keyMap).mapping;
            if (i >= objArr3.length) {
                break;
            }
            if (objArr3[i] != null) {
                i3++;
            }
            i++;
        }
        if (i3 == 0 && keyMap2 != null) {
            ((KeyMap) keyMap2).mapping[charSequence.charAt(charSequence.length() - 2)] = ((KeyMap) keyMap).anotherKey;
        }
        return t;
    }

    private static <T> void bind(KeyMap<T> keyMap, CharSequence charSequence, T t, boolean z) {
        char cCharAt;
        if (charSequence == null || charSequence.length() <= 0) {
            return;
        }
        for (int i = 0; i < charSequence.length() && (cCharAt = charSequence.charAt(i)) < ((KeyMap) keyMap).mapping.length; i++) {
            if (i < charSequence.length() - 1) {
                if (!(((KeyMap) keyMap).mapping[cCharAt] instanceof KeyMap)) {
                    KeyMap keyMap2 = new KeyMap();
                    Object[] objArr = ((KeyMap) keyMap).mapping;
                    keyMap2.anotherKey = (T) objArr[cCharAt];
                    objArr[cCharAt] = keyMap2;
                }
                keyMap = (KeyMap) ((KeyMap) keyMap).mapping[cCharAt];
            } else {
                Object[] objArr2 = ((KeyMap) keyMap).mapping;
                Object obj = objArr2[cCharAt];
                if (obj instanceof KeyMap) {
                    ((KeyMap) obj).anotherKey = t;
                } else if (!z || obj == null) {
                    objArr2[cCharAt] = t;
                }
            }
        }
    }
}
