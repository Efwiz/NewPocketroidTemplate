package org.antlr.runtime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import name.antonsmirnov.firmata.FormatHelper;

/* JADX INFO: loaded from: classes12.dex */
public abstract class BaseRecognizer {
    public static final int DEFAULT_TOKEN_CHANNEL = 0;
    public static final int HIDDEN = 99;
    public static final int INITIAL_FOLLOW_STACK_SIZE = 100;
    public static final int MEMO_RULE_FAILED = -2;
    public static final int MEMO_RULE_UNKNOWN = -1;
    public static final String NEXT_TOKEN_RULE_NAME = "nextToken";
    protected RecognizerSharedState state;

    public void beginResync() {
    }

    public void endResync() {
    }

    protected Object getCurrentInputSymbol(IntStream intStream) {
        return null;
    }

    public String getGrammarFileName() {
        return null;
    }

    protected Object getMissingSymbol(IntStream intStream, RecognitionException recognitionException, int i, BitSet bitSet) {
        return null;
    }

    public abstract String getSourceName();

    public String[] getTokenNames() {
        return null;
    }

    public BaseRecognizer() {
        this.state = new RecognizerSharedState();
    }

    public BaseRecognizer(RecognizerSharedState recognizerSharedState) {
        this.state = recognizerSharedState == null ? new RecognizerSharedState() : recognizerSharedState;
    }

    public void reset() {
        RecognizerSharedState recognizerSharedState = this.state;
        if (recognizerSharedState == null) {
            return;
        }
        recognizerSharedState._fsp = -1;
        this.state.errorRecovery = false;
        this.state.lastErrorIndex = -1;
        this.state.failed = false;
        this.state.syntaxErrors = 0;
        this.state.backtracking = 0;
        for (int i = 0; this.state.ruleMemo != null && i < this.state.ruleMemo.length; i++) {
            this.state.ruleMemo[i] = null;
        }
    }

    public Object match(IntStream intStream, int i, BitSet bitSet) throws RecognitionException {
        Object currentInputSymbol = getCurrentInputSymbol(intStream);
        if (intStream.LA(1) == i) {
            intStream.consume();
            this.state.errorRecovery = false;
            this.state.failed = false;
            return currentInputSymbol;
        }
        if (this.state.backtracking > 0) {
            this.state.failed = true;
            return currentInputSymbol;
        }
        return recoverFromMismatchedToken(intStream, i, bitSet);
    }

    public void matchAny(IntStream intStream) {
        this.state.errorRecovery = false;
        this.state.failed = false;
        intStream.consume();
    }

    public boolean mismatchIsUnwantedToken(IntStream intStream, int i) {
        return intStream.LA(2) == i;
    }

    public boolean mismatchIsMissingToken(IntStream intStream, BitSet bitSet) {
        if (bitSet == null) {
            return false;
        }
        if (bitSet.member(1)) {
            bitSet = bitSet.or(computeContextSensitiveRuleFOLLOW());
            if (this.state._fsp >= 0) {
                bitSet.remove(1);
            }
        }
        return bitSet.member(intStream.LA(1)) || bitSet.member(1);
    }

    public void reportError(RecognitionException recognitionException) {
        if (this.state.errorRecovery) {
            return;
        }
        this.state.syntaxErrors++;
        this.state.errorRecovery = true;
        displayRecognitionError(getTokenNames(), recognitionException);
    }

    public void displayRecognitionError(String[] strArr, RecognitionException recognitionException) {
        emitErrorMessage(getErrorHeader(recognitionException) + " " + getErrorMessage(recognitionException, strArr));
    }

