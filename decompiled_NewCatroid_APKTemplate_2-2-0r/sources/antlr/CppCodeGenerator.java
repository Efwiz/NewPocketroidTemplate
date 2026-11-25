package antlr;

import antlr.actions.cpp.ActionLexer;
import antlr.collections.impl.BitSet;
import antlr.collections.impl.Vector;
import com.huawei.hms.framework.common.ContainerUtils;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import name.antonsmirnov.firmata.FormatHelper;
import org.antlr.runtime.BaseRecognizer;
import org.apache.commons.lang3.SystemProperties;
import org.catrobat.catroid.common.Constants;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiLiteralUtil;

/* JADX INFO: loaded from: classes.dex */
public class CppCodeGenerator extends CodeGenerator {
    public static final int caseSizeThreshold = 127;
    private static final String postIncludeCpp = "post_include_cpp";
    private static final String postIncludeHpp = "post_include_hpp";
    private static final String preIncludeCpp = "pre_include_cpp";
    private static final String preIncludeHpp = "pre_include_hpp";
    private Vector astTypes;
    String commonExtraArgs;
    String commonExtraParams;
    String commonLocalVars;
    String currentASTResult;
    RuleBlock currentRule;
    String exceptionThrown;
    String labeledElementASTInit;
    String labeledElementASTType;
    String labeledElementInit;
    String labeledElementType;
    String lt1Value;
    protected String outputFile;
    protected int outputLine;
    private Vector semPreds;
    String throwNoViable;
    protected static final String NONUNIQUE = new String();
    private static String namespaceStd = "ANTLR_USE_NAMESPACE(std)";
    private static String namespaceAntlr = "ANTLR_USE_NAMESPACE(antlr)";
    private static NameSpace nameSpace = null;
    boolean DEBUG_CPP_CODE_GENERATOR = false;
    protected int syntacticPredLevel = 0;
    protected boolean genAST = false;
    protected boolean saveText = false;
    protected boolean genHashLines = true;
    protected boolean noConstructors = false;
    boolean usingCustomAST = false;
    Hashtable treeVariableMap = new Hashtable();
    Hashtable declaredASTVariables = new Hashtable();
    int astVarNumber = 1;

    protected void genMatch(BitSet bitSet) {
    }

