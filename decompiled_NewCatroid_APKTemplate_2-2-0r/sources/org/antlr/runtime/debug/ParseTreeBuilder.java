package org.antlr.runtime.debug;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.Token;
import org.antlr.runtime.tree.ParseTree;

/* JADX INFO: loaded from: classes12.dex */
public class ParseTreeBuilder extends BlankDebugEventListener {
    public static final String EPSILON_PAYLOAD = "<epsilon>";
    Stack<ParseTree> callStack = new Stack<>();
    List<Token> hiddenTokens = new ArrayList();
    int backtracking = 0;

    public ParseTreeBuilder(String str) {
        this.callStack.push(create("<grammar " + str + ">"));
    }

    public ParseTree getTree() {
        return this.callStack.elementAt(0);
    }

    public ParseTree create(Object obj) {
        return new ParseTree(obj);
    }

    public ParseTree epsilonNode() {
        return create(EPSILON_PAYLOAD);
    }

    @Override // org.antlr.runtime.debug.BlankDebugEventListener, org.antlr.runtime.debug.DebugEventListener
    public void enterDecision(int i, boolean z) {
        this.backtracking++;
    }

    @Override // org.antlr.runtime.debug.BlankDebugEventListener, org.antlr.runtime.debug.DebugEventListener
    public void exitDecision(int i) {
        this.backtracking--;
    }

    @Override // org.antlr.runtime.debug.BlankDebugEventListener, org.antlr.runtime.debug.DebugEventListener
    public void enterRule(String str, String str2) {
        if (this.backtracking > 0) {
            return;
        }
        ParseTree parseTreePeek = this.callStack.peek();
        ParseTree parseTreeCreate = create(str2);
        parseTreePeek.addChild(parseTreeCreate);
        this.callStack.push(parseTreeCreate);
    }

    @Override // org.antlr.runtime.debug.BlankDebugEventListener, org.antlr.runtime.debug.DebugEventListener
    public void exitRule(String str, String str2) {
        if (this.backtracking > 0) {
            return;
        }
        ParseTree parseTreePeek = this.callStack.peek();
        if (parseTreePeek.getChildCount() == 0) {
            parseTreePeek.addChild(epsilonNode());
        }
        this.callStack.pop();
    }

    @Override // org.antlr.runtime.debug.BlankDebugEventListener, org.antlr.runtime.debug.DebugEventListener
    public void consumeToken(Token token) {
        if (this.backtracking > 0) {
            return;
        }
        ParseTree parseTreePeek = this.callStack.peek();
        ParseTree parseTreeCreate = create(token);
        parseTreeCreate.hiddenTokens = this.hiddenTokens;
        this.hiddenTokens = new ArrayList();
        parseTreePeek.addChild(parseTreeCreate);
    }

    @Override // org.antlr.runtime.debug.BlankDebugEventListener, org.antlr.runtime.debug.DebugEventListener
    public void consumeHiddenToken(Token token) {
        if (this.backtracking > 0) {
            return;
        }
        this.hiddenTokens.add(token);
    }

    @Override // org.antlr.runtime.debug.BlankDebugEventListener, org.antlr.runtime.debug.DebugEventListener
    public void recognitionException(RecognitionException recognitionException) {
        if (this.backtracking > 0) {
            return;
        }
        this.callStack.peek().addChild(create(recognitionException));
    }
}
