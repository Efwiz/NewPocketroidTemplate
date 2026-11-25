package antlr;

import antlr.actions.python.ActionLexer;
import antlr.actions.python.CodeLexer;
import antlr.collections.impl.BitSet;
import antlr.collections.impl.Vector;
import com.huawei.hms.framework.common.ContainerUtils;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Hashtable;
import name.antonsmirnov.firmata.FormatHelper;
import org.antlr.runtime.BaseRecognizer;
import org.apache.commons.lang3.SystemProperties;
import org.catrobat.catroid.common.Constants;
import org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;

/* JADX INFO: loaded from: classes.dex */
public class PythonCodeGenerator extends CodeGenerator {
    protected static final String NONUNIQUE = new String();
    public static final int caseSizeThreshold = 127;
    public static final String initHeaderAction = "__init__";
    public static final String mainHeaderAction = "__main__";
    String commonExtraArgs;
    String commonExtraParams;
    String commonLocalVars;
    String currentASTResult;
    RuleBlock currentRule;
    String exceptionThrown;
    String labeledElementASTType;
    String labeledElementInit;
    String labeledElementType;
    String lexerClassName;
    String lt1Value;
    String parserClassName;
    private Vector semPreds;
    String throwNoViable;
    String treeWalkerClassName;
    protected int syntacticPredLevel = 0;
    protected boolean genAST = false;
    protected boolean saveText = false;
    Hashtable treeVariableMap = new Hashtable();
    Hashtable declaredASTVariables = new Hashtable();
    int astVarNumber = 1;

    protected void genMatch(BitSet bitSet) {
    }

    protected boolean isspace(char c) {
        return c == '\t' || c == '\n' || c == '\r' || c == ' ';
    }

    @Override // antlr.CodeGenerator
    protected void printTabs() {
        for (int i = 0; i < this.tabs; i++) {
            this.currentOutput.print("    ");
        }
    }

    public PythonCodeGenerator() {
        this.charFormatter = new PythonCharFormatter();
        this.DEBUG_CODE_GENERATOR = true;
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

    protected void checkCurrentOutputStream() {
        try {
            if (this.currentOutput != null) {
            } else {
                throw new NullPointerException();
            }
        } catch (Exception unused) {
            Utils.error("current output is not set");
        }
    }

    @Override // antlr.CodeGenerator
    protected String extractIdOfAction(String str, int i, int i2) {
        return removeAssignmentFromDeclaration(str).trim();
    }

    @Override // antlr.CodeGenerator
    protected String extractTypeOfAction(String str, int i, int i2) {
        return "";
    }

    protected void flushTokens() {
        try {
            checkCurrentOutputStream();
            println("");
            println("### import antlr.Token ");
            println("from antlr import Token");
            println("### >>>The Known Token Types <<<");
            PrintWriter printWriter = this.currentOutput;
            Enumeration enumerationElements = this.behavior.tokenManagers.elements();
            boolean z = false;
            while (enumerationElements.hasMoreElements()) {
                TokenManager tokenManager = (TokenManager) enumerationElements.nextElement();
                if (!tokenManager.isReadOnly()) {
                    if (!z) {
                        genTokenTypes(tokenManager);
                        z = true;
                    }
                    this.currentOutput = printWriter;
                    genTokenInterchange(tokenManager);
                    this.currentOutput = printWriter;
                }
                exitIfError();
            }
        } catch (Exception unused) {
            exitIfError();
        }
        checkCurrentOutputStream();
        println("");
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
        } catch (IOException e) {
            this.antlrTool.reportException(e, null);
        }
    }

    @Override // antlr.CodeGenerator
    public void gen(ActionElement actionElement) {
        if (actionElement.isSemPred) {
            genSemPred(actionElement.actionText, actionElement.line);
            return;
        }
        if (this.grammar.hasSyntacticPredicate) {
            println("if not self.inputState.guessing:");
            this.tabs++;
        }
        ActionTransInfo actionTransInfo = new ActionTransInfo();
        String strProcessActionForSpecialSymbols = processActionForSpecialSymbols(actionElement.actionText, actionElement.getLine(), this.currentRule, actionTransInfo);
        if (actionTransInfo.refRuleRoot != null) {
            println(new StringBuffer().append(actionTransInfo.refRuleRoot).append(" = currentAST.root").toString());
        }
        printAction(strProcessActionForSpecialSymbols);
        if (actionTransInfo.assignToRoot) {
            println(new StringBuffer("currentAST.root = ").append(actionTransInfo.refRuleRoot).append("").toString());
            println(new StringBuffer("if (").append(actionTransInfo.refRuleRoot).append(" != None) and (").append(actionTransInfo.refRuleRoot).append(".getFirstChild() != None):").toString());
            this.tabs++;
            println(new StringBuffer("currentAST.child = ").append(actionTransInfo.refRuleRoot).append(".getFirstChild()").toString());
            this.tabs--;
            println("else:");
            this.tabs++;
            println(new StringBuffer("currentAST.child = ").append(actionTransInfo.refRuleRoot).toString());
            this.tabs--;
            println("currentAST.advanceChildToEnd()");
        }
        if (this.grammar.hasSyntacticPredicate) {
            this.tabs--;
        }
    }

    @Override // antlr.CodeGenerator
    public void gen(AlternativeBlock alternativeBlock) {
        if (this.DEBUG_CODE_GENERATOR) {
            System.out.println(new StringBuffer("gen(").append(alternativeBlock).append(Constant.AFTER_QUTO).toString());
        }
        genBlockPreamble(alternativeBlock);
        genBlockInitAction(alternativeBlock);
        String str = this.currentASTResult;
        if (alternativeBlock.getLabel() != null) {
            this.currentASTResult = alternativeBlock.getLabel();
        }
        this.grammar.theLLkAnalyzer.deterministic(alternativeBlock);
        int i = this.tabs;
        genBlockFinish(genCommonBlock(alternativeBlock, true), this.throwNoViable);
        this.tabs = i;
        this.currentASTResult = str;
    }

    @Override // antlr.CodeGenerator
    public void gen(BlockEndElement blockEndElement) {
        if (this.DEBUG_CODE_GENERATOR) {
            System.out.println(new StringBuffer("genRuleEnd(").append(blockEndElement).append(Constant.AFTER_QUTO).toString());
        }
    }

    /* JADX WARN: Code duplicated, block: B:13:0x0050  */
    @Override // antlr.CodeGenerator
    public void gen(CharLiteralElement charLiteralElement) {
        boolean z;
        if (this.DEBUG_CODE_GENERATOR) {
            System.out.println(new StringBuffer("genChar(").append(charLiteralElement).append(Constant.AFTER_QUTO).toString());
        }
        if (charLiteralElement.getLabel() != null) {
            println(new StringBuffer().append(charLiteralElement.getLabel()).append(" = ").append(this.lt1Value).toString());
        }
        boolean z2 = this.saveText;
        if (z2) {
            z = charLiteralElement.getAutoGenType() == 1;
        }
        this.saveText = z;
        genMatch(charLiteralElement);
        this.saveText = z2;
    }

    String toString(boolean z) {
        if (z) {
            return "True";
        }
        return "False";
    }

    @Override // antlr.CodeGenerator
    public void gen(CharRangeElement charRangeElement) {
        if (charRangeElement.getLabel() != null && this.syntacticPredLevel == 0) {
            println(new StringBuffer().append(charRangeElement.getLabel()).append(" = ").append(this.lt1Value).toString());
        }
        boolean z = (this.grammar instanceof LexerGrammar) && (!this.saveText || charRangeElement.getAutoGenType() == 3);
        if (z) {
            println("_saveIndex = self.text.length()");
        }
        println(new StringBuffer("self.matchRange(u").append(charRangeElement.beginText).append(", u").append(charRangeElement.endText).append(Constant.AFTER_QUTO).toString());
        if (z) {
            println("self.text.setLength(_saveIndex)");
        }
    }

    @Override // antlr.CodeGenerator
    public void gen(LexerGrammar lexerGrammar) throws IOException {
        String string;
        if (lexerGrammar.debuggingOutput) {
            this.semPreds = new Vector();
        }
        setGrammar(lexerGrammar);
        if (!(this.grammar instanceof LexerGrammar)) {
            this.antlrTool.panic("Internal error generating lexer");
        }
        setupOutput(this.grammar.getClassName());
        this.genAST = false;
        this.saveText = true;
        this.tabs = 0;
        genHeader();
        println("### import antlr and other modules ..");
        println("import sys");
        println("import antlr");
        println("");
        println("version = sys.version.split()[0]");
        println("if version < '2.2.1':");
        this.tabs++;
        println("False = 0");
        this.tabs--;
        println("if version < '2.3':");
        this.tabs++;
        println("True = not False");
        this.tabs--;
        println("### header action >>> ");
        printActionCode(this.behavior.getHeaderAction(""), 0);
        println("### header action <<< ");
        println("### preamble action >>> ");
        printActionCode(this.grammar.preambleAction.getText(), 0);
        println("### preamble action <<< ");
        if (this.grammar.superClass != null) {
            string = this.grammar.superClass;
        } else {
            string = new StringBuffer("antlr.").append(this.grammar.getSuperClass()).toString();
        }
        Token token = (Token) this.grammar.options.get("classHeaderPrefix");
        if (token != null) {
            StringUtils.stripFrontBack(token.getText(), "\"", "\"");
        }
        println("### >>>The Literals<<<");
        println("literals = {}");
        Enumeration tokenSymbolKeys = this.grammar.tokenManager.getTokenSymbolKeys();
        while (tokenSymbolKeys.hasMoreElements()) {
            String str = (String) tokenSymbolKeys.nextElement();
            if (str.charAt(0) == '\"') {
                TokenSymbol tokenSymbol = this.grammar.tokenManager.getTokenSymbol(str);
                if (tokenSymbol instanceof StringLiteralSymbol) {
                    StringLiteralSymbol stringLiteralSymbol = (StringLiteralSymbol) tokenSymbol;
                    println(new StringBuffer("literals[u").append(stringLiteralSymbol.getId()).append("] = ").append(stringLiteralSymbol.getTokenType()).toString());
                }
            }
        }
        println("");
        flushTokens();
        genJavadocComment(this.grammar);
        println(new StringBuffer(ExternalAnnotationProvider.CLASS_PREFIX).append(this.lexerClassName).append("(").append(string).append(") :").toString());
        this.tabs++;
        printGrammarAction(this.grammar);
        println("def __init__(self, *argv, **kwargs) :");
        this.tabs++;
        println(new StringBuffer().append(string).append(".__init__(self, *argv, **kwargs)").toString());
        println(new StringBuffer("self.caseSensitiveLiterals = ").append(toString(lexerGrammar.caseSensitiveLiterals)).toString());
        println(new StringBuffer("self.setCaseSensitive(").append(toString(lexerGrammar.caseSensitive)).append(Constant.AFTER_QUTO).toString());
        println("self.literals = literals");
        if (this.grammar.debuggingOutput) {
            println("ruleNames[] = [");
            Enumeration enumerationElements = this.grammar.rules.elements();
            this.tabs++;
            while (enumerationElements.hasMoreElements()) {
                GrammarSymbol grammarSymbol = (GrammarSymbol) enumerationElements.nextElement();
                if (grammarSymbol instanceof RuleSymbol) {
                    println(new StringBuffer("\"").append(((RuleSymbol) grammarSymbol).getId()).append("\",").toString());
                }
            }
            this.tabs--;
            println("]");
        }
        genHeaderInit(this.grammar);
        this.tabs--;
        genNextToken();
        println("");
        Enumeration enumerationElements2 = this.grammar.rules.elements();
        int i = 0;
        while (enumerationElements2.hasMoreElements()) {
            RuleSymbol ruleSymbol = (RuleSymbol) enumerationElements2.nextElement();
            if (!ruleSymbol.getId().equals("mnextToken")) {
                genRule(ruleSymbol, false, i);
                i++;
            }
            exitIfError();
        }
        if (this.grammar.debuggingOutput) {
            genSemPredMap();
        }
        genBitsets(this.bitsetsUsed, ((LexerGrammar) this.grammar).charVocabulary.size());
        println("");
        genHeaderMain(this.grammar);
        this.currentOutput.close();
        this.currentOutput = null;
    }

