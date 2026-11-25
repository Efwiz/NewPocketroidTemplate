package antlr;

import antlr.actions.java.ActionLexer;
import antlr.collections.impl.BitSet;
import antlr.collections.impl.Vector;
import com.huawei.hms.framework.common.ContainerUtils;
import com.huawei.hms.ml.language.common.utils.Constant;
import com.huawei.hms.ml.language.common.utils.LanguageCodeUtil;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import name.antonsmirnov.firmata.FormatHelper;
import org.antlr.runtime.BaseRecognizer;
import org.apache.commons.lang3.SystemProperties;
import org.catrobat.catroid.common.Constants;
import org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;

/* JADX INFO: loaded from: classes.dex */
public class JavaCodeGenerator extends CodeGenerator {
    public static final int CONTINUE_LAST_MAPPING = -888;
    protected static final String NONUNIQUE = new String();
    public static final int NO_MAPPING = -999;
    public static final int caseSizeThreshold = 127;
    String commonExtraArgs;
    String commonExtraParams;
    String commonLocalVars;
    String currentASTResult;
    RuleBlock currentRule;
    String exceptionThrown;
    String labeledElementASTType;
    String labeledElementInit;
    String labeledElementType;
    String lt1Value;
    private JavaCodeGeneratorPrintWriterManager printWriterManager;
    private Vector semPreds;
    String throwNoViable;
    private int defaultLine = -999;
    protected int syntacticPredLevel = 0;
    protected boolean genAST = false;
    protected boolean saveText = false;
    Hashtable treeVariableMap = new Hashtable();
    Hashtable declaredASTVariables = new Hashtable();
    int astVarNumber = 1;

    protected void genMatch(BitSet bitSet) {
    }

    public JavaCodeGenerator() {
        this.charFormatter = new JavaCharFormatter();
    }

    @Override // antlr.CodeGenerator
    protected void printAction(String str) {
        printAction(str, this.defaultLine);
    }

    protected void printAction(String str, int i) {
        getPrintWriterManager().startMapping(i);
        super.printAction(str);
        getPrintWriterManager().endMapping();
    }

    @Override // antlr.CodeGenerator
    public void println(String str) {
        println(str, this.defaultLine);
    }

    public void println(String str, int i) {
        if (i > 0 || i == -888) {
            getPrintWriterManager().startSingleSourceLineMapping(i);
        }
        super.println(str);
        if (i > 0 || i == -888) {
            getPrintWriterManager().endMapping();
        }
    }

    @Override // antlr.CodeGenerator
    protected void print(String str) {
        print(str, this.defaultLine);
    }

    protected void print(String str, int i) {
        if (i > 0 || i == -888) {
            getPrintWriterManager().startMapping(i);
        }
        super.print(str);
        if (i > 0 || i == -888) {
            getPrintWriterManager().endMapping();
        }
    }

    @Override // antlr.CodeGenerator
    protected void _print(String str) {
        _print(str, this.defaultLine);
    }

    protected void _print(String str, int i) {
        if (i > 0 || i == -888) {
            getPrintWriterManager().startMapping(i);
        }
        super._print(str);
        if (i > 0 || i == -888) {
            getPrintWriterManager().endMapping();
        }
    }

    @Override // antlr.CodeGenerator
    protected void _println(String str) {
        _println(str, this.defaultLine);
    }

    protected void _println(String str, int i) {
        if (i > 0 || i == -888) {
            getPrintWriterManager().startMapping(i);
        }
        super._println(str);
        if (i > 0 || i == -888) {
            getPrintWriterManager().endMapping();
        }
    }

    protected int addSemPred(String str) {
        this.semPreds.appendElement(str);
        return this.semPreds.size() - 1;
    }

    public void exitIfError() {
        if (this.antlrTool.hasError()) {
            this.antlrTool.fatalError("Exiting due to errors.");
        }
    }

    @Override // antlr.CodeGenerator
    public void gen() {
        try {
            Enumeration enumerationElements = this.behavior.grammars.elements();
            while (enumerationElements.hasMoreElements()) {
                Grammar grammar = (Grammar) enumerationElements.nextElement();
                grammar.setGrammarAnalyzer(this.analyzer);
                grammar.setCodeGenerator(this);
                this.analyzer.setGrammar(grammar);
                setupGrammarParameters(grammar);
                grammar.generate();
                exitIfError();
            }
            Enumeration enumerationElements2 = this.behavior.tokenManagers.elements();
            while (enumerationElements2.hasMoreElements()) {
                TokenManager tokenManager = (TokenManager) enumerationElements2.nextElement();
                if (!tokenManager.isReadOnly()) {
                    genTokenTypes(tokenManager);
                    genTokenInterchange(tokenManager);
                }
                exitIfError();
            }
        } catch (IOException e) {
            this.antlrTool.reportException(e, null);
        }
    }

    @Override // antlr.CodeGenerator
    public void gen(ActionElement actionElement) {
        int i = this.defaultLine;
        try {
            this.defaultLine = actionElement.getLine();
            if (this.DEBUG_CODE_GENERATOR) {
                System.out.println(new StringBuffer("genAction(").append(actionElement).append(Constant.AFTER_QUTO).toString());
            }
            if (actionElement.isSemPred) {
                genSemPred(actionElement.actionText, actionElement.line);
            } else {
                if (this.grammar.hasSyntacticPredicate) {
                    println("if ( inputState.guessing==0 ) {");
                    this.tabs++;
                }
                ActionTransInfo actionTransInfo = new ActionTransInfo();
                String strProcessActionForSpecialSymbols = processActionForSpecialSymbols(actionElement.actionText, actionElement.getLine(), this.currentRule, actionTransInfo);
                if (actionTransInfo.refRuleRoot != null) {
                    println(new StringBuffer().append(actionTransInfo.refRuleRoot).append(" = (").append(this.labeledElementASTType).append(")currentAST.root;").toString());
                }
                printAction(strProcessActionForSpecialSymbols);
                if (actionTransInfo.assignToRoot) {
                    println(new StringBuffer("currentAST.root = ").append(actionTransInfo.refRuleRoot).append(";").toString());
                    println(new StringBuffer("currentAST.child = ").append(actionTransInfo.refRuleRoot).append("!=null &&").append(actionTransInfo.refRuleRoot).append(".getFirstChild()!=null ?").toString(), -999);
                    this.tabs++;
                    println(new StringBuffer().append(actionTransInfo.refRuleRoot).append(".getFirstChild() : ").append(actionTransInfo.refRuleRoot).append(";").toString());
                    this.tabs--;
                    println("currentAST.advanceChildToEnd();");
                }
                if (this.grammar.hasSyntacticPredicate) {
                    this.tabs--;
                    println("}", -999);
                }
            }
        } finally {
            this.defaultLine = i;
        }
    }

    @Override // antlr.CodeGenerator
    public void gen(AlternativeBlock alternativeBlock) {
        if (this.DEBUG_CODE_GENERATOR) {
            System.out.println(new StringBuffer("gen(").append(alternativeBlock).append(Constant.AFTER_QUTO).toString());
        }
        println("{", -999);
        genBlockPreamble(alternativeBlock);
        genBlockInitAction(alternativeBlock);
        String str = this.currentASTResult;
        if (alternativeBlock.getLabel() != null) {
            this.currentASTResult = alternativeBlock.getLabel();
        }
        this.grammar.theLLkAnalyzer.deterministic(alternativeBlock);
        genBlockFinish(genCommonBlock(alternativeBlock, true), this.throwNoViable, alternativeBlock.getLine());
        println("}", -999);
        this.currentASTResult = str;
    }

    @Override // antlr.CodeGenerator
    public void gen(BlockEndElement blockEndElement) {
        if (this.DEBUG_CODE_GENERATOR) {
            System.out.println(new StringBuffer("genRuleEnd(").append(blockEndElement).append(Constant.AFTER_QUTO).toString());
        }
    }

    /* JADX WARN: Code duplicated, block: B:13:0x005a  */
    @Override // antlr.CodeGenerator
    public void gen(CharLiteralElement charLiteralElement) {
        boolean z;
        if (this.DEBUG_CODE_GENERATOR) {
            System.out.println(new StringBuffer("genChar(").append(charLiteralElement).append(Constant.AFTER_QUTO).toString());
        }
        if (charLiteralElement.getLabel() != null) {
            println(new StringBuffer().append(charLiteralElement.getLabel()).append(" = ").append(this.lt1Value).append(";").toString(), charLiteralElement.getLine());
        }
        boolean z2 = this.saveText;
        if (z2) {
            z = charLiteralElement.getAutoGenType() == 1;
        }
        this.saveText = z;
        genMatch(charLiteralElement);
        this.saveText = z2;
    }

    @Override // antlr.CodeGenerator
    public void gen(CharRangeElement charRangeElement) {
        int i = this.defaultLine;
        try {
            this.defaultLine = charRangeElement.getLine();
            if (charRangeElement.getLabel() != null && this.syntacticPredLevel == 0) {
                println(new StringBuffer().append(charRangeElement.getLabel()).append(" = ").append(this.lt1Value).append(";").toString());
            }
            boolean z = (this.grammar instanceof LexerGrammar) && (!this.saveText || charRangeElement.getAutoGenType() == 3);
            if (z) {
                println("_saveIndex=text.length();");
            }
            println(new StringBuffer("matchRange(").append(charRangeElement.beginText).append(",").append(charRangeElement.endText).append(");").toString());
            if (z) {
                println("text.setLength(_saveIndex);");
            }
        } finally {
            this.defaultLine = i;
        }
    }

    @Override // antlr.CodeGenerator
    public void gen(LexerGrammar lexerGrammar) throws Throwable {
        int i;
        String strStripFrontBack;
        String strStripFrontBack2;
        int i2 = this.defaultLine;
        try {
            this.defaultLine = -999;
            if (lexerGrammar.debuggingOutput) {
                this.semPreds = new Vector();
            }
            setGrammar(lexerGrammar);
            if (!(this.grammar instanceof LexerGrammar)) {
                this.antlrTool.panic("Internal error generating lexer");
            }
            try {
                this.currentOutput = getPrintWriterManager().setupOutput(this.antlrTool, this.grammar);
                this.genAST = false;
                this.saveText = true;
                this.tabs = 0;
                genHeader();
                try {
                    this.defaultLine = this.behavior.getHeaderActionLine("");
                    println(this.behavior.getHeaderAction(""));
                    this.defaultLine = -999;
                    println("import java.io.InputStream;");
                    println("import antlr.TokenStreamException;");
                    println("import antlr.TokenStreamIOException;");
                    println("import antlr.TokenStreamRecognitionException;");
                    println("import antlr.CharStreamException;");
                    println("import antlr.CharStreamIOException;");
                    println("import antlr.ANTLRException;");
                    println("import java.io.Reader;");
                    println("import java.util.Hashtable;");
                    println(new StringBuffer("import antlr.").append(this.grammar.getSuperClass()).append(";").toString());
                    println("import antlr.InputBuffer;");
                    println("import antlr.ByteBuffer;");
                    println("import antlr.CharBuffer;");
                    println("import antlr.Token;");
                    println("import antlr.CommonToken;");
                    println("import antlr.RecognitionException;");
                    println("import antlr.NoViableAltForCharException;");
                    println("import antlr.MismatchedCharException;");
                    println("import antlr.TokenStream;");
                    println("import antlr.ANTLRHashString;");
                    println("import antlr.LexerSharedInputState;");
                    println("import antlr.collections.impl.BitSet;");
                    println("import antlr.SemanticException;");
                    println(this.grammar.preambleAction.getText());
                    String string = this.grammar.superClass != null ? this.grammar.superClass : new StringBuffer("antlr.").append(this.grammar.getSuperClass()).toString();
                    if (this.grammar.comment != null) {
                        _println(this.grammar.comment);
                    }
                    String str = "public";
                    Token token = (Token) this.grammar.options.get("classHeaderPrefix");
                    if (token != null && (strStripFrontBack2 = StringUtils.stripFrontBack(token.getText(), "\"", "\"")) != null) {
                        str = strStripFrontBack2;
                    }
                    print(new StringBuffer().append(str).append(" ").toString());
                    print(new StringBuffer(ExternalAnnotationProvider.CLASS_PREFIX).append(this.grammar.getClassName()).append(" extends ").append(string).toString());
                    println(new StringBuffer(" implements ").append(this.grammar.tokenManager.getName()).append(TokenTypesFileSuffix).append(", TokenStream").toString());
                    Token token2 = (Token) this.grammar.options.get("classHeaderSuffix");
                    if (token2 != null && (strStripFrontBack = StringUtils.stripFrontBack(token2.getText(), "\"", "\"")) != null) {
                        print(new StringBuffer(", ").append(strStripFrontBack).toString());
                    }
                    println(" {");
                    print(processActionForSpecialSymbols(this.grammar.classMemberAction.getText(), this.grammar.classMemberAction.getLine(), this.currentRule, null), this.grammar.classMemberAction.getLine());
                    println(new StringBuffer("public ").append(this.grammar.getClassName()).append("(InputStream in) {").toString());
                    this.tabs++;
                    println("this(new ByteBuffer(in));");
                    this.tabs--;
                    println("}");
                    println(new StringBuffer("public ").append(this.grammar.getClassName()).append("(Reader in) {").toString());
                    this.tabs++;
                    println("this(new CharBuffer(in));");
                    this.tabs--;
                    println("}");
                    println(new StringBuffer("public ").append(this.grammar.getClassName()).append("(InputBuffer ib) {").toString());
                    this.tabs++;
                    if (this.grammar.debuggingOutput) {
                        println("this(new LexerSharedInputState(new antlr.debug.DebuggingInputBuffer(ib)));");
                    } else {
                        println("this(new LexerSharedInputState(ib));");
                    }
                    this.tabs--;
                    println("}");
                    println(new StringBuffer("public ").append(this.grammar.getClassName()).append("(LexerSharedInputState state) {").toString());
                    this.tabs++;
                    println("super(state);");
                    if (this.grammar.debuggingOutput) {
                        println("  ruleNames  = _ruleNames;");
                        println("  semPredNames = _semPredNames;");
                        println("  setupDebugging();");
                    }
                    println(new StringBuffer("caseSensitiveLiterals = ").append(lexerGrammar.caseSensitiveLiterals).append(";").toString());
                    println(new StringBuffer("setCaseSensitive(").append(lexerGrammar.caseSensitive).append(");").toString());
                    println("literals = new Hashtable();");
                    Enumeration tokenSymbolKeys = this.grammar.tokenManager.getTokenSymbolKeys();
                    while (tokenSymbolKeys.hasMoreElements()) {
                        String str2 = (String) tokenSymbolKeys.nextElement();
                        if (str2.charAt(0) == '\"') {
                            TokenSymbol tokenSymbol = this.grammar.tokenManager.getTokenSymbol(str2);
                            if (tokenSymbol instanceof StringLiteralSymbol) {
                                StringLiteralSymbol stringLiteralSymbol = (StringLiteralSymbol) tokenSymbol;
                                println(new StringBuffer().append("literals.put(new ANTLRHashString(").append(stringLiteralSymbol.getId()).append(", this), new Integer(").append(stringLiteralSymbol.getTokenType()).append("));").toString());
                            }
                        }
                    }
                    this.tabs--;
                    println("}");
                    if (this.grammar.debuggingOutput) {
                        println("private static final String _ruleNames[] = {");
                        Enumeration enumerationElements = this.grammar.rules.elements();
                        while (enumerationElements.hasMoreElements()) {
                            GrammarSymbol grammarSymbol = (GrammarSymbol) enumerationElements.nextElement();
                            if (grammarSymbol instanceof RuleSymbol) {
                                println(new StringBuffer().append("  \"").append(((RuleSymbol) grammarSymbol).getId()).append("\",").toString());
                            }
                        }
                        println("};");
                    }
                    genNextToken();
                    Enumeration enumerationElements2 = this.grammar.rules.elements();
                    int i3 = 0;
                    while (enumerationElements2.hasMoreElements()) {
                        RuleSymbol ruleSymbol = (RuleSymbol) enumerationElements2.nextElement();
                        if (!ruleSymbol.getId().equals("mnextToken")) {
                            genRule(ruleSymbol, false, i3);
                            i3++;
                        }
                        exitIfError();
                    }
                    if (this.grammar.debuggingOutput) {
                        genSemPredMap();
                    }
                    genBitsets(this.bitsetsUsed, ((LexerGrammar) this.grammar).charVocabulary.size());
                    println("");
                    println("}");
                    getPrintWriterManager().finishOutput();
                    this.defaultLine = i2;
                } catch (Throwable th) {
                    i = i2;
                    try {
                        this.defaultLine = -999;
                        throw th;
                    } catch (Throwable th2) {
                        th = th2;
                        this.defaultLine = i;
                        throw th;
                    }
                }
            } catch (Throwable th3) {
                th = th3;
                i = i2;
            }
        } catch (Throwable th4) {
            th = th4;
            i = i2;
        }
    }

