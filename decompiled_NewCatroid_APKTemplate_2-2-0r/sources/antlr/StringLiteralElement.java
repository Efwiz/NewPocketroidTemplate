package antlr;

/* JADX INFO: loaded from: classes.dex */
class StringLiteralElement extends GrammarAtom {
    protected String processedAtomText;

    public StringLiteralElement(Grammar grammar, Token token, int i) {
        int i2;
        super(grammar, token, i);
        boolean z = grammar instanceof LexerGrammar;
        if (!z) {
            TokenSymbol tokenSymbol = this.grammar.tokenManager.getTokenSymbol(this.atomText);
            if (tokenSymbol == null) {
                grammar.antlrTool.error(new StringBuffer("Undefined literal: ").append(this.atomText).toString(), this.grammar.getFilename(), token.getLine(), token.getColumn());
            } else {
                this.tokenType = tokenSymbol.getTokenType();
            }
        }
        this.line = token.getLine();
        this.processedAtomText = new String();
        int i3 = 1;
        while (i3 < this.atomText.length() - 1) {
            char cCharAt = this.atomText.charAt(i3);
            if (cCharAt == '\\' && (i2 = i3 + 1) < this.atomText.length() - 1) {
                cCharAt = this.atomText.charAt(i2);
                if (cCharAt == 'n') {
                    cCharAt = '\n';
                } else if (cCharAt == 'r') {
                    cCharAt = '\r';
                } else if (cCharAt == 't') {
                    cCharAt = '\t';
                }
                i3 = i2;
            }
            if (z) {
                ((LexerGrammar) grammar).charVocabulary.add(cCharAt);
            }
            this.processedAtomText = new StringBuffer().append(this.processedAtomText).append(cCharAt).toString();
            i3++;
        }
    }

    @Override // antlr.GrammarElement
    public void generate() {
        this.grammar.generator.gen(this);
    }

    @Override // antlr.GrammarElement
    public Lookahead look(int i) {
        return this.grammar.theLLkAnalyzer.look(i, this);
    }
}
