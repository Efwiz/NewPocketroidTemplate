package org.eclipse.jdt.internal.compiler.lookup;

import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.compiler.ast.ASTNode;
import org.eclipse.jdt.internal.compiler.ast.Annotation;
import org.eclipse.jdt.internal.compiler.ast.CaseStatement;
import org.eclipse.jdt.internal.compiler.ast.TypeDeclaration;
import org.eclipse.jdt.internal.compiler.ast.TypeReference;

/* JADX INFO: loaded from: classes14.dex */
public final class LocalTypeBinding extends NestedTypeBinding {
    static final char[] LocalTypePrefix = {'$', 'L', 'o', 'c', 'a', 'l', '$'};
    private InnerEmulationDependency[] dependents;
    public CaseStatement enclosingCase;
    public MethodBinding enclosingMethod;
    public int sourceStart;

    public LocalTypeBinding(ClassScope classScope, SourceTypeBinding sourceTypeBinding, CaseStatement caseStatement) {
        super(new char[][]{CharOperation.concat(LocalTypePrefix, classScope.referenceContext.f619name)}, classScope, sourceTypeBinding);
        TypeDeclaration typeDeclaration = classScope.referenceContext;
        if ((typeDeclaration.bits & 512) != 0) {
            this.tagBits |= TagBits.AnonymousTypeMask;
        } else {
            this.tagBits |= TagBits.LocalTypeMask;
        }
        this.enclosingCase = caseStatement;
        this.sourceStart = typeDeclaration.sourceStart;
        MethodBinding methodBindingReferenceMethodBinding = classScope.enclosingMethodScope().referenceMethodBinding();
        if (methodBindingReferenceMethodBinding != null) {
            this.enclosingMethod = methodBindingReferenceMethodBinding;
        }
    }

    public LocalTypeBinding(LocalTypeBinding localTypeBinding) {
        super(localTypeBinding);
        this.dependents = localTypeBinding.dependents;
        this.enclosingCase = localTypeBinding.enclosingCase;
        this.sourceStart = localTypeBinding.sourceStart;
        this.enclosingMethod = localTypeBinding.enclosingMethod;
    }

