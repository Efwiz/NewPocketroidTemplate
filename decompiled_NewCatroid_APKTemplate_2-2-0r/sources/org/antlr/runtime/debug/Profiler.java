package org.antlr.runtime.debug;

import com.beust.jcommander.Parameters;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import org.antlr.runtime.IntStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenStream;
import org.antlr.runtime.misc.DoubleKeyMap;
import org.apache.commons.lang3.SystemProperties;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.cli.common.arguments.PreprocessCommandLineArgumentsKt;

/* JADX INFO: loaded from: classes12.dex */
public class Profiler extends BlankDebugEventListener {
    public static final String DATA_SEP = "\t";
    public static final String RUNTIME_STATS_FILENAME = "runtime.stats";
    public static final String Version = "3";
    protected int backtrackDepth;
    protected Stack<String> currentGrammarFileName;
    protected Stack<Integer> currentLine;
    protected Stack<Integer> currentPos;
    protected Stack<String> currentRuleName;
    protected List<DecisionEvent> decisionEvents;
    protected Stack<DecisionEvent> decisionStack;
    protected DoubleKeyMap<String, Integer, DecisionDescriptor> decisions;
    protected Token lastRealTokenTouchedInDecision;
    public DebugParser parser;
    protected int ruleLevel;
    ProfileStats stats;
    protected Set<String> uniqueRules;
    public static final String newline = System.getProperty(SystemProperties.LINE_SEPARATOR);
    static boolean dump = false;

    public static class DecisionDescriptor {
        public float avgk;
        public boolean couldBacktrack;
        public int decision;
        public String fileName;
        public int line;
        public int maxk;
        public int n;
        public int numBacktrackOccurrences;
        public int numSemPredEvals;
        public int pos;
        public String ruleName;
    }

    public static class DecisionEvent {
        public boolean backtracks;
        public DecisionDescriptor decision;
        public boolean evalSemPred;
        public int k;
        public int numMemoizationCacheHits;
        public int numMemoizationCacheMisses;
        public int startIndex;
        public long startTime;
        public long stopTime;
    }

    public static class ProfileStats {
        public String Version;
        public float averageDecisionPercentBacktracks;
        public int avgDecisionMaxCyclicLookaheads;
        public int avgDecisionMaxFixedLookaheads;
        public float avgkPerBacktrackingDecisionEvent;
        public float avgkPerDecisionEvent;
        public int maxDecisionMaxCyclicLookaheads;
        public int maxDecisionMaxFixedLookaheads;
        public int maxRuleInvocationDepth;
        public int minDecisionMaxCyclicLookaheads;
        public int minDecisionMaxFixedLookaheads;

        /* JADX INFO: renamed from: name, reason: collision with root package name */
        public String f424name;
        public int numBacktrackOccurrences;
        public int numCharsMatched;
        public int numCyclicDecisions;
        public int numDecisionEvents;
        public int numDecisionsCovered;
        public int numDecisionsThatDoBacktrack;
        public int numDecisionsThatPotentiallyBacktrack;
        public int numFixedDecisions;
        public int numGuessingRuleInvocations;
        public int numHiddenCharsMatched;
        public int numHiddenTokens;
        public int numMemoizationCacheEntries;
        public int numMemoizationCacheHits;
        public int numMemoizationCacheMisses;
        public int numReportedErrors;
        public int numRuleInvocations;
        public int numSemanticPredicates;
        public int numTokens;
        public int numUniqueRulesInvoked;
        public int stddevDecisionMaxCyclicLookaheads;
        public int stddevDecisionMaxFixedLookaheads;
    }

    public Profiler() {
        this.parser = null;
        this.ruleLevel = 0;
        this.uniqueRules = new HashSet();
        this.currentGrammarFileName = new Stack<>();
        this.currentRuleName = new Stack<>();
        this.currentLine = new Stack<>();
        this.currentPos = new Stack<>();
        this.decisions = new DoubleKeyMap<>();
        this.decisionEvents = new ArrayList();
        this.decisionStack = new Stack<>();
        this.stats = new ProfileStats();
    }

    public Profiler(DebugParser debugParser) {
        this.parser = null;
        this.ruleLevel = 0;
        this.uniqueRules = new HashSet();
        this.currentGrammarFileName = new Stack<>();
        this.currentRuleName = new Stack<>();
        this.currentLine = new Stack<>();
        this.currentPos = new Stack<>();
        this.decisions = new DoubleKeyMap<>();
        this.decisionEvents = new ArrayList();
        this.decisionStack = new Stack<>();
        this.stats = new ProfileStats();
        this.parser = debugParser;
    }

