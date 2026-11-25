package org.eclipse.jdt.internal.compiler.lookup;

import org.eclipse.jdt.internal.compiler.ast.ASTNode;
import org.eclipse.jdt.internal.compiler.ast.Expression;
import org.eclipse.jdt.internal.compiler.ast.Invocation;
import org.eclipse.jdt.internal.compiler.ast.NullAnnotationMatching;
import org.eclipse.jdt.internal.compiler.ast.ReferenceExpression;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;

/* JADX INFO: loaded from: classes14.dex */
public class ParameterizedGenericMethodBinding extends ParameterizedMethodBinding implements Substitution {
    private static /* synthetic */ int[] $SWITCH_TABLE$org$eclipse$jdt$internal$compiler$lookup$TypeConstants$BoundCheckStatus;
    protected LookupEnvironment environment;
    public boolean inferredReturnType;
    public boolean inferredWithUncheckedConversion;
    public boolean isRaw;
    private MethodBinding tiebreakMethod;
    public TypeBinding[] typeArguments;
    public boolean wasInferred;

    @Override // org.eclipse.jdt.internal.compiler.lookup.MethodBinding
    public boolean isParameterizedGeneric() {
        return true;
    }

    static /* synthetic */ int[] $SWITCH_TABLE$org$eclipse$jdt$internal$compiler$lookup$TypeConstants$BoundCheckStatus() {
        int[] iArr = $SWITCH_TABLE$org$eclipse$jdt$internal$compiler$lookup$TypeConstants$BoundCheckStatus;
        if (iArr != null) {
            return iArr;
        }
        int[] iArr2 = new int[TypeConstants.BoundCheckStatus.valuesCustom().length];
        try {
            iArr2[TypeConstants.BoundCheckStatus.MISMATCH.ordinal()] = 4;
        } catch (NoSuchFieldError unused) {
        }
        try {
            iArr2[TypeConstants.BoundCheckStatus.NULL_PROBLEM.ordinal()] = 2;
        } catch (NoSuchFieldError unused2) {
        }
        try {
            iArr2[TypeConstants.BoundCheckStatus.OK.ordinal()] = 1;
        } catch (NoSuchFieldError unused3) {
        }
        try {
            iArr2[TypeConstants.BoundCheckStatus.UNCHECKED.ordinal()] = 3;
        } catch (NoSuchFieldError unused4) {
        }
        $SWITCH_TABLE$org$eclipse$jdt$internal$compiler$lookup$TypeConstants$BoundCheckStatus = iArr2;
        return iArr2;
    }

