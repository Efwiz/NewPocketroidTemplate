package org.jetbrains.kotlin.js.util;

import io.netty.util.internal.StringUtil;
import java.util.Arrays;

/* JADX INFO: loaded from: classes5.dex */
public class TextOutputImpl implements TextOutput {
    private int column;
    private final boolean compact;
    private int identLevel;
    private char[][] indents;
    private boolean justNewlined;
    private int line;
    private final StringBuilder out;
    private int position;

    public TextOutputImpl() {
        this(false);
    }

    @Override // org.jetbrains.kotlin.js.util.TextOutput
    public boolean isCompact() {
        return this.compact;
    }

    public TextOutputImpl(boolean z) {
        this.identLevel = 0;
        this.indents = new char[][]{new char[0]};
        this.position = 0;
        this.line = 0;
        this.column = 0;
        this.compact = z;
        this.out = new StringBuilder();
    }

    public String toString() {
        return this.out.toString();
    }

    @Override // org.jetbrains.kotlin.js.util.TextOutput
    public int getPosition() {
        return this.position;
    }

    @Override // org.jetbrains.kotlin.js.util.TextOutput
    public int getLine() {
        return this.line;
    }

    @Override // org.jetbrains.kotlin.js.util.TextOutput
    public int getColumn() {
        return this.column;
    }

    @Override // org.jetbrains.kotlin.js.util.TextOutput
    public void indentIn() {
        int i = this.identLevel + 1;
        this.identLevel = i;
        if (i >= this.indents.length) {
            char[] cArr = new char[i * 2];
            Arrays.fill(cArr, StringUtil.SPACE);
            char[][] cArr2 = this.indents;
            char[][] cArr3 = new char[cArr2.length + 1][];
            System.arraycopy(cArr2, 0, cArr3, 0, cArr2.length);
            cArr3[this.identLevel] = cArr;
            this.indents = cArr3;
        }
    }

    @Override // org.jetbrains.kotlin.js.util.TextOutput
    public void indentOut() {
        this.identLevel--;
    }

    @Override // org.jetbrains.kotlin.js.util.TextOutput
    public void newline() {
        this.out.append('\n');
        this.position++;
        this.line++;
        this.column = 0;
        this.justNewlined = true;
    }

    @Override // org.jetbrains.kotlin.js.util.TextOutput
    public void print(double d) {
        maybeIndent();
        int length = this.out.length();
        this.out.append(d);
        movePosition(this.out.length() - length);
    }

    @Override // org.jetbrains.kotlin.js.util.TextOutput
    public void print(int i) {
        maybeIndent();
        int length = this.out.length();
        this.out.append(i);
        movePosition(this.out.length() - length);
    }

    @Override // org.jetbrains.kotlin.js.util.TextOutput
    public void print(char c) {
        maybeIndent();
        this.out.append(c);
        movePosition(1);
    }

    private void movePosition(int i) {
        this.position += i;
        this.column += i;
    }

    @Override // org.jetbrains.kotlin.js.util.TextOutput
    public void print(char[] cArr) {
        maybeIndent();
        printAndCount(cArr);
    }

    @Override // org.jetbrains.kotlin.js.util.TextOutput
    public void print(CharSequence charSequence) {
        maybeIndent();
        printAndCount(charSequence);
    }

    @Override // org.jetbrains.kotlin.js.util.TextOutput
    public void printOpt(char c) {
        if (this.compact) {
            return;
        }
        print(c);
    }

    @Override // org.jetbrains.kotlin.js.util.TextOutput
    public void maybeIndent() {
        if (!this.justNewlined || this.compact) {
            return;
        }
        printAndCount(this.indents[this.identLevel]);
        this.justNewlined = false;
    }

    private void printAndCount(CharSequence charSequence) {
        this.position += charSequence.length();
        this.column += charSequence.length();
        this.out.append(charSequence);
    }

    private void printAndCount(char[] cArr) {
        this.position += cArr.length;
        this.column += cArr.length;
        this.out.append(cArr);
    }
}
