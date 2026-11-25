package org.antlr.runtime.tree;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.antlr.runtime.BaseRecognizer;
import org.antlr.runtime.BitSet;
import org.antlr.runtime.CommonToken;
import org.antlr.runtime.IntStream;
import org.antlr.runtime.MismatchedTreeNodeException;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;
import org.eclipse.jgit.transport.WalkEncryption;

/* JADX INFO: loaded from: classes12.dex */
public class TreeParser extends BaseRecognizer {
    public static final int DOWN = 2;
    public static final int UP = 3;
    static String dotdot = ".*[^.]\\.\\.[^.].*";
    static Pattern dotdotPattern = Pattern.compile(".*[^.]\\.\\.[^.].*");
    static String doubleEtc = ".*\\.\\.\\.\\s+\\.\\.\\..*";
    static Pattern doubleEtcPattern = Pattern.compile(doubleEtc);
    protected TreeNodeStream input;

    public TreeParser(TreeNodeStream treeNodeStream) {
        setTreeNodeStream(treeNodeStream);
    }

    public TreeParser(TreeNodeStream treeNodeStream, RecognizerSharedState recognizerSharedState) {
        super(recognizerSharedState);
        setTreeNodeStream(treeNodeStream);
    }

    @Override // org.antlr.runtime.BaseRecognizer
    public void reset() {
        super.reset();
        TreeNodeStream treeNodeStream = this.input;
        if (treeNodeStream != null) {
            treeNodeStream.seek(0);
        }
    }

    public void setTreeNodeStream(TreeNodeStream treeNodeStream) {
        this.input = treeNodeStream;
    }

    public TreeNodeStream getTreeNodeStream() {
        return this.input;
    }

    @Override // org.antlr.runtime.BaseRecognizer, org.antlr.runtime.TokenSource
    public String getSourceName() {
        return this.input.getSourceName();
    }

    @Override // org.antlr.runtime.BaseRecognizer
    protected Object getCurrentInputSymbol(IntStream intStream) {
        return ((TreeNodeStream) intStream).LT(1);
    }

    @Override // org.antlr.runtime.BaseRecognizer
    protected Object getMissingSymbol(IntStream intStream, RecognitionException recognitionException, int i, BitSet bitSet) {
        return ((TreeNodeStream) recognitionException.input).getTreeAdaptor().create(new CommonToken(i, "<missing " + getTokenNames()[i] + ">"));
    }

    @Override // org.antlr.runtime.BaseRecognizer
    public void matchAny(IntStream intStream) {
        int i = 0;
        this.state.errorRecovery = false;
        this.state.failed = false;
        Object objLT = this.input.LT(1);
        if (this.input.getTreeAdaptor().getChildCount(objLT) == 0) {
            this.input.consume();
            return;
        }
        int type = this.input.getTreeAdaptor().getType(objLT);
        while (type != -1 && (type != 3 || i != 0)) {
            this.input.consume();
            type = this.input.getTreeAdaptor().getType(this.input.LT(1));
            if (type == 2) {
                i++;
            } else if (type == 3) {
                i--;
            }
        }
        this.input.consume();
    }

    @Override // org.antlr.runtime.BaseRecognizer
    protected Object recoverFromMismatchedToken(IntStream intStream, int i, BitSet bitSet) throws RecognitionException {
        throw new MismatchedTreeNodeException(i, (TreeNodeStream) intStream);
    }

    @Override // org.antlr.runtime.BaseRecognizer
    public String getErrorHeader(RecognitionException recognitionException) {
        return getGrammarFileName() + ": node from " + (recognitionException.approximateLineInfo ? "after " : "") + "line " + recognitionException.line + ":" + recognitionException.charPositionInLine;
    }

    @Override // org.antlr.runtime.BaseRecognizer
    public String getErrorMessage(RecognitionException recognitionException, String[] strArr) {
        if (this instanceof TreeParser) {
            TreeAdaptor treeAdaptor = ((TreeNodeStream) recognitionException.input).getTreeAdaptor();
            recognitionException.token = treeAdaptor.getToken(recognitionException.node);
            if (recognitionException.token == null) {
                recognitionException.token = new CommonToken(treeAdaptor.getType(recognitionException.node), treeAdaptor.getText(recognitionException.node));
            }
        }
        return super.getErrorMessage(recognitionException, strArr);
    }

    public boolean inContext(String str) {
        return inContext(this.input.getTreeAdaptor(), getTokenNames(), this.input.LT(1), str);
    }

    public static boolean inContext(TreeAdaptor treeAdaptor, String[] strArr, Object obj, String str) {
        Matcher matcher = dotdotPattern.matcher(str);
        Matcher matcher2 = doubleEtcPattern.matcher(str);
        if (matcher.find()) {
            throw new IllegalArgumentException("invalid syntax: ..");
        }
        if (matcher2.find()) {
            throw new IllegalArgumentException("invalid syntax: ... ...");
        }
        String[] strArrSplit = str.replaceAll("\\.\\.\\.", " ... ").trim().split(WalkEncryption.Vals.REGEX_WS);
        int length = strArrSplit.length - 1;
        Object parent = treeAdaptor.getParent(obj);
        while (length >= 0 && parent != null) {
            if (strArrSplit[length].equals("...")) {
                if (length == 0) {
                    return true;
                }
                parent = getAncestor(treeAdaptor, strArr, parent, strArrSplit[length - 1]);
                if (parent == null) {
                    return false;
                }
                length--;
            }
            if (!strArr[treeAdaptor.getType(parent)].equals(strArrSplit[length])) {
                return false;
            }
            length--;
            parent = treeAdaptor.getParent(parent);
        }
        return parent != null || length < 0;
    }

    protected static Object getAncestor(TreeAdaptor treeAdaptor, String[] strArr, Object obj, String str) {
        while (obj != null) {
            if (strArr[treeAdaptor.getType(obj)].equals(str)) {
                return obj;
            }
            obj = treeAdaptor.getParent(obj);
        }
        return null;
    }

    public void traceIn(String str, int i) {
        super.traceIn(str, i, this.input.LT(1));
    }

    public void traceOut(String str, int i) {
        super.traceOut(str, i, this.input.LT(1));
    }
}
