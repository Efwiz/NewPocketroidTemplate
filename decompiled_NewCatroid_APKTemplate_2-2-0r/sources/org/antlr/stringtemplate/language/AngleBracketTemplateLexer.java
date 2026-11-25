package org.antlr.stringtemplate.language;

import antlr.ByteBuffer;
import antlr.CharBuffer;
import antlr.CharScanner;
import antlr.CharStreamException;
import antlr.CharStreamIOException;
import antlr.InputBuffer;
import antlr.LexerSharedInputState;
import antlr.NoViableAltForCharException;
import antlr.RecognitionException;
import antlr.SemanticException;
import antlr.Token;
import antlr.TokenStream;
import antlr.TokenStreamException;
import antlr.TokenStreamIOException;
import antlr.TokenStreamRecognitionException;
import antlr.collections.impl.BitSet;
import io.netty.util.internal.StringUtil;
import java.io.InputStream;
import java.io.Reader;
import java.util.Hashtable;
import org.antlr.stringtemplate.StringTemplate;
import org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider;
import org.eclipse.jdt.internal.compiler.util.Util;

/* JADX INFO: loaded from: classes12.dex */
public class AngleBracketTemplateLexer extends CharScanner implements AngleBracketTemplateLexerTokenTypes, TokenStream {
    protected String currentIndent;
    protected StringTemplate self;
    public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
    public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
    public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
    public static final BitSet _tokenSet_3 = new BitSet(mk_tokenSet_3());
    public static final BitSet _tokenSet_4 = new BitSet(mk_tokenSet_4());
    public static final BitSet _tokenSet_5 = new BitSet(mk_tokenSet_5());
    public static final BitSet _tokenSet_6 = new BitSet(mk_tokenSet_6());
    public static final BitSet _tokenSet_7 = new BitSet(mk_tokenSet_7());
    public static final BitSet _tokenSet_8 = new BitSet(mk_tokenSet_8());
    public static final BitSet _tokenSet_9 = new BitSet(mk_tokenSet_9());
    public static final BitSet _tokenSet_10 = new BitSet(mk_tokenSet_10());
    public static final BitSet _tokenSet_11 = new BitSet(mk_tokenSet_11());
    public static final BitSet _tokenSet_12 = new BitSet(mk_tokenSet_12());
    public static final BitSet _tokenSet_13 = new BitSet(mk_tokenSet_13());

    public AngleBracketTemplateLexer(StringTemplate stringTemplate, Reader reader) {
        this(reader);
        this.self = stringTemplate;
    }

    @Override // antlr.CharScanner
    public void reportError(RecognitionException recognitionException) {
        this.self.error("<...> chunk lexer error", recognitionException);
    }

    protected boolean upcomingELSE(int i) throws CharStreamException {
        return LA(i) == '<' && LA(i + 1) == 'e' && LA(i + 2) == 'l' && LA(i + 3) == 's' && LA(i + 4) == 'e' && LA(i + 5) == '>';
    }

    protected boolean upcomingENDIF(int i) throws CharStreamException {
        return LA(i) == '<' && LA(i + 1) == 'e' && LA(i + 2) == 'n' && LA(i + 3) == 'd' && LA(i + 4) == 'i' && LA(i + 5) == 'f' && LA(i + 6) == '>';
    }

    protected boolean upcomingAtEND(int i) throws CharStreamException {
        return LA(i) == '<' && LA(i + 1) == '@' && LA(i + 2) == 'e' && LA(i + 3) == 'n' && LA(i + 4) == 'd' && LA(i + 5) == '>';
    }

    protected boolean upcomingNewline(int i) throws CharStreamException {
        return (LA(i) == '\r' && LA(i + 1) == '\n') || LA(i) == '\n';
    }

    public AngleBracketTemplateLexer(InputStream inputStream) {
        this(new ByteBuffer(inputStream));
    }

    public AngleBracketTemplateLexer(Reader reader) {
        this(new CharBuffer(reader));
    }

    public AngleBracketTemplateLexer(InputBuffer inputBuffer) {
        this(new LexerSharedInputState(inputBuffer));
    }

    public AngleBracketTemplateLexer(LexerSharedInputState lexerSharedInputState) {
        super(lexerSharedInputState);
        this.currentIndent = null;
        this.caseSensitiveLiterals = true;
        setCaseSensitive(true);
        this.literals = new Hashtable();
    }

