package org.eclipse.jdt.internal.compiler;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.jdt.core.compiler.CategorizedProblem;
import org.eclipse.jdt.core.compiler.CompilationProgress;
import org.eclipse.jdt.internal.compiler.ast.CompilationUnitDeclaration;
import org.eclipse.jdt.internal.compiler.ast.ImportReference;
import org.eclipse.jdt.internal.compiler.env.AccessRestriction;
import org.eclipse.jdt.internal.compiler.env.IBinaryType;
import org.eclipse.jdt.internal.compiler.env.ICompilationUnit;
import org.eclipse.jdt.internal.compiler.env.INameEnvironment;
import org.eclipse.jdt.internal.compiler.env.ISourceType;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.eclipse.jdt.internal.compiler.impl.CompilerStats;
import org.eclipse.jdt.internal.compiler.impl.ITypeRequestor;
import org.eclipse.jdt.internal.compiler.impl.ReferenceContext;
import org.eclipse.jdt.internal.compiler.lookup.LookupEnvironment;
import org.eclipse.jdt.internal.compiler.lookup.PackageBinding;
import org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding;
import org.eclipse.jdt.internal.compiler.lookup.SourceTypeCollisionException;
import org.eclipse.jdt.internal.compiler.parser.Parser;
import org.eclipse.jdt.internal.compiler.problem.AbortCompilation;
import org.eclipse.jdt.internal.compiler.problem.AbortCompilationUnit;
import org.eclipse.jdt.internal.compiler.problem.DefaultProblem;
import org.eclipse.jdt.internal.compiler.problem.ProblemReporter;
import org.eclipse.jdt.internal.compiler.problem.ProblemSeverities;
import org.eclipse.jdt.internal.compiler.util.Messages;
import org.eclipse.jdt.internal.compiler.util.Util;

/* JADX INFO: loaded from: classes14.dex */
public class Compiler implements ITypeRequestor, ProblemSeverities {
    public static boolean DEBUG = false;
    public static IDebugRequestor DebugRequestor;
    public AbstractAnnotationProcessorManager annotationProcessorManager;
    public int annotationProcessorStartIndex;
    private Map<String, APTProblem[]> aptProblems;
    public LookupEnvironment lookupEnvironment;
    public CompilerOptions options;
    protected PrintWriter out;
    public int parseThreshold;
    public Parser parser;
    public ProblemReporter problemReporter;
    public CompilationProgress progress;
    public ReferenceBinding[] referenceBindings;
    public int remainingIterations;
    public ICompilerRequestor requestor;
    public CompilerStats stats;
    public int totalUnits;
    public CompilationUnitDeclaration[] unitsToProcess;
    public boolean useSingleThread;

    public Compiler(INameEnvironment iNameEnvironment, IErrorHandlingPolicy iErrorHandlingPolicy, Map<String, String> map, ICompilerRequestor iCompilerRequestor, IProblemFactory iProblemFactory) {
        this(iNameEnvironment, iErrorHandlingPolicy, new CompilerOptions(map), iCompilerRequestor, iProblemFactory, null, null);
    }

    public Compiler(INameEnvironment iNameEnvironment, IErrorHandlingPolicy iErrorHandlingPolicy, Map map, ICompilerRequestor iCompilerRequestor, IProblemFactory iProblemFactory, boolean z) {
        this(iNameEnvironment, iErrorHandlingPolicy, new CompilerOptions(map, z), iCompilerRequestor, iProblemFactory, null, null);
    }

    public Compiler(INameEnvironment iNameEnvironment, IErrorHandlingPolicy iErrorHandlingPolicy, CompilerOptions compilerOptions, ICompilerRequestor iCompilerRequestor, IProblemFactory iProblemFactory) {
        this(iNameEnvironment, iErrorHandlingPolicy, compilerOptions, iCompilerRequestor, iProblemFactory, null, null);
    }

    public Compiler(INameEnvironment iNameEnvironment, IErrorHandlingPolicy iErrorHandlingPolicy, CompilerOptions compilerOptions, ICompilerRequestor iCompilerRequestor, IProblemFactory iProblemFactory, PrintWriter printWriter) {
        this(iNameEnvironment, iErrorHandlingPolicy, compilerOptions, iCompilerRequestor, iProblemFactory, printWriter, null);
    }

