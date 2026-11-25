package org.eclipse.jdt.internal.compiler.lookup;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.compiler.ast.TypeDeclaration;
import org.eclipse.jdt.internal.compiler.ast.TypeReference;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.eclipse.jdt.internal.compiler.problem.ProblemReporter;
import org.eclipse.jdt.internal.compiler.util.HashtableOfObject;
import org.eclipse.jdt.internal.compiler.util.SimpleSet;
import org.eclipse.jdt.internal.compiler.util.Sorting;

/* JADX INFO: loaded from: classes14.dex */
public abstract class MethodVerifier extends ImplicitNullAnnotationVerifier {
    HashtableOfObject currentMethods;
    HashtableOfObject inheritedMethods;
    HashtableOfObject inheritedOverriddenMethods;
    SourceTypeBinding type;

    protected boolean canOverridingMethodDifferInErasure(MethodBinding methodBinding, MethodBinding methodBinding2) {
        return false;
    }

    void checkForBridgeMethod(MethodBinding methodBinding, MethodBinding methodBinding2, MethodBinding[] methodBindingArr) {
    }

    abstract void checkMethods();

    SimpleSet findSuperinterfaceCollisions(ReferenceBinding referenceBinding, ReferenceBinding[] referenceBindingArr) {
        return null;
    }

    public void reportRawReferences(MethodBinding methodBinding, MethodBinding methodBinding2) {
    }

    MethodVerifier(LookupEnvironment lookupEnvironment) {
        super(lookupEnvironment);
        this.type = null;
        this.inheritedMethods = null;
        this.currentMethods = null;
        this.inheritedOverriddenMethods = null;
    }

    boolean areMethodsCompatible(MethodBinding methodBinding, MethodBinding methodBinding2) {
        return areMethodsCompatible(methodBinding, methodBinding2, this.environment);
    }

    static boolean areMethodsCompatible(MethodBinding methodBinding, MethodBinding methodBinding2, LookupEnvironment lookupEnvironment) {
        MethodBinding methodBindingOriginal = methodBinding.original();
        MethodBinding methodBindingFindOriginalInheritedMethod = methodBindingOriginal.findOriginalInheritedMethod(methodBinding2);
        if (methodBindingFindOriginalInheritedMethod == null) {
            return false;
        }
        return isParameterSubsignature(methodBindingOriginal, methodBindingFindOriginalInheritedMethod, lookupEnvironment);
    }

    boolean areReturnTypesCompatible(MethodBinding methodBinding, MethodBinding methodBinding2) {
        return areReturnTypesCompatible(methodBinding, methodBinding2, this.type.scope.environment());
    }

    public static boolean areReturnTypesCompatible(MethodBinding methodBinding, MethodBinding methodBinding2, LookupEnvironment lookupEnvironment) {
        if (TypeBinding.equalsEquals(methodBinding.returnType, methodBinding2.returnType)) {
            return true;
        }
        if (lookupEnvironment.globalOptions.sourceLevel >= ClassFileConstants.JDK1_5) {
            if (methodBinding.returnType.isBaseType()) {
                return false;
            }
            if (!methodBinding.declaringClass.isInterface() && methodBinding.declaringClass.f626id == 1) {
                return methodBinding2.returnType.isCompatibleWith(methodBinding.returnType);
            }
            return methodBinding.returnType.isCompatibleWith(methodBinding2.returnType);
        }
        return areTypesEqual(methodBinding.returnType.erasure(), methodBinding2.returnType.erasure());
    }

    boolean canSkipInheritedMethods() {
        return (this.type.superclass() == null || !this.type.superclass().isAbstract()) && this.type.superInterfaces() == Binding.NO_SUPERINTERFACES;
    }

    boolean canSkipInheritedMethods(MethodBinding methodBinding, MethodBinding methodBinding2) {
        return methodBinding2 == null || TypeBinding.equalsEquals(methodBinding.declaringClass, methodBinding2.declaringClass);
    }

    void checkAbstractMethod(MethodBinding methodBinding) {
        if (mustImplementAbstractMethod(methodBinding.declaringClass)) {
            TypeDeclaration typeDeclaration = this.type.scope.referenceContext;
            if (typeDeclaration != null) {
                typeDeclaration.addMissingAbstractMethodFor(methodBinding).scope.problemReporter().abstractMethodMustBeImplemented(this.type, methodBinding);
            } else {
                problemReporter().abstractMethodMustBeImplemented(this.type, methodBinding);
            }
        }
    }

