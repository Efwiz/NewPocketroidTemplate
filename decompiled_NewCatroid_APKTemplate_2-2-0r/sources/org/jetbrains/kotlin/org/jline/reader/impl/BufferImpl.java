package org.jetbrains.kotlin.org.jline.reader.impl;

import java.util.Objects;
import org.jetbrains.kotlin.org.jline.reader.Buffer;

/* JADX INFO: loaded from: classes4.dex */
public class BufferImpl implements Buffer {
    private int[] buffer;
    private int cursor;
    private int cursorCol;
    private int g0;
    private int g1;

    public BufferImpl() {
        this(64);
    }

    public BufferImpl(int i) {
        this.cursor = 0;
        this.cursorCol = -1;
        int[] iArr = new int[i];
        this.buffer = iArr;
        this.g0 = 0;
        this.g1 = iArr.length;
    }

    @Override // org.jetbrains.kotlin.org.jline.reader.Buffer
    public BufferImpl copy() {
        BufferImpl bufferImpl = new BufferImpl();
        bufferImpl.copyFrom(this);
        return bufferImpl;
    }

    @Override // org.jetbrains.kotlin.org.jline.reader.Buffer
    public int cursor() {
        return this.cursor;
    }

    @Override // org.jetbrains.kotlin.org.jline.reader.Buffer
    public int length() {
        return this.buffer.length - (this.g1 - this.g0);
    }

    @Override // org.jetbrains.kotlin.org.jline.reader.Buffer
    public boolean currChar(int i) {
        if (this.cursor == length()) {
            return false;
        }
        this.buffer[adjust(this.cursor)] = i;
        return true;
    }

    @Override // org.jetbrains.kotlin.org.jline.reader.Buffer
    public int currChar() {
        if (this.cursor == length()) {
            return 0;
        }
        return atChar(this.cursor);
    }

    @Override // org.jetbrains.kotlin.org.jline.reader.Buffer
    public int prevChar() {
        int i = this.cursor;
        if (i <= 0) {
            return 0;
        }
        return atChar(i - 1);
    }

    @Override // org.jetbrains.kotlin.org.jline.reader.Buffer
    public int nextChar() {
        if (this.cursor >= length() - 1) {
            return 0;
        }
        return atChar(this.cursor + 1);
    }

    @Override // org.jetbrains.kotlin.org.jline.reader.Buffer
    public int atChar(int i) {
        if (i < 0 || i >= length()) {
            return 0;
        }
        return this.buffer[adjust(i)];
    }

    private int adjust(int i) {
        int i2 = this.g0;
        return i >= i2 ? (i + this.g1) - i2 : i;
    }

    @Override // org.jetbrains.kotlin.org.jline.reader.Buffer
    public void write(int i) {
        write(new int[]{i});
    }

    @Override // org.jetbrains.kotlin.org.jline.reader.Buffer
    public void write(CharSequence charSequence) {
        Objects.requireNonNull(charSequence);
        write(charSequence.codePoints().toArray());
    }

    @Override // org.jetbrains.kotlin.org.jline.reader.Buffer
    public void write(CharSequence charSequence, boolean z) {
        Objects.requireNonNull(charSequence);
        int[] array = charSequence.codePoints().toArray();
        if (z) {
            delete(array.length);
        }
        write(array);
    }

    private void write(int[] iArr) {
        moveGapToCursor();
        int length = length() + iArr.length;
        int length2 = this.buffer.length;
        if (length2 < length) {
            while (length2 < length) {
                length2 *= 2;
            }
            int[] iArr2 = new int[length2];
            System.arraycopy(this.buffer, 0, iArr2, 0, this.g0);
            int[] iArr3 = this.buffer;
            int i = this.g1;
            System.arraycopy(iArr3, i, iArr2, (i + length2) - iArr3.length, iArr3.length - i);
            this.g1 += length2 - this.buffer.length;
            this.buffer = iArr2;
        }
        System.arraycopy(iArr, 0, this.buffer, this.cursor, iArr.length);
        this.g0 += iArr.length;
        this.cursor += iArr.length;
        this.cursorCol = -1;
    }

    @Override // org.jetbrains.kotlin.org.jline.reader.Buffer
    public boolean clear() {
        if (length() == 0) {
            return false;
        }
        this.g0 = 0;
        this.g1 = this.buffer.length;
        this.cursor = 0;
        this.cursorCol = -1;
        return true;
    }

    @Override // org.jetbrains.kotlin.org.jline.reader.Buffer
    public String substring(int i) {
        return substring(i, length());
    }

    @Override // org.jetbrains.kotlin.org.jline.reader.Buffer
    public String substring(int i, int i2) {
        if (i >= i2 || i < 0 || i2 > length()) {
            return "";
        }
        int i3 = this.g0;
        if (i2 <= i3) {
            return new String(this.buffer, i, i2 - i);
        }
        if (i > i3) {
            return new String(this.buffer, (this.g1 - this.g0) + i, i2 - i);
        }
        int[] iArr = (int[]) this.buffer.clone();
        int i4 = this.g1;
        System.arraycopy(iArr, i4, iArr, this.g0, iArr.length - i4);
        return new String(iArr, i, i2 - i);
    }

