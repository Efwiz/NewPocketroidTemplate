package antlr.debug;

import antlr.CommonToken;
import antlr.LLkParser;
import antlr.MismatchedTokenException;
import antlr.ParseTree;
import antlr.ParseTreeRule;
import antlr.ParseTreeToken;
import antlr.ParserSharedInputState;
import antlr.TokenBuffer;
import antlr.TokenStream;
import antlr.TokenStreamException;
import antlr.collections.impl.BitSet;
import java.util.Stack;

/* JADX INFO: loaded from: classes.dex */
public class ParseTreeDebugParser extends LLkParser {
    protected Stack currentParseTreeRoot;
    protected ParseTreeRule mostRecentParseTreeRoot;
    protected int numberOfDerivationSteps;

    public ParseTreeDebugParser(int i) {
        super(i);
        this.currentParseTreeRoot = new Stack();
        this.mostRecentParseTreeRoot = null;
        this.numberOfDerivationSteps = 1;
    }

    public ParseTreeDebugParser(ParserSharedInputState parserSharedInputState, int i) {
        super(parserSharedInputState, i);
        this.currentParseTreeRoot = new Stack();
        this.mostRecentParseTreeRoot = null;
        this.numberOfDerivationSteps = 1;
    }

    public ParseTreeDebugParser(TokenBuffer tokenBuffer, int i) {
        super(tokenBuffer, i);
        this.currentParseTreeRoot = new Stack();
        this.mostRecentParseTreeRoot = null;
        this.numberOfDerivationSteps = 1;
    }

    public ParseTreeDebugParser(TokenStream tokenStream, int i) {
        super(tokenStream, i);
        this.currentParseTreeRoot = new Stack();
        this.mostRecentParseTreeRoot = null;
        this.numberOfDerivationSteps = 1;
    }

    public ParseTree getParseTree() {
        return this.mostRecentParseTreeRoot;
    }

    public int getNumberOfDerivationSteps() {
        return this.numberOfDerivationSteps;
    }

    @Override // antlr.Parser
    public void match(int i) throws TokenStreamException, MismatchedTokenException {
        addCurrentTokenToParseTree();
        super.match(i);
    }

    @Override // antlr.Parser
    public void match(BitSet bitSet) throws TokenStreamException, MismatchedTokenException {
        addCurrentTokenToParseTree();
        super.match(bitSet);
    }

    @Override // antlr.Parser
    public void matchNot(int i) throws TokenStreamException, MismatchedTokenException {
        addCurrentTokenToParseTree();
        super.matchNot(i);
    }

    protected void addCurrentTokenToParseTree() throws TokenStreamException {
        ParseTreeToken parseTreeToken;
        if (this.inputState.guessing > 0) {
            return;
        }
        ParseTreeRule parseTreeRule = (ParseTreeRule) this.currentParseTreeRoot.peek();
        if (LA(1) == 1) {
            parseTreeToken = new ParseTreeToken(new CommonToken("EOF"));
        } else {
            parseTreeToken = new ParseTreeToken(LT(1));
        }
        parseTreeRule.addChild(parseTreeToken);
    }

    @Override // antlr.LLkParser, antlr.Parser
    public void traceIn(String str) throws TokenStreamException {
        if (this.inputState.guessing > 0) {
            return;
        }
        ParseTreeRule parseTreeRule = new ParseTreeRule(str);
        if (this.currentParseTreeRoot.size() > 0) {
            ((ParseTreeRule) this.currentParseTreeRoot.peek()).addChild(parseTreeRule);
        }
        this.currentParseTreeRoot.push(parseTreeRule);
        this.numberOfDerivationSteps++;
    }

    @Override // antlr.LLkParser, antlr.Parser
    public void traceOut(String str) throws TokenStreamException {
        if (this.inputState.guessing > 0) {
            return;
        }
        this.mostRecentParseTreeRoot = (ParseTreeRule) this.currentParseTreeRoot.pop();
    }
}
