package io.github.rosemoe.sora.text;

import android.text.GetChars;
import io.github.rosemoe.sora.text.bidi.BidiRequirementChecker;
import io.github.rosemoe.sora.text.bidi.TextBidi;

/* JADX INFO: loaded from: classes11.dex */
public class ContentLine implements CharSequence, GetChars, BidiRequirementChecker {
    private int length;
    private LineSeparator lineSeparator;
    private int rtlAffectingCount;
    public int styleHash;
    public long timestamp;
    public char[] value;
    public float[] widthCache;

    public ContentLine() {
        this(true);
    }

    public ContentLine(CharSequence charSequence) {
        this(true);
        insert(0, charSequence);
    }

    public ContentLine(ContentLine contentLine) {
        this(contentLine.length + 16);
        int i = contentLine.length;
        this.length = i;
        this.rtlAffectingCount = contentLine.rtlAffectingCount;
        this.lineSeparator = contentLine.lineSeparator;
        System.arraycopy(contentLine.value, 0, this.value, 0, i);
    }

    public ContentLine(int i) {
        this.length = 0;
        this.value = new char[i];
    }

    private ContentLine(boolean z) {
        if (z) {
            this.length = 0;
            this.value = new char[32];
        }
    }

    private void checkIndex(int i) {
        if (i < 0 || i > this.length) {
            throw new StringIndexOutOfBoundsException("index = " + i + ", length = " + this.length);
        }
    }

    private void ensureCapacity(int i) {
        char[] cArr = this.value;
        if (cArr.length < i) {
            char[] cArr2 = new char[cArr.length * 2 < i ? i + 2 : cArr.length * 2];
            System.arraycopy(cArr, 0, cArr2, 0, this.length);
            this.value = cArr2;
        }
    }

    public ContentLine insert(int i, CharSequence charSequence) {
        if (charSequence == null) {
            charSequence = "null";
        }
        return insert(i, charSequence, 0, charSequence.length());
    }

    public ContentLine insert(int i, CharSequence charSequence, int i2, int i3) {
        if (charSequence == null) {
            charSequence = "null";
        }
        if (i < 0 || i > length()) {
            throw new IndexOutOfBoundsException("dstOffset " + i);
        }
        if (i2 < 0 || i3 < 0 || i2 > i3 || i3 > charSequence.length()) {
            throw new IndexOutOfBoundsException("start " + i2 + ", end " + i3 + ", s.length() " + charSequence.length());
        }
        int i4 = i3 - i2;
        ensureCapacity(this.length + i4);
        char[] cArr = this.value;
        System.arraycopy(cArr, i, cArr, i + i4, this.length - i);
        while (i2 < i3) {
            char cCharAt = charSequence.charAt(i2);
            int i5 = i + 1;
            this.value[i] = cCharAt;
            if (TextBidi.couldAffectRtl(cCharAt)) {
                this.rtlAffectingCount++;
            }
            i2++;
            i = i5;
        }
        this.length += i4;
        return this;
    }

    public ContentLine insert(int i, char c) {
        ensureCapacity(this.length + 1);
        int i2 = this.length;
        if (i < i2) {
            char[] cArr = this.value;
            System.arraycopy(cArr, i, cArr, i + 1, i2 - i);
        }
        if (TextBidi.couldAffectRtl(c)) {
            this.rtlAffectingCount++;
        }
        this.value[i] = c;
        this.length++;
        return this;
    }

    public ContentLine delete(int i, int i2) {
        if (i < 0) {
            throw new StringIndexOutOfBoundsException(i);
        }
        int i3 = this.length;
        if (i2 > i3) {
            i2 = i3;
        }
        if (i > i2) {
            throw new StringIndexOutOfBoundsException();
        }
        int i4 = i2 - i;
        if (i4 > 0) {
            for (int i5 = i; i5 < i2; i5++) {
                if (TextBidi.couldAffectRtl(this.value[i5])) {
                    this.rtlAffectingCount--;
                }
            }
            char[] cArr = this.value;
            System.arraycopy(cArr, i + i4, cArr, i, this.length - i2);
            this.length -= i4;
        }
        return this;
    }

    @Override // io.github.rosemoe.sora.text.bidi.BidiRequirementChecker
    public boolean mayNeedBidi() {
        return this.rtlAffectingCount > 0;
    }

    public ContentLine append(CharSequence charSequence) {
        return insert(this.length, charSequence);
    }

    @Override // java.lang.CharSequence
    public int length() {
        return this.length;
    }

    @Override // java.lang.CharSequence
    public char charAt(int i) {
        if (i >= this.length) {
            if (getLineSeparator().getLength() > 0) {
                return getLineSeparator().getContent().charAt(i - this.length);
            }
            return '\n';
        }
        return this.value[i];
    }

    @Override // java.lang.CharSequence
    public ContentLine subSequence(int i, int i2) {
        checkIndex(i);
        checkIndex(i2);
        if (i2 < i) {
            throw new StringIndexOutOfBoundsException("start is bigger than end");
        }
        int i3 = i2 - i;
        char[] cArr = new char[i3 + 16];
        System.arraycopy(this.value, i, cArr, 0, i3);
        ContentLine contentLine = new ContentLine(false);
        contentLine.value = cArr;
        contentLine.length = i3;
        if (this.rtlAffectingCount > 0) {
            for (int i4 = 0; i4 < contentLine.length; i4++) {
                if (TextBidi.couldAffectRtl(cArr[i4])) {
                    contentLine.rtlAffectingCount++;
                }
            }
        }
        return contentLine;
    }

    public void appendTo(StringBuilder sb) {
        sb.append(this.value, 0, this.length);
    }

    @Override // java.lang.CharSequence
    public String toString() {
        return new String(this.value, 0, this.length);
    }

    public String toStringWithNewline() {
        int length = this.value.length;
        int i = this.length;
        if (length == i) {
            ensureCapacity(i + 1);
        }
        this.value[this.length] = '\n';
        return new String(this.value, 0, this.length + 1);
    }

    public char[] getRawData() {
        return this.value;
    }

    @Override // android.text.GetChars
    public void getChars(int i, int i2, char[] cArr, int i3) {
        if (i < 0) {
            throw new StringIndexOutOfBoundsException(i);
        }
        if (i2 < 0 || i2 > this.length) {
            throw new StringIndexOutOfBoundsException(i2);
        }
        if (i > i2) {
            throw new StringIndexOutOfBoundsException("srcBegin > srcEnd");
        }
        System.arraycopy(this.value, i, cArr, i3, i2 - i);
    }

    public void setLineSeparator(LineSeparator lineSeparator) {
        this.lineSeparator = lineSeparator;
    }

    public LineSeparator getLineSeparator() {
        LineSeparator lineSeparator = this.lineSeparator;
        return lineSeparator == null ? LineSeparator.NONE : lineSeparator;
    }
}