    /* JADX WARN: Code duplicated, block: B:33:0x0101 A[Catch: all -> 0x01ab, TRY_ENTER, TryCatch #0 {all -> 0x01ab, blocks: (B:3:0x0012, B:5:0x001c, B:6:0x0034, B:8:0x0045, B:10:0x0066, B:12:0x0082, B:14:0x0096, B:16:0x00c2, B:17:0x00c8, B:19:0x00d8, B:21:0x00e0, B:23:0x00ec, B:33:0x0101, B:35:0x0105, B:36:0x0119, B:37:0x0150, B:24:0x00ef, B:26:0x00f3, B:13:0x0087, B:9:0x0057), top: B:43:0x0012 }] */
    /* JADX WARN: Code duplicated, block: B:35:0x0105 A[Catch: all -> 0x01ab, TryCatch #0 {all -> 0x01ab, blocks: (B:3:0x0012, B:5:0x001c, B:6:0x0034, B:8:0x0045, B:10:0x0066, B:12:0x0082, B:14:0x0096, B:16:0x00c2, B:17:0x00c8, B:19:0x00d8, B:21:0x00e0, B:23:0x00ec, B:33:0x0101, B:35:0x0105, B:36:0x0119, B:37:0x0150, B:24:0x00ef, B:26:0x00f3, B:13:0x0087, B:9:0x0057), top: B:43:0x0012 }] */
    @Override // antlr.CodeGenerator
    public void gen(OneOrMoreBlock oneOrMoreBlock) {
        boolean z;
        int i = this.defaultLine;
        try {
            this.defaultLine = oneOrMoreBlock.getLine();
            if (this.DEBUG_CODE_GENERATOR) {
                System.out.println(new StringBuffer("gen+(").append(oneOrMoreBlock).append(Constant.AFTER_QUTO).toString());
            }
            println("{", -999);
            genBlockPreamble(oneOrMoreBlock);
            String string = oneOrMoreBlock.getLabel() != null ? new StringBuffer("_cnt_").append(oneOrMoreBlock.getLabel()).toString() : new StringBuffer("_cnt").append(oneOrMoreBlock.ID).toString();
            println(new StringBuffer("int ").append(string).append("=0;").toString());
            String label = oneOrMoreBlock.getLabel() != null ? oneOrMoreBlock.getLabel() : new StringBuffer("_loop").append(oneOrMoreBlock.ID).toString();
            println(new StringBuffer().append(label).append(":").toString());
            println("do {");
            this.tabs++;
            genBlockInitAction(oneOrMoreBlock);
            String str = this.currentASTResult;
            if (oneOrMoreBlock.getLabel() != null) {
                this.currentASTResult = oneOrMoreBlock.getLabel();
            }
            this.grammar.theLLkAnalyzer.deterministic(oneOrMoreBlock);
            int i2 = this.grammar.maxk;
            if (oneOrMoreBlock.greedy || oneOrMoreBlock.exitLookaheadDepth > this.grammar.maxk || !oneOrMoreBlock.exitCache[oneOrMoreBlock.exitLookaheadDepth].containsEpsilon()) {
                if (oneOrMoreBlock.greedy || oneOrMoreBlock.exitLookaheadDepth != Integer.MAX_VALUE) {
                    z = false;
                }
                if (z) {
                    if (this.DEBUG_CODE_GENERATOR) {
                        System.out.println(new StringBuffer("nongreedy (...)+ loop; exit depth is ").append(oneOrMoreBlock.exitLookaheadDepth).toString());
                    }
                    String lookaheadTestExpression = getLookaheadTestExpression(oneOrMoreBlock.exitCache, i2);
                    println("// nongreedy exit test", -999);
                    println(new StringBuffer("if ( ").append(string).append(">=1 && ").append(lookaheadTestExpression).append(") break ").append(label).append(";").toString(), CONTINUE_LAST_MAPPING);
                }
                genBlockFinish(genCommonBlock(oneOrMoreBlock, false), new StringBuffer("if ( ").append(string).append(">=1 ) { break ").append(label).append("; } else {").append(this.throwNoViable).append("}").toString(), oneOrMoreBlock.getLine());
                println(new StringBuffer().append(string).append("++;").toString());
                this.tabs--;
                println("} while (true);");
                println("}");
                this.currentASTResult = str;
            }
            i2 = oneOrMoreBlock.exitLookaheadDepth;
            z = true;
            if (z) {
                if (this.DEBUG_CODE_GENERATOR) {
                    System.out.println(new StringBuffer("nongreedy (...)+ loop; exit depth is ").append(oneOrMoreBlock.exitLookaheadDepth).toString());
                }
                String lookaheadTestExpression2 = getLookaheadTestExpression(oneOrMoreBlock.exitCache, i2);
                println("// nongreedy exit test", -999);
                println(new StringBuffer("if ( ").append(string).append(">=1 && ").append(lookaheadTestExpression2).append(") break ").append(label).append(";").toString(), CONTINUE_LAST_MAPPING);
            }
            genBlockFinish(genCommonBlock(oneOrMoreBlock, false), new StringBuffer("if ( ").append(string).append(">=1 ) { break ").append(label).append("; } else {").append(this.throwNoViable).append("}").toString(), oneOrMoreBlock.getLine());
            println(new StringBuffer().append(string).append("++;").toString());
            this.tabs--;
            println("} while (true);");
            println("}");
            this.currentASTResult = str;
        } finally {
            this.defaultLine = i;
        }
    }

    @Override // antlr.CodeGenerator
    public void gen(ParserGrammar parserGrammar) throws IOException {
        String strStripFrontBack;
        String strStripFrontBack2;
        int i = this.defaultLine;
        try {
            this.defaultLine = -999;
            if (parserGrammar.debuggingOutput) {
                this.semPreds = new Vector();
            }
            setGrammar(parserGrammar);
            if (!(this.grammar instanceof ParserGrammar)) {
                this.antlrTool.panic("Internal error generating parser");
            }
            this.currentOutput = getPrintWriterManager().setupOutput(this.antlrTool, this.grammar);
            this.genAST = this.grammar.buildAST;
            this.tabs = 0;
            genHeader();
            try {
                this.defaultLine = this.behavior.getHeaderActionLine("");
                println(this.behavior.getHeaderAction(""));
                this.defaultLine = -999;
                println("import antlr.TokenBuffer;");
                println("import antlr.TokenStreamException;");
                println("import antlr.TokenStreamIOException;");
                println("import antlr.ANTLRException;");
                println(new StringBuffer("import antlr.").append(this.grammar.getSuperClass()).append(";").toString());
                println("import antlr.Token;");
                println("import antlr.TokenStream;");
                println("import antlr.RecognitionException;");
                println("import antlr.NoViableAltException;");
                println("import antlr.MismatchedTokenException;");
                println("import antlr.SemanticException;");
                println("import antlr.ParserSharedInputState;");
                println("import antlr.collections.impl.BitSet;");
                if (this.genAST) {
                    println("import antlr.collections.AST;");
                    println("import java.util.Hashtable;");
                    println("import antlr.ASTFactory;");
                    println("import antlr.ASTPair;");
                    println("import antlr.collections.impl.ASTArray;");
                }
                println(this.grammar.preambleAction.getText());
                String string = this.grammar.superClass != null ? this.grammar.superClass : new StringBuffer("antlr.").append(this.grammar.getSuperClass()).toString();
                if (this.grammar.comment != null) {
                    _println(this.grammar.comment);
                }
                String str = "public";
                Token token = (Token) this.grammar.options.get("classHeaderPrefix");
                if (token != null && (strStripFrontBack2 = StringUtils.stripFrontBack(token.getText(), "\"", "\"")) != null) {
                    str = strStripFrontBack2;
                }
                print(new StringBuffer().append(str).append(" ").toString());
                print(new StringBuffer(ExternalAnnotationProvider.CLASS_PREFIX).append(this.grammar.getClassName()).append(" extends ").append(string).toString());
                println(new StringBuffer("       implements ").append(this.grammar.tokenManager.getName()).append(TokenTypesFileSuffix).toString());
                Token token2 = (Token) this.grammar.options.get("classHeaderSuffix");
                if (token2 != null && (strStripFrontBack = StringUtils.stripFrontBack(token2.getText(), "\"", "\"")) != null) {
                    print(new StringBuffer(", ").append(strStripFrontBack).toString());
                }
                println(" {");
                if (this.grammar.debuggingOutput) {
                    println("private static final String _ruleNames[] = {");
                    Enumeration enumerationElements = this.grammar.rules.elements();
                    while (enumerationElements.hasMoreElements()) {
                        GrammarSymbol grammarSymbol = (GrammarSymbol) enumerationElements.nextElement();
                        if (grammarSymbol instanceof RuleSymbol) {
                            println(new StringBuffer().append("  \"").append(((RuleSymbol) grammarSymbol).getId()).append("\",").toString());
                        }
                    }
                    println("};");
                }
                print(processActionForSpecialSymbols(this.grammar.classMemberAction.getText(), this.grammar.classMemberAction.getLine(), this.currentRule, null), this.grammar.classMemberAction.getLine());
                println("");
                println(new StringBuffer().append("protected ").append(this.grammar.getClassName()).append("(TokenBuffer tokenBuf, int k) {").toString());
                println("  super(tokenBuf,k);");
                println("  tokenNames = _tokenNames;");
                if (this.grammar.debuggingOutput) {
                    println("  ruleNames  = _ruleNames;");
                    println("  semPredNames = _semPredNames;");
                    println("  setupDebugging(tokenBuf);");
                }
                if (this.grammar.buildAST) {
                    println("  buildTokenTypeASTClassMap();");
                    println("  astFactory = new ASTFactory(getTokenTypeToASTClassMap());");
                }
                println("}");
                println("");
                println(new StringBuffer().append("public ").append(this.grammar.getClassName()).append("(TokenBuffer tokenBuf) {").toString());
                println(new StringBuffer().append("  this(tokenBuf,").append(this.grammar.maxk).append(");").toString());
                println("}");
                println("");
                println(new StringBuffer().append("protected ").append(this.grammar.getClassName()).append("(TokenStream lexer, int k) {").toString());
                println("  super(lexer,k);");
                println("  tokenNames = _tokenNames;");
                if (this.grammar.debuggingOutput) {
                    println("  ruleNames  = _ruleNames;");
                    println("  semPredNames = _semPredNames;");
                    println("  setupDebugging(lexer);");
                }
                if (this.grammar.buildAST) {
                    println("  buildTokenTypeASTClassMap();");
                    println("  astFactory = new ASTFactory(getTokenTypeToASTClassMap());");
                }
                println("}");
                println("");
                println(new StringBuffer().append("public ").append(this.grammar.getClassName()).append("(TokenStream lexer) {").toString());
                println(new StringBuffer().append("  this(lexer,").append(this.grammar.maxk).append(");").toString());
                println("}");
                println("");
                println(new StringBuffer().append("public ").append(this.grammar.getClassName()).append("(ParserSharedInputState state) {").toString());
                println(new StringBuffer().append("  super(state,").append(this.grammar.maxk).append(");").toString());
                println("  tokenNames = _tokenNames;");
                if (this.grammar.buildAST) {
                    println("  buildTokenTypeASTClassMap();");
                    println("  astFactory = new ASTFactory(getTokenTypeToASTClassMap());");
                }
                println("}");
                println("");
                Enumeration enumerationElements2 = this.grammar.rules.elements();
                int i2 = 0;
                while (enumerationElements2.hasMoreElements()) {
                    GrammarSymbol grammarSymbol2 = (GrammarSymbol) enumerationElements2.nextElement();
                    if (grammarSymbol2 instanceof RuleSymbol) {
                        RuleSymbol ruleSymbol = (RuleSymbol) grammarSymbol2;
                        genRule(ruleSymbol, ruleSymbol.references.size() == 0, i2);
                        i2++;
                    }
                    exitIfError();
                }
                genTokenStrings();
                if (this.grammar.buildAST) {
                    genTokenASTNodeMap();
                }
                genBitsets(this.bitsetsUsed, this.grammar.tokenManager.maxTokenType());
                if (this.grammar.debuggingOutput) {
                    genSemPredMap();
                }
                println("");
                println("}");
                getPrintWriterManager().finishOutput();
                this.defaultLine = i;
            } catch (Throwable th) {
                this.defaultLine = -999;
                throw th;
            }
        } catch (Throwable th2) {
            this.defaultLine = i;
            throw th2;
        }
    }

