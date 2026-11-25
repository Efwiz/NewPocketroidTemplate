package org.eclipse.jdt.internal.compiler.lookup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.compiler.ast.BinaryExpression;
import org.eclipse.jdt.internal.compiler.ast.ConditionalExpression;
import org.eclipse.jdt.internal.compiler.ast.Expression;
import org.eclipse.jdt.internal.compiler.ast.FunctionalExpression;
import org.eclipse.jdt.internal.compiler.ast.Invocation;
import org.eclipse.jdt.internal.compiler.ast.LambdaExpression;
import org.eclipse.jdt.internal.compiler.ast.ReferenceExpression;
import org.eclipse.jdt.internal.compiler.util.Sorting;

/* JADX INFO: loaded from: classes14.dex */
public class InferenceContext18 {
    public static final int APPLICABILITY_INFERRED = 1;
    static final boolean ARGUMENT_CONSTRAINTS_ARE_SOFT = false;
    public static final int CHECK_LOOSE = 2;
    public static final int CHECK_STRICT = 1;
    public static final int CHECK_UNKNOWN = 0;
    public static final int CHECK_VARARG = 3;
    public static final int NOT_INFERRED = 0;
    static final boolean SHOULD_WORKAROUND_BUG_JDK_8054721 = true;
    static final boolean SIMULATE_BUG_JDK_8026527 = true;
    public static final int TYPE_INFERRED = 2;
    public BoundSet b2;
    public List<ConstraintFormula> constraintsWithUncheckedConversion;
    BoundSet currentBounds;
    InvocationSite currentInvocation;
    LookupEnvironment environment;
    ConstraintExpressionFormula[] finalConstraints;
    int inferenceKind;
    InferenceVariable[] inferenceVariables;
    ConstraintFormula[] initialConstraints;
    private InferenceVariable[] internedVariables;
    Expression[] invocationArguments;
    int nextVarId;
    ReferenceBinding object;
    public InferenceContext18 outerContext;
    Scope scope;
    public boolean usesUncheckedConversion;
    public int stepCompleted = 0;
    int captureId = 0;

    private InferenceVariable getInferenceVariable(TypeBinding typeBinding, int i, InvocationSite invocationSite) {
        InferenceContext18 inferenceContext18 = this.environment.currentInferenceContext;
        if (inferenceContext18 == null) {
            inferenceContext18 = this;
        }
        InferenceVariable[] inferenceVariableArr = inferenceContext18.internedVariables;
        int i2 = 0;
        if (inferenceVariableArr == null) {
            inferenceContext18.internedVariables = new InferenceVariable[10];
        } else {
            int length = inferenceVariableArr.length;
            int i3 = 0;
            while (i3 < length) {
                InferenceVariable inferenceVariable = inferenceVariableArr[i3];
                if (inferenceVariable == null) {
                    break;
                }
                if (inferenceVariable.typeParameter == typeBinding && inferenceVariable.rank == i && isSameSite(inferenceVariable.site, invocationSite)) {
                    return inferenceVariable;
                }
                i3++;
            }
            if (i3 >= length) {
                InferenceVariable[] inferenceVariableArr2 = new InferenceVariable[length + 10];
                inferenceContext18.internedVariables = inferenceVariableArr2;
                System.arraycopy(inferenceVariableArr, 0, inferenceVariableArr2, 0, length);
            }
            i2 = i3;
        }
        InferenceVariable[] inferenceVariableArr3 = inferenceContext18.internedVariables;
        int i4 = this.nextVarId;
        this.nextVarId = i4 + 1;
        InferenceVariable inferenceVariable2 = new InferenceVariable(typeBinding, i, i4, invocationSite, this.environment, this.object);
        inferenceVariableArr3[i2] = inferenceVariable2;
        return inferenceVariable2;
    }

    boolean isSameSite(InvocationSite invocationSite, InvocationSite invocationSite2) {
        if (invocationSite == invocationSite2) {
            return true;
        }
        return invocationSite != null && invocationSite2 != null && invocationSite.sourceStart() == invocationSite2.sourceStart() && invocationSite.sourceEnd() == invocationSite2.sourceEnd() && invocationSite.toString().equals(invocationSite2.toString());
    }

    static class SuspendedInferenceRecord {
        int inferenceKind;
        InferenceVariable[] inferenceVariables;
        Expression[] invocationArguments;
        InvocationSite site;
        boolean usesUncheckedConversion;

        SuspendedInferenceRecord(InvocationSite invocationSite, Expression[] expressionArr, InferenceVariable[] inferenceVariableArr, int i, boolean z) {
            this.site = invocationSite;
            this.invocationArguments = expressionArr;
            this.inferenceVariables = inferenceVariableArr;
            this.inferenceKind = i;
            this.usesUncheckedConversion = z;
        }
    }

    public InferenceContext18(Scope scope, Expression[] expressionArr, InvocationSite invocationSite, InferenceContext18 inferenceContext18) {
        this.scope = scope;
        this.environment = scope.environment();
        this.object = scope.getJavaLangObject();
        this.invocationArguments = expressionArr;
        this.currentInvocation = invocationSite;
        this.outerContext = inferenceContext18;
        if (invocationSite instanceof Invocation) {
            scope.compilationUnitScope().registerInferredInvocation((Invocation) invocationSite);
        }
    }

    public InferenceContext18(Scope scope) {
        this.scope = scope;
        this.environment = scope.environment();
        this.object = scope.getJavaLangObject();
    }

    public InferenceVariable[] createInitialBoundSet(TypeVariableBinding[] typeVariableBindingArr) {
        if (this.currentBounds == null) {
            this.currentBounds = new BoundSet();
        }
        if (typeVariableBindingArr != null) {
            InferenceVariable[] inferenceVariableArrAddInitialTypeVariableSubstitutions = addInitialTypeVariableSubstitutions(typeVariableBindingArr);
            this.currentBounds.addBoundsFromTypeParameters(this, typeVariableBindingArr, inferenceVariableArrAddInitialTypeVariableSubstitutions);
            return inferenceVariableArrAddInitialTypeVariableSubstitutions;
        }
        return Binding.NO_INFERENCE_VARIABLES;
    }

    public TypeBinding substitute(TypeBinding typeBinding) {
        InferenceSubstitution inferenceSubstitution = new InferenceSubstitution(this);
        return inferenceSubstitution.substitute(inferenceSubstitution, typeBinding);
    }

    public void createInitialConstraintsForParameters(TypeBinding[] typeBindingArr, boolean z, TypeBinding typeBinding, MethodBinding methodBinding) {
        int length;
        boolean z2;
        int i;
        Expression[] expressionArr = this.invocationArguments;
        if (expressionArr == null) {
            return;
        }
        int i2 = 1;
        int length2 = z ? typeBindingArr.length - 1 : Math.min(typeBindingArr.length, expressionArr.length);
        int length3 = z ? this.invocationArguments.length : length2;
        ConstraintFormula[] constraintFormulaArr = this.initialConstraints;
        if (constraintFormulaArr == null) {
            this.initialConstraints = new ConstraintFormula[length3];
            z2 = true;
            length = 0;
        } else {
            length = constraintFormulaArr.length;
            length3 += length;
            ConstraintFormula[] constraintFormulaArr2 = new ConstraintFormula[length3];
            this.initialConstraints = constraintFormulaArr2;
            System.arraycopy(constraintFormulaArr, 0, constraintFormulaArr2, 0, length);
            z2 = false;
        }
        int i3 = 0;
        while (i3 < length2) {
            TypeBinding typeBindingSubstitute = substitute(typeBindingArr[i3]);
            if (this.invocationArguments[i3].isPertinentToApplicability(typeBindingArr[i3], methodBinding)) {
                this.initialConstraints[length] = new ConstraintExpressionFormula(this.invocationArguments[i3], typeBindingSubstitute, i2, false);
                length++;
            } else if (!isTypeVariableOfCandidate(typeBindingArr[i3], methodBinding)) {
                this.initialConstraints[length] = new ConstraintExpressionFormula(this.invocationArguments[i3], typeBindingSubstitute, 8);
                length++;
            }
            i3++;
            i2 = 1;
        }
        if (z && (typeBinding instanceof ArrayBinding)) {
            TypeBinding typeBindingElementsType = ((ArrayBinding) typeBinding).elementsType();
            TypeBinding typeBindingSubstitute2 = substitute(typeBindingElementsType);
            while (true) {
                Expression[] expressionArr2 = this.invocationArguments;
                if (length2 >= expressionArr2.length) {
                    break;
                }
                if (expressionArr2[length2].isPertinentToApplicability(typeBindingElementsType, methodBinding)) {
                    i = length + 1;
                    this.initialConstraints[length] = new ConstraintExpressionFormula(this.invocationArguments[length2], typeBindingSubstitute2, i2, false);
                } else {
                    if (!isTypeVariableOfCandidate(typeBindingElementsType, methodBinding)) {
                        i = length + 1;
                        this.initialConstraints[length] = new ConstraintExpressionFormula(this.invocationArguments[length2], typeBindingSubstitute2, 8);
                    }
                    length2++;
                }
                length = i;
                length2++;
            }
        }
        if (length == 0) {
            this.initialConstraints = ConstraintFormula.NO_CONSTRAINTS;
        } else if (length < length3) {
            ConstraintFormula[] constraintFormulaArr3 = this.initialConstraints;
            ConstraintFormula[] constraintFormulaArr4 = new ConstraintFormula[length];
            this.initialConstraints = constraintFormulaArr4;
            System.arraycopy(constraintFormulaArr3, 0, constraintFormulaArr4, 0, length);
        }
        if (z2) {
            ConstraintFormula[] constraintFormulaArr5 = this.initialConstraints;
            int length4 = constraintFormulaArr5.length;
            ConstraintExpressionFormula[] constraintExpressionFormulaArr = new ConstraintExpressionFormula[length4];
            this.finalConstraints = constraintExpressionFormulaArr;
            System.arraycopy(constraintFormulaArr5, 0, constraintExpressionFormulaArr, 0, length4);
        }
    }

