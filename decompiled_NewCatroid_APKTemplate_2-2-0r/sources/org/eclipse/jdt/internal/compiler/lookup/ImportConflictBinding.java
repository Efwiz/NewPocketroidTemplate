package org.eclipse.jdt.internal.compiler.lookup;

import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.compiler.ast.ImportReference;

/* JADX INFO: loaded from: classes14.dex */
public class ImportConflictBinding extends ImportBinding {
    public ReferenceBinding conflictingTypeBinding;

    public ImportConflictBinding(char[][] cArr, Binding binding, ReferenceBinding referenceBinding, ImportReference importReference) {
        super(cArr, false, binding, importReference);
        this.conflictingTypeBinding = referenceBinding;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ImportBinding, org.eclipse.jdt.internal.compiler.lookup.Binding
    public char[] readableName() {
        return CharOperation.concatWith(this.compoundName, '.');
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ImportBinding
    public String toString() {
        return "method import : ".concat(new String(readableName()));
    }
}
