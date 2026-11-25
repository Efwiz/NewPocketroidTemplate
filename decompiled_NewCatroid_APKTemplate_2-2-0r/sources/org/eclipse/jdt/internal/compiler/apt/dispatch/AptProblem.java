package org.eclipse.jdt.internal.compiler.apt.dispatch;

import org.eclipse.jdt.internal.compiler.impl.ReferenceContext;
import org.eclipse.jdt.internal.compiler.problem.DefaultProblem;

/* JADX INFO: loaded from: classes14.dex */
public class AptProblem extends DefaultProblem {
    private static final String MARKER_ID = "org.eclipse.jdt.apt.pluggable.core.compileProblem";
    public final ReferenceContext _referenceContext;

    @Override // org.eclipse.jdt.internal.compiler.problem.DefaultProblem, org.eclipse.jdt.core.compiler.CategorizedProblem
    public int getCategoryID() {
        return 0;
    }

    public AptProblem(ReferenceContext referenceContext, char[] cArr, String str, int i, String[] strArr, int i2, int i3, int i4, int i5, int i6) {
        super(cArr, str, i, strArr, i2, i3, i4, i5, i6);
        this._referenceContext = referenceContext;
    }

    @Override // org.eclipse.jdt.internal.compiler.problem.DefaultProblem, org.eclipse.jdt.core.compiler.CategorizedProblem
    public String getMarkerType() {
        return MARKER_ID;
    }
}
