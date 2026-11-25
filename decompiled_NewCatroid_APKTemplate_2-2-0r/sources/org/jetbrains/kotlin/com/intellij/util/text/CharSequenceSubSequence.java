package org.jetbrains.kotlin.com.intellij.util.text;

import org.jetbrains.kotlin.com.intellij.openapi.util.text.CharSequenceWithStringHash;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.Strings;

/* JADX INFO: loaded from: classes3.dex */
public class CharSequenceSubSequence implements CharSequenceWithStringHash, CharArrayExternalizable, CharSequence {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private transient int hash;
    private final CharSequence myChars;
    private final int myEnd;
    private final int myStart;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 3 || i == 4) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 3 || i == 4) ? 2 : 3];
        if (i == 2 || i == 3 || i == 4) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/util/text/CharSequenceSubSequence";
        } else if (i != 5) {
            objArr[0] = "chars";
        } else {
            objArr[0] = "dest";
        }
        if (i == 2) {
            objArr[1] = "subSequence";
        } else if (i == 3) {
            objArr[1] = "toString";
        } else if (i != 4) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/text/CharSequenceSubSequence";
        } else {
            objArr[1] = "getBaseSequence";
        }
        if (i != 2 && i != 3 && i != 4) {
            if (i != 5) {
                objArr[2] = "<init>";
            } else {
                objArr[2] = "getChars";
            }
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 3 && i != 4) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public CharSequenceSubSequence(CharSequence charSequence, int i, int i2) {
        if (charSequence == null) {
            $$$reportNull$$$0(1);
        }
        if (i < 0 || i2 > charSequence.length() || i > i2) {
            throw new IndexOutOfBoundsException("chars sequence.length:" + charSequence.length() + ", start:" + i + ", end:" + i2);
        }
        this.myChars = charSequence;
        this.myStart = i;
        this.myEnd = i2;
    }

    @Override // java.lang.CharSequence
    public final int length() {
        return this.myEnd - this.myStart;
    }

    @Override // java.lang.CharSequence
    public final char charAt(int i) {
        return this.myChars.charAt(i + this.myStart);
    }

    @Override // java.lang.CharSequence
    public CharSequence subSequence(int i, int i2) {
        int i3 = this.myStart;
        return (i == i3 && i2 == this.myEnd) ? this : new CharSequenceSubSequence(this.myChars, i + i3, i3 + i2);
    }

    @Override // java.lang.CharSequence
    public String toString() {
        CharSequence charSequence = this.myChars;
        if (!(charSequence instanceof String)) {
            return new String(CharArrayUtil.fromSequence(this.myChars, this.myStart, this.myEnd));
        }
        String strSubstring = ((String) charSequence).substring(this.myStart, this.myEnd);
        if (strSubstring == null) {
            $$$reportNull$$$0(3);
        }
        return strSubstring;
    }

    CharSequence getBaseSequence() {
        CharSequence charSequence = this.myChars;
        if (charSequence == null) {
            $$$reportNull$$$0(4);
        }
        return charSequence;
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.text.CharArrayExternalizable
    public void getChars(int i, int i2, char[] cArr, int i3) {
        if (cArr == null) {
            $$$reportNull$$$0(5);
        }
        CharArrayUtil.getChars(this.myChars, cArr, this.myStart + i, i3, i2 - i);
    }

    public int hashCode() {
        int i = this.hash;
        if (i != 0) {
            return i;
        }
        int iStringHashCode = Strings.stringHashCode(this.myChars, this.myStart, this.myEnd);
        this.hash = iStringHashCode;
        return iStringHashCode;
    }
}