    private boolean isTypeVariableOfCandidate(TypeBinding typeBinding, MethodBinding methodBinding) {
        if (!(typeBinding instanceof TypeVariableBinding)) {
            return false;
        }
        Binding binding = ((TypeVariableBinding) typeBinding).declaringElement;
        if (binding == methodBinding) {
            return true;
        }
        return methodBinding.isConstructor() && binding == methodBinding.declaringClass;
    }

    private InferenceVariable[] addInitialTypeVariableSubstitutions(TypeBinding[] typeBindingArr) {
        int length = typeBindingArr.length;
        if (length == 0) {
            if (this.inferenceVariables == null) {
                this.inferenceVariables = Binding.NO_INFERENCE_VARIABLES;
            }
            return Binding.NO_INFERENCE_VARIABLES;
        }
        InferenceVariable[] inferenceVariableArr = new InferenceVariable[length];
        for (int i = 0; i < length; i++) {
            inferenceVariableArr[i] = getInferenceVariable(typeBindingArr[i], i, this.currentInvocation);
        }
        InferenceVariable[] inferenceVariableArr2 = this.inferenceVariables;
        if (inferenceVariableArr2 == null || inferenceVariableArr2.length == 0) {
            this.inferenceVariables = inferenceVariableArr;
        } else {
            int length2 = inferenceVariableArr2.length;
            InferenceVariable[] inferenceVariableArr3 = new InferenceVariable[length + length2];
            this.inferenceVariables = inferenceVariableArr3;
            System.arraycopy(inferenceVariableArr2, 0, inferenceVariableArr3, 0, length2);
            System.arraycopy(inferenceVariableArr, 0, this.inferenceVariables, length2, length);
        }
        return inferenceVariableArr;
    }

    public InferenceVariable[] addTypeVariableSubstitutions(TypeBinding[] typeBindingArr) {
        int length;
        int length2 = typeBindingArr.length;
        InferenceVariable[] inferenceVariableArr = new InferenceVariable[length2];
        InferenceVariable[] inferenceVariableArr2 = new InferenceVariable[length2];
        int i = 0;
        for (int i2 = 0; i2 < typeBindingArr.length; i2++) {
            TypeBinding typeBinding = typeBindingArr[i2];
            if (typeBinding instanceof InferenceVariable) {
                inferenceVariableArr[i2] = (InferenceVariable) typeBinding;
            } else {
                InferenceVariable inferenceVariable = getInferenceVariable(typeBinding, i2, this.currentInvocation);
                inferenceVariableArr[i2] = inferenceVariable;
                inferenceVariableArr2[i] = inferenceVariable;
                i++;
            }
        }
        if (i > 0) {
            InferenceVariable[] inferenceVariableArr3 = this.inferenceVariables;
            if (inferenceVariableArr3 != null) {
                length = inferenceVariableArr3.length;
                InferenceVariable[] inferenceVariableArr4 = new InferenceVariable[length + i];
                this.inferenceVariables = inferenceVariableArr4;
                System.arraycopy(inferenceVariableArr3, 0, inferenceVariableArr4, 0, length);
            } else {
                this.inferenceVariables = new InferenceVariable[i];
                length = 0;
            }
            System.arraycopy(inferenceVariableArr2, 0, this.inferenceVariables, length, i);
        }
        return inferenceVariableArr;
    }

    public void addThrowsContraints(TypeBinding[] typeBindingArr, InferenceVariable[] inferenceVariableArr, ReferenceBinding[] referenceBindingArr) {
        for (int i = 0; i < typeBindingArr.length; i++) {
            TypeBinding typeBinding = typeBindingArr[i];
            for (ReferenceBinding referenceBinding : referenceBindingArr) {
                if (TypeBinding.equalsEquals(typeBinding, referenceBinding)) {
                    this.currentBounds.inThrows.add(inferenceVariableArr[i].prototype());
                    break;
                }
            }
        }
    }