    @Override // org.antlr.runtime.debug.BlankDebugEventListener, org.antlr.runtime.debug.DebugEventListener
    public void enterRule(String str, String str2) {
        this.ruleLevel++;
        this.stats.numRuleInvocations++;
        this.uniqueRules.add(str + ":" + str2);
        ProfileStats profileStats = this.stats;
        profileStats.maxRuleInvocationDepth = Math.max(profileStats.maxRuleInvocationDepth, this.ruleLevel);
        this.currentGrammarFileName.push(str);
        this.currentRuleName.push(str2);
    }

    @Override // org.antlr.runtime.debug.BlankDebugEventListener, org.antlr.runtime.debug.DebugEventListener
    public void exitRule(String str, String str2) {
        this.ruleLevel--;
        this.currentGrammarFileName.pop();
        this.currentRuleName.pop();
    }

    public void examineRuleMemoization(IntStream intStream, int i, int i2, String str) {
        if (dump) {
            System.out.println("examine memo " + str + " at " + intStream.index() + ": " + i2);
        }
        if (i2 == -1) {
            this.stats.numMemoizationCacheMisses++;
            this.stats.numGuessingRuleInvocations++;
            currentDecision().numMemoizationCacheMisses++;
            return;
        }
        this.stats.numMemoizationCacheHits++;
        currentDecision().numMemoizationCacheHits++;
    }

    public void memoize(IntStream intStream, int i, int i2, String str) {
        if (dump) {
            System.out.println("memoize " + str);
        }
        this.stats.numMemoizationCacheEntries++;
    }

    @Override // org.antlr.runtime.debug.BlankDebugEventListener, org.antlr.runtime.debug.DebugEventListener
    public void location(int i, int i2) {
        this.currentLine.push(Integer.valueOf(i));
        this.currentPos.push(Integer.valueOf(i2));
    }

    @Override // org.antlr.runtime.debug.BlankDebugEventListener, org.antlr.runtime.debug.DebugEventListener
    public void enterDecision(int i, boolean z) {
        this.lastRealTokenTouchedInDecision = null;
        this.stats.numDecisionEvents++;
        int iIndex = this.parser.getTokenStream().index();
        TokenStream tokenStream = this.parser.getTokenStream();
        if (dump) {
            System.out.println("enterDecision canBacktrack=" + z + " " + i + " backtrack depth " + this.backtrackDepth + " @ " + tokenStream.get(tokenStream.index()) + " rule " + locationDescription());
        }
        String strPeek = this.currentGrammarFileName.peek();
        DecisionDescriptor decisionDescriptor = this.decisions.get(strPeek, Integer.valueOf(i));
        if (decisionDescriptor == null) {
            decisionDescriptor = new DecisionDescriptor();
            this.decisions.put(strPeek, Integer.valueOf(i), decisionDescriptor);
            decisionDescriptor.decision = i;
            decisionDescriptor.fileName = this.currentGrammarFileName.peek();
            decisionDescriptor.ruleName = this.currentRuleName.peek();
            decisionDescriptor.line = this.currentLine.peek().intValue();
            decisionDescriptor.pos = this.currentPos.peek().intValue();
            decisionDescriptor.couldBacktrack = z;
        }
        decisionDescriptor.n++;
        DecisionEvent decisionEvent = new DecisionEvent();
        this.decisionStack.push(decisionEvent);
        decisionEvent.decision = decisionDescriptor;
        decisionEvent.startTime = System.currentTimeMillis();
        decisionEvent.startIndex = iIndex;
    }

    @Override // org.antlr.runtime.debug.BlankDebugEventListener, org.antlr.runtime.debug.DebugEventListener
    public void exitDecision(int i) {
        DecisionEvent decisionEventPop = this.decisionStack.pop();
        decisionEventPop.stopTime = System.currentTimeMillis();
        int tokenIndex = this.lastRealTokenTouchedInDecision.getTokenIndex();
        int numberOfHiddenTokens = ((tokenIndex - decisionEventPop.startIndex) - getNumberOfHiddenTokens(decisionEventPop.startIndex, tokenIndex)) + 1;
        decisionEventPop.k = numberOfHiddenTokens;
        decisionEventPop.decision.maxk = Math.max(decisionEventPop.decision.maxk, numberOfHiddenTokens);
        if (dump) {
            System.out.println("exitDecision " + i + " in " + decisionEventPop.decision.ruleName + " lookahead " + decisionEventPop.k + " max token " + this.lastRealTokenTouchedInDecision);
        }
        this.decisionEvents.add(decisionEventPop);
    }