    void checkAgainstInheritedMethods(MethodBinding methodBinding, MethodBinding[] methodBindingArr, int i, MethodBinding[] methodBindingArr2) {
        if (this.type.isAnnotationType()) {
            problemReporter().annotationCannotOverrideMethod(methodBinding, methodBindingArr[i - 1]);
            return;
        }
        CompilerOptions compilerOptions = this.type.scope.compilerOptions();
        int[] iArrFindOverriddenInheritedMethods = i > 1 ? findOverriddenInheritedMethods(methodBindingArr, i) : null;
        int i2 = i;
        while (true) {
            i2--;
            if (i2 < 0) {
                break;
            }
            MethodBinding methodBinding2 = methodBindingArr[i2];
            if (iArrFindOverriddenInheritedMethods == null || iArrFindOverriddenInheritedMethods[i2] == 0) {
                if (methodBinding.isStatic() != methodBinding2.isStatic()) {
                    problemReporter(methodBinding).staticAndInstanceConflict(methodBinding, methodBinding2);
                } else {
                    if (methodBinding2.isAbstract()) {
                        if (methodBinding2.declaringClass.isInterface()) {
                            methodBinding.modifiers |= 536870912;
                        } else {
                            methodBinding.modifiers |= 805306368;
                        }
                    } else if (methodBinding2.isPublic() || !this.type.isInterface()) {
                        if (methodBinding.isDefaultMethod() && !methodBinding2.isFinal() && methodBinding2.declaringClass.f626id == 1) {
                            problemReporter(methodBinding).defaultMethodOverridesObjectMethod(methodBinding);
                        } else if (methodBinding2.isDefaultMethod()) {
                            methodBinding.modifiers |= 536870912;
                        } else {
                            methodBinding.modifiers |= 268435456;
                        }
                    }
                    if (areReturnTypesCompatible(methodBinding, methodBinding2) || (methodBinding.returnType.tagBits & 128) != 0 || !reportIncompatibleReturnTypeError(methodBinding, methodBinding2)) {
                        reportRawReferences(methodBinding, methodBinding2);
                        if (methodBinding.thrownExceptions != Binding.NO_EXCEPTIONS) {
                            checkExceptions(methodBinding, methodBinding2);
                        }
                        if (methodBinding2.isFinal()) {
                            problemReporter(methodBinding).finalMethodCannotBeOverridden(methodBinding, methodBinding2);
                        }
                        if (!isAsVisible(methodBinding, methodBinding2)) {
                            problemReporter(methodBinding).visibilityConflict(methodBinding, methodBinding2);
                        }
                        if (methodBinding2.isSynchronized() && !methodBinding.isSynchronized()) {
                            problemReporter(methodBinding).missingSynchronizedOnInheritedMethod(methodBinding, methodBinding2);
                        }
                        if (compilerOptions.reportDeprecationWhenOverridingDeprecatedMethod && methodBinding2.isViewedAsDeprecated() && (!methodBinding.isViewedAsDeprecated() || compilerOptions.reportDeprecationInsideDeprecatedCode)) {
                            ReferenceBinding referenceBinding = methodBinding2.declaringClass;
                            if (referenceBinding.isInterface()) {
                                int i3 = i;
                                while (true) {
                                    i3--;
                                    if (i3 >= 0) {
                                        if (i2 == i3 || !methodBindingArr[i3].declaringClass.implementsInterface(referenceBinding, false)) {
                                        }
                                    }
                                }
                            }
                            problemReporter(methodBinding).overridesDeprecatedMethod(methodBinding, methodBinding2);
                        }
                    }
                }
            }
            if (!methodBinding2.isStatic() && !methodBinding2.isFinal()) {
                checkForBridgeMethod(methodBinding, methodBinding2, methodBindingArr2);
            }
        }
        MethodBinding[] methodBindingArr3 = (MethodBinding[]) this.inheritedOverriddenMethods.get(methodBinding.selector);
        if (methodBindingArr3 == null) {
            return;
        }
        int length = methodBindingArr3.length;
        while (true) {
            length--;
            if (length < 0) {
                return;
            }
            MethodBinding methodBinding3 = methodBindingArr3[length];
            if (isParameterSubsignature(methodBinding, methodBinding3) && !methodBinding3.isStatic() && !methodBinding3.isFinal()) {
                checkForBridgeMethod(methodBinding, methodBinding3, methodBindingArr2);
            }
        }
    }

    void addBridgeMethodCandidate(MethodBinding methodBinding) {
        MethodBinding[] methodBindingArr;
        MethodBinding[] methodBindingArr2 = (MethodBinding[]) this.inheritedOverriddenMethods.get(methodBinding.selector);
        if (methodBindingArr2 == null) {
            methodBindingArr = new MethodBinding[]{methodBinding};
        } else {
            int length = methodBindingArr2.length;
            MethodBinding[] methodBindingArr3 = new MethodBinding[length + 1];
            System.arraycopy(methodBindingArr2, 0, methodBindingArr3, 0, length);
            methodBindingArr3[length] = methodBinding;
            methodBindingArr = methodBindingArr3;
        }
        this.inheritedOverriddenMethods.put(methodBinding.selector, methodBindingArr);
    }

    void checkConcreteInheritedMethod(MethodBinding methodBinding, MethodBinding[] methodBindingArr) {
        if (methodBinding.isStatic()) {
            problemReporter().staticInheritedMethodConflicts(this.type, methodBinding, methodBindingArr);
        }
        if (!methodBinding.isPublic()) {
            int length = methodBindingArr.length;
            int i = 0;
            if (methodBinding.isProtected()) {
                while (i < length && !methodBindingArr[i].isPublic()) {
                    i++;
                }
            } else if (methodBinding.isDefault()) {
                while (i < length && methodBindingArr[i].isDefault()) {
                    i++;
                }
            }
            if (i < length) {
                problemReporter().inheritedMethodReducesVisibility(this.type, methodBinding, methodBindingArr);
            }
        }
        if (methodBinding.thrownExceptions != Binding.NO_EXCEPTIONS) {
            int length2 = methodBindingArr.length;
            while (true) {
                length2--;
                if (length2 < 0) {
                    break;
                } else {
                    checkExceptions(methodBinding, methodBindingArr[length2]);
                }
            }
        }
        if (methodBinding.isOrEnclosedByPrivateType()) {
            methodBinding.original().modifiers |= 134217728;
        }
    }

    void checkExceptions(MethodBinding methodBinding, MethodBinding methodBinding2) {
        ReferenceBinding[] referenceBindingArrResolvedExceptionTypesFor = resolvedExceptionTypesFor(methodBinding);
        ReferenceBinding[] referenceBindingArrResolvedExceptionTypesFor2 = resolvedExceptionTypesFor(methodBinding2);
        int length = referenceBindingArrResolvedExceptionTypesFor.length;
        while (true) {
            length--;
            if (length < 0) {
                return;
            }
            ReferenceBinding referenceBinding = referenceBindingArrResolvedExceptionTypesFor[length];
            int length2 = referenceBindingArrResolvedExceptionTypesFor2.length;
            do {
                length2--;
                if (length2 <= -1) {
                    break;
                }
            } while (!isSameClassOrSubclassOf(referenceBinding, referenceBindingArrResolvedExceptionTypesFor2[length2]));
            if (length2 == -1 && !referenceBinding.isUncheckedException(false) && (referenceBinding.tagBits & 128) == 0) {
                problemReporter(methodBinding).incompatibleExceptionInThrowsClause(this.type, methodBinding, methodBinding2, referenceBinding);
            }
        }
    }

    void checkForMissingHashCodeMethod() throws Throwable {
        boolean z;
        MethodBinding exactMethod;
        MethodBinding[] methods = this.type.getMethods(TypeConstants.EQUALS);
        int length = methods.length;
        loop0: while (true) {
            z = false;
            while (!z && (length = length - 1) >= 0) {
                if (methods[length].parameters.length == 1 && methods[length].parameters[0].f626id == 1) {
                    z = true;
                }
            }
            break loop0;
        }
        if (z && (exactMethod = this.type.getExactMethod(TypeConstants.HASHCODE, Binding.NO_PARAMETERS, null)) != null && exactMethod.declaringClass.f626id == 1) {
            problemReporter().shouldImplementHashcode(this.type);
        }
    }

