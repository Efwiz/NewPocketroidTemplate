package com.android.tools.r8.position;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class TextPosition implements Position {
    public static final int UNKNOWN_COLUMN = -1;
    static final /* synthetic */ boolean d = true;
    private final long a;
    private final int b;
    private final int c;

    public TextPosition(long j, int i, int i2) {
        if (!d && (j < 0 || i < 1 || (i2 < 1 && i2 != -1))) {
            throw new AssertionError();
        }
        this.a = j;
        this.b = i;
        this.c = i2;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || !obj.getClass().equals(TextPosition.class)) {
            return false;
        }
        TextPosition textPosition = (TextPosition) obj;
        return this.a == textPosition.a && this.b == textPosition.b && this.c == textPosition.c;
    }

    public int getColumn() {
        return this.c;
    }

    @Override // com.android.tools.r8.position.Position
    public String getDescription() {
        int i = this.b;
        int i2 = this.c;
        return "line " + i + (i2 != -1 ? ", column " + i2 : "");
    }

    public int getLine() {
        return this.b;
    }

    public long getOffset() {
        return this.a;
    }

    public int hashCode() {
        return (Long.hashCode(this.a) ^ this.b) ^ (this.c << 16);
    }

    public String toString() {
        return "offset: " + this.a + ", line: " + this.b + ", column: " + this.c;
    }
}
