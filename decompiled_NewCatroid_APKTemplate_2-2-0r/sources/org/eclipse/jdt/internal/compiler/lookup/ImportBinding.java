package org.eclipse.jdt.internal.compiler.lookup;

import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.compiler.ast.ImportReference;

/* JADX INFO: loaded from: classes14.dex */
public class ImportBinding extends Binding {
    public char[][] compoundName;
    public boolean onDemand;
    public ImportReference reference;
    public Binding resolvedImport;

    @Override // org.eclipse.jdt.internal.compiler.lookup.Binding
    public final int kind() {
        return 32;
    }

    public ImportBinding(char[][] cArr, boolean z, Binding binding, ImportReference importReference) {
        this.compoundName = cArr;
        this.onDemand = z;
        this.resolvedImport = binding;
        this.reference = importReference;
    }

    public boolean isStatic() {
        ImportReference importReference = this.reference;
        return importReference != null && importReference.isStatic();
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.Binding
    public char[] readableName() {
        if (this.onDemand) {
            return CharOperation.concat(CharOperation.concatWith(this.compoundName, '.'), ".*".toCharArray());
        }
        return CharOperation.concatWith(this.compoundName, '.');
    }

    public String toString() {
        return "import : ".concat(new String(readableName()));
    }
}