    void checkForRedundantSuperinterfaces(ReferenceBinding referenceBinding, ReferenceBinding[] referenceBindingArr) {
        int i;
        ReferenceBinding[] referenceBindingArr2;
        if (referenceBindingArr == Binding.NO_SUPERINTERFACES) {
            return;
        }
        SimpleSet simpleSet = new SimpleSet(referenceBindingArr.length);
        int length = referenceBindingArr.length;
        int i2 = 0;
        SimpleSet simpleSet2 = null;
        int i3 = 0;
        while (true) {
            i = 3;
            if (i3 >= length) {
                break;
            }
            ReferenceBinding referenceBinding2 = referenceBindingArr[i3];
            for (int i4 = 0; i4 < length; i4++) {
                ReferenceBinding referenceBinding3 = referenceBindingArr[i4];
                if (i3 != i4 && referenceBinding2.implementsInterface(referenceBinding3, true)) {
                    if (simpleSet2 == null) {
                        simpleSet2 = new SimpleSet(3);
                    } else if (simpleSet2.includes(referenceBinding3)) {
                    }
                    SimpleSet simpleSet3 = simpleSet2;
                    simpleSet3.add(referenceBinding3);
                    TypeReference[] typeReferenceArr = this.type.scope.referenceContext.superInterfaces;
                    for (TypeReference typeReference : typeReferenceArr) {
                        if (TypeBinding.equalsEquals(typeReference.resolvedType, referenceBinding2)) {
                            problemReporter().redundantSuperInterface(this.type, typeReferenceArr[i4], referenceBinding3, referenceBinding2);
                            break;
                        }
                    }
                    simpleSet2 = simpleSet3;
                }
            }
            simpleSet.add(referenceBinding2);
            i3++;
            i2 = 0;
        }
        SimpleSet simpleSet4 = new SimpleSet(5);
        for (ReferenceBinding referenceBindingSuperclass = referenceBinding; referenceBindingSuperclass != null && referenceBindingSuperclass.isValidBinding(); referenceBindingSuperclass = referenceBindingSuperclass.superclass()) {
            ReferenceBinding[] referenceBindingArrSuperInterfaces = referenceBindingSuperclass.superInterfaces();
            if (referenceBindingArrSuperInterfaces != Binding.NO_SUPERINTERFACES) {
                int length2 = referenceBindingArrSuperInterfaces.length;
                for (int i5 = i2; i5 < length2; i5++) {
                    ReferenceBinding referenceBinding4 = referenceBindingArrSuperInterfaces[i5];
                    if (!simpleSet4.includes(referenceBinding4) && referenceBinding4.isValidBinding()) {
                        if (simpleSet.includes(referenceBinding4)) {
                            if (simpleSet2 == null) {
                                simpleSet2 = new SimpleSet(3);
                            } else if (simpleSet2.includes(referenceBinding4)) {
                            }
                            SimpleSet simpleSet5 = simpleSet2;
                            simpleSet5.add(referenceBinding4);
                            TypeReference[] typeReferenceArr2 = this.type.scope.referenceContext.superInterfaces;
                            int length3 = typeReferenceArr2.length;
                            for (int i6 = i2; i6 < length3; i6++) {
                                if (TypeBinding.equalsEquals(typeReferenceArr2[i6].resolvedType, referenceBinding4)) {
                                    problemReporter().redundantSuperInterface(this.type, typeReferenceArr2[i6], referenceBinding4, referenceBindingSuperclass);
                                    break;
                                }
                            }
                            simpleSet2 = simpleSet5;
                        } else {
                            simpleSet4.add(referenceBinding4);
                        }
                    }
                }
            }
        }
        int i7 = simpleSet4.elementSize;
        if (i7 == 0) {
            return;
        }
        ReferenceBinding[] referenceBindingArr3 = new ReferenceBinding[i7];
        simpleSet4.asArray(referenceBindingArr3);
        int i8 = i2;
        while (i8 < i7) {
            ReferenceBinding referenceBinding5 = referenceBindingArr3[i8];
            ReferenceBinding[] referenceBindingArrSuperInterfaces2 = referenceBinding5.superInterfaces();
            if (referenceBindingArrSuperInterfaces2 != Binding.NO_SUPERINTERFACES) {
                int length4 = referenceBindingArrSuperInterfaces2.length;
                int i9 = i7 + length4;
                if (i9 >= referenceBindingArr3.length) {
                    referenceBindingArr2 = new ReferenceBinding[i9 + 5];
                    System.arraycopy(referenceBindingArr3, i2, referenceBindingArr2, i2, i7);
                } else {
                    referenceBindingArr2 = referenceBindingArr3;
                }
                int i10 = i2;
                while (i10 < length4) {
                    ReferenceBinding referenceBinding6 = referenceBindingArrSuperInterfaces2[i10];
                    if (!simpleSet4.includes(referenceBinding6) && referenceBinding6.isValidBinding()) {
                        if (simpleSet.includes(referenceBinding6)) {
                            if (simpleSet2 == null) {
                                simpleSet2 = new SimpleSet(i);
                            } else if (simpleSet2.includes(referenceBinding6)) {
                            }
                            SimpleSet simpleSet6 = simpleSet2;
                            simpleSet6.add(referenceBinding6);
                            TypeReference[] typeReferenceArr3 = this.type.scope.referenceContext.superInterfaces;
                            int length5 = typeReferenceArr3.length;
                            while (i2 < length5) {
                                if (TypeBinding.equalsEquals(typeReferenceArr3[i2].resolvedType, referenceBinding6)) {
                                    problemReporter().redundantSuperInterface(this.type, typeReferenceArr3[i2], referenceBinding6, referenceBinding5);
                                    break;
                                }
                                i2++;
                            }
                            simpleSet2 = simpleSet6;
                        } else {
                            simpleSet4.add(referenceBinding6);
                            referenceBindingArr2[i7] = referenceBinding6;
                            i7++;
                        }
                    }
                    i10++;
                    i2 = 0;
                    i = 3;
                }
                referenceBindingArr3 = referenceBindingArr2;
            }
            i8++;
            i2 = 0;
            i = 3;
        }
    }

