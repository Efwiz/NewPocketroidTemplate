package antlr;

import antlr.actions.csharp.ActionLexer;
import antlr.collections.impl.BitSet;
import com.huawei.hms.framework.common.ContainerUtils;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;
import name.antonsmirnov.firmata.FormatHelper;
import org.antlr.runtime.BaseRecognizer;
import org.apache.commons.lang3.SystemProperties;
import org.catrobat.catroid.common.Constants;
import org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;

/* JADX INFO: loaded from: classes.dex */
public class CSharpCodeGenerator extends CodeGenerator {
    public static final int caseSizeThreshold = 127;
    private Vector astTypes;
    int blockNestingLevel;
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
    int saveIndexCreateLevel;
    private antlr.collections.impl.Vector semPreds;
    String throwNoViable;
    protected static final String NONUNIQUE = new String();
    private static CSharpNameSpace nameSpace = null;
    protected int syntacticPredLevel = 0;
    protected boolean genAST = false;
    protected boolean saveText = false;
    boolean usingCustomAST = false;
    Hashtable treeVariableMap = new Hashtable();
    Hashtable declaredASTVariables = new Hashtable();
    int astVarNumber = 1;

    protected void genMatch(BitSet bitSet) {
    }

    public CSharpCodeGenerator() {
        this.charFormatter = new CSharpCharFormatter();
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
        if (this.DEBUG_CODE_GENERATOR) {
            System.out.println(new StringBuffer("genAction(").append(actionElement).append(Constant.AFTER_QUTO).toString());
        }
        if (actionElement.isSemPred) {
            genSemPred(actionElement.actionText, actionElement.line);
            return;
        }
        if (this.grammar.hasSyntacticPredicate) {
            println("if (0==inputState.guessing)");
            println("{");
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
            println(new StringBuffer("if ( (null != ").append(actionTransInfo.refRuleRoot).append(") && (null != ").append(actionTransInfo.refRuleRoot).append(".getFirstChild()) )").toString());
            this.tabs++;
            println(new StringBuffer("currentAST.child = ").append(actionTransInfo.refRuleRoot).append(".getFirstChild();").toString());
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
        if (this.DEBUG_CODE_GENERATOR) {
            System.out.println(new StringBuffer("gen(").append(alternativeBlock).append(Constant.AFTER_QUTO).toString());
        }
        println("{");
        this.tabs++;
        genBlockPreamble(alternativeBlock);
        genBlockInitAction(alternativeBlock);
        String str = this.currentASTResult;
        if (alternativeBlock.getLabel() != null) {
            this.currentASTResult = alternativeBlock.getLabel();
        }
        this.grammar.theLLkAnalyzer.deterministic(alternativeBlock);
        genBlockFinish(genCommonBlock(alternativeBlock, true), this.throwNoViable);
        this.tabs--;
        println("}");
        this.currentASTResult = str;
    }

    @Override // antlr.CodeGenerator
    public void gen(BlockEndElement blockEndElement) {
        if (this.DEBUG_CODE_GENERATOR) {
            System.out.println(new StringBuffer("genRuleEnd(").append(blockEndElement).append(Constant.AFTER_QUTO).toString());
        }
    }

    /* JADX WARN: Code duplicated, block: B:13:0x0056  */
    @Override // antlr.CodeGenerator
    public void gen(CharLiteralElement charLiteralElement) {
        boolean z;
        if (this.DEBUG_CODE_GENERATOR) {
            System.out.println(new StringBuffer("genChar(").append(charLiteralElement).append(Constant.AFTER_QUTO).toString());
        }
        if (charLiteralElement.getLabel() != null) {
            println(new StringBuffer().append(charLiteralElement.getLabel()).append(" = ").append(this.lt1Value).append(";").toString());
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
        if (charRangeElement.getLabel() != null && this.syntacticPredLevel == 0) {
            println(new StringBuffer().append(charRangeElement.getLabel()).append(" = ").append(this.lt1Value).append(";").toString());
        }
        boolean z = (this.grammar instanceof LexerGrammar) && (!this.saveText || charRangeElement.getAutoGenType() == 3);
        if (z) {
            println("_saveIndex = text.Length;");
        }
        println(new StringBuffer("matchRange(").append(OctalToUnicode(charRangeElement.beginText)).append(",").append(OctalToUnicode(charRangeElement.endText)).append(");").toString());
        if (z) {
            println("text.Length = _saveIndex;");
        }
    }

    @Override // antlr.CodeGenerator
    public void gen(LexerGrammar lexerGrammar) throws IOException {
        if (lexerGrammar.debuggingOutput) {
            this.semPreds = new antlr.collections.impl.Vector();
        }
        setGrammar(lexerGrammar);
        if (!(this.grammar instanceof LexerGrammar)) {
            this.antlrTool.panic("Internal error generating lexer");
        }
        genBody(lexerGrammar);
    }

    /* JADX WARN: Code duplicated, block: B:30:0x00ee  */
    /* JADX WARN: Code duplicated, block: B:32:0x00f2  */
    /* JADX WARN: Code duplicated, block: B:36:0x0197  */
    /* JADX WARN: Code duplicated, block: B:39:0x01c9  */
    @Override // antlr.CodeGenerator
    public void gen(OneOrMoreBlock oneOrMoreBlock) {
        String string;
        String string2;
        boolean z;
        int i;
        int i2;
        if (this.DEBUG_CODE_GENERATOR) {
            System.out.println(new StringBuffer("gen+(").append(oneOrMoreBlock).append(Constant.AFTER_QUTO).toString());
        }
        println("{ // ( ... )+");
        this.tabs++;
        this.blockNestingLevel++;
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
        println("for (;;)");
        println("{");
        this.tabs++;
        this.blockNestingLevel++;
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
                if (this.DEBUG_CODE_GENERATOR) {
                    System.out.println(new StringBuffer("nongreedy (...)+ loop; exit depth is ").append(oneOrMoreBlock.exitLookaheadDepth).toString());
                }
                String lookaheadTestExpression = getLookaheadTestExpression(oneOrMoreBlock.exitCache, i3);
                println("// nongreedy exit test");
                println(new StringBuffer("if ((").append(string).append(" >= 1) && ").append(lookaheadTestExpression).append(") goto ").append(string2).append("_breakloop;").toString());
            }
            genBlockFinish(genCommonBlock(oneOrMoreBlock, false), new StringBuffer("if (").append(string).append(" >= 1) { goto ").append(string2).append("_breakloop; } else { ").append(this.throwNoViable).append("; }").toString());
            println(new StringBuffer().append(string).append("++;").toString());
            this.tabs--;
            i = this.blockNestingLevel;
            this.blockNestingLevel = i - 1;
            if (i == this.saveIndexCreateLevel) {
                this.saveIndexCreateLevel = 0;
            }
            println("}");
            _print(new StringBuffer().append(string2).append("_breakloop:").toString());
            println(";");
            this.tabs--;
            i2 = this.blockNestingLevel;
            this.blockNestingLevel = i2 - 1;
            if (i2 == this.saveIndexCreateLevel) {
                this.saveIndexCreateLevel = 0;
            }
            println("}    // ( ... )+");
            this.currentASTResult = str;
        }
        z = true;
        if (z) {
            if (this.DEBUG_CODE_GENERATOR) {
                System.out.println(new StringBuffer("nongreedy (...)+ loop; exit depth is ").append(oneOrMoreBlock.exitLookaheadDepth).toString());
            }
            String lookaheadTestExpression2 = getLookaheadTestExpression(oneOrMoreBlock.exitCache, i3);
            println("// nongreedy exit test");
            println(new StringBuffer("if ((").append(string).append(" >= 1) && ").append(lookaheadTestExpression2).append(") goto ").append(string2).append("_breakloop;").toString());
        }
        genBlockFinish(genCommonBlock(oneOrMoreBlock, false), new StringBuffer("if (").append(string).append(" >= 1) { goto ").append(string2).append("_breakloop; } else { ").append(this.throwNoViable).append("; }").toString());
        println(new StringBuffer().append(string).append("++;").toString());
        this.tabs--;
        i = this.blockNestingLevel;
        this.blockNestingLevel = i - 1;
        if (i == this.saveIndexCreateLevel) {
            this.saveIndexCreateLevel = 0;
        }
        println("}");
        _print(new StringBuffer().append(string2).append("_breakloop:").toString());
        println(";");
        this.tabs--;
        i2 = this.blockNestingLevel;
        this.blockNestingLevel = i2 - 1;
        if (i2 == this.saveIndexCreateLevel) {
            this.saveIndexCreateLevel = 0;
        }
        println("}    // ( ... )+");
        this.currentASTResult = str;
    }

    @Override // antlr.CodeGenerator
    public void gen(ParserGrammar parserGrammar) throws IOException {
        if (parserGrammar.debuggingOutput) {
            this.semPreds = new antlr.collections.impl.Vector();
        }
        setGrammar(parserGrammar);
        if (!(this.grammar instanceof ParserGrammar)) {
            this.antlrTool.panic("Internal error generating parser");
        }
        genBody(parserGrammar);
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
            println(new StringBuffer().append(ruleRefElement.getLabel()).append(" = _t==ASTNULL ? null : ").append(this.lt1Value).append(";").toString());
        }
        if ((this.grammar instanceof LexerGrammar) && (!this.saveText || ruleRefElement.getAutoGenType() == 3)) {
            declareSaveIndexVariableIfNeeded();
            println("_saveIndex = text.Length;");
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
            declareSaveIndexVariableIfNeeded();
            println("text.Length = _saveIndex;");
        }
        if (this.syntacticPredLevel == 0) {
            boolean z = this.grammar.hasSyntacticPredicate && ((this.grammar.buildAST && ruleRefElement.getLabel() != null) || (this.genAST && ruleRefElement.getAutoGenType() == 1));
            if (z) {
                println("if (0 == inputState.guessing)");
                println("{");
                this.tabs++;
            }
            if (this.grammar.buildAST && ruleRefElement.getLabel() != null) {
                println(new StringBuffer().append(ruleRefElement.getLabel()).append("_AST = (").append(this.labeledElementASTType).append(")returnAST;").toString());
            }
            if (this.genAST) {
                int autoGenType = ruleRefElement.getAutoGenType();
                if (autoGenType != 1) {
                    if (autoGenType == 2) {
                        this.antlrTool.error("Internal: encountered ^ after rule reference");
                    }
                } else if (this.usingCustomAST) {
                    println("astFactory.addASTChild(ref currentAST, (AST)returnAST);");
                } else {
                    println("astFactory.addASTChild(ref currentAST, returnAST);");
                }
            }
            if ((this.grammar instanceof LexerGrammar) && ruleRefElement.getLabel() != null) {
                println(new StringBuffer().append(ruleRefElement.getLabel()).append(" = returnToken_;").toString());
            }
            if (z) {
                this.tabs--;
                println("}");
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
            println("_t = _t.getNextSibling();");
        }
    }

