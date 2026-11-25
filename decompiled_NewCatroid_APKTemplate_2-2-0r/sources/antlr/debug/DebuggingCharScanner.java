package antlr.debug;

import antlr.CharScanner;
import antlr.CharStreamException;
import antlr.InputBuffer;
import antlr.LexerSharedInputState;
import antlr.MismatchedCharException;
import antlr.RecognitionException;
import antlr.Token;
import antlr.collections.impl.BitSet;

/* JADX INFO: loaded from: classes.dex */
public abstract class DebuggingCharScanner extends CharScanner implements DebuggingParser {
    private boolean _notDebugMode;
    private ParserEventSupport parserEventSupport;
    protected String[] ruleNames;
    protected String[] semPredNames;

    public void setupDebugging() {
    }

    public DebuggingCharScanner(InputBuffer inputBuffer) {
        super(inputBuffer);
        this.parserEventSupport = new ParserEventSupport(this);
        this._notDebugMode = false;
    }

    public DebuggingCharScanner(LexerSharedInputState lexerSharedInputState) {
        super(lexerSharedInputState);
        this.parserEventSupport = new ParserEventSupport(this);
        this._notDebugMode = false;
    }

    public void addMessageListener(MessageListener messageListener) {
        this.parserEventSupport.addMessageListener(messageListener);
    }

    public void addNewLineListener(NewLineListener newLineListener) {
        this.parserEventSupport.addNewLineListener(newLineListener);
    }

    public void addParserListener(ParserListener parserListener) {
        this.parserEventSupport.addParserListener(parserListener);
    }

    public void addParserMatchListener(ParserMatchListener parserMatchListener) {
        this.parserEventSupport.addParserMatchListener(parserMatchListener);
    }

    public void addParserTokenListener(ParserTokenListener parserTokenListener) {
        this.parserEventSupport.addParserTokenListener(parserTokenListener);
    }

    public void addSemanticPredicateListener(SemanticPredicateListener semanticPredicateListener) {
        this.parserEventSupport.addSemanticPredicateListener(semanticPredicateListener);
    }

    public void addSyntacticPredicateListener(SyntacticPredicateListener syntacticPredicateListener) {
        this.parserEventSupport.addSyntacticPredicateListener(syntacticPredicateListener);
    }

    public void addTraceListener(TraceListener traceListener) {
        this.parserEventSupport.addTraceListener(traceListener);
    }

