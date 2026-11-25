package org.eclipse.jdt.internal.compiler.apt.dispatch;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import org.eclipse.jdt.internal.compiler.Compiler;
import org.eclipse.jdt.internal.compiler.apt.model.ElementsImpl;
import org.eclipse.jdt.internal.compiler.apt.model.Factory;
import org.eclipse.jdt.internal.compiler.apt.model.TypesImpl;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants;
import org.eclipse.jdt.internal.compiler.env.ICompilationUnit;
import org.eclipse.jdt.internal.compiler.lookup.LookupEnvironment;
import org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding;

/* JADX INFO: loaded from: classes14.dex */
public abstract class BaseProcessingEnvImpl implements ProcessingEnvironment {
    protected Compiler _compiler;
    protected Filer _filer;
    protected Messager _messager;
    protected Map<String, String> _processorOptions;
    private List<ICompilationUnit> _addedUnits = new ArrayList();
    private List<ReferenceBinding> _addedClassFiles = new ArrayList();
    private List<ICompilationUnit> _deletedUnits = new ArrayList();
    protected Elements _elementUtils = new ElementsImpl(this);
    protected Types _typeUtils = new TypesImpl(this);
    private Factory _factory = new Factory(this);
    private boolean _errorRaised = false;

    public void addNewUnit(ICompilationUnit iCompilationUnit) {
        this._addedUnits.add(iCompilationUnit);
    }

    public void addNewClassFile(ReferenceBinding referenceBinding) {
        this._addedClassFiles.add(referenceBinding);
    }

    public Compiler getCompiler() {
        return this._compiler;
    }

    public ICompilationUnit[] getDeletedUnits() {
        ICompilationUnit[] iCompilationUnitArr = new ICompilationUnit[this._deletedUnits.size()];
        this._deletedUnits.toArray(iCompilationUnitArr);
        return iCompilationUnitArr;
    }

    public ICompilationUnit[] getNewUnits() {
        ICompilationUnit[] iCompilationUnitArr = new ICompilationUnit[this._addedUnits.size()];
        this._addedUnits.toArray(iCompilationUnitArr);
        return iCompilationUnitArr;
    }

    public Elements getElementUtils() {
        return this._elementUtils;
    }

    public Filer getFiler() {
        return this._filer;
    }

    public Messager getMessager() {
        return this._messager;
    }

    public Map<String, String> getOptions() {
        return this._processorOptions;
    }

    public Types getTypeUtils() {
        return this._typeUtils;
    }

    public LookupEnvironment getLookupEnvironment() {
        return this._compiler.lookupEnvironment;
    }

    public SourceVersion getSourceVersion() {
        if (this._compiler.options.sourceLevel <= ClassFileConstants.JDK1_5) {
            return SourceVersion.RELEASE_5;
        }
        if (this._compiler.options.sourceLevel == ClassFileConstants.JDK1_6) {
            return SourceVersion.RELEASE_6;
        }
        try {
            return SourceVersion.valueOf("RELEASE_7");
        } catch (IllegalArgumentException unused) {
            return SourceVersion.RELEASE_6;
        }
    }

    public void reset() {
        this._addedUnits.clear();
        this._addedClassFiles.clear();
        this._deletedUnits.clear();
    }

    public boolean errorRaised() {
        return this._errorRaised;
    }

    public void setErrorRaised(boolean z) {
        this._errorRaised = true;
    }

    public Factory getFactory() {
        return this._factory;
    }

    public ReferenceBinding[] getNewClassFiles() {
        ReferenceBinding[] referenceBindingArr = new ReferenceBinding[this._addedClassFiles.size()];
        this._addedClassFiles.toArray(referenceBindingArr);
        return referenceBindingArr;
    }
}