    protected void genHeaderMain(Grammar grammar) {
        String headerAction = this.behavior.getHeaderAction(new StringBuffer().append(grammar.getClassName()).append(".__main__").toString());
        if (isEmpty(headerAction)) {
            headerAction = this.behavior.getHeaderAction(mainHeaderAction);
        }
        if (isEmpty(headerAction)) {
            if (grammar instanceof LexerGrammar) {
                int i = this.tabs;
                this.tabs = 0;
                println("### __main__ header action >>> ");
                genLexerTest();
                this.tabs = 0;
                println("### __main__ header action <<< ");
                this.tabs = i;
                return;
            }
            return;
        }
        int i2 = this.tabs;
        this.tabs = 0;
        println("");
        println("### __main__ header action >>> ");
        printMainFunc(headerAction);
        this.tabs = 0;
        println("### __main__ header action <<< ");
        this.tabs = i2;
    }

    protected void genHeaderInit(Grammar grammar) {
        String headerAction = this.behavior.getHeaderAction(new StringBuffer().append(grammar.getClassName()).append(".__init__").toString());
        if (isEmpty(headerAction)) {
            headerAction = this.behavior.getHeaderAction(initHeaderAction);
        }
        if (isEmpty(headerAction)) {
            return;
        }
        int i = this.tabs;
        println("### __init__ header action >>> ");
        printActionCode(headerAction, 0);
        this.tabs = i;
        println("### __init__ header action <<< ");
    }

    protected void printMainFunc(String str) {
        int i = this.tabs;
        this.tabs = 0;
        println("if __name__ == '__main__':");
        this.tabs++;
        printActionCode(str, 0);
        this.tabs--;
        this.tabs = i;
    }

    /* JADX WARN: Code duplicated, block: B:24:0x00a2  */
    @Override // antlr.CodeGenerator
    public void gen(OneOrMoreBlock oneOrMoreBlock) {
        String string;
        boolean z;
        int i = this.tabs;
        genBlockPreamble(oneOrMoreBlock);
        if (oneOrMoreBlock.getLabel() != null) {
            string = new StringBuffer("_cnt_").append(oneOrMoreBlock.getLabel()).toString();
        } else {
            string = new StringBuffer("_cnt").append(oneOrMoreBlock.ID).toString();
        }
        println(new StringBuffer("").append(string).append("= 0").toString());
        println("while True:");
        this.tabs++;
        int i2 = this.tabs;
        genBlockInitAction(oneOrMoreBlock);
        String str = this.currentASTResult;
        if (oneOrMoreBlock.getLabel() != null) {
            this.currentASTResult = oneOrMoreBlock.getLabel();
        }
        this.grammar.theLLkAnalyzer.deterministic(oneOrMoreBlock);
        int i3 = this.grammar.maxk;
        if (!oneOrMoreBlock.greedy && oneOrMoreBlock.exitLookaheadDepth <= this.grammar.maxk && oneOrMoreBlock.exitCache[oneOrMoreBlock.exitLookaheadDepth].containsEpsilon()) {
            i3 = oneOrMoreBlock.exitLookaheadDepth;
        } else {
            if (oneOrMoreBlock.greedy || oneOrMoreBlock.exitLookaheadDepth != Integer.MAX_VALUE) {
                z = false;
            }
            if (z) {
                println(new StringBuffer("### nongreedy (...)+ loop; exit depth is ").append(oneOrMoreBlock.exitLookaheadDepth).toString());
                String lookaheadTestExpression = getLookaheadTestExpression(oneOrMoreBlock.exitCache, i3);
                println("### nongreedy exit test");
                println(new StringBuffer("if ").append(string).append(" >= 1 and ").append(lookaheadTestExpression).append(":").toString());
                this.tabs++;
                println(PsiKeyword.BREAK);
                this.tabs--;
            }
            int i4 = this.tabs;
            genBlockFinish(genCommonBlock(oneOrMoreBlock, false), PsiKeyword.BREAK);
            this.tabs = i4;
            this.tabs = i2;
            println(new StringBuffer().append(string).append(" += 1").toString());
            this.tabs = i2;
            this.tabs--;
            println(new StringBuffer("if ").append(string).append(" < 1:").toString());
            this.tabs++;
            println(this.throwNoViable);
            this.tabs--;
            this.currentASTResult = str;
        }
        z = true;
        if (z) {
            println(new StringBuffer("### nongreedy (...)+ loop; exit depth is ").append(oneOrMoreBlock.exitLookaheadDepth).toString());
            String lookaheadTestExpression2 = getLookaheadTestExpression(oneOrMoreBlock.exitCache, i3);
            println("### nongreedy exit test");
            println(new StringBuffer("if ").append(string).append(" >= 1 and ").append(lookaheadTestExpression2).append(":").toString());
            this.tabs++;
            println(PsiKeyword.BREAK);
            this.tabs--;
        }
        int i5 = this.tabs;
        genBlockFinish(genCommonBlock(oneOrMoreBlock, false), PsiKeyword.BREAK);
        this.tabs = i5;
        this.tabs = i2;
        println(new StringBuffer().append(string).append(" += 1").toString());
        this.tabs = i2;
        this.tabs--;
        println(new StringBuffer("if ").append(string).append(" < 1:").toString());
        this.tabs++;
        println(this.throwNoViable);
        this.tabs--;
        this.currentASTResult = str;
    }

    @Override // antlr.CodeGenerator
    public void gen(ParserGrammar parserGrammar) throws IOException {
        String string;
        if (parserGrammar.debuggingOutput) {
            this.semPreds = new Vector();
        }
        setGrammar(parserGrammar);
        if (!(this.grammar instanceof ParserGrammar)) {
            this.antlrTool.panic("Internal error generating parser");
        }
        setupOutput(this.grammar.getClassName());
        this.genAST = this.grammar.buildAST;
        this.tabs = 0;
        genHeader();
        println("### import antlr and other modules ..");
        println("import sys");
        println("import antlr");
        println("");
        println("version = sys.version.split()[0]");
        println("if version < '2.2.1':");
        this.tabs++;
        println("False = 0");
        this.tabs--;
        println("if version < '2.3':");
        this.tabs++;
        println("True = not False");
        this.tabs--;
        println("### header action >>> ");
        printActionCode(this.behavior.getHeaderAction(""), 0);
        println("### header action <<< ");
        println("### preamble action>>>");
        printActionCode(this.grammar.preambleAction.getText(), 0);
        println("### preamble action <<<");
        flushTokens();
        if (this.grammar.superClass != null) {
            string = this.grammar.superClass;
        } else {
            string = new StringBuffer("antlr.").append(this.grammar.getSuperClass()).toString();
        }
        genJavadocComment(this.grammar);
        Token token = (Token) this.grammar.options.get("classHeaderPrefix");
        if (token != null) {
            StringUtils.stripFrontBack(token.getText(), "\"", "\"");
        }
        print(new StringBuffer(ExternalAnnotationProvider.CLASS_PREFIX).append(this.parserClassName).append("(").append(string).toString());
        println("):");
        this.tabs++;
        if (this.grammar.debuggingOutput) {
            println("_ruleNames = [");
            Enumeration enumerationElements = this.grammar.rules.elements();
            this.tabs++;
            while (enumerationElements.hasMoreElements()) {
                GrammarSymbol grammarSymbol = (GrammarSymbol) enumerationElements.nextElement();
                if (grammarSymbol instanceof RuleSymbol) {
                    println(new StringBuffer("\"").append(((RuleSymbol) grammarSymbol).getId()).append("\",").toString());
                }
            }
            this.tabs--;
            println("]");
        }
        printGrammarAction(this.grammar);
        println("");
        println("def __init__(self, *args, **kwargs):");
        this.tabs++;
        println(new StringBuffer().append(string).append(".__init__(self, *args, **kwargs)").toString());
        println("self.tokenNames = _tokenNames");
        if (this.grammar.debuggingOutput) {
            println("self.ruleNames  = _ruleNames");
            println("self.semPredNames = _semPredNames");
            println("self.setupDebugging(self.tokenBuf)");
        }
        if (this.grammar.buildAST) {
            println("self.buildTokenTypeASTClassMap()");
            println("self.astFactory = antlr.ASTFactory(self.getTokenTypeToASTClassMap())");
            if (this.labeledElementASTType != null) {
                println(new StringBuffer("self.astFactory.setASTNodeClass(").append(this.labeledElementASTType).append(Constant.AFTER_QUTO).toString());
            }
        }
        genHeaderInit(this.grammar);
        println("");
        Enumeration enumerationElements2 = this.grammar.rules.elements();
        int i = 0;
        while (enumerationElements2.hasMoreElements()) {
            GrammarSymbol grammarSymbol2 = (GrammarSymbol) enumerationElements2.nextElement();
            if (grammarSymbol2 instanceof RuleSymbol) {
                RuleSymbol ruleSymbol = (RuleSymbol) grammarSymbol2;
                genRule(ruleSymbol, ruleSymbol.references.size() == 0, i);
                i++;
            }
            exitIfError();
        }
        if (this.grammar.buildAST) {
            genTokenASTNodeMap();
        }
        genTokenStrings();
        genBitsets(this.bitsetsUsed, this.grammar.tokenManager.maxTokenType());
        if (this.grammar.debuggingOutput) {
            genSemPredMap();
        }
        println("");
        this.tabs = 0;
        genHeaderMain(this.grammar);
        this.currentOutput.close();
        this.currentOutput = null;
    }

    @Override // antlr.CodeGenerator
    public void gen(RuleRefElement ruleRefElement) {
        if (this.DEBUG_CODE_GENERATOR) {
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
            println(new StringBuffer().append(ruleRefElement.getLabel()).append(" = antlr.ifelse(_t == antlr.ASTNULL, None, ").append(this.lt1Value).append(Constant.AFTER_QUTO).toString());
        }
        if ((this.grammar instanceof LexerGrammar) && (!this.saveText || ruleRefElement.getAutoGenType() == 3)) {
            println("_saveIndex = self.text.length()");
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
            println("self.text.setLength(_saveIndex)");
        }
        if (this.syntacticPredLevel == 0) {
            if (this.grammar.hasSyntacticPredicate && ((!this.grammar.buildAST || ruleRefElement.getLabel() == null) && this.genAST)) {
                ruleRefElement.getAutoGenType();
            }
            if (this.grammar.buildAST && ruleRefElement.getLabel() != null) {
                println(new StringBuffer().append(ruleRefElement.getLabel()).append("_AST = self.returnAST").toString());
            }
            if (this.genAST) {
                int autoGenType = ruleRefElement.getAutoGenType();
                if (autoGenType == 1) {
                    println("self.addASTChild(currentAST, self.returnAST)");
                } else if (autoGenType == 2) {
                    this.antlrTool.error("Internal: encountered ^ after rule reference");
                }
            }
            if ((this.grammar instanceof LexerGrammar) && ruleRefElement.getLabel() != null) {
                println(new StringBuffer().append(ruleRefElement.getLabel()).append(" = self._returnToken").toString());
            }
        }
        genErrorCatchForElement(ruleRefElement);
    }

