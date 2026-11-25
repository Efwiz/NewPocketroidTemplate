package org.eclipse.jdt.internal.compiler.batch;

import org.eclipse.jdt.internal.compiler.CompilationResult;
import org.eclipse.jdt.internal.compiler.ICompilerRequestor;

/* JADX INFO: loaded from: classes14.dex */
public class BatchCompilerRequestor implements ICompilerRequestor {
    private Main compiler;
    private int lineDelta = 0;

    protected void reportProblems(CompilationResult compilationResult) {
    }

    public BatchCompilerRequestor(Main main) {
        this.compiler = main;
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
}
