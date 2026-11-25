package org.jetbrains.kotlin.org.jline.terminal;

/* JADX INFO: loaded from: classes4.dex */
public class Cursor {
    private final int x;
    private final int y;

    public Cursor(int i, int i2) {
        this.x = i;
        this.y = i2;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Cursor)) {
            return false;
        }
        Cursor cursor = (Cursor) obj;
        return this.x == cursor.x && this.y == cursor.y;
    }

    public int hashCode() {
        return (this.x * 31) + this.y;
    }

    public String toString() {
        return "Cursor[x=" + this.x + ", y=" + this.y + ']';
    }
}
