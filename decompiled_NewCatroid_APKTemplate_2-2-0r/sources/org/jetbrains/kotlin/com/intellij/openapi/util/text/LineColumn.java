package org.jetbrains.kotlin.com.intellij.openapi.util.text;

/* JADX INFO: loaded from: classes3.dex */
public final class LineColumn {
    public final int column;
    public final int line;

    private LineColumn(int i, int i2) {
        this.line = i;
        this.column = i2;
    }

    public static LineColumn of(int i, int i2) {
        return new LineColumn(i, i2);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        LineColumn lineColumn = (LineColumn) obj;
        return this.line == lineColumn.line && this.column == lineColumn.column;
    }

    public int hashCode() {
        return (this.line * 31) + this.column;
    }

    public String toString() {
        return "LineColumn{line=" + this.line + ", column=" + this.column + '}';
    }
}
