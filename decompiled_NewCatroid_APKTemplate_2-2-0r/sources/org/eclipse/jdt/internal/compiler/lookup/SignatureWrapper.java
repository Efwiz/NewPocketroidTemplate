package org.eclipse.jdt.internal.compiler.lookup;

import org.eclipse.jdt.core.compiler.CharOperation;

/* JADX INFO: loaded from: classes14.dex */
public class SignatureWrapper {
    public int bracket;
    public int end;
    public char[] signature;
    public int start;
    private boolean use15specifics;
    private boolean useExternalAnnotations;

    public SignatureWrapper(char[] cArr, boolean z) {
        this.signature = cArr;
        this.start = 0;
        this.bracket = -1;
        this.end = -1;
        this.use15specifics = z;
        if (z) {
            return;
        }
        removeTypeArguments();
    }

    public SignatureWrapper(char[] cArr, boolean z, boolean z2) {
        this.signature = cArr;
        this.start = 0;
        this.bracket = -1;
        this.end = -1;
        this.use15specifics = z;
        this.useExternalAnnotations = z2;
        if (z) {
            return;
        }
        removeTypeArguments();
    }

    public SignatureWrapper(char[] cArr) {
        this(cArr, true);
    }

    public boolean atEnd() {
        int i = this.start;
        return i < 0 || i >= this.signature.length;
    }

    public boolean isParameterized() {
        return this.bracket == this.end;
    }

    public int computeEnd() {
        int i;
        int i2 = this.start;
        if (this.useExternalAnnotations) {
            while (true) {
                char c = this.signature[i2];
                if (c == '0' || c == '1' || c == '@') {
                    if (i2 == this.start) {
                        break;
                    }
                    i2++;
                } else {
                    if (c != '[') {
                        break;
                    }
                    i2++;
                }
            }
        } else {
            while (this.signature[i2] == '[') {
                i2++;
            }
        }
        char[] cArr = this.signature;
        char c2 = cArr[i2];
        if (c2 == 'L' || c2 == 'T') {
            this.end = CharOperation.indexOf(';', cArr, this.start);
            int i3 = this.bracket;
            int i4 = this.start;
            if (i3 <= i4) {
                this.bracket = CharOperation.indexOf('<', this.signature, i4);
            }
            int i5 = this.bracket;
            if (i5 > this.start && i5 < this.end) {
                this.end = i5;
            } else if (this.end == -1) {
                this.end = this.signature.length + 1;
            }
        } else {
            this.end = i2;
        }
        if (this.use15specifics || (i = this.end) != this.bracket) {
            this.start = this.end + 1;
        } else {
            this.start = skipAngleContents(i) + 1;
            this.bracket = -1;
        }
        return this.end;
    }

    private void removeTypeArguments() {
        StringBuilder sb = new StringBuilder();
        int iSkipAngleContents = this.start;
        if (this.signature[0] == '<') {
            iSkipAngleContents++;
        }
        int i = 0;
        while (true) {
            char[] cArr = this.signature;
            if (iSkipAngleContents < cArr.length) {
                if (cArr[iSkipAngleContents] == '<') {
                    sb.append(cArr, i, iSkipAngleContents - i);
                    iSkipAngleContents = skipAngleContents(iSkipAngleContents);
                    i = iSkipAngleContents;
                }
                iSkipAngleContents++;
            } else {
                sb.append(cArr, i, iSkipAngleContents - i);
                char[] cArr2 = new char[sb.length()];
                this.signature = cArr2;
                sb.getChars(0, cArr2.length, cArr2, 0);
                return;
            }
        }
    }

    public int skipAngleContents(int i) {
        char[] cArr = this.signature;
        if (cArr[i] != '<') {
            return i;
        }
        int length = cArr.length;
        int i2 = i + 1;
        int i3 = 0;
        while (i2 < length) {
            char c = this.signature[i2];
            if (c == '<') {
                i3++;
            } else if (c == '>' && (i3 = i3 - 1) < 0) {
                return i2 + 1;
            }
            i2++;
        }
        return i2;
    }

    public char[] nextWord() {
        this.end = CharOperation.indexOf(';', this.signature, this.start);
        int i = this.bracket;
        int i2 = this.start;
        if (i <= i2) {
            this.bracket = CharOperation.indexOf('<', this.signature, i2);
        }
        int iIndexOf = CharOperation.indexOf('.', this.signature, this.start);
        int i3 = this.bracket;
        int i4 = this.start;
        if (i3 > i4 && i3 < this.end) {
            this.end = i3;
        }
        if (iIndexOf > i4 && iIndexOf < this.end) {
            this.end = iIndexOf;
        }
        char[] cArr = this.signature;
        int i5 = this.end;
        this.start = i5;
        return CharOperation.subarray(cArr, i4, i5);
    }

    public char[] nextName() {
        this.end = CharOperation.indexOf(';', this.signature, this.start);
        int i = this.bracket;
        int i2 = this.start;
        if (i <= i2) {
            this.bracket = CharOperation.indexOf('<', this.signature, i2);
        }
        int i3 = this.bracket;
        int i4 = this.start;
        if (i3 > i4 && i3 < this.end) {
            this.end = i3;
        }
        char[] cArr = this.signature;
        int i5 = this.end;
        this.start = i5;
        return CharOperation.subarray(cArr, i4, i5);
    }

    public char[] peekFullType() {
        int i = this.start;
        int i2 = this.bracket;
        int i3 = this.end;
        int iSkipAngleContents = skipAngleContents(computeEnd());
        this.start = i;
        this.bracket = i2;
        this.end = i3;
        return CharOperation.subarray(this.signature, i, iSkipAngleContents + 1);
    }

    public char[] getFrom(int i) {
        int i2 = this.end;
        int i3 = this.bracket;
        if (i2 == i3) {
            int iSkipAngleContents = skipAngleContents(i3);
            this.end = iSkipAngleContents;
            this.start = iSkipAngleContents + 1;
        }
        return CharOperation.subarray(this.signature, i, this.end + 1);
    }

    public char[] tail() {
        char[] cArr = this.signature;
        return CharOperation.subarray(cArr, this.start, cArr.length);
    }

    public String toString() {
        return String.valueOf(new String(this.signature)) + " @ " + this.start;
    }
}
