package org.eclipse.jdt.internal.compiler.tool;

import javax.tools.DiagnosticListener;
import javax.tools.JavaFileObject;
import org.eclipse.jdt.core.compiler.CategorizedProblem;
import org.eclipse.jdt.internal.compiler.CompilationResult;
import org.eclipse.jdt.internal.compiler.ICompilerRequestor;
import org.eclipse.jdt.internal.compiler.batch.Main;
import org.eclipse.jdt.internal.compiler.problem.DefaultProblemFactory;

/* JADX INFO: loaded from: classes14.dex */
public class EclipseBatchRequestor implements ICompilerRequestor {
    private final Main compiler;
    private final DiagnosticListener<? super JavaFileObject> diagnosticListener;
    private int lineDelta = 0;
    private final DefaultProblemFactory problemFactory;

    public EclipseBatchRequestor(Main main, DiagnosticListener<? super JavaFileObject> diagnosticListener, DefaultProblemFactory defaultProblemFactory) {
        this.compiler = main;
        this.diagnosticListener = diagnosticListener;
        this.problemFactory = defaultProblemFactory;
    }

    @Override // org.eclipse.jdt.internal.compiler.ICompilerRequestor
    public void acceptResult(CompilationResult compilationResult) throws Throwable {
        if (compilationResult.lineSeparatorPositions != null) {
            this.lineDelta += compilationResult.lineSeparatorPositions.length;
            if (this.compiler.showProgress && this.lineDelta > 2000) {
                this.compiler.logger.logProgress();
                this.lineDelta = 0;
            }
        }
        this.compiler.logger.startLoggingSource(compilationResult);
        if (compilationResult.hasProblems() || compilationResult.hasTasks()) {
            this.compiler.logger.logProblems(compilationResult.getAllProblems(), compilationResult.compilationUnit.getContents(), this.compiler);
            reportProblems(compilationResult);
        }
        this.compiler.outputClassFiles(compilationResult);
        this.compiler.logger.endLoggingSource();
    }

    private void reportProblems(CompilationResult compilationResult) {
        for (CategorizedProblem categorizedProblem : compilationResult.getAllProblems()) {
            this.diagnosticListener.report(EclipseDiagnostic.newInstance(categorizedProblem, this.problemFactory));
        }
    }
}
