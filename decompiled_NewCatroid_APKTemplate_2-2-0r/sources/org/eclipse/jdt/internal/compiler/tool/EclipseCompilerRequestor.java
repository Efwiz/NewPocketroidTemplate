package org.eclipse.jdt.internal.compiler.tool;

import javax.tools.DiagnosticListener;
import javax.tools.JavaFileObject;
import org.eclipse.jdt.core.compiler.CategorizedProblem;
import org.eclipse.jdt.internal.compiler.CompilationResult;
import org.eclipse.jdt.internal.compiler.batch.BatchCompilerRequestor;
import org.eclipse.jdt.internal.compiler.batch.Main;
import org.eclipse.jdt.internal.compiler.problem.DefaultProblemFactory;

/* JADX INFO: loaded from: classes14.dex */
public class EclipseCompilerRequestor extends BatchCompilerRequestor {
    private final DiagnosticListener<? super JavaFileObject> diagnosticListener;
    private final DefaultProblemFactory problemFactory;

    public EclipseCompilerRequestor(Main main, DiagnosticListener<? super JavaFileObject> diagnosticListener, DefaultProblemFactory defaultProblemFactory) {
        super(main);
        this.diagnosticListener = diagnosticListener;
        this.problemFactory = defaultProblemFactory;
    }

    @Override // org.eclipse.jdt.internal.compiler.batch.BatchCompilerRequestor
    protected void reportProblems(CompilationResult compilationResult) {
        if (this.diagnosticListener != null) {
            for (CategorizedProblem categorizedProblem : compilationResult.getAllProblems()) {
                this.diagnosticListener.report(EclipseDiagnostic.newInstance(categorizedProblem, this.problemFactory));
            }
        }
    }
}
