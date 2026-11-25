package org.eclipse.jdt.internal.compiler.lookup;

import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.compiler.IErrorHandlingPolicy;
import org.eclipse.jdt.internal.compiler.ast.ASTNode;
import org.eclipse.jdt.internal.compiler.ast.FieldDeclaration;
import org.eclipse.jdt.internal.compiler.ast.TypeDeclaration;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants;
import org.eclipse.jdt.internal.compiler.impl.Constant;
import org.eclipse.jdt.internal.compiler.problem.ProblemReporter;
import org.eclipse.jdt.internal.compiler.util.Util;

/* JADX INFO: loaded from: classes14.dex */
public class FieldBinding extends VariableBinding {
    public int compoundUseFlag;
    public ReferenceBinding declaringClass;

    @Override // org.eclipse.jdt.internal.compiler.lookup.Binding
    public final int kind() {
        return 1;
    }

    public FieldBinding original() {
        return this;
    }

    protected FieldBinding() {
        super(null, null, 0, null);
        this.compoundUseFlag = 0;
    }

    public FieldBinding(char[] cArr, TypeBinding typeBinding, int i, ReferenceBinding referenceBinding, Constant constant) {
        super(cArr, typeBinding, i, constant);
        this.compoundUseFlag = 0;
        this.declaringClass = referenceBinding;
    }

    public FieldBinding(FieldBinding fieldBinding, ReferenceBinding referenceBinding) {
        super(fieldBinding.f628name, fieldBinding.type, fieldBinding.modifiers, fieldBinding.constant());
        this.compoundUseFlag = 0;
        this.declaringClass = referenceBinding;
        this.f627id = fieldBinding.f627id;
        setAnnotations(fieldBinding.getAnnotations());
    }

    public FieldBinding(FieldDeclaration fieldDeclaration, TypeBinding typeBinding, int i, ReferenceBinding referenceBinding) {
        this(fieldDeclaration.f617name, typeBinding, i, referenceBinding, null);
        fieldDeclaration.binding = this;
    }

