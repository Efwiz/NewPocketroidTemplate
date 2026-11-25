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
import java.util.List;
import net.bytebuddy.asm.Advice;
import org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;

/* JADX INFO: loaded from: classes12.dex */
public class ActionLexer extends CharScanner implements ActionParserTokenTypes, TokenStream {
    public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
    public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
    public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
    public static final BitSet _tokenSet_3 = new BitSet(mk_tokenSet_3());
    public static final BitSet _tokenSet_4 = new BitSet(mk_tokenSet_4());
    public static final BitSet _tokenSet_5 = new BitSet(mk_tokenSet_5());
    public static final BitSet _tokenSet_6 = new BitSet(mk_tokenSet_6());

    public ActionLexer(InputStream inputStream) {
        this(new ByteBuffer(inputStream));
    }

    public ActionLexer(Reader reader) {
        this(new CharBuffer(reader));
    }

    public ActionLexer(InputBuffer inputBuffer) {
        this(new LexerSharedInputState(inputBuffer));
    }

    public ActionLexer(LexerSharedInputState lexerSharedInputState) {
        super(lexerSharedInputState);
        this.caseSensitiveLiterals = true;
        setCaseSensitive(true);
        this.literals = new Hashtable();
        this.literals.put(new ANTLRHashString(PsiKeyword.SUPER, this), new Integer(32));
        this.literals.put(new ANTLRHashString(PsiKeyword.IF, this), new Integer(8));
        this.literals.put(new ANTLRHashString("first", this), new Integer(26));
        this.literals.put(new ANTLRHashString("last", this), new Integer(28));
        this.literals.put(new ANTLRHashString("rest", this), new Integer(27));
        this.literals.put(new ANTLRHashString("trunc", this), new Integer(31));
        this.literals.put(new ANTLRHashString("strip", this), new Integer(30));
        this.literals.put(new ANTLRHashString("length", this), new Integer(29));
        this.literals.put(new ANTLRHashString("elseif", this), new Integer(18));
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:51:0x00cb A[Catch: CharStreamException -> 0x00f6, RecognitionException -> 0x00f8, TryCatch #0 {RecognitionException -> 0x00f8, blocks: (B:3:0x0004, B:23:0x0030, B:24:0x0033, B:25:0x0036, B:26:0x0039, B:27:0x003c, B:29:0x0044, B:31:0x004b, B:55:0x00e2, B:58:0x00e8, B:32:0x0052, B:34:0x0058, B:35:0x005f, B:37:0x0068, B:38:0x0073, B:39:0x0088, B:40:0x0089, B:41:0x008f, B:42:0x0095, B:43:0x009b, B:44:0x00a1, B:45:0x00a7, B:46:0x00ad, B:47:0x00b3, B:48:0x00b9, B:49:0x00bf, B:50:0x00c5, B:51:0x00cb, B:52:0x00d1, B:53:0x00d7, B:54:0x00dd), top: B:71:0x0004, outer: #1 }] */
    /* JADX WARN: Code duplicated, block: B:54:0x00dd A[Catch: CharStreamException -> 0x00f6, RecognitionException -> 0x00f8, TryCatch #0 {RecognitionException -> 0x00f8, blocks: (B:3:0x0004, B:23:0x0030, B:24:0x0033, B:25:0x0036, B:26:0x0039, B:27:0x003c, B:29:0x0044, B:31:0x004b, B:55:0x00e2, B:58:0x00e8, B:32:0x0052, B:34:0x0058, B:35:0x005f, B:37:0x0068, B:38:0x0073, B:39:0x0088, B:40:0x0089, B:41:0x008f, B:42:0x0095, B:43:0x009b, B:44:0x00a1, B:45:0x00a7, B:46:0x00ad, B:47:0x00b3, B:48:0x00b9, B:49:0x00bf, B:50:0x00c5, B:51:0x00cb, B:52:0x00d1, B:53:0x00d7, B:54:0x00dd), top: B:71:0x0004, outer: #1 }] */
    @Override // antlr.TokenStream
    public Token nextToken() throws TokenStreamException {
        do {
            resetText();
            try {
                try {
                    char cLA = LA(1);
                    if (cLA != '\t' && cLA != '\n' && cLA != '\r') {
                        if (cLA != '=') {
                            if (cLA != ']') {
                                if (cLA != '_') {
                                    if (cLA != '(') {
                                        if (cLA != ')') {
                                            if (cLA != '+') {
                                                if (cLA == ',') {
                                                    mCOMMA(true);
                                                    Token token = this._returnToken;
                                                } else {
                                                    switch (cLA) {
                                                        case ' ':
                                                            mWS(true);
                                                            Token token2 = this._returnToken;
                                                            break;
                                                        case '!':
                                                            mNOT(true);
                                                            Token token3 = this._returnToken;
                                                            break;
                                                        case '\"':
                                                            mSTRING(true);
                                                            Token token4 = this._returnToken;
                                                            break;
                                                        default:
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
                                                                    mINT(true);
                                                                    Token token5 = this._returnToken;
                                                                    break;
                                                                case ':':
                                                                    mCOLON(true);
                                                                    Token token6 = this._returnToken;
                                                                    break;
                                                                case ';':
                                                                    mSEMI(true);
                                                                    Token token7 = this._returnToken;
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
                                                                            mID(true);
                                                                            Token token8 = this._returnToken;
                                                                            break;
                                                                        case '[':
                                                                            mLBRACK(true);
                                                                            Token token9 = this._returnToken;
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
                                                                                    mID(true);
                                                                                    Token token10 = this._returnToken;
                                                                                    break;
                                                                                case '{':
                                                                                    mANONYMOUS_TEMPLATE(true);
                                                                                    Token token11 = this._returnToken;
                                                                                    break;
                                                                                default:
                                                                                    if (LA(1) == '.' && LA(2) == '.') {
                                                                                        mDOTDOTDOT(true);
                                                                                        Token token12 = this._returnToken;
                                                                                    } else if (LA(1) == '.') {
                                                                                        mDOT(true);
                                                                                        Token token13 = this._returnToken;
                                                                                    } else {
                                                                                        if (LA(1) != 65535) {
                                                                                            throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
                                                                                        }
                                                                                        uponEOF();
                                                                                        this._returnToken = makeToken(1);
                                                                                    }
                                                                                    break;
                                                                            }
                                                                            break;
                                                                    }
                                                                    break;
                                                            }
                                                            break;
                                                    }
                                                }
                                            } else {
                                                mPLUS(true);
                                                Token token14 = this._returnToken;
                                            }
                                        } else {
                                            mRPAREN(true);
                                            Token token15 = this._returnToken;
                                        }
                                    } else {
                                        mLPAREN(true);
                                        Token token16 = this._returnToken;
                                    }
                                } else {
                                    mID(true);
                                    Token token17 = this._returnToken;
                                }
                            } else {
                                mRBRACK(true);
                                Token token18 = this._returnToken;
                            }
                        } else {
                            mASSIGN(true);
                            Token token19 = this._returnToken;
                        }
                    } else {
                        mWS(true);
                        Token token20 = this._returnToken;
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
        this._returnToken.setType(this._returnToken.getType());
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
            if (cLA == '_') {
                match('_');
            } else {
                switch (cLA) {
                    case '/':
                        match('/');
                        break;
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
                                        int iTestLiteralsTable = testLiteralsTable(20);
                                        if (!z || iTestLiteralsTable == -1) {
                                            tokenMakeToken = null;
                                        } else {
                                            tokenMakeToken = makeToken(iTestLiteralsTable);
                                            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
                                        }
                                        this._returnToken = tokenMakeToken;
                                        return;
                                }
                                break;
                        }
                        break;
                }
            }
        }
    }

