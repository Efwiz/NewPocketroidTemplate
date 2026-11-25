package org.antlr.stringtemplate.language;

import antlr.ASTFactory;
import antlr.ASTPair;
import antlr.LLkParser;
import antlr.NoViableAltException;
import antlr.ParserSharedInputState;
import antlr.RecognitionException;
import antlr.Token;
import antlr.TokenBuffer;
import antlr.TokenStream;
import antlr.TokenStreamException;
import antlr.collections.impl.ASTArray;
import antlr.collections.impl.BitSet;
import java.util.HashMap;
import java.util.Map;
import org.antlr.stringtemplate.StringTemplate;
import org.apache.xerces.impl.xs.SchemaSymbols;

/* JADX INFO: loaded from: classes12.dex */
public class ActionParser extends LLkParser implements ActionParserTokenTypes {
    protected StringTemplate self;
    public static final String[] _tokenNames = {"<0>", "EOF", "<2>", "NULL_TREE_LOOKAHEAD", "APPLY", "MULTI_APPLY", "ARGS", "INCLUDE", "\"if\"", "VALUE", "TEMPLATE", "FUNCTION", "SINGLEVALUEARG", "LIST", "NOTHING", "SEMI", "LPAREN", "RPAREN", "\"elseif\"", "COMMA", SchemaSymbols.ATTVAL_ID, "ASSIGN", "COLON", "NOT", "PLUS", "DOT", "\"first\"", "\"rest\"", "\"last\"", "\"length\"", "\"strip\"", "\"trunc\"", "\"super\"", "ANONYMOUS_TEMPLATE", "STRING", "INT", "LBRACK", "RBRACK", "DOTDOTDOT", "TEMPLATE_ARGS", "NESTED_ANONYMOUS_TEMPLATE", "ESC_CHAR", "WS", "WS_CHAR"};
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

    public ActionParser(TokenStream tokenStream, StringTemplate stringTemplate) {
        this(tokenStream, 2);
        this.self = stringTemplate;
    }

    @Override // antlr.Parser
    public void reportError(RecognitionException recognitionException) {
        if (this.self.getGroup() == StringTemplate.defaultGroup) {
            this.self.error(new StringBuffer("action parse error; template context is ").append(this.self.getEnclosingInstanceStackString()).toString(), recognitionException);
        } else {
            this.self.error(new StringBuffer("action parse error in group ").append(this.self.getGroup().getName()).append(" line ").append(this.self.getGroupFileLine()).append("; template context is ").append(this.self.getEnclosingInstanceStackString()).toString(), recognitionException);
        }
    }

    protected ActionParser(TokenBuffer tokenBuffer, int i) {
        super(tokenBuffer, i);
        this.self = null;
        this.tokenNames = _tokenNames;
        buildTokenTypeASTClassMap();
        this.astFactory = new ASTFactory(getTokenTypeToASTClassMap());
    }

    public ActionParser(TokenBuffer tokenBuffer) {
        this(tokenBuffer, 2);
    }

    protected ActionParser(TokenStream tokenStream, int i) {
        super(tokenStream, i);
        this.self = null;
        this.tokenNames = _tokenNames;
        buildTokenTypeASTClassMap();
        this.astFactory = new ASTFactory(getTokenTypeToASTClassMap());
    }

    public ActionParser(TokenStream tokenStream) {
        this(tokenStream, 2);
    }

    public ActionParser(ParserSharedInputState parserSharedInputState) {
        super(parserSharedInputState, 2);
        this.self = null;
        this.tokenNames = _tokenNames;
        buildTokenTypeASTClassMap();
        this.astFactory = new ASTFactory(getTokenTypeToASTClassMap());
    }

    /* JADX WARN: Code duplicated, block: B:16:0x0058  */
    /* JADX WARN: Code duplicated, block: B:18:0x005c A[Catch: RecognitionException -> 0x00ad, TRY_LEAVE, TryCatch #0 {RecognitionException -> 0x00ad, blocks: (B:3:0x0009, B:10:0x001f, B:11:0x0022, B:12:0x002f, B:13:0x0030, B:14:0x0048, B:18:0x005c, B:21:0x006b, B:22:0x0078, B:28:0x0082), top: B:38:0x0009 }] */
    /* JADX WARN: Code duplicated, block: B:21:0x006b A[Catch: RecognitionException -> 0x00ad, TRY_ENTER, TryCatch #0 {RecognitionException -> 0x00ad, blocks: (B:3:0x0009, B:10:0x001f, B:11:0x0022, B:12:0x002f, B:13:0x0030, B:14:0x0048, B:18:0x005c, B:21:0x006b, B:22:0x0078, B:28:0x0082), top: B:38:0x0009 }] */
    /* JADX WARN: Code duplicated, block: B:23:0x0079  */
    public final Map action() throws RecognitionException, TokenStreamException {
        Map mapOptionList;
        StringTemplateAST stringTemplateAST;
        int iLA;
        StringTemplateAST stringTemplateAST2 = null;
        map = null;
        Map map = null;
        this.returnAST = null;
        ASTPair aSTPair = new ASTPair();
        try {
            int iLA2 = LA(1);
            if (iLA2 == 8) {
                this.astFactory.makeASTRoot(aSTPair, (StringTemplateAST) this.astFactory.create(LT(1)));
                match(8);
                match(16);
                ifCondition();
                this.astFactory.addASTChild(aSTPair, this.returnAST);
                match(17);
                stringTemplateAST = (StringTemplateAST) aSTPair.root;
            } else if (iLA2 == 16) {
                templatesExpr();
                this.astFactory.addASTChild(aSTPair, this.returnAST);
                iLA = LA(1);
                if (iLA != 1) {
                    mapOptionList = null;
                } else if (iLA == 15) {
                    match(15);
                    mapOptionList = optionList();
                    try {
                        this.astFactory.addASTChild(aSTPair, this.returnAST);
                    } catch (RecognitionException e) {
                        e = e;
                        if (this.inputState.guessing == 0) {
                            reportError(e);
                            recover(e, _tokenSet_0);
                        } else {
                            throw e;
                        }
                    }
                } else {
                    throw new NoViableAltException(LT(1), getFilename());
                }
                stringTemplateAST = (StringTemplateAST) aSTPair.root;
                map = mapOptionList;
            } else if (iLA2 != 18) {
                if (iLA2 != 20) {
                    switch (iLA2) {
                        case 26:
                        case 27:
                        case 28:
                        case 29:
                        case 30:
                        case 31:
                        case 32:
                        case 33:
                        case 34:
                        case 35:
                        case 36:
                            break;
                        default:
                            throw new NoViableAltException(LT(1), getFilename());
                    }
                }
                templatesExpr();
                this.astFactory.addASTChild(aSTPair, this.returnAST);
                iLA = LA(1);
                if (iLA != 1) {
                    mapOptionList = null;
                } else if (iLA == 15) {
                    match(15);
                    mapOptionList = optionList();
                    this.astFactory.addASTChild(aSTPair, this.returnAST);
                } else {
                    throw new NoViableAltException(LT(1), getFilename());
                }
                stringTemplateAST = (StringTemplateAST) aSTPair.root;
                map = mapOptionList;
            } else {
                match(18);
                match(16);
                ifCondition();
                this.astFactory.addASTChild(aSTPair, this.returnAST);
                match(17);
                stringTemplateAST = (StringTemplateAST) aSTPair.root;
            }
            mapOptionList = map;
            stringTemplateAST2 = stringTemplateAST;
        } catch (RecognitionException e2) {
            e = e2;
            mapOptionList = null;
        }
        this.returnAST = stringTemplateAST2;
        return mapOptionList;
    }