    @Override // org.antlr.runtime.debug.BlankDebugEventListener, org.antlr.runtime.debug.DebugEventListener
    public void consumeToken(Token token) {
        if (dump) {
            System.out.println("consume token " + token);
        }
        if (!inDecision()) {
            this.stats.numTokens++;
            return;
        }
        Token token2 = this.lastRealTokenTouchedInDecision;
        if (token2 == null || token2.getTokenIndex() < token.getTokenIndex()) {
            this.lastRealTokenTouchedInDecision = token;
        }
        DecisionEvent decisionEventCurrentDecision = currentDecision();
        int tokenIndex = token.getTokenIndex();
        int numberOfHiddenTokens = ((tokenIndex - decisionEventCurrentDecision.startIndex) - getNumberOfHiddenTokens(decisionEventCurrentDecision.startIndex, tokenIndex)) + 1;
        if (dump) {
            System.out.println("consume " + tokenIndex + " " + numberOfHiddenTokens + " tokens ahead in " + decisionEventCurrentDecision.decision.ruleName + Parameters.DEFAULT_OPTION_PREFIXES + decisionEventCurrentDecision.decision.decision + " start index " + decisionEventCurrentDecision.startIndex);
        }
    }

    public boolean inDecision() {
        return this.decisionStack.size() > 0;
    }

    @Override // org.antlr.runtime.debug.BlankDebugEventListener, org.antlr.runtime.debug.DebugEventListener
    public void consumeHiddenToken(Token token) {
        if (inDecision()) {
            return;
        }
        this.stats.numHiddenTokens++;
    }

    @Override // org.antlr.runtime.debug.BlankDebugEventListener, org.antlr.runtime.debug.DebugEventListener
    public void LT(int i, Token token) {
        if (!inDecision() || i <= 0) {
            return;
        }
        DecisionEvent decisionEventCurrentDecision = currentDecision();
        if (dump) {
            System.out.println("LT(" + i + ")=" + token + " index " + token.getTokenIndex() + " relative to " + decisionEventCurrentDecision.decision.ruleName + Parameters.DEFAULT_OPTION_PREFIXES + decisionEventCurrentDecision.decision.decision + " start index " + decisionEventCurrentDecision.startIndex);
        }
        Token token2 = this.lastRealTokenTouchedInDecision;
        if (token2 == null || token2.getTokenIndex() < token.getTokenIndex()) {
            this.lastRealTokenTouchedInDecision = token;
            if (dump) {
                System.out.println("set last token " + this.lastRealTokenTouchedInDecision);
            }
        }
    }

    @Override // org.antlr.runtime.debug.BlankDebugEventListener, org.antlr.runtime.debug.DebugEventListener
    public void beginBacktrack(int i) {
        if (dump) {
            System.out.println("enter backtrack " + i);
        }
        this.backtrackDepth++;
        DecisionEvent decisionEventCurrentDecision = currentDecision();
        if (decisionEventCurrentDecision.decision.couldBacktrack) {
            this.stats.numBacktrackOccurrences++;
            decisionEventCurrentDecision.decision.numBacktrackOccurrences++;
            decisionEventCurrentDecision.backtracks = true;
        }
    }

    @Override // org.antlr.runtime.debug.BlankDebugEventListener, org.antlr.runtime.debug.DebugEventListener
    public void endBacktrack(int i, boolean z) {
        if (dump) {
            System.out.println("exit backtrack " + i + ": " + z);
        }
        this.backtrackDepth--;
    }

    @Override // org.antlr.runtime.debug.BlankDebugEventListener, org.antlr.runtime.debug.DebugEventListener
    public void mark(int i) {
        if (dump) {
            System.out.println("mark " + i);
        }
    }

    @Override // org.antlr.runtime.debug.BlankDebugEventListener, org.antlr.runtime.debug.DebugEventListener
    public void rewind(int i) {
        if (dump) {
            System.out.println("rewind " + i);
        }
    }

    @Override // org.antlr.runtime.debug.BlankDebugEventListener, org.antlr.runtime.debug.DebugEventListener
    public void rewind() {
        if (dump) {
            System.out.println("rewind");
        }
    }

    protected DecisionEvent currentDecision() {
        return this.decisionStack.peek();
    }

    @Override // org.antlr.runtime.debug.BlankDebugEventListener, org.antlr.runtime.debug.DebugEventListener
    public void recognitionException(RecognitionException recognitionException) {
        this.stats.numReportedErrors++;
    }

