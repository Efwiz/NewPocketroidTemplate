package org.eclipse.jdt.internal.compiler.tool;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import javax.annotation.processing.Processor;
import javax.tools.Diagnostic;
import javax.tools.DiagnosticListener;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import org.eclipse.jdt.core.compiler.CategorizedProblem;
import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.core.compiler.CompilationProgress;
import org.eclipse.jdt.internal.compiler.ClassFile;
import org.eclipse.jdt.internal.compiler.CompilationResult;
import org.eclipse.jdt.internal.compiler.IErrorHandlingPolicy;
import org.eclipse.jdt.internal.compiler.IProblemFactory;
import org.eclipse.jdt.internal.compiler.batch.ClasspathJsr199;
import org.eclipse.jdt.internal.compiler.batch.CompilationUnit;
import org.eclipse.jdt.internal.compiler.batch.FileSystem;
import org.eclipse.jdt.internal.compiler.batch.Main;
import org.eclipse.jdt.internal.compiler.problem.AbortCompilationUnit;
import org.eclipse.jdt.internal.compiler.problem.DefaultProblem;
import org.eclipse.jdt.internal.compiler.problem.DefaultProblemFactory;
import org.eclipse.jdt.internal.compiler.util.Messages;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;

/* JADX INFO: loaded from: classes14.dex */
public class EclipseCompilerImpl extends Main {
    private static final CompilationUnit[] NO_UNITS = new CompilationUnit[0];
    Iterable<? extends JavaFileObject> compilationUnits;
    public DiagnosticListener<? super JavaFileObject> diagnosticListener;
    public JavaFileManager fileManager;
    private HashMap<CompilationUnit, JavaFileObject> javaFileObjectMap;
    protected Processor[] processors;

    public EclipseCompilerImpl(PrintWriter printWriter, PrintWriter printWriter2, boolean z) {
        super(printWriter, printWriter2, z, null, null);
    }

    public boolean call() {
        try {
            try {
                if (this.proceed) {
                    this.globalProblemsCount = 0;
                    this.globalErrorsCount = 0;
                    this.globalWarningsCount = 0;
                    this.globalTasksCount = 0;
                    this.exportedClassFilesCounter = 0;
                    performCompilation();
                }
                cleanup();
                return this.globalErrorsCount == 0;
            } catch (IllegalArgumentException e) {
                this.logger.logException(e);
                if (this.systemExitWhenFinished) {
                    cleanup();
                    System.exit(-1);
                }
                cleanup();
                return false;
            } catch (RuntimeException e2) {
                this.logger.logException(e2);
                cleanup();
                return false;
            }
        } catch (Throwable th) {
            cleanup();
            throw th;
        }
    }

