package org.eclipse.jdt.internal.compiler.parser;

import org.eclipse.jdt.internal.compiler.ast.ASTNode;
import org.eclipse.jdt.internal.compiler.ast.ImportReference;

/* JADX INFO: loaded from: classes14.dex */
public class RecoveredImport extends RecoveredElement {
    public ImportReference importReference;

    public RecoveredImport(ImportReference importReference, RecoveredElement recoveredElement, int i) {
        super(recoveredElement, i);
        this.importReference = importReference;
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredElement
    public ASTNode parseTree() {
        return this.importReference;
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredElement
    public int sourceEnd() {
        return this.importReference.declarationSourceEnd;
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredElement
    public String toString(int i) {
        return String.valueOf(tabString(i)) + "Recovered import: " + this.importReference.toString();
    }

    public ImportReference updatedImportReference() {
        return this.importReference;
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredElement
    public void updateParseTree() {
        updatedImportReference();
    }

    @Override // org.eclipse.jdt.internal.compiler.parser.RecoveredElement
    public void updateSourceEndIfNecessary(int i, int i2) {
        if (this.importReference.declarationSourceEnd == 0) {
            this.importReference.declarationSourceEnd = i2;
            this.importReference.declarationEnd = i2;
        }
    }
}