    @Override // antlr.CodeGenerator
    public void gen(RuleRefElement ruleRefElement) {
        int i = this.defaultLine;
        try {
            this.defaultLine = ruleRefElement.getLine();
            if (this.DEBUG_CODE_GENERATOR) {
                System.out.println(new StringBuffer("genRR(").append(ruleRefElement).append(Constant.AFTER_QUTO).toString());
            }
            RuleSymbol ruleSymbol = (RuleSymbol) this.grammar.getSymbol(ruleRefElement.targetRule);
            if (ruleSymbol != null && ruleSymbol.isDefined()) {
                if (!(ruleSymbol instanceof RuleSymbol)) {
                    this.antlrTool.error(new StringBuffer(FormatHelper.QUOTE).append(ruleRefElement.targetRule).append("' does not name a grammar rule").toString(), this.grammar.getFilename(), ruleRefElement.getLine(), ruleRefElement.getColumn());
                    return;
                }
                genErrorTryForElement(ruleRefElement);
                if ((this.grammar instanceof TreeWalkerGrammar) && ruleRefElement.getLabel() != null && this.syntacticPredLevel == 0) {
                    println(new StringBuffer().append(ruleRefElement.getLabel()).append(" = _t==ASTNULL ? null : ").append(this.lt1Value).append(";").toString());
                }
                if ((this.grammar instanceof LexerGrammar) && (!this.saveText || ruleRefElement.getAutoGenType() == 3)) {
                    println("_saveIndex=text.length();");
                }
                printTabs();
                if (ruleRefElement.idAssign != null) {
                    if (ruleSymbol.block.returnAction == null) {
                        this.antlrTool.warning(new StringBuffer("Rule '").append(ruleRefElement.targetRule).append("' has no return type").toString(), this.grammar.getFilename(), ruleRefElement.getLine(), ruleRefElement.getColumn());
                    }
                    _print(new StringBuffer().append(ruleRefElement.idAssign).append(ContainerUtils.KEY_VALUE_DELIMITER).toString());
                } else if (!(this.grammar instanceof LexerGrammar) && this.syntacticPredLevel == 0 && ruleSymbol.block.returnAction != null) {
                    this.antlrTool.warning(new StringBuffer("Rule '").append(ruleRefElement.targetRule).append("' returns a value").toString(), this.grammar.getFilename(), ruleRefElement.getLine(), ruleRefElement.getColumn());
                }
                GenRuleInvocation(ruleRefElement);
                if ((this.grammar instanceof LexerGrammar) && (!this.saveText || ruleRefElement.getAutoGenType() == 3)) {
                    println("text.setLength(_saveIndex);");
                }
                if (this.syntacticPredLevel == 0) {
                    if (this.grammar.hasSyntacticPredicate && ((!this.grammar.buildAST || ruleRefElement.getLabel() == null) && this.genAST)) {
                        ruleRefElement.getAutoGenType();
                    }
                    if (this.grammar.buildAST && ruleRefElement.getLabel() != null) {
                        println(new StringBuffer().append(ruleRefElement.getLabel()).append("_AST = (").append(this.labeledElementASTType).append(")returnAST;").toString());
                    }
                    if (this.genAST) {
                        int autoGenType = ruleRefElement.getAutoGenType();
                        if (autoGenType == 1) {
                            println("astFactory.addASTChild(currentAST, returnAST);");
                        } else if (autoGenType == 2) {
                            this.antlrTool.error("Internal: encountered ^ after rule reference");
                        }
                    }
                    if ((this.grammar instanceof LexerGrammar) && ruleRefElement.getLabel() != null) {
                        println(new StringBuffer().append(ruleRefElement.getLabel()).append("=_returnToken;").toString());
                    }
                }
                genErrorCatchForElement(ruleRefElement);
                return;
            }
            this.antlrTool.error(new StringBuffer("Rule '").append(ruleRefElement.targetRule).append("' is not defined").toString(), this.grammar.getFilename(), ruleRefElement.getLine(), ruleRefElement.getColumn());
        } finally {
            this.defaultLine = i;
        }
    }

    /* JADX WARN: Code duplicated, block: B:15:0x0061  */
    @Override // antlr.CodeGenerator
    public void gen(StringLiteralElement stringLiteralElement) {
        boolean z;
        if (this.DEBUG_CODE_GENERATOR) {
            System.out.println(new StringBuffer("genString(").append(stringLiteralElement).append(Constant.AFTER_QUTO).toString());
        }
        if (stringLiteralElement.getLabel() != null && this.syntacticPredLevel == 0) {
            println(new StringBuffer().append(stringLiteralElement.getLabel()).append(" = ").append(this.lt1Value).append(";").toString(), stringLiteralElement.getLine());
        }
        genElementAST(stringLiteralElement);
        boolean z2 = this.saveText;
        if (z2) {
            z = stringLiteralElement.getAutoGenType() == 1;
        }
        this.saveText = z;
        genMatch(stringLiteralElement);
        this.saveText = z2;
        if (this.grammar instanceof TreeWalkerGrammar) {
            println("_t = _t.getNextSibling();", stringLiteralElement.getLine());
        }
    }

    @Override // antlr.CodeGenerator
    public void gen(TokenRangeElement tokenRangeElement) {
        genErrorTryForElement(tokenRangeElement);
        if (tokenRangeElement.getLabel() != null && this.syntacticPredLevel == 0) {
            println(new StringBuffer().append(tokenRangeElement.getLabel()).append(" = ").append(this.lt1Value).append(";").toString(), tokenRangeElement.getLine());
        }
        genElementAST(tokenRangeElement);
        println(new StringBuffer("matchRange(").append(tokenRangeElement.beginText).append(",").append(tokenRangeElement.endText).append(");").toString(), tokenRangeElement.getLine());
        genErrorCatchForElement(tokenRangeElement);
    }

    @Override // antlr.CodeGenerator
    public void gen(TokenRefElement tokenRefElement) {
        if (this.DEBUG_CODE_GENERATOR) {
            System.out.println(new StringBuffer("genTokenRef(").append(tokenRefElement).append(Constant.AFTER_QUTO).toString());
        }
        if (this.grammar instanceof LexerGrammar) {
            this.antlrTool.panic("Token reference found in lexer");
        }
        genErrorTryForElement(tokenRefElement);
        if (tokenRefElement.getLabel() != null && this.syntacticPredLevel == 0) {
            println(new StringBuffer().append(tokenRefElement.getLabel()).append(" = ").append(this.lt1Value).append(";").toString(), tokenRefElement.getLine());
        }
        genElementAST(tokenRefElement);
        genMatch(tokenRefElement);
        genErrorCatchForElement(tokenRefElement);
        if (this.grammar instanceof TreeWalkerGrammar) {
            println("_t = _t.getNextSibling();", tokenRefElement.getLine());
        }
    }

    @Override // antlr.CodeGenerator
    public void gen(TreeElement treeElement) {
        int i = this.defaultLine;
        try {
            this.defaultLine = treeElement.getLine();
            println(new StringBuffer("AST __t").append(treeElement.ID).append(" = _t;").toString());
            if (treeElement.root.getLabel() != null) {
                println(new StringBuffer().append(treeElement.root.getLabel()).append(" = _t==ASTNULL ? null :(").append(this.labeledElementASTType).append(")_t;").toString(), treeElement.root.getLine());
            }
            if (treeElement.root.getAutoGenType() == 3) {
                this.antlrTool.error("Suffixing a root node with '!' is not implemented", this.grammar.getFilename(), treeElement.getLine(), treeElement.getColumn());
                treeElement.root.setAutoGenType(1);
            }
            if (treeElement.root.getAutoGenType() == 2) {
                this.antlrTool.warning("Suffixing a root node with '^' is redundant; already a root", this.grammar.getFilename(), treeElement.getLine(), treeElement.getColumn());
                treeElement.root.setAutoGenType(1);
            }
            genElementAST(treeElement.root);
            if (this.grammar.buildAST) {
                println(new StringBuffer("ASTPair __currentAST").append(treeElement.ID).append(" = currentAST.copy();").toString());
                println("currentAST.root = currentAST.child;");
                println("currentAST.child = null;");
            }
            if (treeElement.root instanceof WildcardElement) {
                println("if ( _t==null ) throw new MismatchedTokenException();", treeElement.root.getLine());
            } else {
                genMatch(treeElement.root);
            }
            println("_t = _t.getFirstChild();");
            for (int i2 = 0; i2 < treeElement.getAlternatives().size(); i2++) {
                for (AlternativeElement alternativeElement = treeElement.getAlternativeAt(i2).head; alternativeElement != null; alternativeElement = alternativeElement.next) {
                    alternativeElement.generate();
                }
            }
            if (this.grammar.buildAST) {
                println(new StringBuffer().append("currentAST = __currentAST").append(treeElement.ID).append(";").toString());
            }
            println(new StringBuffer().append("_t = __t").append(treeElement.ID).append(";").toString());
            println("_t = _t.getNextSibling();");
        } finally {
            this.defaultLine = i;
        }
    }

    @Override // antlr.CodeGenerator
    public void gen(TreeWalkerGrammar treeWalkerGrammar) throws IOException {
        String strStripFrontBack;
        String strStripFrontBack2;
        int i = this.defaultLine;
        try {
            this.defaultLine = -999;
            setGrammar(treeWalkerGrammar);
            if (!(this.grammar instanceof TreeWalkerGrammar)) {
                this.antlrTool.panic("Internal error generating tree-walker");
            }
            this.currentOutput = getPrintWriterManager().setupOutput(this.antlrTool, this.grammar);
            this.genAST = this.grammar.buildAST;
            this.tabs = 0;
            genHeader();
            try {
                this.defaultLine = this.behavior.getHeaderActionLine("");
                println(this.behavior.getHeaderAction(""));
                this.defaultLine = -999;
                println(new StringBuffer("import antlr.").append(this.grammar.getSuperClass()).append(";").toString());
                println("import antlr.Token;");
                println("import antlr.collections.AST;");
                println("import antlr.RecognitionException;");
                println("import antlr.ANTLRException;");
                println("import antlr.NoViableAltException;");
                println("import antlr.MismatchedTokenException;");
                println("import antlr.SemanticException;");
                println("import antlr.collections.impl.BitSet;");
                println("import antlr.ASTPair;");
                println("import antlr.collections.impl.ASTArray;");
                println(this.grammar.preambleAction.getText());
                String string = this.grammar.superClass != null ? this.grammar.superClass : new StringBuffer("antlr.").append(this.grammar.getSuperClass()).toString();
                println("");
                if (this.grammar.comment != null) {
                    _println(this.grammar.comment);
                }
                String str = "public";
                Token token = (Token) this.grammar.options.get("classHeaderPrefix");
                if (token != null && (strStripFrontBack2 = StringUtils.stripFrontBack(token.getText(), "\"", "\"")) != null) {
                    str = strStripFrontBack2;
                }
                print(new StringBuffer().append(str).append(" ").toString());
                print(new StringBuffer(ExternalAnnotationProvider.CLASS_PREFIX).append(this.grammar.getClassName()).append(" extends ").append(string).toString());
                println(new StringBuffer("       implements ").append(this.grammar.tokenManager.getName()).append(TokenTypesFileSuffix).toString());
                Token token2 = (Token) this.grammar.options.get("classHeaderSuffix");
                if (token2 != null && (strStripFrontBack = StringUtils.stripFrontBack(token2.getText(), "\"", "\"")) != null) {
                    print(new StringBuffer(", ").append(strStripFrontBack).toString());
                }
                println(" {");
                print(processActionForSpecialSymbols(this.grammar.classMemberAction.getText(), this.grammar.classMemberAction.getLine(), this.currentRule, null), this.grammar.classMemberAction.getLine());
                println(new StringBuffer("public ").append(this.grammar.getClassName()).append("() {").toString());
                this.tabs++;
                println("tokenNames = _tokenNames;");
                this.tabs--;
                println("}");
                println("");
                Enumeration enumerationElements = this.grammar.rules.elements();
                int i2 = 0;
                while (enumerationElements.hasMoreElements()) {
                    GrammarSymbol grammarSymbol = (GrammarSymbol) enumerationElements.nextElement();
                    if (grammarSymbol instanceof RuleSymbol) {
                        RuleSymbol ruleSymbol = (RuleSymbol) grammarSymbol;
                        genRule(ruleSymbol, ruleSymbol.references.size() == 0, i2);
                        i2++;
                    }
                    exitIfError();
                }
                genTokenStrings();
                genBitsets(this.bitsetsUsed, this.grammar.tokenManager.maxTokenType());
                println("}");
                println("");
                getPrintWriterManager().finishOutput();
                this.defaultLine = i;
            } catch (Throwable th) {
                this.defaultLine = -999;
                throw th;
            }
        } catch (Throwable th2) {
            this.defaultLine = i;
            throw th2;
        }
    }

    @Override // antlr.CodeGenerator
    public void gen(WildcardElement wildcardElement) {
        int i = this.defaultLine;
        try {
            this.defaultLine = wildcardElement.getLine();
            if (wildcardElement.getLabel() != null && this.syntacticPredLevel == 0) {
                println(new StringBuffer().append(wildcardElement.getLabel()).append(" = ").append(this.lt1Value).append(";").toString());
            }
            genElementAST(wildcardElement);
            if (this.grammar instanceof TreeWalkerGrammar) {
                println("if ( _t==null ) throw new MismatchedTokenException();");
            } else if (this.grammar instanceof LexerGrammar) {
                if ((this.grammar instanceof LexerGrammar) && (!this.saveText || wildcardElement.getAutoGenType() == 3)) {
                    println("_saveIndex=text.length();");
                }
                println("matchNot(EOF_CHAR);");
                if ((this.grammar instanceof LexerGrammar) && (!this.saveText || wildcardElement.getAutoGenType() == 3)) {
                    println("text.setLength(_saveIndex);");
                }
            } else {
                println(new StringBuffer("matchNot(").append(getValueString(1)).append(");").toString());
            }
            if (this.grammar instanceof TreeWalkerGrammar) {
                println("_t = _t.getNextSibling();");
            }
        } finally {
            this.defaultLine = i;
        }
    }

