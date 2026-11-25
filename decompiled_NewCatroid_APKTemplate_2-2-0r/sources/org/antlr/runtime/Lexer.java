package org.antlr.runtime;

import name.antonsmirnov.firmata.FormatHelper;

/* JADX INFO: loaded from: classes12.dex */
public abstract class Lexer extends BaseRecognizer implements TokenSource {
    protected CharStream input;

    public abstract void mTokens() throws RecognitionException;

    public Lexer() {
    }

    public Lexer(CharStream charStream) {
        this.input = charStream;
    }

    public Lexer(CharStream charStream, RecognizerSharedState recognizerSharedState) {
        super(recognizerSharedState);
        this.input = charStream;
    }

    @Override // org.antlr.runtime.BaseRecognizer
    public void reset() {
        super.reset();
        CharStream charStream = this.input;
        if (charStream != null) {
            charStream.seek(0);
        }
        if (this.state == null) {
            return;
        }
        this.state.token = null;
        this.state.type = 0;
        this.state.channel = 0;
        this.state.tokenStartCharIndex = -1;
        this.state.tokenStartCharPositionInLine = -1;
        this.state.tokenStartLine = -1;
        this.state.text = null;
    }

    @Override // org.antlr.runtime.TokenSource
    public Token nextToken() {
        while (true) {
            this.state.token = null;
            this.state.channel = 0;
            this.state.tokenStartCharIndex = this.input.index();
            this.state.tokenStartCharPositionInLine = this.input.getCharPositionInLine();
            this.state.tokenStartLine = this.input.getLine();
            this.state.text = null;
            if (this.input.LA(1) == -1) {
                return getEOFToken();
            }
            try {
                mTokens();
                if (this.state.token == null) {
                    emit();
                } else if (this.state.token == Token.SKIP_TOKEN) {
                }
                return this.state.token;
            } catch (MismatchedRangeException e) {
                reportError(e);
            } catch (MismatchedTokenException e2) {
                reportError(e2);
            } catch (RecognitionException e3) {
                reportError(e3);
                recover(e3);
            }
        }
    }

    public Token getEOFToken() {
        CharStream charStream = this.input;
        CommonToken commonToken = new CommonToken(charStream, -1, 0, charStream.index(), this.input.index());
        commonToken.setLine(getLine());
        commonToken.setCharPositionInLine(getCharPositionInLine());
        return commonToken;
    }

    public void skip() {
        this.state.token = Token.SKIP_TOKEN;
    }

    public void setCharStream(CharStream charStream) {
        this.input = null;
        reset();
        this.input = charStream;
    }

    public CharStream getCharStream() {
        return this.input;
    }

    @Override // org.antlr.runtime.BaseRecognizer, org.antlr.runtime.TokenSource
    public String getSourceName() {
        return this.input.getSourceName();
    }

    public void emit(Token token) {
        this.state.token = token;
    }

    public Token emit() {
        CommonToken commonToken = new CommonToken(this.input, this.state.type, this.state.channel, this.state.tokenStartCharIndex, getCharIndex() - 1);
        commonToken.setLine(this.state.tokenStartLine);
        commonToken.setText(this.state.text);
        commonToken.setCharPositionInLine(this.state.tokenStartCharPositionInLine);
        emit(commonToken);
        return commonToken;
    }

    public void match(String str) throws MismatchedTokenException {
        int i = 0;
        while (i < str.length()) {
            if (this.input.LA(1) != str.charAt(i)) {
                if (this.state.backtracking > 0) {
                    this.state.failed = true;
                    return;
                } else {
                    MismatchedTokenException mismatchedTokenException = new MismatchedTokenException(str.charAt(i), this.input);
                    recover(mismatchedTokenException);
                    throw mismatchedTokenException;
                }
            }
            i++;
            this.input.consume();
            this.state.failed = false;
        }
    }

    public void matchAny() {
        this.input.consume();
    }

