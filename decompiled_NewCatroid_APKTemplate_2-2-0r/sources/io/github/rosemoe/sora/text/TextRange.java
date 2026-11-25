package io.github.rosemoe.sora.text;

/* JADX INFO: loaded from: classes11.dex */
public class TextRange {
    private CharPosition end;
    private CharPosition start;

    public TextRange(CharPosition charPosition, CharPosition charPosition2) {
        this.start = charPosition;
        this.end = charPosition2;
    }

    public CharPosition getStart() {
        return this.start;
    }

    public void setStart(CharPosition charPosition) {
        this.start = charPosition;
    }

    public CharPosition getEnd() {
        return this.end;
    }

    public void setEnd(CharPosition charPosition) {
        this.end = charPosition;
    }

    public int getStartIndex() {
        return this.start.index;
    }

    public int getEndIndex() {
        return this.end.index;
    }

    public boolean isPositionInside(CharPosition charPosition) {
        return charPosition.index >= this.start.index && charPosition.index < this.end.index;
    }

    public String toString() {
        return "TextRange{start=" + this.start + ", end=" + this.end + '}';
    }
}