    public CppCodeGenerator() {
        this.charFormatter = new CppCharFormatter();
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

    protected int countLines(String str) {
        int i = 0;
        for (int i2 = 0; i2 < str.length(); i2++) {
            if (str.charAt(i2) == '\n') {
                i++;
            }
        }
        return i;
    }

    @Override // antlr.CodeGenerator
    protected void _print(String str) {
        if (str != null) {
            this.outputLine += countLines(str);
            this.currentOutput.print(str);
        }
    }

    @Override // antlr.CodeGenerator
    protected void _printAction(String str) {
        if (str != null) {
            this.outputLine += countLines(str) + 1;
            super._printAction(str);
        }
    }

    public void printAction(Token token) {
        if (token != null) {
            genLineNo(token.getLine());
            printTabs();
            _printAction(processActionForSpecialSymbols(token.getText(), token.getLine(), null, null));
            genLineNo2();
        }
    }

    public void printHeaderAction(String str) {
        Token token = (Token) this.behavior.headerActions.get(str);
        if (token != null) {
            genLineNo(token.getLine());
            println(processActionForSpecialSymbols(token.getText(), token.getLine(), null, null));
            genLineNo2();
        }
    }

    @Override // antlr.CodeGenerator
    protected void _println(String str) {
        if (str != null) {
            this.outputLine += countLines(str) + 1;
            this.currentOutput.println(str);
        }
    }

    @Override // antlr.CodeGenerator
    protected void println(String str) {
        if (str != null) {
            printTabs();
            this.outputLine += countLines(str) + 1;
            this.currentOutput.println(str);
        }
    }

    public void genLineNo(int i) {
        if (i == 0) {
            i++;
        }
        if (this.genHashLines) {
            _println(new StringBuffer("#line ").append(i).append(" \"").append(this.antlrTool.fileMinusPath(this.antlrTool.grammarFile)).append("\"").toString());
        }
    }

    public void genLineNo(GrammarElement grammarElement) {
        if (grammarElement != null) {
            genLineNo(grammarElement.getLine());
        }
    }

    public void genLineNo(Token token) {
        if (token != null) {
            genLineNo(token.getLine());
        }
    }

    public void genLineNo2() {
        if (this.genHashLines) {
            _println(new StringBuffer("#line ").append(this.outputLine + 1).append(" \"").append(this.outputFile).append("\"").toString());
        }
    }

    private boolean charIsDigit(String str, int i) {
        return i < str.length() && Character.isDigit(str.charAt(i));
    }

    /* JADX WARN: Code duplicated, block: B:100:0x02ce  */
    /* JADX WARN: Code duplicated, block: B:101:0x02e9  */
    /* JADX WARN: Code duplicated, block: B:83:0x0210  */
    /* JADX WARN: Code duplicated, block: B:86:0x0230  */
    /* JADX WARN: Code duplicated, block: B:87:0x0233  */
    /* JADX WARN: Code duplicated, block: B:90:0x026f  */
    /* JADX WARN: Code duplicated, block: B:92:0x0275  */
    /* JADX WARN: Code duplicated, block: B:95:0x0291  */
    /* JADX WARN: Code duplicated, block: B:99:0x02b5  */
    private String convertJavaToCppString(String str, boolean z) {
        int size;
        int i;
        char c;
        boolean z2;
        String string;
        String str2;
        String str3 = new String();
        if (z) {
            if (!str.startsWith(FormatHelper.QUOTE) || !str.endsWith(FormatHelper.QUOTE)) {
                this.antlrTool.error(new StringBuffer("Invalid character literal: '").append(str).append(FormatHelper.QUOTE).toString());
            }
        } else if (!str.startsWith("\"") || !str.endsWith("\"")) {
            this.antlrTool.error(new StringBuffer("Invalid character string: '").append(str).append(FormatHelper.QUOTE).toString());
        }
        String strSubstring = str.substring(1, str.length() - 1);
        String str4 = "";
        if (this.grammar instanceof LexerGrammar) {
            size = ((LexerGrammar) this.grammar).charVocabulary.size() - 1;
            if (size > 255) {
                str4 = "L";
            }
        } else {
            size = 255;
        }
        int i2 = 0;
        int iCharAt = 0;
        while (i2 < strSubstring.length()) {
            if (strSubstring.charAt(i2) == '\\') {
                int i3 = i2 + 1;
                if (strSubstring.length() == i3) {
                    this.antlrTool.error(new StringBuffer("Invalid escape in char literal: '").append(str).append("' looking at '").append(strSubstring.substring(i2)).append(FormatHelper.QUOTE).toString());
                }
                char cCharAt = strSubstring.charAt(i3);
                if (cCharAt != '\"' && cCharAt != '\'' && cCharAt != '\\') {
                    if (cCharAt != 'f') {
                        if (cCharAt != 'n') {
                            if (cCharAt != 'r') {
                                if (cCharAt != 'a') {
                                    if (cCharAt != 'b') {
                                        if (cCharAt != 't') {
                                            if (cCharAt == 'u') {
                                                int i4 = i2 + 5;
                                                if (i4 < strSubstring.length()) {
                                                    iCharAt = (Character.digit(strSubstring.charAt(i2 + 2), 16) * 4096) + (Character.digit(strSubstring.charAt(i2 + 3), 16) * 256) + (Character.digit(strSubstring.charAt(i2 + 4), 16) * 16) + Character.digit(strSubstring.charAt(i4), 16);
                                                    i2 += 6;
                                                } else {
                                                    this.antlrTool.error(new StringBuffer("Invalid escape in char literal: '").append(str).append("' looking at '").append(strSubstring.substring(i2)).append(FormatHelper.QUOTE).toString());
                                                }
                                            } else {
                                                switch (cCharAt) {
                                                    case '0':
                                                    case '1':
                                                    case '2':
                                                    case '3':
                                                        i = i2 + 2;
                                                        if (charIsDigit(strSubstring, i)) {
                                                            int i5 = i2 + 3;
                                                            if (charIsDigit(strSubstring, i5)) {
                                                                iCharAt = ((strSubstring.charAt(i3) - '0') * 64) + ((strSubstring.charAt(i) - '0') * 8) + (strSubstring.charAt(i5) - '0');
                                                                i2 += 4;
                                                            } else {
                                                                iCharAt = ((strSubstring.charAt(i3) - '0') * 8) + (strSubstring.charAt(i) - '0');
                                                                i2 = i5;
                                                            }
                                                        } else {
                                                            iCharAt = strSubstring.charAt(i3) - '0';
                                                        }
                                                        break;
                                                    case '4':
                                                    case '5':
                                                    case '6':
                                                    case '7':
                                                        int i6 = i2 + 2;
                                                        if (charIsDigit(strSubstring, i6)) {
                                                            strSubstring.charAt(i3);
                                                            strSubstring.charAt(i6);
                                                            i2 += 3;
                                                            break;
                                                        } else {
                                                            strSubstring.charAt(i3);
                                                            i2 = i6;
                                                            break;
                                                        }
                                                    default:
                                                        this.antlrTool.error(new StringBuffer("Unhandled escape in char literal: '").append(str).append("' looking at '").append(strSubstring.substring(i2)).append(FormatHelper.QUOTE).toString());
                                                        iCharAt = 0;
                                                        break;
                                                }
                                            }
                                        } else {
                                            i2 += 2;
                                            iCharAt = 9;
                                        }
                                    } else {
                                        i2 += 2;
                                        iCharAt = 8;
                                    }
                                } else {
                                    i2 += 2;
                                    iCharAt = 7;
                                }
                            } else {
                                i2 += 2;
                                iCharAt = 13;
                            }
                        } else {
                            i2 += 2;
                            iCharAt = 10;
                        }
                    } else {
                        i2 += 2;
                        iCharAt = 12;
                    }
                } else {
                    iCharAt = strSubstring.charAt(i3);
                    i2 += 2;
                }
                if ((this.grammar instanceof LexerGrammar) && iCharAt > size) {
                    if (32 > iCharAt && iCharAt < 127) {
                        string = this.charFormatter.escapeChar(iCharAt, true);
                    } else {
                        string = new StringBuffer(PsiLiteralUtil.HEX_PREFIX).append(Integer.toString(iCharAt, 16)).toString();
                    }
                    Tool tool = this.antlrTool;
                    StringBuffer stringBuffer = new StringBuffer("Character out of range in ");
                    if (z) {
                        str2 = "char literal";
                    } else {
                        str2 = "string constant";
                    }
                    tool.error(stringBuffer.append(str2).append(": '").append(strSubstring).append(FormatHelper.QUOTE).toString());
                    this.antlrTool.error(new StringBuffer("Vocabulary size: ").append(size).append(" Character ").append(string).toString());
                }
                if (z) {
                    if (i2 != strSubstring.length()) {
                        this.antlrTool.error(new StringBuffer("Invalid char literal: '").append(str).append(FormatHelper.QUOTE).toString());
                    }
                    c = 255;
                    if (size <= 255) {
                        z2 = true;
                        str3 = new StringBuffer("L'").append(this.charFormatter.escapeChar(iCharAt, true)).append(FormatHelper.QUOTE).toString();
                    } else if (iCharAt > 255 && (iCharAt & 128) != 0) {
                        z2 = true;
                        str3 = new StringBuffer("static_cast<unsigned char>('").append(this.charFormatter.escapeChar(iCharAt, true)).append("')").toString();
                    } else {
                        z2 = true;
                        str3 = new StringBuffer(FormatHelper.QUOTE).append(this.charFormatter.escapeChar(iCharAt, true)).append(FormatHelper.QUOTE).toString();
                    }
                } else {
                    c = 255;
                    z2 = true;
                    str3 = new StringBuffer().append(str3).append(this.charFormatter.escapeChar(iCharAt, true)).toString();
                }
            } else {
                i = i2 + 1;
                iCharAt = strSubstring.charAt(i2);
            }
            i2 = i;
            if (this.grammar instanceof LexerGrammar) {
                if (32 > iCharAt) {
                    string = new StringBuffer(PsiLiteralUtil.HEX_PREFIX).append(Integer.toString(iCharAt, 16)).toString();
                } else {
                    string = new StringBuffer(PsiLiteralUtil.HEX_PREFIX).append(Integer.toString(iCharAt, 16)).toString();
                }
                Tool tool2 = this.antlrTool;
                StringBuffer stringBuffer2 = new StringBuffer("Character out of range in ");
                if (z) {
                    str2 = "char literal";
                } else {
                    str2 = "string constant";
                }
                tool2.error(stringBuffer2.append(str2).append(": '").append(strSubstring).append(FormatHelper.QUOTE).toString());
                this.antlrTool.error(new StringBuffer("Vocabulary size: ").append(size).append(" Character ").append(string).toString());
            }
            if (z) {
                if (i2 != strSubstring.length()) {
                    this.antlrTool.error(new StringBuffer("Invalid char literal: '").append(str).append(FormatHelper.QUOTE).toString());
                }
                c = 255;
                if (size <= 255) {
                    z2 = true;
                    str3 = new StringBuffer("L'").append(this.charFormatter.escapeChar(iCharAt, true)).append(FormatHelper.QUOTE).toString();
                } else if (iCharAt > 255) {
                    z2 = true;
                    str3 = new StringBuffer(FormatHelper.QUOTE).append(this.charFormatter.escapeChar(iCharAt, true)).append(FormatHelper.QUOTE).toString();
                } else {
                    z2 = true;
                    str3 = new StringBuffer(FormatHelper.QUOTE).append(this.charFormatter.escapeChar(iCharAt, true)).append(FormatHelper.QUOTE).toString();
                }
            } else {
                c = 255;
                z2 = true;
                str3 = new StringBuffer().append(str3).append(this.charFormatter.escapeChar(iCharAt, true)).toString();
            }
        }
        return !z ? new StringBuffer().append(str4).append("\"").append(str3).append("\"").toString() : str3;
    }

    @Override // antlr.CodeGenerator
    public void gen() {
        try {
            Enumeration enumerationElements = this.behavior.grammars.elements();
            while (enumerationElements.hasMoreElements()) {
                Grammar grammar = (Grammar) enumerationElements.nextElement();
                if (grammar.debuggingOutput) {
                    this.antlrTool.error(new StringBuffer().append(grammar.getFilename()).append(": C++ mode does not support -debug").toString());
                }
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
        if (this.DEBUG_CODE_GENERATOR || this.DEBUG_CPP_CODE_GENERATOR) {
            System.out.println(new StringBuffer("genAction(").append(actionElement).append(Constant.AFTER_QUTO).toString());
        }
        if (actionElement.isSemPred) {
            genSemPred(actionElement.actionText, actionElement.line);
            return;
        }
        if (this.grammar.hasSyntacticPredicate) {
            println("if ( inputState->guessing==0 ) {");
            this.tabs++;
        }
        ActionTransInfo actionTransInfo = new ActionTransInfo();
        String strProcessActionForSpecialSymbols = processActionForSpecialSymbols(actionElement.actionText, actionElement.getLine(), this.currentRule, actionTransInfo);
        if (actionTransInfo.refRuleRoot != null) {
            println(new StringBuffer().append(actionTransInfo.refRuleRoot).append(" = ").append(this.labeledElementASTType).append("(currentAST.root);").toString());
        }
        genLineNo(actionElement);
        printAction(strProcessActionForSpecialSymbols);
        genLineNo2();
        if (actionTransInfo.assignToRoot) {
            println(new StringBuffer("currentAST.root = ").append(actionTransInfo.refRuleRoot).append(";").toString());
            println(new StringBuffer("if ( ").append(actionTransInfo.refRuleRoot).append("!=").append(this.labeledElementASTInit).append(" &&").toString());
            this.tabs++;
            println(new StringBuffer().append(actionTransInfo.refRuleRoot).append("->getFirstChild() != ").append(this.labeledElementASTInit).append(" )").toString());
            println(new StringBuffer("  currentAST.child = ").append(actionTransInfo.refRuleRoot).append("->getFirstChild();").toString());
            this.tabs--;
            println(PsiKeyword.ELSE);
            this.tabs++;
            println(new StringBuffer("currentAST.child = ").append(actionTransInfo.refRuleRoot).append(";").toString());
            this.tabs--;
            println("currentAST.advanceChildToEnd();");
        }
        if (this.grammar.hasSyntacticPredicate) {
            this.tabs--;
            println("}");
        }
    }

    @Override // antlr.CodeGenerator
    public void gen(AlternativeBlock alternativeBlock) {
        if (this.DEBUG_CODE_GENERATOR || this.DEBUG_CPP_CODE_GENERATOR) {
            System.out.println(new StringBuffer("gen(").append(alternativeBlock).append(Constant.AFTER_QUTO).toString());
        }
        println("{");
        genBlockPreamble(alternativeBlock);
        genBlockInitAction(alternativeBlock);
        String str = this.currentASTResult;
        if (alternativeBlock.getLabel() != null) {
            this.currentASTResult = alternativeBlock.getLabel();
        }
        this.grammar.theLLkAnalyzer.deterministic(alternativeBlock);
        genBlockFinish(genCommonBlock(alternativeBlock, true), this.throwNoViable);
        println("}");
        this.currentASTResult = str;
    }

    @Override // antlr.CodeGenerator
    public void gen(BlockEndElement blockEndElement) {
        if (this.DEBUG_CODE_GENERATOR || this.DEBUG_CPP_CODE_GENERATOR) {
            System.out.println(new StringBuffer("genRuleEnd(").append(blockEndElement).append(Constant.AFTER_QUTO).toString());
        }
    }

    @Override // antlr.CodeGenerator
    public void gen(CharLiteralElement charLiteralElement) {
        if (this.DEBUG_CODE_GENERATOR || this.DEBUG_CPP_CODE_GENERATOR) {
            System.out.println(new StringBuffer("genChar(").append(charLiteralElement).append(Constant.AFTER_QUTO).toString());
        }
        if (!(this.grammar instanceof LexerGrammar)) {
            this.antlrTool.error(new StringBuffer("cannot ref character literals in grammar: ").append(charLiteralElement).toString());
        }
        if (charLiteralElement.getLabel() != null) {
            println(new StringBuffer().append(charLiteralElement.getLabel()).append(" = ").append(this.lt1Value).append(";").toString());
        }
        boolean z = this.saveText;
        boolean z2 = z && charLiteralElement.getAutoGenType() == 1;
        this.saveText = z2;
        if (!z2 || charLiteralElement.getAutoGenType() == 3) {
            println("_saveIndex = text.length();");
        }
        print(charLiteralElement.not ? "matchNot(" : "match(");
        _print(convertJavaToCppString(charLiteralElement.atomText, true));
        _println(" /* charlit */ );");
        if (!this.saveText || charLiteralElement.getAutoGenType() == 3) {
            println("text.erase(_saveIndex);");
        }
        this.saveText = z;
    }

    @Override // antlr.CodeGenerator
    public void gen(CharRangeElement charRangeElement) {
        if (this.DEBUG_CODE_GENERATOR || this.DEBUG_CPP_CODE_GENERATOR) {
            System.out.println(new StringBuffer("genCharRangeElement(").append(charRangeElement.beginText).append("..").append(charRangeElement.endText).append(Constant.AFTER_QUTO).toString());
        }
        if (!(this.grammar instanceof LexerGrammar)) {
            this.antlrTool.error(new StringBuffer("cannot ref character range in grammar: ").append(charRangeElement).toString());
        }
        if (charRangeElement.getLabel() != null && this.syntacticPredLevel == 0) {
            println(new StringBuffer().append(charRangeElement.getLabel()).append(" = ").append(this.lt1Value).append(";").toString());
        }
        boolean z = (this.grammar instanceof LexerGrammar) && (!this.saveText || charRangeElement.getAutoGenType() == 3);
        if (z) {
            println("_saveIndex=text.length();");
        }
        println(new StringBuffer("matchRange(").append(convertJavaToCppString(charRangeElement.beginText, true)).append(",").append(convertJavaToCppString(charRangeElement.endText, true)).append(");").toString());
        if (z) {
            println("text.erase(_saveIndex);");
        }
    }

    @Override // antlr.CodeGenerator
    public void gen(LexerGrammar lexerGrammar) throws IOException {
        if (lexerGrammar.debuggingOutput) {
            this.semPreds = new Vector();
        }
        if (lexerGrammar.charVocabulary.size() > 256) {
            this.antlrTool.warning(new StringBuffer().append(lexerGrammar.getFilename()).append(": Vocabularies of this size still experimental in C++ mode (vocabulary size now: ").append(lexerGrammar.charVocabulary.size()).append(Constant.AFTER_QUTO).toString());
        }
        setGrammar(lexerGrammar);
        if (!(this.grammar instanceof LexerGrammar)) {
            this.antlrTool.panic("Internal error generating lexer");
        }
        genBody(lexerGrammar);
        genInclude(lexerGrammar);
    }

    /* JADX WARN: Code duplicated, block: B:33:0x00df  */
    /* JADX WARN: Code duplicated, block: B:37:0x00e7  */
    @Override // antlr.CodeGenerator
    public void gen(OneOrMoreBlock oneOrMoreBlock) {
        String string;
        String string2;
        boolean z;
        if (this.DEBUG_CODE_GENERATOR || this.DEBUG_CPP_CODE_GENERATOR) {
            System.out.println(new StringBuffer("gen+(").append(oneOrMoreBlock).append(Constant.AFTER_QUTO).toString());
        }
        println("{ // ( ... )+");
        genBlockPreamble(oneOrMoreBlock);
        if (oneOrMoreBlock.getLabel() != null) {
            string = new StringBuffer("_cnt_").append(oneOrMoreBlock.getLabel()).toString();
        } else {
            string = new StringBuffer("_cnt").append(oneOrMoreBlock.ID).toString();
        }
        println(new StringBuffer("int ").append(string).append("=0;").toString());
        if (oneOrMoreBlock.getLabel() != null) {
            string2 = oneOrMoreBlock.getLabel();
        } else {
            string2 = new StringBuffer("_loop").append(oneOrMoreBlock.ID).toString();
        }
        println("for (;;) {");
        this.tabs++;
        genBlockInitAction(oneOrMoreBlock);
        String str = this.currentASTResult;
        if (oneOrMoreBlock.getLabel() != null) {
            this.currentASTResult = oneOrMoreBlock.getLabel();
        }
        this.grammar.theLLkAnalyzer.deterministic(oneOrMoreBlock);
        int i = this.grammar.maxk;
        if (!oneOrMoreBlock.greedy && oneOrMoreBlock.exitLookaheadDepth <= this.grammar.maxk && oneOrMoreBlock.exitCache[oneOrMoreBlock.exitLookaheadDepth].containsEpsilon()) {
            i = oneOrMoreBlock.exitLookaheadDepth;
        } else {
            if (oneOrMoreBlock.greedy || oneOrMoreBlock.exitLookaheadDepth != Integer.MAX_VALUE) {
                z = false;
            }
            if (z) {
                if (!this.DEBUG_CODE_GENERATOR || this.DEBUG_CPP_CODE_GENERATOR) {
                    System.out.println(new StringBuffer("nongreedy (...)+ loop; exit depth is ").append(oneOrMoreBlock.exitLookaheadDepth).toString());
                }
                String lookaheadTestExpression = getLookaheadTestExpression(oneOrMoreBlock.exitCache, i);
                println("// nongreedy exit test");
                println(new StringBuffer("if ( ").append(string).append(">=1 && ").append(lookaheadTestExpression).append(") goto ").append(string2).append(";").toString());
            }
            genBlockFinish(genCommonBlock(oneOrMoreBlock, false), new StringBuffer("if ( ").append(string).append(">=1 ) { goto ").append(string2).append("; } else {").append(this.throwNoViable).append("}").toString());
            println(new StringBuffer().append(string).append("++;").toString());
            this.tabs--;
            println("}");
            println(new StringBuffer().append(string2).append(":;").toString());
            println("}  // ( ... )+");
            this.currentASTResult = str;
        }
        z = true;
        if (z) {
            if (!this.DEBUG_CODE_GENERATOR) {
                System.out.println(new StringBuffer("nongreedy (...)+ loop; exit depth is ").append(oneOrMoreBlock.exitLookaheadDepth).toString());
            } else {
                System.out.println(new StringBuffer("nongreedy (...)+ loop; exit depth is ").append(oneOrMoreBlock.exitLookaheadDepth).toString());
            }
            String lookaheadTestExpression2 = getLookaheadTestExpression(oneOrMoreBlock.exitCache, i);
            println("// nongreedy exit test");
            println(new StringBuffer("if ( ").append(string).append(">=1 && ").append(lookaheadTestExpression2).append(") goto ").append(string2).append(";").toString());
        }
        genBlockFinish(genCommonBlock(oneOrMoreBlock, false), new StringBuffer("if ( ").append(string).append(">=1 ) { goto ").append(string2).append("; } else {").append(this.throwNoViable).append("}").toString());
        println(new StringBuffer().append(string).append("++;").toString());
        this.tabs--;
        println("}");
        println(new StringBuffer().append(string2).append(":;").toString());
        println("}  // ( ... )+");
        this.currentASTResult = str;
    }

    @Override // antlr.CodeGenerator
    public void gen(ParserGrammar parserGrammar) throws IOException {
        if (parserGrammar.debuggingOutput) {
            this.semPreds = new Vector();
        }
        setGrammar(parserGrammar);
        if (!(this.grammar instanceof ParserGrammar)) {
            this.antlrTool.panic("Internal error generating parser");
        }
        genBody(parserGrammar);
        genInclude(parserGrammar);
    }

    @Override // antlr.CodeGenerator
    public void gen(RuleRefElement ruleRefElement) {
        if (this.DEBUG_CODE_GENERATOR || this.DEBUG_CPP_CODE_GENERATOR) {
            System.out.println(new StringBuffer("genRR(").append(ruleRefElement).append(Constant.AFTER_QUTO).toString());
        }
        RuleSymbol ruleSymbol = (RuleSymbol) this.grammar.getSymbol(ruleRefElement.targetRule);
        if (ruleSymbol == null || !ruleSymbol.isDefined()) {
            this.antlrTool.error(new StringBuffer("Rule '").append(ruleRefElement.targetRule).append("' is not defined").toString(), this.grammar.getFilename(), ruleRefElement.getLine(), ruleRefElement.getColumn());
            return;
        }
        if (!(ruleSymbol instanceof RuleSymbol)) {
            this.antlrTool.error(new StringBuffer(FormatHelper.QUOTE).append(ruleRefElement.targetRule).append("' does not name a grammar rule").toString(), this.grammar.getFilename(), ruleRefElement.getLine(), ruleRefElement.getColumn());
            return;
        }
        genErrorTryForElement(ruleRefElement);
        if ((this.grammar instanceof TreeWalkerGrammar) && ruleRefElement.getLabel() != null && this.syntacticPredLevel == 0) {
            println(new StringBuffer().append(ruleRefElement.getLabel()).append(" = (_t == ASTNULL) ? ").append(this.labeledElementASTInit).append(" : ").append(this.lt1Value).append(";").toString());
        }
        if ((this.grammar instanceof LexerGrammar) && (!this.saveText || ruleRefElement.getAutoGenType() == 3)) {
            println("_saveIndex = text.length();");
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
            println("text.erase(_saveIndex);");
        }
        if (this.syntacticPredLevel == 0) {
            boolean z = this.grammar.hasSyntacticPredicate && ((this.grammar.buildAST && ruleRefElement.getLabel() != null) || (this.genAST && ruleRefElement.getAutoGenType() == 1));
            if (z) {
                println("if (inputState->guessing==0) {");
                this.tabs++;
            }
            if (this.grammar.buildAST && ruleRefElement.getLabel() != null) {
                println(new StringBuffer().append(ruleRefElement.getLabel()).append("_AST = returnAST;").toString());
            }
            if (this.genAST) {
                int autoGenType = ruleRefElement.getAutoGenType();
                if (autoGenType != 1) {
                    if (autoGenType == 2) {
                        this.antlrTool.error("Internal: encountered ^ after rule reference");
                    }
                } else if (this.usingCustomAST) {
                    println(new StringBuffer("astFactory->addASTChild(currentAST, ").append(namespaceAntlr).append("RefAST(returnAST));").toString());
                } else {
                    println("astFactory->addASTChild( currentAST, returnAST );");
                }
            }
            if ((this.grammar instanceof LexerGrammar) && ruleRefElement.getLabel() != null) {
                println(new StringBuffer().append(ruleRefElement.getLabel()).append("=_returnToken;").toString());
            }
            if (z) {
                this.tabs--;
                println("}");
            }
        }
        genErrorCatchForElement(ruleRefElement);
    }

    /* JADX WARN: Code duplicated, block: B:17:0x0061  */
    @Override // antlr.CodeGenerator
    public void gen(StringLiteralElement stringLiteralElement) {
        boolean z;
        if (this.DEBUG_CODE_GENERATOR || this.DEBUG_CPP_CODE_GENERATOR) {
            System.out.println(new StringBuffer("genString(").append(stringLiteralElement).append(Constant.AFTER_QUTO).toString());
        }
        if (stringLiteralElement.getLabel() != null && this.syntacticPredLevel == 0) {
            println(new StringBuffer().append(stringLiteralElement.getLabel()).append(" = ").append(this.lt1Value).append(";").toString());
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
            println("_t = _t->getNextSibling();");
        }
    }

    @Override // antlr.CodeGenerator
    public void gen(TokenRangeElement tokenRangeElement) {
        genErrorTryForElement(tokenRangeElement);
        if (tokenRangeElement.getLabel() != null && this.syntacticPredLevel == 0) {
            println(new StringBuffer().append(tokenRangeElement.getLabel()).append(" = ").append(this.lt1Value).append(";").toString());
        }
        genElementAST(tokenRangeElement);
        println(new StringBuffer("matchRange(").append(tokenRangeElement.beginText).append(",").append(tokenRangeElement.endText).append(");").toString());
        genErrorCatchForElement(tokenRangeElement);
    }

    @Override // antlr.CodeGenerator
    public void gen(TokenRefElement tokenRefElement) {
        if (this.DEBUG_CODE_GENERATOR || this.DEBUG_CPP_CODE_GENERATOR) {
            System.out.println(new StringBuffer("genTokenRef(").append(tokenRefElement).append(Constant.AFTER_QUTO).toString());
        }
        if (this.grammar instanceof LexerGrammar) {
            this.antlrTool.panic("Token reference found in lexer");
        }
        genErrorTryForElement(tokenRefElement);
        if (tokenRefElement.getLabel() != null && this.syntacticPredLevel == 0) {
            println(new StringBuffer().append(tokenRefElement.getLabel()).append(" = ").append(this.lt1Value).append(";").toString());
        }
        genElementAST(tokenRefElement);
        genMatch(tokenRefElement);
        genErrorCatchForElement(tokenRefElement);
        if (this.grammar instanceof TreeWalkerGrammar) {
            println("_t = _t->getNextSibling();");
        }
    }

    @Override // antlr.CodeGenerator
    public void gen(TreeElement treeElement) {
        println(new StringBuffer().append(this.labeledElementType).append(" __t").append(treeElement.ID).append(" = _t;").toString());
        if (treeElement.root.getLabel() != null) {
            println(new StringBuffer().append(treeElement.root.getLabel()).append(" = (_t == ").append(this.labeledElementType).append("(ASTNULL)) ? ").append(this.labeledElementASTInit).append(" : _t;").toString());
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
            println(new StringBuffer().append(namespaceAntlr).append("ASTPair __currentAST").append(treeElement.ID).append(" = currentAST;").toString());
            println("currentAST.root = currentAST.child;");
            println(new StringBuffer("currentAST.child = ").append(this.labeledElementASTInit).append(";").toString());
        }
        if (treeElement.root instanceof WildcardElement) {
            println(new StringBuffer("if ( _t == ASTNULL ) throw ").append(namespaceAntlr).append("MismatchedTokenException();").toString());
        } else {
            genMatch(treeElement.root);
        }
        println("_t = _t->getFirstChild();");
        for (int i = 0; i < treeElement.getAlternatives().size(); i++) {
            for (AlternativeElement alternativeElement = treeElement.getAlternativeAt(i).head; alternativeElement != null; alternativeElement = alternativeElement.next) {
                alternativeElement.generate();
            }
        }
        if (this.grammar.buildAST) {
            println(new StringBuffer("currentAST = __currentAST").append(treeElement.ID).append(";").toString());
        }
        println(new StringBuffer("_t = __t").append(treeElement.ID).append(";").toString());
        println("_t = _t->getNextSibling();");
    }

    @Override // antlr.CodeGenerator
    public void gen(TreeWalkerGrammar treeWalkerGrammar) throws IOException {
        setGrammar(treeWalkerGrammar);
        if (!(this.grammar instanceof TreeWalkerGrammar)) {
            this.antlrTool.panic("Internal error generating tree-walker");
        }
        genBody(treeWalkerGrammar);
        genInclude(treeWalkerGrammar);
    }

    @Override // antlr.CodeGenerator
    public void gen(WildcardElement wildcardElement) {
        if (wildcardElement.getLabel() != null && this.syntacticPredLevel == 0) {
            println(new StringBuffer().append(wildcardElement.getLabel()).append(" = ").append(this.lt1Value).append(";").toString());
        }
        genElementAST(wildcardElement);
        if (this.grammar instanceof TreeWalkerGrammar) {
            println(new StringBuffer("if ( _t == ").append(this.labeledElementASTInit).append(" ) throw ").append(namespaceAntlr).append("MismatchedTokenException();").toString());
        } else if (this.grammar instanceof LexerGrammar) {
            if ((this.grammar instanceof LexerGrammar) && (!this.saveText || wildcardElement.getAutoGenType() == 3)) {
                println("_saveIndex = text.length();");
            }
            println("matchNot(EOF/*_CHAR*/);");
            if ((this.grammar instanceof LexerGrammar) && (!this.saveText || wildcardElement.getAutoGenType() == 3)) {
                println("text.erase(_saveIndex);");
            }
        } else {
            println(new StringBuffer("matchNot(").append(getValueString(1)).append(");").toString());
        }
        if (this.grammar instanceof TreeWalkerGrammar) {
            println("_t = _t->getNextSibling();");
        }
    }

    /* JADX WARN: Code duplicated, block: B:29:0x009c  */
    /* JADX WARN: Code duplicated, block: B:33:0x00a4  */
    @Override // antlr.CodeGenerator
    public void gen(ZeroOrMoreBlock zeroOrMoreBlock) {
        String string;
        boolean z;
        if (this.DEBUG_CODE_GENERATOR || this.DEBUG_CPP_CODE_GENERATOR) {
            System.out.println(new StringBuffer("gen*(").append(zeroOrMoreBlock).append(Constant.AFTER_QUTO).toString());
        }
        println("{ // ( ... )*");
        genBlockPreamble(zeroOrMoreBlock);
        if (zeroOrMoreBlock.getLabel() != null) {
            string = zeroOrMoreBlock.getLabel();
        } else {
            string = new StringBuffer("_loop").append(zeroOrMoreBlock.ID).toString();
        }
        println("for (;;) {");
        this.tabs++;
        genBlockInitAction(zeroOrMoreBlock);
        String str = this.currentASTResult;
        if (zeroOrMoreBlock.getLabel() != null) {
            this.currentASTResult = zeroOrMoreBlock.getLabel();
        }
        this.grammar.theLLkAnalyzer.deterministic(zeroOrMoreBlock);
        int i = this.grammar.maxk;
        if (!zeroOrMoreBlock.greedy && zeroOrMoreBlock.exitLookaheadDepth <= this.grammar.maxk && zeroOrMoreBlock.exitCache[zeroOrMoreBlock.exitLookaheadDepth].containsEpsilon()) {
            i = zeroOrMoreBlock.exitLookaheadDepth;
        } else {
            if (zeroOrMoreBlock.greedy || zeroOrMoreBlock.exitLookaheadDepth != Integer.MAX_VALUE) {
                z = false;
            }
            if (z) {
                if (!this.DEBUG_CODE_GENERATOR || this.DEBUG_CPP_CODE_GENERATOR) {
                    System.out.println(new StringBuffer("nongreedy (...)* loop; exit depth is ").append(zeroOrMoreBlock.exitLookaheadDepth).toString());
                }
                String lookaheadTestExpression = getLookaheadTestExpression(zeroOrMoreBlock.exitCache, i);
                println("// nongreedy exit test");
                println(new StringBuffer("if (").append(lookaheadTestExpression).append(") goto ").append(string).append(";").toString());
            }
            genBlockFinish(genCommonBlock(zeroOrMoreBlock, false), new StringBuffer("goto ").append(string).append(";").toString());
            this.tabs--;
            println("}");
            println(new StringBuffer().append(string).append(":;").toString());
            println("} // ( ... )*");
            this.currentASTResult = str;
        }
        z = true;
        if (z) {
            if (!this.DEBUG_CODE_GENERATOR) {
                System.out.println(new StringBuffer("nongreedy (...)* loop; exit depth is ").append(zeroOrMoreBlock.exitLookaheadDepth).toString());
            } else {
                System.out.println(new StringBuffer("nongreedy (...)* loop; exit depth is ").append(zeroOrMoreBlock.exitLookaheadDepth).toString());
            }
            String lookaheadTestExpression2 = getLookaheadTestExpression(zeroOrMoreBlock.exitCache, i);
            println("// nongreedy exit test");
            println(new StringBuffer("if (").append(lookaheadTestExpression2).append(") goto ").append(string).append(";").toString());
        }
        genBlockFinish(genCommonBlock(zeroOrMoreBlock, false), new StringBuffer("goto ").append(string).append(";").toString());
        this.tabs--;
        println("}");
        println(new StringBuffer().append(string).append(":;").toString());
        println("} // ( ... )*");
        this.currentASTResult = str;
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
            println("try {      // for error handling");
            this.tabs++;
        }
        for (AlternativeElement alternativeElement = alternative.head; !(alternativeElement instanceof BlockEndElement); alternativeElement = alternativeElement.next) {
            alternativeElement.generate();
        }
        if (this.genAST) {
            if (alternativeBlock instanceof RuleBlock) {
                RuleBlock ruleBlock = (RuleBlock) alternativeBlock;
                if (this.usingCustomAST) {
                    println(new StringBuffer().append(ruleBlock.getRuleName()).append("_AST = ").append(this.labeledElementASTType).append("(currentAST.root);").toString());
                } else {
                    println(new StringBuffer().append(ruleBlock.getRuleName()).append("_AST = currentAST.root;").toString());
                }
            } else if (alternativeBlock.getLabel() != null) {
                this.antlrTool.warning("Labeled subrules are not implemented", this.grammar.getFilename(), alternativeBlock.getLine(), alternativeBlock.getColumn());
            }
        }
        if (alternative.exceptionSpec != null) {
            this.tabs--;
            println("}");
            genErrorHandler(alternative.exceptionSpec);
        }
        this.genAST = z;
        this.saveText = z3;
        this.treeVariableMap = hashtable;
    }

    protected void genBitsets(Vector vector, int i, String str) {
        TokenManager tokenManager = this.grammar.tokenManager;
        println("");
        for (int i2 = 0; i2 < vector.size(); i2++) {
            BitSet bitSet = (BitSet) vector.elementAt(i2);
            bitSet.growToInclude(i);
            println(new StringBuffer("const unsigned long ").append(str).append(getBitsetName(i2)).append("_data_[] = { ").append(bitSet.toStringOfHalfWords()).append(" };").toString());
            String string = "// ";
            for (int i3 = 0; i3 < tokenManager.getVocabulary().size(); i3++) {
                if (bitSet.member(i3)) {
                    if (!(this.grammar instanceof LexerGrammar)) {
                        string = new StringBuffer().append(string).append(tokenManager.getTokenStringAt(i3)).append(" ").toString();
                    } else if (32 <= i3 && i3 < 127 && i3 != 92) {
                        string = new StringBuffer().append(string).append(this.charFormatter.escapeChar(i3, true)).append(" ").toString();
                    } else {
                        string = new StringBuffer().append(string).append(PsiLiteralUtil.HEX_PREFIX).append(Integer.toString(i3, 16)).append(" ").toString();
                    }
                    if (string.length() > 70) {
                        println(string);
                        string = "// ";
                    }
                }
            }
            if (string != "// ") {
                println(string);
            }
            println(new StringBuffer("const ").append(namespaceAntlr).append("BitSet ").append(str).append(getBitsetName(i2)).append("(").append(getBitsetName(i2)).append("_data_,").append(bitSet.size() / 32).append(");").toString());
        }
    }

    protected void genBitsetsHeader(Vector vector, int i) {
        println("");
        for (int i2 = 0; i2 < vector.size(); i2++) {
            ((BitSet) vector.elementAt(i2)).growToInclude(i);
            println(new StringBuffer("static const unsigned long ").append(getBitsetName(i2)).append("_data_[];").toString());
            println(new StringBuffer("static const ").append(namespaceAntlr).append("BitSet ").append(getBitsetName(i2)).append(";").toString());
        }
    }

    private void genBlockFinish(CppBlockFinishingInfo cppBlockFinishingInfo, String str) {
        if (cppBlockFinishingInfo.needAnErrorClause && (cppBlockFinishingInfo.generatedAnIf || cppBlockFinishingInfo.generatedSwitch)) {
            if (cppBlockFinishingInfo.generatedAnIf) {
                println("else {");
            } else {
                println("{");
            }
            this.tabs++;
            println(str);
            this.tabs--;
            println("}");
        }
        if (cppBlockFinishingInfo.postscript != null) {
            println(cppBlockFinishingInfo.postscript);
        }
    }

    protected void genBlockInitAction(AlternativeBlock alternativeBlock) {
        if (alternativeBlock.initAction != null) {
            genLineNo(alternativeBlock);
            printAction(processActionForSpecialSymbols(alternativeBlock.initAction, alternativeBlock.line, this.currentRule, null));
            genLineNo2();
        }
    }

    /* JADX WARN: Code duplicated, block: B:25:0x0089  */
    /* JADX WARN: Code duplicated, block: B:34:0x00d9  */
    /* JADX WARN: Code duplicated, block: B:36:0x00df  */
    /* JADX WARN: Code duplicated, block: B:39:0x00e8  */
    /* JADX WARN: Code duplicated, block: B:42:0x0112  */
    /* JADX WARN: Code duplicated, block: B:52:0x013e A[SYNTHETIC] */
    protected void genBlockPreamble(AlternativeBlock alternativeBlock) {
        if (alternativeBlock instanceof RuleBlock) {
            RuleBlock ruleBlock = (RuleBlock) alternativeBlock;
            if (ruleBlock.labeledElements != null) {
                for (int i = 0; i < ruleBlock.labeledElements.size(); i++) {
                    AlternativeElement alternativeElement = (AlternativeElement) ruleBlock.labeledElements.elementAt(i);
                    boolean z = alternativeElement instanceof RuleRefElement;
                    if (!z && (!(alternativeElement instanceof AlternativeBlock) || (alternativeElement instanceof RuleBlock) || (alternativeElement instanceof SynPredBlock))) {
                        println(new StringBuffer().append(this.labeledElementType).append(" ").append(alternativeElement.getLabel()).append(" = ").append(this.labeledElementInit).append(";").toString());
                        if (this.grammar.buildAST) {
                            if (alternativeElement instanceof GrammarAtom) {
                                GrammarAtom grammarAtom = (GrammarAtom) alternativeElement;
                                if (grammarAtom.getASTNodeType() != null) {
                                    genASTDeclaration(alternativeElement, new StringBuffer("Ref").append(grammarAtom.getASTNodeType()).toString());
                                } else {
                                    genASTDeclaration(alternativeElement);
                                }
                            } else {
                                genASTDeclaration(alternativeElement);
                            }
                        }
                    } else if (!z) {
                        AlternativeBlock alternativeBlock2 = (AlternativeBlock) alternativeElement;
                        if (alternativeBlock2.not && this.analyzer.subruleCanBeInverted(alternativeBlock2, this.grammar instanceof LexerGrammar)) {
                            println(new StringBuffer().append(this.labeledElementType).append(" ").append(alternativeElement.getLabel()).append(" = ").append(this.labeledElementInit).append(";").toString());
                            if (this.grammar.buildAST) {
                                genASTDeclaration(alternativeElement);
                            }
                        } else {
                            if (this.grammar.buildAST) {
                                genASTDeclaration(alternativeElement);
                            }
                            if (this.grammar instanceof LexerGrammar) {
                                println(new StringBuffer().append(namespaceAntlr).append("RefToken ").append(alternativeElement.getLabel()).append(";").toString());
                            }
                            if (this.grammar instanceof TreeWalkerGrammar) {
                                println(new StringBuffer().append(this.labeledElementType).append(" ").append(alternativeElement.getLabel()).append(" = ").append(this.labeledElementInit).append(";").toString());
                            }
                        }
                    } else {
                        if (this.grammar.buildAST) {
                            genASTDeclaration(alternativeElement);
                        }
                        if (this.grammar instanceof LexerGrammar) {
                            println(new StringBuffer().append(namespaceAntlr).append("RefToken ").append(alternativeElement.getLabel()).append(";").toString());
                        }
                        if (this.grammar instanceof TreeWalkerGrammar) {
                            println(new StringBuffer().append(this.labeledElementType).append(" ").append(alternativeElement.getLabel()).append(" = ").append(this.labeledElementInit).append(";").toString());
                        }
                    }
                }
            }
        }
    }

    public void genBody(LexerGrammar lexerGrammar) throws IOException {
        String string;
        this.outputFile = new StringBuffer().append(this.grammar.getClassName()).append(".cpp").toString();
        this.outputLine = 1;
        this.currentOutput = this.antlrTool.openOutputFile(this.outputFile);
        this.genAST = false;
        this.saveText = true;
        this.tabs = 0;
        genHeader(this.outputFile);
        printHeaderAction(preIncludeCpp);
        println(new StringBuffer("#include \"").append(this.grammar.getClassName()).append(".hpp\"").toString());
        println("#include <antlr/CharBuffer.hpp>");
        println("#include <antlr/TokenStreamException.hpp>");
        println("#include <antlr/TokenStreamIOException.hpp>");
        println("#include <antlr/TokenStreamRecognitionException.hpp>");
        println("#include <antlr/CharStreamException.hpp>");
        println("#include <antlr/CharStreamIOException.hpp>");
        println("#include <antlr/NoViableAltForCharException.hpp>");
        if (this.grammar.debuggingOutput) {
            println("#include <antlr/DebuggingInputBuffer.hpp>");
        }
        println("");
        printHeaderAction(postIncludeCpp);
        NameSpace nameSpace2 = nameSpace;
        if (nameSpace2 != null) {
            nameSpace2.emitDeclarations(this.currentOutput);
        }
        printAction(this.grammar.preambleAction);
        if (this.grammar.superClass != null) {
            string = this.grammar.superClass;
        } else {
            String superClass = this.grammar.getSuperClass();
            if (superClass.lastIndexOf(46) != -1) {
                superClass = superClass.substring(superClass.lastIndexOf(46) + 1);
            }
            string = new StringBuffer().append(namespaceAntlr).append(superClass).toString();
        }
        if (this.noConstructors) {
            println("#if 0");
            println("// constructor creation turned of with 'noConstructor' option");
        }
        println(new StringBuffer().append(this.grammar.getClassName()).append("::").append(this.grammar.getClassName()).append("(").append(namespaceStd).append("istream& in)").toString());
        this.tabs++;
        if (this.grammar.debuggingOutput) {
            println(new StringBuffer(": ").append(string).append("(new ").append(namespaceAntlr).append("DebuggingInputBuffer(new ").append(namespaceAntlr).append("CharBuffer(in)),").append(lexerGrammar.caseSensitive).append(Constant.AFTER_QUTO).toString());
        } else {
            println(new StringBuffer(": ").append(string).append("(new ").append(namespaceAntlr).append("CharBuffer(in),").append(lexerGrammar.caseSensitive).append(Constant.AFTER_QUTO).toString());
        }
        this.tabs--;
        println("{");
        this.tabs++;
        if (this.grammar.debuggingOutput) {
            println("setRuleNames(_ruleNames);");
            println("setSemPredNames(_semPredNames);");
            println("setupDebugging();");
        }
        println("initLiterals();");
        this.tabs--;
        println("}");
        println("");
        println(new StringBuffer().append(this.grammar.getClassName()).append("::").append(this.grammar.getClassName()).append("(").append(namespaceAntlr).append("InputBuffer& ib)").toString());
        this.tabs++;
        if (this.grammar.debuggingOutput) {
            println(new StringBuffer(": ").append(string).append("(new ").append(namespaceAntlr).append("DebuggingInputBuffer(ib),").append(lexerGrammar.caseSensitive).append(Constant.AFTER_QUTO).toString());
        } else {
            println(new StringBuffer(": ").append(string).append("(ib,").append(lexerGrammar.caseSensitive).append(Constant.AFTER_QUTO).toString());
        }
        this.tabs--;
        println("{");
        this.tabs++;
        if (this.grammar.debuggingOutput) {
            println("setRuleNames(_ruleNames);");
            println("setSemPredNames(_semPredNames);");
            println("setupDebugging();");
        }
        println("initLiterals();");
        this.tabs--;
        println("}");
        println("");
        println(new StringBuffer().append(this.grammar.getClassName()).append("::").append(this.grammar.getClassName()).append("(const ").append(namespaceAntlr).append("LexerSharedInputState& state)").toString());
        this.tabs++;
        println(new StringBuffer(": ").append(string).append("(state,").append(lexerGrammar.caseSensitive).append(Constant.AFTER_QUTO).toString());
        this.tabs--;
        println("{");
        this.tabs++;
        if (this.grammar.debuggingOutput) {
            println("setRuleNames(_ruleNames);");
            println("setSemPredNames(_semPredNames);");
            println("setupDebugging();");
        }
        println("initLiterals();");
        this.tabs--;
        println("}");
        println("");
        if (this.noConstructors) {
            println("// constructor creation turned of with 'noConstructor' option");
            println("#endif");
        }
        println(new StringBuffer("void ").append(this.grammar.getClassName()).append("::initLiterals()").toString());
        println("{");
        this.tabs++;
        Enumeration tokenSymbolKeys = this.grammar.tokenManager.getTokenSymbolKeys();
        while (tokenSymbolKeys.hasMoreElements()) {
            String str = (String) tokenSymbolKeys.nextElement();
            if (str.charAt(0) == '\"') {
                TokenSymbol tokenSymbol = this.grammar.tokenManager.getTokenSymbol(str);
                if (tokenSymbol instanceof StringLiteralSymbol) {
                    StringLiteralSymbol stringLiteralSymbol = (StringLiteralSymbol) tokenSymbol;
                    println(new StringBuffer("literals[").append(stringLiteralSymbol.getId()).append("] = ").append(stringLiteralSymbol.getTokenType()).append(";").toString());
                }
            }
        }
        this.tabs--;
        println("}");
        if (this.grammar.debuggingOutput) {
            println(new StringBuffer("const char* ").append(this.grammar.getClassName()).append("::_ruleNames[] = {").toString());
            this.tabs++;
            Enumeration enumerationElements = this.grammar.rules.elements();
            while (enumerationElements.hasMoreElements()) {
                GrammarSymbol grammarSymbol = (GrammarSymbol) enumerationElements.nextElement();
                if (grammarSymbol instanceof RuleSymbol) {
                    println(new StringBuffer("\"").append(((RuleSymbol) grammarSymbol).getId()).append("\",").toString());
                }
            }
            println("0");
            this.tabs--;
            println("};");
        }
        genNextToken();
        Enumeration enumerationElements2 = this.grammar.rules.elements();
        int i = 0;
        while (enumerationElements2.hasMoreElements()) {
            RuleSymbol ruleSymbol = (RuleSymbol) enumerationElements2.nextElement();
            if (!ruleSymbol.getId().equals("mnextToken")) {
                genRule(ruleSymbol, false, i, new StringBuffer().append(this.grammar.getClassName()).append("::").toString());
                i++;
            }
            exitIfError();
        }
        if (this.grammar.debuggingOutput) {
            genSemPredMap(new StringBuffer().append(this.grammar.getClassName()).append("::").toString());
        }
        genBitsets(this.bitsetsUsed, ((LexerGrammar) this.grammar).charVocabulary.size(), new StringBuffer().append(this.grammar.getClassName()).append("::").toString());
        println("");
        NameSpace nameSpace3 = nameSpace;
        if (nameSpace3 != null) {
            nameSpace3.emitClosures(this.currentOutput);
        }
        this.currentOutput.close();
        this.currentOutput = null;
    }

    public void genInitFactory(Grammar grammar) {
        String str;
        if (grammar.buildAST) {
            str = "factory ";
        } else {
            str = "";
        }
        println(new StringBuffer("void ").append(grammar.getClassName()).append("::initializeASTFactory( ").append(namespaceAntlr).append("ASTFactory& ").append(str).append(Constant.AFTER_QUTO).toString());
        println("{");
        this.tabs++;
        if (grammar.buildAST) {
            TokenManager tokenManager = this.grammar.tokenManager;
            Enumeration tokenSymbolKeys = tokenManager.getTokenSymbolKeys();
            while (tokenSymbolKeys.hasMoreElements()) {
                String str2 = (String) tokenSymbolKeys.nextElement();
                TokenSymbol tokenSymbol = tokenManager.getTokenSymbol(str2);
                if (tokenSymbol.getASTNodeType() != null) {
                    this.astTypes.ensureCapacity(tokenSymbol.getTokenType());
                    String str3 = (String) this.astTypes.elementAt(tokenSymbol.getTokenType());
                    if (str3 == null) {
                        this.astTypes.setElementAt(tokenSymbol.getASTNodeType(), tokenSymbol.getTokenType());
                    } else if (!tokenSymbol.getASTNodeType().equals(str3)) {
                        this.antlrTool.warning(new StringBuffer("Token ").append(str2).append(" taking most specific AST type").toString(), this.grammar.getFilename(), 1, 1);
                        this.antlrTool.warning(new StringBuffer("  using ").append(str3).append(" ignoring ").append(tokenSymbol.getASTNodeType()).toString(), this.grammar.getFilename(), 1, 1);
                    }
                }
            }
            for (int i = 0; i < this.astTypes.size(); i++) {
                String str4 = (String) this.astTypes.elementAt(i);
                if (str4 != null) {
                    println(new StringBuffer("factory.registerFactory(").append(i).append(", \"").append(str4).append("\", ").append(str4).append("::factory);").toString());
                }
            }
            println(new StringBuffer("factory.setMaxNodeType(").append(this.grammar.tokenManager.maxTokenType()).append(");").toString());
        }
        this.tabs--;
        println("}");
    }

    public void genBody(ParserGrammar parserGrammar) throws IOException {
        String string;
        this.outputFile = new StringBuffer().append(this.grammar.getClassName()).append(".cpp").toString();
        this.outputLine = 1;
        this.currentOutput = this.antlrTool.openOutputFile(this.outputFile);
        this.genAST = this.grammar.buildAST;
        this.tabs = 0;
        genHeader(this.outputFile);
        printHeaderAction(preIncludeCpp);
        println(new StringBuffer("#include \"").append(this.grammar.getClassName()).append(".hpp\"").toString());
        println("#include <antlr/NoViableAltException.hpp>");
        println("#include <antlr/SemanticException.hpp>");
        println("#include <antlr/ASTFactory.hpp>");
        printHeaderAction(postIncludeCpp);
        NameSpace nameSpace2 = nameSpace;
        if (nameSpace2 != null) {
            nameSpace2.emitDeclarations(this.currentOutput);
        }
        printAction(this.grammar.preambleAction);
        if (this.grammar.superClass != null) {
            string = this.grammar.superClass;
        } else {
            String superClass = this.grammar.getSuperClass();
            if (superClass.lastIndexOf(46) != -1) {
                superClass = superClass.substring(superClass.lastIndexOf(46) + 1);
            }
            string = new StringBuffer().append(namespaceAntlr).append(superClass).toString();
        }
        if (this.grammar.debuggingOutput) {
            println(new StringBuffer("const char* ").append(this.grammar.getClassName()).append("::_ruleNames[] = {").toString());
            this.tabs++;
            Enumeration enumerationElements = this.grammar.rules.elements();
            while (enumerationElements.hasMoreElements()) {
                GrammarSymbol grammarSymbol = (GrammarSymbol) enumerationElements.nextElement();
                if (grammarSymbol instanceof RuleSymbol) {
                    println(new StringBuffer("\"").append(((RuleSymbol) grammarSymbol).getId()).append("\",").toString());
                }
            }
            println("0");
            this.tabs--;
            println("};");
        }
        if (this.noConstructors) {
            println("#if 0");
            println("// constructor creation turned of with 'noConstructor' option");
        }
        print(new StringBuffer().append(this.grammar.getClassName()).append("::").append(this.grammar.getClassName()).toString());
        println(new StringBuffer("(").append(namespaceAntlr).append("TokenBuffer& tokenBuf, int k)").toString());
        println(new StringBuffer(": ").append(string).append("(tokenBuf,k)").toString());
        println("{");
        println("}");
        println("");
        print(new StringBuffer().append(this.grammar.getClassName()).append("::").append(this.grammar.getClassName()).toString());
        println(new StringBuffer("(").append(namespaceAntlr).append("TokenBuffer& tokenBuf)").toString());
        println(new StringBuffer(": ").append(string).append("(tokenBuf,").append(this.grammar.maxk).append(Constant.AFTER_QUTO).toString());
        println("{");
        println("}");
        println("");
        print(new StringBuffer().append(this.grammar.getClassName()).append("::").append(this.grammar.getClassName()).toString());
        println(new StringBuffer("(").append(namespaceAntlr).append("TokenStream& lexer, int k)").toString());
        println(new StringBuffer(": ").append(string).append("(lexer,k)").toString());
        println("{");
        println("}");
        println("");
        print(new StringBuffer().append(this.grammar.getClassName()).append("::").append(this.grammar.getClassName()).toString());
        println(new StringBuffer("(").append(namespaceAntlr).append("TokenStream& lexer)").toString());
        println(new StringBuffer(": ").append(string).append("(lexer,").append(this.grammar.maxk).append(Constant.AFTER_QUTO).toString());
        println("{");
        println("}");
        println("");
        print(new StringBuffer().append(this.grammar.getClassName()).append("::").append(this.grammar.getClassName()).toString());
        println(new StringBuffer("(const ").append(namespaceAntlr).append("ParserSharedInputState& state)").toString());
        println(new StringBuffer(": ").append(string).append("(state,").append(this.grammar.maxk).append(Constant.AFTER_QUTO).toString());
        println("{");
        println("}");
        println("");
        if (this.noConstructors) {
            println("// constructor creation turned of with 'noConstructor' option");
            println("#endif");
        }
        this.astTypes = new Vector();
        Enumeration enumerationElements2 = this.grammar.rules.elements();
        int i = 0;
        while (enumerationElements2.hasMoreElements()) {
            GrammarSymbol grammarSymbol2 = (GrammarSymbol) enumerationElements2.nextElement();
            if (grammarSymbol2 instanceof RuleSymbol) {
                RuleSymbol ruleSymbol = (RuleSymbol) grammarSymbol2;
                genRule(ruleSymbol, ruleSymbol.references.size() == 0, i, new StringBuffer().append(this.grammar.getClassName()).append("::").toString());
                i++;
            }
            exitIfError();
        }
        genInitFactory(parserGrammar);
        genTokenStrings(new StringBuffer().append(this.grammar.getClassName()).append("::").toString());
        genBitsets(this.bitsetsUsed, this.grammar.tokenManager.maxTokenType(), new StringBuffer().append(this.grammar.getClassName()).append("::").toString());
        if (this.grammar.debuggingOutput) {
            genSemPredMap(new StringBuffer().append(this.grammar.getClassName()).append("::").toString());
        }
        println("");
        println("");
        NameSpace nameSpace3 = nameSpace;
        if (nameSpace3 != null) {
            nameSpace3.emitClosures(this.currentOutput);
        }
        this.currentOutput.close();
        this.currentOutput = null;
    }

    public void genBody(TreeWalkerGrammar treeWalkerGrammar) throws IOException {
        this.outputFile = new StringBuffer().append(this.grammar.getClassName()).append(".cpp").toString();
        this.outputLine = 1;
        this.currentOutput = this.antlrTool.openOutputFile(this.outputFile);
        this.genAST = this.grammar.buildAST;
        this.tabs = 0;
        genHeader(this.outputFile);
        printHeaderAction(preIncludeCpp);
        println(new StringBuffer("#include \"").append(this.grammar.getClassName()).append(".hpp\"").toString());
        println("#include <antlr/Token.hpp>");
        println("#include <antlr/AST.hpp>");
        println("#include <antlr/NoViableAltException.hpp>");
        println("#include <antlr/MismatchedTokenException.hpp>");
        println("#include <antlr/SemanticException.hpp>");
        println("#include <antlr/BitSet.hpp>");
        printHeaderAction(postIncludeCpp);
        NameSpace nameSpace2 = nameSpace;
        if (nameSpace2 != null) {
            nameSpace2.emitDeclarations(this.currentOutput);
        }
        printAction(this.grammar.preambleAction);
        if (this.grammar.superClass != null) {
            String str = this.grammar.superClass;
        } else {
            String superClass = this.grammar.getSuperClass();
            if (superClass.lastIndexOf(46) != -1) {
                superClass = superClass.substring(superClass.lastIndexOf(46) + 1);
            }
            new StringBuffer().append(namespaceAntlr).append(superClass).toString();
        }
        if (this.noConstructors) {
            println("#if 0");
            println("// constructor creation turned of with 'noConstructor' option");
        }
        println(new StringBuffer().append(this.grammar.getClassName()).append("::").append(this.grammar.getClassName()).append("()").toString());
        println(new StringBuffer("\t: ").append(namespaceAntlr).append("TreeParser() {").toString());
        this.tabs++;
        this.tabs--;
        println("}");
        if (this.noConstructors) {
            println("// constructor creation turned of with 'noConstructor' option");
            println("#endif");
        }
        println("");
        this.astTypes = new Vector();
        Enumeration enumerationElements = this.grammar.rules.elements();
        int i = 0;
        while (enumerationElements.hasMoreElements()) {
            GrammarSymbol grammarSymbol = (GrammarSymbol) enumerationElements.nextElement();
            if (grammarSymbol instanceof RuleSymbol) {
                RuleSymbol ruleSymbol = (RuleSymbol) grammarSymbol;
                genRule(ruleSymbol, ruleSymbol.references.size() == 0, i, new StringBuffer().append(this.grammar.getClassName()).append("::").toString());
                i++;
            }
            exitIfError();
        }
        genInitFactory(this.grammar);
        genTokenStrings(new StringBuffer().append(this.grammar.getClassName()).append("::").toString());
        genBitsets(this.bitsetsUsed, this.grammar.tokenManager.maxTokenType(), new StringBuffer().append(this.grammar.getClassName()).append("::").toString());
        println("");
        println("");
        NameSpace nameSpace3 = nameSpace;
        if (nameSpace3 != null) {
            nameSpace3.emitClosures(this.currentOutput);
        }
        this.currentOutput.close();
        this.currentOutput = null;
    }

    protected void genCases(BitSet bitSet) {
        if (this.DEBUG_CODE_GENERATOR || this.DEBUG_CPP_CODE_GENERATOR) {
            System.out.println(new StringBuffer("genCases(").append(bitSet).append(Constant.AFTER_QUTO).toString());
        }
        for (int i : bitSet.toArray()) {
            print("");
            _print(new StringBuffer("case ").append(getValueString(i)).append(":").toString());
            _println("");
        }
    }

    public CppBlockFinishingInfo genCommonBlock(AlternativeBlock alternativeBlock, boolean z) {
        String str;
        boolean z2;
        CppBlockFinishingInfo cppBlockFinishingInfo;
        boolean zLookaheadIsEmpty;
        String lookaheadTestExpression;
        CppBlockFinishingInfo cppBlockFinishingInfo2;
        String str2;
        String str3;
        int i;
        CppBlockFinishingInfo cppBlockFinishingInfo3 = new CppBlockFinishingInfo();
        if (this.DEBUG_CODE_GENERATOR || this.DEBUG_CPP_CODE_GENERATOR) {
            System.out.println(new StringBuffer("genCommonBlk(").append(alternativeBlock).append(Constant.AFTER_QUTO).toString());
        }
        boolean z3 = this.genAST;
        int i2 = 1;
        this.genAST = z3 && alternativeBlock.getAutoGen();
        boolean z4 = this.saveText;
        this.saveText = z4 && alternativeBlock.getAutoGen();
        String string = "";
        if (alternativeBlock.not && this.analyzer.subruleCanBeInverted(alternativeBlock, this.grammar instanceof LexerGrammar)) {
            Lookahead lookaheadLook = this.analyzer.look(1, alternativeBlock);
            if (alternativeBlock.getLabel() != null && this.syntacticPredLevel == 0) {
                println(new StringBuffer().append(alternativeBlock.getLabel()).append(" = ").append(this.lt1Value).append(";").toString());
            }
            genElementAST(alternativeBlock);
            if (this.grammar instanceof TreeWalkerGrammar) {
                if (this.usingCustomAST) {
                    string = new StringBuffer().append(namespaceAntlr).append("RefAST(_t),").toString();
                } else {
                    string = "_t,";
                }
            }
            println(new StringBuffer("match(").append(string).append(getBitsetName(markBitsetForGen(lookaheadLook.fset))).append(");").toString());
            if (this.grammar instanceof TreeWalkerGrammar) {
                println("_t = _t->getNextSibling();");
            }
            return cppBlockFinishingInfo3;
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
                return cppBlockFinishingInfo3;
            }
        }
        int i3 = 0;
        for (int i4 = 0; i4 < alternativeBlock.getAlternatives().size(); i4++) {
            if (suitableForCaseExpression(alternativeBlock.getAlternativeAt(i4))) {
                i3++;
            }
        }
        String str4 = "{";
        String str5 = "}";
        if (i3 >= this.makeSwitchThreshold) {
            String strLookaheadString = lookaheadString(1);
            if (this.grammar instanceof TreeWalkerGrammar) {
                println(new StringBuffer("if (_t == ").append(this.labeledElementASTInit).append(" )").toString());
                this.tabs++;
                println("_t = ASTNULL;");
                this.tabs--;
            }
            println(new StringBuffer("switch ( ").append(strLookaheadString).append(") {").toString());
            int i5 = 0;
            while (i5 < alternativeBlock.alternatives.size()) {
                Alternative alternativeAt2 = alternativeBlock.getAlternativeAt(i5);
                if (suitableForCaseExpression(alternativeAt2)) {
                    Lookahead lookahead = alternativeAt2.cache[i2];
                    if (lookahead.fset.degree() == 0 && !lookahead.containsEpsilon()) {
                        this.antlrTool.warning("Alternate omitted due to empty prediction set", this.grammar.getFilename(), alternativeAt2.head.getLine(), alternativeAt2.head.getColumn());
                        i = 1;
                    } else {
                        genCases(lookahead.fset);
                        println("{");
                        i = 1;
                        this.tabs++;
                        genAlt(alternativeAt2, alternativeBlock);
                        println("break;");
                        this.tabs--;
                        println("}");
                    }
                } else {
                    i = i2;
                }
                i5++;
                i2 = i;
                string = string;
            }
            str = string;
            println("default:");
            this.tabs += i2;
            z2 = true;
        } else {
            str = "";
            z2 = false;
        }
        int i6 = this.grammar instanceof LexerGrammar ? this.grammar.maxk : 0;
        int i7 = 0;
        int i8 = 0;
        while (i6 >= 0) {
            if (this.DEBUG_CODE_GENERATOR || this.DEBUG_CPP_CODE_GENERATOR) {
                System.out.println(new StringBuffer("checking depth ").append(i6).toString());
            }
            i8 = i8;
            int i9 = 0;
            while (i9 < alternativeBlock.alternatives.size()) {
                Alternative alternativeAt3 = alternativeBlock.getAlternativeAt(i9);
                boolean z5 = z4;
                if (this.DEBUG_CODE_GENERATOR || this.DEBUG_CPP_CODE_GENERATOR) {
                    System.out.println(new StringBuffer("genAlt: ").append(i9).toString());
                }
                if (z2 && suitableForCaseExpression(alternativeAt3)) {
                    if (this.DEBUG_CODE_GENERATOR || this.DEBUG_CPP_CODE_GENERATOR) {
                        System.out.println("ignoring alt because it was in the switch");
                    }
                } else {
                    if (this.grammar instanceof LexerGrammar) {
                        int i10 = alternativeAt3.lookaheadDepth;
                        if (i10 == Integer.MAX_VALUE) {
                            i10 = this.grammar.maxk;
                        }
                        while (i10 >= 1 && alternativeAt3.cache[i10].containsEpsilon()) {
                            i10--;
                        }
                        if (i10 != i6) {
                            if (this.DEBUG_CODE_GENERATOR || this.DEBUG_CPP_CODE_GENERATOR) {
                                System.out.println(new StringBuffer("ignoring alt because effectiveDepth!=altDepth;").append(i10).append("!=").append(i6).toString());
                            }
                        } else {
                            zLookaheadIsEmpty = lookaheadIsEmpty(alternativeAt3, i10);
                            lookaheadTestExpression = getLookaheadTestExpression(alternativeAt3, i10);
                        }
                    } else {
                        zLookaheadIsEmpty = lookaheadIsEmpty(alternativeAt3, this.grammar.maxk);
                        lookaheadTestExpression = getLookaheadTestExpression(alternativeAt3, this.grammar.maxk);
                    }
                    int i11 = i7;
                    if (alternativeAt3.cache[1].fset.degree() <= 127 || !suitableForCaseExpression(alternativeAt3)) {
                        if (zLookaheadIsEmpty && alternativeAt3.semPred == null && alternativeAt3.synPred == null) {
                            if (i8 == 0) {
                                println(str4);
                            } else {
                                println("else {");
                            }
                            cppBlockFinishingInfo3.needAnErrorClause = false;
                        } else {
                            if (alternativeAt3.semPred != null) {
                                str2 = str4;
                                cppBlockFinishingInfo2 = cppBlockFinishingInfo3;
                                String strProcessActionForSpecialSymbols = processActionForSpecialSymbols(alternativeAt3.semPred, alternativeBlock.line, this.currentRule, new ActionTransInfo());
                                if (this.grammar.debuggingOutput && ((this.grammar instanceof ParserGrammar) || (this.grammar instanceof LexerGrammar))) {
                                    lookaheadTestExpression = new StringBuffer("(").append(lookaheadTestExpression).append("&& fireSemanticPredicateEvaluated(antlr.debug.SemanticPredicateEvent.PREDICTING,").append(addSemPred(this.charFormatter.escapeString(strProcessActionForSpecialSymbols))).append(",").append(strProcessActionForSpecialSymbols).append("))").toString();
                                } else {
                                    lookaheadTestExpression = new StringBuffer("(").append(lookaheadTestExpression).append("&&(").append(strProcessActionForSpecialSymbols).append("))").toString();
                                }
                            } else {
                                cppBlockFinishingInfo2 = cppBlockFinishingInfo3;
                                str2 = str4;
                            }
                            if (i8 > 0) {
                                if (alternativeAt3.synPred != null) {
                                    println("else {");
                                    this.tabs++;
                                    genSynPred(alternativeAt3.synPred, lookaheadTestExpression);
                                    i7 = i11 + 1;
                                } else {
                                    println(new StringBuffer("else if ").append(lookaheadTestExpression).append(" {").toString());
                                }
                            } else if (alternativeAt3.synPred != null) {
                                genSynPred(alternativeAt3.synPred, lookaheadTestExpression);
                            } else {
                                if (this.grammar instanceof TreeWalkerGrammar) {
                                    println(new StringBuffer("if (_t == ").append(this.labeledElementASTInit).append(" )").toString());
                                    this.tabs++;
                                    println("_t = ASTNULL;");
                                    this.tabs--;
                                }
                                println(new StringBuffer("if ").append(lookaheadTestExpression).append(" {").toString());
                            }
                            i7 = i11;
                        }
                        i8++;
                        this.tabs++;
                        genAlt(alternativeAt3, alternativeBlock);
                        this.tabs--;
                        str3 = str5;
                        println(str3);
                        i9++;
                        str5 = str3;
                        z4 = z5;
                        z3 = z3;
                        z2 = z2;
                        i6 = i6;
                        str4 = str2;
                        cppBlockFinishingInfo3 = cppBlockFinishingInfo2;
                    } else if (i8 == 0) {
                        if (this.grammar instanceof TreeWalkerGrammar) {
                            println(new StringBuffer("if (_t == ").append(this.labeledElementASTInit).append(" )").toString());
                            this.tabs++;
                            println("_t = ASTNULL;");
                            this.tabs--;
                        }
                        println(new StringBuffer("if ").append(lookaheadTestExpression).append(" {").toString());
                    } else {
                        println(new StringBuffer("else if ").append(lookaheadTestExpression).append(" {").toString());
                    }
                    cppBlockFinishingInfo2 = cppBlockFinishingInfo3;
                    str2 = str4;
                    i7 = i11;
                    i8++;
                    this.tabs++;
                    genAlt(alternativeAt3, alternativeBlock);
                    this.tabs--;
                    str3 = str5;
                    println(str3);
                    i9++;
                    str5 = str3;
                    z4 = z5;
                    z3 = z3;
                    z2 = z2;
                    i6 = i6;
                    str4 = str2;
                    cppBlockFinishingInfo3 = cppBlockFinishingInfo2;
                }
                cppBlockFinishingInfo2 = cppBlockFinishingInfo3;
                str2 = str4;
                str3 = str5;
                i9++;
                str5 = str3;
                z4 = z5;
                z3 = z3;
                z2 = z2;
                i6 = i6;
                str4 = str2;
                cppBlockFinishingInfo3 = cppBlockFinishingInfo2;
            }
            i6--;
            cppBlockFinishingInfo3 = cppBlockFinishingInfo3;
        }
        CppBlockFinishingInfo cppBlockFinishingInfo4 = cppBlockFinishingInfo3;
        boolean z6 = z3;
        boolean z7 = z2;
        boolean z8 = z4;
        int i12 = i8;
        String str6 = str5;
        String string2 = str;
        for (int i13 = 1; i13 <= i7; i13++) {
            this.tabs--;
            string2 = new StringBuffer().append(string2).append(str6).toString();
        }
        this.genAST = z6;
        this.saveText = z8;
        if (z7) {
            this.tabs--;
            String string3 = new StringBuffer().append(string2).append(str6).toString();
            cppBlockFinishingInfo = cppBlockFinishingInfo4;
            cppBlockFinishingInfo.postscript = string3;
            cppBlockFinishingInfo.generatedSwitch = true;
            cppBlockFinishingInfo.generatedAnIf = i12 > 0;
        } else {
            cppBlockFinishingInfo = cppBlockFinishingInfo4;
            cppBlockFinishingInfo.postscript = string2;
            cppBlockFinishingInfo.generatedSwitch = false;
            cppBlockFinishingInfo.generatedAnIf = i12 > 0;
        }
        return cppBlockFinishingInfo;
    }

    private static boolean suitableForCaseExpression(Alternative alternative) {
        return alternative.lookaheadDepth == 1 && alternative.semPred == null && !alternative.cache[1].containsEpsilon() && alternative.cache[1].fset.degree() <= 127;
    }

    private void genElementAST(AlternativeElement alternativeElement) {
        String label;
        String label2;
        if ((this.grammar instanceof TreeWalkerGrammar) && !this.grammar.buildAST) {
            if (alternativeElement.getLabel() == null) {
                String str = this.lt1Value;
                String string = new StringBuffer(Constants.TMP_DIRECTORY_NAME).append(this.astVarNumber).append("_AST").toString();
                this.astVarNumber++;
                mapTreeVariable(alternativeElement, string);
                println(new StringBuffer().append(this.labeledElementASTType).append(" ").append(string).append("_in = ").append(str).append(";").toString());
                return;
            }
            return;
        }
        if (this.grammar.buildAST && this.syntacticPredLevel == 0) {
            boolean z = false;
            boolean z2 = this.genAST && !(alternativeElement.getLabel() == null && alternativeElement.getAutoGenType() == 3);
            if (alternativeElement.getAutoGenType() != 3 && (alternativeElement instanceof TokenRefElement)) {
                z2 = true;
            }
            if (this.grammar.hasSyntacticPredicate && z2) {
                z = true;
            }
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
            if (z2) {
                if (alternativeElement instanceof GrammarAtom) {
                    GrammarAtom grammarAtom = (GrammarAtom) alternativeElement;
                    if (grammarAtom.getASTNodeType() != null) {
                        genASTDeclaration(alternativeElement, label, new StringBuffer("Ref").append(grammarAtom.getASTNodeType()).toString());
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
                println(new StringBuffer().append(this.labeledElementASTType).append(" ").append(string3).append("_in = ").append(this.labeledElementASTInit).append(";").toString());
            }
            if (z) {
                println("if ( inputState->guessing == 0 ) {");
                this.tabs++;
            }
            if (alternativeElement.getLabel() != null) {
                if (alternativeElement instanceof GrammarAtom) {
                    println(new StringBuffer().append(string3).append(" = ").append(getASTCreateString((GrammarAtom) alternativeElement, label2)).append(";").toString());
                } else {
                    println(new StringBuffer().append(string3).append(" = ").append(getASTCreateString(label2)).append(";").toString());
                }
            }
            if (alternativeElement.getLabel() == null && z2) {
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
                if (autoGenType != 1) {
                    if (autoGenType == 2) {
                        if (this.usingCustomAST || ((alternativeElement instanceof GrammarAtom) && ((GrammarAtom) alternativeElement).getASTNodeType() != null)) {
                            println(new StringBuffer("astFactory->makeASTRoot(currentAST, ").append(namespaceAntlr).append("RefAST(").append(string3).append("));").toString());
                        } else {
                            println(new StringBuffer("astFactory->makeASTRoot(currentAST, ").append(string3).append(");").toString());
                        }
                    }
                } else if (this.usingCustomAST || ((alternativeElement instanceof GrammarAtom) && ((GrammarAtom) alternativeElement).getASTNodeType() != null)) {
                    println(new StringBuffer("astFactory->addASTChild(currentAST, ").append(namespaceAntlr).append("RefAST(").append(string3).append("));").toString());
                } else {
                    println(new StringBuffer("astFactory->addASTChild(currentAST, ").append(string3).append(");").toString());
                }
            }
            if (z) {
                this.tabs--;
                println("}");
            }
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
            println("}");
            genErrorHandler(exceptionSpecFindExceptionSpec);
        }
    }

    private void genErrorHandler(ExceptionSpec exceptionSpec) {
        for (int i = 0; i < exceptionSpec.handlers.size(); i++) {
            ExceptionHandler exceptionHandler = (ExceptionHandler) exceptionSpec.handlers.elementAt(i);
            println(new StringBuffer("catch (").append(exceptionHandler.exceptionTypeAndName.getText()).append(") {").toString());
            this.tabs++;
            if (this.grammar.hasSyntacticPredicate) {
                println("if (inputState->guessing==0) {");
                this.tabs++;
            }
            ActionTransInfo actionTransInfo = new ActionTransInfo();
            genLineNo(exceptionHandler.action);
            printAction(processActionForSpecialSymbols(exceptionHandler.action.getText(), exceptionHandler.action.getLine(), this.currentRule, actionTransInfo));
            genLineNo2();
            if (this.grammar.hasSyntacticPredicate) {
                this.tabs--;
                println("} else {");
                this.tabs++;
                println("throw;");
                this.tabs--;
                println("}");
            }
            this.tabs--;
            println("}");
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
            println("try { // for error handling");
            this.tabs++;
        }
    }

    protected void genHeader(String str) {
        StringBuffer stringBuffer = new StringBuffer("/* $ANTLR ");
        Tool tool = this.antlrTool;
        println(stringBuffer.append(Tool.version).append(": \"").append(this.antlrTool.fileMinusPath(this.antlrTool.grammarFile)).append("\" -> \"").append(str).append("\"$ */").toString());
    }

    public void genInclude(LexerGrammar lexerGrammar) throws IOException {
        String string;
        String strStripFrontBack;
        this.outputFile = new StringBuffer().append(this.grammar.getClassName()).append(".hpp").toString();
        this.outputLine = 1;
        this.currentOutput = this.antlrTool.openOutputFile(this.outputFile);
        this.genAST = false;
        this.saveText = true;
        this.tabs = 0;
        println(new StringBuffer("#ifndef INC_").append(this.grammar.getClassName()).append("_hpp_").toString());
        println(new StringBuffer("#define INC_").append(this.grammar.getClassName()).append("_hpp_").toString());
        println("");
        printHeaderAction(preIncludeHpp);
        println("#include <antlr/config.hpp>");
        genHeader(this.outputFile);
        println("#include <antlr/CommonToken.hpp>");
        println("#include <antlr/InputBuffer.hpp>");
        println("#include <antlr/BitSet.hpp>");
        println(new StringBuffer("#include \"").append(this.grammar.tokenManager.getName()).append(TokenTypesFileSuffix).append(".hpp\"").toString());
        if (this.grammar.superClass != null) {
            string = this.grammar.superClass;
            println("\n// Include correct superclass header with a header statement for example:");
            println("// header \"post_include_hpp\" {");
            println(new StringBuffer("// #include \"").append(string).append(".hpp\"").toString());
            println("// }");
            println("// Or....");
            println("// header {");
            println(new StringBuffer("// #include \"").append(string).append(".hpp\"").toString());
            println("// }\n");
        } else {
            String superClass = this.grammar.getSuperClass();
            if (superClass.lastIndexOf(46) != -1) {
                superClass = superClass.substring(superClass.lastIndexOf(46) + 1);
            }
            println(new StringBuffer("#include <antlr/").append(superClass).append(".hpp>").toString());
            string = new StringBuffer().append(namespaceAntlr).append(superClass).toString();
        }
        printHeaderAction(postIncludeHpp);
        NameSpace nameSpace2 = nameSpace;
        if (nameSpace2 != null) {
            nameSpace2.emitDeclarations(this.currentOutput);
        }
        printHeaderAction("");
        if (this.grammar.comment != null) {
            _println(this.grammar.comment);
        }
        print(new StringBuffer("class CUSTOM_API ").append(this.grammar.getClassName()).append(" : public ").append(string).toString());
        println(new StringBuffer(", public ").append(this.grammar.tokenManager.getName()).append(TokenTypesFileSuffix).toString());
        Token token = (Token) this.grammar.options.get("classHeaderSuffix");
        if (token != null && (strStripFrontBack = StringUtils.stripFrontBack(token.getText(), "\"", "\"")) != null) {
            print(new StringBuffer(", ").append(strStripFrontBack).toString());
        }
        println("{");
        if (this.grammar.classMemberAction != null) {
            genLineNo(this.grammar.classMemberAction);
            print(processActionForSpecialSymbols(this.grammar.classMemberAction.getText(), this.grammar.classMemberAction.getLine(), this.currentRule, null));
            genLineNo2();
        }
        this.tabs = 0;
        println("private:");
        this.tabs = 1;
        println("void initLiterals();");
        this.tabs = 0;
        println("public:");
        this.tabs = 1;
        println("bool getCaseSensitiveLiterals() const");
        println("{");
        this.tabs++;
        println(new StringBuffer("return ").append(lexerGrammar.caseSensitiveLiterals).append(";").toString());
        this.tabs--;
        println("}");
        this.tabs = 0;
        println("public:");
        this.tabs = 1;
        if (this.noConstructors) {
            this.tabs = 0;
            println("#if 0");
            println("// constructor creation turned of with 'noConstructor' option");
            this.tabs = 1;
        }
        println(new StringBuffer().append(this.grammar.getClassName()).append("(").append(namespaceStd).append("istream& in);").toString());
        println(new StringBuffer().append(this.grammar.getClassName()).append("(").append(namespaceAntlr).append("InputBuffer& ib);").toString());
        println(new StringBuffer().append(this.grammar.getClassName()).append("(const ").append(namespaceAntlr).append("LexerSharedInputState& state);").toString());
        if (this.noConstructors) {
            this.tabs = 0;
            println("// constructor creation turned of with 'noConstructor' option");
            println("#endif");
            this.tabs = 1;
        }
        println(new StringBuffer().append(namespaceAntlr).append("RefToken nextToken();").toString());
        Enumeration enumerationElements = this.grammar.rules.elements();
        while (enumerationElements.hasMoreElements()) {
            RuleSymbol ruleSymbol = (RuleSymbol) enumerationElements.nextElement();
            if (!ruleSymbol.getId().equals("mnextToken")) {
                genRuleHeader(ruleSymbol, false);
            }
            exitIfError();
        }
        this.tabs = 0;
        println("private:");
        this.tabs = 1;
        if (this.grammar.debuggingOutput) {
            println("static const char* _ruleNames[];");
        }
        if (this.grammar.debuggingOutput) {
            println("static const char* _semPredNames[];");
        }
        genBitsetsHeader(this.bitsetsUsed, ((LexerGrammar) this.grammar).charVocabulary.size());
        this.tabs = 0;
        println("};");
        println("");
        NameSpace nameSpace3 = nameSpace;
        if (nameSpace3 != null) {
            nameSpace3.emitClosures(this.currentOutput);
        }
        println(new StringBuffer("#endif /*INC_").append(this.grammar.getClassName()).append("_hpp_*/").toString());
        this.currentOutput.close();
        this.currentOutput = null;
    }

    public void genInclude(ParserGrammar parserGrammar) throws IOException {
        String string;
        String strStripFrontBack;
        this.outputFile = new StringBuffer().append(this.grammar.getClassName()).append(".hpp").toString();
        this.outputLine = 1;
        this.currentOutput = this.antlrTool.openOutputFile(this.outputFile);
        this.genAST = this.grammar.buildAST;
        this.tabs = 0;
        println(new StringBuffer("#ifndef INC_").append(this.grammar.getClassName()).append("_hpp_").toString());
        println(new StringBuffer("#define INC_").append(this.grammar.getClassName()).append("_hpp_").toString());
        println("");
        printHeaderAction(preIncludeHpp);
        println("#include <antlr/config.hpp>");
        genHeader(this.outputFile);
        println("#include <antlr/TokenStream.hpp>");
        println("#include <antlr/TokenBuffer.hpp>");
        println(new StringBuffer("#include \"").append(this.grammar.tokenManager.getName()).append(TokenTypesFileSuffix).append(".hpp\"").toString());
        if (this.grammar.superClass != null) {
            string = this.grammar.superClass;
            println("\n// Include correct superclass header with a header statement for example:");
            println("// header \"post_include_hpp\" {");
            println(new StringBuffer("// #include \"").append(string).append(".hpp\"").toString());
            println("// }");
            println("// Or....");
            println("// header {");
            println(new StringBuffer("// #include \"").append(string).append(".hpp\"").toString());
            println("// }\n");
        } else {
            String superClass = this.grammar.getSuperClass();
            if (superClass.lastIndexOf(46) != -1) {
                superClass = superClass.substring(superClass.lastIndexOf(46) + 1);
            }
            println(new StringBuffer("#include <antlr/").append(superClass).append(".hpp>").toString());
            string = new StringBuffer().append(namespaceAntlr).append(superClass).toString();
        }
        println("");
        printHeaderAction(postIncludeHpp);
        NameSpace nameSpace2 = nameSpace;
        if (nameSpace2 != null) {
            nameSpace2.emitDeclarations(this.currentOutput);
        }
        printHeaderAction("");
        if (this.grammar.comment != null) {
            _println(this.grammar.comment);
        }
        print(new StringBuffer("class CUSTOM_API ").append(this.grammar.getClassName()).append(" : public ").append(string).toString());
        println(new StringBuffer(", public ").append(this.grammar.tokenManager.getName()).append(TokenTypesFileSuffix).toString());
        Token token = (Token) this.grammar.options.get("classHeaderSuffix");
        if (token != null && (strStripFrontBack = StringUtils.stripFrontBack(token.getText(), "\"", "\"")) != null) {
            print(new StringBuffer(", ").append(strStripFrontBack).toString());
        }
        println("{");
        if (this.grammar.debuggingOutput) {
            println("public: static const char* _ruleNames[];");
        }
        if (this.grammar.classMemberAction != null) {
            genLineNo(this.grammar.classMemberAction.getLine());
            print(processActionForSpecialSymbols(this.grammar.classMemberAction.getText(), this.grammar.classMemberAction.getLine(), this.currentRule, null));
            genLineNo2();
        }
        println("public:");
        this.tabs = 1;
        println(new StringBuffer("void initializeASTFactory( ").append(namespaceAntlr).append("ASTFactory& factory );").toString());
        this.tabs = 0;
        if (this.noConstructors) {
            println("#if 0");
            println("// constructor creation turned of with 'noConstructor' option");
        }
        println("protected:");
        this.tabs = 1;
        println(new StringBuffer().append(this.grammar.getClassName()).append("(").append(namespaceAntlr).append("TokenBuffer& tokenBuf, int k);").toString());
        this.tabs = 0;
        println("public:");
        this.tabs = 1;
        println(new StringBuffer().append(this.grammar.getClassName()).append("(").append(namespaceAntlr).append("TokenBuffer& tokenBuf);").toString());
        this.tabs = 0;
        println("protected:");
        this.tabs = 1;
        println(new StringBuffer().append(this.grammar.getClassName()).append("(").append(namespaceAntlr).append("TokenStream& lexer, int k);").toString());
        this.tabs = 0;
        println("public:");
        this.tabs = 1;
        println(new StringBuffer().append(this.grammar.getClassName()).append("(").append(namespaceAntlr).append("TokenStream& lexer);").toString());
        println(new StringBuffer().append(this.grammar.getClassName()).append("(const ").append(namespaceAntlr).append("ParserSharedInputState& state);").toString());
        if (this.noConstructors) {
            this.tabs = 0;
            println("// constructor creation turned of with 'noConstructor' option");
            println("#endif");
            this.tabs = 1;
        }
        println("int getNumTokens() const");
        println("{");
        this.tabs++;
        println(new StringBuffer("return ").append(this.grammar.getClassName()).append("::NUM_TOKENS;").toString());
        this.tabs--;
        println("}");
        println("const char* getTokenName( int type ) const");
        println("{");
        this.tabs++;
        println("if( type > getNumTokens() ) return 0;");
        println(new StringBuffer("return ").append(this.grammar.getClassName()).append("::tokenNames[type];").toString());
        this.tabs--;
        println("}");
        println("const char* const* getTokenNames() const");
        println("{");
        this.tabs++;
        println(new StringBuffer("return ").append(this.grammar.getClassName()).append("::tokenNames;").toString());
        this.tabs--;
        println("}");
        Enumeration enumerationElements = this.grammar.rules.elements();
        while (enumerationElements.hasMoreElements()) {
            GrammarSymbol grammarSymbol = (GrammarSymbol) enumerationElements.nextElement();
            if (grammarSymbol instanceof RuleSymbol) {
                RuleSymbol ruleSymbol = (RuleSymbol) grammarSymbol;
                genRuleHeader(ruleSymbol, ruleSymbol.references.size() == 0);
            }
            exitIfError();
        }
        this.tabs = 0;
        println("public:");
        this.tabs = 1;
        println(new StringBuffer().append(namespaceAntlr).append("RefAST getAST()").toString());
        println("{");
        if (this.usingCustomAST) {
            this.tabs++;
            println(new StringBuffer("return ").append(namespaceAntlr).append("RefAST(returnAST);").toString());
            this.tabs--;
        } else {
            this.tabs++;
            println("return returnAST;");
            this.tabs--;
        }
        println("}");
        println("");
        this.tabs = 0;
        println("protected:");
        this.tabs = 1;
        println(new StringBuffer().append(this.labeledElementASTType).append(" returnAST;").toString());
        this.tabs = 0;
        println("private:");
        this.tabs = 1;
        println("static const char* tokenNames[];");
        _println("#ifndef NO_STATIC_CONSTS");
        println(new StringBuffer("static const int NUM_TOKENS = ").append(this.grammar.tokenManager.getVocabulary().size()).append(";").toString());
        _println("#else");
        println("enum {");
        println(new StringBuffer("\tNUM_TOKENS = ").append(this.grammar.tokenManager.getVocabulary().size()).toString());
        println("};");
        _println("#endif");
        genBitsetsHeader(this.bitsetsUsed, this.grammar.tokenManager.maxTokenType());
        if (this.grammar.debuggingOutput) {
            println("static const char* _semPredNames[];");
        }
        this.tabs = 0;
        println("};");
        println("");
        NameSpace nameSpace3 = nameSpace;
        if (nameSpace3 != null) {
            nameSpace3.emitClosures(this.currentOutput);
        }
        println(new StringBuffer("#endif /*INC_").append(this.grammar.getClassName()).append("_hpp_*/").toString());
        this.currentOutput.close();
        this.currentOutput = null;
    }

    public void genInclude(TreeWalkerGrammar treeWalkerGrammar) throws IOException {
        String string;
        String strStripFrontBack;
        this.outputFile = new StringBuffer().append(this.grammar.getClassName()).append(".hpp").toString();
        this.outputLine = 1;
        this.currentOutput = this.antlrTool.openOutputFile(this.outputFile);
        this.genAST = this.grammar.buildAST;
        this.tabs = 0;
        println(new StringBuffer("#ifndef INC_").append(this.grammar.getClassName()).append("_hpp_").toString());
        println(new StringBuffer("#define INC_").append(this.grammar.getClassName()).append("_hpp_").toString());
        println("");
        printHeaderAction(preIncludeHpp);
        println("#include <antlr/config.hpp>");
        println(new StringBuffer("#include \"").append(this.grammar.tokenManager.getName()).append(TokenTypesFileSuffix).append(".hpp\"").toString());
        genHeader(this.outputFile);
        if (this.grammar.superClass != null) {
            string = this.grammar.superClass;
            println("\n// Include correct superclass header with a header statement for example:");
            println("// header \"post_include_hpp\" {");
            println(new StringBuffer("// #include \"").append(string).append(".hpp\"").toString());
            println("// }");
            println("// Or....");
            println("// header {");
            println(new StringBuffer("// #include \"").append(string).append(".hpp\"").toString());
            println("// }\n");
        } else {
            String superClass = this.grammar.getSuperClass();
            if (superClass.lastIndexOf(46) != -1) {
                superClass = superClass.substring(superClass.lastIndexOf(46) + 1);
            }
            println(new StringBuffer("#include <antlr/").append(superClass).append(".hpp>").toString());
            string = new StringBuffer().append(namespaceAntlr).append(superClass).toString();
        }
        println("");
        printHeaderAction(postIncludeHpp);
        NameSpace nameSpace2 = nameSpace;
        if (nameSpace2 != null) {
            nameSpace2.emitDeclarations(this.currentOutput);
        }
        printHeaderAction("");
        if (this.grammar.comment != null) {
            _println(this.grammar.comment);
        }
        print(new StringBuffer("class CUSTOM_API ").append(this.grammar.getClassName()).append(" : public ").append(string).toString());
        println(new StringBuffer(", public ").append(this.grammar.tokenManager.getName()).append(TokenTypesFileSuffix).toString());
        Token token = (Token) this.grammar.options.get("classHeaderSuffix");
        if (token != null && (strStripFrontBack = StringUtils.stripFrontBack(token.getText(), "\"", "\"")) != null) {
            print(new StringBuffer(", ").append(strStripFrontBack).toString());
        }
        println("{");
        if (this.grammar.classMemberAction != null) {
            genLineNo(this.grammar.classMemberAction.getLine());
            print(processActionForSpecialSymbols(this.grammar.classMemberAction.getText(), this.grammar.classMemberAction.getLine(), this.currentRule, null));
            genLineNo2();
        }
        this.tabs = 0;
        println("public:");
        if (this.noConstructors) {
            println("#if 0");
            println("// constructor creation turned of with 'noConstructor' option");
        }
        this.tabs = 1;
        println(new StringBuffer().append(this.grammar.getClassName()).append("();").toString());
        if (this.noConstructors) {
            this.tabs = 0;
            println("#endif");
            this.tabs = 1;
        }
        println(new StringBuffer("static void initializeASTFactory( ").append(namespaceAntlr).append("ASTFactory& factory );").toString());
        println("int getNumTokens() const");
        println("{");
        this.tabs++;
        println(new StringBuffer("return ").append(this.grammar.getClassName()).append("::NUM_TOKENS;").toString());
        this.tabs--;
        println("}");
        println("const char* getTokenName( int type ) const");
        println("{");
        this.tabs++;
        println("if( type > getNumTokens() ) return 0;");
        println(new StringBuffer("return ").append(this.grammar.getClassName()).append("::tokenNames[type];").toString());
        this.tabs--;
        println("}");
        println("const char* const* getTokenNames() const");
        println("{");
        this.tabs++;
        println(new StringBuffer("return ").append(this.grammar.getClassName()).append("::tokenNames;").toString());
        this.tabs--;
        println("}");
        Enumeration enumerationElements = this.grammar.rules.elements();
        while (enumerationElements.hasMoreElements()) {
            GrammarSymbol grammarSymbol = (GrammarSymbol) enumerationElements.nextElement();
            if (grammarSymbol instanceof RuleSymbol) {
                RuleSymbol ruleSymbol = (RuleSymbol) grammarSymbol;
                genRuleHeader(ruleSymbol, ruleSymbol.references.size() == 0);
            }
            exitIfError();
        }
        this.tabs = 0;
        println("public:");
        this.tabs = 1;
        println(new StringBuffer().append(namespaceAntlr).append("RefAST getAST()").toString());
        println("{");
        if (this.usingCustomAST) {
            this.tabs++;
            println(new StringBuffer("return ").append(namespaceAntlr).append("RefAST(returnAST);").toString());
            this.tabs--;
        } else {
            this.tabs++;
            println("return returnAST;");
            this.tabs--;
        }
        println("}");
        println("");
        this.tabs = 0;
        println("protected:");
        this.tabs = 1;
        println(new StringBuffer().append(this.labeledElementASTType).append(" returnAST;").toString());
        println(new StringBuffer().append(this.labeledElementASTType).append(" _retTree;").toString());
        this.tabs = 0;
        println("private:");
        this.tabs = 1;
        println("static const char* tokenNames[];");
        _println("#ifndef NO_STATIC_CONSTS");
        println(new StringBuffer("static const int NUM_TOKENS = ").append(this.grammar.tokenManager.getVocabulary().size()).append(";").toString());
        _println("#else");
        println("enum {");
        println(new StringBuffer("\tNUM_TOKENS = ").append(this.grammar.tokenManager.getVocabulary().size()).toString());
        println("};");
        _println("#endif");
        genBitsetsHeader(this.bitsetsUsed, this.grammar.tokenManager.maxTokenType());
        this.tabs = 0;
        println("};");
        println("");
        NameSpace nameSpace3 = nameSpace;
        if (nameSpace3 != null) {
            nameSpace3.emitClosures(this.currentOutput);
        }
        println(new StringBuffer("#endif /*INC_").append(this.grammar.getClassName()).append("_hpp_*/").toString());
        this.currentOutput.close();
        this.currentOutput = null;
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
        String string = this.labeledElementASTInit;
        if (alternativeElement instanceof GrammarAtom) {
            GrammarAtom grammarAtom = (GrammarAtom) alternativeElement;
            if (grammarAtom.getASTNodeType() != null) {
                string = new StringBuffer("Ref").append(grammarAtom.getASTNodeType()).append("(").append(this.labeledElementASTInit).append(Constant.AFTER_QUTO).toString();
            }
        }
        println(new StringBuffer().append(str2).append(" ").append(str).append("_AST = ").append(string).append(";").toString());
        this.declaredASTVariables.put(alternativeElement, alternativeElement);
    }

    private void genLiteralsTest() {
        println("_ttype = testLiteralsTable(_ttype);");
    }

    private void genLiteralsTestForPartialToken() {
        println("_ttype = testLiteralsTable(text.substr(_begin, text.length()-_begin),_ttype);");
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
            this.antlrTool.error(new StringBuffer("cannot ref character literals in grammar: ").append(grammarAtom).toString());
        } else if (grammarAtom instanceof TokenRefElement) {
            genMatchUsingAtomTokenType(grammarAtom);
        } else if (grammarAtom instanceof WildcardElement) {
            gen((WildcardElement) grammarAtom);
        }
    }

    protected void genMatchUsingAtomText(GrammarAtom grammarAtom) {
        String string;
        if (!(this.grammar instanceof TreeWalkerGrammar)) {
            string = "";
        } else if (this.usingCustomAST) {
            string = new StringBuffer().append(namespaceAntlr).append("RefAST(_t),").toString();
        } else {
            string = "_t,";
        }
        if ((this.grammar instanceof LexerGrammar) && (!this.saveText || grammarAtom.getAutoGenType() == 3)) {
            println("_saveIndex = text.length();");
        }
        print(grammarAtom.not ? "matchNot(" : "match(");
        _print(string);
        if (grammarAtom.atomText.equals("EOF")) {
            _print(new StringBuffer().append(namespaceAntlr).append("Token::EOF_TYPE").toString());
        } else if (this.grammar instanceof LexerGrammar) {
            _print(convertJavaToCppString(grammarAtom.atomText, false));
        } else {
            _print(grammarAtom.atomText);
        }
        _println(");");
        if (this.grammar instanceof LexerGrammar) {
            if (!this.saveText || grammarAtom.getAutoGenType() == 3) {
                println("text.erase(_saveIndex);");
            }
        }
    }

    protected void genMatchUsingAtomTokenType(GrammarAtom grammarAtom) {
        String string;
        if (!(this.grammar instanceof TreeWalkerGrammar)) {
            string = "";
        } else if (this.usingCustomAST) {
            string = new StringBuffer().append(namespaceAntlr).append("RefAST(_t),").toString();
        } else {
            string = "_t,";
        }
        println(new StringBuffer().append(grammarAtom.not ? "matchNot(" : "match(").append(new StringBuffer().append(string).append(getValueString(grammarAtom.getType())).toString()).append(");").toString());
    }

    public void genNextToken() {
        String string;
        for (int i = 0; i < this.grammar.rules.size(); i++) {
            RuleSymbol ruleSymbol = (RuleSymbol) this.grammar.rules.elementAt(i);
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
                println(new StringBuffer().append(namespaceAntlr).append("RefToken ").append(this.grammar.getClassName()).append("::nextToken()").toString());
                println("{");
                this.tabs++;
                println(new StringBuffer().append(namespaceAntlr).append("RefToken theRetToken;").toString());
                println("for (;;) {");
                this.tabs++;
                println(new StringBuffer().append(namespaceAntlr).append("RefToken theRetToken;").toString());
                println(new StringBuffer("int _ttype = ").append(namespaceAntlr).append("Token::INVALID_TYPE;").toString());
                if (((LexerGrammar) this.grammar).filterMode) {
                    println("setCommitToPath(false);");
                    if (str != null) {
                        if (!this.grammar.isDefined(CodeGenerator.encodeLexerRuleName(str))) {
                            this.grammar.antlrTool.error(new StringBuffer("Filter rule ").append(str).append(" does not exist in this lexer").toString());
                        } else {
                            RuleSymbol ruleSymbol3 = (RuleSymbol) this.grammar.getSymbol(CodeGenerator.encodeLexerRuleName(str));
                            if (!ruleSymbol3.isDefined()) {
                                this.grammar.antlrTool.error(new StringBuffer("Filter rule ").append(str).append(" does not exist in this lexer").toString());
                            } else if (ruleSymbol3.access.equals("public")) {
                                this.grammar.antlrTool.error(new StringBuffer("Filter rule ").append(str).append(" must be protected").toString());
                            }
                        }
                        println("int _m;");
                        println("_m = mark();");
                    }
                }
                println("resetText();");
                println("try {   // for lexical and char stream error handling");
                this.tabs++;
                for (int i2 = 0; i2 < ruleBlockCreateNextTokenRule.getAlternatives().size(); i2++) {
                    if (ruleBlockCreateNextTokenRule.getAlternativeAt(i2).cache[1].containsEpsilon()) {
                        this.antlrTool.warning("found optional path in nextToken()");
                    }
                }
                String property = System.getProperty(SystemProperties.LINE_SEPARATOR);
                CppBlockFinishingInfo cppBlockFinishingInfoGenCommonBlock = genCommonBlock(ruleBlockCreateNextTokenRule, false);
                String string2 = new StringBuffer().append(new StringBuffer("if (LA(1)==EOF_CHAR)").append(property).append("\t\t\t\t{").append(property).append("\t\t\t\t\tuponEOF();").append(property).append("\t\t\t\t\t_returnToken = makeToken(").append(namespaceAntlr).append("Token::EOF_TYPE);").append(property).append("\t\t\t\t}").toString()).append(property).append("\t\t\t\t").toString();
                if (!((LexerGrammar) this.grammar).filterMode) {
                    string = new StringBuffer().append(string2).append("else {").append(this.throwNoViable).append("}").toString();
                } else if (str == null) {
                    string = new StringBuffer().append(string2).append("else {consume(); goto tryAgain;}").toString();
                } else {
                    string = new StringBuffer().append(string2).append("else {").append(property).append("\t\t\t\t\tcommit();").append(property).append("\t\t\t\t\ttry {m").append(str).append("(false);}").append(property).append("\t\t\t\t\tcatch(").append(namespaceAntlr).append("RecognitionException& e) {").append(property).append("\t\t\t\t\t\t// catastrophic failure").append(property).append("\t\t\t\t\t\treportError(e);").append(property).append("\t\t\t\t\t\tconsume();").append(property).append("\t\t\t\t\t}").append(property).append("\t\t\t\t\tgoto tryAgain;").append(property).append("\t\t\t\t}").toString();
                }
                genBlockFinish(cppBlockFinishingInfoGenCommonBlock, string);
                if (((LexerGrammar) this.grammar).filterMode && str != null) {
                    println("commit();");
                }
                println(new StringBuffer("if ( !_returnToken )").append(property).append("\t\t\t\tgoto tryAgain; // found SKIP token").append(property).toString());
                println("_ttype = _returnToken->getType();");
                if (((LexerGrammar) this.grammar).getTestLiterals()) {
                    genLiteralsTest();
                }
                println("_returnToken->setType(_ttype);");
                println("return _returnToken;");
                this.tabs--;
                println("}");
                println(new StringBuffer("catch (").append(namespaceAntlr).append("RecognitionException& e) {").toString());
                this.tabs++;
                if (((LexerGrammar) this.grammar).filterMode) {
                    if (str == null) {
                        println("if ( !getCommitToPath() ) {");
                        this.tabs++;
                        println("consume();");
                        println("goto tryAgain;");
                        this.tabs--;
                        println("}");
                    } else {
                        println("if ( !getCommitToPath() ) {");
                        this.tabs++;
                        println("rewind(_m);");
                        println("resetText();");
                        println(new StringBuffer("try {m").append(str).append("(false);}").toString());
                        println(new StringBuffer("catch(").append(namespaceAntlr).append("RecognitionException& ee) {").toString());
                        println("\t// horrendous failure: error in filter rule");
                        println("\treportError(ee);");
                        println("\tconsume();");
                        println("}");
                        this.tabs--;
                        println("}");
                        println(PsiKeyword.ELSE);
                    }
                }
                if (ruleBlockCreateNextTokenRule.getDefaultErrorHandler()) {
                    println("{");
                    this.tabs++;
                    println("reportError(e);");
                    println("consume();");
                    this.tabs--;
                    println("}");
                } else {
                    this.tabs++;
                    println(new StringBuffer("throw ").append(namespaceAntlr).append("TokenStreamRecognitionException(e);").toString());
                    this.tabs--;
                }
                this.tabs--;
                println("}");
                println(new StringBuffer("catch (").append(namespaceAntlr).append("CharStreamIOException& csie) {").toString());
                println(new StringBuffer("\tthrow ").append(namespaceAntlr).append("TokenStreamIOException(csie.io);").toString());
                println("}");
                println(new StringBuffer("catch (").append(namespaceAntlr).append("CharStreamException& cse) {").toString());
                println(new StringBuffer("\tthrow ").append(namespaceAntlr).append("TokenStreamException(cse.getMessage());").toString());
                println("}");
                _println("tryAgain:;");
                this.tabs--;
                println("}");
                this.tabs--;
                println("}");
                println("");
                return;
            }
        }
        println("");
        println(new StringBuffer().append(namespaceAntlr).append("RefToken ").append(this.grammar.getClassName()).append("::nextToken() { return ").append(namespaceAntlr).append("RefToken(new ").append(namespaceAntlr).append("CommonToken(").append(namespaceAntlr).append("Token::EOF_TYPE, \"\")); }").toString());
        println("");
    }

    public void genRule(RuleSymbol ruleSymbol, boolean z, int i, String str) {
        int i2;
        int i3;
        int i4;
        if (this.DEBUG_CODE_GENERATOR || this.DEBUG_CPP_CODE_GENERATOR) {
            System.out.println(new StringBuffer("genRule(").append(ruleSymbol.getId()).append(Constant.AFTER_QUTO).toString());
        }
        if (!ruleSymbol.isDefined()) {
            this.antlrTool.error(new StringBuffer("undefined rule: ").append(ruleSymbol.getId()).toString());
            return;
        }
        RuleBlock block = ruleSymbol.getBlock();
        this.currentRule = block;
        this.currentASTResult = ruleSymbol.getId();
        this.declaredASTVariables.clear();
        boolean z2 = this.genAST;
        this.genAST = z2 && block.getAutoGen();
        this.saveText = block.getAutoGen();
        if (ruleSymbol.comment != null) {
            _println(ruleSymbol.comment);
        }
        if (block.returnAction != null) {
            _print(new StringBuffer().append(extractTypeOfAction(block.returnAction, block.getLine(), block.getColumn())).append(" ").toString());
        } else {
            _print("void ");
        }
        _print(new StringBuffer().append(str).append(ruleSymbol.getId()).append("(").toString());
        _print(this.commonExtraParams);
        if (this.commonExtraParams.length() != 0 && block.argAction != null) {
            _print(",");
        }
        if (block.argAction != null) {
            _println("");
            this.tabs++;
            String string = block.argAction;
            int i5 = 61;
            int iIndexOf = string.indexOf(61);
            if (iIndexOf != -1) {
                int iIndexOf2 = 0;
                String str2 = "";
                int iIndexOf3 = iIndexOf;
                String strTrim = string;
                string = str2;
                while (iIndexOf2 != -1 && iIndexOf3 != -1) {
                    string = new StringBuffer().append(string).append(str2).append(strTrim.substring(0, iIndexOf3).trim()).toString();
                    iIndexOf2 = strTrim.indexOf(44, iIndexOf3);
                    str2 = ", ";
                    if (iIndexOf2 != -1 && (iIndexOf3 = (strTrim = strTrim.substring(iIndexOf2 + 1).trim()).indexOf(i5)) == -1) {
                        string = new StringBuffer().append(string).append(", ").append(strTrim).toString();
                        i5 = 61;
                    }
                }
            }
            println(string);
            i2 = 1;
            this.tabs--;
            print(") ");
        } else {
            i2 = 1;
            _print(") ");
        }
        _println("{");
        this.tabs += i2;
        if (this.grammar.traceRules) {
            if (this.grammar instanceof TreeWalkerGrammar) {
                if (this.usingCustomAST) {
                    println(new StringBuffer("Tracer traceInOut(this,\"").append(ruleSymbol.getId()).append("\",").append(namespaceAntlr).append("RefAST(_t));").toString());
                } else {
                    println(new StringBuffer("Tracer traceInOut(this,\"").append(ruleSymbol.getId()).append("\",_t);").toString());
                }
            } else {
                println(new StringBuffer("Tracer traceInOut(this, \"").append(ruleSymbol.getId()).append("\");").toString());
            }
        }
        if (block.returnAction != null) {
            genLineNo(block);
            println(new StringBuffer().append(block.returnAction).append(";").toString());
            genLineNo2();
        }
        if (!this.commonLocalVars.equals("")) {
            println(this.commonLocalVars);
        }
        if (this.grammar instanceof LexerGrammar) {
            if (ruleSymbol.getId().equals("mEOF")) {
                println(new StringBuffer("_ttype = ").append(namespaceAntlr).append("Token::EOF_TYPE;").toString());
            } else {
                println(new StringBuffer("_ttype = ").append(ruleSymbol.getId().substring(1)).append(";").toString());
            }
            println(new StringBuffer().append(namespaceStd).append("string::size_type _saveIndex;").toString());
        }
        if (this.grammar.debuggingOutput) {
            if (this.grammar instanceof ParserGrammar) {
                println(new StringBuffer("fireEnterRule(").append(i).append(",0);").toString());
            } else if (this.grammar instanceof LexerGrammar) {
                println(new StringBuffer("fireEnterRule(").append(i).append(",_ttype);").toString());
            }
        }
        if (this.grammar instanceof TreeWalkerGrammar) {
            println(new StringBuffer().append(this.labeledElementASTType).append(" ").append(ruleSymbol.getId()).append("_AST_in = (_t == ").append(this.labeledElementASTType).append("(ASTNULL)) ? ").append(this.labeledElementASTInit).append(" : _t;").toString());
        }
        if (this.grammar.buildAST) {
            println(new StringBuffer("returnAST = ").append(this.labeledElementASTInit).append(";").toString());
            println(new StringBuffer().append(namespaceAntlr).append("ASTPair currentAST;").toString());
            println(new StringBuffer().append(this.labeledElementASTType).append(" ").append(ruleSymbol.getId()).append("_AST = ").append(this.labeledElementASTInit).append(";").toString());
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
            String str3 = alternativeAt.semPred;
            if (str3 != null) {
                genSemPred(str3, this.currentRule.line);
            }
            if (alternativeAt.synPred != null) {
                this.antlrTool.warning("Syntactic predicate ignored for single alternative", this.grammar.getFilename(), alternativeAt.synPred.getLine(), alternativeAt.synPred.getColumn());
            }
            genAlt(alternativeAt, block);
        } else {
            this.grammar.theLLkAnalyzer.deterministic(block);
            genBlockFinish(genCommonBlock(block, false), this.throwNoViable);
        }
        if (exceptionSpecFindExceptionSpec != null || block.getDefaultErrorHandler()) {
            this.tabs--;
            println("}");
        }
        if (exceptionSpecFindExceptionSpec != null) {
            genErrorHandler(exceptionSpecFindExceptionSpec);
        } else if (block.getDefaultErrorHandler()) {
            println(new StringBuffer("catch (").append(this.exceptionThrown).append("& ex) {").toString());
            this.tabs++;
            if (this.grammar.hasSyntacticPredicate) {
                println("if( inputState->guessing == 0 ) {");
                this.tabs++;
            }
            println("reportError(ex);");
            if (!(this.grammar instanceof TreeWalkerGrammar)) {
                println(new StringBuffer("recover(ex,").append(getBitsetName(markBitsetForGen(this.grammar.theLLkAnalyzer.FOLLOW(1, block.endNode).fset))).append(");").toString());
                i4 = 1;
            } else {
                println(new StringBuffer("if ( _t != ").append(this.labeledElementASTInit).append(" )").toString());
                i4 = 1;
                this.tabs++;
                println("_t = _t->getNextSibling();");
                this.tabs--;
            }
            if (this.grammar.hasSyntacticPredicate) {
                this.tabs -= i4;
                println("} else {");
                this.tabs += i4;
                println("throw;");
                this.tabs -= i4;
                println("}");
            }
            this.tabs -= i4;
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
            println(new StringBuffer("if ( _createToken && _token==").append(namespaceAntlr).append("nullToken && _ttype!=").append(namespaceAntlr).append("Token::SKIP ) {").toString());
            println("   _token = makeToken(_ttype);");
            println("   _token->setText(text.substr(_begin, text.length()-_begin));");
            println("}");
            println("_returnToken = _token;");
            println("_saveIndex=0;");
        }
        if (block.returnAction != null) {
            println(new StringBuffer("return ").append(extractIdOfAction(block.returnAction, block.getLine(), block.getColumn())).append(";").toString());
        }
        this.tabs--;
        println("}");
        println("");
        this.genAST = z2;
    }

    public void genRuleHeader(RuleSymbol ruleSymbol, boolean z) {
        this.tabs = 1;
        if (this.DEBUG_CODE_GENERATOR || this.DEBUG_CPP_CODE_GENERATOR) {
            System.out.println(new StringBuffer("genRuleHeader(").append(ruleSymbol.getId()).append(Constant.AFTER_QUTO).toString());
        }
        if (!ruleSymbol.isDefined()) {
            this.antlrTool.error(new StringBuffer("undefined rule: ").append(ruleSymbol.getId()).toString());
            return;
        }
        RuleBlock block = ruleSymbol.getBlock();
        this.currentRule = block;
        this.currentASTResult = ruleSymbol.getId();
        boolean z2 = this.genAST;
        this.genAST = z2 && block.getAutoGen();
        this.saveText = block.getAutoGen();
        print(new StringBuffer().append(ruleSymbol.access).append(": ").toString());
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
        if (block.argAction != null) {
            _println("");
            this.tabs++;
            println(block.argAction);
            this.tabs--;
            print(Constant.AFTER_QUTO);
        } else {
            _print(Constant.AFTER_QUTO);
        }
        _println(";");
        this.tabs--;
        this.genAST = z2;
    }

    private void GenRuleInvocation(RuleRefElement ruleRefElement) {
        _print(new StringBuffer().append(ruleRefElement.targetRule).append("(").toString());
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
            String strProcessActionForSpecialSymbols = processActionForSpecialSymbols(ruleRefElement.args, ruleRefElement.line, this.currentRule, actionTransInfo);
            if (actionTransInfo.assignToRoot || actionTransInfo.refRuleRoot != null) {
                this.antlrTool.error(new StringBuffer("Arguments of rule reference '").append(ruleRefElement.targetRule).append("' cannot set or ref #").append(this.currentRule.getRuleName()).append(" on line ").append(ruleRefElement.getLine()).toString());
            }
            _print(strProcessActionForSpecialSymbols);
            if (ruleSymbol.block.argAction == null) {
                this.antlrTool.warning(new StringBuffer("Rule '").append(ruleRefElement.targetRule).append("' accepts no arguments").toString(), this.grammar.getFilename(), ruleRefElement.getLine(), ruleRefElement.getColumn());
            }
        }
        _println(");");
        if (this.grammar instanceof TreeWalkerGrammar) {
            println("_t = _retTree;");
        }
    }

