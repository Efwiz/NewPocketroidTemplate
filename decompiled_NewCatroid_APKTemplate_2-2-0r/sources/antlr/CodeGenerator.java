package antlr;

import antlr.collections.impl.BitSet;
import antlr.collections.impl.Vector;
import com.huawei.hms.framework.common.ContainerUtils;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.IOException;
import java.io.PrintWriter;

/* JADX INFO: loaded from: classes.dex */
public abstract class CodeGenerator {
    protected static final int BITSET_OPTIMIZE_INIT_THRESHOLD = 8;
    protected static final int DEFAULT_BITSET_TEST_THRESHOLD = 4;
    protected static final int DEFAULT_MAKE_SWITCH_THRESHOLD = 2;
    private static boolean OLD_ACTION_TRANSLATOR = true;
    public static String TokenTypesFileExt = ".txt";
    public static String TokenTypesFileSuffix = "TokenTypes";
    protected LLkGrammarAnalyzer analyzer;
    protected Tool antlrTool;
    protected DefineGrammarSymbols behavior;
    protected Vector bitsetsUsed;
    protected CharFormatter charFormatter;
    protected transient PrintWriter currentOutput;
    protected int tabs = 0;
    protected Grammar grammar = null;
    protected boolean DEBUG_CODE_GENERATOR = false;
    protected int makeSwitchThreshold = 2;
    protected int bitsetTestThreshold = 4;

    public abstract void gen();

    public abstract void gen(ActionElement actionElement);

    public abstract void gen(AlternativeBlock alternativeBlock);

    public abstract void gen(BlockEndElement blockEndElement);

    public abstract void gen(CharLiteralElement charLiteralElement);

    public abstract void gen(CharRangeElement charRangeElement);

    public abstract void gen(LexerGrammar lexerGrammar) throws IOException;

    public abstract void gen(OneOrMoreBlock oneOrMoreBlock);

    public abstract void gen(ParserGrammar parserGrammar) throws IOException;

    public abstract void gen(RuleRefElement ruleRefElement);

    public abstract void gen(StringLiteralElement stringLiteralElement);

    public abstract void gen(TokenRangeElement tokenRangeElement);

    public abstract void gen(TokenRefElement tokenRefElement);

    public abstract void gen(TreeElement treeElement);

    public abstract void gen(TreeWalkerGrammar treeWalkerGrammar) throws IOException;

    public abstract void gen(WildcardElement wildcardElement);

    public abstract void gen(ZeroOrMoreBlock zeroOrMoreBlock);

    public abstract String getASTCreateString(GrammarAtom grammarAtom, String str);

    public abstract String getASTCreateString(Vector vector);

    public abstract String mapTreeId(String str, ActionTransInfo actionTransInfo);

    protected abstract String processActionForSpecialSymbols(String str, int i, RuleBlock ruleBlock, ActionTransInfo actionTransInfo);

    public String processStringForASTConstructor(String str) {
        return str;
    }

    protected void _print(String str) {
        if (str != null) {
            this.currentOutput.print(str);
        }
    }

    protected void _printAction(String str) {
        boolean z;
        if (str == null) {
            return;
        }
        int i = 0;
        while (i < str.length() && Character.isSpaceChar(str.charAt(i))) {
            i++;
        }
        int length = str.length() - 1;
        while (length > i && Character.isSpaceChar(str.charAt(length))) {
            length--;
        }
        while (i <= length) {
            char cCharAt = str.charAt(i);
            int i2 = i + 1;
            if (cCharAt == '\n') {
                z = true;
            } else if (cCharAt == '\r') {
                if (i2 <= length && str.charAt(i2) == '\n') {
                    i2 = i + 2;
                }
                z = true;
            } else {
                this.currentOutput.print(cCharAt);
                z = false;
            }
            i = i2;
            if (z) {
                this.currentOutput.println();
                printTabs();
                while (i <= length && Character.isSpaceChar(str.charAt(i))) {
                    i++;
                }
            }
        }
        this.currentOutput.println();
    }

    protected void _println(String str) {
        if (str != null) {
            this.currentOutput.println(str);
        }
    }

