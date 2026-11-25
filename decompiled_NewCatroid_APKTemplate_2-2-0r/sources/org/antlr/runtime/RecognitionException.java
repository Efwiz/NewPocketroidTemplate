package org.antlr.runtime;

import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.PositionTrackingStream;
import org.antlr.runtime.tree.Tree;
import org.antlr.runtime.tree.TreeAdaptor;
import org.antlr.runtime.tree.TreeNodeStream;

/* JADX INFO: loaded from: classes12.dex */
public class RecognitionException extends Exception {
    public boolean approximateLineInfo;
    public int c;
    public int charPositionInLine;
    public int index;
    public transient IntStream input;
    public int line;
    public Object node;
    public Token token;

    public RecognitionException() {
    }

    public RecognitionException(IntStream intStream) {
        this.input = intStream;
        this.index = intStream.index();
        if (intStream instanceof TokenStream) {
            Token tokenLT = ((TokenStream) intStream).LT(1);
            this.token = tokenLT;
            this.line = tokenLT.getLine();
            this.charPositionInLine = this.token.getCharPositionInLine();
        }
        if (intStream instanceof TreeNodeStream) {
            extractInformationFromTreeNodeStream(intStream);
            return;
        }
        if (intStream instanceof CharStream) {
            this.c = intStream.LA(1);
            CharStream charStream = (CharStream) intStream;
            this.line = charStream.getLine();
            this.charPositionInLine = charStream.getCharPositionInLine();
            return;
        }
        this.c = intStream.LA(1);
    }

    protected void extractInformationFromTreeNodeStream(IntStream intStream) {
        Object knownPositionElement;
        TreeNodeStream treeNodeStream = (TreeNodeStream) intStream;
        this.node = treeNodeStream.LT(1);
        if (treeNodeStream instanceof PositionTrackingStream) {
            PositionTrackingStream positionTrackingStream = (PositionTrackingStream) treeNodeStream;
            knownPositionElement = positionTrackingStream.getKnownPositionElement(false);
            if (knownPositionElement == null) {
                knownPositionElement = positionTrackingStream.getKnownPositionElement(true);
                this.approximateLineInfo = knownPositionElement != null;
            }
        } else {
            knownPositionElement = null;
        }
        TreeAdaptor treeAdaptor = treeNodeStream.getTreeAdaptor();
        if (knownPositionElement == null) {
            knownPositionElement = this.node;
        }
        Token token = treeAdaptor.getToken(knownPositionElement);
        if (token != null) {
            this.token = token;
            if (token.getLine() <= 0) {
                Object objLT = treeNodeStream.LT(-1);
                int i = -1;
                while (objLT != null) {
                    Token token2 = treeAdaptor.getToken(objLT);
                    if (token2 != null && token2.getLine() > 0) {
                        this.line = token2.getLine();
                        this.charPositionInLine = token2.getCharPositionInLine();
                        this.approximateLineInfo = true;
                        return;
                    } else {
                        i--;
                        try {
                            objLT = treeNodeStream.LT(i);
                        } catch (UnsupportedOperationException unused) {
                            objLT = null;
                        }
                    }
                }
                return;
            }
            this.line = token.getLine();
            this.charPositionInLine = token.getCharPositionInLine();
            return;
        }
        Object obj = this.node;
        if (obj instanceof Tree) {
            this.line = ((Tree) obj).getLine();
            this.charPositionInLine = ((Tree) this.node).getCharPositionInLine();
            Object obj2 = this.node;
            if (obj2 instanceof CommonTree) {
                this.token = ((CommonTree) obj2).token;
                return;
            }
            return;
        }
        this.token = new CommonToken(treeAdaptor.getType(obj), treeAdaptor.getText(this.node));
    }

    public int getUnexpectedType() {
        IntStream intStream = this.input;
        if (intStream instanceof TokenStream) {
            return this.token.getType();
        }
        if (intStream instanceof TreeNodeStream) {
            return ((TreeNodeStream) intStream).getTreeAdaptor().getType(this.node);
        }
        return this.c;
    }
}
