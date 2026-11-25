package antlr;

/* JADX INFO: loaded from: classes.dex */
class TreeElement extends AlternativeBlock {
    GrammarAtom root;

    public TreeElement(Grammar grammar, Token token) {
        super(grammar, token, false);
    }

    @Override // antlr.AlternativeBlock, antlr.GrammarElement
    public void generate() {
        this.grammar.generator.gen(this);
    }

    @Override // antlr.AlternativeBlock, antlr.GrammarElement
    public Lookahead look(int i) {
        return this.grammar.theLLkAnalyzer.look(i, this);
    }

    @Override // antlr.AlternativeBlock, antlr.GrammarElement
    public String toString() {
        String string = new StringBuffer(" #(").append(this.root).toString();
        for (AlternativeElement alternativeElement = ((Alternative) this.alternatives.elementAt(0)).head; alternativeElement != null; alternativeElement = alternativeElement.next) {
            string = new StringBuffer().append(string).append(alternativeElement).toString();
        }
        return new StringBuffer().append(string).append(" )").toString();
    }
}