    /* JADX WARN: Code duplicated, block: B:29:0x00bd A[Catch: all -> 0x0130, TRY_ENTER, TryCatch #0 {all -> 0x0130, blocks: (B:3:0x000d, B:5:0x0017, B:6:0x002f, B:8:0x003e, B:10:0x0052, B:12:0x007e, B:13:0x0084, B:15:0x0094, B:17:0x009c, B:19:0x00a8, B:29:0x00bd, B:31:0x00c1, B:32:0x00d5, B:33:0x00fe, B:20:0x00ab, B:22:0x00af, B:9:0x0043), top: B:39:0x000d }] */
    /* JADX WARN: Code duplicated, block: B:31:0x00c1 A[Catch: all -> 0x0130, TryCatch #0 {all -> 0x0130, blocks: (B:3:0x000d, B:5:0x0017, B:6:0x002f, B:8:0x003e, B:10:0x0052, B:12:0x007e, B:13:0x0084, B:15:0x0094, B:17:0x009c, B:19:0x00a8, B:29:0x00bd, B:31:0x00c1, B:32:0x00d5, B:33:0x00fe, B:20:0x00ab, B:22:0x00af, B:9:0x0043), top: B:39:0x000d }] */
    @Override // antlr.CodeGenerator
    public void gen(ZeroOrMoreBlock zeroOrMoreBlock) {
        String string;
        boolean z;
        int i = this.defaultLine;
        try {
            this.defaultLine = zeroOrMoreBlock.getLine();
            if (this.DEBUG_CODE_GENERATOR) {
                System.out.println(new StringBuffer("gen*(").append(zeroOrMoreBlock).append(Constant.AFTER_QUTO).toString());
            }
            println("{");
            genBlockPreamble(zeroOrMoreBlock);
            if (zeroOrMoreBlock.getLabel() != null) {
                string = zeroOrMoreBlock.getLabel();
            } else {
                string = new StringBuffer("_loop").append(zeroOrMoreBlock.ID).toString();
            }
            println(new StringBuffer().append(string).append(":").toString());
            println("do {");
            this.tabs++;
            genBlockInitAction(zeroOrMoreBlock);
            String str = this.currentASTResult;
            if (zeroOrMoreBlock.getLabel() != null) {
                this.currentASTResult = zeroOrMoreBlock.getLabel();
            }
            this.grammar.theLLkAnalyzer.deterministic(zeroOrMoreBlock);
            int i2 = this.grammar.maxk;
            if (!zeroOrMoreBlock.greedy && zeroOrMoreBlock.exitLookaheadDepth <= this.grammar.maxk && zeroOrMoreBlock.exitCache[zeroOrMoreBlock.exitLookaheadDepth].containsEpsilon()) {
                i2 = zeroOrMoreBlock.exitLookaheadDepth;
            } else {
                if (zeroOrMoreBlock.greedy || zeroOrMoreBlock.exitLookaheadDepth != Integer.MAX_VALUE) {
                    z = false;
                }
                if (z) {
                    if (this.DEBUG_CODE_GENERATOR) {
                        System.out.println(new StringBuffer("nongreedy (...)* loop; exit depth is ").append(zeroOrMoreBlock.exitLookaheadDepth).toString());
                    }
                    String lookaheadTestExpression = getLookaheadTestExpression(zeroOrMoreBlock.exitCache, i2);
                    println("// nongreedy exit test");
                    println(new StringBuffer("if (").append(lookaheadTestExpression).append(") break ").append(string).append(";").toString());
                }
                genBlockFinish(genCommonBlock(zeroOrMoreBlock, false), new StringBuffer("break ").append(string).append(";").toString(), zeroOrMoreBlock.getLine());
                this.tabs--;
                println("} while (true);");
                println("}");
                this.currentASTResult = str;
            }
            z = true;
            if (z) {
                if (this.DEBUG_CODE_GENERATOR) {
                    System.out.println(new StringBuffer("nongreedy (...)* loop; exit depth is ").append(zeroOrMoreBlock.exitLookaheadDepth).toString());
                }
                String lookaheadTestExpression2 = getLookaheadTestExpression(zeroOrMoreBlock.exitCache, i2);
                println("// nongreedy exit test");
                println(new StringBuffer("if (").append(lookaheadTestExpression2).append(") break ").append(string).append(";").toString());
            }
            genBlockFinish(genCommonBlock(zeroOrMoreBlock, false), new StringBuffer("break ").append(string).append(";").toString(), zeroOrMoreBlock.getLine());
            this.tabs--;
            println("} while (true);");
            println("}");
            this.currentASTResult = str;
        } finally {
            this.defaultLine = i;
        }
    }

    protected void genAlt(Alternative alternative, AlternativeBlock alternativeBlock) {
        boolean z = this.genAST;
        boolean z2 = false;
        this.genAST = z && alternative.getAutoGen();
        boolean z3 = this.saveText;
        if (z3 && alternative.getAutoGen()) {
            z2 = true;
        }
        this.saveText = z2;
        Hashtable hashtable = this.treeVariableMap;
        this.treeVariableMap = new Hashtable();
        if (alternative.exceptionSpec != null) {
            println("try {      // for error handling", alternative.head.getLine());
            this.tabs++;
        }
        for (AlternativeElement alternativeElement = alternative.head; !(alternativeElement instanceof BlockEndElement); alternativeElement = alternativeElement.next) {
            alternativeElement.generate();
        }
        if (this.genAST) {
            if (alternativeBlock instanceof RuleBlock) {
                boolean z4 = this.grammar.hasSyntacticPredicate;
                println(new StringBuffer().append(((RuleBlock) alternativeBlock).getRuleName()).append("_AST = (").append(this.labeledElementASTType).append(")currentAST.root;").toString(), CONTINUE_LAST_MAPPING);
                boolean z5 = this.grammar.hasSyntacticPredicate;
            } else if (alternativeBlock.getLabel() != null) {
                this.antlrTool.warning("Labeled subrules not yet supported", this.grammar.getFilename(), alternativeBlock.getLine(), alternativeBlock.getColumn());
            }
        }
        if (alternative.exceptionSpec != null) {
            this.tabs--;
            println("}", -999);
            genErrorHandler(alternative.exceptionSpec);
        }
        this.genAST = z;
        this.saveText = z3;
        this.treeVariableMap = hashtable;
    }

    protected void genBitsets(Vector vector, int i) {
        println("", -999);
        for (int i2 = 0; i2 < vector.size(); i2++) {
            BitSet bitSet = (BitSet) vector.elementAt(i2);
            bitSet.growToInclude(i);
            genBitSet(bitSet, i2);
        }
    }

    private void genBitSet(BitSet bitSet, int i) {
        int i2 = this.defaultLine;
        try {
            this.defaultLine = -999;
            println(new StringBuffer("private static final long[] mk").append(getBitsetName(i)).append("() {").toString());
            int iLengthInLongWords = bitSet.lengthInLongWords();
            if (iLengthInLongWords < 8) {
                println(new StringBuffer("\tlong[] data = { ").append(bitSet.toStringOfWords()).append("};").toString());
            } else {
                println(new StringBuffer("\tlong[] data = new long[").append(iLengthInLongWords).append("];").toString());
                long[] packedArray = bitSet.toPackedArray();
                int i3 = 0;
                while (i3 < packedArray.length) {
                    long j = packedArray[i3];
                    if (j == 0) {
                        i3++;
                    } else {
                        int i4 = i3 + 1;
                        if (i4 == packedArray.length || j != packedArray[i4]) {
                            println(new StringBuffer().append("\tdata[").append(i3).append("]=").append(packedArray[i3]).append("L;").toString());
                        } else {
                            while (i4 < packedArray.length && packedArray[i4] == packedArray[i3]) {
                                i4++;
                            }
                            println(new StringBuffer().append("\tfor (int i = ").append(i3).append("; i<=").append(i4 - 1).append("; i++) { data[i]=").append(packedArray[i3]).append("L; }").toString());
                        }
                        i3 = i4;
                    }
                }
            }
            println("\treturn data;");
            println("}");
            println(new StringBuffer().append("public static final BitSet ").append(getBitsetName(i)).append(" = new BitSet(").append(LanguageCodeUtil.MK).append(getBitsetName(i)).append("()").append(");").toString());
        } finally {
            this.defaultLine = i2;
        }
    }

    private void genBlockFinish(JavaBlockFinishingInfo javaBlockFinishingInfo, String str, int i) {
        int i2 = this.defaultLine;
        try {
            this.defaultLine = i;
            if (javaBlockFinishingInfo.needAnErrorClause && (javaBlockFinishingInfo.generatedAnIf || javaBlockFinishingInfo.generatedSwitch)) {
                if (javaBlockFinishingInfo.generatedAnIf) {
                    println("else {");
                } else {
                    println("{");
                }
                this.tabs++;
                println(str);
                this.tabs--;
                println("}");
            }
            if (javaBlockFinishingInfo.postscript != null) {
                println(javaBlockFinishingInfo.postscript);
            }
        } finally {
            this.defaultLine = i2;
        }
    }

    protected void genBlockInitAction(AlternativeBlock alternativeBlock) {
        if (alternativeBlock.initAction != null) {
            printAction(processActionForSpecialSymbols(alternativeBlock.initAction, alternativeBlock.getLine(), this.currentRule, null), alternativeBlock.getLine());
        }
    }

    protected void genBlockPreamble(AlternativeBlock alternativeBlock) {
        if (alternativeBlock instanceof RuleBlock) {
            RuleBlock ruleBlock = (RuleBlock) alternativeBlock;
            if (ruleBlock.labeledElements != null) {
                for (int i = 0; i < ruleBlock.labeledElements.size(); i++) {
                    AlternativeElement alternativeElement = (AlternativeElement) ruleBlock.labeledElements.elementAt(i);
                    int i2 = this.defaultLine;
                    try {
                        this.defaultLine = alternativeElement.getLine();
                        if ((alternativeElement instanceof RuleRefElement) || ((alternativeElement instanceof AlternativeBlock) && !(alternativeElement instanceof RuleBlock) && !(alternativeElement instanceof SynPredBlock))) {
                            if (!(alternativeElement instanceof RuleRefElement) && ((AlternativeBlock) alternativeElement).not && this.analyzer.subruleCanBeInverted((AlternativeBlock) alternativeElement, this.grammar instanceof LexerGrammar)) {
                                println(new StringBuffer().append(this.labeledElementType).append(" ").append(alternativeElement.getLabel()).append(" = ").append(this.labeledElementInit).append(";").toString());
                                if (this.grammar.buildAST) {
                                    genASTDeclaration(alternativeElement);
                                }
                            } else {
                                if (this.grammar.buildAST) {
                                    genASTDeclaration(alternativeElement);
                                }
                                if (this.grammar instanceof LexerGrammar) {
                                    println(new StringBuffer().append("Token ").append(alternativeElement.getLabel()).append("=null;").toString());
                                }
                                if (this.grammar instanceof TreeWalkerGrammar) {
                                    println(new StringBuffer().append(this.labeledElementType).append(" ").append(alternativeElement.getLabel()).append(" = ").append(this.labeledElementInit).append(";").toString());
                                }
                            }
                        } else {
                            println(new StringBuffer().append(this.labeledElementType).append(" ").append(alternativeElement.getLabel()).append(" = ").append(this.labeledElementInit).append(";").toString());
                            if (this.grammar.buildAST) {
                                if ((alternativeElement instanceof GrammarAtom) && ((GrammarAtom) alternativeElement).getASTNodeType() != null) {
                                    genASTDeclaration(alternativeElement, ((GrammarAtom) alternativeElement).getASTNodeType());
                                } else {
                                    genASTDeclaration(alternativeElement);
                                }
                            }
                        }
                        this.defaultLine = i2;
                    } catch (Throwable th) {
                        this.defaultLine = i2;
                        throw th;
                    }
                }
            }
        }
    }

    protected void genCases(BitSet bitSet, int i) {
        int i2 = this.defaultLine;
        try {
            this.defaultLine = i;
            if (this.DEBUG_CODE_GENERATOR) {
                System.out.println(new StringBuffer("genCases(").append(bitSet).append(Constant.AFTER_QUTO).toString());
            }
            int[] array = bitSet.toArray();
            int i3 = this.grammar instanceof LexerGrammar ? 4 : 1;
            boolean z = true;
            int i4 = 1;
            for (int i5 : array) {
                if (i4 == 1) {
                    print("");
                } else {
                    _print("  ");
                }
                _print(new StringBuffer().append("case ").append(getValueString(i5)).append(":").toString());
                if (i4 == i3) {
                    _println("");
                    z = true;
                    i4 = 1;
                } else {
                    i4++;
                    z = false;
                }
            }
            if (!z) {
                _println("");
            }
        } finally {
            this.defaultLine = i2;
        }
    }