    public Compiler(INameEnvironment iNameEnvironment, IErrorHandlingPolicy iErrorHandlingPolicy, CompilerOptions compilerOptions, final ICompilerRequestor iCompilerRequestor, IProblemFactory iProblemFactory, PrintWriter printWriter, CompilationProgress compilationProgress) {
        this.remainingIterations = 1;
        this.parseThreshold = -1;
        this.annotationProcessorStartIndex = 0;
        this.useSingleThread = true;
        this.options = compilerOptions;
        this.progress = compilationProgress;
        if (DebugRequestor == null) {
            this.requestor = iCompilerRequestor;
        } else {
            this.requestor = new ICompilerRequestor() { // from class: org.eclipse.jdt.internal.compiler.Compiler.1
                @Override // org.eclipse.jdt.internal.compiler.ICompilerRequestor
                public void acceptResult(CompilationResult compilationResult) {
                    if (Compiler.DebugRequestor.isActive()) {
                        Compiler.DebugRequestor.acceptDebugResult(compilationResult);
                    }
                    iCompilerRequestor.acceptResult(compilationResult);
                }
            };
        }
        this.problemReporter = new ProblemReporter(iErrorHandlingPolicy, this.options, iProblemFactory);
        this.lookupEnvironment = new LookupEnvironment(this, this.options, this.problemReporter, iNameEnvironment);
        this.out = printWriter == null ? new PrintWriter((OutputStream) System.out, true) : printWriter;
        this.stats = new CompilerStats();
        initializeParser();
    }

    @Override // org.eclipse.jdt.internal.compiler.impl.ITypeRequestor
    public void accept(IBinaryType iBinaryType, PackageBinding packageBinding, AccessRestriction accessRestriction) {
        if (this.options.verbose) {
            this.out.println(Messages.bind(Messages.compilation_loadBinary, new String(iBinaryType.getName())));
        }
        this.lookupEnvironment.createBinaryTypeFrom(iBinaryType, packageBinding, accessRestriction);
    }