    public final void templatesExpr() throws Throwable {
        StringTemplateAST stringTemplateAST;
        StringTemplateAST stringTemplateAST2 = null;
        this.returnAST = null;
        ASTPair aSTPair = new ASTPair();
        try {
            boolean z = false;
            if (_tokenSet_1.member(LA(1)) && _tokenSet_2.member(LA(2))) {
                int iMark = mark();
                this.inputState.guessing++;
                try {
                    parallelArrayTemplateApplication();
                    z = true;
                } catch (RecognitionException unused) {
                }
                rewind(iMark);
                this.inputState.guessing--;
            }
            if (z) {
                parallelArrayTemplateApplication();
                this.astFactory.addASTChild(aSTPair, this.returnAST);
                stringTemplateAST = (StringTemplateAST) aSTPair.root;
            } else if (_tokenSet_1.member(LA(1)) && _tokenSet_3.member(LA(2))) {
                expr();
                this.astFactory.addASTChild(aSTPair, this.returnAST);
                while (LA(1) == 22) {
                    StringTemplateAST stringTemplateAST3 = (StringTemplateAST) this.astFactory.create(LT(1));
                    this.astFactory.makeASTRoot(aSTPair, stringTemplateAST3);
                    match(22);
                    if (this.inputState.guessing == 0) {
                        stringTemplateAST3.setType(4);
                    }
                    template();
                    this.astFactory.addASTChild(aSTPair, this.returnAST);
                    while (LA(1) == 19) {
                        match(19);
                        template();
                        this.astFactory.addASTChild(aSTPair, this.returnAST);
                    }
                }
                stringTemplateAST = (StringTemplateAST) aSTPair.root;
            } else {
                throw new NoViableAltException(LT(1), getFilename());
            }
            stringTemplateAST2 = stringTemplateAST;
        } catch (RecognitionException e) {
            if (this.inputState.guessing == 0) {
                reportError(e);
                recover(e, _tokenSet_4);
            } else {
                throw e;
            }
        }
        this.returnAST = stringTemplateAST2;
    }

    public final Map optionList() throws Throwable {
        HashMap map = new HashMap();
        this.returnAST = null;
        new ASTPair();
        try {
            option(map);
            while (LA(1) == 19) {
                match(19);
                option(map);
            }
        } catch (RecognitionException e) {
            if (this.inputState.guessing == 0) {
                reportError(e);
                recover(e, _tokenSet_0);
            } else {
                throw e;
            }
        }
        this.returnAST = null;
        return map;
    }

    /* JADX WARN: Code duplicated, block: B:13:0x004d A[Catch: RecognitionException -> 0x005d, TRY_LEAVE, TryCatch #0 {RecognitionException -> 0x005d, blocks: (B:3:0x0009, B:9:0x0019, B:10:0x001c, B:11:0x0029, B:12:0x002a, B:13:0x004d), top: B:22:0x0009 }] */
    public final void ifCondition() throws Throwable {
        StringTemplateAST stringTemplateAST;
        StringTemplateAST stringTemplateAST2 = null;
        this.returnAST = null;
        ASTPair aSTPair = new ASTPair();
        try {
            int iLA = LA(1);
            if (iLA == 16 || iLA == 20) {
                ifAtom();
                this.astFactory.addASTChild(aSTPair, this.returnAST);
                stringTemplateAST = (StringTemplateAST) aSTPair.root;
            } else if (iLA != 23) {
                switch (iLA) {
                    case 26:
                    case 27:
                    case 28:
                    case 29:
                    case 30:
                    case 31:
                    case 32:
                    case 33:
                    case 34:
                    case 35:
                    case 36:
                        ifAtom();
                        this.astFactory.addASTChild(aSTPair, this.returnAST);
                        stringTemplateAST = (StringTemplateAST) aSTPair.root;
                        break;
                    default:
                        throw new NoViableAltException(LT(1), getFilename());
                }
            } else {
                this.astFactory.makeASTRoot(aSTPair, (StringTemplateAST) this.astFactory.create(LT(1)));
                match(23);
                ifAtom();
                this.astFactory.addASTChild(aSTPair, this.returnAST);
                stringTemplateAST = (StringTemplateAST) aSTPair.root;
            }
            stringTemplateAST2 = stringTemplateAST;
        } catch (RecognitionException e) {
            if (this.inputState.guessing == 0) {
                reportError(e);
                recover(e, _tokenSet_5);
            } else {
                throw e;
            }
        }
        this.returnAST = stringTemplateAST2;
    }

