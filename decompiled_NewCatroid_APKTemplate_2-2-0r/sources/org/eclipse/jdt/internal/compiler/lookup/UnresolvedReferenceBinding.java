package org.eclipse.jdt.internal.compiler.lookup;

import org.eclipse.jdt.core.compiler.CharOperation;

/* JADX INFO: loaded from: classes14.dex */
public class UnresolvedReferenceBinding extends ReferenceBinding {
    UnresolvedReferenceBinding prototype;
    ReferenceBinding resolvedType;
    TypeBinding[] wrappers;

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public boolean hasTypeBit(int i) {
        return false;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public boolean isUnresolvedType() {
        return true;
    }

    UnresolvedReferenceBinding(char[][] cArr, PackageBinding packageBinding) {
        this.compoundName = cArr;
        this.sourceName = cArr[cArr.length - 1];
        this.fPackage = packageBinding;
        this.wrappers = null;
        this.prototype = this;
        computeId();
    }

    public UnresolvedReferenceBinding(UnresolvedReferenceBinding unresolvedReferenceBinding) {
        super(unresolvedReferenceBinding);
        this.resolvedType = unresolvedReferenceBinding.resolvedType;
        this.wrappers = null;
        this.prototype = unresolvedReferenceBinding.prototype;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public TypeBinding clone(TypeBinding typeBinding) {
        ReferenceBinding referenceBinding = this.resolvedType;
        if (referenceBinding != null) {
            return referenceBinding.clone(typeBinding);
        }
        UnresolvedReferenceBinding unresolvedReferenceBinding = new UnresolvedReferenceBinding(this);
        addWrapper(unresolvedReferenceBinding, null);
        return unresolvedReferenceBinding;
    }

    void addWrapper(TypeBinding typeBinding, LookupEnvironment lookupEnvironment) {
        ReferenceBinding referenceBinding = this.resolvedType;
        if (referenceBinding != null) {
            typeBinding.swapUnresolved(this, referenceBinding, lookupEnvironment);
            return;
        }
        TypeBinding[] typeBindingArr = this.wrappers;
        if (typeBindingArr == null) {
            this.wrappers = new TypeBinding[]{typeBinding};
            return;
        }
        int length = typeBindingArr.length;
        TypeBinding[] typeBindingArr2 = new TypeBinding[length + 1];
        this.wrappers = typeBindingArr2;
        System.arraycopy(typeBindingArr, 0, typeBindingArr2, 0, length);
        this.wrappers[length] = typeBinding;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public String debugName() {
        return toString();
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public int depth() {
        return CharOperation.occurencesOf('$', this.compoundName[this.compoundName.length - 1]);
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public TypeBinding prototype() {
        return this.prototype;
    }

    ReferenceBinding resolve(LookupEnvironment lookupEnvironment, boolean z) {
        UnresolvedReferenceBinding unresolvedReferenceBinding = this.prototype;
        if (this != unresolvedReferenceBinding) {
            ReferenceBinding referenceBindingResolve = unresolvedReferenceBinding.resolve(lookupEnvironment, z);
            if (z && referenceBindingResolve != null && referenceBindingResolve.isRawType()) {
                return (ReferenceBinding) lookupEnvironment.createAnnotatedType(referenceBindingResolve, this.typeAnnotations);
            }
            return this.resolvedType;
        }
        ReferenceBinding referenceBindingCreateMissingType = this.resolvedType;
        if (referenceBindingCreateMissingType == null) {
            char[] cArr = this.compoundName[this.compoundName.length - 1];
            ReferenceBinding type0 = this.fPackage.getType0(cArr);
            if (type0 == this) {
                type0 = lookupEnvironment.askForType(this.compoundName);
            }
            if ((type0 == null || type0 == this) && CharOperation.contains('.', cArr)) {
                type0 = lookupEnvironment.askForType(this.fPackage, CharOperation.replaceOnCopy(cArr, '.', '$'));
            }
            if (type0 == null || type0 == this) {
                if ((this.tagBits & 128) == 0 && !lookupEnvironment.mayTolerateMissingType) {
                    lookupEnvironment.problemReporter.isClassPathCorrect(this.compoundName, lookupEnvironment.unitBeingCompleted, lookupEnvironment.missingClassFileLocation);
                }
                referenceBindingCreateMissingType = lookupEnvironment.createMissingType(null, this.compoundName);
            } else {
                referenceBindingCreateMissingType = type0;
            }
            if (referenceBindingCreateMissingType.f626id != Integer.MAX_VALUE) {
                this.f626id = referenceBindingCreateMissingType.f626id;
            }
            setResolvedType(referenceBindingCreateMissingType, lookupEnvironment);
        }
        return z ? (ReferenceBinding) lookupEnvironment.convertUnresolvedBinaryToRawType(referenceBindingCreateMissingType) : referenceBindingCreateMissingType;
    }

    void setResolvedType(ReferenceBinding referenceBinding, LookupEnvironment lookupEnvironment) {
        if (this.resolvedType == referenceBinding) {
            return;
        }
        this.resolvedType = referenceBinding;
        lookupEnvironment.updateCaches(this, referenceBinding);
        TypeBinding[] typeBindingArr = this.wrappers;
        if (typeBindingArr != null) {
            int length = typeBindingArr.length;
            for (int i = 0; i < length; i++) {
                this.wrappers[i].swapUnresolved(this, referenceBinding, lookupEnvironment);
            }
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public void swapUnresolved(UnresolvedReferenceBinding unresolvedReferenceBinding, ReferenceBinding referenceBinding, LookupEnvironment lookupEnvironment) {
        if (this.resolvedType != null) {
            return;
        }
        ReferenceBinding referenceBinding2 = (ReferenceBinding) referenceBinding.clone(null);
        this.resolvedType = referenceBinding2;
        referenceBinding2.setTypeAnnotations(getTypeAnnotations(), lookupEnvironment.globalOptions.isAnnotationBasedNullAnalysisEnabled);
        int i = this.f626id;
        referenceBinding.f626id = i;
        referenceBinding2.f626id = i;
        lookupEnvironment.updateCaches(this, referenceBinding2);
        TypeBinding[] typeBindingArr = this.wrappers;
        if (typeBindingArr != null) {
            int length = typeBindingArr.length;
            for (int i2 = 0; i2 < length; i2++) {
                this.wrappers[i2].swapUnresolved(this, referenceBinding2, lookupEnvironment);
            }
        }
    }

    public String toString() {
        if (hasTypeAnnotations()) {
            return String.valueOf(super.annotatedDebugName()) + "(unresolved)";
        }
        return "Unresolved type " + (this.compoundName != null ? CharOperation.toString(this.compoundName) : "UNNAMED");
    }
}