    @Override // antlr.TokenStream
    public Token nextToken() throws TokenStreamException {
        do {
            resetText();
            try {
                try {
                    char cLA = LA(1);
                    if (cLA == '\n' || cLA == '\r') {
                        mNEWLINE(true);
                        Token token = this._returnToken;
                    } else if (cLA == '<') {
                        mACTION(true);
                        Token token2 = this._returnToken;
                    } else if (_tokenSet_0.member(LA(1)) && LA(1) != '\r' && LA(1) != '\n') {
                        mLITERAL(true);
                        Token token3 = this._returnToken;
                    } else {
                        if (LA(1) != 65535) {
                            throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
                        }
                        uponEOF();
                        this._returnToken = makeToken(1);
                    }
                } catch (RecognitionException e) {
                    throw new TokenStreamRecognitionException(e);
                }
            } catch (CharStreamException e2) {
                if (e2 instanceof CharStreamIOException) {
                    throw new TokenStreamIOException(((CharStreamIOException) e2).f54io);
                }
                throw new TokenStreamException(e2.getMessage());
            }
        } while (this._returnToken == null);
        this._returnToken.setType(testLiteralsTable(this._returnToken.getType()));
        return this._returnToken;
    }

    /* JADX WARN: Code duplicated, block: B:27:0x00a0  */
    /* JADX WARN: Code duplicated, block: B:47:0x0116  */
    /* JADX WARN: Code duplicated, block: B:49:0x011d  */
    /* JADX WARN: Code duplicated, block: B:52:0x012f  */
    public final void mLITERAL(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        if (LA(1) == '\r' || LA(1) == '\n') {
            throw new SemanticException("LA(1)!='\\r'&&LA(1)!='\\n'");
        }
        int i = 0;
        while (true) {
            int length2 = this.text.length();
            int column = getColumn();
            if (LA(1) == '\\' && LA(2) == '<') {
                int length3 = this.text.length();
                match('\\');
                this.text.setLength(length3);
                match('<');
            } else if (LA(1) == '\\' && LA(2) == '>') {
                int length4 = this.text.length();
                match('\\');
                this.text.setLength(length4);
                match('>');
            } else if (LA(1) == '\\' && LA(2) == '\\') {
                int length5 = this.text.length();
                match('\\');
                this.text.setLength(length5);
                match('\\');
            } else if (LA(1) == '\\') {
                BitSet bitSet = _tokenSet_1;
                if (bitSet.member(LA(2))) {
                    match('\\');
                    match(bitSet);
                } else {
                    tokenMakeToken = null;
                    if (LA(1) != '\t' || LA(1) == ' ') {
                        mINDENT(true);
                        Token token = this._returnToken;
                        if (column != 1 && LA(1) == '<') {
                            this.currentIndent = token.getText();
                            this.text.setLength(length2);
                        } else {
                            this.currentIndent = null;
                        }
                    } else {
                        BitSet bitSet2 = _tokenSet_0;
                        if (!bitSet2.member(LA(1))) {
                            break;
                        } else {
                            match(bitSet2);
                        }
                    }
                }
            } else {
                tokenMakeToken = null;
                if (LA(1) != '\t') {
                    mINDENT(true);
                    Token token2 = this._returnToken;
                    if (column != 1) {
                        this.currentIndent = null;
                    } else {
                        this.currentIndent = null;
                    }
                } else {
                    mINDENT(true);
                    Token token3 = this._returnToken;
                    if (column != 1) {
                        this.currentIndent = null;
                    } else {
                        this.currentIndent = null;
                    }
                }
            }
            i++;
        }
        if (i < 1) {
            throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
        }
        int i2 = new String(this.text.getBuffer(), length, this.text.length() - length).length() == 0 ? -1 : 4;
        if (z && i2 != -1) {
            tokenMakeToken = makeToken(i2);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        }
        this._returnToken = tokenMakeToken;
    }