    /* JADX WARN: Code duplicated, block: B:17:0x006d  */
    public final void option(Map map) throws Throwable {
        Object obj;
        StringTemplateAST stringTemplateAST = null;
        this.returnAST = null;
        ASTPair aSTPair = new ASTPair();
        try {
            StringTemplateAST stringTemplateAST2 = (StringTemplateAST) this.astFactory.create(LT(1));
            this.astFactory.addASTChild(aSTPair, stringTemplateAST2);
            match(20);
            int iLA = LA(1);
            if (iLA == 1 || iLA == 19) {
                if (this.inputState.guessing == 0) {
                    obj = ASTExpr.EMPTY_OPTION;
                } else {
                    obj = null;
                }
            } else if (iLA == 21) {
                this.astFactory.addASTChild(aSTPair, (StringTemplateAST) this.astFactory.create(LT(1)));
                match(21);
                nonAlternatingTemplateExpr();
                obj = (StringTemplateAST) this.returnAST;
                this.astFactory.addASTChild(aSTPair, this.returnAST);
                if (this.inputState.guessing != 0) {
                    obj = null;
                }
            } else {
                throw new NoViableAltException(LT(1), getFilename());
            }
            if (this.inputState.guessing == 0) {
                map.put(stringTemplateAST2.getText(), obj);
            }
            stringTemplateAST = (StringTemplateAST) aSTPair.root;
        } catch (RecognitionException e) {
            if (this.inputState.guessing == 0) {
                reportError(e);
                recover(e, _tokenSet_6);
            } else {
                throw e;
            }
        }
        this.returnAST = stringTemplateAST;
    }

    public final void nonAlternatingTemplateExpr() throws Throwable {
        StringTemplateAST stringTemplateAST = null;
        this.returnAST = null;
        ASTPair aSTPair = new ASTPair();
        try {
            expr();
            this.astFactory.addASTChild(aSTPair, this.returnAST);
            while (LA(1) == 22) {
                StringTemplateAST stringTemplateAST2 = (StringTemplateAST) this.astFactory.create(LT(1));
                this.astFactory.makeASTRoot(aSTPair, stringTemplateAST2);
                match(22);
                if (this.inputState.guessing == 0) {
                    stringTemplateAST2.setType(4);
                }
                template();
                this.astFactory.addASTChild(aSTPair, this.returnAST);
            }
            stringTemplateAST = (StringTemplateAST) aSTPair.root;
        } catch (RecognitionException e) {
            if (this.inputState.guessing == 0) {
                reportError(e);
                recover(e, _tokenSet_7);
            } else {
                throw e;
            }
        }
        this.returnAST = stringTemplateAST;
    }

    public final void parallelArrayTemplateApplication() throws Throwable {
        StringTemplateAST stringTemplateAST;
        StringTemplateAST stringTemplateAST2 = null;
        this.returnAST = null;
        ASTPair aSTPair = new ASTPair();
        try {
            expr();
            this.astFactory.addASTChild(aSTPair, this.returnAST);
            int i = 0;
            while (LA(1) == 19) {
                match(19);
                expr();
                this.astFactory.addASTChild(aSTPair, this.returnAST);
                i++;
            }
            if (i < 1) {
                throw new NoViableAltException(LT(1), getFilename());
            }
            this.astFactory.addASTChild(aSTPair, (StringTemplateAST) this.astFactory.create(LT(1)));
            match(22);
            anonymousTemplate();
            this.astFactory.addASTChild(aSTPair, this.returnAST);
            if (this.inputState.guessing == 0) {
                StringTemplateAST stringTemplateAST3 = (StringTemplateAST) aSTPair.root;
                try {
                    StringTemplateAST stringTemplateAST4 = (StringTemplateAST) this.astFactory.make(new ASTArray(2).add((StringTemplateAST) this.astFactory.create(5, "MULTI_APPLY")).add(stringTemplateAST3));
                    aSTPair.root = stringTemplateAST4;
                    aSTPair.child = (stringTemplateAST4 == null || stringTemplateAST4.getFirstChild() == null) ? stringTemplateAST4 : stringTemplateAST4.getFirstChild();
                    aSTPair.advanceChildToEnd();
                } catch (RecognitionException e) {
                    e = e;
                    stringTemplateAST2 = stringTemplateAST3;
                    if (this.inputState.guessing == 0) {
                        reportError(e);
                        recover(e, _tokenSet_4);
                        stringTemplateAST = stringTemplateAST2;
                    } else {
                        throw e;
                    }
                }
            }
            stringTemplateAST = (StringTemplateAST) aSTPair.root;
            this.returnAST = stringTemplateAST;
        } catch (RecognitionException e2) {
            e = e2;
        }
    }

    public final void expr() throws Throwable {
        StringTemplateAST stringTemplateAST = null;
        this.returnAST = null;
        ASTPair aSTPair = new ASTPair();
        try {
            primaryExpr();
            this.astFactory.addASTChild(aSTPair, this.returnAST);
            while (LA(1) == 24) {
                this.astFactory.makeASTRoot(aSTPair, (StringTemplateAST) this.astFactory.create(LT(1)));
                match(24);
                primaryExpr();
                this.astFactory.addASTChild(aSTPair, this.returnAST);
            }
            stringTemplateAST = (StringTemplateAST) aSTPair.root;
        } catch (RecognitionException e) {
            if (this.inputState.guessing == 0) {
                reportError(e);
                recover(e, _tokenSet_8);
            } else {
                throw e;
            }
        }
        this.returnAST = stringTemplateAST;
    }

