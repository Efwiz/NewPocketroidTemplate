package org.eclipse.jdt.internal.compiler.lookup;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.eclipse.jdt.internal.compiler.ast.ASTNode;
import org.eclipse.jdt.internal.compiler.ast.AbstractMethodDeclaration;
import org.eclipse.jdt.internal.compiler.ast.Argument;
import org.eclipse.jdt.internal.compiler.ast.MethodDeclaration;
import org.eclipse.jdt.internal.compiler.ast.NullAnnotationMatching;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;

/* JADX INFO: loaded from: classes14.dex */
public class ImplicitNullAnnotationVerifier {
    ImplicitNullAnnotationVerifier buddyImplicitNullAnnotationsVerifier;
    protected LookupEnvironment environment;
    private boolean inheritNullAnnotations;

    static class InheritedNonNullnessInfo {
        MethodBinding annotationOrigin;
        boolean complained;
        Boolean inheritedNonNullness;

        InheritedNonNullnessInfo() {
        }
    }

    public ImplicitNullAnnotationVerifier(LookupEnvironment lookupEnvironment, boolean z) {
        this.buddyImplicitNullAnnotationsVerifier = this;
        this.inheritNullAnnotations = z;
        this.environment = lookupEnvironment;
    }

    ImplicitNullAnnotationVerifier(LookupEnvironment lookupEnvironment) {
        CompilerOptions compilerOptions = lookupEnvironment.globalOptions;
        this.buddyImplicitNullAnnotationsVerifier = new ImplicitNullAnnotationVerifier(lookupEnvironment, compilerOptions.inheritNullAnnotations);
        this.inheritNullAnnotations = compilerOptions.inheritNullAnnotations;
        this.environment = lookupEnvironment;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r10v13 */
    /* JADX WARN: Type inference failed for: r10v14 */
    /* JADX WARN: Type inference failed for: r10v2, types: [boolean] */
    /* JADX WARN: Type inference failed for: r10v3, types: [int] */
    /* JADX WARN: Type inference failed for: r10v4 */
    /* JADX WARN: Type inference failed for: r25v0, types: [org.eclipse.jdt.internal.compiler.lookup.ImplicitNullAnnotationVerifier] */
    /* JADX WARN: Type inference failed for: r3v2 */
    /* JADX WARN: Type inference failed for: r4v4 */
    public void checkImplicitNullAnnotations(MethodBinding methodBinding, AbstractMethodDeclaration abstractMethodDeclaration, boolean z, Scope scope) {
        boolean z2;
        boolean z3;
        Argument argument;
        Argument argument2;
        long j;
        try {
            ReferenceBinding referenceBinding = methodBinding.declaringClass;
            boolean z4 = true;
            if (referenceBinding.f626id != 1) {
                boolean zUsesNullTypeAnnotations = scope.environment().usesNullTypeAnnotations();
                boolean zHasNonNullDefaultFor = methodBinding.hasNonNullDefaultFor(16, zUsesNullTypeAnnotations);
                boolean zHasNonNullDefaultFor2 = methodBinding.hasNonNullDefaultFor(8, zUsesNullTypeAnnotations);
                z2 = (zHasNonNullDefaultFor ? 1 : 0) | (zHasNonNullDefaultFor2 ? 1 : 0);
                boolean z5 = false;
                if (methodBinding.isConstructor() || methodBinding.isStatic()) {
                    z4 = false;
                }
                boolean z6 = z & z4;
                if (z2 || z6 || (this.inheritNullAnnotations && z4)) {
                    if (z4) {
                        ArrayList arrayList = new ArrayList();
                        if ((referenceBinding instanceof SourceTypeBinding) && !referenceBinding.isHierarchyConnected() && !referenceBinding.isAnonymousType()) {
                            ((SourceTypeBinding) referenceBinding).scope.connectTypeHierarchy();
                        }
                        int length = methodBinding.parameters.length;
                        MethodBinding methodBindingOriginal = methodBinding.original();
                        char[] cArr = methodBinding.selector;
                        HashSet hashSet = new HashSet();
                        boolean z7 = z2 ? 1 : 0;
                        int i = length;
                        findAllOverriddenMethods(methodBindingOriginal, cArr, length, referenceBinding, hashSet, arrayList);
                        int i2 = i + 1;
                        InheritedNonNullnessInfo[] inheritedNonNullnessInfoArr = new InheritedNonNullnessInfo[i2];
                        int i3 = 0;
                        while (i3 < i2) {
                            InheritedNonNullnessInfo[] inheritedNonNullnessInfoArr2 = inheritedNonNullnessInfoArr;
                            boolean z8 = z5 ? 1 : 0;
                            inheritedNonNullnessInfoArr2[i3] = new InheritedNonNullnessInfo();
                            i3++;
                            inheritedNonNullnessInfoArr = inheritedNonNullnessInfoArr2;
                            i = i;
                            arrayList = arrayList;
                            z5 = z8 ? 1 : 0;
                        }
                        int size = arrayList.size();
                        ?? r10 = z5;
                        while (true) {
                            int i4 = size - 1;
                            argument = null;
                            if (i4 < 0) {
                                break;
                            }
                            InheritedNonNullnessInfo[] inheritedNonNullnessInfoArr3 = inheritedNonNullnessInfoArr;
                            MethodBinding methodBinding2 = (MethodBinding) arrayList.get(i4);
                            if ((methodBinding2.tagBits & 4096) == 0) {
                                checkImplicitNullAnnotations(methodBinding2, null, r10, scope);
                            }
                            boolean z9 = r10 == true ? 1 : 0;
                            checkNullSpecInheritance(methodBinding, abstractMethodDeclaration, zHasNonNullDefaultFor, zHasNonNullDefaultFor2, z6, methodBinding2, null, scope, inheritedNonNullnessInfoArr3);
                            inheritedNonNullnessInfoArr = inheritedNonNullnessInfoArr3;
                            i = i;
                            size = i4;
                            arrayList = arrayList;
                            boolean z10 = z9 ? 1 : 0;
                            z7 = z10 ? 1 : 0;
                            r10 = z10;
                        }
                        InheritedNonNullnessInfo inheritedNonNullnessInfo = inheritedNonNullnessInfoArr[r10 == true ? 1 : 0];
                        if (!inheritedNonNullnessInfo.complained) {
                            if (inheritedNonNullnessInfo.inheritedNonNullness == Boolean.TRUE) {
                                j = 72057594037927936L;
                            } else {
                                j = inheritedNonNullnessInfo.inheritedNonNullness == Boolean.FALSE ? 36028797018963968L : 0L;
                            }
                            if (j != 0) {
                                if (!zUsesNullTypeAnnotations) {
                                    methodBinding.tagBits |= j;
                                } else if (!methodBinding.returnType.isBaseType()) {
                                    LookupEnvironment lookupEnvironmentEnvironment = scope.environment();
                                    methodBinding.returnType = lookupEnvironmentEnvironment.createAnnotatedType(methodBinding.returnType, lookupEnvironmentEnvironment.nullAnnotationsFromTagBits(j));
                                }
                            }
                        }
                        while (r10 < i) {
                            int i5 = r10 + 1;
                            InheritedNonNullnessInfo inheritedNonNullnessInfo2 = inheritedNonNullnessInfoArr[i5];
                            if (inheritedNonNullnessInfo2.complained || inheritedNonNullnessInfo2.inheritedNonNullness == null) {
                                argument2 = argument;
                            } else {
                                Argument argument3 = abstractMethodDeclaration == null ? argument : abstractMethodDeclaration.arguments[r10];
                                if (!zUsesNullTypeAnnotations) {
                                    recordArgNonNullness(methodBinding, i, r10 == true ? 1 : 0, argument3, inheritedNonNullnessInfo2.inheritedNonNullness);
                                    argument2 = argument;
                                } else {
                                    Argument argument4 = argument3;
                                    argument2 = argument;
                                    recordArgNonNullness18(methodBinding, r10 == true ? 1 : 0, argument4, inheritedNonNullnessInfo2.inheritedNonNullness, scope.environment());
                                }
                            }
                            r10 = i5;
                            argument = argument2;
                            inheritedNonNullnessInfoArr = inheritedNonNullnessInfoArr;
                        }
                        z3 = z7;
                    }
                    if (z3) {
                        if (!zUsesNullTypeAnnotations) {
                            methodBinding.fillInDefaultNonNullness(abstractMethodDeclaration);
                        } else {
                            methodBinding.fillInDefaultNonNullness18(abstractMethodDeclaration, scope.environment());
                        }
                    }
                }
            }
            z3 = z2;
            methodBinding.tagBits |= 4096;
        } catch (Throwable th) {
            methodBinding.tagBits |= 4096;
            throw th;
        }
    }

    private void findAllOverriddenMethods(MethodBinding methodBinding, char[] cArr, int i, ReferenceBinding referenceBinding, Set set, List list) {
        ReferenceBinding referenceBindingSuperclass;
        if (referenceBinding.f626id == 1 || (referenceBindingSuperclass = referenceBinding.superclass()) == null) {
            return;
        }
        collectOverriddenMethods(methodBinding, cArr, i, referenceBindingSuperclass, set, list);
        for (ReferenceBinding referenceBinding2 : referenceBinding.superInterfaces()) {
            if (set.add(referenceBinding2.original())) {
                collectOverriddenMethods(methodBinding, cArr, i, referenceBinding2, set, list);
            }
        }
    }

    private void collectOverriddenMethods(MethodBinding methodBinding, char[] cArr, int i, ReferenceBinding referenceBinding, Set set, List list) {
        boolean z = false;
        for (MethodBinding methodBinding2 : referenceBinding.getMethods(cArr, i)) {
            if (!methodBinding2.isStatic() && MethodVerifier.doesMethodOverride(methodBinding, methodBinding2, this.environment)) {
                list.add(methodBinding2);
                z = true;
            }
        }
        if (z) {
            return;
        }
        findAllOverriddenMethods(methodBinding, cArr, i, referenceBinding, set, list);
    }

    /* JADX WARN: Code duplicated, block: B:167:0x02f0 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:168:0x02f2  */
    /* JADX WARN: Code duplicated, block: B:170:0x02f8  */
    /* JADX WARN: Code duplicated, block: B:171:0x02fd  */
    /* JADX WARN: Code duplicated, block: B:174:0x0317 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:175:0x0319  */
    /* JADX WARN: Code duplicated, block: B:176:0x0323  */
    /* JADX WARN: Code duplicated, block: B:177:0x032c  */
    /* JADX WARN: Code duplicated, block: B:35:0x009a  */
    /* JADX WARN: Code duplicated, block: B:43:0x00bb  */
    /* JADX WARN: Code duplicated, block: B:44:0x00bd  */
    /* JADX WARN: Code duplicated, block: B:46:0x00c5  */
    /* JADX WARN: Code duplicated, block: B:49:0x00d7  */
    /* JADX WARN: Code duplicated, block: B:52:0x00f3 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:53:0x00f5  */
    /* JADX WARN: Code duplicated, block: B:54:0x0103  */
    void checkNullSpecInheritance(MethodBinding methodBinding, AbstractMethodDeclaration abstractMethodDeclaration, boolean z, boolean z2, boolean z3, MethodBinding methodBinding2, MethodBinding[] methodBindingArr, Scope scope, InheritedNonNullnessInfo[] inheritedNonNullnessInfoArr) {
        Boolean bool;
        Argument argument;
        Argument[] argumentArr;
        int i;
        char[][] nullableAnnotationName;
        TypeBinding typeBinding;
        TypeBinding typeBinding2;
        int i2;
        TypeVariableBinding[] typeVariableBindingArr;
        TypeVariableBinding[] typeVariableBindingArr2;
        TypeBinding typeBinding3;
        AbstractMethodDeclaration abstractMethodDeclaration2 = abstractMethodDeclaration;
        if ((methodBinding2.tagBits & 4096) == 0) {
            this.buddyImplicitNullAnnotationsVerifier.checkImplicitNullAnnotations(methodBinding2, null, false, scope);
        }
        boolean zUsesNullTypeAnnotations = this.environment.usesNullTypeAnnotations();
        long returnTypeNullnessTagBits = getReturnTypeNullnessTagBits(methodBinding2, zUsesNullTypeAnnotations);
        long returnTypeNullnessTagBits2 = getReturnTypeNullnessTagBits(methodBinding, zUsesNullTypeAnnotations);
        boolean z4 = this.inheritNullAnnotations;
        if (methodBinding.returnType != null && !methodBinding.returnType.isBaseType()) {
            if (returnTypeNullnessTagBits2 != 0) {
                if (z3) {
                    if ((returnTypeNullnessTagBits & 72057594037927936L) != 0 || returnTypeNullnessTagBits2 == 72057594037927936L) {
                        if (zUsesNullTypeAnnotations) {
                            typeVariableBindingArr2 = methodBinding2.original().typeVariables;
                            if (typeVariableBindingArr2 != null || methodBinding.returnType.f626id == 6) {
                                typeBinding3 = null;
                            } else {
                                typeBinding3 = this.environment.createParameterizedGenericMethod(methodBinding, typeVariableBindingArr2).returnType;
                            }
                            if (NullAnnotationMatching.analyse(methodBinding2.returnType, methodBinding.returnType, typeBinding3, null, 0, null, NullAnnotationMatching.CheckMode.OVERRIDE_RETURN).isAnyMismatch()) {
                                if (abstractMethodDeclaration2 != null) {
                                    scope.problemReporter().illegalReturnRedefinition(abstractMethodDeclaration2, methodBinding2, this.environment.getNonNullAnnotationName());
                                    return;
                                } else {
                                    scope.problemReporter().cannotImplementIncompatibleNullness(methodBinding, methodBinding2, zUsesNullTypeAnnotations);
                                    return;
                                }
                            }
                        }
                    } else if (abstractMethodDeclaration2 != null) {
                        scope.problemReporter().illegalReturnRedefinition(abstractMethodDeclaration2, methodBinding2, this.environment.getNonNullAnnotationName());
                    } else {
                        scope.problemReporter().cannotImplementIncompatibleNullness(methodBinding, methodBinding2, zUsesNullTypeAnnotations);
                        return;
                    }
                }
            } else if (!z4 || returnTypeNullnessTagBits == 0) {
                if (z && (!zUsesNullTypeAnnotations || methodBinding.returnType.acceptsNonNullDefault())) {
                    applyReturnNullBits(methodBinding, 72057594037927936L);
                    returnTypeNullnessTagBits2 = 72057594037927936L;
                }
                if (z3) {
                    if ((returnTypeNullnessTagBits & 72057594037927936L) != 0) {
                        if (zUsesNullTypeAnnotations) {
                            typeVariableBindingArr2 = methodBinding2.original().typeVariables;
                            if (typeVariableBindingArr2 != null) {
                                typeBinding3 = null;
                            } else {
                                typeBinding3 = null;
                            }
                            if (NullAnnotationMatching.analyse(methodBinding2.returnType, methodBinding.returnType, typeBinding3, null, 0, null, NullAnnotationMatching.CheckMode.OVERRIDE_RETURN).isAnyMismatch()) {
                                if (abstractMethodDeclaration2 != null) {
                                    scope.problemReporter().illegalReturnRedefinition(abstractMethodDeclaration2, methodBinding2, this.environment.getNonNullAnnotationName());
                                    return;
                                } else {
                                    scope.problemReporter().cannotImplementIncompatibleNullness(methodBinding, methodBinding2, zUsesNullTypeAnnotations);
                                    return;
                                }
                            }
                        }
                    } else if (zUsesNullTypeAnnotations) {
                        typeVariableBindingArr2 = methodBinding2.original().typeVariables;
                        if (typeVariableBindingArr2 != null) {
                            typeBinding3 = null;
                        } else {
                            typeBinding3 = null;
                        }
                        if (NullAnnotationMatching.analyse(methodBinding2.returnType, methodBinding.returnType, typeBinding3, null, 0, null, NullAnnotationMatching.CheckMode.OVERRIDE_RETURN).isAnyMismatch()) {
                            if (abstractMethodDeclaration2 != null) {
                                scope.problemReporter().illegalReturnRedefinition(abstractMethodDeclaration2, methodBinding2, this.environment.getNonNullAnnotationName());
                                return;
                            } else {
                                scope.problemReporter().cannotImplementIncompatibleNullness(methodBinding, methodBinding2, zUsesNullTypeAnnotations);
                                return;
                            }
                        }
                    }
                }
            } else {
                if (z && z3 && returnTypeNullnessTagBits == 36028797018963968L) {
                    scope.problemReporter().conflictingNullAnnotations(methodBinding, ((MethodDeclaration) abstractMethodDeclaration2).returnType, methodBinding2);
                }
                if (inheritedNonNullnessInfoArr != null && abstractMethodDeclaration2 != null) {
                    recordDeferredInheritedNullness(scope, ((MethodDeclaration) abstractMethodDeclaration2).returnType, methodBinding2, Boolean.valueOf(returnTypeNullnessTagBits == 72057594037927936L), inheritedNonNullnessInfoArr[0]);
                } else {
                    applyReturnNullBits(methodBinding, returnTypeNullnessTagBits);
                }
            }
        }
        TypeBinding[] typeBindingArr = (!z3 || (typeVariableBindingArr = methodBinding.original().typeVariables) == Binding.NO_TYPE_VARIABLES) ? null : this.environment.createParameterizedGenericMethod(methodBinding2, typeVariableBindingArr).parameters;
        Argument[] argumentArr2 = abstractMethodDeclaration2 == null ? null : abstractMethodDeclaration2.arguments;
        int length = argumentArr2 != null ? argumentArr2.length : 0;
        if (zUsesNullTypeAnnotations) {
            length = methodBinding.parameters.length;
        } else if (methodBinding2.parameterNonNullness != null) {
            length = methodBinding2.parameterNonNullness.length;
        } else if (methodBinding.parameterNonNullness != null) {
            length = methodBinding.parameterNonNullness.length;
        }
        int i3 = length;
        int i4 = 0;
        while (i4 < i3) {
            if (methodBinding.parameters[i4].isBaseType()) {
                argumentArr = argumentArr2;
                i = i4;
            } else {
                Argument argument2 = argumentArr2 == null ? null : argumentArr2[i4];
                Boolean parameterNonNullness = getParameterNonNullness(methodBinding2, i4, zUsesNullTypeAnnotations);
                Boolean parameterNonNullness2 = getParameterNonNullness(methodBinding, i4, zUsesNullTypeAnnotations);
                if (parameterNonNullness2 != null) {
                    bool = parameterNonNullness;
                    argument = argument2;
                    argumentArr = argumentArr2;
                    i = i4;
                } else if (parameterNonNullness == null || !z4) {
                    int i5 = i4;
                    if (z2) {
                        Boolean bool2 = Boolean.TRUE;
                        if (!zUsesNullTypeAnnotations) {
                            argument = argument2;
                            bool = parameterNonNullness;
                            recordArgNonNullness(methodBinding, i3, i5, argument2, Boolean.TRUE);
                            Argument[] argumentArr3 = argumentArr2;
                            i = i5;
                            argumentArr = argumentArr3;
                        } else {
                            argument = argument2;
                            bool = parameterNonNullness;
                            if (methodBinding.parameters[i5].acceptsNonNullDefault()) {
                                argumentArr = argumentArr2;
                                i = i5;
                                recordArgNonNullness18(methodBinding, i5, argument, Boolean.TRUE, this.environment);
                            } else {
                                argumentArr = argumentArr2;
                                i = i5;
                                parameterNonNullness2 = null;
                            }
                        }
                        parameterNonNullness2 = bool2;
                    } else {
                        argument = argument2;
                        bool = parameterNonNullness;
                        Argument[] argumentArr4 = argumentArr2;
                        i = i5;
                        argumentArr = argumentArr4;
                    }
                } else {
                    if (z2 && z3 && parameterNonNullness == Boolean.FALSE && argument2 != null) {
                        scope.problemReporter().conflictingNullAnnotations(methodBinding, argument2, methodBinding2);
                    }
                    if (inheritedNonNullnessInfoArr == null || abstractMethodDeclaration2 == null) {
                        i2 = i4;
                        if (!zUsesNullTypeAnnotations) {
                            recordArgNonNullness(methodBinding, i3, i2, argument2, parameterNonNullness);
                        } else {
                            recordArgNonNullness18(methodBinding, i2, argument2, parameterNonNullness, this.environment);
                        }
                    } else {
                        i2 = i4;
                        recordDeferredInheritedNullness(scope, abstractMethodDeclaration2.arguments[i4].type, methodBinding2, parameterNonNullness, inheritedNonNullnessInfoArr[i4 + 1]);
                    }
                    Argument[] argumentArr5 = argumentArr2;
                    i = i2;
                    argumentArr = argumentArr5;
                }
                if (z3) {
                    if (bool == Boolean.TRUE) {
                        nullableAnnotationName = this.environment.getNonNullAnnotationName();
                    } else {
                        nullableAnnotationName = this.environment.getNullableAnnotationName();
                    }
                    if (bool == Boolean.TRUE || parameterNonNullness2 != Boolean.TRUE) {
                        Argument argument3 = argument;
                        if (parameterNonNullness2 != null) {
                            if (zUsesNullTypeAnnotations) {
                                typeBinding = methodBinding2.parameters[i];
                                if (typeBindingArr != null) {
                                    typeBinding2 = typeBindingArr[i];
                                } else {
                                    typeBinding2 = null;
                                }
                                if (NullAnnotationMatching.analyse(methodBinding.parameters[i], typeBinding, typeBinding2, null, 0, null, NullAnnotationMatching.CheckMode.OVERRIDE).isAnyMismatch()) {
                                    if (argument3 != null) {
                                        scope.problemReporter().illegalParameterRedefinition(argument3, methodBinding2.declaringClass, typeBinding);
                                    } else {
                                        scope.problemReporter().cannotImplementIncompatibleNullness(methodBinding, methodBinding2, false);
                                    }
                                }
                            }
                        } else if (bool == Boolean.FALSE) {
                            if (argument3 != null) {
                                scope.problemReporter().parameterLackingNullableAnnotation(argument3, methodBinding2.declaringClass, nullableAnnotationName);
                            } else {
                                scope.problemReporter().cannotImplementIncompatibleNullness(methodBinding, methodBinding2, false);
                            }
                        } else if (bool == Boolean.TRUE) {
                            if (methodBindingArr != null) {
                                int length2 = methodBindingArr.length;
                                int i6 = 0;
                                while (true) {
                                    if (i6 < length2) {
                                        MethodBinding methodBinding3 = methodBindingArr[i6];
                                        if (TypeBinding.equalsEquals(methodBinding2.declaringClass, methodBinding3.declaringClass) && getParameterNonNullness(methodBinding3, i, zUsesNullTypeAnnotations) != Boolean.TRUE) {
                                            break;
                                        } else {
                                            i6++;
                                        }
                                    } else {
                                        scope.problemReporter().parameterLackingNonnullAnnotation(argument3, methodBinding2.declaringClass, nullableAnnotationName);
                                        break;
                                    }
                                }
                            } else {
                                scope.problemReporter().parameterLackingNonnullAnnotation(argument3, methodBinding2.declaringClass, nullableAnnotationName);
                                break;
                            }
                        } else if (zUsesNullTypeAnnotations) {
                            typeBinding = methodBinding2.parameters[i];
                            if (typeBindingArr != null) {
                                typeBinding2 = typeBindingArr[i];
                            } else {
                                typeBinding2 = null;
                            }
                            if (NullAnnotationMatching.analyse(methodBinding.parameters[i], typeBinding, typeBinding2, null, 0, null, NullAnnotationMatching.CheckMode.OVERRIDE).isAnyMismatch()) {
                                break;
                            } else if (argument3 != null) {
                                scope.problemReporter().illegalParameterRedefinition(argument3, methodBinding2.declaringClass, typeBinding);
                            } else {
                                scope.problemReporter().cannotImplementIncompatibleNullness(methodBinding, methodBinding2, false);
                            }
                        }
                    } else {
                        Argument argument4 = argument;
                        if (argument4 != null) {
                            scope.problemReporter().illegalRedefinitionToNonNullParameter(argument4, methodBinding2.declaringClass, bool == null ? null : this.environment.getNullableAnnotationName());
                        } else {
                            scope.problemReporter().cannotImplementIncompatibleNullness(methodBinding, methodBinding2, false);
                        }
                    }
                }
            }
            i4 = i + 1;
            abstractMethodDeclaration2 = abstractMethodDeclaration;
            argumentArr2 = argumentArr;
        }
        if (z3 && zUsesNullTypeAnnotations && abstractMethodDeclaration2 != null) {
            TypeVariableBinding[] typeVariableBindingArrTypeVariables = methodBinding.typeVariables();
            TypeVariableBinding[] typeVariableBindingArrTypeVariables2 = methodBinding2.typeVariables();
            if (typeVariableBindingArrTypeVariables == Binding.NO_TYPE_VARIABLES || typeVariableBindingArrTypeVariables.length != typeVariableBindingArrTypeVariables2.length) {
                return;
            }
            for (int i7 = 0; i7 < typeVariableBindingArrTypeVariables.length; i7++) {
                TypeVariableBinding typeVariableBinding = typeVariableBindingArrTypeVariables2[i7];
                if (NullAnnotationMatching.analyse(typeVariableBinding, typeVariableBindingArrTypeVariables[i7], null, null, -1, null, NullAnnotationMatching.CheckMode.BOUND_CHECK).isAnyMismatch()) {
                    scope.problemReporter().cannotRedefineTypeArgumentNullity(typeVariableBinding, methodBinding2, abstractMethodDeclaration.typeParameters()[i7]);
                }
            }
        }
    }

    void applyReturnNullBits(MethodBinding methodBinding, long j) {
        if (this.environment.usesNullTypeAnnotations()) {
            if (methodBinding.returnType.isBaseType()) {
                return;
            }
            methodBinding.returnType = this.environment.createAnnotatedType(methodBinding.returnType, this.environment.nullAnnotationsFromTagBits(j));
            return;
        }
        methodBinding.tagBits = j | methodBinding.tagBits;
    }

    private Boolean getParameterNonNullness(MethodBinding methodBinding, int i, boolean z) {
        if (z) {
            TypeBinding typeBinding = methodBinding.parameters[i];
            if (typeBinding != null) {
                long jValidNullTagBits = NullAnnotationMatching.validNullTagBits(typeBinding.tagBits);
                if (jValidNullTagBits != 0) {
                    return Boolean.valueOf(jValidNullTagBits == 72057594037927936L);
                }
            }
            return null;
        }
        if (methodBinding.parameterNonNullness == null) {
            return null;
        }
        return methodBinding.parameterNonNullness[i];
    }

    private long getReturnTypeNullnessTagBits(MethodBinding methodBinding, boolean z) {
        if (z) {
            if (methodBinding.returnType == null) {
                return 0L;
            }
            return NullAnnotationMatching.validNullTagBits(methodBinding.returnType.tagBits);
        }
        return methodBinding.tagBits & TagBits.AnnotationNullMASK;
    }

    protected void recordDeferredInheritedNullness(Scope scope, ASTNode aSTNode, MethodBinding methodBinding, Boolean bool, InheritedNonNullnessInfo inheritedNonNullnessInfo) {
        if (inheritedNonNullnessInfo.inheritedNonNullness != null && inheritedNonNullnessInfo.inheritedNonNullness != bool) {
            scope.problemReporter().conflictingInheritedNullAnnotations(aSTNode, inheritedNonNullnessInfo.inheritedNonNullness.booleanValue(), inheritedNonNullnessInfo.annotationOrigin, bool.booleanValue(), methodBinding);
            inheritedNonNullnessInfo.complained = true;
        } else {
            inheritedNonNullnessInfo.inheritedNonNullness = bool;
            inheritedNonNullnessInfo.annotationOrigin = methodBinding;
        }
    }

    void recordArgNonNullness(MethodBinding methodBinding, int i, int i2, Argument argument, Boolean bool) {
        if (methodBinding.parameterNonNullness == null) {
            methodBinding.parameterNonNullness = new Boolean[i];
        }
        methodBinding.parameterNonNullness[i2] = bool;
        if (argument != null) {
            argument.binding.tagBits |= bool.booleanValue() ? 72057594037927936L : 36028797018963968L;
        }
    }

    void recordArgNonNullness18(MethodBinding methodBinding, int i, Argument argument, Boolean bool, LookupEnvironment lookupEnvironment) {
        methodBinding.parameters[i] = lookupEnvironment.createAnnotatedType(methodBinding.parameters[i], new AnnotationBinding[]{bool.booleanValue() ? lookupEnvironment.getNonNullAnnotation() : lookupEnvironment.getNullableAnnotation()});
        if (argument != null) {
            argument.binding.type = methodBinding.parameters[i];
        }
    }

    static boolean areParametersEqual(MethodBinding methodBinding, MethodBinding methodBinding2) {
        TypeBinding[] typeBindingArr = methodBinding.parameters;
        TypeBinding[] typeBindingArr2 = methodBinding2.parameters;
        if (typeBindingArr == typeBindingArr2) {
            return true;
        }
        int length = typeBindingArr.length;
        if (length != typeBindingArr2.length) {
            return false;
        }
        int i = 0;
        while (i < length) {
            if (!areTypesEqual(typeBindingArr[i], typeBindingArr2[i])) {
                if (!typeBindingArr[i].leafComponentType().isRawType() || typeBindingArr[i].dimensions() != typeBindingArr2[i].dimensions() || !typeBindingArr[i].leafComponentType().isEquivalentTo(typeBindingArr2[i].leafComponentType()) || methodBinding.typeVariables != Binding.NO_TYPE_VARIABLES) {
                    return false;
                }
                for (int i2 = 0; i2 < i; i2++) {
                    if (typeBindingArr[i2].leafComponentType().isParameterizedTypeWithActualArguments()) {
                        return false;
                    }
                }
                break;
            }
            i++;
        }
        for (int i3 = i + 1; i3 < length; i3++) {
            if (!areTypesEqual(typeBindingArr[i3], typeBindingArr2[i3])) {
                if (!typeBindingArr[i3].leafComponentType().isRawType() || typeBindingArr[i3].dimensions() != typeBindingArr2[i3].dimensions() || !typeBindingArr[i3].leafComponentType().isEquivalentTo(typeBindingArr2[i3].leafComponentType())) {
                    return false;
                }
            } else if (typeBindingArr[i3].leafComponentType().isParameterizedTypeWithActualArguments()) {
                return false;
            }
        }
        return true;
    }

    static boolean areTypesEqual(TypeBinding typeBinding, TypeBinding typeBinding2) {
        if (TypeBinding.equalsEquals(typeBinding, typeBinding2)) {
            return true;
        }
        int iKind = typeBinding.kind();
        if (iKind == 4) {
            int iKind2 = typeBinding2.kind();
            if ((iKind2 == 260 || iKind2 == 1028) && TypeBinding.equalsEquals(typeBinding, typeBinding2.erasure())) {
                return true;
            }
        } else if ((iKind == 260 || iKind == 1028) && typeBinding2.kind() == 4 && TypeBinding.equalsEquals(typeBinding.erasure(), typeBinding2)) {
            return true;
        }
        return typeBinding.isParameterizedType() && typeBinding2.isParameterizedType() && typeBinding.isEquivalentTo(typeBinding2) && typeBinding2.isEquivalentTo(typeBinding);
    }
}