    void checkInheritedMethods(MethodBinding[] methodBindingArr, int i, boolean[] zArr, boolean[] zArr2) {
        MethodBinding methodBinding;
        MethodBinding methodBinding2 = (this.type.isInterface() || methodBindingArr[0].isAbstract()) ? null : methodBindingArr[0];
        if (methodBinding2 == null) {
            MethodBinding methodBindingFindBestInheritedAbstractOrDefaultMethod = i == 1 ? methodBindingArr[0] : findBestInheritedAbstractOrDefaultMethod(methodBindingArr, i);
            boolean z = methodBindingFindBestInheritedAbstractOrDefaultMethod == null;
            if (z) {
                methodBindingFindBestInheritedAbstractOrDefaultMethod = methodBindingArr[0];
            }
            if (!mustImplementAbstractMethod(methodBindingFindBestInheritedAbstractOrDefaultMethod.declaringClass)) {
                if (z) {
                    problemReporter().inheritedMethodsHaveIncompatibleReturnTypes(this.type, methodBindingArr, i, zArr);
                    return;
                }
                return;
            }
            TypeDeclaration typeDeclaration = this.type.scope.referenceContext;
            MethodBinding methodBinding3 = methodBindingArr[0];
            if (methodBinding3 == methodBindingFindBestInheritedAbstractOrDefaultMethod || methodBinding3.declaringClass.isInterface()) {
                if (typeDeclaration != null) {
                    typeDeclaration.addMissingAbstractMethodFor(methodBindingFindBestInheritedAbstractOrDefaultMethod).scope.problemReporter().abstractMethodMustBeImplemented(this.type, methodBindingFindBestInheritedAbstractOrDefaultMethod);
                    return;
                } else {
                    problemReporter().abstractMethodMustBeImplemented(this.type, methodBindingFindBestInheritedAbstractOrDefaultMethod);
                    return;
                }
            }
            if (typeDeclaration != null) {
                typeDeclaration.addMissingAbstractMethodFor(methodBindingFindBestInheritedAbstractOrDefaultMethod).scope.problemReporter().abstractMethodMustBeImplemented(this.type, methodBindingFindBestInheritedAbstractOrDefaultMethod, methodBinding3);
                return;
            } else {
                problemReporter().abstractMethodMustBeImplemented(this.type, methodBindingFindBestInheritedAbstractOrDefaultMethod, methodBinding3);
                return;
            }
        }
        if (i < 2) {
            return;
        }
        int i2 = i;
        do {
            i2--;
            if (i2 <= 0) {
                break;
            }
        } while (checkInheritedReturnTypes(methodBinding2, methodBindingArr[i2]));
        if (i2 > 0) {
            MethodBinding methodBindingFindBestInheritedAbstractOrDefaultMethod2 = findBestInheritedAbstractOrDefaultMethod(methodBindingArr, i);
            if (methodBindingFindBestInheritedAbstractOrDefaultMethod2 == null) {
                problemReporter().inheritedMethodsHaveIncompatibleReturnTypes(this.type, methodBindingArr, i, zArr);
                return;
            } else {
                problemReporter().abstractMethodMustBeImplemented(this.type, methodBindingFindBestInheritedAbstractOrDefaultMethod2, methodBinding2);
                return;
            }
        }
        int i3 = i - 1;
        MethodBinding[] methodBindingArr2 = new MethodBinding[i3];
        int i4 = 0;
        for (int i5 = 0; i5 < i; i5++) {
            if (methodBindingArr[i5].isAbstract() || ((methodBinding = methodBindingArr[i5]) != methodBinding2 && methodBinding.isDefaultMethod())) {
                methodBindingArr2[i4] = methodBindingArr[i5];
                i4++;
            }
        }
        if (i4 == 0) {
            return;
        }
        if (i4 < i3) {
            MethodBinding[] methodBindingArr3 = new MethodBinding[i4];
            System.arraycopy(methodBindingArr2, 0, methodBindingArr3, 0, i4);
            methodBindingArr2 = methodBindingArr3;
        }
        checkConcreteInheritedMethod(methodBinding2, methodBindingArr2);
    }

    boolean checkInheritedReturnTypes(MethodBinding methodBinding, MethodBinding methodBinding2) {
        if (areReturnTypesCompatible(methodBinding, methodBinding2)) {
            return true;
        }
        return !this.type.isInterface() && (methodBinding.declaringClass.isClass() || !this.type.implementsInterface(methodBinding.declaringClass, false)) && (methodBinding2.declaringClass.isClass() || !this.type.implementsInterface(methodBinding2.declaringClass, false));
    }

    void checkPackagePrivateAbstractMethod(MethodBinding methodBinding) {
        PackageBinding packageBinding = methodBinding.declaringClass.fPackage;
        if (packageBinding == this.type.fPackage) {
            return;
        }
        ReferenceBinding referenceBindingSuperclass = this.type.superclass();
        char[] cArr = methodBinding.selector;
        while (referenceBindingSuperclass.isValidBinding() && referenceBindingSuperclass.isAbstract()) {
            if (packageBinding == referenceBindingSuperclass.fPackage) {
                MethodBinding[] methods = referenceBindingSuperclass.getMethods(cArr);
                int length = methods.length;
                while (true) {
                    length--;
                    if (length < 0) {
                        break;
                    }
                    MethodBinding methodBinding2 = methods[length];
                    if (!methodBinding2.isPrivate() && !methodBinding2.isConstructor() && !methodBinding2.isDefaultAbstract() && areMethodsCompatible(methodBinding2, methodBinding)) {
                        return;
                    }
                }
            }
            referenceBindingSuperclass = referenceBindingSuperclass.superclass();
            if (!TypeBinding.notEquals(referenceBindingSuperclass, methodBinding.declaringClass)) {
                problemReporter().abstractMethodCannotBeOverridden(this.type, methodBinding);
                return;
            }
        }
    }