    public final void template() throws Throwable {
        StringTemplateAST stringTemplateAST;
        StringTemplateAST stringTemplateAST2 = null;
        this.returnAST = null;
        ASTPair aSTPair = new ASTPair();
        try {
            int iLA = LA(1);
            if (iLA == 16 || iLA == 20 || iLA == 32) {
                namedTemplate();
                this.astFactory.addASTChild(aSTPair, this.returnAST);
            } else if (iLA == 33) {
                anonymousTemplate();
                this.astFactory.addASTChild(aSTPair, this.returnAST);
            } else {
                throw new NoViableAltException(LT(1), getFilename());
            }
            if (this.inputState.guessing == 0) {
                StringTemplateAST stringTemplateAST3 = (StringTemplateAST) aSTPair.root;
                try {
                    StringTemplateAST stringTemplateAST4 = (StringTemplateAST) this.astFactory.make(new ASTArray(2).add((StringTemplateAST) this.astFactory.create(10)).add(stringTemplateAST3));
                    aSTPair.root = stringTemplateAST4;
                    aSTPair.child = (stringTemplateAST4 == null || stringTemplateAST4.getFirstChild() == null) ? stringTemplateAST4 : stringTemplateAST4.getFirstChild();
                    aSTPair.advanceChildToEnd();
                } catch (RecognitionException e) {
                    e = e;
                    stringTemplateAST2 = stringTemplateAST3;
                    if (this.inputState.guessing == 0) {
                        reportError(e);
                        recover(e, _tokenSet_8);
                        stringTemplateAST = stringTemplateAST2;
                    } else {
                        throw e;
                    }
                }
            }
            stringTemplateAST = (StringTemplateAST) aSTPair.root;
        } catch (RecognitionException e2) {
            e = e2;
        }
        this.returnAST = stringTemplateAST;
    }

    public final void anonymousTemplate() throws Throwable {
        StringTemplateAST stringTemplateAST = null;
        this.returnAST = null;
        ASTPair aSTPair = new ASTPair();
        try {
            Token tokenLT = LT(1);
            StringTemplateAST stringTemplateAST2 = (StringTemplateAST) this.astFactory.create(tokenLT);
            this.astFactory.addASTChild(aSTPair, stringTemplateAST2);
            match(33);
            if (this.inputState.guessing == 0) {
                StringTemplate stringTemplate = new StringTemplate();
                stringTemplate.setGroup(this.self.getGroup());
                stringTemplate.setEnclosingInstance(this.self);
                stringTemplate.setTemplate(tokenLT.getText());
                stringTemplate.defineFormalArguments(((StringTemplateToken) tokenLT).args);
                stringTemplateAST2.setStringTemplate(stringTemplate);
            }
            stringTemplateAST = (StringTemplateAST) aSTPair.root;
        } catch (RecognitionException e) {
            if (this.inputState.guessing == 0) {
                reportError(e);
                recover(e, _tokenSet_8);
            } else {
                throw e;
            }
        }
        this.returnAST = stringTemplateAST;
    }

    public final void ifAtom() throws Throwable {
        StringTemplateAST stringTemplateAST = null;
        this.returnAST = null;
        ASTPair aSTPair = new ASTPair();
        try {
            templatesExpr();
            this.astFactory.addASTChild(aSTPair, this.returnAST);
            stringTemplateAST = (StringTemplateAST) aSTPair.root;
        } catch (RecognitionException e) {
            if (this.inputState.guessing == 0) {
                reportError(e);
                recover(e, _tokenSet_5);
            } else {
                throw e;
            }
        }
        this.returnAST = stringTemplateAST;
    }

    public final void primaryExpr() throws Throwable {
        StringTemplateAST stringTemplateAST;
        StringTemplateAST stringTemplateAST2 = null;
        this.returnAST = null;
        ASTPair aSTPair = new ASTPair();
        try {
            int iLA = LA(1);
            if (iLA != 36) {
                switch (iLA) {
                    case 26:
                    case 27:
                    case 28:
                    case 29:
                    case 30:
                    case 31:
                        function();
                        this.astFactory.addASTChild(aSTPair, this.returnAST);
                        while (LA(1) == 25) {
                            this.astFactory.makeASTRoot(aSTPair, (StringTemplateAST) this.astFactory.create(LT(1)));
                            match(25);
                            int iLA2 = LA(1);
                            if (iLA2 == 16) {
                                valueExpr();
                                this.astFactory.addASTChild(aSTPair, this.returnAST);
                            } else if (iLA2 == 20) {
                                this.astFactory.addASTChild(aSTPair, (StringTemplateAST) this.astFactory.create(LT(1)));
                                match(20);
                            } else {
                                throw new NoViableAltException(LT(1), getFilename());
                            }
                        }
                        stringTemplateAST = (StringTemplateAST) aSTPair.root;
                        break;
                    default:
                        boolean z = false;
                        if ((LA(1) == 16 || LA(1) == 20 || LA(1) == 32) && _tokenSet_9.member(LA(2))) {
                            int iMark = mark();
                            this.inputState.guessing++;
                            try {
                                templateInclude();
                                z = true;
                            } catch (RecognitionException unused) {
                            }
                            rewind(iMark);
                            this.inputState.guessing--;
                        }
                        if (z) {
                            templateInclude();
                            this.astFactory.addASTChild(aSTPair, this.returnAST);
                            stringTemplateAST = (StringTemplateAST) aSTPair.root;
                        } else if (_tokenSet_10.member(LA(1)) && _tokenSet_11.member(LA(2))) {
                            atom();
                            this.astFactory.addASTChild(aSTPair, this.returnAST);
                            while (LA(1) == 25) {
                                this.astFactory.makeASTRoot(aSTPair, (StringTemplateAST) this.astFactory.create(LT(1)));
                                match(25);
                                int iLA3 = LA(1);
                                if (iLA3 == 16) {
                                    valueExpr();
                                    this.astFactory.addASTChild(aSTPair, this.returnAST);
                                } else if (iLA3 == 20) {
                                    this.astFactory.addASTChild(aSTPair, (StringTemplateAST) this.astFactory.create(LT(1)));
                                    match(20);
                                } else {
                                    throw new NoViableAltException(LT(1), getFilename());
                                }
                            }
                            stringTemplateAST = (StringTemplateAST) aSTPair.root;
                        } else if (LA(1) == 16 && _tokenSet_1.member(LA(2))) {
                            valueExpr();
                            this.astFactory.addASTChild(aSTPair, this.returnAST);
                            stringTemplateAST = (StringTemplateAST) aSTPair.root;
                        } else {
                            throw new NoViableAltException(LT(1), getFilename());
                        }
                        break;
                }
            } else {
                list();
                this.astFactory.addASTChild(aSTPair, this.returnAST);
                stringTemplateAST = (StringTemplateAST) aSTPair.root;
            }
            stringTemplateAST2 = stringTemplateAST;
        } catch (RecognitionException e) {
            if (this.inputState.guessing == 0) {
                reportError(e);
                recover(e, _tokenSet_12);
            } else {
                throw e;
            }
        }
        this.returnAST = stringTemplateAST2;
    }