    public String getErrorMessage(RecognitionException recognitionException, String[] strArr) {
        String message = recognitionException.getMessage();
        if (recognitionException instanceof UnwantedTokenException) {
            UnwantedTokenException unwantedTokenException = (UnwantedTokenException) recognitionException;
            return "extraneous input " + getTokenErrorDisplay(unwantedTokenException.getUnexpectedToken()) + " expecting " + (unwantedTokenException.expecting != -1 ? strArr[unwantedTokenException.expecting] : "EOF");
        }
        if (recognitionException instanceof MissingTokenException) {
            MissingTokenException missingTokenException = (MissingTokenException) recognitionException;
            return "missing " + (missingTokenException.expecting != -1 ? strArr[missingTokenException.expecting] : "EOF") + " at " + getTokenErrorDisplay(recognitionException.token);
        }
        if (recognitionException instanceof MismatchedTokenException) {
            MismatchedTokenException mismatchedTokenException = (MismatchedTokenException) recognitionException;
            return "mismatched input " + getTokenErrorDisplay(recognitionException.token) + " expecting " + (mismatchedTokenException.expecting != -1 ? strArr[mismatchedTokenException.expecting] : "EOF");
        }
        if (recognitionException instanceof MismatchedTreeNodeException) {
            MismatchedTreeNodeException mismatchedTreeNodeException = (MismatchedTreeNodeException) recognitionException;
            return "mismatched tree node: " + mismatchedTreeNodeException.node + " expecting " + (mismatchedTreeNodeException.expecting != -1 ? strArr[mismatchedTreeNodeException.expecting] : "EOF");
        }
        if (recognitionException instanceof NoViableAltException) {
            return "no viable alternative at input " + getTokenErrorDisplay(recognitionException.token);
        }
        if (recognitionException instanceof EarlyExitException) {
            return "required (...)+ loop did not match anything at input " + getTokenErrorDisplay(recognitionException.token);
        }
        if (recognitionException instanceof MismatchedSetException) {
            return "mismatched input " + getTokenErrorDisplay(recognitionException.token) + " expecting set " + ((MismatchedSetException) recognitionException).expecting;
        }
        if (recognitionException instanceof MismatchedNotSetException) {
            return "mismatched input " + getTokenErrorDisplay(recognitionException.token) + " expecting set " + ((MismatchedNotSetException) recognitionException).expecting;
        }
        if (!(recognitionException instanceof FailedPredicateException)) {
            return message;
        }
        FailedPredicateException failedPredicateException = (FailedPredicateException) recognitionException;
        return "rule " + failedPredicateException.ruleName + " failed predicate: {" + failedPredicateException.predicateText + "}?";
    }

    public int getNumberOfSyntaxErrors() {
        return this.state.syntaxErrors;
    }

    public String getErrorHeader(RecognitionException recognitionException) {
        if (getSourceName() != null) {
            return getSourceName() + " line " + recognitionException.line + ":" + recognitionException.charPositionInLine;
        }
        return "line " + recognitionException.line + ":" + recognitionException.charPositionInLine;
    }

    public String getTokenErrorDisplay(Token token) {
        String text = token.getText();
        if (text == null) {
            if (token.getType() == -1) {
                text = "<EOF>";
            } else {
                text = "<" + token.getType() + ">";
            }
        }
        return FormatHelper.QUOTE + text.replaceAll("\n", "\\\\n").replaceAll("\r", "\\\\r").replaceAll("\t", "\\\\t") + FormatHelper.QUOTE;
    }

    public void emitErrorMessage(String str) {
        System.err.println(str);
    }

    public void recover(IntStream intStream, RecognitionException recognitionException) {
        if (this.state.lastErrorIndex == intStream.index()) {
            intStream.consume();
        }
        this.state.lastErrorIndex = intStream.index();
        BitSet bitSetComputeErrorRecoverySet = computeErrorRecoverySet();
        beginResync();
        consumeUntil(intStream, bitSetComputeErrorRecoverySet);
        endResync();
    }

    protected BitSet computeErrorRecoverySet() {
        return combineFollows(false);
    }

    protected BitSet computeContextSensitiveRuleFOLLOW() {
        return combineFollows(true);
    }

    protected BitSet combineFollows(boolean z) {
        BitSet bitSet = new BitSet();
        for (int i = this.state._fsp; i >= 0; i--) {
            BitSet bitSet2 = this.state.following[i];
            bitSet.orInPlace(bitSet2);
            if (z) {
                if (!bitSet2.member(1)) {
                    break;
                }
                if (i > 0) {
                    bitSet.remove(1);
                }
            }
        }
        return bitSet;
    }

    protected Object recoverFromMismatchedToken(IntStream intStream, int i, BitSet bitSet) throws RecognitionException {
        if (mismatchIsUnwantedToken(intStream, i)) {
            UnwantedTokenException unwantedTokenException = new UnwantedTokenException(i, intStream);
            beginResync();
            intStream.consume();
            endResync();
            reportError(unwantedTokenException);
            Object currentInputSymbol = getCurrentInputSymbol(intStream);
            intStream.consume();
            return currentInputSymbol;
        }
        if (mismatchIsMissingToken(intStream, bitSet)) {
            Object missingSymbol = getMissingSymbol(intStream, null, i, bitSet);
            reportError(new MissingTokenException(i, intStream, missingSymbol));
            return missingSymbol;
        }
        throw new MismatchedTokenException(i, intStream);
    }

    public Object recoverFromMismatchedSet(IntStream intStream, RecognitionException recognitionException, BitSet bitSet) throws RecognitionException {
        if (mismatchIsMissingToken(intStream, bitSet)) {
            reportError(recognitionException);
            return getMissingSymbol(intStream, recognitionException, 0, bitSet);
        }
        throw recognitionException;
    }