    public JavaBlockFinishingInfo genCommonBlock(AlternativeBlock alternativeBlock, boolean z) throws Throwable {
        int i;
        boolean z2;
        JavaBlockFinishingInfo javaBlockFinishingInfo;
        int i2;
        boolean zLookaheadIsEmpty;
        String lookaheadTestExpression;
        JavaBlockFinishingInfo javaBlockFinishingInfo2;
        String str;
        String str2;
        int i3 = this.defaultLine;
        try {
            this.defaultLine = alternativeBlock.getLine();
            JavaBlockFinishingInfo javaBlockFinishingInfo3 = new JavaBlockFinishingInfo();
            if (this.DEBUG_CODE_GENERATOR) {
                System.out.println(new StringBuffer("genCommonBlock(").append(alternativeBlock).append(Constant.AFTER_QUTO).toString());
            }
            boolean z3 = this.genAST;
            char c = 1;
            this.genAST = z3 && alternativeBlock.getAutoGen();
            boolean z4 = this.saveText;
            this.saveText = z4 && alternativeBlock.getAutoGen();
            String string = "";
            if (alternativeBlock.not && this.analyzer.subruleCanBeInverted(alternativeBlock, this.grammar instanceof LexerGrammar)) {
                if (this.DEBUG_CODE_GENERATOR) {
                    System.out.println("special case: ~(subrule)");
                }
                Lookahead lookaheadLook = this.analyzer.look(1, alternativeBlock);
                if (alternativeBlock.getLabel() != null && this.syntacticPredLevel == 0) {
                    println(new StringBuffer().append(alternativeBlock.getLabel()).append(" = ").append(this.lt1Value).append(";").toString());
                }
                genElementAST(alternativeBlock);
                println(new StringBuffer("match(").append(this.grammar instanceof TreeWalkerGrammar ? "_t," : "").append(getBitsetName(markBitsetForGen(lookaheadLook.fset))).append(");").toString());
                if (this.grammar instanceof TreeWalkerGrammar) {
                    println("_t = _t.getNextSibling();");
                }
                this.defaultLine = i3;
                return javaBlockFinishingInfo3;
            }
            if (alternativeBlock.getAlternatives().size() == 1) {
                Alternative alternativeAt = alternativeBlock.getAlternativeAt(0);
                if (alternativeAt.synPred != null) {
                    this.antlrTool.warning("Syntactic predicate superfluous for single alternative", this.grammar.getFilename(), alternativeBlock.getAlternativeAt(0).synPred.getLine(), alternativeBlock.getAlternativeAt(0).synPred.getColumn());
                }
                if (z) {
                    if (alternativeAt.semPred != null) {
                        genSemPred(alternativeAt.semPred, alternativeBlock.line);
                    }
                    genAlt(alternativeAt, alternativeBlock);
                    this.defaultLine = i3;
                    return javaBlockFinishingInfo3;
                }
            }
            int i4 = 0;
            for (int i5 = 0; i5 < alternativeBlock.getAlternatives().size(); i5++) {
                if (suitableForCaseExpression(alternativeBlock.getAlternativeAt(i5))) {
                    i4++;
                }
            }
            String str3 = "{";
            String str4 = "}";
            if (i4 >= this.makeSwitchThreshold) {
                String strLookaheadString = lookaheadString(1);
                if (this.grammar instanceof TreeWalkerGrammar) {
                    println("if (_t==null) _t=ASTNULL;");
                }
                println(new StringBuffer().append("switch ( ").append(strLookaheadString).append(") {").toString());
                int i6 = 0;
                while (i6 < alternativeBlock.alternatives.size()) {
                    Alternative alternativeAt2 = alternativeBlock.getAlternativeAt(i6);
                    if (suitableForCaseExpression(alternativeAt2)) {
                        Lookahead lookahead = alternativeAt2.cache[c];
                        if (lookahead.fset.degree() == 0 && !lookahead.containsEpsilon()) {
                            this.antlrTool.warning("Alternate omitted due to empty prediction set", this.grammar.getFilename(), alternativeAt2.head.getLine(), alternativeAt2.head.getColumn());
                        } else {
                            genCases(lookahead.fset, alternativeAt2.head.getLine());
                            println("{", alternativeAt2.head.getLine());
                            this.tabs++;
                            genAlt(alternativeAt2, alternativeBlock);
                            println("break;", -999);
                            this.tabs--;
                            println("}", -999);
                        }
                    }
                    i6++;
                    c = 1;
                }
                println("default:");
                this.tabs++;
                z2 = true;
            } else {
                z2 = false;
            }
            int i7 = this.grammar instanceof LexerGrammar ? this.grammar.maxk : 0;
            int i8 = 0;
            int i9 = 0;
            while (i7 >= 0) {
                try {
                    if (this.DEBUG_CODE_GENERATOR) {
                        System.out.println(new StringBuffer().append("checking depth ").append(i7).toString());
                    }
                    int i10 = 0;
                    while (i10 < alternativeBlock.alternatives.size()) {
                        Alternative alternativeAt3 = alternativeBlock.getAlternativeAt(i10);
                        if (this.DEBUG_CODE_GENERATOR) {
                            i2 = i3;
                            try {
                                System.out.println(new StringBuffer().append("genAlt: ").append(i10).toString());
                            } catch (Throwable th) {
                                th = th;
                                i = i2;
                                this.defaultLine = i;
                                throw th;
                            }
                        } else {
                            i2 = i3;
                        }
                        if (z2 && suitableForCaseExpression(alternativeAt3)) {
                            if (this.DEBUG_CODE_GENERATOR) {
                                System.out.println("ignoring alt because it was in the switch");
                            }
                        } else {
                            if (this.grammar instanceof LexerGrammar) {
                                int i11 = alternativeAt3.lookaheadDepth;
                                if (i11 == Integer.MAX_VALUE) {
                                    i11 = this.grammar.maxk;
                                }
                                while (i11 >= 1 && alternativeAt3.cache[i11].containsEpsilon()) {
                                    i11--;
                                }
                                if (i11 != i7) {
                                    if (this.DEBUG_CODE_GENERATOR) {
                                        System.out.println(new StringBuffer().append("ignoring alt because effectiveDepth!=altDepth;").append(i11).append("!=").append(i7).toString());
                                    }
                                } else {
                                    zLookaheadIsEmpty = lookaheadIsEmpty(alternativeAt3, i11);
                                    lookaheadTestExpression = getLookaheadTestExpression(alternativeAt3, i11);
                                }
                            } else {
                                zLookaheadIsEmpty = lookaheadIsEmpty(alternativeAt3, this.grammar.maxk);
                                lookaheadTestExpression = getLookaheadTestExpression(alternativeAt3, this.grammar.maxk);
                            }
                            int i12 = this.defaultLine;
                            try {
                                this.defaultLine = alternativeAt3.head.getLine();
                                String str5 = str4;
                                if (alternativeAt3.cache[1].fset.degree() <= 127 || !suitableForCaseExpression(alternativeAt3)) {
                                    if (zLookaheadIsEmpty && alternativeAt3.semPred == null && alternativeAt3.synPred == null) {
                                        if (i8 == 0) {
                                            println(str3);
                                        } else {
                                            println("else {");
                                        }
                                        javaBlockFinishingInfo3.needAnErrorClause = false;
                                    } else {
                                        if (alternativeAt3.semPred != null) {
                                            str = str3;
                                            javaBlockFinishingInfo2 = javaBlockFinishingInfo3;
                                            String strProcessActionForSpecialSymbols = processActionForSpecialSymbols(alternativeAt3.semPred, alternativeBlock.line, this.currentRule, new ActionTransInfo());
                                            if (((this.grammar instanceof ParserGrammar) || (this.grammar instanceof LexerGrammar)) && this.grammar.debuggingOutput) {
                                                lookaheadTestExpression = new StringBuffer().append("(").append(lookaheadTestExpression).append("&& fireSemanticPredicateEvaluated(antlr.debug.SemanticPredicateEvent.PREDICTING,").append(addSemPred(this.charFormatter.escapeString(strProcessActionForSpecialSymbols))).append(",").append(strProcessActionForSpecialSymbols).append("))").toString();
                                            } else {
                                                lookaheadTestExpression = new StringBuffer().append("(").append(lookaheadTestExpression).append("&&(").append(strProcessActionForSpecialSymbols).append("))").toString();
                                            }
                                        } else {
                                            javaBlockFinishingInfo2 = javaBlockFinishingInfo3;
                                            str = str3;
                                        }
                                        if (i8 > 0) {
                                            if (alternativeAt3.synPred != null) {
                                                println("else {", alternativeAt3.synPred.getLine());
                                                this.tabs++;
                                                genSynPred(alternativeAt3.synPred, lookaheadTestExpression);
                                                i9++;
                                            } else {
                                                println(new StringBuffer().append("else if ").append(lookaheadTestExpression).append(" {").toString());
                                            }
                                        } else if (alternativeAt3.synPred != null) {
                                            genSynPred(alternativeAt3.synPred, lookaheadTestExpression);
                                        } else {
                                            if (this.grammar instanceof TreeWalkerGrammar) {
                                                println("if (_t==null) _t=ASTNULL;");
                                            }
                                            println(new StringBuffer().append("if ").append(lookaheadTestExpression).append(" {").toString());
                                        }
                                    }
                                    this.defaultLine = i12;
                                    i8++;
                                    this.tabs++;
                                    genAlt(alternativeAt3, alternativeBlock);
                                    this.tabs--;
                                    str2 = str5;
                                    println(str2);
                                    i10++;
                                    str4 = str2;
                                    i3 = i2;
                                    z4 = z4;
                                    z3 = z3;
                                    str3 = str;
                                    i7 = i7;
                                    javaBlockFinishingInfo3 = javaBlockFinishingInfo2;
                                    z2 = z2;
                                } else if (i8 == 0) {
                                    println(new StringBuffer().append("if ").append(lookaheadTestExpression).append(" {").toString());
                                } else {
                                    println(new StringBuffer().append("else if ").append(lookaheadTestExpression).append(" {").toString());
                                }
                                javaBlockFinishingInfo2 = javaBlockFinishingInfo3;
                                str = str3;
                                this.defaultLine = i12;
                                i8++;
                                this.tabs++;
                                genAlt(alternativeAt3, alternativeBlock);
                                this.tabs--;
                                str2 = str5;
                                println(str2);
                                i10++;
                                str4 = str2;
                                i3 = i2;
                                z4 = z4;
                                z3 = z3;
                                str3 = str;
                                i7 = i7;
                                javaBlockFinishingInfo3 = javaBlockFinishingInfo2;
                                z2 = z2;
                            } catch (Throwable th2) {
                                this.defaultLine = i12;
                                throw th2;
                            }
                        }
                        javaBlockFinishingInfo2 = javaBlockFinishingInfo3;
                        str = str3;
                        str2 = str4;
                        i10++;
                        str4 = str2;
                        i3 = i2;
                        z4 = z4;
                        z3 = z3;
                        str3 = str;
                        i7 = i7;
                        javaBlockFinishingInfo3 = javaBlockFinishingInfo2;
                        z2 = z2;
                    }
                    i7--;
                    string = string;
                    z2 = z2;
                } catch (Throwable th3) {
                    th = th3;
                    i2 = i3;
                }
            }
            boolean z5 = z2;
            boolean z6 = z3;
            int i13 = i3;
            JavaBlockFinishingInfo javaBlockFinishingInfo4 = javaBlockFinishingInfo3;
            boolean z7 = z4;
            String str6 = str4;
            for (int i14 = 1; i14 <= i9; i14++) {
                string = new StringBuffer().append(string).append(str6).toString();
            }
            this.genAST = z6;
            this.saveText = z7;
            if (z5) {
                this.tabs--;
                String string2 = new StringBuffer().append(string).append(str6).toString();
                javaBlockFinishingInfo = javaBlockFinishingInfo4;
                javaBlockFinishingInfo.postscript = string2;
                javaBlockFinishingInfo.generatedSwitch = true;
                javaBlockFinishingInfo.generatedAnIf = i8 > 0;
            } else {
                javaBlockFinishingInfo = javaBlockFinishingInfo4;
                javaBlockFinishingInfo.postscript = string;
                javaBlockFinishingInfo.generatedSwitch = false;
                javaBlockFinishingInfo.generatedAnIf = i8 > 0;
            }
            this.defaultLine = i13;
            return javaBlockFinishingInfo;
        } catch (Throwable th4) {
            th = th4;
            i = i3;
        }
    }

    private static boolean suitableForCaseExpression(Alternative alternative) {
        return alternative.lookaheadDepth == 1 && alternative.semPred == null && !alternative.cache[1].containsEpsilon() && alternative.cache[1].fset.degree() <= 127;
    }

    private void genElementAST(AlternativeElement alternativeElement) {
        String label;
        String label2;
        int i = this.defaultLine;
        try {
            this.defaultLine = alternativeElement.getLine();
            if ((this.grammar instanceof TreeWalkerGrammar) && !this.grammar.buildAST) {
                if (alternativeElement.getLabel() == null) {
                    String str = this.lt1Value;
                    String string = new StringBuffer(Constants.TMP_DIRECTORY_NAME).append(this.astVarNumber).append("_AST").toString();
                    this.astVarNumber++;
                    mapTreeVariable(alternativeElement, string);
                    println(new StringBuffer().append(this.labeledElementASTType).append(" ").append(string).append("_in = ").append(str).append(";").toString());
                }
                return;
            }
            if (this.grammar.buildAST && this.syntacticPredLevel == 0) {
                boolean z = this.genAST && !(alternativeElement.getLabel() == null && alternativeElement.getAutoGenType() == 3);
                if (alternativeElement.getAutoGenType() != 3 && (alternativeElement instanceof TokenRefElement)) {
                    z = true;
                }
                boolean z2 = this.grammar.hasSyntacticPredicate;
                if (alternativeElement.getLabel() != null) {
                    label2 = alternativeElement.getLabel();
                    label = alternativeElement.getLabel();
                } else {
                    String str2 = this.lt1Value;
                    String string2 = new StringBuffer(Constants.TMP_DIRECTORY_NAME).append(this.astVarNumber).toString();
                    this.astVarNumber++;
                    label = string2;
                    label2 = str2;
                }
                if (z) {
                    if (alternativeElement instanceof GrammarAtom) {
                        GrammarAtom grammarAtom = (GrammarAtom) alternativeElement;
                        if (grammarAtom.getASTNodeType() != null) {
                            genASTDeclaration(alternativeElement, label, grammarAtom.getASTNodeType());
                        } else {
                            genASTDeclaration(alternativeElement, label, this.labeledElementASTType);
                        }
                    } else {
                        genASTDeclaration(alternativeElement, label, this.labeledElementASTType);
                    }
                }
                String string3 = new StringBuffer().append(label).append("_AST").toString();
                mapTreeVariable(alternativeElement, string3);
                if (this.grammar instanceof TreeWalkerGrammar) {
                    println(new StringBuffer().append(this.labeledElementASTType).append(" ").append(string3).append("_in = null;").toString());
                }
                if (alternativeElement.getLabel() != null) {
                    if (alternativeElement instanceof GrammarAtom) {
                        println(new StringBuffer().append(string3).append(" = ").append(getASTCreateString((GrammarAtom) alternativeElement, label2)).append(";").toString());
                    } else {
                        println(new StringBuffer().append(string3).append(" = ").append(getASTCreateString(label2)).append(";").toString());
                    }
                }
                if (alternativeElement.getLabel() == null && z) {
                    String str3 = this.lt1Value;
                    if (alternativeElement instanceof GrammarAtom) {
                        println(new StringBuffer().append(string3).append(" = ").append(getASTCreateString((GrammarAtom) alternativeElement, str3)).append(";").toString());
                    } else {
                        println(new StringBuffer().append(string3).append(" = ").append(getASTCreateString(str3)).append(";").toString());
                    }
                    if (this.grammar instanceof TreeWalkerGrammar) {
                        println(new StringBuffer().append(string3).append("_in = ").append(str3).append(";").toString());
                    }
                }
                if (this.genAST) {
                    int autoGenType = alternativeElement.getAutoGenType();
                    if (autoGenType == 1) {
                        println(new StringBuffer("astFactory.addASTChild(currentAST, ").append(string3).append(");").toString());
                    } else if (autoGenType == 2) {
                        println(new StringBuffer("astFactory.makeASTRoot(currentAST, ").append(string3).append(");").toString());
                    }
                }
            }
        } finally {
            this.defaultLine = i;
        }
    }

    private void genErrorCatchForElement(AlternativeElement alternativeElement) {
        if (alternativeElement.getLabel() == null) {
            return;
        }
        String strEncodeLexerRuleName = alternativeElement.enclosingRuleName;
        if (this.grammar instanceof LexerGrammar) {
            strEncodeLexerRuleName = CodeGenerator.encodeLexerRuleName(alternativeElement.enclosingRuleName);
        }
        RuleSymbol ruleSymbol = (RuleSymbol) this.grammar.getSymbol(strEncodeLexerRuleName);
        if (ruleSymbol == null) {
            this.antlrTool.panic("Enclosing rule not found!");
        }
        ExceptionSpec exceptionSpecFindExceptionSpec = ruleSymbol.block.findExceptionSpec(alternativeElement.getLabel());
        if (exceptionSpecFindExceptionSpec != null) {
            this.tabs--;
            println("}", alternativeElement.getLine());
            genErrorHandler(exceptionSpecFindExceptionSpec);
        }
    }

    private void genErrorHandler(ExceptionSpec exceptionSpec) {
        for (int i = 0; i < exceptionSpec.handlers.size(); i++) {
            ExceptionHandler exceptionHandler = (ExceptionHandler) exceptionSpec.handlers.elementAt(i);
            int i2 = this.defaultLine;
            try {
                this.defaultLine = exceptionHandler.action.getLine();
                println(new StringBuffer().append("catch (").append(exceptionHandler.exceptionTypeAndName.getText()).append(") {").toString(), exceptionHandler.exceptionTypeAndName.getLine());
                this.tabs++;
                if (this.grammar.hasSyntacticPredicate) {
                    println("if (inputState.guessing==0) {");
                    this.tabs++;
                }
                printAction(processActionForSpecialSymbols(exceptionHandler.action.getText(), exceptionHandler.action.getLine(), this.currentRule, new ActionTransInfo()));
                if (this.grammar.hasSyntacticPredicate) {
                    this.tabs--;
                    println("} else {");
                    this.tabs++;
                    println(new StringBuffer().append("throw ").append(extractIdOfAction(exceptionHandler.exceptionTypeAndName)).append(";").toString());
                    this.tabs--;
                    println("}");
                }
                this.tabs--;
                println("}");
                this.defaultLine = i2;
            } catch (Throwable th) {
                this.defaultLine = i2;
                throw th;
            }
        }
    }

    private void genErrorTryForElement(AlternativeElement alternativeElement) {
        if (alternativeElement.getLabel() == null) {
            return;
        }
        String strEncodeLexerRuleName = alternativeElement.enclosingRuleName;
        if (this.grammar instanceof LexerGrammar) {
            strEncodeLexerRuleName = CodeGenerator.encodeLexerRuleName(alternativeElement.enclosingRuleName);
        }
        RuleSymbol ruleSymbol = (RuleSymbol) this.grammar.getSymbol(strEncodeLexerRuleName);
        if (ruleSymbol == null) {
            this.antlrTool.panic("Enclosing rule not found!");
        }
        if (ruleSymbol.block.findExceptionSpec(alternativeElement.getLabel()) != null) {
            println("try { // for error handling", alternativeElement.getLine());
            this.tabs++;
        }
    }