    public final void templateInclude() throws Throwable {
        StringTemplateAST stringTemplateAST;
        StringTemplateAST stringTemplateAST2 = null;
        this.returnAST = null;
        ASTPair aSTPair = new ASTPair();
        try {
            int iLA = LA(1);
            if (iLA == 16) {
                indirectTemplate();
                this.astFactory.addASTChild(aSTPair, this.returnAST);
            } else if (iLA == 20) {
                this.astFactory.addASTChild(aSTPair, (StringTemplateAST) this.astFactory.create(LT(1)));
                match(20);
                argList();
                this.astFactory.addASTChild(aSTPair, this.returnAST);
            } else if (iLA == 32) {
                match(32);
                match(25);
                StringTemplateAST stringTemplateAST3 = (StringTemplateAST) this.astFactory.create(LT(1));
                this.astFactory.addASTChild(aSTPair, stringTemplateAST3);
                match(20);
                if (this.inputState.guessing == 0) {
                    stringTemplateAST3.setText(new StringBuffer("super.").append(stringTemplateAST3.getText()).toString());
                }
                argList();
                this.astFactory.addASTChild(aSTPair, this.returnAST);
            } else {
                throw new NoViableAltException(LT(1), getFilename());
            }
            if (this.inputState.guessing == 0) {
                StringTemplateAST stringTemplateAST4 = (StringTemplateAST) aSTPair.root;
                try {
                    StringTemplateAST stringTemplateAST5 = (StringTemplateAST) this.astFactory.make(new ASTArray(2).add((StringTemplateAST) this.astFactory.create(7, "include")).add(stringTemplateAST4));
                    aSTPair.root = stringTemplateAST5;
                    aSTPair.child = (stringTemplateAST5 == null || stringTemplateAST5.getFirstChild() == null) ? stringTemplateAST5 : stringTemplateAST5.getFirstChild();
                    aSTPair.advanceChildToEnd();
                } catch (RecognitionException e) {
                    stringTemplateAST2 = stringTemplateAST4;
                    e = e;
                    if (this.inputState.guessing == 0) {
                        reportError(e);
                        recover(e, _tokenSet_12);
                        stringTemplateAST = stringTemplateAST2;
                    } else {
                        throw e;
                    }
                }
            }
            stringTemplateAST = (StringTemplateAST) aSTPair.root;
        } catch (RecognitionException e2) {
            e = e2;
        }
        this.returnAST = stringTemplateAST;
    }

    public final void atom() throws RecognitionException, TokenStreamException {
        StringTemplateAST stringTemplateAST;
        StringTemplateAST stringTemplateAST2 = null;
        this.returnAST = null;
        ASTPair aSTPair = new ASTPair();
        try {
            int iLA = LA(1);
            if (iLA == 20) {
                this.astFactory.addASTChild(aSTPair, (StringTemplateAST) this.astFactory.create(LT(1)));
                match(20);
                stringTemplateAST = (StringTemplateAST) aSTPair.root;
            } else {
                switch (iLA) {
                    case 33:
                        this.astFactory.addASTChild(aSTPair, (StringTemplateAST) this.astFactory.create(LT(1)));
                        match(33);
                        stringTemplateAST = (StringTemplateAST) aSTPair.root;
                        break;
                    case 34:
                        this.astFactory.addASTChild(aSTPair, (StringTemplateAST) this.astFactory.create(LT(1)));
                        match(34);
                        stringTemplateAST = (StringTemplateAST) aSTPair.root;
                        break;
                    case 35:
                        this.astFactory.addASTChild(aSTPair, (StringTemplateAST) this.astFactory.create(LT(1)));
                        match(35);
                        stringTemplateAST = (StringTemplateAST) aSTPair.root;
                        break;
                    default:
                        throw new NoViableAltException(LT(1), getFilename());
                }
            }
            stringTemplateAST2 = stringTemplateAST;
        } catch (RecognitionException e) {
            if (this.inputState.guessing == 0) {
                reportError(e);
                recover(e, _tokenSet_11);
            } else {
                throw e;
            }
        }
        this.returnAST = stringTemplateAST2;
    }

