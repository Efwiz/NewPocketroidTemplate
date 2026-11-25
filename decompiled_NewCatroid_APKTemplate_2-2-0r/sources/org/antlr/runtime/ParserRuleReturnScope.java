package org.antlr.runtime;

/* JADX INFO: loaded from: classes12.dex */
public class ParserRuleReturnScope extends RuleReturnScope {
    public Token start;
    public Token stop;

    @Override // org.antlr.runtime.RuleReturnScope
    public Object getTree() {
        return null;
    }

    @Override // org.antlr.runtime.RuleReturnScope
    public Object getStart() {
        return this.start;
    }

    @Override // org.antlr.runtime.RuleReturnScope
    public Object getStop() {
        return this.stop;
    }
}