    protected void genSemPred(String str, int i) {
        String strProcessActionForSpecialSymbols = processActionForSpecialSymbols(str, i, this.currentRule, new ActionTransInfo());
        String strEscapeString = this.charFormatter.escapeString(strProcessActionForSpecialSymbols);
        if (this.grammar.debuggingOutput && ((this.grammar instanceof ParserGrammar) || (this.grammar instanceof LexerGrammar))) {
            strProcessActionForSpecialSymbols = new StringBuffer("fireSemanticPredicateEvaluated(antlr.debug.SemanticPredicateEvent.VALIDATING,").append(addSemPred(strEscapeString)).append(",").append(strProcessActionForSpecialSymbols).append(Constant.AFTER_QUTO).toString();
        }
        println(new StringBuffer("if (!(").append(strProcessActionForSpecialSymbols).append("))").toString());
        this.tabs++;
        println(new StringBuffer("throw ").append(namespaceAntlr).append("SemanticException(\"").append(strEscapeString).append("\");").toString());
        this.tabs--;
    }

    protected void genSemPredMap(String str) {
        Enumeration enumerationElements = this.semPreds.elements();
        println(new StringBuffer("const char* ").append(str).append("_semPredNames[] = {").toString());
        this.tabs++;
        while (enumerationElements.hasMoreElements()) {
            println(new StringBuffer("\"").append(enumerationElements.nextElement()).append("\",").toString());
        }
        println("0");
        this.tabs--;
        println("};");
    }