    public void consumeUntil(IntStream intStream, int i) {
        int iLA = intStream.LA(1);
        while (iLA != -1 && iLA != i) {
            intStream.consume();
            iLA = intStream.LA(1);
        }
    }

    public void consumeUntil(IntStream intStream, BitSet bitSet) {
        int iLA = intStream.LA(1);
        while (iLA != -1 && !bitSet.member(iLA)) {
            intStream.consume();
            iLA = intStream.LA(1);
        }
    }

    protected void pushFollow(BitSet bitSet) {
        if (this.state._fsp + 1 >= this.state.following.length) {
            BitSet[] bitSetArr = new BitSet[this.state.following.length * 2];
            System.arraycopy(this.state.following, 0, bitSetArr, 0, this.state.following.length);
            this.state.following = bitSetArr;
        }
        BitSet[] bitSetArr2 = this.state.following;
        RecognizerSharedState recognizerSharedState = this.state;
        int i = recognizerSharedState._fsp + 1;
        recognizerSharedState._fsp = i;
        bitSetArr2[i] = bitSet;
    }

    public List<String> getRuleInvocationStack() {
        return getRuleInvocationStack(new Throwable(), getClass().getName());
    }

    public static List<String> getRuleInvocationStack(Throwable th, String str) {
        ArrayList arrayList = new ArrayList();
        StackTraceElement[] stackTrace = th.getStackTrace();
        for (int length = stackTrace.length - 1; length >= 0; length--) {
            StackTraceElement stackTraceElement = stackTrace[length];
            if (!stackTraceElement.getClassName().startsWith("org.antlr.runtime.") && !stackTraceElement.getMethodName().equals(NEXT_TOKEN_RULE_NAME) && stackTraceElement.getClassName().equals(str)) {
                arrayList.add(stackTraceElement.getMethodName());
            }
        }
        return arrayList;
    }

    public int getBacktrackingLevel() {
        return this.state.backtracking;
    }

    public void setBacktrackingLevel(int i) {
        this.state.backtracking = i;
    }

    public boolean failed() {
        return this.state.failed;
    }

    public List<String> toStrings(List<? extends Token> list) {
        if (list == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList(list.size());
        for (int i = 0; i < list.size(); i++) {
            arrayList.add(list.get(i).getText());
        }
        return arrayList;
    }

    public int getRuleMemoization(int i, int i2) {
        if (this.state.ruleMemo[i] == null) {
            this.state.ruleMemo[i] = new HashMap();
        }
        Integer num = this.state.ruleMemo[i].get(Integer.valueOf(i2));
        if (num == null) {
            return -1;
        }
        return num.intValue();
    }

    public boolean alreadyParsedRule(IntStream intStream, int i) {
        int ruleMemoization = getRuleMemoization(i, intStream.index());
        if (ruleMemoization == -1) {
            return false;
        }
        if (ruleMemoization == -2) {
            this.state.failed = true;
        } else {
            intStream.seek(ruleMemoization + 1);
        }
        return true;
    }

    public void memoize(IntStream intStream, int i, int i2) {
        int iIndex = this.state.failed ? -2 : intStream.index() - 1;
        if (this.state.ruleMemo == null) {
            System.err.println("!!!!!!!!! memo array is null for " + getGrammarFileName());
        }
        if (i >= this.state.ruleMemo.length) {
            System.err.println("!!!!!!!!! memo size is " + this.state.ruleMemo.length + ", but rule index is " + i);
        }
        if (this.state.ruleMemo[i] != null) {
            this.state.ruleMemo[i].put(Integer.valueOf(i2), Integer.valueOf(iIndex));
        }
    }

    public int getRuleMemoizationCacheSize() {
        int size = 0;
        for (int i = 0; this.state.ruleMemo != null && i < this.state.ruleMemo.length; i++) {
            Map<Integer, Integer> map = this.state.ruleMemo[i];
            if (map != null) {
                size += map.size();
            }
        }
        return size;
    }

    public void traceIn(String str, int i, Object obj) {
        System.out.print("enter " + str + " " + obj);
        if (this.state.backtracking > 0) {
            System.out.print(" backtracking=" + this.state.backtracking);
        }
        System.out.println();
    }

    public void traceOut(String str, int i, Object obj) {
        System.out.print("exit " + str + " " + obj);
        if (this.state.backtracking > 0) {
            System.out.print(" backtracking=" + this.state.backtracking);
            if (this.state.failed) {
                System.out.print(" failed");
            } else {
                System.out.print(" succeeded");
            }
        }
        System.out.println();
    }
}
