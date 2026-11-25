package org.jetbrains.kotlin.org.jline.utils;

import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.Objects;

/* JADX INFO: loaded from: classes4.dex */
public class AttributedString extends AttributedCharSequence {
    public static final AttributedString EMPTY = new AttributedString("");
    public static final AttributedString NEWLINE = new AttributedString("\n");
    final char[] buffer;
    final int end;
    final int start;
    final int[] style;

    public AttributedString(CharSequence charSequence) {
        this(charSequence, 0, charSequence.length(), (AttributedStyle) null);
    }

    public AttributedString(CharSequence charSequence, int i, int i2) {
        this(charSequence, i, i2, (AttributedStyle) null);
    }

    public AttributedString(CharSequence charSequence, AttributedStyle attributedStyle) {
        this(charSequence, 0, charSequence.length(), attributedStyle);
    }

    public AttributedString(CharSequence charSequence, int i, int i2, AttributedStyle attributedStyle) {
        if (i2 < i) {
            throw new InvalidParameterException();
        }
        int i3 = 0;
        if (charSequence instanceof AttributedString) {
            AttributedString attributedString = (AttributedString) charSequence;
            this.buffer = attributedString.buffer;
            if (attributedStyle != null) {
                this.style = (int[]) attributedString.style.clone();
                while (true) {
                    int[] iArr = this.style;
                    if (i3 >= iArr.length) {
                        break;
                    }
                    iArr[i3] = (iArr[i3] & (~attributedStyle.getMask())) | attributedStyle.getStyle();
                    i3++;
                }
            } else {
                this.style = attributedString.style;
            }
            this.start = attributedString.start + i;
            this.end = attributedString.start + i2;
            return;
        }
        if (charSequence instanceof AttributedStringBuilder) {
            AttributedString attributedStringSubSequence = ((AttributedStringBuilder) charSequence).subSequence(i, i2);
            this.buffer = attributedStringSubSequence.buffer;
            this.style = attributedStringSubSequence.style;
            if (attributedStyle != null) {
                while (true) {
                    int[] iArr2 = this.style;
                    if (i3 >= iArr2.length) {
                        break;
                    }
                    iArr2[i3] = (iArr2[i3] & (~attributedStyle.getMask())) | attributedStyle.getStyle();
                    i3++;
                }
            }
            this.start = attributedStringSubSequence.start;
            this.end = attributedStringSubSequence.end;
            return;
        }
        int i4 = i2 - i;
        this.buffer = new char[i4];
        for (int i5 = 0; i5 < i4; i5++) {
            this.buffer[i5] = charSequence.charAt(i + i5);
        }
        int[] iArr3 = new int[i4];
        this.style = iArr3;
        if (attributedStyle != null) {
            Arrays.fill(iArr3, attributedStyle.getStyle());
        }
        this.start = 0;
        this.end = i4;
    }

    AttributedString(char[] cArr, int[] iArr, int i, int i2) {
        this.buffer = cArr;
        this.style = iArr;
        this.start = i;
        this.end = i2;
    }

    public static AttributedString fromAnsi(String str) {
        return fromAnsi(str, 0);
    }

    public static AttributedString fromAnsi(String str, int i) {
        if (str == null) {
            return null;
        }
        AttributedStringBuilder attributedStringBuilder = new AttributedStringBuilder(str.length());
        attributedStringBuilder.tabs(i);
        attributedStringBuilder.appendAnsi(str);
        return attributedStringBuilder.toAttributedString();
    }

    @Override // org.jetbrains.kotlin.org.jline.utils.AttributedCharSequence
    protected char[] buffer() {
        return this.buffer;
    }

    @Override // org.jetbrains.kotlin.org.jline.utils.AttributedCharSequence
    protected int offset() {
        return this.start;
    }

    @Override // java.lang.CharSequence
    public int length() {
        return this.end - this.start;
    }

    @Override // org.jetbrains.kotlin.org.jline.utils.AttributedCharSequence
    public AttributedStyle styleAt(int i) {
        int[] iArr = this.style;
        int i2 = this.start;
        return new AttributedStyle(iArr[i2 + i], iArr[i2 + i]);
    }

    @Override // org.jetbrains.kotlin.org.jline.utils.AttributedCharSequence
    int styleCodeAt(int i) {
        return this.style[this.start + i];
    }

    @Override // org.jetbrains.kotlin.org.jline.utils.AttributedCharSequence, java.lang.CharSequence
    public AttributedString subSequence(int i, int i2) {
        return new AttributedString(this, i, i2);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        AttributedString attributedString = (AttributedString) obj;
        int i = this.end;
        int i2 = this.start;
        int i3 = i - i2;
        int i4 = attributedString.end;
        int i5 = attributedString.start;
        if (i3 == i4 - i5 && arrEq(this.buffer, attributedString.buffer, i2, i5, i - i2)) {
            int[] iArr = this.style;
            int[] iArr2 = attributedString.style;
            int i6 = this.start;
            if (arrEq(iArr, iArr2, i6, attributedString.start, this.end - i6)) {
                return true;
            }
        }
        return false;
    }

    private boolean arrEq(char[] cArr, char[] cArr2, int i, int i2, int i3) {
        for (int i4 = 0; i4 < i3; i4++) {
            if (cArr[i + i4] != cArr2[i2 + i4]) {
                return false;
            }
        }
        return true;
    }

    private boolean arrEq(int[] iArr, int[] iArr2, int i, int i2, int i3) {
        for (int i4 = 0; i4 < i3; i4++) {
            if (iArr[i + i4] != iArr2[i2 + i4]) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        return (((((Arrays.hashCode(this.buffer) * 31) + Arrays.hashCode(this.style)) * 31) + this.start) * 31) + this.end;
    }

    public static AttributedString join(AttributedString attributedString, Iterable<AttributedString> iterable) {
        Objects.requireNonNull(iterable);
        AttributedStringBuilder attributedStringBuilder = new AttributedStringBuilder();
        int i = 0;
        for (AttributedString attributedString2 : iterable) {
            int i2 = i + 1;
            if (i > 0 && attributedString != null) {
                attributedStringBuilder.append(attributedString);
            }
            attributedStringBuilder.append(attributedString2);
            i = i2;
        }
        return attributedStringBuilder.toAttributedString();
    }
}