    @Override // org.antlr.runtime.debug.BlankDebugEventListener, org.antlr.runtime.debug.DebugEventListener
    public void semanticPredicate(boolean z, String str) {
        this.stats.numSemanticPredicates++;
        if (inDecision()) {
            DecisionEvent decisionEventCurrentDecision = currentDecision();
            decisionEventCurrentDecision.evalSemPred = true;
            decisionEventCurrentDecision.decision.numSemPredEvals++;
            if (dump) {
                System.out.println("eval " + str + " in " + decisionEventCurrentDecision.decision.ruleName + Parameters.DEFAULT_OPTION_PREFIXES + decisionEventCurrentDecision.decision.decision);
            }
        }
    }

    @Override // org.antlr.runtime.debug.BlankDebugEventListener, org.antlr.runtime.debug.DebugEventListener
    public void terminate() {
        for (DecisionEvent decisionEvent : this.decisionEvents) {
            decisionEvent.decision.avgk += decisionEvent.k;
            this.stats.avgkPerDecisionEvent += decisionEvent.k;
            if (decisionEvent.backtracks) {
                this.stats.avgkPerBacktrackingDecisionEvent += decisionEvent.k;
            }
        }
        this.stats.averageDecisionPercentBacktracks = 0.0f;
        for (DecisionDescriptor decisionDescriptor : this.decisions.values()) {
            this.stats.numDecisionsCovered++;
            decisionDescriptor.avgk = (float) (((double) decisionDescriptor.avgk) / ((double) decisionDescriptor.n));
            if (decisionDescriptor.couldBacktrack) {
                this.stats.numDecisionsThatPotentiallyBacktrack++;
                this.stats.averageDecisionPercentBacktracks += decisionDescriptor.numBacktrackOccurrences / decisionDescriptor.n;
            }
            if (decisionDescriptor.numBacktrackOccurrences > 0) {
                this.stats.numDecisionsThatDoBacktrack++;
            }
        }
        this.stats.averageDecisionPercentBacktracks /= this.stats.numDecisionsThatPotentiallyBacktrack;
        this.stats.averageDecisionPercentBacktracks *= 100.0f;
        this.stats.avgkPerDecisionEvent /= this.stats.numDecisionEvents;
        ProfileStats profileStats = this.stats;
        profileStats.avgkPerBacktrackingDecisionEvent = (float) (((double) profileStats.avgkPerBacktrackingDecisionEvent) / ((double) this.stats.numBacktrackOccurrences));
        System.err.println(toString());
        System.err.println(getDecisionStatsDump());
    }

    public void setParser(DebugParser debugParser) {
        this.parser = debugParser;
    }

    public String toNotifyString() {
        return "3\t" + this.parser.getClass().getName();
    }

    public String toString() {
        return toString(getReport());
    }

    public ProfileStats getReport() {
        this.stats.Version = "3";
        this.stats.f424name = this.parser.getClass().getName();
        this.stats.numUniqueRulesInvoked = this.uniqueRules.size();
        return this.stats;
    }

    public DoubleKeyMap<String, Integer, DecisionDescriptor> getDecisionStats() {
        return this.decisions;
    }

    public List<DecisionEvent> getDecisionEvents() {
        return this.decisionEvents;
    }

