package org.eclipse.jdt.internal.compiler.problem;

import org.eclipse.jdt.core.compiler.CategorizedProblem;
import org.eclipse.jdt.internal.compiler.CompilationResult;
import org.eclipse.jdt.internal.compiler.ast.ASTNode;
import org.eclipse.jdt.internal.compiler.lookup.InvocationSite;
import org.eclipse.jdt.internal.compiler.util.Util;

/* JADX INFO: loaded from: classes14.dex */
public class AbortCompilation extends RuntimeException {
    private static final long serialVersionUID = -2047226595083244852L;
    public CompilationResult compilationResult;
    public Throwable exception;
    public boolean isSilent;
    public CategorizedProblem problem;
    public RuntimeException silentException;

    public AbortCompilation() {
    }

    public AbortCompilation(CompilationResult compilationResult, CategorizedProblem categorizedProblem) {
        this();
        this.compilationResult = compilationResult;
        this.problem = categorizedProblem;
    }

    public AbortCompilation(CompilationResult compilationResult, Throwable th) {
        this();
        this.compilationResult = compilationResult;
        this.exception = th;
    }

    public AbortCompilation(boolean z, RuntimeException runtimeException) {
        this();
        this.isSilent = z;
        this.silentException = runtimeException;
    }

    @Override // java.lang.Throwable
    public String getMessage() {
        String message = super.getMessage();
        if (message == null) {
            message = Util.EMPTY_STRING;
        }
        StringBuffer stringBuffer = new StringBuffer(message);
        CategorizedProblem categorizedProblem = this.problem;
        if (categorizedProblem != null) {
            stringBuffer.append(categorizedProblem);
        } else {
            Throwable th = this.exception;
            if (th != null) {
                String message2 = th.getMessage();
                if (message2 == null) {
                    message2 = Util.EMPTY_STRING;
                }
                stringBuffer.append(message2);
            } else {
                RuntimeException runtimeException = this.silentException;
                if (runtimeException != null) {
                    String message3 = runtimeException.getMessage();
                    if (message3 == null) {
                        message3 = Util.EMPTY_STRING;
                    }
                    stringBuffer.append(message3);
                }
            }
        }
        return String.valueOf(stringBuffer);
    }

    public void updateContext(InvocationSite invocationSite, CompilationResult compilationResult) {
        CategorizedProblem categorizedProblem = this.problem;
        if (categorizedProblem != null && categorizedProblem.getSourceStart() == 0 && this.problem.getSourceEnd() == 0) {
            this.problem.setSourceStart(invocationSite.sourceStart());
            this.problem.setSourceEnd(invocationSite.sourceEnd());
            int[] lineSeparatorPositions = compilationResult.getLineSeparatorPositions();
            this.problem.setSourceLineNumber(Util.getLineNumber(invocationSite.sourceStart(), lineSeparatorPositions, 0, lineSeparatorPositions.length - 1));
            this.compilationResult = compilationResult;
        }
    }

    public void updateContext(ASTNode aSTNode, CompilationResult compilationResult) {
        CategorizedProblem categorizedProblem = this.problem;
        if (categorizedProblem != null && categorizedProblem.getSourceStart() == 0 && this.problem.getSourceEnd() == 0) {
            this.problem.setSourceStart(aSTNode.sourceStart());
            this.problem.setSourceEnd(aSTNode.sourceEnd());
            int[] lineSeparatorPositions = compilationResult.getLineSeparatorPositions();
            this.problem.setSourceLineNumber(Util.getLineNumber(aSTNode.sourceStart(), lineSeparatorPositions, 0, lineSeparatorPositions.length - 1));
            this.compilationResult = compilationResult;
        }
    }

    public String getKey() {
        StringBuffer stringBuffer = new StringBuffer();
        CategorizedProblem categorizedProblem = this.problem;
        if (categorizedProblem != null) {
            stringBuffer.append(categorizedProblem);
        }
        return String.valueOf(stringBuffer);
    }
}