    @Override // org.eclipse.jdt.internal.compiler.impl.ITypeRequestor
    public void accept(ICompilationUnit iCompilationUnit, AccessRestriction accessRestriction) {
        CompilationUnitDeclaration compilationUnitDeclarationDietParse;
        int i = this.totalUnits;
        CompilationResult compilationResult = new CompilationResult(iCompilationUnit, i, i, this.options.maxProblemsPerUnit);
        compilationResult.checkSecondaryTypes = true;
        try {
            if (this.options.verbose) {
                String strValueOf = String.valueOf(this.totalUnits + 1);
                this.out.println(Messages.bind(Messages.compilation_request, (Object[]) new String[]{strValueOf, strValueOf, new String(iCompilationUnit.getFileName())}));
            }
            if (this.totalUnits < this.parseThreshold) {
                compilationUnitDeclarationDietParse = this.parser.parse(iCompilationUnit, compilationResult);
            } else {
                compilationUnitDeclarationDietParse = this.parser.dietParse(iCompilationUnit, compilationResult);
            }
            this.lookupEnvironment.buildTypeBindings(compilationUnitDeclarationDietParse, accessRestriction);
            addCompilationUnit(iCompilationUnit, compilationUnitDeclarationDietParse);
            this.lookupEnvironment.completeTypeBindings(compilationUnitDeclarationDietParse);
        } catch (AbortCompilationUnit e) {
            if (compilationResult.compilationUnit == iCompilationUnit) {
                this.requestor.acceptResult(compilationResult.tagAsAccepted());
                return;
            }
            throw e;
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.impl.ITypeRequestor
    public void accept(ISourceType[] iSourceTypeArr, PackageBinding packageBinding, AccessRestriction accessRestriction) {
        this.problemReporter.abortDueToInternalError(Messages.bind(Messages.abort_againstSourceModel, (Object[]) new String[]{String.valueOf(iSourceTypeArr[0].getName()), String.valueOf(iSourceTypeArr[0].getFileName())}));
    }

    protected synchronized void addCompilationUnit(ICompilationUnit iCompilationUnit, CompilationUnitDeclaration compilationUnitDeclaration) {
        CompilationUnitDeclaration[] compilationUnitDeclarationArr = this.unitsToProcess;
        if (compilationUnitDeclarationArr == null) {
            return;
        }
        int length = compilationUnitDeclarationArr.length;
        int i = this.totalUnits;
        if (i == length) {
            CompilationUnitDeclaration[] compilationUnitDeclarationArr2 = new CompilationUnitDeclaration[length * 2];
            this.unitsToProcess = compilationUnitDeclarationArr2;
            System.arraycopy(compilationUnitDeclarationArr, 0, compilationUnitDeclarationArr2, 0, i);
        }
        CompilationUnitDeclaration[] compilationUnitDeclarationArr3 = this.unitsToProcess;
        int i2 = this.totalUnits;
        this.totalUnits = i2 + 1;
        compilationUnitDeclarationArr3[i2] = compilationUnitDeclaration;
    }

    protected void beginToCompile(ICompilationUnit[] iCompilationUnitArr) {
        int length = iCompilationUnitArr.length;
        this.totalUnits = 0;
        this.unitsToProcess = new CompilationUnitDeclaration[length];
        internalBeginToCompile(iCompilationUnitArr, length);
    }

    protected void reportProgress(String str) {
        CompilationProgress compilationProgress = this.progress;
        if (compilationProgress != null) {
            if (compilationProgress.isCanceled()) {
                throw new AbortCompilation(true, (RuntimeException) null);
            }
            this.progress.setTaskName(str);
        }
    }

    protected void reportWorked(int i, int i2) {
        CompilationProgress compilationProgress = this.progress;
        if (compilationProgress != null) {
            if (compilationProgress.isCanceled()) {
                throw new AbortCompilation(true, (RuntimeException) null);
            }
            this.progress.worked(i, ((this.totalUnits * this.remainingIterations) - i2) - 1);
        }
    }

    public void compile(ICompilationUnit[] iCompilationUnitArr) throws Throwable {
        compile(iCompilationUnitArr, false);
    }

    private void compile(ICompilationUnit[] iCompilationUnitArr, boolean z) throws Throwable {
        this.stats.startTime = System.currentTimeMillis();
        try {
            reportProgress(Messages.compilation_beginningToCompile);
            if (this.annotationProcessorManager == null) {
                beginToCompile(iCompilationUnitArr);
            } else {
                ICompilationUnit[] iCompilationUnitArr2 = (ICompilationUnit[]) iCompilationUnitArr.clone();
                try {
                    beginToCompile(iCompilationUnitArr);
                    if (!z) {
                        processAnnotations();
                    }
                    if (!this.options.generateClassFiles) {
                        return;
                    }
                } catch (SourceTypeCollisionException e) {
                    backupAptProblems();
                    reset();
                    int length = iCompilationUnitArr2.length;
                    int length2 = e.newAnnotationProcessorUnits.length;
                    ICompilationUnit[] iCompilationUnitArr3 = new ICompilationUnit[length + length2];
                    System.arraycopy(iCompilationUnitArr2, 0, iCompilationUnitArr3, 0, length);
                    System.arraycopy(e.newAnnotationProcessorUnits, 0, iCompilationUnitArr3, length, length2);
                    this.annotationProcessorStartIndex = length;
                    compile(iCompilationUnitArr3, e.isLastRound);
                    return;
                }
            }
            restoreAptProblems();
            processCompiledUnits(0, z);
        } catch (AbortCompilation e2) {
            handleInternalException(e2, null);
        }
        if (this.options.verbose) {
            if (this.totalUnits > 1) {
                this.out.println(Messages.bind(Messages.compilation_units, String.valueOf(this.totalUnits)));
            } else {
                this.out.println(Messages.bind(Messages.compilation_unit, String.valueOf(this.totalUnits)));
            }
        }
    }

    class APTProblem {
        ReferenceContext context;
        CategorizedProblem problem;

        APTProblem(CategorizedProblem categorizedProblem, ReferenceContext referenceContext) {
            this.problem = categorizedProblem;
            this.context = referenceContext;
        }
    }

    protected void backupAptProblems() {
        if (this.unitsToProcess == null) {
            return;
        }
        for (int i = 0; i < this.totalUnits; i++) {
            CompilationUnitDeclaration compilationUnitDeclaration = this.unitsToProcess[i];
            CompilationResult compilationResult = compilationUnitDeclaration.compilationResult;
            if (compilationResult != null && compilationResult.hasErrors()) {
                for (CategorizedProblem categorizedProblem : compilationResult.getErrors()) {
                    if (categorizedProblem.getCategoryID() == 0) {
                        if (this.aptProblems == null) {
                            this.aptProblems = new HashMap();
                        }
                        APTProblem[] aPTProblemArr = this.aptProblems.get(new String(compilationUnitDeclaration.getFileName()));
                        if (aPTProblemArr == null) {
                            this.aptProblems.put(new String(compilationUnitDeclaration.getFileName()), new APTProblem[]{new APTProblem(categorizedProblem, compilationResult.getContext(categorizedProblem))});
                        } else {
                            APTProblem[] aPTProblemArr2 = new APTProblem[aPTProblemArr.length + 1];
                            System.arraycopy(aPTProblemArr, 0, aPTProblemArr2, 0, aPTProblemArr.length);
                            aPTProblemArr2[aPTProblemArr.length] = new APTProblem(categorizedProblem, compilationResult.getContext(categorizedProblem));
                            this.aptProblems.put(new String(compilationUnitDeclaration.getFileName()), aPTProblemArr2);
                        }
                    }
                }
            }
        }
    }

    protected void restoreAptProblems() {
        if (this.unitsToProcess != null && this.aptProblems != null) {
            for (int i = 0; i < this.totalUnits; i++) {
                CompilationUnitDeclaration compilationUnitDeclaration = this.unitsToProcess[i];
                APTProblem[] aPTProblemArr = this.aptProblems.get(new String(compilationUnitDeclaration.getFileName()));
                if (aPTProblemArr != null) {
                    for (APTProblem aPTProblem : aPTProblemArr) {
                        compilationUnitDeclaration.compilationResult.record(aPTProblem.problem, aPTProblem.context);
                    }
                }
            }
        }
        this.aptProblems = null;
    }

    /* JADX WARN: Code duplicated, block: B:58:0x0121 A[Catch: AbortCompilation -> 0x017a, RuntimeException -> 0x0187, Error -> 0x0189, all -> 0x01b3, LOOP:1: B:56:0x011b->B:58:0x0121, LOOP_END, TryCatch #6 {all -> 0x01b3, blocks: (B:51:0x010d, B:53:0x0111, B:55:0x0117, B:57:0x011d, B:58:0x0121, B:81:0x0190, B:82:0x0193, B:85:0x0197, B:86:0x019a, B:89:0x019d, B:48:0x0105, B:61:0x012f, B:63:0x0152, B:68:0x017e, B:69:0x0181, B:71:0x0183, B:72:0x0186), top: B:103:0x0006 }] */
    /* JADX WARN: Code duplicated, block: B:60:0x012d  */
    /* JADX WARN: Not initialized variable reg: 4, insn: 0x01b4: MOVE (r3 I:??[OBJECT, ARRAY]) = (r4 I:??[OBJECT, ARRAY]), block:B:95:0x01b4 */
    protected void processCompiledUnits(int i, boolean z) throws Throwable {
        ProcessTaskManager processTaskManager;
        CompilationUnitDeclaration compilationUnitDeclarationRemoveNextUnit;
        ProcessTaskManager processTaskManager2;
        int i2;
        int i3;
        int i4;
        CompilationUnitDeclaration compilationUnitDeclaration = null;
        ProcessTaskManager processTaskManager3 = null;
        try {
            try {
                try {
                    if (this.useSingleThread) {
                        compilationUnitDeclarationRemoveNextUnit = null;
                        for (int i5 = i; i5 < this.totalUnits; i5++) {
                            try {
                                compilationUnitDeclarationRemoveNextUnit = this.unitsToProcess[i5];
                                if (compilationUnitDeclarationRemoveNextUnit.compilationResult == null || !compilationUnitDeclarationRemoveNextUnit.compilationResult.hasBeenAccepted) {
                                    reportProgress(Messages.bind(Messages.compilation_processing, new String(compilationUnitDeclarationRemoveNextUnit.getFileName())));
                                    try {
                                        if (this.options.verbose) {
                                            this.out.println(Messages.bind(Messages.compilation_process, (Object[]) new String[]{String.valueOf(i5 + 1), String.valueOf(this.totalUnits), new String(this.unitsToProcess[i5].getFileName())}));
                                        }
                                        process(compilationUnitDeclarationRemoveNextUnit, i5);
                                        if (this.annotationProcessorManager == null || shouldCleanup(i5)) {
                                            compilationUnitDeclarationRemoveNextUnit.cleanUp();
                                        }
                                        if (this.annotationProcessorManager == null) {
                                            this.unitsToProcess[i5] = null;
                                        }
                                        reportWorked(1, i5);
                                        this.stats.lineCount += (long) compilationUnitDeclarationRemoveNextUnit.compilationResult.lineSeparatorPositions.length;
                                        long jCurrentTimeMillis = System.currentTimeMillis();
                                        this.requestor.acceptResult(compilationUnitDeclarationRemoveNextUnit.compilationResult.tagAsAccepted());
                                        this.stats.generateTime += System.currentTimeMillis() - jCurrentTimeMillis;
                                        if (this.options.verbose) {
                                            this.out.println(Messages.bind(Messages.compilation_done, (Object[]) new String[]{String.valueOf(i5 + 1), String.valueOf(this.totalUnits), new String(compilationUnitDeclarationRemoveNextUnit.getFileName())}));
                                        }
                                    } catch (Throwable th) {
                                        if (this.annotationProcessorManager == null || shouldCleanup(i5)) {
                                            compilationUnitDeclarationRemoveNextUnit.cleanUp();
                                        }
                                        throw th;
                                    }
                                }
                            } catch (Error e) {
                                e = e;
                                handleInternalException(e, compilationUnitDeclarationRemoveNextUnit, null);
                                throw e;
                            } catch (AbortCompilation e2) {
                                e = e2;
                                processTaskManager2 = null;
                                compilationUnitDeclaration = compilationUnitDeclarationRemoveNextUnit;
                                handleInternalException(e, compilationUnitDeclaration);
                                if (processTaskManager2 != null) {
                                }
                                reset();
                                this.annotationProcessorStartIndex = 0;
                                this.stats.endTime = System.currentTimeMillis();
                            } catch (RuntimeException e3) {
                                e = e3;
                                handleInternalException(e, compilationUnitDeclarationRemoveNextUnit, null);
                                throw e;
                            }
                        }
                        processTaskManager2 = null;
                        if (!z && this.annotationProcessorManager != null && (i2 = this.totalUnits) > (i3 = this.annotationProcessorStartIndex)) {
                            processAnnotations();
                            for (i4 = i3; i4 < i2; i4++) {
                                this.unitsToProcess[i4].cleanUp();
                            }
                            processCompiledUnits(i3, z);
                        }
                        if (processTaskManager2 != null) {
                            processTaskManager2.shutdown();
                        }
                    } else {
                        processTaskManager2 = new ProcessTaskManager(this, i);
                        int i6 = 0;
                        compilationUnitDeclarationRemoveNextUnit = null;
                        while (true) {
                            try {
                                try {
                                    compilationUnitDeclarationRemoveNextUnit = processTaskManager2.removeNextUnit();
                                    if (compilationUnitDeclarationRemoveNextUnit == null) {
                                        break;
                                    }
                                    try {
                                        int i7 = i6 + 1;
                                        reportWorked(1, i6);
                                        this.stats.lineCount += (long) compilationUnitDeclarationRemoveNextUnit.compilationResult.lineSeparatorPositions.length;
                                        this.requestor.acceptResult(compilationUnitDeclarationRemoveNextUnit.compilationResult.tagAsAccepted());
                                        if (this.options.verbose) {
                                            this.out.println(Messages.bind(Messages.compilation_done, (Object[]) new String[]{String.valueOf(i7), String.valueOf(this.totalUnits), new String(compilationUnitDeclarationRemoveNextUnit.getFileName())}));
                                        }
                                        i6 = i7;
                                    } catch (Error e4) {
                                        e = e4;
                                        handleInternalException(e, compilationUnitDeclarationRemoveNextUnit, null);
                                        throw e;
                                    } catch (RuntimeException e5) {
                                        e = e5;
                                        handleInternalException(e, compilationUnitDeclarationRemoveNextUnit, null);
                                        throw e;
                                    }
                                } catch (AbortCompilation e6) {
                                    e = e6;
                                    compilationUnitDeclaration = compilationUnitDeclarationRemoveNextUnit;
                                    handleInternalException(e, compilationUnitDeclaration);
                                    if (processTaskManager2 != null) {
                                    }
                                    reset();
                                    this.annotationProcessorStartIndex = 0;
                                    this.stats.endTime = System.currentTimeMillis();
                                }
                            } catch (Error e7) {
                                CompilationUnitDeclaration compilationUnitDeclaration2 = processTaskManager2.unitToProcess;
                                throw e7;
                            } catch (RuntimeException e8) {
                                CompilationUnitDeclaration compilationUnitDeclaration3 = processTaskManager2.unitToProcess;
                                throw e8;
                            }
                        }
                        if (!z) {
                            processAnnotations();
                            while (i4 < i2) {
                                this.unitsToProcess[i4].cleanUp();
                            }
                            processCompiledUnits(i3, z);
                        }
                        if (processTaskManager2 != null) {
                            processTaskManager2.shutdown();
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                    if (processTaskManager3 != null) {
                        processTaskManager3.shutdown();
                    }
                    reset();
                    this.annotationProcessorStartIndex = 0;
                    this.stats.endTime = System.currentTimeMillis();
                    throw th;
                }
            } catch (Error e9) {
                e = e9;
                compilationUnitDeclarationRemoveNextUnit = null;
            } catch (AbortCompilation e10) {
                e = e10;
                processTaskManager2 = null;
            } catch (RuntimeException e11) {
                e = e11;
                compilationUnitDeclarationRemoveNextUnit = null;
            }
            reset();
            this.annotationProcessorStartIndex = 0;
            this.stats.endTime = System.currentTimeMillis();
        } catch (Throwable th3) {
            th = th3;
            processTaskManager3 = processTaskManager;
        }
    }

    public synchronized CompilationUnitDeclaration getUnitToProcess(int i) {
        if (i >= this.totalUnits) {
            return null;
        }
        CompilationUnitDeclaration[] compilationUnitDeclarationArr = this.unitsToProcess;
        CompilationUnitDeclaration compilationUnitDeclaration = compilationUnitDeclarationArr[i];
        if (this.annotationProcessorManager == null || i < this.annotationProcessorStartIndex) {
            compilationUnitDeclarationArr[i] = null;
        }
        return compilationUnitDeclaration;
    }

    public boolean shouldCleanup(int i) {
        return i < this.annotationProcessorStartIndex;
    }

    public void setBinaryTypes(ReferenceBinding[] referenceBindingArr) {
        this.referenceBindings = referenceBindingArr;
    }

    protected void handleInternalException(Throwable th, CompilationUnitDeclaration compilationUnitDeclaration, CompilationResult compilationResult) {
        int i;
        if (compilationResult == null && compilationUnitDeclaration != null) {
            compilationResult = compilationUnitDeclaration.compilationResult;
        }
        if (compilationResult == null && this.lookupEnvironment.unitBeingCompleted != null) {
            compilationResult = this.lookupEnvironment.unitBeingCompleted.compilationResult;
        }
        if (compilationResult == null) {
            synchronized (this) {
                CompilationUnitDeclaration[] compilationUnitDeclarationArr = this.unitsToProcess;
                if (compilationUnitDeclarationArr != null && (i = this.totalUnits) > 0) {
                    compilationResult = compilationUnitDeclarationArr[i - 1].compilationResult;
                }
            }
        }
        if (compilationResult != null) {
            String[] strArr = {Messages.bind(Messages.compilation_internalError, Util.getExceptionSummary(th))};
            compilationResult.record(this.problemReporter.createProblem(compilationResult.getFileName(), 0, strArr, strArr, 1, 0, 0, 0, 0), compilationUnitDeclaration, true);
            if (!compilationResult.hasBeenAccepted) {
                this.requestor.acceptResult(compilationResult.tagAsAccepted());
                return;
            }
        }
        th.printStackTrace();
    }

    protected void handleInternalException(AbortCompilation abortCompilation, CompilationUnitDeclaration compilationUnitDeclaration) {
        int i;
        if (abortCompilation.isSilent) {
            if (abortCompilation.silentException != null) {
                throw abortCompilation.silentException;
            }
            return;
        }
        CompilationResult compilationResult = abortCompilation.compilationResult;
        if (compilationResult == null && compilationUnitDeclaration != null) {
            compilationResult = compilationUnitDeclaration.compilationResult;
        }
        if (compilationResult == null && this.lookupEnvironment.unitBeingCompleted != null) {
            compilationResult = this.lookupEnvironment.unitBeingCompleted.compilationResult;
        }
        if (compilationResult == null) {
            synchronized (this) {
                CompilationUnitDeclaration[] compilationUnitDeclarationArr = this.unitsToProcess;
                if (compilationUnitDeclarationArr != null && (i = this.totalUnits) > 0) {
                    compilationResult = compilationUnitDeclarationArr[i - 1].compilationResult;
                }
            }
        }
        if (compilationResult != null && !compilationResult.hasBeenAccepted) {
            if (abortCompilation.problem != null) {
                CategorizedProblem categorizedProblem = abortCompilation.problem;
                CategorizedProblem[] categorizedProblemArr = compilationResult.problems;
                int i2 = 0;
                while (true) {
                    if (i2 < compilationResult.problemCount) {
                        if (categorizedProblemArr[i2] == categorizedProblem) {
                            break;
                        } else {
                            i2++;
                        }
                    } else {
                        if (categorizedProblem instanceof DefaultProblem) {
                            ((DefaultProblem) categorizedProblem).setOriginatingFileName(compilationResult.getFileName());
                        }
                        compilationResult.record(categorizedProblem, compilationUnitDeclaration, true);
                        break;
                    }
                }
            } else if (abortCompilation.exception != null) {
                handleInternalException(abortCompilation.exception, null, compilationResult);
                return;
            }
            if (compilationResult.hasBeenAccepted) {
                return;
            }
            this.requestor.acceptResult(compilationResult.tagAsAccepted());
            return;
        }
        abortCompilation.printStackTrace();
    }

    public void initializeParser() {
        this.parser = new Parser(this.problemReporter, this.options.parseLiteralExpressionsAsConstants);
    }

    protected void internalBeginToCompile(ICompilationUnit[] iCompilationUnitArr, int i) {
        CompilationResult compilationResult;
        CompilationUnitDeclaration compilationUnitDeclarationDietParse;
        if (!this.useSingleThread && i >= 10) {
            this.parser.readManager = new ReadManager(iCompilationUnitArr, i);
        }
        for (int i2 = 0; i2 < i; i2++) {
            try {
                try {
                    if (this.options.verbose) {
                        this.out.println(Messages.bind(Messages.compilation_request, (Object[]) new String[]{String.valueOf(i2 + 1), String.valueOf(i), new String(iCompilationUnitArr[i2].getFileName())}));
                    }
                    compilationResult = new CompilationResult(iCompilationUnitArr[i2], i2, i, this.options.maxProblemsPerUnit);
                    try {
                        long jCurrentTimeMillis = System.currentTimeMillis();
                        if (this.totalUnits < this.parseThreshold) {
                            compilationUnitDeclarationDietParse = this.parser.parse(iCompilationUnitArr[i2], compilationResult);
                        } else {
                            compilationUnitDeclarationDietParse = this.parser.dietParse(iCompilationUnitArr[i2], compilationResult);
                        }
                        long jCurrentTimeMillis2 = System.currentTimeMillis();
                        this.stats.parseTime += jCurrentTimeMillis2 - jCurrentTimeMillis;
                        this.lookupEnvironment.buildTypeBindings(compilationUnitDeclarationDietParse, null);
                        this.stats.resolveTime += System.currentTimeMillis() - jCurrentTimeMillis2;
                        addCompilationUnit(iCompilationUnitArr[i2], compilationUnitDeclarationDietParse);
                        ImportReference importReference = compilationUnitDeclarationDietParse.currentPackage;
                        if (importReference != null) {
                            compilationResult.recordPackageName(importReference.tokens);
                        }
                        iCompilationUnitArr[i2] = null;
                    } catch (AbortCompilation e) {
                        e = e;
                        if (e.compilationResult == null) {
                            e.compilationResult = compilationResult;
                        }
                        throw e;
                    }
                } catch (Throwable th) {
                    iCompilationUnitArr[i2] = null;
                    throw th;
                }
            } catch (AbortCompilation e2) {
                e = e2;
                compilationResult = null;
            }
        }
        if (this.parser.readManager != null) {
            this.parser.readManager.shutdown();
            this.parser.readManager = null;
        }
        this.lookupEnvironment.completeTypeBindings();
    }

    public void process(CompilationUnitDeclaration compilationUnitDeclaration, int i) throws Throwable {
        this.lookupEnvironment.unitBeingCompleted = compilationUnitDeclaration;
        long jCurrentTimeMillis = System.currentTimeMillis();
        this.parser.getMethodBodies(compilationUnitDeclaration);
        long jCurrentTimeMillis2 = System.currentTimeMillis();
        this.stats.parseTime += jCurrentTimeMillis2 - jCurrentTimeMillis;
        if (compilationUnitDeclaration.scope != null) {
            compilationUnitDeclaration.scope.faultInTypes();
        }
        if (compilationUnitDeclaration.scope != null) {
            compilationUnitDeclaration.scope.verifyMethods(this.lookupEnvironment.methodVerifier());
        }
        compilationUnitDeclaration.resolve();
        long jCurrentTimeMillis3 = System.currentTimeMillis();
        this.stats.resolveTime += jCurrentTimeMillis3 - jCurrentTimeMillis2;
        if (!this.options.ignoreMethodBodies) {
            compilationUnitDeclaration.analyseCode();
        }
        long jCurrentTimeMillis4 = System.currentTimeMillis();
        this.stats.analyzeTime += jCurrentTimeMillis4 - jCurrentTimeMillis3;
        if (!this.options.ignoreMethodBodies) {
            compilationUnitDeclaration.generateCode();
        }
        if (this.options.produceReferenceInfo && compilationUnitDeclaration.scope != null) {
            compilationUnitDeclaration.scope.storeDependencyInfo();
        }
        compilationUnitDeclaration.finalizeProblems();
        this.stats.generateTime += System.currentTimeMillis() - jCurrentTimeMillis4;
        compilationUnitDeclaration.compilationResult.totalUnitsKnown = this.totalUnits;
        this.lookupEnvironment.unitBeingCompleted = null;
    }

    protected void processAnnotations() {
        int i = this.annotationProcessorStartIndex;
        int i2 = this.totalUnits;
        ReferenceBinding[] referenceBindingArr = this.referenceBindings;
        if (i2 == 0 && referenceBindingArr == null) {
            return;
        }
        this.referenceBindings = null;
        while (true) {
            int i3 = i2;
            int i4 = i;
            i = i3;
            int i5 = i - i4;
            CompilationUnitDeclaration[] compilationUnitDeclarationArr = new CompilationUnitDeclaration[i5];
            int i6 = 0;
            while (i4 < i) {
                compilationUnitDeclarationArr[i6] = this.unitsToProcess[i4];
                i4++;
                i6++;
            }
            if (i6 != i5) {
                CompilationUnitDeclaration[] compilationUnitDeclarationArr2 = new CompilationUnitDeclaration[i6];
                System.arraycopy(compilationUnitDeclarationArr, 0, compilationUnitDeclarationArr2, 0, i6);
                compilationUnitDeclarationArr = compilationUnitDeclarationArr2;
            }
            this.annotationProcessorManager.processAnnotations(compilationUnitDeclarationArr, referenceBindingArr, false);
            int i7 = this.totalUnits;
            if (i < i7) {
                int i8 = i7 - i;
                CompilationUnitDeclaration[] compilationUnitDeclarationArr3 = new CompilationUnitDeclaration[i8];
                System.arraycopy(this.unitsToProcess, i, compilationUnitDeclarationArr3, 0, i8);
                this.annotationProcessorManager.processAnnotations(compilationUnitDeclarationArr3, referenceBindingArr, false);
            }
            this.annotationProcessorStartIndex = i;
            ICompilationUnit[] newUnits = this.annotationProcessorManager.getNewUnits();
            int length = newUnits.length;
            ReferenceBinding[] newClassFiles = this.annotationProcessorManager.getNewClassFiles();
            int length2 = newClassFiles.length;
            if (length != 0) {
                ICompilationUnit[] iCompilationUnitArr = (ICompilationUnit[]) newUnits.clone();
                try {
                    try {
                        this.lookupEnvironment.isProcessingAnnotations = true;
                        internalBeginToCompile(newUnits, length);
                        this.lookupEnvironment.isProcessingAnnotations = false;
                        this.annotationProcessorManager.reset();
                        i2 = this.totalUnits;
                        this.annotationProcessorStartIndex = i2;
                    } catch (SourceTypeCollisionException e) {
                        e.newAnnotationProcessorUnits = iCompilationUnitArr;
                        throw e;
                    }
                } catch (Throwable th) {
                    this.lookupEnvironment.isProcessingAnnotations = false;
                    this.annotationProcessorManager.reset();
                    throw th;
                }
            } else {
                this.annotationProcessorManager.reset();
                i2 = i;
            }
            if (length == 0 && length2 == 0) {
                break;
            } else {
                referenceBindingArr = newClassFiles;
            }
        }
        this.annotationProcessorManager.processAnnotations(null, null, true);
        ICompilationUnit[] newUnits2 = this.annotationProcessorManager.getNewUnits();
        int length3 = newUnits2.length;
        if (length3 != 0) {
            ICompilationUnit[] iCompilationUnitArr2 = (ICompilationUnit[]) newUnits2.clone();
            try {
                try {
                    this.lookupEnvironment.isProcessingAnnotations = true;
                    internalBeginToCompile(newUnits2, length3);
                    this.lookupEnvironment.isProcessingAnnotations = false;
                    this.annotationProcessorManager.reset();
                } catch (SourceTypeCollisionException e2) {
                    e2.isLastRound = true;
                    e2.newAnnotationProcessorUnits = iCompilationUnitArr2;
                    throw e2;
                }
            } catch (Throwable th2) {
                this.lookupEnvironment.isProcessingAnnotations = false;
                this.annotationProcessorManager.reset();
                throw th2;
            }
        } else {
            this.annotationProcessorManager.reset();
        }
        this.annotationProcessorStartIndex = this.totalUnits;
    }

    public void reset() {
        this.lookupEnvironment.reset();
        this.parser.scanner.source = null;
        this.unitsToProcess = null;
        IDebugRequestor iDebugRequestor = DebugRequestor;
        if (iDebugRequestor != null) {
            iDebugRequestor.reset();
        }
        this.problemReporter.reset();
    }

    public CompilationUnitDeclaration resolve(CompilationUnitDeclaration compilationUnitDeclaration, ICompilationUnit iCompilationUnit, boolean z, boolean z2, boolean z3) throws Throwable {
        try {
            if (compilationUnitDeclaration == null) {
                this.parseThreshold = 0;
                beginToCompile(new ICompilationUnit[]{iCompilationUnit});
                for (int i = 0; i < this.totalUnits; i++) {
                    CompilationUnitDeclaration compilationUnitDeclaration2 = this.unitsToProcess[i];
                    if (compilationUnitDeclaration2 != null && compilationUnitDeclaration2.compilationResult.compilationUnit == iCompilationUnit) {
                        compilationUnitDeclaration = this.unitsToProcess[i];
                        break;
                    }
                }
                if (compilationUnitDeclaration == null) {
                    compilationUnitDeclaration = this.unitsToProcess[0];
                }
            } else {
                this.lookupEnvironment.buildTypeBindings(compilationUnitDeclaration, null);
                this.lookupEnvironment.completeTypeBindings();
            }
            this.lookupEnvironment.unitBeingCompleted = compilationUnitDeclaration;
            this.parser.getMethodBodies(compilationUnitDeclaration);
            if (compilationUnitDeclaration.scope != null) {
                compilationUnitDeclaration.scope.faultInTypes();
                if (compilationUnitDeclaration.scope != null && z) {
                    compilationUnitDeclaration.scope.verifyMethods(this.lookupEnvironment.methodVerifier());
                }
                compilationUnitDeclaration.resolve();
                if (z2) {
                    compilationUnitDeclaration.analyseCode();
                }
                if (z3) {
                    compilationUnitDeclaration.generateCode();
                }
                compilationUnitDeclaration.finalizeProblems();
            }
            CompilationUnitDeclaration[] compilationUnitDeclarationArr = this.unitsToProcess;
            if (compilationUnitDeclarationArr != null) {
                compilationUnitDeclarationArr[0] = null;
            }
            this.requestor.acceptResult(compilationUnitDeclaration.compilationResult.tagAsAccepted());
            return compilationUnitDeclaration;
        } catch (Error e) {
            handleInternalException(e, compilationUnitDeclaration, null);
            throw e;
        } catch (AbortCompilation e2) {
            handleInternalException(e2, compilationUnitDeclaration);
            return compilationUnitDeclaration == null ? this.unitsToProcess[0] : compilationUnitDeclaration;
        } catch (RuntimeException e3) {
            handleInternalException(e3, compilationUnitDeclaration, null);
            throw e3;
        }
    }

    public CompilationUnitDeclaration resolve(ICompilationUnit iCompilationUnit, boolean z, boolean z2, boolean z3) {
        return resolve(null, iCompilationUnit, z, z2, z3);
    }
}
