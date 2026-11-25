package org.eclipse.jdt.internal.compiler.lookup;

import java.util.Set;
import org.eclipse.jdt.core.compiler.CharOperation;

/* JADX INFO: loaded from: classes14.dex */
public class InferenceVariable extends TypeVariableBinding {
    long nullHints;
    private InferenceVariable prototype;
    InvocationSite site;
    TypeBinding typeParameter;
    int varId;

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public boolean isCompatibleWith(TypeBinding typeBinding, Scope scope) {
        return true;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeVariableBinding, org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public boolean isProperType(boolean z) {
        return false;
    }

    public InferenceVariable(TypeBinding typeBinding, int i, int i2, InvocationSite invocationSite, LookupEnvironment lookupEnvironment, ReferenceBinding referenceBinding) {
        this(typeBinding, i, invocationSite, CharOperation.concat(typeBinding.shortReadableName(), Integer.toString(i2).toCharArray(), '#'), lookupEnvironment, referenceBinding);
        this.varId = i2;
    }

    private InferenceVariable(TypeBinding typeBinding, int i, InvocationSite invocationSite, char[] cArr, LookupEnvironment lookupEnvironment, ReferenceBinding referenceBinding) {
        super(cArr, null, i, lookupEnvironment);
        this.site = invocationSite;
        this.typeParameter = typeBinding;
        this.tagBits |= typeBinding.tagBits & TagBits.AnnotationNullMASK;
        if (typeBinding.isTypeVariable()) {
            TypeVariableBinding typeVariableBinding = (TypeVariableBinding) typeBinding;
            if (typeVariableBinding.firstBound != null) {
                long j = typeVariableBinding.firstBound.tagBits & TagBits.AnnotationNullMASK;
                if (j == 72057594037927936L) {
                    this.tagBits = j | this.tagBits;
                } else {
                    this.nullHints = j | this.nullHints;
                }
            }
        }
        this.superclass = referenceBinding;
        this.prototype = this;
    }

    void updateSourceName(int i) {
        int iIndexOf = CharOperation.indexOf('#', this.sourceName);
        this.varId = i;
        this.sourceName = CharOperation.concat(CharOperation.subarray(this.sourceName, 0, iIndexOf), Integer.toString(this.varId).toCharArray(), '#');
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeVariableBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public TypeBinding clone(TypeBinding typeBinding) {
        InferenceVariable inferenceVariable = new InferenceVariable(this.typeParameter, this.rank, this.site, this.sourceName, this.environment, this.superclass);
        inferenceVariable.tagBits = this.tagBits;
        inferenceVariable.nullHints = this.nullHints;
        inferenceVariable.varId = this.varId;
        inferenceVariable.prototype = this;
        return inferenceVariable;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public InferenceVariable prototype() {
        return this.prototype;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeVariableBinding, org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public char[] constantPoolName() {
        throw new UnsupportedOperationException();
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public PackageBinding getPackage() {
        throw new UnsupportedOperationException();
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeVariableBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    TypeBinding substituteInferenceVariable(InferenceVariable inferenceVariable, TypeBinding typeBinding) {
        return TypeBinding.equalsEquals(this, inferenceVariable) ? typeBinding : this;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeVariableBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    void collectInferenceVariables(Set<InferenceVariable> set) {
        set.add(this);
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeVariableBinding, org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public ReferenceBinding[] superInterfaces() {
        return Binding.NO_SUPERINTERFACES;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public char[] qualifiedSourceName() {
        throw new UnsupportedOperationException();
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public char[] sourceName() {
        return this.sourceName;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeVariableBinding, org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.Binding
    public char[] readableName() {
        return this.sourceName;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeVariableBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public boolean hasTypeBit(int i) {
        throw new UnsupportedOperationException();
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeVariableBinding, org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public String debugName() {
        return String.valueOf(this.sourceName);
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeVariableBinding
    public String toString() {
        return debugName();
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding
    public int hashCode() {
        int iHashCode = this.typeParameter.hashCode() + (this.rank * 17);
        InvocationSite invocationSite = this.site;
        return invocationSite != null ? (iHashCode * 31) + invocationSite.hashCode() : iHashCode;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof InferenceVariable)) {
            return false;
        }
        InferenceVariable inferenceVariable = (InferenceVariable) obj;
        return this.rank == inferenceVariable.rank && this.site == inferenceVariable.site && TypeBinding.equalsEquals(this.typeParameter, inferenceVariable.typeParameter);
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeVariableBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public TypeBinding erasure() {
        if (this.superclass == null) {
            this.superclass = this.environment.getType(TypeConstants.JAVA_LANG_OBJECT);
        }
        return super.erasure();
    }
}
