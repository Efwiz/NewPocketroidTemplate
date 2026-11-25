package antlr;

import antlr.collections.impl.Vector;

/* JADX INFO: loaded from: classes.dex */
class AlternativeBlock extends AlternativeElement {
    protected static int nblks;
    protected int ID;
    protected Vector alternatives;
    protected int alti;
    protected int altj;
    protected int analysisAlt;
    protected boolean doAutoGen;
    protected boolean generateAmbigWarnings;
    boolean greedy;
    boolean greedySet;
    protected boolean hasASynPred;
    protected boolean hasAnAction;
    protected String initAction;
    protected String label;
    boolean not;
    protected boolean warnWhenFollowAmbig;

    public AlternativeBlock(Grammar grammar) {
        super(grammar);
        this.initAction = null;
        this.hasAnAction = false;
        this.hasASynPred = false;
        this.ID = 0;
        this.not = false;
        this.greedy = true;
        this.greedySet = false;
        this.doAutoGen = true;
        this.warnWhenFollowAmbig = true;
        this.generateAmbigWarnings = true;
        this.alternatives = new Vector(5);
        this.not = false;
        int i = nblks + 1;
        nblks = i;
        this.ID = i;
    }

    public AlternativeBlock(Grammar grammar, Token token, boolean z) {
        super(grammar, token);
        this.initAction = null;
        this.hasAnAction = false;
        this.hasASynPred = false;
        this.ID = 0;
        this.not = false;
        this.greedy = true;
        this.greedySet = false;
        this.doAutoGen = true;
        this.warnWhenFollowAmbig = true;
        this.generateAmbigWarnings = true;
        this.alternatives = new Vector(5);
        this.not = z;
        int i = nblks + 1;
        nblks = i;
        this.ID = i;
    }

    public void addAlternative(Alternative alternative) {
        this.alternatives.appendElement(alternative);
    }

    @Override // antlr.GrammarElement
    public void generate() {
        this.grammar.generator.gen(this);
    }

    public Alternative getAlternativeAt(int i) {
        return (Alternative) this.alternatives.elementAt(i);
    }

    public Vector getAlternatives() {
        return this.alternatives;
    }

    public boolean getAutoGen() {
        return this.doAutoGen;
    }

    public String getInitAction() {
        return this.initAction;
    }

    @Override // antlr.AlternativeElement
    public String getLabel() {
        return this.label;
    }

    @Override // antlr.GrammarElement
    public Lookahead look(int i) {
        return this.grammar.theLLkAnalyzer.look(i, this);
    }

    public void prepareForAnalysis() {
        for (int i = 0; i < this.alternatives.size(); i++) {
            Alternative alternative = (Alternative) this.alternatives.elementAt(i);
            alternative.cache = new Lookahead[this.grammar.maxk + 1];
            alternative.lookaheadDepth = -1;
        }
    }

    public void removeTrackingOfRuleRefs(Grammar grammar) {
        for (int i = 0; i < this.alternatives.size(); i++) {
            for (AlternativeElement alternativeElement = getAlternativeAt(i).head; alternativeElement != null; alternativeElement = alternativeElement.next) {
                if (alternativeElement instanceof RuleRefElement) {
                    RuleRefElement ruleRefElement = (RuleRefElement) alternativeElement;
                    RuleSymbol ruleSymbol = (RuleSymbol) grammar.getSymbol(ruleRefElement.targetRule);
                    if (ruleSymbol == null) {
                        this.grammar.antlrTool.error(new StringBuffer("rule ").append(ruleRefElement.targetRule).append(" referenced in (...)=>, but not defined").toString());
                    } else {
                        ruleSymbol.references.removeElement(ruleRefElement);
                    }
                } else if (alternativeElement instanceof AlternativeBlock) {
                    ((AlternativeBlock) alternativeElement).removeTrackingOfRuleRefs(grammar);
                }
            }
        }
    }

    public void setAlternatives(Vector vector) {
        this.alternatives = vector;
    }

    public void setAutoGen(boolean z) {
        this.doAutoGen = z;
    }

    public void setInitAction(String str) {
        this.initAction = str;
    }

    @Override // antlr.AlternativeElement
    public void setLabel(String str) {
        this.label = str;
    }

    public void setOption(Token token, Token token2) {
        if (token.getText().equals("warnWhenFollowAmbig")) {
            if (token2.getText().equals("true")) {
                this.warnWhenFollowAmbig = true;
                return;
            } else if (token2.getText().equals("false")) {
                this.warnWhenFollowAmbig = false;
                return;
            } else {
                this.grammar.antlrTool.error("Value for warnWhenFollowAmbig must be true or false", this.grammar.getFilename(), token.getLine(), token.getColumn());
                return;
            }
        }
        if (token.getText().equals("generateAmbigWarnings")) {
            if (token2.getText().equals("true")) {
                this.generateAmbigWarnings = true;
                return;
            } else if (token2.getText().equals("false")) {
                this.generateAmbigWarnings = false;
                return;
            } else {
                this.grammar.antlrTool.error("Value for generateAmbigWarnings must be true or false", this.grammar.getFilename(), token.getLine(), token.getColumn());
                return;
            }
        }
        if (token.getText().equals("greedy")) {
            if (token2.getText().equals("true")) {
                this.greedy = true;
                this.greedySet = true;
                return;
            } else if (token2.getText().equals("false")) {
                this.greedy = false;
                this.greedySet = true;
                return;
            } else {
                this.grammar.antlrTool.error("Value for greedy must be true or false", this.grammar.getFilename(), token.getLine(), token.getColumn());
                return;
            }
        }
        this.grammar.antlrTool.error(new StringBuffer("Invalid subrule option: ").append(token.getText()).toString(), this.grammar.getFilename(), token.getLine(), token.getColumn());
    }

    @Override // antlr.GrammarElement
    public String toString() {
        String string = this.initAction != null ? new StringBuffer(" (").append(this.initAction).toString() : " (";
        for (int i = 0; i < this.alternatives.size(); i++) {
            Alternative alternativeAt = getAlternativeAt(i);
            Lookahead[] lookaheadArr = alternativeAt.cache;
            int i2 = alternativeAt.lookaheadDepth;
            if (i2 != -1) {
                if (i2 == Integer.MAX_VALUE) {
                    string = new StringBuffer().append(string).append("{?}:").toString();
                } else {
                    String string2 = new StringBuffer().append(string).append(" {").toString();
                    for (int i3 = 1; i3 <= i2; i3++) {
                        string2 = new StringBuffer().append(string2).append(lookaheadArr[i3].toString(",", this.grammar.tokenManager.getVocabulary())).toString();
                        if (i3 < i2 && lookaheadArr[i3 + 1] != null) {
                            string2 = new StringBuffer().append(string2).append(";").toString();
                        }
                    }
                    string = new StringBuffer().append(string2).append("}:").toString();
                }
            }
            String str = alternativeAt.semPred;
            if (str != null) {
                string = new StringBuffer().append(string).append(str).toString();
            }
            for (AlternativeElement alternativeElement = alternativeAt.head; alternativeElement != null; alternativeElement = alternativeElement.next) {
                string = new StringBuffer().append(string).append(alternativeElement).toString();
            }
            if (i < this.alternatives.size() - 1) {
                string = new StringBuffer().append(string).append(" |").toString();
            }
        }
        return new StringBuffer().append(string).append(" )").toString();
    }
}
