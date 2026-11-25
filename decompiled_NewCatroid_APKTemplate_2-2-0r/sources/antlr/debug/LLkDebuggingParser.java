package antlr.debug;

import antlr.LLkParser;
import antlr.MismatchedTokenException;
import antlr.ParserSharedInputState;
import antlr.RecognitionException;
import antlr.TokenBuffer;
import antlr.TokenStream;
import antlr.TokenStreamException;
import antlr.Utils;
import antlr.collections.impl.BitSet;

/* JADX INFO: loaded from: classes.dex */
public class LLkDebuggingParser extends LLkParser implements DebuggingParser {
    static /* synthetic */ Class class$antlr$TokenBuffer;
    static /* synthetic */ Class class$antlr$TokenStream;
    static /* synthetic */ Class class$antlr$debug$LLkDebuggingParser;
    private boolean _notDebugMode;
    protected ParserEventSupport parserEventSupport;
    protected String[] ruleNames;
    protected String[] semPredNames;

    public LLkDebuggingParser(int i) {
        super(i);
        this.parserEventSupport = new ParserEventSupport(this);
        this._notDebugMode = false;
    }

    public LLkDebuggingParser(ParserSharedInputState parserSharedInputState, int i) {
        super(parserSharedInputState, i);
        this.parserEventSupport = new ParserEventSupport(this);
        this._notDebugMode = false;
    }

    public LLkDebuggingParser(TokenBuffer tokenBuffer, int i) {
        super(tokenBuffer, i);
        this.parserEventSupport = new ParserEventSupport(this);
        this._notDebugMode = false;
    }

    public LLkDebuggingParser(TokenStream tokenStream, int i) {
        super(tokenStream, i);
        this.parserEventSupport = new ParserEventSupport(this);
        this._notDebugMode = false;
    }

    @Override // antlr.Parser
    public void addMessageListener(MessageListener messageListener) {
        this.parserEventSupport.addMessageListener(messageListener);
    }

    @Override // antlr.Parser
    public void addParserListener(ParserListener parserListener) {
        this.parserEventSupport.addParserListener(parserListener);
    }

    @Override // antlr.Parser
    public void addParserMatchListener(ParserMatchListener parserMatchListener) {
        this.parserEventSupport.addParserMatchListener(parserMatchListener);
    }

    @Override // antlr.Parser
    public void addParserTokenListener(ParserTokenListener parserTokenListener) {
        this.parserEventSupport.addParserTokenListener(parserTokenListener);
    }

    @Override // antlr.Parser
    public void addSemanticPredicateListener(SemanticPredicateListener semanticPredicateListener) {
        this.parserEventSupport.addSemanticPredicateListener(semanticPredicateListener);
    }

    @Override // antlr.Parser
    public void addSyntacticPredicateListener(SyntacticPredicateListener syntacticPredicateListener) {
        this.parserEventSupport.addSyntacticPredicateListener(syntacticPredicateListener);
    }

    @Override // antlr.Parser
    public void addTraceListener(TraceListener traceListener) {
        this.parserEventSupport.addTraceListener(traceListener);
    }

    @Override // antlr.LLkParser, antlr.Parser
    public void consume() throws TokenStreamException {
        int iLA = LA(1);
        super.consume();
        this.parserEventSupport.fireConsume(iLA);
    }

    protected void fireEnterRule(int i, int i2) {
        if (isDebugMode()) {
            this.parserEventSupport.fireEnterRule(i, this.inputState.guessing, i2);
        }
    }

    protected void fireExitRule(int i, int i2) {
        if (isDebugMode()) {
            this.parserEventSupport.fireExitRule(i, this.inputState.guessing, i2);
        }
    }

    protected boolean fireSemanticPredicateEvaluated(int i, int i2, boolean z) {
        return isDebugMode() ? this.parserEventSupport.fireSemanticPredicateEvaluated(i, i2, z, this.inputState.guessing) : z;
    }

    protected void fireSyntacticPredicateFailed() {
        if (isDebugMode()) {
            this.parserEventSupport.fireSyntacticPredicateFailed(this.inputState.guessing);
        }
    }

    protected void fireSyntacticPredicateStarted() {
        if (isDebugMode()) {
            this.parserEventSupport.fireSyntacticPredicateStarted(this.inputState.guessing);
        }
    }

    protected void fireSyntacticPredicateSucceeded() {
        if (isDebugMode()) {
            this.parserEventSupport.fireSyntacticPredicateSucceeded(this.inputState.guessing);
        }
    }

    @Override // antlr.debug.DebuggingParser
    public String getRuleName(int i) {
        return this.ruleNames[i];
    }

    @Override // antlr.debug.DebuggingParser
    public String getSemPredName(int i) {
        return this.semPredNames[i];
    }

    public synchronized void goToSleep() {
        try {
            wait();
        } catch (InterruptedException unused) {
        }
    }

    @Override // antlr.Parser
    public boolean isDebugMode() {
        return !this._notDebugMode;
    }

    public boolean isGuessing() {
        return this.inputState.guessing > 0;
    }

    @Override // antlr.LLkParser, antlr.Parser
    public int LA(int i) throws TokenStreamException {
        int iLA = super.LA(i);
        this.parserEventSupport.fireLA(i, iLA);
        return iLA;
    }

