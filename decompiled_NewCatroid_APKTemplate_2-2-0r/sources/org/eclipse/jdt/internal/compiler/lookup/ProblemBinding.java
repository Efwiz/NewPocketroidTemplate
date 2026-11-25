package org.eclipse.jdt.internal.compiler.lookup;

import org.eclipse.jdt.core.compiler.CharOperation;

/* JADX INFO: loaded from: classes14.dex */
public class ProblemBinding extends Binding {

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    public char[] f625name;
    private int problemId;
    public ReferenceBinding searchType;

    @Override // org.eclipse.jdt.internal.compiler.lookup.Binding
    public final int kind() {
        return 7;
    }

    public ProblemBinding(char[][] cArr, int i) {
        this(CharOperation.concatWith(cArr, '.'), i);
    }

    public ProblemBinding(char[][] cArr, ReferenceBinding referenceBinding, int i) {
        this(CharOperation.concatWith(cArr, '.'), referenceBinding, i);
    }

    ProblemBinding(char[] cArr, int i) {
        this.f625name = cArr;
        this.problemId = i;
    }

    ProblemBinding(char[] cArr, ReferenceBinding referenceBinding, int i) {
        this(cArr, i);
        this.searchType = referenceBinding;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.Binding
    public final int problemId() {
        return this.problemId;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.Binding
    public char[] readableName() {
        return this.f625name;
    }
}
