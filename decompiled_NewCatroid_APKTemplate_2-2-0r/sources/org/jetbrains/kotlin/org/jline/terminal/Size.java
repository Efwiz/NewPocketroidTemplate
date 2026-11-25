package org.jetbrains.kotlin.org.jline.terminal;

/* JADX INFO: loaded from: classes4.dex */
public class Size {
    private int cols;
    private int rows;

    public Size() {
    }

    public Size(int i, int i2) {
        this();
        setColumns(i);
        setRows(i2);
    }

    public int getColumns() {
        return this.cols;
    }

    public void setColumns(int i) {
        this.cols = (short) i;
    }

    public int getRows() {
        return this.rows;
    }

    public void setRows(int i) {
        this.rows = (short) i;
    }

    public int cursorPos(int i, int i2) {
        return (i * (this.cols + 1)) + i2;
    }

    public void copy(Size size) {
        setColumns(size.getColumns());
        setRows(size.getRows());
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Size)) {
            return false;
        }
        Size size = (Size) obj;
        return this.rows == size.rows && this.cols == size.cols;
    }

    public int hashCode() {
        return (this.rows * 31) + this.cols;
    }

    public String toString() {
        return "Size[cols=" + this.cols + ", rows=" + this.rows + ']';
    }
}