    protected void genSynPred(SynPredBlock synPredBlock, String str) {
        if (this.DEBUG_CODE_GENERATOR || this.DEBUG_CPP_CODE_GENERATOR) {
            System.out.println(new StringBuffer("gen=>(").append(synPredBlock).append(Constant.AFTER_QUTO).toString());
        }
        println(new StringBuffer("bool synPredMatched").append(synPredBlock.ID).append(" = false;").toString());
        if (this.grammar instanceof TreeWalkerGrammar) {
            println(new StringBuffer("if (_t == ").append(this.labeledElementASTInit).append(" )").toString());
            this.tabs++;
            println("_t = ASTNULL;");
            this.tabs--;
        }
        println(new StringBuffer("if (").append(str).append(") {").toString());
        this.tabs++;
        if (this.grammar instanceof TreeWalkerGrammar) {
            println(new StringBuffer().append(this.labeledElementType).append(" __t").append(synPredBlock.ID).append(" = _t;").toString());
        } else {
            println(new StringBuffer("int _m").append(synPredBlock.ID).append(" = mark();").toString());
        }
        println(new StringBuffer("synPredMatched").append(synPredBlock.ID).append(" = true;").toString());
        println("inputState->guessing++;");
        if (this.grammar.debuggingOutput && ((this.grammar instanceof ParserGrammar) || (this.grammar instanceof LexerGrammar))) {
            println("fireSyntacticPredicateStarted();");
        }
        this.syntacticPredLevel++;
        println("try {");
        this.tabs++;
        gen(synPredBlock);
        this.tabs--;
        println("}");
        println(new StringBuffer("catch (").append(this.exceptionThrown).append("& pe) {").toString());
        this.tabs++;
        println(new StringBuffer("synPredMatched").append(synPredBlock.ID).append(" = false;").toString());
        this.tabs--;
        println("}");
        if (this.grammar instanceof TreeWalkerGrammar) {
            println(new StringBuffer("_t = __t").append(synPredBlock.ID).append(";").toString());
        } else {
            println(new StringBuffer("rewind(_m").append(synPredBlock.ID).append(");").toString());
        }
        println("inputState->guessing--;");
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
    }

