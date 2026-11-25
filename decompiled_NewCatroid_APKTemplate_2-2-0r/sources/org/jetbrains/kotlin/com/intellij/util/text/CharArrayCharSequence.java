package org.jetbrains.kotlin.com.intellij.util.text;

import org.jetbrains.kotlin.com.intellij.openapi.util.text.CharSequenceWithStringHash;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.Strings;

/* JADX INFO: loaded from: classes3.dex */
public class CharArrayCharSequence implements CharSequenceWithStringHash, CharSequenceBackedByArray {
    private transient int hash;
    protected final char[] myChars;
    protected final int myEnd;
    protected final int myStart;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 3) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 3) ? 2 : 3];
        if (i == 2 || i == 3) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/util/text/CharArrayCharSequence";
        } else if (i != 4) {
            objArr[0] = "chars";
        } else {
            objArr[0] = "dst";
        }
        if (i == 2 || i == 3) {
            objArr[1] = "getChars";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/text/CharArrayCharSequence";
        }
        if (i != 2 && i != 3) {
            if (i != 4) {
                objArr[2] = "<init>";
            } else {
                objArr[2] = "getChars";
            }
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 3) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public CharArrayCharSequence(char... cArr) {
        this(cArr, 0, cArr.length);
        if (cArr == null) {
            $$$reportNull$$$0(0);
        }
    }

    public CharArrayCharSequence(char[] cArr, int i, int i2) {
        if (cArr == null) {
            $$$reportNull$$$0(1);
        }
        if (i < 0 || i2 > cArr.length || i > i2) {
            throw new IndexOutOfBoundsException("chars.length:" + cArr.length + ", start:" + i + ", end:" + i2);
        }
        this.myChars = cArr;
        this.myStart = i;
        this.myEnd = i2;
    }

    @Override // java.lang.CharSequence
    public final int length() {
        return this.myEnd - this.myStart;
    }

    @Override // java.lang.CharSequence
    public final char charAt(int i) {
        return this.myChars[i + this.myStart];
    }

    @Override // java.lang.CharSequence
    public CharSequence subSequence(int i, int i2) {
        if (i == 0 && i2 == length()) {
            return this;
        }
        char[] cArr = this.myChars;
        int i3 = this.myStart;
        return new CharArrayCharSequence(cArr, i + i3, i3 + i2);
    }

    @Override // java.lang.CharSequence
    public String toString() {
        char[] cArr = this.myChars;
        int i = this.myStart;
        return new String(cArr, i, this.myEnd - i);
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.text.CharSequenceBackedByArray
    public char[] getChars() {
        if (this.myStart != 0) {
            char[] cArr = new char[length()];
            getChars(cArr, 0);
            return cArr;
        }
        char[] cArr2 = this.myChars;
        if (cArr2 == null) {
            $$$reportNull$$$0(2);
        }
        return cArr2;
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.text.CharSequenceBackedByArray
    public void getChars(char[] cArr, int i) {
        if (cArr == null) {
            $$$reportNull$$$0(4);
        }
        System.arraycopy(this.myChars, this.myStart, cArr, i, length());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        CharSequence charSequence = (CharSequence) obj;
        if (length() != charSequence.length()) {
            return false;
        }
        return CharArrayUtil.regionMatches(this.myChars, this.myStart, this.myEnd, charSequence);
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
