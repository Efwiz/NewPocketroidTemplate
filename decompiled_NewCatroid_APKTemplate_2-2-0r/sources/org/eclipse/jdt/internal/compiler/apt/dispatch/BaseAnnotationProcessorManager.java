package org.eclipse.jdt.internal.compiler.apt.dispatch;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.jdt.internal.compiler.AbstractAnnotationProcessorManager;
import org.eclipse.jdt.internal.compiler.Compiler;
import org.eclipse.jdt.internal.compiler.ast.CompilationUnitDeclaration;
import org.eclipse.jdt.internal.compiler.env.ICompilationUnit;
import org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding;

/* JADX INFO: loaded from: classes14.dex */
public abstract class BaseAnnotationProcessorManager extends AbstractAnnotationProcessorManager implements IProcessorProvider {
    protected PrintWriter _err;
    protected PrintWriter _out;
    protected BaseProcessingEnvImpl _processingEnv;
    protected int _round;
    protected boolean _isFirstRound = true;
    protected List<ProcessorInfo> _processors = new ArrayList();
    protected boolean _printProcessorInfo = false;
    protected boolean _printRounds = false;

    @Override // org.eclipse.jdt.internal.compiler.AbstractAnnotationProcessorManager
    public void configure(Object obj, String[] strArr) {
        throw new UnsupportedOperationException();
    }

    @Override // org.eclipse.jdt.internal.compiler.AbstractAnnotationProcessorManager
    public void configureFromPlatform(Compiler compiler, Object obj, Object obj2) {
        throw new UnsupportedOperationException();
    }

    @Override // org.eclipse.jdt.internal.compiler.apt.dispatch.IProcessorProvider
    public List<ProcessorInfo> getDiscoveredProcessors() {
        return this._processors;
    }

    @Override // org.eclipse.jdt.internal.compiler.AbstractAnnotationProcessorManager
    public ICompilationUnit[] getDeletedUnits() {
        return this._processingEnv.getDeletedUnits();
    }

    @Override // org.eclipse.jdt.internal.compiler.AbstractAnnotationProcessorManager
    public ICompilationUnit[] getNewUnits() {
        return this._processingEnv.getNewUnits();
    }

    @Override // org.eclipse.jdt.internal.compiler.AbstractAnnotationProcessorManager
    public ReferenceBinding[] getNewClassFiles() {
        return this._processingEnv.getNewClassFiles();
    }

    @Override // org.eclipse.jdt.internal.compiler.AbstractAnnotationProcessorManager
    public void reset() {
        this._processingEnv.reset();
    }

    @Override // org.eclipse.jdt.internal.compiler.AbstractAnnotationProcessorManager
    public void setErr(PrintWriter printWriter) {
        this._err = printWriter;
    }

    @Override // org.eclipse.jdt.internal.compiler.AbstractAnnotationProcessorManager
    public void setOut(PrintWriter printWriter) {
        this._out = printWriter;
    }

    @Override // org.eclipse.jdt.internal.compiler.AbstractAnnotationProcessorManager
    public void setProcessors(Object[] objArr) {
        throw new UnsupportedOperationException();
    }

    @Override // org.eclipse.jdt.internal.compiler.AbstractAnnotationProcessorManager
    public void processAnnotations(CompilationUnitDeclaration[] compilationUnitDeclarationArr, ReferenceBinding[] referenceBindingArr, boolean z) {
        RoundEnvImpl roundEnvImpl = new RoundEnvImpl(compilationUnitDeclarationArr, referenceBindingArr, z, this._processingEnv);
        if (this._isFirstRound) {
            this._isFirstRound = false;
        }
        PrintWriter printWriter = this._printProcessorInfo ? this._out : null;
        PrintWriter printWriter2 = this._printRounds ? this._out : null;
        if (printWriter2 != null) {
            StringBuilder sb = new StringBuilder("Round ");
            int i = this._round + 1;
            this._round = i;
            printWriter2.println(sb.append(i).append(':').toString());
        }
        new RoundDispatcher(this, roundEnvImpl, roundEnvImpl.getRootAnnotations(), printWriter, printWriter2).round();
    }
}
