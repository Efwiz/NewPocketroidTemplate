package org.jetbrains.kotlin.parsing;

/* JADX INFO: loaded from: classes4.dex */
public class SemanticWhitespaceAwarePsiBuilderForByClause extends SemanticWhitespaceAwarePsiBuilderAdapter {
    private int stackSize;

    public SemanticWhitespaceAwarePsiBuilderForByClause(SemanticWhitespaceAwarePsiBuilder semanticWhitespaceAwarePsiBuilder) {
        super(semanticWhitespaceAwarePsiBuilder);
        this.stackSize = 0;
    }

    @Override // org.jetbrains.kotlin.parsing.SemanticWhitespaceAwarePsiBuilderAdapter, org.jetbrains.kotlin.parsing.SemanticWhitespaceAwarePsiBuilder
    public void disableNewlines() {
        super.disableNewlines();
        this.stackSize++;
    }

    @Override // org.jetbrains.kotlin.parsing.SemanticWhitespaceAwarePsiBuilderAdapter, org.jetbrains.kotlin.parsing.SemanticWhitespaceAwarePsiBuilder
    public void enableNewlines() {
        super.enableNewlines();
        this.stackSize++;
    }

    @Override // org.jetbrains.kotlin.parsing.SemanticWhitespaceAwarePsiBuilderAdapter, org.jetbrains.kotlin.parsing.SemanticWhitespaceAwarePsiBuilder
    public void restoreNewlinesState() {
        super.restoreNewlinesState();
        this.stackSize--;
    }

    public int getStackSize() {
        return this.stackSize;
    }
}