    public void match(int i) throws MismatchedTokenException {
        if (this.input.LA(1) != i) {
            if (this.state.backtracking > 0) {
                this.state.failed = true;
                return;
            } else {
                MismatchedTokenException mismatchedTokenException = new MismatchedTokenException(i, this.input);
                recover(mismatchedTokenException);
                throw mismatchedTokenException;
            }
        }
        this.input.consume();
        this.state.failed = false;
    }

    public void matchRange(int i, int i2) throws MismatchedRangeException {
        if (this.input.LA(1) < i || this.input.LA(1) > i2) {
            if (this.state.backtracking > 0) {
                this.state.failed = true;
                return;
            } else {
                MismatchedRangeException mismatchedRangeException = new MismatchedRangeException(i, i2, this.input);
                recover(mismatchedRangeException);
                throw mismatchedRangeException;
            }
        }
        this.input.consume();
        this.state.failed = false;
    }

    public int getLine() {
        return this.input.getLine();
    }

    public int getCharPositionInLine() {
        return this.input.getCharPositionInLine();
    }

    public int getCharIndex() {
        return this.input.index();
    }

    public String getText() {
        if (this.state.text != null) {
            return this.state.text;
        }
        return this.input.substring(this.state.tokenStartCharIndex, getCharIndex() - 1);
    }

    public void setText(String str) {
        this.state.text = str;
    }

    @Override // org.antlr.runtime.BaseRecognizer
    public void reportError(RecognitionException recognitionException) {
        displayRecognitionError(getTokenNames(), recognitionException);
    }

    @Override // org.antlr.runtime.BaseRecognizer
    public String getErrorMessage(RecognitionException recognitionException, String[] strArr) {
        if (recognitionException instanceof MismatchedTokenException) {
            return "mismatched character " + getCharErrorDisplay(recognitionException.c) + " expecting " + getCharErrorDisplay(((MismatchedTokenException) recognitionException).expecting);
        }
        if (recognitionException instanceof NoViableAltException) {
            return "no viable alternative at character " + getCharErrorDisplay(recognitionException.c);
        }
        if (recognitionException instanceof EarlyExitException) {
            return "required (...)+ loop did not match anything at character " + getCharErrorDisplay(recognitionException.c);
        }
        if (recognitionException instanceof MismatchedNotSetException) {
            return "mismatched character " + getCharErrorDisplay(recognitionException.c) + " expecting set " + ((MismatchedNotSetException) recognitionException).expecting;
        }
        if (recognitionException instanceof MismatchedSetException) {
            return "mismatched character " + getCharErrorDisplay(recognitionException.c) + " expecting set " + ((MismatchedSetException) recognitionException).expecting;
        }
        if (recognitionException instanceof MismatchedRangeException) {
            MismatchedRangeException mismatchedRangeException = (MismatchedRangeException) recognitionException;
            return "mismatched character " + getCharErrorDisplay(recognitionException.c) + " expecting set " + getCharErrorDisplay(mismatchedRangeException.a) + ".." + getCharErrorDisplay(mismatchedRangeException.b);
        }
        return super.getErrorMessage(recognitionException, strArr);
    }

    public String getCharErrorDisplay(int i) {
        String strValueOf = String.valueOf((char) i);
        if (i == -1) {
            strValueOf = "<EOF>";
        } else if (i == 13) {
            strValueOf = "\\r";
        } else if (i == 9) {
            strValueOf = "\\t";
        } else if (i == 10) {
            strValueOf = "\\n";
        }
        return FormatHelper.QUOTE + strValueOf + FormatHelper.QUOTE;
    }

    public void recover(RecognitionException recognitionException) {
        this.input.consume();
    }

    public void traceIn(String str, int i) {
        super.traceIn(str, i, ((char) this.input.LT(1)) + " line=" + getLine() + ":" + getCharPositionInLine());
    }

    public void traceOut(String str, int i) {
        super.traceOut(str, i, ((char) this.input.LT(1)) + " line=" + getLine() + ":" + getCharPositionInLine());
    }
}