    @Override // antlr.CodeGenerator
    public void gen(TokenRangeElement tokenRangeElement) {
        genErrorTryForElement(tokenRangeElement);
        if (tokenRangeElement.getLabel() != null && this.syntacticPredLevel == 0) {
            println(new StringBuffer().append(tokenRangeElement.getLabel()).append(" = ").append(this.lt1Value).append(";").toString());
        }
        genElementAST(tokenRangeElement);
        println(new StringBuffer("matchRange(").append(OctalToUnicode(tokenRangeElement.beginText)).append(",").append(OctalToUnicode(tokenRangeElement.endText)).append(");").toString());
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
            println(new StringBuffer().append(tokenRefElement.getLabel()).append(" = ").append(this.lt1Value).append(";").toString());
        }
        genElementAST(tokenRefElement);
        genMatch(tokenRefElement);
        genErrorCatchForElement(tokenRefElement);
        if (this.grammar instanceof TreeWalkerGrammar) {
            println("_t = _t.getNextSibling();");
        }
    }

    @Override // antlr.CodeGenerator
    public void gen(TreeElement treeElement) {
        println(new StringBuffer("AST __t").append(treeElement.ID).append(" = _t;").toString());
        if (treeElement.root.getLabel() != null) {
            println(new StringBuffer().append(treeElement.root.getLabel()).append(" = (ASTNULL == _t) ? null : (").append(this.labeledElementASTType).append(")_t;").toString());
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
            println("if (null == _t) throw new MismatchedTokenException();");
        } else {
            genMatch(treeElement.root);
        }
        println("_t = _t.getFirstChild();");
        for (int i = 0; i < treeElement.getAlternatives().size(); i++) {
            for (AlternativeElement alternativeElement = treeElement.getAlternativeAt(i).head; alternativeElement != null; alternativeElement = alternativeElement.next) {
                alternativeElement.generate();
            }
        }
        if (this.grammar.buildAST) {
            println(new StringBuffer("currentAST = __currentAST").append(treeElement.ID).append(";").toString());
        }
        println(new StringBuffer("_t = __t").append(treeElement.ID).append(";").toString());
        println("_t = _t.getNextSibling();");
    }

    @Override // antlr.CodeGenerator
    public void gen(TreeWalkerGrammar treeWalkerGrammar) throws IOException {
        setGrammar(treeWalkerGrammar);
        if (!(this.grammar instanceof TreeWalkerGrammar)) {
            this.antlrTool.panic("Internal error generating tree-walker");
        }
        genBody(treeWalkerGrammar);
    }

    @Override // antlr.CodeGenerator
    public void gen(WildcardElement wildcardElement) {
        if (wildcardElement.getLabel() != null && this.syntacticPredLevel == 0) {
            println(new StringBuffer().append(wildcardElement.getLabel()).append(" = ").append(this.lt1Value).append(";").toString());
        }
        genElementAST(wildcardElement);
        if (this.grammar instanceof TreeWalkerGrammar) {
            println("if (null == _t) throw new MismatchedTokenException();");
        } else if (this.grammar instanceof LexerGrammar) {
            if ((this.grammar instanceof LexerGrammar) && (!this.saveText || wildcardElement.getAutoGenType() == 3)) {
                declareSaveIndexVariableIfNeeded();
                println("_saveIndex = text.Length;");
            }
            println("matchNot(EOF/*_CHAR*/);");
            if ((this.grammar instanceof LexerGrammar) && (!this.saveText || wildcardElement.getAutoGenType() == 3)) {
                declareSaveIndexVariableIfNeeded();
                println("text.Length = _saveIndex;");
            }
        } else {
            println(new StringBuffer("matchNot(").append(getValueString(1)).append(");").toString());
        }
        if (this.grammar instanceof TreeWalkerGrammar) {
            println("_t = _t.getNextSibling();");
        }
    }

    /* JADX WARN: Code duplicated, block: B:27:0x00ad  */
    /* JADX WARN: Code duplicated, block: B:29:0x00b1  */
    /* JADX WARN: Code duplicated, block: B:33:0x011c  */
    /* JADX WARN: Code duplicated, block: B:36:0x014e  */
    @Override // antlr.CodeGenerator
    public void gen(ZeroOrMoreBlock zeroOrMoreBlock) {
        String string;
        boolean z;
        int i;
        int i2;
        if (this.DEBUG_CODE_GENERATOR) {
            System.out.println(new StringBuffer("gen*(").append(zeroOrMoreBlock).append(Constant.AFTER_QUTO).toString());
        }
        println("{    // ( ... )*");
        this.tabs++;
        this.blockNestingLevel++;
        genBlockPreamble(zeroOrMoreBlock);
        if (zeroOrMoreBlock.getLabel() != null) {
            string = zeroOrMoreBlock.getLabel();
        } else {
            string = new StringBuffer("_loop").append(zeroOrMoreBlock.ID).toString();
        }
        println("for (;;)");
        println("{");
        this.tabs++;
        this.blockNestingLevel++;
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
                println("// nongreedy exit test");
                println(new StringBuffer("if (").append(lookaheadTestExpression).append(") goto ").append(string).append("_breakloop;").toString());
            }
            genBlockFinish(genCommonBlock(zeroOrMoreBlock, false), new StringBuffer("goto ").append(string).append("_breakloop;").toString());
            this.tabs--;
            i = this.blockNestingLevel;
            this.blockNestingLevel = i - 1;
            if (i == this.saveIndexCreateLevel) {
                this.saveIndexCreateLevel = 0;
            }
            println("}");
            _print(new StringBuffer().append(string).append("_breakloop:").toString());
            println(";");
            this.tabs--;
            i2 = this.blockNestingLevel;
            this.blockNestingLevel = i2 - 1;
            if (i2 == this.saveIndexCreateLevel) {
                this.saveIndexCreateLevel = 0;
            }
            println("}    // ( ... )*");
            this.currentASTResult = str;
        }
        z = true;
        if (z) {
            if (this.DEBUG_CODE_GENERATOR) {
                System.out.println(new StringBuffer("nongreedy (...)* loop; exit depth is ").append(zeroOrMoreBlock.exitLookaheadDepth).toString());
            }
            String lookaheadTestExpression2 = getLookaheadTestExpression(zeroOrMoreBlock.exitCache, i3);
            println("// nongreedy exit test");
            println(new StringBuffer("if (").append(lookaheadTestExpression2).append(") goto ").append(string).append("_breakloop;").toString());
        }
        genBlockFinish(genCommonBlock(zeroOrMoreBlock, false), new StringBuffer("goto ").append(string).append("_breakloop;").toString());
        this.tabs--;
        i = this.blockNestingLevel;
        this.blockNestingLevel = i - 1;
        if (i == this.saveIndexCreateLevel) {
            this.saveIndexCreateLevel = 0;
        }
        println("}");
        _print(new StringBuffer().append(string).append("_breakloop:").toString());
        println(";");
        this.tabs--;
        i2 = this.blockNestingLevel;
        this.blockNestingLevel = i2 - 1;
        if (i2 == this.saveIndexCreateLevel) {
            this.saveIndexCreateLevel = 0;
        }
        println("}    // ( ... )*");
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
            println("try        // for error handling");
            println("{");
            this.tabs++;
        }
        for (AlternativeElement alternativeElement = alternative.head; !(alternativeElement instanceof BlockEndElement); alternativeElement = alternativeElement.next) {
            alternativeElement.generate();
        }
        if (this.genAST) {
            if (alternativeBlock instanceof RuleBlock) {
                RuleBlock ruleBlock = (RuleBlock) alternativeBlock;
                if (this.usingCustomAST) {
                    println(new StringBuffer().append(ruleBlock.getRuleName()).append("_AST = (").append(this.labeledElementASTType).append(")currentAST.root;").toString());
                } else {
                    println(new StringBuffer().append(ruleBlock.getRuleName()).append("_AST = currentAST.root;").toString());
                }
            } else if (alternativeBlock.getLabel() != null) {
                this.antlrTool.warning("Labeled subrules not yet supported", this.grammar.getFilename(), alternativeBlock.getLine(), alternativeBlock.getColumn());
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

    protected void genBitsets(antlr.collections.impl.Vector vector, int i) {
        println("");
        for (int i2 = 0; i2 < vector.size(); i2++) {
            BitSet bitSet = (BitSet) vector.elementAt(i2);
            bitSet.growToInclude(i);
            genBitSet(bitSet, i2);
        }
    }

    private void genBitSet(BitSet bitSet, int i) {
        int i2;
        println(new StringBuffer("private static long[] mk_").append(getBitsetName(i)).append("()").toString());
        println("{");
        this.tabs++;
        int iLengthInLongWords = bitSet.lengthInLongWords();
        if (iLengthInLongWords < 8) {
            println(new StringBuffer("long[] data = { ").append(bitSet.toStringOfWords()).append("};").toString());
        } else {
            println(new StringBuffer("long[] data = new long[").append(iLengthInLongWords).append("];").toString());
            long[] packedArray = bitSet.toPackedArray();
            for (int i3 = 0; i3 < packedArray.length; i3 = i2) {
                i2 = i3 + 1;
                if (i2 == packedArray.length || packedArray[i3] != packedArray[i2]) {
                    println(new StringBuffer("data[").append(i3).append("]=").append(packedArray[i3]).append("L;").toString());
                } else {
                    while (i2 < packedArray.length && packedArray[i2] == packedArray[i3]) {
                        i2++;
                    }
                    println(new StringBuffer("for (int i = ").append(i3).append("; i<=").append(i2 - 1).append("; i++) { data[i]=").append(packedArray[i3]).append("L; }").toString());
                }
            }
        }
        println("return data;");
        this.tabs--;
        println("}");
        println(new StringBuffer("public static readonly BitSet ").append(getBitsetName(i)).append(" = new BitSet(mk_").append(getBitsetName(i)).append("());").toString());
    }

    @Override // antlr.CodeGenerator
    protected String getBitsetName(int i) {
        return new StringBuffer("tokenSet_").append(i).append("_").toString();
    }

    private void genBlockFinish(CSharpBlockFinishingInfo cSharpBlockFinishingInfo, String str) {
        if (cSharpBlockFinishingInfo.needAnErrorClause && (cSharpBlockFinishingInfo.generatedAnIf || cSharpBlockFinishingInfo.generatedSwitch)) {
            if (cSharpBlockFinishingInfo.generatedAnIf) {
                println(PsiKeyword.ELSE);
                println("{");
            } else {
                println("{");
            }
            this.tabs++;
            println(str);
            this.tabs--;
            println("}");
        }
        if (cSharpBlockFinishingInfo.postscript != null) {
            if (cSharpBlockFinishingInfo.needAnErrorClause && cSharpBlockFinishingInfo.generatedSwitch && !cSharpBlockFinishingInfo.generatedAnIf && str != null) {
                if (str.indexOf(PsiKeyword.THROW) == 0 || str.indexOf(PsiKeyword.GOTO) == 0) {
                    println(cSharpBlockFinishingInfo.postscript.substring(cSharpBlockFinishingInfo.postscript.indexOf("break;") + 6));
                    return;
                } else {
                    println(cSharpBlockFinishingInfo.postscript);
                    return;
                }
            }
            println(cSharpBlockFinishingInfo.postscript);
        }
    }

    protected void genBlockInitAction(AlternativeBlock alternativeBlock) {
        if (alternativeBlock.initAction != null) {
            printAction(processActionForSpecialSymbols(alternativeBlock.initAction, alternativeBlock.getLine(), this.currentRule, null));
        }
    }

    /* JADX WARN: Code duplicated, block: B:25:0x007a  */
    /* JADX WARN: Code duplicated, block: B:34:0x00ca  */
    /* JADX WARN: Code duplicated, block: B:36:0x00d0  */
    /* JADX WARN: Code duplicated, block: B:39:0x00d9  */
    /* JADX WARN: Code duplicated, block: B:42:0x00fb  */
    /* JADX WARN: Code duplicated, block: B:52:0x0127 A[SYNTHETIC] */
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
                            println(new StringBuffer().append(this.labeledElementType).append(" ").append(alternativeElement.getLabel()).append(" = ").append(this.labeledElementInit).append(";").toString());
                            if (this.grammar.buildAST) {
                                genASTDeclaration(alternativeElement);
                            }
                        } else {
                            if (this.grammar.buildAST) {
                                genASTDeclaration(alternativeElement);
                            }
                            if (this.grammar instanceof LexerGrammar) {
                                println(new StringBuffer("IToken ").append(alternativeElement.getLabel()).append(" = null;").toString());
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
                            println(new StringBuffer("IToken ").append(alternativeElement.getLabel()).append(" = null;").toString());
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
        String strStripFrontBack;
        String strStripFrontBack2;
        setupOutput(this.grammar.getClassName());
        this.genAST = false;
        this.saveText = true;
        this.tabs = 0;
        genHeader();
        println(this.behavior.getHeaderAction(""));
        CSharpNameSpace cSharpNameSpace = nameSpace;
        if (cSharpNameSpace != null) {
            cSharpNameSpace.emitDeclarations(this.currentOutput);
        }
        this.tabs++;
        println("// Generate header specific to lexer CSharp file");
        println("using System;");
        println("using Stream                          = System.IO.Stream;");
        println("using TextReader                      = System.IO.TextReader;");
        println("using Hashtable                       = System.Collections.Hashtable;");
        println("using Comparer                        = System.Collections.Comparer;");
        if (!lexerGrammar.caseSensitiveLiterals) {
            println("using CaseInsensitiveHashCodeProvider = System.Collections.CaseInsensitiveHashCodeProvider;");
            println("using CaseInsensitiveComparer         = System.Collections.CaseInsensitiveComparer;");
        }
        println("");
        println("using TokenStreamException            = antlr.TokenStreamException;");
        println("using TokenStreamIOException          = antlr.TokenStreamIOException;");
        println("using TokenStreamRecognitionException = antlr.TokenStreamRecognitionException;");
        println("using CharStreamException             = antlr.CharStreamException;");
        println("using CharStreamIOException           = antlr.CharStreamIOException;");
        println("using ANTLRException                  = antlr.ANTLRException;");
        println("using CharScanner                     = antlr.CharScanner;");
        println("using InputBuffer                     = antlr.InputBuffer;");
        println("using ByteBuffer                      = antlr.ByteBuffer;");
        println("using CharBuffer                      = antlr.CharBuffer;");
        println("using Token                           = antlr.Token;");
        println("using IToken                          = antlr.IToken;");
        println("using CommonToken                     = antlr.CommonToken;");
        println("using SemanticException               = antlr.SemanticException;");
        println("using RecognitionException            = antlr.RecognitionException;");
        println("using NoViableAltForCharException     = antlr.NoViableAltForCharException;");
        println("using MismatchedCharException         = antlr.MismatchedCharException;");
        println("using TokenStream                     = antlr.TokenStream;");
        println("using LexerSharedInputState           = antlr.LexerSharedInputState;");
        println("using BitSet                          = antlr.collections.impl.BitSet;");
        println(this.grammar.preambleAction.getText());
        if (this.grammar.superClass != null) {
            string = this.grammar.superClass;
        } else {
            string = new StringBuffer("antlr.").append(this.grammar.getSuperClass()).toString();
        }
        if (this.grammar.comment != null) {
            _println(this.grammar.comment);
        }
        Token token = (Token) this.grammar.options.get("classHeaderPrefix");
        if (token == null || (strStripFrontBack = StringUtils.stripFrontBack(token.getText(), "\"", "\"")) == null) {
            print("public ");
        } else {
            print(new StringBuffer().append(strStripFrontBack).append(" ").toString());
        }
        print(new StringBuffer(ExternalAnnotationProvider.CLASS_PREFIX).append(this.grammar.getClassName()).append(" : ").append(string).toString());
        println(", TokenStream");
        Token token2 = (Token) this.grammar.options.get("classHeaderSuffix");
        if (token2 != null && (strStripFrontBack2 = StringUtils.stripFrontBack(token2.getText(), "\"", "\"")) != null) {
            print(new StringBuffer(", ").append(strStripFrontBack2).toString());
        }
        println(" {");
        this.tabs++;
        genTokenDefinitions(this.grammar.tokenManager);
        print(processActionForSpecialSymbols(this.grammar.classMemberAction.getText(), this.grammar.classMemberAction.getLine(), this.currentRule, null));
        println(new StringBuffer("public ").append(this.grammar.getClassName()).append("(Stream ins) : this(new ByteBuffer(ins))").toString());
        println("{");
        println("}");
        println("");
        println(new StringBuffer("public ").append(this.grammar.getClassName()).append("(TextReader r) : this(new CharBuffer(r))").toString());
        println("{");
        println("}");
        println("");
        print(new StringBuffer("public ").append(this.grammar.getClassName()).append("(InputBuffer ib)").toString());
        if (this.grammar.debuggingOutput) {
            println(" : this(new LexerSharedInputState(new antlr.debug.DebuggingInputBuffer(ib)))");
        } else {
            println(" : this(new LexerSharedInputState(ib))");
        }
        println("{");
        println("}");
        println("");
        println(new StringBuffer("public ").append(this.grammar.getClassName()).append("(LexerSharedInputState state) : base(state)").toString());
        println("{");
        this.tabs++;
        println("initialize();");
        this.tabs--;
        println("}");
        println("private void initialize()");
        println("{");
        this.tabs++;
        if (this.grammar.debuggingOutput) {
            println("ruleNames  = _ruleNames;");
            println("semPredNames = _semPredNames;");
            println("setupDebugging();");
        }
        println(new StringBuffer("caseSensitiveLiterals = ").append(lexerGrammar.caseSensitiveLiterals).append(";").toString());
        println(new StringBuffer("setCaseSensitive(").append(lexerGrammar.caseSensitive).append(");").toString());
        if (lexerGrammar.caseSensitiveLiterals) {
            println("literals = new Hashtable(100, (float) 0.4, null, Comparer.Default);");
        } else {
            println("literals = new Hashtable(100, (float) 0.4, CaseInsensitiveHashCodeProvider.Default, CaseInsensitiveComparer.Default);");
        }
        Enumeration tokenSymbolKeys = this.grammar.tokenManager.getTokenSymbolKeys();
        while (tokenSymbolKeys.hasMoreElements()) {
            String str = (String) tokenSymbolKeys.nextElement();
            if (str.charAt(0) == '\"') {
                TokenSymbol tokenSymbol = this.grammar.tokenManager.getTokenSymbol(str);
                if (tokenSymbol instanceof StringLiteralSymbol) {
                    StringLiteralSymbol stringLiteralSymbol = (StringLiteralSymbol) tokenSymbol;
                    println(new StringBuffer("literals.Add(").append(stringLiteralSymbol.getId()).append(", ").append(stringLiteralSymbol.getTokenType()).append(");").toString());
                }
            }
        }
        this.tabs--;
        println("}");
        if (this.grammar.debuggingOutput) {
            println("private static readonly string[] _ruleNames = new string[] {");
            Enumeration enumerationElements = this.grammar.rules.elements();
            while (enumerationElements.hasMoreElements()) {
                GrammarSymbol grammarSymbol = (GrammarSymbol) enumerationElements.nextElement();
                if (grammarSymbol instanceof RuleSymbol) {
                    println(new StringBuffer("  \"").append(((RuleSymbol) grammarSymbol).getId()).append("\",").toString());
                }
            }
            println("};");
        }
        genNextToken();
        Enumeration enumerationElements2 = this.grammar.rules.elements();
        int i = 0;
        while (enumerationElements2.hasMoreElements()) {
            RuleSymbol ruleSymbol = (RuleSymbol) enumerationElements2.nextElement();
            if (!ruleSymbol.getId().equals("mnextToken")) {
                genRule(ruleSymbol, false, i, this.grammar.tokenManager);
                i++;
            }
            exitIfError();
        }
        if (this.grammar.debuggingOutput) {
            genSemPredMap();
        }
        genBitsets(this.bitsetsUsed, ((LexerGrammar) this.grammar).charVocabulary.size());
        println("");
        this.tabs--;
        println("}");
        this.tabs--;
        CSharpNameSpace cSharpNameSpace2 = nameSpace;
        if (cSharpNameSpace2 != null) {
            cSharpNameSpace2.emitClosures(this.currentOutput);
        }
        this.currentOutput.close();
        this.currentOutput = null;
    }

    public void genInitFactory(Grammar grammar) {
        TokenSymbol tokenSymbol;
        if (grammar.buildAST) {
            println("static public void initializeASTFactory( ASTFactory factory )");
            println("{");
            this.tabs++;
            println(new StringBuffer("factory.setMaxNodeType(").append(grammar.tokenManager.maxTokenType()).append(");").toString());
            antlr.collections.impl.Vector vocabulary = grammar.tokenManager.getVocabulary();
            for (int i = 0; i < vocabulary.size(); i++) {
                String str = (String) vocabulary.elementAt(i);
                if (str != null && (tokenSymbol = grammar.tokenManager.getTokenSymbol(str)) != null && tokenSymbol.getASTNodeType() != null) {
                    println(new StringBuffer("factory.setTokenTypeASTNodeType(").append(str).append(", \"").append(tokenSymbol.getASTNodeType()).append("\");").toString());
                }
            }
            this.tabs--;
            println("}");
        }
    }

    public void genBody(ParserGrammar parserGrammar) throws IOException {
        String string;
        String strStripFrontBack;
        String strStripFrontBack2;
        setupOutput(this.grammar.getClassName());
        this.genAST = this.grammar.buildAST;
        this.tabs = 0;
        genHeader();
        println(this.behavior.getHeaderAction(""));
        CSharpNameSpace cSharpNameSpace = nameSpace;
        if (cSharpNameSpace != null) {
            cSharpNameSpace.emitDeclarations(this.currentOutput);
        }
        this.tabs++;
        println("// Generate the header common to all output files.");
        println("using System;");
        println("");
        println("using TokenBuffer              = antlr.TokenBuffer;");
        println("using TokenStreamException     = antlr.TokenStreamException;");
        println("using TokenStreamIOException   = antlr.TokenStreamIOException;");
        println("using ANTLRException           = antlr.ANTLRException;");
        String superClass = this.grammar.getSuperClass();
        String[] strArrSplit = split(superClass, ".");
        println(new StringBuffer("using ").append(strArrSplit[strArrSplit.length - 1]).append(" = antlr.").append(superClass).append(";").toString());
        println("using Token                    = antlr.Token;");
        println("using IToken                   = antlr.IToken;");
        println("using TokenStream              = antlr.TokenStream;");
        println("using RecognitionException     = antlr.RecognitionException;");
        println("using NoViableAltException     = antlr.NoViableAltException;");
        println("using MismatchedTokenException = antlr.MismatchedTokenException;");
        println("using SemanticException        = antlr.SemanticException;");
        println("using ParserSharedInputState   = antlr.ParserSharedInputState;");
        println("using BitSet                   = antlr.collections.impl.BitSet;");
        if (this.genAST) {
            println("using AST                      = antlr.collections.AST;");
            println("using ASTPair                  = antlr.ASTPair;");
            println("using ASTFactory               = antlr.ASTFactory;");
            println("using ASTArray                 = antlr.collections.impl.ASTArray;");
        }
        println(this.grammar.preambleAction.getText());
        if (this.grammar.superClass != null) {
            string = this.grammar.superClass;
        } else {
            string = new StringBuffer("antlr.").append(this.grammar.getSuperClass()).toString();
        }
        if (this.grammar.comment != null) {
            _println(this.grammar.comment);
        }
        Token token = (Token) this.grammar.options.get("classHeaderPrefix");
        if (token == null || (strStripFrontBack = StringUtils.stripFrontBack(token.getText(), "\"", "\"")) == null) {
            print("public ");
        } else {
            print(new StringBuffer().append(strStripFrontBack).append(" ").toString());
        }
        println(new StringBuffer(ExternalAnnotationProvider.CLASS_PREFIX).append(this.grammar.getClassName()).append(" : ").append(string).toString());
        Token token2 = (Token) this.grammar.options.get("classHeaderSuffix");
        if (token2 != null && (strStripFrontBack2 = StringUtils.stripFrontBack(token2.getText(), "\"", "\"")) != null) {
            print(new StringBuffer("              , ").append(strStripFrontBack2).toString());
        }
        println("{");
        this.tabs++;
        genTokenDefinitions(this.grammar.tokenManager);
        if (this.grammar.debuggingOutput) {
            println("private static readonly string[] _ruleNames = new string[] {");
            this.tabs++;
            Enumeration enumerationElements = this.grammar.rules.elements();
            while (enumerationElements.hasMoreElements()) {
                GrammarSymbol grammarSymbol = (GrammarSymbol) enumerationElements.nextElement();
                if (grammarSymbol instanceof RuleSymbol) {
                    println(new StringBuffer("  \"").append(((RuleSymbol) grammarSymbol).getId()).append("\",").toString());
                }
            }
            this.tabs--;
            println("};");
        }
        print(processActionForSpecialSymbols(this.grammar.classMemberAction.getText(), this.grammar.classMemberAction.getLine(), this.currentRule, null));
        println("");
        println("protected void initialize()");
        println("{");
        this.tabs++;
        println("tokenNames = tokenNames_;");
        if (this.grammar.buildAST) {
            println("initializeFactory();");
        }
        if (this.grammar.debuggingOutput) {
            println("ruleNames  = _ruleNames;");
            println("semPredNames = _semPredNames;");
            println("setupDebugging(tokenBuf);");
        }
        this.tabs--;
        println("}");
        println("");
        println("");
        println(new StringBuffer("protected ").append(this.grammar.getClassName()).append("(TokenBuffer tokenBuf, int k) : base(tokenBuf, k)").toString());
        println("{");
        this.tabs++;
        println("initialize();");
        this.tabs--;
        println("}");
        println("");
        println(new StringBuffer("public ").append(this.grammar.getClassName()).append("(TokenBuffer tokenBuf) : this(tokenBuf,").append(this.grammar.maxk).append(Constant.AFTER_QUTO).toString());
        println("{");
        println("}");
        println("");
        println(new StringBuffer("protected ").append(this.grammar.getClassName()).append("(TokenStream lexer, int k) : base(lexer,k)").toString());
        println("{");
        this.tabs++;
        println("initialize();");
        this.tabs--;
        println("}");
        println("");
        println(new StringBuffer("public ").append(this.grammar.getClassName()).append("(TokenStream lexer) : this(lexer,").append(this.grammar.maxk).append(Constant.AFTER_QUTO).toString());
        println("{");
        println("}");
        println("");
        println(new StringBuffer("public ").append(this.grammar.getClassName()).append("(ParserSharedInputState state) : base(state,").append(this.grammar.maxk).append(Constant.AFTER_QUTO).toString());
        println("{");
        this.tabs++;
        println("initialize();");
        this.tabs--;
        println("}");
        println("");
        this.astTypes = new Vector(100);
        Enumeration enumerationElements2 = this.grammar.rules.elements();
        int i = 0;
        while (enumerationElements2.hasMoreElements()) {
            GrammarSymbol grammarSymbol2 = (GrammarSymbol) enumerationElements2.nextElement();
            if (grammarSymbol2 instanceof RuleSymbol) {
                RuleSymbol ruleSymbol = (RuleSymbol) grammarSymbol2;
                genRule(ruleSymbol, ruleSymbol.references.size() == 0, i, this.grammar.tokenManager);
                i++;
            }
            exitIfError();
        }
        if (this.usingCustomAST) {
            println(new StringBuffer("public new ").append(this.labeledElementASTType).append(" getAST()").toString());
            println("{");
            this.tabs++;
            println(new StringBuffer("return (").append(this.labeledElementASTType).append(") returnAST;").toString());
            this.tabs--;
            println("}");
            println("");
        }
        println("private void initializeFactory()");
        println("{");
        this.tabs++;
        if (this.grammar.buildAST) {
            println("if (astFactory == null)");
            println("{");
            this.tabs++;
            if (this.usingCustomAST) {
                println(new StringBuffer("astFactory = new ASTFactory(\"").append(this.labeledElementASTType).append("\");").toString());
            } else {
                println("astFactory = new ASTFactory();");
            }
            this.tabs--;
            println("}");
            println("initializeASTFactory( astFactory );");
        }
        this.tabs--;
        println("}");
        genInitFactory(parserGrammar);
        genTokenStrings();
        genBitsets(this.bitsetsUsed, this.grammar.tokenManager.maxTokenType());
        if (this.grammar.debuggingOutput) {
            genSemPredMap();
        }
        println("");
        this.tabs--;
        println("}");
        this.tabs--;
        CSharpNameSpace cSharpNameSpace2 = nameSpace;
        if (cSharpNameSpace2 != null) {
            cSharpNameSpace2.emitClosures(this.currentOutput);
        }
        this.currentOutput.close();
        this.currentOutput = null;
    }

    public void genBody(TreeWalkerGrammar treeWalkerGrammar) throws IOException {
        String string;
        String strStripFrontBack;
        String strStripFrontBack2;
        setupOutput(this.grammar.getClassName());
        this.genAST = this.grammar.buildAST;
        this.tabs = 0;
        genHeader();
        println(this.behavior.getHeaderAction(""));
        CSharpNameSpace cSharpNameSpace = nameSpace;
        if (cSharpNameSpace != null) {
            cSharpNameSpace.emitDeclarations(this.currentOutput);
        }
        this.tabs++;
        println("// Generate header specific to the tree-parser CSharp file");
        println("using System;");
        println("");
        println(new StringBuffer("using ").append(this.grammar.getSuperClass()).append(" = antlr.").append(this.grammar.getSuperClass()).append(";").toString());
        println("using Token                    = antlr.Token;");
        println("using IToken                   = antlr.IToken;");
        println("using AST                      = antlr.collections.AST;");
        println("using RecognitionException     = antlr.RecognitionException;");
        println("using ANTLRException           = antlr.ANTLRException;");
        println("using NoViableAltException     = antlr.NoViableAltException;");
        println("using MismatchedTokenException = antlr.MismatchedTokenException;");
        println("using SemanticException        = antlr.SemanticException;");
        println("using BitSet                   = antlr.collections.impl.BitSet;");
        println("using ASTPair                  = antlr.ASTPair;");
        println("using ASTFactory               = antlr.ASTFactory;");
        println("using ASTArray                 = antlr.collections.impl.ASTArray;");
        println(this.grammar.preambleAction.getText());
        if (this.grammar.superClass != null) {
            string = this.grammar.superClass;
        } else {
            string = new StringBuffer("antlr.").append(this.grammar.getSuperClass()).toString();
        }
        println("");
        if (this.grammar.comment != null) {
            _println(this.grammar.comment);
        }
        Token token = (Token) this.grammar.options.get("classHeaderPrefix");
        if (token == null || (strStripFrontBack = StringUtils.stripFrontBack(token.getText(), "\"", "\"")) == null) {
            print("public ");
        } else {
            print(new StringBuffer().append(strStripFrontBack).append(" ").toString());
        }
        println(new StringBuffer(ExternalAnnotationProvider.CLASS_PREFIX).append(this.grammar.getClassName()).append(" : ").append(string).toString());
        Token token2 = (Token) this.grammar.options.get("classHeaderSuffix");
        if (token2 != null && (strStripFrontBack2 = StringUtils.stripFrontBack(token2.getText(), "\"", "\"")) != null) {
            print(new StringBuffer("              , ").append(strStripFrontBack2).toString());
        }
        println("{");
        this.tabs++;
        genTokenDefinitions(this.grammar.tokenManager);
        print(processActionForSpecialSymbols(this.grammar.classMemberAction.getText(), this.grammar.classMemberAction.getLine(), this.currentRule, null));
        println(new StringBuffer("public ").append(this.grammar.getClassName()).append("()").toString());
        println("{");
        this.tabs++;
        println("tokenNames = tokenNames_;");
        this.tabs--;
        println("}");
        println("");
        this.astTypes = new Vector();
        Enumeration enumerationElements = this.grammar.rules.elements();
        int i = 0;
        while (enumerationElements.hasMoreElements()) {
            GrammarSymbol grammarSymbol = (GrammarSymbol) enumerationElements.nextElement();
            if (grammarSymbol instanceof RuleSymbol) {
                RuleSymbol ruleSymbol = (RuleSymbol) grammarSymbol;
                genRule(ruleSymbol, ruleSymbol.references.size() == 0, i, this.grammar.tokenManager);
                i++;
            }
            exitIfError();
        }
        if (this.usingCustomAST) {
            println(new StringBuffer("public new ").append(this.labeledElementASTType).append(" getAST()").toString());
            println("{");
            this.tabs++;
            println(new StringBuffer("return (").append(this.labeledElementASTType).append(") returnAST;").toString());
            this.tabs--;
            println("}");
            println("");
        }
        genInitFactory(this.grammar);
        genTokenStrings();
        genBitsets(this.bitsetsUsed, this.grammar.tokenManager.maxTokenType());
        this.tabs--;
        println("}");
        println("");
        this.tabs--;
        CSharpNameSpace cSharpNameSpace2 = nameSpace;
        if (cSharpNameSpace2 != null) {
            cSharpNameSpace2.emitClosures(this.currentOutput);
        }
        this.currentOutput.close();
        this.currentOutput = null;
    }

    protected void genCases(BitSet bitSet) {
        if (this.DEBUG_CODE_GENERATOR) {
            System.out.println(new StringBuffer("genCases(").append(bitSet).append(Constant.AFTER_QUTO).toString());
        }
        int[] array = bitSet.toArray();
        int i = this.grammar instanceof LexerGrammar ? 4 : 1;
        boolean z = true;
        int i2 = 1;
        for (int i3 : array) {
            if (i2 == 1) {
                print("");
            } else {
                _print("  ");
            }
            _print(new StringBuffer("case ").append(getValueString(i3)).append(":").toString());
            if (i2 == i) {
                _println("");
                z = true;
                i2 = 1;
            } else {
                i2++;
                z = false;
            }
        }
        if (z) {
            return;
        }
        _println("");
    }

    /* JADX WARN: Code duplicated, block: B:162:0x0449  */
    public CSharpBlockFinishingInfo genCommonBlock(AlternativeBlock alternativeBlock, boolean z) {
        boolean z2;
        CSharpBlockFinishingInfo cSharpBlockFinishingInfo;
        boolean z3;
        boolean zLookaheadIsEmpty;
        String lookaheadTestExpression;
        CSharpBlockFinishingInfo cSharpBlockFinishingInfo2;
        int i;
        int i2;
        CSharpBlockFinishingInfo cSharpBlockFinishingInfo3 = new CSharpBlockFinishingInfo();
        if (this.DEBUG_CODE_GENERATOR) {
            System.out.println(new StringBuffer("genCommonBlock(").append(alternativeBlock).append(Constant.AFTER_QUTO).toString());
        }
        boolean z4 = this.genAST;
        this.genAST = z4 && alternativeBlock.getAutoGen();
        boolean z5 = this.saveText;
        this.saveText = z5 && alternativeBlock.getAutoGen();
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
            if (this.grammar instanceof TreeWalkerGrammar) {
                if (this.usingCustomAST) {
                    string = "(AST)_t,";
                } else {
                    string = "_t,";
                }
            }
            println(new StringBuffer("match(").append(string).append(getBitsetName(markBitsetForGen(lookaheadLook.fset))).append(");").toString());
            if (this.grammar instanceof TreeWalkerGrammar) {
                println("_t = _t.getNextSibling();");
            }
            return cSharpBlockFinishingInfo3;
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
                return cSharpBlockFinishingInfo3;
            }
        }
        int i3 = 0;
        for (int i4 = 0; i4 < alternativeBlock.getAlternatives().size(); i4++) {
            if (suitableForCaseExpression(alternativeBlock.getAlternativeAt(i4))) {
                i3++;
            }
        }
        if (i3 >= this.makeSwitchThreshold) {
            String strLookaheadString = lookaheadString(1);
            if (this.grammar instanceof TreeWalkerGrammar) {
                println("if (null == _t)");
                this.tabs++;
                println("_t = ASTNULL;");
                this.tabs--;
            }
            println(new StringBuffer("switch ( ").append(strLookaheadString).append(" )").toString());
            println("{");
            this.blockNestingLevel++;
            for (int i5 = 0; i5 < alternativeBlock.alternatives.size(); i5++) {
                Alternative alternativeAt2 = alternativeBlock.getAlternativeAt(i5);
                if (suitableForCaseExpression(alternativeAt2)) {
                    Lookahead lookahead = alternativeAt2.cache[1];
                    if (lookahead.fset.degree() == 0 && !lookahead.containsEpsilon()) {
                        this.antlrTool.warning("Alternate omitted due to empty prediction set", this.grammar.getFilename(), alternativeAt2.head.getLine(), alternativeAt2.head.getColumn());
                    } else {
                        genCases(lookahead.fset);
                        println("{");
                        this.tabs++;
                        this.blockNestingLevel++;
                        genAlt(alternativeAt2, alternativeBlock);
                        println("break;");
                        int i6 = this.blockNestingLevel;
                        this.blockNestingLevel = i6 - 1;
                        if (i6 == this.saveIndexCreateLevel) {
                            this.saveIndexCreateLevel = 0;
                        }
                        this.tabs--;
                        println("}");
                    }
                }
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
            if (this.DEBUG_CODE_GENERATOR) {
                System.out.println(new StringBuffer("checking depth ").append(i7).toString());
            }
            int i10 = 0;
            while (i10 < alternativeBlock.alternatives.size()) {
                Alternative alternativeAt3 = alternativeBlock.getAlternativeAt(i10);
                if (this.DEBUG_CODE_GENERATOR) {
                    System.out.println(new StringBuffer("genAlt: ").append(i10).toString());
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
                                System.out.println(new StringBuffer("ignoring alt because effectiveDepth!=altDepth;").append(i11).append("!=").append(i7).toString());
                            }
                        } else {
                            zLookaheadIsEmpty = lookaheadIsEmpty(alternativeAt3, i11);
                            lookaheadTestExpression = getLookaheadTestExpression(alternativeAt3, i11);
                        }
                    } else {
                        zLookaheadIsEmpty = lookaheadIsEmpty(alternativeAt3, this.grammar.maxk);
                        lookaheadTestExpression = getLookaheadTestExpression(alternativeAt3, this.grammar.maxk);
                    }
                    if (alternativeAt3.cache[1].fset.degree() <= 127 || !suitableForCaseExpression(alternativeAt3)) {
                        if (zLookaheadIsEmpty && alternativeAt3.semPred == null && alternativeAt3.synPred == null) {
                            if (i8 == 0) {
                                println("{");
                            } else {
                                println("else {");
                            }
                            cSharpBlockFinishingInfo3.needAnErrorClause = false;
                        } else {
                            if (alternativeAt3.semPred != null) {
                                cSharpBlockFinishingInfo2 = cSharpBlockFinishingInfo3;
                                i = i10;
                                String strProcessActionForSpecialSymbols = processActionForSpecialSymbols(alternativeAt3.semPred, alternativeBlock.line, this.currentRule, new ActionTransInfo());
                                if (((this.grammar instanceof ParserGrammar) || (this.grammar instanceof LexerGrammar)) && this.grammar.debuggingOutput) {
                                    lookaheadTestExpression = new StringBuffer("(").append(lookaheadTestExpression).append("&& fireSemanticPredicateEvaluated(antlr.debug.SemanticPredicateEventArgs.PREDICTING,").append(addSemPred(this.charFormatter.escapeString(strProcessActionForSpecialSymbols))).append(",").append(strProcessActionForSpecialSymbols).append("))").toString();
                                } else {
                                    lookaheadTestExpression = new StringBuffer("(").append(lookaheadTestExpression).append("&&(").append(strProcessActionForSpecialSymbols).append("))").toString();
                                }
                            } else {
                                cSharpBlockFinishingInfo2 = cSharpBlockFinishingInfo3;
                                i = i10;
                            }
                            if (i8 > 0) {
                                if (alternativeAt3.synPred != null) {
                                    println("else {");
                                    this.tabs++;
                                    this.blockNestingLevel++;
                                    genSynPred(alternativeAt3.synPred, lookaheadTestExpression);
                                    i9++;
                                } else {
                                    println(new StringBuffer("else if ").append(lookaheadTestExpression).append(" {").toString());
                                }
                            } else if (alternativeAt3.synPred != null) {
                                genSynPred(alternativeAt3.synPred, lookaheadTestExpression);
                            } else {
                                if (this.grammar instanceof TreeWalkerGrammar) {
                                    println("if (_t == null)");
                                    this.tabs++;
                                    println("_t = ASTNULL;");
                                    this.tabs--;
                                }
                                println(new StringBuffer("if ").append(lookaheadTestExpression).toString());
                                println("{");
                            }
                        }
                        this.blockNestingLevel++;
                        i8++;
                        this.tabs++;
                        genAlt(alternativeAt3, alternativeBlock);
                        this.tabs--;
                        i2 = this.blockNestingLevel;
                        this.blockNestingLevel = i2 - 1;
                        if (i2 == this.saveIndexCreateLevel) {
                            this.saveIndexCreateLevel = 0;
                        }
                        println("}");
                        i10 = i + 1;
                        z2 = z2;
                        string = string;
                        z5 = z5;
                        z4 = z4;
                        i7 = i7;
                        cSharpBlockFinishingInfo3 = cSharpBlockFinishingInfo2;
                    } else if (i8 == 0) {
                        println(new StringBuffer("if ").append(lookaheadTestExpression).toString());
                        println("{");
                    } else {
                        println(new StringBuffer("else if ").append(lookaheadTestExpression).toString());
                        println("{");
                    }
                    cSharpBlockFinishingInfo2 = cSharpBlockFinishingInfo3;
                    i = i10;
                    this.blockNestingLevel++;
                    i8++;
                    this.tabs++;
                    genAlt(alternativeAt3, alternativeBlock);
                    this.tabs--;
                    i2 = this.blockNestingLevel;
                    this.blockNestingLevel = i2 - 1;
                    if (i2 == this.saveIndexCreateLevel) {
                        this.saveIndexCreateLevel = 0;
                    }
                    println("}");
                    i10 = i + 1;
                    z2 = z2;
                    string = string;
                    z5 = z5;
                    z4 = z4;
                    i7 = i7;
                    cSharpBlockFinishingInfo3 = cSharpBlockFinishingInfo2;
                }
                cSharpBlockFinishingInfo2 = cSharpBlockFinishingInfo3;
                i = i10;
                i10 = i + 1;
                z2 = z2;
                string = string;
                z5 = z5;
                z4 = z4;
                i7 = i7;
                cSharpBlockFinishingInfo3 = cSharpBlockFinishingInfo2;
            }
            i7--;
        }
        CSharpBlockFinishingInfo cSharpBlockFinishingInfo4 = cSharpBlockFinishingInfo3;
        boolean z6 = z4;
        boolean z7 = z2;
        boolean z8 = z5;
        for (int i12 = 1; i12 <= i9; i12++) {
            string = new StringBuffer().append(string).append("}").toString();
            int i13 = this.blockNestingLevel;
            this.blockNestingLevel = i13 - 1;
            if (i13 == this.saveIndexCreateLevel) {
                this.saveIndexCreateLevel = 0;
            }
        }
        this.genAST = z6;
        this.saveText = z8;
        if (z7) {
            this.tabs--;
            cSharpBlockFinishingInfo = cSharpBlockFinishingInfo4;
            cSharpBlockFinishingInfo.postscript = new StringBuffer().append(string).append("break; }").toString();
            int i14 = this.blockNestingLevel;
            this.blockNestingLevel = i14 - 1;
            if (i14 == this.saveIndexCreateLevel) {
                z3 = false;
                this.saveIndexCreateLevel = 0;
            } else {
                z3 = false;
            }
            cSharpBlockFinishingInfo.generatedSwitch = true;
            cSharpBlockFinishingInfo.generatedAnIf = i8 > 0 ? true : z3;
        } else {
            cSharpBlockFinishingInfo = cSharpBlockFinishingInfo4;
            cSharpBlockFinishingInfo.postscript = string;
            cSharpBlockFinishingInfo.generatedSwitch = false;
            cSharpBlockFinishingInfo.generatedAnIf = i8 > 0;
        }
        return cSharpBlockFinishingInfo;
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
                    if (this.usingCustomAST || ((alternativeElement instanceof GrammarAtom) && ((GrammarAtom) alternativeElement).getASTNodeType() != null)) {
                        println(new StringBuffer("astFactory.addASTChild(ref currentAST, (AST)").append(string3).append(");").toString());
                        return;
                    } else {
                        println(new StringBuffer("astFactory.addASTChild(ref currentAST, ").append(string3).append(");").toString());
                        return;
                    }
                }
                if (autoGenType != 2) {
                    return;
                }
                if (this.usingCustomAST || ((alternativeElement instanceof GrammarAtom) && ((GrammarAtom) alternativeElement).getASTNodeType() != null)) {
                    println(new StringBuffer("astFactory.makeASTRoot(ref currentAST, (AST)").append(string3).append(");").toString());
                } else {
                    println(new StringBuffer("astFactory.makeASTRoot(ref currentAST, ").append(string3).append(");").toString());
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
            println("}");
            genErrorHandler(exceptionSpecFindExceptionSpec);
        }
    }

    private void genErrorHandler(ExceptionSpec exceptionSpec) {
        for (int i = 0; i < exceptionSpec.handlers.size(); i++) {
            ExceptionHandler exceptionHandler = (ExceptionHandler) exceptionSpec.handlers.elementAt(i);
            println(new StringBuffer("catch (").append(exceptionHandler.exceptionTypeAndName.getText()).append(Constant.AFTER_QUTO).toString());
            println("{");
            this.tabs++;
            if (this.grammar.hasSyntacticPredicate) {
                println("if (0 == inputState.guessing)");
                println("{");
                this.tabs++;
            }
            printAction(processActionForSpecialSymbols(exceptionHandler.action.getText(), exceptionHandler.action.getLine(), this.currentRule, new ActionTransInfo()));
            if (this.grammar.hasSyntacticPredicate) {
                this.tabs--;
                println("}");
                println(PsiKeyword.ELSE);
                println("{");
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
            println("try   // for error handling");
            println("{");
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
        println(new StringBuffer("// $ANTLR ").append(Tool.version).append(": \"").append(this.antlrTool.fileMinusPath(this.antlrTool.grammarFile)).append("\" -> \"").append(this.grammar.getClassName()).append(".cs\"$").toString());
    }

    private void genLiteralsTest() {
        println("_ttype = testLiteralsTable(_ttype);");
    }

    private void genLiteralsTestForPartialToken() {
        println("_ttype = testLiteralsTable(text.ToString(_begin, text.Length-_begin), _ttype);");
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
        } else if (this.usingCustomAST) {
            str = "(AST)_t,";
        } else {
            str = "_t,";
        }
        if ((this.grammar instanceof LexerGrammar) && (!this.saveText || grammarAtom.getAutoGenType() == 3)) {
            declareSaveIndexVariableIfNeeded();
            println("_saveIndex = text.Length;");
        }
        print(grammarAtom.not ? "matchNot(" : "match(");
        _print(str);
        if (grammarAtom.atomText.equals("EOF")) {
            _print("Token.EOF_TYPE");
        } else {
            _print(grammarAtom.atomText);
        }
        _println(");");
        if (this.grammar instanceof LexerGrammar) {
            if (!this.saveText || grammarAtom.getAutoGenType() == 3) {
                declareSaveIndexVariableIfNeeded();
                println("text.Length = _saveIndex;");
            }
        }
    }

    protected void genMatchUsingAtomTokenType(GrammarAtom grammarAtom) {
        String str;
        if (!(this.grammar instanceof TreeWalkerGrammar)) {
            str = "";
        } else if (this.usingCustomAST) {
            str = "(AST)_t,";
        } else {
            str = "_t,";
        }
        println(new StringBuffer().append(grammarAtom.not ? "matchNot(" : "match(").append(new StringBuffer().append(str).append(getValueString(grammarAtom.getType())).toString()).append(");").toString());
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
                println("override public IToken nextToken()\t\t\t//throws TokenStreamException");
                println("{");
                this.tabs++;
                this.blockNestingLevel = 1;
                this.saveIndexCreateLevel = 0;
                println("IToken theRetToken = null;");
                _println("tryAgain:");
                println("for (;;)");
                println("{");
                this.tabs++;
                println("IToken _token = null;");
                println("int _ttype = Token.INVALID_TYPE;");
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
                println("try     // for char stream error handling");
                println("{");
                this.tabs++;
                println("try     // for lexical error handling");
                println("{");
                this.tabs++;
                for (int i2 = 0; i2 < ruleBlockCreateNextTokenRule.getAlternatives().size(); i2++) {
                    Alternative alternativeAt = ruleBlockCreateNextTokenRule.getAlternativeAt(i2);
                    if (alternativeAt.cache[1].containsEpsilon()) {
                        this.antlrTool.warning(new StringBuffer("public lexical rule ").append(CodeGenerator.decodeLexerRuleName(((RuleRefElement) alternativeAt.head).targetRule)).append(" is optional (can match \"nothing\")").toString());
                    }
                }
                String property = System.getProperty(SystemProperties.LINE_SEPARATOR);
                CSharpBlockFinishingInfo cSharpBlockFinishingInfoGenCommonBlock = genCommonBlock(ruleBlockCreateNextTokenRule, false);
                String string2 = new StringBuffer("if (cached_LA1==EOF_CHAR) { uponEOF(); returnToken_ = makeToken(Token.EOF_TYPE); }").append(property).append("\t\t\t\t").toString();
                if (!((LexerGrammar) this.grammar).filterMode) {
                    string = new StringBuffer().append(string2).append("else {").append(this.throwNoViable).append("}").toString();
                } else if (str == null) {
                    string = new StringBuffer().append(new StringBuffer().append(new StringBuffer().append(new StringBuffer().append(new StringBuffer().append(string2).append("\t\t\t\telse").toString()).append("\t\t\t\t{").toString()).append("\t\t\t\t\tconsume();").toString()).append("\t\t\t\t\tgoto tryAgain;").toString()).append("\t\t\t\t}").toString();
                } else {
                    string = new StringBuffer().append(string2).append("\t\t\t\t\telse").append(property).append("\t\t\t\t\t{").append(property).append("\t\t\t\t\tcommit();").append(property).append("\t\t\t\t\ttry {m").append(str).append("(false);}").append(property).append("\t\t\t\t\tcatch(RecognitionException e)").append(property).append("\t\t\t\t\t{").append(property).append("\t\t\t\t\t\t// catastrophic failure").append(property).append("\t\t\t\t\t\treportError(e);").append(property).append("\t\t\t\t\t\tconsume();").append(property).append("\t\t\t\t\t}").append(property).append("\t\t\t\t\tgoto tryAgain;").append(property).append("\t\t\t\t}").toString();
                }
                genBlockFinish(cSharpBlockFinishingInfoGenCommonBlock, string);
                if (((LexerGrammar) this.grammar).filterMode && str != null) {
                    println("commit();");
                }
                println("if ( null==returnToken_ ) goto tryAgain; // found SKIP token");
                println("_ttype = returnToken_.Type;");
                if (((LexerGrammar) this.grammar).getTestLiterals()) {
                    genLiteralsTest();
                }
                println("returnToken_.Type = _ttype;");
                println("return returnToken_;");
                this.tabs--;
                println("}");
                println("catch (RecognitionException e) {");
                this.tabs++;
                if (((LexerGrammar) this.grammar).filterMode) {
                    if (str == null) {
                        println("if (!getCommitToPath())");
                        println("{");
                        this.tabs++;
                        println("consume();");
                        println("goto tryAgain;");
                        this.tabs--;
                        println("}");
                    } else {
                        println("if (!getCommitToPath())");
                        println("{");
                        this.tabs++;
                        println("rewind(_m);");
                        println("resetText();");
                        println(new StringBuffer("try {m").append(str).append("(false);}").toString());
                        println("catch(RecognitionException ee) {");
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
                    println("throw new TokenStreamRecognitionException(e);");
                    this.tabs--;
                }
                this.tabs--;
                println("}");
                this.tabs--;
                println("}");
                println("catch (CharStreamException cse) {");
                println("\tif ( cse is CharStreamIOException ) {");
                println("\t\tthrow new TokenStreamIOException(((CharStreamIOException)cse).io);");
                println("\t}");
                println("\telse {");
                println("\t\tthrow new TokenStreamException(cse.Message);");
                println("\t}");
                println("}");
                this.tabs--;
                println("}");
                this.tabs--;
                println("}");
                println("");
                return;
            }
        }
        println("");
        println("override public IToken nextToken()\t\t\t//throws TokenStreamException");
        println("{");
        this.tabs++;
        println(PsiKeyword.TRY);
        println("{");
        this.tabs++;
        println("uponEOF();");
        this.tabs--;
        println("}");
        println("catch(CharStreamIOException csioe)");
        println("{");
        this.tabs++;
        println("throw new TokenStreamIOException(csioe.io);");
        this.tabs--;
        println("}");
        println("catch(CharStreamException cse)");
        println("{");
        this.tabs++;
        println("throw new TokenStreamException(cse.Message);");
        this.tabs--;
        println("}");
        println("return new CommonToken(Token.EOF_TYPE, \"\");");
        this.tabs--;
        println("}");
        println("");
    }

    public void genRule(RuleSymbol ruleSymbol, boolean z, int i, TokenManager tokenManager) {
        int i2;
        int i3;
        this.tabs = 1;
        if (this.DEBUG_CODE_GENERATOR) {
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
        print(new StringBuffer().append(ruleSymbol.access).append(" ").toString());
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
        _print(new StringBuffer(" //throws ").append(this.exceptionThrown).toString());
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
        _println("");
        _println("{");
        this.tabs++;
        if (block.returnAction != null) {
            println(new StringBuffer().append(block.returnAction).append(";").toString());
        }
        println(this.commonLocalVars);
        String str2 = "\");";
        if (this.grammar.traceRules) {
            if (this.grammar instanceof TreeWalkerGrammar) {
                if (this.usingCustomAST) {
                    println(new StringBuffer("traceIn(\"").append(ruleSymbol.getId()).append("\",(AST)_t);").toString());
                } else {
                    println(new StringBuffer("traceIn(\"").append(ruleSymbol.getId()).append("\",_t);").toString());
                }
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
            this.blockNestingLevel = 1;
            this.saveIndexCreateLevel = 0;
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
            println(new StringBuffer().append(this.labeledElementASTType).append(" ").append(ruleSymbol.getId()).append("_AST_in = (").append(this.labeledElementASTType).append(")_t;").toString());
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
            this.tabs++;
        }
        if (block.alternatives.size() == 1) {
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
            z2 = z2;
            str = "";
            str2 = "\");";
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
            println(new StringBuffer("catch (").append(this.exceptionThrown).append(" ex)").toString());
            println("{");
            this.tabs++;
            if (this.grammar.hasSyntacticPredicate) {
                println("if (0 == inputState.guessing)");
                println("{");
                this.tabs++;
            }
            println("reportError(ex);");
            if (!(this.grammar instanceof TreeWalkerGrammar)) {
                println(new StringBuffer("recover(ex,").append(getBitsetName(markBitsetForGen(this.grammar.theLLkAnalyzer.FOLLOW(1, block.endNode).fset))).append(");").toString());
                i2 = 1;
            } else {
                println("if (null != _t)");
                println("{");
                i2 = 1;
                this.tabs++;
                println("_t = _t.getNextSibling();");
                this.tabs--;
                println("}");
            }
            if (this.grammar.hasSyntacticPredicate) {
                this.tabs -= i2;
                println("}");
                println(PsiKeyword.ELSE);
                println("{");
                this.tabs += i2;
                println("throw ex;");
                this.tabs -= i2;
                println("}");
            }
            this.tabs -= i2;
            println("}");
        }
        if (this.grammar.buildAST) {
            println(new StringBuffer("returnAST = ").append(ruleSymbol.getId()).append("_AST;").toString());
        }
        if (this.grammar instanceof TreeWalkerGrammar) {
            println("retTree_ = _t;");
        }
        if (block.getTestLiterals()) {
            if (ruleSymbol.access.equals("protected")) {
                genLiteralsTestForPartialToken();
            } else {
                genLiteralsTest();
            }
        }
        if (this.grammar instanceof LexerGrammar) {
            println("if (_createToken && (null == _token) && (_ttype != Token.SKIP))");
            println("{");
            this.tabs++;
            println("_token = makeToken(_ttype);");
            println("_token.setText(text.ToString(_begin, text.Length-_begin));");
            this.tabs--;
            println("}");
            println("returnToken_ = _token;");
        }
        if (block.returnAction != null) {
            println(new StringBuffer("return ").append(extractIdOfAction(block.returnAction, block.getLine(), block.getColumn())).append(";").toString());
        }
        if (this.grammar.debuggingOutput || this.grammar.traceRules) {
            this.tabs--;
            println("}");
            println(PsiKeyword.FINALLY);
            println("{ // debugging");
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
                    println(new StringBuffer("traceOut(\"").append(ruleSymbol.getId()).append("\",_t);").toString());
                } else {
                    println(new StringBuffer("traceOut(\"").append(ruleSymbol.getId()).append(str2).toString());
                }
            }
            i3 = 1;
            this.tabs--;
            println("}");
        } else {
            i3 = 1;
        }
        this.tabs -= i3;
        println("}");
        println(str);
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
            println("_t = retTree_;");
        }
    }

    protected void genSemPred(String str, int i) {
        String strProcessActionForSpecialSymbols = processActionForSpecialSymbols(str, i, this.currentRule, new ActionTransInfo());
        String strEscapeString = this.charFormatter.escapeString(strProcessActionForSpecialSymbols);
        if (this.grammar.debuggingOutput && ((this.grammar instanceof ParserGrammar) || (this.grammar instanceof LexerGrammar))) {
            strProcessActionForSpecialSymbols = new StringBuffer("fireSemanticPredicateEvaluated(antlr.debug.SemanticPredicateEvent.VALIDATING,").append(addSemPred(strEscapeString)).append(",").append(strProcessActionForSpecialSymbols).append(Constant.AFTER_QUTO).toString();
        }
        println(new StringBuffer("if (!(").append(strProcessActionForSpecialSymbols).append("))").toString());
        println(new StringBuffer("  throw new SemanticException(\"").append(strEscapeString).append("\");").toString());
    }

    protected void genSemPredMap() {
        Enumeration enumerationElements = this.semPreds.elements();
        println("private string[] _semPredNames = {");
        this.tabs++;
        while (enumerationElements.hasMoreElements()) {
            println(new StringBuffer("\"").append(enumerationElements.nextElement()).append("\",").toString());
        }
        this.tabs--;
        println("};");
    }

    protected void genSynPred(SynPredBlock synPredBlock, String str) {
        if (this.DEBUG_CODE_GENERATOR) {
            System.out.println(new StringBuffer("gen=>(").append(synPredBlock).append(Constant.AFTER_QUTO).toString());
        }
        println(new StringBuffer("bool synPredMatched").append(synPredBlock.ID).append(" = false;").toString());
        if (this.grammar instanceof TreeWalkerGrammar) {
            println("if (_t==null) _t=ASTNULL;");
        }
        println(new StringBuffer("if (").append(str).append(Constant.AFTER_QUTO).toString());
        println("{");
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
        println(new StringBuffer("catch (").append(this.exceptionThrown).append(Constant.AFTER_QUTO).toString());
        println("{");
        this.tabs++;
        println(new StringBuffer("synPredMatched").append(synPredBlock.ID).append(" = false;").toString());
        this.tabs--;
        println("}");
        if (this.grammar instanceof TreeWalkerGrammar) {
            println(new StringBuffer("_t = __t").append(synPredBlock.ID).append(";").toString());
        } else {
            println(new StringBuffer("rewind(_m").append(synPredBlock.ID).append(");").toString());
        }
        println("inputState.guessing--;");
        if (this.grammar.debuggingOutput && ((this.grammar instanceof ParserGrammar) || (this.grammar instanceof LexerGrammar))) {
            println(new StringBuffer("if (synPredMatched").append(synPredBlock.ID).append(Constant.AFTER_QUTO).toString());
            println("  fireSyntacticPredicateSucceeded();");
            println(PsiKeyword.ELSE);
            println("  fireSyntacticPredicateFailed();");
        }
        this.syntacticPredLevel--;
        this.tabs--;
        println("}");
        println(new StringBuffer("if ( synPredMatched").append(synPredBlock.ID).append(" )").toString());
        println("{");
    }

    public void genTokenStrings() {
        println("");
        println("public static readonly string[] tokenNames_ = new string[] {");
        this.tabs++;
        antlr.collections.impl.Vector vocabulary = this.grammar.tokenManager.getVocabulary();
        for (int i = 0; i < vocabulary.size(); i++) {
            String strStripFrontBack = (String) vocabulary.elementAt(i);
            if (strStripFrontBack == null) {
                strStripFrontBack = new StringBuffer("<").append(String.valueOf(i)).append(">").toString();
            }
            if (!strStripFrontBack.startsWith("\"") && !strStripFrontBack.startsWith("<")) {
                TokenSymbol tokenSymbol = this.grammar.tokenManager.getTokenSymbol(strStripFrontBack);
                if (tokenSymbol != null && tokenSymbol.getParaphrase() != null) {
                    strStripFrontBack = StringUtils.stripFrontBack(tokenSymbol.getParaphrase(), "\"", "\"");
                }
            } else if (strStripFrontBack.startsWith("\"")) {
                strStripFrontBack = StringUtils.stripFrontBack(strStripFrontBack, "\"", "\"");
            }
            print(this.charFormatter.literalString(strStripFrontBack));
            if (i != vocabulary.size() - 1) {
                _print(",");
            }
            _println("");
        }
        this.tabs--;
        println("};");
    }

    protected void genTokenTypes(TokenManager tokenManager) throws IOException {
        setupOutput(new StringBuffer().append(tokenManager.getName()).append(TokenTypesFileSuffix).toString());
        this.tabs = 0;
        genHeader();
        println(this.behavior.getHeaderAction(""));
        CSharpNameSpace cSharpNameSpace = nameSpace;
        if (cSharpNameSpace != null) {
            cSharpNameSpace.emitDeclarations(this.currentOutput);
        }
        this.tabs++;
        println(new StringBuffer("public class ").append(tokenManager.getName()).append(TokenTypesFileSuffix).toString());
        println("{");
        this.tabs++;
        genTokenDefinitions(tokenManager);
        this.tabs--;
        println("}");
        this.tabs--;
        CSharpNameSpace cSharpNameSpace2 = nameSpace;
        if (cSharpNameSpace2 != null) {
            cSharpNameSpace2.emitClosures(this.currentOutput);
        }
        this.currentOutput.close();
        this.currentOutput = null;
        exitIfError();
    }

    protected void genTokenDefinitions(TokenManager tokenManager) throws IOException {
        antlr.collections.impl.Vector vocabulary = tokenManager.getVocabulary();
        println("public const int EOF = 1;");
        println("public const int NULL_TREE_LOOKAHEAD = 3;");
        for (int i = 4; i < vocabulary.size(); i++) {
            String str = (String) vocabulary.elementAt(i);
            if (str != null) {
                if (str.startsWith("\"")) {
                    StringLiteralSymbol stringLiteralSymbol = (StringLiteralSymbol) tokenManager.getTokenSymbol(str);
                    if (stringLiteralSymbol == null) {
                        this.antlrTool.panic(new StringBuffer("String literal ").append(str).append(" not in symbol table").toString());
                    } else if (stringLiteralSymbol.label != null) {
                        println(new StringBuffer("public const int ").append(stringLiteralSymbol.label).append(" = ").append(i).append(";").toString());
                    } else {
                        String strMangleLiteral = mangleLiteral(str);
                        if (strMangleLiteral != null) {
                            println(new StringBuffer("public const int ").append(strMangleLiteral).append(" = ").append(i).append(";").toString());
                            stringLiteralSymbol.label = strMangleLiteral;
                        } else {
                            println(new StringBuffer("// ").append(str).append(" = ").append(i).toString());
                        }
                    }
                } else if (!str.startsWith("<")) {
                    println(new StringBuffer("public const int ").append(str).append(" = ").append(i).append(";").toString());
                }
            }
        }
        println("");
    }

    @Override // antlr.CodeGenerator
    public String processStringForASTConstructor(String str) {
        if (this.usingCustomAST) {
            return (((this.grammar instanceof TreeWalkerGrammar) || (this.grammar instanceof ParserGrammar)) && !this.grammar.tokenManager.tokenDefined(str)) ? new StringBuffer("(AST)").append(str).toString() : str;
        }
        return str;
    }

    @Override // antlr.CodeGenerator
    public String getASTCreateString(antlr.collections.impl.Vector vector) {
        if (vector.size() == 0) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(new StringBuffer("(").append(this.labeledElementASTType).append(") astFactory.make(").toString());
        stringBuffer.append(vector.elementAt(0));
        for (int i = 1; i < vector.size(); i++) {
            stringBuffer.append(new StringBuffer(", ").append(vector.elementAt(i)).toString());
        }
        stringBuffer.append(Constant.AFTER_QUTO);
        return stringBuffer.toString();
    }

    @Override // antlr.CodeGenerator
    public String getASTCreateString(GrammarAtom grammarAtom, String str) {
        String string = new StringBuffer("astFactory.create(").append(str).append(Constant.AFTER_QUTO).toString();
        if (grammarAtom == null) {
            return getASTCreateString(str);
        }
        if (grammarAtom.getASTNodeType() == null) {
            return this.usingCustomAST ? new StringBuffer("(").append(this.labeledElementASTType).append(") ").append(string).toString() : string;
        }
        TokenSymbol tokenSymbol = this.grammar.tokenManager.getTokenSymbol(grammarAtom.getText());
        if (tokenSymbol == null || tokenSymbol.getASTNodeType() != grammarAtom.getASTNodeType()) {
            return new StringBuffer("(").append(grammarAtom.getASTNodeType()).append(") astFactory.create(").append(str).append(", \"").append(grammarAtom.getASTNodeType()).append("\")").toString();
        }
        return (tokenSymbol == null || tokenSymbol.getASTNodeType() == null) ? string : new StringBuffer("(").append(tokenSymbol.getASTNodeType()).append(") ").append(string).toString();
    }

    public String getASTCreateString(String str) {
        if (str == null) {
            str = "";
        }
        String string = new StringBuffer("astFactory.create(").append(str).append(Constant.AFTER_QUTO).toString();
        int iIndexOf = str.indexOf(44);
        if (iIndexOf != -1) {
            String strSubstring = str.substring(0, iIndexOf);
            str.substring(iIndexOf + 1, str.length()).indexOf(44);
            str = strSubstring;
        }
        TokenSymbol tokenSymbol = this.grammar.tokenManager.getTokenSymbol(str);
        if (tokenSymbol == null || tokenSymbol.getASTNodeType() == null) {
            return this.usingCustomAST ? new StringBuffer("(").append(this.labeledElementASTType).append(") ").append(string).toString() : string;
        }
        return new StringBuffer("(").append(tokenSymbol.getASTNodeType()).append(") ").append(string).toString();
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
            return "_t.Type";
        }
        if (this.grammar instanceof LexerGrammar) {
            if (i == 1) {
                return "cached_LA1";
            }
            if (i == 2) {
                return "cached_LA2";
            }
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
        Token option2;
        String strStripFrontBack;
        Token option3;
        String strStripFrontBack2;
        boolean z = grammar instanceof ParserGrammar;
        if (z || (grammar instanceof LexerGrammar) || (grammar instanceof TreeWalkerGrammar)) {
            if (this.antlrTool.nameSpace != null) {
                nameSpace = new CSharpNameSpace(this.antlrTool.nameSpace.getName());
            }
            if (grammar.hasOption("namespace") && (option = grammar.getOption("namespace")) != null) {
                nameSpace = new CSharpNameSpace(option.getText());
            }
        }
        if (z) {
            this.labeledElementASTType = "AST";
            if (grammar.hasOption("ASTLabelType") && (option3 = grammar.getOption("ASTLabelType")) != null && (strStripFrontBack2 = StringUtils.stripFrontBack(option3.getText(), "\"", "\"")) != null) {
                this.usingCustomAST = true;
                this.labeledElementASTType = strStripFrontBack2;
            }
            this.labeledElementType = "IToken ";
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
            this.commonExtraParams = "bool _createToken";
            this.commonLocalVars = "int _ttype; IToken _token=null; int _begin=text.Length;";
            this.lt1Value = "cached_LA1";
            this.exceptionThrown = "RecognitionException";
            this.throwNoViable = "throw new NoViableAltForCharException(cached_LA1, getFilename(), getLine(), getColumn());";
            return;
        }
        if (grammar instanceof TreeWalkerGrammar) {
            this.labeledElementASTType = "AST";
            this.labeledElementType = "AST";
            if (grammar.hasOption("ASTLabelType") && (option2 = grammar.getOption("ASTLabelType")) != null && (strStripFrontBack = StringUtils.stripFrontBack(option2.getText(), "\"", "\"")) != null) {
                this.usingCustomAST = true;
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
            if (this.usingCustomAST) {
                this.lt1Value = new StringBuffer("(_t==ASTNULL) ? null : (").append(this.labeledElementASTType).append(")_t").toString();
            } else {
                this.lt1Value = "_t";
            }
            this.exceptionThrown = "RecognitionException";
            this.throwNoViable = "throw new NoViableAltException(_t);";
            return;
        }
        this.antlrTool.panic("Unknown grammar type");
    }

    public void setupOutput(String str) throws IOException {
        this.currentOutput = this.antlrTool.openOutputFile(new StringBuffer().append(str).append(".cs").toString());
    }

    private static String OctalToUnicode(String str) {
        if (4 > str.length() || '\'' != str.charAt(0) || '\\' != str.charAt(1) || '0' > str.charAt(2) || '7' < str.charAt(2) || '\'' != str.charAt(str.length() - 1)) {
            return str;
        }
        return new StringBuffer("'\\x").append(Integer.toHexString(Integer.valueOf(str.substring(2, str.length() - 1), 8).intValue())).append(FormatHelper.QUOTE).toString();
    }

    public String getTokenTypesClassName() {
        return new String(new StringBuffer().append(this.grammar.tokenManager.getName()).append(TokenTypesFileSuffix).toString());
    }

    private void declareSaveIndexVariableIfNeeded() {
        if (this.saveIndexCreateLevel == 0) {
            println("int _saveIndex = 0;");
            this.saveIndexCreateLevel = this.blockNestingLevel;
        }
    }

    public String[] split(String str, String str2) {
        StringTokenizer stringTokenizer = new StringTokenizer(str, str2);
        String[] strArr = new String[stringTokenizer.countTokens()];
        int i = 0;
        while (stringTokenizer.hasMoreTokens()) {
            strArr[i] = stringTokenizer.nextToken();
            i++;
        }
        return strArr;
    }
}