    public void inferInvocationApplicability(MethodBinding methodBinding, TypeBinding[] typeBindingArr, boolean z) {
        ConstraintExpressionFormula.inferInvocationApplicability(this, methodBinding, typeBindingArr, z, this.inferenceKind);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public BoundSet inferInvocationType(TypeBinding typeBinding, InvocationSite invocationSite, MethodBinding methodBinding) throws InferenceFailureException {
        if (typeBinding == null && methodBinding.returnType != null) {
            substitute(methodBinding.returnType);
        }
        this.currentBounds = this.b2.copy();
        if (typeBinding != null) {
            try {
                if (typeBinding != TypeBinding.VOID && (invocationSite instanceof Expression) && ((Expression) invocationSite).isPolyExpression(methodBinding) && !ConstraintExpressionFormula.inferPolyInvocationType(this, invocationSite, typeBinding, methodBinding)) {
                    this.stepCompleted = 2;
                    return null;
                }
            } catch (Throwable th) {
                this.stepCompleted = 2;
                throw th;
            }
        }
        Set<ConstraintFormula> hashSet = new HashSet<>();
        if (!addConstraintsToC(this.invocationArguments, hashSet, methodBinding, this.inferenceKind, false, invocationSite)) {
            this.stepCompleted = 2;
            return null;
        }
        BoundSet boundSetCopy = this.currentBounds.copy();
        Iterator<ConstraintFormula> it = hashSet.iterator();
        while (it.hasNext()) {
            boundSetCopy.reduceOneConstraint(this, it.next());
        }
        List<Set<InferenceVariable>> listComputeConnectedComponents = boundSetCopy.computeConnectedComponents(this.inferenceVariables);
        while (!hashSet.isEmpty()) {
            Set<ConstraintFormula> setFindBottomSet = findBottomSet(hashSet, allOutputVariables(hashSet), listComputeConnectedComponents);
            if (setFindBottomSet.isEmpty()) {
                setFindBottomSet.add(pickFromCycle(hashSet));
            }
            hashSet.removeAll(setFindBottomSet);
            HashSet hashSet2 = new HashSet();
            Iterator<ConstraintFormula> it2 = setFindBottomSet.iterator();
            while (it2.hasNext()) {
                hashSet2.addAll(it2.next().inputVariables(this));
            }
            InferenceVariable[] inferenceVariableArr = (InferenceVariable[]) hashSet2.toArray(new InferenceVariable[hashSet2.size()]);
            if (!this.currentBounds.incorporate(this)) {
                this.stepCompleted = 2;
                return null;
            }
            BoundSet boundSetResolve = resolve(inferenceVariableArr);
            if (boundSetResolve == null) {
                boundSetResolve = resolve(this.inferenceVariables);
            }
            for (ConstraintFormula constraintFormula : setFindBottomSet) {
                if (boundSetResolve != null && !constraintFormula.applySubstitution(boundSetResolve, inferenceVariableArr)) {
                    this.stepCompleted = 2;
                    return null;
                }
                if (!this.currentBounds.reduceOneConstraint(this, constraintFormula)) {
                    this.stepCompleted = 2;
                    return null;
                }
            }
        }
        BoundSet boundSetSolve = solve();
        if (boundSetSolve != null && isResolved(boundSetSolve)) {
            reportUncheckedConversions(boundSetSolve);
            this.currentBounds = boundSetSolve;
            this.stepCompleted = 2;
            return boundSetSolve;
        }
        this.currentBounds = this.b2;
        this.stepCompleted = 2;
        return null;
    }

    private boolean addConstraintsToC(Expression[] expressionArr, Set<ConstraintFormula> set, MethodBinding methodBinding, int i, boolean z, InvocationSite invocationSite) throws InferenceFailureException {
        TypeBinding[] typeBindingArrVarArgTypes;
        if (expressionArr != null) {
            int length = expressionArr.length;
            int length2 = methodBinding.parameters.length;
            if (length < (methodBinding.isVarargs() ? length2 - 1 : length2)) {
                return false;
            }
            if (i == 1 || i == 2) {
                typeBindingArrVarArgTypes = methodBinding.parameters;
            } else if (i == 3) {
                typeBindingArrVarArgTypes = varArgTypes(methodBinding.parameters, length);
            } else {
                throw new IllegalStateException("Unexpected checkKind " + this.inferenceKind);
            }
            TypeBinding[] typeBindingArr = typeBindingArrVarArgTypes;
            for (int i2 = 0; i2 < length; i2++) {
                TypeBinding typeBinding = typeBindingArr[Math.min(i2, length2 - 1)];
                InferenceSubstitution inferenceSubstitution = new InferenceSubstitution(this.environment, this.inferenceVariables, invocationSite);
                if (!addConstraintsToC_OneExpr(expressionArr[i2], set, typeBinding, inferenceSubstitution.substitute(inferenceSubstitution, typeBinding), methodBinding, z)) {
                    return false;
                }
            }
        }
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private boolean addConstraintsToC_OneExpr(Expression expression, Set<ConstraintFormula> set, TypeBinding typeBinding, TypeBinding typeBinding2, MethodBinding methodBinding, boolean z) throws InferenceFailureException {
        Invocation invocation;
        MethodBinding methodBindingBinding;
        MethodBinding singleAbstractMethod;
        LambdaExpression lambdaExpressionResolveExpressionExpecting;
        if (!expression.isPertinentToApplicability(typeBinding, methodBinding)) {
            set.add(new ConstraintExpressionFormula(expression, typeBinding2, 1, false));
        }
        if (expression instanceof FunctionalExpression) {
            set.add(new ConstraintExceptionFormula((FunctionalExpression) expression, typeBinding2));
            if (expression instanceof LambdaExpression) {
                LambdaExpression lambdaExpression = (LambdaExpression) expression;
                BlockScope blockScope = lambdaExpression.enclosingScope;
                if (typeBinding2.isFunctionalInterface(blockScope)) {
                    ReferenceBinding referenceBindingFindGroundTargetType = (ReferenceBinding) typeBinding2;
                    ParameterizedTypeBinding parameterizedTypeBindingParameterizedWithWildcard = parameterizedWithWildcard(referenceBindingFindGroundTargetType);
                    if (parameterizedTypeBindingParameterizedWithWildcard != null) {
                        referenceBindingFindGroundTargetType = ConstraintExpressionFormula.findGroundTargetType(this, blockScope, lambdaExpression, parameterizedTypeBindingParameterizedWithWildcard);
                    }
                    if (!referenceBindingFindGroundTargetType.isProperType(true) && referenceBindingFindGroundTargetType.isParameterizedType()) {
                        referenceBindingFindGroundTargetType = (ReferenceBinding) Scope.substitute(getResultSubstitution(this.currentBounds, false), referenceBindingFindGroundTargetType);
                    }
                    if (referenceBindingFindGroundTargetType != null && (singleAbstractMethod = referenceBindingFindGroundTargetType.getSingleAbstractMethod(blockScope, true)) != null && (lambdaExpressionResolveExpressionExpecting = lambdaExpression.resolveExpressionExpecting((TypeBinding) referenceBindingFindGroundTargetType, this.scope, this)) != null) {
                        TypeBinding typeBinding3 = singleAbstractMethod.returnType;
                        Expression[] expressionArrResultExpressions = lambdaExpressionResolveExpressionExpecting.resultExpressions();
                        int length = expressionArrResultExpressions == null ? 0 : expressionArrResultExpressions.length;
                        for (int i = 0; i < length; i++) {
                            if (!addConstraintsToC_OneExpr(expressionArrResultExpressions[i], set, typeBinding3.original(), typeBinding3, methodBinding, true)) {
                                return false;
                            }
                        }
                    }
                }
            }
        } else {
            if ((expression instanceof Invocation) && expression.isPolyExpression()) {
                if (typeBinding2.isProperType(true) || (methodBindingBinding = (invocation = (Invocation) expression).binding()) == null) {
                    return true;
                }
                Expression[] expressionArrArguments = invocation.arguments();
                TypeBinding[] typeBindingArr = expressionArrArguments == null ? Binding.NO_PARAMETERS : new TypeBinding[expressionArrArguments.length];
                for (int i2 = 0; i2 < typeBindingArr.length; i2++) {
                    typeBindingArr[i2] = expressionArrArguments[i2].resolvedType;
                }
                InferenceContext18 inferenceContext = methodBindingBinding instanceof ParameterizedGenericMethodBinding ? invocation.getInferenceContext((ParameterizedGenericMethodBinding) methodBindingBinding) : null;
                if (z && inferenceContext != null) {
                    MethodBinding methodBindingShallowOriginal = methodBindingBinding.shallowOriginal();
                    inferenceContext.outerContext = this;
                    if (inferenceContext.stepCompleted < 1) {
                        inferenceContext.inferInvocationApplicability(methodBindingShallowOriginal, typeBindingArr, methodBindingShallowOriginal.isConstructor());
                    }
                    if (ConstraintExpressionFormula.inferPolyInvocationType(inferenceContext, invocation, typeBinding2, methodBindingShallowOriginal)) {
                        return inferenceContext.addConstraintsToC(expressionArrArguments, set, methodBindingBinding.genericMethod(), inferenceContext.inferenceKind, z, invocation);
                    }
                    return false;
                }
                return addConstraintsToC(expressionArrArguments, set, methodBindingBinding.genericMethod(), getInferenceKind(methodBindingBinding, typeBindingArr), z, invocation);
            }
            if (expression instanceof ConditionalExpression) {
                ConditionalExpression conditionalExpression = (ConditionalExpression) expression;
                return addConstraintsToC_OneExpr(conditionalExpression.valueIfTrue, set, typeBinding, typeBinding2, methodBinding, z) && addConstraintsToC_OneExpr(conditionalExpression.valueIfFalse, set, typeBinding, typeBinding2, methodBinding, z);
            }
        }
        return true;
    }

    protected int getInferenceKind(MethodBinding methodBinding, TypeBinding[] typeBindingArr) {
        int iParameterCompatibilityLevel = this.scope.parameterCompatibilityLevel(methodBinding, typeBindingArr);
        if (iParameterCompatibilityLevel != 1) {
            return iParameterCompatibilityLevel != 2 ? 1 : 3;
        }
        return 2;
    }

    public ReferenceBinding inferFunctionalInterfaceParameterization(LambdaExpression lambdaExpression, BlockScope blockScope, ParameterizedTypeBinding parameterizedTypeBinding) {
        TypeBinding[] typeBindingArrCreateBoundsForFunctionalInterfaceParameterizationInference = createBoundsForFunctionalInterfaceParameterizationInference(parameterizedTypeBinding);
        if (typeBindingArrCreateBoundsForFunctionalInterfaceParameterizationInference == null || typeBindingArrCreateBoundsForFunctionalInterfaceParameterizationInference.length != lambdaExpression.arguments().length || !reduceWithEqualityConstraints(lambdaExpression.argumentTypes(), typeBindingArrCreateBoundsForFunctionalInterfaceParameterizationInference)) {
            return parameterizedTypeBinding;
        }
        return blockScope.environment().createParameterizedType(parameterizedTypeBinding.genericType(), getFunctionInterfaceArgumentSolutions(parameterizedTypeBinding.arguments), parameterizedTypeBinding.enclosingType());
    }

    TypeBinding[] createBoundsForFunctionalInterfaceParameterizationInference(ParameterizedTypeBinding parameterizedTypeBinding) {
        TypeBound typeBound;
        if (this.currentBounds == null) {
            this.currentBounds = new BoundSet();
        }
        TypeBinding[] typeBindingArr = parameterizedTypeBinding.arguments;
        if (typeBindingArr == null) {
            return null;
        }
        InferenceVariable[] inferenceVariableArrAddInitialTypeVariableSubstitutions = addInitialTypeVariableSubstitutions(typeBindingArr);
        for (int i = 0; i < typeBindingArr.length; i++) {
            if (typeBindingArr[i].kind() == 516) {
                WildcardBinding wildcardBinding = (WildcardBinding) typeBindingArr[i];
                int i2 = wildcardBinding.boundKind;
                if (i2 == 0) {
                    typeBound = new TypeBound(inferenceVariableArrAddInitialTypeVariableSubstitutions[i], this.object, 2);
                } else if (i2 != 1) {
                    if (i2 == 2) {
                        typeBound = new TypeBound(inferenceVariableArrAddInitialTypeVariableSubstitutions[i], wildcardBinding.bound, 3);
                    }
                } else {
                    typeBound = new TypeBound(inferenceVariableArrAddInitialTypeVariableSubstitutions[i], wildcardBinding.allBounds(), 2);
                }
            } else {
                typeBound = new TypeBound(inferenceVariableArrAddInitialTypeVariableSubstitutions[i], typeBindingArr[i], 4);
            }
            this.currentBounds.addBound(typeBound, this.environment);
        }
        return substitute(parameterizedTypeBinding).getSingleAbstractMethod(this.scope, true).parameters;
    }

    public boolean reduceWithEqualityConstraints(TypeBinding[] typeBindingArr, TypeBinding[] typeBindingArr2) {
        if (typeBindingArr == null) {
            return true;
        }
        for (int i = 0; i < typeBindingArr.length; i++) {
            try {
                if (!reduceAndIncorporate(ConstraintTypeFormula.create(typeBindingArr[i], typeBindingArr2[i], 4))) {
                    return false;
                }
            } catch (InferenceFailureException unused) {
                return false;
            }
        }
        return true;
    }

    public boolean isMoreSpecificThan(MethodBinding methodBinding, MethodBinding methodBinding2, boolean z, boolean z2) {
        if (z != z2) {
            return z2;
        }
        Expression[] expressionArr = this.invocationArguments;
        int length = expressionArr == null ? 0 : expressionArr.length;
        TypeVariableBinding[] typeVariableBindingArrTypeVariables = methodBinding2.typeVariables();
        TypeBinding[] typeBindingArr = methodBinding.parameters;
        int length2 = methodBinding2.parameters.length;
        TypeBinding[] typeBindingArr2 = new TypeBinding[length2];
        createInitialBoundSet(typeVariableBindingArrTypeVariables);
        for (int i = 0; i < length2; i++) {
            typeBindingArr2[i] = substitute(methodBinding2.parameters[i]);
        }
        for (int i2 = 0; i2 < length; i2++) {
            try {
                TypeBinding parameter = getParameter(typeBindingArr, i2, z);
                TypeBinding parameter2 = getParameter(typeBindingArr2, i2, z);
                Boolean boolMoreSpecificMain = moreSpecificMain(parameter, parameter2, this.invocationArguments[i2]);
                if (boolMoreSpecificMain == Boolean.FALSE) {
                    return false;
                }
                if (boolMoreSpecificMain == null && !reduceAndIncorporate(ConstraintTypeFormula.create(parameter, parameter2, 2))) {
                    return false;
                }
            } catch (InferenceFailureException unused) {
                return false;
            }
        }
        return (length2 != length + 1 || reduceAndIncorporate(ConstraintTypeFormula.create(getParameter(typeBindingArr, length, true), getParameter(typeBindingArr2, length, true), 2))) && solve() != null;
    }

    private Boolean moreSpecificMain(TypeBinding typeBinding, TypeBinding typeBinding2, Expression expression) throws InferenceFailureException {
        if (typeBinding.isProperType(true) && typeBinding2.isProperType(true)) {
            return expression.sIsMoreSpecific(typeBinding, typeBinding2, this.scope) ? Boolean.TRUE : Boolean.FALSE;
        }
        if (!typeBinding2.isFunctionalInterface(this.scope)) {
            return null;
        }
        TypeBinding typeBindingOriginal = typeBinding2.original();
        if (!typeBinding.isFunctionalInterface(this.scope) || siSuperI(typeBinding, typeBindingOriginal) || siSubI(typeBinding, typeBindingOriginal)) {
            return null;
        }
        if (typeBinding instanceof IntersectionTypeBinding18) {
            ReferenceBinding[] referenceBindingArr = ((IntersectionTypeBinding18) typeBinding).intersectingTypes;
            for (ReferenceBinding referenceBinding : referenceBindingArr) {
                if (!siSuperI(referenceBinding, typeBindingOriginal)) {
                    for (ReferenceBinding referenceBinding2 : referenceBindingArr) {
                        if (siSubI(referenceBinding2, typeBindingOriginal)) {
                            return null;
                        }
                    }
                }
            }
            return null;
        }
        MethodBinding singleAbstractMethod = typeBinding.capture(this.scope, expression.sourceStart, expression.sourceEnd).getSingleAbstractMethod(this.scope, false);
        TypeBinding[] typeBindingArr = singleAbstractMethod.parameters;
        TypeBinding typeBinding3 = singleAbstractMethod.isConstructor() ? singleAbstractMethod.declaringClass : singleAbstractMethod.returnType;
        MethodBinding singleAbstractMethod2 = typeBinding2.getSingleAbstractMethod(this.scope, true);
        return Boolean.valueOf(checkExpression(expression, typeBindingArr, typeBinding3, singleAbstractMethod2.parameters, singleAbstractMethod2.isConstructor() ? singleAbstractMethod2.declaringClass : singleAbstractMethod2.returnType));
    }

    private boolean checkExpression(Expression expression, TypeBinding[] typeBindingArr, TypeBinding typeBinding, TypeBinding[] typeBindingArr2, TypeBinding typeBinding2) throws InferenceFailureException {
        if (expression instanceof LambdaExpression) {
            LambdaExpression lambdaExpression = (LambdaExpression) expression;
            if (!lambdaExpression.argumentsTypeElided()) {
                for (int i = 0; i < typeBindingArr.length; i++) {
                    if (!reduceAndIncorporate(ConstraintTypeFormula.create(typeBindingArr[i], typeBindingArr2[i], 4))) {
                        return false;
                    }
                }
                if (typeBinding2.f626id == 6) {
                    return true;
                }
                Expression[] expressionArrResultExpressions = lambdaExpression.resultExpressions();
                if (expressionArrResultExpressions != Expression.NO_EXPRESSIONS) {
                    if (typeBinding.isFunctionalInterface(this.scope) && typeBinding2.isFunctionalInterface(this.scope) && !typeBinding.isCompatibleWith(typeBinding2) && !typeBinding2.isCompatibleWith(typeBinding)) {
                        for (Expression expression2 : expressionArrResultExpressions) {
                            if (!checkExpression(expression2, typeBindingArr, typeBinding, typeBindingArr2, typeBinding2)) {
                                return false;
                            }
                        }
                        return true;
                    }
                    if (typeBinding.isPrimitiveType() && !typeBinding2.isPrimitiveType()) {
                        for (int i2 = 0; i2 < expressionArrResultExpressions.length; i2++) {
                            if (!expressionArrResultExpressions[i2].isPolyExpression() && (expressionArrResultExpressions[i2].resolvedType == null || expressionArrResultExpressions[i2].resolvedType.isPrimitiveType())) {
                            }
                        }
                        return true;
                    }
                    if (typeBinding2.isPrimitiveType() && !typeBinding.isPrimitiveType()) {
                        for (int i3 = 0; i3 < expressionArrResultExpressions.length; i3++) {
                            if ((!expressionArrResultExpressions[i3].isPolyExpression() && expressionArrResultExpressions[i3].resolvedType != null && !expressionArrResultExpressions[i3].resolvedType.isPrimitiveType()) || expressionArrResultExpressions[i3].isPolyExpression()) {
                            }
                        }
                        return true;
                    }
                }
                return reduceAndIncorporate(ConstraintTypeFormula.create(typeBinding, typeBinding2, 2));
            }
        }
        if (expression instanceof ReferenceExpression) {
            ReferenceExpression referenceExpression = (ReferenceExpression) expression;
            if (referenceExpression.isExactMethodReference()) {
                for (int i4 = 0; i4 < typeBindingArr.length; i4++) {
                    if (!reduceAndIncorporate(ConstraintTypeFormula.create(typeBindingArr[i4], typeBindingArr2[i4], 4))) {
                        return false;
                    }
                }
                if (typeBinding2.f626id == 6) {
                    return true;
                }
                MethodBinding exactMethod = referenceExpression.getExactMethod();
                TypeBinding typeBinding3 = exactMethod.isConstructor() ? exactMethod.declaringClass : exactMethod.returnType;
                if (typeBinding.isPrimitiveType() && !typeBinding2.isPrimitiveType() && typeBinding3.isPrimitiveType()) {
                    return true;
                }
                if (!typeBinding2.isPrimitiveType() || typeBinding.isPrimitiveType() || typeBinding3.isPrimitiveType()) {
                    return reduceAndIncorporate(ConstraintTypeFormula.create(typeBinding, typeBinding2, 2));
                }
                return true;
            }
        }
        if (expression instanceof ConditionalExpression) {
            ConditionalExpression conditionalExpression = (ConditionalExpression) expression;
            if (checkExpression(conditionalExpression.valueIfTrue, typeBindingArr, typeBinding, typeBindingArr2, typeBinding2) && checkExpression(conditionalExpression.valueIfFalse, typeBindingArr, typeBinding, typeBindingArr2, typeBinding2)) {
                return true;
            }
        }
        return false;
    }

    private boolean siSuperI(TypeBinding typeBinding, TypeBinding typeBinding2) {
        if (TypeBinding.equalsEquals(typeBinding, typeBinding2) || TypeBinding.equalsEquals(typeBinding.original(), typeBinding2)) {
            return true;
        }
        ReferenceBinding[] referenceBindingArrSuperInterfaces = typeBinding2.superInterfaces();
        if (referenceBindingArrSuperInterfaces == null) {
            return false;
        }
        for (ReferenceBinding referenceBinding : referenceBindingArrSuperInterfaces) {
            if (siSuperI(typeBinding, referenceBinding.original())) {
                return true;
            }
        }
        return false;
    }

    private boolean siSubI(TypeBinding typeBinding, TypeBinding typeBinding2) {
        if (TypeBinding.equalsEquals(typeBinding, typeBinding2) || TypeBinding.equalsEquals(typeBinding.original(), typeBinding2)) {
            return true;
        }
        ReferenceBinding[] referenceBindingArrSuperInterfaces = typeBinding.superInterfaces();
        if (referenceBindingArrSuperInterfaces == null) {
            return false;
        }
        for (ReferenceBinding referenceBinding : referenceBindingArrSuperInterfaces) {
            if (siSubI(referenceBinding, typeBinding2)) {
                return true;
            }
        }
        return false;
    }

    public BoundSet solve(boolean z) throws InferenceFailureException {
        ConstraintExpressionFormula[] constraintExpressionFormulaArr;
        if (!reduce() || !this.currentBounds.incorporate(this)) {
            return null;
        }
        if (z) {
            this.b2 = this.currentBounds.copy();
        }
        BoundSet boundSetResolve = resolve(this.inferenceVariables);
        if (z && boundSetResolve != null && (constraintExpressionFormulaArr = this.finalConstraints) != null) {
            for (ConstraintExpressionFormula constraintExpressionFormula : constraintExpressionFormulaArr) {
                if (!constraintExpressionFormula.left.isPolyExpression()) {
                    constraintExpressionFormula.applySubstitution(boundSetResolve, this.inferenceVariables);
                    if (!this.currentBounds.reduceOneConstraint(this, constraintExpressionFormula)) {
                        return null;
                    }
                }
            }
        }
        return boundSetResolve;
    }

    public BoundSet solve() throws InferenceFailureException {
        return solve(false);
    }

    public BoundSet solve(InferenceVariable[] inferenceVariableArr) throws InferenceFailureException {
        if (reduce() && this.currentBounds.incorporate(this)) {
            return resolve(inferenceVariableArr);
        }
        return null;
    }

    private boolean reduce() throws InferenceFailureException {
        int i = 0;
        while (true) {
            ConstraintFormula[] constraintFormulaArr = this.initialConstraints;
            if (constraintFormulaArr == null || i >= constraintFormulaArr.length) {
                break;
            }
            ConstraintFormula constraintFormula = constraintFormulaArr[i];
            if (constraintFormula != null) {
                constraintFormulaArr[i] = null;
                if (!this.currentBounds.reduceOneConstraint(this, constraintFormula)) {
                    return false;
                }
            }
            i++;
        }
        this.initialConstraints = null;
        return true;
    }

    public boolean isResolved(BoundSet boundSet) {
        if (this.inferenceVariables == null) {
            return true;
        }
        int i = 0;
        while (true) {
            InferenceVariable[] inferenceVariableArr = this.inferenceVariables;
            if (i >= inferenceVariableArr.length) {
                return true;
            }
            if (!boundSet.isInstantiated(inferenceVariableArr[i])) {
                return false;
            }
            i++;
        }
    }

    public TypeBinding[] getSolutions(TypeVariableBinding[] typeVariableBindingArr, InvocationSite invocationSite, BoundSet boundSet) {
        TypeBinding equivalentOuterVariable;
        TypeBinding[] typeBindingArr = new TypeBinding[typeVariableBindingArr.length];
        InferenceContext18 inferenceContext18 = this.outerContext;
        InferenceVariable[] inferenceVariableArr = (inferenceContext18 == null || inferenceContext18.stepCompleted >= 2) ? null : inferenceContext18.inferenceVariables;
        for (int i = 0; i < typeVariableBindingArr.length; i++) {
            int i2 = 0;
            while (true) {
                InferenceVariable[] inferenceVariableArr2 = this.inferenceVariables;
                if (i2 >= inferenceVariableArr2.length) {
                    break;
                }
                InferenceVariable inferenceVariable = inferenceVariableArr2[i2];
                if (isSameSite(inferenceVariable.site, invocationSite) && TypeBinding.equalsEquals(inferenceVariable.typeParameter, typeVariableBindingArr[i])) {
                    if (inferenceVariableArr != null && (equivalentOuterVariable = boundSet.getEquivalentOuterVariable(inferenceVariable, inferenceVariableArr)) != null) {
                        typeBindingArr[i] = equivalentOuterVariable;
                        break;
                    }
                    typeBindingArr[i] = boundSet.getInstantiation(inferenceVariable, this.environment);
                    break;
                }
                i2++;
            }
            if (typeBindingArr[i] == null) {
                return null;
            }
        }
        return typeBindingArr;
    }

    public boolean reduceAndIncorporate(ConstraintFormula constraintFormula) throws InferenceFailureException {
        return this.currentBounds.reduceOneConstraint(this, constraintFormula);
    }

    /* JADX WARN: Code duplicated, block: B:115:0x01a6 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:117:0x0199 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:73:0x0148  */
    /* JADX WARN: Code duplicated, block: B:75:0x014c  */
    /* JADX WARN: Code duplicated, block: B:84:0x0180 A[LOOP:8: B:80:0x016a->B:84:0x0180, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:85:0x018b  */
    /* JADX WARN: Code duplicated, block: B:88:0x019c  */
    /* JADX WARN: Code duplicated, block: B:91:0x01ad A[LOOP:7: B:86:0x0197->B:91:0x01ad, LOOP_END] */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v17 */
    /* JADX WARN: Type inference failed for: r1v5 */
    /* JADX WARN: Type inference failed for: r1v6, types: [int] */
    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    private BoundSet resolve(InferenceVariable[] inferenceVariableArr) throws InferenceFailureException {
        BoundSet boundSetCopy;
        BoundSet boundSet;
        HashSet hashSet;
        Iterator it;
        int length;
        int i;
        TypeBinding typeBindingLowerUpperBound;
        IntersectionTypeBinding18 intersectionTypeBinding18;
        boolean z = false;
        this.captureId = 0;
        BoundSet boundSetCopy2 = this.currentBounds;
        if (this.inferenceVariables == null) {
            return boundSetCopy2;
        }
        while (true) {
            BoundSet boundSet2 = boundSetCopy2;
            while (true) {
                Set<InferenceVariable> smallestVariableSet = getSmallestVariableSet(boundSet2, inferenceVariableArr);
                if (smallestVariableSet == null) {
                    return boundSet2;
                }
                int iNumUninstantiatedVariables = boundSet2.numUninstantiatedVariables(this.inferenceVariables);
                final int size = smallestVariableSet.size();
                if (size > 0) {
                    final InferenceVariable[] inferenceVariableArr2 = (InferenceVariable[]) smallestVariableSet.toArray(new InferenceVariable[size]);
                    BoundSet boundSet3 = null;
                    boolean z2 = true;
                    if (!boundSet2.hasCaptureBound(smallestVariableSet)) {
                        boundSetCopy2 = boundSet2.copy();
                        int i2 = z ? 1 : 0;
                        while (true) {
                            if (i2 < inferenceVariableArr2.length) {
                                InferenceVariable inferenceVariable = inferenceVariableArr2[i2];
                                TypeBinding[] typeBindingArrLowerBounds = boundSetCopy2.lowerBounds(inferenceVariable, true);
                                if (typeBindingArrLowerBounds != Binding.NO_TYPES) {
                                    TypeBinding typeBindingLowerUpperBound2 = this.scope.lowerUpperBound(typeBindingArrLowerBounds);
                                    if (typeBindingLowerUpperBound2 == TypeBinding.VOID || typeBindingLowerUpperBound2 == null) {
                                        return null;
                                    }
                                    boundSetCopy2.addBound(new TypeBound(inferenceVariable, typeBindingLowerUpperBound2, 4), this.environment);
                                } else {
                                    TypeBinding[] typeBindingArrUpperBounds = boundSetCopy2.upperBounds(inferenceVariable, true);
                                    if (boundSetCopy2.inThrows.contains(inferenceVariable.prototype()) && boundSetCopy2.hasOnlyTrivialExceptionBounds(inferenceVariable, typeBindingArrUpperBounds)) {
                                        boundSetCopy2.addBound(new TypeBound(inferenceVariable, this.scope.getType(TypeConstants.JAVA_LANG_RUNTIMEEXCEPTION, 3), 4), this.environment);
                                    } else {
                                        TypeBinding typeBinding = this.object;
                                        if (typeBindingArrUpperBounds != Binding.NO_TYPES) {
                                            if (typeBindingArrUpperBounds.length == 1) {
                                                typeBinding = typeBindingArrUpperBounds[z ? 1 : 0];
                                            } else {
                                                ReferenceBinding[] referenceBindingArrGreaterLowerBound = Scope.greaterLowerBound((ReferenceBinding[]) typeBindingArrUpperBounds);
                                                if (referenceBindingArrGreaterLowerBound == null) {
                                                    throw new UnsupportedOperationException("no glb for " + Arrays.asList(typeBindingArrUpperBounds));
                                                }
                                                if (referenceBindingArrGreaterLowerBound.length == 1) {
                                                    typeBinding = referenceBindingArrGreaterLowerBound[z ? 1 : 0];
                                                } else {
                                                    intersectionTypeBinding18 = (IntersectionTypeBinding18) this.environment.createIntersectionType18(referenceBindingArrGreaterLowerBound);
                                                    if (!ReferenceBinding.isConsistentIntersection(intersectionTypeBinding18.intersectingTypes)) {
                                                        typeBinding = intersectionTypeBinding18;
                                                    }
                                                }
                                            }
                                        }
                                        typeBinding = intersectionTypeBinding18;
                                        boundSetCopy2.addBound(new TypeBound(inferenceVariable, typeBinding, 4), this.environment);
                                    }
                                }
                                i2++;
                            } else if (boundSetCopy2.incorporate(this)) {
                                break;
                            }
                        }
                    }
                    Sorting.sortInferenceVariables(inferenceVariableArr2);
                    final CaptureBinding18[] captureBinding18Arr = new CaptureBinding18[size];
                    int i3 = z ? 1 : 0;
                    while (i3 < size) {
                        captureBinding18Arr[i3] = freshCapture(inferenceVariableArr2[i3]);
                        i3++;
                        z = false;
                        boundSet3 = null;
                        z2 = true;
                    }
                    final BoundSet boundSet4 = boundSet2;
                    Substitution substitution = new Substitution() { // from class: org.eclipse.jdt.internal.compiler.lookup.InferenceContext18.1
                        @Override // org.eclipse.jdt.internal.compiler.lookup.Substitution
                        public boolean isRawSubstitution() {
                            return false;
                        }

                        @Override // org.eclipse.jdt.internal.compiler.lookup.Substitution
                        public LookupEnvironment environment() {
                            return InferenceContext18.this.environment;
                        }

                        @Override // org.eclipse.jdt.internal.compiler.lookup.Substitution
                        public TypeBinding substitute(TypeVariableBinding typeVariableBinding) {
                            for (int i4 = 0; i4 < size; i4++) {
                                if (TypeBinding.equalsEquals(inferenceVariableArr2[i4], typeVariableBinding)) {
                                    return captureBinding18Arr[i4];
                                }
                            }
                            if (typeVariableBinding instanceof InferenceVariable) {
                                TypeBinding instantiation = boundSet4.getInstantiation((InferenceVariable) typeVariableBinding, null);
                                if (instantiation != null) {
                                    return instantiation;
                                }
                            }
                            return typeVariableBinding;
                        }
                    };
                    ?? r1 = z;
                    boundSet2 = boundSet2;
                    while (r1 < size) {
                        InferenceVariable inferenceVariable2 = inferenceVariableArr2[r1];
                        CaptureBinding18 captureBinding18 = captureBinding18Arr[r1];
                        TypeBinding[] typeBindingArrLowerBounds2 = boundSet2.lowerBounds(inferenceVariable2, z2);
                        if (typeBindingArrLowerBounds2 != Binding.NO_TYPES && (typeBindingLowerUpperBound = this.scope.lowerUpperBound(typeBindingArrLowerBounds2)) != TypeBinding.VOID && typeBindingLowerUpperBound != null) {
                            captureBinding18.lowerBound = typeBindingLowerUpperBound;
                        }
                        TypeBinding[] typeBindingArrUpperBounds2 = boundSet2.upperBounds(inferenceVariable2, z);
                        if (typeBindingArrUpperBounds2 != Binding.NO_TYPES) {
                            for (int i4 = z ? 1 : 0; i4 < typeBindingArrUpperBounds2.length; i4++) {
                                typeBindingArrUpperBounds2[i4] = Scope.substitute(substitution, typeBindingArrUpperBounds2[i4]);
                            }
                            if (setUpperBounds(captureBinding18, typeBindingArrUpperBounds2)) {
                                boundSetCopy = boundSet2;
                                if (boundSet2 == this.currentBounds) {
                                    boundSetCopy = boundSet2.copy();
                                }
                                boundSet = boundSetCopy;
                                hashSet = new HashSet();
                                for (ParameterizedTypeBinding parameterizedTypeBinding : boundSet.captures.keySet()) {
                                    length = parameterizedTypeBinding.arguments.length;
                                    i = 0;
                                    while (true) {
                                        if (i >= length) {
                                            z = false;
                                            boundSet3 = null;
                                            break;
                                        }
                                        if (TypeBinding.equalsEquals(parameterizedTypeBinding.arguments[i], inferenceVariable2)) {
                                            hashSet.add(parameterizedTypeBinding);
                                            z = false;
                                            boundSet3 = null;
                                            z2 = true;
                                            break;
                                        }
                                        i++;
                                        z2 = true;
                                    }
                                }
                                it = hashSet.iterator();
                                while (it.hasNext()) {
                                    boundSet.captures.remove(it.next());
                                }
                                boundSet.addBound(new TypeBound(inferenceVariable2, captureBinding18, 4), this.environment);
                                boundSet2 = boundSet;
                            }
                        } else {
                            boundSetCopy = boundSet2;
                            if (boundSet2 == this.currentBounds) {
                                boundSetCopy = boundSet2.copy();
                            }
                            boundSet = boundSetCopy;
                            hashSet = new HashSet();
                            while (r5.hasNext()) {
                                length = parameterizedTypeBinding.arguments.length;
                                i = 0;
                                while (true) {
                                    if (i >= length) {
                                        z = false;
                                        boundSet3 = null;
                                        break;
                                        break;
                                    }
                                    if (TypeBinding.equalsEquals(parameterizedTypeBinding.arguments[i], inferenceVariable2)) {
                                        hashSet.add(parameterizedTypeBinding);
                                        z = false;
                                        boundSet3 = null;
                                        z2 = true;
                                        break;
                                        break;
                                    }
                                    i++;
                                    z2 = true;
                                }
                            }
                            it = hashSet.iterator();
                            while (it.hasNext()) {
                                boundSet.captures.remove(it.next());
                            }
                            boundSet.addBound(new TypeBound(inferenceVariable2, captureBinding18, 4), this.environment);
                            boundSet2 = boundSet;
                        }
                        r1++;
                        boundSet2 = boundSet2;
                    }
                    if (!boundSet2.incorporate(this) || boundSet2.numUninstantiatedVariables(this.inferenceVariables) == iNumUninstantiatedVariables) {
                        return boundSet3;
                    }
                }
            }
        }
    }

    private CaptureBinding18 freshCapture(InferenceVariable inferenceVariable) {
        int i = this.captureId;
        this.captureId = i + 1;
        char[] cArrConcat = CharOperation.concat("Z".toCharArray(), '#', String.valueOf(i).toCharArray(), '-', inferenceVariable.sourceName);
        InvocationSite invocationSite = this.currentInvocation;
        int iSourceStart = invocationSite != null ? invocationSite.sourceStart() : 0;
        InvocationSite invocationSite2 = this.currentInvocation;
        return new CaptureBinding18(this.scope.enclosingSourceType(), cArrConcat, inferenceVariable.typeParameter.shortReadableName(), iSourceStart, invocationSite2 != null ? invocationSite2.sourceEnd() : 0, i, this.environment);
    }

    private boolean setUpperBounds(CaptureBinding18 captureBinding18, TypeBinding[] typeBindingArr) {
        if (typeBindingArr.length == 1) {
            return captureBinding18.setUpperBounds(typeBindingArr, this.object);
        }
        TypeBinding[] typeBindingArrGreaterLowerBound = Scope.greaterLowerBound(typeBindingArr, this.scope, this.environment);
        if (typeBindingArrGreaterLowerBound == null) {
            return false;
        }
        if (captureBinding18.lowerBound != null) {
            for (TypeBinding typeBinding : typeBindingArrGreaterLowerBound) {
                if (!captureBinding18.lowerBound.isCompatibleWith(typeBinding)) {
                    return false;
                }
            }
        }
        sortTypes(typeBindingArrGreaterLowerBound);
        return captureBinding18.setUpperBounds(typeBindingArrGreaterLowerBound, this.object);
    }

    static void sortTypes(TypeBinding[] typeBindingArr) {
        Arrays.sort(typeBindingArr, new Comparator<TypeBinding>() { // from class: org.eclipse.jdt.internal.compiler.lookup.InferenceContext18.2
            @Override // java.util.Comparator
            public int compare(TypeBinding typeBinding, TypeBinding typeBinding2) {
                int i = typeBinding.f626id;
                int i2 = typeBinding2.f626id;
                if (i < i2) {
                    return -1;
                }
                return i == i2 ? 0 : 1;
            }
        });
    }

    private Set<InferenceVariable> getSmallestVariableSet(BoundSet boundSet, InferenceVariable[] inferenceVariableArr) {
        HashSet<InferenceVariable> hashSet = new HashSet();
        HashMap map = new HashMap();
        for (InferenceVariable inferenceVariable : inferenceVariableArr) {
            HashSet hashSet2 = new HashSet();
            addDependencies(boundSet, hashSet2, inferenceVariable);
            map.put(inferenceVariable, hashSet2);
            hashSet.addAll(hashSet2);
        }
        int i = Integer.MAX_VALUE;
        Set<InferenceVariable> set = null;
        for (InferenceVariable inferenceVariable2 : hashSet) {
            if (!boundSet.isInstantiated(inferenceVariable2)) {
                Set<InferenceVariable> hashSet3 = (Set) map.get(inferenceVariable2);
                if (hashSet3 == null) {
                    hashSet3 = new HashSet<>();
                    addDependencies(boundSet, hashSet3, inferenceVariable2);
                }
                int size = hashSet3.size();
                if (size == 1) {
                    return hashSet3;
                }
                if (size < i) {
                    i = size;
                    set = hashSet3;
                }
            }
        }
        return set;
    }

    private void addDependencies(BoundSet boundSet, Set<InferenceVariable> set, InferenceVariable inferenceVariable) {
        if (boundSet.isInstantiated(inferenceVariable) || !set.add(inferenceVariable)) {
            return;
        }
        int i = 0;
        while (true) {
            InferenceVariable[] inferenceVariableArr = this.inferenceVariables;
            if (i >= inferenceVariableArr.length) {
                return;
            }
            InferenceVariable inferenceVariable2 = inferenceVariableArr[i];
            if (!TypeBinding.equalsEquals(inferenceVariable2, inferenceVariable) && boundSet.dependsOnResolutionOf(inferenceVariable, inferenceVariable2)) {
                addDependencies(boundSet, set, inferenceVariable2);
            }
            i++;
        }
    }

    private ConstraintFormula pickFromCycle(Set<ConstraintFormula> set) {
        ConstraintExpressionFormula constraintExpressionFormula;
        HashMap map = new HashMap();
        HashSet<ConstraintFormula> hashSet = new HashSet();
        for (ConstraintFormula constraintFormula : set) {
            Collection<InferenceVariable> collectionInputVariables = constraintFormula.inputVariables(this);
            for (ConstraintFormula constraintFormula2 : set) {
                if (constraintFormula2 != constraintFormula && dependsOn(collectionInputVariables, constraintFormula2.outputVariables(this))) {
                    Set hashSet2 = (Set) map.get(constraintFormula);
                    if (hashSet2 == null) {
                        hashSet2 = new HashSet();
                        map.put(constraintFormula, hashSet2);
                    }
                    hashSet2.add(constraintFormula2);
                    HashSet hashSet3 = new HashSet();
                    if (isReachable(map, constraintFormula2, constraintFormula, new HashSet<>(), hashSet3)) {
                        hashSet.addAll(hashSet3);
                    }
                }
            }
        }
        HashSet hashSet4 = new HashSet(set);
        hashSet4.removeAll(hashSet);
        HashSet hashSet5 = new HashSet();
        for (ConstraintFormula constraintFormula3 : hashSet) {
            Collection<InferenceVariable> collectionInputVariables2 = constraintFormula3.inputVariables(this);
            Iterator it = hashSet4.iterator();
            do {
                if (!it.hasNext()) {
                    hashSet5.add(constraintFormula3);
                    break;
                }
            } while (!dependsOn(collectionInputVariables2, ((ConstraintFormula) it.next()).outputVariables(this)));
        }
        Set<ConstraintFormula> set2 = hashSet5.isEmpty() ? set : hashSet5;
        HashSet hashSet6 = new HashSet();
        for (ConstraintFormula constraintFormula4 : set2) {
            if (constraintFormula4 instanceof ConstraintExpressionFormula) {
                hashSet6.add(constraintFormula4);
            }
        }
        if (!hashSet6.isEmpty()) {
            Map<ConstraintExpressionFormula, ConstraintExpressionFormula> map2 = new HashMap<>();
            for (ConstraintFormula constraintFormula5 : hashSet6) {
                ConstraintExpressionFormula constraintExpressionFormula2 = (ConstraintExpressionFormula) constraintFormula5;
                Expression expression = constraintExpressionFormula2.left;
                for (ConstraintFormula constraintFormula6 : hashSet6) {
                    if (constraintFormula5 != constraintFormula6) {
                        ConstraintExpressionFormula constraintExpressionFormula3 = (ConstraintExpressionFormula) constraintFormula6;
                        if (doesExpressionContain(expression, constraintExpressionFormula3.left) && ((constraintExpressionFormula = map2.get(constraintFormula6)) == null || doesExpressionContain(constraintExpressionFormula.left, expression))) {
                            map2.put(constraintExpressionFormula3, constraintExpressionFormula2);
                        }
                    }
                }
            }
            Map<ConstraintExpressionFormula, Set<ConstraintExpressionFormula>> map3 = new HashMap<>();
            for (Map.Entry<ConstraintExpressionFormula, ConstraintExpressionFormula> entry : map2.entrySet()) {
                ConstraintExpressionFormula value = entry.getValue();
                Set<ConstraintExpressionFormula> hashSet7 = map3.get(value);
                if (hashSet7 == null) {
                    hashSet7 = new HashSet<>();
                    map3.put(value, hashSet7);
                }
                hashSet7.add(entry.getKey());
            }
            int i = -1;
            ConstraintExpressionFormula constraintExpressionFormula4 = null;
            for (ConstraintExpressionFormula constraintExpressionFormula5 : map3.keySet()) {
                int iRankNode = rankNode(constraintExpressionFormula5, map2, map3);
                if (iRankNode > i) {
                    constraintExpressionFormula4 = constraintExpressionFormula5;
                    i = iRankNode;
                }
            }
            if (constraintExpressionFormula4 != null) {
                return constraintExpressionFormula4;
            }
            set2 = hashSet6;
        }
        if (set2.isEmpty()) {
            throw new IllegalStateException("cannot pick constraint from cyclic set");
        }
        return set2.iterator().next();
    }

    private boolean dependsOn(Collection<InferenceVariable> collection, Collection<InferenceVariable> collection2) {
        for (InferenceVariable inferenceVariable : collection) {
            Iterator<InferenceVariable> it = collection2.iterator();
            while (it.hasNext()) {
                if (this.currentBounds.dependsOnResolutionOf(inferenceVariable, it.next())) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isReachable(Map<ConstraintFormula, Set<ConstraintFormula>> map, ConstraintFormula constraintFormula, ConstraintFormula constraintFormula2, Set<ConstraintFormula> set, Set<ConstraintFormula> set2) {
        Set<ConstraintFormula> set3;
        if (constraintFormula == constraintFormula2) {
            set2.add(constraintFormula);
            return true;
        }
        if (set.add(constraintFormula) && (set3 = map.get(constraintFormula)) != null) {
            Iterator<ConstraintFormula> it = set3.iterator();
            while (it.hasNext()) {
                if (isReachable(map, it.next(), constraintFormula2, set, set2)) {
                    set2.add(constraintFormula);
                    return true;
                }
            }
        }
        return false;
    }

    private boolean doesExpressionContain(Expression expression, Expression expression2) {
        if (expression2.sourceStart > expression.sourceStart) {
            return expression2.sourceEnd <= expression.sourceEnd;
        }
        return expression2.sourceStart == expression.sourceStart && expression2.sourceEnd < expression.sourceEnd;
    }

    private int rankNode(ConstraintExpressionFormula constraintExpressionFormula, Map<ConstraintExpressionFormula, ConstraintExpressionFormula> map, Map<ConstraintExpressionFormula, Set<ConstraintExpressionFormula>> map2) {
        if (map.get(constraintExpressionFormula) != null) {
            return -1;
        }
        Set<ConstraintExpressionFormula> set = map2.get(constraintExpressionFormula);
        int i = 1;
        if (set == null) {
            return 1;
        }
        Iterator<ConstraintExpressionFormula> it = set.iterator();
        while (it.hasNext()) {
            int iRankNode = rankNode(it.next(), map, map2);
            if (iRankNode > 0) {
                i += iRankNode;
            }
        }
        return i;
    }

    private Set<ConstraintFormula> findBottomSet(Set<ConstraintFormula> set, Set<InferenceVariable> set2, List<Set<InferenceVariable>> list) {
        HashSet hashSet = new HashSet();
        for (ConstraintFormula constraintFormula : set) {
            Iterator<InferenceVariable> it = constraintFormula.inputVariables(this).iterator();
            do {
                if (!it.hasNext()) {
                    hashSet.add(constraintFormula);
                    break;
                }
            } while (!canInfluenceAnyOf(it.next(), set2, list));
        }
        return hashSet;
    }

    private boolean canInfluenceAnyOf(InferenceVariable inferenceVariable, Set<InferenceVariable> set, List<Set<InferenceVariable>> list) {
        for (Set<InferenceVariable> set2 : list) {
            if (set2.contains(inferenceVariable)) {
                Iterator<InferenceVariable> it = set.iterator();
                while (it.hasNext()) {
                    if (set2.contains(it.next())) {
                        return true;
                    }
                }
                return false;
            }
        }
        return false;
    }

    Set<InferenceVariable> allOutputVariables(Set<ConstraintFormula> set) {
        HashSet hashSet = new HashSet();
        Iterator<ConstraintFormula> it = set.iterator();
        while (it.hasNext()) {
            hashSet.addAll(it.next().outputVariables(this));
        }
        return hashSet;
    }

    private TypeBinding[] varArgTypes(TypeBinding[] typeBindingArr, int i) {
        TypeBinding[] typeBindingArr2 = new TypeBinding[i];
        int length = typeBindingArr.length - 1;
        System.arraycopy(typeBindingArr, 0, typeBindingArr2, 0, length);
        TypeBinding typeBindingElementsType = ((ArrayBinding) typeBindingArr[length]).elementsType();
        while (length < i) {
            typeBindingArr2[length] = typeBindingElementsType;
            length++;
        }
        return typeBindingArr2;
    }

    public SuspendedInferenceRecord enterPolyInvocation(InvocationSite invocationSite, Expression[] expressionArr) {
        SuspendedInferenceRecord suspendedInferenceRecord = new SuspendedInferenceRecord(this.currentInvocation, this.invocationArguments, this.inferenceVariables, this.inferenceKind, this.usesUncheckedConversion);
        this.inferenceVariables = null;
        this.invocationArguments = expressionArr;
        this.currentInvocation = invocationSite;
        this.usesUncheckedConversion = false;
        return suspendedInferenceRecord;
    }

    public SuspendedInferenceRecord enterLambda(LambdaExpression lambdaExpression) {
        SuspendedInferenceRecord suspendedInferenceRecord = new SuspendedInferenceRecord(this.currentInvocation, this.invocationArguments, this.inferenceVariables, this.inferenceKind, this.usesUncheckedConversion);
        this.inferenceVariables = null;
        this.invocationArguments = null;
        this.currentInvocation = null;
        this.usesUncheckedConversion = false;
        return suspendedInferenceRecord;
    }

    public void integrateInnerInferenceB2(InferenceContext18 inferenceContext18) {
        this.currentBounds.addBounds(inferenceContext18.b2, this.environment);
        InferenceVariable[] inferenceVariableArr = inferenceContext18.inferenceVariables;
        this.inferenceVariables = inferenceVariableArr;
        this.inferenceKind = inferenceContext18.inferenceKind;
        inferenceContext18.outerContext = this;
        this.usesUncheckedConversion = inferenceContext18.usesUncheckedConversion;
        for (InferenceVariable inferenceVariable : inferenceVariableArr) {
            int i = this.nextVarId;
            this.nextVarId = i + 1;
            inferenceVariable.updateSourceName(i);
        }
    }

    public void resumeSuspendedInference(SuspendedInferenceRecord suspendedInferenceRecord) {
        InferenceVariable[] inferenceVariableArr = this.inferenceVariables;
        if (inferenceVariableArr == null) {
            this.inferenceVariables = suspendedInferenceRecord.inferenceVariables;
        } else {
            int length = inferenceVariableArr.length;
            int length2 = suspendedInferenceRecord.inferenceVariables.length;
            InferenceVariable[] inferenceVariableArr2 = this.inferenceVariables;
            InferenceVariable[] inferenceVariableArr3 = new InferenceVariable[length + length2];
            this.inferenceVariables = inferenceVariableArr3;
            System.arraycopy(inferenceVariableArr2, 0, inferenceVariableArr3, length2, length);
            System.arraycopy(suspendedInferenceRecord.inferenceVariables, 0, this.inferenceVariables, 0, length2);
        }
        this.currentInvocation = suspendedInferenceRecord.site;
        this.invocationArguments = suspendedInferenceRecord.invocationArguments;
        this.inferenceKind = suspendedInferenceRecord.inferenceKind;
        this.usesUncheckedConversion = suspendedInferenceRecord.usesUncheckedConversion;
    }

    private Substitution getResultSubstitution(final BoundSet boundSet, final boolean z) {
        return new Substitution() { // from class: org.eclipse.jdt.internal.compiler.lookup.InferenceContext18.3
            @Override // org.eclipse.jdt.internal.compiler.lookup.Substitution
            public boolean isRawSubstitution() {
                return false;
            }

            @Override // org.eclipse.jdt.internal.compiler.lookup.Substitution
            public LookupEnvironment environment() {
                return InferenceContext18.this.environment;
            }

            @Override // org.eclipse.jdt.internal.compiler.lookup.Substitution
            public TypeBinding substitute(TypeVariableBinding typeVariableBinding) {
                TypeBinding instantiation;
                return (!(typeVariableBinding instanceof InferenceVariable) || ((instantiation = boundSet.getInstantiation((InferenceVariable) typeVariableBinding, InferenceContext18.this.environment)) == null && !z)) ? typeVariableBinding : instantiation;
            }
        };
    }

    public boolean isVarArgs() {
        return this.inferenceKind == 3;
    }

    public static TypeBinding getParameter(TypeBinding[] typeBindingArr, int i, boolean z) {
        if (z) {
            if (i >= typeBindingArr.length - 1) {
                return ((ArrayBinding) typeBindingArr[typeBindingArr.length - 1]).elementsType();
            }
        } else if (i >= typeBindingArr.length) {
            return null;
        }
        return typeBindingArr[i];
    }

    public MethodBinding getReturnProblemMethodIfNeeded(TypeBinding typeBinding, MethodBinding methodBinding) {
        if (typeBinding != null && (methodBinding.returnType instanceof ReferenceBinding) && methodBinding.returnType.erasure().isCompatibleWith(typeBinding)) {
            return methodBinding;
        }
        ProblemMethodBinding problemMethodBinding = new ProblemMethodBinding(methodBinding, methodBinding.selector, methodBinding.parameters, 23);
        if (typeBinding == null) {
            typeBinding = methodBinding.returnType;
        }
        problemMethodBinding.returnType = typeBinding;
        problemMethodBinding.inferenceContext = this;
        return problemMethodBinding;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer("Inference Context");
        int i = this.stepCompleted;
        if (i == 0) {
            stringBuffer.append(" (initial)");
        } else if (i == 1) {
            stringBuffer.append(" (applicability inferred)");
        } else if (i == 2) {
            stringBuffer.append(" (type inferred)");
        }
        int i2 = this.inferenceKind;
        if (i2 == 1) {
            stringBuffer.append(" (strict)");
        } else if (i2 == 2) {
            stringBuffer.append(" (loose)");
        } else if (i2 == 3) {
            stringBuffer.append(" (vararg)");
        }
        BoundSet boundSet = this.currentBounds;
        if (boundSet != null && isResolved(boundSet)) {
            stringBuffer.append(" (resolved)");
        }
        stringBuffer.append('\n');
        int i3 = 0;
        if (this.inferenceVariables != null) {
            stringBuffer.append("Inference Variables:\n");
            for (int i4 = 0; i4 < this.inferenceVariables.length; i4++) {
                stringBuffer.append('\t').append(this.inferenceVariables[i4].sourceName).append("\t:\t");
                BoundSet boundSet2 = this.currentBounds;
                if (boundSet2 != null && boundSet2.isInstantiated(this.inferenceVariables[i4])) {
                    stringBuffer.append(this.currentBounds.getInstantiation(this.inferenceVariables[i4], this.environment).readableName());
                } else {
                    stringBuffer.append("NOT INSTANTIATED");
                }
                stringBuffer.append('\n');
            }
        }
        if (this.initialConstraints != null) {
            stringBuffer.append("Initial Constraints:\n");
            while (true) {
                ConstraintFormula[] constraintFormulaArr = this.initialConstraints;
                if (i3 >= constraintFormulaArr.length) {
                    break;
                }
                if (constraintFormulaArr[i3] != null) {
                    stringBuffer.append('\t').append(this.initialConstraints[i3].toString()).append('\n');
                }
                i3++;
            }
        }
        BoundSet boundSet3 = this.currentBounds;
        if (boundSet3 != null) {
            stringBuffer.append(boundSet3.toString());
        }
        return stringBuffer.toString();
    }

    public static ParameterizedTypeBinding parameterizedWithWildcard(TypeBinding typeBinding) {
        ParameterizedTypeBinding parameterizedTypeBinding;
        TypeBinding[] typeBindingArr;
        if (typeBinding != null && typeBinding.kind() == 260 && (typeBindingArr = (parameterizedTypeBinding = (ParameterizedTypeBinding) typeBinding).arguments) != null) {
            for (TypeBinding typeBinding2 : typeBindingArr) {
                if (typeBinding2.isWildcard()) {
                    return parameterizedTypeBinding;
                }
            }
        }
        return null;
    }

    public TypeBinding[] getFunctionInterfaceArgumentSolutions(TypeBinding[] typeBindingArr) {
        TypeBinding[] typeBindingArr2 = new TypeBinding[typeBindingArr.length];
        int i = 0;
        while (true) {
            InferenceVariable[] inferenceVariableArr = this.inferenceVariables;
            if (i >= inferenceVariableArr.length) {
                return typeBindingArr2;
            }
            TypeBinding instantiation = this.currentBounds.getInstantiation(inferenceVariableArr[i], this.environment);
            if (instantiation != null) {
                typeBindingArr2[i] = instantiation;
            } else {
                typeBindingArr2[i] = typeBindingArr[i];
            }
            i++;
        }
    }

    public void recordUncheckedConversion(ConstraintTypeFormula constraintTypeFormula) {
        if (this.constraintsWithUncheckedConversion == null) {
            this.constraintsWithUncheckedConversion = new ArrayList();
        }
        this.constraintsWithUncheckedConversion.add(constraintTypeFormula);
        this.usesUncheckedConversion = true;
    }

    void reportUncheckedConversions(BoundSet boundSet) {
        List<ConstraintFormula> list = this.constraintsWithUncheckedConversion;
        if (list != null) {
            int size = list.size();
            Substitution resultSubstitution = getResultSubstitution(boundSet, true);
            for (int i = 0; i < size; i++) {
                ConstraintTypeFormula constraintTypeFormula = (ConstraintTypeFormula) this.constraintsWithUncheckedConversion.get(i);
                TypeBinding typeBinding = constraintTypeFormula.right;
                TypeBinding typeBinding2 = constraintTypeFormula.left;
                if (!typeBinding.isProperType(true)) {
                    Scope.substitute(resultSubstitution, typeBinding);
                }
                if (!typeBinding2.isProperType(true)) {
                    Scope.substitute(resultSubstitution, typeBinding2);
                }
            }
        }
    }

    public boolean usesUncheckedConversion() {
        return this.constraintsWithUncheckedConversion != null;
    }

    public static void missingImplementation(String str) {
        throw new UnsupportedOperationException(str);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void forwardResults(BoundSet boundSet, Invocation invocation, ParameterizedMethodBinding parameterizedMethodBinding, TypeBinding typeBinding) {
        Invocation invocation2;
        MethodBinding methodBindingBinding;
        ParameterizedMethodBinding parameterizedMethodBindingCreateParameterizedGenericMethod;
        ParameterizedMethodBinding parameterizedMethodBinding2 = parameterizedMethodBinding;
        if (typeBinding != null) {
            invocation.registerResult(typeBinding, parameterizedMethodBinding2);
        }
        Expression[] expressionArrArguments = invocation.arguments();
        int length = expressionArrArguments == null ? 0 : expressionArrArguments.length;
        for (int i = 0; i < length; i++) {
            Expression[] polyExpressions = expressionArrArguments[i].getPolyExpressions();
            int length2 = polyExpressions.length;
            int i2 = 0;
            while (i2 < length2) {
                BinaryExpression binaryExpression = polyExpressions[i2];
                if ((binaryExpression instanceof Invocation) && (methodBindingBinding = (invocation2 = (Invocation) binaryExpression).binding()) != null && methodBindingBinding.isValidBinding()) {
                    if (methodBindingBinding instanceof ParameterizedGenericMethodBinding) {
                        MethodBinding methodBindingShallowOriginal = methodBindingBinding.shallowOriginal();
                        TypeBinding[] solutions = getSolutions(methodBindingShallowOriginal.typeVariables(), invocation2, boundSet);
                        if (solutions != null) {
                            parameterizedMethodBindingCreateParameterizedGenericMethod = this.environment.createParameterizedGenericMethod(methodBindingShallowOriginal, solutions);
                            if (parameterizedMethodBindingCreateParameterizedGenericMethod == null && parameterizedMethodBindingCreateParameterizedGenericMethod.isValidBinding()) {
                                boolean zIsVarargs = parameterizedMethodBinding.isVarargs();
                                TypeBinding[] typeBindingArr = parameterizedMethodBinding2.parameters;
                                if (zIsVarargs && typeBindingArr.length == expressionArrArguments.length && i == length - 1 && parameterizedMethodBindingCreateParameterizedGenericMethod.returnType.capture(this.scope, binaryExpression.sourceStart, binaryExpression.sourceEnd).isCompatibleWith(typeBindingArr[typeBindingArr.length - 1], this.scope)) {
                                    zIsVarargs = false;
                                }
                                forwardResults(boundSet, invocation2, parameterizedMethodBindingCreateParameterizedGenericMethod, getParameter(typeBindingArr, i, zIsVarargs));
                            }
                        }
                    } else if (methodBindingBinding.isConstructor() && (methodBindingBinding instanceof ParameterizedMethodBinding)) {
                        MethodBinding methodBindingShallowOriginal2 = methodBindingBinding.shallowOriginal();
                        ReferenceBinding referenceBinding = methodBindingShallowOriginal2.declaringClass;
                        TypeBinding[] solutions2 = getSolutions(referenceBinding.typeVariables(), invocation2, boundSet);
                        if (solutions2 != null) {
                            MethodBinding[] methodBindingArrMethods = this.environment.createParameterizedType(referenceBinding, solutions2, methodBindingBinding.declaringClass.enclosingType()).methods();
                            int length3 = methodBindingArrMethods.length;
                            int i3 = 0;
                            while (true) {
                                if (i3 >= length3) {
                                    parameterizedMethodBindingCreateParameterizedGenericMethod = null;
                                    break;
                                }
                                MethodBinding methodBinding = methodBindingArrMethods[i3];
                                MethodBinding[] methodBindingArr = methodBindingArrMethods;
                                if (methodBinding.original() == methodBindingShallowOriginal2) {
                                    parameterizedMethodBindingCreateParameterizedGenericMethod = (ParameterizedMethodBinding) methodBinding;
                                    break;
                                } else {
                                    i3++;
                                    parameterizedMethodBinding2 = parameterizedMethodBinding;
                                    methodBindingArrMethods = methodBindingArr;
                                }
                            }
                            if (parameterizedMethodBindingCreateParameterizedGenericMethod == null) {
                            }
                        }
                    }
                }
                i2++;
                parameterizedMethodBinding2 = parameterizedMethodBinding;
            }
        }
    }

    public void cleanUp() {
        this.b2 = null;
        this.currentBounds = null;
    }
}
