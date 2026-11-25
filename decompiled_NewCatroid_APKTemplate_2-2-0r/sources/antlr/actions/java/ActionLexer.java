package antlr.actions.java;

import antlr.ActionTransInfo;
import antlr.ByteBuffer;
import antlr.CharBuffer;
import antlr.CharScanner;
import antlr.CharStreamException;
import antlr.CharStreamIOException;
import antlr.CodeGenerator;
import antlr.InputBuffer;
import antlr.LexerSharedInputState;
import antlr.NoViableAltForCharException;
import antlr.RecognitionException;
import antlr.RuleBlock;
import antlr.Token;
import antlr.TokenStream;
import antlr.TokenStreamException;
import antlr.TokenStreamIOException;
import antlr.TokenStreamRecognitionException;
import antlr.Tool;
import antlr.collections.impl.BitSet;
import antlr.collections.impl.Vector;
import com.huawei.hms.ml.language.common.utils.Constant;
import io.netty.util.internal.StringUtil;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.Hashtable;
import net.bytebuddy.asm.Advice;
import org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider;
import org.eclipse.jdt.internal.compiler.lookup.TagBits;
import org.eclipse.jgit.transport.RefSpec;

/* JADX INFO: loaded from: classes.dex */
public class ActionLexer extends CharScanner implements ActionLexerTokenTypes, TokenStream {
    private Tool antlrTool;
    protected RuleBlock currentRule;
    protected CodeGenerator generator;
    protected int lineOffset;
    ActionTransInfo transInfo;
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
    public static final BitSet _tokenSet_14 = new BitSet(mk_tokenSet_14());
    public static final BitSet _tokenSet_15 = new BitSet(mk_tokenSet_15());
    public static final BitSet _tokenSet_16 = new BitSet(mk_tokenSet_16());
    public static final BitSet _tokenSet_17 = new BitSet(mk_tokenSet_17());
    public static final BitSet _tokenSet_18 = new BitSet(mk_tokenSet_18());
    public static final BitSet _tokenSet_19 = new BitSet(mk_tokenSet_19());
    public static final BitSet _tokenSet_20 = new BitSet(mk_tokenSet_20());
    public static final BitSet _tokenSet_21 = new BitSet(mk_tokenSet_21());
    public static final BitSet _tokenSet_22 = new BitSet(mk_tokenSet_22());
    public static final BitSet _tokenSet_23 = new BitSet(mk_tokenSet_23());
    public static final BitSet _tokenSet_24 = new BitSet(mk_tokenSet_24());
    public static final BitSet _tokenSet_25 = new BitSet(mk_tokenSet_25());

    public ActionLexer(String str, RuleBlock ruleBlock, CodeGenerator codeGenerator, ActionTransInfo actionTransInfo) {
        this(new StringReader(str));
        this.currentRule = ruleBlock;
        this.generator = codeGenerator;
        this.transInfo = actionTransInfo;
    }

    public void setLineOffset(int i) {
        setLine(i);
    }

    public void setTool(Tool tool) {
        this.antlrTool = tool;
    }

    @Override // antlr.CharScanner
    public void reportError(RecognitionException recognitionException) {
        this.antlrTool.error(new StringBuffer("Syntax error in action: ").append(recognitionException).toString(), getFilename(), getLine(), getColumn());
    }

    @Override // antlr.CharScanner
    public void reportError(String str) {
        this.antlrTool.error(str, getFilename(), getLine(), getColumn());
    }

    @Override // antlr.CharScanner
    public void reportWarning(String str) {
        if (getFilename() == null) {
            this.antlrTool.warning(str);
        } else {
            this.antlrTool.warning(str, getFilename(), getLine(), getColumn());
        }
    }

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
        this.lineOffset = 0;
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
                    if (LA(1) >= 3 && LA(1) <= 255) {
                        mACTION(true);
                        Token token = this._returnToken;
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
        this._returnToken.setType(this._returnToken.getType());
        return this._returnToken;
    }