    public final void valueExpr() throws Throwable {
        StringTemplateAST stringTemplateAST = null;
        this.returnAST = null;
        ASTPair aSTPair = new ASTPair();
        try {
            StringTemplateAST stringTemplateAST2 = (StringTemplateAST) this.astFactory.create(LT(1));
            this.astFactory.makeASTRoot(aSTPair, stringTemplateAST2);
            match(16);
            templatesExpr();
            this.astFactory.addASTChild(aSTPair, this.returnAST);
            match(17);
            if (this.inputState.guessing == 0) {
                stringTemplateAST2.setType(9);
                stringTemplateAST2.setText("value");
            }
            stringTemplateAST = (StringTemplateAST) aSTPair.root;
        } catch (RecognitionException e) {
            if (this.inputState.guessing == 0) {
                reportError(e);
                recover(e, _tokenSet_11);
            } else {
                throw e;
            }
        }
        this.returnAST = stringTemplateAST;
    }

    public final void function() throws RecognitionException, TokenStreamException {
        StringTemplateAST stringTemplateAST;
        StringTemplateAST stringTemplateAST2 = null;
        this.returnAST = null;
        ASTPair aSTPair = new ASTPair();
        try {
            switch (LA(1)) {
                case 26:
                    this.astFactory.addASTChild(aSTPair, (StringTemplateAST) this.astFactory.create(LT(1)));
                    match(26);
                    break;
                case 27:
                    this.astFactory.addASTChild(aSTPair, (StringTemplateAST) this.astFactory.create(LT(1)));
                    match(27);
                    break;
                case 28:
                    this.astFactory.addASTChild(aSTPair, (StringTemplateAST) this.astFactory.create(LT(1)));
                    match(28);
                    break;
                case 29:
                    this.astFactory.addASTChild(aSTPair, (StringTemplateAST) this.astFactory.create(LT(1)));
                    match(29);
                    break;
                case 30:
                    this.astFactory.addASTChild(aSTPair, (StringTemplateAST) this.astFactory.create(LT(1)));
                    match(30);
                    break;
                case 31:
                    this.astFactory.addASTChild(aSTPair, (StringTemplateAST) this.astFactory.create(LT(1)));
                    match(31);
                    break;
                default:
                    throw new NoViableAltException(LT(1), getFilename());
            }
            singleArg();
            this.astFactory.addASTChild(aSTPair, this.returnAST);
            if (this.inputState.guessing == 0) {
                StringTemplateAST stringTemplateAST3 = (StringTemplateAST) aSTPair.root;
                try {
                    StringTemplateAST stringTemplateAST4 = (StringTemplateAST) this.astFactory.make(new ASTArray(2).add((StringTemplateAST) this.astFactory.create(11)).add(stringTemplateAST3));
                    aSTPair.root = stringTemplateAST4;
                    aSTPair.child = (stringTemplateAST4 == null || stringTemplateAST4.getFirstChild() == null) ? stringTemplateAST4 : stringTemplateAST4.getFirstChild();
                    aSTPair.advanceChildToEnd();
                } catch (RecognitionException e) {
                    e = e;
                    stringTemplateAST2 = stringTemplateAST3;
                    if (this.inputState.guessing == 0) {
                        reportError(e);
                        recover(e, _tokenSet_11);
                        stringTemplateAST = stringTemplateAST2;
                    } else {
                        throw e;
                    }
                }
            }
            stringTemplateAST = (StringTemplateAST) aSTPair.root;
        } catch (RecognitionException e2) {
            e = e2;
        }
        this.returnAST = stringTemplateAST;
    }

    public final void list() throws RecognitionException, TokenStreamException {
        StringTemplateAST stringTemplateAST = null;
        this.returnAST = null;
        ASTPair aSTPair = new ASTPair();
        try {
            StringTemplateAST stringTemplateAST2 = (StringTemplateAST) this.astFactory.create(LT(1));
            this.astFactory.makeASTRoot(aSTPair, stringTemplateAST2);
            match(36);
            if (this.inputState.guessing == 0) {
                stringTemplateAST2.setType(13);
                stringTemplateAST2.setText("value");
            }
            listElement();
            this.astFactory.addASTChild(aSTPair, this.returnAST);
            while (LA(1) == 19) {
                match(19);
                listElement();
                this.astFactory.addASTChild(aSTPair, this.returnAST);
            }
            match(37);
            stringTemplateAST = (StringTemplateAST) aSTPair.root;
        } catch (RecognitionException e) {
            if (this.inputState.guessing == 0) {
                reportError(e);
                recover(e, _tokenSet_12);
            } else {
                throw e;
            }
        }
        this.returnAST = stringTemplateAST;
    }

    public final void singleArg() throws Throwable {
        StringTemplateAST stringTemplateAST;
        StringTemplateAST stringTemplateAST2 = null;
        this.returnAST = null;
        ASTPair aSTPair = new ASTPair();
        try {
            match(16);
            nonAlternatingTemplateExpr();
            this.astFactory.addASTChild(aSTPair, this.returnAST);
            match(17);
            if (this.inputState.guessing == 0) {
                StringTemplateAST stringTemplateAST3 = (StringTemplateAST) aSTPair.root;
                try {
                    stringTemplateAST2 = (StringTemplateAST) this.astFactory.make(new ASTArray(2).add((StringTemplateAST) this.astFactory.create(12, "SINGLEVALUEARG")).add(stringTemplateAST3));
                    aSTPair.root = stringTemplateAST2;
                    aSTPair.child = (stringTemplateAST2 == null || stringTemplateAST2.getFirstChild() == null) ? stringTemplateAST2 : stringTemplateAST2.getFirstChild();
                    aSTPair.advanceChildToEnd();
                } catch (RecognitionException e) {
                    e = e;
                    stringTemplateAST2 = stringTemplateAST3;
                    if (this.inputState.guessing == 0) {
                        reportError(e);
                        recover(e, _tokenSet_11);
                        stringTemplateAST = stringTemplateAST2;
                    } else {
                        throw e;
                    }
                }
            }
            stringTemplateAST = (StringTemplateAST) aSTPair.root;
        } catch (RecognitionException e2) {
            e = e2;
        }
        this.returnAST = stringTemplateAST;
    }