    public void genTokenStrings(String str) {
        TokenSymbol tokenSymbol;
        println(new StringBuffer("const char* ").append(str).append("tokenNames[] = {").toString());
        this.tabs++;
        Vector vocabulary = this.grammar.tokenManager.getVocabulary();
        for (int i = 0; i < vocabulary.size(); i++) {
            String strStripFrontBack = (String) vocabulary.elementAt(i);
            if (strStripFrontBack == null) {
                strStripFrontBack = new StringBuffer("<").append(String.valueOf(i)).append(">").toString();
            }
            if (!strStripFrontBack.startsWith("\"") && !strStripFrontBack.startsWith("<") && (tokenSymbol = this.grammar.tokenManager.getTokenSymbol(strStripFrontBack)) != null && tokenSymbol.getParaphrase() != null) {
                strStripFrontBack = StringUtils.stripFrontBack(tokenSymbol.getParaphrase(), "\"", "\"");
            }
            print(this.charFormatter.literalString(strStripFrontBack));
            _println(",");
        }
        println("0");
        this.tabs--;
        println("};");
    }

    protected void genTokenTypes(TokenManager tokenManager) throws IOException {
        this.outputFile = new StringBuffer().append(tokenManager.getName()).append(TokenTypesFileSuffix).append(".hpp").toString();
        this.outputLine = 1;
        this.currentOutput = this.antlrTool.openOutputFile(this.outputFile);
        this.tabs = 0;
        println(new StringBuffer("#ifndef INC_").append(tokenManager.getName()).append(TokenTypesFileSuffix).append("_hpp_").toString());
        println(new StringBuffer("#define INC_").append(tokenManager.getName()).append(TokenTypesFileSuffix).append("_hpp_").toString());
        println("");
        NameSpace nameSpace2 = nameSpace;
        if (nameSpace2 != null) {
            nameSpace2.emitDeclarations(this.currentOutput);
        }
        genHeader(this.outputFile);
        println("");
        println("#ifndef CUSTOM_API");
        println("# define CUSTOM_API");
        println("#endif");
        println("");
        println("#ifdef __cplusplus");
        println(new StringBuffer("struct CUSTOM_API ").append(tokenManager.getName()).append(TokenTypesFileSuffix).append(" {").toString());
        println("#endif");
        this.tabs++;
        println("enum {");
        this.tabs++;
        Vector vocabulary = tokenManager.getVocabulary();
        println("EOF_ = 1,");
        for (int i = 4; i < vocabulary.size(); i++) {
            String str = (String) vocabulary.elementAt(i);
            if (str != null) {
                if (str.startsWith("\"")) {
                    StringLiteralSymbol stringLiteralSymbol = (StringLiteralSymbol) tokenManager.getTokenSymbol(str);
                    if (stringLiteralSymbol == null) {
                        this.antlrTool.panic(new StringBuffer("String literal ").append(str).append(" not in symbol table").toString());
                    } else if (stringLiteralSymbol.label != null) {
                        println(new StringBuffer().append(stringLiteralSymbol.label).append(" = ").append(i).append(",").toString());
                    } else {
                        String strMangleLiteral = mangleLiteral(str);
                        if (strMangleLiteral != null) {
                            println(new StringBuffer().append(strMangleLiteral).append(" = ").append(i).append(",").toString());
                            stringLiteralSymbol.label = strMangleLiteral;
                        } else {
                            println(new StringBuffer("// ").append(str).append(" = ").append(i).toString());
                        }
                    }
                } else if (!str.startsWith("<")) {
                    println(new StringBuffer().append(str).append(" = ").append(i).append(",").toString());
                }
            }
        }
        println("NULL_TREE_LOOKAHEAD = 3");
        this.tabs--;
        println("};");
        this.tabs--;
        println("#ifdef __cplusplus");
        println("};");
        println("#endif");
        NameSpace nameSpace3 = nameSpace;
        if (nameSpace3 != null) {
            nameSpace3.emitClosures(this.currentOutput);
        }
        println(new StringBuffer("#endif /*INC_").append(tokenManager.getName()).append(TokenTypesFileSuffix).append("_hpp_*/").toString());
        this.currentOutput.close();
        this.currentOutput = null;
        exitIfError();
    }