    public static String toString(ProfileStats profileStats) {
        StringBuilder sb = new StringBuilder("ANTLR Runtime Report; Profile Version ");
        sb.append(profileStats.Version);
        String str = newline;
        sb.append(str);
        sb.append("parser name ");
        sb.append(profileStats.f424name);
        sb.append(str);
        sb.append("Number of rule invocations ");
        sb.append(profileStats.numRuleInvocations);
        sb.append(str);
        sb.append("Number of unique rules visited ");
        sb.append(profileStats.numUniqueRulesInvoked);
        sb.append(str);
        sb.append("Number of decision events ");
        sb.append(profileStats.numDecisionEvents);
        sb.append(str);
        sb.append("Overall average k per decision event ");
        sb.append(profileStats.avgkPerDecisionEvent);
        sb.append(str);
        sb.append("Number of backtracking occurrences (can be multiple per decision) ");
        sb.append(profileStats.numBacktrackOccurrences);
        sb.append(str);
        sb.append("Overall average k per decision event that backtracks ");
        sb.append(profileStats.avgkPerBacktrackingDecisionEvent);
        sb.append(str);
        sb.append("Number of rule invocations while backtracking ");
        sb.append(profileStats.numGuessingRuleInvocations);
        sb.append(str);
        sb.append("num decisions that potentially backtrack ");
        sb.append(profileStats.numDecisionsThatPotentiallyBacktrack);
        sb.append(str);
        sb.append("num decisions that do backtrack ");
        sb.append(profileStats.numDecisionsThatDoBacktrack);
        sb.append(str);
        sb.append("num decisions that potentially backtrack but don't ");
        sb.append(profileStats.numDecisionsThatPotentiallyBacktrack - profileStats.numDecisionsThatDoBacktrack);
        sb.append(str);
        sb.append("average % of time a potentially backtracking decision backtracks ");
        sb.append(profileStats.averageDecisionPercentBacktracks);
        sb.append(str);
        sb.append("num unique decisions covered ");
        sb.append(profileStats.numDecisionsCovered);
        sb.append(str);
        sb.append("max rule invocation nesting depth ");
        sb.append(profileStats.maxRuleInvocationDepth);
        sb.append(str);
        sb.append("rule memoization cache size ");
        sb.append(profileStats.numMemoizationCacheEntries);
        sb.append(str);
        sb.append("number of rule memoization cache hits ");
        sb.append(profileStats.numMemoizationCacheHits);
        sb.append(str);
        sb.append("number of rule memoization cache misses ");
        sb.append(profileStats.numMemoizationCacheMisses);
        sb.append(str);
        sb.append("number of tokens ");
        sb.append(profileStats.numTokens);
        sb.append(str);
        sb.append("number of hidden tokens ");
        sb.append(profileStats.numHiddenTokens);
        sb.append(str);
        sb.append("number of char ");
        sb.append(profileStats.numCharsMatched);
        sb.append(str);
        sb.append("number of hidden char ");
        sb.append(profileStats.numHiddenCharsMatched);
        sb.append(str);
        sb.append("number of syntax errors ");
        sb.append(profileStats.numReportedErrors);
        sb.append(str);
        return sb.toString();
    }

    public String getDecisionStatsDump() {
        StringBuilder sb = new StringBuilder("location\tn\tavgk\tmaxk\tsynpred\tsempred\tcanbacktrack\n");
        for (String str : this.decisions.keySet()) {
            Iterator<Integer> it = this.decisions.keySet(str).iterator();
            while (it.hasNext()) {
                DecisionDescriptor decisionDescriptor = this.decisions.get(str, Integer.valueOf(it.next().intValue()));
                sb.append(decisionDescriptor.decision);
                sb.append(PreprocessCommandLineArgumentsKt.ARGFILE_ARGUMENT);
                sb.append(locationDescription(decisionDescriptor.fileName, decisionDescriptor.ruleName, decisionDescriptor.line, decisionDescriptor.pos));
                sb.append("\t");
                sb.append(decisionDescriptor.n);
                sb.append("\t");
                sb.append(String.format("%.2f", Float.valueOf(decisionDescriptor.avgk)));
                sb.append("\t");
                sb.append(decisionDescriptor.maxk);
                sb.append("\t");
                sb.append(decisionDescriptor.numBacktrackOccurrences);
                sb.append("\t");
                sb.append(decisionDescriptor.numSemPredEvals);
                sb.append("\t");
                sb.append(decisionDescriptor.couldBacktrack ? SchemaSymbols.ATTVAL_TRUE_1 : "0");
                sb.append(newline);
            }
        }
        return sb.toString();
    }

    protected int[] trim(int[] iArr, int i) {
        if (i >= iArr.length) {
            return iArr;
        }
        int[] iArr2 = new int[i];
        System.arraycopy(iArr, 0, iArr2, 0, i);
        return iArr2;
    }

    protected int[] toArray(List<Integer> list) {
        int[] iArr = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            iArr[i] = list.get(i).intValue();
        }
        return iArr;
    }

    public int getNumberOfHiddenTokens(int i, int i2) {
        TokenStream tokenStream = this.parser.getTokenStream();
        int i3 = 0;
        while (i < tokenStream.size() && i <= i2) {
            if (tokenStream.get(i).getChannel() != 0) {
                i3++;
            }
            i++;
        }
        return i3;
    }

    protected String locationDescription() {
        return locationDescription(this.currentGrammarFileName.peek(), this.currentRuleName.peek(), this.currentLine.peek().intValue(), this.currentPos.peek().intValue());
    }

    protected String locationDescription(String str, String str2, int i, int i2) {
        return str + ":" + i + ":" + i2 + "(" + str2 + Constant.AFTER_QUTO;
    }
}