    public final void namedTemplate() throws RecognitionException, TokenStreamException {
        StringTemplateAST stringTemplateAST;
        StringTemplateAST stringTemplateAST2 = null;
        this.returnAST = null;
        ASTPair aSTPair = new ASTPair();
        try {
            int iLA = LA(1);
            if (iLA == 16) {
                indirectTemplate();
                this.astFactory.addASTChild(aSTPair, this.returnAST);
                stringTemplateAST = (StringTemplateAST) aSTPair.root;
            } else if (iLA == 20) {
                this.astFactory.addASTChild(aSTPair, (StringTemplateAST) this.astFactory.create(LT(1)));
                match(20);
                argList();
                this.astFactory.addASTChild(aSTPair, this.returnAST);
                stringTemplateAST = (StringTemplateAST) aSTPair.root;
            } else if (iLA == 32) {
                match(32);
                match(25);
                StringTemplateAST stringTemplateAST3 = (StringTemplateAST) this.astFactory.create(LT(1));
                this.astFactory.addASTChild(aSTPair, stringTemplateAST3);
                match(20);
                if (this.inputState.guessing == 0) {
                    stringTemplateAST3.setText(new StringBuffer("super.").append(stringTemplateAST3.getText()).toString());
                }
                argList();
                this.astFactory.addASTChild(aSTPair, this.returnAST);
                stringTemplateAST = (StringTemplateAST) aSTPair.root;
            } else {
                throw new NoViableAltException(LT(1), getFilename());
            }
            stringTemplateAST2 = stringTemplateAST;
        } catch (RecognitionException e) {
            if (this.inputState.guessing == 0) {
                reportError(e);
                recover(e, _tokenSet_8);
            } else {
                throw e;
            }
        }
        this.returnAST = stringTemplateAST2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v1 */
    /* JADX WARN: Type inference failed for: r0v11 */
    /* JADX WARN: Type inference failed for: r0v12 */
    /* JADX WARN: Type inference failed for: r0v13 */
    /* JADX WARN: Type inference failed for: r0v2, types: [antlr.collections.AST] */
    /* JADX WARN: Type inference failed for: r0v3 */
    /* JADX WARN: Type inference failed for: r0v7 */
    public final void argList() throws Throwable {
        StringTemplateAST stringTemplateAST;
        ?? r0 = 0;
        ?? r1 = 0;
        this.returnAST = null;
        ASTPair aSTPair = new ASTPair();
        try {
            try {
                if (LA(1) == 16 && LA(2) == 17) {
                    match(16);
                    match(17);
                    if (this.inputState.guessing == 0) {
                        StringTemplateAST stringTemplateAST2 = (StringTemplateAST) this.astFactory.create(6, "ARGS");
                        aSTPair.root = stringTemplateAST2;
                        aSTPair.child = (stringTemplateAST2 == null || stringTemplateAST2.getFirstChild() == null) ? stringTemplateAST2 : stringTemplateAST2.getFirstChild();
                        aSTPair.advanceChildToEnd();
                        r1 = stringTemplateAST2;
                    }
                } else {
                    boolean z = false;
                    if (LA(1) == 16 && _tokenSet_1.member(LA(2))) {
                        int iMark = mark();
                        this.inputState.guessing++;
                        try {
                            singleArg();
                            z = true;
                        } catch (RecognitionException unused) {
                        }
                        rewind(iMark);
                        this.inputState.guessing--;
                    }
                    if (z) {
                        singleArg();
                        this.astFactory.addASTChild(aSTPair, this.returnAST);
                        stringTemplateAST = (StringTemplateAST) aSTPair.root;
                    } else if (LA(1) == 16 && (LA(2) == 20 || LA(2) == 38)) {
                        match(16);
                        argumentAssignment();
                        this.astFactory.addASTChild(aSTPair, this.returnAST);
                        while (LA(1) == 19) {
                            match(19);
                            argumentAssignment();
                            this.astFactory.addASTChild(aSTPair, this.returnAST);
                        }
                        match(17);
                        if (this.inputState.guessing == 0) {
                            StringTemplateAST stringTemplateAST3 = (StringTemplateAST) this.astFactory.make(new ASTArray(2).add((StringTemplateAST) this.astFactory.create(6, "ARGS")).add((StringTemplateAST) aSTPair.root));
                            aSTPair.root = stringTemplateAST3;
                            aSTPair.child = (stringTemplateAST3 == null || stringTemplateAST3.getFirstChild() == null) ? stringTemplateAST3 : stringTemplateAST3.getFirstChild();
                            aSTPair.advanceChildToEnd();
                        }
                        stringTemplateAST = (StringTemplateAST) aSTPair.root;
                    } else {
                        throw new NoViableAltException(LT(1), getFilename());
                    }
                    r1 = stringTemplateAST;
                }
            } catch (RecognitionException e) {
                e = e;
                r0 = 1;
                if (this.inputState.guessing == 0) {
                    reportError(e);
                    recover(e, _tokenSet_12);
                    r1 = r0;
                } else {
                    throw e;
                }
            }
        } catch (RecognitionException e2) {
            e = e2;
        }
        this.returnAST = r1;
    }

    public final void indirectTemplate() throws Throwable {
        StringTemplateAST stringTemplateAST = null;
        this.returnAST = null;
        ASTPair aSTPair = new ASTPair();
        try {
            match(16);
            templatesExpr();
            StringTemplateAST stringTemplateAST2 = (StringTemplateAST) this.returnAST;
            match(17);
            argList();
            StringTemplateAST stringTemplateAST3 = (StringTemplateAST) this.returnAST;
            if (this.inputState.guessing == 0) {
                StringTemplateAST stringTemplateAST4 = (StringTemplateAST) aSTPair.root;
                try {
                    stringTemplateAST = (StringTemplateAST) this.astFactory.make(new ASTArray(3).add((StringTemplateAST) this.astFactory.create(9, "value")).add(stringTemplateAST2).add(stringTemplateAST3));
                    aSTPair.root = stringTemplateAST;
                    aSTPair.child = (stringTemplateAST == null || stringTemplateAST.getFirstChild() == null) ? stringTemplateAST : stringTemplateAST.getFirstChild();
                    aSTPair.advanceChildToEnd();
                } catch (RecognitionException e) {
                    e = e;
                    stringTemplateAST = stringTemplateAST4;
                    if (this.inputState.guessing == 0) {
                        reportError(e);
                        recover(e, _tokenSet_12);
                    } else {
                        throw e;
                    }
                }
            }
        } catch (RecognitionException e2) {
            e = e2;
        }
        this.returnAST = stringTemplateAST;
    }

    public final void listElement() throws RecognitionException, TokenStreamException {
        StringTemplateAST stringTemplateAST;
        StringTemplateAST stringTemplateAST2 = null;
        this.returnAST = null;
        ASTPair aSTPair = new ASTPair();
        try {
            int iLA = LA(1);
            if (iLA == 16) {
                nonAlternatingTemplateExpr();
                this.astFactory.addASTChild(aSTPair, this.returnAST);
                stringTemplateAST = (StringTemplateAST) aSTPair.root;
            } else {
                if (iLA != 19) {
                    if (iLA != 20) {
                        switch (iLA) {
                            case 26:
                            case 27:
                            case 28:
                            case 29:
                            case 30:
                            case 31:
                            case 32:
                            case 33:
                            case 34:
                            case 35:
                            case 36:
                                break;
                            case 37:
                                break;
                            default:
                                throw new NoViableAltException(LT(1), getFilename());
                        }
                    }
                    nonAlternatingTemplateExpr();
                    this.astFactory.addASTChild(aSTPair, this.returnAST);
                    stringTemplateAST = (StringTemplateAST) aSTPair.root;
                }
                if (this.inputState.guessing == 0) {
                    StringTemplateAST stringTemplateAST3 = (StringTemplateAST) aSTPair.root;
                    try {
                        StringTemplateAST stringTemplateAST4 = (StringTemplateAST) this.astFactory.create(14, "NOTHING");
                        aSTPair.root = stringTemplateAST4;
                        aSTPair.child = (stringTemplateAST4 == null || stringTemplateAST4.getFirstChild() == null) ? stringTemplateAST4 : stringTemplateAST4.getFirstChild();
                        aSTPair.advanceChildToEnd();
                    } catch (RecognitionException e) {
                        e = e;
                        stringTemplateAST2 = stringTemplateAST3;
                        if (this.inputState.guessing == 0) {
                            reportError(e);
                            recover(e, _tokenSet_13);
                            stringTemplateAST = stringTemplateAST2;
                        } else {
                            throw e;
                        }
                    }
                }
                stringTemplateAST = (StringTemplateAST) aSTPair.root;
            }
        } catch (RecognitionException e2) {
            e = e2;
        }
        this.returnAST = stringTemplateAST;
    }

    public final void argumentAssignment() throws Throwable {
        StringTemplateAST stringTemplateAST;
        StringTemplateAST stringTemplateAST2 = null;
        this.returnAST = null;
        ASTPair aSTPair = new ASTPair();
        try {
            int iLA = LA(1);
            if (iLA == 20) {
                this.astFactory.addASTChild(aSTPair, (StringTemplateAST) this.astFactory.create(LT(1)));
                match(20);
                this.astFactory.makeASTRoot(aSTPair, (StringTemplateAST) this.astFactory.create(LT(1)));
                match(21);
                nonAlternatingTemplateExpr();
                this.astFactory.addASTChild(aSTPair, this.returnAST);
                stringTemplateAST = (StringTemplateAST) aSTPair.root;
            } else if (iLA == 38) {
                this.astFactory.addASTChild(aSTPair, (StringTemplateAST) this.astFactory.create(LT(1)));
                match(38);
                stringTemplateAST = (StringTemplateAST) aSTPair.root;
            } else {
                throw new NoViableAltException(LT(1), getFilename());
            }
            stringTemplateAST2 = stringTemplateAST;
        } catch (RecognitionException e) {
            if (this.inputState.guessing == 0) {
                reportError(e);
                recover(e, _tokenSet_14);
            } else {
                throw e;
            }
        }
        this.returnAST = stringTemplateAST2;
    }

    protected void buildTokenTypeASTClassMap() {
        this.tokenTypeToASTClassMap = null;
    }

    private static final long[] mk_tokenSet_0() {
        return new long[]{2, 0};
    }

    private static final long[] mk_tokenSet_1() {
        return new long[]{137372958720L, 0};
    }

    private static final long[] mk_tokenSet_2() {
        return new long[]{274862768128L, 0};
    }

    private static final long[] mk_tokenSet_3() {
        return new long[]{274867126274L, 0};
    }

    private static final long[] mk_tokenSet_4() {
        return new long[]{163842, 0};
    }

    private static final long[] mk_tokenSet_5() {
        return new long[]{131072, 0};
    }

    private static final long[] mk_tokenSet_6() {
        return new long[]{524290, 0};
    }

    private static final long[] mk_tokenSet_7() {
        return new long[]{137439608834L, 0};
    }

    private static final long[] mk_tokenSet_8() {
        return new long[]{137443835906L, 0};
    }

    private static final long[] mk_tokenSet_9() {
        return new long[]{137406513152L, 0};
    }

    private static final long[] mk_tokenSet_10() {
        return new long[]{60130590720L, 0};
    }

    private static final long[] mk_tokenSet_11() {
        return new long[]{137494167554L, 0};
    }

    private static final long[] mk_tokenSet_12() {
        return new long[]{137460613122L, 0};
    }

    private static final long[] mk_tokenSet_13() {
        return new long[]{137439477760L, 0};
    }

    private static final long[] mk_tokenSet_14() {
        return new long[]{655360, 0};
    }
}
