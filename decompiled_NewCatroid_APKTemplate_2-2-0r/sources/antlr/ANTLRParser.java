package antlr;

import antlr.collections.impl.BitSet;

/* JADX INFO: loaded from: classes.dex */
public class ANTLRParser extends LLkParser implements ANTLRTokenTypes {
    private static final boolean DEBUG_PARSER = false;
    Tool antlrTool;
    ANTLRGrammarParseBehavior behavior;
    protected int blockNesting;
    public static final String[] _tokenNames = {"<0>", "EOF", "<2>", "NULL_TREE_LOOKAHEAD", "\"tokens\"", "\"header\"", "STRING_LITERAL", "ACTION", "DOC_COMMENT", "\"lexclass\"", "\"class\"", "\"extends\"", "\"Lexer\"", "\"TreeParser\"", "OPTIONS", "ASSIGN", "SEMI", "RCURLY", "\"charVocabulary\"", "CHAR_LITERAL", "INT", "OR", "RANGE", "TOKENS", "TOKEN_REF", "OPEN_ELEMENT_OPTION", "CLOSE_ELEMENT_OPTION", "LPAREN", "RPAREN", "\"Parser\"", "\"protected\"", "\"public\"", "\"private\"", "BANG", "ARG_ACTION", "\"returns\"", "COLON", "\"throws\"", "COMMA", "\"exception\"", "\"catch\"", "RULE_REF", "NOT_OP", "SEMPRED", "TREE_BEGIN", "QUESTION", "STAR", "PLUS", "IMPLIES", "CARET", "WILDCARD", "\"options\"", "WS", "COMMENT", "SL_COMMENT", "ML_COMMENT", "ESC", "DIGIT", "XDIGIT", "NESTED_ARG_ACTION", "NESTED_ACTION", "WS_LOOP", "INTERNAL_RULE_REF", "WS_OPT"};
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

    public ANTLRParser(TokenBuffer tokenBuffer, ANTLRGrammarParseBehavior aNTLRGrammarParseBehavior, Tool tool) {
        super(tokenBuffer, 1);
        this.blockNesting = -1;
        this.tokenNames = _tokenNames;
        this.behavior = aNTLRGrammarParseBehavior;
        this.antlrTool = tool;
    }

    @Override // antlr.Parser
    public void reportError(String str) {
        this.antlrTool.error(str, getFilename(), -1, -1);
    }

    @Override // antlr.Parser
    public void reportError(RecognitionException recognitionException) {
        reportError(recognitionException, recognitionException.getErrorMessage());
    }

    public void reportError(RecognitionException recognitionException, String str) {
        this.antlrTool.error(str, recognitionException.getFilename(), recognitionException.getLine(), recognitionException.getColumn());
    }

    @Override // antlr.Parser
    public void reportWarning(String str) {
        this.antlrTool.warning(str, getFilename(), -1, -1);
    }

    private boolean lastInRule() throws TokenStreamException {
        if (this.blockNesting == 0) {
            return LA(1) == 16 || LA(1) == 39 || LA(1) == 21;
        }
        return false;
    }

    private void checkForMissingEndRule(Token token) {
        if (token.getColumn() == 1) {
            this.antlrTool.warning("did you forget to terminate previous rule?", getFilename(), token.getLine(), token.getColumn());
        }
    }

    protected ANTLRParser(TokenBuffer tokenBuffer, int i) {
        super(tokenBuffer, i);
        this.blockNesting = -1;
        this.tokenNames = _tokenNames;
    }

    public ANTLRParser(TokenBuffer tokenBuffer) {
        this(tokenBuffer, 2);
    }

    protected ANTLRParser(TokenStream tokenStream, int i) {
        super(tokenStream, i);
        this.blockNesting = -1;
        this.tokenNames = _tokenNames;
    }

    public ANTLRParser(TokenStream tokenStream) {
        this(tokenStream, 2);
    }

    public ANTLRParser(ParserSharedInputState parserSharedInputState) {
        super(parserSharedInputState, 2);
        this.blockNesting = -1;
        this.tokenNames = _tokenNames;
    }

    public final void grammar() throws RecognitionException, TokenStreamException {
        Token tokenLT = null;
        while (LA(1) == 5) {
            try {
                if (this.inputState.guessing == 0) {
                    tokenLT = null;
                }
                match(5);
                int iLA = LA(1);
                if (iLA == 6) {
                    tokenLT = LT(1);
                    match(6);
                } else if (iLA != 7) {
                    throw new NoViableAltException(LT(1), getFilename());
                }
                Token tokenLT2 = LT(1);
                match(7);
                if (this.inputState.guessing == 0) {
                    this.behavior.refHeaderAction(tokenLT, tokenLT2);
                }
            } catch (RecognitionException e) {
                if (this.inputState.guessing == 0) {
                    reportError(e, new StringBuffer("rule grammar trapped:\n").append(e.toString()).toString());
                    consumeUntil(1);
                    return;
                }
                throw e;
            }
        }
        int iLA2 = LA(1);
        if (iLA2 != 1) {
            if (iLA2 == 14) {
                fileOptionsSpec();
            } else {
                switch (iLA2) {
                    case 7:
                    case 8:
                    case 9:
                    case 10:
                        break;
                    default:
                        throw new NoViableAltException(LT(1), getFilename());
                }
            }
        }
        while (LA(1) >= 7 && LA(1) <= 10) {
            classDef();
        }
        match(1);
    }

