package org.eclipse.jdt.internal.compiler.problem;

import org.eclipse.jdt.core.compiler.CategorizedProblem;
import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.compiler.CompilationResult;
import org.eclipse.jdt.internal.compiler.IErrorHandlingPolicy;
import org.eclipse.jdt.internal.compiler.IProblemFactory;
import org.eclipse.jdt.internal.compiler.ast.CompilationUnitDeclaration;
import org.eclipse.jdt.internal.compiler.env.ICompilationUnit;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.eclipse.jdt.internal.compiler.impl.ReferenceContext;
import org.eclipse.jdt.internal.compiler.util.Util;

/* JADX INFO: loaded from: classes14.dex */
public class ProblemHandler {
    public static final String[] NoArgument = CharOperation.NO_STRINGS;
    public final CompilerOptions options;
    public IErrorHandlingPolicy policy;
    public final IProblemFactory problemFactory;
    private IErrorHandlingPolicy rootPolicy;
    protected boolean suppressTagging = false;

    public int computeSeverity(int i) {
        return 1;
    }

    public ProblemHandler(IErrorHandlingPolicy iErrorHandlingPolicy, CompilerOptions compilerOptions, IProblemFactory iProblemFactory) {
        this.policy = iErrorHandlingPolicy;
        this.problemFactory = iProblemFactory;
        this.options = compilerOptions;
    }

    public CategorizedProblem createProblem(char[] cArr, int i, String[] strArr, String[] strArr2, int i2, int i3, int i4, int i5, int i6) {
        return this.problemFactory.createProblem(cArr, i, strArr, strArr2, i2, i3, i4, i5, i6);
    }

    public CategorizedProblem createProblem(char[] cArr, int i, String[] strArr, int i2, String[] strArr2, int i3, int i4, int i5, int i6, int i7) {
        return this.problemFactory.createProblem(cArr, i, strArr, i2, strArr2, i3, i4, i5, i6, i7);
    }

    public void handle(int i, String[] strArr, int i2, String[] strArr2, int i3, int i4, int i5, ReferenceContext referenceContext, CompilationResult compilationResult) {
        int lineNumber;
        CompilationUnitDeclaration compilationUnitDeclaration;
        ICompilationUnit compilationUnit;
        if (i3 == 256) {
            return;
        }
        boolean z = (i3 & 33) == 1;
        if ((i3 & 512) == 0 && this.policy.ignoreAllErrors()) {
            if (referenceContext == null) {
                if ((i3 & 1) != 0) {
                    throw new AbortCompilation((CompilationResult) null, createProblem(null, i, strArr, i2, strArr2, i3, 0, 0, 0, 0));
                }
                return;
            } else {
                if (z) {
                    referenceContext.tagAsHavingIgnoredMandatoryErrors(i);
                    return;
                }
                return;
            }
        }
        if ((i3 & 32) != 0 && i != 536871362 && !this.options.ignoreSourceFolderWarningOption && (compilationUnit = compilationResult.getCompilationUnit()) != null) {
            try {
                if (compilationUnit.ignoreOptionalProblems()) {
                    return;
                }
            } catch (AbstractMethodError unused) {
            }
        }
        if (referenceContext == null) {
            if ((i3 & 1) != 0) {
                throw new AbortCompilation((CompilationResult) null, createProblem(null, i, strArr, i2, strArr2, i3, 0, 0, 0, 0));
            }
            return;
        }
        if (i4 >= 0) {
            int[] lineSeparatorPositions = compilationResult.getLineSeparatorPositions();
            lineNumber = Util.getLineNumber(i4, lineSeparatorPositions, 0, lineSeparatorPositions.length - 1);
        } else {
            lineNumber = 0;
        }
        boolean z2 = z;
        CategorizedProblem categorizedProblemCreateProblem = createProblem(compilationResult.getFileName(), i, strArr, i2, strArr2, i3, i4, i5, lineNumber, i4 >= 0 ? Util.searchColumnNumber(compilationResult.getLineSeparatorPositions(), lineNumber, i4) : 0);
        if (categorizedProblemCreateProblem == null) {
            return;
        }
        int i6 = i3 & 1;
        if (i6 == 0) {
            record(categorizedProblemCreateProblem, compilationResult, referenceContext, false);
            return;
        }
        if (i6 != 1) {
            return;
        }
        record(categorizedProblemCreateProblem, compilationResult, referenceContext, z2);
        if ((i3 & 128) != 0) {
            if (referenceContext.hasErrors() || z2 || !this.options.suppressOptionalErrors || (compilationUnitDeclaration = referenceContext.getCompilationUnitDeclaration()) == null || !compilationUnitDeclaration.isSuppressed(categorizedProblemCreateProblem)) {
                if (!this.suppressTagging || this.options.treatOptionalErrorAsFatal) {
                    referenceContext.tagAsHavingErrors();
                }
                int i7 = this.policy.stopOnFirstError() ? 2 : i3 & 30;
                if (i7 != 0) {
                    referenceContext.abort(i7, categorizedProblemCreateProblem);
                }
            }
        }
    }

    public void handle(int i, String[] strArr, String[] strArr2, int i2, int i3, ReferenceContext referenceContext, CompilationResult compilationResult) {
        handle(i, strArr, 0, strArr2, computeSeverity(i), i2, i3, referenceContext, compilationResult);
    }

    public void record(CategorizedProblem categorizedProblem, CompilationResult compilationResult, ReferenceContext referenceContext, boolean z) {
        compilationResult.record(categorizedProblem, referenceContext, z);
    }

    public IErrorHandlingPolicy switchErrorHandlingPolicy(IErrorHandlingPolicy iErrorHandlingPolicy) {
        if (this.rootPolicy == null) {
            this.rootPolicy = this.policy;
        }
        IErrorHandlingPolicy iErrorHandlingPolicy2 = this.policy;
        this.policy = iErrorHandlingPolicy;
        return iErrorHandlingPolicy2;
    }

    public IErrorHandlingPolicy suspendTempErrorHandlingPolicy() {
        IErrorHandlingPolicy iErrorHandlingPolicy = this.policy;
        IErrorHandlingPolicy iErrorHandlingPolicy2 = this.rootPolicy;
        if (iErrorHandlingPolicy2 != null) {
            this.policy = iErrorHandlingPolicy2;
        }
        return iErrorHandlingPolicy;
    }

    public void resumeTempErrorHandlingPolicy(IErrorHandlingPolicy iErrorHandlingPolicy) {
        this.policy = iErrorHandlingPolicy;
    }
}
