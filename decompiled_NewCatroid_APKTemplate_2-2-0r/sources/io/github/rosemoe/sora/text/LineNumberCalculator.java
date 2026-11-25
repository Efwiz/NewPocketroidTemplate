package io.github.rosemoe.sora.text;

/* JADX INFO: loaded from: classes11.dex */
public class LineNumberCalculator {
    private final int length;
    private final CharSequence target;
    private int column = 0;
    private int line = 0;
    private int offset = 0;

    public LineNumberCalculator(CharSequence charSequence) {
        this.target = charSequence;
        this.length = charSequence.length();
    }

    public void update(int i) {
        for (int i2 = 0; i2 < i; i2++) {
            int i3 = this.offset;
            if (i3 + i2 == this.length) {
                break;
            }
            if (this.target.charAt(i3 + i2) == '\n') {
                this.line++;
                this.column = 0;
            } else {
                this.column++;
            }
        }
        this.offset += i;
    }

    public int findLineStart() {
        return this.offset - this.column;
    }

    public int findLineEnd() {
        int i = 0;
        while (true) {
            int i2 = this.offset;
            if (i + i2 >= this.length || this.target.charAt(i2 + i) == '\n') {
                break;
            }
            i++;
        }
        return this.offset + i;
    }

    public int getLine() {
        return this.line;
    }

    public int getColumn() {
        return this.column;
    }
}