    private void cleanup() {
        this.logger.flush();
        this.logger.close();
        this.processors = null;
        try {
            JavaFileManager javaFileManager = this.fileManager;
            if (javaFileManager != null) {
                javaFileManager.flush();
            }
        } catch (IOException unused) {
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.batch.Main
    public CompilationUnit[] getCompilationUnits() {
        if (this.compilationUnits == null) {
            return NO_UNITS;
        }
        ArrayList arrayList = new ArrayList();
        for (final JavaFileObject javaFileObject : this.compilationUnits) {
            if (javaFileObject.getKind() != JavaFileObject.Kind.SOURCE) {
                throw new IllegalArgumentException();
            }
            CompilationUnit compilationUnit = new CompilationUnit(null, javaFileObject.getName().replace('\\', '/'), null) { // from class: org.eclipse.jdt.internal.compiler.tool.EclipseCompilerImpl.1
                @Override // org.eclipse.jdt.internal.compiler.batch.CompilationUnit, org.eclipse.jdt.internal.compiler.env.ICompilationUnit
                public char[] getContents() {
                    try {
                        return javaFileObject.getCharContent(true).toString().toCharArray();
                    } catch (IOException e) {
                        e.printStackTrace();
                        throw new AbortCompilationUnit(null, e, null);
                    }
                }
            };
            arrayList.add(compilationUnit);
            this.javaFileObjectMap.put(compilationUnit, javaFileObject);
        }
        CompilationUnit[] compilationUnitArr = new CompilationUnit[arrayList.size()];
        arrayList.toArray(compilationUnitArr);
        return compilationUnitArr;
    }

    @Override // org.eclipse.jdt.internal.compiler.batch.Main
    public IErrorHandlingPolicy getHandlingPolicy() {
        return new IErrorHandlingPolicy() { // from class: org.eclipse.jdt.internal.compiler.tool.EclipseCompilerImpl.2
            @Override // org.eclipse.jdt.internal.compiler.IErrorHandlingPolicy
            public boolean ignoreAllErrors() {
                return false;
            }

            @Override // org.eclipse.jdt.internal.compiler.IErrorHandlingPolicy
            public boolean proceedOnErrors() {
                return false;
            }

            @Override // org.eclipse.jdt.internal.compiler.IErrorHandlingPolicy
            public boolean stopOnFirstError() {
                return false;
            }
        };
    }

    @Override // org.eclipse.jdt.internal.compiler.batch.Main
    public IProblemFactory getProblemFactory() {
        return new DefaultProblemFactory() { // from class: org.eclipse.jdt.internal.compiler.tool.EclipseCompilerImpl.3
            @Override // org.eclipse.jdt.internal.compiler.problem.DefaultProblemFactory, org.eclipse.jdt.internal.compiler.IProblemFactory
            public CategorizedProblem createProblem(final char[] cArr, final int i, final String[] strArr, String[] strArr2, final int i2, final int i3, final int i4, final int i5, final int i6) {
                DiagnosticListener<? super JavaFileObject> diagnosticListener = EclipseCompilerImpl.this.diagnosticListener;
                if (diagnosticListener != null) {
                    diagnosticListener.report(new Diagnostic<JavaFileObject>() { // from class: org.eclipse.jdt.internal.compiler.tool.EclipseCompilerImpl.3.1
                        public String getCode() {
                            return Integer.toString(i);
                        }

                        public long getColumnNumber() {
                            return i6;
                        }

                        public long getEndPosition() {
                            return i4;
                        }

                        public Diagnostic.Kind getKind() {
                            int i7 = i2;
                            if ((i7 & 1) != 0) {
                                return Diagnostic.Kind.ERROR;
                            }
                            if ((i7 & 32) != 0) {
                                return Diagnostic.Kind.WARNING;
                            }
                            return Diagnostic.Kind.OTHER;
                        }

                        public long getLineNumber() {
                            return i5;
                        }

                        public String getMessage(Locale locale) {
                            if (locale != null) {
                                setLocale(locale);
                            }
                            return getLocalizedMessage(i, strArr);
                        }

                        public long getPosition() {
                            return i3;
                        }

                        public JavaFileObject getSource() {
                            File file = new File(new String(cArr));
                            if (file.exists()) {
                                return new EclipseFileObject(null, file.toURI(), JavaFileObject.Kind.SOURCE, null);
                            }
                            return null;
                        }

                        public long getStartPosition() {
                            return i3;
                        }
                    });
                }
                return super.createProblem(cArr, i, strArr, strArr2, i2, i3, i4, i5, i6);
            }

            @Override // org.eclipse.jdt.internal.compiler.problem.DefaultProblemFactory, org.eclipse.jdt.internal.compiler.IProblemFactory
            public CategorizedProblem createProblem(final char[] cArr, final int i, final String[] strArr, int i2, String[] strArr2, final int i3, final int i4, final int i5, final int i6, final int i7) {
                DiagnosticListener<? super JavaFileObject> diagnosticListener = EclipseCompilerImpl.this.diagnosticListener;
                if (diagnosticListener != null) {
                    diagnosticListener.report(new Diagnostic<JavaFileObject>() { // from class: org.eclipse.jdt.internal.compiler.tool.EclipseCompilerImpl.3.2
                        public String getCode() {
                            return Integer.toString(i);
                        }

                        public long getColumnNumber() {
                            return i7;
                        }

                        public long getEndPosition() {
                            return i5;
                        }

                        public Diagnostic.Kind getKind() {
                            int i8 = i3;
                            if ((i8 & 1) != 0) {
                                return Diagnostic.Kind.ERROR;
                            }
                            if ((i8 & 1024) != 0) {
                                return Diagnostic.Kind.NOTE;
                            }
                            if ((i8 & 32) != 0) {
                                return Diagnostic.Kind.WARNING;
                            }
                            return Diagnostic.Kind.OTHER;
                        }

                        public long getLineNumber() {
                            return i6;
                        }

                        public String getMessage(Locale locale) {
                            if (locale != null) {
                                setLocale(locale);
                            }
                            return getLocalizedMessage(i, strArr);
                        }

                        public long getPosition() {
                            return i4;
                        }

                        public JavaFileObject getSource() {
                            File file = new File(new String(cArr));
                            if (file.exists()) {
                                return new EclipseFileObject(null, file.toURI(), JavaFileObject.Kind.SOURCE, null);
                            }
                            return null;
                        }

                        public long getStartPosition() {
                            return i4;
                        }
                    });
                }
                return super.createProblem(cArr, i, strArr, i2, strArr2, i3, i4, i5, i6, i7);
            }
        };
    }

    @Override // org.eclipse.jdt.internal.compiler.batch.Main
    protected void initialize(PrintWriter printWriter, PrintWriter printWriter2, boolean z, Map<String, String> map, CompilationProgress compilationProgress) {
        super.initialize(printWriter, printWriter2, z, map, compilationProgress);
        this.javaFileObjectMap = new HashMap<>();
    }

    @Override // org.eclipse.jdt.internal.compiler.batch.Main
    protected void initializeAnnotationProcessorManager() {
        super.initializeAnnotationProcessorManager();
        if (this.batchCompiler.annotationProcessorManager != null && this.processors != null) {
            this.batchCompiler.annotationProcessorManager.setProcessors(this.processors);
        } else if (this.processors != null) {
            throw new UnsupportedOperationException("Cannot handle annotation processing");
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.batch.Main
    public void outputClassFiles(CompilationResult compilationResult) throws Throwable {
        int i;
        if (compilationResult != null) {
            if (!compilationResult.hasErrors() || this.proceedOnError) {
                ClassFile[] classFiles = compilationResult.getClassFiles();
                boolean zHasLocation = this.fileManager.hasLocation(StandardLocation.CLASS_OUTPUT);
                String str = this.destinationPath;
                if (str != null) {
                    new File(str).mkdirs();
                }
                int length = classFiles.length;
                int i2 = 0;
                int i3 = 0;
                while (i3 < length) {
                    ClassFile classFile = classFiles[i3];
                    char[] cArrFileName = classFile.fileName();
                    int length2 = cArrFileName.length;
                    char[] cArr = new char[length2 + 6];
                    System.arraycopy(cArrFileName, i2, cArr, i2, length2);
                    System.arraycopy(SuffixConstants.SUFFIX_class, i2, cArr, length2, 6);
                    CharOperation.replace(cArr, '/', File.separatorChar);
                    String str2 = new String(cArr);
                    if (this.compilerOptions.verbose) {
                        this.out.println(Messages.bind(Messages.compilation_write, (Object[]) new String[]{String.valueOf(this.exportedClassFilesCounter + 1), str2}));
                    }
                    try {
                        JavaFileObject javaFileForOutput = this.fileManager.getJavaFileForOutput(StandardLocation.CLASS_OUTPUT, new String(cArrFileName), JavaFileObject.Kind.CLASS, this.javaFileObjectMap.get(compilationResult.compilationUnit));
                        if (zHasLocation) {
                            if (str != null) {
                                int iLastIndexOf = CharOperation.lastIndexOf(File.separatorChar, cArr);
                                if (iLastIndexOf != -1) {
                                    new File(str, str2.substring(0, iLastIndexOf)).mkdirs();
                                }
                            } else {
                                String path = javaFileForOutput.toUri().getPath();
                                int iLastIndexOf2 = path.lastIndexOf(47);
                                if (iLastIndexOf2 != -1) {
                                    new File(path.substring(0, iLastIndexOf2)).mkdirs();
                                }
                            }
                        }
                        Throwable th = null;
                        try {
                            OutputStream outputStreamOpenOutputStream = javaFileForOutput.openOutputStream();
                            try {
                                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStreamOpenOutputStream);
                                try {
                                    i = 0;
                                    try {
                                        bufferedOutputStream.write(classFile.header, 0, classFile.headerOffset);
                                        bufferedOutputStream.write(classFile.contents, 0, classFile.contentsOffset);
                                        bufferedOutputStream.flush();
                                        try {
                                            bufferedOutputStream.close();
                                            if (outputStreamOpenOutputStream != null) {
                                                try {
                                                    outputStreamOpenOutputStream.close();
                                                } catch (Throwable th2) {
                                                    th = th2;
                                                    if (th == null) {
                                                        th = th;
                                                    } else if (th != th) {
                                                        try {
                                                            th.addSuppressed(th);
                                                        } catch (IOException e) {
                                                            e = e;
                                                            this.logger.logNoClassFileCreated(str, str2, e);
                                                            this.logger.logClassFile(zHasLocation, str, str2);
                                                            this.exportedClassFilesCounter++;
                                                            i3++;
                                                            i2 = i;
                                                        }
                                                    }
                                                    throw th;
                                                }
                                            }
                                        } catch (Throwable th3) {
                                            th = th3;
                                            if (th == null) {
                                                th = th;
                                            } else if (th != th) {
                                                th.addSuppressed(th);
                                            }
                                            if (outputStreamOpenOutputStream != null) {
                                                outputStreamOpenOutputStream.close();
                                            }
                                            throw th;
                                        }
                                    } catch (Throwable th4) {
                                        th = th4;
                                        th = th;
                                        bufferedOutputStream.close();
                                        throw th;
                                    }
                                } catch (Throwable th5) {
                                    th = th5;
                                    i = 0;
                                }
                            } catch (Throwable th6) {
                                th = th6;
                                i = 0;
                            }
                        } catch (Throwable th7) {
                            th = th7;
                            i = 0;
                        }
                    } catch (IOException e2) {
                        e = e2;
                        i = 0;
                    }
                    this.logger.logClassFile(zHasLocation, str, str2);
                    this.exportedClassFilesCounter++;
                    i3++;
                    i2 = i;
                }
                this.batchCompiler.lookupEnvironment.releaseClassFiles(classFiles);
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:29:0x006e  */
    /* JADX WARN: Code duplicated, block: B:60:0x00ff  */
    @Override // org.eclipse.jdt.internal.compiler.batch.Main
    protected void setPaths(ArrayList arrayList, String str, ArrayList arrayList2, ArrayList arrayList3, ArrayList arrayList4, ArrayList arrayList5, String str2) {
        boolean z;
        ArrayList arrayList6 = new ArrayList();
        EclipseFileManager eclipseFileManager = this.fileManager;
        EclipseFileManager eclipseFileManager2 = eclipseFileManager instanceof EclipseFileManager ? eclipseFileManager : null;
        EclipseFileManager eclipseFileManager3 = eclipseFileManager instanceof StandardJavaFileManager ? eclipseFileManager : null;
        if (eclipseFileManager2 != null && (eclipseFileManager2.flags & 4) == 0 && (eclipseFileManager2.flags & 2) != 0) {
            arrayList6.addAll(handleEndorseddirs(null));
        }
        boolean z2 = true;
        int i = 0;
        if (eclipseFileManager3 != null) {
            Iterable location = eclipseFileManager3.getLocation(StandardLocation.PLATFORM_CLASS_PATH);
            if (location != null) {
                Iterator it = location.iterator();
                z = false;
                while (it.hasNext()) {
                    FileSystem.Classpath classpath = FileSystem.getClasspath(((File) it.next()).getAbsolutePath(), null, null, this.options);
                    if (classpath != null) {
                        arrayList6.add(classpath);
                        z = true;
                    }
                }
            } else {
                z = false;
            }
        } else if (eclipseFileManager != null) {
            arrayList6.add(new ClasspathJsr199(this.fileManager, StandardLocation.PLATFORM_CLASS_PATH));
            z = true;
        } else {
            z = false;
        }
        if (eclipseFileManager2 != null && (eclipseFileManager2.flags & 1) == 0 && (eclipseFileManager2.flags & 2) != 0) {
            arrayList6.addAll(handleExtdirs(null));
        }
        if (eclipseFileManager3 != null) {
            Iterable location2 = eclipseFileManager3.getLocation(StandardLocation.SOURCE_PATH);
            if (location2 != null) {
                Iterator it2 = location2.iterator();
                while (it2.hasNext()) {
                    FileSystem.Classpath classpath2 = FileSystem.getClasspath(((File) it2.next()).getAbsolutePath(), null, null, this.options);
                    if (classpath2 != null) {
                        arrayList6.add(classpath2);
                    }
                }
            }
            Iterable location3 = eclipseFileManager3.getLocation(StandardLocation.CLASS_PATH);
            if (location3 != null) {
                Iterator it3 = location3.iterator();
                boolean z3 = false;
                while (it3.hasNext()) {
                    FileSystem.Classpath classpath3 = FileSystem.getClasspath(((File) it3.next()).getAbsolutePath(), null, null, this.options);
                    if (classpath3 != null) {
                        arrayList6.add(classpath3);
                        z3 = true;
                    }
                }
                z2 = z3;
            } else {
                z2 = false;
            }
        } else if (eclipseFileManager != null) {
            if (this.fileManager.hasLocation(StandardLocation.SOURCE_PATH)) {
                arrayList6.add(new ClasspathJsr199(this.fileManager, StandardLocation.SOURCE_PATH));
            }
            arrayList6.add(new ClasspathJsr199(this.fileManager, StandardLocation.CLASS_PATH));
        } else {
            z2 = false;
        }
        if (this.checkedClasspaths == null) {
            if (!z) {
                arrayList6.addAll(handleBootclasspath(null, null));
            }
            if (!z2) {
                arrayList6.addAll(handleClasspath(null, null));
            }
        }
        ArrayList arrayListNormalize = FileSystem.ClasspathNormalizer.normalize(arrayList6);
        int size = arrayListNormalize.size();
        if (size != 0) {
            this.checkedClasspaths = new FileSystem.Classpath[size];
            Iterator it4 = arrayListNormalize.iterator();
            while (it4.hasNext()) {
                this.checkedClasspaths[i] = (FileSystem.Classpath) it4.next();
                i++;
            }
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.batch.Main
    protected void loggingExtraProblems() throws Throwable {
        super.loggingExtraProblems();
        for (final CategorizedProblem categorizedProblem : this.extraProblems) {
            DiagnosticListener<? super JavaFileObject> diagnosticListener = this.diagnosticListener;
            if (diagnosticListener != null) {
                diagnosticListener.report(new Diagnostic<JavaFileObject>() { // from class: org.eclipse.jdt.internal.compiler.tool.EclipseCompilerImpl.4
                    public String getCode() {
                        return null;
                    }

                    public long getColumnNumber() {
                        CategorizedProblem categorizedProblem2 = categorizedProblem;
                        if (categorizedProblem2 instanceof DefaultProblem) {
                            return ((DefaultProblem) categorizedProblem2).column;
                        }
                        return -1L;
                    }

                    public long getEndPosition() {
                        CategorizedProblem categorizedProblem2 = categorizedProblem;
                        if (categorizedProblem2 instanceof DefaultProblem) {
                            return ((DefaultProblem) categorizedProblem2).getSourceEnd();
                        }
                        return -1L;
                    }

                    public Diagnostic.Kind getKind() {
                        if (categorizedProblem.isError()) {
                            return Diagnostic.Kind.ERROR;
                        }
                        if (categorizedProblem.isWarning()) {
                            return Diagnostic.Kind.WARNING;
                        }
                        CategorizedProblem categorizedProblem2 = categorizedProblem;
                        if ((categorizedProblem2 instanceof DefaultProblem) && ((DefaultProblem) categorizedProblem2).isInfo()) {
                            return Diagnostic.Kind.NOTE;
                        }
                        return Diagnostic.Kind.OTHER;
                    }

                    public long getLineNumber() {
                        CategorizedProblem categorizedProblem2 = categorizedProblem;
                        if (categorizedProblem2 instanceof DefaultProblem) {
                            return ((DefaultProblem) categorizedProblem2).getSourceLineNumber();
                        }
                        return -1L;
                    }

                    public String getMessage(Locale locale) {
                        return categorizedProblem.getMessage();
                    }

                    public long getPosition() {
                        CategorizedProblem categorizedProblem2 = categorizedProblem;
                        if (categorizedProblem2 instanceof DefaultProblem) {
                            return ((DefaultProblem) categorizedProblem2).getSourceStart();
                        }
                        return -1L;
                    }

                    public JavaFileObject getSource() {
                        if (categorizedProblem instanceof DefaultProblem) {
                            File file = new File(new String(((DefaultProblem) categorizedProblem).getOriginatingFileName()));
                            if (file.exists()) {
                                return new EclipseFileObject(null, file.toURI(), JavaFileObject.Kind.SOURCE, EclipseCompilerImpl.this.fileManager instanceof EclipseFileManager ? EclipseCompilerImpl.this.fileManager.charset : Charset.defaultCharset());
                            }
                        }
                        return null;
                    }

                    public long getStartPosition() {
                        return getPosition();
                    }
                });
            }
        }
    }
}