    public static boolean elementsAreRange(int[] iArr) {
        if (iArr.length == 0) {
            return false;
        }
        int i = iArr[0];
        int i2 = iArr[iArr.length - 1];
        if (iArr.length <= 2 || (i2 - i) + 1 > iArr.length) {
            return false;
        }
        int i3 = i + 1;
        for (int i4 = 1; i4 < iArr.length - 1; i4++) {
            if (i3 != iArr[i4]) {
                return false;
            }
            i3++;
        }
        return true;
    }

    protected String extractIdOfAction(Token token) {
        return extractIdOfAction(token.getText(), token.getLine(), token.getColumn());
    }

    protected String extractIdOfAction(String str, int i, int i2) {
        String strRemoveAssignmentFromDeclaration = removeAssignmentFromDeclaration(str);
        for (int length = strRemoveAssignmentFromDeclaration.length() - 2; length >= 0; length--) {
            if (!Character.isLetterOrDigit(strRemoveAssignmentFromDeclaration.charAt(length)) && strRemoveAssignmentFromDeclaration.charAt(length) != '_') {
                return strRemoveAssignmentFromDeclaration.substring(length + 1);
            }
        }
        this.antlrTool.warning("Ill-formed action", this.grammar.getFilename(), i, i2);
        return "";
    }

    protected String extractTypeOfAction(Token token) {
        return extractTypeOfAction(token.getText(), token.getLine(), token.getColumn());
    }

    protected String extractTypeOfAction(String str, int i, int i2) {
        String strRemoveAssignmentFromDeclaration = removeAssignmentFromDeclaration(str);
        for (int length = strRemoveAssignmentFromDeclaration.length() - 2; length >= 0; length--) {
            if (!Character.isLetterOrDigit(strRemoveAssignmentFromDeclaration.charAt(length)) && strRemoveAssignmentFromDeclaration.charAt(length) != '_') {
                return strRemoveAssignmentFromDeclaration.substring(0, length + 1);
            }
        }
        this.antlrTool.warning("Ill-formed action", this.grammar.getFilename(), i, i2);
        return "";
    }

    protected void genTokenInterchange(TokenManager tokenManager) throws IOException {
        String string = new StringBuffer().append(tokenManager.getName()).append(TokenTypesFileSuffix).append(TokenTypesFileExt).toString();
        this.currentOutput = this.antlrTool.openOutputFile(string);
        StringBuffer stringBufferAppend = new StringBuffer("// $ANTLR ").append(Tool.version).append(": ");
        Tool tool = this.antlrTool;
        println(stringBufferAppend.append(tool.fileMinusPath(tool.grammarFile)).append(" -> ").append(string).append("$").toString());
        this.tabs = 0;
        println(new StringBuffer().append(tokenManager.getName()).append("    // output token vocab name").toString());
        Vector vocabulary = tokenManager.getVocabulary();
        for (int i = 4; i < vocabulary.size(); i++) {
            String str = (String) vocabulary.elementAt(i);
            if (this.DEBUG_CODE_GENERATOR) {
                System.out.println(new StringBuffer("gen persistence file entry for: ").append(str).toString());
            }
            if (str != null && !str.startsWith("<")) {
                if (str.startsWith("\"")) {
                    StringLiteralSymbol stringLiteralSymbol = (StringLiteralSymbol) tokenManager.getTokenSymbol(str);
                    if (stringLiteralSymbol != null && stringLiteralSymbol.label != null) {
                        print(new StringBuffer().append(stringLiteralSymbol.label).append(ContainerUtils.KEY_VALUE_DELIMITER).toString());
                    }
                    println(new StringBuffer().append(str).append(ContainerUtils.KEY_VALUE_DELIMITER).append(i).toString());
                } else {
                    print(str);
                    TokenSymbol tokenSymbol = tokenManager.getTokenSymbol(str);
                    if (tokenSymbol == null) {
                        this.antlrTool.warning(new StringBuffer("undefined token symbol: ").append(str).toString());
                    } else if (tokenSymbol.getParaphrase() != null) {
                        print(new StringBuffer("(").append(tokenSymbol.getParaphrase()).append(Constant.AFTER_QUTO).toString());
                    }
                    println(new StringBuffer(ContainerUtils.KEY_VALUE_DELIMITER).append(i).toString());
                }
            }
        }
        this.currentOutput.close();
        this.currentOutput = null;
    }

    protected String getBitsetName(int i) {
        return new StringBuffer("_tokenSet_").append(i).toString();
    }

    public static String encodeLexerRuleName(String str) {
        return new StringBuffer("m").append(str).toString();
    }