    public final void mACTION(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        int i = 0;
        while (true) {
            char cLA = LA(1);
            if (cLA == '#') {
                mAST_ITEM(false);
            } else if (cLA == '$') {
                mTEXT_ITEM(false);
            } else if (!_tokenSet_0.member(LA(1))) {
                break;
            } else {
                mSTUFF(false);
            }
            i++;
        }
        if (i < 1) {
            throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
        }
        if (z) {
            tokenMakeToken = makeToken(4);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    /* JADX WARN: Code duplicated, block: B:25:0x0064  */
    /* JADX WARN: Code duplicated, block: B:27:0x006a  */
    /* JADX WARN: Code duplicated, block: B:28:0x0071  */
    /* JADX WARN: Code duplicated, block: B:30:0x007d  */
    /* JADX WARN: Code duplicated, block: B:31:0x0081  */
    protected final void mSTUFF(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        BitSet bitSet;
        int length = this.text.length();
        char cLA = LA(1);
        if (cLA == '\n') {
            match('\n');
            newline();
        } else if (cLA == '\"') {
            mSTRING(false);
        } else if (cLA == '\'') {
            mCHAR(false);
        } else if (LA(1) == '/' && (LA(2) == '*' || LA(2) == '/')) {
            mCOMMENT(false);
        } else if (LA(1) == '\r' && LA(2) == '\n') {
            match("\r\n");
            newline();
        } else if (LA(1) == '/') {
            BitSet bitSet2 = _tokenSet_1;
            if (bitSet2.member(LA(2))) {
                match('/');
                match(bitSet2);
            } else if (LA(1) == '\r') {
                match('\r');
                newline();
            } else {
                bitSet = _tokenSet_2;
                if (bitSet.member(LA(1))) {
                    match(bitSet);
                } else {
                    throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
                }
            }
        } else if (LA(1) == '\r') {
            match('\r');
            newline();
        } else {
            bitSet = _tokenSet_2;
            if (bitSet.member(LA(1))) {
                match(bitSet);
            } else {
                throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
            }
        }
        if (z) {
            tokenMakeToken = makeToken(5);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    protected final void mAST_ITEM(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        if (LA(1) == '#' && LA(2) == '(') {
            int length2 = this.text.length();
            match('#');
            this.text.setLength(length2);
            mTREE(true);
            Token token = this._returnToken;
        } else if (LA(1) == '#' && _tokenSet_3.member(LA(2))) {
            int length3 = this.text.length();
            match('#');
            this.text.setLength(length3);
            mID(true);
            String strMapTreeId = this.generator.mapTreeId(this._returnToken.getText(), this.transInfo);
            if (strMapTreeId != null) {
                this.text.setLength(length);
                this.text.append(strMapTreeId);
            }
            if (_tokenSet_4.member(LA(1))) {
                mWS(false);
            }
            if (LA(1) == '=') {
                mVAR_ASSIGN(false);
            }
        } else if (LA(1) == '#' && LA(2) == '[') {
            int length4 = this.text.length();
            match('#');
            this.text.setLength(length4);
            mAST_CONSTRUCTOR(true);
            Token token2 = this._returnToken;
        } else if (LA(1) == '#' && LA(2) == '#') {
            match("##");
            String string = new StringBuffer().append(this.currentRule.getRuleName()).append("_AST").toString();
            this.text.setLength(length);
            this.text.append(string);
            ActionTransInfo actionTransInfo = this.transInfo;
            if (actionTransInfo != null) {
                actionTransInfo.refRuleRoot = string;
            }
            if (_tokenSet_4.member(LA(1))) {
                mWS(false);
            }
            if (LA(1) == '=') {
                mVAR_ASSIGN(false);
            }
        } else {
            throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
        }
        if (z) {
            tokenMakeToken = makeToken(6);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    protected final void mTEXT_ITEM(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token token;
        Token tokenMakeToken;
        Token token2;
        int length = this.text.length();
        if (LA(1) == '$' && LA(2) == 'F' && LA(3) == 'O') {
            match("$FOLLOW");
            if (!_tokenSet_5.member(LA(1)) || !_tokenSet_6.member(LA(2)) || LA(3) < 3 || LA(3) > 255) {
                token2 = null;
            } else {
                char cLA = LA(1);
                if (cLA == '\t' || cLA == '\n' || cLA == '\r' || cLA == ' ') {
                    mWS(false);
                } else if (cLA != '(') {
                    throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
                }
                match('(');
                mTEXT_ARG(true);
                token2 = this._returnToken;
                match(')');
            }
            String ruleName = this.currentRule.getRuleName();
            if (token2 != null) {
                ruleName = token2.getText();
            }
            String fOLLOWBitSet = this.generator.getFOLLOWBitSet(ruleName, 1);
            if (fOLLOWBitSet == null) {
                reportError(new StringBuffer("$FOLLOW(").append(ruleName).append("): unknown rule or bad lookahead computation").toString());
            } else {
                this.text.setLength(length);
                this.text.append(fOLLOWBitSet);
            }
        } else if (LA(1) == '$' && LA(2) == 'F' && LA(3) == 'I') {
            match("$FIRST");
            if (!_tokenSet_5.member(LA(1)) || !_tokenSet_6.member(LA(2)) || LA(3) < 3 || LA(3) > 255) {
                token = null;
            } else {
                char cLA2 = LA(1);
                if (cLA2 == '\t' || cLA2 == '\n' || cLA2 == '\r' || cLA2 == ' ') {
                    mWS(false);
                } else if (cLA2 != '(') {
                    throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
                }
                match('(');
                mTEXT_ARG(true);
                token = this._returnToken;
                match(')');
            }
            String ruleName2 = this.currentRule.getRuleName();
            if (token != null) {
                ruleName2 = token.getText();
            }
            String fIRSTBitSet = this.generator.getFIRSTBitSet(ruleName2, 1);
            if (fIRSTBitSet == null) {
                reportError(new StringBuffer("$FIRST(").append(ruleName2).append("): unknown rule or bad lookahead computation").toString());
            } else {
                this.text.setLength(length);
                this.text.append(fIRSTBitSet);
            }
        } else if (LA(1) == '$' && LA(2) == 'a') {
            match("$append");
            char cLA3 = LA(1);
            if (cLA3 == '\t' || cLA3 == '\n' || cLA3 == '\r' || cLA3 == ' ') {
                mWS(false);
            } else if (cLA3 != '(') {
                throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
            }
            match('(');
            mTEXT_ARG(true);
            Token token3 = this._returnToken;
            match(')');
            String string = new StringBuffer("text.append(").append(token3.getText()).append(Constant.AFTER_QUTO).toString();
            this.text.setLength(length);
            this.text.append(string);
        } else if (LA(1) == '$' && LA(2) == 's') {
            match("$set");
            if (LA(1) == 'T' && LA(2) == 'e') {
                match("Text");
                char cLA4 = LA(1);
                if (cLA4 == '\t' || cLA4 == '\n' || cLA4 == '\r' || cLA4 == ' ') {
                    mWS(false);
                } else if (cLA4 != '(') {
                    throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
                }
                match('(');
                mTEXT_ARG(true);
                Token token4 = this._returnToken;
                match(')');
                String string2 = new StringBuffer("text.setLength(_begin); text.append(").append(token4.getText()).append(Constant.AFTER_QUTO).toString();
                this.text.setLength(length);
                this.text.append(string2);
            } else if (LA(1) == 'T' && LA(2) == 'o') {
                match("Token");
                char cLA5 = LA(1);
                if (cLA5 == '\t' || cLA5 == '\n' || cLA5 == '\r' || cLA5 == ' ') {
                    mWS(false);
                } else if (cLA5 != '(') {
                    throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
                }
                match('(');
                mTEXT_ARG(true);
                Token token5 = this._returnToken;
                match(')');
                String string3 = new StringBuffer("_token = ").append(token5.getText()).toString();
                this.text.setLength(length);
                this.text.append(string3);
            } else if (LA(1) == 'T' && LA(2) == 'y') {
                match("Type");
                char cLA6 = LA(1);
                if (cLA6 == '\t' || cLA6 == '\n' || cLA6 == '\r' || cLA6 == ' ') {
                    mWS(false);
                } else if (cLA6 != '(') {
                    throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
                }
                match('(');
                mTEXT_ARG(true);
                Token token6 = this._returnToken;
                match(')');
                String string4 = new StringBuffer("_ttype = ").append(token6.getText()).toString();
                this.text.setLength(length);
                this.text.append(string4);
            } else {
                throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
            }
        } else if (LA(1) == '$' && LA(2) == 'g') {
            match("$getText");
            this.text.setLength(length);
            this.text.append("new String(text.getBuffer(),_begin,text.length()-_begin)");
        } else {
            throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
        }
        if (z) {
            tokenMakeToken = makeToken(7);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    protected final void mCOMMENT(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        if (LA(1) == '/' && LA(2) == '/') {
            mSL_COMMENT(false);
        } else if (LA(1) == '/' && LA(2) == '*') {
            mML_COMMENT(false);
        } else {
            throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
        }
        if (z) {
            tokenMakeToken = makeToken(19);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    protected final void mSTRING(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        match('\"');
        while (true) {
            if (LA(1) == '\\') {
                mESC(false);
            } else if (!_tokenSet_7.member(LA(1))) {
                break;
            } else {
                matchNot('\"');
            }
        }
        match('\"');
        if (z) {
            tokenMakeToken = makeToken(23);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    protected final void mCHAR(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        match('\'');
        if (LA(1) == '\\') {
            mESC(false);
        } else if (_tokenSet_8.member(LA(1))) {
            matchNot('\'');
        } else {
            throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
        }
        match('\'');
        if (z) {
            tokenMakeToken = makeToken(22);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    /* JADX WARN: Code duplicated, block: B:29:0x00c1  */
    /* JADX WARN: Code duplicated, block: B:42:0x00ff  */
    protected final void mTREE(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        Vector vector = new Vector(10);
        int length2 = this.text.length();
        match('(');
        this.text.setLength(length2);
        char cLA = LA(1);
        if (cLA != '\t' && cLA != '\n' && cLA != '\r' && cLA != ' ') {
            if (cLA != '(' && cLA != '_' && cLA != '\"' && cLA != '#') {
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
                    case '[':
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
                                break;
                            default:
                                throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
                        }
                        break;
                }
            }
        } else {
            int length3 = this.text.length();
            mWS(false);
            this.text.setLength(length3);
        }
        int length4 = this.text.length();
        mTREE_ELEMENT(true);
        this.text.setLength(length4);
        vector.appendElement(this._returnToken.getText());
        char cLA2 = LA(1);
        if (cLA2 == '\t' || cLA2 == '\n' || cLA2 == '\r' || cLA2 == ' ') {
            int length5 = this.text.length();
            mWS(false);
            this.text.setLength(length5);
        } else if (cLA2 != ')' && cLA2 != ',') {
            throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
        }
        while (LA(1) == ',') {
            int length6 = this.text.length();
            match(',');
            this.text.setLength(length6);
            char cLA3 = LA(1);
            if (cLA3 == '\t' && cLA3 != '\n' && cLA3 != '\r' && cLA3 != ' ') {
                if (cLA3 != '(' && cLA3 != '_' && cLA3 != '\"' && cLA3 != '#') {
                    switch (cLA3) {
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
                        case '[':
                            break;
                        default:
                            switch (cLA3) {
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
                                    throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
                            }
                            while (LA(1) == ',') {
                                int length7 = this.text.length();
                                match(',');
                                this.text.setLength(length7);
                                char cLA4 = LA(1);
                                if (cLA4 == '\t') {
                                    int length8 = this.text.length();
                                    mWS(false);
                                    this.text.setLength(length8);
                                } else {
                                    int length9 = this.text.length();
                                    mWS(false);
                                    this.text.setLength(length9);
                                }
                                break;
                            }
                            break;
                    }
                }
            } else {
                int length10 = this.text.length();
                mWS(false);
                this.text.setLength(length10);
            }
            int length11 = this.text.length();
            mTREE_ELEMENT(true);
            this.text.setLength(length11);
            vector.appendElement(this._returnToken.getText());
            char cLA5 = LA(1);
            if (cLA5 == '\t' || cLA5 == '\n' || cLA5 == '\r' || cLA5 == ' ') {
                int length12 = this.text.length();
                mWS(false);
                this.text.setLength(length12);
            } else if (cLA5 == ')') {
                continue;
            } else if (cLA5 != ',') {
                throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
            }
        }
        this.text.setLength(length);
        this.text.append(this.generator.getASTCreateString(vector));
        int length13 = this.text.length();
        match(')');
        this.text.setLength(length13);
        if (z) {
            tokenMakeToken = makeToken(8);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    protected final void mID(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
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
        while (_tokenSet_9.member(LA(1))) {
            char cLA = LA(1);
            if (cLA != '_') {
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
                                        throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
                                }
                                break;
                        }
                        break;
                }
            } else {
                match('_');
            }
        }
        if (z) {
            tokenMakeToken = makeToken(17);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    protected final void mWS(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        int i = 0;
        while (true) {
            if (LA(1) == '\r' && LA(2) == '\n') {
                match('\r');
                match('\n');
                newline();
            } else if (LA(1) == ' ') {
                match(StringUtil.SPACE);
            } else if (LA(1) == '\t') {
                match('\t');
            } else if (LA(1) == '\r') {
                match('\r');
                newline();
            } else {
                if (LA(1) != '\n') {
                    break;
                }
                match('\n');
                newline();
            }
            i++;
        }
        if (i < 1) {
            throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
        }
        if (z) {
            tokenMakeToken = makeToken(28);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    protected final void mVAR_ASSIGN(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        ActionTransInfo actionTransInfo;
        int length = this.text.length();
        match('=');
        if (LA(1) != '=' && (actionTransInfo = this.transInfo) != null && actionTransInfo.refRuleRoot != null) {
            this.transInfo.assignToRoot = true;
        }
        if (z) {
            tokenMakeToken = makeToken(18);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    /* JADX WARN: Code duplicated, block: B:104:0x023e  */
    /* JADX WARN: Code duplicated, block: B:51:0x0125 A[FALL_THROUGH] */
    /* JADX WARN: Code duplicated, block: B:53:0x013b A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:62:0x015e  */
    /* JADX WARN: Code duplicated, block: B:94:0x0207 A[FALL_THROUGH] */
    /* JADX WARN: Code duplicated, block: B:96:0x021d A[ADDED_TO_REGION] */
    protected final void mAST_CONSTRUCTOR(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token token;
        Token token2;
        char cLA;
        Token tokenMakeToken;
        char cLA2;
        int length = this.text.length();
        int length2 = this.text.length();
        match('[');
        this.text.setLength(length2);
        char cLA3 = LA(1);
        if (cLA3 != '\t' && cLA3 != '\n' && cLA3 != '\r' && cLA3 != ' ') {
            if (cLA3 != '(' && cLA3 != '_' && cLA3 != '\"' && cLA3 != '#') {
                switch (cLA3) {
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
                        break;
                    default:
                        switch (cLA3) {
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
                            case '[':
                                break;
                            default:
                                switch (cLA3) {
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
                                        throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
                                }
                                break;
                        }
                        break;
                }
            }
        } else {
            int length3 = this.text.length();
            mWS(false);
            this.text.setLength(length3);
        }
        int length4 = this.text.length();
        mAST_CTOR_ELEMENT(true);
        this.text.setLength(length4);
        Token token3 = this._returnToken;
        char cLA4 = LA(1);
        if (cLA4 == '\t' || cLA4 == '\n' || cLA4 == '\r' || cLA4 == ' ') {
            int length5 = this.text.length();
            mWS(false);
            this.text.setLength(length5);
        } else if (cLA4 != ',' && cLA4 != ']') {
            throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
        }
        if (LA(1) == ',' && _tokenSet_10.member(LA(2)) && LA(3) >= 3 && LA(3) <= 255) {
            int length6 = this.text.length();
            match(',');
            this.text.setLength(length6);
            char cLA5 = LA(1);
            if (cLA5 != '\t' && cLA5 != '\n' && cLA5 != '\r' && cLA5 != ' ') {
                if (cLA5 != '(' && cLA5 != '_' && cLA5 != '\"' && cLA5 != '#') {
                    switch (cLA5) {
                        default:
                            switch (cLA5) {
                                default:
                                    switch (cLA5) {
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
                                            throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
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
                                case '[':
                                    int length7 = this.text.length();
                                    mAST_CTOR_ELEMENT(true);
                                    this.text.setLength(length7);
                                    token = this._returnToken;
                                    cLA2 = LA(1);
                                    if (cLA2 != '\t' || cLA2 == '\n' || cLA2 == '\r' || cLA2 == ' ') {
                                        int length8 = this.text.length();
                                        mWS(false);
                                        this.text.setLength(length8);
                                    } else if (cLA2 != ',' && cLA2 != ']') {
                                        throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
                                    }
                                    break;
                            }
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
                            int length9 = this.text.length();
                            mAST_CTOR_ELEMENT(true);
                            this.text.setLength(length9);
                            token = this._returnToken;
                            cLA2 = LA(1);
                            if (cLA2 != '\t') {
                                int length10 = this.text.length();
                                mWS(false);
                                this.text.setLength(length10);
                            } else {
                                int length11 = this.text.length();
                                mWS(false);
                                this.text.setLength(length11);
                            }
                            break;
                    }
                }
            } else {
                int length12 = this.text.length();
                mWS(false);
                this.text.setLength(length12);
            }
            int length13 = this.text.length();
            mAST_CTOR_ELEMENT(true);
            this.text.setLength(length13);
            token = this._returnToken;
            cLA2 = LA(1);
            if (cLA2 != '\t') {
                int length14 = this.text.length();
                mWS(false);
                this.text.setLength(length14);
            } else {
                int length15 = this.text.length();
                mWS(false);
                this.text.setLength(length15);
            }
        } else {
            if (LA(1) != ',' && LA(1) != ']') {
                throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
            }
            token = null;
        }
        char cLA6 = LA(1);
        if (cLA6 == ',') {
            int length16 = this.text.length();
            match(',');
            this.text.setLength(length16);
            char cLA7 = LA(1);
            if (cLA7 != '\t' && cLA7 != '\n' && cLA7 != '\r' && cLA7 != ' ') {
                if (cLA7 != '(' && cLA7 != '_' && cLA7 != '\"' && cLA7 != '#') {
                    switch (cLA7) {
                        default:
                            switch (cLA7) {
                                default:
                                    switch (cLA7) {
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
                                            throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
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
                                case '[':
                                    int length17 = this.text.length();
                                    mAST_CTOR_ELEMENT(true);
                                    this.text.setLength(length17);
                                    token2 = this._returnToken;
                                    cLA = LA(1);
                                    if (cLA != '\t' || cLA == '\n' || cLA == '\r' || cLA == ' ') {
                                        int length18 = this.text.length();
                                        mWS(false);
                                        this.text.setLength(length18);
                                    } else if (cLA != ']') {
                                        throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
                                    }
                                    break;
                            }
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
                            int length19 = this.text.length();
                            mAST_CTOR_ELEMENT(true);
                            this.text.setLength(length19);
                            token2 = this._returnToken;
                            cLA = LA(1);
                            if (cLA != '\t') {
                                int length110 = this.text.length();
                                mWS(false);
                                this.text.setLength(length110);
                            } else {
                                int length111 = this.text.length();
                                mWS(false);
                                this.text.setLength(length111);
                            }
                            break;
                    }
                }
            } else {
                int length20 = this.text.length();
                mWS(false);
                this.text.setLength(length20);
            }
            int length112 = this.text.length();
            mAST_CTOR_ELEMENT(true);
            this.text.setLength(length112);
            token2 = this._returnToken;
            cLA = LA(1);
            if (cLA != '\t') {
                int length113 = this.text.length();
                mWS(false);
                this.text.setLength(length113);
            } else {
                int length114 = this.text.length();
                mWS(false);
                this.text.setLength(length114);
            }
        } else {
            if (cLA6 != ']') {
                throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
            }
            token2 = null;
        }
        int length21 = this.text.length();
        match(']');
        this.text.setLength(length21);
        String text = token3.getText();
        if (token != null) {
            text = new StringBuffer().append(text).append(",").append(token.getText()).toString();
        }
        if (token2 != null) {
            text = new StringBuffer().append(text).append(",").append(token2.getText()).toString();
        }
        this.text.setLength(length);
        this.text.append(this.generator.getASTCreateString(null, text));
        if (z) {
            tokenMakeToken = makeToken(10);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    protected final void mTEXT_ARG(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        char cLA = LA(1);
        if (cLA != '\t' && cLA != '\n' && cLA != '\r' && cLA != ' ') {
            if (cLA != '\"' && cLA != '$' && cLA != '\'' && cLA != '+' && cLA != '_') {
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
                                        break;
                                    default:
                                        throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
                                }
                                break;
                        }
                        break;
                }
            }
        } else {
            mWS(false);
        }
        int i = 0;
        while (_tokenSet_11.member(LA(1)) && LA(2) >= 3 && LA(2) <= 255) {
            mTEXT_ARG_ELEMENT(false);
            if (_tokenSet_4.member(LA(1)) && _tokenSet_12.member(LA(2))) {
                mWS(false);
            } else if (!_tokenSet_12.member(LA(1))) {
                throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
            }
            i++;
        }
        if (i < 1) {
            throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
        }
        if (z) {
            tokenMakeToken = makeToken(13);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:35:0x00ee  */
    protected final void mTREE_ELEMENT(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        int length = this.text.length();
        char cLA = LA(1);
        Token tokenMakeToken = null;
        if (cLA == '\"') {
            mSTRING(false);
        } else if (cLA == '(') {
            mTREE(false);
        } else if (cLA != '_') {
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
                    mID_ELEMENT(false);
                    break;
                case '[':
                    mAST_CONSTRUCTOR(false);
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
                            mID_ELEMENT(false);
                            break;
                        default:
                            if (LA(1) == '#' && LA(2) == '(') {
                                int length2 = this.text.length();
                                match('#');
                                this.text.setLength(length2);
                                mTREE(false);
                            } else if (LA(1) == '#' && LA(2) == '[') {
                                int length3 = this.text.length();
                                match('#');
                                this.text.setLength(length3);
                                mAST_CONSTRUCTOR(false);
                            } else if (LA(1) == '#' && _tokenSet_3.member(LA(2))) {
                                int length4 = this.text.length();
                                match('#');
                                this.text.setLength(length4);
                                boolean zMID_ELEMENT = mID_ELEMENT(true);
                                Token token = this._returnToken;
                                if (!zMID_ELEMENT) {
                                    String strMapTreeId = this.generator.mapTreeId(token.getText(), null);
                                    this.text.setLength(length);
                                    this.text.append(strMapTreeId);
                                }
                            } else if (LA(1) == '#' && LA(2) == '#') {
                                match("##");
                                String string = new StringBuffer().append(this.currentRule.getRuleName()).append("_AST").toString();
                                this.text.setLength(length);
                                this.text.append(string);
                            } else {
                                throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
                            }
                            break;
                    }
                    break;
            }
        } else {
            mID_ELEMENT(false);
        }
        if (z) {
            tokenMakeToken = makeToken(9);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        }
        this._returnToken = tokenMakeToken;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:110:0x0206  */
    /* JADX WARN: Code duplicated, block: B:146:0x02d4  */
    /* JADX WARN: Code duplicated, block: B:153:0x0302  */
    /* JADX WARN: Code duplicated, block: B:53:0x0111  */
    /* JADX WARN: Code duplicated, block: B:56:0x011c  */
    /* JADX WARN: Code duplicated, block: B:58:0x0125 A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:74:0x015a  */
    /* JADX WARN: Code duplicated, block: B:92:0x01c3  */
    protected final boolean mID_ELEMENT(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        ActionTransInfo actionTransInfo;
        Token tokenMakeToken;
        char cLA;
        int length = this.text.length();
        boolean z2 = true;
        mID(true);
        Token token = this._returnToken;
        BitSet bitSet = _tokenSet_4;
        if (bitSet.member(LA(1)) && _tokenSet_13.member(LA(2))) {
            int length2 = this.text.length();
            mWS(false);
            this.text.setLength(length2);
        } else if (!_tokenSet_13.member(LA(1))) {
            throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
        }
        char cLA2 = LA(1);
        if (cLA2 == '\t' || cLA2 == '\n' || cLA2 == '\r' || cLA2 == ' ' || cLA2 == '=') {
            String strMapTreeId = this.generator.mapTreeId(token.getText(), this.transInfo);
            this.text.setLength(length);
            this.text.append(strMapTreeId);
            if (!_tokenSet_15.member(LA(1)) && _tokenSet_16.member(LA(2)) && (actionTransInfo = this.transInfo) != null && actionTransInfo.refRuleRoot != null) {
                char cLA3 = LA(1);
                if (cLA3 == '\t' || cLA3 == '\n' || cLA3 == '\r' || cLA3 == ' ') {
                    mWS(false);
                } else if (cLA3 != '=') {
                    throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
                }
                mVAR_ASSIGN(false);
            } else if (!_tokenSet_17.member(LA(1))) {
                throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
            }
        } else {
            if (cLA2 == '[') {
                int i = 0;
                while (LA(1) == '[') {
                    match('[');
                    char cLA4 = LA(1);
                    if (cLA4 != '\t' || cLA4 == '\n' || cLA4 == '\r' || cLA4 == ' ') {
                        int length3 = this.text.length();
                        mWS(false);
                        this.text.setLength(length3);
                    } else if (cLA4 != '_' && cLA4 != '\"') {
                        if (cLA4 != '#' && cLA4 != '\'' && cLA4 != '(') {
                            switch (cLA4) {
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
                                    break;
                                default:
                                    switch (cLA4) {
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
                                        case '[':
                                            break;
                                        default:
                                            switch (cLA4) {
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
                                                    throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
                                            }
                                            while (LA(1) == '[') {
                                                match('[');
                                                char cLA5 = LA(1);
                                                if (cLA5 != '\t') {
                                                    int length4 = this.text.length();
                                                    mWS(false);
                                                    this.text.setLength(length4);
                                                } else {
                                                    int length5 = this.text.length();
                                                    mWS(false);
                                                    this.text.setLength(length5);
                                                }
                                                break;
                                            }
                                            break;
                                    }
                                    break;
                            }
                        }
                    }
                    mARG(false);
                    char cLA6 = LA(1);
                    if (cLA6 == '\t' || cLA6 == '\n' || cLA6 == '\r' || cLA6 == ' ') {
                        int length6 = this.text.length();
                        mWS(false);
                        this.text.setLength(length6);
                    } else if (cLA6 != ']') {
                        throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
                    }
                    match(']');
                    i++;
                }
                if (i < 1) {
                    throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
                }
            } else {
                if (cLA2 != ']') {
                    switch (cLA2) {
                        case '(':
                            match('(');
                            if (bitSet.member(LA(1)) && _tokenSet_14.member(LA(2)) && LA(3) >= 3 && LA(3) <= 255) {
                                int length7 = this.text.length();
                                mWS(false);
                                this.text.setLength(length7);
                            } else if (!_tokenSet_14.member(LA(1)) || LA(2) < 3 || LA(2) > 255) {
                                throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
                            }
                            char cLA7 = LA(1);
                            if (cLA7 != '\t' && cLA7 != '\n' && cLA7 != '\r' && cLA7 != ' ') {
                                if (cLA7 == '_' || cLA7 == '\"' || cLA7 == '#') {
                                    mARG(false);
                                    while (LA(1) == ',') {
                                        match(',');
                                        cLA = LA(1);
                                        if (cLA != '\t' || cLA == '\n' || cLA == '\r' || cLA == ' ') {
                                            int length8 = this.text.length();
                                            mWS(false);
                                            this.text.setLength(length8);
                                        } else if (cLA != '_' && cLA != '\"' && cLA != '#' && cLA != '\'' && cLA != '(') {
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
                                                    continue;
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
                                                        case '[':
                                                            continue;
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
                                                                    throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
                                                            }
                                                            break;
                                                    }
                                                    break;
                                            }
                                        }
                                        mARG(false);
                                    }
                                } else {
                                    switch (cLA7) {
                                        case '\'':
                                        case '(':
                                            mARG(false);
                                            while (LA(1) == ',') {
                                                match(',');
                                                cLA = LA(1);
                                                if (cLA != '\t') {
                                                    int length9 = this.text.length();
                                                    mWS(false);
                                                    this.text.setLength(length9);
                                                } else {
                                                    int length10 = this.text.length();
                                                    mWS(false);
                                                    this.text.setLength(length10);
                                                }
                                                mARG(false);
                                            }
                                            break;
                                        case ')':
                                            break;
                                        default:
                                            switch (cLA7) {
                                                default:
                                                    switch (cLA7) {
                                                        default:
                                                            switch (cLA7) {
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
                                                                    throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
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
                                                        case '[':
                                                            mARG(false);
                                                            while (LA(1) == ',') {
                                                                match(',');
                                                                cLA = LA(1);
                                                                if (cLA != '\t') {
                                                                    int length11 = this.text.length();
                                                                    mWS(false);
                                                                    this.text.setLength(length11);
                                                                } else {
                                                                    int length12 = this.text.length();
                                                                    mWS(false);
                                                                    this.text.setLength(length12);
                                                                }
                                                                mARG(false);
                                                            }
                                                            break;
                                                    }
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
                                                    mARG(false);
                                                    while (LA(1) == ',') {
                                                        match(',');
                                                        cLA = LA(1);
                                                        if (cLA != '\t') {
                                                            int length13 = this.text.length();
                                                            mWS(false);
                                                            this.text.setLength(length13);
                                                        } else {
                                                            int length14 = this.text.length();
                                                            mWS(false);
                                                            this.text.setLength(length14);
                                                        }
                                                        mARG(false);
                                                    }
                                                    break;
                                            }
                                            break;
                                    }
                                }
                            }
                            char cLA8 = LA(1);
                            if (cLA8 == '\t' || cLA8 == '\n' || cLA8 == '\r' || cLA8 == ' ') {
                                int length15 = this.text.length();
                                mWS(false);
                                this.text.setLength(length15);
                            } else if (cLA8 != ')') {
                                throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
                            }
                            match(')');
                            break;
                        case ')':
                        case '*':
                        case '+':
                        case ',':
                        case '-':
                        case '/':
                            break;
                        case '.':
                            match('.');
                            mID_ELEMENT(false);
                            break;
                        default:
                            throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
                    }
                }
                String strMapTreeId2 = this.generator.mapTreeId(token.getText(), this.transInfo);
                this.text.setLength(length);
                this.text.append(strMapTreeId2);
                if (!_tokenSet_15.member(LA(1))) {
                    if (!_tokenSet_17.member(LA(1))) {
                        throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
                    }
                } else if (!_tokenSet_17.member(LA(1))) {
                    throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
                }
            }
            z2 = false;
        }
        if (z) {
            tokenMakeToken = makeToken(12);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
        return z2;
    }

    protected final void mAST_CTOR_ELEMENT(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        if (LA(1) == '\"' && LA(2) >= 3 && LA(2) <= 255 && LA(3) >= 3 && LA(3) <= 255) {
            mSTRING(false);
        } else if (_tokenSet_18.member(LA(1)) && LA(2) >= 3 && LA(2) <= 255) {
            mTREE_ELEMENT(false);
        } else if (LA(1) >= '0' && LA(1) <= '9') {
            mINT(false);
        } else {
            throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
        }
        if (z) {
            tokenMakeToken = makeToken(11);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    protected final void mINT(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        int i = 0;
        while (LA(1) >= '0' && LA(1) <= '9') {
            mDIGIT(false);
            i++;
        }
        if (i < 1) {
            throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
        }
        if (z) {
            tokenMakeToken = makeToken(26);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    protected final void mARG(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        char cLA = LA(1);
        char c = 255;
        if (cLA == '\'') {
            mCHAR(false);
        } else {
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
                    mINT_OR_FLOAT(false);
                    break;
                default:
                    if (_tokenSet_18.member(LA(1)) && LA(2) >= 3 && LA(2) <= 255 && LA(3) >= 3 && LA(3) <= 255) {
                        mTREE_ELEMENT(false);
                    } else if (LA(1) == '\"' && LA(2) >= 3 && LA(2) <= 255 && LA(3) >= 3 && LA(3) <= 255) {
                        mSTRING(false);
                    } else {
                        throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
                    }
                    break;
            }
        }
        for (int i = 2; _tokenSet_19.member(LA(1)) && _tokenSet_20.member(LA(i)) && LA(3) >= 3 && LA(3) <= c; i = 2) {
            char cLA2 = LA(1);
            if (cLA2 == '\t' || cLA2 == '\n' || cLA2 == '\r' || cLA2 == ' ') {
                mWS(false);
            } else if (cLA2 != '-' && cLA2 != '/' && cLA2 != '*' && cLA2 != '+') {
                throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
            }
            char cLA3 = LA(1);
            if (cLA3 == '*') {
                match('*');
            } else if (cLA3 == '+') {
                match('+');
            } else if (cLA3 == '-') {
                match('-');
            } else if (cLA3 == '/') {
                match('/');
            } else {
                throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
            }
            char cLA4 = LA(1);
            if (cLA4 == '\t' || cLA4 == '\n' || cLA4 == '\r' || cLA4 == ' ') {
                mWS(false);
            } else if (cLA4 != '_' && cLA4 != '\"' && cLA4 != '#' && cLA4 != '\'' && cLA4 != '(') {
                switch (cLA4) {
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
                        continue;
                    default:
                        switch (cLA4) {
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
                            case '[':
                                continue;
                            default:
                                switch (cLA4) {
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
                                        throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
                                }
                                break;
                        }
                        break;
                }
            }
            mARG(false);
            c = 255;
        }
        if (z) {
            tokenMakeToken = makeToken(16);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    /* JADX WARN: Code duplicated, block: B:18:0x0043 A[FALL_THROUGH] */
    protected final void mTEXT_ARG_ELEMENT(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        char cLA = LA(1);
        if (cLA == '\"') {
            mSTRING(false);
        } else if (cLA == '$') {
            mTEXT_ITEM(false);
        } else if (cLA == '\'') {
            mCHAR(false);
        } else if (cLA == '+') {
            match('+');
        } else if (cLA == '_') {
            mTEXT_ARG_ID_ELEMENT(false);
        } else {
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
                    mINT_OR_FLOAT(false);
                    break;
                default:
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
                                    throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
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
                            mTEXT_ARG_ID_ELEMENT(false);
                            break;
                    }
                    break;
            }
        }
        if (z) {
            tokenMakeToken = makeToken(14);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    protected final void mTEXT_ARG_ID_ELEMENT(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        mID(true);
        Token token = this._returnToken;
        BitSet bitSet = _tokenSet_4;
        if (bitSet.member(LA(1)) && _tokenSet_21.member(LA(2))) {
            int length2 = this.text.length();
            mWS(false);
            this.text.setLength(length2);
        } else if (!_tokenSet_21.member(LA(1))) {
            throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
        }
        char cLA = LA(1);
        if (cLA != '\t' && cLA != '\n' && cLA != '\r' && cLA != ' ' && cLA != '\"' && cLA != '$') {
            if (cLA == '.') {
                match('.');
                mTEXT_ARG_ID_ELEMENT(false);
            } else if (cLA != ']' && cLA != '_' && cLA != '+' && cLA != ',') {
                switch (cLA) {
                    case '\'':
                    case ')':
                        break;
                    case '(':
                        match('(');
                        if (bitSet.member(LA(1)) && _tokenSet_22.member(LA(2)) && LA(3) >= 3 && LA(3) <= 255) {
                            int length3 = this.text.length();
                            mWS(false);
                            this.text.setLength(length3);
                        } else if (!_tokenSet_22.member(LA(1)) || LA(2) < 3 || LA(2) > 255) {
                            throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
                        }
                        while (_tokenSet_23.member(LA(1)) && LA(2) >= 3 && LA(2) <= 255 && LA(3) >= 3 && LA(3) <= 255) {
                            mTEXT_ARG(false);
                            while (LA(1) == ',') {
                                match(',');
                                mTEXT_ARG(false);
                            }
                        }
                        char cLA2 = LA(1);
                        if (cLA2 == '\t' || cLA2 == '\n' || cLA2 == '\r' || cLA2 == ' ') {
                            int length4 = this.text.length();
                            mWS(false);
                            this.text.setLength(length4);
                        } else if (cLA2 != ')') {
                            throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
                        }
                        match(')');
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
                                        break;
                                    case '[':
                                        int i = 0;
                                        while (LA(1) == '[') {
                                            match('[');
                                            if (_tokenSet_4.member(LA(1)) && _tokenSet_23.member(LA(2)) && LA(3) >= 3 && LA(3) <= 255) {
                                                int length5 = this.text.length();
                                                mWS(false);
                                                this.text.setLength(length5);
                                            } else if (!_tokenSet_23.member(LA(1)) || LA(2) < 3 || LA(2) > 255 || LA(3) < 3 || LA(3) > 255) {
                                                throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
                                            }
                                            mTEXT_ARG(false);
                                            char cLA3 = LA(1);
                                            if (cLA3 == '\t' || cLA3 == '\n' || cLA3 == '\r' || cLA3 == ' ') {
                                                int length6 = this.text.length();
                                                mWS(false);
                                                this.text.setLength(length6);
                                            } else if (cLA3 != ']') {
                                                throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
                                            }
                                            match(']');
                                            i++;
                                        }
                                        if (i < 1) {
                                            throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
                                        }
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
                                                break;
                                            default:
                                                throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
                                        }
                                        break;
                                }
                                break;
                        }
                        break;
                }
            }
        }
        if (z) {
            tokenMakeToken = makeToken(15);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    protected final void mINT_OR_FLOAT(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        int i = 0;
        while (LA(1) >= '0' && LA(1) <= '9' && _tokenSet_24.member(LA(2))) {
            mDIGIT(false);
            i++;
        }
        if (i < 1) {
            throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
        }
        if (LA(1) == 'L' && _tokenSet_25.member(LA(2))) {
            match('L');
        } else if (LA(1) == 'l' && _tokenSet_25.member(LA(2))) {
            match('l');
        } else if (LA(1) == '.') {
            match('.');
            while (LA(1) >= '0' && LA(1) <= '9' && _tokenSet_25.member(LA(2))) {
                mDIGIT(false);
            }
        } else if (!_tokenSet_25.member(LA(1))) {
            throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
        }
        if (z) {
            tokenMakeToken = makeToken(27);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    protected final void mSL_COMMENT(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        match("//");
        while (LA(1) != '\n' && LA(1) != '\r' && LA(1) >= 3 && LA(1) <= 255 && LA(2) >= 3 && LA(2) <= 255) {
            matchNot((char) 65535);
        }
        if (LA(1) == '\r' && LA(2) == '\n') {
            match("\r\n");
        } else if (LA(1) == '\n') {
            match('\n');
        } else if (LA(1) == '\r') {
            match('\r');
        } else {
            throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
        }
        newline();
        if (z) {
            tokenMakeToken = makeToken(20);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    protected final void mML_COMMENT(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        match(RefSpec.WILDCARD_SUFFIX);
        while (true) {
            if (LA(1) == '*' && LA(2) == '/') {
                break;
            }
            if (LA(1) == '\r' && LA(2) == '\n' && LA(3) >= 3 && LA(3) <= 255) {
                match('\r');
                match('\n');
                newline();
            } else if (LA(1) == '\r' && LA(2) >= 3 && LA(2) <= 255 && LA(3) >= 3 && LA(3) <= 255) {
                match('\r');
                newline();
            } else if (LA(1) == '\n' && LA(2) >= 3 && LA(2) <= 255 && LA(3) >= 3 && LA(3) <= 255) {
                match('\n');
                newline();
            } else if (LA(1) < 3 || LA(1) > 255 || LA(2) < 3 || LA(2) > 255 || LA(3) < 3 || LA(3) > 255) {
                break;
            } else {
                matchNot((char) 65535);
            }
        }
        match("*/");
        if (z) {
            tokenMakeToken = makeToken(21);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    protected final void mESC(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        match('\\');
        char cLA = LA(1);
        if (cLA == '\"') {
            match('\"');
        } else if (cLA == '\'') {
            match('\'');
        } else if (cLA == '\\') {
            match('\\');
        } else if (cLA == 'b') {
            match('b');
        } else if (cLA == 'f') {
            match('f');
        } else if (cLA == 'n') {
            match('n');
        } else if (cLA == 'r') {
            match(Advice.OffsetMapping.ForOrigin.Renderer.ForReturnTypeName.SYMBOL);
        } else if (cLA == 't') {
            match(Advice.OffsetMapping.ForOrigin.Renderer.ForTypeName.SYMBOL);
        } else {
            switch (cLA) {
                case '0':
                case '1':
                case '2':
                case '3':
                    matchRange(ExternalAnnotationProvider.NULLABLE, '3');
                    if (LA(1) >= '0' && LA(1) <= '9' && LA(2) >= 3 && LA(2) <= 255) {
                        mDIGIT(false);
                        if (LA(1) >= '0' && LA(1) <= '9' && LA(2) >= 3 && LA(2) <= 255) {
                            mDIGIT(false);
                        } else if (LA(1) < 3 || LA(1) > 255) {
                            throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
                        }
                    } else if (LA(1) < 3 || LA(1) > 255) {
                        throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
                    }
                    break;
                case '4':
                case '5':
                case '6':
                case '7':
                    matchRange('4', '7');
                    if (LA(1) >= '0' && LA(1) <= '9' && LA(2) >= 3 && LA(2) <= 255) {
                        mDIGIT(false);
                    } else if (LA(1) < 3 || LA(1) > 255) {
                        throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
                    }
                    break;
                default:
                    throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
            }
        }
        if (z) {
            tokenMakeToken = makeToken(24);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    protected final void mDIGIT(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        matchRange(ExternalAnnotationProvider.NULLABLE, '9');
        if (z) {
            tokenMakeToken = makeToken(25);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    private static final long[] mk_tokenSet_0() {
        long[] jArr = new long[8];
        jArr[0] = -103079215112L;
        for (int i = 1; i <= 3; i++) {
            jArr[i] = -1;
        }
        return jArr;
    }

    private static final long[] mk_tokenSet_1() {
        long[] jArr = new long[8];
        jArr[0] = -145135534866440L;
        for (int i = 1; i <= 3; i++) {
            jArr[i] = -1;
        }
        return jArr;
    }

    private static final long[] mk_tokenSet_2() {
        long[] jArr = new long[8];
        jArr[0] = -141407503262728L;
        for (int i = 1; i <= 3; i++) {
            jArr[i] = -1;
        }
        return jArr;
    }

    private static final long[] mk_tokenSet_3() {
        return new long[]{0, 576460745995190270L, 0, 0, 0};
    }

    private static final long[] mk_tokenSet_4() {
        return new long[]{4294977024L, 0, 0, 0, 0};
    }

    private static final long[] mk_tokenSet_5() {
        return new long[]{1103806604800L, 0, 0, 0, 0};
    }

    private static final long[] mk_tokenSet_6() {
        return new long[]{287959436729787904L, 576460745995190270L, 0, 0, 0};
    }

    private static final long[] mk_tokenSet_7() {
        long[] jArr = new long[8];
        jArr[0] = -17179869192L;
        jArr[1] = -268435457;
        for (int i = 2; i <= 3; i++) {
            jArr[i] = -1;
        }
        return jArr;
    }

    private static final long[] mk_tokenSet_8() {
        long[] jArr = new long[8];
        jArr[0] = -549755813896L;
        jArr[1] = -268435457;
        for (int i = 2; i <= 3; i++) {
            jArr[i] = -1;
        }
        return jArr;
    }

    private static final long[] mk_tokenSet_9() {
        return new long[]{287948901175001088L, 576460745995190270L, 0, 0, 0};
    }

    private static final long[] mk_tokenSet_10() {
        return new long[]{287950056521213440L, 576460746129407998L, 0, 0, 0};
    }

    private static final long[] mk_tokenSet_11() {
        return new long[]{287958332923183104L, 576460745995190270L, 0, 0, 0};
    }

    private static final long[] mk_tokenSet_12() {
        return new long[]{287978128427460096L, 576460746532061182L, 0, 0, 0};
    }

    private static final long[] mk_tokenSet_13() {
        return new long[]{2306123388973753856L, 671088640, 0, 0, 0};
    }

    private static final long[] mk_tokenSet_14() {
        return new long[]{287952805300282880L, 576460746129407998L, 0, 0, 0};
    }

    private static final long[] mk_tokenSet_15() {
        return new long[]{2305843013508670976L, 0, 0, 0, 0};
    }

    private static final long[] mk_tokenSet_16() {
        return new long[]{2306051920717948416L, TagBits.HasTypeVariable, 0, 0, 0};
    }

    private static final long[] mk_tokenSet_17() {
        return new long[]{208911504254464L, TagBits.HasTypeVariable, 0, 0, 0};
    }

    private static final long[] mk_tokenSet_18() {
        return new long[]{1151051235328L, 576460746129407998L, 0, 0, 0};
    }

    private static final long[] mk_tokenSet_19() {
        return new long[]{189120294954496L, 0, 0, 0, 0};
    }

    private static final long[] mk_tokenSet_20() {
        return new long[]{288139722277004800L, 576460746129407998L, 0, 0, 0};
    }

    private static final long[] mk_tokenSet_21() {
        return new long[]{288049596683265536L, 576460746666278910L, 0, 0, 0};
    }

    private static final long[] mk_tokenSet_22() {
        return new long[]{287960536241415680L, 576460745995190270L, 0, 0, 0};
    }

    private static final long[] mk_tokenSet_23() {
        return new long[]{287958337218160128L, 576460745995190270L, 0, 0, 0};
    }

    private static final long[] mk_tokenSet_24() {
        return new long[]{288228817078593024L, 576460746532061182L, 0, 0, 0};
    }

    private static final long[] mk_tokenSet_25() {
        return new long[]{288158448334415360L, 576460746532061182L, 0, 0, 0};
    }
}