    public final void fileOptionsSpec() throws RecognitionException, TokenStreamException {
        match(14);
        while (true) {
            if (LA(1) == 24 || LA(1) == 41) {
                Token tokenId = id();
                match(15);
                Token tokenOptionValue = optionValue();
                if (this.inputState.guessing == 0) {
                    this.behavior.setFileOption(tokenId, tokenOptionValue, getInputState().filename);
                }
                match(16);
            } else {
                match(17);
                return;
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:29:0x0077 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:30:0x0079 A[Catch: RecognitionException -> 0x009b, TryCatch #0 {RecognitionException -> 0x009b, blocks: (B:27:0x0071, B:30:0x0079, B:31:0x0088, B:32:0x0095, B:33:0x0096), top: B:89:0x0071 }] */
    /* JADX WARN: Code duplicated, block: B:31:0x0088 A[Catch: RecognitionException -> 0x009b, TryCatch #0 {RecognitionException -> 0x009b, blocks: (B:27:0x0071, B:30:0x0079, B:31:0x0088, B:32:0x0095, B:33:0x0096), top: B:89:0x0071 }] */
    /* JADX WARN: Code duplicated, block: B:33:0x0096 A[Catch: RecognitionException -> 0x009b, TRY_LEAVE, TryCatch #0 {RecognitionException -> 0x009b, blocks: (B:27:0x0071, B:30:0x0079, B:31:0x0088, B:32:0x0095, B:33:0x0096), top: B:89:0x0071 }] */
    /* JADX WARN: Code duplicated, block: B:38:0x00a8 A[Catch: RecognitionException -> 0x0137, TryCatch #2 {RecognitionException -> 0x0137, blocks: (B:3:0x0008, B:4:0x000c, B:5:0x000f, B:67:0x012b, B:68:0x0136, B:6:0x0013, B:8:0x0021, B:9:0x0026, B:10:0x002a, B:11:0x002d, B:65:0x011f, B:66:0x012a, B:12:0x0031, B:14:0x003e, B:16:0x0044, B:18:0x0051, B:38:0x00a8, B:60:0x0101, B:62:0x010a, B:39:0x00ac, B:41:0x00b2, B:43:0x00b8, B:45:0x00be, B:49:0x00da, B:52:0x00e8, B:53:0x00ec, B:55:0x00f2, B:57:0x00f8, B:59:0x00fe, B:63:0x0111, B:64:0x011e, B:20:0x0057, B:22:0x005d, B:26:0x0066, B:36:0x009c), top: B:92:0x0008 }] */
    /* JADX WARN: Code duplicated, block: B:39:0x00ac A[Catch: RecognitionException -> 0x0137, TryCatch #2 {RecognitionException -> 0x0137, blocks: (B:3:0x0008, B:4:0x000c, B:5:0x000f, B:67:0x012b, B:68:0x0136, B:6:0x0013, B:8:0x0021, B:9:0x0026, B:10:0x002a, B:11:0x002d, B:65:0x011f, B:66:0x012a, B:12:0x0031, B:14:0x003e, B:16:0x0044, B:18:0x0051, B:38:0x00a8, B:60:0x0101, B:62:0x010a, B:39:0x00ac, B:41:0x00b2, B:43:0x00b8, B:45:0x00be, B:49:0x00da, B:52:0x00e8, B:53:0x00ec, B:55:0x00f2, B:57:0x00f8, B:59:0x00fe, B:63:0x0111, B:64:0x011e, B:20:0x0057, B:22:0x005d, B:26:0x0066, B:36:0x009c), top: B:92:0x0008 }] */
    /* JADX WARN: Code duplicated, block: B:50:0x00e5  */
    /* JADX WARN: Code duplicated, block: B:52:0x00e8 A[Catch: RecognitionException -> 0x0137, TryCatch #2 {RecognitionException -> 0x0137, blocks: (B:3:0x0008, B:4:0x000c, B:5:0x000f, B:67:0x012b, B:68:0x0136, B:6:0x0013, B:8:0x0021, B:9:0x0026, B:10:0x002a, B:11:0x002d, B:65:0x011f, B:66:0x012a, B:12:0x0031, B:14:0x003e, B:16:0x0044, B:18:0x0051, B:38:0x00a8, B:60:0x0101, B:62:0x010a, B:39:0x00ac, B:41:0x00b2, B:43:0x00b8, B:45:0x00be, B:49:0x00da, B:52:0x00e8, B:53:0x00ec, B:55:0x00f2, B:57:0x00f8, B:59:0x00fe, B:63:0x0111, B:64:0x011e, B:20:0x0057, B:22:0x005d, B:26:0x0066, B:36:0x009c), top: B:92:0x0008 }] */
    /* JADX WARN: Code duplicated, block: B:53:0x00ec A[Catch: RecognitionException -> 0x0137, TryCatch #2 {RecognitionException -> 0x0137, blocks: (B:3:0x0008, B:4:0x000c, B:5:0x000f, B:67:0x012b, B:68:0x0136, B:6:0x0013, B:8:0x0021, B:9:0x0026, B:10:0x002a, B:11:0x002d, B:65:0x011f, B:66:0x012a, B:12:0x0031, B:14:0x003e, B:16:0x0044, B:18:0x0051, B:38:0x00a8, B:60:0x0101, B:62:0x010a, B:39:0x00ac, B:41:0x00b2, B:43:0x00b8, B:45:0x00be, B:49:0x00da, B:52:0x00e8, B:53:0x00ec, B:55:0x00f2, B:57:0x00f8, B:59:0x00fe, B:63:0x0111, B:64:0x011e, B:20:0x0057, B:22:0x005d, B:26:0x0066, B:36:0x009c), top: B:92:0x0008 }] */
    /* JADX WARN: Code duplicated, block: B:62:0x010a A[Catch: RecognitionException -> 0x0137, TryCatch #2 {RecognitionException -> 0x0137, blocks: (B:3:0x0008, B:4:0x000c, B:5:0x000f, B:67:0x012b, B:68:0x0136, B:6:0x0013, B:8:0x0021, B:9:0x0026, B:10:0x002a, B:11:0x002d, B:65:0x011f, B:66:0x012a, B:12:0x0031, B:14:0x003e, B:16:0x0044, B:18:0x0051, B:38:0x00a8, B:60:0x0101, B:62:0x010a, B:39:0x00ac, B:41:0x00b2, B:43:0x00b8, B:45:0x00be, B:49:0x00da, B:52:0x00e8, B:53:0x00ec, B:55:0x00f2, B:57:0x00f8, B:59:0x00fe, B:63:0x0111, B:64:0x011e, B:20:0x0057, B:22:0x005d, B:26:0x0066, B:36:0x009c), top: B:92:0x0008 }] */
    /* JADX WARN: Code duplicated, block: B:87:0x0197 A[ORIG_RETURN, RETURN] */
    public final void classDef() throws RecognitionException, TokenStreamException {
        String text;
        boolean z;
        int iLA;
        boolean z2;
        try {
            switch (LA(1)) {
                case 7:
                    Token tokenLT = LT(1);
                    match(7);
                    if (this.inputState.guessing == 0) {
                        this.behavior.refPreambleAction(tokenLT);
                    }
                    break;
                case 8:
                case 9:
                case 10:
                    break;
                default:
                    throw new NoViableAltException(LT(1), getFilename());
            }
            switch (LA(1)) {
                case 8:
                    Token tokenLT2 = LT(1);
                    match(8);
                    text = this.inputState.guessing == 0 ? tokenLT2.getText() : null;
                    if ((LA(1) != 9 || LA(1) == 10) && (LA(2) == 24 || LA(2) == 41)) {
                        int iMark = mark();
                        this.inputState.guessing++;
                        try {
                            iLA = LA(1);
                            if (iLA == 9) {
                                match(9);
                            } else if (iLA == 10) {
                                match(10);
                                id();
                                match(11);
                                match(12);
                            } else {
                                throw new NoViableAltException(LT(1), getFilename());
                            }
                            z = true;
                        } catch (RecognitionException unused) {
                            z = false;
                        }
                        rewind(iMark);
                        this.inputState.guessing--;
                    } else {
                        z = false;
                    }
                    if (z) {
                        lexerSpec(text);
                    } else {
                        if (LA(1) == 10 || !(LA(2) == 24 || LA(2) == 41)) {
                            z2 = false;
                        } else {
                            int iMark2 = mark();
                            this.inputState.guessing++;
                            try {
                                match(10);
                                id();
                                match(11);
                                match(13);
                                z2 = true;
                            } catch (RecognitionException unused2) {
                                z2 = false;
                            }
                            rewind(iMark2);
                            this.inputState.guessing--;
                        }
                        if (z2) {
                            treeParserSpec(text);
                        } else if (LA(1) != 10 && (LA(2) == 24 || LA(2) == 41)) {
                            parserSpec(text);
                        } else {
                            throw new NoViableAltException(LT(1), getFilename());
                        }
                    }
                    rules();
                    if (this.inputState.guessing == 0) {
                        this.behavior.endGrammar();
                        return;
                    }
                    return;
                case 9:
                case 10:
                    if (LA(1) != 9) {
                        int iMark3 = mark();
                        this.inputState.guessing++;
                        iLA = LA(1);
                        if (iLA == 9) {
                            match(9);
                        } else if (iLA == 10) {
                            match(10);
                            id();
                            match(11);
                            match(12);
                        } else {
                            throw new NoViableAltException(LT(1), getFilename());
                        }
                        z = true;
                        rewind(iMark3);
                        this.inputState.guessing--;
                    } else {
                        int iMark4 = mark();
                        this.inputState.guessing++;
                        iLA = LA(1);
                        if (iLA == 9) {
                            match(9);
                        } else if (iLA == 10) {
                            match(10);
                            id();
                            match(11);
                            match(12);
                        } else {
                            throw new NoViableAltException(LT(1), getFilename());
                        }
                        z = true;
                        rewind(iMark4);
                        this.inputState.guessing--;
                    }
                    if (z) {
                        lexerSpec(text);
                    } else {
                        if (LA(1) == 10) {
                            z2 = false;
                        } else {
                            z2 = false;
                        }
                        if (z2) {
                            treeParserSpec(text);
                        } else {
                            if (LA(1) != 10) {
                                break;
                            }
                            throw new NoViableAltException(LT(1), getFilename());
                        }
                    }
                    rules();
                    if (this.inputState.guessing == 0) {
                        this.behavior.endGrammar();
                        return;
                    }
                    return;
                default:
                    throw new NoViableAltException(LT(1), getFilename());
            }
        } catch (RecognitionException e) {
            if (this.inputState.guessing == 0) {
                if ((e instanceof NoViableAltException) && ((NoViableAltException) e).token.getType() == 8) {
                    reportError(e, "JAVADOC comments may only prefix rules and grammars");
                } else {
                    reportError(e, new StringBuffer("rule classDef trapped:\n").append(e.toString()).toString());
                }
                this.behavior.abortGrammar();
                boolean z3 = true;
                while (z3) {
                    consume();
                    int iLA2 = LA(1);
                    if (iLA2 == 1 || iLA2 == 9 || iLA2 == 10) {
                        z3 = false;
                    }
                }
                return;
            }
            throw e;
        }
    }

    public final Token id() throws RecognitionException, TokenStreamException {
        int iLA = LA(1);
        if (iLA == 24) {
            Token tokenLT = LT(1);
            match(24);
            if (this.inputState.guessing == 0) {
                return tokenLT;
            }
        } else if (iLA == 41) {
            Token tokenLT2 = LT(1);
            match(41);
            if (this.inputState.guessing == 0) {
                return tokenLT2;
            }
        } else {
            throw new NoViableAltException(LT(1), getFilename());
        }
        return null;
    }

    public final void lexerSpec(String str) throws RecognitionException, TokenStreamException {
        Token tokenId;
        int iLA = LA(1);
        String strSuperClass = null;
        if (iLA == 9) {
            Token tokenLT = LT(1);
            match(9);
            Token tokenId2 = id();
            if (this.inputState.guessing == 0) {
                this.antlrTool.warning("lexclass' is deprecated; use 'class X extends Lexer'", getFilename(), tokenLT.getLine(), tokenLT.getColumn());
            }
            tokenId = tokenId2;
        } else if (iLA == 10) {
            match(10);
            tokenId = id();
            match(11);
            match(12);
            int iLA2 = LA(1);
            if (iLA2 != 16) {
                if (iLA2 == 27) {
                    strSuperClass = superClass();
                } else {
                    throw new NoViableAltException(LT(1), getFilename());
                }
            }
        } else {
            throw new NoViableAltException(LT(1), getFilename());
        }
        if (this.inputState.guessing == 0) {
            this.behavior.startLexer(getFilename(), tokenId, strSuperClass, str);
        }
        match(16);
        int iLA3 = LA(1);
        if (iLA3 != 7 && iLA3 != 8) {
            if (iLA3 == 14) {
                lexerOptionsSpec();
            } else if (iLA3 != 41 && iLA3 != 23 && iLA3 != 24) {
                switch (iLA3) {
                    case 30:
                    case 31:
                    case 32:
                        break;
                    default:
                        throw new NoViableAltException(LT(1), getFilename());
                }
            }
        }
        if (this.inputState.guessing == 0) {
            this.behavior.endOptions();
        }
        int iLA4 = LA(1);
        if (iLA4 != 7 && iLA4 != 8) {
            if (iLA4 == 23) {
                tokensSpec();
            } else if (iLA4 != 24 && iLA4 != 41) {
                switch (iLA4) {
                    case 30:
                    case 31:
                    case 32:
                        break;
                    default:
                        throw new NoViableAltException(LT(1), getFilename());
                }
            }
        }
        int iLA5 = LA(1);
        if (iLA5 == 7) {
            Token tokenLT2 = LT(1);
            match(7);
            if (this.inputState.guessing == 0) {
                this.behavior.refMemberAction(tokenLT2);
                return;
            }
            return;
        }
        if (iLA5 == 8 || iLA5 == 24 || iLA5 == 41) {
            return;
        }
        switch (iLA5) {
            case 30:
            case 31:
            case 32:
                return;
            default:
                throw new NoViableAltException(LT(1), getFilename());
        }
    }

    public final void treeParserSpec(String str) throws RecognitionException, TokenStreamException {
        String strSuperClass;
        match(10);
        Token tokenId = id();
        match(11);
        match(13);
        int iLA = LA(1);
        if (iLA == 16) {
            strSuperClass = null;
        } else if (iLA == 27) {
            strSuperClass = superClass();
        } else {
            throw new NoViableAltException(LT(1), getFilename());
        }
        if (this.inputState.guessing == 0) {
            this.behavior.startTreeWalker(getFilename(), tokenId, strSuperClass, str);
        }
        match(16);
        int iLA2 = LA(1);
        if (iLA2 != 7 && iLA2 != 8) {
            if (iLA2 == 14) {
                treeParserOptionsSpec();
            } else if (iLA2 != 41 && iLA2 != 23 && iLA2 != 24) {
                switch (iLA2) {
                    case 30:
                    case 31:
                    case 32:
                        break;
                    default:
                        throw new NoViableAltException(LT(1), getFilename());
                }
            }
        }
        if (this.inputState.guessing == 0) {
            this.behavior.endOptions();
        }
        int iLA3 = LA(1);
        if (iLA3 != 7 && iLA3 != 8) {
            if (iLA3 == 23) {
                tokensSpec();
            } else if (iLA3 != 24 && iLA3 != 41) {
                switch (iLA3) {
                    case 30:
                    case 31:
                    case 32:
                        break;
                    default:
                        throw new NoViableAltException(LT(1), getFilename());
                }
            }
        }
        int iLA4 = LA(1);
        if (iLA4 == 7) {
            Token tokenLT = LT(1);
            match(7);
            if (this.inputState.guessing == 0) {
                this.behavior.refMemberAction(tokenLT);
                return;
            }
            return;
        }
        if (iLA4 == 8 || iLA4 == 24 || iLA4 == 41) {
            return;
        }
        switch (iLA4) {
            case 30:
            case 31:
            case 32:
                return;
            default:
                throw new NoViableAltException(LT(1), getFilename());
        }
    }

    /* JADX WARN: Code duplicated, block: B:20:0x006b  */
    /* JADX WARN: Code duplicated, block: B:26:0x008c  */
    /* JADX WARN: Code duplicated, block: B:30:0x0095  */
    /* JADX WARN: Code duplicated, block: B:32:0x00a3  */
    /* JADX WARN: Code duplicated, block: B:35:0x00ac  */
    /* JADX WARN: Code duplicated, block: B:40:0x00bb  */
    /* JADX WARN: Code duplicated, block: B:43:0x00c2  */
    /* JADX WARN: Code duplicated, block: B:45:0x00d0  */
    /* JADX WARN: Code duplicated, block: B:48:0x00d9  */
    /* JADX WARN: Code duplicated, block: B:54:0x00f0  */
    /* JADX WARN: Code duplicated, block: B:56:0x00fd  */
    /* JADX WARN: Code duplicated, block: B:62:? A[RETURN, SYNTHETIC] */
    public final void parserSpec(String str) throws RecognitionException, TokenStreamException {
        String strSuperClass;
        int iLA;
        int iLA2;
        int iLA3;
        Token tokenLT;
        match(10);
        Token tokenId = id();
        int iLA4 = LA(1);
        if (iLA4 == 11) {
            match(11);
            match(29);
            int iLA5 = LA(1);
            if (iLA5 != 16) {
                if (iLA5 == 27) {
                    strSuperClass = superClass();
                } else {
                    throw new NoViableAltException(LT(1), getFilename());
                }
            }
            if (this.inputState.guessing == 0) {
                this.behavior.startParser(getFilename(), tokenId, strSuperClass, str);
            }
            match(16);
            iLA = LA(1);
            if (iLA != 7 && iLA != 8) {
                if (iLA != 14) {
                    parserOptionsSpec();
                } else if (iLA != 41 && iLA != 23 && iLA != 24) {
                    switch (iLA) {
                        case 30:
                        case 31:
                        case 32:
                            break;
                        default:
                            throw new NoViableAltException(LT(1), getFilename());
                    }
                }
            }
            if (this.inputState.guessing == 0) {
                this.behavior.endOptions();
            }
            iLA2 = LA(1);
            if (iLA2 != 7 && iLA2 != 8) {
                if (iLA2 != 23) {
                    tokensSpec();
                } else if (iLA2 != 24 && iLA2 != 41) {
                    switch (iLA2) {
                        case 30:
                        case 31:
                        case 32:
                            break;
                        default:
                            throw new NoViableAltException(LT(1), getFilename());
                    }
                }
            }
            iLA3 = LA(1);
            if (iLA3 == 7) {
                tokenLT = LT(1);
                match(7);
                if (this.inputState.guessing == 0) {
                    this.behavior.refMemberAction(tokenLT);
                }
            }
            if (iLA3 != 8 || iLA3 == 24 || iLA3 == 41) {
                return;
            }
            switch (iLA3) {
                case 30:
                case 31:
                case 32:
                    return;
                default:
                    throw new NoViableAltException(LT(1), getFilename());
            }
        }
        if (iLA4 == 16) {
            if (this.inputState.guessing == 0) {
                this.antlrTool.warning("use 'class X extends Parser'", getFilename(), tokenId.getLine(), tokenId.getColumn());
            }
        } else {
            throw new NoViableAltException(LT(1), getFilename());
        }
        strSuperClass = null;
        if (this.inputState.guessing == 0) {
            this.behavior.startParser(getFilename(), tokenId, strSuperClass, str);
        }
        match(16);
        iLA = LA(1);
        if (iLA != 7) {
            if (iLA != 14) {
                parserOptionsSpec();
            } else if (iLA != 41) {
                switch (iLA) {
                    case 30:
                    case 31:
                    case 32:
                        break;
                    default:
                        throw new NoViableAltException(LT(1), getFilename());
                }
            }
        }
        if (this.inputState.guessing == 0) {
            this.behavior.endOptions();
        }
        iLA2 = LA(1);
        if (iLA2 != 7) {
            if (iLA2 != 23) {
                tokensSpec();
            } else if (iLA2 != 24) {
                switch (iLA2) {
                    case 30:
                    case 31:
                    case 32:
                        break;
                    default:
                        throw new NoViableAltException(LT(1), getFilename());
                }
            }
        }
        iLA3 = LA(1);
        if (iLA3 == 7) {
            if (iLA3 != 8) {
                return;
            } else {
                return;
            }
        }
        tokenLT = LT(1);
        match(7);
        if (this.inputState.guessing == 0) {
            this.behavior.refMemberAction(tokenLT);
        }
    }

    public final void rules() throws RecognitionException, TokenStreamException {
        int i = 0;
        while (_tokenSet_0.member(LA(1)) && _tokenSet_1.member(LA(2))) {
            rule();
            i++;
        }
        if (i < 1) {
            throw new NoViableAltException(LT(1), getFilename());
        }
    }

    public final Token optionValue() throws RecognitionException, TokenStreamException {
        int iLA = LA(1);
        if (iLA == 6) {
            Token tokenLT = LT(1);
            match(6);
            if (this.inputState.guessing == 0) {
                return tokenLT;
            }
        } else {
            if (iLA == 24 || iLA == 41) {
                return qualifiedID();
            }
            if (iLA == 19) {
                Token tokenLT2 = LT(1);
                match(19);
                if (this.inputState.guessing == 0) {
                    return tokenLT2;
                }
            } else if (iLA == 20) {
                Token tokenLT3 = LT(1);
                match(20);
                if (this.inputState.guessing == 0) {
                    return tokenLT3;
                }
            } else {
                throw new NoViableAltException(LT(1), getFilename());
            }
        }
        return null;
    }

    public final void parserOptionsSpec() throws RecognitionException, TokenStreamException {
        match(14);
        while (true) {
            if (LA(1) == 24 || LA(1) == 41) {
                Token tokenId = id();
                match(15);
                Token tokenOptionValue = optionValue();
                if (this.inputState.guessing == 0) {
                    this.behavior.setGrammarOption(tokenId, tokenOptionValue);
                }
                match(16);
            } else {
                match(17);
                return;
            }
        }
    }

    public final void treeParserOptionsSpec() throws RecognitionException, TokenStreamException {
        match(14);
        while (true) {
            if (LA(1) == 24 || LA(1) == 41) {
                Token tokenId = id();
                match(15);
                Token tokenOptionValue = optionValue();
                if (this.inputState.guessing == 0) {
                    this.behavior.setGrammarOption(tokenId, tokenOptionValue);
                }
                match(16);
            } else {
                match(17);
                return;
            }
        }
    }

    public final void lexerOptionsSpec() throws RecognitionException, TokenStreamException {
        match(14);
        while (true) {
            int iLA = LA(1);
            if (iLA == 18) {
                match(18);
                match(15);
                BitSet bitSetCharSet = charSet();
                match(16);
                if (this.inputState.guessing == 0) {
                    this.behavior.setCharVocabulary(bitSetCharSet);
                }
            } else if (iLA == 24 || iLA == 41) {
                Token tokenId = id();
                match(15);
                Token tokenOptionValue = optionValue();
                if (this.inputState.guessing == 0) {
                    this.behavior.setGrammarOption(tokenId, tokenOptionValue);
                }
                match(16);
            } else {
                match(17);
                return;
            }
        }
    }

    public final BitSet charSet() throws RecognitionException, TokenStreamException {
        BitSet blockElement = setBlockElement();
        while (LA(1) == 21) {
            match(21);
            BitSet blockElement2 = setBlockElement();
            if (this.inputState.guessing == 0) {
                blockElement.orInPlace(blockElement2);
            }
        }
        return blockElement;
    }

    public final void subruleOptionsSpec() throws RecognitionException, TokenStreamException {
        match(14);
        while (true) {
            if (LA(1) == 24 || LA(1) == 41) {
                Token tokenId = id();
                match(15);
                Token tokenOptionValue = optionValue();
                if (this.inputState.guessing == 0) {
                    this.behavior.setSubruleOption(tokenId, tokenOptionValue);
                }
                match(16);
            } else {
                match(17);
                return;
            }
        }
    }

    public final Token qualifiedID() throws RecognitionException, TokenStreamException {
        StringBuffer stringBuffer = new StringBuffer(30);
        Token tokenId = id();
        if (this.inputState.guessing == 0) {
            stringBuffer.append(tokenId.getText());
        }
        while (LA(1) == 50) {
            match(50);
            tokenId = id();
            if (this.inputState.guessing == 0) {
                stringBuffer.append('.');
                stringBuffer.append(tokenId.getText());
            }
        }
        if (this.inputState.guessing != 0) {
            return null;
        }
        CommonToken commonToken = new CommonToken(24, stringBuffer.toString());
        commonToken.setLine(tokenId.getLine());
        return commonToken;
    }

    public final BitSet setBlockElement() throws RecognitionException, TokenStreamException {
        BitSet bitSetOf;
        int i;
        Token tokenLT = LT(1);
        match(19);
        if (this.inputState.guessing == 0) {
            i = ANTLRLexer.tokenTypeForCharLiteral(tokenLT.getText());
            bitSetOf = BitSet.of(i);
        } else {
            bitSetOf = null;
            i = 0;
        }
        int iLA = LA(1);
        if (iLA != 16 && iLA != 21) {
            if (iLA == 22) {
                match(22);
                Token tokenLT2 = LT(1);
                match(19);
                if (this.inputState.guessing == 0) {
                    int i2 = ANTLRLexer.tokenTypeForCharLiteral(tokenLT2.getText());
                    if (i2 < i) {
                        this.antlrTool.error("Malformed range line ", getFilename(), tokenLT.getLine(), tokenLT.getColumn());
                    }
                    for (int i3 = i + 1; i3 <= i2; i3++) {
                        bitSetOf.add(i3);
                    }
                }
            } else {
                throw new NoViableAltException(LT(1), getFilename());
            }
        }
        return bitSetOf;
    }

    public final void tokensSpec() throws RecognitionException, TokenStreamException {
        match(23);
        int i = 0;
        Token tokenLT = null;
        while (true) {
            if (LA(1) != 6 && LA(1) != 24) {
                if (i < 1) {
                    throw new NoViableAltException(LT(1), getFilename());
                }
                match(17);
                return;
            }
            int iLA = LA(1);
            if (iLA == 6) {
                Token tokenLT2 = LT(1);
                match(6);
                if (this.inputState.guessing == 0) {
                    this.behavior.defineToken(null, tokenLT2);
                }
                int iLA2 = LA(1);
                if (iLA2 == 16) {
                    continue;
                } else if (iLA2 == 25) {
                    tokensSpecOptions(tokenLT2);
                } else {
                    throw new NoViableAltException(LT(1), getFilename());
                }
            } else if (iLA == 24) {
                if (this.inputState.guessing == 0) {
                    tokenLT = null;
                }
                Token tokenLT3 = LT(1);
                match(24);
                int iLA3 = LA(1);
                if (iLA3 == 15) {
                    match(15);
                    tokenLT = LT(1);
                    match(6);
                } else if (iLA3 != 16 && iLA3 != 25) {
                    throw new NoViableAltException(LT(1), getFilename());
                }
                if (this.inputState.guessing == 0) {
                    this.behavior.defineToken(tokenLT3, tokenLT);
                }
                int iLA4 = LA(1);
                if (iLA4 == 16) {
                    continue;
                } else if (iLA4 == 25) {
                    tokensSpecOptions(tokenLT3);
                } else {
                    throw new NoViableAltException(LT(1), getFilename());
                }
            } else {
                throw new NoViableAltException(LT(1), getFilename());
            }
            match(16);
            i++;
        }
    }

    public final void tokensSpecOptions(Token token) throws RecognitionException, TokenStreamException {
        match(25);
        Token tokenId = id();
        match(15);
        Token tokenOptionValue = optionValue();
        if (this.inputState.guessing == 0) {
            this.behavior.refTokensSpecElementOption(token, tokenId, tokenOptionValue);
        }
        while (LA(1) == 16) {
            match(16);
            Token tokenId2 = id();
            match(15);
            Token tokenOptionValue2 = optionValue();
            if (this.inputState.guessing == 0) {
                this.behavior.refTokensSpecElementOption(token, tokenId2, tokenOptionValue2);
            }
        }
        match(26);
    }

    public final String superClass() throws RecognitionException, TokenStreamException {
        match(27);
        String strStripFrontBack = this.inputState.guessing == 0 ? StringUtils.stripFrontBack(LT(1).getText(), "\"", "\"") : null;
        match(6);
        match(28);
        return strStripFrontBack;
    }

    /* JADX WARN: Code duplicated, block: B:104:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:15:0x003e A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:28:0x008d  */
    /* JADX WARN: Code duplicated, block: B:31:0x009d A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:38:0x00bd A[ADDED_TO_REGION, REMOVE] */
    /* JADX WARN: Code duplicated, block: B:41:0x00c4  */
    /* JADX WARN: Code duplicated, block: B:44:0x00d1 A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:46:0x00d6  */
    /* JADX WARN: Code duplicated, block: B:48:0x00e4  */
    /* JADX WARN: Code duplicated, block: B:50:0x00f1  */
    /* JADX WARN: Code duplicated, block: B:53:0x00fc A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:55:0x0101  */
    /* JADX WARN: Code duplicated, block: B:57:0x010f  */
    /* JADX WARN: Code duplicated, block: B:59:0x0121  */
    /* JADX WARN: Code duplicated, block: B:62:0x012e A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:66:0x0136  */
    /* JADX WARN: Code duplicated, block: B:67:0x013a  */
    /* JADX WARN: Code duplicated, block: B:71:0x014e A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:72:0x0150 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:74:0x0153  */
    /* JADX WARN: Code duplicated, block: B:76:0x0161  */
    /* JADX WARN: Code duplicated, block: B:79:0x016a A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:81:0x016d  */
    /* JADX WARN: Code duplicated, block: B:83:0x017b  */
    /* JADX WARN: Code duplicated, block: B:85:0x0188  */
    /* JADX WARN: Code duplicated, block: B:88:0x019e A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:91:0x01a4 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:92:0x01a6  */
    /* JADX WARN: Code duplicated, block: B:93:0x01a9  */
    /* JADX WARN: Code duplicated, block: B:94:0x01ac  */
    /* JADX WARN: Code duplicated, block: B:96:0x01ba  */
    /* JADX WARN: Code duplicated, block: B:99:0x01c3  */
    public final void rule() throws RecognitionException, TokenStreamException {
        String text;
        int iLA;
        String text2;
        Token tokenId;
        int iLA2;
        boolean z;
        int iLA3;
        int iLA4;
        int iLA5;
        int iLA6;
        int iLA7;
        Token tokenLT;
        int iLA8;
        Token tokenLT2;
        Token tokenLT3;
        this.blockNesting = -1;
        int iLA9 = LA(1);
        if (iLA9 == 8) {
            Token tokenLT4 = LT(1);
            match(8);
            text = this.inputState.guessing == 0 ? tokenLT4.getText() : null;
            iLA = LA(1);
            if (iLA == 24) {
                text2 = "public";
            } else {
                text2 = "public";
            }
            tokenId = id();
            iLA2 = LA(1);
            if (iLA2 != 7) {
                z = true;
            } else {
                z = true;
            }
            if (this.inputState.guessing == 0) {
                this.behavior.defineRuleName(tokenId, text2, z, text);
            }
            iLA3 = LA(1);
            if (iLA3 != 7) {
                switch (iLA3) {
                    case 34:
                        tokenLT3 = LT(1);
                        match(34);
                        if (this.inputState.guessing == 0) {
                            this.behavior.refArgAction(tokenLT3);
                        }
                        break;
                    case 35:
                    case 36:
                    case 37:
                        break;
                    default:
                        throw new NoViableAltException(LT(1), getFilename());
                }
            }
            iLA4 = LA(1);
            if (iLA4 != 7) {
                switch (iLA4) {
                    case 35:
                        match(35);
                        tokenLT2 = LT(1);
                        match(34);
                        if (this.inputState.guessing == 0) {
                            this.behavior.refReturnAction(tokenLT2);
                        }
                        break;
                    case 36:
                    case 37:
                        break;
                    default:
                        throw new NoViableAltException(LT(1), getFilename());
                }
            }
            iLA5 = LA(1);
            if (iLA5 != 7) {
                if (iLA5 == 37) {
                    throwsSpec();
                } else {
                    throw new NoViableAltException(LT(1), getFilename());
                }
            }
            iLA6 = LA(1);
            if (iLA6 != 7) {
                if (iLA6 == 14) {
                    ruleOptionsSpec();
                } else if (iLA6 != 36) {
                    throw new NoViableAltException(LT(1), getFilename());
                }
            }
            iLA7 = LA(1);
            if (iLA7 == 7) {
                tokenLT = LT(1);
                match(7);
                if (this.inputState.guessing == 0) {
                    this.behavior.refInitAction(tokenLT);
                }
            } else if (iLA7 != 36) {
                throw new NoViableAltException(LT(1), getFilename());
            }
            match(36);
            block();
            match(16);
            iLA8 = LA(1);
            if (iLA8 != 1) {
                if (iLA8 != 39) {
                    if (iLA8 != 41) {
                        switch (iLA8) {
                            case 7:
                            case 8:
                            case 9:
                            case 10:
                                break;
                            default:
                                switch (iLA8) {
                                    case 30:
                                    case 31:
                                    case 32:
                                        break;
                                    default:
                                        throw new NoViableAltException(LT(1), getFilename());
                                }
                                break;
                        }
                    }
                } else {
                    exceptionGroup();
                }
            }
            if (this.inputState.guessing == 0) {
                this.behavior.endRule(tokenId.getText());
            }
        }
        if (iLA9 != 24 && iLA9 != 41) {
            switch (iLA9) {
                case 30:
                case 31:
                case 32:
                    break;
                default:
                    throw new NoViableAltException(LT(1), getFilename());
            }
            iLA = LA(1);
            if (iLA == 24 && iLA != 41) {
                switch (iLA) {
                    case 30:
                        Token tokenLT5 = LT(1);
                        match(30);
                        if (this.inputState.guessing == 0) {
                            text2 = tokenLT5.getText();
                        } else {
                            text2 = "public";
                        }
                        break;
                    case 31:
                        Token tokenLT6 = LT(1);
                        match(31);
                        if (this.inputState.guessing == 0) {
                            text2 = tokenLT6.getText();
                        } else {
                            text2 = "public";
                        }
                        break;
                    case 32:
                        Token tokenLT7 = LT(1);
                        match(32);
                        if (this.inputState.guessing == 0) {
                            text2 = tokenLT7.getText();
                        } else {
                            text2 = "public";
                        }
                        break;
                    default:
                        throw new NoViableAltException(LT(1), getFilename());
                }
            } else {
                text2 = "public";
            }
            tokenId = id();
            iLA2 = LA(1);
            if (iLA2 != 7 || iLA2 == 14) {
                z = true;
            } else {
                switch (iLA2) {
                    case 33:
                        match(33);
                        z = this.inputState.guessing != 0;
                        break;
                    case 34:
                    case 35:
                    case 36:
                    case 37:
                        z = true;
                        break;
                    default:
                        throw new NoViableAltException(LT(1), getFilename());
                }
            }
            if (this.inputState.guessing == 0) {
                this.behavior.defineRuleName(tokenId, text2, z, text);
            }
            iLA3 = LA(1);
            if (iLA3 != 7 && iLA3 != 14) {
                switch (iLA3) {
                    case 34:
                        tokenLT3 = LT(1);
                        match(34);
                        if (this.inputState.guessing == 0) {
                            this.behavior.refArgAction(tokenLT3);
                        }
                        break;
                    case 35:
                    case 36:
                    case 37:
                        break;
                    default:
                        throw new NoViableAltException(LT(1), getFilename());
                }
            }
            iLA4 = LA(1);
            if (iLA4 != 7 && iLA4 != 14) {
                switch (iLA4) {
                    case 35:
                        match(35);
                        tokenLT2 = LT(1);
                        match(34);
                        if (this.inputState.guessing == 0) {
                            this.behavior.refReturnAction(tokenLT2);
                        }
                        break;
                    case 36:
                    case 37:
                        break;
                    default:
                        throw new NoViableAltException(LT(1), getFilename());
                }
            }
            iLA5 = LA(1);
            if (iLA5 != 7 && iLA5 != 14 && iLA5 != 36) {
                if (iLA5 == 37) {
                    throwsSpec();
                } else {
                    throw new NoViableAltException(LT(1), getFilename());
                }
            }
            iLA6 = LA(1);
            if (iLA6 != 7) {
                if (iLA6 == 14) {
                    ruleOptionsSpec();
                } else if (iLA6 != 36) {
                    throw new NoViableAltException(LT(1), getFilename());
                }
            }
            iLA7 = LA(1);
            if (iLA7 == 7) {
                tokenLT = LT(1);
                match(7);
                if (this.inputState.guessing == 0) {
                    this.behavior.refInitAction(tokenLT);
                }
            } else if (iLA7 != 36) {
                throw new NoViableAltException(LT(1), getFilename());
            }
            match(36);
            block();
            match(16);
            iLA8 = LA(1);
            if (iLA8 != 1 && iLA8 != 24) {
                if (iLA8 != 39) {
                    if (iLA8 != 41) {
                        switch (iLA8) {
                            case 7:
                            case 8:
                            case 9:
                            case 10:
                                break;
                            default:
                                switch (iLA8) {
                                    case 30:
                                    case 31:
                                    case 32:
                                        break;
                                    default:
                                        throw new NoViableAltException(LT(1), getFilename());
                                }
                                break;
                        }
                    }
                } else {
                    exceptionGroup();
                }
            }
            if (this.inputState.guessing == 0) {
                this.behavior.endRule(tokenId.getText());
            }
        }
        iLA = LA(1);
        if (iLA == 24) {
            text2 = "public";
        } else {
            text2 = "public";
        }
        tokenId = id();
        iLA2 = LA(1);
        if (iLA2 != 7) {
            z = true;
        } else {
            z = true;
        }
        if (this.inputState.guessing == 0) {
            this.behavior.defineRuleName(tokenId, text2, z, text);
        }
        iLA3 = LA(1);
        if (iLA3 != 7) {
            switch (iLA3) {
                case 34:
                    tokenLT3 = LT(1);
                    match(34);
                    if (this.inputState.guessing == 0) {
                        this.behavior.refArgAction(tokenLT3);
                    }
                    break;
                case 35:
                case 36:
                case 37:
                    break;
                default:
                    throw new NoViableAltException(LT(1), getFilename());
            }
        }
        iLA4 = LA(1);
        if (iLA4 != 7) {
            switch (iLA4) {
                case 35:
                    match(35);
                    tokenLT2 = LT(1);
                    match(34);
                    if (this.inputState.guessing == 0) {
                        this.behavior.refReturnAction(tokenLT2);
                    }
                    break;
                case 36:
                case 37:
                    break;
                default:
                    throw new NoViableAltException(LT(1), getFilename());
            }
        }
        iLA5 = LA(1);
        if (iLA5 != 7) {
            if (iLA5 == 37) {
                throwsSpec();
            } else {
                throw new NoViableAltException(LT(1), getFilename());
            }
        }
        iLA6 = LA(1);
        if (iLA6 != 7) {
            if (iLA6 == 14) {
                ruleOptionsSpec();
            } else if (iLA6 != 36) {
                throw new NoViableAltException(LT(1), getFilename());
            }
        }
        iLA7 = LA(1);
        if (iLA7 == 7) {
            tokenLT = LT(1);
            match(7);
            if (this.inputState.guessing == 0) {
                this.behavior.refInitAction(tokenLT);
            }
        } else if (iLA7 != 36) {
            throw new NoViableAltException(LT(1), getFilename());
        }
        match(36);
        block();
        match(16);
        iLA8 = LA(1);
        if (iLA8 != 1) {
            if (iLA8 != 39) {
                if (iLA8 != 41) {
                    switch (iLA8) {
                        case 7:
                        case 8:
                        case 9:
                        case 10:
                            break;
                        default:
                            switch (iLA8) {
                                case 30:
                                case 31:
                                case 32:
                                    break;
                                default:
                                    throw new NoViableAltException(LT(1), getFilename());
                            }
                            break;
                    }
                }
            } else {
                exceptionGroup();
            }
        }
        if (this.inputState.guessing == 0) {
            this.behavior.endRule(tokenId.getText());
        }
    }

    public final void throwsSpec() throws RecognitionException, TokenStreamException {
        match(37);
        String text = this.inputState.guessing == 0 ? id().getText() : null;
        while (LA(1) == 38) {
            match(38);
            Token tokenId = id();
            if (this.inputState.guessing == 0) {
                text = new StringBuffer().append(text).append(",").append(tokenId.getText()).toString();
            }
        }
        if (this.inputState.guessing == 0) {
            this.behavior.setUserExceptions(text);
        }
    }

    public final void ruleOptionsSpec() throws RecognitionException, TokenStreamException {
        match(14);
        while (true) {
            if (LA(1) == 24 || LA(1) == 41) {
                Token tokenId = id();
                match(15);
                Token tokenOptionValue = optionValue();
                if (this.inputState.guessing == 0) {
                    this.behavior.setRuleOption(tokenId, tokenOptionValue);
                }
                match(16);
            } else {
                match(17);
                return;
            }
        }
    }

    public final void block() throws RecognitionException, TokenStreamException {
        if (this.inputState.guessing == 0) {
            this.blockNesting++;
        }
        alternative();
        while (LA(1) == 21) {
            match(21);
            alternative();
        }
        if (this.inputState.guessing == 0) {
            this.blockNesting--;
        }
    }

    public final void exceptionGroup() throws RecognitionException, TokenStreamException {
        if (this.inputState.guessing == 0) {
            this.behavior.beginExceptionGroup();
        }
        int i = 0;
        while (LA(1) == 39) {
            exceptionSpec();
            i++;
        }
        if (i < 1) {
            throw new NoViableAltException(LT(1), getFilename());
        }
        if (this.inputState.guessing == 0) {
            this.behavior.endExceptionGroup();
        }
    }

    public final void alternative() throws RecognitionException, TokenStreamException {
        boolean z;
        int iLA = LA(1);
        if (iLA == 6 || iLA == 7 || iLA == 16 || iLA == 19 || iLA == 21 || iLA == 24) {
            z = true;
        } else if (iLA == 33) {
            match(33);
            z = this.inputState.guessing != 0;
        } else if (iLA == 39 || iLA == 50 || iLA == 27 || iLA == 28) {
            z = true;
        } else {
            switch (iLA) {
                case 41:
                case 42:
                case 43:
                case 44:
                    z = true;
                    break;
                default:
                    throw new NoViableAltException(LT(1), getFilename());
            }
        }
        if (this.inputState.guessing == 0) {
            this.behavior.beginAlt(z);
        }
        while (_tokenSet_2.member(LA(1))) {
            element();
        }
        int iLA2 = LA(1);
        if (iLA2 != 16 && iLA2 != 21 && iLA2 != 28) {
            if (iLA2 == 39) {
                exceptionSpecNoLabel();
            } else {
                throw new NoViableAltException(LT(1), getFilename());
            }
        }
        if (this.inputState.guessing == 0) {
            this.behavior.endAlt();
        }
    }

    public final void element() throws RecognitionException, TokenStreamException {
        elementNoOptionSpec();
        int iLA = LA(1);
        if (iLA == 6 || iLA == 7 || iLA == 16 || iLA == 19 || iLA == 21 || iLA == 39 || iLA == 50 || iLA == 24) {
            return;
        }
        if (iLA == 25) {
            elementOptionSpec();
        } else {
            if (iLA == 27 || iLA == 28) {
                return;
            }
            switch (iLA) {
                case 41:
                case 42:
                case 43:
                case 44:
                    return;
                default:
                    throw new NoViableAltException(LT(1), getFilename());
            }
        }
    }

    public final void exceptionSpecNoLabel() throws RecognitionException, TokenStreamException {
        match(39);
        if (this.inputState.guessing == 0) {
            this.behavior.beginExceptionSpec(null);
        }
        while (LA(1) == 40) {
            exceptionHandler();
        }
        if (this.inputState.guessing == 0) {
            this.behavior.endExceptionSpec();
        }
    }

    /* JADX WARN: Code duplicated, block: B:16:0x0039 A[FALL_THROUGH] */
    public final void exceptionSpec() throws RecognitionException, TokenStreamException {
        Token tokenLT;
        match(39);
        int iLA = LA(1);
        if (iLA == 1 || iLA == 24) {
            tokenLT = null;
        } else {
            if (iLA == 34) {
                tokenLT = LT(1);
                match(34);
                if (this.inputState.guessing != 0) {
                }
            } else {
                switch (iLA) {
                    default:
                        switch (iLA) {
                            default:
                                switch (iLA) {
                                    case 39:
                                    case 40:
                                    case 41:
                                        break;
                                    default:
                                        throw new NoViableAltException(LT(1), getFilename());
                                }
                            case 30:
                            case 31:
                            case 32:
                                tokenLT = null;
                                break;
                        }
                    case 7:
                    case 8:
                    case 9:
                    case 10:
                        tokenLT = null;
                        break;
                }
            }
            tokenLT = null;
        }
        if (this.inputState.guessing == 0) {
            this.behavior.beginExceptionSpec(tokenLT);
        }
        while (LA(1) == 40) {
            exceptionHandler();
        }
        if (this.inputState.guessing == 0) {
            this.behavior.endExceptionSpec();
        }
    }

    public final void exceptionHandler() throws RecognitionException, TokenStreamException {
        match(40);
        Token tokenLT = LT(1);
        match(34);
        Token tokenLT2 = LT(1);
        match(7);
        if (this.inputState.guessing == 0) {
            this.behavior.refExceptionHandler(tokenLT, tokenLT2);
        }
    }

    public final void elementNoOptionSpec() throws RecognitionException, TokenStreamException {
        Token token;
        Token token2;
        Token token3;
        int i;
        Token tokenId;
        Token token4;
        int i2;
        int iLA = LA(1);
        if (iLA == 7) {
            Token tokenLT = LT(1);
            match(7);
            if (this.inputState.guessing == 0) {
                this.behavior.refAction(tokenLT);
                return;
            }
            return;
        }
        if (iLA == 43) {
            Token tokenLT2 = LT(1);
            match(43);
            if (this.inputState.guessing == 0) {
                this.behavior.refSemPred(tokenLT2);
                return;
            }
            return;
        }
        if (iLA == 44) {
            tree();
            return;
        }
        if ((LA(1) == 24 || LA(1) == 41) && LA(2) == 15) {
            Token tokenId2 = id();
            match(15);
            if ((LA(1) == 24 || LA(1) == 41) && LA(2) == 36) {
                Token tokenId3 = id();
                match(36);
                if (this.inputState.guessing == 0) {
                    checkForMissingEndRule(tokenId3);
                }
                token = tokenId3;
            } else {
                if ((LA(1) != 24 && LA(1) != 41) || !_tokenSet_3.member(LA(2))) {
                    throw new NoViableAltException(LT(1), getFilename());
                }
                token = null;
            }
            int iLA2 = LA(1);
            if (iLA2 == 24) {
                Token tokenLT3 = LT(1);
                match(24);
                int iLA3 = LA(1);
                if (iLA3 == 6 || iLA3 == 7 || iLA3 == 16 || iLA3 == 19 || iLA3 == 21) {
                    token2 = null;
                } else if (iLA3 == 34) {
                    Token tokenLT4 = LT(1);
                    match(34);
                    token2 = this.inputState.guessing == 0 ? tokenLT4 : null;
                } else if (iLA3 == 39 || iLA3 == 50 || iLA3 == 24 || iLA3 == 25 || iLA3 == 27 || iLA3 == 28) {
                    token2 = null;
                } else {
                    switch (iLA3) {
                        case 41:
                        case 42:
                        case 43:
                        case 44:
                            token2 = null;
                            break;
                        default:
                            throw new NoViableAltException(LT(1), getFilename());
                    }
                }
                if (this.inputState.guessing == 0) {
                    this.behavior.refToken(tokenId2, tokenLT3, token, token2, false, 1, lastInRule());
                    return;
                }
                return;
            }
            if (iLA2 == 41) {
                Token tokenLT5 = LT(1);
                match(41);
                int iLA4 = LA(1);
                if (iLA4 == 6 || iLA4 == 7 || iLA4 == 16 || iLA4 == 19 || iLA4 == 21 || iLA4 == 39 || iLA4 == 50 || iLA4 == 24 || iLA4 == 25 || iLA4 == 27 || iLA4 == 28 || iLA4 == 33) {
                    token3 = null;
                } else if (iLA4 == 34) {
                    Token tokenLT6 = LT(1);
                    match(34);
                    token3 = this.inputState.guessing == 0 ? tokenLT6 : null;
                } else {
                    switch (iLA4) {
                        case 41:
                        case 42:
                        case 43:
                        case 44:
                            token3 = null;
                            break;
                        default:
                            throw new NoViableAltException(LT(1), getFilename());
                    }
                }
                int iLA5 = LA(1);
                if (iLA5 == 6 || iLA5 == 7 || iLA5 == 16 || iLA5 == 19 || iLA5 == 21) {
                    i = 1;
                } else if (iLA5 == 33) {
                    match(33);
                    i = this.inputState.guessing == 0 ? 3 : 1;
                } else if (iLA5 == 39 || iLA5 == 50 || iLA5 == 24 || iLA5 == 25 || iLA5 == 27 || iLA5 == 28) {
                    i = 1;
                } else {
                    switch (iLA5) {
                        case 41:
                        case 42:
                        case 43:
                        case 44:
                            i = 1;
                            break;
                        default:
                            throw new NoViableAltException(LT(1), getFilename());
                    }
                }
                if (this.inputState.guessing == 0) {
                    this.behavior.refRule(tokenId2, tokenLT5, token, token3, i);
                    return;
                }
                return;
            }
            throw new NoViableAltException(LT(1), getFilename());
        }
        BitSet bitSet = _tokenSet_4;
        if (bitSet.member(LA(1)) && _tokenSet_5.member(LA(2))) {
            if ((LA(1) == 24 || LA(1) == 41) && LA(2) == 36) {
                tokenId = id();
                match(36);
                if (this.inputState.guessing == 0) {
                    checkForMissingEndRule(tokenId);
                }
            } else {
                if (!bitSet.member(LA(1)) || !_tokenSet_6.member(LA(2))) {
                    throw new NoViableAltException(LT(1), getFilename());
                }
                tokenId = null;
            }
            int iLA6 = LA(1);
            if (iLA6 == 27) {
                ebnf(tokenId, false);
                return;
            }
            if (iLA6 != 41) {
                if (iLA6 == 42) {
                    match(42);
                    int iLA7 = LA(1);
                    if (iLA7 == 19 || iLA7 == 24) {
                        notTerminal(tokenId);
                        return;
                    } else {
                        if (iLA7 == 27) {
                            ebnf(tokenId, true);
                            return;
                        }
                        throw new NoViableAltException(LT(1), getFilename());
                    }
                }
                if ((LA(1) == 6 || LA(1) == 19 || LA(1) == 24) && LA(2) == 22) {
                    range(tokenId);
                    return;
                } else {
                    if (_tokenSet_7.member(LA(1)) && _tokenSet_8.member(LA(2))) {
                        terminal(tokenId);
                        return;
                    }
                    throw new NoViableAltException(LT(1), getFilename());
                }
            }
            Token tokenLT7 = LT(1);
            match(41);
            int iLA8 = LA(1);
            if (iLA8 == 6 || iLA8 == 7 || iLA8 == 16 || iLA8 == 19 || iLA8 == 21 || iLA8 == 39 || iLA8 == 50 || iLA8 == 24 || iLA8 == 25 || iLA8 == 27 || iLA8 == 28 || iLA8 == 33) {
                token4 = null;
            } else if (iLA8 == 34) {
                Token tokenLT8 = LT(1);
                match(34);
                token4 = this.inputState.guessing == 0 ? tokenLT8 : null;
            } else {
                switch (iLA8) {
                    case 41:
                    case 42:
                    case 43:
                    case 44:
                        token4 = null;
                        break;
                    default:
                        throw new NoViableAltException(LT(1), getFilename());
                }
            }
            int iLA9 = LA(1);
            if (iLA9 == 6 || iLA9 == 7 || iLA9 == 16 || iLA9 == 19 || iLA9 == 21) {
                i2 = 1;
            } else if (iLA9 == 33) {
                match(33);
                i2 = this.inputState.guessing == 0 ? 3 : 1;
            } else if (iLA9 == 39 || iLA9 == 50 || iLA9 == 24 || iLA9 == 25 || iLA9 == 27 || iLA9 == 28) {
                i2 = 1;
            } else {
                switch (iLA9) {
                    case 41:
                    case 42:
                    case 43:
                    case 44:
                        i2 = 1;
                        break;
                    default:
                        throw new NoViableAltException(LT(1), getFilename());
                }
            }
            if (this.inputState.guessing == 0) {
                this.behavior.refRule(null, tokenLT7, tokenId, token4, i2);
                return;
            }
            return;
        }
        throw new NoViableAltException(LT(1), getFilename());
    }

    public final void elementOptionSpec() throws RecognitionException, TokenStreamException {
        match(25);
        Token tokenId = id();
        match(15);
        Token tokenOptionValue = optionValue();
        if (this.inputState.guessing == 0) {
            this.behavior.refElementOption(tokenId, tokenOptionValue);
        }
        while (LA(1) == 16) {
            match(16);
            Token tokenId2 = id();
            match(15);
            Token tokenOptionValue2 = optionValue();
            if (this.inputState.guessing == 0) {
                this.behavior.refElementOption(tokenId2, tokenOptionValue2);
            }
        }
        match(26);
    }

    /* JADX WARN: Code duplicated, block: B:51:0x00c2 A[PHI: r1
      0x00c2: PHI (r1v3 antlr.Token) = (r1v2 antlr.Token), (r1v11 antlr.Token) binds: [B:50:0x00c0, B:45:0x00a4] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:52:0x00c4  */
    /* JADX WARN: Code duplicated, block: B:63:0x00f9 A[PHI: r0
      0x00f9: PHI (r0v2 antlr.Token) = (r0v1 antlr.Token), (r0v6 antlr.Token) binds: [B:62:0x00f7, B:57:0x00db] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:64:0x00fb  */
    public final void range(Token token) throws RecognitionException, TokenStreamException {
        Token tokenLT;
        Token token2;
        Token tokenLT2;
        Token token3;
        int i = 1;
        int iLA = LA(1);
        if (iLA != 6) {
            if (iLA == 19) {
                Token tokenLT3 = LT(1);
                match(19);
                match(22);
                Token tokenLT4 = LT(1);
                match(19);
                int iLA2 = LA(1);
                if (iLA2 != 6 && iLA2 != 7 && iLA2 != 16 && iLA2 != 19 && iLA2 != 21) {
                    if (iLA2 == 33) {
                        match(33);
                        if (this.inputState.guessing == 0) {
                            i = 3;
                        }
                    } else if (iLA2 != 39 && iLA2 != 50 && iLA2 != 24 && iLA2 != 25 && iLA2 != 27 && iLA2 != 28) {
                        switch (iLA2) {
                            case 41:
                            case 42:
                            case 43:
                            case 44:
                                break;
                            default:
                                throw new NoViableAltException(LT(1), getFilename());
                        }
                    }
                }
                if (this.inputState.guessing == 0) {
                    this.behavior.refCharRange(tokenLT3, tokenLT4, token, i, lastInRule());
                    return;
                }
                return;
            }
            if (iLA != 24) {
                throw new NoViableAltException(LT(1), getFilename());
            }
        }
        int iLA3 = LA(1);
        if (iLA3 == 6) {
            tokenLT = LT(1);
            match(6);
            if (this.inputState.guessing == 0) {
                token2 = tokenLT;
            } else {
                token2 = null;
            }
        } else if (iLA3 == 24) {
            tokenLT = LT(1);
            match(24);
            if (this.inputState.guessing == 0) {
                token2 = tokenLT;
            } else {
                token2 = null;
            }
        } else {
            throw new NoViableAltException(LT(1), getFilename());
        }
        match(22);
        int iLA4 = LA(1);
        if (iLA4 == 6) {
            tokenLT2 = LT(1);
            match(6);
            if (this.inputState.guessing == 0) {
                token3 = tokenLT2;
            } else {
                token3 = null;
            }
        } else if (iLA4 == 24) {
            tokenLT2 = LT(1);
            match(24);
            if (this.inputState.guessing == 0) {
                token3 = tokenLT2;
            } else {
                token3 = null;
            }
        } else {
            throw new NoViableAltException(LT(1), getFilename());
        }
        int iAst_type_spec = ast_type_spec();
        if (this.inputState.guessing == 0) {
            this.behavior.refTokenRange(token2, token3, token, iAst_type_spec, lastInRule());
        }
    }

    public final void terminal(Token token) throws RecognitionException, TokenStreamException {
        Token tokenLT;
        int i = 1;
        int iLA = LA(1);
        if (iLA == 6) {
            Token tokenLT2 = LT(1);
            match(6);
            int iAst_type_spec = ast_type_spec();
            if (this.inputState.guessing == 0) {
                this.behavior.refStringLiteral(tokenLT2, token, iAst_type_spec, lastInRule());
                return;
            }
            return;
        }
        if (iLA == 19) {
            Token tokenLT3 = LT(1);
            match(19);
            int iLA2 = LA(1);
            if (iLA2 != 6 && iLA2 != 7 && iLA2 != 16 && iLA2 != 19 && iLA2 != 21) {
                if (iLA2 == 33) {
                    match(33);
                    if (this.inputState.guessing == 0) {
                        i = 3;
                    }
                } else if (iLA2 != 39 && iLA2 != 50 && iLA2 != 24 && iLA2 != 25 && iLA2 != 27 && iLA2 != 28) {
                    switch (iLA2) {
                        case 41:
                        case 42:
                        case 43:
                        case 44:
                            break;
                        default:
                            throw new NoViableAltException(LT(1), getFilename());
                    }
                }
            }
            int i2 = i;
            if (this.inputState.guessing == 0) {
                this.behavior.refCharLiteral(tokenLT3, token, false, i2, lastInRule());
                return;
            }
            return;
        }
        if (iLA != 24) {
            if (iLA == 50) {
                Token tokenLT4 = LT(1);
                match(50);
                int iAst_type_spec2 = ast_type_spec();
                if (this.inputState.guessing == 0) {
                    this.behavior.refWildcard(tokenLT4, token, iAst_type_spec2);
                    return;
                }
                return;
            }
            throw new NoViableAltException(LT(1), getFilename());
        }
        Token tokenLT5 = LT(1);
        match(24);
        int iAst_type_spec3 = ast_type_spec();
        int iLA3 = LA(1);
        if (iLA3 == 6 || iLA3 == 7 || iLA3 == 16 || iLA3 == 19 || iLA3 == 21) {
            tokenLT = null;
        } else {
            if (iLA3 == 34) {
                tokenLT = LT(1);
                match(34);
                if (this.inputState.guessing != 0) {
                }
            } else if (iLA3 != 39 && iLA3 != 50 && iLA3 != 24 && iLA3 != 25 && iLA3 != 27 && iLA3 != 28) {
                switch (iLA3) {
                    case 41:
                    case 42:
                    case 43:
                    case 44:
                        break;
                    default:
                        throw new NoViableAltException(LT(1), getFilename());
                }
            }
            tokenLT = null;
        }
        Token token2 = tokenLT;
        if (this.inputState.guessing == 0) {
            this.behavior.refToken(null, tokenLT5, token, token2, false, iAst_type_spec3, lastInRule());
        }
    }

    public final void notTerminal(Token token) throws RecognitionException, TokenStreamException {
        int i = 1;
        int iLA = LA(1);
        if (iLA != 19) {
            if (iLA == 24) {
                Token tokenLT = LT(1);
                match(24);
                int iAst_type_spec = ast_type_spec();
                if (this.inputState.guessing == 0) {
                    this.behavior.refToken(null, tokenLT, token, null, true, iAst_type_spec, lastInRule());
                    return;
                }
                return;
            }
            throw new NoViableAltException(LT(1), getFilename());
        }
        Token tokenLT2 = LT(1);
        match(19);
        int iLA2 = LA(1);
        if (iLA2 != 6 && iLA2 != 7 && iLA2 != 16 && iLA2 != 19 && iLA2 != 21) {
            if (iLA2 == 33) {
                match(33);
                if (this.inputState.guessing == 0) {
                    i = 3;
                }
            } else if (iLA2 != 39 && iLA2 != 50 && iLA2 != 24 && iLA2 != 25 && iLA2 != 27 && iLA2 != 28) {
                switch (iLA2) {
                    case 41:
                    case 42:
                    case 43:
                    case 44:
                        break;
                    default:
                        throw new NoViableAltException(LT(1), getFilename());
                }
            }
        }
        int i2 = i;
        if (this.inputState.guessing == 0) {
            this.behavior.refCharLiteral(tokenLT2, token, true, i2, lastInRule());
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:48:0x00e3  */
    /* JADX WARN: Code duplicated, block: B:62:0x0102  */
    /* JADX WARN: Code duplicated, block: B:64:0x0110  */
    /* JADX WARN: Code duplicated, block: B:66:0x011b  */
    /* JADX WARN: Code duplicated, block: B:67:0x0121  */
    /* JADX WARN: Code duplicated, block: B:69:0x012c  */
    /* JADX WARN: Code duplicated, block: B:70:0x0132  */
    /* JADX WARN: Code duplicated, block: B:72:0x013d  */
    /* JADX WARN: Code duplicated, block: B:80:0x0152  */
    /* JADX WARN: Code duplicated, block: B:87:0x0161  */
    /* JADX WARN: Code duplicated, block: B:89:0x016f  */
    /* JADX WARN: Code duplicated, block: B:91:0x0178  */
    public final void ebnf(Token token, boolean z) throws RecognitionException, TokenStreamException {
        int iLA;
        int iLA2;
        Token tokenLT = LT(1);
        match(27);
        if (this.inputState.guessing == 0) {
            this.behavior.beginSubRule(token, tokenLT, z);
        }
        if (LA(1) == 14) {
            subruleOptionsSpec();
            int iLA3 = LA(1);
            if (iLA3 == 7) {
                Token tokenLT2 = LT(1);
                match(7);
                if (this.inputState.guessing == 0) {
                    this.behavior.refInitAction(tokenLT2);
                }
            } else if (iLA3 != 36) {
                throw new NoViableAltException(LT(1), getFilename());
            }
            match(36);
        } else if (LA(1) == 7 && LA(2) == 36) {
            Token tokenLT3 = LT(1);
            match(7);
            if (this.inputState.guessing == 0) {
                this.behavior.refInitAction(tokenLT3);
            }
            match(36);
        } else if (!_tokenSet_9.member(LA(1)) || !_tokenSet_10.member(LA(2))) {
            throw new NoViableAltException(LT(1), getFilename());
        }
        block();
        match(28);
        int iLA4 = LA(1);
        if (iLA4 == 6 || iLA4 == 7 || iLA4 == 16 || iLA4 == 19 || iLA4 == 21 || iLA4 == 33 || iLA4 == 39 || iLA4 == 50 || iLA4 == 24 || iLA4 == 25 || iLA4 == 27 || iLA4 == 28) {
            iLA = LA(1);
            if (iLA != 6 && iLA != 7 && iLA != 16 && iLA != 19 && iLA != 21 && iLA != 33 && iLA != 39 && iLA != 50 && iLA != 24 && iLA != 25 && iLA != 27 && iLA != 28) {
                switch (iLA) {
                    case 41:
                    case 42:
                    case 43:
                    case 44:
                        break;
                    case 45:
                        match(45);
                        if (this.inputState.guessing == 0) {
                            this.behavior.optionalSubRule();
                        }
                        break;
                    case 46:
                        match(46);
                        if (this.inputState.guessing == 0) {
                            this.behavior.zeroOrMoreSubRule();
                        }
                        break;
                    case 47:
                        match(47);
                        if (this.inputState.guessing == 0) {
                            this.behavior.oneOrMoreSubRule();
                        }
                        break;
                    default:
                        throw new NoViableAltException(LT(1), getFilename());
                }
            }
            iLA2 = LA(1);
            if (iLA2 != 6 && iLA2 != 7 && iLA2 != 16 && iLA2 != 19 && iLA2 != 21) {
                if (iLA2 != 33) {
                    match(33);
                    if (this.inputState.guessing == 0) {
                        this.behavior.noASTSubRule();
                    }
                } else if (iLA2 != 39 && iLA2 != 50 && iLA2 != 24 && iLA2 != 25 && iLA2 != 27 && iLA2 != 28) {
                    switch (iLA2) {
                        case 41:
                        case 42:
                        case 43:
                        case 44:
                            break;
                        default:
                            throw new NoViableAltException(LT(1), getFilename());
                    }
                }
            }
        } else {
            switch (iLA4) {
                case 41:
                case 42:
                case 43:
                case 44:
                case 45:
                case 46:
                case 47:
                    iLA = LA(1);
                    if (iLA != 6) {
                        switch (iLA) {
                            case 41:
                            case 42:
                            case 43:
                            case 44:
                                break;
                            case 45:
                                match(45);
                                if (this.inputState.guessing == 0) {
                                    this.behavior.optionalSubRule();
                                }
                                break;
                            case 46:
                                match(46);
                                if (this.inputState.guessing == 0) {
                                    this.behavior.zeroOrMoreSubRule();
                                }
                                break;
                            case 47:
                                match(47);
                                if (this.inputState.guessing == 0) {
                                    this.behavior.oneOrMoreSubRule();
                                }
                                break;
                            default:
                                throw new NoViableAltException(LT(1), getFilename());
                        }
                    }
                    iLA2 = LA(1);
                    if (iLA2 != 6) {
                        if (iLA2 != 33) {
                            match(33);
                            if (this.inputState.guessing == 0) {
                                this.behavior.noASTSubRule();
                            }
                            break;
                        } else if (iLA2 != 39) {
                            switch (iLA2) {
                                case 41:
                                case 42:
                                case 43:
                                case 44:
                                    break;
                                default:
                                    throw new NoViableAltException(LT(1), getFilename());
                            }
                        }
                    }
                    break;
                case 48:
                    match(48);
                    if (this.inputState.guessing == 0) {
                        this.behavior.synPred();
                    }
                    break;
                default:
                    throw new NoViableAltException(LT(1), getFilename());
            }
        }
        if (this.inputState.guessing == 0) {
            this.behavior.endSubRule();
        }
    }

    public final void tree() throws RecognitionException, TokenStreamException {
        Token tokenLT = LT(1);
        match(44);
        if (this.inputState.guessing == 0) {
            this.behavior.beginTree(tokenLT);
        }
        rootNode();
        if (this.inputState.guessing == 0) {
            this.behavior.beginChildList();
        }
        int i = 0;
        while (_tokenSet_2.member(LA(1))) {
            element();
            i++;
        }
        if (i < 1) {
            throw new NoViableAltException(LT(1), getFilename());
        }
        if (this.inputState.guessing == 0) {
            this.behavior.endChildList();
        }
        match(28);
        if (this.inputState.guessing == 0) {
            this.behavior.endTree();
        }
    }

    public final void rootNode() throws RecognitionException, TokenStreamException {
        Token tokenId;
        if ((LA(1) == 24 || LA(1) == 41) && LA(2) == 36) {
            tokenId = id();
            match(36);
            if (this.inputState.guessing == 0) {
                checkForMissingEndRule(tokenId);
            }
        } else {
            if (!_tokenSet_7.member(LA(1)) || !_tokenSet_11.member(LA(2))) {
                throw new NoViableAltException(LT(1), getFilename());
            }
            tokenId = null;
        }
        terminal(tokenId);
    }

    public final int ast_type_spec() throws RecognitionException, TokenStreamException {
        int iLA = LA(1);
        if (iLA == 6 || iLA == 7 || iLA == 16 || iLA == 19 || iLA == 21 || iLA == 39 || iLA == 24 || iLA == 25 || iLA == 27 || iLA == 28) {
            return 1;
        }
        if (iLA == 33) {
            match(33);
            return this.inputState.guessing == 0 ? 3 : 1;
        }
        if (iLA == 34) {
            return 1;
        }
        if (iLA == 49) {
            match(49);
            return this.inputState.guessing == 0 ? 2 : 1;
        }
        if (iLA == 50) {
            return 1;
        }
        switch (iLA) {
            case 41:
            case 42:
            case 43:
            case 44:
                return 1;
            default:
                throw new NoViableAltException(LT(1), getFilename());
        }
    }

    private static final long[] mk_tokenSet_0() {
        return new long[]{2206556225792L, 0};
    }

    private static final long[] mk_tokenSet_1() {
        return new long[]{2472844214400L, 0};
    }

    private static final long[] mk_tokenSet_2() {
        return new long[]{1158885407195328L, 0};
    }

    private static final long[] mk_tokenSet_3() {
        return new long[]{1159461236965568L, 0};
    }

    private static final long[] mk_tokenSet_4() {
        return new long[]{1132497128128576L, 0};
    }

    private static final long[] mk_tokenSet_5() {
        return new long[]{1722479914074304L, 0};
    }

    private static final long[] mk_tokenSet_6() {
        return new long[]{1722411194597568L, 0};
    }

    private static final long[] mk_tokenSet_7() {
        return new long[]{1125899924144192L, 0};
    }

    private static final long[] mk_tokenSet_8() {
        return new long[]{1722411190386880L, 0};
    }

    private static final long[] mk_tokenSet_9() {
        return new long[]{1159444023476416L, 0};
    }

    private static final long[] mk_tokenSet_10() {
        return new long[]{2251345007067328L, 0};
    }

    private static final long[] mk_tokenSet_11() {
        return new long[]{1721861130420416L, 0};
    }
}