    public final void mINT(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        int i = 0;
        while (LA(1) >= '0' && LA(1) <= '9') {
            matchRange(ExternalAnnotationProvider.NULLABLE, '9');
            i++;
        }
        if (i < 1) {
            throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
        }
        if (z) {
            tokenMakeToken = makeToken(35);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    public final void mSTRING(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        int length2 = this.text.length();
        match('\"');
        this.text.setLength(length2);
        while (true) {
            if (LA(1) == '\\') {
                mESC_CHAR(false, true);
            } else if (!_tokenSet_0.member(LA(1))) {
                break;
            } else {
                matchNot('\"');
            }
        }
        int length3 = this.text.length();
        match('\"');
        this.text.setLength(length3);
        if (z) {
            tokenMakeToken = makeToken(34);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    protected final void mESC_CHAR(boolean z, boolean z2) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        match('\\');
        if (LA(1) == 'n' && LA(2) >= 3 && LA(2) <= 65534) {
            match('n');
            if (this.inputState.guessing == 0 && z2) {
                this.text.setLength(length);
                this.text.append("\n");
            }
        } else if (LA(1) == 'r' && LA(2) >= 3 && LA(2) <= 65534) {
            match(Advice.OffsetMapping.ForOrigin.Renderer.ForReturnTypeName.SYMBOL);
            if (this.inputState.guessing == 0 && z2) {
                this.text.setLength(length);
                this.text.append("\r");
            }
        } else if (LA(1) == 't' && LA(2) >= 3 && LA(2) <= 65534) {
            match(Advice.OffsetMapping.ForOrigin.Renderer.ForTypeName.SYMBOL);
            if (this.inputState.guessing == 0 && z2) {
                this.text.setLength(length);
                this.text.append("\t");
            }
        } else if (LA(1) == 'b' && LA(2) >= 3 && LA(2) <= 65534) {
            match('b');
            if (this.inputState.guessing == 0 && z2) {
                this.text.setLength(length);
                this.text.append("\b");
            }
        } else if (LA(1) == 'f' && LA(2) >= 3 && LA(2) <= 65534) {
            match('f');
            if (this.inputState.guessing == 0 && z2) {
                this.text.setLength(length);
                this.text.append("\f");
            }
        } else if (LA(1) >= 3 && LA(1) <= 65534 && LA(2) >= 3 && LA(2) <= 65534) {
            char cLA = LA(1);
            matchNot((char) 65535);
            if (this.inputState.guessing == 0 && z2) {
                this.text.setLength(length);
                this.text.append(String.valueOf(cLA));
            }
        } else {
            throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
        }
        if (z) {
            tokenMakeToken = makeToken(41);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    /* JADX WARN: Code duplicated, block: B:58:0x013b  */
    /* JADX WARN: Code duplicated, block: B:83:0x012f A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:84:0x012b A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:85:0x013f A[EDGE_INSN: B:85:0x013f->B:59:0x013f BREAK  A[LOOP:0: B:36:0x00cf->B:86:0x00cf], SYNTHETIC] */
    public final void mANONYMOUS_TEMPLATE(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        boolean z2;
        StringTemplateToken stringTemplateToken;
        Token tokenMakeToken;
        int length = this.text.length();
        int length2 = this.text.length();
        match('{');
        this.text.setLength(length2);
        if (_tokenSet_1.member(LA(1)) && _tokenSet_2.member(LA(2))) {
            int iMark = mark();
            this.inputState.guessing++;
            try {
                mTEMPLATE_ARGS(false);
                z2 = true;
            } catch (RecognitionException unused) {
                z2 = false;
            }
            rewind(iMark);
            this.inputState.guessing--;
        } else {
            z2 = false;
        }
        if (z2) {
            List listMTEMPLATE_ARGS = mTEMPLATE_ARGS(false);
            if (_tokenSet_3.member(LA(1)) && LA(2) >= 3 && LA(2) <= 65534) {
                int length3 = this.text.length();
                mWS_CHAR(false);
                this.text.setLength(length3);
            } else if (LA(1) < 3 || LA(1) > 65534) {
                throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
            }
            stringTemplateToken = this.inputState.guessing == 0 ? new StringTemplateToken(33, new String(this.text.getBuffer(), length, this.text.length() - length), listMTEMPLATE_ARGS) : null;
            tokenMakeToken = stringTemplateToken;
            while (true) {
                if (LA(1) != '\\' && LA(2) == '{') {
                    int length4 = this.text.length();
                    match('\\');
                    this.text.setLength(length4);
                    match('{');
                } else if (LA(1) != '\\' && LA(2) == '}') {
                    int length5 = this.text.length();
                    match('\\');
                    this.text.setLength(length5);
                    match('}');
                } else if (LA(1) != '\\' && LA(2) >= 3 && LA(2) <= 65534) {
                    mESC_CHAR(false, false);
                } else if (LA(1) == '{') {
                    mNESTED_ANONYMOUS_TEMPLATE(false);
                } else if (_tokenSet_4.member(LA(1))) {
                    break;
                } else {
                    matchNot('}');
                }
            }
            if (this.inputState.guessing == 0 && stringTemplateToken != null) {
                stringTemplateToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
            }
            int length6 = this.text.length();
            match('}');
            this.text.setLength(length6);
            if (z && tokenMakeToken == null) {
                tokenMakeToken = makeToken(33);
                tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
            }
            this._returnToken = tokenMakeToken;
        }
        if (LA(1) < 3 || LA(1) > 65534) {
            throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
        }
        tokenMakeToken = stringTemplateToken;
        while (true) {
            if (LA(1) != '\\') {
            }
            if (LA(1) != '\\') {
            }
            if (LA(1) != '\\') {
            }
            if (LA(1) == '{') {
                mNESTED_ANONYMOUS_TEMPLATE(false);
            } else {
                if (_tokenSet_4.member(LA(1))) {
                    break;
                    break;
                }
                matchNot('}');
            }
        }
        if (this.inputState.guessing == 0) {
            stringTemplateToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        }
        int length7 = this.text.length();
        match('}');
        this.text.setLength(length7);
        if (z) {
            tokenMakeToken = makeToken(33);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        }
        this._returnToken = tokenMakeToken;
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Not found exit edge by exit block: B:17:0x0076
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.checkLoopExits(LoopRegionMaker.java:272)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.makeLoopRegion(LoopRegionMaker.java:237)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.process(LoopRegionMaker.java:80)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:92)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.addCases(SwitchRegionMaker.java:127)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.process(SwitchRegionMaker.java:75)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:115)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:111)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:109)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:117)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:109)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.process(LoopRegionMaker.java:162)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:92)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeMthRegion(RegionMaker.java:49)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:25)
        */
    protected final java.util.List mTEMPLATE_ARGS(boolean r13) throws antlr.RecognitionException, antlr.CharStreamException, antlr.TokenStreamException {
        /*
            Method dump skipped, instruction units count: 612
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.antlr.stringtemplate.language.ActionLexer.mTEMPLATE_ARGS(boolean):java.util.List");
    }

    protected final void mWS_CHAR(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        char cLA = LA(1);
        if (cLA == '\t') {
            match('\t');
        } else if (cLA == '\n') {
            match('\n');
            if (this.inputState.guessing == 0) {
                newline();
            }
        } else if (cLA == '\r') {
            match('\r');
        } else if (cLA == ' ') {
            match(StringUtil.SPACE);
        } else {
            throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
        }
        if (z) {
            tokenMakeToken = makeToken(43);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    protected final void mNESTED_ANONYMOUS_TEMPLATE(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        match('{');
        while (true) {
            if (LA(1) == '\\' && LA(2) == '{') {
                int length2 = this.text.length();
                match('\\');
                this.text.setLength(length2);
                match('{');
            } else if (LA(1) == '\\' && LA(2) == '}') {
                int length3 = this.text.length();
                match('\\');
                this.text.setLength(length3);
                match('}');
            } else if (LA(1) == '\\' && LA(2) >= 3 && LA(2) <= 65534) {
                mESC_CHAR(false, false);
            } else if (LA(1) == '{') {
                mNESTED_ANONYMOUS_TEMPLATE(false);
            } else if (!_tokenSet_4.member(LA(1))) {
                break;
            } else {
                matchNot('}');
            }
        }
        match('}');
        if (z) {
            tokenMakeToken = makeToken(40);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    public final void mLBRACK(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        match('[');
        if (z) {
            tokenMakeToken = makeToken(36);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    public final void mRBRACK(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        match(']');
        if (z) {
            tokenMakeToken = makeToken(37);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    public final void mLPAREN(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        match('(');
        if (z) {
            tokenMakeToken = makeToken(16);
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
            tokenMakeToken = makeToken(17);
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
            tokenMakeToken = makeToken(19);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    public final void mDOT(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        match('.');
        if (z) {
            tokenMakeToken = makeToken(25);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    public final void mASSIGN(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        match('=');
        if (z) {
            tokenMakeToken = makeToken(21);
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
            tokenMakeToken = makeToken(22);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    public final void mPLUS(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        match('+');
        if (z) {
            tokenMakeToken = makeToken(24);
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
            tokenMakeToken = makeToken(15);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    public final void mNOT(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        match('!');
        if (z) {
            tokenMakeToken = makeToken(23);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    public final void mDOTDOTDOT(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        match("...");
        if (z) {
            tokenMakeToken = makeToken(38);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    public final void mWS(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        int i = 0;
        while (true) {
            char cLA = LA(1);
            if (cLA == '\t') {
                match('\t');
            } else if (cLA == '\n') {
                match('\n');
                if (this.inputState.guessing == 0) {
                    newline();
                }
            } else if (cLA == '\r') {
                match('\r');
            } else if (cLA != ' ') {
                break;
            } else {
                match(StringUtil.SPACE);
            }
            i++;
        }
        if (i < 1) {
            throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
        }
        int i2 = this.inputState.guessing == 0 ? -1 : 42;
        if (!z || i2 == -1) {
            tokenMakeToken = null;
        } else {
            tokenMakeToken = makeToken(i2);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        }
        this._returnToken = tokenMakeToken;
    }

    private static final long[] mk_tokenSet_0() {
        long[] jArr = new long[2048];
        jArr[0] = -17179869192L;
        jArr[1] = -268435457;
        for (int i = 2; i <= 1022; i++) {
            jArr[i] = -1;
        }
        jArr[1023] = Long.MAX_VALUE;
        return jArr;
    }

    private static final long[] mk_tokenSet_1() {
        long[] jArr = new long[1025];
        jArr[0] = 4294977024L;
        jArr[1] = 576460745995190270L;
        return jArr;
    }

    private static final long[] mk_tokenSet_2() {
        long[] jArr = new long[1025];
        jArr[0] = 288107235144377856L;
        jArr[1] = 1729382250602037246L;
        return jArr;
    }

    private static final long[] mk_tokenSet_3() {
        long[] jArr = new long[1025];
        jArr[0] = 4294977024L;
        return jArr;
    }

    private static final long[] mk_tokenSet_4() {
        long[] jArr = new long[2048];
        jArr[0] = -8;
        jArr[1] = -2882303761785552897L;
        for (int i = 2; i <= 1022; i++) {
            jArr[i] = -1;
        }
        jArr[1023] = Long.MAX_VALUE;
        return jArr;
    }

    private static final long[] mk_tokenSet_5() {
        long[] jArr = new long[1025];
        jArr[0] = 17596481021440L;
        return jArr;
    }

    private static final long[] mk_tokenSet_6() {
        long[] jArr = new long[1025];
        jArr[0] = 17596481021440L;
        jArr[1] = 576460745995190270L;
        return jArr;
    }
}
