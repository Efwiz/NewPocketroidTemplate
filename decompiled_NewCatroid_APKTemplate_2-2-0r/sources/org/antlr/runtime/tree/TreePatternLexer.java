package org.antlr.runtime.tree;

/* JADX INFO: loaded from: classes12.dex */
public class TreePatternLexer {
    public static final int ARG = 4;
    public static final int BEGIN = 1;
    public static final int COLON = 6;
    public static final int DOT = 7;
    public static final int END = 2;
    public static final int EOF = -1;
    public static final int ID = 3;
    public static final int PERCENT = 5;
    protected int c;
    protected int n;
    protected String pattern;
    protected int p = -1;
    public StringBuffer sval = new StringBuffer();
    public boolean error = false;

    public TreePatternLexer(String str) {
        this.pattern = str;
        this.n = str.length();
        consume();
    }

    public int nextToken() {
        this.sval.setLength(0);
        while (true) {
            int i = this.c;
            if (i == -1) {
                return -1;
            }
            if (i == 32 || i == 10 || i == 13 || i == 9) {
                consume();
            } else if ((i >= 97 && i <= 122) || ((i >= 65 && i <= 90) || i == 95)) {
                this.sval.append((char) i);
                consume();
                while (true) {
                    int i2 = this.c;
                    if ((i2 < 97 || i2 > 122) && ((i2 < 65 || i2 > 90) && ((i2 < 48 || i2 > 57) && i2 != 95))) {
                        return 3;
                    }
                    this.sval.append((char) i2);
                    consume();
                }
            } else {
                if (i == 40) {
                    consume();
                    return 1;
                }
                if (i == 41) {
                    consume();
                    return 2;
                }
                if (i == 37) {
                    consume();
                    return 5;
                }
                if (i == 58) {
                    consume();
                    return 6;
                }
                if (i == 46) {
                    consume();
                    return 7;
                }
                if (i == 91) {
                    consume();
                    while (true) {
                        int i3 = this.c;
                        if (i3 != 93) {
                            if (i3 == 92) {
                                consume();
                                if (this.c != 93) {
                                    this.sval.append('\\');
                                }
                                this.sval.append((char) this.c);
                            } else {
                                this.sval.append((char) i3);
                            }
                            consume();
                        } else {
                            consume();
                            return 4;
                        }
                    }
                } else {
                    consume();
                    this.error = true;
                    return -1;
                }
            }
        }
    }

    protected void consume() {
        int i = this.p + 1;
        this.p = i;
        if (i >= this.n) {
            this.c = -1;
        } else {
            this.c = this.pattern.charAt(i);
        }
    }
}
