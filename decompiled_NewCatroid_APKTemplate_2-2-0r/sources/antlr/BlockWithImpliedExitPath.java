package antlr;

/* JADX INFO: loaded from: classes.dex */
abstract class BlockWithImpliedExitPath extends AlternativeBlock {
    protected Lookahead[] exitCache;
    protected int exitLookaheadDepth;

    public BlockWithImpliedExitPath(Grammar grammar) {
        super(grammar);
        this.exitCache = new Lookahead[this.grammar.maxk + 1];
    }

    public BlockWithImpliedExitPath(Grammar grammar, Token token) {
        super(grammar, token, false);
        this.exitCache = new Lookahead[this.grammar.maxk + 1];
    }
}