    @Override // org.jetbrains.kotlin.org.jline.reader.Buffer
    public String upToCursor() {
        return substring(0, this.cursor);
    }

    @Override // org.jetbrains.kotlin.org.jline.reader.Buffer
    public boolean cursor(int i) {
        int i2 = this.cursor;
        return i == i2 || move(i - i2) != 0;
    }

    @Override // org.jetbrains.kotlin.org.jline.reader.Buffer
    public int move(int i) {
        int i2 = this.cursor;
        if (i2 == 0 && i <= 0) {
            return 0;
        }
        if (i2 == length() && i >= 0) {
            return 0;
        }
        int i3 = this.cursor;
        if (i3 + i < 0) {
            i = -i3;
        } else if (i3 + i > length()) {
            i = length() - this.cursor;
        }
        this.cursor += i;
        this.cursorCol = -1;
        return i;
    }

    @Override // org.jetbrains.kotlin.org.jline.reader.Buffer
    public boolean up() {
        int cursorCol = getCursorCol();
        int i = this.cursor - 1;
        while (i >= 0 && atChar(i) != 10) {
            i--;
        }
        if (i < 0) {
            return false;
        }
        int i2 = i - 1;
        while (i2 >= 0 && atChar(i2) != 10) {
            i2--;
        }
        this.cursor = Math.min(i2 + cursorCol + 1, i);
        return true;
    }

    @Override // org.jetbrains.kotlin.org.jline.reader.Buffer
    public boolean down() {
        int cursorCol = getCursorCol();
        int i = this.cursor;
        while (i < length() && atChar(i) != 10) {
            i++;
        }
        if (i >= length()) {
            return false;
        }
        int i2 = i + 1;
        while (i2 < length() && atChar(i2) != 10) {
            i2++;
        }
        this.cursor = Math.min(i + cursorCol + 1, i2);
        return true;
    }

    @Override // org.jetbrains.kotlin.org.jline.reader.Buffer
    public boolean moveXY(int i, int i2) {
        int i3 = 0;
        while (prevChar() != 10 && move(-1) == -1) {
            i3++;
        }
        this.cursorCol = 0;
        while (i2 < 0) {
            up();
            i2++;
        }
        while (i2 > 0) {
            down();
            i2--;
        }
        int iMax = Math.max(i3 + i, 0);
        for (int i4 = 0; i4 < iMax && move(1) == 1 && currChar() != 10; i4++) {
        }
        this.cursorCol = iMax;
        return true;
    }

    private int getCursorCol() {
        if (this.cursorCol < 0) {
            this.cursorCol = 0;
            int i = this.cursor - 1;
            while (i >= 0 && atChar(i) != 10) {
                i--;
            }
            this.cursorCol = (this.cursor - i) - 1;
        }
        return this.cursorCol;
    }

    @Override // org.jetbrains.kotlin.org.jline.reader.Buffer
    public int backspace(int i) {
        int iMax = Math.max(Math.min(this.cursor, i), 0);
        moveGapToCursor();
        this.cursor -= iMax;
        this.g0 -= iMax;
        this.cursorCol = -1;
        return iMax;
    }

    @Override // org.jetbrains.kotlin.org.jline.reader.Buffer
    public boolean backspace() {
        return backspace(1) == 1;
    }

    @Override // org.jetbrains.kotlin.org.jline.reader.Buffer
    public int delete(int i) {
        int iMax = Math.max(Math.min(length() - this.cursor, i), 0);
        moveGapToCursor();
        this.g1 += iMax;
        this.cursorCol = -1;
        return iMax;
    }

    @Override // org.jetbrains.kotlin.org.jline.reader.Buffer
    public boolean delete() {
        return delete(1) == 1;
    }

    @Override // org.jetbrains.kotlin.org.jline.reader.Buffer
    public String toString() {
        return substring(0, length());
    }

    @Override // org.jetbrains.kotlin.org.jline.reader.Buffer
    public void copyFrom(Buffer buffer) {
        if (!(buffer instanceof BufferImpl)) {
            throw new IllegalStateException();
        }
        BufferImpl bufferImpl = (BufferImpl) buffer;
        this.g0 = bufferImpl.g0;
        this.g1 = bufferImpl.g1;
        this.buffer = (int[]) bufferImpl.buffer.clone();
        this.cursor = bufferImpl.cursor;
        this.cursorCol = bufferImpl.cursorCol;
    }

    private void moveGapToCursor() {
        int i = this.cursor;
        int i2 = this.g0;
        if (i < i2) {
            int i3 = i2 - i;
            int[] iArr = this.buffer;
            System.arraycopy(iArr, i, iArr, this.g1 - i3, i3);
            this.g0 -= i3;
            this.g1 -= i3;
            return;
        }
        if (i > i2) {
            int i4 = i - i2;
            int[] iArr2 = this.buffer;
            System.arraycopy(iArr2, this.g1, iArr2, i2, i4);
            this.g0 += i4;
            this.g1 += i4;
        }
    }
}