    @Override // antlr.Parser
    public void match(int i) throws TokenStreamException, MismatchedTokenException {
        String text = LT(1).getText();
        int iLA = LA(1);
        try {
            super.match(i);
            this.parserEventSupport.fireMatch(i, text, this.inputState.guessing);
        } catch (MismatchedTokenException e) {
            if (this.inputState.guessing == 0) {
                this.parserEventSupport.fireMismatch(iLA, i, text, this.inputState.guessing);
            }
            throw e;
        }
    }

    @Override // antlr.Parser
    public void match(BitSet bitSet) throws TokenStreamException, MismatchedTokenException {
        String text = LT(1).getText();
        int iLA = LA(1);
        try {
            super.match(bitSet);
            this.parserEventSupport.fireMatch(iLA, bitSet, text, this.inputState.guessing);
        } catch (MismatchedTokenException e) {
            if (this.inputState.guessing == 0) {
                this.parserEventSupport.fireMismatch(iLA, bitSet, text, this.inputState.guessing);
            }
            throw e;
        }
    }

    @Override // antlr.Parser
    public void matchNot(int i) throws TokenStreamException, MismatchedTokenException {
        String text = LT(1).getText();
        int iLA = LA(1);
        try {
            super.matchNot(i);
            this.parserEventSupport.fireMatchNot(iLA, i, text, this.inputState.guessing);
        } catch (MismatchedTokenException e) {
            if (this.inputState.guessing == 0) {
                this.parserEventSupport.fireMismatchNot(iLA, i, text, this.inputState.guessing);
            }
            throw e;
        }
    }

    @Override // antlr.Parser
    public void removeMessageListener(MessageListener messageListener) {
        this.parserEventSupport.removeMessageListener(messageListener);
    }

    @Override // antlr.Parser
    public void removeParserListener(ParserListener parserListener) {
        this.parserEventSupport.removeParserListener(parserListener);
    }

    @Override // antlr.Parser
    public void removeParserMatchListener(ParserMatchListener parserMatchListener) {
        this.parserEventSupport.removeParserMatchListener(parserMatchListener);
    }

    @Override // antlr.Parser
    public void removeParserTokenListener(ParserTokenListener parserTokenListener) {
        this.parserEventSupport.removeParserTokenListener(parserTokenListener);
    }

    @Override // antlr.Parser
    public void removeSemanticPredicateListener(SemanticPredicateListener semanticPredicateListener) {
        this.parserEventSupport.removeSemanticPredicateListener(semanticPredicateListener);
    }

    @Override // antlr.Parser
    public void removeSyntacticPredicateListener(SyntacticPredicateListener syntacticPredicateListener) {
        this.parserEventSupport.removeSyntacticPredicateListener(syntacticPredicateListener);
    }

    @Override // antlr.Parser
    public void removeTraceListener(TraceListener traceListener) {
        this.parserEventSupport.removeTraceListener(traceListener);
    }

    @Override // antlr.Parser
    public void reportError(RecognitionException recognitionException) {
        this.parserEventSupport.fireReportError(recognitionException);
        super.reportError(recognitionException);
    }

    @Override // antlr.Parser
    public void reportError(String str) {
        this.parserEventSupport.fireReportError(str);
        super.reportError(str);
    }

    @Override // antlr.Parser
    public void reportWarning(String str) {
        this.parserEventSupport.fireReportWarning(str);
        super.reportWarning(str);
    }

    @Override // antlr.Parser
    public void setDebugMode(boolean z) {
        this._notDebugMode = !z;
    }

    public void setupDebugging(TokenBuffer tokenBuffer) {
        setupDebugging(null, tokenBuffer);
    }

    public void setupDebugging(TokenStream tokenStream) {
        setupDebugging(tokenStream, null);
    }

    protected void setupDebugging(TokenStream tokenStream, TokenBuffer tokenBuffer) {
        setDebugMode(true);
        try {
            try {
                Utils.loadClass("javax.swing.JButton");
            } catch (ClassNotFoundException unused) {
                System.err.println("Swing is required to use ParseView, but is not present in your CLASSPATH");
                System.exit(1);
            }
            Class clsLoadClass = Utils.loadClass("antlr.parseview.ParseView");
            Class<?>[] clsArr = new Class[3];
            Class<?> clsClass$ = class$antlr$debug$LLkDebuggingParser;
            if (clsClass$ == null) {
                clsClass$ = class$("antlr.debug.LLkDebuggingParser");
                class$antlr$debug$LLkDebuggingParser = clsClass$;
            }
            clsArr[0] = clsClass$;
            Class<?> clsClass$2 = class$antlr$TokenStream;
            if (clsClass$2 == null) {
                clsClass$2 = class$("antlr.TokenStream");
                class$antlr$TokenStream = clsClass$2;
            }
            clsArr[1] = clsClass$2;
            Class<?> clsClass$3 = class$antlr$TokenBuffer;
            if (clsClass$3 == null) {
                clsClass$3 = class$("antlr.TokenBuffer");
                class$antlr$TokenBuffer = clsClass$3;
            }
            clsArr[2] = clsClass$3;
            clsLoadClass.getConstructor(clsArr).newInstance(this, tokenStream, tokenBuffer);
        } catch (Exception e) {
            System.err.println(new StringBuffer("Error initializing ParseView: ").append(e).toString());
            System.err.println("Please report this to Scott Stanchfield, thetick@magelang.com");
            System.exit(1);
        }
    }

    static /* synthetic */ Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }

    public synchronized void wakeUp() {
        notify();
    }
}