    public void addInnerEmulationDependent(BlockScope blockScope, boolean z) {
        if (!isPrototype()) {
            throw new IllegalStateException();
        }
        InnerEmulationDependency[] innerEmulationDependencyArr = this.dependents;
        int i = 0;
        if (innerEmulationDependencyArr == null) {
            this.dependents = new InnerEmulationDependency[1];
        } else {
            int length = innerEmulationDependencyArr.length;
            for (int i2 = 0; i2 < length; i2++) {
                if (this.dependents[i2].scope == blockScope) {
                    return;
                }
            }
            InnerEmulationDependency[] innerEmulationDependencyArr2 = this.dependents;
            InnerEmulationDependency[] innerEmulationDependencyArr3 = new InnerEmulationDependency[length + 1];
            this.dependents = innerEmulationDependencyArr3;
            System.arraycopy(innerEmulationDependencyArr2, 0, innerEmulationDependencyArr3, 0, length);
            i = length;
        }
        this.dependents[i] = new InnerEmulationDependency(blockScope, z);
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public MethodBinding enclosingMethod() {
        return this.enclosingMethod;
    }

    public ReferenceBinding anonymousOriginalSuperType() {
        TypeReference typeReference;
        if (!isPrototype()) {
            return ((LocalTypeBinding) this.prototype).anonymousOriginalSuperType();
        }
        if (this.superclass == null && this.scope != null) {
            return this.scope.getJavaLangObject();
        }
        if (this.superInterfaces != Binding.NO_SUPERINTERFACES) {
            return this.superInterfaces[0];
        }
        if ((this.tagBits & 131072) == 0) {
            return this.superclass;
        }
        if (this.scope != null && (typeReference = this.scope.referenceContext.allocation.type) != null) {
            return (ReferenceBinding) typeReference.resolvedType;
        }
        return this.superclass;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.NestedTypeBinding, org.eclipse.jdt.internal.compiler.lookup.SourceTypeBinding
    protected void checkRedundantNullnessDefaultRecurse(ASTNode aSTNode, Annotation[] annotationArr, long j, boolean z) {
        long j2;
        if (!isPrototype()) {
            throw new IllegalStateException();
        }
        MethodBinding methodBinding = this.enclosingMethod;
        if (methodBinding == null) {
            j2 = 0;
        } else if (z) {
            j2 = methodBinding.defaultNullness;
        } else {
            j2 = methodBinding.tagBits & 432345564227567616L;
        }
        if (j2 == 0) {
            super.checkRedundantNullnessDefaultRecurse(aSTNode, annotationArr, j, z);
        } else if (j2 == j) {
            this.scope.problemReporter().nullDefaultAnnotationIsRedundant(aSTNode, annotationArr, this.enclosingMethod);
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.SourceTypeBinding, org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.Binding
    public char[] computeUniqueKey(boolean z) {
        if (!isPrototype()) {
            return this.prototype.computeUniqueKey(z);
        }
        char[] cArrComputeUniqueKey = outermostEnclosingType().computeUniqueKey(z);
        int iLastIndexOf = CharOperation.lastIndexOf(';', cArrComputeUniqueKey);
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(cArrComputeUniqueKey, 0, iLastIndexOf);
        stringBuffer.append('$');
        stringBuffer.append(String.valueOf(this.sourceStart));
        if (!isAnonymousType()) {
            stringBuffer.append('$');
            stringBuffer.append(this.sourceName);
        }
        stringBuffer.append(cArrComputeUniqueKey, iLastIndexOf, cArrComputeUniqueKey.length - iLastIndexOf);
        int length = stringBuffer.length();
        char[] cArr = new char[length];
        stringBuffer.getChars(0, length, cArr, 0);
        return cArr;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public char[] constantPoolName() {
        if (this.constantPoolName != null) {
            return this.constantPoolName;
        }
        if (!isPrototype()) {
            char[] cArrConstantPoolName = this.prototype.constantPoolName();
            this.constantPoolName = cArrConstantPoolName;
            return cArrConstantPoolName;
        }
        if (this.constantPoolName == null && this.scope != null) {
            this.constantPoolName = this.scope.compilationUnitScope().computeConstantPoolName(this);
        }
        return this.constantPoolName;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.SourceTypeBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public TypeBinding clone(TypeBinding typeBinding) {
        LocalTypeBinding localTypeBinding = new LocalTypeBinding(this);
        localTypeBinding.enclosingType = (SourceTypeBinding) typeBinding;
        return localTypeBinding;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding
    public int hashCode() {
        return this.enclosingType.hashCode();
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.SourceTypeBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public char[] genericTypeSignature() {
        if (!isPrototype()) {
            return this.prototype.genericTypeSignature();
        }
        if (this.genericReferenceTypeSignature == null && this.constantPoolName == null) {
            if (isAnonymousType()) {
                setConstantPoolName(superclass().sourceName());
            } else {
                setConstantPoolName(sourceName());
            }
        }
        return super.genericTypeSignature();
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.Binding
    public char[] readableName() {
        char[] cArrConcat;
        if (isAnonymousType()) {
            cArrConcat = CharOperation.concat(TypeConstants.ANONYM_PREFIX, anonymousOriginalSuperType().readableName(), TypeConstants.ANONYM_SUFFIX);
        } else if (isMemberType()) {
            cArrConcat = CharOperation.concat(enclosingType().readableName(), this.sourceName, '.');
        } else {
            cArrConcat = this.sourceName;
        }
        TypeVariableBinding[] typeVariableBindingArrTypeVariables = typeVariables();
        if (typeVariableBindingArrTypeVariables == Binding.NO_TYPE_VARIABLES) {
            return cArrConcat;
        }
        StringBuffer stringBuffer = new StringBuffer(10);
        stringBuffer.append(cArrConcat).append('<');
        int length = typeVariableBindingArrTypeVariables.length;
        for (int i = 0; i < length; i++) {
            if (i > 0) {
                stringBuffer.append(',');
            }
            stringBuffer.append(typeVariableBindingArrTypeVariables[i].readableName());
        }
        stringBuffer.append('>');
        int length2 = stringBuffer.length();
        char[] cArr = new char[length2];
        stringBuffer.getChars(0, length2, cArr, 0);
        return cArr;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.Binding
    public char[] shortReadableName() {
        char[] cArrConcat;
        if (isAnonymousType()) {
            cArrConcat = CharOperation.concat(TypeConstants.ANONYM_PREFIX, anonymousOriginalSuperType().shortReadableName(), TypeConstants.ANONYM_SUFFIX);
        } else if (isMemberType()) {
            cArrConcat = CharOperation.concat(enclosingType().shortReadableName(), this.sourceName, '.');
        } else {
            cArrConcat = this.sourceName;
        }
        TypeVariableBinding[] typeVariableBindingArrTypeVariables = typeVariables();
        if (typeVariableBindingArrTypeVariables == Binding.NO_TYPE_VARIABLES) {
            return cArrConcat;
        }
        StringBuffer stringBuffer = new StringBuffer(10);
        stringBuffer.append(cArrConcat).append('<');
        int length = typeVariableBindingArrTypeVariables.length;
        for (int i = 0; i < length; i++) {
            if (i > 0) {
                stringBuffer.append(',');
            }
            stringBuffer.append(typeVariableBindingArrTypeVariables[i].shortReadableName());
        }
        stringBuffer.append('>');
        int length2 = stringBuffer.length();
        char[] cArr = new char[length2];
        stringBuffer.getChars(0, length2, cArr, 0);
        return cArr;
    }

    public void setAsMemberType() {
        if (!isPrototype()) {
            this.tagBits |= TagBits.MemberTypeMask;
            ((LocalTypeBinding) this.prototype).setAsMemberType();
        } else {
            this.tagBits |= TagBits.MemberTypeMask;
        }
    }

    public void setConstantPoolName(char[] cArr) {
        if (!isPrototype()) {
            this.constantPoolName = cArr;
            ((LocalTypeBinding) this.prototype).setConstantPoolName(cArr);
        } else {
            this.constantPoolName = cArr;
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public char[] signature() {
        if (!isPrototype()) {
            return this.prototype.signature();
        }
        if (this.signature == null && this.constantPoolName == null) {
            if (isAnonymousType()) {
                setConstantPoolName(superclass().sourceName());
            } else {
                setConstantPoolName(sourceName());
            }
        }
        return super.signature();
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public char[] sourceName() {
        if (isAnonymousType()) {
            return CharOperation.concat(TypeConstants.ANONYM_PREFIX, anonymousOriginalSuperType().sourceName(), TypeConstants.ANONYM_SUFFIX);
        }
        return this.sourceName;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.SourceTypeBinding
    public String toString() {
        if (hasTypeAnnotations()) {
            return String.valueOf(annotatedDebugName()) + " (local)";
        }
        if (isAnonymousType()) {
            return "Anonymous type : " + super.toString();
        }
        if (isMemberType()) {
            return "Local member type : " + new String(sourceName()) + " " + super.toString();
        }
        return "Local type : " + new String(sourceName()) + " " + super.toString();
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.NestedTypeBinding
    public void updateInnerEmulationDependents() {
        if (!isPrototype()) {
            throw new IllegalStateException();
        }
        if (this.dependents == null) {
            return;
        }
        int i = 0;
        while (true) {
            InnerEmulationDependency[] innerEmulationDependencyArr = this.dependents;
            if (i >= innerEmulationDependencyArr.length) {
                return;
            }
            InnerEmulationDependency innerEmulationDependency = innerEmulationDependencyArr[i];
            innerEmulationDependency.scope.propagateInnerEmulation(this, innerEmulationDependency.wasEnclosingInstanceSupplied);
            i++;
        }
    }
}
