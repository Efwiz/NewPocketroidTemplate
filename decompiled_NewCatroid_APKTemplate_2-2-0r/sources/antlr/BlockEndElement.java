package antlr;

/* JADX INFO: loaded from: classes.dex */
class BlockEndElement extends AlternativeElement {
    protected AlternativeBlock block;
    protected boolean[] lock;

    public BlockEndElement(Grammar grammar) {
        super(grammar);
        this.lock = new boolean[grammar.maxk + 1];
    }

    @Override // antlr.GrammarElement
    public Lookahead look(int i) {
        return this.grammar.theLLkAnalyzer.look(i, this);
    }

    @Override // antlr.GrammarElement
    public String toString() {
        return "";
    }
}
