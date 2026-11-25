package org.eclipse.jdt.internal.compiler.lookup;

/* JADX INFO: loaded from: classes14.dex */
public class ProblemPackageBinding extends PackageBinding {
    private int problemId;

    ProblemPackageBinding(char[][] cArr, int i) {
        this.compoundName = cArr;
        this.problemId = i;
    }

    ProblemPackageBinding(char[] cArr, int i) {
        this(new char[][]{cArr}, i);
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.PackageBinding, org.eclipse.jdt.internal.compiler.lookup.Binding
    public final int problemId() {
        return this.problemId;
    }
}
