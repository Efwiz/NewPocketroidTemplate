package org.jetbrains.kotlin.com.intellij.util.text;

import java.nio.charset.StandardCharsets;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.CharSequenceWithStringHash;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.Strings;

/* JADX INFO: loaded from: classes3.dex */
public final class ByteArrayCharSequence implements CharSequenceWithStringHash {
    private static final boolean JAVA_9;
    private transient int hash;
    private final byte[] myChars;
    private final int myEnd;
    private final int myStart;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 3 || i == 5 || i == 6 || i == 7) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 3 || i == 5 || i == 6 || i == 7) ? 2 : 3];
        switch (i) {
            case 2:
                objArr[0] = "name";
                break;
            case 3:
            case 5:
            case 6:
            case 7:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/util/text/ByteArrayCharSequence";
                break;
            case 4:
                objArr[0] = "string";
                break;
            case 8:
                objArr[0] = "seq";
                break;
            default:
                objArr[0] = "chars";
                break;
        }
        if (i == 3) {
            objArr[1] = "convertToBytesIfAsciiString";
        } else if (i == 5 || i == 6) {
            objArr[1] = "convertToBytesIfPossible";
        } else if (i != 7) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/text/ByteArrayCharSequence";
        } else {
            objArr[1] = "getBytes";
        }
        switch (i) {
            case 2:
                objArr[2] = "convertToBytesIfAsciiString";
                break;
            case 3:
            case 5:
            case 6:
            case 7:
                break;
            case 4:
                objArr[2] = "convertToBytesIfPossible";
                break;
            case 8:
                objArr[2] = "toBytesIfPossible";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 3 && i != 5 && i != 6 && i != 7) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public ByteArrayCharSequence(byte[] bArr, int i, int i2) {
        if (bArr == null) {
            $$$reportNull$$$0(1);
        }
        this.myChars = bArr;
        this.myStart = i;
        this.myEnd = i2;
    }

    public int hashCode() {
        int i = this.hash;
        if (i != 0) {
            return i;
        }
        int iStringHashCode = Strings.stringHashCode(this, 0, length());
        this.hash = iStringHashCode;
        return iStringHashCode;
    }

    @Override // java.lang.CharSequence
    public int length() {
        return this.myEnd - this.myStart;
    }

    @Override // java.lang.CharSequence
    public char charAt(int i) {
        return (char) (this.myChars[i + this.myStart] & 255);
    }

    @Override // java.lang.CharSequence
    public CharSequence subSequence(int i, int i2) {
        if (i == 0 && i2 == length()) {
            return this;
        }
        byte[] bArr = this.myChars;
        int i3 = this.myStart;
        return new ByteArrayCharSequence(bArr, i + i3, i3 + i2);
    }

    @Override // java.lang.CharSequence
    public String toString() {
        return new String(this.myChars, this.myStart, length(), StandardCharsets.ISO_8859_1);
    }

    public void getChars(int i, int i2, char[] cArr, int i3) {
        for (int i4 = i; i4 < i2; i4++) {
            cArr[(i4 - i) + i3] = (char) (this.myChars[this.myStart + i4] & 255);
        }
    }

    static {
        boolean z = false;
        try {
            Class.class.getMethod("getModule", new Class[0]);
            z = true;
        } catch (Throwable unused) {
        }
        JAVA_9 = z;
    }
}
