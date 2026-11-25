package org.antlr.runtime;

import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes12.dex */
public class ANTLRStringStream implements CharStream {
    protected int charPositionInLine;
    protected char[] data;
    protected int lastMarker;
    protected int line;
    protected int markDepth;
    protected List<CharStreamState> markers;
    protected int n;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    public String f421name;
    protected int p;

    public ANTLRStringStream() {
        this.p = 0;
        this.line = 1;
        this.charPositionInLine = 0;
        this.markDepth = 0;
    }

    public ANTLRStringStream(String str) {
        this();
        this.data = str.toCharArray();
        this.n = str.length();
    }

    public ANTLRStringStream(char[] cArr, int i) {
        this();
        this.data = cArr;
        this.n = i;
    }

    public void reset() {
        this.p = 0;
        this.line = 1;
        this.charPositionInLine = 0;
        this.markDepth = 0;
    }

    @Override // org.antlr.runtime.IntStream
    public void consume() {
        int i = this.p;
        if (i < this.n) {
            this.charPositionInLine++;
            if (this.data[i] == '\n') {
                this.line++;
                this.charPositionInLine = 0;
            }
            this.p = i + 1;
        }
    }

    @Override // org.antlr.runtime.IntStream
    public int LA(int i) {
        if (i == 0) {
            return 0;
        }
        if (i < 0) {
            i++;
            if ((this.p + i) - 1 < 0) {
                return -1;
            }
        }
        int i2 = this.p;
        if ((i2 + i) - 1 >= this.n) {
            return -1;
        }
        return this.data[(i2 + i) - 1];
    }

    @Override // org.antlr.runtime.CharStream
    public int LT(int i) {
        return LA(i);
    }

    @Override // org.antlr.runtime.IntStream
    public int index() {
        return this.p;
    }

    @Override // org.antlr.runtime.IntStream
    public int size() {
        return this.n;
    }

    @Override // org.antlr.runtime.IntStream
    public int mark() {
        CharStreamState charStreamState;
        if (this.markers == null) {
            ArrayList arrayList = new ArrayList();
            this.markers = arrayList;
            arrayList.add(null);
        }
        int i = this.markDepth + 1;
        this.markDepth = i;
        if (i >= this.markers.size()) {
            charStreamState = new CharStreamState();
            this.markers.add(charStreamState);
        } else {
            charStreamState = this.markers.get(this.markDepth);
        }
        charStreamState.p = this.p;
        charStreamState.line = this.line;
        charStreamState.charPositionInLine = this.charPositionInLine;
        int i2 = this.markDepth;
        this.lastMarker = i2;
        return i2;
    }

    @Override // org.antlr.runtime.IntStream
    public void rewind(int i) {
        CharStreamState charStreamState = this.markers.get(i);
        seek(charStreamState.p);
        this.line = charStreamState.line;
        this.charPositionInLine = charStreamState.charPositionInLine;
        release(i);
    }

    @Override // org.antlr.runtime.IntStream
    public void rewind() {
        rewind(this.lastMarker);
    }

    @Override // org.antlr.runtime.IntStream
    public void release(int i) {
        this.markDepth = i - 1;
    }

    @Override // org.antlr.runtime.IntStream
    public void seek(int i) {
        if (i <= this.p) {
            this.p = i;
        } else {
            while (this.p < i) {
                consume();
            }
        }
    }

    @Override // org.antlr.runtime.CharStream
    public String substring(int i, int i2) {
        return new String(this.data, i, (i2 - i) + 1);
    }

    @Override // org.antlr.runtime.CharStream
    public int getLine() {
        return this.line;
    }

    @Override // org.antlr.runtime.CharStream
    public int getCharPositionInLine() {
        return this.charPositionInLine;
    }

    @Override // org.antlr.runtime.CharStream
    public void setLine(int i) {
        this.line = i;
    }

    @Override // org.antlr.runtime.CharStream
    public void setCharPositionInLine(int i) {
        this.charPositionInLine = i;
    }

    @Override // org.antlr.runtime.IntStream
    public String getSourceName() {
        return this.f421name;
    }

    public String toString() {
        return new String(this.data);
    }
}
