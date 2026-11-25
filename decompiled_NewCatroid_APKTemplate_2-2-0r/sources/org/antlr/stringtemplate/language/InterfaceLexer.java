package org.antlr.stringtemplate.language;

import antlr.ANTLRHashString;
import antlr.ByteBuffer;
import antlr.CharBuffer;
import antlr.CharScanner;
import antlr.CharStreamException;
import antlr.CharStreamIOException;
import antlr.InputBuffer;
import antlr.LexerSharedInputState;
import antlr.NoViableAltForCharException;
import antlr.RecognitionException;
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
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider;
import org.eclipse.jgit.transport.RefSpec;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;

/* JADX INFO: loaded from: classes12.dex */
public class InterfaceLexer extends CharScanner implements InterfaceParserTokenTypes, TokenStream {
    public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());

    public InterfaceLexer(InputStream inputStream) {
        this(new ByteBuffer(inputStream));
    }

    public InterfaceLexer(Reader reader) {
        this(new CharBuffer(reader));
    }

    public InterfaceLexer(InputBuffer inputBuffer) {
        this(new LexerSharedInputState(inputBuffer));
    }

    public InterfaceLexer(LexerSharedInputState lexerSharedInputState) {
        super(lexerSharedInputState);
        this.caseSensitiveLiterals = true;
        setCaseSensitive(true);
        this.literals = new Hashtable();
        this.literals.put(new ANTLRHashString(PsiKeyword.INTERFACE, this), new Integer(4));
        this.literals.put(new ANTLRHashString(SchemaSymbols.ATTVAL_OPTIONAL, this), new Integer(7));
    }

    /* JADX WARN: Code duplicated, block: B:46:0x00a4 A[Catch: CharStreamException -> 0x00cd, RecognitionException -> 0x00cf, FALL_THROUGH, TryCatch #1 {RecognitionException -> 0x00cf, blocks: (B:3:0x0004, B:25:0x0034, B:26:0x0037, B:27:0x003a, B:29:0x0043, B:31:0x0049, B:49:0x00b5, B:52:0x00bb, B:32:0x004f, B:34:0x0055, B:36:0x005d, B:37:0x0063, B:39:0x006c, B:40:0x0076, B:41:0x008b, B:42:0x008c, B:43:0x0092, B:44:0x0098, B:45:0x009e, B:46:0x00a4, B:47:0x00aa, B:48:0x00b0), top: B:65:0x0004, outer: #0 }] */
    @Override // antlr.TokenStream
    public Token nextToken() throws TokenStreamException {
        do {
            resetText();
            try {
                try {
                    char cLA = LA(1);
                    if (cLA != '\t' && cLA != '\n' && cLA != '\f' && cLA != '\r' && cLA != ' ') {
                        if (cLA != ',') {
                            if (cLA != '_') {
                                if (cLA != '(') {
                                    if (cLA != ')') {
                                        if (cLA != ':') {
                                            if (cLA == ';') {
                                                mSEMI(true);
                                                Token token = this._returnToken;
                                            } else {
                                                switch (cLA) {
                                                    default:
                                                        switch (cLA) {
                                                            case 'a':
                                                            case 'b':
                                                            case 'c':
                                                            case 'd':
                                                            case 'e':
                                                            case 'f':
                                                            case 'g':
                                                            case 'h':
                                                            case 'i':
                                                            case 'j':
                                                            case 'k':
                                                            case 'l':
                                                            case 'm':
                                                            case 'n':
                                                            case 'o':
                                                            case 'p':
                                                            case 'q':
                                                            case 'r':
                                                            case 's':
                                                            case 't':
                                                            case 'u':
                                                            case 'v':
                                                            case 'w':
                                                            case 'x':
                                                            case 'y':
                                                            case 'z':
                                                                break;
                                                            default:
                                                                if (LA(1) == '/' && LA(2) == '/') {
                                                                    mSL_COMMENT(true);
                                                                    Token token2 = this._returnToken;
                                                                } else if (LA(1) == '/' && LA(2) == '*') {
                                                                    mML_COMMENT(true);
                                                                    Token token3 = this._returnToken;
                                                                } else {
                                                                    if (LA(1) != 65535) {
                                                                        throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
                                                                    }
                                                                    uponEOF();
                                                                    this._returnToken = makeToken(1);
                                                                }
                                                                break;
                                                        }
                                                    case 'A':
                                                    case 'B':
                                                    case 'C':
                                                    case 'D':
                                                    case 'E':
                                                    case 'F':
                                                    case 'G':
                                                    case 'H':
                                                    case 'I':
                                                    case 'J':
                                                    case 'K':
                                                    case 'L':
                                                    case 'M':
                                                    case 'N':
                                                    case 'O':
                                                    case 'P':
                                                    case 'Q':
                                                    case 'R':
                                                    case 'S':
                                                    case 'T':
                                                    case 'U':
                                                    case 'V':
                                                    case 'W':
                                                    case 'X':
                                                    case 'Y':
                                                    case 'Z':
                                                        mID(true);
                                                        Token token4 = this._returnToken;
                                                        break;
                                                }
                                            }
                                        } else {
                                            mCOLON(true);
                                            Token token5 = this._returnToken;
                                        }
                                    } else {
                                        mRPAREN(true);
                                        Token token6 = this._returnToken;
                                    }
                                } else {
                                    mLPAREN(true);
                                    Token token7 = this._returnToken;
                                }
                            } else {
                                mID(true);
                                Token token8 = this._returnToken;
                            }
                        } else {
                            mCOMMA(true);
                            Token token9 = this._returnToken;
                        }
                    } else {
                        mWS(true);
                        Token token10 = this._returnToken;
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

    public final void mID(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        switch (LA(1)) {
            case 'A':
            case 'B':
            case 'C':
            case 'D':
            case 'E':
            case 'F':
            case 'G':
            case 'H':
            case 'I':
            case 'J':
            case 'K':
            case 'L':
            case 'M':
            case 'N':
            case 'O':
            case 'P':
            case 'Q':
            case 'R':
            case 'S':
            case 'T':
            case 'U':
            case 'V':
            case 'W':
            case 'X':
            case 'Y':
            case 'Z':
                matchRange('A', 'Z');
                break;
            case '[':
            case '\\':
            case ']':
            case '^':
            case '`':
            default:
                throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
            case '_':
                match('_');
                break;
            case 'a':
            case 'b':
            case 'c':
            case 'd':
            case 'e':
            case 'f':
            case 'g':
            case 'h':
            case 'i':
            case 'j':
            case 'k':
            case 'l':
            case 'm':
            case 'n':
            case 'o':
            case 'p':
            case 'q':
            case 'r':
            case 's':
            case 't':
            case 'u':
            case 'v':
            case 'w':
            case 'x':
            case 'y':
            case 'z':
                matchRange('a', 'z');
                break;
        }
        while (true) {
            char cLA = LA(1);
            if (cLA == '-') {
                match('-');
            } else if (cLA != '_') {
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
                            case 'G':
                            case 'H':
                            case 'I':
                            case 'J':
                            case 'K':
                            case 'L':
                            case 'M':
                            case 'N':
                            case 'O':
                            case 'P':
                            case 'Q':
                            case 'R':
                            case 'S':
                            case 'T':
                            case 'U':
                            case 'V':
                            case 'W':
                            case 'X':
                            case 'Y':
                            case 'Z':
                                matchRange('A', 'Z');
                                break;
                            default:
                                switch (cLA) {
                                    case 'a':
                                    case 'b':
                                    case 'c':
                                    case 'd':
                                    case 'e':
                                    case 'f':
                                    case 'g':
                                    case 'h':
                                    case 'i':
                                    case 'j':
                                    case 'k':
                                    case 'l':
                                    case 'm':
                                    case 'n':
                                    case 'o':
                                    case 'p':
                                    case 'q':
                                    case 'r':
                                    case 's':
                                    case 't':
                                    case 'u':
                                    case 'v':
                                    case 'w':
                                    case 'x':
                                    case 'y':
                                    case 'z':
                                        matchRange('a', 'z');
                                        break;
                                    default:
                                        if (z) {
                                            tokenMakeToken = makeToken(5);
                                            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
                                        } else {
                                            tokenMakeToken = null;
                                        }
                                        this._returnToken = tokenMakeToken;
                                        return;
                                }
                                break;
                        }
                        break;
                }
            } else {
                match('_');
            }
        }
    }

    public final void mLPAREN(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        match('(');
        if (z) {
            tokenMakeToken = makeToken(8);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    public final void mRPAREN(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        match(')');
        if (z) {
            tokenMakeToken = makeToken(9);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    public final void mCOMMA(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        match(',');
        if (z) {
            tokenMakeToken = makeToken(10);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    public final void mSEMI(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        match(';');
        if (z) {
            tokenMakeToken = makeToken(6);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    public final void mCOLON(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        match(':');
        if (z) {
            tokenMakeToken = makeToken(11);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    public final void mSL_COMMENT(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        this.text.length();
        match("//");
        while (true) {
            BitSet bitSet = _tokenSet_0;
            if (!bitSet.member(LA(1))) {
                break;
            } else {
                match(bitSet);
            }
        }
        if (LA(1) == '\n' || LA(1) == '\r') {
            char cLA = LA(1);
            if (cLA != '\n') {
                if (cLA == '\r') {
                    match('\r');
                } else {
                    throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
                }
            }
            match('\n');
        }
        newline();
        this._returnToken = null;
    }

    public final void mML_COMMENT(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        this.text.length();
        match(RefSpec.WILDCARD_SUFFIX);
        while (true) {
            if (LA(1) == '*' && LA(2) == '/') {
                break;
            }
            if ((LA(1) == '\n' || LA(1) == '\r') && LA(2) >= 0 && LA(2) <= 65534) {
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
            } else if (LA(1) < 0 || LA(1) > 65534 || LA(2) < 0 || LA(2) > 65534) {
                break;
            } else {
                matchNot((char) 65535);
            }
        }
        match("*/");
        this._returnToken = null;
    }

    /* JADX WARN: Code duplicated, block: B:19:0x0043  */
    /* JADX WARN: Code duplicated, block: B:21:0x0049 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:22:0x004b  */
    /* JADX WARN: Code duplicated, block: B:29:0x004f A[SYNTHETIC] */
    public final void mWS(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        char cLA;
        this.text.length();
        int i = 0;
        while (true) {
            char cLA2 = LA(1);
            if (cLA2 == '\t') {
                match('\t');
            } else if (cLA2 == '\n') {
                cLA = LA(1);
                if (cLA != '\n') {
                    if (cLA == '\r') {
                        match('\r');
                    } else {
                        throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
                    }
                }
                match('\n');
                newline();
            } else if (cLA2 == '\f') {
                match('\f');
            } else if (cLA2 == '\r') {
                cLA = LA(1);
                if (cLA != '\n') {
                    if (cLA == '\r') {
                        match('\r');
                    } else {
                        throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
                    }
                }
                match('\n');
                newline();
            } else {
                if (cLA2 != ' ') {
                    if (i < 1) {
                        throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
                    }
                    this._returnToken = null;
                    return;
                }
                match(StringUtil.SPACE);
            }
            i++;
        }
    }

    private static final long[] mk_tokenSet_0() {
        long[] jArr = new long[2048];
        jArr[0] = -9217;
        for (int i = 1; i <= 1022; i++) {
            jArr[i] = -1;
        }
        jArr[1023] = Long.MAX_VALUE;
        return jArr;
    }
}