    protected void genASTDeclaration(AlternativeElement alternativeElement) {
        genASTDeclaration(alternativeElement, this.labeledElementASTType);
    }

    protected void genASTDeclaration(AlternativeElement alternativeElement, String str) {
        genASTDeclaration(alternativeElement, alternativeElement.getLabel(), str);
    }

    protected void genASTDeclaration(AlternativeElement alternativeElement, String str, String str2) {
        if (this.declaredASTVariables.contains(alternativeElement)) {
            return;
        }
        println(new StringBuffer().append(str2).append(" ").append(str).append("_AST = null;").toString());
        this.declaredASTVariables.put(alternativeElement, alternativeElement);
    }

    protected void genHeader() {
        println(new StringBuffer("// $ANTLR ").append(Tool.version).append(": \"").append(this.antlrTool.fileMinusPath(this.antlrTool.grammarFile)).append("\" -> \"").append(this.grammar.getClassName()).append(".java\"$").toString(), -999);
    }

    private void genLiteralsTest() {
        println("_ttype = testLiteralsTable(_ttype);");
    }

    private void genLiteralsTestForPartialToken() {
        println("_ttype = testLiteralsTable(new String(text.getBuffer(),_begin,text.length()-_begin),_ttype);");
    }

    protected void genMatch(GrammarAtom grammarAtom) {
        if (grammarAtom instanceof StringLiteralElement) {
            if (this.grammar instanceof LexerGrammar) {
                genMatchUsingAtomText(grammarAtom);
                return;
            } else {
                genMatchUsingAtomTokenType(grammarAtom);
                return;
            }
        }
        if (grammarAtom instanceof CharLiteralElement) {
            if (this.grammar instanceof LexerGrammar) {
                genMatchUsingAtomText(grammarAtom);
                return;
            } else {
                this.antlrTool.error(new StringBuffer("cannot ref character literals in grammar: ").append(grammarAtom).toString());
                return;
            }
        }
        if (grammarAtom instanceof TokenRefElement) {
            genMatchUsingAtomText(grammarAtom);
        } else if (grammarAtom instanceof WildcardElement) {
            gen((WildcardElement) grammarAtom);
        }
    }

    protected void genMatchUsingAtomText(GrammarAtom grammarAtom) {
        int i = this.defaultLine;
        try {
            this.defaultLine = grammarAtom.getLine();
            String str = "";
            if (this.grammar instanceof TreeWalkerGrammar) {
                str = "_t,";
            }
            if ((this.grammar instanceof LexerGrammar) && (!this.saveText || grammarAtom.getAutoGenType() == 3)) {
                println("_saveIndex=text.length();");
            }
            print(grammarAtom.not ? "matchNot(" : "match(");
            _print(str, -999);
            if (grammarAtom.atomText.equals("EOF")) {
                _print("Token.EOF_TYPE");
            } else {
                _print(grammarAtom.atomText);
            }
            _println(");");
            if ((this.grammar instanceof LexerGrammar) && (!this.saveText || grammarAtom.getAutoGenType() == 3)) {
                println("text.setLength(_saveIndex);");
            }
        } finally {
            this.defaultLine = i;
        }
    }

    protected void genMatchUsingAtomTokenType(GrammarAtom grammarAtom) {
        String str;
        if (!(this.grammar instanceof TreeWalkerGrammar)) {
            str = "";
        } else {
            str = "_t,";
        }
        println(new StringBuffer().append(grammarAtom.not ? "matchNot(" : "match(").append(new StringBuffer().append(str).append(getValueString(grammarAtom.getType())).toString()).append(");").toString(), grammarAtom.getLine());
    }

    public void genNextToken() {
        String string;
        int i = this.defaultLine;
        try {
            this.defaultLine = -999;
            for (int i2 = 0; i2 < this.grammar.rules.size(); i2++) {
                RuleSymbol ruleSymbol = (RuleSymbol) this.grammar.rules.elementAt(i2);
                if (ruleSymbol.isDefined() && ruleSymbol.access.equals("public")) {
                    RuleBlock ruleBlockCreateNextTokenRule = MakeGrammar.createNextTokenRule(this.grammar, this.grammar.rules, BaseRecognizer.NEXT_TOKEN_RULE_NAME);
                    RuleSymbol ruleSymbol2 = new RuleSymbol("mnextToken");
                    ruleSymbol2.setDefined();
                    ruleSymbol2.setBlock(ruleBlockCreateNextTokenRule);
                    ruleSymbol2.access = "private";
                    this.grammar.define(ruleSymbol2);
                    this.grammar.theLLkAnalyzer.deterministic(ruleBlockCreateNextTokenRule);
                    String str = ((LexerGrammar) this.grammar).filterMode ? ((LexerGrammar) this.grammar).filterRule : null;
                    println("");
                    println("public Token nextToken() throws TokenStreamException {");
                    this.tabs++;
                    println("Token theRetToken=null;");
                    _println("tryAgain:");
                    println("for (;;) {");
                    this.tabs++;
                    println("Token _token = null;");
                    println("int _ttype = Token.INVALID_TYPE;");
                    if (((LexerGrammar) this.grammar).filterMode) {
                        println("setCommitToPath(false);");
                        if (str != null) {
                            if (!this.grammar.isDefined(CodeGenerator.encodeLexerRuleName(str))) {
                                this.grammar.antlrTool.error(new StringBuffer().append("Filter rule ").append(str).append(" does not exist in this lexer").toString());
                            } else {
                                RuleSymbol ruleSymbol3 = (RuleSymbol) this.grammar.getSymbol(CodeGenerator.encodeLexerRuleName(str));
                                if (!ruleSymbol3.isDefined()) {
                                    this.grammar.antlrTool.error(new StringBuffer().append("Filter rule ").append(str).append(" does not exist in this lexer").toString());
                                } else if (ruleSymbol3.access.equals("public")) {
                                    this.grammar.antlrTool.error(new StringBuffer().append("Filter rule ").append(str).append(" must be protected").toString());
                                }
                            }
                            println("int _m;");
                            println("_m = mark();");
                        }
                    }
                    println("resetText();");
                    println("try {   // for char stream error handling");
                    this.tabs++;
                    println("try {   // for lexical error handling");
                    this.tabs++;
                    for (int i3 = 0; i3 < ruleBlockCreateNextTokenRule.getAlternatives().size(); i3++) {
                        Alternative alternativeAt = ruleBlockCreateNextTokenRule.getAlternativeAt(i3);
                        if (alternativeAt.cache[1].containsEpsilon()) {
                            this.antlrTool.warning(new StringBuffer().append("public lexical rule ").append(CodeGenerator.decodeLexerRuleName(((RuleRefElement) alternativeAt.head).targetRule)).append(" is optional (can match \"nothing\")").toString());
                        }
                    }
                    String property = System.getProperty(SystemProperties.LINE_SEPARATOR);
                    JavaBlockFinishingInfo javaBlockFinishingInfoGenCommonBlock = genCommonBlock(ruleBlockCreateNextTokenRule, false);
                    String string2 = new StringBuffer().append("if (LA(1)==EOF_CHAR) {uponEOF(); _returnToken = makeToken(Token.EOF_TYPE);}").append(property).append("\t\t\t\t").toString();
                    if (!((LexerGrammar) this.grammar).filterMode) {
                        string = new StringBuffer().append(string2).append("else {").append(this.throwNoViable).append("}").toString();
                    } else if (str == null) {
                        string = new StringBuffer().append(string2).append("else {consume(); continue tryAgain;}").toString();
                    } else {
                        string = new StringBuffer().append(string2).append("else {").append(property).append("\t\t\t\t\tcommit();").append(property).append("\t\t\t\t\ttry {m").append(str).append("(false);}").append(property).append("\t\t\t\t\tcatch(RecognitionException e) {").append(property).append("\t\t\t\t\t\t// catastrophic failure").append(property).append("\t\t\t\t\t\treportError(e);").append(property).append("\t\t\t\t\t\tconsume();").append(property).append("\t\t\t\t\t}").append(property).append("\t\t\t\t\tcontinue tryAgain;").append(property).append("\t\t\t\t}").toString();
                    }
                    genBlockFinish(javaBlockFinishingInfoGenCommonBlock, string, ruleBlockCreateNextTokenRule.getLine());
                    if (((LexerGrammar) this.grammar).filterMode && str != null) {
                        println("commit();");
                    }
                    println("if ( _returnToken==null ) continue tryAgain; // found SKIP token");
                    println("_ttype = _returnToken.getType();");
                    if (((LexerGrammar) this.grammar).getTestLiterals()) {
                        genLiteralsTest();
                    }
                    println("_returnToken.setType(_ttype);");
                    println("return _returnToken;");
                    this.tabs--;
                    println("}");
                    println("catch (RecognitionException e) {");
                    this.tabs++;
                    if (((LexerGrammar) this.grammar).filterMode) {
                        if (str == null) {
                            println("if ( !getCommitToPath() ) {consume(); continue tryAgain;}");
                        } else {
                            println("if ( !getCommitToPath() ) {");
                            this.tabs++;
                            println("rewind(_m);");
                            println("resetText();");
                            println(new StringBuffer().append("try {m").append(str).append("(false);}").toString());
                            println("catch(RecognitionException ee) {");
                            println("\t// horrendous failure: error in filter rule");
                            println("\treportError(ee);");
                            println("\tconsume();");
                            println("}");
                            println("continue tryAgain;");
                            this.tabs--;
                            println("}");
                        }
                    }
                    if (ruleBlockCreateNextTokenRule.getDefaultErrorHandler()) {
                        println("reportError(e);");
                        println("consume();");
                    } else {
                        println("throw new TokenStreamRecognitionException(e);");
                    }
                    this.tabs--;
                    println("}");
                    this.tabs--;
                    println("}");
                    println("catch (CharStreamException cse) {");
                    println("\tif ( cse instanceof CharStreamIOException ) {");
                    println("\t\tthrow new TokenStreamIOException(((CharStreamIOException)cse).io);");
                    println("\t}");
                    println("\telse {");
                    println("\t\tthrow new TokenStreamException(cse.getMessage());");
                    println("\t}");
                    println("}");
                    this.tabs--;
                    println("}");
                    this.tabs--;
                    println("}");
                    println("");
                    this.defaultLine = i;
                    return;
                }
            }
            println("");
            println("public Token nextToken() throws TokenStreamException {");
            println("\ttry {uponEOF();}");
            println("\tcatch(CharStreamIOException csioe) {");
            println("\t\tthrow new TokenStreamIOException(csioe.io);");
            println("\t}");
            println("\tcatch(CharStreamException cse) {");
            println("\t\tthrow new TokenStreamException(cse.getMessage());");
            println("\t}");
            println("\treturn new CommonToken(Token.EOF_TYPE, \"\");");
            println("}");
            println("");
            this.defaultLine = i;
        } catch (Throwable th) {
            this.defaultLine = i;
            throw th;
        }
    }

