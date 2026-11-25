package org.antlr.runtime.debug;

import org.antlr.runtime.IntStream;
import org.antlr.runtime.TokenStream;

/* JADX INFO: loaded from: classes12.dex */
public class Tracer extends BlankDebugEventListener {
    public IntStream input;
    protected int level = 0;

    public Tracer(IntStream intStream) {
        this.input = intStream;
    }

    public void enterRule(String str) {
        for (int i = 1; i <= this.level; i++) {
            System.out.print(" ");
        }
        System.out.println("> " + str + " lookahead(1)=" + getInputSymbol(1));
        this.level++;
    }

    public void exitRule(String str) {
        this.level--;
        for (int i = 1; i <= this.level; i++) {
            System.out.print(" ");
        }
        System.out.println("< " + str + " lookahead(1)=" + getInputSymbol(1));
    }

    public Object getInputSymbol(int i) {
        IntStream intStream = this.input;
        if (intStream instanceof TokenStream) {
            return ((TokenStream) intStream).LT(i);
        }
        return Character.valueOf((char) intStream.LA(i));
    }
}