    protected final void mINDENT(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        int i = 0;
        while (true) {
            if (LA(1) == ' ') {
                match(StringUtil.SPACE);
            } else if (LA(1) != '\t') {
                break;
            } else {
                match('\t');
            }
            i++;
        }
        if (i < 1) {
            throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
        }
        if (z) {
            tokenMakeToken = makeToken(21);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    public final void mNEWLINE(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        int length = this.text.length();
        char cLA = LA(1);
        if (cLA != '\n') {
            if (cLA == '\r') {
                match('\r');
            } else {
                throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
            }
        }
        match('\n');
        newline();
        Token tokenMakeToken = null;
        this.currentIndent = null;
        if (z) {
            tokenMakeToken = makeToken(5);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        }
        this._returnToken = tokenMakeToken;
    }

    /* JADX WARN: Code duplicated, block: B:265:0x05ce  */
    /* JADX WARN: Code duplicated, block: B:295:0x0675  */
    /* JADX WARN: Code duplicated, block: B:304:0x06a1  */
    /* JADX WARN: Code duplicated, block: B:306:0x06a7  */
    /* JADX WARN: Code duplicated, block: B:311:0x06d8  */
    /* JADX WARN: Code duplicated, block: B:314:0x06e1  */
    /* JADX WARN: Code duplicated, block: B:316:0x06e5  */
    /* JADX WARN: Code duplicated, block: B:318:0x06eb A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:319:0x06ed  */
    /* JADX WARN: Code duplicated, block: B:320:0x06fc  */
    /* JADX WARN: Code restructure failed: missing block: B:231:0x054f, code lost:
    
        if (LA(2) <= 65534) goto L232;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void mACTION(boolean r17) throws antlr.RecognitionException, antlr.CharStreamException, antlr.TokenStreamException {
        /*
            Method dump skipped, instruction units count: 2171
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.antlr.stringtemplate.language.AngleBracketTemplateLexer.mACTION(boolean):void");
    }

    protected final void mLINE_BREAK(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        match("<\\\\>");
        char cLA = LA(1);
        if (cLA == '\t') {
            mINDENT(false);
        } else if (cLA != '\n' && cLA != '\r') {
            if (cLA != ' ') {
                throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
            }
            mINDENT(false);
        }
        char cLA2 = LA(1);
        if (cLA2 != '\n') {
            if (cLA2 == '\r') {
                match('\r');
            } else {
                throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
            }
        }
        match('\n');
        newline();
        if (LA(1) == '\t' || LA(1) == ' ') {
            mINDENT(false);
        }
        if (z) {
            tokenMakeToken = makeToken(23);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    protected final char mESC_CHAR(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        char c;
        Token tokenMakeToken;
        int length = this.text.length();
        if (LA(1) == '\\' && LA(2) == 'n') {
            int length2 = this.text.length();
            match("\\n");
            this.text.setLength(length2);
            c = '\n';
        } else if (LA(1) == '\\' && LA(2) == 'r') {
            int length3 = this.text.length();
            match("\\r");
            this.text.setLength(length3);
            c = '\r';
        } else if (LA(1) == '\\' && LA(2) == 't') {
            int length4 = this.text.length();
            match("\\t");
            this.text.setLength(length4);
            c = '\t';
        } else if (LA(1) == '\\' && LA(2) == ' ') {
            int length5 = this.text.length();
            match("\\ ");
            this.text.setLength(length5);
            c = ' ';
        } else if (LA(1) == '\\' && LA(2) == 'u') {
            int length6 = this.text.length();
            match("\\u");
            this.text.setLength(length6);
            int length7 = this.text.length();
            mHEX(true);
            this.text.setLength(length7);
            Token token = this._returnToken;
            int length8 = this.text.length();
            mHEX(true);
            this.text.setLength(length8);
            Token token2 = this._returnToken;
            int length9 = this.text.length();
            mHEX(true);
            this.text.setLength(length9);
            Token token3 = this._returnToken;
            int length10 = this.text.length();
            mHEX(true);
            this.text.setLength(length10);
            c = (char) Integer.parseInt(new StringBuffer().append(token.getText()).append(token2.getText()).append(token3.getText()).append(this._returnToken.getText()).toString(), 16);
        } else {
            throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
        }
        if (z) {
            tokenMakeToken = makeToken(16);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
        return c;
    }

    protected final void mCOMMENT(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        int column = getColumn();
        match("<!");
        while (true) {
            if (LA(1) == '!' && LA(2) == '>') {
                break;
            }
            if ((LA(1) == '\n' || LA(1) == '\r') && LA(2) >= 1 && LA(2) <= 65534 && LA(3) >= 1 && LA(3) <= 65534) {
                char cLA = LA(1);
                if (cLA != '\n') {
                    if (cLA == '\r') {
                        match('\r');
                    } else {
                        throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
                    }
                }
                match('\n');
                newline();
            } else if (LA(1) < 1 || LA(1) > 65534 || LA(2) < 1 || LA(2) > 65534 || LA(3) < 1 || LA(3) > 65534) {
                break;
            } else {
                matchNot((char) 65535);
            }
        }
        match("!>");
        if ((LA(1) == '\n' || LA(1) == '\r') && column == 1) {
            char cLA2 = LA(1);
            if (cLA2 != '\n') {
                if (cLA2 == '\r') {
                    match('\r');
                } else {
                    throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
                }
            }
            match('\n');
            newline();
        }
        if (z) {
            tokenMakeToken = makeToken(22);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    protected final void mIF_EXPR(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        int i = 0;
        while (true) {
            char cLA = LA(1);
            if (cLA == '\n' || cLA == '\r') {
                char cLA2 = LA(1);
                if (cLA2 != '\n') {
                    if (cLA2 == '\r') {
                        match('\r');
                    } else {
                        throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
                    }
                }
                match('\n');
                newline();
            } else if (cLA == '(') {
                mNESTED_PARENS(false);
            } else if (cLA == '\\') {
                mESC(false);
            } else if (cLA == '{') {
                mSUBTEMPLATE(false);
            } else {
                if (!_tokenSet_8.member(LA(1))) {
                    if (i < 1) {
                        throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
                    }
                    if (z) {
                        tokenMakeToken = makeToken(15);
                        tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
                    } else {
                        tokenMakeToken = null;
                    }
                    this._returnToken = tokenMakeToken;
                    return;
                }
                matchNot(')');
            }
            i++;
        }
    }

    /* JADX WARN: Code duplicated, block: B:47:0x00be  */
    /* JADX WARN: Code duplicated, block: B:49:0x00ca  */
    /* JADX WARN: Code duplicated, block: B:51:0x00d3 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:52:0x00d5  */
    /* JADX WARN: Code duplicated, block: B:53:0x00ef  */
    /* JADX WARN: Code duplicated, block: B:56:0x00f3  */
    /* JADX WARN: Code duplicated, block: B:78:0x00d1 A[SYNTHETIC] */
    protected final void mEXPR(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        int i = 0;
        while (true) {
            char cLA = LA(1);
            if (cLA == '\n' || cLA == '\r') {
                char cLA2 = LA(1);
                if (cLA2 != '\n') {
                    if (cLA2 == '\r') {
                        match('\r');
                    } else {
                        throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
                    }
                }
                match('\n');
                newline();
            } else if (cLA == '\\') {
                mESC(false);
            } else if (cLA == '{') {
                mSUBTEMPLATE(false);
            } else if ((LA(1) == '+' || LA(1) == '=') && (LA(2) == '\"' || LA(2) == '<')) {
                char cLA3 = LA(1);
                if (cLA3 == '+') {
                    match('+');
                } else if (cLA3 == '=') {
                    match('=');
                } else {
                    throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
                }
                mTEMPLATE(false);
            } else if ((LA(1) == '+' || LA(1) == '=') && LA(2) == '{') {
                char cLA4 = LA(1);
                if (cLA4 == '+') {
                    match('+');
                } else if (cLA4 == '=') {
                    match('=');
                } else {
                    throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
                }
                mSUBTEMPLATE(false);
            } else if (LA(1) == '+' || LA(1) == '=') {
                BitSet bitSet = _tokenSet_9;
                if (bitSet.member(LA(2))) {
                    char cLA5 = LA(1);
                    if (cLA5 == '+') {
                        match('+');
                    } else if (cLA5 == '=') {
                        match('=');
                    } else {
                        throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
                    }
                    match(bitSet);
                } else {
                    if (_tokenSet_10.member(LA(1))) {
                        if (i >= 1) {
                            throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
                        }
                        if (z) {
                            tokenMakeToken = makeToken(13);
                            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
                        } else {
                            tokenMakeToken = null;
                        }
                        this._returnToken = tokenMakeToken;
                        return;
                    }
                    matchNot('>');
                }
            } else {
                if (_tokenSet_10.member(LA(1))) {
                    if (i >= 1) {
                        throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
                    }
                    if (z) {
                        tokenMakeToken = makeToken(13);
                        tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
                    } else {
                        tokenMakeToken = null;
                    }
                    this._returnToken = tokenMakeToken;
                    return;
                }
                matchNot('>');
            }
            i++;
        }
    }

    protected final void mESC(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        match('\\');
        matchNot((char) 65535);
        if (z) {
            tokenMakeToken = makeToken(17);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    protected final void mSUBTEMPLATE(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        match('{');
        while (true) {
            char cLA = LA(1);
            if (cLA == '\\') {
                mESC(false);
            } else if (cLA == '{') {
                mSUBTEMPLATE(false);
            } else if (!_tokenSet_11.member(LA(1))) {
                break;
            } else {
                matchNot('}');
            }
        }
        match('}');
        if (z) {
            tokenMakeToken = makeToken(19);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    protected final void mTEMPLATE(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        char cLA = LA(1);
        if (cLA == '\"') {
            match('\"');
            while (true) {
                if (LA(1) == '\\') {
                    mESC(false);
                } else if (!_tokenSet_12.member(LA(1))) {
                    break;
                } else {
                    matchNot('\"');
                }
            }
            match('\"');
        } else if (cLA == '<') {
            match("<<");
            if ((LA(1) == '\n' || LA(1) == '\r') && LA(2) >= 1 && LA(2) <= 65534 && LA(3) >= 1 && LA(3) <= 65534 && LA(4) >= 1 && LA(4) <= 65534) {
                char cLA2 = LA(1);
                if (cLA2 != '\n') {
                    if (cLA2 == '\r') {
                        int length2 = this.text.length();
                        match('\r');
                        this.text.setLength(length2);
                    } else {
                        throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
                    }
                }
                int length3 = this.text.length();
                match('\n');
                this.text.setLength(length3);
                newline();
            } else if (LA(1) < 1 || LA(1) > 65534 || LA(2) < 1 || LA(2) > 65534 || LA(3) < 1 || LA(3) > 65534) {
                throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
            }
            while (true) {
                if (LA(1) == '>' && LA(2) == '>' && LA(3) >= 1 && LA(3) <= 65534) {
                    break;
                }
                if (LA(1) == '\r' && LA(2) == '\n' && LA(3) >= 1 && LA(3) <= 65534 && LA(4) >= 1 && LA(4) <= 65534 && LA(5) >= 1 && LA(5) <= 65534 && LA(3) == '>' && LA(4) == '>') {
                    int length4 = this.text.length();
                    match('\r');
                    this.text.setLength(length4);
                    int length5 = this.text.length();
                    match('\n');
                    this.text.setLength(length5);
                    newline();
                } else if (LA(1) == '\n' && LA(2) >= 1 && LA(2) <= 65534 && LA(3) >= 1 && LA(3) <= 65534 && LA(4) >= 1 && LA(4) <= 65534 && LA(2) == '>' && LA(3) == '>') {
                    int length6 = this.text.length();
                    match('\n');
                    this.text.setLength(length6);
                    newline();
                } else if ((LA(1) == '\n' || LA(1) == '\r') && LA(2) >= 1 && LA(2) <= 65534 && LA(3) >= 1 && LA(3) <= 65534 && LA(4) >= 1 && LA(4) <= 65534) {
                    char cLA3 = LA(1);
                    if (cLA3 != '\n') {
                        if (cLA3 == '\r') {
                            match('\r');
                        } else {
                            throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
                        }
                    }
                    match('\n');
                    newline();
                } else if (LA(1) < 1 || LA(1) > 65534 || LA(2) < 1 || LA(2) > 65534 || LA(3) < 1 || LA(3) > 65534 || LA(4) < 1 || LA(4) > 65534) {
                    break;
                } else {
                    matchNot((char) 65535);
                }
            }
            match(">>");
        } else {
            throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
        }
        if (z) {
            tokenMakeToken = makeToken(14);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    protected final void mNESTED_PARENS(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        match('(');
        int i = 0;
        while (true) {
            char cLA = LA(1);
            if (cLA == '(') {
                mNESTED_PARENS(false);
            } else if (cLA == '\\') {
                mESC(false);
            } else if (!_tokenSet_13.member(LA(1))) {
                break;
            } else {
                matchNot(')');
            }
            i++;
        }
        if (i < 1) {
            throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
        }
        match(')');
        if (z) {
            tokenMakeToken = makeToken(20);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    protected final void mHEX(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        char cLA = LA(1);
        switch (cLA) {
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
                matchRange(ExternalAnnotationProvider.NULLABLE, '9');
                break;
            default:
                switch (cLA) {
                    case 'A':
                    case 'B':
                    case 'C':
                    case 'D':
                    case 'E':
                    case 'F':
                        matchRange('A', Util.C_FLOAT);
                        break;
                    default:
                        switch (cLA) {
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                matchRange('a', 'f');
                                break;
                            default:
                                throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
                        }
                        break;
                }
                break;
        }
        if (z) {
            tokenMakeToken = makeToken(18);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    private static final long[] mk_tokenSet_0() {
        long[] jArr = new long[2048];
        jArr[0] = -1152921504606856194L;
        for (int i = 1; i <= 1022; i++) {
            jArr[i] = -1;
        }
        jArr[1023] = Long.MAX_VALUE;
        return jArr;
    }

    private static final long[] mk_tokenSet_1() {
        long[] jArr = new long[2048];
        jArr[0] = -5764607523034234882L;
        for (int i = 1; i <= 1022; i++) {
            jArr[i] = -1;
        }
        jArr[1023] = Long.MAX_VALUE;
        return jArr;
    }

    private static final long[] mk_tokenSet_2() {
        long[] jArr = new long[1025];
        jArr[0] = 4294977024L;
        return jArr;
    }

    private static final long[] mk_tokenSet_3() {
        long[] jArr = new long[1025];
        jArr[0] = 4294967296L;
        jArr[1] = 14707067533131776L;
        return jArr;
    }

    private static final long[] mk_tokenSet_4() {
        long[] jArr = new long[1025];
        jArr[0] = 4899634919602388992L;
        jArr[1] = 541434314878L;
        return jArr;
    }

    private static final long[] mk_tokenSet_5() {
        long[] jArr = new long[2048];
        jArr[0] = -4611686018427387906L;
        for (int i = 1; i <= 1022; i++) {
            jArr[i] = -1;
        }
        jArr[1023] = Long.MAX_VALUE;
        return jArr;
    }

    private static final long[] mk_tokenSet_6() {
        long[] jArr = new long[2048];
        jArr[0] = -2199023255554L;
        for (int i = 1; i <= 1022; i++) {
            jArr[i] = -1;
        }
        jArr[1023] = Long.MAX_VALUE;
        return jArr;
    }

    private static final long[] mk_tokenSet_7() {
        long[] jArr = new long[2048];
        jArr[0] = -4611687117939015682L;
        for (int i = 1; i <= 1022; i++) {
            jArr[i] = -1;
        }
        jArr[1023] = Long.MAX_VALUE;
        return jArr;
    }

    private static final long[] mk_tokenSet_8() {
        long[] jArr = new long[2048];
        jArr[0] = -3298534892546L;
        jArr[1] = -576460752571858945L;
        for (int i = 2; i <= 1022; i++) {
            jArr[i] = -1;
        }
        jArr[1023] = Long.MAX_VALUE;
        return jArr;
    }

    private static final long[] mk_tokenSet_9() {
        long[] jArr = new long[2048];
        jArr[0] = -1152921521786716162L;
        jArr[1] = -576460752303423489L;
        for (int i = 2; i <= 1022; i++) {
            jArr[i] = -1;
        }
        jArr[1023] = Long.MAX_VALUE;
        return jArr;
    }

    private static final long[] mk_tokenSet_10() {
        long[] jArr = new long[2048];
        jArr[0] = -6917537823734113282L;
        jArr[1] = -576460752571858945L;
        for (int i = 2; i <= 1022; i++) {
            jArr[i] = -1;
        }
        jArr[1023] = Long.MAX_VALUE;
        return jArr;
    }

    private static final long[] mk_tokenSet_11() {
        long[] jArr = new long[2048];
        jArr[0] = -2;
        jArr[1] = -2882303761785552897L;
        for (int i = 2; i <= 1022; i++) {
            jArr[i] = -1;
        }
        jArr[1023] = Long.MAX_VALUE;
        return jArr;
    }

    private static final long[] mk_tokenSet_12() {
        long[] jArr = new long[2048];
        jArr[0] = -17179869186L;
        jArr[1] = -268435457;
        for (int i = 2; i <= 1022; i++) {
            jArr[i] = -1;
        }
        jArr[1023] = Long.MAX_VALUE;
        return jArr;
    }

    private static final long[] mk_tokenSet_13() {
        long[] jArr = new long[2048];
        jArr[0] = -3298534883330L;
        jArr[1] = -268435457;
        for (int i = 2; i <= 1022; i++) {
            jArr[i] = -1;
        }
        jArr[1023] = Long.MAX_VALUE;
        return jArr;
    }
}