    @Override // antlr.CodeGenerator
    public String processStringForASTConstructor(String str) {
        if (this.usingCustomAST) {
            return (((this.grammar instanceof TreeWalkerGrammar) || (this.grammar instanceof ParserGrammar)) && !this.grammar.tokenManager.tokenDefined(str)) ? new StringBuffer().append(namespaceAntlr).append("RefAST(").append(str).append(Constant.AFTER_QUTO).toString() : str;
        }
        return str;
    }

    @Override // antlr.CodeGenerator
    public String getASTCreateString(Vector vector) {
        if (vector.size() == 0) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(new StringBuffer().append(this.labeledElementASTType).append("(astFactory->make((new ").append(namespaceAntlr).append("ASTArray(").append(vector.size()).append("))").toString());
        for (int i = 0; i < vector.size(); i++) {
            stringBuffer.append(new StringBuffer("->add(").append(vector.elementAt(i)).append(Constant.AFTER_QUTO).toString());
        }
        stringBuffer.append("))");
        return stringBuffer.toString();
    }

    @Override // antlr.CodeGenerator
    public String getASTCreateString(GrammarAtom grammarAtom, String str) {
        if (grammarAtom != null && grammarAtom.getASTNodeType() != null) {
            this.astTypes.ensureCapacity(grammarAtom.getType());
            String str2 = (String) this.astTypes.elementAt(grammarAtom.getType());
            if (str2 != null && !grammarAtom.getASTNodeType().equals(str2)) {
                this.antlrTool.warning(new StringBuffer("Attempt to redefine AST type for ").append(grammarAtom.getText()).toString(), this.grammar.getFilename(), grammarAtom.getLine(), grammarAtom.getColumn());
                this.antlrTool.warning(new StringBuffer(" from \"").append(str2).append("\" to \"").append(grammarAtom.getASTNodeType()).append("\" sticking to \"").append(str2).append("\"").toString(), this.grammar.getFilename(), grammarAtom.getLine(), grammarAtom.getColumn());
            } else {
                this.astTypes.setElementAt(grammarAtom.getASTNodeType(), grammarAtom.getType());
            }
            return new StringBuffer("astFactory->create(").append(str).append(Constant.AFTER_QUTO).toString();
        }
        boolean z = str.indexOf(44) != -1 ? this.grammar.tokenManager.tokenDefined(str.substring(0, str.indexOf(44))) : false;
        if (!this.usingCustomAST || !(this.grammar instanceof TreeWalkerGrammar) || this.grammar.tokenManager.tokenDefined(str) || z) {
            return new StringBuffer("astFactory->create(").append(str).append(Constant.AFTER_QUTO).toString();
        }
        return new StringBuffer("astFactory->create(").append(namespaceAntlr).append("RefAST(").append(str).append("))").toString();
    }