    /* JADX WARN: Code duplicated, block: B:15:0x005d  */
    @Override // antlr.CodeGenerator
    public void gen(StringLiteralElement stringLiteralElement) {
        boolean z;
        if (this.DEBUG_CODE_GENERATOR) {
            System.out.println(new StringBuffer("genString(").append(stringLiteralElement).append(Constant.AFTER_QUTO).toString());
        }
        if (stringLiteralElement.getLabel() != null && this.syntacticPredLevel == 0) {
            println(new StringBuffer().append(stringLiteralElement.getLabel()).append(" = ").append(this.lt1Value).append("").toString());
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
            println("_t = _t.getNextSibling()");
        }
    }

    @Override // antlr.CodeGenerator
    public void gen(TokenRangeElement tokenRangeElement) {
        genErrorTryForElement(tokenRangeElement);
        if (tokenRangeElement.getLabel() != null && this.syntacticPredLevel == 0) {
            println(new StringBuffer().append(tokenRangeElement.getLabel()).append(" = ").append(this.lt1Value).toString());
        }
        genElementAST(tokenRangeElement);
        println(new StringBuffer("self.matchRange(u").append(tokenRangeElement.beginText).append(", u").append(tokenRangeElement.endText).append(Constant.AFTER_QUTO).toString());
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
            println(new StringBuffer().append(tokenRefElement.getLabel()).append(" = ").append(this.lt1Value).append("").toString());
        }
        genElementAST(tokenRefElement);
        genMatch(tokenRefElement);
        genErrorCatchForElement(tokenRefElement);
        if (this.grammar instanceof TreeWalkerGrammar) {
            println("_t = _t.getNextSibling()");
        }
    }

    @Override // antlr.CodeGenerator
    public void gen(TreeElement treeElement) {
        println(new StringBuffer("_t").append(treeElement.ID).append(" = _t").toString());
        if (treeElement.root.getLabel() != null) {
            println(new StringBuffer().append(treeElement.root.getLabel()).append(" = antlr.ifelse(_t == antlr.ASTNULL, None, _t)").toString());
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
            println(new StringBuffer("_currentAST").append(treeElement.ID).append(" = currentAST.copy()").toString());
            println("currentAST.root = currentAST.child");
            println("currentAST.child = None");
        }
        if (treeElement.root instanceof WildcardElement) {
            println("if not _t: raise antlr.MismatchedTokenException()");
        } else {
            genMatch(treeElement.root);
        }
        println("_t = _t.getFirstChild()");
        for (int i = 0; i < treeElement.getAlternatives().size(); i++) {
            for (AlternativeElement alternativeElement = treeElement.getAlternativeAt(i).head; alternativeElement != null; alternativeElement = alternativeElement.next) {
                alternativeElement.generate();
            }
        }
        if (this.grammar.buildAST) {
            println(new StringBuffer("currentAST = _currentAST").append(treeElement.ID).append("").toString());
        }
        println(new StringBuffer("_t = _t").append(treeElement.ID).append("").toString());
        println("_t = _t.getNextSibling()");
    }

    @Override // antlr.CodeGenerator
    public void gen(TreeWalkerGrammar treeWalkerGrammar) throws IOException {
        String string;
        setGrammar(treeWalkerGrammar);
        if (!(this.grammar instanceof TreeWalkerGrammar)) {
            this.antlrTool.panic("Internal error generating tree-walker");
        }
        setupOutput(this.grammar.getClassName());
        this.genAST = this.grammar.buildAST;
        this.tabs = 0;
        genHeader();
        println("### import antlr and other modules ..");
        println("import sys");
        println("import antlr");
        println("");
        println("version = sys.version.split()[0]");
        println("if version < '2.2.1':");
        this.tabs++;
        println("False = 0");
        this.tabs--;
        println("if version < '2.3':");
        this.tabs++;
        println("True = not False");
        this.tabs--;
        println("### header action >>> ");
        printActionCode(this.behavior.getHeaderAction(""), 0);
        println("### header action <<< ");
        flushTokens();
        println("### user code>>>");
        printActionCode(this.grammar.preambleAction.getText(), 0);
        println("### user code<<<");
        if (this.grammar.superClass != null) {
            string = this.grammar.superClass;
        } else {
            string = new StringBuffer("antlr.").append(this.grammar.getSuperClass()).toString();
        }
        println("");
        Token token = (Token) this.grammar.options.get("classHeaderPrefix");
        if (token != null) {
            StringUtils.stripFrontBack(token.getText(), "\"", "\"");
        }
        genJavadocComment(this.grammar);
        println(new StringBuffer(ExternalAnnotationProvider.CLASS_PREFIX).append(this.treeWalkerClassName).append("(").append(string).append("):").toString());
        this.tabs++;
        println("");
        println("# ctor ..");
        println("def __init__(self, *args, **kwargs):");
        this.tabs++;
        println(new StringBuffer().append(string).append(".__init__(self, *args, **kwargs)").toString());
        println("self.tokenNames = _tokenNames");
        genHeaderInit(this.grammar);
        this.tabs--;
        println("");
        printGrammarAction(this.grammar);
        Enumeration enumerationElements = this.grammar.rules.elements();
        int i = 0;
        while (enumerationElements.hasMoreElements()) {
            GrammarSymbol grammarSymbol = (GrammarSymbol) enumerationElements.nextElement();
            if (grammarSymbol instanceof RuleSymbol) {
                RuleSymbol ruleSymbol = (RuleSymbol) grammarSymbol;
                genRule(ruleSymbol, ruleSymbol.references.size() == 0, i);
                i++;
            }
            exitIfError();
        }
        genTokenStrings();
        genBitsets(this.bitsetsUsed, this.grammar.tokenManager.maxTokenType());
        this.tabs = 0;
        genHeaderMain(this.grammar);
        this.currentOutput.close();
        this.currentOutput = null;
    }

    @Override // antlr.CodeGenerator
    public void gen(WildcardElement wildcardElement) {
        if (wildcardElement.getLabel() != null && this.syntacticPredLevel == 0) {
            println(new StringBuffer().append(wildcardElement.getLabel()).append(" = ").append(this.lt1Value).append("").toString());
        }
        genElementAST(wildcardElement);
        if (this.grammar instanceof TreeWalkerGrammar) {
            println("if not _t:");
            this.tabs++;
            println("raise antlr.MismatchedTokenException()");
            this.tabs--;
        } else if (this.grammar instanceof LexerGrammar) {
            if ((this.grammar instanceof LexerGrammar) && (!this.saveText || wildcardElement.getAutoGenType() == 3)) {
                println("_saveIndex = self.text.length()");
            }
            println("self.matchNot(antlr.EOF_CHAR)");
            if ((this.grammar instanceof LexerGrammar) && (!this.saveText || wildcardElement.getAutoGenType() == 3)) {
                println("self.text.setLength(_saveIndex)");
            }
        } else {
            println(new StringBuffer("self.matchNot(").append(getValueString(1, false)).append(Constant.AFTER_QUTO).toString());
        }
        if (this.grammar instanceof TreeWalkerGrammar) {
            println("_t = _t.getNextSibling()");
        }
    }

    /* JADX WARN: Code duplicated, block: B:20:0x005d  */
    /* JADX WARN: Code duplicated, block: B:22:0x0061  */
    @Override // antlr.CodeGenerator
    public void gen(ZeroOrMoreBlock zeroOrMoreBlock) {
        boolean z;
        int i = this.tabs;
        genBlockPreamble(zeroOrMoreBlock);
        println("while True:");
        this.tabs++;
        int i2 = this.tabs;
        genBlockInitAction(zeroOrMoreBlock);
        String str = this.currentASTResult;
        if (zeroOrMoreBlock.getLabel() != null) {
            this.currentASTResult = zeroOrMoreBlock.getLabel();
        }
        this.grammar.theLLkAnalyzer.deterministic(zeroOrMoreBlock);
        int i3 = this.grammar.maxk;
        if (!zeroOrMoreBlock.greedy && zeroOrMoreBlock.exitLookaheadDepth <= this.grammar.maxk && zeroOrMoreBlock.exitCache[zeroOrMoreBlock.exitLookaheadDepth].containsEpsilon()) {
            i3 = zeroOrMoreBlock.exitLookaheadDepth;
        } else {
            if (zeroOrMoreBlock.greedy || zeroOrMoreBlock.exitLookaheadDepth != Integer.MAX_VALUE) {
                z = false;
            }
            if (z) {
                if (this.DEBUG_CODE_GENERATOR) {
                    System.out.println(new StringBuffer("nongreedy (...)* loop; exit depth is ").append(zeroOrMoreBlock.exitLookaheadDepth).toString());
                }
                String lookaheadTestExpression = getLookaheadTestExpression(zeroOrMoreBlock.exitCache, i3);
                println("###  nongreedy exit test");
                println(new StringBuffer("if (").append(lookaheadTestExpression).append("):").toString());
                this.tabs++;
                println(PsiKeyword.BREAK);
                this.tabs--;
            }
            int i4 = this.tabs;
            genBlockFinish(genCommonBlock(zeroOrMoreBlock, false), PsiKeyword.BREAK);
            this.tabs = i4;
            this.tabs = i2;
            this.tabs--;
            this.currentASTResult = str;
        }
        z = true;
        if (z) {
            if (this.DEBUG_CODE_GENERATOR) {
                System.out.println(new StringBuffer("nongreedy (...)* loop; exit depth is ").append(zeroOrMoreBlock.exitLookaheadDepth).toString());
            }
            String lookaheadTestExpression2 = getLookaheadTestExpression(zeroOrMoreBlock.exitCache, i3);
            println("###  nongreedy exit test");
            println(new StringBuffer("if (").append(lookaheadTestExpression2).append("):").toString());
            this.tabs++;
            println(PsiKeyword.BREAK);
            this.tabs--;
        }
        int i5 = this.tabs;
        genBlockFinish(genCommonBlock(zeroOrMoreBlock, false), PsiKeyword.BREAK);
        this.tabs = i5;
        this.tabs = i2;
        this.tabs--;
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
            println("try:");
            this.tabs++;
        }
        println("pass");
        for (AlternativeElement alternativeElement = alternative.head; !(alternativeElement instanceof BlockEndElement); alternativeElement = alternativeElement.next) {
            alternativeElement.generate();
        }
        if (this.genAST) {
            if (alternativeBlock instanceof RuleBlock) {
                boolean z4 = this.grammar.hasSyntacticPredicate;
                println(new StringBuffer().append(((RuleBlock) alternativeBlock).getRuleName()).append("_AST = currentAST.root").toString());
                boolean z5 = this.grammar.hasSyntacticPredicate;
            } else if (alternativeBlock.getLabel() != null) {
                this.antlrTool.warning("Labeled subrules not yet supported", this.grammar.getFilename(), alternativeBlock.getLine(), alternativeBlock.getColumn());
            }
        }
        if (alternative.exceptionSpec != null) {
            this.tabs--;
            genErrorHandler(alternative.exceptionSpec);
        }
        this.genAST = z;
        this.saveText = z3;
        this.treeVariableMap = hashtable;
    }

    protected void genBitsets(Vector vector, int i) {
        println("");
        for (int i2 = 0; i2 < vector.size(); i2++) {
            BitSet bitSet = (BitSet) vector.elementAt(i2);
            bitSet.growToInclude(i);
            genBitSet(bitSet, i2);
        }
    }

    private void genBitSet(BitSet bitSet, int i) {
        int i2 = this.tabs;
        int i3 = 0;
        this.tabs = 0;
        println("");
        println("### generate bit set");
        println(new StringBuffer("def mk").append(getBitsetName(i)).append("(): ").toString());
        this.tabs++;
        int iLengthInLongWords = bitSet.lengthInLongWords();
        if (iLengthInLongWords < 8) {
            println("### var1");
            println(new StringBuffer("data = [ ").append(bitSet.toStringOfWords()).append("]").toString());
        } else {
            println(new StringBuffer("data = [0L] * ").append(iLengthInLongWords).append(" ### init list").toString());
            long[] packedArray = bitSet.toPackedArray();
            while (i3 < packedArray.length) {
                long j = packedArray[i3];
                if (j == 0) {
                    i3++;
                } else {
                    int i4 = i3 + 1;
                    if (i4 == packedArray.length || j != packedArray[i4]) {
                        println(new StringBuffer("data[").append(i3).append("] =").append(packedArray[i3]).append("L").toString());
                    } else {
                        while (i4 < packedArray.length && packedArray[i4] == packedArray[i3]) {
                            i4++;
                        }
                        long j2 = packedArray[i3];
                        println(new StringBuffer("for x in xrange(").append(i3).append(", ").append(i4).append("):").toString());
                        this.tabs++;
                        println(new StringBuffer("data[x] = ").append(j2).append("L").toString());
                        this.tabs--;
                    }
                    i3 = i4;
                }
            }
        }
        println("return data");
        this.tabs--;
        println(new StringBuffer().append(getBitsetName(i)).append(" = antlr.BitSet(mk").append(getBitsetName(i)).append("())").toString());
        this.tabs = i2;
    }

    private void genBlockFinish(PythonBlockFinishingInfo pythonBlockFinishingInfo, String str) {
        if (pythonBlockFinishingInfo.needAnErrorClause && (pythonBlockFinishingInfo.generatedAnIf || pythonBlockFinishingInfo.generatedSwitch)) {
            if (pythonBlockFinishingInfo.generatedAnIf) {
                println("else:");
            }
            this.tabs++;
            println(str);
            this.tabs--;
        }
        if (pythonBlockFinishingInfo.postscript != null) {
            println(pythonBlockFinishingInfo.postscript);
        }
    }

    private void genBlockFinish1(PythonBlockFinishingInfo pythonBlockFinishingInfo, String str) {
        if (pythonBlockFinishingInfo.needAnErrorClause && (pythonBlockFinishingInfo.generatedAnIf || pythonBlockFinishingInfo.generatedSwitch)) {
            if (pythonBlockFinishingInfo.generatedAnIf) {
                println("else:");
            }
            this.tabs++;
            println(str);
            this.tabs--;
            boolean z = pythonBlockFinishingInfo.generatedAnIf;
        }
        if (pythonBlockFinishingInfo.postscript != null) {
            println(pythonBlockFinishingInfo.postscript);
        }
    }

    protected void genBlockInitAction(AlternativeBlock alternativeBlock) {
        if (alternativeBlock.initAction != null) {
            printAction(processActionForSpecialSymbols(alternativeBlock.initAction, alternativeBlock.getLine(), this.currentRule, null));
        }
    }

    /* JADX WARN: Code duplicated, block: B:25:0x0068  */
    /* JADX WARN: Code duplicated, block: B:34:0x00aa  */
    /* JADX WARN: Code duplicated, block: B:36:0x00b0  */
    /* JADX WARN: Code duplicated, block: B:39:0x00b9  */
    /* JADX WARN: Code duplicated, block: B:42:0x00d9  */
    /* JADX WARN: Code duplicated, block: B:52:0x00f7 A[SYNTHETIC] */
    protected void genBlockPreamble(AlternativeBlock alternativeBlock) {
        if (alternativeBlock instanceof RuleBlock) {
            RuleBlock ruleBlock = (RuleBlock) alternativeBlock;
            if (ruleBlock.labeledElements != null) {
                for (int i = 0; i < ruleBlock.labeledElements.size(); i++) {
                    AlternativeElement alternativeElement = (AlternativeElement) ruleBlock.labeledElements.elementAt(i);
                    boolean z = alternativeElement instanceof RuleRefElement;
                    if (!z && (!(alternativeElement instanceof AlternativeBlock) || (alternativeElement instanceof RuleBlock) || (alternativeElement instanceof SynPredBlock))) {
                        println(new StringBuffer().append(alternativeElement.getLabel()).append(" = ").append(this.labeledElementInit).toString());
                        if (this.grammar.buildAST) {
                            if (alternativeElement instanceof GrammarAtom) {
                                GrammarAtom grammarAtom = (GrammarAtom) alternativeElement;
                                if (grammarAtom.getASTNodeType() != null) {
                                    genASTDeclaration(alternativeElement, grammarAtom.getASTNodeType());
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
                            println(new StringBuffer().append(alternativeElement.getLabel()).append(" = ").append(this.labeledElementInit).toString());
                            if (this.grammar.buildAST) {
                                genASTDeclaration(alternativeElement);
                            }
                        } else {
                            if (this.grammar.buildAST) {
                                genASTDeclaration(alternativeElement);
                            }
                            if (this.grammar instanceof LexerGrammar) {
                                println(new StringBuffer().append(alternativeElement.getLabel()).append(" = None").toString());
                            }
                            if (this.grammar instanceof TreeWalkerGrammar) {
                                println(new StringBuffer().append(alternativeElement.getLabel()).append(" = ").append(this.labeledElementInit).toString());
                            }
                        }
                    } else {
                        if (this.grammar.buildAST) {
                            genASTDeclaration(alternativeElement);
                        }
                        if (this.grammar instanceof LexerGrammar) {
                            println(new StringBuffer().append(alternativeElement.getLabel()).append(" = None").toString());
                        }
                        if (this.grammar instanceof TreeWalkerGrammar) {
                            println(new StringBuffer().append(alternativeElement.getLabel()).append(" = ").append(this.labeledElementInit).toString());
                        }
                    }
                }
            }
        }
    }

    protected void genCases(BitSet bitSet) {
        if (this.DEBUG_CODE_GENERATOR) {
            System.out.println(new StringBuffer("genCases(").append(bitSet).append(Constant.AFTER_QUTO).toString());
        }
        int[] array = bitSet.toArray();
        boolean z = this.grammar instanceof LexerGrammar;
        print("elif la1 and la1 in ");
        if (this.grammar instanceof LexerGrammar) {
            _print("u'");
            for (int i : array) {
                _print(getValueString(i, false));
            }
            _print("':\n");
            return;
        }
        _print("[");
        int i2 = 0;
        while (i2 < array.length) {
            _print(getValueString(array[i2], false));
            i2++;
            if (i2 < array.length) {
                _print(",");
            }
        }
        _print("]:\n");
    }

    public PythonBlockFinishingInfo genCommonBlock(AlternativeBlock alternativeBlock, boolean z) {
        boolean z2;
        PythonBlockFinishingInfo pythonBlockFinishingInfo;
        boolean zLookaheadIsEmpty;
        String lookaheadTestExpression;
        PythonBlockFinishingInfo pythonBlockFinishingInfo2;
        int i = this.tabs;
        PythonBlockFinishingInfo pythonBlockFinishingInfo3 = new PythonBlockFinishingInfo();
        boolean z3 = this.genAST;
        this.genAST = z3 && alternativeBlock.getAutoGen();
        boolean z4 = this.saveText;
        this.saveText = z4 && alternativeBlock.getAutoGen();
        String str = "";
        if (alternativeBlock.not && this.analyzer.subruleCanBeInverted(alternativeBlock, this.grammar instanceof LexerGrammar)) {
            if (this.DEBUG_CODE_GENERATOR) {
                System.out.println("special case: ~(subrule)");
            }
            Lookahead lookaheadLook = this.analyzer.look(1, alternativeBlock);
            if (alternativeBlock.getLabel() != null && this.syntacticPredLevel == 0) {
                println(new StringBuffer().append(alternativeBlock.getLabel()).append(" = ").append(this.lt1Value).toString());
            }
            genElementAST(alternativeBlock);
            println(new StringBuffer("self.match(").append(this.grammar instanceof TreeWalkerGrammar ? "_t, " : "").append(getBitsetName(markBitsetForGen(lookaheadLook.fset))).append(Constant.AFTER_QUTO).toString());
            if (this.grammar instanceof TreeWalkerGrammar) {
                println("_t = _t.getNextSibling()");
            }
            return pythonBlockFinishingInfo3;
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
                return pythonBlockFinishingInfo3;
            }
        }
        int i2 = 0;
        for (int i3 = 0; i3 < alternativeBlock.getAlternatives().size(); i3++) {
            if (suitableForCaseExpression(alternativeBlock.getAlternativeAt(i3))) {
                i2++;
            }
        }
        if (i2 >= this.makeSwitchThreshold) {
            String strLookaheadString = lookaheadString(1);
            if (this.grammar instanceof TreeWalkerGrammar) {
                println("if not _t:");
                this.tabs++;
                println("_t = antlr.ASTNULL");
                this.tabs--;
            }
            println(new StringBuffer("la1 = ").append(strLookaheadString).toString());
            println("if False:");
            this.tabs++;
            println("pass");
            this.tabs--;
            for (int i4 = 0; i4 < alternativeBlock.alternatives.size(); i4++) {
                Alternative alternativeAt2 = alternativeBlock.getAlternativeAt(i4);
                if (suitableForCaseExpression(alternativeAt2)) {
                    Lookahead lookahead = alternativeAt2.cache[1];
                    if (lookahead.fset.degree() == 0 && !lookahead.containsEpsilon()) {
                        this.antlrTool.warning("Alternate omitted due to empty prediction set", this.grammar.getFilename(), alternativeAt2.head.getLine(), alternativeAt2.head.getColumn());
                    } else {
                        genCases(lookahead.fset);
                        this.tabs++;
                        genAlt(alternativeAt2, alternativeBlock);
                        this.tabs--;
                    }
                }
            }
            println("else:");
            this.tabs++;
            z2 = true;
        } else {
            z2 = false;
        }
        int i5 = 0;
        for (int i6 = this.grammar instanceof LexerGrammar ? this.grammar.maxk : 0; i6 >= 0; i6--) {
            int i7 = 0;
            while (i7 < alternativeBlock.alternatives.size()) {
                Alternative alternativeAt3 = alternativeBlock.getAlternativeAt(i7);
                if (this.DEBUG_CODE_GENERATOR) {
                    System.out.println(new StringBuffer("genAlt: ").append(i7).toString());
                }
                if (z2 && suitableForCaseExpression(alternativeAt3)) {
                    if (this.DEBUG_CODE_GENERATOR) {
                        System.out.println("ignoring alt because it was in the switch");
                    }
                } else {
                    if (this.grammar instanceof LexerGrammar) {
                        int i8 = alternativeAt3.lookaheadDepth;
                        if (i8 == Integer.MAX_VALUE) {
                            i8 = this.grammar.maxk;
                        }
                        while (i8 >= 1 && alternativeAt3.cache[i8].containsEpsilon()) {
                            i8--;
                        }
                        if (i8 != i6) {
                            if (this.DEBUG_CODE_GENERATOR) {
                                System.out.println(new StringBuffer("ignoring alt because effectiveDepth!=altDepth").append(i8).append("!=").append(i6).toString());
                            }
                        } else {
                            zLookaheadIsEmpty = lookaheadIsEmpty(alternativeAt3, i8);
                            lookaheadTestExpression = getLookaheadTestExpression(alternativeAt3, i8);
                        }
                    } else {
                        zLookaheadIsEmpty = lookaheadIsEmpty(alternativeAt3, this.grammar.maxk);
                        lookaheadTestExpression = getLookaheadTestExpression(alternativeAt3, this.grammar.maxk);
                    }
                    if (alternativeAt3.cache[1].fset.degree() <= 127 || !suitableForCaseExpression(alternativeAt3)) {
                        if (zLookaheadIsEmpty && alternativeAt3.semPred == null && alternativeAt3.synPred == null) {
                            if (i5 == 0) {
                                println("##<m3> <closing");
                            } else {
                                println("else: ## <m4>");
                                this.tabs++;
                            }
                            pythonBlockFinishingInfo3.needAnErrorClause = false;
                        } else {
                            if (alternativeAt3.semPred != null) {
                                pythonBlockFinishingInfo2 = pythonBlockFinishingInfo3;
                                String strProcessActionForSpecialSymbols = processActionForSpecialSymbols(alternativeAt3.semPred, alternativeBlock.line, this.currentRule, new ActionTransInfo());
                                if (((this.grammar instanceof ParserGrammar) || (this.grammar instanceof LexerGrammar)) && this.grammar.debuggingOutput) {
                                    lookaheadTestExpression = new StringBuffer("(").append(lookaheadTestExpression).append(" and fireSemanticPredicateEvaluated(antlr.debug.SemanticPredicateEvent.PREDICTING, ").append(addSemPred(this.charFormatter.escapeString(strProcessActionForSpecialSymbols))).append(", ").append(strProcessActionForSpecialSymbols).append("))").toString();
                                } else {
                                    lookaheadTestExpression = new StringBuffer("(").append(lookaheadTestExpression).append(" and (").append(strProcessActionForSpecialSymbols).append("))").toString();
                                }
                            } else {
                                pythonBlockFinishingInfo2 = pythonBlockFinishingInfo3;
                            }
                            if (i5 > 0) {
                                if (alternativeAt3.synPred != null) {
                                    println("else:");
                                    this.tabs++;
                                    genSynPred(alternativeAt3.synPred, lookaheadTestExpression);
                                } else {
                                    println(new StringBuffer("elif ").append(lookaheadTestExpression).append(":").toString());
                                }
                            } else if (alternativeAt3.synPred != null) {
                                genSynPred(alternativeAt3.synPred, lookaheadTestExpression);
                            } else {
                                if (this.grammar instanceof TreeWalkerGrammar) {
                                    println("if not _t:");
                                    this.tabs++;
                                    println("_t = antlr.ASTNULL");
                                    this.tabs--;
                                }
                                println(new StringBuffer("if ").append(lookaheadTestExpression).append(":").toString());
                            }
                        }
                        i5++;
                        this.tabs++;
                        genAlt(alternativeAt3, alternativeBlock);
                        this.tabs--;
                        i7++;
                        z2 = z2;
                        str = str;
                        z4 = z4;
                        pythonBlockFinishingInfo3 = pythonBlockFinishingInfo2;
                    } else if (i5 == 0) {
                        println(new StringBuffer("<m1> if ").append(lookaheadTestExpression).append(":").toString());
                    } else {
                        println(new StringBuffer("<m2> elif ").append(lookaheadTestExpression).append(":").toString());
                    }
                    pythonBlockFinishingInfo2 = pythonBlockFinishingInfo3;
                    i5++;
                    this.tabs++;
                    genAlt(alternativeAt3, alternativeBlock);
                    this.tabs--;
                    i7++;
                    z2 = z2;
                    str = str;
                    z4 = z4;
                    pythonBlockFinishingInfo3 = pythonBlockFinishingInfo2;
                }
                pythonBlockFinishingInfo2 = pythonBlockFinishingInfo3;
                i7++;
                z2 = z2;
                str = str;
                z4 = z4;
                pythonBlockFinishingInfo3 = pythonBlockFinishingInfo2;
            }
        }
        PythonBlockFinishingInfo pythonBlockFinishingInfo4 = pythonBlockFinishingInfo3;
        boolean z5 = z2;
        String str2 = str;
        this.genAST = z3;
        this.saveText = z4;
        if (z5) {
            pythonBlockFinishingInfo = pythonBlockFinishingInfo4;
            pythonBlockFinishingInfo.postscript = str2;
            pythonBlockFinishingInfo.generatedSwitch = true;
            pythonBlockFinishingInfo.generatedAnIf = i5 > 0;
        } else {
            pythonBlockFinishingInfo = pythonBlockFinishingInfo4;
            pythonBlockFinishingInfo.postscript = str2;
            pythonBlockFinishingInfo.generatedSwitch = false;
            pythonBlockFinishingInfo.generatedAnIf = i5 > 0;
        }
        return pythonBlockFinishingInfo;
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
                println(new StringBuffer().append(string).append("_in = ").append(str).toString());
                return;
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
                println(new StringBuffer().append(string3).append("_in = None").toString());
            }
            if (alternativeElement.getLabel() != null) {
                if (alternativeElement instanceof GrammarAtom) {
                    println(new StringBuffer().append(string3).append(" = ").append(getASTCreateString((GrammarAtom) alternativeElement, label2)).append("").toString());
                } else {
                    println(new StringBuffer().append(string3).append(" = ").append(getASTCreateString(label2)).append("").toString());
                }
            }
            if (alternativeElement.getLabel() == null && z) {
                String str3 = this.lt1Value;
                if (alternativeElement instanceof GrammarAtom) {
                    println(new StringBuffer().append(string3).append(" = ").append(getASTCreateString((GrammarAtom) alternativeElement, str3)).append("").toString());
                } else {
                    println(new StringBuffer().append(string3).append(" = ").append(getASTCreateString(str3)).append("").toString());
                }
                if (this.grammar instanceof TreeWalkerGrammar) {
                    println(new StringBuffer().append(string3).append("_in = ").append(str3).append("").toString());
                }
            }
            if (this.genAST) {
                int autoGenType = alternativeElement.getAutoGenType();
                if (autoGenType == 1) {
                    println(new StringBuffer("self.addASTChild(currentAST, ").append(string3).append(Constant.AFTER_QUTO).toString());
                } else {
                    if (autoGenType != 2) {
                        return;
                    }
                    println(new StringBuffer("self.makeASTRoot(currentAST, ").append(string3).append(Constant.AFTER_QUTO).toString());
                }
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
            genErrorHandler(exceptionSpecFindExceptionSpec);
        }
    }

    private void genErrorHandler(ExceptionSpec exceptionSpec) {
        String strSubstring;
        String strSubstring2;
        for (int i = 0; i < exceptionSpec.handlers.size(); i++) {
            ExceptionHandler exceptionHandler = (ExceptionHandler) exceptionSpec.handlers.elementAt(i);
            String strTrim = removeAssignmentFromDeclaration(exceptionHandler.exceptionTypeAndName.getText()).trim();
            int length = strTrim.length() - 1;
            while (true) {
                if (length < 0) {
                    strSubstring = "";
                    strSubstring2 = "";
                    break;
                } else {
                    if (!Character.isLetterOrDigit(strTrim.charAt(length)) && strTrim.charAt(length) != '_') {
                        strSubstring = strTrim.substring(0, length);
                        strSubstring2 = strTrim.substring(length + 1);
                        break;
                    }
                    length--;
                }
            }
            println(new StringBuffer("except ").append(strSubstring).append(", ").append(strSubstring2).append(":").toString());
            this.tabs++;
            if (this.grammar.hasSyntacticPredicate) {
                println("if not self.inputState.guessing:");
                this.tabs++;
            }
            printAction(processActionForSpecialSymbols(exceptionHandler.action.getText(), exceptionHandler.action.getLine(), this.currentRule, new ActionTransInfo()));
            if (this.grammar.hasSyntacticPredicate) {
                this.tabs--;
                println("else:");
                this.tabs++;
                println(new StringBuffer("raise ").append(strSubstring2).toString());
                this.tabs--;
            }
            this.tabs--;
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
            println("try: # for error handling");
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
        println(new StringBuffer().append(str).append("_AST = None").toString());
        this.declaredASTVariables.put(alternativeElement, alternativeElement);
    }

    protected void genHeader() {
        println(new StringBuffer("### $ANTLR ").append(Tool.version).append(": \"").append(this.antlrTool.fileMinusPath(this.antlrTool.grammarFile)).append("\" -> \"").append(this.grammar.getClassName()).append(".py\"$").toString());
    }

    protected void genLexerTest() {
        String className = this.grammar.getClassName();
        println("if __name__ == '__main__' :");
        this.tabs++;
        println("import sys");
        println("import antlr");
        println(new StringBuffer("import ").append(className).toString());
        println("");
        println("### create lexer - shall read from stdin");
        println("try:");
        this.tabs++;
        println(new StringBuffer("for token in ").append(className).append(".Lexer():").toString());
        this.tabs++;
        println("print token");
        println("");
        this.tabs--;
        this.tabs--;
        println("except antlr.TokenStreamException, e:");
        this.tabs++;
        println("print \"error: exception caught while lexing: \", e");
        this.tabs--;
        this.tabs--;
    }

    private void genLiteralsTest() {
        println("### option { testLiterals=true } ");
        println("_ttype = self.testLiteralsTable(_ttype)");
    }

    private void genLiteralsTestForPartialToken() {
        println("_ttype = self.testLiteralsTable(self.text.getString(), _begin, self.text.length()-_begin, _ttype)");
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
        String str;
        if (!(this.grammar instanceof TreeWalkerGrammar)) {
            str = "";
        } else {
            str = "_t,";
        }
        if ((this.grammar instanceof LexerGrammar) && (!this.saveText || grammarAtom.getAutoGenType() == 3)) {
            println("_saveIndex = self.text.length()");
        }
        print(grammarAtom.not ? "self.matchNot(" : "self.match(");
        _print(str);
        if (grammarAtom.atomText.equals("EOF")) {
            _print("EOF_TYPE");
        } else {
            _print(grammarAtom.atomText);
        }
        _println(Constant.AFTER_QUTO);
        if (this.grammar instanceof LexerGrammar) {
            if (!this.saveText || grammarAtom.getAutoGenType() == 3) {
                println("self.text.setLength(_saveIndex)");
            }
        }
    }

    protected void genMatchUsingAtomTokenType(GrammarAtom grammarAtom) {
        String str;
        if (!(this.grammar instanceof TreeWalkerGrammar)) {
            str = "";
        } else {
            str = "_t,";
        }
        println(new StringBuffer().append(grammarAtom.not ? "self.matchNot(" : "self.match(").append(new StringBuffer().append(str).append(getValueString(grammarAtom.getType(), true)).toString()).append(Constant.AFTER_QUTO).toString());
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
                println("def nextToken(self):");
                this.tabs++;
                println("while True:");
                this.tabs++;
                println("try: ### try again ..");
                this.tabs++;
                println("while True:");
                this.tabs++;
                int i2 = this.tabs;
                println("_token = None");
                println("_ttype = INVALID_TYPE");
                if (((LexerGrammar) this.grammar).filterMode) {
                    println("self.setCommitToPath(False)");
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
                        println("_m = self.mark()");
                    }
                }
                println("self.resetText()");
                println("try: ## for char stream error handling");
                this.tabs++;
                int i3 = this.tabs;
                println("try: ##for lexical error handling");
                this.tabs++;
                int i4 = this.tabs;
                for (int i5 = 0; i5 < ruleBlockCreateNextTokenRule.getAlternatives().size(); i5++) {
                    Alternative alternativeAt = ruleBlockCreateNextTokenRule.getAlternativeAt(i5);
                    if (alternativeAt.cache[1].containsEpsilon()) {
                        this.antlrTool.warning(new StringBuffer("public lexical rule ").append(CodeGenerator.decodeLexerRuleName(((RuleRefElement) alternativeAt.head).targetRule)).append(" is optional (can match \"nothing\")").toString());
                    }
                }
                System.getProperty(SystemProperties.LINE_SEPARATOR);
                PythonBlockFinishingInfo pythonBlockFinishingInfoGenCommonBlock = genCommonBlock(ruleBlockCreateNextTokenRule, false);
                if (!((LexerGrammar) this.grammar).filterMode) {
                    string = "self.default(self.LA(1))";
                } else if (str == null) {
                    string = "self.filterdefault(self.LA(1))";
                } else {
                    string = new StringBuffer("self.filterdefault(self.LA(1), self.m").append(str).append(", False)").toString();
                }
                genBlockFinish1(pythonBlockFinishingInfoGenCommonBlock, string);
                this.tabs = i4;
                if (((LexerGrammar) this.grammar).filterMode && str != null) {
                    println("self.commit()");
                }
                println("if not self._returnToken:");
                this.tabs++;
                println("raise antlr.TryAgain ### found SKIP token");
                this.tabs--;
                if (((LexerGrammar) this.grammar).getTestLiterals()) {
                    println("### option { testLiterals=true } ");
                    println("self.testForLiteral(self._returnToken)");
                }
                println("### return token to caller");
                println("return self._returnToken");
                this.tabs--;
                println("### handle lexical errors ....");
                println("except antlr.RecognitionException, e:");
                this.tabs++;
                if (((LexerGrammar) this.grammar).filterMode) {
                    if (str == null) {
                        println("if not self.getCommitToPath():");
                        this.tabs++;
                        println("self.consume()");
                        println("raise antlr.TryAgain()");
                        this.tabs--;
                    } else {
                        println("if not self.getCommitToPath(): ");
                        this.tabs++;
                        println("self.rewind(_m)");
                        println("self.resetText()");
                        println("try:");
                        this.tabs++;
                        println(new StringBuffer("self.m").append(str).append("(False)").toString());
                        this.tabs--;
                        println("except antlr.RecognitionException, ee:");
                        this.tabs++;
                        println("### horrendous failure: error in filter rule");
                        println("self.reportError(ee)");
                        println("self.consume()");
                        this.tabs--;
                        println("raise antlr.TryAgain()");
                        this.tabs--;
                    }
                }
                if (ruleBlockCreateNextTokenRule.getDefaultErrorHandler()) {
                    println("self.reportError(e)");
                    println("self.consume()");
                } else {
                    println("raise antlr.TokenStreamRecognitionException(e)");
                }
                this.tabs--;
                this.tabs--;
                println("### handle char stream errors ...");
                println("except antlr.CharStreamException,cse:");
                this.tabs++;
                println("if isinstance(cse, antlr.CharStreamIOException):");
                this.tabs++;
                println("raise antlr.TokenStreamIOException(cse.io)");
                this.tabs--;
                println("else:");
                this.tabs++;
                println("raise antlr.TokenStreamException(str(cse))");
                this.tabs--;
                this.tabs--;
                this.tabs--;
                this.tabs--;
                println("except antlr.TryAgain:");
                this.tabs++;
                println("pass");
                this.tabs--;
                this.tabs--;
                return;
            }
        }
        println("");
        println("def nextToken(self): ");
        this.tabs++;
        println("try:");
        this.tabs++;
        println("self.uponEOF()");
        this.tabs--;
        println("except antlr.CharStreamIOException, csioe:");
        this.tabs++;
        println("raise antlr.TokenStreamIOException(csioe.io)");
        this.tabs--;
        println("except antlr.CharStreamException, cse:");
        this.tabs++;
        println("raise antlr.TokenStreamException(str(cse))");
        this.tabs--;
        println("return antlr.CommonToken(type=EOF_TYPE, text=\"\")");
        this.tabs--;
    }

    public void genRule(RuleSymbol ruleSymbol, boolean z, int i) {
        int i2;
        int i3;
        this.tabs = 1;
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
        genJavadocComment(ruleSymbol);
        print(new StringBuffer("def ").append(ruleSymbol.getId()).append("(").toString());
        _print(this.commonExtraParams);
        if (this.commonExtraParams.length() != 0 && block.argAction != null) {
            _print(",");
        }
        if (block.argAction != null) {
            _println("");
            this.tabs++;
            println(block.argAction);
            this.tabs--;
            print("):");
        } else {
            _print("):");
        }
        println("");
        this.tabs++;
        if (block.returnAction != null) {
            if (block.returnAction.indexOf(61) >= 0) {
                println(block.returnAction);
            } else {
                println(new StringBuffer().append(extractIdOfAction(block.returnAction, block.getLine(), block.getColumn())).append(" = None").toString());
            }
        }
        println(this.commonLocalVars);
        if (this.grammar.traceRules) {
            if (this.grammar instanceof TreeWalkerGrammar) {
                println(new StringBuffer("self.traceIn(\"").append(ruleSymbol.getId()).append("\",_t)").toString());
            } else {
                println(new StringBuffer("self.traceIn(\"").append(ruleSymbol.getId()).append("\")").toString());
            }
        }
        if (this.grammar instanceof LexerGrammar) {
            if (ruleSymbol.getId().equals("mEOF")) {
                println("_ttype = EOF_TYPE");
            } else {
                println(new StringBuffer("_ttype = ").append(ruleSymbol.getId().substring(1)).toString());
            }
            println("_saveIndex = 0");
        }
        if (this.grammar.debuggingOutput) {
            if (this.grammar instanceof ParserGrammar) {
                println(new StringBuffer("self.fireEnterRule(").append(i).append(", 0)").toString());
            } else if (this.grammar instanceof LexerGrammar) {
                println(new StringBuffer("self.fireEnterRule(").append(i).append(", _ttype)").toString());
            }
        }
        if (this.grammar.debuggingOutput || this.grammar.traceRules) {
            println("try: ### debugging");
            this.tabs++;
        }
        if (this.grammar instanceof TreeWalkerGrammar) {
            println(new StringBuffer().append(ruleSymbol.getId()).append("_AST_in = None").toString());
            println("if _t != antlr.ASTNULL:");
            this.tabs++;
            println(new StringBuffer().append(ruleSymbol.getId()).append("_AST_in = _t").toString());
            this.tabs--;
        }
        if (this.grammar.buildAST) {
            println("self.returnAST = None");
            println("currentAST = antlr.ASTPair()");
            println(new StringBuffer().append(ruleSymbol.getId()).append("_AST = None").toString());
        }
        genBlockPreamble(block);
        genBlockInitAction(block);
        ExceptionSpec exceptionSpecFindExceptionSpec = block.findExceptionSpec("");
        if (exceptionSpecFindExceptionSpec != null || block.getDefaultErrorHandler()) {
            println("try:      ## for error handling");
            this.tabs++;
        }
        int i4 = this.tabs;
        if (block.alternatives.size() == 1) {
            Alternative alternativeAt = block.getAlternativeAt(0);
            String str = alternativeAt.semPred;
            if (str != null) {
                genSemPred(str, this.currentRule.line);
            }
            if (alternativeAt.synPred != null) {
                this.antlrTool.warning("Syntactic predicate ignored for single alternative", this.grammar.getFilename(), alternativeAt.synPred.getLine(), alternativeAt.synPred.getColumn());
            }
            genAlt(alternativeAt, block);
        } else {
            this.grammar.theLLkAnalyzer.deterministic(block);
            genBlockFinish(genCommonBlock(block, false), this.throwNoViable);
        }
        this.tabs = i4;
        if (exceptionSpecFindExceptionSpec != null || block.getDefaultErrorHandler()) {
            this.tabs--;
            println("");
        }
        if (exceptionSpecFindExceptionSpec != null) {
            genErrorHandler(exceptionSpecFindExceptionSpec);
        } else if (block.getDefaultErrorHandler()) {
            println(new StringBuffer("except ").append(this.exceptionThrown).append(", ex:").toString());
            this.tabs++;
            if (this.grammar.hasSyntacticPredicate) {
                println("if not self.inputState.guessing:");
                this.tabs++;
            }
            println("self.reportError(ex)");
            if (!(this.grammar instanceof TreeWalkerGrammar)) {
                String bitsetName = getBitsetName(markBitsetForGen(this.grammar.theLLkAnalyzer.FOLLOW(1, block.endNode).fset));
                println("self.consume()");
                println(new StringBuffer("self.consumeUntil(").append(bitsetName).append(Constant.AFTER_QUTO).toString());
                i2 = 1;
            } else {
                println("if _t:");
                i2 = 1;
                this.tabs++;
                println("_t = _t.getNextSibling()");
                this.tabs--;
            }
            if (this.grammar.hasSyntacticPredicate) {
                this.tabs -= i2;
                println("else:");
                this.tabs += i2;
                println("raise ex");
                this.tabs -= i2;
            }
            this.tabs -= i2;
            println("");
        }
        if (this.grammar.buildAST) {
            println(new StringBuffer("self.returnAST = ").append(ruleSymbol.getId()).append("_AST").toString());
        }
        if (this.grammar instanceof TreeWalkerGrammar) {
            println("self._retTree = _t");
        }
        if (block.getTestLiterals()) {
            if (ruleSymbol.access.equals("protected")) {
                genLiteralsTestForPartialToken();
            } else {
                genLiteralsTest();
            }
        }
        if (this.grammar instanceof LexerGrammar) {
            println("self.set_return_token(_createToken, _token, _ttype, _begin)");
        }
        if (block.returnAction != null) {
            println(new StringBuffer("return ").append(extractIdOfAction(block.returnAction, block.getLine(), block.getColumn())).append("").toString());
        }
        if (this.grammar.debuggingOutput || this.grammar.traceRules) {
            this.tabs--;
            println("finally:  ### debugging");
            this.tabs++;
            if (this.grammar.debuggingOutput) {
                if (this.grammar instanceof ParserGrammar) {
                    println(new StringBuffer("self.fireExitRule(").append(i).append(", 0)").toString());
                } else if (this.grammar instanceof LexerGrammar) {
                    println(new StringBuffer("self.fireExitRule(").append(i).append(", _ttype)").toString());
                }
            }
            if (this.grammar.traceRules) {
                if (this.grammar instanceof TreeWalkerGrammar) {
                    println(new StringBuffer("self.traceOut(\"").append(ruleSymbol.getId()).append("\", _t)").toString());
                } else {
                    println(new StringBuffer("self.traceOut(\"").append(ruleSymbol.getId()).append("\")").toString());
                }
            }
            i3 = 1;
            this.tabs--;
        } else {
            i3 = 1;
        }
        this.tabs -= i3;
        println("");
        this.genAST = z2;
    }

    private void GenRuleInvocation(RuleRefElement ruleRefElement) {
        _print(new StringBuffer("self.").append(ruleRefElement.targetRule).append("(").toString());
        if (this.grammar instanceof LexerGrammar) {
            if (ruleRefElement.getLabel() != null) {
                _print("True");
            } else {
                _print("False");
            }
            if (this.commonExtraArgs.length() != 0 || ruleRefElement.args != null) {
                _print(", ");
            }
        }
        _print(this.commonExtraArgs);
        if (this.commonExtraArgs.length() != 0 && ruleRefElement.args != null) {
            _print(", ");
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
        _println(Constant.AFTER_QUTO);
        if (this.grammar instanceof TreeWalkerGrammar) {
            println("_t = self._retTree");
        }
    }

    protected void genSemPred(String str, int i) {
        String strProcessActionForSpecialSymbols = processActionForSpecialSymbols(str, i, this.currentRule, new ActionTransInfo());
        String strEscapeString = this.charFormatter.escapeString(strProcessActionForSpecialSymbols);
        if (this.grammar.debuggingOutput && ((this.grammar instanceof ParserGrammar) || (this.grammar instanceof LexerGrammar))) {
            strProcessActionForSpecialSymbols = new StringBuffer("fireSemanticPredicateEvaluated(antlr.debug.SemanticPredicateEvent.VALIDATING,").append(addSemPred(strEscapeString)).append(", ").append(strProcessActionForSpecialSymbols).append(Constant.AFTER_QUTO).toString();
        }
        println(new StringBuffer("if not ").append(strProcessActionForSpecialSymbols).append(":").toString());
        this.tabs++;
        println(new StringBuffer("raise antlr.SemanticException(\"").append(strEscapeString).append("\")").toString());
        this.tabs--;
    }

    protected void genSemPredMap() {
        Enumeration enumerationElements = this.semPreds.elements();
        println("_semPredNames = [");
        this.tabs++;
        while (enumerationElements.hasMoreElements()) {
            println(new StringBuffer("\"").append(enumerationElements.nextElement()).append("\",").toString());
        }
        this.tabs--;
        println("]");
    }

    protected void genSynPred(SynPredBlock synPredBlock, String str) {
        if (this.DEBUG_CODE_GENERATOR) {
            System.out.println(new StringBuffer("gen=>(").append(synPredBlock).append(Constant.AFTER_QUTO).toString());
        }
        println(new StringBuffer("synPredMatched").append(synPredBlock.ID).append(" = False").toString());
        println(new StringBuffer("if ").append(str).append(":").toString());
        this.tabs++;
        if (this.grammar instanceof TreeWalkerGrammar) {
            println(new StringBuffer("_t").append(synPredBlock.ID).append(" = _t").toString());
        } else {
            println(new StringBuffer("_m").append(synPredBlock.ID).append(" = self.mark()").toString());
        }
        println(new StringBuffer("synPredMatched").append(synPredBlock.ID).append(" = True").toString());
        println("self.inputState.guessing += 1");
        if (this.grammar.debuggingOutput && ((this.grammar instanceof ParserGrammar) || (this.grammar instanceof LexerGrammar))) {
            println("self.fireSyntacticPredicateStarted()");
        }
        this.syntacticPredLevel++;
        println("try:");
        this.tabs++;
        gen(synPredBlock);
        this.tabs--;
        println(new StringBuffer("except ").append(this.exceptionThrown).append(", pe:").toString());
        this.tabs++;
        println(new StringBuffer("synPredMatched").append(synPredBlock.ID).append(" = False").toString());
        this.tabs--;
        if (this.grammar instanceof TreeWalkerGrammar) {
            println(new StringBuffer("_t = _t").append(synPredBlock.ID).append("").toString());
        } else {
            println(new StringBuffer("self.rewind(_m").append(synPredBlock.ID).append(Constant.AFTER_QUTO).toString());
        }
        println("self.inputState.guessing -= 1");
        if (this.grammar.debuggingOutput && ((this.grammar instanceof ParserGrammar) || (this.grammar instanceof LexerGrammar))) {
            println(new StringBuffer("if synPredMatched").append(synPredBlock.ID).append(":").toString());
            this.tabs++;
            println("self.fireSyntacticPredicateSucceeded()");
            this.tabs--;
            println("else:");
            this.tabs++;
            println("self.fireSyntacticPredicateFailed()");
            this.tabs--;
        }
        this.syntacticPredLevel--;
        this.tabs--;
        println(new StringBuffer("if synPredMatched").append(synPredBlock.ID).append(":").toString());
    }

    public void genTokenStrings() {
        TokenSymbol tokenSymbol;
        int i = this.tabs;
        this.tabs = 0;
        println("");
        println("_tokenNames = [");
        this.tabs++;
        Vector vocabulary = this.grammar.tokenManager.getVocabulary();
        for (int i2 = 0; i2 < vocabulary.size(); i2++) {
            String strStripFrontBack = (String) vocabulary.elementAt(i2);
            if (strStripFrontBack == null) {
                strStripFrontBack = new StringBuffer("<").append(String.valueOf(i2)).append(">").toString();
            }
            if (!strStripFrontBack.startsWith("\"") && !strStripFrontBack.startsWith("<") && (tokenSymbol = this.grammar.tokenManager.getTokenSymbol(strStripFrontBack)) != null && tokenSymbol.getParaphrase() != null) {
                strStripFrontBack = StringUtils.stripFrontBack(tokenSymbol.getParaphrase(), "\"", "\"");
            }
            print(this.charFormatter.literalString(strStripFrontBack));
            if (i2 != vocabulary.size() - 1) {
                _print(", ");
            }
            _println("");
        }
        this.tabs--;
        println("]");
        this.tabs = i;
    }

    protected void genTokenASTNodeMap() {
        TokenSymbol tokenSymbol;
        println("");
        println("def buildTokenTypeASTClassMap(self):");
        this.tabs++;
        Vector vocabulary = this.grammar.tokenManager.getVocabulary();
        int i = 0;
        boolean z = false;
        for (int i2 = 0; i2 < vocabulary.size(); i2++) {
            String str = (String) vocabulary.elementAt(i2);
            if (str != null && (tokenSymbol = this.grammar.tokenManager.getTokenSymbol(str)) != null && tokenSymbol.getASTNodeType() != null) {
                i++;
                if (!z) {
                    println("self.tokenTypeToASTClassMap = {}");
                    z = true;
                }
                println(new StringBuffer("self.tokenTypeToASTClassMap[").append(tokenSymbol.getTokenType()).append("] = ").append(tokenSymbol.getASTNodeType()).toString());
            }
        }
        if (i == 0) {
            println("self.tokenTypeToASTClassMap = None");
        }
        this.tabs--;
    }

    protected void genTokenTypes(TokenManager tokenManager) throws IOException {
        this.tabs = 0;
        Vector vocabulary = tokenManager.getVocabulary();
        println("SKIP                = antlr.SKIP");
        println("INVALID_TYPE        = antlr.INVALID_TYPE");
        println("EOF_TYPE            = antlr.EOF_TYPE");
        println("EOF                 = antlr.EOF");
        println("NULL_TREE_LOOKAHEAD = antlr.NULL_TREE_LOOKAHEAD");
        println("MIN_USER_TYPE       = antlr.MIN_USER_TYPE");
        for (int i = 4; i < vocabulary.size(); i++) {
            String str = (String) vocabulary.elementAt(i);
            if (str != null) {
                if (str.startsWith("\"")) {
                    StringLiteralSymbol stringLiteralSymbol = (StringLiteralSymbol) tokenManager.getTokenSymbol(str);
                    if (stringLiteralSymbol == null) {
                        this.antlrTool.panic(new StringBuffer("String literal ").append(str).append(" not in symbol table").toString());
                    }
                    if (stringLiteralSymbol.label != null) {
                        println(new StringBuffer().append(stringLiteralSymbol.label).append(" = ").append(i).toString());
                    } else {
                        String strMangleLiteral = mangleLiteral(str);
                        if (strMangleLiteral != null) {
                            println(new StringBuffer().append(strMangleLiteral).append(" = ").append(i).toString());
                            stringLiteralSymbol.label = strMangleLiteral;
                        } else {
                            println(new StringBuffer("### ").append(str).append(" = ").append(i).toString());
                        }
                    }
                } else if (!str.startsWith("<")) {
                    println(new StringBuffer().append(str).append(" = ").append(i).toString());
                }
            }
        }
        this.tabs--;
        exitIfError();
    }

    @Override // antlr.CodeGenerator
    public String getASTCreateString(Vector vector) {
        if (vector.size() == 0) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer("antlr.make(");
        int i = 0;
        while (i < vector.size()) {
            stringBuffer.append(vector.elementAt(i));
            i++;
            if (i < vector.size()) {
                stringBuffer.append(", ");
            }
        }
        stringBuffer.append(Constant.AFTER_QUTO);
        return stringBuffer.toString();
    }

    @Override // antlr.CodeGenerator
    public String getASTCreateString(GrammarAtom grammarAtom, String str) {
        if (grammarAtom != null && grammarAtom.getASTNodeType() != null) {
            return new StringBuffer("self.astFactory.create(").append(str).append(", ").append(grammarAtom.getASTNodeType()).append(Constant.AFTER_QUTO).toString();
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
            return new StringBuffer("self.astFactory.create(").append(str).append(Constant.AFTER_QUTO).toString();
        }
        int iIndexOf = str.indexOf(44);
        str.lastIndexOf(44);
        TokenSymbol tokenSymbol = this.grammar.tokenManager.getTokenSymbol(i > 0 ? str.substring(0, iIndexOf) : str);
        if (tokenSymbol != null) {
            String aSTNodeType = tokenSymbol.getASTNodeType();
            if (i == 0) {
                str2 = ", \"\"";
            }
            if (aSTNodeType != null) {
                return new StringBuffer("self.astFactory.create(").append(str).append(str2).append(", ").append(aSTNodeType).append(Constant.AFTER_QUTO).toString();
            }
        }
        return this.labeledElementASTType.equals("AST") ? new StringBuffer("self.astFactory.create(").append(str).append(Constant.AFTER_QUTO).toString() : new StringBuffer("self.astFactory.create(").append(str).append(Constant.AFTER_QUTO).toString();
    }

    protected String getLookaheadTestExpression(Lookahead[] lookaheadArr, int i) {
        StringBuffer stringBuffer = new StringBuffer(100);
        stringBuffer.append("(");
        int i2 = 1;
        boolean z = true;
        while (i2 <= i) {
            BitSet bitSet = lookaheadArr[i2].fset;
            if (!z) {
                stringBuffer.append(") and (");
            }
            if (lookaheadArr[i2].containsEpsilon()) {
                stringBuffer.append("True");
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
            return "True";
        }
        return getLookaheadTestExpression(alternative.cache, i2);
    }

    protected String getLookaheadTestTerm(int i, BitSet bitSet) {
        String strLookaheadString = lookaheadString(i);
        int[] array = bitSet.toArray();
        if (elementsAreRange(array)) {
            return getRangeExpression(i, array);
        }
        int iDegree = bitSet.degree();
        if (iDegree == 0) {
            return "True";
        }
        if (iDegree >= this.bitsetTestThreshold) {
            return new StringBuffer().append(getBitsetName(markBitsetForGen(bitSet))).append(".member(").append(strLookaheadString).append(Constant.AFTER_QUTO).toString();
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (int i2 = 0; i2 < array.length; i2++) {
            String valueString = getValueString(array[i2], true);
            if (i2 > 0) {
                stringBuffer.append(" or ");
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
        return new StringBuffer("(").append(lookaheadString(i)).append(" >= ").append(getValueString(iArr[0], true)).append(" and ").append(lookaheadString(i)).append(" <= ").append(getValueString(iArr[iArr.length - 1], true)).append(Constant.AFTER_QUTO).toString();
    }

    private String getValueString(int i, boolean z) {
        if (this.grammar instanceof LexerGrammar) {
            String strLiteralChar = this.charFormatter.literalChar(i);
            return z ? new StringBuffer("u'").append(strLiteralChar).append(FormatHelper.QUOTE).toString() : strLiteralChar;
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
        if (label != null) {
            return label;
        }
        String strMangleLiteral = mangleLiteral(id2);
        return strMangleLiteral == null ? String.valueOf(i) : strMangleLiteral;
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
        return new StringBuffer("self.LA(").append(i).append(Constant.AFTER_QUTO).toString();
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
        if (isEmpty(str)) {
            return "";
        }
        if (this.grammar == null) {
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

    static boolean isEmpty(String str) {
        boolean z = true;
        for (int i = 0; z && i < str.length(); i++) {
            char cCharAt = str.charAt(i);
            if (cCharAt != '\t' && cCharAt != '\n' && cCharAt != '\f' && cCharAt != '\r' && cCharAt != ' ') {
                z = false;
            }
        }
        return z;
    }

    protected String processActionCode(String str, int i) {
        if (str == null || isEmpty(str)) {
            return "";
        }
        CodeLexer codeLexer = new CodeLexer(str, this.grammar.getFilename(), i, this.antlrTool);
        try {
            codeLexer.mACTION(true);
            return codeLexer.getTokenObject().getText();
        } catch (CharStreamException unused) {
            this.antlrTool.panic(new StringBuffer("Error reading action:").append(str).toString());
            return str;
        } catch (RecognitionException e) {
            codeLexer.reportError(e);
            return str;
        } catch (TokenStreamException unused2) {
            this.antlrTool.panic(new StringBuffer("Error reading action:").append(str).toString());
            return str;
        }
    }

    protected void printActionCode(String str, int i) {
        printAction(processActionCode(str, i));
    }

    private void setupGrammarParameters(Grammar grammar) {
        Token option;
        String strStripFrontBack;
        Token option2;
        String strStripFrontBack2;
        Token option3;
        String strStripFrontBack3;
        Token option4;
        String strStripFrontBack4;
        Token option5;
        String strStripFrontBack5;
        if (grammar instanceof ParserGrammar) {
            this.labeledElementASTType = "";
            if (grammar.hasOption("ASTLabelType") && (option5 = grammar.getOption("ASTLabelType")) != null && (strStripFrontBack5 = StringUtils.stripFrontBack(option5.getText(), "\"", "\"")) != null) {
                this.labeledElementASTType = strStripFrontBack5;
            }
            this.labeledElementType = "";
            this.labeledElementInit = "None";
            this.commonExtraArgs = "";
            this.commonExtraParams = "self";
            this.commonLocalVars = "";
            this.lt1Value = "self.LT(1)";
            this.exceptionThrown = "antlr.RecognitionException";
            this.throwNoViable = "raise antlr.NoViableAltException(self.LT(1), self.getFilename())";
            this.parserClassName = "Parser";
            if (!grammar.hasOption("className") || (option4 = grammar.getOption("className")) == null || (strStripFrontBack4 = StringUtils.stripFrontBack(option4.getText(), "\"", "\"")) == null) {
                return;
            }
            this.parserClassName = strStripFrontBack4;
            return;
        }
        if (grammar instanceof LexerGrammar) {
            this.labeledElementType = "char ";
            this.labeledElementInit = "'\\0'";
            this.commonExtraArgs = "";
            this.commonExtraParams = "self, _createToken";
            this.commonLocalVars = "_ttype = 0\n        _token = None\n        _begin = self.text.length()";
            this.lt1Value = "self.LA(1)";
            this.exceptionThrown = "antlr.RecognitionException";
            this.throwNoViable = "self.raise_NoViableAlt(self.LA(1))";
            this.lexerClassName = "Lexer";
            if (!grammar.hasOption("className") || (option3 = grammar.getOption("className")) == null || (strStripFrontBack3 = StringUtils.stripFrontBack(option3.getText(), "\"", "\"")) == null) {
                return;
            }
            this.lexerClassName = strStripFrontBack3;
            return;
        }
        if (grammar instanceof TreeWalkerGrammar) {
            this.labeledElementASTType = "";
            this.labeledElementType = "";
            if (grammar.hasOption("ASTLabelType") && (option2 = grammar.getOption("ASTLabelType")) != null && (strStripFrontBack2 = StringUtils.stripFrontBack(option2.getText(), "\"", "\"")) != null) {
                this.labeledElementASTType = strStripFrontBack2;
                this.labeledElementType = strStripFrontBack2;
            }
            if (!grammar.hasOption("ASTLabelType")) {
                grammar.setOption("ASTLabelType", new Token(6, "<4>AST"));
            }
            this.labeledElementInit = "None";
            this.commonExtraArgs = "_t";
            this.commonExtraParams = "self, _t";
            this.commonLocalVars = "";
            this.lt1Value = "_t";
            this.exceptionThrown = "antlr.RecognitionException";
            this.throwNoViable = "raise antlr.NoViableAltException(_t)";
            this.treeWalkerClassName = "Walker";
            if (!grammar.hasOption("className") || (option = grammar.getOption("className")) == null || (strStripFrontBack = StringUtils.stripFrontBack(option.getText(), "\"", "\"")) == null) {
                return;
            }
            this.treeWalkerClassName = strStripFrontBack;
            return;
        }
        this.antlrTool.panic("Unknown grammar type");
    }

    public void setupOutput(String str) throws IOException {
        this.currentOutput = this.antlrTool.openOutputFile(new StringBuffer().append(str).append(".py").toString());
    }

    @Override // antlr.CodeGenerator
    protected void _printAction(String str) {
        if (str == null) {
            return;
        }
        int length = str.length();
        int i = 0;
        int i2 = 0;
        boolean z = true;
        while (i < length && z) {
            int i3 = i + 1;
            char cCharAt = str.charAt(i);
            if (cCharAt == '\n') {
                i = i3;
                i2 = i;
            } else if (cCharAt != '\r') {
                if (cCharAt != ' ') {
                    z = false;
                }
                i = i3;
            } else {
                i2 = (i3 > length || str.charAt(i3) != '\n') ? i3 : i + 2;
                i = i2;
            }
        }
        if (!z) {
            i--;
        }
        int i4 = i - i2;
        int i5 = length - 1;
        while (i5 > i && isspace(str.charAt(i5))) {
            i5--;
        }
        boolean z2 = false;
        while (i <= i5) {
            char cCharAt2 = str.charAt(i);
            if (cCharAt2 == '\t') {
                System.err.println("warning: tab characters used in Python action");
                this.currentOutput.print("        ");
            } else if (cCharAt2 == '\n') {
                z2 = true;
            } else if (cCharAt2 == '\r') {
                int i6 = i + 1;
                if (i6 <= i5 && str.charAt(i6) == '\n') {
                    i = i6;
                }
                z2 = true;
            } else if (cCharAt2 == ' ') {
                this.currentOutput.print(" ");
            } else {
                this.currentOutput.print(cCharAt2);
            }
            if (z2) {
                this.currentOutput.print("\n");
                printTabs();
                i++;
                z2 = false;
                int i7 = 0;
                while (i <= i5) {
                    char cCharAt3 = str.charAt(i);
                    if (!isspace(cCharAt3)) {
                        i--;
                        break;
                    }
                    if (cCharAt3 == '\n') {
                        z2 = true;
                    } else if (cCharAt3 == '\r') {
                        int i8 = i + 1;
                        if (i8 <= i5 && str.charAt(i8) == '\n') {
                            i = i8;
                        }
                        z2 = true;
                    }
                    if (!z2) {
                        if (i7 >= i4) {
                            break;
                        } else {
                            i7++;
                        }
                    } else {
                        this.currentOutput.print("\n");
                        printTabs();
                        z2 = false;
                        i7 = 0;
                    }
                    i++;
                }
            }
            i++;
        }
        this.currentOutput.println();
    }

    protected void od(String str, int i, int i2, String str2) {
        System.out.println(str2);
        while (i <= i2) {
            char cCharAt = str.charAt(i);
            if (cCharAt == '\t') {
                System.out.print(" ht ");
            } else if (cCharAt == '\n') {
                System.out.print(" nl ");
            } else if (cCharAt == ' ') {
                System.out.print(" sp ");
            } else {
                System.out.print(new StringBuffer(" ").append(cCharAt).append(" ").toString());
            }
            i++;
        }
        System.out.println("");
    }

    @Override // antlr.CodeGenerator
    protected void printAction(String str) {
        if (str != null) {
            printTabs();
            _printAction(str);
        }
    }

    protected void printGrammarAction(Grammar grammar) {
        println("### user action >>>");
        printAction(processActionForSpecialSymbols(grammar.classMemberAction.getText(), grammar.classMemberAction.getLine(), this.currentRule, null));
        println("### user action <<<");
    }

    protected void _printJavadoc(String str) {
        int length = str.length();
        this.currentOutput.print("\n");
        printTabs();
        this.currentOutput.print("###");
        int i = 0;
        boolean z = false;
        while (i < length) {
            char cCharAt = str.charAt(i);
            if (cCharAt == '\t') {
                this.currentOutput.print("\t");
            } else if (cCharAt == '\n') {
                z = true;
            } else if (cCharAt == '\r') {
                int i2 = i + 1;
                if (i2 <= length && str.charAt(i2) == '\n') {
                    i = i2;
                }
                z = true;
            } else if (cCharAt == ' ') {
                this.currentOutput.print(" ");
            } else {
                this.currentOutput.print(cCharAt);
            }
            if (z) {
                this.currentOutput.print("\n");
                printTabs();
                this.currentOutput.print("###");
                z = false;
            }
            i++;
        }
        this.currentOutput.println();
    }

    protected void genJavadocComment(Grammar grammar) {
        if (grammar.comment != null) {
            _printJavadoc(grammar.comment);
        }
    }

    protected void genJavadocComment(RuleSymbol ruleSymbol) {
        if (ruleSymbol.comment != null) {
            _printJavadoc(ruleSymbol.comment);
        }
    }
}
