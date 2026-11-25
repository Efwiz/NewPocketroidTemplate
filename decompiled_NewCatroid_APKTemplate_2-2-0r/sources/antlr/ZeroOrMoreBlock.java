package antlr;

/* JADX INFO: loaded from: classes.dex */
class ZeroOrMoreBlock extends BlockWithImpliedExitPath {
    public ZeroOrMoreBlock(Grammar grammar) {
        super(grammar);
    }

    public ZeroOrMoreBlock(Grammar grammar, Token token) {
        super(grammar, token);
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
        return new StringBuffer().append(super.toString()).append("*").toString();
    }
}
