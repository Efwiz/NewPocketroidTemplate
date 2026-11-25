package org.eclipse.jdt.internal.compiler.ast;

import io.netty.util.internal.StringUtil;
import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.compiler.ASTVisitor;
import org.eclipse.jdt.internal.compiler.lookup.Binding;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.eclipse.jdt.internal.compiler.lookup.ClassScope;
import org.eclipse.jdt.internal.compiler.lookup.LookupEnvironment;
import org.eclipse.jdt.internal.compiler.lookup.PackageBinding;
import org.eclipse.jdt.internal.compiler.lookup.ProblemReferenceBinding;
import org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding;
import org.eclipse.jdt.internal.compiler.lookup.Scope;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.TypeVariableBinding;
import org.eclipse.jdt.internal.compiler.problem.AbortCompilation;

/* JADX INFO: loaded from: classes14.dex */
public class QualifiedTypeReference extends TypeReference {
    public long[] sourcePositions;
    public char[][] tokens;

    public QualifiedTypeReference(char[][] cArr, long[] jArr) {
        this.tokens = cArr;
        this.sourcePositions = jArr;
        this.sourceStart = (int) (jArr[0] >>> 32);
        long[] jArr2 = this.sourcePositions;
        this.sourceEnd = (int) (4294967295L & jArr2[jArr2.length - 1]);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.TypeReference
    public TypeReference augmentTypeWithAdditionalDimensions(int i, Annotation[][] annotationArr, boolean z) {
        ArrayQualifiedTypeReference arrayQualifiedTypeReference = new ArrayQualifiedTypeReference(this.tokens, dimensions() + i, getMergedAnnotationsOnDimensions(i, annotationArr), this.sourcePositions);
        arrayQualifiedTypeReference.annotations = this.annotations;
        arrayQualifiedTypeReference.bits |= this.bits & 1048576;
        if (!z) {
            arrayQualifiedTypeReference.extendedDimensions = i;
        }
        return arrayQualifiedTypeReference;
    }

    protected TypeBinding findNextTypeBinding(int i, Scope scope, PackageBinding packageBinding) {
        LookupEnvironment lookupEnvironmentEnvironment = scope.environment();
        try {
            try {
                lookupEnvironmentEnvironment.missingClassFileLocation = this;
                if (this.resolvedType == null) {
                    this.resolvedType = scope.getType(this.tokens[i], packageBinding);
                } else {
                    this.resolvedType = scope.getMemberType(this.tokens[i], (ReferenceBinding) this.resolvedType);
                    if (!this.resolvedType.isValidBinding()) {
                        this.resolvedType = new ProblemReferenceBinding(CharOperation.subarray(this.tokens, 0, i + 1), (ReferenceBinding) this.resolvedType.closestMatch(), this.resolvedType.problemId());
                    }
                }
                TypeBinding typeBinding = this.resolvedType;
                lookupEnvironmentEnvironment.missingClassFileLocation = null;
                return typeBinding;
            } catch (AbortCompilation e) {
                e.updateContext(this, scope.referenceCompilationUnit().compilationResult);
                throw e;
            }
        } catch (Throwable th) {
            lookupEnvironmentEnvironment.missingClassFileLocation = null;
            throw th;
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.TypeReference
    public char[] getLastToken() {
        char[][] cArr = this.tokens;
        return cArr[cArr.length - 1];
    }

    protected void rejectAnnotationsOnPackageQualifiers(Scope scope, PackageBinding packageBinding) {
        if (packageBinding == null || this.annotations == null) {
            return;
        }
        int length = packageBinding.compoundName.length;
        for (int i = 0; i < length; i++) {
            Annotation[] annotationArr = this.annotations[i];
            if (annotationArr != null && annotationArr.length > 0) {
                scope.problemReporter().misplacedTypeAnnotations(annotationArr[0], annotationArr[annotationArr.length - 1]);
                this.annotations[i] = null;
            }
        }
    }

    protected static void rejectAnnotationsOnStaticMemberQualififer(Scope scope, ReferenceBinding referenceBinding, Annotation[] annotationArr) {
        if (!referenceBinding.isMemberType() || !referenceBinding.isStatic() || annotationArr == null || annotationArr.length <= 0) {
            return;
        }
        scope.problemReporter().illegalTypeAnnotationsInStaticMemberAccess(annotationArr[0], annotationArr[annotationArr.length - 1]);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.TypeReference
    protected TypeBinding getTypeBinding(Scope scope) {
        if (this.resolvedType != null) {
            return this.resolvedType;
        }
        Binding binding = scope.getPackage(this.tokens);
        if (binding != null && !binding.isValidBinding()) {
            if ((binding instanceof ProblemReferenceBinding) && binding.problemId() == 1) {
                return new ProblemReferenceBinding(((ProblemReferenceBinding) binding).compoundName, scope.getTypeOrPackage(this.tokens) instanceof PackageBinding ? null : scope.environment().createMissingType(null, this.tokens), 1);
            }
            return (ReferenceBinding) binding;
        }
        PackageBinding packageBinding = binding == null ? null : (PackageBinding) binding;
        rejectAnnotationsOnPackageQualifiers(scope, packageBinding);
        boolean z = scope.kind == 3;
        int length = this.tokens.length;
        int i = length - 1;
        ReferenceBinding referenceBindingCreateParameterizedType = null;
        for (int length2 = packageBinding == null ? 0 : packageBinding.compoundName.length; length2 < length; length2++) {
            findNextTypeBinding(length2, scope, packageBinding);
            if (!this.resolvedType.isValidBinding()) {
                return this.resolvedType;
            }
            if (length2 == 0 && this.resolvedType.isTypeVariable() && ((TypeVariableBinding) this.resolvedType).firstBound == null) {
                scope.problemReporter().illegalAccessFromTypeVariable((TypeVariableBinding) this.resolvedType, this);
                return null;
            }
            if (length2 <= i && isTypeUseDeprecated(this.resolvedType, scope)) {
                reportDeprecatedType(this.resolvedType, scope, length2);
            }
            if (z && ((ClassScope) scope).detectHierarchyCycle(this.resolvedType, this)) {
                return null;
            }
            ReferenceBinding referenceBinding = (ReferenceBinding) this.resolvedType;
            if (referenceBindingCreateParameterizedType != null) {
                if (this.annotations != null) {
                    rejectAnnotationsOnStaticMemberQualififer(scope, referenceBinding, this.annotations[length2 - 1]);
                }
                ReferenceBinding referenceBindingEnclosingType = referenceBinding.enclosingType();
                if (referenceBindingEnclosingType != null && TypeBinding.notEquals(referenceBindingEnclosingType.erasure(), referenceBindingCreateParameterizedType.erasure())) {
                    referenceBindingCreateParameterizedType = referenceBindingEnclosingType;
                }
                if (referenceBinding.isGenericType()) {
                    referenceBindingCreateParameterizedType = scope.environment().createRawType(referenceBinding, referenceBindingCreateParameterizedType);
                } else {
                    boolean zIsRawType = referenceBindingCreateParameterizedType.isRawType();
                    if (zIsRawType && !referenceBinding.isStatic()) {
                        referenceBindingCreateParameterizedType = scope.environment().createRawType((ReferenceBinding) referenceBinding.erasure(), referenceBindingCreateParameterizedType);
                    } else if ((zIsRawType || referenceBindingCreateParameterizedType.isParameterizedType()) && TypeBinding.equalsEquals(referenceBindingCreateParameterizedType.erasure(), referenceBinding.enclosingType().erasure())) {
                        referenceBindingCreateParameterizedType = scope.environment().createParameterizedType((ReferenceBinding) referenceBinding.erasure(), null, referenceBindingCreateParameterizedType);
                    }
                }
                recordResolution(scope.environment(), referenceBindingCreateParameterizedType);
            } else if (referenceBinding.isGenericType()) {
                referenceBinding = (ReferenceBinding) scope.environment().convertToRawType(referenceBinding, false);
            }
            referenceBindingCreateParameterizedType = referenceBinding;
            recordResolution(scope.environment(), referenceBindingCreateParameterizedType);
        }
        this.resolvedType = referenceBindingCreateParameterizedType;
        return this.resolvedType;
    }

    void recordResolution(LookupEnvironment lookupEnvironment, TypeBinding typeBinding) {
        if (typeBinding == null || !typeBinding.isValidBinding()) {
            return;
        }
        for (int i = 0; i < lookupEnvironment.resolutionListeners.length; i++) {
            lookupEnvironment.resolutionListeners[i].recordResolution(this, typeBinding);
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.TypeReference
    public char[][] getTypeName() {
        return this.tokens;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public StringBuffer printExpression(int i, StringBuffer stringBuffer) {
        for (int i2 = 0; i2 < this.tokens.length; i2++) {
            if (i2 > 0) {
                stringBuffer.append('.');
            }
            if (this.annotations != null && this.annotations[i2] != null) {
                printAnnotations(this.annotations[i2], stringBuffer);
                stringBuffer.append(StringUtil.SPACE);
            }
            stringBuffer.append(this.tokens[i2]);
        }
        return stringBuffer;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.TypeReference, org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.ASTNode
    public void traverse(ASTVisitor aSTVisitor, BlockScope blockScope) {
        if (aSTVisitor.visit(this, blockScope) && this.annotations != null) {
            int length = this.annotations.length;
            for (int i = 0; i < length; i++) {
                int length2 = this.annotations[i] == null ? 0 : this.annotations[i].length;
                for (int i2 = 0; i2 < length2; i2++) {
                    this.annotations[i][i2].traverse(aSTVisitor, blockScope);
                }
            }
        }
        aSTVisitor.endVisit(this, blockScope);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.TypeReference, org.eclipse.jdt.internal.compiler.ast.Expression
    public void traverse(ASTVisitor aSTVisitor, ClassScope classScope) {
        if (aSTVisitor.visit(this, classScope) && this.annotations != null) {
            int length = this.annotations.length;
            for (int i = 0; i < length; i++) {
                int length2 = this.annotations[i] == null ? 0 : this.annotations[i].length;
                for (int i2 = 0; i2 < length2; i2++) {
                    this.annotations[i][i2].traverse(aSTVisitor, classScope);
                }
            }
        }
        aSTVisitor.endVisit(this, classScope);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.TypeReference
    public int getAnnotatableLevels() {
        return this.tokens.length;
    }
}