    void computeInheritedMethods() {
        ReferenceBinding referenceBindingSuperclass;
        if (this.type.isInterface()) {
            referenceBindingSuperclass = this.type.scope.getJavaLangObject();
        } else {
            referenceBindingSuperclass = this.type.superclass();
        }
        computeInheritedMethods(referenceBindingSuperclass, this.type.superInterfaces());
        checkForRedundantSuperinterfaces(referenceBindingSuperclass, this.type.superInterfaces());
    }

    void computeInheritedMethods(ReferenceBinding referenceBinding, ReferenceBinding[] referenceBindingArr) {
        ReferenceBinding[] referenceBindingArrSortTypes;
        MethodBinding[] methodBindingArr;
        MethodBinding[] methodBindingArr2;
        MethodBinding[] methodBindingArr3;
        this.inheritedMethods = new HashtableOfObject(51);
        this.inheritedOverriddenMethods = new HashtableOfObject(11);
        HashtableOfObject hashtableOfObject = new HashtableOfObject(3);
        for (ReferenceBinding referenceBindingSuperclass = referenceBinding; referenceBindingSuperclass != null && referenceBindingSuperclass.isValidBinding(); referenceBindingSuperclass = referenceBindingSuperclass.superclass()) {
            MethodBinding[] methodBindingArrUnResolvedMethods = referenceBindingSuperclass.unResolvedMethods();
            int length = methodBindingArrUnResolvedMethods.length;
            while (true) {
                length--;
                if (length < 0) {
                    break;
                }
                MethodBinding methodBinding = methodBindingArrUnResolvedMethods[length];
                if (!methodBinding.isPrivate() && !methodBinding.isConstructor() && !methodBinding.isDefaultAbstract()) {
                    MethodBinding[] methodBindingArr4 = (MethodBinding[]) this.inheritedMethods.get(methodBinding.selector);
                    if (methodBindingArr4 != null) {
                        int length2 = methodBindingArr4.length;
                        int i = 0;
                        while (true) {
                            if (i < length2) {
                                MethodBinding methodBinding2 = methodBindingArr4[i];
                                if (TypeBinding.notEquals(methodBinding2.declaringClass, methodBinding.declaringClass) && areMethodsCompatible(methodBinding2, methodBinding) && !canOverridingMethodDifferInErasure(methodBinding2, methodBinding)) {
                                    if (methodBinding.isDefault()) {
                                        if (methodBinding.isAbstract()) {
                                            checkPackagePrivateAbstractMethod(methodBinding);
                                        } else if (methodBinding2.declaringClass.fPackage == methodBinding.declaringClass.fPackage || this.type.fPackage != methodBinding.declaringClass.fPackage || areReturnTypesCompatible(methodBinding, methodBinding2)) {
                                        }
                                    }
                                    if (TypeBinding.notEquals(methodBinding.returnType.erasure(), methodBinding2.returnType.erasure()) && areReturnTypesCompatible(methodBinding2, methodBinding)) {
                                        addBridgeMethodCandidate(methodBinding);
                                    }
                                }
                                i++;
                            }
                        }
                    }
                    if (!methodBinding.isDefault() || methodBinding.declaringClass.fPackage == this.type.fPackage) {
                        if (methodBindingArr4 == null) {
                            methodBindingArr2 = new MethodBinding[]{methodBinding};
                        } else {
                            int length3 = methodBindingArr4.length;
                            MethodBinding[] methodBindingArr5 = new MethodBinding[length3 + 1];
                            System.arraycopy(methodBindingArr4, 0, methodBindingArr5, 0, length3);
                            methodBindingArr5[length3] = methodBinding;
                            methodBindingArr2 = methodBindingArr5;
                        }
                        this.inheritedMethods.put(methodBinding.selector, methodBindingArr2);
                    } else {
                        MethodBinding[] methodBindingArr6 = (MethodBinding[]) hashtableOfObject.get(methodBinding.selector);
                        if (methodBindingArr6 != null && methodBinding.isAbstract()) {
                            int length4 = methodBindingArr6.length;
                            int i2 = 0;
                            while (true) {
                                if (i2 < length4) {
                                    if (!areMethodsCompatible(methodBindingArr6[i2], methodBinding)) {
                                        i2++;
                                    }
                                }
                            }
                        }
                        if (methodBindingArr6 == null) {
                            methodBindingArr3 = new MethodBinding[]{methodBinding};
                        } else {
                            int length5 = methodBindingArr6.length;
                            MethodBinding[] methodBindingArr7 = new MethodBinding[length5 + 1];
                            System.arraycopy(methodBindingArr6, 0, methodBindingArr7, 0, length5);
                            methodBindingArr7[length5] = methodBinding;
                            methodBindingArr3 = methodBindingArr7;
                        }
                        hashtableOfObject.put(methodBinding.selector, methodBindingArr3);
                        if (methodBinding.isAbstract() && !this.type.isAbstract()) {
                            problemReporter().abstractMethodCannotBeOverridden(this.type, methodBinding);
                        }
                        MethodBinding[] methodBindingArr8 = (MethodBinding[]) this.currentMethods.get(methodBinding.selector);
                        if (methodBindingArr8 != null && !methodBinding.isStatic()) {
                            int length6 = methodBindingArr8.length;
                            for (int i3 = 0; i3 < length6; i3++) {
                                if (!methodBindingArr8[i3].isStatic() && areMethodsCompatible(methodBindingArr8[i3], methodBinding)) {
                                    problemReporter().overridesPackageDefaultMethod(methodBindingArr8[i3], methodBinding);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        ArrayList arrayList = new ArrayList();
        HashSet hashSet = new HashSet();
        collectAllDistinctSuperInterfaces(referenceBindingArr, hashSet, arrayList);
        for (ReferenceBinding referenceBindingSuperclass2 = referenceBinding; referenceBindingSuperclass2 != null && referenceBindingSuperclass2.f626id != 1; referenceBindingSuperclass2 = referenceBindingSuperclass2.superclass()) {
            collectAllDistinctSuperInterfaces(referenceBindingSuperclass2.superInterfaces(), hashSet, arrayList);
        }
        if (arrayList.size() == 0) {
            return;
        }
        if (arrayList.size() == 1) {
            referenceBindingArrSortTypes = new ReferenceBinding[]{(ReferenceBinding) arrayList.get(0)};
        } else {
            referenceBindingArrSortTypes = Sorting.sortTypes((ReferenceBinding[]) arrayList.toArray(new ReferenceBinding[arrayList.size()]));
        }
        SimpleSet simpleSetFindSuperinterfaceCollisions = findSuperinterfaceCollisions(referenceBinding, referenceBindingArrSortTypes);
        for (int length7 = referenceBindingArrSortTypes.length - 1; length7 >= 0; length7--) {
            ReferenceBinding referenceBinding2 = referenceBindingArrSortTypes[length7];
            if (referenceBinding2.isValidBinding() && (simpleSetFindSuperinterfaceCollisions == null || !simpleSetFindSuperinterfaceCollisions.includes(referenceBinding2))) {
                MethodBinding[] methodBindingArrUnResolvedMethods2 = referenceBinding2.unResolvedMethods();
                int length8 = methodBindingArrUnResolvedMethods2.length;
                while (true) {
                    length8--;
                    if (length8 < 0) {
                        break;
                    }
                    MethodBinding methodBinding3 = methodBindingArrUnResolvedMethods2[length8];
                    if (!methodBinding3.isStatic()) {
                        MethodBinding[] methodBindingArr9 = (MethodBinding[]) this.inheritedMethods.get(methodBinding3.selector);
                        if (methodBindingArr9 == null) {
                            methodBindingArr = new MethodBinding[]{methodBinding3};
                        } else {
                            int length9 = methodBindingArr9.length;
                            int i4 = 0;
                            while (true) {
                                if (i4 < length9) {
                                    if (isInterfaceMethodImplemented(methodBinding3, methodBindingArr9[i4], referenceBinding2)) {
                                        if (TypeBinding.notEquals(methodBinding3.returnType.erasure(), methodBindingArr9[i4].returnType.erasure())) {
                                            addBridgeMethodCandidate(methodBinding3);
                                        }
                                        if (!canOverridingMethodDifferInErasure(methodBindingArr9[i4], methodBinding3)) {
                                            break;
                                        }
                                    }
                                    i4++;
                                } else {
                                    MethodBinding[] methodBindingArr10 = new MethodBinding[length9 + 1];
                                    System.arraycopy(methodBindingArr9, 0, methodBindingArr10, 0, length9);
                                    methodBindingArr10[length9] = methodBinding3;
                                    methodBindingArr = methodBindingArr10;
                                }
                            }
                        }
                        this.inheritedMethods.put(methodBinding3.selector, methodBindingArr);
                        break;
                    }
                }
            }
        }
    }

    void collectAllDistinctSuperInterfaces(ReferenceBinding[] referenceBindingArr, Set set, List list) {
        for (ReferenceBinding referenceBinding : referenceBindingArr) {
            if (set.add(referenceBinding)) {
                list.add(referenceBinding);
                collectAllDistinctSuperInterfaces(referenceBinding.superInterfaces(), set, list);
            }
        }
    }

    void computeMethods() throws Throwable {
        MethodBinding[] methodBindingArr;
        MethodBinding[] methodBindingArrMethods = this.type.methods();
        int length = methodBindingArrMethods.length;
        this.currentMethods = new HashtableOfObject(length == 0 ? 1 : length);
        while (true) {
            length--;
            if (length < 0) {
                return;
            }
            MethodBinding methodBinding = methodBindingArrMethods[length];
            if (!methodBinding.isConstructor() && !methodBinding.isDefaultAbstract()) {
                MethodBinding[] methodBindingArr2 = (MethodBinding[]) this.currentMethods.get(methodBinding.selector);
                if (methodBindingArr2 == null) {
                    methodBindingArr = new MethodBinding[1];
                } else {
                    int length2 = methodBindingArr2.length;
                    MethodBinding[] methodBindingArr3 = new MethodBinding[length2 + 1];
                    System.arraycopy(methodBindingArr2, 0, methodBindingArr3, 0, length2);
                    methodBindingArr = methodBindingArr3;
                }
                methodBindingArr[methodBindingArr.length - 1] = methodBinding;
                this.currentMethods.put(methodBinding.selector, methodBindingArr);
            }
        }
    }

    MethodBinding computeSubstituteMethod(MethodBinding methodBinding, MethodBinding methodBinding2) {
        return computeSubstituteMethod(methodBinding, methodBinding2, this.environment);
    }

    public static MethodBinding computeSubstituteMethod(MethodBinding methodBinding, MethodBinding methodBinding2, LookupEnvironment lookupEnvironment) {
        if (methodBinding == null || methodBinding2.parameters.length != methodBinding.parameters.length) {
            return null;
        }
        if (methodBinding2.declaringClass instanceof BinaryTypeBinding) {
            ((BinaryTypeBinding) methodBinding2.declaringClass).resolveTypesFor(methodBinding2);
        }
        if (methodBinding.declaringClass instanceof BinaryTypeBinding) {
            ((BinaryTypeBinding) methodBinding.declaringClass).resolveTypesFor(methodBinding);
        }
        TypeVariableBinding[] typeVariableBindingArr = methodBinding.typeVariables;
        int length = typeVariableBindingArr.length;
        if (length == 0) {
            return methodBinding;
        }
        TypeVariableBinding[] typeVariableBindingArr2 = methodBinding2.typeVariables;
        int length2 = typeVariableBindingArr2.length;
        if (length2 == 0) {
            return methodBinding.asRawMethod(lookupEnvironment);
        }
        if (length2 != length) {
            return methodBinding;
        }
        TypeBinding[] typeBindingArr = new TypeBinding[length2];
        System.arraycopy(typeVariableBindingArr2, 0, typeBindingArr, 0, length2);
        ParameterizedGenericMethodBinding parameterizedGenericMethodBindingCreateParameterizedGenericMethod = lookupEnvironment.createParameterizedGenericMethod(methodBinding, typeBindingArr);
        for (int i = 0; i < length; i++) {
            TypeVariableBinding typeVariableBinding = typeVariableBindingArr[i];
            TypeVariableBinding typeVariableBinding2 = (TypeVariableBinding) typeBindingArr[i];
            if (TypeBinding.equalsEquals(typeVariableBinding2.firstBound, typeVariableBinding.firstBound)) {
                if (typeVariableBinding2.firstBound == null) {
                    continue;
                }
            } else if (typeVariableBinding2.firstBound != null && typeVariableBinding.firstBound != null && typeVariableBinding2.firstBound.isClass() != typeVariableBinding.firstBound.isClass()) {
                return methodBinding;
            }
            if (TypeBinding.notEquals(Scope.substitute(parameterizedGenericMethodBindingCreateParameterizedGenericMethod, typeVariableBinding.superclass), typeVariableBinding2.superclass)) {
                return methodBinding;
            }
            int length3 = typeVariableBinding.superInterfaces.length;
            ReferenceBinding[] referenceBindingArr = typeVariableBinding2.superInterfaces;
            if (length3 != referenceBindingArr.length) {
                return methodBinding;
            }
            for (int i2 = 0; i2 < length3; i2++) {
                TypeBinding typeBindingSubstitute = Scope.substitute(parameterizedGenericMethodBindingCreateParameterizedGenericMethod, typeVariableBinding.superInterfaces[i2]);
                for (int i3 = 0; i3 < length3; i3++) {
                    if (TypeBinding.equalsEquals(typeBindingSubstitute, referenceBindingArr[i3])) {
                    }
                }
                return methodBinding;
            }
        }
        return parameterizedGenericMethodBindingCreateParameterizedGenericMethod;
    }

    static boolean couldMethodOverride(MethodBinding methodBinding, MethodBinding methodBinding2) {
        if (!CharOperation.equals(methodBinding.selector, methodBinding2.selector) || methodBinding == methodBinding2 || methodBinding.isStatic() || methodBinding2.isStatic() || methodBinding2.isPrivate()) {
            return false;
        }
        if (methodBinding2.isDefault() && methodBinding.declaringClass.getPackage() != methodBinding2.declaringClass.getPackage()) {
            return false;
        }
        if (methodBinding.isPublic()) {
            return true;
        }
        if (methodBinding2.isPublic()) {
            return false;
        }
        return !methodBinding2.isProtected() || methodBinding.isProtected();
    }

    public boolean doesMethodOverride(MethodBinding methodBinding, MethodBinding methodBinding2) {
        return doesMethodOverride(methodBinding, methodBinding2, this.environment);
    }

    public static boolean doesMethodOverride(MethodBinding methodBinding, MethodBinding methodBinding2, LookupEnvironment lookupEnvironment) {
        return couldMethodOverride(methodBinding, methodBinding2) && areMethodsCompatible(methodBinding, methodBinding2, lookupEnvironment);
    }

    MethodBinding findBestInheritedAbstractOrDefaultMethod(MethodBinding[] methodBindingArr, int i) {
        for (int i2 = 0; i2 < i; i2++) {
            MethodBinding methodBinding = methodBindingArr[i2];
            if (methodBinding.isAbstract() || methodBinding.isDefaultMethod()) {
                for (int i3 = 0; i3 < i; i3++) {
                    if (i2 != i3 && !checkInheritedReturnTypes(methodBinding, methodBindingArr[i3])) {
                        if (this.type.isInterface() && methodBindingArr[i3].declaringClass.f626id == 1) {
                            return methodBinding;
                        }
                    }
                }
                return methodBinding;
            }
        }
        return null;
    }

    int[] findOverriddenInheritedMethods(MethodBinding[] methodBindingArr, int i) {
        int i2 = 0;
        ReferenceBinding referenceBinding = methodBindingArr[0].declaringClass;
        int[] iArr = null;
        if (!referenceBinding.isInterface()) {
            ReferenceBinding referenceBinding2 = methodBindingArr[1].declaringClass;
            int i3 = 1;
            while (TypeBinding.equalsEquals(referenceBinding, referenceBinding2)) {
                i3++;
                if (i3 == i) {
                    return null;
                }
                referenceBinding2 = methodBindingArr[i3].declaringClass;
            }
            if (!referenceBinding2.isInterface()) {
                if (referenceBinding.fPackage != referenceBinding2.fPackage && methodBindingArr[i3].isDefault()) {
                    return null;
                }
                int[] iArr2 = new int[i];
                do {
                    iArr2[i3] = -1;
                    i3++;
                    if (i3 == i) {
                        return iArr2;
                    }
                } while (!methodBindingArr[i3].declaringClass.isInterface());
                iArr = iArr2;
            }
            i2 = i3;
        }
        while (i2 < i) {
            if (iArr == null || iArr[i2] != -1) {
                ReferenceBinding referenceBinding3 = methodBindingArr[i2].declaringClass;
                for (int i4 = i2 + 1; i4 < i; i4++) {
                    if (iArr == null || iArr[i4] != -1) {
                        ReferenceBinding referenceBinding4 = methodBindingArr[i4].declaringClass;
                        if (!TypeBinding.equalsEquals(referenceBinding3, referenceBinding4)) {
                            if (referenceBinding3.implementsInterface(referenceBinding4, true)) {
                                if (iArr == null) {
                                    iArr = new int[i];
                                }
                                iArr[i4] = -1;
                            } else if (referenceBinding4.implementsInterface(referenceBinding3, true)) {
                                if (iArr == null) {
                                    iArr = new int[i];
                                }
                                iArr[i2] = -1;
                                break;
                            }
                        } else {
                            continue;
                        }
                    }
                }
            }
            i2++;
        }
        return iArr;
    }

    boolean isAsVisible(MethodBinding methodBinding, MethodBinding methodBinding2) {
        if (methodBinding2.modifiers == methodBinding.modifiers || methodBinding.isPublic()) {
            return true;
        }
        if (methodBinding2.isPublic()) {
            return false;
        }
        if (methodBinding.isProtected()) {
            return true;
        }
        if (methodBinding2.isProtected()) {
            return false;
        }
        return !methodBinding.isPrivate();
    }

    boolean isInterfaceMethodImplemented(MethodBinding methodBinding, MethodBinding methodBinding2, ReferenceBinding referenceBinding) {
        return areParametersEqual(methodBinding2, methodBinding) && methodBinding2.declaringClass.implementsInterface(referenceBinding, true);
    }

    public boolean isMethodSubsignature(MethodBinding methodBinding, MethodBinding methodBinding2) {
        return CharOperation.equals(methodBinding.selector, methodBinding2.selector) && isParameterSubsignature(methodBinding, methodBinding2);
    }

    boolean isParameterSubsignature(MethodBinding methodBinding, MethodBinding methodBinding2) {
        return isParameterSubsignature(methodBinding, methodBinding2, this.environment);
    }

    static boolean isParameterSubsignature(MethodBinding methodBinding, MethodBinding methodBinding2, LookupEnvironment lookupEnvironment) {
        MethodBinding methodBindingComputeSubstituteMethod = computeSubstituteMethod(methodBinding2, methodBinding, lookupEnvironment);
        return methodBindingComputeSubstituteMethod != null && isSubstituteParameterSubsignature(methodBinding, methodBindingComputeSubstituteMethod, lookupEnvironment);
    }

    boolean isSubstituteParameterSubsignature(MethodBinding methodBinding, MethodBinding methodBinding2) {
        return isSubstituteParameterSubsignature(methodBinding, methodBinding2, this.environment);
    }

    public static boolean isSubstituteParameterSubsignature(MethodBinding methodBinding, MethodBinding methodBinding2, LookupEnvironment lookupEnvironment) {
        if (!areParametersEqual(methodBinding, methodBinding2)) {
            if (methodBinding2.hasSubstitutedParameters() && methodBinding.areParameterErasuresEqual(methodBinding2)) {
                return methodBinding.typeVariables == Binding.NO_TYPE_VARIABLES && !hasGenericParameter(methodBinding);
            }
            if (methodBinding.declaringClass.isRawType() && methodBinding2.declaringClass.isRawType() && methodBinding.hasSubstitutedParameters() && methodBinding2.hasSubstitutedParameters()) {
                return areMethodsCompatible(methodBinding, methodBinding2, lookupEnvironment);
            }
            return false;
        }
        if (!(methodBinding2 instanceof ParameterizedGenericMethodBinding)) {
            return methodBinding.typeVariables == Binding.NO_TYPE_VARIABLES;
        }
        if (methodBinding.typeVariables != Binding.NO_TYPE_VARIABLES) {
            return !((ParameterizedGenericMethodBinding) methodBinding2).isRaw;
        }
        return !hasGenericParameter(methodBinding);
    }

    static boolean hasGenericParameter(MethodBinding methodBinding) {
        if (methodBinding.genericSignature() == null) {
            return false;
        }
        for (TypeBinding typeBinding : methodBinding.parameters) {
            TypeBinding typeBindingLeafComponentType = typeBinding.leafComponentType();
            if ((typeBindingLeafComponentType instanceof ReferenceBinding) && (((ReferenceBinding) typeBindingLeafComponentType).modifiers & 1073741824) != 0) {
                return true;
            }
        }
        return false;
    }

    boolean isSameClassOrSubclassOf(ReferenceBinding referenceBinding, ReferenceBinding referenceBinding2) {
        while (!TypeBinding.equalsEquals(referenceBinding, referenceBinding2)) {
            referenceBinding = referenceBinding.superclass();
            if (referenceBinding == null) {
                return false;
            }
        }
        return true;
    }

    boolean mustImplementAbstractMethod(ReferenceBinding referenceBinding) {
        if (!mustImplementAbstractMethods()) {
            return false;
        }
        ReferenceBinding referenceBindingSuperclass = this.type.superclass();
        if (referenceBinding.isClass()) {
            while (referenceBindingSuperclass.isAbstract() && TypeBinding.notEquals(referenceBindingSuperclass, referenceBinding)) {
                referenceBindingSuperclass = referenceBindingSuperclass.superclass();
            }
        } else {
            if (this.type.implementsInterface(referenceBinding, false) && !referenceBindingSuperclass.implementsInterface(referenceBinding, true)) {
                return true;
            }
            while (referenceBindingSuperclass.isAbstract() && !referenceBindingSuperclass.implementsInterface(referenceBinding, false)) {
                referenceBindingSuperclass = referenceBindingSuperclass.superclass();
            }
        }
        return referenceBindingSuperclass.isAbstract();
    }

    boolean mustImplementAbstractMethods() {
        return (this.type.isInterface() || this.type.isAbstract()) ? false : true;
    }

    ProblemReporter problemReporter() {
        return this.type.scope.problemReporter();
    }

    ProblemReporter problemReporter(MethodBinding methodBinding) {
        ProblemReporter problemReporter = problemReporter();
        if (TypeBinding.equalsEquals(methodBinding.declaringClass, this.type) && methodBinding.sourceMethod() != null) {
            problemReporter.referenceContext = methodBinding.sourceMethod();
        }
        return problemReporter;
    }

    boolean reportIncompatibleReturnTypeError(MethodBinding methodBinding, MethodBinding methodBinding2) {
        problemReporter(methodBinding).incompatibleReturnType(methodBinding, methodBinding2);
        return true;
    }

    ReferenceBinding[] resolvedExceptionTypesFor(MethodBinding methodBinding) {
        ReferenceBinding[] referenceBindingArr = methodBinding.thrownExceptions;
        if ((methodBinding.modifiers & 33554432) == 0) {
            return referenceBindingArr;
        }
        if (!(methodBinding.declaringClass instanceof BinaryTypeBinding)) {
            return Binding.NO_EXCEPTIONS;
        }
        int length = referenceBindingArr.length;
        while (true) {
            length--;
            if (length < 0) {
                return referenceBindingArr;
            }
            referenceBindingArr[length] = (ReferenceBinding) BinaryTypeBinding.resolveType(referenceBindingArr[length], this.environment, true);
        }
    }

    void verify() throws Throwable {
        computeMethods();
        computeInheritedMethods();
        checkMethods();
        if (this.type.isClass()) {
            checkForMissingHashCodeMethod();
        }
    }

    void verify(SourceTypeBinding sourceTypeBinding) {
        if (this.type == null) {
            try {
                this.type = sourceTypeBinding;
                verify();
                return;
            } finally {
                this.type = null;
            }
        }
        this.environment.newMethodVerifier().verify(sourceTypeBinding);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer(10);
        stringBuffer.append("MethodVerifier for type: ");
        stringBuffer.append(this.type.readableName());
        stringBuffer.append("\n\t-inherited methods: ");
        stringBuffer.append(this.inheritedMethods);
        return stringBuffer.toString();
    }
}