    @Override // antlr.CharScanner
    public void consume() throws CharStreamException {
        int iLA;
        try {
            iLA = LA(1);
        } catch (CharStreamException unused) {
            iLA = -99;
        }
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

    public boolean isDebugMode() {
        return !this._notDebugMode;
    }

    @Override // antlr.CharScanner
    public char LA(int i) throws CharStreamException {
        char cLA = super.LA(i);
        this.parserEventSupport.fireLA(i, cLA);
        return cLA;
    }

    @Override // antlr.CharScanner
    protected Token makeToken(int i) {
        return super.makeToken(i);
    }

    @Override // antlr.CharScanner
    public void match(char c) throws CharStreamException, MismatchedCharException {
        char cLA = LA(1);
        try {
            super.match(c);
            this.parserEventSupport.fireMatch(c, this.inputState.guessing);
        } catch (MismatchedCharException e) {
            if (this.inputState.guessing == 0) {
                this.parserEventSupport.fireMismatch(cLA, c, this.inputState.guessing);
            }
            throw e;
        }
    }

    @Override // antlr.CharScanner
    public void match(BitSet bitSet) throws CharStreamException, MismatchedCharException {
        String string = this.text.toString();
        char cLA = LA(1);
        try {
            super.match(bitSet);
            this.parserEventSupport.fireMatch(cLA, bitSet, string, this.inputState.guessing);
        } catch (MismatchedCharException e) {
            if (this.inputState.guessing == 0) {
                this.parserEventSupport.fireMismatch(cLA, bitSet, string, this.inputState.guessing);
            }
            throw e;
        }
    }

    @Override // antlr.CharScanner
    public void match(String str) throws CharStreamException, MismatchedCharException {
        StringBuffer stringBuffer = new StringBuffer("");
        int length = str.length();
        for (int i = 1; i <= length; i++) {
            try {
                stringBuffer.append(super.LA(i));
            } catch (Exception unused) {
            }
        }
        try {
            super.match(str);
            this.parserEventSupport.fireMatch(str, this.inputState.guessing);
        } catch (MismatchedCharException e) {
            if (this.inputState.guessing == 0) {
                this.parserEventSupport.fireMismatch(stringBuffer.toString(), str, this.inputState.guessing);
            }
            throw e;
        }
    }

    @Override // antlr.CharScanner
    public void matchNot(char c) throws CharStreamException, MismatchedCharException {
        char cLA = LA(1);
        try {
            super.matchNot(c);
            this.parserEventSupport.fireMatchNot(cLA, c, this.inputState.guessing);
        } catch (MismatchedCharException e) {
            if (this.inputState.guessing == 0) {
                this.parserEventSupport.fireMismatchNot(cLA, c, this.inputState.guessing);
            }
            throw e;
        }
    }

    @Override // antlr.CharScanner
    public void matchRange(char c, char c2) throws CharStreamException, MismatchedCharException {
        char cLA = LA(1);
        try {
            super.matchRange(c, c2);
            this.parserEventSupport.fireMatch(cLA, new StringBuffer("").append(c).append(c2).toString(), this.inputState.guessing);
        } catch (MismatchedCharException e) {
            if (this.inputState.guessing == 0) {
                this.parserEventSupport.fireMismatch(cLA, new StringBuffer("").append(c).append(c2).toString(), this.inputState.guessing);
            }
            throw e;
        }
    }

    @Override // antlr.CharScanner
    public void newline() {
        super.newline();
        this.parserEventSupport.fireNewLine(getLine());
    }

    public void removeMessageListener(MessageListener messageListener) {
        this.parserEventSupport.removeMessageListener(messageListener);
    }

    public void removeNewLineListener(NewLineListener newLineListener) {
        this.parserEventSupport.removeNewLineListener(newLineListener);
    }

    public void removeParserListener(ParserListener parserListener) {
        this.parserEventSupport.removeParserListener(parserListener);
    }

    public void removeParserMatchListener(ParserMatchListener parserMatchListener) {
        this.parserEventSupport.removeParserMatchListener(parserMatchListener);
    }

    public void removeParserTokenListener(ParserTokenListener parserTokenListener) {
        this.parserEventSupport.removeParserTokenListener(parserTokenListener);
    }

    public void removeSemanticPredicateListener(SemanticPredicateListener semanticPredicateListener) {
        this.parserEventSupport.removeSemanticPredicateListener(semanticPredicateListener);
    }

    public void removeSyntacticPredicateListener(SyntacticPredicateListener syntacticPredicateListener) {
        this.parserEventSupport.removeSyntacticPredicateListener(syntacticPredicateListener);
    }

    public void removeTraceListener(TraceListener traceListener) {
        this.parserEventSupport.removeTraceListener(traceListener);
    }

    public void reportError(MismatchedCharException mismatchedCharException) {
        this.parserEventSupport.fireReportError(mismatchedCharException);
        super.reportError((RecognitionException) mismatchedCharException);
    }

    @Override // antlr.CharScanner
    public void reportError(String str) {
        this.parserEventSupport.fireReportError(str);
        super.reportError(str);
    }

    @Override // antlr.CharScanner
    public void reportWarning(String str) {
        this.parserEventSupport.fireReportWarning(str);
        super.reportWarning(str);
    }

    public void setDebugMode(boolean z) {
        this._notDebugMode = !z;
    }

    public synchronized void wakeUp() {
        notify();
    }
}
