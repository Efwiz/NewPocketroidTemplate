package org.eclipse.jdt.internal.compiler.lookup;

import java.util.Arrays;
import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.compiler.ast.AbstractMethodDeclaration;
import org.eclipse.jdt.internal.compiler.ast.Argument;
import org.eclipse.jdt.internal.compiler.ast.MethodDeclaration;
import org.eclipse.jdt.internal.compiler.ast.TypeParameter;
import org.eclipse.jdt.internal.compiler.ast.TypeReference;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.eclipse.jdt.internal.compiler.problem.ProblemReporter;
import org.eclipse.jdt.internal.compiler.util.HashtableOfObject;
import org.eclipse.jdt.internal.compiler.util.SimpleSet;
import org.eclipse.jdt.internal.compiler.util.Sorting;

/* JADX INFO: loaded from: classes14.dex */
class MethodVerifier15 extends MethodVerifier {
    MethodVerifier15(LookupEnvironment lookupEnvironment) {
        super(lookupEnvironment);
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.MethodVerifier
    protected boolean canOverridingMethodDifferInErasure(MethodBinding methodBinding, MethodBinding methodBinding2) {
        return (methodBinding.areParameterErasuresEqual(methodBinding2) || methodBinding.declaringClass.isRawType()) ? false : true;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.MethodVerifier
    boolean canSkipInheritedMethods() {
        return (this.type.superclass() == null || !(this.type.superclass().isAbstract() || this.type.superclass().isParameterizedType())) && this.type.superInterfaces() == Binding.NO_SUPERINTERFACES;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.MethodVerifier
    boolean canSkipInheritedMethods(MethodBinding methodBinding, MethodBinding methodBinding2) {
        if (methodBinding2 != null) {
            return TypeBinding.equalsEquals(methodBinding.declaringClass, methodBinding2.declaringClass) && !methodBinding.declaringClass.isParameterizedType();
        }
        return true;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.MethodVerifier
    void checkConcreteInheritedMethod(MethodBinding methodBinding, MethodBinding[] methodBindingArr) {
        super.checkConcreteInheritedMethod(methodBinding, methodBindingArr);
        boolean z = this.environment.globalOptions.isAnnotationBasedNullAnalysisEnabled;
        AbstractMethodDeclaration abstractMethodDeclarationSourceMethod = (z && this.type.equals(methodBinding.declaringClass)) ? methodBinding.sourceMethod() : null;
        boolean zUsesNullTypeAnnotations = this.environment.usesNullTypeAnnotations();
        boolean z2 = z && methodBinding.hasNonNullDefaultFor(16, zUsesNullTypeAnnotations);
        boolean z3 = z && methodBinding.hasNonNullDefaultFor(8, zUsesNullTypeAnnotations);
        int i = 0;
        for (int length = methodBindingArr.length; i < length; length = length) {
            MethodBinding methodBinding2 = methodBindingArr[i];
            if (methodBinding.isVarargs() != methodBinding2.isVarargs()) {
                problemReporter().varargsConflict(methodBinding, methodBinding2, this.type);
            }
            MethodBinding methodBindingOriginal = methodBinding2.original();
            if (TypeBinding.notEquals(methodBindingOriginal.returnType, methodBinding.returnType) && !isAcceptableReturnTypeOverride(methodBinding, methodBinding2)) {
                problemReporter().unsafeReturnTypeOverride(methodBinding, methodBindingOriginal, this.type);
            }
            if (methodBindingOriginal.declaringClass.isInterface() && ((TypeBinding.equalsEquals(methodBinding.declaringClass, this.type.superclass) && this.type.superclass.isParameterizedType() && !areMethodsCompatible(methodBinding, methodBindingOriginal)) || this.type.superclass.erasure().findSuperTypeOriginatingFrom(methodBindingOriginal.declaringClass) == null)) {
                this.type.addSyntheticBridgeMethod(methodBindingOriginal, methodBinding.original());
            }
            if (z && !methodBinding.isStatic() && !methodBinding2.isStatic()) {
                checkNullSpecInheritance(methodBinding, abstractMethodDeclarationSourceMethod, z2, z3, true, methodBinding2, methodBindingArr, this.type.scope, null);
            }
            i++;
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.MethodVerifier
    void checkForBridgeMethod(MethodBinding methodBinding, MethodBinding methodBinding2, MethodBinding[] methodBindingArr) {
        if (methodBinding.isVarargs() != methodBinding2.isVarargs()) {
            problemReporter(methodBinding).varargsConflict(methodBinding, methodBinding2, this.type);
        }
        MethodBinding methodBindingOriginal = methodBinding2.original();
        if (TypeBinding.notEquals(methodBindingOriginal.returnType, methodBinding.returnType) && !isAcceptableReturnTypeOverride(methodBinding, methodBinding2)) {
            problemReporter(methodBinding).unsafeReturnTypeOverride(methodBinding, methodBindingOriginal, this.type);
        }
        SyntheticMethodBinding syntheticMethodBindingAddSyntheticBridgeMethod = this.type.addSyntheticBridgeMethod(methodBindingOriginal, methodBinding.original());
        if (syntheticMethodBindingAddSyntheticBridgeMethod != null) {
            int length = methodBindingArr == null ? 0 : methodBindingArr.length;
            for (int i = 0; i < length; i++) {
                MethodBinding methodBinding3 = methodBindingArr[i];
                if (methodBinding3 != null && detectInheritedNameClash(methodBindingOriginal, methodBinding3.original())) {
                    return;
                }
            }
            MethodBinding[] methodBindingArr2 = (MethodBinding[]) this.currentMethods.get(syntheticMethodBindingAddSyntheticBridgeMethod.selector);
            for (int length2 = methodBindingArr2.length - 1; length2 >= 0; length2--) {
                MethodBinding methodBinding4 = methodBindingArr2[length2];
                if (methodBinding4.areParameterErasuresEqual(syntheticMethodBindingAddSyntheticBridgeMethod) && TypeBinding.equalsEquals(methodBinding4.returnType.erasure(), syntheticMethodBindingAddSyntheticBridgeMethod.returnType.erasure())) {
                    ProblemReporter problemReporter = problemReporter(methodBinding4);
                    if (!methodBinding2.declaringClass.isRawType()) {
                        methodBinding2 = methodBinding2.original();
                    }
                    problemReporter.methodNameClash(methodBinding4, methodBinding2, 1);
                    return;
                }
            }
        }
    }

    void checkForNameClash(MethodBinding methodBinding, MethodBinding methodBinding2) {
        int length;
        ReferenceBinding[] referenceBindingArr;
        ReferenceBinding[] referenceBindingArr2;
        if (methodBinding2.isStatic() || methodBinding.isStatic()) {
            MethodBinding methodBindingOriginal = methodBinding2.original();
            if (this.type.scope.compilerOptions().complianceLevel < ClassFileConstants.JDK1_7 || !methodBinding.areParameterErasuresEqual(methodBindingOriginal)) {
                return;
            }
            ProblemReporter problemReporter = problemReporter(methodBinding);
            if (!methodBinding2.declaringClass.isRawType()) {
                methodBinding2 = methodBindingOriginal;
            }
            problemReporter.methodNameClashHidden(methodBinding, methodBinding2);
            return;
        }
        if (detectNameClash(methodBinding, methodBinding2, false)) {
            return;
        }
        TypeBinding[] typeBindingArr = methodBinding.parameters;
        TypeBinding[] typeBindingArr2 = methodBinding2.parameters;
        int length2 = typeBindingArr.length;
        if (length2 != typeBindingArr2.length) {
            return;
        }
        for (int i = 0; i < length2; i++) {
            if (TypeBinding.notEquals(typeBindingArr[i], typeBindingArr2[i]) && (typeBindingArr[i].isBaseType() != typeBindingArr2[i].isBaseType() || !typeBindingArr2[i].isCompatibleWith(typeBindingArr[i]))) {
                return;
            }
        }
        ReferenceBinding[] referenceBindingArrSuperInterfaces = methodBinding2.declaringClass.superInterfaces();
        if (referenceBindingArrSuperInterfaces != Binding.NO_SUPERINTERFACES) {
            length = referenceBindingArrSuperInterfaces.length;
        } else {
            referenceBindingArrSuperInterfaces = null;
            length = 0;
        }
        for (ReferenceBinding referenceBindingSuperclass = r12.superclass(); referenceBindingSuperclass != null && referenceBindingSuperclass.isValidBinding(); referenceBindingSuperclass = referenceBindingSuperclass.superclass()) {
            for (MethodBinding methodBinding3 : referenceBindingSuperclass.getMethods(methodBinding.selector)) {
                MethodBinding methodBindingComputeSubstituteMethod = computeSubstituteMethod(methodBinding3, methodBinding);
                if (methodBindingComputeSubstituteMethod != null && !isSubstituteParameterSubsignature(methodBinding, methodBindingComputeSubstituteMethod) && detectNameClash(methodBinding, methodBindingComputeSubstituteMethod, true)) {
                    return;
                }
            }
            ReferenceBinding[] referenceBindingArrSuperInterfaces2 = referenceBindingSuperclass.superInterfaces();
            if (referenceBindingArrSuperInterfaces2 != Binding.NO_SUPERINTERFACES) {
                if (referenceBindingArrSuperInterfaces == null) {
                    length = referenceBindingArrSuperInterfaces2.length;
                    referenceBindingArrSuperInterfaces = referenceBindingArrSuperInterfaces2;
                } else {
                    int length3 = length + referenceBindingArrSuperInterfaces2.length;
                    if (length3 >= referenceBindingArrSuperInterfaces.length) {
                        referenceBindingArr2 = new ReferenceBinding[length3 + 5];
                        System.arraycopy(referenceBindingArrSuperInterfaces, 0, referenceBindingArr2, 0, length);
                    } else {
                        referenceBindingArr2 = referenceBindingArrSuperInterfaces;
                    }
                    int i2 = length;
                    for (ReferenceBinding referenceBinding : referenceBindingArrSuperInterfaces2) {
                        int i3 = 0;
                        while (true) {
                            if (i3 < i2) {
                                if (TypeBinding.equalsEquals(referenceBinding, referenceBindingArr2[i3])) {
                                    break;
                                } else {
                                    i3++;
                                }
                            } else {
                                referenceBindingArr2[i2] = referenceBinding;
                                i2++;
                                break;
                            }
                        }
                    }
                    referenceBindingArrSuperInterfaces = referenceBindingArr2;
                    length = i2;
                }
            }
        }
        for (int i4 = 0; i4 < length; i4++) {
            ReferenceBinding referenceBinding2 = referenceBindingArrSuperInterfaces[i4];
            if (referenceBinding2.isValidBinding()) {
                for (MethodBinding methodBinding4 : referenceBinding2.getMethods(methodBinding.selector)) {
                    MethodBinding methodBindingComputeSubstituteMethod2 = computeSubstituteMethod(methodBinding4, methodBinding);
                    if (methodBindingComputeSubstituteMethod2 != null && !isSubstituteParameterSubsignature(methodBinding, methodBindingComputeSubstituteMethod2) && detectNameClash(methodBinding, methodBindingComputeSubstituteMethod2, true)) {
                        return;
                    }
                }
                ReferenceBinding[] referenceBindingArrSuperInterfaces3 = referenceBinding2.superInterfaces();
                if (referenceBindingArrSuperInterfaces3 != Binding.NO_SUPERINTERFACES) {
                    int length4 = length + referenceBindingArrSuperInterfaces3.length;
                    if (length4 >= referenceBindingArrSuperInterfaces.length) {
                        referenceBindingArr = new ReferenceBinding[length4 + 5];
                        System.arraycopy(referenceBindingArrSuperInterfaces, 0, referenceBindingArr, 0, length);
                    } else {
                        referenceBindingArr = referenceBindingArrSuperInterfaces;
                    }
                    int i5 = length;
                    for (ReferenceBinding referenceBinding3 : referenceBindingArrSuperInterfaces3) {
                        int i6 = 0;
                        while (true) {
                            if (i6 < i5) {
                                if (TypeBinding.equalsEquals(referenceBinding3, referenceBindingArr[i6])) {
                                    break;
                                } else {
                                    i6++;
                                }
                            } else {
                                referenceBindingArr[i5] = referenceBinding3;
                                i5++;
                                break;
                            }
                        }
                    }
                    referenceBindingArrSuperInterfaces = referenceBindingArr;
                    length = i5;
                }
            }
        }
    }

    void checkInheritedMethods(MethodBinding methodBinding, MethodBinding methodBinding2) {
        if (methodBinding.isStatic()) {
            return;
        }
        if (this.environment.globalOptions.complianceLevel >= ClassFileConstants.JDK1_7 || !methodBinding.declaringClass.isInterface()) {
            detectInheritedNameClash(methodBinding.original(), methodBinding2.original());
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.MethodVerifier
    void checkInheritedMethods(MethodBinding[] methodBindingArr, int i, boolean[] zArr, boolean[] zArr2) {
        MethodBinding methodBinding;
        MethodBinding methodBinding2;
        int i2 = 0;
        while (true) {
            methodBinding = null;
            if (i2 >= i) {
                methodBinding2 = null;
                break;
            }
            if (!methodBindingArr[i2].declaringClass.isInterface() && TypeBinding.notEquals(methodBindingArr[i2].declaringClass, this.type) && methodBindingArr[i2].isAbstract()) {
                methodBinding2 = methodBindingArr[i2];
                break;
            }
            i2++;
        }
        boolean z = false;
        boolean z2 = true;
        for (int i3 = 0; i3 < i; i3++) {
            if (zArr2[i3] && !methodBindingArr[i3].isAbstract()) {
                if (methodBindingArr[i3].isDefaultMethod() && methodBinding2 != null && areParametersEqual(methodBinding2, methodBindingArr[i3]) && methodBinding == null) {
                    z = true;
                } else {
                    if (methodBinding != null) {
                        if ((!zArr[i3] || !areMethodsCompatible(methodBinding, methodBindingArr[i3])) && (!TypeBinding.equalsEquals(methodBinding.declaringClass, methodBindingArr[i3].declaringClass) || methodBinding.typeVariables.length == methodBindingArr[i3].typeVariables.length || ((methodBinding.typeVariables != Binding.NO_TYPE_VARIABLES || methodBinding.original() != methodBindingArr[i3]) && (methodBindingArr[i3].typeVariables != Binding.NO_TYPE_VARIABLES || methodBindingArr[i3].original() != methodBinding)))) {
                            problemReporter().duplicateInheritedMethods(this.type, methodBinding, methodBindingArr[i3], this.environment.globalOptions.sourceLevel >= ClassFileConstants.JDK1_8);
                            z2 = false;
                            methodBinding = methodBindingArr[i3];
                        }
                    } else {
                        methodBinding = methodBindingArr[i3];
                    }
                    z = false;
                }
            }
        }
        if (z2) {
            if (z) {
                if (!this.type.isAbstract()) {
                    problemReporter().abstractMethodMustBeImplemented(this.type, methodBinding2);
                    return;
                }
            } else if (methodBinding != null && methodBinding.isDefaultMethod() && this.environment.globalOptions.complianceLevel >= ClassFileConstants.JDK1_8 && !checkInheritedDefaultMethods(methodBindingArr, zArr, i)) {
                return;
            }
            super.checkInheritedMethods(methodBindingArr, i, zArr, zArr2);
        }
    }

    boolean checkInheritedDefaultMethods(MethodBinding[] methodBindingArr, boolean[] zArr, int i) {
        boolean z = true;
        if (i < 2) {
            return true;
        }
        for (int i2 = 0; i2 < i; i2++) {
            if (methodBindingArr[i2].isDefaultMethod() && !zArr[i2]) {
                for (int i3 = 0; i3 < i; i3++) {
                    if (i3 != i2 && !zArr[i3] && isMethodSubsignature(methodBindingArr[i2], methodBindingArr[i3]) && !doesMethodOverride(methodBindingArr[i2], methodBindingArr[i3]) && !doesMethodOverride(methodBindingArr[i3], methodBindingArr[i2])) {
                        problemReporter().inheritedDefaultMethodConflictsWithOtherInherited(this.type, methodBindingArr[i2], methodBindingArr[i3]);
                        z = false;
                        break;
                    }
                }
            }
        }
        return z;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.MethodVerifier
    boolean checkInheritedReturnTypes(MethodBinding methodBinding, MethodBinding methodBinding2) {
        if (areReturnTypesCompatible(methodBinding, methodBinding2)) {
            return true;
        }
        if (!isUnsafeReturnTypeOverride(methodBinding, methodBinding2)) {
            return false;
        }
        if (!methodBinding.declaringClass.implementsInterface(methodBinding2.declaringClass, false)) {
            problemReporter(methodBinding).unsafeReturnTypeOverride(methodBinding, methodBinding2, this.type);
        }
        return true;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.MethodVerifier
    void checkAgainstInheritedMethods(MethodBinding methodBinding, MethodBinding[] methodBindingArr, int i, MethodBinding[] methodBindingArr2) {
        super.checkAgainstInheritedMethods(methodBinding, methodBindingArr, i, methodBindingArr2);
        if (!this.environment.globalOptions.isAnnotationBasedNullAnalysisEnabled || (methodBinding.tagBits & 4096) != 0) {
            return;
        }
        AbstractMethodDeclaration abstractMethodDeclarationSourceMethod = this.type.equals(methodBinding.declaringClass) ? methodBinding.sourceMethod() : null;
        boolean zUsesNullTypeAnnotations = this.environment.usesNullTypeAnnotations();
        boolean zHasNonNullDefaultFor = methodBinding.hasNonNullDefaultFor(16, zUsesNullTypeAnnotations);
        boolean zHasNonNullDefaultFor2 = methodBinding.hasNonNullDefaultFor(8, zUsesNullTypeAnnotations);
        int i2 = i;
        while (true) {
            int i3 = i2 - 1;
            if (i3 < 0) {
                return;
            }
            if (!methodBinding.isStatic() && !methodBindingArr[i3].isStatic()) {
                checkNullSpecInheritance(methodBinding, abstractMethodDeclarationSourceMethod, zHasNonNullDefaultFor, zHasNonNullDefaultFor2, true, methodBindingArr[i3], methodBindingArr, this.type.scope, null);
            }
            i2 = i3;
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ImplicitNullAnnotationVerifier
    void checkNullSpecInheritance(MethodBinding methodBinding, AbstractMethodDeclaration abstractMethodDeclaration, boolean z, boolean z2, boolean z3, MethodBinding methodBinding2, MethodBinding[] methodBindingArr, Scope scope, ImplicitNullAnnotationVerifier.InheritedNonNullnessInfo[] inheritedNonNullnessInfoArr) {
        boolean z4 = z3 & (!methodBinding.isConstructor());
        if (!z && !z2 && !z4 && !this.environment.globalOptions.inheritNullAnnotations) {
            methodBinding.tagBits = 4096 | methodBinding.tagBits;
            return;
        }
        if (TypeBinding.notEquals(methodBinding.declaringClass, this.type) && (4096 & methodBinding.tagBits) == 0) {
            this.buddyImplicitNullAnnotationsVerifier.checkImplicitNullAnnotations(methodBinding, abstractMethodDeclaration, z4, scope);
        }
        super.checkNullSpecInheritance(methodBinding, abstractMethodDeclaration, z, z2, z4, methodBinding2, methodBindingArr, scope, inheritedNonNullnessInfoArr);
    }

    void reportRawReferences() {
        CompilerOptions compilerOptions = this.type.scope.compilerOptions();
        if (compilerOptions.sourceLevel < ClassFileConstants.JDK1_5 || compilerOptions.reportUnavoidableGenericTypeProblems) {
            return;
        }
        Object[] objArr = this.currentMethods.valueTable;
        int length = objArr.length;
        while (true) {
            length--;
            if (length < 0) {
                return;
            }
            Object obj = objArr[length];
            if (obj != null) {
                for (MethodBinding methodBinding : (MethodBinding[]) obj) {
                    if ((methodBinding.modifiers & 805306368) == 0) {
                        AbstractMethodDeclaration abstractMethodDeclarationSourceMethod = methodBinding.sourceMethod();
                        if (abstractMethodDeclarationSourceMethod == null) {
                            return;
                        }
                        TypeBinding[] typeBindingArr = methodBinding.parameters;
                        Argument[] argumentArr = abstractMethodDeclarationSourceMethod.arguments;
                        int length2 = methodBinding.parameters.length;
                        for (int i = 0; i < length2; i++) {
                            TypeBinding typeBinding = typeBindingArr[i];
                            Argument argument = argumentArr[i];
                            if (typeBinding.leafComponentType().isRawType() && compilerOptions.getSeverity(CompilerOptions.RawTypeReference) != 256 && (argument.type.bits & 1073741824) == 0) {
                                abstractMethodDeclarationSourceMethod.scope.problemReporter().rawTypeReference(argument.type, typeBinding);
                            }
                        }
                        if (!abstractMethodDeclarationSourceMethod.isConstructor() && (abstractMethodDeclarationSourceMethod instanceof MethodDeclaration)) {
                            TypeReference typeReference = ((MethodDeclaration) abstractMethodDeclarationSourceMethod).returnType;
                            TypeBinding typeBinding2 = methodBinding.returnType;
                            if (typeReference != null && typeBinding2.leafComponentType().isRawType() && compilerOptions.getSeverity(CompilerOptions.RawTypeReference) != 256 && (typeReference.bits & 1073741824) == 0) {
                                abstractMethodDeclarationSourceMethod.scope.problemReporter().rawTypeReference(typeReference, typeBinding2);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.MethodVerifier
    public void reportRawReferences(MethodBinding methodBinding, MethodBinding methodBinding2) {
        AbstractMethodDeclaration abstractMethodDeclarationSourceMethod;
        TypeReference typeReference;
        CompilerOptions compilerOptions = this.type.scope.compilerOptions();
        if (compilerOptions.sourceLevel < ClassFileConstants.JDK1_5 || compilerOptions.reportUnavoidableGenericTypeProblems || (abstractMethodDeclarationSourceMethod = methodBinding.sourceMethod()) == null) {
            return;
        }
        TypeBinding[] typeBindingArr = methodBinding.parameters;
        TypeBinding[] typeBindingArr2 = methodBinding2.parameters;
        Argument[] argumentArr = abstractMethodDeclarationSourceMethod.arguments;
        int length = methodBinding.parameters.length;
        for (int i = 0; i < length; i++) {
            TypeBinding typeBinding = typeBindingArr[i];
            TypeBinding typeBinding2 = typeBindingArr2[i];
            Argument argument = argumentArr[i];
            if (typeBinding.leafComponentType().isRawType()) {
                if (typeBinding2.leafComponentType().isRawType()) {
                    argument.binding.tagBits |= 512;
                } else if (compilerOptions.getSeverity(CompilerOptions.RawTypeReference) != 256 && (1073741824 & argument.type.bits) == 0) {
                    abstractMethodDeclarationSourceMethod.scope.problemReporter().rawTypeReference(argument.type, typeBinding);
                }
            }
        }
        if (abstractMethodDeclarationSourceMethod.isConstructor() || !(abstractMethodDeclarationSourceMethod instanceof MethodDeclaration) || (typeReference = ((MethodDeclaration) abstractMethodDeclarationSourceMethod).returnType) == null) {
            return;
        }
        TypeBinding typeBinding3 = methodBinding2.returnType;
        TypeBinding typeBinding4 = methodBinding.returnType;
        if (!typeBinding4.leafComponentType().isRawType() || typeBinding3.leafComponentType().isRawType() || (typeReference.bits & 1073741824) != 0 || compilerOptions.getSeverity(CompilerOptions.RawTypeReference) == 256) {
            return;
        }
        abstractMethodDeclarationSourceMethod.scope.problemReporter().rawTypeReference(typeReference, typeBinding4);
    }

    /* JADX WARN: Code duplicated, block: B:134:0x0228  */
    /* JADX WARN: Code duplicated, block: B:14:0x0028  */
    @Override // org.eclipse.jdt.internal.compiler.lookup.MethodVerifier
    void checkMethods() {
        boolean z;
        char[][] cArr;
        int i;
        int i2;
        boolean[] zArr;
        int i3;
        boolean z2;
        boolean[] zArr2;
        boolean[] zArr3;
        boolean[] zArr4;
        int i4;
        boolean[] zArr5;
        boolean[] zArr6;
        int i5;
        MethodBinding methodBinding;
        boolean zMustImplementAbstractMethods = mustImplementAbstractMethods();
        int i6 = 0;
        boolean z3 = true;
        boolean z4 = zMustImplementAbstractMethods && canSkipInheritedMethods();
        boolean zIsOrEnclosedByPrivateType = this.type.isOrEnclosedByPrivateType();
        char[][] cArr2 = this.inheritedMethods.keyTable;
        int length = cArr2.length;
        while (true) {
            int i7 = length - 1;
            if (i7 < 0) {
                return;
            }
            if (cArr2[i7] == null) {
                z = z4;
                cArr = cArr2;
            } else {
                MethodBinding[] methodBindingArr = (MethodBinding[]) this.currentMethods.get(cArr2[i7]);
                MethodBinding[] methodBindingArr2 = (MethodBinding[]) this.inheritedMethods.valueTable[i7];
                MethodBinding[] methodBindingArrConcreteFirst = Sorting.concreteFirst(methodBindingArr2, methodBindingArr2.length);
                if (methodBindingArr == null && !zIsOrEnclosedByPrivateType) {
                    int length2 = methodBindingArrConcreteFirst.length;
                    for (int i8 = i6; i8 < length2; i8++) {
                        methodBindingArrConcreteFirst[i8].original().modifiers |= 134217728;
                    }
                }
                if (methodBindingArr == null && this.type.isPublic()) {
                    int length3 = methodBindingArrConcreteFirst.length;
                    for (int i9 = i6; i9 < length3; i9++) {
                        MethodBinding methodBinding2 = methodBindingArrConcreteFirst[i9];
                        if (methodBinding2.isPublic() && !methodBinding2.declaringClass.isInterface() && !methodBinding2.declaringClass.isPublic()) {
                            this.type.addSyntheticBridgeMethod(methodBinding2.original());
                        }
                    }
                }
                if (methodBindingArr == null && z4) {
                    z = z4;
                    cArr = cArr2;
                } else if (methodBindingArrConcreteFirst.length == z3 && methodBindingArr == null) {
                    if (zMustImplementAbstractMethods && methodBindingArrConcreteFirst[i6].isAbstract()) {
                        checkAbstractMethod(methodBindingArrConcreteFirst[i6]);
                    }
                    z = z4;
                    cArr = cArr2;
                } else {
                    int length4 = methodBindingArrConcreteFirst.length;
                    MethodBinding[] methodBindingArr3 = new MethodBinding[length4];
                    MethodBinding[] methodBindingArr4 = new MethodBinding[length4];
                    boolean[] zArr7 = new boolean[length4];
                    boolean[] zArr8 = new boolean[length4];
                    boolean[] zArr9 = new boolean[length4];
                    Arrays.fill(zArr9, z3);
                    if (methodBindingArr != null) {
                        int length5 = methodBindingArr.length;
                        int i10 = 0;
                        i = -1;
                        while (i10 < length5) {
                            boolean z5 = z4;
                            MethodBinding methodBinding3 = methodBindingArr[i10];
                            char[][] cArr3 = cArr2;
                            int i11 = length5;
                            MethodBinding[] methodBindingArr5 = null;
                            int i12 = 0;
                            while (i12 < length4) {
                                int i13 = i10;
                                MethodBinding methodBindingComputeSubstituteMethod = computeSubstituteMethod(methodBindingArrConcreteFirst[i12], methodBinding3);
                                if (methodBindingComputeSubstituteMethod == null) {
                                    methodBinding = methodBinding3;
                                } else if (methodBindingArr4[i12] == null && isSubstituteParameterSubsignature(methodBinding3, methodBindingComputeSubstituteMethod)) {
                                    boolean zCouldMethodOverride = couldMethodOverride(methodBinding3, methodBindingComputeSubstituteMethod);
                                    zArr7[i12] = zCouldMethodOverride;
                                    zArr8[i12] = zCouldMethodOverride;
                                    i++;
                                    methodBindingArr3[i] = methodBindingComputeSubstituteMethod;
                                    methodBindingArr4[i12] = methodBinding3;
                                    methodBinding = methodBinding3;
                                } else {
                                    checkForNameClash(methodBinding3, methodBindingComputeSubstituteMethod);
                                    methodBinding = methodBinding3;
                                    if (length4 > 1) {
                                        if (methodBindingArr5 == null) {
                                            methodBindingArr5 = new MethodBinding[length4];
                                        }
                                        methodBindingArr5[i12] = methodBindingComputeSubstituteMethod;
                                    }
                                }
                                i12++;
                                i10 = i13;
                                methodBinding3 = methodBinding;
                            }
                            if (i >= 0) {
                                checkAgainstInheritedMethods(methodBinding3, methodBindingArr3, i + 1, methodBindingArr5);
                                while (i >= 0) {
                                    methodBindingArr3[i] = null;
                                    i--;
                                }
                            }
                            i10++;
                            z4 = z5;
                            cArr2 = cArr3;
                            length5 = i11;
                        }
                        z = z4;
                        cArr = cArr2;
                    } else {
                        z = z4;
                        cArr = cArr2;
                        i = -1;
                    }
                    int i14 = 0;
                    while (i14 < length4) {
                        boolean[] zArr10 = zArr7;
                        int i15 = length4;
                        boolean z6 = zIsOrEnclosedByPrivateType;
                        int i16 = i7;
                        MethodBinding methodBinding4 = methodBindingArr4[i14];
                        if (methodBinding4 == null && methodBindingArr != null && this.type.isPublic()) {
                            MethodBinding methodBinding5 = methodBindingArrConcreteFirst[i14];
                            if (methodBinding5.isPublic() && !methodBinding5.declaringClass.isInterface() && !methodBinding5.declaringClass.isPublic()) {
                                this.type.addSyntheticBridgeMethod(methodBinding5.original());
                            }
                        }
                        if (!z6 && methodBinding4 == null && methodBindingArr != null) {
                            methodBindingArrConcreteFirst[i14].original().modifiers |= 134217728;
                        }
                        MethodBinding methodBinding6 = methodBindingArrConcreteFirst[i14];
                        int i17 = i14 + 1;
                        int i18 = i17;
                        length4 = i15;
                        while (i18 < length4) {
                            MethodBinding methodBinding7 = methodBindingArrConcreteFirst[i18];
                            if ((methodBinding4 != methodBindingArr4[i18] || methodBinding4 == null) && !canSkipInheritedMethods(methodBinding6, methodBinding7)) {
                                boolean[] zArr11 = zArr8;
                                if (TypeBinding.notEquals(methodBinding6.declaringClass, methodBinding7.declaringClass)) {
                                    zArr5 = zArr11;
                                    zArr6 = zArr10;
                                    i5 = i18;
                                    methodBindingArr4 = methodBindingArr4;
                                    methodBindingArr3 = methodBindingArr3;
                                    length4 = length4;
                                    methodBindingArrConcreteFirst = methodBindingArrConcreteFirst;
                                    methodBindingArr = methodBindingArr;
                                    if (!isSkippableOrOverridden(methodBinding6, methodBinding7, zArr6, zArr5, zArr9, i5)) {
                                        isSkippableOrOverridden(methodBinding7, methodBinding6, zArr6, zArr5, zArr9, i14);
                                    }
                                } else {
                                    zArr5 = zArr11;
                                }
                                i18 = i5 + 1;
                                zArr8 = zArr5;
                                zArr10 = zArr6;
                                methodBindingArr4 = methodBindingArr4;
                                methodBindingArr3 = methodBindingArr3;
                                length4 = length4;
                                methodBindingArrConcreteFirst = methodBindingArrConcreteFirst;
                                methodBindingArr = methodBindingArr;
                            } else {
                                zArr5 = zArr8;
                            }
                            zArr6 = zArr10;
                            i5 = i18;
                            i18 = i5 + 1;
                            zArr8 = zArr5;
                            zArr10 = zArr6;
                            methodBindingArr4 = methodBindingArr4;
                            methodBindingArr3 = methodBindingArr3;
                            length4 = length4;
                            methodBindingArrConcreteFirst = methodBindingArrConcreteFirst;
                            methodBindingArr = methodBindingArr;
                        }
                        i14 = i17;
                        i7 = i16;
                        zIsOrEnclosedByPrivateType = z6;
                        zArr7 = zArr10;
                    }
                    int i19 = 0;
                    while (i19 < length4) {
                        MethodBinding methodBinding8 = methodBindingArr4[i19];
                        if (zArr7[i19]) {
                            zArr = zArr7;
                            i3 = length4;
                            z2 = zIsOrEnclosedByPrivateType;
                            i2 = i7;
                        } else {
                            MethodBinding methodBinding9 = methodBindingArrConcreteFirst[i19];
                            if (methodBinding8 == null) {
                                i++;
                                methodBindingArr3[i] = methodBinding9;
                            }
                            int i20 = i19 + 1;
                            int i21 = i;
                            while (i20 < length4) {
                                boolean[] zArr12 = zArr7;
                                int i22 = length4;
                                boolean z7 = zIsOrEnclosedByPrivateType;
                                int i23 = i7;
                                MethodBinding methodBinding10 = methodBindingArr4[i20];
                                if (methodBinding10 == null) {
                                    MethodBinding methodBinding11 = methodBindingArrConcreteFirst[i20];
                                    if ((methodBinding8 != methodBinding10 || methodBinding8 == null) && !canSkipInheritedMethods(methodBinding9, methodBinding11)) {
                                        MethodBinding methodBindingFindReplacedMethod = findReplacedMethod(methodBinding9, methodBinding11);
                                        if (methodBindingFindReplacedMethod != null) {
                                            i21++;
                                            methodBindingArr3[i21] = methodBindingFindReplacedMethod;
                                            zArr12[i20] = true;
                                        } else {
                                            MethodBinding methodBindingFindReplacedMethod2 = findReplacedMethod(methodBinding11, methodBinding9);
                                            if (methodBindingFindReplacedMethod2 != null) {
                                                i21++;
                                                methodBindingArr3[i21] = methodBindingFindReplacedMethod2;
                                                zArr12[i20] = true;
                                            } else if (methodBinding8 == null) {
                                                checkInheritedMethods(methodBinding9, methodBinding11);
                                            }
                                        }
                                    }
                                }
                                i20++;
                                i7 = i23;
                                zIsOrEnclosedByPrivateType = z7;
                                length4 = i22;
                                zArr7 = zArr12;
                            }
                            i2 = i7;
                            if (i21 == -1) {
                                zArr = zArr7;
                                i3 = length4;
                                i = i21;
                                z2 = zIsOrEnclosedByPrivateType;
                            } else {
                                if (i21 > 0) {
                                    int i24 = i21 + 1;
                                    if (i24 != length4) {
                                        zArr2 = new boolean[i24];
                                        zArr3 = new boolean[i24];
                                        int i25 = 0;
                                        while (i25 < i24) {
                                            boolean z8 = zIsOrEnclosedByPrivateType;
                                            int i26 = 0;
                                            while (true) {
                                                if (i26 >= length4) {
                                                    zArr4 = zArr7;
                                                    i4 = length4;
                                                    break;
                                                }
                                                i4 = length4;
                                                zArr4 = zArr7;
                                                if (methodBindingArr3[i25] == methodBindingArrConcreteFirst[i26]) {
                                                    zArr2[i25] = zArr8[i26];
                                                    zArr3[i25] = zArr9[i26];
                                                    break;
                                                } else {
                                                    i26++;
                                                    length4 = i4;
                                                    zArr7 = zArr4;
                                                }
                                            }
                                            i25++;
                                            zIsOrEnclosedByPrivateType = z8;
                                            length4 = i4;
                                            zArr7 = zArr4;
                                        }
                                        zArr = zArr7;
                                        i3 = length4;
                                        z2 = zIsOrEnclosedByPrivateType;
                                    } else {
                                        zArr = zArr7;
                                        i3 = length4;
                                        z2 = zIsOrEnclosedByPrivateType;
                                        zArr2 = zArr8;
                                        zArr3 = zArr9;
                                    }
                                    checkInheritedMethods(methodBindingArr3, i24, zArr2, zArr3);
                                } else {
                                    zArr = zArr7;
                                    i3 = length4;
                                    z2 = zIsOrEnclosedByPrivateType;
                                    if (zMustImplementAbstractMethods && methodBindingArr3[0].isAbstract() && methodBinding8 == null) {
                                        checkAbstractMethod(methodBindingArr3[0]);
                                    }
                                }
                                while (i21 >= 0) {
                                    methodBindingArr3[i21] = null;
                                    i21--;
                                }
                                i = i21;
                            }
                            i19++;
                            i7 = i2;
                            zIsOrEnclosedByPrivateType = z2;
                            length4 = i3;
                            zArr7 = zArr;
                        }
                        i19++;
                        i7 = i2;
                        zIsOrEnclosedByPrivateType = z2;
                        length4 = i3;
                        zArr7 = zArr;
                    }
                }
            }
            length = i7;
            z4 = z;
            cArr2 = cArr;
            i6 = 0;
            z3 = true;
        }
    }

    boolean isSkippableOrOverridden(MethodBinding methodBinding, MethodBinding methodBinding2, boolean[] zArr, boolean[] zArr2, boolean[] zArr3, int i) {
        boolean zIsInterface = methodBinding.declaringClass.isInterface();
        boolean zIsInterface2 = methodBinding2.declaringClass.isInterface();
        if (!zIsInterface && zIsInterface2) {
            if (!methodBinding.isAbstract() && isParameterSubsignature(methodBinding, methodBinding2)) {
                zArr3[i] = false;
                return true;
            }
            if (isInterfaceMethodImplemented(methodBinding2, methodBinding, methodBinding2.declaringClass)) {
                zArr[i] = true;
                zArr2[i] = true;
                return true;
            }
        } else if (zIsInterface == zIsInterface2 && methodBinding.declaringClass.isCompatibleWith(methodBinding2.declaringClass) && isMethodSubsignature(methodBinding, methodBinding2)) {
            zArr[i] = true;
            zArr2[i] = true;
            return true;
        }
        return false;
    }

    MethodBinding findReplacedMethod(MethodBinding methodBinding, MethodBinding methodBinding2) {
        MethodBinding methodBindingComputeSubstituteMethod = computeSubstituteMethod(methodBinding2, methodBinding);
        if (methodBindingComputeSubstituteMethod == null) {
            return null;
        }
        if ((!methodBinding.isAbstract() || methodBinding2.isAbstract() || (methodBinding2.isDefaultMethod() && methodBinding.declaringClass.isClass())) && isSubstituteParameterSubsignature(methodBinding, methodBindingComputeSubstituteMethod)) {
            return methodBindingComputeSubstituteMethod;
        }
        return null;
    }

    /* JADX WARN: Code duplicated, block: B:46:0x0092  */
    /* JADX WARN: Code duplicated, block: B:47:0x0094  */
    /* JADX WARN: Code duplicated, block: B:49:0x009c  */
    /* JADX WARN: Code duplicated, block: B:52:0x00a2 A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:56:0x00bc  */
    /* JADX WARN: Code duplicated, block: B:60:0x00c9  */
    /* JADX WARN: Code duplicated, block: B:8:0x0012 A[PHI: r2 r3
      0x0012: PHI (r2v19 char[][]) = (r2v2 char[][]), (r2v2 char[][]), (r2v3 char[][]) binds: [B:7:0x0010, B:10:0x0022, B:76:0x0012] A[DONT_GENERATE, DONT_INLINE]
      0x0012: PHI (r3v12 int) = (r3v2 int), (r3v2 int), (r3v3 int) binds: [B:7:0x0010, B:10:0x0022, B:76:0x0012] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:91:0x00df A[ADDED_TO_REGION, REMOVE, SYNTHETIC] */
    void checkTypeVariableMethods(TypeParameter typeParameter) {
        char c;
        MethodBinding methodBinding;
        MethodBinding methodBinding2;
        MethodBinding methodBinding3;
        MethodBinding methodBindingComputeSubstituteMethod;
        char[][] cArr = this.inheritedMethods.keyTable;
        int length = cArr.length;
        while (true) {
            int i = -1;
            int i2 = length - 1;
            if (i2 < 0) {
                return;
            }
            if (cArr[i2] == null) {
                cArr = cArr;
                i2 = i2;
                break;
            }
            MethodBinding[] methodBindingArr = (MethodBinding[]) this.inheritedMethods.valueTable[i2];
            if (methodBindingArr.length == 1) {
                cArr = cArr;
                i2 = i2;
                break;
                break;
            }
            MethodBinding[] methodBindingArr2 = new MethodBinding[methodBindingArr.length];
            int length2 = methodBindingArr.length;
            char c2 = 0;
            int i3 = -1;
            int i4 = 0;
            while (true) {
                if (i4 >= length2) {
                    cArr = cArr;
                    i2 = i2;
                    break;
                    break;
                }
                while (i3 >= 0) {
                    methodBindingArr2[i3] = null;
                    i3--;
                    cArr = cArr;
                    i2 = i2;
                    i = -1;
                }
                MethodBinding methodBinding4 = methodBindingArr[i4];
                if (methodBinding4 != null) {
                    i3++;
                    methodBindingArr2[i3] = methodBinding4;
                    for (int i5 = i4 + 1; i5 < length2; i5++) {
                        MethodBinding methodBinding5 = methodBindingArr[i5];
                        if (!canSkipInheritedMethods(methodBinding4, methodBinding5) && (methodBindingComputeSubstituteMethod = computeSubstituteMethod(methodBinding5, methodBinding4)) != null && isSubstituteParameterSubsignature(methodBinding4, methodBindingComputeSubstituteMethod)) {
                            i3++;
                            methodBindingArr2[i3] = methodBindingComputeSubstituteMethod;
                            methodBindingArr[i5] = null;
                        }
                    }
                }
                if (i3 > 0) {
                    MethodBinding methodBinding6 = methodBindingArr2[c2];
                    int i6 = i3 + 1;
                    int i7 = i6;
                    while (true) {
                        i7 += i;
                        if (i7 <= 0) {
                            cArr = cArr;
                            i2 = i2;
                            c = c2;
                            break;
                        }
                        MethodBinding methodBinding7 = methodBindingArr2[i7];
                        if (methodBinding6.declaringClass.isInterface()) {
                            methodBinding = methodBinding6;
                        } else {
                            if (methodBinding6.declaringClass.isClass()) {
                                methodBinding2 = methodBinding6;
                                methodBinding = null;
                            } else {
                                methodBinding = null;
                            }
                            if (methodBinding7.declaringClass.isInterface()) {
                                methodBinding = methodBinding7;
                            } else {
                                if (methodBinding7.declaringClass.isClass()) {
                                    methodBinding3 = methodBinding7;
                                }
                                if (methodBinding != null || methodBinding3 == null || isAsVisible(methodBinding3, methodBinding)) {
                                    c = 0;
                                } else {
                                    c = 0;
                                    problemReporter().inheritedMethodReducesVisibility(typeParameter, methodBinding3, new MethodBinding[]{methodBinding});
                                }
                                if (!areReturnTypesCompatible(methodBinding6, methodBinding7) && !(methodBinding6.declaringClass.isInterface() && methodBinding7.declaringClass.isInterface() && areReturnTypesCompatible(methodBinding7, methodBinding6))) {
                                    break;
                                }
                                cArr = cArr;
                                c2 = c;
                                i2 = i2;
                                i = -1;
                            }
                            methodBinding3 = methodBinding2;
                            if (methodBinding != null) {
                                c = 0;
                            } else {
                                c = 0;
                            }
                            if (!areReturnTypesCompatible(methodBinding6, methodBinding7)) {
                                break;
                            }
                            cArr = cArr;
                            c2 = c;
                            i2 = i2;
                            i = -1;
                        }
                        methodBinding2 = null;
                        if (methodBinding7.declaringClass.isInterface()) {
                            methodBinding = methodBinding7;
                        } else {
                            if (methodBinding7.declaringClass.isClass()) {
                                methodBinding3 = methodBinding7;
                            }
                            if (methodBinding != null) {
                                c = 0;
                            } else {
                                c = 0;
                            }
                            if (!areReturnTypesCompatible(methodBinding6, methodBinding7)) {
                                break;
                                break;
                            }
                            cArr = cArr;
                            c2 = c;
                            i2 = i2;
                            i = -1;
                        }
                        methodBinding3 = methodBinding2;
                        if (methodBinding != null) {
                            c = 0;
                        } else {
                            c = 0;
                        }
                        if (!areReturnTypesCompatible(methodBinding6, methodBinding7)) {
                            break;
                            break;
                        }
                        cArr = cArr;
                        c2 = c;
                        i2 = i2;
                        i = -1;
                    }
                    if (i7 > 0) {
                        problemReporter().inheritedMethodsHaveIncompatibleReturnTypes(typeParameter, methodBindingArr2, i6);
                        break;
                    }
                } else {
                    cArr = cArr;
                    i2 = i2;
                    c = c2;
                }
                i4++;
                cArr = cArr;
                c2 = c;
                i2 = i2;
                i = -1;
            }
            cArr = cArr;
            length = i2;
        }
    }

    boolean detectInheritedNameClash(MethodBinding methodBinding, MethodBinding methodBinding2) {
        if (!methodBinding.areParameterErasuresEqual(methodBinding2) || TypeBinding.notEquals(methodBinding.returnType.erasure(), methodBinding2.returnType.erasure())) {
            return false;
        }
        if (TypeBinding.notEquals(methodBinding.declaringClass.erasure(), methodBinding2.declaringClass.erasure()) && (methodBinding.declaringClass.findSuperTypeOriginatingFrom(methodBinding2.declaringClass) != null || methodBinding2.declaringClass.findSuperTypeOriginatingFrom(methodBinding.declaringClass) != null)) {
            return false;
        }
        problemReporter().inheritedMethodsHaveNameClash(this.type, methodBinding, methodBinding2);
        return true;
    }

    /* JADX WARN: Code duplicated, block: B:23:0x0051 A[EDGE_INSN: B:23:0x0051->B:24:0x0052 BREAK  A[LOOP:0: B:15:0x0040->B:22:0x004e]] */
    boolean detectNameClash(MethodBinding methodBinding, MethodBinding methodBinding2, boolean z) {
        MethodBinding methodBinding3;
        MethodBinding methodBindingOriginal = methodBinding2.original();
        if (!methodBinding.areParameterErasuresEqual(methodBindingOriginal)) {
            return false;
        }
        int i = (this.environment.globalOptions.complianceLevel == ClassFileConstants.JDK1_6 && TypeBinding.notEquals(methodBinding.returnType.erasure(), methodBindingOriginal.returnType.erasure())) ? 0 : 1;
        if (!z) {
            MethodBinding[] methodBindingArr = (MethodBinding[]) this.currentMethods.get(methodBinding2.selector);
            if (methodBindingArr.length <= 1) {
                methodBinding3 = methodBinding2;
                break;
            }
            int length = methodBindingArr.length;
            int i2 = 0;
            while (true) {
                if (i2 >= length) {
                    methodBinding3 = methodBinding2;
                    break;
                }
                methodBinding3 = methodBindingArr[i2];
                if (methodBinding3 != methodBinding && doesMethodOverride(methodBinding3, methodBinding2)) {
                    break;
                }
                i2++;
            }
        } else {
            methodBinding3 = methodBinding2;
            break;
        }
        if (!methodBinding.areParameterErasuresEqual(methodBinding3.original())) {
            return false;
        }
        MethodBinding methodBindingOriginal2 = methodBinding2.original();
        ProblemReporter problemReporter = problemReporter(methodBinding);
        if (!methodBinding2.declaringClass.isRawType()) {
            methodBinding2 = methodBindingOriginal2;
        }
        problemReporter.methodNameClash(methodBinding, methodBinding2, i);
        return i != 0;
    }

    boolean doTypeVariablesClash(MethodBinding methodBinding, MethodBinding methodBinding2) {
        return (methodBinding.typeVariables == Binding.NO_TYPE_VARIABLES || (methodBinding2 instanceof ParameterizedGenericMethodBinding)) ? false : true;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.MethodVerifier
    SimpleSet findSuperinterfaceCollisions(ReferenceBinding referenceBinding, ReferenceBinding[] referenceBindingArr) {
        int length;
        ReferenceBinding[] referenceBindingArr2;
        ReferenceBinding[] referenceBindingArr3;
        SimpleSet simpleSet = null;
        if (referenceBindingArr != Binding.NO_SUPERINTERFACES) {
            length = referenceBindingArr.length;
        } else {
            referenceBindingArr = null;
            length = 0;
        }
        boolean zIsHierarchyInconsistent = this.type.isHierarchyInconsistent();
        while (referenceBinding != null && referenceBinding.isValidBinding()) {
            zIsHierarchyInconsistent |= referenceBinding.isHierarchyInconsistent();
            ReferenceBinding[] referenceBindingArrSuperInterfaces = referenceBinding.superInterfaces();
            if (referenceBindingArrSuperInterfaces != Binding.NO_SUPERINTERFACES) {
                if (referenceBindingArr == null) {
                    length = referenceBindingArrSuperInterfaces.length;
                    referenceBindingArr = referenceBindingArrSuperInterfaces;
                } else {
                    int length2 = length + referenceBindingArrSuperInterfaces.length;
                    if (length2 >= referenceBindingArr.length) {
                        referenceBindingArr3 = new ReferenceBinding[length2 + 5];
                        System.arraycopy(referenceBindingArr, 0, referenceBindingArr3, 0, length);
                    } else {
                        referenceBindingArr3 = referenceBindingArr;
                    }
                    int i = length;
                    for (ReferenceBinding referenceBinding2 : referenceBindingArrSuperInterfaces) {
                        int i2 = 0;
                        while (true) {
                            if (i2 < i) {
                                if (TypeBinding.equalsEquals(referenceBinding2, referenceBindingArr3[i2])) {
                                    break;
                                }
                                i2++;
                            } else {
                                referenceBindingArr3[i] = referenceBinding2;
                                i++;
                                break;
                            }
                        }
                    }
                    referenceBindingArr = referenceBindingArr3;
                    length = i;
                }
            }
            referenceBinding = referenceBinding.superclass();
        }
        for (int i3 = 0; i3 < length; i3++) {
            ReferenceBinding referenceBinding3 = referenceBindingArr[i3];
            if (referenceBinding3.isValidBinding()) {
                zIsHierarchyInconsistent |= referenceBinding3.isHierarchyInconsistent();
                ReferenceBinding[] referenceBindingArrSuperInterfaces2 = referenceBinding3.superInterfaces();
                if (referenceBindingArrSuperInterfaces2 != Binding.NO_SUPERINTERFACES) {
                    int length3 = length + referenceBindingArrSuperInterfaces2.length;
                    if (length3 >= referenceBindingArr.length) {
                        referenceBindingArr2 = new ReferenceBinding[length3 + 5];
                        System.arraycopy(referenceBindingArr, 0, referenceBindingArr2, 0, length);
                    } else {
                        referenceBindingArr2 = referenceBindingArr;
                    }
                    int i4 = length;
                    for (ReferenceBinding referenceBinding4 : referenceBindingArrSuperInterfaces2) {
                        int i5 = 0;
                        while (true) {
                            if (i5 < i4) {
                                if (TypeBinding.equalsEquals(referenceBinding4, referenceBindingArr2[i5])) {
                                    break;
                                }
                                i5++;
                            } else {
                                referenceBindingArr2[i4] = referenceBinding4;
                                i4++;
                                break;
                            }
                        }
                    }
                    referenceBindingArr = referenceBindingArr2;
                    length = i4;
                }
            }
        }
        if (!zIsHierarchyInconsistent) {
            return null;
        }
        for (int i6 = 0; i6 < length; i6++) {
            ReferenceBinding referenceBinding5 = referenceBindingArr[i6];
            if (referenceBinding5.isValidBinding()) {
                TypeBinding typeBindingErasure = referenceBinding5.erasure();
                for (int i7 = i6 + 1; i7 < length; i7++) {
                    ReferenceBinding referenceBinding6 = referenceBindingArr[i7];
                    if (referenceBinding6.isValidBinding() && TypeBinding.equalsEquals(referenceBinding6.erasure(), typeBindingErasure)) {
                        if (simpleSet == null) {
                            simpleSet = new SimpleSet(length);
                        }
                        simpleSet.add(referenceBindingArr[i6]);
                        simpleSet.add(referenceBindingArr[i7]);
                    }
                }
            }
        }
        return simpleSet;
    }

    boolean isAcceptableReturnTypeOverride(MethodBinding methodBinding, MethodBinding methodBinding2) {
        if (methodBinding2.declaringClass.isRawType()) {
            return true;
        }
        MethodBinding methodBindingOriginal = methodBinding2.original();
        TypeBinding typeBindingLeafComponentType = methodBindingOriginal.returnType.leafComponentType();
        if (typeBindingLeafComponentType.isParameterizedTypeWithActualArguments()) {
            return !methodBinding.returnType.leafComponentType().isRawType();
        }
        TypeBinding typeBindingLeafComponentType2 = methodBinding.returnType.leafComponentType();
        return ((typeBindingLeafComponentType2.kind() != 4100 || !TypeBinding.equalsEquals(typeBindingLeafComponentType2, methodBinding2.returnType.leafComponentType())) && typeBindingLeafComponentType.isTypeVariable() && ((TypeVariableBinding) typeBindingLeafComponentType).declaringElement == methodBindingOriginal) ? false : true;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.MethodVerifier
    boolean isInterfaceMethodImplemented(MethodBinding methodBinding, MethodBinding methodBinding2, ReferenceBinding referenceBinding) {
        MethodBinding methodBindingComputeSubstituteMethod;
        if ((methodBinding.original() != methodBinding && methodBinding2.declaringClass.isInterface()) || (methodBindingComputeSubstituteMethod = computeSubstituteMethod(methodBinding, methodBinding2)) == null || !doesMethodOverride(methodBinding2, methodBindingComputeSubstituteMethod)) {
            return false;
        }
        if (TypeBinding.equalsEquals(methodBindingComputeSubstituteMethod.returnType, methodBinding2.returnType)) {
            return true;
        }
        return TypeBinding.notEquals(this.type, methodBinding2.declaringClass) && !methodBinding2.declaringClass.isInterface() && areReturnTypesCompatible(methodBinding2, methodBindingComputeSubstituteMethod);
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.MethodVerifier
    public boolean isMethodSubsignature(MethodBinding methodBinding, MethodBinding methodBinding2) {
        if (!CharOperation.equals(methodBinding.selector, methodBinding2.selector)) {
            return false;
        }
        if (methodBinding.declaringClass.isParameterizedType()) {
            methodBinding = methodBinding.original();
        }
        MethodBinding methodBindingFindOriginalInheritedMethod = methodBinding.findOriginalInheritedMethod(methodBinding2);
        if (methodBindingFindOriginalInheritedMethod != null) {
            methodBinding2 = methodBindingFindOriginalInheritedMethod;
        }
        return isParameterSubsignature(methodBinding, methodBinding2);
    }

    boolean isUnsafeReturnTypeOverride(MethodBinding methodBinding, MethodBinding methodBinding2) {
        if (TypeBinding.equalsEquals(methodBinding.returnType, methodBinding2.returnType.erasure())) {
            TypeBinding[] typeBindingArr = methodBinding.parameters;
            TypeBinding[] typeBindingArr2 = methodBinding2.parameters;
            int length = typeBindingArr.length;
            for (int i = 0; i < length; i++) {
                if (!areTypesEqual(typeBindingArr[i], typeBindingArr2[i])) {
                    return true;
                }
            }
        }
        return (methodBinding.typeVariables != Binding.NO_TYPE_VARIABLES || methodBinding2.original().typeVariables == Binding.NO_TYPE_VARIABLES || methodBinding.returnType.erasure().findSuperTypeOriginatingFrom(methodBinding2.returnType.erasure()) == null) ? false : true;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.MethodVerifier
    boolean reportIncompatibleReturnTypeError(MethodBinding methodBinding, MethodBinding methodBinding2) {
        if (isUnsafeReturnTypeOverride(methodBinding, methodBinding2)) {
            problemReporter(methodBinding).unsafeReturnTypeOverride(methodBinding, methodBinding2, this.type);
            return false;
        }
        return super.reportIncompatibleReturnTypeError(methodBinding, methodBinding2);
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.MethodVerifier
    void verify() throws Throwable {
        ReferenceBinding referenceBinding;
        if (this.type.isAnnotationType()) {
            this.type.detectAnnotationCycle();
        }
        super.verify();
        reportRawReferences();
        int length = this.type.typeVariables.length;
        while (true) {
            length--;
            if (length < 0) {
                return;
            }
            TypeVariableBinding typeVariableBinding = this.type.typeVariables[length];
            if (typeVariableBinding.superInterfaces != Binding.NO_SUPERINTERFACES && (typeVariableBinding.superInterfaces.length != 1 || typeVariableBinding.superclass.f626id != 1)) {
                this.currentMethods = new HashtableOfObject(0);
                ReferenceBinding referenceBindingSuperclass = typeVariableBinding.superclass();
                if (referenceBindingSuperclass.kind() == 4100) {
                    referenceBindingSuperclass = (ReferenceBinding) referenceBindingSuperclass.erasure();
                }
                ReferenceBinding[] referenceBindingArrSuperInterfaces = typeVariableBinding.superInterfaces();
                ReferenceBinding[] referenceBindingArr = new ReferenceBinding[referenceBindingArrSuperInterfaces.length];
                int length2 = referenceBindingArrSuperInterfaces.length;
                while (true) {
                    length2--;
                    if (length2 < 0) {
                        break;
                    }
                    if (referenceBindingArrSuperInterfaces[length2].kind() == 4100) {
                        referenceBinding = (ReferenceBinding) referenceBindingArrSuperInterfaces[length2].erasure();
                    } else {
                        referenceBinding = referenceBindingArrSuperInterfaces[length2];
                    }
                    referenceBindingArr[length2] = referenceBinding;
                }
                computeInheritedMethods(referenceBindingSuperclass, referenceBindingArr);
                checkTypeVariableMethods(this.type.scope.referenceContext.typeParameters[length]);
            }
        }
    }
}