    public final boolean canBeSeenBy(PackageBinding packageBinding) {
        if (isPublic()) {
            return true;
        }
        return !isPrivate() && packageBinding == this.declaringClass.getPackage();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.SourceTypeBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding] */
    /* JADX WARN: Type inference failed for: r0v11, types: [org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding] */
    /* JADX WARN: Type inference failed for: r0v12, types: [org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding] */
    /* JADX WARN: Type inference failed for: r0v13 */
    /* JADX WARN: Type inference failed for: r0v14 */
    /* JADX WARN: Type inference failed for: r0v9 */
    /* JADX WARN: Type inference failed for: r3v5, types: [org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding] */
    /* JADX WARN: Type inference failed for: r6v0, types: [org.eclipse.jdt.internal.compiler.lookup.TypeBinding] */
    public final boolean canBeSeenBy(TypeBinding typeBinding, InvocationSite invocationSite, Scope scope) {
        ?? r6;
        ReferenceBinding referenceBinding;
        if (isPublic()) {
            return true;
        }
        ?? EnclosingSourceType = scope.enclosingSourceType();
        if (TypeBinding.equalsEquals(EnclosingSourceType, this.declaringClass) && TypeBinding.equalsEquals(EnclosingSourceType, typeBinding)) {
            return true;
        }
        if (EnclosingSourceType == 0) {
            return !isPrivate() && scope.getCurrentPackage() == this.declaringClass.fPackage;
        }
        if (isProtected()) {
            if (TypeBinding.equalsEquals(EnclosingSourceType, this.declaringClass) || EnclosingSourceType.fPackage == this.declaringClass.fPackage) {
                return true;
            }
            ?? r3 = (ReferenceBinding) typeBinding.erasure();
            ReferenceBinding referenceBinding2 = (ReferenceBinding) this.declaringClass.erasure();
            int i = 0;
            do {
                if (EnclosingSourceType.findSuperTypeOriginatingFrom(referenceBinding2) != null) {
                    if (invocationSite.isSuperAccess()) {
                        return true;
                    }
                    if (typeBinding instanceof ArrayBinding) {
                        return false;
                    }
                    if (isStatic()) {
                        if (i > 0) {
                            invocationSite.setDepth(i);
                        }
                        return true;
                    }
                    if (TypeBinding.equalsEquals(EnclosingSourceType, r3) || r3.findSuperTypeOriginatingFrom(EnclosingSourceType) != null) {
                        if (i > 0) {
                            invocationSite.setDepth(i);
                        }
                        return true;
                    }
                }
                i++;
                EnclosingSourceType = EnclosingSourceType.enclosingType();
            } while (EnclosingSourceType != 0);
            return false;
        }
        if (isPrivate()) {
            if (TypeBinding.notEquals(typeBinding, this.declaringClass) && (scope.compilerOptions().complianceLevel > ClassFileConstants.JDK1_6 || !typeBinding.isTypeVariable() || !((TypeVariableBinding) typeBinding).isErasureBoundTo(this.declaringClass.erasure()))) {
                return false;
            }
            if (TypeBinding.notEquals(EnclosingSourceType, this.declaringClass)) {
                ReferenceBinding referenceBindingEnclosingType = EnclosingSourceType.enclosingType();
                ?? r0 = EnclosingSourceType;
                while (true) {
                    r6 = r0;
                    ReferenceBinding referenceBinding3 = referenceBindingEnclosingType;
                    if (referenceBinding3 == null) {
                        break;
                    }
                    referenceBindingEnclosingType = referenceBinding3.enclosingType();
                    r0 = referenceBinding3;
                }
                ReferenceBinding referenceBinding4 = (ReferenceBinding) this.declaringClass.erasure();
                ReferenceBinding referenceBindingEnclosingType2 = referenceBinding4.enclosingType();
                while (true) {
                    ReferenceBinding referenceBinding5 = referenceBindingEnclosingType2;
                    referenceBinding = referenceBinding4;
                    referenceBinding4 = referenceBinding5;
                    if (referenceBinding4 == null) {
                        break;
                    }
                    referenceBindingEnclosingType2 = referenceBinding4.enclosingType();
                }
                if (TypeBinding.notEquals(r6, referenceBinding)) {
                    return false;
                }
            }
            return true;
        }
        PackageBinding packageBinding = this.declaringClass.fPackage;
        if (EnclosingSourceType.fPackage != packageBinding || (typeBinding instanceof ArrayBinding)) {
            return false;
        }
        TypeBinding typeBindingOriginal = this.declaringClass.original();
        ReferenceBinding referenceBindingSuperclass = (ReferenceBinding) typeBinding;
        do {
            if (referenceBindingSuperclass.isCapture()) {
                if (TypeBinding.equalsEquals(typeBindingOriginal, referenceBindingSuperclass.erasure().original())) {
                    return true;
                }
            } else if (TypeBinding.equalsEquals(typeBindingOriginal, referenceBindingSuperclass.original())) {
                return true;
            }
            PackageBinding packageBinding2 = referenceBindingSuperclass.fPackage;
            if (packageBinding2 != null && packageBinding2 != packageBinding) {
                return false;
            }
            referenceBindingSuperclass = referenceBindingSuperclass.superclass();
        } while (referenceBindingSuperclass != null);
        return false;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.Binding
    public char[] computeUniqueKey(boolean z) {
        char[] cArrComputeUniqueKey;
        ReferenceBinding referenceBinding = this.declaringClass;
        if (referenceBinding == null) {
            cArrComputeUniqueKey = CharOperation.NO_CHAR;
        } else {
            cArrComputeUniqueKey = referenceBinding.computeUniqueKey(false);
        }
        int length = cArrComputeUniqueKey.length;
        int length2 = this.f628name.length;
        char[] cArrComputeUniqueKey2 = this.type == null ? new char[]{Util.C_VOID} : this.type.computeUniqueKey(false);
        int length3 = cArrComputeUniqueKey2.length;
        char[] cArr = new char[length + 1 + length2 + 1 + length3];
        System.arraycopy(cArrComputeUniqueKey, 0, cArr, 0, length);
        int i = length + 1;
        cArr[length] = '.';
        System.arraycopy(this.f628name, 0, cArr, i, length2);
        int i2 = i + length2;
        cArr[i2] = ')';
        System.arraycopy(cArrComputeUniqueKey2, 0, cArr, i2 + 1, length3);
        return cArr;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.VariableBinding
    public Constant constant() {
        Constant constant = this.constant;
        if (constant == null) {
            if (isFinal()) {
                FieldBinding fieldBindingOriginal = original();
                ReferenceBinding referenceBinding = fieldBindingOriginal.declaringClass;
                if (referenceBinding instanceof SourceTypeBinding) {
                    SourceTypeBinding sourceTypeBinding = (SourceTypeBinding) referenceBinding;
                    if (sourceTypeBinding.scope != null) {
                        TypeDeclaration typeDeclaration = sourceTypeBinding.scope.referenceContext;
                        FieldDeclaration fieldDeclarationDeclarationOf = typeDeclaration.declarationOf(fieldBindingOriginal);
                        MethodScope methodScope = fieldBindingOriginal.isStatic() ? typeDeclaration.staticInitializerScope : typeDeclaration.initializerScope;
                        boolean z = methodScope.insideTypeAnnotation;
                        try {
                            methodScope.insideTypeAnnotation = false;
                            fieldDeclarationDeclarationOf.resolve(methodScope);
                            methodScope.insideTypeAnnotation = z;
                            constant = fieldBindingOriginal.constant == null ? Constant.NotAConstant : fieldBindingOriginal.constant;
                        } catch (Throwable th) {
                            methodScope.insideTypeAnnotation = z;
                            throw th;
                        }
                    } else {
                        constant = Constant.NotAConstant;
                    }
                } else {
                    constant = Constant.NotAConstant;
                }
            } else {
                constant = Constant.NotAConstant;
            }
            this.constant = constant;
        }
        return constant;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.VariableBinding
    public Constant constant(Scope scope) {
        if (this.constant != null) {
            return this.constant;
        }
        ProblemReporter problemReporter = scope.problemReporter();
        IErrorHandlingPolicy iErrorHandlingPolicySuspendTempErrorHandlingPolicy = problemReporter.suspendTempErrorHandlingPolicy();
        try {
            return constant();
        } finally {
            problemReporter.resumeTempErrorHandlingPolicy(iErrorHandlingPolicySuspendTempErrorHandlingPolicy);
        }
    }

    public void fillInDefaultNonNullness(FieldDeclaration fieldDeclaration, Scope scope) {
        if (this.type == null || this.type.isBaseType()) {
            return;
        }
        LookupEnvironment lookupEnvironmentEnvironment = scope.environment();
        if (!lookupEnvironmentEnvironment.usesNullTypeAnnotations()) {
            if ((this.tagBits & TagBits.AnnotationNullMASK) == 0) {
                this.tagBits |= 72057594037927936L;
                return;
            } else {
                if ((this.tagBits & 72057594037927936L) != 0) {
                    scope.problemReporter().nullAnnotationIsRedundant(fieldDeclaration);
                    return;
                }
                return;
            }
        }
        if (this.type.acceptsNonNullDefault()) {
            if ((this.type.tagBits & TagBits.AnnotationNullMASK) == 0) {
                this.type = lookupEnvironmentEnvironment.createAnnotatedType(this.type, new AnnotationBinding[]{lookupEnvironmentEnvironment.getNonNullAnnotation()});
            } else if ((this.type.tagBits & 72057594037927936L) != 0) {
                scope.problemReporter().nullAnnotationIsRedundant(fieldDeclaration);
            }
        }
    }

    public char[] genericSignature() {
        if ((this.modifiers & 1073741824) == 0) {
            return null;
        }
        return this.type.genericTypeSignature();
    }

    public final int getAccessFlags() {
        return this.modifiers & 65535;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.VariableBinding, org.eclipse.jdt.internal.compiler.lookup.Binding
    public AnnotationBinding[] getAnnotations() {
        FieldBinding fieldBindingOriginal = original();
        ReferenceBinding referenceBinding = fieldBindingOriginal.declaringClass;
        if (referenceBinding == null) {
            return Binding.NO_ANNOTATIONS;
        }
        return referenceBinding.retrieveAnnotations(fieldBindingOriginal);
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.Binding
    public long getAnnotationTagBits() {
        FieldBinding fieldBindingOriginal = original();
        if ((fieldBindingOriginal.tagBits & 8589934592L) == 0) {
            ReferenceBinding referenceBinding = fieldBindingOriginal.declaringClass;
            if (referenceBinding instanceof SourceTypeBinding) {
                ClassScope classScope = ((SourceTypeBinding) referenceBinding).scope;
                if (classScope == null) {
                    this.tagBits |= 25769803776L;
                    return 0L;
                }
                TypeDeclaration typeDeclaration = classScope.referenceContext;
                FieldDeclaration fieldDeclarationDeclarationOf = typeDeclaration.declarationOf(fieldBindingOriginal);
                if (fieldDeclarationDeclarationOf != null) {
                    MethodScope methodScope = isStatic() ? typeDeclaration.staticInitializerScope : typeDeclaration.initializerScope;
                    FieldBinding fieldBinding = methodScope.initializedField;
                    int i = methodScope.lastVisibleFieldID;
                    try {
                        methodScope.initializedField = fieldBindingOriginal;
                        methodScope.lastVisibleFieldID = fieldBindingOriginal.f627id;
                        ASTNode.resolveAnnotations(methodScope, fieldDeclarationDeclarationOf.annotations, fieldBindingOriginal);
                    } finally {
                        methodScope.initializedField = fieldBinding;
                        methodScope.lastVisibleFieldID = i;
                    }
                }
            }
        }
        return fieldBindingOriginal.tagBits;
    }

    public final boolean isDefault() {
        return (isPublic() || isProtected() || isPrivate()) ? false : true;
    }

    public final boolean isDeprecated() {
        return (this.modifiers & 1048576) != 0;
    }

    public final boolean isPrivate() {
        return (this.modifiers & 2) != 0;
    }

    public final boolean isOrEnclosedByPrivateType() {
        if ((this.modifiers & 2) != 0) {
            return true;
        }
        ReferenceBinding referenceBinding = this.declaringClass;
        return referenceBinding != null && referenceBinding.isOrEnclosedByPrivateType();
    }

    public final boolean isProtected() {
        return (this.modifiers & 4) != 0;
    }

    public final boolean isPublic() {
        return (this.modifiers & 1) != 0;
    }

    public final boolean isStatic() {
        return (this.modifiers & 8) != 0;
    }

    public final boolean isSynthetic() {
        return (this.modifiers & 4096) != 0;
    }

    public final boolean isTransient() {
        return (this.modifiers & 128) != 0;
    }

    public final boolean isUsed() {
        return (this.modifiers & 134217728) != 0 || this.compoundUseFlag > 0;
    }

    public final boolean isUsedOnlyInCompound() {
        return (this.modifiers & 134217728) == 0 && this.compoundUseFlag > 0;
    }

    public final boolean isViewedAsDeprecated() {
        return (this.modifiers & 3145728) != 0;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.Binding
    public final boolean isVolatile() {
        return (this.modifiers & 64) != 0;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.Binding
    public void setAnnotations(AnnotationBinding[] annotationBindingArr) {
        this.declaringClass.storeAnnotations(this, annotationBindingArr);
    }

    public FieldDeclaration sourceField() {
        try {
            FieldDeclaration[] fieldDeclarationArr = ((SourceTypeBinding) this.declaringClass).scope.referenceContext.fields;
            if (fieldDeclarationArr == null) {
                return null;
            }
            int length = fieldDeclarationArr.length;
            do {
                length--;
                if (length < 0) {
                    return null;
                }
            } while (this != fieldDeclarationArr[length].binding);
            return fieldDeclarationArr[length];
        } catch (ClassCastException unused) {
            return null;
        }
    }
}