    public void genRule(RuleSymbol ruleSymbol, boolean z, int i) throws Throwable {
        int i2;
        int i3;
        int i4;
        this.tabs = 1;
        if (this.DEBUG_CODE_GENERATOR) {
            System.out.println(new StringBuffer("genRule(").append(ruleSymbol.getId()).append(Constant.AFTER_QUTO).toString());
        }
        if (!ruleSymbol.isDefined()) {
            this.antlrTool.error(new StringBuffer("undefined rule: ").append(ruleSymbol.getId()).toString());
            return;
        }
        RuleBlock block = ruleSymbol.getBlock();
        int i5 = this.defaultLine;
        try {
            this.defaultLine = block.getLine();
            this.currentRule = block;
            this.currentASTResult = ruleSymbol.getId();
            this.declaredASTVariables.clear();
            boolean z2 = this.genAST;
            this.genAST = z2 && block.getAutoGen();
            this.saveText = block.getAutoGen();
            if (ruleSymbol.comment != null) {
                _println(ruleSymbol.comment);
            }
            try {
                print(new StringBuffer().append(ruleSymbol.access).append(" final ").toString());
                if (block.returnAction != null) {
                    _print(new StringBuffer().append(extractTypeOfAction(block.returnAction, block.getLine(), block.getColumn())).append(" ").toString());
                } else {
                    _print("void ");
                }
                _print(new StringBuffer().append(ruleSymbol.getId()).append("(").toString());
                _print(this.commonExtraParams);
                if (this.commonExtraParams.length() != 0 && block.argAction != null) {
                    _print(",");
                }
                String str = "";
                if (block.argAction != null) {
                    _println("");
                    this.tabs++;
                    println(block.argAction);
                    this.tabs--;
                    print(Constant.AFTER_QUTO);
                } else {
                    _print(Constant.AFTER_QUTO);
                }
                _print(new StringBuffer(" throws ").append(this.exceptionThrown).toString());
                if (this.grammar instanceof ParserGrammar) {
                    _print(", TokenStreamException");
                } else if (this.grammar instanceof LexerGrammar) {
                    _print(", CharStreamException, TokenStreamException");
                }
                if (block.throwsSpec != null) {
                    if (this.grammar instanceof LexerGrammar) {
                        this.antlrTool.error(new StringBuffer("user-defined throws spec not allowed (yet) for lexer rule ").append(block.ruleName).toString());
                    } else {
                        _print(new StringBuffer(", ").append(block.throwsSpec).toString());
                    }
                }
                _println(" {");
                this.tabs++;
                if (block.returnAction != null) {
                    println(new StringBuffer().append(block.returnAction).append(";").toString());
                }
                println(this.commonLocalVars);
                String str2 = "\",_t);";
                String str3 = "\");";
                if (this.grammar.traceRules) {
                    if (this.grammar instanceof TreeWalkerGrammar) {
                        println(new StringBuffer("traceIn(\"").append(ruleSymbol.getId()).append("\",_t);").toString());
                    } else {
                        println(new StringBuffer("traceIn(\"").append(ruleSymbol.getId()).append("\");").toString());
                    }
                }
                if (this.grammar instanceof LexerGrammar) {
                    if (ruleSymbol.getId().equals("mEOF")) {
                        println("_ttype = Token.EOF_TYPE;");
                    } else {
                        println(new StringBuffer("_ttype = ").append(ruleSymbol.getId().substring(1)).append(";").toString());
                    }
                    println("int _saveIndex;");
                }
                if (this.grammar.debuggingOutput) {
                    if (this.grammar instanceof ParserGrammar) {
                        println(new StringBuffer("fireEnterRule(").append(i).append(",0);").toString());
                    } else if (this.grammar instanceof LexerGrammar) {
                        println(new StringBuffer("fireEnterRule(").append(i).append(",_ttype);").toString());
                    }
                }
                if (this.grammar.debuggingOutput || this.grammar.traceRules) {
                    println("try { // debugging");
                    this.tabs++;
                }
                if (this.grammar instanceof TreeWalkerGrammar) {
                    println(new StringBuffer().append(this.labeledElementASTType).append(" ").append(ruleSymbol.getId()).append("_AST_in = (_t == ASTNULL) ? null : (").append(this.labeledElementASTType).append(")_t;").toString(), -999);
                }
                if (this.grammar.buildAST) {
                    println("returnAST = null;");
                    println("ASTPair currentAST = new ASTPair();");
                    println(new StringBuffer().append(this.labeledElementASTType).append(" ").append(ruleSymbol.getId()).append("_AST = null;").toString());
                }
                genBlockPreamble(block);
                genBlockInitAction(block);
                println("");
                ExceptionSpec exceptionSpecFindExceptionSpec = block.findExceptionSpec("");
                if (exceptionSpecFindExceptionSpec != null || block.getDefaultErrorHandler()) {
                    println("try {      // for error handling");
                    i3 = 1;
                    this.tabs++;
                } else {
                    i3 = 1;
                }
                if (block.alternatives.size() == i3) {
                    Alternative alternativeAt = block.getAlternativeAt(0);
                    String str4 = alternativeAt.semPred;
                    if (str4 != null) {
                        genSemPred(str4, this.currentRule.line);
                    }
                    if (alternativeAt.synPred != null) {
                        this.antlrTool.warning("Syntactic predicate ignored for single alternative", this.grammar.getFilename(), alternativeAt.synPred.getLine(), alternativeAt.synPred.getColumn());
                    }
                    genAlt(alternativeAt, block);
                } else {
                    str = "";
                    str2 = "\",_t);";
                    str3 = "\");";
                    this.grammar.theLLkAnalyzer.deterministic(block);
                    genBlockFinish(genCommonBlock(block, false), this.throwNoViable, block.getLine());
                }
                if (exceptionSpecFindExceptionSpec != null || block.getDefaultErrorHandler()) {
                    this.tabs--;
                    println("}");
                }
                if (exceptionSpecFindExceptionSpec != null) {
                    genErrorHandler(exceptionSpecFindExceptionSpec);
                } else if (block.getDefaultErrorHandler()) {
                    println(new StringBuffer("catch (").append(this.exceptionThrown).append(" ex) {").toString());
                    this.tabs++;
                    if (this.grammar.hasSyntacticPredicate) {
                        println("if (inputState.guessing==0) {");
                        this.tabs++;
                    }
                    println("reportError(ex);");
                    if (!(this.grammar instanceof TreeWalkerGrammar)) {
                        println(new StringBuffer("recover(ex,").append(getBitsetName(markBitsetForGen(this.grammar.theLLkAnalyzer.FOLLOW(1, block.endNode).fset))).append(");").toString());
                    } else {
                        println("if (_t!=null) {_t = _t.getNextSibling();}");
                    }
                    if (this.grammar.hasSyntacticPredicate) {
                        this.tabs--;
                        println("} else {");
                        println("  throw ex;");
                        println("}");
                    }
                    this.tabs--;
                    println("}");
                }
                if (this.grammar.buildAST) {
                    println(new StringBuffer("returnAST = ").append(ruleSymbol.getId()).append("_AST;").toString());
                }
                if (this.grammar instanceof TreeWalkerGrammar) {
                    println("_retTree = _t;");
                }
                if (block.getTestLiterals()) {
                    if (ruleSymbol.access.equals("protected")) {
                        genLiteralsTestForPartialToken();
                    } else {
                        genLiteralsTest();
                    }
                }
                if (this.grammar instanceof LexerGrammar) {
                    println("if ( _createToken && _token==null && _ttype!=Token.SKIP ) {");
                    println("\t_token = makeToken(_ttype);");
                    println("\t_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));");
                    println("}");
                    println("_returnToken = _token;");
                }
                if (block.returnAction != null) {
                    println(new StringBuffer("return ").append(extractIdOfAction(block.returnAction, block.getLine(), block.getColumn())).append(";").toString());
                }
                if (this.grammar.debuggingOutput || this.grammar.traceRules) {
                    this.tabs--;
                    println("} finally { // debugging");
                    this.tabs++;
                    if (this.grammar.debuggingOutput) {
                        if (this.grammar instanceof ParserGrammar) {
                            println(new StringBuffer("fireExitRule(").append(i).append(",0);").toString());
                        } else if (this.grammar instanceof LexerGrammar) {
                            println(new StringBuffer("fireExitRule(").append(i).append(",_ttype);").toString());
                        }
                    }
                    if (this.grammar.traceRules) {
                        if (this.grammar instanceof TreeWalkerGrammar) {
                            println(new StringBuffer("traceOut(\"").append(ruleSymbol.getId()).append(str2).toString());
                        } else {
                            println(new StringBuffer("traceOut(\"").append(ruleSymbol.getId()).append(str3).toString());
                        }
                    }
                    i4 = 1;
                    this.tabs--;
                    println("}");
                } else {
                    i4 = 1;
                }
                this.tabs -= i4;
                println("}");
                println(str);
                this.genAST = z2;
                this.defaultLine = i5;
            } catch (Throwable th) {
                th = th;
                i2 = i5;
                this.defaultLine = i2;
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            i2 = i5;
        }
    }

    private void GenRuleInvocation(RuleRefElement ruleRefElement) {
        int i = this.defaultLine;
        try {
            this.defaultLine = ruleRefElement.getLine();
            getPrintWriterManager().startSingleSourceLineMapping(ruleRefElement.getLine());
            _print(new StringBuffer().append(ruleRefElement.targetRule).append("(").toString());
            getPrintWriterManager().endMapping();
            if (this.grammar instanceof LexerGrammar) {
                if (ruleRefElement.getLabel() != null) {
                    _print("true");
                } else {
                    _print("false");
                }
                if (this.commonExtraArgs.length() != 0 || ruleRefElement.args != null) {
                    _print(",");
                }
            }
            _print(this.commonExtraArgs);
            if (this.commonExtraArgs.length() != 0 && ruleRefElement.args != null) {
                _print(",");
            }
            RuleSymbol ruleSymbol = (RuleSymbol) this.grammar.getSymbol(ruleRefElement.targetRule);
            if (ruleRefElement.args != null) {
                ActionTransInfo actionTransInfo = new ActionTransInfo();
                String strProcessActionForSpecialSymbols = processActionForSpecialSymbols(ruleRefElement.args, 0, this.currentRule, actionTransInfo);
                if (actionTransInfo.assignToRoot || actionTransInfo.refRuleRoot != null) {
                    this.antlrTool.error(new StringBuffer("Arguments of rule reference '").append(ruleRefElement.targetRule).append("' cannot set or ref #").append(this.currentRule.getRuleName()).toString(), this.grammar.getFilename(), ruleRefElement.getLine(), ruleRefElement.getColumn());
                }
                _print(strProcessActionForSpecialSymbols);
                if (ruleSymbol.block.argAction == null) {
                    this.antlrTool.warning(new StringBuffer("Rule '").append(ruleRefElement.targetRule).append("' accepts no arguments").toString(), this.grammar.getFilename(), ruleRefElement.getLine(), ruleRefElement.getColumn());
                }
            } else if (ruleSymbol.block.argAction != null) {
                this.antlrTool.warning(new StringBuffer("Missing parameters on reference to rule ").append(ruleRefElement.targetRule).toString(), this.grammar.getFilename(), ruleRefElement.getLine(), ruleRefElement.getColumn());
            }
            _println(");");
            if (this.grammar instanceof TreeWalkerGrammar) {
                println("_t = _retTree;");
            }
        } finally {
            this.defaultLine = i;
        }
    }

    protected void genSemPred(String str, int i) {
        String strProcessActionForSpecialSymbols = processActionForSpecialSymbols(str, i, this.currentRule, new ActionTransInfo());
        String strEscapeString = this.charFormatter.escapeString(strProcessActionForSpecialSymbols);
        if (this.grammar.debuggingOutput && ((this.grammar instanceof ParserGrammar) || (this.grammar instanceof LexerGrammar))) {
            strProcessActionForSpecialSymbols = new StringBuffer("fireSemanticPredicateEvaluated(antlr.debug.SemanticPredicateEvent.VALIDATING,").append(addSemPred(strEscapeString)).append(",").append(strProcessActionForSpecialSymbols).append(Constant.AFTER_QUTO).toString();
        }
        println(new StringBuffer("if (!(").append(strProcessActionForSpecialSymbols).append("))").toString(), i);
        println(new StringBuffer("  throw new SemanticException(\"").append(strEscapeString).append("\");").toString(), i);
    }

    protected void genSemPredMap() {
        Enumeration enumerationElements = this.semPreds.elements();
        println("private String _semPredNames[] = {", -999);
        while (enumerationElements.hasMoreElements()) {
            println(new StringBuffer("\"").append(enumerationElements.nextElement()).append("\",").toString(), -999);
        }
        println("};", -999);
    }

    protected void genSynPred(SynPredBlock synPredBlock, String str) {
        int i = this.defaultLine;
        try {
            this.defaultLine = synPredBlock.getLine();
            if (this.DEBUG_CODE_GENERATOR) {
                System.out.println(new StringBuffer("gen=>(").append(synPredBlock).append(Constant.AFTER_QUTO).toString());
            }
            println(new StringBuffer("boolean synPredMatched").append(synPredBlock.ID).append(" = false;").toString());
            if (this.grammar instanceof TreeWalkerGrammar) {
                println("if (_t==null) _t=ASTNULL;");
            }
            println(new StringBuffer("if (").append(str).append(") {").toString());
            this.tabs++;
            if (this.grammar instanceof TreeWalkerGrammar) {
                println(new StringBuffer("AST __t").append(synPredBlock.ID).append(" = _t;").toString());
            } else {
                println(new StringBuffer("int _m").append(synPredBlock.ID).append(" = mark();").toString());
            }
            println(new StringBuffer("synPredMatched").append(synPredBlock.ID).append(" = true;").toString());
            println("inputState.guessing++;");
            if (this.grammar.debuggingOutput && ((this.grammar instanceof ParserGrammar) || (this.grammar instanceof LexerGrammar))) {
                println("fireSyntacticPredicateStarted();");
            }
            this.syntacticPredLevel++;
            println("try {");
            this.tabs++;
            gen(synPredBlock);
            this.tabs--;
            println("}");
            println(new StringBuffer("catch (").append(this.exceptionThrown).append(" pe) {").toString());
            this.tabs++;
            println(new StringBuffer("synPredMatched").append(synPredBlock.ID).append(" = false;").toString());
            this.tabs--;
            println("}");
            if (this.grammar instanceof TreeWalkerGrammar) {
                println(new StringBuffer("_t = __t").append(synPredBlock.ID).append(";").toString());
            } else {
                println(new StringBuffer("rewind(_m").append(synPredBlock.ID).append(");").toString());
            }
            _println("inputState.guessing--;");
            if (this.grammar.debuggingOutput && ((this.grammar instanceof ParserGrammar) || (this.grammar instanceof LexerGrammar))) {
                println(new StringBuffer("if (synPredMatched").append(synPredBlock.ID).append(Constant.AFTER_QUTO).toString());
                println("  fireSyntacticPredicateSucceeded();");
                println(PsiKeyword.ELSE);
                println("  fireSyntacticPredicateFailed();");
            }
            this.syntacticPredLevel--;
            this.tabs--;
            println("}");
            println(new StringBuffer("if ( synPredMatched").append(synPredBlock.ID).append(" ) {").toString());
        } finally {
            this.defaultLine = i;
        }
    }

    public void genTokenStrings() {
        TokenSymbol tokenSymbol;
        int i = this.defaultLine;
        try {
            this.defaultLine = -999;
            println("");
            println("public static final String[] _tokenNames = {");
            this.tabs++;
            Vector vocabulary = this.grammar.tokenManager.getVocabulary();
            for (int i2 = 0; i2 < vocabulary.size(); i2++) {
                String strStripFrontBack = (String) vocabulary.elementAt(i2);
                if (strStripFrontBack == null) {
                    strStripFrontBack = new StringBuffer().append("<").append(String.valueOf(i2)).append(">").toString();
                }
                if (!strStripFrontBack.startsWith("\"") && !strStripFrontBack.startsWith("<") && (tokenSymbol = this.grammar.tokenManager.getTokenSymbol(strStripFrontBack)) != null && tokenSymbol.getParaphrase() != null) {
                    strStripFrontBack = StringUtils.stripFrontBack(tokenSymbol.getParaphrase(), "\"", "\"");
                }
                print(this.charFormatter.literalString(strStripFrontBack));
                if (i2 != vocabulary.size() - 1) {
                    _print(",");
                }
                _println("");
            }
            this.tabs--;
            println("};");
        } finally {
            this.defaultLine = i;
        }
    }

    protected void genTokenASTNodeMap() {
        TokenSymbol tokenSymbol;
        int i = this.defaultLine;
        try {
            this.defaultLine = -999;
            println("");
            println("protected void buildTokenTypeASTClassMap() {");
            this.tabs++;
            Vector vocabulary = this.grammar.tokenManager.getVocabulary();
            int i2 = 0;
            boolean z = false;
            for (int i3 = 0; i3 < vocabulary.size(); i3++) {
                String str = (String) vocabulary.elementAt(i3);
                if (str != null && (tokenSymbol = this.grammar.tokenManager.getTokenSymbol(str)) != null && tokenSymbol.getASTNodeType() != null) {
                    i2++;
                    if (!z) {
                        println("tokenTypeToASTClassMap = new Hashtable();");
                        z = true;
                    }
                    println(new StringBuffer().append("tokenTypeToASTClassMap.put(new Integer(").append(tokenSymbol.getTokenType()).append("), ").append(tokenSymbol.getASTNodeType()).append(".class);").toString());
                }
            }
            if (i2 == 0) {
                println("tokenTypeToASTClassMap=null;");
            }
            this.tabs--;
            println("};");
        } finally {
            this.defaultLine = i;
        }
    }

    protected void genTokenTypes(TokenManager tokenManager) throws IOException {
        int i = this.defaultLine;
        try {
            this.defaultLine = -999;
            this.currentOutput = getPrintWriterManager().setupOutput(this.antlrTool, new StringBuffer().append(tokenManager.getName()).append(TokenTypesFileSuffix).toString());
            this.tabs = 0;
            genHeader();
            try {
                this.defaultLine = this.behavior.getHeaderActionLine("");
                println(this.behavior.getHeaderAction(""));
                this.defaultLine = -999;
                println(new StringBuffer("public interface ").append(tokenManager.getName()).append(TokenTypesFileSuffix).append(" {").toString());
                this.tabs++;
                Vector vocabulary = tokenManager.getVocabulary();
                println("int EOF = 1;");
                println("int NULL_TREE_LOOKAHEAD = 3;");
                for (int i2 = 4; i2 < vocabulary.size(); i2++) {
                    String str = (String) vocabulary.elementAt(i2);
                    if (str != null) {
                        if (str.startsWith("\"")) {
                            StringLiteralSymbol stringLiteralSymbol = (StringLiteralSymbol) tokenManager.getTokenSymbol(str);
                            if (stringLiteralSymbol == null) {
                                this.antlrTool.panic(new StringBuffer().append("String literal ").append(str).append(" not in symbol table").toString());
                            } else if (stringLiteralSymbol.label != null) {
                                println(new StringBuffer().append("int ").append(stringLiteralSymbol.label).append(" = ").append(i2).append(";").toString());
                            } else {
                                String strMangleLiteral = mangleLiteral(str);
                                if (strMangleLiteral != null) {
                                    println(new StringBuffer().append("int ").append(strMangleLiteral).append(" = ").append(i2).append(";").toString());
                                    stringLiteralSymbol.label = strMangleLiteral;
                                } else {
                                    println(new StringBuffer().append("// ").append(str).append(" = ").append(i2).toString());
                                }
                            }
                        } else if (!str.startsWith("<")) {
                            println(new StringBuffer().append("int ").append(str).append(" = ").append(i2).append(";").toString());
                        }
                    }
                }
                this.tabs--;
                println("}");
                getPrintWriterManager().finishOutput();
                exitIfError();
                this.defaultLine = i;
            } catch (Throwable th) {
                this.defaultLine = -999;
                throw th;
            }
        } catch (Throwable th2) {
            this.defaultLine = i;
            throw th2;
        }
    }

    @Override // antlr.CodeGenerator
    public String getASTCreateString(Vector vector) {
        if (vector.size() == 0) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(new StringBuffer("(").append(this.labeledElementASTType).append(")astFactory.make( (new ASTArray(").append(vector.size()).append("))").toString());
        for (int i = 0; i < vector.size(); i++) {
            stringBuffer.append(new StringBuffer(".add(").append(vector.elementAt(i)).append(Constant.AFTER_QUTO).toString());
        }
        stringBuffer.append(Constant.AFTER_QUTO);
        return stringBuffer.toString();
    }

    @Override // antlr.CodeGenerator
    public String getASTCreateString(GrammarAtom grammarAtom, String str) {
        if (grammarAtom != null && grammarAtom.getASTNodeType() != null) {
            return new StringBuffer("(").append(grammarAtom.getASTNodeType()).append(")astFactory.create(").append(str).append(",\"").append(grammarAtom.getASTNodeType()).append("\")").toString();
        }
        return getASTCreateString(str);
    }

    public String getASTCreateString(String str) {
        String str2 = "";
        if (str == null) {
            str = "";
        }
        int i = 0;
        for (int i2 = 0; i2 < str.length(); i2++) {
            if (str.charAt(i2) == ',') {
                i++;
            }
        }
        if (i >= 2) {
            return new StringBuffer("(").append(this.labeledElementASTType).append(")astFactory.create(").append(str).append(Constant.AFTER_QUTO).toString();
        }
        int iIndexOf = str.indexOf(44);
        str.lastIndexOf(44);
        TokenSymbol tokenSymbol = this.grammar.tokenManager.getTokenSymbol(i > 0 ? str.substring(0, iIndexOf) : str);
        if (tokenSymbol != null) {
            String aSTNodeType = tokenSymbol.getASTNodeType();
            if (i == 0) {
                str2 = ",\"\"";
            }
            if (aSTNodeType != null) {
                return new StringBuffer("(").append(aSTNodeType).append(")astFactory.create(").append(str).append(str2).append(",\"").append(aSTNodeType).append("\")").toString();
            }
        }
        return this.labeledElementASTType.equals("AST") ? new StringBuffer("astFactory.create(").append(str).append(Constant.AFTER_QUTO).toString() : new StringBuffer("(").append(this.labeledElementASTType).append(")astFactory.create(").append(str).append(Constant.AFTER_QUTO).toString();
    }

    protected String getLookaheadTestExpression(Lookahead[] lookaheadArr, int i) {
        StringBuffer stringBuffer = new StringBuffer(100);
        stringBuffer.append("(");
        int i2 = 1;
        boolean z = true;
        while (i2 <= i) {
            BitSet bitSet = lookaheadArr[i2].fset;
            if (!z) {
                stringBuffer.append(") && (");
            }
            if (lookaheadArr[i2].containsEpsilon()) {
                stringBuffer.append("true");
            } else {
                stringBuffer.append(getLookaheadTestTerm(i2, bitSet));
            }
            i2++;
            z = false;
        }
        stringBuffer.append(Constant.AFTER_QUTO);
        return stringBuffer.toString();
    }

    protected String getLookaheadTestExpression(Alternative alternative, int i) {
        int i2 = alternative.lookaheadDepth;
        if (i2 == Integer.MAX_VALUE) {
            i2 = this.grammar.maxk;
        }
        if (i == 0) {
            return "( true )";
        }
        return new StringBuffer("(").append(getLookaheadTestExpression(alternative.cache, i2)).append(Constant.AFTER_QUTO).toString();
    }

    protected String getLookaheadTestTerm(int i, BitSet bitSet) {
        String strLookaheadString = lookaheadString(i);
        int[] array = bitSet.toArray();
        if (elementsAreRange(array)) {
            return getRangeExpression(i, array);
        }
        int iDegree = bitSet.degree();
        if (iDegree == 0) {
            return "true";
        }
        if (iDegree >= this.bitsetTestThreshold) {
            return new StringBuffer().append(getBitsetName(markBitsetForGen(bitSet))).append(".member(").append(strLookaheadString).append(Constant.AFTER_QUTO).toString();
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (int i2 = 0; i2 < array.length; i2++) {
            String valueString = getValueString(array[i2]);
            if (i2 > 0) {
                stringBuffer.append("||");
            }
            stringBuffer.append(strLookaheadString);
            stringBuffer.append("==");
            stringBuffer.append(valueString);
        }
        return stringBuffer.toString();
    }

    public String getRangeExpression(int i, int[] iArr) {
        if (!elementsAreRange(iArr)) {
            this.antlrTool.panic("getRangeExpression called with non-range");
        }
        return new StringBuffer("(").append(lookaheadString(i)).append(" >= ").append(getValueString(iArr[0])).append(" && ").append(lookaheadString(i)).append(" <= ").append(getValueString(iArr[iArr.length - 1])).append(Constant.AFTER_QUTO).toString();
    }

    private String getValueString(int i) {
        if (this.grammar instanceof LexerGrammar) {
            return this.charFormatter.literalChar(i);
        }
        TokenSymbol tokenSymbolAt = this.grammar.tokenManager.getTokenSymbolAt(i);
        if (tokenSymbolAt == null) {
            return new StringBuffer("").append(i).toString();
        }
        String id2 = tokenSymbolAt.getId();
        if (!(tokenSymbolAt instanceof StringLiteralSymbol)) {
            return id2;
        }
        String label = ((StringLiteralSymbol) tokenSymbolAt).getLabel();
        return (label == null && (label = mangleLiteral(id2)) == null) ? String.valueOf(i) : label;
    }

    protected boolean lookaheadIsEmpty(Alternative alternative, int i) {
        int i2 = alternative.lookaheadDepth;
        if (i2 == Integer.MAX_VALUE) {
            i2 = this.grammar.maxk;
        }
        for (int i3 = 1; i3 <= i2 && i3 <= i; i3++) {
            if (alternative.cache[i3].fset.degree() != 0) {
                return false;
            }
        }
        return true;
    }

    private String lookaheadString(int i) {
        if (this.grammar instanceof TreeWalkerGrammar) {
            return "_t.getType()";
        }
        return new StringBuffer("LA(").append(i).append(Constant.AFTER_QUTO).toString();
    }

    private String mangleLiteral(String str) {
        String string = this.antlrTool.literalsPrefix;
        for (int i = 1; i < str.length() - 1; i++) {
            if (!Character.isLetter(str.charAt(i)) && str.charAt(i) != '_') {
                return null;
            }
            string = new StringBuffer().append(string).append(str.charAt(i)).toString();
        }
        return this.antlrTool.upperCaseMangledLiterals ? string.toUpperCase() : string;
    }

    /* JADX WARN: Code duplicated, block: B:15:0x0032  */
    @Override // antlr.CodeGenerator
    public String mapTreeId(String str, ActionTransInfo actionTransInfo) {
        boolean z;
        StringBuffer stringBuffer;
        StringBuffer stringBufferAppend;
        if (this.currentRule == null) {
            return str;
        }
        if (this.grammar instanceof TreeWalkerGrammar) {
            z = true;
            if (this.grammar.buildAST) {
                if (str.length() <= 3 || str.lastIndexOf("_in") != str.length() - 3) {
                    z = false;
                } else {
                    str = str.substring(0, str.length() - 3);
                }
            }
        } else {
            z = false;
        }
        for (int i = 0; i < this.currentRule.labeledElements.size(); i++) {
            if (((AlternativeElement) this.currentRule.labeledElements.elementAt(i)).getLabel().equals(str)) {
                return z ? str : new StringBuffer().append(str).append("_AST").toString();
            }
        }
        String str2 = (String) this.treeVariableMap.get(str);
        if (str2 != null) {
            if (str2 == NONUNIQUE) {
                this.antlrTool.error(new StringBuffer("Ambiguous reference to AST element ").append(str).append(" in rule ").append(this.currentRule.getRuleName()).toString());
                return null;
            }
            if (!str2.equals(this.currentRule.getRuleName())) {
                return z ? new StringBuffer().append(str2).append("_in").toString() : str2;
            }
            this.antlrTool.error(new StringBuffer("Ambiguous reference to AST element ").append(str).append(" in rule ").append(this.currentRule.getRuleName()).toString());
            return null;
        }
        if (str.equals(this.currentRule.getRuleName())) {
            if (z) {
                stringBuffer = new StringBuffer();
                stringBufferAppend = stringBuffer.append(str).append("_AST_in");
            } else {
                stringBuffer = new StringBuffer();
                stringBufferAppend = stringBuffer.append(str).append("_AST");
            }
            str = stringBufferAppend.toString();
            if (actionTransInfo != null && !z) {
                actionTransInfo.refRuleRoot = str;
            }
        }
        return str;
    }

    /* JADX WARN: Code duplicated, block: B:14:0x0024  */
    private void mapTreeVariable(AlternativeElement alternativeElement, String str) {
        String str2;
        if (alternativeElement instanceof TreeElement) {
            mapTreeVariable(((TreeElement) alternativeElement).root, str);
            return;
        }
        if (alternativeElement.getLabel() != null) {
            str2 = null;
        } else if (alternativeElement instanceof TokenRefElement) {
            str2 = ((TokenRefElement) alternativeElement).atomText;
        } else if (alternativeElement instanceof RuleRefElement) {
            str2 = ((RuleRefElement) alternativeElement).targetRule;
        } else {
            str2 = null;
        }
        if (str2 != null) {
            if (this.treeVariableMap.get(str2) != null) {
                this.treeVariableMap.remove(str2);
                this.treeVariableMap.put(str2, NONUNIQUE);
            } else {
                this.treeVariableMap.put(str2, str);
            }
        }
    }

    @Override // antlr.CodeGenerator
    protected String processActionForSpecialSymbols(String str, int i, RuleBlock ruleBlock, ActionTransInfo actionTransInfo) {
        if (str == null || str.length() == 0) {
            return null;
        }
        if (this.grammar == null) {
            return str;
        }
        if ((!this.grammar.buildAST || str.indexOf(35) == -1) && !(this.grammar instanceof TreeWalkerGrammar) && (!((this.grammar instanceof LexerGrammar) || (this.grammar instanceof ParserGrammar)) || str.indexOf(36) == -1)) {
            return str;
        }
        ActionLexer actionLexer = new ActionLexer(str, ruleBlock, this, actionTransInfo);
        actionLexer.setLineOffset(i);
        actionLexer.setFilename(this.grammar.getFilename());
        actionLexer.setTool(this.antlrTool);
        try {
            actionLexer.mACTION(true);
            return actionLexer.getTokenObject().getText();
        } catch (CharStreamException unused) {
            this.antlrTool.panic(new StringBuffer("Error reading action:").append(str).toString());
            return str;
        } catch (RecognitionException e) {
            actionLexer.reportError(e);
            return str;
        } catch (TokenStreamException unused2) {
            this.antlrTool.panic(new StringBuffer("Error reading action:").append(str).toString());
            return str;
        }
    }

    private void setupGrammarParameters(Grammar grammar) {
        Token option;
        String strStripFrontBack;
        Token option2;
        String strStripFrontBack2;
        if (grammar instanceof ParserGrammar) {
            this.labeledElementASTType = "AST";
            if (grammar.hasOption("ASTLabelType") && (option2 = grammar.getOption("ASTLabelType")) != null && (strStripFrontBack2 = StringUtils.stripFrontBack(option2.getText(), "\"", "\"")) != null) {
                this.labeledElementASTType = strStripFrontBack2;
            }
            this.labeledElementType = "Token ";
            this.labeledElementInit = "null";
            this.commonExtraArgs = "";
            this.commonExtraParams = "";
            this.commonLocalVars = "";
            this.lt1Value = "LT(1)";
            this.exceptionThrown = "RecognitionException";
            this.throwNoViable = "throw new NoViableAltException(LT(1), getFilename());";
            return;
        }
        if (grammar instanceof LexerGrammar) {
            this.labeledElementType = "char ";
            this.labeledElementInit = "'\\0'";
            this.commonExtraArgs = "";
            this.commonExtraParams = "boolean _createToken";
            this.commonLocalVars = "int _ttype; Token _token=null; int _begin=text.length();";
            this.lt1Value = "LA(1)";
            this.exceptionThrown = "RecognitionException";
            this.throwNoViable = "throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());";
            return;
        }
        if (grammar instanceof TreeWalkerGrammar) {
            this.labeledElementASTType = "AST";
            this.labeledElementType = "AST";
            if (grammar.hasOption("ASTLabelType") && (option = grammar.getOption("ASTLabelType")) != null && (strStripFrontBack = StringUtils.stripFrontBack(option.getText(), "\"", "\"")) != null) {
                this.labeledElementASTType = strStripFrontBack;
                this.labeledElementType = strStripFrontBack;
            }
            if (!grammar.hasOption("ASTLabelType")) {
                grammar.setOption("ASTLabelType", new Token(6, "AST"));
            }
            this.labeledElementInit = "null";
            this.commonExtraArgs = "_t";
            this.commonExtraParams = "AST _t";
            this.commonLocalVars = "";
            this.lt1Value = new StringBuffer("(").append(this.labeledElementASTType).append(")_t").toString();
            this.exceptionThrown = "RecognitionException";
            this.throwNoViable = "throw new NoViableAltException(_t);";
            return;
        }
        this.antlrTool.panic("Unknown grammar type");
    }

    public JavaCodeGeneratorPrintWriterManager getPrintWriterManager() {
        if (this.printWriterManager == null) {
            this.printWriterManager = new DefaultJavaCodeGeneratorPrintWriterManager();
        }
        return this.printWriterManager;
    }

    public void setPrintWriterManager(JavaCodeGeneratorPrintWriterManager javaCodeGeneratorPrintWriterManager) {
        this.printWriterManager = javaCodeGeneratorPrintWriterManager;
    }

    @Override // antlr.CodeGenerator
    public void setTool(Tool tool) {
        super.setTool(tool);
    }
}