    public String getASTCreateString(String str) {
        if (this.usingCustomAST) {
            return new StringBuffer().append(this.labeledElementASTType).append("(astFactory->create(").append(namespaceAntlr).append("RefAST(").append(str).append(")))").toString();
        }
        return new StringBuffer("astFactory->create(").append(str).append(Constant.AFTER_QUTO).toString();
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
            return "true";
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
                stringBuffer.append(" || ");
            }
            stringBuffer.append(strLookaheadString);
            stringBuffer.append(" == ");
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
            return id2.equals("EOF") ? new StringBuffer().append(namespaceAntlr).append("Token::EOF_TYPE").toString() : id2;
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
            return "_t->getType()";
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
            boolean z2 = !this.grammar.buildAST;
            if (str.length() <= 3 || str.lastIndexOf("_in") != str.length() - 3) {
                z = z2;
            } else {
                str = str.substring(0, str.length() - 3);
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

    private String fixNameSpaceOption(String str) {
        String strStripFrontBack = StringUtils.stripFrontBack(str, "\"", "\"");
        return (strStripFrontBack.length() <= 2 || strStripFrontBack.substring(strStripFrontBack.length() - 2, strStripFrontBack.length()).equals("::")) ? strStripFrontBack : new StringBuffer().append(strStripFrontBack).append("::").toString();
    }

    private void setupGrammarParameters(Grammar grammar) {
        Token option;
        Token option2;
        String strStripFrontBack;
        Token option3;
        String strStripFrontBack2;
        Token option4;
        Token option5;
        String strStripFrontBack3;
        Token option6;
        String strStripFrontBack4;
        boolean z = grammar instanceof ParserGrammar;
        if (z || (grammar instanceof LexerGrammar) || (grammar instanceof TreeWalkerGrammar)) {
            if (this.antlrTool.nameSpace != null) {
                nameSpace = this.antlrTool.nameSpace;
            }
            if (this.antlrTool.namespaceStd != null) {
                namespaceStd = fixNameSpaceOption(this.antlrTool.namespaceStd);
            }
            if (this.antlrTool.namespaceAntlr != null) {
                namespaceAntlr = fixNameSpaceOption(this.antlrTool.namespaceAntlr);
            }
            this.genHashLines = this.antlrTool.genHashLines;
            if (grammar.hasOption("namespace") && (option4 = grammar.getOption("namespace")) != null) {
                nameSpace = new NameSpace(option4.getText());
            }
            if (grammar.hasOption("namespaceAntlr") && (option3 = grammar.getOption("namespaceAntlr")) != null && (strStripFrontBack2 = StringUtils.stripFrontBack(option3.getText(), "\"", "\"")) != null) {
                if (strStripFrontBack2.length() > 2 && !strStripFrontBack2.substring(strStripFrontBack2.length() - 2, strStripFrontBack2.length()).equals("::")) {
                    strStripFrontBack2 = new StringBuffer().append(strStripFrontBack2).append("::").toString();
                }
                namespaceAntlr = strStripFrontBack2;
            }
            if (grammar.hasOption("namespaceStd") && (option2 = grammar.getOption("namespaceStd")) != null && (strStripFrontBack = StringUtils.stripFrontBack(option2.getText(), "\"", "\"")) != null) {
                if (strStripFrontBack.length() > 2 && !strStripFrontBack.substring(strStripFrontBack.length() - 2, strStripFrontBack.length()).equals("::")) {
                    strStripFrontBack = new StringBuffer().append(strStripFrontBack).append("::").toString();
                }
                namespaceStd = strStripFrontBack;
            }
            if (grammar.hasOption("genHashLines") && (option = grammar.getOption("genHashLines")) != null) {
                this.genHashLines = StringUtils.stripFrontBack(option.getText(), "\"", "\"").equals("true");
            }
            this.noConstructors = this.antlrTool.noConstructors;
            if (grammar.hasOption("noConstructors")) {
                Token option7 = grammar.getOption("noConstructors");
                if (option7 != null && !option7.getText().equals("true") && !option7.getText().equals("false")) {
                    this.antlrTool.error("noConstructors option must be true or false", this.antlrTool.getGrammarFile(), option7.getLine(), option7.getColumn());
                }
                this.noConstructors = option7.getText().equals("true");
            }
        }
        if (z) {
            this.labeledElementASTType = new StringBuffer().append(namespaceAntlr).append("RefAST").toString();
            this.labeledElementASTInit = new StringBuffer().append(namespaceAntlr).append("nullAST").toString();
            if (grammar.hasOption("ASTLabelType") && (option6 = grammar.getOption("ASTLabelType")) != null && (strStripFrontBack4 = StringUtils.stripFrontBack(option6.getText(), "\"", "\"")) != null) {
                this.usingCustomAST = true;
                this.labeledElementASTType = strStripFrontBack4;
                this.labeledElementASTInit = new StringBuffer().append(strStripFrontBack4).append("(").append(namespaceAntlr).append("nullAST)").toString();
            }
            this.labeledElementType = new StringBuffer().append(namespaceAntlr).append("RefToken ").toString();
            this.labeledElementInit = new StringBuffer().append(namespaceAntlr).append("nullToken").toString();
            this.commonExtraArgs = "";
            this.commonExtraParams = "";
            this.commonLocalVars = "";
            this.lt1Value = "LT(1)";
            this.exceptionThrown = new StringBuffer().append(namespaceAntlr).append("RecognitionException").toString();
            this.throwNoViable = new StringBuffer("throw ").append(namespaceAntlr).append("NoViableAltException(LT(1), getFilename());").toString();
            return;
        }
        if (grammar instanceof LexerGrammar) {
            this.labeledElementType = "char ";
            this.labeledElementInit = "'\\0'";
            this.commonExtraArgs = "";
            this.commonExtraParams = "bool _createToken";
            this.commonLocalVars = new StringBuffer("int _ttype; ").append(namespaceAntlr).append("RefToken _token; ").append(namespaceStd).append("string::size_type _begin = text.length();").toString();
            this.lt1Value = "LA(1)";
            this.exceptionThrown = new StringBuffer().append(namespaceAntlr).append("RecognitionException").toString();
            this.throwNoViable = new StringBuffer("throw ").append(namespaceAntlr).append("NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());").toString();
            return;
        }
        if (grammar instanceof TreeWalkerGrammar) {
            this.labeledElementInit = new StringBuffer().append(namespaceAntlr).append("nullAST").toString();
            this.labeledElementASTInit = new StringBuffer().append(namespaceAntlr).append("nullAST").toString();
            this.labeledElementASTType = new StringBuffer().append(namespaceAntlr).append("RefAST").toString();
            this.labeledElementType = new StringBuffer().append(namespaceAntlr).append("RefAST").toString();
            this.commonExtraParams = new StringBuffer().append(namespaceAntlr).append("RefAST _t").toString();
            this.throwNoViable = new StringBuffer("throw ").append(namespaceAntlr).append("NoViableAltException(_t);").toString();
            this.lt1Value = "_t";
            if (grammar.hasOption("ASTLabelType") && (option5 = grammar.getOption("ASTLabelType")) != null && (strStripFrontBack3 = StringUtils.stripFrontBack(option5.getText(), "\"", "\"")) != null) {
                this.usingCustomAST = true;
                this.labeledElementASTType = strStripFrontBack3;
                this.labeledElementType = strStripFrontBack3;
                String string = new StringBuffer().append(strStripFrontBack3).append("(").append(namespaceAntlr).append("nullAST)").toString();
                this.labeledElementInit = string;
                this.labeledElementASTInit = string;
                this.commonExtraParams = new StringBuffer().append(strStripFrontBack3).append(" _t").toString();
                this.throwNoViable = new StringBuffer("throw ").append(namespaceAntlr).append("NoViableAltException(").append(namespaceAntlr).append("RefAST(_t));").toString();
                this.lt1Value = "_t";
            }
            if (!grammar.hasOption("ASTLabelType")) {
                grammar.setOption("ASTLabelType", new Token(6, new StringBuffer().append(namespaceAntlr).append("RefAST").toString()));
            }
            this.commonExtraArgs = "_t";
            this.commonLocalVars = "";
            this.exceptionThrown = new StringBuffer().append(namespaceAntlr).append("RecognitionException").toString();
            return;
        }
        this.antlrTool.panic("Unknown grammar type");
    }
}
