package org.eclipse.jdt.internal.compiler.lookup;

/* JADX INFO: loaded from: classes14.dex */
public class ProblemFieldBinding extends FieldBinding {
    public FieldBinding closestMatch;
    private int problemId;

    public ProblemFieldBinding(ReferenceBinding referenceBinding, char[] cArr, int i) {
        this(null, referenceBinding, cArr, i);
    }

    public ProblemFieldBinding(FieldBinding fieldBinding, ReferenceBinding referenceBinding, char[] cArr, int i) {
        this.closestMatch = fieldBinding;
        this.declaringClass = referenceBinding;
        this.f628name = cArr;
        this.problemId = i;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.Binding
    public final int problemId() {
        return this.problemId;
    }
}