    public static String decodeLexerRuleName(String str) {
        if (str == null) {
            return null;
        }
        return str.substring(1, str.length());
    }

    protected int markBitsetForGen(BitSet bitSet) {
        for (int i = 0; i < this.bitsetsUsed.size(); i++) {
            if (bitSet.equals((BitSet) this.bitsetsUsed.elementAt(i))) {
                return i;
            }
        }
        this.bitsetsUsed.appendElement(bitSet.clone());
        return this.bitsetsUsed.size() - 1;
    }

    protected void print(String str) {
        if (str != null) {
            printTabs();
            this.currentOutput.print(str);
        }
    }

    protected void printAction(String str) {
        if (str != null) {
            printTabs();
            _printAction(str);
        }
    }

    protected void println(String str) {
        if (str != null) {
            printTabs();
            this.currentOutput.println(str);
        }
    }

    protected void printTabs() {
        for (int i = 1; i <= this.tabs; i++) {
            this.currentOutput.print("\t");
        }
    }

    public String getFOLLOWBitSet(String str, int i) {
        GrammarSymbol symbol = this.grammar.getSymbol(str);
        if (!(symbol instanceof RuleSymbol)) {
            return null;
        }
        return getBitsetName(markBitsetForGen(this.grammar.theLLkAnalyzer.FOLLOW(i, ((RuleSymbol) symbol).getBlock().endNode).fset));
    }

    public String getFIRSTBitSet(String str, int i) {
        GrammarSymbol symbol = this.grammar.getSymbol(str);
        if (!(symbol instanceof RuleSymbol)) {
            return null;
        }
        return getBitsetName(markBitsetForGen(this.grammar.theLLkAnalyzer.look(i, ((RuleSymbol) symbol).getBlock()).fset));
    }

    protected String removeAssignmentFromDeclaration(String str) {
        return str.indexOf(61) >= 0 ? str.substring(0, str.indexOf(61)).trim() : str;
    }

    private void reset() {
        this.tabs = 0;
        this.bitsetsUsed = new Vector();
        this.currentOutput = null;
        this.grammar = null;
        this.DEBUG_CODE_GENERATOR = false;
        this.makeSwitchThreshold = 2;
        this.bitsetTestThreshold = 4;
    }

    public static String reverseLexerRuleName(String str) {
        return str.substring(1, str.length());
    }

    public void setAnalyzer(LLkGrammarAnalyzer lLkGrammarAnalyzer) {
        this.analyzer = lLkGrammarAnalyzer;
    }

    public void setBehavior(DefineGrammarSymbols defineGrammarSymbols) {
        this.behavior = defineGrammarSymbols;
    }

    protected void setGrammar(Grammar grammar) {
        reset();
        this.grammar = grammar;
        if (grammar.hasOption("codeGenMakeSwitchThreshold")) {
            try {
                this.makeSwitchThreshold = this.grammar.getIntegerOption("codeGenMakeSwitchThreshold");
            } catch (NumberFormatException unused) {
                Token option = this.grammar.getOption("codeGenMakeSwitchThreshold");
                this.antlrTool.error("option 'codeGenMakeSwitchThreshold' must be an integer", this.grammar.getClassName(), option.getLine(), option.getColumn());
            }
        }
        if (this.grammar.hasOption("codeGenBitsetTestThreshold")) {
            try {
                this.bitsetTestThreshold = this.grammar.getIntegerOption("codeGenBitsetTestThreshold");
            } catch (NumberFormatException unused2) {
                Token option2 = this.grammar.getOption("codeGenBitsetTestThreshold");
                this.antlrTool.error("option 'codeGenBitsetTestThreshold' must be an integer", this.grammar.getClassName(), option2.getLine(), option2.getColumn());
            }
        }
        if (this.grammar.hasOption("codeGenDebug")) {
            Token option3 = this.grammar.getOption("codeGenDebug");
            if (option3.getText().equals("true")) {
                this.DEBUG_CODE_GENERATOR = true;
            } else if (option3.getText().equals("false")) {
                this.DEBUG_CODE_GENERATOR = false;
            } else {
                this.antlrTool.error("option 'codeGenDebug' must be true or false", this.grammar.getClassName(), option3.getLine(), option3.getColumn());
            }
        }
    }

    public void setTool(Tool tool) {
        this.antlrTool = tool;
    }
}
