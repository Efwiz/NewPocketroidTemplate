package org.eclipse.jdt.internal.compiler.ast;

import org.eclipse.jdt.internal.compiler.ASTVisitor;
import org.eclipse.jdt.internal.compiler.lookup.CompilationUnitScope;

/* JADX INFO: loaded from: classes14.dex */
public class ImportReference extends ASTNode {
    public Annotation[] annotations;
    public int declarationEnd;
    public int declarationSourceEnd;
    public int declarationSourceStart;
    public int modifiers;
    public long[] sourcePositions;
    public char[][] tokens;
    public int trailingStarPosition;

    public ImportReference(char[][] cArr, long[] jArr, boolean z, int i) {
        this.tokens = cArr;
        this.sourcePositions = jArr;
        if (z) {
            this.bits |= 131072;
        }
        this.sourceEnd = (int) jArr[jArr.length - 1];
        this.sourceStart = (int) (jArr[0] >>> 32);
        this.modifiers = i;
    }

    public boolean isStatic() {
        return (this.modifiers & 8) != 0;
    }

    public char[][] getImportName() {
        return this.tokens;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.ASTNode
    public StringBuffer print(int i, StringBuffer stringBuffer) {
        return print(i, stringBuffer, true);
    }

    public StringBuffer print(int i, StringBuffer stringBuffer, boolean z) {
        for (int i2 = 0; i2 < this.tokens.length; i2++) {
            if (i2 > 0) {
                stringBuffer.append('.');
            }
            stringBuffer.append(this.tokens[i2]);
        }
        if (z && (this.bits & 131072) != 0) {
            stringBuffer.append(".*");
        }
        return stringBuffer;
    }

    public void traverse(ASTVisitor aSTVisitor, CompilationUnitScope compilationUnitScope) {
        aSTVisitor.visit(this, compilationUnitScope);
        aSTVisitor.endVisit(this, compilationUnitScope);
    }
}