    /* JADX WARN: Code duplicated, block: B:51:0x00d2  */
    public static MethodBinding computeCompatibleMethod(MethodBinding methodBinding, TypeBinding[] typeBindingArr, Scope scope, InvocationSite invocationSite) {
        InferenceContext inferenceContext;
        TypeBinding[] typeBindingArr2;
        ParameterizedGenericMethodBinding parameterizedGenericMethodBindingInferFromArgumentTypes;
        TypeBinding typeBindingInvocationTargetType;
        TypeVariableBinding[] typeVariableBindingArr = methodBinding.typeVariables;
        TypeBinding[] typeBindingArrGenericTypeArguments = invocationSite.genericTypeArguments();
        if (typeBindingArrGenericTypeArguments != null) {
            if (typeBindingArrGenericTypeArguments.length != typeVariableBindingArr.length) {
                return new ProblemMethodBinding(methodBinding, methodBinding.selector, typeBindingArrGenericTypeArguments, 11);
            }
            parameterizedGenericMethodBindingInferFromArgumentTypes = scope.environment().createParameterizedGenericMethod(methodBinding, typeBindingArrGenericTypeArguments);
            inferenceContext = null;
            typeBindingArr2 = null;
        } else {
            TypeBinding[] typeBindingArr3 = methodBinding.parameters;
            CompilerOptions compilerOptions = scope.compilerOptions();
            if (compilerOptions.sourceLevel >= ClassFileConstants.JDK1_8) {
                return computeCompatibleMethod18(methodBinding, typeBindingArr, scope, invocationSite);
            }
            inferenceContext = new InferenceContext(methodBinding);
            ParameterizedGenericMethodBinding parameterizedGenericMethodBindingInferFromArgumentTypes2 = inferFromArgumentTypes(scope, methodBinding, typeBindingArr, typeBindingArr3, inferenceContext);
            if (parameterizedGenericMethodBindingInferFromArgumentTypes2 == null) {
                return null;
            }
            if (inferenceContext.hasUnresolvedTypeArgument()) {
                if (inferenceContext.isUnchecked) {
                    int length = inferenceContext.substitutes.length;
                    typeBindingArr2 = new TypeBinding[length];
                    System.arraycopy(inferenceContext.substitutes, 0, typeBindingArr2, 0, length);
                } else {
                    typeBindingArr2 = null;
                }
                if (parameterizedGenericMethodBindingInferFromArgumentTypes2.returnType != TypeBinding.VOID) {
                    TypeBinding typeBindingInvocationTargetType2 = invocationSite.invocationTargetType();
                    if (typeBindingInvocationTargetType2 != null) {
                        inferenceContext.hasExplicitExpectedType = true;
                    } else {
                        typeBindingInvocationTargetType2 = scope.getJavaLangObject();
                    }
                    inferenceContext.expectedType = typeBindingInvocationTargetType2;
                }
                parameterizedGenericMethodBindingInferFromArgumentTypes = parameterizedGenericMethodBindingInferFromArgumentTypes2.inferFromExpectedType(scope, inferenceContext);
                if (parameterizedGenericMethodBindingInferFromArgumentTypes == null) {
                    return null;
                }
            } else if (compilerOptions.sourceLevel != ClassFileConstants.JDK1_7 || parameterizedGenericMethodBindingInferFromArgumentTypes2.returnType == TypeBinding.VOID || (typeBindingInvocationTargetType = invocationSite.invocationTargetType()) == null || methodBinding.returnType.mentionsAny(methodBinding.parameters, -1)) {
                typeBindingArr2 = null;
                parameterizedGenericMethodBindingInferFromArgumentTypes = parameterizedGenericMethodBindingInferFromArgumentTypes2;
            } else {
                TypeBinding typeBindingUncapture = parameterizedGenericMethodBindingInferFromArgumentTypes2.returnType.uncapture(scope);
                if (parameterizedGenericMethodBindingInferFromArgumentTypes2.returnType.isCompatibleWith(typeBindingInvocationTargetType) || !typeBindingInvocationTargetType.isCompatibleWith(typeBindingUncapture)) {
                    typeBindingArr2 = null;
                    parameterizedGenericMethodBindingInferFromArgumentTypes = parameterizedGenericMethodBindingInferFromArgumentTypes2;
                } else {
                    InferenceContext inferenceContext2 = new InferenceContext(methodBinding);
                    methodBinding.returnType.collectSubstitutes(scope, typeBindingInvocationTargetType, inferenceContext2, 1);
                    parameterizedGenericMethodBindingInferFromArgumentTypes = inferFromArgumentTypes(scope, methodBinding, typeBindingArr, typeBindingArr3, inferenceContext2);
                    if (parameterizedGenericMethodBindingInferFromArgumentTypes == null || !parameterizedGenericMethodBindingInferFromArgumentTypes.returnType.isCompatibleWith(typeBindingInvocationTargetType) || scope.parameterCompatibilityLevel((MethodBinding) parameterizedGenericMethodBindingInferFromArgumentTypes, typeBindingArr, false) <= -1) {
                        typeBindingArr2 = null;
                        parameterizedGenericMethodBindingInferFromArgumentTypes = parameterizedGenericMethodBindingInferFromArgumentTypes2;
                    } else {
                        inferenceContext = inferenceContext2;
                        typeBindingArr2 = null;
                    }
                }
            }
        }
        Substitution lingeringTypeVariableEliminator = inferenceContext != null ? new LingeringTypeVariableEliminator(typeVariableBindingArr, inferenceContext.substitutes, scope) : parameterizedGenericMethodBindingInferFromArgumentTypes;
        int length2 = typeVariableBindingArr.length;
        for (int i = 0; i < length2; i++) {
            TypeVariableBinding typeVariableBinding = typeVariableBindingArr[i];
            TypeBinding typeBinding = parameterizedGenericMethodBindingInferFromArgumentTypes.typeArguments[i];
            TypeBinding typeBindingSubstitute = typeBinding instanceof TypeVariableBinding ? typeBinding : Scope.substitute(new LingeringTypeVariableEliminator(typeVariableBindingArr, null, scope), typeBinding);
            if (typeBindingArr2 == null || typeBindingArr2[i] != null) {
                int i2 = $SWITCH_TABLE$org$eclipse$jdt$internal$compiler$lookup$TypeConstants$BoundCheckStatus()[typeVariableBinding.boundCheck(lingeringTypeVariableEliminator, typeBindingSubstitute, scope, null).ordinal()];
                if (i2 == 3) {
                    parameterizedGenericMethodBindingInferFromArgumentTypes.tagBits |= 256;
                } else if (i2 == 4) {
                    int length3 = typeBindingArr.length;
                    TypeBinding[] typeBindingArr4 = new TypeBinding[length3 + 2];
                    System.arraycopy(typeBindingArr, 0, typeBindingArr4, 0, length3);
                    typeBindingArr4[length3] = typeBinding;
                    typeBindingArr4[length3 + 1] = typeVariableBinding;
                    return new ProblemMethodBinding(parameterizedGenericMethodBindingInferFromArgumentTypes, methodBinding.selector, typeBindingArr4, 10);
                }
            }
        }
        return parameterizedGenericMethodBindingInferFromArgumentTypes;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static MethodBinding computeCompatibleMethod18(MethodBinding methodBinding, TypeBinding[] typeBindingArr, Scope scope, InvocationSite invocationSite) {
        boolean z;
        BoundSet boundSetSolve;
        boolean z2;
        TypeBinding[] solutions;
        MethodBinding methodBinding2 = methodBinding;
        TypeVariableBinding[] typeVariableBindingArr = methodBinding2.typeVariables;
        if (invocationSite.checkingPotentialCompatibility()) {
            return scope.environment().createParameterizedGenericMethod(methodBinding2, typeVariableBindingArr);
        }
        InferenceContext18 inferenceContext18FreshInferenceContext = invocationSite.freshInferenceContext(scope);
        if (inferenceContext18FreshInferenceContext == null) {
            return methodBinding2;
        }
        TypeBinding[] typeBindingArr2 = methodBinding2.parameters;
        CompilerOptions compilerOptions = scope.compilerOptions();
        int length = typeBindingArr.length;
        TypeBinding[] typeBindingArr3 = new TypeBinding[length];
        int length2 = typeBindingArr.length;
        int length3 = typeBindingArr2.length;
        int i = 0;
        boolean z3 = false;
        while (true) {
            z = true;
            if (i >= length2) {
                break;
            }
            TypeBinding typeBinding = i < length3 ? typeBindingArr2[i] : typeBindingArr2[length3 - 1];
            TypeBinding typeBinding2 = typeBindingArr[i];
            if (typeBinding2.isPrimitiveType() != typeBinding.isPrimitiveType()) {
                typeBindingArr3[i] = scope.environment().computeBoxingType(typeBinding2);
                z3 = true;
            } else {
                typeBindingArr3[i] = typeBinding2;
            }
            i++;
            methodBinding2 = methodBinding;
        }
        LookupEnvironment lookupEnvironmentEnvironment = scope.environment();
        InferenceContext18 inferenceContext18 = lookupEnvironmentEnvironment.currentInferenceContext;
        if (inferenceContext18 == null) {
            lookupEnvironmentEnvironment.currentInferenceContext = inferenceContext18FreshInferenceContext;
        }
        try {
            try {
                boolean z4 = (invocationSite instanceof Expression) && ((Expression) invocationSite).isPolyExpression(methodBinding2);
                boolean z5 = z4 && methodBinding.isConstructor();
                if (length == typeBindingArr2.length) {
                    inferenceContext18FreshInferenceContext.inferenceKind = z3 ? 2 : 1;
                    inferenceContext18FreshInferenceContext.inferInvocationApplicability(methodBinding2, typeBindingArr3, z5);
                    boundSetSolve = inferenceContext18FreshInferenceContext.solve(true);
                } else {
                    boundSetSolve = null;
                }
                if (boundSetSolve == null && methodBinding.isVarargs()) {
                    inferenceContext18FreshInferenceContext = invocationSite.freshInferenceContext(scope);
                    inferenceContext18FreshInferenceContext.inferenceKind = 3;
                    inferenceContext18FreshInferenceContext.inferInvocationApplicability(methodBinding2, typeBindingArr3, z5);
                    boundSetSolve = inferenceContext18FreshInferenceContext.solve(true);
                }
                if (boundSetSolve == null) {
                    lookupEnvironmentEnvironment.currentInferenceContext = inferenceContext18;
                    return null;
                }
                if (!inferenceContext18FreshInferenceContext.isResolved(boundSetSolve)) {
                    lookupEnvironmentEnvironment.currentInferenceContext = inferenceContext18;
                    return null;
                }
                inferenceContext18FreshInferenceContext.stepCompleted = 1;
                TypeBinding typeBindingInvocationTargetType = invocationSite.invocationTargetType();
                if (typeBindingInvocationTargetType == null && invocationSite.getExpressionContext().definesTargetType() && z4) {
                    z2 = false;
                    z = false;
                } else {
                    BoundSet boundSetInferInvocationType = inferenceContext18FreshInferenceContext.inferInvocationType(typeBindingInvocationTargetType, invocationSite, methodBinding2);
                    boolean z6 = boundSetInferInvocationType == null;
                    if (!z6) {
                        boundSetSolve = boundSetInferInvocationType;
                    }
                    z2 = z6;
                }
                if (boundSetSolve == null || (solutions = inferenceContext18FreshInferenceContext.getSolutions(typeVariableBindingArr, invocationSite, boundSetSolve)) == null) {
                    lookupEnvironmentEnvironment.currentInferenceContext = inferenceContext18;
                    return null;
                }
                ParameterizedGenericMethodBinding parameterizedGenericMethodBindingCreateParameterizedGenericMethod = scope.environment().createParameterizedGenericMethod(methodBinding2, solutions, inferenceContext18FreshInferenceContext.usesUncheckedConversion, z2);
                if (invocationSite instanceof Invocation) {
                    inferenceContext18FreshInferenceContext.forwardResults(boundSetSolve, (Invocation) invocationSite, parameterizedGenericMethodBindingCreateParameterizedGenericMethod, typeBindingInvocationTargetType);
                }
                if (z2) {
                    try {
                        MethodBinding returnProblemMethodIfNeeded = inferenceContext18FreshInferenceContext.getReturnProblemMethodIfNeeded(typeBindingInvocationTargetType, parameterizedGenericMethodBindingCreateParameterizedGenericMethod);
                        if (returnProblemMethodIfNeeded instanceof ProblemMethodBinding) {
                            if (invocationSite instanceof Invocation) {
                                ((Invocation) invocationSite).registerInferenceContext(parameterizedGenericMethodBindingCreateParameterizedGenericMethod, inferenceContext18FreshInferenceContext);
                            } else if (invocationSite instanceof ReferenceExpression) {
                                ((ReferenceExpression) invocationSite).registerInferenceContext(parameterizedGenericMethodBindingCreateParameterizedGenericMethod, inferenceContext18FreshInferenceContext);
                            }
                            lookupEnvironmentEnvironment.currentInferenceContext = inferenceContext18;
                            return returnProblemMethodIfNeeded;
                        }
                    } catch (Throwable th) {
                        if (invocationSite instanceof Invocation) {
                            ((Invocation) invocationSite).registerInferenceContext(parameterizedGenericMethodBindingCreateParameterizedGenericMethod, inferenceContext18FreshInferenceContext);
                        } else if (invocationSite instanceof ReferenceExpression) {
                            ((ReferenceExpression) invocationSite).registerInferenceContext(parameterizedGenericMethodBindingCreateParameterizedGenericMethod, inferenceContext18FreshInferenceContext);
                        }
                        throw th;
                    }
                }
                if (z) {
                    if (compilerOptions.isAnnotationBasedNullAnalysisEnabled) {
                        NullAnnotationMatching.checkForContradictions(parameterizedGenericMethodBindingCreateParameterizedGenericMethod, invocationSite, scope);
                    }
                    MethodBinding methodBindingBoundCheck18 = parameterizedGenericMethodBindingCreateParameterizedGenericMethod.boundCheck18(scope, typeBindingArr3, invocationSite);
                    if (methodBindingBoundCheck18 != null) {
                        if (invocationSite instanceof Invocation) {
                            ((Invocation) invocationSite).registerInferenceContext(parameterizedGenericMethodBindingCreateParameterizedGenericMethod, inferenceContext18FreshInferenceContext);
                        } else if (invocationSite instanceof ReferenceExpression) {
                            ((ReferenceExpression) invocationSite).registerInferenceContext(parameterizedGenericMethodBindingCreateParameterizedGenericMethod, inferenceContext18FreshInferenceContext);
                        }
                        lookupEnvironmentEnvironment.currentInferenceContext = inferenceContext18;
                        return methodBindingBoundCheck18;
                    }
                } else {
                    parameterizedGenericMethodBindingCreateParameterizedGenericMethod = new PolyParameterizedGenericMethodBinding(parameterizedGenericMethodBindingCreateParameterizedGenericMethod);
                }
                if (invocationSite instanceof Invocation) {
                    ((Invocation) invocationSite).registerInferenceContext(parameterizedGenericMethodBindingCreateParameterizedGenericMethod, inferenceContext18FreshInferenceContext);
                } else if (invocationSite instanceof ReferenceExpression) {
                    ((ReferenceExpression) invocationSite).registerInferenceContext(parameterizedGenericMethodBindingCreateParameterizedGenericMethod, inferenceContext18FreshInferenceContext);
                }
                lookupEnvironmentEnvironment.currentInferenceContext = inferenceContext18;
                return parameterizedGenericMethodBindingCreateParameterizedGenericMethod;
            } catch (InferenceFailureException e) {
                scope.problemReporter().genericInferenceError(e.getMessage(), invocationSite);
                lookupEnvironmentEnvironment.currentInferenceContext = inferenceContext18;
                return null;
            }
        } catch (Throwable th2) {
            lookupEnvironmentEnvironment.currentInferenceContext = inferenceContext18;
            throw th2;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    MethodBinding boundCheck18(Scope scope, TypeBinding[] typeBindingArr, InvocationSite invocationSite) {
        TypeVariableBinding[] typeVariableBindingArr = this.originalMethod.typeVariables;
        int length = typeVariableBindingArr.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                return null;
            }
            TypeVariableBinding typeVariableBinding = typeVariableBindingArr[i];
            TypeBinding typeBinding = this.typeArguments[i];
            int i2 = $SWITCH_TABLE$org$eclipse$jdt$internal$compiler$lookup$TypeConstants$BoundCheckStatus()[typeVariableBinding.boundCheck(this, typeBinding, scope, invocationSite instanceof ASTNode ? (ASTNode) invocationSite : null).ordinal()];
            if (i2 == 3) {
                this.tagBits |= 256;
            } else if (i2 == 4) {
                int length2 = typeBindingArr.length;
                TypeBinding[] typeBindingArr2 = new TypeBinding[length2 + 2];
                System.arraycopy(typeBindingArr, 0, typeBindingArr2, 0, length2);
                typeBindingArr2[length2] = typeBinding;
                typeBindingArr2[length2 + 1] = typeVariableBinding;
                return new ProblemMethodBinding(this, this.originalMethod.selector, typeBindingArr2, 10);
            }
            i++;
        }
    }

    private static ParameterizedGenericMethodBinding inferFromArgumentTypes(Scope scope, MethodBinding methodBinding, TypeBinding[] typeBindingArr, TypeBinding[] typeBindingArr2, InferenceContext inferenceContext) {
        int iDimensions;
        if (methodBinding.isVarargs()) {
            int length = typeBindingArr2.length;
            int i = length - 1;
            int length2 = typeBindingArr.length;
            for (int i2 = 0; i2 < i; i2++) {
                typeBindingArr2[i2].collectSubstitutes(scope, typeBindingArr[i2], inferenceContext, 1);
                if (inferenceContext.status == 1) {
                    return null;
                }
            }
            if (i < length2) {
                TypeBinding typeBindingElementsType = typeBindingArr2[i];
                TypeBinding typeBinding = typeBindingArr[i];
                if (length != length2 || (typeBinding != TypeBinding.NULL && ((iDimensions = typeBinding.dimensions()) == 0 || (iDimensions == 1 && typeBinding.leafComponentType().isBaseType())))) {
                    typeBindingElementsType = ((ArrayBinding) typeBindingElementsType).elementsType();
                }
                while (i < length2) {
                    typeBindingElementsType.collectSubstitutes(scope, typeBindingArr[i], inferenceContext, 1);
                    if (inferenceContext.status == 1) {
                        return null;
                    }
                    i++;
                }
            }
        } else {
            int length3 = typeBindingArr2.length;
            for (int i3 = 0; i3 < length3; i3++) {
                typeBindingArr2[i3].collectSubstitutes(scope, typeBindingArr[i3], inferenceContext, 1);
                if (inferenceContext.status == 1) {
                    return null;
                }
            }
        }
        TypeVariableBinding[] typeVariableBindingArr = methodBinding.typeVariables;
        if (!resolveSubstituteConstraints(scope, typeVariableBindingArr, inferenceContext, false)) {
            return null;
        }
        TypeBinding[] typeBindingArr3 = inferenceContext.substitutes;
        int length4 = typeVariableBindingArr.length;
        TypeBinding[] typeBindingArr4 = typeBindingArr3;
        for (int i4 = 0; i4 < length4; i4++) {
            TypeBinding typeBinding2 = typeBindingArr3[i4];
            if (typeBinding2 == null) {
                if (typeBindingArr4 == typeBindingArr3) {
                    typeBindingArr4 = new TypeBinding[length4];
                    System.arraycopy(typeBindingArr3, 0, typeBindingArr4, 0, i4);
                }
                typeBindingArr4[i4] = typeVariableBindingArr[i4];
            } else if (typeBindingArr4 != typeBindingArr3) {
                typeBindingArr4[i4] = typeBinding2;
            }
        }
        return scope.environment().createParameterizedGenericMethod(methodBinding, typeBindingArr4);
    }

    private static boolean resolveSubstituteConstraints(Scope scope, TypeVariableBinding[] typeVariableBindingArr, InferenceContext inferenceContext, boolean z) {
        TypeBinding[] substitutes;
        TypeBinding typeBindingCreateWildcard;
        TypeBinding[] substitutes2;
        TypeBinding[] substitutes3;
        TypeBinding[] typeBindingArr = inferenceContext.substitutes;
        int length = typeVariableBindingArr.length;
        for (int i = 0; i < length; i++) {
            TypeVariableBinding typeVariableBinding = typeVariableBindingArr[i];
            if (typeBindingArr[i] == null && (substitutes3 = inferenceContext.getSubstitutes(typeVariableBinding, 0)) != null) {
                int length2 = substitutes3.length;
                int i2 = 0;
                while (i2 < length2) {
                    TypeBinding typeBinding = substitutes3[i2];
                    if (typeBinding != null) {
                        if (!TypeBinding.equalsEquals(typeBinding, typeVariableBinding)) {
                            typeBindingArr[i] = typeBinding;
                            break;
                        }
                        while (true) {
                            i2++;
                            if (i2 < length2) {
                                TypeBinding typeBinding2 = substitutes3[i2];
                                if (TypeBinding.notEquals(typeBinding2, typeVariableBinding) && typeBinding2 != null) {
                                    typeBindingArr[i] = typeBinding2;
                                    break;
                                }
                            } else {
                                typeBindingArr[i] = typeVariableBinding;
                                break;
                            }
                        }
                    } else {
                        i2++;
                    }
                }
            }
        }
        if (inferenceContext.hasUnresolvedTypeArgument()) {
            for (int i3 = 0; i3 < length; i3++) {
                TypeVariableBinding typeVariableBinding2 = typeVariableBindingArr[i3];
                if (typeBindingArr[i3] == null && (substitutes2 = inferenceContext.getSubstitutes(typeVariableBinding2, 2)) != null) {
                    TypeBinding typeBindingLowerUpperBound = scope.lowerUpperBound(substitutes2);
                    if (typeBindingLowerUpperBound == null) {
                        return false;
                    }
                    if (typeBindingLowerUpperBound != TypeBinding.VOID) {
                        typeBindingArr[i3] = typeBindingLowerUpperBound;
                    }
                }
            }
        }
        if (z && inferenceContext.hasUnresolvedTypeArgument()) {
            for (int i4 = 0; i4 < length; i4++) {
                TypeVariableBinding typeVariableBinding3 = typeVariableBindingArr[i4];
                if (typeBindingArr[i4] == null && (substitutes = inferenceContext.getSubstitutes(typeVariableBinding3, 1)) != null) {
                    TypeBinding[] typeBindingArrGreaterLowerBound = Scope.greaterLowerBound(substitutes, scope, scope.environment());
                    if (typeBindingArrGreaterLowerBound == null) {
                        typeBindingCreateWildcard = null;
                    } else if (typeBindingArrGreaterLowerBound.length == 1) {
                        typeBindingCreateWildcard = typeBindingArrGreaterLowerBound[0];
                    } else {
                        TypeBinding[] typeBindingArr2 = new TypeBinding[typeBindingArrGreaterLowerBound.length - 1];
                        System.arraycopy(typeBindingArrGreaterLowerBound, 1, typeBindingArr2, 0, typeBindingArrGreaterLowerBound.length - 1);
                        typeBindingCreateWildcard = scope.environment().createWildcard(null, 0, typeBindingArrGreaterLowerBound[0], typeBindingArr2, 1);
                    }
                    if (typeBindingCreateWildcard != null) {
                        typeBindingArr[i4] = typeBindingCreateWildcard;
                    }
                }
            }
        }
        return true;
    }

    public ParameterizedGenericMethodBinding(MethodBinding methodBinding, RawTypeBinding rawTypeBinding, LookupEnvironment lookupEnvironment) {
        TypeBinding[] typeBindingArrSubstitute;
        ReferenceBinding[] referenceBindingArrSubstitute;
        TypeBinding typeBindingSubstitute;
        TypeVariableBinding[] typeVariableBindingArr = methodBinding.typeVariables;
        int length = typeVariableBindingArr.length;
        TypeBinding[] typeBindingArr = new TypeBinding[length];
        for (int i = 0; i < length; i++) {
            typeBindingArr[i] = lookupEnvironment.convertToRawType(typeVariableBindingArr[i].erasure(), false);
        }
        boolean z = true;
        this.isRaw = true;
        this.tagBits = methodBinding.tagBits;
        this.environment = lookupEnvironment;
        this.modifiers = methodBinding.modifiers;
        this.selector = methodBinding.selector;
        this.declaringClass = rawTypeBinding == null ? methodBinding.declaringClass : rawTypeBinding;
        this.typeVariables = Binding.NO_TYPE_VARIABLES;
        this.typeArguments = typeBindingArr;
        this.originalMethod = methodBinding;
        if (rawTypeBinding != null && !methodBinding.isStatic()) {
            z = false;
        }
        if (z) {
            typeBindingArrSubstitute = methodBinding.parameters;
        } else {
            typeBindingArrSubstitute = Scope.substitute(rawTypeBinding, methodBinding.parameters);
        }
        this.parameters = Scope.substitute(this, typeBindingArrSubstitute);
        if (z) {
            referenceBindingArrSubstitute = methodBinding.thrownExceptions;
        } else {
            referenceBindingArrSubstitute = Scope.substitute((Substitution) rawTypeBinding, methodBinding.thrownExceptions);
        }
        this.thrownExceptions = Scope.substitute((Substitution) this, referenceBindingArrSubstitute);
        if (this.thrownExceptions == null) {
            this.thrownExceptions = Binding.NO_EXCEPTIONS;
        }
        if (z) {
            typeBindingSubstitute = methodBinding.returnType;
        } else {
            typeBindingSubstitute = Scope.substitute(rawTypeBinding, methodBinding.returnType);
        }
        this.returnType = Scope.substitute(this, typeBindingSubstitute);
        this.wasInferred = false;
        this.parameterNonNullness = methodBinding.parameterNonNullness;
        this.defaultNullness = methodBinding.defaultNullness;
    }

    public ParameterizedGenericMethodBinding(MethodBinding methodBinding, TypeBinding[] typeBindingArr, LookupEnvironment lookupEnvironment, boolean z, boolean z2) {
        this.environment = lookupEnvironment;
        this.inferredWithUncheckedConversion = z;
        this.modifiers = methodBinding.modifiers;
        this.selector = methodBinding.selector;
        this.declaringClass = methodBinding.declaringClass;
        if (z && methodBinding.isConstructor() && this.declaringClass.isParameterizedType()) {
            this.declaringClass = (ReferenceBinding) lookupEnvironment.convertToRawType(this.declaringClass.erasure(), false);
        }
        this.typeVariables = Binding.NO_TYPE_VARIABLES;
        this.typeArguments = typeBindingArr;
        this.isRaw = false;
        this.tagBits = methodBinding.tagBits;
        this.originalMethod = methodBinding;
        this.parameters = Scope.substitute(this, methodBinding.parameters);
        if (z) {
            this.returnType = getErasure18_5_2(methodBinding.returnType, lookupEnvironment, z2);
            this.thrownExceptions = new ReferenceBinding[methodBinding.thrownExceptions.length];
            for (int i = 0; i < methodBinding.thrownExceptions.length; i++) {
                this.thrownExceptions[i] = (ReferenceBinding) getErasure18_5_2(methodBinding.thrownExceptions[i], lookupEnvironment, false);
            }
        } else {
            this.returnType = Scope.substitute(this, methodBinding.returnType);
            this.thrownExceptions = Scope.substitute((Substitution) this, methodBinding.thrownExceptions);
        }
        if (this.thrownExceptions == null) {
            this.thrownExceptions = Binding.NO_EXCEPTIONS;
        }
        if ((this.tagBits & 128) == 0) {
            if ((this.returnType.tagBits & 128) != 0) {
                this.tagBits |= 128;
            } else {
                int length = this.parameters.length;
                int i2 = 0;
                while (true) {
                    if (i2 >= length) {
                        int length2 = this.thrownExceptions.length;
                        for (int i3 = 0; i3 < length2; i3++) {
                            if ((this.thrownExceptions[i3].tagBits & 128) != 0) {
                                this.tagBits |= 128;
                                break;
                            }
                        }
                        break;
                    }
                    if ((this.parameters[i2].tagBits & 128) != 0) {
                        this.tagBits |= 128;
                        break;
                    }
                    i2++;
                }
            }
        }
        this.wasInferred = true;
        this.parameterNonNullness = methodBinding.parameterNonNullness;
        this.defaultNullness = methodBinding.defaultNullness;
        int length3 = this.parameters.length;
        for (int i4 = 0; i4 < length3; i4++) {
            if (this.parameters[i4] == TypeBinding.NULL && (methodBinding.parameters[i4].tagBits & TagBits.AnnotationNullMASK) == 72057594037927936L) {
                if (this.parameterNonNullness == null) {
                    this.parameterNonNullness = new Boolean[length3];
                }
                this.parameterNonNullness[i4] = Boolean.TRUE;
            }
        }
    }

    TypeBinding getErasure18_5_2(TypeBinding typeBinding, LookupEnvironment lookupEnvironment, boolean z) {
        if (z) {
            typeBinding = Scope.substitute(this, typeBinding);
        }
        return lookupEnvironment.convertToRawType(typeBinding.erasure(), true);
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.MethodBinding, org.eclipse.jdt.internal.compiler.lookup.Binding
    public char[] computeUniqueKey(boolean z) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(this.originalMethod.computeUniqueKey(false));
        stringBuffer.append("%<");
        if (!this.isRaw) {
            int length = this.typeArguments.length;
            for (int i = 0; i < length; i++) {
                stringBuffer.append(this.typeArguments[i].computeUniqueKey(false));
            }
        }
        stringBuffer.append('>');
        int length2 = stringBuffer.length();
        char[] cArr = new char[length2];
        stringBuffer.getChars(0, length2, cArr, 0);
        return cArr;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.Substitution
    public LookupEnvironment environment() {
        return this.environment;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ParameterizedMethodBinding, org.eclipse.jdt.internal.compiler.lookup.MethodBinding
    public boolean hasSubstitutedParameters() {
        if (this.wasInferred) {
            return this.originalMethod.hasSubstitutedParameters();
        }
        return super.hasSubstitutedParameters();
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ParameterizedMethodBinding, org.eclipse.jdt.internal.compiler.lookup.MethodBinding
    public boolean hasSubstitutedReturnType() {
        if (this.inferredReturnType) {
            return this.originalMethod.hasSubstitutedReturnType();
        }
        return super.hasSubstitutedReturnType();
    }

    private ParameterizedGenericMethodBinding inferFromExpectedType(Scope scope, InferenceContext inferenceContext) {
        TypeVariableBinding[] typeVariableBindingArr = this.originalMethod.typeVariables;
        int length = typeVariableBindingArr.length;
        if (inferenceContext.expectedType != null) {
            this.returnType.collectSubstitutes(scope, inferenceContext.expectedType, inferenceContext, 2);
            if (inferenceContext.status == 1) {
                return null;
            }
        }
        for (int i = 0; i < length; i++) {
            TypeVariableBinding typeVariableBinding = typeVariableBindingArr[i];
            TypeBinding typeBinding = this.typeArguments[i];
            boolean zNotEquals = TypeBinding.notEquals(typeBinding, typeVariableBinding);
            if (TypeBinding.equalsEquals(typeVariableBinding.firstBound, typeVariableBinding.superclass)) {
                TypeBinding typeBindingSubstitute = Scope.substitute(this, typeVariableBinding.superclass);
                typeBinding.collectSubstitutes(scope, typeBindingSubstitute, inferenceContext, 2);
                if (inferenceContext.status == 1) {
                    return null;
                }
                if (zNotEquals) {
                    typeBindingSubstitute.collectSubstitutes(scope, typeBinding, inferenceContext, 1);
                    if (inferenceContext.status == 1) {
                        return null;
                    }
                }
            }
            int length2 = typeVariableBinding.superInterfaces.length;
            for (int i2 = 0; i2 < length2; i2++) {
                TypeBinding typeBindingSubstitute2 = Scope.substitute(this, typeVariableBinding.superInterfaces[i2]);
                typeBinding.collectSubstitutes(scope, typeBindingSubstitute2, inferenceContext, 2);
                if (inferenceContext.status == 1) {
                    return null;
                }
                if (zNotEquals) {
                    typeBindingSubstitute2.collectSubstitutes(scope, typeBinding, inferenceContext, 1);
                    if (inferenceContext.status == 1) {
                        return null;
                    }
                }
            }
        }
        if (!resolveSubstituteConstraints(scope, typeVariableBindingArr, inferenceContext, true)) {
            return null;
        }
        for (int i3 = 0; i3 < length; i3++) {
            TypeBinding typeBinding2 = inferenceContext.substitutes[i3];
            if (typeBinding2 != null) {
                this.typeArguments[i3] = typeBinding2;
            } else {
                TypeBinding[] typeBindingArr = this.typeArguments;
                TypeBinding[] typeBindingArr2 = inferenceContext.substitutes;
                TypeBinding typeBindingUpperBound = typeVariableBindingArr[i3].upperBound();
                typeBindingArr2[i3] = typeBindingUpperBound;
                typeBindingArr[i3] = typeBindingUpperBound;
            }
        }
        this.typeArguments = Scope.substitute(this, this.typeArguments);
        TypeBinding typeBinding3 = this.returnType;
        this.returnType = Scope.substitute(this, this.returnType);
        this.inferredReturnType = inferenceContext.hasExplicitExpectedType && TypeBinding.notEquals(this.returnType, typeBinding3);
        this.parameters = Scope.substitute(this, this.parameters);
        this.thrownExceptions = Scope.substitute((Substitution) this, this.thrownExceptions);
        if (this.thrownExceptions == null) {
            this.thrownExceptions = Binding.NO_EXCEPTIONS;
        }
        if ((this.tagBits & 128) == 0) {
            if ((this.returnType.tagBits & 128) != 0) {
                this.tagBits |= 128;
            } else {
                int length3 = this.parameters.length;
                for (int i4 = 0; i4 < length3; i4++) {
                    if ((this.parameters[i4].tagBits & 128) != 0) {
                        this.tagBits |= 128;
                    }
                }
                int length4 = this.thrownExceptions.length;
                for (int i5 = 0; i5 < length4; i5++) {
                    if ((this.thrownExceptions[i5].tagBits & 128) != 0) {
                        this.tagBits |= 128;
                        break;
                    }
                }
            }
        }
        return this;
    }

    private static class LingeringTypeVariableEliminator implements Substitution {
        private final Scope scope;
        private final TypeBinding[] substitutes;
        private final TypeVariableBinding[] variables;

        @Override // org.eclipse.jdt.internal.compiler.lookup.Substitution
        public boolean isRawSubstitution() {
            return false;
        }

        public LingeringTypeVariableEliminator(TypeVariableBinding[] typeVariableBindingArr, TypeBinding[] typeBindingArr, Scope scope) {
            this.variables = typeVariableBindingArr;
            this.substitutes = typeBindingArr;
            this.scope = scope;
        }

        @Override // org.eclipse.jdt.internal.compiler.lookup.Substitution
        public TypeBinding substitute(TypeVariableBinding typeVariableBinding) {
            int i = typeVariableBinding.rank;
            TypeVariableBinding[] typeVariableBindingArr = this.variables;
            if (i >= typeVariableBindingArr.length || TypeBinding.notEquals(typeVariableBindingArr[typeVariableBinding.rank], typeVariableBinding)) {
                return typeVariableBinding;
            }
            if (this.substitutes != null) {
                return Scope.substitute(new LingeringTypeVariableEliminator(this.variables, null, this.scope), this.substitutes[typeVariableBinding.rank]);
            }
            return this.scope.environment().createWildcard((ReferenceBinding) (typeVariableBinding.declaringElement instanceof ReferenceBinding ? typeVariableBinding.declaringElement : null), typeVariableBinding.rank, null, null, 0, typeVariableBinding.getTypeAnnotations());
        }

        @Override // org.eclipse.jdt.internal.compiler.lookup.Substitution
        public LookupEnvironment environment() {
            return this.scope.environment();
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.Substitution
    public boolean isRawSubstitution() {
        return this.isRaw;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.Substitution
    public TypeBinding substitute(TypeVariableBinding typeVariableBinding) {
        TypeVariableBinding[] typeVariableBindingArr = this.originalMethod.typeVariables;
        return (typeVariableBinding.rank >= typeVariableBindingArr.length || !TypeBinding.equalsEquals(typeVariableBindingArr[typeVariableBinding.rank], typeVariableBinding)) ? typeVariableBinding : typeVariableBinding.combineTypeAnnotations(this.typeArguments[typeVariableBinding.rank]);
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.MethodBinding
    public MethodBinding tiebreakMethod() {
        if (this.tiebreakMethod == null) {
            this.tiebreakMethod = this.originalMethod.asRawMethod(this.environment);
        }
        return this.tiebreakMethod;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.MethodBinding
    public MethodBinding genericMethod() {
        return this.isRaw ? this : this.originalMethod;
    }
}
