package org.eclipse.jdt.internal.compiler.parser;

import java.util.HashSet;
import java.util.Set;
import org.eclipse.jdt.internal.compiler.ast.ASTNode;
import org.eclipse.jdt.internal.compiler.ast.Statement;
import org.eclipse.jdt.internal.compiler.ast.TypeDeclaration;

/* JADX INFO: loaded from: classes14.dex */
public class RecoveredStatement extends RecoveredElement {
    public Statement statement;

    public RecoveredStatement(Statement statement, RecoveredElement recoveredElement, int i) {
        super(recoveredElement, i);
        this.statement = statement;
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredElement
    public ASTNode parseTree() {
        return this.statement;
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredElement
    public int sourceEnd() {
        return this.statement.sourceEnd;
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredElement
    public String toString(int i) {
        return String.valueOf(tabString(i)) + "Recovered statement:\n" + ((Object) this.statement.print(i + 1, new StringBuffer(10)));
    }

    public Statement updatedStatement(int i, Set<TypeDeclaration> set) {
        return this.statement;
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredElement
    public void updateParseTree() {
        updatedStatement(0, new HashSet());
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredElement
    public void updateSourceEndIfNecessary(int i, int i2) {
        if (this.statement.sourceEnd == 0) {
            this.statement.sourceEnd = i2;
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredElement
    public RecoveredElement updateOnClosingBrace(int i, int i2) {
        int i3 = this.bracketBalance - 1;
        this.bracketBalance = i3;
        if (i3 > 0 || this.parent == null) {
            return this;
        }
        updateSourceEndIfNecessary(i, i2);
        return this.parent.updateOnClosingBrace(i, i2);
    }
}
